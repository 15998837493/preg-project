
/**
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 * 
 * 阅读说明：
 * 
 * 本js为公共脚本，由多个页面的js组成，多个页面的js写在同一个js中，目的是为了避免重复定义多次创建
 * 为了阅读方便，js以页面为单位，注释表明是哪个jsp页面的js
 * 
 */
//======================================【初始化方法】========================================
//焦点div
var focusDivName = "none";

/**
 * JSP页面--gudie_top.jsp  定义按钮点击事件
 */
$().ready(function (){
	//刷新父页面诊疗平台列表,原登记页面没有此方法不能调用。
	opener.refreshDataTable();
	// 初始化--加入必填项提示
	common.requiredHint("complateWeightForm", complateWeightOptions);
	$("#complateWeightForm").validate(complateWeightOptions);
	// 获取问诊主页面
	getInterrogationView();
	
	// 初始化末次月经的插件
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	common.initDate(null,null,null,"#adjustLmp",preArchiveJson.lmp);
	$("#adjustLmp").val(preArchiveJson.lmp);
	$("#adjustLmp").datetimepicker("setEndDate",nowDate);
	$("#adjustLmp").datetimepicker("setStartDate",getDueDateStr(-279));// 距现在时间280天(40周)以前，预产期不能是当天日期所以*279
	
	// 定义自动保存
	$("body").on("click", function(){
		// 接诊信息保存
/*		var diagnosisInfoDivLength = $(":focus").parents("div[id='saveDiagnosis_info_div']").length;
    	if(diagnosisInfoDivLength == 1){
    		focusDivName = "saveDiagnosis_info_div";
    		return ;
    	} else{
    		if(focusDivName == "saveDiagnosis_info_div"){
    			$("#diagnosisUpdateForm").submit();
    			updateDiagnosisStatus();
    			console.log("保存接诊信息");
    		}
    	}*/
		
		// 接诊信息保存
		var obstetricalInfoDivLength = $(":focus").parents("div[id='saveObstetrical_info_div']").length;
    	if(obstetricalInfoDivLength == 1){
    		focusDivName = "saveObstetrical_info_div";
    		return ;
    	} else{
    		if(focusDivName == "saveObstetrical_info_div"){
    			$("#diagnosisObstetricalUpdateForm").submit();
    			updateDiagnosisStatus();
    			console.log("保存接诊信息");
    		}
    	}
		
    	// 保存营养处方
    	var prescriptionInfoDivLength = $(":focus").parents("div[id='savePrescription_info_div']").length;
    	if(prescriptionInfoDivLength == 1){
    		focusDivName = "savePrescription_info_div";
    		return ;
    	} else{
    		if(focusDivName == "savePrescription_info_div"){
    			console.log("保存营养处方");
    			updateDiagnosisStatus();
    			saveOrderDetailList();
    		}
    	}
    	
    	focusDivName = "none";
    });
	
	// 定义点击事件
	$("#dropdown-div button").click(function(){
		// 暂时没有作用，为了自动保存写的
		$("#guide_customerinfo_div").click();
		// 回到顶部
		document.body.scrollTop = document.documentElement.scrollTop = 0;
		
		$("#dropdown-div button").removeClass("button-action");
		$(this).addClass("button-action");
		if(this.id == "diagnosisRecordShow"){
			$("#guide_customerinfo_div").hide();
		} 
		if(this.id == "interrogation" || this.id == "doctor"){
			$("#guide_customerinfo_div").show();
		}
		
		var id = this.id;
		// 问诊
		if(id == "interrogation"){
			getInterrogationView();
		}
		// 医嘱
		if(id == "doctor"){
			getDoctorView();
		}
		// 营养病历
		if(id == "diagnosisRecordShow"){
			getSummaryView();
		}
		// 打印营养病历
		if(id == "diagnosisRecordPDF"){
			$("#itemModal").modal("show");
		}
	});
	
	$("#dropdown-div a").click(function(){
		
		var id = this.id;
		// 宫高腹围对照表
		if(id == "compare"){
			openPregTools('compare');
		}
		// 胎龄计算器（B超）
		if(id == "calculator"){
			openPregTools('calculator');
		}
		// 体重计算器
		if(id == "weightTool"){
			weightTools();
		}
		// 查询系统营养评价
		if(id == "diagnosisItem"){
			queryDiagnosisReport();
		}
		// 查询历次就诊记录
		if(id == "diagnosisHistory"){
			queryDiagnosisHistory();
		}
		// 实验室指标收集
		if(id == "collectItem"){
			collectItem();
		}
		// 膳食方案
		if(id == "createDietPlan"){
			createDietPlan();
		}
		// 教育课程方案
		if(id == "createCoursePlan"){
			createCoursePlan();
		}
		// 复查预约
		if(id == "diagnosisReview"){
			openReview();
		}
	});
	
	// 初始化websocket
	common.initWs(function(message){
		var data = $.parseJSON(message.data);
		if(data.type == "inspect"){
			refreshDiagnosisInspectInfo();
			layer.alert(data.value);
		}
		if(data.type == "diagnosisInfo"){
			var diagnosisInfo = $.parseJSON(data.value);
			diagnosisJson.diagnosisCustWeight = diagnosisInfo.diagnosisCustWeight;
			diagnosisJson.diagnosisSystolic = diagnosisInfo.diagnosisSystolic;
			diagnosisJson.diagnosisDiastolic = diagnosisInfo.diagnosisDiastolic;			
			diagnosisJson.diagnosisMain = diagnosisInfo.diagnosisMain;
			diagnosisJson.diagnosisBaby = diagnosisInfo.diagnosisBaby;
			diagnosisJson.preg_top_week = diagnosisInfo.preg_top_week;
			diagnosisJson.obstetricalFundalHeight = diagnosisInfo.obstetricalFundalHeight;
			diagnosisJson.obstetricalAbdominalPerimeter = diagnosisInfo.obstetricalAbdominalPerimeter;			
			diagnosisJson.preg_bottom_week = diagnosisInfo.preg_bottom_week;
			diagnosisJson.obstetricalBabyWeight = diagnosisInfo.obstetricalBabyWeight;
			diagnosisJson.obstetricalBabyFemur = diagnosisInfo.obstetricalBabyFemur;
			diagnosisJson.obstetricalBabyBdp = diagnosisInfo.obstetricalBabyBdp;
			diagnosisJson.obstetricalBabyAbdominalPerimeter = diagnosisInfo.obstetricalBabyAbdominalPerimeter;
			diagnosisJson.fundal_standard = diagnosisInfo.fundal_standard;
			diagnosisJson.obstetricalFundalHeightResult = diagnosisInfo.obstetricalFundalHeightResult;			
			diagnosisJson.obstetricalAbdominalPerimeterResult = diagnosisInfo.obstetricalAbdominalPerimeterResult;
			diagnosisJson.obstetricalBabyWeightResult = diagnosisInfo.obstetricalBabyWeightResult;
			diagnosisJson.obstetricalBabyFemurResult = diagnosisInfo.obstetricalBabyFemurResult;
			diagnosisJson.obstetricalBabyBdpResult = diagnosisInfo.obstetricalBabyBdpResult;
			diagnosisJson.obstetricalBabyAbdominalPerimeterResult = diagnosisInfo.obstetricalBabyAbdominalPerimeterResult;
			if(!common.isEmpty(diagnosisInfo.obstetricalTopDate)) {
				diagnosisJson.obstetricalTopDate = diagnosisInfo.obstetricalTopDate;
				$("#obstetricalTopDate").val(diagnosisInfo.obstetricalTopDate);
			}
			if(!common.isEmpty(diagnosisInfo.obstetricalBottomDate)) {
				diagnosisJson.obstetricalBottomDate = diagnosisInfo.obstetricalBottomDate;
				$("#obstetricalBottomDate").val(diagnosisInfo.obstetricalBottomDate);
			}
			if(!common.isEmpty(diagnosisInfo.obstetricalAmnioticFluidTwo)) {
				diagnosisJson.obstetricalAmnioticFluidTwo = diagnosisInfo.obstetricalAmnioticFluidTwo;
				$("#obstetricalAmnioticFluidTwo").val(diagnosisInfo.obstetricalAmnioticFluidTwo);
			}
			if(!common.isEmpty(diagnosisInfo.obstetricalAmnioticFluidOne)) {
				diagnosisJson.obstetricalAmnioticFluidOne = diagnosisInfo.obstetricalAmnioticFluidOne;
				$("#obstetricalAmnioticFluidOne").val(diagnosisInfo.obstetricalAmnioticFluidOne);
			}			
			if(!common.isEmpty(diagnosisInfo.obstetricalAmnioticFluidThree)) {
				diagnosisJson.obstetricalAmnioticFluidThree = diagnosisInfo.obstetricalAmnioticFluidThree;
				$("#obstetricalAmnioticFluidThree").val(diagnosisInfo.obstetricalAmnioticFluidThree);
			}			
			if(!common.isEmpty(diagnosisInfo.obstetricalAmnioticFluidFour)) {
				diagnosisJson.obstetricalAmnioticFluidFour = diagnosisInfo.obstetricalAmnioticFluidFour;
				$("#obstetricalAmnioticFluidFour").val(diagnosisInfo.obstetricalAmnioticFluidFour);
			}
			if(!common.isEmpty(diagnosisInfo.obstetricalAmnioticFluid) && diagnosisInfo.obstetricalAmnioticFluid != 0) {
				diagnosisJson.obstetricalAmnioticFluid = diagnosisInfo.obstetricalAmnioticFluid;
				$("#obstetricalAmnioticFluid").val(diagnosisInfo.obstetricalAmnioticFluid);
			}
			$("#weight_input_text").val(diagnosisInfo.diagnosisCustWeight);
			$("#obstetricalDiagnosisSystolic").val(diagnosisInfo.diagnosisSystolic);
			$("#obstetricalDiagnosisDiastolic").val(diagnosisInfo.diagnosisDiastolic);
			$("#obstetricalDiagnosisMain").val(diagnosisInfo.diagnosisMain);
			$("#obstetricalBaby").val(diagnosisInfo.diagnosisBaby);
			$("#preg_top_week").html(diagnosisJson.preg_top_week);			
			$("#obstetricalFundalHeight").val(diagnosisInfo.obstetricalFundalHeight);
			$("#obstetricalAbdominalPerimeter").val(diagnosisInfo.obstetricalAbdominalPerimeter);
			$("#preg_bottom_week").html(diagnosisJson.preg_bottom_week);
			$("#obstetricalBabyWeight").val(diagnosisInfo.obstetricalBabyWeight);
			$("#obstetricalBabyFemur").val(diagnosisInfo.obstetricalBabyFemur);
			$("#obstetricalBabyBdp").val(diagnosisInfo.obstetricalBabyBdp);
			$("#obstetricalBabyAbdominalPerimeter").val(diagnosisInfo.obstetricalBabyAbdominalPerimeter);
			
			$("#fundal_standard").html(diagnosisInfo.fundal_standard);
			$("#obstetricalFundalHeightResult").val(diagnosisInfo.obstetricalFundalHeightResult);
			
			$("#abdominal_standard").html(diagnosisInfo.abdominal_standard);
			$("#obstetricalAbdominalPerimeterResult").val(diagnosisInfo.obstetricalAbdominalPerimeterResult);
			
			$("#foetus_weight").html(diagnosisInfo.foetus_weight);
			$("#obstetricalBabyWeightResult").val(diagnosisInfo.obstetricalBabyWeightResult);
			
			$("#foetus_femur_length").html(diagnosisInfo.foetus_femur_length);
			$("#obstetricalBabyFemurResult").val(diagnosisInfo.obstetricalBabyFemurResult);
			
			$("#foetus_biparietal_diameter").html(diagnosisInfo.foetus_biparietal_diameter);
			$("#obstetricalBabyBdpResult").val(diagnosisInfo.obstetricalBabyBdpResult);
			
			$("#foetus_fetal_abdominal").html(diagnosisInfo.foetus_fetal_abdominal);
			$("#obstetricalBabyAbdominalPerimeterResult").val(diagnosisInfo.obstetricalBabyAbdominalPerimeterResult);
		}
		if(data.type == "diagnosisQuota"){
			queryClinicalItem();
			layer.alert(data.value);
		}
	});
});

