<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="/common/common.jsp" %>
    <title>查询诊疗客户</title>
    <script type="text/javascript">
        //转诊医生信息
        var referralListJson = ${referralListJson};
        var referralMap = new Map();
        if (!_.isEmpty(referralListJson)) {
            $.each(referralListJson, function (index, value) {
                referralMap.set(value.doctorId, value);
            });
        }

        //评价项目信息
        var inspectListJson = ${inspectListJson};
        var inspectListData = [];
        if (!_.isEmpty(inspectListJson)) {
            $.each(inspectListJson, function (index, value) {
                inspectListData.push({name: value.inspectName, val: value});
            });
        }

        //诊断项目信息
        var diseaseListJson = ${diseaseListJson};
        var diseaseListData = [];
        var diseaseMap = new Map();
        if (!_.isEmpty(diseaseListJson)) {
            $.each(diseaseListJson, function (index, value) {
                diseaseListData.push({name: value.diseaseName, val: value});
                diseaseMap.set(value.diseaseId, value);
            });
        }

        //个人默认评价项目信息
        var userInspectListJson;

    </script>
    <script type="text/javascript" src="${common.basepath}/page/evaluate/evaluate_customer_view.js"></script>
    <style type="text/css">
        .label-checkbox {
            position: relative;
            display: inline-block;
            padding-left: 26px;
            margin-bottom: 0;
            vertical-align: middle;
            font-weight: normal;
            cursor: pointer;
        }

        #inspect-checkbox {
            position: absolute;
            margin-left: -18px;
        }
    </style>
</head>
<body>
<websocket userName="${loginUserId }" ws="yes"></websocket>
<!-- 登记 -->
<div class="row">
    <div class="panel-body form-inline" id="customerCondition">
        <form id="customerRegisterQuery" action="${common.basepath }/${applicationScope.URL.Customer.QUERY_CUST_DIAGNOSIS_REGISTER}" method="post" class="form-horizontal">
            <input class="form-control" type="text" id="my_content" name="content" placeholder="病案号/ID/姓名/身份证号" style="width: 16%"/>
            <button type="button" id="searchButton" name="diagCustButton" class="btn btn-default">
                <i class="fa fa-search fa-fw"></i>查询
            </button>
            <div class="vertical-line"></div>
            <button id="addButton" name="diagCustButton" type="button" class="btn btn-default">
                <i class="fa fa-plus fa-fw"></i> 首诊登记
            </button>
        </form>
    </div>
</div>

<!-- 未接诊列表 -->
<div id="diagCustCondition">
    <form id="customerQuery" action="${common.basepath }/${applicationScope.URL.Platform.DIAGNOSIS_QUERY_MORE_EVALUATE}" method="post" class="form-horizontal">
        <input type="hidden" name="diagnosisAssistantStatus" value="1"/>
    </form>
</div>
<table id="diagCustListTable" class="table table-bordered table-hover ">
    <thead>
    <tr class="active">
        <th class="text-center" style="width: 8%;">序号</th>
        <th class='text-center' style="display: none">是否有接诊历史</th>
        <th class='text-center' style="width: 14%;">病案号</th>
        <th class='text-center' style="width: 14%;">ID</th>
        <th class='text-center' style="width: 14%;">姓名</th>
        <th class='text-center' style="width: 10%;">年龄</th>
        <th class='text-center' style="width: 10%;">阶段</th>
        <th class='text-center' style="width: 15%;">接诊医生</th>
        <th class='text-center' style="width: 15%;">进度</th>
    </tr>
    </thead>
</table>

<div class="panel-body" id="admissionsCondition">
    <form id="admissionsQuery" action="${common.basepath }/${applicationScope.URL.Platform.DIAGNOSIS_QUERY_MORE_EVALUATE}" method="post" class="form-horizontal">
        <input type="hidden" name="diagnosisAssistantStatusNot" value="1"/>
    </form>
</div>
<table id="admissionsTable" class="table table-bordered table-hover">
    <thead>
    <tr class="active">
        <th class="text-center" style="width: 8%;">序号</th>
        <th class='text-center' style="display: none">是否有接诊历史</th>
        <th class='text-center' style="width: 14%;">病案号</th>
        <th class='text-center' style="width: 14%;">ID</th>
        <th class='text-center' style="width: 14%;">姓名</th>
        <th class='text-center' style="width: 10%;">年龄</th>
        <th class='text-center' style="width: 10%;">阶段</th>
        <th class='text-center' style="width: 15%;">接诊医生</th>
        <th class='text-center' style="width: 15%;">进度</th>
    </tr>
    </thead>
</table>

