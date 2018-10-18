var aliveBaby = 0;
function newBaby(babyInfo,isDeath,i){
	// tittle
	var tittleArray = $("div[id^=newBaby_tittle_]");
	var tittleNumber = parseInt(tittleArray.length) + 1;
	
	if(i == undefined){
		i = $("#tempI").val() == '' ? 1 : parseInt($("#tempI").val())+1;
	}
	$("#tempI").val(i);
	
	var tr = "";
	var discharge_tr ="";
	var babyId = "babyId"+i;
	var disBabyDiagnosisId = "disBabyDiagnosis"+i;
	var disBabyDiagnosis_hiddenId = "disBabyDiagnosis"+i+"_hidden";
	var disBabyDiagnosis_divId = "disBabyDiagnosis_div"+i;
	var disBaby_Id = "disBaby_hidden"+i;
	var disId_Id = "disId_Id"+i;
	
	var babyBirthTimeId = "babyBirthTime_"+i;
	var babyDeathTimeId = "babyDeathTime_"+i;
	var babyComplication_hiddenId = "babyComplication_"+i+"_hidden";
	var babyComplicationId = "babyComplication_"+i;
	var babyComplication_divId = "babyComplication_div_"+i;
	var babyGenderId = "babyGender_"+i;
	var babyRescueId = "babyRescue_"+i;
	var babyAmnioticFluidVolId = "babyAmnioticFluidVol_"+i;
	var babyAmnioticFluidTraitsId = "babyAmnioticFluidTraits_"+i;
	var newBabyId = "newBaby_tittle_"+i;
	var birthBabyInfo_info_divId = "birthBabyInfo_info_div_"+i;
	
	
	if(babyInfo != undefined){
		var babyInfoId = babyInfo.babyId == undefined ? '' : babyInfo.babyId;
		var birthId = babyInfo.birthId == undefined ? '' : babyInfo.birthId;
		var babyBirthTime = babyInfo.babyBirthTime == undefined ? '' : babyInfo.babyBirthTime;
		var babyBirthTimeHour = babyInfo.babyBirthTimeHour == undefined ? '' : babyInfo.babyBirthTimeHour;
		var babyBirthTimeMinutes = babyInfo.babyBirthTimeMinutes == undefined ? '' : babyInfo.babyBirthTimeMinutes;
		var babyLength = babyInfo.babyLength == undefined ? '' : babyInfo.babyLength;
		var babyWeight = babyInfo.babyWeight == undefined ? '' : babyInfo.babyWeight;
		var babyHeadCircum = babyInfo.babyHeadCircum == undefined ? '' : babyInfo.babyHeadCircum;
		var babyBust = babyInfo.babyBust == undefined ? '' : babyInfo.babyBust;
		var babyAshesOneMinute = babyInfo.babyAshesOneMinute == undefined ? '' : babyInfo.babyAshesOneMinute;
		var babyAshesFiveMinutes = babyInfo.babyAshesFiveMinutes == undefined ? '' : babyInfo.babyAshesFiveMinutes;
		var babyAshesTenMinutes = babyInfo.babyAshesTenMinutes == undefined ? '' : babyInfo.babyAshesTenMinutes;
		var babyStifle = babyInfo.babyStifle == undefined ? '' : babyInfo.babyStifle;
		var babyDefect = babyInfo.babyDefect == undefined ? '' : babyInfo.babyDefect;
		var babyGuid = babyInfo.babyGuid == undefined ? '' : babyInfo.babyGuid;
		var babyPlacentaWeight = babyInfo.babyPlacentaWeight == undefined ? '' : babyInfo.babyPlacentaWeight;
		var babyPlacentaLength = babyInfo.babyPlacentaLength == undefined ? '' : babyInfo.babyPlacentaLength;
		var babyPlacentaWidth = babyInfo.babyPlacentaWidth == undefined ? '' : babyInfo.babyPlacentaWidth;
		var babyPlacentaThick = babyInfo.babyPlacentaThick == undefined ? '' : babyInfo.babyPlacentaThick;
		var babyRemark = babyInfo.babyRemark == undefined ? '' : babyInfo.babyRemark;
		var babyAmnioticFluidDeathReason = babyInfo.babyAmnioticFluidDeathReason == undefined ? '' : babyInfo.babyAmnioticFluidDeathReason;
		var babyDeathTime = babyInfo.babyDeathTime == undefined ? '' : babyInfo.babyDeathTime;
		var babyDeathTimeHour = babyInfo.babyDeathTimeHour == undefined ? '' : babyInfo.babyDeathTimeHour;
		var babyDeathTimeMinutes = babyInfo.babyDeathTimeMinutes == undefined ? '' : babyInfo.babyDeathTimeMinutes;
		
		if($("input[name='newBaby']:checked").val() == '1' || isDeath != '1'){ // 编辑存活新生儿
			tr+=`
		<div class="panel-heading text-left" id="`+newBabyId+`">
			<i class="fa fa-tag fa-fw"></i> <span>新生儿`+tittleNumber+`情况</span>
			<a><span style="float: right;color: white; margin-right:5px;" onclick="removeBaby('`+newBabyId+`','`+birthBabyInfo_info_divId+`','discharge_newbaby`+i+`')">X</span></a> 
		</div>
		<div class="table-responsive" id="`+birthBabyInfo_info_divId+`">
			<form action="" method="post" class="form-inline no-bottom">
				<input type="hidden" id="` + babyId + `" name="c_babyId" value="` + babyInfo.babyId + `"/>
				<input type="hidden" name="baby_birthId" value="`+babyInfo.birthId+`"/>
				<table id="babyTable" class="table table-bordered table-padding-4 no-bottom">
					<tr>
						<td style="width:120px;">新生儿</td>
						<td>
						<div class="div-inline">
							<span class="title-span"><font color="red">性别</font></span>
							<select id="`+babyGenderId+`" name="babyGender" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">男</option>
								<option value="2">女</option>
								<option value="3">不明</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">出生日期</font></span>
							<input id="` +babyBirthTimeId +`" name="babyBirthTime" value="`+babyBirthTime+`" type="text" class="intake-sm form_date" style="width:100px;" placeholder="请选择出生日期" readonly onclick="common.dateShow('` +babyBirthTimeId +`')" />
							<input type="text" id="babyBirthTimeHour" name="babyBirthTimeHour" value="`+ babyBirthTimeHour +`" class="intake-sm" style="width: 30px;" onkeyup="checkHourMinutes(this,'hour');"/>
							<span style="width: 20px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="babyBirthTimeMinutes" name="babyBirthTimeMinutes" value="`+babyBirthTimeMinutes+`" class="intake-sm" style="width: 30px;" onkeyup="checkHourMinutes(this,'minutes');"/>
							<span style="width: 20px; display: inline-block; text-align: center;">分 </span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">身长</font></span>
							<input type="text" id="babyLength" name="babyLength" value="`+babyLength+`" class="intake-input" onkeyup="checkNumPoint(this);" /><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">体重</font></span>
							<input type="text" id="babyWeight" name="babyWeight" value="`+babyWeight+`" class="intake-input" onkeyup="checkNum(this);"/><span class="intake-input-group-addon">g</span>
						</div>
						<div class="div-inline">
							<span class="title-span">头围</span>
							<input type="text" id="babyHeadCircum" name="babyHeadCircum" value="`+babyHeadCircum+`" class="intake-input" onkeyup="checkNumPoint(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span">胸围</span>
							<input type="text" id="babyBust" name="babyBust" value="`+babyBust+`" class="intake-input" onkeyup="checkNumPoint(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">阿氏评分</font></span>
							<font color="red">1分钟</font><input type="text" id="babyAshesOneMinute" name="babyAshesOneMinute" value="`+babyAshesOneMinute+`" class="intake-input" style="width:148px;" onkeyup="checkNum(this);"/><span class="intake-input-group-addon">分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">5分钟</font></span><input type="text" id="babyAshesFiveMinutes" name="babyAshesFiveMinutes" value="`+babyAshesFiveMinutes+`" class="intake-input" onkeyup="checkNum(this);" /><span class="intake-input-group-addon">分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">10分钟</font></span><input type="text" id="babyAshesTenMinutes" name="babyAshesTenMinutes" value="`+babyAshesTenMinutes+`" class="intake-input" onkeyup="checkNum(this);" /><span class="intake-input-group-addon">分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">新生儿窒息</font></span>
							<input type="text" id="babyStifle" name="babyStifle" value="`+babyStifle+`" class="intake-input" onkeyup="checkNum(this);"/><span class="intake-input-group-addon">分钟</span>
						</div>
						<div class="div-inline">
							<span class="title-span">出生缺陷</span>
							<input type="text" id="babyDefect" name="babyDefect" value="`+babyDefect+`" class="intake-sm"/>
						</div>
						<div class="div-inline">
							<span class="title-span">抢救</span>
							<select id="`+babyRescueId+`" name="babyRescue" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
						<div class="form-inline" id="`+babyComplication_divId+`">
							<span class="title-span">并发症</span>
							<input type="hidden" id="`+babyComplication_hiddenId+`" name="babyComplication" class='input-sm' />
							<input type="text" id="`+babyComplicationId+`"  />
						</div>
						<div style="display: block;">
							<span id="birthHospital_span" class="title-span">新生儿指导</span>
							<textarea id="babyGuid" name="babyGuid" class="form-control" placeholder="点击输入内容" maxlength="100"length="1000" style="width: 92%;">`+babyGuid+`</textarea>
						</div>
					</td>
					</tr>
					<tr>
						<td>胎盘/羊水情况</td>
						<td>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘重</font></span>
								<input type="text" id="babyPlacentaWeight" name="babyPlacentaWeight" value="`+babyPlacentaWeight+`" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">g</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘长</font></span>
								<input type="text" id="babyPlacentaLength" name="babyPlacentaLength" value="`+babyPlacentaLength+`" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘宽</font></span>
								<input type="text" id="babyPlacentaWidth" name="babyPlacentaWidth" value="`+babyPlacentaWidth+`" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘厚</font></span>
								<input type="text" id="babyPlacentaThick" name="babyPlacentaThick" value="`+babyPlacentaThick+`" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">羊水量</font></span>
								<select id="`+babyAmnioticFluidVolId+`" name="babyAmnioticFluidVol" class="intake-sm">
									<option value="">请选择</option>
									<option value="1">少</option>
									<option value="2">中</option>
									<option value="3">多</option>
								</select>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">羊水性状</font></span>
								<select id="`+babyAmnioticFluidTraitsId+`" name="babyAmnioticFluidTraits" class="intake-sm">
									<option value="">请选择</option>
									<option value="1">清</option>
									<option value="2">浊</option>
								</select>
							</div>
						</td>
					</tr>
					<div>
					<tr>
						<td>新生儿备注</td>
						<td>
							<textarea id="babyRemark" name="babyRemark" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;">`+babyRemark+`</textarea>
						</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
			`;
			
			discharge_tr+=`
				<tr id="discharge_newbaby`+i+`" data="`+i+`">
					<td>新生儿`+tittleNumber+`</td>
					<td>
						<div class="form-inline" id="`+disBabyDiagnosis_divId+`">
							<input type='hidden' id="`+disId_Id+`" name="disId" />
							<input type='hidden' id="`+disBaby_Id+`" name="dis_babyId" value="`+babyInfoId+`"/>
							<span class="title-span">出院诊断</span>
							<input type='hidden' id="`+disBabyDiagnosis_hiddenId+`" name="disBabyDiagnosis" class="input-sm"/>
							<input type="text" id="`+disBabyDiagnosisId+`" class="intake-sm" placeholder="请输入疾病" style="width:180px;" />
						</div>
						<div class="form-inline">
							<span class="title-span">新生儿血糖</span> 
							<input type="text" id="disBabyBloodSuger`+i+`" name="disBabyBloodSuger" class="intake-input" onkeyup="checkNumPoint(this);" style="width:138px;" /><span class="intake-input-group-addon">mg/dl</span>
						</div>
					</td>
				</tr>`;
		}else{ // 编辑死亡新生儿
			tr+=`
		<div class="panel-heading text-left" id="`+newBabyId+`">
			<i class="fa fa-tag fa-fw"></i> <span>新生儿`+tittleNumber+`情况</span>
			<a><span style="float: right;color: white; margin-right:5px;" onclick="removeBaby('`+newBabyId+`','`+birthBabyInfo_info_divId+`')">X</span></a>
		</div>
		
		<div class="table-responsive" id="`+birthBabyInfo_info_divId+`">
			<form action="" method="post" class="form-inline no-bottom">
				<input type="hidden" id="` + babyId + `" name="w_babyId" value="`+babyInfoId+`"/>
				<input type="hidden" name="baby_birthId" value="`+birthId+`" />
				<input type="hidden" name="babyIsDeath" value="1" />
				<table id="babyTable" class="table table-bordered table-padding-4 no-bottom">
					<tr>
						<td style="width:120px;">新生儿死亡</td>
						<td>
							<div class="div-inline">
								<span class="title-span"><font color="red">死亡时间</font></span>
								<input type="text" id="` + babyDeathTimeId + `" name="babyDeathTime" value="" class="intake-sm form_date" style="width:108px;" placeholder="请选择死亡日期" readonly onclick="common.dateShow('`+babyDeathTimeId+`')"/>
								<input type="text" id="babyDeathTimeHour" name="babyDeathTimeHour" value="`+babyDeathTimeHour+`" class="intake-sm" style="width: 30px;" onkeyup="checkNum(this);"/>
								<span style="width: 15px; display: inline-block; text-align: center;">时 </span>
								<input type="text" id="babyDeathTimeMinutes" name="babyDeathTimeMinutes" value="`+babyDeathTimeMinutes+`" class="intake-sm" style="width: 30px;" onkeyup="checkNum(this);"/>
								<span style="width: 15px; display: inline-block; text-align: center;">分 </span>
							</div>
							<div style="display: block;">
								<span id="babyAmnioticFluidDeathReason_span" class="title-span">死亡原因</span>
								<textarea id="babyAmnioticFluidDeathReason" name="babyAmnioticFluidDeathReason" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;">`+babyAmnioticFluidDeathReason+`</textarea>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
			`;
		}
		
		$("#birthBabyInfo_div").append(tr);
		$("#discharge_remark").before(discharge_tr);
		//初始化日期
		var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
		if(babyBirthTime != ''){
			common.initDate("yyyy-mm-dd",null,null,"#"+babyBirthTimeId,babyBirthTime);
			$("#"+babyBirthTimeId).val(babyBirthTime);
		}else{
			common.initDate("yyyy-mm-dd",null,null,"#"+babyBirthTimeId,nowDate);
		}
		if(babyDeathTime != ''){
			common.initDate(null,null,null,"#"+babyDeathTimeId,babyDeathTime);
			$("#"+babyDeathTimeId).val(babyDeathTime);
		}else{
			common.initDate("yyyy-mm-dd",null,null,"#"+babyDeathTimeId,nowDate);
		}
		
		//新生儿并发症
		common.autocompleteMethod(babyComplicationId, productListData, function(data){
			$("#"+babyComplicationId).val("");
			var birthDiagnosis_value = $("#"+babyComplication_hiddenId).val();
			var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
			if(birthDiagnosisLength > 5 ){
				layer.msg("最多选择6种诊断");
				$("#"+babyComplicationId).val("");
				return;
			}
			selectOperation.addSelectOperation(data.value.diseaseId,data.name,babyComplication_divId,babyComplication_hiddenId);
		});
	    
		//出院诊断_新生儿出院诊断
		common.autocompleteMethod(disBabyDiagnosisId, productListData, function(data){
			$("#"+disBabyDiagnosisId).val("");
			var birthDiagnosis_value = $("#"+disBabyDiagnosis_hiddenId).val();
			var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
			if(birthDiagnosisLength > 5 ){
				layer.msg("最多选择6种诊断");
				$("#"+disBabyDiagnosisId).val("");
				return;
			}
			selectOperation.addSelectOperation(data.value.diseaseId,data.name,disBabyDiagnosis_divId,disBabyDiagnosis_hiddenId);
		});
		
		//初始化下拉菜单
		if(babyInfo.babyComplication != undefined && babyInfo.babyComplication != ''){
			//新生儿_并发症
			var b_cyzdArray = getSelectArray(babyInfo.babyComplication,productAllMap);
			selectOperation.init(babyComplication_divId,babyComplication_hiddenId,b_cyzdArray);
			$("#"+babyComplication_hiddenId).val(babyInfo.babyComplication);
			$("#"+babyComplicationId).val("");
		}
	    if(babyInfo.babyGender != undefined){
	    	$("#"+babyGenderId).val(babyInfo.babyGender);
	    }
	    if(babyInfo.babyRescue != undefined){
	    	$("#"+babyRescueId).val(babyInfo.babyRescue);
	    }
	    if(babyInfo.babyAmnioticFluidVol != undefined){
	    	$("#"+babyAmnioticFluidVolId).val(babyInfo.babyAmnioticFluidVol);
	    }
	    if(babyInfo.babyAmnioticFluidTraits != undefined){
	    	$("#"+babyAmnioticFluidTraitsId).val(babyInfo.babyAmnioticFluidTraits);
	    }
	}else{
		if($("input[name='newBaby']:checked").val() == '1'){ // 添加存活新生儿
			tr+=`
		<div class="panel-heading text-left" id="`+newBabyId+`">
			<i class="fa fa-tag fa-fw"></i> <span>新生儿`+tittleNumber+`情况</span>
			<a><span style="float: right;color: white; margin-right:5px;" onclick="removeBaby('`+newBabyId+`','`+birthBabyInfo_info_divId+`','discharge_newbaby`+i+`')">X</span></a>
		</div>
		<div class="table-responsive" id="`+birthBabyInfo_info_divId+`">
			<form action="" method="post" class="form-inline no-bottom">
				<input type="hidden" id="` + babyId + `" name="c_babyId" />
				<input type="hidden" name="baby_birthId" value=""/>
				<table id="babyTable" class="table table-bordered table-padding-4 no-bottom">
					<tr>
						<td style="width:120px;">新生儿</td>
						<td>
						<div class="div-inline">
							<span class="title-span"><font color="red">性别</font></span>
							<select id="`+babyGenderId+`" name="babyGender" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">男</option>
								<option value="2">女</option>
								<option value="3">不明</option>
							</select>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">出生日期</font></span>
							<input id="` +babyBirthTimeId +`" name="babyBirthTime" value="" type="text" class="intake-sm form_date" style="width:100px;" placeholder="请选择出生日期" readonly onclick="common.dateShow('` +babyBirthTimeId +`')" />
							<input type="text" id="babyBirthTimeHour" name="babyBirthTimeHour" value="" class="intake-sm" style="width: 30px;" onkeyup="checkHourMinutes(this,'hour');"/>
							<span style="width: 10px; display: inline-block; text-align: center;">时 </span>
							<input type="text" id="babyBirthTimeMinutes" name="babyBirthTimeMinutes" value="" class="intake-sm" style="width: 30px;" onkeyup="checkHourMinutes(this,'minutes');"/>
							<span style="width: 10px; display: inline-block; text-align: center;">分 </span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">身长</font></span>
							<input type="text" id="babyLength" name="babyLength" value="" class="intake-input" onkeyup="checkNumPoint(this);" /><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">体重</font></span>
							<input type="text" id="babyWeight" name="babyWeight" value="" class="intake-input" onkeyup="checkNum(this);"/><span class="intake-input-group-addon">g</span>
						</div>
						<div class="div-inline">
							<span class="title-span">头围</span>
							<input type="text" id="babyHeadCircum" name="babyHeadCircum" value="" class="intake-input" onkeyup="checkNumPoint(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span">胸围</span>
							<input type="text" id="babyBust" name="babyBust" value="" class="intake-input" onkeyup="checkNumPoint(this);"/><span class="intake-input-group-addon">cm</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">阿氏评分</font></span>
							<font color="red">1分钟</font><input type="text" id="babyAshesOneMinute" name="babyAshesOneMinute" value="" class="intake-input" style="width:148px;" onkeyup="checkNum(this);"/><span class="intake-input-group-addon">分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">5分钟</font></span><input type="text" id="babyAshesFiveMinutes" name="babyAshesFiveMinutes" value="" class="intake-input" onkeyup="checkNum(this);" /><span class="intake-input-group-addon">分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">10分钟</font></span><input type="text" id="babyAshesTenMinutes" name="babyAshesTenMinutes" value="" class="intake-input" onkeyup="checkNum(this);" /><span class="intake-input-group-addon">分</span>
						</div>
						<div class="div-inline">
							<span class="title-span"><font color="red">新生儿窒息</font></span>
							<input type="text" id="babyStifle" name="babyStifle" value="" class="intake-input" onkeyup="checkNum(this);"/><span class="intake-input-group-addon">分钟</span>
						</div>
						<div class="div-inline">
							<span class="title-span">出生缺陷</span>
							<input type="text" id="babyDefect" name="babyDefect" value="" class="intake-sm"/>
						</div>
						<div class="div-inline">
							<span class="title-span">抢救</span>
							<select id="`+babyRescueId+`" name="babyRescue" class="intake-sm">
								<option value="">请选择</option>
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
						</div>
						<div class="form-inline" id="`+babyComplication_divId+`">
							<span class="title-span">并发症</span>
							<input type='hidden' id="`+babyComplication_hiddenId+`" name="babyComplication" class='input-sm' />
							<input type="text" id="`+babyComplicationId+`"  value=""/>
						</div>
						<div style="display: block;">
							<span id="birthHospital_span" class="title-span">新生儿指导</span>
							<textarea id="babyGuid" name="babyGuid" value="" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
						</div>
					</td>
					</tr>
					<tr>
						<td>胎盘/羊水情况</td>
						<td>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘重</font></span>
								<input type="text" id="babyPlacentaWeight" name="babyPlacentaWeight" value="" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">g</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘长</font></span>
								<input type="text" id="babyPlacentaLength" name="babyPlacentaLength" value="" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘宽</font></span>
								<input type="text" id="babyPlacentaWidth" name="babyPlacentaWidth" value="" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">胎盘厚</font></span>
								<input type="text" id="babyPlacentaThick" name="babyPlacentaThick" value="" class="intake-input" onkeyup="checkNumPoint2(this);"/><span class="intake-input-group-addon">cm</span>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">羊水量</font></span>
								<select id="`+babyAmnioticFluidVolId+`" name="babyAmnioticFluidVol" class="intake-sm">
									<option value="">请选择</option>
									<option value="1">少</option>
									<option value="2">中</option>
									<option value="3">多</option>
								</select>
							</div>
							<div class="div-inline">
								<span class="title-span"><font color="red">羊水性状</font></span>
								<select id="`+babyAmnioticFluidTraitsId+`" name="babyAmnioticFluidTraits" class="intake-sm">
									<option value="">请选择</option>
									<option value="1">清</option>
									<option value="2">浊</option>
								</select>
							</div>
						</td>
					</tr>
					<div>
					<tr>
						<td>新生儿备注</td>
						<td>
							<textarea id="babyRemark" name="babyRemark" class="form-control" value="" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
						</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
			`;
			
			discharge_tr+=`
				<tr id="discharge_newbaby`+i+`" data="`+i+`">
					<td>新生儿`+tittleNumber+`</td>
					<td>
						<div class="form-inline" id="`+disBabyDiagnosis_divId+`">
							<input type='hidden' id="`+disBaby_Id+`" name="dis_babyId" />
							<input type='hidden' id="`+disId_Id+`" name="disId" />
							<span class="title-span">出院诊断</span>
							<input type='hidden' id="`+disBabyDiagnosis_hiddenId+`" name="disBabyDiagnosis" class="input-sm" />
							<input type="text" id="`+disBabyDiagnosisId+`" class="intake-sm" placeholder="请输入疾病" style="width:180px;" />
						</div>
						<div class="form-inline">
							<span class="title-span">新生儿血糖</span> 
							<input type="text" id="disBabyBloodSuger`+i+`" name="disBabyBloodSuger" class="intake-input" onkeyup="checkNumPoint(this);" style="width:138px;" /><span class="intake-input-group-addon">mg/dl</span>
						</div>
					</td>
				</tr>`;
			
		}else{ //添加死亡新生儿
			tr+=`
				<div class="panel-heading text-left" id="`+newBabyId+`">
					<i class="fa fa-tag fa-fw"></i> <span>新生儿`+tittleNumber+`情况</span>
					<a><span style="float: right;color: white; margin-right:5px;" onclick="removeBaby('`+newBabyId+`','`+birthBabyInfo_info_divId+`')">X</span></a>
				</div>
				<div class="table-responsive" id="`+birthBabyInfo_info_divId+`">
					<form action="" method="post" class="form-inline no-bottom">
						<input type="hidden" id="` + babyId + `" name="w_babyId"  />
						<input type="hidden" name="baby_birthId" value="" />
						<input type="hidden" name="babyIsDeath" value="1" />
						<table id="babyTable" class="table table-bordered table-padding-4 no-bottom">
							<tr>
								<td style="width:120px;">新生儿死亡</td>
								<td>
									<div class="div-inline">
										<span class="title-span"><font color="red">死亡时间</font></span>
										<input type="text" id="` + babyDeathTimeId + `" name="babyDeathTime" value="" class="intake-sm form_date" style="width:108px;" placeholder="请选择死亡日期" readonly onclick="common.dateShow('`+babyDeathTimeId+`')"/>
										<input type="text" id="babyDeathTimeHour" name="babyDeathTimeHour" value="" class="intake-sm" style="width: 30px;" onkeyup="checkHourMinutes(this,'hour');"/>
										<span style="width: 15px; display: inline-block; text-align: center;">时 </span>
										<input type="text" id="babyDeathTimeMinutes" name="babyDeathTimeMinutes" value="" class="intake-sm" style="width: 30px;" onkeyup="checkHourMinutes(this,'minutes');"/>
										<span style="width: 15px; display: inline-block; text-align: center;">分 </span>
									</div>
									<div style="display: block;">
										<span id="babyAmnioticFluidDeathReason_span" class="title-span">死亡原因</span>
										<textarea id="babyAmnioticFluidDeathReason" name="babyAmnioticFluidDeathReason" value="" class="form-control" placeholder="点击输入内容" maxlength="100" style="width: 92%;"></textarea>
									</div>
								</td>
							</tr>
						</table>
					</form>
				</div>
					`;
				}
		
		$("#birthBabyInfo_div").append(tr);
		$("#discharge_remark").before(discharge_tr);
		var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
		
		//初始化日期
		common.initDate(null,null,null,"#"+babyBirthTimeId,nowDate);
		common.initDate(null,null,null,"#"+babyDeathTimeId,nowDate);
		
		//新生儿并发症
		common.autocompleteMethod(babyComplicationId, productListData, function(data){
			$("#"+babyComplicationId).val("");
			var birthDiagnosis_value = $("#"+babyComplication_hiddenId).val();
			var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
			if(birthDiagnosisLength > 5 ){
				layer.msg("最多选择6种诊断");
				$("#"+babyComplicationId).val("");
				return;
			}
			selectOperation.addSelectOperation(data.value.diseaseId,data.name,babyComplication_divId,babyComplication_hiddenId);
		});
	    
		//出院诊断_新生儿出院诊断
		common.autocompleteMethod(disBabyDiagnosisId, productListData, function(data){
			$("#"+disBabyDiagnosisId).val("");
			var birthDiagnosis_value = $("#"+disBabyDiagnosis_hiddenId).val();
			var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
			if(birthDiagnosisLength > 5 ){
				layer.msg("最多选择6种诊断");
				$("#"+disBabyDiagnosisId).val("");
				return;
			}
			selectOperation.addSelectOperation(data.value.diseaseId,data.name,disBabyDiagnosis_divId,disBabyDiagnosis_hiddenId);
		});
	}
	
}

