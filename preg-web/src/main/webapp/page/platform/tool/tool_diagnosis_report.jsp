<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>查询检测报告--${custName}</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<style type="text/css">
.tooltip{
	min-width: 300px;
}
</style>
<script type="text/javascript">
//服用周期，打印报告选择项
var finalTakingCycleOptions = [];
var finalTakingCycle = [];
var elementAllList;

$().ready(function(){
	ajax.post(URL.get("Master.QUERY_NUTRIENT"), null, dataType.json, function(data){
		elementAllList = data.value;
	}, false, false);
	// 日期插件格式定义
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	common.initDate(null,null,null,"#examDate",nowDate);
	$("#examDate").datetimepicker("setEndDate",nowDate);
	$("#examDate").val("");
	$("#examDate").datetimepicker("update");
	queryReportList();
});

function queryReportList(){
	$("#reportQuery").ajaxPost(dataType.json,function(data){
		var reportHtml = "";
		$(data.value).each(function(index, report){
			if(report.examCategory=="B00004"){
				reportHtml += "<tr>"
					+"	<td class='text-center' width='50%'>"+report.examDate+"</td>"
					+"	<td class='text-center'><a style='cursor: pointer;' onclick='getExtenderHistoryReport(\""+report.examCode+"\",\""+report.examCategory+"\")'>"+report.inspectName+"</a></td>"
					+"</tr>";
			} else if(!common.isEmpty(report.inspectName) && !common.isEmpty(report.examPdf) && report.inspectCode != "A00001"){
				reportHtml += "<tr>"
					+"	<td class='text-center' width='50%'>"+report.examDate+"</td>"
					+"	<td class='text-center'><a style='cursor: pointer;' onclick='printPDF(\""+report.examPdf+"\",\""+report.examId+"\")'>"+report.inspectName+"</a></td>"
					+"</tr>";
			}
		});
		if(common.isEmpty(reportHtml)){
			reportHtml = "<tr><td colspan='10' class='text-center'><h4>没有找到记录！</h4></td></tr>";
		}
		$("#report_body").html(reportHtml);
	});
}

