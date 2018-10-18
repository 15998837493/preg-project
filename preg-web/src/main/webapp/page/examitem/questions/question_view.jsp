<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/common/plugins/laytpl/laytpl.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/examitem/questions/question_view.js"></script>
<style type="text/css">
/* 其他后输入线样式 */
.underline {
    background-color: transparent;
    border: 0;
    border-bottom: solid 1px #7F9DB9;
}

/* 问题提示样式 */
.div_ins_question {
    color: #666666;
    padding-left: 15px;
    line-height: 18px;
    clear: both;
}
</style>
<!-- 单选题模板 -->
<script id="p1" type="text/html">
	{{# 
			if(question.param.flag==2){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum)+"-"+(question.param.currentgNum+1);
			}
			if(question.param.flag==1){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum+1);
			}
			if(question.param.flag==0){
				var order = (question.param.currentpNum+1)
			}
	 }}
<div class="col-xs-12 alert alert-danger"><h4>{{ order }}、{{ question.param.currentProblem.problemContent}}
		{{# if(question.param.currentProblem.problemRequired==question.param.problemRequired){  }}
			<span style="color: red;">*</span>
		{{# } }}
		</h4></div>
<div class="panel panel-danger">
		<div class="panel-body">
		{{# var optlength = question.param.currentProblem.optionVos.length ;}}
		{{# for(var i = 0; i <optlength; i++){ }}
		<div class="col-xs-12">
			<h5>
				<label class="radio-inline">
					<input ptype="radio" type="radio" name={{ question.param.currentProblem.problemId }}  pid={{ question.param.currentProblem.problemId }} id={{ question.param.currentProblem.optionVos[i].problemOptionId }} iswrite={{ question.param.currentProblem.optionVos[i].optionWrite }}
				   	{{# var answerContent= "";
						   var isChecked = false;
								 for(var j = 0; j <question.param.canswer.length; j++){
										isChecked = question.param.currentProblem.optionVos[i].problemOptionId==question.param.canswer[j].proptid;
												if(isChecked){
													answerContent = question.param.canswer[j].answerContent;
												}
								 			} 
									  if(question.param.currentProblem.optionVos[i].optionDefault==question.param.optionDefault&&question.param.canswer.length==0){
												isChecked = true;
										}
								}}
								{{ isChecked?"checked":"" }}
					 >
					{{ question.param.currentProblem.optionVos[i].optionContent }}
					{{# if(question.param.currentProblem.optionVos[i].optionWrite==question.param.optionWrite ){ }}
						<input type="text" style="max-width: 500px; color: rgb(153, 153, 153); position: static;" id={{ question.param.currentProblem.optionVos[i].problemOptionId+question.param.iswrite }}  optionId={{ question.param.currentProblem.optionVos[i].problemOptionId }} class="underline"  onfocus="question.optionChecked(this)" value={{ answerContent }} ></input>
					{{# } }}
				</label>
			</h5>
		</div>
		{{# }
			 if(!common.isEmpty(question.param.currentProblem.problemHint)){ }}
				<div class="col-xs-12"><hr style="height:2px;" ></div>
				<div class="div_ins_question">提示：{{question.param.currentProblem.problemHint}}</div>
		{{# } }}
</div>
</div>
</script>
<!-- 多选题模板 -->
<script id="p2" type="text/html">
	{{# 
			if(question.param.flag==2){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum)+"-"+(question.param.currentgNum+1);
			}
			if(question.param.flag==1){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum+1);
			}
			if(question.param.flag==0){
				var order = (question.param.currentpNum+1)
			}
	 }}
		<div class="col-xs-12 alert alert-danger"><h4>{{ order }}、{{ question.param.currentProblem.problemContent}}
		{{# if(question.param.currentProblem.problemRequired==question.param.problemRequired){  }}
		<span style="color: red;">*</span>
		{{# } }}
		</h4></div>
<div class="panel panel-danger">
		<div class="panel-body">
		{{# var optlength = question.param.currentProblem.optionVos.length ;
				var count = question.param.cbrownum;
				var row = 0;
		}}
		{{# while (optlength>0){
			 if(optlength<question.param.cbrownum){
				count = optlength;
			} 
			for(var i = 0; i <count; i++){ }}
					<div class="col-xs-6">
						<h5>
							<label class="checkbox-inline">
								<input ptype="checkbox" type="checkbox" name={{ question.param.currentProblem.problemId }} pid={{ question.param.currentProblem.problemId }} id={{ question.param.currentProblem.optionVos[row*question.param.cbrownum+i].problemOptionId }} iswrite={{ question.param.currentProblem.optionVos[row*question.param.cbrownum+i].optionWrite }}
									{{# var answerContent= "";
										   var isChecked = false;
										   var times = 0;
										  for(var j = 0; j <question.param.canswer.length; j++){
												if(question.param.currentProblem.optionVos[row*question.param.cbrownum+i].problemOptionId==question.param.canswer[j].proptid){
													answerContent = question.param.canswer[j].answerContent;
													times++;
												}
								 			} 
									  if(question.param.currentProblem.optionVos[row*question.param.cbrownum+i].optionDefault==question.param.optionDefault&&question.param.canswer.length==0){
												isChecked = true;
										}
									  if(times>0){
												isChecked = true;
										}
								}}
								{{ isChecked?"checked":"" }}
								>&nbsp;{{ question.param.currentProblem.optionVos[row*question.param.cbrownum+i].optionContent }}

								{{# if(question.param.currentProblem.optionVos[row*question.param.cbrownum+i].optionWrite==question.param.optionWrite ){ }}
									<input type="text" style="max-width: 500px; color: rgb(153, 153, 153); position: static;" id={{ question.param.currentProblem.optionVos[row*question.param.cbrownum+i].problemOptionId+question.param.iswrite }}  optionId={{ question.param.currentProblem.optionVos[row*question.param.cbrownum+i].problemOptionId }} onfocus="question.optionChecked(this)" class="underline" value= {{ answerContent }} ></input>
								{{# } }}
			       		</label>
						</h5>
					</div>
			{{# } }}
			<br>
			{{# optlength= optlength-question.param.cbrownum;
					row++;
		 	}}
		{{# } }}
		{{#  if(!common.isEmpty(question.param.currentProblem.problemHint)){ }}
			<div class="col-xs-12"><hr style="height:2px;" ></div>
			<div class="div_ins_question">提示：{{question.param.currentProblem.problemHint}}</div>
		{{# } }}
		</div>
</div>
</script>
<!-- 是非题模板 -->
<script id="p4" type="text/html">
			{{# 
			if(question.param.flag==2){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum)+"-"+(question.param.currentgNum+1);
			}
			if(question.param.flag==1){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum+1);
			}
			if(question.param.flag==0){
				var order = (question.param.currentpNum+1)
			}
	 		}}
				<div class="col-xs-12 alert alert-danger"><h4>{{ order }}、{{ question.param.currentProblem.problemContent}}
					{{# if(question.param.currentProblem.problemRequired==question.param.problemRequired){  }}
						<span style="color: red;">*</span>
					{{# } }}
				</h4></div>
				<div class="panel panel-danger">
		<div class="panel-body">
				<div class="col-xs-12">
					<h5>
						<label class="radio-inline">
							<input ptype="boolean" type="radio" name={{ question.param.currentProblem.problemId }} pid={{ question.param.currentProblem.problemId }} id={{ question.param.currentProblem.optionVos[0].problemOptionId }} iswrite={{ question.param.currentProblem.optionVos[0].optionWrite }}
								{{# var answerContent= "";
									   var isChecked = false;
										  for(var j = 0; j <question.param.canswer.length; j++){
												var isChecked = question.param.currentProblem.optionVos[0].problemOptionId==question.param.canswer[j].proptid;
												if(isChecked){
													answerContent = question.param.canswer[j].answerContent;
												}
										 }
									  if(question.param.currentProblem.optionVos[0].optionDefault==question.param.optionDefault&&question.param.canswer.length==0){
												isChecked = true;
										}
								 }}
								{{isChecked?"checked":"" }}
								>
								{{ question.param.currentProblem.optionVos[0].optionContent}}  
								{{# if(question.param.currentProblem.optionVos[0].optionWrite==question.param.optionWrite ){ }}
									<input type="text" style="max-width: 500px; color: rgb(153, 153, 153); position: static;" id={{ question.param.currentProblem.optionVos[0].problemOptionId+question.param.iswrite }} optionId={{ question.param.currentProblem.optionVos[0].problemOptionId }} onfocus="question.optionChecked(this)" class="underline" value= {{ answerContent }}></input>
								{{# } }}
						</label>
					</h5>
				</div>
				<div class="col-xs-12">
					<h5>
						<label class="radio-inline">
							<input ptype="boolean" type="radio" name={{ question.param.currentProblem.problemId }} pid={{ question.param.currentProblem.problemId }} id={{ question.param.currentProblem.optionVos[1].problemOptionId }}  iswrite={{ question.param.currentProblem.optionVos[1].optionWrite }}
								{{# 
										  for(var j = 0; j <question.param.canswer.length; j++){
												var isChecked = question.param.currentProblem.optionVos[1].problemOptionId==question.param.canswer[j].proptid;
												if(isChecked){
													answerContent = question.param.canswer[j].answerContent;
												}
								 		  } 
										if(question.param.currentProblem.optionVos[1].optionDefault==question.param.optionDefault&&question.param.canswer.length==0){
												isChecked = true;
										}
								}}							
								{{isChecked?"checked":"" }}
								>{{ question.param.currentProblem.optionVos[1].optionContent }}
								{{# if(question.param.currentProblem.optionVos[1].optionWrite==question.param.optionWrite ){ }}
									<input type="text" style="max-width: 500px; color: rgb(153, 153, 153); position: static;" id={{ question.param.currentProblem.optionVos[0].problemOptionId+question.param.iswrite }} optionId={{ question.param.currentProblem.optionVos[0].problemOptionId }} onfocus="question.optionChecked(this)" class="underline" value={{ question.param.canswer.answerContent }}></input>
								{{# } }}
						</label>
					</h5>
				</div>
				{{#  if(!common.isEmpty(question.param.currentProblem.problemHint)){ }}
					<div class="col-xs-12"><hr style="height:2px;" ></div>
					<div class="div_ins_question">提示：{{question.param.currentProblem.problemHint}}</div>
				{{# } }}
</div>
</div>
</script>
<!-- 简答题模板 -->
<script id="p3" type="text/html">
			{{# 
			if(question.param.flag==2){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum)+"-"+(question.param.currentgNum+1);
			}
			if(question.param.flag==1){
				var order = (question.param.currentpNum+1)+"-"+(question.param.currentcNum+1);
			}
			if(question.param.flag==0){
				var order = (question.param.currentpNum+1)
			}
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ order }}、
		{{# var content = question.param.currentProblem.problemContent.split(new RegExp('#',"gm"));
			for(var i=0;i<content.length-1;i++){ }}
					{{ content[i] }}<input id={{ question.param.currentProblem.optionVos[i].problemOptionId}} pid={{ question.param.currentProblem.problemId }} ptype='text' type='text' size='20' value={{ question.param.canswer[i]==undefined?"":question.param.canswer[i].answerContent }}>
		{{#
			}
		}}
	{{ content[content.length-1] }}
	{{# if(question.param.currentProblem.problemRequired==question.param.problemRequired){  }}
			<span style="color: red;">*</span>
	{{# } }}
	</h4></div>
<div class="panel panel-danger">
	{{#  if(!common.isEmpty(question.param.currentProblem.problemHint)){ }}
			<div class="col-xs-12"><hr style="height:2px;" ></div>
			<div class="div_ins_question">提示：{{question.param.currentProblem.problemHint}}</div>
	{{# } }}
</div>
</script>
<script type="text/javascript">
$(function(){
	var H = $(window).height()-30;
    $("body").height(H);
    $(window).resize(function(){
    	restBodyHeight();
    });
    
    $("#problem_div").delegate("input[type='radio']", "click", function(){
    	$("#downpro").click();
    });
});

formSubmint = function(){
	$(window).on("keypress", function(event){
		if(event.which == 13){
			$("#downpro").click();
		}
	});
	
	$("#mainId").removeAttr("hidden"); 
	$("#minorId").prop("hidden",true); 
	setDH();
};
clearData = function(){
	window.close();
};
</script>
</head>
<body class="promo">
<div class="container-fluid" hidden="hidden" id="mainId">
	<div class="row" style="padding-top:10%;">
		<input type="hidden" id="questionAllocId" name="questionAllocId" value="${questionAllocId}">
		<!-- <div>选择问题：
			<select>
				<option></option>
			</select>
		</div> -->
		<div id="uppro" class="col-xs-2 text-center" style="cursor: pointer;padding-top: 50px;color: #ebccd1;">
			<i class="fa fa-chevron-left fa-4x"></i>
		</div>
		<div id="problem_div" class="col-xs-8">
			<div id="parent_problem_div" class="col-xs-12"></div>
		</div>
		<div id="downpro" class="col-xs-2 text-center" style="cursor: pointer;padding-top: 50px;color: #ebccd1;">
			<i class="fa fa-chevron-right fa-4x"></i>
		</div>
	</div>
</div>
<div id="minorId">
	<div class="row">
		<div class="col-xs-12 text-center" style="padding-top:20%;">
			<p style="font-size:30px;color:#fff;">一共${problemNo}道题，是否开始答题？</p>
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
<form id="initForm" action="" method="post">
	<input type="hidden" name="questionId" value="${questionId}">
	<input id="diagnosisId" name="diagnosisId" type="hidden" value="${diagnosisId }"/>
	<input id="inspectId" name="inspectId" type="hidden" value="${inspectId }"/>
	<input type="hidden" id="sex" name="sex" value="${sex}">
</form>
</body>
</html>