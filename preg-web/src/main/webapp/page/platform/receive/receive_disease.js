// 诊断项目Map集合
var diseaseMap = new Map();
/**
 * 诊断项目的json对象封装
 * @param name 诊断项目名称
 * @param type 类型：0确诊的诊断，1勾选的待确诊诊断，2指标推断的待确诊诊断
 * @param id
 * @param code
 * @param icd10
 * @param status
 * @returns
 */
function DiseaseObj(name, status, type, id, code, icd10){
	//必填
	this.diseaseName = name;
	this.diseaseStatus = status == '待确诊'?'待确诊':'确诊';
	//非必填
	this.type = type || '0';
	//防止旧数据没有type属性
	if(status == '待确诊'){
		this.type = '1'; 
	}
	if(type == '2'){
		this.diseaseStatus = '待确诊';
	}
	this.diseaseId = id || '';
	this.diseaseCode = code || '';
	this.diseaseIcd10 = icd10 || '';
}

/**
 * 页面初始化方法
 */
$(function () {
	// 初始化诊断项目的map集合
	$.each(interveneDiseaseList, function(index, value){
		diseaseMap.set(value.diseaseName,value);
	});
	// 添加诊断项目的方法
	autocompleteDiseaseMethod("diagnosisAddDisease");
	// 初始化诊断项目
	initdiagnosisDiseaseJson();
	// 初始化历史诊断项目
	loadDiagnosisHistoryLisst();
	// 初始化常用诊断项目 
	diseaseOftenOperation.initDiseaseOften();
});

/**
 * 补全的方法
 */
function autocompleteDiseaseMethod(id){
	// 自动补全功能使用的集合
	var diseaseListData = [];
	// 遍历所有诊断疾病
	$.each(interveneDiseaseList,function(index, obj){
		diseaseListData.push({name:obj.diseaseName, val:obj});
	});
	// 诊断项目添加到自动补全
	$("#"+id).autocomplete(diseaseListData, {
		position: {my:"right bottom", at:"right bottom"},
		width: 170,
		matchContains: true,
		autoFill: false,
		formatItem: function(row, i, max) {        
			return row.name;
		},
        formatMatch: function(row, i, max) {
            return row.name + " " + pinyin.getCamelChars(row.name);
        },
        formatResult: function(row) {
            return row.name;
        }
	}).result(function(event, data, formatted){
		if(id=="diagnosisAddDisease"){//诊断项目
			saveDiagnosisDisease(data.val.diseaseName);
		}else if(id == "addDiseaseOften"){//常用诊断项目列表
			diseaseOftenOperation.addDiseaseOften(data.val);
			$("#"+id).val("");
		}
    });
}

/**
 * 诊断项目添加按钮的方法
 */
function saveDiagnosisDiseaseButton(){
	saveDiagnosisDisease();
}

/**
 * 添加诊断项目
 * @param diseaseName
 * @param type 类型: 2指标推断的待确诊诊断
 */
function saveDiagnosisDisease(diseaseName, type){

	// 是否指定了诊断项目
	if(common.isEmpty(diseaseName)){
		diseaseName = $("#diagnosisAddDisease").val();
	}
	if(common.isEmpty(diseaseName)){
		layer.alert("请先输入诊断项目名称！");
		return;
	}
	
	//清空诊断项目添加的输入框
	$("#diagnosisAddDisease").val("");
	
	//判断是否选择了待确诊
	var statusStr = '确诊';
	if(common.isEmpty(type) && $('#daiquezhen').attr('checked')){
		type = '1';//如果勾选了“待确诊”则type修改为1
		statusStr = '待确诊';
		$('#daiquezhen').attr('checked',false);//取消“待确诊”勾选
	}
	
	//判断是否是自定义的诊断项目
	var disJson = new DiseaseObj(diseaseName, statusStr, type);
	if(!common.isEmpty(diseaseMap.get(diseaseName))){
		var dis = diseaseMap.get(diseaseName);
		disJson = new DiseaseObj(diseaseName, statusStr, type, dis.diseaseId, dis.diseaseCode, dis.diseaseIcd10);
	}
	if(addDiagnosisDisease(disJson)){
		addRow(disJson);
	}
}

