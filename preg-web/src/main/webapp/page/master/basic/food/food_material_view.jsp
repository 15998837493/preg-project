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
</script>
<script type="text/javascript"src="${common.basepath}/page/master/basic/food/food_material_view.js"></script>
</head>
<body>
<input type="hidden" id="id"  />
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 食物类别管理
			</div>
			<div class="panel-body" id="left_div">
				<div id="zTree_div" class="inp_width">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default col-xs-9">
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 指定类别下的食材
			</div>
			<div class="panel-body" id="cust_div" style="height: auto !important;">
				<div class="form-inline" id="fmCondition">
					<form action="${common.basepath }/${applicationScope.URL.Master.FOOD_MATERIAL_AJAX_QUERY}" id="fmQuery" method="post" >
						<input type="hidden" name="fmTypeLike" id="categoryIdQuery"/>
						<input id="fmName" name="fmName" class="form-control" type="text" placeholder="请输入食材名称" />
						<button type="button" id="searchss" name="fmPage" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button id="addFmPage" name="fmPage" type="button" class="btn btn-default">
							<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button id="editFmPage" name="fmPage" type="button" class="btn btn-default">
							<i class="fa fa-edit fa-fw"></i> 编辑
						</button>
						<button id="removeFmPage" name="fmPage" type="button" class="btn btn-default">
							<i class="fa fa-remove fa-fw"></i> 删除
						</button>
					</form>
				</div>
				<div class="table-responsive" style="padding-top: 10px;">
					<table class="table table-bordered table-hover table-padding-2" id="fmListTable">
						<thead>
							<tr class="active">
								<th class="text-center">操作</th>
								<th class="text-center">名称</th>
								<th class="text-center">别名</th>
								<th class="text-center">类别</th>
								<th class="text-center">能量(kcal)</th>
								<th class="text-center">蛋白质(g)</th>
								<th class="text-center">脂肪(g)</th>
								<th class="text-center">炭水化合物(g)</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- 弹窗添加、编辑 -->
