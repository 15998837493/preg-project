
// 孕期--服用周期选项
var weekOptions = "<option value=''>请选择孕周</option>";
for(var i=1;i<=gestationalWeeks;i++){
	weekOptions += "<option value='"+i+"'>第"+i+"周</option>";
}

// 备孕期--服用周期选项
var monthOptions = "<option value=''>请选择周期</option><option value='0.5'>半个月</option>";
for(var i=1;i<=12;i++){
	monthOptions += "<option value='"+i+"'>"+i+"个月</option>";
}

// 记录上一次数值
var oldWeekValue = "";

// 服用周期，打印报告选择项
var finalTakingCycleOptions = [];
var finalTakingCycle = [];

// 产品代码表
var codeMap=new Map();
codeMap.set("productAttribute","PRODUCT_ATTRIBUTE");
codeMap.set("productFrequency","PRODUCTFREQUENCY");
codeMap.set("productIsOfficina","TRUEORFALSE");
codeMap.set("productIsAssess","TRUEORFALSE");
codeMap.set("productIsEnergy","TRUEORFALSE");

//************************************* 补充剂库 *************************************
//选中项信息
var extenderData;
// 选中行信息
var supplementsRow;
// table对象
var supplementsTable;
//补充剂库表单配置
var supplementsTableOpt = {
	id : "supplementsTable",
	form : "supplementsForm",
	columns : [{
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return common.setImg(PublicConstant.basePath+"/resource/upload/product/ext/"+data.productPic);
		}
	}, {
		"data" : "productCategoryName",
		"sClass" : "text-center"
	}, {
		"data" : "productName",
		"sClass" : "text-left"
	}, {
		"data" : "productUnit",
		"sClass" : "text-center"
	}, {
		"data" : "productSellPrice",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			if(common.isEmpty(data)){
				return "";
			}
			var result = data/100.0;
			return result.toFixed(2);
		}
	}, {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<a href='javascript:addProduct2Order();'>添加</a>";
		}
	}],
	rowClick : function(data, row) {
		extenderData = data;
	},
	condition : "supplementsCondition",
	loading: false
};

/**
 * 多级导航栏--产品分类
 */
function initDdlHtmlFunc(){
	var url = URL.get("Master.PRODUCT_CATALOG_QUERY_ALL");
	ajax.post(url, null, dataType.json, function(result) {
		common.initDdlHtml(result.data, "productCategoryDiv", "请选择类别", function(data){
			if(!_.isEmpty(data.value.catalogId)){
				supplementsTableOpt.searchExtendSelect = [[1, data.value.catalogName]];
			} else{
				supplementsTableOpt.searchExtendSelect = [[1, ""]];
			}
			datatable.search(supplementsTable, supplementsTableOpt);
		}, "<li><a data-ddl='{\"name\":\"请选择类别\",\"value\":{\"catalogId\":\"\",\"catalogName\":\"请选择类别\"}}'>请选择类别</a></li>");
	}, false, false);
}

//************************************* 产品列表 *************************************
var rowIndex = 0;

/**
 * 浮窗显示
 * @param code
 * @param content
 * @returns {String}
 */
function nameDetailOnMouseover(productId,rowIndex,content){
	return "   <a id='tooltip_"+productId+"_"+rowIndex+"'" + 
	       "   data-toggle='tooltip'" + 
	       "   data-placement='top'" +
	       "   data-title='"+content+"'" +
	       "   onclick='showProductInfo(\"" + productId + "\");'" + 
	       "    title='"+content+"'>" + content + "</a>";
}

/**
 * 添加产品到评估列表
 */
