
var ramStr = 100;// 天数基础值
/*************************************************基础配置*********************************************************/
// 菜谱类别配置
var setting = {
    flag: {
        removeFlag: false,
        editNameFlag: false
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
    	onClick: zTreeOnClick,
        onExpand: function() {
            setDH();
        }
    }
};

// 菜谱列表配置
var foodTableOption = {
    id: "foodListTable",
    form: "queryForm",
    isShowRecord: false,
    bServerSide: true,
    columns: [
    {
        "data": null,
        "sClass": "text-left",
        "render": function(data, type, row, meta) {
            return data.foodName;
        }
    },
    {
        "data": null,
        "sClass": "text-center",
        "render": function(data, type, row, meta) {
            return "<a onclick=addFoodExt('" + data.foodId + "','" + data.foodName.replace(/\s/g,"") + "')>添加</a>";
        }
    }],
    condition : "foodCondition",
	async : false,
	loading : false,
	sPaginationType:"numbers"
};


// 类别树点击节点查询指定类别下的菜谱
function zTreeOnClick(event, treeId, treeNode) {	
	selectNode = treeNode;
	var id = treeNode.id;
	if ("000" == id) {
	     id = "";
	}
	$("#categoryIdQuery").val(id);
	// 菜谱列表数据加载
	foodTable = datatable.table(foodTableOption);
}

// 设置目录高度
function setDH() {
	/*$("#left_div").css("height", "auto");
	$("#cust_div").css("height", "auto");
	var hLeft = $("#left_div").height();
	var hRight = $("#cust_div").height();
	var max = hLeft > hRight ? hLeft: hRight;
	$("#left_div").height(max + 50);
	$("#cust_div").height(max + 50);*/
}

/*************************************************页面初始化*********************************************************/

$().ready(function() {
	// 主列表隐藏
    $("#showDiv").css("display", "none");
    $("#addDiv").css("display", "block");
    
    // 初始化
    $("#backButton").click(function() {
    	common.pageForward(PublicConstant.basePath + "/page/master/plan/diet_template_view.jsp");
    });
    
    // 能量范围变化
    $("#amountLevel").on("change",function(){
    	// 更改数据库能量范围
    	var url = URL.get("Master.PLAN_DIETTEMPLATEDETAIL_UPDATE");
        var params = "dietId="+dietId+"&foodDay="+$("#foodDay").val()+"&amountLevel="+$("#amountLevel").val();
        ajax.post(url, params, dataType.json,function(data) {
        	sumDosage();
        },
        false, false);
    })
    
    // 食谱类别数据源加载ztree
    $.fn.zTree.init($("#zTree"), setting, zNodes);
    treeObj = $.fn.zTree.getZTreeObj("zTree");
    treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
    
    // 初始化餐次下拉选
    common.initCodeSelect("MEALSTYPE", "MEALSTYPE", "foodMeal");
    $("#foodMeal").find("option:first-child").text("请选择餐次");
    
    // 食谱数据源加载（查询类别下的菜谱）
    foodTable = datatable.table(foodTableOption);
    
    // 获取食谱天数信息
    queryFoodDay();
});


/*************************************************菜谱列表*********************************************************/

/**
 * 添加食谱明细
 * @param foodId
 * @param foodName
 */
function addFoodExt(foodId, foodName) {
	var index = $("#buttonList .checkClass").attr("id");
	if (common.isEmpty(index)) {
		hint.warning("请先添加天数！");
		return;
	}
	
	var amountLevel = $('#amountLevel').val();
	if (common.isEmpty(amountLevel)) {
		hint.warning("请选择能量范围！");
		return;
	}
	
	var foodMeal = $('#foodMeal').val();
	if (common.isEmpty(foodMeal)) {
		hint.warning("请选择餐次！");
		return;
	}
	
	var count = $("tr[name='"+foodMeal+"_"+foodId+"']").length;
	if (count > 0) {
		hint.warning(CODE.transCode("MEALSTYPE",foodMeal)+"中已存在此菜名！");
		return;
	}
	
	var url = URL.get("Master.PLAN_DIETTEMPLATEDETAIL_ADD");
    var params = "dietId="+dietId+"&foodDay="+$("#foodDay").val()+"&foodMeal="+foodMeal+"&foodId="+foodId
    			+"&foodName="+foodName+"&amountLevel="+amountLevel;
    ajax.post(url, params, dataType.json,function(data) {
    	// 查询食谱明细
    	queryDetail();
    },
    false, false);
	
}


