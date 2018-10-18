$.validator.addMethod("RegCode", function(value,element) {
	return new RegExp(validateRegExp.id).test(element.value);
}, '只允许字母与数字组合');
/***************************************************检验项目***************************************************/
var itemTableOptions={
	id:"itemListTable",
	form:"itemQuery",
	columns: [
	        {"data": null,"sClass": "text-center",
	        	"render":  function (data, type, row, meta) {
	        		return parseInt(meta.row) + 1;
	        	}
	        },
	  		{"data": "itemName","sClass": "text-left" },
	  		{"data": "itemUnit","sClass": "text-center"},
	  		{"data": "itemRefValue","sClass": "text-left"},
	  		{"data": null,"sClass": "text-center",
	  			"render":  function (data, type, row, meta) {
	  				return `<a onclick='editItemButton("${data.itemId}")'>编辑</a>&nbsp<a onclick='removeClick("${data.itemId}")'>删除</a>&nbsp<a onclick='moveItemOrder("${data.itemId}")'>移动</a>`;
	  			}
	  		}
	],
	rowClick: function(data, row){
		itemData = data;
		itemRow = row;
		$("#itemId").val(data.itemId);
		$("#id").val(data.itemId);
	},
	async: false,
	loading: false,// 是否启用遮罩层
	condition : "itemCondition"
};
/**
 * 验证参数设置
 */
