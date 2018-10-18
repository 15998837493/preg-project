//人员列表对象
var personDataTable;
//人员列表数据
var personData = [];
//阶段值，用来存放查人员列表的阶段值，在导出Excel中用
var pregPeriodValue;
var queryCondition = "";
//妊娠期
var pregColumns = [
    {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            return meta.row + 1;
        }
    },
    {
        "data": "custName",
        "sClass": "text-center"
    },
    {
        "data": "custSikeId",
        "sClass": "text-center"
    },
    {
        "data": "custPatientId",
        "sClass": "text-center"
    },
    {
        "data": "custAge",
        "sClass": "text-center"
    },
    /*{
        "data" : "custOrg",
        "sClass" : "text-center",
        "render" : function(data, type, row, meta){
            if(data == "孕期营养门诊"){
                return "是";
            }else{
                return "否";
            }
        }
    },*/
    {
        "data": "doctorName",
        "sClass": "text-center"
    },
    {
        "data": "diagnosisCount",
        "sClass": "text-center"
    },
    {
        "data": "pregnancyNum",
        "sClass": "text-center"
    },
    {
        "data": "birthNum",
        "sClass": "text-center"
    },
    {
        "data": "encyesisSituation",
        "sClass": "text-center"
    },
    {
        "data": "pregnancyDueDate",
        "sClass": "text-center"
    },
    {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (data.diagnosisCount == null || data.diagnosisCount == 0) {
                return "————";
            } else {
                return '<a onclick="clickToHistory(\'' + data.custId + '\')">查看</a>';
            }
        }
    }];
//产后
var birthColumns = [
    {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            return meta.row + 1;
        }
    },
    {
        "data": "custName",
        "sClass": "text-center"
    },
    {
        "data": "custSikeId",
        "sClass": "text-center"
    },
    {
        "data": "custPatientId",
        "sClass": "text-center"
    },
    {
        "data": "custAge",
        "sClass": "text-center"
    },
    /*{
        "data" : "custOrg",
        "sClass" : "text-center",
        "render" : function(data, type, row, meta){
            if(data == "孕期营养门诊"){
                return "是";
            }else{
                return "否";
            }
        }
    },*/
    {
        "data": "doctorName",
        "sClass": "text-center"
    },
    {
        "data": "diagnosisCount",
        "sClass": "text-center"
    },
    {
        "data": "pregnancyNum",
        "sClass": "text-center"
    },
    {
        "data": "birthNum",
        "sClass": "text-center"
    },
    {
        "data": "birthTiresType",
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            var type = "";
            if (data == 1) {
                if (row.birthTiresType2 == 1) {
                    type = "自然受孕 意外妊娠";
                } else if (row.birthTiresType2 == 2) {
                    type = "自然受孕 计划妊娠";
                } else {
                    type = "自然受孕";
                }
            } else if (data == 2) {
                type = "辅助生殖";
            } else {
                type = data;
            }
            return type;
        }
    },
    {
        "data": "birthDate",
        "sClass": "text-center"
    },
    {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (data.diagnosisCount == null || data.diagnosisCount == 0) {
                return "————";
            } else {
                return '<a onclick="clickToHistory(\'' + data.custId + '\')">查看</a>';
            }
        }
    },
    {
        "data": null,
        "sClass": "text-center",
        "render": function (data, type, row, meta) {
            if (data.birthNum == null || data.birthNum == 0) {
                return "————";
            } else {
                return "<a onclick='birthEnding(\"" + data.custId + "\")' style='cursor:pointer;'>查看</a>";
            }
        }
    }];
//列表datatable
var tableOptions = {
    id: "personPregTable",
    form: "queryForm",
    callback: function () {
        $("#condition0_timeType").attr("disabled", true);
    },
    columns: pregColumns
};
$().ready(function () {
    $("#condition0_pregPeriod").addClass("inputborder");
    $("#condition0_timeType").addClass("inputborder");
    $("#condition0_startDate").addClass("inputborder");
    $("#condition0_endDate").addClass("inputborder");

    initAllDate();

    initAllSelectOperation();

    initSelectAll(userList, "#condition0_userSelect", "全部营养医师", "全部医师", "150px", 1, "", "non", "请选择营养医师", "个医师");
    initSelectAll(bmiList, "#condition1_bmiScope", "全部", "全部", "150px", 2, "", "non", "请选择BMI范围", "种范围");
    initSelectAll(birthNumList, "#condition1_birthNum", "全部", "全部", "150px", 2, "", "non", "请选择胎数", "种");
    initSelectAll(pregTimesList, "#condition2_pregTimes", "全部", "全部", "180px", 2, "", "non", "请选择孕次", "种");
    initSelectAll(birthTimesList, "#condition2_birthTimes", "全部", "全部", "180px", 2, "", "non", "请选择产次", "种");
    initSelectAll(badPregHistoryList, "#condition2_badPregHistory", "全部", "全部", "180px", 2, "", "non", "请选择不良孕史", "种");
    initSelectAll(badBirthHistoryList, "#condition2_badBirthHistory", "全部", "全部", "180px", 2, "", "non", "请选择不良产史", "种");

    initSelectAll(weightConditionList, "#condition4_weightCondition", "全部", "全部", "170px", 2, "", "non", "请选择体重增长情况", "种");
    initSelectAll(fuzhongConditionList, "#condition4_fuzhongCondition", "全部", "全部", "150px", 2, "", "non", "请选择浮肿情况", "种");
    initSelectAll(firstLevelList, "#condition5_firstLevel", "全部", "全部", "240px", 2, "", "non", "请选择首次产检妊娠风险级别", "种");
    initSelectAll(lastLevelList, "#condition5_lastLevel", "全部", "全部", "240px", 2, "", "non", "请选择末次产检妊娠风险级别", "种");
    initSelectAll(childbirthTypeList, "#condition6_childbirthType", "全部", "全部", "150px", 2, "", "non", "请选择分娩方式", "种");
    initSelectAll(mazuiTypeList, "#condition6_mazuiType", "全部", "全部", "150px", 2, "", "non", "请选择麻醉类型", "种");
    initSelectAll(newBirthSexList, "#condition6_newBirthSex", "全部", "全部", "150px", 2, "", "non", "请选择新生儿性别", "种");
    initSelectAll(whenDeadList, "#condition6_whenDead", "全部", "全部", "100px", 2, "", "non", "请选择", "种");
    $("#condition6_whenDead").parent().hide();
    initSelectAll(afvList, "#condition6_afv", "全部", "全部", "150px", 2, "", "non", "请选择羊水量", "种");
    initSelectAll(afluidList, "#condition6_afluid", "全部", "全部", "150px", 2, "", "non", "请选择羊水性状", "种");

    initSelectAll(pregComplicationsList, "#condition2_pregComplications", "全部", "全部", "180px", 2, "", "non", "请选择妊娠并发症", "种");
    initSelectAll(diseaseHistoryList, "#condition2_diseaseHistory", "全部", "全部", "180px", 2, "", "non", "请选择疾病史", "种");
    initSelectAll(familyHistoryList, "#condition2_familyHistory", "全部", "全部", "180px", 2, "", "non", "请选择家族史", "种");

    layer.close(layer.index);
});