/**
 * 删除新生儿
 * @param newBabyId
 * @param birthBabyInfoDivId
 * @param dischargeNewbabyId
 * @returns
 */
function removeBaby(newBabyId,birthBabyInfoDivId,dischargeNewbabyId){
	layer.confirm("确定对该内容执行【删除】操作？", function(index) {
		// 删除已保存数据
		var c_babyId = $("#"+birthBabyInfoDivId).children('form').find("input[name=c_babyId]").val();
		var w_babyId = $("#"+birthBabyInfoDivId).children('form').find("input[name=w_babyId]").val();
		var babyId = c_babyId == undefined ? w_babyId : c_babyId;
		var dischargeId =$("#"+dischargeNewbabyId).children('td').find("input[name=disId]").val(); 
		if(babyId == ""){
			// 删除页面元素
			$("#"+newBabyId).remove();
			$("#"+birthBabyInfoDivId).remove();
			if(dischargeNewbabyId != '') {$("#"+dischargeNewbabyId).remove();}
			
			// 重新生成排序
			var tittleArray = $("div[id^=newBaby_tittle_]");
			var aliveArray = new Array();
			var newbabyDis = $("tr[id^=discharge_newbaby]");
			$.each(tittleArray,function(i,tittle){
				tittle.children[1].innerHTML = "新生儿"+(i+1)+"情况";
				var isDeath = $("#birthBabyInfo_info_div_"+(i+1)).children().find("input[name='babyIsDeath']");
				if(isDeath[0] ==undefined || isDeath[0].value != '1'){
					aliveArray.push(i+1);
				}
			});
			//给诊断赋值
			$.each(newbabyDis, function(i,dis){
				dis.children[0].innerHTML = "新生儿"+(aliveArray[i]);
			});
			
			layer.msg("删除成功");
			return;
		}
		var url = URL.get("BirthEnding.BIRTHENDING_BABYINFO_DELETE");
		var params = "babyId="+babyId+"&dischargeId="+dischargeId;
		setTimeout(function() {
			ajax.post(url, params, "json", function(data) {
				if(data.value){
					// 删除页面元素
					$("#"+newBabyId).remove();
					$("#"+birthBabyInfoDivId).remove();
					if(dischargeNewbabyId != '') {$("#"+dischargeNewbabyId).remove();}
					
					// 重新生成排序
					var tittleArray = $("div[id^=newBaby_tittle_]");
					var aliveArray = new Array();
					var newbabyDis = $("tr[id^=discharge_newbaby]");
					$.each(tittleArray,function(i,tittle){
						tittle.children[1].innerHTML = "新生儿"+(i+1)+"情况";
						var isDeath = $("#birthBabyInfo_info_div_"+(i+1)).children().find("input[name='babyIsDeath']");
						if(isDeath[0] ==undefined || isDeath[0].value != '1'){
							aliveArray.push(i+1);
						}
					});
					//给诊断重新排序
					$.each(newbabyDis, function(i,dis){
						dis.children[0].innerHTML = "新生儿"+(aliveArray[i]);
					});
					layer.msg("删除成功");
				}else{
					layer.msg("删除失败");
				}
			});
		},200);
	}, function(layerIndex){
		layer.close(layerIndex);
	});
}


