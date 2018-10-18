var minData = null; // 下限值
var norData = null; // 正常值
var maxData = null; // 上限值
var pregArchive = preArchiveJson; // 孕期建档信息

/**
 * 查询用户是否改过结论值(true为没改过，false为改过)
 */
function queryResult(type) {
	var flag = false;
    var url = URL.get("Platform.QUERY_DIAGNOSISES");
    var params = "diagnosisId=" + $("#diagnosisId").val();
    ajax.post(url, params, dataType.json, function(data) {
    	var result = data.value;
    	if(type=='yunqi' && result.length > 0) {
        	if(result[result.length-1].diagnosisRiseYunqi == null || result[result.length-1].diagnosisRiseYunqi == "") {
        		flag = true;
        	}
    	}else if(type=='riseweek' && result.length > 0) {
        	if(result[result.length-1].diagnosisRiseWeek == null || result[result.length-1].diagnosisRiseWeek == "") {
        		flag = true;
        	}
    	}
    },false, false);
	return flag;
}

/** 转诊医生列表*/
var referralDoctors;
var doctorOptions = {
		rules: {
			diagnosisReferralDoctor: {
				required:true
			},
		},
		//设置错误信息显示到指定位置
		errorPlacement: function(error, element) {
			element = element.parent();
			common.showmassage(error, element);
		},
		success: $.noop,
		submitHandler: function(form) {
			$(form).ajaxPost(dataType.json,function(data){
				if(!common.isEmpty(data.value)){
					if(!common.isEmpty(data.value.diagnosisReferralDoctorName)) {
						doctorName=data.value.diagnosisReferralDoctorName;
						if(!common.isEmpty(data.value.diagnosisOrg)) {
							org=data.value.diagnosisOrg;	
						}
						if(!common.isEmpty(data.value.diagnosisReferralDoctor)) {
							diagnosisReferralDoctor=data.value.diagnosisReferralDoctor;	
						}
						var templat="科室来源："+org+" &nbsp;&nbsp;&nbsp;&nbsp;转诊医生："+doctorName;
						$("#diagnonsisInfo").html(templat);	
						$("#diagnosisInfoModal").modal("hide");
					}					
				};
			},false,false);
		}
	};
	
$.validator.addMethod("babyWeekNumber", function(value,element) {
	if(element.value.trim()==""){
		return true;
	}
	return element.value <= 40;
}, '胎儿周数最大为40周');
	
$.validator.addMethod("babyDayNumber", function(value,element) {
	if(element.value.trim()==""){
		return true;
	}
	return element.value <= 6;
}, '额外天数最大为6天');

