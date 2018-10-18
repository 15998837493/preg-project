/**
 * 孕周数特殊校验：1+2或1 这两种格式
 */
function validWeekNum(obj) {
	const num = obj.value;
	const reg = /(^[1-9]{1}\d{0,1}\+{1}[1-9]{1}\d{0,1}$)|(^[1-9]{1}\d{0,1}$)/g;
	if(!reg.test(num)) {
		obj.value = "";
	}
}

/**
 * 根据分娩日期计算孕周
 */
function getCustLmp(birthDate, lmpDate){
	var lmp = "";
	if(!common.isEmpty(lmpDate)){
		var time = new Date($("#baseTime").val().split(" ")[0]).getTime() - new Date(lmpDate).getTime();
		var pregnantDays = parseInt(time / (1000 * 60 * 60 * 24));
		var pregnantWeeks = pregnantDays / 7;
        var plusDays = pregnantDays % 7;
        lmp = Math.floor(pregnantWeeks) + "+" + plusDays;
	}
	return lmp;
}

/**
 * 初始化 自动补全
 */
function initAutoCompletion() {
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
}

/**
 * 1.7.2新需求，分娩日期 >= 住院日期
 * @param otherDate
 * @param thisDate
 * @param type
 */
function compareDate(otherDate,thisDate,type) {
	if(!otherDate.includes("-") || !thisDate.includes("-")) {
		layer.alert("日期选择插件格式有误！");
	}else {
		const date1 = parseInt(otherDate.split("-")[0]+otherDate.split("-")[1]+otherDate.split("-")[2]);
		const date2 = parseInt(thisDate.split("-")[0]+thisDate.split("-")[1]+thisDate.split("-")[2]);
		if(type == '1') {// 住院日期触发
			if(date2 > date1) {
				layer.alert("住院日期不能大于分娩日期！");
				$("#birthHospitalDate").val("");
			}
		}else if(type == '2') {// 分娩日期触发
			if(date2 < date1) {
				layer.alert("分娩日期不能小于住院日期！");
				$("#baseTime").val("");
			}
		}
	};
};

/**
 * 监听触发事件
 */