function archivesTr(index,data){
	var archivesTr = "";
	var createDatetime = data.createDatetime == null ? "——" : data.createDatetime;
	var encyesisSituation = data.encyesisSituation == null ? "——" : data.encyesisSituation;
	var embryoNum = data.embryoNum == null ? "——" : data.embryoNum ;
	var pregnancyDueDate = data.pregnancyDueDate == null ? "——" : data.pregnancyDueDate;
	var userName = data.userName == null ? "——" : data.userName;
	var lmp = data.lmp == null ? "" : data.lmp;
	var pregnancyNum = data.pregnancyNum == null ? "" : data.pregnancyNum;
	var birthNum = data.birthNum == null ? "" : data.birthNum;
	var height = data.height == null ? "" : data.height;
	var weight = data.weight == null ? "" : data.weight;
	var encysituation = "";
	var birthTiresType2 = "";
	if(data.encyesisSituation == null || data.encyesisSituation == ''){
		encysituation = "";
	}else if(data.encyesisSituation == "辅助生殖"){
		encysituation =  2; 
	}else if(data.encyesisSituation == "自然受孕"){
		encysituation =  1; 
	}else if(data.encyesisSituation.indexOf("意外怀孕") !=-1 || data.encyesisSituation.indexOf("意外妊娠") !=-1){
		birthTiresType2 =  1; 
		encysituation =  1; 
	}else if(data.encyesisSituation.indexOf("计划怀孕") !=-1 || data.encyesisSituation.indexOf("计划妊娠") !=-1 ){
		birthTiresType2 =  2;
		encysituation =  1; 
	}
	archivesTr = "<tr>" +
				 "<td>" + index + "</td>" +
				 "<td>" + createDatetime + "</td>" +
				 "<td>" + encyesisSituation +"</td>" +
				 "<td>" + embryoNum +"</td>" +
				 "<td>" + pregnancyDueDate +"</td>" +
				 "<td>" + userName +"</td>" +
				 "<td><a href=\"#\" onclick=\"setArchivesToBirth('" + data.id + "','" + lmp + "','" + pregnancyNum + "','" + birthNum + "','" + height + "','" + weight + "','" + encysituation + "','" + birthTiresType2 + "')\">选择</a></td>" +
				 "</tr>";
	return archivesTr;
}

