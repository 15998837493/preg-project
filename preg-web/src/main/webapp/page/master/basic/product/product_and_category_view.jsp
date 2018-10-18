<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>商品类型编辑页面</title>
<%@ include file="/common/common.jsp"%>
<link type=text/css rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<script type="text/javascript" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
<style type="text/css">
.ztree li span.button.home_ico_open{margin-right:2px; background: url(${common.basepath}/common/plugins/zTree/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.home_ico_close{margin-right:2px; background: url(${common.basepath}/common/plugins/zTree/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_open{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_close{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_docu{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.menu_ico_open{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/menu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.menu_ico_close{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/menu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.menu_ico_docu{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/menu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
.paddingBottom {
	padding-bottom: 15px;
}
.margin-zero{
	margin: 0px;
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
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};
//所有元素
var elementList = ${elementList};
</script>
<script type="text/javascript"
	src="${common.basepath}/page/master/basic/product/product_and_category_view.js"></script>
</head>
<body>
<!-- 添加类别model -->
<form id="addMenuForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.Customer.PRODUCT_CATALOG_ADD}">
	<input type="hidden" name="catalogParentId" id="catalogParentId"/> 
	<input type="hidden" name="catalogOrder" id="catalogOrder"/>
	<div id="addMenuModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body" style="padding-bottom: 0px;">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center"><i class="fa fa-tag fa-fw"></i> 编辑商品类别<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="row">
							<div class="col-xs-9 col-xs-offset-1 row-top">
								<div class="form-group">
									<label class="col-xs-4 control-label">类别名称</label>
									<div class="col-xs-6">
										<input id="catalogId" name="catalogId" class="form-control" type="hidden" />
										<input id="catalogName" name="catalogName" class="form-control" maxlength="50" type="text" />
										<input id="catalogNameOld" name="catalogNameOld" class="form-control" type="hidden" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body padding-zero" style="padding-bottom: 15px;">
					<div class="col-xs-2 col-xs-offset-10 ">
						<button type="submit" class="btn btn-primary btn-block">确定</button>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<!-- 添加/编辑商品的model -->
<form id="editProductForm" name="editProductForm" action="" class="form-horizontal" method="post">
	<div id="editProductModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg" style="width: 1200px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue margin-zero" id="productDiv">
						<div class="panel-heading text-center"><i class="fa fa-tag fa-fw"></i> 编辑商品<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="row">
							<div class="col-xs-4 row-top">
						        <div class="form-group">
						        	<label class="col-xs-5 control-label">图片</label>
						        	<div class="col-xs-7">
						        		<img class="img-thumbnail" id="showpic" style="width:100px;height:100px;margin-bottom:5px;"/>
							        	<input type="file" name="file_upload" id="file_upload"  class="form-control" />
							        	<input type="hidden" id="productPic" name="productPic" class="form-control"/>				        	
						        	</div>
				              	</div>	
							</div>
							<div class="col-xs-7 row-top">
								<div class="form-group">
					        		<label class="col-xs-2 control-label">类别</label>
					                <div class="col-xs-4">
					                	<input type="hidden" id="productId" name="productId"/>
					                    <input type="hidden" id="productCategory" name="productCategory"/>
					                    <div id="productCategoryDiv"></div>
					                </div>
					                <label class="col-xs-2 control-label">助记码</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productMemoryMark" name="productMemoryMark" class="form-control" maxlength="200"/>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">名称</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productName" name="productName" class="form-control" maxlength="200"/>
					                    <input type="hidden" id="productNameOld" name="productNameOld"/>
					                </div>
					                <label class="col-xs-2 control-label">剂量说明</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productDosageDesc" name="productDosageDesc" class="form-control" maxlength="20"/>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">剂量单位</label>
					                <div class="col-xs-4">
					                    <input type="text" placeholder="双击可选择" id="productUnit" name="productUnit" list="productUnitList" class="form-control" maxlength="20"/>
					                    <datalist id="productUnitList"></datalist>
					                </div>
					        		<label class="col-xs-2 control-label">单次剂量</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productDosage" name="productDosage" class="form-control" maxlength="20"/>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">用药频次</label>
					                <div class="col-xs-4">
					                    <select id="productFrequency" name="productFrequency" class="form-control"></select>
					                </div>
					        		<label class="col-xs-2 control-label">用药方法</label>
					                <div class="col-xs-4">
					                    <input type="text" placeholder="双击可选择" id="productUsageMethod" name="productUsageMethod" list="productUsageMethodList" class="form-control" maxlength="20"/>
					                    <datalist id="productUsageMethodList"></datalist>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">商品规格</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productStandard" name="productStandard" class="form-control"/>
					                </div>
					        		<label class="col-xs-2 control-label">品牌</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productBrand" name="productBrand" class="form-control" maxlength="20"/>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">成本价格</label>
					                <div class="col-xs-4">
					                	<div class="input-group">
						                    <input type="text" id="productPurchasePricestr" name="productPurchasePricestr" class="form-control" maxlength="5"/>
						                    <div class="input-group-addon">元</div>
					                	</div>
					                </div>
					        		<label class="col-xs-2 control-label">销售价格</label>
					                <div class="col-xs-4">
					                	<div class="input-group">
						                    <input type="text" id="productSellPricestr" name="productSellPricestr" class="form-control" maxlength="11"/>
						                    <div class="input-group-addon">元</div>
						                </div>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">批准文号</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productLicense" name="productLicense" class="form-control"/>
					                </div>
					        		<label class="col-xs-2 control-label">产地</label>
					                <div class="col-xs-4">
					                    <input type="text" placeholder="双击可选择" id="productArea" name="productArea" list="productAreaList" class="form-control" maxlength="11"/>
					                    <datalist id="productAreaList"></datalist>
					                </div>
					            </div>
					            <div class="form-group">
					        		<label class="col-xs-2 control-label">疗程</label>
					                <div class="col-xs-4">
					                    <input type="text" id="productTreatment" name="productTreatment" class="form-control"/>
					                </div>
					                <label class="col-xs-2 control-label">是否参与计算</label>
					                <div class="col-xs-4">
					                        <select class="form-control" id="productCalculation" name="productCalculation">
										      <option value=1>参与</option>
										      <option value=0>不参与</option>
										    </select>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">注意事项</label>
					                <div class="col-xs-10">
					                	<textarea id="productMatters" name="productMatters" class="form-control text-left">
					                	</textarea>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-2 control-label">功效说明</label>
					                <div class="col-xs-10">
					                	<textarea id="productEffect" name="productEffect" class="form-control text-left">
					                	</textarea>
					                </div>
					            </div>
							</div>
						</div>
					</div>
					<div class="panel panel-lightblue" id="productElementDiv">
						<div class="panel-heading text-center">元素信息</div>
						<div class="row">
							<div class="col-xs-12 row-top" id="elmentList">
							</div>
						</div>
					</div>
					<div class="panel-body text-right" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right" >
							<button type="submit" class="btn btn-primary btn-block" id="span_button">保存</button>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<div class="container-fluid">
	<div class="row row-top">
<!-- 		ztree部分 -->
		<div class="panel panel-default col-xs-3">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 商品类别管理
			</div>
			<div class="panel-body" id="left_div">
				<div id="zTree_div" class="inp_width">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
<!-- 		商品展示部分 -->
		<div class="panel panel-default col-xs-9">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 指定类别下的商品
			</div>
			<div class="panel-body" id="cust_div" style="height: auto !important;">
				<div class="form-inline" id="productCondition">
		            <form id="queryForm" action="${common.basepath }/${applicationScope.URL.Master.PRODUCT_QUERY}">
	                    <input type="hidden" name="productCategoryLike" id="categoryIdQuery"/>
	                       <input type='text' id='productNameSearch' name="productName" class='form-control' placeholder='请输入检索内容'/>
			      		<button id="searchButton" name="productButton"  type="button" class="btn btn-default">
			      			<i class="fa fa-search fa-fw"></i>查询
			      		</button>
			      		<div class="vertical-line"></div>
				  		<button id="addButton" name="productButton" type="button" class="btn btn-default">
				     		<i class="fa fa-plus fa-fw"></i> 增加
				  		</button>
				  		<button id="editButton" name="productButton" type="button" class="btn btn-default">
				     		<i class='fa fa-edit fa-fw'></i> 编辑
				  		</button>
				  		<button id="removeButton" name="productButton" type="button" class="btn btn-default">
				     		<i class="fa fa-remove fa-fw"></i> 删除
				  		</button>
		           	</form>
	           	</div>
				<div class="table-responsive" style="padding-top:10px;">
					<table id="productListTable" class="table table-bordered table-hover mytable table-padding-2">
						<thead>
							<tr class="active">
								<th class="text-center" style="width: 5%">选择</th>
								<th class="text-center" style="width: 40%">名称</th>
								<th class="text-center" style="width: 10%">剂量单位</th>
								<th class="text-center" style="width: 10%">剂量说明</th>
						     	<th class="text-center" style="width: 10%">用药频次</th>
						     	<th class="text-center" style="width: 10%">用药方法</th>
						     	<th class="text-center" style="width: 10%">规格</th>
						     	<th class="text-center" style="width: 5%">价格</th>
						   </tr>
						</thead>
					</table>
				</div>
	   		</div>
		</div>
	</div>
</div>
</body>
</html>
