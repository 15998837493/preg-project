/**
 * 本js为echarts统一配置文件
 */

var echartObject = {};

var echartsMap = new Map();// 存放echarts实例
var valueMap = new Map();// 存放echarts绘制图表所需的动态数据
var graphMap = new Map();// 存放图表切换的状态

/**
 * 柱状图
 */
echartObject.getOptionBar = function(echartsValue) {
    return options = {
        "tab1": {
            title: echartsValue.title,
            tooltip: {},
            legend: echartsValue.legend,
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
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        },
        "tab2": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridBar,
            dataZoom: [{
                type: 'slider',
                show: true,
                xAxisIndex: [0],
                startValue: 0,
                endValue: 2
            }],
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
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            series: echartsValue.echartsData,
            color: echartsValue.color,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                        color: 'black'
                    }
                }
            }
        },
        "tab4": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridBar,
            xAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            yAxis: [{
                type: 'value',
                nameGap: 10,
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        },
        "tab5": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridBar,
            xAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            yAxis: [{
                type: 'value',
                nameGap: 10,
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        },
        "tab6": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value + "%";
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value + "%";
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridBar,
            xAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            yAxis: [{
                type: 'value',
                nameGap: 10,
                splitNumber: 10,
                axisLabel: {
                    formatter: '{value} %'
                },
                minInterval: echartsValue.minInterval
            }],
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                        color: 'black'
                    },
                    formatter: function(params) {
                        return params.value + "%";
                    }
                }
            },
            color: echartsValue.color
        },
        "tab7": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridBar,
            dataZoom: echartsValue.xDataZoom,
            xAxis: {
                type: 'category',
                data: echartsValue.xData,
                axisLabel: {
                    margin: 12
                }
            },
            yAxis: [{
                type: 'value',
                splitLine: {
                    show: true
                },
                nameGap: 10,
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'top',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        }
    };
};

/**
 * 条形图
 */
echartObject.getOptionChart = function(echartsValue) {
    return options = {
        "tab1": {
            title: echartsValue.title,
            tooltip: {},
            legend: echartsValue.legend,
            grid: echartsValue.gridLine,
            xAxis: [{
                type: 'value',
                splitLine: {
                    show: true
                },
                nameGap: 10,
                minInterval: echartsValue.minInterval
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        },
        "tab2": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridLine,
            dataZoom: [{
                type: 'slider',
                show: true,
                yAxisIndex: [0],
                startValue: 0,
                endValue: 2
            }],
            xAxis: [{
                type: 'value',
                splitLine: {
                    show: true
                },
                nameGap: 10,
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData,
            color: echartsValue.color,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: 'black'
                    }
                }
            }
        },
        "tab4": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridLine,
            xAxis: [{
                type: 'value',
                nameGap: 10,
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        },
        "tab5": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value;
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value;
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridLine,
            xAxis: [{
                type: 'value',
                nameGap: 10,
                splitNumber: 10,
                minInterval: echartsValue.minInterval
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        },
        "tab6": {
            title: echartsValue.title,
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.seriesName.indexOf(",") > -1) {
                        return p.seriesName.substring(0, p.seriesName.indexOf(",")) + "<br/>" + p.name + "：" + p.value + "%";
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value + "%";
                    }
                }
            },
            legend: echartsValue.legend,
            grid: echartsValue.gridLine,
            xAxis: [{
                type: 'value',
                nameGap: 10,
                splitNumber: 10,
                axisLabel: {
                    formatter: '{value} %'
                },
                minInterval: echartsValue.minInterval
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: 'black'
                    },
                    formatter: function(params) {
                        return params.value + "%";
                    }
                }
            },
            color: echartsValue.color
        },
        "tab7": {
            title: echartsValue.title,
            tooltip: {},
            legend: echartsValue.legend,
            grid: echartsValue.gridLine,
            xAxis: [{
                type: 'value',
                splitLine: {
                    show: true
                },
                nameGap: 10,
                minInterval: echartsValue.minInterval
            }],
            yAxis: {
                type: 'category',
                data: echartsValue.xData
            },
            series: echartsValue.echartsData,
            label: {
                normal: {
                    show: true,
                    position: 'right',
                    textStyle: {
                        color: 'black'
                    }
                }
            },
            color: echartsValue.color
        }
    };
};

/**
 * 饼状图
 */
echartObject.getOptionPie = function(echartsValue) {
    return options = {
        "tab2": {
            title: {
                text: '患者分布',
                top: 18,
                x: 'center'
            },
            tooltip: {
                show: true,
                formatter: function(p) {
                    if (p.name.indexOf(",") > -1) {
                        return p.seriesName + "<br/>" + p.name.substring(0, p.name.indexOf(",")) + "：" + p.value + "(" + p.percent + "%)";
                    } else {
                        return p.seriesName + "<br/>" + p.name + "：" + p.value + "(" + p.percent + "%)";
                    }
                }
            },
            legend: echartsValue.legend,
            calculable: true,
            color: echartsValue.color,
            series: [{
                name: '患者分布',
                type: 'pie',
                radius: '40%',
                center: ['50%', '70%'],
                data: echartsValue.pieData,
                label: {
                    normal: {
                        formatter: function(p) {
                            if (p.name.indexOf(",") > -1) {
                                return p.name.substring(0, p.name.indexOf(","));
                            } else {
                                return p.name;
                            }
                        }
                    }
                }
            }]
        },
        "tab3": {
            title: echartsValue.title,
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: echartsValue.legend,
            calculable: true,
            color: echartsValue.color,
            series: [{
                name: '初诊孕期患者分布',
                type: 'pie',
                radius: '60%',
                center: ['50%', '65%'],
                data: echartsValue.pieData
            }]
        }
    };
};

/**
 * 给饼形图无数据时特殊
 * @param pieData
 * @param obj
 * @param option
 */
echartObject.pieNoData = function(echartsValue, obj, option) {
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
};