<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<title>摄入类型一览页</title>
</head>
<script type="text/javascript">
var checkedData;// 选中项信息
var checkedRow;// 选中行信息
var intakeTypeTable;// table，注意命名不要重复

//配置datatable
var tableOptions = {
	id: "intakeTypeTable",
	form: "queryForm",
	columns: [
		{"data": null,"sClass": "text-center",
			"render":  function (data, type, row, meta) {
        		return "<input type='radio' name='tableRadio' value='"+data.id+"' />";
        	}
		},
		{"data": "code","sClass": "text-center" },
		/* {"data": "type","sClass": "text-center",
		 	"render": function(data, type, row, meta) {
				return $("#type").find("option[value='"+data+"']").text();
			}
		}, */
		{"data": "name","sClass": "text-center" },
		{"data": "unit","sClass": "text-center" },
		{"data": "unitAmount","sClass": "text-center" },
		{"data": "unitEnergy","sClass": "text-center" },
		{"data": "unitCbr","sClass": "text-center" },
		{"data": "unitProtein","sClass": "text-center" },
		{"data": "unitFat","sClass": "text-center" }
	],
	rowClick: function(data, row){
		checkedData = data;
		checkedRow = row;
		$("#id").val(data.id);
	},
	condition : "intakeTypeCondition"
};
</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/plan/intake_type_view.js"></script>
<script type="text/javascript">
$().ready(function (){

	$("#addForm").validate(addOptions);
	common.requiredHint("addForm",addOptions);
	$("#updateForm").validate(updateOptions);
    common.requiredHint("updateForm", updateOptions);
    
	intakeTypeTable = datatable.table(tableOptions);
	// 按钮点击事件
	$("button[name='operateButton']").click(function(){
		var id = $("#id").val();
		if(this.id == 'addButton'){
			common.clearForm("addForm");
			$("#addModal").modal("show");
		}
		if(this.id == 'editButton' && common.isChoose(id)){
			$.each(checkedData,function (key, value) {
				$("#"+key).val(value);
			});
			
			$("#updateModal").modal("show");
		}
		if(this.id == 'removeButton' && common.isChoose(id)){
			removeIntakeType(id);
		}
		if(this.id == 'searchButton'){
			//intakeTypeTable = datatable.table(tableOptions);
			datatable.search(intakeTypeTable, tableOptions);
		}
	});
});
</script>
<body>
	<div class="row">
		<div class="panel-body">
	        <div id="intakeTypeCondition">
	            <form id="queryForm" action="${common.basepath}/${applicationScope.URL.Master.PLAN_INTAKE_TYPE_QUERY}" method="post" class="form-horizontal">
                	<div class="form-inline">
                        <input type='text' name='name' class='form-control' placeholder='请输入检索内容'/>
			      		<button type="button" id="searchButton" name="operateButton" class="btn btn-default">
			      			<i class="fa fa-search fa-fw"></i>查询
			      		</button>
			      		<div class="vertical-line"></div>
			      		<!-- <button type="button" id="addButton" name="operateButton" class="btn btn-default">
				     		<i class="fa fa-plus fa-fw"></i> 增加
				  		</button> -->
				  		<button type="button" id="editButton" name="operateButton" class="btn btn-default">
				     		<i class='fa fa-edit fa-fw'></i> 编辑
				  		</button>
				  		<!-- <button type="button" id="removeButton" name="operateButton" class="btn btn-default">
				     		<i class='fa fa-remove fa-fw'></i> 删除
				  		</button> -->
                    </div>
	            </form>
	        </div>
		</div>
	</div>
<div class="table-responsive">
	<table id="intakeTypeTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">代码</th>
				<!-- <th class="text-center">类别</th> -->
				<th class="text-center">名称</th>
				<th class="text-center">单位</th>
				<th class="text-center">每单位量（克）</th>
				<th class="text-center">每份热量（kcal）</th>
				<th class="text-center">每份碳水化合物（克）</th>
				<th class="text-center">每份蛋白质（克）</th>
				<th class="text-center">每份脂肪（克）</th>
		   	</tr>
		</thead>
	</table>
