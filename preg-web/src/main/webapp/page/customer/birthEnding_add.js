//焦点div
var focusDivName = "none";
var archivesTable;// table，注意命名不要重复
$.validator.addMethod("babyWeekNumber",
function(value, element) {
    if (element.value.trim() == "") {
        return true;
    }
    return element.value <= 40;
},
'胎儿周数最大为40周');

$.validator.addMethod("babyDayNumber",
function(value, element) {
    if (element.value.trim() == "") {
        return true;
    }
    return element.value <= 6;
},
'额外天数最大为6天');


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

//配置或者建档记录datatable
var archivesTableOptions = {
	id: "archivesTable",
	form: "archivesListForm",
	bServerSide: true,
    async: false,
    bSort:false,
	columns: [
		{"data": "id","sClass": "text-center","orderable": false,
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "createDatetime","sClass": "text-center" , 
		 	"render": function(data, type, row, meta) {
		 		return data?data.substring(0,10):"————";
			}
		},
		{"data": "encyesisSituation","sClass": "text-center",},
		{"data": "embryoNum","sClass": "text-center",
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}},
		{"data": "pregnancyDueDate","sClass": "text-center","orderable": false,},
		{"data": "userName","sClass": "text-center","orderable": false,},
		{"data": null,"sClass": "text-center","orderable": false,
			"render": function(data, type, row, meta) {
				return "<a onclick='setArchivesToBirth(\""+data.id+"\",\""+data.lmp+"\""+data.pregnancyNum+"\""+data.birthNum+"\")' style='cursor:pointer;'>选择</a>";
			}
		}
	],
	/*rowClick: function(data, row){
		checkedData = data;
		checkedRow = row;
		$("#custId").val(data.custId);
	},*/
	aaSorting: [],
	condition : "",
};

/**
 * 根据分娩日期计算孕周
 * @param lmpDate
 */
function getCustLmp(birthDate, lmpDate){
	var lmp = "";
	if(!common.isEmpty(lmpDate)){
		var time = new Date($("#baseTime").val()).getTime() - new Date(lmpDate).getTime();
		var pregnantDays = parseInt(time / (1000 * 60 * 60 * 24));
		var pregnantWeeks = pregnantDays / 7;
        var plusDays = pregnantDays % 7;
        lmp = Math.floor(pregnantWeeks) + "+" + plusDays;
	}
	return lmp;
}

/**
 * 删除添加的诊断的页面元素
 * @returns
 */
function removeExtraElement(){
	
	$("[id^='newBaby_tittle_']").remove();
	$("[id^='birthBabyInfo_info_div_']").remove();
	
	$("[id^='birthDiagnosis_hidden_']").remove();
	$("[id^='babyComplication_hidden_']").remove();
	$("[id^='baseComplicationPrenatal_hidden_']").remove();
	$("[id^='baseComplicationBirthing_hidden_']").remove();
	$("[id^='baseComplicationAfterBirthing_hidden_']").remove();
	$("[id^='baseComplicationsMedical_hidden_']").remove();
	$("[id^='disMotherDisagnosis_hidden_']").remove();
	$("[id^='disBabyDiagnosis_hidden_']").remove();
}

/**
 * 编辑
 * @param birthId
 * @returns
 */