var diagnosisUpdateOption = {
	rules: {
		diagnosisFetusweek: {
			babyWeekNumber:true
		},
		diagnosisFetusday: {
			babyDayNumber:true
		},
		gestationalWeeks:{
			babyWeekNumber:true
		},
		gestationalDays:{
			babyDayNumber:true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		$(form).ajaxPost(dataType.json, null, false, false);
	}
};

/**
 * 羊水和
 */
function fluidSum() {
	var sum = 0;
	if(!common.isEmpty($("#obstetricalAmnioticFluidOne").val()) && !common.isEmpty($("#obstetricalAmnioticFluidTwo").val()) && !common.isEmpty($("#obstetricalAmnioticFluidThree").val()) && !common.isEmpty($("#obstetricalAmnioticFluidFour").val())) {
		sum += parseFloat($("#obstetricalAmnioticFluidOne").val());
		sum += parseFloat($("#obstetricalAmnioticFluidTwo").val());
		sum += parseFloat($("#obstetricalAmnioticFluidThree").val());
		sum += parseFloat($("#obstetricalAmnioticFluidFour").val());
	}
	$("#obstetricalAmnioticFluid").val(sum.toFixed(1));
}

/**
 * 设置整体体重增长速度
 * @param totalWeight
 * @param pregWeek
 * @param min
 * @param max
 */
function getCurrentNormalWeight(totalWeight, pregWeek, min, max, weekSpeed){
	var result = [];
	if(pregWeek < 13){
		result.push(common.accMul(common.accDiv(min[0], 12), pregWeek));
		result.push(common.accMul(common.accDiv(max[0], 12), pregWeek));
	} else{
		result.push(common.accAdd(common.accMul(common.accDiv(common.accSubtr(min[1], min[0]), 28), common.accSubtr(pregWeek, 12)), min[0]));
		result.push(common.accAdd(common.accMul(common.accDiv(common.accSubtr(max[1], max[0]), 28), common.accSubtr(pregWeek, 12)), max[0]));
	}
}

/**
 * 根据BMI计算折线范围 以及 整体体重增长范围
 */
function getInfoByBmi(allWeight,weekYun) {
	if(parseFloat(allWeight)>0) {//按需求做的，小于等于0，不做计算
		$("#rise_all_present").html(parseFloat(allWeight).toFixed(1)+"公斤");//保留一位小数
	}
    var bmi = pregArchive.bmi;
    if (bmi < 18.5) {
        minData = [[0, 0], [12, 1], [40, 12.5]];
        norData = [[0, 0], [12, 1.5], [40, 16]];
        maxData = [[0, 0], [12, 2], [40, 18]];
		// 设置整体体重增长速度
        getCurrentNormalWeight(allWeight, weekYun, [1, 12.5], [2, 18]);
    } else if (bmi >= 18.5 && bmi < 25) {
        minData = [[0, 0], [12, 0.5], [40, 11.5]];
        norData = [[0, 0], [12, 1.5], [40, 12.5]];
        maxData = [[0, 0], [12, 2], [40, 16]];
        // 设置整体体重增长速度
		getCurrentNormalWeight(allWeight, weekYun, [0.5, 11.5], [2, 16]);
    } else if (bmi >= 25 && bmi < 30) {
        minData = [[0, 0], [12, 0.5], [40, 7]];
        norData = [[0, 0], [12, 1], [40, 8]];
        maxData = [[0, 0], [12, 1.5], [40, 11.5]];
		// 设置整体体重增长速度
		getCurrentNormalWeight(allWeight, weekYun, [0.5, 7], [1.5, 11.5]);
    } else {
        minData = [[0, 0], [12, 0], [40, 5]];
        norData = [[0, 0], [12, 1], [40, 7.5]];
        maxData = [[0, 0], [12, 1.5], [40, 9]];
		// 设置整体体重增长速度
		getCurrentNormalWeight(allWeight, weekYun, [0, 5], [1.5, 9]);
    };
	// 建议整体孕期体重适宜增长范围
    if(queryResult("yunqi")) {//如果用户没改过结论（数据库没有存值）
    	var preg = pregnancy.getPregnancyWeightByBmi(bmi);
   	 	$("#rise_yunqi_one").val(preg.split("-")[0]);
   	 	$("#rise_yunqi_two").val(preg.split("-")[1]);
    }
    //建议每周体重增长范围
    if(queryResult("riseweek")) {//如果用户没改过结论（数据库没有存值）
    	var week = pregnancy.getWeekWeightByBmi(bmi);
		$("#rise_week_one").val(week.split("-")[0]);
		$("#rise_week_two").val(week.split("-")[1]);
    }
}

/**
 * 体重
 */
function drawWeight(weight, gestationalWeeks, nowWeight, weekYun) {
    // 基于准备好的dom，初始化echarts图表
    var myChart_weight = echarts.init(document.getElementById('weight'));
    var yMin = 0;
    var yMax = 20;
    var splitNumber = 20;
    var custWeight = pregArchive.weight; // 孕前体重
    var diagnosisCustWeight = weight; // 当前体重
    var totalWeight = []; // y轴（体重增长）
    var pregWeek = []; //x轴（孕周）
    var scatterDatamnt = [];
    var min; //（体重增长）最小值
    if(nowWeight=="") {
    	nowWeight = "0";
    }
    var allWeight = parseFloat(nowWeight) - parseFloat(custWeight); //整体体重变化 = 当前体重 - 孕前体重
    for (var x = 0; x < diagnosisCustWeight.length; x++) {
    	if(common.isEmpty(diagnosisCustWeight[x]) || diagnosisCustWeight[x] == 0){
    		totalWeight.push(common.accSubtr(custWeight, custWeight)); // 整体体重变化
    	} else{
    		totalWeight.push(common.accSubtr(diagnosisCustWeight[x], custWeight)); // 整体体重变化
    	}
    }
    min = totalWeight[0];
    for (var x = 0; x < totalWeight.length; x++) {
        if (parseFloat(totalWeight[x]) < parseFloat(min)) {
            min = totalWeight[x];
        }
        if (parseFloat(totalWeight[x]) < -5) {
            totalWeight[x] = -5;
        }
        if (parseFloat(totalWeight[x]) > 20) {
            totalWeight[x] = 20;
        }
    }
    for (var x = 0; x < gestationalWeeks.length; x++) {
        pregWeek.push(gestationalWeeks[x].split("\+")[0]); // 孕周
    }
    /*data : [pregWeek, totalWeight],*/
    //需要传入一个二维数组
    for (var x = 0; x < totalWeight.length; x++) {
        scatterDatamnt.push([pregWeek[x], totalWeight[x]]);
    }
    if(parseInt(min) != min && min < 0) {//如果最小值为小数
    	min = min.split(".")[0]-1;
    }
    if (min < -5) {
        min = -5;
    }
    if (min < 0) {
        yMin = min;
        yMax = 20;
        splitNumber = 20 - min;
    }
    getInfoByBmi(allWeight,weekYun); // 数据准备
    var seriesData = []; //小圆点标识
    for (var x = 0; x < totalWeight.length; x++) {
        seriesData.push({
            xAxis: pregWeek[x],
            yAxis: totalWeight[x]
        });
    }
    //计算  建议目前体重增长至：上一次体重+（本次孕周-上次孕周）✖（每周增长范围）
    var week_now = diagnosisJson.diagnosisGestationalWeeks.substring(0,diagnosisJson.diagnosisGestationalWeeks.indexOf("+"));//本次孕周 注意：不能用参数里面的本次孕周，参数里的如果不给现体重，则没有现孕周，应从缓存中取   
    var url = URL.get("Platform.QUERY_DIAGNOSISES");
    var params = "diagnosisId=" + $("#diagnosisId").val();
    ajax.post(url, params, dataType.json, function(data) {//上一次孕周以及上一次体重，不能用方法里面的参数（曲线图专用），没有现体重就没有值
    	var result = data.value;
        if(result.length > 1 && common.isEmpty(result[result.length-2].diagnosisGestationalWeeks) == false) {//必须得有上一次的孕周
        	var week_last = parseInt(result[result.length-2].diagnosisGestationalWeeks.split("+")[0]);//上一次孕周
        	if(parseInt(week_now)-week_last > 0) {//按需求做的，只有现孕周减去上次孕周大于0才会做出计算
            	if(result[result.length-1].diagnosisRisePresent == null || result[result.length-1].diagnosisRisePresent == "") {
            		var weight_last = parseFloat(result[result.length-2].diagnosisCustWeight);//上一次体重
            	    ajax.post(URL.get("Platform.QUERY_ECHARTS_WEIGHT_RESULT"), "diagnosisId=" + $("#diagnosisId").val(), dataType.json,function(data) {
            	    	//每周体重适宜增长范围，如果数据库有值，就取数据库的值(这里是为了防止数据库有值而计算没有值，那么下面的rise_week_one和two是取不到的)
            	    	if(data.value.diagnosisRiseWeek!=null && data.value.diagnosisRiseWeek!="") {
            	    	    $("#rise_week_one").val(data.value.diagnosisRiseWeek.split("-")[0]);
            	    	    $("#rise_week_two").val(data.value.diagnosisRiseWeek.split("-")[1]);
            	    	 }
            	    },false, false);
                	var param_one = (weight_last + (parseInt(week_now)-week_last)*parseFloat($("#rise_week_one").val())).toFixed(2);
                	var param_two = (weight_last + (parseInt(week_now)-week_last)*parseFloat($("#rise_week_two").val())).toFixed(2);
                	if(isNaN(weight_last)==false) {//上一次体重有值
                		if(isNaN(param_one)||isNaN(param_two)) {
                        	$("#rise_present_one").val("");
                       	 	$("#rise_present_two").val("");
                		}else if(isNaN(param_one)==false&&isNaN(param_two)==false) {
                        	$("#rise_present_one").val(param_one);
                       	 	$("#rise_present_two").val(param_two);	
                		}
                	}
            	}                                
        	}
        }
    },false, false);
    var option_weight = {
        grid: {
            x: 50,
            y: 35,
            x2: 30,
            y2: 30,
            top: 45
        },
        legend: {
        	top:6,
            data: ['增长下限', '正常值', '增长上限']
        },
        // 是否可拖拽
        calculable: false,
        xAxis: [{
            type: 'value',
            splitNumber: 20
        }],
        yAxis: [{
            type: 'value',
            name: '体重增长（kg）',
            nameGap:20,
            splitNumber: splitNumber,
            min: yMin,
            max: yMax
        }],
        series: [{
            type: 'line',
            symbol: 'circle',
            symbolSize: function(data) { //圆圈大小
                return 8;
            },
            zlevel:1,//分层级别，默认为0，为了红心圆不被折线白心圆覆盖，要设置为1
            label: {
            	normal: {
            		show:true,
            		position:'top',
            		fontWeight:'lighter',
            		textStyle:{
            			color:'black',
            			fontSize:16
            		},
            		formatter:  function(p){
               			 return diagnosisCustWeight[p.dataIndex];           				
            		 }
            	}
            },
//            smooth: true,//是否开启线条圆滑
            data: scatterDatamnt,
            itemStyle: {
                normal: {
                    color: '#ff0000'
                }
            },
//            markPoint: {
//                data: seriesData/*,
//                symbolSize:60*/
//            }
        },
        {
            name: '增长下限',
            smooth: false,
            type: 'line',
            data: minData,
            itemStyle: {
                normal: {
                    color: '#FF7F50'
                }
            }
        },
        {
            name: '正常值',
            smooth: false,
            type: 'line',
            data: norData,
            itemStyle: {
                normal: {
                    color: '#87CEFA'
                }
            }
        },
        {
            name: '增长上限',
            smooth: false,
            type: 'line',
            data: maxData,
            itemStyle: {
                normal: {
                    color: '#f6b4f4'
                }
            }
        }]
    };
    // 随着分辨率缩放
    window.onresize = myChart_weight.resize;
    // 为echarts对象加载数据 
    myChart_weight.setOption(option_weight);
}

/**
 * 收缩压
 */
function drawSystolic(date, systolic) {

    // 基于准备好的dom，初始化echarts图表
    var myChart_systolic = echarts.init(document.getElementById('systolic'));

    var seriesData = []; //小圆点标识
    /*    seriesData.push({
        type: 'max',
        name: '最大值'
    });
    seriesData.push({
        type: 'min',
        name: '最小值'
    });*/
    /* 			seriesData.push({type : 'average',name : '平均值'}); */
    for (var x = 0; x < systolic.length; x++) {
        seriesData.push({
            xAxis: date[x],
            yAxis: systolic[x]
        });
    }

    var option_systolic = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['收缩压变化']
        },
        calculable: false,
        toolbox: {
            show: false,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['line', 'bar']
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        dataZoom: [{
            type: 'slider',
            show: true,
            xAxisIndex: [0],
            startValue: 0,
            endValue: 8
        },
        {
            type: 'inside',
            xAxisIndex: [0],
            start: 0,
            end: 30
        }],
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            /* axisLabel:{interval: 0}, */
            data: date,
            splitLine: {
                show: true
            }
        }],
        yAxis: [{
            type: 'value',
            name: '收缩压（mmHg）',
            splitLine: {
                show: true
            },
            nameGap: 10,
            splitNumber: 10
        }],
        series: [{
            name: '收缩压变化',
            type: 'line',
            data: systolic,
            markPoint: {
                data: seriesData
            },
            itemStyle: {
                normal: {
                    color: '#FF0000'
                }
            }
            /* ,
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					} */
        }]
    };
    // 为echarts对象加载数据 
    myChart_systolic.setOption(option_systolic);
}

