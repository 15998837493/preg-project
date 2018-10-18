<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>元素库</title>
</head>
<script type="text/javascript">
	var checkedData;// 选中项信息
	var checkedRow;// 选中行信息
	var nutrientTable;// table，注意命名不要重复

	//配置datatable
	var tableOptions = {
		id : "nutrientTable",
		form : "queryForm",
		isClearChecked: false,// 重绘后是否清空选中状态
		columns : [
				{
					"data" : null,
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return "<input type='checkbox' class='"+data.userName+"' name='leafBox' value='"+data.userId+"' />";
					}
				},
				{
					"data" : "userId",
					"sClass" : "text-center"/* ,
		  			"render":  function (data, type, row, meta) {
		          		return meta.row+1;
		          	} */					
				},
				{
					"data" : "userName",
					"sClass" : "text-center"
				},
				{
					"data" : "userSex",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("SEX",data);
					}
				},
				{
					"data" : "userBirthday",
					"sClass" : "text-center"
				},
				{
					"data" : "roleName",
					"sClass" : "text-center"
				},
				{
					"data" : "userPhone",
					"sClass" : "text-center"
				},
				{
					"data" : "userIcard",
					"sClass" : "text-left"
				} ],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			$("#saveSign").remove();//去除保存状态
			var flag = $("input[value="+data.userId+"]").is(':checked');//判断当前点中行的checkbox是否选中，true为选中，false为没选中
/* 			var boxFlag = true;//判断10个checkbox是否全部选中，true为全部选中，false为没有全部选中
			var box = $("input[name='leafBox']");//每页的10个checkbox		 */	
			if(flag) {//选中则记录ID
				$("#selectedBoxUsersId").append("<p name='"+data.userName+"' id='"+data.userId+"'></p>");
			}else {//取消则删除记录的ID
				$("#"+data.userId).remove();
			}
/* 			$.each(box,function(key,value) {
				if($(this).is(":checked")==false){
					boxFlag = false;
				}
			});
			if(boxFlag) {//如果全部选中，全选框就要打勾
				$("#rootBox").attr("checked",true);
			}else {//如果没有全选中，全选框的勾就要消除
				$("#rootBox").attr("checked",false);
			} */
		},
		condition : "nutrientCondition"
	};
</script>
<script type="text/javascript" charset="utf-8"
	src="${common.basepath}/page/system/doctor/doctor_view.js"></script>
<script type="text/javascript">
	$().ready(function() {
		nutrientTable = datatable.table(tableOptions);
		//分页点击事件
		$(".pagination").find("a").live("click",function() {
			if($("#saveSign").val()=="saveSign") {
				$("input[name='leafBox']").attr("checked",false);
			}			
		});
		$("button[name='operateButton']").click(function() {
			if(this.id == 'searchButton') {
				//nutrientTable = datatable.table(tableOptions);
				datatable.search(nutrientTable, tableOptions);
				$("#selectedBoxUsersId").find("p").remove();//清空所有checkbox的储存标签
			}
			if (this.id == 'addButton') {// 添加/编辑
				validator = $("#addForm").validate(Options);
				//加入必填项提示
				common.requiredHint("addForm", Options);
				var sons = $("#selectedBoxUsersId").children();//选择checkbox
				if(sons.length>0) {
					common.clearForm("addForm");
					var id="";
					var name="";
					$.each(sons,function(key,value){
						id+=$(this).attr("id")+",";
						name+=$(this).attr("name")+"、";
					});
					id=id.substring(0,id.length-1);
					name=name.substring(0,name.length-1);
					$("#userId").val(id);
					$("#userName").val(name);
					if(name.length>30) {
						name = name.substring(0,33)+"...";
						$("#showBut").css("display","block");
						$("#hideBut").css("display","none");
					}else {
						$("#showBut").css("display","none");
						$("#hideBut").css("display","none");
					}
					$("#showUserName").text(name);
					if(sons.length==1) {
						var url = URL.get("System.DOCTOR_MODEL_VIEW");
						var params = "userId="+sons.first().attr("id");
						ajax.post(url, params, dataType.json, function(data){
							var value = data.data;
							if(value.length == 1) {//只可能返回一条或0条,此处用于回显，只有选择一个用户并且这一个用户在数据库里有数据才会回显
							$("#scheduleMaxPerson").val(value[0].scheduleMaxPerson);
							if(value[0].scheduleWeek.indexOf(",")>-1) {//有多个工作日
								var weeks = value[0].scheduleWeek.split(",");
								$.each(weeks,function(key,value) {
									$("#addForm input[value='"+value+"']").attr("checked",true);
								});
							}else {//只有一个工作日
								$("#addForm input[value='"+value[0].scheduleWeek+"']").attr("checked",true);
							}
							}
						});
					}
					$("#addModal").modal("show");
				}else {
					layer.alert('请选择操作的数据');
				}
			}
		});
		$("#rootBox").click(function() {	
			if(this.checked) {
				$.each($("input[name='leafBox']"), function(key, value) {
					if($("#"+$(this).val()).val()==undefined) {//只有不重复，才添加
						$("#selectedBoxUsersId").append("<p name='"+$(this).attr("class")+"' id='"+$(this).val()+"'></p>");
					}
				});	
			}else {
				$.each($("input[name='leafBox']"), function(key, value) {
					$("#"+$(this).val()).remove();
				});					
			}
			$("input[name='leafBox']").attr("checked",this.checked);
		});
		$("span[name='boxContent']").click(function(){
			var box = $(this).siblings("input[name='scheduleWeek']");
			if(box.is(":checked")) {
				box.attr("checked",false);
			}else {
				box.attr("checked",true);
			}
		});
	});
	function show() {
		var name = $("#showUserName");	
		name.text($("#userName").val());
		name.css("height","auto");
		$("#showBut").css("display","none");
		$("#hideBut").css("display","block");
	}
	function hide() {
		var name = $("#showUserName");	
		name.text($("#userName").val().substring(0,33)+"...");
		name.css("height","35px");
		$("#showBut").css("display","block");
		$("#hideBut").css("display","none");
	}