/**
 * 添加行的方法
 */
function addRow(value){
	$("#diagnosis_disease_norecord_tr").remove();
	var colorHtml = "";
	var nameHtml = value.diseaseName;
	if(value.type == '2'){
		colorHtml = "style='color:red;'";
	}
	if(value.diseaseStatus == '待确诊'){
		nameHtml = value.diseaseName + "&nbsp;&nbsp;<button class='btn-sm disease-sure-btn' type='button' onclick='verifyDisease(this,\"" + value.diseaseName + "\")'>确诊</button>";
	}
	$("#diagnosis_disease_tbody").append(
			"<tr ondblclick='removeRow(\"" + value.diseaseName + "\", this)'>"+
			"	<td class='text-left' " + colorHtml + ">" + nameHtml + "</td>"+
			"	<td class='text-center'>" + value.diseaseIcd10 + "</td>" +
			"	<td class='text-center'>" + value.diseaseStatus + "</td>" +
			"	<td class='text-center'><a onclick='removeRow(\"" + value.diseaseName + "\", this)'>删除</a></td>"+
			"</tr>"
	);
}

/**
 * 初始化诊断项目列表
 */
function initdiagnosisDiseaseJson(){
	// 清空
	$("#diagnosis_disease_tbody").empty();
	//初始化接诊诊断项目
	if(!_.isEmpty(diagnosisDiseaseNow) && diagnosisDiseaseNow.length != 0){
		// 遍历
		$.each(diagnosisDiseaseNow,function(index, value){
			addRow(value);// js添加诊断项目
		});
	}
	if($("#diagnosis_disease_tbody").find("tr").length <= 0){
		$("#diagnosis_disease_tbody").append(
				"<tr id='diagnosis_disease_norecord_tr'><td colspan='11' class='text-center'><h4>没有找到记录！</h4></td></tr>"
		);
	}
}

/**
 * 诊断项目的添加按钮
 */
function verifyDisease(obj, name){
	$(obj).hide();//隐藏元素
	$(obj).parent().next().next().html("确诊");
	url = URL.get("Platform.DIAGNOSIS_DISEASE_UPDATE");
	params = "diseaseName=" + name + "&diagnosisId=" + diagnosisJson.diagnosisId;
	ajax.get(url, params, dataType.json, null, false, false);
}

/**
 * js删除诊断项目
 */
function removeRow(diseaseName, obj){
	layer.confirm('确认删除该诊断项目吗？', {btn: ['是','否']},function(layerIndex){
		url = URL.get("Platform.DIAGNOSIS_DISEASE_DELETE");
		params = "diseaseName=" + diseaseName + "&diagnosisId=" + diagnosisJson.diagnosisId;
		ajax.get(url, params, dataType.json, function(data){
			if($(obj).is('tr')){
				$(obj).remove();
			}else{
				$(obj).parent().parent().remove();
			}
			if($("#diagnosis_disease_tbody").find("tr").length <= 0){
				$("#diagnosis_disease_tbody").append(
						"<tr id='diagnosis_disease_norecord_tr'><td colspan='11' class='text-center'><h4>没有找到记录！</h4></td></tr>"
				);
			}
		}, false, false);
		layer.close(layerIndex);
	},function(layerIndex){
		layer.close(layerIndex);
	});
}

/**
 * 更新诊断项目
 * 在删除行，点击确认修改行的时候使用
 */
function addDiagnosisDisease(diseaseJson){
	// 保存诊断项目
	var url = URL.get("Platform.DIAGNOSIS_DISEASE_ADD");
	diseaseJson.diagnosisId = diagnosisJson.diagnosisId;
	var params = "disease="+JSON.stringify(diseaseJson);
	var result = false;
	ajax.post(url, params, dataType.json, function(data){
		if(data.value){
			result = true;
			if("2" != diseaseJson.type){
				layer.msg("添加成功");
			}
		}else{
			if("2" != diseaseJson.type){
				layer.alert("诊断项目已存在，添加失败");
			}
		}
		updateDiagnosisStatus();
	}, false, false);
	return result;
}

