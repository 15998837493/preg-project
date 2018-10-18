$.validator.addMethod("twoDigit", function(value,element) {
	if(element.value.trim()==""){
		return true;
	}
	return new RegExp("^[0-9]+(.[0-9]{1})?$").test(element.value);
}, '必须是数字，最多支持一位小数');

$.validator.addMethod("range1", function(value,element) {
	if(element.value.trim()==""){
		return true;
	}
	return element.value < 1000 && element.value >= 0;
}, '必须是小于1000的正数');

$.validator.addMethod("range2", function(value,element) {
	if(element.value.trim()==""){
		return true;
	}
	return element.value < 10000 && element.value >= 0;
}, '必须是小于10000的正数');


var addOptions = {
	rules: {
		name: {
			required: true,
			maxlength:80,
		},
		code: {
			required: true,
			remote : {
				url : URL.get("Master.PLAN_INTAKE_TYPE_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					name : function() {
						return $("#addForm").find("input[name='code']").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		type: {
			required: true
		},
		unit: {
			required: true,
			maxlength:20
		},
		unitAmount: {
			maxlength:4,
			required:true,
			twoDigit:true
		},
		unitEnergy: {
			maxlength:4,
			required:true,
			twoDigit:true
		},
		unitCbr: {
			maxlength:4,
			required:true,
			twoDigit:true
		},
		unitProtein: {
			maxlength:4,
			required:true,
			twoDigit:true
		},
		unitFat: {
			maxlength:4,
			required:true,
			twoDigit:true
		}
	},
	messages : {
		code : {
			remote : "该编码已经存在，请重新输入"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		$(form).ajaxPost(dataType.json, function(data){
			datatable.add(intakeTypeTable, data.value);// 添加
			checkedRow = null;
			checkedData = null;
			$("#id").val("");
			$("#addModal").modal("hide");
		});
	}
};

var updateOptions = {
		rules: {
			name: {
				maxlength:80,
				required: true
			},
			type: {
				required: true
			},
			unit: {
				maxlength:20,
				required: true
			},
			unitAmount: {
				required:true,
				twoDigit:true,
				range1:true
			},
			unitEnergy: {
				required:true,
				twoDigit:true,
				range2:true
			},
			unitCbr: {
				required:true,
				twoDigit:true,
				range2:true
			},
			unitProtein: {
				required:true,
				twoDigit:true,
				range2:true
			},
			unitFat: {
				required:true,
				twoDigit:true,
				range2:true
			}
		},
		//设置错误信息显示到指定位置
		errorPlacement: function(error, element) {
			element = element.parent();
			common.showmassage(error, element);
		},
		success: $.noop,
		submitHandler: function(form) {
			$(form).ajaxPost(dataType.json, function(data){
				layer.alert(data.message);
				if(data.result){
					datatable.update(intakeTypeTable, data.value,checkedRow);// 修改
				}
				$("#updateModal").modal("hide");
			});
		}
};

function removeIntakeType(id){
	layer.confirm("确定对选中内容执行【删除】操作？", function (index) {
		var url = URL.get("Master.PLAN_INTAKE_TYPE_REMOVE");
		var params = "id="+id;
		ajax.post(url,params,dataType.json,function(data){
			datatable.remove(intakeTypeTable,checkedRow);
			checkedRow = null;
			checkedData = null;
			$("#id").val("");
		});
		layer.close(index);
	});
}
