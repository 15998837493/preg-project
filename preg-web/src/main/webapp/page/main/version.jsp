<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>系统版本信息</title>
<%@ include file="/common/common.jsp" %>
<link rel="stylesheet" type="text/css" href="../common/css/style.css">

<style>
p{margin:0; padding:0; line-height:24px;}
.color-orange{ color:#ff8748;}
.color-blue{ color:#0085d0;}
.error_warning { background:#fff; width:80%; overflow:hidden; border:1px solid #0085d0; -moz-border-radius:10px; -webkit-border-radius:10px; border-radius:10px; margin:80px auto 0; -moz-box-shadow:2px 5px 6px #666; -webkit-box-shadow:2px 5px 6px #666; box-shadow:2px 5px 6px #666;}
.error_warning p span{ display:block;}
.error_warning .matter{ background:#0085d0; color:#fff; font-size:14px; line-height:24px; padding:20px 0; margin:0;}
.error_warning h2,h1{ margin:0; padding:0;}
</style>
<script>
layer.close(layer.index);
window.onload = function () {
	var nowDate = new Date();//取得当前的日期
	document.getElementById("copyright").innerHTML = "© 2014-"+ nowDate.getFullYear() + " &nbsp;&nbsp;&nbsp;北京麦芽健康管理有限公司";//update为自动更新的年份
}
</script>
</head>
<body>
<div style="text-align:center;margin-top:100px;">
    <table cellspacing="0" cellpadding="0" class="error_warning" style="border: 0 solid;margin-bottom: 100px;">
		<tr>
			<td class="matter" style="text-align: left; padding: 50px 40px 20px 38px;">
				<div style="font-size: 35px;display: inline-block;">孕期营养门诊诊疗系统 </div>
				<div style="font-size: 20px;display: inline-block;"> &nbsp;&nbsp;（${common.systemVersion }）</div>
			</td>
		</tr>
		<tr>
			<td valign="top">
				<div class="col-xs-12 text-left" style="padding: 30px 40px 30px 40px;color:gray;font-size: 17px;">
					本产品版权归北京麦芽健康管理有限公司所有。
				</div>
				<div class="col-xs-12 text-left" style="padding: 0 40px 30px 40px;color:gray;font-size: 17px;">
					警告：此软件程序受版权法和国际条约的保护。未经授权擅自复制和分发此程序的一部分或全部，可能导致严厉的民事或刑事制裁，并将在法律许可的范围内受到最大程度的起诉。
				</div>
				<div class="col-xs-12 text-right" style="padding: 20px 40px 50px 40px;color:gray;font-size: 15px;" id="copyright"></div>
			</td>
		</tr>
	</table>
</div>
</body>
</html>
