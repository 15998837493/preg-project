/**
 * 检验input只能为数字
 */
function checkNum(obj,name,length) {
    var flag = true;
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
        flag = false;
    }
    if (obj != null) {
        if (obj.value.toString().split(".").length > 1) { //如果是小数
//            if (obj.value.toString().split(".")[0].length > 1) { //如果是这种格式的小数 00.1 000.1 0000.1 000000.1
                layer.msg("请输入整数！");
                obj.value = "";
                flag = false;
//            }
        } else { //如果是整数
            if (obj.value.toString().length > 1 && obj.value.toString().substring(0, 1) == "0") { //如果是这种格式 01 001 001 0001
                layer.msg("请输入正常整数！");
                obj.value = "";
                flag = false;
            }
            if(length!='' && obj.value.toString().length > length){
            	layer.msg("不能输入整数超过"+length+"位");
            	obj.value = "";
            	flag = false;
            }
            if(name != "AmnioticFluid") {
                if (obj.value.toString() == "0") {
                    layer.msg("不能输入0");
                    obj.value = "";
                    flag = false;
                }	
            }
        }
    }
    return flag;
};
function checkWeeks(obj){
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
	var checkWeeks = /[\u4e00-\u9fa5_a-zA-Z]/;//汉字和英文

	var plus = "\\+";
	var regex = new RegExp(plus, 'g'); 
	var result = obj.value.match(regex);
	
	if(checkWeeks.test(obj.value) || (result != null && result.length > 1)){
		layer.alert("输入有误，请重新输入");
		obj.value="";
		return;
	}	
}
function checkPregBorn(){
	if($("#birthPregNumber").val() < $("#birthBornNumber").val() ){
		layer.msg("孕次不能小于产次");
		$("#birthBornNumber").val("");
		return;
	}	
}
function checkHourMinutes(obj,name){
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
        return;
    }
    
    if (!(/(^[0-9]\d*$)/.test(obj.value))) {
		layer.msg("请输入正常整数！"); 
		obj.value = "";
		return;
	}
	
	if(name == "hour" && obj.value > 23){
		 layer.msg("请输入正确时间！");
         obj.value = "";
         return;
	}	
	
	if(name == "minuters" && obj.value > 59){
		layer.msg("请输入正确时间！");
		obj.value = "";
		return;
	}	
}


/**
 * 检验input只能为数字，小数点后保留一位
 */
function checkNumPoint2(obj,name) {
    var flag = true;
	// 不允许输入空格
	obj.value = obj.value.replace(/\s+/g,'');
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
        flag = false;
    }
    if (obj != null) {
        if (obj.value.toString().split(".").length > 1) { //如果是小数
            if (obj.value.toString().split(".")[0].length > 1 && obj.value.toString().split(".")[0].substring(0, 1) == "0") { //如果是这种格式的小数 00.1 000.1 0000.1 000000.1
                layer.msg("请输入正常小数！");
                obj.value = "";
                flag = false;
            }
            if (obj.value.toString().split(".")[1].length > 1) { //如果小数点后大于1位
                layer.msg("小数点后只能有一位！");
                obj.value = "";
                flag = false;
            }
        } else { //如果是整数
            if (obj.value.toString().length > 1 && obj.value.toString().substring(0, 1) == "0") { //如果是这种格式 01 001 001 0001
                layer.msg("请输入正常整数！");
                obj.value = "";
                flag = false;
            }
            if(name != "AmnioticFluid") {
                if (obj.value.toString() == "0") {
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
 * 检验input只能为数字，小数点后保留一位
 */
function checkNumPoint(obj,name) {
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
		if (obj.value.toString().split(".")[0].length > 3) {
			layer.msg("整数位最多三位数");
			obj.value = "";
			flag = false;
		}
		if (obj.value.toString().split(".").length > 1) { //如果是小数
			if (obj.value.toString().split(".")[0].length > 1 && obj.value.toString().split(".")[0].substring(0, 1) == "0") { //如果是这种格式的小数 00.1 000.1 0000.1 000000.1
				layer.msg("请输入正常小数！");
				obj.value = "";
				flag = false;
			}
			if (obj.value.toString().split(".")[1].length > 1) { //如果小数点后大于1位
				layer.msg("小数点后只能有一位！");
				obj.value = "";
				flag = false;
			}
		} else { //如果是整数
			if (obj.value.toString().length > 1 && obj.value.toString().substring(0, 1) == "0") { //如果是这种格式 01 001 001 0001
				layer.msg("请输入正常整数！");
				obj.value = "";
				flag = false;
			}
			if(name != "AmnioticFluid") {
				if (obj.value.toString() == "0") {
					layer.msg("不能输入0");
					obj.value = "";
					flag = false;
				}	
			}
		}
	}
	return flag;
};
