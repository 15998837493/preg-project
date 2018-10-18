//选中项信息
var masQuestionData;
// 选中行信息
var masQuestionRow;
// table对象
var masQuestionTable;
// 列表配置信息
var masQuestionTableOptions = {
	id : "masQuestionTable",
	form : "masQuestionQueryForm",
	columns : [ {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<input type='radio' name='tableRadio'/>";
		}
	}, {
		"data" : "questionId",
		"sClass" : "text-center"
	}, {
		"data" : "questionName",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return data || "————";
		}
	}, {
		"data" : "questionType",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			var questionType = data;
			if (common.isEmpty(questionType)) {
				questionType = '————';
			} else {
				questionType = CODE.transCode("QUESTIONTYPE", questionType);
			}
			return questionType;
		}
	}, {
		"data" : "questionState",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			var questionState = data;
			if (common.isEmpty(questionState)) {
				questionState = '————';
			} else if (questionState == '0') {
				questionState = "未启用";
			} else if (questionState == '1') {
				questionState = "已启用";
			}
			return questionState;
		}
	} ],
	rowClick : function(data, row) {
		masQuestionData = data;
		masQuestionRow = row;
		$("#hiddenForm #id").val(data.questionId);
	},
	condition : "masQuestionCondition"
};
var intakeTemplateOptions = {
	rules : {
		questionId : {
			maxlength : 64,
			required : true
		},
		questionName : {
			maxlength : 200,
			required : true
		},
		questionState : {
			required : true
		},
		questionDesc : {
			maxlength : 400,
			required : false
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm("确定要执行【保存】操作？", {
			  btn: ['确定','取消'] //按钮
		}, function() {
			$(form).ajaxPost(dataType.json, function(data){
				submitSuc(data);
			});
		});
	}
};
/**
 * form提交成功回调函数
 */
function submitSuc(data) {
	$("#editModal").modal("hide");
	masQuestionTable = datatable.table(masQuestionTableOptions);
};
$().ready(function() {
	common.requiredHint("editForm", intakeTemplateOptions);
	$("#editForm").validate(intakeTemplateOptions);
	masQuestionTable = datatable.table(masQuestionTableOptions);
	// 按钮初始化
	$("button[name='operateButton']").click(function() {
		var id = $("#hiddenForm #id").val();
		// 新增初始化
		if (this.id == "add") {
			common.initCodeSelect("QUESTIONTYPE", "QUESTIONTYPE", "questionType");
			common.clearForm("editForm");
			$("#editForm").attr("action", URL.get("Question.ADD_QUESTION"));
			$("#editModal").modal("show");
		}
		// 编辑初始化
		if (this.id == "edit" && common.isChoose(id)) {
			common.initCodeSelect("QUESTIONTYPE", "QUESTIONTYPE", "questionType");
			common.clearForm("editForm");
			$("#editForm").attr("action", URL.get("Question.UPDATE_QUESTION"));
			$("#editForm #questionId").val(masQuestionData.questionId);
			$("#editForm #questionName").val(masQuestionData.questionName);
			$("#editForm #questionState").val(masQuestionData.questionState);
			$("#editForm #questionType").val(masQuestionData.questionType);
			$("#editForm #questionDesc").val(masQuestionData.questionDesc);
			$("#editModal").modal("show");
		}
		// 删除初始化
		if (this.id == "remove" && common.isChoose(id)) {
			layer.confirm("确定对选中内容执行【删除】操作？", function() {
				$("#hiddenForm").attr("action", URL.get("Question.QUESTION_REMOVE"));
				$("#hiddenForm").ajaxPost(dataType.json, submitSuc);
			});
		}
		if (this.id == "config" && common.isChoose(id)) {
			common.pageForward(URL.get("Question.ADD_INIT_QUESTION_CONFIG") + "?id=" + id);
		}
		if (this.id == "search") {
			masQuestionTable = datatable.table(masQuestionTableOptions);
		}
	});
});
