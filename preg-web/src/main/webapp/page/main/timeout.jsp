<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>超时页面</title>
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" type="text/css" href="${common.basepath}/common/css/style.css">

<style>
p{margin:0; padding:0; line-height:24px;}
.color-orange{ color:#ff8748;}
.color-blue{ color:#0085d0;}
.error_warning { background:#fff; width:80%; overflow:hidden; border:1px solid #0085d0; -moz-border-radius:10px; -webkit-border-radius:10px; border-radius:10px; margin:80px auto 0; -moz-box-shadow:2px 5px 6px #666; -webkit-box-shadow:2px 5px 6px #666; box-shadow:2px 5px 6px #666;}
.error_warning p span{ display:inline-block;}
.error_warning .matter{ background:#0085d0; color:#fff; font-size:14px; line-height:24px; padding:20px 0; margin:0;}
.error_warning h2,h1{ margin:0; padding:0;}
</style>
<script type="text/javascript">    
function countDown(secs,surl){    
	var jumpTo = document.getElementById('jumpTo');
	jumpTo.innerHTML=secs;
	if(--secs>0){
	    setTimeout("countDown("+secs+",'"+surl+"')",1000);
	}else{
	    parent.location.href=surl;
	}
}    
</script>
</head>
<body>

<table cellspacing="0" cellpadding="0" class="error_warning">
	<tr>
		<td style="width:30%; padding:50px 0 0; text-align:right;" valign="top"><img src="${common.basepath}/common/images/error.jpg"></td>
		<td style="width:60%; padding:90px 0 0 20px;" valign="top">
			<div class="row">
				<div class="col-xs-offset-1">
					<h1>登录超时，请重新登录！</h1>
				</div>
				<div class="col-xs-offset-1 color-orange">
					<p>系统会在 <strong id="jumpTo">5</strong> 秒后自动跳转</p>
				</div>
			</div>
		</td>
		<td style="width:10%;"></td>
	</tr>
	<tr>
		<td class="matter" ></td>
		<td class="matter" colspan="2">
			<p>注意事项：</p>
			<p>1、请不要发送任何带有敏感信息的短信；</p>
			<p>2、本系统仅限于企业内部正常办公用途；</p>
			<p>3、请注意保管好您的账号密码，以免被恶意盗用；</p>
			<p>4、如果页面等待时间过长无反应，请按F5键进行刷新。</p>
		</td>
	</tr>
</table>

<script type="text/javascript">countDown(5,'${redirect_url}');</script>

</body>
</html>
