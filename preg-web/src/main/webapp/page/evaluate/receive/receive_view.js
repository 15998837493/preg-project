$.validator.addMethod("babyWeekNumber",
function(value, element) {
    if (element.value.trim() == "") {
        return true;
    }
    return element.value <= 40;
},
'胎儿周数最大为40周');

$.validator.addMethod("babyDayNumber",
function(value, element) {
    if (element.value.trim() == "") {
        return true;
    }
    return element.value <= 6;
},
'额外天数最大为6天');

/**
 * 问诊信息数据校验
 */
var diagnosisUpdateOption = {
    rules: {
        diagnosisFetusweek: {
            babyWeekNumber: true
        },
        diagnosisFetusday: {
            babyDayNumber: true
        },
        gestationalWeeks: {
            babyWeekNumber: true
        },
        gestationalDays: {
            babyDayNumber: true
        }
    },
    //设置错误信息显示到指定位置
    errorPlacement: function(error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        $(form).ajaxPost(dataType.json, null, false, false);
    }
};

/**
 * 浮窗显示
 */
function getContentDetail(code, content) {
    if (common.isEmpty(content)) {
        content = "无";
    }
    return "<a title='" + content + "'>详情</a>";
}

/**
 * 羊水和
 */
function fluidSum() {
    var sum = 0;
    if (!common.isEmpty($("#obstetricalAmnioticFluidOne").val()) && !common.isEmpty($("#obstetricalAmnioticFluidTwo").val()) && !common.isEmpty($("#obstetricalAmnioticFluidThree").val()) && !common.isEmpty($("#obstetricalAmnioticFluidFour").val())) {
        sum += parseFloat($("#obstetricalAmnioticFluidOne").val());
        sum += parseFloat($("#obstetricalAmnioticFluidTwo").val());
        sum += parseFloat($("#obstetricalAmnioticFluidThree").val());
        sum += parseFloat($("#obstetricalAmnioticFluidFour").val());
    }
    $("#obstetricalAmnioticFluid").val(sum.toFixed(1));
}

/**
 * 检验input只能为数字，小数点后保留一位
 */
function checkNum(obj,name) {
    var flag = true;
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
        flag = false;
    }
    if (obj != null) {
        //检查小数点后是否多于两位
        if (obj.value.toString().split(".")[0].length > 3) {
            layer.msg("整数位最多三位数");
            obj.value = "";
            flag = false;
        }
        if (obj.value.toString().split(".").length > 1) { //如果是小数
            if (obj.value.toString().split(".")[0].length > 1 && obj.value.toString().split(".")[0].substring(0, 1) == "0") { //如果是这种格式的小数 00.1 000.1 0000.1 000000.1
                layer.msg("请输入正常小数！");
                obj.value = "";
                flag = false;
            }
            if (obj.value.toString().split(".")[1].length > 1) { //如果小数点后大于1位
                layer.msg("小数点后只能有一位！");
                obj.value = "";
                flag = false;
            }
        } else { //如果是整数
            if (obj.value.toString().length > 1 && obj.value.toString().substring(0, 1) == "0") { //如果是这种格式 01 001 001 0001
                layer.msg("请输入正常整数！");
                obj.value = "";
                flag = false;
            }
            if(name != "AmnioticFluid") {
                if (obj.value.toString() == "0") {
                    layer.msg("不能输入0");
                    obj.value = "";
                    flag = false;
                }	
            }
        }
    }
    return flag;
};

/**
 * 初始化接诊信息
 */
function initDaignosisInfo() {
    if (!common.isEmpty(doctorName)) {
        var templat = "科室来源：" + org + " 转诊医生：" + doctorName;
        $("#diagnonsisInfo").html(templat);
        $("#editDiagnosisInfo").removeClass("hide");
    };
}

/**
 * 初始化时间插件(选择时间的范围是末次月经~预产期之间)
 */
function initTimeDateReceiveView(id) {
    common.initDate(null, null, null, "#" + id);
    if (id == "obstetricalTopDate") {
        $("#" + id).val(obstetricalTopDateJson);
    } else if (id == "obstetricalBottomDate") {
        $("#" + id).val(obstetricalBottomDateJson);
    }
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
    $("#" + id).datetimepicker("setStartDate", d_lmp); // 末次月经
    $("#"+id).datetimepicker("setEndDate",nowDate);// 今天
}

/**
 * 根据孕周计算推荐值
 */
