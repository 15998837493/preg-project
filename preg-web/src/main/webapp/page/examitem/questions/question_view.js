/*
 * 注意：目前问卷只支持二级子问题
 */
//问卷对象
question = {};

//快速营养问卷
array_nutritious = [];

//问卷相关访问路径
question.url = {
    submit: URL.get("Question.SAVE_QUESTION")
};

//问卷答案对象
question.answer = {
    problemId: null,
    //问题编号
    proptid: null,
    //问题选项编号
    answerContent: null //答案内容
};

//问卷相关数据
question.data = {
    allProblem: null,
    //全部问题
    parentProblem: null,
    //只包含父问题
    childrenProblem: null,
    //只包含子问题
    answer: new Array() //保存用户的答案选项
};

//问卷相关参数
question.param = {
    optionDefault: 1,
    //对应是否是数据库中的是否是默认选项1=是，0=否
    optionWrite: 1,
    //选项后是否可填写信息，例如其他选项后可添加描述  1=是，0=否
    iswrite: "w",
    //选项后面的输入框和选项用的是相同的id编号，为了区分，在选项后面的输入框ID的属性值后面加“w”标识用于区分
    parentlevel: 0,
    //父问题节点等级
    childrenlevel: 1,
    //子问题节点等级
    grandson: 2,
    //二级子问题节点等级
    currentpNum: 0,
    //当前问题编号
    currentcNum: 0,
    //当前问题子问题编号
    currentgNum: 0,
    //当前问题二级子问题编号
    sumpNum: 0,
    //当前问题编号
    currentProblem: null,
    //当前问题
    sanswered: null,
    //当前问题上次答案组
    cpcpg: null,
    //当前问题的子问题组，因为当前问题可能存在多个子问题
    cpgpg: null,
    //当前问题的二级子问题组，因为当前问题可能存在多个子问题
    sanswer: null,
    //当前问题的答案组，因为当前问题可能存在多个答案（多选）
    canswer: null,
    //与当前问题相关联的答案信息
    cbrownum: 3,
    //checkbox多选题每行显示个数，默认3个
    text: '3',
    //简答题
    boolean: '4',
    //是否题
    checkbox: '2',
    //多选题	
    radio: '1',
    //单选题	
    flag: 0,
    // 0 表示父问题 1 表示一级子问题 2 表示二级子问题
    isreply: true,
    //用户是否回答当前问题（只针对必答题）
    problemRequired: 1,
    //用户是否回答当前问题（只针对必答题）
    hasChildren: 1,
    //有子问题	
    any: 'any',
    //选中任何选项都显示子问题
    parent: 1,
    //显示父问题标识
    children: 1 //显示子问题标识
};

//问卷初始化
question.init = function() {
    var allProblem = question.data.allProblem;
    var parentProblems = new Array();
    var childrenProblems = new Array();
    for (var i = 0; i < allProblem.length; i++) {
        if (allProblem[i].problemLevel == question.param.parentlevel) {
            parentProblems.push(allProblem[i]);
        } else {
            var flag = true;
            for (var j = 0; j < childrenProblems.length; j++) {
                if (allProblem[i].problemParentId == childrenProblems[j].problemParentId) {
                    childrenProblems[j].content.push(allProblem[i]);
                    flag = false;
                }
            }
            if (flag) {
                var childrenProblem = new Object();
                childrenProblem.problemParentId = allProblem[i].problemParentId;
                childrenProblem.content = new Array();
                childrenProblem.content.push(allProblem[i]);
                childrenProblems.push(childrenProblem);
            }
        }
    }
    question.data.parentProblem = parentProblems;
    question.data.childrenProblem = childrenProblems;
    question.param.sumpNum = question.data.parentProblem.length;
};

//问卷父问题展示
question.showParent = function(isLast) {
    question.param.currentProblem = question.data.parentProblem[question.param.currentpNum];
    question.showAnwer(question.param.currentProblem.problemId);
    //除当前问题以外的问题变为不可操作状态
    $("input[ptype='radio']").attr("disabled", true);
    $("input[ptype='checkbox']").attr("disabled", true);
    $("input[ptype='boolean']").attr("disabled", true);
    $("input[ptype='text']").attr("disabled", true);
    //alert(JSON.stringify(question.param.currentProblem));
    var modelid = 'p' + question.param.currentProblem.problemType;
    var gettpl = document.getElementById(modelid).innerHTML;
    slideUpDown(gettpl, question.param.currentProblem, isLast);
};

function setDH() {
    var height = $("#problem_div").height();
    var $uppro = $("#uppro");
    var $downpro = $("#downpro");
    $uppro.css("height", "auto");
    $downpro.css("height", "auto");
    $uppro.height(height);
    $downpro.height(height);
    restBodyHeight();
}

