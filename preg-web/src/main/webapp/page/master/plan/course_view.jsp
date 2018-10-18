<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/plan/course.js"></script>
<title>孕期课程管理</title>
<script type="text/javascript">
//按钮状态
var operateType;
$.validator.addMethod("pregWeekCourse", function(value,element) {
	if(common.isEmpty($("#saveForm [name='pregWeekBegin']").val()) || common.isEmpty($("#saveForm [name='pregWeekEnd']").val())){
		return true;
	}
return parseInt($("#saveForm [name='pregWeekBegin']").val()) < parseInt($("#saveForm [name='pregWeekEnd']").val());
}, '孕周数的开始必须小于结束');

$.validator.addMethod("pregWeekCourseUpdate", function(value,element) {
	if(common.isEmpty($("#updateForm [name='pregWeekBegin']").val()) || common.isEmpty($("#updateForm [name='pregWeekEnd']").val())){
		return true;
	}
return parseInt($("#updateForm [name='pregWeekBegin']").val()) < parseInt($("#updateForm [name='pregWeekEnd']").val());
}, '孕周数的开始必须小于结束');

	var checkedData;// 选中项信息
	var checkedRow;// 选中行信息
	var courseTable;// table，注意命名不要重复
	//配置datatable
	var tableOptions = {
		id: "courseTable",
		form: "courseQuery",
		columns: [
			{"data": null,"sClass": "text-center",
				"render":  function (data, type, row, meta) {
	        		return "<input type='radio' name='tableRadio' value='"+data.id+"' />";
	        	}
			},
			{"data": "pregId","sClass": "text-center" },
			{"data": "pregName","sClass": "text-center" },
			{"data": "pregDeString","sClass": "text-center" },
			{"data": null,"sClass": "text-center",
			 	"render": function(data, type, row, meta) {
					return "<a style='cursor: pointer;' onclick='getPregCourseDetail(\""+data.pregId+"\",\""+data.pregName+"\")' ><i class='fa fa-edit fa-fw'></i> 配置</a>";
				}
			}
		],
		rowClick: function(data, row){
			checkedData = data;
			checkedRow = row;
			$("#id").val(data.id);
		},
		condition : "courseCondition"
	};
	
	
	$().ready(function() {
			$("#saveForm").validate(addOptions);
		    //加入必填项提示
		    common.requiredHint("saveForm", addOptions);
			$("#updateForm").validate(update);
		    //加入必填项提示
		    common.requiredHint("updateForm", update);
		/* $("#courseQuery").attr("action",PublicConstant.basePath+"/page/exam/item/query_item.action"); */
		
		courseTable = datatable.table(tableOptions);
		
		$("button[name='operateButton']").click(function(){
			
			var id = $("#id").val();
			
			if(this.id == 'searchButton'){
				//courseTable = datatable.table(tableOptions);
				datatable.search(courseTable, tableOptions);
			}
			
			if(this.id == 'addButton'){
				operateType = "add";
				common.clearForm("saveForm");
				$("#saveModal").modal('show');
			}
			if(this.id == 'editButton' && isChoose(id)){
				 operateType = "update";
		    	 $.ajax({
		    	       url: PublicConstant.basePath + URL.Master.UPDATE_INIT_PREGNANCYCOURSE,
		    	       data: {"id":id},    
		    	       dataType: 'json',
		    	       success: function(data) {
	    		    	   console.log(data);
	    					$("#updateForm").find("input[name='pregId']").val(data.value.pregId);
	    					$("#updateForm").find("input[name='pregName']").val(data.value.pregName);
	    					$("#updateForm").find("input[name='pregWeekBegin']").val(data.value.pregWeekBegin);
	    					$("#updateForm").find("input[name='pregWeekEnd']").val(data.value.pregWeekEnd);
	    					$("#updateModal").modal('show');
		    	       },
		    	       error: function(e) {
		    	           console.log(e.responseText);
		    	       }
		    	    });	
				
			}
			if(this.id == 'removeButton' && isChoose(id)){
				removeClick(id);
			}
		});
		$("#updateModal").modal('hide');
	});
	
	
	function isChoose(id){
		if(common.isEmpty(id)){
		    layer.alert('请选择操作的记录');
		    return false;
		}else{
			return true;
		}
	}
