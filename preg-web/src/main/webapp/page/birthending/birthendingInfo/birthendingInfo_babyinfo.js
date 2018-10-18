// 焦点div
var babyDivName = "none";
// 新生儿诊断集合
var disMap = new Map();

/**
 * from表单自动提交
 */
function formSubmit(){
	$("body").on("click", function(){
		// 母亲诊断
		var motherLength = $(":focus").parents("form[id='motherForm']").length;
		if(motherLength == 0 && $("#motherForm").val() == 1){
			var postURL = URL.get("BirthEnding.BIRTHENDING_DISCHARGE_UPDATE");
			var sendInfoParams = $("#motherForm").serialize()+"&birthId=" + $("#birthId").val()+"&disRemark="+$("#disRemark").val();
			saveBirthEnding(postURL, sendInfoParams);
		}
		$("#motherForm").val(motherLength);
		
		
		// 备注
		var demarkLength = $(":focus").parents("form[id='remarkForm']").length;
		if(demarkLength == 0 && $("#remarkForm").val() == 1){
			var postURL = URL.get("BirthEnding.BIRTHENDING_DISCHARGE_UPDATE");
			var sendInfoParams = $("#remarkForm").serialize()+"&birthId=" + $("#birthId").val()
			+"&disHemoglobin="+$("#disHemoglobin").val()+"&disMotherDisagnosis="+$("#disMotherDiagnosis_hidden").val();
			saveBirthEnding(postURL, sendInfoParams);
		}
		$("#remarkForm").val(demarkLength);
		
		
		// 新生儿
		$("div[name='babyForm']").each(function(i,val){
			var focusLength = $(":focus").parents("div[id='"+val.id+"']").length;
			if(focusLength == 0 && $("#focus_"+val.id).val() == 1){
				var postURL = URL.get("BirthEnding.BIRTHENDING_BABYINFO_UPDATE");
				var sendInfoParams = $("#"+val.id+" form").serialize()+"&birthId=" + $("#birthId").val();
				saveBirthEnding(postURL, sendInfoParams);
			}
			$("#focus_"+val.id).val(focusLength);
		});
		
		
		// 新生儿出院诊断
		$("div[name='babyDis']").each(function(i,val){
			var focusLength = $(":focus").parents("div[id='"+val.id+"']").length;
			if(focusLength == 0 && $("#focus_"+val.id).val() == 1){
				var postURL = URL.get("BirthEnding.BIRTHENDING_DISCHARGE_UPDATE");
				var sendInfoParams = $("#"+val.id+" form").serialize()+"&birthId=" + $("#birthId").val()+"&disHemoglobin="+$("#disHemoglobin").val()
				+"&disMotherDisagnosis="+$("#disMotherDiagnosis_hidden").val()+"&disRemark="+$("#disRemark").val();
				saveBirthEnding(postURL, sendInfoParams);
			}
			$("#focus_"+val.id).val(focusLength);
		});
	})
}

/**
 * 清空新生儿信息
 */
function clearBabyInfo(){
	$("#selectedMotherDiseases div[id*='disMotherDiagnosis_hidden']").remove();// 母亲诊断
	$("#disMotherDiagnosis_hidden").val("");
	common.clearForm("remarkForm");
	common.clearForm("motherForm");
	$("#babyInfo").empty();// 清空新生儿显示
	$("#babyDiagonsis").empty();//清空诊断信息
}


/**
 * 加载页面数据
 */
