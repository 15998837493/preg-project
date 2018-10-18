<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
    //初始化诊断项目勾选
    initDiagnosisDisease();
});

/**
 * 初始化选择内容
 */
function initDiagnosisDisease(){
	// 清空历史记录
	$("input:checkbox[name='diseaseIdList']").attr("checked", false);
	$("#otherDisease").val("");
	// 设置明细
	if(!common.isEmpty($("#diagnosisDiseaseCodes").val())){
		$($("#diagnosisDiseaseCodes").val().split(",")).each(function(index,diseaseCode){
			$("input:checkbox[name='diseaseIdList'][value='"+ diseaseCode + "']").attr("checked", true);
		});
	}
    var thisDiagnosisDiseaseNames = $("#diagnosisDiseaseNames").val();
    if(!common.isEmpty(thisDiagnosisDiseaseNames) && thisDiagnosisDiseaseNames.indexOf("；") > -1){
		$("#otherDisease").val(thisDiagnosisDiseaseNames.split("；")[1]);
	}
}


function chooseDiseaseModal(){
	initDiagnosisDisease();
	$("#diseaseModal").modal("show");
	$("#diseaseModal").css("z-index","1080");
}
function chooseDiseaseModal_update() {
	$("#diseaseModal_update").modal("show");
	$("#diseaseModal_update").css("z-index","1080");
}
function getDisease(){
	var diseaseCodes = "";
	var diseaseNames = "";
	$("input:checkbox[name='diseaseIdList']:checked").each(function(index, disease){
		if(index == 0){
			diseaseCodes = disease.value;
			diseaseNames = $("#" + disease.value + "_diseaseName").html();
		}else{
			diseaseCodes += "," + disease.value;
			diseaseNames += "、" + $("#" + disease.value + "_diseaseName").html();
		}
	});
	$("#diagnosisDiseaseCodes").val(diseaseCodes);
	$("#diagnosisDiseaseNames").val(diseaseNames);
	$("#diseaseModal").modal("hide");
}
function getDisease_update(){
	var diseaseCodes = "";
	var diseaseNames = "";
	$("input:checkbox[name='diseaseIdList_update']:checked").each(function(index, disease){
		if(index == 0){
			diseaseCodes = disease.value;
			diseaseNames = $("#" + disease.value + "_diseaseName_update").html();
		}else{
			diseaseCodes += "," + disease.value;
			diseaseNames += "、" + $("#" + disease.value + "_diseaseName_update").html();
		}
	});
	$("#diagnosisDiseaseCodes_update").val(diseaseCodes);
	$("#diagnosisDiseaseNames_update").val(diseaseNames);
	$("#diseaseModal_update").modal("hide");
}
function chooseItemModal(){
	$("#diseaseItemModal").modal("show");
	$("#diseaseItemModal").css("z-index",1080);
}
function chooseItemModal_update(){
	$("#diseaseItemModal_update").modal("show");
	$("#diseaseItemModal_update").css("z-index",1080);
}
function getItem(){
	var diseaseCodes = "";
	var diseaseNames = "";
	$("input:checkbox[name='itemIdList']:checked").each(function(index, disease){
		if(index == 0){
			diseaseCodes = disease.value;
			diseaseNames = $("#" + disease.value + "_itemName").html();
		}else{
			diseaseCodes += "," + disease.value;
			diseaseNames += "、" + $("#" + disease.value + "_itemName").html();
		}
	});
	$("#diagnosisItemIds").val(diseaseCodes);
	$("#diagnosisItemNames").val(diseaseNames);
	$("#diseaseItemModal").modal("hide");
}
function getItem_update(){
	var diseaseCodes = "";
	var diseaseNames = "";
	$("input:checkbox[name='itemIdList_update']:checked").each(function(index, disease){
		if(index == 0){
			diseaseCodes = disease.value;
			diseaseNames = $("#" + disease.value + "_itemName_update").html();
		}else{
			diseaseCodes += "," + disease.value;
			diseaseNames += "、" + $("#" + disease.value + "_itemName_update").html();
		}
	});
	$("#diagnosisItemIds_update").val(diseaseCodes);
	$("#diagnosisItemNames_update").val(diseaseNames);
	$("#diseaseItemModal_update").modal("hide");
}
</script>
<!-- 适合诊断项目（添加） -->
<div id="diseaseModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		<div class="modal-body">
				<div class="panel panel-lightblue margin-zero">
					<div class="panel-heading text-center"><i class="fa fa-search"></i>选择接诊项目</div>
				<div class="row">
					<div class="col-xs-12 form-horizontal">
						<div class="panel panel-default margin-zero">
							<div class="panel-body">
								<c:forEach items="${diseaseList }" var="list" varStatus="i">							
								<div class="form-group">
									<div class="col-xs-12">
										<span class="label-title" id="${list.type }_diseaseType" style="font-size: 14px;"></span>
										<script>$("#${list.type }_diseaseType").html("◆ &nbsp;"+CODE.transCode("DISEASETYPE", "${list.type }"));</script>
									</div>
									<div class="col-xs-11 col-xs-offset-1">
									<c:forEach items="${list.interveneDiseaseList }" var="disease">
										<div class="col-xs-4 no-left">
											<label class="checkbox-inline">
												<input type="checkbox" name="diseaseIdList" value="${disease.diseaseCode }"/>
												<span id="${disease.diseaseCode }_diseaseName">${disease.diseaseName }</span>
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
			</div>
			<div class="panel-body padding-zero" style="padding-bottom: 15px;padding-right: 15px;">
				<div class="col-xs-2 col-xs-offset-8 no-right">
					<button type="button" class="btn btn-primary btn-block" onclick="getDisease();">确认</button>
				</div>
				<div class="col-xs-2 no-right">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 适合诊断项目（添加） END-->