<form action="" id="editForm" name="editForm" method="post" class="form-horizontal">
<div id="editModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg" style="width: 1200px;">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue margin-zero" id="productDiv">
						<div class="panel-heading text-center"> <i class="fa fa-edit fa-fw"></i>编辑食材<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="row">
							<div class="col-xs-3 row-top" >
						        <div class="form-group">
						        	<label class="col-xs-5 control-label" ></label>
						        	<div class="col-xs-7 no-right" >
						        		<img class="img-thumbnail" style="width:100px;height:100px;margin-bottom:5px;" id="showpic" onerror="errorImg(this)"/>
										<input type="file" name="file_upload"id="file_upload" />
										<input type="hidden" name="fmPic" id="fmPic" />			        	
						        		<input type="hidden" name="fmPicOld" id="fmPicOld"  />
						        		<input type="hidden" id="fmId" name="fmId" /> 
						        	</div>
				              	</div>	
							</div>
							<div class="col-xs-8 row-top">
								<div class="form-group">
					        		<label class="col-xs-3 control-label">食物类别</label>
					                <div class="col-xs-8">
					                	<input type="hidden" id="fmType" name="fmType"/>
					                	<div id="fmTypeDiv"></div>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-3 control-label">食物名称</label>
					                <div class="col-xs-8">
					                    <input type="text" class="form-control" id="fmName" name="fmName" placeholder="请输入食物名称"  data-toggle="tags"/>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-3 control-label">食物别名</label>
					                <div class="col-xs-8">
					                    <input type="hidden" class="form-control" name="fmAlias" placeholder="请输入食品别名,号分割" />
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-3 control-label">普通食品类别</label>
					                <div class="col-xs-8">
					                    <select id="fmGeneralType" name="fmGeneralType"class="form-control">
												<option value="">==请选择普通食品类别==</option>
												<c:forEach items="${intakeTypes}" var="list">
													<option value="${list.code}">${list.name }</option>
												</c:forEach>
										</select>
					                </div>
					            </div>
								<div class="form-group">
					        		<label class="col-xs-3 control-label">食物简介</label>
					                <div class="col-xs-8">
					                    <textarea class="form-control" name="fmDesc"
													placeholder="请输入简介"></textarea>
					                </div>
					            </div>
					            <div class="form-group">
					        		<label class="col-xs-3 control-label">食物功效</label>
					                <div class="col-xs-8">
					                    <textarea class="form-control" name="fmEfficacy"
													placeholder="请输入功效"></textarea>
					                </div>
					            </div>
							</div>
						</div>
					</div>
					<div class="panel panel-lightblue" id="elementDiv">
						<div class="panel-heading text-center">元素信息</div>
						<div class="row">
							<div class="col-xs-10 col-xs-offset-1 row-top" id="elmentList" >
								<table class="table table-condensed">
									<tbody>
										<tr>
											<td><label class="control-label text-right" for="食物级别">食物级别</label></td>
											<td><select id="fmLevel" name="fmLevel"
												class="form-control">
													<option value="">==食物级别==</option>
													<option value="green">绿色</option>
													<option value="yellow">黄色</option>
													<option value="red">红色</option>
											</select></td>
											<td><label class="control-label text-right"
												for="可食用部分（%）">可食用部分（%）</label></td>
											<td><input type="text" class="form-control"
												id="fmEsculent" name="fmEsculent" value="100"
												placeholder="请输入可食用部分（%）" /></td>
											<td><label class="control-label" for="能量_kcal">能量_kcal</label></td>
											<td><input type="text" class="form-control"
												id="fmEnergy" name="fmEnergy" value="0"
												placeholder="请输入能量_kcal" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="蛋白质(g)">蛋白质(g)</label></td>
											<td><input type="text" class="form-control"
												id="fmProtid" name="fmProtid" value="0"
												placeholder="请输入蛋白质(g)" /></td>
											<td><label class="control-label text-right" for="脂肪(g)">脂肪(g)</label></td>
											<td><input type="text" class="form-control" id="fmFat"
												name="fmFat" value="0" placeholder="请输入脂肪(g)" /></td>
											<td><label class="control-label text-right"
												for="饱和脂肪酸(g)">饱和脂肪酸(g)</label></td>
											<td><input type="text" class="form-control" id="fmSfa"
												name="fmSfa" value="0" placeholder="请输入饱和脂肪酸(g)" /></td>

										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="单不饱和脂肪酸(g)">单不饱和脂肪酸(g)</label></td>
											<td><input type="text" class="form-control" id="fmMfa"
												name="fmMfa" value="0" placeholder="请输入单不饱和脂肪酸(g)" /></td>
											<td><label class="control-label" for="多不饱和脂肪酸(g)">多不饱和脂肪酸(g)</label></td>
											<td><input type="text" class="form-control" id="fmPfa"
												name="fmPfa" value="0" placeholder="请输入多不饱和脂肪酸(g)" /></td>
											<td><label class="control-label" for="碳水化合物(g)">碳水化合物(g)</label></td>
											<td><input type="text" class="form-control" id="fmCbr"
												name="fmCbr" value="0" placeholder="请输入碳水化合物(g)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="膳食纤维(g)">膳食纤维(g)</label></td>
											<td><input type="text" class="form-control" id="fmDf"
												name="fmDf" value="0" placeholder="请输入膳食纤维(g)" /></td>
											<td><label class="control-label" for="灰分(g)">灰分(g)</label></td>
											<td><input type="text" class="form-control" id="fmAshc"
												name="fmAshc" value="0" placeholder="请输入灰分(g)" /></td>
											<td><label class="control-label text-right"
												for="维生素A(ug)">维生素A(ug)</label></td>
											<td><input type="text" class="form-control" id="fmVa"
												name="fmVa" value="0" placeholder="请输入维生素A(ug)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="胡萝卜素(μg)">胡萝卜素(μg)</label></td>
											<td><input type="text" class="form-control"
												id="fmCarotin" name="fmCarotin" value="0"
												placeholder="请输入胡萝卜素(μg)" /></td>
											<td><label class="control-label text-right"
												for="胆固醇(mg)">胆固醇(mg)</label></td>
											<td><input type="text" class="form-control" id="fmCho"
												name="fmCho" value="0" placeholder="请输入胆固醇(mg)" /></td>
											<td><label class="control-label" for="维生素B1(mg)">维生素B1(mg)</label></td>
											<td><input type="text" class="form-control" id="fmVb1"
												name="fmVb1" value="0" placeholder="请输入维生素B1(mg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right"
												for="维生素B2(mg)">维生素B2(mg)</label></td>
											<td><input type="text" class="form-control" id="fmVb2"
												name="fmVb2" value="0" placeholder="请输入维生素B2(mg)" /></td>
											<td><label class="control-label text-right" for="烟酸(mg)">烟酸(mg)</label></td>
											<td><input type="text" class="form-control" id="fmAf"
												name="fmAf" value="0" placeholder="请输入烟酸(mg)" /></td>
											<td><label class="control-label" for="维生素C">维生素C</label></td>
											<td><input type="text" class="form-control" id="fmVc"
												name="fmVc" value="0" placeholder="请输入维生素C" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="维生素E">维生素E</label></td>
											<td><input type="text" class="form-control" id="fmVe"
												name="fmVe" value="0" placeholder="请输入维生素E" /></td>
											<td><label class="control-label text-right" for="钙(mg)">钙(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEca"
												name="fmEca" value="0" placeholder="请输入钙(mg)" /></td>
											<td><label class="control-label" for="磷(mg)">磷(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEp"
												name="fmEp" value="0" placeholder="请输入磷(mg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="钾(mg)">钾(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEk"
												name="fmEk" value="0" placeholder="请输入钾(mg)" /></td>
											<td><label class="control-label text-right" for="钠(mg)">钠(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEna"
												name="fmEna" value="0" placeholder="请输入钠(mg)" /></td>
											<td><label class="control-label" for="镁(mg)">镁(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEmg"
												name="fmEmg" value="0" placeholder="请输入镁(mg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="铁(mg)">铁(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEfe"
												name="fmEfe" value="0" placeholder="请输入铁(mg)" /></td>
											<td><label class="control-label text-right" for="锌(mg)">锌(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEzn"
												name="fmEzn" value="0" placeholder="请输入锌(mg)" /></td>
											<td><label class="control-label" for="硒(μg)">硒(μg)</label></td>
											<td><input type="text" class="form-control" id="fmEse"
												name="fmEse" value="0" placeholder="请输入硒(μg)" /></td>
										</tr>
										<tr>
											<td><label class="control-label text-right" for="铜(mg)">铜(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEcu"
												name="fmEcu" value="0" placeholder="请输入铜(mg)" /></td>
											<td><label class="control-label text-right" for="钠(mg)">锰(mg)</label></td>
											<td><input type="text" class="form-control" id="fmEmn"
												name="fmEmn" value="0" placeholder="请输入锰(mg)" /></td>
											<!-- <td><label class="control-label text-right" for="胆固醇">胆固醇(mg)</label></td>
											<td><input type="text" class="form-control" id="fmCho"
												name="fmCho" value="0" placeholder="请输入胆固醇(mg)" /></td> -->
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="panel-body text-right" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right" >
							<button type="submit" class="btn btn-primary btn-block">保存</button>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

<!-- 添加类别model -->
<form id="addCatalogForm" class="form-horizontal" action="">
	<input type="hidden" name="catalogParentId" id="catalogParentId"/> 
	<input type="hidden" name="catalogOrder" id="catalogOrder"/>
	<div id="addCatalogModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-edit fa-fw"></i> 编辑商品类别
							<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
						</div>
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

</body>
</html>