function addProduct2Order(){
	$("#noRecordTr").remove();
	// 先判断已选列表中是否存在
	var productName = extenderData.productName;
	var productId = extenderData.productId;
	// 添加至已选列表
	$("#nutrientDoseListTable").append(
		"<tr id='"+productId+"_tr_"+rowIndex+"' "+productId+"_tr>"+
		"		<input type='hidden' name='extenderList["+rowIndex+"].productId' value='" + productId + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productCode' value='" + extenderData.productCode + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productName' value='" + productName + "'/>" +
		"		<input type='hidden' name='extenderList["+rowIndex+"].productUnit' value='" + extenderData.productUnit + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productUsageMethod' value='" + extenderData.productUsageMethod + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productTreatment' value='" + extenderData.productTreatment + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productStandard' value='" + extenderData.productStandard + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productPic' value='" + extenderData.productPic + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productPackageUnit' value='" + extenderData.productPackageUnit + "'/>" + 
		"		<input type='hidden' name='extenderList["+rowIndex+"].productDosageDesc' value='" + extenderData.productDosageDesc + "'/>" + 
		"	<td class='text-center'>" + common.setImg(PublicConstant.basePath+"/resource/upload/product/ext/"+extenderData.productPic) + "</td>" +
		"	<td>" +  
/*		"		<a id='tooltip_"+productId+"_"+rowIndex+"'" + 
		"   		data-toggle='tooltip'" + 
		"   		data-title='" + productName + "'" + 
		"   		onclick='showProductInfo(\"" + productId + "\");'" + 
		"   		onmouseover='common.showToolTipContent(\"tooltip_" + productId + "_"+rowIndex+"\");'>" + productName + 
		"		</a>" + */
		nameDetailOnMouseover(productId,rowIndex,productName) +
		"	</td>" +
		"	<td>" +
		"   	<div class='input-group'>"+
		"		<input type='text' class='form-control input-sm' "+
		"						   id='"+productId+"_productDosage_"+rowIndex+"' " +
		"						   name='extenderList["+rowIndex+"].productDosage' " +
		"						   value='1' input-required productDosage-list/>"+
		"       <div class='input-group-addon'>" + extenderData.productUnit + "</div>" +
		"    </div>" +
		"	</td>" +
		"	<td class='text-center'>" + extenderData.productDosageDesc + "</td>" +
		"	<td class='text-center'>" + extenderData.productUsageMethod + "</td>" +
		"	<td class='text-center'>" + 
		"		<select class='form-control input-sm' id='"+productId+"_productFrequency_"+rowIndex+"' name='extenderList["+rowIndex+"].productFrequency' productFrequency-list></select>" +  
		"	</td>" +
		"	<td class='text-center'>" + 
		"		<select class='form-control input-sm' id='"+productId+"_regularity_"+rowIndex+"' name='extenderList["+rowIndex+"].regularity'>"+
		"			<option value='规律'>规律</option>"+
		"			<option value='较规律'>较规律</option>"+
		"			<option value='不规律'>不规律</option>"+
		"		</select>" +  
		"	</td>" +
		"	<td class='text-center'>" +  
		"		<select class='form-control input-sm' id='"+productId+"_pregnancy_"+rowIndex+"' name='extenderList["+rowIndex+"].pregnancy' onchange='changePregnancy(this);' pregnancy-list>" +  
		"			<option value='孕期'>孕期</option>"+
		"			<option value='备孕期'>备孕期</option>"+
		"		</select>" +  
		"	</td>" + 
		"	<td class='text-center'>" +  
		"     	<div class='col-xs-5 no-padding'>"+
		"			<select class='form-control input-sm' id='"+productId+"_start_"+rowIndex+"' "+productId+"_start_end name='extenderList["+rowIndex+"].cycleStart' takingCycle-list onchange='changeTakingCycle(this);' onclick='recordOldWeekValue(this.value);'>"+weekOptions+"</select>"+
		"		</div>" +  
		"       <div class='col-xs-2 no-padding' style='margin-top: 5px;'>至</div>"+
		"     	<div class='col-xs-5 no-padding'>"+
		"			<select class='form-control input-sm' id='"+productId+"_end_"+rowIndex+"' "+productId+"_start_end name='extenderList["+rowIndex+"].cycleEnd' takingCycle-list onchange='changeTakingCycle(this);' onclick='recordOldWeekValue(this.value);'>"+weekOptions+"</select>"+
		"		</div>" +  
		"   </td>" + 
		"	<td class='text-center'><a href='javascript:void(0);' onclick='removeProduct2Order(this);'>删除</a></td>" +
		"</tr>"
	);
	common.initCodeSelect("PRODUCTFREQUENCY", "PRODUCTFREQUENCY", productId+"_productFrequency_"+rowIndex, extenderData.productFrequency);// 频次
	$("input[input-required]").bindNumber();
	
	rowIndex++;
}

