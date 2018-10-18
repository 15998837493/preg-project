/**
 * 验证参数设置
 */
var Options = {
	rules : {
		userName:{
			required : true
		},
		scheduleMaxPerson : {
			maxlength : 5,
			required : true,
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		/*		var editFormType = $("#editFormType").val();
		if ("add" == editFormType) {
				$("#addForm").ajaxPost(dataType.json, function(data) {
					layer.alert(data.message);
					if (data.result) {
						datatable.add(nutrientTable, data.value);// 添加
						checkedRow = null;
						checkedData = null;
						$("#scheduleId").val("");
					}
					$("#addModal").modal("hide");
				});
		}
		if ("update" == editFormType) {
				$("#addForm").ajaxPost(dataType.json, function(data) {
					layer.alert(data.message);
					if (data.result) {
						datatable.update(nutrientTable, data.value,checkedRow);// 修改					
					}
					$("#addModal").modal("hide");
				});
		}*/
		var flag = 0;//checkbox选中的个数，为0则是一个都没选
		//var flagWeek = true;//同一天不能同时选择上下午，false为选择同一天的上下午了
		//var workDay=[];//工作日安排
		var box = $("input[name='scheduleWeek']");
/*		workDay.push("周一");
		workDay.push("周二");
		workDay.push("周三");
		workDay.push("周四");
		workDay.push("周五");
		workDay.push("周六");
		workDay.push("周日");*/
		for(var x=0;x<box.length;x++) {
			if(box[x].checked == true) {
				flag++;
			}
		}
/*		for(var x=0;x<workDay.length;x++) {
		if($("input[value='"+workDay[x]+"上午']").is(":checked")&&$("input[value='"+workDay[x]+"下午']").is(":checked")) {
			flagWeek = false;
		}
		}*/
		if(flag==0) {
			layer.alert("工作日安排至少要选择一项！");
		}else {
			$("#addForm").ajaxPost(dataType.json, function(data) {
				layer.alert(data.message);
				$("#addModal").modal("hide");
			});
			$("input[name='leafBox']").attr("checked",false);//当前页的checkbox清空选中状态
			$("#nutrientTable").append("<input type='hidden' id='saveSign' value='saveSign'/>");//添加一个保存成功之后的状态，每次监听分页点击事件，清空checkbox选中状态（因为只能获取当前页checkbox，无法清空所有页的checkbox）
			$("#selectedBoxUsersId").find("p").remove();//清空所有checkbox的储存标签
			validator.resetForm();
		}
	}
};

/**
 * 删除提交form请求
 */
function removeClick(id) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		var url = URL.get("System.SCHEDULE_DELETE");
		var params = "scheduleId=" + id;
		ajax.post(url, params, dataType.json, function(data) {
			datatable.remove(nutrientTable, checkedRow);
			checkedRow = null;
			checkedData = null;
			$("#scheduleId").val("");
		});
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