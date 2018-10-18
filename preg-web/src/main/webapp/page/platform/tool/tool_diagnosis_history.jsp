<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>历史营养病历--${custName}</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/platform/tool/tool_diagnosis_history.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	// 初始化datatables
	recordTable = datatable.table(recordTableOptions);
});
</script>
<body>
<!-- 营养病历--打印营养病历报告 -->
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-heading">
					<h4 class="timeline-title">就诊记录</h4>
				</div>
				<div class="timeline-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-folder-open"></i>
							客户病历
				   		</div>
				   		<form id="recordQuery" action="${common.basepath }/${applicationScope.URL.Platform.DIAGNOSIS_HISTORY_QUERY}" method="post" class="form-horizontal">
							<input type="hidden" name="diagnosisCustomer" value="${custId }"/>
							<input type="hidden" name="diagnosisStatus" value="2"/>
						</form>
						<div class="table-responsive">
							<table id="recordListTable" class="table table-bordered table-hover">
								<thead>
									<tr class="active">
										<th class='text-center'>序号</th>
										<th class='text-center'>日期</th>
										<th class='text-center'>孕周</th>
								     	<th class='text-center'>接诊医生</th>
								     	<th class='text-center' style="width: 45%">诊断</th>
								     	<th class='text-center'>病历小结</th>
								   </tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>