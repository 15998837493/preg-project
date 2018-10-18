/**
 * 添加操作前初始化数据
 */
function initAddDiagnosis() {
    $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_ADD_BOOKDATE"));
    $("#diagnosisDateCreate").val("");
    $("#diagnosisRemark").val("");
    $("#diagnosisResetSuggest").val("");
}

/**
 * 修改操作前初始化数据
 */
function initUpdateDiagnosis(bookingId) {
    $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_UPDATE_BOOKDATE"));
    var diagnosis = $("#" + bookingId + "_tr").data();
    if (!common.isEmpty(diagnosis)) {
        $("#diagnosisDateCreate").val(diagnosis.bookingDate + " " + diagnosis.bookingVisitTime); // 复诊日期
        $("#diagnosisResetSuggest").val(diagnosis.bookingSuggest); // 复诊建议
        $("#diagnosisRemark").val(diagnosis.bookingRemark); // 其他复诊建议
        $("#diagnosisDateCreateOld").val(diagnosis.bookingDate);
        $("#diagnosisDateParam").val(diagnosis.bookingDate);
        $("#fuzhenbookingId").val(bookingId);
    }
}

/**
 * 复诊预约一览添加标签
 */
function addDiagnosisRow(diagnosis) {
    $("#noRecordTr").remove();
    var bookingId = diagnosis.bookingId;
    $("#t_body").append("<tr id='" + bookingId + "_tr'>" + "	<td class='text-center'><span date='" + diagnosis.bookingDate + "' id='diagnosisDate'>" + diagnosis.bookingDate + "</span></td>" +
    /* "	<td class='text-center'><span id='"+diagnosisId+"_visit_week'>"+diagnosis.diagnosisVisitTime+"</span></td>" + */
    "	<td><span id='" + bookingId + "_mark'>" + diagnosis.bookingSuggest + " " + diagnosis.bookingRemark + "</span></td>" + "	<td class='text-center'>" + "		<a onclick='initUpdateDiagnosis(\"" + bookingId + "\");'><i class='fa fa-edit fa-fw'></i>编辑预约</a>" + "		<a onclick='deleteDiagnosis(\"" + bookingId + "\");'><i class='fa fa-remove fa-fw'></i>取消预约</a>" + "	</td>" + "</tr>");
    $("#" + bookingId + "_tr").data(diagnosis);
}

/**
 * 验证参数设置
 */
var diagnosisOptions = {
    rules: {
        showDiagnosisDate: {
            required: true
        }
    },
    //设置错误信息显示到指定位置
    errorPlacement: function(error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        $(form).ajaxPost(dataType.json, function(data) {
        	updateDiagnosisStatus();
            var diagnosis = data.value;
            var $diagnosisTr = $("#" + diagnosis.bookingId + "_tr");
            if ($diagnosisTr.length == 0) { // 添加
                addDiagnosisRow(diagnosis);
                initAddDiagnosis();
                layer.alert("预约成功！");
            } else { // 编辑
                $("#" + diagnosis.bookingId + "_tr").find("#diagnosisDate").text(diagnosis.bookingDate);
                $("#" + diagnosis.bookingId + "_mark").text(diagnosis.bookingSuggest + " " + diagnosis.bookingRemark);
                $("#" + diagnosis.bookingId + "_tr").data(diagnosis);
                initAddDiagnosis();
                layer.alert("预约成功！");
            }
        });
    }
};

/**
 * 删除 复诊预约一览 记录
 */
function deleteDiagnosis(bookingId) {
    var url = URL.get("Platform.DIAGNOSIS_DELETE_BOOKDATE");
    var params = "bookingId=" + bookingId;
    $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_ADD_BOOKDATE"));
    $("#diagnosisDateCreate").val("");
    $("#diagnosisRemark").val("");
    $("#diagnosisResetSuggest").val("");
    ajax.post(url, params, dataType.json,
    function(data) {
        $("#" + bookingId + "_tr").remove();
        if ($("#t_body").find("tr").length == 0) {
            $("#t_body").html("<tr id='noRecordTr'><td colspan='10' class='text-center'><h4>没有找到记录！</h4></td></tr>");
        }
    });
}

/**
 * 弹出预约模态框
 */
function showData() {
    $("#nutrientTable").find("tbody").find("tr").remove();
    $("#addModal").modal("show");
    var url = URL.get("System.DOCTOR_QUERY");
    ajax.post(url, null, dataType.json,
    function(data) {
        if (data.data.length > 0) {
            //永远不可能没数据，最少也有1条数据，所以不用做无数据判断
            if (data.data.length < 11) { //小于或等于10条
                $("#showBody").css("height", "auto");
                $("#showBody").css("overflow-y", "none");
            } else { //大于10条
                $("#showBody").css("height", "430px");
                $("#showBody").css("overflow-y", "scroll");
            }
            $.each(data.data,
            function(key, value) {
                var background = "";
                var radios = "<input name='leafRadio' type='radio'/>";
                if (value.scheduleRealPerson >= value.scheduleMaxPerson) { //超出或等于预定上限
                    background = "style='background-color:#F5F5F5;'";
                    radios = "<input name='leafRadio' type='radio' disabled/>";
                }
                $("#nutrientTable").find("tbody").append("<tr " + background + ">" + "<td style='text-align:center;'>" + "<input name='maxPerson' type='hidden' value='" + value.scheduleMaxPerson + "'/>" + "<input name='realPerson' type='hidden' value='" + value.scheduleRealPerson + "'/>" + radios + "</td>" + "<td name='riqi' style='text-align:center;'>" + value.data + "</td>" + "<td name='zhou' style='text-align:center;'>" + value.scheduleWeek + "</td>" + "<td style='text-align:center;'>已预约" + value.scheduleRealPerson + "人，可预约" + value.scheduleMaxPerson + "人</td>" + "</tr>");
            });
        } else if (data.data.length == 0) {
            $("#showBody tbody").append("<tr><td colspan=4;><h4 style='text-align: center;'>没有找到记录！</h4></td></tr>");
        }
    },
    false, false);
}

