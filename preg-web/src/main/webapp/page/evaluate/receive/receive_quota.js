// 自动检索使用检验项目list
var itemListData = [];
// 所有的检验项目的map{检验项目code：检验项目信息}
var itemMap = new Map();
// 所有收费项目的map{收费项目id：收费项目信息}
var inspectMap = new Map();
//已经添加的指标
var readyItemMap = new Map();
// 禁止双击事件使单击触发两次
var clickId;
// 历史检验数据悬浮提示
var differentindex = 999;
$(function(){
	//根据收费项目id初始化收费项目map
	$.each(hospitalInspectPayList, function(index, value){
		value.id = '';
		inspectMap.set(value.inspectId, value);
		
		//根据检验项目code初始化检验项目map
		$.each(value.itemList, function(index, obj){
			obj.id = '';
			obj.inspectId = value.inspectId;
			obj.custId = $("#custId").val();
			obj.itemValue = '';
			obj.itemResult = '';
			itemMap.set(obj.inspectId+"_"+obj.itemId, obj);//同一检验项目可能同时存在于多个收费项目下
		});
	});
	
	//初始化报告单数据
	initLoadReport();
});

/**
 * 初始化加载报告单、收费项目、检验项目信息
 */
function initLoadReport(){
	if(!common.isEmpty(reportList)){
		$.each(reportList, function(index, value){
			addReportHtml(value);
		});
	}
}

/**************************************************************报告单模块start**************************************************************/

/**
 *  添加报告单
 */
function addReport(){
	var isExist = false;
	//判断是否存在空报告单
	$(".report").each(function(i,val){
		if($("#"+val.id+"_items tbody tr[name='noItemTr']").length > 0) {
			layer.alert("已存在空报告单");
			isExist = true;
		}
	});
	
	if(!isExist){
		//添加空报告单
		var url = URL.get("Platform.DIAGNOSIS_REPORT_ADD");
		var params = "diagnosisId=" + $("#diagnosisId").val() + "&custId=" + $("#custId").val();
		ajax.post(url, params, dataType.json, function(data){
			if(!common.isEmpty(data.value)){
				addReportHtml(data.value);
				//新加报告单获得焦点
				var scrollHeight = $(".bodyClass").prop("scrollHeight");
				$(".bodyClass").animate({scrollTop:scrollHeight}, 400);
				$("#"+data.value.reportId+"_inspect").focus();
			}
		}, false, false);
	}
}

/**
 * 删除报告单
 * @param reportId
 */
function removeReport(reportId){
	layer.confirm('是否删除该检验报告？', {btn: ['是','否']},function(layerIndex){
		var url = URL.get("Platform.DIAGNOSIS_ITEM_DELETE");
		var params = "reportId=" + reportId;
		ajax.post(url, params, dataType.json, function(data){
			//删除相应代码块
			$("#"+reportId).remove();
		}, false, false);
		updateDiagnosisAssisStatus();
		layer.close(layerIndex);
	}, function(layerIndex){
		layer.close(layerIndex);
	});
}

/**
 * 将检验项目发送给医生
 */
function sendItemInfo() {
	if($("input[class='item-checkbox']:checked").length > 0){
		//检验日期必填
		var reportDateEmpty = false;
		$(".report").each(function(i,val){
			//医生端添加的空报告单不做校验
			if(!val.disabled){
				//助理端添加的空报告单不做校验  后台删除
				if($("#"+val.id+"_items tbody tr[name='noItemTr']").length == 0) {
					if(common.isEmpty($("#"+val.id+"_date").val())){
						reportDateEmpty = true;
						return;
					}
				}
			}
		});
		if(reportDateEmpty){
			layer.alert("请先填写检验日期");
			return;
		}
		layer.confirm('确认【发送】实验室检验项目到医生端？', {btn: ['是','否']}, function(layerIndex){
			//更新报告单名称
			$(".report").each(function(i,val){
				//空报告单不做名称修改 后台删除
				if($("#"+val.id+"_items tbody tr[name='noItemTr']").length == 0) {
					//未填写名称
					var reportId = val.id;
					var reportName = $("#"+reportId+"_name").val();
					if(common.isEmpty(reportName)){
						var obj = {};
						obj["id"] = reportId+"_name";
						//收费项目名称拼接
						$("#"+reportId+"_inspects ._inspectName").each(function(index,val){
							reportName = reportName+"+"+val.name;
						});
						reportName = reportName.substr(1);
						obj["value"] = reportName;
						
						changeReportInfo(obj,reportId);
					}
				}
			});
			
			//修改检验项目状态
			var ids="";
			$("input[class='item-checkbox']:checked").each(function(i, v){
				if(!v.disabled){
					ids = ids + "," + v.value;
				}
			});
			
			var url = URL.get("Platform.DIAGNOSIS_ITEMS_SEND");
			var params = "custId=" + $("#custId").val() + "&diagnosisId=" + $("#diagnosisId").val() + "&ids=" + ids.substr(1);
			ajax.post(url, params, dataType.json, function(data){
				if(data.value){
					//清空所有报告单内容
					$(".bodyClass").empty();
					readyItemMap = new Map();
					
					//重新加载报告单信息
					var url = URL.get("Platform.DIAGNOSIS_REPORTS_QUERY");
					var params = "custId=" + $("#custId").val() + "&diagnosisId=" + $("#diagnosisId").val();
					ajax.post(url,params, dataType.json,function(data){
						reportList = data.value;
						initLoadReport();
					})
				}
			}, false, false);
			
			//通知医生端
			var url = URL.get("Platform.DIAGNOSIS_ITEM_SEND");
			ajax.post(url, "diagnosisId=" + $("#diagnosisId").val(), dataType.json, function(data){
				opener.refreshDataTable();
				layer.alert("检验项目已发送");
			}, false, false);
			layer.close(layerIndex);
		});
	}else{
		layer.alert("请勾选要发送的检验项目");
	}
}

