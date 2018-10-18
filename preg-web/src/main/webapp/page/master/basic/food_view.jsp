<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>菜谱库</title>
<%@ include file="/common/common.jsp"%>
<!-- 上传图片 -->
<link rel="stylesheet" type=text/css href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<!-- 自动补全 -->
<link rel="stylesheet" href="${common.basepath}/common/plugins/autocomplete/jquery.autocomplete.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/autocomplete/jquery.autocomplete.js"></script>
<!-- 标签 -->
<script type="text/javascript"  src="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.js"></script>
<link rel="stylesheet" type="text/css"  href="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.css">
<!-- Ztree -->
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${common.basepath}/common/mnt/css/ztree.css" />
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

.inp_width{
	width: 70%;
}
</style>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes_food};
//树结点集合
var zNodes2 = ${treeNodes};
//类别按钮状态
var opernerCatalog;
//类别按钮状态
var opernerCatalog2;
//当前选中结点
var selectNode; 
//树对象
var treeObj;
//当前选中结点
var selectNode2; 
//树对象
var treeObj2;
// fmID
var fmIdArray = [];
// fmlType
var fmlTypeArray = [];
// fmlAmount
var fmlAmountArray = [];
// fmlMaterial
var fmlMaterialArray = [];
</script>
<!-- 自定义css -->
<style>
.myTable {
	border-collapse:separate; 
	border-spacing:10px;
}
</style>
<!-- JavaScript页面 -->	
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/master/basic/food_view.js"></script>	
<!-- 计算元素js -->
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/master/basic/calculate_element.js"></script>	
</head>
<body>
<div class="panel-body" id="showDiv"> 
  	<ul id="timeline"> 
   	<li class="timeline-inverted"> 
   	<div id="timeline-panel">
    <!-- 一览页面 --> 
       	<div class="panel panel-default col-xs-3" id="left_div">  
       		<div class="panel-heading row">
				<i class="fa fa-filter fa-fw"></i> 菜谱类别管理
			</div>
			<div class="panel-body" id="zTree_div">
	        	<div class="panel-body" > 
	        		<ul id="zTree" class="ztree"></ul> 
	        	</div> 
	        </div>
       	</div> 
       	<div class="panel panel-default col-xs-9" id="list_div">
       		<div class="panel-heading row">
				<i class="fa fa-filter fa-fw"></i> 指定类别下的菜谱
			</div> 
			<div class="panel-body" id="foodCondition">
	    		<form id="cuisineQuery" action="${common.basepath }/${applicationScope.URL.Food.FOOD_CUISINE_AJAX_QUERY}" class="form-inline" method="post"> 
	          		<input type="hidden" name="foodId" id="foodId" /> 
	          		<input type="hidden" name="foodTreeTypeLike" id="categoryIdQuery"/>	
            		<label for="companyName">菜谱名称</label> 
            		<input type="text" class="form-control" id="foodName" name="foodName" placeholder="名称" /> 
            		<button id="searchButton" name="clickButton"  type="button" class="btn btn-primary">
						<i class="fa fa-search fa-fw"></i> 搜索
					</button>
					<div class="vertical-line"></div>
            		<button id="addButton" name="clickButton" type="button" class="btn btn-primary">
            			<i class="fa fa-plus fa-fw"></i> 增加
            		</button> 
            		<button id="editButton" name="clickButton" type="button" class="btn btn-primary">
						<i class="fa fa-edit fa-fw"></i> 编辑
					</button>
	           		<button id="removeButton" name="clickButton" type="button" class="btn btn-primary">
	           			<i class="fa fa-remove fa-fw"></i> 删除 
	           		</button> 
				</form> 
			</div> 
	        <div class="table-responsive"> 
	        	<table id="foodListTable" class="table table-bordered table-hover"> 
	           		<thead> 
	            		<tr class="active"> 
	             			<th class="text-center">选择</th> 
	             			<th class="text-center">菜谱名称</th> 
	             			<th class="text-center">菜谱类型</th> 
	             			<th class="text-center">口味</th>
	             			<th class="text-center">烹饪方式</th>  
	            		</tr> 
					</thead> 
	          	</table> 
			</div> 
        </div> 
	</div>
	</li>
	</ul>
