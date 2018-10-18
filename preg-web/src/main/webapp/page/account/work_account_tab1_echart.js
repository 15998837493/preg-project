/**
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 * 
 * 阅读说明：
 * 
 * 本js为工作量对比-孕期营养门诊门诊量统计 单独echarts配置文件，统一被work_account_view.jsp引用
 * echarts点击事件统一放在在work_account_view.js中
 * 
 */

/**
 * 显示孕期营养门诊门诊量统计echarts图表
 */
function showTab1Echarts(echartsValue) {
    //柱状图
    var option_bar = echartObject.getOptionBar(echartsValue).tab1;
    //条形图
    var option_tiao = echartObject.getOptionChart(echartsValue).tab1;
    // 初始化echarts	                    
    var myChart = echarts.init(document.getElementById('echartsTab1Count'));
    myChart.setOption(option_bar, true);
    // 随着分辨率缩放
    echartsMap.set("tab1",myChart);
    //各种图形按钮切换事件绑定
    $("*[name='button']").click(function() {
        if (this.id == 'tab1_bar_button' || $(this).prev().attr("id") == 'tab1_bar_button') {// 切换为柱状图
            myChart.setOption(option_bar, true);
            compareJson["tab1"]["chartType"] = "colnum";
            graphMap.set("tab1","bar");
        } else if (this.id == 'tab1_leaf_button' || $(this).prev().attr("id") == 'tab1_leaf_button') {// 切换为条形图
            myChart.setOption(option_tiao, true);
            compareJson["tab1"]["chartType"] = "bar";
            graphMap.set("tab1","chart");
        }
    });
}