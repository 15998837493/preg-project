<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/platform/tool/tool_diagnosis_history.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/customer/customer.js"></script>
<title>查询客户</title>
</head>
<body>
<input type="hidden" id="custId"/>
<div class="row">
	<div class="panel-body form-inline" id="customerCondition">
		<form id="customerQuery" action="${common.basepath }/${applicationScope.URL.Customer.QUERY_CUST_DIAGNOSIS}" method="post" class="form-horizontal">
			<input type='text' name="custName" class='form-control' placeholder='请输入客户姓名' />
			<button type="button" id="searchButton" name="operateButton" class="btn btn-default">
				<i class="fa fa-search fa-fw"></i>查询
			</button>
			<!--
			<div class="vertical-line"></div>
			 <button type="button" id="addButton" name="operateButton" class="btn btn-default">
				<i class="fa fa-plus fa-fw"></i> 建档
			</button> 
			 <button type="button" id="editButton" name="operateButton" class="btn btn-default">
				<i class='fa fa-edit fa-fw'></i> 编辑
			</button> -->
		</form>
	</div>
</div>
<div class="table-responsive">
	<table id="customerTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class='text-center'>病案号</th>
				<th class='text-center'>病人ID</th>
				<th class='text-center'>姓名</th>
				<th class='text-center'>年龄</th>
				<th class='text-center'>身份证号</th>
				<th class='text-center'>手机号</th>
				<th class='text-center'>营养诊疗次数</th>
				<th class='text-center'>建档信息</th>
				<th class='text-center'>系统营养评价</th>
				<th class='text-center'>就诊记录</th>
				<th class='text-center'>分娩结局</th>
			</tr>
		</thead>
	</table>
</div>
<!--  建档信息modal-->
<div id="pregnancyModal" class="modal fade bs-example-modal">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content">
			<div class="modal-body">
				<form id="queryForm" action="${common.basepath }/${applicationScope.URL.Customer.QUERY_CUST_PREG_RECPRD}">
					<input type="hidden" name="custId"/>
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left"><i class="fa fa-search"></i>建档信息<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="panel panel-default" style="margin-bottom: 0px;">
							<div class="table-responsive">
								<table id="pregnancyTable" class="table table-bordered table-hover">
									<thead>
										<tr class="active">
											<th class="text-center">序号</th>
											<th class="text-center">建档日期</th>
											<th class="text-center">建档孕周数</th>
											<th class="text-center">建档医生</th>  
						                    <th class="text-center">建档信息</th> 
									   	</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!--  分娩结局modal-->
<div id="birthEndingModal" class="modal fade bs-example-modal">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content">
			<div class="modal-body">
				<form id="queryBirthEndingForm" action="${common.basepath }/${applicationScope.URL.BirthEnding.BIRTHENDING_GETLIST}">
					<input type="hidden" name="custId"/>
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left"><i class="fa fa-search"></i>分娩记录<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="panel panel-default" style="margin-bottom: 0px;">
							<div class="table-responsive">
								<table id="birthEndingTable" class="table table-bordered table-hover">
									<thead>
										<tr class="active">
											<th class="text-center">序号</th>
											<th class="text-center">分娩日期</th>
											<th class="text-center">分娩孕周</th>
											<th class="text-center">分娩医院</th>  
						                    <th class="text-center">产检医院</th> 
						                    <th class="text-center">登记时间</th> 
						                    <th class="text-center">登记人员</th> 
						                    <th class="text-center">操作</th> 
									   	</tr>
									</thead>
								</table>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 弹窗添加、编辑 -->
