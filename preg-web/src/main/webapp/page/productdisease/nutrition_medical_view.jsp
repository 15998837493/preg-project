<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>营养制剂医嘱配置</title>
</head>
<script type="text/javascript">
	var checkedData;// 选中项信息
	var checkedRow;// 选中行信息
	var productUnit;//商品单位
	var nutritionMedicalTable;// table，注意命名不要重复
	var diseaseList = ${diseaseList};// 疾病列表
	//自动补全功能使用的集合（诊断名称）
	var diseaseListData = [];
	// 遍历所有诊断疾病
	$.each(diseaseList, function(index, obj){
		diseaseListData.push({name:obj.diseaseName, val:obj});
	});
	//配置页面列表datatable
	var configTableOptions = {
		id : "productDiseaseConfigTable",
		form : "productDiseaseConfigForm",
		isPage:false,
		async : false,
		columns : [
				{
					"data" : "productUserDisease",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return '<input id="diseaseId_'+row.productDiseaseId+'" type="hidden" value="'+row.diseaseId+'"/><input type="text" id="diseaseName'+row.productDiseaseId+'" class="form-control" placeholder="请输入适用人群" value="'+data+'"/>';
					}
				},
				{
					"data" : "productDiseaseFrequency",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return '<input id="productDiseaseFrequency'+row.productDiseaseId+'" type="hidden" value="'+data+'"/><select id="productDiseaseFrequency_'+row.productDiseaseId+'" class="form-control" onchange="diseaseFrequencyChange(\'productDiseaseFrequency_'+row.productDiseaseId+'\')"></select>';
					}
				},
				{
					"data" : "productDiseaseExecutionlist",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						if(data == ''){
							return '<div id="dayFrequencyGroup'+row.productDiseaseId+'">'
								 + '</div>';
						}
						var jsonObj = eval("("+data+")");
						var dayFrequencyDiv = '';
						for(i = 0; i < jsonObj.length; i++){
							dayFrequencyDiv = dayFrequencyDiv
								+ '<div id="rowProductDayFrequency'+i+row.productDiseaseId+'" class="form-inline">'
								      + '<input id="productDayFrequency'+i+row.productDiseaseId+'" type="hidden" value="'+jsonObj[i].mealtime+'"/><select id="productDayFrequency_'+i+row.productDiseaseId+'" class="form-control input-sm" onchange="executionListChange(\''+row.productDiseaseId+'\')"></select>&nbsp;&nbsp;'
								      + '<div class="input-group input-group-sm">'
								     	 + '<input id="dayFrequency'+i+row.productDiseaseId+'" type="text" value="'+jsonObj[i].mealnum+'" class="form-control text-center" style="width:50px;" maxlength="6"  onblur="executionListChange(\''+row.productDiseaseId+'\')"/>'
							             + '<span class="input-group-addon">'+row.productUnit+'</span>'
							          +' </div>'
								+ '</div>';
						}
						return '<input id="executionList'+row.productDiseaseId+'" type="hidden" /><div id="dayFrequencyGroup'+row.productDiseaseId+'">'
									+ dayFrequencyDiv 
							 + '</div>';
					}
				},
				{
					"data" : null,
					"sClass" : "text-center",
					"render" :function(data, type, row, meta) {
						return '<a id="remove'+row.productDiseaseId+'" onclick="removeConfig(\''+row.productId+'\',\''+row.productDiseaseId+'\')">删除<a/>';
					}
				}],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			if(data!= null){
				$("#productDiseaseId_saveForm").val(data.productDiseaseId);
				$("#productId_saveForm").val(data.productId);
			}
		}
	};
	//列表datatable
	var tableOptions = {
		id : "nutritionMedicalTable",
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
					"data" : "productName",
					"sClass" : "text-center"
				},
				{
					"data" : "productUser",
					"sClass" : "text-center"
				},
				{
					"data" : "productUserDisease",
					"sClass" : "text-center"
				},
				{
					"data" : "productCategoryName",
					"sClass" : "text-center"					
				},
				{
					"data" : "productBrand",
					"sClass" : "text-center"
				} ],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			$("#id").val(data.productId);
			$("#configProductName").html(data.productName);
			$("#configProductBrand").html(data.productBrand);
			$("#configProductCategoryName").html(data.productCategoryName);
			productUnit = data.productUnit;
		},
		condition : "nutritionMedicalCondition"
	};
</script>
<script type="text/javascript" charset="utf-8"
	src="${common.basepath}/page/productdisease/nutrition_medical.js"></script>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="nutritionMedicalCondition">
				<form id="queryForm" name="queryForm" action="${common.basepath}/${applicationScope.URL.ProductDisease.QUERY_NUTRITION_MEDICAL}" method="post" class="form-horizontal">
					<div id="allDiseaseListTable" class="form-inline">
						<input type='hidden' id='productUserDisease' name="productUserDisease" class='form-control' />
						<input type='text' id='diseaseName' class='form-control' placeholder='请输入适用人群' />
					</div>
					<div class="form-inline" style="margin-top:0.3em;">
						<input name='productBrand' type='text' class='form-control' placeholder='请输入品牌名称' />
						<input name='productName' type='text' class='form-control' placeholder='请输入商品名称' />
						<button type="button" id="searchButton" name="operateButton"
							class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button type="button" id="configButton" name="operateButton"
							class="btn btn-default">
							<i class='fa fa-cogs'></i> 配置
						</button>
						<button type="button" id="removeButton" name="operateButton"
							class="btn btn-default">
							<i class='fa fa-remove fa-fw'></i> 删除
						</button>
					</div>
					
				</form>
			</div>
		</div>
	</div>
	<div class="table-responsive">
		<table id="nutritionMedicalTable" class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class="text-center">选择</th>
					<th class="text-center">产品/商品名称</th>
					<th class="text-center">适宜人群</th>
					<th class="text-center">适用人群</th>
					<th class="text-center">产品类别</th>
					<th class="text-center">品牌名称</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 配置页面模态框（Modal） -->
	<%@ include file="/page/productdisease/nutrition_medical_config.jsp"%>
		
</body>
</html>
