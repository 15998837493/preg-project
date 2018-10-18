//-----------------------------------------【工作量统计--统计汇总】----------------------------------
//datatable参数
var accountTableOption={
    id: "accountListTable",
    form: "workQueryForm",
    columns: [
        {"data":"month","sClass":"text-center"},
        {"data":"diagnosisNum","sClass":"text-center"},
        {"data":"diagnosisRen","sClass":"text-center"},
        {"data":"diagnosisFirst","sClass":"text-center"},
        {"data":"subsequentRate","sClass":"text-center"},
        {"data":"pregStagePre","sClass":"text-center"},
        {"data":"pregStagePreRate","sClass":"text-center"},
        {"data":"pregStageMid","sClass":"text-center"},
        {"data":"pregStageMidRate","sClass":"text-center"},
        {"data":"pregStageLate","sClass":"text-center"},
        {"data":"pregStageLateRate","sClass":"text-center"}
    ],
    isPage: false,
    async: false
};
//-----------------------------------------【工作量统计--统计汇总】----------------------------------

//-----------------------------------------【工作量统计--诊断频次】----------------------------------
// 诊断MAP
var diseaseFrequencyMap = new Map();

/**
 * 工作量统计--诊断频次，导出Excel数据
 * @returns
 */
function getDiseaseFrequencyExcel(){
	var diseaseFrequencyExcel = {};
	diseaseFrequencyExcel.title = "诊断频次";
	diseaseFrequencyExcel.dataList = [];
	var names = [];
	var values = [];
	diseaseFrequencyMap.forEach(function(frequency, diseaseId){
		names.push(frequency.diseaseName);
		values.push(frequency.value);
	});
	diseaseFrequencyExcel.dataList.push(names.join("###"));
	diseaseFrequencyExcel.dataList.push(values.join("###"));
	return diseaseFrequencyExcel;
}

/**
 * 添加统计--诊断频次
 * @param data
 * @returns
 */
function addTdHtml(data){
	if(diseaseFrequencyMap.has(data.val.diseaseId)){
		layer.alert("该诊断已添加！");
		return false;
	}
	var frequency = {diseaseName:data.name, value:null};
	
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var diagnosisUserList = $("#userSelect").val();
	
	if(_.isEmpty(startDate) || _.isEmpty(endDate)){
		layer.alert("请先完善开始时间和结束时间！");
		return false;
	}
	if(_.isEmpty(diagnosisUserList)){
		layer.alert("请先选择医师！");
		return false;
	}
	var params = "diseaseId="+data.val.diseaseId+"&startDate="+startDate+"&endDate="+endDate+"&diagnosisUserList="+diagnosisUserList;
	ajax.post(URL.get("WorkAccount.WORK_ACCOUNT_DISEASE_FREQUENCY"), params, dataType.json, function(result){
		frequency.value = result.value;
	}, false, false);
	
	var $table = $("#diseaseFrequencyTable");
	var $head = $table.children(":first").children(":first");
	var $body = $table.children(":last").children(":last");
	var thLenght = $head.children().length;
	var tdLength = $body.children().length;
	if(thLenght == 2 && tdLength == 1){
		$body.html(createTdHtml(data.val.diseaseId, frequency.diseaseName, frequency.value));
	} else if(thLenght < 8){
		$head.append("<th class='text-center'>诊断名称</th><th class='text-center'>诊断频次（次/人）</th>");
		$body.append(createTdHtml(data.val.diseaseId, frequency.diseaseName, frequency.value));
		$table.attr("style", "width:"+25*(thLenght/2+1)+"%");
	} else if(thLenght == 8 && tdLength < 8){
		$body.append(createTdHtml(data.val.diseaseId, frequency.diseaseName, frequency.value));
	} else if(thLenght == 8 && tdLength == 8){
		$table.children(":last").append("<tr>"+createTdHtml(data.val.diseaseId, frequency.diseaseName, frequency.value)+"</tr>");
	}
	diseaseFrequencyMap.set(data.val.diseaseId, frequency);
}

/**
 * 删除统计--诊断频次
 * @param diseaseId
 * @returns
 */
function removeTdHtml(diseaseId){
	diseaseFrequencyMap.delete(diseaseId);
	frequencyDiseaseMap.delete(diseaseId);
	var $table = $("#diseaseFrequencyTable").html(
			"<thead>" + 
			"	<tr class='active'>" +
			"		<th class='text-center'>诊断名称</th>" +
			"		<th class='text-center'>诊断频次（次/人）</th>" +
			"	</tr>" + 
			"</thead>" + 
			"<tbody>" +
			"	<tr><td class='text-center' colspan='100' style='border-bottom: 1px solid #ddd;'><h4>没有数据！</h4></td></tr>" +
			"</tbody>").attr("style", "border: 1px solid #ddd; width: 25%;");
	var $head = $table.children(":first").children(":first");
	var $body = $table.children(":last").children(":last");
	var thLenght = 2;
	var tdLength = 0;
	diseaseFrequencyMap.forEach(function(frequency, diseaseId){
		if(thLenght == 2 && tdLength == 0){
			$body.html(createTdHtml(diseaseId, frequency.diseaseName, frequency.value));
			tdLength += 2;
		} else if(thLenght < 8){
			$head.append("<th class='text-center'>诊断名称</th><th class='text-center'>诊断频次（次/人）</th>");
			$body.append(createTdHtml(diseaseId, frequency.diseaseName, frequency.value));
			$table.attr("style", "width:"+25*(thLenght/2+1)+"%");
			thLenght += 2;
			tdLength += 2;
		} else if(thLenght == 8 && tdLength < 8){
			$body.append(createTdHtml(diseaseId, frequency.diseaseName, frequency.value));
			tdLength += 2;
		} else if(thLenght == 8 && tdLength == 8){
			$table.children(":last").append("<tr>"+createTdHtml(diseaseId, frequency.diseaseName, frequency.value)+"</tr>");
			$body = $table.children(":last").children(":last");
			tdLength = 2;
		}
	});
}

var frequencyDiseaseMap = new Map();

/**
 * 生成统计--诊断频次html
 * @param diseaseId
 * @param diseaseName
 * @param value
 * @returns
 */
function createTdHtml(diseaseId, diseaseName, value) {
	frequencyDiseaseMap.set(diseaseId, diseaseName);
	var html = "<td style='border-bottom: 1px solid #ddd;'>" + diseaseName
			 + "	<a onclick='removeTdHtml(\"" + diseaseId + "\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>" 
			 + "</td>"
			 + "<td class='text-center' style='border: 1px solid #ddd;'>"+value+"</td>";
	return html;
}

/**
 * 时间段改变
 * @returns
 */
function changeFrequencyDate(){
	$("#diseaseFrequencyTable").html(
			"<thead>" + 
			"	<tr class='active'>" +
			"		<th class='text-center'>诊断名称</th>" +
			"		<th class='text-center'>诊断频次（次/人）</th>" +
			"	</tr>" + 
			"</thead>" + 
			"<tbody>" +
			"	<tr><td class='text-center' colspan='100' style='border-bottom: 1px solid #ddd;'><h4>没有数据！</h4></td></tr>" +
			"</tbody>").attr("style", "border: 1px solid #ddd; width: 25%;");
	if(frequencyDiseaseMap.size > 0){
		diseaseFrequencyMap.clear();
		frequencyDiseaseMap.forEach(function(value, key){
			var obj = {val:{},name:value};
			obj.val.diseaseId = key;
			addTdHtml(obj);
		});
	}
}

//-----------------------------------------【工作量统计--诊断频次】----------------------------------

//-----------------------------------------【工作量统计--统计列表】----------------------------------
/** 统计列表datatable*/
var statisticTableOption={
    id: "statisticDataTable",
    form: "statisticDataForm",
    columns: [
        {"data":"diagnosisDate","sClass":"text-center"},
        {"data":"diagnosisCustSikeId","sClass":"text-center"},
        {"data":"diagnosisCustName","sClass":"text-center"},
        {"data":"diagnosisGestationalWeeks","sClass":"text-center"},
        {"data":"diagnosisCustHeight","sClass":"text-center"},
        {"data":null,"sClass":"text-center",
        	"render": function(data, type, row, meta) {
        		if(data.weight == null){
        			return "——————";
        		}else{
        			return data.weight;
        		}
			}
        },
        {"data":"diagnosisCustWeight","sClass":"text-center"},
        {"data":null,"sClass":"text-left content-ellipsis",
        	"render" : function(data, type, row, meta) {
        		var names=data.names;
	            if (!common.isEmpty(names)) {
	            	return  "<a id='tooltip_"+data.diagnosisId+"_"+meta.row+"'" + 
						 	"   data-toggle='tooltip'" + 
					    	"	data-title='" + names + "'" + 
					    	"   onmouseover='common.showToolTipContent(\"tooltip_" + data.diagnosisId + "_"+meta.row+"\");'>" + names + 
					    	"</a>";
	            }else{
	                return "";
		        }
			}
        },
        {"data":"diagnosisUserName","sClass":"text-center"},
        {"data":null,"sClass":"text-center",
			"render": function(data, type, row, meta) {
				return "<a onclick='getDiagnosisHistory(\""+data.diagnosisId+"\")'>查看</a>";
			}
        },
    ],
	async: false,// 是否同步
	loading: true,// 是否启用遮罩层
};
/**
 * 查看诊断分析
 * @param diagnosisId
 */
function getDiagnosisHistory(diagnosisId){
//	common.openWindow(URL.get("Platform.DIAGNOSIS_SUMMARY_PAGE") + "?diagnosisId=" + diagnosisId);
	common.openWindow(PublicConstant.basePath + "/page/platform/tool/tool_disgnosis_summary_window.jsp?diagnosisId=" + diagnosisId);
}
//-----------------------------------------【工作量统计--统计列表】----------------------------------

//-----------------------------------------【工作量对比--孕期营养门诊门诊量统计】----------------------------------
/**
 * 初始化孕期门诊门诊量统计
 */