/**
 * 重新加载报告代码块信息
 * @param itemList
 * @param reportId
 */
function loadReportHtml(itemList,reportId){
	//清空报告单检验信息  重新获取数据
	$("#"+reportId+"_items tbody").empty();
	
	//重新加载检验项目信息
	if(!common.isEmpty(itemList)){
		//超过固定条数  分两表显示
		var sumLength = itemList.length;
		if(sumLength > 10){
			$("#"+reportId+"_item1").attr("class","col-xs-6 padding-zero");
			$("#"+reportId+"_item2").show();
		}else{
			$("#"+reportId+"_item1").attr("class","col-xs-12 padding-zero");
			$("#"+reportId+"_item2").hide();
		}
		
		//添加对应检验项目信息
		$.each(itemList, function(index, value){
			addItemHtml(index,sumLength,value,reportId);
		});
	}else{
		//清空表头信息
		$("#"+reportId+"_items").empty();
	}
}

/**
 * 修改报告单
 * @param obj
 * @param reportId
 */
function changeReportInfo(obj,reportId) {
	var report = {};
	report["reportId"] = reportId;
	//修改名称
	if(obj.id == reportId+'_name'){
		report["reportName"] = obj.value;
	}
	
	//修改检验日期  计算孕周数
	if(obj.id == reportId+'_date'){
		if(!common.isEmpty(obj.value)) {
			report["reportDate"] = obj.value;
			var preg_week = pregnancy.getGestWeeksByLmpDate($("#"+reportId+"_date").val(),d_lmp);
	    	var week = preg_week.split("+")[0];
	    	var day = preg_week.split("+")[1];
	    	
	    	if(week >= 0 && day >=0){
	    		var result = week+'+'+day;
		    	report["gestationalWeeks"] = result;	
		    	//显示孕周数
		    	$("#"+reportId+"_gestationalWeeks span").html(week+' <sup style="font-size: 85%;">+'+day+'</sup>');
	    	}else{
	    		//显示孕周数
	    		report["gestationalWeeks"] = "";
		    	$("#"+reportId+"_gestationalWeeks span").html('');
	    	}
		}
	}
	report = JSON.stringify(report).replace(/%/g, "%25").replace(/\+/g, "%2B");
	
	var url = URL.get("Platform.DIAGNOSIS_REPORT_UPDATE");
	var params = "report=" + report;
	ajax.post(url, params, dataType.json, function(data){
	}, false, false);
}

/**
 * 添加报告单信息html
 * @param report
 */
