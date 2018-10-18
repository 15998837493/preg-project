<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>商品类型编辑页面</title>
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet"
	href="${common.basepath}/common/plugins/zTree/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
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
</style>

<script type="text/javascript">
	//树结点集合
	var zNodes = ${treeNodes};
</script>
<script type="text/javascript"
	src="${common.basepath}/page/master/basic/product/product_category_view.js"></script>
</head>
<body>
	<form id="addMenuForm" class="form-horizontal"
		action="${common.basepath }/${applicationScope.URL.Customer.PRODUCT_CATALOG_ADD}">
		<input type="hidden" name="catalogParentId" /> <input type="hidden"
			name="catalogOrder" />
		<div id="addMenuModal" class="modal fade bs-example-modal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue margin-zero">
							<div class="panel-heading text-center">添加商品类别</div>
							<div class="row">
								<div class="col-xs-9 col-xs-offset-1 row-top">
									<div class="form-group">
										<label class="col-xs-4 control-label">类别名称</label>
										<div class="col-xs-6">
											<input name="catalogName" class="form-control" maxlength="50"
												type="text" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero" style="padding-bottom: 15px;">
						<div class="col-xs-2 col-xs-offset-8 ">
							<button type="submit" class="btn btn-primary btn-block">确定</button>
						</div>
						<div class="col-xs-2 ">
							<button type="button" class="btn btn-primary btn-block"
								data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</form>
	<div class="container-fluid">
		<div class="row row-top">
			<div class="panel panel-default col-xs-3">
				<div class="panel-heading">
					<i class="fa fa-filter fa-fw"></i> 商品类别管理
				</div>
				<div class="panel-body" id="zTree_div">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
			<div class="panel panel-default col-xs-9">
				<div class="panel-heading">
					<i class="fa fa-filter fa-fw"></i> 编辑商品类别
				</div>
				<div class="panel-body" id="cust_div">
					<form id="updateForm" class="form-horizontal"
						action="${common.basepath}/${applicationScope.URL.Customer.PRODUCT_CATALOG_UPDATE}"
						method="post">
						<input type="hidden" id="menuNameOld" name="menuNameOld" />
						<div class="col-xs-12 table-responsive"></div>
						<div class="form-group">
							<label class="col-xs-4 control-label">上级菜单</label>
							<div class="col-xs-4">
								<input id="menuParentName" name="menuParentName"
									class="form-control" type="text" readonly />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">类别名称</label>
							<div class="col-xs-4">
								<input id="catalogId" name="catalogId" class="form-control"
									type="text" maxlength="50" readonly />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">类别名称</label>
							<div class="col-xs-4">
								<input id="catalogName" name="catalogName" class="form-control"
									type="text" maxlength="50" />
							</div>
						</div>
						<div class="form-group">
							<div class='col-xs-12 col-xs-offset-4'>
								<button class="btn btn-default" id="submitButton" type="submit"
									disabled>
									<i class='fa fa-save fa-fw'></i> 保存
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