function initNutritionOutpatient(){
	// 为日期插件初始化并限制
	accountDateValid("tab1StartDate", "tab1EndDate");
	
	//tab1生成图表按钮
	$("#tab1CreatButton").on("click", function(){
        graphMap.set("tab1","bar");
		if(common.isEmpty($("#tab1StartDate").val()) || common.isEmpty($("#tab1EndDate").val()) || common.isEmpty($("#tab1UserSelect").val())){
			layer.alert("时间和医师不允许为空");
			return;
		}
		$("#tab1SelectDiagnosisUser").val($("#tab1UserSelect").val().join(","));
		//将影藏的区域显示
		$("#tab1Echart").css("display", "block");
		
		//图表变量
		$("#tab1Table").empty();
		$("#tab1Table").append(
				"<tr class='active'><td class='text-center'>分类</th></tr>"+
				"<tr><td class='text-center'>初诊人数</td></tr>"+
				"<tr><td class='text-center'>总就诊人次</td></tr>"
		);
		// 发送请求
		var rowLength = 0;
		$("#tab1QueryForm").ajaxPost(dataType.json, function(data){
			var result = data.value;
			rowLength = result.xData.length;
			for ( var i = 0; i < result.xData.length; i++) {
				$("#tab1Table tr").eq(0).append("<td class='text-center'>" + result.xData[i] + "</th>");
				$("#tab1Table tr").eq(1).append("<td class='text-center'>" + result.echartsData[0].data[i] + "</td>");
				$("#tab1Table tr").eq(2).append("<td class='text-center'>" + result.echartsData[1].data[i] + "</td>");
			}
			valueMap.set("tab1",result);
			showTab1Echarts(result);
		}, true, false);
		var tab1Data = {
				"sheetNum": 0,
				"sheetName": "工作表1",
				"dataRows":2,
				"dataCols":0,
				"title":"初诊人数 VS 总就诊人次",
				"rowList":[],
				"chartType":"colnum"
		};
		$("#tab1Table tr").each(function(index, value){
			var newList = [];
			$(value).find("td").each(function(i, v){
				newList.push($(v).text());
			});
			tab1Data.rowList.push(newList);
		})
		tab1Data.dataCols = rowLength;
		compareJson["tab1"] = tab1Data;
		// 日期选择器联动
		var sDate = $("#tab1StartDate").val();// 工作量统计-开始日期
		var eDate = $("#tab1EndDate").val();// 工作量统计-结束日期
		if(common.isEmpty($("#outpatientStartDate").val()) && common.isEmpty($("#outpatientEndDate").val())) {// 不同门诊单元门诊量统计
			$("#outpatientEndDate").attr("disabled",false);
			$("#outpatientStartDate").val(sDate);
			$("#outpatientEndDate").val(eDate);
		}
		if(common.isEmpty($("#pregStartDate").val()) && common.isEmpty($("#pregEndDate").val())) {// 初诊患者孕期分布
			$("#pregEndDate").attr("disabled",false);
			$("#pregStartDate").val(sDate);
			$("#pregEndDate").val(eDate);
		}
		if(common.isEmpty($("#tab7 input[name='startDate']").val()) && common.isEmpty($("#tab7 input[name='endDate']").val())) {// 诊断频次
			$("#tab7 input[name='endDate']").attr("disabled",false);
			$("#tab7 input[name='startDate']").val(sDate);
			$("#tab7 input[name='endDate']").val(eDate);
		}
	});
}
//-----------------------------------------【工作量对比--孕期营养门诊门诊量统计】----------------------------------

//-----------------------------------------【工作量对比--不同出诊单元门诊量统计】----------------------------------
/**
 * 初始化门诊量统计
 */
function initOutpatienData(){
	// 初始化时间插件
	initTime("outpatientStartDate","outpatientEndDate",null,null);// 就诊人数对比
	// 校验时间选择是否合理
	accountDateValid("outpatientStartDate", "outpatientEndDate");
	$("#tab2 button[name='creativeEchartBut']").click(function(){
		if(validTime("outpatientStartDate","outpatientEndDate",null,null,"outpatientUserSelect")){
			graphMap.set("tab2","bar");
			initOutpatienEcharts();
			// 日期选择器联动
			var sDate = $("#outpatientStartDate").val();// 工作量统计-开始日期
			var eDate = $("#outpatientEndDate").val();// 工作量统计-结束日期
			if(common.isEmpty($("#pregStartDate").val()) && common.isEmpty($("#pregEndDate").val())) {// 初诊患者孕期分布
				$("#pregEndDate").attr("disabled",false);
				$("#pregStartDate").val(sDate);
				$("#pregEndDate").val(eDate);
			}
			if(common.isEmpty($("#tab7 input[name='startDate']").val()) && common.isEmpty($("#tab7 input[name='endDate']").val())) {// 诊断频次
				$("#tab7 input[name='endDate']").attr("disabled",false);
				$("#tab7 input[name='startDate']").val(sDate);
				$("#tab7 input[name='endDate']").val(eDate);
			}
		}
	});
}

/**
 * 生成图表以及列表
 * @returns
 */
function initOutpatienEcharts(){
	$("#tab2EchartLeft").css("display", "block");
	var url = URL.get("WorkAccount.OUTPATIENT_DATA_QUERY");
	var params = "diagnosisUserList=" + $("#outpatientUserSelect").val()+ "&startDate=" + $("#outpatientStartDate").val() + "&endDate=" + $("#outpatientEndDate").val() ;
	ajax.post(url, params, dataType.json, function(data){
		if(!common.isEmpty(data.value.legend.data)){
			valueMap.set("tab2",data.value);
			showDiagnosisCountEchartsTab2Left(data.value);
			showDiagnosisCountEchartsTab2Right(data.value);
			creativeTableTab2(data.value);
		}else{
			$("#tab2EchartLeft").css("display", "none");
		}
	});
}

/**
 * 生成门诊量统计表格
 * @param echartsValue
 * @returns
 */
function creativeTableTab2(echartsValue){
	var tab2_1 = {
			"sheetNum": 0,
			"sheetName": "工作表1",
			"dataRows":echartsValue.echartsData.length,
			"dataCols":echartsValue.echartsData[0].data.length,
			"title":"不同医师门诊量",
			"rowList":[]
	};
	
	//准备数据
	var xDatas=echartsValue.xData;
	var echartsDatas=echartsValue.echartsData;
	//添加表头
	var head="<th class='active text-center'>姓名</th>";
	var arrayHead=[];//导出excal对象填充数据，rowList数据模板
	arrayHead.push("姓名");
	$(xDatas).each(function(index,xData){
		head=head+"<th class='active text-center'>"+xData+"</th>";
		arrayHead.push(xData);
	});
	head=head+"<th class='active text-center'>总计</th>";
	$("#tab2Table thead").find("tr").empty();
	$("#tab2Table thead").find("tr").append(head);
	
	//导出excal对象填充数据
	arrayHead.push("总计");
	tab2_1.rowList.push(arrayHead);
	
	//添加表格内容
	var body="";
	$("#tab2Table tbody").empty();
	$(echartsDatas).each(function(index,echartsData){
		body=body+"<tr><td class='text-center'>"+formatName(echartsData.name);
		var datas=echartsData.data;
		var sum=0;
		var arrayBody=[];//导出excal对象填充数据，rowList数据模板
		arrayBody.push(formatName(echartsData.name));
		$(datas).each(function(index,data){
			sum=sum+data;
			body=body+"<td class='text-center'>"+data+"</td>";
			//导出excal对象填充数据
			arrayBody.push(data);
		});
		arrayBody.push(sum);
		body=body+"<td class='text-center'>"+sum+"</td>";
		
		tab2_1.rowList.push(arrayBody);//导出excal对象填充数据
	});
	body=body+"</td></tr>";
	$("#tab2Table tbody").append(body);
	compareJson["tab2_1"] = tab2_1;
	
	//饼形图数据
	var tab2_2 = {
			"sheetNum": 0,
			"sheetName": "工作表1",
			"dataRows":1,
			"dataCols":tab2_1["rowList"].length - 1,
			"title":"患者分布",
			"chartType":"pie",
			"rowList":[[],[]]
	};
	$.each(tab2_1["rowList"], function(index, value){
		tab2_2["rowList"][0].push(value[0]);
		tab2_2["rowList"][1].push(value[value.length - 1]);
	});
	compareJson["tab2_2"] = tab2_2;
}
//-----------------------------------------【工作量对比--不同出诊单元门诊量统计】----------------------------------

//-----------------------------------------【工作量对比--初诊患者孕期分布】----------------------------------
function pregDistribution(){
	// 初始化时间插件
	initTime("pregStartDate","pregEndDate",null,null);// 就诊人数对比
	// 校验时间选择是否合理
	accountDateValid("pregStartDate", "pregEndDate");
	$("#tab3 button[name='creativeEchartBut']").click(function(){
		if(validTime("pregStartDate","pregEndDate",null,null,"pregUserSelect")){
			initPregEcharts();
			// 日期选择器联动
			var sDate = $("#pregStartDate").val();// 工作量统计-开始日期
			var eDate = $("#pregEndDate").val();// 工作量统计-结束日期
			if(common.isEmpty($("#tab7 input[name='startDate']").val()) && common.isEmpty($("#tab7 input[name='endDate']").val())) {// 诊断频次
				$("#tab7 input[name='endDate']").attr("disabled",false);
				$("#tab7 input[name='startDate']").val(sDate);
				$("#tab7 input[name='endDate']").val(eDate);
			}
		}
	});
}

/**
 * 生成图表以及列表
 * @returns
 */
function initPregEcharts(){
	var url = URL.get("WorkAccount.DISTRIBUTION_DATA_QUERY");
	var params = "diagnosisUserList=" + $("#pregUserSelect").val()+ "&startDate=" + $("#pregStartDate").val() + "&endDate=" + $("#pregEndDate").val() ;
	ajax.post(url, params, dataType.json, function(result){
		if(result.data.length > 0){
			//生成图表
			valueMap.set("tab3",result.data);
			creativeEcharts(result.data);
			//生成表格
			creativeTable(result.data);
		}else{
			//$("#tab3Title").css("display", "none");
			$("#tab3Table tbody").empty();
			$("#tab3Table thead").find("tr").empty();
			$("#tab3Table thead").find("tr").append("<td colspan='100' class='text-center'><h4>没有数据！</h4></td>");
			$("#tab3EchartLeft").css("display", "none");
		};
	});
}

