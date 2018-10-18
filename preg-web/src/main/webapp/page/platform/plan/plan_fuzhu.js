//用于检验是否有重复的收费项目
var checkInspectItemIds=[];
//用于检验组合类型的医院收费项目中是否有该单一收费项目
var checkInspectItemCodes=[];
/**
 * 查找数组中指定元素位置
 */
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
	if (this[i] == val) return i;
	}
	return -1;
};
/**
 * 删除数组中指定元素
 */
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
		if (index > -1) {
		this.splice(index, 1);
	}
};
/**
 * 添加辅助检查项目套餐验证
 */
var intakeTemplateOptions = {
		rules: {
			templetName: {
				maxlength: 20,
				required:true,
				remote : {
					url : URL.get("item.TEMPLETNAME_VALID"),// 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据，默认已传递应用此规则的表单项
						templetName : function() {
							return $("#templetName").val();;
						},
						random : function() {
							return Math.random();
						}
					}
				}
			},
			inspectNames: {
				required:true
			}
		},
		messages : {
			templetName : {
				remote : "名称已经存在，请重新输入"
			}
		},	
		errorPlacement:function(error, element) {
			element = element.parent();
			common.showmassage(error, element);
		},
		success: $.noop,
		submitHandler: function(form) {
			layer.confirm('确定要执行【保存】操作？', {
				  btn: ['确定','取消'] //按钮
				},
			function(){
				$(form).ajaxPost(dataType.json,function(data){
					var url = URL.get("Platform.QUERY_HOSPITAL");
					var params ="";
					ajax.post(url, params, dataType.json, function(result){
						refurbishAuxiliary(result.data);
						$("#templatModal").modal("hide");
					},false,false);
				});
			});
		}
	};
/**
 * 页面初始化方法
 */
$(function () {
	//添加验证
	$("#editItemForm").validate(intakeTemplateOptions);
	common.requiredHint("editItemForm",intakeTemplateOptions);
	//初始化
	initAuxiliary();
	initHospitals();
	initQuotaItems();
	initSingle();
	//自动补全
	autocompleteHosMethod();
	// 初始化诊断项目；
	initDiagnosisDisease();
	//批量删除辅助检测项目
	removeAuxiliaryAll();
});

/**
 * 辅助检测项目一键全选
 * @param obj
 */
function checkInspectItem(obj){
	var check=$(obj).prop("checked");
	var $choose = $("[name='inspectItem']");
	$choose.each(function(){
		$(this).prop("checked",check);
	});
}
/**
 * 批量删除辅助检查项目
 */
function removeAuxiliaryAll(){
	$("#removeInspectItem").click(function(){
		var itemTr = $("#disease_quota_item").length;
		var checkNum = $("[name='inspectItem']:checked").length;
		if(itemTr == 0) {
			if(checkNum > 0) {
				layer.confirm('是否确定删除辅助检测项目？', {
					btn: ['确定','取消'] //按钮
				}, function(){
					var inspectId=[];
					var ids=[];
				    $.each($("[name='inspectItem']:checked"),function(){
				    	$(this).parent().parent().remove();
				    	inspectId.push($(this).val());
				    	checkInspectItemIds.remove($(this).val());
				    	ids.push(this.id);
				    });
				    toRemoveAuxiliary(ids,inspectId);
					$("#inspectItems").prop("checked",false);
				});					
			}else {
				layer.msg("请勾选想要删除的辅助检测项目！");
			}		
		}
	});
}

/**
 * 删除辅助检查项目接口
 * @param ids
 * @param inspectId
 */
function toRemoveAuxiliary(ids,inspectId){
	var params = "ids="+ids;
	var url = URL.get("Platform.REMOVE_AUXILIARY");
	ajax.post(url, params, dataType.json, function(data){
		if(data.result){
			removeAuxiliaryCallBack(inspectId);
		}else{
			layer.msg("删除辅助检测项目失败！");
		}
		if(checkInspectItemIds.length == 0){
			$("#diagnosis_disease_tbody").append(
					"<tr id='disease_quota_item'>"+
					"   <td  class='text-center'><h4>没有找到记录！</h4></td>"+
					"</tr>");
		}
	});
}

