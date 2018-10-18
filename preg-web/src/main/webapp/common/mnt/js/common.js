/**
 * 第一部分：自定义工具
 * 第二部分：日期转换
 * 第三部分：加减乘除运算
 * 第四部分：获取区域信息
 * 第五部分：获取代码表信息
 * 第六部分：初始化插件
 * 第七部分：定义公共业务工具方法
 * 第八部分：拓展jquery
 */

// 第一部分：自定义工具---------------------------------------------------------------------
/**
 * 工具--常量
 */
var dataType = {
    text: 'text',// 返回纯文本字符串
    html: 'html',// 返回纯文本 HTML 信息；包含的 script 标签会在插入 dom 时执行。
    xml: 'xml',// 返回 XML 文档，可用 jQuery 处理。
    script: 'script',// 返回纯文本 JavaScript 代码
    json: 'json', // 返回 JSON 数据 。
    jsonp: 'jsonp'// 如 "myurl?callback=?" jQuery将自动替换 ? 为正确的函数名，以执行回调函数。
};

common = {};

common = {
    create: 'create',
    update: 'update',
    retrieve: 'retrieve',
    remove: 'remove'
};

/**
 * 判断是否为空
 *
 * @param value
 * @returns {Boolean}
 */
common.isEmpty = function (value) {
    return value == null || value == '' || value == undefined || value == "null" || value == "NULL" || value == "NaN";
};

/**
 * 去掉字符串头尾空格
 *
 * @param str
 * @returns
 */
common.trim = function (str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 验证记录是否被选中
 *
 * @param id
 * @returns {Boolean}
 */
common.isChoose = function (id) {
    if (common.isEmpty(id)) {
        layer.msg("请先选择要操作的记录!");
        return false;
    } else {
        return true;
    }
};

/**
 * 浏览器重定向到新的页面
 *
 * @param href
 */
common.pageForward = function (href) {
    // 清空插件生成的垃圾
    common.clearPlugGarbage();

    $.get(href, function (data) {
        $("#content").html(data);
    });
};

/**
 * 清空表单
 *
 * @param formId
 */
common.clearForm = function (formId) {
    // 绑定前清空表单
    $(':input', '#' + formId).not(':button, :submit, :reset').removeAttr('checked').removeAttr('selected').not(':radio, :checkbox').val('').removeData("previousValue");
};

/**
 * 清空插件产生的垃圾代码
 */
common.clearPlugGarbage = function () {
    $('#scrollUp').nextAll().not("div[id='BlackBox']").remove();
    var inputs = $(':input').filter(".form-control");
    for (var i = 0; i < inputs.length; i++) {
        $(inputs[i]).parent().qtip("destroy");
    }
};

/**
 * 打开新窗口
 *
 * @param url
 * @param sFeatures
 */
common.openWindow = function (url, sFeatures) {
    if (common.isEmpty(sFeatures)) {
        var left = (window.screen.availWidth - 10 - 1280) / 2;
        var height = window.screen.availHeight;
        sFeatures = "toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,titlebar=0,width=1280,height=" + height + ",left=" + left;
    }
    window.open(url, new Date().getTime(), sFeatures);
};

/**
 * 图片放大
 *
 * @param img
 */
common.largepic = function (path) {
    content = "<div><img onerror='common.errorImg(this)' src='" + path + "' alt='图片加载失败！' class='img-responsive center-block'></div>";
    layer.open({
        type: 1,
        title: false,
        closeBtn: 0,
        area: "auto",
        skin: 'layui-layer-nobg', // 没有背景色
        shadeClose: true,
        content: content
    });
};

/**
 * 图片加载失败提供的默认图片
 */
common.errorImg = function (img) {
    $(img).attr("src", PublicConstant.basePath + "/common/images/img_error.png");
};

/**
 * 显示图片
 *
 * @param path
 * @param name
 */
common.setImg = function (path, name) {
    return "<img class='img-thumbnail img-responsive' " + "     style='width:40px;height:40px;' " + "     onclick='common.largepic(this.src)' "
        + "     onerror='common.errorImg(this)' " + "     src='" + path + "'/> " + (name || "");
};

/**
 * 结尾字符
 *
 * @param s
 * @returns {Boolean}
 */
common.endWith = function (str, end) {
    if (common.isEmpty(str) || common.isEmpty(end))
        return false;
    if (str.substring(str.length - end.length) == end)
        return true;
    else
        return false;
    return true;
};

/**
 * 开头字符
 *
 * @param s
 * @returns {Boolean}
 */
common.startWith = function (str, start) {
    if (common.isEmpty(str) || common.isEmpty(start))
        return false;
    if (str.substr(0, start.length) == start)
        return true;
    else
        return false;
    return true;
};

/**
 * 过滤特殊字符
 *
 * @param str
 * @returns {String}
 */
common.filterSpecialCharacters = function (str) {
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？%+_]");
    var specialStr = "";
    for (var i = 0; i < str.length; i++) {
        specialStr += str.substr(i, 1).replace(pattern, '');
    }
    return specialStr;
};

/**
 * 数字校验正则表达式
 */
common.numberRegs = {
    "reg1": /^([1-9]\d*[.]\d+)|(0[.]\d+)|0$/,// 正浮点数+零
    "reg2": /^-(([1-9]\d*[.]\d+)|(0[.]\d+)|0)$/,// 负浮点数+零
    "reg3": /^-?(([1-9]\d*[.]\d+)|(0[.]\d+)|0)$/,// 正负浮点数+零
    "reg4": /^([1-9]\d*)|0$/,// 正整数+零
    "reg5": /^-([1-9]\d*)|0$/,// 负整数+零
    "reg6": /^-?([1-9]\d*)|0$/,// 所有整数
    "reg7": /^-?(([1-9]\d*([.]\d+)?)|(0[.]\d+))|0$/,// 所有数
    "reg8": /^(([1-9]\d*([.]\d+)?)|(0[.]\d+))|0$/,// 所有正数+零
    "reg9": /^(([1-9]\d*([.]\d+)?)|(0[.]\d+))$/,// 所有正数
};

/**
 * 校验数据是否正确
 *
 * @param reg
 *            正则表达式
 * @param value
 *            数值
 * @param length
 *            保留小数位数
 * @param msgFlag
 *              是否显示提示信息
 * @returns
 */