function calculator(week) {
    //fundal:代表宫高   abdominal:代表腹围   foetus:代表胎儿{"胎儿体重":weight,"胎儿股骨长":femurLength,"胎儿双顶径":biparietalDiameter,"胎儿腹围":fetalAbdominal}
    var foetus = {}; //胎儿信息
    var fundal = {}; //宫高
    var abdominal = {}; //腹围
    switch (parseInt(week)) {
    case 17:
        foetus = {
            "weight": "",
            "femurLength": "23",
            "biparietalDiameter": "36",
            "fetalAbdominal": "112"
        };
        break;
    case 18:
        foetus = {
            "weight": "",
            "femurLength": "26",
            "biparietalDiameter": "39",
            "fetalAbdominal": "124"
        };
        break;
    case 19:
        foetus = {
            "weight": "",
            "femurLength": "29",
            "biparietalDiameter": "43",
            "fetalAbdominal": "135"
        };
        break;
    case 20:
        foetus = {
            "weight": "",
            "femurLength": "32",
            "biparietalDiameter": "46",
            "fetalAbdominal": "147"
        };
        fundal = {
            "down": "15.3",
            "up": "21.4",
            "standard": "18.6"
        };
        break;
    case 21:
        foetus = {
            "weight": "320",
            "femurLength": "35",
            "biparietalDiameter": "50",
            "fetalAbdominal": "159"
        };
        fundal = {
            "down": "15.3",
            "up": "21.4",
            "standard": "19"
        };
        break;
    case 22:
        foetus = {
            "weight": "320",
            "femurLength": "37",
            "biparietalDiameter": "53",
            "fetalAbdominal": "170"
        };
        fundal = {
            "down": "15.3",
            "up": "21.4",
            "standard": "20.2"
        };
        break;
    case 23:
        foetus = {
            "weight": "365",
            "femurLength": "40",
            "biparietalDiameter": "56",
            "fetalAbdominal": "182"
        };
        fundal = {
            "down": "15.3",
            "up": "21.4",
            "standard": "21.1"
        };
        break;
    case 24:
        foetus = {
            "weight": "417",
            "femurLength": "43",
            "biparietalDiameter": "59",
            "fetalAbdominal": "193"
        };
        fundal = {
            "down": "22",
            "up": "25.1",
            "standard": "22-24"
        };
        break;
    case 25:
        foetus = {
            "weight": "477",
            "femurLength": "45",
            "biparietalDiameter": "62",
            "fetalAbdominal": "204"
        };
        fundal = {
            "down": "22",
            "up": "25.1",
            "standard": "23.4"
        };
        break;
    case 26:
        foetus = {
            "weight": "546",
            "femurLength": "48",
            "biparietalDiameter": "64",
            "fetalAbdominal": "215"
        };
        fundal = {
            "down": "22",
            "up": "25.1",
            "standard": "23.9"
        };
        break;
    case 27:
        foetus = {
            "weight": "627",
            "femurLength": "50",
            "biparietalDiameter": "67",
            "fetalAbdominal": "226"
        };
        fundal = {
            "down": "22",
            "up": "25.1",
            "standard": "24.8"
        };
        break;
    case 28:
        foetus = {
            "weight": "720",
            "femurLength": "53",
            "biparietalDiameter": "70",
            "fetalAbdominal": "237"
        };
        fundal = {
            "down": "22.4",
            "up": "29",
            "standard": "25.6-26"
        };
        break;
    case 29:
        foetus = {
            "weight": "829",
            "femurLength": "55",
            "biparietalDiameter": "72",
            "fetalAbdominal": "248"
        };
        fundal = {
            "down": "22.4",
            "up": "29",
            "standard": "26.5"
        };
        break;
    case 30:
        foetus = {
            "weight": "955",
            "femurLength": "57",
            "biparietalDiameter": "75",
            "fetalAbdominal": "258"
        };
        fundal = {
            "down": "22.4",
            "up": "29",
            "standard": "27.8"
        };
        break;
    case 31:
        foetus = {
            "weight": "1100",
            "femurLength": "60",
            "biparietalDiameter": "77",
            "fetalAbdominal": "269"
        };
        fundal = {
            "down": "22.4",
            "up": "29",
            "standard": "28.6"
        };
        break;
    case 32:
        foetus = {
            "weight": "1284",
            "femurLength": "62",
            "biparietalDiameter": "80",
            "fetalAbdominal": "279"
        };
        fundal = {
            "down": "25.3",
            "up": "32",
            "standard": "29"
        };
        break;
    case 33:
        foetus = {
            "weight": "1499",
            "femurLength": "64",
            "biparietalDiameter": "82",
            "fetalAbdominal": "290"
        };
        fundal = {
            "down": "25.3",
            "up": "32",
            "standard": "29.8"
        };
        break;
    case 34:
        foetus = {
            "weight": "1728",
            "femurLength": "66",
            "biparietalDiameter": "85",
            "fetalAbdominal": "300"
        };
        fundal = {
            "down": "25.3",
            "up": "32",
            "standard": "30.6"
        };
        break;
    case 35:
        foetus = {
            "weight": "1974",
            "femurLength": "68",
            "biparietalDiameter": "87",
            "fetalAbdominal": "311"
        };
        fundal = {
            "down": "25.3",
            "up": "32",
            "standard": "31.1"
        };
        break;
    case 36:
        foetus = {
            "weight": "2224",
            "femurLength": "70",
            "biparietalDiameter": "89",
            "fetalAbdominal": "321"
        };
        fundal = {
            "down": "29.8",
            "up": "34.5",
            "standard": "31.6-32"
        };
        break;
    case 37:
        foetus = {
            "weight": "2455",
            "femurLength": "72",
            "biparietalDiameter": "91",
            "fetalAbdominal": "331"
        };
        fundal = {
            "down": "29.8",
            "up": "34.5",
            "standard": "31.9"
        };
        break;
    case 38:
        foetus = {
            "weight": "2642",
            "femurLength": "74",
            "biparietalDiameter": "93",
            "fetalAbdominal": "341"
        };
        fundal = {
            "down": "29.8",
            "up": "34.5",
            "standard": "32.3"
        };
        break;
    case 39:
        foetus = {
            "weight": "2790",
            "femurLength": "76",
            "biparietalDiameter": "96",
            "fetalAbdominal": "351"
        };
        fundal = {
            "down": "29.8",
            "up": "34.5",
            "standard": "32.8"
        };
        break;
    case 40:
        foetus = {
            "weight": "2891",
            "femurLength": "78",
            "biparietalDiameter": "98",
            "fetalAbdominal": "361"
        };
        fundal = {
            "down": "29.8",
            "up": "34.5",
            "standard": "33"
        };
        break;
    default:
    }
    if (week >= 20 && week < 24) {
        abdominal = {
            "down": "76",
            "up": "89",
            "standard": "82"
        };
    } else if (week >= 24 && week < 28) {
        abdominal = {
            "down": "80",
            "up": "91",
            "standard": "85"
        };
    } else if (week >= 28 && week < 32) {
        abdominal = {
            "down": "82",
            "up": "94",
            "standard": "87"
        };
    } else if (week >= 32 && week < 36) {
        abdominal = {
            "down": "84",
            "up": "95",
            "standard": "89"
        };
    } else if (week >= 36 && week <= 40) {
        if (week == 40) {
            abdominal = {
                "down": "89",
                "up": "100",
                "standard": "94"
            };
        } else {
            abdominal = {
                "down": "86",
                "up": "98",
                "standard": "92"
            };
        };
    };

    var fetus = {
        "week": week,
        "fundal": fundal,
        "abdominal": abdominal,
        "foetus": foetus
    };
    return fetus;
}

