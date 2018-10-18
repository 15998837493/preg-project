/*
 * 膳食模板列表页面用JavaScript文件
 *
 * 北京麦芽健康管理有限公司
 * 
 * 变更履历：
 *   v1.0  wsy  2017-2-18  初版
 */
// 选中项信息
var planIntakeData;
// 选中行信息
var planIntakeRow;
// table对象
var planIntakeTable;
// 列表配置信息
var planIntakeTableOptions = {
	id : "planIntakeTable",
	form : "planIntakeQueryForm",
	columns : [ {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<input type='radio' name='tableRadio'  />";
		}
	}, {
		"data" : "intakeName",
		"sClass" : "text-left"
	}, {
		"data" : "intakeActualEnergy",
		"sClass" : "text-center"
	}, {
		"data" : "intakeCbr",
		"sClass" : "text-center"
	}, {
		"data" : "intakeCbrPercent",
		"sClass" : "text-center"
	}, {
		"data" : "intakeProtein",
		"sClass" : "text-center"
	}, {
		"data" : "intakeProteinPercent",
		"sClass" : "text-center"
	}, {
		"data" : "intakeFat",
		"sClass" : "text-center"
	}, {
		"data" : "intakeFatPercent",
		"sClass" : "text-center"
	}, {
		"data" : "pregnantStage",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("PREGNANT_STAGE", data);
		}
	}, {
		"data" : "dataType",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return CODE.transCode("DATA_BELONG_TYPE", data);
		}
	}, {
		"data" : "createUserName",
		"sClass" : "text-center"
	} ],
	rowClick : function(data, row) {
		planIntakeData = data;
		planIntakeRow = row;
		$("#planIntakeId").val(data.intakeId);
	},
	condition : "planIntakeCondition",
	selecttarget : [ 9, 10 ]
};
// 编辑页表单校验配置
var intakeOption = {
	rules : {
		intakeName : {
			required : true
		},
		intakeMode : {
			required : true
		},
		foodStructureId : {
			required : true
		},
		intakeProductDesc : {
			required : true
		},
		intakeActualEnergy : {
			numg : true,
			required : true
		},
		intakeActualEnergy : {
			numg : true,
			required : true
		},
		intakeProtein : {
			numg : true,
			required : true
		},
		intakeProteinPercent : {
			numg : true,
			required : true
		},
		intakeCbr : {
			numg : true,
			required : true
		},
		intakeCbrPercent : {
			numg : true,
			required : true
		},
		intakeFat : {
			numg : true,
			required : true
		},
		intakeFatPercent : {
			numg : true,
			required : true
		},
		dietId : {
			numg : true,
			required : true
		},
		intakePrompt : {
			required : true
		},
		intakeCaloricMin : {
			required : true,
			intege1 : true,
			compareToNumberMin : "#intakeCaloricMax"
		},
		intakeCaloricMax : {
			required : true,
			intege1 : true,
			compareToNumberMax : "#intakeCaloricMin"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		$(form).ajaxPost(dataType.json, function(data) {
			if (data.result) {
				common.pageForward(PublicConstant.basePath + "/page/master/intake_init.action");
			} else {
				layer.alert(data.message);
			}
		});
	}
};
function chooseDiseaseModal() {
	$("#diseaseModal").modal("show");
}

function getDisease() {
	var diseaseCode = $("input:radio[name='diseaseIdList']:checked").attr("value");
	$("#diseaseCode").val(diseaseCode);
	$("input[name='diseaseName']").val($("#" + diseaseCode + "_diseaseName").html());
	$("#diseaseModal").modal("hide");
}

function getFoodItem(planIntakeId) {
	common.pageForward(URL.get("Master.PLAN_planIntake_DETAIL_INIT") + "?planIntakeId=" + planIntakeId);
}

function removeplanIntake(planIntakeId) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(data) {
		if (data) {
			var url = URL.get("Master.PLAN_INTAKE_REMOVE");
			var params = "intakeId=" + planIntakeId;
			ajax.post(url, params, dataType.json, function(data) {
				if (data.result) {
					datatable.remove(planIntakeTable, planIntakeRow);
					$("#planIntakeId").val("");
				} else {
					layer.alert(data.message);
				}
			});
		}
	});
}

function isChoose(planIntakeId) {
	var tableRadios = $("#planIntakeTable").find("input:radio:checked");
	if (tableRadios.length == 0) {
		layer.alert('请选择操作的模版');
		return false;
	} else {
		return true;
	}
}
// 按钮点击事件
$("button[name='operateBtn']").click(function() {
	if (this.id == 'search') {
		datatable.search(planIntakeTable, planIntakeTableOptions);
	}
	var planIntakeId = $("#planIntakeId").val();
	if (this.id == 'save') {
		common.openWindow(URL.get("Master.PLAN_INTAKE_EDIT_INIT"));
	}
	if (this.id == 'update' && isChoose(planIntakeId)) {
		common.openWindow(URL.get("Master.PLAN_INTAKE_EDIT_INIT") + "?intakeId=" + planIntakeId);
	}
	if (this.id == 'delete' && isChoose(planIntakeId)) {
		removeplanIntake(planIntakeId);
	}
});

function reLoadTable() {
	planIntakeTable = datatable.table(planIntakeTableOptions);
}

$().ready(function() {
	planIntakeTable = datatable.table(planIntakeTableOptions);
	common.initCodeSelect("PREGNANT_STAGE", "PREGNANT_STAGE", "pregnantStage", "", "请选择孕期阶段");
	common.initCodeSelect("DATA_BELONG_TYPE", "DATA_BELONG_TYPE", "dataBelongType", "", "请选择模板类型");
});