function getBabysInfoByName (){
	var sendInfoParams = [];
	var alive_babyIds = $("input[name='c_babyId']");
	var death_babyIds = $("input[name='w_babyId']");
	var birthId = $("#baby_birthId").val();
	var babyGender =  $("select[name='babyGender']");
	var babyBirthTime =  $("input[name='babyBirthTime']");
	var babyBirthTimeHour =  $("input[name='babyBirthTimeHour']");
	var babyBirthTimeMinutes =  $("input[name='babyBirthTimeMinutes']");
	var babyLength =  $("input[name='babyLength']");
	var babyWeight =  $("input[name='babyWeight']");
	var babyHeadCircum =  $("input[name='babyHeadCircum']");
	var babyBust =  $("input[name='babyBust']");
	var babyAshesOneMinute =  $("input[name='babyAshesOneMinute']");
	var babyAshesFiveMinutes =  $("input[name='babyAshesFiveMinutes']");
	var babyAshesTenMinutes =  $("input[name='babyAshesTenMinutes']");
	var babyStifle =  $("input[name='babyStifle']");
	var babyDefect =  $("input[name='babyDefect']");
	var babyRescue =  $("select[name='babyRescue']");
	var babyComplication =  $("input[name='babyComplication']");
	var babyGuid =  $("textarea[name='babyGuid']");
	var babyPlacentaWeight =  $("input[name='babyPlacentaWeight']");
	var babyPlacentaLength =  $("input[name='babyPlacentaLength']");
	var babyPlacentaWidth =  $("input[name='babyPlacentaWidth']");
	var babyPlacentaThick =  $("input[name='babyPlacentaThick']");
	var babyAmnioticFluidVol =  $("select[name='babyAmnioticFluidVol']");
	var babyAmnioticFluidTraits =  $("select[name='babyAmnioticFluidTraits']");
	var babyRemark =  $("textarea[name='babyRemark']");
	var babyDeathTime =  $("input[name='babyDeathTime']");
	var babyDeathTimeHour =  $("input[name='babyDeathTimeHour']");
	var babyDeathTimeMinutes =  $("input[name='babyDeathTimeMinutes']");
	var babyAmnioticFluidDeathReason = $("textarea[name='babyAmnioticFluidDeathReason']");
	for(var i=0; i<alive_babyIds.length; i++){
		var params = {
				birthId : birthId,
				babyId :  alive_babyIds[i].value,
				babyGender :  babyGender[i] == undefined ? null :babyGender[i].value,
				babyBirthTime :  babyBirthTime[i] == undefined ? "" :babyBirthTime[i].value,
				babyBirthTimeHour :  babyBirthTimeHour[i] == undefined ? null :babyBirthTimeHour[i].value,
				babyBirthTimeMinutes :  babyBirthTimeMinutes[i] == undefined ? null :babyBirthTimeMinutes[i].value,
				babyLength :  babyLength[i] == undefined ? null :babyLength[i].value,
				babyWeight :  babyWeight[i] == undefined ? null :babyWeight[i].value,
				babyHeadCircum :  babyHeadCircum[i] == undefined ? null :babyHeadCircum[i].value,
				babyBust :  babyBust[i] == undefined ? null :babyBust[i].value,
				babyAshesOneMinute :  babyAshesOneMinute[i] == undefined ? null :babyAshesOneMinute[i].value,
				babyAshesFiveMinutes :  babyAshesFiveMinutes[i] == undefined ? null :babyAshesFiveMinutes[i].value,
				babyAshesTenMinutes :  babyAshesTenMinutes[i] == undefined ? null :babyAshesTenMinutes[i].value,
				babyStifle :  babyStifle[i] == undefined ? null :babyStifle[i].value,
				babyDefect :  babyDefect[i] == undefined ? null :babyDefect[i].value,
				babyRescue :  babyRescue[i] == undefined ? null :babyRescue[i].value,
				babyComplication :  babyComplication[i] == undefined ? null :babyComplication[i].value,
				babyGuid :  babyGuid[i] == undefined ? null :babyGuid[i].value,
				babyPlacentaWeight :  babyPlacentaWeight[i] == undefined ? null :babyPlacentaWeight[i].value,
				babyPlacentaLength :  babyPlacentaLength[i] == undefined ? null :babyPlacentaLength[i].value,
				babyPlacentaWidth :  babyPlacentaWidth[i] == undefined ? null :babyPlacentaWidth[i].value,
				babyPlacentaThick :  babyPlacentaThick[i] == undefined ? null :babyPlacentaThick[i].value,
				babyAmnioticFluidVol :  babyAmnioticFluidVol[i] == undefined ? null :babyAmnioticFluidVol[i].value,
				babyAmnioticFluidTraits :  babyAmnioticFluidTraits[i] == undefined ? null :babyAmnioticFluidTraits[i].value,
				babyRemark :  babyRemark[i] == undefined ? null :babyRemark[i].value,
				babyIsDeath : 0
			}
		sendInfoParams.push(params);
	}
	
	for(var i=0; i<death_babyIds.length; i++){
		var params = {
				birthId : birthId,
				babyId :  death_babyIds[i].value,
				babyDeathTime :  babyDeathTime[i] == undefined ? "" :babyDeathTime[i].value,
				babyDeathTimeHour :  babyDeathTimeHour[i] == undefined ? null :babyDeathTimeHour[i].value,
				babyDeathTimeMinutes :  babyDeathTimeMinutes[i] == undefined ? null :babyDeathTimeMinutes[i].value,
				babyAmnioticFluidDeathReason :  babyAmnioticFluidDeathReason[i] == undefined ? null :babyAmnioticFluidDeathReason[i].value,
				babyIsDeath : 1
			}
		sendInfoParams.push(params);
	}
	
	return JSON.stringify(sendInfoParams);
}

