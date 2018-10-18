<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>营养素安全剂量评估--${custName}</title>
<%@ include file="/common/base.jsp"%>
<%@ include file="/common/common.jsp"%>
<script type="text/javascript">
var initExtenderList = ${initExtenderList};// 本次已选补充剂列表
var elementAllList = ${elementAllList};// 所有元素列表
var productAllMap = ${productAllMap};// 所有商品
var gestationalWeeks = ${gestationalWeeks};// 孕周数
</script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/examitem/extender/extender_assess.js"></script>
<style type="text/css">
.tooltip{
	width: 300px;
}
hr{
	margin: 5px 0 5px 0;
}
</style>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<ul id="timeline">
			<li class="timeline-inverted">
				<div class="timeline-panel" id="timeline-panel">
					<div class="timeline-heading">
						<h4 class="timeline-title">营养素安全剂量评估</h4>
					</div>
					<div class="timeline-body form-horizontal">
						<!-- 产品列表 -->
						<form id="planProductForm" method="post">
							<div class="panel panel-lightblue">
								<input type="hidden" id="diagnosisId" name="diagnosisId" value="${diagnosisId }">
								<input type="hidden" id="takingCycleList" name="takingCycleList" >
								<input id="inspectId" name="inspectId" type="hidden" value="${inspectId }" />
								<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 产品列表</div>
								<div class="table-responsive">
									<table class="table table-padding-2 table-bordered table-inline" id="nutrientDoseListTable">
										<thead>
											<tr class="active">
												<th class="text-center" style="width: 5%;">图片</th>
												<th class="text-center" style="width: 20%;">名称</th>
												<th class="text-center" style="width: 9%;">单次剂量</th>
												<th class="text-center" style="width: 7%;">剂量说明</th>
												<th class="text-center" style="width: 5%;">用法</th>
												<th class="text-center" style="width: 12%;">频次</th>
												<th class="text-center" style="width: 9%;">规律服用等级</th>
												<th class="text-center" style="width: 7%;">孕期</th>
												<th class="text-center">服用周期</th>
												<th class="text-center" style="width: 5%;">操作</th>
											</tr>
										</thead>
										<tbody id="nutrientDoseListTable_body">
											<tr id="noRecordTr">
												<td colspan="10" class="text-center"><h4>没有找到记录！</h4></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="panel-body" style="padding: 7px;">
									<div class="col-xs-12 text-right no-right">
										<button id="extenderPreView" type="button" name="extenderAssessButton" class="btn btn-primary">剂量评估</button>
									</div>
								</div>
							</div>
						</form>
						<!-- 补充剂库 -->
						<div class="panel panel-lightblue">
							<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 补充剂库</div>
							<div class="panel-body" id="supplementsCondition">
								<form id="supplementsForm" action="${common.basepath}${applicationScope.URL.Master.PRODUCT_QUERY}" method="post">
									<div id="productCategoryDiv" class="div-inline"></div>
									<div class="div-inline">
										<input type='text' id='searchText' name="searchText" class='form-control' placeholder='请输入检索内容' />
									</div>
									<div class="div-inline">
										<button id="searchExtender" type="button" name="extenderAssessButton" class="btn btn-primary">
											<i class="fa fa-search fa-fw"></i>查询
										</button>
									</div>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-padding-2 table-bordered table-hover" id="supplementsTable">
									<thead>
										<tr class="active">
											<th class="text-center" style="width: 5%;">图片</th>
											<th class="text-center">分类</th>
											<th class="text-center">品名</th>
											<th class="text-center">单位</th>
											<th class="text-center">价格</th>
											<th class="text-center">操作</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
	
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
										<div class="col-xs-2 col-xs-offset-8 no-padding"><select class="form-control" id="pdf_taking_cyle" multiple="multiple"></select></div>
										<div class="col-xs-1"><label class="control-label radio-inline"><input type="radio" id="selectAllButton" checked onclick="selectAll();"> 全部</label></div>
										<div class="col-xs-1 no-padding"><button id="assessExtenderPdf" type="button" name="extenderAssessButton" class="btn btn-primary btn-block">打印报告</button></div>
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

<!-- 商品详情 -->
<div id="orderProductModel" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 商品信息一览<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="table-responsive">
						<table class="table table-bordered">
						<tbody>
						<tr>
							<td class="text-right active" style="width: 15%">
								分类
							</td>
							<td class="text-left" id="product_productCategoryName">
							</td>
							<td class="text-right active" style="width: 15%">
								商品代码
							</td>
							<td class="text-left" id="product_productCode">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								商品名称
							</td>
							<td id="product_productGoodsName">
							</td>
							<td class="text-right active" style="width: 15%">
								通用名称
							</td>
							<td class="text-left" id="product_productCommonName">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								产品名称
							</td>
							<td class="text-left" id="product_productName">
							</td>
							<td class="text-right active" style="width: 15%">
								使用属性
							</td>
							<td class="text-left" id="product_productAttribute">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								药局处方
							</td>
							<td class="text-left" id="product_productIsOfficina">
							</td>
							<td class="text-right active" style="width: 15%">
								剂量评估
							</td>
							<td class="text-left" id="product_productIsAssess">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								能量评估
							</td>
							<td class="text-left" id="product_productIsEnergy">
							</td>
							<td class="text-right active" style="width: 15%">
								地区
							</td>
							<td class="text-left" id="product_productArea">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								剂量单位
							</td>
							<td class="text-left" id="product_productUnit">
							</td>
							<td class="text-right active" style="width: 15%">
								剂量说明
							</td>
							<td class="text-left" id="product_productDosageDesc">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								用药方法
							</td>
							<td class="text-left" id="product_productUsageMethod">
							</td>
							<td class="text-right active" style="width: 15%">
								用药频次
							</td>
							<td class="text-left" id="product_productFrequency">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								商品规格
							</td>
							<td class="text-left" id="product_productStandard">
							</td>
							<td class="text-right active" style="width: 15%">
								整包单位
							</td>
							<td class="text-left" id="product_productPackageUnit">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								用药疗程
							</td>
							<td class="text-left" id="product_productTreatment">
							</td>
							<td class="text-right active" style="width: 15%">
								可用天数
							</td>
							<td class="text-left" id="product_productDays">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								品牌
							</td>
							<td class="text-left" id="product_productBrand">
							</td>
							<td class="text-right active" style="width: 15%">
								批准文号
							</td>
							<td class="text-left" id="product_productLicense">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								过敏源
							</td>
							<td class="text-left" colspan="3" id="product_productAllergy">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								使用方法
							</td>
							<td class="text-left" colspan="3" id="product_productUsage">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								适合人群
							</td>
							<td class="text-left" colspan="3" id="product_productUser">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								注意事项
							</td>
							<td class="text-left" colspan="3" id="product_productMatters">
							</td>
						</tr>
						<tr>
							<td class="text-right active" style="width: 15%">
								功效说明
							</td>
							<td class="text-left" colspan="3" id="product_productEffect">
							</td>
						</tr>
						</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>