<!-- 首诊登记Modal -->
<form id="addCustomerForm" name="addCustomerForm" action="${common.basepath }/${applicationScope.URL.Platform.REGISTRATION_ADD}" class="form-horizontal" method="post">
    <div id="addModal" class="modal fade bs-example-modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left">
                            <i class="fa fa-plus fa-fw"></i>首诊登记
                            <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
                        </div>
                        <div class="panel-body">
                            <div class="form-group row-top">
                                <label class="col-xs-4 control-label">病案号</label>
                                <div class="col-xs-5">
                                    <input id="custSikeId" name="custSikeId" class="form-control" type="text" maxlength="80"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">ID</label>
                                <div class="col-xs-5">
                                    <input id="custPatientId" name="custPatientId" class="form-control" type="text" maxlength="80"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">姓名</label>
                                <div class="col-xs-5">
                                    <input type="hidden" id="diagnosisStatus" name="diagnosisStatus" value="1"/>
                                    <input type="hidden" id="diagnosisType" name="diagnosisType" value="1"/>
                                    <input type="hidden" id="diagnosisOrg" name="diagnosisOrg" value="孕期营养门诊"/>
                                    <input type="hidden" id="custSex" name="custSex" value="female"/>
                                    <input type="hidden" id="inspectListJson" name="inspectListJson"/>
                                    <input type="hidden" id="diseaseListJson" name="diseaseListJson"/>
                                    <input type="hidden" id="custBirthday" name="custBirthday"/>
                                    <input id="custName" name="custName" class="form-control" type="text" maxlength="80"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">身份证号</label>
                                <div class="col-xs-5">
                                    <input id="custIcard" name="custIcard" class="form-control" type="text" onblur="initBirthSex(this.value)" maxlength="64"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">接诊医生</label>
                                <div class="col-xs-5">
                                    <input type="hidden" id="diagnosisUserName" name="diagnosisUserName"/>
                                    <select id="diagnosisUser" name="diagnosisUser" class="form-control" onchange="getDoctorInspectListJson(this.value);">
                                        <option value="">请选择接诊医生</option>
                                        <c:forEach items="${userAssistantList }" var="list">
                                            <option value="${list.doctorId }">${list.doctorName }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">转诊医生</label>
                                <div class="col-xs-5">
                                    <input type="hidden" id="diagnosisReferralDoctorName" name="diagnosisReferralDoctorName"/>
                                    <select id="diagnosisReferralDoctor" name="diagnosisReferralDoctor" class="form-control" onchange="getDoctorDepartmentName(this.value);">
                                        <option value="">请选择转诊医生</option>
                                        <c:forEach items="${referralList }" var="list">
                                            <option value="${list.doctorId }">${list.doctorName }</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-xs-3 no-left" style="margin-top: 6px;">
                                    <span id="deptNameSpan"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label">就诊目的</label>
                                <div class="col-xs-5">
                                    <input id="inspectItem" class="form-control" type="text" placeholder="请输入营养评价项目"/>
                                    <!-- <div class="input-group">
                                        <input id="inspectItem" class="form-control" type="text" placeholder="请输入营养评价项目"/>
                                        <span class="input-group-btn">
                                            <button type="button" class="btn btn-primary" onclick="showDefaultInspectModal();">默认评价项目</button>
                                        </span>
                                  </div> -->
                                    <table class="table table-bordered table-padding-4" style="margin-top: 5px;margin-bottom: 0px;" id="inspectTable"></table>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-4 control-label"></label>
                                <div class="col-xs-5">
                                    <input id="diseaseItem" class="form-control" type="text" placeholder="请输入疾病名称"/>
                                    <table class="table table-bordered table-padding-4" style="margin-top: 5px;" id="diseaseTable"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body padding-zero">
                        <div class="col-xs-2 col-xs-offset-10 no-right">
                            <button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i>保存</button>
                        </div>
                    </div>
                </div>
            </div><!--/.modal-content -->
        </div><!--/.modal-dialog -->
    </div><!-- /.modal -->
</form>

<!-- 随诊登记Modal -->
<div id="editModal" class="modal fade bs-example-modal">
    <div class="modal-dialog modal-lg" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left">
                        <i class="fa fa-search fa-fw"></i>患者登记
                        <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
                    </div>
                    <div class="table-responsive" style="padding: 0px;">
                        <table id="custListTable" class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th style="display: none">编码</th>
                                <th>病案号</th>
                                <th>ID</th>
                                <th>姓名</th>
                                <th>年龄</th>
                                <th>身份证号</th>
                                <th>营养诊疗次数</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div><!--/.modal-content -->
    </div><!--/.modal-dialog -->
