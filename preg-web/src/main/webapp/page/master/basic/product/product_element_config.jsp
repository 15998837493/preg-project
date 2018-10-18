<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/plugins/bootstrap-table/bootstrap-table.jsp"%>
<script type="text/javascript"
	src="${common.basepath}/page/master/basic/product/product.js"></script>
<title>编辑商品元素明细</title>
<script type="text/javascript">
	var validate;
	window.operateEvents = {
		'click .up' : function(e, value, row, index) {
			$('#reportTable').bootstrapTable('removeRow', index);
			$("#productId").removeData(row.nutrientId);
		}
	};

	//初始化列表框       		
	function inittable() {
		$('#reportTable')
				.bootstrapTable(
						{
							method : 'get',
							idField : 'nutrientId',
							uniqueId : 'nutrientId',
							editable : true,//开启编辑模式
							clickToSelect : true,
							columns : [
									{
										title : '营养素',
										field : 'nutrientName',
										align : "center",
										edit : false
									},
									{
										field : 'productNutrientDosage',
										title : '含量',
										align : "center",
										required : true
									},
									{
										title : '单位',
										field : 'nutrientUnitstr',
										align : "center",
										edit : false
									},
									{
										title : '成分备注',
										field : 'productNutrientRemark',
										align : "center"
									},
									{
										title : '操作',
										formatter : function(value, row,
												rowIndex) {
											var upHtml = '<a href="javascript:void(0);" class="up">删除</a>';
											$("a[class='up']").click(
													function() {
														alert("12");
													});
											return upHtml;
										},
										events : operateEvents,
										edit : false,
										align : "center"
									}, {
										title : '元素id',
										field : "nutrientId",
										align : "center",
										edit : false,
										visible : false
									}, {
										title : '单位编码',
										field : "nutrientUnit",
										align : "center",
										edit : false,
										visible : false
									} ]
						});
	}
	//保存数据
	function saveData() {
		var url = URL.get("Master.PRODUCT_ELEMENT_ADD");
		//将表单序列化为json
		var fromParams = $('#updateForm').serializeObject();
		var optionlistData = $('#reportTable').bootstrapTable('getData');
		if (common.isEmpty(optionlistData)) {
			layer.alert("请添加营养素");
			return;
		}
		var validate = true;
		errorMsg = "";
		$
				.each(
						optionlistData,
						function(index, value) {
							validate = new RegExp("^[0-9]+(.[0-9]{1,2})?$")
									.test(value.productNutrientDosage);
							if (!validate) {
								var dosagemsg = common
										.isEmpty(value.productNutrientDosage) ? "不能为<font color='red'>空</font>"
										: "<font color='red'>"
												+ value.productNutrientDosage
												+ "</font>不合法";
								errorMsg = errorMsg + "元素<font color='red'>"
										+ value.nutrientName + "</font>的含量"
										+ dosagemsg + "<br>";
							}

						});
		if (!validate) {
			layer
					.alert(errorMsg
							+ "<span class='label label-warning'>含量必须是数字，最多支持两位小数</span>");
			return;
		}
		//将选项的json放入到fromParams
		var params = "fromParams=" + JSON.stringify(fromParams)
				+ "&optionParams=" + JSON.stringify(optionlistData);
		layer.confirm("确定对选中内容执行【保存】操作？", function(index) {
			ajax.post(url, params, dataType.json, submitSuc);
		});
	}
	//增加选项数据
	function saveMemData(nutrientName, nutrientUnit, nutrientId) {
		if (common.isEmpty($("#productId").data(nutrientId))) {
			$("#productId").data(nutrientId, nutrientName);
			var nutrientUnitstr = CODE.transCode("PRODUCTUNIT", nutrientUnit);
			$('#reportTable').bootstrapTable('append', {
				nutrientName : nutrientName,
				nutrientUnit : nutrientUnit,
				nutrientId : nutrientId,
				nutrientUnitstr : nutrientUnitstr
			});
		}

	}
	//初始化列表数据
	function initData(productId) {
		$.ajax({
			url : PublicConstant.basePath
					+ URL.Master.QUERY_DETAIL_PRODUCT_ELEMENT,
			data : {
				"productId" : productId
			},
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					$.each(data, function(i, n) {
						console.log(n);
						n.nutrientUnitstr = CODE.transCode("PRODUCTUNIT",
								n.nutrientUnit);
						if (n.productNutrientDosage === 0) {
							n.productNutrientDosage = "0";
						}
						$("#productId").data(n.nutrientId, n.nutrientName);
						//n.productNutrientDosageStr=n.productNutrientDosage;
						$('#reportTable').bootstrapTable('append', n);
					});

				}
			},
			error : function(e) {
				console.log(e.responseText);
			}
		});
	}
	//删除行
	function removeRow(rowIndex) {
		//$("#productId").removeData(nutrientId);
		$('#reportTable').bootstrapTable('removeRow', rowIndex);
	}
	openModel = function() {
		itemModel.openItemModel();
	};