function setNutritious() {
    //--------------------B3-----------------------
    if ($("#11000022018010900025").is(":checked")) { // 无此类不良日常膳食摄入问题 
        $("input[name='402880f460d8bac30160d8c6e1150002']").attr("disabled", "disabled");
        $(this).attr("disabled", false);
    } else {
    	if($.inArray("A00001",array_nutritious) > -1) {// 如果没有上一题特殊操作
    		for (var x = 0; x < 11; x++) {
    			if(x!=5) {
    				$("input[name='402880f460d8bac30160d8c6e1150002']:eq("+x+")").attr("disabled", false);
    			}
    		}
    	}else {
    		$("input[name='402880f460d8bac30160d8c6e1150002']").attr("disabled", false);
    	}        
    }
    if ($("input[name='402880f460d8bac30160d8c6e1150002']:not(:last)").is(":checked")) {
        $("#11000022018010900025").attr("disabled", "disabled");
        if($.inArray("A00001",array_nutritious) > -1) {// 如果第二题选了“肉食；偏肉食”则不能选择“很少摄入鱼肉蛋”(首操作默认恢复disabled，所以要做处理)
        	$("#11000022018010900019").attr("disabled", true);
        }
        if ($("#11000022018010900014").is(":checked")) { // 很少摄入主食
            $("#11000022018010900024").attr("disabled", "disabled");
        } else {
            $("#11000022018010900024").attr("disabled", false);
        }
        if ($("#11000022018010900024").is(":checked")) { // 平均每天主食摄入量大于300g（熟重约等于750g米饭） 
            $("#11000022018010900014").attr("disabled", "disabled");
        } else {
            $("#11000022018010900014").attr("disabled", false);
        }
        if ($("#11000022018010900016").is(":checked")) { // 很少摄入水果 
            $("#11000022018010900023").attr("disabled", "disabled");
        } else {
            $("#11000022018010900023").attr("disabled", false);
        }
        if ($("#11000022018010900023").is(":checked")) { // 平均每天水果摄入量大于500g 
            $("#11000022018010900016").attr("disabled", "disabled");
        } else {
            $("#11000022018010900016").attr("disabled", false);
        }
        if ($("#11000022018010900018").is(":checked")) { // 很少摄入坚果 
            $("#11000022018010900021").attr("disabled", "disabled");
        } else {
            $("#11000022018010900021").attr("disabled", false);
        }
        if ($("#11000022018010900021").is(":checked")) { // 平均每天坚果摄入量大于20g 
            $("#11000022018010900018").attr("disabled", "disabled");
        } else {
            $("#11000022018010900018").attr("disabled", false);
        }
        if ($("#11000022018010900019").is(":checked")) { // 很少摄入鱼肉蛋 
            $("#11000022018010900022").attr("disabled", "disabled");
        } else {
            $("#11000022018010900022").attr("disabled", false);
        }
        if($.inArray("A00001",array_nutritious) < 0) {// 如果第二题选了“肉食；偏肉食”则不能选择“很少摄入鱼肉蛋”
            if ($("#11000022018010900022").is(":checked")) { // 平均每天鱼肉蛋摄入量大于250g 
                $("#11000022018010900019").attr("disabled", "disabled");
            } else {
                $("#11000022018010900019").attr("disabled", false);
            }
        }
    } else {
        if($.inArray("A00001",array_nutritious) > -1) {// 如果第二题选了“肉食；偏肉食”则不能选择“很少摄入鱼肉蛋”(首操作默认恢复disabled，所以要做处理)
        	$("#11000022018010900019").attr("disabled", true);
        }
        var flag = 0;
        for (var x = 0; x < 11; x++) {
            if ($("input[name='402880f460d8bac30160d8c6e1150002']:eq(" + x + ")").is(":checked")) {
                flag = 1;
            }
        }
        if (flag == 0) {
            $("#11000022018010900025").attr("disabled", false);
        }
    }
    //--------------------B4-----------------------
    if ($("#11000022018010900034").is(":checked")) { // 无
        $("input[name='402880f460d8bac30160d8d42b940003']").attr("disabled", "disabled");
        $(this).attr("disabled", false);
    } else {
    	if($.inArray("A00002",array_nutritious) > -1) {
    		for (var x = 0; x < 8; x++) {
    			if(x!=1) {
    				$("input[name='402880f460d8bac30160d8d42b940003']:eq("+x+")").attr("disabled", false);
    			}
    		}
    	}else {
    		$("input[name='402880f460d8bac30160d8d42b940003']").attr("disabled", false);
    	}        
    }
    if ($("input[name='402880f460d8bac30160d8d42b940003']:not(:last)").is(":checked")) {
        $("#11000022018010900034").attr("disabled", "disabled");
        if($.inArray("A00002",array_nutritious) > -1) {// 若选择问题B2选择“全素食；蛋奶素”则不能选择“加工类肉食品（肉干、肉松、香肠等）”(首操作默认恢复disabled，所以要做处理)
        	$("#11000022018010900027").attr("disabled", true);
        }
    } else {
        if($.inArray("A00002",array_nutritious) > -1) {// 若选择问题B2选择“全素食；蛋奶素”则不能选择“加工类肉食品（肉干、肉松、香肠等）”(首操作默认恢复disabled，所以要做处理)
        	$("#11000022018010900027").attr("disabled", true);
        }
        var flag = 0;
        for (var x = 0; x < 8; x++) {
            if ($("input[name='402880f460d8bac30160d8d42b940003']:eq(" + x + ")").is(":checked")) {
                flag = 1;
            }
        }
        if (flag == 0) {
            $("#11000022018010900034").attr("disabled", false);
        }
    }
    //--------------------B5-----------------------
    if ($("#11000022018010900040").is(":checked")) { // 无此类不良用餐习惯 
        $("input[name='402880f460d8bac30160d8d59f130004']").not("input:checkbox[id='11000022018010900040']").attr("disabled", "disabled");
        $(this).attr("disabled", false);
    } else {
        $("input[name='402880f460d8bac30160d8d59f130004']").attr("disabled", false);
    }
    if ($("input[name='402880f460d8bac30160d8d59f130004']:not(:last)").is(":checked")) {
        $("#11000022018010900040").attr("disabled", "disabled");
    } else {
        var flag = 0;
        for (var x = 0; x < 8; x++) {
            if ($("input[name='402880f460d8bac30160d8d59f130004']:eq(" + x + ")").is(":checked")) {
                flag = 1;
            }
        }
        if (flag == 0) {
            $("#11000022018010900040").attr("disabled", false);
        }
    }
    //--------------------B8-----------------------
    if ($("#11000022018020100011").is(":checked")) { // 无
        $("input[name='402880f460d8bac30160d8d859d30007']").not("#11000022018020100011").attr("disabled", "disabled");
    }else {
    	$("input[name='402880f460d8bac30160d8d859d30007']").attr("disabled", false);
    }
    if ($("input[name='402880f460d8bac30160d8d859d30007']:not(:last)").is(":checked")) {
        $("#11000022018020100011").attr("disabled", "disabled");
    } else {
        var flag = 0;
        for (var x = 0; x < 6; x++) {
            if ($("input[name='402880f460d8bac30160d8d859d30007']:eq(" + x + ")").is(":checked")) {
                flag = 1;
            }
        }
        if (flag == 0) {
            $("#11000022018020100011").attr("disabled", false);
        }
    }
}

