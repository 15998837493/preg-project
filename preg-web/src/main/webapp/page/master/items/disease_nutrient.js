//存放指标主键用以判断当前检查项目中是否已经存在选择添加的指标，如果存在给提示不添加，如果不存在直接添加。
var itemConfigIds=[];
//当前选中结点
var selectNode;
// 树对象
var treeObj;
var zTree_1 = {
	beforeClick : function(treeId, treeNode) {
		selectNode = treeNode;
		treeObj.selectNode(treeNode);
		$("#id").val("");
		$("#itemId").val("");
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
// 配置
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
			$("#inspectName").val("");
			datatable.table(itemListDataOptions);
			searchItem();
			$("#inspectItemModal").modal("show");
		}
		if(this.id == 'removeButton' && isChoose(id)){
			deleteDiseaseNutrient(id);
		}
		if(this.id == 'searchButton'){
			refreshDataTable();
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
	itemConfigIds=[];//选择不同的检查项目时清空数组
	if(treeNode.level >= 1 && !treeNode.isParent){
		emptyCheckbox();//清空原来的勾选
		$("#t_body").empty();
		$("button[name='diseaseItemPage']").attr("disabled", false);
		$("#queryForm input[name='diseaseCode']").val(treeNode.value.diseaseCode);
		$("#diseaseCode").val(treeNode.value.diseaseCode);
		$("#diseaseName").val(treeNode.value.diseaseName);
		refreshDataTable();
		//重新选择检查项目清空存放指标主键的数组后重新初始化该数组
		$("input:radio[name='tableRadio']").each(function(index, obj){
			itemConfigIds.push($(obj).val()); //这里的value就是每一个input的value值~
		});
	}else{
		$("button[name='diseaseItemPage']").attr("disabled", true);
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
function refreshDataTable(){
	$("#id").val("");
	//加载对应的指标信息
	quotaTable = datatable.table(quotaOptions);
	setDH();
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

function deleteDiseaseNutrient(id){
	layer.confirm('确定要执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		}, 
	function(){
		var url = URL.get("item.DELETE_NUTRIENT");
    	var params = "id=" + id;
    	ajax.post(url, params, dataType.json, function(data){
    		if(data.result){
    			datatable.remove(quotaTable, quotaRow);
    			itemConfigIds.remove(data.value);//删除数据时同步存放指标主键的数组
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
function addNutrient(nutrientId){
	//判断指不存在的话，添加指标
	if(itemConfigIds.indexOf(nutrientId) < 0){
		var url = URL.get("item.ADD_NUTRIENT");
		var params = "nutrientId=" + nutrientId + "&diseaseCode=" + $("#diseaseIdQuery").val();
		ajax.post(url,params,dataType.json,function(data){
			datatable.add(quotaTable, data.value);// 添加
			itemConfigIds.push(nutrientId);//添加数据时同步存放指标主键的数组
			setDH();
		});
	}else{
		layer.msg('不能重复添加相同的元素！');
	}
}