//======================================【工具条按钮的方法】========================================
/**
 * JSP页面--gudie_top.jsp  问诊
 */
function getInterrogationView(){
	// 问诊主页面
	var index = layer.loading();
	var action = URL.get("Platform.PLATFORM_RECEIVE_MAIN") + "?custId="+$("#custId").val()+"&diagnosisId="+$("#diagnosisId").val();
	ajax.getHtml(action, "platform_content_div", null, false, false);
	layer.close(index);
}

/**
 * JSP页面--gudie_top.jsp  医嘱
 */
function getDoctorView(){
	// 医嘱主页面
	var index = layer.loading();
	var action = URL.get("Platform.PLAN_MAIN_VIEW") + "?custId="+$("#custId").val()+"&diagnosisId="+$("#diagnosisId").val();
	ajax.getHtml(action, "platform_content_div", null, false, false);
	layer.close(index);
}

/**
 * JSP页面--gudie_top.jsp  营养病历
 */
function getSummaryView(){
	// 营养病历页面
	var index = layer.loading();
	var action = URL.get("Platform.DIAGNOSIS_SUMMARY_PAGE") + "?diagnosisId="+$("#diagnosisId").val();
	ajax.getHtml(action, "platform_content_div", null, false, false);
	layer.close(index);
}

//======================================【个人信息】========================================
/**
 * JSP页面--guide_page.jsp 查询患者孕期建档信息
 */
