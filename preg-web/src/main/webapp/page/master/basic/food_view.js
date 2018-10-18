/**
 * datatables配置
 */
var foodData;
var foodRow;
var foodTable;
var elementList = [];

var foodTableOption = {
    id: "foodListTable",
    form: "cuisineQuery",
    bServerSide: true,
    columns: [{
        "data": null,
        "sClass": "text-center",
        "render": function(data, type, row, meta) {
            return "<input id='" + data.foodId + "' name='foodRadio' type='radio'/>";
        }
    },
    {
        "data": "foodName",
        "sClass": "text-center"
    },
    {
        "data": "foodTreeType",
        "sClass": "text-center",
			"render" : function(data, type, row, meta) {
				return data.length == 0?"______":data;
			}
    },
    {
        "data": "foodTaste",
        "sClass": "text-center"
    },
    {
        "data": "foodCuisine",
        "sClass": "text-center",
        "render": function(data, type, row, meta) {
            return CODE.transCode("COKE_MODE", data);
        }
    },
    ],
    callbackpage: function(option) {
        icheck.controlChecked({
            flag: false,
            checkname: "foodRadio"
        });
    },
    rowClick: function(data, row) {
        foodData = data;
        foodRow = row;
        $("#foodId").val(data.foodId);
    },
    inputtarget: [1],
    // 配置input检索条件对应的目标列
    condition: "foodCondition",
    async: false,
    loading: false
};

var fmData;
var fmRow;
var fmTable;
var fmdatableOption = {
    id: "fmListTable",
    form: "fmQuery",
    bServerSide: true,
    columns: [{
        "data": null,
        "sClass": "text-left",
        "render": function(data, type, row, meta) {
            return data.fmName;

        }
    },
    {
        "data": null,
        "sClass": "text-center",
        "render": function(data, type, row, meta) {
            return "<a onclick=addFm('" + data.fmId + "','" + data.fmName.replace(/\s/g,"") + "')>添加</a>";
        }
    }],
    rowClick: function(data, row) {
        fmData = data;
        fmRow = row;
        $("#fmId").val(data.fmId);
        $("#id2").val(data.fmId);
    },
    inputtarget: [0],
    // 配置input检索条件对应的目标列
    condition: "fmCondition",
//    async: false,
    loading: false,
    bLengthChange : false,
    isShowRecord : false,
//    dom: "t<'row'<'col-xs-12'p>>",
    pagingType:"numbers"
    // 是否启用遮罩层
};

/**
 * 验证参数设置
 */
var cuisineOperation = {
    rules: {
        foodName: {
            required: true,
            maxlength: 80
        },
        foodType: {
            required: true
        }
    },
	//设置错误信息显示到指定位置
	errorClass:"text-danger",
	errorElement:"em",
	errorContainer:"em",
	errorPlacement : function(error, element) {
		common.showmassage(error, element);
	},
	success : $.noop,
    submitHandler: function() {
        //校验是否上传图片
        if ($("#foodType").val() == ' ') {
            hint.warning("请选择菜谱类型");
            return;
        }
        var picPath = $("#foodPic").val();
        if ($("#foodType").val() == '') {
            hint.warning("请选择菜谱类型");
            return;
        }

        var extCount = $("#addFoodExt").find("tr");
        if (extCount.length == 0) {
            layer.alert("请添加食材原料");
            return;
        }
        hint.confirm("确定要执行【保存】操作？",
        function(data) {
        	if (data) {
        	$("#addFoodExt").empty();
            $('#addCuisineForm').ajaxPost(dataType.json,function(data) {
            		$("#foodId").val("");
                    foodTable = datatable.table(foodTableOption);
                    $("#showDiv").css("display", "block");
                    $("#addDiv").css("display", "none");
            },false,false);
        	 }
        });
    }
};

/**************************************************************ZTREE_BEGIN(一览页面)************************************************************/

/**
		 * 验证设置，类型表单验证
		 */
var menuAddOptions = {
    rules: {
        treeName: {
            required: true,
            maxlength: 50,
            remote: {
                url: URL.get("Food.FOOD_CATALOGINFO_NAME_VALID"),
                // 后台处理程序
                type: "post",
                // 数据发送方式
                dataType: "json",
                // 接受数据格式
                data: { // 要传递的数据，默认已传递应用此规则的表单项
                    treeName: function() {
                        return $("#treeName").val();
                    },
                    treeNameOld: function() {
                        return $("#treeNameOld").val();
                    },
                    opernerCatalog: function() {
                        return opernerCatalog;
                    },
                    random: function() {
                        return Math.random();
                    }
                }
            }
        }
    },
    messages: {
        treeName: {
            remote: "该类别名称已经存在，请重新输入"
        }
    },
    // 设置错误信息显示到指定位置
    errorClass: "text-danger",
    errorElement: "em",
    errorContainer: "em",
    errorPlacement: function(error, element) {
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        $('#addCatalogForm').ajaxPost(dataType.json,
        function(data) {
            if ("update" == opernerCatalog) {
                selectNode.name = $("#treeName").val();
                treeObj.updateNode(selectNode);
                layer.msg("修改成功");
            } else {
                treeObj.addNodes(selectNode, data.value);
                var pNode = treeObj.getNodeByParam("id", data.value.pId);
                pNode.iconSkin = "mulu";
                treeObj.updateNode(pNode);
                layer.msg("添加成功");
            }
            catalogValidator.resetForm();
            common.clearForm("addCatalogForm");
            $("#addCatalogModal").modal("hide");
        });
    }
};

