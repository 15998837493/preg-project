/**
 * ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
 * 
 * 阅读说明：
 * 
 * 本js为工作量对比-就诊人数对比 单独echarts配置文件，统一被work_account_view.jsp引用
 * echarts点击事件统一放在在work_account_view.js中
 * 
 */


var objTab2={
	name:'患者分布',
	type:"pie",
	radius : '40%',
	center: ['50%', '50%'],
	data:[{"name":"无数据","value":"0"}]
};
/**
 * 显示就诊人数对比echarts图表
 */
function showDiagnosisCountEchartsTab2Left(echartsValue) {
    if (!common.isEmpty(echartsValue.legend.data) && echartsValue.legend.data.length > 0) {
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
        var option_bar = echartObject.getOptionBar(echartsValue).tab2;
        //条形图
        var option_tiao = echartObject.getOptionChart(echartsValue).tab2;
        // 显示echarts div
        $("#tab2EchartLeft").css("display", "block");
        // 初始化echarts	                    
        var myChart = echarts.init(document.getElementById('tab2CreativeEchartLeft'));
        //如果柱形图的x轴数据或条形图的y轴数据为一条不显示滚动条
        if(option_bar.xAxis.data.length <= 1){
        	delete option_bar.dataZoom;
        }
        if(option_tiao.yAxis.data.length <= 1){
        	delete option_tiao.dataZoom;
        }
        // 默认柱状图
        myChart.setOption(option_bar,true);
        // 随着分辨率缩放
        echartsMap.set("tab2-1",myChart);
        //各种图形按钮切换事件绑定
        $("*[name='button']").click(function() {
            if (this.id == 'tab2_zhuzhuang' || $(this).prev().attr("id") == 'zhuzhuang') {
                myChart.setOption(option_bar, true);
                compareJson["tab2_1"]["chartType"] = "colnum";
                graphMap.set("tab2","bar");
            } else if (this.id == 'tab2_tiaoxing' || $(this).prev().attr("id") == 'tiaoxing') {
                myChart.setOption(option_tiao, true);
                compareJson["tab2_1"]["chartType"] = "bar";
                graphMap.set("tab2","chart");
            }
        });
    }
}

function showDiagnosisCountEchartsTab2Right(echartsValue) {
	// 图例名称可重复
	var legend = echartsValue.legend;
	legend.formatter = function(p) {
		if(p.indexOf(",") > -1) {
			return p.substring(0,p.indexOf(","));
		}else {
			return p;
		}
	};
	//饼状图
	var option_pie = echartObject.getOptionPie(echartsValue).tab2;
    // 显示echarts div
    $("#tab2EchartLeft").css("display", "block");
    echartObject.pieNoData(echartsValue,objTab2,option_pie);
    // 初始化echarts	                    
    var myChart2 = echarts.init(document.getElementById("tab2CreativeEchartRight"));
    // 默认柱状图
    myChart2.setOption(option_pie);
    // 随着分辨率缩放
    echartsMap.set("tab2-2",myChart2);
}