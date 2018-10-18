<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>分娩结局录入</title>
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp" %>
</head>
<body>
<div class="container-fluid">

	<!-- 分娩结局记录列表 -->
	<div class="row">
		<ul id="timeline">
			<li class="timeline-inverted">
				<div class="timeline-panel" id="timeline-panel">
					<div class="timeline-body form-horizontal">
						<!-- 分娩结页面——个人历史信息 -->
						<%@ include file="/page/birthending/birthendingInfo/birthendingHistory.jsp"%>
					</div>
				</div>
			</li>
		</ul>
	</div>
	
	<!-- 分娩结局录入 -->
	<div class="row row-top" id="birthending" style="display: none">
		<ul id="timeline">
			<li class="timeline-inverted">
				<div class="timeline-panel" id="timeline-panel">
					<div class="timeline-body form-horizontal">
						<div class="row" style="margin-bottom: 10px;">
							<div class="col-xs-8 col-xs-offset-2 text-center">
								<h3>分娩结局录入</h3>
							</div>
						</div>

						<!-- 分娩结页面——基础信息+入院诊断 -->
						<%@ include file="/page/birthending/birthendingInfo/birthendingInfo.jsp"%>

						<!-- 分娩结页面——分娩信息 -->
						<%@ include file="/page/birthending/birthendingInfo/birthendingInfo_baseinfo.jsp"%>

						<!-- 分娩结页面——新生儿情况+出院诊断 -->
						<%@ include file="/page/birthending/birthendingInfo/birthendingInfo_babyinfo.jsp"%>
					</div>
				</div>
			</li>
		</ul>
	</div>
	
</div>
</body>
</html>