function getPregArchive(){
	common.openWindow(PublicConstant.basePath + "/page/platform/receive/receive_archive.jsp");
}

/**
 * JSP页面--new_diagnosis_view.jsp 初诊/编辑建档
 */
function newlyDiagnosedMethod(){
	common.openWindow(URL.get("Customer.DIAGNOSIS_INITAL_VIEW_GET") + "?diagnosisId="+$("#diagnosisId").val());
}

/**
 * JSP页面--guide_page.jsp 计算n天以后的日期
 */
function getDueDateStr(AddDayCount, date) {
	var dd = common.isEmpty(date)?new Date():new Date(date);
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期    
	var y = dd.getFullYear();     
	var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0    
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0    
	return y+"-"+m+"-"+d;     
}

/**
 * JSP页面--guide_page.jsp 调整末次月经
 */
function showAdjustByLmpModal(){
	$("#adjustLmp").val(preArchiveJson.lmp);
	$("#adjustLmp").datetimepicker("update");
	$("#ajdustLmpReason").val(common.isEmpty(recordJson)?'':recordJson.adjustReason);
	$("#adjustByLmpModal").modal('show');
}

/**
 * JSP页面--guide_page.jsp 调整孕周数
 */
function showAdjustByWeeksModal(){
	$("#ajdustWeeks").val(diagnosisJson.diagnosisGestationalWeeks.split("+")[0]);
	$("#ajdustDays").val(diagnosisJson.diagnosisGestationalWeeks.split("+")[1]);
	$("#ajdustWeeksReason").val(common.isEmpty(recordJson)?'':recordJson.adjustReason);
	$("#adjustByWeeksModal").modal('show');
}