/**
 * 生成图表
 * @param array
 * @returns
 */
function creativeEcharts(array){
	$("#tab3EchartLeft").css("display", "block");
	//$("#tab3Title").css("display", "block");
	$("#tab3EchartLeft").empty();
	$("#tab3EchartLeft").append("<div id='echartTabSum' class='col-xs-4 padding-zero' style='height:300px;'></div>");
	
	$("#tab3EchartLeft").height((array.length/3)*$("#echartTabSum").height());
	
	for(var i=0;i<array.length;i++){
		if(i<array.length-1){
			var echartId="echartTab"+i;
			$("#tab3EchartLeft").append("<div id='"+echartId+"' class='col-xs-4 padding-zero' style='height:300px;'></div>");
			showDiagnosisCountEchartsTab3(array[i],echartId,i);
		}else{
			showDiagnosisCountEchartsTab3(array[i],"echartTabSum",i);
		};
	};
	
}

/**
 * 生成表格
 * @param array
 * @returns
 */
function creativeTable(array){
	//清空tab3之前的数据
	for(var key in compareJson){
		var keys = key.split("_");
		if(keys[0] == "tab3"){
			delete compareJson[key];
		};
	}
	$("#tab3Table thead").find("tr").empty();
	var tab3tr="<th class='active text-center'>姓名</th>"
			+"<th class='active text-center'>孕早期</th>"
			+"<th class='active text-center'>孕中期</th>"
			+"<th class='active text-center'>孕晚期</th>";
	$("#tab3Table thead").find("tr").append(tab3tr);
	
	var body="";
	$("#tab3Table tbody").empty();
	// 把总计的条目放在前面
	createCompareJsonTab3(array.length - 1, array);
	// 添加其余条目
	for(var i=0;i<array.length;i++){
		var template="<tr>"
			+"<td class='text-center'>"+array[i].title.text+"</td>"
		    +"<td class='text-center'>"+array[i].pieData[0].value+"</td>"
		    +"<td class='text-center'>"+array[i].pieData[1].value+"</td>"
		    +"<td class='text-center'>"+array[i].pieData[2].value+"</td>"
		    +"</tr>";
		body=body+template;
		if(i < array.length - 1){
			createCompareJsonTab3(i, array);
		}
	};
	$("#tab3Table tbody").append(body);
}

/**
 * 向compareJson中添加数据
 * @param num
 * @param array
 * @returns
 */
function createCompareJsonTab3(num,array){
	var tab3= {
			"sheetNum": 0,
			"sheetName": "工作表1",
			"dataRows":array[0].pieData.length+1,//总记录数为检索出的总记录数 + 一条总计
			"dataCols":3,
			"title":"初诊患者孕期分布",
			"chartType":"pie",
			"rowList":[]
	};
	//导出excal填充数据--表头
	var arrayHead=[];
	arrayHead.push("姓名");
	arrayHead.push("孕早期");
	arrayHead.push("孕中期");
	arrayHead.push("孕晚期");
	tab3.rowList.push(arrayHead);
	//导出excal填充数据--表身
	var arrayBody=[];
	arrayBody.push(array[num].title.text);
	arrayBody.push(array[num].pieData[0].value);
	arrayBody.push(array[num].pieData[1].value);
	arrayBody.push(array[num].pieData[2].value);
	tab3.rowList.push(arrayBody);
	//导出excal填充数据
	compareJson["tab3_"+num] = tab3;
}
//-----------------------------------------【工作量对比--初诊患者孕期分布】----------------------------------

//-----------------------------------------【工作量对比--就诊人数对比】 【工作量对比--初诊人数对比】----------------------------------
/**
 * 格式化日期 2018-02-07  ->  20180207
 */
function formatTime(time) {
    return parseInt(time.replace(/-/g, ""));
}

/**
 * 校验时间和医师不能为空
 */
function validTime(id, id2, id3, id4, id5) {
    if (!common.isEmpty(id3) || !common.isEmpty(id4)) {
        if (common.isEmpty($("#" + id).val()) || common.isEmpty($("#" + id2).val()) || common.isEmpty($("#" + id3).val()) || common.isEmpty($("#" + id4).val()) || common.isEmpty($("#" + id5).val())) {
            layer.alert("时间和医师不允许为空");
            return false;
        }
    } else {
        if (common.isEmpty($("#" + id).val()) || common.isEmpty($("#" + id2).val()) || common.isEmpty($("#" + id5).val())) {
            layer.alert("时间和医师不允许为空");
            return false;
        }
    }

    return true;
}

/**
 * 拼日期
 */
function formatDate(id,id2) {
	return $("#" + id).val().replace(/-/g, "/")+"~"+$("#" + id2).val().replace(/-/g, "/");
}

/**
 * 格式化姓名
 */
function formatName(p) {
	if(p.indexOf(",") > -1) {
		return p.substring(0,p.indexOf(","));
	}else {
		return p;
	}
}

/**
 * 初始化表格
 */
function initTable(data, tableId, id, id2, id3, id4) {
    var html = "";
    var rate = "";
    var scope1 = formatDate(id,id2);
    var scope2 = formatDate(id3,id4);
    if("rateTable" == tableId) {
    	rate = "%";
    }
    // 移除‘没有数据’提示
    $("#" + tableId).children().remove();
    html += "<tr class='active'><th class='text-center'>姓名</th><th class='text-center'>"+scope1+"</th><th class='text-center'>"+scope2+"</th></tr>";
    for (var x = 0; x < data.length; x++) {
        html += "<tr>" + 
        		"	<td class='text-center'>" + formatName(data[x].name) + "</td>" + 
        		"	<td class='text-center'>" + data[x].data[0] + rate + "</td>" + 
        		"	<td class='text-center'>" + data[x].data[1] + rate + "</td>" +
        		"</tr>";
    }
    $("#" + tableId).append(html);
}

/**
 * echarts初始化数据准备
 */
function initDiagnosisCountEcharts(firstDate1, firstDate2, lastDate1, lastDate2, userId, type) {
	var userNames = [];// 全部的医师姓名
	var allUsersId = [];// 全部的医师Id
	var chooseUserNames = [];// 选中的医师姓名
	$.each(userList, function(index, user){
		allUsersId.push(user.userId);
		userNames.push(user.userName);
	});
    if (type == 1 || type == 2) { // 就诊/初诊
        var url = URL.get("WorkAccount.DIAGNOSIS_ACCOUNT_QUERY_ECHART");
        var param_type = "";
        if (type == 2) {// 初诊
            param_type = "&type=firstDiagnosis";
        	var chooseUserIds = $("#first_diagnosis_userId").val().split(",");// 选中的医师Id
        	for(var x=0;x<chooseUserIds.length;x++) {
        		chooseUserNames.push(userMap.get(chooseUserIds[x]));
        	}
        }else if(type == 1) {// 就诊
        	var chooseUserIds = $("#userId").val().split(",");// 选中的医师Id
        	for(var x=0;x<chooseUserIds.length;x++) {
        		chooseUserNames.push(userMap.get(chooseUserIds[x]));
        	}
        }
        var param = "diagnosisUser=" + userId + "&startDate=" + firstDate1 + "&endDate=" + firstDate2 + "&startDate2=" + lastDate1 + "&endDate2=" + lastDate2 +"&diagnosisUserNameList=" + userNames + "&diagnosisUserList=" + allUsersId + "&chooseDiagnosisUserName=" + chooseUserNames + param_type;
        ajax.post(url, param, dataType.json,
        function(data) {
            var value = data.value;
            if (type == 1) { // 就诊人数对比
            	valueMap.set("tab4",value);
                showDiagnosisCountEcharts(value); // echart
                initTable(value.echartsData, "diagnosisCountTable", "firstScope1", "firstScope2", "lastScope1", "lastScope2"); // 表格
        		var tab4Data = {
        				"sheetNum": 0,
        				"sheetName": "工作表1",
        				"dataRows":1,
        				"dataCols":2,
        				"title":"",
        				"rowList":[]
        		};
                tab4Data.title = "就诊人数对比";
                tab4Data.dataRows = value.echartsData.length;
                var rowList = [];
                var rowListFirst = [];
                rowListFirst.push("姓名");
                rowListFirst.push(formatDate("firstScope1","firstScope2"));
                rowListFirst.push(formatDate("lastScope1","lastScope2"));
                rowList.push(rowListFirst);
                for(var x=0;x<value.echartsData.length;x++) {
                	var row = [];
                	row.push(formatName(value.echartsData[x].name));
                	row.push(value.echartsData[x].data[0]);
                	row.push(value.echartsData[x].data[1]);
                	rowList.push(row);
                }
                tab4Data.rowList = rowList;
        		compareJson["tab4"] = tab4Data;
            } else if (type == 2) { // 初诊人数对比
            	valueMap.set("tab5",value);
                showFirstDiagnosisCountEcharts(value); // echart
                initTable(value.echartsData, "firstDiagnosisCountTable", "firstDiagnosisScope1", "firstDiagnosisScope2", "firstDiagnosislastScope1", "firstDiagnosislastScope2"); // 表格
        		var tab5Data = {
        				"sheetNum": 0,
        				"sheetName": "工作表1",
        				"dataRows":1,
        				"dataCols":2,
        				"title":"",
        				"rowList":[]
        		};
        		tab5Data.title = "初诊人数对比";
        		tab5Data.dataRows = value.echartsData.length;
                var rowList = [];
                var rowListFirst = [];
                rowListFirst.push("姓名");
                rowListFirst.push(formatDate("firstDiagnosisScope1","firstDiagnosisScope2"));
                rowListFirst.push(formatDate("firstDiagnosislastScope1","firstDiagnosislastScope2"));
                rowList.push(rowListFirst);
                for(var x=0;x<value.echartsData.length;x++) {
                	var row = [];
                	row.push(formatName(value.echartsData[x].name));
                	row.push(value.echartsData[x].data[0]);
                	row.push(value.echartsData[x].data[1]);
                	rowList.push(row);
                }
                tab5Data.rowList = rowList;
        		compareJson["tab5"] = tab5Data;
            }
        },
        false, false);
    } else if (type == 3) { // 复诊率
    	var chooseUserIds = $("#rate_userId").val().split(",");// 选中的医师Id
    	for(var x=0;x<chooseUserIds.length;x++) {
    		chooseUserNames.push(userMap.get(chooseUserIds[x]));
    	}
        var url = URL.get("WorkAccount.FURTHER_RATE_QUERY_ECHART");
        var param = "diagnosisUser=" + userId + "&startDate=" + firstDate1 + "&endDate=" + firstDate2 + "&startDate2=" + lastDate1 + "&endDate2=" + lastDate2 + "&diagnosisUserNameList=" + userNames + "&diagnosisUserList=" + allUsersId + "&chooseDiagnosisUserName=" + chooseUserNames;
        ajax.post(url, param, dataType.json,
        function(data) {
            var value = data.value;
            valueMap.set("tab6",value);
            showRateEcharts(value); // echart
            initTable(value.echartsData, "rateTable", "rateScope1", "rateScope2", "rateLastScope1", "rateLastScope2"); // 表格
    		var tab6Data = {
    				"sheetNum": 0,
    				"sheetName": "工作表1",
    				"dataRows":1,
    				"dataCols":2,
    				"title":"",
    				"rowList":[]
    		};
    		tab6Data.title = "复诊率";
    		tab6Data.dataRows = value.echartsData.length;
            var rowList = [];
            var rowListFirst = [];
            rowListFirst.push("姓名");
            rowListFirst.push(formatDate("rateScope1","rateScope2"));
            rowListFirst.push(formatDate("rateLastScope1","rateLastScope2"));
            rowList.push(rowListFirst);
            for(var x=0;x<value.echartsData.length;x++) {
            	var row = [];
            	row.push(formatName(value.echartsData[x].name));
            	row.push(value.echartsData[x].data[0]);
            	row.push(value.echartsData[x].data[1]);
            	rowList.push(row);
            }
            tab6Data.rowList = rowList;
    		compareJson["tab6"] = tab6Data;
        },
        false, false);
    }
}

