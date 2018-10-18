var cityMap = new Map();
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
	        		return "<input type='radio' name='tableRadio' value='"+data.id+"' />";
	        	}
	        },
	  		{"data": "hospitalClassify","sClass": "text-left" },
	  		{"data": "hospitalName","sClass": "text-left"},
	  		{"data": null,"sClass": "text-left",
	  			"render": function (data, type, row, meta) {
	  				if(!common.isEmpty(data.hospitalCapital) && !common.isEmpty(data.hospitalCity) && !common.isEmpty(data.hospitalArea)) {
	  					let cap = cityMap.get(parseInt(data.hospitalCapital));
	  					const city = cityMap.get(parseInt(data.hospitalCity));
	  					const area = cityMap.get(parseInt(data.hospitalArea));
	  					if(cap.indexOf("北京") > -1 || cap.indexOf("天津") > -1 || cap.indexOf("上海") > -1 || cap.indexOf("重庆") > -1) {
	  						cap = "";
	  					}
	  					return cap + city + area;
	  				}else {
	  					return "无";
	  				}
	  			}
	  		},
	  		{"data": "hospitalWorkWith","sClass": "text-center",
	  			"render":function(data, type, row, meta) {
					return $("#hospitalWorkWith").find(
							"option[value='" + data + "']").text();
	  			}
	  		}
	],
	rowClick: function(data, row){
		itemData = data;
		itemRow = row;
		$("#hospitalId").val(data.hospitalId);
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
		hospitalName: {
			maxlength: 50,
			required:true,
			remote : {
				url : URL.get("item.HOSPITAL_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					itemName : function() {
						return $("#hospitalName").val();
					},
					hospitalId : function() {
						return $("#hospitalId").val();
					},
					hospitalType : function() {
						return $("#hospitalType").val();
					},
					hospitalClassify : function() {
						return $("#hospitalClassify").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		hospitalClassify:{
			required:true
		},
		hospitalWorkWith:{
			required:true
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
		if(common.isEmpty($("#hospitalCapital").val()) || common.isEmpty($("#hospitalCity").val()) || $("#hospitalCity").val() == 0 || common.isEmpty($("#hospitalArea").val()) || $("#hospitalArea").val() == 0) {
			layer.alert("请完善所在城市！");
		}else {
			$("#hospitalClassify").attr("disabled",false);
			//提交后台处理数据
			$(form).ajaxPost(dataType.json,function(data){
				if(operateType == "add"){
					$("#editItemModal").modal("hide");
					datatable.add(itemTable, data.value);// 添加
					setDH();
					layer.alert("添加成功");
				} else if(operateType == "update"){
					$("#editItemModal").modal("hide");
					datatable.update(itemTable, data.value, itemRow);// 修改
					layer.alert("修改成功");
				}
				validator.resetForm();
			});
		}
	}
};

/**
 * 清空城/市/区
 */
function initCity() {
	$("#hospitalCapital *").not("option[value='']").remove();
	$("#hospitalCity *").not("option[value='0']").remove();
	$("#hospitalArea *").not("option[value='0']").remove();
}

/**
 * 删除提交form请求
 */
function removeClick(id){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		},
	function(){
		var url = URL.get("item.HOSPITAL_DELETE");
		var params = "hospitalId=" + id;
		ajax.post(url, params, dataType.json, function(data){
			itemTable = datatable.table(itemTableOptions);
		});
	});
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
		$("#addLevelTypeNameSpan").html("编辑医院种类");
		$("#addLevelTypeNameLabel").html("医院种类");
	} else{
		$("#addLevelTypeNameSpan").html("编辑医院类型");
		$("#addLevelTypeNameLabel").html("医院类型");
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
			$("#addItemTypeClassify").val("");
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
	var content = "删除该类别将删除其下所有的子类别，确定删除吗？";
	layer.confirm(content, function(index){
		const url = URL.get("item.HOSPITAL_REMOVE");
		let params = "";
		if (treeNode.level == 1) {
			params = "type=hospitalType&itemType="+treeNode.id;
		}else{
			params = "type=hospitalClassify&itemType="+treeNode.pId+"&itemClassify="+treeNode.id;
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
		$("#editLevelTypeNameSpan").html("编辑医院种类");
		$("#editLevelTypeNameLabel").html("医院种类");
	} else{
		$("#editLevelTypeNameSpan").html("编辑医院类型");
		$("#editLevelTypeNameLabel").html("医院类型");
	}
	var node = treeObj .getNodeByTId(treeNode.tId);
	treeObj.selectNode(treeNode);
	selectNode = treeNode;
	zTreeOnClick(null, treeId, treeNode);
	$("#editItemTypeClassify").val(selectNode.name);
	$("#oldTypeName").val(selectNode.name);
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
		$("[name='hospitalType']").val("null");
		$("[name='hospitalClassify']").val("null");
		$("#addItemButton").attr("disabled", true);
		$("#editButton").attr("disabled", true);
		$("#removeButton").attr("disabled", true);
	}else{
		$("[name='hospitalType']").val(selectNode.pId);
		$("[name='hospitalClassify']").val(selectNode.id);
		$("#addItemButton").attr("disabled", false);
		$("#editButton").attr("disabled", false);
		$("#removeButton").attr("disabled", false);
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
	$("#editButton").attr("disabled", true);
	$("#removeButton").attr("disabled", true);
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
function updateShow(hospitalId) {
	var url = URL.get("item.ITEM_QUERY");
	var params = "hospitalId=" + hospitalId;
	ajax.post(url, params, dataType.json, function(data){
		var value = data.data[0];
		var result = value.resultJson;//结论算法，为json格式
		$("#itemName").val(value.itemName);
		$("#itemUnit").val(value.itemUnit);
		$("#itemRefValue").val(value.itemRefValue);
		$("#itemSex").val(value.itemSex);
		$("#hospitalId").val($("#id").val());
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
 * @param hospitalId
 * @returns
 */
function editItemButton(hospitalId){
	operateType = "update";
	common.clearForm("editItemForm");
	$("#editItemForm").attr("action", URL.get("item.ITEM_UPDATE"));
	$("[name='hospitalType']").val(selectNode.pId);
	$("[name='hospitalClassify']").val(selectNode.id);
	$("#normal").val("正常");
	$("#many").val("↑");
	$("#less").val("↓");
	updateShow(hospitalId);//回显（包括json解析）
	$("#edit_item").html("编辑检验项目");
	$("#editItemModal").modal("show");
}

/**
 * 检验项目排序
 * @param hospitalId
 * @returns
 */
function moveItemOrder(hospitalId){
	$("#hospitalId").val(hospitalId);
	$("#editItemOrderModal").modal("show");
}

/**
 * 检验项目排序的编辑
 * @returns
 */
function editItemOrderButton(){
	var hospitalId = $("#hospitalId").val(); 
	var orderNum = $("#editItemOrder").val();
	if (common.isEmpty(orderNum) || isNaN(orderNum)) {
		layer.alert("不能为空，请输入数字");
		return false;
	}
	
	var itemListNow = datatable.getAllData(itemTable);
	var itemArr = [];
	for ( var i = 0; i < itemListNow.length; i++) {
		var item = itemListNow[i];
		itemArr.push(item.hospitalId);
	}
	if (orderNum > itemArr.length || orderNum < 1) {
		layer.alert("可以输入的最大值为"+itemArr.length+"，最小是为1");
		return false;
	}
	
	// 调整排序后的检验项目在数组中的位置
	itemArr.splice($.inArray(hospitalId, itemArr), 1);
	itemArr.splice(orderNum - 1, 0, hospitalId);
	
	var url = URL.get("item.ITEM_ORDER");
	var params = "hospitalIds=" + itemArr.join(",");
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
		if (selectNode.childList == undefined) {
			// 如果该类别下没有检验项目，则不需要更新检验项目的类别
			$("#editItemTypeClassifyModal").modal("hide"); 
			$("#editItemTypeClassify").val("");
			return false;
		}else{
			var type = "hospitalType";
			var pName = "";
			if (selectNode.level == 2) {
				type = "hospitalClassify";
				pName = "&pType=" + selectNode.pId;
			}else {
				if(selectNode.value != selectNode.name) {
					const all = selectNode.children;
					for(let x=0;x<all.length;x++) {
						all[x].pId = selectNode.name;
					}
				}
			}
			var url = URL.get("item.HOSPITAL_FATHER_NAME_UPDATE");
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
		const oldName = $("#oldTypeName").val();
		if(oldName != name) {
			checkNodes = selectNode.getParentNode().children;	
		};
	}
	const all = $(".node_name");
	// 存放1级菜单名称
	let array = new Array();
	let flag2 = true;
	for(let x=0;x<all.length;x++) {
		if(all.eq(x).parent().attr("class").includes("level1")) {
			array.push(all.eq(x).html());
		}
	}
	for(let x=0;x<array.length;x++) {
		if(name == array[x]) {
			layer.alert("名称已存在，请重新输入！");
			return false;
		}
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
	//初始化下拉选
	common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE","hospitalWorkWith","A");
	var url = PublicConstant.basePath + "/getChinaArea.action";
	var params = "type=none";
	ajax.post(url, params, dataType.json, function(data) {
		if (data.result) {
			for(let x=0;x<data.value.length;x++) {
				cityMap.set(data.value[x].id,data.value[x].name);
			}
		}
	},false,false);	
	//加载dataTable;
	layer.close(layer.index);
	itemTable = datatable.table(itemTableOptions);

	//定义按钮的方法
	$("button[name='itemPage']").click(function(){
		var hospitalId = $("#hospitalId").val();
		if(this.id == 'addItemButton'){
			operateType = "add";
			common.clearForm("editItemForm");
			initCity();
			//初始化省、市、区
			common.initChinaArea('0','0','0','hospitalCapital','hospitalCity','hospitalArea');
			$("input[name='tableRadio']").attr("checked",false);
			$("#editItemForm").attr("action", URL.get("item.HOSPITAL_ADD"));
			$("#hospitalClassify").attr("disabled",true);
			$("[name='hospitalType']").val(selectNode.pId);
			$("[name='hospitalClassify']").val(selectNode.id);
			$("#edit_item").html("增加医院信息");
			$("#editItemModal").modal("show");
		}else if(this.id == 'editButton' && isChoose(hospitalId)) {
			operateType = "update";
			common.clearForm("editItemForm");
			$("#editItemForm").attr("action", URL.get("item.HOSPITAL_UPDATE"));
			$("#hospitalClassify").attr("disabled",true);
			$("[name='hospitalType']").val(selectNode.pId);
			$("[name='hospitalClassify']").val(selectNode.id);
			$("#edit_item").html("修改医院信息");
			var url = URL.get("item.HOSPITAL_QUERY");
			var params = "hospitalId=" + hospitalId;
			ajax.post(url, params, dataType.json, function(data){
				const result = data.data;
				if(result.length == 1) {
					initCity();
					common.initChinaArea(result[0].hospitalCapital,result[0].hospitalCity,result[0].hospitalArea,'hospitalCapital','hospitalCity','hospitalArea');
					$("#hospitalName").val(result[0].hospitalName);
					$("#hospitalWorkWith").val(result[0].hospitalWorkWith);
					$("#hospitalId").val(hospitalId);
					$("#editItemModal").modal("show");
				}else {
					layer.msg("数据返回错误！");
				}
			},false,false);
		}else if(this.id == 'removeButton' && isChoose(hospitalId)) {
			removeClick(hospitalId);
		}else if(this.id == 'searchButton') {
//			itemTable = datatable.table(itemTableOptions);
			datatable.search(itemTable, itemTableOptions);
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