/*************************************************食谱列表（tab页签）*********************************************************/

/**
 * 获取食谱天数信息(天数tab页面)
 */
function queryFoodDay(){
	var url = URL.get("Master.QUERY_FOODDAY_BY_DIETID");
	var params = "dietId=" + dietId;
	ajax.post(url, params, dataType.json,function(data) {
		$("#buttonList > button[name='foodDay']").remove();
		
	    if (data.value.length >= 1) {
	    	var list = data.value;
	    	$(list).each(function(index, obj) {
	    		$("#buttonList").append("<button id='" + (index+1) + "' name='foodDay' data-id='"+obj.id+"' class='button button-default button-box button-circle' style='font-size: 14px;margin-right: 5px;color:#1B9AF7' onclick='queryDetail(" + (index+1) + ")'>" + obj.foodDay + "</button>");
	    	});
	    	
	    	// 查询食谱明细
	    	queryDetail(1);
	    }else{
	    	// 食谱页面初始化
	    	$("#addFoodExt").empty();
		    $("#amountLevel").val("");
		    $("#dosage").html("实际能量：");
	    }
	},
	false, false, false);
}

/**
 * 添加食谱天数信息(天数tab页面)
 */
function alertAddDay() {
    $("#foodAddDay").val("");
    $("#editModel").modal("show");
}
function addDayButton() {
	if(common.isEmpty($("#foodAddDay").val())){ 	
		layer.alert("一日食谱名称不能为空");
		return;
	}
	
	var foodDay = $("#foodAddDay").val();
	var validRep = true;
	$(".button-circle").each(function(index, value){
		if(foodDay == $(value).text().trim()){
			validRep = false;
		}
	});
	if(!validRep){
		layer.alert("一日食谱名称不能重复");
		return false;
	}
	
	// 确保不重复,确定位置
	index = ramStr + 1;
	$("#buttonList").append("<button id='" + index + "' name='foodDay' data-id='' class='button button-default button-box button-circle' style='font-size: 14px;margin-right: 5px;color:#1B9AF7' onclick='queryDetail(" + index + ")'>"+ foodDay + "</button>");
	
	// 数据初始化
	$("#"+index).addClass("checkClass").siblings().removeClass("checkClass");
	$("#addFoodExt").empty();
    $("#amountLevel").val("");
    $("#dosage").html("实际能量：");
    
    $("#foodDay").val(foodDay);
    $("#editModel").modal("hide");
}

/**
 * 删除食谱天数信息(天数tab页面)
 */
function removeFoodDay() {
	if($("#buttonList > button[name='foodDay']").length == 0){
		return;
	}
	
	hint.confirm("确定执行【删除】操作？",function(data) {
		var url = URL.get("Master.REMOVE_FOODDAY_BY_DIETIDANDFOODDAY");
	    var params = "dietId=" + dietId + "&foodDay=" + $("#foodDay").val();
	    ajax.post(url, params, dataType.json,function(data) {
	        if (data.result) {
	        	// 重新加载tab页天数
	        	queryFoodDay();
	        }
	    });
	});
}

/*************************************************食谱列表（明细）*********************************************************/

/**
 * 获取食谱明细数据(已选食材列表)
 */
function queryDetail(index){
	// 选中样式切换
	if(common.isEmpty(index)){
		index = $("#buttonList > .checkClass").attr("id");
	}else{
		$("#"+index).addClass("checkClass").siblings().removeClass("checkClass");
	}
	// tab名称替换
	var foodDay = $("#" + index).text();
    $("#foodDay").val(foodDay);
	
    var url = URL.get("Master.QUERY_FOODDAY_BY_DIETIDANDFOODDAY");
    var params = "dietId="+dietId+"&foodDay="+$("#foodDay").val();
    ajax.post(url, params, dataType.json,function(data) {
    	// 数据初始化
    	$("#addFoodExt").empty();
	    $("#amountLevel").val("");
	    $("#dosage").html("实际能量：");
    	
        // 数据渲染
	    result = data.value;
        if (!common.isEmpty(result) && result.length > 0) {
        	// 能量范围下拉框回显
            $("#amountLevel").val(result[0].amountLevel);
        	
            for (var x = 0; x < result.length; x++) {
            	loadDetailHtml(result[x]);
            }
            
            // 实际能量值计算
            sumDosage();
        } else if(!common.isEmpty($("#"+index).data("id"))){
        	// 重新加载tab页标签（删除最后一个菜名时）
        	queryFoodDay();
        }
    },
    false, false);
}

