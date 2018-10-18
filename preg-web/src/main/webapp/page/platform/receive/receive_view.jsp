<%@ page language="java" pageEncoding="UTF-8"%>
<style>
.margin-zero{
	margin: 0px;
}
.echarts_close {
font-size: 21px;
font-weight: bold;
line-height: 1;
color: #000;
text-shadow: 0 1px 0 #fff;
filter: alpha(opacity=20);
opacity: .2;
}
.echarts_close:hover,
.echarts_close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
  filter: alpha(opacity=50);
  opacity: .5;
}
.intake-input{
	width: 55px;
	height: 20px;
	font-size: 14px;
	line-height: 1.42857143;
	background-color: #fff;
	background-image: none;
	border: 1px solid #ccc;
	border-radius: 4px;
	border-top-right-radius: 0px;
	border-bottom-right-radius: 0px;
	margin: 0 0 0 3px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
	box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
	transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
}
.my-input-group-addon {
    padding: 5px 6px;
    font-size: 14px;
    font-weight: normal;
    line-height: 1;
    color: #555;
    text-align: center;
    margin-right: 2px;
    border: 1px solid #ccc;
    border-left-width: 0px;
    border-left-style: solid;
    border-left-color: rgb(204, 204, 204);
	border-radius: 4px;
    border-top-left-radius: 0px;
    border-bottom-left-radius: 0px;
}
.div-inline {
	width: 210px;
}
#x-line {
	width:422px;
}
#y-line{
	width: 1px; 
	height: 72px; 
	background: #ddd; 
	margin: -38px 0 -34px 212px;
}
#weight {
	height:530px;
}
.top_result_out_div {
	width: 320px;
}
/*  高度变化，舒张压被挤到下一行 */
@media screen and (max-width:1642px){
	#diastolicMarginTop {
		margin-top:4px;
	}
	#weight {
		height:570px;
	}
}

