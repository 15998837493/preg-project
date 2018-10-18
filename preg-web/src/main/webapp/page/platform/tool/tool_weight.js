
var myEchart;// echarts实例
var minData = null;// 下限值
var norData = null;// 正常值
var maxData = null;// 上限值
var diagnosis = opener.diagnosisJson;// 接诊信息
var pregArchive = opener.preArchiveJson;// 孕期建档信息

function getWeightOption(){
	var yMin = 0;
	var yMax = 20;
	var splitNumber = 20;
	var custWeight = $("#custWeight").val();// 孕前体重
	var diagnosisCustWeight = $("#diagnosisCustWeight").val() || $("#diagnosisCustWeight", window.opener.document).val();// 当前体重
	var diagnosisCustWeightPreweek = $("#diagnosisCustWeightPreweek").val();// 一周前体重
	var totalWeight = common.accSubtr(diagnosisCustWeight, custWeight);// 整体体重变化
	var weekWeight = null;// 一周体重变化
	if(!common.isEmpty(diagnosisCustWeightPreweek)){
		weekWeight = common.accSubtr(diagnosisCustWeight, diagnosisCustWeightPreweek);
	}
	var pregWeek = $("#diagnosisGestationalWeeks").val().split("\+")[0];// 孕周
	var height = $("#diagnosisCustHeight").val();
	$("#bmi").val(common.accDiv(custWeight*10000, common.accMul(height, height)).toFixed(1));
	
	if(totalWeight < -5){
		totalWeight = -5;
	}
	if(totalWeight < 0){
		yMin = totalWeight;
		yMax = 18;
		splitNumber = 18 - totalWeight;
	}
	
	getInfoByBmi(totalWeight, weekWeight, pregWeek);// 数据准备
	
	return weightOption = {
		grid : {
			x : 50,
			y : 35,
			x2: 30,
			y2 : 30
		},
	    legend: {
	        data:['增长下限', '正常值', '增长上限']
	    },
	    calculable : false,// 是否可拖拽
	    xAxis : [
	        {
	            type : 'value',
	            splitNumber : 20
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '体重增长（kg）',
	            splitNumber : splitNumber,
	            min : yMin,
	            max : yMax
	        }
	    ],
	    series : [
			{
				type : 'scatter',
				symbolSize: function (data) {
			        return 5;
			    },
				data : [[pregWeek, totalWeight]],
				itemStyle: {
				    normal: {
				        color : '#ff0000'
				    }
				}
			},
			{
				name:'增长下限',
				smooth:false,
	            type:'line',
	            data : minData
	       	},
	       	{
	        	name:'正常值',
	       		smooth:false,
	           	type:'line',
	           	data : norData
	       	},
	      	{
	       		name:'增长上限',
				smooth:false,
	            type:'line',
	            data : maxData,
	            itemStyle : {
	            	normal : {
	            		color : '#f6b4f4'
	            	}
	            }
	        }
	    ]
	};
}

var formOption = {
	rules: {
		lmp:{required: true},
		diagnosisCustHeight:{required: true},
		custWeight:{required: true},
		diagnosisCustWeight:{required: true}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element); 
	},
	success: $.noop,
	submitHandler: function() {
		
	}
};

var valid;
$().ready(function(){
	common.requiredHint("custData",formOption);
	valid = $("#custData").validate(formOption);
	
	$("input[input-required]").bindNumberOrFloat();
	
	$("#custData").jsonToForm(diagnosis);
	$("#lmp").val(pregArchive.lmp);
	$("#custWeight").val(pregArchive.weight);
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	common.initDate(null,null,2,"#lmp",nowDate);
	$("#lmp").datetimepicker("setEndDate",nowDate);
	// 体重计算
	getWeightChart();
});

//******************************************【自定义工具方法】****************************************

/**
 * echarts实例化
 */
function getWeightChart(){
	if(valid.form()){
		myEchart = echarts.init(document.getElementById("weightEchart"));
		myEchart.setOption(getWeightOption());
	}
}

/**
 * 根据BMI计算折线范围 以及 整体体重增长范围
 * @param totalWeight
 * @param weekWeight
 * @param pregWeek
 */
