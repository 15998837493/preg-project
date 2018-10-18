//****************************************************【html模版】**************************************************************

var threeMealHtml = document.getElementById("threeMealHtml").innerHTML;
var plusMealHtml = document.getElementById("plusMealHtml").innerHTML;
var mealtypeData = [
    {"key": "C00000CA000000000001", "name": "早餐", "value": threeMealHtml},
    {"key": "C00000CA000000000002", "name": "上午加餐", "value": plusMealHtml},
    {"key": "C00000CA000000000003", "name": "午餐", "value": threeMealHtml},
    {"key": "C00000CA000000000004", "name": "下午加餐", "value": plusMealHtml},
    {"key": "C00000CA000000000005", "name": "晚餐", "value": threeMealHtml},
    {"key": "C00000CA000000000006", "name": "睡前", "value": plusMealHtml}
];
var threeCodes = ["E004", "E005", "E007", "E008", "E012", "E013"];
var plusCodes = ["E006", "E009", "E010"];

var productNameList = [];
$.each(productList, function (index, value) {
    var productName = value.productName || (value.productGoodsName + " " + value.productCommonName);
    if (!_.isEmpty($.trim(productName))) {
        value.productName = productName;
        productNameList.push({name: productName, val: value});
    }
});

$("#intakeMeals_tbody").empty();
var layData = null;
$.each(mealtypeData, function (index, meal) {
    layData = {mealtype: meal.key, mealtypeName: meal.name};
    laytpl(meal.value).render(layData, function (html) {
        $("#intakeMeals_tbody").append(html);
        if (!_.isEmpty(productNameList)) {
            $("#" + meal.key + "_search").autocomplete(productNameList, {
                width: 170,
                matchContains: true,
                autoFill: false,
                formatItem: function (row, i, max) {
                    return row.name;
                },
                formatMatch: function (row, i, max) {
                    var pinyinString = pinyin.getCamelChars(row.name);
                    return row.name + " " + pinyinString;
                },
                formatResult: function (row) {
                    return row.name;
                }
            }).result(function (event, data, formatted) {
                // 添加到明细中
                if ($("#" + meal.key + "_" + data.val.productId + "_productData").length == 0) {
                    $("#" + meal.key + "_product").append(
                        "<div id='" + meal.key + "_" + data.val.productId + "_productData' " +
                        "	    ondblclick='removeProductModal(\"" + meal.key + "_" + data.val.productId + "_productData\")'" +
                        " 	    class='div-inline'>" +
                        "<a id='" + meal.key + "_" + data.val.productId + "_remove'" +
                        "       data-toggle='tooltip'" +
                        "       data-title='双击删除'" +
                        "       onmouseover='showToolTipContent(\"" + meal.key + "_" + data.val.productId + "_remove\");'>" + data.val.productName + "</a>" +
                        "<input type='text'" +
                        " 		name='intakeList'" +
                        " 		id='" + meal.key + "_" + data.val.productId + "'" +
                        " 		info='product_" + data.val.productName + "'" +
                        " 		value=''" +
                        " 		class='intake-input' input-required />" +
                        "<span class='my-input-group-addon'>" + data.val.productUnit + "</span>" +
                        "</div>"
                    );
                } else {
                    layer.msg("该产品已添加！");
                }
                $("#" + meal.key + "_search").val("");
            });
        }
        ;
    });
});

laytpl(document.getElementById("normalFoodHtml").innerHTML).render(intakeTypeList, function (html) {
    $("#normalFood_tbody").html(html);
});

/**
 * 诊断提示
 * @param intakeId
 */
function showToolTipContent(id) {
    $("#" + id).tooltip("show");
}

/**
 * 查看更多
 * @param mealtype
 */
function showMoreProduct(mealtype) {
    if ($("#" + mealtype + "_more_div").is(":hidden")) {
        $("#" + mealtype + "_more_div").show();
        $("#" + mealtype + "_meal_i").removeClass("fa-angle-double-down").addClass("fa-angle-double-up").html("隐藏");
    } else {
        $("#" + mealtype + "_more_div").hide();
        $("#" + mealtype + "_meal_i").removeClass("fa-angle-double-up").addClass("fa-angle-double-down").html("更多");
    }
}

//****************************************************【膳食方案数据操作】**************************************************************