function addReportHtml(report){
	if(common.isEmpty(report)){
		return;
	}
	var reportId = report.reportId;
	var reportName = report.reportName || "";
	
	var gestationalWeeks = "";
	if(report.gestationalWeeks != null && report.gestationalWeeks.length > 0){
		week = report.gestationalWeeks.split("+")[0];
		day = report.gestationalWeeks.split("+")[1];
		gestationalWeeks = week+' <sup style="font-size: 85%;">+'+day+'</sup>';
	}
	
	//添加到已选报告单
	var reportHtml = 
			"<div id='"+reportId+"' class='report'>"+
		    "	<div class='panel-body form-inline'>"+
			"	        <input type='text' class='form-control' placeholder='请输入收费项目' id='"+reportId+"_inspect'/>"+
			"	        <input type='hidden' id='"+reportId+"_inspect_id'/>"+
			"			<input type='text' class='form-control' placeholder='请输入检验项目' id='"+reportId+"_item'/>"+
			"	        <input type='hidden' id='"+reportId+"_item_id'/>"+
			"	        <button class='btn btn-lightblue' type='button' onclick='addItems(\""+reportId+"\");'>添加</button>"+
			"	        <div class='vertical-line'></div>"+
			"           <input type='text' class='form-control' style='width:18%;' placeholder='检验报告名称' id='"+reportId+"_name' value='"+reportName+"' onblur='changeReportInfo(this,\""+reportId+"\");'/>"+
			"	        <div class='input-group'>"+
			"	            <input id='"+reportId+"_date' class='form-control' placeholder='请选择检验日期' style='background-color: white;' onchange='changeReportInfo(this,\""+reportId+"\");'>"+
			"				<span class='input-group-btn'>"+
			"					<button class='btn btn-primary' type='button' onclick='common.dateShow(\""+reportId+"_date\");'>"+
			"						<i class='fa fa-calendar fa-fw'></i>选择"+
			"					</button>"+
			"				</span>"+
			"	        </div>"+
			"			<div class='input-group' style='margin-left:5px;' id='"+reportId+"_gestationalWeeks'>孕周数： <span></span></div>"+
			"	        <button class='btn btn-lightblue redClass pull-right' type='button' onclick='removeReport(\""+reportId+"\");'>删除检验报告</button>"+
			"	</div>"+
			"	<div class='panel-body form-inline' name='inspects' id='"+reportId+"_inspects'>"+
			"		<div id='"+reportId+"_noInspectDiv'><font color='gray'>请选择收费项目！</font></div></div>"+
			"	<div class='panel-body form-inline padding-zero' name='items' id='"+reportId+"_items'>"+
			"		<div class='col-xs-12 padding-zero' id='"+reportId+"_item1'>"+
			"			<table class='table table-bordered table-hover table-padding-2' style='margin-bottom: 0px;'>"+
			"				<thead>"+
			"					<tr class='active'>"+
			"						<th class='text-center' style='width:5%;'><input id='"+reportId+"_allItemCheckbox1' type='checkbox' onclick='allItemCheck(this,\""+reportId+"\",1)'></th>"+
			"						<th class='text-center' colspan='2' style='width:40%;'>检验项目</th>"+
			"						<th class='text-center' style='width:15%;'>结果</th>"+
			"						<th class='text-center' style='width:10%;'>结论</th>"+
			"						<th class='text-center' style='width:10%;'>单位</th>"+
			"						<th class='text-center' style='width:20%;'>参考范围</th>"+
			"				</thead>"+
			"				<tbody></tbody>"+
			"			</table>"+
			"		</div>"+
			"		<div class='col-xs-6 padding-zero' id='"+reportId+"_item2' style='display:none'>"+
			"			<table class='table table-bordered table-hover table-padding-2' style='margin-bottom: 0px;'>"+
			"				<thead>"+
			"					<tr class='active'>"+
			"						<th class='text-center' style='width:5%;'><input id='"+reportId+"_allItemCheckbox2' type='checkbox' onclick='allItemCheck(this,\""+reportId+"\",2)'></th>"+
			"						<th class='text-center' colspan='2' style='width:40%;'>检验项目</th>"+
			"						<th class='text-center' style='width:15%;'>结果</th>"+
			"						<th class='text-center' style='width:10%;'>结论</th>"+
			"						<th class='text-center' style='width:10%;'>单位</th>"+
			"						<th class='text-center' style='width:20%;'>参考范围</th>"+
			"				</thead>"+
			"				<tbody></tbody>"+
			"			</table>"+
			"		</div>"+
			"	</div>"+
			"</div>";
	$(".bodyClass").append(reportHtml);
	$("#"+reportId+"_gestationalWeeks span").html(gestationalWeeks);
	
	// 初始化日期选择插件
	initTimeDateReceiveQuota(reportId+"_date");
	//检验日期
	$("#"+reportId+"_date").val(report.reportDate);
	//自动补全--收费项目输入框
	autocompleteInspectMethod(reportId);
	
	//添加相应收费项目信息
	if(!common.isEmpty(report.inspectPayList)){
		$("#"+reportId+"_noInspectDiv").addClass("hiddenClass");
		$.each(report.inspectPayList, function(index, value){
			addInspectHtml(value,reportId);
		});
	}
	
	//超过固定条数  分两表显示
	if(!common.isEmpty(report.itemList)){
		//清空报告单检验信息
		$("#"+reportId+"_items tbody").empty();
		var sumLength = report.itemList.length;
		if(sumLength > 10){
			$("#"+reportId+"_item1").attr("class","col-xs-6 padding-zero");
			$("#"+reportId+"_item2").show();
		}
		//添加对应检验项目信息
		$.each(report.itemList, function(index, value){
			addItemHtml(index,sumLength,value,reportId);
		});
	}else{
		$("#"+reportId+"_item1 tbody").append("<tr name='noItemTr'><td colspan='7' style='text-align: center;'><h4>请选择检验项目！</h4></td></tr>");
	}

	//如果添加进来的报告单状态是1,那么禁用指定报告单
	if(report.reportStatus == 1){
		$("#"+reportId).disable();
		$("#"+reportId+" td a").css("pointer-events","none");
		//医生端权限数据 不勾选
		$("#"+reportId+" .item-checkbox").attr("checked",false);
	}
	// 解决样式不生效  
    $("#"+reportId).trigger("create");
}
/**************************************************************报告单模块end**************************************************************/