/**
 * 复诊建议体重计算
 */
function getSuggestWeight(diagnosisDate) {
    var url = URL.get("Platform.QUERY_ECHARTS_WEIGHT_RESULT");
    var params = "diagnosisId=" + $("#fuzhendiagnosisId").val();
    var yunZhou = pregnancy.getGestWeeksByDueDate(diagnosisDate, preArchiveJson.pregnancyDueDate); //下次随诊时孕周(根据预产期计算)
    yunZhou = parseInt(yunZhou.substring(0, yunZhou.indexOf("+")));
    var return_result = ""; //返回值：复诊建议体重
    ajax.post(url, params, dataType.json,
    function(data) {
        var result = data.value;
        var weight_present = result.diagnosisCustWeight; //现体重
        var week_present = result.diagnosisGestationalWeeks; //现孕周
        var week_rise = result.diagnosisRiseWeek; //每周增长范围（可能为空）
        week_present = parseInt(week_present.substring(0, week_present.indexOf("+")));
        var week_yun = yunZhou - week_present; //随诊时孕周-现孕周
        if (week_rise == null || week_rise == "") { //每周增长范围在数据库中为空（也就是没有值）
            week_rise = pregnancy.getWeekWeightByBmi(preArchiveJson.bmi); //计算每周增长范围
        }
        if (weight_present != null && weight_present != "") {
            if (isNaN(parseFloat(week_rise.split("-")[0])) == false) { //如果每周增长范围不为空（如果为空，那就是bmi给的不正确）
                weight_present = parseFloat(result.diagnosisCustWeight);
                var result = (weight_present + week_yun * parseFloat(week_rise.split("-")[0])).toFixed(2) + "-" + (weight_present + week_yun * parseFloat(week_rise.split("-")[1])).toFixed(2);
                return_result = "建议下次复诊时体重增长至：" + result + "公斤";
                if (parseFloat(result.split("-")[0]) == parseFloat(result.split("-")[1])) {
                    result = week_rise;
                    return_result = "建议每周体重适宜增长范围：" + result + "公斤";
                }
            }
        }
    },
    false, false);
    return return_result;
}

$().ready(function() {
    // 填充当前的接诊ID
    $("#planBookForm #fuzhendiagnosisId").val(diagnosisJson.diagnosisId);

    // 加入必填项提示
    common.requiredHint("planBookForm", diagnosisOptions);
    $("#planBookForm").validate(diagnosisOptions);

    // 为复诊预约一览填充数据
    if (!common.isEmpty(diagnosisList)) {
        $(diagnosisList).each(function(index, diagnosis) {
            addDiagnosisRow(diagnosis);
        });
    };

    // 预约列表绑定行单击事件
    $("#nutrientTable tbody tr").live("click",
    function() {
        var max = parseInt($(this).find("input[name='maxPerson']").val());
        var real = parseInt($(this).find("input[name='realPerson']").val());
        if (real < max) { //预约人数没过上限
            $("input[name='leafRadio']").attr("checked", false);
            $(this).find("input[name='leafRadio']").attr("checked", true);
            $("#scheduleId").val($(this).find("#id").val());
        }
    });

    // 预约列表绑定行双击事件
    $("#nutrientTable tbody tr").live("dblclick",
    function() {
        var max = parseInt($(this).find("input[name='maxPerson']").val());
        var real = parseInt($(this).find("input[name='realPerson']").val());
        if (real < max) { //预约人数没过上限
            $("#diagnosisDateCreate").val($(this).find("td[name='riqi']").text() + " " + $(this).find("td[name='zhou']").text());
            $("#diagnosisDateParam").val($(this).find("td[name='riqi']").text());
            $("#diagnosisVisitTime").val($(this).find("td[name='zhou']").text());
            if ($("#planBookForm").attr("action") == URL.get("Platform.DIAGNOSIS_UPDATE_BOOKDATE")) {
                $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_UPDATE_BOOKDATE"));
            } else {
                $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_ADD_BOOKDATE"));
            }
            $("#diagnosisResetSuggest").val(getSuggestWeight($(this).find("td[name='riqi']").text()));
            $("#addModal").modal("hide");
        }
    });

    // 预约按钮绑定点击事件
    $("#book").click(function() {
        var radio = $("#nutrientTable").find("input[type='radio']");
        var checkedCount = $("#nutrientTable").find("input[type='radio']:checked").length; //radio选中的个数 1/0
        if (checkedCount == 1) {
            $.each(radio,
            function(key, value) {
                if ($(this).is(":checked")) {
                    $("#diagnosisDateCreate").val($(this).parent().siblings("td[name='riqi']").text() + " " + $(this).parent().siblings("td[name='zhou']").text());
                    $("#diagnosisDateParam").val($(this).parent().siblings("td[name='riqi']").text());
                    $("#diagnosisVisitTime").val($(this).parent().siblings("td[name='zhou']").text());
                    if ($("#planBookForm").attr("action") == URL.get("Platform.DIAGNOSIS_UPDATE_BOOKDATE")) {
                        $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_UPDATE_BOOKDATE"));
                    } else {
                        $("#planBookForm").attr("action", URL.get("Platform.DIAGNOSIS_ADD_BOOKDATE"));
                    }
                    $("#diagnosisResetSuggest").val(getSuggestWeight($(this).parent().siblings("td[name='riqi']").text()));
                    $("#addModal").modal("hide");
                }
            });
        } else {
            layer.alert("请选择你要预约的时间！");
        }
    });
});