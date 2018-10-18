<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>历史检验项目</title>
<script type="text/javascript" src="${common.basepath}/page/platform/receive/receive_quota_history.js"></script>
</head>
<div class="panel panel-lightblue col-xs-12 padding-zero">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 历史检验报告</div>
	<div class="panel-body" style="padding: 7px 0 0 0;">
		<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#tab-report-list" data-toggle="tab">检验报告列表</a></li>
			<li><a href="#tab-item" data-toggle="tab">查询检验项目</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			
			<!-- 检验报告列表tab -->
			<div class="tab-pane fade in active" id="tab-report-list">
				
				<!-- 检验报告检索展示区 -->
				<div class="panel-body form-inline" id="customerCondition" style="padding: 7px;">
					<form id="historyReportForm" method="post" class="form-horizontal">
						<input type="hidden" id="historyReportCustId" name="custId">
						<div class="input-group">
				    		<input id="historyDiagnosisDate" name="diagnosisDate" class="form-control" placeholder="请选择接诊日期" readonly/>
				    		<span class="input-group-btn">
					        	<button class="btn btn-default" type="button" onclick="common.dateShow('historyDiagnosisDate')"><i class="fa fa-calendar fa-fw"></i>选择</button>
					      	</span>
				    	</div>
				    	<div class="input-group">
				    		<input id="historyReportDate" name="reportDate" class="form-control" placeholder="请选择检验日期" readonly/>
				    		<span class="input-group-btn">
					        	<button class="btn btn-default" type="button" onclick="common.dateShow('historyReportDate')"><i class="fa fa-calendar fa-fw"></i>选择</button>
					      	</span>
				    	</div>
				    	<input type="text" name="reportName" class="form-control" placeholder="请输入检验报告" />
						<button type="button" id="historyReportSearchButton" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
					</form>
				</div>
				<div class="table-responsive table-fixed-head">
					<table class="table table-bordered no-bottom">
						<thead>
							<tr class="active">
								<th class="text-center" style="width: 5%;">选择</th>
								<th class="text-center" style="width: 30%;">检验报告</th>
								<th class="text-center" style="width: 15%;">检验日期</th>
								<th class="text-center" style="width: 15%;">孕周数</th>
								<th class="text-center" style="width: 15%;">接诊医生</th>
								<th class="text-center" style="width: 15%;">接诊日期</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="table-responsive table-scroll-body" style="max-height: 310px;">
					<table class="table table-bordered no-bottom table-padding-4">
						<tbody id="history-report-tbody"><tr id="history-report-tbody-tr"><td class="text-center"><h4>没有找到记录！</h4></td></tr></tbody>
					</table>
				</div>
				
				<!-- 检验报告详情展示区 -->
				<div id="history-line-div" style="height:5px; background-color: #84abe1; display: none;"></div>
				<div id="history-report-detail-div" class="table-scroll-body" style="display: none; max-height: 427px;"></div>
			</div>
			
			<!-- 查询检验项目tab -->
			<div class="tab-pane fade" id="tab-item">
				<div class="panel-body form-inline" id="historyItemCondition" style="padding: 7px;">
					<form id="historyItemForm" method="post" class="form-horizontal">
						<input type="hidden" id="historyItemCustId" name="custId">
				    	<input type="text" id="historyItemReportName" name="itemName" class="form-control" placeholder="请输入检验项目" />
						<button type="button" id="historyItemSearchButton" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
					</form>
				</div>
				<div class="table-responsive table-fixed-head">
					<table class="table table-bordered no-bottom table-padding-4">
						<thead>
							<tr class="active">
								<th class="text-center" style="width: 5%">序号</th>
								<th class="text-center" style="width: 25%;">检验项目</th>
								<th class="text-center" style="width: 10%;">检验日期</th>
								<th class="text-center" style="width: 10%;">孕周数</th>
								<th class="text-center" style="width: 10%;">结果</th>
								<th class="text-center" style="width: 10%;">结论</th>
								<th class="text-center" style="width: 10%;">单位</th>
								<th class="text-center" style="width: 20%;">参考范围</th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="table-responsive table-scroll-body" style="max-height: 200px;">
					<table class="table table-bordered no-bottom table-padding-4">
						<tbody id="history-item-tbody"><tr id="history-item-tbody-tr"><td class="text-center"><h4>没有找到记录！</h4></td></tr></tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
