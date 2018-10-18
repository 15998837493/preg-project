<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>食物模版一览页</title>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/plan/diet_template_view.js"></script>
</head>
<body>
<input type="hidden" id="dietId" name="dietId" />
<div class="row">
	<div class="panel-body">
		<div id="dtableCondition">
			<form id="dtableQueryForm" action="${common.basepath}/${applicationScope.URL.Master.PLAN_DIETTEMPLATE_QUERY}" method="post"
				class="form-horizontal">
				<div class="form-inline">
					<input type='text' id='searchText' name="dietName" class='form-control' placeholder='请输入检索内容' />
					<button id="search" type="button" name="operateBtn" class="btn btn-default">
						<i class="fa fa-search fa-fw"></i>查询
					</button>
					<div class="vertical-line"></div>
					<button id="add" type="button" name="operateBtn" class="btn btn-default">
						<i class="fa fa-plus fa-fw"></i> 增加
					</button>
					<button id="update" type="button" name="operateBtn" class="btn btn-default">
						<i class='fa fa-edit fa-fw'></i> 编辑
					</button>
					<button id="remove" type="button" name="operateBtn" class="btn btn-default">
						<i class='fa fa-remove fa-fw'></i> 删除
					</button>
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
				<th class="text-center">序号</th>
				<th class="text-center">模板名称</th>
				<th class="text-center">孕期阶段</th>
				<th class="text-center">食谱明细</th>
				<th class="text-center">类型</th>
				<th class="text-center">创建人</th>
			</tr>
		</thead>
	</table>
</div>

<form id="editForm" class="form-horizontal" method="post">
<div id="editModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class='fa fa-edit fa-fw'></i>编辑食谱模板
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="row">
							<input id="editFormType" name="editFormType" class="form-control" type="hidden" />
								<input id="dietId" name="dietId" class="form-control" type="hidden" />
								<div class="form-group">
									<div>
										<label class="col-xs-4 control-label">模板名称</label>
										<div class="col-xs-6">
											<input id="dietName" name="dietName" class="form-control" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div>
										<label class="col-xs-4 control-label">模板类型</label>
										<div class="col-xs-6">
											<select id="dietType" name="dietType" class="form-control"></select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div>
										<label class="col-xs-4 control-label">孕期阶段</label>
										<div class="col-xs-6">
											<select id="pregnantStage" name="pregnantStage" class="form-control"></select>
										</div>
									</div>
								</div>
								<div class="form-group">
									<div>
										<label class="col-xs-4 control-label">模板介绍</label>
										<div class="col-xs-6">
											<textarea id="dietDesc" name="dietDesc" class="form-control" rows="3"></textarea>
										</div>
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
		</div>
	</div>
</div>
</form>
</body>
</html>