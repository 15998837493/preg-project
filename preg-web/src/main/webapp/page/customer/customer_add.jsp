<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>创建客户</title>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/customer/customer.js"></script>
<script type="text/javascript">
$().ready(function() {
	//初始化信息
	common.initCodeSelect("sex", "sex", "custSex","female");
	common.initCodeSelect('custLevel','custLevel','custLevel');
	common.initCodeSelect('nation','nation','custNation');
	common.initCodeSelect('custMarriage','custMarriage','custMarriage');
	common.initCodeSelect('custJob','custJob','custJob');
	common.initCodeSelect('custEducation','custEducation','custEducation');
	common.initCodeSelect('custPlevel','custPlevel','custPlevel');
	common.initCodeSelect('custIncome','custIncome','custIncome');
	//初始化省、市、区
	common.initChinaArea('0','0','0','custProvince','custCity','custDistrict');
	//验证、必填项提示
	$("#registerCustomerForm").validate(customerOptions);
	common.requiredHint("registerCustomerForm",customerOptions);
	
	//日期插件格式定义--出生日期
	common.initDate(null,null,4,"#custBirthday","1990-01-01");
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	$("#custBirthday").datetimepicker("setEndDate",nowDate);
	common.initDate(null,null,2,"#lmp",nowDate);
	$("#lmp").datetimepicker("setEndDate",nowDate);
	// 返回按钮点击事件
	$("#backButton").click(function(){
		common.pageForward("${common.basepath}/page/customer/customer_view.jsp");
	});
});
</script>
</head>
<body>
<div class="container-fluid">
	<div class="panel panel-default">
		<div class="panel-heading">
			<label class="label-title"><i class="fa fa-edit fa-fw"></i>基本信息</label>
		</div>
		<form id="registerCustomerForm" action="${common.basepath }/${applicationScope.URL.Customer.ADD_CUST}" class="form-horizontal" method="post">
			<div class="panel-body">
				<div class="col-xs-11">
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
						      	<input id="lmp" name="lmp" type="text" class="form-control form_date" placeholder="请选择末次月经日期" readonly value=""/>
						      	<span class="input-group-btn">
						        	<button class="btn btn-primary" type="button" onclick="common.dateShow('lmp')"><i class="fa fa-calendar fa-fw"></i>选择</button>
						      	</span>
			    			</div>
		                </div>	
		                <label class="col-xs-2 control-label">预产期</label>
		                <div class="col-xs-4">
		                	<div class="input-group">
		                    	<input id="pregnancyDueDate" name="pregnancyDueDate" class="form-control" type="text" readonly/>
		                    	<span class="input-group-btn">
						        	<button class="btn btn-primary" type="button" onclick="calculationDelivery()"><i class="fa fa-cloud-download"></i> 获取</button>
						      	</span>
			    			</div>
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
					            	<select id="custProvince" name="custProvince" class="form-control" onchange="common.getChinaArea(this.value,'custCity','custDistrict')">
					                	<option value="">选择省</option>
					                </select>
					            </div>
					            <div class="col-xs-4">
					            	<select id="custCity" name="custCity" class="form-control" onchange="common.getChinaArea(this.value,'','custDistrict')">
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
		                </div>
				    </div>
				    <div class="form-group">
		                <label class="col-xs-2 control-label">现居住地址</label>
		                <div class="col-xs-10">
		                    <input id="custAddress" name="custAddress" class="form-control" type="text" maxlength="80" value=""/>
		                </div>
				    </div>
				    <div class="form-group">
		            	<div class='col-xs-12 col-xs-offset-2'>
		            		<button class='btn btn-primary' type='submit'><i class='fa fa-save fa-fw'></i><span id='span_button'> 保存</span></button>
		            		<button class='btn btn-primary' type='button' id='backButton'><i class='fa fa-share-square-o fa-fw'></i> 返回</button>
		            	</div>
				    </div>
				</div>
			</div>
		</form>
	</div>
</div>
</body>
</html>