</style>
<script type="text/javascript">
// 科室来源
var org="${diagnosis.diagnosisOrg }";
// 转诊医生姓名
var doctorName="${diagnosis.diagnosisReferralDoctorName }";
// 转诊医生信息
var diagnosisReferralDoctor="${diagnosis.diagnosisReferralDoctor }";
// 接诊ID
var diagnosisId="${diagnosis.diagnosisId }";
// 末次月经
var d_lmp = "${preArchive.lmp }";
// 预产期
var d_due = "${preArchive.pregnancyDueDate }";
// 胎儿发育情况
var obstetricalBabyJson = "${obstetrical.obstetricalBaby }";
// 日期
var obstetricalTopDateJson = "${obstetrical.obstetricalTopDate }";
var obstetricalBottomDateJson = "${obstetrical.obstetricalBottomDate }";
//现体重
var diagnosisCustWeight = "${diagnosis.diagnosisCustWeight }";
//妊娠风险级别
var obstetricalGestationLevelJson = "${diagnosis.gestationLevel }";
</script>
<script charset="UTF-8" src="${common.basepath}/page/platform/receive/receive_view.js"></script>
<div id="resultDiv" class="panel panel-lightblue col-xs-7 no-left padding-zero" style="border-right: 0px;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 接诊信息 &nbsp;&nbsp;&nbsp;
		<span id="diagnonsisInfo"></span>
		<button type="button" class="btn btn-sm btn-default" style="float: right;padding: 1px 10px;" id="editDiagnosisInfo" onclick="updateDiagnosisInfo();">
			编辑
		</button>
	</div>
	<div class="table-responsive" id="saveObstetrical_info_div">
		<form id="diagnosisObstetricalUpdateForm" action="${common.basepath }/${applicationScope.URL.Platform.DIAGNOSIS_OBSTETRICAL_UPDATE}" method="post">
			<input type="hidden" name="diagnosisId" value="${diagnosis.diagnosisId }" />
			<table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
				<tr>
					<td class="active" style="width: 140px;">主诉</td>
					<td><textarea rows="5" id="obstetricalDiagnosisMain" name="obstetricalDiagnosisMain" class="form-control" placeholder="点击输入主诉内容" maxlength="1000">${obstetrical.obstetricalDiagnosisMain }</textarea>
					</td>
				</tr>
				<tr>
					<td class="active" rowspan="3">胎儿发育状态</td>
					<td>
						<div>
							<div class="div-inline">
								<div class="input-group">
									<input id="obstetricalTopDate" name="obstetricalTopDate" type="text" class="form-control" placeholder="请选择日期" readonly />
									<span class="input-group-btn">
										<button class="btn btn-primary" type="button" onclick="common.dateShow('obstetricalTopDate')">
											<i class="fa fa-calendar fa-fw"></i>选择
										</button>
									</span>
								</div>
							</div>
							<div class="div-inline" style="width:150px;">
								<select id="obstetricalBaby" name="obstetricalBaby" class="form-control">
									<option value="">请选择状态</option>
									<option value="正常胎龄儿">正常胎龄儿</option>
									<option value="大于胎龄儿">大于胎龄儿</option>
									<option value="小于胎龄儿">小于胎龄儿</option>
								</select>
							</div>
							<div class="div-inline" style="width:160px;">
								<span id="preg_top_week">${obstetrical.obstetricalTopGestationalweeks }</span>
								<input type="hidden" value='${obstetrical.obstetricalTopGestationalweeks }' id="obstetricalTopGestationalweeks" name="obstetricalTopGestationalweeks" />
							</div>
						</div>
						<div style="margin-top: 4px;">
							<div class="div-inline">
								<div class="input-group">
									<div class="input-group-addon">宫高</div>
									<input id="obstetricalFundalHeight" name="obstetricalFundalHeight" value="${obstetrical.obstetricalFundalHeight }" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入宫高" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
							<div class="div-inline top_result_out_div">
								<span id="fundal_standard">${obstetrical.obstetricalFundalHeightResult }</span>
								<input type="hidden" id="obstetricalFundalHeightResult" name="obstetricalFundalHeightResult" value="${obstetrical.obstetricalFundalHeightResult }"/>
							</div>
						</div>
						<div style="margin-top: 4px;">
							<div class="div-inline">
								<div class="input-group">
									<div class="input-group-addon">腹围</div>
									<input id="obstetricalAbdominalPerimeter" value="${obstetrical.obstetricalAbdominalPerimeter }" name="obstetricalAbdominalPerimeter" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入腹围" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
							<div class="div-inline top_result_out_div">
								<span id="abdominal_standard">${obstetrical.obstetricalAbdominalPerimeterResult }</span>
								<input type="hidden" id="obstetricalAbdominalPerimeterResult" name="obstetricalAbdominalPerimeterResult" value="${obstetrical.obstetricalAbdominalPerimeterResult }"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div>
							<div class="div-inline">
								<div class="input-group">
									<input id="obstetricalBottomDate" name="obstetricalBottomDate" type="text" class="form-control" placeholder="请选择日期" readonly />
									<span class="input-group-btn">
										<button class="btn btn-primary" type="button" onclick="common.dateShow('obstetricalBottomDate')">
											<i class="fa fa-calendar fa-fw"></i>选择
										</button>
									</span>
								</div>
							</div>
							<div class="div-inline">
								<span id="preg_bottom_week">${obstetrical.obstetricalBottomGestationalweeks }</span>
								<input type="hidden" value='${obstetrical.obstetricalBottomGestationalweeks }' id="obstetricalBottomGestationalweeks" name="obstetricalBottomGestationalweeks" />
							</div>
						</div>
						<div style="margin-top: 4px;">
							<div class="div-inline" style="width:380px;">
								<div class="input-group">
									<div class="input-group-addon">胎儿体重10百分位</div>
									<input id="obstetricalBabyWeight" value="${obstetrical.obstetricalBabyWeight }" name="obstetricalBabyWeight" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入百分位" />
									<div class="input-group-addon" style="width: 47px;">g</div>
								</div>
							</div>
							<div class="div-inline" style="width: 118px;">
								<span id="foetus_weight">${obstetrical.obstetricalBabyWeightResult }</span>
								<input type="hidden" id="obstetricalBabyWeightResult" name="obstetricalBabyWeightResult" value="${obstetrical.obstetricalBabyWeightResult }"/>
							</div>
							</div>
							<div style="margin-top: 4px;">
							<div class="div-inline" style="width:380px;">
								<div class="input-group">
									<div class="input-group-addon">胎儿股骨长</div>
									<input id="obstetricalBabyFemur" name="obstetricalBabyFemur" value="${obstetrical.obstetricalBabyFemur }" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入股骨长" />
									<div class="input-group-addon">mm</div>
								</div>
							</div>
							<div class="div-inline" style="width:150px;">
								<span id="foetus_femur_length">${obstetrical.obstetricalBabyFemurResult }</span>
								<input type="hidden" id="obstetricalBabyFemurResult" name="obstetricalBabyFemurResult" value="${obstetrical.obstetricalBabyFemurResult }"/>
							</div>
						</div>
						<div style="margin-top: 4px;">
							<div class="div-inline" style="width:380px;">
								<div class="input-group">
									<div class="input-group-addon">胎儿双顶径</div>
									<input id="obstetricalBabyBdp" name="obstetricalBabyBdp" value="${obstetrical.obstetricalBabyBdp }" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入双顶径" />
									<div class="input-group-addon">mm</div>
								</div>
							</div>
							<div class="div-inline" style="width: 118px;">
								<span id="foetus_biparietal_diameter">${obstetrical.obstetricalBabyBdpResult }</span>
								<input type="hidden" id="obstetricalBabyBdpResult" name="obstetricalBabyBdpResult" value="${obstetrical.obstetricalBabyBdpResult }"/>
							</div>
						</div>
						<div style="margin-top: 4px;">	
							<div class="div-inline" style="width:380px;">
								<div class="input-group">
									<div class="input-group-addon">胎儿腹围</div>
									<input id="obstetricalBabyAbdominalPerimeter" value="${obstetrical.obstetricalBabyAbdominalPerimeter }" name="obstetricalBabyAbdominalPerimeter" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入胎儿腹围" />
									<div class="input-group-addon">mm</div>
								</div>
							</div>
							<div class="div-inline" style="width:150px;">
								<span id="foetus_fetal_abdominal">${obstetrical.obstetricalBabyAbdominalPerimeterResult }</span>
								<input type="hidden" id="obstetricalBabyAbdominalPerimeterResult" name="obstetricalBabyAbdominalPerimeterResult" value="${obstetrical.obstetricalBabyAbdominalPerimeterResult }"/>
							</div>
						</div>	
					</td>
				</tr>
				<tr>
					<td>
						<div>
							<div class="div-inline">
								<div class="input-group">
									<div class="input-group-addon">羊水指数</div>
									<input id="obstetricalAmnioticFluid" name="obstetricalAmnioticFluid" class="form-control text-right" type="text" readonly="readonly" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
						</div>
						<div style="margin-top: 4px;">
							<div class="div-inline">
								<div class="input-group">
									<input id="obstetricalAmnioticFluidOne" name="obstetricalAmnioticFluidOne" class="form-control text-right" onkeyup="checkNum(this,'AmnioticFluid');" type="text" maxlength="6" value="${obstetrical.obstetricalAmnioticFluidOne }" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
							<div class="div-inline">
								<div class="input-group">
									<input id="obstetricalAmnioticFluidTwo" name="obstetricalAmnioticFluidTwo" class="form-control text-right" onkeyup="checkNum(this,'AmnioticFluid');" type="text" maxlength="6" value="${obstetrical.obstetricalAmnioticFluidTwo }" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
						</div>
						<hr id="x-line" style="margin: 2px 0 2px 1px;">
						<div id="y-line"></div>
						<div>
							<div class="div-inline">
								<div class="input-group">
									<input id="obstetricalAmnioticFluidThree" name="obstetricalAmnioticFluidThree" class="form-control text-right" onkeyup="checkNum(this,'AmnioticFluid');" type="text" maxlength="6" value="${obstetrical.obstetricalAmnioticFluidThree }" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
							<div class="div-inline">
								<div class="input-group">
									<input id="obstetricalAmnioticFluidFour" name="obstetricalAmnioticFluidFour" class="form-control text-right" onkeyup="checkNum(this,'AmnioticFluid');" type="text" maxlength="6" value="${obstetrical.obstetricalAmnioticFluidFour }" />
									<div class="input-group-addon">cm</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="active">一般检查</td>
					<td>
						<div class="div-inline" style="width:250px;">
							<div class="input-group">
								<div class="input-group-addon">现体重</div>
								<input id="weight_input_text" name="obstetricalDiagnosisWeight" onblur="showWeightCheck(this);" class="form-control text-right" onkeyup="checkNum(this);" type="text" value="${diagnosis.diagnosisCustWeight }" maxlength="5"/>
								<div class="input-group-addon">kg</div>
							</div>
						</div>
						<div class="div-inline" style="width:250px;">
							<div class="input-group">
								<div class="input-group-addon"><a onclick="showSystolic();">收缩压</a></div>
								<input id="obstetricalDiagnosisSystolic" name="obstetricalDiagnosisSystolic" class="form-control text-right" onkeyup="checkNum(this);" type="text" value="${obstetrical.obstetricalDiagnosisSystolic }" maxlength="3"/>
								<div class="input-group-addon">mmHg</div>
							</div>
						</div>
						<div class="div-inline" style="width:250px;">
							<div class="input-group" id="diastolicMarginTop">
								<div class="input-group-addon"><a onclick="showDiastolic();">舒张压</a></div>
								<input id="obstetricalDiagnosisDiastolic" name="obstetricalDiagnosisDiastolic" class="form-control text-right" onkeyup="checkNum(this);" type="text" value="${obstetrical.obstetricalDiagnosisDiastolic }" maxlength="3"/>
								<div class="input-group-addon">mmHg</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="active">妊娠风险级别</td>
					<td>
						<div class="div-inline">
							<select id="obstetricalGestationLevel" name="obstetricalGestationLevel" class="form-control"></select>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<!-- 体重曲线图部分 -->
