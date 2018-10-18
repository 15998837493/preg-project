// 问卷对象
question = {};
// 问卷相关数据
question.data = {
	allProblem : null, // 全部问题
	parentProblem : null, // 只包含父问题
	childrenProblem : null, // 只包含子问题
	answer : new Array()
// 保存用户的答案选项
};
// 必填题
question.requiredProblems = new Map();
// 既往病史 其他
question.diseaseHistoryOther = [];
// 展示问题
question.showParent2 = function() {
	var count = 0;
	// 获取全部的problem数据
	question.data.allProblem.forEach(function(element, index, data) {
		// 记录必填问题
		if(element.problemRequired == "1"){
			var currentElement = {};
			currentElement.problemContent = element.problemContent.replace(/#/g, "__");
			currentElement.problemRequired = element.problemRequired;
			question.requiredProblems.set(element.problemId, currentElement);
		}
		if(element.problemParentId == "402880ef5a91a2b1015a91d2aa8b000e"){
			question.diseaseHistoryOther.push(element.problemId);
		}
		
		question.currentProblem = element;
		var displayFlag = "block";
		if (question.currentProblem.child) {
			displayFlag = "none";
		} else {
			count = count + 1;
			question.currentProblem.orderNo = count;
		}
		question.currentProblem.displayFlag = displayFlag;
		var modelid = 'p' + question.currentProblem.problemType;
		var gettpl = $("#" + modelid).html();
		laytpl(gettpl).render(question.currentProblem, function(html) {
			$("#parent_problem_div").append(html);
			setDH();
		});
	});
};

var rulemap = new Map();
rulemap.set("1", [// 当孕次等于1时
new Map([ [ "flag402880ef5a91a2b1015a91e5f7c50027", "none" ] ]),// 自然流产 次不显示
new Map([ [ "flag402880b35eb6f014015eb72059cf0015", "none" ] ]),// 人工流畅 次不显示
new Map([ [ "flag402880ef5a91a2b1015a91e66c450028", "none" ] ]), // 胎停育 次
new Map([ [ "flag402880ef5a91a2b1015a91e73e730029", "none" ] ]), // 是否有早产
new Map([ [ "flag402880ef5a91a2b1015a91e812f1002a", "none" ] ]),// 是否有中期引产
new Map([ [ "flag402880ef5a91a2b1015a922ae8bf002e", "none" ] ]),// 是否有足月分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8818e002b", "none" ] ]),// 是否有巨大儿分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8ec52002c", "block" ] ]),// 是否畸形
new Map([ [ "flag402880ef5a91a2b1015a91eb0c02002d", "none" ] ]) ]);// 既往妊娠并发症
rulemap.set("2", [// 当孕次大于等于2并且孕次-产次=1
new Map([ [ "flag402880ef5a91a2b1015a91e5f7c50027", "none" ] ]),// 自然流产
new Map([ [ "flag402880b35eb6f014015eb72059cf0015", "none" ] ]),// 人工流畅 次不显示
new Map([ [ "flag402880ef5a91a2b1015a91e66c450028", "none" ] ]),// 胎停育 次
new Map([ [ "flag402880ef5a91a2b1015a91e812f1002a", "none" ] ]),// 是否有中期引产
new Map([ [ "flag402880ef5a91a2b1015a91e73e730029", "block" ] ]), // 是否早产
new Map([ [ "flag402880ef5a91a2b1015a922ae8bf002e", "block" ] ]),// 是否足月分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8ec52002c", "block" ] ]),// 是否畸形
new Map([ [ "flag402880ef5a91a2b1015a91eb0c02002d", "block" ] ]),// 既往妊娠并发症
new Map([ [ "flag402880ef5a91a2b1015a91e8818e002b", "block" ] ]) ]);// 是否巨大儿
rulemap.set("3", [// 当孕次大于等于1并且孕次-产次=0
new Map([ [ "flag402880ef5a91a2b1015a91e5f7c50027", "none" ] ]),// 自然流产
new Map([ [ "flag402880b35eb6f014015eb72059cf0015", "none" ] ]),// 人工流畅 次不显示
new Map([ [ "flag402880ef5a91a2b1015a91e66c450028", "none" ] ]),// 胎停育 次
new Map([ [ "flag402880ef5a91a2b1015a91e812f1002a", "none" ] ]),// 是否有中期引产
new Map([ [ "flag402880ef5a91a2b1015a91e73e730029", "block" ] ]), // 是否早产
new Map([ [ "flag402880ef5a91a2b1015a922ae8bf002e", "block" ] ]),// 是否足月分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8ec52002c", "block" ] ]),// 是否畸形
new Map([ [ "flag402880ef5a91a2b1015a91eb0c02002d", "block" ] ]),// 既往妊娠并发症
new Map([ [ "flag402880ef5a91a2b1015a91e8818e002b", "block" ] ]) ]);// 是否巨大儿
rulemap.set("4", [// 若产次为1时且该题选项为是时，不显示6、7题；
new Map([ [ "flag402880ef5a91a2b1015a922ae8bf002e", "none" ] ]),// 是否足月分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8818e002b", "none" ] ]) ]);// 是否巨大儿
rulemap.set("5", [// 若产次为1时且该题选项为是时，不显示6、7题；
new Map([ [ "flag402880ef5a91a2b1015a922ae8bf002e", "block" ] ]),// 是否足月分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8818e002b", "block" ] ]) ]);// 是否巨大儿
rulemap.set("6", [// 当孕次大于等于2并且孕次-产次=1
new Map([ [ "flag402880ef5a91a2b1015a91e5f7c50027", "block" ] ]),// 自然流产
new Map([ [ "flag402880b35eb6f014015eb72059cf0015", "block" ] ]),// 人工流畅 次不显示
new Map([ [ "flag402880ef5a91a2b1015a91e66c450028", "block" ] ]),// 胎停育 次
new Map([ [ "flag402880ef5a91a2b1015a91e812f1002a", "block" ] ]),// 是否有中期引产
new Map([ [ "flag402880ef5a91a2b1015a91e73e730029", "block" ] ]), // 是否早产
new Map([ [ "flag402880ef5a91a2b1015a922ae8bf002e", "block" ] ]),// 是否足月分娩
new Map([ [ "flag402880ef5a91a2b1015a91e8ec52002c", "block" ] ]),// 是否畸形
new Map([ [ "flag402880ef5a91a2b1015a91eb0c02002d", "block" ] ]),// 既往妊娠并发症
new Map([ [ "flag402880ef5a91a2b1015a91e8818e002b", "block" ] ]) ]);// 是否巨大儿

function setDH() {
	var height = $("#problem_div").height();
	var $uppro = $("#uppro");
	var $downpro = $("#downpro");
	$uppro.css("height", "auto");
	$downpro.css("height", "auto");
	$uppro.height(height);
	$downpro.height(height);
}

$(function() {

	$("#initForm").attr("action", URL.get("Platform.DIAGNOSIS_INITAL_VIEW_INIT"));
	$("#initForm").ajaxPost(dataType.json, function(data) {
		$("#questionAllocId").val(data.value.allowId);
		question.data.allProblem = data.value.problemVos;
		question.showParent2();
		$("#210100700000229, #210100700000228").on("input propertychange", function() {// 监听产次
			if($("#210100700000228").val() == 0){
				layer.alert("孕次不能为0");
				$("#210100700000228").val("1");
				$("#210100700000229").val("0");
			}
			// 如果孕次-产次大于等于0，提示信息并且情况产次
			var yunNum = $("#210100700000228").val();// 孕次
			var chanNum = $("#210100700000229").val();// 产次
			var ziranNum = $("#210100700000230").val();// 自然流产次数
			var rengongNum = $("#11000022017092500004").val();// 人工流产次数
			var tingNum = $("#210100700000231").val();// 胎停育次数
			var yunNumInt = parseInt(yunNum);
			var chanNumInt = parseInt(chanNum);
			var ziranNumInt = parseInt(ziranNum);
			var rengongNumInt = parseInt(rengongNum);
			var tingNumInt = parseInt(tingNum);
			if (yunNumInt <= chanNumInt) {
				layer.alert("孕次不能小于等于产次");
				$('#210100700000229').val("0");
			}
			if (yunNumInt <= (chanNumInt+ziranNumInt+rengongNumInt+tingNumInt)){
				layer.alert("孕次和产次、自然流产、人工流产、胎停育次数不匹配");
				$('#210100700000229').val("0");
				$('#210100700000230').val("0");
				$('#11000022017092500004').val("0");
				$('#210100700000231').val("0");
			}
			if(yunNumInt == 1){
				specialhandDiv("1");
			}else if(yunNumInt - chanNumInt >= 2 || yunNumInt - chanNumInt <= 0 ){//小于0是处理上面的特殊情情况，有js修改内容的时候触发
				specialhandDiv("6");
			}else{
				specialhandDiv("3");
			}
		});
		$("input[name='402880ef5a91a2b1015a91e73e730029']").change(function() {// 若产次为1时且该题选项为是时，不显示6、7题；
			var radioValue = this.value;
			var chanNum = $("#210100700000229").val();// 产次
			var chanNumInt = parseInt(chanNum);
			if (radioValue === "210100700000232" && chanNumInt === 1) {
				specialhandDiv("4");
			} else {
				specialhandDiv("5");
			}
		});
		// 最低体重时的孕周数不能大于当前孕周 11000022017092800035
		$("#11000022017092800035").change(function() {
			var leastWeek = $("#11000022017092800035").val();// 孕周数
			var lmpDate = $("#110000200000258").val();// 末次月经
			var weekDays = pregnancy.getGestWeeksByLmpDate(common.dateFormatToString(new Date(), "yyyy-MM-dd"), lmpDate);
			if(!_.isEmpty(leastWeek) && !_.isEmpty(lmpDate) && parseInt(weekDays.split("\+")[0]) < parseInt(leastWeek)){
				layer.alert("最低体重时的孕周数不能大于当前孕周！");
				 $("#11000022017092800035").val("");
				return false;
			}
		});
		// 既往病史 选择"无"时，不能选择其他选项
		$("#11000022018030200026").change(function(){
			if(this.checked){
				$("#flag402880ef5a91a2b1015a91d2aa8b000e").find("input:checkbox").not(this).attr("checked",false).attr("disabled", true);
				if(!_.isEmpty(question.diseaseHistoryOther)){
					$(question.diseaseHistoryOther).each(function(index, problemId){
						$("#flag" + problemId).hide();
						$("#flag" + problemId).find("input").val("");
					});
				}
			} else{
				$("#flag402880ef5a91a2b1015a91d2aa8b000e").find("input:checkbox").attr("disabled", false);
			}
		});
		// 既往妊娠并发症 选择"无"时，不能选择其他选项
		$("#210100700000251").change(function(){
			if(this.checked){
				$("#flag402880ef5a91a2b1015a91eb0c02002d").find("input:checkbox").not(this).attr("checked",false).attr("disabled", true);
			} else{
				$("#flag402880ef5a91a2b1015a91eb0c02002d").find("input:checkbox").attr("disabled", false);
			}
		});

        //隐藏是否有刨宫产
        $("#flag402880f9653c86f901653ca26c9c0004").css("display","none");
		//加载数据时，如果产次大于1，显示是否刨宫产
		var json = data.value.answerVos;
		for (var i =0 , size = json.length ; i < size ;i++){
			if (json[i].problemOptionId == "210100700000229"){
				var a = json[i].answerContent;
				if (a >= 1) {
                    $("#flag402880f9653c86f901653ca26c9c0004").css("display","block");
				}
			}
		}

		//监听产次数--产次大等于1时显示，否则隐藏
		$("#210100700000229").on("input propertychange",function () {
			var chanNum = $("#210100700000229").val();
            if (chanNum >= 1){
                $("#flag402880f9653c86f901653ca26c9c0004").css("display","block");
			} else {
                $("#flag402880f9653c86f901653ca26c9c0004").css("display","none");
			}
        })

		changeRowNum();
		initAnswer(data.value.answerVos);
		$("#110000200000258,#110000200000259").addClass("form-control");
		var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
		common.initDate(null,null,2,"#110000200000258");
		var minDate = getDateStr(-279);// 距现在时间280天以前，预产期不能是当天日期所以*279
		$("#110000200000258").datetimepicker("setStartDate",minDate);
		$("#110000200000258").datetimepicker("setEndDate",nowDate);
		//日期插件格式定义--出生日期
		common.initDate(null,null,4,"#110000200000259","1990-01-01");
		$("#110000200000259").datetimepicker("setEndDate",nowDate);
		$(".form_date").attr("readonly","readonly");
		$("#110000200000258,#110000200000259").removeClass("form-control");
		
		//初始化显示内容
		var yunNum = $("#210100700000228").val();// 孕次
		var chanNum = $("#210100700000229").val();// 产次
		var yunNumInt = parseInt(yunNum);
		var chanNumInt = parseInt(chanNum);
		if(yunNumInt == 1){
			specialhandDiv("1");
		}else if(yunNumInt - chanNumInt >= 2 || yunNumInt - chanNumInt <= 0 ){//小于0是处理上面的特殊情情况，有js修改内容的时候触发
			specialhandDiv("6");
		}else{
			specialhandDiv("3");
		}
		
		// 自然受孕方式
		if($("#210100700000138").is(":checked")) {
			$("#flag402880f4650b0fbb01650b255b860009").css("display","block");	
		}else {
			$("#flag402880f4650b0fbb01650b255b860009").css("display","none");
		}
		
		// 过敏源
		if($("#210100700000174").is(":checked")) {
			$("#flag402880f4650b0fbb01650c0a34a50085").css("display","block");	
			$("#flag402880f4650b0fbb01650c3afa63008b").css("display","block");
			$("#flag402880f4650b0fbb01650c4202170096").css("display","block");
			$("#flag402880f4650b0fbb01650c45a9a200a6").css("display","block");
		}else {
			$("#flag402880f4650b0fbb01650c0a34a50085").css("display","none");	
			$("#flag402880f4650b0fbb01650c3afa63008b").css("display","none");
			$("#flag402880f4650b0fbb01650c4202170096").css("display","none");
			$("#flag402880f4650b0fbb01650c45a9a200a6").css("display","none");
		}

        // // 否有糖尿病病史
        // if($("#11000022017092500032").is(":checked")||$("#11000022017092500033").is(":checked")||$("#11000022017092500034").is(":checked")||$("#11000022017092500031").is(":checked")) {
        //     $("#flag402880f9653c86f901653ca01df80000").css("display","block");
        // }else {
        //     $("#flag402880f9653c86f901653ca01df80000").css("display","none");
        // }
		
		// 其他：您对哪种药物有过敏史 填空内容回显
		if($("#402880f965890cff0165891072840004").is(":checked")) {
			// 如果选了“其他”，就判断填空是否有内容，如果有：显示填空及内容，如果没有，不显示
			if(data.value.answerVos.length > 0) {
				for(let x=0;x<data.value.answerVos.length;x++) {
					if(data.value.answerVos[x].problemOptionId == "402880f965890cff0165891072840004") {
						if($("input[name='402880f965890cff0165891072840004_content']").length == 0) {
							$("#402880f965890cff0165891072840004").parent().append("<input type='text' value='"+data.value.answerVos[x].answerContent+"' maxlength=20 id='402880f965890cff0165891072840004_content' name='402880f965890cff0165891072840004_content' style='border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;'/>");
						}
					}
				}
			}
		}
		
		// 其他：您对哪种食物有急性过敏史 填空内容回显
		if($("#402880f965890cff0165891bfc1e000e").is(":checked")) {
			// 如果选了“其他”，就判断填空是否有内容，如果有：显示填空及内容，如果没有，不显示
			if(data.value.answerVos.length > 0) {
				for(let x=0;x<data.value.answerVos.length;x++) {
					if(data.value.answerVos[x].problemOptionId == "402880f965890cff0165891bfc1e000e") {
						if($("input[name='402880f965890cff0165891bfc1e000e_content']").length == 0) {
							$("#402880f965890cff0165891bfc1e000e").parent().append("<input type='text' value='"+data.value.answerVos[x].answerContent+"' maxlength=20 id='402880f965890cff0165891bfc1e000e_content' name='402880f965890cff0165891bfc1e000e_content' style='border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;'/>");
						}
					}
				}
			}
		}
		
		// 其他：您对哪种食物慢性食物不耐受 填空内容回显
		if($("#402880f965890cff0165891c2221001d").is(":checked")) {
			// 如果选了“其他”，就判断填空是否有内容，如果有：显示填空及内容，如果没有，不显示
			if(data.value.answerVos.length > 0) {
				for(let x=0;x<data.value.answerVos.length;x++) {
					if(data.value.answerVos[x].problemOptionId == "402880f965890cff0165891c2221001d") {
						if($("input[name='402880f965890cff0165891c2221001d_content']").length == 0) {
							$("#402880f965890cff0165891c2221001d").parent().append("<input type='text' value='"+data.value.answerVos[x].answerContent+"' maxlength=20 id='402880f965890cff0165891c2221001d_content' name='402880f965890cff0165891c2221001d_content' style='border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;'/>");
						}
					}
				}
			}
		}
		
		// 其他：是否还对其他物质过敏 填空内容回显
		if($("#402880f965890cff0165891c4a530022").is(":checked")) {
			// 如果选了“其他”，就判断填空是否有内容，如果有：显示填空及内容，如果没有，不显示
			if(data.value.answerVos.length > 0) {
				for(let x=0;x<data.value.answerVos.length;x++) {
					if(data.value.answerVos[x].problemOptionId == "402880f965890cff0165891c4a530022") {
						if($("input[name='402880f965890cff0165891c4a530022_content']").length == 0) {
							$("#402880f965890cff0165891c4a530022").parent().append("<input type='text' value='"+data.value.answerVos[x].answerContent+"' maxlength=20 id='402880f965890cff0165891c4a530022_content' name='402880f965890cff0165891c4a530022_content' style='border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;'/>");
						}
					}
				}
			}
		}

        // 父母兄弟姐妹是否患疾病其他：填空内容回显
        if($("#402880f9657a5a2201657a5d309f0002").is(":checked")) {
            // 如果选了“其他”，就判断填空是否有内容，如果有：显示填空及内容，如果没有，不显示
            if(data.value.answerVos.length > 0) {
                for(let x=0;x<data.value.answerVos.length;x++) {
                    if(data.value.answerVos[x].problemOptionId == "402880f9657a5a2201657a5d309f0002") {
                        if($("input[name='402880f9657a5a2201657a5d309f0002_content']").length == 0) {
                            $("#402880f9657a5a2201657a5d309f0002").parent().append("<input type='text' value='"+data.value.answerVos[x].answerContent+"' maxlength=20 id='402880f9657a5a2201657a5d309f0002_content' name='402880f9657a5a2201657a5d309f0002_content' style='border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;'/>");
                        }
                    }
                }
            }
        }
		
		//获取永久诊断项目
		var url = URL.get("item.QUERY_INTERVENEDISEASE");
		var params = "diagnosisIsPermanent=1";
		ajax.post(url, params, dataType.json, function(data) {
			if(!common.isEmpty(data)){
				$.each(data.data, function(index, value){
					$("#diseaseCheckbox").append(
							"<div class='col-xs-3'>"+
							"    <label class='checkbox-inline'><input type='checkbox' name='diseaseName' value="+value.diseaseName+">"+value.diseaseName+"</label>"+
							"</div>"
					);
				});
			}
		}, false, false);
		$("#11000022017101800002").attr("readonly","readonly");
		$("#11000022017101800002").width(500);
		$("#11000022017101800002").on("click", function(){
			var disArr = $("#11000022017101800002").val().split("、");
			$.each(disArr, function(index, value){
				$("[name='diseaseName'][value='"+value+"']").attr("checked",true); 
			});
			$("#diseaseModal").modal("show");
		});
		changeRowNum();
		
		// 判断既往病史是否选择了"无"
		if($("#11000022018030200026").attr("checked")){
			$("#flag402880ef5a91a2b1015a91d2aa8b000e").find("input:checkbox").not("#11000022018030200026").attr("checked",false).attr("disabled", true);
		}
		// 判断妊娠并发症是否选择了"无"
		if($("#210100700000251").attr("checked")){
			$("#flag402880ef5a91a2b1015a91eb0c02002d").find("input:checkbox").not("#210100700000251").attr("checked",false).attr("disabled", true);
		}
		// 判断既往病史是否选择了"其他"
		if($("#210100700000199").attr("checked")){
			$("#flag402880b35eb6f014015eb794d3710026").show();
			changeRowNum();
		}
		//您对哪种药物有过敏史
        $("input[name='402880f965890cff0165891072840004']").click(function() {
            if(this.id == "402880f965890cff0165891072840004" && $("#402880f965890cff0165891072840004").prop("checked")) {
                $("#402880f965890cff0165891072840004").parent().append('<input maxlength=20 id="402880f965890cff0165891072840004_content" name="402880f965890cff0165891072840004_content" type="text" style="border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;" />');
            } else {
                $("#402880f965890cff0165891072840004_content").remove();
            }
        });
        //您对哪种食物有急性过敏史
        $("input[name='402880f965890cff0165891bfc1e000e']").click(function() {
            if(this.id == "402880f965890cff0165891bfc1e000e" && $("#402880f965890cff0165891bfc1e000e").prop("checked")) {
                $("#402880f965890cff0165891bfc1e000e").parent().append('<input maxlength=20 id="402880f965890cff0165891bfc1e000e_content" name="402880f965890cff0165891bfc1e000e_content" type="text" style="border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;" />');
            } else {
                $("#402880f965890cff0165891bfc1e000e_content").remove();
            }
        });
        //您对哪种食物慢性食物不耐受？
        $("input[name='402880f965890cff0165891c2221001d']").click(function() {
            if(this.id == "402880f965890cff0165891c2221001d" && $("#402880f965890cff0165891c2221001d").prop("checked")) {
                $("#402880f965890cff0165891c2221001d").parent().append('<input maxlength=20 id="402880f965890cff0165891c2221001d_content" name="402880f965890cff0165891c2221001d_content" type="text" style="border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;" />');
            } else {
                $("#402880f965890cff0165891c2221001d_content").remove();
            }
        });
		//是否还对其他物质过敏
        $("input[name='402880f965890cff0165891c4a530022']").click(function() {
            if(this.id == "402880f965890cff0165891c4a530022" && $("#402880f965890cff0165891c4a530022").prop("checked")) {
                $("#402880f965890cff0165891c4a530022").parent().append('<input maxlength=20 id="402880f965890cff0165891c4a530022_content" name="402880f965890cff0165891c4a530022_content" type="text" style="border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;" />');
            } else {
                $("#402880f965890cff0165891c4a530022_content").remove();
            }
        });
		//父母兄弟姐妹是否患病
        $("input[name='402880f9657a5a2201657a5d309f0002']").click(function() {
            if(this.id == "402880f9657a5a2201657a5d309f0002" && $("#402880f9657a5a2201657a5d309f0002").prop("checked")) {
                $("#402880f9657a5a2201657a5d309f0002").parent().append('<input maxlength=20 id="402880f9657a5a2201657a5d309f0002_content" name="402880f9657a5a2201657a5d309f0002_content" type="text" style="border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;" />');
            } else {
                $("#402880f9657a5a2201657a5d309f0002_content").remove();
			}
        });
	});
});
question.setCheckBoxHiden = function(optionId, childId) {
	if (!common.isEmpty(childId)) {
		var childArray = childId.split(",");
		for ( var i in childArray) {
			if ($("#" + optionId).is(':checked')) {
				// 展示子节点
				$("#flag" + childArray[i]).show();
			} else {
				// 隐藏子节点
				$("#flag" + childArray[i]).hide();
				$("#flag" + childArray[i]).find("input").val("");
			}
		}
	}
	changeRowNum();
};

question.setRadioHiden = function(optionId, childId) {
	// 获取同辈元素及同辈元素中的childId元素
	var siblingsElement = $("#" + optionId).parents("label").siblings("label")
			.find("input").attr("childId");
	if (!common.isEmpty(siblingsElement)) {
		var siblingsElementA = siblingsElement.split(",");
		for ( var i in siblingsElementA) {
			$("#flag" + siblingsElementA[i]).find("input").val("");
			$("#flag" + siblingsElementA[i]).find("input").attr("checked",false);
			$("#flag" + siblingsElementA[i]).find("input").change();
			$("#flag" + siblingsElementA[i]).hide();
		}
	}
	if (!common.isEmpty(childId)) {
		var childArray = childId.split(",");
		for ( var i in childArray) {
			if ($("#" + optionId).is(':checked')) {
				// 展示子节点
				$("#flag" + childArray[i]).show();
			}
		}
	}
	changeRowNum();
};
/**
 * 表单必填项校验方法
 */
function validateForm(){
	//计算bmi如果大于999则身高体重不合理
	var custHeight = $("#11000022017072800008").val();
	var custWeight = $("#11000022017072800009").val();
	var bmi = custWeight / Math.pow((custHeight/100),2);
	if(bmi > 100){
		layer.alert("身高或体重不合理，请调整");
		return false;
	}
	
	// 如果孕次-产次大于等于0，提示信息并且情况产次
	var yunNum = $("#210100700000228").val();// 孕次
	var chanNum = $("#210100700000229").val();// 产次
	var ziranNum = $("#210100700000230").val();// 自然流产次数
	var rengongNum = $("#11000022017092500004").val();// 人工流产次数
	var tingNum = $("#210100700000231").val();// 胎停育次数
	var yunNumInt = parseInt(yunNum);
	var chanNumInt = parseInt(chanNum);
	var ziranNumInt = parseInt(ziranNum);
	var rengongNumInt = parseInt(rengongNum);
	var tingNumInt = parseInt(tingNum);
	if (yunNumInt <= (chanNumInt+ziranNumInt+rengongNumInt+tingNumInt)){
		layer.alert("孕次和产次、自然流产、人工流产、胎停育次数不匹配");
//		$('#210100700000229').val("0");
//		$('#210100700000230').val("0");
//		$('#11000022017092500004').val("0");
//		$('#210100700000231').val("0");
		return false;
	}
	
	// 最低体重时的孕周数不能大于当前孕周 11000022017092800035
	var leastWeek = $("#11000022017092800035").val();// 孕周数
	var lmpDate = $("#110000200000258").val();// 末次月经
	var weekDays = pregnancy.getGestWeeksByLmpDate(common.dateFormatToString(new Date(), "yyyy-MM-dd"), lmpDate);
	if(!_.isEmpty(leastWeek) && !_.isEmpty(lmpDate) && parseInt(weekDays.split("\+")[0]) < parseInt(leastWeek)){
		layer.alert("最低体重时的孕周数不能大于当前孕周！");
		return false;
	}
	return true;
}
// 保存问卷相关信息
function saveQuestion() {
	if(!validateForm()){
		return;
	}
	// 获取表单信息
	var fromJson = $("#initForm").serializeArray();
	var diaId = $("#diagnosisId").val();
	var questionAllocId = $("#questionAllocId").val();
	var answerList = new Array();
	var answerMap = new Map();
	fromJson.forEach(function(element, index, data) {
		var type = $("input[name='" + element.name + "']").attr("ptype");// 数据类型
		if (common.isEmpty(type)) {
			return;
		}
		var problemId = $("input[name='" + element.name + "']").attr("pid");// 父节点
		// 问卷答案对象
		var answer = {
			problemId : null, // 问题编号
			proptid : null, // 问题选项编号
			answerContent : null, // 答案内容
			diagnosisId : null
		// 问卷id
		};
		if (type === "checkbox") {
			answer.proptid = element.name;
			answer.problemId = problemId;
			answer.diagnosisId = diaId;
			answerList.push(answer);
            answerMap.set(problemId, element.name);
            if(element.name == "402880f965890cff0165891072840004") {
                answer.answerContent = $("#402880f965890cff0165891072840004_content").val();//您对哪种药物有过敏史
            }
            if(element.name == "402880f965890cff0165891bfc1e000e") {
                answer.answerContent = $("#402880f965890cff0165891bfc1e000e_content").val();//您对哪种食物有急性过敏史
            }
            if(element.name == "402880f965890cff0165891c2221001d") {
                answer.answerContent = $("#402880f965890cff0165891c2221001d_content").val();//您对哪种食物慢性食物不耐受
            }
            if(element.name == "402880f965890cff0165891c4a530022") {
                answer.answerContent = $("#402880f965890cff0165891c4a530022_content").val();//是否还对其他物质过敏
            }
            if(element.name == "402880f9657a5a2201657a5d309f0002") {
                answer.answerContent = $("#402880f9657a5a2201657a5d309f0002_content").val();//父母兄弟姐妹是否患病
            }
        } else if (type === "radio") {
			answer.proptid = element.value;
			answer.problemId = problemId;
			answer.diagnosisId = diaId;
			answerList.push(answer);
			answerMap.set(problemId, element.value);
		} else if (type === "text") {
			if (common.isEmpty(element.value)) {
				return;
			}
			answer.proptid = element.name;
			answer.problemId = problemId;
			answer.answerContent = element.value;
			answer.diagnosisId = diaId;
			answerList.push(answer);
			answerMap.set(problemId, element.value);
		}
	});
	var validateResult = "";
	if(question.requiredProblems.size > 0){
		question.requiredProblems.forEach(function(value, key){
			var problemRequired = question.requiredProblems.get(key).problemRequired;
			if(problemRequired == "1"){
				if(_.isEmpty(validateResult) && (!answerMap.has(key) || _.isEmpty(answerMap.get(key)))){
					validateResult = question.requiredProblems.get(key).problemContent + "#" + key;
				} else if($("input[pid='"+key+"']").attr("ptype") == "text" && $("input[pid='"+key+"']").length > 1){
					$("input[pid='"+key+"']").each(function(){
						if(_.isEmpty(validateResult) && _.isEmpty(this.value)){
							validateResult = question.requiredProblems.get(key).problemContent + "#" + key;
						}
					});
				}
			}
		});
		// 自然受孕
		if($("#210100700000138").is(":checked") == true) {
			const len = $("[name='402880f4650b0fbb01650b255b860009']").length;
			let flag = false;
			for(let x=0;x<len;x++) {
				if($("[name='402880f4650b0fbb01650b255b860009']:eq("+x+")").is(":checked")) {
					flag = true;
					break;
				}
			}
			if(flag == false) {
				layer.alert("自然受孕方式未选择！");
				return false;
			}
		}
		// //是否有糖尿病
		// if($("#11000022017092500035").is(":checked") == true){
		//
        //     const tangniaobing = $("[name='402880f9653c86f901653ca01df80000']").length;
        //     let flag = false;
        //     for(let x=0;x<len;x++) {
        //         if($("[name='402880f9653c86f901653ca01df80000']:eq("+x+")").is(":checked")) {
        //             flag = true;
        //             break;
        //         }
        //     }
        //     if(flag == false) {
        //         layer.alert("父母兄弟姐妹是否患病未选择！");
        //         return false;
        //     }
		// }
		
		// 过敏源
//		if($("#210100700000174").is(":checked") == true) {
//			for(let out=0;out<4;out++) {
//				let name = "";
//				let mes = "";
//				if(out == 0) {
//					name = "402880f4650b0fbb01650c0a34a50085";
//					mes = "药物过敏史未选择！";
//				}else if(out == 1) {
//					name = "402880f4650b0fbb01650c3afa63008b";
//					mes = "食物过敏史未选择！";
//				}else if(out == 2) {
//					name = "402880f4650b0fbb01650c4202170096";
//					mes = "食物不耐受未选择！";
//				}else if(out == 3) {
//					name = "402880f4650b0fbb01650c45a9a200a6";
//					mes = "其他物质过敏史未选择！";
//				}
//				const len = $("[name='"+name+"']").length;
//				let flag = false;
//				for(let x=0;x<len;x++) {
//					if($("[name='"+name+"']:eq("+x+")").is(":checked")) {
//						flag = true;
//						break;
//					}
//				}
//				if(flag == false) {
//					layer.alert(mes);
//					return false;
//				}	
//			}
//		}

	}
	if(!_.isEmpty(validateResult)){
		layer.alert("问题【"+validateResult.split("#")[0]+"】为必答题！");
		window.location.hash = "#a_question_index_"+validateResult.split("#")[1];
		return false;
	}
	var url = URL.get("Question.SAVE_QUESTION_ARCHIVE");
	var params = "allocId=" + questionAllocId + "&diagnosisId=" + diaId + "&questionAnswers=" + JSON.stringify(answerList);
	layer.confirm("确定要执行【保存】操作？", function () {
		ajax.post(url, params, dataType.json, function(data) {
			// 保存孕期档案结论
			if (openerType == 'assistant'){
				opener.window.open(URL.get("Evaluate.EVALUATE_GUIDE_PAGE") + "?diagnosisId=" + $("#diagnosisId").val());
				opener.refreshDataTable();
			} else if (openerType == 'customer'){
				opener.window.open(URL.get("Platform.PLAN_GUIDE_PAGE") + "?diagnosisId=" + $("#diagnosisId").val());
				opener.refreshDataTable();
			} else {
				opener.location.reload(true);
			}
			window.close();
		});
	});
}
function initAnswer(answer) {
	if (!common.isEmpty(answer)) {
		$.each(answer, function(key, value) {
			var option = value.problemOptionId;
			if (!common.isEmpty(option)) {
				var elementType = $('#' + option).attr("ptype");// 数据类型
				if (elementType === 'text') {
					$('#flag' + value.problemId).show();
					$('#' + option).val(value.answerContent);
					// 过敏史
					if(value.problemId == "402880ef5a91a2b1015a91c6905d000b" 
						|| value.problemId == "402880ef5a91a2b1015a91c7b794000c" 
						|| value.problemId == "402880ef5a91a2b1015a91ccd1ea000d"){
						$('#flag402880ef5a91a2b1015a91c6905d000b').show();
						$('#flag402880ef5a91a2b1015a91c7b794000c').show();
						$('#flag402880ef5a91a2b1015a91ccd1ea000d').show();
					}
					changeRowNum();
				} else if (elementType === 'radio'
						|| elementType === "checkbox") {
					$('#' + option).attr("checked", true);
					$('#' + option).show();
					changeRowNum();
				}
			}
		});
	}
};
/**
 * 验证
 * @param id
 * @param value
 * @param optionValidate
 */
function validate(id, value, optionValidate) {
	if (!(common.isEmpty(optionValidate) || common.isEmpty(value))) {
		if (!new RegExp(optionValidate).test(value)) {
			layer.alert("输入数据不正确");
			$('#' + id).val("");
			return;
		}
		if(id == "11000022017092800035" && value > 40){
			layer.alert("孕周数不能大于40周");
			return;
		}
	}
}

function specialhandDiv(flag) {
	if (rulemap.has(flag)) {
		var mapresult = rulemap.get(flag);
		mapresult.forEach(function(element, index, map) {
			element.forEach(function(e, i, m) {
				$('#' + i).css("display", e);
				if("none" == e){
					$("#" + i).find("input[ptype='text']").val("");
					$("#" + i).find("input[ptype='radio'],input[ptype='checkbox']").attr("checked", false).attr("disabled", false);
				}
				// 是否有巨大儿分娩
				if(i == "flag402880ef5a91a2b1015a91e8818e002b" && question.requiredProblems.has("402880ef5a91a2b1015a91e8818e002b")){
					if("none" == e){
						question.requiredProblems.get("402880ef5a91a2b1015a91e8818e002b").problemRequired = 2;
					} else{
						question.requiredProblems.get("402880ef5a91a2b1015a91e8818e002b").problemRequired = 1;
					}
				}
				// 既往妊娠并发症
				if(i == "flag402880ef5a91a2b1015a91eb0c02002d" && question.requiredProblems.has("402880ef5a91a2b1015a91eb0c02002d")){
					if("none" == e){
						question.requiredProblems.get("402880ef5a91a2b1015a91eb0c02002d").problemRequired = 2;
					} else{
						question.requiredProblems.get("402880ef5a91a2b1015a91eb0c02002d").problemRequired = 1;
					}
				}
			});
		});
		changeRowNum();
	}
}
function changeRowNum() {
	var divNum = 0;
	$("div[id*='flag']").each(function(i, element) {
		if ($(element).css("display") == 'block') {
			var flagId = element.id;
			divNum = divNum + 1;
			$("#title" + flagId.substring(4)).text(divNum);

		}
	});
}
/**
 * 获取永久诊断项目
 */
function getDisease(){
	$("#11000022017101800002").val("");
	var diseaseArr = [];
	$('input[name="diseaseName"]:checked').each(function(index, value){
		diseaseArr.push($(value).val());
	});
	if(diseaseArr.length > 10){
		layer.alert("最多添加10个诊断项目");
		return;
	}
	$("#11000022017101800002").val(diseaseArr.join("、"));
	$("#diseaseModal").modal("hide");
}

/**
 * 计算AddDayCount天前的日期
 * 
 * @param AddDayCount
 * @param date
 * @returns {String}
 */
function getDateStr(AddDayCount, date) {
	var dd = common.isEmpty(date)?new Date():new Date(date);
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期    
	var y = dd.getFullYear();   
	var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0    
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0    
	return y+"-"+m+"-"+d;     
}
