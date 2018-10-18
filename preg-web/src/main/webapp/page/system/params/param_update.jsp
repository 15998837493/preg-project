<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>设置参数</title>
<body>
<script type="text/javascript">
/**
 * 验证参数设置
 */
var systemParamOptions = {
	rules: {
		paramName: {
			required:true,
			maxlength: 25
		},
		paramValue: {
			maxlength: 50
		},
		paramRemark: {
			maxlength: 250
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
$().ready(function() {
	//表单校验
	$("#updateForm").validate(systemParamOptions);
	common.requiredHint("updateForm",systemParamOptions);
	//返回
	$("#backButton").click(function(){
		common.pageForward(URL.get("SystemPage.SYSTEM_PARAM_VIEW"));
	});
});
</script>
<div class="container-fluid">
	<div class="row row-top">
		<div class="col-xs-12">
 			<div class="panel panel-primary">
				<div class="panel-heading">
					<i class="fa fa-edit fa-fw"></i>设置参数
	    		</div>
	  			<div class="panel-body">
			    <form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.System.SYSTEM_PARAM_UPDATE}" method="post">
					<div class="form-group">
				    	<label class="col-xs-3 control-label">参数编码</label>
			        	<div class="col-xs-6">
							<input id="paramId" name="paramId" class="form-control" type="text" value="${systemParam.paramId}" readonly />
		               	</div>
					</div>
		           	<div class="form-group">
		              	<label class="col-xs-3 control-label">参数名称</label>
		              	<div class="col-xs-6">
							<input id="paramName" name="paramName" class="form-control" type="text" value="${systemParam.paramName}" />
		              	</div>
		           	</div>
		           	<div class="form-group">
		              	<label class="col-xs-3 control-label">参数值</label>
		              	<div class="col-xs-6">
							<input id="paramValue" name="paramValue" class="form-control" type="text" value="${systemParam.paramValue}" required/>
		            	</div>
		       	   	</div>
		           	<div class="form-group">
		            	<label class="col-xs-3 control-label">参数类型</label>
		            	<div class="col-xs-6">
							<input id="paramType" name="paramType" class="form-control" type="text" value="${systemParam.paramType}" readonly/>
		            	</div>
		           	</div>
		           	<div class="form-group">
		            	<label class="col-xs-3 control-label">参数说明</label>
		            	<div class="col-xs-6">
		               		<textarea rows="3" id="paramRemark" name="paramRemark" class="form-control">${systemParam.paramRemark}</textarea>
		            	</div>
		           	</div>
		           	<div class="form-group">
		           		<div class="col-xs-6 col-xs-offset-4">
		           			<div class="col-xs-4">
		           				<button class="btn btn-default btn-block" type="submit"><i class='fa fa-cog fa-fw'></i> 设置</button>
		           			</div>
		           			<div class="col-xs-4">
		           				<button class="btn btn-default btn-block" type="button" id="backButton"><i class='fa fa-share-square-o fa-fw'></i> 返回</button>
		           			</div>
		           		</div>
		           	</div>
			    </form>
	  			</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>