/**
 * 显示宫高腹围胎儿体重
 */
function showResult(week, type) {
    if (_.isEmpty(week)) {
        layer.msg("孕周为空！");
        return;
    }
    var fetus = calculator(week);
    if (type == 1) {
        //宫高
        if (!_.isEmpty(fetus.fundal)) {
            //宫高标准值（推荐）
            var fundal = ""; //（推荐：18.6 cm；上下限： 15.3-21.4 cm）
            if (fetus.fundal.standard.indexOf("-") > 0) {
                var strArr = fetus.fundal.standard.split("-");
                fundal += "（推荐： <font color='red'>" + strArr[0] + "</font>-" + strArr[1] + " CM(推荐)；";
            } else {
                if (fetus.week == "32" || fetus.week == "40") {
                    fundal += "（推荐： <font color='red'>" + fetus.fundal.standard + "</font> CM(推荐)；";
                } else {
                    fundal += "（推荐： " + fetus.fundal.standard + " CM(推荐)；";
                };
            };
            //宫高上下限
            fundal += " 上下限：" + fetus.fundal.down + "-" + fetus.fundal.up + " CM）";
            $("#fundal_standard").html(fundal);
            $("#obstetricalFundalHeightResult").val(fundal);
        } else {
            $("#fundal_standard").html("");
            $("#obstetricalFundalHeightResult").val("");
        }

        //腹围
        if (!_.isEmpty(fetus.abdominal)) {
            var abdominal = "";
            //腹围标准		
            abdominal += "（推荐： " + fetus.abdominal.standard + " CM(推荐)；";
            //腹围上下限
            abdominal += " 上下限：" + fetus.abdominal.down + "-" + fetus.abdominal.up + " CM）";
            $("#abdominal_standard").html(abdominal);
            $("#obstetricalAbdominalPerimeterResult").val(abdominal);
        } else {
            $("#abdominal_standard").html("");
            $("#obstetricalAbdominalPerimeterResult").val("");
        }
    } else if (type == 2) {
        //胎儿信息
        if (!_.isEmpty(fetus.foetus)) {
            //胎儿体重
            var weight = "";
            if (!_.isEmpty(fetus.foetus.weight)) {
                weight += "（推荐： " + fetus.foetus.weight + " g）";
            };
            $("#foetus_weight").html(weight);
            $("#obstetricalBabyWeightResult").val(weight);
            //胎儿股长
            var femur = "";
            femur += "（推荐： " + fetus.foetus.femurLength + " mm）";
            $("#foetus_femur_length").html(femur);
            $("#obstetricalBabyFemurResult").val(femur);
            //胎儿双顶径 
            var diameter = "";
            diameter += "（推荐： " + fetus.foetus.biparietalDiameter + " mm）";
            $("#foetus_biparietal_diameter").html(diameter);
            $("#obstetricalBabyBdpResult").val(diameter);
            //胎儿腹围
            var abdominal = "";
            abdominal += "（推荐： " + fetus.foetus.fetalAbdominal + " mm）";
            $("#foetus_fetal_abdominal").html(abdominal);
            $("#obstetricalBabyAbdominalPerimeterResult").val(abdominal);
        } else {
            $("#foetus_weight").html("");
            $("#obstetricalBabyWeightResult").val("");
            $("#foetus_femur_length").html("");
            $("#obstetricalBabyFemurResult").val("");
            $("#foetus_biparietal_diameter").html("");
            $("#obstetricalBabyBdpResult").val("");
            $("#foetus_fetal_abdominal").html("");
            $("#obstetricalBabyAbdominalPerimeterResult").val("");
        }
    }
};

