<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/base.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>孕期膳食及生活方式评估--${custName}</title>
<link rel="stylesheet" type="text/css" href="${common.basepath}/common/plugins/blackbox/css/blackbox.css" />
<link rel="stylesheet" type="text/css" href="${common.basepath}/common/plugins/bootstrap3.2.0/css/bootstrap-default.min.css" />
<link rel="stylesheet" type="text/css" href="${common.basepath}/common/plugins/jquery/qtip/tips.css" />
<link rel="stylesheet" type="text/css" href="${common.basepath}/common/plugins/scrollup/css/themes/image.css">
<link rel="stylesheet" type="text/css" href="${common.basepath}/common/plugins/slider/bootstrap-slider.min.css">

<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/blackbox/js/jquery.blackbox.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/scrollup/js/jquery.scrollUp.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/jquery.validate.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/additional-methods.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/localization/messages_zh.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/validate.regexp.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/bootstrap3.2.0/js/bootstrap.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/jquery/qtip/tips.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/slider/bootstrap-slider.min.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/layer/layer.js"></script>

<title>生活方式调查及生活方式评估问卷</title>
<style type="text/css">
/* 其他后输入线样式 */
.underline {
    background-color: transparent;
    border: 0;
    border-bottom: solid 1px #7F9DB9;
    max-width: 500px;
    color: rgb(0, 0, 0);
    position: static;
}

/* 问题提示样式 */
.div_ins_question {
    padding-top: 8px;
    color: #666666;
    padding-left: 20px;
    line-height: 18px;
    clear: both;
}
#ex1Slider .slider-selection {
	background: #BABABA;
}