//分娩结局datatable
var birthEndingOptions = {
    id: "birthEndingTable",
    form: "queryBirthEndingForm",
    async: true,
    columns: [
        {"data": null, "sClass": "text-center", "orderable": false},
        {"data": "birthDate", "sClass": "text-center", "orderable": false,},
        {"data": "birthWeeks", "sClass": "text-center", "orderable": false,},
        {"data": "birthHospital", "sClass": "text-center", "orderable": false,},
        {"data": "birthPregHospital", "sClass": "text-center", "orderable": false,},
        {"data": "createTime", "sClass": "text-center", "orderable": false,},
        {"data": "createUserName", "sClass": "text-center", "orderable": false,},
        {
            "data": null, "sClass": "text-center", "orderable": false,
            "render": function (data, type, row, meta) {
                return "<a onclick='showBirthEnding(\"" + data.birthId + "\",\"" + data.custId + "\")'>查看</a>";
            }
        }
    ],
    condition: "Condition",
    orderIndex: 0,
};

/**
 * 跳转到营养病历
 */
function clickToHistory(custId) {
    common.openWindow(URL.get("Platform.DIAGNOSIS_RECORD") + "?custId=" + custId);
}

/**
 * 跳转到分娩记录页面
 * @param custId
 * @returns
 */
function birthEnding(custId) {
    $("#queryBirthEndingForm [name='custId']").val(custId);
    datatable.table(birthEndingOptions);
    //关闭遮罩层
    layer.close(layer.index);
    $("#birthEndingModal").modal("show");
}

/**
 * 查看分娩结局
 * @param custId
 * @returns
 */
function showBirthEnding(birthId, custId) {
    common.openWindow(URL.get("BirthEnding.BIRTHENDING_DETAIL") + "?birthId=" + birthId + "&custId=" + custId);
}

/**
 * 超级查询
 * @returns
 */
function queryForm() {
    if (common.isEmpty($("#condition0_pregPeriod").val())) {
        layer.msg("请填写基础条件：阶段");
    } else if (common.isEmpty($("#condition0_timeType").val())) {
        layer.msg("请填写基础条件：日期分类");
    } else if (common.isEmpty($("#condition0_startDate").val())) {
        layer.msg("请填写基础条件：开始日期");
    } else if (common.isEmpty($("#condition0_endDate").val())) {
        layer.msg("请填写基础条件：结束日期");
    } else {
        $("#condition0_timeType").removeAttr("disabled")
        pregPeriodValue = $("#condition0_pregPeriod").val();
        queryCondition = "";
        $("#queryCondition").children(":visible").each(function () {//tr
            if ($(this).attr("id") == "queryButton") {
                return false;
            }
            $(this).children().each(function (index) {
                if (index == 0) {
                    queryCondition += "换行符******" + $(this).html() + "******换行符";
                } else {//td
                    $(this).children().children(":visible").each(function (index) {
                        $(this).children().each(function () {
                            queryCondition += " " + $(this).html().replace("&lt;", "小于").replace("&gt;", "大于");
                        })
                    });
                }
            });
        });
        personDataTable = datatable.table(tableOptions);
    }

}

/**
 * 分娩结局统计报表查询
 * @returns
 */
function queryBirthResultForm() {
    if (common.isEmpty($("#conditionResult_resultStartDate").val()) || common.isEmpty($("#conditionResult_resultEndDate").val())) {
        layer.msg("请选择开始日期和结束日期");
        return false;
    }
    $('#birthResultQueryForm').ajaxPost(dataType.json, function (data) {
        if (data.result) {
            jsonToTable("birthResultTable", data.value);
        } else {
            layer.alert(data.message);
        }

    });
}

/**
 * 导出Excel
 * @returns
 */
