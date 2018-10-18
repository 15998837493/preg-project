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
		columns : [
				{
					"data" : null,
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return "<input type='radio' name='tableRadio' value='"+data.id+"' />";
					}
				},
				{
					"data" : "nutrientId",
					"sClass" : "text-center"
				},
				{
					"data" : "nutrientNameEnglish",
					"sClass" : "text-center hide"
				},
				{
					"data" : "nutrientName",
					"sClass" : "text-center"
				},
				{
					"data" : "nutrientType",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return $("#nutrientType").find(
								"option[value='" + data + "']").text();
					}
				},
				{
					"data" : "nutrientEvalOne",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return $("#nutrientEvalOne").find(
								"option[value='" + data + "']").text();
					}
				},
				{
					"data" : "nutrientEvalTwo",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return $("#nutrientEvalTwo").find(
								"option[value='" + data + "']").text();
					}
				},
				{
					"data" : "nutrientUnit",
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return $("#nutrientUnit").find(
								"option[value='" + data + "']").text();
					}
				} ],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			$("#id").val(data.id);
		},
		condition : "nutrientCondition"
	};
</script>
<script type="text/javascript" charset="utf-8"
	src="${common.basepath}/page/master/basic/nutrient_view.js"></script>
<script type="text/javascript">
	$().ready(function() {
		nutrientTable = datatable.table(tableOptions);
		$("button[name='operateButton']").click(function() {
			var id = $("#id").val();
			if (this.id == 'addButton') {//添加
				$("#addForm").validate(addOptions);
				//加入必填项提示
				common.requiredHint("addForm", addOptions);

				common.clearForm("addForm");
				$("#addModal").modal("show");
			}
			if (this.id == 'editButton' && isChoose(id)) {//编辑
				$("#updateForm").validate(updateOptions);
				//加入必填项提示
				common.requiredHint("updateForm", updateOptions);
				$.each(checkedData, function(key, value) {
					$("#" + key).val(value);
				});
				$("#updateModal").modal("show");
			}
			if (this.id == 'removeButton' && isChoose(id)) {//删除
				removeClick(id);
			}
			if (this.id == "searchButton") {//查询
				//nutrientTable = datatable.table(tableOptions);
				datatable.search(nutrientTable, tableOptions);
			}
		});

	});
