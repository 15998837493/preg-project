<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<%@ include file="/common/plugins/bootstrap-table/bootstrap-table.jsp" %>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/question/problem.js"></script>
<title>问题库管理</title>
</head>
<script type="text/javascript">
$().ready(function() {
    /*********************************添加问题页面**************************************************************/
	//初始化下拉列表
	common.initCodeSelect("PROBLEMCATEGORY", "PROBLEMCATEGORY","problemCategory");
	common.initCodeSelect("OPTIONVALIDATE","OPTIONVALIDATE","optionValidate");
	//问题种类绑定事件
	problemTypeBindChange();
    //加入必填项提示
    validator= $("#editForm").validate(intakeTemplateOptions);
    common.requiredHint("editForm", intakeTemplateOptions);
    //初始化可编辑问题选项/问题内容表格
    common.initEditTable(reportOption);
    common.initEditTable(fillOption);
    //绑定modal关闭保存事件
    editModalOnhHideBs();
	/*********************************问题页面********************************************************/
    masProblemTable = datatable.table(masProblemTableOptions);
    $("button[name='operateBtn']").click(function(){
		var id = $("#id").val();
		if(this.id == "save"){
			//redirectPage("init_add");
			//设置按钮状态
			operateType = "add";
			//清空表单内容
			common.clearForm("editForm");
			//清空选项表格
			$("#reportTable").bootstrapTable('removeAll');
	    	$("#fillTable").bootstrapTable('removeAll');
	    	//设置readio选中
	    	$("[name='problemRequired']")[0].checked = true;
	    	$("#fill").hide();
			$("#choice").show();
	    	//打开modal
			$("#editModal").modal("show");
		}
		if(this.id == "update" && common.isChoose(id)){
			//common.pageForward(URL.get("Question.UPDATE_INIT_PROBLEM") +"?id="+id);
			//设置按钮状态
			operateType = "update";
		    if(masProblemData.problemType == 3){
				$("#fill").show();
				$("#choice").hide();
				//placeholder("problemContent","输入'#'后失去光标编辑默认内容与验证！");
		    }else{
				$("#fill").hide();
				$("#choice").show();	
				//placeholder("problemContent","输入标题！");
		    }
			//清空表单内容
			common.clearForm("editForm");
			//清空选项表格
			$("#reportTable").bootstrapTable('removeAll');
	    	$("#fillTable").bootstrapTable('removeAll'); 
	    	//给表单赋值
			$("#editForm").jsonToForm(masProblemData);
			//设置readio选中
			$("[name='problemRequired']").each(function(index,obj){
				if(obj.value == masProblemData.problemRequired){
					obj.checked = true;
				}
			});
			//设置适合性别
			$("[name='problemSex']").val(masProblemData.problemSex);
			//给选项表格赋值
			initData(id);
			//打开modal
			$("#editModal").modal("show");
		}
		if(this.id == "delete" && common.isChoose(id)){
			removeClick(id);
		}
		if(this.id == "search"){
			masProblemTable = datatable.table(masProblemTableOptions);
		}
	});
});
</script>
<body>
<input id="id" type="hidden" name="id" />
<div class="row">
	<div class="panel-body">
		<div id="masProblemCondition">
			<form id="masProblemQueryForm" action="${common.basepath}/${applicationScope.URL.Question.QUERY_PROBLEM}" method="post"
				class="form-horizontal">
				<div class="form-inline">
					<input type='text' id='searchText' name="searchText" class='form-control' placeholder='请输入检索内容' />
					<button id="search" type="button" name="operateBtn" class="btn btn-default">
						<i class="fa fa-search fa-fw"></i>查询
					</button>
					<div class="vertical-line"></div>
					<button id="save" type="button" name="operateBtn" class="btn btn-default">
						<i class="fa fa-plus fa-fw"></i> 增加
					</button>
					<button id="update" type="button" name="operateBtn" class="btn btn-default">
						<i class='fa fa-edit fa-fw'></i> 编辑
					</button>
					<button id="delete" type="button" name="operateBtn" class="btn btn-default">
						<i class='fa fa-remove fa-fw'></i> 删除
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-bordered table-hover" id="masProblemTable">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">问题编号</th>
				<th class="text-center">问题名称</th>
				<th class="text-center">问题类型</th>
				<th class="text-center">分类</th>
		     	<th class="text-center">性别</th>
			</tr>
		</thead>
	</table>
