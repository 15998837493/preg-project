var noBodyTr = "<tr><td colspan=\"100\" class=\"text-center no-body\"><h4>没有找到记录！</h4></td></tr>";
var noBodyDiv = "<div class=\"text-center no-body\"><h4>没有找到记录！</h4></div>";
var nowDate = common.dateFormat(new Date(), "yyyy-MM-dd");
var map; //判断项目是否已经做过
/**
 * 设置患者信息
 */
function setCustomerInfo() {
    var diagnosis = summaryMap.diagnosis;
    var customer = summaryMap.customer;
    var preArchive = summaryMap.preArchive;
    var obstetrical = summaryMap.obstetrical;
    $("#customerInfo #custPatientId").html(customer.custPatientId || "");
    $("#customerInfo #custSikeId").html(customer.custSikeId || "");
    $("#customerInfo #custName").html(customer.custName || "");
    $("#customerInfo #custAge").html(diagnosis.diagnosisCustAge == 0 ? "": diagnosis.diagnosisCustAge + " 岁");
    $("#customerInfo #LastYJ").html(preArchive.lmp || "");
    $("#customerInfo #custHeight").html(customer.custHeight || "");
    var diagnosisGestationalWeeks = common.isEmpty(diagnosis.diagnosisGestationalWeeks) ? "": pregnancy.gestationalWeeksSupHtml(diagnosis.diagnosisGestationalWeeks) + " 周";
    $("#customerInfo #diagnosisGestationalWeeks").html(diagnosisGestationalWeeks || "");
    $("#customerInfo #custWeight").html(customer.custWeight || "");
    $("#customerInfo #bmi").html(preArchive.bmi || "");
    $("#customerInfo #pregnancyDueDate").html(preArchive.pregnancyDueDate || "");
    $("#customerInfo #embryoNum").html(preArchive.embryoNum || "");
    $("#disease").html(diagnosis.diagnosisDiseases); // 诊断
    var inspect = "";
    if (!common.isEmpty(diagnosis.diagnosisCustWeight)) { // 现体重
        inspect += "现体重：" + diagnosis.diagnosisCustWeight + " kg";
    }
    if (!common.isEmpty(obstetrical) && !common.isEmpty(obstetrical.obstetricalDiagnosisSystolic)) { // 收缩压
        inspect += " 收缩压：" + obstetrical.obstetricalDiagnosisSystolic + " mmHg";
    }
    if (!common.isEmpty(obstetrical) && !common.isEmpty(obstetrical.obstetricalDiagnosisDiastolic)) { // 舒张压
        inspect += " 舒张压：" + obstetrical.obstetricalDiagnosisDiastolic + " mmHg";
    }
    $("#general-inspect").html(inspect); // 一般检查
};

/**
 * 设置接诊信息
 */
function setDiagnosisInfo() {
    var diagnosis = summaryMap.diagnosis;
    var obstetrical = summaryMap.obstetrical;
    // 标题
    $("#summaryTitle").html(diagnosis.diagnosisDate + " 营养科");
    // 主述信息
    if (!common.isEmpty(obstetrical)) {
        $("#diagnosisInfo #diagnosisMain").html(obstetrical.obstetricalDiagnosisMain || "");
    }
    // 营养评价结论信息
    let inspectResult = "";
    if(!common.isEmpty(diagnosis.diagnosisInspectResult)) {
    	inspectResult = diagnosis.diagnosisInspectResult.replace(/\n/g,"<br/>");
    }
    $("#diagnosisInfo #diagnosisInspectResult").html(inspectResult);
}

/**
 * 设置辅助检查指标信息
 */
