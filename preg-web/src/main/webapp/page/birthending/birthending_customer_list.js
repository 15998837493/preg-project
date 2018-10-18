var checkedData;// 选中项信息
var checkedRow;// 选中行信息
var customerTable;// table，注意命名不要重复

/**
 * 分娩登记参数验证设置
 */
var customerOptions = {
	rules: {
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
					random : function() {
						return Math.random();
					}
				}
			}
		},
		custPatientId: {
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
					random : function() {
						return Math.random();
					}
				}
			}
		},
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
			remote: "身份证号已存在，请重新填写"
		}
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function (index) {
			var isAllowOpen = true;
			$(form).ajaxPost(dataType.json,function(data){
				common.modal("editItemModal", common.modalType.hidden, function(){
					layer.close(index);
					// 处理非保存操作打开信息录入页面
					if(isAllowOpen){
						window.open(URL.get("BirthEnding.BIRTHENDING_ADD")+"?custId="+data.value.custId);
					}
					isAllowOpen = false;
				});
			}, false);
        }, function(index){
			layer.close(index);
		});
	}
};

/**
 * 分娩结局用户录入列表
 */
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
		 		return "<a onclick='getDiagnosisHistoryInfo(\""+row.custId+"\")' style='cursor:pointer;'>" + data + "</a>";
			}
		},
		{"data": "birthDate","sClass": "text-center",
		 	"render": function(data, type, row, meta) {
		 		return data?data:"————";
			}},
		{"data": "custIcard","sClass": "text-center","orderable": false,},
		{"data": "custPhone","sClass": "text-center","orderable": false,},
		{"data": null,"sClass": "text-center","orderable": false,
			"render": function(data, type, row, meta) {
				return "<a onclick='getBirthEndingInfo(\""+data.custId+"\")' style='cursor:pointer;'>分娩结局录入</a>";
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

/**
 * 系统参数设置 页面初始化
 */
$(function () {
	//分娩登记校验
	$("#editItemForm").validate(customerOptions);
	common.requiredHint("editItemForm",customerOptions);
	
	// 按钮点击事件
	$("button[name='operateButton']").click(function(){
		if(this.id=="registerButton"){
			operateType="add";
			common.clearForm("editItemForm");
			$("#editItemForm").attr("action", URL.get("Customer.ADD_CUST_ONLY"));
			$("#editItemModal").modal("show");
			$("#custSex").val("female");
		}
		if(this.id=="searchButton"){
			customerTable = datatable.table(tableOptions);
		}
	});
});


/**
 * 跳转到用户就诊记录信息页面
 * @param custId
 */
function getDiagnosisHistoryInfo(custId){
	common.openWindow(URL.get("Platform.DIAGNOSIS_RECORD") + "?custId=" + custId);
}

/**
 * 获取分娩结局录入信息
 * @param custId
 */
function getBirthEndingInfo(custId){
	window.open(URL.get("BirthEnding.BIRTHENDING_ADD") + "?custId=" + custId);
}
