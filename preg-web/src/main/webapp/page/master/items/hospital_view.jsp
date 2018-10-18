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
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/items/hospital_view.js"></script>
<body>
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 医院类型
			</div>
			<div class="panel-body" id="left_div">
				<div id="zTree_div" class="inp_width" style="max-height: 480px; overflow-y: scroll;">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default col-xs-9">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 医院详细信息列表
			</div>
			<div class="panel-body" id="cust_div" style="height: auto !important;">
		       <div id="itemCondition" class="form-inline">
			       <form id="itemQuery" action="${common.basepath }/${applicationScope.URL.item.HOSPITAL_QUERY}">
			           	<input type="hidden" name="hospitalType"     id="hospitalType-form"     value="null"/>
			           	<input type="hidden" name="hospitalClassify" id="hospitalClassify-form" value="null"/>
			            <input style="width:210px;" type="text"   name="hospitalName" class='form-control' placeholder='医院名称/医院类型/所在城市'/>
				   		<button type="button" id="searchButton" name="itemPage" class="btn btn-default"><i class="fa fa-search fa-fw"></i>查询</button>
				   			<div class="vertical-line"></div>
						<button id="addItemButton" name="itemPage" type="button" class="btn btn-default">
				   			<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button type="button" id="editButton" name="itemPage"
							class="btn btn-default">
							<i class='fa fa-edit fa-fw'></i> 编辑
						</button>
						<button type="button" id="removeButton" name="itemPage"
							class="btn btn-default">
							<i class='fa fa-remove fa-fw'></i> 删除
						</button>
			       </form>
		       </div>
				<div class="table-responsive" style="padding-top: 10px;">
					<table id="itemListTable" class="table table-bordered table-hover">
						<thead>
							<tr class="active">
								<th class="text-center">选择</th>
								<th class="text-center">医院类型</th>
						     	<th class="text-center">医院名称</th>
						     	<th class="text-center">所在城市</th>
						     	<th class="text-center">是否合作</th>
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
	                            <input type="hidden" name="hospitalId"   id="hospitalId"/>
	                            <input type="hidden" name="hospitalType" id="hospitalType"/>
	                            <label class="col-xs-2 control-label">医院类型</label>
	                            <div class="col-xs-10">
	                                <input id="hospitalClassify" name="hospitalClassify" type="text" class="form-control" disabled="disabled"/>
	                            </div>
	                        </div>
	                        <div class="form-group">
 	                            <label class="col-xs-2 control-label">医院名称</label>	                        	                        
	                            <div class="col-xs-10">
	                                <input id="hospitalName" name="hospitalName" class="form-control"/>
	                            </div>		
	                        </div>
					        <div class="form-group">
				                <label class="col-xs-2 control-label">所在城市</label>
				                <div class="col-xs-10">
				                	<div class="row">
					                	<div class="col-xs-4">
					                    	<select style="border-color:red;" id="hospitalCapital" name="hospitalCapital" class=" form-control" onchange="common.getChinaArea(this.value,'hospitalCity','custDistrict')">
					                 			<option value="">=请选择区域=</option>
					                 		</select>
				                 		</div>
				                 		<div class="col-xs-4">
					                  		<select style="border-color:red;" id="hospitalCity" name="hospitalCity" class="col-xs-4 form-control" onchange="common.getChinaArea(this.value,'','hospitalArea')">
					                 			<option value="">选择市</option>
					                 		</select>
				                 		</div>
					                	<div class="col-xs-4">
						                 	<select style="border-color:red;" id="hospitalArea" name="hospitalArea" class="form-control">
						                 		<option value="">选择区</option>
						                 	</select>
					                	</div>
						            </div>
						        </div>
							</div>
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">是否合作</label>
	                            <div class="col-xs-10">
	                                <select id="hospitalWorkWith" name="hospitalWorkWith" class="form-control"></select>
	                            </div>
	                        </div>	                        	                        
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i>确认</button>
					</div>
				</div>				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>

<!-- 添加检验项目科室model -->
<div id="addItemTypeClassifyModal" class="modal fade bs-example-modal form-horizontal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i><span id="addLevelTypeNameSpan"></span>
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row">
						<div class="col-xs-9 col-xs-offset-1 row-top">
							<div class="form-group">
								<label class="col-xs-4 control-label" id="addLevelTypeNameLabel"></label>
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
						<i class="fa fa-edit fa-fw"></i><span id="editLevelTypeNameSpan"></span>
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row">
						<div class="col-xs-9 col-xs-offset-1 row-top">
							<div class="form-group">
								<label class="col-xs-4 control-label" id="editLevelTypeNameLabel"></label>
								<div class="col-xs-6">
									<input type="hidden" id="oldTypeName"/>
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