/**
 * JSP页面--guide_page.jsp 通过孕周数保存调整记录
 */
function saveAdjustByWeek(){
	if(common.isEmpty($("#ajdustWeeks").val()) || common.isEmpty($("#ajdustDays").val())){
		layer.alert("周数和天数都不可以为空");
		return;
	}
	var week = parseInt($("#ajdustWeeks").val());
	var day = parseInt($("#ajdustDays").val());
	if(week > 40){
		layer.alert("周数最大为40周");
		return;
	}
	if(day > 6){
		layer.alert("天数最大为6天");
		return;
	}
	var spendDays = common.accAdd(week * 7, day);
	if(spendDays > 280){
		layer.alert("孕周数最大为40+0天");
		return;
	}
	var residueDay = 280 - spendDays;
	var newDue = getDueDateStr(residueDay);
	$("#adjustRecirdForm [name='dueDateNew']").val(newDue);
	$("#adjustRecirdForm [name='adjustReason']").val($("#ajdustWeeksReason").val());
	submitAdjustRecirdForm();
	$("#adjustByWeeksModal").modal("hide");
}

/**
 * JSP页面--guide_page.jsp 通过末次月经保存调整记录
 */
function saveAdjustByLmp(){
	var newDue = getDueDateStr(280, $("#adjustLmp").val());
	$("#adjustRecirdForm [name='dueDateNew']").val(newDue);
	$("#adjustRecirdForm [name='adjustReason']").val($("#ajdustLmpReason").val());
	submitAdjustRecirdForm();
	$("#adjustByLmpModal").modal("hide");
}