common.checkInputNumber = function (reg, value, length, msgFlag) {
    if (msgFlag != false) {
        msgFlag = true;
    }
    var result;
    if (length == 0) {
        result = common.numberRegs["reg6"].exec(value) || [""];// 返回符合条件的数值
        if (!_.isEmpty(result[0])) {
            layer.msg("只能保留整数部分！");
        }
        return result[0];
    }
    result = common.numberRegs[reg].exec(value) || [""];// 返回符合条件的数值
    if (!_.isEmpty(length) && length >= 1) {
        if (result[0].toString().split(".").length > 1 && result[0].toString().split(".")[1].length > length) {
            if (msgFlag) {
                layer.msg("小数点后只能保留" + length + "位！");
            }
            result[0] = result[0].substring(0, (result[0].indexOf(".") + (length + 1)));
        }
    }
    return result[0];
};

/**
 * 显示信息内容
 *
 * @param id
 */
common.showToolTipContent = function (id) {
    $("#" + id).tooltip("show");
};

// 第二部分：日期转换---------------------------------------------------------------------

/**
 * @summary: 日期转换，将日期对象转换为一定格式的字符串
 * @param ：
 *            {date} date 要转换的日期对象
 * @param ：
 *            {string} retV 要转换成的格式
 * @return： {string} 转换后的日期格式
 * @example: || var date = new Date(2008,8,8,12,12,30); | var retV =
 *           "yyyy-MM-dd" | alert(common.dateFormatToString(date,retV)); |
 */
common.dateFormatToString = function (date, retV) {
    // parse year
    if (retV.indexOf("yyyy") != -1) {
        retV = retV.replace(/yyyy/gi, date.getFullYear());
    }

    // parse month
    if (retV.indexOf("MM") != -1) {
        var m = date.getMonth() + 1;
        m = m < 10 ? "0" + m : m;
        retV = retV.replace(/MM/g, m);
    }

    // parse day
    if (retV.indexOf("dd") != -1) {
        var d = date.getDate();
        d = d < 10 ? "0" + d : d;
        retV = retV.replace(/dd/g, d);
    }

    // parse hour
    if (retV.indexOf('hh') != -1) {
        var h = date.getHours();
        if (h >= 12) {
            retV = retV + " pm";
            h = (h == 12) ? 12 : h - 12; // 如果当前时间是12:12,转换成12进制为12:12 pm
        } else {
            retV = retV + " am";
            h = (h == 0) ? 12 : h; // 如果当前时间是00:12,转换成12进制为12:12 am
        }
        h = h < 10 ? "0" + h : h;
        retV = retV.replace(/hh/g, h);
    } else if (retV.indexOf('HH') != -1) {
        var h = date.getHours();
        h = h < 10 ? "0" + h : h;
        retV = retV.replace(/HH/g, h);
    }

    // parse minute
    if (retV.indexOf("mm") != -1) {
        var mm = date.getMinutes();
        mm = mm < 10 ? "0" + mm : mm;
        retV = retV.replace(/mm/g, mm);
    }
    // parse second
    if (retV.indexOf("ss") != -1) {
        var s = date.getSeconds();
        s = s < 10 ? "0" + s : s;
        retV = retV.replace(/ss/g, s);
    }
    // week
    if (retV.indexOf("w") != -1) {
        retV = retV.replace(/w/g, "0");
    }
    return retV;
};

/**
 * @summary: 日期转换函数
 * @param ：
 *            {string|number} inValue 输入值
 * @param： {string} datePattern 日期格式，默认为"yyyy-MM-dd"
 * @param: {object|null} data 里面包含{dataType:"text",valueFormat:"yyyy-MM-dd"}
 * @return: {string} 转换后的日期格式
 * @example: | var value = "19810615"; | var datePattern = "yyyy-MM-dd" |
 *           alert(common.dateFormat(value,datePattern,{dataType:"string"})); |
 */
common.dateFormat = function (inValue, datePattern, data) {
    if (!inValue || inValue == "")
        return "";
    var date, retV = datePattern || "yyyy-MM-dd";
    !data && (data = {
        dataType: "date"
    });

    if (data["dataType"] == "string" || data["dataType"] == "text") {
        var valueFormat = data["valueFormat"] || retV;
        date = common.dateParse(inValue, valueFormat);
    } else {
        date = new Date(Number(inValue));
    }
    return common.dateFormatToString(date, retV);
};

/**
 * @summary: 日期转换，将一定格式的字符串转换为日期对象
 * @param: {string} str 要转换的日期字符串
 * @param: {string} format 字符串符合的格式
 * @return : {date} 转换后的日期对象
 * @example: | var value = "19810615"; | var datePattern = "yyyy-MM-dd" |
 *           alert(common.dateParse(value,datePattern));
 */
common.dateParse = function (str, format) {
    var now = new Date();
    if (str.indexOf('am') > -1) {
        format = format + ' am';
    }

    if (str.indexOf('pm') > -1) {
        format = format + ' pm';
    }

    if (str.length !== format.length) {
        return now;
    }
    var sub = function (s, f1) {
        var rtv = -1;
        var index = format.indexOf(f1);
        if (index != -1) {
            rtv = parseInt(s.substr(index, f1.length), 10);
        }
        return rtv;
    };
    var year = sub(str, "yyyy");
    (year == -1) && (year = now.getYear());
    var month = sub(str, "MM");
    (month == -1) && (month = now.getMonth() + 1);
    var date = sub(str, "dd");
    (date == -1) && (date = 1);// 如果没有dd，则日期选中的每月1号

    // 处理12小时和24小时制度
    var hour = -1;
    if (sub(str, 'hh') != -1) { // 如果是12进制
        hour = sub(str, 'hh');
        if (str.indexOf('pm') != -1) {
            // 12进制12:12 pm转换为24进制应该还是12:12
            // 1:12pm 应该是13:12
            hour = (hour == 12) ? 12 : hour + 12;

        } else if (str.indexOf('am')) {
            // 12进制12:12am转换为24进制应该是00:12
            hour = (hour == 12) ? 0 : hour;
        }
    } else if (sub(str, 'HH') != -1) {
        hour = sub(str, 'HH');
    }
    (hour == -1) && (hour = 0);
    var minute = sub(str, "mm");
    (minute == -1) && (minute = 0);
    var second = sub(str, "ss");
    (second == -1) && (second = 0);
    var d = new Date(year, month - 1, date, hour, minute, second);
    if (d == "NaN") {
        return now;
    }
    return d;
};