/**
 * 初始化已编辑的补充剂列表
 */
function initExtenderListInfo(){
	if(!_.isEmpty(initExtenderList)){
		$.each(initExtenderList, function(index, extenderData){
			$("#noRecordTr").remove();
			//先判断已选列表中是否存在
			var productName = extenderData.productName;
			var productId = extenderData.productId;
			var pregnancy = extenderData.pregnancy;
			var pregHtml = // 孕期
					"	<td class='text-center'>" +  
					"     	<div class='col-xs-5 no-padding'>"+
					"			<select class='form-control input-sm' id='"+productId+"_start_"+rowIndex+"' "+productId+"_start_end name='extenderList["+rowIndex+"].cycleStart' takingCycle-list onchange='changeTakingCycle(this);' onclick='recordOldWeekValue(this.value);'>"+weekOptions+"</select>"+
					"		</div>" +  
					"       <div class='col-xs-2 no-padding' style='margin-top: 5px;'>至</div>"+
					"     	<div class='col-xs-5 no-padding'>"+
					"			<select class='form-control input-sm' id='"+productId+"_end_"+rowIndex+"' "+productId+"_start_end name='extenderList["+rowIndex+"].cycleEnd' takingCycle-list onchange='changeTakingCycle(this);' onclick='recordOldWeekValue(this.value);'>"+weekOptions+"</select>"+
					"		</div>" +  
					"   </td>";
			var pareHtml = // 备孕期
					"	<td class='text-center'>" +  
					"     	<div class='col-xs-12 no-padding'>"+
					"			<select class='form-control input-sm' id='"+productId+"_takingCycle_"+rowIndex+"' "+productId+"_pare name='extenderList["+rowIndex+"].takingCycle' takingCycle-list>"+monthOptions+"</select>"+
					"		</div>" +  
					"   </td>";
			
			if($("#nutrientDoseListTable #"+productId+"_tr").length == 0){
				//添加至已选列表
				$("#nutrientDoseListTable").append(
					"<tr id='"+productId+"_tr_"+rowIndex+"' "+productId+"_tr>"+
					"		<input type='hidden' name='extenderList["+rowIndex+"].productId' value='" + productId + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productCode' value='" + extenderData.productCode + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productName' value='" + productName + "'/>" +
					"		<input type='hidden' name='extenderList["+rowIndex+"].productUnit' value='" + extenderData.productUnit + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productUsageMethod' value='" + extenderData.productUsageMethod + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productTreatment' value='" + extenderData.productTreatment + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productStandard' value='" + extenderData.productStandard + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productPic' value='" + extenderData.productPic + "'/>" + 
					"		<input type='hidden' name='extenderList["+rowIndex+"].productPackageUnit' value='" + extenderData.productPackageUnit + "'/>" + 
					"	<td class='text-center'>" + common.setImg(PublicConstant.basePath+"/resource/upload/product/ext/"+extenderData.productPic) + "</td>" +
					"	<td>" +  
					"		<a id='tooltip_"+productId+"_"+rowIndex+"'" + 
					"   		data-toggle='tooltip'" + 
					"   		data-title='" + productName + "'" + 
					"   		onclick='showProductInfo(\"" + productId + "\");'" + 
					"   		onmouseover='common.showToolTipContent(\"tooltip_" + productId + "_"+rowIndex+"\");'>" + productName + 
					"		</a>" + 
					"	</td>" +
					"	<td>" +
					"   	<div class='input-group'>"+
					"		<input type='text' class='form-control input-sm' "+
					"						   id='"+productId+"_productDosage_"+rowIndex+"' " +
					"						   name='extenderList["+rowIndex+"].productDosage' " +
					"						   value='"+extenderData.productDosage+"' input-required productDosage-list/>"+
					"       <div class='input-group-addon'>" + extenderData.productUnit + "</div>" +
					"	</td>" +
					"	<td class='text-center'>" + extenderData.productDosageDesc + "</td>" +
					"	<td class='text-center'>" + extenderData.productUsageMethod + "</td>" +
					"	<td class='text-center'>" + 
					"		<select class='form-control input-sm' id='"+productId+"_productFrequency_"+rowIndex+"' name='extenderList["+rowIndex+"].productFrequency' productFrequency-list></select>" +  
					"	</td>" +
					"	<td class='text-center'>" + 
					"		<select class='form-control input-sm' id='"+productId+"_regularity_"+rowIndex+"' name='extenderList["+rowIndex+"].regularity'>"+
					"			<option value='规律'>规律</option>"+
					"			<option value='较规律'>较规律</option>"+
					"			<option value='不规律'>不规律</option>"+
					"		</select>" +  
					"	</td>" +
					"	<td class='text-center'>" +  
					"		<select class='form-control input-sm' id='"+productId+"_pregnancy_"+rowIndex+"' name='extenderList["+rowIndex+"].pregnancy' onchange='changePregnancy(this);' pregnancy-list>" +  
					"			<option value='孕期'>孕期</option>"+
					"			<option value='备孕期'>备孕期</option>"+
					"		</select>" +  
					"	</td>" + ((pregnancy == "孕期")?pregHtml:pareHtml) + 
					"	<td class='text-center'><a href='javascript:void(0);' onclick='removeProduct2Order(this);'>删除</a></td>" +
					"</tr>"
				);
				$("#"+productId+"_productDosage_"+rowIndex).val(extenderData.productDosage);// 单次剂量
				common.initCodeSelect("PRODUCTFREQUENCY", "PRODUCTFREQUENCY", productId+"_productFrequency_"+rowIndex, extenderData.productFrequency);// 频次
				$("#"+productId+"_regularity_"+rowIndex).val(extenderData.regularity);// 是否规律
				$("#"+productId+"_pregnancy_"+rowIndex).val(extenderData.pregnancy);// 孕期
				var takingCycle = extenderData.takingCycle;// 服用周期
				if(pregnancy == "孕期"){
					$("#"+productId+"_start_"+rowIndex).val(takingCycle.split(",")[0]);// 开始周期
					$("#"+productId+"_end_"+rowIndex).val(takingCycle.split(",")[1]);// 结束周期
				} else{
					$("#"+productId+"_takingCycle_"+rowIndex).val(takingCycle);// 服用周期
				}
				$("input[input-required]").bindNumber();
				
				rowIndex++;
			}
		});
	}
}

