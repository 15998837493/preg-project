<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" type=text/css
	href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<script type="text/javascript" charset="UTF-8"
	src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<link rel="stylesheet"
	href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<title>食物查询列表</title>
<script type="text/javascript">
	//datatable配置
	var foodData;
	var foodRow;
	var foodTable;

	var foodTableOption = {
		id : "foodListTable",
		form : "cuisineQuery",
		columns : [
				{
					"data" : null,
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return "<input id='"+data.foodId+"' name='foodRadio' type='radio'/>";
					}
				}, {
					"data" : "foodName",
					"sClass" : "text-center"
				}, {
					"data" : "foodAlias",
					"sClass" : "text-left"
				}, {
					"data" : "foodType",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("FOOD_TYPE", data);
					}
				} ],
		rowClick : function(data, row) {
			foodData = data;
			foodRow = row;
			$("#foodId").val(data.foodId);
		},
		condition : "foodCondition",
		selecttarget : []
	};

	$().ready(function() {
		foodTable = datatable.table(foodTableOption);
		$("button[name='cuisinePage']").click(function() {
							var foodId = $("#foodId").val();
											if (this.id == 'addCuisinePage') {
												common.pageForward(PublicConstant.basePath+ "/page/food/food_cuisine_add_init.action");
											}
											if (this.id == 'editCuisinePage'&& common.isChoose(foodId)) {
												common.pageForward(PublicConstant.basePath+ "/page/food/food_cuisine_update_init.action?foodId="+ foodId);
											}
											if (this.id == 'removeCuisinePage'&& common.isChoose(foodId)) {
												removeClick(foodId);
											}
											if (this.id == 'search') {
												foodTable = datatable.table(foodTableOption);
											}
										});
					});

	function removeClick(foodId) {
		layer.confirm("确定对选中内容执行【删除】操作？", function(data) {
			var url = URL.get("Master.FOOD_CUISINE_REMOVE");
			var params = "foodId=" + foodId;
			ajax.post(url, params, dataType.json, function(data) {
				datatable.remove(foodTable, foodRow);
			});
			layer.close(index);
		});
	};
</script>
</head>
<body>
	<input id="foodId" name="foodId" type="hidden" />
	<div class="row">
		<div class="panel-body">
			<div id="foodCondition">
				<form action="${common.basepath }/${applicationScope.URL.Master.FOOD_CUISINE_AJAX_QUERY}" id="cuisineQuery" method="post" class="form-horizontal">
					<div class="form-inline">
						<input id="foodName" name="foodName" class="form-control" type="text" placeholder="请输入食物名称" />
						<button type="button" id="search" name="cuisinePage" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button id="addCuisinePage" name="cuisinePage" type="button" class="btn btn-default">
							<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button id="editCuisinePage" name="cuisinePage" type="button" class="btn btn-default">
							<i class="fa fa-edit fa-fw"></i> 编辑
						</button>
						<button id="removeCuisinePage" name="cuisinePage" type="button" class="btn btn-default">
							<i class="fa fa-remove fa-fw"></i> 删除
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="table-responsive">
		<table class="table table-bordered table-hover" id="foodListTable">
			<thead>
				<tr class="active">
					<th class="text-center">操作</th>
					<th class="text-center">食谱名称</th>
					<th class="text-center">食谱别名</th>
					<th class="text-center">食谱类型</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</HTML>
