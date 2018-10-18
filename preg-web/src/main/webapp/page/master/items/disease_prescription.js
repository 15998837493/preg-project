//当前选中结点
var selectNode;
// 树对象
var treeObj;
// 配置
var zTree_1 = {
	beforeClick : function(treeId, treeNode) {
		selectNode = treeNode;
		treeObj.selectNode(treeNode);
		$("#id").val("");
		if (treeNode.level >= 1 && !treeNode.isParent) {
			$("#t_body").empty();
			$("button[name='diseaseItemPage']").attr("disabled", false);
			$("#queryForm input[name='diseaseId']").val(treeNode.value.diseaseId);
			$("#diseaseId").val(treeNode.value.diseaseId);
			$("#diseaseName").val(treeNode.value.diseaseName);
			// 查询推断指标
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
			// 加载对应的指标信息
			quotaTable = datatable.table(quotaOptions);
		} else {
			$("button[name='diseaseItemPage']").attr("disabled", true);
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
			$("#t_body").empty();
			$("#t_body").append("<tr><td colspan='100' class='text-center'><h4>请先选择疾病</h4></td></tr>");
			$("#t_foot").hide();
		}
		setDH();
	},
	onCollapse : function() {
		setDH();
	},
	onExpand : function() {
		setDH();
	}
};
var setting = {
	view : {
		selectedMulti : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onExpand: function(){setDH();},
		onCollapse: function(){setDH();},
		onClick: zTreeOnClick
	}
};

$(document).ready(function() {
	datatable.table(itemListDataOptions);
	//添加指标名称和修改指标类型检索符合条件的信息
	
	$("#itemName").on("keyup", function(){
		searchItem();
	});
	$("#itemTypeAlert").on("change", function(){
		searchItem();
	});
	//初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandAll(false);
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// ztree树拓展搜索框
	common.ztreeSearch(treeObj, zTree_1, "left_div");
	// 调节div高度
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	setNoSelect();
	// 按钮点击事件
	$("button[name='diseaseItemPage']").click(function(){
		var id = $("#id").val();
		if(this.id == 'addButton'){
			//添加修改
			$("#itemName").val("");
			datatable.table(itemListDataOptions);
			searchItem();
			$("#inspectItemModal").modal("show");
		}
		if(this.id == 'removeButton' && isChoose(id)){
			deleteDiseaseItem(id);
		}
		if(this.id == 'searchButton'){
			//quotaTable = datatable.table(quotaOptions);
			datatable.search(quotaTable, quotaOptions);
		}
	});
	layer.closeAll();
});

// *****************************************zTree开始****************************************

function filter(treeId, parentNode, responseData) {
	if (!responseData)
		return null;
	for ( var i = 0, l = responseData.length; i < l; i++) {
		responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
	}
	return responseData;
}

//单击节点会回调函数
function zTreeOnClick(event, treeId, treeNode) {
	if(treeNode.level >= 1 && !treeNode.isParent){
		emptyCheckbox();//清空原来的勾选
		$("#t_body").empty();
		$("button[name='diseaseItemPage']").attr("disabled", false);
		$("#queryForm [name='itemName']").attr("disabled", false);
		$("#queryForm input[name='diseaseId']").val(treeNode.value.diseaseId);
		$("#diseaseId").val(treeNode.value.diseaseId);
		$("#diseaseName").val(treeNode.value.diseaseName);
		refreshDataTable();
		$("#quotaListTable_paginate").show();
	}else{
		$("button[name='diseaseItemPage']").attr("disabled", true);
		$("#queryForm [name='itemName']").attr("disabled", true);
		$("#zTree_div").css("height","auto");
		$("#cust_div").css("height","auto");
		$("#t_body").empty();
		$("#t_body").append("<tr><td colspan='100' class='text-center'><h4>请先选择疾病</h4></td></tr>");
		$("#t_foot").hide();
		$("#quotaListTable_paginate").hide();
	}
	setDH();
};

//*****************************************自定义开始****************************************
function refreshDataTable(){
	$("#zTree_div").css("height","auto");
	$("#cust_div").css("height","auto");
	//加载对应的指标信息
	quotaTable = datatable.table(quotaOptions);
}