/**
 * 初始化日期插件
 */
function initTime(id, id2, id3, id4) {
    common.initDate(null, null, null, "#" + id);
    common.initDate(null, null, null, "#" + id2);
    common.initDate(null, null, null, "#" + id3);
    common.initDate(null, null, null, "#" + id4);
}

/**
 * 日期选择器联动
 */
function timeChange(id, id2, buttonId) {
    var $array = [];
    $array.push($("#" + id));
    $array.push($("#" + id2));
    var $allTimeObj = $array[0].parent().find("input[type='text']");
    for (var x = 0; x < $array.length; x++) {
        $($array[x]).on("changeDate", function() {
            var flag = true;
            $.each($allTimeObj, function(index, data) {
                if (common.isEmpty(data.value)) {
                    flag = false;
                }
            });
            if (flag) {
                $("#" + buttonId).click();
            }
        });
    }
}
//-----------------------------------------【工作量对比--就诊人数对比】 【工作量对比--初诊人数对比】----------------------------------

//-----------------------------------------【工作量对比--复诊率】----------------------------------

//-----------------------------------------【工作量对比--复诊率】----------------------------------

//-----------------------------------------【工作量对比--诊断频次】----------------------------------

// 工作量对比--诊断频次组合Map
var compareFrequencyGroupMap = new Map();
var resultFrequencyMap = new Map();

/**
 * 添加对比--诊断频次
 * @param data
 * @param index
 * @returns
 */
function addCompareFrequencyTdHtml(data, index){
	var compareFrequencyMap = compareFrequencyGroupMap.get("index_"+index);
	if(compareFrequencyMap.has(data.val.diseaseId)){
		layer.alert("该诊断已添加！");
		return false;
	}
	var startDate = $("#tab7 div[group='group_index_"+index+"'] input[name='startDate']").val();
	var endDate = $("#tab7 div[group='group_index_"+index+"'] input[name='endDate']").val();
	if(_.isEmpty(startDate) || _.isEmpty(endDate)){
		layer.alert("请先完善开始时间和结束时间！");
		return false;
	}
	var diagnosisUserList = $("#tab7 div[group='group_index_"+index+"'] select[name='compareFrequencySelect']").val();
	if(_.isEmpty(diagnosisUserList)){
		layer.alert("请先选择医师！");
		return false;
	}
	
	var frequency = {diseaseName:data.name};
	compareFrequencyMap.set(data.val.diseaseId, frequency);
	// 分析诊断频次
	analysisDiseaseFrequency(index);
	// 诊断选择table
	var $table = $("#tab7 div[group='group_index_"+index+"'] table[name='compareFrequencyTable']");
	var $body = $table.children(":first").children(":last");
	var tdLength = $body.children().length;
	if((tdLength % 5) == 0){
		$table.append("<tr>"+createCompareFrequencyTdHtml(data.val.diseaseId, frequency.diseaseName, index)+"</tr>");
		$body = $table.children(":first").children(":last");
		if($table.children(":first").children().length == 1){
			$table.attr("style", "width:"+20*(tdLength+1)+"%");
		}
	} else {
		$body.append(createCompareFrequencyTdHtml(data.val.diseaseId, frequency.diseaseName, index));
		if($table.children(":first").children().length == 1){
			$table.attr("style", "width:"+20*(tdLength+1)+"%");
		}
	}
	$table.parent().css("padding-bottom", "7px");
}

/**
 * 删除统计--诊断频次
 * @param diseaseId
 * @param index
 * @returns
 */
function removeCompareFrequencyTdHtml(diseaseId, index){
	var compareFrequencyMap = compareFrequencyGroupMap.get("index_"+index);
	compareFrequencyMap.delete(diseaseId);
	var $table = $("#tab7 div[group='group_index_"+index+"'] table[name='compareFrequencyTable']").empty();;
	var $body = $table.children(":first").children(":last");
	var tdLength = 0;
	compareFrequencyMap.forEach(function(frequency, diseaseId){
		if((tdLength % 5) == 0){
			$table.append("<tr>"+createCompareFrequencyTdHtml(diseaseId, frequency.diseaseName, index)+"</tr>");
			$body = $table.children(":first").children(":last");
			if($table.children(":first").children().length == 1){
				$table.attr("style", "width:"+20*(tdLength+1)+"%");
			}
			tdLength++;
		} else {
			$body.append(createCompareFrequencyTdHtml(diseaseId, frequency.diseaseName, index));
			if($table.children(":first").children().length == 1){
				$table.attr("style", "width:"+20*(tdLength+1)+"%");
			}
			tdLength = (tdLength + 1) % 5;
		}
	});
	if(compareFrequencyMap.size == 0){
		$table.parent().css("padding-bottom", "0px");
	}
	analysisDiseaseFrequency(index);
}

/**
 * 生成对比--诊断频次html
 * @param diseaseId
 * @param diseaseName
 * @param index
 * @returns
 */
function createCompareFrequencyTdHtml(diseaseId, diseaseName, index) {
	var html = "<td style='border: 1px solid #ddd;'>" + diseaseName
			 + "	<a onclick='removeCompareFrequencyTdHtml(\"" + diseaseId + "\",\"" + index + "\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>" 
			 + "</td>";
	return html;
}

/**
 * 分析对比--诊断频次
 * @param index
 * @returns
 */
function analysisDiseaseFrequency(index){
	var compareFrequencyMap = compareFrequencyGroupMap.get("index_"+index);
	var diagnosisUserList = $("#tab7 div[group='group_index_"+index+"'] select[name='compareFrequencySelect']").val();
	var diagnosisUserNameList = [];
	$(diagnosisUserList).each(function(index, userId){
		diagnosisUserNameList.push(userMap.get(userId));
	});
	var diseaseIdList = [];
	var diseaseNameList = [];
	compareFrequencyMap.forEach(function(value, diseaseId){
		diseaseIdList.push(diseaseId);
		diseaseNameList.push(value.diseaseName);
	});
	// 分析查询结果
	var url = URL.get("WorkAccount.WORK_ACCOUNT_COMPARE_DISEASE_FREQUENCY");
	var params = "diseaseIdList="+diseaseIdList + 
				 "&diseaseNameList="+diseaseNameList+
				 "&startDate="+$("#tab7 div[group='group_index_"+index+"'] input[name='startDate']").val()+
				 "&endDate="+$("#tab7 div[group='group_index_"+index+"'] input[name='endDate']").val()+
				 "&diagnosisUserList="+diagnosisUserList+
				 "&diagnosisUserNameList="+diagnosisUserNameList;
	ajax.post(url, params, dataType.json, function(result){
		var tableData = setEchartFrequencyTable(result.value, index);// 生成Table
		valueMap.set("tab7",result.value);
		echartTab7(result.value, index);// 生成图表
		setFrequencyExcelData(tableData, index);// 生成诊断频次导出数据
	}, false, false);
}

/**
 * 设置table数据到Exceldata
 * @param tableData
 * @param index
 * @returns
 */
function setFrequencyExcelData(tableData, index){
	if(!_.isEmpty(tableData)){
		var myChart = echartTab7Map.get("index_"+index);
		var chartType = "colnum";
		if(!common.isEmpty(resultFrequencyMap.get("index_"+index))){
			chartType = resultFrequencyMap.get("index_"+index).chartType;
		}
		var tab7Data = {
				"sheetNum": 0,
				"sheetName": "工作表1",
				"dataRows":tableData.length - 1,
				"dataCols":tableData[0].length - 1,
				"chartType":chartType,
				"title":"",
				"rowList":[]
		};
		tab7Data.title = myChart.getOption().title[0].text;
		tab7Data.rowList = tableData;
		resultFrequencyMap.set("index_"+index, tab7Data);
	}
}