// 第三部分：加减乘除运算---------------------------------------------------------------------

/**
 * 精确计算加法
 *
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
common.accAdd = function (arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
};

/**
 * 精确计算减法
 *
 * @param arg1
 * @param arg2
 * @returns
 */
common.accSubtr = function (arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    // 动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
};

/**
 * 精确计算乘法
 */
common.accMul = function (arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    } catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    } catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
};

/**
 * 精确计算除法
 *
 * @param arg1
 * @param arg2
 * @returns {Number}
 */
common.accDiv = function (arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length;
    } catch (e) {
    }
    r1 = Number(arg1.toString().replace(".", ""));
    r2 = Number(arg2.toString().replace(".", ""));
    return (r1 / r2) * Math.pow(10, t2 - t1);
};

// 第四部分：获取区域信息---------------------------------------------------------------------

/**
 * 获取指定区域 参数 id 是选中省份或城市的value值 参数 city 和 area 分别是其<select>标签的Id名称
 */
common.getChinaArea = function (id, city, area) {
    if (id == 0) {
        if (!common.isEmpty(city)) {
            $("#" + city).html("<option value='0'>=请选择城市=</option>");// 清楚之前保留的数据
        }
        $("#" + area).html("<option value='0'>=请选择城区=</option>");// 清楚之前保留的数据
        return false;
    } else {
        common.chinaAreaList(id, null, null, city, area);
    }
    ;
};

/**
 * 初始化区域信息
 *
 * @param pId
 *            省份ID
 * @param cId
 *            城市ID
 * @param aId
 *            城区ID
 * @param province
 *            省份<select>标签的ID名称
 * @param city
 *            城市<select>标签的ID名称
 * @param area
 *            城区<select>标签的ID名称
 */
common.initChinaArea = function (pId, cId, aId, province, city, area) {
    var url = PublicConstant.basePath + "/getChinaArea.action";
    var params = "pId=0";
    ajax.post(url, params, dataType.json, function (data) {
        $.each(data.value, function (index, list) {
            $('<option value=' + list.id + '>' + list.name + '</option>').appendTo("#" + province);
        });
        if (common.isEmpty(pId)) {
            pId = '0';
        }
        $("#" + province).val(pId);
        common.chinaAreaList(pId, cId, aId, city, area);
    }, false, false);
};

/**
 * 获取并初始化指定区域信息 备注：由于js执行代码是以块执行，非按行执行，所以要联动下一级时注意防止出现城市能初始化成功但城区初始化失败的情况
 *
 * @param pId
 *            省份ID
 * @param cId
 *            城市ID
 * @param aId
 *            城区ID
 * @param city
 *            城市<select>标签的ID名称
 * @param area
 *            城区<select>标签的ID名称
 */
common.chinaAreaList = function (pId, cId, aId, city, area) {
    var url = PublicConstant.basePath + "/getChinaArea.action";
    var params = "pId=" + pId;
    ajax.post(url, params, dataType.json, function (data) {
        if (data.result) {
            if (!common.isEmpty(city)) {
                $("#" + city).html("<option value='0'>=请选择城市=</option>");// 清除之前保留的数据
            }
            $("#" + area).html("<option value='0'>=请选择城区=</option>");// 清除之前保留的数据
            for (var i = 0; i < data.value.length; i++) {// 循环加载城市或区域信息
                if (!common.isEmpty(city) && pId != 0) {
                    $('<option value=' + data.value[i].id + '>' + data.value[i].name + '</option>').appendTo("#" + city);
                    $("#" + city).val(cId);
                }
                if (common.isEmpty(city) && pId != 0) {
                    $('<option value=' + data.value[i].id + '>' + data.value[i].name + '</option>').appendTo("#" + area);
                    $("#" + area).val(cId);
                }
            }
            if (!common.isEmpty(aId)) {
                common.chinaAreaList(cId, aId, null, null, area);
            }
            ;
        }
        ;
    }, false, false);
};

// 第五部分：获取代码表信息---------------------------------------------------------------------

/**
 * 初始化代码表选项
 *
 * @param codeKind
 * @param codeValue
 * @param id
 * @param value
 */
common.initCodeSelect = function (codeKind, codeValue, id, selectedValue, title) {
    if (common.isEmpty(title)) {
        title = "==请选择==";
    }
    $("#" + id).html("<option value=''>" + title + "</option>");
    var url = URL.get("System.CODE_CACHE_GET");
    var params = "codeKind=" + codeKind + "&codeValue=" + codeValue;
    ajax.post(url, params, dataType.json, function (data) {
        if (!common.isEmpty(data.value)) {
            $(data.value).each(function (index, code) {
                $("#" + id).append("<option value=" + code.codeValue + ">" + code.codeName + '</option>');
            });
            if (!common.isEmpty(selectedValue)) {
                $("#" + id).val(selectedValue);
            }
        }
    }, false, false);
};

/**
 * 初始化代码表checkbox
 *
 * @param dataJson
 * @param id
 * @param name
 * @param cols
 */
common.initCodeCheckbox = function (codeKind, codeValue, id, name, cols) {
    if (common.isEmpty(cols)) {
        cols = 2;
    }
    // 清空
    $("#" + id).html("");
    var url = URL.get("System.CODE_CACHE_GET");
    var params = "codeKind=" + codeKind + "&codeValue=" + codeValue;
    ajax.post(url, params, dataType.json, function (data) {
        if (!common.isEmpty(data.value)) {
            $.each(data.value, function (index, codeInfo) {
                $("#" + id).append(
                    "<div class='col-xs-" + cols + "'>" + "    <label class='checkbox-inline'><input type='checkbox' name=" + name + " value=" + codeInfo.codeValue + ">"
                    + codeInfo.codeName + "</label>" + "</div>");
            });
        }
    }, false, false);
};

/**
 * 初始化代码表radio
 *
 * @param codeKind
 * @param codeValue
 * @param id
 * @param name
 * @param cols
 */