function getDischargesInfoByName (){
	var sendInfoParams = [];
	var disId =  $("input[name='disId']");
	var birthId =  $("#dis_birthId").val();
	var babyIds =  $("input[name='dis_babyId']");
	var disMotherDisagnosis = $("#disMotherDisagnosis_hidden").val();
	var disHemoglobin = $("#disHemoglobin").val();
	var disBabyDiagnosis =  $("input[name='disBabyDiagnosis']");
	var disBabyBloodSuger =  $("input[name='disBabyBloodSuger']");
	var disRemark = $("#disRemark").val();
	
	var newbabyDis = $("tr[id^=discharge_newbaby]");
	var params = {};
	if(newbabyDis.length != 0){
		for(var i=0; i<disId.size(); i++){
			if(i==0){
				params = {
						disId : disId[i].value,
						birthId : birthId,
						disMotherDisagnosis :  disMotherDisagnosis,
						disHemoglobin :  disHemoglobin,
						disRemark :  disRemark
				}
				sendInfoParams.push(params);
				continue;
			}
			params = {
					disId : disId[i].value,
					birthId : birthId,
					babyId : babyIds[i-1] == undefined ? null : babyIds[i-1].value,
					disMotherDisagnosis :  disMotherDisagnosis,
					disHemoglobin :  disHemoglobin,
					disBabyDiagnosis :  disBabyDiagnosis[i-1] == undefined ? null :disBabyDiagnosis[i-1].value,
					disBabyBloodSuger :  disBabyBloodSuger[i-1] == undefined ? "" :disBabyBloodSuger[i-1].value,
					disRemark :  disRemark
			}
			sendInfoParams.push(params);
		}
	}else{
		params = {
				disId : $("#disId").val(),
				birthId : birthId,
				disMotherDisagnosis :  disMotherDisagnosis,
				disHemoglobin :  disHemoglobin,
				disRemark :  disRemark
		}
		sendInfoParams.push(params);
	}
	return JSON.stringify(sendInfoParams);
}


