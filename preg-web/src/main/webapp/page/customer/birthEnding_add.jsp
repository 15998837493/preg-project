<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp" %>
<script charset="UTF-8" src="${common.basepath}/page/customer/birthCheck.js"></script>
<script charset="UTF-8" src="${common.basepath}/page/customer/birthEnding_add.js"></script>
<script charset="UTF-8" src="${common.basepath}/page/customer/birthEnding_baby_add.js"></script>
<script type="text/javascript">
//取消回车事件
$(document).keydown(function(event){
    switch(event.keyCode){
       case 13:return false; 
       }
});

// 分娩结局信息
var birthEndingPojo = "${birthEndingPojo }";
var customer = "${customerPojo }";
var custId = "${customerPojo.custId }";
var customerBirthday = "${customerPojo.custBirthday }";
var insPojo = ${insPojo };
var archivesList = "${archivesList }";
//补充剂自动补全数组
var productAllMap = ${diseaseList};
var productListData = [];
if(!_.isEmpty(productAllMap)){
	for(var key in productAllMap){  
		var product = productAllMap[key];
		productListData.push({name:product.diseaseName, value:product});	
	};
}
var hospitalMap = ${hospitalList };
var hospitalListData = [];
if(!_.isEmpty(hospitalMap)){
	for(var key in hospitalMap){  
		var hospital = hospitalMap[key];
		hospitalListData.push({name:hospital.hospitalName, value:hospital});	
	};
}
var birthDirectionMap = ${codeList}
var birthDirectionListData = [];
if(!_.isEmpty(birthDirectionMap)){
	for(var key in birthDirectionMap){  
		var birthDirection = birthDirectionMap[key];
		birthDirectionListData.push({name:birthDirection.codeName, value:birthDirection});	
	};
}
</script>
<style>
.div-inline{
	width: 300px;
	padding: 2px;
}
.title-span{
	width: 70px;
	display: inline-block;
	text-align: left;
}
.intake-sm{
	width: 180px;
	height: 30px;
	margin: 0px;
	font-size: 12px;
}
.intake-input{
	width: 155px;
	height: 30px;
	color: black;
	margin: 0px;
	font-size: 12px;
}
.intake-input-group-addon {
    padding: 7px 6px;
    margin-right: 0px;
    font-size: 12px;
}
.form-inline select.form-control {
    display: inline-block;
    width: 218px;
    vertical-align: middle;
}
.input-collect{
	width:71px;
	height: 30px;
	margin: 0px;
	font-size: 12px;
}
</style>
<body>
<!-- 个人信息 -->
<div class="panel panel-lightblue" id="guide_customerinfo_div">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> <span>分娩结局录入列表</span>
	</div>
	<table class="table table-bordered" style="text-align: center">
		<thead>
			<tr>
				<td style="border: 0px; text-align: right;">病案号：</td>
				<td style="border: 0px; text-align: left;">${customerPojo.custSikeId }</td>
				<td style="border: 0px; text-align: right; ">ID：</td>
				<td style="border: 0px; text-align: left; ">${customerPojo.custPatientId }</td>
				<td style="border: 0px; text-align: right; ">姓名：</td>
				<td style="border: 0px; text-align: left; ">${customerPojo.custName }</td>
				<td style="border: 0px; text-align: right; ">身份证号：</td>
				<td style="border: 0px; text-align: left; ">${customerPojo.custIcard }</td>
			</tr>
		</thead>
		<thead>
			<tr>
				<td>序号</td>
				<td>分娩日期</td>
				<td>分娩孕周</td>
				<td>分娩医院</td>
				<td>产检医院</td>
				<td>登记时间</td>
				<td>登记人员</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody id="birrhInfoTable_tbody">
			<c:if test="${birthEndingPojo != '[]' }">
				<c:forEach var="birthEnding" items="${birthEndingPojo }" varStatus="status">
					<tr>
						<td>${status.count }</td>
						<td>${birthEnding.birthDate }</td>
						<td>${birthEnding.birthWeeks }</td>
						<td>${birthEnding.birthHospital }</td>
						<td>${birthEnding.birthPregHospital }</td>
						<td>${fn:substring(birthEnding.createTime, 0, 19)}</td>
						<td>${birthEnding.createUserName }</td>
						<td>
							<a href="#" onclick="showBirthEnding('${birthEnding.birthId}','${birthEnding.custId }')">查看</a>
							<a href="javascript:void(0)" onclick="editBirthEnding('${birthEnding.birthId}')">编辑</a>
							<a href="#" onclick="deleteBirthEnding('${birthEnding.birthId}',this)">删除</a>
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${birthEndingPojo == '[]' }">
				<tr>
					<td colspan="8">无分娩记录</td>
				</tr>
			</c:if>
			<tr id="birthInfoTable_add">
				<td colspan="8" style="text-align: center"><a href="#" onclick="showBirthEndingDiv('add')">+添加</a></td>
			</tr>
		</tbody>
	</table>
