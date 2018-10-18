<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" type="text/css"  href="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.css">
<link type=text/css rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<link rel="stylesheet" type="text/css"  href="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.css">
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript"  src="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
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
	width: 70%;
}
.paddingBottom {
	padding-bottom: 15px;
}
</style>
<title>食材查询列表</title>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};
//按钮状态
var operateType;
//类别按钮状态
var opernerCatalog;
//当前选中结点
var selectNode; 
//树对象
var treeObj;
//datatable配置
var fmData;
var fmRow;
var fmTable;

//验证
var catalogValidator;
var foodValidator;

//所有元素
var elementList = ${elementList};

</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/basic/food_material_view.js"></script>
</head>
<body>
<!--  类别树形菜单，食材信息列表-->
<div class="panel-body" id="food_material_view">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div id="timeline-panel">
				<div class="panel panel-body ">
				<input type="hidden" id="id"  />
			    <div class="panel panel-default col-xs-3" id="left_div">
					<div class="panel-heading row">
						<i class="fa fa-filter fa-fw"></i> 食材类别管理
					</div>
					<div class="panel-body" >
						<div id="zTree_div" class="inp_width">
							<ul id="zTree" class="ztree"></ul>
						</div>
					</div>
				</div> 
				<div class="panel panel-default col-xs-9" id="cust_div">
					<div class="panel-heading row">
						<i class="fa fa-filter fa-fw"></i> 指定类别下的食材
					</div>
					<div class="panel-body">
						<div class="form-inline" id="fmCondition">
							<form action="${common.basepath }/${applicationScope.URL.foodMaterial.FOOD_MATERIAL_AJAX_QUERY}" id="fmQuery" method="post" >
								<input type="hidden" name="fmTypeLike" id="categoryIdQuery"/>
								<input id="fmName" name="fmName" class="form-control" type="text" placeholder="请输入食材名称" />
								<button type="button" id="searchss" name="fmPage" class="btn btn-primary">
									<i class="fa fa-search fa-fw"></i>搜索
								</button>
								<div class="vertical-line"></div>
								<button id="addFmPage" name="fmPage" type="button" class="btn btn-primary">
									<i class="fa fa-plus fa-fw"></i> 增加
								</button>
								<button id="editFmPage" name="fmPage" type="button" class="btn btn-primary">
									<i class="fa fa-edit fa-fw"></i> 编辑
								</button>
								<button id="removeFmPage" name="fmPage" type="button" class="btn btn-primary">
									<i class="fa fa-remove fa-fw"></i> 删除
								</button>
							</form>
						</div>
						<div class="table-responsive" style="padding-top: 10px;">
							<table id="fmListTable" class="table table-bordered table-hover" >
								<thead>
									<tr class="active">
										<th class="text-center" width="80px;">操作</th>
										<th class="text-center" width="280px;">名称</th>
										<th class="text-center" width="200px">别名</th>
										<th class="text-center" width="100px;">类别</th>
										<th class="text-center" width="80px;">能量(kcal)</th>
										<th class="text-center" width="80px;">蛋白质(g)</th>
										<th class="text-center" width="80px;">脂肪(g)</th>
										<th class="text-center" width="100px;">碳水化合物(g)</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
				</div>
			</div>
		</li>
	</ul>
</div>