function exportResultForm() {
    if (common.isEmpty(pregPeriodValue)) {
        layer.msg("请先查询人员列表");
        return false;
    }
    personData = datatable.getAllData(personDataTable);
    var personIds = [];
    $.each(personData, function (index, obj) {
        if ($.inArray(obj.custId, personIds) < 0) {
            personIds.push(obj.custId);
        }
    });
    if (personIds.length == 0) {
        layer.msg("人员列表为空");
        return false;
    }
    if ($("#pregInspectExcel").is(":checked") && common.isEmpty($("#pregInspectIds").val())) {
        $("#pregInspectNameExcel").addClass("inputborder");
        layer.msg("请选择检验项目");
    } else {
        var resultFormParams = $("#exportForm").serializeJson();
        var choice = [];
        if (!common.isEmpty(resultFormParams.allExcel)) {
            choice.push(resultFormParams.allExcel);
        }
        if (!common.isEmpty(resultFormParams.bodyExcel)) {
            choice.push(resultFormParams.bodyExcel);
        }
        if (!common.isEmpty(resultFormParams.pregInspectExcel)) {
            choice.push(resultFormParams.pregInspectExcel);
        }
        if (!common.isEmpty(resultFormParams.nutritionExcel)) {
            choice.push(resultFormParams.nutritionExcel);
        }
        if (!common.isEmpty(resultFormParams.birthResultExcel)) {
            choice.push(resultFormParams.birthResultExcel);
        }
        if (choice.length == 0) {
            layer.msg("请选择要导出的内容");
            return false;
        }
        var url = URL.get("Statistic.STATISTIC_EXPORT");
        var params = "custIds=" + personIds.join(",")
            + "&conditions=" + choice.join(",")
            + "&items=" + resultFormParams.inspectIds
            + "&stage=" + pregPeriodValue
            + "&queryCondition=" + queryCondition;
        if ((!common.isEmpty(resultFormParams.allExcel) || !common.isEmpty(resultFormParams.bodyExcel) || !common.isEmpty(resultFormParams.nutritionExcel)) && common.isEmpty($("#pregInspectIds").val())) {
            hint.confirm("未选择检验项目，是否继续导出？", function (lay) {
                layer.msg('正在导出，请稍后...', {
                    icon: 16,
                    shade: 0.01,
                    time: false
                });
                ajax.post(url, params, dataType.json, function (data) {
                    if (!data.result) {
                        layer.alert(data.message)
                    } else {
                        window.location.href = URL.get("Statistic.EXPORT_EXCEL") + "?dataKey=" + data.value;
                        setTimeout(isExported, 1500);
                    }
                }, false);
            })
        } else {
            layer.msg('正在导出，请稍后...', {
                icon: 16,
                shade: 0.01,
                time: false
            });
            ajax.post(url, params, dataType.json, function (data) {
                if (!data.result) {
                    layer.alert(data.message)
                } else {
                    window.location.href = URL.get("Statistic.EXPORT_EXCEL") + "?dataKey=" + data.value;
                    ;
                    setTimeout(isExported, 1500);
                }
            }, false)
        }
    }

}

/**
 * 检测导出文件是否生成完毕
 * @returns
 */
function isExported() {
    ajax.post(URL.get("Statistic.IS_EXPORTED"), null, dataType.json, function (data) {
        if (data.value) {
            layer.close(layer.index);
        } else {
            setTimeout(isExported, 1500);
        }
    }, false)
}

/**
 * 将json数据赋值给对应的table
 * @param tableId
 * @param jsonData
 * @returns
 */
function jsonToTable(tableId, jsonData) {
    $.each(jsonData, function (key, value) {
        if (!common.isEmpty(key) && !common.isEmpty(value)) {
            $("#" + tableId).find("[id='" + key + "']").html(value);
        } else {
            $("#" + tableId).find("[id='" + key + "']").html("");
        }
    });
}

/**
 * 身高、体重、相位角联动
 * @param fromId
 * @param toId
 * @param tipText
 * @returns
 */
function onFromToChange(fromId, toId, tipText) {
    var fromValue = $("#" + fromId).val();
    var toValue = $("#" + toId).val();
    var startId = fromId.substr(0, fromId.indexOf("_"));
    startId = fromId.replace(startId, startId + startId.charAt(startId.length - 1));
    var conditionId = toId.substr(0, toId.indexOf("_"));
    conditionId = toId.replace(conditionId, conditionId + conditionId.charAt(conditionId.length - 1));

    var startId1 = fromId.substr(0, fromId.indexOf("_"));
    startId1 = fromId.replace(startId1, startId1 + startId1.charAt(startId1.length - 1) + startId1.charAt(startId1.length - 1));
    var endId1 = toId.substr(0, toId.indexOf("_"));
    endId1 = toId.replace(endId1, endId1 + endId1.charAt(endId1.length - 1) + endId1.charAt(endId1.length - 1));
    var equalId = toId.substr(0, toId.indexOf("_"));
    equalId = toId.replace(equalId, equalId + equalId.charAt(equalId.length - 1) + equalId.charAt(equalId.length - 1) + equalId.charAt(equalId.length - 1));
    if (!common.isEmpty(fromValue) && !common.isEmpty(toValue)) {
        if (Number(fromValue) > Number(toValue)) {
            layer.msg(tipText + "输入有误，下限值大于上限值，请重新输入！");
            $("#" + toId).val("");
            var showId = fromId.substr(0, fromId.indexOf("_"));
            showId = fromId.replace(showId, showId + showId.charAt(showId.length - 1) + showId.charAt(showId.length - 1));
            setNullId(fromId, toId, showId, fromValue);
        } else if (Number(fromValue) == Number(toValue)) {
            setNullId(fromId, toId, equalId, toValue);
        } else {
            setConditionTable(fromId, "input");
            setConditionTable(toId, "input");

            $("#" + startId1).html("");
            $("#" + startId1).parent().hide();

            $("#" + endId1).html("");
            $("#" + endId1).parent().hide();

            $("#" + equalId).html("");
            $("#" + equalId).parent().hide();
        }
    } else if (common.isEmpty(fromValue) && !common.isEmpty(toValue)) {
        setNullId(fromId, toId, endId1, toValue);
    } else if (!common.isEmpty(fromValue) && common.isEmpty(toValue)) {
        setNullId(fromId, toId, startId1, fromValue);
    } else {
        setConditionTable(fromId, "input");
        setConditionTable(toId, "input");
    }
}