</div>
	  		

<!-- 编辑模态框（Modal） -->
<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.PLAN_INTAKE_TYPE_UPDATE}" method="post">
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<input type="hidden" name="id" id="id">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-edit fa-fw"></i>修改摄入类型
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="row row-top">
							<div class="col-xs-12">
		        				<div class="form-group">
									<label class="col-xs-3 control-label">代码</label>
									<div class="col-xs-7">
										<input id="code" name="code" class="form-control"  readonly/>
									</div>
								</div>
		        				<div class="form-group">
									<label class="col-xs-3 control-label">名称</label>
									<div class="col-xs-7">
										<input id="name" name="name" class="form-control"  placeholder="请输入名称"/>
									</div>
								</div>
								<%-- <div class="form-group">
									<label class="col-xs-3 control-label">类型</label>
									<div class="col-xs-7">
										<select id="type" name="type" class="form-control">
											<option value="">==请选择==</option>
											<c:if test="${types!=null}">
												<c:forEach items="${types}" var="type">
													<option value="${type.codeValue }">${type.codeName }</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div> --%>
								<div class="form-group">
									<label class="col-xs-3 control-label">单位</label>
									<div class="col-xs-7">
										<input id="unit" name="unit" class="form-control"  placeholder="请输入单位"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每单位量</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input id="unitAmount" name="unitAmount" class="form-control"  placeholder="请输入每单位量" maxlength="5"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份热量</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input id="unitEnergy" name="unitEnergy" class="form-control"  placeholder="请输入每份热量" maxlength="6"/>
											<div class="input-group-addon">kcal</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份碳水化合物</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input id="unitCbr" name="unitCbr" class="form-control"  placeholder="请输入每份碳水化合物" maxlength="6"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份蛋白质</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input id="unitProtein" name="unitProtein" class="form-control"  placeholder="请输入每份蛋白质" maxlength="6"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份脂肪</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input id="unitFat" name="unitFat" class="form-control"  placeholder="请输入每份脂肪" maxlength="6"/>
											<div class="input-group-addon">克</div>
										</div>
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
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div>
</form>

<!-- 增加模态框（Modal） -->
<form id="addForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.PLAN_INTAKE_TYPE_ADD}" method="post">
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-center">增加摄入类型</div>
					<div class="panel-body">
						<div class="row row-top">
							<div class="col-xs-12">
		        				<div class="form-group">
									<label class="col-xs-3 control-label">代码</label>
									<div class="col-xs-7">
										<input name="code" class="form-control"  placeholder="请输入代码"/>
									</div>
								</div>
		        				<div class="form-group">
									<label class="col-xs-3 control-label">名称</label>
									<div class="col-xs-7">
										<input name="name" class="form-control"  placeholder="请输入名称"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">类型</label>
									<div class="col-xs-7">
										<select name="type" class="form-control">
											<option value="">==请选择==</option>
											<c:if test="${types!=null}">
												<c:forEach items="${types}" var="type">
													<option value="${type.codeValue }">${type.codeName }</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">单位</label>
									<div class="col-xs-7">
										<input name="unit" class="form-control"  placeholder="请输入单位"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每单位量</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input name="unitAmount" class="form-control"  placeholder="请输入每单位量" maxlength="4"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份热量</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input name="unitEnergy" class="form-control"  placeholder="请输入每份热量" maxlength="4"/>
											<div class="input-group-addon">kcal</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份碳水化合物</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input name="unitCbr" class="form-control"  placeholder="请输入每份碳水化合物" maxlength="4"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份蛋白质</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input name="unitProtein" class="form-control"  placeholder="请输入每份蛋白质" maxlength="4"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">每份脂肪</label>
									<div class="col-xs-7">
										<div class="input-group">
											<input name="unitFat" class="form-control"  placeholder="请输入每份脂肪" maxlength="4"/>
											<div class="input-group-addon">克</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				 <div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="submit" class="btn btn-primary btn-block">确定</button>
					</div>
					<div class="col-xs-2 no-right">
						<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
					</div>
				</div>				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div>
</form>

</body>
</html>
