<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>复查预约--${custName}</title>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
var diagnosisList = ${diagnosisBookingList};// 预约一览列表
</script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/platform/plan/plan_booking.js"></script>
<body>
<!-- 复诊预约一览 -->
<div class="panel panel-lightblue">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 复诊预约一览</div>
	<div class="table-responsive">
		<table class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class='text-center' style="width: 13%">复诊日期</th>
					<!-- <th class='text-center' style="width: 13%">就诊时间</th> -->
					<th class='text-center'>复诊建议</th>
					<th class='text-center' style="width: 25%">操作</th>
				</tr>
			</thead>
			<tbody id="t_body">
				<tr id='noRecordTr'>
					<td colspan="10" class='text-center'><h4>没有找到记录！</h4></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<!-- 复诊预约 -->
<form id="planBookForm" action="${common.basepath}/${applicationScope.URL.Platform.DIAGNOSIS_ADD_BOOKDATE}" class="form-horizontal" method="post">
  <input type="hidden" id="fuzhendiagnosisId" name="diagnosisId" />
  <input type="hidden" id="fuzhenbookingId" name="bookingId" />
  <div class="panel panel-lightblue padding-zero">
    <div class="panel-heading text-left">
      <i class="fa fa-tag fa-fw"></i>复诊预约</div>
    <div class="table-responsive">
      <table class="table table-bordered" style="margin-bottom: 0px;">
        <tr>
          <td class="text-center active" style="width: 13%;">复诊日期</td>
          <td style="padding:2px;">
            <div class="input-group">
              <input type="hidden" id="diagnosisDateParam" name="bookingDate" />
              <input type="hidden" id="diagnosisVisitTime" name="bookingVisitTime" />
              <input id="diagnosisDateCreate" name="showDiagnosisDate" type="text" class="form-control" onclick="showData();" placeholder="请选择预约日期" readonly style="background-color: white;" />
              <input id="diagnosisDateCreateOld" name="diagnosisDateOld" type="hidden" />
              <span class="input-group-btn">
                <button class="btn btn-primary" type="button" onclick="showData();">
                  <i class="fa fa-calendar fa-fw"></i>选择</button>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <td class="text-center active">复诊建议体重</td>
          <td style="padding:2px;">
            <input id="diagnosisResetSuggest" name="bookingSuggest" class="form-control" type="text" readonly="readonly" /></td>
        </tr>
        <tr>
          <td class="text-center active">其他复诊建议</td>
          <td style="padding:2px;">
            <textarea id="diagnosisRemark" name="bookingRemark" class="form-control" maxlength="70"></textarea>
          </td>
        </tr>
      </table>
    </div>
    <div class="panel-body text-right">
      <button class="btn btn-primary" type="submit">
        <i class="fa fa-check"></i>提交</button>
      <button class="btn btn-primary" type="button" onclick="initAddDiagnosis();">
        <i class="fa fa-trash"></i>清空</button>
    </div>
  </div>
</form>
		
<!-- 模态框（Modal） -->
<form id="addForm" class="form-horizontal" method="post">
		<div class="modal fade bs-example-modal" id="addModal">
			<div class="modal-dialog modal-lg" style="margin-top: 80px;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue ">
							<div class="panel-heading text-left">
								<i class="fa fa-tag fa-fw"></i>复查预约
								<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
							</div>
			<div class="table-responsive" id="showBody">
				<table id="nutrientTable" class="table table-bordered table-hover">
					<thead>
						<tr class="active">
							<th class="text-center">选择</th>
							<th class="text-center">日期</th>
							<th class="text-center">就诊时间</th>
							<th class="text-center">预约状态</th>
						</tr>
					</thead>
					<tbody>			
					</tbody>
				</table>
			</div>													
		</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="button" id="book" class="btn btn-primary btn-block"><i class="fa fa-save fa-fw"></i>预约</button>
					</div>
				</div>						
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
</form>								
</body>
</html>