</div>
<!-- 弹窗添加、编辑 -->
<div id="editModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg"  style="width: 1200px;" >
		<div class="modal-content">
			<div class="modal-body">
				<form id="editForm" name="editInspectForm" action="" class="form-horizontal" method="post">
					<input class="form-control" type="hidden" name="problemId" id="problemId"/>
					<div class="panel panel-lightblue margin-zero">
						<div class="panel-heading text-center"> <i class="fa fa-edit fa-fw"></i>问题属性<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="row">
		                    <div class="col-xs-9 col-xs-offset-1 row-top">
			                   <div class="form-group">
			                       <label class="col-xs-2 control-label">问题属性</label>
			                       <div class="col-xs-4">
			                           <select id="problemType"  name="problemType" class="form-control">
											<option value="1">单选题</option>
											<option value="2">多选题</option>
											<option value="3">单项填空题</option>
											<option value="4">多项填空题</option>
										</select>
			                       </div>
			                       <div>
			                           <label class="col-xs-2 control-label">必答题</label>
			                           <div class="col-xs-4">
			                           	   <div class="col-xs-6 control-label">
			                           	   		<input  type="radio" name="problemRequired" value="1" checked="checked" /> 是
			                           	   </div>
				                           <div class="col-xs-6 control-label">
				                           		<input  type="radio" name="problemRequired" value="0" /> 否         
				                           </div>
			                           </div>
			                       </div>
			                   </div>
			                   <div class="form-group">
			                       <div>
			                           <label class="col-xs-2 control-label">适用性别</label>
			                           <div class="col-xs-4">
			                               <select name="problemSex" class="form-control">
												<option value="">==请选择适用性别==</option>
													<option value="all">不限</option>
													<option value="female">女性</option>
													<option value="male">男性</option>
											</select>
			                           </div>
			                       </div>
			                       <div>
			                           <label class="col-xs-2 control-label">问题分类</label>
			                           <div class="col-xs-4">
			                               <select id="problemCategory" name="problemCategory" class="form-control"></select>
			                           </div>
			                       </div>
			                    </div> 
			                    <div class="form-group">
									<label class="col-xs-2  control-label">标题/内容</label>
									<div class="col-xs-10">
										<textarea id="problemContent" name="problemContent" class="form-control" maxlength="200"></textarea>
										<!-- <input id="optionVos" name="optionVos" class="form-control" /> -->
									</div>                                    
			                    </div>                             
			                    <div class="form-group">
									<label class="col-xs-2  control-label">提示</label>
									<div class="col-xs-10">
										<textarea name="problemHint" class="form-control" maxlength="200"></textarea>
									</div>                                    
			                    </div>                             
							</div>
						</div>
					</div>
				</form>
				<!--  选项属性-->
				<div id="choice" class="panel panel-lightblue" style="display: none;">
					<div class="panel-heading text-center"><i class="fa fa-edit fa-fw"></i>选项属性</div>
				   	<div class="row">
				        <div class="col-xs-12" id="tab2">
					      <table class="table table-striped table-hover" id="reportTable"></table>
					      <div class="panel-body padding-zero" style="padding: 5px;">
							<div class="col-xs-2 col-xs-offset-10 no-right">
								<button class="btn btn-primary btn-block" onclick="addRowbtn('reportTable')"><i class="fa fa-plus fa-fw"></i> 增加选项</button>
							</div>
						  </div> 
					    </div>
					</div>
				</div>
				<!--  填空属性-->
				<div id="fill" class="panel panel-lightblue" >
					<div class="panel-heading text-center"><i class="fa fa-edit fa-fw"></i>填空属性</div>
				    <div class="row">
				    	<div class="col-xs-12">
					      <table class="table table-striped table-hover" id="fillTable"></table>
						  <div class="panel-body padding-zero" style="padding: 5px;">
							<div class="col-xs-2 col-xs-offset-10 no-right">
								<button class="btn btn-primary btn-block" onclick="addRowbtn('fillTable')"><i class="fa fa-plus fa-fw"></i> 增加选项</button>
							</div>
						  </div> 
						</div>
					</div>
				</div>
				<!-- 确认是否保存   -->
				<!-- <div class="panel-body padding-zero" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" onclick="saveData()"><i class="fa fa-plus fa-fw"></i> 保存</button>
					</div>
				</div> -->
			</div>			
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>