/**************************************************************收费项目模块start**************************************************************/
/**
 * 收费项目或者指标的添加按钮
 * 情况说明：
 * 如果收费项目和指标都是空，则提示
 * 如果只有收费项目，则批量添加
 * 如果收费项目和指标都有，则单独添加指标
 * @param reportId
 */
function addItems(reportId){
	var inspectId = $("#"+reportId+"_inspect_id").val();//收费项目选中项
	var itemId = $("#"+reportId+"_item_id").val();//检验项目选中项
	
	if(inspectId != null &&  inspectId.length > 0){
		var items = [];
		var inspect = Object.assign({}, inspectMap.get(inspectId));
		//添加具体检验项目
		if(itemId != null &&  itemId.length > 0){
			//多个收费项目下的同一检验项目不重复添加
			if($("tr[id='"+reportId+"_"+itemId+"']").length > 0){
				return;
			}
			item = Object.assign({}, itemMap.get(inspect.inspectId+"_"+itemId));
			items.push(item);
		//添加整个收费项目
		}else{
			if($("div[id='"+reportId+"_"+inspectId+"']").length > 0){
				return;
			}
			$.each(inspect.itemList, function(index, value){
				item = Object.assign({}, itemMap.get(inspect.inspectId+"_"+value.itemId));
				if($("tr[id='"+reportId+"_"+value.itemId+"']").length == 0){
					items.push(item);
				}
			});
		}
		saveItems(items,inspect,reportId);
		updateDiagnosisAssisStatus();
	}else{
		layer.alert("请输入收费项目名称！");
	}
}

/**
 * 删除收费项目
 * @param reportId
 * @param inspectId
 */
function removeInspect(reportId,inspectId){
	layer.confirm('是否删除该收费项目？', {btn: ['是','否']},function(layerIndex){
		var url = URL.get("Platform.DIAGNOSIS_ITEM_DELETE");
		var params = "reportId=" + reportId+ "&inspectId=" + inspectId;
		if($("#"+reportId+"_inspects .btn-group").length == 1){
			params = "reportId=" + reportId;
		}
		ajax.post(url, params, dataType.json, function(data){
			if($("#"+reportId+"_inspects .btn-group").length == 1){
				$("#"+reportId).remove();
			}else{
				//加载剩余检验项目
				$("#"+reportId+"_"+inspectId).remove();
				loadReportHtml(data.value,reportId);
			}
		}, false, false);
		layer.close(layerIndex);
	}, function(layerIndex){
		layer.close(layerIndex);
	});
}

/**
 * 自动补全提示添加收费项目
 * @param reportId
 */
function autocompleteInspectMethod(reportId){
	// 初始化自动补全插件
	common.autocompleteMethod(reportId+"_inspect", inspectMapListData, function(data){
		$("#"+reportId+"_inspect_id").val(data.val.inspectId);// 记录所选收费项目
		$("#"+reportId+"_inspect").blur();// 失去焦点
		autocompleteItemMethod(reportId, data.val.inspectId);// 自动补全提示检验项目
		$("#"+reportId+"_item").attr("disabled", false);
	});
	// 收费项目名称的自动补全提示在获得焦点时清空
	$("#"+reportId+"_inspect").click(function(){
		$(this).val("");
		$("#"+reportId+"_inspect_id").val("");
		$("#"+reportId+"_item").attr("disabled", true).val("");
		$("#"+reportId+"_item_id").val("");
	});
}

