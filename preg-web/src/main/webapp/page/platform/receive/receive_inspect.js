
$(document).ready(function(){
	// 膳食回顾的方法
    $("#dietReviewForm").validate(dietReviewOption);
    common.requiredHint("dietReviewForm", dietReviewOption);
	// 初始化营养评价项目
	refreshDiagnosisInspects();
	// 获取评价项目结果
	getDiagnosisInspectResult();
	// 默认评价项目弹出框关闭时触发事件
	initUserInspectItem("diagnosisInspectTable", "diagnosisInspect");
	$("#diagnosisInspectModal").on("hidden.bs.modal", function() {
		$("#diagnosisInspectTable").empty();
		initUserInspectItem("diagnosisInspectTable", "diagnosisInspect");
	});
	// 评价项目自动补全
	common.autocompleteMethod("receiveInspectItem", inspectListData, function(data){
		if(!diagnosisInspectMap.has(data.val.inspectCode)){
			var inspect = data.val;
			var diagnosisItemJson = {};
			diagnosisItemJson.inspectCode = inspect.inspectCode;
			diagnosisItemJson.inspectStatus = inspect.inspectStatus;
			diagnosisItemJson.diagnosisId = $("#diagnosisId").val();
			// 保存
			ajax.post(URL.get("Platform.DIAGNOSIS_INSPECT_ADD"), "diagnosisItemJson="+JSON.stringify(diagnosisItemJson), dataType.json, function(result){
				inspect.id = result.value;
				inspect.inspectStatus = "1";
				inspect.inspectPrompt = "";
				diagnosisInspectMap.set(inspect.inspectCode, inspect);
				refreshDiagnosisInspects();// 刷新操作区
			}, false, false);
		} else{
			layer.alert("该营养评价项目已添加！");
		}
		$("#receiveInspectItem").val("");
	});
});

/**
 * 局部刷新营养评价项目区
 * 
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
			// 初始化营养评价项目
			refreshDiagnosisInspects();
			// 获取评价项目结果
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
		// 展示营养评价报告单
		if(diagnosisInspectMap.has("B00006")){
			var b6status = diagnosisInspectMap.get("B00006").inspectStatus;
			if(b6status == "3"){
				$("#diagnosis_item_btns").append(
					"<div class='btn-group btn-group-right' id='receive_inspect_guide'>"+
					"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='getConfigHtml(\""+$("#diagnosisId").val()+"\", \"guide\", \"3\");'>营养评价报告单</button>" +
					"</div>"
				);
			} else{
				$("#diagnosis_item_btns").append(
					"<div class='btn-group btn-group-right' id='receive_inspect_guide'>"+
					"	<button type='button' class='btn btn-lightblue' style='height: 34px;' disabled>营养评价报告单</button>" +
					"</div>"
				);
			}
		}
		// 没有内容则隐藏提示区
		if($("#diagnosis_item_qits").children().length > 0){
			$("#diagnosis_item_qits").show();
		} else{
			$("#diagnosis_item_qits").hide();
		}
	}
}

/**
 * 获取评估项目操作按钮
 * 
 * @param inspect
 * @returns {String}
 */
