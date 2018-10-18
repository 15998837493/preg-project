<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>膳食模板</title>
<style type="text/css">
.intak-input {
	line-height: 15px;
	margin: 2px;
	width: 40px;
}

#intakeDetailTable tr {
	height: 83px;
	border: 1px solid #fff;
}
</style>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/plan/intake_view.js"></script>
</head>
<body>
<input type="hidden" id="planIntakeId" name="planIntakeId" />
<div class="row">
	<div class="panel-body">
		<div id="planIntakeCondition">
			<form id="planIntakeQueryForm" action="${common.basepath}/${applicationScope.URL.Master.PLAN_INTAKE_QUERY}" method="post" class="form-horizontal">
				<div class="form-inline">
					<input type='text' id='searchText' name="intakeName" class='form-control' placeholder='请输入检索内容' />				
					<select id="pregnantStage" name="pregnantStage" class="form-control">
						<option value="">请选择孕期阶段</option>
					</select>
					<select id="dataBelongType" name="dataBelongType" class="form-control">
						<option value="">请选择模版类型</option>
					</select>
					<button id="search" type="button" name="operateBtn" class="btn btn-default">
						<i class="fa fa-search fa-fw"></i>查询
					</button>
					<div class="vertical-line"></div>
					<button id="save" type="button" name="operateBtn" class="btn btn-default">
						<i class="fa fa-plus fa-fw"></i> 增加
					</button>
					<button id="update" type="button" name="operateBtn" class="btn btn-default">
						<i class='fa fa-edit fa-fw'></i> 编辑
					</button>
					<button id="delete" type="button" name="operateBtn" class="btn btn-default">
						<i class='fa fa-remove fa-fw'></i> 删除
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-bordered table-hover" id="planIntakeTable">
		<thead>
			<tr class="active">
				<th class="text-center" rowspan="2" style="line-height: 52px;">选择</th>
			<th class="text-center" rowspan="2" style="line-height: 52px;">模板名称</th>
			<th class="text-center" rowspan="2" style="line-height: 52px;">能量</th>
			<th class="text-center" colspan="2" style="width: 140px;">碳水化合物</th>
			<th class="text-center" colspan="2" style="width: 140px;">蛋白质</th>
			<th class="text-center" colspan="2" style="width: 140px;">脂肪</th>
			<th class="text-center" rowspan="2" style="line-height: 52px;">孕期阶段</th>
			<th class="text-center" rowspan="2" style="line-height: 52px;">模板类型</th>
			<th class="text-center" rowspan="2" style="line-height: 52px;">撰写医生</th>
		</tr>
		<tr class="active">
			<th class="text-center" style="width: 70px;">克(g)</th>
			<th class="text-center" style="width: 70px;">占比(%)</th>
			<th class="text-center" style="width: 70px;">克(g)</th>
			<th class="text-center" style="width: 70px;">占比(%)</th>
			<th class="text-center" style="width: 70px;">克(g)</th>
			<th class="text-center" style="width: 70px;">占比(%)</th>
			</tr>
		</thead>
	</table>
</div>
			
</body>
</html>