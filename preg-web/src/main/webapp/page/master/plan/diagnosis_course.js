/**
 * 验证参数设置
 */
var updatePointOptions = {
		rules: {
//			pointOrder:{
//				required:true,
//				maxlength:20
//			},
			pointSubclass:{
				required:true
			},
			pointDesc:{
				required:true,
				maxlength:2000
			}
		},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function (data) {
            if (data) {
            	$(form).ajaxPost(dataType.json,function(data){
            		layer.alert(data.message);
            		if(data.result){
            			datatable.update(pointTable, data.value,checkedRow);// 修改
            		}
					$("#updateModal").modal("hide");
            	});
            }
        });
	}
};

/**
 * 验证参数设置
 */
var appPointOptions = {
		rules: {
			pointOrder:{
				required:true,
				maxlength:20
			},
			pointSubclass:{
				required:true
			},
			pointDesc:{
				required:true,
				maxlength:2000
			}
		},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
	submitHandler: function(form) {
		layer.confirm("确定要执行【保存】操作？", function (data) {
            if (data) {
            	$(form).ajaxPost(dataType.json,function(data){
            		layer.alert(data.message);
            		if(data.result){
            			datatable.add(pointTable, data.value);// 添加
            			checkedRow = null;
        				checkedData = null;
        				$("#id").val("");
            		}
					$("#addModal").modal("hide");
            	});
            }
        });
	}
};

/**
 * 删除提交form请求
 */
function removeClick(id){
       layer.confirm("确定对选中内容执行【删除】操作？", function (data) {
           if (data) {
        	    var url = URL.get("Master.REMOVE_INTERVENEPOINTS");
	   			var params = "id="+$("#id").val();
	   			ajax.post(url,params,dataType.json,function(data){
	   				layer.alert(data.message);
	   				if(data.result){
	   					datatable.remove(pointTable,checkedRow);
	   					checkedRow = null;
	   					checkedData = null;
	   					$("#id").val("");
	   				}
	   			});
           }
       });
};

function chooseDiseaseModal(formName){
	$("#diseaseModal").modal("show");
	if(formName=='addForm'){
		$("input:checkbox[name='diseaseIdList']").attr("checked",false);
	}
	$("#confirmBtn").one("click",function(){
		getDisease(formName);
	});
}

function getDisease(formName){
	 var checkedValues="";
	 var checkedValueNames="";
	    $("input:checkbox[name='diseaseIdList']").each(function(){ 
	        if($(this).attr("checked")){
	        		 checkedValues += $(this).val()+",";
	        		 checkedValueNames += $(this).next().text()+",";
	        }
	    });
	    if(common.isEmpty(checkedValues)){
	    	checkedValues="";
	    }else{
	    	checkedValues=checkedValues.substring(0, checkedValues.length-1);
	    }
	    if(common.isEmpty(checkedValueNames)){
	    	checkedValueNames="";
	    }else{
	    	checkedValueNames=checkedValueNames.substring(0, checkedValueNames.length-1);
	    }
	    $("#"+formName).find("input[name='interveneDiseaseIds']").val(checkedValues);
	    $("#"+formName).find("textarea[name='interveneDiseaseNames']").val(checkedValueNames);
	    $("input:checkbox[name='diseaseIdList']").attr("checked",false);
	    $("#diseaseModal").modal("hide");
}

