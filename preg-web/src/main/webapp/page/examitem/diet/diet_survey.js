//食物信息
var foodRowData;
//每100g食物的能量值
var perFoodEnergy;
//旧食物重量
var foodAmountOld;
//食物图片地址
var serverPath = URL.get("resource.server.path")+URL.get("path.constants.food_cuisine_image");
//datable参数设置
var foodData;
var foodRowData;
var foodTable;

var foodTableOption = {
		id:"foodTable",
		form:"foodQuery",
		bServerSide: true,
		columns: [
		  		{"data": null,"sClass": "text-center",
		  			"render":  function (data, type, row, meta) {
		          		return "<input type='radio' name='foodChooseRadio' value='"+data.foodId+"'/>";
		          	}
		  		},
		  		{"data": null,"sClass": "text-center",
		  			"render":  function (data, type, row, meta) {
		          		return "<img src='"+serverPath+data.foodPic+"' width='64' height='64' class='img-thumbnail'/>";
		          	}
		  		},
		  		{"data": "foodName","sClass": "text-center" },
		  		{"data": "foodEnergy","sClass": "text-center"},
		  		{"data": null,"sClass": "text-center",
					"render":function(data, type, row, meta){
						return "<button class='btn btn-primary btn-xs' type='button' onclick='chooseFood()'><i class='fa fa-plus fa-fw'></i>添加</button>";
					}
			  	}
		],
		rowClick: function(data, row){
			foodData = data;
			foodRowData = row;
		},
		condition : "foodCondition",
		async: false,
		iDisplayLength:5
};
/**
 * 验证参数设置
 */
