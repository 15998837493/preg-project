<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.all-3.5.js"></script>
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
<title>机构管理</title>
</head>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};
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


//dataTable配置
var insData;
var insRow;
var insTable;

var insTableOption = {
		id:"insListTable",
		form:"insQuery",
		columns: [
		  		{"data": null,"sClass": "text-center",
		  			"render":  function (data, type, row, meta) {
		          		return "<input type='radio' name='tableRadio' value='"+data.insId+"' />";
		          	}
		  		},
 		  	    {"data": "insId","sClass": "text-center" },
		  		{"data": "insName","sClass": "text-left" },
		  		{"data": "insPlace","sClass": "text-left" },
		  		{"data": "insGrade","sClass": "text-left" },
		  		{"data": "insPost","sClass": "text-left" },
		  		{"data": "insType","sClass": "text-left" },
		  		{"data": "insTel","sClass": "text-left" },
		  		{"data": null,"sClass": "text-center",
					"render":function(data, type, row, meta){
						insState = data.insState=="1"?"运行":(data.insState=="0"?"停止":"--");
						return  insState;
					}	
			  	},
		],
		rowClick: function(data, row){
			insData = data;
			insRow = row;
			$("#insId").val(data.insId);
			if(data.insState == 1){// 当前是运行状态
				$("#stateButton").hide();
				$("#runButton").hide();
				$("#stopButton").show();
			}else{
				$("#stateButton").hide();
				$("#runButton").show();
				$("#stopButton").hide();
			}
		},
		condition : "insCondition",
		selecttarget: [8]
};

$().ready(function (){
	
	//初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandAll(true);
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	//初始化加载页面数据
	insTable = datatable.table(insTableOption);
	
	//绑定点击事件
	$("button[name='insPage']").click(function(){
		var insId = $("#insId").val();
		if(this.id == 'addButton'){
			common.pageForward(URL.get("SystemPage.INS_ADD"));
		}
		if(this.id == 'editButton' && common.isChoose(insId)){
			common.pageForward(URL.get("System.INS_UPDATE_INIT")+"?insId="+insId);
		}
		if(this.id == 'menuButton' && common.isChoose(insId)){
			//初始化菜单权限
			initInsMenu();
		}
		if(this.id == 'printButton' && common.isChoose(insId)){
			//初始化菜单权限
			initInsPrint();
		}
		if(this.id == 'runButton' && common.isChoose(insId)){
			setInsState(1);
		}
		if(this.id == 'stopButton' && common.isChoose(insId)){
			setInsState(0);
		}
		if(this.id == 'searchButton'){
			insTable = datatable.table(insTableOption);
		}
	});
});

//*****************************************自定义开始****************************************
// 初始化机构功能菜单
function initInsMenu(){
	// 取消选中状态
	treeObj.checkAllNodes(false);
	// 异步获取机构功能菜单列表
	var url = URL.get("System.INS_MENU");
	var params = "insId="+$("#insId").val();
	ajax.post(url,params,dataType.json,function(data){
		var menuList = data.value;
		if(!common.isEmpty(menuList)){
			$(menuList).each(function(index,menu){
				if(menu.menuType != 1){
					treeObj.checkNode(treeObj.getNodeByParam("id", menu.menuId, null), true, true);
				}
			});
		}
		$('#InsMenuModal').modal('show');
	}, false, false);
}

// 保存机构权限菜单
function saveInsMenu(){
    layer.confirm("确定要执行【保存】操作？", function () {
       	var url = URL.get("System.INS_MENU_SAVE");
       	var params = "insId="+$("#insId").val()+"&menuIdStr="+getAllNodes();
	    ajax.post(url,params,dataType.json,function(data){
	    	$("#InsMenuModal").modal("hide");
	    });
    });
}

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
	return str;
}

// 更改机构运行状态
function setInsState(insState){
	var content = (insState==1)?"运行":"停止";
	layer.confirm("确定要执行【"+content+"】操作？", function () {
		var url = URL.get("System.INS_STATE");
		var params = "insId="+$("#insId").val()+"&insState="+insState;
		ajax.post(url,params,dataType.json,function(data){
			common.pageForward(URL.get("System.INS_VIEW"));
		});
	});
}

//初始化机构功能菜单
function initInsPrint(){
	$("input:checkbox[name='printIdList']").attr("checked", false);
	var url = URL.get("System.INS_PRINT");
	var params = "insId="+$("#insId").val();
	ajax.post(url, params, dataType.json, function(data){
		var printIdList = data.value;
		if(!_.isEmpty(printIdList)){
			$(printIdList).each(function(index, printId){
				$("input:checkbox[name='printIdList'][value='"+printId+"']").attr("checked", true);
			});
		}
		$('#InsPrintModal').modal('show');
	}, false, false);
}

