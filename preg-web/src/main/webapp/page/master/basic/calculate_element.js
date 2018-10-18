/**
 * 食材元素计算
 */
var elementArray = [];

//食材map
var fmMap = new Map();

//元素map
var elMap = new Map();

/**
 * 添加(更新)fmMap
 * @params weight 重量
 */
function addFmMap(fmId, weight) {
	 // 获取食材对象
	 var fm = getFmBase(fmId);
	 // 获取可食用部分
	 var fmEsculent = fm.fmEsculent;
	 var tempEleArray = ajaxData(fmId);
	 for (var i = 0; i <tempEleArray.length; i++){
		 if (tempEleArray[i].nutrientDosage > 0) {
			 tempEleArray[i].nutrientDosage = common.accMul(tempEleArray[i].nutrientDosage, weight);
			 if (fmEsculent != null) {
				 //计算可食用部分
				 tempEleArray[i].nutrientDosage = common.accMul(tempEleArray[i].nutrientDosage, fmEsculent);
				 tempEleArray[i].nutrientDosage = common.accDiv(tempEleArray[i].nutrientDosage, 100);
			 }
			 tempEleArray[i].nutrientDosage = common.accDiv(tempEleArray[i].nutrientDosage, 100).toFixed(4); //保留四位小数
		 }		 
	 }	 
	fmMap.set(fmId, tempEleArray);
}

//删除
function delFmMap(fmId) {
	fmMap.delete(fmId);
}

/**
 * 添加元素elMap
 * @params nutriId 元素id
 * @params ele 元素
 */
function addElMap(nutriId, ele) {	
	if (!elMap.has(nutriId)) {
		ele.nutrientDosage = Number(ele.nutrientDosage).toFixed(4);
		elMap.set(nutriId, ele);
	} else {
		var c = common.accAdd(elMap.get(nutriId).nutrientDosage, ele.nutrientDosage);
		ele.nutrientDosage = c.toFixed(4);
		elMap.set(nutriId, ele);
	}	
}

//删除元素
function delElMap(nutriId) {
	elMap.delete(nutriId);
}

/**
 * 将Map转化成List,重新调动DOM元素
 * @params elMap 元素Map
 */
function refresh(elMap) {
	elementArray.splice(0,elementArray.length);  
	elMap.forEach(function (item) {
		elementArray.push(item);
	});	
	//初始化元素的html
	drawEleDom();
}

/**
 * 元素含量加和
 * @params fmMap 食材Map
 */
function add(fmMap) {
	//清空元素Map
	elMap.clear();
	fmMap.forEach(function (item) {
		 for (var i = 0; i <item.length; i++){
			 addElMap(item[i].nutrientId, item[i]);			
		 }	   
	});	
}

/**
 * 获取食材元素数据
 * @params fmId 食材id
 */
function ajaxData(fmId) {
	var tempEleArray = [];
	var url = URL.get("foodMaterial.GET_FOOD_ELEMENT");
	var params = "fmId=" + fmId;
	ajax.post(url, params, dataType.json, function(data) {		
		tempEleArray = data.data;		
	},false,false);
	return tempEleArray;	
}

/**
 * 获取食材基本信息
 * @params fmId 食材id
 */
function getFmBase(fmId) {
	var tempEleArray = {};
	var url = URL.get("foodMaterial.GET_FM");
	var params = "fmId=" + fmId;
	ajax.post(url, params, dataType.json, function(data) {		
		tempEleArray = data.value;		
	},false,false);
	return tempEleArray;	
}

/**
 * 查询食谱对应的食材集合
 * @param foodId 食谱Id
 */
function ajaxListData(foodId) {
	var tempEleArray = [];
	var url = URL.get("Food.FOOD_QUERY_MATERIAL");
	var params = "foodId=" + foodId;
	ajax.post(url, params, dataType.json, function(data) {		
		tempEleArray = data.data;		
	},false,false);
	return tempEleArray;	
}

/**
 * 添加/修改 食材计算元素方法
 * @params fmId   食材id
 * @params weight 食材重量
 * @params fmMap  食材Map
 * @params elMap  元素Map
 * @params caluateType 运算类型
 * @params fmUpdate  是否修改
 */
function caluateEle(fmId, weight, fmMap, elMap, caluateType, fmUpdate) {
	if (fmUpdate != "fmUpdate") {//修改食材
		if (caluateType == "add") {//增加食材
			//1、添加食材
			addFmMap(fmId, weight);
		} else if (caluateType == "sub") {//减少食材
			//删除食材
			delFmMap(fmId);
		}
	} else {
		delFmMap(fmId);
		addFmMap(fmId, weight);
	}

	//2、添加元素
	add(fmMap);
	//3、生成数组、刷新Map
	refresh(elMap);
}

/**
 * 初始化编辑食谱元素数据
 * @params foodId 食谱id
 */
function initEle(foodId) {
	fmMap.clear();
	var tempEleArray = ajaxListData(foodId);
	for (var i = 0; i <tempEleArray.length; i++){
		caluateEle(tempEleArray[i].fmId, tempEleArray[i].fmlAmount, fmMap, elMap, 'add', '');
	}	
}

