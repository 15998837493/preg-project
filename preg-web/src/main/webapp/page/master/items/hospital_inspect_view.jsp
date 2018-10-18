<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
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
</style>
<title>检验项目配置</title>
</head>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};
//dataTable常量
var itemData;
var itemRow;
var itemTable;
//按钮状态
var operateType;
//检验项目按钮状态
var opernerCatalog;
</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/items/hospital_inspect.js"></script>
<body>
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 检验项目分类
			</div>
			<div class="panel-body" id="left_div">
				<div id="zTree_div" class="inp_width" style="max-height: 480px; overflow-y: scroll;">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default col-xs-9">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 检验项目列表
			</div>
			<div class="panel-body" id="cust_div" style="height: auto !important;">
		       <div id="itemCondition" class="form-inline">
			       <form id="itemQuery" action="${common.basepath }/${applicationScope.URL.item.ITEM_QUERY}">
			           	<input type="hidden" name="itemType"     id="itemType-form"     value="null"/>
			           	<input type="hidden" name="itemClassify" id="itemClassify-form" value="null"/>
			            <input type="text"   name="itemName" class='form-control' placeholder='请输入检验项目'/>
				   		<button type="button" id="searchButton" name="itemPage" class="btn btn-default"><i class="fa fa-search fa-fw"></i>查询</button>
				   			<div class="vertical-line"></div>
						<button id="addItemButton" name="itemPage" type="button" class="btn btn-default">
				   			<i class="fa fa-plus fa-fw"></i> 增加
						</button>
			       </form>
		       </div>
				<div class="table-responsive" style="padding-top: 10px;">
					<table id="itemListTable" class="table table-bordered table-hover">
						<thead>
							<tr class="active">
								<th class="text-center">序号</th>
								<th class="text-center">检验项目</th>
						     	<th class="text-center">单位</th>
						     	<th class="text-center">参考标准</th>
						     	<th class="text-center">操作</th>
						   	</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 弹窗添加、编辑 -->
<form id="editItemForm" name="editItemForm" action="" class="form-horizontal" method="post">
<div id="editItemModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue ">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i><span id="edit_item"></span>
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>					
					<div class="row">
	                    <div class="col-xs-9 col-xs-offset-1 row-top">
	                        <div class="form-group">
	                            <input type="hidden" name="resultJson"   id="resultJson"/>
	                            <input type="hidden" name="itemId"       id="itemId"/>
	                            <input type="hidden" name="itemType"     id="itemType"/>
			           			<input type="hidden" name="itemClassify" id="itemClassify"/>
	                            <label class="col-xs-2 control-label">检验项目</label>
	                            <div class="col-xs-10">
	                                <input id="itemName" name="itemName" class="form-control" type="text"/>
	                            </div>
	                        </div>
	                        <div class="form-group">
 	                            <label class="col-xs-2 control-label">单位</label>	                        	                        
	                            <div class="col-xs-4">
	                                <input id="itemUnit" name="itemUnit" class="form-control"/>
	                            </div>	
 	                            <label class="col-xs-2 control-label">性别</label>	                        	                        
	                            <div class="col-xs-4">
	                                <select id="itemSex" name="itemSex" class="form-control"></select>
	                            </div>	
	                        </div>
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">参考标准</label>
	                            <div class="col-xs-10">
	                                <textarea id="itemRefValue" name="itemRefValue" class="form-control"></textarea>
	                            </div>
	                        </div>   
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">正常</label>
	                            <div class="col-xs-3">
	                                <input id="normalDown" name="checkNum" type="text" placeholder="下限值" class="form-control"/>
	                            </div>
	                            <div class="col-xs-3">
	                                <input id="normalUp" name="checkNum" type="text" placeholder="上限值" class="form-control"/>
	                            </div>
	                            <div class="col-xs-4">
	                                <input id="normal" type="text" maxlength="10" class="form-control"/>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">超标</label>
	                            <div class="col-xs-3">
	                                <select id="manyMark" class="form-control">
		                                <option value="">==请选择==</option>
		                                <option value="&gt;">大于</option>
		                                <option value="&gt;=">大于等于</option>
	                                </select>
	                            </div>
	                            <div class="col-xs-3">
	                                <input id="manyUp" name="checkNum" placeholder="上限值" type="text" class="form-control"/>
	                            </div>
	                            <div class="col-xs-4">
	                                <input id="many" type="text" maxlength="10" class="form-control"/>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">低标</label>
	                            <div class="col-xs-3">
	                                <select id="lessMark" class="form-control">
		                                <option value="">==请选择==</option>
		                                <option value="&lt;">小于</option>
		                                <option value="&lt;=">小于等于</option>
	                                </select>
	                            </div>
	                            <div class="col-xs-3">
	                                <input id="lessDown" name="checkNum" placeholder="下限值" type="text" class="form-control"/>
	                            </div>
	                            <div class="col-xs-4">
	                                <input id="less" type="text" maxlength="10" class="form-control"/>
	                            </div>
	                        </div>	                        	                        
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<!-- 编辑检验项目排序 -->
<div id="editItemOrderModal" class="modal fade bs-example-modal form-horizontal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue ">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i><span>编辑检验项目排序</span>
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row">
	                    <div class="col-xs-10 col-xs-offset-2 row-top">
	                        <div class="form-group">
 	                            <label class="col-xs-2 control-label">移动到序号</label>	                        	                        
	                            <div class="col-xs-6">
	                                <input id="editItemOrder" class="form-control" type="text"/>
	                            </div>	
	                        </div>	                        	                        
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="editItemOrderButton();"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 添加检验项目科室model -->
<div id="addItemTypeClassifyModal" class="modal fade bs-example-modal form-horizontal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i><span id="addLevelTypeNameSpan">编辑检验项目科室</span>
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row">
						<div class="col-xs-9 col-xs-offset-1 row-top">
							<div class="form-group">
								<label class="col-xs-4 control-label" id="addLevelTypeNameLabel">所属科室</label>
								<div class="col-xs-6">
									<input id="addItemTypeClassify" class="form-control" type="text" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="addItemTypeClassify();"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>	
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<!-- 编辑检验项目科室model -->
<div id="editItemTypeClassifyModal" class="modal fade bs-example-modal form-horizontal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i><span id="editLevelTypeNameSpan">编辑检验项目科室</span>
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row">
						<div class="col-xs-9 col-xs-offset-1 row-top">
							<div class="form-group">
								<label class="col-xs-4 control-label" id="editLevelTypeNameLabel">检验项目科室</label>
								<div class="col-xs-6">
									<input id="editItemTypeClassify" class="form-control" type="text" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="editItemTypeClassify();"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>	
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</html>