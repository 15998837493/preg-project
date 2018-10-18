<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>营养病历</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
$().ready(function () {
	layer.loading();
	ajax.getHtml(URL.get("Platform.DIAGNOSIS_SUMMARY_PAGE") + "?custId="+$("#custId").val()+"&diagnosisId="+$.getUrlVars()["diagnosisId"], "", function(data){
		$("#tool_diagnosis_summary_div").html(data);
	}, false, false);
	layer.close(layer.index);
});
</script>
</head>
<body>
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-body form-horizontal">
					<div id="tool_diagnosis_summary_div"></div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>