var planOptions = {
    rules: {
        intakeMode: {required: true},
        caloric: {required: true}
    },
    //设置错误信息显示到指定位置
    errorPlacement: function (error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function () {
        $("#planCreateForm").ajaxPost(dataType.json, function (data) {
            $("#planId").val(data.value);
        }, false, false);
    }
};

var operateType = "add";

var intakeOptions = {
    rules: {
        intakeName: {
            required: true,
            remote: {
                url: URL.get("Platform.PERSON_INTAKENAME_VALIDATE"),//后台处理程序
                type: "post",               		//数据发送方式
                dataType: "json",           		//接受数据格式
                data: {              	      		//要传递的数据，默认已传递应用此规则的表单项
                    intakeName: function () {
                        return $("#intakeTemplateForm input[name='intakeName']").val();
                    },
                    intakeNameOld: function () {
                        return $("#intakeNameOld").val();
                    },
                    operateType: function () {
                        return operateType;
                    },
                    random: function () {
                        return Math.random();
                    }
                }
            }
        },
        pregnantStage: {required: true}
    },
    messages: {
        intakeName: {
            remote: "该名称已经存在，请重新输入"
        }
    },
    //设置错误信息显示到指定位置
    errorPlacement: function (error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function () {
        $("#intakeTemplateForm").ajaxPost(dataType.json, function (data) {
            $("#intakeTemplateForm input[name='intakeId']").val(data.value);
            $("#addPersonTemplateModal").modal("hide");
            $("#intakeNameOld").val($("#intakeTemplateForm input[name='intakeName']").val());
            operateType = "update";
        }, false, false);
    }
};

/**
 * JSP页面--plan_adjust.jsp
 *
 * 动态设置 【能量/产能营养素明细】 和【食物份数明细】
 * @returns {Boolean}
 */
function setPlanInfo() {
    var sumEnergy = 0;// 能量和
    var sumCbr = 0;// 碳水化合物和
    var sumProtein = 0;// 蛋白质和
    var sumFat = 0;// 脂肪和
    var normalFoodMap = new Map();// 普通食物
    var productFoodMap = new Map();// 营养食品

    $("#intakeProduct_tbody").empty();
    // 集合数据
    $.each($("input[name='intakeList']"), function (index, obj) {
        var count = this.value;
        var $obj = $(this);
        var id = $obj.attr("id").split("_");
        var info = $obj.attr("info").split("_");
        var intakeData = null;
        if (info[0] == "intake") {
            if (common.isEmpty(intakeTypeMap.get(id[1]))) {
                return;
            }
            intakeData = intakeTypeMap.get(id[1]);
        }
        if (info[0] == "product") {
            if (common.isEmpty(productMap.get(id[1]))) {
                return;
            }
            intakeData = productMap.get(id[1]);
        }
        if (!common.isEmpty(count) && count != 0 && !common.isEmpty(intakeData)) {
            sumEnergy = common.accAdd(sumEnergy, common.accMul(intakeData.unitEnergy, count));
            sumCbr = common.accAdd(sumCbr, common.accMul(intakeData.unitCbr, count));
            sumProtein = common.accAdd(sumProtein, common.accMul(intakeData.unitProtein, count));
            sumFat = common.accAdd(sumFat, common.accMul(intakeData.unitFat, count));

            var intakeFoodType = $obj.attr("info").split("_")[0];// 食品类型
            var intakeTypeName = $obj.attr("info").split("_")[1];// 食品类型名称
            if ("intake" == intakeFoodType) {
                if (!normalFoodMap.has(id[1])) {
                    normalFoodMap.set(id[1], count);
                } else {
                    normalFoodMap.set(id[1], common.accAdd(normalFoodMap.get(id[1]), count));
                }
                ;
            }
            if ("product" == intakeFoodType) {
                intakeTypeName += "_" + id[1];
                if (!productFoodMap.has(intakeTypeName)) {
                    productFoodMap.set(intakeTypeName, count);
                } else {
                    productFoodMap.set(intakeTypeName, common.accAdd(productFoodMap.get(intakeTypeName), count));
                }
                ;
                // 添加到营养食品列表中
                $("#intakeProduct_norecord_tr").remove();
                if ($("#" + intakeData.productId + "_productData_modal").length == 0) {
                    $("#intakeProduct_tbody").append(
                        "<tr id='" + intakeData.productId + "_productData_modal'>" +
                        "	<td>" + intakeData.productName + "</td>" +
                        "	<td class='text-center'>" + intakeData.productUnit + "</td>" +
                        "	<td class='text-center'>" + intakeData.productStandard + "</td>" +
                        "	<td class='text-center'>1</td>" +
                        "	<td class='text-center'>" + count + "</td>" +
                        "</tr>"
                    );
                } else {
                    var $intakeProductTr = $("#" + intakeData.productId + "_productData_modal");
                    $intakeProductTr.children()[4].innerText = common.accAdd($intakeProductTr.children()[4].innerText, count);
                    $intakeProductTr.children()[3].innerText = common.accAdd($intakeProductTr.children()[3].innerText, 1);
                }
            }
            ;
        }
        ;
    });
    if ($("#intakeProduct_tbody").children().length == 0) {
        $("#intakeProduct_tbody").html("<tr id='intakeProduct_norecord_tr'><td class='text-center' colspan='10'><h4>未选择营养食品！</h4></td></tr>");
    }

    sumEnergy = Math.round(sumEnergy);
    sumCbr = Math.round(sumCbr);
    sumProtein = Math.round(sumProtein);
    sumFat = Math.round(sumFat);

    if (sumEnergy == 0) {
        $("span[name='info_span']").html("——");
        $("span[name='prompt_span']").html("");
        $("#normal_food").html("<font color='gray'>未选择普通食物</font>");
        $("#product_food").html("<font color='gray'>未选择营养食品</font>");

        layer.alert("热量和为零，请确认食品参数是否正确！");
        return false;
    }

    // 三大营养素含量
    var energyMin = Math.floor(sumEnergy / 200) * 200, energyMax = Math.ceil(sumEnergy / 200) * 200;
    var cbrAmountMax = sumCbr + 5;
    var proteinAmountMin = (sumProtein < 2) ? 0 : (sumProtein - 2), proteinAmountMax = sumProtein + 2;
    var fatAmountMin = (sumFat < 2) ? 0 : (sumFat - 2), fatAmountMax = sumFat + 2;

    if (energyMin == energyMax) {
        energyMax = energyMax + 200;
    }

    $("#energy_amount").html(energyMin + "~" + energyMax);
    $("#cbr_amount").html(sumCbr + " ± " + "5");
    $("#protein_amount").html(sumProtein + " ± " + "2");
    $("#fat_amount").html(sumFat + " ± " + "2");

    $("#energy_amount_modal").html(energyMin + "~" + energyMax);
    $("#cbr_amount_modal").html(sumCbr + " ± " + "5");
    $("#protein_amount_modal").html(sumProtein + " ± " + "2");
    $("#fat_amount_modal").html(sumFat + " ± " + "2");

    // 三大营养素占比
    var cbrPercent = common.accMul(common.accDiv(common.accMul(sumCbr, 4), sumEnergy).toFixed(2), 100);
    var proteinPercent = common.accMul(common.accDiv(common.accMul(sumProtein, 4), sumEnergy).toFixed(2), 100);
    var fatPercent = common.accMul(common.accDiv(common.accMul(sumFat, 9), sumEnergy).toFixed(2), 100);

    var cbrPercentMin = (cbrPercent < 1) ? cbrPercent : (cbrPercent - 1), cbrPercentMax = cbrPercent + 1;
    var proteinPercentMin = (proteinPercent < 1) ? proteinPercent : (proteinPercent - 1), proteinPercentMax = proteinPercent + 1;
    var fatPercentMin = (fatPercent < 1) ? fatPercent : (fatPercent - 1), fatPercentMax = fatPercent + 1;

    $("#cbr_percent").html(cbrPercentMin + "%" + "~" + cbrPercentMax + "%");
    $("#protein_percent").html(proteinPercentMin + "%" + "~" + proteinPercentMax + "%");
    $("#fat_percent").html(fatPercentMin + "%" + "~" + fatPercentMax + "%");

    $("#cbr_percent_modal").html(cbrPercentMin + "%" + "~" + cbrPercentMax + "%");
    $("#protein_percent_modal").html(proteinPercentMin + "%" + "~" + proteinPercentMax + "%");
    $("#fat_percent_modal").html(fatPercentMin + "%" + "~" + fatPercentMax + "%");

    // 三大营养素提示信息
    var custWeight = $("#diagnosisCustWeight").val();
    var perUnitProteinMin = common.accDiv(proteinAmountMin, custWeight);
    var perUnitProteinMax = common.accDiv(proteinAmountMax, custWeight);

    var cbrPrompt = "";// 碳水化合物
    var proteinPrompt = "";// 蛋白质
    var fatPrompt = "";// 脂肪
    // 碳水化合物
    if (cbrAmountMax < 130) {
        cbrPrompt = "碳水化合物摄入不足，建议增加谷薯/水果份数，为了避免饥饿性酮症的产生，碳水化合物尽量达到175克以上";
    }
    if (cbrPercentMin > 65) {
        cbrPrompt = "碳水化合物摄入过多，过低建议适当减少谷物/水果份数";
    }
    if (cbrAmountMax < 130 && cbrPercentMin > 65) {
        layer.alert("请确认方案和食物选择！");
        cbrPrompt = "请确认方案和食物选择！";
    }
    // 蛋白质
    if (perUnitProteinMax < 1 && proteinPercentMax < 12) {
        proteinPrompt = "蛋白质摄入不足，建议增加鱼肉蛋豆类份数或额外添加高优质蛋白营养食品";
    }
    if (perUnitProteinMin > 2 && proteinPercentMin > 20) {
        proteinPrompt = "蛋白质摄入过多，建议减少鱼肉蛋豆类份数";
    }
    // 脂肪
    if (fatAmountMin > 87 && fatPercentMax > 40) {
        fatPrompt = "脂肪摄入过多，建议适当减少富含烹饪油、坚果及鱼肉蛋的份数";
    }
    if (fatAmountMax < 35.6) {
        fatPrompt = "脂肪摄入不足，建议适当减少富含烹饪油、坚果及鱼肉蛋的份数";
    }

    $("#cbr_prompt").html(cbrPrompt);
    $("#protein_prompt").html(proteinPrompt);
    $("#fat_prompt").html(fatPrompt);

    // 食物份数明细
    var normalFood = [];
    var productFood = [];
    $("span[name='modal_span']").html("");
    normalFoodMap.forEach(function (value, key) {
        normalFood.push(intakeTypeMap.get(key).name + "：" + value + " 份");
        $("#" + key + "_modal").html(value);
    });
    productFoodMap.forEach(function (value, key) {
        if (common.isEmpty(productMap.get(key.split("_")[1]))) {
            return;
        }
        productFood.push("<a id='productFood_" + key.split("_")[1] + "'" +
            " data-title=\"" + productMap.get(key.split("_")[1]).productMatters + "\"" +
            " onmouseover='showToolTipContent(\"productFood_" + key.split("_")[1] + "\");'>" + key.split("_")[0] + "</a>" +
            "：" + value + " " + productMap.get(key.split("_")[1]).productUnit);
    });
    var normalFoodHtml = normalFood.join("、");
    var productFoodHtml = productFood.join("、");
    if (!common.isEmpty(normalFoodHtml)) {
        $("#normal_food").html(normalFoodHtml);
    } else {
        $("#normal_food").html("<font color='gray'>未选择普通食物</font>");
    }
    if (!common.isEmpty(productFoodHtml)) {
        $("#product_food").html(productFoodHtml);
    } else {
        $("#product_food").html("<font color='gray'>未选择营养食品</font>");
    }
}

/**
 * 组合膳食清单摄入明细
 */
function getPlanIntakeDetail() {
    var detailList = [];
    // 摄入类型
    $.each($("input[name='intakeList']"), function (index, obj) {
        if (!common.isEmpty(this.value)) {
            var $obj = $(this);
            var id = $obj.attr("id").split("_");
            var info = $obj.attr("info").split("_");
            var unitAmount = 0;
            var unit = "份";
            if (info[0] == "intake") {
                if (common.isEmpty(intakeTypeMap.get(id[1]))) {
                    return;
                }
                unitAmount = (common.isEmpty(intakeTypeMap.get(id[1]))) ? 0 : intakeTypeMap.get(id[1]).unitAmount;
                unit = "份";
            }
            if (info[0] == "product") {
                if (common.isEmpty(productMap.get(id[1]))) {
                    return;
                }
                unitAmount = (common.isEmpty(productMap.get(id[1]))) ? 0 : productMap.get(id[1]).unitAmount;
                unit = productMap.get(id[1]).productUnit;
            }
            detailList.push("{" +
                "\"intakeMealtype\":\"" + id[0] + "\"," +
                "\"intakeMealtypeName\":\"" + CODE.transCode("MEALSTYPE", id[0]) + "\"," +
                "\"intakeType\":\"" + id[1] + "\"," +
                "\"intakeTypeName\":\"" + info[1] + "\"," +
                "\"intakeFoodType\":\"" + info[0] + "\"," +
                "\"intakeAmount\":\"" + unitAmount + "\"," +
                "\"intakeCount\":\"" + this.value + "\"," +
                "\"intakeUnit\":\"" + unit + "\"}"
            );
        }
    });
    $("#detailList").val("[" + detailList + "]");
    $("#detailsJson").val("[" + detailList + "]");

    // 食谱天数
    var foodDayArray = [];
    $.each($("input:checkbox[name='myFoodDays'][checked]"), function (index, obj) {
        foodDayArray.push(this.value);
    });
    $("#foodDays").val(foodDayArray.join(","));

    $("input[name='intakeCaloric']").val($("#energy_amount").html());
    $("input[name='intakeActualEnergy']").val($("#energy_amount").html());
    $("input[name='intakeCbr']").val($("#cbr_amount").html());
    $("input[name='intakeCbrPercent']").val($("#cbr_percent").html());
    $("input[name='intakeProtein']").val($("#protein_amount").html());
    $("input[name='intakeProteinPercent']").val($("#protein_percent").html());
    $("input[name='intakeFat']").val($("#fat_amount").html());
    $("input[name='intakeFatPercent']").val($("#fat_percent").html());

    // 膳食处方备注信息
    $("#diagnosisDietRemark").val($("#diagnosisDietRemarkText").val());
}

/**
 * 初始化上一次膳食清单明细
 * @param intakeDetailList
 */
function initHistoryIntakeDetailList(intakeDetailList) {
    // 清空
    $("td[name='intake-td']").find("input").val("");
    $.each($("td[name='product-td']"), function (index, td) {
        $(td).children().not(":first").remove();
    });

    // 初始化数据
    if (!common.isEmpty(intakeDetailList)) {
        var initProductMap = new Map();
        $.each(intakeDetailList, function (index, intake) {
            if (intake.intakeFoodType == "intake") {
                $("#" + intake.intakeMealtype + "_" + intake.intakeType).val(intake.intakeCount);
            }
            if (intake.intakeFoodType == "product") {
                if (!initProductMap.has(intake.intakeMealtype)) {
                    initProductMap.set(intake.intakeMealtype, new Array());
                }
                if (!common.isEmpty(intake.intakeTypeName)) {
                    initProductMap.get(intake.intakeMealtype).push(
                        "<div class='div-inline'" +
                        "	    ondblclick='removeProductModal(\"" + intake.intakeMealtype + "_" + intake.intakeType + "_productData\")'" +
                        "       id='" + intake.intakeMealtype + "_" + intake.intakeType + "_productData'>" +
                        "<a id='" + intake.intakeMealtype + "_" + intake.intakeType + "_remove'" +
                        "       data-toggle='tooltip'" +
                        "       data-title='双击删除'" +
                        "       onmouseover='showToolTipContent(\"" + intake.intakeMealtype + "_" + intake.intakeType + "_remove\");'>" + intake.intakeTypeName + "</a>" +
                        "<input type='text'" +
                        " 		name='intakeList'" +
                        " 		id='" + intake.intakeMealtype + "_" + intake.intakeType + "'" +
                        " 		info='product_" + intake.intakeTypeName + "'" +
                        " 		value='" + intake.intakeCount + "'" +
                        " 		class='intake-input' input-required/>" +
                        "<span class='my-input-group-addon'>" + (intake.intakeUnit || "份") + "</span>" +
                        "</div>"
                    );
                }
            }
        });
        // 添加到明细中
        initProductMap.forEach(function (value, key) {
            $("#" + key + "_product").append(value.join(""));
        });

        setPlanInfo();
    }
}

/**
 * 删除产品
 * @param id
 */
function removeProductModal(id) {
    layer.confirm("确认删除该产品？", function (index) {
        $("#" + id).remove();
        // 重新计算设置
        setPlanInfo();
        layer.close(index);
    });
}

/**
 * 校验数据是否正确
 * @param value
 * @returns
 */
function checkInputNumber(value) {
    var result = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/.exec(value);// 正浮点数
    if (common.isEmpty(result)) {
        result = /^[1-9]\d*|0$/.exec(value);// 非负整数
    }
    result = result || [""];
    if (result[0].toString().split(".").length > 1 && result[0].toString().split(".")[1].length > 2) {
        layer.msg("小数点后只能保留两位！");
        result[0] = result[0].substring(0, (result[0].indexOf(".") + 3));
    }
    return result[0];
}

//****************************************************【食谱模版】**************************************************************

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
        onClick: function (event, treeId, treeNode) {
            selectNode = treeNode;
            var id = treeNode.id;
            if ("000" == id) {
                id = "";
            }
            $("#categoryIdQuery").val(id);
            // 菜谱列表数据加载
            foodTable = datatable.table(foodTableOption);
        },
        onExpand: function () {
            // setDH();
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
            "render": function (data, type, row, meta) {
                return data.foodName;
            }
        },
        {
            "data": null,
            "sClass": "text-center",
            "render": function (data, type, row, meta) {
                return "<a onclick=addFoodExt('" + data.foodId + "','" + data.foodName.replace(/\s/g, "") + "')>添加</a>";
            }
        }],
    condition: "foodCondition",
    async: false,
    loading: false,
    sPaginationType: "simple"
};