.tooltip-inner {
	max-width: 40px;
	margin-top: 0px;
    padding: 5px;
    color: #000;
    text-align: left;
    text-decoration: none;
    background-color: #fff;
    border-radius: 4px;
    border: 1px solid #337AB7;
    line-height: 20px;
    box-shadow: 0 2px 6px #000;
}
</style>
<script type="text/javascript">
var box = new BlackBox();
var URL = opener.URL;
var CODE = opener.CODE;
var PublicConstant = opener.PublicConstant;
var ratio = "0";
</script>
<script type="text/javascript"  charset="utf-8" src="${common.basepath}/common/plugins/laytpl/laytpl.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/examitem/dietary_frequency/dietary_frequency.js"></script>
<%@ include file="/page/examitem/dietary_frequency/dietary_frequency_model.jsp" %>
<script type="text/javascript">
$().ready(function(){
	questionnaire.init();
	
	var H = $(window).height()-30;
    $("body").height(H);
    $(window).resize(function(){
    	restBodyHeight();
    });
    
    $("#problem_div").delegate("input[type='radio']", "click", function(){
    	$("#downpro").click();
    });
	
	$("#downpro").click(function(){
		questionnaire.next();
	});
	$("#uppro").click(function(){
		questionnaire.previous();
	});
	$("#problem_div").delegate("input[option='relation']","click",function(){
		$("#sel"+this.id.substr(2,this.id.length)).attr("disabled",($(this).attr("checked")=="checked")?true:false);
		if($(this).attr("checked")=="checked"){
			$(this).attr("pid",questionnaire.problem.current.problemId);
		}
	});
	
	$("#problem_div").delegate("input[pid='p201']","click",function(){
		if($(this).attr("id")=="O20101"){
			var p202Index = _.findIndex(questionnaire.actualData, {
				problemId: "p202"
				});
			var p204Index = _.findIndex(questionnaire.actualData, {
				problemId: "p204"
				});
			var p205Index = _.findIndex(questionnaire.actualData, {
				problemId: "p205"
				});
			
			//记录删除的问题
			questionnaire.problem.removePid.push(["p202","p204","p205"]);
			//删除问题库中对应的问题
			questionnaire.actualData = _.without(questionnaire.actualData, questionnaire.actualData[p202Index],questionnaire.actualData[p204Index],questionnaire.actualData[p205Index]);
			
			var p202Result ;
			var p204Result ;
			var p205Result ;
		   do{
				p202Result = _.findIndex(questionnaire.result.data, {
					problemId: "p202"
					});
				//删除对应问题的回答结果
				if(p202Result!=-1){
					questionnaire.result.data = _.without(questionnaire.result.data, questionnaire.result.data[p202Result]);
				}
			}while (p202Result!=-1);
			   
		   do{
			   p204Result = _.findIndex(questionnaire.result.data, {
					problemId: "p204"
					});
				//删除对应问题的回答结果
			   if(p204Result!=-1){
					questionnaire.result.data = _.without(questionnaire.result.data, questionnaire.result.data[p204Result]);
				}
			}while (p204Result!=-1);
			   
		   do{
			   p205Result = _.findIndex(questionnaire.result.data, {
					problemId: "p205"
					});
				//删除对应问题的回答结果
			   if(p205Result!=-1){
					questionnaire.result.data = _.without(questionnaire.result.data, questionnaire.result.data[p205Result]);
				}
			}while (p205Result!=-1);
			
		}else{
			questionnaire.problem.removePid = _.without(questionnaire.problem.removePid,"p202","p204","p205");
			questionnaire.problem.getProblems(questionnaire.problem.removePid);
		}
	});
	
	$("#problem_div").delegate("input[pid='p301']","click",function(){
		if($(this).attr("id")=="O30101"){
			p302Index = _.findIndex(questionnaire.actualData, {
				problemId: "p302"
				});
			
			//记录删除的问题
			questionnaire.problem.removePid.push("p302");
			//删除问题库中对应的问题
			questionnaire.actualData = _.without(questionnaire.actualData, questionnaire.actualData[p302Index]);
			
			
			var p302Result;
			do{
				p302Result = _.findIndex(questionnaire.result.data, {
					problemId: "p302"
					});
				//删除对应问题的回答结果
				if(p302Result!=-1){
					questionnaire.result.data = _.without(questionnaire.result.data, questionnaire.result.data[p302Result]);
				}
			}while (p302Result!=-1);
		}else{
			questionnaire.problem.removePid = _.without(questionnaire.problem.removePid,"p302");
			questionnaire.problem.getProblems(questionnaire.problem.removePid);
		}
	});
	
	$("#problem_div").delegate("input[filter='disabled']","click",function(){
		if($(this).attr("pid")=="p109"){
			if($(this).attr("id")=="O10901"){
				$("input[filter='disabled']:not('#O10901')").attr("disabled",$(this).attr("checked")=="checked");
			}else{
				$("#O10901").attr("disabled",validateStatus($("input[filter='disabled']:not('#O10901')"),"checked"));
			}
		}
		if($(this).attr("pid")=="p601"){
			if($(this).attr("id")=="O60101"){
				$("input[filter='disabled']:not('#O60101')").attr("disabled",$(this).attr("checked")=="checked");
			}else{
				$("#O60101").attr("disabled",validateStatus($("input[filter='disabled']:not('#O60101')"),"checked"));
			}
		}
		if($(this).attr("pid")=="p401"){
			if($(this).attr("id")=="O40108"){
				$("input[filter='disabled']:not('#O40108')").attr("disabled",$(this).attr("checked")=="checked");
			}else{
				$("#O40108").attr("disabled",validateStatus($("input[filter='disabled']:not('#O40108')"),"checked"));
			}
		}
	});
	
	
	$("#problem_div").delegate("select[option='relation']","change",function(){
		$("#cb"+this.id.substr(3,this.id.length)).attr("disabled",($(this).find(":selected").attr("value")=="")?false:true);
		if(!$(this).find(":selected").attr("value")==""){
			$(this).attr("pid",questionnaire.problem.current.problemId);
		}
	});
	
	$("#problem_div").delegate("select[option='get']","change",function(){
		if($(this).val()!=0){
			$("#input"+this.id.substr(6,this.id.length)).removeAttr("disabled");
			$("#input"+this.id.substr(6,this.id.length)).attr("frequency",$(this).val());
		}else{
			$("#input"+this.id.substr(6,this.id.length)).attr("disabled",true);
			$("#input"+this.id.substr(6,this.id.length)).val("");
			$("#input"+this.id.substr(6,this.id.length)).attr("frequency",$(this).val());
		}
	}); 
	
	$("#problem_div").delegate("input[regexp]","blur",function(){
		if(!new RegExp($(this).attr("regexp")).test($(this).val())){
			$(this).val("");
			layer.alert("输入不合法");
// 			$(this).next().html("输入不合法");
		}else{
			$(this).next().html("");
		}
	});
	$("#problem_div").delegate("input[regexp][unit]","blur",function(){
		if(!new RegExp($(this).attr("regexp")).test($(this).val())){
			$(this).val("");
			layer.alert("输入不合法");
// 			$(this).next().html("输入不合法");
		}else{
			$(this).next().html($(this).attr("unit"));
			if(!common.isEmpty($(this).val())){
				$(this).attr("pid",questionnaire.problem.current.problemId);	
			}
		}
	});
});
formSubmint = function(){
	$("#problem_div").delegate("input,select", "keypress", function(event){
		if(event.which == 13){
			var elementTag = $(this).prop("nodeName").toLowerCase();
			if(elementTag=="input" || elementTag=="select"){
				$(this).attr("pid",questionnaire.problem.current.problemId);
			}
			$("#downpro").click();
		}
	});
	$("#mainId").removeAttr("hidden"); 
	$("#minorId").attr("hidden",true); 
};
clearData = function(){
	window.close();
};

