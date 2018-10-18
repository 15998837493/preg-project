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
function showDiagnosisCountEcharts(echartsValue) {
	// 图例名称可重复
	var legend = echartsValue.legend;
	legend.formatter = function(p) {
		if(p.indexOf(",") > -1) {
			return p.substring(0,p.indexOf(","));
		}else {
			return p;
		}
	};
    // 柱状图
    var option_bar_tab4 = echartObject.getOptionBar(echartsValue).tab4;
    //条形图
    var option_tiao_tab4 = echartObject.getOptionChart(echartsValue).tab4;
    // 显示echarts div
    $("#diagnosisCountBody").css("display", "block");
    // 初始化echarts	                    
    var myChart = echarts.init(document.getElementById('echartsDiagnosisCount'));
    // 默认柱状图
    myChart.setOption(option_bar_tab4, true);
    // 随着分辨率缩放
    echartsMap.set("tab4",myChart);
    //各种图形按钮切换事件绑定
    $("*[name='button']").click(function() {
        if (this.id == 'diagnosis_count_bar_button' || $(this).prev().attr("id") == 'diagnosis_count_bar_button') {
            myChart.setOption(option_bar_tab4, true);
            compareJson["tab4"]["chartType"] = "colnum";
            graphMap.set("tab4","bar");
        } else if (this.id == 'diagnosis_count_leaf_button' || $(this).prev().attr("id") == 'diagnosis_count_leaf_button') {
            myChart.setOption(option_tiao_tab4, true);
            compareJson["tab4"]["chartType"] = "bar";
            graphMap.set("tab4","chart");
        }
    });
}