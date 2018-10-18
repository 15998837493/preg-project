/**
 * 自定义封装datatables插件
 * 
 * @author zcq
 */
$.fn.dataTable.ext.errMode = 'throw';
var datatable = {
	// 默认值
	defaultopt : {
		id : "",// datatable id（*必填项）
		form : "",// 表单
		ajax : "",// 配置数据源（*必填项）
		async : true,// 是否同步
		columns : "",// 配置显示内容（*必填项）
		columnDefs : [],// 配置列
		condition : "",// 配置检索条件
		selecttarget : [],// 配置检索目标列
		buttons : "",// 配置按钮
		aaSorting : [ [ 1, "asc" ] ],// 默认的排序方式，第2列，升序排列
		width : "",// 默认宽度，注意宽度值必须带px后缀
		rowClick : false,// 定义行点击事件
		isPage : true,// 是否分页
		iDisplayLength : 10,// 默认每页显示10条数据
		bServerSide : false, // 是否启动服务器端数据导入
		bProcessing : false, // DataTables载入数据时，是否显示‘进度’提示
		bAutoWidth : false, // 是否自动调整宽度
		scrollY : "", // 是否启动垂直滚动条
		loading : true,// 是否启用遮罩层
		bSort : false,// 开启datatable排序
		search : true,// 默认开启自动搜索
		searchExtendInput : [],// 扩展检索内容--输入框
		searchExtendSelect : [],// 扩展检索内容--下拉列表
		isClearChecked : true, // 重绘后是否清空选中状态
		isShowRecord : true, // 是否显示记录条数
        sPaginationType : "full_numbers",// 详细分页组，可以支持直接跳转到某页
	},
	// 初始化datatable控件
	table : function(opt) {
		var options = $.extend({}, datatable.defaultopt, opt);

		if (_.isEmpty(options.id)) {
			layer.alert("table id 为空！");
			return false;
		}
		if (_.isEmpty(options.columns)) {
			layer.alert("columns 未设置！");
			return false;
		}
		if ((!_.isEmpty(options.searchExtendInput) && !$.isArray(options.searchExtendInput)) || (!_.isEmpty(options.searchExtendSelect) && !$.isArray(options.searchExtendSelect))) {
			layer.alert("扩展搜索内容传入格式不正确！");
			return false;
		}
		if (!_.isEmpty(options.scrollY)) {
			options.bAutoWidth = true;
		}
		if(!_.isEmpty(options.ajax) && !_.isEmpty(options.ajax.url)){
			$.extend(options.ajax.url, {traditional : true});
		}
		if (!_.isEmpty(options.form)) {
			var $form = $("#" + options.form);
			if (_.isEmpty(options.ajax.url)) {
				if (_.isEmpty($form.attr("action"))) {
					layer.alert("没有配置数据源信息！");
					return false;
				}
				if (_.isEmpty(options.ajax)) {
					options.ajax = {traditional : true};
				}
				// 设置url
				options.ajax.url = $form.attr("action");
			}
			// 设置Server-side
			if (options.bServerSide) {
				options.bProcessing = true;
				if (_.isEmpty(options.ajax.data) || !$.isFunction(options.ajax.data)) {
					options.ajax.data = function(d) {
						// d.order[0]
						// datatable定义的排序条件，不为空时向后台传递其中的排序列名orderColumn和排序方式orderDir。
						if (!_.isEmpty(d.order[0])) {
							// d.order[0].column 为列索引，d.columns[].data为列名称
							if (!_.isEmpty(d.columns[d.order[0].column].data)) {
								d.orderColumn = d.columns[d.order[0].column].data;
								d.orderDir = d.order[0].dir;
							} else {
								layer.msg("列名为空！");
							}
						}
						return $.extend({}, d, $form.serializeJson());
					};
					options.ajax.dataSrc = function(json) {
						return json.data;
					};
				}
				options.ajax.async = false;
			} else {
				options.ajax.data = $.extend({}, options.ajax.data, $form.serializeJson());
				options.ajax.async = options.async;
			}
		}
		if (!options.isShowRecord) {
			options.dom = "t<'row'<'col-xs-12'p>>";
		} else {
			options.dom = "t<'row'<'col-xs-4'i><'col-xs-8'p>>";
		}

		// 开启遮罩层
		if (options.loading && $(".layui-layer-shade").length == 0) {
			layer.loading();
		}

		$("#" + options.id).on('error.dt', function(e, settings, techNote, message) {
			if (message.indexOf("timeout") > -1) {
				var XMLHttpRequest = new Object();
				XMLHttpRequest.responseText = "timeout";
				ajax._error(XMLHttpRequest);
			}
		}).DataTable({
			"bProcessing" : options.bProcessing, // DataTables载入数据时，是否显示‘进度’提示
			"bServerSide" : options.bServerSide, // 是否启动服务器端数据导入
			"scrollY" : options.scrollY,// 是否垂直滚动
			"scrollCollapse" : true,// 是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变
			"bAutoWidth" : options.bAutoWidth,// 是否自适应宽度
			"aLengthMenu" : [ 5, 10, 20 ],// 更改显示记录数选项
			"iDisplayLength" : options.iDisplayLength,// 默认显示的记录数
			"bPaginate" : options.isPage,// 是否显示（应用）分页器
			"sPaginationType" : options.sPaginationType,// 详细分页组，可以支持直接跳转到某页
			"bSort" : options.bSort,// 是否启动各个字段的排序功能
			"bFilter" : true,// 是否启动过滤、搜索功能
			"destroy" : true,// 初始化一个新的Datatables，如果已经存在，则销毁（配置和数据），成为一个全新的Datatables实例
			"aaSorting" : options.aaSorting,// 默认的排序方式，第2列，升序排列
			"ajax" : options.ajax,
			"columns" : options.columns,
			"data" : options.data,
			"oLanguage" : { // 国际化配置
				"sProcessing" : "正在获取数据，请稍后...",
				"sLengthMenu" : "显示 _MENU_ 条",
				"sZeroRecords" : "<h4>没有数据！</h4>",
				"sInfo" : "&nbsp;从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
				"sInfoEmpty" : "记录数为0",
				"sInfoFiltered" : "(全部记录数 _MAX_ 条)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索 ",
				"sUrl" : "",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上一页",
					"sNext" : "下一页",
					"sLast" : "尾页"
				}
			},
			"columnDefs" : options.columnDefs,
			"dom" : options.dom,
			// 初始化结束后的回调函数
			"initComplete" : function() {
				// 设置table最小宽度
				if (!_.isEmpty(options.width)) {
					$("#" + options.id + ",.paging_full_numbers").attr("style", "min-width: " + options.width + ";");
				}

				// 获取databable对象
				var $this = this;

				// 定义搜索条件事件
				if (options.search) {
					var $mycondition = $("#" + options.condition);
					// TODO:排除其他input，如hidden
					var $myinput = $mycondition.find("input[type=text]");
					var $myselect = $mycondition.find("select");
					var myconditionEvent = "change keyup";
					if (options.bServerSide) {
						myconditionEvent = "change";
					}
					$mycondition.off("change keyup").on(myconditionEvent, function() {
						// input条件
						var myinput = [];
						$myinput.each(function() {
							myinput.push(this.value);
						});
						$this.api().search(myinput.join(" ") + options.searchExtendInput.join(" "));

						// select条件
						if ($myselect.length != options.selecttarget.length) {
							layer.alert("下拉列表搜索列个数 和 options.selecttarget数组不匹配！");
							return false;
						}
						$myselect.each(function(index) {
							if (!_.isEmpty(this.value)) {
								$this.api().column(options.selecttarget[index]).search($(this).find("option:selected").text());
							} else {
								$this.api().column(options.selecttarget[index]).search("");
							}
						});
						if (!_.isEmpty(options.searchExtendSelect)) {
							$.each(options.searchExtendSelect, function(index, data) {
								if (!_.isEmpty(data)) {
									if ($.isArray(data) && data.length == 2 && !isNaN(data[0])) {
										if (!_.isEmpty(data[0])) {
											if (!_.isEmpty(data[1])) {
												$this.api().column(data[0]).search(data[1]);
											} else {
												$this.api().column(data[0]).search("");
											}
										}
									} else {
										layer.alert("扩展搜索内容传入格式不正确！");
										return false;
									}
								}
							});
						}
						// 重绘检索内容
						$this.api().draw();
					});
				}

				// 定义行点击事件
				$("#" + options.id + " tbody").off("click").on('click', 'tr', function(e) {
					// 行选中
					$("#" + options.id + " tbody tr").removeClass('active');
					$(this).addClass('active');
					// 行 rido checkbox 选中
					var $checkradio = $($(this).children()[0]).children("input:checkbox, input:radio");
					if (e.target.type != "checkbox" && e.target.type != "radio") {
						if ($checkradio.length == 1) {
							if ($checkradio.attr("type") == "radio") {
								$checkradio.attr("checked", true);
							}
							if ($checkradio.attr("type") == "checkbox") {
								if ($checkradio.attr("checked")) {
									$checkradio.attr("checked", false);
								} else {
									$checkradio.attr("checked", true);
								}
							}
						}
					}
					// 执行自定义函数
					if ($.isFunction(options.rowClick)) {
						options.rowClick($this.fnGetData(this), this);// 获取行数据
					}
				});

				// 配置datatables序号
				if (!isNaN(options.orderIndex)) {
					$this.api().on('order.dt search.dt', function() {
						$this.api().column(options.orderIndex, {
							search : 'applied',
							order : 'applied'
						}).nodes().each(function(cell, i) {
							cell.innerHTML = i + 1;
						});
					}).draw();
				}

				// 定义搜索框回车事件
				if (!_.isEmpty(options.condition)) {
					$("#" + options.condition + " input").off("keypress").on("keypress", function(event) {
						if (event.which == 13) {
							return false;
						}
					});
				}
				// 去掉遮罩层
				layer.close(layer.index);
				// 执行配置的回调函数
				if (options.callback) {
					options.callback();
				}
				;
			},
			// 重绘回调函数--取消自定义缓存数据
			"drawCallback" : function(settings) {
				if (options.isClearChecked) {
					// 清空js缓存的行数据
					if ($.isFunction(options.rowClick)) {
						options.rowClick("", this);
					}
				}
			},
			// 行回调函数--取消本页显示内容的选中状态
			"rowCallback" : function(row, data, index) {
				if (options.isClearChecked) {
					var $rowTds = $(row).children();
					if ($rowTds.length > 0 && $($rowTds[0]).children().length > 0) {
						var tdChild = $($rowTds[0]).children()[0];
						if (tdChild.type == "checkbox" || tdChild.type == "radio") {
							tdChild.checked = false;
						}
					}
				}
			}
		});
		return $("#" + options.id).dataTable();
	},
	// 查询
	search : function(table, opt) {
		// 获取databable对象
		var $this = table;
		var options = $.extend({}, datatable.defaultopt, opt);

		// 校验数据
		if ((!_.isEmpty(options.searchExtendInput) && !$.isArray(options.searchExtendInput)) || (!_.isEmpty(options.searchExtendSelect) && !$.isArray(options.searchExtendSelect))) {
			layer.alert("扩展搜索内容传入格式不正确！");
			return false;
		}

		// 定义搜索条件事件
		var $mycondition = $("#" + options.condition);
		// TODO 排除其他input，如hidden
		var $myinput = $mycondition.find("input[type=text]");
		var $myselect = $mycondition.find("select");

		// input条件
		var myinput = [];
		$myinput.each(function() {
			myinput.push(this.value);
		});
		$this.api().search(myinput.join(" ") + options.searchExtendInput.join(" "));

		// select条件
		if ($myselect.length != options.selecttarget.length) {
			layer.alert("下拉列表搜索列个数 和 options.selecttarget数组不匹配！");
			return false;
		}
		$myselect.each(function(index) {
			var myselect = [];
			if (!_.isEmpty(this.value)) {
				myselect.push($(this).find("option:selected").text());
			}
			$this.api().column(options.selecttarget[index]).search(myselect.join(" "));
		});

		if (!_.isEmpty(options.searchExtendSelect)) {
			$.each(options.searchExtendSelect, function(index, data) {
				if (!_.isEmpty(data)) {
					if ($.isArray(data) && data.length == 2 && !isNaN(data[0])) {
						if (!_.isEmpty(data[0])) {
							if (!_.isEmpty(data[1])) {
								$this.api().column(data[0]).search(data[1]);
							} else {
								$this.api().column(data[0]).search("");
							}
						}
					} else {
						layer.alert("扩展搜索内容传入格式不正确！");
						return false;
					}
				}
			});
		}

		$this.api().draw();
	},
	// 获取所有数据
	getAllData : function(table) {
		return table.fnGetData();
	},
	// 行添加（datatable默认添加都最后一行，本方法功能把最后一行挪到第一行）
	add : function(table, value) {
		table.fnAddData(value);
		var data = table.api().data();
		table.api().clear();
		data.splice(0, 0, data.splice(data.length - 1, 1)[0]);
		table.api().rows.add(data).draw();
	},
	// 修改行
	update : function(table, value, row) {
		table.api().row(row).data(value).draw(false);
		$(row).click();
	},
	// 删除行
	remove : function(table, row) {
		table.api().row(row).remove().draw(false);
		row = null;
	},
	// 校验是否选中行
	isDatatableTR : function(row){
		if(row.tagName != "TR"){
			layer.alert("请先选择要操作的记录!");
			return false;
		}
		return true;
	}
};
