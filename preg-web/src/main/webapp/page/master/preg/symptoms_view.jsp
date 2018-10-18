<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>功能症状</title>
</head>
<script type="text/javascript">
	var checkedData;// 选中项信息
	var checkedRow;// 选中行信息
	var symptomsTable;// table，注意命名不要重复

	//配置datatable
	var tableOptions = {
		id : "symptomsTable",
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
					"data" : "sympCode",
					"sClass" : "text-center"
				},
				{
					"data" : "sympPart",
					"sClass" : "text-center",
				 	"render": function(data, type, row, meta) {
				 		return CODE.transCode("symp_part",data);
					}
				},
				{
					"data" : "sympCategory",
					"sClass" : "text-center",
					"render": function(data, type, row, meta) {
				 		return CODE.transCode("symp_category",data);
					}
				},
				{
					"data" : "sympName",
					"sClass" : "text-center"
				}],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			$("#id").val(data.id);
			$("#sympCategoryUpdate").val(data.sympCategory);
			$("#sympPartUpdate").val(data.sympPart);
			$("#sympSexUpdate").val(data.sympSex);
			$("#sympFunctionUpdate").val(data.sympFunction);
			$("#sympIsScoreUpdate").val(data.sympIsScore);
		},
		condition : "SymptomsCondition"
	};
</script>
<script type="text/javascript" charset="utf-8"
	src="${common.basepath}/page/master/health/sysmptoms_view.js"></script>
<script type="text/javascript">
	$().ready(function() {
		//初始化下拉选表单(添加弹出框)
		common.initCodeSelect("SYMP_CATEGORY", "SYMP_CATEGORY","sympCategory","","请选择器官类别");
		common.initCodeSelect("SYMP_PART", "SYMP_PART","sympPart","","请选择器官");
		common.initCodeSelect("SEX", "SEX","sympSex","","请选择人群");
		common.initCodeSelect("SYMP_FUNCTION", "SYMP_FUNCTION","sympFunction","","请选择规则");
		common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE","sympIsScore","","是否参与症状评分");
		//初始化下拉选表单(修改弹出框)
		common.initCodeSelect("SYMP_CATEGORY", "SYMP_CATEGORY","sympCategoryUpdate","","请选择器官类别");
		common.initCodeSelect("SYMP_PART", "SYMP_PART","sympPartUpdate","","请选择器官");
		common.initCodeSelect("SEX", "SEX","sympSexUpdate","","请选择人群");
		common.initCodeSelect("SYMP_FUNCTION", "SYMP_FUNCTION","sympFunctionUpdate","","请选择规则");
		common.initCodeSelect("TRUEORFALSE", "TRUEORFALSE","sympIsScoreUpdate","","是否参与症状评分");
		symptomsTable = datatable.table(tableOptions);
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
				symptomsTable = datatable.table(tableOptions);
			}
		});

	});