</script>
</head>

<body>
	<div class="container-fluid">
		<div class="row row-top">
			<div class="col-xs-12">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<i class="fa fa-table fa-fw"></i> 商品属性
					</div>
					<div class="panel-body">
						<form id="updateForm" class="form-horizontal"
							action="${common.basepath}/${applicationScope.URL.Master.ADD_PREGNANCYCOURSE}"
							method="post">
							<div class="col-xs-12 border-bottom-blue">
								<label class="label-title"><i class="fa fa-edit fa-fw"></i>商品信息</label>
							</div>
							<input id="productId" name="productId" type="hidden"
								value="${product.productId }" />
							<div class="col-xs-9 col-xs-offset-1 row-top">
								<div class="form-group">
									<label class="col-xs-2 control-label">编码</label>
									<div class="col-xs-8">
										<input value="${product.productCode }" class="form-control"
											readonly="readonly" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-2 control-label">产品名称</label>
									<div class="col-xs-8">
										<input value="${product.productGoodsName }"
											class="form-control" readonly="readonly" />
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div id="choice" class="container-fluid">
				<div class="row row-top">
					<div class="col-xs-12">
						<div
							class="row margin-zero padding-zero border-top-1px border-bottom-1px">
							<div class="menu btn-group">
								<button id="addintakeTemplatePage"
									style="margin-left:16px;margin-bottom:1px;"
									name="intakeTemplatePage" type="button" onclick="openModel();"
									class="btn btn-primary">
									<i class="fa fa-plus fa-fw"></i> 添加
								</button>
							</div>
						</div>
						<div class="panel panel-primary">
							<div class="panel-heading">
								<i class="fa fa-table fa-fw"></i> 营养素表
							</div>
							<div class="panel-body">
								<div class="row">
									<div class="col-xs-12">
										<form id="optionQuery" name="optionQuery" action=""
											method="post" class="form-horizontal">
											<input class="form-control" type="hidden" name="product"
												id="product" value="${product.productId }" />
										</form>
									</div>
								</div>
							</div>
							<div class="tab-pane fade in active" id="tab2">
								<table class="table table-striped table-hover" id="reportTable"></table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-xs-12 border-top-blue row-top">
				<div class="col-xs-8 col-xs-offset-3 ">
					<div class="col-xs-4">
						<button class="btn btn-primary btn-block" onclick="saveData()">
							<i class='fa fa-save fa-fw'></i>保存
						</button>
					</div>
					<div class="col-xs-4">
						<button class="btn btn-primary btn-block" type="button"
							onclick="redirectPage('query');">
							<i class='fa fa-share-square-o fa-fw'></i>返回
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<div id="itemModel" class="modal fade">
	<div class="modal-dialog modal-lg" style="width: 1050px;">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">元素库</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12" id="nutrientCondition">
						<form id="nutrientQuery" name="nutrientQuery"
							action="${common.basepath }/${applicationScope.URL.Master.QUERY_NUTRIENT}"
							method="post" class="form-horizontal">
							<div class="pull-right">
								<input type='text' class='form-control' placeholder='请输入检索内容' />
							</div>
						</form>
					</div>
				</div>
				<div class="table-responsive">
					<table id="nutrientTable" class="table table-bordered table-hover">
						<thead>
							<tr class="active">
								<th class="text-center">编码</th>
								<th class="text-center">元素名称</th>
								<th class="text-center">单位</th>
								<th class="text-center">选择</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button id="closeUserButton" type="button" class="btn btn-primary"
					data-dismiss="modal">返回</button>
			</div>
		</div>
	</div>
</div>



<script type="text/javascript">
	$().ready(function() {
		$('#itemModel').on('show.bs.modal', function(e) {
			nutrientTable = datatable.table(tableOptions);
		});
		$('#itemModel').on('hidden.bs.modal', function() {
			$('#nutrientName').val("");
		});
		inittable();
		initData("${product.productId}");
	});

	var itemModel = {};
	var nutrientTable;// table，注意命名不要重复

	//配置datatable
	var tableOptions = {
		id : "nutrientTable",
		form : "nutrientQuery",
		columns : [
				{
					"data" : "nutrientId",
					"sClass" : "text-center"
				},
				{
					"data" : "nutrientName",
					"sClass" : "text-center"
				},
				{
					"data" : "nutrientUnit",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("PRODUCTUNIT", data);
					}
				},
				{
					"data" : null,
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return "<button class='btn btn-primary btn-xs' type='button' "
								+ "onclick=saveMemData('"
								+ data.nutrientName
								+ "','"
								+ data.nutrientUnit
								+ "','"
								+ data.nutrientId
								+ "')>"
								+ "<i class='fa fa-plus fa-fw'></i></button>";
					}
				},

		],
		condition : "nutrientCondition"
	};

	itemModel.openItemModel = function() {
		$('#itemModel').modal('show');
	};
</script>
</html>