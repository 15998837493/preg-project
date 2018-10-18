
var historyReportMap = null;

/**
 * 自动补全诊断项目
 */
function autocompleteDisease(){
	var url = URL.get("item.ITEM_QUERY");
	ajax.post(url, null, dataType.json, function(data){
		let itemNameListData = [];
		for(let x=0;x<data.data.length;x++) {
			itemNameListData.push({name:data.data[x].itemName, val:data.data[x]});
		}
		// 诊断项目添加到自动补全
		common.autocompleteMethod("historyItemReportName", itemNameListData, null);
	}, false, false);
}

$().ready(function(){
	// 初始化自动补全
	autocompleteDisease();
	// 初始化接诊日期
	initTimeDateQuotaHistory("historyDiagnosisDate");
	// 初始化检验日期
	initTimeDateQuotaHistory("historyReportDate");
	// 查询历史检验报告
	$("#historyReportSearchButton").on("click", function(){
		$("#history-report-detail-div").empty().hide();
		$("#history-line-div").hide();
		historyReportMap = new Map();
		$("#historyReportForm #historyReportCustId").val(diagnosisJson.diagnosisCustomer);
		$("#historyReportForm").ajaxPost(dataType.json, function(data){
			$("#history-report-tbody").html(`<tr id="history-report-tbody-tr"><td class="text-center"><h4>没有找到记录！</h4></td></tr>`);
			if(!_.isEmpty(data.value)){
				$.each(data.value, function(index, report){
					addHistoryReportHtml(report);// 添加html
					historyReportMap.set(report.reportId, report);// 记录检验报告
				});
			}
		}, false, true, URL.get("Platform.DIAGNOSIS_HOSPITAL_REPORT_QUERY"));
	});
	// 查询历史检验项目
	$("#historyItemSearchButton").on("click", function(){
		$("#historyItemForm #historyItemCustId").val(diagnosisJson.diagnosisCustomer);
		$("#historyItemForm").ajaxPost(dataType.json, function(data){
			$("#history-item-tbody").html(`<tr id="history-item-tbody-tr"><td class="text-center"><h4>没有找到记录！</h4></td></tr>`);
			if(!_.isEmpty(data.value)){
				$.each(data.value, function(index, item){
					addHistoryItemHtml(++index, item);// 添加html
				});
			}
		}, false, true, URL.get("Platform.DIAGNOSIS_HOSPITAL_ITEM_QUERY"));
	});
	
	$("#historyItemReportName").keypress(function(event){
		if(event.which == 13){
			return false;
		}
	});
});

/**
 * 初始化时间插件(选择时间的范围是小于今天)
 */
function initTimeDateQuotaHistory(id) {
	common.initDate(null,null,null,"#"+id);
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
    $("#"+id).datetimepicker("setEndDate",nowDate);// 今天
}

/**
 * 添加历史检验报告html
 * @param report
 * @returns
 */
function addHistoryReportHtml(report){
	if(!_.isEmpty(report.reportDate) && !_.isEmpty(report.reportName)){
		$("#history-report-tbody-tr").remove();
		var html = `
			<tr>
				<td class="text-center" style="width: 5%;">
					<input type="checkbox" name="historyReportCheckbox" value="${report.reportId}" onchange="getHistoryReportDetail(this, '${report.reportId}');">
				</td>
				<td class="text-left" style="width: 30%;"><a onclick="getHistoryReportDetail(this, '${report.reportId}');">${report.reportName}</a></td>
				<td class="text-center" style="width: 15%;">${report.reportDate}</td>
				<td class="text-center" style="width: 15%;">${report.gestationalWeeks}</td>
				<td class="text-center" style="width: 15%;">${report.diagnosisDoctor}</td>
				<td class="text-center" style="width: 15%;">${report.diagnosisDate}</td>
			</tr>
		`;
		$("#history-report-tbody").append(html);
	}
}

/**
 * 展示检验报告内容
 * @param obj
 * @param reportId
 * @returns
 */
function getHistoryReportDetail(obj, reportId){
	var flag = true;
	if(obj.tagName == "INPUT" && obj.type == "checkbox"){
		if(!obj.checked){
			flag = false;
			$("#"+reportId+"-history-report-item-table").remove();
		}
	} else{
		var $checkboxObj = $("#history-report-tbody input:checkbox[name='historyReportCheckbox'][value='"+reportId+"']");
		$checkboxObj.click();
		return false;
	}
	
	if(flag){
		ajax.post(URL.get("Platform.DIAGNOSIS_HOSPITAL_ITEM_QUERY"), "reportId="+reportId, dataType.json, function(data){
			if(!_.isEmpty(data.value)){
				var length = data.value.length;
				// 添加表头html
				addHistoryReportHeadHtml(reportId, length);
				// 遍历添加表体html
				$.each(data.value, function(index, item){
					addHistoryReportItemHtml(length, ++index, item);
				});
			} else{
				layer.alert("该检验报告没有检验项目！");
			}
		}, false, false);
	}
	
	// 判断是否有要展示的检验报告
	if($("#history-report-detail-div").children().length > 0){
		$("#history-report-detail-div").show();
		$("#history-line-div").show();
	} else{
		$("#history-report-detail-div").hide();
		$("#history-line-div").hide();
	}
}

