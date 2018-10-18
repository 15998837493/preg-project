<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>营养病历小结--${custName}</title>
<%@ include file="/common/common.jsp" %>
<style type="text/css">
.mytitle-left {
	background-color: #84ABE1;height: 34px;width:2%;padding: 7px;border:1px solid;border-color: #337AB7
}
.mytitle-right {
	background-color: #F5F5F5;height: 34px;width:98%;padding: 7px;border:1px solid;border-color: #84ABE1
}
</style>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/common/plugins/laytpl/laytpl.js"></script>
<script type="text/javascript">
// 营养病历数据
var summaryMap = ${resultMap};
</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/evaluate/tool/tool_disgnosis_summary.js"></script>
</head>
<body>

<div class="row" style="margin-bottom: 10px;">
	<div class="col-xs-8 col-xs-offset-2 text-center">
		<h3 id="summaryTitle"></h3>
	</div>
	<div class="col-xs-2 text-right" style="margin-top: 18px;">
		<div class="button-group" style="border:1px solid;border-color: #337AB7;">
			<button type="button" class="button button-default button-small button-rounded button-raised" onclick="showItemModal();">
				<i class="fa fa-print fa-fw"></i> 打印
			</button>
		</div>
	</div>
</div>

<!-- 患者信息 -->
<div class="row" style="margin: 10px 0 10px 0;">
	<div class="col-xs-1 mytitle-left" > </div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i>  个人信息</div>
</div>
<div class="panel panel-lightblue" style="margin-bottom: 10px;">
    <div class="table-responsive">
        <table class="table table-bordered" id="customerInfo">
            <tr>
                <td class='text-center active' style="width:6%;">病案号</td>
                <td style="width:10%;" id="custSikeId"></td>
                <td class='text-center active' style="width:6%;">ID</td>
                <td style="width:8%;" id="custPatientId"></td>
                <td class='text-center active' style="width:6%;">姓名</td>
                <td id="custName" style="width:8%;"></td>
                <td class='text-center active' style="width:6%;">年龄</td>
                <td id="custAge" style="width:8%;"></td>
                <td class='text-center active' style="width:6%;">末次月经</td>
                <td id="LastYJ" style="width:8%;"></td>
                <td class='text-center active' style="width:6%;">孕周数</td>
                <td id="diagnosisGestationalWeeks" style="width:8%;"></td>
                <td class='text-center active' style="width:7%;">胎数</td>
				<td id="embryoNum"></td>
            </tr>
		</table>
    </div>
</div>

<div class="panel panel-lightblue">
    <div class="table-responsive">
        <table class="table table-bordered table-padding-4" id="diagnosisInfo">
            <tr>
                <td class='active text-center' style="width: 12%; min-width: 130px;">诊断</td>
                <td><span id="disease"></span></td>
            </tr>        
            <tr>
                <td class='active text-center' style="width: 12%; min-width: 130px;">营养主诉</td>
                <td><span id="diagnosisMain"></span></td>
            </tr>
            <tr>
                <td class='active text-center' style="width: 12%; min-width: 130px;">一般检查</td>
                <td><span id="general-inspect"></span></td>
            </tr>            
            <tr>
                <td class='active text-center'>本次营养评价结论</td>
                <td style="padding: 2px;" id="diagnosisInspectResult">
                </td>
            </tr>
        </table>
    </div>
</div>

<!-- 辅助检查异常结果 -->
<div id="summary_abnormal_div" style="display: none;">
	<div class="row" style="margin: 10px 0 10px 0;">
		<div class="col-xs-1 mytitle-left"> </div>
		<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i>辅助检查异常结果</div>
	</div>
</div>

<!-- 营养处方 -->
<div id="summary_prescription_div" style="display: none;">
<div class="row" style="margin: 10px 0 10px 0;">
	<div class="col-xs-1 mytitle-left"> </div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i> 营养处方</div>
</div>
<div class="panel panel-lightblue">
    <div class="table-responsive">
        <table class="table table-bordered table-padding-4">
        	<thead>
        	    <tr class='active'>
	                <td class="text-center" style="width: 5%;">序号</td>
	                <td class="text-center" style="width: 35%;">名称</td>
	                <td class="text-center" style="width: 10%;">单次剂量</td>
	                <td class="text-center" style="width: 10%;">剂量说明</td>
	                <td class="text-center" style="width: 10%;">用法</td>
	                <td class="text-center" style="width: 10%;">频次</td>
	                <td class="text-center" style="width: 10%;">疗程</td>
	                <td class="text-center" style="width: 10%;">状态</td>
	            </tr>
        	</thead>
            <tbody id="extenderList"></tbody>
            <tfoot id="extenderListTfoot" style="display:none;">
	            <tr>
	            	<td colspan="10" style="padding: 2px;" id="diagnosisExtenderDesc"></td>
	            </tr>
            </tfoot>
        </table>
    </div>
