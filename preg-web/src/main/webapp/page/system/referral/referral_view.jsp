<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/system/referral/referral.js"></script>
<title>评价项目</title>
</head>
<body>
<input type="hidden" id="id"/>
<!-- 弹窗添加、编辑 -->
<form id="editReferralForm" name="editReferralForm" action="" class="form-horizontal" method="post">
	<div id="editReferralModal" class="modal fade bs-example-modal">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-search"></i>编辑科室信息
							<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
						</div>
						<div class="row">
		                    <div class="col-xs-9 col-xs-offset-1 row-top">
		                        <input type="hidden" id="referralId" name="referralId" />
		                        <input type="hidden" id="referralCode" name="referralCode" />
		                        <div class="form-group">
		                            <label class="col-xs-3 control-label">科室名称</label>
		                            <div class="col-xs-9">
		                                <input id="referralName" name="referralName" placeholder="请输入名称"  class="form-control" type="text"/>
		                            	<input id="referralOldName" name="referralOldName" type="hidden"/>
		                            </div>
		                        </div>
		                        <div class="form-group">
									<label class="col-xs-3 control-label">备注</label>
									<div class="col-xs-9">
										<textarea id="referralPs" 
									                  name="referralPs" 
									                  class="form-control text-left" 
									                  placeholder="点击输入内容" 
									                  style="background-color: white;" maxlength="1000"></textarea>
									</div>   	
		                        </div>  
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right">
							<button type="submit" class="btn btn-primary btn-block">确认</button>
						</div>
					</div>	
				</div>		
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>
<div class="row">
	<div class="panel-body form-inline" id="referralCondition">
    	<form id="referralQuery" name="referralQuery" action="${common.basepath}/${applicationScope.URL.referral.REFERRAL_QUERY_BY_CONTENT}" method="post">
            <input name="content" class="form-control" type="text" placeholder="请输入科室编码/名称">
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
	<table  id="referralListTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">科室编码</th>
				<th class="text-center">科室名称</th>
		   	</tr>
		</thead>
	</table>
</div>
</body>
</html>