/**
 * 身高、年龄等字段 from to 两个字段有一个为空 走这里
 * @param fromId
 * @param toId
 * @param showId
 * @param showValue
 * @returns
 */
function setNullId(fromId, toId, showId, showValue) {
    var startId = fromId.substr(0, fromId.indexOf("_"));
    startId = fromId.replace(startId, startId + startId.charAt(startId.length - 1));
    var endId = toId.substr(0, toId.indexOf("_"));
    endId = toId.replace(endId, endId + endId.charAt(endId.length - 1));

    var startId1 = fromId.substr(0, fromId.indexOf("_"));
    startId1 = fromId.replace(startId1, startId1 + startId1.charAt(startId1.length - 1) + startId1.charAt(startId1.length - 1));
    var endId1 = toId.substr(0, toId.indexOf("_"));
    endId1 = toId.replace(endId1, endId1 + endId1.charAt(endId1.length - 1) + endId1.charAt(endId1.length - 1));

    var showId1 = showId.substr(0, showId.indexOf("_"));
    showId1 = showId.replace(showId1, showId1 + showId1.charAt(showId1.length - 1) + showId1.charAt(showId1.length - 1));

    var equalId = toId.substr(0, toId.indexOf("_"));
    equalId = toId.replace(equalId, equalId + equalId.charAt(equalId.length - 1) + equalId.charAt(equalId.length - 1) + equalId.charAt(equalId.length - 1));

    $("#" + startId).html("");
    $("#" + endId).html("");
    $("#" + startId).parent().hide();

    $("#" + startId1).html("");
    $("#" + startId1).parent().hide();

    $("#" + endId1).html("");
    $("#" + endId1).parent().hide();

    $("#" + equalId).html("");
    $("#" + equalId).parent().hide();

    $("#" + showId).html(showValue);
    $("#" + showId).parent().show();
    $("#" + showId).parent().parent().parent().parent().show();
}

/**
 * 初始化所有的诊断、检验项目等selectOperation控件
 * @returns
 */
function initAllSelectOperation() {
    // 诊断
    common.autocompleteMethod("diseaseName", diseaseListData, function (data) {
        selectOperation.addSelectOperation(data.val.diseaseId, data.val.diseaseName, "diseaseList", "condition5_diseaseIds");
        $("#diseaseName").val("");
    });

    // 产妇并发症情况
    common.autocompleteMethod("complicationName", diseaseListData, function (data) {
        selectOperation.addSelectOperation(data.val.diseaseId, data.val.diseaseName, "complicationList", "condition6_complicationIds");
        $("#complicationName").val("");
    });

    // 新生儿并发症
    common.autocompleteMethod("childComplicationName", diseaseListData, function (data) {
        selectOperation.addSelectOperation(data.val.diseaseId, data.val.diseaseName, "childComplicationList", "condition6_childComplicationIds");
        $("#childComplicationName").val("");
    });

    // 检验项目
    common.autocompleteMethod("normalInspectItemName", inspectAllListData, function (data) {
        var itemId = data.val.itemId;
        $("#normalInspectList").append(addInspectHtml(data.val));
        // 定义校验数值(实时)
        $("#normalInspectList input[input-valid]").off("change").on("change", function () {
            this.value = common.checkInputNumber("reg7", this.value);
            var itemMin = $("#" + itemId + "_min").val() || "";
            var itemMax = $("#" + itemId + "_max").val() || "";
            if (!common.isEmpty(itemMin) && !common.isEmpty(itemMax) && parseFloat(itemMin) > parseFloat(itemMax)) {
                layer.alert("下限值不能大于上限值！");
                $("#" + itemId + "_max").val("").change();
                return false;
            }
            getInspectIdsAndValues();
        });
        getInspectIdsAndValues();
        $("#normalInspectItemName").val("").change();
    });

    // 检验项目--导出Excel
    common.autocompleteMethod("pregInspectNameExcel", inspectAllListData, function (data) {
        selectOperation.addSelectOperation(data.val.itemId, data.val.itemName, "inspectList", "pregInspectIds");
        $("#pregInspectNameExcel").val("");
        $("#pregInspectNameExcel").removeClass("inputborder");
    });

    // 分娩方位
    common.autocompleteMethod("condition6_birthPlace", birthPlaceListData, function (data) {
        $("#condition6_birthPlaceId").val(data.val.codeValue);
        setConditionTable('condition6_birthPlace', 'input');
    });

}

/**
 * 添加检验项目HTML
 */
function addInspectHtml(inspect) {
    if (common.isEmpty(inspect) || $("#condition3_normalInspectItemIds_" + inspect.itemId).length > 0) {
        return false;
    }
    var inspectHtml = `
		<div class="div-table-cell" id="condition3_normalInspectItemIds_${inspect.itemId}" name="${inspect.itemName}">
			<div class="intake-sm div-table-cell">
				<a>${inspect.itemName}</a>
				<a onclick="removeInspectHtml('condition3_normalInspectItemIds_${inspect.itemId}');"><i class='fa fa-remove fa-fw'></i></a>
			</div>
			<div class="div-table-cell">
				<input type="text" id="${inspect.itemId}_min" class="intake-sm" placeholder="下限" style="width: 50px;" input-valid/>
			</div>
			<div class="div-table-cell">
				<input type="text" id="${inspect.itemId}_max" class="intake-sm" placeholder="上限" style="width: 50px; margin-right: 10px;" input-valid/>
			</div>
		</div>
	`;
    return inspectHtml;
}

