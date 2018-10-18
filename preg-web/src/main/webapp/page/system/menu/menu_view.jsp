<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑页面</title>
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${common.basepath}/common/mnt/css/ztree.css" />
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};
</script>
<script type="text/javascript" src="${common.basepath}/page/system/menu/menu_view.js"></script>
</head>
<body>
<form id="addMenuForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.Menu.MENU_ADD}">
	<input type="hidden" name="menuParent" />
	<input type="hidden" name="menuGrade" />
	<input type="hidden" name="menuOrder" />
	<div id="addMenuModal" class="modal fade bs-example-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
	               	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class='fa fa-remove'></i></button>
	               	<h4 class="modal-title" id="myModalLabel">添加功能菜单</h4>
	           	</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-xs-4 control-label">菜单类型</label>
						<div class="col-xs-6">
							<select name="menuType" onchange="setMenuSelect(this.value);" class="form-control">
								<option value="">==请选择菜单类型==</option>
								<option value="1">目录</option>
								<option value="2">菜单</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">菜单名称</label>
						<div class="col-xs-6">
							<input name="menuName" class="form-control" maxlength="50" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">菜单链接</label>
						<div class="col-xs-6">
							<input name="menuUrl" class="form-control" maxlength="100" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">菜单状态</label>
						<div class="col-xs-6">
							<select name="menuState" class="form-control">
								<option value="">==请选择菜单状态==</option>
								<option value="1">启用</option>
								<option value="0">停止</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">菜单图标</label>
						<div class="col-xs-6">
							<input name="menuIcon" class="form-control" maxlength="100" type="text" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">菜单备注</label>
						<div class="col-xs-6">
							<textarea name="menuRemark" class="form-control" maxlength="200"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
				  	<button type="submit" class="btn btn-default">确定</button>
				  	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>				
			</div>
		</div>
	</div>
</form>

<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 选择功能菜单
			</div>
			<div class="panel-body" id="zTree_div">
				<ul id="zTree" class="ztree"></ul>
			</div>
		</div>
		<div class="panel panel-default col-xs-9">
			<div class="panel-heading">
				<i class="fa fa-edit fa-fw"></i> 编辑菜单信息
			</div>
			<div class="panel-body" id="cust_div">
				<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Menu.MENU_UPDATE}" method="post">
					<input type="hidden" id="menuNameOld" name="menuNameOld" />
					<div class="col-xs-11">
						<div class="form-group">
							<label class="col-xs-3 control-label">上级菜单</label>
							<div class="col-xs-8">
								<input id="menuParentName" name="menuParentName" class="form-control" type="text" readonly/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单编码</label>
							<div class="col-xs-8">
								<input id="menuId" name="menuId" class="form-control" type="text" readonly />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单类型</label>
							<div class="col-xs-8">
								<select id="menuType" name="menuType" class="form-control" readonly >
									<option value="">==请选择菜单类型==</option>
									<option value="1">目录</option>
									<option value="2">菜单</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单名称</label>
							<div class="col-xs-8">
								<input id="menuName" name="menuName" class="form-control" type="text" maxlength="50"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单链接</label>
							<div class="col-xs-8">
								<input id="menuUrl" name="menuUrl" class="form-control" type="text" maxlength="100"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单状态</label>
							<div class="col-xs-8">
								<select id="menuState" name="menuState" class="form-control">
									<option value="">==请选择菜单状态==</option>
									<option value="1">启用</option>
									<option value="0">停止</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单图标</label>
							<div class="col-xs-8">
								<input id="menuIcon" name="menuIcon" class="form-control" type="text" maxlength="100"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-3 control-label">菜单备注</label>
							<div class="col-xs-8">
								<textarea id="menuRemark" name="menuRemark" class="form-control" maxlength="200"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-2 col-xs-offset-6">
								<button class="btn btn-primary btn-block" id="submitButton" type="submit" disabled>
									<i class='fa fa-save fa-fw'></i> 保存
								</button>						
							</div>
						</div>					
					</div>
				</form>
			</div>
		</div>	
	</div>
</div>
</body>
</html>