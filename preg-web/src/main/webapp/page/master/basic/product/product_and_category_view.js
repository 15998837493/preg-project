//*****************************************商品的类别开始****************************************
//表单验证插件
var operateType;
var validator;// 商品验证
var validatorC;// 商品类别验证
var productOption = {
	rules : {
		productName : {
			required : true,
			maxlength : 200,
			remote : {
				url : URL.get("Master.PRODUCT_PRODUCTNAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					productName : function(){
						return $("#productName").val();
					},
					productNameOld : function(){
						return $("#productNameOld").val();
					},
					operateType : function(){
						return operateType;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		productMemoryMark : {
			required : true,
			maxlength : 200
		},
		productPurchasePricestr : {
			required : true,
			twoDigit : true
		},
		productSellPricestr : {
			required : true,
			twoDigit : true
		}
	},
	messages : {
		productName : {
			remote : "商品名称已存在，请重新输入"
		}		
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		if(common.isEmpty($("#productCategory").val().trim())){
			layer.alert("类别不可以为空");
			return;
		}
		$(form).ajaxPost(dataType.json, function(data){
			productTable = datatable.table(productTableOptions);//加载datatable
			$("#editProductModal").modal("hide");
			$("#productId").val("");
			validator.resetForm();
		}, false, false);
	}
}
//商品的datatable
var productData;// 选中项信息
var productRow;// 选中行信息
var productTable;// table，注意命名不要重复

//配置datatable
var productTableOptions = {
	id : "productListTable",
	form : "queryForm",
	columns : [{"data" : null,
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return "<input type='radio' name='tableRadio' value='"+data.productId+"' />";
				}
			},
			{
				"data" : null,
				"sClass" : "text-left",
				"render" : function(data, type, row, meta) {
					return "<img class='img-thumbnail img-responsive' style='width:40px;height:40px;' onclick='showImg(this.src)' onerror='errorImg(this)' src='"+PublicConstant.basePath+"/resource/upload/product/ext/"+data.productPic+"'/> "+nameDetailOnMouseover(data.productId, data.productName);
				}
			}, {
				"data" : "productUnit",
				"sClass" : "text-center"
			}, {
				"data" : "productDosageDesc",
				"sClass" : "text-center"
			}, {
				"data" : "productFrequency",
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return CODE.transCode("PRODUCTFREQUENCY", data);
				}
			}, {
				"data" : "productUsageMethod",
				"sClass" : "text-center"
			}, {
				"data" : "productStandard",
				"sClass" : "text-center"
			},  {
				"data" : "productSellPrice",
				"sClass" : "text-center",
				"render": function(data, type, row, meta) {
					return data/100;
				}
			}
	],
	rowClick : function(data, row) {
		productData = data;
		productRow = row;
		$("#productId").val(data.productId);
	},
	condition : "productCondition",
	async : false,
	loading : false
};
/**
 * 添加商品的model
 */
function addProduct(){
	initDdlHtmlFunc(null);
	operateType = "add";
	common.clearForm("productDiv");
	$('#showpic').attr("src", "");
	$('#productPic').val("");
	$("#productElementDiv input[id^='E']").val("");//只清空元素部分的输入框，不清空隐藏的input
	$("#editProductForm").attr("action", URL.get("Master.PRODUCT_ADD"));
	initDdlHtmlFunc(null);
	//初始化datalist标签的内容（因为要异步添加需要每次都更新dataList标签内容）
	common.initSelectAndInput("productUnitList", "mas_product", "product_unit");
	common.initSelectAndInput("productUsageMethodList", "mas_product", "product_usage_method");
	common.initSelectAndInput("productAreaList", "mas_product", "product_area");
	$("#editProductModal").modal("show");
}
/**
 * 编辑按钮的方法
 */
function updateProduct(id){
	operateType = "update";
	common.clearForm("productDiv");
	$("#productElementDiv input[id^='E']").val("");//只清空元素部分的输入框，不清空隐藏的input
	$("#editProductForm").attr("action", URL.get("Master.PRODUCT_UPDATE"));
	//初始化datalist标签的内容（因为要异步添加需要每次都更新dataList标签内容）
	common.initSelectAndInput("productUnitList", "mas_product", "product_unit");
	common.initSelectAndInput("productUsageMethodList", "mas_product", "product_usage_method");
	common.initSelectAndInput("productAreaList", "mas_product", "product_area");
	//初始化商品部分的内容
	$("#editProductForm").jsonToForm(productData);
	$("#productNameOld").val(productData.productName);
	var node = treeObj.getNodeByParam("id",productData.productCategory);
	//异步获取商品类别
	var catalogName = common.isEmpty(node)?null:node.name;
	initDdlHtmlFunc(catalogName);
	$("#productPurchasePricestr").val(productData.productPurchasePrice/100);
	$("#productSellPricestr").val(productData.productSellPrice/100);
	$("#productCalculation").val(productData.productCalculation);
	//初始化商品元素部分
	var url = URL.get("Master.QUERY_DETAIL_PRODUCT_ELEMENT");
	var params = "productId=" + id;
	ajax.post(url, params, dataType.json, function(data) {
		if(!common.isEmpty(data)){
			$.each(data.data, function(index, value){
				$("#"+value.nutrientId).val(value.nutrientDosage);
				$("#"+value.nutrientId+'_remark').val(value.nutrientRemark);
			});
			//处理图片
			$('#showpic').attr("src", data.value);
			var picName = data.value.split("/");
			if(common.isEmpty(picName[picName.length-1])){
				$('#productPic').val("");
			}else{
				$('#productPic').val(picName[picName.length-1]);
			}
		}
	}, false, false);
	$("#editProductModal").modal("show");
}
/**
 * 通过id删除商品
 */
function removeClick(id) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		var url = URL.get("Master.REMOVE_PRODUCT");
		var params = "id=" + id;
		ajax.post(url, params, dataType.json, function(data) {
			layer.alert("删除成功");
			$("#productId").val("");
			datatable.remove(productTable, productRow);// 删除
		}, false, false);
	});
};
//*****************************************商品类别部分开始****************************************
//类型操作
var opernerCatalog;
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
			$("#categoryIdQuery").val(treeNode.value.catalogId);
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
		} else {
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
			$("#categoryIdQuery").val(null);
		}
		// 加载对应的指标信息
		productTable = datatable.table(productTableOptions);//加载datatable
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
		showRemoveBtn : function showRemoveBtn(treeId, treeNode) {return treeNode.id!="000" && treeNode.id !="000000000";},
		showRenameBtn: function showRenameBtn(treeId,treeNode){return treeNode.id!="000" && treeNode.id !="000000000";},
		removeTitle : "删除类别",
		renameTitle : "编辑类别",
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeRemove : beforeRemove,
		beforeEditName: zTreeBeforeEditName,
		onClick: zTreeOnClick,
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
					catalogName : function() {
						return $("#catalogName").val();
					},
					catalogNameOld : function() {
						return $("#catalogNameOld").val();
					},
					opernerCatalog : function() {
						return opernerCatalog;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		}
	},
	messages : {
		catalogName : {
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
			$('#addMenuForm').ajaxPost(dataType.json, function(data) {
				if("update" == opernerCatalog){
					selectNode.name = $("#catalogName").val();
					treeObj.updateNode(selectNode);
					layer.alert("修改成功");
				}else{
					treeObj.addNodes(selectNode, data.value);
					var pNode = treeObj.getNodeByParam("id", data.value.pId);
					pNode.iconSkin="mulu";
					treeObj.updateNode(pNode);
					layer.alert("添加成功");
				}
				common.clearForm("addMenuForm");
				$("#addMenuModal").modal("hide");
				validatorC.resetForm();
			});
	}
};

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