function loadBabyPage(){
	// 清空新生儿信息
	clearBabyInfo();
	
	// from表单自动提交
	formSubmit();
	
	// 母亲出院诊断自动补全
	initAutocomplete("disMotherDiagnosis","disMotherDiagnosis_hidden","selectedMotherDiseases");
	
	// 加载数据
	var birthId = $("#birthId").val();
	if(!common.isEmpty(birthId)){
		var url = URL.get("BirthEnding.BIRTHENDING_BABYINFO_SEARCH");
		var params = "birthId=" + birthId;
		ajax.post(url, params, dataType.json, function(data){
			// 记录诊断信息
			disMap = new Map();
			if(!common.isEmpty(data.value.disList)){
				$.each(data.value.disList, function(index, value){
					if(common.isEmpty(value.babyId)){
						// 加载母亲出院诊断信息+备注
						$("#mother_disId").val(value.disId || "");
						$("#remark_disId").val(value.disId || "");
						$("#disHemoglobin").val(value.disHemoglobin || "");
						$("#disRemark").val(value.disRemark || "");
						// 母亲诊断数据回显
						if(!common.isEmpty(value.disMotherDisagnosis)){
							var disMotherDiagnosis_array = getSelectArray(value.disMotherDisagnosis,productAllMap);
					        selectOperation.init("selectedMotherDiseases","disMotherDiagnosis_hidden",disMotherDiagnosis_array);
					        $("#disMotherDiagnosis_hidden").val(value.disMotherDisagnosis);
					        $("#disMotherDiagnosis").val("");
						}
					}else{
						disMap.set(value.babyId, value);// 记录新生儿出院诊断
					}
				});
			}
			// 加载新生儿信息
			if(!common.isEmpty(data.value.babyList)){
				$.each(data.value.babyList, function(index, value){
					loadBabyInfoHtml(value);
					// 加载新生儿诊断信息
					if(!common.isEmpty(disMap.get(value.babyId))){
						loadDisInfoHtml(disMap.get(value.babyId));// 加载新生儿出院诊断信息
					}
				});
				// 处理新生儿出生日期
				saveBabyBirthTime();
			}
			
			// 自动补全改变对应输入框获取焦点
			$('.bodyClass input[id*="_hidden"]').bind("change", function(){
				var id = $(this).attr("id").replace("_hidden","");
				$("#"+id).focus();
			})
		}, false, false);
	}
}

/**************************************************************新生儿模块start***********************************************************/
/**
 * 添加新生儿信息
 */
function addBabyInfo(){
	//添加新生儿信息
	var url = URL.get("BirthEnding.BIRTHENDING_BABYINFO_SAVE");
	var params = "birthId=" + $("#birthId").val()+ "&babyIsDeath=" + $("input[name='newBaby']:checked").val();
	ajax.post(url, params, dataType.json, function(data){
		if(!common.isEmpty(data.value)){
			// 添加新生儿诊断信息
			if(data.value.babyIsDeath == 0){
				addDisInfo(data.value.babyId);
			}
			
			// 重新load页面
			layer.msg('数据加载中', {icon: 16,shade: 0.01,time:false});
			var top = getScroll();
			loadBabyPage();
			window.scrollTo(0,top);
			setTimeout(function(){
			  layer.close(layer.index);
			}, 200);
		}
		//此处演示关闭
	}, false, false);
}

/**
 * 删除新生儿信息
 * @param babyId
 */
function delBabyInfo(babyId){
	// 存活新生儿删除对应诊断信息==》后台删除对应诊断信息
	layer.confirm('是否删除该新生儿信息？', {btn: ['是','否']},function(layerIndex){
		var url = URL.get("BirthEnding.BIRTHENDING_BABYINFO_DELETE");
		var params = "babyId=" + babyId;
		ajax.post(url, params, dataType.json, function(data){
			// 重新load页面
			layer.msg('数据加载中', {icon: 16,shade: 0.01,time:false});
			var top = getScroll();
			loadBabyPage();
			window.scrollTo(0,top);
			setTimeout(function(){
			  layer.close(layer.index);
			}, 200);
		}, false, false);
		layer.close(layerIndex);
	}, function(layerIndex){
		layer.close(layerIndex);
	});
}

/**
 * 新生儿出生日期(为空)默认母亲的分娩日期
 */
function saveBabyBirthTime(){
	if(!common.isEmpty($("#baseTime").val())){
		$("div[name='babyForm']").each(function(i,val){
			if(common.isEmpty($("#babyBirthTime_"+val.id).val())){
				// 赋值
				$("#babyBirthTime_"+val.id).val($("#baseTime").val());
				
				// 保存
				var postURL = URL.get("BirthEnding.BIRTHENDING_BABYINFO_UPDATE");
				var sendInfoParams = $("#"+val.id+" form").serialize()+"&birthId=" + $("#birthId").val();
				saveBirthEnding(postURL, sendInfoParams);
			}
		});
	}
}
/**
 * 添加新生儿信息html
 * @param babyInfo
 */