/**
 * 创建对比--诊断频次table
 * @param data
 * @param index
 * @returns
 */
function setEchartFrequencyTable(data, index){
	var xData = data.xData;
	var echartsData = data.echartsData;
	var rowMap = new Map();
	if(!_.isEmpty(echartsData)){
		$(echartsData).each(function(index, rowData){
			rowMap.set(rowData.id, rowData);
		});
	}
	// 记录数据
	var dataList = [];
	// 设置table
	var $table = $("#tab7 div[group='group_index_"+index+"'] table[name='echartFrequencyTable']");
	$table.children(":first").empty();
	$table.children(":last").empty();
	if(!_.isEmpty(xData)){
		// 显示图表，隐藏没有数据
		$("#tab7 div[group='group_index_"+index+"'] div[echarts-body-"+index+"],#tab7 div[group='group_index_"+index+"'] div[echarts-table-"+index+"]").show();
		$("#tab7 div[group='group_index_"+index+"'] div[echarts-empty-"+index+"]").hide();
		// 设置表头信息
		var theadData = ["姓名"];
		var theadHtml = "";
		theadHtml = "<th class='text-center' style='min-width: 100px;'>姓名</th>";
		$.each(xData, function(index, diseaseName){
			theadHtml += "<th class='text-center' style='min-width: 180px;'>"+diseaseName+"</th>"
			theadData.push(diseaseName);
		});
		theadHtml = "<tr class='active'>"+theadHtml+"</tr>";
		dataList.push(theadData);
		// 设置表内容
		var tbodyHtml = "";
		var userIdList = $("#tab7 div[group='group_index_"+index+"'] select[name='compareFrequencySelect']").val();
		if(!_.isEmpty(userIdList)){
			$.each(userIdList, function(index, userId){
				var tbodyData = [];
				var userData = rowMap.get(userId);
				if(!_.isEmpty(userData)){
					tbodyHtml += "<tr><td class='text-center'>"+formatName(userData.name)+"</td>";
					tbodyData.push(formatName(userData.name));
					$.each(userData.data, function(i, count){
						tbodyHtml += "<td class='text-center'>"+(count || "0")+"</td>";
						tbodyData.push(count);
					});
					tbodyHtml += "</tr>";
				}
				dataList.push(tbodyData);
			});
		}
		$table.children(":first").html(theadHtml);
		$table.children(":last").html(tbodyHtml);
		// 设置table宽度
		if($table.width() > $table.parent().width()){
			$table.parent().css("overflow-x", "scroll");
		} else{
			$table.parent().css("overflow-x", "");
		}
	} else{
		$("#tab7 div[group='group_index_"+index+"'] div[echarts-body-"+index+"],#tab7 div[group='group_index_"+index+"'] div[echarts-table-"+index+"]").hide();
		$("#tab7 div[group='group_index_"+index+"'] div[echarts-empty-"+index+"]").show();
	}
	
	return dataList;
}

// 诊断频次下标
var group_index = 0;

/**
 * 创建诊断组
 * @returns
 */
function createDiseaseFrequencyGroup(){
	// 判断是否符合条件
	if($("#tab7").children("div[group]").length != 0 && $("#tab7").children("div[group]:last").find("table[name='compareFrequencyTable']").children(":first").children().length == 0){
		layer.alert("已添加新诊断组！");
		return false;
	}
	// 分配诊断组的独立map
	compareFrequencyGroupMap.set("index_"+group_index, new Map());
	// 创建诊断组
	$("#tab7").append(
		"<div group='group_index_"+group_index+"'><a name='a_group_index_"+group_index+"'></a>" +
		"	<!-- 检索条件 -->" +
		"	<div class='panel-body'>" +
		"		<div class='form-inline'>" +
		"			<input type='text' id='group_startDate_"+group_index+"' name='startDate' class='form-control' placeholder='请选择开始日期' readonly='readonly' />" +
		"			至" +
		"			<input type='text' id='group_endDate_"+group_index+"' name='endDate' class='form-control' placeholder='请选择结束日期' readonly='readonly' disabled='disabled'/>" +
		"			<select class='form-control' select-index='"+group_index+"' name='compareFrequencySelect' multiple='multiple' style='height: 34px; width: 170px;'>" +
		"			</select>" +
		"			<input type='text' name='echartTitle' onchange='updateEchartTitle(this, \""+group_index+"\");' class='form-control' placeholder='请输入图表标题'/>" +
		"			<button class='btn btn-default' onclick='removeTab7Group(\""+group_index+"\");' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i> 删除该组</button>" +
		"		</div>" +
		"	</div>" +
		"	<!-- 诊断名称 -->" +
		"	<div class='panel-body' style='padding-top: 0px;'>" +
		"		<div class='form-inline'>" +
		"			<div class='div-inline'><input type='text' group='"+group_index+"' name='compareFrequency' class='form-control' placeholder='请输入诊断名称' /></div>" +
		"			<div class='div-inline'><a onclick='createDiseaseFrequencyGroup();'><i class='fa fa-plus-square-o fa-fw'></i></a></div>" +
		"		</div>" +
		"	</div>" +
		"	<div class='panel-body' style='padding-top: 0px; padding-bottom: 0px;'>" +
		"		<table class='table table-bordered table-padding-4' name='compareFrequencyTable'>" +
		"			<tbody></tbody>" +
		"		</table>" +
		"	</div>" +
		"	<!-- 诊断频次图表 -->" +
		"	<div class='panel-body' echarts-body-"+group_index+" style='border-top: 1px solid #ddd;display: none;'>" +
		"		<div class='echarts-left'>" +
		"			<div id='echartsCompareFrequency_"+group_index+"' style='height:500px;'></div>" +
		"		</div>" +
//		"		<div class='echarts-right'>" +
//		"			<ul>" +
//		"				<li><img alt='加载失败' src='"+PublicConstant.basePath+"/common/images/echarts/zhuzhuang.png'/><a id='tab7_colnum_"+group_index+"'>柱状图</a></li>" + 
//		"				<li><img alt='加载失败' src='"+PublicConstant.basePath+"/common/images/echarts/tiaoxing.png'/><a id='tab7_bar_"+group_index+"'>条形图</a></li>" +
//		"			</ul>" +
//		"		</div>" +
		"	</div>" +
		"	<div class='panel-body' echarts-empty-"+group_index+" style='border-top: 1px solid #ddd;'>" +
		"		<div class='col-xs-12 text-center'><h4>没有数据！</h4></div>" +
		"	</div>" +
		"	<!-- 诊断频次列表 -->" +
		"	<div class='panel-body' echarts-table-"+group_index+" style='display: none;'>" +
		"		<table class='table table-bordered table-hover' name='echartFrequencyTable'>" +
		"			<thead></thead>" +
		"			<tbody></tbody>" +
		"		</table>" +
		"	</div>" +
		"	<HR style='margin: 10px 0 0 0; border-top: 0px solid #ddd;'>" +
		"</div>"
	);
	$("#tab7").children("div[group]").not(":last").children("HR").css("border-top", "1px solid #337ab7");
	$("#tab7").children("div[group]:last").children("HR").css("border-top", "0px solid #337ab7");
	// 定位
	if(group_index > 0){
		window.location.hash = "#a_group_index_"+group_index;
	}
	// 初始化日期插件
	accountDateValid("group_startDate_"+group_index, "group_endDate_"+group_index);
	$("#group_startDate_"+group_index+",#group_endDate_"+group_index).change(function(){
		var date_index = this.id.split("_")[2];
		if(!_.isEmpty($("#group_startDate_"+date_index).val()) 
				&& !_.isEmpty($("#group_endDate_"+date_index).val()) 
				&& !common.isEmpty(compareFrequencyGroupMap.get("index_"+date_index)) 
				&& compareFrequencyGroupMap.get("index_"+date_index).size > 0){
			analysisDiseaseFrequency(date_index);
		}
	});
	// 初始化诊断
	$("#tab7 div[group='group_index_"+group_index+"'] input[name='compareFrequency']").autocomplete(diseaseListData, {
		width: 170,
		matchContains: true,
		autoFill: false,
		formatItem: function(row, i, max) {        
			return row.name;
		},
        formatMatch: function(row, i, max) {
            return row.name + " " + pinyin.getCamelChars(row.name);
        },
        formatResult: function(row) {
            return row.name;
        }
	}).result(function(event, data, formatted){
		addCompareFrequencyTdHtml(data, $(this).attr("group"));
		$("#tab7 div[group='group_index_"+$(this).attr("group")+"'] input[name='compareFrequency']").val("");
    });
	// 初始化下拉多选
	$("#tab7 div[group='group_index_"+group_index+"'] select[name='compareFrequencySelect']").multiselect({
		templates: {
            ul: "<ul class='multiselect-container dropdown-menu' style='width:170px;'></ul>"
        },
		nonSelectedText : "请选择医师",
		nSelectedText: "个医师",
        maxHeight : 250,
        includeSelectAllOption : true,
        selectAllText: "全部医师",
        allSelectedText: "全部医师",
        selectedClass: 'multiselect-selected',
        buttonWidth: "170px",
        selectIndex: group_index,
        onChange: function(option, checked, select) {
        	var select_index = $(option).parent().attr("select-index");
        	var userIdList = $("#tab7 div[group='group_index_"+select_index+"'] select[name='compareFrequencySelect']").val();
        	if(!_.isEmpty(userIdList) 
        			&& !common.isEmpty(compareFrequencyGroupMap.get("index_"+select_index)) 
    				&& compareFrequencyGroupMap.get("index_"+select_index).size > 0){
        		analysisDiseaseFrequency(select_index);
        	}
        },
        onSelectAll: function() {
        	var select_index = this.options.selectIndex;
        	if(!common.isEmpty(compareFrequencyGroupMap.get("index_"+select_index)) 
    				&& compareFrequencyGroupMap.get("index_"+select_index).size > 0){
        		analysisDiseaseFrequency(select_index);
        	}
        	analysisDiseaseFrequency(select_index);
        }
	});
	if(!_.isEmpty(userList)){
		$.each(userList, function(index, user){
			$("#tab7 div[group='group_index_"+group_index+"'] select[name='compareFrequencySelect']").append("<option value='"+user.userId+"'>"+user.userName+"</option>");
		});
	}
	$("#tab7 div[group='group_index_"+group_index+"'] select[name='compareFrequencySelect']").multiselect("rebuild").multiselect('selectAll', false).multiselect('updateButtonText');
	
	group_index++;
}