var validator;
var itemValidOptions = {
	rules: {
		itemName: {
			maxlength: 50,
			required:true,
			remote : {
				url : URL.get("item.ITEMNAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					itemName : function() {
						return $("#itemName").val();
					},
					itemId : function() {
						return $("#itemId").val();
					},
					itemType : function() {
						return $("#itemType").val();
					},
					itemClassify : function() {
						return $("#itemClassify").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		itemCode: {
			maxlength: 20,
			remote : {
				url : URL.get("item.ITEMCODE_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					itemCode : function() {
						return $("#itemCode").val();
					},
					itemId : function() {
						return $("#itemId").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		itemAbbr:{
			maxlength: 15
		},
		itemRefValue:{
			maxlength: 50
		},
		itemUnit:{
			maxlength: 15
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	messages : {
		itemName : {
			remote : "该名称已经存在，请修改"
		},
		itemCode : {
			remote : "该编码已经存在，请修改"
		}
	},
	success: $.noop,
	submitHandler: function(form) {
		var flag = validSubmit();
		//flag为true只有两种情况
		//1 ： 九项全部填写（需拼json）
		//2 ：前六项没有填写，最后一列就不用管了（不用拼json）
		if(flag) { 
			//如果有1项不为空（前6项，最后一列不算），就得拼接为json字符串
			//json格式为：{"normalName":"normalDown~normalUp","manyName":"manyMark normalUp","lessName":"less normalDown"}
			if($("#normalDown").val()!="" || $("#normalUp").val()!="") {
				var normalDown = $("#normalDown").val();//正常-下限值
				var normalUp = $("#normalUp").val();//正常-上限值
				var normalName = $("#normal").val();//正常-最后一列
				var manyMark = $("#manyMark").val();//超标-符号
				var manyName = $("#many").val();//超标-最后一列
				var lessMark = $("#lessMark").val();//低标-符号
				var lessName = $("#less").val();//低标-最后一列
				var json = {};
				json[normalName] = normalDown + "~" + normalUp;
				// " "拆分字符串，不可分割
				json[manyName] = manyMark + " " + normalUp;
				json[lessName] = lessMark + " " + normalDown;
				$("#resultJson").val(JSON.stringify(json));
			}
			//提交后台处理数据
			$(form).ajaxPost(dataType.json,function(data){
				if(operateType == "add"){
					datatable.add(itemTable, data.value);// 添加
					setDH();
					$("#editItemModal").modal("hide");
				} else if(operateType == "update"){
					datatable.update(itemTable, data.value, itemRow);// 修改
					$("#editItemModal").modal("hide");
				}
				validator.resetForm();
			});	
		}
	}
};

/**
 * 删除提交form请求
 */
function removeClick(id){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		},
	function(){
		var url = URL.get("item.ITEM_REMOVE");
		var params = "itemId=" + id;
		ajax.post(url, params, dataType.json, function(data){
			itemTable = datatable.table(itemTableOptions);
		});
	});
};
/***************************************************************检查项目*****************************************************/
/**
 * 验证设置，类型表单验证
 */
var inspectValidator;
var inspectOptions = {
	rules : {
		inspectName : {
			required : true,
			maxlength : 20,
			remote : {
				url : URL.get("item.HOSPITALINSPECT_NAME_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					inspectName : function() {
						return $("#inspectName").val();
					},
					inspectOldName : function() {
						return $("#inspectOldName").val();
					},
					type : function() {
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
		inspectName : {
			remote : "该名称已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm('确定要执行【保存】操作？', {
			btn: ['确定','取消'] //按钮
		},
		function(){
			$('#editInspectForm').ajaxPost(dataType.json, function(data) {
				if("update" == opernerCatalog){
					selectNode.name = $("#inspectName").val();
					selectNode.value.inspectSex =data.value.inspectSex;
					treeObj.updateNode(selectNode);
					layer.msg('修改成功!');
				}else{
					treeObj.addNodes(selectNode, data.value);
					layer.msg('添加成功!');
				}
				inspectValidator.resetForm();
				common.clearForm("editInspectForm");
				$("#editInspectModal").modal("hide");
			},false,false);
		});
		
			
	}
};
/***************************************************************zTree_begin******************************************/
//树配置
var zTree_1 = {
	beforeClick : function(treeId, treeNode) {
		selectNode = treeNode;
		treeObj.selectNode(treeNode);
		$("#id").val("");
		if (treeNode.level >= 1 && !treeNode.isParent) {
			$("#t_body").empty();
			$("#queryItemButton").attr("disabled", false);
			$("#checkInspectId").val(treeNode.value.inspectId);
			// 查询推断检验项目
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
		} else {
			$("#queryItemButton").attr("disabled", true);
			$("#zTree_div").css("height", "auto");
			$("#cust_div").css("height", "auto");
			$("#checkInspectId").val(null);
		}
		// 加载对应的检验项目信息
		itemTable = datatable.table(itemTableOptions);
		setDH();
	},
	onCollapse : function() {
		setDH();
	},
	onExpand : function() {
		setDH();
	}
};
//配置
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
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : function showRemoveBtn(treeId, treeNode) {return treeNode.level != 0;},
		showRenameBtn: function showRenameBtn(treeId,treeNode){return treeNode.level != 0;},
		removeTitle : "删除",
		renameTitle : "编辑",
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
		onExpand : function() {
			setDH();
		}
	}
};

//添加结点
function addHoverDom(treeId, treeNode) {
	if(treeNode.level >= 2){
		return;
	}
	if(treeNode.level == 0){
		$("#addLevelTypeNameSpan").html("编辑检验项目科室");
		$("#addLevelTypeNameLabel").html("所属科室");
	} else{
		$("#addLevelTypeNameSpan").html("编辑检验项目分类");
		$("#addLevelTypeNameLabel").html("项目分类");
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
			$("#addItemTypeClassifyModal").modal("show"); 
		});
	}
};


function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};

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
	return false;
}

function checkInspectIsConfigure(inspectId){
	var flag = false;
	var url = URL.get("item.HOSPITAL_INSPECT_IS_CONFIGURE");
	var params = "inspectId=" + inspectId;
	ajax.post(url, params, dataType.json, function(data) {
		if (data.value) {
			flag = true;
		} 
	},false,false);
	return flag;
}

//删除结点回调函数
function beforeRemove(treeId, treeNode) {
	var content = "删除该类别将删除其下所有的子类别及其检验项目，确定删除吗？";
	layer.confirm(content, function(index){
		var url = URL.get("item.REMOVE_ITEM_BY_TYPE");
		var params = "";
		
		var type = "";
		if (treeNode.level == 1) {
			type = "itemType";
			params = `type=${type}&itemType=${treeNode.id}`;
		}else{
			type = "itemClassify";
			params = `type=${type}&itemType=${treeNode.pId}&itemClassify=${treeNode.id}`;
		}
		ajax.post(url, params, dataType.json, function(){
			setNoSelect();
			treeObj.removeChildNodes(treeNode);
			treeObj.removeNode(treeNode);
			layer.msg("删除成功！");
			itemTable = datatable.table(itemTableOptions);
			layer.close(index);
		}, false, false);
	});
	
}

/**
* 修改名称
*/
function zTreeBeforeEditName(treeId, treeNode){
	if(treeNode.level == 1){
		$("#editLevelTypeNameSpan").html("编辑检验项目科室");
		$("#editLevelTypeNameLabel").html("所属科室");
	} else{
		$("#editLevelTypeNameSpan").html("编辑检验项目分类");
		$("#editLevelTypeNameLabel").html("项目分类");
	}
	var node = treeObj .getNodeByTId(treeNode.tId);
	treeObj.selectNode(treeNode);
	selectNode = treeNode;
	console.log(selectNode);
	zTreeOnClick(null, treeId, treeNode);
	$("#editItemTypeClassify").val(selectNode.name);
	$("#editItemTypeClassifyModal").modal("show");
	setDH();
}

/**
 * 点击节点事件
 */
function zTreeOnClick(event, treeId, treeNode){
	selectNode = treeNode;
	treeObj.selectNode(treeNode);
	if(selectNode.level < 2){
		common.clearForm("itemQuery");
		$("[name='itemType']").val("null");
		$("[name='itemClassify']").val("null");
		$("#addItemButton").attr("disabled", true);
	}else{
		$("[name='itemType']").val(selectNode.pId);
		$("[name='itemClassify']").val(selectNode.id);
		$("#addItemButton").attr("disabled", false);
	}
	//datatable
	itemTable = datatable.table(itemTableOptions);
	setDH();
}

//设置目录高度
function setDH() {
	$("#left_div").css("height", "auto");
	$("#cust_div").css("height", "auto");
	var hLeft = $("#left_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft > hRight ? hLeft : hRight;
	$("#left_div").height(max);
	$("#cust_div").height(max);
}

//未选中或选中最顶级节点时设置
function setNoSelect() {
	$("#addItemButton").attr("disabled", true);
}
/******************************************************************工具方法************************************************/

//判断是否选择检验项目/MNT_WEB_PREG/WebRoot/page/master/food/diet_tenet_view.jsp
function isChoose(id){
	if(common.isEmpty(id)){
	    layer.msg('请选择操作的记录');
	    return false;
	}else{
		return true;
	}
}

//编辑回显
function updateShow(itemId) {
	var url = URL.get("item.ITEM_QUERY");
	var params = "itemId=" + itemId;
	ajax.post(url, params, dataType.json, function(data){
		var value = data.data[0];
		var result = value.resultJson;//结论算法，为json格式
		$("#itemName").val(value.itemName);
		$("#itemUnit").val(value.itemUnit);
		$("#itemRefValue").val(value.itemRefValue);
		$("#itemSex").val(value.itemSex);
		$("#itemId").val($("#id").val());
		if(result!=null && result!="") {
			$("#normalDown").val(result.split(",")[0].split(":")[1].split("~")[0].substring(1));
			$("#normalUp").val(result.split(",")[0].split(":")[1].split("~")[1].substring(0,result.split(",")[0].split(":")[1].split("~")[1].length-1));
			$("#normal").val(result.split(",")[0].split(":")[0].substring(2,result.split(",")[0].split(":")[0].length-1));
			$("#manyMark").val(result.split(",")[1].split(":")[1].split(" ")[0].substring(1));
			$("#manyUp").val(result.split(",")[0].split(":")[1].split("~")[1].substring(0,result.split(",")[0].split(":")[1].split("~")[1].length-1));
			$("#many").val(result.split(",")[1].split(":")[0].substring(1,result.split(",")[1].split(":")[0].length-1));
			$("#lessMark").val(result.split(",")[2].split(":")[1].split(" ")[0].substring(1));
			$("#lessDown").val(result.split(",")[0].split(":")[1].split("~")[0].substring(1));
			$("#less").val(result.split(",")[2].split(":")[0].substring(1,result.split(",")[2].split(":")[0].length-1));
		}else if(result=="") {
			$("#normal").val("正常");
			$("#many").val("↑");
			$("#less").val("↓");
		}
	},false,false);
}

//正常 超标 低标，任何一个上限值或者下限值只要填写一个，另一个就会自动填写；反之，一个清空另一个就会自动清空
function relevancy(val,id) {
	if(id=="normalDown") {
		$("#lessDown").val(val);
	}
	if(id=="normalUp") {
		$("#manyUp").val(val);
	}
	if(id=="manyUp") {
		$("#normalUp").val(val);
	}
	if(id=="lessDown") {
		$("#normalDown").val(val);
	}
}

//正常一行中，下限值必须小于上限值
function compare(val,id) {
	if(id=="normalDown" || id=="lessDown") {
		var up = $("#manyUp").val();
		if(up!=""&&val!="") {
			if(parseFloat(val)>parseFloat(up)||parseFloat(val)==parseFloat(up)) {
		         $("#normalDown").val("");
		         $("#lessDown").val("");
			     layer.msg("下限值必须小于上限值！");
			}	
		}
	}
	if(id=="normalUp" || id=="manyUp") {
		var down = $("#normalDown").val();
		if(down!=""&&val!="") {
			if(parseFloat(val)<parseFloat(down)||parseFloat(val)==parseFloat(down)) {
				$("#normalUp").val("");
				$("#manyUp").val("");
			     layer.msg("上限值必须大于下限值！");
			}	
		}
	}
}

//正常 超标 低标 3行（除每行的最后一项，因为给了默认值）如果有任何一项填了那么所有项都必须填，反之则可以不填
//如果返回true，则通过验证，可以提交后台
//如果返回false，则给出提示，不提交后台
/**
 * 需求修改为可以是单边界值
 * 如果填写下限值，那么低标的信息不可以为空
 * 如果填写上限值，那么超标的信息不可以为空
 * 如果都填写了，那么都不可以为空
 * 
 * 2017-12-28 修改
 */
function validSubmit() {
	//判断下限的内容有一个不为空，则都不可以为空
	var downFlag = !common.isEmpty($("#normalDown").val()) || 
	               !common.isEmpty($("#lessMark").val()) || 
	               !common.isEmpty($("#lessDown").val());
	if(downFlag){
		var downRe = !common.isEmpty($("#normalDown").val()) && 
		             !common.isEmpty($("#lessMark").val()) && 
		             !common.isEmpty($("#lessDown").val()) && 
		             !common.isEmpty($("#less").val());
		if(!downRe){
			layer.alert("低标数据不可以为空");
			return false;
		}
	}
	// 同样的方法判断上限值
	var upFlag = !common.isEmpty($("#normalUp").val()) || 
				 !common.isEmpty($("#manyMark").val()) || 
				 !common.isEmpty($("#manyUp").val());
	if(upFlag){
		var upRe = !common.isEmpty($("#normalUp").val()) && 
		           !common.isEmpty($("#manyMark").val()) && 
		           !common.isEmpty($("#manyUp").val()) && 
		           !common.isEmpty($("#many").val());
		if(!upRe){
			layer.alert("超标数据不可以为空");
			return false;
		}
	}
	return true;
}

//验证结尾不能有小数点
function checkNum(val,id) {
	if(val!="") {
		if(val.substring(0,1)==".") {
	        layer.msg("不能以小数点开头！");
			$("#"+id).val(val.substring(1));
		}
		if(val.substring(val.length-1)==".") {
	        layer.msg("不能以小数点结尾！");
			$("#"+id).val(val.substring(0,val.length-1));
		}	
	}
}

//验证输入框不能只输入"-",要在失去焦点时触发才行
function validNumberOnBlur(val,id) {
	if(val.toString().length==1&&val=="-") {
      layer.msg("不能输入数字以外的字符！");
      $("#"+id).val("");
	}
}

//验证上下限值（可输入 负数，小数，整数，0）
function validNumber(val,id) {
	var flag = true;
	if(val!=""&&val.toString().substring(0,1)=="-") {
		if (isNaN(val.toString().substring(1))) {
	        layer.msg("不能输入数字以外的字符！");
	        $("#"+id).val("");
	        flag = false;
	    }else {
	    	val = val.toString().substring(1);
	        if (val.toString().split(".").length > 1) {//如果是小数
	        	if(val.toString().split(".")[0].length > 1 && val.toString().split(".")[0].substring(0,1)=="0"){//如果是这种格式的小数 00.1 000.1 0000.1 000000.1
	                layer.msg("请输入正常负数！");
	                $("#"+id).val("");
	                flag = false;
	        	}
	        	if(val.toString().split(".")[1].length > 2){//如果小数点后大于2位
	                layer.msg("小数点后只能有两位！");
	                $("#"+id).val("");
	                flag = false;
	        	}
	        }else {//如果是整数
	        	if(val.toString().length>1 && val.toString().substring(0,1)=="0") {//如果是这种格式 01 001 001 0001
	                layer.msg("请输入正常负数！");
	                $("#"+id).val("");
	                flag = false;
	        	}
	        }
	        if(val.replace(".","").length>8) {
          	layer.msg("最多只能输入8位数字(不包括小数点)");
          	$("#"+id).val("");
          	flag = false;
      	}
	    }
	}else if (isNaN(val)) {
      layer.msg("不能输入数字以外的字符！");
      $("#"+id).val("");
      flag = false;
  }
      if (val.toString().split(".").length > 1) {//如果是小数
      	if(val.toString().split(".")[0].length > 1 && val.toString().split(".")[0].substring(0,1)=="0"){//如果是这种格式的小数 00.1 000.1 0000.1 000000.1
              layer.msg("请输入正常小数！");
              $("#"+id).val("");
              flag = false;
      	}
      	if(val.toString().split(".")[1].length > 2){//如果小数点后大于2位
              layer.msg("小数点后只能有两位！");
              $("#"+id).val("");
              flag = false;
      	}
      }else {//如果是整数
      	if(val.toString().length>1 && val.toString().substring(0,1)=="0") {//如果是这种格式 01 001 001 0001
              layer.msg("请输入正常整数！");
              $("#"+id).val("");
              flag = false;
      	}
      }
      if(val.replace(".","").length>8) {
          	layer.msg("最多只能输入8位数字(不包括小数点)");
          	$("#"+id).val("");
          	flag = false;
      }
  return flag;
}

//只能输入英文和数字(小数点也不能输入)
function checkCode(value) {
	var reg= /^[0-9a-zA-Z]*$/;
		if(reg.test(value)==false) {
      	layer.msg("只能输入英文字母和数字！");
      	$("#itemCode").val("");
		}
}

/**
 * 编辑检验项目
 * @param itemId
 * @returns
 */
function editItemButton(itemId){
	operateType = "update";
	common.clearForm("editItemForm");
	$("#editItemForm").attr("action", URL.get("item.ITEM_UPDATE"));
	$("[name='itemType']").val(selectNode.pId);
	$("[name='itemClassify']").val(selectNode.id);
	$("#normal").val("正常");
	$("#many").val("↑");
	$("#less").val("↓");
	updateShow(itemId);//回显（包括json解析）
	$("#edit_item").html("编辑检验项目");
	$("#editItemModal").modal("show");
}

/**
 * 检验项目排序
 * @param itemId
 * @returns
 */
function moveItemOrder(itemId){
	$("#itemId").val(itemId);
	$("#editItemOrderModal").modal("show");
}

/**
 * 检验项目排序的编辑
 * @returns
 */
function editItemOrderButton(){
	var itemId = $("#itemId").val(); 
	var orderNum = $("#editItemOrder").val();
	if (common.isEmpty(orderNum) || isNaN(orderNum)) {
		layer.alert("不能为空，请输入数字");
		return false;
	}
	
	var itemListNow = datatable.getAllData(itemTable);
	var itemArr = [];
	for ( var i = 0; i < itemListNow.length; i++) {
		var item = itemListNow[i];
		itemArr.push(item.itemId);
	}
	if (orderNum > itemArr.length || orderNum < 1) {
		layer.alert("可以输入的最大值为"+itemArr.length+"，最小是为1");
		return false;
	}
	
	// 调整排序后的检验项目在数组中的位置
	itemArr.splice($.inArray(itemId, itemArr), 1);
	itemArr.splice(orderNum - 1, 0, itemId);
	
	var url = URL.get("item.ITEM_ORDER");
	var params = "itemIds=" + itemArr.join(",");
	ajax.post(url, params, dataType.json, function(data){
		itemTable = datatable.table(itemTableOptions);
	}, false, false)
	$("#editItemOrderModal").modal("hide");
}

/**
 * 检验项目类别添加节点
 * @returns
 */
function addItemTypeClassify(){
	// 验证
	var nodeName = $("#addItemTypeClassify").val();
	if(validateName("add", nodeName)){
		var treeNode = {};
		treeNode.id = nodeName;
		treeNode.pId = selectNode.id;
		treeNode.name = nodeName;
		if(selectNode.level == 0){
			treeNode.iconSkin = "mulu";
		}else{
			treeNode.iconSkin = "menu";
		}
		treeObj.addNodes(selectNode, treeNode);
		layer.msg('添加成功!');
		$("#addItemTypeClassifyModal").modal("hide"); 
		$("#addItemTypeClassify").val("");
	}
}

/**
 * 修改类别名称
 * @returns
 */
function editItemTypeClassify(){
	var name = $("#editItemTypeClassify").val();
	if(validateName("edit", name)){
		var oldName = selectNode.name;
		selectNode.name = name;
		selectNode.id = name;
		treeObj.updateNode(selectNode);
		var itemListNow = datatable.getAllData(itemTable);
		if (common.isEmpty(selectNode.children) || selectNode.children.length <= 0) {
			// 如果该类别下没有检验项目，则不需要更新检验项目的类别
			$("#editItemTypeClassifyModal").modal("hide"); 
			$("#editItemTypeClassify").val("");
			return false;
		}else{
			var type = "itemType";
			var pName = "";
			if (selectNode.level == 2) {
				type = "itemClassify";
				pName = "&pType=" + selectNode.pId;
			}
			var url = URL.get("item.UPDATE_ITEM_TYPE");
			var params = `oldType=${oldName}&newType=${name}&type=${type}${pName}`;
			ajax.post(url, params, dataType.json, null, false, false);
		}
		$("#editItemTypeClassifyModal").modal("hide"); 
		$("#editItemTypeClassify").val("");
	}
}

/**
 * 校验数据名称不能重复
 * @returns
 */
function validateName(type, name){
	var flag = true;
	if(common.isEmpty(name)){
		flag = false;
		layer.alert("名称不能为空");
	}
	
	var checkNodes;
	if(type == "add"){
		checkNodes = selectNode.children;
	} else{
		checkNodes = selectNode.getParentNode().children;
	}
	if(flag && !_.isEmpty(checkNodes)){
		$.each(checkNodes, function(index, node){
			if(name == node.name){
				flag = false;
				layer.alert("名称已存在，请重新输入！");
			}
		});
	}
	return flag;
}

/************************************************************************页面初始化******************************************************/
$().ready(function() {
	// 初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	// ztree树拓展搜索框
	common.ztreeSearch(treeObj, zTree_1, "left_div");
	// 调节div高度
	setTimeout(function(){setDH();}, 500);
	$("[treenode_switch]").live('click', function() {
		setDH();
	});
	//页面初始化时检验项目增加按钮禁用，选中检查项目后解除禁用。
	setNoSelect();
	//验证添加
	validator = $("#editItemForm").validate(itemValidOptions);
	common.requiredHint("editItemForm",itemValidOptions);
	// 检查项目表单验证
	inspectValidator = $("#editInspectForm").validate(inspectOptions);
	common.requiredHint("editInspectForm", inspectOptions);
	//初始化下拉选
	common.initCodeSelect("ITEMSEX", "ITEMSEX","itemSex","A");
	//加载dataTable;
	layer.close(layer.index);
	itemTable = datatable.table(itemTableOptions);

	//定义按钮的方法
	$("button[name='itemPage']").click(function(){
		var itemId = $("#id").val();
		if(this.id == 'addItemButton'){
			operateType = "add";
			common.clearForm("editItemForm");
			$("#editItemForm").attr("action", URL.get("item.ITEM_ADD"));
			$("#inspectId").val($("#checkInspectId").val());
			$("[name='itemType']").val(selectNode.pId);
			$("[name='itemClassify']").val(selectNode.id);
			$("#normal").val("正常");
			$("#many").val("↑");
			$("#less").val("↓");
			$("#edit_item").html("增加检验项目");
			$("#editItemModal").modal("show");
		}
		if(this.id == 'removeintakeTemplatePage' && isChoose(itemId)){
			removeClick(itemId);
		}
		if(this.id == 'searchButton'){
			itemTable = datatable.table(itemTableOptions);
		}
	});
	
	//结论算法输入验证
	$("input[name='checkNum']").keyup(function() {
		validNumber(this.value,this.id);
		relevancy(this.value,this.id);
	});
	//结论算法，焦点失去验证
	$("input[name='checkNum']").blur(function() {
		validNumber(this.value,this.id);
		validNumberOnBlur(this.value,this.id);
		checkNum(this.value,this.id);
		relevancy(this.value,this.id);
		compare(this.value,this.id);
	});
	// 项目编码输入验证
	// $("input[name='itemCode']").keyup(function() {
	//     checkCode(this.value);
	// });
	// 项目编码输入验证
	$("input[name='itemCode']").blur(function() {
		checkCode(this.value);
	});
});