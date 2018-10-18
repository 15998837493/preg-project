<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>营养制剂处方模板</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
$().ready(function () {
	layer.loading();
	ajax.getHtml(URL.get("item.DISEASE_PRESCRIPTION_VIEW"), "", function(data){
		$("#platform_plan_prescription_div").html(data);
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
					<div id="platform_plan_prescription_div"></div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>