/**
 * 删除辅助检测项目接口回调
 * @param inspectId
 */
function removeAuxiliaryCallBack(inspectId){
	var hospital = getInspectById(inspectId);
	if(hospital.inspectCategory == '1'){
		checkInspectItemIds.remove(hospital.inspectId);
		var codes = queryRelationDeail(hospital.inspectId);
		var inspectItemCodes = codes.split(",");
		for(var i=0;i<inspectItemCodes.length;i++){
			checkInspectItemCodes.remove(inspectItemCodes[i]);
		}
	}else if(hospital.inspectCategory == '0'){
		checkInspectItemIds.remove(hospital.inspectId);
	}
}
/**
 * 初始化辅助检查项目
 */
function initQuotaItems(){
	if(quotaItems.length > 0){
		$(quotaItems).each(function(index,quotaItem){
			$("#diagnosis_disease_tbody").append(
				"<tr id="+quotaItem.inspectItemId+">"+
				"    <td class='text-center col-xs-2'><input type='checkbox' name='inspectItem' value='"+quotaItem.inspectItemId+"' id='"+quotaItem.id+"'/></td>"+
				"   <td class='text-left col-xs-10'>"+quotaItem.inspectItemName+"</td>"+
				"</tr>"
		);
		checkInspectItemIds.push(quotaItem.inspectItemId);
/*		var codes = queryRelationDeail(quotaItem.inspectItemId);
		var hospital = getInspectById(quotaItem.inspectItemId);
		var inspectItemCodes = codes.split(",");
		checkInspectItemCodes = checkInspectItemCodes.concat(inspectItemCodes);*/
	});
	}else{
		$("#diagnosis_disease_tbody").append(
				"<tr id='disease_quota_item'>"+
				"   <td  class='text-center'><h4>没有找到记录！</h4></td>"+
				"</tr>");
	}
}

/**
 * 初始化辅助检查项目套餐
 * "   <td style='width: 65%;' id='"+auxiliary.inspectIds+"' class='text-center' onclick='checkAuxiliaryRow(this);'>"+auxiliary.templetName+"</td>"+
 */
function initAuxiliary(){
	if(auxiliaryCheck.length>0){
		$(auxiliaryCheck).each(function(index,auxiliary){
			$("#auxiliary_tbody").append(
					"<tr id='"+auxiliary.templetId+"tr"+"'>"+
					"   <td style='width: 65%;' class='text-left' id='"+auxiliary.inspectIds+"' onclick='checkAuxiliaryRow(this);' ondblclick='addAuxiliary(this);' >"+auxiliary.templetName+"</td>"+
					"   <td class='text-center'><a id='"+auxiliary.inspectIds+"' onclick='addAuxiliary(this);'>添加</a>&nbsp;&nbsp;<a id='"+auxiliary.templetId+"' onclick='removeTemplet(this);'>删除</a> </td>"+
					"</tr>");
		});
	}else{
		$("#auxiliary_tbody").append(
				"<tr id='auxiliary_tbody_templet'>"+
				"   <td  class='text-center'><h4>没有找到记录！</h4></td>"+
				"</tr>");
	}
}

/**
 * 初始化医院收费项目
 */
function initHospitals(){
	$(hospitals).each(function(index,hospital){
		$("#hospital_tbody").append(
				"<tr id="+hospital.inspectId+" >"+
				"   <td style='width: 65%;' class='text-left' id='"+hospital.inspectId+"' onclick='checkHospitalRow(this);'>"+hospital.inspectName+"</td>"+
				"   <td class='text-center' id='"+hospital.inspectId+"' onclick='addHospital(this);'><a>添加</a></td>"+
				"</tr>");
	});
}

/**
 * 初始化单一辅助检查项目
 */
function initSingle(){
	$("#children_hospital_tbody").append(
			"<tr>"+
			"   <td  class='text-center'><h4>没有找到记录！</h4></td>"+
			"</tr>");
}

/**
 * 辅助检查套餐行点击事件
 */