function initListenEvent() {
	// 计算孕期体重增长： 孕期体重增长 = 分娩前体重*10 - 孕前体重*10
	$("#baseWeightBeforeBirth").change(function(){
		if( !common.isEmpty($("#birthWeight").val()) && !common.isEmpty($("#baseWeightBeforeBirth").val())){
			const result = (parseFloat($("#baseWeightBeforeBirth").val())*10 - parseFloat($("#birthWeight").val())*10)/10;
			$("#baseGrowthPregnancying").val(result);
		};
	});

	// 选择会阴情况会联动会阴裂伤程度
	$("#basePerineumState").change(function(){
		var tiresType = $(this).children('option:selected').val();
		if(tiresType != '2'){
			$("#basePerineumHurt").attr("disabled","disabled");
			$("#basePerineumHurt").val("");
		}else{
			$("#basePerineumHurt").removeAttr("disabled");
		};
	});
	if($("#basePerineumState").val() != '2') {
		$("#basePerineumHurt").attr("disabled",true);
	};

	// 通过分娩时间计算 孕周和年龄
	$("#baseTime").off("change").on("change",function(){
		// 新生儿出生日期默认母亲的分娩日期
		let babyDateInputLength = $("input[sign='babyDateInput']").length;
		if(babyDateInputLength > 0 && !common.isEmpty(this.value)) {
			for(let x=0;x<babyDateInputLength;x++) {
				const babyBirthDateVal = $("input[sign='babyDateInput']:eq("+x+")").val();
				if(common.isEmpty(babyBirthDateVal)) {
					$("input[sign='babyDateInput']:eq("+x+")").val(this.value);
					$("input[id^='babyBirthTimeHidden_']:eq("+x+")").val(this.value+" 0:0");
				}
			}
			// 保存新生儿出生日期
			saveBabyBirthTime();
		}
		const zhuyuanDate = $("#birthHospitalDate").val();
		if(!common.isEmpty(zhuyuanDate)) {
			compareDate(zhuyuanDate,this.value.split(" ")[0],"2");
		}
		const riqi = $("#baseTime").val().split(" ")[0];
		if(!common.isEmpty($("#lmpId").val())){
			var weeks = getCustLmp(riqi, $("#lmpId").val());
			if(weeks.indexOf("-") != -1){
				layer.msg("分娩时间有误，请重新选择");
				$("#baseTime").val("");
				$("#baseWeeks").val("");
				return;
			}else{
				$("#baseWeeks").val(weeks);
			};
		}
		if(!common.isEmpty($("#birthBirthday").val())){
			var age = riqi.substring(0,4) - $("#birthBirthday").val().substring(0,4);
			$("#birthAge").val(age < 0 ? "" : age);
			$("#birthAge_hidden").val(age < 0 ? "" : age);
		};
	});

	// 分娩方式为自然分娩时：剖宫产指征显示"无"
	$("#baseBirthType").change(function(){
		var tiresType = $(this).children('option:selected').val();
		if(tiresType == '1'){
			$("#basePgcIndication").val("无");
			$("#baseBirthType2").attr("disabled","disabled");
			$("#baseBirthType2").val("");
		}else if(tiresType == '2' || tiresType == '3' || tiresType == '4' || tiresType == '5' || tiresType == ''){
			$("#baseBirthType2").attr("disabled","disabled");
			$("#baseBirthType2").val("");
			if($("#basePgcIndication").val() == "无") {
				$("#basePgcIndication").val("");
			};
		}else if(tiresType == '6'){
			$("#baseBirthType2").removeAttr("disabled");
			if($("#basePgcIndication").val() == "无") {
				$("#basePgcIndication").val("");
			};
		};
	});
	$("#baseBirthType2").attr("disabled","disabled");
	if($("#baseBirthType").val() == '1') {
		$("#basePgcIndication").val("无");
		$("#baseBirthType2").attr("disabled","disabled");
		$("#baseBirthType2").val("");
	}else if($("#baseBirthType").val() == '6') {
		$("#baseBirthType2").removeAttr("disabled");
	}

	// 引产checkbox 联动
	if(!$("#baseSurgeryType_check_1").is(":checked")){//没选中
		$("#baseSurgeryDetail").attr("disabled","disabled");
		$("#baseSurgeryDetail").val("");
	}
	$("#baseSurgeryType_check_1").change(function() {
		if($("#baseSurgeryType_check_1").is(":checked")){//选中
			$("#baseSurgeryDetail").removeAttr("disabled");
		}else{
			$("#baseSurgeryDetail").attr("disabled","disabled");
			$("#baseSurgeryDetail").val("");
		}
	});

	// 死亡checkbox 联动
	if(!$("#baseMaterEndingDeath").is(":checked")){//没选中
		$("#baseBirthBirthingDetail").attr("disabled","disabled");
		$("#baseBirthBirthingDetail").val("");
	}
	$("input[name='baseMaterEndingLiveOrDeath']").click(function() {
		if($("#baseMaterEndingDeath").is(":checked")){//选中
			$("#baseBirthBirthingDetail").removeAttr("disabled");
		}else{
			$("#baseBirthBirthingDetail").attr("disabled","disabled");
			$("#baseBirthBirthingDetail").val("");
		}
	});

	// 其他checkbox 联动
	if(!$("#baseSurgeryType_check_5").is(":checked")){//选中
		$("#baseSurgeryDetail2").attr("disabled","disabled");
		$("#baseSurgeryDetail2").val("");
	}
	$("#baseSurgeryType_check_5").change(function() {
		if($("#baseSurgeryType_check_5").is(":checked")){//选中
			$("#baseSurgeryDetail2").removeAttr("disabled");
		}else{
			$("#baseSurgeryDetail2").attr("disabled","disabled");
			$("#baseSurgeryDetail2").val("");
		}
	});

	// 分娩时间
	$("#baseTimeHour").change(function() {
		const minu = $("#baseTimeMinuters").val();
		const year = $("#baseTime").val();
		if(common.isEmpty(year)) {
			this.value = "";
			layer.alert("请选择分娩时间！");
		}
	});
	$("#baseTimeMinuters").change(function() {
		const hour = $("#baseTimeHour").val();
		const year = $("#baseTime").val();
		if(common.isEmpty(year)) {
			this.value = "";
			layer.alert("请选择分娩时间！");
		}else if(common.isEmpty(hour)) {
			this.value = "";
			layer.alert("请填写分娩时间：小时！");
		}else if(common.isEmpty(this.value)) {
			if(!common.isEmpty(hour)) {
				this.value = "0";
			}
		}
	});
	$("#baseTime").keyup(function() {
		if(common.isEmpty(this.value)) {
			$("#baseTimeMinuters").val("");
			$("#baseTimeHour").val("");
		}
	});
	$("#baseTimeHour").keyup(function() {
		const minu = $("#baseTimeMinuters");
		if(common.isEmpty(this.value)) {
			$("#baseTimeMinuters").val("");
		}else {
			if(common.isEmpty(minu.val())) {
				minu.val("0");
			}
		}
	});
}

