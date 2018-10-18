$.validator.addMethod("newMobile", function(value,element) {
	if(element.value == '' || (/\d{11}$/).test(element.value)){ 
		return true;
	}else{
		return false;
	}
}, '请输入正确的手机号');

var operner;
// 科室ID:科室名称
//var map = new Map();
var validator;
// 选中项信息
var dtableData;
// 选中行信息
var dtableRow;
// table对象
var dtableTable;
// 列表配置信息
var dtableOptions = {
    id: "dTable",
    form: "dtableQueryForm",
    columns: [{
        "data": null,
        "sClass": "text-center",
        "render": function(data, type, row, meta) {
            return "<input type='radio' name='tableRadio'  />";
        }
    },
    {
        "data": "doctorId",
        "sClass": "text-center"
    },
    {
        "data": "doctorName",
        "sClass": "text-center"
    },
    {
        "data": "doctorSex",
        "sClass": "text-center",
        "render": function(data, type, row, meta) {
            return CODE.transCode("SEX", data);
        }
    },
    {
        "data": "doctorDepartmentName",
        "sClass": "text-left"
    }],
    rowClick: function(data, row) {
        dtableData = data;
        dtableRow = row;
        $("#dietId").val(data.id);
    },
    condition: "dtableCondition"
};
// 配置表单校验参数
var editOptions = {
    rules: {
        doctorId: {
            required: true,
            remote: {
                url: URL.get("System.REFERRAL_DOCTOR_ICARD_VALIDATE"),
                // 后台处理程序
                type: "post",
                // 数据发送方式
                dataType: "json",
                // 接受数据格式
                data: { // 要传递的数据，默认已传递应用此规则的表单项
                    value: function() {
                        return $("#doctorId").val();
                    },
                    editFormType: function() {
                        return $("#editFormType").val();
                    },
                    property:"doctorId",
                    random: function() {
                        return Math.random();
                    }
                }
            }
        },
        doctorName: {
            required: true
        },
        doctorDepartmentId: {
            required: true
        },
        doctorIcard: {
            idcardno: true,
            remote: {
                url: URL.get("System.REFERRAL_DOCTOR_ICARD_VALIDATE"),
                // 后台处理程序
                type: "post",
                // 数据发送方式
                dataType: "json",
                // 接受数据格式
                data: { // 要传递的数据，默认已传递应用此规则的表单项
                    value: function() {
                        return $("#doctorIcard").val();
                    },
                    editFormType: function() {
                        return $("#editFormType").val();
                    },
                    property:"doctorIcard",
                    random: function() {
                        return Math.random();
                    }
                }
            }
        },
        doctorPhone: {
        	newMobile: true
        }
    },
    messages: {
        doctorIcard: {
            remote: "身份证号不能重复"
        },
        doctorId: {
        	remote: "医生工号不能重复"
        }
    },
    // 设置错误信息显示到指定位置
    errorPlacement: function(error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        var editFormType = $("#editFormType").val();
        if ("add" == editFormType) {
            $("#editForm").ajaxPost(dataType.json,
            function(data) {
                datatable.add(dtableTable, data.value);
                $("#editModal").modal("hide");
                $("#dietId").val("");
            });
        }
        if ("update" == editFormType) {
            $("#editForm").ajaxPost(dataType.json,
            function(data) {
            	layer.alert(data.message);
                datatable.update(dtableTable, data.value, dtableRow);
                $("#editModal").modal("hide");
                $("#dietId").val("");
                $("input[type='radio']").attr("checked", false);
                $("tr:gt(0)").removeClass("active");
            });
        }
        validator.resetForm();
    }
};

function getDietTemplate(dietId, dietName) {
    //	common.pageForward(URL.get("Master.PLAN_DIETTEMPLATEDETAIL_INIT") + "?dietId=" + dietId);
    common.pageForward(URL.get("Master.PLAN_DIETTEMPLATEDETAIL_INIT") + "?dietId=" + dietId + "&dietName=" + dietName);
}

