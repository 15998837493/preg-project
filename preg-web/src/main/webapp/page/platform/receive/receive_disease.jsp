<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>诊断</title>
<style type="text/css">
.mytable {   
   table-layout: fixed;   
   width: 98% border:0px;   
   margin: 0px;
}
.mytable tr td {
	text-overflow: ellipsis;   
    overflow: hidden;   
    white-space: nowrap;   
    border: 1px solid;   
}

/* 实现两个元素两端对齐，两个元素之间必须要有一个空格或者其他分隔符，此类在其父元素。 */
.JustifyAlign {
     text-align:justify;
     text-align-last:justify;
     height: 31px;
     border-bottom: 1px solid #ddd !important;
     border-right: 1px solid #ddd !important;
     border-left: 0px;
     border-top: 0px;
	 border-collapse: collapse;
	 border-spacing: 0;
	 padding: 4px;
 	 line-height: 1.42857143;
	 vertical-align: middle;
}

.JustifyAlign a{
	text-decoration:none;
}
.noData {
	text-align: center;
	margin-top: 10px;
	margin-bottom: 10px;
	font-family: inherit;
	font-weight: 500;
	line-height: 1.1;
	color: inherit;
	height: 40px;
	padding: 2px;
}
.disease-sure-btn{
	color:black;
	height:23px;
	padding:0px 5px 0px 5px;
}
</style>
<script type="text/javascript">
var diagnosisJson = ${diagnosisJson};
// 所有诊断项目
var interveneDiseaseList = ${interveneDiseaseList};
// 历史诊断项目
var diagnosisHistoryList = ${diagnosisHisList};
// 当前的诊断项目
var diagnosisDiseaseNow = ${diagnosisDiseaseNow};
</script>
<script charset="UTF-8" src="${common.basepath}/page/platform/receive/receive_disease.js"></script>
</head>

<!-- 诊断项目选择与配置 -->
<div class="panel panel-lightblue col-xs-6 padding-zero" style="height: 333px;">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 诊断项目</div>
	<div class="table-responsive" style="overflow-y: auto; height: 248px;">
		<table class="table table-bordered table-padding-4">
			<thead>
				<tr class="active">
					<th class="text-center" style="width: 35%;">诊断名称</th>
					<th class="text-center" style="width: 25%;">ICD10编码</th>
					<th class="text-center" style="width: 20%;">诊断状态</th>
					<th class="text-center" style="width: 20%;">操作</th>
				</tr>
			</thead>
			<tbody id="diagnosis_disease_tbody" class="table-bordered">
			</tbody>
		</table>
	</div>
	<div class="panel-body form-inline" style="padding: 5px;">
		<input type='text' class='form-control' placeholder='请输入检索内容' id="diagnosisAddDisease" />
		<label class="checkbox-inline">
			<input type="checkbox" id="daiquezhen" value="1" />待确诊
		</label>
		<button class="btn btn-lightblue" type="button" onclick="saveDiagnosisDiseaseButton();">
			<i class="fa fa-plus fa-fw"></i> 添加
		</button>
	</div>
</div>

<!-- 历次诊断 -->
<div class="panel panel-lightblue col-xs-6 padding-zero" style="height: 333px;border-left: 0px;">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 历史诊断项目</div>
	<div class="table-responsive" style="overflow-y: auto; height: 295px;">
		<table class="table table-bordered mytable table-padding-4">
			<thead>
				<tr class="active">
					<th class="text-center" style="width: 8em !important;">日期</th>
					<th class="text-center" style="width: 8em !important;">孕周数</th>
					<th class="text-center" style="width: 8em !important;">医生</th>
					<th class="text-center" style="width: 100%">诊断</th>
				</tr>
			</thead>
			<tbody id="diagnosis_disease_history_tbody" class="table-bordered"></tbody>
		</table>
	</div>
</div>

<!-- 诊断常用模板 -->
<div class="panel panel-lightblue col-xs-12 no-left padding-zero">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 常用诊断项目</div>
	<div class="table-responsive" style="overflow-y: auto; max-height: 185px;">
		<div class="table table-bordered" id="allDiseaseListTable">
			
		</div>
	</div>
	<div class="panel-body form-inline" style="padding: 5px;">
		<input type='text' class='form-control' placeholder='请输入检索内容' id="addDiseaseOften" maxlength="20" />
		<button class="btn btn-lightblue" type="button" onclick="diseaseOftenOperation.addCustom()">
			<i class="fa fa-plus fa-fw"></i> 添加
		</button>
	</div>
</div>