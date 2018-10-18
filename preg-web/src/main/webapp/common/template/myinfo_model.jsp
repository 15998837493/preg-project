<%@ page language="java" pageEncoding="utf-8"%>
<script type="text/javascript">
var myInfoUpdateModel = {};
var userVo;

$().ready(function() {
	//加入必填项提示
	$("#updateUserForm").validate(updateUserOperation);
	common.requiredHint("updateUserForm",updateUserOperation);
});

myInfoUpdateModel.openMyInfoUpdateModel = function(loginUserData){
	userVo=loginUserData;
	$("#userId").val(userVo.userId);//用户编码
	$("#userCode").val(userVo.userCode);//用户编码
	$("#userName").val(userVo.userName);//用户姓名
	$("#userSex").val(userVo.userSex);//用户性别
	$("#roleName").val(userVo.roleName);//用户职位
	common.initDate(null,null,4,"#userBirthday");
	$("#userBirthday").datetimepicker("update", userVo.userBirthday);
	$("#userPhone").val(userVo.userPhone);//用户手机
	$("#userEmail").val(userVo.userEmail);//用户邮箱
	$('#myInfoUpdateModel').modal('show');
};


/**
 * 验证参数设置
 */
var updateUserOperation = {
	rules: {
		userName: {
			required: true
		},
		userPhone: {
			mobile: true
		},
		roleId:{
			required: true
		},
		userEmail:{
			email: true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function() {
		layer.confirm("确定要执行【修改】操作？", function (data) {
			if (data) {
				$('#updateUserForm').ajaxPost(dataType.json,function(data){
					$('#myInfoUpdateModel').modal('hide');
					loginUserData=data.value;
				});
			}
		});
	}
};
clearData = function(){
	$('#myInfoUpdateModel').modal('hide');
};
	
</script>
<form id="updateUserForm" name="updateUserForm" action="${common.basepath}/page/user/update_user_myinfo.action" method="post" class="form-horizontal">
<div id="myInfoUpdateModel"  class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 个人信息<a class="close" onclick="clearData();" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="row">
						<div class="col-xs-10 col-xs-offset-2 row-top">
							<input type="hidden" id="userId" name="userId"/>
							<div class="form-group">
			                    <label class="col-xs-3 control-label">用户编号</label>
			                    <div class="col-xs-5">
			                        <input id="userCode" name="userCode" class="form-control" type="text" maxlength="32" readonly/>
			                    </div>
		               		</div>
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">用户姓名</label>
			                    <div class="col-xs-5">
			                        <input id="userName" name="userName" class="form-control" type="text" maxlength="80"/>
			                    </div>
		               		</div>
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">用户性别</label>
			                    <div class="col-xs-5">
		                        <select id="userSex" name="userSex" class="form-control">
		                        	<option value="">==请选择性别==</option>
		                        	<option value="male">男</option>
		                        	<option value="female">女</option>
		                        </select>	                        
			                    </div>
		               		</div>             		
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">用户职位</label>
			                    <div class="col-xs-5">
									<input id="roleName" name="roleName" class="form-control" type="text" readonly/>                       
			                    </div>
		               		</div>
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">出生日期</label>
			                    <div class="col-xs-5">
			                        <div class="input-group">
								      	<input id="userBirthday" name="userBirthday" type="text" class="form-control form_date" placeholder="请选择出生日期" readonly value=""/>
								      	<span class="input-group-btn">
								        	<button class="btn btn-primary" type="button" onclick="common.dateShow('userBirthday')"><i class="fa fa-calendar fa-fw"></i>选择</button>
								      	</span>
					    			</div>                       
			                    </div>
		               		</div>
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">用户手机</label>
			                    <div class="col-xs-5">
									<input id="userPhone" name="userPhone" class="form-control" type="text" maxlength="22"/>	                      
			                    </div>
		               		</div>
		               		<div class="form-group" style="padding: 0px;">
			                    <label class="col-xs-3 control-label">用户邮箱</label>
			                    <div class="col-xs-5">
									<input id="userEmail" name="userEmail" class="form-control" type="text" maxlength="50"/>	                      
			                	</div>
               				</div>
						</div>
					</div>
				</div>
				<div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right" >
						<button type="submit"  class="btn btn-primary btn-block">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>