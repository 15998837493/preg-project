<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>增加职位</title>
<%@ include file="/common/common.jsp" %>
<body>
<script type="text/javascript">
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
						return ""; 
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
		common.btnDisable(true);
		box.confirm("确定要执行【保存】操作？", function (data) {
            if (data) {
				$(form).ajaxPost(dataType.json,function(data){
					if(data.result){
						common.pageForward(URL.get("SystemPage.ROLE_VIEW"));
					}else{
						box.alert(data.message,{
							title: '错误信息'
						});
					}
				});
            }else{
            	common.btnDisable(false);
            }
        }, {
            title: '提示信息'
        });
	}
};

$().ready(function() {
	//加入必填项提示
	$("#addRoleForm").validate(roleOptions);
	common.requiredHint("addRoleForm",roleOptions);
	
	//返回按钮点击事件
	$("#backButton").click(function(){
		common.pageForward(URL.get("SystemPage.ROLE_VIEW"));
	});
});
</script>
<div class="container-fluid">
	<div class="row row-top">
		<form id="addRoleForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.System.ROLE_ADD}" method="post">
			<div class="col-xs-10 col-xs-offset-1 border-bottom-blue">
				<label class="label-title"><i class="fa fa-edit fa-fw"></i>编辑职位信息</label>
			</div>
			<div class="col-xs-9 col-xs-offset-2 row-top">
				<div class="form-group">
				    <label class="col-xs-4 control-label">职位名称</label>
				    <div class="col-xs-3">
				        <input id="roleName" name="roleName" class="form-control" type="text" maxlength="50" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">职位类型</label>
	               	<div class="col-xs-5">
	               		<label class="radio-inline"><input type="radio" name="roleType" value="0" checked/>默认</label>
	               		<label class="radio-inline"><input type="radio" name="roleType" value="1"/>系统</label>
	               	</div>
	          	</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">选择权限</label>
					<div class="col-xs-5" id="right_list">
						<c:if test="${empty rightList }">
	               			<label class="col-xs-12 control-label no-left" style="color:red;text-align:left;">请先创建权限！</label>
	               		</c:if>
					   	<c:forEach items="${rightList}" var="rights" varStatus="i">
							<div class="checkbox">
								<label><input id="rightIdList" name="rightIdList" type="checkbox" value="${rights.rightId}" />${rights.rightName}</label>
							</div>
						</c:forEach>
				    </div>
				</div>
			</div>
			<div class="panel-body padding-zero" style="padding-bottom: 15px;">
				<div class="col-xs-2 col-xs-offset-8 no-right">
					<button type="submit" class="btn btn-primary btn-block">确认</button>
				</div>
				<div class="col-xs-2 no-right">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>