function loadBabyInfoHtml(babyInfo){
	if(common.isEmpty(babyInfo)){
		return;
	}
	// 基本信息
	var babyId = babyInfo.babyId || "";
	var babyGender = babyInfo.babyGender || "";
	var babyBirthTime = babyInfo.babyBirthTime || "";
	var babyBirthTimeHour = babyInfo.babyBirthTimeHour == 0 ? babyInfo.babyBirthTimeHour && "":babyInfo.babyBirthTimeHour || "";
	var babyBirthTimeMinutes = babyInfo.babyBirthTimeMinutes == 0 ? babyInfo.babyBirthTimeMinutes && "":babyInfo.babyBirthTimeMinutes || "";
	var babyLength = babyInfo.babyLength || "";
	var babyWeight = babyInfo.babyWeight || "";
	var babyHeadCircum = babyInfo.babyHeadCircum || "";
	var babyBust = babyInfo.babyBust || "";
	var babyAshesOneMinute = babyInfo.babyAshesOneMinute || "";
	var babyAshesFiveMinutes = babyInfo.babyAshesFiveMinutes || "";
	var babyAshesTenMinutes = babyInfo.babyAshesTenMinutes || "";
	var babyStifle = babyInfo.babyStifle || "";
	var babyDefect = babyInfo.babyDefect || "";
	var babyRescue = babyInfo.babyRescue || "";
	var babyComplication = babyInfo.babyComplication || "";// 并发症
	var babyGuid = babyInfo.babyGuid || "";
	
	// 胎盘、羊水性状
	var babyPlacentaWeight = babyInfo.babyPlacentaWeight || "";
	var babyPlacentaLength = babyInfo.babyPlacentaLength || "";
	var babyPlacentaWidth = babyInfo.babyPlacentaWidth || "";
	var babyPlacentaThick = babyInfo.babyPlacentaThick || "";
	var babyAmnioticFluidVol = babyInfo.babyAmnioticFluidVol || "";
	var babyAmnioticFluidTraits = babyInfo.babyAmnioticFluidTraits || "";
	var babyRemark = babyInfo.babyRemark || "";
	
	// 死亡
	var babyDeathTime = babyInfo.babyDeathTime || "";
	var babyDeathTimeHour = babyInfo.babyDeathTimeHour == 0 ? babyInfo.babyDeathTimeHour && "":babyInfo.babyDeathTimeHour || "";
	var babyDeathTimeMinutes = babyInfo.babyDeathTimeMinutes == 0 ? babyInfo.babyDeathTimeMinutes && "":babyInfo.babyDeathTimeMinutes || "";
	var babyAmnioticFluidDeathReason = babyInfo.babyAmnioticFluidDeathReason || "";
	
	// html拼接
	var html = ``;
	var babyIsDeath = babyInfo.babyIsDeath || "";
	
	// 存活
	if(babyIsDeath == 0){
		var babyIndex = $("div[name='liveBabyInfo']").length + 1;// 新生儿位置标识
		html = `
		<div name="liveBabyInfo">
			<div class="table-responsive" id="${babyId }" name="babyForm">
				<input id="focus_${babyId }" type="hidden" value=""/>
				<form action="" method="post" class="no-bottom">
					<input type="hidden" name="babyId" value="${babyId }"/>
					<input type="hidden" name="babyIsDeath" value="0"/>
					<table id="babyTable_${babyId }" class="table table-bordered table-padding-4 no-bottom">
						<tr class="active">
							<td colspan="2">
								<div class="text-center">
									<label class="control-label text-center">新生儿${babyIndex }</label>
									<button class="btn btn-lightblue redClass btn-sm pull-right" type="button" onclick="delBabyInfo('${babyId }')">删 除</button>
								</div>
							</td>
						</tr>
						<tr>
							<td class="thirdTitle">新生儿</td>
							<td>
								<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon redClass addon-left">性别</div>
											<select class="intake-sm" id="babyGender_${babyId }" name="babyGender">
												<option value="">请选择新生儿性别</option>
												<option value="1">男</option>
												<option value="2">女</option>
												<option value="3">不明</option>
											</select>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon redClass addon-left">出生日期</div>
											<input class="intake-input form-control date" sign="babyDateInput" id="babyBirthTime_${babyId }" name="babyBirthTime" value="" onkeyup="checkDateHourMinutes(this,'date');" type="text" placeholder="请选择出生日期" readonly style="background-color: white;" onclick="common.dateShow(this.id)" />
											<div class="div-table-cell"><input type="text" id="babyBirthTimeHour_${babyId }" name="babyBirthTimeHour" class="intake-sm hour" onkeyup="checkDateHourMinutes(this,'hour');" placeholder="时" value="${babyBirthTimeHour }"></div>
											<div class="div-table-cell"><input type="text" id="babyBirthTimeMinutes_${babyId }" name="babyBirthTimeMinutes" class="intake-sm minutes" onkeyup="checkDateHourMinutes(this,'minuters');" onchange="defaultMinutes(this);" placeholder="分" value="${babyBirthTimeMinutes }"></div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon redClass addon-left">身长</div>
											<input class="intake-input" type="text" id="babyLength_${babyId }" maxlength="6" name="babyLength" value="${babyLength }" onkeyup="checkNumPoint(this);"/>
											<div class="input-group-addon">cm</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon redClass addon-left">体重</div>
											<input class="intake-input" type="text" id="babyWeight_${babyId }" maxlength="6" name="babyWeight" value="${babyWeight }" onkeyup="checkNum(this);"/>
											<div class="input-group-addon">g</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left">头围</div>
											<input class="intake-input" type="text" id="babyHeadCircum_${babyId }" maxlength="6" name="babyHeadCircum" value="${babyHeadCircum }" onkeyup="checkNumPoint(this);"/>
											<div class="input-group-addon">cm</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left">胸围</div>
											<input class="intake-input" type="text" id="babyBust_${babyId }" maxlength="6" name="babyBust" value="${babyBust }" onkeyup="checkNumPoint(this);"/>
											<div class="input-group-addon">cm</div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">阿氏评分 1分钟</div>
											<input class="intake-input" type="text" id="babyAshesOneMinute_${babyId }" maxlength="6" name="babyAshesOneMinute" value="${babyAshesOneMinute }" onkeyup="checkNum(this);"/>
											<div class="input-group-addon">分</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">5分钟</div>
											<input class="intake-input" type="text" id="babyAshesFiveMinutes_${babyId }" maxlength="6" name="babyAshesFiveMinutes" value="${babyAshesFiveMinutes }" onkeyup="checkNum(this);" />
											<div class="input-group-addon">分</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">10分钟</div>
											<input class="intake-input" type="text" id="babyAshesTenMinutes_${babyId }" maxlength="6" name="babyAshesTenMinutes" value="${babyAshesTenMinutes }" onkeyup="checkNum(this);" />
											<div class="input-group-addon">分</div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">新生儿窒息</div>
											<input class="intake-input" type="text" id="babyStifle_${babyId }" maxlength="6" name="babyStifle" value="${babyStifle }" onkeyup="checkNum(this);"/>
											<div class="input-group-addon">分钟</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left">出生缺陷</div>
											<input class="intake-sm" type="text" id="babyDefect_${babyId }" maxlength="100" name="babyDefect" value="${babyDefect }" />
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left">抢救</div>
											<select class="intake-sm" id="babyRescue_${babyId }" name="babyRescue">
												<option value="">请选择</option>
												<option value="1">有</option>
												<option value="0">无</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-xs-12 form-inline padding-zero" id="selectedDiseaseNames_${babyId }" name="diseases" style="margin-bottom: 5px;">
									<div class="col-xs-3 input-group padding-zero">
										<input type="hidden" id="babyComplication_hidden_${babyId }" name="babyComplication"/><!-- 保存所选疾病id -->
										<div class="input-group-addon addon-left" style="width: 82px;">并发症</div>
										<input class="intake-sm" type="text" id="babyComplication_${babyId }" value="" placeholder="请输入并发症"/>
									</div>
								</div>
								<div class="col-xs-12 padding-zero">
									<div class="input-group">
										<div class="input-group-addon addon-left">新生儿指导</div>
										<textarea id="babyGuid_${babyId }" name="babyGuid" maxlength="1000" class="form-control" placeholder="点击输入内容"  maxlength="1000">${babyGuid }</textarea>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="thirdTitle">胎盘/羊水情况</td>
							<td>
								<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">胎盘重</div>
											<input class="intake-input" type="text" maxlength="6" id="babyPlacentaWeight_${babyId }" name="babyPlacentaWeight" value="${babyPlacentaWeight }" onkeyup="checkNumPoint2(this);" />
											<div class="input-group-addon">g</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">胎盘长</div>
											<input class="intake-input" type="text" maxlength="6" id="babyPlacentaLength_${babyId }" name="babyPlacentaLength" value="${babyPlacentaLength }"  onkeyup="checkNumPoint2(this);"/>
											<div class="input-group-addon">cm</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">胎盘宽</div>
											<input class="intake-input" type="text" maxlength="6" id="babyPlacentaWidth_${babyId }" name="babyPlacentaWidth" value="${babyPlacentaWidth }"  onkeyup="checkNumPoint2(this);"/>
											<div class="input-group-addon">cm</div>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">胎盘厚</div>
											<input class="intake-input" type="text" maxlength="6" id="babyPlacentaThick_${babyId }" name="babyPlacentaThick" value="${babyPlacentaThick }"  onkeyup="checkNumPoint2(this);"/>
											<div class="input-group-addon">cm</div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 padding-zero">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">羊水量</div>
											<select class="intake-sm" id="babyAmnioticFluidVol_${babyId }" name="babyAmnioticFluidVol">
												<option value="">请选择</option>
												<option value="1">少</option>
												<option value="2">中</option>
												<option value="3">多</option>
											</select>
										</div>
									</div>
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">羊水性状</div>
											<select class="intake-sm" id="babyAmnioticFluidTraits_${babyId }" name="babyAmnioticFluidTraits">
												<option value="">请选择</option>
												<option value="1">清</option>
												<option value="2">浊</option>
											</select>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="thirdTitle">新生儿备注</td>
							<td>
								<textarea id="babyRemark_${babyId }" name="babyRemark" class="form-control" placeholder="点击输入内容" maxlength="1000">${babyRemark }</textarea>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		`
		// 赋值
		if($("div[name='liveBabyInfo']").length == 0){
			$("#babyInfo").prepend(html);
		}else{
			$("div[name='liveBabyInfo']:last").after(html);
		}
		
		// 出生日期初始化
		// common.initDate(null,null,null,"#babyBirthTime_"+babyId);
		initTimeDate("babyBirthTime_"+babyId,babyBirthTime);
		
		// 并发症初始化
		initAutocomplete("babyComplication_"+babyId,"babyComplication_hidden_"+babyId,"selectedDiseaseNames_"+babyId);
		// 并发症数据回显
		if(!common.isEmpty(babyComplication)){
			var babyComplication_array = getSelectArray(babyComplication,productAllMap);
	        selectOperation.init("selectedDiseaseNames_"+babyId,"babyComplication_hidden_"+babyId,babyComplication_array);
	        $("#babyComplication_hidden_"+babyId).val(babyComplication);
	        $("#babyComplication_"+babyId).val("");
		}
		
		// 下拉框数据回显
		$("#babyGender_"+babyId).val(babyGender);
		$("#babyRescue_"+babyId).val(babyRescue);
		$("#babyAmnioticFluidVol_"+babyId).val(babyAmnioticFluidVol);
		$("#babyAmnioticFluidTraits_"+babyId).val(babyAmnioticFluidTraits);
	}
	// 死亡
	if(babyIsDeath == 1){
		var babyIndex = $("div[name='deathBabyInfo']").length + $("div[name='liveBabyInfo']").length + 1;// 新生儿位置标识
		html = `
		<div name="deathBabyInfo">
			<div class="table-responsive" id="${babyId }" name="babyForm"> 
				<input id="focus_${babyId }" type="hidden" value=""/>
				<form action="" method="post" class="no-bottom">
					<input type="hidden" name="babyId" value="${babyId }"/>
					<input type="hidden" name="babyIsDeath" value="1"/>
					<table id="babyTable" class="table table-bordered table-padding-4 no-bottom">
						<!-- 死亡 -->
						<tr class="active">
							<td colspan="2">
								<div class="text-center">
									<label class="control-label text-center">新生儿${babyIndex}死亡</label>
									<button class="btn btn-lightblue btn-sm pull-right redClass" type="button" onclick="delBabyInfo('${babyId }')">删 除</button>
								</div>
							</td>
						</tr>
						<tr>
							<td class="thirdTitle">新生儿死亡</td>
							<td>
								<div class="col-xs-12 padding-zero" style="margin-bottom: 5px;">
									<div class="col-xs-3 padding-zero">
										<div class="input-group">
											<div class="input-group-addon addon-left redClass">死亡时间</div>
											<input class="intake-input form-control date" sign="babyDateInput" id="babyDeathTime_${babyId }" name="babyDeathTime" value="" onkeyup="checkDateHourMinutes(this,'date');" type="text" placeholder="请选择死亡时间" readonly style="background-color: white;" onclick="common.dateShow(this.id)" />
											<div class="div-table-cell"><input type="text" id="babyDeathTimeHour_${babyId }" name="babyDeathTimeHour" class="intake-sm hour" onkeyup="checkDateHourMinutes(this,'hour');" placeholder="时" value="${babyDeathTimeHour }"></div>
											<div class="div-table-cell"><input type="text" id="babyDeathTimeMinutes_${babyId }" name="babyDeathTimeMinutes" class="intake-sm minutes" onkeyup="checkDateHourMinutes(this,'minuters');" onchange="defaultMinutes(this);" placeholder="分" value="${babyDeathTimeMinutes }"></div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 padding-zero">
									<div class="input-group">
										<div class="input-group-addon addon-left">死亡原因</div>
										<textarea id="babyAmnioticFluidDeathReason_${babyId }" name="babyAmnioticFluidDeathReason" class="form-control" placeholder="请输入死亡原因"  maxlength="1000">${babyAmnioticFluidDeathReason }</textarea>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		`
		// 赋值
		$("#babyInfo").append(html);
		// 死亡时间初始化
		initTimeDate("babyDeathTime_"+babyId,babyDeathTime);
	}
}

