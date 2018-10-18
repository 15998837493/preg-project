<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<link type=text/css rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<script type="text/javascript" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<title>编辑机构信息</title>
<script type="text/javascript">
/**
 * 验证参数设置
 */
var companyOption = {
	rules: {
		insName: {
			required: true,
			remote:	{
				url: URL.get("System.INSNAME_CHECK"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    insName: function() {  
				        return $("#insName").val();  
					},
					insNameOld: function() {  
						return $("#insNameOld").val(); 
					},
					operateType: function() {  
				        return "update";  
					},
					random: function() {
						return Math.random();
					}
				}
			},
			maxlength: 100
		},
		insPlace: {
			required: true
		},
		insGrade: {
			required: true
		},
		insType: {
			required: true
		},
		insPost: {
			required: true,
			maxlength: 12
		},
		insTel: {
			required: true,
			maxlength: 20
		},
		insAddress: {
			required: true,
			maxlength: 200
		},
		insState: {
			required: true
		}
	},
	messages: {
		insName: {
			remote: "名称已经存在，请重新输入"
		}
	},
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function () {
			$(form).ajaxPost(dataType.json,function(data){
				common.pageForward(URL.get("System.INS_CONFIG"));
			});
		});
	}
};

$().ready(function(){
	//加入必填项提示
	$("#updateCompanyForm").validate(companyOption);
	common.requiredHint("updateCompanyForm",companyOption);
	
	common.initCodeSelect("status","status","insState","${insVo.insState}");
	
	// 初始化数据
	$("#insType").val("${insVo.insType}");
	$("#insPlace").val("${insVo.insPlace}");
	$("#insGrade").val("${insVo.insGrade}");
	
	// 初始化 uploadifive 上传控件
	common.uploadifive("选择图片",function(data){
		$('#insLogo').val(data);
		$('#showpic').attr("src", data);
	});
	
	// 初始化用户头像
	var serverPath = URL.get("resource.server.path")+URL.get("path.constants.ins_logo_image");
	if(!common.isEmpty("${insVo.insLogo}")){
		$('#showpic').attr("src",serverPath + "${insVo.insLogo}"+"?t=" + new Date());
	}
	
	layer.close(layer.index);
});

//取消操作
function cancelUpdate(){
	common.pageForward(URL.get("System.INS_VIEW"));//公司
}
</script>
<body>
<input id="insNameOld" name="insNameOld" type="hidden" value="${insVo.insName }"/>
<div class="container-fluid">
	<div class="row row-top">
		<div class="panel panel-default">
			<!-- <div class="panel-heading">
				<label class="label-title"><i class="fa fa-edit fa-fw"></i> 编辑机构信息</label>
			</div> -->
			<form id="updateCompanyForm" name="updateCompanyForm" action="${common.basepath}/page/institution/ins_update.action" class="form-horizontal" method="post">
				<div class="panel-body">
				<div class="row">
					<div class="col-xs-3 no-right col-xs-offset-1">
					        <div class="form-group">
					        	<label class="col-xs-4 control-label">机构图片</label>
					        	<div class="col-xs-5">
					        		<img class="img-thumbnail" id="showpic" style="width:100px;height:100px;margin-bottom:5px;" src="${common.basepath }/common/images/ins_logo.png"/>
						        	<input type="file" name="file_upload" id="file_upload"  class="form-control" />
						        	<input type="hidden" id="insLogo" class="form-control" name="insLogo" value="${insVo.insLogo }"/>				        	
					        	</div>
			              	</div>			        
					</div>
					<div class="col-xs-8  no-left">
					        <div class="form-group">
					        	<label class="col-xs-1 control-label">机构编码</label>
						        <div class="col-xs-4">
						        	<input id="insId" name="insId" class="form-control" type="text" value="${insVo.insId }" readonly/>
						        </div>
						        <label class="col-xs-1 control-label">机构名称</label>
						        <div class="col-xs-4">
						        	<input id="insName" name="insName" class="form-control" type="text" value="${insVo.insName }" maxlength="100" />
						        </div>
			              	</div>
			              	<div class="form-group">
					        	<label class="col-xs-1 control-label">机构电话</label>
			                    <div class="col-xs-4">
			                        <input id="insTel" name="insTel" class="form-control" type="text" value="${insVo.insTel }" maxlength="20" />
			                    </div>					        
			                    <label class="col-xs-1 control-label">机构邮编</label>
						        <div class="col-xs-4">
						        	<input id="insPost" name="insPost" class="form-control" type="text" value="${insVo.insPost }" maxlength="12" />
						        </div>					        
			              	</div>
	<!-- 		              	<div class="form-group">
			                    <label class="col-xs-2 control-label">客户类型</label>
			                    <div class="col-xs-4">
			                        <select id="insType" name="insType" class="form-control">
			                        	<option value="">==请选择==</option>
			                        	<option value="1">1</option>
			                        </select>
			                    </div>
						        <label class="col-xs-2 control-label">客户等级</label>
			                    <div class="col-xs-4">
			                        <select id="insGrade" name="insGrade" class="form-control">
			                        	<option value="">==请选择==</option>
			                        	<option value="1">1</option>
			                        </select>
			                    </div>		                    
			              	</div> -->
	<%-- 		              	<div class="form-group">
					        	<label class="col-xs-2 control-label">机构地区</label>
						        <div class="col-xs-4">
						        	<select id="insPlace" name="insPlace" class="form-control">
			                        	<option value="">==请选择==</option>
			                        	<option value="1">1</option>
			                        </select>
						        </div>
			                   	<label class="col-xs-2 control-label">机构传真</label>
			                    <div class="col-xs-4">
			                        <input id="insFax" name="insFax" class="form-control" type="text" value="${insVo.insFax }" maxlength="20" />
			                    </div>		                    
			              	</div> --%>
			              	<div class="form-group">
					        	<label class="col-xs-1 control-label">机构网址</label>
			                    <div class="col-xs-4">
			                        <input id="insWeb" name="insWeb" class="form-control" type="text" maxlength="50" value="${insVo.insWeb }"/>
			                    </div>
								<label class="col-xs-1 control-label">机构状态</label>
			                    <div class="col-xs-4">
			                        <select id="insState" name="insState" class="form-control"></select>
			                    </div>		                    
			              	</div>
			              	<div class="form-group">
			                    <label class="col-xs-1 control-label">机构地址</label>
			                    <div class="col-xs-9">
			                        <input id="insAddress" name="insAddress" class="form-control" type="text" value="${insVo.insAddress }" maxlength="200" />
			                    </div>
			              	</div>
			              	<div class="form-group">
			                    <div class="col-xs-2 col-xs-offset-4">
			                        <button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i> 保存</button>
			                    </div>
			              	</div>		              	
					</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
