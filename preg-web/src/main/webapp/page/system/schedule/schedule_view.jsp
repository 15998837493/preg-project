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
					"data" : "scheduleWeek",
					"sClass" : "text-left"
				},
				{
					"data" : "scheduleNoon",
					"sClass" : "text-left"
				},
				{
					"data" : "scheduleContent",
					"sClass" : "text-left"
				},
				{
					"data" : "scheduleMaxPerson",
					"sClass" : "text-center"
				} ],
		rowClick : function(data, row) {
			checkedData = data;
			checkedRow = row;
			$("#scheduleId").val(data.scheduleId);
		},
		condition : "nutrientCondition"
	};
</script>
<script type="text/javascript" charset="utf-8"
	src="${common.basepath}/page/system/schedule/schedule_view.js"></script>
<script type="text/javascript">
	$().ready(function() {
		/* nutrientTable = datatable.table(tableOptions); */
		$("button[name='operateButton']").click(function() {
			var id = $("#scheduleId").val();
			validator = $("#addForm").validate(Options);
			//加入必填项提示
			common.requiredHint("addForm", Options);
			if (this.id == 'addButton') {//添加
				$("#addForm").attr("action",URL.get("System.SCHEDULE_ADD"));
				common.clearForm("addForm");
				$("#scheduleId").val(id);
				// 记录操作类型
				$("#editFormType").val("add");
				$("#addModal").modal("show");
			}
			if (this.id == 'editButton' && isChoose(id)) {//编辑	
				/* $("#addForm").attr("action",URL.get("System.SCHEDULE_UPDATE")); */
				var url = URL.get("System.SCHEDULE_QUERY");
				var params = "scheduleId=" + id;
				ajax.post(url, params, dataType.json, function(data) {
					if(data.data.length==1) {
						var value = data.data[0];
						$("#scheduleWeek").val(value.scheduleWeek);
						$("#scheduleNoon").val(value.scheduleNoon);
						$("#scheduleMaxPerson").val(value.scheduleMaxPerson);
						$("#scheduleContent").val(value.scheduleContent);
					}else {
						layer.alert('返回数据错误，大于或小于一条');
					}
				},false,false);
				// 记录操作类型
				$("#editFormType").val("update");
				$("#addModal").modal("show");
			}
			if (this.id == 'removeButton' && isChoose(id)) {//删除
				removeClick(id);
			}
/* 			if (this.id == "searchButton") {//查询
				nutrientTable = datatable.table(tableOptions);
			} */
		});
		$("#nutrientTable").find("tr").click(function() {
			$("input[name='leafRadio']").attr("checked",false);
			$(this).find("input[name='leafRadio']").attr("checked",true);
			$("#scheduleId").val($(this).find("#id").val());
		});		
	layer.close(layer.index);
	});
</script>
<!-- 添加/编辑后提示信息 -->
<c:if test="${!empty successInfo}">
<script type="text/javascript">
layer.alert('操作成功！');
</script>
</c:if>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="nutrientCondition">
				<form id="queryForm" name="queryForm" action="${common.basepath}/${applicationScope.URL.System.SCHEDULE_VIEW}" method="post" class="form-horizontal">
					<div class="form-inline">
<!-- 						<input type='text' class='form-control' placeholder='请输入检索内容' />
						<button type="button" id="searchButton" name="operateButton"
							class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button> 
						<div class="vertical-line"></div>-->
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
					<th class="text-center">周</th>
					<th class="text-center">课程时间</th>
					<th class="text-center">课程内容</th>
					<th class="text-center">课程人数</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${fn:length(requestScope.schedule)==0}">
			<td style="text-align:center;"colspan="5"><h4>没有找到数据！</h4></td>
			</c:if>
			<c:forEach items="${requestScope.schedule }" var="schedule">			
            <tr>
            <td style="text-align:center;"><input name="leafRadio" type="radio" /></td>
            <td style="text-align:center;">${schedule.scheduleWeek }</td>
            <td style="text-align:left;">${schedule.scheduleNoon }</td>
            <td style="text-align:left;">${schedule.scheduleContent }</td>
            <td style="text-align:center;">${schedule.scheduleMaxPerson }</td>
            <input type="hidden" id="id" value="${schedule.scheduleId }":/>
            </tr>			
			</c:forEach>
			</tbody>
		</table>
	</div>


	<!-- 模态框（Modal） -->
	<!-- ADD_NUTRIENT=/page/master/plan/add_nutrient.action -->
	<form id="addForm" class="form-horizontal" method="post">
		<div class="modal fade bs-example-modal" id="addModal">
			<div class="modal-dialog modal-lg" style="margin-top: 80px;">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue ">
							<div class="panel-heading text-left">
								<!-- <i class="fa fa-plus fa-fw"></i> -->增加/编辑
								<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
							</div>						
							<div class="row row-top">
								<div class="col-xs-12 ">
									<div class="form-group">
									<input type="hidden" id="scheduleId" name="scheduleId"/>
									<input id="editFormType" name="editFormType" class="form-control" type="hidden" />
										<label class="col-xs-3 control-label">周</label>
										<div class="col-xs-7">
											<select id="scheduleWeek" name="scheduleWeek" class="form-control">
											<option value="周一" selected>周一</option>
											<option value="周二">周二</option>
											<option value="周三">周三</option>
											<option value="周四">周四</option>
											<option value="周五">周五</option>
											<option value="周六">周六</option>
											<option value="周日">周日</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">课程时间</label>
										<div class="col-xs-7">
											<input id="scheduleNoon" maxlength="20" name="scheduleNoon" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">允许参与课程人数</label>
										<div class="col-xs-7">
											<input onblur="validNum(this.value);" maxlength="8" onkeyup="validNum(this.value);" id="scheduleMaxPerson" name="scheduleMaxPerson" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-3 control-label">课程内容</label>
										<div class="col-xs-7">
											<textarea id="scheduleContent" maxlength="100" name="scheduleContent" class="form-control"></textarea>
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