function setClinicalInfo() {
    var jsonData = summaryMap.clinicalItemList;
    if (!common.isEmpty(jsonData) && jsonData.length > 0) {
        var jsonDataNew = []; // 报告单（没有空报告单的情况）
        for (var x = 0; x < jsonData.length; x++) {
            var itemList = jsonData[x].itemList;
            var jsonDataShowFlag = 0;
            for (var z = 0; z < itemList.length; z++) {
                if (!common.isEmpty(itemList[z].itemValue)) {
                    jsonDataShowFlag = 1;
                    break;
                }
            }
            if (jsonDataShowFlag == 1) { // 报告单里面至少有一个指标
                jsonDataNew.push(jsonData[x]);
            }
        }

        for (var x = 0; x < jsonDataNew.length; x++) {
            var itemList = jsonDataNew[x].itemList;
            if (itemList.length > 0) {
                $("#summary_abnormal_div").show();
                var style_bottom = "";
                if (jsonDataNew.length > 1 && x == 0) {
                    style_bottom = "style='margin-bottom: 10px;'";
                } else {
                    style_bottom = "style='margin-bottom: 20px;'";
                }
                var html = "<div class='panel panel-lightblue' " + style_bottom + " name='summary_abnormal_items_div'>" + "<div class='table-responsive'>" + "<table class='table table-bordered table-padding-4' name='clinicaIndex' style='margin-bottom: 0px;word-break:break-all; word-wrap:break-all;'>" + "<thead><tr> " + "<td class='text-left active' colspan='8' style='border-right-color: white;font-weight:bold;' id='clini_report_name_" + x + "'></td>" + "</tr>" + "<tr class='active'>" + "<td class='text-center' style='width: 6%'>序号</td>" + "<td class='text-center' style='width: 42%'>检验项目</td>" + "<td class='text-center' style='width: 8%'>结果</td>" + "<td class='text-center' style='width: 8%'>结论</td>" + "<td class='text-center' style='width: 8%'>上次孕周</td>" + "<td class='text-center' style='width: 8%'>上次结果</td>" + "<td class='text-center' style='width: 8%'>单位</td>" + "<td class='text-center' style='width: 12%'>参考范围</td></tr></thead></table></div></div>";
                $("#summary_abnormal_div").append(html);
                $("#clini_report_name_" + x).html("检验报告：" + jsonDataNew[x].reportName + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;检验日期：" + jsonDataNew[x].reportDate + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;孕周：" + (common.isEmpty(jsonDataNew[x].gestationalWeeks) ? "": pregnancy.gestationalWeeksSupHtml(jsonDataNew[x].gestationalWeeks)));
                index_order = 0;
                $(itemList).each(function(index, clinicalItem) {
                    if (!common.isEmpty(clinicalItem.itemValue)) { // 结果为空的数据不显示
                        index_order++;
                        var style_color = "";
                        if (clinicalItem.itemResult == "↑" || clinicalItem.itemResult == "↓") {
                            style_color = "style='color:red;'";
                        }
                        $("table[name='clinicaIndex']:eq(" + x + ")").append("<tbody><tr id=clini_out_" + x + ">" + "	<td class='text-center'>" + index_order + "</td>" + "	<td class='text-left' " + style_color + ">" + (clinicalItem.itemName || "——") + "</td>" + "	<td class='text-center' " + style_color + ">" + (clinicalItem.itemValue || "——") + "</td>" + "	<td class='text-center' " + style_color + ">" + (clinicalItem.itemResult || "——") + "</td>" + "	<td name='clini_week_" + index + "' class='text-center' " + style_color + "></td>" + "	<td name='clini_result_" + index + "' class='text-center' " + style_color + "></td>" + "	<td class='text-center' " + style_color + ">" + (clinicalItem.itemUnit || "——") + "</td>" + "	<td class='text-center' " + style_color + ">" + (clinicalItem.itemRefValue || "——") + "</td>" + "</tr></tbody>");
                        var lastResult = "";
                        if (!common.isEmpty(clinicalItem.lastItemValue)) {
                            lastResult += clinicalItem.lastItemValue;
                        }
                        if (!common.isEmpty(clinicalItem.lastItemResult)) {
                            lastResult += clinicalItem.lastItemResult;
                        }
                        $("#clini_out_" + x + " td[name='clini_result_" + index + "']").html(common.isEmpty(lastResult) ? "——": lastResult);
                        $("#clini_out_" + x + " td[name='clini_week_" + index + "']").html(common.isEmpty(clinicalItem.gestationalWeeks) ? "——": pregnancy.gestationalWeeksSupHtml(clinicalItem.gestationalWeeks));
                    }
                });
            }
        }
    }
}

