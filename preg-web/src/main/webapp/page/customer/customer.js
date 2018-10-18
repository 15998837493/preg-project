var checkedData;// 选中项信息
var checkedRow;// 选中行信息
var customerTable;// table，注意命名不要重复

/**
 * 验证参数设置
 */
var customerOptions = {
	rules: {
		custPatientId: {
			required: true,
			code:true,
			maxlength: 50,
			remote : {
				url : URL.get("Customer.VALIDATE_PATIENTID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					custPatientId : function() {
						return $("#custPatientId").val();
					},
					custId : function() {
						return $("#custId").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		custSikeId: {
			code:true,
			maxlength: 50,
			remote : {
				url : URL.get("Customer.VALIDATE_SIKEID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					custSikeId : function() {
						return $("#custSikeId").val();
					},
					custSikeIdOld : function() {
						return $("#custSikeIdOld").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		custName: {
			required: true,
			maxlength: 20
		},
		custPhone :{
			mobile: true
		},
		custIcard: {
			idcard:true,
			remote : {
				url : URL.get("Customer.VALIDATE_PATIENT_ICARD"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					custIcard : function() {
						return $("#custIcard").val();
					},
					oldCustIcard : function() {
						return $("#oldCustIcard").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		custSex: {
			required:true
		},
		custBirthday: {
			required: true
		},
		custBirthWeight: {
			isWeight: true,
			min:1
		},
		custWeight: {
			isWeight: true,
			min:1,
			required: true
		},
		custHeight: {
			isHeigth: true,
			min:1,
			required: true
		},
		custWaistline: {
			min:1
		},
		lmp:{
			required: true
		},
		pregnancyDueDate:{
			required: true
		}
	},
	messages:{
		custPatientId:{
			remote: "ID已存在，请重新填写"
		},
		custSikeId:{
			remote: "病案号已存在，请重新填写"
		},
		custIcard:{
			remote: "身份证号已存在，请重新填写"
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		var opName = $("#span_button").html();
		layer.confirm("确定要执行【"+opName+"】操作？", function (data) {
			$(form).ajaxPost(dataType.json,function(data){
				common.modal("editItemModal", common.modalType.hidden, function(){
					common.pageForward(PublicConstant.basePath + "/page/customer/customer_view.jsp");
				});
			}, false);
        });
	}
};
//配置或者建档记录datatable
var tableOptions = {
	id: "customerTable",
	form: "customerQuery",
	bServerSide: true,
    async: false,
    bSort:true,
	columns: [
		{"data": "custSikeId","sClass": "text-center","orderable": false,
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "custPatientId","sClass": "text-center" , 
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "custName","sClass": "text-center",
		 	"render": function(data, type, row, meta) {
		 		return "<a onclick='getMyInfo(\""+row.custId+"\")' style='cursor:pointer;'>" + data + "</a>";
			}
		},
		{"data": "custAge","sClass": "text-center","orderable": false,},
		{"data": "custIcard","sClass": "text-center","orderable": false,},
		{"data": "custPhone","sClass": "text-center","orderable": false,},
		{"data": "diagnosisCount","sClass": "text-center","orderable": false,},
		{"data": null,"sClass": "text-center","orderable": false,
			"render": function(data, type, row, meta) {
				return "<a onclick='getPregnancy(\""+data.custId+"\",\""+data.id+"\")' style='cursor:pointer;'>查询</a>";
			}
		},
		{"data": null,"sClass": "text-center","orderable": false,
			"render":  function (data, type, row, meta) {
        		return '<a onclick="clickToHistory(\''+data.custId+'\',1)">查询<a/>';
        	}
		},
		{"data": null,"sClass": "text-center","orderable": false,
			"render":  function (data, type, row, meta) {
				return '<a onclick="clickToHistory(\''+data.custId+'\',2)">查询<a/>';
        	}
		},
		{"data": null,"sClass": "text-center","orderable": false,
			"render":  function (data, type, row, meta) {
				return '<a onclick="getBirthEnding(\''+data.custId+'\')">查询<a/>';
        	}
		}
	],
	rowClick: function(data, row){
		checkedData = data;
		checkedRow = row;
		$("#custId").val(data.custId);
	},
	aaSorting: [],
	condition : "customerCondition",
};

//配置或者建档信息datatable
var pregInfoOptions = {
	id: "pregnancyTable",
	form: "queryForm",
    async: true,
	columns: [
        {"data": null,"sClass": "text-center","orderable": false},
		{"data": "createDatetime","sClass": "text-center","orderable": false,},
		{"data": null,"sClass": "text-center","orderable": false,
			"render": function(data, type, row, meta) {
		 		return pregnancy.getGestWeeksByDueDate(data.createDatetime,data.pregnancyDueDate);
			}	
		},
		{"data": "userName","sClass": "text-center","orderable": false,},
		{"data": null,"sClass": "text-center","orderable": false,
			"render": function(data, type, row, meta) {
		 		return "<a onclick='getPregArchive(\""+data.id+"\")'>查询</a>";
			}
		}
	],
	condition : "Condition",
	orderIndex : 0,
};

//分娩结局datatable
var birthEndingOptions = {
		id: "birthEndingTable",
		form: "queryBirthEndingForm",
		async: true,
		columns: [
			{"data": null,"sClass": "text-center","orderable": false},
			{"data": "birthDate","sClass": "text-center","orderable": false,},
			{"data": "birthWeeks","sClass": "text-center","orderable": false,},
			{"data": "birthHospital","sClass": "text-center","orderable": false,},
			{"data": "birthPregHospital","sClass": "text-center","orderable": false,},
			{"data": "createTime","sClass": "text-center","orderable": false,},
			{"data": "createUserName","sClass": "text-center","orderable": false,},
			{"data": null,"sClass": "text-center","orderable": false,
				"render": function(data, type, row, meta) {
					return "<a onclick='showBirthEnding(\""+data.birthId+"\",\""+data.custId+"\")'>查看</a>";
				}
			}
			],
			condition : "Condition",
			orderIndex : 0,
};

/**  
 * 身份证验证通过后设置性别和出生日期  
 * @param idCard 15/18位身份证号码   
 * @return   
 */ 
function initBirthSex(idCard){
	//判断验证是否通过
	if(idCardResult){
		//设置性别值
		common.initCodeSelect("sex", "sex", "custSex",getMaleOrFemalByIdCard(idCard));
		//设置出生日期
		$("#custBirthday").val(getBirthdayByIdCard(idCard));
	}
};

/**
 * 跳转到历史记录
 */
function clickToHistory(custId,type) {
	if(type==1) {
		common.openWindow(URL.get("Platform.QUERY_REPORT_VIEW") + "?custId=" + custId);
	}else if(type==2) {
		common.openWindow(URL.get("Platform.DIAGNOSIS_RECORD") + "?custId=" + custId);
	}
}

/**
 * 计算孕产期--修改末次月经的值是自动触发
 */
function calculationDelivery(){
	var lmp = $('#lmp').val();
	var pregnancyDueDate ="请先设置末次月经";
	if(!common.isEmpty(lmp)){
		pregnancyDueDate = pregnancy.computeDueDate(lmp);
	}
	$('#pregnancyDueDate').val(pregnancyDueDate);
}

/**  
 * 通过身份证判断是男是女  
 * @param idCard 15/18位身份证号码   
 * @return 'female'-女、'male'-男  
 */  
function getMaleOrFemalByIdCard(idCard){   
    idCard = common.trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }   
} ;
/**  
 * 获取出生日期 
 * @param idCard 15/18位身份证号码   
 * @return birthday 出生日期  
 */ 
function getBirthdayByIdCard(idCard){
	var birthday = "";
	idCard = common.trim(idCard.replace(/ /g, ""));
	if(idCard.length == 15){
		var year =  idCard.substring(6,8);   
	    var month = idCard.substring(8,10);   
	    var day = idCard.substring(10,12);
	    birthday = "19"+year+"-"+month+"-"+day;
	}
	if(idCard.length == 18){
		var year =  idCard.substring(6,10);   
	    var month = idCard.substring(10,12);   
	    var day = idCard.substring(12,14); 
	    birthday = year+"-"+month+"-"+day;
	}
	return birthday;
};
function getMyInfo(custId){
	common.openWindow(URL.get("Platform.DIAGNOSIS_RECORD") + "?custId=" + custId);
}

function isChoose(id) {
	if (common.isEmpty(id)) {
		layer.alert('请选择操作的记录', {
			title : '提示信息'
		});
		return false;
	} else {
		return true;
	}
};
//设置系统参数
function resetParam(custId){
	var url = URL.get("Customer.INIT_UPDATE_CUST");
	var params = "custId=" + paramId;
	ajax.post(url, params, dataType.json, function(data) {
		var d = data.value;
		$("#paramId_update").val(d.paramId);
		$("#paramName_update").val(d.paramName);
		$("#paramValue_update").val(d.paramValue);
		$("#paramType_update").val(d.paramType);
		$("#paramRemark_update").val(d.paramRemark);
	});
	$("#updateForm").validate(systemParamOptions);
	//加入必填项提示
	common.requiredHint("updateForm", systemParamOptions);
	$("#updateModal").modal("show");
}
$().ready(function (){
	
	//$("#custSikeIdOld").val(custVo.custSikeId);
	//初始化信息
	common.initCodeSelect("sex", "sex", "custSex","female");
	common.initCodeSelect('custLevel','custLevel','custLevel');
	common.initCodeSelect('nation','nation','custNation');
	common.initCodeSelect('custMarriage','custMarriage','custMarriage');
	common.initCodeSelect('custJob','custJob','custJob');
	common.initCodeSelect('custEducation','custEducation','custEducation');
	common.initCodeSelect('custPlevel','custPlevel','custPlevel');
	common.initCodeSelect('custIncome','custIncome','custIncome');
	//初始化省、市、区
	common.initChinaArea('0','0','0','custProvince','custCity','custDistrict');
	//加载datatable
	customerTable = datatable.table(tableOptions);
	//验证、必填项提示
	$("#editItemForm").validate(customerOptions);
	common.requiredHint("editItemForm",customerOptions);
	
	//日期插件格式定义--出生日期
	common.initDate(null,null,4,"#custBirthday","1990-01-01");
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	$("#custBirthday").datetimepicker("setEndDate",nowDate);
	common.initDate(null,null,2,"#lmp",nowDate);
	$("#lmp").datetimepicker("setEndDate",nowDate);
	// 返回按钮点击事件
	// 按钮点击事件
	$("button[name='operateButton']").click(function(){
		var custId = $("#custId").val();
		if(this.id=="addButton"){
			//common.pageForward(PublicConstant.basePath + "/page/customer/customer_add.jsp");
			operateType="add";
			common.clearForm("editItemForm");
			$("#editItemForm").attr("action", URL.get("Customer.ADD_CUST"));
			$("#tittle").html('<i class="fa fa-plus fa-fw"></i> 建档');
			$("#editItemModal").modal("show");
		}
		if(this.id=="editButton" && isChoose(custId)){
			//common.pageForward(URL.get("Customer.INIT_UPDATE_CUST")+"?custId=" + custId);
			operateType="updata";
			common.clearForm("editItemForm");
			$("#editItemForm").attr("action", URL.get("Customer.UPDATE_CUST")+"?custId=" + custId);
//			//处理 省/市/区
			if(checkedData.custProvince != null) {
			var custProvince = checkedData.custProvince;
			common.getChinaArea(custProvince, 'custCity', 'custDistrict');
			}
			if(checkedData.custCity != null) {
			var custCity = checkedData.custCity;
			common.getChinaArea(custCity, '', 'custDistrict');
			}
			
			$("#editItemForm").jsonToForm(checkedData);
			$("#custSikeIdOld").val($("#custSikeId").val());
			$("#oldCustIcard").val($("#custIcard").val());
			$("#tittle").html('<i class="fa fa-edit fa-fw"></i>编辑');
			$("#editItemModal").modal("show");
		}
		if(this.id=="searchButton"){
			customerTable = datatable.table(tableOptions);
		}
	});
});
/**
 * 根据预产期计算孕周数
 * @param date
 * @returns {String}
 */
function getPregWeeksAndPlusDay(date){
	var numDays = 280 - GetDateDiff(date) - 1;
	var pregnantWeeks = numDays / 7;
    var plusDays = numDays % 7;
    return Math.floor(pregnantWeeks) + "<sup style='font-size: 85%'> +" + plusDays + "</sup>";
}
/**
 * 获取日期间隔
 * @param endDate
 * @returns {Number}
 */
function GetDateDiff(endDate) {  
    var startTime = new Date().getTime();     
    var endTime = new Date(endDate).getTime();
    var dates = Math.abs((endTime - startTime))/(1000*60*60*24);
    return  Math.floor(dates);    
}

function getPregnancy(custId,id){
	$("#queryForm [name='custId']").val(custId);
	datatable.table(pregInfoOptions);
	//关闭遮罩层
	layer.close(layer.index);
	$("#pregnancyModal").modal("show");
}

/**
 * 分娩结局
 * @param custId
 * @returns
 */
function getBirthEnding(custId){
	$("#queryBirthEndingForm [name='custId']").val(custId);
	datatable.table(birthEndingOptions);
	//关闭遮罩层
	layer.close(layer.index);
	$("#birthEndingModal").modal("show");
}

/**
 * 查看
 * @param custId
 * @returns
 */
function showBirthEnding(birthId,custId){
	common.openWindow(URL.get("BirthEnding.BIRTHENDING_DETAIL") + "?birthId=" + birthId+"&custId="+custId);
}

function getPregArchive(id){
	common.openWindow(URL.get("Customer.QUERY_CUST_PREG_INFO") + "?id=" + id);
}