</div>

<!-- 增加菜谱 -->
<div class="panel-body" id="addDiv" style="display:none"> 
  	<ul id="timeline"> 
   	<li class="timeline-inverted"> 
   	<div id="timeline-panel">
		<div class="form-group text-left">
			<button class="btn btn-primary"id="backButton" name="clickButton">返回列表</button> 		
		</div>	
       	<div class="panel panel-default">  
       		<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 菜谱信息
			</div>
			<div class="panel-body">
				<form action="${common.basepath }/${applicationScope.URL.Food.FOOD_CUISINE_ADD}" id="addCuisineForm" name="addCuisineForm" class="form-horizontal" method="post">
					<input type="hidden" id="addFoodId" name="foodId"/>
					<input type="hidden" id="fmId" name="fmId" /> 
					<input type="hidden" id="fmIdTmp" name="fmIdTmp" /> 
					<input type="hidden" id="fmlAmount" name="fmlAmount" /> 
					<input type="hidden" id="foodCount" name="foodCount" /> 
					<input type="hidden" id="fmlType" name="fmlType" />
					<input type="hidden" id="fmlMaterial" name="fmlMaterial" />
					<!-- 提交 -->
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">菜谱类型</label>
		                <div class="col-xs-4">
		                	<input type="hidden" id="foodType" name="foodType"/>
		                	<div style="width:165px;">
		                		<div id="productCategoryDiv"></div>
		                	</div>
		                </div>   
		            </div>
		        	<div class="form-group">
     				    <label class="col-xs-2 control-label">菜谱名称</label>
		                <div class="col-xs-5">
							<input type="text" id="foodName" name="foodName" placeholder="请输入" maxlength="50" class="form-control"/>
		                </div>     	
		            </div>		            
					<div class="form-group">
		                <label class="col-xs-2 control-label">菜谱图片</label>
		                <div class="col-xs-8">
							<img class="img-thumbnail" id="showpic" style="width:100px;height:100px;margin-bottom:5px;" onerror="errorImg(this)"/> 
							<input type="file" name="file_upload" id="file_upload" /> 
							<input type="hidden" name="foodPic" id="foodPic" />
							<input type="hidden" name="foodPicOld" id="foodPicOld"  />
		                </div>   
		            </div>
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">菜谱别名</label>
		                <div class="col-xs-5">
							<input type="hidden" name="foodAlias" class="form-control"/>
		                </div>   
		            </div>
		        	<div class="form-group">
     				    <label class="col-xs-2 control-label">口味</label>
		                <div class="col-xs-5">
							<input id="foodTaste" name="foodTaste" type="text" placeholder="请输入口味" class="form-control"/>
		                </div>     	
		            </div>		            
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">烹饪方式</label>
		                <div class="col-xs-5">
							<select class="form-control" id="foodCuisine" name="foodCuisine"></select>
		                </div>   
		            </div>
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">有益成分</label>
		                <div class="col-xs-8">
		                	<label class="checkbox-inline">
								<input name="foodBenefitList" type="checkbox" value="30"/>ω-3		                	
		                	</label>
		                	<label class="checkbox-inline">
								<input name="foodBenefitList" type="checkbox" value="31"/>碘		                	
		                	</label>
		                	<label class="checkbox-inline">
								<input name="foodBenefitList" type="checkbox" value="32"/>抗肿瘤		                	
		                	</label>		                			                	
		                </div>   
		            </div>
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">有害成分</label>
		                <div class="col-xs-8">
		                	<label class="checkbox-inline">
		                		<input name="foodHarmList" type="checkbox" value="50"/>草酸
		                	</label>
		                	<label class="checkbox-inline">
		                		<input name="foodHarmList" type="checkbox" value="51"/>麸质
		                	</label>
		                	<label class="checkbox-inline">
								<input name="foodHarmList" type="checkbox" value="52"/>嘌呤		                	
		                	</label>
		                	<label class="checkbox-inline">
								<input name="foodHarmList" type="checkbox" value="53"/>刺激性                	
		                	</label>
		                	<label class="checkbox-inline">
								<input name="foodHarmList" type="checkbox" value="54"/>十字花科		                	
		                	</label>		                			                								
		                </div>   
		            </div>
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">功效</label>
		                <div class="col-xs-8">
							<textarea class="form-control" id="foodEfficacy" name="foodEfficacy" placeholder="请输入" maxlength="500"></textarea>
		                </div>   
		            </div>
		        	<div class="form-group">
		                <label class="col-xs-2 control-label">菜肴做法</label>
		                <div class="col-xs-8">
							<textarea id="foodMake" class="form-control" name="foodMake" placeholder="请输入" maxlength="500"></textarea>
		                </div>   
		            </div>			                       	                                  
				</form>						
			</div>
		</div>
       	<div class="panel panel-default padding-zero">  
       		<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 原料及用量
			</div>
			<div class="panel-body">
				<div class="panel panel-default col-xs-2 padding-zero">  
		        	<div class="panel-body" id="zTree_add_page_div"> 
		         		<ul id="zTree_add_page" class="ztree" style="overflow: hidden;"></ul> 
		         	</div>
	       		</div> 
		       	<div class="panel panel-default col-xs-5 padding-zero"> 
		        	<div class="panel-body" id="fmCondition"> 
			         	<form id="fmQuery" action="${common.basepath }/${applicationScope.URL.foodMaterial.FOOD_MATERIAL_AJAX_QUERY}" class="form-inline" method="post"> 
			          		<input type="hidden" name="fmTypeLike" id="categoryIdQuery2"/>
							<input id="fmName" name="fmName" class="form-control" type="text" placeholder="名称" />
							<button type="button" id="searchss" name="fmPage" class="btn btn-primary">
								<i class="fa fa-search fa-fw"></i>搜索
							</button> 
						</form> 
						<div class="table-responsive">
							<table id="fmListTable" class="table table-bordered table-hover ">
								<thead>
								<tr class="active">
									<th class="text-center">食物名称</th>
									<th class="text-center">操作</th>
								</tr>
								</thead>
							</table>
						</div>
		        	</div> 
		       	</div>
		      	<div class="col-xs-5 padding-zero">  
					<div class="table-responsive">
						<table class="table table-bordered table-hover">
							<thead>
								<tr class="active">
									<th class="text-center">食物名称</th>
									<th class="text-center" style="width:70px;">数量(g)</th>
									<th class="text-center" style="width:90px;">主料/辅料</th>
									<th class="text-center" style="width:80px;">操作</th>
								</tr>
							</thead>
							<tbody id="addFoodExt"></tbody>
						</table>
					</div> 
				</div> 			
			</div>
		</div>
		
		
       	<div class="panel panel-default">  
       		<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 菜谱元素信息
			</div>
			
			<div class="panel-body" >
			<div class="col-xs-1"></div>
				<div class="col-xs-12" id="elmentList" style="font-size: 12px;color: #1d1007;">
				</div>	
			<div class="col-xs-1"></div>	
			</div>
		</div>
		
		
		
		<div class="form-group text-left">
			<button class="btn btn-primary col-xs-1" onclick="subForm();" >保存</button> 
		</div>				
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
	            	<h4 class="modal-title">菜谱类型</h4>
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

<!-- 添加类别model -->
<form id="addCatalogForm2" class="form-horizontal" action="">
	<div id="addCatalogModal2" class="modal fade bs-example-modal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
	            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class='fa fa-remove'></i></button>
	            	<h4 class="modal-title">菜谱类型</h4>
	          	</div>				
				<div class="modal-body">
					<div class="form-group">
						<label class="col-xs-3 control-label">类别名称</label>
						<div class="col-xs-6">
							<input type="hidden" name="treeId" id="treeId2"/>
							<input type="hidden" name="parentTreeId" id="parentTreeId2"/> 
							<input type="hidden" name="treeOrder" id="treeOrder2"/>
							<input type="hidden" name="id" id="id2"   />
							<input id="treeName2" name="treeName" class="form-control" maxlength="50" type="text" />
							<input id="treeNameOld2" name="treeNameOld" class="form-control" type="hidden" />
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
</HTML>