/**
 * 舒张压
 */
function drawDiastolic(date, diastolic) {

    // 基于准备好的dom，初始化echarts图表
    var myChart_diastolic = echarts.init(document.getElementById('diastolic'));

    var seriesData = []; //小圆点标识
    /*    seriesData.push({
        type: 'max',
        name: '最大值'
    });
    seriesData.push({
        type: 'min',
        name: '最小值'
    });*/
    /* 			seriesData.push({type : 'average',name : '平均值'}); */
    for (var x = 0; x < diastolic.length; x++) {
        seriesData.push({
            xAxis: date[x],
            yAxis: diastolic[x]
        });
    }

    var option_diastolic = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['舒张压变化']
        },
        calculable: false,
        toolbox: {
            show: false,
            feature: {
                mark: {
                    show: true
                },
                dataView: {
                    show: true,
                    readOnly: false
                },
                magicType: {
                    show: true,
                    type: ['line', 'bar']
                },
                restore: {
                    show: true
                },
                saveAsImage: {
                    show: true
                }
            }
        },
        dataZoom: [{
            type: 'slider',
            show: true,
            xAxisIndex: [0],
            startValue: 0,
            endValue: 8
        },
        {
            type: 'inside',
            xAxisIndex: [0],
            start: 0,
            end: 30
        }],
        xAxis: [{
            type: 'category',
            boundaryGap: false,
            /* axisLabel:{interval: 0}, */
            data: date,
            splitLine: {
                show: true
            }
        }],
        yAxis: [{
            type: 'value',
            name: '舒张压（mmHg）',
            splitLine: {
                show: true
            },
            nameGap: 10,
            splitNumber: 10
        }],
        series: [{
            name: '舒张压变化',
            type: 'line',
            data: diastolic,
            markPoint: {
                data: seriesData
            },
            itemStyle: {
                normal: {
                    color: '#FF0000'
                }
            }
            /* ,
					markLine : {
						data : [ {
							type : 'average',
							name : '平均值'
						} ]
					} */
        }]
    };
    // 为echarts对象加载数据 
    myChart_diastolic.setOption(option_diastolic);
}

