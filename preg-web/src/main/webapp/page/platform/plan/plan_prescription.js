
var add_count=0;// 添加营养处方专用计数，计数只加不减（不能用index，否则删除之后再添加会报错）
var codeMap=new Map();// 代码表MAP

$().ready(function(){	
	//初始化代码表
	codeMap.set("productAttribute","PRODUCT_ATTRIBUTE");
	codeMap.set("productFrequency","PRODUCTFREQUENCY");
	codeMap.set("productIsOfficina","TRUEORFALSE");
	codeMap.set("productIsAssess","TRUEORFALSE");
	codeMap.set("productIsEnergy","TRUEORFALSE");
	// 初始化
	initDiagnosisExtender(diagnosisExtenderList);
	//添加备注内容
	$("#extenderHint").val(diagnosis.diagnosisExtenderDesc);
	// 给补充添加模糊提示功能；
	common.autocompleteMethod("productAddInput", productListData, function(data){
		// 如果是指标可以直接添加  如果是检查项目或者模板需要查询他们的指标明细
		data.value.status = "1";// 设置状态为“新增”
		addExtenderList(data.value);
		// 清空输入的内容
		$("#productAddInput").val("");
	});
	// 初始化诊断项目；
	initDiagnosisDisease();
	// 编辑补充剂信息
	$("#extenderHint").off("change").on("change", function(){
		var url = URL.get("Platform.DIAGNOSIS_UPDATE");
		var params = "diagnosisId=" + $("#diagnosisId").val() + "&diagnosisExtenderDesc=" + $("#extenderHint").val();
		ajax.post(url, params, dataType.json, null, false, false);
	});
});

// =================================================【页面初始化内容】=================================================

/**
 * 初始化添加补充剂列表的方法
 * @param diagnosisExtenderList
 */
function initDiagnosisExtender(diagnosisExtenderList){
	$("#order_tbody").empty();// 清空列表
	if(!common.isEmpty(diagnosisExtenderList)){
		$(diagnosisExtenderList).each(function(index, product){
			addExtenderList(product);
		});
	}
	if($("#order_tbody").children().length == 0){
		$("#order_tbody").append("<tr><td class='text-center' colspan='100'><h4 id='zero' style='text-align: center;'>没有找到记录！</h4></td></tr>");
	}
}

/**
 * 初始化诊断项目
 */
function initDiagnosisDisease(){
	if(!common.isEmpty(diagnosisDisease)){
		var html = "";
		$.each(diagnosisDisease, function(index, value){
			//系统定义的排在前面，自定义的排在后面
			if(common.isEmpty(value.diseaseId)){
				var h = "<button type='button' style='color:#888888'  class='btn-sm' disabled>"+value.diseaseName+"</button>";
				html = html + h;
			}else{
				var h = "<button type='button' class='btn-sm' onclick='diseaseAddProduct(\""+value.diseaseId+"\")'>"+value.diseaseName+"</button>";
				html = h + html;
			}
		});
		$("#diseaseSpan").append(html);
	}
}

/**
 * 通过诊断项目code获取诊断项目关联的产品
 * @param diseaseCode
 */
function diseaseAddProduct(diseaseId) {
	if(common.isEmpty(diseaseId)){
		layer.alert("自定义诊断项目无关联商品");
	}else{
		var url = URL.get("item.QUERY_PRESCRIPTION");
		var params = "diseaseId="+diseaseId;
		ajax.post(url, params, dataType.json, function(data){
			if(common.isEmpty(data.data)){
				layer.alert("该诊断项目没有关联的商品");
			}else{
				$.each(data.data, function(index, product){
					product.status = "1";// 设置状态为“新增”
					addExtenderList(product);
				});
				layer.msg("商品添加成功");
			}
		}, false, false);
	}
}

//=================================================【页面Html操作】=================================================

/**
 * 统一添加补充剂的方法
 * @param product
 * @returns {Boolean}
 */