/**
 * 历史诊断项目列表
 */
function loadDiagnosisHistoryLisst(){
	$("#diagnosis_disease_history_tbody").html("");
	if(!common.isEmpty(diagnosisHistoryList)){
		$.each(diagnosisHistoryList, function(index, value){
			$("#diagnosis_disease_history_tbody").append(
				"<tr>"+
				"	<td class='text-center'><a onclick='getSummary(\""+value.diagnosisId+"\");'>"+value.diagnosisDate+"</a></td>"+
				"	<td class='text-center'>"+pregnancy.gestationalWeeksSupHtml(value.diagnosisGestationalWeeks)+"</td>"+
				"	<td class='text-center'>"+value.diagnosisUserName+"</td>"+
				"	<td class='text-left'>"+nameDetailOnMouseover(value.diagnosisId+"_"+index, value.diagnosisDiseases, value.diagnosisId)+"</td>"+
				"</tr>"
			);
		});
	}
	// 判断是否为空
	if($("#diagnosis_disease_history_tbody tr").length < 1){
		$("#diagnosis_disease_history_tbody").append('<tr><td colspan="11" class="text-center"><h4>没有找到记录！</h4></td></tr>');
	}
}
/**
 * 获取病例小结
 */
function getSummary(id){
	common.openWindow(PublicConstant.basePath + "/page/platform/tool/tool_disgnosis_summary_window.jsp?diagnosisId=" + id);
}

/**
 * 点击历史诊断项目添加到当前诊断项目
 */
function getHistoryToNow(diagnosisOldId) {
	url = URL.get("Platform.DIAGNOSIS_DISEASE_BATCH_SAVE");
	params = "diagnosisOldId=" + diagnosisOldId + "&diagnosisId=" + diagnosisJson.diagnosisId;
	ajax.get(url, params, dataType.json, function(data){
		if(data.value > 0){
			$.each(data.data, function(index, value){
				addRow(value);
			});
		}
		layer.alert(data.message);
		updateDiagnosisStatus();
	}, false, false);
}

/**========================================常用诊断项目========================================*/
/** 常用诊断项目部分*/
var diseaseOftenOperation={
		id:"addDiseaseOften",//自动补全控件ID
		showID:"allDiseaseListTable",//常用诊断项目列表显示区域 ID
		initDiseaseOften:function(){//初始化常用诊断项目列表及自动补全插件
			initDiseaseOften(diseaseOftenOperation.id,diseaseOftenOperation.showID);
		},
		addDiseaseOften:function(diseaseOften){//添加诊断列表中的常用诊断项目
			saveDiseaseOften(diseaseOften);
		},
		addCustom:function(){//添加自定义的常用诊断项目
			addCell();
			$("#"+diseaseOftenOperation.id).val("");
		},
		removeDiseaseOften:function(diseaseId){//逻辑删除常用诊断项目
			removeDiagnosisOften(diseaseId);
		},
		checkNameDiseaseOften:function(diseaseOften){//验证常用诊断项目名称是否重复
			return checkDiseaseOftenRepeat(diseaseOften);
		}
};

/** 初始化常用诊断项目,初始化自动补全*/
function initDiseaseOften(id,showID){
	initDiseaseOftenTable(showID);//初始化常用诊断项目列表
	autocompleteDiseaseMethod(id);//初始化诊断项目自动补全数据
}

/** 初始化常用诊断项目列表*/
function initDiseaseOftenTable(showID){
	var url = URL.get("Platform.DISEASE_OFTEN_QUERY");
	ajax.post(url, null, dataType.json, function(result){
		if(result.data.length > 0){
			$.each(result.data,function(index, diseaseOften){
				$("#"+showID).append(diseaseOftenTemplate(diseaseOften));
			});
		}else{
			$("#"+showID).append(noData());
		}
	},false,false);
}

