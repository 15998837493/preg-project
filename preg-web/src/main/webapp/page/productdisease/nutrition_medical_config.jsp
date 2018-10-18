<%@ page language="java" pageEncoding="UTF-8"%>
<div class="modal fade bs-example-modal" id="productDiseaseConfigModal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="productDiseaseConfigForm" class="form-horizontal"
				action="${common.basepath}/${applicationScope.URL.ProductDisease.QUERY_PRODUCT_DISEASE_CONFIG}"
				method="post">
				<input type="hidden" name="productId" id="id">
			</form>
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class='fa fa-cogs'></i> 配置营养制剂医嘱路径<a class="close"
							data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row" style="margin-top:10px;">
						<div class="col-md-6">
							<b style="margin-left:10px;">商品/产品名称：</b>
							<span id="configProductName" style="max-width:300px;display:inline-block;vertical-align: top;"></span>
						</div>
						<div class="col-md-3">
							<b>品牌名称：</b>
							<span id="configProductBrand"></span>
						</div>
						<div class="col-md-3">
							<b>产品类别：</b>
							<span id="configProductCategoryName"></span>
						</div>
					</div>
					<div class="table-responsive" style="margin-top:10px">
						<table id="productDiseaseConfigTable"
							class="table table-bordered table-hover table-condensed">
							<thead>
								<tr class="active">
									<th class="text-center">适用人群</th>
									<th class="text-center">频次</th>
									<th class="text-center">餐次剂量</th>
									<th class="text-center">操作</th>
								</tr>
							</thead>
						</table>
						<form id="configSaveForm" style="display: none;"
							action="${common.basepath}/${applicationScope.URL.ProductDisease.ADDORUPDATE_NUTRITION_MEDICAL}"
							method="post">
							<input id="productDiseaseId_saveForm" name="productDiseaseId" />
							<input id="productId_saveForm" name="productId" /> <input
								id="diseaseId_saveForm" name="diseaseId" /> <input
								id="productDiseaseFrequency_saveForm"
								name="productDiseaseFrequency" /> <input
								id="productDiseaseExecutionlist_saveForm"
								name="productDiseaseExecutionlist" />
						</form>
						<div class="panel-body text-center footerClass"
							style="padding: 13px;" id="fotter">
							<a onclick="addConfig();"> + 添加</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>