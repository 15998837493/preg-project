/**
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 * 
 * 阅读说明：
 * 
 * 本js为工作量对比-初诊人数对比 单独echarts配置文件，统一被work_account_view.jsp引用
 * echarts点击事件统一放在在work_account_view.js中
 * 
 */

/**
 * 显示初诊人数对比echarts图表
 */
function showFirstDiagnosisCountEcharts(echartsValue) {
	// 图例名称可重复
	var legend = echartsValue.legend;
	legend.formatter = function(p) {
		if(p.indexOf(",") > -1) {
			return p.substring(0,p.indexOf(","));
		}else {
			return p;
		}
	};
    //柱状图
    var option_bar_tab5 = echartObject.getOptionBar(echartsValue).tab5;
    //条形图
    var option_tiao_tab5 = echartObject.getOptionChart(echartsValue).tab5;
    // 显示echarts div
    $("#firstDiagnosisCountBody").css("display", "block");
    // 初始化echarts	                    
    var myChart = echarts.init(document.getElementById('echartsFirstDiagnosisCount'));
    // 默认柱状图
    myChart.setOption(option_bar_tab5, true);
    // 随着分辨率缩放
    echartsMap.set("tab5",myChart);
    //各种图形按钮切换事件绑定
    $("*[name='button']").click(function() {
        if (this.id == 'first_diagnosis_count_bar_button' || $(this).prev().attr("id") == 'first_diagnosis_count_bar_button') {
            myChart.setOption(option_bar_tab5, true);
            compareJson["tab5"]["chartType"] = "colnum";
            graphMap.set("tab5","bar");
        } else if (this.id == 'first_diagnosis_count_leaf_button' || $(this).prev().attr("id") == 'first_diagnosis_count_leaf_button') {
            myChart.setOption(option_tiao_tab5, true);
            compareJson["tab5"]["chartType"] = "bar";
            graphMap.set("tab5","chart");
        }
    });
}