/**
 * 删除诊断
 */
function emptyBirthBaseDiease() {
    $("[id^='baseComplicationPrenatal_hidden_']").remove();
    $("[id^='baseComplicationBirthing_hidden_']").remove();
    $("[id^='baseComplicationAfterBirthing_hidden_']").remove();
    $("[id^='baseComplicationsMedical_hidden_']").remove();
}

/**
 * 编辑后的数据回显
 */
function editBirthBaseShow(birthBasePojo) {
	common.clearForm("birthBaseInfoForm");
	// 清空分娩信息诊断框
    emptyBirthBaseDiease();
	if(!common.isEmpty(birthBasePojo)){
		$("#base_birthId").val(birthBasePojo.birthId);
		$("#birthBaseInfoForm").jsonToForm(birthBasePojo);
		// 时和分用jsonToForm方法，为0时不回显，所以在这里又赋值一次
		$("#baseTimeHour").val(birthBasePojo.baseTimeHour);
		$("#baseTimeMinuters").val(birthBasePojo.baseTimeMinuters)
		$("#baseId").val(birthBasePojo.baseId);
		$("#lmpId").val(birthBasePojo.baseLmp);
		$("#baseIscritical").val(birthBasePojo.baseIscritical);
		if(birthBasePojo.basePerineumState != 2){
			$("#basePerineumHurt").attr("disabled","disabled");
		}else{
			$("#basePerineumHurt").removeAttr("disabled");
		}
		//手术
		var baseSurgeryType = birthBasePojo.baseSurgeryType;
		if(!common.isEmpty(baseSurgeryType)){
			var surgeryArray = baseSurgeryType.split(",");
			$.each(surgeryArray, function(i, data){
				$("#baseSurgeryType_check_"+data).attr("checked",'true');
				if(data == 1){
					$("#baseSurgeryDetail").removeAttr("disabled");
				}
			});
		}
		//下拉列表处理
		$("#baseComplicationHypertension").val(birthBasePojo.baseComplicationHypertension);
		$("#baseComplicationAnemia").val(birthBasePojo.baseComplicationAnemia);
		$("#baseComplicationPrenatalCal").val(birthBasePojo.baseComplicationPrenatalCal);
		$("#baseBirthEndingLiveBirths").val(birthBasePojo.baseBirthEndingLiveBirths);
		$("#baseBirthEndingDeathBabys").val(birthBasePojo.baseBirthEndingDeathBabys);
		$("#baseBirthEndingDeathBirths").val(birthBasePojo.baseBirthEndingDeathBirths);
		$("#baseBirthEndingPerinatal").val(birthBasePojo.baseBirthEndingPerinatal);
		$("#baseDeathBefor28").val(birthBasePojo.baseDeathBefor28);

		//产程
		if(!common.isEmpty(birthBasePojo.baseChildBirthFist)){
			$("#baseChildBirthFist_hour").val(birthBasePojo.baseChildBirthFist.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthFist.substring(0,birthBasePojo.baseChildBirthFist.indexOf("时")));
			$("#baseChildBirthFist_minutes").val(birthBasePojo.baseChildBirthFist.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthFist.substring(birthBasePojo.baseChildBirthFist.indexOf("时")+1,birthBasePojo.baseChildBirthFist.indexOf("分")));
		}
		if(!common.isEmpty(birthBasePojo.baseChildBirthSecond)){
			$("#baseChildBirthSecond_hour").val(birthBasePojo.baseChildBirthSecond.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthSecond.substring(0,birthBasePojo.baseChildBirthSecond.indexOf("时")));
			$("#baseChildBirthSecond_minutes").val(birthBasePojo.baseChildBirthSecond.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthSecond.substring(birthBasePojo.baseChildBirthSecond.indexOf("时")+1,birthBasePojo.baseChildBirthSecond.indexOf("分")));
		}
		if(!common.isEmpty(birthBasePojo.baseChildBirthThrid)){
			$("#baseChildBirthThrid_hour").val(birthBasePojo.baseChildBirthThrid.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthThrid.substring(0,birthBasePojo.baseChildBirthThrid.indexOf("时")));
			$("#baseChildBirthThrid_minutes").val(birthBasePojo.baseChildBirthThrid.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthThrid.substring(birthBasePojo.baseChildBirthThrid.indexOf("时")+1,birthBasePojo.baseChildBirthThrid.indexOf("分")));
		}
		if(!common.isEmpty(birthBasePojo.baseChildBirthSum)){
			$("#baseChildBirthSum_hour").val(birthBasePojo.baseChildBirthSum.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthSum.substring(0,birthBasePojo.baseChildBirthSum.indexOf("时")));
			$("#baseChildBirthSum_minutes").val(birthBasePojo.baseChildBirthSum.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthSum.substring(birthBasePojo.baseChildBirthSum.indexOf("时")+1,birthBasePojo.baseChildBirthSum.indexOf("分")));
		}
		if(!common.isEmpty(birthBasePojo.baseAfterBirthingBreastMilkl)){
			$("#baseAfterBirthingBreastMilkl_hour").val(birthBasePojo.baseAfterBirthingBreastMilkl.indexOf("时") == -1 ? "" : birthBasePojo.baseAfterBirthingBreastMilkl.substring(0,birthBasePojo.baseAfterBirthingBreastMilkl.indexOf("时")));
			$("#baseAfterBirthingBreastMilkl_minutes").val(birthBasePojo.baseAfterBirthingBreastMilkl.indexOf("分") == -1 ? "" : birthBasePojo.baseAfterBirthingBreastMilkl.substring(birthBasePojo.baseAfterBirthingBreastMilkl.indexOf("时")+1,birthBasePojo.baseAfterBirthingBreastMilkl.indexOf("分")));
		}

		//产前合并症
        var baseArray = getSelectArray(birthBasePojo.baseComplicationPrenatal,productAllMap);
        selectOperation.init("baseComplicationPrenatal_div","baseComplicationPrenatal_hidden",baseArray);
        $("#baseComplicationPrenatal_hidden").val(birthBasePojo.baseComplicationPrenatal);
        $("#baseComplicationPrenatal").val("");
        //产中并发症
        var czbfzArray = getSelectArray(birthBasePojo.baseComplicationBirthing,productAllMap);
        selectOperation.init("baseComplicationBirthing_div","baseComplicationBirthing_hidden",czbfzArray);
        $("#baseComplicationBirthing_hidden").val(birthBasePojo.baseComplicationBirthing);
        $("#baseComplicationBirthing").val("");
        //产后并发
        var chbfArray = getSelectArray(birthBasePojo.baseComplicationAfterBirthing,productAllMap);
        selectOperation.init("baseComplicationAfterBirthing_div","baseComplicationAfterBirthing_hidden",chbfArray);
        $("#baseComplicationAfterBirthing_hidden").val(birthBasePojo.baseComplicationAfterBirthing);
        $("#baseComplicationAfterBirthing").val("");
        //内科并发
        var nkbfArray = getSelectArray(birthBasePojo.baseComplicationsMedical,productAllMap);
        selectOperation.init("baseComplicationsMedical_div","baseComplicationsMedical_hidden",nkbfArray);
        $("#baseComplicationsMedical_hidden").val(birthBasePojo.baseComplicationsMedical);
        $("#baseComplicationsMedical").val("");
        // 分娩方式
        let tiresType = $("#baseBirthType").val();
        if(tiresType == '6') {// 其他
        	$("#baseBirthType2").attr("disabled",false);
        } else {
        	$("#baseBirthType2").attr("disabled",true);
        };
        // 手术
        const shoushu_check = $("#baseSurgeryType_check_5").is(":checked");
        if(shoushu_check) {
        	$("#baseSurgeryDetail2").attr("disabled",false);
        }else {
        	$("#baseSurgeryDetail2").attr("disabled",true);
        };
        // 产妇结局
        const jieju_radio = $("#baseMaterEndingDeath").is(":checked");
        if(jieju_radio) {
        	$("#baseBirthBirthingDetail").attr("disabled",false);
        }else {
        	$("#baseBirthBirthingDetail").attr("disabled",true);
        };
	};
}