<div id="weightEcharDiv" class="panel panel-lightblue col-xs-5 no-left padding-zero margin-zero">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 体重曲线
	</div>
	<div id="weight"></div>
	<span style="position: absolute;left:35px;bottom: 90px;">该孕妇当前整体体重增长：<span id="rise_all_present"></span></span>
	<span style="position: absolute;left:35px;bottom: 63px;">建议整体孕期体重适宜增长范围：<input style="height:28px;" class="intake-input" name='rise_yunqi' id='rise_yunqi_one' type="text"/><span class="my-input-group-addon" style="height:28px;">公斤</span>~<input style="height:28px;" class="intake-input" name='rise_yunqi' id='rise_yunqi_two' type="text"/><span class="my-input-group-addon">公斤</span></span><br/>
	<span style="position: absolute;left:35px;bottom: 33px;">建议目前体重增长至：<input style="height:28px;" class="intake-input" name='rise_present' id='rise_present_one' type="text"/><span class="my-input-group-addon">公斤</span>~<input style="height:28px;" class="intake-input" name='rise_present' id='rise_present_two' type="text"/><span class="my-input-group-addon">公斤</span></span><br/>
	<span style="position: absolute;left:35px;bottom: 2px;">建议每周体重适宜增长范围：<input style="height:28px;" class="intake-input" name='rise_week' id='rise_week_one' type="text"/><span class="my-input-group-addon">公斤</span>~<input style="height:28px;" class="intake-input" name='rise_week' id='rise_week_two' type="text"/><span class="my-input-group-addon">公斤</span></span>
