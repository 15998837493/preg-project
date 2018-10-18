<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>编辑膳食模板</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/plan/intake_edit.js"></script>
<style>
.intak-input {
	line-height: 15px;
	margin: 2px;
	width: 40px;
}
</style>
<script type="text/javascript">
//商品列表
var productList = ${productList };
//摄入类型列表
var intakeTypeList = ${intakeTypeList };
//食谱模板列表
var dietTemplateList = ${dietTemplateList };
//编辑时的基本信息
var intakeDTO = ${intakeDTO };
//编辑时的明细列表信息
var intakeDetails = ${intakeDetails };	
</script>
<body>
	<div class="container-fluid">
		<div class="row">
			<ul id="timeline">
				<li class="timeline-inverted">
					<div class="timeline-panel" id="timeline-panel">
						<div class="timeline-heading">
							<h4 class="timeline-title">${customerQuotaVo.inspectItemName}</h4>
						</div>
						<div class="timeline-body form-horizontal">
							<form id="intakeEditForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.Master.PLAN_INTAKE_ADD}" method="post">
								<input type="hidden" id="intakeId" name="intakeId" />
								<input type="hidden" id="dataType" name="dataType" value='1' />
								<input type="hidden" id="detailsJson" name="detailsJson" />
								<div class="panel panel-lightblue">
									<div class="panel-heading text-center">膳食方案模板信息</div>
									<div class="panel-body" style="margin-right: 8%;">
										<div class="form-group">
											<label class="col-xs-2 control-label">模板名称</label>
											<div class="col-xs-4">
												<input type="text" id="intakeName" maxlength="80" name="intakeName" class="form-control" />
											</div>
											<label class="col-xs-2 control-label">膳食模式</label>
											<div class="col-xs-4">
												<select id="intakeMode" name="intakeMode" class="form-control"></select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-2 control-label">实际能量</label>
											<div class="col-xs-4">
												<input type="text" id="intakeActualEnergy" name="intakeActualEnergy" class="form-control not-edit" readonly="readonly" />
											</div>
											<label class="col-xs-2 control-label">碳水化合物/能量占比</label>
											<div class="col-xs-2">
												<div class="input-group">
													<input type="text" id="intakeCbr" name="intakeCbr" class="form-control not-edit" readonly="readonly" />
													<div class="input-group-addon">g</div>
												</div>
											</div>
											<div class="col-xs-2">
												<div class="input-group">
													<input type="text" id="intakeCbrPercent" name="intakeCbrPercent" class="form-control" readonly="readonly" />
													<div class="input-group-addon">%</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-2 control-label">蛋白质/能量占比</label>
											<div class="col-xs-2">
												<div class="input-group">
													<input type="text" id="intakeProtein" name="intakeProtein" class="form-control" readonly="readonly" />
													<div class="input-group-addon">g</div>
												</div>
											</div>
											<div class="col-xs-2">
												<div class="input-group">
													<input type="text" id="intakeProteinPercent" name="intakeProteinPercent" class="form-control" readonly="readonly" />
													<div class="input-group-addon">%</div>
												</div>
											</div>
											<label class="col-xs-2 control-label">脂肪/脂肪能量占比</label>
											<div class="col-xs-2">
												<div class="input-group">
													<input type="text" id="intakeFat" name="intakeFat" class="form-control" readonly="readonly" />
													<div class="input-group-addon">g</div>
												</div>
											</div>
											<div class="col-xs-2">
												<div class="input-group">
													<input type="text" id="intakeFatPercent" name="intakeFatPercent" class="form-control" readonly="readonly" />
													<div class="input-group-addon">%</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-2 control-label">食谱</label>
											<div class="col-xs-4">
												<input type="hidden" id="dietId" name="dietId" />
												<div class="input-group" onclick="showFoodbookModal();">
													<input type="text" id="dietName" name="dietName" class="form-control" readonly="readonly" style="background-color: #fff;" />
													<div class="input-group-addon">选择</div>
												</div>
											</div>
											<label class="col-xs-2 control-label">孕期阶段</label>
											<div class="col-xs-4">
												<select id="pregnantStage" name="pregnantStage" class="form-control"></select>
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-2 control-label">营养食品</label>
											<div class="col-xs-4">
												<div class="input-group">
													<input type="text" maxlength="1000" id="intakeProductDesc" name="intakeProductDesc" class="form-control" />
													<div class="input-group-addon">天</div>
												</div>
											</div>
											<label class="col-xs-2 control-label">套餐提示</label>
											<div class="col-xs-4">
												<div class="input-group">
													<input type="text" id="intakePrompt" name="intakePrompt" class="form-control" />
													<div class="input-group-addon">2周</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-2 control-label">诊断</label>
											<div class="col-xs-10">
												<input type="text" id="intakeMark" name="intakeMark" class="form-control" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-2 control-label">备注</label>
											<div class="col-xs-10">
												<textarea id="intakeRemark" name="intakeRemark" class="form-control" rows="2"></textarea>
											</div>
										</div>
									</div>
								</div>
								<div class="panel panel-lightblue">
									<div class="panel-heading text-center">编辑膳食清单</div>
									<div class="panel-body">
										<div class="col-xs-12 no-left no-right row-top">
											<td>
												<table id="intakeDetailTable" class="table table-bordered" style="margin: 5px 0 5px 0;">
					                        		<tr id ="C00000CA000000000001">
					                        			<td class="text-center" rowspan="2" style="width: 10%">早餐</td>
					                        			<td id="intake" style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;">
					                       					谷薯类<input name="谷薯类" type="text" id="E004" class="intak-input" maxlength='4'/>份、
					                       					蔬菜<input name="蔬菜" type="text" id="E005" class="intak-input" maxlength='4'/>份、
					                       					鱼肉蛋<input name="鱼肉蛋" type="text" id="E007" class="intak-input" maxlength='4'/>份、
					                       					大豆类<input name="大豆类" type="text" id="E008" class="intak-input" maxlength='4'/>份 、
					                       					油脂类<input name="油脂类" type="text" id="E012" class="intak-input" maxlength='4'/>份、
					                       					调味品<input name="调味品" type="text" id="E013" class="intak-input" maxlength='4'/>份
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000001">
					                        			<td id="product" style="border-top: 0px solid #fff;border-left: 0px solid #fff;">
					                        				<a href="javascript:showProductModal('C00000CA000000000001');"><i class="fa fa-plus-circle"></i> 添加食品</a>&nbsp;&nbsp;
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000002">
					                        			<td class="text-center" rowspan="2" style="width: 10%">上午加餐</td>
					                        			<td id="intake" style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;">
															水果<input name="水果" type="text" id="E006" class="intak-input" maxlength='4'/>份、
					                       					坚果<input name="坚果" type="text" id="E009" class="intak-input" maxlength='4'/>份、
					                       					奶及奶制品<input name="奶及奶制品" type="text" id="E010" class="intak-input" maxlength='4'/>份、
					                       					脱脂奶及奶制品<input name="脱脂奶及奶制品" type="text" id="E011" class="intak-input" maxlength='4'/>份
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000002">
					                        			<td id="product" style="border-top: 0px solid #fff;border-left: 0px solid #fff;">
					                        				<a href="javascript:showProductModal('C00000CA000000000002');"><i class="fa fa-plus-circle"></i> 添加食品</a>&nbsp;&nbsp;
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000003">
					                        			<td class="text-center" rowspan="2" style="width: 10%">午餐</td>
					                        			<td id="intake" style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;">
					                       					谷薯类<input name="谷薯类" type="text" id="E004" class="intak-input" maxlength='4'/>份、
					                       					蔬菜<input name="蔬菜" type="text" id="E005" class="intak-input" maxlength='4'/>份、
					                       					鱼肉蛋<input name="鱼肉蛋" type="text" id="E007" class="intak-input" maxlength='4'/>份、
					                       					大豆类<input name="大豆类" type="text" id="E008" class="intak-input" maxlength='4'/>份 、
					                       					油脂类<input name="油脂类" type="text" id="E012" class="intak-input" maxlength='4'/>份、
					                       					调味品<input name="调味品" type="text" id="E013" class="intak-input" maxlength='4'/>份
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000003">
					                        			<td id="product" style="border-top: 0px solid #fff;border-left: 0px solid #fff;">
					                        				<a href="javascript:showProductModal('C00000CA000000000003');"><i class="fa fa-plus-circle"></i> 添加食品</a>&nbsp;&nbsp;
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000004">
					                        			<td class="text-center" rowspan="2" style="width: 10%">下午加餐</td>
					                        			<td id="intake" style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;">
															水果<input name="水果" type="text" id="E006" class="intak-input" maxlength='4'/>份、
					                       					坚果<input name="坚果" type="text" id="E009" class="intak-input" maxlength='4'/>份、
					                       					奶及奶制品<input name="奶及奶制品" type="text" id="E010" class="intak-input" maxlength='4'/>份、
					                       					脱脂奶及奶制品<input name="脱脂奶及奶制品" type="text" id="E011" class="intak-input" maxlength='4'/>份
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000004">
					                        			<td id="product" style="border-top: 0px solid #fff;border-left: 0px solid #fff;">
					                        				<a href="javascript:showProductModal('C00000CA000000000004');"><i class="fa fa-plus-circle"></i> 添加食品</a>&nbsp;&nbsp;
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000005">
					                        			<td class="text-center" rowspan="2" style="width: 10%">晚餐</td>
					                        			<td id="intake" style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;">
					                       					<span>谷薯类</span><input name="谷薯类" type="text" id="E004" class="intak-input" maxlength='4'/>份、
					                       					蔬菜<input name="蔬菜" type="text" id="E005" class="intak-input" maxlength='4'/>份、
					                       					鱼肉蛋<input name="鱼肉蛋" type="text" id="E007" class="intak-input" maxlength='4'/>份、
					                       					大豆类<input name="大豆类" type="text" id="E008" class="intak-input" maxlength='4'/>份 、
					                       					油脂类<input name="油脂类" type="text" id="E012" class="intak-input" maxlength='4'/>份、
					                       					调味品<input name="调味品" type="text" id="E013" class="intak-input" maxlength='4'/>份
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000005">
					                        			<td id="product" style="border-top: 0px solid #fff;border-left: 0px solid #fff;">
					                        				<a href="javascript:showProductModal('C00000CA000000000005');"><i class="fa fa-plus-circle"></i> 添加食品</a>&nbsp;&nbsp;
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000006">
					                        			<td class="text-center" rowspan="2" style="width: 10%">睡前</td>
					                        			<td id="intake" style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;">
															水果<input name="水果" type="text" id="E006" class="intak-input" maxlength='4'/>份、
					                       					坚果<input name="坚果" type="text" id="E009" class="intak-input" maxlength='4'/>份、
					                       					奶及奶制品<input name="奶及奶制品" type="text" id="E010" class="intak-input" maxlength='4'/>份、
					                       					脱脂奶及奶制品<input name="脱脂奶及奶制品" type="text" id="E011" class="intak-input" maxlength='4'/>份
					                        			</td>
					                        		</tr>
					                        		<tr id ="C00000CA000000000006">
					                        			<td id="product" style="border-top: 0px solid #fff;border-left: 0px solid #fff;">
					                        				<a href="javascript:showProductModal('C00000CA000000000006');"><i class="fa fa-plus-circle"></i> 添加食品</a>&nbsp;&nbsp;
					                        			</td>
					                        		</tr>
												</table>
											</td>
										</div>
									</div>
								</div>
							</form>
						</div>
						<div class="col-xs-2 col-xs-offset-4 row-top">
							<button class="btn btn-primary btn-block" type="button" onclick="doSubmitIntakeDetail();">
								<i class="fa fa-check fa-fw"></i> 保存
							</button>
						</div>
						<div class="col-xs-2 row-top">
							<button class="btn btn-primary btn-block" type="button" onclick="closeWinsow();">
								<i class="fa fa-reply fa-fw"></i> 返回
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>

