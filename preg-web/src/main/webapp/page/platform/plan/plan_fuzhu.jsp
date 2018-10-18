<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/common.jsp"%>
<title>指标录入</title>
<script type="text/javascript">
<%-- 页面初始化对象 --%>
var auxiliaryCheck=${queryAuxiliaryCheck};
var hospitals=${hospitals};
var quotaItems=${quotaItems};
var diagnosis = ${diagnosis};
var diagnosisDisease = ${diagnosisDisease};
</script>
<script type="text/javascript" src="${common.basepath}/page/platform/plan/plan_fuzhu.js"></script>
</head>
<body>
<input type="hidden" id="diagnosisId" value='${diagnosisId}'/>
<div class="panel panel-lightblue">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 辅助检查
	</div>
	<div class="panel-body">
		<div class="col-xs-12 no-padding" id="diseaseSpanFuzhu">
			<label class="control-label">诊断：</label>
		</div>
	</div>
	<div class="panel-body panel-body-zero">
		<div class="col-xs-3 no-padding">
			<div class="table-responsive table-fixed-head" >
				<table class="table table-bordered no-bottom">
					<thead>
						<tr class="active">
							<th class="text-center col-xs-2"><input type="checkbox" id='inspectItems' onclick="checkInspectItem(this)"/></th>
							<th class="text-center col-xs-10">辅助检验收费项目</th>
							<!-- <th class="text-center col-xs-3">操作</th> -->
						</tr>
					</thead>
				</table>
			</div>
			<div class="table-responsive" style="width: 100%; height: 208px;overflow-y: scroll;">
				<table class="table table-bordered no-bottom" style="">
					<tbody id="diagnosis_disease_tbody"></tbody>
				</table>
			</div>
			<div class="table-responsive">
				<table class="table table-bordered no-bottom">
					<tfoot>
						<tr class="active">
							<td class="text-left" style="width: 70%;border-right: 0px;">
								<input type='text' class='form-control' placeholder='请输入收费项目' id="diagnosisAddDisease" />
							</td>
							<td class="text-center" style="border: 0px;padding-right: 0px;">
								<button class="btn btn-default" type="button" id="removeInspectItem" style="color: #0C78D5; margin-top: 1px;">
									<i class="fa fa-remove fa-fw"></i>  删除
								</button>
							</td>
							<td class="text-center" style="border-left: 0px;">
								<button class="btn btn-default" type="button" onclick="saveHospitalTemplate();" style="color: #0C78D5; margin-top: 1px;">
									<i class="fa fa-file-text-o fa-fw"></i> 生成套餐
								</button>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		
		<div class="col-xs-3 padding-zero">
			<!-- 收费项目套餐 -->
			<div class="table-responsive table-fixed-head">
				<table class="table table-bordered no-bottom">
					<thead>
						<tr class="active">
							<th class="text-center" style="width: 65%;">套餐</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="table-responsive" style="width: 100%; overflow-y: scroll; height: 260px;">
				<table class="table table-bordered no-bottom">
					<tbody id="auxiliary_tbody"></tbody>
				</table>
			</div>
		</div>
		<div class="col-xs-3 padding-zero">
			<!-- 收费项目 -->
			<div class="table-responsive  table-fixed-head">
				<table class="table table-bordered no-bottom">
					<thead>
						<tr class="active">
							<th class="text-center" style="width: 65%;">收费项目</th>
							<th class="text-center">操作</th>
						</tr>
					</thead>
				</table>
			</div>
			<div class="table-responsive" style="width: 100%; overflow-y: scroll; height: 260px;">
				<table class="table table-bordered no-bottom">
					<tbody id="hospital_tbody"></tbody>
				</table>
			</div>
		</div>
		<!-- 收费项目子项 -->
		<div class="col-xs-3 padding-zero">
			<div class="table-responsive table-fixed-head">
				<table class="table table-bordered no-bottom">
					<thead>
						<tr class="active">
							<th class="text-center" style="width: 65%;">检验项目</th>
							<!-- <th class="text-center">操作</th> -->
						</tr>
					</thead>
				</table>
			</div>
			<div class="table-responsive" style="width: 100%; height: 260px;overflow-y: scroll;">
				<table class="table table-bordered no-bottom">
					<tbody id="children_hospital_tbody"></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<form id="editItemForm"  action="" class="form-horizontal" method="post">
<div id="templatModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">
						<i class="fa fa-file-text-o fa-fw"></i>生成套餐
						<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="row">
	                    <div class="col-xs-9 col-xs-offset-1 row-top">
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">模板名称</label>
	                            <div class="col-xs-10">
	                            	<input id="templetName" name="templetName" class="form-control" type="text"/>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-xs-2 control-label">收费项目</label>
	                            <div class="col-xs-10">
									<input type="hidden" id="inspectIds" name="inspectIds" />
<!-- 	                            	<input id="inspectNames" name="inspectNames" class="form-control" type="text"/>
 -->	                            	<textarea id="inspectNames" 
								                  name="inspectNames" 
								                  class="form-control text-left" 
								                  placeholder="请选择辅助检查项目" 
								                  style="background-color: white;" readonly="readonly"></textarea>
	                            </div>
	                        </div>
						</div>
					</div>
				</div>
				<div class="panel-body text-right" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right ">
						<button type="submit" class="btn btn-primary btn-block">确认</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
</body>
</html>