/**
 * JSP页面--guide_page.jsp 调整记录表单提交
 */
function submitAdjustRecirdForm(){
	$("#adjustRecirdForm").ajaxPost(dataType.json,function(data){
		location.reload();
	}, false, false);
}

//调整记录的datatable
var adjustRecordOption = {
		id: "adjustRecordListTable",
		form:"adjustQueryForm",
		columns:[
		  		{"data": "adjustDate","sClass": "text-center"},
		  		{"data": "lmpDateOld","sClass": "text-center"},
		  		{"data": "gestationalWeeksOld","sClass": "text-center",
					"render":  function (data, type, row, meta) {
						return pregnancy.gestationalWeeksSupHtml(data);
					}
			  	},
		  		{"data": "dueDateOld","sClass": "text-center"},
		 		{"data": "lmpDateNew","sClass": "text-center"},
		 		{"data": "gestationalWeeksNew","sClass": "text-center",
					"render":  function (data, type, row, meta) {
						return pregnancy.gestationalWeeksSupHtml(data);
					}
			  	},
		 		{"data": "dueDateNew","sClass": "text-center"},
		 		{"data": null,"sClass": "text-center",
					"render":  function (data, type, row, meta) {
			      		return getContentDetail(data.id, data.adjustReason);
			      	}
			 	}
		],
		async: false,
		loading: false
};

/**
 * JSP页面--guide_page.jsp 查询调整记录
 */
function showPregAdjustRecordList(){
	datatable.table(adjustRecordOption);
	$("#adjustRecordModal").modal("show");
}

//======================================【guide_page.jsp 页面】========================================

/**
 * JSP页面--guide_page.jsp 检验数据
 */
function checkNum(obj) {
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
    }
    if (obj != null) {
        //检查小数点后是否对于两位
        if (obj.value.toString().split(".").length > 1 && obj.value.toString().split(".")[1].length > 1) {
            layer.msg('小数点后多于一位！');
            obj.value = "";
        }
    }
};

/**
 * 更新接诊状态
 */
function updateDiagnosisStatus(){
	// 如果保存过  更新接诊状态
	if(diagnosisJson.diagnosisStatus != 2){
		var url = URL.get("Platform.UPDATE_DIAGNOSIS_STATUS");
		var params = "diagnosisId=" + diagnosisJson.diagnosisId;
		ajax.post(url, params, dataType.json, function(data){
			diagnosisJson.diagnosisStatus = 2;
			// 刷新诊疗平台一览页
			opener.refreshDataTable();
	    	console.log("更新接诊状态");
		}, false, false);
	}
}	