/**
 * 删除检验项目HTML
 */
function removeInspectHtml(id) {
    $("#" + id).remove();
    getInspectIdsAndValues();
}

/**
 * 获取当前选择检验项目值
 */
function getInspectIdsAndValues() {
    var inspectArray = [];
    var items = [];
    $("#normalInspectList").children("div").not(":first").each(function (index, div) {
        var itemId = div.id.substring("condition3_normalInspectItemIds_".length, div.id.length);
        var itemMin = $("#" + itemId + "_min").val() || "";
        var itemMax = $("#" + itemId + "_max").val() || "";
        var itemName = $(div).attr("name");
        var resultQit = "";
        if (!common.isEmpty(itemMin) && common.isEmpty(itemMax)) {
            resultQit = itemName + "：≥" + itemMin;
        } else if (common.isEmpty(itemMin) && !common.isEmpty(itemMax)) {
            resultQit = itemName + "：≤" + itemMax;
        } else if (!common.isEmpty(itemMin) && !common.isEmpty(itemMax)) {
            resultQit = itemName + "：" + itemMin + "~" + itemMax;
        } else {
            resultQit = itemName;
        }
        items.push(resultQit);
        inspectArray.push(itemId + "~" + itemMin + "~" + itemMax);
    });
    $("#condition3_normalInspectItemIds").val(inspectArray.join(","));
    $("#condition3_normalInspectItemIdsNames").val(items.join("；")).change();
}

/**
 * 分娩方位联动
 * @param id
 * @param type
 * @returns
 */
function onBirthPlaceChange(id, type) {
    if (common.isEmpty($("#" + id).val())) {
        $("#condition6_birthPlaceId").val("");
    }
    setConditionTable(id, type);
}

/**
 * 初始化页面中的所有日期控件
 * @returns
 */
function initAllDate() {
    common.initDate(null, null, null, "#condition0_startDate");
    common.initDate(null, null, null, "#condition0_endDate");
    accountDateValid("condition0_startDate", "condition0_endDate");

    common.initDate(null, null, null, "#conditionResult_resultStartDate");
    common.initDate(null, null, null, "#conditionResult_resultEndDate");
    accountDateValid("conditionResult_resultStartDate", "conditionResult_resultEndDate");
}

/**
 * 限定时间的方法
 * 1. 下限为空时上限不能选择
 * 2. 上限选择完，调整下限时如果差值超过一年，或者大于上限，则清空上限，并调整上限的范围
 * 3. startId（下限），endId（上限）表示两个日期的插件的id，不可颠倒
 */
function accountDateValid(startId, endId) {
    var $startObj = $("#" + startId);
    var $endObj = $("#" + endId);
    // 初始化日期插件
    var nowDate = new Date();
    var tempNowDate = new Date();
    // if(!common.isEmpty($startObj.val())){
    //     tempNowDate = new Date($startObj.val());
    // }
    if ($("#condition0_pregPeriod").val() == "1") {
        nowDate.setDate(nowDate.getDate() + 50 * 7);
    }
    tempNowDate = addYearStr(2, common.dateFormatToString(tempNowDate, "yyyy-MM-dd"));
    if (common.getDateDiff(tempNowDate, common.dateFormatToString(nowDate, "yyyy-MM-dd")) > 0) {
        nowDate = new Date(tempNowDate);
    }

    var maxDate = common.dateFormatToString(nowDate, "yyyy-MM-dd");
    common.initDate(null, null, 2, "#" + startId);
    $startObj.datetimepicker("setEndDate", maxDate);
    common.initDate(null, null, 2, "#" + endId);
    $endObj.datetimepicker("setEndDate", maxDate);

    // 限制日期选择插件
    $startObj.on("change", function () {
        var endDate = addYearStr(2, $startObj.val());
        //如果超过当前时间，那么最多只能是当前时间
        if (common.getDateDiff(endDate, nowDate) < 0) {
            endDate = nowDate;
        }
        $endObj.datetimepicker("setStartDate", $startObj.val());
        $endObj.datetimepicker("setEndDate", endDate);
        var dateDiff = common.getDateDiff($startObj.val(), $endObj.val());
        if (!common.isEmpty($endObj.val()) && (dateDiff > 730 || dateDiff < 0)) {
            $endObj.val("");
        }
        $endObj.attr("disabled", false);
        onFromToChange(startId, endId);
    });
    $endObj.on("change", function () {
        onFromToChange(startId, endId);
    })
}

/**
 * 计算AddYearCount天后的日期
 *
 * @param AddDayCount
 * @param date
 * @returns {String}
 */
function addYearStr(AddYearCount, date) {
    var dd = common.isEmpty(date) ? new Date() : new Date(date);
    var y = dd.getFullYear() + AddYearCount;
    var m = (dd.getMonth() + 1) < 10 ? "0" + (dd.getMonth() + 1) : (dd.getMonth() + 1);//获取当前月份的日期，不足10补0
    var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();//获取当前几号，不足10补0

    return y + "-" + m + "-" + d;
}

/**
 * 初始化下拉多选框
 */
