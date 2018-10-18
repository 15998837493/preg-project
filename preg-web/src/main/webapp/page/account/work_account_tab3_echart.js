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
function showDiagnosisCountEchartsTab3(echartsValue,echartId,i) {
	//饼状图
	var option_pie = echartObject.getOptionPie(echartsValue).tab3;
    // 显示echarts div
    $("#tab3EchartLeft").css("display", "block");
    echartObject.pieNoData(echartsValue,obj,option_pie);
    // 初始化echarts	                    
    var myChart = echarts.init(document.getElementById(echartId));
    // 默认饼状图
    myChart.setOption(option_pie,true);
    // 随着分辨率缩放
    echartsMap.set("tab3-"+i,myChart);
}