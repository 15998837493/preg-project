//查询请求action
var initQueryAction = PublicConstant.basePath + URL.Master.PRODUCT_VIEW;
var initAddAction = PublicConstant.basePath + URL.Master.ADD_INIT_PRODUCT;
var initUpdateAction = PublicConstant.basePath + URL.Master.UPDATE_INIT_PRODUCT;
var configAction = PublicConstant.basePath + URL.Master.PRODUCT_ELEMENT_INIT;
var deleteAction = PublicConstant.basePath + URL.Master.REMOVE_PRODUCT;
/**
 * 验证参数设置
 */
var addOptions = {
	rules : {
		productCategoryName : {
			required : true
		},
		productCode : {
			required : true,
			maxlength : 16,
			remote : {
				url : URL.get("Master.PRODUCT_PRODUCTCODE_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					productCode : function() {
						return $("#addForm").find("input[name='productCode']")
								.val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		productGoodsName : {
			maxlength : 200
		},
		productCommonName : {
			maxlength : 100
		},
		productName : {
			maxlength : 100
		},
		productIsOfficina : {
			required : true
		},
		productIsAssess : {
			required : true
		},
		productIsEnergy : {
			required : true
		},
		productDays : {
			intege1 : true
		},
		productPurchasePricestr : {
			required : true,
			twoDigit : true
		},
		productSellPricestr : {
			required : true,
			twoDigit : true
		}
	},
	messages : {
		productCode : {
			remote : "此商品代码已存在"
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
			$(form).ajaxPost(dataType.json, submitSuc);
		});

	}
};

var updateOptions = {
	rules : {
		productCategoryName : {
			required : true
		},
		productGoodsName : {
			maxlength : 100
		},
		productCommonName : {
			maxlength : 50
		},
		productName : {
			maxlength : 100
		},
/*		productAttribute : {
			required : true
		},*/
		productIsOfficina : {
			required : true
		},
		productIsAssess : {
			required : true
		},
		productIsEnergy : {
			required : true
		},
		productDays : {
			intege1 : true
		},
		productPurchasePricestr : {
			twoDigit : true,
		},
		productSellPricestr : {
			twoDigit : true,
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
			$(form).ajaxPost(dataType.json, submitSuc);
		});
	}
};

redirectPage = function(actionName) {
	if (actionName == 'query') {
		common.pageForward(initQueryAction);
	} else if (actionName == 'init_add') {
		common.pageForward(initAddAction);
	} else {
		common.pageForward(actionName);
	}
};

/**
 * form提交成功回调函数
 */
function submitSuc(data) {
	layer.alert(data.message, function() {
		// 操作成功返回查询页
		if (data.result) {
			redirectPage('query');
		}
	});
};

/**
 * 删除提交form请求
 */
function removeClick(id) {
	layer.confirm("确定对选中内容执行【删除】操作？", function(index) {
		$("#id").val(id);
		$("#editForm").attr("action", deleteAction);
		$("#editForm").ajaxPost(dataType.json, submitSuc);
	});
};

function getDisease() {
	var checkedValues = "";
	var checkedValueNames = "";
	$("input:checkbox[name='diseaseIdList']").each(function() {
		if ($(this).attr("checked")) {
			checkedValues += $(this).val() + ",";
			checkedValueNames += $(this).next().text() + ",";
		}
	});
	if (common.isEmpty(checkedValues)) {
		checkedValues = "";
	} else {
		checkedValues = checkedValues.substring(0, checkedValues.length - 1);
	}
	if (common.isEmpty(checkedValueNames)) {
		checkedValueNames = "";
	} else {
		checkedValueNames = checkedValueNames.substring(0,
				checkedValueNames.length - 1);
	}
	$("#interveneDiseaseIds").val(checkedValues);
	$("#interveneDiseaseNames").val(checkedValueNames);
	$("#diseaseModal").modal("hide");
}

function isChoose(id) {
	if (common.isEmpty(id)) {
		layer.alert('请选择操作的记录');
		return false;
	} else {
		return true;
	}
}