function addExtenderList(product){
	$("#zero").remove();
	// 校验重复
	if($("#order_tr_"+product.productId).length > 0){
		layer.alert("该商品已存在！");
		return false;
	}
	
	// 获取产品元素列表
	var qitArray = [];
	if(!_.isEmpty(diseEleAllMap)){
		ajax.post(URL.get("Master.PRODUCT_ELEMENT_QUERY"), "productId="+product.productId, dataType.json, function(data){
			$.each(data.value, function(index, proElement){
				var element = diseEleAllMap[proElement.nutrientId];
				if(!_.isEmpty(element) && !_.isEmpty(proElement.productNutrientDosage)){
					qitArray.push(proElement.nutrientName);
				}
			});
		}, false, false);
	}
	var qitInfo = qitArray.join("、");
	if(!_.isEmpty(qitInfo)){
		qitInfo += " 超标";
	}
	// 添加补充剂html
	var productDosage = common.isEmpty(product.productDosage)?"1":product.productDosage;
	
	// 对于商品名的处理
	var productGoodsName = common.isEmpty(product.productGoodsName)?"":product.productGoodsName;
	var productCommonName = common.isEmpty(product.productCommonName)?"":product.productCommonName;
	var productPicPath = PublicConstant.basePath+"/resource/upload/product/ext/"+product.productPic;
	$("#order_tbody").prepend(
		"<tr id='order_tr_" + product.productId + "'>" +
		"	<td class='text-center' style='width: 5%;'>" +
		"		<img class='img-thumbnail img-responsive' style='width:40px;height:40px;' onclick='common.largepic(this.src)' onerror='common.errorImg(this)' src='"+productPicPath+"'/>" + 
		"	</td>" +
		"	<td style='width: 32%;'>"+ nameDetailOnMouseover(product.productId + "_" + add_count, product.productName + " " + productGoodsName + " " + productCommonName) + "</td>" +
		"	<td class='text-center' style='width: 10%;'>" + 
		"		<input type='hidden' id='" + product.productId + "' name='productId' value='" + product.productId + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productId' value='" + product.productId + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productPic' value='" + product.productPic + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productCode' value='" + product.productCode + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productName' value='" + product.productName + " " + productGoodsName + " " + productCommonName + "'/>" +
		"		<input type='hidden' name='extenderList["+add_count+"].productUnit' value='" + product.productUnit + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productDosageDesc' value='" + product.productDosageDesc + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productUsageMethod' value='" + product.productUsageMethod + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productTreatment' value='" + (product.productTreatment || "") + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productPackageUnit' value='" + product.productPackageUnit + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productStandard' value='" + product.productStandard + "'/>" + 
		"		<input type='hidden' name='extenderList["+add_count+"].productDays' value='" + (product.productDays || "") + "'/>" + 
		"       <div class='input-group'>" +
		"		    <input type='text' style='text-align:right' class='form-control input-sm text-center' id='"+product.productId+"_productDosage' name='extenderList["+add_count+"].productDosage' value='" + productDosage + "' productDosage-list input-required />" +  
		"           <div class='input-group-addon'>" + product.productUnit + "</div>" +
		"       </div>"+
		"	</td>" +
		"	<td class='text-center' style='width: 8%;'>" + product.productDosageDesc + "</td>" +
		"	<td class='text-center' style='width: 5%;'>" + product.productUsageMethod + "</td>" +
		"	<td class='text-center' style='width: 13%;'>" + 
		"		<select class='form-control input-sm' id='"+product.productId+"_productFrequency' name='extenderList["+add_count+"].productFrequency' productFrequency-list></select>" +  
		"	</td>" +
		"	<td class='text-left' style='width: 5%;'>" + (product.productTreatment || "") + "</td>" +
		"	<td class='text-left' style='width: 15%; color: red;'>" + qitInfo + "</td>" +
		"	<td class='text-center' style='width: 7%;'>" +
		"		" + getButtonHtml(product.status, product.productId) +
		"	</td>"+
		"</tr>"
	);
	common.initCodeSelect("PRODUCTFREQUENCY", "PRODUCTFREQUENCY", product.productId+"_productFrequency", product.productFrequency);
	
	// 定义校验数值
	$("#order_tbody input[input-required]").off("change").on("change", function(){
		this.value = common.checkInputNumber("reg8", this.value, 2);
	});
	
	// 新增的标记为红色
	setProductColor(product.productId, product.status);
	
	// 专用计数器加1
	add_count++;
	
	return true;
}