/**************************************************************新生儿模块end***********************************************************/

/**************************************************************出院诊断模块start***********************************************************/

/**
 * 添加新生儿诊断信息
 * @param babyId
 */
function addDisInfo(babyId){
	var url = URL.get("BirthEnding.BIRTHENDING_DISCHARGE_SAVE");
	var params = "birthId=" + $("#birthId").val()+ "&babyId=" + babyId
				 + "&disMotherDiagnosis=" + $("#disMotherDiagnosis_hidden").val() 
				 + "&disHemoglobin=" + $("#disHemoglobin").val() + "&disRemark=" + $("#disRemark").val();
	ajax.post(url, params, dataType.json, function(data){
		if(!common.isEmpty(data.value)){
			// 渲染新生儿诊断信息
			loadDisInfoHtml(data.value);
		}
	}, false, false);
}

/**
 * 添加出院诊断信息html
 * @param disInfo
 */
function loadDisInfoHtml(disInfo){
	if(common.isEmpty(disInfo)){
		return;
	}
	// 基本信息
	var disId = disInfo.disId || "";
	var babyId = disInfo.babyId || "";
	var disBabyDiagnosis = disInfo.disBabyDiagnosis || "";
	var disBabyBloodSuger = disInfo.disBabyBloodSuger || "";
	
	var babyDiagnosisIndex = $("div[name='liveBabyInfo']").length;// 新生儿诊断标识
	// html拼接
	var html = `
		<div name="babyDis" id="${disId }">
			<input id="focus_${disId }" type="hidden" value=""/>
			<form action="" method="post" class="no-bottom">
				<table class="table table-bordered table-padding-4 no-bottom">
					<tr>
						<td class="thirdTitle">新生儿${babyDiagnosisIndex }</td>
						<td>
							<input type="hidden" id="disgnisis_${babyId }" name="babyId" value="${babyId }"/><!-- babyId -->
							<input type="hidden" name="disId" value="${disId }"/><!-- disId -->
							<div class="col-xs-12 form-inline padding-zero" id="selectedDiseaseNames_${disId }" name="diseases" style="margin-bottom: 5px;">
								<div class="col-xs-3 input-group">
									<input type="hidden" id="disBabyDiagnosis_hidden_${disId }" name="disBabyDiagnosis"/><!-- 保存所选疾病id -->
									<div class="input-group-addon addon-left" style="width: 82px;">出院诊断</div>
									<input class="intake-sm" type="text" id="disBabyDiagnosis_${disId }" value="" placeholder="请输入诊断"/>
								</div>
							</div>
							<div class="col-xs-12 padding-zero">
								<div class="col-xs-3 input-group">
									<div class="input-group-addon addon-left">新生儿血糖</div>
									<input class="intake-input" type="text" id="disBabyBloodSuger_${disId }" maxlength="6" name="disBabyBloodSuger" value="${disBabyBloodSuger }" onkeyup="checkNumPoint2(this,'AmnioticFluid');" />
									<div class="input-group-addon">mg/dl</div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	`;
	// 赋值
	$("#babyDiagonsis").append(html);
	initAutocomplete("disBabyDiagnosis_"+disId,"disBabyDiagnosis_hidden_"+disId,"selectedDiseaseNames_"+disId);
	// 新生儿诊断数据回显
	if(!common.isEmpty(disBabyDiagnosis)){
		var disBabyDiagnosis_array = getSelectArray(disBabyDiagnosis,productAllMap);
        selectOperation.init("selectedDiseaseNames_"+disId,"disBabyDiagnosis_hidden_"+disId,disBabyDiagnosis_array);
        $("#disBabyDiagnosis_hidden_"+disId).val(disBabyDiagnosis);
        $("#disBabyDiagnosis_"+disId).val("");
	}
}
/**************************************************************出院诊断模块end***********************************************************/

