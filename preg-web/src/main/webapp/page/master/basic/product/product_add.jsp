<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet"href="${common.basepath}/common/plugins/zTree/zTreeStyle.css"type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.all-3.5.js"></script>
<%@ include file="/common/common.jsp"%>

<style type="text/css">
.ztree li span.button.home_ico_open {
	margin-right: 2px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/1_open.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.home_ico_close {
	margin-right: 2px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/1_close.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.mulu_ico_open {
	margin-right: 4px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.mulu_ico_close {
	margin-right: 4px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.mulu_ico_docu {
	margin-right: 4px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.menu_ico_open {
	margin-right: 4px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/menu.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.menu_ico_close {
	margin-right: 4px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/menu.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.menu_ico_docu {
	margin-right: 4px;
	background:
		url(${common.basepath}/common/plugins/zTree/img/diy/menu.png)
		no-repeat scroll 0 0 transparent;
	vertical-align: top;
	*vertical-align: middle
}

.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}

.ztree {
	margin: 0;
	padding: 5px;
	color: #333;
	padding-left: 0px;
	padding-top: 8px;
}

.ztree li ul {
	padding: 0 0 5px 30px;
}

.ztree li span.button.chk {
	width: 13px;
	height: 13px;
	margin: 0 7px 0 0;
	cursor: auto;
}
</style>
<script type="text/javascript">
	//树结点集合
	var zNodes = ${treeNodes};
	var products = ${products};
	var productAreas = [];//已经输入的商品区域
	var productUnits = [];//已经输入的剂量单位
	var productUsageMethods = [];//已经输入的用药方法
	var productPackageUnits = [];//已经输入的整包单位
	var productStandards = [];//已经输入的商品规格
	$.each(products, function(n, value) {
		if (productAreas.indexOf(value.productArea) == -1) {
			productAreas.push(value.productArea);
		}
		if (productUnits.indexOf(value.productUnit) == -1) {
			productUnits.push(value.productUnit);
		}
		if (productUsageMethods.indexOf(value.productUsageMethod) == -1) {
			productUsageMethods.push(value.productUsageMethod);
		}
		if (productPackageUnits.indexOf(value.productPackageUnit) == -1) {
			productPackageUnits.push(value.productPackageUnit);
		}
		if (productStandards.indexOf(value.productStandard) == -1) {
			productStandards.push(value.productStandard);
		}
	});
	//*****************************************zTree开始****************************************
	//当前选中结点
	var selectNode;
	//树对象
	var treeObj;
	//配置
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeCheck
		}
	};

	$().ready(function() {
		//初始化生成树
		$.fn.zTree.init($("#zTree"), setting, zNodes);
		treeObj = $.fn.zTree.getZTreeObj("zTree");
		treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
		$("#modalSureButton").click(function() {
			$("#productCategoryName").val(selectNode.name);
			$("#productCategory").val(selectNode.id);
			$("#addMenuModal").modal("hide");
		});
		var autoOption = {
			max : 20,
			highlight : false,
			multiple : true,
			multipleSeparator : "",
			scroll : true,
			scrollHeight : 300
		};

		//设置默认
		$("#productAttribute").val("A");
		$("#productIsOfficina").val("2");
		$("#productIsAssess").val("2");

		$("#productArea").autocomplete(productAreas, autoOption);
		$("#productUnit").autocomplete(productUnits, autoOption);
		$("#productUsageMethod").autocomplete(productUsageMethods, autoOption);
		$("#productPackageUnit").autocomplete(productPackageUnits, autoOption);
		$("#productStandard").autocomplete(productStandards, autoOption);
	});

	//节点选中事件
	function beforeCheck(treeId, treeNode) {
		treeObj.selectNode(treeNode);
		selectNode = treeNode;
	}
	function chooseMenuModal() {
		$("#productCategoryName").val("");
		$("#productCategory").val("");
		$("#addMenuModal").modal("show");
	}
</script>
<script type="text/javascript"
	src="${common.basepath}/page/master/basic/product/product.js"></script>
<title>添加商品记录</title>
</head>
<body>
	<form id="addMenuForm" class="form-horizontal">
		<input id="menuIdStr" name="menuIdStr" type="hidden" /> <input
			name="rightId" type="hidden" />
		<div id="addMenuModal" class="modal fade bs-example-modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="modal-title text-center">选择分类</h3>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label class="col-xs-4 control-label">商品分类</label>
							<div class="col-xs-7">
								<ul id="zTree" class="ztree"></ul>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" id="modalSureButton">确定</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">清空</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	<div class="container-fluid">
		<div class="row row-top">
			<div class="panel panel-default">
				<div class="panel-heading">
					<label class="label-title"><i class="fa fa-edit fa-fw"></i>
						增加商品</label>
				</div>
				<form id="addForm" class="form-horizontal" action="${common.basepath}${applicationScope.URL.Master.PRODUCT_ADD}" method="post">
					<div class="panel-body">
						<div class="col-xs-11">
							<div class="form-group">
								<label class="col-xs-2 control-label">商品分类</label>
								<div class="col-xs-4">
									<input id="productCategoryName" name="productCategoryName"
										class="form-control" onclick="chooseMenuModal();" readonly
										placeholder="点击选择适合的分类"></input> <input id="productCategory"
										style="display:none;" name="productCategory"
										class="form-control" />
								</div>
								<label class="col-xs-2 control-label">商品代码</label>
								<div class="col-xs-4">
									<input id="productCode" name="productCode" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">商品名称</label>
								<div class="col-xs-4">
									<input id="productGoodsName" name="productGoodsName"
										class="form-control" />
								</div>
								<label class="col-xs-2 control-label">通用名称</label>
								<div class="col-xs-4">
									<input id="productCommonName" name="productCommonName"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">产品名称</label>
								<div class="col-xs-4">
									<input id="productName" name="productName" class="form-control" />
								</div>
								<label class="col-xs-2 control-label">使用属性</label>
								<div class="col-xs-4">
									<select id="productAttribute" name="productAttribute"
										class="form-control"></select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">药局处方</label>
								<div class="col-xs-4">
									<select id="productIsOfficina" name="productIsOfficina"
										class="form-control"></select>
								</div>
								<label class="col-xs-2 control-label">剂量评估</label>
								<div class="col-xs-4">
									<select id="productIsAssess" name="productIsAssess"
										class="form-control"></select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">能量评估</label>
								<div class="col-xs-4">
									<select id="productIsEnergy" name="productIsEnergy"
										class="form-control"></select>
								</div>
								<label class="col-xs-2 control-label">地区</label>
								<div class="col-xs-4">
									<input id="productArea" name="productArea" class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">剂量单位</label>
								<div class="col-xs-4">
									<input id="productUnit" name="productUnit" class="form-control" />
								</div>
								<label class="col-xs-2 control-label">剂量说明</label>
								<div class="col-xs-4">
									<input id="productDosageDesc" name="productDosageDesc"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">用药方法</label>
								<div class="col-xs-4">
									<input id="productUsageMethod" name="productUsageMethod"
										class="form-control" />
								</div>
								<label class="col-xs-2 control-label">用药频次</label>
								<div class="col-xs-4">
									<select id="productFrequency" name="productFrequency"
										class="form-control"></select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">商品规格</label>
								<div class="col-xs-4">
									<input id="productStandard" name="productStandard"
										class="form-control" />
								</div>
								<label class="col-xs-2 control-label">整包单位</label>
								<div class="col-xs-4">
									<input id="productPackageUnit" name="productPackageUnit"
										class="form-control" list="packageUnitList" placeholder="双击后可选择"/>
									<datalist id="packageUnitList"></datalist> 
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">用药疗程</label>
								<div class="col-xs-4">
									<input id="productTreatment" name="productTreatment"
										class="form-control" />
								</div>
								<label class="col-xs-2 control-label">可用天数</label>
								<div class="col-xs-4">
									<div class="input-group">
										<input id="productDays" name="productDays"
											class="form-control" />
										<div class="input-group-addon">天</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">成本价</label>
								<div class="col-xs-4">
									<div class="input-group">
										<input id="productPurchasePrice"
											name="productPurchasePricestr" class="form-control" value="0" />
										<div class="input-group-addon">元</div>
									</div>
								</div>
								<label class="col-xs-2 control-label">销售价</label>
								<div class="col-xs-4">
									<div class="input-group">
										<input id="productSellPrice" name="productSellPricestr"
											class="form-control" value="0" />
										<div class="input-group-addon">元</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">品牌</label>
								<div class="col-xs-4">
									<input id="productBrand" name="productBrand"
										class="form-control" />
								</div>
								<label class="col-xs-2 control-label">批准文号</label>
								<div class="col-xs-4">
									<input id="productLicense" name="productLicense"
										class="form-control" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">过敏源</label>
								<div class="col-xs-10">
									<textarea id="productAllergy" name="productAllergy"
										class="form-control" maxlength="100"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">使用方法</label>
								<div class="col-xs-10">
									<textarea id="productUsage" name="productUsage"
										class="form-control" maxlength="500"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">适合人群</label>
								<div class="col-xs-10">
									<textarea id="productUser" name="productUser"
										class="form-control" maxlength="500"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">注意事项</label>
								<div class="col-xs-10">
									<textarea id="productMatters" name="productMatters"
										class="form-control" maxlength="200"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-2 control-label">功效说明</label>
								<div class="col-xs-10">
									<textarea id="productEffect" name="productEffect"
										class="form-control" maxlength="500"></textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-12 col-xs-offset-2 ">
									<button class="btn btn-primary" type="submit">
										<i class='fa fa-save fa-fw'></i> 保存
									</button>
									<button class="btn btn-primary" type="button"
										onclick="redirectPage('query');">
										<i class='fa fa-share-square-o fa-fw'></i> 返回
									</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$().ready(function() {
				common.initCodeSelect("PRODUCTUNIT", "PRODUCTUNIT",
						"productUnit");
				common.initCodeSelect("PRODUCT_ATTRIBUTE", "PRODUCT_ATTRIBUTE",
						"productAttribute");
				common.initCodeSelect("PRODUCTFREQUENCY", "PRODUCTFREQUENCY",
						"productFrequency");
				common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE",
						"productIsOfficina");
				common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE",
						"productIsAssess");
				common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE",
						"productIsEnergy");
				$("#addForm").validate(addOptions);
				//加入必填项提示
				common.requiredHint("addForm", addOptions);

				//可输入可选择的示例
				common.initSelectAndInput("packageUnitList", "mas_product", "product_package_unit");
			});
</script>
</html>