/**
 * 验证参数设置
 */
var Options = {
	rules : {
		scheduleNoon:{
			required : true
		},
		scheduleMaxPerson : {
			required : true,
		},
		scheduleContent : {
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
		var editFormType = $("#editFormType").val();
		if ("add" == editFormType) {
/*				$("#addForm").ajaxPost(dataType.json, function(data) {
					layer.alert(data.message);
					if (data.result) {
						datatable.add(nutrientTable, data.value);// 添加
						checkedRow = null;
						checkedData = null;
						$("#scheduleId").val("");
					}
					$("#addModal").modal("hide");
				});*/
			common.pageForward(URL.get("System.SCHEDULE_ADD")+ "?scheduleWeek=" + $("#scheduleWeek").val()+"&scheduleNoon=" + $("#scheduleNoon").val()+"&scheduleMaxPerson=" + $("#scheduleMaxPerson").val()+"&scheduleContent=" + $("#scheduleContent").val());
		}
		if ("update" == editFormType) {
/*				$("#addForm").ajaxPost(dataType.json, function(data) {
					layer.alert(data.message);
					if (data.result) {
//						datatable.update(nutrientTable, data.value,checkedRow);// 修改					
					}
					$("#addModal").modal("hide");
				});*/
			common.pageForward(URL.get("System.SCHEDULE_UPDATE")+ "?scheduleWeek=" + $("#scheduleWeek").val()+"&scheduleNoon=" + $("#scheduleNoon").val()+"&scheduleMaxPerson=" + $("#scheduleMaxPerson").val()+"&scheduleContent=" + $("#scheduleContent").val()+"&scheduleId=" + $("#scheduleId").val());
		}
		validator.resetForm();
	}
};

/**
 * 删除提交form请求
 */
function removeClick(id) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
/*		var url = URL.get("System.SCHEDULE_DELETE");
		var params = "scheduleId=" + id;
		ajax.post(url, params, dataType.json, function(data) {
			datatable.remove(nutrientTable, checkedRow);
			checkedRow = null;
			checkedData = null;
			$("#scheduleId").val("");
		});*/
		common.pageForward(URL.get("System.SCHEDULE_DELETE")+ "?scheduleId=" + $("#scheduleId").val());
		$("#scheduleId").val("");
		$("#addModal").modal("hide");
		layer.close(index);
	});
};

function isChoose(dietId) {
	if (common.isEmpty(dietId)) {
		layer.alert('请选择操作的数据');
		return false;
	} else {
		return true;
	}
}

//只能数字(小数点也不能输入)
function validNum(val) {
	var reg= /^[1-9][0-9]*$/;
	if(reg.test(val)==false) {
    	layer.msg("必须输入数字(不能为0)！");
    	$("#scheduleMaxPerson").val("");
	}
}