<!-- 适合诊断项目（修改） -->
<div id="diseaseModal_update" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		<div class="modal-body">
				<div class="panel panel-lightblue margin-zero">
					<div class="panel-heading text-center"><i class="fa fa-search"></i>选择接诊项目</div>
				<div class="row">
					<div class="col-xs-12 form-horizontal">
						<div class="panel panel-default margin-zero">
							<div class="panel-body">
								<c:forEach items="${diseaseList }" var="list" varStatus="i">							
								<div class="form-group">
									<div class="col-xs-12">
										<span class="label-title" id="${list.type }_diseaseType_update" style="font-size: 14px;"></span>
										<script>$("#${list.type }_diseaseType_update").html("◆ &nbsp;"+CODE.transCode("DISEASETYPE", "${list.type }"));</script>
									</div>
									<div class="col-xs-11 col-xs-offset-1">
									<c:forEach items="${list.interveneDiseaseList }" var="disease">
										<div class="col-xs-4 no-left">
											<label class="checkbox-inline">
												<input type="checkbox" name="diseaseIdList_update" value="${disease.diseaseCode }"/>
												<span id="${disease.diseaseCode }_diseaseName_update">${disease.diseaseName }</span>
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
			</div>
			<div class="panel-body padding-zero" style="padding-bottom: 15px;padding-right: 15px;">
				<div class="col-xs-2 col-xs-offset-8 no-right">
					<button type="button" class="btn btn-primary btn-block" onclick="getDisease_update();">确认</button>
				</div>
				<div class="col-xs-2 no-right">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 适合诊断项目（修改） END-->

<!-- 禁用诊断项目（添加）-->
<div id="diseaseItemModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		<div class="modal-body">
				<div class="panel panel-lightblue margin-zero">
					<div class="panel-heading text-center"><i class="fa fa-search"></i> 选择诊断项目</div>
				<div class="row">
					<div class="col-xs-12 form-horizontal">
						<div class="panel panel-default margin-zero">
							<div class="panel-body">
								<c:forEach items="${diseaseItemList }" var="list" varStatus="i">
								<div class="form-group">
									<div class="col-xs-12">
										<span class="label-title" id="${list.type }_itemType" style="font-size: 14px;"></span>
										<script>$("#${list.type }_itemType").html("◆ &nbsp;"+CODE.transCode("DISEASETYPE", "${list.type }"));</script>
									</div>
									<div class="col-xs-11 col-xs-offset-1">
									<c:forEach items="${list.interveneDiseaseList }" var="disease">
										<div class="col-xs-4 no-left">
											<label class="checkbox-inline">
												<input type="checkbox" name="itemIdList" value="${disease.diseaseCode }"/>
												<span id="${disease.diseaseCode }_itemName">${disease.diseaseName }</span>
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
			</div>
			<div class="panel-body padding-zero" style="padding-bottom: 15px;padding-right: 15px;">
				<div class="col-xs-2 col-xs-offset-8 no-right">
					<button type="button" class="btn btn-primary btn-block" onclick="getItem();">确认</button>
				</div>
				<div class="col-xs-2 no-right">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 禁用诊断项目（添加） END-->

<!-- 禁用诊断项目（修改）-->
<div id="diseaseItemModal_update" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
		<div class="modal-body">
				<div class="panel panel-lightblue margin-zero">
					<div class="panel-heading text-center"><i class="fa fa-search"></i> 选择诊断项目</div>
				<div class="row">
					<div class="col-xs-12 form-horizontal">
						<div class="panel panel-default margin-zero">
							<div class="panel-body">
								<c:forEach items="${diseaseItemList }" var="list" varStatus="i">
								<div class="form-group">
									<div class="col-xs-12">
										<span class="label-title" id="${list.type }_itemType_update" style="font-size: 14px;"></span>
										<script>$("#${list.type }_itemType_update").html("◆ &nbsp;"+CODE.transCode("DISEASETYPE", "${list.type }"));</script>
									</div>
									<div class="col-xs-11 col-xs-offset-1">
									<c:forEach items="${list.interveneDiseaseList }" var="disease">
										<div class="col-xs-4 no-left">
											<label class="checkbox-inline">
												<input type="checkbox" name="itemIdList_update" value="${disease.diseaseCode }"/>
												<span id="${disease.diseaseCode }_itemName_update">${disease.diseaseName }</span>
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
			</div>
			<div class="panel-body padding-zero" style="padding-bottom: 15px;padding-right: 15px;">
				<div class="col-xs-2 col-xs-offset-8 no-right">
					<button type="button" class="btn btn-primary btn-block" onclick="getItem_update();">确认</button>
				</div>
				<div class="col-xs-2 no-right">
					<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 禁用诊断项目（修改） END-->