/**
 * 添加收费项目信息
 * @param inspect
 * @param reportId
 */
function addInspectHtml(inspect,reportId){
	//存在不添加
	if($("div[id='"+reportId+"_"+inspect.inspectId+"']").length > 0){
		return;
	}
	var inspectId = inspect.inspectId;
	//添加到已选检验项目  ""+
	var inspectPayHtml = 
		"<div class='btn-group' id='"+reportId+"_"+inspectId+"' style='margin-right:10px;'>"+
		"	<input type='hidden' id='"+reportId+"_"+inspectId+"_items' value='"+inspect.numInspectItems+"'>"+
		"	<button type='button' class='btn btn-lightblue _inspectName' name='"+inspect.inspectName+"'>"+inspect.inspectName+"</button>"+
		"	<button type='button' class='btn btn-lightblue redClass' onclick='removeInspect(\""+reportId+"\",\""+inspectId+"\")'"+
		"		<a class='text-right' onclick=''>X</a></td>"+
		"	</button>"+
		"</div>";
	$("#"+reportId+"_inspects").append(inspectPayHtml);
	// 解决样式不生效  
    $("#"+reportId+"_"+inspectId).trigger("create");
}
/**************************************************************收费项目模块end**************************************************************/

/**************************************************************检验项目模块start**************************************************************/

/**
 * 添加检验项目
 * @param items
 * @param inspect
 * @param reportId
 */
function saveItems(items,inspect,reportId){
	if(items.length <= 0){
		return;
	}
	
	var inspectNew = {};
	//收费项目存在不重复添加
	if($("div[id='"+reportId+"_"+inspect.inspectId+"']").length == 0){
		inspectNew["inspectId"]=inspect.inspectId;
		inspectNew["inspectName"]=inspect.inspectName;
		inspectNew = JSON.stringify(inspectNew);
	}else{
		inspectNew = "";
	}
	//添加所选检验项目
	var itemsNew = [];
	$.each(items, function(index, value){
		delete value["hisItemList"];
		delete value["resultJson"];
		itemsNew.push(JSON.stringify(value).replace(/%/g, "%25").replace(/\+/g, "%2B"));
	});
	
	var url = URL.get("Platform.DIAGNOSIS_ITEM_ADD");
	var params = "diagnosisId="+$("#diagnosisId").val()+"&reportId="+reportId+"&inspect="+inspectNew+"&items="+itemsNew.join("###");
	ajax.post(url, params, dataType.json, function(data){
		if(!common.isEmpty(data.value)){
			if($("div[id='"+reportId+"_"+inspect.inspectId+"']").length == 0){
				//添加相应收费项目信息
				$("#"+reportId+"_noInspectDiv").addClass("hiddenClass");
				inspect.numInspectItems = items.length;
				addInspectHtml(inspect,reportId);
			}else{
				var beforeNum = $("#"+reportId+"_"+inspect.inspectId+"_items").val();
				$("#"+reportId+"_"+inspect.inspectId+"_items").val(beforeNum+1);
			}
			//清空报告单检验项目信息  重新获取数据
			$("#"+reportId+"_items tbody").empty();
			//超过固定条数  分两表显示
			var sumLength = data.value.length;
			if(sumLength > 10){
				$("#"+reportId+"_item1").attr("class","col-xs-6 padding-zero");
				$("#"+reportId+"_item2").show();
			}
			//添加对应检验项目信息
			$.each(data.value, function(index, value){
				addItemHtml(index,sumLength,value,reportId);
			});
		}
	}, false, false);
}

/**
 * 更新检验项目（结果、结论等信息）
 * @param reportId
 * @param itemId
 */
function updateItem(reportId,itemId){
	var item ={};
	$("[name^='"+reportId+"_"+itemId+"']").each(function(index, obj){
		var name = $(obj).attr("name").split("_")[2];
		item[name] = $(obj).val();
	});
	item = JSON.stringify(item).replace(/%/g, "%25").replace(/\+/g, "%2B");
	
	var url = URL.get("Platform.DIAGNOSIS_ITEM_UPDATE");
	var params = "reportId=" + reportId + "&item=" + item;
	ajax.post(url, params, dataType.json, function(data){
	}, false, false);
}

/**
 * 删除检验项目
 * @param reportId
 * @param inspectId
 * @param itemId
 */
