<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
// 分娩结局信息
var birthEnding ="${birthEndingPojo }";
// 患者信息
var customer="${customerPojo }";
</script>
<style>
.div-inline{
	width: 300px;
	padding: 2px;
}
.title-span{
	width: 56px;
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
	width: 188px;
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
.intake-input{
	widht:180px;
}
</style>
<body>
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-tag fa-fw"></i>基本信息
						</div>
						<div class="table-responsive">
							<table summary=" 一、基础信息" class="table table-bordered">
								<tr>
									<td class='text-right active' style="width: 10%">病案号</td>
									<td class='text-left' style="width: 17%">${customerPojo.custSikeId }</td>
									<td class='text-right active' style="width: 10%">ID</td>
									<td class='text-left' style="width: 17%">${customerPojo.custPatientId }</td>
									<td class='text-right active' style="width: 10%">姓名</td>
									<td class='text-left' style="width: 12.5%">${customerPojo.custName }</td>
									<td class='text-right active' style="width: 10%">身份证号</td>
									<td class='text-left' style="width: 12.5%">${customerPojo.custIcard }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">出生日期</td>
									<td class='text-left' style="width: 12.5%">${birthEndingPojo.birthBirthday }</td>
									<td class='text-right active' style="width: 10%">分娩年龄</td>
									<td class='text-left' style="width: 12.5%">${birthBasePojo.birthAge }</td>
									<td class='text-right active' style="width: 10%">身高</td>
									<td class='text-left' style="width: 12.5%">
										<c:if test="${birthEndingPojo.birthHeight != null }">
											${birthEndingPojo.birthHeight }cm
										</c:if>
									</td>
									<td class='text-right active' style="width: 10%">孕前体重</td>
									<td class='text-left' style="width: 12.5%">
										<c:if test="${birthEndingPojo.birthWeight != null}">${birthEndingPojo.birthWeight }kg</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">住院日期</td>
									<td class='text-left' style="width: 12.5%">${birthEndingPojo.birthHospitalDate }</td>
									<td class='text-right active' style="width: 10%">住院号</td>
									<td class='text-left' style="width: 12.5%">${birthEndingPojo.birthPatientId }</td>
									<td class='text-right active' style="width: 10%">胎数</td>
									<td class='text-left' style="width: 12.5%">${birthEndingPojo.birthTiresNumber }</td>
									<td class='text-right active' style="width: 10%">孕产次</td>
									<td class='text-left' style="width: 12.5%">
										<c:if test="${birthEndingPojo.birthPregNumber != null and birthEndingPojo.birthPregNumber != 0}">孕${birthEndingPojo.birthPregNumber }</c:if>
										<c:if test="${birthEndingPojo.birthBornNumber != null and birthEndingPojo.birthBornNumber != 0}">产${birthEndingPojo.birthBornNumber }</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">受孕方式</td>
									<td class='text-left' style="width: 12.5%">
										<font color="red">
											<c:if test="${birthEndingPojo.birthTiresType == '1'}">
												自然受孕
												<c:if test="${birthEndingPojo.birthTiresType2 == '1'}">
													（意外妊娠）
												</c:if>
												<c:if test="${birthEndingPojo.birthTiresType2 == '2'}">
													（计划妊娠）
												</c:if>
											</c:if>
											<c:if test="${birthEndingPojo.birthTiresType == '2'}">辅助生殖</c:if>
										</font>
									</td>
									<td class='text-right active' style="width: 10%">产检医院</td>
									<td class='text-left' style="width: 12.5%">${birthEndingPojo.birthPregHospital }</td>
									<td class='text-right active' style="width: 10%">分娩医院</td>
									<td class='text-left' style="width: 12.5%">${birthEndingPojo.birthHospital }</td>
									<td class='text-right active' style="width: 10%"></td>
									<td class='text-left' style="width: 12.5%"></td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">备注</td>
									<td colspan="7">${birthEndingPojo.birthBaseRemark }</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-tag fa-fw"></i>入院诊断
						</div>
						<div class="table-responsive">
							<table summary=" 入院诊断" class="table table-bordered">
								<tr>
									<td class='text-right active' style="width: 10%">诊断</td>
									<td colspan="7">${birthEndingPojo.birthDiagnosis}</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">备注</td>
									<td colspan="7">${birthEndingPojo.birthDiagRemark }</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-tag fa-fw"></i>分娩信息
						</div>
						<div class="table-responsive">
							<table summary=" 一、分娩信息" class="table table-bordered">
								<tr>
									<td class='text-right active' style="width: 10%">分娩时间</td>
									<td style="width: 17%">
										<fmt:formatDate value="${birthBasePojo.baseTime }" pattern="yyyy年MM月dd日"  />&nbsp;
										<c:if test="${birthBasePojo.baseTimeHour != null}">
											${birthBasePojo.baseTimeHour }时 &nbsp; 
										</c:if>	
										<c:if test="${birthBasePojo.baseTimeHour == null and birthBasePojo.baseTimeMinuters != null}">
											0时 &nbsp; 
										</c:if>	
										<c:if test="${birthBasePojo.baseTimeMinuters != null}">
											${birthBasePojo.baseTimeMinuters }分
										</c:if>
									</td>
									<td class='text-right active' style="width: 10%">分娩孕周数</td>
									<td style="width: 17%">
										<c:if test="${birthBasePojo.baseWeeks != null && birthBasePojo.baseWeeks != '' }">${birthBasePojo.baseWeeks }周</c:if>
									</td>
									<td class='text-right active' style="width: 10%">危重孕产妇</td>
									<td style="width: 12.5%">
										<c:if test="${birthBasePojo.baseIscritical == 0}">否</c:if>
										<c:if test="${birthBasePojo.baseIscritical == 1}">是</c:if>
									</td>
									<td class='text-right active' style="width: 10%">麻醉方式</td>
									<td style="width: 12.5%">
										<c:if test="${birthBasePojo.baseHocusType == 1}">局部麻醉</c:if>
										<c:if test="${birthBasePojo.baseHocusType == 2}">全身麻醉</c:if>
										<c:if test="${birthBasePojo.baseHocusType == 3}">椎管内麻醉</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">分娩方式</td>
									<td>
										<c:if test="${birthBasePojo.baseBirthType == 1}">自然分娩</c:if>
										<c:if test="${birthBasePojo.baseBirthType == 2}">吸引</c:if>
										<c:if test="${birthBasePojo.baseBirthType == 3}">产钳</c:if>
										<c:if test="${birthBasePojo.baseBirthType == 4}">臀助产</c:if>
										<c:if test="${birthBasePojo.baseBirthType == 5}">剖宫产</c:if>
										<c:if test="${birthBasePojo.baseBirthType == 6}">
											其他&nbsp;(${birthBasePojo.baseBirthType2 })
										</c:if>
									</td>
									<td class='text-right active' style="width: 10%">剖宫产指征</td>
									<td>${birthBasePojo.basePgcIndication }</td>
									<td class='text-right active' style="width: 10%">分娩方位</td>
									<td>${birthBasePojo.baseBirthDirection}</td>
									<td class='text-right active' style="width: 10%">分娩前体重</td>
									<td>
										<c:if test="${birthBasePojo.baseWeightBeforeBirth != null}">${birthBasePojo.baseWeightBeforeBirth }kg</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">分娩后体重</td>
									<td>
										<c:if test="${birthBasePojo.baseWeightAfterBirth != null}">${birthBasePojo.baseWeightAfterBirth }kg</c:if>
									</td>
									<td class='text-right active' style="width: 10%">产时出血量</td>
									<td>
										<c:if test="${birthBasePojo.baseBloodVolBirthing != null}">${birthBasePojo.baseBloodVolBirthing }ml</c:if>
									</td>
									<td class='text-right active' style="width: 10%">产后两小时出血量</td>
									<td>
										<c:if test="${birthBasePojo.baseBloodVolAfterBirth != null}">${birthBasePojo.baseBloodVolAfterBirth}ml</c:if>
									</td>
									<td class='text-right active' style="width: 10%">总出血量</td>
									<td>
										<c:if test="${birthBasePojo.baseBloodVolSum != null}">${birthBasePojo.baseBloodVolSum }ml</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">孕期总体重增长</td>
									<td>
										<c:if test="${birthBasePojo.baseGrowthPregnancying != null}">${birthBasePojo.baseGrowthPregnancying }kg</c:if>
									</td>
									<td class='text-right active' style="width: 10%">阴检</td>
									<td>
										<c:if test="${birthBasePojo.basePerineumCheckTimes != null}">${birthBasePojo.basePerineumCheckTimes }次</c:if>
									</td>
									<td class='text-right active' style="width: 10%">会阴</td>
									<td>
										<font color="red">
											<c:if test="${birthBasePojo.basePerineumState != '' and birthBasePojo.basePerineumState != null and birthBasePojo.basePerineumState != 2 }">
												<c:if test="${birthBasePojo.basePerineumState ==1 }">完整</c:if>	
												<c:if test="${birthBasePojo.basePerineumState ==3 }">切开</c:if>	
											</c:if>
											<c:if test="${birthBasePojo.basePerineumHurt != '' and birthBasePojo.basePerineumHurt != null}">
												<c:if test="${birthBasePojo.basePerineumHurt ==1 }">Ⅰ°</c:if>	
												<c:if test="${birthBasePojo.basePerineumHurt ==2 }">Ⅱ°</c:if>	
												<c:if test="${birthBasePojo.basePerineumHurt ==3 }">Ⅲ°</c:if>	
											度裂伤
											</c:if>
											<c:if test="${birthBasePojo.basePerineumHurt == null && birthBasePojo.basePerineumState ==2 }">
											裂伤
											</c:if>
											<c:if test="${birthBasePojo.basePerineumState != '' and birthBasePojo.basePerineumState != null }">
												<c:if test="${birthBasePojo.basePerineumStitching != '' and birthBasePojo.basePerineumStitching != null}">，</c:if>
											</c:if>
											<c:if test="${birthBasePojo.basePerineumStitching != '' and birthBasePojo.basePerineumStitching != null}">
												缝合${birthBasePojo.basePerineumStitching }针
											</c:if>
										</font>
									</td>
									<td class='text-right active' style="width: 10%">胎盘</td>
									<td>
											<c:if test="${birthBasePojo.basePerineumPlacenta == 1 }">手剥</c:if>
											<c:if test="${birthBasePojo.basePerineumPlacenta == 2 }">沾水</c:if>
											<c:if test="${birthBasePojo.basePerineumPlacenta == 3 }">自然脱落</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">第一产程</td>
									<td>${birthBasePojo.baseChildBirthFist }</td>
									<td class='text-right active' style="width: 10%">第二产程</td>
									<td>${birthBasePojo.baseChildBirthSecond }</td>
									<td class='text-right active' style="width: 10%">第三产程</td>
									<td>${birthBasePojo.baseChildBirthThrid}</td>
									<td class='text-right active' style="width: 10%">总产程</td>
									<td>${birthBasePojo.baseChildBirthSum }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">手术</td>
									<td colspan="7">
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '1') != -1 }">
											<font color="red">引产
												<c:if test="${birthBasePojo.baseSurgeryDetail == 1}">（改良药物）</c:if>
												<c:if test="${birthBasePojo.baseSurgeryDetail == 2}">（剥膜）</c:if>
												<c:if test="${birthBasePojo.baseSurgeryDetail == 3}">（点滴）</c:if>
												<c:if test="${birthBasePojo.baseSurgeryDetail == 4}">（破膜）</c:if>
												<c:if test="${birthBasePojo.baseSurgeryDetail == 5}">（其他）</c:if>
											</font>
										</c:if>
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '2') != -1 && fn:indexOf(birthBasePojo.baseSurgeryType, '2') != 0}">，</c:if>										
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '2') != -1 }">产后刮宫</c:if>
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '3') != -1 && fn:indexOf(birthBasePojo.baseSurgeryType, '3') != 0 }">，</c:if>										
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '3') != -1 }">手转胎头</c:if>
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '4') != -1 && fn:indexOf(birthBasePojo.baseSurgeryType, '4') != 0 }">，</c:if>										
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '4') != -1 }">点滴加强</c:if>
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '5') != -1 && fn:indexOf(birthBasePojo.baseSurgeryType, '5') != 0 }">，</c:if>										
										<c:if test="${fn:indexOf(birthBasePojo.baseSurgeryType, '5') != -1 }">
											其他
											<c:if test="${birthBasePojo.baseSurgeryDetail2 != null and birthBasePojo.baseSurgeryDetail2 != ''}">（${birthBasePojo.baseSurgeryDetail2 }）</c:if>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">产前检查</td>
									<td>
										<c:if test="${birthBasePojo.baseComplicationPrenatalCal == 1}">有</c:if>
										<c:if test="${birthBasePojo.baseComplicationPrenatalCal == 0}">无</c:if>
									</td>
									<td class='text-right active' style="width: 10%">中度贫血HB小于90g/L</td>
									<td>
										<c:if test="${birthBasePojo.baseComplicationAnemia == 1}">是</c:if>
										<c:if test="${birthBasePojo.baseComplicationAnemia == 0}">否</c:if>
									</td>
									<td class='text-right active' style="width: 10%">妊娠高血压疾病</td>
									<td>
										<c:if test="${birthBasePojo.baseComplicationHypertension == 1}">是</c:if>
										<c:if test="${birthBasePojo.baseComplicationHypertension == 0}">否</c:if>
									</td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">产前合并症</td>
									<td colspan="7">${birthBasePojo.baseComplicationPrenatal }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">产时并发症</td>
									<td colspan="7">${birthBasePojo.baseComplicationBirthing }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">产后并发症</td>
									<td colspan="7">${birthBasePojo.baseComplicationAfterBirthing }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">内科合并症</td>
									<td colspan="7">${birthBasePojo.baseComplicationsMedical }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">传染病检测情况</td>
									<td colspan="7">${birthBasePojo.baseComplicationDisease }</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%"><font color="red">产后收缩压</font></td>
									<td>
										<c:if test="${birthBasePojo.baseAfterBirthingSsy != null}">${birthBasePojo.baseAfterBirthingSsy }mmHg</c:if>
									</td>
									<td class='text-right active' style="width: 10%"><font color="red">产后舒张压</font></td>
									<td>
										<c:if test="${birthBasePojo.baseAfterBirthingSzy != null}">${birthBasePojo.baseAfterBirthingSzy }mmHg</c:if>
									</td>
									<td class='text-right active' style="width: 10%">开奶时间</td>
									<td>
										<c:if test="${birthBasePojo.baseAfterBirthingBreastMilkl != null && birthBasePojo.baseAfterBirthingBreastMilkl != ''}">产后${birthBasePojo.baseAfterBirthingBreastMilkl}</c:if>
									</td>
									<td class='text-right active' style="width: 10%">产妇结局</td>
									<td>
										<c:if test="${birthBasePojo.baseMaterEndingLiveOrDeath == 1}"><font color="red">存活</font></c:if>
										<c:if test="${birthBasePojo.baseMaterEndingLiveOrDeath == 2}">
											<font color="red">
												<c:if test="${birthBasePojo.baseBirthBirthingDetail == 1}">产时</c:if>
												<c:if test="${birthBasePojo.baseBirthBirthingDetail == 2}">产后</c:if>
											死亡</font>
										</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">活产数</td>
									<td>${birthBasePojo.baseBirthEndingLiveBirths }</td>
									<td class='text-right active' style="width: 10%">死胎数</td>
									<td>${birthBasePojo.baseBirthEndingDeathBabys }</td>
									<td class='text-right active' style="width: 10%">死产数</td>
									<td>${birthBasePojo.baseBirthEndingDeathBirths}</td>
									<td class='text-right active' style="width: 10%">围产儿数</td>
									<td>${birthBasePojo.baseBirthEndingPerinatal }</td>
								</tr>
								<tr>
									<td colspan="3" class='text-right active' style="width: 10%">孕28周前双/多胎宫内死亡胎数</td>
									<td>${birthBasePojo.baseDeathBefor28 }</td>
									<td colspan="3" class='text-right active' style="width: 10%">孕28周前双/多胎宫内死亡原因</td>
									<td>
										<c:if test="${birthBasePojo.baseDeathReasonBefor28 == 1}">胎停育</c:if>
										<c:if test="${birthBasePojo.baseDeathReasonBefor28 == 2}">治疗性减胎</c:if>
										<c:if test="${birthBasePojo.baseDeathReasonBefor28 == 3}">其他</c:if>
									</td>
								</tr>
								<tr>
									<td class='text-right active' style="width: 10%">备注</td>
									<td colspan="7">${birthBasePojo.baseRemark }</td>
								</tr>
							</table>
						</div>
					</div>
						<c:forEach var="baby" items="${birthBabyList }" varStatus="status">
							<c:if test="${baby.babyIsDeath != 1}">
								<div class="panel panel-lightblue">
									<div class="panel-heading text-left">
										<i class="fa fa-tag fa-fw"></i>新生儿${status.count }情况
									</div>
									<div class="table-responsive">
										<table summary="新生儿情况" class="table table-bordered">
											<tr>
												<td class='text-right active' style="width: 10%">性别</td>
												<td style="width: 17%">
													<c:if test="${baby.babyGender eq 1}">男</c:if>
													<c:if test="${baby.babyGender eq 2}">女</c:if>
													<c:if test="${baby.babyGender eq 3}">不明</c:if>
												</td>
												<td class='text-right active' style="width: 10%">出生日期</td>
												<td style="width: 17%"><fmt:formatDate value="${baby.babyBirthTime }" pattern="yyyy年MM月dd日" />  &nbsp;
													<c:if test="${baby.babyBirthTimeHour != null }">${baby.babyBirthTimeHour }时 &nbsp; </c:if>
													<c:if test="${baby.babyBirthTimeHour == null and baby.babyBirthTimeMinutes != null}">0时 &nbsp; </c:if>
													<c:if test="${baby.babyBirthTimeMinutes != null }">${baby.babyBirthTimeMinutes }分</c:if>
												</td>
												<td class='text-right active' style="width: 10%">出生缺陷</td>
												<td style="width: 12.5%">${baby.babyDefect}</td>
												<td class='text-right active' style="width: 10%">抢救</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyRescue ==1}">有</c:if>
													<c:if test="${baby.babyRescue ==0}">无</c:if>
												</td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">身长</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyLength != null }">${baby.babyLength}cm</c:if>
												</td>
												<td class='text-right active' style="width: 10%">体重</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyWeight != null }">${baby.babyWeight}g</c:if>
												</td>
												<td class='text-right active' style="width: 10%">头围</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyHeadCircum != null }">${baby.babyHeadCircum}cm</c:if>
												</td>
												<td class='text-right active' style="width: 10%">胸围</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyBust != null }">${baby.babyBust}cm</c:if>
												</td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">阿氏评分1分钟</td>
												<td style="width: 12.5%"><c:if test="${baby.babyAshesOneMinute != null}">${baby.babyAshesOneMinute}分</c:if></td>
												<td class='text-right active' style="width: 10%">阿氏评分5分钟</td>
												<td style="width: 12.5%"><c:if test="${baby.babyAshesFiveMinutes != null}">${baby.babyAshesFiveMinutes}分</c:if></td>
												<td class='text-right active' style="width: 10%">阿氏评分10分钟</td>
												<td style="width: 12.5%"><c:if test="${baby.babyAshesTenMinutes != null}">${baby.babyAshesTenMinutes}分</c:if></td>
												<td class='text-right active' style="width: 10%">新生儿窒息</td>
												<td style="width: 12.5%"><c:if test="${baby.babyStifle != null}">${baby.babyStifle }分钟</c:if></td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">并发症</td>
												<td colspan="7">${baby.babyComplication }</td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">新生儿指导</td>
												<td colspan="7">${baby.babyGuid }</td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">胎盘重</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyPlacentaWeight != null}">${baby.babyPlacentaWeight }g</c:if></td>
												<td class='text-right active' style="width: 10%">胎盘长</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyPlacentaLength != null}">${baby.babyPlacentaLength }cm</c:if></td>
												</td>
												<td class='text-right active' style="width: 10%">胎盘宽</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyPlacentaWidth != null}">${baby.babyPlacentaWidth }cm</c:if></td>
												</td>
												<td class='text-right active' style="width: 10%">胎盘厚</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyPlacentaThick != null}">${baby.babyPlacentaThick }cm</c:if></td>
												</td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">羊水量</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyAmnioticFluidVol == 1}">少</c:if>
													<c:if test="${baby.babyAmnioticFluidVol == 2}">中</c:if>
													<c:if test="${baby.babyAmnioticFluidVol == 3}">多</c:if>
												</td>
												<td class='text-right active' style="width: 10%">羊水性状</td>
												<td style="width: 12.5%">
													<c:if test="${baby.babyAmnioticFluidTraits == 2}">清</c:if>
													<c:if test="${baby.babyAmnioticFluidTraits == 1}">浊</c:if>
												</td>
												<td class='text-right active' style="width: 10%"></td>
												<td style="width: 12.5%"></td>
												<td class='text-right active' style="width: 10%"></td>
												<td style="width: 12.5%"></td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%"><font color="red">备注</font></td>
												<td colspan="7">${baby.babyRemark }</td>
											</tr>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${baby.babyIsDeath == 1 }">
								<div class="panel panel-lightblue">
									<div class="panel-heading text-left">
										<i class="fa fa-tag fa-fw"></i>新生儿${status.count }死亡
									</div>
									<div class="table-responsive">
										<table summary=" 新生儿死亡" class="table table-bordered">
											<tr>
												<td class='text-right active' style="width: 10%">死亡时间</td>
												<td colspan="7"><fmt:formatDate value="${baby.babyDeathTime }" pattern="yyyy年MM月dd日" /> &nbsp;
													<c:if test="${baby.babyDeathTimeHour != null}">${baby.babyDeathTimeHour }时&nbsp;</c:if>
													<c:if test="${baby.babyDeathTimeMinutes != null}">${baby.babyDeathTimeMinutes }分&nbsp;</c:if>
												</td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 10%">死亡原因</td>
												<td colspan="7">${baby.babyAmnioticFluidDeathReason }</td>
											</tr>
										</table>
									</div>
								</div>
							</c:if>
							</c:forEach>
							<div class="panel panel-lightblue">
								<div class="panel-heading text-left">
									<i class="fa fa-tag fa-fw"></i>出院诊断
								</div>
								<div class="table-responsive">
									<table summary=" 出院诊断" class="table table-bordered">
										<c:if test="${empty dischargedList }">
											<tr>
												<td class='text-right active' style="width: 11%">母亲出院诊断</td>
												<td colspan="4"></td>
												<td class='text-right active' style="width: 11%">产后血红蛋白</td>
												<td colspan=""></td>
											</tr>
											<tr>
												<td class='text-right active' style="width: 11%">备注</td>
												<td colspan="7"></td>
											</tr>
										</c:if>
										<c:if test="${!empty dischargedList }">
											<c:forEach var="dischargedPojo" items="${dischargedList }" varStatus="disStatus">
												<c:if test="${disStatus.count == 1 }">
													<tr>
														<td class='text-right active' style="width: 11%">母亲出院诊断</td>
														<td colspan="4">${dischargedPojo.disMotherDisagnosis }</td>
														<td class='text-right active' style="width: 11%">产后血红蛋白</td>
														<td colspan="">
															<c:if test="${dischargedPojo.disHemoglobin != null }">${dischargedPojo.disHemoglobin }&nbsp;g/L</c:if>
														</td>
													</tr>
												</c:if>
											</c:forEach>
											<c:forEach var="baby" items="${birthBabyList }" varStatus="status">
												<c:forEach var="dischargedPojo" items="${dischargedList }" varStatus="disStatus">
													<c:if test="${baby.babyId eq dischargedPojo.babyId}">
														<tr>
															<td class='text-right active' style="width: 11%">新生儿${status.count }出院诊断</td>
															<td colspan="4">${dischargedPojo.disBabyDiagnosis }</td>
															<td class='text-right active' style="width: 11%">新生儿血糖</td>
															<td colspan="">
																<c:if test="${dischargedPojo.disBabyBloodSuger != null }">${dischargedPojo.disBabyBloodSuger }&nbsp;mg/dl</c:if>
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</c:forEach>
											<c:forEach var="dischargedPojo" items="${dischargedList }" varStatus="disStatus">
												<c:if test="${disStatus.count == 1 }">
													<tr>
														<td class='text-right active' style="width: 11%">备注</td>
														<td colspan="7">${dischargedPojo.disRemark }</td>
													</tr>
												</c:if>
											</c:forEach>
										</c:if>
									</table>
								</div>
							</div>
					</div>
			</div>
		</li>
	</ul>
</div>
</body>
</html>