/**
 * 初始诊断下拉自动补全
 * @returns
 */
function initAllDiseaseSelect(){
	//入院诊断
	common.autocompleteMethod("birthDiagnosis", productListData, function(data){
		$("#birthDiagnosis").val("");
		var birthDiagnosis_value = $("#birthDiagnosis_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#birthDiagnosis").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"diagnosis_div","birthDiagnosis_hidden");
	});
	//产前合并诊断
	common.autocompleteMethod("baseComplicationPrenatal", productListData, function(data){
		$("#baseComplicationPrenatal").val("");
		var birthDiagnosis_value = $("#baseComplicationPrenatal_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#baseComplicationPrenatal").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"baseComplicationPrenatal_div","baseComplicationPrenatal_hidden");
	});
	//产时并发症
	common.autocompleteMethod("baseComplicationBirthing", productListData, function(data){
		$("#baseComplicationBirthing").val("");
		var birthDiagnosis_value = $("#baseComplicationBirthing_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#baseComplicationBirthing").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"baseComplicationBirthing_div","baseComplicationBirthing_hidden");
	});
	//产后并发症
	common.autocompleteMethod("baseComplicationAfterBirthing", productListData, function(data){
		$("#baseComplicationAfterBirthing").val("");
		var birthDiagnosis_value = $("#baseComplicationAfterBirthing_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#baseComplicationAfterBirthing").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"baseComplicationAfterBirthing_div","baseComplicationAfterBirthing_hidden");
	});
	//内科并发症
	common.autocompleteMethod("baseComplicationsMedical", productListData, function(data){
		$("#baseComplicationsMedical").val("");
		var birthDiagnosis_value = $("#baseComplicationsMedical_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#baseComplicationsMedical").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"baseComplicationsMedical_div","baseComplicationsMedical_hidden");
	});
	//新生儿并发症
	common.autocompleteMethod("babyComplication", productListData, function(data){
		$("#babyComplication").val("");
		var birthDiagnosis_value = $("#babyComplication_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#babyComplication").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"babyComplication_div","babyComplication_hidden");
	});
	//母亲出院诊断
	common.autocompleteMethod("disMotherDisagnosis", productListData, function(data){
		$("#disMotherDisagnosis").val("");
		var birthDiagnosis_value = $("#disMotherDisagnosis_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#disMotherDisagnosis").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"disMotherDisagnosis_div","disMotherDisagnosis_hidden");
	});
	//新生儿出院诊断
	common.autocompleteMethod("disBabyDiagnosis", productListData, function(data){
		$("#disBabyDiagnosis").val("");
		var birthDiagnosis_value = $("#disBabyDiagnosis_hidden").val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种诊断");
			$("#disBabyDiagnosis").val("");
			return;
		}
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,"disBabyDiagnosis_div","disBabyDiagnosis_hidden");
	});
}