//设置目录高度
function setDH() {
	$("#left_div").css("height", "auto");
	$("#cust_div").css("height", "auto");
	var hLeft = $("#left_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft > hRight ? hLeft : hRight;
	$("#left_div").height(max+50);
	$("#cust_div").height(max+50);
}

//未选中或选中最顶级节点时设置
function setNoSelect() {
	$("#submitButton").attr("disabled", true);
	common.clearForm("updateForm");
}

function deleteDiseaseItem(id){
	layer.confirm('确定要执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		}, 
	function(){
		var url = URL.get("item.DELETE_PRESCRIPTION");
    	var params = "prescriptionId=" + id;
    	ajax.post(url, params, dataType.json, function(data){
    		if(data.result){
    			datatable.remove(quotaTable, quotaRow);
    			$("#id").val("");
    		}else{
    			layer.msg(data.message);
    		}
    	});
	});
}

function isChoose(id){
	if(common.isEmpty(id)){
	    layer.msg('请选择操作的记录');
	    return false;
	}else{
		return true;
	}
}
//添加指标
function addItem(){
	//清空前一次的选择
	$("#itemCodeListValue").empty();
	var $itemList = $("input:checkbox[name='itemList'][checked]");
	if($itemList.length > 0){
		$itemList.each(function(index, item){
			var id = item.value;
			$("#itemCodeListValue").append(
				"<tr id='"+id+"_add'>"+
				"	<input type='hidden' name='itemCodeList' value='"+id+"'/>"+
				"</tr>"
			);
		});
	}
	//提交表单
	saveItem();
}
//提交表单方法
function saveItem(){
	layer.confirm('确定要执行【保存】操作？', {
		  btn: ['确定','取消'] //按钮
		}, 
	function(){
		$("#diseaseItemForm").ajaxPost(dataType.json, function(data){
			if(data.result){
				layer.msg('保存成功！');
	    		refreshDataTable();
	    		$("#diseaseItemModal").modal("hide");
			}else{
				layer.msg(data.message);
			}
		});
	});
}
//选择行
function trClick(id){
	var $check = $("#"+id+"_checkbox");
	if($check.attr("checked") == "checked"){
		$check.attr("checked", false);
	}else{
		$check.attr("checked", true);
	}
}
//清空所有的勾选
function emptyCheckbox(){
	$("input:checkbox[name='itemList']").removeAttr("checked");
}
//检索指标
function searchItem(){
	$("#itemListTr tr").show();
	var name = $("#itemName").val();
	$("#itemListTr tr").each(function(i,o){
		var $obj = $(o);
		var trName = $obj.attr("name");
		if((!common.isEmpty(name) && trName.indexOf(name) == -1)){
			$obj.hide();
		}
	});
}
//弹出窗中添加链接的方法
function addProductToOrder(productId){
	var itemConfigIds = '';
	$("input:radio[name='tableRadio']").each(function(index, obj){
		itemConfigIds += $(obj).val(); //这里的value就是每一个input的value值~
	});
	//判断指不存在的话，添加指标
	if(itemConfigIds.indexOf(productId) < 0){
		var url = URL.get("item.ADD_PRESCRIPTION");
		var params = "productId=" + productId + "&diseaseId=" + $("#diseaseIdQuery").val();
		ajax.post(url,params,dataType.json,function(data){
			datatable.add(quotaTable, data.value);// 添加
			setDH();
		});
	}else{
		layer.msg('不能重复添加相同的营养处方！');
	}
}
/**************************************************************************************************/

/**
 * 显示信息内容
 * 
 * @param intakeId
 * @param type
 */
function showToolTipContent(intakeId, type){
	$("#" + type + intakeId).tooltip("show");
}
/**
 * @return 
 * 		返回名称浮窗显示的方法
 */
function nameDetailOnMouseover(code, content){
	return "<a id='mark_"+code+"'" + 
	       "   data-toggle='tooltip'" + 
	       "   data-title='" + content + "'" + " data-placement='top'" +
	       "   onmouseover='showToolTipContent(\"" + code + "\", \"mark_\");'>" + content + "</a>" + 
	       "<span style='display: none;'>" + code + "</span>";
}

	
	
	
