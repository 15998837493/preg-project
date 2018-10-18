
var diagnosisDiseaseMap = {};
diagnosisDiseaseMap.first = new Map();
diagnosisDiseaseMap.vist = new Map();

//=====================================【首诊登记开始】====================================
/**
 * 验证参数设置
 */
var custOptions = {
	rules: {
		custPatientId: {
			maxlength: 50,
			remote : {
				url : URL.get("Customer.VALIDATE_PATIENTID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					custPatientId : function() {
						return $("#custPatientId").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		custName: {
			maxlength: 80,
			required: true
		},
		custSikeId:{
			code : true,
			maxlength: 100,
			remote : {
				url : URL.get("Customer.VALIDATE_SIKEId"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					custSikeId : function() {
						return $("#custSikeId").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		custIcard:{
			maxlength: 30,
			idcard : true,
			remote : {
				url : URL.get("Customer.VALIDATE_ICARD"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					custIcard : function() {
						return $("#custIcard").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	messages:{
		custPatientId:{
			remote: "ID已存在，请重新填写"
		},
		custSikeId:{
			remote: "病案号已存在，请重新填写"
		},
		custIcard:{
			remote: "该身份证已存在，请重新填写"
		}
	},
	success: $.noop,
	submitHandler: function(form) {
		if("male" == $("#custSex").val()){
			layer.confirm('根据身份证判断该患者为男性，是否继续注册？', {btn: ['是','否']}, function(index){
				layer.close(index);
				addRegistration();
			}, function(index){
				layer.close(index);
				$("#addModal").modal("hide");
			});
		} else{
			addRegistration();
		}
	}
};

/**
 * 更具身份证判断性别和出生日期
 * @param idCard
 * @returns
 */
function initBirthSex(idCard){
	// 判断验证是否通过
	if(idCard){
		$("#custSex").val(common.getPersonalByIdCard(idCard, "sex"));// 设置性别值
		$("#custBirthday").val(common.getPersonalByIdCard(idCard, "birthday"));// 设置出生日期
	}
};

/**
 * 获取科室名称--首诊登记
 * @param deptId
 */
function getDoctorDepartmentName(doctorId){
	if(!_.isEmpty(doctorId)){
		var doctor = referralMap.get(doctorId);
		$("#diagnosisReferralDoctorName").val(doctor.doctorName);
		$("#diagnosisOrg").val(doctor.doctorDepartmentName);
		$("#deptNameSpan").html(doctor.doctorDepartmentName);
	} else{
		$("#diagnosisReferralDoctorName").val("");
		$("#diagnosisOrg").val("孕期营养门诊");
		$("#deptNameSpan").html("");
	}
}

/**
 * 保存首诊登记信息
 * @returns
 */
function addRegistration(){
	// 评价项目信息
	var inspectArray = [];
	$("#inspectTable input:checkbox[inspectName]").each(function(index, value){
		var inspect = {};
		inspect.inspectId = $(value).attr("value");
		inspect.inspectCode = $(value).attr("inspectCode");
		inspect.inspectName = $(value).attr("inspectName");
		if(value.checked){
			inspect.inspectStatus = "2";
		} else{
			inspect.inspectStatus = "1";
		}
		inspectArray.push(inspect);
	});
	if(!_.isEmpty(inspectArray)){
		$("#inspectListJson").val(JSON.stringify(inspectArray));
	}
	// 诊断项目信息
	var diseaseArray = [];
	diagnosisDiseaseMap.first.forEach(function(value, key){
		var masDisease = diseaseMap.get(key);
		var disease = {};
		disease.diseaseId = key;
		disease.diseaseCode = masDisease.diseaseCode;
		disease.diseaseName = masDisease.diseaseName;
		disease.diseaseIcd10 = masDisease.diseaseIcd10;
		disease.diseaseStatus = "确诊";
		disease.type = "0";
		diseaseArray.push(disease);
	});
	if(!_.isEmpty(diseaseArray)){
		$("#diseaseListJson").val(JSON.stringify(diseaseArray));
	}
	// 保存信息
	layer.confirm("请确认登记信息是否正确！", function (index) {
		$("#addCustomerForm").ajaxPost(dataType.json, function(data){
			$("#addModal").modal("hide");
			datatable.table(tableOptions);
			// $("#diagCustListTable_wrapper .row").remove();
			// $("#admissionsTable_wrapper .row").remove();
		});
	});
}

//=====================================【首诊登记结束】====================================

//=====================================【默认评价项目开始】====================================

/**
 * 弹出并初始化默认评价项目
 */
function showDefaultInspectModal(){
	common.modal("defaultInspectModal", "shown.bs.modal", function(){
		common.autocompleteMethod("inspectItem2", inspectListData, function(data){
			addTdHtml("inspectTable2", "inspectItem2", data.val);
		});
	});
}

/**
 * 初始化个人默认评价项目
 * @param tableId
 * @param inputId
 * @returns
 */
function initUserInspectItem(tableId, inputId){
	if(!_.isEmpty(userInspectListJson)){
		$.each(userInspectListJson, function(index, value){
			addTdHtml(tableId, inputId, value, value.inspectCheck);
		});
	}
}

/**
 * 保存首诊个人默认评价项目
 */
function saveUserInspectItem(){
	var inspectArray = [];
	$("#inspectTable2 input:checkbox[inspectName]").each(function(index, value){
		var inspectItem = {};
		inspectItem.inspectId = $(value).attr("value");
		inspectItem.inspectType = "first";
		inspectItem.inspectCode = $(value).attr("inspectCode");
		inspectItem.inspectName = $(value).attr("inspectName");
		if(value.checked){
			inspectItem.inspectCheck = 1;
		} else{
			inspectItem.inspectCheck = 0;
		}
		inspectArray.push(inspectItem);
	});
	if(_.isEmpty(inspectArray)){
		layer.alert("请添加评价项目！");
		return false;
	}
	var url = URL.get("Platform.USER_INSPECT_ITEM_SAVE");
	var params = "userInspectItemListJson="+JSON.stringify(inspectArray)+"&inspectType=first";
	ajax.post(url, params, dataType.json, function(data){
		userInspectListJson = data.value;
		$("#defaultInspectModal").modal("hide");
	}, false, false);
}

//=====================================【默认评价项目结束】====================================

//=====================================【患者登记开始】====================================

var custData;
var custRow;
var custTable;

var custTableOption = {
	id:"custListTable",
	form:"customerRegisterQuery",
	bServerSide: true,
	async: false,
	bSort:true,
	columns: [
  		{"data": "custCode","sClass": "text-center hide","orderable": false, },
  		{"data": "custSikeId","sClass": "text-center","orderable": false, //病案号
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
  		{"data": "custPatientId","sClass": "text-center",},
  		{"data": "custName","sClass": "text-center",
  			render: function(data, type, row, meta) {
  				return "<a onclick='getMyInfo(\""+row.custId+"\")' style='cursor:pointer;'>" + data + "</a>";
  			}
	  	},
  		{"data": "custAge","sClass": "text-center","orderable": false,},
  		{"data": "custIcard","sClass": "text-center","orderable": false,},
  		{"data": "diagnosisCount","sClass": "text-center","orderable": false,},
  		{"data": null,"sClass": "text-center","orderable": false,
  			render: function(data, type, row, meta) {
  				return "<a style='cursor: pointer;' onclick='diagnosisVist(\""+ data.custId +"\", \"3\")'>随诊</a> &nbsp;"+
  				"<a style='cursor: pointer;' onclick='diagnosisVist(\""+ data.custId +"\", \"2\")'>复诊</a>";
  			}
	  	},
  	],
	rowClick: function(data, row){
		custData = data;
		custRow = row;
	},
	aaSorting: [],
	condition : "customerCondition",
	loading: false,// 是否启用遮罩层
};

/**
 * 查看个人信息
 * @param custId
 * @returns
 */
function getMyInfo(custId){
	common.openWindow(URL.get("Platform.DIAGNOSIS_RECORD") + "?custId=" + custId);
}

/**
 * 患者登记
 * @param custId
 * @param type
 * @returns
 */
function diagnosisVist(custId, diagnosisType){
	if($("span[diagnosisCustomer='"+custId+"']").length > 0){
		layer.alert("该患者已登记！");
		return false;
	} 
	// 随诊登记
	if(diagnosisType == "3"){
		var diagnosisJson = {
			diagnosisCustomer: custId,
			diagnosisStatus: "1",
			diagnosisType: "3",
			diagnosisOrg: "孕期营养门诊",
		};
		var url = URL.get("Platform.DIAGNOSIS_ADD");
		var param = "diagnosisJson=" + JSON.stringify(diagnosisJson);
		ajax.post(url, param, dataType.json, function(data){
			datatable.table(tableOptions);// 刷新未接诊列表
		}, false, false);
	}
	// 复诊登记
	if(diagnosisType == "2"){
		$("#editModal").modal("hide");
		$("#custIdVist").val(custId);
		// 初始化诊断项目
		ajax.post(URL.get("Platform.DIAGNOSIS_LAST_DISEASE"), "custId="+custId, dataType.json, function(data){
			if(!_.isEmpty(data.value)){
				$(data.value).each(function(index, disease){
					$("#diseaseTableVist").empty();
					addDiseaseTdHtml("diseaseTableVist", "diseaseItemVist", disease);
				});
			}
		}, false, false);
		// 初始化评价项目
		common.modal("vistModal", "shown.bs.modal", function(){
			common.autocompleteMethod("inspectItemVist", inspectListData, function(data){
				addTdHtml("inspectTableVist", "inspectItemVist", data.val);
			});
			common.autocompleteMethod("diseaseItemVist", diseaseListData, function(data){
				addDiseaseTdHtml("diseaseTableVist", "diseaseItemVist", data.val);
			});
		});
	}
	$("#editModal").modal("hide");
}

/**
 * 复诊登记
 * @returns
 */
function vistDiagnosis(){
	if(_.isEmpty($("#diagnosisReferralDoctorVist").val())){
		layer.alert("转诊医生不能为空！");
		return false;
	}
	// 评价项目信息
	var inspectArray = [];
	$("#inspectTableVist input:checkbox[inspectName]").each(function(index, value){
		var inspect = {};
		inspect.inspectId = $(value).attr("value");
		inspect.inspectCode = $(value).attr("inspectCode");
		inspect.inspectName = $(value).attr("inspectName");
		if(value.checked){
			inspect.inspectStatus = "2";
		} else{
			inspect.inspectStatus = "1";
		}
		inspectArray.push(inspect);
	});
	if(!_.isEmpty(inspectArray)){
		$("#inspectListJson").val(JSON.stringify(inspectArray));
	}
	// 诊断项目信息
	var diseaseArray = [];
	diagnosisDiseaseMap.vist.forEach(function(value, key){
		var masDisease = diseaseMap.get(key);
		var disease = {};
		disease.diseaseId = key;
		disease.diseaseCode = masDisease.diseaseCode;
		disease.diseaseName = masDisease.diseaseName;
		disease.diseaseIcd10 = masDisease.diseaseIcd10;
		disease.diseaseStatus = "确诊";
		disease.type = "0";
		diseaseArray.push(disease);
	});
	if(!_.isEmpty(diseaseArray)){
		$("#diseaseListJson").val(JSON.stringify(diseaseArray));
	}
	var diagnosisJson = {
		diagnosisCustomer: $("#custIdVist").val(),
		diagnosisStatus: "1",
		diagnosisType: "2",
		diagnosisOrg: $("#deptNameSpanVist").html(),
		inspectListJson: JSON.stringify(inspectArray),
		diseaseListJson: JSON.stringify(diseaseArray)
	};
	
	// 保存信息
	layer.confirm("请确认登记信息是否正确！", function (index) {
		var url = URL.get("Platform.DIAGNOSIS_ADD");
		var param = "diagnosisJson="+JSON.stringify(diagnosisJson);
		ajax.post(url, param, dataType.json, function(data){
			$("#vistModal").modal("hide");
			datatable.table(tableOptions);
			// $("#diagCustListTable_wrapper .row").remove();
			// $("#admissionsTable_wrapper .row").remove();
		}, false, false);
	});
}

/**
 * 获取科室名称--复诊登记
 * @param deptId
 */
function getDoctorDepartmentNameVist(doctorId){
	if(!_.isEmpty(doctorId)){
		var doctor = referralMap.get(doctorId);
		$("#deptNameSpanVist").html(doctor.doctorDepartmentName);
	} else{
		$("#deptNameSpanVist").html("");
	}
}

//=====================================【患者登记结束】====================================

//=====================================【诊疗一览开始】====================================

var diagCustData;// 选中项信息
var diagCustRow;// 选中行信息
var diagCustTable;// table，注意命名不要重复
var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");

// 配置未接诊datatable
var tableOptions = {
	id: "diagCustListTable",
	form: "customerQuery",
	columnDefs: [{ type: 'chinese-string', targets: 6 }],
	columns: [
		{"data": null,"sClass": "text-center","orderable": false},
		{"data": "diagnosisId","sClass": "text-center hide","orderable": false },
		{"data": "createTime","sClass": "text-center hide","orderable": true},
		{"data": null,"sClass": "text-center hide","orderable": false,
			"render": function(data, type, row, meta) {
		 		return "<span diagnosisCustomer='"+data.diagnosisCustomer+"'></span>";
			}
		},
		{"data": "diagnosisCustSikeId","sClass": "text-center","orderable": false,
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "diagnosisCustPatientId","sClass": "text-center",
			"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "diagnosisCustName","sClass": "text-center"},
		{"data": "diagnosisCustAge","sClass": "text-center","orderable": false},
		{"data": "diagnosisGestationalWeeks","sClass": "text-center" ,
		 	"render": function(data, type, row, meta) {
			 	if(common.isEmpty(data)){
					return "";
				}
				return pregnancy.gestationalWeeksSupHtml(data);
			}
		}, 
		{"data": "diagnosisCount","sClass": "text-center","orderable": false },
		/* {"data": "diagnosisOrg","sClass": "text-center" ,"orderable": false}, */
		{"data": "diagnosisStatus","sClass": "text-center","orderable": false,
		 	render: function(data, type, row, meta) {
				return CODE.transCode("diagnosisStatus",data);
			}
		},
		{"data": null,"sClass": "text-center","orderable": false,
			"render":  function (data, type, row, meta) {
				var html = "<a onclick='toGuidePage(\""+data.diagnosisId+"\");'><font color=\"red\">接诊</a></font>";	
				if(data.diagnosisAssistantStatus == undefined || data.diagnosisAssistantStatus == null || data.diagnosisAssistantStatus == '3' ){
					html ="<a onclick='toGuidePage(\""+data.diagnosisId+"\");'>接诊</a>"; 
				}
				
//				if(data.diagnosisStatus != "1"){
//					html += " &nbsp;<a onclick='resetDiagnosis(\""+data.diagnosisId+"\");'>重新接诊</a>";
//				}
				return html;
        	}
		}
	],
	rowClick: function(data, row){
		diagCustData = data;
		diagCustRow = row;
		$("#userId").val(data.userId);
	},
	async: false,
	bSort:true,
	orderIndex: 0,
	condition : "diagCustCondition",
	isPage:false,
	scrollY: 367,
	aaSorting: [],
	loading: false,// 是否启用遮罩层
};

// 配置已接诊datatable
var admissionsTableOptions = {
	id: "admissionsTable",
	form: "admissionsQuery",
	columnDefs: [{ type: 'chinese-string', targets: 6 }],
	columns: [
		{"data": null,"sClass": "text-center","orderable": false},
		{"data": "diagnosisId","sClass": "text-center hide","orderable": false },
		{"data": "updateTime","sClass": "text-center hide","orderable": true},
		{"data": null,"sClass": "text-center hide","orderable": false,
			"render": function(data, type, row, meta) {
				return "<span diagnosisCustomer='"+data.diagnosisCustomer+"'></span>";
			}
		},
		{"data": "diagnosisCustSikeId","sClass": "text-center","orderable": false,  
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "diagnosisCustPatientId","sClass": "text-center",
			"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}
		},
		{"data": "diagnosisCustName","sClass": "text-center"},
		{"data": "diagnosisCustAge","sClass": "text-center" ,"orderable": false},
		{"data": "diagnosisGestationalWeeks","sClass": "text-center" ,
		 	"render": function(data, type, row, meta) {
		 		if(common.isEmpty(data)){
					return "";
				}
		 		return pregnancy.gestationalWeeksSupHtml(data);
			}
		},
		{"data": "diagnosisCount","sClass": "text-center","orderable": false },
		/* {"data": "diagnosisOrg","sClass": "text-center"}, */
		{"data": "diagnosisStatus","sClass": "text-center","orderable": false,
		 	render: function(data, type, row, meta) {
				return CODE.transCode("diagnosisStatus",data);
			}
		},
		{"data": null,"sClass": "text-center","orderable": false,
			"render":  function (data, type, row, meta) {
				var html = "<a onclick='toGuidePage(\""+data.diagnosisId+"\");'>诊疗</a>";
//				if(data.diagnosisStatus != "1"){
//					html += " &nbsp;<a onclick='resetDiagnosis(\""+data.diagnosisId+"\");'>重新接诊</a>";
//				}
				return html;
        	}
		}
	],
	rowClick: function(data, row){
		diagCustData = data;
		diagCustRow = row;
		$("#userId").val(data.userId);
	},
	async: false,
	bSort:true,
	orderIndex: 0,
	condition : "admissionsCondition",
	isPage:false,
	scrollY: 367,
	aaSorting: [],
	loading: false,// 是否启用遮罩层
};

/**
 * 跳转问诊页面
 * @param diagnosisId
 * @returns
 */
function toGuidePage(diagnosisId){
	var url = URL.get("Platform.DIAGNOSIS_INITAL_CHECK") + "?diagnosisId=" + diagnosisId;
	ajax.post(url, null, dataType.json, function(data){
		if(data.value){
			window.open(URL.get("Platform.PLAN_GUIDE_PAGE") + "?diagnosisId=" + diagnosisId);
		}else{
			refreshDataTable();
			common.openWindow(URL.get("Customer.DIAGNOSIS_INITAL_VIEW_GET") + "?openerType=customer&diagnosisId=" + diagnosisId);
		}
	}, false, false);
}

/**
 * 重新接诊
 * @param diagnosisId
 * @returns
 */
function resetDiagnosis(diagnosisId){
	layer.confirm("确定要执行【重新接诊】操作？", function (data) {
        if (data) {
        	var url = URL.get("Platform.DIAGNOSIS_RESET");
        	var param = "diagnosisId=" + diagnosisId;
        	ajax.post(url, param, dataType.json, function(data){
        		window.open(URL.get("Platform.PLAN_GUIDE_PAGE") + "?diagnosisId=" + data.value.diagnosisId);
        		datatable.table(tableOptions);
    			datatable.table(admissionsTableOptions);
    			// $("#diagCustListTable_wrapper .row").remove();
    			// $("#admissionsTable_wrapper .row").remove();
        	});
        }
	});
}

//=====================================【诊疗一览结束】====================================

//=====================================【自定义工具方法开始】====================================

/**
 * 添加评价项目Td
 * @param tableId
 * @param inputId
 * @param inspectId
 * @param inspectName
 */
function addTdHtml(tableId, inputId, inspect, checked){
	if($("#" + tableId + "_" + inspect.inspectId).length == 0){
		if(checked){
			$("#"+tableId).append(
				"<tr id='"+tableId+"_"+inspect.inspectId+"'><td>"+
				"	<label class='label-checkbox'><input type='checkbox' id='inspect-checkbox' inspectCode='"+inspect.inspectCode+"' inspectName='"+inspect.inspectName+"' value='"+inspect.inspectId+"' checked>"+inspect.inspectName+"</label>"+
				"	<a onclick='removeTdHtml(\""+tableId+"_"+inspect.inspectId+"\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>"+	
				"</td></tr>"
			);
		} else {
			$("#"+tableId).append(
				"<tr id='"+tableId+"_"+inspect.inspectId+"'><td>"+
				"	<label class='label-checkbox'><input type='checkbox' id='inspect-checkbox' inspectCode='"+inspect.inspectCode+"' inspectName='"+inspect.inspectName+"' value='"+inspect.inspectId+"'>"+inspect.inspectName+"</label>"+
				"	<a onclick='removeTdHtml(\""+tableId+"_"+inspect.inspectId+"\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>"+	
				"</td></tr>"
			);
		}
	} else{
		layer.alert("该营养评价项目已添加！");
	}
	if(!_.isEmpty(inputId)){
		$("#"+inputId).val("");
	}
}

/**
 * 添加疾病Td
 * @param tableId
 * @param inputId
 * @param disease
 * @returns
 */
function addDiseaseTdHtml(tableId, inputId, disease){
	if($("#" + tableId + "_" + disease.diseaseId).length == 0){
		$("#"+tableId).append(
			"<tr id='"+tableId+"_"+disease.diseaseId+"'><td style='padding-left: 12px;'>"+disease.diseaseName+
			"	<a onclick='removeTdHtml(\""+tableId+"_"+disease.diseaseId+"\")' style='float: right; color: red;'><i class='fa fa-remove fa-fw'></i></a>"+	
			"</td></tr>"
		);
		if(tableId == "diseaseTable"){
			diagnosisDiseaseMap.first.set(disease.diseaseId, disease.diseaseId);
		}
		if(tableId == "diseaseTableVist"){
			diagnosisDiseaseMap.vist.set(disease.diseaseId, disease.diseaseId);
		}
	} else{
		layer.alert("该诊断项目已添加！");
	}
	if(!_.isEmpty(inputId)){
		$("#"+inputId).val("");
	}
}

/**
 * 删除Td内容
 * @param id
 */
function removeTdHtml(id){
	$("#"+id).remove();
	var tableId = id.split("_")[0];
	if(tableId == "diseaseTable"){
		diagnosisDiseaseMap.first.delete(id.split("_")[1]);
	}
	if(tableId == "diseaseTableVist"){
		diagnosisDiseaseMap.vist.delete(id.split("_")[1]);
	}
}

/**
 * 刷新列表
 * @returns
 */
function refreshDataTable(){
	datatable.table(tableOptions);
	datatable.table(admissionsTableOptions);
	datatable.table(custTableOption);
	// $("#diagCustListTable_wrapper .row").remove();
	// $("#admissionsTable_wrapper .row").remove();
}

//=====================================【自定义工具方法结束】====================================

$().ready(function (){
	// 初始化datatables
	datatable.table(tableOptions);
	datatable.table(admissionsTableOptions);
	custTable = datatable.table(custTableOption);
	// $("#diagCustListTable_wrapper .row").remove();
	// $("#admissionsTable_wrapper .row").remove();
	
	// 必填提示
	$("#addCustomerForm").validate(custOptions);
	common.requiredHint("addCustomerForm",custOptions);
	$("#diagnosisReferralDoctorVist").addClass("inputborder");
	
	// 首诊登记弹出框关闭时触发事件
	initUserInspectItem("inspectTable", "inspectItem");
	
	// 默认评价项目弹出框关闭时触发事件
	initUserInspectItem("inspectTable2", "inspectItem2");
	$("#defaultInspectModal").on("hidden.bs.modal", function() {
		$("#inspectTable2").empty();
		initUserInspectItem("inspectTable2", "inspectItem2");
	});
	
	// 复诊评价项目弹出框关闭时触发事件
	initUserInspectItem("inspectTableVist", "inspectItemVist");
	$("#vistModal").on("hidden.bs.modal", function() {
		common.clearForm("vistModal");
		$("#deptNameSpanVist").html("");
		$("#inspectTableVist").empty();
		$("#diseaseTableVist").empty();
		initUserInspectItem("inspectTableVist", "inspectItemVist");
	});
	
	// 定义点击事件
	$("button[name='diagCustButton']").click(function(){
		if(this.id == "searchButton"){
			custTable = datatable.table(custTableOption);
			// $("#diagCustListTable_wrapper .row").remove();
			// $("#admissionsTable_wrapper .row").remove();
			$("#editModal").modal("show");
		}
		if(this.id == "addButton"){
			// 初始化
			common.clearForm("addCustomerForm");
			$("#diagnosisType").val("1");
			$("#diagnosisStatus").val("1");
			$("#diagnosisOrg").val("孕期营养门诊");
			$("#custSex").val("female");
			$("#inspectTable").empty();
			$("#diseaseTable").empty();
			$("#deptNameSpan").html("");
			initUserInspectItem("inspectTable", "inspectItem");
			
			common.modal("addModal", "shown.bs.modal", function(){
				// 评价项目自动补全
				common.autocompleteMethod("inspectItem", inspectListData, function(data){
					addTdHtml("inspectTable", "inspectItem", data.val);
				});
				// 诊断项目自动补全
				common.autocompleteMethod("diseaseItem", diseaseListData, function(data){
					addDiseaseTdHtml("diseaseTable", "diseaseItem", data.val);
				});
			});
		}
	});
	// ws初始化
	common.initWs(function(message){
		var data = $.parseJSON(message.data);
		if(!common.isEmpty(data) && data.type == "diagnosisView"){
			refreshDataTable();
		}
	});
});