</div>
</div>

<!-- 膳食处方 -->
<div id="summary_diet_div" style="display: none;">
<div class="row" style="margin: 10px 0 10px 0;">
	<div class="col-xs-1 mytitle-left"> </div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i> 膳食处方</div>
</div>
<div class="panel panel-lightblue">
    <div class="table-responsive">
        <table class="table table-bordered">
        	<tbody id="summary_diet_div_tbody">
	            <tr>
	                <td class="text-center active" style="width: 12%; min-width: 130px;">能量</td>
	                <td class="text-left" id="intakeCaloric"></td>
	                <td class="text-center active" style="width: 12%;">CPF</td>
	                <td class="text-left" id="cpf"></td>
	                <td class="text-center active" style="width: 12%;">食谱名称</td>
	                <td class="text-left" id="dietName"></td>
	            </tr>
            </tbody>
            <tfoot id="dietRemarkFoot" style="display:none;">
	            <tr>
	            	<td colspan="10" style="padding: 2px;" id="diagnosisDietRemark">	            		
	            	</td>
	            </tr>
            </tfoot>
        </table>
    </div>
</div>
</div>

<!-- 复查与复诊预约  20170930新改 -->
<div id="summary_booking_div" style="display: none;">
<div class="row" style="margin: 10px 0 10px 0;">
	<div class="col-xs-1 mytitle-left"> </div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i> 复查与复诊预约</div>
</div>
<div class="panel panel-lightblue">
    <div class="table-responsive">
        <table class="table table-bordered">
        	<tr>
                <td class="text-center active" style="width: 12%; min-width: 130px;">复查检测</td>
                <td class="text-left" id="booking_inspect"></td>
            </tr>
        </table>
    </div>
</div>
</div>

<!-- 课程预约 -->
<div id="course_booding_div" style="display: none;">
<div class="row" style="margin: 10px 0 10px 0;">
	<div class="col-xs-1 mytitle-left"> </div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i> 课程预约</div>
</div>
<div class="panel panel-lightblue">
    <div class="table-responsive">
       	<table class="table table-bordered table-padding-4">
			<thead>
				<tr class="active">
					<th class='text-center'>课程日期</th>
					<th class='text-center'>课程时间</th>
					<th class='text-center'>课程内容</th>
			   	</tr>
			</thead>
			<tbody id="course_booking_tbody_summary">
			</tbody>
		</table>
    </div>
</div>
</div>

<!-- 妊娠日记-->
<c:if test="${not empty previewList }">
<div id="diary_div">
<div class="row" style="margin: 10px 0 10px 0;">
	<div class="col-xs-1 mytitle-left"> </div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i> <span>妊娠日记</span></div>
</div>
<div class="panel panel-lightblue">
    <div class="panel-body" style="padding: 15px 15px 0 15px;">
    	<c:forEach items="${previewList }" var="list">
	    	<div class="form-group"><div class="col-xs-12">模板：<a onclick="showDiaryPdf('${list.printId}');"> ${list.printItem }</a></div></div>
    	</c:forEach>
    </div>
</div>
</div>
</c:if>

<!-- 营养病历--打印营养病历报告 -->
<div id="itemModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-print"></i> 选择营养病历内容
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body form-horizontal">
						<c:forEach items="${printListMap }" var="map">
							<c:if test="${not empty map.value }">
								<div class="form-group">
									<div class="col-xs-12">
										<span class="label-title"><i class="fa fa-tag fa-fw"></i> ${map.key }</span>
									</div>
									<div class="col-xs-12"><HR style="margin: 5px 0 0 0;"></div>
									<div class="col-xs-11 col-xs-offset-1">
										<c:forEach items="${map.value }" var="list">
											<div class="col-xs-4 no-left">
												<label class="checkbox-inline">
													<input type="checkbox" name="printIdList" value="${list.printId }">
													<span name="dietFont">${list.printItem }</span>
												</label>
											</div>
										</c:forEach>
									</div>
								</div>
							</c:if>
						</c:forEach>
						<div class="form-group">
							<div class="col-xs-12" style="color: red;">注：未创建状态，对应的内容不能打印。</div>
						</div>
					</div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="printPlanPDF();" style="margin-left:145px;">确认</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>