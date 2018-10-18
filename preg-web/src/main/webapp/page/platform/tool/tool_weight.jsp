<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>体重计算器</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script charset="UTF-8" src="${common.basepath}/common/plugins/echarts/echarts-all.js"></script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/platform/tool/tool_weight.js"></script>
<body>
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-heading">
					<h4 class="timeline-title">孕期体重计算</h4>
				</div>
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">孕期体重计算</div>
						<div class="panel-body">
						<form id="custData" class="form-horizontal" action="">
							<div class="col-xs-5" style="padding-top: 20px;">
								<div class="form-group">
									<label class="col-xs-3 control-label">末次月经</label>
									<div class="col-xs-8">
										<div class="input-group">
									      	<input id="lmp" name="lmp" type="text" class="form-control form_date" placeholder="请选择末次月经" readonly value="" onchange="getCustLmp(this.value);"/>
									      	<span class="input-group-btn">
									        	<button class="btn btn-default" type="button" onclick="common.dateShow('lmp')"><i class="fa fa-calendar fa-fw"></i>选择</button>
									      	</span>
						    			</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">孕周数</label>
									<div class="col-xs-8">
										<input id="diagnosisGestationalWeeks" name="diagnosisGestationalWeeks" class="form-control" type="text" readonly />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">孕前BMI</label>
									<div class="col-xs-8">
										<input id="bmi" name="bmi" class="form-control" type="text" readonly />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">身高</label>
									<div class="col-xs-8">
										<div class="input-group">
											<input id="diagnosisCustHeight" name="diagnosisCustHeight" class="form-control" type="text" input-required maxlength="6" />
											<div class="input-group-btn">
												<button class="btn btn-default" type="button" style="width:72px;">cm</button>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">孕前体重</label>
									<div class="col-xs-8">
										<div class="input-group">
											<input id="custWeight" name="custWeight" class="form-control" type="text" input-required maxlength="6" />
											<div class="input-group-btn">
												<button class="btn btn-default" type="button" style="width:72px;">kg</button>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">当前体重</label>
									<div class="col-xs-8">
										<div class="input-group">
											<input id="diagnosisCustWeight" name="diagnosisCustWeight" type="text" input-required maxlength="6" class="form-control" placeholder="请输入当前体重" />
											<div class="input-group-btn">
												<button class="btn btn-default" type="button" style="width:72px;">kg</button>
											</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">一周前体重</label>
									<div class="col-xs-8">
										<div class="input-group">
											<input id="diagnosisCustWeightPreweek" name="diagnosisCustWeightPreweek" type="text" input-required maxlength="6" class="form-control" placeholder="请输入一周前体重" />
											<div class="input-group-btn">
												<button class="btn btn-default" type="button" style="width:72px;">kg</button>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-2 col-xs-offset-4">
									<button class="btn btn-primary" type="button" onclick="getWeightChart();" style="width: 150px;">体重计算</button>
								</div>
							</div>
							<div class="col-xs-7" id="weightEchart" style="height:400px"></div>
						</form>
						</div>
					</div>
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">结论</div>
						<table class="table table-bordered">
							<tr><td style="width :230px;">该孕妇当前整体体重增长：</td><td id="totalWeight_td"></td></tr>
							<tr><td>本周体重增长：</td><td id="weekWeight_td"></td></tr>
							<tr><td>当前整体体重增长速度：</td><td id="totalSpeed_td"></td></tr>
							<tr><td>本周体重增长速度：</td><td id="weekSpeed_td"></td></tr>
							<tr><td>建议整体孕期体重适宜增长范围：</td><td id="totalRange_td"></td></tr>
							<tr><td>建议每周体重适宜增长范围：</td><td id="weekRange_td"></td></tr>
						</table>
					</div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>