/**
 * 体重，曲线图展现形式与其他不同，需特殊处理，所以传sign=weight作为体重的标记
 */
function showWeight(textVal) {
    var weight = []; //体重
    var gestationalWeeks = []; //孕周(全部)
    var weekYun = "";//当前接诊ID下的孕周
    var custWeight = pregArchive.weight; // 孕前体重
    var url = URL.get("Platform.ECHARTS_PICTURE_GET_DATA");
    var params = "diagnosisId=" + $("#diagnosisId").val()+"&sign=weight"+"&textVal="+textVal+"&custWeight="+custWeight;
    ajax.post(url, params, dataType.json,
    function(data) {
        for (var x = 0; x < data.value.length; x++) {
            if (data.value[x] != null) {
            	gestationalWeeks.push(data.value[x].diagnosisGestationalWeeks);
                weight.push(parseFloat(data.value[x].diagnosisCustWeight));
                if(data.value[x].diagnosisCustWeight==textVal) {
                	weekYun = data.value[x].diagnosisGestationalWeeks.split("\+")[0];
                }
            }
        }
        drawWeight(weight, gestationalWeeks, textVal, weekYun);
    },false, false);
    ajax.post(URL.get("Platform.QUERY_ECHARTS_WEIGHT_RESULT"), "diagnosisId=" + $("#diagnosisId").val(), dataType.json,function(data) {
    	//如果数据库有值，就会把计算出的结论覆盖    注意：如果数据库没有值，则显示计算出来的，不存在数据库里面
    	if(data.value.diagnosisRiseYunqi!=null && data.value.diagnosisRiseYunqi!="") {
    	   $("#rise_yunqi_one").val(data.value.diagnosisRiseYunqi.split("-")[0]);
    	   $("#rise_yunqi_two").val(data.value.diagnosisRiseYunqi.split("-")[1]);		      		
    	 }
    	 if(data.value.diagnosisRisePresent!=null && data.value.diagnosisRisePresent!="") {
    	   $("#rise_present_one").val(data.value.diagnosisRisePresent.split("-")[0]);
    	   $("#rise_present_two").val(data.value.diagnosisRisePresent.split("-")[1]);	      		
    	 }
    	 if(data.value.diagnosisRiseWeek!=null && data.value.diagnosisRiseWeek!="") {
    	   $("#rise_week_one").val(data.value.diagnosisRiseWeek.split("-")[0]);
    	   $("#rise_week_two").val(data.value.diagnosisRiseWeek.split("-")[1]);
    	 }
    },false, false);
	if(textVal=="") {
	    $("#rise_all_present").html("");//该孕妇当前整体体重增长
	}
 /*   $("#weightShowModal").modal("show");*/
}

/**
 * 收缩压
 */
function showSystolic() {
    var date = []; //日期：年月日
    var systolic = []; //收缩压
    var url = URL.get("Platform.ECHARTS_PICTURE_GET_DATA");
    var params = "diagnosisId=" + $("#diagnosisId").val();
    ajax.post(url, params, dataType.json,
    function(data) {
        for (var x = 0; x < data.value.length; x++) {
            if (data.value[x] != null) date.push(data.value[x].diagnosisDate);
            if (data.value[x] != null) if (data.value[x].diagnosisSystolic == null || data.value[x].diagnosisSystolic.length == 0) {
                systolic.push(0);
            } else {
                systolic.push(parseFloat(data.value[x].diagnosisSystolic));
            }
        }
        drawSystolic(date, systolic);
    },
    false, false);
    $("#systolicShowModal").modal("show");
}

/**
 * 舒张压
 */