</script>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="nutrientCondition">
				<form id="queryForm" name="queryForm" action="${common.basepath}/${applicationScope.URL.Master.QUERY_NUTRIENT}" method="post" class="form-horizontal">
					<div class="form-inline">
						<input type='text' class='form-control' placeholder='请输入检索内容' />
						<button type="button" id="searchButton" name="operateButton"
							class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button type="button" id="addButton" name="operateButton"
							class="btn btn-default">
							<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button type="button" id="editButton" name="operateButton"
							class="btn btn-default">
							<i class='fa fa-edit fa-fw'></i> 编辑
						</button>
						<button type="button" id="removeButton" name="operateButton"
							class="btn btn-default">
							<i class='fa fa-remove fa-fw'></i> 删除
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
					<th class="text-center">选择</th>
					<th class="text-center">代码</th>
					<th class="text-center" style="display: none;">英文名称</th>
					<th class="text-center">名称</th>
					<th class="text-center">类别</th>
					<th class="text-center">膳食评价</th>
					<th class="text-center">营养补充剂评价</th>
					<th class="text-center">单位</th>
				</tr>
			</thead>
		</table>
	</div>


	<!-- 增加模态框（Modal） -->
	<!-- ADD_NUTRIENT=/page/master/plan/add_nutrient.action -->
	<form id="addForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.ADD_NUTRIENT}" method="post">
		<div class="modal fade bs-example-modal" id="addModal">
			<div class="modal-dialog modal-lg" style="margin-top: 80px;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue ">
							<div class="panel-heading text-left"><i class="fa fa-plus fa-fw"></i>增加元素<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
							<div class="row row-top">
								<div class="col-xs-12 ">
									<div class="form-group">
										<label class="col-xs-3 control-label">代码</label>
										<div class="col-xs-7">
											<input name="nutrientId" class="form-control"
												placeholder="请输入代码" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">英文名称</label>
										<div class="col-xs-7">
											<input name="nutrientNameEnglish" class="form-control"
												placeholder="请输入英文名称" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">名称</label>
										<div class="col-xs-7">
											<input name="nutrientName" class="form-control"
												placeholder="请输入名称" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">类型</label>
										<div class="col-xs-7">
											<select name="nutrientType" class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${nutrientTypes!=null}">
													<c:forEach items="${nutrientTypes}" var="nutrientType">
														<option value="${nutrientType.codeValue }">${nutrientType.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">膳食评价</label>
										<div class="col-xs-7">
											<select name="nutrientEvalOne" class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${torfs!=null}">
													<c:forEach items="${torfs}" var="torf">
														<option value="${torf.codeValue }">${torf.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">营养补充剂评价</label>
										<div class="col-xs-7">
											<select name="nutrientEvalTwo" class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${torfs!=null}">
													<c:forEach items="${torfs}" var="torf">
														<option value="${torf.codeValue }">${torf.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">单位</label>
										<div class="col-xs-7">
											<select name="nutrientUnit" class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${productUnits!=null}">
													<c:forEach items="${productUnits}" var="productUnit">
														<option value="${productUnit.codeValue }">${productUnit.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
							<div class="panel-body padding-zero" style="padding: 0px;">
							<div class="col-xs-2 col-xs-offset-10 no-right">
								<button type="submit" class="btn btn-primary btn-block">确定</button>
							</div>
						</div>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</form>

	<!-- 修改模态框（Modal） -->
	<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.UPDATE_NUTRIENT}" method="post">
		<div class="modal fade bs-example-modal" id="updateModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<input type="hidden" name="id" id="id">
					<div class="modal-body">
						<div class="panel panel-lightblue ">
							<div class="panel-heading text-left"><i class='fa fa-edit fa-fw'></i>修改元素库<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
							<div class="row row-top">
								<div class="col-xs-12 col-xs-offset-1">
									<div class="form-group">
										<label class="col-xs-3 control-label">代码</label>
										<div class="col-xs-7">
											<input id="nutrientId" name="nutrientId" class="form-control"
												readonly />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">英文名称</label>
										<div class="col-xs-7">
											<input id="nutrientNameEnglish" name="nutrientNameEnglish" class="form-control"
												placeholder="请输入英文名称" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">名称</label>
										<div class="col-xs-7">
											<input id="nutrientName" name="nutrientName"
												class="form-control" placeholder="请输入名称" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">类型</label>
										<div class="col-xs-7">
											<select id="nutrientType" name="nutrientType"
												class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${nutrientTypes!=null}">
													<c:forEach items="${nutrientTypes}" var="nutrientType">
														<option value="${nutrientType.codeValue }">${nutrientType.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">膳食评价</label>
										<div class="col-xs-7">
											<select id="nutrientEvalOne" name="nutrientEvalOne"
												class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${torfs!=null}">
													<c:forEach items="${torfs}" var="torf">
														<option value="${torf.codeValue }">${torf.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">营养补充剂评价</label>
										<div class="col-xs-7">
											<select id="nutrientEvalTwo" name="nutrientEvalTwo"
												class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${torfs!=null}">
													<c:forEach items="${torfs}" var="torf">
														<option value="${torf.codeValue }">${torf.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">单位</label>
										<div class="col-xs-7">
											<select id="nutrientUnit" name="nutrientUnit"
												class="form-control">
												<option value="">==请选择==</option>
												<c:if test="${productUnits!=null}">
													<c:forEach items="${productUnits}" var="productUnit">
														<option value="${productUnit.codeValue }">${productUnit.codeName }</option>
													</c:forEach>
												</c:if>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-body padding-zero" style="padding: 0px;">
							<div class="col-xs-2 col-xs-offset-10 no-right">
								<button type="submit" class="btn btn-primary btn-block">确定</button>
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