function getExtenderHistoryReport(inspectCode, inspectItem){
	var url = URL.get("Customer.PLAN_EXTENDER_ANALYSIS_HISTORY");
	var params = "inspectCode="+inspectCode+"&inspectItem="+inspectItem;
	finalTakingCycleOptions = [];
	finalTakingCycle = [];
	ajax.post(url, params, dataType.json, function(data){
		if(!_.isEmpty(data.value) && !_.isEmpty(data.value.inspectId) && !_.isEmpty(data.value.resultMap)){
			$("#inspectId").val(data.value.inspectId);
			var trHtml0 = "<tr class='active'><th style='min-width: 140px;' class='text-center'>孕期阶段</th>";
			var trHtml1 = "<tr><th class='text-center'>服用周期</th>";
			var trHtml2 = "<tr><th class='text-center'>营养制剂</th>";
			var bodyMap = new Map();
			var nameArray = [];
			var recordCode = [];
			$.each(data.value.resultMap, function(key, list){// 遍历 Map<String, List<PregPlanExtenderAssessPojo>>
				if(!_.isEmpty(list)){
					trHtml0 += "<th class='text-center' colspan='"+list.length+"'>"+key.substring(1, key.length)+"</th>";
					$.each(list, function(index, value){// 遍历 List<PregPlanExtenderAssessPojo>
						// 获取补充剂列表
						$.each(value.extenderList, function(j, extender){
							nameArray.push(extender.indexName+" "+extender.productName);
						});
						if(key == "0备孕期"){
							trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+value.takingCycle+" 月</th>";
							finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+value.takingCycle+" 月</option>");// 记录周期
						} else{
							trHtml1 += "<th style='min-width: 170px;' class='text-center'>"+value.takingCycle.substring(1, value.takingCycle.length)+" 周</th>";
							finalTakingCycleOptions.push("<option value='"+value.takingCycle+"'>"+value.takingCycle.substring(1, value.takingCycle.length)+" 周</option>");// 记录周期
						}
						finalTakingCycle.push(value.takingCycle);
						trHtml2 += "<td class='text-left'>"+nameArray.join("<br>")+"</td>";
						// 主体内容
						nameArray = [];
						$.each(elementAllList, function(c, element){
							var RNI = "——";
							var AI = "——";
							var UL = "——";
							var name = element.nutrientName+"（"+element.nutrientUnitName+"）";
							var detail = value.elementMap[element.nutrientId];
							var result = "";
							var tooltipId = "";
							var tooltipContent = "";
							if(!_.isEmpty(detail)){
								RNI = detail.elementRNI;
								AI = detail.elementAI;
								UL = detail.elementUL;
								diet = detail.dietElement || "——";
								extender = detail.extenderElement || "——";
								tooltipId = common.filterSpecialCharacters(element.nutrientId+value.takingCycle);
								if(!_.isEmpty(detail.detailStrList)){
									$.each(detail.detailStrList, function(d, str){
										tooltipContent += "<font color='red'>" + str + "</font><br>";
									});
								}
								result = "<div class='col-xs-5' style='padding-left: 4px;'>"+
										 "	<a id='tooltip_"+tooltipId+"'"+
										 "		data-html='true'"+
								         "	 	data-toggle='tooltip'"+
								         "	 	data-title=\""+tooltipContent+"<hr>"+UL+"\""+
								         "	 	onmouseover='common.showToolTipContent(\"tooltip_"+tooltipId+"\")'>"+RNI+"</a>"+
								         "</div>"+
								         "<div class='col-xs-7 no-padding'>" + 
								         	AI.replace("↓", "<font color='blue'>↓</font>").replace("↑", "<font color='red'>↑</font>")+
								         "</div>";
								recordCode.push(element.nutrientId);
							}
							if(bodyMap.has(element.nutrientId)){
								bodyMap.set(element.nutrientId, bodyMap.get(element.nutrientId) + "<td class='text-left'>"+result+"</td>");
							} else{
								bodyMap.set(element.nutrientId, "<tr id='temp_"+element.nutrientId+"'><td class='text-left'>"+name+"</td><td class='text-left'>"+result+"</td>");
							}
						});
					});
				}
			});
			trHtml0 += "</tr>";
			trHtml1 += "</tr>";
			trHtml2 += "</tr>";
			var bodyHtml = "";
			bodyMap.forEach(function(value, key){
				bodyHtml += bodyMap.get(key) + "</tr>";
			});
			$("#elementTable").html("<thead>" + trHtml0 + trHtml1 + "</thead>" + trHtml2 + bodyHtml);
			// 删除无用行
			$.each(elementAllList, function(c, element){
				if($.inArray(element.nutrientId, recordCode) == -1){
					$("#temp_"+element.nutrientId).remove();
				}
			});
		}
		
		// 设置打印报告周期选项
		$("#pdf_taking_cyle").html("");
		if(!_.isEmpty(finalTakingCycleOptions)){
			$.each(finalTakingCycleOptions, function(index, cycle){
				$("#pdf_taking_cyle").append(cycle);
			});
			$("#pdf_taking_cyle").multiselect("destroy").multiselect({  
                // 自定义参数，按自己需求定义  
				nonSelectedText : "==请选择服用周期==",   
                maxHeight : 350,   
                includeSelectAllOption : false,   
                numberDisplayed : false,
                nSelectedText: "个周期已选择",
                allSelectedText: "已全部选中",
                selectAllText: "全部选中",
                dropUp: true,
                buttonWidth: "100%",
                buttonContainer: "<div class='multiselect-wrapper' />",
                onChange: function(option, checked, select) {
                	$("#selectAllButton").attr("checked", false);
                }
            });
		}
		
		// 展示结果
		$("#assessModal").modal("show");
		
	}, false, false);
}

/**
 * 选中全部，清空服用周期下拉列表
 */
function selectAll(){
	$("#pdf_taking_cyle").multiselect("deselectAll", false);
	$("#pdf_taking_cyle").multiselect("updateButtonText");
}

/**
 * 打印PDF报告
 */
function getAssessExtenderPdf(){
	var finalCycle = null;
	if($("#selectAllButton").attr("checked")){
		$("#takingCycleList").val(finalTakingCycle);
		finalCycle = finalTakingCycle;
	} else{
		$("#takingCycleList").val($("#pdf_taking_cyle").val());
		finalCycle = $("#pdf_taking_cyle").val();
	}
	if(_.isEmpty(finalCycle)){
		layer.alert("请先选择服用周期");
		return false;
	}
	
	var url = URL.get("Customer.PLAN_EXTENDER_ASSESS_REPORT");
	var params = "id=" + $("#inspectId").val() + "&takingCycleList="+finalCycle.join(",");
	ajax.post(url, params, dataType.json, function(pdfData){
    	common.openWindow(PublicConstant.basePath +"/"+ pdfData.value);
	});
}