//选中项信息
var dietRowData;
//列表配置信息
var dietTableOptions = {
    id: "dietTable",
    data: dietList,
    columns: [{
        "data": null,
        "sClass": "text-center"
    }, {
        "data": "dietName"
    }, {
        "data": "pregnantStage",
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            return CODE.transCode("PREGNANT_STAGE", data);
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            var rowData = "{\"dietId\":\"" + data.dietId + "\"," +
                "\"dietName\":\"" + data.dietName + "\"," +
                "\"pregnantStage\":\"" + data.pregnantStage + "\"}";
            return "<a onclick='resetPlanDiet(" + rowData + ");'>添加</>";
        }
    }],
    condition: "dietCondition",
    orderIndex: 0
};

/**
 * 重新选择食谱
 * @param rowData
 */
function resetPlanDiet(rowData) {
    // 初始化食谱
    initDietTemplate(rowData);
    // 保存食谱
    savePlanDiet();
}

/**
 * 初始化食谱（七天）
 * @param rowData
 */
function initDietTemplate(rowData) {
    var dietId = rowData.dietId;
    if (!common.isEmpty(dietId)) {
        var url = URL.get("Platform.PLAN_DIETLIST_QUERY");
        var params = "dietId=" + dietId;
        ajax.post(url, params, dataType.json, function (data) {
            // 清空html
            $("#diet_list_body").empty();
            if (!common.isEmpty(data.value)) {
                $.each(data.value, function (index, obj) {
                    var foodDay = obj.foodDay;
                    $("#diet_list_body").append(
                        "<input type='checkbox' name='myFoodDays' value='" + foodDay + "' style='margin-top: 10px;'/>" +
                        "<label style='font-weight: normal; padding-left: 5px; padding-right: 10px;'>" +
                        "	<a id='foodDay_" + foodDay + "' onclick='getDietDetail(\"" + foodDay + "\");'>第 " + foodDay + " 天 </a> " +
                        "</label>"
                    );
                });
            }
            if ($("#diet_list_body").find("label").length == 0) {
                $("#diet_list_body").html("<h4>没有找到记录！</h4>");
            }
            $("input[name='dietId']").val(rowData.dietId);
            $("input[name='dietName']").val(rowData.dietName);
            if (!_.isEmpty(rowData.dietName)) {
                $("#dietName_span").html("【" + rowData.dietName + "】");
            } else {
                $("#dietName_span").html("");
            }
            $("#dietModal").modal("hide");
            // 选中上次所选天数
            if (!common.isEmpty(rowData.foodDays)) {
                $.each(rowData.foodDays.split(","), function (index, foodDay) {
                    $("input:checkbox[name='myFoodDays'][value='" + foodDay + "']").attr("checked", true);
                });
            }
        }, false, false);
    }
}

