//*****************************************zTree开始****************************************
//当前选中结点
var selectNode;
// 树对象
var treeObj;
// 配置
var setting = {
	flag : {
		removeFlag : false,
		editNameFlag : false
	},
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		drag : {
			autoExpandTrigger : true,
			prev : dropPrev,
			inner : dropInner,
			next : dropNext
		},
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : function showRemoveBtn(treeId, treeNode) {
			return treeNode.id != "000";
		},
		showRenameBtn : false,
		removeTitle : "删除类别",
		renameTitle : "编辑类别"
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeClick : beforeClick,
		beforeRemove : beforeRemove,
		onDrop : onDrop,
		onExpand : function() {
			setDH();
		}
	}
};

/**
 * 验证设置
 */
var menuAddOptions = {
	rules : {
		catalogName : {
			required : true,
			maxlength : 50,
			remote : {
				url : URL.get("Customer.CATALOG_NAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					menuName : function() {
						return $("#addMenuForm [name='catalogName']").val();
					},
					menuNameOld : function() {
						return "";
					},
					operateType : function() {
						return "add";
					},
					random : function() {
						return Math.random();
					}
				}
			}
		}
	},
	messages : {
		menuName : {
			remote : "该类别名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm("确定要执行【保存】操作？", function(index) {
			$('#addMenuForm').ajaxPost(dataType.json, function(data) {
				// 操作成功返回查询页
				if (data.result) {
					treeObj.addNodes(selectNode, data.value);
					common.clearForm("addMenuForm");
					$("#addMenuModal").modal("hide");
					console.log(data.value);
				} else {
					layer.alert(data.message);
				}
			});
		});
	}
};

/**
 * 验证设置
 */
var menuUpdateOptions = {
	rules : {
		catalogName : {
			required : true,
			maxlength : 50,
			remote : {
				url : URL.get("Customer.CATALOG_NAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					menuName : function() {
						return $("#menuName").val();
					},
					menuNameOld : function() {
						return $("#menuNameOld").val();
					},
					operateType : function() {
						return "update";
					},
					random : function() {
						return Math.random();
					}
				}
			}
		}
	},
	messages : {
		menuName : {
			remote : "该菜单名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm("确定要执行【保存】操作？", function(index) {
			$('#updateForm').ajaxPost(
					dataType.json,
					function(data) {
						// 操作成功返回查询页
						if (data.result) {
							selectNode.name = $(
									"#updateForm [name='catalogName']").val();
							treeObj.updateNode(selectNode);
							layer.alert("修改成功！");
						} else {
							layer.alert(data.message);
						}
					});
		});
	}
};

$().ready(function() {
	// 表单校验
	$("#updateForm").validate(menuUpdateOptions);
	common.requiredHint("updateForm", menuUpdateOptions);
	// 表单校验
	$("#addMenuForm").validate(menuAddOptions);
	common.requiredHint("addMenuForm", menuAddOptions);
	// 初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// treeObj.expandAll(true);
	// 调节div高度
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	setNoSelect();
	layer.close(layer.index);
});

function filter(treeId, parentNode, responseData) {
	if (!responseData)
		return null;
	for (var i = 0, l = responseData.length; i < l; i++) {
		responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
	}
	return responseData;
}