function editBirthEnding(birthId){
	
	var url = URL.get("BirthEnding.BIRTHENDING_GET_DETAIL_BYBIRTHID");
	var params = "birthId="+birthId;
	ajax.post(url, params, "json", function(data) {
		var birthEnding = data.value.birthEndingPojo;
		var birthBasePojo = data.value.birthBasePojo;
        var birthBabyList = data.value.birthBabyList;
        var dischargedList = data.value.dischargedList;
		
        removeExtraElement();			//删除页面上的诊断
        showBirthEndingDiv('show');		//显示页面上元素
        initBirthEndingInfo();			//初始化
        if(birthEnding != undefined){
        	$("#birthEndingUpdateForm").jsonToForm(birthEnding);
        	$("#birthDiagnosisForm").jsonToForm(birthEnding);
        	if(birthEnding.birthAge != '' && birthEnding.birthAge != 0){
        		$("#birthAge").val(birthEnding.birthAge);
        		$("#birthAge_hidden").val(birthEnding.birthAge);
        	}
        	if(birthEnding.birthIsPregHospital == 1){
        		$("#birthIsPregHospital").attr("checked","true");
        		$("#birthPregHospital").val("");
        	}
        	if(birthEnding.birthIsThisHospital == 1){
        		$("#birthIsThisHospital").attr("checked","true");
        		$("#birthHospital").val("");
        	}
        	//入院诊断
            var babyComArray = getSelectArray(birthEnding.birthDiagnosis,productAllMap);
            selectOperation.init("diagnosis_div","birthDiagnosis_hidden",babyComArray);
            $("#birthDiagnosis_hidden").val(birthEnding.birthDiagnosis);
            $("#birthDiagnosis").val("");
        }
        if(birthBabyList !=[]){
        	$("input:radio[name=newBaby]").removeAttr("checked");
        	for(var i=0; i<birthBabyList.length ; i++){
        		newBaby(birthBabyList[i], birthBabyList[i].babyIsDeath, (i+1));
        	}
        	$('input[id$="_hidden"]').bind("change", function(){
        		var id = $(this).attr("id").substr(0, $(this).attr("id").indexOf("_hidden"))
        		$("#"+id).focus();
        	})
        	$("#liveAdd").attr("checked","checked");
        }
        
        $("#birthId").val(birthEnding.birthId);
        $("#custId").val(birthEnding.custId);
		$("#diag_birthId").val(birthEnding.birthId);
		$("#base_birthId").val(birthEnding.birthId);
		$("#dis_birthId").val(birthEnding.birthId);
		$("#baby_birthId").val(birthEnding.birthId);
		if(birthBasePojo != undefined){
			$("#birthBaseInfoForm").jsonToForm(birthBasePojo);
			
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
			if(baseSurgeryType != undefined){
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
			if(birthBasePojo.baseChildBirthFist != undefined){
				$("#baseChildBirthFist_hour").val(birthBasePojo.baseChildBirthFist.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthFist.substring(0,birthBasePojo.baseChildBirthFist.indexOf("时")));
				$("#baseChildBirthFist_minutes").val(birthBasePojo.baseChildBirthFist.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthFist.substring(birthBasePojo.baseChildBirthFist.indexOf("时")+1,birthBasePojo.baseChildBirthFist.indexOf("分")));
			}
			if(birthBasePojo.baseChildBirthSecond != undefined){
				$("#baseChildBirthSecond_hour").val(birthBasePojo.baseChildBirthSecond.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthSecond.substring(0,birthBasePojo.baseChildBirthSecond.indexOf("时")));
				$("#baseChildBirthSecond_minutes").val(birthBasePojo.baseChildBirthSecond.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthSecond.substring(birthBasePojo.baseChildBirthSecond.indexOf("时")+1,birthBasePojo.baseChildBirthSecond.indexOf("分")));
			}
			if(birthBasePojo.baseChildBirthThrid != undefined){
				$("#baseChildBirthThrid_hour").val(birthBasePojo.baseChildBirthThrid.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthThrid.substring(0,birthBasePojo.baseChildBirthThrid.indexOf("时")));
				$("#baseChildBirthThrid_minutes").val(birthBasePojo.baseChildBirthThrid.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthThrid.substring(birthBasePojo.baseChildBirthThrid.indexOf("时")+1,birthBasePojo.baseChildBirthThrid.indexOf("分")));
			}
			if(birthBasePojo.baseChildBirthSum != undefined){
				$("#baseChildBirthSum_hour").val(birthBasePojo.baseChildBirthSum.indexOf("时") == -1 ? "" : birthBasePojo.baseChildBirthSum.substring(0,birthBasePojo.baseChildBirthSum.indexOf("时")));
				$("#baseChildBirthSum_minutes").val(birthBasePojo.baseChildBirthSum.indexOf("分") == -1 ? "" : birthBasePojo.baseChildBirthSum.substring(birthBasePojo.baseChildBirthSum.indexOf("时")+1,birthBasePojo.baseChildBirthSum.indexOf("分")));
			}
			if(birthBasePojo.baseAfterBirthingBreastMilkl != undefined){
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
			
		}
		var dischargedPojo = dischargedList[0];
		if(dischargedList[0] != undefined){
			$("#disId").val(dischargedPojo.disId);
			$("#disRemark").val(dischargedPojo.disRemark);
			$("#disHemoglobin").val(dischargedPojo.disHemoglobin);
			//出院诊断_母亲
	        var m_cyzdArray = getSelectArray(dischargedPojo.disMotherDisagnosis,productAllMap);
	        selectOperation.init("disMotherDisagnosis_div","disMotherDisagnosis_hidden",m_cyzdArray);
	        $("#disMotherDisagnosis_hidden").val(dischargedPojo.disMotherDisagnosis);
	        $("#disMotherDisagnosis").val("");
	        
	        if(dischargedList !=[] && dischargedList.length >1){
	        	var disBabyArray = $("tr[id^=discharge_newbaby]");
	        	for(var i=0; i<disBabyArray.length; i++){
	        		var disBaby = disBabyArray[i];
	        		var disBabyNumber = disBaby.getAttribute("data");
	        		
        			$("#disBabyBloodSuger"+(disBabyNumber)).val(dischargedList[i+1].disBabyBloodSuger);
        			$("#disId_Id"+(disBabyNumber)).val(dischargedList[i+1].disId);
        			//出院诊断_新生儿
        			var b_cyzdArray = getSelectArray(dischargedList[i+1].disBabyDiagnosis,productAllMap);
        			selectOperation.init("disBabyDiagnosis_div"+(disBabyNumber),"disBabyDiagnosis"+disBabyNumber+"_hidden",b_cyzdArray);
        			$("#disBabyDiagnosis"+disBabyNumber+"_hidden").val(dischargedList[i+1].disBabyDiagnosis);
        			$("#disBabyDiagnosis"+(disBabyNumber)).val("");
	        	}
            }
		}
	});
}


/**
 * 查看
 * @param custId
 * @returns
 */
function showBirthEnding(birthId,custId){
	common.openWindow(URL.get("BirthEnding.BIRTHENDING_DETAIL") + "?birthId=" + birthId+"&custId="+custId+"&_="+Math.random());
}

/**
 * 删除
 * @param custId
 * @returns
 */
function deleteBirthEnding(birthId,nowTr){
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		if($("#birthId").val() == birthId){
			showBirthEndingDiv("init");
		}
		var url = URL.get("BirthEnding.BIRTHENDING_DELETE");
		var params = "birthId="+birthId;
		setTimeout(function() {
			ajax.post(url, params, "json", function(data) {
				$(nowTr).parent().parent().remove();
				if($("#birrhInfoTable_tbody").children("tr").length == 1){
					var noRowsTr = "<tr><td colspan=\"8\">无分娩记录</td></tr>";
					$("#birthInfoTable_add").before(noRowsTr);
				}
			});
			layer.close(index);
		},200);
	}, function(layerIndex){
		layer.close(layerIndex);
	});
}