/**
 * 保存食谱
 */
function savePlanDiet() {
    updateDiagnosisStatus();
    // 食谱天数
    var foodDayArray = [];
    $("input:checkbox[name='myFoodDays'][checked]").each(function (index, obj) {
        foodDayArray.push(this.value);
    });
    var url = URL.get("Platform.RECEIVE_PLAN_DIET");
    var params = "planId=" + $("#planId").val() +
        "&dietId=" + $("#dietId").val() +
        "&dietName=" + $("#planCreateForm input[name='dietName']").val() +
        "&foodDays=" + foodDayArray.join(",") +
        "&diagnosisId=" + $("#planCreateForm input[name='diagnosisId']").val();
    ajax.post(url, params, dataType.json, function (data) {
        $("#planId").val(data.value);
    }, false, false);
    console.log("保存食谱");
}

/**
 * 添加食谱明细
 * @param foodId
 * @param foodName
 */
function addFoodExt(foodId, foodName) {
    var foodMeal = $('#foodMeal').val();
    if (common.isEmpty(foodMeal)) {
        hint.warning("请选择餐次！");
        return;
    }

    var count = $("td[id='" + foodMeal + foodId + "']").length;
    if (count > 0) {
        hint.warning(CODE.transCode("MEALSTYPE", foodMeal) + "中已存在此菜名！");
        return;
    }

    var url = URL.get("Platform.PLAN_DIETDETAIL_ADD");
    var params = "planId=" + $("#planId").val() +
        "&dietId=" + $("#dietId").val() +
        "&foodMeal=" + foodMeal +
        "&foodId=" + foodId +
        "&foodName=" + foodName +
        "&amountLevel=" + $("#amountLevel").val() +
        "&foodDay=" + $("#foodDay").val();
    ajax.post(url, params, dataType.json, function (data) {
        getDietDetail($("#foodDay").val());
    }, false, false);
}

