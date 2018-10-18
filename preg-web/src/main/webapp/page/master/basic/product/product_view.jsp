<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>检查商品信息</title>
</head>
<script type="text/javascript">
	var checkedData;// 选中项信息
	var checkedRow;// 选中行信息
	var productTable;// table，注意命名不要重复

	//配置datatable
	var tableOptions = {
		id : "productTable",
		form : "queryForm",
		columns : [
				{
					"data" : null,
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return "<input type='radio' name='tableRadio' value='"+data.productId+"' />";
					}
				},
				{
					"data" : "productCode",
					"sClass" : "text-center"
				},
				{
					"data" : null,
					"render" : function(data, type, row, meta) {
						return data.productName
								|| (data.productGoodsName + " " + data.productCommonName);
					}
				}, {
					"data" : "productCategoryName",
					"sClass" : "text-center"
				}, {
					"data" : "productIsAssess",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("trueorfalse", data);
					}
				}, {
					"data" : "productIsEnergy",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("trueorfalse", data);
					}
				}, {
					"data" : "productUnit",
					"sClass" : "text-center"
				}, {
					"data" : "productDosageDesc",
					"sClass" : "text-center"
				}, {
					"data" : "productFrequency",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("productfrequency", data);
					}
				}, {
					"data" : "productUsageMethod",
					"sClass" : "text-center"
				}, {
					"data" : "productStandard",
					"sClass" : "text-center"
				}, {
					"data" : "productPurchasePrice",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						if (common.isEmpty(data)) {
							return "";
						}
						return parseInt(data) / 100;
					}
				}, {
					"data" : "productSellPrice",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						if (common.isEmpty(data)) {
							return "";
						}
						return parseInt(data) / 100;
					}
				}

		],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			$("#id").val(data.productId);
		},
		condition : "productCondition"
	};
</script>
<script type="text/javascript" src="${common.basepath}/page/master/basic/product/product.js"></script>
<script type="text/javascript">
	$().ready(function() {
		productTable = datatable.table(tableOptions);

		$("button[name='operateButton']").click(function() {
			var id = $("#id").val();
			if (this.id == 'addButton') {
				redirectPage('init_add');
			}
			if (this.id == 'editButton' && isChoose(id)) {
				common.pageForward(initUpdateAction + "?id=" + id);
			}
			if (this.id == 'removeButton' && isChoose(id)) {
				removeClick(id);
			}
			if (this.id == 'configintakeButton' && isChoose(id)) {
				console.log(configAction);
				common.pageForward(configAction + "?id=" + id);
			}
			if (this.id == "searchButton") {
				productTable = datatable.table(tableOptions);
			}
		});

	});
</script>
<body>
	<div class="row">
		<c:if test="${not empty productCatalogs}">
			<c:forEach items="${productCatalogs}" var="productCatalog">
				<input type="hidden" id="cat${productCatalog.catalogId}"
					value="${productCatalog.catalogName}" />
			</c:forEach>
		</c:if>
		<div class="panel-body ">
			<div class="row">
				<div class="col-xs-12" id="productCondition">
					<form id="queryForm" name="queryForm" action="${common.basepath}/${applicationScope.URL.Master.PRODUCT_QUERY}" method="post" class="form-horizontal">
						<div class="form-inline">
							<input type='text' class='form-control' placeholder='请输入检索内容' />
							<button type="button" id="searchButton" name="operateButton" class="btn btn-default">
								<i class="fa fa-search fa-fw"></i>查询
							</button>
							<div class="vertical-line"></div>
							<button type="button" id="addButton" name="operateButton" class="btn btn-default">
								<i class="fa fa-plus fa-fw"></i> 增加
							</button>
							<button type="button" id="editButton" name="operateButton" class="btn btn-default">
								<i class='fa fa-edit fa-fw'></i> 编辑
							</button>
							<button type="button" id="removeButton" name="operateButton" class="btn btn-default">
								<i class='fa fa-remove fa-fw'></i> 删除
							</button>
							<button type="button" id="configintakeButton" name="operateButton" class="btn btn-default">
								<i class='fa fa-cogs'></i> 营养素配置
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="table-responsive">
		<table id="productTable" class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class="text-center">选择</th>
					<th class="text-center">编码</th>
					<th class="text-center">商品名称</th>
					<th class="text-center">商品类别</th>
					<th class="text-center">剂量评估</th>
					<th class="text-center">能量评估</th>
					<th class="text-center">剂量单位</th>
					<th class="text-center">剂量说明</th>
					<th class="text-center">用药频次</th>
					<th class="text-center">用药方法</th>
					<th class="text-center">规格</th>
					<th class="text-center">成本价</th>
					<th class="text-center">销售价</th>
				</tr>
			</thead>
		</table>
	</div>
	<form id="editForm" name="editForm" action="" method="post">
		<input type="hidden" id="id" name="id" />
	</form>
</body>
</html>