/**
 * 补充体重
 */
var complateWeightOptions = {
	rules: {
		diagnosisCustWeight:{required: true}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element); 
	},
	success: $.noop,
	submitHandler: function() {
		$("#complateWeightForm").ajaxPost(dataType.json,function(data){
			$("#complateArticalModal").modal("hide");
			$("input[name='diagnosisCustWeight']").val($("#diagnosisCustWeight").val());
		});
	}
};

//======================================【guide_top.jsp 页面】========================================

/**
 * JSP页面--gudie_top.jsp  实验室指标收集
 */
function collectItem(){
	common.openWindow(URL.get("Platform.PLAN_GET_CHECK_ITEMS") + "?diagnosisId="+$("#diagnosisId").val());
}

/**
 * JSP页面--gudie_top.jsp  创建膳食方案
 */
function createDietPlan(){
	common.openWindow(URL.get("Platform.RECEIVE_PLAN_ADJUST") + "?diagnosisId="+$("#diagnosisId").val());
}

/**
 * JSP页面--gudie_top.jsp  创建教育课程方案
 */
function createCoursePlan(){
	common.openWindow(URL.get("Platform.PLAN_COURSE") + "?diagnosisId="+$("#diagnosisId").val());
}

/**
 *JSP页面--order_view.jsp  处方管理
 */
function orderManagerMethod(){
	common.openWindow(URL.get("Customer.PLAN_GET_ORDER_MANAGER") + "?diagnosisId="+$("#diagnosisId").val());
}

/**
 * JSP页面--gudie_top.jsp  打开预约查询窗口
 */
function openReview(){
	common.openWindow(URL.get("Platform.PLAN_GET_BOOKING") + "?custId="+$("#custId").val());
}

/**
 * 
 * JSP页面--gudie_top.jsp  打开孕期工具(optionType=compare:宫高腹围对比窗口, optionType=calculator:胎龄计算器)
 */
function openPregTools(optionType){
	var lmp = $("#custlmp").text();
	if(!common.isEmpty(lmp)){
		lmp = lmp.split("+")[0];
	}
	common.openWindow(URL.get("Platform.TRANSFORM_JUMP") + "?custlmp="+lmp+"&type="+optionType);
}

/**
 * JSP页面--gudie_top.jsp 历史营养病历
 */
function queryDiagnosisHistory(){
	common.openWindow(URL.get("Platform.DIAGNOSIS_RECORD") + "?custId=" + $("#custId").val());
}

/**
 * JSP页面--gudie_top.jsp 本次干预方案
 */
function getPlanPdf(){
	var codes = "P01001,P01002,P01003,P01004,P01005,P01006";
	var url = URL.get("Platform.RECEIVE_CREATE_PDF");
	var params = "codes=" + codes +"&diagnosisId=" + $("#diagnosisId").val();
	ajax.post(url, params, dataType.json, function(data){
    	common.openWindow(PublicConstant.basePath + "/" + data.value);
	}, true, false, true);
}

/**
 * JSP页面--gudie_top.jsp 查询检测报告
 */
function queryDiagnosisReport(){
	common.openWindow(URL.get("Platform.QUERY_REPORT_VIEW") + "?custId=" + $("#custId").val());
}

/**
 * JSP页面--gudie_top.jsp 孕期体重计算器
 */
function weightTools(){
	common.openWindow(PublicConstant.basePath + "/page/platform/tool/tool_weight.jsp");
}

/**
 * JSP页面--gudie_top.jsp 营养病例小节
 */
function showDiagnosisSummary(){
	common.openWindow(URL.get("Platform.DIAGNOSIS_SUMMARY_PAGE")+"?diagnosisId=" + $("#diagnosisId").val());
}