/**
 * 展示隐藏页面具体内容
 * @param flag
 * @param birthEndingNum
 * @returns
 */
function showBirthEndingDiv(flag,birthEndingNum){
	if(flag == 'init'){
		$("#birthEndingTitle").hide();
		$("#birthEnding_div").hide();
		$("#dischargeIn_div").hide();
		$("#birthBaseInfo_div").hide();
		$("#birthBabyInfo_div").hide();
		$("#discharged_div").hide();
		$("#archivesListModal").hide();
	}else{
		$("#birthEndingTitle").show();
		$("#birthEnding_div").show();
		$("#dischargeIn_div").show();
		$("#birthBaseInfo_div").show();
		$("#birthBabyInfo_div").show();
		$("#discharged_div").show();
		
		var birthDiagnosis_hidden = $("#birthDiagnosis_hidden").val().split(",");
		for(var i=0; i<birthDiagnosis_hidden.length; i++){
			$("#"+birthDiagnosis_hidden[i]).remove();
		}
		var baseComplicationPrenatal_hidden = $("#baseComplicationPrenatal_hidden").val().split(",");
		for(var i=0; i<baseComplicationPrenatal_hidden.length; i++){
			$("#"+baseComplicationPrenatal_hidden[i]).remove();
		}
		var baseComplicationBirthing_hidden = $("#baseComplicationBirthing_hidden").val().split(",");
		for(var i=0; i<baseComplicationBirthing_hidden.length; i++){
			$("#"+baseComplicationBirthing_hidden[i]).remove();
		}
		var baseComplicationAfterBirthing_hidden = $("#baseComplicationAfterBirthing_hidden").val().split(",");
		for(var i=0; i<baseComplicationAfterBirthing_hidden.length; i++){
			$("#"+baseComplicationAfterBirthing_hidden[i]).remove();
		}
		var baseComplicationsMedical_hidden = $("#baseComplicationsMedical_hidden").val().split(",");
		for(var i=0; i<baseComplicationsMedical_hidden.length; i++){
			$("#"+baseComplicationsMedical_hidden[i]).remove();
		}
		var disMotherDisagnosis_hidden = $("#disMotherDisagnosis_hidden").val().split(",");
		for(var i=0; i<disMotherDisagnosis_hidden.length; i++){
			$("#"+disMotherDisagnosis_hidden[i]).remove();
		}
		
		$("div[id^='newBaby_tittle_']").remove();
		$("div[id^='birthBabyInfo_info_div_']").remove();
		$("div[id^='newBaby_tittle_']").remove();
		$("tr[id^='discharge_newbaby']").remove();
		
		common.clearForm("birthEndingUpdateForm");
		common.clearForm("birthDiagnosisForm");
		common.clearForm("birthBaseInfoForm");
		common.clearForm("birthInfoForm");
		common.clearForm("dischargedForm");
		
		$("#custId").val(custId);
		$("#diag_custId").val(custId);
		
		$("#liveAdd").attr("checked","checked");
	}
	if(flag == 'add' || (flag== 'show' && birthEndingNum == '0')){
		
		removeExtraElement();			//删除页面上的诊断
		//建档列表
		common.clearForm("archivesListForm");
		var url =URL.get("BirthEnding.BIRTHENDING_GET_UNLINK_PREGRECORD");
		var params = "custId="+custId;
		var archivesTbody ="";
		ajax.post(url, params, "json", function(data) {
			
			if(data.result && data.data.length != 0){
				$.each(data.data,function(index, archives){
					archivesTbody += archivesTr(index+1,archives);
				});
				$("#tittle").html('建档列表');
				$("#archives_tbody").html(archivesTbody);
				$("#archivesListModal").modal("show");
			}
		},false);
	}
	
}