function initSelectAll(dataList, id, name, selectAllName, width, type, ulWidth, nonSelected, nonSelectName, nonSelectText) {
    if (common.isEmpty(ulWidth)) {
        ulWidth = 170;
    }
    $(id).multiselect({
        templates: {
            ul: '<ul class="multiselect-container dropdown-menu" style=\'width:' + ulWidth + 'px;\'></ul>'
        },
        nonSelectedText: nonSelectName,
        nSelectedText: nonSelectText,
        maxHeight: 250,
        templates: {
            button: '<button type="button" class="multiselect dropdown-toggle" data-toggle="dropdown" style="padding:4px;"><span class="multiselect-selected-text"></span> <b class="caret"></b></button>',
            ul: '<ul class="multiselect-container dropdown-menu"></ul>',
            filter: '<li class="multiselect-item multiselect-filter"><div class="input-group"><span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span><input class="form-control multiselect-search" type="text"></div></li>',
            filterClearBtn: '<span class="input-group-btn"><button class="btn btn-default multiselect-clear-filter" type="button"><i class="glyphicon glyphicon-remove-circle"></i></button></span>',
            li: '<li><a tabindex="0"><label></label></a></li>',
            divider: '<li class="multiselect-item divider"></li>',
            liGroup: '<li class="multiselect-item multiselect-group"><label></label></li>'
        },
        includeSelectAllOption: true,
        selectAllText: selectAllName,
        allSelectedText: name,
        selectedClass: 'multiselect-selected',
        buttonWidth: width,
        onChange: function (option, checked) {
            setConditionTable(id.substr(1), "mutiple", option.text(), checked)
        },
        onSelectAll: function () {
            setConditionTable(id.substr(1), "mutiple", null, null, true);
        },
        onDeselectAll: function () {
            setConditionTable(id.substr(1), "mutiple", null, null, false);
        }
    });
    if (!_.isEmpty(dataList) && type == 1) {
        $.each(dataList, function (index, user) {
            $(id).append("<option value='" + user.userId + "'>" + user.userName + "</option>");
        });
    } else if (!_.isEmpty(dataList) && type == 2) {
        $.each(dataList, function (index, data) {
            $(id).append("<option value='" + data.codeValue + "'>" + data.codeName + "</option>");
        });
    }

    if (common.isEmpty(nonSelected)) {
        $(id).multiselect("rebuild").multiselect('selectAll', false).multiselect('updateButtonText');
    } else {
        $(id).multiselect("rebuild").multiselect('updateButtonText');
    }

}

/**
 * 基础条件阶段联动事件
 * @param obj
 * @returns
 */
function onPregPeriodChange(id) {
    pregPeriodValue = "";
    var value = $("#" + id).val();
    accountDateValid("condition0_startDate", "condition0_endDate");
    if (value == "1") {
        $("#condition0_timeType").val("1");
        $('input:radio[name=birthChild]').attr('checked', false);
        $("#condition0_childbirthHospital").hide();
        $('input:radio[name=birthSeize]').attr('checked', false);

        $("#condition00_birthSeize").html("");
        $("#condition00_birthSeize").parent().hide();

        $("#birthResultExcel").attr("checked", false);
        $("#birthResultExcelLabel").hide();

        $("#condition00_birthChild").html("");
        $("#condition00_birthChild").parent().hide();

        if ($("#navTab6").hasClass("active")) {
            $("#navTabA1").click();
        }
        $("#navTab6").hide();

        $("#pregTable").show();
        $("#birthTable").hide();

        tableOptions.columns = pregColumns;
        tableOptions.id = "personPregTable";
        $("#birthInfo").hide();
        $("#condition0_userSelect").parent().show();
    } else if (value == "2") {
        $("#condition0_timeType").val("2");
        $("#condition0_childbirthHospital").show();
        $("#navTab6").show();
        $('input:radio[name=birthSeize]').attr('checked', false);

        $("#condition00_birthSeize").html("");
        $("#condition00_birthSeize").parent().hide();
        $('input:radio[name=birthChild]').attr('checked', false);
        $("#condition00_birthChild").html("");
        $("#condition00_birthChild").parent().hide();

        $("#birthResultExcelLabel").show();

        $("#pregTable").hide();
        $("#birthTable").show();
        tableOptions.columns = birthColumns;
        tableOptions.id = "personBirthTable";

        $("#birthInfo").show();
        if ($("#birthInfo").children(1).children(0).children(".form-group:hidden").length == $("#birthInfo").children(1).children(0).children().length) {
            $("#birthInfo").hide();
        }

        $("#condition0_userSelect").multiselect("rebuild").multiselect('deselectAll', false).multiselect('updateButtonText');
        $("#condition0_userSelect").parent().hide();
        $("#condition00_userSelect").html("");
        $("#condition00_userSelect").parent().hide();
    } else {
        $("#condition0_timeType").val("");
        $('input:radio[name=birthChild]').attr('checked', false);
        $("#condition0_childbirthHospital").hide();
        $("#condition00_birthChild").html("");
        $("#condition00_birthChild").parent().hide();
        $('input:radio[name=birthSeize]').attr('checked', false);
        $("#condition00_birthSeize").html("");
        $("#condition00_birthSeize").parent().hide();

        $("#birthResultExcel").attr("checked", false);
        $("#birthResultExcelLabel").hide();
        if ($("#navTab6").hasClass("active")) {
            $("#navTabA1").click();
        }
        $("#navTab6").hide();

        $("#pregTable").show();
        $("#birthTable").hide();
        tableOptions.columns = pregColumns;
        tableOptions.id = "personPregTable";
        $("#birthInfo").hide();
        $("#condition0_userSelect").parent().show();
    }
    setConditionTable(id, "select");
    setConditionTable("condition0_timeType", "select");
}

