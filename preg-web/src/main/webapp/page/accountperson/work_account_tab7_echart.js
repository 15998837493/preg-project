
var echartTab7Map = new Map();
var tab7ColumnMap = new Map();
var tab7BarMap = new Map();

/**
 * 工作量对比--诊断频次echart
 * @param echartsValue
 * @param index
 */
function echartTab7(echartsValue, index) {
	var title = $("#tab7 div[group='group_index_"+index+"'] input[name='echartTitle']").val();
	echartsValue.title.textStyle = {color: "#aeaeae"};
	if(!_.isEmpty(title)){
		echartsValue.title.text = title;
		echartsValue.title.textStyle = {color: "#000000"};
	}
    //柱状图
    var option_column = echartObject.getOptionBar(echartsValue).tab7;
    //条形图
    var option_bar = echartObject.getOptionChart(echartsValue).tab7;
    tab7ColumnMap.set("index_"+index, option_column);
    tab7BarMap.set("index_"+index, option_bar);
    // 初始化echarts
    var myChart = echarts.init($('#echartsCompareFrequency_'+index)[0]);
    // 默认柱状图
    if(!common.isEmpty(resultFrequencyMap.get("index_"+index))){
    	var chartType = resultFrequencyMap.get("index_"+index).chartType;
    	if("colnum" == chartType){
    		myChart.setOption(option_column, true);
    	} else{
    		myChart.setOption(option_bar, true);
    	}
    } else{
    	myChart.setOption(option_column, true);
    }
    // 随着分辨率缩放
    echartsMap.set("tab7",myChart);
    // 记录
    echartTab7Map.set("index_"+index, myChart);
    //各种图形按钮切换事件绑定
    $("#tab7_colnum_"+index).click(function() {
    	var charIndex = this.id.split("_")[2];
    	var chart = echartTab7Map.get("index_"+charIndex);
    	chart.setOption(tab7ColumnMap.get("index_"+charIndex), true);
    	resultFrequencyMap.get("index_"+charIndex).chartType = "colnum";
        graphMap.set("tab7","bar");
    });
    $("#tab7_bar_"+index).click(function() {
    	var charIndex = this.id.split("_")[2];
    	var chart = echartTab7Map.get("index_"+charIndex);
    	chart.setOption(tab7BarMap.get("index_"+charIndex), true);
    	resultFrequencyMap.get("index_"+charIndex).chartType = "bar";
        graphMap.set("tab7","chart");
    });
}