<!--  编辑食材信息modal-->
<div class="panel-body" style="display: none;" id="food_material_edit">
	<ul id="timeline">
	<li class="timeline-inverted">
	<div id="timeline-panel">
		<div class="form-group text-left">
			<button id="returnList" type="button" name="fmPage" class="btn btn-primary">返回列表</button>	
		</div>
		<form action="" id="editForm" name="editForm" method="post" class="form-horizontal">	
       	<div class="panel panel-default">  
       		<div class="panel-heading">
				<i class="fa fa-edit fa-fw"></i> 食材信息
			</div>
			<div class="panel-body">
	        	<div class="form-group">
	                <label class="col-xs-2 control-label">食材类别</label>
	                <div class="col-xs-4">
	                	<input type="hidden" id="fmType" name="fmType"/>
	                	<div style="width:165px;">
	                		<div id="fmTypeDiv"></div>
	                	</div>
	                </div>   
	            </div>
	        	<div class="form-group">
	                <label class="col-xs-2 control-label">食材名称</label>
	                <div class="col-xs-4">
						<input type="text" class="form-control" id="fmName" name="fmName" placeholder="请输入" maxlength="50"  data-toggle="tags"/>
	                </div>   
	            </div>
	            <div class="form-group">
		        	<label class="col-xs-2 control-label" >食材图片</label>
		        	<div class="col-xs-10 no-right" >
		        		<img class="img-thumbnail" style="width:100px;height:100px;margin-bottom:5px;" id="showpic" onerror="errorImg(this)"/>
						<input type="file" name="file_upload"id="file_upload" />
						<input type="hidden" name="fmPic" id="fmPic" />			        	
		        		<input type="hidden" name="fmPicOld" id="fmPicOld"  />
		        		<input type="hidden" id="fmId" name="fmId" /> 
		        	</div>
              	</div>
				<div class="form-group">
	        		<label class="col-xs-2 control-label">食材别名</label>
	                <div class="col-xs-4">
	                    <input type="hidden" class="form-control" name="fmAlias" placeholder="请输入食品别名,号分割" required  maxlength="200"/>
	                </div>
	            </div>
				<div class="form-group">		              
	                <label class="col-xs-2 control-label">嘌呤</label>
	                <div class="col-xs-4">
	                    <input type="text" class="form-control" id="fmPurin" name="fmPurin" placeholder="请输入"/>
	                </div>
	            </div>
				<div class="form-group">		              
	                <label class="col-xs-2 control-label">GI</label>
	                <div class="col-xs-4">
	                    <input type="text" class="form-control" id="fmGi" name="fmGi" placeholder="请输入"/>
	                </div>
	            </div>
        		<div class="form-group">		              
	                <label class="col-xs-2 control-label">可食部分</label>
	                <div class="col-xs-4">
	                    	<div class="input-group form-inline" style="float:left;">	                 	
								<input type="text" class="form-control " style="width:180px;" id="fmEsculent" name="fmEsculent"  placeholder="必须数字0-100区间" twoDigit="true" />
								<div class="input-group-addon" style="width:100px;min-width:100px;">%</div>
							</div>
	                </div>
	            </div>
	            <div class="form-group">		              
	                <label class="col-xs-2 control-label">水分</label>
	                <div class="col-xs-4">
	                	<div class="input-group form-inline" style="float:left;">
                	    	<input type="text" class="form-control" style="width:180px;" id="fmWater" name="fmWater" placeholder="必须数字，保留两位小数"  twoDigit="true" max="100000"/>
							<div class="input-group-addon" style="width:100px;min-width:100px"> g</div>	
	                	</div>
	                </div>
	            </div>
	            <div class="form-group">		              
	                <label class="col-xs-2 control-label">灰分</label>
	                <div class="col-xs-4">
              	        <div class="input-group form-inline" style="float:left;">
                	    	<input type="text" class="form-control" style="width:180px;" id="fmAsh" name="fmAsh" placeholder="0.00"  twoDigit="true" max="100000"/>
							<div class="input-group-addon" style="width:100px;min-width:100px"> g</div>	
	                	</div>
	                </div>
	            </div>
			</div>
		</div>
       	<div class="panel panel-default">  
       		<div class="panel-heading">
				<i class="fa fa-edit fa-fw"></i> 食材元素
			</div>
			<div class="panel-body">
				<div class="col-xs-12" id="elmentList" style="font-size: 12px;color: #1d1007; font-weight:normal;">	              	              		           
    					<!-- 动态关联元素表生成表单 -->
	          	</div>
			</div>
		</div>
		<div class="text-left">
			<button type="submit" class="btn btn-primary col-xs-1">提交</button>
		</div>
		</form>					
	</div>
	</li>
    </ul>
</div>

<!-- 添加类别model -->
<form id="addCatalogForm" class="form-horizontal" action="">
	<div id="addCatalogModal" class="modal fade bs-example-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class='fa fa-remove'></i></button>
	            	<h4 class="modal-title">编辑食材类别</h4>
	          	</div>					
				<div class="modal-body">
					<div class="form-group">
						<label class="col-xs-3 control-label">类别名称</label>
						<div class="col-xs-6">
							<input type="hidden" name="treeId" id="treeId"/>
							<input type="hidden" name="parentTreeId" id="parentTreeId"/> 
							<input type="hidden" name="treeOrder" id="treeOrder"/>
							<input type="hidden" name="id" id="id"   />
							<input id="treeName" name="treeName" class="form-control" maxlength="50" type="text" />
							<input id="treeNameOld" name="treeNameOld" class="form-control" type="hidden" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary">保存</button>
				  	<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div> 				
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>
</body>
</html>