/** 验证某表单的jquery对象中是否存在指定的属性值 */
function validateStatus(obj,status){
	var flag = false;
	if(_.isString(status)){
		$.each(obj,function(index,value){
			if(_.isObject(value)){
				if(!flag){
					flag = $(value).attr(status)==status;
				}
			}
		});
	}
	return flag;
}

</script>
</head>
<body class="promo">
<input id="inspectId" name="inspectId" type="hidden" value="${inspectId }"/>
<input id="recordId" name="recordId" type="hidden" value="${recordId }"/>
<input id="diagnosisId" name="diagnosisId" type="hidden" value="${diagnosisId }"/>
<div class="container-fluid" hidden="hidden" id="mainId">
	<div class="row row-top" style="margin-top: 120px;">
		<%-- <input type="hidden" id="questionAllocId" name="questionAllocId" value="${questionAllocId}" > --%>
		<div id="uppro" class="col-xs-2 text-center" style="cursor: pointer;padding-top: 50px;color: #ebccd1;">
			<i class="fa fa-chevron-left fa-4x"></i>
		</div>
		<div id="problem_div" class="col-xs-8">
		</div>
		<div id="downpro" class="col-xs-2 text-center" style="cursor: pointer;padding-top: 50px;color: #ebccd1;">
			<i class="fa fa-chevron-right fa-4x"></i>
		</div>
	</div>
</div>

<div id="minorId">
	<div class="row">
		<div class="col-xs-12 text-center" style="padding-top:20%;">
			<p style="font-size:30px;color:#fff;">确认是否开始答题？</p>
		</div>
		<div class="col-xs-12 text-center">
			<div class="col-xs-2 col-xs-offset-4">
				<button onclick="formSubmint()" type="button" class="btn btn-default btn-block" style="font-size:20px">开始答题</button>
			</div>
			<div class="col-xs-2">
				<button onclick="clearData()" type="button"  class="btn btn-default btn-block" style="font-size:20px" data-dismiss="modal">稍后再答</button>
			</div>
		</div>
	</div>
</div>
</body>
</html>