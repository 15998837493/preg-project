var operner;
var validator;
// 选中项信息
var dtableData;
// 选中行信息
var dtableRow;
// table对象
var dtableTable;
// 列表配置信息
var dtableOptions = {
	id : "dTable",
	form : "dtableQueryForm",
	columns : [ {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<input type='radio' name='tableRadio'  />";
		}
	}, {
		"data" : null,
		"sClass" : "text-center"
	}, {
		"data" : "dietName",
		"sClass" : "text-left"
	}, {
		"data" : "pregnantStage",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("PREGNANT_STAGE", data);
		}
	}, {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<a style='cursor: pointer;' onclick='getDietTemplate(\"" + data.dietId + "\",\""+ data.dietName+"\")'><i class='fa fa-edit fa-fw'></i>配置</a>";
		}
	}, {
		"data" : "dietType",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("DATA_BELONG_TYPE", data);
		}
	}, {
		"data" : "createUserName",
		"sClass" : "text-center"
	} ],
	rowClick : function(data, row) {
		dtableData = data;
		dtableRow = row;
		$("#dietId").val(data.dietId);
	},
	condition : "dtableCondition",
	orderIndex : 1
};
// 配置表单校验参数
var editOptions = {
	rules : {
		dietName : {
			required : true,
			remote : {
				url : URL.get("Master.CHECK_DIET_NAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					dietName : function() {
						return $("#dietName").val();
					},
					editFormType : function() {
						return $("#editFormType").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		}
	},
	messages:{
		dietName:{
			remote: "模板名称不能重复"
		},
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler: function(form) {
		var editFormType = $("#editFormType").val();
		if ("add" == editFormType) {
			$("#editForm #dietType").removeAttr("disabled");
			$("#editForm").ajaxPost(dataType.json, function(data) {
				$("#editForm #dietType").attr("disabled", true);
				datatable.add(dtableTable, data.value);
				$("#editModal").modal("hide");
				$("#dietId").val("");
			});
		}
		if ("update" == editFormType) {
			$("#editForm #dietType").removeAttr("disabled");
			$("#editForm").ajaxPost(dataType.json, function(data) {
				$("#editForm #dietType").attr("disabled", true);
				datatable.update(dtableTable, data.value, dtableRow);
				$("#editModal").modal("hide");
			});
		}
		validator.resetForm();
	}
};

function getDietTemplate(dietId,dietName) {
//	common.pageForward(URL.get("Master.PLAN_DIETTEMPLATEDETAIL_INIT") + "?dietId=" + dietId);
	common.pageForward(URL.get("Master.PLAN_DIETTEMPLATEDETAIL_INIT")+"?dietId=" + dietId + "&dietName=" + dietName);
}

function removeCookbook(dietId) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(data) {
		if (data) {
			var url = URL.get("Master.PLAN_DIETTEMPLATE_REMOVE");
			var params = "dietId=" + dietId;
			ajax.post(url, params, dataType.json, function(data) {
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
		layer.alert('请选择操作的模版');
		return false;
	} else {
		return true;
	}
}
// 按钮点击事件
$("button[name='operateBtn']").click(function() {
	if (this.id == 'search') {
		dtableTable = datatable.table(dtableOptions);
	}
	$("#editForm #dietType").attr("disabled", true);
	var dietId = $("#dietId").val();
	// 清空表单
	common.clearForm("editForm");
	if (this.id == 'add') {
		$("#editForm #dietName").attr("readonly", false);
		$("#editForm").attr("action", URL.get("Master.PLAN_DIETTEMPLATE_ADD"));
		// 自动填写模板类型
		if ("admin" == (loginUserData.userCode)) {
			$("#editForm #dietType").val("1");
		} else {
			$("#editForm #dietType").val("2");
		}
		// 记录操作类型
		$("#editFormType").val("add");
		$("#editModal").modal("show");
	}
	if (this.id == 'update' && isChoose(dietId)) {
		// 初始化数据
		$("#editForm").attr("action", URL.get("Master.PLAN_DIETTEMPLATE_UPDATE"));
		$("#editForm #dietId").val(dtableData.dietId);
		$("#editForm #dietName").val(dtableData.dietName);
		$("#editForm #dietName").attr("readonly", true);
		$("#editForm #dietType").val(dtableData.dietType);
		$("#editForm #pregnantStage").val(dtableData.pregnantStage);
		$("#editForm #dietDesc").val(dtableData.dietDesc);
		// 记录操作类型
		$("#editFormType").val("update");
		$("#editModal").modal("show");
	}
	if (this.id == 'remove' && isChoose(dietId)) {
		removeCookbook(dietId);
	}
});

// 初始化编辑页面下拉框
common.initCodeSelect("DATA_BELONG_TYPE", "DATA_BELONG_TYPE", "dietType");
common.initCodeSelect("PREGNANT_STAGE", "PREGNANT_STAGE", "pregnantStage");

$().ready(function() {
	// 加载列表
	dtableTable = datatable.table(dtableOptions);
	// 表单验证
	validator = $("#editForm").validate(editOptions);
	// 加入必填项提示
	common.requiredHint("editForm", editOptions);
});