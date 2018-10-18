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
/*function showDiagnosisCountEchartsTab2Left(echartsValue) {
    if (!common.isEmpty(echartsValue.legend.data) && echartsValue.legend.data.length > 0) {
        //柱状图
        var option_bar = {
            title:echartsValue.title,
            tooltip: {},
            legend:echartsValue.legend,
            grid: echartsValue.gridBar,
            xAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            yAxis: [{
                type: 'value',
                splitLine: {
                    show: true
                },
                nameGap: 10,
                splitNumber: 10
            }],
            series: echartsValue.echartsData
        };
        //条形图
        option_tiao = {
            title: {
                text: '不同医师门诊量',
                left: 'center'
            },
            tooltip: {},
            legend: {
                bottom: 5,
                data: echartsValue.name
            },
            grid: {
                x: 150,
                x2: 60
            },
            xAxis: [{
                type: 'value',
                splitLine: {
                    show: true
                },
                nameGap: 10,
                splitNumber: 10
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData
        };
        // 显示echarts div
        $("#tab2EchartLeft").css("display", "block");
        // 初始化echarts	                    
        var myChart = echarts.init(document.getElementById('tab2CreativeEchartLeft'));
        // 默认柱状图
        myChart.setOption(option_bar);
        // 随着分辨率缩放
        echartsArray.push(myChart);
        //各种图形按钮切换事件绑定
        $("*[name='button']").click(function() {
            if (this.id == 'zhuzhuang' || $(this).prev().attr("id") == 'zhuzhuang') {
                myChart.setOption(option_bar, true);
                compareJson["tab2"]["chartType"] = "colnum";
            } else if (this.id == 'tiaoxing' || $(this).prev().attr("id") == 'tiaoxing') {
                myChart.setOption(option_tiao, true);
                compareJson["tab2"]["chartType"] = "bar";
            }
        });
    }
}

function showDiagnosisCountEchartsTab2Right(echartsValue) {
    	//饼状图
    	option_pie = {
    		    title : {
    		        text: 'ECharts 简单饼状图',
    		        subtext: '纯属虚构',
    		        x:'center'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        formatter: "{a} <br/>{b} : {c} ({d}%)"
    		    },
    		    legend: {
    		        orient : 'vertical',
    		        x : 'right',
    	            data:echartsValue.legend.data
    		    },
    		    calculable : true,
    		    series : [
    		        {
    		            name:'粉丝数',
    		            type:'pie',
    		            radius : '55%',
    		            center: ['50%', '60%'],
    		            data:echartsValue.pieData
    		        }
    		    ]
    		};
        // 显示echarts div
        $("#tab2EchartLeft").css("display", "block");
        // 初始化echarts	                    
        var myChart2 = echarts.init(document.getElementById("tab2CreativeEchartRight"));
        // 默认柱状图
        myChart2.setOption(option_pie);
        // 随着分辨率缩放
        echartsArray.push(myChart2);
}*/