</div>

<!-- 弹窗选择建档数据 -->
<form id="archivesListForm" name="editItemForm" action="${common.basepath }/${applicationScope.URL.Customer.QUERY_CUST_PREG_RECPRD}" class="form-horizontal" method="post" >
	<div id="archivesListModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left" id="tittle"></div>
						<table class="table table-bordered" id="archivesTable">
							<thead class="active">
								<th>序号</th>
								<th>建档日期</th>
								<th>受孕方式</th>
								<th>胎数</th>
								<th>预产期</th>
								<th>建档医师</th>
								<th>操作</th>
							</thead>
							<tbody id="archives_tbody">
								
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>


<div id="birthEndingTitle" class="panel panel-lightblue col-xs-12 no-left padding-zero" style="display: none;">
	<div class="panel-heading text-center">
		<i class="fa fa-tag fa-fw"></i> <span>分娩结局录入</span>
	</div>
</div>
<!-- 基础信息 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero" id="birthEnding_div" style="display: none;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> <span>基础信息</span>
	</div>
	<div class="table-responsive panel-body" id="birthEndingInfo_div">
		<form id="birthEndingUpdateForm" action="${common.basepath }/${applicationScope.URL.BirthEnding.BIRTHENDING_SAVE}" method="post" class="form-inline no-bottom">
			<input type="hidden" name="archivesId" id="archivesId" value="${birthEnding.archivesId }" />
			<input type="hidden" name="custId" id="custId" value="${customerPojo.custId }" />
			<input type="hidden" name="birthId" id="birthId" value="${birthEnding.birthId }" />
			<input type="hidden" id="birthAge_hidden" name="birthAge" />
			<div class="div-inline">
				<span class="title-span">出生日期</span>
				<input id="birthBirthday" name="birthBirthday" type="text" class="intake-sm form_date" placeholder="请选择出生日期" readonly onclick="common.dateShow('birthBirthday')" />
			</div>
			<div class="div-inline">
				<span class="title-span">身高</span>
				<input type="text" id="birthHeight" name="birthHeight" value="" class="intake-input" onchange="checkNum(this);"/><span class="intake-input-group-addon">cm</span>
			</div>
			<div class="div-inline">
				<span class="title-span">孕前体重</span>
				<input type="text" id="birthWeight" name="birthWeight" class="intake-input" onchange="checkNumPoint(this);"/><span class="intake-input-group-addon">kg</span>
			</div>
			<div class="div-inline">
				<span class="title-span">住院日期</span>
				<input id="birthHospitalDate" name="birthHospitalDate" type="text" class="intake-sm form_date" placeholder="请选择住院日期" readonly onclick="common.dateShow('birthHospitalDate')" />
			</div>
			<div class="div-inline">
				<span class="title-span">住院号</span>
				<input type="text" id="birthPatientId" name="birthPatientId" class="intake-sm" onchange="checkHourMinutes(this);" />
			</div>
			<div class="div-inline">
				<span class="title-span"><font color="red">胎数</font></span>
				<input type="text" id="birthTiresNumber" name="birthTiresNumber" class="intake-sm" onchange="checkNum(this);" />
			</div>
			<div class="div-inline">
				<span class="title-span"><font color="red">孕</font></span>
				<input type="text" id="birthPregNumber" name="birthPregNumber" class="input-collect"  onchange="checkNum(this);" onblur="checkPregBorn();" />
				<span style="width: 30px; display: inline-block; text-align: center;"><font color="red">产</font> </span>
				<input type="text" id="birthBornNumber" name="birthBornNumber" class="input-collect" onchange="checkNum(this);" onblur="checkPregBorn();" />
			</div>
			<div class="div-inline">
				<span id="birthTiresType_span" class="title-span"><font color="red">受孕方式</font></span>
				<select id="birthTiresType" name="birthTiresType" class="input-collect" style="width:91px;" display: inline-block;">
					<option value="">请选择</option>
					<option value="1">自然受孕</option>
					<option value="2">辅助生殖</option>
				</select>
				<select id="birthTiresType2" name="birthTiresType2" class="input-collect" style="width:91px;" display: inline-block;">
					<option value="">请选择</option>
					<option value="1">意外妊娠</option>
					<option value="2">计划妊娠</option>
				</select>
			</div>
			<div class="div-inline">
				<span id="birthPregHospital_span" class="title-span">产检医院</span>
				<input type="checkbox" id="birthIsPregHospital" /> 本院
					<input type='hidden' id='birthPregHospital_hidden' name="birthIsPregHospital"/>
					<input type="text" id="birthPregHospital" name="birthPregHospital" class='intake-sm' style="width:131px;" placeholder="请选择产检医院" />
				</select>
			</div>
			<div class="div-inline">
				<span id="birthHospital_span" class="title-span">分娩医院</span>
				<input type="checkbox" id="birthIsThisHospital" /> 本院
				<input type='hidden' id='birthHospital_hidden' name="birthIsThisHospital"/>
				<input type="text" id="birthHospital" name="birthHospital" class='intake-sm' style="width:131px;" placeholder="请选择分娩医院" />
			</div>
			<div style="display: block;">
				<span id="birthHospital_span" class="title-span">备注</span>
				<textarea id="birthBaseRemark" name="birthBaseRemark" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
			</div>
		</form>
	</div>
