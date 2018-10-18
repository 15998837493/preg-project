<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>诊断推断配置</title>
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" href="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.css">
<link rel="stylesheet" href="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.css">
<script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.js"></script>
<script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.js"></script>

<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${common.basepath}/common/mnt/css/ztree.css" />
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
<style type="text/css">
.tag-editor li {
	float: none;
	margin: 8px;
}
.tag-editor .tag-editor-tag {
    white-space: normal;
}
.tag-editor {
    text-align: left;
    border: 0px solid #cccccc;
}
.prompt{
	left: auto;
	float: right !important;
	position: absolute;
	z-index: 1000;
	min-width: 160px;
	padding: 5px 0;
	list-style: none;
	font-size: 14px;
	text-align: left;
	background-color: #fff;
	border-radius: 4px;
	box-shadow: 0 6px 12px rgba(0,0,0,0.175);
	background-clip: padding-box;
	box-sizing: border-box;
	font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
}
.prompt > li > a {
    display: block;
    padding: 3px 20px;
    clear: both;
    font-weight: normal;
    line-height: 1.42857143;
    color: #333;
    white-space: nowrap;
    cursor: pointer;
    text-decoration: none;
    background-color: transparent;
    list-style: none;
	font-size: 14px;
	text-align: left;
	font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
}
.prompt > li > a:HOVER {
    background-color:#EEEEEE;
}

.inp_width{
	width: 100%;
}
.autoWidth{
	width: 48.6%!important;
}
div [name="standard-div"]{
	margin-bottom:0px;
}
</style>
<script type="text/javascript">
//取消回车事件
$(document).keydown(function(event){
    switch(event.keyCode){
       case 13:return false; 
       }
});

var zNodes = ${treeNodes};// 树结点集合
var itemList = ${itemList};// 检验项目库列表
</script>
<script type="text/javascript" src="${common.basepath}/page/master/items/disease_analysis_quota.js"></script>
</head>
<body>
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3" >
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 选择疾病
			</div>
			<div class="panel-body" id="left_div">
				<div id="zTree_div" class="inp_width" style="max-height: 480px; overflow-y: scroll;">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default col-xs-9" >
			<div class="panel-heading">
				<i class="fa fa-list fa-fw"></i> 推断检验项目
			</div>
			<div class="panel-body" id="cust_div" style="height: auto !important;">
				<div id="quotaItemCondition" class="form-inline">
	            	<form id="queryForm" action="${common.basepath }/${applicationScope.URL.item.INSEPCT_ITEM_DISEASE_QUERY}">
                		<input type="hidden" id="diseaseId" name="diseaseId" /><!-- 诊断项目 -->
						<input type="hidden" id="itemId" name="itemId" /><!-- 检验项目 -->
						<input type="text" id='itemName' name="itemName" class='form-control' placeholder='请输入检验项目'/>
			      		<button type="button" id="searchButton" name="diseaseItemPage" class="btn btn-default" disabled>
			      			<i class="fa fa-search fa-fw"></i>查询
			      		</button>
			      		<div class="vertical-line"></div>
				  		<button id="addButton" name="diseaseItemPage" type="button" class="btn btn-default" disabled>
				     		<i class="fa fa-plus fa-fw"></i> 增加
				  		</button>
				  		<button id="configButton" name="diseaseItemPage" type="button" class="btn btn-default" disabled>
				     		<i class="fa fa-cog fa-fw"></i> 配置
				  		</button>
				  		<button id="groupButton" name="diseaseItemPage" type="button" class="btn btn-default" disabled>
				     		<i class="fa fa-suitcase fa-fw"></i> 组合
				  		</button>
				  		<button id="removeButton" name="diseaseItemPage" type="button" class="btn btn-default" disabled>
				     		<i class="fa fa-remove fa-fw"></i> 删除
				  		</button>
	           		</form>
			    </div>
				<div class="table-responsive" style="padding-top: 10px;">
					<table id="quotaListTable" class="table table-bordered table-hover">
						<thead>
							<tr class="active">
								<th class="text-center">选择</th>
								<th class="text-center">检验项目</th>
								<th class="text-center">检验项目分类</th>
						     	<th class="text-center">所属科室</th>
						     	<th class="text-center">单位</th>
						   </tr>
						</thead>
						<tbody id="t_body">
							<tr><td colspan='100' class='text-center'><h4>请先选择疾病</h4></td></tr>
						</tbody>
					</table>
				</div>
	   		</div>
		</div>
	</div>
</div>

<!-- 增加Modal -->
<div id="inspectItemModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 检验项目推断配置<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="panel-body form-inline" id="itemCondition">
                        <input id="itemName" name="itemName" class="form-control" type="text" placeholder="请输入检验项目" />
					</div>
					<div class="table-responsive">
						<table id="itemListTable" class="table table-bordered table-hover">
							<thead>
								<tr class="active">
									<th class="text-center">检验项目</th>
									<th class="text-center">检查项目分类</th>
							     	<th class="text-center">所属科室</th>
							     	<th class="text-center">单位</th>
							     	<th class="text-center">操作</th>
							   	</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 配置Modal -->
<div id="itemConfigModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-tag fa-fw"></i> 推断配置
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body form-horizontal">
						<div class="form-group">
							<label class="col-xs-3 control-label">检验项目名称</label>
	                        <div class="col-xs-7">
		                        <input id="configItemName" class="form-control" type="text" disabled/>
		                    </div>
		                    <div class="col-xs-2">
		                    	<button id="addConfigButton" name="diseaseItemPage" type="button" class="btn btn-primary" onclick="addDiseaseItemConfig();">添加配置</button>
		                    </div>
						</div>
					</div>
					<div id="diseaseItemConfig"></div>
				</div>
				<div class="panel-body padding-zero">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button id="saveDiseaseItemButton" name="diseaseItemPage" class="btn btn-primary btn-block">
							<i class="fa fa-save fa-fw"></i> 保存
						</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 组合Modal -->
<div id="itemGroupModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue no-bottom">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 推断组合<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="panel-body" style="padding: 0px;">
						<div class="col-xs-6 padding-zero">
							<div class="panel panel-default no-bottom" style="border-top: 0 solid;border-bottom: 0 solid;">
								<div class="panel-body no-left no-right" id="groupCheckbox_div"></div>
								<div class="col-xs-12 no-left no-right"><HR style="margin: 0px 0 5px;"></div>
								<div class="panel-body" style="padding: 0 5px 5px 5px;">
									<div class="col-xs-12 no-left no-right text-right">
										<button id="saveGroupButton" name="diseaseItemPage" class="btn btn-primary">确认</button>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-6 padding-zero">
							<div id="groupContent_div"></div>
						</div>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>