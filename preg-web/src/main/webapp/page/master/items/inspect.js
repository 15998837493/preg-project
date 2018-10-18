/**
 * 验证参数设置
 */
var validator;
var intakeTemplateOptions = {
	rules: {
		inspectCategory: {
			required:true
		},
		inspectCode: {
			maxlength: 64,
			required: true,
			remote : {
				url : URL.get("item.INSPECTITEM_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					inspectCode : function() {
						return $("#inspectCode").val();;
					},
					inspectOldCode : function() {
						return $("#inspectOldCode").val();;
					},
					type : function() {
						return operateType;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		inspectName: {
			maxlength: 100,
			required:true
		},
		inspectSex: {
			required:true
		},
		inspectConfigName: {
			maxlength: 200,
			required:true
		},
		inspectTable: {
			required:true
		},
		inspectReport: {
			required:true
		},
		inspectStatus: {
			required:true
		},
	},
	messages : {
		inspectCode : {
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
		layer.confirm('确定要执行【保存】操作？', {
			  btn: ['确定','取消'] //按钮
			}, 
		function(){
			$(form).ajaxPost(dataType.json,function(data){
				if(operateType == "add"){
					datatable.add(inspectTable, data.value);// 添加
					$("#editInspectModal").modal("hide");
				} else if(operateType == "update"){
					datatable.update(inspectTable, data.value, inspectRow);// 修改
					$("#editInspectModal").modal("hide");
				}
				validator.resetForm();
			});
		});
	}
};

/**
 * 删除提交form请求
 */
function removeClick(inspectId){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		},
	function(){
		var url = URL.get("item.REMOVE_INSPECTITEM");
		var params = "inspectId=" + inspectId;
		ajax.post(url, params, dataType.json, function(data){
			datatable.remove(inspectTable, inspectRow);// 删除
			$("#id").val("");
		});
	});
};