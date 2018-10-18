$.validator.addMethod("range", function(value,element) {
	if(element.value.trim()==""){
		return true;
	}
	return element.value <= 100 && element.value>=1;
}, '只能输入1-100之间的整数');
var fmdatableOption = {
	id : "fmListTable",
	form : "fmQuery",
	bServerSide: true,
	columns : [
	        {"data" : null,"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return "<input type='radio' name='tableRadio' value='"+data.fmId+"' />";
				}
			}, 
			{"data" : null,"sClass" : "text-left",
				"render" : function(data, type, row, meta) {
					/*return "<i class='fa fa-eye' onclick='showImg(\""+PublicConstant.basePath+"/resource/upload/food/ext/"+data.fmPic+"\");' aria-hidden='true'></i> "+data.fmName*/
					var fmName=data.fmName;
					var template1="<a id='dataFmAlias' href='#' title='"+data.fmName+"'>"+fmName+"</a>";
		            var template2="<a id='dataFmAlias' href='#' title='"+data.fmName+"'>"+fmName+"</a>";	
		            return fmName;
							   
				}
			}, 
			{"data" : null,"sClass" : "text-left",
				"render" : function(data, type, row, meta) {
					var fmAlias=data.fmAlias;
					if (fmAlias != null && fmAlias != '') {
			            if (fmAlias.length >= 15) {
			            	return "<a id='dataFmAlias' href='#' title='"+data.fmAlias+"'>"+fmAlias.substring(0,15)+"......" +"</a>";
			            }else{
			                return "<a id='dataFmAlias' href='#' title='"+data.fmAlias+"'>"+fmAlias+"</a>";	
				        }
					} else {
						return "______";
					}
				}
			},
			{"data" : "treeName","sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return data == null?"______":data;
				}
			}, 
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					if (Array.prototype.isPrototypeOf(data.productElementList) && data.productElementList.length === 0) {
						return "______";
					}
					return data.productElementList.find(findElement) == null?"______":data.productElementList.find(findElement).nutrientDosage;
				}
			},
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					if (Array.prototype.isPrototypeOf(data.productElementList) && data.productElementList.length === 0) {
						return "______";
					}
					return data.productElementList.find(findElementProtein) == null?"______":data.productElementList.find(findElementProtein).nutrientDosage;
				}
			},
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					if (Array.prototype.isPrototypeOf(data.productElementList) && data.productElementList.length === 0) {
						return "______";
					}
					return data.productElementList.find(findElementFat) == null?"______":data.productElementList.find(findElementFat).nutrientDosage;
				}
			},
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					if (Array.prototype.isPrototypeOf(data.productElementList) && data.productElementList.length === 0) {
						return "______";
					}
					return data.productElementList.find(findElementCarbo) == null?"______":data.productElementList.find(findElementCarbo).nutrientDosage;
				}
			},
	],
	rowClick : function(data, row) {
		fmData = data;
		fmRow = row;
		$("#fmId").val(data.fmId);
		$("#id").val(data.fmId);
	},
	callbackpage:function(option){
		icheck.controlChecked({flag:false,checkname:"tableRadio"});
		$("#fmId").val("");
		$("#id").val("");
	},
	condition : "fmCondition",
	async: false,
	loading: false // 是否启用遮罩层
};

//能量
function findElement(x) {
  return x.nutrientId == "E00001";
}
//蛋白质
function findElementProtein(x) {
  return x.nutrientId == "E00002";
}
//脂肪
function findElementFat(x) {
  return x.nutrientId == "E00003";
}
//碳水化合物
function findElementCarbo(x) {
  return x.nutrientId == "E00004";
}

/**
 * 删除食材
 * @param fmId
 */