</script>
<body>
	<div class="row">
		<div class="panel-body">
			<div  id="SymptomsCondition">
				<form id="queryForm" name="queryForm"
					action="${common.basepath}/${applicationScope.URL.Master.QUERY_SYMPTOMS_d}"
					method="post" class="form-horizontal">
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
		<table id="symptomsTable" class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class="text-center">选择</th>
					<th class="text-center">编码</th>
					<th class="text-center">器官</th>
					<th class="text-center">分类</th>
					<th class="text-center">名称</th>
				</tr>
			</thead>
		</table>
	</div>


	<!-- 增加模态框（Modal） -->
	<!-- ADD_NUTRIENT=/page/master/plan/add_nutrient.action -->
	<form id="addForm" class="form-horizontal"
		action="${common.basepath}/${applicationScope.URL.Master.ADD_SYMPTOMS}"
		method="post">
		<div class="modal fade bs-example-modal" id="addModal">
			<div class="modal-dialog modal-lg" style="margin-top: 80px;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue margin-zero">
							<div class="panel-heading text-center">添加功能症状信息</div>
							<div class="row row-top">
								<div class="col-xs-12 ">
									<div class="form-group">
										<label class="col-xs-3 control-label">代码</label>
										<div class="col-xs-7">
											<input name="sympCode" class="form-control"
												placeholder="请输入代码" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">症状</label>
										<div class="col-xs-7">
											<input name="sympName" class="form-control"
												placeholder="请输入名称" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">器官</label>
										<div class="col-xs-7">
											<select id="sympPart" name="sympPart" class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">器官分类</label>
										<div class="col-xs-7">
											<select id="sympCategory" name="sympCategory"
												class="form-control"></select>
										</div>
									</div>								
									<div class="form-group">
										<label class="col-xs-3 control-label">人群</label>
										<div class="col-xs-7">
											<select id="sympSex" name="sympSex" class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">症状规则</label>
										<div class="col-xs-7">
											<select id="sympFunction" name="sympFunction"
												class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">医学症状评分</label>
										<div class="col-xs-7">
											<select id="sympIsScore" name="sympIsScore" class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">症状的判断</label>
										<div class="col-xs-7">
											<textarea name="sympJudge" class="form-control"
												maxlength="100"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">可能的患病结果</label>
										<div class="col-xs-7">
											<textarea name="sympDisease" class="form-control"
												maxlength="100"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">建议去做的检查</label>
										<div class="col-xs-7">
											<textarea name="sympPositive" class="form-control"
												maxlength="100"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero"
						style="padding-bottom: 15px;position:relative;">
						<div class="col-xs-2 col-xs-offset-8 ">
							<button type="submit"
								style="position:absolute;bottom: 7px;right:35px;"
								class="btn btn-primary btn-block">确定</button>
						</div>
						<div class="col-xs-2 ">
							<button type="button"
								style="position:absolute;bottom: 7px;right:10px;"
								class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
						</div>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</form>

	<!-- 修改模态框（Modal） -->
	<form id="updateForm" class="form-horizontal"
		action="${common.basepath}/${applicationScope.URL.Master.UPDATE_Symptoms}"
		method="post">
		<div class="modal fade bs-example-modal" id="updateModal"
			tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<input type="hidden" name="id" id="id">
					<div class="modal-body">
						<div class="panel panel-lightblue margin-zero">
							<div class="panel-heading text-center">修改功能症状</div>
							<div class="row row-top">
								<div class="col-xs-12 col-xs-offset-1">
									<div class="form-group">
										<label class="col-xs-3 control-label">代码</label>
										<div class="col-xs-7">
											<input id="sympCode" name="sympCode" class="form-control"
												readonly />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">症状</label>
										<div class="col-xs-7">
											<input id="sympName"  name="sympName" class="form-control"
												placeholder="请输入名称" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">器官</label>
										<div class="col-xs-7">
											<select id="sympPartUpdate" name="sympPart" class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">器官分类</label>
										<div class="col-xs-7">
											<select id="sympCategoryUpdate" name="sympCategory"
												class="form-control"></select>
										</div>
									</div>								
									<div class="form-group">
										<label class="col-xs-3 control-label">人群</label>
										<div class="col-xs-7">
											<select id="sympSexUpdate" name="sympSex" class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">症状规则</label>
										<div class="col-xs-7">
											<select id="sympFunctionUpdate" name="sympFunction"
												class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">医学症状评分</label>
										<div class="col-xs-7">
											<select id="sympIsScoreUpdate" name="sympIsScore" class="form-control"></select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">症状的判断</label>
										<div class="col-xs-7">
											<textarea id="sympJudge" name="sympJudge" class="form-control"
												maxlength="100"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">可能的患病结果</label>
										<div class="col-xs-7">
											<textarea id="sympDisease" name="sympDisease" class="form-control"
												maxlength="100"></textarea>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">建议去做的检查</label>
										<div class="col-xs-7">
											<textarea id="sympPositive" name="sympPositive" class="form-control"
												maxlength="100"></textarea>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero"
						style="padding-bottom: 15px;position:relative;">
						<div class="col-xs-2 col-xs-offset-8 ">
							<button type="submit"
								style="position:absolute;bottom: 7px;right:35px;"
								class="btn btn-primary btn-block">确定</button>
						</div>
						<div class="col-xs-2 ">
							<button type="button"
								style="position:absolute;bottom: 7px;right:10px;"
								class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
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
