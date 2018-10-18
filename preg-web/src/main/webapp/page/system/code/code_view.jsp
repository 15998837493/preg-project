<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>代码表一览页</title>
</head>
<script type="text/javascript">
	/**
	 * 验证设置
	 */
	var codeOptions = {
		rules : {
			codeType : {
				required : true
			},
			codeKind : {
				required : true,
				maxlength : 30,
				remote : {
					url : URL.get("System.CODE_KIND_VALID"),//后台处理程序  
					type : "post", //数据发送方式  
					dataType : "json", //接受数据格式     
					data : { //要传递的数据，默认已传递应用此规则的表单项  
						codeKind : function() {
							return $("#codeForm [name='codeKind']").val();
						},
						random : function() {
							return Math.random();
						}
					}
				}
			},
			codeName : {
				required : true,
				maxlength : 50,
				remote : {
					url : URL.get("System.CODE_KIND_VALID"),//后台处理程序  
					type : "post", //数据发送方式  
					dataType : "json", //接受数据格式     
					data : { //要传递的数据，默认已传递应用此规则的表单项  
						codeKind : function() {
							return $("#codeForm [name='codeKind']").val();
						},
						random : function() {
							return Math.random();
						}
					}
				}
			}
		},
		messages : {
			codeKind : {
				remote : "该类型已存在，请重新输入"
			}
		},
		//设置错误信息显示到指定位置
		errorPlacement : function(error, element) {
			element = element.parent();
			common.showmassage(error, element);
		},
		success : $.noop,
		submitHandler : function(form) {
			$("#codeForm #codeValue").val(
					$("#codeForm input[name=codeKind]").val());
			$('#codeForm').ajaxPost(dataType.json, function(data) {
				datatable.add(codeTable, data.value);// 添加
				$("#codeModal").modal("hide");
			});
		}
	};

	var codeData;
	//选中行信息
	var codeRow;
	//table对象
	var codeTable;
	//列表配置信息
	var masCodeTableOptions = {
		id : "masCodeTable",
		form : "masCodeQueryForm",
		columns : [ {
			"data" : null,
			"sClass" : "text-center",
			"render" : function(data, type, row, meta) {
				return "<input type='radio' name='tableRadio'  />";
			}
		}, {
			"data" : "codeKind",
			"sClass" : "text-center"
		}, {
			"data" : "codeName",
			"sClass" : "text-center"
		}, {
			"data" : "codeValue",
			"sClass" : "text-center"
		} ],
		rowClick : function(data, row) {
			codeData = data;
			codeRow = row;
			$("#codeId").val(data.codeId);
			$("#codeKind").val(data.codeKind);
		},
		condition : "masCodeCondition"
	};

	$().ready(
			function() {
				codeTable = datatable.table(masCodeTableOptions);
				//表单校验
				$("#codeForm").validate(codeOptions);
				common.requiredHint("codeForm", codeOptions);

				// 按钮点击事件
				$("button[name='operateBtn']").click(
						function() {
							var codeId = $("#codeId").val();
							if (this.id == "addButton") {
								$("#codeModal").modal("show");
							}
							if (this.id == "editButton"
									&& common.isChoose($("#codeKind").val())) {
								common.pageForward(URL
										.get("System.CODE_UPDATE_VIEW")
										+ "?codeKind=" + $("#codeKind").val());
							}
							if (this.id == "removeButton"
									&& common.isChoose(codeId)) {
								removeCode(codeId);
							}
							if (this.id == "search") {
								//codeTable = datatable.table(masCodeTableOptions);
								datatable.search(codeTable, masCodeTableOptions);
							}
						});
			});

	function codeFormSubmit() {
		$("#codeModal").modal("hide");
		$("#codeForm").submit();
	}

	//删除代码表
	function removeCode(codeId) {
		layer.confirm("确定对选中内容执行【删除】操作？", function(data) {
			var url = URL.get("System.CODE_REMOVE");
			var params = "codeId=" + codeId;
			ajax.post(url, params, dataType.json, function(data) {
				if (data.result) {
					common.pageForward(URL.get("SystemPage.CODE_VIEW"));
				} else {
					layer.alert(data.message);
				}
			});
		});
	};
