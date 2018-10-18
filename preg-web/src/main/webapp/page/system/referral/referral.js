/**
 * 验证参数设置
 */
var validator;
var referralValid = {
	rules: {
		referralName: {
			maxlength: 100,
			required: true,
			remote : {
				url : URL.get("referral.REFERRAL_CHEACK_NAME"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					referralName : function() {
						return $("#referralName").val();;
					},
					referralOldName : function() {
						return $("#referralOldName").val();;
					},
					type : function() {
						return operateType;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		referralPs: {
			maxlength: 1000
		}
	},
	messages : {
		referralName : {
			remote : "该名称已经存在，请重新输入"
		}
	},	
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm('确定要执行【保存】操作？', {
			  btn: ['确定','取消'] //按钮
			}, 
		function(){
			$(form).ajaxPost(dataType.json,function(data){
				if(operateType == "add"){
					datatable.add(referralTable, data.value);// 添加
					$("#editReferralModal").modal("hide");
				} else if(operateType == "update"){
					datatable.update(referralTable, data.value, referralRow);// 修改
					$("#editReferralModal").modal("hide");
				}
				validator.resetForm();
			});
		});
	}
};
//按钮状态
var operateType;
//配置dataTable
var referralData;
var referralRow;
var referralTable;

var referralOptions = {
	id: "referralListTable",
	form: "referralQuery",
	columns: [
		{"data": null,"sClass": "text-center",
			"render":  function (data, type, row, meta) {
		      		return "<input type='radio' name='tableRadio' value='"+data.referralId+"' />";
		      	}
		},
		{"data": "referralCode","sClass": "text-center" },
		{"data": "referralName","sClass": "text-center" }
	],
	rowClick: function(data, row){
		referralData = data;
		referralRow = row;
		$("#referralId").val(data.referralId);
		$("#id").val(data.referralId); 
	},
	condition : "referralCondition"
};

/**
 * 删除提交form请求
 */
function removeClick(referralId){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		},
	function(){
		var url = URL.get("referral.REFERRAL_REMOVE");
		var params = "referralId=" + referralId;
		ajax.post(url, params, dataType.json, function(data){
			datatable.remove(referralTable, referralRow);// 删除
			$("#id").val("");
		});
	});
};

function isChoose(id){
	if(common.isEmpty(id)){
	    layer.msg('请选择操作的记录');
	    return false;
	}else{
		return true;
	}
}

$().ready(function() {
	//添加验证
	validator =$("#editReferralForm").validate(referralValid);
	common.requiredHint("editReferralForm",referralValid);
	//加载dataTable
	referralTable = datatable.table(referralOptions);
	//按钮方法
	$("button[name='intakeTemplatePage']").click(function(){
		//获取Id
		var referralId = $("#id").val();
		if(this.id == 'addintakeTemplatePage'){
			operateType = "add";
			$("#editReferralForm").attr("action", URL.get("referral.REFERRAL_SAVE"));
			common.clearForm("editReferralForm");
			$("#editReferralModal").modal("show");
		}
		if(this.id == 'editintakeTemplatePage' && isChoose(referralId)){
			operateType = "update";
			$("#editReferralForm").attr("action", URL.get("referral.REFERRAL_UPDATE"));
			common.clearForm("editReferralForm");
			$("#editReferralForm").jsonToForm(referralData);
			$("#referralOldName").val($("#referralName").val());
			$("#editReferralModal").modal("show");
		}
		if(this.id == 'removeintakeTemplatePage' && isChoose(referralId)){
			removeClick(referralId);
		}
		if(this.id == "searchButton"){
			referralTable = datatable.table(referralOptions);
		}
	});
});