</script>
</head>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="courseCondition">
				<form id="courseQuery" name="courseQuery" action="${common.basepath }/${applicationScope.URL.Master.QUERY_PREGNANCYCOURSE}" method="post"
					class="form-horizontal">
					<div class="form-inline">
						<input type='text' name='pregName' class='form-control' placeholder='请输入检索内容' />
						<button type="button" id="searchButton" name="operateButton" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button type="button" id="addButton" name="operateButton" class="btn btn-default">
							<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button type="button" id="editButton" name="operateButton" class="btn btn-default">
							<i class='fa fa-edit fa-fw'></i> 编辑
						</button>
						<button type="button" id="removeButton" name="operateButton" class="btn btn-default">
							<i class='fa fa-remove fa-fw'></i> 删除
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="table-responsive">
		<table id="courseTable" class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class="text-center">选择</th>
					<th class="text-center">编号</th>
					<th class="text-center">名称</th>
					<th class="text-center">包含课程</th>
					<th class="text-center">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 增加模态框（Modal） -->
	<form id="saveForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.ADD_PREGNANCYCOURSE}" method="post">
		<div class="modal fade bs-example-modal form-horizontal" id="saveModal">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-body">
						<div class="panel panel-lightblue">
							<div class="panel-heading text-left">
								<i class="fa fa-plus fa-fw"></i>增加课程
								<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
							</div>
							<div class="panel-body">
								<div class="row row-top">
									<div class="col-xs-12">
										<div class="form-group">
											<label class="col-xs-3 control-label">编码</label>
											<div class="col-xs-7">
												<input id="pregId" name="pregId" class="form-control" placeholder="请输入编码" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-3 control-label">标题</label>
											<div class="col-xs-7">
												<input id="pregName" name="pregName" class="form-control" placeholder="请输入标题" />
											</div>
										</div>
									</div>
									<div class="col-xs-12">
										<div class="form-group">
											<label class="col-xs-3 control-label">孕周</label>
											<div class="col-xs-7">
												<div class="col-xs-5 padding-zero no-left">
													<input id="pregWeekBegin" name="pregWeekBegin" class="form-control" />
												</div>
												<label class="col-xs-2 control-label" style="text-align:center;">至</label>
												<div class="col-xs-5 padding-zero no-right">
													<input id="pregWeekEnd" name="pregWeekEnd" class="form-control" />
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
				</div>
			</div>
		</div>
	</form>
	<!-- 更新模态框（Modal） -->
	<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.UPDATE_PREGNANCYCOURSE}" method="post">
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog  modal-lg">
				<div class="modal-content">
					<input id="id" type="hidden" name="id" />
					<div class="modal-body">
						<div class="panel panel-lightblue">
							<div class="panel-heading text-left">
								<i class="fa fa-search fa-fw"></i>修改课程
								<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
							</div>
							<div class="panel-body">
								<div class="row row-top">
									<div class="col-xs-12">
										<div class="form-group">
											<label class="col-xs-3 control-label">编码</label>
											<div class="col-xs-7">
												<input id="pregId" name="pregId" class="form-control" placeholder="请输入编码" readonly="readonly" />
											</div>
										</div>
										<div class="form-group">
											<label class="col-xs-3 control-label">标题</label>
											<div class="col-xs-7">
												<input id="pregName" name="pregName" class="form-control" placeholder="请输入标题" />
											</div>
										</div>
									</div>
									<div class="col-xs-12">
										<div class="form-group">
											<label class="col-xs-3 control-label">孕周</label>
											<div class="col-xs-7">
												<div class="col-xs-5 padding-zero no-left">
													<input id="pregWeekBegin" name="pregWeekBegin" class="form-control" />
												</div>
												<label class="col-xs-2 control-label" style="text-align:center;">至</label>
												<div class="col-xs-5 padding-zero no-right">
													<input id="pregWeekEnd" name="pregWeekEnd" class="form-control" />
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
				</div>
			</div>
		</div>
	</form>
</body>
</html>