/**
 * 设置营养处方信息
 */
function setPrescriptionInfo() {
    if(!common.isEmpty(summaryMap.extenderList) || !common.isEmpty(summaryMap.diagnosis.diagnosisExtenderDesc)) {
    	$("#summary_prescription_div").show();
    	if(!common.isEmpty(summaryMap.extenderList)) {
    		$("#summary_prescription_div table thead").show();
    		$("#summary_prescription_div table tbody").show();
            $(summaryMap.extenderList).each(function(index, extender) {
                var dosage = "";
                if (!common.isEmpty(extender.productDosage)) {
                    dosage = extender.productDosage + " " + extender.productUnit;
                }
                var statusCode = extender.status; // 状态
                var status = "";
                var styleSign = "";
                if (!common.isEmpty(statusCode)) {
                    if (statusCode == 1) {
                        status = "新增";
                        styleSign = "style='color:red;'";
                    } else if (statusCode == 2) {
                        status = "继服";
                    } else if (statusCode == 3) {
                        status = "停用";
                    }
                }
                $("#extenderList").append("<tr>" + "	<td class='text-center'>" + (index + 1) + "</td>" + "	<td " + styleSign + " class='text-left'>" + extender.productName + "</td>" + "	<td " + styleSign + " class='text-center'>" + dosage + "</td>" + "	<td " + styleSign + " class='text-left'>" + extender.productDosageDesc + "</td>" + "	<td " + styleSign + " class='text-left'>" + extender.productUsageMethod + "</td>" + "	<td " + styleSign + " class='text-left'>" + CODE.transCode("PRODUCTFREQUENCY", extender.productFrequency) + "</td>" + "	<td " + styleSign + " class='text-center'>" + extender.productTreatment + "</td>" + "	<td " + styleSign + " class='text-center'>" + status + "</td>" + "</tr>");
        });	
    	}else {
    		$("#summary_prescription_div table thead").hide();
    		$("#summary_prescription_div table tbody").hide();
    	}
        if (!common.isEmpty(summaryMap.diagnosis.diagnosisExtenderDesc)) {
            $("#extenderListTfoot").show();         
            $("#diagnosisExtenderDesc").html(summaryMap.diagnosis.diagnosisExtenderDesc.replace(/\n/g,"<br/>"));
        }
    }
    if ($("#extenderList tr").length == 0) {
        $("#extenderList").append(noBodyTr);
    }
}

/**
 * 设置食谱信息
 */
function setDietInfo() {
    var planInfo = summaryMap.planPojo;
    if (!common.isEmpty(summaryMap.diagnosis.diagnosisDietRemark)) {
        $("#dietRemarkFoot").show();
    }
    if (!common.isEmpty(planInfo) && (!common.isEmpty(planInfo.dietName) || !common.isEmpty(planInfo.intakeCaloric))) {
        $("#summary_diet_div").show();
        $("#dietName").html(planInfo.dietName);
        $("#intakeCaloric").html(planInfo.intakeCaloric);
        var cpfArray = [];
        if (!_.isEmpty(planInfo.intakeCbrPercent)) {
            cpfArray.push(planInfo.intakeCbrPercent);
        }
        if (!_.isEmpty(planInfo.intakeProteinPercent)) {
            cpfArray.push(planInfo.intakeProteinPercent);
        }
        if (!_.isEmpty(planInfo.intakeFatPercent)) {
            cpfArray.push(planInfo.intakeFatPercent);
        }
        $("#cpf").html(cpfArray.join("；"));
        $("#diagnosisDietRemark").html(summaryMap.diagnosis.diagnosisDietRemark.replace(/\n/g,"<br/>"));
    } else if (!_.isEmpty(summaryMap.diagnosis.diagnosisDietRemark)) {
        $("#summary_diet_div").show();
        $("#diagnosisDietRemark").html(summaryMap.diagnosis.diagnosisDietRemark.replace(/\n/g,"<br/>"));
        $("#summary_diet_div_tbody").css("display", "none");
    };
}