/**
 * 查看食谱明细
 * @param dietId
 * @param foodDay
 */
function getDietDetail(foodDay) {
    $("#foodDay").val(foodDay);
    var $chexbox = $("input:checkbox[name='myFoodDays'][value='" + foodDay + "']");
    if ($chexbox[0] && !$chexbox[0].checked) {
        $chexbox[0].checked = true;
        savePlanDiet();// 保存一天食谱
    }
    var url = URL.get("Platform.PLAN_DIETDETAIL_GET");
    var params = "planId=" + $("#planId").val() + "&foodDay=" + foodDay;
    ajax.post(url, params, dataType.json, function (data) {
        if (!common.isEmpty(data.value)) {
            $("#intake_diet_detail_inbody").empty();
            $.each(data.value, function (mealName, mealMap) {
                var mealCount = 0;
                var mealHtml = "";
                var detailHtml = "";
                $.each(mealMap, function (foodName, foodMap) {
                    var food = foodMap[0];
                    $("#amountLevel").val(food.amountLevel);
                    $("#amountLevelSpan").html(food.amountLevel);
                    if (mealCount == 0) {
                        detailHtml += "<td rowspan='" + foodMap.length + "' id='" + food.foodMeal + food.foodId + "'>" + foodName + "<a style='float: right; color: red;' onclick='removeDietDetail(\"" + food.foodMeal + "\",\"" + food.foodId + "\",\"" + food.foodDay + "\");'><li class='fa fa-remove fa-fw'></li></a></td>";
                    } else {
                        detailHtml += "<tr><td rowspan='" + foodMap.length + "' id='" + food.foodMeal + food.foodId + "'>" + foodName + "<a style='float: right; color: red;' onclick='removeDietDetail(\"" + food.foodMeal + "\",\"" + food.foodId + "\",\"" + food.foodDay + "\");'><li class='fa fa-remove fa-fw'></li></a></td>";
                    }
                    mealCount += foodMap.length;
                    $.each(foodMap, function (index, detail) {
                        var foodMaterialAmount = detail.foodMaterialAmount;
                        var tdId = detail.id + "_" + detail.fmId + "_td";
                        if (index == 0) {
                            detailHtml += "<td>" + detail.foodMaterialName + "</td>" +
                                "<td class='text-center'><input type='text' class='intake-sm' value='" + foodMaterialAmount + "' onchange='restFmDosage(\"" + tdId + "\", this);'></td>" +
                                "<td class='text-center' id='" + tdId + "' name='dosage'>" + queryFmDosage(detail.fmId, foodMaterialAmount) + "</td>" +
                                "</tr>";
                        } else {
                            detailHtml += "<tr><td>" + detail.foodMaterialName + "</td>" +
                                "<td class='text-center'><input type='text' class='intake-sm' value='" + foodMaterialAmount + "' onchange='restFmDosage(\"" + tdId + "\", this);'></td>" +
                                "<td class='text-center' id='" + tdId + "'>" + queryFmDosage(detail.fmId, foodMaterialAmount) + "</td>" +
                                "</tr>";
                        }
                    });
                });
                mealHtml += "<tr><td rowspan='" + mealCount + "' class='text-center'>" + mealName + "</td>" + detailHtml;
                $("#intake_diet_detail_inbody").append(mealHtml);
            });
        } else {
            layer.alert("没有该食谱明细信息！");
        }
        // 判断是否为空
        if ($("#intake_diet_detail_inbody").find("tr").length == 0) {
            $("#intake_diet_detail_inbody").html("<tr><td colspan='10' class='text-center'><h4>没有找到记录！</h4></td></tr>");
        }

        sumDosage();// 计算能量

        $("#dietDetailModal").modal("show");
    }, false, false);
}

/**
 * 删除菜谱
 *
 * @author zcq
 * @date 2018/10/17 14:59
 */
function removeDietDetail(foodMeal, foodId, foodDay) {
    layer.confirm("确定要执行【刪除】操作？", function (index) {
        var url = URL.get("Platform.PLAN_DIETDETAIL_DELETE");
        var params = "planId=" + $("#planId").val() + "&foodMeal=" + foodMeal + "&foodId=" + foodId;
        ajax.post(url, params, dataType.json, function (data) {
            getDietDetail(foodDay);
        }, false, false);
        layer.close(index);
    });
}

/**
 * 重新计算食材能量
 *
 * @author zcq
 * @date 2018/10/17 13:32
 */