</script>
<body>
	<div class="row">
		<div class="panel-body">
			<div id="masCodeCondition">
				<form id="masCodeQueryForm" action="${common.basepath}/${applicationScope.URL.System.CODE_VIEW_QUERY}" method="post" class="form-horizontal">
				<div class="form-inline">
					<input type="hidden" id="parentCodeKind" name="parentCodeKind" value="0000" /> 
					<input type="hidden" id="parentCodeValue" name="parentCodeValue" value="0000" /> 
					<input type="hidden" id="codeGrade" name="codeGrade" value="1" /> 
					<input type="hidden" id="codeOrder" name="codeOrder" /> 
					<input type="hidden" id="codeValue" name="codeValue" />
						<input type='text' id='codeName' name="codeName" class='form-control' placeholder='请输入代码名称' />
						<button id="search" type="button" name="operateBtn" class="btn btn-default">
							<i class="fa fa-search fa-fw"></i>查询
						</button>
						<div class="vertical-line"></div>
						<button id="addButton" type="button" name="operateBtn" class="btn btn-default">
							<i class="fa fa-plus fa-fw"></i> 增加
						</button>
						<button id="editButton" type="button" name="operateBtn" class="btn btn-default">
							<i class='fa fa-edit fa-fw'></i> 维护
						</button>
						<button id="removeButton" type="button" name="operateBtn" class="btn btn-default">
							<i class='fa fa-remove fa-fw'></i> 删除
						</button>
					</div>
				</form>
			</div>
          </div>
		</div>
		<div class="table-responsive">
			<table class="table table-bordered table-hover" id="masCodeTable">
				<thead>
					<tr class="active">
						<th class="text-center">选择</th>
						<th class="text-center">代码类型（codeKind）</th>
						<th class="text-center">代码名称（codeName）</th>
						<th class="text-center">代码值（codeValue）</th>
					</tr>
				</thead>
			</table>
		</div>



		<input type="hidden" id="codeId" name="codeId" /> <input
			type="hidden" id="codeKind" name="codeKind" />

		<form id="codeForm" class="form-horizontal"
			action="${common.basepath }/${applicationScope.URL.System.CODE_ADD}">
			<input type="hidden" id="parentCodeKind" name="parentCodeKind"
				value="0000" /> <input type="hidden" id="parentCodeValue"
				name="parentCodeValue" value="0000" /> <input type="hidden"
				id="codeGrade" name="codeGrade" value="1" /> <input type="hidden"
				id="codeOrder" name="codeOrder" /> <input type="hidden"
				id="codeValue" name="codeValue" />
			<div id="codeModal" class="modal fade bs-example-modal">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-body">
							<div class="panel panel-lightblue">
								<div class="panel-heading text-center">代码表</div>
								<div class="panel-body">
									<div class="form-group" id="codeType_div">
										<label class="col-xs-4 control-label">级别类型</label>
										<div class="col-xs-6">
											<select id="codeType" name="codeType" class="form-control">
												<option value="2">两级</option>
												<option value="1">多级</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-4 control-label">代码类型</label>
										<div class="col-xs-6">
											<input name="codeKind" class="form-control" maxlength="30"
												type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-4 control-label">代码名称</label>
										<div class="col-xs-6">
											<input id="codeName" name="codeName" class="form-control"
												maxlength="50" type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-4 control-label">代码描述</label>
										<div class="col-xs-6">
											<input id="codeDesc" name="codeDesc" class="form-control"
												maxlength="100" type="text" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-xs-4 control-label">代码备注</label>
										<div class="col-xs-6">
											<textarea id="codeRemark" name="codeRemark"
												class="form-control" maxlength="100"></textarea>
										</div>
									</div>
								</div>
							</div>
							<div class="panel-body text-right" style="padding: 0px;">
								<div class="col-xs-2 col-xs-offset-8 no-right">
									<button type="button" onclick="codeFormSubmit();"
										class="btn btn-primary btn-block">确定</button>
								</div>
								<div class="col-xs-2 no-right">
									<button type="button" class="btn btn-primary btn-block"
										data-dismiss="modal">取消</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
		</form>
</body>
</html>