/**
 * 是否死亡选择联动
 * @param value
 * @returns
 */
function onIsLiveOrDeadClick(value, selfId) {
    var radioChecked = $("#" + selfId).prop("checked");
    $("#" + selfId).prop('checked', !radioChecked);
    if (!radioChecked) {
        if (value == '1') {
            $("#condition6_whenDead").multiselect("rebuild").multiselect('deselectAll', false).multiselect('updateButtonText');
            $("#condition6_whenDead").parent().hide();
            $("#condition66_whenDead").html("");
            $("#condition66_isLiveOrDead").html("存活");
            $("#condition66_isLiveOrDead").parent().show();
        } else if (value == '2') {
            $("#condition6_whenDead").multiselect("rebuild").multiselect('selectAll', false).multiselect('updateButtonText');
            $("#condition6_whenDead").show()
            $("#condition6_whenDead").parent().show();
            $("#condition66_whenDead").html("产时,产后");
            $("#condition66_isLiveOrDead").html("死亡");
            $("#condition66_isLiveOrDead").parent().show();
        }
    } else {
        $("#condition6_whenDead").multiselect("rebuild").multiselect('deselectAll', false).multiselect('updateButtonText');
        $("#condition6_whenDead").parent().hide();
        $("#condition66_whenDead").html("");
        $("#condition66_isLiveOrDead").html("");
        $("#condition66_isLiveOrDead").parent().hide();
    }
    $("#condition66_whenDead").parent().parent().parent().parent().show();
    if ($("#condition66_whenDead").parent().parent().children(".form-group:hidden").length == $("#condition66_whenDead").parent().parent().children().length) {
        $("#condition66_whenDead").parent().parent().parent().parent().hide();
    }
}

/**
 * 本院、其他医院选择联动
 * @param id
 * @param value
 * @returns
 */
function onHospitalClick(id, value, selfId) {
    var radioChecked = $("#" + selfId).prop("checked");
    $("#" + selfId).prop('checked', !radioChecked);
    var conditionId = id.substr(0, id.indexOf("_"));
    conditionId = id.replace(conditionId, conditionId + conditionId.charAt(conditionId.length - 1));
    if (!radioChecked) {
        if (value == '1') {
            $("#" + conditionId).html("本院");
            $("#" + conditionId).parent().show();
        } else if (value == '2') {
            $("#" + conditionId).html("其他医院");
            $("#" + conditionId).parent().show();
        }
    } else {
        $("#" + conditionId).html("");
        $("#" + conditionId).parent().hide();
    }

    return false;
}

/**
 * 填写查询条件表格一栏
 * @param id
 * @param type
 * @param text
 * @param checked
 * @param selectAll
 * @param obj
 * @returns
 */
function setConditionTable(id, type, text, checked, selectAll, obj) {
    var conditionId = id.substr(0, id.indexOf("_"));
    conditionId = id.replace(conditionId, conditionId + conditionId.charAt(conditionId.length - 1));
    var conditionId1 = id.substr(0, id.indexOf("_"));
    conditionId1 = id.replace(conditionId1, conditionId1 + conditionId1.charAt(conditionId1.length - 1) + conditionId1.charAt(conditionId1.length - 1));
    if (type == "select") {
        if (common.isEmpty($("#" + id).val())) {
            $("#" + conditionId).html("");
            $("#" + conditionId).parent().hide();
            if ($("#" + conditionId).parent().parent().children(".form-group:hidden").length == $("#" + conditionId).parent().parent().children().length) {
                $("#" + conditionId).parent().parent().parent().parent().hide();
            }
        } else {
            if (id == "condition0_timeType") {
                $("#" + conditionId).html($("#" + id + " option:selected").text() + "：");
            } else {
                $("#" + conditionId).html($("#" + id + " option:selected").text());
            }
            $("#" + conditionId).parent().show();
            $("#" + conditionId).parent().parent().parent().parent().show();
        }
    } else if (type == "input") {
        if (common.isEmpty($("#" + id).val())) {
            $("#" + conditionId).html("");
            $("#" + conditionId).parent().hide();
            if ($("#" + conditionId).parent().parent().children(".form-group:hidden").length == $("#" + conditionId).parent().parent().children().length) {
                $("#" + conditionId).parent().parent().parent().parent().hide();
            }
            $("#" + conditionId1).html("");
            $("#" + conditionId1).parent().hide();
            if ($("#" + conditionId1).parent().parent().children(".form-group:hidden").length == $("#" + conditionId1).parent().parent().children().length) {
                $("#" + conditionId1).parent().parent().parent().parent().hide();
            }
        } else {
            $("#" + conditionId).html($("#" + id).val());
            $("#" + conditionId).parent().show();
            $("#" + conditionId).parent().parent().parent().parent().show();
        }
    } else if (type == "checkbox") {
        var newText = $("#" + conditionId).html();
        if (obj.checked) {
            if (!common.isEmpty(newText)) {
                newText += ",";
            }
            newText += text;
        } else {
            newText = newText.replace("," + text, "").replace(text + ",", "").replace(text, "");
        }
        if (common.isEmpty(newText)) {
            $("#" + conditionId).html("");
            $("#" + conditionId).parent().hide();
            if ($("#" + conditionId).parent().parent().children(".form-group:hidden").length == $("#" + conditionId).parent().parent().children().length) {
                $("#" + conditionId).parent().parent().parent().parent().hide();
            }
            return;
        }
        $("#" + conditionId).html(newText);
        $("#" + conditionId).parent().show();
        $("#" + conditionId).parent().parent().parent().parent().show();
    } else if (type == "mutiple") {
        if (selectAll != null && !selectAll) {
            $("#" + conditionId).html("");
            if (conditionId == "condition66_whenDead") {
                return;
            }
            $("#" + conditionId).parent().hide();
            if ($("#" + conditionId).parent().parent().children(".form-group:hidden").length == $("#" + conditionId).parent().parent().children().length) {
                $("#" + conditionId).parent().parent().parent().parent().hide();
            }
            return;
        } else if (selectAll) {
            var html = "";
            $("#" + id).multiselect("getSelected").find("option").each(function () {
                if (!common.isEmpty(html)) {
                    html += ",";
                }
                html += $(this).text();
            });
            $("#" + conditionId).html(html);
            $("#" + conditionId).parent().show();
            $("#" + conditionId).parent().parent().parent().parent().show();
            return;
        }
        var newText = $("#" + conditionId).html();
        if (checked) {
            if (!common.isEmpty(newText)) {
                newText += ",";
            }
            newText += text;
        } else {
            var newTextArray = newText.split(",");
            newTextArray.splice(newTextArray.indexOf(text.replace("<", "&lt;").replace(">", "&gt;")), 1);
            newText = newTextArray.join(",");
        }
        if (common.isEmpty(newText)) {
            $("#" + conditionId).html("");
            if (conditionId == "condition66_whenDead") {
                return;
            }
            $("#" + conditionId).parent().hide();
            if ($("#" + conditionId).parent().parent().children(".form-group:hidden").length == $("#" + conditionId).parent().parent().children().length) {
                $("#" + conditionId).parent().parent().parent().parent().hide();
            }
            return;
        }
        $("#" + conditionId).parent().show();
        $("#" + conditionId).parent().parent().parent().parent().show();
        $("#" + conditionId).html(newText);
    }
}