function showDiastolic() {
    var date = []; //日期：年月日
    var diastolic = []; //收缩压
    var url = URL.get("Platform.ECHARTS_PICTURE_GET_DATA");
    var params = "diagnosisId=" + $("#diagnosisId").val();
    ajax.post(url, params, dataType.json,
    function(data) {
        for (var x = 0; x < data.value.length; x++) {
            if (data.value[x] != null) date.push(data.value[x].diagnosisDate);
            if (data.value[x] != null) if (data.value[x].diagnosisDiastolic == null || data.value[x].diagnosisDiastolic.length == 0) {
                diastolic.push(0);
            } else {
                diastolic.push(parseFloat(data.value[x].diagnosisDiastolic));
            }
        }
        drawDiastolic(date, diastolic);
    },
    false, false);
    $("#diastolicShowModal").modal("show");
}

//孕妇胎儿信息计算器 week:孕周参数
function calculator(week){
	//fundal:代表宫高   abdominal:代表腹围   foetus:代表胎儿{"胎儿体重":weight,"胎儿股骨长":femurLength,"胎儿双顶径":biparietalDiameter,"胎儿腹围":fetalAbdominal}
	var foetus={};//胎儿信息
	var fundal={};//宫高
	var abdominal={};//腹围
	switch(parseInt(week)){
		case 17: foetus={"weight":"","femurLength":"23","biparietalDiameter":"36","fetalAbdominal":"112"};
		  break;
		case 18: foetus={"weight":"","femurLength":"26","biparietalDiameter":"39","fetalAbdominal":"124"};
		  break;
		case 19: foetus={"weight":"","femurLength":"29","biparietalDiameter":"43","fetalAbdominal":"135"};
		  break;
		case 20: foetus={"weight":"","femurLength":"32","biparietalDiameter":"46","fetalAbdominal":"147"};
				 fundal={"down":"15.3","up":"21.4","standard":"18.6"};
		  break;
		case 21: foetus={"weight":"320","femurLength":"35","biparietalDiameter":"50","fetalAbdominal":"159"};
		 		 fundal={"down":"15.3","up":"21.4","standard":"19"};
		  break;
		case 22: foetus={"weight":"320","femurLength":"37","biparietalDiameter":"53","fetalAbdominal":"170"};
			     fundal={"down":"15.3","up":"21.4","standard":"20.2"};
		  break;
		case 23: foetus={"weight":"365","femurLength":"40","biparietalDiameter":"56","fetalAbdominal":"182"};
				 fundal={"down":"15.3","up":"21.4","standard":"21.1"}; 	
		  break;
		case 24: foetus={"weight":"417","femurLength":"43","biparietalDiameter":"59","fetalAbdominal":"193"};
				 fundal={"down":"22","up":"25.1","standard":"22-24"};
		  break;
		case 25: foetus={"weight":"477","femurLength":"45","biparietalDiameter":"62","fetalAbdominal":"204"};
				 fundal={"down":"22","up":"25.1","standard":"23.4"};
		  break;
		case 26: foetus={"weight":"546","femurLength":"48","biparietalDiameter":"64","fetalAbdominal":"215"};
		         fundal={"down":"22","up":"25.1","standard":"23.9"};
		  break;
		case 27: foetus={"weight":"627","femurLength":"50","biparietalDiameter":"67","fetalAbdominal":"226"};
				 fundal={"down":"22","up":"25.1","standard":"24.8"};
		  break;
		case 28: foetus={"weight":"720","femurLength":"53","biparietalDiameter":"70","fetalAbdominal":"237"};
				 fundal={"down":"22.4","up":"29","standard":"25.6-26"};
		  break;
		case 29: foetus={"weight":"829","femurLength":"55","biparietalDiameter":"72","fetalAbdominal":"248"};
				 fundal={"down":"22.4","up":"29","standard":"26.5"}; 
		  break;
		case 30: foetus={"weight":"955","femurLength":"57","biparietalDiameter":"75","fetalAbdominal":"258"};
		 		 fundal={"down":"22.4","up":"29","standard":"27.8"};
		  break;
		case 31: foetus={"weight":"1100","femurLength":"60","biparietalDiameter":"77","fetalAbdominal":"269"};
			     fundal={"down":"22.4","up":"29","standard":"28.6"};
		  break;
		case 32: foetus={"weight":"1284","femurLength":"62","biparietalDiameter":"80","fetalAbdominal":"279"};
			     fundal={"down":"25.3","up":"32","standard":"29"};
		  break;
		case 33: foetus={"weight":"1499","femurLength":"64","biparietalDiameter":"82","fetalAbdominal":"290"};
				 fundal={"down":"25.3","up":"32","standard":"29.8"};
		  break;
		case 34: foetus={"weight":"1728","femurLength":"66","biparietalDiameter":"85","fetalAbdominal":"300"};
				 fundal={"down":"25.3","up":"32","standard":"30.6"};
		  break;
		case 35: foetus={"weight":"1974","femurLength":"68","biparietalDiameter":"87","fetalAbdominal":"311"};
		         fundal={"down":"25.3","up":"32","standard":"31.1"};
		  break;
		case 36: foetus={"weight":"2224","femurLength":"70","biparietalDiameter":"89","fetalAbdominal":"321"};
				 fundal={"down":"29.8","up":"34.5","standard":"31.6-32"};
		  break;
		case 37: foetus={"weight":"2455","femurLength":"72","biparietalDiameter":"91","fetalAbdominal":"331"};
			     fundal={"down":"29.8","up":"34.5","standard":"31.9"};
		  break;
		case 38: foetus={"weight":"2642","femurLength":"74","biparietalDiameter":"93","fetalAbdominal":"341"};
				 fundal={"down":"29.8","up":"34.5","standard":"32.3"};
		  break;
		case 39: foetus={"weight":"2790","femurLength":"76","biparietalDiameter":"96","fetalAbdominal":"351"};
				 fundal={"down":"29.8","up":"34.5","standard":"32.8"};
		  break;
		case 40: foetus={"weight":"2891","femurLength":"78","biparietalDiameter":"98","fetalAbdominal":"361"};
			     fundal={"down":"29.8","up":"34.5","standard":"33"};
		  break;
		default:
	}
	if(week>=20 && week<24){
		abdominal={"down":"76","up":"89","standard":"82"};
	}else if(week>=24 && week<28){
		abdominal={"down":"80","up":"91","standard":"85"};
	}else if(week>=28 && week<32){
		abdominal={"down":"82","up":"94","standard":"87"};
	}else if(week>=32 && week<36){
		abdominal={"down":"84","up":"95","standard":"89"};
	}else if(week>=36 && week<=40){
		if(week == 40){
			abdominal={"down":"89","up":"100","standard":"94"};
		}else{
			abdominal={"down":"86","up":"98","standard":"92"};
		};
	};

	var fetus={"week":week,"fundal":fundal,"abdominal":abdominal,"foetus":foetus};
	return fetus;
}

