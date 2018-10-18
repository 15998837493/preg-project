var nodataHtml = 
	 '<tr class="odd">'
		+ '<td class="dataTables_empty" colspan="4" valign="top">'
			+ '<h4>没有数据</h4>'
		+ '</td>'
	+'</tr>';
$().ready(function() {
		autocompleteDisease("diseaseName");
		nutritionMedicalTable = datatable.table(tableOptions);
		$("button[name='operateButton']").click(function() {
			var id = $("#id").val();
			if (this.id == 'configButton' && common.isChoose(id)) {//配置
				datatable.table(configTableOptions);
				$("#productDiseaseConfigModal").modal("show");
				autocomplete();
			}
			if (this.id == 'removeButton' && common.isChoose(id)) {//删除
				removeClick(id);
			}
			if (this.id == "searchButton") {//查询
				nutritionMedicalTable = datatable.table(tableOptions);
			}
		});
		common.modal("productDiseaseConfigModal", common.modalType.hide, function () {
			$('tr[id^="added"]').each(function () {
				remove($("#id").val(), $(this).attr("id").replace("added",""))
			});
			nutritionMedicalTable = datatable.table(tableOptions);
		});
	});

/**
 * 删除提交form请求
 */
function removeClick(id) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		var url = URL.get("ProductDisease.REMOVE_NUTRITION_MEDICAL");
		var params = "productId="+id;
		layer.loading();
		ajax.post(url, params, dataType.json, function(data) {
			datatable.table(tableOptions);
			
			checkedRow = null;
			checkedData = null;
			$("#id").val("");
		});
		layer.close(index);
	});
};

/**
 *  删除一条餐次剂量 
 */
function removeConfig(productId, productDiseaseId){
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		remove(productId, productDiseaseId);
		if($("#productDiseaseConfigTable tbody tr").length == 1){
			$(".dataTables_empty").parent().show();
		}else if($("#productDiseaseConfigTable tbody tr").length == 0){
			$("#productDiseaseConfigTable tbody").append(nodataHtml);
		}
			
	});
};

function remove(productId, productDiseaseId){
	var url = URL.get("ProductDisease.REMOVE_NUTRITION_MEDICAL");
	var params = "productId="+ productId + "&productDiseaseId="+productDiseaseId;
	ajax.post(url, params, dataType.json, function(data) {
		$("#remove"+productDiseaseId).parent().parent().remove();
		checkedRow = null;
		checkedData = null;
	}, null, false);
}

/**
 * 添加一条
 */
function addConfig(){
	if($('tr[id^="added"]').length > 0){
		layer.msg("请先填写");
		return;
	}
	var url = URL.get("ProductDisease.ADDORUPDATE_NUTRITION_MEDICAL");
	
	$("#productDiseaseId_saveForm").val("");
	$("#productId_saveForm").val($("#id").val());
	$("#diseaseId_saveForm").val("");
	$("#productDiseaseFrequency_saveForm").val("");
	
	var dataArray = [];
	var dataObject = {};
	dataObject.mealtime = "";
	dataObject.mealnum = "";
	dataArray.push(dataObject);
	$("#productDiseaseExecutionlist_saveForm").val(JSON.stringify(dataArray));
	
	$("#configSaveForm").ajaxPost("json",function(data) {
		if (data.result) {
			$(".dataTables_empty").parent().hide();
			var addedHtml = 
				'<tr id="added'+data.value+'" class="odd active">' +
				'    <td class="table-bordered table-hover text-center"><input type="hidden" id="diseaseId_'+data.value+'"/>' +
							'<input type="text" id="diseaseName'+data.value+'" class="form-control" placeholder="请输入适用人群"/></td>' +
				'    <td class="table-bordered table-hover text-center"><input id="productDiseaseFrequency'+data.value+'" type="hidden"/>' +
							'<input id="productDiseaseFrequency'+data.value+'" type="hidden" /><select id="productDiseaseFrequency_'+data.value+'" class="form-control" onchange="diseaseFrequencyChange(\'productDiseaseFrequency_'+data.value+'\')"></select></td>' +
				'    <td class="table-bordered table-hover text-center"><div id="dayFrequencyGroup'+data.value+'"></div></td>' +
				'    <td class="table-bordered table-hover text-center"><input type="hidden"/>' +
							'<a id="remove'+data.value+'" onclick="removeConfig(\''+$("#id").val()+'\',\''+data.value+'\')">删除<a/></td>' +
				'</tr>'
			$("#productDiseaseConfigTable tbody").append(addedHtml);
			autocompleteDisease("diseaseName"+data.value);
			autocompleteFrequency("productDiseaseFrequency_"+data.value);
			autocompleteDayFrequency("productDayFrequency_"+data.value);
		}else{
			layer.alert(data.message)
		}
	},false,false,null);
};


/**
 * 自动补全诊断项目
 */