/**
 * 设置复查预约信息
 */
function setBookingInfo() {
    if (!common.isEmpty(summaryMap.bookingList)) {
        $("#summary_booking_div").show();
        // 复查预约及时间
        var dateHtml = "";
        var diagnosis = summaryMap.bookingList[summaryMap.bookingList.length - 1];
        dateHtml = "复诊建议：" + diagnosis.bookingSuggest + " " + diagnosis.bookingRemark + "<br/>复诊时间：" + diagnosis.bookingDate;
        $("#booking_inspect").html(dateHtml);
        // 辅助检查项目
        if (!common.isEmpty(summaryMap.fuzhuList)) {
            var inspectNames = [];
            var hints = [];
            var suggest = [];
            $(summaryMap.fuzhuList).each(function(index, fuzhu) {
                inspectNames.push(fuzhu.inspectItemName);
                if (!common.isEmpty($.trim(fuzhu.reviewHints))) {
                    hints.push(fuzhu.reviewHints);
                }
                suggest.push(fuzhu.resultsSuggest);
            });
            var hintsHtml = "";
            if ($.inArray("7", suggest) != -1) {
                var suggestDate = common.dateFormat(new Date((new Date(diagnosis.bookingDate).getTime()) - 1000 * 60 * 60 * 24 * 7), "yyyy-MM-dd");
                hintsHtml = "提示，您的复查检测中包含一周出结果的项目，来院复检时间应不晚于 " + suggestDate + "，" + hints.join("；") + " 下次随诊时请携带复查结果。";
            } else if ($.inArray("1", suggest) != -1) {
                var suggestDate = common.dateFormat(new Date((new Date(diagnosis.bookingDate).getTime()) - 1000 * 60 * 60 * 24), "yyyy-MM-dd");
                hintsHtml = "提示，您的复查检测中包含次日出结果的项目，来院复检时间应不晚于 " + suggestDate + "，" + hints.join("；") + " 下次随诊时请携带复查结果。";
            } else {
                hintsHtml = "提示，您的复查检测当日化验即可出结果，" + hints.join("；") + " 来院时需先化验取结果后随诊。";
            }
            $("#booking_inspect").append("<br/>复查检测：" + inspectNames.join("、") + "<br/>" + "复查时间：" + hintsHtml);
        } else {
            $("#booking_inspect").html(dateHtml);
        }
    } else {
        if (!common.isEmpty(summaryMap.fuzhuList)) {
            $("#summary_booking_div").show();
            var inspectNames = [];
            $(summaryMap.fuzhuList).each(function(index, fuzhu) {
                inspectNames.push(fuzhu.inspectItemName);
            });
            $("#booking_inspect").html("复查检测：" + inspectNames.join("、"));
        }
    }
}

/**
 * 显示课程预约的信息
 */
function setCourseBookInfo() {
    if (!common.isEmpty(summaryMap.courseBookList)) {
        var courseBookList = summaryMap.courseBookList;
        $.each(courseBookList,
        function(index, value) {
            $("#course_booking_tbody_summary").append("<tr>" + "  <td class='text-center'>" + value.bookingDate + "</td>" + "  <td class='text-center'>" + value.courseTime + "</td>" + "  <td class='text-left'>" + value.courseContent + "</td>" + "</tr>");
        });
        $("#course_booding_div").show();
    }
}

function showItemModal() {
    validPdf();
    $("#itemModal").modal("show");
};

