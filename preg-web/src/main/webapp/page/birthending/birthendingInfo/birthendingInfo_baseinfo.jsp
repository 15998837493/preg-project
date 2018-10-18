<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_main.css" />
<script charset="UTF-8" src="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_validate.js"></script>
<script charset="UTF-8" src="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_baseinfo.js"></script>
<body>
	<!-- 分娩信息 -->
	<div class="col-xs-1 mytitle-left"></div>
	<div class="col-xs-11 mytitle-right"><i class="fa fa-tag fa-fw"></i><span>分娩信息</span></div>
	<div class="panel panel-lightblue col-xs-12 padding-zero" id="birthBaseInfo_div">
	<div class="table-responsive" id="birthBaseInfo_info_div">
		<form id="birthBaseInfoForm" action="" method="post">
			<input type="hidden" id="baseId" name="baseId" />
			<input type="hidden" id="lmpId" name="baseLmp"/>
			<input type="hidden" id="base_birthId" name="birthId"/>
			<table id="bodyStatusTable" class="table table-bordered table-padding-4 no-bottom">
				<tr>
					<td class="thirdTitle">基本情况</td>
					<td>
						<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>分娩时间</span></div>
									<div class="div-table-cell" style="width: 35%;"><input style="background-color: white;" id="baseTime" name="baseTime" type="text" class="intake-input form-control" placeholder="请选择日期" readonly onclick="common.dateShow('baseTime')" /></div>
									<div class="div-table-cell"><input type="text" id="baseTimeHour" name="baseTimeHour" class="intake-sm" onkeyup="checkHourMinutes(this,'hour');" placeholder="时"></div>
									<div class="div-table-cell"><input type="text" id="baseTimeMinuters" name="baseTimeMinuters" class="intake-sm" onkeyup="checkHourMinutes(this,'minuters');" placeholder="分"></div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>分娩孕周数</span></div>
									<input type="text" id="baseWeeks" maxlength="5" name="baseWeeks" class="intake-sm" onchange="validWeekNum(this)" />
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span id="birthAge_span">分娩年龄</span></div>
									<input id="birthAge" name="birthAge" maxlength="3" type="text" class="intake-sm" onkeyup="checkNum(this);" />
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>危重孕产妇</span></div>
									<select id="baseIscritical" name="baseIscritical" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>分娩方式</span></div>
									<div class="div-table-cell" style="width: 30%;">
										<select id="baseBirthType" name="baseBirthType" class="intake-sm">
											<option value="">请选择</option>
											<option value="1">自然分娩</option>
											<option value="2">吸引</option>
											<option value="3">产钳</option>
											<option value="4">臀助产</option>
											<option value="5">剖宫产</option>
											<option value="6">其他</option>
										</select>
									</div>
									<div class="div-table-cell">
										<input type="text" id="baseBirthType2" maxlength="60" name="baseBirthType2" class="intake-sm"/>
									</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>剖宫产指征</span></div>
									<input type="text" id="basePgcIndication" maxlength="80" name="basePgcIndication" class="intake-sm" />
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>分娩方位</span></div>
									<input type="text" id="baseBirthDirection" maxlength="30" name="baseBirthDirection" class="intake-sm"/>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>麻醉方式</span></div>
									<select id="baseHocusType" name="baseHocusType" class="intake-sm" style="display: inline-block;">
										<option value="">请选择</option>
										<option value="1">局部麻醉</option>
										<option value="2">全身麻醉</option>
										<option value="3">椎管内麻醉</option>
									</select>
								</div>
							</div>
						</div>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>分娩前体重</span></div>
									<input type="text" id="baseWeightBeforeBirth" name="baseWeightBeforeBirth" class="intake-input" onkeyup="checkNumPoint(this);"/>
									<div class="input-group-addon">kg</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>分娩后体重</span></div>
									<input type="text" id="baseWeightAfterBirth" name="baseWeightAfterBirth" class="intake-input" onkeyup="checkNumPoint(this);"/>
									<div class="input-group-addon">kg</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>孕期总体重增长</span></div>
									<input type="text" id="baseGrowthPregnancying" name="baseGrowthPregnancying" class="intake-input" onkeyup="checkNumPoint(this);"/>
									<div class="input-group-addon">kg</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">产程时长</td>
					<td>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left">第一产程</div>
									<input type="text" id="baseChildBirthFist_hour" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon first-addon">时</div>
									<div class="input-group-addon addon-center"></div>
									<input type="hidden" id="baseChildBirthFist" name="baseChildBirthFist"/>
									<input type="text" id="baseChildBirthFist_minutes" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon">分</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>第二产程</span></div>
									<input type="text" id="baseChildBirthSecond_hour" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon first-addon">时</div>
									<div class="input-group-addon addon-center"></div>
									<input type="hidden" id="baseChildBirthSecond" name="baseChildBirthSecond"/>
									<input type="text" id="baseChildBirthSecond_minutes" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon">分</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>第三产程</span></div>
									<input type="text" id="baseChildBirthThrid_hour" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon first-addon">时</div>
									<div class="input-group-addon addon-center"></div>
									<input type="hidden" id="baseChildBirthThrid" name="baseChildBirthThrid"/>
									<input type="text" id="baseChildBirthThrid_minutes" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon">分</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>总产程</span></div>
									<input type="text" id="baseChildBirthSum_hour" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon first-addon">时</div>
									<div class="input-group-addon addon-center"></div>
									<input type="hidden" id="baseChildBirthSum" name="baseChildBirthSum"/>
									<input type="text" id="baseChildBirthSum_minutes" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon">分</div>
								</div>
							</div>																					
						</div>
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">出血量</td>
					<td>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>产时出血量</span></div>
									<input type="text" maxlength="6" id="baseBloodVolBirthing" name="baseBloodVolBirthing" class="intake-input" onkeyup="checkNum(this,'AmnioticFluid');"/>
									<div class="input-group-addon">ml</div>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>产后两小时出血量</span></div>
									<input type="text" maxlength="6" id="baseBloodVolAfterBirth" name="baseBloodVolAfterBirth" class="intake-input" onkeyup="checkNum(this,'AmnioticFluid');"/>
									<span class="input-group-addon">ml</span>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>总出血量</span></div>
									<input type="text" maxlength="6" id="baseBloodVolSum" name="baseBloodVolSum" class="intake-input" onkeyup="checkNum(this,'AmnioticFluid');"/>
									<span class="input-group-addon">ml</span>
								</div>
							</div>																										
						</div>					
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">会阴及胎盘</td>
					<td>
						<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>阴检</span></div>
									<input type="text" maxlength="3" id="basePerineumCheckTimes" name="basePerineumCheckTimes" class="intake-input" onkeyup="checkNum(this,'AmnioticFluid');"/>
									<span class="input-group-addon">次</span>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>会阴情况</span></div>
									<select id="basePerineumState" name="basePerineumState" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">完整</option>
										<option value="2">裂伤</option>
										<option value="3">切开</option>
									</select>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left redClass"><span>会阴裂伤程度</span></div>
									<select id="basePerineumHurt" name="basePerineumHurt" class="form-control intake-sm">
										<option value="">请选择</option>
										<option value="1">Ⅰ°</option>
										<option value="2">Ⅱ°</option>
										<option value="3">Ⅲ°</option>
									</select>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>缝合</span></div>
									<input maxlength="5" type="text" id="basePerineumStitching" name="basePerineumStitching" class="intake-input" onkeyup="checkNum(this,'AmnioticFluid');"/>
									<span class="input-group-addon">针</span>
								</div>
							</div>							
						</div>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>胎盘</span></div>
									<select id="basePerineumPlacenta" name="basePerineumPlacenta" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">手剥</option>
										<option value="2">沾水</option>
										<option value="3">自然脱落</option>
									</select>
								</div>
							</div>						
						</div>
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">手术</td>
					<td>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">     	
		    						<label class="input-group-addon addon-left"><input type="checkbox" name="baseSurgeryType" id="baseSurgeryType_check_1" value="1"/> 引产</label>
		    						<select id="baseSurgeryDetail" name="baseSurgeryDetail" class="intake-sm form-control">
										<option value="">请选择</option>
										<option value="1">改良药物</option>
										<option value="2">剥膜</option>
										<option value="3">点滴</option>
										<option value="4">破膜</option>
										<option value="5">其他</option>
									</select>
								</div>
							</div>
							<div class="col-xs-1 padding-zero">
								<div class="input-group">
		    						<label class="input-group-addon addon-checkbox">
		    							<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_2" value="2"> 产后刮宫
		    						</label>
								</div>
							</div>
							<div class="col-xs-1 padding-zero">
								<div class="input-group">
	    							<label class="input-group-addon addon-checkbox">
	    								<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_3" value="3"> 手转胎头
	    							</label>
								</div>
							</div>
							<div class="col-xs-1 padding-zero">
								<div class="input-group">
	    							<label class="input-group-addon addon-checkbox">
	    								<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_4" value="4"> 点滴加强
	    							</label>
								</div>
							</div>
							<div class="col-xs-6 padding-zero">
								<div class="input-group">
	    							<label class="input-group-addon addon-left">
	    								<input type="checkbox" name = "baseSurgeryType" id="baseSurgeryType_check_5" value="5"> 其他
	    							</label>
									<input type="text" maxlength="50" id="baseSurgeryDetail2" name="baseSurgeryDetail2" class="intake-sm" placeholder="请输入内容50个字符以内"/>
								</div>
							</div>								
						</div>
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">并发症</td>
					<td>
						<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>产前检查</span></div>
									<select id="baseComplicationPrenatalCal" name="baseComplicationPrenatalCal" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">有</option>
										<option value="0">无</option>
									</select>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>中度贫血HB小于90g/L</span></div>
									<select id="baseComplicationAnemia" name="baseComplicationAnemia" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>妊娠高血压疾病</span></div>
									<select id="baseComplicationHypertension" name="baseComplicationHypertension" class="intake-sm">
										<option value="">请选择</option>
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
								</div>
							</div>																										
						</div>
						<div class="col-xs-12 form-inline padding-zero" id="baseComplicationPrenatal_div" style="margin-bottom: 5px;">
							<div class="col-xs-3 input-group padding-zero">
								<div class="input-group-addon addon-left" style="width: 82px;">产前合并症</div>
								<input type='hidden' id='baseComplicationPrenatal_hidden' name="baseComplicationPrenatal"/>
								<input type="text" id="baseComplicationPrenatal" class="intake-sm" placeholder="请输入疾病名称" />
							</div>
						</div>
						<div class="col-xs-12 form-inline padding-zero" id="baseComplicationBirthing_div" style="margin-bottom: 5px;">
							<div class="col-xs-3 input-group padding-zero">
								<div class="input-group-addon addon-left" style="width: 82px;">产时并发症</div>
								<input type='hidden' id='baseComplicationBirthing_hidden' name="baseComplicationBirthing"/>
								<input type="text"id="baseComplicationBirthing" class="intake-sm" placeholder="请输入疾病名称" />
							</div>
						</div>
						<div class="col-xs-12 form-inline padding-zero" id="baseComplicationAfterBirthing_div" style="margin-bottom: 5px;">
							<div class="col-xs-3 input-group padding-zero">
								<div class="input-group-addon addon-left" style="width: 82px;">产后并发症</div>
								<input type='hidden' id='baseComplicationAfterBirthing_hidden' name="baseComplicationAfterBirthing" />
								<input type="text" id="baseComplicationAfterBirthing" class="intake-sm" placeholder="请输入疾病名称" />
							</div>
						</div>
						<div class="col-xs-12 form-inline padding-zero" id="baseComplicationsMedical_div" style="margin-bottom: 5px;">
							<div class="col-xs-3 input-group padding-zero">
								<div class="input-group-addon addon-left" style="width: 82px;">内科合并症</div>
								<input type='hidden' id='baseComplicationsMedical_hidden' name="baseComplicationsMedical"/>
								<input type="text" id="baseComplicationsMedical" class="intake-sm" placeholder="请输入疾病名称" />
							</div>
						</div>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 input-group padding-zero">
								<div class="input-group-addon addon-left" style="width: 82px;">传染病检测情况</div>
								<input type="text" id="baseComplicationDisease" maxlength="50" name ="baseComplicationDisease" class="intake-sm" />
							</div>
						</div>																									
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">产后情况</td>
					<td>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>收缩压</span></div>
									<input type="text" id="baseAfterBirthingSsy" name="baseAfterBirthingSsy" class="intake-input" onkeyup="checkNum(this,'ssy','3');"/>
									<span class="input-group-addon">mmHg</span>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>舒张压</span></div>
									<input type="text" id="baseAfterBirthingSzy" name="baseAfterBirthingSzy" class="intake-input" onkeyup="checkNum(this,'szy','3');"/>
									<span class="input-group-addon">mmHg</span>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>开奶时间  产后</span></div>
									<input type="text" id="baseAfterBirthingBreastMilkl_hour" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon first-addon">时</div>
									<div class="input-group-addon addon-center"></div>
									<input type="hidden" id="baseAfterBirthingBreastMilkl" name="baseAfterBirthingBreastMilkl" />
									<input type="text" id="baseAfterBirthingBreastMilkl_minutes" class="intake-input" onkeyup="checkHourMinutes(this,'minuters');"/>
									<div class="input-group-addon first-addon">分</div>
								</div>
							</div>																										
						</div>
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">产妇结局</td>
					<td>
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<label class="input-group-addon addon-left">
										<input type="radio" id="baseMaterEndingLive" name="baseMaterEndingLiveOrDeath" checked="checked" value="1"> 存活
									</label>
									<label class="input-group-addon addon-center" style="padding: 6px;">
										<input type="radio" id="baseMaterEndingDeath" name = "baseMaterEndingLiveOrDeath" value="2" > 死亡
									</label>
									<select id="baseBirthBirthingDetail" name="baseBirthBirthingDetail" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">产时</option>
										<option value="2">产后</option>
									</select>
								</div>
							</div>
						</div>		
					</td>
				</tr>
				<tr>
					<td class="thirdTitle">分娩结局</td>
					<td>
						<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>活产数</span></div>
									<input type="text" id="baseBirthEndingLiveBirths" name="baseBirthEndingLiveBirths" class="intake-sm" maxlength="1" onkeyup="checkNum(this,'AmnioticFluid');"/>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>死胎数</span></div>
									<input type="text" id="baseBirthEndingDeathBabys" name="baseBirthEndingDeathBabys" class="intake-sm" maxlength="1" onkeyup="checkNum(this,'AmnioticFluid');"/>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>死产数</span></div>
									<input type="text" id="baseBirthEndingDeathBirths" name="baseBirthEndingDeathBirths" class="intake-sm" maxlength="1" onkeyup="checkNum(this,'AmnioticFluid');"/>
								</div>
							</div>
							<div class="col-xs-3 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>围产儿数</span></div>
									<input type="text" id="baseBirthEndingPerinatal" name="baseBirthEndingPerinatal" class="intake-sm" maxlength="1" onkeyup="checkNum(this,'AmnioticFluid');"/>
								</div>
							</div>																									
						</div>
						<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
							<div class="col-xs-4 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>孕28周前双/多胎宫内死亡胎数</span></div>
									<input type="text" id="baseDeathBefor28" name="baseDeathBefor28" class="intake-sm" maxlength="1" onkeyup="checkNum(this,'AmnioticFluid');"/>
								</div>
							</div>
						</div>	
						<div class="col-xs-12 padding-zero">
							<div class="col-xs-4 padding-zero">
								<div class="input-group">
									<div class="input-group-addon addon-left"><span>孕28周前双/多胎宫内死亡原因</span></div>
									<select id="baseDeathReasonBefor28" name="baseDeathReasonBefor28" class="intake-sm">
										<option value="">请选择</option>
										<option value="1">胎停育</option>
										<option value="2">治疗性减胎</option>
										<option value="3">其他</option>
									</select>
								</div>
							</div>							
						</div>	
					 </td>
				 </tr>
				<tr>
					<td class="thirdTitle">备注</td>
					<td>
						<textarea id="baseRemark" name="baseRemark" class="form-control" placeholder="点击输入内容" maxlength="100" ></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
  </div>
</body>
</html>
