<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/items/inspect.js"></script>
<title>评价项目</title>
</head>
<script type="text/javascript">
//按钮状态
var operateType;
//配置dataTable
var inspectData;
var inspectRow;
var inspectTable;

var inspectOptions = {
		id: "inspectListTable",
		form: "familyQuery",
		columns: [
			{"data": null,"sClass": "text-center",
				"render":  function (data, type, row, meta) {
			      		return "<input type='radio' name='tableRadio' value='"+data.inspectId+"' />";
			      	}
			},
			{"data": "inspectCode","sClass": "text-center" },
			{"data": "inspectName","sClass": "text-center" },
			{"data": "inspectSex","sClass": "text-center",
				"render": function(data, type, row, meta) {
					return CODE.transCode("ITEMSEX",data);
				}
			},
			{"data": "inspectReport","sClass": "text-center", 
				"render": function(data, type, row, meta) {
					return CODE.transCode("TRUEORFALSE",data);
				}
			},
			{"data": "inspectCategory","sClass": "text-center hide",
				"render": function(data, type, row, meta) {
					return CODE.transCode("INSPECTCATEGORY",data);
				}
			}
		],
		rowClick: function(data, row){
			inspectData = data;
			inspectRow = row;
			$("#inspectId").val(data.inspectId);
			$("#id").val(data.inspectId);
		},
		condition : "inspectCondition",
		selecttarget: [5]
};

	$().ready(function() {
		//添加验证
		validator =$("#editInspectForm").validate(intakeTemplateOptions);
		common.requiredHint("editInspectForm",intakeTemplateOptions);
		//初始化下拉选表单
		common.initCodeSelect("INSPECTCATEGORY", "INSPECTCATEGORY","inspectCategoryForm","","请选择项目类别");
		//Model
		common.initCodeSelect("INSPECTCATEGORY", "INSPECTCATEGORY","inspectCategory");
		common.initCodeSelect("STATUS", "STATUS","inspectStatus");
		common.initCodeSelect("ITEMSEX", "ITEMSEX","inspectSex");
		common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE","inspectReport");
		//加载dataTable
		inspectTable = datatable.table(inspectOptions);
		//按钮方法
		$("button[name='intakeTemplatePage']").click(function(){
			//获取Id
			var inspectId = $("#id").val();
			if(this.id == 'addintakeTemplatePage'){
				operateType = "add";
				$("#editInspectForm").attr("action", URL.get("item.ADD_INSPECTITEM"));
				common.clearForm("editInspectForm");
				$("[name='inspectSource']").val("1");
				//为下拉菜单设置默认值
				$("#inspectSex").val("A");
				$("#inspectStatus").val("1");
				$("#inspectReport").val("1");
				$("#inspectOldCode").val("");
				$("#editInspectModal").modal("show");
			}
			if(this.id == 'editintakeTemplatePage' && isChoose(inspectId)){
				operateType = "update";
				$("#editInspectForm").attr("action", URL.get("item.INSPECTITEM_UPDATE"));
				common.clearForm("editInspectForm");
				$("#editInspectForm").jsonToForm(inspectData);
				$("#inspectOldCode").val($("#inspectCode").val());
				$("#inspectStatus").val("1");
				$("#editInspectModal").modal("show");
			}
			if(this.id == 'removeintakeTemplatePage' && isChoose(inspectId)){
				removeClick(inspectId);
			}
			if(this.id == 'configintakeTemplatePage' && isChoose(inspectId)){
				common.openWindow(URL.get("item.CONFIG_INIT_INSPECTITEM") + "?inspectId=" + inspectId);
			}
			if(this.id == "searchButton"){
				inspectTable = datatable.table(inspectOptions);
			}
		});
		
	});
	
	function isChoose(id){
		if(common.isEmpty(id)){
		    layer.msg('请选择操作的记录');
		    return false;
		}else{
			return true;
		}
	}
	
</script>
<body>
<input type="hidden" id="id"/>
<!-- 弹窗添加、编辑 -->
<form id="editInspectForm" name="editInspectForm" action="" class="form-horizontal" method="post">
<div id="editInspectModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg" >
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue ">
					<div class="panel-heading text-center">检查项目编辑</div>
					<div class="row">
	                    <div class="col-xs-9 col-xs-offset-1 row-top">
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">项目分类</label>
	                            <div class="col-xs-4">
	                                <select id="inspectCategory" name="inspectCategory" class="form-control"></select>
	                            </div>
	                            <label class="col-xs-2 control-label">项目代码</label>
	                            <div class="col-xs-4">
									<input type="hidden" id="inspectId" name="inspectId" />
									<input type="hidden" id="inspectStatus" name="inspectStatus"/>
	                            	<input id="inspectOldCode" name="inspectOldCode" type="hidden"/>
	                            	<input id="inspectCode" name="inspectCode" class="form-control" type="text"/>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">适宜性别</label>
	                            <div class="col-xs-4">
	                                <select id="inspectSex" name="inspectSex" class="form-control"></select>
	                            </div>
	                            <label class="col-xs-2 control-label">操作名称</label>
	                            <div class="col-xs-4">
	                                <input id="inspectConfigName" name="inspectConfigName" class="form-control"  type="text"/>
	                            </div>
	                        </div> 
	                        <div class="form-group">
								<label class="col-xs-2 control-label">输出报告</label>
								<div class="col-xs-4">
									<select id="inspectReport" name="inspectReport" class="form-control"></select>                                           
								</div>
	                            <label class="col-xs-2 control-label">项目名称</label>
	                            <div class="col-xs-4">
	                                <input id="inspectName" name="inspectName" class="form-control" maxlength="50" type="text"/>
	                            </div>
	                        </div>    
						</div>
					</div>
				</div>
				<div class="panel-body padding-zero" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="submit" class="btn btn-primary btn-block">确认</button>
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

<div class="row">
	<div class="panel-body form-inline" id="inspectCondition">
    	<form id="familyQuery" name="familyQuery" action="${common.basepath}/${applicationScope.URL.item.INSPECTITEM_QUERY}" method="post">
            <select id="inspectCategoryForm" name="inspectCategory" class="form-control"></select> 				                    	
            <input name="inspectName" class="form-control" type="text" placeholder="请输入项目名称">
            <input name="inspectCategory" class="form-control" type="hidden" value="1" >
			<button type="button" id="searchButton" name="intakeTemplatePage" class="btn btn-default">
				<i class="fa fa-search fa-fw"></i>查询
			</button>
   			<div class="vertical-line"></div>
   			<button id="addintakeTemplatePage" name="intakeTemplatePage" type="button" class="btn btn-default">
	     		<i class="fa fa-plus fa-fw"></i> 增加
	  		</button>
      		<button id="editintakeTemplatePage" name="intakeTemplatePage" type="button" class="btn btn-default">
	     		<i class="fa fa-edit fa-fw"></i> 编辑
	  		</button>
      		<button id="removeintakeTemplatePage" name="intakeTemplatePage" type="button" class="btn btn-default">
	     		<i class="fa fa-remove fa-fw"></i> 删除
	  		</button>
    	</form>
	</div>	
</div>			
<div class="table-responsive">
	<table  id="inspectListTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">代码</th>
				<th class="text-center">名称</th>
				<th class="text-center">适合性别</th>
				<th class="text-center">输出报告</th>
				<th class="text-center" style="display: none">项目类别</th>
		   	</tr>
		</thead>
	</table>
</div>
</body>
</html>