function checkAuxiliaryRow(auxiliary){
		$("#hospital_tbody").html(" ");
		var inspectIds = auxiliary.id; 
		if(_.isEmpty(inspectIds)){
			$("#hospital_tbody").append(
					"<tr>"+
					"   <td  class='text-center'><h4>没有找到记录！</h4></td>"+
					"</tr>");
			return;
		}
		 $(inspectIds.split(",")).each(function(index, inspectCode) {
	 		 var hospital = getInspectByCode(inspectCode);
	 		 if(hospital.inspectName != null){
	 			$("#hospital_tbody").append(
						"<tr id="+hospital.inspectId+" >"+
						"   <td style='width: 65%;' class='text-left'  id='"+hospital.inspectId+"' onclick='checkHospitalRow(this);'>"+hospital.inspectName+"</td>"+
						"   <td class='text-center' id='"+hospital.inspectId+"' onclick='addHospital(this);'><a>添加</a></td>"+
						"</tr>");
	 		 }
		});
}
/** 异步检索组合检查项目关联的单一检查项目  */
function queryRelationDeail(inspectId){
	var inspectIds = ''; 
	var url = URL.get("item.QUERY_RELATION_DEAIL");
	var params = "inspectId="+inspectId;
	ajax.post(url,params, dataType.json, function(result){
		if(!_.isEmpty(result.data)){
			$(result.data).each(function(index, deail) {
				if(index == 0){
					inspectIds = deail.inspectRelationId;
				}else{
					inspectIds += "," + deail.inspectRelationId;
				}
			});
		}
	},false,false);
	return inspectIds;
}
/**
 * 添加检查项目html
 */
function addTdHtml(item) {
	$("#children_hospital_tbody").html("");
	var html = "";
	$.each(item, function(index, val){
		html += "<tr><td>"+val.itemName+"</td></tr>";
	});
	$("#children_hospital_tbody").html(html);
}
/**
 * 异步查询收费项目下的检查项目
 */
function queryInspectsItem(inspectId) {
	var url = URL.get("item.INSPECT_PAY_ITEMS_GET");
	var params = "inspectId=" + inspectId;
	ajax.post(url, params, dataType.json, function(data){
/*		if(!_.isEmpty(data.value)){
			$.each(data.value, function(index, item){
				addTdHtml(item);// 添加检验项目html
			});
		}*/
		if(!_.isEmpty(data.value)){
			addTdHtml(data.value);// 添加检验项目html
		}else {
			layer.msg("此收费项目下没有配置检查项目！");
		}
	}, false, false);
}
/**
 * 医院收费项目行点击事件
 */
function checkHospitalRow(masHospitals){
	queryInspectsItem(masHospitals.id);
/*	$("#children_hospital_tbody").html(" ");
	var inspectCodes =queryRelationDeail(masHospitals.id);
	if(!_.isEmpty(inspectCodes)){
		$(inspectCodes.split(",")).each(function(index,inspectId){
			var hospital = getInspectById(inspectId);
			if(hospital.inspectName != null){
				$("#children_hospital_tbody").append(
						"<tr id="+hospital.inspectId+" >"+
						"   <td style='width: 65%;' class='text-left' id='"+hospital.inspectRelationCodes+"'>"+hospital.inspectName+"</td>"+
						"   <td class='text-center' id='"+hospital.inspectId+"' onclick='addSingleHospital(this);'><a>添加</a></td>"+
						"</tr>");
			}
		});
	}else{
		$("#children_hospital_tbody").append(
				"<tr>"+
				"   <td  class='text-center'><h4>无检查项目！</h4></td>"+
				"</tr>");
	}*/
}

/**
 * 辅助检查项目套餐添加点击事件
 */
function addAuxiliary(data){
	var auxiliarys=data.id;
	if(auxiliarys!=null){
		$("#diagnosis_disease_norecord_tr").hide();
	}
	$(auxiliarys.split(",")).each(function(index,inspectCode){
		var hospital = getInspectByCode(inspectCode);
		InspectionRecord(hospital);
	});
	/*inputIndex();*/
}

/**
 * 医院收费项目添加事件
 */
function addHospital(data){
	var inspectId=data.id;
	var hospital = getInspectById(inspectId);
	InspectionRecord(hospital);
		/*inputIndex();*/
}

