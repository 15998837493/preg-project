<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>食物模版一览页</title>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/system/referral/doctor_view.js"></script>
</head>
 <body> 
  <input type="hidden" id="dietId" name="dietId" /> 
  <div class="row"> 
   <div class="panel-body"> 
    <div id="dtableCondition"> 
     <form id="dtableQueryForm" action="${common.basepath}/${applicationScope.URL.System.REFERRAL_DOCTOR_VIEW}" method="post" class="form-horizontal"> 
      <div class="form-inline"> 
       <input type="text" id="searchText" name="dietName" class="form-control" placeholder="请输入医生编码/姓名" /> 
       <button id="search" type="button" name="operateBtn" class="btn btn-default"> <i class="fa fa-search fa-fw"></i>查询 </button> 
       <div class="vertical-line"></div> 
       <button id="add" type="button" name="operateBtn" class="btn btn-default"> <i class="fa fa-plus fa-fw"></i> 增加 </button> 
       <button id="update" type="button" name="operateBtn" class="btn btn-default"> <i class="fa fa-edit fa-fw"></i> 编辑 </button> 
       <button id="remove" type="button" name="operateBtn" class="btn btn-default"> <i class="fa fa-remove fa-fw"></i> 删除 </button> 
      </div> 
     </form> 
    </div> 
   </div> 
  </div> 
  <div class="table-responsive"> 
   <table id="dTable" class="table table-bordered table-hover"> 
    <thead> 
     <tr class="active"> 
      <th class="text-center">选择</th> 
      <th class="text-center">医生工号</th> 
      <th class="text-center">医生姓名</th> 
      <th class="text-center">医生性别</th> 
      <th class="text-center">所属科室</th> 
     </tr> 
    </thead> 
   </table> 
  </div> 
  <!-- 弹出窗口 --> 
  <form id="editForm" class="form-horizontal" method="post"> 
   <input type="hidden" id="id" name="id" /> 
   <div id="editModal" class="modal fade bs-example-modal"> 
    <div class="modal-dialog modal-lg"> 
     <div class="modal-content"> 
      <div class="modal-body"> 
       <div class="panel panel-lightblue"> 
        <div class="panel-heading text-left"> 
         <i class="fa fa-edit fa-fw"></i>编辑医生信息 
         <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a> 
        </div> 
        <div class="panel-body"> 
         <div class="row"> 
          <input id="editFormType" name="editFormType" class="form-control" type="hidden" /> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">医生工号</label> 
            <div class="col-xs-6"> 
             <input id="doctorId" maxlength="25" name="doctorId" class="form-control" /> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">医生姓名</label> 
            <div class="col-xs-6"> 
             <input id="doctorName" maxlength="6" name="doctorName" class="form-control" /> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">所属科室</label> 
            <div class="col-xs-6"> 
             <select id="doctorDepartmentId" name="doctorDepartmentId" class="form-control"> </select> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">身份证号</label> 
            <div class="col-xs-6"> 
             <input id="doctorIcard" name="doctorIcard" class="form-control" /> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">医生性别</label> 
            <div class="col-xs-6"> 
             <select id="doctorSex" name="doctorSex" class="form-control"> </select> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">出生日期</label> 
            <div class="col-xs-6"> 
             <input id="doctorBirthday" name="doctorBirthday" class="form-control" /> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">医生手机</label> 
            <div class="col-xs-6"> 
             <input id="doctorPhone" name="doctorPhone" class="form-control" /> 
            </div> 
           </div> 
          </div> 
          <div class="form-group"> 
           <div> 
            <label class="col-xs-4 control-label">医生职位</label> 
            <div class="col-xs-6"> 
             <input id="doctorJob" maxlength="30" name="doctorJob" class="form-control" /> 
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
  </form> 
 </body>
</html>