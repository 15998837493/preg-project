/**
 * 验证参数设置
 */
var addOptions = {
	rules : {
		sympCode : {
			maxlength : 50,
			required : true
		},
		sympName : {
			required : true
		},
		sympPart : {
			required : true
		},
		sympCategory : {
			required : true
		},
		sympCode : {
			maxlength : 64,
			required : true,
			remote : {
				url : URL.get("Master.SYMPTOMS_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					symptomsId : function() {
						return $("#addForm").find("input[name='sympCode']")
								.val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		}
	},
	messages : {
		sympCode : {
			remote : "该编码已经存在，请重新输入"
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm("确定要执行【保存】操作？", function(index) {
			$(form).ajaxPost(dataType.json, function(data) {
				layer.alert(data.message);
				if (data.result) {
					datatable.add(symptomsTable, data.value);// 添加
					checkedRow = null;
					checkedData = null;
					$("#id").val("");
				}
				$("#addModal").modal("hide");
			});
		});
	}
};
var updateOptions = {
	rules : {
		sympCode : {
			maxlength : 50,
			required : true
		},
		sympName : {
			required : true
		},
		sympPart : {
			required : true
		},
		sympCategory : {
			required : true
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		layer.confirm("确定要执行【保存】操作？", function(index) {
			$(form).ajaxPost(dataType.json, function(data) {
				layer.alert(data.message);
				if (data.result) {
					datatable.update(symptomsTable, data.value, checkedRow);// 修改
				}
				$("#updateModal").modal("hide");
			});

		});
	}
};

/**
 * 删除提交form请求
 */
function removeClick(id) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		var url = URL.get("Master.REMOVE_Symptoms");
		var params = "id=" + id;
		ajax.post(url, params, dataType.json, function(data) {
			/* layer.alert(data.message); */
			datatable.remove(symptomsTable, checkedRow);
			checkedRow = null;
			checkedData = null;
			$("#id").val("");
		});
		layer.close(index);
	});
};

function isChoose(id) {
	if (common.isEmpty(id)) {
		layer.alert('请选择操作的记录', {
			title : '提示信息'
		});
		return false;
	} else {
		return true;
	}
};