//显示宫高腹围胎儿体重
function showResult(week,type){
	if(_.isEmpty(week)){
		layer.msg("孕周为空！");
		return;
	}
	var fetus = calculator(week);
	if(type == 1) {
		//宫高
		if(!_.isEmpty(fetus.fundal) ){
			//宫高标准值（推荐）
			var fundal = "";//（推荐：18.6 cm；上下限： 15.3-21.4 cm）
			if(fetus.fundal.standard.indexOf("-") > 0){
				var strArr  = fetus.fundal.standard.split("-");
				fundal += "（推荐： <font color='red'>"+strArr[0]+"</font>-"+strArr[1]+" CM(推荐)；";
			}else{
				if(fetus.week == "32" || fetus.week == "40"){
					fundal += "（推荐： <font color='red'>"+fetus.fundal.standard+"</font> CM(推荐)；";
				}else{				
					fundal += "（推荐： "+fetus.fundal.standard+" CM(推荐)；";
				};
			};
			//宫高上下限
			fundal += " 上下限："+fetus.fundal.down+"-"+fetus.fundal.up+" CM）";
			$("#fundal_standard").html(fundal);
			$("#obstetricalFundalHeightResult").val(fundal);
		}else {
			$("#fundal_standard").html("");
			$("#obstetricalFundalHeightResult").val("");
		}

		//腹围
		if( !_.isEmpty(fetus.abdominal) ){
			var abdominal = "";
			//腹围标准		
			abdominal += "（推荐： "+fetus.abdominal.standard+" CM(推荐)；";
			//腹围上下限
			abdominal += " 上下限："+fetus.abdominal.down+"-"+fetus.abdominal.up+" CM）";
			$("#abdominal_standard").html(abdominal);
			$("#obstetricalAbdominalPerimeterResult").val(abdominal);
		}else {
			$("#abdominal_standard").html("");
			$("#obstetricalAbdominalPerimeterResult").val("");
		}
	}else if(type == 2) {
		//胎儿信息
		if(!_.isEmpty(fetus.foetus)){
			//胎儿体重
			var weight = "";
			if(!_.isEmpty(fetus.foetus.weight)){
				weight += "（推荐： "+fetus.foetus.weight+" g）";
			};
			$("#foetus_weight").html(weight);
			$("#obstetricalBabyWeightResult").val(weight);
			//胎儿股长
			var femur = "";
			femur += "（推荐： "+fetus.foetus.femurLength+" mm）";
			$("#foetus_femur_length").html(femur);
			$("#obstetricalBabyFemurResult").val(femur);
			//胎儿双顶径 
			var diameter = "";
			diameter += "（推荐： "+fetus.foetus.biparietalDiameter+" mm）";
			$("#foetus_biparietal_diameter").html(diameter);
			$("#obstetricalBabyBdpResult").val(diameter);
			//胎儿腹围
			var abdominal = "";
			abdominal += "（推荐： "+fetus.foetus.fetalAbdominal+" mm）";
			$("#foetus_fetal_abdominal").html(abdominal);
			$("#obstetricalBabyAbdominalPerimeterResult").val(abdominal);
		}else {
			$("#foetus_weight").html("");
			$("#obstetricalBabyWeightResult").val("");
			$("#foetus_femur_length").html("");
			$("#obstetricalBabyFemurResult").val("");
			$("#foetus_biparietal_diameter").html("");
			$("#obstetricalBabyBdpResult").val("");
			$("#foetus_fetal_abdominal").html("");
			$("#obstetricalBabyAbdominalPerimeterResult").val("");
		}
	}
};


/**
 * 初始化接诊信息
 */
function initDaignosisInfo(){
	if(!common.isEmpty(doctorName)){
		var templat="科室来源："+org+" &nbsp;&nbsp;&nbsp;&nbsp;转诊医生："+doctorName;
		$("#diagnonsisInfo").html(templat);	
		$("#editDiagnosisInfo").removeClass("hide");
	};
}

/**
 * 编辑接诊信息modal 
 */