//配置
var zTree_1 = {
    beforeClick: function(treeId, treeNode) {
        selectNode = treeNode;
        treeObj.selectNode(treeNode);
        $("#id").val("");
        if (treeNode.level >= 1 && !treeNode.isParent) {
            $("#t_body").empty();
            $("#categoryIdQuery").val(treeNode.value.catalogId);
           // $("#zTree_div").css("height", "auto");
           // $("#cust_div").css("height", "auto");
        } else {
           // $("#zTree_div").css("height", "auto");
            //$("#cust_div").css("height", "auto");
            $("#categoryIdQuery").val(null);
        }
        // 加载对应的指标信息
        foodTable = datatable.table(foodTableOption);
        setDH();
    }
};
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
        showRemoveBtn: function showRemoveBtn(treeId, treeNode) {
            return treeNode.id != "000" && treeNode.id != "000000000";
        },
        showRenameBtn: function showRenameBtn(treeId, treeNode) {
            return treeNode.id != "000" && treeNode.id != "000000000";
        },
        removeTitle: "删除类别",
        renameTitle: "编辑类别",
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeRemove: beforeRemove,
        beforeEditName: zTreeBeforeEditName,
        onClick: zTreeOnClick,
        onDrop: onDrop,
        onExpand: function() {
            setDH();
        }
    }
};

