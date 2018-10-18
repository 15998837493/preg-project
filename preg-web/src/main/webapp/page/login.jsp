<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>孕期营养门诊诊疗系统</title>
<link rel="stylesheet" type="text/css" href="common/plugins/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="common/plugins/bootstrap3.2.0/css/bootstrap-default.min.css" />
<link rel="stylesheet" type="text/css" href="common/mnt/css/style.css" />

<script type="text/javascript" src="common/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="common/plugins/layer/layer.js"></script>

<style type="text/css">
body {
	padding-top: 180px;
	padding-bottom: 40px;
	background-color:#d5d5d5; 
	background-image:url(common/images/login-bj.jpg);
	background-repeat:no-repeat;
	background-position:center 0;
}
#table_td{
	border:0;
}
.panel{
	background-color:transparent;
	background-image:url(common/images/login-bj02.png);
	background-repeat:no-repeat;
	background-position:center 0;
	box-shadow:none;
}
.btn-primary{
	background-image: linear-gradient(to bottom, #2881df 0px, #1666b9 100%);
   	background-repeat: repeat-x;
   	border-color: #2b669a;
}
.btn-primary:hover,.btn-primary:focus{
	background-color:#1666b9;
}
.input-lg, .form-horizontal .form-group-lg .form-control{
	font-size:16px;
}
.control-label{
	line-height:30px;
	font-size:18px;
	color:#696969;
}
</style>
<script type="text/javascript">
// 登录
function loginsubmit(){
	// 校验数据有效性
	if ($("#loginCode").val() == "") {
		layer.alert("请输入账户!", function(index){
    		$("#loginCode").focus();
    		layer.close(index);
    	});
		return false;
	}
	if ($("#loginPsw").val() == "") {
		layer.alert("请输入密码!", function(index){
    		$("#loginPsw").focus();
    		layer.close(index);
    	});
		return false;
	}
	// 执行登录操作
	var index = layer.loading();	
	var form = $("#LoginForm");
	$.ajax({
		type: "post",
    	url: form.attr('action'),
    	data: form.serialize()+'&random='+Math.random(),
    	dataType: "json", 
    	success: function(data){
    		if(data.result){
    			//登陆成功
    			$("#LoginForm").attr('action', 'main.action');
    			$("#LoginForm").submit();
    		} else{
    			layer.close(index);
    			layer.alert(data.message, function(index){
    				if(data.message == "输入的账号不存在"){
    					$("#loginCode").focus();
    				} else{
    					$("#loginPsw").focus();
    				}
    				layer.close(index);
    			});
    		}
    	},
	    error: function(data, textstatus){
	    	layer.close(index);
	    	layer.alert(data.responseText);
	    }
    });
}

$(document).ready(function(){
	$("#loginPsw").keypress(function(event){
		if(event.which == 13){
			loginsubmit();
		}
	});
	$("#loginCode").keypress(function(event){
		if(event.which == 13){
			$("#loginPsw").focus();
		}
	});
	$("#loginCode").focus();
});
</script>
</head>
<body class="">
	<div class="container">
		<div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3">
			<div class="panel">
				<div class="panel-heading text-center">
					<img src="common/images/logo/logo.png" />
				</div>
				<form id="LoginForm" name="LoginForm" method="post" action="userLogin.action" class="form-horizontal" style="padding-top:20px;">
					<div class="form-group">
						<label class="col-xs-4 control-label text-right" style="">用户名</label>
						<div class="col-xs-6 has-feedback">
							<!-- 当前机构编码 -->
							<input id="insId" name="insId" type="hidden" value="${insId}" />
							<input id="loginCode" name="loginCode" class="form-control input-lg" type="text" maxlength="20" placeholder="请输入帐号" />
							<span class="fa fa-user fa-fw form-control-feedback"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label text-right">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
						<div class="col-xs-6 has-feedback">
							<input id="loginPsw" name="loginPsw" class="form-control input-lg" type="password" maxlength="20" placeholder="请输入密码" />
							<span class="fa fa-lock form-control-feedback"></span>
						</div>
					</div>
					<div class="form-group" style="padding:10px 25px 20px 35px;">
						<button class="btn btn-primary btn-lg col-xs-offset-2 col-xs-8" type="button" onclick="return loginsubmit();">登 录</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>