//保存机构权限菜单
function saveInsPrint(){
    layer.confirm("确定要执行【保存】操作？", function () {
    	var printIds = [];
    	$("input:checkbox[name='printIdList'][checked]").each(function(index, print){
    		printIds.push(print.value);
    	});
       	var url = URL.get("System.INS_PRINT_SAVE");
       	var params = "insId="+$("#insId").val()+"&printIdStr="+printIds.join(",");
	    ajax.post(url,params,dataType.json,function(data){
	    	$("#InsPrintModal").modal("hide");
	    });
    });
}
</script>
<body>
<input type="hidden" id="insId" name="insId" />

<!-- 分配权限modal -->
<form id="InsMenuForm" class="form-horizontal" action="${common.basepath}/page/institution/add_ins_user.action" method="post">
<div id="InsMenuModal" class="modal fade bs-example-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body" style="padding-bottom: 0px;">
				<div class="panel panel-lightblue margin-zero">
					<div class="panel-heading text-center"><h3 class="modal-title text-center">分配权限</h3></div>
					<div class="form-group">
						<div class="col-xs-7" style="margin-left: 27%"><ul id="zTree" class="ztree"></ul></div>
					</div>
				</div>
			</div>
			<div class="panel-body padding-zero" style="padding-top: 0px;">
				<div class="col-xs-2 col-xs-offset-8 ">
					<button type="button" class="btn btn-primary btn-block" onclick="saveInsMenu();">确定</button>
				</div>
				<div class="col-xs-2 ">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<!-- 配置打印选项modal -->
<form id="InsPrintForm" class="form-horizontal" action="" method="post">
<div id="InsPrintModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-print"></i> 选择营养病历内容
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body form-horizontal">
						<c:forEach items="${printListMap }" var="map">
							<c:if test="${not empty map.value }">
								<div class="form-group">
									<div class="col-xs-12">
										<span class="label-title"><i class="fa fa-tag fa-fw"></i> ${map.key }</span>
									</div>
									<div class="col-xs-12"><HR style="margin: 5px 0 0 0;"></div>
									<div class="col-xs-11 col-xs-offset-1">
										<c:forEach items="${map.value }" var="list">
											<div class="col-xs-4 no-left">
												<label class="checkbox-inline">
													<input type="checkbox" name="printIdList" value="${list.printId }">${list.printItem }
												</label>
											</div>
										</c:forEach>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="saveInsPrint();">确定</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<div class="row">
	<div class="panel-body" id="insCondition">
		<form id="insQuery" action="${common.basepath}/${applicationScope.URL.System.INS_QUERY}" method="post" class="form-horizontal">
			<div class="form-inline">
				<input name="insName" class="form-control" type="text" placeholder="请输入机构名称" />
				<select id="insState" name="insState" class="form-control">
					<option value="">==请选择==</option>
					<option value="1">运行</option>
					<option value="0">停止</option>
				</select>
				<button type="button" id="searchButton" name="insPage" class="btn btn-default">
					<i class="fa fa-search fa-fw"></i>查询
				</button>
				<div class="vertical-line"></div>
				<button id="addButton" name="insPage" type="button" class="btn btn-default">
					<i class="fa fa-plus fa-fw"></i> 机构注册
				</button>
				<button id="editButton" name="insPage" type="button" class="btn btn-default">
					<i class="fa fa-edit fa-fw"></i> 机构编辑
				</button>
				<button id="menuButton" name="insPage" type="button" class="btn btn-default">
					<i class="fa fa-male fa-fw"></i> 分配权限
				</button>
				<button id="printButton" name="insPage" type="button" class="btn btn-default">
					<i class="fa fa-male fa-fw"></i> 配置打印选项
				</button>
				<button id="stateButton" name="insPage" type="button" class="btn btn-default">
					<i class="fa fa-refresh fa-fw"></i> 运行/停止
				</button>
				<button id="runButton" name="insPage" type="button" class="btn btn-default" style="display: none">
					<i class="fa fa-play fa-fw"></i> 运行
				</button>
				<button id="stopButton" name="insPage" type="button" class="btn btn-default" style="display: none">
					<i class="fa fa-stop fa-fw"></i> 停止
				</button>
			</div>
		</form>
	</div>
</div>
<div class="table-responsive">
	<table id="insListTable" class="table table-bordered table-hover">
	<thead>
		<tr class="active">
			<th class="text-center">选择</th>
			<th class="text-center">编号</th>
			<th class="text-center">名称</th>
	     	<th class="text-center">地区</th>
	     	<th class="text-center">客户等级</th>
	     	<th class="text-center">客户邮编</th>
	     	<th class="text-center">客户类型</th>
	     	<th class="text-center">电话</th>
	     	<th class="text-center">运行状态</th>
	   </tr>
	</thead>
	</table>
</div>
</body>
</html>