/**
 * 删除诊断组
 * @param index
 * @returns
 */
function removeTab7Group(index){
	if($("#tab7").children("div[group]").length == 1){
		layer.alert("只有一组诊断数据，不能进行删除操作！");
		return false;
	}
	layer.confirm("确定要进行【删除】操作吗？", function (lay) {
		$("#tab7 div[group='group_index_"+index+"']").remove();
		$("#tab7").children("div[group]").not(":last").children("HR").css("border-top", "1px solid #337ab7");
		$("#tab7").children("div[group]:last").children("HR").css("border-top", "0px solid #337ab7");
		compareFrequencyGroupMap.delete("index_"+index);
		echartTab7Map.delete("index_"+index);
		resultFrequencyMap.delete("index_"+index);
		layer.close(lay);
    });
}

/**
 * 修改echarts图表标题
 * @param index
 * @returns
 */
function updateEchartTitle(obj, index){
	var myChart = echartTab7Map.get("index_"+index);
	if(!_.isEmpty(myChart) && !_.isEmpty(obj.value)){
		myChart.setOption({title:{text:obj.value, textStyle:{color: "#000000"}}});
		resultFrequencyMap.get("index_"+index).title = obj.value;
	}
}

//-----------------------------------------【工作量对比--诊断频次】----------------------------------

//-----------------------------------------【公共部分】---------------------------------------------
var compareJson = {};

/**
 * 初始化
 */
