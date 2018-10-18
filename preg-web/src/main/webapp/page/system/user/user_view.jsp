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
  <script type="text/javascript">
  var userData; // 选中项信息
  var userRow; // 选中行信息
  var userTable; // table，注意命名不要重复
  //配置datatable
  var tableOptions = {
      id: "userListTable",
      form: "userQuery",
      columns: [{
          "data": null,
          "sClass": "text-center",
          "render": function(data, type, row, meta) {
              return "<input type='radio' name='tableRadio' value='" + data.userId + "' />";
          }
      },
      {
          "data": "userId",
          "sClass": "text-center"
      },
      {
          "data": "userName",
          "sClass": "text-center"
      },
      {
          "data": "userCode",
          "sClass": "text-center"
      },
      {
          "data": "roleName",
          "sClass": "text-center"
      },
      /* 		{"data": "deptName","sClass": "text-center" }, */
      {
          "data": "userType",
          "sClass": "text-center",
          render: function(data, type, row, meta) {
              return CODE.transCode("usertype", data);
          }
      },
      {
          "data": "userPhone",
          "sClass": "text-center"
      },
      {
          "data": "userStatus",
          "sClass": "text-center",
          render: function(data, type, row, meta) {
              return CODE.transCode("status", data);
          }
      }],
      rowClick: function(data, row) {
          userData = data;
          userRow = row;
          $("#userId").val(data.userId);
          $("#userTypeSign").val(data.userType);
          $("#userNameSign").val(data.userName);
      },
      condition: "userCondition",
      selecttarget: [5] // 当 condition中存在select时，需要匹配与之对应的列下标，注意数组长度和select个数相同
  };

  $().ready(function() {
      // 初始化datatables
      userTable = datatable.table(tableOptions);
  });
  </script>
  <script type="text/javascript" charset="utf-8" src="${common.basepath}/page/system/user/user.js"></script>
 </head>
 <body> 
  <div class="row"> 
   <div class="panel-body"> 
    <div id="userCondition"> 
     <form id="userQuery" action="${common.basepath }/${applicationScope.URL.User.USER_QUERY}"> 
      <div class="form-inline"> 
       <input type="hidden" id="userTypeSign" /> 
       <input type="hidden" id="userNameSign" /> 
       <input type="text" id="userName" name="userName" class="form-control" placeholder="请输入检索内容" /> 
       <select id="status" name="userStatus" class="form-control"></select> 
       <button type="button" id="searchButton" name="userButton" class="btn btn-default"> <i class="fa fa-search fa-fw"></i>查询 </button> 
       <div class="vertical-line"></div> 
       <button type="button" id="addButton" name="userButton" class="btn btn-default"> <i class="fa fa-plus fa-fw"></i> 增加 </button> 
       <button type="button" id="editButton" name="userButton" class="btn btn-default"> <i class="fa fa-edit fa-fw"></i> 编辑 </button> 
       <button type="button" id="resetPswButton" name="userButton" class="btn btn-default"> <i class="fa fa-key fa-fw"></i> 重置密码 </button> 
       <button type="button" id="config" name="userButton" class="btn btn-default"> <i class="fa fa-cogs"></i> 医生助理配置 </button> 
      </div> 
     </form> 
    </div> 
   </div> 
  </div> 
  <div class="table-responsive"> 
   <table id="userListTable" class="table table-bordered table-hover"> 
    <thead> 
     <tr class="active"> 
      <th class="text-center">选择</th> 
      <th class="text-center">编号</th> 
      <th class="text-center">姓名</th> 
      <th class="text-center">帐号</th> 
      <th class="text-center">职位</th> 
      <!-- <th class="text-center">部门</th> --> 
      <th class="text-center">用户类别</th> 
      <th class="text-center">手机号</th> 
      <th class="text-center">状态</th> 
     </tr> 
    </thead> 
   </table> 
  </div> 
  <form id="addUserForm" name="addUserForm" action="" class="form-horizontal" method="post"> 
   <div id="updateModal" class="modal fade bs-example-modal"> 
    <div class="modal-dialog modal-lg"> 
     <div class="modal-content"> 
      <div class="modal-body"> 
       <div class="panel panel-lightblue"> 
        <div class="panel-heading text-left"> 
         <i class="fa fa-edit fa-fw"></i>人员管理 
         <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a> 
        </div> 
        <div class="row row-top"> 
         <div class="col-xs-3 no-right"> 
          <div class="form-group"> 
           <div class="col-xs-8 col-xs-offset-4 no-right" style="padding-left:30px;"> 
            <img class="img-thumbnail" id="showpic" style="width:100px;height:100px;margin-bottom:5px;" src="${common.basepath }/common/images/upload_head.png" /> 
            <input type="hidden" id="userId" name="userId" /> 
            <input type="file" name="file_upload" id="file_upload" /> 
            <input type="hidden" id="userHeadSculpture" name="userHeadSculpture" /> 
            <input type="hidden" id="userHeadSculptureOld" name="userHeadSculptureOld" /> 
           </div> 
          </div> 
         </div> 
         <div class="col-xs-9 no-left"> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户编号</label> 
           <div class="col-xs-6"> 
            <input id="userCode" name="userCode" class="form-control" type="text" maxlength="32" /> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户类别</label> 
           <div class="col-xs-6"> 
            <select id="userType" name="userType" class="form-control"> <option value="doctor">医师</option> <option value="assistant">助理</option> <option value="manager">维护人员</option> </select> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">身份证号</label> 
           <div class="col-xs-6"> 
            <input id="userIcard" name="userIcard" class="form-control" type="text" maxlength="64" /> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户姓名</label> 
           <div class="col-xs-6"> 
            <input id="userName" name="userName" class="form-control" type="text" maxlength="80" /> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户性别</label> 
           <div class="col-xs-6"> 
            <select id="userSex" name="userSex" class="form-control"> <option value="">==请选择性别==</option> <option value="male">男</option> <option value="female">女</option> </select> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">出生日期</label> 
           <div class="col-xs-6"> 
            <div class="input-group"> 
             <input id="userBirthday" name="userBirthday" type="text" class="form-control form_date" placeholder="请选择出生日期" readonly="" value="" /> 
             <span class="input-group-btn"> <button class="btn btn-primary" type="button" onclick="common.dateShow('userBirthday')"> <i class="fa fa-calendar fa-fw"></i>选择 </button> </span> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户手机</label> 
           <div class="col-xs-6"> 
            <input id="userPhone" name="userPhone" class="form-control" type="text" maxlength="22" /> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户邮箱</label> 
           <div class="col-xs-6"> 
            <input id="userEmail" name="userEmail" class="form-control" type="text" maxlength="50" /> 
           </div> 
          </div> 
          <div class="form-group"> 
           <label class="col-xs-2 control-label">用户职位</label> 
           <div class="col-xs-6"> 
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
           <div class="col-xs-6"> 
            <select id="userStatus" name="userStatus" class="form-control"></select> 
           </div> 
          </div> 
         </div> 
        </div> 
       </div> 
       <div class="panel-body text-right" style="padding: 0px;"> 
        <div class="col-xs-2 col-xs-offset-10 no-right"> 
         <button type="submit" class="btn btn-primary btn-block">保存</button> 
        </div> 
       </div> 
      </div> 
     </div>
     <!-- /.modal-content --> 
    </div>
    <!-- /.modal-dialog --> 
   </div>
   <!-- /.modal --> 
  </form> 
  <form id="configUserForm" name="configUserForm" action="" class="form-horizontal" method="post"> 
   <div id="configModal" class="modal fade bs-example-modal"> 
    <div class="modal-dialog modal-lg"> 
     <div class="modal-content"> 
      <div class="modal-body"> 
       <div class="panel panel-lightblue"> 
        <div class="panel-heading text-left"> 
         <i class="fa fa-edit fa-fw"></i>
         <span id="configTitle"></span> 
         <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a> 
        </div> 
        <div class="panel-body"> 
         <div class="row" id="configDiv"> 
          <input type="hidden" id="doctorId" name="doctorId" /> 
          <input type="hidden" id="assistantId" name="assistantId" /> 
          <input id="editFormType" name="editFormType" class="form-control" type="hidden" /> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label" id="configAuthorName"></label> 
            <div class="col-xs-6"> 
             <input id="configDoctorName" class="form-control" disabled="" /> 
            </div> 
           </div> 
          </div> 
         </div> 
        </div> 
       </div> 
       <div class="panel-body text-right" style="padding: 0px;"> 
        <div class="col-xs-2 col-xs-offset-10 no-right"> 
         <button type="submit" class="btn btn-primary btn-block">保存</button> 
        </div> 
       </div> 
      </div> 
     </div> 
    </div> 
   </div>
   <!-- /.modal --> 
  </form> 
 </body>
</html>