/**
 * 单击明细按钮显示商品信息的方法
 * @param productId
 */
function showProductInfo(productId){
	var product = productAllMap[productId];
	if(!_.isEmpty(product)){
		$.each(product, function(key, val) {
			if(_.isEmpty(val)){
				val="无";
			}
			if(codeMap.get(key)){
				val=CODE.transCode(codeMap.get(key),val);
			}
			$("#orderProductModel #product_"+key).html(val);
		});	
	}
	$("#orderProductModel").modal('show');
}

/**
 * 孕期变换时调整输入框
 * @param obj
 */
function changePregnancy(obj){
	var id = obj.id;
	var value = obj.value;
	var productId = id.split("_")[0];
	var indexCount = id.split("_")[2];
	if(value == "孕期"){
		$($("#"+productId+"_tr_"+indexCount+" td")[8]).html(
			"<div class='col-xs-5 no-padding'>"+
			"	<select class='form-control input-sm' id='"+productId+"_start_"+indexCount+"' "+productId+"_start_end name='extenderList["+indexCount+"].cycleStart' takingCycle-list onchange='changeTakingCycle(this);' onclick='recordOldWeekValue(this.value);'>"+weekOptions+"</select>"+
			"</div>" +  
			"<div class='col-xs-2 no-padding' style='margin-top: 5px;'>至</div>"+
			"<div class='col-xs-5 no-padding'>"+
			"	<select class='form-control input-sm' id='"+productId+"_end_"+indexCount+"' "+productId+"_start_end name='extenderList["+indexCount+"].cycleEnd' takingCycle-list onchange='changeTakingCycle(this);' onclick='recordOldWeekValue(this.value);'>"+weekOptions+"</select>"+
			"</div>"
		);
	} else{
		if($("#nutrientDoseListTable select["+productId+"_pare]").length > 0){
			layer.msg("备孕期已存在该补充剂，不能重复添加");
			$(obj).val("孕期");
			return false;
		}
		$($("#"+productId+"_tr_"+indexCount+" td")[8]).html(
			"<div class='col-xs-12 no-padding'>"+
			"	<select class='form-control input-sm' id='"+productId+"_takingCycle_"+indexCount+"' "+productId+"_pare name='extenderList["+indexCount+"].takingCycle' takingCycle-list>"+monthOptions+"</select>"+
			"</div>"
		);
	}
}