</div>
<!-- 入院诊断 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero" id="dischargeIn_div" style="display: none;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> <span>入院诊断</span>
	</div>
	<div class="table-responsive panel-body" id="birthDiagnosis_info_div">
		<form id="birthDiagnosisForm" action="${common.basepath }/${applicationScope.URL.BirthEnding.BIRTHENDING_UPDATE}" method="post" class="form-inline no-bottom">
			<table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
				<div class="form-inline" id="diagnosis_div">
					<span class="title-span">入院诊断</span>
					<input type='hidden' id='birthDiagnosis_hidden' name="birthDiagnosis" class='input-sm' />
					<input id="birthDiagnosis" type="text" class="intake-sm form_date" placeholder="点击输入内容" />
				</div>
				<div style="display: block;">
					<span id="birthDiagRemark_span" class="title-span">备注</span>
					<textarea id="birthDiagRemark" name="birthDiagRemark" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
				</div>
			</table>
		</form>
	</div>
</div>
<!-- 分娩信息 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero" id="birthBaseInfo_div" style="display: none;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> <span>分娩信息</span>
	</div>
	<div class="table-responsive" id="birthBaseInfo_info_div">
		<form id="birthBaseInfoForm" action="${common.basepath }${applicationScope.URL.BirthEnding.BIRTHENDING_BASEINFO_SAVE}" method="post">
			<input type="hidden" id="baseId" name="baseId" />
			<input type="hidden" id="lmpId" name="baseLmp"/>
			<input type="hidden" id="base_birthId" name="birthId" value="${birthEnding.birthId }"/>
			<table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
				<tr>
					<td style="width:120px;">基本情况</td>
					<td>
						<div class="div-inline">
							<span class="title-span"><font color="red">分娩时间</font></span>
							<input id="baseTime" name="baseTime" type="text" class="intake-sm form_date" style="width:97px;" placeholder="请选择分娩日期" readonly onclick="common.dateShow('baseTime')" />
							<input type="text" id="baseTimeHour" name="baseTimeHour" class="intake-sm" style="width: 28px;" onchange="checkHourMinutes(this,'hour');"/>
							<span style="width: 10px; display: inline-block; text-align: center;" onchange="checkNum(this);" >时</span>
							<input type="text" id="baseTimeMinuters" name="baseTimeMinuters" class="intake-sm" style="width: 28px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 10px; display: inline-block; text-align: center;" onchange="checkNum(this);" >分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">分娩周数</font></span>
							<input type="text" id="baseWeeks" name="baseWeeks" class="intake-sm" onchange="checkWeeks(this)" />
						</div>
						<div class="div-inline">
							<span id="birthAge_span" class="title-span"><font color="red">分娩年龄</font></span>
							<input id="birthAge" name="birthAge" type="text" class="intake-sm" onchange="checkNum(this);" />
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">危重孕产妇</font></span>
							<select id="baseIscritical" name="baseIscritical" class="intake-sm" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span">麻醉方式</span>
							<select id="baseHocusType" name="baseHocusType" class="intake-sm" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">局部麻醉</option>
								<option value="2">全身麻醉</option>
								<option value="3">椎管内麻醉</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">分娩方式</font></span>
							<select id="baseBirthType" name="baseBirthType" class="intake-sm" style="width: 108px;" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">自然分娩</option>
								<option value="2">吸引</option>
								<option value="3">产钳</option>
								<option value="4">臀助产</option>
								<option value="5">剖宫产</option>
								<option value="6">其他</option>
							</select>
							<input type="text" id="baseBirthType2" name="baseBirthType2" class="intake-sm" style="width: 100px;"/>
						</div>
						<div class="div-inline">
							<span class="title-span">剖宫产指征</span>
							<input type="text" id="basePgcIndication" name="basePgcIndication" class="intake-sm" />
						</div>
						<div class="div-inline">
							<span class="title-span">分娩方位</span>
							<input type="text" id="baseBirthDirection" name="baseBirthDirection" class="intake-sm"/>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">分娩前体重</font></span>
							<input type="text" id="baseWeightBeforeBirth" name="baseWeightBeforeBirth" class="intake-input" onchange="checkNumPoint(this);"/><span class="intake-input-group-addon">kg</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">分娩后体重</font></span>
							<input type="text" id="baseWeightAfterBirth" name="baseWeightAfterBirth" class="intake-input" onchange="checkNumPoint(this);"/><span class="intake-input-group-addon">kg</span>
						</div>
						<div class="div-inline">
							<span><font color="red">孕期总体重增长</font></span>
							<input type="text" id="baseGrowthPregnancying" name="baseGrowthPregnancying" class="intake-input" style="width:160px;" onchange="checkNumPoint(this);"/><span class="intake-input-group-addon">kg</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>产程时长</td>
					<td>
						<div class="div-inline">
							<span class="title-span">第一产程</span>
							<input type="text" id="baseChildBirthFist_hour" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="baseChildBirthFist_minutes" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">分 </span>
							<input type="hidden" id="baseChildBirthFist" name="baseChildBirthFist"/>
						</div>
						<div class="div-inline">
							<span class="title-span">第二产程</span>
							<input type="text" id="baseChildBirthSecond_hour" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="baseChildBirthSecond_minutes" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">分 </span>
							<input type="hidden" id="baseChildBirthSecond" name="baseChildBirthSecond"/>
						</div>
						<div class="div-inline">
							<span class="title-span">第三产程</span>
							<input type="text" id="baseChildBirthThrid_hour" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="baseChildBirthThrid_minutes" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">分 </span>
							<input type="hidden" id="baseChildBirthThrid" name="baseChildBirthThrid"/>
						</div>
						<div class="div-inline">
							<span class="title-span">总产程</span>
							<input type="text" id="baseChildBirthSum_hour" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="baseChildBirthSum_minutes" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 30px; display: inline-block; text-align: center;">分 </span>
							<input type="hidden" id="baseChildBirthSum" name="baseChildBirthSum"/>
						</div>
					</td>
				</tr>
				<tr>
					<td>出血量</td>
					<td>
						<div class="div-inline">
							<span class="title-span">产时出血量</span>
							<input type="text" id="baseBloodVolBirthing" name="baseBloodVolBirthing" class="intake-input" onchange="checkNum(this,'AmnioticFluid');"/><span class="intake-input-group-addon">ml</span>
						</div>
						<div class="div-inline">
							<span>产后两小时出血量</span>
							<input type="text" id="baseBloodVolAfterBirth" name="baseBloodVolAfterBirth" class="intake-input" style="width:113px;" onchange="checkNum(this,'AmnioticFluid');"/><span class="intake-input-group-addon">ml</span>
						</div>
						<div class="div-inline">
							<span class="title-span">总出血量</span>
							<input type="text" id="baseBloodVolSum" name="baseBloodVolSum" class="intake-input" onchange="checkNum(this,'AmnioticFluid');"/><span class="intake-input-group-addon">ml</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>会阴及胎盘</td>
					<td>
						<div class="div-inline">
							<span class="title-span">阴检</span>
							<input type="text" id="basePerineumCheckTimes" name="basePerineumCheckTimes" class="intake-input" onchange="checkNum(this,'AmnioticFluid');"/><span class="intake-input-group-addon">次</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">会阴情况</font></span>
							<select id="basePerineumState" name="basePerineumState" class="intake-sm" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">完整</option>
								<option value="2">裂伤</option>
								<option value="3">切开</option>
							</select>
						</div>
						<div class="div-inline">
							<span><font color="red">会阴裂伤程度</font></span>
							<select id="basePerineumHurt" name="basePerineumHurt" class="form-control" style="width: 166px; display: inline-block;">
								<option value="">请选择</option>
								<option value="1">Ⅰ°</option>
								<option value="2">Ⅱ°</option>
								<option value="3">Ⅲ°</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span">缝合</span>
							<input type="text" id="basePerineumStitching" name="basePerineumStitching" class="intake-input" onchange="checkNum(this,'AmnioticFluid');"/><span class="intake-input-group-addon">针</span>
						</div>
						<div class="div-inline">
							<span class="title-span">胎盘</span>
							<select id="basePerineumPlacenta" name="basePerineumPlacenta" class="intake-sm" style="display: inline-block;">
								<option value="">请选择</option>
								<option value="1">手剥</option>
								<option value="2">沾水</option>
								<option value="3">自然脱落</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>手术</td>
					<td>
						<div class="div-inline">
							<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_1" value="1">
							<span class="title-span">引产</span>
							<select id="baseSurgeryDetail" name="baseSurgeryDetail" class="form-control" style="width: 164px; display: inline-block;">
								<option value="">请选择</option>
								<option value="1">改良药物</option>
								<option value="2">剥膜</option>
								<option value="3">点滴</option>
								<option value="4">破膜</option>
								<option value="5">其他</option>
							</select>
						</div>
						<div class="div-inline">
							<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_2" value="2">
							<span class="baseSurgeryType-span">产后刮宫</span>
						</div>
						<div class="div-inline">
							<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_3" value="3">
							<span class="baseSurgeryType-span">手转胎头</span>
						</div>
						<div class="div-inline">
							<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_4" value="4">
							<span class="baseSurgeryType-span">点滴加强</span>
						</div>
						<div class="div-inline">
							<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_5" value="5">
							<span class="baseSurgeryType-span">其他</span>
							<input type="text" id="baseSurgeryDetail2" name="baseSurgeryDetail2" class="intake-sm" style="width:170px;"/>(50个字符)
						</div>
