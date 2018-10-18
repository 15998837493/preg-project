<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_main.css" />
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_babyinfo.js"></script>
<body>
<div class="col-xs-1 mytitle-left"></div>
<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i><span>新生儿情况</span></div>
<div class="panel panel-lightblue col-xs-12 padding-zero" id="saveQuota_info_div">
	<div class="bodyClass" id="babyInfo"></div>
	<div class="table-responsive" id="addbaby">
		<table class="table table-bordered no-bottom">
			<tr>
				<td colspan="2" style="text-align: center">
					<label><input type="radio" name="newBaby" checked="checked" value="0" style="vertical-align: top;"/> 存活&nbsp;&nbsp;</label>
					<label><input type="radio" name="newBaby" value="1" style="vertical-align: top;"/> 死亡&nbsp;&nbsp;</label>
					<label><a onclick="addBabyInfo()"> + 添加新生儿 </a></label>
				</td>
			</tr>
		</table>
	</div>
</div>

<!-- 出院诊断 -->
<div class="col-xs-1 mytitle-left"></div>
<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i><span>出院诊断</span></div>
<div class="panel panel-lightblue col-xs-12 padding-zero" id="saveQuota_info_div">
	<div class="bodyClass" id="disagnosisInfo">
		<div class="table-responsive">
			<form action="" method="post" class="no-bottom" id="motherForm">
				<table class="table table-bordered table-padding-4 no-bottom">
					<tr>
						<td class="thirdTitle">母亲</td>
						<td>
							<input type="hidden" id="mother_babyId" value="" name="babyId"/><!-- babyId -->
							<input type="hidden" id="mother_disId" name="disId"/><!-- disId -->
							<div class="col-xs-12 form-inline padding-zero" id="selectedMotherDiseases" name="diseases" style="margin-bottom: 5px;">
								<div class="col-xs-3 input-group padding-zero">
									<input type="hidden" id="disMotherDiagnosis_hidden" name="disMotherDisagnosis"/><!-- 保存所选疾病id -->
									<div class="input-group-addon addon-left" style="width: 96px;">出院诊断</div>
									<input class="intake-sm" type="text" id="disMotherDiagnosis" value="" placeholder="请输入诊断"/>
								</div>
							</div>
							<div class="col-xs-12 padding-zero">
								<div class="col-xs-3 input-group padding-zero">
									<div class="input-group-addon addon-left">产后血红蛋白</div>
									<input class="intake-input" type="text" id="disHemoglobin" maxlength="6" name="disHemoglobin" value="" onkeyup="checkNum(this);" />
									<div class="input-group-addon">g/L</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
			<div id="babyDiagonsis"></div>
			<form action="" method="post" class="no-bottom" id="remarkForm">
				<div class="table-responsive">
					<table class="table table-bordered table-padding-4 no-bottom">
						<tbody>
							<tr>
								<td class="thirdTitle">备注</td>
								<td>
									<input type="hidden" id="remark_babyId" value="" name="babyId"/><!-- babyId -->
									<input type="hidden" id="remark_disId" name="disId"/><!-- disId -->
									<textarea id="disRemark" name="disRemark" class="form-control" placeholder="点击输入内容" maxlength="1000"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