/**
 * 选择建档信息
 * @param archivesId
 * @param lmp
 * @param pregNumber
 * @param birthNumber
 * @returns
 */
function setArchivesToBirth(archivesId,lmp,pregNumber,birthNumber,height,weight,encysituation,birthTiresType2){
	$("#archivesId").val(archivesId);
	$("#lmpId").val(lmp);
	$("#birthHeight").val(height);
	$("#birthWeight").val(weight);
	$("#birthBirthday").val(customerBirthday);
	$("#birthTiresType").val(encysituation);
	$("#birthTiresType2").val(birthTiresType2);
	if(pregNumber != ''){
		$("#birthPregNumber").val(pregNumber);
	}
	if(birthNumber != ''){
		$("#birthBornNumber").val(parseInt(birthNumber)+1);
	}
	if(encysituation == 2){
		$("#birthTiresType2").attr("disabled","disabled");  
	}
	$("#archivesListModal").modal("hide");
}

/**
 * 保存
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
			if(data){
				if(data.value.birthId != '' && data.value.birthId != undefined){	
					$("#birthId").val(data.value.birthId);
					$("#diag_birthId").val(data.value.birthId);
					$("#base_birthId").val(data.value.birthId);
					$("#baby_birthId").val(data.value.birthId);
					$("#dis_birthId").val(data.value.birthId);
				}
				if(data.value.baseId != '' && data.value.baseId != undefined){
					$("#baseId").val(data.value.baseId);
				}
				if(data.value.length != '' && data.value.length != undefined){		
					$("#birthId").val(data.value[0].birthId);
					$("#diag_birthId").val(data.value[0].birthId);
					$("#base_birthId").val(data.value[0].birthId);
					$("#baby_birthId").val(data.value[0].birthId);
					$("#babyId").val(data.value[0].babyId);
					$("#dis_babyId").val(data.value[0].babyId);
					var babyIds = $("input[id^=babyId]");
					$.each(babyIds, function(i, babyId){
						if(babyId.value == ''){
							babyId.value = data.value[i].babyId;
						}
					});
					for(var i=0; i<data.value.length; i++){
						if(data.value[i].babyIsDeath != '1' && $("#disBaby_hidden"+(i+1)).val() == ''){
							$("#disBaby_hidden"+(i+1)).val(data.value[i].babyId);
						}
					}
				}
				if(data.value.length !='' && data.value.length != undefined){
					if(data.value[0].disId != '' && data.value[0].disId != undefined){
						$("#disId").val(data.value[0].disId);
						var disArray = $("input[id^=disId_Id]");
						for(var i=0; i<disArray.length; i++){
							$("#"+disArray[i].id).val(data.value[i+1].disId);
						}
					}
				}
			}
		},
		error : function(XMLHttpRequest) {
			ajax._error(XMLHttpRequest, "")
			focusDivName = "none";
		}
	});
}

/**
 * 初始化基础信息
 * @returns
 */
