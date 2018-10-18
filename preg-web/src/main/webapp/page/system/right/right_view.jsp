<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.all-3.5.js"></script>
<title>权限管理</title>
<style type="text/css">
.ztree li span.button.home_ico_open{margin-right:2px; background: url(${common.basepath}/common/plugins/zTree/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.home_ico_close{margin-right:2px; background: url(${common.basepath}/common/plugins/zTree/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_open{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_close{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_docu{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.menu_ico_open{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/menu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.menu_ico_close{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/menu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.menu_ico_docu{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/menu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.ztree {
    margin: 0;
    padding: 5px;
    color: #333;
    padding-left: 0px;
    padding-top: 8px;
}
.ztree li ul {
    padding: 0 0 5px 30px;
}
.ztree li span.button.chk {
    width: 13px;
    height: 13px;
    margin: 0 7px 0 0;
    cursor: auto;
}
</style>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};


var operateType;
var rightNameOld;
//*****************************************zTree开始****************************************
//当前选中结点
var selectNode;
//树对象
var treeObj;
//配置
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback : {
		beforeClick : beforeClick,
		beforeCheck : beforeCheck
	}
};
//*****************************************DataTable开始****************************************
var rightTable;
var rightData;
var rightRow;

var rightTableOptions = {
	id:"rightListTable",
	form:"rightQuery",
	columns: [
  		{"data": null,"sClass": "text-center",
  			"render":  function (data, type, row, meta) {
          		return "<input type='radio' name='tableRadio' value='"+data.rightId+"' />";
          	}
  		},
  		{"data": "rightId","sClass": "text-center" },
  		{"data": "rightName","sClass": "text-center" },
  		{"data": "rightType","sClass": "text-center",
  			"render":function(data,type,row,meta){
				return data==0?"默认":"系统";
			}	
  		}
	],
	rowClick: function(data, row){
		rightData = data;
		rightRow = row;
		$("#rightId").val(data.rightId);
	},
	condition : "rightCondition",
	selecttarget: []
};

//*****************************************验证开始**********************************************
/**
 * 验证参数设置
 */
var rightOptions = {
	rules: {
		rightName: {
			required: true,
			remote:	{
				url: URL.get("System.RIGHT_NAME_CHECK"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    rightName: function() {  
				        return $("#rightName").val();  
					},
					rightNameOld: function() {  
						return $("#rightNameOld").val(); 
					},
					operateType: function() {  
				        return operateType;  
					},
					random: function() {
						return Math.random();
					}
				}
			},
			maxlength: 50
		},
		rightType: {
			required: true
		}
	},
	messages: {
		rightName: {
			remote: "名称已经存在，请重新输入"
		}
	},
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		getAllNodes();
		$(form).ajaxPost(dataType.json,function(data){
			if(operateType == "add"){
				datatable.add(rightTable, data.value);// 添加
				$("#addMenuModal").modal("hide");
			} else if(operateType == "update"){
				datatable.update(rightTable, data.value, rightRow);// 修改
				$("#addMenuModal").modal("hide");
			}
		},true);
	}
};


$().ready(function (){
	//加入必填项提示
	$("#addMenuForm").validate(rightOptions);
	common.requiredHint("addMenuForm",rightOptions);
	
	//初始化生成树,以及控制节点张开
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandAll(true);
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	
	//加载dataTable插件
	rightTable = datatable.table(rightTableOptions);
	
	// 按钮点击事件
	$("button[name='rightPage']").click(function(){
		var rightId = $("#rightId").val();
		if(this.id == 'addButton'){
			operateType = "add";
			addRight();
		}
		if(this.id == 'editButton' && common.isChoose(rightId)){
			operateType = "update";
			editRight(rightId);
		}
		if(this.id == 'removeButton' && common.isChoose(rightId)){
			removeRight(rightId);
		}
		if(this.id == 'search'){
			rightTable = datatable.table(rightTableOptions);
		}
	});
});

function filter(treeId, parentNode, responseData) {
	if (!responseData)
		return null;
	for ( var i = 0, l = responseData.length; i < l; i++) {
		responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
	}
	return responseData;
}

//结点点击时间回调函数
function beforeClick(treeId, treeNode){
	if(!treeNode.checked){
		treeNode.checked = true;
	}else{
		treeNode.checked = false;
	}
	treeObj.updateNode(treeNode);
}

//节点选中事件
function beforeCheck(treeId, treeNode){
	treeObj.selectNode(treeNode);
}

//*****************************************自定义开始****************************************

function getAllNodes(){
	var nodes = treeObj.getCheckedNodes(true);
	var str = "";
	if(nodes.length > 0){
		$(nodes).each(function(index,node){
			if(!common.isEmpty(node.value)){
				str += node.id+",";
			}
		});
		str = str.substring(0, str.lastIndexOf(","));
	}
	$("#menuIdStr").val(str);
}

//添加权限
function addRight(){
	$("#addMenuForm").attr("action", URL.get("System.RIGHT_ADD"));
	common.clearForm("addMenuForm");
	$("input:radio[name='rightType']").attr("disabled", false);
	$("#addMenuForm [name='rightType'][value='0']").attr("checked", true);
	treeObj.checkAllNodes(false);
	$("#addMenuModal").modal("show");
}

//修改权限
function editRight(rightId){
	$("#addMenuForm").attr("action", URL.get("System.RIGHT_UPDATE"));// 设置action
	common.clearForm("addMenuForm");// 清空表单
	$("#addMenuForm [name='rightId']").val(rightId);// 设置修改主键
	treeObj.checkAllNodes(false);// 取消选中状态
	var url = URL.get("System.RIGHT_INIT_UPDATE");
	var params = "rightId="+rightId;
	ajax.post(url,params,dataType.json,function(data){
		var right = data.value;
		$("#addMenuModal").modal("show");
		$("#rightName").val(right.rightName);
		$("#rightNameOld").val(right.rightName);
		$("input:radio[name='rightType'][value='"+right.rightType+"']").attr("checked", true);
		if(right.rightType == 1){
			$("input:radio[name='rightType']").attr("disabled", true);
		}else{
			$("input:radio[name='rightType']").attr("disabled", false);
			$("#removeButton").attr("disabled", false);
		}
		if(!common.isEmpty(right.menuList)){
			$(right.menuList).each(function(index,menu){
				if(menu.menuType != 1){
					treeObj.checkNode(treeObj.getNodeByParam("id", menu.menuId, null), true, true);
				}
			});
		}
	});
};


//删除角色
function removeRight(rightId){
	layer.confirm("确定对选中内容执行【删除】操作？", function (index) {
		var url = URL.get("System.RIGHT_DEL");
		var params = "rightId="+rightId;
		ajax.post(url,params,dataType.json,function(data){
			datatable.remove(rightTable, rightRow);
		});
		layer.close(index);
	});
};
</script>
</head>
<body>
<form id="addMenuForm" class="form-horizontal">
<input id="menuIdStr" name="menuIdStr" type="hidden" />
<input id="rightNameOld" name="rightNameOld" type="hidden" />
<div id="addMenuModal" class="modal fade bs-example-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 添加权限<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="panel-body">
						<div class="form-group">
		                    <label class="col-xs-4 control-label">权限名称</label>
		                    <div class="col-xs-5">
		                    	<input id="rightId" name="rightId" type="hidden" />
		                        <input id="rightName" name="rightName" class="form-control" type="text" maxlength="50" />
		                    </div>
		               	</div>
		               	<div class="form-group">
		                    <label class="col-xs-4 control-label">权限类型</label>
		                    <div class="col-xs-5">
		                    	<label class="radio-inline"><input type="radio" name="rightType" value="0" checked/>默认</label>
		                    	<label class="radio-inline"><input type="radio" name="rightType" value="1"/>系统</label>
		                    </div>
		               	</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">权限菜单</label>
							<div class="col-xs-7"><ul id="zTree" class="ztree"></ul></div>
						</div>
					</div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="submit" class="btn btn-primary btn-block">确定</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<div class="row">
	<div class="panel-body">
		<div id="rightCondition">
			<form id="rightQuery" name="rightQuery" action="${common.basepath }/${applicationScope.URL.System.RIGHT_QUERY}" class="form-horizontal" method="post">
                 <div class="form-inline">
                     <input name="rightName" class="form-control" type="text" placeholder="请输入角色名称">
		      		<button type="button" id="search" name ="rightPage" class="btn btn-default">
		      			<i class="fa fa-search fa-fw"></i>查询
		      		</button>
		      		<div class="vertical-line"></div>
		            <button type="button" id="addButton" name="rightPage" class="btn btn-default">
			     		<i class="fa fa-plus fa-fw"></i> 增加
			  		</button>
			  		<button type="button" id="editButton" name="rightPage" class="btn btn-default">
			     		<i class='fa fa-edit fa-fw'></i> 编辑
			  		</button>
			  		<button type="button" id="removeButton" name="rightPage" class="btn btn-default">
		    	 		<i class='fa fa-remove fa-fw'></i> 删除
		  			</button>
                  </div>
			</form>
		</div>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-bordered table-hover" id="rightListTable">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">权限编号</th>
				<th class="text-center">权限名称</th>
				<th class="text-center">权限类型</th>
			</tr>
		</thead>
	</table>
</div>

</body>
</html>