<form id="editItemForm" name="editItemForm" action="" class="form-horizontal" method="post">
	<div id="editItemModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center" id="tittle"></div>
						<div class="row">
							<div class="col-xs-9 col-xs-offset-1 row-top">
								<div class="form-group">
					        		<label class="col-xs-2 control-label">ID</label>
					                <div class="col-xs-4">
					                    <input type="text" id="custPatientId" name="custPatientId" class="form-control" maxlength="50"/>
					                </div>
					       			<label class="col-xs-2 control-label">姓名</label>
					                <div class="col-xs-4">
					                    <input id="custName" name="custName" class="form-control" type="text" maxlength="30" value=""/>
					                </div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-2 control-label">手机号码</label>
					                <div class="col-xs-4">
					                    <input id="custPhone" name="custPhone" class="form-control" type="text" maxlength="11" value=""/>
					                </div>
					                <label class="col-xs-2 control-label">身份证号</label>
					                <div class="col-xs-4">
					                	<input id="oldCustIcard" name="oldCustIcard" type="hidden"/>
					                    <input id="custIcard" name="custIcard" class="form-control" type="text" maxlength="23" onblur="initBirthSex(this.value)" />
					                </div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-2 control-label">性别</label>
					                <div class="col-xs-4">
					                    <select id="custSex" name="custSex" class="form-control">
						           		</select>
					                </div> 
					            	<label class="col-xs-2 control-label">出生日期</label>
					                <div class="col-xs-4">
					                    <div class="input-group">
									      	<input id="custBirthday" name="custBirthday" type="text" class="form-control form_date" placeholder="请选择出生日期" readonly value=""/>
									      	<span class="input-group-btn">
									        	<button class="btn btn-primary" type="button" onclick="common.dateShow('custBirthday')"><i class="fa fa-calendar fa-fw"></i>选择</button>
									      	</span>
						    			</div>
					                </div>
					            </div>    
					            <div class="form-group">
					                <label class="col-xs-2 control-label">文化程度</label>
					                <div class="col-xs-4">
					                    <select id="custEducation" name="custEducation" class="form-control">
					                	</select>
					                </div>	
					                <label class="col-xs-2 control-label">职业</label>
					                <div class="col-xs-4">
					                    <select id="custJob" name="custJob" class="form-control">
					                	</select>
					                </div>		                
					            </div>	
					            <div class="form-group">
					                <label class="col-xs-2 control-label">婚姻状况</label>
					                <div class="col-xs-4">
					                    <select id="custMarriage" name="custMarriage" class="form-control">
					                	</select>
					                </div>
					                <label class="col-xs-2 control-label">家庭收入</label>
					                <div class="col-xs-4">
					                    <select id="custIncome" name="custIncome" class="form-control" >
					                	</select>
					                </div>
					            </div>        
						        <div class="form-group">
					                <label class="col-xs-2 control-label">出生体重</label>
					                <div class="col-xs-4">
					                    <div class="input-group">
						      				<input id="custBirthWeight" name="custBirthWeight" class="form-control text-right" type="text" maxlength="5" value=""/>
						      				<div class="input-group-addon">kg</div>
						    			</div>
					                </div>  
					            	<label class="col-xs-2 control-label">孕前体重</label>
					                <div class="col-xs-4">
					                    <div class="input-group">
						      				<input id="custWeight" name="custWeight" class="form-control text-right" type="text" maxlength="5" value=""/>
						      				<div class="input-group-addon">kg</div>
						    			</div>
					                </div>                
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-2 control-label">身高</label>
					                <div class="col-xs-4">
					                    <div class="input-group">
						      				<input id="custHeight" name="custHeight" class="form-control text-right" type="text" maxlength="5" value="" />
						      				<div class="input-group-addon">cm</div>
						    			</div>
					                </div>
					            	<label class="col-xs-2 control-label">孕前腰围</label>
					                <div class="col-xs-4">
					                    <div class="input-group">
						      				<input id="custWaistline" name="custWaistline" class="form-control text-right" type="text" maxlength="5" />
						      				<div class="input-group-addon">cm</div>
						    			</div>
					                </div>
					            </div>
					        	<div class="form-group">
					            	<label class="col-xs-2 control-label">末次月经</label>
					                <div class="col-xs-4">
					                    <div class="input-group">
									      	<input id="lmp" name="lmp" type="text" class="form-control form_date" placeholder="请选择末次月经日期" readonly value="" onchange="calculationDelivery();"/>
									      	<span class="input-group-btn">
									        	<button class="btn btn-primary" type="button" onclick="common.dateShow('lmp')"><i class="fa fa-calendar fa-fw"></i>选择</button>
									      	</span>
						    			</div>
					                </div>	
					                <label class="col-xs-2 control-label">预产期</label>
					                <div class="col-xs-4">
				                    	<input id="pregnancyDueDate" name="pregnancyDueDate" class="form-control" type="text" readonly/>
					                </div>				             
					            </div>				            
					        	<div class="form-group">
					       			<label class="col-xs-2 control-label">体力强度</label>
						            <div class="col-xs-4">
						            	<select id="custPlevel" name="custPlevel" class="form-control">
						           		</select>
						            </div>
					                <label class="col-xs-2 control-label">民族</label>
					                <div class="col-xs-4">
					                    <select id="custNation" name="custNation" class="form-control">
					                	</select>
					                </div>
					            </div>		            	
					            <div class="form-group">
					                <label class="col-xs-2 control-label">省/市/区</label>
					                <div class="col-xs-10">
					                	<div class="row">
						                	<div class="col-xs-4">
						                    	<select id="custProvince" name="custProvince" class=" form-control" onchange="common.getChinaArea(this.value,'custCity','custDistrict')">
						                 			<option value="">选择省</option>
						                 		</select>
					                 		</div>
					                 		<div class="col-xs-4">
						                  		<select id="custCity" name="custCity" class="col-xs-4 form-control" onchange="common.getChinaArea(this.value,'','custDistrict')">
						                 			<option value="">选择市</option>
						                 		</select>
					                 		</div>
						                	<div class="col-xs-4">
							                 	<select id="custDistrict" name="custDistrict" class="form-control">
							                 		<option value="">选择区</option>
							                 	</select>
						                	</div>
							            </div>
							        </div>
								</div>	
							    <div class="form-group">
					                <label class="col-xs-2 control-label">病案号</label>
					                <div class="col-xs-10">
					                    <input id="custSikeId" name="custSikeId" class="form-control" type="text" maxlength="80" value=""/>
					                    <input id="custSikeIdOld" name="custSikeIdOld" type="hidden"/>
					                </div>
							    </div>
							    <div class="form-group">
					                <label class="col-xs-2 control-label">现居住地址</label>
					                <div class="col-xs-10">
					                    <input id="custAddress" name="custAddress" class="form-control" type="text" maxlength="80" value=""/>
					                </div>
							    </div>
							</div>
						</div>
					</div>
					<div class="panel-body text-right" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-8 no-right" >
							<button type="submit" class="btn btn-primary btn-block" id="span_button">保存</button>
						</div>
						<div class="col-xs-2 no-right" >
							<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>
</body>
</html>