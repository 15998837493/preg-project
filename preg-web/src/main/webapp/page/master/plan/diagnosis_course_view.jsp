<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<title>诊断课程</title>
</head>
<script type="text/javascript" src="${common.basepath}/page/master/plan/diagnosis_course.js"></script>
<script type="text/javascript">
var checkedData;// 选中项信息
var checkedRow;// 选中行信息
var pointTable;// table，注意命名不要重复

//配置datatable
var tableOptions = {
	id: "pointTable",
	form: "courseQuery",
	columns: [
		{"data": null,"sClass": "text-center",
			"render":  function (data, type, row, meta) {
        		return "<input type='radio' name='tableRadio' value='"+data.pointId+"' />";
        	}
		},
		{"data": "pointSubclass","sClass": "text-center" },
		{"data": "pointDesc","sClass": "text-left" },
		{"data": "interveneDiseaseNames","sClass": "text-left"}
// 		{"data": "pointOrder","sClass": "text-center"}
	],
	rowClick: function(data, row){
		checkedData = data;
		checkedRow = row;
		$("#id").val(data.pointId);
	},
	condition : "courseCondition"
};

$().ready(function() {
		pointTable = datatable.table(tableOptions);	
		
		$("button[name='operateButton']").click(function(){
			var id = $("#id").val();
			if(this.id == 'addButton'){
				common.clearForm("addForm");
				//表单验证
				$("#addForm").validate(appPointOptions);
				//加入必填项提示
				common.requiredHint("addForm", appPointOptions);
				$("#addModal").modal("show");
			}
			if(this.id == 'editButton' && isChoose(id)){
				//表单验证
				$("#updateForm").validate(updatePointOptions);
				//加入必填项提示
				common.requiredHint("updateForm", updatePointOptions);
				$("#pointSubclass").val(checkedData.pointSubclass);
				$("#pointOrder").val(checkedData.pointOrder);
				$("#pointDesc").val(checkedData.pointDesc);
				$("#interveneDiseaseNames").val(checkedData.interveneDiseaseNames);
				var interveneDiseaseCode = common.isEmpty(checkedData.interveneDiseaseIds)?"":checkedData.interveneDiseaseIds.split(",");
// 				var ids = "";
				for(var i=0;i<interveneDiseaseCode.length;i++){
					$("input:checkbox[name='diseaseIdList'][value='"+ interveneDiseaseCode[i] + "']").attr("checked", true);
// 					var checkbox = $("#diseaseModal").find("span[dname='"+interveneDiseaseCode[i]+"']").prev();
// 					checkbox.attr("checked",true);
// 					ids = ids+checkbox.val()+",";
				}
				$("#interveneDiseaseIds").val(checkedData.interveneDiseaseIds);
// 				if(!common.isEmpty(ids)){
// 					$("#interveneDiseaseIds").val(ids.substring(0, ids.length-1));
// 				}
				$("#updateModal").modal("show");
			}
			if(this.id == 'removeButton' && isChoose(id)){
				removeClick(id);
			}
			if(this.id == 'searchButton'){
				//pointTable = datatable.table(tableOptions);
				datatable.search(pointTable, tableOptions);
			}
		});
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
<body>
<div class="row">
	<div class="panel-body ">
        <form id="courseQuery" name="courseQuery" action="${common.basepath}/${applicationScope.URL.Master.INTERVENEPOINTS_QUERY}" method="post" class="form-horizontal">
	        <input type='hidden' name="pointType" value="30"/>
	       	<div id="courseCondition">
		    	<div class="form-inline">
		        	<input type='text' name='pointSubclass' class='form-control' placeholder='请输入检索内容'/>
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
	       	</div>
    	</form>
	</div>
</div>
<div class="table-responsive">
	<table id="pointTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
			<th class="text-center" style="width: 5%">选择</th>
			<th class="text-center" style="width: 15%">课程代码</th>
			<th class="text-center" style="width: 50%">课程名称</th>
			<th class="text-center" style="width: 20%">诊断项目</th>
		   	</tr>
		</thead>
	</table>
</div>
		

<!-- 增加模态框（Modal） -->
<form id="addForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.INTERVENEPOINTS_ADD}" method="post">
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-plus fa-fw"></i>增加诊断课程
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="row row-top">
							<div class="col-xs-12">
		        				<div class="form-group">
									<label class="col-xs-3 control-label">课程名称</label>
									<div class="col-xs-7">
											<input name="pointSubclass" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">课程介绍</label>
									<div class="col-xs-7">
									<input name="pointDesc" class="form-control"/>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">诊断项目</label>
									<div class="col-xs-7">
										<textarea name="interveneDiseaseNames" 
												  class="form-control"
											      onclick="chooseDiseaseModal('addForm');" 
											      placeholder="点击选择适合的疾病"  
											      style="background-color: white;" readonly></textarea>
										<input name="interveneDiseaseIds" style="display:none;"value="" />
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

<form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Master.INTERVENEPOINTS_UPDATE}" method="post">
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<input type="hidden" name="pointId" id="id">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-search fa-fw"></i>修改诊断课程
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body">
						<div class="row row-top">
							<div class="col-xs-12">
			       				<div class="form-group">
									<label class="col-xs-3 control-label">课程名称</label>
									<div class="col-xs-7">
											<input id="pointSubclass" name="pointSubclass" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">课程介绍</label>
									<div class="col-xs-7">
									    <input id="pointDesc" name="pointDesc" class="form-control" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">诊断项目</label>
									<div class="col-xs-7">
										<textarea id="interveneDiseaseNames" 
										          name="interveneDiseaseNames" 
										          class="form-control"
											      onclick="chooseDiseaseModal('updateForm');" 
											      placeholder="点击选择适合的疾病" 
											      style="background: white;" readonly="readonly"></textarea>
										<input id="interveneDiseaseIds" name="interveneDiseaseIds" style="display:none;" value="" />
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


<div id="diseaseModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-center">
						<i class="fa fa-search fa-fw"></i>诊断项目
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12 form-horizontal">
								<div class="panel-body">
									<c:forEach items="${diseaseList }" var="list" varStatus="i">
										<div class="form-group">
										<%-- <label class="col-xs-2 control-label"><span id="${list.type }_span"></span>：</label>
										<script>$("#${list.type }_span").html(CODE.transCode("DISEASETYPE", "${list.type }"));</script> --%>
										<div class="col-xs-10 col-xs-offset-1">
										<c:forEach items="${list.interveneDiseaseList }" var="disease">
											<div class="col-xs-3" style="padding-left:0px;">
												<label class="checkbox-inline"> 
													<input type="checkbox" name="diseaseIdList" value="${disease.diseaseId }" />
													<span id="${disease.diseaseId }_diseaseName" name="${disease.diseaseName}">${disease.diseaseName}</span>
												</label>
											</div>
										</c:forEach>
										</div>
										</div>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
				  <div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="button" id="confirmBtn" class="btn btn-primary btn-block">确定</button>
					</div>
					<div class="col-xs-2 no-right">
						<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
					</div>
				</div>				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>