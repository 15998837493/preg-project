<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>指标录入</title>
<style type="text/css">
.report{
	border-bottom: 1px solid #337ab7;
    padding-bottom: 30px;
}
.report .panel-body{
    padding:7px;
    border-top: 1px solid #ddd;
}
.report .padding-zero{
   padding:0px;
}
.report .redClass{
   color:red;
}
.report  .input-group-addon{
	color: #fff;
    background-color: #337ab7;
    border-color: #2e6da4;
}
.footerClass{
	cursor:pointer;
    font-weight: bold;
    color: #0C78D5;
    font-size: 15px;
    border-top: 1px solid #337ab7;
}
.hiddenClass{
	display:none;
}
.bodyClass{
	overflow-x: hidden;
	max-height: 720px;
}
.report table tr td a{
    text-decoration: none;
}
.report table tr td input{
    height:28px;
}
.intake-sm{margin:0;width:100%;}
.report .form-control[disabled]{
    background-color: #fff;
}
</style>
<script type="text/javascript">
<%-- 页面初始化对象 --%>
// 已添加报告信息
var reportList = ${reportList};
//收费项目--基础数据
var hospitalInspectPayList = ${hospitalInspectPayList};
var inspectMapListData = [];
// 添加收费项目数据
if(!_.isEmpty(hospitalInspectPayList)){
	$.each(hospitalInspectPayList,function(index,value){
		inspectMapListData.push({name:value.inspectName, val:value});
	});
}
// 问诊的辅助项目信息
var lastQuotaList = ${lastQuotaList};
//接诊id
var diagnosisId = "${diagnosisId}";
//末次月经、预产期
var d_lmp = "${preArchive.lmp }";
var d_due = "${preArchive.pregnancyDueDate }";
</script>
<script type="text/javascript" src="${common.basepath}/page/evaluate/receive/receive_quota.js"></script>
</head>
<div class="panel panel-lightblue col-xs-12 no-left padding-zero" id="saveQuota_info_div">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 实验室指标信息</div>
	<div class="panel-body form-inline">
		<div class="col-xs-8 padding-zero">
		<button class="btn btn-lightblue" type="button" onclick="getLastReportList();">
			<i class="fa fa-history fa-fw"></i> 上次检验项目
		</button>
		</div>
		<div class="col-xs-4 text-right padding-zero">
			<button class="btn btn-primary" type="button" onclick="sendItemInfo();">
				<i class="fa fa-send-o fa-fw"></i> 发送
			</button>
		</div>
	</div>
	<!-- 医生是1,助手是2 -->
	<input type="hidden" name="status" value='2'/>
	<!-- 收费项目信息 -->
	<div class="bodyClass" style="border-top: 1px solid #337ab7;"></div>
	<div class="panel-body text-center footerClass" style="padding: 13px;" id="fotter" onclick="addReport();">+ 添加检验报告</div>
</div>

<!-- 上次检验项目Modal -->
<div id="lastDiagnosisInspectsModal" class="modal fade bs-example-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i> 上次检验项目
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="form-group row-top">
					        <div class="col-xs-8 col-xs-offset-2">
					        	<input id="newReportName" class="form-control" type="text" placeholder="检验报告名称"/>
					        	<table class="table table-bordered table-padding-4" style="margin-top: 5px;" id="lastInspects"></table>
					        </div>
		              	</div>
					</div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-3 col-xs-offset-9 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="saveReportFromlastInspects();"><i class="fa fa-save fa-fw"></i>生成检验报告</button>
					</div>
				</div>
			</div>
 		</div><!--/.modal-content -->
 	</div><!--/.modal-dialog -->
</div><!-- /.modal -->
