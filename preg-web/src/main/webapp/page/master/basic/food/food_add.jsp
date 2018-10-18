<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<link rel="stylesheet"
	href="${common.basepath}/common/plugins/kindeditor-4.1.11/themes/default/default.css" />
<link rel="stylesheet"
	href="${common.basepath}/common/plugins/kindeditor-4.1.11/plugins/code/prettify.css" />
<script charset="utf-8"
	src="${common.basepath}/common/plugins/kindeditor-4.1.11/kindeditor-all.js"></script>
<script charset="utf-8"
	src="${common.basepath}/common/plugins/kindeditor-4.1.11/lang/zh-CN.js"></script>
<script charset="utf-8"
	src="${common.basepath}/common/plugins/kindeditor-4.1.11/plugins/code/prettify.js"></script>

<link type=text/css rel="stylesheet"
	href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
<script type="text/javascript"
	src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script>
<link rel="stylesheet"
	href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" />
<title>增加食谱</title>
<script type="text/javascript">
	var serverPath = URL.get("resource.server.path")
			+ URL.get("path.constants.food_ext_image");

	$.validator.addMethod("twoDigitEle", function(value,element) {
		if(element.value.trim()==""){
			return true;
		}
		return new RegExp("^[0-9]+(.[0-9]{1,2})?$").test(element.value) && element.value < 100000;
		}, '必须小于100000，最多支持两位小数');
	
	/**
	 * 验证参数设置
	 */
	var cuisineOperation = {
		rules : {
			foodName : {
				required : true,
				maxlength : 80
			},
			foodType : {
				required : true
			},
			foodCuisine : {
				required : true
			},
			foodStaple : {
				required : true
			},
			foodGi : {
				required : true
			},
			foodLevel : {
				required : true
			},
			foodEsculent : {
				required : true,
				number : true
			},
			foodEnergy : {
				required : true,
				number : true
			},
			foodProtid : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodFat : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodSfa : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodMfa : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodPfa : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodCbr : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodDf : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodAshc : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodVa : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodCarotin : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodCho : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodVb1 : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodVb2 : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodAf : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodVc : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodVe : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEca : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEp : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEk : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEna : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEmg : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEfe : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEzn : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEse : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEcu : {
				required : true,
				number : true,
				twoDigitEle:true
			},
			foodEmn : {
				required : true,
				number : true,
				twoDigitEle:true
			}
		},
		//设置错误信息显示到指定位置
		errorPlacement : function(error, element) {
			element = element.parent();
			common.showmassage(error, element);
		},
		success : $.noop,
		submitHandler : function() {
			//校验是否上传图片
			var picPath = $("#foodPic").val();
			if ("" == picPath || null == picPath) {
				layer.alert("请上传图片");
				return;
			}
			//校验是否添加食材原料
			if (!addfmlAmount()) {
				return;
			}
			var extCount = $("#foodCount").val();
			if ("" == extCount || null == extCount || extCount < 1) {
				layer.alert("请添加食材原料");
				return;
			}
			layer.confirm("确定要执行【保存】操作？",function(data) {
									$('#addCuisineForm').ajaxPost(dataType.json,function(data) {
															if (data.result) {
																common.pageForward(PublicConstant.basePath+ "/page/food/food_cuisine_query.action");
															} else {
															layer.alert(data.message);
															}
									});
			});
		}
	};

	//datatable配置
	var fmDate;
	var fmRow;
	var fmTable;

	var fmdatableOption = {
		id : "fmListTable",
		form : "fmQuery",
		bServerSide: true,
		iDisplayLength : 5,// 每页显示5条数据
		columns : [
				{
					"data" : null,
					"sClass" : "text-center",
					"render" : function(data, type, row, meta) {
						return "<a href='#'><img style='width:50px;height:50px;' src='"+serverPath + data.fmPic+"'></a>";
					}
				}, {
					"data" : "fmName",
					"sClass" : "text-center"
				},
				{
					"data" : "fmAlias",
					"sClass" : "text-left"
				}, {
					"data" : "fmType",
					"sClass" : "text-left",
					"render" : function(data, type, row, meta) {
						return CODE.transCode("FOOD_MATERIAL_TYPE", data);
					}
				},
		 	  		{"data": null,"sClass": "text-left",
		 				"render": function (data, type, row, meta) {
		 					return "<input class='btn btn-primary' type='button' onclick=addFoodExt('"+data.fsId+"','"+data.fmId+"','"+data.fmName+"') value='添加'>"; 
		 				}
		 		  	}
		],
		rowClick : function(data, row) {
			fmData = data;
			fmRow = row;
			$("#fmId").val(data.fmId);
		},
		condition : "fmCondition",
		selecttarget : []
	};

	$().ready(function() {
				$("#addCuisineForm").validate(cuisineOperation);
				//加入必填项提示
				common.requiredHint("addCuisineForm", cuisineOperation);
				//初始化烹饪方式
				common.initCodeSelect("COKE_MODE", "COKE_MODE", "foodCuisine");
				//初始化食谱结构类型
				common.initCodeSelect("FOOD_STAPLE", "FOOD_STAPLE","foodStaple");
				//初始化食谱类型
				common.initCodeSelect("FOOD_TYPE", "FOOD_TYPE", "foodType");
				 	fmTable = datatable.table(fmdatableOption);	
				// 初始化 uploadifive 上传控件
				common.uploadifive("上传食物图片", function(data) {
					//var food = JSON.parse(data);
					$('#foodPic').val(data);
					$('#showpic').attr("src", data);
				});
				$("button[name='fmPage']").click(function() {
					if(this.id=='searchss') {
						fmTable = datatable.table(fmdatableOption);	
					}
				});
				$("#backButton").click(
						function() {
							common.pageForward(PublicConstant.basePath+ "/page/food/food_cuisine_query.action");
						});
			});

	//js添加菜谱的食材组成原料
	function addFoodExt(fsId, foodId, foodName) {
		$("#initExt").remove();
		if ($("#" + foodId).length == 0) {
			var exthtml = "<tr id='tr"+foodId+"'>"
					+ "<td>"
					+ foodName
					+ "</td>"
					+ "<td><input type='text' class='form-control' name='"
					+ foodName
					+ "' id='"
					+ foodId
					+ "' onfocus=recordFoodAmount('"
					+ foodId
					+ "') onblur=checkFoodAmount('"
					+ foodId
					+ "') value='100' maxlength='5' placeholder='请输入食材重量'/></td>"
					+ "<td align='center'><label><input type='radio' name='radio"+foodId+"' value='primary' checked />主料</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "<label><input type='radio' name='radio"+foodId+"' value='secondary'/>辅料</label></td>"
					+ "<td align='center'><input class='btn btn-primary' type='button' onclick=removeFoodExt('"
					+ foodId + "') value='删除'></td>" + "</tr>";
			$("#addFoodExt").append(exthtml);
			if ($("#fmIdTmp").val() == null || $("#fmIdTmp").val() == "") {
				$("#fmIdTmp").val(foodId);
			} else {
				$("#fmIdTmp").val($("#fmIdTmp").val() + "," + foodId);
			}
		} else {
			$("#" + foodId).val(
					parseFloat($("#" + foodId).val()) + parseFloat(100));
		}
	}

	//记录初始食物重量
	var foodAmountOld;

	/**
	 * 记录初始食物重量
	 */
	function recordFoodAmount(foodId) {
		foodAmountOld = $("#" + foodId).val();
	}

	/**
	 * 校验输入的食物重量
	 */
	function checkFoodAmount(foodId) {
		var reg = /^[0-9]*$/;
		var foodAmount = $("#" + foodId).val();
		if (common.isEmpty(foodAmount)) {
			layer.alert("食物重量不能为空！", {
				title : '提示信息'
			});
			$("#" + foodId).val(foodAmountOld);
			return false;
		}
		if (!reg.test(foodAmount)) {
			layer.alert("食材重量请输入正整数！", {
				title : '提示信息'
			});
			$("#" + foodId).val(foodAmountOld);
			return false;
		}
		return true;
	}

	//把所有的食材数量都存入到字符串中
	function addfmlAmount() {
		var reg = /^[0-9]*$/;
		var arrayIdTmp = $("#fmIdTmp").val().split(",");
		var finalIdTmp = new Array();
		finalIdTmp[0] = arrayIdTmp[0];
		//排除重复的ID
		for (var m = 0; m < arrayIdTmp.length; m++) {
			var aa = 1;
			for (var n = 0; n < finalIdTmp.length; n++) {
				if (arrayIdTmp[m] != arrayIdTmp[n]) {
					aa = 1;
				} else {
					aa = 0;
					break;
				}
			}
			if (aa == 1) {
				finalIdTmp[finalIdTmp.length] = arrayIdTmp[m];
			}
		}

		//遍历保存前初始化为空
		$("#fmId").val("");

		//循环校验是否存在，存在就保存
		for (var i = 0; i < finalIdTmp.length; i++) {
			if ($("#" + finalIdTmp[i]).length == 0) {
				continue;
			}
			if ($("#fmId").val() == null || $("#fmId").val() == "") {
				$("#fmId").val(finalIdTmp[i]);
			} else {
				$("#fmId").val($("#fmId").val() + "," + finalIdTmp[i]);
			}
		}

		//清空
		$("#fmIdTmp").val("");
		$("#foodCount").val("");
		$("#fmlAmount").val("");
		$("#fmlType").val("");

		var arrayId = $("#fmId").val().split(",");

		//重新记录临时fmId和fsId的值
		for (var s = 0; s < arrayId.length; s++) {
			if ($("#fmIdTmp").val() == null || $("#fmIdTmp").val() == "") {
				$("#fmIdTmp").val(arrayId[s]);
			} else {
				$("#fmIdTmp").val($("#fmIdTmp").val() + "," + arrayId[s]);
			}
		}
		//记录各个食材原料的重量和主辅料类型
		for (var j = 0; j < arrayId.length; j++) {
			if ($("#" + arrayId[j]).val() == null
					|| $("#" + arrayId[j]).val() == "") {
				layer.alert("食材重量不能为空！");
				return false;
			}
			if (!reg.test($("#" + arrayId[j]).val())) {
				layer.alert("食材重量格式不正确！");
				return false;
			}
			if ($("#fmlAmount").val() == null || $("#fmlAmount").val() == "") {
				$("#fmlAmount").val($("#" + arrayId[j]).val());
				$("#fmlType").val(
						$("input:radio[name='radio" + arrayId[j]+ "'][checked]").val());
			} else {
				$("#fmlAmount").val(
								$("#fmlAmount").val() + ","+ $("#" + arrayId[j]).val());
				$("#fmlType").val(
						$("#fmlType").val()+ ","+ $("input:radio[name='radio" + arrayId[j]+ "'][checked]").val());
			}
		}
		//记录食物组合数量
		if ($("#fmId").val() == "") {
			$("#foodCount").val("0");
		} else {
			$("#foodCount").val(arrayId.length);
		}
		return true;
	}

	//食材初始化提示信息
	function addInitExt() {
		if ($("#addFoodExt").find("tr").length == 0) {
			var initExt = "<tr id='initExt'>"
					+ "<td colspan='3' align='center'><font color='red'>请添加菜谱的组成原料!</font></td>"
					+ "</tr>";
			$("#addFoodExt").append(initExt);
		}
	}

	//组成原料的删除操作
	function removeFoodExt(foodId) {
		$("#tr" + foodId).remove();
		addInitExt();
	}

	//插件输入文本框
	var keditor;
	KindEditor.ready(function(K) {
				keditor = K.create(
								'#foodMake',
								{
									cssPath : '${common.basepath}/common/plugins/kindeditor-4.1.11/plugins/code/prettify.css',
									uploadJson : '${common.basepath}/common/plugins/kindeditor-4.1.11/jsp/upload_json.jsp',
									fileManagerJson : '${common.basepath}/common/plugins/kindeditor-4.1.11/jsp/file_manager_json.jsp',
									allowFileManager : true,
									items : [ 'undo', 'redo', '|',
											'justifyleft', 'justifycenter',
											'justifyright', 'justifyfull',
											'insertorderedlist',
											'insertunorderedlist', 'indent',
											'outdent', 'clearhtml',
											'selectall', '|', 'formatblock',
											'fontname', 'fontsize', '|',
											'forecolor', 'hilitecolor', 'bold',
											'italic', 'underline',
											'strikethrough', 'lineheight',
											'removeformat', '|', 'image',
											'table', 'link', 'unlink' ],
									afterCreate : function() {
									},
									afterBlur : function() {
										var s = keditor.text();
										document.getElementById("foodMake").value = s;
									}
								});
				prettyPrint();
			});