/**************************************************************工具类start**************************************************************/

/**
 * 初始化时间插件
 * @param id 当前操作的dom元素
 * @param date 日期参数
 */
function initTimeDate(id,date) {
	common.initDate(null,null,null,"#"+id);
	//var nowdate = common.dateFormatToString(new Date(),"yyyy-mm-dd HH:ii");
	//$("#"+id).datetimepicker("setEndDate",nowdate);// 截止到今天
	if(!common.isEmpty(date)){
		$("#"+id).val(date);
	}
}

/**
 * 初始化疾病自动补全
 * @param currentDomId 当前操作的dom元素
 * @param saveIdsDomId 存储疾病id的dom元素
 * @param saveNamesDomId 存储疾病名称的dom元素
 */
function initAutocomplete(currentDomId,saveIdsDomId,saveNamesDomId) {
	common.autocompleteMethod(currentDomId, productListData, function(data){
		$("#"+currentDomId).val("");
		var birthDiagnosis_value = $("#"+saveIdsDomId).val();
		var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
		if(birthDiagnosisLength > 5 ){
			layer.msg("最多选择6种！");
			$("#"+currentDomId).val("");
			return;
		}
		// 所选id，对应name，名称显示，id存储
		selectOperation.addSelectOperation(data.value.diseaseId,data.name,saveNamesDomId,saveIdsDomId);
	});
}