</div>

<!-- 收缩压曲线图弹出窗 -->
<form id="systolicShowForm" name="systolicShowForm" action="" class="form-horizontal" method="post">
<div id="systolicShowModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg" style="margin-top: 150px;">
		<div class="modal-content">
			<div class="modal-body">
				<a style="margin-left:840px;" class="echarts_close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
 				<div id="systolic" style="width: 900px;height:400px;"></div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<!-- 舒张压曲线图弹出窗 -->
<form id="diastolicShowForm" name="diastolicShowForm" action="" class="form-horizontal" method="post">
<div id="diastolicShowModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg" style="margin-top: 150px;">
		<div class="modal-content">
			<div class="modal-body" style="overflow:hidden">
			<a style="margin-left:840px;" class="echarts_close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
 				<div id="diastolic" style="width: 900px;height:400px;"></div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
<!-- 编辑接诊信息modal -->
<form id="diagnosisInfoForm" name="diagnosisInfoForm" action="${common.basepath}/${applicationScope.URL.Platform.DISEASE_INFO_UPDATE}"class="form-horizontal" method="post">
	<div id="diagnosisInfoModal" class="modal fade bs-example-modal">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-search"></i>编辑患者接诊信息
							<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
						</div>
						<div class="row">
		                    <div class="col-xs-9 col-xs-offset-1 row-top">
		                        <div class="form-group">
									<label class="col-xs-3 control-label">转诊医生</label>
									<div class="col-xs-9">
										<div class="col-xs-9">
											<input type="hidden"  name="diagnosisId"/>
											<input type="hidden"  name="diagnosisOrg"/>
											<input type="hidden"  name="diagnosisReferralDoctorName"/>
											<select id="diagnosisReferralDoctor" name="diagnosisReferralDoctor" class="form-control"></select>
										</div>
										<div class="col-xs-3 padding-zero text-muted" id="diagnosisReferralDept" ></div>
									</div>
		                        </div>  
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right">
							<button type="submit" class="btn btn-primary btn-block">确认</button>
						</div>
					</div>	
				</div>		
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>