function updateDiagnosisInfo(){
	initSelectDoctor("diagnosisReferralDoctor",diagnosisReferralDoctor);
	updataSelectDoctor();
	$("#diagnosisInfoForm [name='diagnosisId']").val(diagnosisId);
	$("#diagnosisInfoModal").modal("show");
}



/***
 * 初始化转诊医生下拉列表
 */
function initSelectDoctor(id,selectedValue,title){
	if (common.isEmpty(title)) {
		title = "==请选择转诊医生==";
	}
	$("#" + id).html("<option value=''>" + title + "</option>");
	var url = URL.get("System.REFERRAL_DOCTOR_DEPT");
	ajax.post(url, null, dataType.json, function(result){
		if (!common.isEmpty(result.data)) {
			referralDoctors=result.data;
			$(referralDoctors).each(function(index, obj) {
				$("#" + id).append("<option value=" + obj.id + ">" + obj.doctorName + '</option>');
				if(selectedValue == obj.id){
					$("#diagnosisReferralDept").html("<h5>"+obj.doctorDepartmentName+"</h5>");
					$("#diagnosisInfoForm [name='diagnosisOrg']").val(obj.doctorDepartmentName);
					$("#diagnosisInfoForm [name='diagnosisReferralDoctorName']").val(obj.doctorName);
				}
			});
			if (!common.isEmpty(selectedValue)) {
				$("#" + id).val(selectedValue);
			};
		};
	});	
}

/**
 * 转诊医生下拉列表绑定事件
 */
function updataSelectDoctor(){
	$("#diagnosisReferralDoctor").change(function(){
		$(referralDoctors).each(function(index, obj) {
			if(!common.isEmpty($("#diagnosisReferralDoctor").val())){
				if($("#diagnosisReferralDoctor").val() == obj.id){
					$("#diagnosisReferralDept").html("<h5>"+obj.doctorDepartmentName+"</h5>");
					$("#diagnosisInfoForm [name='diagnosisOrg']").val(obj.doctorDepartmentName);
					$("#diagnosisInfoForm [name='diagnosisReferralDoctorName']").val(obj.doctorName);
				}
			}else{
				$("#diagnosisReferralDept").html("");
			}
		});
	});
}

//体重曲线图自动高度
function echartAutoHeight() {
	var height = $("#resultDiv").height()+3;
	$("#weightEcharDiv").css("height",height+"px");
}

//将历史中的诊断记录放到本次接诊中
function addDiagnosisHis(diseaseCodes,diseaseNames){
	$("#diagnosisDiseaseNames").val(diseaseNames);
	$("#diagnosisDiseaseCodes").val(diseaseCodes);
}

function checkNum(obj,name) {
	var flag = true;
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
        flag = false;
    }
    if (obj != null) {
        //检查小数点后是否多于两位
		if(obj.value.toString().split(".")[0].length > 3){
			layer.msg("整数位最多三位数");
            obj.value = "";
            flag = false;
		}
        if (obj.value.toString().split(".").length > 1) {//如果是小数
        	if(obj.value.toString().split(".")[0].length > 1 && obj.value.toString().split(".")[0].substring(0,1)=="0"){//如果是这种格式的小数 00.1 000.1 0000.1 000000.1
                layer.msg("请输入正常小数！");
                obj.value = "";
                flag = false;
        	}
        	if(obj.value.toString().split(".")[1].length > 1){//如果小数点后大于1位
                layer.msg("小数点后只能有一位！");
                obj.value = "";
                flag = false;
        	}
        }else {//如果是整数
        	if(obj.value.toString().length>1 && obj.value.toString().substring(0,1)=="0") {//如果是这种格式 01 001 001 0001
                layer.msg("请输入正常整数！");
                obj.value = "";
                flag = false;
        	}
        	if(name != "AmnioticFluid") {
            	if(obj.value.toString()=="0") {
                    layer.msg("不能输入0");
                    obj.value = "";
                    flag = false;
            	}	
        	}
        }
    }
    return flag;
};

/**
 * 如果通过验证，则变化有房曲线图
 */
function showWeightCheck(val) {
	if(checkNum(val)) {
		showWeight(val.value);
	}
}

/**
 * 初始化时间插件(选择时间的范围是末次月经~预产期之间)
 */
function initTimeDateReceiveView(id) {
	common.initDate(null,null,null,"#"+id);
	if(id == "obstetricalTopDate") {
		$("#"+id).val(obstetricalTopDateJson);	
	}else if(id == "obstetricalBottomDate") {
		$("#"+id).val(obstetricalBottomDateJson);	
	}
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
    $("#"+id).datetimepicker("setStartDate",d_lmp);// 末次月经
    $("#"+id).datetimepicker("setEndDate",nowDate);// 今天
}