/**
 * 查询条件导出表-选中 孕期检验记录导出表联动
 * @returns
 */
function onCheckExcelChange(id, obj) {
    var inspectIds = $("#pregInspectIds").val().split(",");
    if (id == "pregInspectExcel") {
        if (obj.checked) {
            $("#inspectList").show();
            $("#pregInspectNameExcel").addClass("inputborder");
        } else {
            if ($("#allExcel").is(':checked') || $("#bodyExcel").is(':checked') || $("#nutritionExcel").is(':checked')) {
                $("#inspectList").show();
            } else {
                $.each(inspectIds, function (index, id) {
                    if (!common.isEmpty(id)) {
                        $("#pregInspectIds_" + id).remove();
                    }
                });
                $("#pregInspectIds").val("");
                $("#inspectList").hide();
            }
            $("#pregInspectNameExcel").removeClass("inputborder");
        }
    } else if (id == "allExcel" || id == "bodyExcel" || id == "nutritionExcel") {
        if (obj.checked) {
            $("#inspectList").show();
            $("#normalInspectList").children("div").not(":first").each(function (index, div) {
                var itemId = div.id.substring("condition3_normalInspectItemIds_".length, div.id.length);
                var itemName = $(div).attr("name");
                selectOperation.addSelectOperation(itemId, itemName, "inspectList", "pregInspectIds");
            });
        } else if (!$("#allExcel").is(':checked')
            && !$("#bodyExcel").is(':checked')
            && !$("#nutritionExcel").is(':checked')
            && !$("#pregInspectExcel").is(':checked')) {
            $.each(inspectIds, function (index, id) {
                if (!common.isEmpty(id)) {
                    $("#pregInspectIds_" + id).remove();
                }
            });
            $("#pregInspectIds").val("");
            $("#inspectList").hide();
        }
    }
}

/**
 * 检验整型
 * @param fromId
 * @param toId
 * @param tipText
 * @param obj
 * @returns
 */
function checkInteger(fromId, toId, tipText, obj) {
    var flag = true;
    // 不允许输入空格
    obj.value = obj.value.replace(/\s+/g, '');
    //检查是否是非数字值
    if (isNaN(obj.value) || obj.value.indexOf("-") == 0 || obj.value.indexOf(".") >= 0) {
        layer.msg("请输入正整数！");
        obj.value = "";
    }
    if (obj.value.toString().length > 1 && obj.value.toString().substring(0, 1) == "0") { //如果是这种格式 01 001 001 0001
        layer.msg("请输入正常整数！");
        obj.value = "";
    }
    onFromToChange(fromId, toId, tipText);
}

/**
 * 检验小数
 * @param fromId
 * @param toId
 * @param tipText
 * @param obj
 * @param num  最多几位小数
 * @returns
 */
function checkFloat(fromId, toId, tipText, obj, num) {
    // 不允许输入空格
    obj.value = obj.value.replace(/\s+/g, '');
    //检查是否是非数字值
    if (isNaN(obj.value) || obj.value.indexOf("-") == 0) {
        layer.msg("请输入正整数或小数！");
        obj.value = "";
    }
    if (obj.value.toString().split(".").length > 1) { //如果是小数
        if (obj.value.toString().split(".")[0].length > 1 && obj.value.toString().split(".")[0].substring(0, 1) == "0") { //如果是这种格式的小数 00.1 000.1 0000.1 000000.1
            layer.msg("请输入正常小数！");
            obj.value = "";
        }
        if (obj.value.toString().split(".")[1].length > num) { //如果小数点后大于num位
            layer.msg("小数点后最多能有 " + num + " 位！");
            obj.value = obj.value.substr(0, obj.value.indexOf(".") + num + 1);
        }
    }
    onFromToChange(fromId, toId, tipText);
}
