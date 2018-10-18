//焦点div
var focusDivName = "none";
var archivesTable;// table，注意命名不要重复

var birthEndingNum = "";//分娩信息数
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
 * 编辑
 * @param birthId
 * @returns
 */
function editBirthEnding(birthId,target){

	var url = URL.get("BirthEnding.BIRTHENDING_GET_DETAIL_BYBIRTHID");
    var params = "birthId=" + birthId;

    if (birthId != $("#birthId").val() && target == "_parent") {
        ajax.post(url, params, "json", function(data) {
            //显示页面信息
            $("#birthending").show();

            $("#birthId").val(birthId);
            var birthEnding = data.value.birthEndingPojo;
            var birthBasePojo = data.value.birthBasePojo;
            // 初始化分娩信息
            editBirthBaseShow(birthBasePojo);
            // 初始化 新生儿+出院诊断
            loadBabyPage();

            if(birthEnding != undefined){
                //先清除诊断内容
                common.clearForm("birthEndingUpdateForm");
                common.clearForm("birthDiagnosisForm");
                //填入数据
                $("#birthEndingUpdateForm").jsonToForm(birthEnding);
                $("#birthDiagnosisForm").jsonToForm(birthEnding);

                if(birthEnding.birthIsPregHospital == 1){
                    $("#birthIsPregHospital").attr("checked","true");
                    $("#birthIsPregHospital").removeAttr("disabled");
                    $("#birthPregHospital").val("");
                    $("#birthPregHospital").attr("disabled",true);
                } else {
                    $("#birthPregHospital").removeAttr("disabled");
                    if ($("#birthPregHospital").val().length != 0){
                        $("#birthIsPregHospital").attr("disabled","true");
                    } else {
                        $("#birthIsPregHospital").removeAttr("disabled");
                    }
                }
                if(birthEnding.birthIsThisHospital == 1){
                    $("#birthIsThisHospital").attr("checked","true");
                    $("#birthIsThisHospital").removeAttr("disabled");
                    $("#birthHospital").val("");
                    $("#birthHospital").attr("disabled",true);
                } else {
                    $("#birthHospital").removeAttr("disabled");
                    if ($("#birthHospital").val().length != 0) {
                        $("#birthIsThisHospital").attr("disabled","true");
                    } else {
                        $("#birthIsThisHospital").removeAttr("disabled");
                    }
                }
                //如果是辅助生殖，受孕方式2不可选择
                if (birthEnding.birthTiresType == 1){
                    $("#birthTiresType2").removeAttrs("disabled");
                } else {
                    $("#birthTiresType2").attr("disabled",true);
                }
                //显示入院诊断信息
                var babyComArray = getSelectArray(birthEnding.birthDiagnosis,productAllMap);
                selectOperation.init("diagnosis_div","birthDiagnosis_hidden",babyComArray);
                $("#birthDiagnosis_hidden").val(birthEnding.birthDiagnosis);
                $("#birthDiagnosis").val("");
            }
        });
    }
    if (birthId == $("#birthId").val() && target == "_parent"){
        ajax.post(url, params, "json", function(data) {
            //显示页面信息
            $("#birthending").show();

            $("#birthId").val(birthId);
            var birthEnding = data.value.birthEndingPojo;
            var birthBasePojo = data.value.birthBasePojo;
            // 初始化分娩信息
            editBirthBaseShow(birthBasePojo);
            // 初始化 新生儿+出院诊断
            loadBabyPage();

            if(birthEnding != undefined){
                //先清除诊断内容
                common.clearForm("birthEndingUpdateForm");
                common.clearForm("birthDiagnosisForm");
                //填入数据
                $("#birthEndingUpdateForm").jsonToForm(birthEnding);
                $("#birthDiagnosisForm").jsonToForm(birthEnding);

                if(birthEnding.birthIsPregHospital == 1){
                    $("#birthIsPregHospital").attr("checked","true");
                    $("#birthIsPregHospital").removeAttr("disabled");
                    $("#birthPregHospital").val("");
                    $("#birthPregHospital").attr("disabled",true);
                } else {
                    $("#birthPregHospital").removeAttr("disabled");
                    if ($("#birthPregHospital").val().length != 0){
                        $("#birthIsPregHospital").attr("disabled","true");
                    }
                }
                if(birthEnding.birthIsThisHospital == 1){
                    $("#birthIsThisHospital").attr("checked","true");
                    $("#birthIsThisHospital").removeAttr("disabled");
                    $("#birthHospital").val("");
                    $("#birthHospital").attr("disabled",true);
                } else {
                    $("#birthHospital").removeAttr("disabled");
                    if ($("#birthHospital").val().length != 0) {
                        $("#birthIsThisHospital").attr("disabled","true");
                    }
                }
                //如果是辅助生殖，受孕方式2不可选择
                if (birthEnding.birthTiresType == 1){
                    $("#birthTiresType2").removeAttrs("disabled");
                } else {
                    $("#birthTiresType2").attr("disabled",true);
                }
                //显示入院诊断信息
                var babyComArray = getSelectArray(birthEnding.birthDiagnosis,productAllMap);
                selectOperation.init("diagnosis_div","birthDiagnosis_hidden",babyComArray);
                $("#birthDiagnosis_hidden").val(birthEnding.birthDiagnosis);
                $("#birthDiagnosis").val("");
            }
        });
    }
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
		var url = URL.get("BirthEnding.BIRTHENDING_DELETE");
		var params = "birthId="+birthId;
		setTimeout(function() {
			ajax.post(url, params, "json", function(data) {
				$(nowTr).parent().parent().remove();
				//重新排序
                var lineNum = $("#birrhInfoTable_tbody").children("tr").length;
                if (lineNum > 1){
                    for (var i = 0; i < lineNum - 1 ; i++){
                        $("#birrhInfoTable_tbody").find("tr").eq(i).find("td:first").html(i+1);
                    }
                }
                //删除当前编辑的数据，隐藏dom
                if ($("#birthId").val() == birthId){
                    //把dom隐藏起来
                    $("#birthending").hide();
                }
				if(lineNum == 1){
					var noRowsTr = "<tr><td colspan=\"8\" style=\"text-align: center\">无分娩记录</td></tr>";
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
 * 添加分娩结局信息
 */
function addBirthEnding() {
	//获取表单中三个id
	var oldCustId = $("#custId").val();
	var oldArchivesId = $("#archivesId").val();

	//根据分娩结局显示状况，提示是否新建分娩结局信息
	if ($("#birthending").is(':visible')) {
        layer.confirm("是否添加新分娩结局信息？", function (index) {
            focusDivName = "clearFocusDivName";
            layer.close(index);
            //清空表单数据
            common.clearForm("birthEndingUpdateForm");
            common.clearForm("birthDiagnosisForm");
            //先清除诊断内容
            $("#diagnosis_div :gt(1)").remove();
            //赋值
            $("#custId").val(oldCustId);
            $("#archivesId").val(oldArchivesId);

            //发送请求创建基础信息
            var addBirthEndingURL = URL.get("BirthEnding.BIRTHENDING_SAVE");
            var addBirthEndingParams = "custId=" + $("#custId").val();
            ajax.post(addBirthEndingURL, addBirthEndingParams, "json", function(data) {
                if(data.result == true){
                    $("#birthId").val(data.value.birthId);
                }
            },false);
            // 清空新生儿数据
            loadBabyPage();
            // 清空分娩信息数据
            common.clearForm("birthBaseInfoForm");
            // 清空分娩信息诊断框
            emptyBirthBaseDiease();
            //获取建档信息
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
                    $("#birthId").val(data.value.birthId);
                }
            },false);
            //把隐藏的dom显示出来
            $("#birthending").show();
            checkBirthTiresType()
        });
    } else {
        //发送请求创建基础信息
        var addBirthEndingURL = URL.get("BirthEnding.BIRTHENDING_SAVE");
        var addBirthEndingParams = "custId=" + $("#custId").val();
        ajax.post(addBirthEndingURL, addBirthEndingParams, "json", function(data) {
            if(data.result == true){
                $("#birthId").val(data.value.birthId);
                
                // 清空新生儿数据
                loadBabyPage();
            }
        },false);
        //清空表单数据
        common.clearForm("birthEndingUpdateForm");
        common.clearForm("birthDiagnosisForm");
        //赋值
        $("#custId").val(oldCustId);
        $("#archivesId").val(oldArchivesId);
        // 清空分娩信息数据
        common.clearForm("birthBaseInfoForm");

        //获取建档信息
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
        //把隐藏的dom显示出来
        $("#birthending").show();
        checkBirthTiresType()
    }
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
	if (encysituation == 0) {
        $("#birthTiresType").removeAttr("disabled");
        $("#birthTiresType2").attr("disabled","disabled");
    }
    if (encysituation == 1) {
        $("#birthTiresType").removeAttr("disabled");
        $("#birthTiresType2").removeAttr("disabled");
    }
    if (encysituation == 2) {
        $("#birthTiresType").removeAttr("disabled");
        $("#birthTiresType2").attr("disabled","disabled");
    }
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
    //触发保存
    var postURL = URL.get("BirthEnding.BIRTHENDING_UPDATE");
    if($("#birthIsPregHospital").is(":checked")){//选中
        $("#birthPregHospital_hidden").val(1);
        $("#birthPregHospital").attr("disabled",true);
    } else {
        $("#birthPregHospital").removeAttr("disabled");
    }
    if($("#birthIsThisHospital").is(":checked")){//选中
        $("#birthHospital_hidden").val(1);
        $("#birthHospital").attr("disabled",true);
    } else {
        $("#birthHospital").removeAttr("disabled");
    }

    //计算bmi如果大于999则身高体重不合理
    var custHeight = $("#birthHeight").val();
    var custWeight = $("#birthWeight").val();
    if(!common.isEmpty(custHeight) && !common.isEmpty(custWeight)){
    	var bmi = custWeight / Math.pow((custHeight/100),2);
	    if(bmi > 100){
	        layer.alert("身高或体重不合理，请调整");
	        $("#birthHeight").val("");
	        $("#birthWeight").val("");
	        return;
	    }
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
					for(var i=0; i<data.value.length; i++){
						if($("#babyId"+(i+1)).val() == ''){
							$("#babyId"+(i+1)).val(data.value[i].babyId);
						}
						if(data.value[i].babyIsDeath != '1'){
							$("#disBaby_hidden"+(i+1)).val(data.value[i].babyId);
						}
						console.log(i+":"+$("#babyId"+(i+1)).val()+"_123" + i);
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
    $("#birthBirthday").change(function() {
        if($("#baseTime").val() != ''){
            var age = $("#baseTime").val().substring(0,4) - $("#birthBirthday").val().substring(0,4);
            $("#birthAge").val(age < 0 ? "" : age);
            $("#birthAge_hidden").val(age < 0 ? "" : age);
        }
    });
    $("#birthTiresType").change(function(){
		var tiresType = $(this).children('option:selected').val();
		if(tiresType == '2' || tiresType == ''){
			$("#birthTiresType2").attr("disabled","disabled");  
			$("#birthTiresType2").val("");  
		}else{
			$("#birthTiresType2").removeAttr("disabled");  
		}
	});
	
}

/**
 * 本院选中，输入框失效
 */
function checkHospital(){
    if ($("#birthIsPregHospital").is(":checked")){
        $("#birthPregHospital").attr("disabled",true);
    } else {
        $("#birthPregHospital").removeAttr("disabled");
    }
    if ($("#birthIsThisHospital").is(":checked")){
        $("#birthHospital").attr("disabled",true);
    } else {
        $("#birthHospital").removeAttr("disabled");
    }
    if ($("#birthPregHospital").val().length == 0) {
        $("#birthIsPregHospital").removeAttr("disabled");
    } else {
        $("#birthIsPregHospital").attr("disabled",true);
    }
    if ($("#birthHospital").val().length == 0) {
        $("#birthIsThisHospital").removeAttr("disabled");
    } else {
        $("#birthIsThisHospital").attr("disabled",true);
    }
}

/**
 * 受孕方式
 */
function checkBirthTiresType(){
    if ($("#birthTiresType").val() == 1) {
        $("#birthTiresType2").removeAttr("disabled");
    }
    if ($("#birthTiresType").val() == 2 || $("#birthTiresType").val() == null || $("#birthTiresType").val() == ""){
        $("#birthTiresType2").attr("disabled",true);
    }
}

$(document).ready(function() {

    //如果无分娩结局信息，显示表单
    if(birthEndingPojo == "[]"){
        $("#birthending").show();
    }
    //初始化时间 日期选择插件
    common.initDate(null,null,null,"#birthBirthday");
    common.initDate(null,null,null,"#birthHospitalDate");

    //初始化入院诊断
    common.autocompleteMethod("birthDiagnosis", productListData, function(data){
        $("#birthDiagnosis").val("");
        var birthDiagnosis_value = $("#birthDiagnosis_hidden").val();
        var birthDiagnosisLength =  birthDiagnosis_value.split(",").length;
        if(birthDiagnosisLength > 5 ){
            layer.msg("最多选择6种！");
            $("#birthDiagnosis").val("");
            return;
        }
        selectOperation.addSelectOperation(data.value.diseaseId,data.name,"diagnosis_div","birthDiagnosis_hidden");
    });

    // 计算孕期体重增长： 孕期体重增长 = 分娩前体重*10 - 孕前体重*10
    $("#birthWeight").change(function(){
        if( !common.isEmpty($("#birthWeight").val()) && !common.isEmpty($("#baseWeightBeforeBirth").val())){
            const result = (parseFloat($("#baseWeightBeforeBirth").val())*10 - parseFloat($("#birthWeight").val())*10)/10;
            $("#baseGrowthPregnancying").val(result);
        };
    });

    $("#birthTiresType").change(function () {
        if ($("#birthTiresType").val() == "辅助生殖") {
            $("#birthTiresType2").disable(true);
        }
    })

    $("#custId").val(custId);
    $("#diag_custId").val(custId);
    initBirthEndingInfo();  //初始化基础信息

    // 删除诊断后，自动保存
    $('input[id$="_hidden"]').unbind("change").one("change", function(){
		var id = $(this).attr("id").substr(0, $(this).attr("id").indexOf("_hidden"));
		$("#"+id).focus();
	});
    $("body").on("click", function(e){
        //保存分娩结局主表信息
        var prescriptionInfoDivLength = $(":focus").parents("div[id='birthEndingInfo_div']").length;
        if(prescriptionInfoDivLength == 1){
            focusDivName = "birthEndingInfo_div";
            return ;
        } else{
            var postURL = URL.get("BirthEnding.BIRTHENDING_UPDATE");
            if(focusDivName == "birthEndingInfo_div"){

                if ($("#birthIsPregHospital").is(":checked") == true){//选中
                    $("#birthPregHospital_hidden").val(1);
                }
                if ($("#birthIsPregHospital").is(":checked") == false && $("#birthPregHospital").val() != "") {
                    $("#birthPregHospital_hidden").val(2);
                }
                if ($("#birthIsPregHospital").is(":checked") == false && $("#birthPregHospital").val() == "") {
                    $("#birthPregHospital_hidden").val("");
                }
                if ($("#birthIsThisHospital").is(":checked") == true){//选中
                    $("#birthHospital_hidden").val(1);
                }
                if ($("#birthIsThisHospital").is(":checked") == false && $("#birthHospital").val() != "") {
                    $("#birthHospital_hidden").val(2);
                }
                if ($("#birthIsThisHospital").is(":checked") == false && $("#birthHospital").val() == "") {
                    $("#birthHospital_hidden").val("");
                }

                //计算bmi如果大于999则身高体重不合理
                var custHeight = $("#birthHeight").val();
                var custWeight = $("#birthWeight").val();
                if(!common.isEmpty(custHeight) && !common.isEmpty(custWeight)){
                	var bmi = custWeight / Math.pow((custHeight/100),2);
                    if(bmi > 100){
                        layer.alert("身高或体重不合理，请调整");
                        $("#birthHeight").val("");
                        $("#birthWeight").val("");
                        return;
                    }
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
        //保存入院诊断信息
		prescriptionInfoDivLength = $(":focus").parents("div[id='birthDiagnosis_info_div']").length;
        if(prescriptionInfoDivLength == 1){
            focusDivName = "birthDiagnosis_info_div";
            return ;
        } else{
            var postURL = URL.get("BirthEnding.BIRTHENDING_UPDATE");
            if(focusDivName == "birthDiagnosis_info_div"){

                if ($("#birthIsPregHospital").is(":checked") == true){//选中
                    $("#birthPregHospital_hidden").val(1);
                }
                if ($("#birthIsPregHospital").is(":checked") == false && $("#birthPregHospital").val() != "") {
                    $("#birthPregHospital_hidden").val(2);
                }
                if ($("#birthIsPregHospital").is(":checked") == false && $("#birthPregHospital").val() == "") {
                    $("#birthPregHospital_hidden").val("");
                }
                if ($("#birthIsThisHospital").is(":checked") == true){//选中
                    $("#birthHospital_hidden").val(1);
                }
                if ($("#birthIsThisHospital").is(":checked") == false && $("#birthHospital").val() != "") {
                    $("#birthHospital_hidden").val(2);
                }
                if ($("#birthIsThisHospital").is(":checked") == false && $("#birthHospital").val() == "") {
                    $("#birthHospital_hidden").val("");
                }

                //计算bmi如果大于999则身高体重不合理
                var custHeight = $("#birthHeight").val();
                var custWeight = $("#birthWeight").val();
                if(!common.isEmpty(custHeight) && !common.isEmpty(custWeight)){
                	var bmi = custWeight / Math.pow((custHeight/100),2);
                	if(bmi > 100){
                		layer.alert("身高或体重不合理，请调整");
                		$("#birthHeight").val("");
                		$("#birthWeight").val("");
                		return;
                	}
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

    	// 住院日期触发事件
    	$("#birthHospitalDate").off("change").on("change",function(){
            const fenmianDate = $("#baseTime").val();
            if(!common.isEmpty(fenmianDate)) {
                compareDate(fenmianDate.split(" ")[0],this.value,"1");
            }
        });
        //如果点编辑按钮，显示编辑信息
        editBirthEnding(e.target.id,e.target.target);
    });
});