/**
 * 添加单一医院收费项目
 */
function addSingleHospital(data){
	var inspectId=data.id;
	var hospital = getInspectById(inspectId);
	if(checkInspectItemCodes.indexOf(hospital.inspectId)< 0 && checkInspectItemIds.indexOf(inspectId) < 0){
		saveQuotaItem(hospital);
	}else{
		layer.msg('不能重复添加！');
	}
	/*inputIndex();*/
}


/**
 * 根据inspect获取医院收费项目对象
 */
function getInspectById(inspectId){
	var inspect={};
	$(hospitals).each(function(index,hospital){
		if(inspectId == hospital.inspectId){
			inspect = hospital;
		}
	});
	return inspect;
};

/**
 * 根据inspectCode获取医院收费项目
 */
function getInspectByCode(inspectCode){
	var inspect={};
	$(hospitals).each(function(index,hospital){
		if(inspectCode == hospital.inspectCode){
			inspect = hospital;
		}
	});
	return inspect;
};

/**
 * #diagnosis_disease_tbody 表格增加一行
 */
function addRow(value){
	$("#disease_quota_item").remove();
//	var NO = document.getElementById('body').getElementsByTagName('tr').length;
	$("#diagnosis_disease_tbody").append(
			"<tr id="+value.inspectItemId+">"+
//			"   <td class='text-center'>"+NO+"</td>"+
			"    <td class='text-center col-xs-2'><input type='checkbox' name='inspectItem' value='"+value.inspectItemId+"' id='"+value.id+"'/></td>"+
			"   <td class='text-left col-xs-10'>"+value.inspectItemName+"</td>"+
			"</tr>"
	);
}

/**
 * #diagnosisAddDisease 输入框序号
 */
function inputIndex(){
	var rowsize = document.getElementById('diagnosis_disease_tbody').getElementsByTagName('tr').length;
	$("#index").html(rowsize);
}

/**
 * 保存到辅助检查项目表
 */
function saveQuotaItem(hospital){
	updateDiagnosisStatus();
	var url = URL.get("Platform.ADD_AUXILIARY");
	delete hospital["itemList"];
	delete hospital["itemIdList"];
	var params = "hospital="+JSON.stringify(hospital)+"&diagnosisId="+$("#diagnosisId").val();
	ajax.post(url, params, dataType.json, function(data){
			addRow(data.value);
			checkInspectItemIds.push(hospital.inspectId);
			//var codes = hospital.inspectRelationCodes;
/*			var codes = queryRelationDeail(hospital.inspectId);
			var inspectItemCodes = codes.split(",");
			checkInspectItemCodes = checkInspectItemCodes.concat(inspectItemCodes);*/
	},false,false);
}

/**
 * 添加辅助检查项目套餐
 */
function saveHospitalTemplate(){
	$("#templetName").val("");
	 var map = assignment();
	 $("#editItemForm").attr("action", URL.get("Platform.ADD_HOSPITAL"));
	 $(("#inspectIds")).val(map.codes);
	 $("#inspectNames").val(map.names);
	 $("#templatModal").modal("show");
}

/**
 * #inspectIds、#inspectNames 赋值
 */
function assignment(){
	var map={};
	 var inspectCodes="";
	 var inspectNames="";
	 $.each($("[name='inspectItem']:checked"),function(){
    	var hospital = getInspectById($(this).val());
    	inspectCodes += hospital.inspectCode+",";
		inspectNames += hospital.inspectName+",";
     });
	 
	 //将字符串末尾的“，”删掉
	 var codes = inspectCodes.substring(0,inspectCodes.lastIndexOf(','));
	 var names = inspectNames.substring(0,inspectNames.lastIndexOf(','));
	 map.codes=codes;
	 map.names=names;
	 return map;
}

/**
 * 刷新辅助项目套餐列表
 * "   <td style='width: 65%;' id='"+auxiliary.inspectIds+"' class='text-center' onclick='checkAuxiliaryRow(this);'>"+auxiliary.templetName+"</td>"+
 */