function restFmDosage(tdId, obj) {
    obj.value = common.checkInputNumber("reg4", obj.value);
    var amount = obj.value;
    var url = URL.get("Platform.PLAN_DIETDETAIL_UPDATE");
    var params = "id=" + tdId.split("_")[0] + "&foodMaterialAmount=" + amount;
    ajax.post(url, params, dataType.json, null, false, false);
    $("#" + tdId).html(queryFmDosage(tdId.split("_")[1], amount));
    sumDosage();
}

/**
 * 食材能量计算
 * @param fmId
 * @param amount
 */
function queryFmDosage(fmId, amount) {
    var donsage = "";

    // 获取食材对象
    if (!common.isEmpty(fmId) && !common.isEmpty(amount)) {
        var fm = getFmBase(fmId);
        // 获取可食用部分
        var fmEsculent = fm.fmEsculent;
        var tempEleArray = ajaxData(fmId);
        for (var i = 0; i < tempEleArray.length; i++) {
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
    ajax.post(url, params, dataType.json, function (data) {
        tempEleArray = data.value;
    }, false, false);
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
    ajax.post(url, params, dataType.json, function (data) {
        tempEleArray = data.data;
    }, false, false);
    return tempEleArray;
}

/**
 * 实际能量值计算
 */
function sumDosage() {
    var dosageCount = 0;
    $("td[name='dosage']").each(function (index, obj) {
        var id = obj.id;
        if (!common.isEmpty($("#" + id).html())) {
            dosageCount = dosageCount + parseFloat($("#" + id).html());
        }
    });

    // 获取能量范围值
    var dosageMin = 0;
    var dosageMax = 0;
    if (!common.isEmpty($("#amountLevel").val())) {
        dosageMin = $("#amountLevel").val().split("~")[0];
        dosageMax = $("#amountLevel").val().split("~")[1];
    }

    // 提示语
    var message = "实际能量：" + dosageCount;
    if (dosageCount > dosageMax) {
        message = message + "<span style='color:red;'> (高于能量推荐范围)</span>"
    }
    if (dosageCount < dosageMin) {
        message = message + "<span style='color:red;'> (低于能量推荐范围)</span>"
    }
    $("#dosage").html(message);
}

//****************************************************【常用模板】**************************************************************

//选中项信息
var planIntakeData;
// 选中行信息
var planIntakeRow;
// table对象
var planIntakeTable;
// 列表配置信息
var planIntakeTableOptions = {
    id: "planIntakeTable",
    form: "planIntakeQueryForm",
    ajax: {data: {"createUserId": $("#createUserId").val(), "dataType": "1"}},
    async: false,
    columns: [{
        "data": "intakeName"
    }, {
        "data": "intakeActualEnergy",
        "sClass": "text-center"
    }, {
        "data": "intakeCbr",
        "sClass": "text-center"
    }, {
        "data": "intakeCbrPercent",
        "sClass": "text-center"
    }, {
        "data": "intakeProtein",
        "sClass": "text-center"
    }, {
        "data": "intakeProteinPercent",
        "sClass": "text-center"
    }, {
        "data": "intakeFat",
        "sClass": "text-center"
    }, {
        "data": "intakeFatPercent",
        "sClass": "text-center"
    }, {
        "data": "pregnantStage",
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            return CODE.transCode("PREGNANT_STAGE", data);
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (common.isEmpty(data.intakeMark)) {
                return "";
            } else {
                return "<a id='mark_" + data.intakeId + "'" +
                    "   data-toggle='tooltip'" +
                    "   data-title='" + data.intakeMark + "'" +
                    "   onmouseover='showToolTipContent(\"mark_" + data.intakeId + "\");'>详情</a>" +
                    "<span style='display: none;'>" + data.intakeMark + "</span>";
            }
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (common.isEmpty(data.intakeMark)) {
                return "";
            } else {
                return "<a id='remark_" + data.intakeId + "'" +
                    "   data-toggle='tooltip'" +
                    "   data-title='" + data.intakeRemark + "'" +
                    "   onmouseover='showToolTipContent(\"remark_" + data.intakeId + "\");'>详情</a>";
            }
        }
    }, {
        "data": "intakeMode",
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            return CODE.transCode("INTAKE_MODE", data);
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            var rowData = "{\"intakeId\":\"" + data.intakeId + "\"," +
                "\"intakeName\":\"" + data.intakeName + "\"," +
                "\"dietId\":\"" + data.dietId + "\"," +
                "\"dietName\":\"" + data.dietName + "\"," +
                "\"intakeCaloric\":\"" + data.intakeCaloricMin + "\"," +
                "\"intakeCbr\":\"" + data.intakeCbr + "\"," +
                "\"intakeCbrPercent\":\"" + data.intakeCbrPercent + "\"," +
                "\"intakeProtein\":\"" + data.intakeProtein + "\"," +
                "\"intakeProteinPercent\":\"" + data.intakeProteinPercent + "\"," +
                "\"intakeFat\":\"" + data.intakeFat + "\"," +
                "\"intakeFatPercent\":\"" + data.intakeFatPercent + "\"}";
            return "<a onclick='setPlanRowData(" + rowData + ",\"master\");'>添加</>";
        }
    }],
    condition: "planIntakeCondition",
    selecttarget: [9, 1, 8, 11],
    iDisplayLength: 5
};

/**
 * 替换方案，需要操作：膳食明细，食谱；
 * @param rowData
 * @param type
 */
function setPlanRowData(rowData, type) {
    // 查询模版数据并初始化到页面
    if (type == "master") {
        ajax.post(URL.get("Master.PLAN_INTAKE_QUERY"), "intakeId=" + rowData.intakeId, dataType.json, function (data) {
            rowData = data.value[0];
        }, false, false);
        ajax.post(URL.get("Master.PLAN_INTAKE_DETAIL_QUERY"), "intakeId=" + rowData.intakeId, dataType.json, function (data) {
            initHistoryIntakeDetailList(data.value);// 初始化膳食明细
        }, false, false);
    }
    if (type == "history") {
        ajax.post(URL.get("Platform.PLAN_INTAKE_GET"), "planId=" + rowData.planId, dataType.json, function (data) {
            initHistoryIntakeDetailList(data.value);// 初始化明细
            $("#historyPlanModal").modal("hide");
        }, false, false);
    }
    if (type == "person") {
        ajax.post(URL.get("Platform.PERSON_INTAKE_DETAIL_QUERY"), "intakeId=" + rowData.intakeId, dataType.json, function (data) {
            initHistoryIntakeDetailList(data.value);// 初始化明细
            $("#personTemplateModal").modal("hide");
        }, false, false);
    }
    initDietTemplate(rowData);// 初始化食谱

    // 设置膳食模版主键、名称；食物卡片主键、名称
    $("#planCreateForm input[name='intakeId']").val(rowData.intakeId);
    $("#intakeName").val(rowData.intakeName);

    // 保存膳食方案
    getPlanIntakeDetail();
    $("#planCreateForm").submit();
    console.log("保存膳食方案");
    // 保存食谱
    savePlanDiet();
}

