/**
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 * 
 * 阅读说明：
 * 
 * 本js为工作量对比-就诊人数对比 单独echarts配置文件，统一被work_account_view.jsp引用
 * echarts点击事件统一放在在work_account_view.js中
 * 
 */

/**
 * 显示就诊人数对比echarts图表
 */
 var obj={
	name:'初诊孕期患者分布',
	type:"pie",
	radius : '60%',
	center: ['50%', '60%'],
	data:[{"name":"无数据","value":"0"}]
};
function showDiagnosisCountEchartsTab3Left(echartsValue) {
	//饼状图
	var option_pie = echartObject.getOptionPie(echartsValue).tab3_1;
    // 显示echarts div
    $("#tab3EchartLeft").css("display", "block");
    pieNoData(echartsValue,obj,option_pie);
    // 初始化echarts	                    
    var myChart1 = echarts.init(document.getElementById("echartTableft"));
    // 默认柱状图
    myChart1.setOption(option_pie,true);
    // 随着分辨率缩放
    echartsMap.set("tab3-1",myChart1);
}


/**
 * 显示就诊人数对比echarts图表
 */
function showDiagnosisCountEchartsTab3Right(echartsValue) {
	//饼状图
	var option_pie = echartObject.getOptionPie(echartsValue).tab3_2;
    // 显示echarts div
    $("#tab3EchartLeft").css("display", "block");
    pieNoData(echartsValue,obj,option_pie);
    // 初始化echarts	                    
    var myChart2 = echarts.init(document.getElementById("echartTabRight"));
    // 默认柱状图
    myChart2.setOption(option_pie,true);
    // 随着分辨率缩放
    echartsMap.set("tab3-2",myChart2);
}


/**
 * 给饼形图无数据时特殊
 * @param pieData
 * @param obj
 * @param option
 */
function pieNoData(echartsValue,obj,option){
	 var flag=0;
	$(echartsValue.pieData).each(function(index,obj){
    	if(obj.value != 0){
    		flag =1;
    	}
    });
    if(flag == 0){
    	option.series.length = 0;
    	option.series.push(obj);
    	option.legend=echartsValue.legend;
    	option.legend.data = ["无数据"];
    }
}