//pdf报告没有内容的置灰不可选
function validPdf() {
    var diagnosisId = summaryMap.diagnosis.diagnosisId;
    var archId = summaryMap.preArchive.id; //建档id
    var url = URL.get("Platform.PDF_ISOK");
    var params = "diagnosisId=" + diagnosisId + "&archId=" + archId;
    ajax.post(url, params, dataType.json,
    function(data) {
        map = data.value; //返回一个map：false(没做)/true(做了)
        //判断报告是否可以打印
        for (var key in map) {
            if (map[key] === false || map[key] === null) {
                $("input[name='printIdList'][value='" + key + "']").attr("disabled", true);
                $("input[name='printIdList'][value='" + key + "']").next().css("color", "gray");
            };
        };

        //判断快速营养调查报告是否已经完成，完成能打印引导单，未完成则不能打印。
        if (map[nutrition] === false) {
            $("input[name='printIdList'][value='" + guideList + "']").attr("disabled", true);
            $("input[name='printIdList'][value='" + guideList + "']").next().css("color", "gray");
        };

        //营养门诊病历小结
        var flag = false;
        var cliniFlag = false;
        if (!common.isEmpty(summaryMap.clinicalItemList) && summaryMap.clinicalItemList.length > 0) {
            for (var x = 0; x < summaryMap.clinicalItemList.length; x++) {
                if (!common.isEmpty(summaryMap.clinicalItemList[x].itemList) && summaryMap.clinicalItemList[x].itemList.length > 0) {
                    cliniFlag = true;
                    break;
                }
            }
        }
        if (cliniFlag) { //辅助检查异常结果
            flag = true;
        } else if (!common.isEmpty(summaryMap.extenderList) || !common.isEmpty(summaryMap.diagnosis.diagnosisExtenderDesc)) { //营养处方
            flag = true;
        } else if (!common.isEmpty(summaryMap.planPojo)) { //膳食处方（注意：膳食处方备注不显示在病例小结pdf报告之中，按需求改的，所以这里不需要判断备注是否为空）
            flag = true;
        } else if (!common.isEmpty(summaryMap.bookingList) || !common.isEmpty(summaryMap.fuzhuList)) { //复查与复诊预约
            flag = true;
        } else if (!common.isEmpty(summaryMap.courseBookList)) { //课程预约
            flag = true;
        }
        if (flag == false) {
            if ($("#diagnosisMain").html() != "" || $("#diagnosisInspectResult").html() != "") { //即使别的都没有，但是营养主诉和本次营养评价结论有内容
                flag = true;
            };
        }
        if (flag == false) {
            $("input[name='printIdList'][value='" + caseSummary + "']").attr("disabled", "disabled");
            $("input[name='printIdList'][value='" + caseSummary + "']").next().css("color", "gray");
        };
    },
    false, false);
};

// 保存处方备注
function saveRemark(fieldName, value) {
    var diagnosisId = summaryMap.diagnosis.diagnosisId;
    var url = URL.get("Platform.DIAGNOSIS_UPDATE");
    var params = "diagnosisId=" + diagnosisId + "&" + fieldName + "=" + value;
    ajax.post(url, params, dataType.json, null, false, false);
}

function cli(type) {
    if (type == 1) { //患者信息
        $("#guide_customerinfo_div").show();
        getInterrogationView();
        $("#diagnosisRecordShow").removeClass("button-action");
        $("#interrogation").addClass("button-action");
        $("html,body").animate({
            scrollTop: 20
        },
        0);
    } else if (type == 2) { //辅助检查
        $("#guide_customerinfo_div").show();
        getInterrogationView();
        $("#diagnosisRecordShow").removeClass("button-action");
        $("#interrogation").addClass("button-action");
        $("html,body").animate({
            scrollTop: 820
        },
        0);
    } else if (type == 3) { //营养处方
        $("#guide_customerinfo_div").show();
        getDoctorView();
        $("#diagnosisRecordShow").removeClass("button-action");
        $("#doctor").addClass("button-action");
        $("html,body").animate({
            scrollTop: 546
        },
        0);
    } else if (type == 4) { //膳食处方
        $("#guide_customerinfo_div").show();
        getDoctorView();
        $("#diagnosisRecordShow").removeClass("button-action");
        $("#doctor").addClass("button-action");
        $("html,body").animate({
            scrollTop: 1000
        },
        0);
    } else if (type == 5) { //复诊预约
        $("#guide_customerinfo_div").show();
        getDoctorView();
        $("#diagnosisRecordShow").removeClass("button-action");
        $("#doctor").addClass("button-action");
        $("html,body").animate({
            scrollTop: $(document).height()
        },
        0);
    } else if (type == 6) {
        $("#guide_customerinfo_div").show();
        getDoctorView();
        $("#diagnosisRecordShow").removeClass("button-action");
        $("#doctor").addClass("button-action");
        $("html,body").animate({
            scrollTop: $(document).height()
        },
        0);
    }
}