function removeCookbook(dietId) {
    layer.confirm("确定对选中内容执行【删除】操作？",
    function(data) {
        if (data) {
            var url = URL.get("System.REFERRAL_DOCTOR_DELETE");
            var params = "id=" + dietId;
            ajax.post(url, params, dataType.json,
            function(data) {
                if (data.result) {
                    datatable.remove(dtableTable, dtableRow);
                    $("#dietId").val("");
                } else {
                    layer.alert(data.message);
                }
            });
        }
    });
}

function isChoose(dietId) {
    if (common.isEmpty(dietId)) {
        layer.msg('请选择要操作的记录');
        return false;
    } else {
        return true;
    }
}
// 按钮点击事件
$("button[name='operateBtn']").click(function() {
    if (this.id == 'search') {
        //dtableTable = datatable.table(dtableOptions);
        datatable.search(dtableTable, dtableOptions);
    }
    $("#editForm #dietType").attr("disabled", true);
    var dietId = $("#dietId").val();
    // 清空表单
    common.clearForm("editForm");
    if (this.id == 'add') {
        $("#editForm").attr("action", URL.get("System.REFERRAL_DOCTOR_ADD"));
        $("#editForm #doctorId").attr("readonly", false);
        // 记录操作类型
        $("#editFormType").val("add");
        $("#editModal").modal("show");
    }
    if (this.id == 'update' && isChoose(dietId)) {
        // 初始化数据
        $("#editForm").attr("action", URL.get("System.REFERRAL_DOCTOR_UPDATE"));
        $("#editForm #id").val(dtableData.id);
        $("#editForm #dietId").val(dtableData.doctorId);
        $("#editForm #doctorId").val(dtableData.doctorId);
        $("#editForm #doctorId").attr("readonly", true);
        $("#editForm #doctorName").val(dtableData.doctorName);
        $("#editForm #doctorDepartmentId").val(dtableData.doctorDepartmentId);
        $("#editForm #doctorIcard").val(dtableData.doctorIcard);
        $("#editForm #doctorSex").val(dtableData.doctorSex);
        $("#editForm #doctorBirthday").val(dtableData.doctorBirthday);
        $("#editForm #doctorPhone").val(dtableData.doctorPhone);
        $("#editForm #doctorJob").val(dtableData.doctorJob);
        // 记录操作类型
        $("#editFormType").val("update");
        $("#editModal").modal("show");
    }
    if (this.id == 'remove' && isChoose(dietId)) {
        removeCookbook(dietId);
    }
});

function isCardNo(card) {
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    if (reg.test(card) === false) {
        return false;
    } else {
        return true;
    }
}

$().ready(function() {
    // 初始化下拉框
    // 性别
    common.initCodeSelect("SEX", "SEX", "doctorSex", "", "请选择性别");
    // 科室
    $("#doctorDepartmentId").html("<option value=''>请选择科室</option>");
    var url = URL.get("referral.REFERRAL_QUERY");
    ajax.post(url, null, dataType.json,
    function(data) {
        if (!common.isEmpty(data.data)) {
            $(data.data).each(function(index, code) {
                /*map.set(code.referralId, code.referralName);*/
                $("#doctorDepartmentId").append("<option value=" + code.referralId + ">" + code.referralName + '</option>');
            });
        }
    },
    false, false);
    // 加载列表
    dtableTable = datatable.table(dtableOptions);
    // 表单自定义验证
    // 手机
    jQuery.validator.addMethod("mobile",
    function(value, element) {
        var length = value.length;
        return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1}))+d{8})$/.test(value));
    },
    "手机号码格式错误");
    // 身份证号
    jQuery.validator.addMethod("idcardno",
    function(value, element) {
        return this.optional(element) || isCardNo(value);
    },
    "身份证号格式错误");
    // 表单验证
    validator = $("#editForm").validate(editOptions);
    // 加入必填项提示
    common.requiredHint("editForm", editOptions);
    // 初始化日期插件
    //日期插件格式定义--出生日期
    common.initDate(null, null, null, "#doctorBirthday");
    var nowDate = common.dateFormatToString(new Date(), "yyyy-MM-dd");
    $("#doctorBirthday").datetimepicker("setEndDate", nowDate);
});