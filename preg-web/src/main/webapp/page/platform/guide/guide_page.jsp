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
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/platform/guide/guide_page.js"></script>
<body id="guide-page-css">
<input type="hidden" id="diagnosisId" value="${diagnosis.diagnosisId }" />
<input type="hidden" id="diagnosisStatus" value="${diagnosis.diagnosisStatus }" />
<input type="hidden" id="diagnosisUserName" value="${diagnosis.diagnosisUserName }" />
<input type="hidden" id="diagnosisDate" value="${diagnosis.diagnosisDate }" />
<input type="hidden" id="custId" value="${diagnosis.diagnosisCustomer }" />

<div class="container-fluid">
	<!-- 顶部导航栏 -->
	<%@ include file="/page/platform/guide/guide_top.jsp"%>
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
										<a onclick="newlyDiagnosedMethod();"><i class="fa fa-edit"></i></a> 
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
									<td><a onclick="showAdjustByLmpModal();">${preArchive.lmp }</a></td>
									<td class='text-center active'>孕周数</td>
									<td>
										<a onclick="showAdjustByWeeksModal();"><span id="custlmp"></span>&nbsp; 周</a>
										<script>$("#custlmp").html(pregnancy.gestationalWeeksSupHtml("${diagnosis.diagnosisGestationalWeeks }"));</script>
									</td>
									<td class='text-center active'><a onclick="showPregAdjustRecordList();"><i class="fa fa-table"></i></a>&nbsp;&nbsp;预产期</td>
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
									<td colspan="10">${lastDiagnosisDisease }</td>
								</tr>
							</table>
						</div>
						<!-- 主页面内容 -->
						<div id="platform_content_div"></div>
						
						<!-- 根据孕周调整 -->
						<div id="adjustByWeeksModal" class="modal fade bs-example-modal">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-body">
										<div class="panel panel-lightblue">
											<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 调整孕周数<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
											<div class="row">
												<div class="col-xs-offset-3 col-xs-6 row-top">
													<div class="form-group">
										                <div class='col-xs-6'>
										                	<div class="input-group">
											            		<input id="ajdustWeeks" class="form-control" type="text" maxlength="2" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
											            		<div class="input-group-addon">周</div>
											            	</div>
														</div>
										                <div class='col-xs-6'>
										                	<div class="input-group">
											            		<input id="ajdustDays" class="form-control" type="text" maxlength="1" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
											            		<div class="input-group-addon">天</div>
											            	</div>
														</div>
									                </div>
													<div class="form-group">
										                <div class='col-xs-12'>
										            		<input id="ajdustWeeksReason" class="form-control" type="text" maxlength="1000" placeholder="请输入调整原因"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="panel-body text-right" style="padding: 0px;">
											<div class="col-xs-2 col-xs-offset-10 no-right" >
												<button type="button" class="btn btn-primary btn-block" onclick="saveAdjustByWeek();">保存</button>
											</div>
										</div>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal-dialog -->
						</div><!-- /.modal -->
						
						<!-- 根据末次月经调整 -->
						<div id="adjustByLmpModal" class="modal fade bs-example-modal">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-body">
										<div class="panel panel-lightblue">
											<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 调整末次月经<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
											<div class="row">
												<div class="col-xs-offset-1 col-xs-10 row-top">
													<div class="form-group">
										                <label class="col-xs-3 control-label">末次月经</label>
										                <div class='col-xs-7'>
										            		<div class="input-group">
														      	<input id="adjustLmp" name="adjustLmp" type="text" class="form-control" readonly/>
														      	<span class="input-group-btn">
														        	<button class="btn btn-primary" type="button" onclick="common.dateShow('adjustLmp')"><i class="fa fa-calendar fa-fw"></i>选择</button>
														      	</span>
											    			</div>
										            	</div>
									                </div>
													<div class="form-group">
										                <label class="col-xs-3 control-label">调整原因</label>
										                <div class='col-xs-7'>
										            		<input id="ajdustLmpReason" class="form-control" type="text" maxlength="1000" placeholder="请输入调整原因"/>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="panel-body text-right" style="padding: 0px;">
											<div class="col-xs-2 col-xs-offset-10 no-right" >
												<button type="button" class="btn btn-primary btn-block" onclick="saveAdjustByLmp();">保存</button>
											</div>
										</div>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal-dialog -->
						</div><!-- /.modal -->
						
						<!-- 调整孕周数的表单 -->
						<form id="adjustRecirdForm" action="${common.basepath }/${applicationScope.URL.Platform.DIAGNOSIS_UPDATE_ARCHIVES}" method="post">
							<input type="hidden" name="diagnosisId" value="${diagnosis.diagnosisId }" />
							<input type="hidden" name="dueDateNew"/>
							<input type="hidden" name="adjustReason"/>
						</form>
						
						<!-- 调整记录表 -->
						<div id="adjustRecordModal" class="modal fade bs-example-modal">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-body">
										<div class="panel panel-lightblue ">
											<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i>调整记录 <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
											<form id="adjustQueryForm" action="${common.basepath }/${applicationScope.URL.Platform.ADJUST_QUERY_HISTORY}">
												<input type="hidden" name="archivesId" value="${preArchive.id }"/>
											</form>
											<div class="table-responsive">
												<table id="adjustRecordListTable" class="table table-bordered table-hover padding-zero margin-zero">
													<thead>
														<tr class="active">
															<th class="text-center" style="width: 12.5%">调整日期</th>
															<th class="text-center" style="width: 12.5%">原末次月经</th>
															<th class="text-center" style="width: 12.5%">原孕周</th>
															<th class="text-center" style="width: 12.5%">原预产期</th>
															<th class="text-center" style="width: 12.5%">调整末次月经</th>
															<th class="text-center" style="width: 12.5%">调整孕周</th>
															<th class="text-center" style="width: 12.5%">调整预产期</th>
															<th class="text-center" style="width: 12.5%">调整原因</th>
														</tr>
													</thead>
												</table>
											</div>
										</div>
									</div>
								</div><!-- /.modal-content -->
							</div><!-- /.modal-dialog -->
						</div><!-- /.modal -->
						
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<websocket userName="${loginUserId }" ws="yes"></websocket>
</body>
</html>