function removeClick(fmId) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		var url = URL.get("foodMaterial.FOOD_MATERIAL_REMOVE");
		var params = "fmId=" + fmId;
		ajax.post(url, params, dataType.json, function(response) {
			if(response.result){
				datatable.remove(fmTable, fmRow);
				hint.success("删除成功");
			}else{
				hint.success(response.message);
			}
		});
		layer.close(index);
	});
};

/**
 * 验证参数设置,食材表单验证
 */
var fmOperation = {
	rules : {
		fmName : {
			required : true,
			maxlength : 80
		},
		fsId : {
			required : true
		},
		fmPurin : {
			required : true,
			number : true
		},
		fmGi : {
			required : true,
			number : true
		},
		fmFat : {
			required : true
		},
		fmEnergy : {
			required : true
		},
		fmProtid : {
			required : true
		},
		fmCbr : {
			required : true
		},
		fmEsculent : {
			required : true,
			number : true,
			min : 0,
			max : 100
		}
	},
	//设置错误信息显示到指定位置
	errorClass:"text-danger",
	errorContainer:"em",
	errorPlacement : function(error, element) {
 		error.insertAfter(element.parent()); 
	},
	errorElement: "span",
	success : $.noop,
	submitHandler : function() {
	   if($("#fmType").val() == ' '){
			layer.msg('食材类别为必填项！');
			return;
	   }		
	   if(common.isEmpty($("#fmType").val()) ){
			layer.msg('食材类别为必填项！');
			return;
	   }
   	  if($("[name='fmAlias']").val().length>200){
   		  layer.msg("别名最大只能输入200个字符！");
   		  return;
   	  	}
		layer.confirm("确定要执行【保存】操作？",function(data) {
			if (data) {
				$('#editForm').ajaxPost(dataType.json,function(response) {
					if(response.result){
						returnList();
						setDH();
						hint.success("保存成功！");
					}
				});
			}
		});
	}
};
	

/**
 * 验证设置，类型表单验证
 */