</div><!-- /.modal -->

<!-- 随诊登记选择转诊医生Modal -->
<div id="editDiagnosisUserModal" class="modal fade bs-example-modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left">
                        <i class="fa fa-plus fa-fw"></i>随诊登记
                        <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
                    </div>
                    <div class="panel-body form-horizontal">
                        <div class="form-group row-top">
                            <label class="col-xs-4 control-label">接诊医生</label>
                            <input type="hidden" id="custIdEdit"/>
                            <div class="col-xs-5">
                                <select id="diagnosisUserEdit" name="diagnosisUserEdit" class="form-control">
                                    <c:forEach items="${userAssistantList }" var="list">
                                        <option name="diagnosisUserEdit" value="${list.doctorId }">${list.doctorName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body padding-zero">
                    <div class="col-xs-2 col-xs-offset-10 no-right">
                        <button class="btn btn-primary btn-block" type="button" onclick="editDiagnosis();"><i class="fa fa-save fa-fw"></i>保存</button>
                    </div>
                </div>
            </div>
        </div><!--/.modal-content -->
    </div><!--/.modal-dialog -->
</div><!-- /.modal -->

<!-- 复诊登记Modal -->
<div id="vistModal" class="modal fade bs-example-modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left">
                        <i class="fa fa-plus fa-fw"></i>复诊登记
                        <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
                    </div>
                    <div class="panel-body form-horizontal">
                        <div class="form-group row-top">
                            <label class="col-xs-4 control-label">接诊医生</label>
                            <div class="col-xs-5">
                                <select id="diagnosisUserVist" name="diagnosisUserVist" class="form-control">
                                    <c:forEach items="${userAssistantList }" var="list">
                                        <option name="diagnosisUserVist" value="${list.doctorId }">${list.doctorName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">转诊医生</label>
                            <div class="col-xs-5">
                                <input type="hidden" id="custIdVist"/>
                                <select id="diagnosisReferralDoctorVist" name="diagnosisReferralDoctor" class="form-control" onchange="getDoctorDepartmentNameVist(this.value);">
                                    <option value="">请选择转诊医生</option>
                                    <c:forEach items="${referralList }" var="list">
                                        <option value="${list.doctorId }">${list.doctorName }</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-xs-3 no-left" style="margin-top: 6px;">
                                <span id="deptNameSpanVist"></span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label">就诊目的</label>
                            <div class="col-xs-5">
                                <input id="inspectItemVist" class="form-control" type="text" placeholder="请输入营养评价项目"/>
                                <!-- <div class="input-group">
                                    <input id="inspectItemVist" class="form-control" type="text" placeholder="请输入营养评价项目"/>
                                    <span class="input-group-btn">
                                        <button type="button" class="btn btn-primary" onclick="showDefaultInspectModal();">默认评价项目</button>
                                    </span>
                              </div> -->
                                <table class="table table-bordered table-padding-4" style="margin-top: 5px;margin-bottom: 0px;" id="inspectTableVist"></table>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-4 control-label"></label>
                            <div class="col-xs-5">
                                <input id="diseaseItemVist" class="form-control" type="text" placeholder="请输入疾病名称"/>
                                <table class="table table-bordered table-padding-4" style="margin-top: 5px;" id="diseaseTableVist"></table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body padding-zero">
                    <div class="col-xs-2 col-xs-offset-10 no-right">
                        <button class="btn btn-primary btn-block" type="button" onclick="vistDiagnosis();"><i class="fa fa-save fa-fw"></i>保存</button>
                    </div>
                </div>
            </div>
        </div><!--/.modal-content -->
    </div><!--/.modal-dialog -->
</div><!-- /.modal -->

<!-- 默认项目Modal -->
<div id="defaultInspectModal" class="modal fade bs-example-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left">
                        <i class="fa fa-edit fa-fw"></i> 编辑默认项目
                        <a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
                    </div>
                    <div class="panel-body">
                        <div class="form-group row-top">
                            <div class="col-xs-8 col-xs-offset-2">
                                <input id="inspectItem2" class="form-control" type="text" placeholder="请输入营养评价项目"/>
                                <table class="table table-bordered table-padding-4" style="margin-top: 5px;" id="inspectTable2"></table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body padding-zero">
                    <div class="col-xs-2 col-xs-offset-10 no-right">
                        <button class="btn btn-primary btn-block" type="button" onclick="saveUserInspectItem();"><i class="fa fa-save fa-fw"></i>保存</button>
                    </div>
                </div>
            </div>
        </div><!--/.modal-content -->
    </div><!--/.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
