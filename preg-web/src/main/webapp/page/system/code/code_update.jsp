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
<script type="text/javascript" src="${common.basepath}/page/system/code/code_update.js"></script>

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
.ztree {
    margin: 0;
    padding: 5px;
    color: #333;
    padding-left: 7%;
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
	if($("#codeType").val()==2){
		beforeClick("zTree", treeObj.getNodes()[0]);
		treeObj.selectNode(treeObj.getNodes()[0]);
	}
});
</script>
</head>
<body>
<div id="orderModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-center">代码表排序</div>
					<div class="panel-body">
						<div class="form-group col-xs-offset-3">
							<ul id="orderTree" class="ztree"></ul>
						</div>
					</div>
				</div>
				<div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="button" id="orderModalButton" class="btn btn-primary btn-block">确定</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<form id="codeForm" class="form-horizontal">
<input type="hidden" id="parentCodeKind" name="parentCodeKind" />
<input type="hidden" id="parentCodeValue" name="parentCodeValue" />
<input type="hidden" id="codeGrade" name="codeGrade" />
<input type="hidden" id="codeOrder" name="codeOrder" />
<input type="hidden" id="codeType" name="codeType" value="${codeType }"/>
<input type="hidden" id="codeId" name="codeId" />
<div id="codeModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-center">编辑代码信息</div>
					<div class="panel-body">
						<div class="form-group" id="codeType_div" style="display: none">
							<label class="col-xs-4 control-label">级别类型</label>
							<div class="col-xs-6">
								<select id="selectType" name="selectType" class="form-control" onchange="setCodeType(this.value)">
									<option value="">==请选择级别类型==</option>
									<option value="1">目录</option>
									<option value="2">类型</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">代码类型</label>
							<div class="col-xs-6">
								<input id="codeKind" name="codeKind" class="form-control" maxlength="30" type="text" readonly/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">代码名称</label>
							<div class="col-xs-6">
								<input id="codeName" name="codeName" class="form-control" maxlength="50" type="text" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">代码值</label>
							<div class="col-xs-6">
								<input id="codeValue" name="codeValue" class="form-control" maxlength="30" type="text" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">代码描述</label>
							<div class="col-xs-6">
								<input id="codeDesc" name="codeDesc" class="form-control" maxlength="100" type="text" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-xs-4 control-label">代码备注</label>
							<div class="col-xs-6">
								<textarea id="codeRemark" name="codeRemark" class="form-control" maxlength="100"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="submit" class="btn btn-primary btn-block">确定</button>
					</div>
					<div class="col-xs-2 no-right">
						<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3" id="zTree_div">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 选择代码类型
			</div>
			<div class="panel-body">
				<ul id="zTree" class="ztree"></ul>
			</div>
		</div>
		<div class="panel panel-default col-xs-9" id="cust_div">
			<div class="panel-heading">
				<i class="fa fa-edit fa-fw"></i> 编辑代码列表
			</div>
			<div class="panel-body">
				<div class="row" id="updateCodeCondition">
					<form id="updateCodeQueryForm" action="${common.basepath}/${applicationScope.URL.System.CODE_QUERY}" method="post" class="form-horizontal">
						<input type="hidden" name="parentCodeKind" />
						<input type="hidden" name="parentCodeValue" />
						<div class="col-xs-3 no-left">
							<input type="text"  class='form-control' placeholder='请输入检索内容' />
						</div>
						<div class="col-xs-6">
							
								<button id="searchButton" type="button" name="codePage" class="btn btn-default">
									<i class="fa fa-search fa-fw"></i>查询
								</button>
								<div class="vertical-line"></div>
						  		<button id="addButton" name="codePage" type="button" class="btn btn-default" disabled>
						     		<i class="fa fa-plus fa-fw"></i> 增加
						  		</button>
					      		<button id="editButton" name="codePage" type="button" class="btn btn-default" disabled>
						     		<i class="fa fa-edit fa-fw"></i> 编辑
						  		</button>
						  		<button id="removeButton" name="codePage" type="button" class="btn btn-default" disabled>
						     		<i class="fa fa-remove fa-fw"></i> 删除
						  		</button>
						  		<button id="orderButton" name="codePage" type="button" class="btn btn-default" disabled>
						     		<i class="fa fa-exchange fa-fw"></i> 排序
						  		</button>
						
						</div>
					</form>
				</div>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered table-hover" id="updateCodeTable">
					<thead>
						<tr class="active">
							<th class="text-center">选择</th>
							<th class="text-center">代码类型（codeKind）</th>
							<th class="text-center">代码名称（codeName）</th>
							<th class="text-center">代码值（codeValue）</th>
					   	</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
</body>
</html>