function addInspectButton(inspect) {
	var id = inspect.id;
	var inspectCode = inspect.inspectCode;
	var inspectStatus = inspect.inspectStatus;
	var inspectName = inspect.inspectName;
	
	var inspectButtonHtml = "";
	// 膳食回顾需要特殊处理
	if(inspectCode == "A00001"){
		if(inspectStatus == "1"){
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue' style='color: red; height: 34px;' onclick='getDietReview(\""+id+"\",\""+inspectCode+"\");'>"+inspectName+"</button>" +
				"</div>";
		} else if(inspectStatus == "3"){
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='getDietReview(\""+id+"\",\""+inspectCode+"\");'>"+inspectName+"</button>" +
				"</div>";
		} else {
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>" +
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");' disabled>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "' readonly/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' disabled>" + inspectName + "</button>"+
				"</div>";
		}
	} else if (inspectStatus == "1") {// 医生未评估
		inspectButtonHtml = 
			"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>" +
			"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
			"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
			"	</button>"+
			"	<button type='button' class='btn btn-lightblue' style='color: red; height: 34px;' onclick='getConfigHtml(\""+id+"\", \""+inspectCode+"\", \""+inspectStatus+"\");'>" + inspectName + "</button>"+
			"</div>";
	} else if (inspectStatus == "2" || inspectStatus == "4") {// 助理未评估或助理评估完成未发送
		inspectButtonHtml = 
			"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>" +
			"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");' disabled>" + 
			"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "' readonly/>" +
			"	</button>"+
			"	<button type='button' class='btn btn-lightblue' style='height: 34px;' disabled>" + inspectName + "</button>"+
			"</div>";
	} else if (inspectStatus == "3") {// 医生评估完成
		// 安全剂量评估
		if(inspectCode == "B00004"){
			// 已经完成的评价项目
			var width = inspectName.length;
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='getConfigHtml(\""+id+"\", \""+inspectCode+"\", \"1\");'>"+ 
				"		<p>" + inspectName + "</p>" + // 正常状态显示
				"	</button>" +
				"	<button title='重新评估' type='button' class='btn btn-lightblue' style='height: 34px;' onclick='resetDiagnosisItem(\"" + id + "\",\"" + inspectCode + "\");'><i class='fa fa-refresh fa-fw'></i></button>"+
				"</div>";
		} else{
			// 已经完成的评价项目
			var width = inspectName.length;
			inspectButtonHtml = 
				"<div class='btn-group btn-group-right' id='receive_inspect_" + inspectCode + "'>"+
				"	<button type='button' class='btn btn-lightblue' style='height: 34px;' onclick='checkInspectButton(\"" + inspectCode + "\");'>" + 
				"		<input type='checkbox' name='inspectCheckList' value='" + inspectCode + "'/>" +
				"	</button>"+
				"	<button type='button' class='btn btn-lightblue btn-width' style='height: 34px;' onclick='getConfigHtml(\"" + id + "\",\"" + inspectCode + "\",\"" + inspectStatus + "\");'>"+ 
				"		<p class='hovershow' style='width:" + width + "em;'>查看报告</p>" + // 鼠标悬浮时显示
				"		<p class='hoverhide' style='width:" + width + "em;'>" + inspectName + "</p>" + // 正常状态显示
				"	</button>" +
				"	<button title='重新评估' type='button' class='btn btn-lightblue' style='height: 34px;' onclick='resetDiagnosisItem(\"" + id + "\",\"" + inspectCode + "\");'><i class='fa fa-refresh fa-fw'></i></button>"+
				"</div>";
		}
	}
	if(!_.isEmpty(inspectButtonHtml)){
		$("#noButtonDiv").remove();
	}
	return inspectButtonHtml;
}

/**
 * 删除评价项目操作按钮
 */
function deleteInspectButton(){
	var $inspectChecked = $("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][checked]");
	if($inspectChecked.length > 0){
		layer.confirm("确认【删除】选中的营养评价项目？", function(index){
			var inspectCodeArray = [];
			$inspectChecked.each(function(index, button){
				inspectCodeArray.push(button.value);
			});
			// 删除数据库的评价项目
			var params = "inspectCodeListStr="+inspectCodeArray.join(",")+"&diagnosisId="+$("#diagnosisId").val();
			ajax.post(URL.get("Platform.DIAGNOSIS_INSPECT_DELETE"), params, dataType.json, function(data){
				$inspectChecked.each(function(index, button){
					$("#receive_inspect_" + button.value).remove();
					diagnosisInspectMap.delete(button.value);
				});
				refreshDiagnosisInspects();// 刷新操作区
				getDiagnosisInspectResult();// 获取评价项目结果
			}, false, false);
			// 默认提示信息
			if($("#diagnosis_item_btns").children().length == 0){
				$("#diagnosis_item_btns").html("<div id='noButtonDiv'><font color='gray'>请先选择评价项目！</font></div>");
			}
			// 发送助理更新
			var urlSend = URL.get("Platform.DIAGNOSIS_INSPECT_SEND");
			var paramsSend = "info=''&infoType=refreshDiagnosisInspect&diagnosisId="+$("#diagnosisId").val();
			ajax.post(urlSend, paramsSend, dataType.json, null, false, false);
			layer.close(index);
		});
	} else{
		layer.alert("请先选中要删除的营养评价项目！");
	}
}

/**
 * 医生发送评价项目到助理端
 * 
 * @returns
 */
function sendInspect(){
	var $inspectChecked = $("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][checked]");
	if($inspectChecked.length > 0){
		var inspectCodeArray = [];
		var qitInspectArray = [];
		$inspectChecked.each(function(index, button){
			if(diagnosisInspectMap.get(button.value).inspectStatus == "3"){
				qitInspectArray.push(button.value);
			}
			inspectCodeArray.push(button.value);
		});
		if(!_.isEmpty(qitInspectArray)){
			var html = "<label class='label-control col-xs-10 col-xs-offset-1 text-left'>确认【发送】营养评价项目到助理端？</label>";
			$(qitInspectArray).each(function(index, value){
				var sendInspectName = diagnosisInspectMap.get(value).inspectName;
				html += 
					"<div class='col-xs-10 col-xs-offset-1 text-left row-top'>" + sendInspectName + "：</div>" +
					"<div class='col-xs-10 col-xs-offset-1' style='margin-top: 10px;'>" + 
					"	<textarea id='textarea_"+value+"' class='form-control' placeholder='请输入"+sendInspectName+"重新评估原因'></textarea>"+
					"</div>";
			});
			$("#sendQitBody").html(html);
			$("#sendInspectModal").modal("show");
		} else{
			layer.confirm("确认【发送】营养评价项目到助理端？", function(index){
				// 发送评价项目
				sendAjax(inspectCodeArray, "2");
				layer.close(index);
			});
		}
	} else{
		layer.alert("请先选中要发送的营养评价项目！");
	}
}