<!-- 							<input type="hidden" id="baseSurgeryType" name="baseSurgeryType" class="intake-sm"/> -->
					</td>
				</tr>
				<tr>
					<td>并发症</td>
					<td>
						<div class="div-inline">
							<span class="title-span">产前检查</span>
							<select id="baseComplicationPrenatalCal" name="baseComplicationPrenatalCal" class="intake-sm" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
						<div class="div-inline">
							<span>中度贫血HB小于90g/L</span>
							<select id="baseComplicationAnemia" name="baseComplicationAnemia" class="intake-sm" style="width:107px;" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="div-inline">
							<span>妊娠高血压疾病</span>
							<select id="baseComplicationHypertension" name="baseComplicationHypertension" class="intake-sm" style="width:162px;" display: inline-block;">
								<option value="">请选择</option>
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
						<div class="form-inline" id="baseComplicationPrenatal_div">
							<span class="title-span">产前合并症</span>
							<input type='hidden' id='baseComplicationPrenatal_hidden' name="baseComplicationPrenatal" class='input-sm' />
							<input type="text" id="baseComplicationPrenatal" class="intake-sm" placeholder="请输入疾病名称" />
						</div>
						<div class="form-inline" id="baseComplicationBirthing_div">
							<span class="title-span">产时并发症</span>
							<input type='hidden' id='baseComplicationBirthing_hidden' name="baseComplicationBirthing" class='input-sm' />
							<input type="text" id="baseComplicationBirthing" class="intake-sm" placeholder="请输入疾病名称" />
						</div>
						<div class="form-inline" id="baseComplicationAfterBirthing_div">
							<span class="title-span">产后并发症</span>
							<input type='hidden' id='baseComplicationAfterBirthing_hidden' name="baseComplicationAfterBirthing" class='input-sm' />
							<input type="text" id="baseComplicationAfterBirthing" class="intake-sm" placeholder="请输入疾病名称" />
						</div>
						<div class="form-inline" id="baseComplicationsMedical_div">
							<span class="title-span">内科合并症</span>
							<input type='hidden' id='baseComplicationsMedical_hidden' name="baseComplicationsMedical" class='input-sm' />
							<input type="text" id="baseComplicationsMedical" class="intake-sm" placeholder="请输入疾病名称" />
						</div>
						<div class="form-inline">
							<span>传染病检测情况</span>
							<input type="text" id="baseComplicationDisease" name ="baseComplicationDisease" class="intake-sm" />
						</div>
					</td>
				</tr>
				<tr>
					<td>产后情况</td>
					<td>
						<div class="div-inline">
							<span class="title-span">收缩压</span>
							<input type="text" id="baseAfterBirthingSsy" name="baseAfterBirthingSsy" class="intake-input" style="width:120px;" onchange="checkNum(this,'ssy','3');"/><span class="intake-input-group-addon">mmHg</span>
						</div>
						<div class="div-inline">
							<span class="title-span">舒张压</span>
							<input type="text" id="baseAfterBirthingSzy" name="baseAfterBirthingSzy" class="intake-input" style="width:120px;" onchange="checkNum(this,'szy','3');"/><span class="intake-input-group-addon">mmHg</span>
						</div>
						<div class="div-inline">
							<span>开奶时间  产后</span>
							<input type="text" id="baseAfterBirthingBreastMilkl_hour" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 20px; display: inline-block; text-align: center;">时</span>
							<input type="text" id="baseAfterBirthingBreastMilkl_minutes" class="intake-sm" style="width: 60px;" onchange="checkHourMinutes(this,'minuters');"/>
							<span style="width: 20px; display: inline-block; text-align: center;">分</span>
							<input type="hidden" id="baseAfterBirthingBreastMilkl" name="baseAfterBirthingBreastMilkl" />
						</div>
					</td>
				</tr>
				<tr>
					<td>产妇结局</td>
					<td>
						<div class="div-inline">
							<input type="radio" id="baseMaterEndingLive" name = "baseMaterEndingLiveOrDeath" checked="checked" value="1" >
							<span class="title-span">存活</span>
						</div>
						<div class="div-inline">
							<input type="radio" id="baseMaterEndingDeath" name = "baseMaterEndingLiveOrDeath" value="2" >
							<span class="title-span">死亡</span>
							<select id="baseBirthBirthingDetail" name="baseBirthBirthingDetail" class="intake-sm" display: inline-block;">
								<option value="">请选择</option>
								<option value="1">产时</option>
								<option value="2">产后</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>分娩结局</td>
					<td>
						<div class="div-inline">
							<span class="title-span">活产数</span>
							<input type="text" id="baseBirthEndingLiveBirths" name="baseBirthEndingLiveBirths" class="intake-sm" maxlength="1" onchange="checkNum(this,'AmnioticFluid');"/>
						</div>
						<div class="div-inline">
							<span class="title-span">死胎数</span>
							<input type="text" id="baseBirthEndingDeathBabys" name="baseBirthEndingDeathBabys" class="intake-sm" maxlength="1" onchange="checkNum(this,'AmnioticFluid');"/>
						</div>
						<div class="div-inline">
							<span class="title-span">死产数</span>
							<input type="text" id="baseBirthEndingDeathBirths" name="baseBirthEndingDeathBirths" class="intake-sm" maxlength="1" onchange="checkNum(this,'AmnioticFluid');"/>
						</div>
						<div class="div-inline">
							<span class="title-span">围产儿数</span>
							<input type="text" id="baseBirthEndingPerinatal" name="baseBirthEndingPerinatal" class="intake-sm" maxlength="1" onchange="checkNum(this,'AmnioticFluid');"/>
						</div>
						<div class="div-inline" style="width:450px;">
							<span>孕28周前双/多胎宫内死亡胎数</span>
							<input type="text" id="baseDeathBefor28" name="baseDeathBefor28" class="intake-sm" maxlength="1" onchange="checkNum(this,'AmnioticFluid');"/>
						</div>
						<div class="div-inline" style="width:550px;">
							<span>孕28周前双/多胎宫内死亡原因</span>
							<select id="baseDeathReasonBefor28" name="baseDeathReasonBefor28" class="intake-sm"  display: inline-block;">
								<option value="">请选择</option>
								<option value="1">胎停育</option>
								<option value="2">治疗性减胎</option>
								<option value="3">其他</option>
							</select>
						</div>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td>
						<textarea id="baseRemark" name="baseRemark" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<!-- 新生儿 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero" id="birthBabyInfo_div" style="display: none;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> <span>新生儿</span>
	</div>
	<div class="table-responsive" id="birthBabyInfo_info_div">
		<form id="birthInfoForm" action="${common.basepath }${applicationScope.URL.BirthEnding.BIRTHENDING_BABYINFO_SAVE}" method="post" class="form-inline no-bottom">
			<input type="hidden" id="tempI" />
			<input type="hidden" id="baby_birthId" name=baby_birthId value="${birthEnding.birthId }" />
			<!-- <input type="hidden" id="babyId" name="babyId" />
			<table id="babyTable" class="table table-bordered table-padding-4 no-bottom" style="display:none">
				<tr>
					<td style="width:120px;">新生儿</td>
					<td>
					<div class="div-inline">
						<span class="title-span"><font color="red">性别</font></span>
						<select id="babyGender" name="babyGender" class="intake-sm">
							<option value="">请选择</option>
							<option value="1">男</option>
							<option value="2">女</option>
							<option value="3">不明</option>
						</select>
					</div>
					<div class="div-inline">
						<span class="title-span"><font color="red">出生日期</font></span>
						<input id="babyBirthTime" name="babyBirthTime" type="text" class="intake-sm form_date" style="width:107px;" placeholder="请选择出生日期" readonly onclick="common.dateShow('babyBirthTime')" />
						<input type="text" id="babyBirthTimeHour" name="babyBirthTimeHour" class="intake-sm" style="width: 30px;" onchange="checkHourMinutes(this,'hour');"/>
						<span style="width: 10px; display: inline-block; text-align: center;">时 </span>
						<input type="text" id="babyBirthTimeMinutes" name="babyBirthTimeMinutes" class="intake-sm" style="width: 30px;" onchange="checkHourMinutes(this,'minutes');"/>
						<span style="width: 10px; display: inline-block; text-align: center;">分 </span>
					</div>
					<div class="div-inline">
						<span class="title-span"><font color="red">身长</font></span>
						<input type="text" id="babyLength" name="babyLength" class="intake-input" onchange="checkNumPoint(this);" /><span class="intake-input-group-addon">cm</span>
					</div>
					<div class="div-inline">
						<span class="title-span"><font color="red">体重</font></span>
						<input type="text" id="babyWeight" name="babyWeight" class="intake-input" onchange="checkNum(this);"/><span class="intake-input-group-addon">g</span>
					</div>
					<div class="div-inline">
						<span class="title-span">头围</span>
						<input type="text" id="babyHeadCircum" name="babyHeadCircum" class="intake-input" onchange="checkNumPoint(this);"/><span class="intake-input-group-addon">cm</span>
					</div>
					<div class="div-inline">
						<span class="title-span">胸围</span>
						<input type="text" id="babyBust" name="babyBust" class="intake-input" onchange="checkNumPoint(this);"/><span class="intake-input-group-addon">cm</span>
					</div>
					<div class="div-inline">
						<span class=""><font color="red">阿氏评分</font></span>
						<font color="red">1分钟</font><input type="text" id="babyAshesOneMinute" name="babyAshesOneMinute" class="intake-input" onchange="checkNum(this);"/><span class="intake-input-group-addon">分</span>
					</div>
					<div class="div-inline">
						<span class="title-span"><font color="red">5分钟</font></span>
						<input type="text" id="babyAshesFiveMinutes" name="babyAshesFiveMinutes" class="intake-input" onchange="checkNum(this);" /><span class="intake-input-group-addon">分</span>
					</div>
					<div class="div-inline">
						<span class="title-span"><font color="red">10分钟</font></span>
						<input type="text" id="babyAshesTenMinutes" name="babyAshesTenMinutes" class="intake-input" onchange="checkNum(this);" /><span class="intake-input-group-addon">分</span>
					</div>
					<div class="div-inline">
						<span class="title-span"><font color="red">新生儿窒息</font></span>
						<input type="text" id="babyStifle" name="babyStifle" class="intake-input" onchange="checkNum(this);"/><span class="intake-input-group-addon">分钟</span>
					</div>
					<div class="div-inline">
						<span class="title-span">出生缺陷</span>
						<input type="text" id="babyDefect" name="babyDefect" class="intake-sm"/>
					</div>
					<div class="div-inline">
						<span class="title-span">抢救</span>
						<select id="babyRescue" name="babyRescue" class="intake-sm">
							<option value="">请选择</option>
							<option value="1">有</option>
							<option value="0">无</option>
						</select>
					</div>
					<div class="form-inline" id="babyComplication_div">
						<span class="title-span">并发症</span>
						<input type='hidden' id="babyComplication_hidden" name="babyComplication" class='form-control' />
						<input type="text" id="babyComplication"  class="intake-sm"/>
					</div>
					<div style="display: block;">
						<span id="birthHospital_span" class="">新生儿指导</span>
						<textarea id="babyGuid" name="babyGuid" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
					</div>
				</td>
				</tr>
				<tr>
					<td>胎盘/羊水情况1</td>
					<td>
						<div class="div-inline">
							<span class="title-span"><font color="red">胎盘重</font></span>
							<input type="text" id="babyPlacentaWeight" name="babyPlacentaWeight" class="intake-input" onchange="checkNumPoint2(this);"/><span class="intake-input-group-addon">g</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">胎盘长</font></span>
							<input type="text" id="babyPlacentaLength" name="babyPlacentaLength" class="intake-input" onchange="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">胎盘宽</font></span>
							<input type="text" id="babyPlacentaWidth" name="babyPlacentaWidth" class="intake-input" onchange="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">胎盘厚</font></span>
							<input type="text" id="babyPlacentaThick" name="babyPlacentaThick" class="intake-input" onchange="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">羊水量</font></span>
							<select id="babyAmnioticFluidVol" name="babyAmnioticFluidVol" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">少</option>
								<option value="2">中</option>
								<option value="3">多</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">羊水性状</font></span>
							<select id="babyAmnioticFluidTraits" name="babyAmnioticFluidTraits" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">清</option>
								<option value="2">浊</option>
							</select>
						</div>
					</td>
				</tr>
				<div>
				<tr>
					<td>新生儿1备注</td>
					<td>
						<textarea id="babyRemark" name="babyRemark" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
					</div>
					</td>
				</tr>
				<tr>
					<td>新生儿1死亡</td>
					<td>
						<div class="div-inline">
							<span class="title-span"><font color="red">新生儿死亡</font></span>
							<select id="babyIsDeath" name="babyIsDeath" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">死亡时间</font></span>
							<input type="text" id="babyDeathTime" name="babyDeathTime" class="intake-sm form_date" style="width:108px;" placeholder="请选择死亡日期" readonly onclick="common.dateShow('baseTime')"/>
							<input type="text" id="babyDeathTimeHour" name="babyDeathTimeHour" class="intake-sm" style="width: 30px;" onchange="checkHourMinutes(this,'hour');"/>
							<span style="width: 15px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="babyDeathTimeMinutes" name="babyDeathTimeMinutes" class="intake-sm" style="width: 30px;" onchange="checkHourMinutes(this,'minutes');"/>
							<span style="width: 15px; display: inline-block; text-align: center;">分 </span>
						</div>
						<div style="display: block;">
							<span class="title-span">死亡原因</span>
							<textarea id="babyAmnioticFluidDeathReason" name="babyAmnioticFluidDeathReason" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center">
						<a onclick="newBaby()">+添加新生儿</a>  
						<input type="radio" id="liveAdd" name="newBaby" checked="checked" value="1"/>存活
						<input type="radio" id="deathAdd" name="newBaby" value="2" />死亡
					</td>
				</tr>
			</table> -->
			<table id="babyTable" class="table table-bordered table-padding-4 no-bottom">
				<tr>
					<td colspan="2" style="text-align: center">
						<a onclick="newBaby()">+添加新生儿</a>  
						<input type="radio" id="liveAdd" name="newBaby" checked="checked" value="1"/>存活
						<input type="radio" id="deathAdd" name="newBaby" value="2" />死亡
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<!-- 出院诊断 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero" id="discharged_div" style="display: none;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> <span>出院诊断</span>
	</div>
	<div class="table-responsive" id="birthDischarged_info_div">
		<form id="dischargedForm" action="${common.basepath }/${applicationScope.URL.BirthEnding.BIRTHENDING_DISCHARGE_SAVE}" method="post" class="form-inline no-bottom">
			<input type="hidden" id="disId" name="disId" value="${birthDischarge.disId }" />
			<input type="hidden" id="dis_birthId" name="birthId" value="${birthEnding.birthId }" />
<!-- 			<input type="hidden" id="dis_babyId" name="dis_babyId" /> -->
			<table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
				<tr>
					<td style="width:120px;">母亲</td>
					<td>
						<div class="form-inline" id="disMotherDisagnosis_div">
							<span class="title-span">出院诊断</span> 
							<input type='hidden' id="disMotherDisagnosis_hidden" name="disMotherDisagnosis" class='input-sm' />
							<input type="text" id="disMotherDisagnosis" class="intake-sm" placeholder="请输入疾病" />
						</div>
						<div class="form-inline">
							<span style="width:90px;">产后血红蛋白</span> 
							<input type="text" id="disHemoglobin" name="disHemoglobin" class="intake-input" style="width:135px;" onchange="checkNum(this);"/><span class="intake-input-group-addon">g/L</span>
						</div>
					</td>
				</tr>
				<tr id="discharge_remark">
					<td>备注</td>
					<td>
						<textarea id="disRemark" name="disRemark" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>