$().ready(function(){
	// 初始化下拉多选
	initSelectAll("#zhuanSelect","全部转诊医师","全部医师","170px",1,"","non","请选择转诊医师","个医师");
	initSelectAll("#userSelect","全部孕期营养门诊医师","全部医师","210px",2,"","","请选择孕期营养门诊医师","个医师");
	initSelectAll("#masItems","系统营养评价项目","全部评价项目","220px",3,220,"","请选择系统营养评价项目","个项目");
	initSelectAll("#workSelect","全部评价项目操作者","全部操作者","210px",4,"","","请选择评价项目操作者","个操作者");
	initSelectAll("#tab1UserSelect,#diagnosis_count_doctor,#first_diagnosis_count_doctor,#rate_doctor,#outpatientUserSelect,#pregUserSelect","全部医师","全部医师","170px",2,"","","请选择医师","个医师");
	// 初始化自动补全插件
	autocompleteDisease();
	
	//日期插件初始化和限制
	accountDateValid("startDate", "endDate");
	
	// 校验下拉框（item）
	validItemsSelect();
	// 定义按钮事件
	$("#searchButton").on("click", function(){
		if(common.isEmpty($("#startDate").val()) || common.isEmpty($("#endDate").val()) || common.isEmpty($("#userSelect").val())){
			layer.alert("时间和医师不允许为空");
			return;
		}
		$("#tfood").remove();
		$("#tstatic").remove();
		// 诊断项目条件处理，如果诊断项目的输入框为空，那么清空隐藏条件，如果不是空，获取对应的诊断项目的Id
		if (common.isEmpty($("#diseaseName").val())) {
			$("#diseaseId").val("");
		}else{
			var diseaseObj = diseaseMap[$("#diseaseName").val()];
			if(common.isEmpty(diseaseObj)){
				$("#diseaseName").val("");
				$("#diseaseId").val("");
				layer.alert("请选择疾病库中的诊断项目");
				return;
			}else{
				$("#diseaseId").val(diseaseObj.diseaseId);
			}
		}
		// 日期选择器联动
		var sDate = $("#startDate").val();// 工作量统计-开始日期
		var eDate = $("#endDate").val();// 工作量统计-结束日期
		if(common.isEmpty($("#tab1StartDate").val()) && common.isEmpty($("#tab1EndDate").val())) {// 孕期营养门诊门诊量统计
			$("#tab1EndDate").attr("disabled",false);
			$("#tab1StartDate").val(sDate);
			$("#tab1EndDate").val(eDate);
		}
		if(common.isEmpty($("#outpatientStartDate").val()) && common.isEmpty($("#outpatientEndDate").val())) {// 不同门诊单元门诊量统计
			$("#outpatientEndDate").attr("disabled",false);
			$("#outpatientStartDate").val(sDate);
			$("#outpatientEndDate").val(eDate);
		}
		if(common.isEmpty($("#pregStartDate").val()) && common.isEmpty($("#pregEndDate").val())) {// 初诊患者孕期分布
			$("#pregEndDate").attr("disabled",false);
			$("#pregStartDate").val(sDate);
			$("#pregEndDate").val(eDate);
		}
		if(common.isEmpty($("#tab7 input[name='startDate']").val()) && common.isEmpty($("#tab7 input[name='endDate']").val())) {// 诊断频次
			$("#tab7 input[name='endDate']").attr("disabled",false);
			$("#tab7 input[name='startDate']").val(sDate);
			$("#tab7 input[name='endDate']").val(eDate);
		}
		// tab1 的结束日期限制
		validEndDate("tab1StartDate","tab1EndDate");
		// 整理参数
		$("#diagnosisUser").val($("#userSelect").val().join(","));
		if(common.isEmpty($("#zhuanSelect").val())) {
			$("#diagnosisZhuanUser").val("");
			$("#diagnosisZhuanUserStatistic").val("");
		}else {
			$("#diagnosisZhuanUser").val($("#zhuanSelect").val().join(","));
			$("#diagnosisZhuanUserStatistic").val($("#zhuanSelect").val().join(","));
		}
		if(common.isEmpty($("#masItems").val())) {
			$("#diagnosisMasItems").val("");
			$("#diagnosisMasItemsStatistic").val("");
			$("#diagnosisMasItemAuthors").val("");
			$("#diagnosisMasItemAuthorsStatistic").val("");
		}else {
			$("#diagnosisMasItemsStatistic").val($("#masItems").val().join(","));
			$("#diagnosisMasItems").val($("#masItems").val().join(","));
			if(!common.isEmpty($("#workSelect").val())){
				$("#diagnosisMasItemAuthors").val($("#workSelect").val().join(","));
				$("#diagnosisMasItemAuthorsStatistic").val($("#workSelect").val().join(","));
			}
		}
		//统计列表datatable查询参数
		$("#diagnosisUserStatistic").val($("#userSelect").val().join(","));
		$("#startStatisticDate").val($("#startDate").val());
		$("#endStatisticDate").val($("#endDate").val());
		$("#diseaseIdStatistic").val($("#diseaseId").val());
		// 处理医师名称
		var diagnosisUserName = [];
		$.each($("#userSelect").val(), function(index, value){
			diagnosisUserName.push(userMap.get(value));
		});
		$("#diagnosisUserName").val(diagnosisUserName.join(","));
		// 加载datatable		
		var table = datatable.table(accountTableOption);
		var dataList = datatable.getAllData(table);
		$("#monthAverageNum").html(Math.round(dataList[dataList.length - 1].diagnosisRen / (dataList.length - 1)));
		datatable.table(statisticTableOption);
		
		// 刷新诊断频次
		changeFrequencyDate();
	});
	//---------------------scd--------------------
	//初始化门诊量统计
	initOutpatienData();
	//初始化孕期初诊分布
	pregDistribution();
	//---------------------scd--------------------
	//---------------------dhs--------------------
	//日期插件初始化和限制 就诊人数对比
	accountDateValid("firstScope1", "firstScope2");
	accountDateValid("lastScope1", "lastScope2");
	//日期插件初始化和限制 初诊人数对比
	accountDateValid("firstDiagnosisScope1", "firstDiagnosisScope2");
	accountDateValid("firstDiagnosislastScope1", "firstDiagnosislastScope2");
	//日期插件初始化和限制 复诊率对比
	accountDateValid("rateScope1", "rateScope2");
	accountDateValid("rateLastScope1", "rateLastScope2");
	// 隐藏echarts div
	$("#diagnosisCountBody").css("display","none");// 就诊人数
	$("#firstDiagnosisCountBody").css("display","none");// 初诊人数
	$("#rateBody").css("display","none");// 复诊率对比	
/*	// 日期选择器 联动 就诊人数对比
	timeChange("firstScope1","firstScope2","search_diagnosis_value");
	timeChange("lastScope1","lastScope2","search_diagnosis_value");
	// 日期选择器 联动 初诊人数对比
	timeChange("firstDiagnosisScope1","firstDiagnosisScope2","search_first_diagnosis_value");
	timeChange("firstDiagnosislastScope1","firstDiagnosislastScope2","search_first_diagnosis_value");
	// 日期选择器 联动 复诊率对比
	timeChange("rateScope1","rateScope2","search_rate_value");
	timeChange("rateLastScope1","rateLastScope2","search_rate_value");*/
	// 点击事件
	$("button[name='echarts_button']").click(function() {
		if(this.id == 'search_diagnosis_value') {// 就诊人数
			if(validTime("firstScope1","firstScope2","lastScope1","lastScope2","diagnosis_count_doctor")) {// 时间必选
				graphMap.set("tab4","bar");
				//初始化医师
				$("#userId").val($("#diagnosis_count_doctor").val());
				// 初始化echarts
				initDiagnosisCountEcharts($("#firstScope1").val(),$("#firstScope2").val(),$("#lastScope1").val(),$("#lastScope2").val(),$("#userId").val(),1);
				// 初诊人数、复诊率 日期联动
				if(common.isEmpty($("#firstDiagnosisScope1").val()) && common.isEmpty($("#firstDiagnosisScope2").val()) && common.isEmpty($("#firstDiagnosislastScope1").val()) && common.isEmpty($("#firstDiagnosislastScope2").val())) {// 初诊人数
					$("#firstDiagnosisScope2").attr("disabled",false);
					$("#firstDiagnosislastScope2").attr("disabled",false);
					$("#firstDiagnosisScope1").val($("#firstScope1").val());
					$("#firstDiagnosisScope2").val($("#firstScope2").val());
					$("#firstDiagnosislastScope1").val($("#lastScope1").val());
					$("#firstDiagnosislastScope2").val($("#lastScope2").val());
				}
				if(common.isEmpty($("#rateScope1").val()) && common.isEmpty($("#rateScope2").val()) && common.isEmpty($("#rateLastScope1").val()) && common.isEmpty($("#rateLastScope2").val())) {// 复诊率
					$("#rateScope2").attr("disabled",false);
					$("#rateLastScope2").attr("disabled",false);
					$("#rateScope1").val($("#firstScope1").val());
					$("#rateScope2").val($("#firstScope2").val());
					$("#rateLastScope1").val($("#lastScope1").val());
					$("#rateLastScope2").val($("#lastScope2").val());
				}
			}
		}else if(this.id == 'search_first_diagnosis_value'){// 初诊人数
			if(validTime("firstDiagnosisScope1","firstDiagnosisScope2","firstDiagnosislastScope1","firstDiagnosislastScope2","first_diagnosis_count_doctor")) {// 时间必选
				graphMap.set("tab5","bar");
				//初始化医师
				$("#first_diagnosis_userId").val($("#first_diagnosis_count_doctor").val());
				// 初始化echarts
				initDiagnosisCountEcharts($("#firstDiagnosisScope1").val(),$("#firstDiagnosisScope2").val(),$("#firstDiagnosislastScope1").val(),$("#firstDiagnosislastScope2").val(),$("#first_diagnosis_userId").val(),2);
				// 复诊率 日期联动
				if(common.isEmpty($("#rateScope1").val()) && common.isEmpty($("#rateScope2").val()) && common.isEmpty($("#rateLastScope1").val()) && common.isEmpty($("#rateLastScope2").val())) {// 复诊率
					$("#rateScope2").attr("disabled",false);
					$("#rateLastScope2").attr("disabled",false);
					$("#rateScope1").val($("#firstDiagnosisScope1").val());
					$("#rateScope2").val($("#firstDiagnosisScope2").val());
					$("#rateLastScope1").val($("#firstDiagnosislastScope1").val());
					$("#rateLastScope2").val($("#firstDiagnosislastScope2").val());
				}
			}
		}else if(this.id == 'search_rate_value') {// 复诊率
			if(validTime("rateScope1","rateScope2","rateLastScope1","rateLastScope2","rate_doctor")) {// 时间必选
				graphMap.set("tab6","bar");
				// 初始化医师
				$("#rate_userId").val($("#rate_doctor").val());
				// 初始化echarts
				initDiagnosisCountEcharts($("#rateScope1").val(),$("#rateScope2").val(),$("#rateLastScope1").val(),$("#rateLastScope2").val(),$("#rate_userId").val(),3);
			}
		}
	});
	// echarts 分辨率缩放
	window.addEventListener("resize", function() {
		echartsMap.forEach(function (value, key, map) {
		     value.resize();
		});
	});
	//--------------------dhs------------------------
	
	//初始化-孕期营养门诊门诊量统计
	initNutritionOutpatient();
	
	$("#myTab a[data-toggle='tab']").click(function() {
	    if ($(this).attr("href") == "#tab1") {
	        if (common.isEmpty(valueMap.get("tab1"))) {
	            $("#tab1").show().siblings().hide();
	        } else {
	            $("#tab1").show(function() {
	                var echartsValue = valueMap.get("tab1");
	                if(common.isEmpty(graphMap.get("tab1")) || "bar" == graphMap.get("tab1")) {
	                	echartsMap.get("tab1").setOption(echartObject.getOptionBar(echartsValue).tab1, true);
	                }else if("chart" == graphMap.get("tab1")) {
	                	echartsMap.get("tab1").setOption(echartObject.getOptionChart(echartsValue).tab1, true);
	                }	                
	                echartsMap.get("tab1").resize();
	            }).siblings().hide();
	        };
	    } else if ($(this).attr("href") == "#tab2") {
	    	validEndDate("outpatientStartDate","outpatientEndDate");
	        if (common.isEmpty(valueMap.get("tab2"))) {
	            $("#tab2").show().siblings().hide();
	        } else {
	            $("#tab2").show(function() {
	                var echartsValue = valueMap.get("tab2");
	                if(common.isEmpty(graphMap.get("tab2")) || "bar" == graphMap.get("tab2")) {
		                echartsMap.get("tab2-1").setOption(echartObject.getOptionBar(echartsValue).tab2, true);
	                }else if("chart" == graphMap.get("tab2")) {
		                echartsMap.get("tab2-1").setOption(echartObject.getOptionChart(echartsValue).tab2, true);
	                }
	                echartsMap.get("tab2-1").resize();
	                showDiagnosisCountEchartsTab2Right(echartsValue);// 特殊，直接调用方法
	                echartsMap.get("tab2-2").resize();
	            }).siblings().hide();
	        };
	    } else if ($(this).attr("href") == "#tab3") { // 特殊，直接调用方法
	    	validEndDate("pregStartDate","pregEndDate");
	    	if (common.isEmpty(valueMap.get("tab3"))) {
	            $("#tab3").show().siblings().hide();
	        } else {
	            $("#tab3").show(function() {
	                var array = valueMap.get("tab3");
	                for (var i = 0; i < array.length; i++) {
	                    if (i < array.length - 1) {
	                        var echartId = "echartTab" + i;
	                        showDiagnosisCountEchartsTab3(array[i], echartId, i);
	                        echartsMap.get("tab3-" + i).resize();
	                    } else {
	                        showDiagnosisCountEchartsTab3(array[i], "echartTabSum", i);
	                        echartsMap.get("tab3-" + i).resize();
	                    };
	                };
	            }).siblings().hide();
	        };
	    } else if ($(this).attr("href") == "#tab4") {
	    	validEndDate("firstScope1","firstScope2");
	    	validEndDate("lastScope1","lastScope2");
	        if (common.isEmpty(valueMap.get("tab4"))) {
	            $("#tab4").show().siblings().hide();
	        } else {
	            $("#tab4").show(function() {
	                var echartsValue = valueMap.get("tab4");
	                if(common.isEmpty(graphMap.get("tab4")) || "bar" == graphMap.get("tab4")) {
	                	echartsMap.get("tab4").setOption(echartObject.getOptionBar(echartsValue).tab4, true);
	                }else if("chart" == graphMap.get("tab4")) {
	                	echartsMap.get("tab4").setOption(echartObject.getOptionChart(echartsValue).tab4, true);
	                }	                
	                echartsMap.get("tab4").resize();
	            }).siblings().hide();
	        };
	    } else if ($(this).attr("href") == "#tab5") {
	    	validEndDate("firstDiagnosisScope1","firstDiagnosisScope2");
	    	validEndDate("firstDiagnosislastScope1","firstDiagnosislastScope2");
	        if (common.isEmpty(valueMap.get("tab5"))) {
	            $("#tab5").show().siblings().hide();
	        } else {
	            $("#tab5").show(function() {
	                var echartsValue = valueMap.get("tab5");
	                if(common.isEmpty(graphMap.get("tab5")) || "bar" == graphMap.get("tab5")) {
	                	echartsMap.get("tab5").setOption(echartObject.getOptionBar(echartsValue).tab5, true);
	                }else if("chart" == graphMap.get("tab5")) {
	                	echartsMap.get("tab5").setOption(echartObject.getOptionChart(echartsValue).tab5, true);
	                }	                
	                echartsMap.get("tab5").resize();
	            }).siblings().hide();
	        };
	    } else if ($(this).attr("href") == "#tab6") {
	    	validEndDate("rateScope1","rateScope2");
	    	validEndDate("rateLastScope1","rateLastScope2");
	        if (common.isEmpty(valueMap.get("tab6"))) {
	            $("#tab6").show().siblings().hide();
	        } else {
	            $("#tab6").show(function() {
	                var echartsValue = valueMap.get("tab6");
	                if(common.isEmpty(graphMap.get("tab6")) || "bar" == graphMap.get("tab6")) {
		                echartsMap.get("tab6").setOption(echartObject.getOptionBar(echartsValue).tab6, true);
	                }else if("chart" == graphMap.get("tab6")) {
		                echartsMap.get("tab6").setOption(echartObject.getOptionChart(echartsValue).tab6, true);
	                }
	                echartsMap.get("tab6").resize();
	            }).siblings().hide();
	        };
	    } else if ($(this).attr("href") == "#tab7") {
	    	validEndDate("tab7 input[name='startDate']","tab7 input[name='endDate']");
	        if (common.isEmpty(valueMap.get("tab7"))) {
	            $("#tab7").show().siblings().hide();
	        } else {
	            $("#tab7").show(function() {
	                var echartsValue = valueMap.get("tab7");
		            echartsMap.get("tab7").setOption(echartObject.getOptionBar(echartsValue).tab7, true);
	                echartsMap.get("tab7").resize();
	            }).siblings().hide();
	        };
	    };
	});
	
	// 初始化工作量对比--诊断频次
	createDiseaseFrequencyGroup();
	
	$(window).resize(function(){
		$($("#tab7 div[group] table[name='echartFrequencyTable']")).each(function(index, table){
			if($(table).width() > $(table).parent().width()){
				$(table).parent().css("overflow-x", "scroll");
			} else{
				$(table).parent().css("overflow-x", "");
			}
		});
    });
	
	layer.closeAll();
});

/**
 * 自动补全诊断项目
 */