function initBirthEndingInfo(){
	if(customerBirthday == ''){
		customerBirthday = '1990-01-01';
	}
	var date=new Date;
	var year=date.getFullYear();
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	//初始化日期
	common.initDate(null,null,null,"#birthBirthday",customerBirthday);
	common.initDate(null,null,null,"#birthHospitalDate",nowDate);
	
	//初始化分娩/产检医院
	common.autocompleteMethod("birthPregHospital", hospitalListData, function(data){
		$("#birthPregHospital").val(data.value.hospitalName);
	});
	//初始化分娩/产检医院
	common.autocompleteMethod("birthHospital", hospitalListData, function(data){
		$("#birthHospital").val(data.value.hospitalName);
	});
	//初始化分娩方位
	common.autocompleteMethod("baseBirthDirection", birthDirectionListData, function(data){
		$("#baseBirthDirection").val(data.value.codeValue);
	});
		
	$("#baseTime").off("change").on("change",function(){
		if($("#lmpId").val() != ''){
			var weeks = getCustLmp($("#baseTime").val(), $("#lmpId").val());
			if(weeks.indexOf("-") != -1){
				layer.msg("分娩时间有误，请重新选择");
				$("#baseTime").val("");
				$("#baseWeeks").val("");
				return;
			}else{
				$("#baseWeeks").val(weeks);
			}
		}
		if($("#birthBirthday").val() != ''){
			var age = $("#baseTime").val().substring(0,4) - $("#birthBirthday").val().substring(0,4);
			$("#birthAge").val(age < 0 ? "" : age);
			$("#birthAge_hidden").val(age < 0 ? "" : age);
		}
		
	});
	$("#birthTiresType").change(function(){
		var tiresType = $(this).children('option:selected').val();
		if(tiresType == '2'){
			$("#birthTiresType2").attr("disabled","disabled");  
			$("#birthTiresType2").val("");  
		}else{
			$("#birthTiresType2").removeAttr("disabled");  
		}
	});
	
}	
/**
 * 初始化分娩信息
 */