/**
 * 重新评估发送按钮
 * 
 * @returns
 */
function sendInspectButton(){
	// 勾选的评价项目
	var $inspectChecked = $("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][checked]");
	var inspectCodeArray = [];
	$inspectChecked.each(function(index, button){
		inspectCodeArray.push(button.value);
	});
	// 重新评估的评价项目
	$("#sendQitBody textarea").each(function(index, textarea){
		if(!_.isEmpty(textarea.value)){
			diagnosisInspectMap.get(textarea.id.split("_")[1]).inspectPrompt = textarea.value;
			ajax.post(URL.get("Platform.USER_INPSECT_RESET"), "id=" + diagnosisInspectMap.get(textarea.id.split("_")[1]).id, dataType.json, null, false, false);
		}
	});
	// 发送评价项目
	sendAjax(inspectCodeArray, "2");
	$("#sendInspectModal").modal("hide");
	$("#sendQitBody").empty();
}

/**
 * 发送评价项目ajax方法
 * 
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
			inspect.resultCode = "";
			inspect.inspectUser = "";
			inspect.inspectUserName = "";
			inspect.inspectStatus = inspectStatus;
			inspect.inspectPrompt = diagnosisInspectMap.get(value).inspectPrompt;
			inspect.diagnosisId = $("#diagnosisId").val();
			inspectArray.push(inspect);
		});
		
		var url = URL.get("Platform.DIAGNOSIS_INSPECT_SEND");
		var params = "info="+JSON.stringify(inspectArray)+"&infoType=inspect&diagnosisId="+$("#diagnosisId").val();
		ajax.post(url, params, dataType.json, function(data){
			$("#diagnosis_item_btns input:checkbox[name='inspectCheckList'][checked]").each(function(index, button){
				diagnosisInspectMap.get(button.value).inspectStatus = "2";
			});
			refreshDiagnosisInspects();
			getDiagnosisInspectResult();// 获取评价项目结果
		}, false, false);
	}
}

/**
 * 评估营养评价项目
 * 
 * @param inspectId
 * @param inspectCode
 * @param inspectStatus
 */
function getConfigHtml(id, inspectCode, inspectStatus) {
	if (inspectStatus == "3") {
		var backUrl = URL.get("ExamItemURL." + inspectCode + "_callback_url") + "?id=" + id;
		ajax.post(backUrl, "", dataType.json, function(data) {
			common.openWindow(PublicConstant.basePath + "/" + data.value);
		}, false, false);
	} else {
		var url = URL.get("ExamItemURL." + inspectCode + "_operate_url");
		if (url.indexOf("?") >= 0) {
			url += "&";
		} else {
			url += "?";
		}
		var operateFlag = true;
		// 24小时膳食回顾和膳食频率调查 需要体重
		if (inspectCode == "B00005" || inspectCode == "B00008") {
			if(_.isEmpty(diagnosisJson.diagnosisCustWeight)){
				operateFlag = false;
				layer.prompt({title: '请先完善体重（kg）！'}, function(value, index, elem){
					if(common.numberRegs.reg9.test(value) && value < 1000){
						var result = common.checkInputNumber("reg9", value, 1, false);
						if(value == result){
							var params = "diagnosisId="+diagnosisJson.diagnosisId+"&diagnosisCustWeight="+value;
							ajax.post(URL.get("Platform.DIAGNOSIS_UPDATE"), params, dataType.json, function(data){
								diagnosisJson.diagnosisCustWeight = value;
								$("#weight_input_text").val(value);
								common.openWindow(url + "custId=" + $("#custId").val() + "&id=" + id + "&diagnosisId=" + $("#diagnosisId").val());
							}, false, false);
							layer.close(index);
						} else{
							$(elem).val(result);
							layer.tips('小数点后只能保留1位！', "." + $(elem).attr("class"));
						}
					} else{
						layer.tips('请输入正确体重', "." + $(elem).attr("class"));
					}
				});
			}
		}
		if(operateFlag){
			common.openWindow(url + "custId=" + $("#custId").val() + "&id=" + id + "&diagnosisId=" + $("#diagnosisId").val());
		}
	}
}