function removeItem(reportId,inspectId,itemId){
	layer.confirm('是否删除该检验项目？', {btn: ['是','否']},function(layerIndex){
		var beforeNum = $("#"+reportId+"_"+inspectId+"_items").val();
		
		var url = URL.get("Platform.DIAGNOSIS_ITEM_DELETE");
		var params = "reportId=" + reportId+ "&itemId=" + itemId;
		
		//检验项目全部删除时，直接删除报告单
		if($("#"+reportId+"_item1 tbody tr").length == 1){
			params = "reportId=" + reportId;
		}else{
			//收费项目对应的检验项目全部删除时，直接删除收费项目
			if((beforeNum-1) == 0){
				params = "reportId=" + reportId+ "&inspectId=" + inspectId;
			}
		}
		ajax.post(url, params, dataType.json, function(data){
			//检验项目全部删除时，直接删除报告单
			if($("#"+reportId+"_item1 tbody tr").length == 1){
				$("#"+reportId).remove();
			}else{
				//收费项目对应的检验项目全部删除时，直接删除收费项目
				if((beforeNum-1) == 0){
					$("#"+reportId+"_"+inspectId).remove();
				}else{
					readyItemMap.set(itemId, null);
					$("#"+reportId+"_"+inspectId+"_items").val(beforeNum-1);
				}
				//处理此报告单下代码块内容
				loadReportHtml(data.value,reportId);
			}
			
		}, false, false);
		layer.close(layerIndex);
	}, function(layerIndex){
		layer.close(layerIndex);
	});
}

/**
 * 自动补全提示添加检验项目
 * @param reportId
 * @param inspectId
 */
function autocompleteItemMethod(reportId, inspectId){
	var itemListData = [];
	// 获取收费项目下的检验项目
	var url = URL.get("item.INSPECT_PAY_ITEMS_GET");
	var params = "inspectId=" + inspectId;
	ajax.post(url, params, dataType.json, function(data) {
		if(!_.isEmpty(data.value)){
			$.each(data.value,function(index,value){
				itemListData.push({name:value.itemName, val:value});
			});
		}
	}, false, false);
	// 初始化自动补全插件
	common.autocompleteMethod(reportId+"_item", itemListData, function(data){
		$("#"+reportId+"_item_id").val(data.val.itemId);// 保存检验项目id
	});
	// 检验项目的自动补全提示在获得焦点时清空
	$("#"+reportId+"_item").click(function(){
		$("#"+reportId+"_item").val("");
		$("#"+reportId+"_item_id").val("");
	});
}

/**
 * 添加检验项目信息html
 * @param index 
 * @param sumLength 检验项目数量
 * @param item
 * @param reportId
 */
function addItemHtml(index,sumLength,item,reportId){
	//存在不添加
	if($("tr[id='"+reportId+"_"+item.itemId+"']").length > 0){
		return;
	}
	
	var id = item.id;
	var inspectId = item.inspectId;
	var itemId = item.itemId;
	var itemValue = item.itemValue || "";
	var itemResult = item.itemResult || "";
	var itemUnit = item.itemUnit || "————";
	var itemRefValue = item.itemRefValue || "————";
	if(itemMap.has(inspectId+"_"+itemId)){
		readyItemMap.set(id, item);
		var itemHtml = 
			"<tr id='"+reportId+"_"+itemId+"'>"+
			"   <input type='hidden' name='"+reportId+"_"+itemId+"_id' value='"+id+"'/>" +
			"   <input type='hidden' name='"+id+"_itemStatus' value='"+item.itemStatus+"'/>" +
			"   <input type='hidden' id='"+reportId+"_"+item.itemOrder+"'/>" +
			"	<td class='text-center' style='border-left:0;'>"+
			"		<input type='checkbox' value='"+id+"' class='item-checkbox'></td>"+
			"	<td style='border-right:0;width:38%;' class='item-name'>"+
			"		<a>"+item.itemName+"</a></td>"+
			"	<td style='border-left:0;width:2%;'>"+
			"		<a class='text-right redClass' onclick='removeItem(\""+reportId+"\",\""+inspectId+"\",\""+itemId+"\")'>X</a></td>"+
			"	<td style='text-align: center;'>"+
			"		<input type='text' style='width:100%;' class='intake-sm enter-input' name='"+reportId+"_"+itemId+"_itemValue' value='"+itemValue+"' maxlength='30' onkeypress='if(event.keyCode==13) focusNextInput(this,\""+reportId+"\");' onchange='changeItemResult(this,\""+reportId+"\",\""+inspectId+"\",\""+itemId+"\");'></td>"+
			"	<td style='text-align: center;'>"+
			"		<input type='text' style='width:100%;' class='intake-sm' name='"+reportId+"_"+itemId+"_itemResult' value='"+itemResult+"' maxlength='1000' onblur='updateItem(\""+reportId+"\",\""+itemId+"\");'></td>"+
			"	<td>"+
			"		<div style='text-align: center;' id=''>"+itemUnit+"</div></td>"+
			"	<td>"+
			"		<div style='text-align: center;' id=''>"+itemRefValue+"</div></td>"+
			"</tr>";
	
		//检验项目添加到对应位置
		if(sumLength > 10){
			if(sumLength%2 == 0){
				if(index > sumLength/2-1){
					$("#"+reportId+"_item2 tbody").append(itemHtml);
				}else{
					$("#"+reportId+"_item1 tbody").append(itemHtml);
				}
			}else{
				if(index > sumLength/2){
					$("#"+reportId+"_item2 tbody").append(itemHtml);
				}else{
					$("#"+reportId+"_item1 tbody").append(itemHtml);
				}
			}
		}else{
			$("#"+reportId+"_item1 tbody").append(itemHtml);
		}
		
		//结果不为空勾选
		if(!common.isEmpty(itemValue)){
			$("#"+reportId+"_"+itemId+" .item-checkbox").attr("checked",true);
		}
		//结论异常标红
		if(!common.isEmpty(itemResult) ){
			if("↓"==itemResult || "↑"==itemResult){
				$("#"+reportId+"_"+itemId+" a").css("color","red");
				$("#"+reportId+"_"+itemId+" div").css("color","red");
			}
		}
	}
	
	// 解决样式不生效  
    $("#"+reportId+"_"+itemId).trigger("create");
}