/**
 * 商品名称浮窗显示
 * @param code
 * @param content
 * @returns {String}
 */
function nameDetailOnMouseover(code, content){
	return "<a id='mark_"+code+"'" + 
	       "   data-toggle='tooltip'" + 
	       "   data-placement='top'" +
	       "   onclick='showProductInfo(\""+ code.split("_")[0] +"\");'" +
	       "   title='"+content+"'>" + content + "</a>";
}

/**
 * 点击商品名称查看明细信息
 * @param productId
 */
function showProductInfo(productId){
	var product = productAllMap[productId];
	if(!common.isEmpty(product)){
		$.each(product, function(key, val) {
			if(common.isEmpty(val)){
				val="无";
			}
			if(codeMap.get(key)){
				val=CODE.transCode(codeMap.get(key),val);
			}
			$("#product_"+key).html(val);
		});	
	}
	$("#orderProductModel").modal('show');
}

/**
 * 设置按钮内容
 * @param status
 * @param productId
 * @returns {String}
 */
function getButtonHtml(status, productId){
	// status 1=新增，2=继服，3=停用
	var buttonHtml = "";
	if(status == "1"){
		buttonHtml = 
			"<input type='hidden' name='extenderList["+add_count+"].status' value='1'/>" + 
			"<a onclick='removeOrderProduct(\"" + productId + "\");'><i class='fa fa-minus'></i> 删除</a>";
	} else if(status == "3"){
		buttonHtml = 
			"<select class='form-control input-sm' id='"+productId+"_status' name='extenderList["+add_count+"].status' onchange='setProductColor(\""+productId+"\", this.value);'>" +
			"	<option value='2'>继服</option>" +
			"	<option value='3' selected>停用</option>" +
			"</select>";
	} else{
		buttonHtml = 
			"<select class='form-control input-sm' id='"+productId+"_status' name='extenderList["+add_count+"].status' onchange='setProductColor(\""+productId+"\", this.value);'>" +
			"	<option value='2' selected>继服</option>" +
			"	<option value='3'>停用</option>" +
			"</select>";
	}
	return buttonHtml;
}

/**
 * 设置字体颜色：新增=红色，继服=蓝色，停用=灰色
 * @param productId
 * @param status
 */
function setProductColor(productId, status){
	var color = "#337ab7";
	if(status == "1"){
		color = "red";
	} else if(status == "3"){
		color = "gray";
	}
	$("#order_tr_"+productId).children().css("color", color).children("a").css("color", color);
}

/**
 * 统一移除补充剂的方法
 * @param productId
 */
function removeOrderProduct(productId){
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		$("#order_tr_"+productId).remove();
		if($("#order_tbody").children().length == 0){
			$("#order_tbody").append("<tr><td class='text-center' colspan='100'><h4 id='zero' style='text-align: center;'>没有找到记录！</h4></td></tr>");
		}
		// 保存营养制剂处方
		saveOrderDetailList();
		layer.close(index);
	});
}

/**
 * 保存营养制剂处方
 */
function saveOrderDetailList(){
	updateDiagnosisStatus();
	$("#planProductForm").ajaxPost(dataType.json, null, false, false, URL.get('Platform.DIAGNOSIS_PRESCRIPTION_SAVE'));
}

//=================================================【剂量评估操作】=================================================

/**
 * 处方模板按钮方法
 */
function redirectTemlate() {
	common.openWindow(PublicConstant.basePath + "/page/platform/plan/plan_prescription_window.jsp");
}

/**
 * 剂量评估按钮的方法
 */
function getExtenderElement(){
	if($("#order_tbody tr").length == 0){
		layer.alert("请添加补充剂");
		return false;
	}
	//提交补充剂表单数据
	var $v =$("#planProductForm") ; //jQuery对象
	var form = $v[0]; //DOM对象
	getExtenderData(form);
}

