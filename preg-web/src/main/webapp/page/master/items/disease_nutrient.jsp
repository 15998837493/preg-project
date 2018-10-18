<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>诊断元素配置</title>
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>

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

body{
	background-color: #eaf1fb;
	padding: 0px;
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
</style>

<script type="text/javascript">

//取消回车事件
$(document).keydown(function(event){
    switch(event.keyCode){
       case 13:return false; 
       }
});

//树结点集合
var zNodes = ${treeNodes};
//dataTable配置
var quotaData;
var quotaRow;
var quotaTable;

var quotaOptions = {
	id:"quotaListTable",
	form:"queryForm",
	columns: [	
	      		{"data":null,"sClass":"text-center",
					"render":function (data, type, row, meta) {
			    		return "<input type='radio' name='tableRadio' value='"+data.nutrientId+"' />";
					}
				},
				{"data": "nutrientName","sClass": "text-left" },
	],
	rowClick: function(data, row){
		quotaData = data;
		quotaRow = row;
		$("#id").val(data.id);
	},
	condition : "quotaItemCondition",
	async: false,
	loading: false,// 是否启用遮罩层
};

//辅助检查项目列表
var elements =${elements};
var itemListDataOptions = {
	id : "inspectsTable",
	data : elements,
	columns: [
				{"data": "nutrientName","sClass": "text-left" },
				{"data": null,"sClass": "text-center",
					render:function(data,type,row,meta){
						return "<a onclick='addNutrient(\""+data.nutrientId+"\")' style='cursor: pointer;'><i class='fa fa-plus'></i>添加</a></td></tr>";
					}	
				}
		],
	condition : "nutrientCondition",
	loading: false,// 是否启用遮罩层
	isShowRecord : false
};
</script>
<script type="text/javascript" src="${common.basepath}/page/master/items/disease_nutrient.js"></script>
</head>
<body>
<input type="hidden" id="id" />
<input type="hidden" id="diseaseCode" name="diseaseCode" />
<div style="display: none" id="itemCodeListValue"></div>
<div id="inspectItemModal" class="modal fade bs-example-modal">
	<div class="modal-dialog"><!-- style="width: 1200px;" -->
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						 <i class="fa fa-list fa-fw"></i>元素列表
						 <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div  class="panel-body form-inline" id="nutrientCondition">
                        <input id="nutrientName" name="nutrientName" class="form-control" type="text" placeholder="请输入指标名称" />
		            </div>
					<div class="panel panel-default" style="margin-bottom: 0px;">
						<div class="table-responsive">
							<table id="inspectsTable" class="table table-bordered table-hover">
								<thead>
									<tr class="active">
										<th class="text-center">名称</th>
										<th class="text-center" >操作</th> 
								   	</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default col-xs-3" >
			<div class="panel-heading">
				<i class="fa fa-filter fa-fw"></i> 选择诊断项目
			</div>
			<div class="panel-body" id="left_div">
				<div id="zTree_div" class="inp_width" style="max-height: 480px; overflow-y: scroll;">
					<ul id="zTree" class="ztree"></ul>
				</div>
			</div>
		</div>
		<div class="panel panel-default col-xs-9" >
			<div class="panel-heading">
				<i class="fa fa-list fa-fw"></i> 诊断元素
			</div>
			<div class="panel-body" id="cust_div" style="height: auto !important;">			
	        <div id="quotaItemCondition" class="form-inline">
	            <form id="queryForm" action="${common.basepath }/${applicationScope.URL.item.QUERY_NUTRIENT}">
	                <input type="hidden" name="diseaseCode" id="diseaseIdQuery"/>	
                        <input type='text' id='nutrientName' name="nutrientName" class='form-control' placeholder='请输入检索内容！'/>
			      		<button type="button" id="searchButton" name="diseaseItemPage" class="btn btn-default" disabled>
			      			<i class="fa fa-search fa-fw"></i>查询
			      		</button>
			      		<div class="vertical-line"></div>
				  		<button id="addButton" name="diseaseItemPage" type="button" class="btn btn-default" disabled>
				     		<i class="fa fa-plus fa-fw"></i> 增加
				  		</button>
				  		<button id="removeButton" name="diseaseItemPage" type="button" class="btn btn-default" disabled>
				     		<i class="fa fa-remove fa-fw"></i> 删除
				  		</button>
	           	</form>
	           	</div>
				<div class="table-responsive"  style="padding-top: 10px;">
					<table id="quotaListTable" class="table table-bordered table-hover">
						<thead>
							<tr class="active">
								<th class="text-center" style="width: 10%;">选择</th>
								<th class="text-center">元素名称</th>
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
</body>
</html>