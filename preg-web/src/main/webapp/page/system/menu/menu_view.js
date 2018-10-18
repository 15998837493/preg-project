//*****************************************zTree开始****************************************
// 当前选中结点
var selectNode;
// 树对象
var treeObj;
// 树配置
var zTree_1 = {
	onDrop: function(event, treeId, treeNodes, targetNode, moveType, isCopy) {
		var menuIds = "";
		$(targetNode.getParentNode().children).each(function(index,node){
			menuIds += node.id+",";
		});
		menuIds = menuIds.substring(0, menuIds.lastIndexOf(","));
		var url = URL.get("Menu.MENU_UPDATE_ORDER");
		var params = "menuIds="+menuIds;
		ajax.post(url,params,dataType.json,function(result){
			layer.alert("修改排序成功！",{
	    		icon: 1
	    	});
		},false);
		zTree_1.beforeClick(treeId, treeObj.getSelectedNodes()[0]);
	},
	beforeClick: function(treeId, treeNode){
		selectNode = treeNode;
		if(treeNode.level != 0){
			$("#submitButton").attr("disabled",false);
			var url = URL.get("Menu.MENU_GET");
			var params = "menuId="+selectNode.id;
			ajax.post(url,params,dataType.json,function(data){
				$("#updateForm").jsonToForm(data.value);
				$("#menuParentName").val(treeNode.getParentNode().name);
				$("#menuNameOld").val(data.value.menuName);
				if(data.value.menuType==1){
					$("#updateForm [name='menuUrl']").attr("readonly", true);
				}else{
					$("#updateForm [name='menuUrl']").attr("readonly", false);
				}
			});
		}else{
			setNoSelect();
		}
	},
	beforeRemove: function (treeId, treeNode) {
		var content = treeNode.value.menuType == 1?"确定要删除目录【"+treeNode.name+"】以及该目录下的所有菜单？":"确定要删除菜单【"+treeNode.name+"】？";
		layer.confirm(content,{
			btn:["确认","取消"]
		}, function(){
			var url = URL.get("Menu.MENU_REMOVE");
			var params = "menuId="+treeNode.id;
			ajax.post(url,params,dataType.json,function(result){
				setNoSelect();
				treeObj.removeChildNodes(treeNode);
				treeObj.removeNode(treeNode);
				layer.alert("删除成功！",{
					icon: 1
				});
			});
		});
	},
	addHoverDom: function (treeId, treeNode) {
		//三级节点和类型为菜单的节点不能添加
		if(treeNode.level == 3 || (!common.isEmpty(treeNode.value) && treeNode.value.menuType == 2)){
			return false;
		}
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='添加功能菜单' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			treeObj.selectNode(treeNode);// 选中
			zTree_1.beforeClick(treeId, treeNode);// 触发单机事件
			selectNode = treeNode;
			setAddFormContent();// 初始化添加表单
			$("#addMenuModal").modal("show");
		});
	}
};
var zTree_setting = {
	edit: {
		showRenameBtn: false,
		removeTitle: "删除菜单",
		renameTitle: "编辑菜单"
	}
};

/**
 * 验证设置
 */
var menuAddOptions = {
	rules: {
		menuName: {
			required:true,
			maxlength: 50,
			remote:	{
				url: URL.get("Menu.MENU_NAME_VALID"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    menuName: function() {  
				        return $("#addMenuForm [name='menuName']").val();  
					},
					menuNameOld: function() {  
				        return "";  
					},
					operateType: function() {  
				        return "add";  
					},
					random: function() {
						return Math.random();
					}
				}
			}
		},
		menuUrl: {
			required:true,
			maxlength: 100
		},
		menuIcon: {
			maxlength: 100
		},
		menuRemark: {
			maxlength: 200
		},
		menuState: {
			required:true
		},
		menuType: {
			required:true
		}
	},
	messages: {
		menuName: {
			remote: "该菜单名称已经存在，请重新输入"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？",{
			btn: ['确认','取消']
		}, function () {
			$('#addMenuForm').ajaxPost(dataType.json,function(result){
				//操作成功返回查询页
				treeObj.addNodes(selectNode, result.value);
	    		common.clearForm("addMenuForm");
	    		$("#addMenuModal").modal("hide");
			});
        });
	}
};

/**
 * 验证设置
 */
var menuUpdateOptions = {
	rules: {
		menuName: {
			required:true,
			maxlength: 50,
			remote:	{
				url: URL.get("Menu.MENU_NAME_VALID"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    menuName: function() {  
				        return $("#menuName").val();  
					},
					menuNameOld: function() {  
				        return $("#menuNameOld").val();  
					},
					operateType: function() {  
				        return "update";  
					},
					random: function() {
						return Math.random();
					}
				}
			}
		},
		menuUrl: {
			required:true,
			maxlength: 100
		},
		menuIcon: {
			maxlength: 100
		},
		menuRemark: {
			maxlength: 200
		},
		menuState: {
			required:true
		},
		menuType: {
			required:true
		}
	},
	messages: {
		menuName: {
			remote: "该菜单名称已经存在，请重新输入"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", {
			btn:["确认","取消"]
		}, function () {
			$('#updateForm').ajaxPost(dataType.json,function(result){
				//操作成功返回查询页
				selectNode.name=$("#updateForm [name='menuName']").val();
	    		treeObj.updateNode(selectNode);
	    		layer.alert("修改成功！",{
	        		icon:1
	        	});
			});
        });
	}
};

$().ready(function() {
	//表单校验
	$("#updateForm").validate(menuUpdateOptions);
	common.requiredHint("updateForm",menuUpdateOptions);
	//表单校验
	$("#addMenuForm").validate(menuAddOptions);
	common.requiredHint("addMenuForm",menuAddOptions);
	//初始化生成树
	$.fn.zTree.init($("#zTree"), common.zTree(zTree_1, zTree_setting), zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false);
	//treeObj.expandAll(true);
	//调节div高度
	setTimeout(function(){setDH();}, 300);
	$("[treenode_switch]").live('click', function(){
		setTimeout(function(){setDH();}, 200);
	});
	setNoSelect();
	
	layer.close(layer.index);
});

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

// 设置addMenuForm表单内容
function setAddFormContent(){
	$("#addMenuForm [name='menuOrder']").val(common.isEmpty(selectNode.children)?1:selectNode.children.length+1);
	$("#addMenuForm [name='menuParent']").val(selectNode.id);
	$("#addMenuForm [name='menuGrade']").val(selectNode.level+1);
	if(selectNode.level==2){
		$("#addMenuForm [name='menuType']").val("2");
		$("#addMenuForm [name='menuType']").attr("readonly", true);
	}else{
		$("#addMenuForm [name='menuType']").val("");
		$("#addMenuForm [name='menuType']").attr("readonly", false);
	}
	$("#addMenuForm [name='menuUrl']").attr("readonly", false);
}

// 未选中或选中最顶级节点时设置
function setNoSelect(){
	$("#submitButton").attr("disabled", true);
	common.clearForm("updateForm");
}

// 菜单类型更改事件
function setMenuSelect(menuType){
	if(menuType == "1"){
		$("#addMenuForm [name='menuUrl']").val("#");
		$("#addMenuForm [name='menuUrl']").attr("readonly", true);
	}else{
		$("#addMenuForm [name='menuUrl']").val("");
		$("#addMenuForm [name='menuUrl']").attr("readonly", false);
	}
}
