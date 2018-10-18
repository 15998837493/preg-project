<%@ page language="java" pageEncoding="UTF-8"%>
<!-- 
同一组数据，不同图形之间的切换 demo
1、柱形图
2、折线图
3、饼状图
4、条形图
5、面积图
6、散点图
 -->
<style>
ul {
	position: absolute;
	left:700px;
	top:50px;
	list-style:none;
}
ul img[name='button'] {
	cursor: pointer;
	vertical-align:middle;
}
ul a {
	color:#5598CF;
	vertical-align:middle;
	text-decoration:none;
	cursor: pointer;
}
ul a:hover {
	text-decoration:underline;
}
ul li {
	padding:6px;
}
</style>
<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
<!-- echarts 3.x -->
<script charset="UTF-8" src="${common.basepath}/common/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//折线图
	var option_line = {
	        title: {
	            text: 'ECharts 简单折线图'
	        },
	        tooltip : {
	            trigger: 'axis'
	        },
	        legend: {
	        	top:30,
	            data:['孙悟空','猪八戒','唐三藏','沙和尚']
	        },
	        xAxis: [{
	        	 type : 'category',
	            data: ["1月","2月","3月","4月","5月"]
	        }],
	        yAxis: [{
	        	type: 'value',
	        	name: '粉丝数',
	            splitLine: {
	                show: true
	            },
	            nameGap: 10,
	            splitNumber: 10
	        }],
	        series: [{
	            name: '孙悟空',
	            smooth: false,
	            type: 'line',
	            data: [30, 10, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒',
	            smooth: false,
	            type: 'line',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏',
	            smooth: false,
	            type: 'line',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚',
	            smooth: false,
	            type: 'line',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        }]
	    };
	//柱状图
	var option_bar = {
	        title: {
	            text: 'ECharts 简单柱状图'
	        },
	        tooltip: {},
	        legend: {
	        	top:30,
	            data:['孙悟空','猪八戒','唐三藏','沙和尚','孙悟空2','猪八戒2','唐三藏2','沙和尚2','孙悟空3','猪八戒3','唐三藏3','沙和尚3','孙悟空4','猪八戒4','唐三藏4','沙和尚4']
	        },
	        dataZoom: [{
	            type: 'slider',
	            show: true,
	            xAxisIndex: [0],
	            startValue: 0,
	            endValue: 0
	        }],
	        xAxis: {
	        	 type : 'category',
		         data: ["1月","2月","3月","4月","5月"]
	        },
	        yAxis: [{
	        	type: 'value',
	        	name: '粉丝数',
	            splitLine: {
	                show: true
	            },
	            nameGap: 10,
	            splitNumber: 10
	        }],
	        grid: {
	        	y:130
	        },
	        series: [{
	            name: '孙悟空',
	            smooth: false,
	            type: 'bar',
	            data: [30, 60, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒',
	            smooth: false,
	            type: 'bar',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏',
	            smooth: false,
	            type: 'bar',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚',
	            smooth: false,
	            type: 'bar',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        },{
	            name: '孙悟空2',
	            smooth: false,
	            type: 'bar',
	            data: [30, 60, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒2',
	            smooth: false,
	            type: 'bar',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏2',
	            smooth: false,
	            type: 'bar',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚2',
	            smooth: false,
	            type: 'bar',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        },{
	            name: '孙悟空3',
	            smooth: false,
	            type: 'bar',
	            data: [30, 60, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒3',
	            smooth: false,
	            type: 'bar',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏3',
	            smooth: false,
	            type: 'bar',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚3',
	            smooth: false,
	            type: 'bar',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        },{
	            name: '孙悟空4',
	            smooth: false,
	            type: 'bar',
	            data: [30, 60, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒4',
	            smooth: false,
	            type: 'bar',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏4',
	            smooth: false,
	            type: 'bar',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚4',
	            smooth: false,
	            type: 'bar',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        }]
	    };	
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
		        x : 'left',
	            data:['孙悟空','猪八戒','唐三藏','沙和尚']
		    },
		    calculable : true,
		    series : [
		        {
		            name:'粉丝数',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[
		                {value:50, name:'孙悟空'},
		                {value:27, name:'猪八戒'},
		                {value:3, name:'唐三藏'},
		                {value:20, name:'沙和尚'},
		            ]
		        }
		    ]
		};
	//散点图
	option_scatter = {
		    title : {
		        text: 'ECharts 简单散点图',
		        subtext: '抽样调查来自: Heinz  2003'
		    },
		    tooltip : {
		        trigger: 'axis',
		        showDelay : 0,
		        formatter : "{a} <br/>{b} : {c}",  
		        axisPointer:{
		            show: true,
		            type : 'cross',
		            lineStyle: {
		                type : 'dashed',
		                width : 1
		            }
		        }
		    },
		    legend: {
	        	top:30,
	            data:['孙悟空','猪八戒','唐三藏','沙和尚']
		    },
		    xAxis : [{
	        	 type : 'category',
		         data: ["1月","2月","3月","4月","5月"]
		    }
		    ],
		    yAxis : [
		        {
		        	type: 'value',
		        	name: '粉丝数',
		            splitLine: {
		                show: true
		            },
		            nameGap: 10,
		            splitNumber: 10 
		        }
		    ],
		    series : [{
	            name: '孙悟空',
	            smooth: false,
	            type: 'scatter',
	            data: [30, 10, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒',
	            smooth: false,
	            type: 'scatter',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏',
	            smooth: false,
	            type: 'scatter',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚',
	            smooth: false,
	            type: 'scatter',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        }
		    ]
		};
	//条形图
	option_tiao = {
		    title : {
		        text: 'ECharts 简单条形图',
		        subtext: '数据来自网络'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
	        	top:30,
	            data:['孙悟空','猪八戒','唐三藏','沙和尚']
		    },
		    calculable : true,
		    xAxis : [
				        {
				        	type: 'value',
				        	name: '粉丝数',
				            splitLine: {
				                show: true
				            },
				            nameGap: 10,
				            splitNumber: 10 
				        }
		    ],
		    yAxis : [
		        {
		        	type : 'category',
			        data: ["1月","2月","3月","4月","5月"]
		        }
		    ],
		    series : [
		        {
	            name: '孙悟空',
	            smooth: false,
	            type: 'bar',
	            data: [30, 10, 20, 40, 50],
	            itemStyle: {
	                normal: {
	                    color: 'red'
	                }
	            }
	        },{
	            name: '猪八戒',
	            smooth: false,
	            type: 'bar',
	            data: [5, 7, 33, 3, 47],
	            itemStyle: {
	                normal: {
	                    color: 'pink'
	                }
	            }
	        },{
	            name: '唐三藏',
	            smooth: false,
	            type: 'bar',
	            data: [10, 1, 10, 2, 5],
	            itemStyle: {
	                normal: {
	                    color: 'gray'
	                }
	            }
	        },{
	            name: '沙和尚',
	            smooth: false,
	            type: 'bar',
	            data: [25, 17, 29, 24, 36],
	            itemStyle: {
	                normal: {
	                    color: 'green'
	                }
	            }
	        }
		    ]
		};
	//面积图
	option_mianji = {
		    title : {
		        text: 'ECharts 简单面积图',
		        subtext: '纯属虚构'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
	        	top:30,
	            data:['孙悟空','猪八戒','唐三藏','沙和尚']
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
			         data: ["1月","2月","3月","4月","5月"]
		        }
		    ],
		    yAxis : [
		        {
		        	type: 'value',
		        	name: '粉丝数',
		            splitLine: {
		                show: true
		            },
		            nameGap: 10,
		            splitNumber: 10
		        }
		    ],
		    series : [
/* 		        {
		            name:'粉丝数',
		            type:'line',
		            smooth:true,
		            itemStyle: {normal: {areaStyle: {type: 'default'}}},
		            data:[40, 19, 15, 18, 9]
		        } */
		        {
		            name: '孙悟空',
		            smooth: false,
		            type: 'line',
		            data: [30, 10, 20, 40, 50],
		            itemStyle: {
		                normal: {
		                    color: 'red',
		                    areaStyle: {type: 'default'}
		                }
		            }
		        },{
		            name: '猪八戒',
		            smooth: false,
		            type: 'line',
		            data: [5, 7, 33, 3, 47],
		            itemStyle: {
		                normal: {
		                    color: 'pink',
		                    areaStyle: {type: 'default'}
		                }
		            }
		        },{
		            name: '唐三藏',
		            smooth: false,
		            type: 'line',
		            data: [10, 1, 10, 2, 5],
		            itemStyle: {
		                normal: {
		                    color: 'gray',
		                    areaStyle: {type: 'default'}
		                }
		            }
		        },{
		            name: '沙和尚',
		            smooth: false,
		            type: 'line',
		            data: [25, 17, 29, 24, 36],
		            itemStyle: {
		                normal: {
		                    color: 'green',
		                    areaStyle: {type: 'default'}
		                }
		            }
		        }
		    ]
		};
	//初始化echarts	                    
	var myChart = echarts.init(document.getElementById('cjj'));
	//默认折线图
	myChart.setOption(option_line, true);
	//各种图形按钮切换事件绑定
	$("*[name='button']").click(function() {
		if(this.id=='zhexian' || $(this).prev().attr("id")=='zhexian') {
			myChart.setOption(option_line,true);//为true是设置清空上一次图形
		}else if(this.id=='zhuzhuang' || $(this).prev().attr("id")=='zhuzhuang') {
			myChart.setOption(option_bar,true);
		}else if(this.id=='bingzhuang' || $(this).prev().attr("id")=='bingzhuang') {
			myChart.setOption(option_pie,true);
		}else if(this.id=='sandian' || $(this).prev().attr("id")=='sandian') {
			myChart.setOption(option_scatter,true);
		}else if(this.id=='tiaoxing' || $(this).prev().attr("id")=='tiaoxing') {
			myChart.setOption(option_tiao,true);
		}else if(this.id=='mianji' || $(this).prev().attr("id")=='mianji') {
			myChart.setOption(option_mianji,true);
		}
	});
	// 导出图片点击事件
    $("#button").click(function() {
    	var i = myChart.getDataURL({
    		type:'png',
    		backgroundColor:'white'
    		// 导出的图片分辨率比例，默认为 1。
    		//pixelRatio: number,
    	});
    	$(this).attr("href",i);
    	$(this).attr("download","test.png");
    });
	// 局部修改标题
    $("#updateTitle").click(function() {
    	//默认折线图
    	option_line.title.text = '修改成功';
    	myChart.setOption(option_line,true);
    });
});
</script>
<html>
<ul>
<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhexian.png" id="zhexian" name="button"/> <a name="button">折线图</a></li>
<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhuzhuang.png" id="zhuzhuang" name="button"/> <a name="button">柱状图</a></li>
<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/bingzhuang.png" id="bingzhuang" name="button"/> <a name="button">饼状图</a></li>
<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/sandian.png" id="sandian" name="button"/> <a name="button">散点图</a></li>
<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/tiaoxing.png" id="tiaoxing" name="button"/> <a name="button">条形图</a></li>
<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/mianji.png" id="mianji" name="button"/> <a name="button">面积图</a></li>
</ul>
<div id="cjj" style="width: 600px;height:600px;"></div>
<a href="#" id="button">导出echarts图片</a> 
<a href="#" id="updateTitle">修改标题</a> 
</html>