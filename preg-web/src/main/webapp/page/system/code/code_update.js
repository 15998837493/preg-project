var codeData;
var parentCodeData;
var size;
var operateType;
var codeNameOld;
var codeValueOld;

var updateCodeData;
//选中行信息
var updateCodeRow;
//table对象
var updateCodeTable;
//列表配置信息
var updateCodeTableOptions = {
	id : "updateCodeTable",
	form : "updateCodeQueryForm",
	columns : [ {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<input type='radio' name='tableRadio'  />";
		}
	}, {
		"data" : "codeKind",
		"sClass" : "text-center"
	}, {
		"data" : "codeName",
		"sClass" : "text-center"
	}, {
		"data" : "codeValue",
		"sClass" : "text-center"
	} ],
	rowClick : function(data, row) {
		codeData = data;
		$("#codeId").val(data.codeId);
	},
	condition : "updateCodeCondition",
	callback : setDH
};
/**
 * 验证设置
 */
var codeOptions = {
	rules: {
		selectType: {
			required:true
		},
		codeKind: {
			required:true,
			maxlength: 30
		},
		codeName: {
			required:true,
			maxlength: 50,
			remote: {
				url: URL.get("System.CODE_NAME_VALID"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    codeKind: function() {  
				        return selectNode.value.codeKind;  
					},
					codeName: function() {  
				        return $("#codeName").val();  
					},
					codeNameOld: function() {  
				        return codeNameOld;  
					},
					operateType: function() {  
				        return operateType;  
					},
					random: function() {
						return Math.random();
					}
				}
			}
		},
		codeValue: {
			required:true,
			maxlength: 30,
			remote: {
				url: URL.get("System.CODE_VALUE_VALID"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    codeKind: function() {  
				        return selectNode.value.codeKind;  
					},
					codeValue: function() {  
				        return $("#codeValue").val();  
					},
					codeValueOld: function() {  
				        return codeValueOld;  
					},
					operateType: function() {  
				        return operateType;  
					},
					random: function() {
						return Math.random();
					}
				}
			}
		}
	},
	messages: {
		codeName: {
			remote: "该代码名称已经存在，请重新输入"
		},
		codeValue: {
			remote: "该代码值已经存在，请重新输入"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		$('#codeForm').ajaxPost(dataType.json,function(data){
			//操作成功返回查询页
			console.log(data);
			if(operateType == "add"){
				if(data.value.codeType==3){
	    			refreshDataTable();
	    		}else{
	    			treeObj.addNodes(selectNode, data.value);
	    		}
			}if(operateType == "update"){
				if(data.value.value.codeType==3){
	    			refreshDataTable();
	    		}else{
	    			selectNode.value = data.value.value;
	    			selectNode.name = data.value.name;
	    			treeObj.updateNode(selectNode);
	    		}
			}
    		
    		$("#codeModal").modal("hide");
    		// 刷新设置页面代码表缓存
    		getCodeCache();
		});
    }
};

//当前选中结点
var selectNode;
//树对象
var treeObj;
//配置
var setting = {
	flag: {
		removeFlag: false,
		editNameFlag: false
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		drag: {
			autoExpandTrigger: true,
			prev: dropPrev,
			inner: dropInner,
			next: dropNext
		},
		enable: true,
		editNameSelectAll: true,
		showRemoveBtn: function showRemoveBtn(treeId, treeNode) { return treeNode.level!=0; },
		showRenameBtn: true,
		removeTitle: "删除",
		renameTitle: "编辑"
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback : {
		beforeDrag: beforeDrag,
		beforeClick : beforeClick,
		beforeEditName: beforeEditName,
		beforeRemove: beforeRemove,
		onDrop: onDrop,
		onExpand: function(){setDH();}
	}
};

$(document).ready(function() {
	//表单校验
	$("#codeForm").validate(codeOptions);
	common.requiredHint("codeForm",codeOptions);
	
	//监听调节div高度
	$(".ztree").live('click', function(){
		setDH();
	});
	$(".pages").live('click', function(){
		setDH();
	});
	
	// 按钮点击事件
	$("button[name='codePage']").click(function(){
		$("#codeType_div").hide();
		$("#codeValue").attr("disabled", false);
		if(this.id=='searchButton'){
			datatable.table(updateCodeTableOptions);
		}
		if(this.id == 'addButton'){
			operateType = "add";
			codeNameOld = "";
			codeValueOld = "";
			$("#codeForm").attr("action", URL.get("System.CODE_ADD"));
			common.clearForm("codeForm");
			$("#parentCodeKind").val(parentCodeData.codeKind);
			$("#parentCodeValue").val(parentCodeData.codeValue);
			$("#codeKind").val(parentCodeData.codeKind);
			$("#codeGrade").val(parentCodeData.codeGrade+1);
			$("#codeOrder").val(size);
			$("#codeType").val(3);
			$("#codeModal").modal("show");
		}
		if(this.id == 'editButton' && common.isChoose(codeData)){
			operateType = "update";
			codeNameOld = codeData.codeName;
			codeValueOld = codeData.codeValue;
			$("#codeForm").attr("action", URL.get("System.CODE_UPDATE"));
			$("#codeForm").jsonToForm(codeData);
			$("#codeModal").modal("show");
		}
		if(this.id == 'removeButton' && common.isChoose(codeData)){
			removeCode(codeData.codeId);
		}
		if(this.id == 'orderButton'){
			initEditCodeOrder();
		}
	});
	
	$("#orderModalButton").click(function(){
		$("#orderModal").modal("hide");
		beforeClick("zTree", treeObj.getSelectedNodes()[0]);
	});
});

function refreshDataTable(){
	$("#zTree_div").css("height","auto");
	$("#cust_div").css("height","auto");
	$("#t_foot").show();
	updateCodeTable = datatable.table(updateCodeTableOptions);
}

//分页信息展示
function pageList(data,start,end){
	size = common.isEmpty(data)?1:data.length+1;
	for(var i=start;i<end;i++){
		var code = data[i];
		$("#t_body").append(
			"<tr>"+
			"   <td class='text-center'><input name='codeRadio' type='radio' value='"+i+"' /></td>" +
			"	<td class='text-center'>" + code.codeKind + "</td>" +
			"	<td class='text-center'>" + code.codeName + "</td>" +
			"	<td class='text-center'>" + code.codeValue + "</td>" +
			"</tr>"
		);
	}
}

//*****************************************zTree开始****************************************

function filter(treeId, parentNode, responseData) {
	if (!responseData)
		return null;
	for ( var i = 0, l = responseData.length; i < l; i++) {
		responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
	}
	return responseData;
}

//拖拽操作
function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodes.length; i<l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropInner(treeId, nodes, targetNode) {
	if (targetNode && targetNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodes.length; i<l; i++) {
			if (!targetNode && curDragNodes[i].dropRoot === false) {
				return false;
			} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropNext(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodes.length; i<l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
var curDragNodes;
function beforeDrag(treeId, treeNodes) {
	for (var i=0,l=treeNodes.length; i<l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodes = null;
			return false;
		} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
			curDragNodes = null;
			return false;
		}
	}
	curDragNodes = treeNodes;
	return true;
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	var codeIds = "";
	$(targetNode.getParentNode().children).each(function(index,node){
		codeIds += node.value.codeId+",";
	});
	codeIds = codeIds.substring(0, codeIds.lastIndexOf(","));
	var url = URL.get("System.CODE_UPDATE_ORDER");
	var params = "codeIds="+codeIds;
	ajax.post(url,params,dataType.json,function(data){
	});
	beforeClick(treeId, treeObj.getSelectedNodes()[0]);
}

// 结点点击时间回调函数
function beforeClick(treeId, treeNode){
	selectNode = treeNode;
	codeData = null;
	parentCodeData = treeNode.value;
	$("#codeId").val("");
	$("#codeKind").val(parentCodeData.codeKind);
	if(treeNode.value.codeType == 2){
		$("button[name='codePage']").attr("disabled", false);
		$("#updateCodeQueryForm [name='parentCodeKind']").val(treeNode.value.codeKind);
		$("#updateCodeQueryForm [name='parentCodeValue']").val(treeNode.value.codeValue);
		refreshDataTable();
	}else{
		$("button[name='codePage']").attr("disabled", true);
		$("#updateCodeQueryForm [name='parentCodeKind']").val("");
		$("#updateCodeQueryForm [name='parentCodeValue']").val("");
		$("#zTree_div").css("height","auto");
		$("#cust_div").css("height","auto");
		$("#t_body").empty();
		$("#t_body").append("<tr><td colspan='100' class='text-center'><h3>请先选择类型</h3></td></tr>");
		$("#t_foot").hide();
		setDH();
	}
}

// 删除结点回调函数
function beforeRemove(treeId, treeNode) {
	treeObj.selectNode(treeNode);// 选中
	beforeClick(treeId, treeNode);// 触发单机事件
	selectNode = treeNode;
	
	layer.confirm("确定对选中内容执行【删除】操作？", function (index) {
		var url = URL.get("System.CODE_REMOVE");
		var params = "codeId="+treeNode.value.codeId;
		ajax.post(url,params,dataType.json,function(data){
			treeObj.removeChildNodes(treeNode);
			treeObj.removeNode(treeNode);
			$("button[name='codePage']").attr("disabled", true);
			$("#updateCodeQueryForm [name='parentCodeKind']").val("");
			$("#updateCodeQueryForm [name='parentCodeValue']").val("");
			$("#zTree_div").css("height","auto");
			$("#cust_div").css("height","auto");
			$("#t_body").empty();
			$("#t_body").append("<tr><td colspan='100' class='text-center'><h3>请先选择类型</h3></td></tr>");
			$("#t_foot").hide();
			setDH();
		});
		layer.close(index);
	});
}

//编辑结点回调函数
function beforeEditName(treeId, treeNode) {
	treeObj.selectNode(treeNode);// 选中
	beforeClick(treeId, treeNode);// 触发单机事件
	selectNode = treeNode;
	
	$("#codeType_div").hide();
	$("#codeValue").attr("disabled", true);
	$("#codeForm").jsonToForm(treeNode.value);
	operateType = "update";
	codeNameOld = treeNode.value.codeName;
	codeValueOld = treeNode.value.codeValue;
	$("#codeForm").attr("action", URL.get("System.CODE_UPDATE"));
	$("#codeModal").modal("show");
}

// 添加结点
function addHoverDom(treeId, treeNode) {
	//三级节点和类型为菜单的节点不能添加
	if(common.isEmpty(treeNode.value) || treeNode.value.codeType != 1){
		return false;
	}
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加代码表' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		common.clearForm("codeForm");
		operateType = "add";
		codeNameOld = "";
		codeValueOld = "";
		$("#codeType_div").show();
		$("#codeValue").attr("disabled", false);
		$("#codeForm").attr("action", URL.get("System.CODE_ADD"));
		treeObj.selectNode(treeNode);// 选中
		beforeClick(treeId, treeNode);// 触发单机事件
		selectNode = treeNode;
		$("#parentCodeKind").val(parentCodeData.codeKind);
		$("#parentCodeValue").val(parentCodeData.codeValue);
		$("#codeKind").val(parentCodeData.codeKind);
		$("#codeGrade").val(parentCodeData.codeGrade+1);
		$("#codeOrder").val(common.isEmpty(selectNode.children)?1:selectNode.children.length+1);
		$("#codeModal").modal("show");
	});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

//*****************************************排序树****************************************

//当前选中结点
var orderNode;
//树对象
var orderObj;
//配置
var order_setting = {
	flag: {
		removeFlag: false,
		editNameFlag: false
	},
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	edit: {
		drag: {
			autoExpandTrigger: true,
			prev: dropPrevOrder,
			inner: dropInnerOrder,
			next: dropNextOrder
		},
		enable: true,
		editNameSelectAll: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback : {
		beforeDrag: beforeDragOrder,
		beforeClick : null,
		onDrop: onDropOrder
	}
};

//拖拽操作
function dropPrevOrder(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodesOrder.length; i<l; i++) {
			var curPNode = curDragNodesOrder[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropInnerOrder(treeId, nodes, targetNode) {
	if (targetNode && targetNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodesOrder.length; i<l; i++) {
			if (!targetNode && curDragNodesOrder[i].dropRoot === false) {
				return false;
			} else if (curDragNodesOrder[i].parentTId && curDragNodesOrder[i].getParentNode() !== targetNode && curDragNodesOrder[i].getParentNode().childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropNextOrder(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodesOrder.length; i<l; i++) {
			var curPNode = curDragNodesOrder[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
var curDragNodesOrder;
function beforeDragOrder(treeId, treeNodes) {
	for (var i=0,l=treeNodes.length; i<l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodesOrder = null;
			return false;
		} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
			curDragNodesOrder = null;
			return false;
		}
	}
	curDragNodesOrder = treeNodes;
	return true;
}
function onDropOrder(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	var codeIds = "";
	$(targetNode.getParentNode().children).each(function(index,node){
		codeIds += node.value.codeId+",";
	});
	codeIds = codeIds.substring(0, codeIds.lastIndexOf(","));
	var url = URL.get("System.CODE_UPDATE_ORDER");
	var params = "codeIds="+codeIds;
	ajax.post(url,params,dataType.json,function(data){
		if(!data.result){
		}
	});
}
//*****************************************自定义开始****************************************
//设置目录高度
function setDH(){
	$("#zTree_div").css("height","auto");
	$("#cust_div").css("height","auto");
	var hLeft = $("#zTree_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft>hRight?hLeft:hRight;
	$("#zTree_div").height(max);
	$("#cust_div").height(max);
}

//删除代码表
function removeCode(codeId){
	layer.confirm("确定对选中内容执行【删除】操作？", function (index) {
		var url = URL.get("System.CODE_REMOVE");
		var params = "codeId="+codeId;
		ajax.post(url,params,dataType.json,function(data){
			refreshDataTable();
		});
		layer.close(index);
	});
};

//设置代码类型
function setCodeType(codeType){
	$("#codeType").val(codeType);
}

//初始化排序列表
function initEditCodeOrder(){
	var url = URL.get("System.CODE_ORDER_TREE");
	var params = "parentCodeKind=" + selectNode.value.codeKind + "&parentCodeValue=" + selectNode.value.codeValue + "&codeId=" + selectNode.value.codeId;
	ajax.post(url,params,dataType.json,function(data){
		$.fn.zTree.init($("#orderTree"), order_setting, data.value);
		orderObj = $.fn.zTree.getZTreeObj("orderTree");
		orderObj.expandNode(orderObj.getNodes()[0], true, false, false);
		$("#orderModal").modal("show");
	});
}