/**
 * 剂量评估方法
 * @param form
 */
function getExtenderData(form){
	var conSubmit = false;
	$($("input[productDosage-list]")).each(function(index, inputObj){
		if(common.isEmpty($(inputObj).val())){
			var tdObj = $(inputObj).parents("tr").children("td")[1];
       	    layer.alert("产品:("+$(tdObj).text()+")<br>单次剂量未输入" );
       	 	conSubmit = true;
       	    return false;
		}
	});
	$($("select[productFrequency-list]")).each(function(index, inputObj){
		if(common.isEmpty($(inputObj).val())){
			var tdObj = $(inputObj).parents("tr").children("td")[1];
       	    layer.alert("产品:("+$(tdObj).text()+")<br>频次未选择" );
       	 	conSubmit = true;
       	    return false;
		}
	});
	if(conSubmit){
		return;
	}
	$(form).attr('action',URL.get('Customer.PLAN_GET_EXTENDERANALYSISDATA'));
	$(form).ajaxPost(dataType.json,function(data){
		var diagnosisInfo = data.value.diagnosis;
		//设置患者基础信息
		var diagnosisInfoTrs = $("#diagnosisInfoTable td");
		$(diagnosisInfoTrs[1]).html(data.value.customer.custPatientId);
		$(diagnosisInfoTrs[3]).html(diagnosisInfo.diagnosisCustName);
		$(diagnosisInfoTrs[5]).html(diagnosisInfo.diagnosisCustAge);
		$(diagnosisInfoTrs[7]).html(data.value.customer.custWeight);
		$(diagnosisInfoTrs[9]).html(data.value.customer.custHeight);
		$(diagnosisInfoTrs[11]).html(data.value.customer.custBmi);
		$(diagnosisInfoTrs[13]).html(pregnancy.gestationalWeeksSupHtml(diagnosisInfo.diagnosisGestationalWeeks));
		$(diagnosisInfoTrs[15]).html(data.value.archives.pregnancyDueDate);
		//添加被评估的商品列表
		$("#extenderTableBody").html("");
		$(data.value.extenderList).each(function(index, product){
			$("#extenderTableBody").append(
					"<tr>" +
					"	<td class='text-center'>"+product.indexName+"</td>" +
					"	<td>"+product.productName+"</td>" +
					"	<td>"+product.productDosage + product.productUnit +"</td>" +
					"	<td>" + (product.productDosageDesc || "")  +"</td>" +
					"	<td>"+product.productUsageMethod+"</td>" +
					"	<td>"+CODE.transCode("PRODUCTFREQUENCY",product.productFrequency)+"</td>" +
					"</tr>"		
			);
		}, false, false);
		if(common.isEmpty(data.value)){
			$("#elementTable").html("<tr><td class='text-center' colspan='10'><h4>没有找到记录！</h4></td></tr>");
		}else{
			//添加评估结果列表
			$("#elementTable").html("");
			var aimMap = data.value.elementMap; 
			for(var key in aimMap){
				var detail = aimMap[key];
				var element = detail.element;
				var diet = detail.dietElement || "——";
				var extender = detail.extenderElement || "——";
				var RNI = detail.elementRNI;
				var AI = detail.elementAI;
				var UL = detail.elementUL;
				if(element.nutrientEvalTwo=="1"){
					$("#elementTable").append(
						"<tr>" +
						"	<td>"+element.nutrientName+"（"+element.nutrientUnitName+"）</td>" +
						"	<td class='text-left'>"+diet+"</td>" +
						"	<td class='text-left'>"+extender+"</td>" +
						"	<td class='text-left'>"+RNI+"</td>" +
						"	<td class='text-left'>"+AI.replace("↓", "<font color='blue'>↓</font>").replace("↑", "<font color='red'>↑</font>")+"</td>" +
						"	<td class='text-left'>"+UL+"</td>" +
						"</tr>"
					);
				}
			}
		}
		$("#assessModal").modal("show");
	});
}
