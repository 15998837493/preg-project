<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>编辑页面</title>
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/page/system/dept/dept_view.js"></script>

<style type="text/css">
.ztree li span.button.home_ico_open{margin-right:2px; background: url(${common.basepath}/common/plugins/zTree/img/diy/1_open.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.home_ico_close{margin-right:2px; background: url(${common.basepath}/common/plugins/zTree/img/diy/1_close.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_open{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_close{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.mulu_ico_docu{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/mulu.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.dept_ico_open{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/dept.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.dept_ico_close{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/dept.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.dept_ico_docu{margin-right:4px; background: url(${common.basepath}/common/plugins/zTree/img/diy/dept.png) no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle}
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
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

$(document).ready(function() {
	//初始化生成树
	$.fn.zTree.init($("#zTree"), setting, zNodes);
	treeObj = $.fn.zTree.getZTreeObj("zTree");
	treeObj.expandNode(treeObj.getNodes()[0], true, false, false, true);
	//treeObj.expandAll(true);
	layer.close(layer.index);
});
</script>
</head>
<body>
<form id="addDeptForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.System.DEPT_ADD}">
	<input type="hidden" name="deptParent" />
	<input type="hidden" name="deptGrade" />
	<input type="hidden" name="deptOrder" />
	<div id="addDeptModal" class="modal fade bs-example-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue margin-zero">
							<div class="panel-heading text-center">添加部门</div>
							<div class="row row-top">
							<div class="form-group">
								<label class="col-xs-3 col-xs-offset-1 control-label">部门名称</label>
								<div class="col-xs-6">
									<input name="deptName" class="form-control" maxlength="50" type="text" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 col-xs-offset-1 control-label">部门电话</label>
								<div class="col-xs-6">
									<input name="deptPhone" class="form-control" maxlength="100" type="text" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 col-xs-offset-1 control-label">部门类型</label>
								<div id="addTypeRadio"></div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 col-xs-offset-1 control-label">部门状态</label>
								<div class="col-xs-6">
									<select id="addStatus" name="deptStatus" class="form-control"></select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 col-xs-offset-1 control-label">部门备注</label>
								<div class="col-xs-6">
									<textarea name="deptRemark" class="form-control" maxlength="200"></textarea>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body padding-zero" style="padding-bottom: 15px;">
				<div class="col-xs-2 col-xs-offset-8 ">
					<button type="submit" class="btn btn-primary btn-block" >确定</button>
				</div>
				<div class="col-xs-2 ">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
			</div>
		</div>
	</div>
</form>
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 组织结构
			</div>
			<div class="panel-body" id="zTree_div">
				<ul id="zTree" class="ztree"></ul>
			</div>
		</div>
		<div class="panel panel-default col-xs-9">
			<div class="panel-heading">
				<i class="fa fa-edit fa-fw"></i> 编辑组织结构信息
			</div>
			<div class="panel-body" id="cust_div">
				<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.System.DEPT_UPDATE}" method="post">
				<input type="hidden" id="deptNameOld" name="deptNameOld" />
					<div class="col-xs-11">
					<div class="form-group">
						<label class="col-xs-3 control-label">上级部门</label>
						<div class="col-xs-8">
							<input id="deptParentName" name="deptParentName" class="form-control" type="text" readonly/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">部门编码</label>
						<div class="col-xs-8">
							<input id="deptId" name="deptId" class="form-control" type="text" readonly/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">部门名称</label>
						<div class="col-xs-8">
							<input id="deptName" name="deptName" class="form-control" type="text" maxlength="50"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">部门电话</label>
						<div class="col-xs-8">
							<input id="deptPhone" name="deptPhone" class="form-control" type="text" maxlength="50"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">部门类型</label>
						<div class="col-xs-8">
							<div id="deptTypeRadio"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">部门状态</label>
						<div class="col-xs-8">
							<select id="deptStatus" name="deptStatus" class="form-control"></select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 control-label">部门备注</label>
						<div class="col-xs-8">
							<textarea id="deptRemark" name="deptRemark" class="form-control" maxlength="200"></textarea>
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