var recordMealsOptions = {
	rules: {
		mealsWater: {
			intege1: true,
			maxlength: 4,
			max: 5000
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		$("#recordMealsSubmit").attr("disabled", true);
		$("#recordMealsForm").ajaxPost(dataType.json,function(data){
			var foodRecordId = data.value;
			opener.updateInspectStatus($("#inspectId").val(), foodRecordId);
			var ajaxUrl = URL.get("DietFood.MEALS_REPORT")+"?id="+$("#inspectId").val();
			var params = "";
			ajax.get(ajaxUrl, params, dataType.json, function(data){
				window.location.href = PublicConstant.basePath +"/"+ data.value;
			});
		});
	}
};

$().ready(function() {
	//是否规律
	common.initCodeRadio("pregnancy_rule","pregnancy_rule","pregnancy_rule","pregnancyRule");
	//饮食速度
	common.initCodeRadio("pregnancy_speed","pregnancy_speed","pregnancy_speed","pregnancySpeed");
	//在外用餐频率
	common.initCodeRadio("pregnancy_frequency","pregnancy_frequency","pregnancy_frequency","pregnancyFrequency");
	//饮食喜好
	common.initCodeCheckbox("pregnancy_like","pregnancy_like","pregnancy_like","pregnancyLike");
	//饮食感受
	common.initCodeRadio("pregnancy_feel","pregnancy_feel","pregnancy_feel","pregnancyFeel",12);
	//饮食避免
	common.initCodeCheckbox("pregnancy_avoid","pregnancy_avoid","pregnancy_avoid","pregnancyAvoid",12);
	//饮食推荐摄入量
	common.initCodeCheckbox("pregnancy_recommend","pregnancy_recommend","pregnancy_recommend","pregnancyRecommend",12);
	//烹饪方式
	common.initCodeSelect("COKE_MODE","COKE_MODE","cook");
	
	$("#recordMealsForm input[name='diagnosisId']").val($("#diagnosisId").val());
	//初始化摄入频率
	initFoodFrequencyForm();
	
	//绑定控件只输入数字
	$("input[input-valid='number']").bindNumber();
	
	//初始化检索食物action
	//$('#foodQuery').attr('action', URL.get("DietFood.RECORD_FOOD_QUERY"));
	
	foodTable = datatable.table(foodTableOption);	
	
//	$('#t_body').datatable({
//		formName : 'foodQuery',
//		pageSize : 5,
//		seletPageSize : false,
//		pageList : function(data,start,end){
//			pageList(data,start,end);
//		},
//		clickRow : function(index,rowData){
//			foodRowData = rowData;
//		}
//	});
//	
//	function pageList(data,start,end){
//		for(var i=start;i<end;i++){
//			var tableData = data[i];
//			var foodPic = serverPath + tableData.foodPic;
//			if(common.isEmpty(tableData.foodPic)){
//				foodPic = PublicConstant.basePath+"/upload/nophoto.jpg";
//			}
//			$("#t_body").append(
//				"<tr id='"+tableData.foodId+"_add'>"+
//				"   <td class='text-center' ><input type='radio' name='foodChooseRadio' value='"+tableData.foodId+"'/></td>" +
//				"   <td class='text-center' ><img src='"+foodPic+"' width='64' height='64' class='img-thumbnail'/></td>" +
//				"   <td class='text-center' style='vertical-align:middle;'>" + tableData.foodName + "</td>" +
//				"   <td class='text-center' style='vertical-align:middle;'>" + tableData.foodEnergy + "</td>" +
//				"   <td class='text-center' style='vertical-align:middle;'>" + 
//				"      <button class='btn btn-primary btn-xs' type='button' onclick='chooseFood()'><i class='fa fa-plus fa-fw'></i>添加</button>" + 
//				"   </td>" +
//				"</tr>"
//			);
//			$('#'+tableData.foodId+"_add").data(tableData);
//		}
//	}	
	//回车事件
    $('#fmName').keydown(function(event) {
		if ((event.keyCode || event.which) == 13) {
			queryFoodByCondition();
		}
	});
	
	//获取焦点时自动清空检索条件
	$("#foodName").focus(function(){
		$("#foodName").val("");
		$("#foodQuery [name='foodName']").val("");
	});
	
	$("#recordMealsSubmit").click(function(){
		$("#recordMealsForm").attr('action',URL.get("DietFood.RECORD_MEALS_SURVEY"));
		$("#recordMealsForm").submit();
	});
	//添加表单验证
	$("#recordMealsForm").validate(recordMealsOptions);
	
	$("#foodName").keydown(function(event) {
		if ((event.keyCode || event.which) == 13) {
			queryFoodByCondition();
		}
	});
	
	$("input:radio[name='editFoodAmountRadio']").click(function(){
		$("input[name='inputRadio']").attr("readonly", true);
		$("input[name='inputRadio']").attr("value", "");
		var $input = $("#"+this.value);
		$input.attr("readonly",false);
		if($input.attr("id") != "0"){
			$input.val(1);
		}
	});
	
	$("input[name='inputRadio']").click(function(){
		$("input[name='inputRadio']").attr("readonly", true);
		$("input[name='inputRadio']").attr("value", "");
		$("input:radio[name='editFoodAmountRadio'][value='"+this.id+"']").attr("checked", true);
		var $input = $(this);
		$input.attr("readonly",false);
		if(this.id != "0"){
			$input.val(1);
		}
	});
	
	$("a[data-parent='#mealsAccordion']").click(function(){
		var mealsId = this.id.split("_")[0];
		$("#mealsType").val(mealsId);
		if($("#collapse_"+mealsId).attr("class") != "collapse in"){
			$("a[data-parent='#mealsAccordion']").removeClass("disabled");
			$(this).addClass("disabled");
			$("#collapse_"+mealsId).attr("class", "collapse");
			changeFoodQuery(mealsId);
		}
	});
});



/**
 * 初始化饮食频率表单
 */
function initFoodFrequencyForm(){
	var url = URL.get("System.CODE_CACHE_GET");
	var params = "codeKind=PREGNANCY_ITEM&codeValue=PREGNANCY_ITEM";
	ajax.post(url, params, dataType.json, function(data){
		if(data.result){
			if(!common.isEmpty(data.value)){
				var count = 0;
				$(data.value).each(function(index, code){
					if(code.codeValue == "water"){
						$("#food_frequency_body").append(
							"<tr>"+
							"   <td class='text-center'>"+code.codeName+"<input type='hidden' name='foodFrequencyList["+count+"].foodType' value='"+code.codeValue+"'></td>"+
							"   <td class='text-center'>饮水</td>"+
							"   <td class='text-center' colspan='3'>"+
							"		<input type='hidden' id='"+code.codeValue+"_foodCycle' name='foodFrequencyList["+count+"].foodCycle' value='day' />"+
							"		<input type='hidden' id='"+code.codeValue+"_number' name='foodFrequencyList["+count+"].averageNumber' value='1'/>"+
							"   	<input type='text' id='"+code.codeValue+"_intake' name='foodFrequencyList["+count+"].averageIntake' class='form-control' input-valid='number' maxlength='6' placeholder='请输入摄入量'/>"+
							"	</td>"+
							"</tr>"
						);
						count++;
					}else {
						$("#food_frequency_body").append(
							"<tr>"+
							"   <td class='text-center'>"+code.codeName+"<input type='hidden' name='foodFrequencyList["+count+"].foodType' value='"+code.codeValue+"'></td>"+
							"   <td class='text-center'><select class='form-control' id='"+code.codeValue+"_foodCycle' name='foodFrequencyList["+count+"].foodCycle'><option value='day'>每日</option><option value='week'>每周</option></select></td>"+
							"   <td class='text-center'>"+
							"		<div class='col-xs-11 no-left no-right'><input type='text' id='"+code.codeValue+"_number' name='foodFrequencyList["+count+"].averageNumber' class='form-control' input-valid='number' maxlength='2' placeholder='请输入次数'/></div>"+
							"		<div class='col-xs-1 text-center no-left no-right'><label class='control-label' style='font-weight: unset;'>次</label></div>"+
							"	</td>"+
							"   <td class='text-center'><input type='text' id='"+code.codeValue+"_intake' name='foodFrequencyList["+count+"].averageIntake' class='form-control' input-valid='number' maxlength='6' placeholder='请输入摄入量'/></td>"+
							"   <td class='text-center'><select class='form-control' id='"+code.codeValue+"_averageIntake' name='foodFrequencyList["+count+"].averageIntakeType'><option value='g'>克</option><option value='f'>份</option></select></td>"+
							"</tr>"
						);
						count++;
					}
				});
				$("input[input-valid='number']").bindNumberOrFloat();
				$("#shulei_foodCycle").val("week");
			}
		}
	}, false);
}

//加入到行操作
function appendFoodRow(){
	var obj = $("input:radio[name='editFoodAmountRadio'][checked]");
	if(obj.length==0 || common.isEmpty($("#"+obj.attr("value")).val())){
		 layer.msg('请填写食物重量！');
		return false;
	}
	$("#foodAmountModal").modal("hide");
	var mtypeId = $("#mealsType").val();
	var foodId = foodData.foodId;
	var foodName = foodData.foodName;
	var foodAmount = calculateFoodAmount(obj.attr("value"), $("#"+obj.attr("value")).val());
	var foodEnergy = foodData.foodEnergy;
	var energy = Math.round(parseInt(foodEnergy) * (parseInt(foodAmount)/100));
	var id = mtypeId + "_" +foodId;
	//验证行是否存在
	if($("#" + id + "_tr").length != 0){
		return;
	}
	//清空
	if($("#"+mtypeId+"_tr_init").length!=0){
		$("#"+mtypeId+"_tr_init").remove();
	}
	//获取模板
	var foodDataTemplate = {id:id,foodName:foodName,foodAmount:foodAmount,foodEnergy:energy};
	var addFoodTemplateScript = $("#add-food-template").html();
	//编译模板
	var addFoodTemplate = Handlebars.compile(addFoodTemplateScript);
	//输出HTML
	$("#"+mtypeId+"_body").append(addFoodTemplate(foodDataTemplate));
	
	$("#"+mtypeId+"_a").append("<span id='"+mtypeId+"_"+foodId+"_span'>"+foodName+foodAmount+"g；&nbsp;&nbsp;</span>");
	
	$("#recordMealsForm").append(
		"<input id='"+id+"_text' name='foodList' type='hidden' value='"+mtypeId + "_" +foodId +"_" + foodAmount+"' />"+
		"<input id='"+id+"_perFoodEnergy' type='hidden' value='"+foodEnergy+"' />"
	);
	//初始化编辑重量页面
	$("input:radio[name='editFoodAmountRadio']").attr("checked", false);
	$("input[name='inputRadio']").attr("readonly", true);
	$("input[name='inputRadio']").attr("value", "");
};

/**
 * 计算食物重量
 * @param type
 * @param mount
 * @returns {String}
 */
function calculateFoodAmount(type, mount){
	var foodAmount = "";
	if(type=="0"){
		foodAmount = mount;
	}else if(type=="1"){
		foodAmount = common.accMul(mount, "20");
	}else if(type=="2"){
		foodAmount = common.accMul(mount, "30");
	}else if(type=="3"){
		foodAmount = common.accMul(mount, "50");
	}else if(type=="4"){
		foodAmount = common.accMul(mount, "100");
	}else if(type=="5"){
		foodAmount = common.accMul(mount, "150");
	}else if(type=="6"){
		foodAmount = common.accMul(mount, "300");
	}else if(type=="7"){
		foodAmount = common.accMul(mount, "50");
	}else if(type=="8"){
		foodAmount = common.accMul(mount, "100");
	}else if(type=="9"){
		foodAmount = common.accMul(mount, "150");
	}
	return foodAmount;
}

/**
 * 选择食物
 */
function chooseFood(){
	var mtypeId = $("#mealsType").val();
	if(common.isEmpty(mtypeId)){
		layer.msg('请选择餐次！');
		return false;
	}
	$("#foodAmountModal").modal("show");
};

/**
 * 删除食物记录
 * @param id
 */
function removeFood(id){
	//餐次编号
	var mealsId = id.split("_")[0];
	//删除行
	$("#" + id + "_tr").remove();
	//删除食物记录
	$("#" + id + "_text").remove();
	//删除食物记录
	$("#" + id + "_span").remove();
	//删除食物总览记录
	$("#" + mealsId + "_span").remove();
	//为空添加提示语
	if($("#" + mealsId + "_body").children().length==0){
		$("#" + mealsId + "_body").append(
			"<tr id='"+mealsId+"_tr_init'><td>请从左边选择食物，然后点击 “添加”。</td></tr>"
		);
	}
};

/**
 * 计算获取每100g食物的能量值
 */
function getPerFoodEnergy(id){
	var foodEnergy = $("#"+id+"_foodEnergy").html();
	var foodAmount = $("#"+id+"_foodAmount").val();
	foodAmountOld = foodAmount;
	perFoodEnergy = Math.round(common.accDiv(foodEnergy,foodAmount/100));
};

/**
 * 食物重量改变后触发，修改食物重量和能量值
 */
function changeFoodAmount(id){
	var submitFlag = true;
	var foodAmount = $("#"+id+"_foodAmount").val();
	if(common.isEmpty(foodAmount)){
		layer.msg('食物重量不能为空！');
		$("#"+id+"_foodAmount").val(foodAmountOld);
		submitFlag = false;
	}
	var reg = /^[0-9]*$/;
	if(!reg.test(foodAmount)){
		layer.msg('食物重量请输入整数！');
		$("#"+id+"_foodAmount").val(foodAmountOld);
		submitFlag = false;
	}
	if(submitFlag){
		var perFoodEnergy = $('#'+id+"_perFoodEnergy").val();
		var foodEnergy = Math.round(perFoodEnergy * (parseInt(foodAmount)/100));
		var foodName = $('#'+id+"_foodName").html();
		$("#"+id+"_foodEnergy").html(foodEnergy);
		$("#"+id+"_text").val(id+"_"+foodAmount);
		$("#"+id+"_span").html(foodName+foodAmount+"g；&nbsp;&nbsp;");
	}
};

/**
 * 设置检索条件，检索食物
 */
function queryFoodByCondition(){
	copyField();
	if(!common.isEmpty($("#foodName").val()) || !common.isEmpty($("#fmName").val()) || !common.isEmpty($("#cook").val())){
		copyField();
		$("#foodQuery [name='foodMealType']").val("");
		datatable.table(foodTableOption);
	}else{
		changeFoodQuery($("#mealsType").val());
	}
}

/**
 * 餐次变化触发查询食物事件
 * @param mealsId
 */
function changeFoodQuery(mealsId){
	$("#foodName").val("");
	$("#cook").val("");
	$("#fmName").val("");
	copyField();
	if(mealsId=="C00000CA000000000001"){
		$("#foodQuery [name='foodMealType']").val("10");
	}else if(mealsId=="C00000CA000000000003"){
		$("#foodQuery [name='foodMealType']").val("12");
	}else if(mealsId=="C00000CA000000000005"){
		$("#foodQuery [name='foodMealType']").val("14");
	}else{
		$("#foodQuery [name='foodMealType']").val("");
	}
	datatable.table(foodTableOption);
}

/**
 * 复制
 */
function copyField(){
	$("#foodQuery [name='foodName']").val($("#foodName").val());
	$("#foodQuery [name='foodCuisine']").val($("#cook").val());
	$("#foodQuery [name='fmName']").val($("#fmName").val());
}
