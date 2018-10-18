<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/question/question_view.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/question/problem.js"></script>
<title>问卷管理</title>
</head>
<body>
<div class="row">
	<div class="panel-body">
		<div id="masQuestionCondition">
			<form id="masQuestionQueryForm" action="${common.basepath }/${applicationScope.URL.Question.QUERY_QUESTION}" method="post"
				class="form-horizontal">
				<div class="form-inline">
					<input type='text' class='form-control' placeholder='请输入检索内容' />
					<button type="button" name="operateButton" id="search" class="btn btn-default">
						<i class="fa fa-search fa-fw"></i>查询
					</button>
					<div class="vertical-line"></div>
					<button type="button" name="operateButton" id="add" class="btn btn-default">
						<i class="fa fa-plus fa-fw"></i> 增加
					</button>
					<button type="button" name="operateButton" id="edit" class="btn btn-default">
						<i class='fa fa-edit fa-fw'></i> 编辑
					</button>
					<button type="button" name="operateButton" id="remove" class="btn btn-default">
						<i class='fa fa-remove fa-fw'></i> 删除
					</button>
					<button type="button" name="operateButton" id="config" class="btn btn-default">
						<i class="fa fa-cogs"></i> 配置
					</button>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="table-responsive">
	<table id="masQuestionTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">问卷编号</th>
				<th class="text-center">问卷名称</th>
				<th class="text-center">问卷类型</th>
				<th class="text-center">是否启用</th>
			</tr>
		</thead>
	</table>
</div>
<form id="hiddenForm" name="hiddenForm" action="" method="post">
	<input id="id" type="hidden" name="id" />
</form>

<div class="modal fade bs-example-modal" id="editModal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id="editForm" class="form-horizontal" method="post">
				<input type="hidden" id="questionId" name="questionId" />
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">
							编辑问卷信息<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
						</div>
						<div class="panel-body">
							<div class="col-xs-10 col-xs-offset-1 row-top">
								<div class="form-group">
									<label class="col-xs-2 control-label">问卷名称</label>
									<div class="col-xs-10">
										<input id="questionName" name="questionName" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-2 control-label">是否启用</label>
									<div class="col-xs-10">
										<select id="questionState" name="questionState" class="form-control">
											<option value="">==请选择是否启用==</option>
											<option value="0">未启用</option>
											<option value="1">启用</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-2 control-label">问卷类型</label>
									<div class="col-xs-10">
										<select id="questionType" name="questionType" class="form-control"></select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-2 control-label">描述</label>
									<div class="col-xs-10">
										<textarea id="questionDesc" name="questionDesc" class="form-control" maxlength="200" rows="3"></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body text-right" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right">
							<button type="submit" class="btn btn-primary btn-block">确定</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>