$(document).ready(function() {
    // 初始化日期选择插件
	initTimeDateReceiveView("obstetricalTopDate");
	initTimeDateReceiveView("obstetricalBottomDate");
    
    //初始化接诊信息
    initDaignosisInfo();

    //加入必填项提示
    $("#diagnosisObstetricalUpdateForm").validate(diagnosisUpdateOption);
    common.requiredHint("diagnosisObstetricalUpdateForm", diagnosisUpdateOption);

    //初始化select 胎儿发育情况
    var babyInfo = obstetricalBabyJson;
    $("#obstetricalBaby").val(babyInfo);
    
    //初始化select 妊娠风险级别
    common.initCodeSelect("BIRTHINSPECTLEVEL", "BIRTHINSPECTLEVEL", "obstetricalGestationLevel", obstetricalGestationLevelJson, "==请选择5色风险级别==");

    // 初始化羊水
    fluidSum();

    $("#weight_input_text").change(function() {
        diagnosisJson.diagnosisCustWeight = $("#weight_input_text").val();
    });

    $("#obstetricalTopDate").change(function() {
        var preg_week = pregnancy.getGestWeeksByLmpDate(this.value, d_lmp);
        if (!common.isEmpty(preg_week)) {
            var week = preg_week.split("+")[0];
            var day = preg_week.split("+")[1];
            var result = '（孕周数：' + week + ' <sup style="font-size: 85%;">＋' + day + '</sup> 周）';
            $("#preg_top_week").html(result);
            $("#obstetricalTopGestationalweeks").val(result);
            showResult(week, 1);
        }
    });

    $("#obstetricalBottomDate").change(function() {
        var preg_week = pregnancy.getGestWeeksByDueDate(this.value, d_due);
        if (!common.isEmpty(preg_week)) {
            var week = preg_week.split("+")[0];
            var day = preg_week.split("+")[1];
            var result = '（孕周数：' + week + ' <sup style="font-size: 85%;">＋' + day + '</sup> 周）';
            $("#preg_bottom_week").html(result);
            $("#obstetricalBottomGestationalweeks").val(result);
            showResult(week, 2);
        }
    });

    $("#obstetricalAmnioticFluidOne,#obstetricalAmnioticFluidTwo,#obstetricalAmnioticFluidThree,#obstetricalAmnioticFluidFour").keyup(function() {
        fluidSum();
    });
});