common.initCodeRadio = function (codeKind, codeValue, id, name, cols, checkedValue) {
    if (common.isEmpty(cols)) {
        cols = 2;
    }
    // 清空
    $("#" + id).html("");
    var url = URL.get("System.CODE_CACHE_GET");
    var params = "codeKind=" + codeKind + "&codeValue=" + codeValue;
    ajax.post(url, params, dataType.json, function (data) {
        if (!common.isEmpty(data.value)) {
            $.each(data.value, function (index, codeInfo) {
                if (checkedValue == codeInfo.codeValue) {
                    $("#" + id).append(
                        "<div class='col-xs-" + cols + "'>" + "    <label class='radio-inline'><input type='radio' name=" + name + " value=" + codeInfo.codeValue + " checked>"
                        + codeInfo.codeName + "</label>" + "</div>");
                } else {
                    $("#" + id).append(
                        "<div class='col-xs-" + cols + "'>" + "    <label class='radio-inline'><input type='radio' name=" + name + " value=" + codeInfo.codeValue + ">"
                        + codeInfo.codeName + "</label>" + "</div>");
                }
            });
        }
    }, false, false);
};

// 第六部分：初始化插件---------------------------------------------------------------------
/**
 * bootstrap可编辑表格
 *
 * @param option
 */
common.initEditTable = function (opt) {
    $('#' + opt.id).bootstrapTable({
        method: opt.method,
        idField: opt.idField,
        uniqueId: opt.uniqueId,
        editable: opt.editable,//开启编辑模式
        clickToSelect: opt.clickToSelect,
        columns: opt.columns,
    });
};

/**
 * 提示信息
 *
 * @param error
 * @param element
 */
common.showmassage = function (error, element) {
    var elem = $(element);
    // var container = $("#content");
    // if(elem.parents("div .modal").length > 0){
    // container = elem.parents("div .modal").first();
    // }
    var corners = ["left center", "right center"];
    var flipIt = elem.parents("span.right").length > 0;
    // 检查是否存在错误信息
    if (!error.is(":empty")) {
        elem.first().qtip({
            overwrite: false,
            content: error,
            position: {
                my: corners[flipIt ? 1 : 0],
                at: corners[flipIt ? 0 : 1],
                viewport: $(window),
                container: $("#content")
            },
            show: {
                event: false,
                ready: true
            },
            hide: false,
            style: {
                classes: "qtip-red" // 设置显示样式
            }
        }).qtip("option", "content.text", error);
    } else {
        // 删除错误信息
        elem.qtip("destroy");
    }
};

/**
 * 去除提示信息
 */
common.removeErrorMsg = function (form) {
    var inputs = $(':input', '#' + form).filter(".form-control");
    for (var i = 0; i < inputs.length; i++) {
        $(inputs[i]).parent().qtip("destroy");
    }
};

/**
 * 将页面中的必填选项输入框设置为红色
 *
 * @param form
 *            表单名称
 * @param validateOption
 *            验证操作名
 */
common.requiredHint = function (form, validateOption) {
    /** 判断validate中是否存在required属性的字段 */
    if (validateOption != null) {
        var myrules = validateOption.rules;
        if (myrules != null) {
            for (var item in myrules) {
                if (myrules[item].required) {
                    $(":input[name=" + item + "]", '#' + form).addClass("inputborder");
                }
            }
        }
    }
    /** 判断input属性中是否存在required属性 */
    var inputs = $(':input', '#' + form);
    if (inputs != null) {
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].required) {
                $("#" + inputs[i].id).addClass("inputborder");
            }
        }
    }
    // 删除垃圾提示信息
    $('.bs-example-modal').on({
        'hide.bs.modal': function () {
            common.removeErrorMsg(form);
        },
        'hidden.bs.modal': function () {
            $(".qtip").qtip("destroy");
        }
    });
    $("body").on("click", function () {
        var qtipLength = $(":focus").parents("form[id='" + form + "']").length;
        if (qtipLength == 0) {
            common.removeErrorMsg(form);
        }
    });
};

/**
 * 默认上传图片
 *
 * @param id
 * @param url
 * @param buttonText
 * @param fileType
 * @returns {String}
 */
common.uploadifive = function (buttonText, fn, id, url, fileType, uploadType) {
    if (common.isEmpty(id)) {
        id = "file_upload";
    }
    if (common.isEmpty(url)) {
        url = PublicConstant.basePath + "/upload.action?picType=" + uploadType;
    }
    if (common.isEmpty(buttonText)) {
        buttonText = "选择上传文件";
    }
    if (common.isEmpty(fileType)) {
        fileType = 'image|*';
    }
    $('#' + id).uploadifive({
        'removeCompleted': true,
        'auto': true,
        'method': 'post',
        'buttonText': buttonText,
        'buttonClass': 'myButton',
        'fileType': fileType,
        'multi': false,
        'queueSizeLimit': '1',
        'fileSizeLimit': '1MB',
        'uploadScript': url,
        'onFallback': function () {

        },
        'onProgress': function (file, e) {
            var percent = 0;
            if (e.lengthComputable) {
                percent = Math.round((e.loaded / e.total) * 100);
            }
            file.queueItem.find('.fileinfo').html(' - ' + percent + '%');
            file.queueItem.find('.progress-bar').css('width', percent + '%');
        },
        'onUploadComplete': function (file, data) {
            if ($.isFunction(fn)) {
                fn(data);
            }
            $("#" + id).uploadifive('clearQueue');
        },
        'onError': function (errorType) {
            if (errorType == "FILE_SIZE_LIMIT_EXCEEDED") {
                layer.alert("上传图片过大");
            } else {
                layer.alert("错误类型：" + errorType);
            }
            $("#" + id).uploadifive('cancel', $('.uploadifive-queue-item').first().data('file'));
            $("#" + id).uploadifive('clearQueue');
        }
    });
};

var keditor;
/**
 * 富文本编辑器
 *
 * @param id
 */