// 拖拽操作
function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode()
					&& curPNode.childOuter === false) {
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
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			if (!targetNode && curDragNodes[i].dropRoot === false) {
				return false;
			} else if (curDragNodes[i].parentTId
					&& curDragNodes[i].getParentNode() !== targetNode
					&& curDragNodes[i].getParentNode().childOuter === false) {
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
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode()
					&& curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
var curDragNodes;
function beforeDrag(treeId, treeNodes) {
	for (var i = 0, l = treeNodes.length; i < l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodes = null;
			return false;
		} else if (treeNodes[i].parentTId
				&& treeNodes[i].getParentNode().childDrag === false) {
			curDragNodes = null;
			return false;
		}
	}
	curDragNodes = treeNodes;
	return true;
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	var menuIds = "";
	$(targetNode.getParentNode().children).each(function(index, node) {
		menuIds += node.id + ",";
	});
	menuIds = menuIds.substring(0, menuIds.lastIndexOf(","));
	var url = URL.get("Customer.CATALOG_ORDER_UPDATE");
	var params = "menuIds=" + menuIds;
	ajax.post(url, params, dataType.json, function(data) {
		if (data.result) {
			layer.alert("修改排序成功！");
		} else {
			layer.alert(data.message);
		}
	}, false);
	beforeClick(treeId, treeObj.getSelectedNodes()[0]);
}

// 结点点击时间回调函数
function beforeClick(treeId, treeNode) {
	selectNode = treeNode;
	if (treeNode.level != 0) {
		$("#submitButton").attr("disabled", false);
		var url = URL.get("Customer.CATALOG_INFO_GET");
		var params = "menuId=" + selectNode.id;
		ajax.post(url, params, dataType.json, function(data) {
			if (data.result) {
				$("#updateForm").jsonToForm(data.value);
				$("#menuParentName").val(treeNode.getParentNode().name);
				$("#menuNameOld").val(data.value.menuName);
				if (data.value.menuType == 1) {
					$("#updateForm [name='menuUrl']").attr("readonly", true);
				} else {
					$("#updateForm [name='menuUrl']").attr("readonly", false);
				}
			} else {
				layer.alert(data.message);
			}
		}, false);
	} else {
		setNoSelect();
	}
}

// 删除结点回调函数
function beforeRemove(treeId, treeNode) {
	var content = treeNode.value.menuType == 1 ? "确定要删除目录【" + treeNode.name
			+ "】以及该目录下的所有菜单？" : "确定要删除菜单【" + treeNode.name + "】？";
	layer.confirm(content, function(data) {
		var url = URL.get("Customer.PRODUCT_CATALOG_REMOVE");
		var params = "menuId=" + treeNode.id;
		ajax.post(url, params, dataType.json, function(data) {
			if (data.result) {
				setNoSelect();
				treeObj.removeChildNodes(treeNode);
				treeObj.removeNode(treeNode);
				layer.alert("删除成功！");
			} else {
				layer.alert(data.message);
			}
		});
	});
}

// 添加结点
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加功能菜单' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			treeObj.selectNode(treeNode);// 选中
			beforeClick(treeId, treeNode);// 触发单机事件
			selectNode = treeNode;
			setAddFormContent();// 初始化添加表单
			$("#addMenuModal").modal("show");
		});
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};

// *****************************************自定义开始****************************************
// 设置目录高度
function setDH() {
	$("#zTree_div").css("height", "auto");
	$("#cust_div").css("height", "auto");
	var hLeft = $("#zTree_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft > hRight ? hLeft : hRight;
	$("#zTree_div").height(max);
	$("#cust_div").height(max);
}

// 设置addMenuForm表单内容
function setAddFormContent() {
	$("#addMenuForm [name='catalogOrder']").val(
			common.isEmpty(selectNode.children) ? 1
					: selectNode.children.length + 1);
	$("#addMenuForm [name='catalogParentId']").val(selectNode.id);
	if (selectNode.level == 2) {
		$("#addMenuForm [name='menuType']").val("2");
		$("#addMenuForm [name='menuType']").attr("readonly", true);
	} else {
		$("#addMenuForm [name='menuType']").val("");
		$("#addMenuForm [name='menuType']").attr("readonly", false);
	}
	$("#addMenuForm [name='menuUrl']").attr("readonly", false);
}

// 未选中或选中最顶级节点时设置
function setNoSelect() {
	$("#submitButton").attr("disabled", true);
	common.clearForm("updateForm");
}

// 菜单类型更改事件
function setMenuSelect(menuType) {
	if (menuType == "1") {
		$("#addMenuForm [name='menuUrl']").val("#");
		$("#addMenuForm [name='menuUrl']").attr("readonly", true);
	} else {
		$("#addMenuForm [name='menuUrl']").val("");
		$("#addMenuForm [name='menuUrl']").attr("readonly", false);
	}
}

$(function() {
	var dragging = false;
	var leftWidth = $("#left_div").width();
	var rightWidth = $("#right_div").width();
	// 全局位置鼠标松开事件
	$(document).mouseup(function(e) {
		dragging = false;
	});
	// 全局位置鼠标移动事件
	$(document).mousemove(function(e) {
		if (dragging) {
			var value = e.pageX - initX;
			if (value > -leftWidth / 2 && value < rightWidth / 2) {
				if ((leftWidth + value) > $("#left_div").width()) {// 向右移动
					$("#right_div").width(rightWidth - value);
					$("#left_div").width(leftWidth + value);
				} else {// 向左移动
					$("#left_div").width(leftWidth + value);
					$("#right_div").width(rightWidth - value);
				}
			}
		}
	});
	// 检测浏览器窗口大小的改变
	$(window).resize(function() {
		$("#left_div").css("width", "20%");
		$("#right_div").css("width", "79.9%");
		leftWidth = $("#left_div").width();
		rightWidth = $("#right_div").width();
	});
});