// 删除结点回调函数
function beforeRemove(treeId, treeNode) {
	if("000000000" == treeNode.id){
		layer.alert("该节点不可以删除");
		return;
	}
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
	if(treeNode.id.length >6){
		return;
	}
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0){
		return;
	}
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加功能菜单' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn){
		btn.bind("click", function() {
			treeObj.selectNode(treeNode);// 选中
			zTreeOnClick(null, treeId, treeNode);
			selectNode = treeNode;
			if(!selectNode.isParent){
				layer.confirm('如果修改类别，此类别下所有商品的类别变更成【未分类】，是否确认修改？', {btn: ['是','否']},function(index){
					layer.close(index);
					$("#addMenuForm").attr("action",URL.get("Customer.PRODUCT_CATALOG_ADD"));
					opernerCatalog = "add";
					setAddFormContent();// 初始化添加表单
					$("#addMenuModal").modal("show");
				},function(index){
					layer.close(index);
				});
			}else{
				$("#addMenuForm").attr("action",URL.get("Customer.PRODUCT_CATALOG_ADD"));
				opernerCatalog = "add";
				setAddFormContent();// 初始化添加表单
				$("#addMenuModal").modal("show");
			}
		});
	}
};

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};

// *****************************************自定义开始****************************************
// 设置目录高度
function setDH() {
	$("#left_div").css("height", "auto");
	$("#cust_div").css("height", "auto");
	var hLeft = $("#left_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft > hRight ? hLeft : hRight;
	$("#left_div").height(max);
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
/**
 * 点击节点是产讯指定的商品
 */
function zTreeOnClick(event, treeId, treeNode){
	selectNode = treeNode;
	treeObj.selectNode(treeNode);
	var id = treeNode.id;
	if("000" == id){
		id = "";
	}
	if("000000000" == id){
		id = " ";
	}
	$("#categoryIdQuery").val(id);
	productTable = datatable.table(productTableOptions);//加载datatable
	setDH();
}
/**
 * 右键修改名称
 */
function zTreeBeforeEditName(treeId, treeNode){
	treeObj.selectNode(treeNode);
	selectNode = treeNode;
	if("000000000" == treeNode.id){
		layer.alert("该节点不能修改名称");
		return;
	}
	zTreeOnClick(null, treeId, treeNode);
	$("#addMenuForm").attr("action",URL.get("Customer.PRODUCT_CATALOG_UPDATE"));
	$("#addMenuForm").jsonToForm(treeNode.value);
	$("#catalogNameOld").val(treeNode.value.catalogName);
	opernerCatalog = "update";
	$("#addMenuModal").modal("show");
	setDH();
}
//*****************************************公共****************************************
$().ready(function() {
	//商品类别部分
	// 表单校验
	validatorC = $("#addMenuForm").validate(menuAddOptions);
	common.requiredHint("addMenuForm", menuAddOptions);
	// 初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// ztree树拓展搜索框
	common.ztreeSearch(treeObj, zTree_1, "left_div");
	// 调节div高度
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	setNoSelect();
	
	//商品部分
	//商品的必填验证
	validator = $("#editProductForm").validate(productOption);
	common.requiredHint("editProductForm", productOption);
	//初始化下拉选
	common.initCodeSelect("PRODUCTFREQUENCY", "PRODUCTFREQUENCY", "productFrequency");
	productTable = datatable.table(productTableOptions);//加载datatable
	// 初始化 uploadifive 上传控件
	common.uploadifive("选择图片",function(data){
		$('#productPic').val(data);
		$('#showpic').attr("src", data);
	});
	//初始化元素的html
	$("#elmentList").empty();
	$.each(elementList, function(index, value){
		$("#elmentList").append(
				'<label class="col-xs-2 control-label paddingBottom">' + value.nutrientName + '</label>'+
				'<div class="col-xs-2 paddingBottom">'+
                '  <div class="input-group">'+
                '    <input name="productElementList[' + index + '].nutrientId" value="'+value.nutrientId+'" type="hidden"/>'+
                '    <input type="text" id="'+value.nutrientId+'" name="productElementList[' + index + '].nutrientDosage" class="form-control text-right" type="text" maxlength="5" twoDigit="true"/>'+
                '    <div class="input-group-addon">'+CODE.transCode("productUnit", value.nutrientUnit)+'</div>'+
                '  </div>'+
	    		'</div>'+
	    		'<div class="col-xs-2 paddingBottom">'+
                '    <input type="text" id="'+value.nutrientId+'_remark" name="productElementList[' + index + '].nutrientRemark" class="form-control text-right" type="text" maxlength="300" placeholder="请输入备注"/>'+
                '</div>'
		);
	});
	//定义按钮时间
	$("button[name='productButton']").click(function() {
		var id = $("#productId").val();
		if (this.id == 'addButton') {
			//添加商品的model
			addProduct();
		}
		if (this.id == 'editButton' && common.isChoose(id)) {
			updateProduct(id);
		}
		if (this.id == 'removeButton' && common.isChoose(id)) {
			removeClick(id);
		}
		if (this.id == "searchButton") {
			productTable = datatable.table(productTableOptions);
		}
	});
	//关闭遮罩层
	layer.close(layer.index);
});
/**
 * 分级列表的初始化
 * @param value
 */
function initDdlHtmlFunc(value){
	var url = URL.get("Master.PRODUCT_CATALOG_QUERY_ALL");
	ajax.post(url, null, dataType.json, function(result) {
		common.initDdlHtml(result.data, "productCategoryDiv", value, function(data){
			if(!common.isEmpty(data.childList)){
				layer.alert("请选择叶子节点作为商品类别");
				return false;//不会有内容被选中
			}
			$("#productCategory").val(data.id);
			//修改节点选中的状态，重新加载datatable,方便添加完
			var node = treeObj.getNodeByParam("id", data.id);
			treeObj.selectNode(node);
			$("#categoryIdQuery").val(data.id);
		});
	}, false, false);
}

/**
 * 浮窗显示
 * @param code
 * @param content
 * @returns {String}
 */
function nameDetailOnMouseover(code, content){
	return "<a id='mark_"+code+"' data-toggle='tooltip' data-placement='left' title='"+content+"'>" + content + "</a>";
}

/**
 * 图片加载失败提供的默认图片
 */
function errorImg(img){
	$(img).attr("src",PublicConstant.basePath+"/common/images/img_error.png");
}

/**
 * 弹出图片
 */
function showImg(path){
	layer.open({
	  type: 1,
	  title: false,
	  closeBtn: 0,
	  area: "auto",
	  skin: 'layui-layer-nobg', //没有背景色
	  shadeClose: true,
	  content: "<div><img onerror='errorImg(this)'  src='"+path+"' alt='图片加载失败！'   class='img-responsive center-block'></div>"
	});
}