function getInfoByBmi(totalWeight, weekWeight, pregWeek){
	$("#totalWeight_td").html(totalWeight + "（kg）");
	if(!common.isEmpty(weekWeight)){
		$("#weekWeight_td").html(weekWeight + "（kg）");
	} else{
		$("#weekWeight_td").html("");
	}
	
	var bmi = $("#bmi").val();
	if(bmi < 18.5){
		minData = [[0, 0], [12, 1], [40, 12.5]];
		norData = [[0, 0], [12, 1.5], [40, 16]];
		maxData = [[0, 0], [12, 2], [40, 18]];
		$("#totalRange_td").html("12.5~18（kg）");
		// 设置整体体重增长速度 和 一周体重增长速度
		getCurrentNormalWeight(totalWeight, weekWeight, pregWeek, [1, 12.5], [2, 18], [0.44, 0.58]);
	} else if(bmi >= 18.5 && bmi < 25){
		minData = [[0, 0], [12, 0.5], [40, 11.5]];
		norData = [[0, 0], [12, 1.5], [40, 12.5]];
		maxData = [[0, 0], [12, 2], [40, 16]];
		$("#totalRange_td").html("11.5~16（kg）");
		// 设置整体体重增长速度 和 一周体重增长速度
		getCurrentNormalWeight(totalWeight, weekWeight, pregWeek, [0.5, 11.5], [2, 16], [0.35, 0.50]);
	} else if(bmi >= 25 && bmi < 30){
		minData = [[0, 0], [12, 0.5], [40, 7]];
		norData = [[0, 0], [12, 1], [40, 8]];
		maxData = [[0, 0], [12, 1.5], [40, 11.5]];
		$("#totalRange_td").html("7~11.5（kg）");
		// 设置整体体重增长速度 和 一周体重增长速度
		getCurrentNormalWeight(totalWeight, weekWeight, pregWeek, [0.5, 7], [1.5, 11.5], [0.23, 0.33]);
	} else {
		minData = [[0, 0], [12, 0], [40, 5]];
		norData = [[0, 0], [12, 1], [40, 7.5]];
		maxData = [[0, 0], [12, 1.5], [40, 9]];
		$("#totalRange_td").html("5~9（kg）");
		// 设置整体体重增长速度 和 一周体重增长速度
		getCurrentNormalWeight(totalWeight, weekWeight, pregWeek, [0, 5], [1.5, 9], [0.17, 0.27]);
	}
}

/**
 * 设置整体体重增长速度 和 一周体重增长速度
 * @param totalWeight
 * @param pregWeek
 * @param min
 * @param max
 */
function getCurrentNormalWeight(totalWeight, weekWeight, pregWeek, min, max, weekSpeed){
	var result = [];
	if(pregWeek < 13){
		result.push(common.accMul(common.accDiv(min[0], 12), pregWeek));
		result.push(common.accMul(common.accDiv(max[0], 12), pregWeek));
	} else{
		result.push(common.accAdd(common.accMul(common.accDiv(common.accSubtr(min[1], min[0]), 28), common.accSubtr(pregWeek, 12)), min[0]));
		result.push(common.accAdd(common.accMul(common.accDiv(common.accSubtr(max[1], max[0]), 28), common.accSubtr(pregWeek, 12)), max[0]));
	}
	if(totalWeight < result[0]){
		$("#totalSpeed_td").html("过缓");
	} else if(totalWeight > result[1]){
		$("#totalSpeed_td").html("过快");
	} else{
		$("#totalSpeed_td").html("正常");
	}
	
	if(common.isEmpty(weekWeight)){
		$("#weekSpeed_td").html("");
	} else{
		if(weekWeight < weekSpeed[0]){
			$("#weekSpeed_td").html("过缓");
		} else if(weekWeight > weekSpeed[1]){
			$("#weekSpeed_td").html("过快");
		} else{
			$("#weekSpeed_td").html("正常");
		}
	}
	$("#weekRange_td").html(weekSpeed[0] + "~" + weekSpeed[1] + "（kg）");
}

/**
 * 根据末次月经计算孕周
 * @param lmpDate
 */
function getCustLmp(lmpDate){
	var lmp = "";
	if(!common.isEmpty(lmpDate)){
		var time = new Date().getTime() - new Date(lmpDate).getTime();
		var pregnantDays = parseInt(time / (1000 * 60 * 60 * 24));
		var pregnantWeeks = pregnantDays / 7;
        var plusDays = pregnantDays % 7;
        lmp = Math.floor(pregnantWeeks) + "+" + plusDays;
	}
	$("#diagnosisGestationalWeeks").val(lmp);
}