/**
 * 疾病数据回显
 * @param ids
 * @param productAllMap
 * @returns
 */
function getSelectArray(ids,productAllMap){
	var selectedObjArray = [];
	if(ids == undefined) {
		return selectedObjArray;
	}
	var idArray= ids.split(",");
	for(var i=0; i<idArray.length; i++){
		var id = idArray[i];
		if(id=="") continue;
		var select ={
				key : id,
				value : productAllMap[id].diseaseName
		};
		selectedObjArray.push(select);
	}
	return selectedObjArray;
}

/**
 * 区域自动保存
 * @param URL
 * @param sendInfoParams
 * @returns
 */
function saveBirthEnding(URL,sendInfoParams){
	if (_.isEmpty(sendInfoParams)) {
		sendInfoParams = 'random=' + Math.random();
	} else {
		sendInfoParams = sendInfoParams + '&random=' + Math.random();
	}
	$.ajax({
		type : "post",
		url : URL,
		data : sendInfoParams,
		async : false,
		dataType : dataType.json,
		success : function(data) {
		},
		error : function(XMLHttpRequest) {
			ajax._error(XMLHttpRequest, "")
			babyDivName = "none";
		}
	});
}

/**
 * 获取当前位置
 */
function getScroll(){
	var top; 
	if (document.documentElement && 
	document.documentElement.scrollTop)   
	{           
		top = document.documentElement.scrollTop;           
	} else if (document.body)   
	{           
		top = document.body.scrollTop;           
	}       
	return top; 
}

/**
 * 给分钟默认值：0
 */
function defaultMinutes(obj) {
	const id = obj.id;
	const value = obj.value;
	const hour = $("#"+id).parent().parent().find("input:eq(1)");
	if(!common.isEmpty(hour) && common.isEmpty(value)) {
		obj.value = "0";
	}
}
/**************************************************************工具类end****************************************************************/