/**
 * 添加检验报告详细内容表头
 * @param length
 * @returns
 */
function addHistoryReportHeadHtml(reportId, length){
	if(length > 0){
		var report = historyReportMap.get(reportId);
		var html1 = `
			<div class="panel-body" style="padding: 7px; border-top: 1px solid #ddd; background-color: #f5f5f5;">
				<label class="control-label">
					检验报告：${report.reportName} &nbsp;&nbsp;&nbsp;&nbsp;
					检验日期：${report.reportDate} &nbsp;&nbsp;&nbsp;&nbsp;
					接诊日期：${report.diagnosisDate} &nbsp;&nbsp;&nbsp;&nbsp;
					孕周：${report.gestationalWeeks}
				</label>
			</div>
		`;
		var html2 = `
			<div class="table-responsive">
				<table class="table table-bordered no-bottom table-padding-4">
					<thead>
						<tr class="active">
							<th class="text-center" style="width: 9%;">序号</th>
							<th class="text-center" style="width: 36%;">检验项目</th>
							<th class="text-center" style="width: 11%;">结果</th>
							<th class="text-center" style="width: 9%;">结论</th>
							<th class="text-center" style="width: 14%;">单位</th>
							<th class="text-center" style="width: 21%;">参考范围</th>
						</tr>
					</thead>
					<tbody id="history-report-detail-tbody"></tbody>
				</table>
			</div>
		`;
		var html3 = `
			<div class="table-responsive">
				<table class="table table-bordered no-bottom table-padding-4">
					<thead>
						<tr class="active">
							<th class="text-center" style="width: 9%;">序号</th>
							<th class="text-center" style="width: 36%;">检验项目</th>
							<th class="text-center" style="width: 11%;">结果</th>
							<th class="text-center" style="width: 9%;">结论</th>
							<th class="text-center" style="width: 14%;">单位</th>
							<th class="text-center" style="width: 21%;">参考范围</th>
						</tr>
					</thead>
					<tbody id="history-report-detail-tbody2"></tbody>
				</table>
			</div>
		`;
		if(length > 10){
			$("#history-report-detail-div").append(`
				<div id="${reportId}-history-report-item-table">
					${html1}
					<div class="row margin-zero">
						<div class="col-xs-6 padding-zero">
							${html2}
						</div>
						<div class="col-xs-6 padding-zero">
							${html3}
						</div>
					</div>
				</div>
			`);
		} else{
			$("#history-report-detail-div").append(`
				<div id="${reportId}-history-report-item-table">
					${html1}
					${html2}
				</div>
			`);
		}
	} else{
		layer.alert("该检验报告没有检验项目！");
	}
}

/**
 * 添加历史检验报告中的检验项目html
 * @param length
 * @param index
 * @param item
 * @returns
 */
function addHistoryReportItemHtml(length, index, item){
	var s = "";
	if("↓"==item.itemResult || "↑"==item.itemResult){
		s = 'style="color:red;"'
	}
	var html = `
		<tr ${s}>
			<td class="text-center">${index}</td>
			<td class="text-left">${item.itemName}</td>
			<td class="text-center">${item.itemValue || ""}</td>
			<td class="text-center">${item.itemResult || ""}</td>
			<td class="text-center">${item.itemUnit || ""}</td>
			<td class="text-center">${item.itemRefValue || ""}</td>
		</tr>
	`;
	if(length > 10 && index >= (length/2 + 1)){
		$("#"+item.reportId+"-history-report-item-table #history-report-detail-tbody2").append(html);
	} else{
		$("#"+item.reportId+"-history-report-item-table #history-report-detail-tbody").append(html);
	}
}

/**
 * 添加历史检验项目html
 * @param item
 * @returns
 */
function addHistoryItemHtml(index, item){
	$("#history-item-tbody-tr").remove();
	var s = "";
	if("↓"==item.itemResult || "↑"==item.itemResult){
		s = 'style="color:red;"'
	}
	var html = `
		<tr ${s}>
			<td class="text-center" style="width: 5%">${index}</td>
			<td class="text-left" style="width: 25%;">${item.itemName}</td>
			<td class="text-center" style="width: 10%;">${item.reportDate}</td>
			<td class="text-center" style="width: 10%;">${item.gestationalWeeks}</td>
			<td class="text-center" style="width: 10%;">${item.itemValue || ""}</td>
			<td class="text-center" style="width: 10%;">${item.itemResult || ""}</td>
			<td class="text-center" style="width: 10%;">${item.itemUnit || ""}</td>
			<td class="text-center" style="width: 20%;">${item.itemRefValue || ""}</td>
		</tr>
	`;
	$("#history-item-tbody").append(html);
}