common.kindEditor = function (id) {
    KindEditor.ready(function (K) {
        keditor = K.create('#' + id, {
            cssPath: PublicConstant.basePath + '/common/plugins/kindeditor-4.1.11/plugins/code/prettify.css',
            uploadJson: PublicConstant.basePath + '/common/plugins/kindeditor-4.1.11/jsp/upload_json.jsp',
            fileManagerJson: PublicConstant.basePath + '/common/plugins/kindeditor-4.1.11/jsp/file_manager_json.jsp',
            allowFileManager: true,
            items: ['undo', 'redo', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent',
                'clearhtml', 'selectall', '|', 'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'strikethrough',
                'lineheight', 'removeformat', '|', 'image', 'table', 'flash', 'media', 'preview', 'link', 'unlink'],
            afterCreate: function () {
            },
            afterBlur: function () {
            }
        });
        prettyPrint();
    });
};
// // 初始化KindEditor
// common.kindEditor("null");

/**
 * 初始化日期插件
 *
 * @param format
 *            显示日期格式
 * @param language
 *            显示语言类型
 * @param startView
 *            显示视图
 * @param id
 *            显示时间的标签ID
 * @param initialDate
 *            初始化时间
 */
common.initDate = function (format, language, startView, id, initialDate, minView) {
    if (common.isEmpty(format)) {
        format = "yyyy-mm-dd";
    }
    if (common.isEmpty(language)) {
        language = "zh-CN";
    }
    if (common.isEmpty(startView)) {
        startView = 2;
    }
    if (common.isEmpty(id)) {
        id = ".form_date";
    }
    if (common.isEmpty(initialDate)) {
        initialDate = new Date();
    }
    if (common.isEmpty(minView)) {
        minView = 2;
    }
    // 日期插件格式定义
    $(id).datetimepicker({
        language: language,
        format: format,
        weekStart: 1,
        todayBtn: false,
        autoclose: 1,
        todayHighlight: 1,
        startView: startView,
        minView: minView,
        forceParse: 0,
        initialDate: initialDate,
        minuteStep: 1
    });
    $().ready(function () {
        $(id).datetimepicker().on('hide', function (ev) {
            $(this).blur();
        });
        $(id).keypress(function (event) {
            if (event.which == 8) {
                $(id).val("");
            }
        });
    });
};

/**
 * @summary: 日期控件显示
 *
 * @param: id,input控件ID
 */
common.dateShow = function (id) {
    $('#' + id).datetimepicker('show');
};

/**
 * Bootstrap-Modal--操作类型
 */
common.modalType = {
    show: "show.bs.modal",// 在调用 show 方法后触发
    shown: "shown.bs.modal",// 当模态框对用户可见时触发（将等待 CSS 过渡效果完成）
    hide: "hide.bs.modal",// 当调用 hide 实例方法时触发
    hidden: "hidden.bs.modal"// 当模态框完全对用户隐藏时触发
};

/**
 * Bootstrap-Modal--在调用 show 方法后触发
 */
common.modal = function (id, type, fun) {
    if (common.isEmpty(id)) {
        layer.alert("请先设置 id");
    }
    if (common.isEmpty(type)) {
        layer.alert("请设置具体类型，请查看common.modalType！");
    }
    $('#' + id).off(type).on(type, function () {
        if ($.isFunction(fun)) {
            fun();
        }
    });
    if (type.indexOf("show") > -1) {
        $('#' + id).modal("show");
    }
    if (type.indexOf("hid") > -1) {
        $('#' + id).modal("hide");
    }
};

/**
 * zTree树插件
 */
