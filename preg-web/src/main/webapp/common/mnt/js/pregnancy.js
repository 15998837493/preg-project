pregnancy = {};

/**
 * 输入孕周（1+2）返回角标形式的html
 */
pregnancy.gestationalWeeksSupHtml = function(content) {
	if (common.isEmpty(content)) {
		return "";
	}
	if(content.indexOf("+") > -1) {
		var contArr = content.split("+");
		return contArr[0] + "<sup style='font-size: 85%'> +" + contArr[1]
				+ "</sup>";
	}else {
		return content;
	}
};

/**
 * 根据预产期计算孕周数（参数均为日期格式字符串）
 * 
 * @param date
 *            要计算孕周数的日期，格式:"yyyy-MM-dd"
 * @param dueDate
 *            预产期，格式:"yyyy-MM-dd"
 * @return
 */
pregnancy.getGestWeeksByDueDate = function(date, dueDate) {
	var numDays = 280 - common.getDateDiff(date, dueDate);
	var pregnantWeeks = numDays / 7;
	var plusDays = numDays % 7;
	return Math.floor(pregnantWeeks) + "+" + plusDays;
};

/**
 * 根据末次月经计算孕周数（参数均为日期格式字符串）
 * 
 * @param date
 *            要计算孕周数的日期，格式:"yyyy-MM-dd"
 * @param lmpDate
 *            末次月经日期，格式:"yyyy-MM-dd"
 * @return
 */
pregnancy.getGestWeeksByLmpDate = function(date, lmpDate) {
	var numDays = common.getDateDiff(lmpDate, date);
	var pregnantWeeks = numDays / 7;
	var plusDays = numDays % 7;
	return Math.floor(pregnantWeeks) + "+" + plusDays;
};

/**
 * 根据末次月经计算预产期 计算方式：末次月经后40周的日期
 * 
 * @param 输入yyyy-mm-dd格式的日期字符串
 * @return 返回固定格式字符串yyyy-mm-dd
 */
pregnancy.computeDueDate = function(date) {
	var nd = new Date(date);
	nd = nd.valueOf();
	nd = nd + 24192000000;// 280*24*3600*1000
	nd = new Date(nd);
	var y = nd.getFullYear();
	var m = nd.getMonth() + 1;
	var d = nd.getDate();
	if (m <= 9)
		m = "0" + m;
	if (d <= 9)
		d = "0" + d;
	var cdate = y + "-" + m + "-" + d;
	return cdate;
};

/**
 * 根据BMI获取【建议整体孕期体重适宜增长范围】
 * 
 * @param bmi
 * @return 返回固定格式字符串 范围-范围或空-空
 */
pregnancy.getPregnancyWeightByBmi = function(bmi) {
	bmi = parseFloat(bmi);
	var pregnancyWeight = "-";
	if (isNaN(bmi) == false) {
		if (bmi < 18.5) {
			pregnancyWeight = "12.5-18";
		} else if (bmi >= 18.5 && bmi <= 24.9) {
			pregnancyWeight = "11.5-16";
		} else if (bmi >= 25.0 && bmi <= 29.9) {
			pregnancyWeight = "7-11.5";
		} else if (bmi >= 30.0) {
			pregnancyWeight = "5-9";
		}
	}
	return pregnancyWeight;
};

/**
 * 根据BMI获取【建议每周体重适宜增长范围】
 * 
 * @param bmi
 * @return 返回固定格式字符串 范围-范围或空-空
 */
pregnancy.getWeekWeightByBmi = function(bmi) {
	bmi = parseFloat(bmi);
	var pregnancyWeight = "-";
	if (isNaN(bmi) == false) {
		if (bmi < 18.5) {
			pregnancyWeight = "0.44-0.58";
		} else if (bmi >= 18.5 && bmi <= 24.9) {
			pregnancyWeight = "0.35-0.50";
		} else if (bmi >= 25.0 && bmi <= 29.9) {
			pregnancyWeight = "0.23-0.33";
		} else if (bmi >= 30.0) {
			pregnancyWeight = "0.17-0.27";
		}
	}
	return pregnancyWeight;
};