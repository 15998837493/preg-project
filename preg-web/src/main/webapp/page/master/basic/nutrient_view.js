$.validator.addMethod("RegCode", function(value,element) {
	if(_.isEmpty($("#nutrientNameEnglish").val())){
		return true;
	}else {
		return new RegExp("^[a-zA-Z][a-zA-Z0-9_]*$").test(element.value);
	}
}, '以英文字母开头，只能包含英文字母、数字、下划线!');
/**
 * 验证参数设置
 */
var addOptions = {
	rules : {
		nutrientNameEnglish:{
			maxlength : 64,
			RegCode:true
		},
		nutrientName : {
			maxlength : 50,
			required : true,
		},
		nutrientType : {
			required : true
		},
		nutrientEvalOne : {
			required : true
		},
		nutrientEvalTwo : {
			required : true
		},
		nutrientUnit : {
			required : true
		},
		nutrientId : {
			maxlength : 64,
			required : true,
			RegCode:true,
			remote : {
				url : URL.get("Master.NUTRIENT_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					nutrientId : function() {
						return $("#addForm").find("input[name='nutrientId']")
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
		nutrientId : {
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
					datatable.add(nutrientTable, data.value);// 添加
					checkedRow = null;
					checkedData = null;
					$("#id").val("");
				}
				$("#addModal").modal("hide");
			});
			// datatable.remove(userTable, userRow);// 删除
		});
	}
};
var updateOptions = {
	rules : {
		nutrientNameEnglish:{
			maxlength : 64,
			RegCode:true
		},
		nutrientName : {
			maxlength : 50,
			required : true,
		},
		nutrientType : {
			required : true
		},
		nutrientEvalOne : {
			required : true
		},
		nutrientEvalTwo : {
			required : true
		},
		nutrientUnit : {
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
					datatable.update(nutrientTable, data.value, checkedRow);// 修改
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
		var url = URL.get("Master.REMOVE_NUTRIENT");
		var params = "id=" + id;
		ajax.post(url, params, dataType.json, function(data) {
			/* layer.alert(data.message); */
			datatable.remove(nutrientTable, checkedRow);
			checkedRow = null;
			checkedData = null;
			$("#id").val("");
		});
		layer.close(index);
	});
};

function isChoose(id) {
	if (common.isEmpty(id)) {
		layer.msg('请选择操作的记录');
		return false;
	} else {
		return true;
	}
};