/**
 * 编辑食谱明细（更改食材用量）
 * @param obj
 * @param id
 */
function changeAmount(obj,id,fmId) {
	var amount = obj.value;
	var url = URL.get("Master.PLAN_DIETTEMPLATEDETAIL_UPDATE");
    var params = "id="+id+"&foodMaterialAmount="+amount;
    ajax.post(url, params, dataType.json,function(data) {
    	// 实际能量值计算
    	$("#"+id+"_dosage").html(queryFmDosage(fmId, amount));
    	sumDosage();
    },
    false, false);
}


/**
 * 删除食谱明细（菜品）
 * @param foodId
 * @param foodMeal
 */
function removeFoodExt(foodId,foodMeal) {
    hint.confirm("确定执行【删除】操作？",function(data) {
    	var url = URL.get("Master.PLAN_DIETTEMPLATEDETAIL_REMOVE");
		var params = "dietId=" + dietId +"&foodDay=" + $("#foodDay").val() +"&foodMeal=" + foodMeal +"&foodId=" + foodId;
		ajax.post(url, params, dataType.json,function(data) {
			// 查询食谱明细
			queryDetail();
		});
    });
}

// 食谱html数据渲染
function loadDetailHtml(detail){
	if(common.isEmpty(detail)){
		return;
	}
	// 数据参数获取
	var id = detail.id || "";//主键
	var foodMeal = detail.foodMeal || "";// 餐次
	var foodMealName = CODE.transCode("MEALSTYPE",foodMeal);
	var foodId = detail.foodId || "";// 菜谱id
	var foodName = detail.foodName || "";// 菜谱名称
	var fmId = detail.fmId || "";// 食材id
	var foodMaterialName = detail.foodMaterialName || "";// 食材名称
	var foodMaterialAmount = detail.foodMaterialAmount || "";// 用量
	var amountLevel = detail.amountLevel || "";// 能量范围级别
	
	// 食材能量计算
	var nutrientDosage = "";
	var amountHtml = "";
	if(!common.isEmpty(fmId)){
		nutrientDosage = common.isEmpty(foodMaterialAmount)?"":queryFmDosage(fmId, foodMaterialAmount);
		amountHtml = `<input type="text"  class="intake-sm text-center enter-input" onkeyup="checkNum(this);" style="width:100%;" name="foodMaterialAmount" value="${foodMaterialAmount}" maxlength="10" onkeypress="if(event.keyCode==13) focusNextInput(this);" onchange="changeAmount(this,'${id}','${fmId}');">`;
	}
	
	// 合并列部分
	var publicHtml = `
				<td class="text-center" style="width: 15%;">${foodMealName}</td> 
				<td class="text-center" style="width: 24%;border-right: 0;">${foodName}</td> 
				<td style="width: 1%;border-left:0;"><a class="text-right" style="color:red;" onclick="removeFoodExt('${foodId}','${foodMeal}')">X</a></td>
				`;
	if($("tr[name='"+foodMeal+"_"+foodId+"']").length > 0){
		publicHtml = ``;
		
		var count = $("tr[name='"+foodMeal+"_"+foodId+"']").length + 1;
		$("tr[name='"+foodMeal+"_"+foodId+"'] td").eq(0).attr("rowspan",count);
		$("tr[name='"+foodMeal+"_"+foodId+"'] td").eq(1).attr("rowspan",count);
		$("tr[name='"+foodMeal+"_"+foodId+"'] td").eq(2).attr("rowspan",count);
	}
    var html = `<tr id="${id}" name="${foodMeal}_${foodId}"> 
    				${publicHtml}
					<td class="text-center" style="width: 25%;">${foodMaterialName}</td> 
					<td class="text-center" style="width: 15%;">
						${amountHtml}	
					</td>
					<td class="text-center" style="width: 20%;" name="dosage" id="${id}_dosage">${nutrientDosage}</td>
				</tr>`;
    $("#addFoodExt").append(html);
}

