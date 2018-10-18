<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<style type="text/css">
#myTabContent .panel{
	margin-bottom: 0px;
}
.mytable {   
   table-layout: fixed;   
   width: 98% border:0px;   
   margin: 0px;   
}   
.mytable tr td {
	text-overflow: ellipsis;   
    overflow: hidden;   
    white-space: nowrap;   
    border: 1px solid;   
}
</style>
<title>系统参数设置</title>
</head>
<script type="text/javascript">
/**
 * 验证参数设置
 */
var systemParamOptions = {
	rules: {
		paramName: {
			required:true,
			maxlength: 50
		},
		paramValue: {
			maxlength: 1500
		},
		paramRemark: {
			maxlength: 1000
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function () {
			$('#updateForm').ajaxPost(dataType.json,function(data){
				layer.alert("修改成功！",{icon: 1});
			});
        });
	}
};

//datatable参数配置
var paramTable;
var paramRow;
var paramData;

var paramTableOptions = {
		id:"paramListTable",
		form:"paramForm",
		columns:[
			{"data":"paramId","sClass":"text-center" },
			{"data":"paramName","sClass":"text-center" },
			{"data":null, "sClass":"text-left",
				"render":function(data,type,row,meta){
					return nameDetailOnMouseover(data.paramId+"_param",data.paramValue);
				}
			},
			{"data":"paramType","sClass":"text-center" },
			{"data":"paramRemark","sClass":"text-center",
				"render":function(data,type,row,meta){
					return data?data:"";
				}
			},
			{"data":null,"sClass":"text-center",
				"render":function(data,type,row,meta){
					return "<button class='btn btn-primary btn-xs' onclick='resetParam(\""+data.paramId+"\")'><i class='fa fa-cog fa-fw'></i> 设置</button>";
				}
			}
		],
		rowClick:function(data,row){
			paramData = data;
			paramRow = row;
		},
		condition:"paramCondition"
};

$().ready(function (){
	$("#updateModal").modal("hide");
	paramTable = datatable.table(paramTableOptions);
	$("#searButton").click(function(){
		datatable.table(paramTableOptions);
	});
});
// 设置系统参数
function resetParam(paramId){
	var url = URL.get("System.SYSTEM_PARAM_INIT_UPDATE");
	var params = "id=" + paramId;
	ajax.post(url, params, dataType.json, function(data) {
		var d = data.value;
		$("#paramId_update").val(d.paramId);
		$("#paramName_update").val(d.paramName);
		$("#paramValue_update").val(d.paramValue);
		$("#paramType_update").val(d.paramType);
		$("#paramRemark_update").val(d.paramRemark);
	});
	$("#updateForm").validate(systemParamOptions);
	//加入必填项提示
	common.requiredHint("updateForm", systemParamOptions);
	$("#updateModal").modal("show");
}

/**
 * 浮窗显示
 * @param code
 * @param content
 * @returns {String}
 */
function nameDetailOnMouseover(code, content){
	return "<a id='mark_"+code+"'" + 
	       "   data-toggle='tooltip'" + 
	       "   data-placement='top'" +
	       "    title="+content+">" + content + "</a>";
}
</script>
<body>
<div class="row">
	<div class="panel-body">
  			<div id="paramCondition">
            <form id="paramForm" name="paramForm" action="${common.basepath}/${applicationScope.URL.System.SYSTEM_PARAM_QUERY}" method="post" class="form-horizontal">
            	<div class="form-inline">
                        <input id="paramName" name="paramName" class="form-control" type="text" placeholder="请输入查询内容">
		      		<button id="searButton" type="button" class="btn btn-default"><i class="fa fa-search fa-fw"></i> 查询</button>
                   </div>
            </form>
  		</div>
	</div>
</div>
<div class="table-responsive">
	<table id="paramListTable" class="table table-bordered table-hover mytable">
		<thead>
			<tr class="active">
				<th class="text-center" style="width: 22.5%">参数编码</th>
				<th class="text-center" style="width: 15%">参数名称</th>
				<th class="text-center" style="width: 35%">参数值</th>
				<th class="text-center" style="width: 10%">参数类型</th>
				<th class="text-center" style="width: 7.5%">参数说明</th>
				<th class="text-center" style="width: 10%">操作</th>
		   	</tr>
		</thead>
	</table>
</div>
<!-- 设置参数模态框（Modal） -->
<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.System.SYSTEM_PARAM_UPDATE}" method="post">
<div id="updateModal" class="modal fade bs-example-modal">	
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<input type="hidden" name="id" id="id">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-center">设置参数</div>
					<div class="row row-top">
						<div class="col-xs-11 col-xs-offset-1">
							<div class="form-group">
						    	<label class="col-xs-2 control-label">参数编码</label>
					        	<div class="col-xs-8">
									<input id="paramId_update" name="paramId" class="form-control" type="text" readonly />
				               	</div>
							</div>
				           	<div class="form-group">
				              	<label class="col-xs-2 control-label">参数名称</label>
				              	<div class="col-xs-8">
									<input id="paramName_update" name="paramName" class="form-control" type="text" />
				              	</div>
				           	</div>
				           	<div class="form-group">
				              	<label class="col-xs-2 control-label">参数值</label>
				              	<div class="col-xs-8">
				              		<textarea rows="5" id="paramValue_update" name="paramValue" class="form-control" required maxlength="1500"></textarea>
				            	</div>
				       	   	</div>
				           	<div class="form-group">
				            	<label class="col-xs-2 control-label">参数类型</label>
				            	<div class="col-xs-8">
									<input id="paramType_update" name="paramType" class="form-control" type="text" readonly/>
				            	</div>
				           	</div>
				           	<div class="form-group">
				            	<label class="col-xs-2 control-label">参数说明</label>
				            	<div class="col-xs-8">
				               		<textarea rows="3" id="paramRemark_update" name="paramRemark" class="form-control"></textarea>
				            	</div>
				           	</div>
						</div>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="submit"
							class="btn btn-primary btn-block">设置</button>
					</div>
					<div class="col-xs-2 no-right">
						<button type="button"
							class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</body>
</html>