common.zTree = function (zTree, zTree_setting) {
    var curDragNodes = null;
    var defaultZTree = {
        filter: function (treeId, parentNode, responseData) {
            if (!responseData)
                return null;
            for (var i = 0, l = responseData.length; i < l; i++) {
                responseData[i].name = responseData[i].name.replace(/\.n/g, '.');
            }
            return responseData;
        },
        dropPrev: function (treeId, nodes, targetNode) {// 拖拽操作
            var pNode = targetNode.getParentNode();
            if (pNode && pNode.dropInner === false) {
                return false;
            } else {
                for (var i = 0, l = curDragNodes.length; i < l; i++) {
                    var curPNode = curDragNodes[i].getParentNode();
                    if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        },
        dropInner: function (treeId, nodes, targetNode) {
            if (targetNode && targetNode.dropInner === false) {
                return false;
            } else {
                for (var i = 0, l = curDragNodes.length; i < l; i++) {
                    if (!targetNode && curDragNodes[i].dropRoot === false) {
                        return false;
                    } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        },
        dropNext: function (treeId, nodes, targetNode) {
            var pNode = targetNode.getParentNode();
            if (pNode && pNode.dropInner === false) {
                return false;
            } else {
                for (var i = 0, l = curDragNodes.length; i < l; i++) {
                    var curPNode = curDragNodes[i].getParentNode();
                    if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        },
        beforeDrag: function (treeId, treeNodes) {
            for (var i = 0, l = treeNodes.length; i < l; i++) {
                if (treeNodes[i].drag === false) {
                    curDragNodes = null;
                    return false;
                } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
                    curDragNodes = null;
                    return false;
                }
            }
            curDragNodes = treeNodes;
            return true;
        },
        removeHoverDom: function (treeId, treeNode) {
            $("#addBtn_" + treeNode.tId).unbind().remove();
        },
        showRemoveBtn: function (treeId, treeNode) {
            return treeNode.level != 0;
        }
        // onDrop: function() {layer.alert("请先定义 onDrop 方法");},
        // beforeEditName: function() {layer.alert("请先定义 beforeEditName 方法");},
        // beforeClick: function() {layer.alert("请先定义 beforeClick 方法");},
        // beforeRemove: function() {layer.alert("请先定义 beforeRemove 方法");},
        // addHoverDom: function() {layer.alert("请先定义 addHoverDom 方法");},
        // onExpand: function() {layer.alert("请先定义 onExpand 方法");}
    };

    var options = $.extend(true, {}, defaultZTree, zTree);

    var defaultSetting = {
        flag: {
            removeFlag: false,
            editNameFlag: false
        },
        view: {
            addHoverDom: options.addHoverDom,
            removeHoverDom: options.removeHoverDom,
            selectedMulti: false
        },
        edit: {
            drag: {
                autoExpandTrigger: true,
                prev: options.dropPrev,
                inner: options.dropInner,
                next: options.dropNext
            },
            enable: true,
            editNameSelectAll: true,
            showRemoveBtn: options.showRemoveBtn,
            showRenameBtn: true,
            removeTitle: "删除",
            renameTitle: "编辑"
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeDrag: options.beforeDrag,
            beforeClick: options.beforeClick,
            beforeEditName: options.beforeEditName,
            beforeRemove: options.beforeRemove,
            onDrop: options.onDrop,
            onExpand: options.onExpand,
            onCollapse: options.onCollapse,
            onClick: options.onClick
        }
    };

    var setting = $.extend(true, {}, defaultSetting, zTree_setting);

    return setting;
};

/**
 * zTree树添加搜索框
 *
 * @param treeObj
 * @param zTree
 * @param divId
 */
common.ztreeSearch = function (treeObj, zTree, divId) {
    // 自定义扩展
    $("#" + divId).prepend(
        "<input type='text' class='form-control' style='width: 70%;' id='ztreeSeachInput' placeholder='请输入检索内容'>"
        + "<ul class='prompt' id='ztreeSeachData' hidden='hidden'></ul>");
    if (!$.isFunction(zTree.beforeClick)) {
        layer.alert("未定义点击事件，搜索框不可用！");
        return false;
    }
    // 定义方法
    var initZtreeSearchData = null;
    initZtreeSearchData = function () {
        $("#ztreeSeachData").empty();
        var keywords = $("#ztreeSeachInput").val();
        var nodes = treeObj.getNodesByParamFuzzy("name", keywords, null);
        if (_.isEmpty(keywords)) {
            $("#ztreeSeachData").hide("slow");
            zTree.beforeClick("zTree", treeObj.getNodes()[0]);
            treeObj.cancelSelectedNode();
            $("#ztreeSeachInput").focus();
            return;
        }
        if (nodes.length < 1) {
            $("#ztreeSeachData").hide("slow");
            return;
        }
        if (nodes.length == 1) {
            $("#ztreeSeachData").hide("slow");
            zTree.beforeClick("zTree", nodes[0]);
            return;
        }
        $(nodes).each(function (index, obj) {
            if (obj != null && obj.level >= 1) {
                $("#ztreeSeachData").append("<li><a name='ztree_search_a' value='" + index + "' href='#'>" + obj.name + "</a></li>");
            }
        });
        $("a[name='ztree_search_a']").off("click").on("click", function (obj) {
            $("#ztreeSeachInput").val(nodes[$(this).attr("value")].name);
            $("#ztreeSeachData").hide("slow");
            zTree.beforeClick("zTree", nodes[$(this).attr("value")]);
        });
        $("#ztreeSeachData").show("slow");
    };
    // 绑定事件
    $("#ztreeSeachInput").off("input").on("input", function () {
        initZtreeSearchData();
    });
};

/**
 * 多级菜单导航插件--递归初始化html
 *
 * @param ddlData
 * @returns {String}
 */
common.ddlRecursion = function (ddlData) {
    var recHtml = "";
    recHtml += "<li><a data-ddl='" + JSON.stringify(ddlData) + "'>" + ddlData.name + "</a>";
    if (!_.isEmpty(ddlData.childList)) {
        recHtml += "<ul>";
        $.each(ddlData.childList, function (i, child) {
            recHtml += common.ddlRecursion(child);
        });
        recHtml += "</ul>";
    }
    recHtml += "</li>";
    return recHtml;
};

/**
 * 多级菜单导航插件
 *
 * @param ddlData
 */
common.initDdlHtml = function (ddlData, divId, selectedName, fn, extendOption) {
    selectedName = selectedName || "===请选择===";
    $("#" + divId).html(
        "<div id='" + divId + "_ddtopmenubar' class='mattblackmenu'>" + "	<li><a rel='" + divId + "_ddsubmenu'><span id='show'>" + selectedName + "<span></a></li>" + "</div>"
        + "<ul id='" + divId + "_ddsubmenu' class='ddsubmenustyle'></ul>");

    var ddlHtml = (extendOption || "");
    $.each(ddlData, function (index, data) {
        ddlHtml += common.ddlRecursion(data);
    });
    $("#" + divId + "_ddsubmenu").append(ddlHtml);

    ddlevelsmenu.setup(divId + "_ddtopmenubar", "topbar", true);

    // 绑定点击事件
    $("#" + divId + "_ddsubmenu a").off("click").on("click", function () {
        if ($.isFunction(fn)) {
            var aData = $(this).data("ddl");
            if (fn(aData) != false) {
                $("#" + divId + " #show").html(aData.name);
            }
        }
    });
};

/**
 * 多级菜单导航插件--递归初始化html
 *
 * @param ddlData
 * @returns {String}
 *
 * @author lp
 */
common.dropdownRecursion = function (ddlData) {
    var recHtml = "";
    if (_.isEmpty(ddlData.childList)) {
        recHtml = "<li><a data-ddl='" + JSON.stringify(ddlData) + "'>" + ddlData.name + "</a>";

    } else {
        recHtml = "<li class='dropdown-submenu'><a data-ddl='" + JSON.stringify(ddlData) + "'>" + ddlData.name + "</a>"
            + "<ul class='dropdown-menu'>";
        $.each(ddlData.childList, function (i, child) {
            recHtml += common.dropdownRecursion(child);
        });
        recHtml += "</ul>";
    }
    recHtml += "</li>";
    return recHtml;
};

/**
 * 多级下拉导航 在页面建一个div，设置id和样式，像调用 common.initDdlHtml  一样调用(common.initDdlHtml换成common.initExpandedListView)，别的都不变，宽度随div宽度变化
 * @param ddlData
 * @param divId
 * @param selectedName
 * @param fn
 * @param extendOption
 *
 * @author lp
 */
common.initExpandedListView = function (ddlData, divId, selectedName, fn, extendOption) {
    selectedName = selectedName || "===请选择===";
    var width = "100%";
    if (!common.isEmpty($("#" + divId).offsetWidth)) {
        width = $("#" + divId).offsetWidth;
    }
    $("#" + divId).html(
        '<div class="dropdown">'
        + '<div id="' + divId + '_dropdownmenubar" type="button" class="btn btn-default dropdown-toggle" style="width: ' + width + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true"><span id="show">' + selectedName + '</span><span class="caret" style="margin-left: 5px"></span> </div>'
        + '<ul id="' + divId + '_dropdownmenu" style="width:100%" class="dropdown-menu"  aria-labelledby="' + divId + '_dropdownmenubar"></ul>'
        + '</div>'
    );
    var ddlHtml = (extendOption || "");
    $.each(ddlData, function (index, data) {
        ddlHtml += common.dropdownRecursion(data);
        // if(index < ddlData.length - 1){
        //     ddlHtml += '<li class="divider" style="margin: 5px"></li>';
        // }
    });
    $("#" + divId + "_dropdownmenu").append(ddlHtml);

    // 绑定点击事件
    $("#" + divId + "_dropdownmenu a").off("click").on("click", function () {
        if ($.isFunction(fn)) {
            var aData = $(this).data("ddl");
            if (fn(aData) != false) {
                $("#" + divId + " #show").html(aData.name);
            }
        }
    });
};

/**
 * 自动补全搜索
 * @param id
 * @param listData
 * @param fn
 */
common.autocompleteMethod = function (id, listData, fn) {
    var width = $("#" + id).width();
    if (width < 170) {
        width = 170;
    }
    $("#" + id).off().autocomplete(listData, {
        width: width,
        //max: 99,//扩展最大搜索数
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
        },
        customSort: function (d1, d2) {
            if (d1.data.name && d2.data.name) {
                return d1.data.name.length - d2.data.name.length;
            }
        }
    }).result(function (event, data, formatted) {
        if ($.isFunction(fn)) {
            fn(data);
        }
    });
}

/**
 * websocket心跳包
 * @param ws
 * @param last_health
 * @param health_timeout
 */
common.keepalive = function (ws, last_health) {
    var time = new Date();
    if (last_health != -1 && (time.getTime() - last_health > 10)) {

        // ws.close();
    } else {
        if (ws.bufferedAmount == 0) {
            ws.send('@heart');
        }
    }
}

/**
 * websocket初始化
 * @param fn
 */
common.initWs = function (fn) {
    var websocket = '';
    var last_health;

    if ($('websocket').attr('userName') != '' && $('websocket').attr('ws') == 'yes') {
        var userName = $('websocket').attr('userName');
        if (window.WebSocket) {
            websocket = new WebSocket(encodeURI('ws://' + document.domain + ':' + PublicConstant.websocketPort));
            websocket.onopen = function () {
                websocket.send("online" + userName);
                heartbeat_timer = setInterval(function () {
                    common.keepalive(websocket, last_health);
                }, 60000);
            };
            websocket.onerror = function () {
                console.log('websocket连接发生错误！');
            };
            websocket.onclose = function () {
                console.log('websocket已经断开连接！');
                common.initWs();
            };
            // 消息接收
            websocket.onmessage = function (message) {
                if ($.isFunction(fn)) {
                    fn(message);
                }
            };
        } else {
            layer.alert("该浏览器不支持提醒<br/>建议使用高版本的火狐浏览器");
        }
    }
}

// 第七部分：定义公共业务工具方法---------------------------------------------------------------------

/**
 * 计算日期的间隔天数（参数均为日期格式字符串）
 *
 * @param startDate
 *            格式:"yyyy-MM-dd"
 * @param endDate
 *            格式:"yyyy-MM-dd"
 * @return
 */
common.getDateDiff = function (startDate, endDate) {
    var startTime = new Date(startDate).getTime();
    var endTime = new Date(endDate).getTime();
    var dates = (endTime - startTime) / (1000 * 60 * 60 * 24);
    return Math.floor(dates);
};

/**
 * 可输入可选择框的初始化数据方法
 *
 * @param id
 *            输入框对应的datalist标签的id
 * @param tableName
 *            查询数据的表名
 * @param fieldName
 *            查询数据的字段名
 */
common.initSelectAndInput = function (id, tableName, fieldName) {
    $("#" + id).empty();
    var url = URL.get("System.QUERY_DISTINCT_STRING");
    var params = "tableName=" + tableName + "&fieldName=" + fieldName;
    ajax.post(url, params, dataType.json, function (data) {
        if (!common.isEmpty(data.data)) {
            $.each(data.data, function (index, value) {
                $("#" + id).append("<option value=" + value + ">" + value + "</option>");
            });
        }
    }, false, false);
};

/**
 * 根据身份证获取出生日期
 * @param idCard
 * @returns {String}
 */
common.getPersonalByIdCard = function (idCard, type) {
    if (type == "birthday") {
        var birthday = "";
        idCard = common.trim(idCard.replace(/ /g, ""));
        if (idCard.length == 15) {
            var year = idCard.substring(6, 8);
            var month = idCard.substring(8, 10);
            var day = idCard.substring(10, 12);
            birthday = "19" + year + "-" + month + "-" + day;
        }
        if (idCard.length == 18) {
            var year = idCard.substring(6, 10);
            var month = idCard.substring(10, 12);
            var day = idCard.substring(12, 14);
            birthday = year + "-" + month + "-" + day;
        }
        return birthday;
    }
    if (type == "sex") {
        var sex = idCard.charAt(16) % 2 == 0 ? "female" : "male";
        return sex;
    }
}

// 第八部分：拓展jquery---------------------------------------------------------------------

/**
 * 将表单序列化为json（1）
 */
$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = this.serializeArray();
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                serializeObj[this.name].push(this.value);
            } else {
                serializeObj[this.name] = [serializeObj[this.name], this.value];
            }
        } else {
            serializeObj[this.name] = this.value;
        }
    });
    return serializeObj;
};