function printPDF(path, examId){
	if(common.isEmpty(path)){
		layer.msg("该PDF文件不存在！");
	}else{
		var url = URL.get("Platform.CHECK_FILE_EXIST");
		var params = "path="+path;
		ajax.post(url, params, dataType.json, function(data){
			if(!data.value){
				ajax.post(URL.get("Platform.REGENERATE_REPORT"), "examId="+examId, dataType.json, null, false, false);
			}
			var sFeatures = "toolbar=0,location=0,directories=0,status=1,menubar=0,scrollbars=0,resizable=0 channelmode"; 
			common.openWindow("${common.basepath}/"+path, sFeatures);
		});
	}
}
</script>
<body>
<input type="hidden" id="inspectId">
<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-heading">
					<h4 class="timeline-title">营养评价项目PDF报告</h4>
				</div>
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">营养评价项目报告列表</div>
						<div class="panel-body form-inline">
							<form id="reportQuery" action="${common.basepath}/${applicationScope.URL.Platform.QUERY_REPORT_LIST}" method="post" class="form-horizontal">
								<input type="hidden" name="custId" value="${custId }" />
									<div class="input-group">
										<input id="examDate" name="examDate" type="text" class="form-control form_date" placeholder="请选择报告日期" readonly /> <span class="input-group-btn">
											<button class="btn btn-default" type="button" onclick="common.dateShow('examDate')">
												<i class="fa fa-calendar fa-fw"></i>选择
											</button>
										</span>
									</div>
									<select id="examCategory" name="examCategory" class="form-control">
										<option value="">==请选择检测项目==</option>
										<option value="B00002">孕期膳食及生活方式评估</option>
										<option value="B00003">孕期人体成分分析</option>
										<option value="B00004">营养素安全剂量评估</option>
										<option value="B00005">孕期24小时膳食回顾</option>
										<option value="B00006">孕期快速营养调查</option>
										<option value="B00007">孕期生活方式调查</option>
										<option value="B00008">孕期膳食频率调查</option>
									</select>
									<button type="button" onclick="queryReportList();" class="btn btn-default">
										<i class="fa fa-search fa-fw"></i>查询
									</button>
							</form>
						</div>
						<div class="table-responsive">
							<table class="table table-bordered table-hover">
								<thead>
									<tr class="active">
										<th class="text-center">检测日期</th>
										<th class="text-center">报告名称</th>
									</tr>
								</thead>
								<tbody id="report_body">
									<tr>
										<td colspan="10" class="text-center"><h4>没有找到记录！</h4></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div style="color: red;">注：没有PDF报告或未生成PDF报告的检测项目，不会在此显示</div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>

<div id="assessModal" class="modal fade bs-example-modal form-horizontal">
	<div class="modal-dialog" style="width: 1200px;">
		<div class="modal-content">
			<div class="container-fluid">
				<div class="row">
					<ul id="timeline">
						<li class="timeline-inverted">
							<div class="timeline-panel" id="timeline-panel">
								<div class="timeline-heading">
								</div>
								<div class="timeline-body form-horizontal">
									<div class="panel panel-lightblue no-bottom">
										<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 剂量评估结果<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
										<div class="table-responsive" style="max-height: 750px;">
											<table class="table table-bordered table-hover table-padding-2" id="elementTable">
											</table>
										</div>
									</div>
									<div class="panel-body no-padding">
										<div class="col-xs-2 col-xs-offset-8 no-padding"><select class="form-control" id="pdf_taking_cyle" multiple="multiple"></select></div>
										<div class="col-xs-1"><label class="control-label radio-inline"><input type="radio" id="selectAllButton" checked onclick="selectAll();"> 全部</label></div>
										<div class="col-xs-1 no-padding"><button id="assessExtenderPdf" onclick="getAssessExtenderPdf();" type="button" name="extenderAssessButton" class="btn btn-primary btn-block">打印报告</button></div>
									</div>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>