var menuAddOptions = {
	rules : {
		treeName : {
			required : true,
			maxlength : 50,
			remote : {
				url : URL.get("foodMaterial.CATALOGINFO_NAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					treeName : function() {
						return $("#treeName").val();
					},
					treeNameOld : function() {
						return $("#treeNameOld").val();
					},
					treeId : function() {
						return $("#categoryIdQuery").val();
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
		treeName : {
			remote : "该类别名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorClass:"text-danger",
	errorElement:"em",
	errorContainer:"em",
	errorPlacement : function(error, element) {
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
			$('#addCatalogForm').ajaxPost(dataType.json, function(data) {
				if("update" == opernerCatalog){
					selectNode.name = $("#treeName").val();
					treeObj.updateNode(selectNode);
					layer.msg("修改成功");
				}else{
					treeObj.addNodes(selectNode, data.value);
					var pNode = treeObj.getNodeByParam("id", data.value.pId);
					pNode.iconSkin="mulu";
					treeObj.updateNode(pNode);
					layer.msg("添加成功");
				}
				catalogValidator.resetForm();
				common.clearForm("addCatalogForm");
				$("#addCatalogModal").modal("hide");
			});
	}
};

/**************************************************************ZTREE_BEGIN************************************************************/

//配置
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
		fmTable = datatable.table(fmdatableOption);
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
		showRemoveBtn : function showRemoveBtn(treeId, treeNode) {return treeNode.id != "000" && treeNode.id !="000000000";},
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

function filter(treeId, parentNode, responseData) {
	if (!responseData)
		return null;
	for (var i = 0, l = responseData.length; i < l; i++) {
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
	var url = URL.get("foodMaterial.CATALOGINFO_UPDATE_ORDER");
	var params = "menuIds=" + menuIds;
	ajax.post(url, params, dataType.json, function(data) {
		if (data.result) {
			layer.msg("修改排序成功！");
		} else {
			layer.msg(data.message);
		}
	}, false);
	beforeClick(treeId, treeObj.getSelectedNodes()[0]);
}

//删除结点回调函数
function beforeRemove(treeId, treeNode) {
	if("000" == treeNode.id){
		layer.alert("该节点不可以删除");
		return;
	}
	if (!checkHaveSub(treeNode.id,"food_material")) {
		layer.alert("该节点下有子目录不可以删除");
		return;
	}
	if(checkFoodTreeAmount(treeNode.id)){
		var content = treeNode.value.menuType == 1 ? "确定要删除目录【" + treeNode.name
				+ "】以及该目录下的所有菜单？" : "确定要删除菜单【" + treeNode.name + "】？";
		layer.confirm(content, function(data) {
			var url = URL.get("foodMaterial.CATALOGINFO_REMOVE");
			var params = "treeId=" + treeNode.id;
			ajax.post(url, params, dataType.json, function(data) {
				if (data.result) {
					setNoSelect();
					treeObj.removeChildNodes(treeNode);
					treeObj.removeNode(treeNode);
					var pNode = treeObj.getNodeByParam("id", treeNode.pId);
					pNode.iconSkin="menu";
					treeObj.updateNode(pNode);
					layer.msg("删除成功！");
				} else {
					layer.msg(data.message);
				}
			});
		});
	}else{
		layer.msg("该节点不能删除！");
	}
}
//添加结点
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
			common.clearForm("addCatalogForm");
			//该类别下有食材配置不能添加子节点
			if(checkFoodTreeAmount(treeNode.id)){
				$("#addCatalogForm").attr("action",URL.get("foodMaterial.CATALOGINFO_ADD"));
				opernerCatalog = "add";
				setAddFormContent();// 初始化添加表单
				$("#addCatalogModal").modal("show");
			}else {
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
function zTreeBeforeEditName(treeId, treeNode){
	var node = treeObj .getNodeByTId(treeNode.tId);
	treeObj.selectNode(treeNode);
	selectNode = treeNode;
	if("000000000" == treeNode.id){
		layer.alert("该节点不能修改名称");
		return;
	}
	zTreeOnClick(null, treeId, treeNode);
	common.clearForm("addCatalogForm");
	$("#addCatalogForm").attr("action",URL.get("foodMaterial.CATALOGINFO_UPDATE"));
	$("#addCatalogForm").jsonToForm(treeNode.value);
	$("#treeName").val(node.name);
	$("#treeNameOld").val(treeNode.value.treeName);
	opernerCatalog = "update";
	$("#addCatalogModal").modal("show");
	setDH();
}

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
	//datatable
	fmTable = datatable.table(fmdatableOption);
	setDH();
}

//设置目录高度
function setDH() {
  $("#left_div").css("height", "auto");
  $("#cust_div").css("height", "auto");
  var hLeft = $("#left_div").height();
  var hRight = $("#cust_div").height();
  var max = hLeft > hRight ? hLeft: hRight;
  $("#left_div").height(max + 50);
  $("#cust_div").height(max + 50);
  listTableLengthChange();
}

//未选中或选中最顶级节点时设置
function setNoSelect() {
	$("#submitButton").attr("disabled", true);
	common.clearForm("updateForm");
}

//设置addMenuForm表单内容
function setAddFormContent() {
	$("#addCatalogForm [name='treeOrder']").val(
			common.isEmpty(selectNode.children) ? 1
					: selectNode.children.length + 1);
	$("#addCatalogForm [name='parentTreeId']").val(selectNode.id);
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
 * 验证食材类别下是否已经配置食材
 * 
 * @param parentTreeId
 * @returns {Boolean}
 */
function checkFoodTreeAmount(treeId){
	var flag =true;
	//检查该节点下是否配置有食材，如果有则不允许添加子节点，如果没有则可以。
	var url = URL.get("foodMaterial.CATALOG_CHECK_HAVE_FOOD");
	var params = "treeId=" + treeId;
	ajax.post(url, params, dataType.json, function(data) {
		flag=data.value;
	},false,false);
	return flag;
}
/**************************************************************ZTREE_END************************************************************/

//图片加载失败提供的默认图片
function errorImg(img){
	$(img).attr("src",PublicConstant.basePath+"/common/images/img_error.png");
}
//弹出图片
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

function checkInputNumber(reg, value, length) {
	var result;
	if (length == 0) {
		result = common.numberRegs["reg6"].exec(value) || [ "" ];// 返回符合条件的数值
		if (!_.isEmpty(result[0])) {
			layer.msg("只能保留整数部分！");
		}
		return result[0];
	}
	result = common.numberRegs[reg].exec(value) || [ "" ];// 返回符合条件的数值
	if (!_.isEmpty(length) && length >= 1) {
		if (result[0].toString().split(".").length > 1 && result[0].toString().split(".")[1].length > length) {
			layer.msg("小数点后只能保留" + length + "位！");
			result[0] = result[0].substring(0, (result[0].indexOf(".") + (length + 1)));
		}
	}
	return result[0];
};
/**
 * 初始化标签插件
 * 
 * @author scd
 * @param fmAliasArray
 */
function tagEditorInit(fmAliasArray){
	//标签插件初始化 请输入食品别名
	$("[name='fmAlias']").tagEditor({
      initialTags: fmAliasArray, 
      onChange: function(field, editor, tags){},
      delimiter: '， ',
      autocomplete: {
	         delay: 0,
	         position: { collision: 'flip' }, 
	         source: [] },
      placeholder: '请输入别名' }).css('display', 'block').attr('readonly', true);
	 $(".tag-editor").addClass("form-control");
}

/**
 * 初始化食材元素列表
 * @param fmId
 */
function getFoodElement(arr){
	for(var j = 0,len = arr.length; j < len; j++){
		 $("#"+arr[j].nutrientId).val(arr[j].nutrientDosage);
	}
}

/**
 * 分级列表的初始化
 * @param value
 */
function initDdlHtmlFunc(value){
	var url = URL.get("foodMaterial.FOOD_TYPE_QUERY");
	ajax.post(url, null, dataType.json, function(result) {
		common.initDdlHtml(result.data, "fmTypeDiv", value, function(data){
			if(!common.isEmpty(data.childList)){
				layer.alert("您选择的食材类别还有下级，请继续选择");
				return false;//不会有内容被选中
			}
			//修改节点选中的状态，重新加载datatable,方便添加完
			var node = treeObj.getNodeByParam("id", data.id);
			treeObj.selectNode(node);
			$("#fmType").val(data.id);
			$("#categoryIdQuery").val(data.id);
		});
	}, false, false);
}
$().ready(function() {
    // 	初始化食材类型
    common.initCodeSelect("FOOD_MATERIAL_TYPE", "FOOD_MATERIAL_TYPE", "fmType");

    fmTable = datatable.table(fmdatableOption);

    //加入必填项提示
	// 类型表单验证 
	catalogValidator = $("#addCatalogForm").validate(menuAddOptions);
	common.requiredHint("addCatalogForm", menuAddOptions);
	//食材表单验证
	$("#editForm").validate(fmOperation);
	common.requiredHint("editForm", fmOperation);
	// 初始化 uploadifive 上传控件
	common.uploadifive("上传食材图片", function(data) {
		$('#fmPic').val(data);
		$('#showpic').attr("src", data);
	});
    // 初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// 调节div高度
	setDH();
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	
	
	//初始化标签插件
	var fmAliasArray=[];
	tagEditorInit(fmAliasArray);	
    $("button[name='fmPage']").click(function() {
        var id = $("#id").val();
        if (this.id == 'searchss') {
    		$("#fmId").val("");
    		$("#id").val("");
            fmTable = datatable.table(fmdatableOption);
            setDH();
        }
        if (this.id == 'addFmPage') {
        	operateType = "add";
//			common.clearForm("editForm");
			$("#editForm").attr("action", URL.get("foodMaterial.FOOD_MATERIAL_ADD"));
			//初始化标签插件
			$("[name='fmAlias']").tagEditor("destroy");// 清空标签
			var fmAliasArray=[];
			tagEditorInit(fmAliasArray);
		    //分级列表初始化
			initDdlHtmlFunc(null);
			$('#showpic').attr("src", PublicConstant.basePath+"/common/images/uplod_img.jpg");
        	$("#food_material_view").hide();
        	$("#food_material_edit").show();
        	
        	
        	//初始化元素的html
        	initElementDom();
        }
        var id = $("input[name='tableRadio']:checked").val();       
        if (this.id == 'editFmPage' && common.isChoose(id)) {       	
        	operateType = "update";
//			common.clearForm("editForm");
			$("#editForm").attr("action", URL.get("foodMaterial.FOOD_MATERIAL_UPDATE"));
			$("#editForm").jsonToForm(fmData);
			//设置是否优质蛋白复选框选中状态
			if("yes"==$("#fmProtidFlag").val()){
				$("#fmProtidFlag").attr('checked',"true");
			}
			//设置gmi
			$("#fmGi").val(fmData.fmGi);
			$("#fmPurin").val(fmData.fmPurin);
			$('#showpic').attr("src", PublicConstant.basePath+"/resource/upload/food/ext/"+fmData.fmPic);
			
        	//初始化元素的html
        	initElementDom();
			
			//初始化食材元素
			getFoodElement(fmData.productElementList);
			
			
			$("#fmGeneralType").val(fmData.fmGeneralType);
			//标签插件初始化 请输入食品别名
			$("[name='fmAlias']").tagEditor("destroy");// 清空标签
        	var fmAliasArray = [];
        	if (fmData.fmAlias != null) {
        		fmAliasArray = fmData.fmAlias.split("，");       		
        	}    
		    tagEditorInit(fmAliasArray);
			$("#fmPicOld").val($("#fmPic").val());
			//初始化分级列表
			var node = treeObj.getNodeByParam("id",fmData.fmType);
			//异步获取商品类别
			var catalogName = common.isEmpty(node)?null:node.name;
			initDdlHtmlFunc(catalogName);
			
        	$("#food_material_view").hide();
        	$("#food_material_edit").show();
        }
        if (this.id == 'removeFmPage' && common.isChoose(id)) {
            removeClick(id);
        }
        if (this.id == 'returnList') {
        	//返回列表
        	returnList();
        }
    });
    
	//初始化元素的html
    initElementDom();
});

function returnList(){
	$("#editForm").find("span").text('');
	$("#food_material_view").show();
	$("#food_material_edit").hide();
	fmTable = datatable.table(fmdatableOption);
	setDH();
	common.clearForm("editForm"); 
}

function listTableLengthChange() {	
	$("select[name='fmListTable_length']").change(function() {
		setDH();
	});
}

function initElementDom() {
	$("#elmentList").empty();
	$.each(elementList, function(index, value){
		if (index % 3 == 0) {			
			$("#elmentList").append('<div style="clear:both"></div>');
		}
		$("#elmentList").append(	
				'<label class="col-xs-2 control-label paddingBottom">' + value.nutrientName + '</label>'+
				'<div class="col-xs-2 paddingBottom">'+
                '  <div class="input-group">'+
                '    <input name="productElementList[' + index + '].nutrientId" value="'+value.nutrientId+'" type="hidden"/>'+
                '    <input type="text" id="'+value.nutrientId+'" name="productElementList[' + index + '].nutrientDosage" class="form-control text-right" type="text" maxlength="5" number="true" twoDigit="true"/>'+
                '    <div class="input-group-addon">'+CODE.transCode("productUnit", value.nutrientUnit)+'</div>'+
                '  </div>'+
	    		'</div>'
		);
	});
}


