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
<title>机构注册</title>
<script type="text/javascript">
/**
 * 验证参数设置
 */
var insOptions = {
	rules: {
		insId: {
			required: true,
			remote:	{
				url: URL.get("System.INSID_CHECK"),//后台处理程序  
				type: "post",               		//数据发送方式  
				dataType: "json",           		//接受数据格式     
				data: {              	      		//要传递的数据，默认已传递应用此规则的表单项  
				    insId: function() {  
				        return $("#insId").val();  
					},
					random: function() {
						return Math.random();
					}
				}
			}
		},
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
				        return "";  
					},
					operateType: function() {  
				        return "add";  
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
			required: true
		},
		insTel: {
			required: true
		},
		insAddress: {
			required: true
		},
		insState: {
			required: true
		}
	},
	messages: {
		insId: {
			remote: "编码已经存在，请重新输入"
		},
		insName: {
			remote: "名称已经存在，请重新输入"
		}
	},
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function() {
		layer.confirm("确定要执行【保存】操作？", function () {
			$('#addForm').ajaxPost(dataType.json,function(data){
				common.pageForward(URL.get("System.INS_VIEW"));
			});
		});
	}
};

$().ready(function(){
	//加入必填项提示
	$("#addForm").validate(insOptions);
	common.requiredHint("addForm",insOptions);
	
	common.initCodeSelect("status","status","insState");
	
	// 初始化 uploadifive 上传控件
	common.uploadifive("企业LOGO",function(data){
		$('#insLogo').val(data);
		$('#showpic').attr("src", data);
	});
	
	// 返回
	$("#backButton").click(function(){
		common.pageForward(URL.get("System.INS_VIEW"));
	});
});
</script>
<body>
<div class="container-fluid">
	<div class="row row-top">
		<form id="addForm" class="form-horizontal" action="${common.basepath}${applicationScope.URL.System.INS_ADD}" method="post">
			<div class="col-xs-10 col-xs-offset-1 border-bottom-blue">
				<label class="label-title"><i class="fa fa-edit fa-fw"></i>填写机构信息</label>
			</div>
			<div class="col-xs-3 row-top no-right">
				<div class="form-group">
			        <div class="col-xs-6 col-xs-offset-6 no-right">
			        	<img class="img-thumbnail" id="showpic" style="width:100px;height:100px;margin-bottom:5px;" src="${common.basepath }/common/images/ins_logo.png"/>
				        <input type="file" name="file_upload" id="file_upload" />
				        <input type="hidden" id="insLogo" name="insLogo" />
			        </div>
              	</div>
			</div>
			<div class="col-xs-6 row-top no-left">
		        <div class="form-group">
		        	<label class="col-xs-2 control-label">机构编码</label>
			        <div class="col-xs-4">
			        	<input id="insId" name="insId" class="form-control" type="text" maxlength="64" />
			        </div>
			        <label class="col-xs-2 control-label">机构名称</label>
			        <div class="col-xs-4">
			        	<input id="insName" name="insName" class="form-control" type="text" maxlength="100" />
			        </div>
              	</div>
              	<div class="form-group">
		        	<label class="col-xs-2 control-label">机构地区</label>
			        <div class="col-xs-4">
			        	<select id="insPlace" name="insPlace" class="form-control">
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
              	</div>
              	<div class="form-group">
                    <label class="col-xs-2 control-label">客户类型</label>
                    <div class="col-xs-4">
                        <select id="insType" name="insType" class="form-control">
                        	<option value="">==请选择==</option>
                        	<option value="1">1</option>
                        </select>
                    </div>
                    <label class="col-xs-2 control-label">机构邮编</label>
			        <div class="col-xs-4">
			        	<input id="insPost" name="insPost" class="form-control" type="text" maxlength="12" />
			        </div>
              	</div>
              	<div class="form-group">
		        	<label class="col-xs-2 control-label">机构电话</label>
                    <div class="col-xs-4">
                        <input id="insTel" name="insTel" class="form-control" type="text" maxlength="20" />
                    </div>
					<label class="col-xs-2 control-label">机构状态</label>
                    <div class="col-xs-4">
                        <select id="insState" name="insState" class="form-control"></select>
                    </div>
              	</div>
              	<div class="form-group">
		        	<label class="col-xs-2 control-label">机构网址</label>
                    <div class="col-xs-4">
                        <input id="insWeb" name="insWeb" class="form-control" type="text" maxlength="50" />
                    </div>
                   	<label class="col-xs-2 control-label">机构传真</label>
                    <div class="col-xs-4">
                        <input id="insFax" name="insFax" class="form-control" type="text" maxlength="20" />
                    </div>
              	</div>
              	<div class="form-group">
                    <label class="col-xs-2 control-label">机构地址</label>
                    <div class="col-xs-10">
                        <input id="insAddress" name="insAddress" class="form-control" type="text" maxlength="200" />
                    </div>
              	</div>
			</div>
			<div class="col-xs-10 col-xs-offset-1 border-top-blue row-top">
				<div class="col-xs-8 row-top" style="margin-left: 30%">
                   	<div class="col-xs-4">
                   		<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i> 注册</button>
                   	</div>
                   	<div class="col-xs-4">
                       	<button id="backButton" class="btn btn-primary btn-block" type="button">
                       		<i class="fa fa-share-square-o fa-fw"></i> 返回
                       	</button>
                    </div>
                </div>
			</div>
		</form>
	</div>
</div>
</body>
</html>