<div id="productListModal" class="modal fade bs-example-modal">
	<input type="hidden" id="canciId" />
	<div class="modal-dialog" style="width: 1000px; margin-top: 90px;">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="label-title">
					<i class="fa fa-fw"></i>选择营养食品
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 form-horizontal">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="form-group">
									<div class="col-xs-11 col-xs-offset-1">
										<div class="col-xs-12 no-left" id="productListDiv"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="chooseProduct();">确认</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
			</div>
		</div>
	</div>
</div>

<div id="foodbookModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="label-title">
					<i class="fa fa-fw"></i>选择食谱
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 form-horizontal">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row" id="dtableCondition">
									<form id="dtableQueryForm" action="${common.basepath}/${applicationScope.URL.Master.PLAN_DIETTEMPLATE_QUERY}" method="post"
										class="form-horizontal">
										<div class="col-xs-3">
											<input type='text' id='searchText' name="searchText" class='form-control' placeholder='请输入检索内容' />
										</div>
										<div class="col-xs-3">
											<select id="dietPregnantStageSelect" name="dietPregnantStageSelect" class="form-control">
												<option value="">请选择孕期阶段</option>
											</select>
										</div>
									</form>
								</div>
							</div>
							<div class="table-responsive">
								<table id="dTable" class="table table-bordered table-hover">
									<thead>
										<tr class="active">
											<th class="text-center">模板名称</th>
											<th class="text-center">孕期阶段</th>
											<th class="text-center">操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer"></div>
		</div>
	</div>
</div>