//****************************************************【历次方案】**************************************************************

//选中项信息
var historyPlanData;
//列表配置信息
var historyPlanTableOptions = {
    id: "historyPlanTable",
    data: historyPlanList,
    columns: [{
        "data": "createDate",
        "sClass": "text-center"
    }, {
        "data": "intakeCaloric",
        "sClass": "text-center"
    }, {
        "data": "intakeCbr",
        "sClass": "text-center"
    }, {
        "data": "intakeCbrPercent",
        "sClass": "text-center"
    }, {
        "data": "intakeProtein",
        "sClass": "text-center"
    }, {
        "data": "intakeProteinPercent",
        "sClass": "text-center"
    }, {
        "data": "intakeFat",
        "sClass": "text-center"
    }, {
        "data": "intakeFatPercent",
        "sClass": "text-center"
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (common.isEmpty(data.diagnosisDiseaseNames)) {
                return "";
            } else {
                return "<a id='plan_" + data.planId + "'" +
                    "   data-toggle='tooltip'" +
                    "   data-title='" + data.diagnosisDiseaseNames + "'" +
                    "   onmouseover='showToolTipContent(\"plan_" + data.planId + "\");'>详情</a>" +
                    "<span style='display: none;'>" + data.diagnosisDiseaseNames + "</span>";
            }
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            var rowData = "{\"planId\":\"" + data.planId + "\"," +
                "\"dietId\":\"" + data.dietId + "\"," +
                "\"dietName\":\"" + data.dietName + "\"," +
                "\"foodDays\":\"" + data.foodDays + "\"," +
                "\"intakeCaloric\":\"" + data.intakeCaloric + "\"," +
                "\"intakeCbr\":\"" + data.intakeCbr + "\"," +
                "\"intakeCbrPercent\":\"" + data.intakeCbrPercent + "\"," +
                "\"intakeProtein\":\"" + data.intakeProtein + "\"," +
                "\"intakeProteinPercent\":\"" + data.intakeProteinPercent + "\"," +
                "\"intakeFat\":\"" + data.intakeFat + "\"," +
                "\"intakeFatPercent\":\"" + data.intakeFatPercent + "\"}";
            return "<a onclick='setPlanRowData(" + rowData + ",\"history\");'>添加</>";
        }
    }],
    condition: "historyPlanCondition"
};

//****************************************************【个人膳食模版】**************************************************************

//table对象
var personIntakeTable;
//选中行信息
var personIntakeRow;
//选中项信息
var personIntakeData;
//列表配置信息
var personIntakeTableOptions = {
    id: "personTemplateTable",
    data: personIntakeList,
    columns: [{
        "data": "intakeName"
    }, {
        "data": "intakeActualEnergy",
        "sClass": "text-center"
    }, {
        "data": "intakeCbr",
        "sClass": "text-center"
    }, {
        "data": "intakeCbrPercent",
        "sClass": "text-center"
    }, {
        "data": "intakeProtein",
        "sClass": "text-center"
    }, {
        "data": "intakeProteinPercent",
        "sClass": "text-center"
    }, {
        "data": "intakeFat",
        "sClass": "text-center"
    }, {
        "data": "intakeFatPercent",
        "sClass": "text-center"
    }, {
        "data": "pregnantStage",
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            return CODE.transCode("PREGNANT_STAGE", data);
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (common.isEmpty(data.intakeMark)) {
                return "";
            } else {
                return "<a id='mark_" + data.intakeId + "'" +
                    "   data-toggle='tooltip'" +
                    "   data-title='" + data.intakeMark + "'" +
                    "   onmouseover='showToolTipContent(\"mark_" + data.intakeId + "\");'>详情</a>" +
                    "<span style='display: none;'>" + data.intakeMark + "</span>";
            }
        }
    }, {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            var rowData = "{\"intakeId\":\"" + data.intakeId + "\"," +
                "\"intakeName\":\"" + data.intakeName + "\"," +
                "\"dietId\":\"" + data.dietId + "\"," +
                "\"dietName\":\"" + data.dietName + "\"," +
                "\"intakeCaloric\":\"" + data.intakeCaloricMin + "\"," +
                "\"intakeCbr\":\"" + data.intakeCbr + "\"," +
                "\"intakeCbrPercent\":\"" + data.intakeCbrPercent + "\"," +
                "\"intakeProtein\":\"" + data.intakeProtein + "\"," +
                "\"intakeProteinPercent\":\"" + data.intakeProteinPercent + "\"," +
                "\"intakeFat\":\"" + data.intakeFat + "\"," +
                "\"intakeFatPercent\":\"" + data.intakeFatPercent + "\"}";
            return "<a onclick='setPlanRowData(" + rowData + ",\"person\");'>添加</> " +
                "<a onclick='removePersonIntake(\"" + data.intakeId + "\");'>删除</>";
        }
    }],
    rowClick: function (data, row) {
        personIntakeData = data;
        personIntakeRow = row;
    },
    condition: "personTemplateCondition"
};

/**
 * 初始化诊断项目
 */
function initPersonTemplateDisease() {
    var diseaseNameList = [];
    $.each(diseaseList, function (index, value) {
        diseaseNameList.push({name: value.diseaseName, val: value});
    });

    if (!_.isEmpty(diseaseList)) {
        $("#diseaseSearch").autocomplete(diseaseNameList, {
            width: 170,
            matchContains: true,
            autoFill: false,
            formatItem: function (row, i, max) {
                return row.name;
            },
            formatMatch: function (row, i, max) {
                var pinyinString = pinyin.getCamelChars(row.name);
                return row.name + " " + pinyinString;
            },
            formatResult: function (row) {
                return row.name;
            }
        }).result(function (event, data, formatted) {
            // 添加到明细中
            if ($("#" + data.val.diseaseCode + "_person_div").length == 0) {
                var input_length = parseInt(data.val.diseaseName.length) * 15;
                $("#personTemplateDisease_div").append(
                    "<div id='" + data.val.diseaseCode + "_person_div' style='margin-bottom: 7px;'>" +
                    "<input type='text'" +
                    " 		value='" + data.val.diseaseName + "'" +
                    " 		class='intake-input'" +
                    "       style='width: " + input_length + "px;margin-left:0px;' disabled/>" +
                    "<input type='hidden' name='diseaseNameList' value='" + data.val.diseaseName + "'/>" +
                    "<span class='my-input-group-addon'><a onclick='removePersonDisease(\"" + data.val.diseaseCode + "_person_div\")'>X</a></span>" +
                    "</div>"
                );
            } else {
                layer.msg("该诊断项目已添加！");
            }
            $("#diseaseSearch").val("");
        });
    }
    ;
}