/**
 * 获取检验项目结论 
 * @param value
 * @param inspectId
 * @param itemId
 */
function getItemResult(value, inspectId, itemId){
	var itemResult = "";
	//获取检验项目结论的json串
	var item = Object.assign({}, itemMap.get(inspectId+"_"+itemId));
	itemResult = item.resultJson;
	//检验项目没有配置结论
	if(common.isEmpty(itemResult)){
		return;
	}
	var inspectResultJson = JSON.parse(itemResult);
	var infoList = [];
	$.each(inspectResultJson, function(index, val){
		infoList.push({result:index, method:val});
	});
	var result = infoList[0].result;
	if(!common.isEmpty(infoList[1].method.trim()) && eval(value+infoList[1].method)){
		result = infoList[1].result;
	}
	if(!common.isEmpty(infoList[2].method.trim()) && eval(value+infoList[2].method)){
		result = infoList[2].result;
	}
	return result;
}

/**
 * 修改检验项目结论
 * @param obj
 * @param reportId
 * @param inspectId
 * @param itemId
 */
function changeItemResult(obj,reportId,inspectId,itemId){
	//结果不为空
	if(!common.isEmpty(obj.value)){
		//得出结论
		$("input[name='"+reportId+"_"+itemId+"_itemResult']").val(getItemResult(obj.value, inspectId, itemId));
		$("#"+reportId+"_"+itemId+" .item-checkbox").attr("checked",true);
	}else{
		//清空结论
		$("input[name='"+reportId+"_"+itemId+"_itemResult']").val("");
		$("#"+reportId+"_"+itemId+" .item-checkbox").attr("checked",false);
	}
	
	var itemResult = $("input[name='"+reportId+"_"+itemId+"_itemResult']").val();
	//结论异常整行标红
	if("↓"==itemResult || "↑"==itemResult){
		$("#"+reportId+"_"+itemId+" .item-name a").css("color","red");
		$("#"+reportId+"_"+itemId+" div").css("color","red");
	}else{
		$("#"+reportId+"_"+itemId+" .item-name a").css("color","#23527c");
		$("#"+reportId+"_"+itemId+" div").css("color","#333");
	}
	updateItem(reportId,itemId);
}
/**********************上次检验项目start**********************/
/**
 * 上次检验项目（收费项目）
 */
function getLastReportList(){
	$("#lastInspects").empty();
	clearTimeout(clickId);
	clickId = setTimeout(function(){
		if(!common.isEmpty(lastQuotaList)){
			common.modal("lastDiagnosisInspectsModal", "shown.bs.modal", function(){
				$.each(lastQuotaList, function(index, value){
					addLastInspectsHtml(value);
				});
			});
		}else{
			layer.alert("上次检验项目未找到!");
		}
	}, 200);
}

