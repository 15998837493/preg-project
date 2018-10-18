<%@ page language="java" pageEncoding="utf-8"%>
<script type="text/javascript">
var pswUpdateModel = {};

$().ready(function() {
	$("#pswModifyForm").validate(pwsModifyOperation);
	//加入必填项提示
	common.requiredHint("pswModifyForm",pwsModifyOperation);
	$('#pswUpdateModel').on('hide.bs.modal', function (e) {
		common.removeErrorMsg("pswModifyForm");
		$("#oldpsw").val('');
	    $("#newpsw").val('');
	    $("#ackpsw").val('');
	});
});

pswUpdateModel.openPswUpdateModel = function(){
	$('#pswUpdateModel').modal('show');
};
formSubmint = function(){
	$("#pswModifyForm").submit();
};


clearData = function(){
	$("#oldpsw").val('');
    $("#newpsw").val('');
    $("#ackpsw").val('');
};


/**
 * 验证参数设置
 */
var pwsModifyOperation = {
	rules: {
		oldpsw: {
			required: true,
			minlength: 6,
			maxlength: 15
		},
		newpsw: {
			required: true,
			minlength: 5,
			maxlength: 15
		},
		ackpsw: {
			required: true,
			minlength: 5,
			maxlength: 15,
			equalTo: "#newpsw"
		}
	},
	messages: {
		oldpsw: {
			required: "请输入原密码",
			minlength: "您的原密码至少输入6个字符",
			maxlength: "您的原密码最大输入15个字符"
		},
		newpsw: {
			required: "请输入新密码",
			minlength: "您的新密码至少输入6个字符",
			maxlength: "您的新密码最大输入15个字符"
		},
		ackpsw: {
			required: "请输入新密码",
			minlength: "您的新密码至少输入6个字符",
			maxlength: "您的新密码最大输入15个字符",
			equalTo: "输入的确认新密码与新密码不同"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		$(form).ajaxPost(dataType.json,function(data){
			addSuc(data);
		});
	}
};

addSuc = function(data){
	layer.msg(data.message);
	$('#pswUpdateModel').modal('hide');
	clearData();
}
</script>

<form id="pswModifyForm" name="pswModifyForm" action="${common.basepath}/page/user/psw_update_user.action" method="post" class="form-horizontal">
<div id="pswUpdateModel"  class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 密码修改<a class="close" onclick="clearData();" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="row">
						<div class="col-xs-10 col-xs-offset-2 row-top">
							<div class="form-group">
			                    <label class="col-xs-3 control-label">输入原密码</label>
			                    <div class="col-xs-5">
			                        <input id="oldpsw" name="oldpsw" class="form-control" type="password" maxlength="15" required />
			                    </div>
		               		</div>
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">输入新密码</label>
			                    <div class="col-xs-5">
			                        <input id="newpsw" name="newpsw" class="form-control" type="password" maxlength="15" required />
			                    </div>
		               		</div>
		               		<div class="form-group">
			                    <label class="col-xs-3 control-label">再次新密码</label>
			                    <div class="col-xs-5">
			                        <input id="ackpsw" name="ackpsw" class="form-control" type="password" maxlength="15" required />
			                    </div>
		               		</div>
						</div>
					</div>
				</div>
				<div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right" >
						<button onclick="formSubmint()" type="button" class="btn btn-primary btn-block">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</form>