/**
 * 选中或取消评价项目
 * 
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
 * 
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
 * 
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
 * 
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
			diagnosisInspectMap.get(inspectCode).inspectStatus = "1";
			refreshDiagnosisInspects();// 刷新列表
			getConfigHtml(id, inspectCode, "1");// 弹出窗口
		}, false, false);
		// 获取评价项目结果
		getDiagnosisInspectResult(); 
		// 发送助理更新
		var urlSend = URL.get("Platform.DIAGNOSIS_INSPECT_SEND");
		var paramsSend = "info=''&infoType=refreshDiagnosisInspect&diagnosisId="+$("#diagnosisId").val();
		ajax.post(urlSend, paramsSend, dataType.json, null, false, false);
	});
}

/**
 * 修改检测项目状态
 * 
 * @param id
 * @param resultCode
 * @param inspectStatus
 * @returns
 */
function updateInspectStatus(id, resultCode, inspectStatus){
	if(_.isEmpty(inspectStatus)){
		inspectStatus = "3";
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
		updateDiagnosisStatus();
	}, true, false);
	// 发送助理更新
	var urlSend = URL.get("Platform.DIAGNOSIS_INSPECT_SEND");
	var paramsSend = "info=''&infoType=refreshDiagnosisInspect&diagnosisId="+$("#diagnosisId").val();
	ajax.post(urlSend, paramsSend, dataType.json, null, false, false);
}

/**
 * 初始化个人默认评价项目
 * 
 * @param tableId
 * @param inputId
 * @returns
 */
function initUserInspectItem(tableId, inputId){
	if(!_.isEmpty(userInspectListJson)){
		$.each(userInspectListJson, function(index, value){
			addTdHtml(tableId, inputId, value);
		});
	}
}

/**
 * 弹出并初始化默认评价项目
 * 
 * @returns
 */
function showDiagnosisInspectModal(){
	common.modal("diagnosisInspectModal", "shown.bs.modal", function(){
		common.autocompleteMethod("diagnosisInspect", inspectListData, function(data){
			addTdHtml("diagnosisInspectTable", "diagnosisInspect", data.val);
		});
	});
}

/**
 * 添加评价项目Td
 * 
 * @param tableId
 * @param inputId
 * @param inspect
 * @returns
 */
function addTdHtml(tableId, inputId, inspect){
	if($("#" + tableId + "_" + inspect.inspectId).length == 0){
		$("#"+tableId).append(
			"<tr id='"+tableId+"_"+inspect.inspectId+"'>" +
			"	<td style='padding-left: 12px;' inspectCode='"+inspect.inspectCode+"' inspectName='"+inspect.inspectName+"' value='"+inspect.inspectId+"'>"+inspect.inspectName+
			"		<a onclick='removeTdHtml(\""+tableId+"_"+inspect.inspectId+"\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>"+	
			"	</td>" +
			"</tr>"
		);
	} else{
		layer.alert("该营养评价项目已添加！");
	}
	if(!_.isEmpty(inputId)){
		$("#"+inputId).val("");
	}
}

/**
 * 删除Td内容
 * 
 * @param id
 */
function removeTdHtml(id){
	$("#"+id).remove();
}

/**
 * 保存首诊个人默认评价项目
 * 
 * @returns
 */
function saveUserInspectItem(){
	var inspectArray = [];
	$("#diagnosisInspectTable td[inspectName]").each(function(index, value){
		var inspectItem = {};
		inspectItem.inspectId = $(value).attr("value");
		inspectItem.inspectType = "vist";
		inspectItem.inspectCode = $(value).attr("inspectCode");
		inspectItem.inspectName = $(value).attr("inspectName");
		inspectArray.push(inspectItem);
	});
	if(_.isEmpty(inspectArray)){
		layer.alert("请添加评价项目！");
		return false;
	}
	var url = URL.get("Platform.USER_INSPECT_ITEM_SAVE");
	var params = "userInspectItemListJson="+JSON.stringify(inspectArray)+"&inspectType=vist";
	ajax.post(url, params, dataType.json, function(data){
		userInspectListJson = data.value;
		$("#diagnosisInspectModal").modal("hide");
	}, false, false);
}

// =========================================【膳食回顾】============================================

var dietReviewOption = {
	rules: {
		dietReviewText:{
			required:true
		}
	},
	// 设置错误信息显示到指定位置
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
				// 更新评价项目状态
				updateInspectStatus($("#dietReviewId").val(), $("#dietReviewId").val(), 3);
				// 刷新评价项目结果
				getDiagnosisInspectResult();
				$("#dietReviewModal").modal("hide");
			}
		});
	}          
};

/**
 * 膳食回顾记录
 * 
 * @param id
 * @param inspectCode
 * @returns
 */
function getDietReview(id, inspectCode){
	$("#dietReviewId").val(id);
	$("#dietReviewCode").val(inspectCode);
	$("#dietReviewModal").modal("show");
}

// =========================================【人体成分】============================================

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