function refurbishAuxiliary(data){
	$("#auxiliary_tbody").html("");
	$(data).each(function(index,auxiliary){
		$("#auxiliary_tbody").append(
				"<tr id='"+auxiliary.templetId+"tr"+"'>"+
				"   <td style='width: 65%;' class='text-center'  id='"+auxiliary.inspectIds+"' onclick='checkAuxiliaryRow(this);' ondblclick='addAuxiliary(this);'>"+auxiliary.templetName+"</td>"+
				"   <td class='text-center' ><a id='"+auxiliary.inspectIds+"' onclick='addAuxiliary(this)'>添加</a>&nbsp;&nbsp;<a id='"+auxiliary.templetId+"' onclick='removeTemplet(this);'>删除</a></td>"+
				"</tr>");
	});
}



/**
 * 自动补全检查项目的方法
 */
function autocompleteHosMethod(){
	var dataList = [];
	$.each(hospitals, function(index, value){
		dataList.push({name:value.inspectName, val:value});
	});
		
    $("#diagnosisAddDisease").autocomplete(dataList, {
        width: $("#diagnosisAddDisease").css('width'),
        matchContains: true,
        autoFill: false,
        formatItem: function(row, i, max) {        
            return  row.name;
        },
        formatMatch: function(row, i, max) {
            return row.name + pinyin.getCamelChars(row.name);
        },
        formatResult: function(row) {
            return row.name;
        }
    }).result(function(event, data, formatted){
    	//如果是指标可以直接添加  如果是检查项目或者模板需要查询他们的指标明细
		InspectionRecord(data.val);
    	//清空输入的内容
    	$("#diagnosisAddDisease").val("");
    });
}

/**
 * 检查辅助检查项目记录
 */
function InspectionRecord(hospital){
/*	if(hospital.inspectCategory == '0'){
		if(checkInspectItemIds.indexOf(hospital.inspectId) < 0 && checkInspectItemCodes.indexOf(hospital.inspectId) < 0){
			saveQuotaItem(hospital);
		}else{
			layer.msg('已经过滤掉重复添加的检查项目！');
		}
	}else if(hospital.inspectCategory == '1'  ){
		if( checkInspectItemIds.indexOf(hospital.inspectId) < 0){
			saveQuotaItem(hospital);
		}else{
			layer.msg('已经过滤掉重复添加的检查项目！');
		}
	}*/
	if(!common.isEmpty(hospital.inspectId)) {
		if( checkInspectItemIds.indexOf(hospital.inspectId) < 0){
			saveQuotaItem(hospital);
		}else{
			layer.msg('已经过滤掉重复添加的检查项目！');
		}	
	}
}

/**
 * 删除辅助检查项目套餐
 */
function removeTemplet(data){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		btn: ['确定','取消'] //按钮
	},
	function(){
		var templetId = data.id;
		var params = "templetId="+templetId;
		var url = URL.get("item.TEMPLETNAME_REMOVE");
		ajax.post(url, params, dataType.json, function(data){
			if(data){
				$("#"+templetId+"tr").remove();
				var rowsize = document.getElementById('auxiliary_tbody').getElementsByTagName('tr').length;
				if(rowsize == 0){
					$("#auxiliary_tbody").append(
							"<tr id='auxiliary_tbody_templet'>"+
							"   <td class='text-center'><h4>没有找到记录！</h4></td>"+
							"</tr>");
				}
			}
		},true,false);
	});
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
				var h = "<button type='button'  class='btn-sm' onclick='diseaseAddInspect(\""+value.diseaseId+"\")'>"+value.diseaseName+"</button>";
				html = h + html;
			}
		});
		$("#diseaseSpanFuzhu").append(html);
	}
}

/**
 * 添加诊断项目
 */
function diseaseAddInspect(id){
	if(common.isEmpty(id)){
		layer.alert('自定义诊断项目无关联辅助检查项目');
		return;
	}
	var params = "diseaseId="+id;
	var url = URL.get("item.DISEASE_INSPECT_CONFIG");
	ajax.post(url, params, dataType.json, function(value){
		if(!_.isEmpty(value.data)){
			$.each(value.data,function(index,hospital){
				InspectionRecord(hospital);
			});	
		}else{
			layer.alert('未关联辅助检查项目！');
		}
		
	},false,false);
}