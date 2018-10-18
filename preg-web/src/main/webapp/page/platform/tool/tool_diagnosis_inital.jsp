<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>孕期初诊建档--${custName}</title>
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/common/plugins/laytpl/laytpl.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/platform/tool/tool_diagnosis_inital.js"></script>
<style type="text/css">
/* 其他后输入线样式 */
.underline {
	background-color: rgb(51, 122, 183);
	border: 0;
	border-bottom: solid 1px #7F9DB9;
}

/* 问题提示样式 */
.div_ins_question {
	padding-top: 8px;
	color: #666666;
	padding-left: 20px;
	line-height: 18px;
	clear: both;
}
</style>
<!-- 单选题模板 -->
<script id="p1" type="text/html">
		<div class="row" style="display:{{question.currentProblem.displayFlag}}" id="flag{{question.currentProblem.problemId}}">
					<div><h5><a name="a_question_index_{{question.currentProblem.problemId}}"></a><span id="title{{question.currentProblem.problemId}}">{{ question.currentProblem.orderNo }}</span>、{{ question.currentProblem.problemContent}} {{# if(question.currentProblem.problemRequired==1 || question.currentProblem.problemRequired==2){ }}<span style="color:red;">（必答题）</span>{{# } }}</h5>
					{{# var optlength = question.currentProblem.optionVos.length ;}}
					{{# for(var i = 0; i <optlength; i++){ }}
							<label class="radio-inline">
								<input ptype="radio" onchange="question.setRadioHiden('{{ question.currentProblem.optionVos[i].problemOptionId }}','{{ question.currentProblem.optionVos[i].childProblemIds }}')" type="radio" name={{ question.currentProblem.problemId }}  pid={{ question.currentProblem.problemId }} id={{ question.currentProblem.optionVos[i].problemOptionId }} childId={{ question.currentProblem.optionVos[i].childProblemIds }} value={{ question.currentProblem.optionVos[i].problemOptionId }}
							 	/>
								{{ question.currentProblem.optionVos[i].optionContent }}
							</label>
				{{# } }}
					</div>
		<hr>
		</div>
		
</script>
<!-- 多选题模板 -->
<script id="p2" type="text/html">
		<div class="row" style="display:{{question.currentProblem.displayFlag}}" id="flag{{question.currentProblem.problemId}}">
				<div><h5><a name="a_question_index_{{question.currentProblem.problemId}}"></a><span id="title{{question.currentProblem.problemId}}">{{ question.currentProblem.orderNo }}</span>、{{ question.currentProblem.problemContent}} {{# if(question.currentProblem.problemRequired==1 || question.currentProblem.problemRequired==2){ }}<span style="color:red;">（必答题）</span>{{# } }}</h5></div>
				{{# var optlength = question.currentProblem.optionVos.length ;
						var count = 3;
						var row = 0;
				}}
				{{# while (optlength>0){
					 if(optlength<4){
						count = optlength;
					} 
					for(var i = 0; i <count; i++){ }}
							<div class="col-xs-4">
									<label class="checkbox-inline">
										<input ptype="checkbox"  onchange="question.setCheckBoxHiden('{{ question.currentProblem.optionVos[row*3+i].problemOptionId }}','{{ question.currentProblem.optionVos[row*3+i].childProblemIds }}')"  type="checkbox" name={{ question.currentProblem.optionVos[row*3+i].problemOptionId }} pid={{ question.currentProblem.problemId }}  id={{ question.currentProblem.optionVos[row*3+i].problemOptionId }}
										>&nbsp;{{ question.currentProblem.optionVos[row*3+i].optionContent }}
					       			</label>
							</div>
					{{# } }}
					<br>
					{{# optlength= optlength-3;
							row++;	
				 	}}
				{{# } }}
		<hr>
	</div>
</script>
<!-- 是非题模板 -->
<script id="p4" type="text/html">
		{{# 
		var displayFlag="block";
		if(question.currentProblem.child){
		displayFlag="none"
		}
		}}	
		<div  style="display:{{displayFlag}}" id="flag{{question.currentProblem.problemId}}">
						<div class="col-xs-12 row-top"><h5><a name="a_question_index_{{question.currentProblem.problemId}}"></a>{{ question.currentProblem.orderNo }}、{{ question.currentProblem.problemContent}}
						 {{# if(question.currentProblem.problemRequired==1 || question.currentProblem.problemRequired==2){ }}<span style="color:red;">（必答题）</span>{{# } }}
						</h5></div>
						<div class="col-xs-12 row-top">
							<h5>
								<label class="radio-inline">
									<input ptype="boolean" type="radio" name={{ question.currentProblem.problemId }} pid={{ question.currentProblem.problemId }} id={{ question.currentProblem.optionVos[0].problemOptionId }}}}
										>
										{{ question.currentProblem.optionVos[0].optionContent}}  
								</label>
							</h5>
						</div>
						<div class="col-xs-12 row-top">
							<h5>
								<label class="radio-inline">
									<input ptype="boolean" type="radio" name={{ question.currentProblem.problemId }} pid={{ question.currentProblem.problemId }} id={{ question.currentProblem.optionVos[1].problemOptionId }} }}
										>{{ question.currentProblem.optionVos[1].optionContent }}
								</label>
							</h5>
						</div>
		</div>
</script>
<!-- 简答题模板 -->
<script id="p3" type="text/html">
		{{#
       var num="◆    ";
		if(!common.isEmpty(question.currentProblem.orderNo)){
		num=question.currentProblem.orderNo+"、";
		}
		}}
		<div class="row" style="display:{{question.currentProblem.displayFlag}}" id="flag{{question.currentProblem.problemId}}">
			<div><a name="a_question_index_{{question.currentProblem.problemId}}"></a><span id="title{{question.currentProblem.problemId}}">{{ question.currentProblem.orderNo }}</span>、
				{{# var content = question.currentProblem.problemContent.split(new RegExp('#',"gm"));
					for(var i=0;i<content.length-1;i++){ }}
					 {{ content[i] }}<input class='{{ question.currentProblem.optionVos[i].optionClass}}' id='{{ question.currentProblem.optionVos[i].problemOptionId}}' name={{question.currentProblem.optionVos[i].problemOptionId}} pid={{ question.currentProblem.problemId }} ptype='text' type='text' size='20' style="border-color: #878787;border-style: solid;border-top-width: 0px;border-right-width: 0px;border-bottom-width: 1px;border-left-width: 0px;" onblur="validate(this.id,this.value,{{ question.currentProblem.optionVos[i].optionValidate}})">
				{{#
					}
				}}
			{{ content[content.length-1] }}
			 {{# if(question.currentProblem.problemRequired==1 || question.currentProblem.problemRequired==2){ }}<span style="color:red;">（必答题）</span>{{# } }}
			</div>
		<hr>
		</div>
		
</script>
<script type="text/javascript">
var openerType = '${openerType}';
</script>
<body>
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-heading">
					<h4 class="timeline-title">孕期初诊建档</h4>
				</div>
				<div class="timeline-body">
					<form id="initForm" action="" method="post">
						<div class="panel panel-lightblue">
							<div class="panel-heading text-center">初诊问卷</div>
							<div class="timeline-body">
								<div id="guide_customerinfo_div">
									<div id="problem_div">
										<div id="parent_problem_div" style="margin-left: 22px;margin-right: 22px;"></div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-2 col-xs-offset-4" style="margin-bottom: 20px;">
							<button class="btn btn-primary btn-block" type="button" id="saveDiagnosisInfo" name="saveDiagnosisInfo"
								onclick="saveQuestion()">
								<i class="fa fa-save fa-fw"></i> 保存
							</button>
						</div>
						<div class="col-xs-2" style="margin-bottom: 10px;">
							<button class="btn btn-primary btn-block" type="button" id="saveDiagnosisInfo" name="saveDiagnosisInfo"
								onclick="window.close()">
								<i class="fa fa-power-off fa-fw"></i> 关闭
							</button>
						</div>
						<input id="diagnosisId" name="diagnosisId" type="hidden" value="${diagnosisId }" />
						<input id="questionAllocId" name="questionAllocId" type="hidden" value="${questionAllocId }" />
						<input name="questionId" type="hidden" value='C000000CA00000000006' />
					</form>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
<!-- 永久诊断项目 -->
<!-- 营养病历--打印营养病历报告 -->
<div id="diseaseModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa  fa-check-square-o"></i> 选择永久诊断项目
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body form-horizontal">
						<div class="col-xs-12" id="diseaseCheckbox">
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="getDisease();" style="margin-left:145px;">确认</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>