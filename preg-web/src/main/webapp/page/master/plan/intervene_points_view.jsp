<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<style>
/* .margin-zero{ */
/* 	margin: 0px; */
/* } */
/* .padding-zero{ */
/* 	padding: 0px; */
/* } */
</style>
<title>健康要点信息</title>
</head>
<script type="text/javascript">
/**
 * 验证参数设置
 */
var intakeTemplateOptions = {
	rules: {
		pointDesc:{
			required:true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function (data) {
            if (data) {
				$(form).ajaxPost(dataType.json,function(data){
					if(operateType == "add"){
						datatable.add(pointTable, data.value);// 添加
						$("#editPointModal").modal("hide");
					} else if(operateType == "update"){
						datatable.update(pointTable, data.value, pointRow);// 修改
						$("#editPointModal").modal("hide");
					}
				});
            }
        });
	}
};

/**
 * 删除提交form请求
 */
function removeClick(id){
	layer.confirm("确定对选中内容执行【删除】操作？", function (data) {
		if (data) {
			var url = URL.get("Master.REMOVE_DIET_TENET");
			var params = "id=" + id;
			ajax.post(url, params, dataType.json, function(data){
				datatable.remove(pointTable, pointRow);// 删除
			});
       }
   });
};
//dataTable配置
var pointRow;
var pointData;
var pointTable;

