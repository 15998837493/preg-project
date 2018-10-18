
var inspectPayJSP = {};// 收费项目页面数据

// ===========================================【初始化主体一览页面】=========================================
// 配置dataTable
var inspectPayData;
var inspectPayRow;
var inspectPayTable;

var inspectPayQueryOption = {
	id : "inspectPayListTable",
	form : "inspectPayQueryForm",
	ajax : {url: URL.get("item.INSPECT_PAY_QUERY")},
	columns : [ {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<input type='radio' name='tableRadio' value='" + data.inspectId + "' />";
		}
	}, {
		"data" : "inspectName",
		"sClass" : "text-center"
	}, {
		"data" : "inspectSex",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("ITEMSEX", data);
		}
	}, {
		"data" : "inspectSort",
		"sClass" : "text-center"
	}, {
		"data" : "resultsSuggest",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("RESULTSSUGGEST", data);
		}
	}, ],
	rowClick : function(data, row) {
		inspectPayData = data;
		inspectPayRow = row;
	},
	condition : "inspectPayCondition",
	async : false,// 是否同步
	loading : false,// 是否启用遮罩层
};

//===========================================【维护操作】=========================================

/**
 * 保存收费项目
 */
var validator;
var inspectPaySaveOption = {
	rules : {
		inspectName : {
			maxlength : 100,
			required : true,
			remote : {
				url : URL.get("item.INSPECT_PAY_NAME_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					inspectName : function() {
						return $("#inspectName").val();
					},
					inspectOldName : function() {
						return $("#inspectOldName").val();
					},
					type : function() {
						return inspectPayJSP.operateType;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		reviewHints : {
			maxlength : 1000,
		},
		inspectPrice : {
			maxlength : 7,
			max : 100000,
			twoDigit : true
		},
		inspectSort : {
			maxlength : 11,
			max : 1000,
			intege1 : true
		}
	},
	messages : {
		inspectName : {
			remote : "该名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm('确定要执行【保存】操作？', {
			btn : [ '确定', '取消' ]// 按钮
		}, function() {
			$(form).ajaxPost(dataType.json, function(data) {
				if (inspectPayJSP.operateType == "add") {
					datatable.add(inspectPayTable, data.value);// 添加
					$("#editInspectPayModal").modal("hide");
				} else if (inspectPayJSP.operateType == "update") {
					datatable.update(inspectPayTable, data.value, inspectPayRow);// 修改
					$("#editInspectPayModal").modal("hide");
				}
				validator.resetForm();
			});
		});
	}
};

/**
 * 删除收费项目
 * @param inspectId
 */
function removeInspectPay(inspectId){
	layer.confirm('确定对选中内容执行【删除】操作？', function(index){
		var url = URL.get("item.INSPECT_PAY_REMOVE");
		var params = "inspectId=" + inspectId;
		ajax.post(url, params, dataType.json, function(data){
			datatable.remove(inspectPayTable, inspectPayRow);// 删除datatable中的数据
		});
	});
};

/**
 * 获取收费项目下的检验项目
 * @param inspectId
 * @returns
 */
function getInspecPayItems(inspectId){
	var url = URL.get("item.INSPECT_PAY_ITEMS_GET");
	var params = "inspectId=" + inspectId;
	ajax.post(url, params, dataType.json, function(data){
		if(!_.isEmpty(data.value)){
			$.each(data.value, function(index, item){
				addTdHtml(item);// 添加检验项目html
			});
		}
	}, false, false);
}

//===========================================【html操作】=========================================

/**
 * 添加Td内容
 * @param itemJson
 * @returns
 */
function addTdHtml(itemJson){
	$("#hospital_inspect_no_record").remove();
	if($("#hospital_inspect_"+itemJson.itemId).length == 0){
		$("#hospital_inspect").append(
			"<tr id='hospital_inspect_"+itemJson.itemId+"'>"+
			"	<td>"+
			"		<input type='hidden' name='itemIdList' value='"+itemJson.itemId+"'>"+
			"		<label class='label-checkbox'>"+itemJson.itemName+"</label>"+
			"		<a onclick='removeTdHtml(\"hospital_inspect_"+itemJson.itemId+"\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>"+	
			"	</td>"+
			"</tr>"
		);
	} else{
		layer.alert("该检验项目已添加！");
	}
}

/**
 * 删除Td内容
 * @param id
 * @returns
 */
function removeTdHtml(id){
	$("#"+id).remove();
}

//===========================================【自定义】=========================================

/**
 * 定义按钮事件
 */
function definedButtonClick(){
	$("button[name='inspectPayButton']").click(function(){
		// 设置搜索按钮
		if(this.id == "searchButton"){
			inspectPayTable = datatable.table(inspectPayQueryOption);// 刷新收费项目列表
		}
		// 设置添加按钮
		if(this.id == 'addButton'){
			inspectPayJSP.operateType = "add";// 设置操作类型
			$("#editInspectPayModal").modal("show");// 弹出编辑页面
		}
		// 设置编辑按钮
		if(this.id == 'updateButton' && datatable.isDatatableTR(inspectPayRow)){
			inspectPayJSP.operateType = "update";// 设置操作类型
			$("#inspectPayEditForm").jsonToForm(inspectPayData);// 初始化表单内容
			$("#inspectOldName").val($("#inspectName").val());// 设置旧数据
			getInspecPayItems(inspectPayData.inspectId);// 初始化该收费项目下的检验项目
			$("#editInspectPayModal").modal("show");// 弹出编辑页面
		}
		// 设置删除按钮
		if(this.id == 'removeButton' && datatable.isDatatableTR(inspectPayRow)){
			removeInspectPay(inspectPayData.inspectId);// 删除收费项目
		}
	});
}

$().ready(function() {
	// 添加验证
	validator =$("#inspectPayEditForm").validate(inspectPaySaveOption);
	common.requiredHint("inspectPayEditForm",inspectPaySaveOption);
	
	// 初始化下拉选表单
	common.initCodeSelect("RESULTSSUGGEST", "RESULTSSUGGEST","resultsSuggest","0");
	common.initCodeSelect("ITEMSEX", "ITEMSEX","inspectSex","A");
	
	// 添加检验项目自动补全
	common.autocompleteMethod("hospital_inspect_text", itemListData, function(data){
		addTdHtml(data.val);// 添加检验项目html
		$("#hospital_inspect_text").val("");// 清空
	});
	
	// 定义弹出modal关闭触发事件
	common.modal("editInspectPayModal", "hidden.bs.modal", function(){
		common.clearForm("inspectPayEditForm");// 清空表单
		$("#hospital_inspect").html(// 清空检验项目
			"<tr id='hospital_inspect_no_record'><td><label class='label-checkbox' style='color:gray;'>未配置检验项目！</label></td></tr>"
		);
	});
	
	// 加载dataTable
	inspectPayTable = datatable.table(inspectPayQueryOption);
	
	// 定义按钮事件
	definedButtonClick();
});