/**
 * 删除选择的诊断项目标签
 * @param id
 */
function removePersonDisease(id) {
    $("#" + id).remove();
}

/**
 * 删除常用模板
 * @param intakeId
 */
function removePersonIntake(intakeId) {
    layer.confirm("确定对选中内容执行【删除】操作？", function (index) {
        var url = URL.get("Platform.PERSON_INTAKE_REMOVE");
        var params = "intakeId=" + intakeId;
        ajax.post(url, params, dataType.json, function (data) {
            datatable.remove(personIntakeTable, personIntakeRow);
        }, false, false);
        layer.close(index);
    });
}

/**
 * 修改膳食方案备注
 */
function saveText(val) {
    val = val.replace(/%/g, "%25");
    val = val.replace(/\&/g, "%26");
    val = val.replace(/\+/g, "%2B");
    var diaId = $("#diaId").val();
    var url = URL.get("Platform.RECEIVE_PLAN_TEXT_ADD");
    var params = "diagnosisId=" + diaId + "&contentRsult=" + val;
    ajax.post(url, params, dataType.json, null, false, false);
}

//****************************************************【页面数据加载】**************************************************************

$().ready(function () {
    //=====================================【第一部分：初始化】=====================================
    // 初始化--加入必填项提示
    common.requiredHint("planCreateForm", planOptions);
    $("#planCreateForm").validate(planOptions);
    common.requiredHint("intakeTemplateForm", intakeOptions);
    $("#intakeTemplateForm").validate(intakeOptions);
    // 初始化页面信息
    common.initCodeSelect("PREGNANT_STAGE", "PREGNANT_STAGE", "pregnantStageSelect", "", "==请选择孕期阶段==");
    common.initCodeSelect("PREGNANT_STAGE", "PREGNANT_STAGE", "pregnantStage", "", "==请选择孕期阶段==");
    common.initCodeSelect("DATA_BELONG_TYPE", "DATA_BELONG_TYPE", "dataBelongType", "", "==请选择模版类型==");
    common.initCodeSelect("INTAKE_MODE", "INTAKE_MODE", "intakeMode", "", "==请选择膳食类型==");
    //初始化--摄入量明细和食谱
    if (!common.isEmpty(planVo)) {
        $("#planCreateForm").jsonToForm(planVo);
        initHistoryIntakeDetailList(intakeDetailList);// 初始化膳食明细
        initDietTemplate(planVo);// 初始化食谱
    }
    // 初始化--常用模板列表
    planIntakeTable = datatable.table(planIntakeTableOptions);
    $("#energy").val(suggestIntakeEnergy);
    $("#pregnantStageSelect").val(diagnosisJson.diagnosisPregnantStage);
    datatable.search(planIntakeTable, planIntakeTableOptions);
    // 初始化--历次方案列表
    datatable.table(historyPlanTableOptions);
    // 初始化--食谱模版
    datatable.table(dietTableOptions);
    // 初始化--个人膳食模板
    personIntakeTable = datatable.table(personIntakeTableOptions);
    // 初始化--诊断项目
    initPersonTemplateDisease();
    // 食谱类别数据源加载ztree
    $.fn.zTree.init($("#zTree"), setting, zNodes);
    treeObj = $.fn.zTree.getZTreeObj("zTree");
    treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
    // 初始化餐次下拉选
    common.initCodeSelect("MEALSTYPE", "MEALSTYPE", "foodMeal");
    $("#foodMeal").find("option:first-child").text("请选择餐次");
    // 食谱数据源加载（查询类别下的菜谱）
    foodTable = datatable.table(foodTableOption);

    //=====================================【第二部分：定义事件】=====================================
    // 定义--食谱天数选择
    $("input:checkbox[name='myFoodDays']").die("change").live("change", function () {
        savePlanDiet();// 保存食谱
    });
    // 定义--点击事件
    $("button[name='planAdjustButton']").off("click").click(function () {
        if (this.id == "historyTemplate") {// 历次方案
            $("#historyPlanModal").modal("show");
        } else if (this.id == "personTemplate") {// 个人模板
            $("#personTemplateModal").modal("show");
        } else if (this.id == "addDietButton") {// 添加食谱
            $("#dietModal").modal("show");
        } else if (this.id == "addPersonTemplate") {// 显示添加到个人模板modal
            $("#addPersonTemplateModal").modal("show");
        } else if (this.id == "submitPersonTemplateButton") {// 添加个人模版
            var diseaseNameArray = [];
            $("#intakeTemplateForm input[name='diseaseNameList']").each(function (index, name) {
                diseaseNameArray.push(name.value);
            });
            $("#intakeMark").val(diseaseNameArray.join("、"));
            getPlanIntakeDetail();
            $("#intakeTemplateForm").submit();
        } else if (this.id == "intakeMealsButton") {// 餐次安排
            $("#intakeMealsModal").modal("show");
        } else if (this.id == "showIntakeTypeQit") {// 摄入类型提示信息
            $("#productQitModal").modal("show");
        }
    });
    // 定义--校验数据是否正确
    $("#planCreateForm input[input-required]").die("change").live("change", function () {
        this.value = checkInputNumber(this.value);
        setPlanInfo();
    });
    // 膳食清单明细框关闭后触发保存事件
    common.modal("intakeMealsModal", common.modalType.hidden, function () {
        // 重新计算设置
        setPlanInfo();
        // 保存饮食管理方案
        getPlanIntakeDetail();
        $("#planCreateForm").submit();
        console.log("保存饮食管理方案");
    });
    // 添加个人模板modal隐藏后清空搜索框
    common.modal("addPersonTemplateModal", common.modalType.hidden, function () {
        $("#diseaseSearch").val("");
    });
});
