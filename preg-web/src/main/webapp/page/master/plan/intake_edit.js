/*
 * 膳食模板编辑页面用JavaScript文件
 *
 * 北京麦芽健康管理有限公司
 * 
 * 变更履历：
 *   v1.0  wsy  2017-2-18  初版
 */
$().ready(function() {
	$("#intakeEditForm").validate(intakeOption);
	common.requiredHint("intakeEditForm", intakeOption);
	initEditSelect();
	// 加载数据
	initEditPage();
	datatable.table(dtableOptions);

	$(".intak-input").bindNumberOrFloat();

	intakeDetailOnChange();
});
var intakeOption = {
	rules : {
		intakeName : {
			required : true,
			remote : {
				url : URL.get("Master.PLAN_INTAKE_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					intakeId : function() {
						return $("#intakeEditForm").find("input[name='intakeId']")
								.val();
					},
					intakeName : function() {
						return $("#intakeName").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		intakeMode : {
			required : true
		},
		pregnantStage : {
			required : true
		}
	},
	messages : {
		intakeName : {
			remote : "该模板名称已经存在，请重新输入"
		}
	},
	errorPlacement: function(error, element) {
		$(element).focus();
		element = element.parent();
		common.showmassage(error, element);
	}
};
function closeWinsow() {
	window.close();
}
/**
 * 表单提交方法
 */
function doSubmitIntakeDetail() {
	//必填提示
	var vilidate=$("#intakeEditForm").validate(intakeOption).form();
	if(!vilidate){
		return;
	}
	$("#detailsJson").val(JSON.stringify(getAllIntake()));
	$('#intakeEditForm').ajaxPost(dataType.json,function(data){
		if (data.value) {
			$("#intakeId").val(data.value);
			window.opener.reLoadTable();
			layer.alert("保存成功！", function() {
				window.close();
				window.location.href = (URL.get("Master.PLAN_INTAKE_EDIT_INIT") + "?intakeId=" + data.value);
			});
		}
	});
}
/**
 * 获取所有膳食明细方法
 */
function getAllIntake() {
	var detailArray = new Array();
	revertJson(detailArray, "C00000CA000000000001");// 早餐
	revertJson(detailArray, "C00000CA000000000002");// 上午加餐
	revertJson(detailArray, "C00000CA000000000003");// 午餐
	revertJson(detailArray, "C00000CA000000000004");// 下午加餐
	revertJson(detailArray, "C00000CA000000000005");// 晚餐
	revertJson(detailArray, "C00000CA000000000006");// 睡前
	return detailArray;
}
/**
 * 根据餐次获取膳食明细方法
 */
function revertJson(array, canci) {
	// 获取摄入类型数据
	$('#' + canci + " #intake input").each(function(index, element) {
		if (!common.isEmpty($(element).val())) {
			array.push({
				"intakeMealtype" : canci,
				"intakeFoodType" : "intake",
				"intakeType" : element.id,
				"intakeCount" : $(element).val(),
				"intakeTypeName" : element.name
			});
		}
	});
	// 获取产品数据
	$('#' + canci + " #product input").each(function(index, element) {
		if (!common.isEmpty($(element).val())) {
			array.push({
				"intakeMealtype" : canci,
				"intakeFoodType" : "product",
				"intakeType" : element.id,
				"intakeCount" : $(element).val(),
				"intakeTypeName" : element.name
			});
		}
	});
}
/**
 * 弹出选择商品方法
 */
function showProductModal(canciId) {
	$("#canciId").val(canciId);
	var choosedArr = [];
	// 循环已选中的产品
	$('#' + canciId + " #product input").each(function(index, element) {
		choosedArr.push(element.id);
	});
	// 定义选择框内容html文本
	var productListDivHTML = "";
	$(productList).each(
			function(index, element) {
				// 已选中的商品填写的数量，默认为空
				var productCountValue = "";
				// 判断是否已选中
				var isCheckedHTML = "";
				if ($.inArray(element.productId, choosedArr) > -1) {
					isCheckedHTML = "checked='checked'";
					// 如果有已选中的商品、打开列表时把值带入
					productCountValue = $('#' + canciId + " #product").find("#" + element.productId).val();
				}
				//规则：有产品名称优先产品名称，商品名称+“ ”+通用名称第二，如果都没有则为空
				var endName = "";
				if(!_.isEmpty(element.productName)) {
					endName = element.productName;
				}else {
					endName = element.productGoodsName+" "+element.productCommonName;
				}
				if(!_.isEmpty($.trim(endName))){
					// 每条选项的html文本
			        var listRow =   "<label class='checkbox-inline'>" +
	                                    "<input type='checkbox' "+isCheckedHTML+" name='productListBox' value='"+element.productId+"'/>" +
	                                    "<span id='product_name_"+element.productId+"'>"+endName+"</span>" +
	                                    "<input type='text' id='count_"+element.productId+"' value='"+ productCountValue +"' class='intak-input' maxlength='4'/>份、" +
	                                "</label>";
					productListDivHTML = productListDivHTML + listRow;
				}
			});
	$("#productListDiv").html(productListDivHTML);
	$(".intak-input").bindNumberOrFloat();
	$("#productListModal").modal('show');
}
/**
 * 选择商品确认方法
 */
function chooseProduct() {
	// 当前选中餐次的id
	var canciId = $("#canciId").val();
	var chooseProductHTML = "";
	$("#productListDiv input:checkbox[name='productListBox']:checked").each(
			function(index, product) {
				var product_name = $("#product_name_" + product.value).html();
				var countValue = $("#count_" + product.value).val();
				chooseProductHTML = chooseProductHTML + product_name + "<input name='" + product_name + "' type='text' id='" + product.value
						+ "' value='" + countValue + "' class='intak-input' intak-input maxlength='4'/>份、";
			});
	chooseProductHTML = "<a href='javascript:showProductModal(\"" + canciId + "\");'><i class='fa fa-plus-circle'></i> 添加食品</a>&nbsp;&nbsp;"
			+ chooseProductHTML;

	$("#" + canciId + " #product").html(chooseProductHTML);
	// 重新绑定事件
	$(".intak-input").bindNumberOrFloat();
	intakeDetailOnChange();
	$("#productListModal").modal('hide');
	countEnergyAccount();
}
/**
 * 膳食明细输入框绑定onchange事件
 */
function intakeDetailOnChange() {
	$("#intakeDetailTable input").change(function() {
		countEnergyAccount();
	});
}
/**
 * 能量占比计算方法 全局参数 productList 、intakeTypeList 传入参数 所有食物组成的数组
 */
function countEnergyAccount() {
	var chooseIntakeList = getAllIntake();
	// 总热量
	var sumEnergy = 0;
	// 总碳水化合物
	var sumCbr = 0;
	// 总蛋白质
	var sumProtein = 0;
	// 总脂肪
	var sumFat = 0;
	$(chooseIntakeList).each(function(index, element) {
		/*
		 * 如果是摄入类型遍历intakeTypeList取值 如果是产品遍历productList取值
		 */
		if ("intake" == element.intakeFoodType) {
			$(intakeTypeList).each(function(index, srcIntake) {
				if (element.intakeType == srcIntake.code) {
					sumEnergy = common.accAdd(sumEnergy, common.accMul(srcIntake.unitEnergy, element.intakeCount));
					sumCbr = common.accAdd(sumCbr, common.accMul(srcIntake.unitCbr, element.intakeCount));
					sumProtein = common.accAdd(sumProtein, common.accMul(srcIntake.unitProtein, element.intakeCount));
					sumFat = common.accAdd(sumFat, common.accMul(srcIntake.unitFat, element.intakeCount));
				}
			});
		}
		if ("product" == element.intakeFoodType) {
			$(productList).each(function(index, srcIntake) {
				if (element.intakeType == srcIntake.productId) {
					sumEnergy = common.accAdd(sumEnergy, common.accMul(srcIntake.unitEnergy, element.intakeCount));
					sumCbr = common.accAdd(sumCbr, common.accMul(srcIntake.unitCbr, element.intakeCount));
					sumProtein = common.accAdd(sumProtein, common.accMul(srcIntake.unitProtein, element.intakeCount));
					sumFat = common.accAdd(sumFat, common.accMul(srcIntake.unitFat, element.intakeCount));
				}
			});
		}
	});
	// 设置数值到表单中
	sumEnergy = Math.round(sumEnergy);
	sumCbr = Math.round(sumCbr);
	sumProtein = Math.round(sumProtein);
	sumFat = Math.round(sumFat);

	// 三大营养素含量
	var energyMin = Math.floor(sumEnergy / 200) * 200, energyMax = Math.ceil(sumEnergy / 200) * 200;
	if (energyMin == energyMax) {
		energyMax = energyMax + 200;
	}

	$("#intakeActualEnergy").val(energyMin + "~" + energyMax + "");
	$("#intakeCbr").val(sumCbr + " ± " + "5");
	$("#intakeProtein").val(sumProtein + " ± " + "2");
	$("#intakeFat").val(sumFat + " ± " + "2");

	// 三大营养素占比
	var cbrPercent = common.accMul(common.accDiv(common.accMul(sumCbr, 4), sumEnergy).toFixed(2), 100);
	var proteinPercent = common.accMul(common.accDiv(common.accMul(sumProtein, 4), sumEnergy).toFixed(2), 100);
	var fatPercent = common.accMul(common.accDiv(common.accMul(sumFat, 9), sumEnergy).toFixed(2), 100);

	var cbrPercentMin = (cbrPercent < 1) ? cbrPercent : (cbrPercent - 1), cbrPercentMax = cbrPercent + 1;
	var proteinPercentMin = (proteinPercent < 1) ? proteinPercent : (proteinPercent - 1), proteinPercentMax = proteinPercent + 1;
	var fatPercentMin = (fatPercent < 1) ? fatPercent : (fatPercent - 1), fatPercentMax = fatPercent + 1;

	$("#intakeCbrPercent").val(cbrPercentMin + "" + "~" + cbrPercentMax + "");
	$("#intakeProteinPercent").val(proteinPercentMin + "" + "~" + proteinPercentMax + "");
	$("#intakeFatPercent").val(fatPercentMin + "" + "~" + fatPercentMax + "");
}
/**
 * 初始化孕期阶段、膳食模式下拉选
 */
function initEditSelect() {
	common.initCodeSelect("PREGNANT_STAGE", "PREGNANT_STAGE", "pregnantStage", "", "请选择孕期阶段");
	common.initCodeSelect("PREGNANT_STAGE", "PREGNANT_STAGE", "dietPregnantStageSelect", "", "请选择孕期阶段");
	common.initCodeSelect("INTAKE_MODE", "INTAKE_MODE", "intakeMode");
}
/**
 * 初始化编辑页面
 */
function initEditPage() {
	if (!common.isEmpty(intakeDTO)) {
		$("#intakeEditForm #intakeId").val(intakeDTO.intakeId);
		$("#intakeEditForm #intakeName").val(intakeDTO.intakeName);
		$("#intakeEditForm #intakeMode").val(intakeDTO.intakeMode);
		$("#intakeEditForm #intakeActualEnergy").val(intakeDTO.intakeActualEnergy);
		$("#intakeEditForm #intakeCbr").val(intakeDTO.intakeCbr);
		$("#intakeEditForm #intakeCbrPercent").val(intakeDTO.intakeCbrPercent);
		$("#intakeEditForm #intakeProtein").val(intakeDTO.intakeProtein);
		$("#intakeEditForm #intakeProteinPercent").val(intakeDTO.intakeProteinPercent);
		$("#intakeEditForm #intakeFat").val(intakeDTO.intakeFat);
		$("#intakeEditForm #intakeFatPercent").val(intakeDTO.intakeFatPercent);
		$("#intakeEditForm #dietId").val(intakeDTO.dietId);
		$("#intakeEditForm #dietName").val(intakeDTO.dietName);
		$("#intakeEditForm #pregnantStage").val(intakeDTO.pregnantStage);
		$("#intakeEditForm #intakeProductDesc").val(intakeDTO.intakeProductDesc);
		$("#intakeEditForm #intakePrompt").val(intakeDTO.intakePrompt);
		$("#intakeEditForm #intakeMark").val(intakeDTO.intakeMark);
		$("#intakeEditForm #intakeRemark").val(intakeDTO.intakeRemark);
	}
	if (!common.isEmpty(intakeDetails)) {
		$(intakeDetails).each(
				function(index, intakeElement) {
					if ("product" == intakeElement.intakeFoodType) {
						var produceTr = $("#" + intakeElement.intakeMealtype + " #product");
						var productSeparator;
						if (produceTr.find("input").length > 0) {
							productSeparator = "、";
						} else {
							productSeparator = "";
						}
						produceTr.html(produceTr.html() + intakeElement.intakeTypeName + "<input name='" + intakeElement.intakeTypeName
								+ "' type='text' id='" + intakeElement.intakeType + "' value='" + intakeElement.intakeCount
								+ "' class='intak-input'/>份" + productSeparator);
					} else {
						$("#" + intakeElement.intakeMealtype).find("#" + intakeElement.intakeType).val(intakeElement.intakeCount);
					}
				});
	}
}
/**
 * 打开食谱选择box
 */
function showFoodbookModal() {
	$('#foodbookModal').modal('show');
}
// 列表配置信息
var dietDataRow;
var dtableOptions = {
	id : "dTable",
	form : "dtableQueryForm",
	columns : [ {
		"data" : "dietName",
		"sClass" : "text-left"
	}, {
		"data" : "pregnantStage",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("PREGNANT_STAGE", data);
		}
	}, {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<a href='javascript:chooseDietTmp()'>选择</a>";
		}
	} ],
	condition : "dtableCondition",
	rowClick : function(data, row) {
		dietDataRow = data;
	},
	selecttarget : [ 1 ]
};
/**
 * 选择模板
 */
function chooseDietTmp() {
	$("#dietId").val(dietDataRow.dietId);
	$("#dietName").val(dietDataRow.dietName);
	$('#foodbookModal').modal('hide');
}