function autocompleteDisease(id){
	
	$('#'+id).autocomplete(diseaseListData, {
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
			//给productUserDisease赋值
			if(this.id == "diseaseName"){
				selectOperation.addSelectOperation(data.val.diseaseId, data.val.diseaseName, "allDiseaseListTable", "productUserDisease");
				$("#diseaseName").val("");
			} else {
				var flag = false;//是否已存在该诊断
				$('input[id^="diseaseId_"]').each(function () {
					if($(this).val() == data.val.diseaseId){
						flag = true;
						return false;
					}
				});
				if(flag){
					layer.msg("已存在");
					$(this).val("");
					return;
				}
				$("#diseaseId_"+$(this).attr("id").replace("diseaseName","")).val(data.val.diseaseId);
				commitToSave($(this).attr("id").replace("diseaseName",""));
				
			}
	    });
}

/**
 * 频次字典
 */
function autocompleteFrequency(id){
	var selectedValue = $("#productDiseaseFrequency"+id.replace("productDiseaseFrequency_","")).val();
	common.initCodeSelect("PRODUCTFREQUENCY", "PRODUCTFREQUENCY",id,selectedValue);
}

/**
 * 餐次字典
 */
function autocompleteDayFrequency(id){
	var selectedValue = $("#productDayFrequency"+id.replace("productDayFrequency_","")).val();
	common.initCodeSelect("MEALSTYPE", "MEALSTYPE",	id,selectedValue);
}
/**
 * 配置页面每次加载完列表调用
 * 
 */
function autocomplete(){
	$('input[id^="diseaseName"]').each(function () {
		autocompleteDisease($(this).attr("id"));
	});
	
	$('select[id^="productDiseaseFrequency_"]').each(function () {
		autocompleteFrequency($(this).attr("id"));
	});
	
	$('select[id^="productDayFrequency_"]').each(function () {
		autocompleteDayFrequency($(this).attr("id"));
	});
	
	bindNumbers();
}

function bindNumbers(){
	$('input[id^="dayFrequency"]').each(function () {
		$(this).bindNumber();
	});
}
/**
 * 提交到后台保存 
 */
function commitToSave(productDiseaseId){
	$("#productDiseaseId_saveForm").val(productDiseaseId);
	$("#diseaseId_saveForm").val($("#diseaseId_"+productDiseaseId).val());
	$("#productDiseaseFrequency_saveForm").val($("#productDiseaseFrequency_"+productDiseaseId).val());

	var mealLength = $("#dayFrequencyGroup"+productDiseaseId).children('div').length;
	var mealArray = [];
	for(i = 0; i < mealLength; i++){
		var mealObject = {};
		mealObject.mealtime = $("#productDayFrequency_"+i+productDiseaseId).val();
		mealObject.mealnum = $("#dayFrequency"+i+productDiseaseId).val();
		mealArray.push(mealObject);
	}
	$("#productDiseaseExecutionlist_saveForm").val(JSON.stringify(mealArray));
	
	$("#configSaveForm").ajaxPost("json", function(data) {
		if (data.result) {
			$('#added'+data.value).attr("id",data.value)
		}
	}, false,false,null);
}

/**
 * 频次变化联动 
 */
function diseaseFrequencyChange(id){
	var frequency = $("#"+id).val();
	var dayFrequencyDiv = "";
	var productDiseaseId = id.substr(id.indexOf("_")+1);
	$("#productDiseaseFrequency"+productDiseaseId).val(frequency);
	var mealArray = [];
	if(common.isEmpty(frequency)){
		return;
	}
	var times = 1;
	if(frequency == 'biw' || frequency == 'bid'){//2
		times = 2;
	}else if(frequency == 'tiw' || frequency == 'tid'){
		times = 3;
	}else if(frequency == 'qid'){
		times = 4
	}
	$("#dayFrequencyGroup"+productDiseaseId).empty();
	for(i = 0; i < times; i++){
		dayFrequencyDiv = '<div id="rowProductDayFrequency'+productDiseaseId+'" class="form-inline">'
							     + '<input id="productDayFrequency'+i+productDiseaseId+'" type="hidden" /><select id="productDayFrequency_'+i+productDiseaseId+'" class="form-control input-sm" onchange="executionListChange(\''+productDiseaseId+'\')"></select>&nbsp;&nbsp;'
							     + '<div class="input-group input-group-sm">'
								     + '<input id="dayFrequency'+i+productDiseaseId+'" type="text" class="form-control text-center" style="width:50px;" maxlength="6"  onblur="executionListChange(\''+productDiseaseId+'\')"/>'
								 	 + '<div class="input-group-addon">'+productUnit+'</div>'
						         +' </div>'
					    + '</div>';
		var mealObject = {};
		mealObject.mealtime = '';
		mealObject.mealnum = '';
		mealArray.push(mealObject);
		$("#dayFrequencyGroup"+productDiseaseId).append(dayFrequencyDiv);
		autocompleteDayFrequency("productDayFrequency_"+i+productDiseaseId);
		bindNumbers();
	}
	
	$("#executionList"+productDiseaseId).val(JSON.stringify(mealArray));
	$("#productDiseaseExecutionlist_saveForm").val(JSON.stringify(mealArray));
	
	commitToSave(productDiseaseId);
}

/**
 * 餐次剂量一列变化联动 
 */
function executionListChange(id){
	commitToSave(id);
}