function saveBaseInfo() {
	//第一产程
	var baseChildBirthFist ="";
	if(!common.isEmpty($("#baseChildBirthFist_hour").val())) baseChildBirthFist += $("#baseChildBirthFist_hour").val() + "时";
	if(!common.isEmpty($("#baseChildBirthFist_minutes").val())) baseChildBirthFist += $("#baseChildBirthFist_minutes").val()+ "分";
	$("#baseChildBirthFist").val(baseChildBirthFist);

	//第二产程
	var baseChildBirthSecond ="";
	if(!common.isEmpty($("#baseChildBirthSecond_hour").val())) baseChildBirthSecond += $("#baseChildBirthSecond_hour").val() + "时";
	if(!common.isEmpty($("#baseChildBirthSecond_minutes").val())) baseChildBirthSecond += $("#baseChildBirthSecond_minutes").val()+ "分";
	$("#baseChildBirthSecond").val(baseChildBirthSecond);

	//第三产程
	var baseChildBirthThrid ="";
	if(!common.isEmpty($("#baseChildBirthThrid_hour").val())) baseChildBirthThrid += $("#baseChildBirthThrid_hour").val() + "时";
	if(!common.isEmpty($("#baseChildBirthThrid_minutes").val())) baseChildBirthThrid += $("#baseChildBirthThrid_minutes").val()+ "分";
	$("#baseChildBirthThrid").val(baseChildBirthThrid);

	//总产程
	var baseChildBirthSum ="";
	if(!common.isEmpty($("#baseChildBirthSum_hour").val())) baseChildBirthSum+= $("#baseChildBirthSum_hour").val() + "时";
	if(!common.isEmpty($("#baseChildBirthSum_minutes").val())) baseChildBirthSum+= $("#baseChildBirthThrid_minutes").val()+ "分";
	$("#baseChildBirthSum").val(baseChildBirthSum);

	//开奶时间
	var baseAfterBirthingBreastMilkl ="";
	if(!common.isEmpty($("#baseAfterBirthingBreastMilkl_hour").val())) baseAfterBirthingBreastMilkl += $("#baseAfterBirthingBreastMilkl_hour").val() + "时";
	if(!common.isEmpty($("#baseAfterBirthingBreastMilkl_minutes").val())) baseAfterBirthingBreastMilkl += $("#baseAfterBirthingBreastMilkl_minutes").val()+ "分";
	$("#baseAfterBirthingBreastMilkl").val(baseAfterBirthingBreastMilkl);

	var baseSurgeryType_check = $("input:checkbox[name=baseSurgeryType]:checked");
	var baseSurgeryType_value="";
	for(var i=0; i<baseSurgeryType_check.length; i++){
		baseSurgeryType_value += ","+baseSurgeryType_check[i].value;
	}
	baseSurgeryType_value = baseSurgeryType_value.substring(1);
	$("#baseSurgeryType").val(baseSurgeryType_value);
	$("#base_birthId").val($("#birthId").val());
	var postURL = URL.get("BirthEnding.BIRTHENDING_BASEINFO_ADD");
	var sendInfoParams = $("#birthBaseInfoForm").serialize();
    ajax.post(postURL, sendInfoParams, dataType.json,function(data) {
        const result = data.value;
        if(!common.isEmpty(result) && result.length == 1) {
        	$("#base_birthId").val(result.birthId);
        	$("#baseId").val(result.baseId);
        }
    },false,false);
}

/**
 * 自动保存
 */
function autoSave() {
	$("body").on("click", function(){
		// 保存分娩结局_分娩信息
		var prescriptionInfoDivLength = $(":focus").parents("div[id='birthBaseInfo_info_div']").length;
		if(prescriptionInfoDivLength == 1){
			focusDivName = "birthBaseInfo_info_div";
			return ;
		} else{
			if(focusDivName == "birthBaseInfo_info_div"){
				saveBaseInfo();
			};
		};
	});
}

$(document).ready(function() {
	// 初始化分娩时间 日期选择插件
	common.initDate(null,null,null,"#baseTime");
	// 初始化并发症（4个）
	initAutoCompletion();
	// 初始化监听事件
	initListenEvent();
	// 自动保存
	autoSave();
});