/**
 * 记住操作前的数据值
 * @param value
 */
function recordOldWeekValue(value){
	oldWeekValue = value;
}

/**
 * 判断周期是否已存在
 * @param obj
 * @returns {Boolean}
 */
function changeTakingCycle(obj){
	var $groupSelect = $(obj).parent().parent("td").children("div").children("select");
	if(!_.isEmpty($groupSelect[0].value) && !_.isEmpty($groupSelect[1].value)){
		var productId = obj.id.split("_")[0];
		var start = parseInt($groupSelect[0].value);
		var end = parseInt($groupSelect[1].value);
		if(start > end){
			$(obj).val(oldWeekValue);
			layer.msg("开始周不能大于结束周");
			return false;
		}
		
		var $selectObj = $("#nutrientDoseListTable select["+productId+"_start_end]").not($groupSelect);
		if($selectObj.length > 0){
			var group = parseInt($selectObj.length)/2;
			for(var i=1;i<=group;i++){
				var num1 = $selectObj[2*(i-1)].value;
				var num2 = $selectObj[2*i-1].value;
				if(!(start > num2 || end < num1)){
					$(obj).val(oldWeekValue);
					layer.msg("同一种补充剂，服用周期不能重叠");
					return false;
				}
			}
		}
	}
}

/**
 * 被评价商品列表删除行方法
 * @param thisObj
 */
function removeProduct2Order(thisObj){
	$(thisObj).parents("tr").remove();
	if($("#nutrientDoseListTable tr").length == 1){
		$("#nutrientDoseListTable").append(
			'<tr id="noRecordTr">'+
			'	<td colspan="10" class="text-center"><h4>没有找到记录！</h4></td>'+
			'</tr>'
		);
	}
}

/**
 * 剂量评估方法
 */