</script>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="nutrientCondition">
				<form id="queryForm" name="queryForm" action="${common.basepath}/${applicationScope.URL.System.DOCTOR_VIEW}" method="post" class="form-horizontal">
					<div class="form-inline">
						<input type='text' name="userName" class='form-control' placeholder='编号/名称/身份证号' />
						<button type="button" id="searchButton" name="operateButton"
							class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button> 
						<div class="vertical-line"></div>
						<button type="button" id="addButton" name="operateButton" class="btn btn-default">
							<i class='fa fa-edit fa-fw'></i> 配置
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="table-responsive">
		<table id="nutrientTable" class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class="text-center"><!-- <input type="checkbox" id="rootBox"/> -->选择</th>
					<th class="text-center">编号</th>
					<th class="text-center">姓名</th>
					<th class="text-center">性别</th>
					<th class="text-center">出生日期</th>
					<th class="text-center">职位</th>
					<th class="text-center">手机号</th>
					<th class="text-center">身份证号</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<!-- 选中数据得所有id -->
	<div id="selectedBoxUsersId" style="display:none"></div>

	<!-- 模态框（Modal） -->
	<!-- ADD_NUTRIENT=/page/master/plan/add_nutrient.action -->
	<form id="addForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.System.DOCTOR_ADD}" method="post">
		<div class="modal fade bs-example-modal" id="addModal">
			<div class="modal-dialog modal-lg" style="margin-top: 80px;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue ">
							<div class="panel-heading text-left">
								<!-- <i class="fa fa-plus fa-fw"></i> -->出诊排班配置
								<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
							</div>						
							<div class="row row-top">
								<div class="col-xs-12 ">
									<div class="form-group">
									<input type="hidden" id="userId" name="userId"/>
									<input id="editFormType" name="editFormType" class="form-control" type="hidden" />
										<label class="col-xs-3 control-label">选择用户</label>
										<div class="col-xs-7">										
											<!-- <textarea readonly="readonly" class="form-control" id="userName" name="userName" rows="3"  style="overflow: hidden;background-color: white;"></textarea> -->
											<input type="hidden" id="userName" name="userName"/>
											<div id="showUserName" class="form-control" style="border:1px solid #FF7200;height:35px;padding:5px;" contenteditable="false"></div>
										</div>
										<a style="float:right;text-decoration:none;color:#333333;margin-right:90px;margin-top:6px;" id="showBut" onclick="show();">全部显示</a>
										<a style="float:right;text-decoration:none;color:#333333;margin-right:120px;margin-top:6px;display:none;" id="hideBut" onclick="hide();">收起</a>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">每日最大接诊人数</label>
										<div class="col-xs-7">
											<input onblur="validNum(this.value);" onkeyup="validNum(this.value);" id="scheduleMaxPerson" name="scheduleMaxPerson" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">工作日安排</label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周一上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周一上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周一下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周一下午</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label"></label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周二上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周二上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周二下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周二下午</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label"></label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周三上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周三上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周三下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周三下午</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label"></label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周四上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周四上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周四下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周四下午</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label"></label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周五上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周五上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周五下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周五下午</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label"></label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周六上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周六上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周六下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周六下午</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label"></label>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周日上午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周日上午</span>
										</div>
										<div class="col-xs-2" style="margin-top:5px;">
											<input type="checkbox" value="周日下午" name="scheduleWeek"/> <span name="boxContent" style="cursor: pointer;">周日下午</span>
										</div>
									</div>																																				
								</div>
							</div>
						</div>
<!-- 							<div class="panel-body padding-zero" style="padding: 0px;">
							<div class="col-xs-2 col-xs-offset-8 no-right">
								<button type="submit" class="btn btn-primary btn-block">保存</button>
							</div>
							<div class="col-xs-2 no-right">
								<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
							</div>
						</div> -->
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i>保存</button>
					</div>
				</div>						
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</form>
</body>
</html>