/*************************************************工具类*********************************************************/

/**
* 回车键将焦点定义到下一个输入框
* @param thisInput
*/
function focusNextInput(thisInput){
	var inputs = $("#addFoodExt").find(".enter-input");
	for(var i = 0;i<inputs.length;i++){
		// 如果是最后一个，则焦点回到第一个
		if(i==(inputs.length-1)){
			inputs[0].focus();
			break;
		}else if(thisInput == inputs[i]){
			inputs[i+1].focus();
			break;
		}
	}
}

/**
* 食材能量计算
* @param fmId
* @param amount
*/
function queryFmDosage(fmId, amount) {
	var donsage = "";
	
	// 获取食材对象
	if(!common.isEmpty(fmId) && !common.isEmpty(amount)){
		var fm = getFmBase(fmId);
		// 获取可食用部分
		var fmEsculent = fm.fmEsculent;
		var tempEleArray = ajaxData(fmId);
		for (var i = 0; i <tempEleArray.length; i++){
			if (tempEleArray[i].nutrientDosage > 0 && tempEleArray[i].nutrientId == 'E00001') {
				tempEleArray[i].nutrientDosage = common.accMul(tempEleArray[i].nutrientDosage, amount);
				if (fmEsculent != null) {
					//计算可食用部分
					tempEleArray[i].nutrientDosage = common.accMul(tempEleArray[i].nutrientDosage, fmEsculent);
					tempEleArray[i].nutrientDosage = common.accDiv(tempEleArray[i].nutrientDosage, 100);
				}
				donsage = Math.ceil(common.accDiv(tempEleArray[i].nutrientDosage, 100));
			}		 
		}	
	}
	return donsage;
}

/**
 * 获取食材基本信息
 * @params fmId 食材id
 */
function getFmBase(fmId) {
	var tempEleArray = {};
	var url = URL.get("foodMaterial.GET_FM");
	var params = "fmId=" + fmId;
	ajax.post(url, params, dataType.json, function(data) {		
		tempEleArray = data.value;		
	},false,false);
	return tempEleArray;	
}

/**
 * 获取食材元素数据
 * @params fmId 食材id
 */
function ajaxData(fmId) {
	var tempEleArray = [];
	var url = URL.get("foodMaterial.GET_FOOD_ELEMENT");
	var params = "fmId=" + fmId;
	ajax.post(url, params, dataType.json, function(data) {		
		tempEleArray = data.data;		
	},false,false);
	return tempEleArray;	
}


/**
* 实际能量值计算
*/
function sumDosage(){
	var dosageCount = 0;
	$("td[name='dosage']").each(function(index, obj) {
		var id = obj.id;
		if(!common.isEmpty($("#"+id).html())){
			dosageCount = dosageCount + parseFloat($("#"+id).html());
		}
	});
	
	// 获取能量范围值
	var dosageUpperLimit = 0;
	var selectObj = document.getElementById('amountLevel');   
	if(!common.isEmpty($("#amountLevel").val())){
		dosageUpperLimit = $("#amountLevel").val().split("~")[1];
	}
	
	// 提示语
	var message = "实际能量："+dosageCount;
	if(dosageCount > dosageUpperLimit){
		message = message + "<span style='color:red;'>(高于能量推荐范围)</span>"
	}
	$("#dosage").html(message);
}

/**
 * 检验input只能为数字
 */
function checkNum(obj) {
    var flag = true;
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
        flag = false;
    }
    if (obj != null) {
        if (obj.value.toString().split(".").length > 1) { //如果是小数
                layer.msg("请输入整数！");
                obj.value = "";
                flag = false;
        } else { //如果是整数
            if (obj.value.toString().length > 1 && obj.value.toString().substring(0, 1) == "0") { //如果是这种格式 01 001 001 0001
                layer.msg("请输入正常整数！");
                obj.value = "";
                flag = false;
            }
            if (obj.value.toString() == "0") {
                layer.msg("不能输入0");
                obj.value = "";
                flag = false;
            }	
        }
    }
    return flag;
};
