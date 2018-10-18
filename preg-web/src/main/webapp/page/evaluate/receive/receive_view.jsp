<%@ page language="java" pageEncoding="UTF-8"%>
<style>
.div-inline{
	width: 300px;
}
</style>
<script type="text/javascript">
// 科室来源
var org="${diagnosis.diagnosisOrg }";
// 转诊医生
var doctorName="${diagnosis.diagnosisReferralDoctorName }";
// 接诊ID
var diagnosisId="${diagnosis.diagnosisId }";
// 末次月经
var d_lmp = "${preArchive.lmp }";
// 预产期
var d_due = "${preArchive.pregnancyDueDate }";
// 接诊信息
var obstetricalBabyJson = "${obstetrical.obstetricalBaby }";
// 日期
var obstetricalTopDateJson = "${obstetrical.obstetricalTopDate }";
var obstetricalBottomDateJson = "${obstetrical.obstetricalBottomDate }";
//现体重
var diagnosisCustWeight = "${diagnosis.diagnosisCustWeight }";
//妊娠风险级别
var obstetricalGestationLevelJson = "${diagnosis.gestationLevel }";
</script>
<script charset="UTF-8" src="${common.basepath}/page/evaluate/receive/receive_view.js"></script>
<div class="panel panel-lightblue col-xs-12 no-left padding-zero">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 接诊信息 &nbsp;&nbsp;&nbsp; <span id="diagnonsisInfo"></span>
	</div>
	<div class="table-responsive" id="saveObstetrical_info_div">
		<form id="diagnosisObstetricalUpdateForm" action="${common.basepath }/${applicationScope.URL.Platform.DIAGNOSIS_OBSTETRICAL_UPDATE}" method="post">
			<input type="hidden" name="diagnosisId" value="${diagnosis.diagnosisId }" />
			<table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
				<tr>
					<td class="active" style="width: 140px;">主诉</td>
					<td><textarea id="obstetricalDiagnosisMain" name="obstetricalDiagnosisMain" class="form-control" placeholder="点击输入主诉内容" maxlength="1000">${obstetrical.obstetricalDiagnosisMain }</textarea>
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
							<div class="div-inline">
								<select id="obstetricalBaby" name="obstetricalBaby" class="form-control">
									<option value="">请选择状态</option>
									<option value="正常胎龄儿">正常胎龄儿</option>
									<option value="大于胎龄儿">大于胎龄儿</option>
									<option value="小于胎龄儿">小于胎龄儿</option>
								</select>
							</div>
							<div class="div-inline">
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
							<div class="div-inline" style="width:500px;">
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
							<div class="div-inline">
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
							<div class="div-inline">
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
							<div class="div-inline">
								<div class="input-group">
									<div class="input-group-addon">胎儿股骨长</div>
									<input id="obstetricalBabyFemur" name="obstetricalBabyFemur" value="${obstetrical.obstetricalBabyFemur }" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入股骨长" />
									<div class="input-group-addon">mm</div>
								</div>
							</div>
							<div class="div-inline" style="width:160px;">
								<span id="foetus_femur_length">${obstetrical.obstetricalBabyFemurResult }</span>
								<input type="hidden" id="obstetricalBabyFemurResult" name="obstetricalBabyFemurResult" value="${obstetrical.obstetricalBabyFemurResult }"/>
							</div>
						</div>
						<div style="margin-top: 4px;">
							<div class="div-inline">
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
							<div class="div-inline">
								<div class="input-group">
									<div class="input-group-addon">胎儿腹围</div>
									<input id="obstetricalBabyAbdominalPerimeter" value="${obstetrical.obstetricalBabyAbdominalPerimeter }" name="obstetricalBabyAbdominalPerimeter" class="form-control text-right" onkeyup="checkNum(this);" type="text" maxlength="6" placeholder="请输入胎儿腹围" />
									<div class="input-group-addon">mm</div>
								</div>
							</div>
							<div class="div-inline" style="width:160px;">
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
						<hr style="margin: 2px 0 2px 1px; width: 600px;">
						<div style="width: 1px; height: 72px; background: #ddd; margin: -39px 0 -34px 301px;"></div>
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
						<div class="div-inline">
							<div class="input-group">
								<div class="input-group-addon">现体重</div>
								<input id="weight_input_text" name="obstetricalDiagnosisWeight" class="form-control text-right" onkeyup="checkNum(this);" type="text" value="${diagnosis.diagnosisCustWeight }" maxlength="5" placeholder="请输入当前体重" />
								<div class="input-group-addon">kg</div>
							</div>
						</div>
						<div class="div-inline">
							<div class="input-group">
								<div class="input-group-addon">收缩压</div>
								<input id="obstetricalDiagnosisSystolic" name="obstetricalDiagnosisSystolic" class="form-control text-right" onkeyup="checkNum(this);" type="text" value="${obstetrical.obstetricalDiagnosisSystolic }" maxlength="3" placeholder="请输入收缩压" />
								<div class="input-group-addon">mmHg</div>
							</div>
						</div>
						<div class="div-inline">
							<div class="input-group">
								<div class="input-group-addon">舒张压</div>
								<input id="obstetricalDiagnosisDiastolic" name="obstetricalDiagnosisDiastolic" class="form-control text-right" onkeyup="checkNum(this);" type="text" value="${obstetrical.obstetricalDiagnosisDiastolic }" maxlength="3" placeholder="请输入舒张压" />
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