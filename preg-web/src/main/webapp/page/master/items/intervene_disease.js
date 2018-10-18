/**
 * 验证参数设置
 */
var validator;
var diseaseVaild = {
	rules: {
		/*diseaseCode: {
			required:true,
			remote : {
				url : URL.get("item.DISEASECODE_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					diseaseCode : function(){
						return $("#diseaseCode").val();
					},
					diseaseOldCode : function(){
						return $("#diseaseOldCode").val();
					},
					type : function(){
						return operateType;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},*/
		diseaseType:{
			required:true,
		},
		diseaseName: {
			maxlength: 50,
			required:true,
			remote : {
				url : URL.get("item.DISEASENAME_VALID"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					diseaseName : function(){
						return $("#diseaseName").val();
					},
					diseaseOldName : function(){
						return $("#diseaseOldName").val();
					},
					type : function(){
						return operateType;
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		/*diseaseTreatmentItem:{
			required:true
		},*/
		diseaseIcd10:{
			maxlength: 30,
		}
	},	
	messages : {
		/*diseaseCode : {
			remote : "该编码已经存在，请重新输入"
		},*/
		diseaseName : {
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
					datatable.add(diseaseTable, data.value);// 添加
					$("#editDiseaseModal").modal("hide");
				} else if(operateType == "update"){
					datatable.update(diseaseTable, data.value, diseaseRow);// 修改
					$("#editDiseaseModal").modal("hide");
				}
				validator.resetForm();
			});
		});
	}
};
redirectPage = function(actionName){
	if(actionName == 'query'){
		common.pageForward(initQueryAction);
	}else if(actionName == 'init_add'){
		common.pageForward(initAddAction);
	}else{
		common.pageForward(actionName);
	}
};
//pageList(data,start,end)写法固定
function pageList(data,start,end){
	for(var i=start;i<end;i++){
		var interveneDisease = data[i];
		var diseaseTreatmentItem=interveneDisease.diseaseTreatmentItem;
		if(common.isEmpty(diseaseTreatmentItem)){
			diseaseTreatmentItem="否";
		}else if(diseaseTreatmentItem=="0"){
			diseaseTreatmentItem="否";
		}else{
			diseaseTreatmentItem="是";
		}
		$("#t_body").append(
			"<tr row-index='"+i+"'>"+
			"<td class='text-center'><input name='chooseRadio' type='radio' value='"+i+"' /></td>" +
			"	<td class='text-center'>" + interveneDisease.diseaseCode + "</td>" +
			"	<td class='text-center'>" + (interveneDisease.diseaseName || "————") + "</td>" +
			"	<td class='text-center'>" + (CODE.transCode("DISEASETYPE", interveneDisease.diseaseType) || "————") + "</td>" +
			"	<td class='text-left'>" + diseaseTreatmentItem + "</td>" +
			"</tr>"
		);
	}
	//支持选择行事件
	$("#t_body tr").on("click",function(){
		var row_index = $(this).attr("row-index");
		var chooseData = data[row_index];
		//选中
		$("input[name='chooseRadio'][value="+row_index+"]").attr("checked",'checked');
		var id=chooseData.diseaseCode;
		$("#id").val(id);
	});
}
/**
 * form提交成功回调函数
 */
function submitSuc(data){
	common.btnDisable(false);
	layer.alert(data.message, {
		},
	function(){
		//操作成功返回查询页
		if(data.result){
    		redirectPage('query');
        }
	});
};

/**
 * 删除提交form请求
 */
function removeClick(diseaseId){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		}, 
	function(){
		var url = URL.get("item.REMOVE_INTERVENEDISEASE");
		var params = "diseaseId=" + diseaseId;
		ajax.post(url, params, dataType.json, function(data){
			datatable.remove(diseaseTable, diseaseRow);// 删除
			$("#id").val("");
		});
	});
};