function filter(treeId, parentNode, responseData) {
    if (!responseData) return null;
    for (var i = 0,
    l = responseData.length; i < l; i++) {
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
        for (var i = 0,
        l = curDragNodes.length; i < l; i++) {
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
        for (var i = 0,
        l = curDragNodes.length; i < l; i++) {
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
        for (var i = 0,
        l = curDragNodes.length; i < l; i++) {
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
    for (var i = 0,
    l = treeNodes.length; i < l; i++) {
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
    var menuIds = "";
    $(targetNode.getParentNode().children).each(function(index, node) {
        menuIds += node.id + ",";
    });
    menuIds = menuIds.substring(0, menuIds.lastIndexOf(","));
    var url = URL.get("Food.FOOD_CATALOGINFO_UPDATE_ORDER");
    var params = "menuIds=" + menuIds;
    ajax.post(url, params, dataType.json,
    function(data) {
        if (data.result) {
            layer.msg("修改排序成功！");
        } else {
            layer.msg(data.message);
        }
    },
    false);
    beforeClick(treeId, treeObj.getSelectedNodes()[0]);
}

//删除结点回调函数
function beforeRemove(treeId, treeNode) {
    if ("000" == treeNode.id) {
        layer.alert("该节点不可以删除");
        return;
    }
	if (!checkHaveSub(treeNode.id,"food")) {
		layer.alert("该节点下有子目录不可以删除");
		return;
	}
    if (checkFoodTreeAmount(treeNode.id)) {
        var content = treeNode.value.menuType == 1 ? "确定要删除目录【" + treeNode.name + "】以及该目录下的所有菜单？": "确定要删除菜单【" + treeNode.name + "】？";
        layer.confirm(content,
        function(data) {
            var url = URL.get("Food.FOOD_CATALOGINFO_DELETE");
            var params = "treeId=" + treeNode.id;
            ajax.post(url, params, dataType.json,
            function(data) {
                if (data.result) {
                    setNoSelect();
                    treeObj.removeChildNodes(treeNode);
                    treeObj.removeNode(treeNode);
                    var pNode = treeObj.getNodeByParam("id", treeNode.pId);
                    pNode.iconSkin = "menu";
                    treeObj.updateNode(pNode);
                    $("#foodListTable").empty();
                    layer.msg("删除成功！");
                } else {
                    layer.msg(data.message);
                }
            });
        });
    } else {
        layer.msg("该节点不能删除！");
    }
}
//添加结点
function addHoverDom(treeId, treeNode) {
    if (treeNode.id.length > 6) {
        return;
    }
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) {
        return;
    }
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加功能菜单' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) {
        btn.bind("click",
        function() {
            treeObj.selectNode(treeNode); // 选中
            zTreeOnClick(null, treeId, treeNode);
            selectNode = treeNode;
            common.clearForm("addCatalogForm");
            //该类别下有食材配置不能添加子节点
            if (checkFoodTreeAmount(treeNode.id)) {
                $("#addCatalogForm").attr("action", URL.get("Food.FOOD_CATALOGINFO_ADD"));
                opernerCatalog = "add";
                setAddFormContent(); // 初始化添加表单
                $("#addCatalogModal").modal("show");
            } else {
                layer.msg("该类别下有食材配置不能添加子节点！");
            }
        });
    }
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};

/**
* 修改名称
*/
function zTreeBeforeEditName(treeId, treeNode) {
    var node = treeObj.getNodeByTId(treeNode.tId);
    treeObj.selectNode(treeNode);
    selectNode = treeNode;
    if ("000000000" == treeNode.id) {
        layer.alert("该节点不能修改名称");
        return;
    }
    zTreeOnClick(null, treeId, treeNode);
    common.clearForm("addCatalogForm");
    $("#addCatalogForm").attr("action", URL.get("Food.FOOD_CATALOGINFO_UPDATE"));
    $("#addCatalogForm").jsonToForm(treeNode.value);
    $("#treeName").val(node.name);
    $("#treeNameOld").val(treeNode.value.treeName);
    opernerCatalog = "update";
    $("#addCatalogModal").modal("show");
    setDH();
}

/**
* 点击节点分类的商品
*/
function zTreeOnClick(event, treeId, treeNode) {
    selectNode = treeNode;
    treeObj.selectNode(treeNode);
    var id = treeNode.id;
    if ("000" == id) {
        id = "";
    }
    if ("000000000" == id) {
        id = " ";
    }
    $("#categoryIdQuery").val(id);
	$("#foodId").val('');
    //datatable
    foodTable = datatable.table(foodTableOption);
    setDH();
}

//设置目录高度
function setDH() {
	  $("#left_div").height(600);
	  $("#list_div").height(600);
}

//未选中或选中最顶级节点时设置
function setNoSelect() {
    $("#submitButton").attr("disabled", true);
    common.clearForm("updateForm");
}

//设置addMenuForm表单内容
function setAddFormContent() {
    $("#addCatalogForm [name='treeOrder']").val(common.isEmpty(selectNode.children) ? 1 : selectNode.children.length + 1);
    $("#addCatalogForm [name='parentTreeId']").val(selectNode.id);
}

/**
* 验证食材类别下是否已经配置食材
* 
* @param parentTreeId
* @returns {Boolean}
*/
function checkFoodTreeAmount(treeId) {
    var flag = true;
    //检查该节点下是否配置有食材，如果有则不允许添加子节点，如果没有则可以。
    var url = URL.get("Food.FOOD_CATALOGINFO_AMOUNT_VALID");
    var params = "treeId=" + treeId;
    ajax.post(url, params, dataType.json,
    function(data) {
        flag = data.value;
    },
    false, false);
    return flag;
}
/**************************************************************ZTREE_END(一览页面)************************************************************/

/**************************************************************ZTREE_BEGIN(添加页面)************************************************************/
/**
 * 验证设置，类型表单验证
 */
var menuAddOptions2 = {
    rules: {
        treeName: {
            required: true,
            maxlength: 50,
            remote: {
                url: URL.get("foodMaterial.CATALOGINFO_NAME_VALID"),
                // 后台处理程序
                type: "post",
                // 数据发送方式
                dataType: "json",
                // 接受数据格式
                data: { // 要传递的数据，默认已传递应用此规则的表单项
                    treeName: function() {
                        return $("#treeName2").val();
                    },
                    treeNameOld: function() {
                        return $("#treeNameOld2").val();
                    },
                    opernerCatalog2: function() {
                        return opernerCatalog2;
                    },
                    random: function() {
                        return Math.random();
                    }
                }
            }
        }
    },
    messages: {
        treeName: {
            remote: "该类别名称已经存在，请重新输入"
        }
    },
    // 设置错误信息显示到指定位置
    errorClass: "text-danger",
    errorElement: "em",
    errorContainer: "em",
    errorPlacement: function(error, element) {
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        $('#addCatalogForm2').ajaxPost(dataType.json,
        function(data) {
            if ("update" == opernerCatalog2) {
                selectNode2.name = $("#treeName2").val();
                treeObj2.updateNode(selectNode2);
                layer.msg("修改成功");
            } else {
                treeObj2.addNodes(selectNode2, data.value);
                var pNode = treeObj2.getNodeByParam("id", data.value.pId);
                pNode.iconSkin = "mulu";
                treeObj2.updateNode(pNode);
                layer.msg("添加成功");
            }
            catalogValidator2.resetForm();
            common.clearForm("addCatalogForm2");
            $("#addCatalogModal2").modal("hide");
        });
    }
};

//配置
var zTree_2 = {
    beforeClick: function(treeId, treeNode) {
        selectNode2 = treeNode;
        treeObj2.selectNode(treeNode);
        $("#id2").val("");
        if (treeNode.level >= 1 && !treeNode.isParent) {
            $("#t_body").empty();
            $("#categoryIdQuery2").val(treeNode.value.catalogId);
            $("#zTree_add_page_div").css("height", "auto");
            $("#cust2_div").css("height", "auto");
        } else {
            $("#zTree_add_page_div").css("height", "auto");
            $("#cust2_div").css("height", "auto");
            $("#categoryIdQuery2").val(null);
        }
        // 加载对应的指标信息
        fmTable = datatable.table(fmdatableOption);
        setDH2();
    },
    onCollapse: function() {
        setDH2();
    },
    onExpand: function() {
        setDH2();
    }
};
var setting2 = {
    flag: {
        removeFlag: false,
        editNameFlag: false
    },
//    view: {
//        addHoverDom: addHoverDom2,
//        removeHoverDom: removeHoverDom2,
//        selectedMulti: false
//    },
//    edit: {
//        drag: {
//            autoExpandTrigger: true,
//            prev: dropPrev2,
//            inner: dropInner2,
//            next: dropNext2
//        },
//        enable: true,
//        editNameSelectAll: true,
//        showRemoveBtn: function showRemoveBtn(treeId, treeNode) {
//            return treeNode.id != "000" && treeNode.id != "000000000";
//        },
//        showRenameBtn: function showRenameBtn(treeId, treeNode) {
//            return treeNode.id != "000" && treeNode.id != "000000000";
//        },
//        removeTitle: "删除类别",
//        renameTitle: "编辑类别",
//    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeDrag: beforeDrag2,
        beforeRemove: beforeRemove2,
        beforeEditName: zTreeBeforeEditName2,
        onClick: zTreeOnClick2,
        onDrop: onDrop2,
        onExpand: function() {
            setDH2();
        }
    }
};

//拖拽操作
function dropPrev2(treeId, nodes, targetNode) {
    var pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner2 === false) {
        return false;
    } else {
        for (var i = 0,
        l = curDragNodes2.length; i < l; i++) {
            var curPNode = curDragNodes2[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropInner2(treeId, nodes, targetNode) {
    if (targetNode && targetNode.dropInner2 === false) {
        return false;
    } else {
        for (var i = 0,
        l = curDragNodes2.length; i < l; i++) {
            if (!targetNode && curDragNodes2[i].dropRoot === false) {
                return false;
            } else if (curDragNodes2[i].parentTId && curDragNodes2[i].getParentNode() !== targetNode && curDragNodes2[i].getParentNode().childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropNext2(treeId, nodes, targetNode) {
    var pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner2 === false) {
        return false;
    } else {
        for (var i = 0,
        l = curDragNodes2.length; i < l; i++) {
            var curPNode = curDragNodes2[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
var curDragNodes2;
function beforeDrag2(treeId, treeNodes) {
    for (var i = 0,
    l = treeNodes.length; i < l; i++) {
        if (treeNodes[i].drag === false) {
            curDragNodes2 = null;
            return false;
        } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
            curDragNodes2 = null;
            return false;
        }
    }
    curDragNodes2 = treeNodes;
    return true;
}
function onDrop2(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    var menuIds = "";
    $(targetNode.getParentNode().children).each(function(index, node) {
        menuIds += node.id + ",";
    });
    menuIds = menuIds.substring(0, menuIds.lastIndexOf(","));
    var url = URL.get("foodMaterial.CATALOGINFO_UPDATE_ORDER");
    var params = "menuIds=" + menuIds;
    ajax.post(url, params, dataType.json,
    function(data) {
        if (data.result) {
            layer.msg("修改排序成功！");
        } else {
            layer.msg(data.message);
        }
    },
    false);
    beforeClick(treeId, treeObj2.getSelectedNodes()[0]);
}

//删除结点回调函数
function beforeRemove2(treeId, treeNode) {
    if ("000" == treeNode.id) {
        layer.alert("该节点不可以删除");
        return;
    }
    if (checkFoodTreeAmount2(treeNode.id)) {
        var content = treeNode.value.menuType == 1 ? "确定要删除目录【" + treeNode.name + "】以及该目录下的所有菜单？": "确定要删除菜单【" + treeNode.name + "】？";
        layer.confirm(content,
        function(data) {
            var url = URL.get("foodMaterial.CATALOGINFO_REMOVE");
            var params = "treeId=" + treeNode.id;
            ajax.post(url, params, dataType.json,
            function(data) {
                if (data.result) {
                    setNoSelect2();
                    treeObj2.removeChildNodes(treeNode);
                    treeObj2.removeNode(treeNode);
                    var pNode = treeObj2.getNodeByParam("id", treeNode.pId);
                    pNode.iconSkin = "menu";
                    treeObj2.updateNode(pNode);
                    layer.msg("删除成功！");
                } else {
                    layer.msg(data.message);
                }
            });
        });
    } else {
        layer.msg("该节点不能删除！");
    }
}
//添加结点
function addHoverDom2(treeId, treeNode) {
    if (treeNode.id.length > 6) {
        return;
    }
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) {
        return;
    }
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加功能菜单' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) {
        btn.bind("click",
        function() {
            treeObj2.selectNode(treeNode); // 选中
            zTreeOnClick2(null, treeId, treeNode);
            selectNode2 = treeNode;
            common.clearForm("addCatalogForm2");
            //该类别下有食材配置不能添加子节点
            if (checkFoodTreeAmount2(treeNode.id)) {
                $("#addCatalogForm2").attr("action", URL.get("foodMaterial.CATALOGINFO_ADD"));
                opernerCatalog2 = "add";
                setAddFormContent2(); // 初始化添加表单
                $("#addCatalogModal2").modal("show");
            } else {
                layer.msg("该类别下有食材配置不能添加子节点！");
            }
        });
    }
};

function removeHoverDom2(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};

/**
* 修改名称
*/
function zTreeBeforeEditName2(treeId, treeNode) {
    var node = treeObj2.getNodeByTId(treeNode.tId);
    treeObj2.selectNode(treeNode);
    selectNode2 = treeNode;
    if ("000000000" == treeNode.id) {
        layer.alert("该节点不能修改名称");
        return;
    }
    zTreeOnClick2(null, treeId, treeNode);
    common.clearForm("addCatalogForm2");
    $("#addCatalogForm2").attr("action", URL.get("foodMaterial.CATALOGINFO_UPDATE"));
    var treeValue = treeNode.value;
    $("#treeId2").val(treeValue.treeId);
    $("#parentTreeId2").val(treeValue.parentTreeId);
    $("#treeOrder2").val(treeValue.treeOrder);
    $("#id2").val(treeValue.id);
    $("#treeName2").val(node.name);
    $("#treeNameOld2").val(treeValue.treeName);
    opernerCatalog2 = "update";
    $("#addCatalogModal2").modal("show");
    setDH2();
}

/**
* 点击节点是产讯指定的商品
*/
function zTreeOnClick2(event, treeId, treeNode) {	
    selectNode2 = treeNode;
//    treeObj2.selectNode(treeNode);
    var id = treeNode.id;
    if ("000" == id) {
        id = "";
    }
    if ("000000000" == id) {
        id = " ";
    }
    $("#categoryIdQuery2").val(id);
    //datatable
    fmTable = datatable.table(fmdatableOption);
//    setDH2();
}

//设置目录高度
function setDH2() {
    $("#left_div2").css("height", "auto");
    $("#cust2_div").css("height", "auto");
    var hLeft = $("#left_div2").height();
    var hRight = $("#cust2_div").height();
    var max = hLeft > hRight ? hLeft: hRight;
    $("#left_div2").height(max + 50);
    $("#cust2_div").height(max + 50);
}

//未选中或选中最顶级节点时设置
function setNoSelect2() {
    $("#submitButton").attr("disabled", true);
    common.clearForm("updateForm");
}

//设置addMenuForm表单内容
function setAddFormContent2() {
    $("#addCatalogForm2 #treeOrder2").val(common.isEmpty(selectNode2.children) ? 1 : selectNode2.children.length + 1);
    $("#addCatalogForm2 #parentTreeId2").val(selectNode2.id);
}

/**
 * 验证食材类别下是否已经配置食材
 * 
 * @param parentTreeId
 * @returns {Boolean}
 */
function checkFoodTreeAmount2(treeId) {
    var flag = true;
    //检查该节点下是否配置有食材，如果有则不允许添加子节点，如果没有则可以。
    var url = URL.get("foodMaterial.CATALOG_CHECK_HAVE_FOOD");
    var params = "treeId=" + treeId;
    ajax.post(url, params, dataType.json,
    function(data) {
        flag = data.value;
    },
    false, false);
    return flag;
}
/**************************************************************ZTREE_END(添加页面)************************************************************/

//图片加载失败提供的默认图片
function errorImg(img){
	$(img).attr("src",PublicConstant.basePath+"/common/images/img_error.png");
}

function addFm(fmId, fmName) {
//	fmName = fmName.replace(/\s/g,"");
    layer.open({
        type: 1,
        title: '食物用量',
        content: "<div class='col-md-2'></div>" 
        			+ "<div class='form-group'>"
        			+ "<div class='input-group' class='col-md-3'>" 
        				+ "<div class='input-group-addon'>数量</div>" 
        				+ "<input class='form-control' type='text' id='appendInputCount' maxlength='8'  placeholder='请填写数量' onkeyup='gaga(this)' />" 
        				+ "<div class='input-group-addon'>g</div><div class='col-md-4'></div>" 
        			+ "</div>"
        			+ "</div>"
        			+ "<div class='form-group' style='position:absolute;top:57px;left:80px;'>" 
        				+ "<input name='appendRadio' type='radio' checked value='MAIN'>主料 <input name='appendRadio' type='radio' value='ACCESSORIES'>辅料" 
        			+ "</div>" 
        			+ "<div class='form-group'>" 
        				+ "<button class='btn btn-primary btn-block' style='width:60px;position:absolute;top:90px;left:150px;' onclick='addFoodExt(\"" + fmId + "\",\"" + fmName + "\")'>确定</button>"
        				+ "<button class='btn btn-default btn-block' style='width:60px;position:absolute;top:85px;left:220px;' onClick='layer.closeAll()'>取消</button>" 
        			+ "</div>",
        area: ['300px', '200px'],
        shadeClose: true
    });
}

/**
 * 删除数据
 */
function removeClick(foodId) {
    hint.confirm("确定执行【删除】操作？",
    function(data) {
    	$("#foodId").val('');
        var url = URL.get("Food.FOOD_CUISINE_REMOVE");
        var params = "foodId=" + foodId;
        ajax.post(url, params, dataType.json,
        function(data) {
            datatable.remove(foodTable, foodRow);
        });
    });
};

function removeFoodExt(foodId) {
    hint.confirm("确定执行【删除】操作？",
    function(data) {
        $("#tr" + foodId).remove();
        var index = "";
        for (var x = 0; x < fmIdArray.length; x++) {
            if (fmIdArray[x] == foodId) {
                index = x;
            }
        }
        fmIdArray.splice(index, 1);
        fmlTypeArray.splice(index, 1);
        fmlAmountArray.splice(index, 1);
        fmlMaterialArray.splice(index, 1);
        
        //元素计算
        caluateEle(foodId, '', fmMap, elMap, 'sub', '', 'drawEleDom');
        layer.closeAll();
    });
}

function updateFoodExt(foodId) {
    var liao = $("#tr" + foodId).find("*[name='foodLiao']").html(); // 主料/辅料
    var count = $("#tr" + foodId).find("*[name='foodCount']").html(); // 数量
    var radio = "";
    if (liao == "主料") {
        radio = "<input name='appendRadioUpdate' type='radio' checked value='MAIN'>主料 <input name='appendRadioUpdate' type='radio' value='ACCESSORIES'>辅料";
    } else if (liao == "辅料") {
        radio = "<input name='appendRadioUpdate' type='radio' value='MAIN'>主料 <input name='appendRadioUpdate' type='radio' checked value='ACCESSORIES'>辅料";
    } else {
    	radio = "<input name='appendRadioUpdate' type='radio' value='MAIN'>主料 <input name='appendRadioUpdate' type='radio' value='ACCESSORIES'>辅料";
    }
    layer.open({
        type: 1,
        title: '食物用量',
        content: "<div style='position:relative;'>" 
        			+ "<span style='position:absolute;top:17px;left:40px;'>数量</span>" 
        			+ "<input class='form-control' style='width:200px;float:right;margin-right:20px;margin-top:10px;' type='text' id='appendInputCountUpdate' maxlength='8' value='" + count + "'  placeholder='请填写数量'   onkeyup='gaga(this)' />" 
        			+ "<div style='position:absolute;top:57px;left:80px;'>" + radio + "</div>" 
        			+ "<button class='btn btn-primary btn-block' style='width:60px;position:absolute;top:90px;left:150px;' onclick='editFoodExt(\"" + foodId + "\")'>修改</button>"
        			+ "<button class='btn btn-default btn-block' style='width:60px;position:absolute;top:85px;left:220px;' onClick='layer.closeAll()'>取消</button>"
        			+ "</div>",
        area: ['300px', '180px'],
        shadeClose: true
    });
}

function editFoodExt(foodId) {
	
	var tag = ($('input:radio[name="appendRadioUpdate"]:checked').val());
	
	if (tag === undefined ) {
		hint.warning("请选择主料、辅料");
		return;
	}
	
	if ($("#appendInputCountUpdate").val()=='') {
		hint.warning("用量不能为空");
		return;
	}
	
	if (!isPositiveInteger($("#appendInputCountUpdate").val())) {
		hint.warning("请填入数字");
		$("#appendInputCountUpdate").val("");
		return;
	}
	
  	for (var x = 0; x < fmlAmountArray.length; x++) {
	  if (fmIdArray[x] == foodId) {
	      fmlAmountArray[x] = $("#appendInputCountUpdate").val();
	  }
	}
	for (var x = 0; x < fmlMaterialArray.length; x++) {
	  if (fmIdArray[x] == foodId) {
		  if ( $("*[name='appendRadioUpdate']:checked").val() == 'ACCESSORIES') {
			  fmlMaterialArray[x] = '辅料';
		  } else {
			  fmlMaterialArray[x] = '主料';
		  }
	      
	  }
	}
	
    $("#tr" + foodId).find("*[name='foodCount']").html($("#appendInputCountUpdate").val()); // 数量
    	var foodLiao = ""; //主辅料
    	if ($("*[name='appendRadioUpdate']:checked").val() == 'MAIN') {
    		foodLiao = "主料";
    	} else if  ($("*[name='appendRadioUpdate']:checked").val() == 'ACCESSORIES') {
    		foodLiao = "辅料";
    	} 
    $("#tr" + foodId).find("*[name='foodLiao']").html(foodLiao); // 主料/辅料
    
    //计算元素
    caluateEle(foodId, $("#appendInputCountUpdate").val(), fmMap, elMap, '', 'fmUpdate');
    layer.closeAll();
}

//js添加菜谱的食材组成原料
function addFoodExt(foodId, foodName) {
    if (common.isEmpty($("#appendInputCount").val())) {
        hint.warning("【数量】为必填项");
    } else if (!isPositiveInteger($("#appendInputCount").val())) {    	
		hint.warning("请填入数字");
		$("#appendInputCount").val("");
		return;
    } else {
        var count = $("#appendInputCount").val();
        var liao = $("*[name='appendRadio']:checked").val();
        if (liao == 'MAIN') {
        	liao = "主料";
        } else if (liao == 'ACCESSORIES'){
        	liao = "辅料";
        }
        $("#initExt").remove();
        if ($("#tr" + foodId).length == 0) {
            var exthtml = "<tr id='tr" + foodId + "'>" 
	            			+ "<td>" + foodName + "</td>" 
	            			+ "<td align='center' name='foodCount'>" + count + "</td>" 
	            			+ "<td align='center' name='foodLiao'>" + liao + "</td>" 
	            			+ "<td align='center'>" 
	            				+ "<a onclick=updateFoodExt('" + foodId + "')>编辑</a> "
	            				+ "<a onclick=removeFoodExt('" + foodId + "')>删除</a>" 
	            			+ "</td>" 
            			+ "</tr>";
            $("#addFoodExt").append(exthtml);
            fmIdArray.push(foodId);
            fmlTypeArray.push("primary");
            fmlAmountArray.push(count);
            fmlMaterialArray.push(liao);
            if ($("#fmIdTmp").val() == null || $("#fmIdTmp").val() == "") {
                $("#fmIdTmp").val(foodId);
            } else {
                $("#fmIdTmp").val($("#fmIdTmp").val() + "," + foodId);
            }
            // 调用计算元素方法
            caluateEle(foodId, count, this.fmMap, this.elMap, "add", '', 'drawEleDom');
                        
            layer.closeAll();
        } else {
            layer.alert("不能重复添加");
        }
    }
}

function subForm() {
	$("#fmId").val(fmIdArray.toString());
    $("#fmlAmount").val(fmlAmountArray.toString());
    $("#fmlType").val(fmlTypeArray.toString());
    $("#fmlMaterial").val(fmlMaterialArray.toString());     
    $("#addCuisineForm").submit();    
}

/**
 * 初始化标签插件
 * 
 * @author scd
 * @param fmAliasArray
 */
function tagEditorInit(fmAliasArray) {
    //标签插件初始化 请输入食品别名
    $("[name='foodAlias']").tagEditor({
        initialTags: fmAliasArray,
        onChange: function(field, editor, tags) {},
        delimiter: '， ',
        autocomplete: {
            delay: 0,
            position: {
                collision: 'flip'
            },
            source: []
        },
        placeholder: '请输入别名'
    }).css('display', 'block').attr('readonly', true);
    $(".tag-editor").addClass("form-control");
}

/**
 * 初始化
 */
$().ready(function() {
    // 初始化datatables
    foodTable = datatable.table(foodTableOption);    

    // 按钮点击事件
    $("button[name='clickButton']").click(function() {
        var foodId = $("#foodId").val();
        if (this.id == 'addButton') {
            // fmID
            fmIdArray = [];
            // fmlType
            fmlTypeArray = [];
            // fmlAmount
            fmlAmountArray = [];
            // fmlMaterial
            fmlMaterialArray = [];
            initDdlHtmlFunc(null);
            //common.pageForward(PublicConstant.basePath + "/page/food/food_cuisine_add_init.action");
            common.clearForm("addCuisineForm");
            $("#addFoodExt tr").remove();
            $("#fmPicOld").val("");
			//初始化标签插件
			$("[name='foodAlias']").tagEditor("destroy");// 清空标签
			var fmAliasArray=[];
			tagEditorInit(fmAliasArray);
            $("#addCuisineForm").attr("action", PublicConstant.basePath + "/page/food/food_cuisine_add.action");
            $('#showpic').attr("src", PublicConstant.basePath + "/common/images/uplod_img.jpg");
            $("#showDiv").css("display", "none");
            $("#addDiv").css("display", "block");            
        }
        if (this.id == "editButton" && common.isChoose(foodId)) {
            fmIdArray = [];
            fmlTypeArray = [];
            fmlAmountArray = [];
            fmlMaterialArray = [];
            $("#addCuisineForm").attr("action", PublicConstant.basePath + "/page/food/food_cuisine_update.action");
            common.clearForm("addCuisineForm");
            //标签插件初始化 请输入食品别名
            $("[name='foodAlias']").tagEditor("destroy"); // 清空标签
        	var fmAliasArray = [];
        	if (foodData.foodAlias != null) {
        		fmAliasArray = foodData.foodAlias.split("，");       		
        	}        
        	tagEditorInit(fmAliasArray);
            $("#fmPicOld").val(foodData.foodPic);
            $("#addCuisineForm").jsonToForm(foodData);
            if (foodData.foodBenefit != null) {
                if (foodData.foodBenefit.indexOf("30") > -1) {
                    $("#addCuisineForm [name='foodBenefitList']:eq(0)").attr("checked", true);
                }
                if (foodData.foodBenefit.indexOf("31") > -1) {
                    $("#addCuisineForm [name='foodBenefitList']:eq(1)").attr("checked", true);
                }
                if (foodData.foodBenefit.indexOf("32") > -1) {
                    $("#addCuisineForm [name='foodBenefitList']:eq(2)").attr("checked", true);
                }
                if (foodData.foodHarm.indexOf("50") > -1) {
                    $("#addCuisineForm [name='foodHarmList']:eq(0)").attr("checked", true);
                }
                if (foodData.foodHarm.indexOf("51") > -1) {
                    $("#addCuisineForm [name='foodHarmList']:eq(1)").attr("checked", true);
                }
                if (foodData.foodHarm.indexOf("52") > -1) {
                    $("#addCuisineForm [name='foodHarmList']:eq(2)").attr("checked", true);
                }
                if (foodData.foodHarm.indexOf("53") > -1) {
                    $("#addCuisineForm [name='foodHarmList']:eq(3)").attr("checked", true);
                }
                if (foodData.foodHarm.indexOf("54") > -1) {
                    $("#addCuisineForm [name='foodHarmList']:eq(4)").attr("checked", true);
                }
            }
            $("#addFoodId").val(foodData.foodId);
            $('#showpic').attr("src", PublicConstant.basePath + "/resource/upload/food/cuisine/" + foodData.foodPic);
            //分级列表加载
            initDdlHtmlFunc(common.isEmpty(foodData.foodTreeType) ? null: foodData.foodTreeType);
            var url = URL.get("Food.FOOD_QUERY_MATERIAL");
            var params = "foodId=" + foodData.foodId;
            ajax.post(url, params, dataType.json,
            function(data) {
                result = data.data;
                if (result.length > 0) {
                    for (var x = 0; x < result.length; x++) {
                        var exthtml = "<tr id='tr" + result[x].fmId + "'>" 
	                        			+ "<td>" + result[x].fmName + "</td>" 
	                        			+ "<td align='center' name='foodCount'>" + result[x].fmlAmount + "</td>" 
	                        			+ "<td align='center' name='foodLiao'>" + result[x].fmlMaterial + "</td>" 
	                        			+ "<td align='center'>" 
	                        				+ "<a onclick=updateFoodExt('" + result[x].fmId + "')>编辑</a> " 
	                        				+ "<a onclick=removeFoodExt('" + result[x].fmId + "')>删除</a>" 
	                        			+ "</td>" 
                        			+ "</tr>";
                        $("#addFoodExt").append(exthtml);
                        fmIdArray.push(result[x].fmId);
                        fmlTypeArray.push("primary");
                        fmlAmountArray.push(result[x].fmlAmount);
                        fmlMaterialArray.push(result[x].fmlMaterial);
                    }
                }
            },
            false, false);
            
            //元素加载
            initEle(foodId);
            
            $("#showDiv").css("display", "none");
            $("#addDiv").css("display", "block");
        }
        if (this.id == 'removeButton' && common.isChoose(foodId)) {
            removeClick(foodId);
        }
        if (this.id == 'backButton') {
        	$("#elmentList").empty();
            $("#foodId").val("");
            foodTable = datatable.table(foodTableOption);
            returnList();
        }
		if (this.id == "searchButton") {
			$("#foodId").val("");
			foodTable = datatable.table(foodTableOption);
			setDH();
		}
    });
    $("#addCuisineForm").validate(cuisineOperation);
    //加入必填项提示
    common.requiredHint("addCuisineForm", cuisineOperation);
    //初始化烹饪方式
    common.initCodeSelect("COKE_MODE", "COKE_MODE", "foodCuisine");
    //初始化标签插件
    var fmAliasArray = [];
    tagEditorInit(fmAliasArray);
    // ZTree(一览)
    // 类型表单验证 
    catalogValidator = $("#addCatalogForm").validate(menuAddOptions);
    common.requiredHint("addCatalogForm", menuAddOptions);
    $.fn.zTree.init($("#zTree"), setting, zNodes);
    treeObj = $.fn.zTree.getZTreeObj("zTree");
    treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);

    // ZTree(添加)
    // 类型表单验证 
    fmTable = datatable.table(fmdatableOption);
    // 调节div高度
    setDH();
    $("[treenode_switch]").live('click',
    function() {
        setDH();
    });
    catalogValidator2 = $("#addCatalogForm2").validate(menuAddOptions2);
    common.requiredHint("addCatalogForm2", menuAddOptions2);
    $.fn.zTree.init($("#zTree_add_page"), setting2, zNodes2);
    treeObj2 = $.fn.zTree.getZTreeObj("zTree_add_page");
    treeObj2.expandNode(treeObj2.getNodes()[0], true, false, false, true);
    //上传图片
    // 初始化 uploadifive 上传控件
    common.uploadifive("上传食物图片",
    function(data) {
        //var food = JSON.parse(data);
        $('#foodPic').val(data);
        $('#showpic').attr("src", data);
    });
    
    $("#searchss").click(function(){
//    	 $("#foodId").val("");
    	 fmTable = datatable.table(fmdatableOption);
    });
   
});

/**
 * 分级列表的初始化
 * @param value
 */
function initDdlHtmlFunc(value) {
    var url = URL.get("Food.FOOD_CATALOG_QUERY_ALL");
    ajax.post(url, null, dataType.json,
    function(result) {
        common.initDdlHtml(result.data, "productCategoryDiv", value,
	        function(data) {
	            if (!common.isEmpty(data.childList)) {
	                layer.alert("您选择的菜谱类型还有下级，请继续选择");
	                return false; //不会有内容被选中
	            }
	            //修改节点选中的状态，重新加载datatable,方便添加完
	            var node = treeObj.getNodeByParam("id", data.id);
	            treeObj.selectNode(node);
	            $("#foodType").val(data.id);
	        });
    },
    false, false);
}


function gaga(obj){ // 值允许输入一个小数点和数字 
	obj.value = obj.value.replace(/[^\d]/g,""); //先把非数字的都替换掉，除了数字和. 
//	obj.value = obj.value.replace(/^\./g,""); //必须保证第一个为数字而不是. 
//	obj.value = obj.value.replace(/\.{2,}/g,"."); //保证只有出现一个.而没有多个. 
//	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); //保证.只出现一次，而不能出现两次以上 
//	var re = /([0-9]+\.[0-9]{1})[0-9]*/;
//	obj.value = obj.value.replace(re,"$1");

	} 

function isPositiveInteger(s){//是否为正整数
    var re = /^[0-9]+$/ ;
    return re.test(s)
}  

/**
 * 验证结点下是否有子结点
 * @param treeId
 * @param type
 * @returns {Boolean}
 */
function checkHaveSub(treeId, type){
	var flag =true;
	//检查该节点下是否配置有食材，如果有则不允许添加子节点，如果没有则可以。
	var url = URL.get("foodMaterial.CATALOG_CHECK_HAVE_SUB");
	var params = "treeId=" + treeId + "&type=" + type;
	ajax.post(url, params, dataType.json, function(data) {
		flag=data.value;
	},false,false);
	return flag;
}

/**
 * 返回到列表页
 */
function returnList(){
    $("#showDiv").css("display", "block");
    $("#addDiv").css("display", "none");
    $("#addFoodExt").empty(); 
    setDH();
}

/**
 * 初始化元素的html
 */
function drawEleDom() {
	$("#elmentList").empty();
	$.each(elementArray, function(index, value){
		$("#elmentList").append(
				'<label class="col-xs-1 control-label paddingBottom">' + value.nutrientName + '</label>' +
				'<div class="col-xs-2 paddingBottom">'+
	            '  <div class="input-group">'+
	            '    <input name="productElementList[' + index + '].nutrientId" value="'+value.nutrientId+'"  type="hidden"/>'+
	            '    <input type="text" id="'+value.nutrientId+'" value="'+value.nutrientDosage+'" class="form-control text-right" type="text" maxlength="5" twoDigit="true"/>'+
	            '    <div class="input-group-addon">'+CODE.transCode("productUnit", value.nutrientUnit)+'</div>'+
	            '  </div>'+
	    		'</div>'
		);
	});		
}


