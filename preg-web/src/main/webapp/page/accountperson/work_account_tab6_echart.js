/**
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 * 
 * 阅读说明：
 * 
 * 本js为工作量对比-复诊率对比 单独echarts配置文件，统一被work_account_view.jsp引用
 * echarts点击事件统一放在在work_account_view.js中
 * 
 */

/**
 * 显示复诊率对比echarts图表
 */
function showRateEcharts(echartsValue) {
    //柱状图
    var option_bar_tab6 = echartObject.getOptionBar(echartsValue).tab6;
    //条形图
    var option_tiao_tab6 = echartObject.getOptionChart(echartsValue).tab6;
    // 显示echarts div
    $("#rateBody").css("display", "block");
    // 初始化echarts	                    
    var myChart = echarts.init(document.getElementById('echartsRate'));
    // 默认柱状图
    myChart.setOption(option_bar_tab6, true);
    // 随着分辨率缩放
    echartsMap.set("tab6",myChart);
    //各种图形按钮切换事件绑定
    $("*[name='button']").click(function() {
        if (this.id == 'rate_bar_button' || $(this).prev().attr("id") == 'rate_bar_button') {
            myChart.setOption(option_bar_tab6, true);
            compareJson["tab6"]["chartType"] = "colnum";
            graphMap.set("tab6","bar");
        } else if (this.id == 'rate_leaf_button' || $(this).prev().attr("id") == 'rate_leaf_button') {
            myChart.setOption(option_tiao_tab6, true);
            compareJson["tab6"]["chartType"] = "bar";
            graphMap.set("tab6","chart");
        }
    });
}