/**
 * 生成妊娠日记pdf报告
 */
function showDiaryPdf(printId) {
    var diagnosisId = summaryMap.diagnosis.diagnosisId;
    var url = URL.get("Platform.SHOW_DIARYPDF");
    var params = "diagnosisId=" + diagnosisId + "&printId=" + printId;
    ajax.post(url, params, dataType.json,
    function(data) {
        common.openWindow(PublicConstant.basePath + "/" + data.value);
    },
    false, false);
}

/**
 * 纵向合并指定列的单位格
 * @param tableId
 * @param col：
 * 			对应列下标：从0开始
 */
function combineCell(tableId, col) {
    var tb = document.getElementById(tableId);
    var i = 0;
    while (i < tb.rows.length) {
        var cellSpan = tb.rows[i].cells[col].rowSpan;
        if (i + cellSpan >= tb.rows.length) {
            return;
        }
        if (tb.rows[i].cells[col].innerHTML == tb.rows[i + cellSpan].cells[col].innerHTML) {
            tb.rows[i + cellSpan].removeChild(tb.rows[i + cellSpan].cells[col]);
            tb.rows[i].cells[col].rowSpan += 1;
        } else {
            i += cellSpan;
        }
    }
}

/**
 * JSP页面--guide_page.jsp 病历打印
 */
function printPlanPDF() {
    var codes = "";
    $("input:checkbox[name='printIdList'][checked]").each(function(index, code) {
        if (index == 0) {
            codes = code.value;
        } else {
            codes += "," + code.value;
        }
    });
    if (!common.isEmpty(codes)) {
        var url = URL.get("Platform.RECEIVE_CREATE_PDF");
        var params = "codes=" + codes + "&diagnosisId=" + summaryMap.diagnosis.diagnosisId;
        ajax.post(url, params, dataType.json,
        function(data) {
            if (data) {
                $("#itemModal").modal("hide");
                common.openWindow(PublicConstant.basePath + "/" + data.value);
            } else {
                layer.msg(data.message);
            }
        });
        return;
    } else {
        layer.msg("请先选择要打印的内容！");
    };
}

/**
 * 选择引导单时，判断人体成分是否有记录。如果有直接选中，如果没有给出提示。
 * map["P02002"] 是人体成分结果 。false为，没做过人体成分true为做过人体成分
 */
function selectedGuideList(obj) {
    var checkVal = $("input[name='printIdList'][value='P02002']");
    if (checkVal.size() > 0) {
        if (map["P02002"] === false && $(obj).prop("checked")) {
            layer.confirm('缺少人体成分分析结论，是否继续选择？', {
                btn: ['继续选择', '返回问诊界面'] //按钮
            },
            function() {
                $(obj).attr("checked", true);
                layer.closeAll();
            },
            function() {
                // 问诊主页面,页面刷新自动回到问诊页面。
                location.reload();
            });
        };
    }
};
/**
 * PDF打印 全选/取消
 */
function checkAllPDF(obj) {
    if (obj.checked) {
        $("input:checkbox[name='printIdList']").attr("checked", true);
    } else {
        $("input:checkbox[name='printIdList']").attr("checked", false);
    }
}

/** 营养病例小结报告编码*/
var caseSummary = "P04001";
/** 引导单报告编码*/
var guideList = "P08001";
/** 快速营养调查报告编码*/
var nutrition = "P02004";
$().ready(function() {
    if (!common.isEmpty(summaryMap)) {
        // 设置患者信息
        setCustomerInfo();
        setDiagnosisInfo();
        // 设置辅助检查指标信息
        setClinicalInfo();
        // 设置营养处方信息
        setPrescriptionInfo();
        // 设置食谱信息
        setDietInfo();
        // 设置复查预约信息
        setBookingInfo();
        // 设置课程预约显示
        setCourseBookInfo();
    }

    $("input[name='printIdList'][value='P08001']").click(function() {
        selectedGuideList(this);
    });
});