function restBodyHeight() {
    $("body").css("height", "auto");
    H = $(window).height() - 30;
    if ($("body").height() < H) {
        $("body").height(H);
    }
}

function slideUpDown(gettpl, currentProblem, isLast) {
    if (isLast != true) {
        $("#parent_problem_div").slideUp("fast",
        function() {
            laytpl(gettpl).render(currentProblem,
            function(html) {
                document.getElementById('parent_problem_div').innerHTML = html;
                setDH();
            });
        });
        $("#parent_problem_div").slideDown();
    } else {
        laytpl(gettpl).render(currentProblem,
        function(html) {
            document.getElementById('parent_problem_div').innerHTML = html;
            setDH();
        });
    }
}

//问卷子问题展示
question.showChildren = function() {
    question.param.currentProblem = question.param.cpcpg[question.param.currentcNum];
    question.showAnwer(question.param.currentProblem.problemId);
    //除当前问题以外的问题变为不可操作状态
    $("input[ptype='radio']").attr("disabled", true);
    $("input[ptype='checkbox']").attr("disabled", true);
    $("input[ptype='boolean']").attr("disabled", true);
    $("input[ptype='text']").attr("disabled", true);
    var modelid = 'p' + question.param.currentProblem.problemType;
    var gettpl = document.getElementById(modelid).innerHTML;
    var cpdiv = document.createElement("div");
    var parentNode = document.getElementById('parent_problem_div');
    var clockdiv = document.createElement("div");
    clockdiv.setAttribute("id", (question.param.currentpNum + 1) + "-" + (question.param.currentcNum + 1));
    clockdiv.appendChild(cpdiv);
    parentNode.appendChild(clockdiv);

    laytpl(gettpl).render(question.param.currentProblem,
    function(html) {
        cpdiv.innerHTML = html;
        setDH();
    });
};

//问卷二级子问题展示
question.showGrandson = function() {
    question.param.currentProblem = question.param.cpgpg[question.param.currentgNum];
    question.showAnwer(question.param.currentProblem.problemId);
    //除当前问题以外的问题变为不可操作状态
    $("input[ptype='radio']").attr("disabled", true);
    $("input[ptype='checkbox']").attr("disabled", true);
    $("input[ptype='boolean']").attr("disabled", true);
    $("input[ptype='text']").attr("disabled", true);
    var modelid = 'p' + question.param.currentProblem.problemType;
    var gettpl = document.getElementById(modelid).innerHTML;
    var cpdiv = document.createElement("div");
    //	var linediv = document.createElement("div");
    //	linediv.setAttribute("class","col-xs-12");
    //	var line = document.createElement("hr");
    //	line.setAttribute("style","height:2px;border:none;border-top:2px double #3285e1;");
    //	linediv.appendChild(line);
    var parentNode = document.getElementById('parent_problem_div');
    var clockdiv = document.createElement("div");
    clockdiv.setAttribute("id", (question.param.currentpNum + 1) + "-" + (question.param.currentcNum) + "-" + (question.param.currentgNum + 1));
    //	clockdiv.appendChild(linediv);
    clockdiv.appendChild(cpdiv);
    parentNode.appendChild(clockdiv);

    laytpl(gettpl).render(question.param.currentProblem,
    function(html) {
        cpdiv.innerHTML = html;
        setDH();
    });
};