</script>
</head>
<body>
<div class="container-fluid">
	<div class="row row-top">
		<div class="col-xs-12" >
			<div class="panel panel-primary">
				<div class="panel-heading">
					<i class="fa fa-filter fa-fw"></i>食谱原料组成
				</div>
				<div class="panel-body">
					<div class="col-xs-12" style="padding-bottom: 10px;">
						<div id="fmCondition">
							<form action="${common.basepath }/${applicationScope.URL.Master.FOOD_MATERIAL_AJAX_QUERY}"
								id="fmQuery" method="post" class="form-horizontal">
								<div class="form-inline">
									<input id="fmNamess" name="fmNamess" class="form-control"
										type="text" placeholder="请输入检索内容" />
									<button type="button" id="searchss" name="fmPage" class="btn btn-default">
										<i class="fa fa-search fa-fw"></i>查询
									</button>
								</div>
					 		</form> 
						</div>
					</div>
					<div class="col-xs-6">
						<div class="table-responsive">
							<table class="table table-bordered table-hover" id="fmListTable">
								<thead>
									<tr class="active">
										<th class='text-center'>原料图片</th>
										<th class='text-center'>原料名称</th>
										<th class='text-center'>原料别名</th>
										<th class='text-center'>原料种类</th>
										<th class='text-center'>操作</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="table-responsive">
							<table class="table table-bordered table-hover">
								<thead>
									<tr class="active">
										<th class="text-center">食物名称</th>
										<th class="text-center">食材重量</th>
										<th class="text-center">主料/辅料</th>
										<th class="text-center">操作</th>
									</tr>
								</thead>
								<tbody id="addFoodExt"></tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

			<form action="${common.basepath }/${applicationScope.URL.Master.FOOD_CUISINE_ADD}"
				id="addCuisineForm" name="addCuisineForm" method="post">
				<input type="hidden" id="fmId" name="fmId" /> 
				<input type="hidden" id="fmIdTmp" name="fmIdTmp" /> 
				<input type="hidden" id="fmlAmount" name="fmlAmount" /> 
				<input type="hidden" id="foodCount" name="foodCount" /> 
				<input type="hidden" id="fmlType" name="fmlType" />
				<div class="panel panel-primary">
					<div class="panel-heading">
						<i class="fa fa-table fa-fw"></i> 食谱信息
					</div>
					<div class="panel-body">
						<table class="table table-condensed">
							<tbody>
								<tr>
									<td rowspan="10" width="6%"><label class="control-label"
										for="图片"></label></td>
									<td rowspan="10"><img class="img-thumbnail" id="showpic"
										style="width:100px;height:100px;margin-bottom:5px;" /> <input
										type="file" name="file_upload" id="file_upload" /> <input
										type="hidden" name="foodPic" id="foodPic" /></td>
									<td width="15%"><label class="control-label text-right"
										for="食物名称">食物名称</label></td>
									<td width="40%"><input type="text" class="form-control"
										id="foodName" name="foodName" placeholder="请输入食物名称" /></td>
									<td width="25%"></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="食品别名">食品别名</label></td>
									<td><input type="text" class="form-control"
										name="foodAlias" placeholder="请输入食品别名,号分割" /></td>
								</tr>
								<tr>
									<td><label class="control-label" for="食谱类型">食谱类型</label></td>
									<td><select class="form-control" id="foodType"
										name="foodType"></select></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="口味">口味</label></td>
									<td><input type="text" class="form-control"
										name="foodTaste" placeholder="请输入口味" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="条码">条码</label></td>
									<td><input type="text" class="form-control"
										name="foodBarcode" placeholder="请输入条码" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="烹饪方式">烹饪方式</label></td>
									<td><select class="form-control" id="foodCuisine"
										name="foodCuisine"></select></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="食谱结构类型">食谱结构类型</label></td>
									<td><select class="form-control" id="foodStaple"
										name="foodStaple"></select></td>
								</tr>
								<tr>
									<td><label class="control-label text-right">是否推荐</label></td>
									<td><label class="radio-inline"><input
											id="foodIsMakeNo" name="foodIsMake" type="radio" value="0"
											checked />否&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;</label> <label
										class="radio-inline"><input id="foodIsMakeYes"
											name="foodIsMake" type="radio" value="1" />是</label></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="有益成分">有益成分</label></td>
									<td><label class="checkbox-inline"><input
											id="foodBenefitList" name="foodBenefitList" type="checkbox"
											value="30" />ω-3&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;</label> <label
										class="checkbox-inline"><input id="foodBenefitList"
											name="foodBenefitList" type="checkbox" value="31" />碘
											&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;</label> <label
										class="checkbox-inline"><input id="foodBenefitList"
											name="foodBenefitList" type="checkbox" value="32" />抗肿瘤</label></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="有害成分">有害成分</label></td>
									<td><label class="checkbox-inline"><input
											id="foodHarmList" name="foodHarmList" type="checkbox"
											value="50" />草酸&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;</label> <label
										class="checkbox-inline"><input id="foodHarmList"
											name="foodHarmList" type="checkbox" value="51" />麸质&nbsp;&nbsp;
											&nbsp;&nbsp; &nbsp;&nbsp;</label> <label class="checkbox-inline"><input
											id="foodHarmList" name="foodHarmList" type="checkbox"
											value="52" />嘌呤 &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;</label> <label
										class="checkbox-inline"><input id="foodHarmList"
											name="foodHarmList" type="checkbox" value="53" />刺激性&nbsp;&nbsp;
											&nbsp;&nbsp; &nbsp;&nbsp;</label> <label class="checkbox-inline"><input
											id="foodHarmList" name="foodHarmList" type="checkbox"
											value="54" />十字花科</label></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for=""></label></td>
									<td></td>
									<td><label class="control-label text-right" for="功效">功效</label></td>
									<td><textarea class="form-control" name="foodEfficacy"
											placeholder="请输入功效"></textarea></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for=""></label></td>
									<td></td>
									<td><label class="control-label text-right" for="做法">做法</label></td>
									<td><textarea id="foodMake" class="form-control"
											name="foodMake" placeholder="请输入做法"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

				<div class="panel panel-primary">
					<div class="panel-heading">
						<i class="fa fa-table fa-fw"></i> 食谱元素信息（每100g含量）
					</div>
					<div class="panel-body">
						<table class="table table-condensed">
							<tbody>
								<tr>
									<td><label class="control-label text-right" for="食物级别">食物级别</label></td>
									<td><select id="foodLevel" name="foodLevel"
										class="form-control">
											<option value="">==食物级别==</option>
											<option value="green">绿色</option>
											<option value="yellow">黄色</option>
											<option value="red">红色</option>
									</select></td>
									<td><label class="control-label text-right"
										for="可食用部分（%）">可食用部分（%）</label></td>
									<td><input type="text" class="form-control"
										id="foodEsculent" name="foodEsculent" value="100"
										placeholder="请输入可食用部分（%）" /></td>
									<td><label class="control-label" for="能量_kcal">能量(kcal)</label></td>
									<td><input type="text" class="form-control"
										id="foodEnergy" name="foodEnergy" value="0"
										placeholder="请输入能量(kcal)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="蛋白质(g)">蛋白质(g)</label></td>
									<td><input type="text" class="form-control"
										id="foodProtid" name="foodProtid" value="0"
										placeholder="请输入蛋白质(g)" /></td>
									<td><label class="control-label text-right" for="脂肪(g)">脂肪(g)</label></td>
									<td><input type="text" class="form-control" id="foodFat"
										name="foodFat" value="0" placeholder="请输入脂肪(g)" /></td>
									<td><label class="control-label text-right"
										for="饱和脂肪酸(g)">饱和脂肪酸(g)</label></td>
									<td><input type="text" class="form-control" id="foodSfa"
										name="foodSfa" value="0" placeholder="请输入饱和脂肪酸(g)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right"
										for="单不饱和脂肪酸(g)">单不饱和脂肪酸(g)</label></td>
									<td><input type="text" class="form-control" id="foodMfa"
										name="foodMfa" value="0" placeholder="请输入单不饱和脂肪酸(g)" /></td>
									<td><label class="control-label" for="多不饱和脂肪酸(g)">多不饱和脂肪酸(g)</label></td>
									<td><input type="text" class="form-control" id="foodPfa"
										name="foodPfa" value="0" placeholder="请输入多不饱和脂肪酸(g)" /></td>
									<td><label class="control-label" for="碳水化合物(g)">碳水化合物(g)</label></td>
									<td><input type="text" class="form-control" id="foodCbr"
										name="foodCbr" value="0" placeholder="请输入碳水化合物(g)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="膳食纤维(g)">膳食纤维(g)</label></td>
									<td><input type="text" class="form-control" id="foodDf"
										name="foodDf" value="0" placeholder="请输入膳食纤维(g)" /></td>
									<td><label class="control-label" for="灰分(g)">灰分(g)</label></td>
									<td><input type="text" class="form-control" id="foodAshc"
										name="foodAshc" value="0" placeholder="请输入灰分(g)" /></td>
									<td><label class="control-label text-right"
										for="维生素A(ug)">维生素A(ug)</label></td>
									<td><input type="text" class="form-control" id="foodVa"
										name="foodVa" value="0" placeholder="请输入维生素A(ug)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right"
										for="胡萝卜素(μg)">胡萝卜素(μg)</label></td>
									<td><input type="text" class="form-control"
										id="foodCarotin" name="foodCarotin" value="0"
										placeholder="请输入胡萝卜素(μg)" /></td>
									<td><label class="control-label text-right" for="胆固醇(mg)">胆固醇(mg)</label></td>
									<td><input type="text" class="form-control" id="foodCho"
										name="foodCho" value="0" placeholder="请输入胆固醇(mg)" /></td>
									<td><label class="control-label" for="维生素B1(mg)">维生素B1(mg)</label></td>
									<td><input type="text" class="form-control" id="foodVb1"
										name="foodVb1" value="0" placeholder="请输入维生素B1(mg)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right"
										for="维生素B2(mg)">维生素B2(mg)</label></td>
									<td><input type="text" class="form-control" id="foodVb2"
										name="foodVb2" value="0" placeholder="请输入维生素B2(mg)" /></td>
									<td><label class="control-label text-right" for="烟酸(mg)">烟酸(mg)</label></td>
									<td><input type="text" class="form-control" id="foodAf"
										name="foodAf" value="0" placeholder="请输入烟酸(mg)" /></td>
									<td><label class="control-label" for="维生素C">维生素C</label></td>
									<td><input type="text" class="form-control" id="foodVc"
										name="foodVc" value="0" placeholder="请输入维生素C" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="维生素E">维生素E</label></td>
									<td><input type="text" class="form-control" id="foodVe"
										name="foodVe" value="0" placeholder="请输入维生素E" /></td>
									<td><label class="control-label text-right" for="钙(mg)">钙(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEca"
										name="foodEca" value="0" placeholder="请输入钙(mg)" /></td>
									<td><label class="control-label" for="磷(mg)">磷(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEp"
										name="foodEp" value="0" placeholder="请输入磷(mg)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="钾(mg)">钾(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEk"
										name="foodEk" value="0" placeholder="请输入钾(mg)" /></td>
									<td><label class="control-label text-right" for="钠(mg)">钠(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEna"
										name="foodEna" value="0" placeholder="请输入钠(mg)" /></td>
									<td><label class="control-label" for="镁(mg)">镁(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEmg"
										name="foodEmg" value="0" placeholder="请输入镁(mg)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="铁(mg)">铁(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEfe"
										name="foodEfe" value="0" placeholder="请输入铁(mg)" /></td>
									<td><label class="control-label text-right" for="锌(mg)">锌(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEzn"
										name="foodEzn" value="0" placeholder="请输入锌(mg)" /></td>
									<td><label class="control-label" for="硒(μg)">硒(μg)</label></td>
									<td><input type="text" class="form-control" id="foodEse"
										name="foodEse" value="0" placeholder="请输入硒(μg)" /></td>
								</tr>
								<tr>
									<td><label class="control-label text-right" for="铜(mg)">铜(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEcu"
										name="foodEcu" value="0" placeholder="请输入铜(mg)" /></td>
									<td><label class="control-label text-right" for="钠(mg)">锰(mg)</label></td>
									<td><input type="text" class="form-control" id="foodEmn"
										name="foodEmn" value="0" placeholder="请输入锰(mg)" maxlength="5"/></td>
									<td></td>
									<td></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="form-group text-center">
					<input id="add" class="btn btn-primary" type="submit"
						value=" 保 存 " /> <input id="backButton" class="btn btn-primary"
						type="button" value=" 返 回 " />
				</div>
			</form>

		</div>
	</div>
</div>
</body>
</html>
