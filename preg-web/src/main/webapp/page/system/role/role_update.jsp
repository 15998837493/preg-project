<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>编辑职位</title>
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
						return $("#roleNameOld").val(); 
					},
					operateType: function() {  
				        return "update";  
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
	$("#updateForm").validate(roleOptions);
	//加入必填项提示
	common.requiredHint("updateForm",roleOptions);
	
	//返回按钮点击事件
	$("#backButton").click(function(){
		common.pageForward(URL.get("SystemPage.ROLE_VIEW"));
	});
	
	// 初始化已选职位
	var list = ${currentRightList};
	if(!common.isEmpty(list)){
		$(list).each(function(index, right){
			$("input:checkbox[name='rightIdList'][value='"+right.rightId+"']").attr("checked", true);
		});
	}
	
	//初始化职位类型
	var roleType = "${role.roleType}";
	$("input:radio[name='roleType'][value='"+roleType+"']").attr("checked", true);
	if(roleType == 1){
		$("input:radio[name='roleType']").attr("disabled", true);
	}else{
		$("input:radio[name='roleType']").attr("disabled", false);
	}
});
</script>
<body>
<input id="roleNameOld" name="roleNameOld" type="hidden" value="${role.roleName}"/>
<div class="container-fluid">
<div class="row row-top">
	<form id="updateForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.System.ROLE_UPDATE}" method="post">
		<div class="col-xs-10 col-xs-offset-1 border-bottom-blue">
	      	<label class="label-title"><i class="fa fa-edit fa-fw"></i>填写职位信息</label>
	  	</div>
		<div class="col-xs-9 col-xs-offset-2 row-top">
			<div class="form-group">
				<label class="col-xs-4 control-label">职位名称</label>
				<div class="col-xs-3">
					<input id="roleId" name="roleId" type="hidden" value="${role.roleId }"/>
					<input id="roleName" name="roleName" type="text" class="form-control" maxlength="50" value="${role.roleName}"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">职位类型</label>
               	<div class="col-xs-5">
               		<label class="radio-inline"><input type="radio" name="roleType" value="0"/>默认</label>
               		<label class="radio-inline"><input type="radio" name="roleType" value="1"/>系统</label>
               	</div>
          	</div>
			<div class="form-group">
				<label class="col-xs-4 control-label">选择权限</label>
				<div class="col-xs-5">
					<c:forEach items="${rightList}" var="right" varStatus="i">
					<div class="checkbox">
						<label><input name="rightIdList" type="checkbox" value="${right.rightId}" />${right.rightName}&nbsp;&nbsp;</label>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-xs-10 col-xs-offset-1 border-top-blue row-top">
			<div class="col-xs-8 col-xs-offset-3">
				<div class="col-xs-4">
					<button class="btn btn-primary btn-block" type="submit"><i class='fa fa-save fa-fw'></i>保存</button>
				</div>
				<div class="col-xs-4">
					<button class="btn btn-primary btn-block" type="button" id="backButton"><i class='fa fa-share-square-o fa-fw'></i>返回</button>
				</div>
			</div>
		</div>
	</form>
</div>
</div>
</body>
</html>