var pointOption = {
		id:"pointListTable",
		form:"familyQuery",
		columns: [
	  		{"data": null,"sClass": "text-center",
	  			"render":  function (data, type, row, meta) {
	          		return "<input type='radio' name='tableRadio' value='"+data.pointId+"' />";
	          	}
	  		},
	  		{"data": null,"sClass": "text-center",
	  			"render":  function (data, type, row, meta) {
	          		return meta.row+1;
	          	}
		  	},
	  		{"data": null,"sClass": "text-left",
	  			"render":  function (data, type, row, meta) {
	  				return pregStageNameMethod(data.pregStage);
	          	}
		  	},
	  		{"data": "interveneDiseaseNames","sClass": "text-left" },
// 	  		{"data": "pointOrder","sClass": "text-center" },
	  		{"data": null,"sClass": "text-left",
				"render":function(data, type, row, meta){
					if(data.pointDesc){
						return "<a id='mark_"+data.pointId+"'" + 
					       "   data-toggle='tooltip'" + 
					       "   data-title='" + data.pointDesc + "'" + " data-placement='auto'" +
					       "   onmouseover='showToolTipContent(\""+data.pointId+"\", \"mark_\");'>详情</a>" + 
					       "<span style='display: none;'>"+data.pointDesc+"</span>";
					}
				}
		  	}
		],
	  	rowClick: function(data, row){
	  		pointData = data;
	  		pointRow = row;
	  		$("#pointId").val(data.pointId);
	  	},
	  	condition : "pointCondition"
};

	$().ready(function() {
		//默认是孕期阶段，将其他输入框隐藏
		$("#diseaseDiv").hide();
		$("#titleDiv").hide();
		
		//必填验证
		$("#editPointForm").validate(intakeTemplateOptions);
		common.requiredHint("editPointForm",intakeTemplateOptions);
		//初始化下拉选
		common.initCodeCheckbox("PREGNANT_STAGE","PREGNANT_STAGE","pregStageModel","pregStageModel",4);
		//加载dataTable;
		pointTable = datatable.table(pointOption);

		$("input:radio[name='type']").change(function(){
			if($(this).val() == 1){
				$("#diseaseDiv").hide();
				$("#titleDiv").hide();
				$("#pergDiv").show();
			}else{
				$("#diseaseDiv").show();
				$("#titleDiv").show();
				$("#pergDiv").hide();
			}
		});

		$("button[name='intakeTemplatePage']").click(function(){
			var pointId = $("#pointId").val();
			if(this.id == 'addintakeTemplatePage'){
				operateType = "add";
				$("#editPointForm").attr("action", URL.get("Master.ADD_DIET_TENET"));
				common.clearForm("editPointForm");
				$("input:radio[name='type'][value='"+ 1 + "']").attr("checked", true);				
				$("#diseaseDiv").hide();
				$("#titleDiv").hide();
				$("#pergDiv").show();
				$("input:checkbox[name='diseaseIdList']").attr("checked", false);
				$("#editPointModal").modal("show");
			}
			if(this.id == 'editintakeTemplatePage' && isChoose(pointId)){
				operateType = "update";
				$("#editPointForm").attr("action", URL.get("Master.UPDATE_DIET_TENET"));
				common.clearForm("editPointForm");
				$("#editPointForm").jsonToForm(pointData);
				//配置孕期阶段和诊断项目
				$("#pregStage").val(pointData.pregStage);
				$("#pregStageNames").val(pregStageNameMethod(pointData.pregStage));
				$("#diagnosisDiseaseIds").val(pointData.interveneDiseaseIds);
				$("#diagnosisDiseaseNames").val(pointData.interveneDiseaseNames);

				if(common.isEmpty(pointData.pregStage)){
					$("input:radio[name='type'][value='"+ 2 + "']").attr("checked", true);
					$("#diseaseDiv").show();
					$("#titleDiv").show();
					$("#pergDiv").hide();
					//勾选关联的疾病
					$(pointData.interveneDiseaseIds.split(",")).each(function(index, diseaseCode) {
						$("input:checkbox[name='diseaseIdList'][value='"+ diseaseCode + "']").attr("checked", true);
					});
				}else if(common.isEmpty(pointData.interveneDiseaseIds)){
					$("input:radio[name='type'][value='"+ 1 + "']").attr("checked", true);
					$("#diseaseDiv").hide();
					$("#titleDiv").hide();
					$("#pergDiv").show();
					//勾选关联的孕期
					$(pointData.pregStage.split(",")).each(function(index, pregStage) {
						$("input:checkbox[name='pregStageModel'][value='"+ pregStage + "']").attr("checked", true);
					});
				}
				$("#editPointModal").modal("show");
			}
			if(this.id == 'removeintakeTemplatePage' && isChoose(pointId)){
				removeClick(pointId);
			}
			if(this.id == 'searchButton'){
				//pointTable = datatable.table(pointOption);
				datatable.search(pointTable, pointOption);
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

	//疾病弹窗
	function chooseDiseaseModal(){
		$("#diseaseModal").modal("show");
	}
	//获取勾选的疾病
	function getDisease(){
		var diseaseIds = "";
		var diseaseNames = "";
		$("input:checkbox[name='diseaseIdList']:checked").each(function(index, disease){
			if(index == 0){
				diseaseIds = disease.value;
				diseaseNames = $("#" + disease.value + "_diseaseName").html();
			}else{
				diseaseIds += "," + disease.value;
				diseaseNames += "、" + $("#" + disease.value + "_diseaseName").html();
			}
		});
		$("#diagnosisDiseaseIds").val(diseaseIds);
		$("#diagnosisDiseaseNames").val(diseaseNames);

		$("#diseaseModal").modal("hide");
	}

	//处理孕期字符串,把code转化成value
	function pregStageNameMethod(pregString){
		if(!common.isEmpty(pregString)){
  			var pregStageName = "";
 			$(pregString.split(",")).each(function(index, stage) {
 				if(index == 0){
 					pregStageName = CODE.transCode("PREGNANT_STAGE",stage);
 				}else{
 					pregStageName += "," + CODE.transCode("PREGNANT_STAGE",stage);
 				}
 			});
         	return pregStageName;
  		}
		return "";
	}

	//表单的处理和提交
	function editPointFormSubmit(){
		var pregStageIds = "";
		$("input:checkbox[name='pregStageModel']:checked").each(function(index, pregStage){
			if(index == 0){
				pregStageIds += pregStage.value;
			}else{
				pregStageIds += "," + pregStage.value;
			}
		});
		$("#pregStage").val(pregStageIds);

		//根据单选按钮清空部分内容
		if($("input:radio[name='type']:checked").val() == "1"){
			$("#diagnosisDiseaseIds").val("");
			$("#diagnosisDiseaseNames").val("");
			$("#pointExample").val("");
			if(common.isEmpty($("#pregStage").val())){
           	    layer.alert("请选择孕期阶段");
           	    return;
			}
		}
		if($("input:radio[name='type']:checked").val() == "2"){
			$("#pregStage").val("");
			if(common.isEmpty($("#diagnosisDiseaseIds").val()) && common.isEmpty($("#diagnosisDiseaseNames").val())){
           	    layer.alert("请选择诊断项目");
           	    return;
			}
/* 			if(common.isEmpty($("#diagnosisDiseaseNames").val())){
           	    layer.alert("请选择诊断项目");
           	    return;
			}	 */		
		}
		
		$("#editPointForm").submit();
	}

	/**
	 * 显示信息内容
	 * 
	 * @param intakeId
	 * @param type
	 */
	function showToolTipContent(intakeId, type){
		$("#" + type + intakeId).tooltip("show");
	}
</script>
<body>
<!-- 弹窗添加、编辑 -->
<form id="editPointForm" name="editItemForm" action="" class="form-horizontal" method="post">
<input type="hidden" id="pointId" name="pointId" />
<div id="editPointModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left">干预方案编辑
					<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
					<div class="panel-body form-horizontal">
						<div class="form-group">
							<label class="col-xs-2 control-label">课程类别</label>
							<div class="col-xs-9">
								<div class="col-xs-4 no-left">
									<label class="checkbox-inline" style="padding-left: 15px;"><input name="type" value="1" type="radio">&nbsp;孕期阶段原则</label>
								</div>
								<div class="col-xs-4 no-left">
									<label class="checkbox-inline" style="padding-left: 15px;"><input name="type" value="2" type="radio">&nbsp;诊断原则</label>
								</div>
							</div>
						</div>
                        <div class="form-group" id="pergDiv">
                            <label class="col-xs-2 control-label">孕期阶段</label>
                            <div class="col-xs-9">
                                <input id="pregStage" name="pregStage" type="hidden"/>
                                <div id="pregStageModel"></div>
                            </div>
                        </div>
                        <div class="form-group" id="diseaseDiv">
                            <label class="col-xs-2 control-label">诊断项目</label>
                            <div class="col-xs-9">
					 	   		<input type="hidden" id="diagnosisDiseaseIds" name="interveneDiseaseIds"/>
						        <textarea id="diagnosisDiseaseNames" 
						                  name="interveneDiseaseNames" 
						                  class="form-control" 
						                  onclick="chooseDiseaseModal();" 
						                  placeholder="点击选择诊断项目" 
						                  readonly 
						                  style="background-color: white;margin-left: 13px;"></textarea>
                            </div>
                        </div>
                        <div class="form-group" id="titleDiv">
                            <label class="col-xs-2 control-label">标题</label>
                            <div class="col-xs-9">
                                <input id="pointSubclass" name="pointSubclass" class="form-control" maxlength="50" type="text" style="margin-left: 13px;"/>
                            </div>
                        </div>   
                        <div class="form-group">
                            <label class="col-xs-2 control-label">内容</label>
                            <div class="col-xs-9">
						        <textarea id="pointDesc" 
						        		  maxlength="2000"
						                  name="pointDesc" 
						                  class="form-control" 
						                  rows="15"
						                  placeholder="添加内容" 
						                  style="background-color: white;margin-left: 13px;"></textarea>
                        	</div>
						</div>
					</div>
				</div>
<!-- 				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="editPointFormSubmit();">确认</button>
					</div>
					<div class="col-xs-2 no-right">
						<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
					</div>
				</div> -->
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="editPointFormSubmit();">确认</button>
					</div>
				</div>	
			</div>	
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>


<!-- 疾病与孕期阶段的Model -->
<div id="diseaseModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue margin-zero">
					<div class="panel-heading text-left"><i class="fa fa-search"></i>选择接诊项目
					<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
					</div>
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
													<input type="checkbox" name="diseaseIdList" value="${disease.diseaseId }"/>
													<span id="${disease.diseaseId }_diseaseName">${disease.diseaseName }</span>
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
<!-- 				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-8 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="getDisease();">确认</button>
					</div>
					<div class="col-xs-2 no-right">
						<button type="button" class="btn btn-primary btn-block" data-dismiss="modal">取消</button>
					</div>
				</div> -->
				<div class="panel-body" style="padding: 0px;margin-top: 15px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button type="button" class="btn btn-primary btn-block" onclick="getDisease();">确认</button>
					</div>
				</div>				
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="row">
	<div class="panel-body">
   		<div id="pointCondition">
            <form id="familyQuery" name="familyQuery" action="${common.basepath}/${applicationScope.URL.Master.QUERY_DIET_TENET}" method="post" class="form-horizontal">
                  <div class="form-inline">
                       <input name="pointDesc" class="form-control" type="text" placeholder="请输入内容">
		      		<button type="button" id="searchButton" name="intakeTemplatePage" class="btn btn-default"><i class="fa fa-search fa-fw"></i>查询</button>
		      		<div class="vertical-line"></div>
			  		<button id="addintakeTemplatePage" name="intakeTemplatePage" type="button" class="btn btn-default">
			     		<i class="fa fa-plus fa-fw"></i> 增加
			  		</button>
		      		<button id="editintakeTemplatePage" name="intakeTemplatePage" type="button" class="btn btn-default">
			     		<i class="fa fa-edit fa-fw"></i> 编辑
			  		</button>
		      		<button id="removeintakeTemplatePage" name="intakeTemplatePage" type="button" class="btn btn-default">
			     		<i class="fa fa-remove fa-fw"></i> 删除
			  		</button>
                   </div>
           </form>
	    </div>
	</div>
</div>
<div class="table-responsive">
	<table id="pointListTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class="text-center" style="width: 5%">选择</th>
				<th class="text-center" style="width: 5%">序号</th>
				<th class="text-center" style="width: 20%">孕期阶段</th>
				<th class="text-center" style="width: 30%">诊断</th>
				<th class="text-center">内容</th>
		   	</tr>
		</thead>
	</table>
</div>
</body>
</html>