/** 验证是否重复添加常用诊断项目*/
function checkDiseaseOftenRepeat(diseaseOften){
	var flag = false;
	var url = URL.get("Platform.DISEASE_OFTEN_CHECK_NAME");
	var params = "diseaseName=" + diseaseOften.diseaseName;
	ajax.post(url, params, dataType.json, function(result){
		if(result.value){
			flag = true;
		}
	},false,false);
	return flag;
}

/** 添加自定义常用诊断项目*/
function addCell(){
	var diseaseName =$("#addDiseaseOften").val();
	if(diseaseName.length > 15){
		layer.msg("名称长度过长！");
		return;
	}
	if(diseaseName.length <= 0){
		layer.msg("必须填写项目名称！");
		return;
	}
	var diseaseOften={"diseaseName":diseaseName};
	diseaseOftenOperation.addDiseaseOften(diseaseOften);
}

/** 添加常用诊断项目*/
function saveDiseaseOften(diseaseOften){
	$(".noData").remove();
	var flag = diseaseOftenOperation.checkNameDiseaseOften(diseaseOften);
	if(flag==true){
		var url = URL.get("Platform.DISEASE_OFTEN_ADD");
		var params = "disease=" + JSON.stringify(diseaseOften);
		ajax.post(url, params, dataType.json, function(data){
			$("#allDiseaseListTable").append(diseaseOftenTemplate(data.value));
		},false,false);
	}else{
		layer.msg(diseaseOften.diseaseName+"已经添加！");
	}
}

/** 删除常用诊断项目*/
function removeDiagnosisOften(diseaseId){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		}, function(){
			var url = URL.get("Platform.DISEASE_OFTEN_REMOVE");
			var params = "diseaseId=" + diseaseId;
			ajax.post(url, params, dataType.json, function(data){
				$("#"+diseaseId).remove();
				  if($("#allDiseaseListTable").children().length <= 0 ){
					  $("#allDiseaseListTable").append(noData());
				  }
				  layer.msg('删除成功！');
			});
		});
}

/** 常用诊断项目添加到诊断项目*/
function saveDisease(diseaseName){
	//诊断表格添加一行
	saveDiagnosisDisease(diseaseName);
}
/** 常用诊断项目模板*/
function diseaseOftenTemplate(diseaseOften){
	var diseaseName=diseaseOften.diseaseName;
	var name;
    if (diseaseName.length >= 10) {
		name ="<a title='"+diseaseName+"' onclick='saveDisease(\""+diseaseOften.diseaseName+"\")'>"+diseaseName.substring(0,10)+"......" +"</a> ";
    }else{
    	name="<a  title='"+diseaseName+"' onclick='saveDisease(\""+diseaseOften.diseaseName+"\")'>"+diseaseOften.diseaseName+"</a> ";
    }
	var template="<div id='"+diseaseOften.diseaseId+"' class='JustifyAlign col-xs-2 table-bordered' >"+name
				+	"<a onclick='diseaseOftenOperation.removeDiseaseOften(\""+diseaseOften.diseaseId+"\")'><i class='fa fa-remove fa-fw'></i></a> " 
				+"</div>";
	return template;
}

/** 常用诊断项目列表为空时的模板*/
function noData(){
	var template="<div class='noData'>"
				+ "   <h4>没有找到记录！</h4>"
				+"</div>";
	return template;
}
//========================================工具方法========================================
/**
 * 浮窗显示
 * @param code
 * @param content
 * @returns {String}
 */
function nameDetailOnMouseover(code, content, diagnosisOldId){
	return "<a id='mark_" + code + "' data-toggle='tooltip' data-placement='left' title='" + content + "' onclick='getHistoryToNow(\"" + diagnosisOldId + "\")'>" + content + "</a>";
}