$().ready(function() {
    $("#initForm").attr("action", URL.get("Question.INIT_QUESTION"));

    $("#initForm").ajaxPost(dataType.json, function(data) {
        question.data.allProblem = data.value.problemVos;
        question.init();
        question.showParent();
    });
    /******************************* 以下代码只针对孕期生活方式问卷：如果选择了指定项，则其他项不可选 ******************************************/
    //您在孕期常用的烹饪习惯有（可多选）（最多可选3项）
    $("#11000022018011200136").live("click", function(e) {
        if ($("#11000022018011200136").attr("checked")) {
            $.each(question.param.currentProblem.optionVos,
            function(index, value) {
                if (value.problemOptionId != "11000022018011200136") {
                    $("#" + value.problemOptionId).attr("checked", false);
                    $("#" + value.problemOptionId).attr("disabled", true);
                }
            });
        } else {
            $("input[ptype='checkbox']").attr("disabled", false);
        }
    });
    //您的工作/生活场所经常会接触到哪些有害物质：
    $("#11000022018011200252").live("click", function(e) {
        if ($("#11000022018011200252").attr("checked")) {
            $.each(question.param.currentProblem.optionVos,
            function(index, value) {
                if (value.problemOptionId != "11000022018011200252") {
                    $("#" + value.problemOptionId).attr("checked", false);
                    $("#" + value.problemOptionId).attr("disabled", true);
                }
            });
        } else {
            $("input[ptype='checkbox']").attr("disabled", false);
        }
    });
    //您最近两周是否经常几种情绪（可多选）？
    $("#11000022018011200215").live("click",function(e) {
    	if ($("#11000022018011200215").attr("checked")) {
    		$.each(question.param.currentProblem.optionVos,
    				function(index, value) {
    			if (value.problemOptionId != "11000022018011200215") {
    				$("#" + value.problemOptionId).attr("checked", false);
    				$("#" + value.problemOptionId).attr("disabled", true);
    			}
    		});
    	} else {
    		$("input[ptype='checkbox']").attr("disabled", false);
    	}
    });
    /******************************* 以上代码只针对孕期生活方式问卷 ******************************************/

    // 下一题事件
    $('#downpro').click(function() {
        question.param.sanswered = new Array();
        var currentResult = new Array();
        question.param.flag = question.param.parentlevel;
        question.param.isreply = true;
        //当前答案选项是否改变 默认没有改变
        var ischange = false;
        //是否为最后一个
        var isLast = false;
        /****************************分割线：下面的代码只用于生活方式问卷****************************/
        //您在孕期常用的烹饪习惯有（可多选）（最多可选3项）
        if ("402880b360e936630160e95005d30041" === question.param.currentProblem.problemId) {
            var len = $("input[ptype='checkbox'][pid='" + question.param.currentProblem.problemId + "']:checked").length;
            if (len > 3) {
                layer.alert("本题最多选择3项");
                return;
            }
        }
        /****************************分割线：上面面的代码只用于生活方式问卷****************************/
        //判断当前问题是否为必答题
        if (question.param.currentProblem.problemRequired == question.param.problemRequired) {
            question.param.isreply = false;
        }

        //设置简答题答案
        if (question.param.currentProblem.problemType == question.param.text) {
            var textobj = $("input[ptype='text'][pid='" + question.param.currentProblem.problemId + "']");
            //用于计算必答题时，所有的空都填才能通
            var count = 0;

            for (var i = 0; i < textobj.length; i++) {
                if (!common.isEmpty(textobj[i].value)) {
                    question.answer = new Object();
                    question.answer.answerContent = textobj[i].value;
                    if (!new RegExp(question.param.currentProblem.optionVos[0].optionValidate).test(question.answer.answerContent)) {
                        layer.alert("输入数据不正确");
                        return;
                    }
                    question.answer.proptid = textobj[i].id;
                    question.answer.problemId = $(textobj[i]).attr("pid");

                    question.data.answer.push(question.answer);
                    count++;
                }
            }

            //判断必答题所有的空都已填写
            if (count == textobj.length) {
                question.param.isreply = true;
            }

            for (var j = 0; j < question.data.answer.length; j++) {
                if (question.answer.problemId == question.data.answer[j].problemId) {
                    question.param.sanswered.push(question.data.answer[j].answerContent);
                    //删除父问题答案信息
                    question.deletepAnwer();
                }
            }

        }

        //设置是否题答案
        if (question.param.currentProblem.problemType == question.param.boolean) {
            if (!common.isEmpty($("input[ptype='boolean'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("id")) && !common.isEmpty($("input[ptype='boolean'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("pid"))) {
                question.answer = new Object();
                question.answer.proptid = $("input[ptype='boolean'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("id");
                question.answer.problemId = $("input[ptype='boolean'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("pid");
                if ($("input[ptype='boolean'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("iswrite") == question.param.optionWrite) {
                    question.answer.answerContent = $("#" + question.answer.proptid + question.param.iswrite).val();
                }
                //alert(JSON.stringify(question.answer));
                for (var j = 0; j < question.data.answer.length; j++) {
                    if (question.answer.problemId == question.data.answer[j].problemId) {
                        question.param.sanswered.push(question.data.answer[j].proptid);
                        //删除父问题答案信息
                        question.deletepAnwer();
                    }
                }
                question.data.answer.push(question.answer);
                currentResult.push(question.answer.proptid);
                question.param.isreply = true;
            }
        }

        //设置多选题答案
        if (question.param.currentProblem.problemType == question.param.checkbox) {
            var cbanswer = new Array();
            var flag = false;
            var ckobj = $("input[ptype='checkbox'][pid='" + question.param.currentProblem.problemId + "']:checked");
            for (var i = 0; i < ckobj.length; i++) {
                question.answer = new Object();
                question.answer.proptid = ckobj[i].id;
                question.answer.problemId = $(ckobj[i]).attr("pid");
                if ($(ckobj[i]).attr("iswrite") == question.param.optionWrite) {
                    question.answer.answerContent = $("#" + question.answer.proptid + question.param.iswrite).val();
                }

                cbanswer.push(question.answer);
                currentResult.push(question.answer.proptid);
                question.param.isreply = true;
            }
            for (var j = 0; j < question.data.answer.length; j++) {
                if (question.param.currentProblem.problemId == question.data.answer[j].problemId) {
                    question.param.sanswered.push(question.data.answer[j].proptid);
                    flag = true;
                }
            }
            if (flag) {
                //删除父问题答案信息
                question.deletepAnwer();
            }
            for (var i = 0; i < cbanswer.length; i++) {
                question.data.answer.push(cbanswer[i]);
            }
        }

        //设置单选题答案
        if (question.param.currentProblem.problemType == question.param.radio) {
            if (!common.isEmpty($("input[ptype='radio'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("id")) && !common.isEmpty($("input[ptype='radio'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("pid"))) {
                question.answer = new Object();
                question.answer.proptid = $("input[ptype='radio'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("id");
                question.answer.problemId = $("input[ptype='radio'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("pid");
                if ($("input[ptype='radio'][pid='" + question.param.currentProblem.problemId + "']:checked").attr("iswrite") == question.param.optionWrite) {
                    question.answer.answerContent = $("#" + question.answer.proptid + question.param.iswrite).val();
                }
                for (var j = 0; j < question.data.answer.length; j++) {
                    if (question.answer.problemId == question.data.answer[j].problemId) {
                        question.param.sanswered.push(question.data.answer[j].proptid);
                        //删除父问题答案信息
                        question.deletepAnwer();
                    }
                }
                //alert(JSON.stringify(question.answer));
                question.data.answer.push(question.answer);
                currentResult.push(question.answer.proptid);
                question.param.isreply = true;
            }
        }

        if (!question.param.isreply) {
            layer.alert("当前问题为必答题");
            return;
        }
        //处理子问题
        if (question.param.currentProblem.problemIsChildren == question.param.hasChildren) {
        	currentChidGroup = new Array();
            for (var i = 0; i < question.data.childrenProblem.length; i++) {
                if (question.data.childrenProblem[i].problemParentId == question.param.currentProblem.problemId) {
                    //遍历当前问题的子问题集合
                    for (var j = 0; j < question.data.childrenProblem[i].content.length; j++) {
                        if (question.data.childrenProblem[i].content[j].problemRule == question.param.any) {
                            currentChidGroup.push(question.data.childrenProblem[i].content[j]);
                            //设置子问题级别
                            question.param.flag = question.data.childrenProblem[i].content[j].problemLevel;
                        }
                        if (question.data.childrenProblem[i].content[j].problemRule.indexOf(",") != -1) {
                            var options = question.data.childrenProblem[i].content[j].problemRule.split(",");
                            for (var n = 0; n < options.length; n++) {
                                //当前答案中有规则选项
                                if ($.inArray(options[n], currentResult) != -1) {
                                    //判断子问题编号是否发生改变。未改变就无需删除子问题信息
                                    //当前的答案和上次答案不一致并且规则答案不处在上次的答案中，需要删除子问题信息（主要处理选择不同的选项可能有不同的子问题）
                                    if (currentResult.sort().toString() != question.param.sanswered.sort().toString() && question.param.sanswered.length > 0 && ($.inArray(options[n], question.param.sanswered) == -1)) {
                                        //删除对应的子答案信息
                                        ischange = true;
                                    }
                                    currentChidGroup.push(question.data.childrenProblem[i].content[j]);
                                    //设置子问题级别
                                    question.param.flag = question.data.childrenProblem[i].content[j].problemLevel;
                                } else {
                                    //当前答案中没有规则选项，而上次操作的答案中有规则选项答案
                                    if (question.param.sanswered.length > 0 && ($.inArray(options[n], question.param.sanswered) != -1)) {
                                        ischange = true;
                                    }
                                }
                            };
                        } else {
                            if ($.inArray(question.data.childrenProblem[i].content[j].problemRule, currentResult) != -1) {
                                //判断子问题编号是否发生改变。为改变就无需删除子问题信息
                                if (currentResult.sort().toString() != question.param.sanswered.sort().toString() && question.param.sanswered.length > 0 && ($.inArray(question.data.childrenProblem[i].content[j].problemRule, question.param.sanswered) == -1)) {
                                    //删除对应的子答案信息
                                    ischange = true;
                                }
                                currentChidGroup.push(question.data.childrenProblem[i].content[j]);
                                //设置子问题级别
                                question.param.flag = question.data.childrenProblem[i].content[j].problemLevel;
                            } else {
                                if (question.data.childrenProblem[i].content[j].problemRule != question.param.any) {
                                    if (question.param.sanswered.length > 0 && ($.inArray(question.data.childrenProblem[i].content[j].problemRule, question.param.sanswered) != -1)) {
                                        ischange = true;
                                    }
                                }
                            }
                        };
                    }
                    //删除当前问题下的子问题的答案信息
                    if (ischange) {
                        for (var n = 0; n < question.data.childrenProblem[i].content.length; n++) {
                            question.deletecAnwer(question.data.childrenProblem[i].content[n].problemId);
                        }
                    }
                }
            }
            if (question.param.currentProblem.problemLevel == question.param.parentlevel) {
                question.param.cpcpg = currentChidGroup;
            }
            if (question.param.currentProblem.problemLevel == question.param.childrenlevel) {
                question.param.cpgpg = currentChidGroup;
            }
        }
        if (question.param.currentProblem.problemLevel == question.param.childrenlevel && !common.isEmpty(question.param.cpcpg[question.param.currentcNum])) {
            //下一问题是子问题
            question.param.flag = question.param.childrenlevel;
        }
        if (question.param.currentProblem.problemLevel == question.param.grandson && !common.isEmpty(question.param.cpgpg[question.param.currentgNum])) {
            //下一问题是二级子问题
            question.param.flag = question.param.grandson;
        }
        //显示第二级子问题
        if (question.param.flag == question.param.grandson) {
            question.showGrandson();
            question.param.currentgNum = ++question.param.currentgNum;
        }
        //显示第一级子问题
        if (question.param.flag == question.param.childrenlevel) {
            question.showChildren();
            question.param.currentcNum = ++question.param.currentcNum;
        }
        //显示第0级子问题
        if (question.param.flag == question.param.parentlevel) {
            question.param.currentcNum = 0;
            question.param.currentgNum = 0;
            question.param.currentpNum = ++question.param.currentpNum;
            console.log("当前问题数：" + question.param.currentpNum + ",问题总数：" + question.param.sumpNum);
            if (question.param.sumpNum == question.param.currentpNum) {
                if (question.data.answer.length == 0) {
                    layer.alert("已经到最后一题，您还没有答题，请先答题！！");
                    isLast = true;
                } else {
                    layer.confirm("确认提交问卷？",
                    function() {
                        question.ajaxsub();
                    });
                    isLast = true;
                }
                question.param.currentpNum = --question.param.currentpNum;
            }
            question.showParent(isLast);
        }
        /*************************以下代码只针对快速营养调查问卷*************************/
        // 若选择问题B2选择“肉食；偏肉食”则不能选择“很少摄入鱼肉蛋”
        if ($("#11000022018010900009").is(":checked") || $("#11000022018010900010").is(":checked")) { // 肉食 偏肉食
            setTimeout(function() {
            	if($.inArray("A00001",array_nutritious) < 0) {
            		array_nutritious.push("A00001");
            	}           	
                $("#11000022018010900019").attr("checked",false);
                $("#11000022018010900019").attr("disabled", true);
                // 相关联的两个问题，也需要处理：无此类不良日常膳食摄入问题  平均每天鱼肉蛋摄入量大于250g 
                if(!$("#11000022018010900025").is(":checked")) {
                	$("#11000022018010900022").attr("disabled",false);
                }
                var flag = 0;
                for (var x = 0; x < 11; x++) {
                    if ($("input[name='402880f460d8bac30160d8c6e1150002']:eq(" + x + ")").is(":checked")) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    $("#11000022018010900025").attr("disabled", false);
                }
                
            },
            400);
        }
        // 若选择问题B2选择“全素食；蛋奶素”则不能选择“加工类肉食品（肉干、肉松、香肠等）
        if($("#11000022018010900012").is(":checked") || $("#11000022018010900011").is(":checked")) {// 全素食 蛋奶素
        	if($.inArray("A00002",array_nutritious) < 0) {
        		array_nutritious.push("A00002");
        	}        	
        }
        if($("#11000022018010900012").length > 0) {// 第二题       	
        	if(!$("#11000022018010900009").is(":checked") && !$("#11000022018010900010").is(":checked")) {
        		var A00001_index = $.inArray("A00001",array_nutritious);
        		if(A00001_index > -1) {
        			array_nutritious.splice(A00001_index, 1);
        		}        		
        	}
        	if(!$("#11000022018010900012").is(":checked") && !$("#11000022018010900011").is(":checked")) {
        		var A00002_index = $.inArray("A00002",array_nutritious);
        		if(A00002_index > -1) {
        			array_nutritious.splice(A00002_index, 1);
        		}        		
        	}
        }
        if($.inArray("A00002",array_nutritious) > -1) {
            setTimeout(function() {
                $("#11000022018010900027").attr("disabled",true);
                $("#11000022018010900027").attr("checked",false);
                var flag = 0;
                for (var x = 0; x < 8; x++) {
                    if ($("input[name='402880f460d8bac30160d8d42b940003']:eq(" + x + ")").is(":checked")) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    $("#11000022018010900034").attr("disabled", false);
                }
            },
            400);
        }
        setTimeout(function() {
            setNutritious();
        },
        300);
        /*************************以上代码只针对快速营养调查问卷*************************/
    });

    // 上一题事件
    $('#uppro').click(function() {
        var isLast = false;

        if (question.param.flag == question.param.parentlevel) {
            question.param.currentpNum = --question.param.currentpNum;
            //alert("父问题=="+question.param.currentpNum+" 一级子问题="+question.param.currentcNum+" 二级子问题="+question.param.currentgNum);
            if (question.param.currentpNum < 0) {
                if (question.param.currentcNum == 0 && question.param.currentgNum == 0) {
                    layer.alert("当前已是第一题了");
                    isLast = true;
                }
                question.param.currentpNum = 0;
            }
            question.param.currentcNum = 0;
            question.param.currentgNum = 0;
            question.showParent(isLast);
        }

        if (question.param.flag == question.param.childrenlevel) {
            //删除当前问题
            $("#" + (question.param.currentpNum + 1) + "-" + question.param.currentcNum).remove();
            question.param.currentcNum = --question.param.currentcNum;
            question.param.currentgNum = 0;
            //使上一题后的当前问题变为可操作，disabled=false;
            //判断上一题是当前题的父问题还是同一级问题
            if (question.param.currentcNum > 0) {
                $("input[ptype='radio'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='checkbox'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='boolean'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='text'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                question.param.flag = question.param.childrenlevel;
                question.param.currentProblem = question.param.cpcpg[question.param.currentcNum - 1];
            } else {
                $("input[ptype='radio'][pid='" + question.data.parentProblem[question.param.currentpNum].problemId + "']").attr("disabled", false);
                $("input[ptype='checkbox'][pid='" + question.data.parentProblem[question.param.currentpNum].problemId + "']").attr("disabled", false);
                $("input[ptype='boolean'][pid='" + question.data.parentProblem[question.param.currentpNum].problemId + "']").attr("disabled", false);
                $("input[ptype='text'][pid='" + question.data.parentProblem[question.param.currentpNum].problemId + "']").attr("disabled", false);
                question.param.flag = question.param.parentlevel;
                question.param.currentProblem = question.data.parentProblem[question.param.currentpNum];
            }

        }

        if (question.param.flag == question.param.grandson) {
            //删除当前问题
            $("#" + (question.param.currentpNum + 1) + "-" + (question.param.currentcNum) + "-" + question.param.currentgNum).remove();
            question.param.currentgNum = --question.param.currentgNum;
            //使上一题后的当前问题变为可操作，disabled=false;
            //判断上一题是当前题的父问题还是同一级问题
            if (question.param.currentgNum > 0) {
                $("input[ptype='radio'][pid='" + question.param.cpgpg[question.param.currentgNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='checkbox'][pid='" + question.param.cpgpg[question.param.currentgNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='boolean'][pid='" + question.param.cpgpg[question.param.currentgNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='text'][pid='" + question.param.cpgpg[question.param.currentgNum - 1].problemId + "']").attr("disabled", false);
                question.param.flag = question.param.grandson;
                question.param.currentProblem = question.param.cpgpg[question.param.currentgNum - 1];
            } else {
                $("input[ptype='radio'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='checkbox'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='boolean'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                $("input[ptype='text'][pid='" + question.param.cpcpg[question.param.currentcNum - 1].problemId + "']").attr("disabled", false);
                question.param.flag = question.param.childrenlevel;
                question.param.currentProblem = question.param.cpcpg[question.param.currentcNum - 1];
            }
        }
        //获取上一题操作后的问题的答案信息
        question.param.canswer = new Array();
        var allAnswer = question.data.answer;
        for (var i = 0; i < allAnswer.length; i++) {
            if (!common.isEmpty(allAnswer[i])) {
                if (question.param.currentProblem.problemId == allAnswer[i].problemId) {
                    question.param.canswer.push(allAnswer[i]);
                }
            }
        }
        /*************************以下代码只针对预期生活方式问卷、快速营养调查问卷*************************/
        // 遍历获取答案，如果答案有特殊选项，这控制其他选项不可以选择，
        setTimeout(function() {
            $.each(allAnswer,
            function(index, value) {
                if (value.proptid == "11000022018011200252" || value.proptid == "11000022018011200136" || value.proptid == "11000022018011200215") {
                    var checkboxList = $("input[type='checkbox']");
                    $.each(checkboxList,
                    function(index, value) {
                        if ($(value).attr("id") != "11000022018011200252" && $(value).attr("id") != "11000022018011200136" && $(value).attr("id") != "11000022018011200215") {
                            $(value).attr("disabled", true);
                        }
                    });
                }
                // 若选择问题B2选择“肉食；偏肉食”则不能选择“很少摄入鱼肉蛋”
                if (value.proptid == "11000022018010900009" || value.proptid == "11000022018010900010") { // 肉食 偏肉食	
                    $("#11000022018010900019").attr("disabled",true);
                }
            });
            // 快速营养调查问卷
            setNutritious();
            if($.inArray("A00002",array_nutritious) > -1) {
                    $("#11000022018010900027").attr("disabled",true);
            }
        },
        300);
        /*************************以上代码只针对预期生活方式问卷、快速营养调查问卷*************************/
    });
    /************************************分割线：下面代码仅用于快速营养调查**********************************************/
    $("#problem_div").delegate("input[type='checkbox']", "click",
    function() { // 多选	
        setNutritious();
    });
    /************************************分割线：上面代码仅用于快速营养调查**********************************************/
});

//答案回显
question.showAnwer = function(problemId) {
    question.param.sanswer = new Array();
    //保存当前问题上次操作结果
    question.param.canswer = new Array();
    var allAnswer = question.data.answer;
    for (var i = 0; i < allAnswer.length; i++) {
        if (!common.isEmpty(allAnswer[i])) {
            if (problemId == allAnswer[i].problemId) {
                /*if(!common.isEmpty(allAnswer[i].answerContent)){
					question.param.sanswer.push(allAnswer[i].proptid);
				}else{
					question.param.sanswer.push(allAnswer[i].answerContent);
				}*/
                //取到原来答案后将答案从question.data.answer 中删除避免重复保存
                question.param.canswer.push(allAnswer[i]);
            }
        }
    }
    //alert(JSON.stringify(question.param.canswer));
};

//删除问题答案（由于用户上一步后可能导致此问题会被记录两遍，所以要删除原记录,由子问题开始上一题时，改变父问题的答案选项，删除原答案选项）
question.deletepAnwer = function() {
    var canswers = question.param.canswer;
    var answer = question.data.answer;
    if (Object.prototype.toString.apply(answer) === '[object Array]') {
        for (var i = 0; i < canswers.length; i++) {
            if (question.contions(answer, canswers[i])) {
                answer.splice($.inArray(canswers[i], answer), 1);
            }
        }
    }
};

//删除父问题下的子问题答案（可同时删除二级子问题信息）
/*
 * problemId 子问题编号
 */
question.deletecAnwer = function(problemId) {
    var deleteobj = new Array();
    var answer = question.data.answer;
    var cproblem = new Array(); //当前子问题下的的问题编号数组
    if (Object.prototype.toString.apply(answer) === '[object Array]') {
        for (var n = 0; n < answer.length; n++) {
            if (answer[n].problemId == problemId) {
                deleteobj.push(answer[n]);
                for (var i = 0; i < question.data.childrenProblem.length; i++) {
                    if (question.data.childrenProblem[i].problemParentId == problemId) {
                        for (var m = 0; m < question.data.childrenProblem[i].content.length; m++) {
                            cproblem.push(question.data.childrenProblem[i].content[m].problemId);
                        }
                    }
                }
            }
        }
        for (var j = 0; j < deleteobj.length; j++) {
            answer.splice($.inArray(deleteobj[j], answer), 1);
        }
        for (var j = 0; j < cproblem.length; j++) {
            question.deletecAnwer(cproblem[j]);
        }
    }
};

//判断 obj中是否包含 element
question.contions = function(obj, element) {
    var flag = false;
    $(obj).each(function(i, o) {
        if (o == element) {
            flag = true;
            return false;
        }
    });
    return flag;
};

//问卷提交
question.ajaxsub = function() {
    var index = layer.loading();
    $.ajax({
        url: question.url.submit,
        async: true,
        type: 'post',
        data: {
            allocId: $("#questionAllocId").val(),
            diagnosisId: $("#diagnosisId").val(),
            questionAnswers: JSON.stringify(question.data.answer)
        },
        dataType: dataType.json,
        success: function(data) { // 完成
            layer.close(index);
            /*alert(JSON.stringify(data.responseText));*/
            layer.alert(data.message,
            function() {
                if (data.result) {
                    opener.updateInspectStatus($("#inspectId").val(), data.value.questionAllocId);
                    window.close();
                }
            });

        },
        error: function(data, textstatus) {
            layer.close(index);
            layer.alert(data.responseText);
        }
    });
};

//文本框获取焦点选项变为选中状态
question.optionChecked = function(obj) {
    var id = $(obj).attr("optionId");
    $("#" + id).attr("checked", true);
};