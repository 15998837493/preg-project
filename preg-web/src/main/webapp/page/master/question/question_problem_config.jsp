<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<%@ include file="/common/plugins/bootstrap-table/bootstrap-table.jsp"%>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/question/question_problem_config.js"></script>
<title>问题库管理</title>
</head>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="qstCofigCondition">
				<form id="qstCofigQueryForm" action="${common.basepath}/${applicationScope.URL.Question.QUERY_QUESTION_PROBLEM}" method="post"
					class="form-horizontal">
					<div class="form-inline">
						<input type="hidden" id="questionId" name="questionId" value="${questionId}" />
						<input type='text' class='form-control' placeholder='请输入检索内容' />
					    <button name="operateButton" id="search" type="button" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button name="operateButton" id="add" type="button" class="btn btn-default">
							<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button name="operateButton" id="edit" type="button" class="btn btn-default">
							<i class="fa fa-edit fa-fw"></i> 编辑
						</button>
						<button name="operateButton" id="remove" type="button" class="btn btn-default">
							<i class="fa fa-remove fa-fw"></i> 删除
						</button>
						<button name="operateButton" id="config" type="button" class="btn btn-default">
							<i class="fa fa-cogs"></i> 配置父节点
						</button>
						<button name="operateButton" id="problem" type="button" class="btn btn-default">
							<i class="fa fa-edit fa-fw"></i> 题库
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="table-responsive">
		<table class="table table-bordered table-hover" id="qstCofigTable">
			<thead>
				<tr class="active">
					<th class="text-center">选择</th>
					<th class="text-center">序号</th>
					<th class="text-center">问题</th>
					<th class="text-center">类型</th>
					<th class="text-center">适用性别</th>
					<th class="text-center">必答题</th>
					<th class="text-center">提示</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 问题移动表单 -->		
	<form id="editForm" name="editForm" action="" method="post">
		<input id="editFormProblemId" type="hidden"  class="form-control" name="editProblemId" /> 
		<input id="moveFormFlag" type="hidden" name="moveFlag" />
	</form>
	<div id="itemModel" class="modal fade">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">
							<i class="fa fa-search fa-fw"></i>请选择系统项目
						</div>
						<div class="panel-body">
							<div class="col-xs-12" id="qstModalCondition">
								<form id="qstModalQueryForm" action="${common.basepath }/${applicationScope.URL.Question.QUERY_PROBLEM_MODEL}" method="post"
									class="form-horizontal">
									<div class="col-xs-4">
										<input type='text' class='form-control' placeholder='请输入检索内容' />
									</div>
								</form>
							</div>
						</div>
						<div class="table-responsive">
							<table id="qstModalTable" class="table table-bordered table-hover">
								<thead>
									<tr class="active">
										<th class="text-center">问题名称</th>
										<th class="text-center">问题类型</th>
										<th class="text-center">性别</th>
										<th class="text-center">选择</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 弹窗添加、编辑 -->
	<div id="configParentModel" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg"  >
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue margin-zero form-horizontal">
						<div class="panel-heading text-center"> <i class="fa fa-edit fa-fw"></i>问题属性<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="row">
		                    <div class="col-xs-9 col-xs-offset-1 row-top">
			                    <div class="form-group">
					                <label class="col-xs-3 control-label">当前题目</label>
					                <div class="col-xs-9">
					                    <input id="ProblemContentModel" name="ProblemContent" class="form-control" readonly="readonly" type="text"  />
					                    <input id="problemIdmodel" name="problemId" type="hidden"  />
					                </div>
					            </div>
								<div class="form-group">
					                <label class="col-xs-3 control-label">关联题目</label>
					                <div class="col-xs-9">
					                 <select id="problemList" class="form-control">
					                 	<option value=''>==请选择==</option>
					                 </select>
					                </div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-3 control-label">关联选项</label>
					            	<div class="col-xs-9" id="checkOption"></div>  
					            </div>
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero" style="padding-top: 5px;">
						<div class="col-xs-2 col-xs-offset-10 no-right">
							<button class="btn btn-primary btn-block" onclick="saveCheckBox()">保存</button>
						</div>
					</div> 
				</div>			
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
	<!-- <div id="configParentModela" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title text-danger">配置父节点</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-12">
							<div class="col-xs-12  row-top">
								<div class="form-group">
					                <label class="col-xs-2 control-label">当前题目</label>
					                <div class="col-xs-10">
					                    <input id="ProblemContentModel" name="ProblemContent" class="form-control" readonly="readonly" type="text"  />
					                    <input id="problemIdmodel" name="problemId" type="hidden"  />
					                </div>
					            </div>
								<div class="form-group">
					                <label class="col-xs-2 control-label">关联题目</label>
					                <div class="col-xs-10">
					                 <select id="problemList" class="form-control"></select>
					                </div>
					            </div>
					            <div class="text-muted">请勾选问题选项：</div>
					            <div id="checkOption"></div>
							</div>
						</div>
					</div>
				</div>	
				<div class="modal-footer">
					<button class="btn btn-default"  onclick="saveCheckBox()">保存</button>
					<button class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div> -->
	<!-- 弹窗添加、编辑 -->
	<div id="editProblemModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg"  style="width: 1200px;" >
			<div class="modal-content">
				<div class="modal-body">
					<form id="editProblemForm" name="editInspectForm" action="" class="form-horizontal" method="post">
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
						      <table class="table table-striped table-hover" id="reportProblemTable"></table>
						      <div class="panel-body padding-zero" style="padding: 5px;">
								<div class="col-xs-2 col-xs-offset-10 no-right">
									<button class="btn btn-primary btn-block" onclick="addRowbtn('reportProblemTable')"><i class="fa fa-plus fa-fw"></i> 增加选项</button>
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
						      <table class="table table-striped table-hover" id="fillProblemTable"></table>
							  <div class="panel-body padding-zero" style="padding: 5px;">
								<div class="col-xs-2 col-xs-offset-10 no-right">
									<button class="btn btn-primary btn-block" onclick="addRowbtn('fillProblemTable')"><i class="fa fa-plus fa-fw"></i> 增加选项</button>
								</div>
							  </div> 
							</div>
						</div>
					</div>
				</div>			
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->

	<form id="editOptionForm" name="editOptionForm" action="" method="post">
		<input id="rowIndex" name="rowIndex" type="hidden" /> <input
			id="editProblemId" type="hidden" name="problemId" /> <input
			id="moveFlag" type="hidden" name="moveFlag" /> <input
			id="problemOptionId" type="hidden" name="problemOptionId" /> <input
			id="optionContent" type="hidden" name="optionContent" /> <input
			id="optionSex" type="hidden" name="optionSex" /> <input
			id="optionOrder" type="hidden" name="optionOrder" />
	</form>
</body>
</html>