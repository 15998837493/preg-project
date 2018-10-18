<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<title>职位管理</title>
</head>
<script type="text/javascript">
//按钮参数
var operateType;
//datatable配置
var roleTable;
var roleData;
var roleRow;

var roleTableOptions = {
	id:"roleListTable",
	form:"roleQuery",
	columns:[
		{"data":null,"sClass":"text-center",
			"render":function(data,type,row,meta){
				return "<input type='radio' name='tableRadio' value="+data.roleId+"/>";
			}
		},
		{"data":"roleId","sClass":"text-center"},
		{"data":"roleName","sClass":"text-center"},
		{"data":"roleType","sClass":"text-center",
			"render":function(data,type,row,meta){
				return data==0?"默认":"系统";
			}
		}
	],
	rowClick: function(data, row){
		roleData = data;
		roleRow = row;
		$("#roleId").val(data.roleId);
	},
	condition : "roleQueryCondition",
};

//验证
var roleOptions = {
	rules: {
		roleName: {
			required: true,
			remote:	{
				url: URL.get("System.ROLE_NAME_CHECK"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    roleName: function() {  
				        return $("#roleName").val();  
					},
					roleNameOld: function() {  
						return $("#roleNameOld").val(); 
					},
					operateType: function() {  
				        return "add";  
					},
					random: function() {
						return Math.random();
					}
				}
			},
			maxlength: 50
		},
		roleType: {
			required: true
		}
	},
	messages: {
		roleName: {
			remote: "名称已经存在，请重新输入"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function () {
			$(form).ajaxPost(dataType.json,function(data){
				if(operateType == "add"){
					datatable.add(roleTable, data.value);// 添加
					$("#editRoleModal").modal("hide");
				} else if(operateType == "update"){
					datatable.update(roleTable, data.value, roleRow);// 修改
					$("#editRoleModal").modal("hide");
				}
			});		
        });
	}
};

$().ready(function() {
	// 加载dataTable
	roleTable = datatable.table(roleTableOptions);

	//参数验证
	$("#addRoleForm").validate(roleOptions);
	common.requiredHint("addRoleForm",roleOptions);
	
	// 按钮点击事件
	$("button[name='rolePage']").click(function(){
		var roleId = $("#roleId").val();
		common.clearForm("addRoleForm");
		if(this.id == 'addButton'){
			operateType = "add";
			$("#operateType").val(operateType); 
			$("#addRoleForm").attr("action", URL.get("System.ROLE_ADD"));
			$("#editRoleModal").modal("show");
		}
		if(this.id == 'editButton' && common.isChoose(roleId)){
			operateType = "update";
			$("#roleNameOld").val(roleData.roleName); 
			$("#operateType").val(operateType); 
			$("#addRoleForm #roleName").val(roleData.roleName);
			$("#addRoleForm #roleId").val(roleData.roleId);
			$("#addRoleForm input[name=roleType]").each(function(index,radioObj){
				if($(radioObj).val()==roleData.roleType){
					$(radioObj).attr("checked",true);
				}
			});
			var rightIdArray = [];
			$(roleData.rightList).each(function(index,right3){
				rightIdArray.push(right3.rightId);
			});
			var rightIdListBox = $("#addRoleForm #rightIdList");
			rightIdListBox.each(function(index,boxObj){
				if($.inArray($(boxObj).val(),rightIdArray)>-1){
					$(boxObj).attr("checked","checked");
				}else{
					$(boxObj).removeAttr("checked");
				}
			});
			$("#addRoleForm").attr("action", URL.get("System.ROLE_UPDATE"));
			$("#editRoleModal").modal("show");
		}
		if(this.id == 'removeButton' && common.isChoose(roleId)){
			removeRole(roleId);
		}
		if(this.id == 'search'){
			//roleTable = datatable.table(roleTableOptions);
			datatable.search(roleTable, roleTableOptions);
		}
	});
});


// 修改职位信息
function editRole(roleId){
	common.pageForward(URL.get("System.ROLE_INIT_UPDATE") +"?roleId="+roleId);
};

// 删除职位
function removeRole(roleId){
	layer.confirm("确定对选中内容执行【删除】操作？", function () {
    	var url = URL.get("System.ROLE_REMOVE");
		var params = "roleId="+roleId;
		ajax.post(url,params,dataType.json,function(data){
			common.pageForward(URL.get("SystemPage.ROLE_VIEW"));
		});
	});
};

</script>
<body>
<form id="addRoleForm" class="form-horizontal" action="" method="post">
<div id="editRoleModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		<div class="modal-body">
			<div class="panel panel-lightblue">
				<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 添加权限<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
				<div class="panel-body">
					<div class="form-group">
					    <label class="col-xs-3 control-label">职位名称</label>
					    <div class="col-xs-8">
					    	<input type="hidden" id="roleId" name="roleId"/>
					        <input id="roleName" name="roleName" class="form-control" type="text" maxlength="50" />
					        <input type="hidden" id="roleNameOld" name="roleNameOld"/>
					        <input type="hidden" id="operateType" name="operateType"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">职位类型</label>
		             	<div class="col-xs-8">
		             		<label class="radio-inline"><input type="radio" name="roleType" value="0" checked/>默认</label>
		             		<label class="radio-inline"><input type="radio" name="roleType" value="1"/>系统</label>
		             	</div>
		        	</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">选择权限</label>
						<div class="col-xs-8" id="right_list">
							<c:if test="${empty rightList }">
		             			<label class="col-xs-12 control-label no-left" style="color:red;text-align:left;">请先创建权限！</label>
		             		</c:if>
						   	<c:forEach items="${rightList}" var="rights" varStatus="i">
								<div class="checkbox col-xs-3" style="padding-left: 0px;">
									<label><input id="rightIdList" name="rightIdList" type="checkbox" value="${rights.rightId}" />${rights.rightName}</label>
								</div>
							</c:forEach>
					    </div>
					</div>
				</div>
			</div>
			<div class="panel-body padding-zero">
				<div class="col-xs-2 col-xs-offset-10 no-right">
					<button type="submit" class="btn btn-primary btn-block">确认</button>
				</div>
			</div>
		</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<div class="row">
	<div class="panel-body" id="roleQueryCondition">
		<form id="roleQuery" name="roleQuery" action="${common.basepath }/${applicationScope.URL.System.ROLE_QUERY}" method="post" class="form-horizontal">
			<div class="form-inline">
				<input id="roleName" name="roleName" class="form-control" type="text" placeholder="请输入职位名称">
				<button type="button" id="search" name="rolePage" class="btn btn-default">
					<i class="fa fa-search fa-fw"></i>查询
				</button>
				<div class="vertical-line"></div>
				<button type="button" id="addButton" name="rolePage" class="btn btn-default">
					<i class="fa fa-plus fa-fw"></i> 增加
				</button>
				<button type="button" id="editButton" name="rolePage" class="btn btn-default">
					<i class='fa fa-edit fa-fw'></i> 编辑
				</button>
				<button type="button" id="removeButton" name="rolePage" class="btn btn-default">
					<i class='fa fa-remove fa-fw'></i> 删除
				</button>
			</div>
		</form>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-bordered table-hover" id="roleListTable">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">职位编号</th>
				<th class="text-center">职位名称</th>
				<th class="text-center">职位类型</th>
			</tr>
		</thead>
	</table>
</div>

</body>
</html>
