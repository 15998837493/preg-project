<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%@ include file="/common/common.jsp" %>
    <script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/items/intervene_disease.js"></script>
    <title>疾病库</title>
</head>
<script type="text/javascript">
//按钮状态
var operateType;
//dataTable 配置
var diseaseData;
var diseaseRow;
var diseaseTable;

var diseaseTableOption={
    id:"diseaseListTable",
    form:"diseaseQuery",
    columns:[
        {"data":null,"sClass":"text-center",
            "render":function(data, type, row, meta) {
                return "<input type='radio' name='tableRadio' value='"+data.diseaseId+"' />";
            }
        },
        {"data":"diseaseCode","sClass":"text-center"},
        {"data":"diseaseName","sClass":"text-center"},
        {"data":"diseaseType","sClass":"text-center",
            "render":function(data, type, row, meta) {
                return CODE.transCode("DISEASETYPE",data);
            }
        },
        {"data":"diseaseIcd10","sClass":"text-center"}
    ],
    rowClick:function(data,row){
        diseaseData = data;
        diseaseRow = row;
        $("#diseaseId").val(data.diseaseId);
        $("#id").val(data.diseaseId);
    },
    condition:"interveneDiseaseCondition",
    /* selecttarget:[3], */
};

$().ready(function() {
    //初始化食物种类
    common.initCodeSelect("DISEASETYPE","DISEASETYPE","diseaseType");
    common.initCodeSelect("DISEASETYPE","DISEASETYPE","diseaseTypeQuery");
    common.initCodeSelect("TRUEORFALSE","TRUEORFALSE","diseaseTreatmentItem");
    //加载dataTable
    diseaseTable = datatable.table(diseaseTableOption);
    //验证
    validator = $("#editDiseaseForm").validate(diseaseVaild);
    common.requiredHint("editDiseaseForm",diseaseVaild);
    
    $("button[name='intakeTemplatePage']").click(function(){
        var diseaseId = $("#id").val();
        if(this.id == 'addintakeTemplatePage'){
            operateType="add";
            $("#addOrUpdate").html("增加");
            $("#editDiseaseForm").attr("action", URL.get("item.ADD_INTERVENEDISEASE"));
            common.clearForm("editDiseaseForm");
            $("#diseaseTreatmentItem").val("2");
            $("#editDiseaseModal").modal("show");
        }
        if(this.id == 'editintakeTemplatePage' && isChoose(diseaseId)){
            operateType = "update";
            $("#addOrUpdate").html("编辑");
            $("#editDiseaseForm").attr("action", URL.get("item.UPDATE_INTERVENEDISEASE"));
            common.clearForm("editDiseaseForm");
            $("#editDiseaseForm").jsonToForm(diseaseData);
            //$("#diseaseOldCode").val($("#diseaseCode").val()); 
            $("#diseaseOldName").val($("#diseaseName").val());
            $("#editDiseaseModal").modal("show");
        }
        if(this.id == 'removeintakeTemplatePage' && isChoose(diseaseId)){
            removeClick(diseaseId);
        }
        if(this.id == 'searchButton'){
            diseaseTable = datatable.table(diseaseTableOption);
        }
    });
});

function isChoose(id){
    if(common.isEmpty(id)){
        layer.msg('请选择操作的记录');
        return false;
    }else{
        return true;
    }
}
</script>
<body>
<input id="id" type="hidden"/>
<form id="editDiseaseForm" name="editItemForm" action="" class="form-horizontal" method="post">
    <div id="editDiseaseModal" class="modal fade bs-example-modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="panel panel-lightblue ">
                        <div class="panel-heading text-left">
                       		<i class="fa fa-edit fa-fw"></i>诊断项目<span id="addOrUpdate"></span>
                       		<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
                       	</div>
                        <div class="row">
                            <div class="col-xs-11 row-top col-xs-offset-1">
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">分类</label>
                                    <div class="col-xs-6">
                                        <select id="diseaseType" name="diseaseType" class="form-control"></select>
                                    </div>
                                </div>
                                 <!-- <div class="form-group">
                                    <label class="col-xs-3 control-label">代码</label>
                                    <div class="col-xs-6">
                                        <input id="diseaseId" name="diseaseId" type="hidden"/>
                                        <input id="diseaseCode" name="diseaseCode" class="form-control" maxlength="50" type="text"/>
                                        <input id="diseaseOldCode" name="diseaseOldCode" type="hidden"/>
                                    </div>
                                </div>  -->
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">疾病名称</label>
                                    <div class="col-xs-6">
                                    	<input id="diseaseId" name="diseaseId" type="hidden"/>
                                        <input id="diseaseName" name="diseaseName" class="form-control" maxlength="50" type="text"/>
                                    	<input id="diseaseOldName" name="diseaseOldName" class="form-control" maxlength="50" type="hidden"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-xs-3 control-label">ICD10编码</label>
                                    <div class="col-xs-6">
                                        <input id="diseaseIcd10" name="diseaseIcd10" class="form-control" maxlength="50" type="text"/>
                                    </div>
                                </div>
                               <!--  <div class="form-group">
                                    <label class="col-xs-3 control-label">营养治疗项目</label>
                                    <div class="col-xs-6">
                                        <select id="diseaseTreatmentItem" name="diseaseTreatmentItem" class="form-control"></select>
                                    </div>
                                </div> -->
<!--                                 <div class="form-group ">
	                                <label class="col-xs-3 control-label"></label>
	                                <div class="col-xs-6">
	                                       	<label class='radio-inline'>
	                                       		<input  name="diagnosisIsPermanent" type="radio" value="0"/>
	                                       		<span>是否营养状态诊断</span>
	                                       	</label>
	                                    	<label class='radio-inline'>
	                                    		<input  name="diagnosisIsPermanent" type="radio" value="1"/>
	                                    		<span>是否营养相关疾病诊断</span>
	                                    	</label>
	                                </div>
                                </div> -->
                            </div>
                        </div>
                    </div>
                    <div class="panel-body text-right" style="padding: 0px;">
                        <div class="col-xs-2 col-xs-offset-10 no-right">
                            <button type="submit" class="btn btn-primary btn-block">保存</button>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form>
<div class="row ">
    <div class="panel-body form-inline" id="interveneDiseaseCondition">
        <form id="diseaseQuery" action="${common.basepath }/${applicationScope.URL.item.QUERY_INTERVENEDISEASE}">
           <input type='text' id='diseaseName' name="diseaseName" class='form-control' placeholder='代码/名称'/>
           <!-- <select id="diseaseTypeQuery" name="diseaseTypeQuery" class="form-control"></select> -->
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
        </form>
    </div>
</div>
<div class="table-responsive">
    <table id="diseaseListTable" class="table table-bordered table-hover">
        <thead>
        <tr class="active">
            <th class="text-center">选择</th>
            <th class="text-center">编码</th>
            <th class="text-center">名称</th>
            <th class="text-center">类别</th>
            <th class="text-center">ICD10编码</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>