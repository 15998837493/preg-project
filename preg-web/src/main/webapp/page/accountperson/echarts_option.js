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
        "tab4": {
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
            tooltip: {},
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
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c}%"
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
        "tab4": {
            title: echartsValue.title,
            tooltip: {},
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
            tooltip: {},
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
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c}%"
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
        "tab3_1": {
            title: echartsValue.title,
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: echartsValue.legend,
            calculable: true,
            color: echartsValue.color,
            series: [{
                name: '初诊孕期分布',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: echartsValue.pieData
            }]
        },
        "tab3_2": {
            title: echartsValue.title,
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: echartsValue.legend,
            calculable: true,
            color: echartsValue.color,
            series: [{
                name: '初诊孕期分布',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: echartsValue.pieData
            }]
        }
    };
};