<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp"%>
<style>
.label {
    display: inline;
    padding: 7px;
    font-size: 100%;
    font-weight: bold;
    line-height: 19px;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: baseline;
    border-radius: .25em;
    margin-right: 2px;
}
.pointShape{
	cursor:pointer;
}
</style>
<script type="text/javascript">
var diagnosisJson = ${diagnosisJson};
var preArchiveJson = ${preArchiveJson};
var preArchiveLmp = preArchiveJson.lmp;
var recordJson = ${recordJson};
</script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/evaluate/guide/guide_page.js"></script>
<body id="guide-page-css">
<input type="hidden" id="diagnosisId" value="${diagnosis.diagnosisId }" />
<input type="hidden" id="diagnosisStatus" value="${diagnosis.diagnosisStatus }" />
<input type="hidden" id="diagnosisUserName" value="${diagnosis.diagnosisUserName }" />
<input type="hidden" id="diagnosisDate" value="${diagnosis.diagnosisDate }" />
<input type="hidden" id="custId" value="${diagnosis.diagnosisCustomer }" />

<div class="container-fluid">
	<!-- 顶部导航栏 -->
	<%@ include file="/page/evaluate/guide/guide_top.jsp"%>
	<!-- 个人信息 -->
	<div class="row">
		<ul id="timeline">
			<li class="timeline-inverted">
				<div class="timeline-panel" id="timeline-panel">
					<div class="timeline-body form-horizontal">
						<!-- 个人信息 -->
						<div class="panel panel-lightblue" id="guide_customerinfo_div">
							<div class="panel-heading text-left">
								<i class="fa fa-tag fa-fw"></i> 个人信息
							</div>
							<table class="table table-bordered">
								<tr>
									<td class='text-center active'>病案号</td>
									<td >${diagnosis.diagnosisCustSikeId }</td>
									<td class='text-center active'>ID</td>
									<td >${diagnosis.diagnosisCustPatientId }</td>
									<td class='text-center active'>姓名</td>
									<td >
										<a onclick="getPregArchive();">${diagnosis.diagnosisCustName }</a> &nbsp;&nbsp;
									</td>
									<td class='text-center active'>年龄</td>
									<td >${diagnosis.diagnosisCustAge } 岁</td>
									<td class='text-center active'>出生日期</td>
									<td>${customerInfo.custBirthday }</td>
									<td class='text-center active'>胎数</td>
									<td>${preArchive.embryoNum }</td>
								</tr>
								<tr>
									<td class='text-center active'>末次月经</td>
									<td>${preArchive.lmp }</td>
									<td class='text-center active'>孕周数</td>
									<td id="custlmp">
										<script>$("#custlmp").html(pregnancy.gestationalWeeksSupHtml(diagnosisJson.diagnosisGestationalWeeks) + " 周");</script>
									</td>
									<td class='text-center active'>预产期</td>
									<td id="custDueDate">${preArchive.pregnancyDueDate }</td>
									<td class='text-center active'>孕前体重</td>
									<td>${preArchive.weight } kg</td>
									<td class='text-center active'>身高</td>
									<td>${preArchive.height } cm</td>
									<td class='text-center active'>孕前BMI</td>
									<td>${preArchive.bmi }</td>
								</tr>
								<tr>
									<td class='text-center active'>诊断</td>
									<td colspan="10" id="lastDiagnosisDisease">${lastDiagnosisDisease }</td>
								</tr>
							</table>
						</div>
						<!-- 主页面内容 -->
						<div id="platform_content_div"></div>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<websocket userName="${loginUserId }" ws="yes"></websocket>
</body>
</html>