/**
 * 将表单序列化为json（2）
 */
$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * JSON型数据绑定至表单
 */
$.fn.jsonToForm = function (jsonData) {
    var formSelector = this.selector;
    // 赋值基本数据
    $.each(jsonData, function (key, value) {
        if (!common.isEmpty(key) && !common.isEmpty(value)) {
            var elementTag = $('#' + key).prop("nodeName") || $($('[name=' + key + ']')[0]).prop("nodeName");
            var elementType = $('#' + key).attr("type") || $($('[name=' + key + ']')[0]).attr("type");
            if (common.isEmpty(elementTag)) {
                return;
            }
            elementTag = elementTag.toLowerCase();
            if (elementTag == 'select' || elementTag == 'textarea') {
                $(formSelector).find("[id='" + key + "']").val(value);
                return;
            }
            if (elementTag == 'input') {
                elementType = elementType.toLowerCase();
                if (elementType == 'text' || elementType == 'hidden' || elementType == 'password') {
                    $(formSelector).find("[id='" + key + "']").val(value);
                } else if (elementType == 'radio') {
                    $(formSelector).find("input:radio[name='" + key + "'][value='" + value + "']").attr("checked", true);
                }
            }
        }
    });
};

/**
 * 绑定文本框只允许输入数值
 */