function getExtenderData(form){
	var conSubmit = false;
	// 校验单次剂量
	$($("input[productDosage-list]")).each(function(index, inputObj){
		if(common.isEmpty($(inputObj).val())){
			var tdObj = $(inputObj).parent().parent("tr").children("td")[1];
       	    layer.alert("产品：("+$(tdObj).text()+")<br><font color='red'>单次剂量未输入</font>");
       	    conSubmit = true;
       	    return false;
		}
		if($(inputObj).val() <= 0 || $(inputObj).val() >= 100){
			var tdObj = $(inputObj).parent().parent("tr").children("td")[1];
			layer.alert("产品：("+$(tdObj).text()+")<br><font color='red'>单次剂量必须在0~100之间，且不包含0和100</font>");
			conSubmit = true;
			return false;
		}
	});
	// 校验频次
	$($("select[productFrequency-list]")).each(function(index, selectObj){
		if(_.isEmpty($(selectObj).val())){
			var tdObj = $(selectObj).parent().parent("tr").children("td")[1];
			layer.alert("产品：("+$(tdObj).text()+")<br><font color='red'>频次不能为空</font>");
			conSubmit = true;
			return false;
		}
	});
	// 校验孕期
	$($("select[pregnancy-list]")).each(function(index, selectObj){
		if(_.isEmpty($(selectObj).val())){
			var tdObj = $(selectObj).parent().parent("tr").children("td")[1];
			layer.alert("产品：("+$(tdObj).text()+")<br><font color='red'>孕期不能为空</font>");
			conSubmit = true;
			return false;
		}
	});
	// 校验服用周期
	$($("select[takingCycle-list]")).each(function(index, selectObj){
		var tdObj = $(selectObj).parent().parent().parent("tr").children("td");
		if(_.isEmpty($(selectObj).val())){
			layer.alert("产品：("+$(tdObj[1]).text()+")<br><font color='red'>服用周期不能为空</font>");
			conSubmit = true;
			return false;
		} else{
			if($(tdObj[7]).children("select").val() == "孕期"){
				var divObj = $(selectObj).parent().parent("td").children("div");
				var start = $(divObj[0]).children("select").val();
				var end = $(divObj[2]).children("select").val();
				if(parseInt(start) > parseInt(end)){
					layer.alert("产品：("+$(tdObj[1]).text()+")<br><font color='red'>服用周期开始周不能大于结束周</font>");
					conSubmit = true;
					return false;
				}
			}
		}
	});
	
	if(conSubmit){
		return false;
	}
	
	finalTakingCycleOptions = [];
	finalTakingCycle = [];
	$(form).ajaxPost(dataType.json,function(data){
		if(!_.isEmpty(data.value) && !_.isEmpty(data.value.resultMap)){
			var trHtml0 = "<tr class='active'><th style='min-width: 140px;' class='text-center'>孕期阶段</th>";
			var trHtml1 = "<tr><th class='text-center'>服用周期</th>";
			var trHtml2 = "<tr><th class='text-center'>营养制剂</th>";
			var bodyMap = new Map();
			var nameArray = [];
			var recordCode = [];
			var cycleName = "";
			$.each(data.value.resultMap, function(key, list){// 遍历 Map<String, List<PregPlanExtenderAssessPojo>>
				if(!_.isEmpty(list)){
					trHtml0 += "<th class='text-center' colspan='"+list.length+"'>"+key.substring(1, key.length)+"</th>";
					$.each(list, function(index, value){// 遍历 List<PregPlanExtenderAssessPojo>
						// 获取补充剂列表
						$.each(value.extenderList, function(j, extender){
							nameArray.push(extender.indexName+" "+extender.productName);
						});
						cycleName = "";
						if(key == "0备孕期"){
							cycleName = value.takingCycle;
							if(cycleName.split("~")[0] == cycleName.split("~")[1]){
								trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+cycleName.split("~")[0]+" 月</th>";
								finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+cycleName.split("~")[0]+" 月</option>");// 记录周期
							} else{
								trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+cycleName+" 月</th>";
								finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+cycleName+" 月</option>");// 记录周期
							}
						} else{
							cycleName = value.takingCycle.substring(1, value.takingCycle.length);
							if(cycleName.split("~")[0] == cycleName.split("~")[1]){
								trHtml1 += "<th style='min-width: 170px;' class='text-center'>第 "+cycleName.split("~")[0]+" 周</th>";
								finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>第 "+cycleName.split("~")[0]+" 周</option>");// 记录周期
							} else{
								trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+cycleName+" 周</th>";
								finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+cycleName+" 周</option>");// 记录周期
							}
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
		
		// 修改状态
		opener.updateInspectStatus($("#inspectId").val(), data.value.inspectCode);
		
	}, false, false, URL.get('Customer.PLAN_EXTENDER_ANALYSIS'));
}

//************************************* 加载数据 *************************************
$().ready(function(){
	$("#searchText").val("");
	// 初始化数据
	initDdlHtmlFunc();
	supplementsTable = datatable.table(supplementsTableOpt);
	
	// 初始化已编辑的补充剂列表
	initExtenderListInfo();
	
	// 按钮点击事件
	$("button[name='extenderAssessButton']").off("click").on("click", function(){
		if(this.id == 'extenderPreView'){// 计量评估按钮
			getExtenderData($("#planProductForm"));
		} else if(this.id == "assessExtenderPdf"){// 生成PDF报告按钮
			getAssessExtenderPdf();
		} else if(this.id == "searchExtender"){// 补充剂库查询按钮
			datatable.search(supplementsTable, supplementsTableOpt);
		}
	});
});

//*****************************************自定义开始****************************************

/**
 * 选中全部，清空服用周期下拉列表
 */
function selectAll(){
	$("#pdf_taking_cyle").multiselect("deselectAll", false);
	$("#pdf_taking_cyle").multiselect("updateButtonText");
}

/**
 * 打印PDF报告
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
	var params = "id=" + $("#inspectId").val() + "&takingCycleList="+finalCycle.join(",");
	ajax.post(url, params, dataType.json, function(pdfData){
    	common.openWindow(PublicConstant.basePath +"/"+ pdfData.value);
	});
}