/**
 * 上次检验项目信息html（收费项目）
 * @param inspect
 */
function addLastInspectsHtml(inspect){
	var inspectId = inspect.inspectItemId;
	var inspectName = inspect.inspectItemName;
	$("#lastInspects").append(
		"<tr><td>"+
		"	<label class='label-checkbox' style='padding-left:8px;'><input type='checkbox' style='margin-right:10px;' class='inspect-checkbox' style='margin-right: 5px;' value='"+inspectId+"'>"+inspectName+"</label>"+
		"</td></tr>");
}

/**
 * 根据所选收费项目组合添加报告单
 */
function saveReportFromlastInspects(){
	if($("input[class='inspect-checkbox']:checked").length > 0){
		//报告单名称拼接
		var reportName = $("#newReportName").val();
		if(common.isEmpty(reportName)){
			$("input[class='inspect-checkbox']:checked").each(function(i, v){
				reportName = reportName+"+"+v.name;
			});
			reportName = reportName.substr(1);
		}
		
		var inspects = [];
		var items = [];
		$("input[class='inspect-checkbox']:checked").each(function(i, v){
			var inspect = Object.assign({}, inspectMap.get(v.value));
			//收费项目
			var inspectNew = {};
			inspectNew["inspectId"]=inspect.inspectId;
			inspectNew["inspectName"]=inspect.inspectName;
			inspects.push(JSON.stringify(inspectNew).replace(/%/g, "%25").replace(/\+/g, "%2B"));
			//检验项目
			$.each(inspect.itemList, function(index, value){
				item = Object.assign({}, itemMap.get(inspect.inspectId+"_"+value.itemId));
				delete item["hisItemList"];
				delete item["resultJson"];
				items.push(JSON.stringify(item).replace(/%/g, "%25").replace(/\+/g, "%2B"));
			})
		});
		
		//添加报告单
		var url = URL.get("Platform.DIAGNOSIS_REPORT_ADD");
		var params = "diagnosisId="+$("#diagnosisId").val() + "&custId=" + $("#custId").val() + "&reportName=" + reportName + "&inspects=" + inspects.join("###") + "&items=" + items.join("###");
		ajax.post(url, params, dataType.json, function(data){
			if(!common.isEmpty(data.value)){
				addReportHtml(data.value);
				$("#lastDiagnosisInspectsModal").modal("hide");
				//新加报告单获得焦点
				var scrollHeight = $(".bodyClass").prop("scrollHeight");
				$(".bodyClass").animate({scrollTop:scrollHeight}, 400);
				$("#"+data.value.reportId+"_inspect").focus();
			}
		}, false, false);
	}else{
		layer.alert("请先勾选收费项目");
	}
}
/**********************上次检验项目end**********************/
/**************************************************************检验项目模块end**************************************************************/

/**************************************************************工具类start****************************************************************/
/**
 * 回车键将焦点定义到下一个输入框
 * @param thisInput
 * @param reportId
 */
function focusNextInput(thisInput,reportId){
	var inputs = $("#"+reportId).find(".enter-input");
	for(var i = 0;i<inputs.length;i++){
		// 如果是最后一个，则焦点回到第一个
		if(i==(inputs.length-1)){
			inputs[0].focus();
			break;
		}else if(thisInput == inputs[i]){
			inputs[i+1].focus();
			break;
		}
	}
}

/**
 * 检验项目全选按钮
 * @param obj
 * @param reportId
 * @param num
 */
function allItemCheck(obj,reportId,num) {
	$("#"+reportId+"_item"+num+" input[class='item-checkbox']").each(function(index, v){
		$(v).attr("checked", obj.checked);
	});
}

/**
 * 禁用区域内的元素
 */
$.fn.disable = function() {
	return $(this).find("*").each(function() {
		$(this).attr("disabled", true); 
	});
}

/**
 * 启用区域内的元素
 */
$.fn.enable = function() {
	return $(this).find("*").each(function() {
		$(this).removeAttr("disabled");
	});
}

/**
 * 初始化时间插件(选择时间的范围是末次月经~预产期之间)
 * @param id
 */
function initTimeDateReceiveQuota(id) {
	common.initDate(null,null,null,"#"+id);
//    $("#"+id).datetimepicker("setStartDate",d_lmp);// 末次月经
//    $("#"+id).datetimepicker("setEndDate",d_due);// 预产期    
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
    $("#"+id).datetimepicker("setEndDate",nowDate);// 今天
}
/**************************************************************工具类end****************************************************************/
