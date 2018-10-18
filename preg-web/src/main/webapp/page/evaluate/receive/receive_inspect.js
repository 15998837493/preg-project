
var elementAllList;

$(document).ready(function(){
	//膳食回顾的方法
    $("#dietReviewForm").validate(dietReviewOption);
    common.requiredHint("dietReviewForm", dietReviewOption);
	//初始化营养评价项目
	refreshDiagnosisInspects();
	//获取评价项目结果
	getDiagnosisInspectResult();
	// 营养素列表
	ajax.post(URL.get("Master.QUERY_NUTRIENT"), null, dataType.json, function(data){
		elementAllList = data.value;
	}, false, false);
});

/**
 * 局部刷新营养评价项目区
 * @returns
 */
function refreshDiagnosisInspectInfo(){
	ajax.post(URL.get("Platform.DIAGNOSIS_INSPECT_QUERY"), "diagnosisId="+$("#diagnosisId").val(), dataType.json, function(data){
		diagnosisItemList = data.value;
		diagnosisInspectMap.clear();
		if(!_.isEmpty(diagnosisItemList)){
			$.each(diagnosisItemList, function(index, value){
				diagnosisInspectMap.set(value.inspectCode, value);
			});
			//初始化营养评价项目
			refreshDiagnosisInspects();
			//获取评价项目结果
			getDiagnosisInspectResult();
		}
	}, false, false);
}

/**
 * 生成评价项目按钮组的方法
 */
function refreshDiagnosisInspects(){
	if(diagnosisInspectMap != null && diagnosisInspectMap.size > 0){
		$("#diagnosis_item_btns").empty();// 操作区
		$("#diagnosis_item_qits").html("");// 提示区
		for (var [key, value] of diagnosisInspectMap.entries()) {
			$("#diagnosis_item_btns").append(addInspectButton(value));
			if(!_.isEmpty(value.inspectPrompt)){
				$("#diagnosis_item_qits").append(
					"<div id='prompt_"+value.inspectCode+"'><font color='red'>"+value.inspectName+"</font>："+value.inspectPrompt+"</div>"
				);
			}
		}
		if($("#diagnosis_item_qits").children().length > 0){
			$("#diagnosis_item_qits").show();
		} else{
			$("#diagnosis_item_qits").hide();
		}
	}
	// 默认提示信息
	if($("#diagnosis_item_btns").children().length == 0){
		$("#diagnosis_item_btns").html("<div id='noButtonDiv'><font color='gray'>请先选择评价项目！</font></div>");
	}
}

/**
 * 获取评估项目操作按钮
 * @param inspect
 * @returns {String}
 */