function autocompleteDisease(){
	// 诊断项目添加到自动补全
	$("#diseaseName,#addDiseaseFrequency").autocomplete(diseaseListData, {
		width: 170,
		matchContains: true,
		autoFill: false,
		formatItem: function(row, i, max) {        
			return row.name;
		},
        formatMatch: function(row, i, max) {
            return row.name + " " + pinyin.getCamelChars(row.name);
        },
        formatResult: function(row) {
            return row.name;
        }
	}).result(function(event, data, formatted){
		//给diseaseId赋值
		if(this.id == "diseaseName"){
			$("#diseaseId").val(data.val.diseaseId);
		} else if(this.id == "addDiseaseFrequency"){
			addTdHtml(data);
			$("#addDiseaseFrequency").val("");
		}
    });
}

/**
 * 计算AddDayCount天前的日期
 * 
 * @param AddDayCount
 * @param date
 * @returns {String}
 */
function getDateStr(AddDayCount, date) {
	var dd = common.isEmpty(date)?new Date():new Date(date);
	dd.setDate(dd.getDate()+AddDayCount);// 获取AddDayCount天后的日期
	var y = dd.getFullYear();   
	var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);// 获取当前月份的日期，不足10补0
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();// 获取当前几号，不足10补0
	return y+"-"+m+"-"+d;     
}

/**
 * 限定时间的方法
 * 1. 下限为空时上限不能选择
 * 2. 上限选择完，调整下限时如果差值超过一年，或者大于上限，则清空上限，并调整上限的范围
 * 3. startId（下限），endId（上限）表示两个日期的插件的id，不可颠倒
 */
function accountDateValid(startId, endId){
	var $startObj = $("#"+startId);
	var $endObj = $("#"+endId);
	// 初始化日期插件
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	common.initDate(null,null,2,"#"+ startId);
	$startObj.datetimepicker("setEndDate",nowDate);
	common.initDate(null,null,2,"#" + endId);
	$endObj.datetimepicker("setEndDate",nowDate);
	
	// 限制日期选择插件
	$startObj.on("change", function(){
		var endDate = addYearStr(1, $startObj.val());
		//如果超过当前时间，那么最多只能是当前时间
		if(common.getDateDiff(endDate, nowDate) < 0){
			endDate = nowDate;
		}
		$endObj.datetimepicker("setStartDate",$startObj.val());
		$endObj.datetimepicker("setEndDate",endDate);
		var dateDiff = common.getDateDiff($startObj.val(), $endObj.val());
		if(!common.isEmpty($endObj.val()) && (dateDiff > 365 || dateDiff < 0)){
			$endObj.val("");
		}
		$endObj.attr("disabled", false);
	});
}

/**
 * 限定结束时间的方法，写在tab点击事件之中
 * 如果写在结束时间的点击事件中，会有细微延迟
 */
function validEndDate(startId, endId) {
	var $startObj = $("#"+startId);
	if(!common.isEmpty($startObj.val())) {// 如果开始时间有值，就要限定结束时间只能在一年之后
		// 初始化日期插件
		var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
		var $endObj = $("#"+endId);
		var endDate = addYearStr(1, $startObj.val());
		//如果超过当前时间，那么最多只能是当前时间
		if(common.getDateDiff(endDate, nowDate) < 0){
			endDate = nowDate;
		}
		$endObj.datetimepicker("setStartDate",$startObj.val());
		$endObj.datetimepicker("setEndDate",endDate);
	};
}

/**
 * 计算AddYearCount天后的日期
 * 
 * @param AddDayCount
 * @param date
 * @returns {String}
 */
function addYearStr(AddYearCount, date) {
	var dd = common.isEmpty(date)?new Date():new Date(date);
	var y = dd.getFullYear() + AddYearCount;
	var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0    
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0    
	return y+"-"+m+"-"+d;     
}

/**
 * 初始化下拉多选框
 */
function initSelectAll(id,name,selectAllName,width,type,ulWidth,nonSelected,nonSelectName,nonSelectText) {
	if(common.isEmpty(ulWidth)) {
		ulWidth = 170;
	}
	$(id).multiselect({
		templates: {
            ul: '<ul class="multiselect-container dropdown-menu" style=\'width:'+ulWidth+'px;\'></ul>'
        },
		nonSelectedText : nonSelectName,
		nSelectedText: nonSelectText,
        maxHeight : 250,
        includeSelectAllOption : true,
        selectAllText: selectAllName,
        allSelectedText: name,
        selectedClass: 'multiselect-selected',
        buttonWidth: width
	});
	if(!_.isEmpty(zhuanDoctorList) && type == 1){
		$.each(zhuanDoctorList, function(index, user){
			$(id).append("<option value='"+user.doctorId+"'>"+user.doctorName+"</option>");
		});
	}else if(!_.isEmpty(userList) && type == 2){
		$.each(userList, function(index, user){
			$(id).append("<option value='"+user.userId+"'>"+user.userName+"</option>");
		});
	}else if(!_.isEmpty(masItemNames) && type == 3){
		$.each(masItemNames, function(index, user){
			$(id).append("<option value='"+user.inspectCode+"'>"+user.inspectName+"</option>");
		});
	}else if(!_.isEmpty(masItemAuthor) && type == 4){
		$.each(masItemAuthor, function(index, user){
			$(id).append("<option value='"+user.userId+"'>"+user.userName+"</option>");
		});
	}
	if(common.isEmpty(nonSelected)) {
		$(id).multiselect("rebuild").multiselect('selectAll', false).multiselect('updateButtonText');
	}else {
		$(id).multiselect("rebuild").multiselect('updateButtonText');
	}

}

/**
 * 校验系统营养评价项目和系统营养评价项目操作者
 * 
 * @param AddDayCount
 * @param date
 * @returns {String}
 */
function validItemsSelect() {
	$("#masItems").change(function() {
		var flag = false;
		var $div = $("#workSelect").next();
		for(var x=0;x<this.length;x++) {
			if(this[x].selected == true) {
				flag = true;
				break;
			}
		}
		if(flag) {// 系统营养评价项目选择了
			$div.find("ul li input[type='checkbox']").eq(0).attr("disabled",false);
			$("#workSelect").multiselect('refresh');
		}else {// 系统营养评价项目一个也没选
			$div.find("ul li input[type='checkbox']").attr("checked",false).attr("disabled",true);
			$div.find("button span").text("请先选择系统营养评价项目");
		}
	});
}
//-----------------------------------------【公共部分】----------------------------------

function createExcelButton(){
	// 整理参数
	if(common.isEmpty($("#startDate").val()) || common.isEmpty($("#endDate").val()) || common.isEmpty($("#userSelect").val())){
		layer.alert("时间和医师不允许为空");
		return;
	}
	$("#diagnosisUser").val($("#userSelect").val().join(","));
	var diagnosisZhuanUser = "";
	var diagnosisZhuanUserCode = "";
	if(!common.isEmpty($("#zhuanSelect").val())) {
		var array = [];
		var arrayCode = [];
		for(var x=0;x<$("#zhuanSelect").val().length;x++) {
			array.push(zhuanUserMap.get($("#zhuanSelect").val()[x]));
			arrayCode.push($("#zhuanSelect").val()[x]);
		}
		diagnosisZhuanUser = array.join(",");
		diagnosisZhuanUserCode = arrayCode.join(",");
	}
	var diagnosisMasItems = "";
	var diagnosisMasItemsCode = "";
	var diagnosisMasItemAuthors = "";
	var diagnosisMasItemAuthorsCode = "";
	if(!common.isEmpty($("#masItems").val())) {
		var array = [];
		var arrayCode = [];
		var array2 = [];
		var array2Code = [];
		for(var x=0;x<$("#masItems").val().length;x++) {
			array.push(itemNamesMap.get($("#masItems").val()[x]));
			arrayCode.push($("#masItems").val()[x]);
		}
		for(var x=0;x<$("#workSelect").val().length;x++) {
			array2.push(masItemAuthorMap.get($("#workSelect").val()[x]));
			array2Code.push($("#workSelect").val()[x]);
		}
		diagnosisMasItems = array.join(",");
		diagnosisMasItemsCode = arrayCode.join(",");
		diagnosisMasItemAuthors = array2.join(",");
		diagnosisMasItemAuthorsCode = array2Code.join(",");
	}
	var disCondtion = "";
	if(!common.isEmpty($("#diseaseId").val())){
		disCondtion = "&diseaseId=" + $("#diseaseId").val() + "&diseaseName=" + $("#diseaseName").val();
	}
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	var diseaseFrequency = getDiseaseFrequencyExcel();
	
	var diagnosisUserName = [];
	$.each($("#userSelect").val(), function(index, value){
		diagnosisUserName.push(userMap.get(value));
	});
	var url = URL.get("WorkAccount.CREATE_EXCEL_ACCOUNT");
	var params = "startDate=" + startDate + 
				 "&endDate=" + endDate + 
				 "&title=" + diseaseFrequency.title + 
				 "&dataList=" + diseaseFrequency.dataList + 
				 "&diagnosisZhuanUserName=" + diagnosisZhuanUser + 
				 "&diagnosisZhuanUser=" + diagnosisZhuanUserCode + 
				 "&diagnosisMasItemsName=" + diagnosisMasItems + 
				 "&diagnosisMasItems=" + diagnosisMasItemsCode + 
				 "&diagnosisMasItemAuthorsName=" + diagnosisMasItemAuthors + 
				 "&diagnosisMasItemAuthors=" + diagnosisMasItemAuthorsCode + 
				 "&diagnosisUserName=" + diagnosisUserName.join(",") + 
				 "&diagnosisUser=" + $("#userSelect").val().join(",") + disCondtion; 
	ajax.post(url, params, dataType.json, function(data){
		window.open(data.value);
	}, true, false);
}

/**
 * 对比excel导出
 * @returns
 */
function createCompareExcel(){
	// 诊断频次
	if(!common.isEmpty(resultFrequencyMap)){
		var index = 0;
		resultFrequencyMap.forEach(function(value, key){
			compareJson["tab7"+(index++)] = value;
		});
	}
	var url = URL.get("WorkAccount.CREATE_EXCEL_COMPARE");
	var params = "dataList=" + JSON.stringify(compareJson);
	ajax.post(url, params, dataType.json, function(data){
		window.open(data.value);
	}, true, false);
}
