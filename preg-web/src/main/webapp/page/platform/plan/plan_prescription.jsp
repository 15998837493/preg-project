<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/common/common.jsp"%>
<style type="text/css">
#myTabContent .panel{
	margin-bottom: 0px;
}
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
</style>
<title>处方管理--剂量评估</title>
<script type="text/javascript">
//取消回车事件
$(document).keydown(function(event){
    switch(event.keyCode){
       case 13:return false; 
       }
});
var productAllMap = ${productAllMap};// 商品map
var diseEleAllMap = ${diseEleAllMap};
var diagnosis = ${diagnosis};
var diagnosisExtenderList = ${diagnosisExtenderList};
// 补充剂自动补全数组
var productListData = [];
if(!_.isEmpty(productAllMap)){
	for(var key in productAllMap){  
		var product = productAllMap[key];
		productListData.push({name:product.productName+" "+product.productGoodsName+" "+product.productCommonName, value:product});	
	};
}
</script>
<script type="text/javascript" src="${common.basepath}/page/platform/plan/plan_prescription.js"></script>
</head>
<body>
<input type="hidden" id="diagnosisId" value="${diagnosisId }" />
<!-- 剂量评估页 -->
<div id="assessModal" class="modal bs-example-modal form-horizontal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="container-fluid">
				<div class="row">
					<ul id="timeline">
						<li class="timeline-inverted">
							<div class="timeline-panel" id="timeline-panel">
								<div class="timeline-heading"></div>
								<div class="timeline-body form-horizontal">
									<div class="panel panel-lightblue" style="margin-bottom: 15px;">
										<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 患者基本信息<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
										<div class="table-responsive">
											<table class="table table-bordered" id="diagnosisInfoTable">
												<tbody>
													<tr>
														<td class="active" width="12.5%">ID</td>
														<td width="12.5%"></td>
														<td class="active" width="12.5%">姓名</td>
														<td width="12.5%"></td>
														<td class="active" width="12.5%">年龄</td>
														<td width="12.5%"></td>
														<td class="active" width="12.5%">孕前体重</td>
														<td width="12.5%"></td>
													</tr>
													<tr>
														<td class="active" width="12.5%">身高</td>
														<td width="12.5%"></td>
														<td class="active" width="12.5%">孕前BMI</td>
														<td width="12.5%"></td>
														<td class="active" width="12.5%">孕周数</td>
														<td width="12.5%"></td>
														<td class="active" width="12.5%">预产期</td>
														<td width="12.5%"></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div class="panel panel-lightblue" style="margin-bottom: 15px;">
										<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 补充剂服用明细</div>
										<div class="table-responsive">
											<table class="table table-bordered table-padding-2">
												<tbody>
													<tr class="active">
														<th class="text-center" width="8%">编号</th>
														<th class="text-center" width="30%">品名</th>
														<th class="text-center" width="12%">单次剂量</th>
														<th class="text-center" width="12%">剂量说明</th>
														<th class="text-center" width="12%">用法</th>
														<th class="text-center" width="13%">频次</th>
													</tr>
												</tbody>
												<tbody id="extenderTableBody"></tbody>
											</table>
										</div>
									</div>
									<div class="panel panel-lightblue">
										<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 营养素剂量评估</div>
										<div class="table-responsive">
											<table class="table table-bordered table-padding-2">
												<thead>
													<tr class="active">
														<th class="text-center" width="23%">元素/剂量单位</th>
														<th class="text-center" width="14%">补充剂来源分析</th>
														<th class="text-center" width="14%">补充剂成分备注</th>
														<th class="text-center" width="13%">结果</th>
														<th class="text-center" width="13%">结论</th>
														<th class="text-center" width="13%">DRIS</th>
													</tr>
												</thead>
												<tbody id="elementTable"></tbody>
											</table>
										</div>
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
	
<!-- 诊断处方 -->
<form id="planProductForm" class="form-horizontal" method="post">
	<div class="panel panel-lightblue" id="savePrescription_info_div">
		<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 营养制剂处方</div>
		<input type="hidden" name="custId" value="${custId }"/>
		<input type="hidden" name="diagnosisId" value="${diagnosisId }"/>
		<input type="hidden" name="templetName" id="formTempletName"/>
		<input type="hidden" name="orderDiagnosis" id="formOrderDiagnosis"/>
		<input type="hidden" name="templetType" value="person"/>
		<input type="hidden" name="orderRemark" id="formOrderRemark"/>
		<div class="panel-body">
			<div class="col-xs-11 no-padding" id="diseaseSpan">
				<label class="control-label">诊断：</label>
			</div>
		</div>
		<div class="table-responsive table-fixed-head">
			<table class="table table-bordered no-bottom">
				<thead>
					<tr class="active">
						<th class="text-center" style="width: 5%;">图片</th>
						<th class="text-center" style="width: 32%;">名称</th>
						<th class="text-center" style="width: 10%;">单次剂量</th>
						<th class="text-center" style="width: 8%;">剂量说明</th>
						<th class="text-center" style="width: 5%;">用法</th>
						<th class="text-center" style="width: 13%;">频次</th>
						<th class="text-center" style="width: 5%;">疗程</th>
						<th class="text-center" style="width: 15%;">提示</th>
						<th class="text-center" style="width: 7%;">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="table-responsive" style="width: 100%; overflow-y: scroll; max-height: 220px;">
			<table class="table table-bordered no-bottom table-padding-2">
				<tbody id="order_tbody">
					<tr><td class='text-center' colspan='100'><h4 id='zero' style='text-align: center;'>没有找到记录！</h4></td></tr>
				</tbody>
			</table>
		</div>
		<table class="table table-bordered no-bottom table-padding-2">
			<tfoot id="order_tfoot">
				<tr> 
				    <td>
				    	<div class="col-xs-3 padding-zero"><input type='text' class='form-control' id='productAddInput' placeholder="请输入营养制剂名称"/></div>
				    	<div class="col-xs-9" style="padding-top: 8px;"><span style="color:gray;">（提示：选中商品后，单击或按Enter键可以添加商品）</span></div>
				    </td> 
				</tr>
				<tr>
					<td class="text-center">
						<textarea maxlength="500" id="extenderHint" name="extenderHint" class="form-control" placeholder="请输入处方备注"></textarea>
					</td>
				</tr>
			</tfoot>
		</table>
		<div class="panel-body text-right">
			<button class="btn btn-primary" type="button" onclick="getExtenderElement();"><i class="fa fa-filter"></i> &nbsp;剂量评估 </button>
			<button class="btn btn-primary" type="button" onclick="redirectTemlate();"><i class="fa fa-link"></i> &nbsp;处方模板</button>
		</div>
	</div>
</form>

</body>
</html>