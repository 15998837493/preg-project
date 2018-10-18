<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<style>
.padding-zero{
	padding: 0px;
}
.margin-zero{
	margin: 0px;
}
.btn-group-right{
	margin-right: 10px;
}
.btn{
	padding: 6px 9px;
}
/* 检查项目鼠标悬浮 */
button {
	height: 34px;
}
#diagnosis_item_btns button .hovershow {
	display: none;
}
#diagnosis_item_btns button:HOVER > .hovershow {
	display: block;
}
#diagnosis_item_btns button:HOVER > .hoverhide {
	display: none;
}
#a:hover > #b:first-child{color : #FF0000;}   
</style>
<script type="text/javascript">
// 所有评价项目
var inspectAllListJson = ${inspectAllListJson};
var inspectListData = [];
if(!_.isEmpty(inspectAllListJson)){
	$.each(inspectAllListJson, function(index, value){
		inspectListData.push({name:value.inspectName, val:value});
	});
}

// 随诊默认评价项目
var userInspectListJson = ${userInspectListJson};

// 接诊已选评价项目
var diagnosisInspectMap = new Map();
var diagnosisItemList = ${diagnosisItemList};
if(!_.isEmpty(diagnosisItemList)){
	$.each(diagnosisItemList, function(index, value){
		diagnosisInspectMap.set(value.inspectCode, value);
	});
}

</script>
<script type="text/javascript" src="${common.basepath}/page/evaluate/receive/receive_inspect.js"></script>
<!-- 评价项目 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 营养评价项目信息
	</div>
	<!-- 评价项目--搜索区 -->
	<div class="panel-body">
		<div class="col-xs-12 text-right padding-zero">
			<button class="btn btn-primary" onclick="sendInspect();"><i class="fa fa-send-o fa-fw"></i> 发送</button>
		</div>
	</div>
	<!-- 评价项目--提示区 -->
	<div class="panel-body" id="diagnosis_item_qits" style="border-top: 1px solid #ddd; display: none;"></div>
	<!-- 评价项目--操作区 -->
	<div class="panel-body" id="diagnosis_item_btns" style="border-top: 1px solid #ddd;">
		<div id="noButtonDiv"><font color='gray'>请先选择评价项目！</font></div>
	</div>
	<!-- 评价项目--结论区 -->
	<table class="table table-bordered margin-zero" id="resultTable">
	</table>
</div>

<!-- 膳食日记回顾Modal -->
<form id="dietReviewForm" name="dietReviewForm" action="${common.basepath }/${applicationScope.URL.DietFood.DIET_REVIEW}" class="form-horizontal" method="post">
<div id="dietReviewModal" class="modal fade bs-example-modal" data-backdrop="static">
	<div class="modal-dialog modal-lg" style="margin-top: 150px;">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue" style="border: 0px;">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i> 膳食日记回顾
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body" style="padding: 0px; margin-top: 2px;">
						<input type="hidden" name="diagnosisId" value="${diagnosis.diagnosisId }"/>
						<input type="hidden" id="dietReviewId" name="dietReviewId"/>
						<input type="hidden" id="dietReviewCode" name="dietReviewCode"/>
						<textarea id="dietReviewText" name="dietReviewText"
				        	class="form-control"
				        	placeholder="请输入膳食日记回顾内容……" 
				        	style="background: white; height: 200px; overflow-y: scroll; border-radius: 0px;" maxlength="1000"></textarea>
					</div>
				</div>
				<div id="dietReviewButtonBody" class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="submit" class="btn btn-primary btn-block">保存</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<div id="assessModal" class="modal fade bs-example-modal form-horizontal">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content">
			<div class="container-fluid">
				<div class="row">
					<ul id="timeline">
						<li class="timeline-inverted">
							<div class="timeline-panel" id="timeline-panel">
								<div class="timeline-heading">
								</div>
								<div class="timeline-body form-horizontal">
									<div class="panel panel-lightblue no-bottom">
										<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 剂量评估结果<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
										<div class="table-responsive" style="max-height: 750px;">
											<table class="table table-bordered table-hover table-padding-2" id="elementTable">
											</table>
										</div>
									</div>
									<div class="panel-body no-padding">
										<input type="hidden" id="diagnosisInspectId" />
										<div class="col-xs-2 col-xs-offset-8 no-padding"><select class="form-control" id="pdf_taking_cyle" multiple="multiple"></select></div>
										<div class="col-xs-1"><label class="control-label radio-inline"><input type="radio" id="selectAllButton" checked onclick="selectAll();"> 全部</label></div>
										<div class="col-xs-1 no-padding"><button id="assessExtenderPdf" onclick="getAssessExtenderPdf();" type="button" name="extenderAssessButton" class="btn btn-primary btn-block">打印报告</button></div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