function initBirthEndingBaseInfo(){
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	//初始化日期
	common.initDate(null,null,null,"#baseTime",nowDate);
	$("#baseWeightBeforeBirth").change(function(){
		if($("#birthWeight").val() != '' && $("#baseWeightBeforeBirth").val() != ''){
			$("#baseGrowthPregnancying").val(($("#baseWeightBeforeBirth").val()*10 - $("#birthWeight").val()*10)/10);
		}
	});
	
	$("#basePerineumState").change(function(){
		var tiresType = $(this).children('option:selected').val();
		if(tiresType != '2'){
			$("#basePerineumHurt").attr("disabled","disabled");  
			$("#basePerineumHurt").val("");  
		}else{
			$("#basePerineumHurt").removeAttr("disabled");  
		}
	});
}
/**
 * 初始化新生儿信息
 */
function initBirthEndingBabyInfo(){
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	//初始化日期
	common.initDate(null,null,null,"#babyBirthTime",nowDate);
	common.initDate(null,null,null,"#babyDeathTime",nowDate);
}

$(document).ready(function() {
	if(birthEndingPojo == "[]"){
		birthEndingNum = "0";
		showBirthEndingDiv("show",birthEndingNum);	
	}else{
		showBirthEndingDiv("init");	
	}
	$("#custId").val(custId);
	$("#diag_custId").val(custId);
	initAllDiseaseSelect(); //初始化下拉
	initBirthEndingInfo();  //初始化基础信息
	initBirthEndingBaseInfo();
	initBirthEndingBabyInfo();
	$("body").on("click", function(){
		// 保存分娩结局主表信息
		var prescriptionInfoDivLength = $(":focus").parents("div[id='birthEndingInfo_div']").length;
		if(prescriptionInfoDivLength == 1){
			focusDivName = "birthEndingInfo_div";
			return ;
		} else{
			var postURL = URL.get("BirthEnding.BIRTHENDING_SAVE");
			if(focusDivName == "birthEndingInfo_div"){
				if($("#birthId").val() != ''){
					postURL = URL.get("BirthEnding.BIRTHENDING_UPDATE");
				}
				
				if($("#birthIsPregHospital").is(":checked")){//选中  
					$("#birthPregHospital_hidden").val(1);
				}  
				if($("#birthIsThisHospital").is(":checked")){//选中  
					$("#birthHospital_hidden").val(1);
				} 
				
				//计算bmi如果大于999则身高体重不合理
				var custHeight = $("#birthHeight").val();
				var custWeight = $("#birthWeight").val();
				var bmi = custWeight / Math.pow((custHeight/100),2);
				if(bmi > 100){
					layer.alert("身高或体重不合理，请调整");
					$("#birthHeight").val("");
					$("#birthWeight").val("");
					return;
				}
		
				if($("#birthPregNumber").val() < $("#birthBornNumber").val() ){
					layer.alert("孕次不能小于产次");
					$("#birthBornNumber").val("");
					return;
				}
				var sendInfoParams = $("#birthEndingUpdateForm").serialize();
				sendInfoParams += "&"+ $("#birthDiagnosisForm").serialize();
				saveBirthEnding(postURL, sendInfoParams);
			}
		}
		// 保存分娩结局入院诊断信息
		var prescriptionInfoDivLength = $(":focus").parents("div[id='birthDiagnosis_info_div']").length;
		if(prescriptionInfoDivLength == 1){
			focusDivName = "birthDiagnosis_info_div";
			return ;
		} else{
			var postURL = URL.get("BirthEnding.BIRTHENDING_SAVE");
			if(focusDivName == "birthDiagnosis_info_div"){
				if($("#diag_birthId").val() != ''){
					postURL = URL.get("BirthEnding.BIRTHENDING_UPDATE")
				}
				var sendInfoParams = $("#birthEndingUpdateForm").serialize();
				sendInfoParams += "&"+ $("#birthDiagnosisForm").serialize();
				saveBirthEnding(postURL, sendInfoParams);
				
			}
		}
		
		// 保存分娩结局_分娩信息
		var prescriptionInfoDivLength = $(":focus").parents("div[id='birthBaseInfo_info_div']").length;
		if(prescriptionInfoDivLength == 1){
			focusDivName = "birthBaseInfo_info_div";
			return ;
		} else{
			if(focusDivName == "birthBaseInfo_info_div"){
				if($("#base_birthId").val() == ''){
					layer.alert("请先保存基础信息");
					focusDivName = "none";
					return;
				}
				var postURL = URL.get("BirthEnding.BIRTHENDING_BASEINFO_SAVE");
				
				if($("#baseId").val() != ''){
					$("#birthBaseInfoForm").attr("action");
					postURL = URL.get("BirthEnding.BIRTHENDING_BASEINFO_UPDATE");
				}
				
				//第一产程
				var baseChildBirthFist ="";
				if($("#baseChildBirthFist_hour").val() != '') baseChildBirthFist += $("#baseChildBirthFist_hour").val() + "时";
				if($("#baseChildBirthFist_minutes").val() != '') baseChildBirthFist += $("#baseChildBirthFist_minutes").val()+ "分";
				$("#baseChildBirthFist").val(baseChildBirthFist);
				
				//第二产程
				var baseChildBirthSecond ="";
				if($("#baseChildBirthSecond_hour").val() != '') baseChildBirthSecond += $("#baseChildBirthSecond_hour").val() + "时";
				if($("#baseChildBirthSecond_minutes").val() != '') baseChildBirthSecond += $("#baseChildBirthSecond_minutes").val()+ "分";
				$("#baseChildBirthSecond").val(baseChildBirthSecond);
				
				//第三产程
				var baseChildBirthThrid ="";
				if($("#baseChildBirthThrid_hour").val() != '') baseChildBirthThrid += $("#baseChildBirthThrid_hour").val() + "时";
				if($("#baseChildBirthThrid_minutes").val() != '') baseChildBirthThrid += $("#baseChildBirthThrid_minutes").val()+ "分";
				$("#baseChildBirthThrid").val(baseChildBirthThrid);
				
				//总产程
				var baseChildBirthSum ="";
				if($("#baseChildBirthSum_hour").val() != '') baseChildBirthSum+= $("#baseChildBirthSum_hour").val() + "时";
				if($("#baseChildBirthSum_minutes").val() != '') baseChildBirthSum+= $("#baseChildBirthThrid_minutes").val()+ "分";
				$("#baseChildBirthSum").val(baseChildBirthSum);
				
				//开奶时间
				var baseAfterBirthingBreastMilkl ="";
				if($("#baseAfterBirthingBreastMilkl_hour").val() != '') baseAfterBirthingBreastMilkl += $("#baseAfterBirthingBreastMilkl_hour").val() + "时";
				if($("#baseAfterBirthingBreastMilkl_minutes").val() != '') baseAfterBirthingBreastMilkl += $("#baseAfterBirthingBreastMilkl_minutes").val()+ "分";
				$("#baseAfterBirthingBreastMilkl").val(baseAfterBirthingBreastMilkl);
				
				var baseSurgeryType_check = $("input:checkbox[name=baseSurgeryType]:checked");
				var baseSurgeryType_value="";
				for(var i=0; i<baseSurgeryType_check.length; i++){
					baseSurgeryType_value += ","+baseSurgeryType_check[i].value;
				}
				baseSurgeryType_value = baseSurgeryType_value.substring(1);
				$("#baseSurgeryType").val(baseSurgeryType_value);
				
				if($("#baseSurgeryDetail2").val() == ''){  
					$("#baseSurgeryType_check_5").removeAttr("checked");  
				}  
				
				var sendInfoParams = $("#birthBaseInfoForm").serialize();
				saveBirthEnding(postURL, sendInfoParams);
			}
		}
		
		// 保存分娩结局_新生儿信息
		var prescriptionInfoDivLength = $(":focus").parents("div[id^='birthBabyInfo_info_div']").length;
		if(prescriptionInfoDivLength == 1){
			focusDivName = "birthBabyInfo_info_div";
			return ;
		} else{
			var postURL = URL.get("BirthEnding.BIRTHENDING_BABYINFO_UPDATE");
			if(focusDivName == "birthBabyInfo_info_div"){
				if($("#base_birthId").val() == ''){
					layer.alert("请先保存基础信息");
					focusDivName = "none";
					return;
				}
				var sendInfoParams = "babyList=" + getBabysInfoByName().replace(/"babyDeathTime":"",/g, '').replace(/"babyBirthTime":"",/g, '');
				saveBirthEnding(postURL, sendInfoParams);
			}
		}
		
		// 保存分娩结局出院诊断信息
		var prescriptionInfoDivLength = $(":focus").parents("div[id='birthDischarged_info_div']").length;
		if(prescriptionInfoDivLength == 1){
			focusDivName = "birthDischarged_info_div";
			return ;
		} else{
			var postURL = URL.get("BirthEnding.BIRTHENDING_DISCHARGE_SAVE");
			if(focusDivName == "birthDischarged_info_div"){
				if($("#dis_birthId").val() == ''){
					layer.alert("请先保存基础信息");
					focusDivName = "none";
					return;
				}
				if($("#disId").val() != ''){
					postURL = URL.get("BirthEnding.BIRTHENDING_DISCHARGE_UPDATE");
				}
				var sendInfoParams = "dischargeList=" + getDischargesInfoByName();
				saveBirthEnding(postURL, sendInfoParams);
			}
		}
	});
	
	$("#baseBirthType2").attr("disabled","disabled");
	$("#baseBirthType").change(function(){
		var tiresType = $(this).children('option:selected').val();
		
		if(tiresType == '1'){
			$("#basePgcIndication").val("无");	
			$("#baseBirthType2").attr("disabled","disabled");  
			$("#baseBirthType2").val("");  
		}else if(tiresType == '2' || tiresType == '3' || tiresType == '4' || tiresType == '5'){
			$("#baseBirthType2").attr("disabled","disabled");  
			$("#baseBirthType2").val("");  
			$("#basePgcIndication").val("");	
		}else{
			$("#basePgcIndication").val("");	
			$("#baseBirthType2").removeAttr("disabled");  
		}
	});
	$("#birthIsPregHospital").change(function() { 
		if($("#birthIsPregHospital").prop('checked')){
			$("#birthPregHospital").attr("disabled","disabled");
			$("#birthPregHospital").val("");
		}else{
			$("#birthPregHospital").removeAttr("disabled");
		}
	}); 
	$("#birthIsThisHospital").change(function() { 
		if($("#birthIsThisHospital").prop('checked')){
			$("#birthHospital").attr("disabled","disabled");
			$("#birthHospital").val("");
		}else{
			$("#birthHospital").removeAttr("disabled");
		}
	}); 
	$("#birthBirthday").change(function() { 
		if($("#baseTime").val() != ''){
			var age = $("#baseTime").val().substring(0,4) - $("#birthBirthday").val().substring(0,4);
			$("#birthAge").val(age < 0 ? "" : age);
			$("#birthAge_hidden").val(age < 0 ? "" : age);
		}
	}); 
	
	if(!$("#baseSurgeryType_check_1").is(":checked")){//没选中  
		$("#baseSurgeryDetail").attr("disabled","disabled");
	}
	$("#baseSurgeryType_check_1").change(function() { 
		if($("#baseSurgeryType_check_1").is(":checked")){//选中  
			$("#baseSurgeryDetail").removeAttr("disabled");  
		}else{
			$("#baseSurgeryDetail").attr("disabled","disabled");
		}  
	}); 
	
	if(!$("#baseSurgeryType_check_5").is(":checked")){//选中  
		$("#baseSurgeryDetail2").attr("disabled","disabled");
	}
	$("#baseSurgeryType_check_5").change(function() { 
		if($("#baseSurgeryType_check_5").is(":checked")){//选中  
			$("#baseSurgeryDetail2").removeAttr("disabled");  
		}else{
			$("#baseSurgeryDetail2").attr("disabled","disabled");
		}  
	}); 
	
});