function addInspectButton(inspect) {
	var id = inspect.id;
	var inspectCode = inspect.inspectCode;
	var inspectStatus = inspect.inspectStatus;
	var inspectName = inspect.inspectName;
	var resultCode = inspect.resultCode;
	
	var inspectButtonHtml = "";
	// 膳食回顾需要特殊处理
	if(inspectCode == "A00001" && inspectStatus != "1"){
		if(inspectStatus == "2"){
			$("#dietReviewModal #dietReviewText").attr("readonly", false);
			$("#dietReviewModal #dietReviewButtonBody").show();
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='color: red;' onclick='getDietReview(\""+id+"\",\""+inspectCode+"\");'>"+inspectName+"</button>" +
				"</div>";
		} else if(inspectStatus == "3"){
			$("#dietReviewModal #dietReviewText").attr("readonly", true);
			$("#dietReviewModal #dietReviewButtonBody").hide();
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' onclick='getDietReview(\""+id+"\",\""+inspectCode+"\");'>"+inspectName+"</button>" +
				"</div>";
		} else if(inspectStatus == "4"){
			$("#dietReviewModal #dietReviewText").attr("readonly", false);
			$("#dietReviewModal #dietReviewButtonBody").show();
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue' style='color: red;' onclick='getDietReview(\""+id+"\",\""+inspectCode+"\");'>"+inspectName+"</button>" +
				"</div>";
		}
	} else if (inspectStatus == "2") {// 助理未评估
		inspectButtonHtml = 
			"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>" +
			"	<button type='button' class='btn btn-lightblue' style='color: red;' onclick='getConfigHtml(\""+id+"\", \""+inspectCode+"\", \""+inspectStatus+"\");'>" + inspectName + "</button>"+
			"</div>";
	} else if (inspectStatus == "3") {// 评估完成
		// 安全剂量评估
		if(inspectCode == "B00004"){
			var width = inspectName.length;
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue btn-width' onclick='getExtenderHistoryReport(\"" + resultCode + "\",\"" + inspectCode + "\",\"" + id + "\");'>"+ 
				"		<p class='hovershow' style='width:" + width + "em;'>查看剂量评估结果</p>" + //鼠标悬浮时显示
				"		<p class='hoverhide' style='width:" + width + "em;'>" + inspectName + "</p>" + //正常状态显示
				"	</button>" +
				"</div>";
		} else{
			// 已经完成的评价项目
			var width = inspectName.length;
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue btn-width' onclick='getConfigHtml(\"" + id + "\",\"" + inspectCode + "\",\"" + inspectStatus + "\");'>"+ 
				"		<p class='hovershow' style='width:" + width + "em;'>查看报告</p>" + //鼠标悬浮时显示
				"		<p class='hoverhide' style='width:" + width + "em;'>" + inspectName + "</p>" + //正常状态显示
				"	</button>" +
				"</div>";
		}
	} else if (inspectStatus == "4") {// 助理评估完成
		// 安全剂量评估
		if(inspectCode == "B00004"){
			// 已经完成的评价项目
			var width = inspectName.length;
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='color: red;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue' style='color: red;' onclick='getConfigHtml(\""+id+"\", \""+inspectCode+"\", \"1\");'>"+ 
				"		<p>" + inspectName + "</p>" + //正常状态显示
				"	</button>" +
				"	<button title='重新评估' type='button' class='btn btn-lightblue' style='color: red;' onclick='resetDiagnosisItem(\"" + id + "\",\"" + inspectCode + "\");'><i class='fa fa-refresh fa-fw'></i></button>"+
				"</div>";
		} else{
			// 已经完成的评价项目
			var width = inspectName.length;
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='color: red;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue btn-width' style='color: red;' onclick='getConfigHtml(\"" + id + "\",\"" + inspectCode + "\",\"" + inspectStatus + "\");'>"+ 
				"		<p class='hovershow' style='width:" + width + "em;'>查看报告</p>" + //鼠标悬浮时显示
				"		<p class='hoverhide' style='width:" + width + "em;'>" + inspectName + "</p>" + //正常状态显示
				"	</button>" +
				"	<button title='重新评估' type='button' class='btn btn-lightblue' style='color: red;' onclick='resetDiagnosisItem(\"" + id + "\",\"" + inspectCode + "\");'><i class='fa fa-refresh fa-fw'></i></button>"+
				"</div>";
		}
	}
	if(!_.isEmpty(inspectButtonHtml)){
		$("#noButtonDiv").remove();
	}
	
	return inspectButtonHtml;
}

/**
 * 医生发送评价项目到医生端
 * @returns
 */
function sendInspect(){
	var $inspectChecked = $("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][checked]");
	if($inspectChecked.length > 0){
		var inspectCodeArray = [];
		$inspectChecked.each(function(index, button){
			inspectCodeArray.push(button.value);
		});
		layer.confirm("确认【发送】营养评价项目到医生端？", function(index){
			// 发送评价项目
			sendAjax(inspectCodeArray, "3");
			layer.close(index);
		});
	} else{
		layer.alert("请先选中要发送的营养评价项目！");
	}
}

/**
 * 发送评价项目ajax方法
 * @param inspectCodeListStr
 * @param inspectPromptListStr
 * @param inspectStatus
 * @returns
 */
function sendAjax(inspectCodeArray, inspectStatus){
	if(!_.isEmpty(inspectCodeArray)){
		var inspectArray = [];
		$(inspectCodeArray).each(function(index, value){
			var inspect = {};
			inspect.id = diagnosisInspectMap.get(value).id;
			inspect.inspectName = diagnosisInspectMap.get(value).inspectName;
			inspect.inspectStatus = inspectStatus;
			inspect.inspectPrompt = "";
			inspect.diagnosisId = $("#diagnosisId").val();
			inspectArray.push(inspect);
		});
		
		var url = URL.get("Platform.DIAGNOSIS_INSPECT_SEND");
		var params = "info="+JSON.stringify(inspectArray)+"&infoType=inspect&diagnosisId="+$("#diagnosisId").val();
		ajax.post(url, params, dataType.json, function(data){
			$("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][checked]").each(function(index, button){
				diagnosisInspectMap.get(button.value).inspectStatus = "3";
				diagnosisInspectMap.get(button.value).inspectPrompt = "";
			});
			refreshDiagnosisInspects();
			getDiagnosisInspectResult();// 获取评价项目结果
			opener.refreshDataTable();
		}, false, false);
	}
}

/**
 * 评估营养评价项目
 * @param inspectId
 * @param inspectCode
 * @param inspectStatus
 */
function getConfigHtml(id, inspectCode, inspectStatus) {
	if (inspectStatus == "3" || inspectStatus == "4") {
		var backUrl = URL.get("ExamItemURL." + inspectCode + "_callback_url") + "?id=" + id;
		ajax.post(backUrl, "", dataType.json, function(data) {
			common.openWindow(PublicConstant.basePath + "/" + data.value);
		}, false, false);
	} else {
		// 24小时膳食回顾和膳食频率调查 需要体重
		if (inspectCode == "B00005" || inspectCode == "B00008") {
			if(_.isEmpty(diagnosisJson.diagnosisCustWeight)){
				layer.msg("该患者无现体重数据！");
				return;
			}
		}
		var url = URL.get("ExamItemURL." + inspectCode + "_operate_url");
		if (url.indexOf("?") >= 0) {
			url += "&";
		} else {
			url += "?";
		}
		common.openWindow(url + "custId=" + $("#custId").val() + "&id=" + id + "&diagnosisId=" + $("#diagnosisId").val());
	}
}

/**
 * 选中或取消评价项目
 * @param inspectId
 */
function checkInspectButton(inspectId){
	var $inspectCheck = $("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][value='"+inspectId+"']");
	if($inspectCheck.attr("checked")){
		$inspectCheck.attr("checked", false);
	} else{
		$inspectCheck.attr("checked", true);
	}
}

/**
 * 获取评价项目结果
 * @returns
 */
function getDiagnosisInspectResult(){
    var url = URL.get("Platform.DIAGNOSIS_INSPECT_RESULT");
    var params = "diagnosisId=" + $("#diagnosisId").val() + "&custId=" + $("#custId").val();
    ajax.post(url, params, dataType.json, function (data) {
		if(data){
			$("#resultTable").empty();
            var inspectResult=[];
			var pregArchive = data.value["pregArchive"];
			var resultMap = data.value["checkItem"];
			// 孕期建档结论
			if(!common.isEmpty(pregArchive)){
				inspectResult.push(resultTableTemplate("孕期建档：",pregArchive));
			}
         	// 膳食回顾结论
         	var dietReviewResult = resultMap["A00001"];
			if(!common.isEmpty(dietReviewResult) && !common.isEmpty(dietReviewResult.diagnosisResultNames)){
				inspectResult.push(resultTableTemplate("膳食日记回顾：",dietReviewResult.diagnosisResultNames));
                $("#dietReviewText").val(dietReviewResult.diagnosisResultNames);
			}			
         	// 膳食及生活方式结论
         	var dietLifeResult = resultMap["B00002"];
            if (!common.isEmpty(dietLifeResult) && !common.isEmpty(dietLifeResult.diagnosisResultNames)) {
                // 膳食评价结论
                var dietResults = dietLifeResult.diagnosisResultNames.split("###")[0];
                if(!common.isEmpty(dietResults)){
                	inspectResult.push(resultTableTemplate("膳食营养状态分析：",dietResults));
                }
                // 生活方式结论
                var lifeResults = dietLifeResult.diagnosisResultNames.split("###")[1];
                if(!common.isEmpty($.trim(lifeResults))){
                	inspectResult.push(resultTableTemplate("生活方式分析：",lifeResults.replace(/、/g,"")));
                };
            }
         	// 人体成分结论
         	var inbodyResult = resultMap["B00003"];
			if (!common.isEmpty(inbodyResult) && !common.isEmpty(inbodyResult.diagnosisResultNames)) {
                inspectResult.push(resultTableTemplate("人体成分结论：",inbodyResult.diagnosisResultNames));
            }
			// 剂量评估结论
			var extenderResult = resultMap["B00004"];
			if(!common.isEmpty(extenderResult)){
				ajax.post(URL.get("Customer.PLAN_EXTENDER_ANALYSIS_RESULT"), "diagnosisId=" + $("#diagnosisId").val(), dataType.json, function(extenderResult){
					if(!_.isEmpty(extenderResult.value)){
						var extenderText = [];
						$.each(extenderResult.value, function(key, value){
							extenderText.push(key + "：" + value);
						});
						inspectResult.push(resultTableTemplate("补充剂分析：", extenderText.join("<br>")));
					};
				}, false, false);
			}

			// 24小时膳食回顾结论
         	var dietaryReviewResult = resultMap["B00005"];
			if (!common.isEmpty(dietaryReviewResult) && !common.isEmpty(dietaryReviewResult.diagnosisResultNames)) {
                inspectResult.push(resultTableTemplate("24小时膳食回顾：",dietaryReviewResult.diagnosisResultNames));
            }
            
			// 孕期膳食频率调查
         	var dietaryReviewResult = resultMap["B00008"];
			if (!common.isEmpty(dietaryReviewResult) && !common.isEmpty(dietaryReviewResult.diagnosisResultNames)) {
                inspectResult.push(resultTableTemplate("孕期膳食频率调查：",dietaryReviewResult.diagnosisResultNames));
            }
            
			// 孕期生活方式调查
         	var dietaryReviewResult = resultMap["B00007"];
			if (!common.isEmpty(dietaryReviewResult) && !common.isEmpty(dietaryReviewResult.diagnosisResultNames)) {
                inspectResult.push(resultTableTemplate("孕期生活方式调查：",dietaryReviewResult.diagnosisResultNames));
            }
			
			// 快速营养问卷调查
         	var nutritiousResult = resultMap["B00006"];
			if (!common.isEmpty(nutritiousResult) && !common.isEmpty(nutritiousResult.diagnosisResultNames)) {
				inspectResult.push(resultTableTemplate("孕期快速营养调查：",nutritiousResult.diagnosisResultNames));
            }
            
            if(!common.isEmpty(inspectResult)){
	            var inspectResultString = inspectResult.join("");
	            $("#resultTable").html(inspectResultString);
            } else{
            	$("#resultTable").html("<tr><td><font color='gray'>请先做评价项目，评估结论会在此显示。</font></td></tr>");
            }
		}
    }, false, false);
}

/**
 * 生成结果显示的table tr模板
 * @param name
 * @param result
 * @returns
 */
function resultTableTemplate(name,result){
	var html = "<tr>"
		+ "         <td class='text-left' style='width:144px;'>"+name+"</td>"
		+ "         <td class='text-left'>"+result+"</td>"
        + "     </tr>";
    return html;
}

/**
 * 重新评估检测项目
 * @param id
 * @param inspectCode
 * @returns
 */
function resetDiagnosisItem(id, inspectCode){
	layer.confirm("重新评估将删除前一次评估记录，确认重新评估？", {
		btn: ["确认","取消"] // 按钮
	}, function(){
		layer.closeAll('dialog');
		var url = URL.get("Platform.USER_INPSECT_RESET");
		var params = "id=" + id;
		ajax.post(url, params, dataType.json, function(data){
			diagnosisInspectMap.get(inspectCode).inspectStatus = "2";
			refreshDiagnosisInspects();// 刷新列表
			getConfigHtml(id, inspectCode, "2");// 弹出窗口
		}, false, false);
		// 获取评价项目结果
		getDiagnosisInspectResult(); 
	});
}

/**
 * 修改检测项目状态
 * @param id
 * @param resultCode
 * @param inspectStatus
 * @returns
 */
function updateInspectStatus(id, resultCode, inspectStatus){
	if(_.isEmpty(inspectStatus) || inspectStatus == "3"){
		inspectStatus = "4";
	}
	var url = URL.get("Platform.PLAN_INSPECTSTATUS_UPDATE");
	var params = "id="+id+"&inspectStatus="+inspectStatus+"&resultCode="+resultCode+"&diagnosisId="+$("#diagnosisId").val();
	ajax.post(url, params, dataType.json, function(data){
		// 加载检测项目列表
		var inspectCode = data.value.inspectCode;
		diagnosisInspectMap.get(inspectCode).inspectStatus = inspectStatus;
		diagnosisInspectMap.get(inspectCode).resultCode = resultCode;
		refreshDiagnosisInspects();
		// 更新评价项目结论
		getDiagnosisInspectResult();
		// 更新接诊状态
		updateDiagnosisAssisStatus();
	}, true, false);
}

//=========================================【膳食回顾】============================================

var dietReviewOption = {
	rules: {
		dietReviewText:{
			required:true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	messages : {
		itemCode : {
			remote : "该编码已经存在，请修改"
		}
	},
	success: $.noop,
	submitHandler: function(form) {
		$(form).ajaxPost(dataType.json,function(data){
			if(data.value){
				//更新评价项目状态
				updateInspectStatus($("#dietReviewId").val(), $("#dietReviewId").val(), 3);
				//刷新评价项目结果
				getDiagnosisInspectResult();
				$("#dietReviewModal").modal("hide");
			}
		});
	}          
};

/**
 * 膳食回顾记录
 * @param id
 * @param inspectCode
 * @returns
 */
function getDietReview(id, inspectCode){
	$("#dietReviewId").val(id);
	$("#dietReviewCode").val(inspectCode);
	$("#dietReviewModal").modal("show");
}

//=========================================【人体成分】============================================

/**
 * 人体成分弹窗调用改方式 更新当前体重
 * @param wt
 * @returns
 */
function inbodyUse(wt){
	diagnosisJson.diagnosisCustWeight = wt;
	$("#weight_input_text").val(wt);
}

//=========================================【剂量评估】============================================

/**
 * 获取剂量评估结论表
 * @param inspectCode
 * @param inspectItem
 * @returns
 */
function getExtenderHistoryReport(inspectCode, inspectItem, id){
	$("#diagnosisInspectId").val(id);
	var url = URL.get("Customer.PLAN_EXTENDER_ANALYSIS_HISTORY");
	var params = "inspectCode="+inspectCode+"&inspectItem="+inspectItem;
	finalTakingCycleOptions = [];
	finalTakingCycle = [];
	ajax.post(url, params, dataType.json, function(data){
		if(!_.isEmpty(data.value) && !_.isEmpty(data.value.inspectId) && !_.isEmpty(data.value.resultMap)){
			$("#inspectId").val(data.value.inspectId);
			var trHtml0 = "<tr class='active'><th style='min-width: 140px;' class='text-center'>孕期阶段</th>";
			var trHtml1 = "<tr><th class='text-center'>服用周期</th>";
			var trHtml2 = "<tr><th class='text-center'>营养制剂</th>";
			var bodyMap = new Map();
			var nameArray = [];
			var recordCode = [];
			$.each(data.value.resultMap, function(key, list){// 遍历 Map<String, List<PregPlanExtenderAssessPojo>>
				if(!_.isEmpty(list)){
					trHtml0 += "<th class='text-center' colspan='"+list.length+"'>"+key.substring(1, key.length)+"</th>";
					$.each(list, function(index, value){// 遍历 List<PregPlanExtenderAssessPojo>
						// 获取补充剂列表
						$.each(value.extenderList, function(j, extender){
							nameArray.push(extender.indexName+" "+extender.productName);
						});
						if(key == "0备孕期"){
							trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+value.takingCycle+" 月</th>";
							finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+value.takingCycle+" 月</option>");// 记录周期
						} else{
							trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+value.takingCycle.substring(1, value.takingCycle.length)+" 周</th>";
							finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+value.takingCycle.substring(1, value.takingCycle.length)+" 周</option>");// 记录周期
						}
						finalTakingCycle.push(value.takingCycle);
						trHtml2 += "<td class='text-left'>"+nameArray.join("<br>")+"</td>";
						// 主体内容
						nameArray = [];
						$.each(elementAllList, function(c, element){
							var RNI = "——";
							var AI = "——";
							var UL = "——";
							var name = element.nutrientName+"（"+element.nutrientUnitName+"）";
							var detail = value.elementMap[element.nutrientId];
							var result = "";
							var tooltipId = "";
							var tooltipContent = "";
							if(!_.isEmpty(detail)){
								RNI = detail.elementRNI;
								AI = detail.elementAI;
								UL = detail.elementUL;
								diet = detail.dietElement || "——";
								extender = detail.extenderElement || "——";
								tooltipId = common.filterSpecialCharacters(element.nutrientId+value.takingCycle);
								if(!_.isEmpty(detail.detailStrList)){
									$.each(detail.detailStrList, function(d, str){
										tooltipContent += "<font color='red'>" + str + "</font><br>";
									});
								}
								result = "<div class='col-xs-5' style='padding-left: 4px;'>"+
										 "	<a id='tooltip_"+tooltipId+"'"+
										 "		data-html='true'"+
								         "	 	data-toggle='tooltip'"+
								         "	 	data-title=\""+tooltipContent+"<hr>"+UL+"\""+
								         "	 	onmouseover='common.showToolTipContent(\"tooltip_"+tooltipId+"\")'>"+RNI+"</a>"+
								         "</div>"+
								         "<div class='col-xs-7 no-padding'>" + 
								         	AI.replace("↓", "<font color='blue'>↓</font>").replace("↑", "<font color='red'>↑</font>")+
								         "</div>";
								recordCode.push(element.nutrientId);
							}
							if(bodyMap.has(element.nutrientId)){
								bodyMap.set(element.nutrientId, bodyMap.get(element.nutrientId) + "<td class='text-left'>"+result+"</td>");
							} else{
								bodyMap.set(element.nutrientId, "<tr id='temp_"+element.nutrientId+"'><td class='text-left'>"+name+"</td><td class='text-left'>"+result+"</td>");
							}
						});
					});
				}
			});
			trHtml0 += "</tr>";
			trHtml1 += "</tr>";
			trHtml2 += "</tr>";
			var bodyHtml = "";
			bodyMap.forEach(function(value, key){
				bodyHtml += bodyMap.get(key) + "</tr>";
			});
			$("#elementTable").html("<thead>" + trHtml0 + trHtml1 + "</thead>" + trHtml2 + bodyHtml);
			// 删除无用行
			$.each(elementAllList, function(c, element){
				if($.inArray(element.nutrientId, recordCode) == -1){
					$("#temp_"+element.nutrientId).remove();
				}
			});
		}
		
		// 设置打印报告周期选项
		$("#pdf_taking_cyle").html("");
		if(!_.isEmpty(finalTakingCycleOptions)){
			$.each(finalTakingCycleOptions, function(index, cycle){
				$("#pdf_taking_cyle").append(cycle);
			});
			$("#pdf_taking_cyle").multiselect("destroy").multiselect({  
                // 自定义参数，按自己需求定义  
				nonSelectedText : "==请选择服用周期==",   
                maxHeight : 350,   
                includeSelectAllOption : false,   
                numberDisplayed : false,
                nSelectedText: "个周期已选择",
                allSelectedText: "已全部选中",
                selectAllText: "全部选中",
                dropUp: true,
                buttonWidth: "100%",
                buttonContainer: "<div class='multiselect-wrapper' />",
                onChange: function(option, checked, select) {
                	$("#selectAllButton").attr("checked", false);
                }
            });
		}
		
		// 展示结果
		$("#assessModal").modal("show");
		
	}, false, false);
}

/**
 * 选中全部，清空服用周期下拉列表
 * @returns
 */
function selectAll(){
	$("#pdf_taking_cyle").multiselect("deselectAll", false);
	$("#pdf_taking_cyle").multiselect("updateButtonText");
}

/**
 * 打印PDF报告
 * @returns
 */
function getAssessExtenderPdf(){
	var finalCycle = null;
	if($("#selectAllButton").attr("checked")){
		$("#takingCycleList").val(finalTakingCycle);
		finalCycle = finalTakingCycle;
	} else{
		$("#takingCycleList").val($("#pdf_taking_cyle").val());
		finalCycle = $("#pdf_taking_cyle").val();
	}
	if(_.isEmpty(finalCycle)){
		layer.alert("请先选择服用周期");
		return false;
	}
	
	var url = URL.get("Customer.PLAN_EXTENDER_ASSESS_REPORT");
	var params = "id=" + $("#diagnosisInspectId").val() + "&takingCycleList="+finalCycle.join(",");
	ajax.post(url, params, dataType.json, function(pdfData){
    	common.openWindow(PublicConstant.basePath +"/"+ pdfData.value);
	});
}

/**
 * 更新当前体重
 * 
 * @param wt
 * @returns
 */
function inbodyUpdateWt(wt){
	$("#weight_input_text").val(wt);
	diagnosisJson.diagnosisCustWeight = wt;
}