$(document).ready(function(){
	//接诊信息加入必填项提示
	$("#diagnosisInfoForm").validate(doctorOptions);
	common.requiredHint("diagnosisInfoForm", doctorOptions);
	// 初始化日期选择插件
	initTimeDateReceiveView("obstetricalTopDate");
	initTimeDateReceiveView("obstetricalBottomDate");
	//接诊信息加入必填项提示
    $("#diagnosisObstetricalUpdateForm").validate(doctorOptions);
    common.requiredHint("diagnosisObstetricalUpdateForm", doctorOptions);
    
	//初始化接诊信息
	initDaignosisInfo();
	
	//初始化体重曲线图
	showWeight($("#weight_input_text").val());
	
    //加入必填项提示
    $("#diagnosisUpdateForm").validate(diagnosisUpdateOption);
    common.requiredHint("diagnosisUpdateForm", diagnosisUpdateOption);

    //初始化select 胎儿发育情况
    var babyInfo = obstetricalBabyJson;
    $("#obstetricalBaby").val(babyInfo);
    
    //初始化select 妊娠风险级别
    common.initCodeSelect("BIRTHINSPECTLEVEL", "BIRTHINSPECTLEVEL", "obstetricalGestationLevel", obstetricalGestationLevelJson, "==请选择5色风险级别==");
    
    // 初始化羊水
    fluidSum();    
    
    echartAutoHeight();
	
	//体重曲线图下结论，保存操作
	$(".intake-input").live("blur",function() {
		var flag = true;
		if(isNaN(this.value)||this.value.substring(this.value.length-1)==".") {
	        layer.msg("不能输入数字以外的字符！");
	        $("#"+this.id).val("");
	        flag = false;
		}else {
			if(this.value.indexOf(".")>-1) {//如果是小数
				if(this.value.split(".")[0].length>3) {
			        layer.msg("小数点前最多保留3位！");
			        $("#"+this.id).val("");
			        flag=false;
				}
				if(this.value.split(".")[1].length>2) {
			        layer.msg("小数点后最多保留2位！");
			        $("#"+this.id).val("");
			        flag = false;
				}
			}else {//如果是整数
				if(this.value.length>3) {
			        layer.msg("最多输入3位整数！");
			        $("#"+this.id).val("");
			        flag = false;
				}
			}
			if(flag) {
				var id = this.id;
				if(id.indexOf("one")>-1) {
					var two = $("#"+id.replace("one","two")).val();//第二个input里面value值
					if(two !="" && this.value !=  "") {//如果前后都有值，走ajax后台修改，这里要做一个验证，第一个input必须小于第二个input
						if(parseFloat(two)>=parseFloat(this.value)) {
						    var url = URL.get("Platform.SAVE_ECHARTS_WEIGHT_RESULT");
						    var params = "diagnosisId=" + $("#diagnosisId").val()+"&oneText="+this.value+"&twoText="+two+"&type="+this.name;
						    ajax.post(url, params, dataType.json, null, false, false);	
						}else {
					        layer.msg("第一个范围值不能大于第二个范围值！");
					        $("#"+this.id).val("");
						}
					}else if(two =="" && this.value ==  "") {//如果都为空，也可以修改
					    var url = URL.get("Platform.SAVE_ECHARTS_WEIGHT_RESULT");
					    var params = "diagnosisId=" + $("#diagnosisId").val()+"&oneText="+this.value+"&twoText="+two+"&type="+this.name;
					    ajax.post(url, params, dataType.json, null, false, false);	
					}
				}else if(id.indexOf("two")>-1) {
					var one = $("#"+id.replace("two","one")).val();//第一个input里面value值
					if(one !="" && this.value !=  "") {//如果前后都有值，走ajax后台修改，这里要做一个验证，第一个input必须小于第二个input
						if(parseFloat(this.value)>=parseFloat(one)) {
						    var url = URL.get("Platform.SAVE_ECHARTS_WEIGHT_RESULT");
						    var params = "diagnosisId=" + $("#diagnosisId").val()+"&oneText="+one+"&twoText="+this.value+"&type="+this.name;
						    ajax.post(url, params, dataType.json, null, false, false);	
						}else {
					        layer.msg("第二个范围值不能小于第一个范围值！");
					        $("#"+this.id).val("");							
						}
					}else if(one =="" && this.value ==  "") {
					    var url = URL.get("Platform.SAVE_ECHARTS_WEIGHT_RESULT");
					    var params = "diagnosisId=" + $("#diagnosisId").val()+"&oneText="+one+"&twoText="+this.value+"&type="+this.name;
					    ajax.post(url, params, dataType.json, null, false, false);	
					}
				}
			}	
		}
	});
	//结论验证操作
	$(".intake-input").live("keyup",function() {
		if(isNaN(this.value)) {
	        layer.msg("不能输入数字以外的字符！");
	        $("#"+this.id).val("");
		}
	});
    $("#obstetricalTopDate").change(function (){
    	var preg_week = pregnancy.getGestWeeksByLmpDate(this.value,d_lmp);
    	if(!common.isEmpty(preg_week)) {
        	var week = preg_week.split("+")[0];
        	var day = preg_week.split("+")[1];
        	var result = '（孕周数：'+week+' <sup style="font-size: 85%;">＋'+day+'</sup> 周）';
        	$("#preg_top_week").html(result);
        	$("#obstetricalTopGestationalweeks").val(result);
        	showResult(week,1);
    	}
	});

    $("#obstetricalBottomDate").change(function (){
    	var preg_week = pregnancy.getGestWeeksByDueDate(this.value,d_due);
    	if(!common.isEmpty(preg_week)) {
        	var week = preg_week.split("+")[0];
        	var day = preg_week.split("+")[1];
        	var result = '（孕周数：'+week+' <sup style="font-size: 85%;">＋'+day+'</sup> 周）';
        	$("#preg_bottom_week").html(result);
        	$("#obstetricalBottomGestationalweeks").val(result);
        	showResult(week,2);
    	}
	});
    
    $("#obstetricalAmnioticFluidOne,#obstetricalAmnioticFluidTwo,#obstetricalAmnioticFluidThree,#obstetricalAmnioticFluidFour").keyup(function (){
    	fluidSum();
	});
    
    $("#weight_input_text").keyup(function(event) {
    	if(event.keyCode == 13) {
    		$("#weight_input_text").blur();
    	}
    });
    
	window.addEventListener("resize", function() {
		echartAutoHeight();
	});
});