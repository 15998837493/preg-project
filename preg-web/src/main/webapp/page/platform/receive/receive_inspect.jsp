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
.label-checkbox{
	position: relative;
	display: inline-block;
	padding-left: 26px;
	margin-bottom: 0;
	vertical-align: middle;
	font-weight: normal;
	cursor: pointer;
}
#inspect-checkbox{
	position: absolute;
	margin-left: -18px;
}
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
<script type="text/javascript" src="${common.basepath}/page/platform/receive/receive_inspect.js"></script>
<!-- 评价项目 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 营养评价项目信息
	</div>
	<!-- 评价项目--搜索区 -->
	<div class="panel-body">
		<div class="col-xs-6 padding-zero">
			<div class="form-inline">
				<input id="receiveInspectItem" type="text" class="form-control" style="min-width: 200px;" placeholder="请输入营养评价项目名称"/>
			</div>
		</div>
		<div class="col-xs-6 text-right padding-zero">
			<button class="btn btn-lightblue" onclick="showDiagnosisInspectModal();"><i class="fa fa-edit fa-fw"></i> 随诊默认项目</button>
			<button class="btn btn-primary" onclick="deleteInspectButton();"><i class="fa fa-trash-o fa-fw"></i> 删除</button>
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
<form id="dietReviewForm" action="${common.basepath }/${applicationScope.URL.DietFood.DIET_REVIEW}" class="form-horizontal" method="post">
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
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="submit" class="btn btn-primary btn-block">保存</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<!-- 默认项目Modal -->
<div id="vistInspectFollow" class="modal fade bs-example-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i> 编辑默认项目
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="form-group row-top">
					        <div class="col-xs-8 col-xs-offset-2">
					        	<input id="inspectItemFollow" class="form-control" type="text" placeholder="请输入营养评价项目"/>
					        	<table class="table table-bordered table-padding-4" style="margin-top: 5px;" id="inspectTableFollow"></table>
					        </div>
		              	</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="saveUserInspectItem();"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>
			</div>
 		</div><!--/.modal-content -->
 	</div><!--/.modal-dialog -->
</div><!-- /.modal -->

<!-- 发送评价项目Modal -->
<div id="sendInspectModal" class="modal fade bs-example-modal" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i> 提示信息
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body" style="padding: 30px;" id="sendQitBody"></div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="button" onclick="sendInspectButton();" class="btn btn-primary btn-block"><i class="fa fa-send-o fa-fw"></i> 发送</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 默认项目Modal -->
<div id="diagnosisInspectModal" class="modal fade bs-example-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i> 编辑默认项目
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="form-group row-top">
					        <div class="col-xs-8 col-xs-offset-2">
					        	<input id="diagnosisInspect" class="form-control" type="text" placeholder="请输入营养评价项目"/>
					        	<table class="table table-bordered table-padding-4" style="margin-top: 5px;" id="diagnosisInspectTable"></table>
					        </div>
		              	</div>
					</div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="saveUserInspectItem();"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>
			</div>
 		</div><!--/.modal-content -->
 	</div><!--/.modal-dialog -->
</div><!-- /.modal -->
