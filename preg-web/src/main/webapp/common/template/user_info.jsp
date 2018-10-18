<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link type=text/css rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<script type="text/javascript" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />

<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
//树结点集合
var zNodes = ${treeNodes};
</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/system/user/user.js"></script>

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
.ztree {
    margin: 0;
    padding: 5px;
    color: #333;
    padding-left: 0px;
    padding-top: 8px;
}
.ztree li ul {
    padding: 0 0 5px 30px;
}
.ztree li span.button.chk {
    width: 13px;
    height: 13px;
    margin: 0 7px 0 0;
    cursor: auto;
}
</style>
<title>编辑用户</title>
<script type="text/javascript">
$().ready(function() {
	common.initCodeSelect("status","status","userStatus","${userVo.userStatus}");
	
	// 初始化 uploadifive 上传控件
	common.uploadifive("上传头像",function(data){
		var pic = JSON.parse(data);
		var path = pic.uppath;
		$('#userHeadSculpture').val(path);
		$('#showpic').attr("src", path);
	});
	
	// 初始化用户出生日期
	common.initDate(null,null,4,"#userBirthday");
	$("#userBirthday").datetimepicker("update","${userVo.userBirthday}");
	// 初始化用户职务
	$("#roleId").val("${userVo.roleId}");
	// 初始化用户头像
	if(!common.isEmpty("${userVo.userHeadSculpture}")){
		$('#showpic').attr("src", "${userVo.userHeadSculpture}");
	};
});
</script>
<body>
<div id="DeptModal" class="modal fade bs-example-modal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title">选择部门</h3>
			</div>
			<div class="modal-body">
			<div class="row">
				<div class="form-group">
					<div class="col-xs-8" style="margin-left: 28%;"><ul id="zTree" class="ztree"></ul></div>
				</div>
			</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="chooseDept();">确定</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="container-fluid">
	<div class="row row-top">
		<form id="updateUserForm" name="updateUserForm" action="${common.basepath}/${applicationScope.URL.System.USER_UPDATE_MYINFO}" class="form-horizontal" method="post">
			<input type="hidden" id="userId" name="userId" value="${userVo.userId }"/>
			<div class="col-xs-10 col-xs-offset-1 border-bottom-blue">
				<label class="label-title"></label>
			</div>
			<div class="col-xs-4 row-top no-right">
				<div class="form-group">
			        <div class="col-xs-4 col-xs-offset-8 no-right" style="padding-left:30px;">
			        	<img class="img-thumbnail" id="showpic" style="width:100px;height:100px;margin-bottom:5px;" src="${common.basepath }/common/images/upload_head.png"/>
				        <input type="file" name="file_upload" id="file_upload" />
				        <input type="hidden" id="userHeadSculpture" name="userHeadSculpture" value="${userVo.userHeadSculpture }"/>
				        <input type="hidden" id="userHeadSculptureOld" name="userHeadSculptureOld" value="${userVo.userHeadSculpture }"/>
			        </div>
              	</div>
			</div>
			<div class="col-xs-6 row-top no-left">
		        <div class="form-group">
		        	<label class="col-xs-2 control-label">用户编号</label>
			        <div class="col-xs-4">
			        	<input id="userCode" name="userCode" class="form-control" type="text" maxlength="32" value="${userVo.userCode }" readonly/>
			        </div>
              	</div>
              	<div class="form-group">
		        	<label class="col-xs-2 control-label">身份证号</label>
			        <div class="col-xs-4">
			        	<input id="userIcard" name="userIcard" class="form-control" type="text" maxlength="64" value="${userVo.userPhone }"/>
			        </div>
              	</div>
              	<div class="form-group">
			    	<label class="col-xs-2 control-label">用户姓名</label>
                    <div class="col-xs-4">
                        <input id="userName" name="userName" class="form-control" type="text" maxlength="80" value="${userVo.userName }"/>
                    </div>
              	</div>
              	<div class="form-group">
			    	<label class="col-xs-2 control-label">用户性别</label>
			    	<input type="hidden" id="hiddenSex" value="${userVo.userSex}">
                    <div class="col-xs-4">
                        <select id="userSex" name="userSex" class="form-control">
                        	<%-- <option value="">==请选择性别==</option>
                        	<c:if test="${not empty sexs}">
								<c:forEach items="${sexs }" var="sex">
									<option value="${sex.codeValue }" ${userVo.userSex==sex.codeValue?'selected':''}>${sex.codeName }</option>
								</c:forEach>
							</c:if> --%>
                        </select>
                    </div>
              	</div>
              	<%-- <div class="form-group">
			    	<label class="col-xs-2 control-label">用户部门</label>
                    <div class="col-xs-4">
                        <div class="input-group">
                        	<input id="deptId" name="deptId" type="hidden" value="${userVo.deptId }"/>
					      	<input id="deptName" name="deptName" class="form-control" type="text" placeholder="请选择部门" value="${userVo.deptName }" readonly/>
					      	<span class="input-group-btn">
					        	<button class="btn btn-primary" type="button" onclick="showZTreeModal();"><i class="fa fa-user fa-fw"></i>选择</button>
					      	</span>
		    			</div>
                    </div>
              	</div> --%>
              	<div class="form-group">
			    	<label class="col-xs-2 control-label">出生日期</label>
                    <div class="col-xs-4">
                        <div class="input-group">
					      	<input id="userBirthday" name="userBirthday" type="text" class="form-control form_date" placeholder="请选择出生日期" readonly value=""/>
					      	<span class="input-group-btn">
					        	<button class="btn btn-info" type="button" onclick="common.dateShow('userBirthday')"><i class="fa fa-calendar fa-fw"></i>选择</button>
					      	</span>
		    			</div>
                    </div>
              	</div>
              	<div class="form-group">
              		<label class="col-xs-2 control-label">用户手机</label>
                    <div class="col-xs-4">
                        <input id="userPhone" name="userPhone" class="form-control" type="text" maxlength="22" value="${userVo.userPhone }"/>
                    </div>
              	</div>
              	<div class="form-group">
                    <label class="col-xs-2 control-label">用户邮箱</label>
                    <div class="col-xs-4">
                        <input id="userEmail" name="userEmail" class="form-control" type="text" maxlength="50" value="${userVo.userEmail }"/>
                    </div>
              	</div>
              	<%-- <div class="form-group">
			        <label class="col-xs-2 control-label">用户职位</label>
                    <div class="col-xs-4">
						<select name="roleId" id="roleId" class="form-control">
					  		<option value="">==请先选择职位==</option>
					  		<c:forEach items="${roleList }" var="list" varStatus="i">
			        			<option value="${list.roleId }">${list.roleName }</option>
			        		</c:forEach>
						</select>
                    </div>
		        </div>
		        <div class="form-group">
					<label class="col-xs-2 control-label">用户状态</label>
					<div class="col-xs-4">
						<select id="userStatus" name="userStatus" class="form-control"></select>
					</div>
				</div>--%>
			</div> 
			<div class="col-xs-10 col-xs-offset-1 border-top-blue row-top">
				<div class="col-xs-8 col-xs-offset-3 ">
                   	<div class="col-xs-4">
                   		<button class="btn btn-info btn-block" type="submit"><i class="fa fa-save fa-fw"></i>保存</button>
                   	</div>
                   	<div class="col-xs-4">
                       	<button class="btn btn-info btn-block" type="button" id="backButton"><i class="fa fa-share-square-o fa-fw"></i>返回</button>
                    </div>
                </div>
			</div>
		</form>
	</div>
</div>
</body>
</html>