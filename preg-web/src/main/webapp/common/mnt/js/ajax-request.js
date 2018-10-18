var ajax = {
	_errorMsg : function(XMLHttpRequest, action) {
		var errorCode = XMLHttpRequest.status;
		var errorMsg = XMLHttpRequest.responseText;
		if (errorMsg == 'timeout') {

		} else if (errorCode == 400) {
			errorMsg = "问题：数据类型错误！<br>错误码：" + errorCode + "<br>提示：页面<font color='red'>传入数据类型</font>和后台<font color='red'>接收数据类型</font>不匹配！";
		} else if (errorCode == 404) {
			errorMsg = "问题：访问URL失效！<br>错误码：" + errorCode + "<br>地址：" + action + "<br><font color='red'>提示：请检查URL是否有效！</font>";
		} else if (errorCode == 500) {
			errorMsg = "问题：服务器内部错误！<br>错误码：" + errorCode + "<br>地址：" + action + "<br><font color='red'>提示：请检查后台是否报错！</font>";
		} else if (errorCode == 200) {
			errorMsg = "问题：" + errorMsg + "<br>错误码：" + errorCode + "<br>地址：" + action + "<br><font color='red'>提示：" + errorMsg + "！</font>";
		} else {
			errorMsg = "问题：ajax 访问出错！<br>错误码：" + errorCode + "<br>地址：" + action + "<br><font color='red'>提示：" + errorMsg + "！</font>";
		}
		return errorMsg;
	},
	_success : function(data, isLoad, callback) {
		if (isLoad) {
			layer.close(layer.index);
		}
		if (data.result) {
			if ($.isFunction(callback)) {
				callback(data);
			}
		} else {
			layer.alert(data.message || "问题：返回结果错误！<br><font color='red'>提示：WebResult 返回 result=false！</font>");
		}
	},
	_error : function(XMLHttpRequest, action) {
		layer.close(layer.index);
		var errorMsg = ajax._errorMsg(XMLHttpRequest, action);
		if (errorMsg == 'timeout') {
			layer.alert('登录超时，请重新登录！', {
				closeBtn : false
			}, function(index) {
				if ($("div[class='main-page']").length == 0) {
					window.close();
				} else {
					ajax._ajax("logout.action", {
						"logout" : 'exit'
					}, "json", function(data) {
						if (data.result) {
							window.location = "dlym.action";
						} else {
							layer.alert(data.message || "系统登出错误！");
						}
					}, false, false, "post");
				}
				layer.close(index);
				return false;
			});
		} else {
			layer.alert(errorMsg);
		}
	},
	_ajax : function(action, params, dataType, callback, isLoad, isAsync, type, success, error) {
		// 默认启动遮罩层
		if (isLoad != false) {
			isLoad = true;
		}
		// 默认异步
		if (isAsync != false) {
			isAsync = true;
		}
		// 启动遮罩
		if (isLoad) {
			layer.loading();
		}

		if (_.isEmpty(params)) {
			params = 'random=' + Math.random();
		} else {
			params = params + '&random=' + Math.random();
		}

		$.ajax({
			type : type,
			url : action,
			data : params,
			async : isAsync,
			dataType : dataType,
			success : function(data) {
				if ($.isFunction(success)) {
					success(data);
				} else {
					ajax._success(data, isLoad, callback);
				}
			},
			error : function(XMLHttpRequest) {
				if ($.isFunction(error)) {
					error();
				} else {
					ajax._error(XMLHttpRequest, action);
				}
			}
		});
	},
	get : function(action, params, dataType, callback, isLoad, isAsync) {
		ajax._ajax(action, params, dataType, callback, isLoad, isAsync, "get");
	},
	post : function(action, params, dataType, callback, isLoad, isAsync) {
		ajax._ajax(action, params, dataType, callback, isLoad, isAsync, "post");
	},
	getHtml : function(action, id, callback, isLoad, isAsync) {
		ajax._ajax(action, null, "html", callback, isLoad, isAsync, "get", function(data) {
			if (data == "timeout") {
				var XMLHttpRequest = new Object();
				XMLHttpRequest.responseText = "timeout";
				ajax._error(XMLHttpRequest, action);
			} else {
				if (!_.isEmpty(id)) {
					$("#" + id).html(data);
				}
				if ($.isFunction(callback)) {
					callback(data);
				}
			}
		});
	}
};

/**
 * ajax-post提交方法
 * 
 * @param dataType
 *            post提交类型
 * @param callback
 *            post提交返回回调函数
 * @param isLoad
 *            post提交加载遮罩层
 * @param isAsync
 *            post提交同步
 */
$.fn.ajaxPost = function(dataType, callback, isLoad, isAsync, action) {
	// 默认启动遮罩层
	if (isLoad != false) {
		isLoad = true;
	}
	// 默认异步
	if (isAsync != false) {
		isAsync = true;
	}
	// 启动遮罩
	if (isLoad) {
		layer.loading();
	}

	var form = this;
	var formAction = action || form.attr('action');
	// 提交表单数据
	var formData = form.serialize();
	// formData = decodeURIComponent(formData,true);
	$.ajax({
		type : "post",
		url : formAction,
		async : isAsync,
		data : formData + '&random=' + Math.random(),// 增加随机数保证每次提交无缓冲
		dataType : dataType,
		success : function(data) {
			ajax._success(data, isLoad, callback);
		},
		error : function(XMLHttpRequest) {
			ajax._error(XMLHttpRequest, formAction);
		}
	});
};
