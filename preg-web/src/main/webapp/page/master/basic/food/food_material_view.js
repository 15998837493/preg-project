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
					return "<img class='img-thumbnail img-responsive' style='width:40px;height:40px;' onclick='showImg(this.src)' onerror='errorImg(this)' src='"+PublicConstant.basePath+"/resource/upload/food/ext/"+data.fmPic+"'/> "+data.fmName;
							   
				}
			}, 
			{"data" : null,"sClass" : "text-left",
				"render" : function(data, type, row, meta) {
					var fmAlias=data.fmAlias;
		            if (fmAlias.length >= 15) {
		            	return "<a id='dataFmAlias' href='#' title='"+data.fmAlias+"'>"+fmAlias.substring(0,15)+"......" +"</a>";
		            	
		            }else{
		                return "<a id='dataFmAlias' href='#' title='"+data.fmAlias+"'>"+fmAlias+"</a>";	
			        }
				}
			},
			{"data" : "catalogName","sClass" : "text-center"}, 
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					return data.foodMaterialExtVo.fmEnergy;
				}
			},
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					return data.foodMaterialExtVo.fmProtid;
				}
			},
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					return data.foodMaterialExtVo.fmFat;
				}
			},
			{"data" : null,"sClass" : "text-right",
				"render" : function(data, type, row, meta) {
					return data.foodMaterialExtVo.fmCbr;
				}
			},
	],
	rowClick : function(data, row) {
		fmData = data;
		fmRow = row;
		$("#fmId").val(data.fmId);
		$("#id").val(data.fmId);
	},
	condition : "fmCondition",
	async: false,
	loading: false,// 是否启用遮罩层
	/*selecttarget : [ 3 ]*/
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
		fmDesc:{
			maxlength : 2000
		},
		fmEfficacy:{
			maxlength : 1000
		},
		fmType : {
			required : true
		},
		fmLevel : {
			required : true
		},
		fmEsculent : {
			required : true,
			intege1 : true,
			range :true
		},
		fmEnergy : {
			required : true,
			number : true
		},
		fmProtid : {
			required : true,
			number : true
		},
		fmFat : {
			required : true,
			number : true
		},
		fmSfa : {
			required : true,
			number : true
		},
		fmMfa : {
			required : true,
			number : true
		},
		fmPfa : {
			required : true,
			number : true
		},
		fmCbr : {
			required : true,
			number : true
		},
		fmDf : {
			required : true,
			number : true
		},
		fmAshc : {
			required : true,
			number : true
		},
		fmVa : {
			required : true,
			number : true
		},
		fmCarotin : {
			required : true,
			number : true
		},
		fmCho : {
			required : true,
			number : true
		},
		fmVb1 : {
			required : true,
			number : true
		},
		fmVb2 : {
			required : true,
			number : true
		},
		fmAf : {
			required : true,
			number : true
		},
		fmVc : {
			required : true,
			number : true
		},
		fmVe : {
			required : true,
			number : true
		},
		fmEca : {
			required : true,
			number : true
		},
		fmEp : {
			required : true,
			number : true
		},
		fmEk : {
			required : true,
			number : true
		},
		fmEna : {
			required : true,
			number : true
		},
		fmEmg : {
			required : true,
			number : true
		},
		fmEfe : {
			required : true,
			number : true
		},
		fmEzn : {
			required : true,
			number : true
		},
		fmEse : {
			required : true,
			number : true
		},
		fmEcu : {
			required : true,
			number : true
		},
		fmEmn : {
			required : true,
			number : true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function() {
		var picPath = $("#fmPic").val();
		if(picPath == "" || picPath == null){
			layer.msg("请上传图片");
			return;
		}
		if(common.isEmpty($("#fmType").val()) ){
			layer.msg('食物类别为必填项！');
			return;
		}
   	  if($("[name='fmAlias']").val().length>200){
   		  layer.msg("别名最大只能输入200个字符！");
   		  return;
   	  	}
		layer.confirm("确定要执行【保存】操作？",function(data) {
			if (data) {
				$('#editForm').ajaxPost(dataType.json,function(data) {
					/*common.pageForward(PublicConstant.basePath+ 
							"/page/master/basic/food/food_material_view.jsp");*/
					fmTable = datatable.table(fmdatableOption);
					$("#editModal").modal("hide");
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
		catalogName : {
			required : true,
			maxlength : 50,
			remote : {
				url : URL.get("System.CATALOGINFO_NAME_VALID"),// 后台处理程序
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
			$('#addCatalogForm').ajaxPost(dataType.json, function(data) {
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
				common.clearForm("addCatalogForm");
				$("#addCatalogModal").modal("hide");
			});
	}
};

function chooseRadio(fmId) {
	//选中
	$("input:radio[id=" + fmId + "]").attr("checked", 'checked');
	$("#fmId").val(fmId);
}

function removeClick(fmId) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		var url = URL.get("Master.FOOD_MATERIAL_REMOVE");
		var params = "fmId=" + fmId;
		ajax.post(url, params, dataType.json, function(data) {
			datatable.remove(fmTable, fmRow);
			$("#id").val("");
		});
		layer.close(index);
	});
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
function getFoodElement(foodMaterialExtVo){
		if(operateType === "add"){
			  $("#elmentList").find("input[type='text']").each(function (index,obj) {
				  $("#"+obj.id).val("0.00");
				  if(obj.id === "fmEsculent"){
                	  $("#"+obj.id).val("100");
                  }
              });
			
		} else if(operateType === "update"){
			for ( var key in foodMaterialExtVo){
				 $("#"+key).val(foodMaterialExtVo[key]);
			 }
		}
}
/**************************************************************ZTREE_BEGIN************************************************************/

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
	var url = URL.get("System.CATALOGINFO_UPDATE_ORDER");
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
		var url = URL.get("System.CATALOGINFO_REMOVE");
		var params = "menuId=" + treeNode.id;
		ajax.post(url, params, dataType.json, function(data) {
			if (data.result) {
				setNoSelect();
				treeObj.removeChildNodes(treeNode);
				treeObj.removeNode(treeNode);
				var pNode = treeObj.getNodeByParam("id", treeNode.pId);
				pNode.iconSkin="menu";
				treeObj.updateNode(pNode);
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
			common.clearForm("addCatalogForm");
			
			if(!selectNode.isParent){
				layer.confirm('如果修改类别，此类别下所有食材的类别变更成【未分类】，是否确认修改？', {btn: ['是','否']},function(index){
					layer.close(index);
					$("#addCatalogForm").attr("action",URL.get("System.CATALOGINFO_ADD"));
					opernerCatalog = "add";
					setAddFormContent();// 初始化添加表单
					$("#addCatalogModal").modal("show");
				},function(index){
					layer.close(index);
				});
			}else{
				$("#addCatalogForm").attr("action",URL.get("System.CATALOGINFO_ADD"));
				opernerCatalog = "add";
				setAddFormContent();// 初始化添加表单
				$("#addCatalogModal").modal("show");
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
	$("#addCatalogForm").attr("action",URL.get("System.CATALOGINFO_UPDATE"));
	$("#addCatalogForm").jsonToForm(treeNode.value);
	$("#catalogName").val(node.name);
	$("#catalogNameOld").val(treeNode.value.catalogName);
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
	var max = hLeft > hRight ? hLeft : hRight;
	$("#left_div").height(max+50);
	$("#cust_div").height(max+50);
}

//未选中或选中最顶级节点时设置
function setNoSelect() {
	$("#submitButton").attr("disabled", true);
	common.clearForm("updateForm");
}
/**************************************************************ZTREE_END************************************************************/
/**
 * 分级列表的初始化
 * @param value
 */
function initDdlHtmlFunc(value){
	var url = URL.get("Master.FOOD_TYPE_QUERY");
	ajax.post(url, null, dataType.json, function(result) {
		common.initDdlHtml(result.data, "fmTypeDiv", value, function(data){
			if(!common.isEmpty(data.childList)){
				layer.alert("请选择叶子节点作为食物类别");
				return false;//不会有内容被选中
			}
			$("#fmType").val(data.id);
			//修改节点选中的状态，重新加载datatable,方便添加完
			var node = treeObj.getNodeByParam("id", data.id);
			treeObj.selectNode(node);
			$("#categoryIdQuery").val(data.id);
		});
	}, false, false);
}

//设置addMenuForm表单内容
function setAddFormContent() {
	$("#addCatalogForm [name='catalogOrder']").val(
			common.isEmpty(selectNode.children) ? 1
					: selectNode.children.length + 1);
	$("#addCatalogForm [name='catalogParentId']").val(selectNode.id);
}
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
$().ready(function() {
	//初始化鼠标悬停显示文字功能，基于jquery-ui.js
	$( "#dataFmAlias" ).tooltip();
	
	// 定义校验数值
	$("#elmentList  input").off("change").on("change", function(){
		this.value = common.checkInputNumber("reg7", this.value,2);
	});
	
	//加入必填项提示
	// 类型表单验证
	$("#addCatalogForm").validate(menuAddOptions);
	common.requiredHint("addCatalogForm", menuAddOptions);
	//食材表单验证
	$("#editForm").validate(fmOperation);
	common.requiredHint("editForm", fmOperation);
	//datatable
	fmTable = datatable.table(fmdatableOption);
	// 初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// ztree树拓展搜索框
	common.ztreeSearch(treeObj, zTree_1, "left_div");
	// 调节div高度
	//setTimeout(function(){setDH();}, 500);
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	//初始化食物种类
	common.initCodeSelect("FOOD_SPECIES", "FOOD_SPECIES", "fsId");
	//初始化食材类型
	common.initCodeSelect("FOOD_MATERIAL_TYPE","FOOD_MATERIAL_TYPE", "fmType");
	// 	初始化食物种类
	common.initCodeSelect("FOOD_SPECIES", "FOOD_SPECIES","fsId");
	// 	初始化食材类型
	common.initCodeSelect("FOOD_MATERIAL_TYPE","FOOD_MATERIAL_TYPE", "fmType");
	// 初始化 uploadifive 上传控件
	common.uploadifive("上传食材图片", function(data) {
	// var food = JSON.parse(data);
		$('#fmPic').val(data);
		$('#showpic').attr("src", data);
	});
	//初始化datalist标签的内容（因为要异步添加需要每次都更新dataList标签内容）
	common.initSelectAndInput("fmGeneralTypeList", "mas_intake_type", "name");
	//初始化标签插件
	var fmAliasArray=[];
	tagEditorInit(fmAliasArray);
	
	setNoSelect();
	$("button[name='fmPage']").click(function() {
						var fmId = $("#id").val();
						if(this.id=='searchss') {
							fmTable = datatable.table(fmdatableOption);
							$("#id").val("");
						}
						if (this.id == 'addFmPage') {
							operateType = "add";
							common.clearForm("editForm");
							$("#editForm").attr("action", URL.get("Master.FOOD_MATERIAL_ADD"));
							//初始化食材元素
							getFoodElement(null);
							//初始化标签插件
							$("[name='fmAlias']").tagEditor("destroy");// 清空标签
							var fmAliasArray=[];
							tagEditorInit(fmAliasArray);
						    //分级列表初始化
							initDdlHtmlFunc(null);
							$('#showpic').attr("src", PublicConstant.basePath+"/common/images/uplod_img.jpg");
							$("#editModal").modal("show");
							//common.pageForward(PublicConstant.basePath+ "/page/food/food_material_add_init.action");
						}
						if (this.id == 'editFmPage'&& common.isChoose(fmId)) {
							operateType = "update";
							common.clearForm("editForm");
							$("#editForm").attr("action", URL.get("Master.FOOD_MATERIAL_UPDATE"));
							$("#editForm").jsonToForm(fmData);
							$('#showpic').attr("src", PublicConstant.basePath+"/resource/upload/food/ext/"+fmData.fmPic);
							//初始化食材元素
							getFoodElement(fmData.foodMaterialExtVo);
							$("#fmGeneralType").val(fmData.fmGeneralType)
							//标签插件初始化 请输入食品别名
							$("[name='fmAlias']").tagEditor("destroy");// 清空标签
							var fmAliasArray=fmData.fmAlias.split("，");
						    tagEditorInit(fmAliasArray);
							$("#fmPicOld").val($("#fmPic").val());
							//初始化分级列表
							//$("#productNameOld").val(productData.productName);
							var node = treeObj.getNodeByParam("id",fmData.fmType);
							//异步获取商品类别
							var catalogName = common.isEmpty(node)?null:node.name;
							initDdlHtmlFunc(catalogName);
							$("#editModal").modal("show");
							//common.pageForward(PublicConstant.basePath+ "/page/food/food_material_update_init.action?fmId="+ fmId); 
						}
						if (this.id == 'removeFmPage'&& common.isChoose(fmId)) {
							removeClick(fmId);
							$("#id").val("");
						}
	});
});