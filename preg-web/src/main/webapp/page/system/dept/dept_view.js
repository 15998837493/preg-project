/**
 * 验证设置
 */
var deptAddOptions = {
	rules : {
		deptName : {
			required : true,
			maxlength : 50,
			remote : {
				url : URL.get("System.DEPT_NAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					deptName : function() {
						return $("#addDeptForm [name='deptName']").val();
					},
					deptNameOld : function() {
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
		},
		deptPhone : {
			maxlength : 50
		},
		deptRemark : {
			maxlength : 100
		}
	},
	messages : {
		deptName : {
			remote : "该部门名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
			$('#addDeptForm').ajaxPost(dataType.json, function(data) {
				// 操作成功返回查询页
				if (data.result) {
					treeObj.addNodes(selectNode, data.value);
					selectNode.iconSkin = "mulu";
					treeObj.updateNode(selectNode);
					common.clearForm("addDeptForm");
					$("#addDeptModal").modal("hide");
				} else {
					layer.msg(data.message);
				}
			});
	}
};

/**
 * 验证设置
 */
var deptUpdateOptions = {
	rules : {
		deptName : {
			required : true,
			maxlength : 50,
			remote : {
				url : URL.get("System.DEPT_NAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					deptName : function() {
						return $("#deptName").val();
					},
					deptNameOld : function() {
						return $("#deptNameOld").val();
					},
					operateType : function() {
						return "update";
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		deptPhone : {
			maxlength : 50
		},
		deptRemark : {
			maxlength : 100
		}
	},
	messages : {
		deptName : {
			remote : "该部门名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		$('#updateForm').ajaxPost(dataType.json, function(data) {
			// 操作成功返回查询页
			if (data.result) {
				selectNode.name = data.value.name;
				selectNode.value = data.value.value;
				treeObj.updateNode(selectNode);
				if (data.value.value.deptType == 2) {
					removeHoverDom("zTree", selectNode);
				}
				if (data.value.value.deptType == 1) {
					addHoverDom("zTree", selectNode);
				}
				layer.msg("修改成功！");
			} else {
				layer.msg(data.message);
			}
		});
	}
};

// 当前选中结点
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
			return treeNode.id != "0000";
		},
		showRenameBtn : false,
		removeTitle : "删除部门"
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
		onExpand: function(){setDH();}
	}
};

$(document).ready(function() {
	// 表单校验
	$("#updateForm").validate(deptUpdateOptions);
	common.requiredHint("updateForm", deptUpdateOptions);
	// 表单校验
	$("#addDeptForm").validate(deptAddOptions);
	common.requiredHint("addDeptForm", deptAddOptions);
	common.initCodeRadio("deptType", "deptType", "deptTypeRadio",
			"deptType", 6);
	common.initCodeSelect("status", "status", "deptStatus");
	common.initCodeRadio("deptType", "deptType", "addTypeRadio",
			"deptType", 2);
	common.initCodeSelect("status", "status", "addStatus");

	// 调节div高度
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	setNoSelect();
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

// 拖拽操作
function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for ( var i = 0, l = curDragNodes.length; i < l; i++) {
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
		for ( var i = 0, l = curDragNodes.length; i < l; i++) {
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
		for ( var i = 0, l = curDragNodes.length; i < l; i++) {
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
	for ( var i = 0, l = treeNodes.length; i < l; i++) {
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
	var deptIds = "";
	$(targetNode.getParentNode().children).each(function(index, node) {
		deptIds += node.id + ",";
	});
	deptIds = deptIds.substring(0, deptIds.lastIndexOf(","));
	var url = URL.get("System.DEPT_UPDATE_ORDER");
	var params = "deptIds=" + deptIds;
	ajax.post(url, params, dataType.json, function(data) {
		if (data.result) {
			layer.msg("修改排序成功！");
		} else {
			layer.msg(data.message);
		}
	}, false);
	beforeClick(treeId, treeObj.getSelectedNodes()[0]);
}

// 结点点击时间回调函数
function beforeClick(treeId, treeNode) {
	selectNode = treeNode;
	if (treeNode.level != 0) {
		$("#submitButton").attr("disabled", false);
		var url = URL.get("System.DEPT_GET");
		var params = "deptId=" + selectNode.id;
		ajax.post(url, params, dataType.json, function(data) {
			if (data.result) {
				$("#updateForm").jsonToForm(data.value);
				$("#deptParentName").val(treeNode.getParentNode().name);
				$("#deptNameOld").val(data.value.deptName);
				if (!common.isEmpty(treeNode.children)
						&& treeNode.children.length > 0) {
					$("#updateForm [name='deptType']").attr("disabled", true);
				} else {
					$("#updateForm [name='deptType']").attr("disabled", false);
				}
			} else {
				layer.msg(data.message);
			}
		}, false);
	} else {
		setNoSelect();
	}
}

// 删除结点回调函数
function beforeRemove(treeId, treeNode) {
	layer.confirm("确定要删除部门【" + treeNode.name + "】以及该部门下的所有子部门和部门员工？", function(data) {
		if (data) {
			var url = URL.get("System.DEPT_REMOVE");
			var params = "deptId=" + treeNode.id;
			ajax.post(url, params, dataType.json, function(data) {
				if (data.result) {
					setNoSelect();
					treeObj.removeChildNodes(treeNode);
					treeObj.removeNode(treeNode);
					var parentNode = treeNode.getParentNode();
					if (parentNode.id != "0000" && !parentNode.isParent) {
						parentNode.iconSkin = "dept";
						treeObj.updateNode(parentNode);
					}
					layer.msg("删除成功！");
				} else {
					layer.msg(data.message);
				}
			});
		}
	});
}

// 添加结点
function addHoverDom(treeId, treeNode) {
	// 三级节点和类型为菜单的节点不能添加
	if (treeNode.level == 6
			|| (!common.isEmpty(treeNode.value) && treeNode.value.deptType == '2')) {
		return false;
	}
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加部门' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			treeObj.selectNode(treeNode);// 选中
			beforeClick(treeId, treeNode);// 触发单机事件
			selectNode = treeNode;
			setAddFormContent();// 初始化添加表单
			$("#addDeptModal").modal("show");
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

// 设置addDeptForm表单内容
function setAddFormContent() {
	$("#addDeptForm [name='deptOrder']").val(
			common.isEmpty(selectNode.children) ? 1
					: selectNode.children.length + 1);
	$("#addDeptForm [name='deptParent']").val(selectNode.id);
	$("#addDeptForm [name='deptGrade']").val(selectNode.level + 1);
}

// 未选中或选中最顶级节点时设置
function setNoSelect() {
	$("#submitButton").attr("disabled", true);
	common.clearForm("updateForm");
}