$.fn.bindNumber = function () {
    $(this).each(function () {
        // 回车事件
        var elementTag = $(this).prop("nodeName").toLowerCase();
        var elementType = $(this).attr("type").toLowerCase();
        if (elementTag == 'input' && elementType == 'text') {
            $(this).keyup(function () {
                // 如果输入非数字，则替换为''
                this.value = this.value.replace(/[^\d]/g, '');
            });
        }
    });
};

/**
 * 绑定文本框只允许输入数值（非负整数和正浮点数）
 */
$.fn.bindNumberOrFloat = function () {
    $(this).each(function () {
        // 回车事件
        var elementTag = $(this).prop("nodeName").toLowerCase();
        var elementType = $(this).attr("type").toLowerCase();
        if (elementTag == 'input' && elementType == 'text') {
            $(this).change(function () {
                var result = /^[1-9]\d*\.\d*|0\.\d*[1-9]\d*$/.exec(this.value);// 正浮点数
                if (common.isEmpty(result)) {
                    result = /^[1-9]\d*|0$/.exec(this.value);// 非负整数
                }
                this.value = result;
            });
        }
    });
};

/**
 * 获取当前地址栏URL参数
 *
 * $.getUrlVars(); 获取所有参数 $.getUrlVar(name); 根据name获取指定参数
 *
 */
$.extend({
    getUrlVars: function () {
        var vars = [], hash;
        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
        for (var i = 0; i < hashes.length; i++) {
            hash = hashes[i].split('=');
            vars.push(hash[0]);
            vars[hash[0]] = hash[1];
        }
        return vars;
    },
    getUrlVar: function (name) {
        return $.getUrlVars()[name];
    }
});

var hint = {
    alert: function (msg) {
        layer.alert(msg);
    },
    //提示消息（非弹出框）
    message: function (msg) {
        layer.msg(msg);
        //layer.alert(msg,{icon: 6});
    },
    //提示成功消息(以后改成弹出并点击确定形式)
    success: function (msg) {
        layer.msg(msg);
        //layer.alert(msg,{icon: 6});
    },
    //提示失败消息
    fail: function (msg) {
        layer.alert(msg, {icon: 5});
    },
    //提示警告消息
    warning: function (msg) {
        layer.alert(msg, {icon: 0});
    },
    //确认询问
    confirm: function (msg, yfun) {
        layer.confirm(msg, {icon: 3, title: '提示'}, yfun);
    }

};


/**
 * 选择项目后在输入框后边显示已选择的项目（如诊断）
 */
var selectOperation = {
    showID: "", //项目列表显示区域 ID
    hiddenID: "", //隐藏的存放选中对象id的 区域的ID
    selectedObjArray: [], //选中的对象数组，如{key:"",value:"}
    init: function (showID, hiddenID, selectedObjArray) {
        // 清除缓存内容
        $("#" + showID + " div[id*='" + hiddenID + "']").remove();
        if (selectedObjArray != null) {
            for (i = 0; i < selectedObjArray.length; i++) {
                $("#" + showID).append(selectOperation.template(selectedObjArray[i].key, selectedObjArray[i].value, showID, hiddenID));
            }
        }
    },
    addSelectOperation: function (key, value, showID, hiddenID, extendHtml) {
        var hiddenValue = $("#" + hiddenID).val();
        if (hiddenValue.indexOf(key) >= 0) {
            return;
        }
        $("#" + showID).append(selectOperation.template(key, value, showID, hiddenID));
        if (!common.isEmpty(hiddenValue)) {
            hiddenValue += ","
        }
        $("#" + hiddenID).val(hiddenValue + key).change();

        if ($("#" + hiddenID + "Names").length > 0) {
            var hiddenName = $("#" + hiddenID + "Names").val();
            if (!common.isEmpty(hiddenName)) {
                hiddenName += ","
            }
            $("#" + hiddenID + "Names").val(hiddenName + value).change();
        }

        if (!common.isEmpty(extendHtml)) {
            $("#" + showID).append(extendHtml);
        }
    },
    removeSelectOperation: function (id, value, hiddenID) {
        $("#" + hiddenID + "_" + id).remove();
        var hiddenValue = $("#" + hiddenID).val();
        hiddenValue = hiddenValue.replace(',' + id, '').replace(id + ',', '').replace(id, '');
        $("#" + hiddenID).val(hiddenValue).change();
        if ($("#" + hiddenID + "Names").length > 0) {
            var hiddenName = $("#" + hiddenID + "Names").val();
            var value1 = "占位符";//解决字符串中有两个相同的字符串，只替换一个
            hiddenName = hiddenName.replace(value, value1).replace(',' + value1, '').replace(value1 + ',', '').replace(value1, '');
            $("#" + hiddenID + "Names").val(hiddenName).change();
        }
    },
    template(key, value, showID, hiddenID) {
        var vName = value;
        value = "<a title='" + value + "' style='text-decoration:none'>" + value + "</a> ";
        var style = "style='height:34px;padding-top:4px;padding-bottom:4px;'";
        var element = $("#" + showID).find("input").not("[type=hidden]")[0];
        if (!common.isEmpty(element)) {
            style = "style='height:" + element.offsetHeight + "px;padding-top:4px;padding-bottom:4px;'";
        }
        var template = "<div id='" + hiddenID + "_" + key + "' class='form-control' " + style + ">" + value
            + "<a onclick='selectOperation.removeSelectOperation(\"" + key + "\",\"" + vName + "\",\"" + hiddenID + "\")'><i class='fa fa-remove fa-fw'></i></a> "
            + "</div>";
        return template;
    }
};
