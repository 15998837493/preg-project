var modelData = {};
//查询请求action
var initQueryAction = PublicConstant.basePath+"/page/master/plan/pregnancy_course_view.jsp";
var initAddAction = PublicConstant.basePath + URL.Master.ADD_INIT_PREGNANCYCOURSE;
var initUpdateAction = PublicConstant.basePath + URL.Master.UPDATE_INIT_PREGNANCYCOURSE;
var deleteAction = PublicConstant.basePath + URL.Master.PREGNANCYCOURSE_REMOVE;
var queryOptionAction = PublicConstant.basePath + URL.Master.QUERY_DETAIL_COURSE;
var initConfigAction = PublicConstant.basePath + URL.Master.ADD_INIT_QUESTION_CONFIG;
/**
 * 验证参数设置
 */
var addOptions = {
	rules: {
		pregName: {
			maxlength: 80,
			required:true
		},
		pregId: {
			maxlength: 64,
			required:true,
			remote : {
				url : URL.get("Master.PREGNANCYCOURSE_VALIDATE"),// 后台处理程序
				type : "post", // 数据发送方式
				dataType : "json", // 接受数据格式
				data : { // 要传递的数据，默认已传递应用此规则的表单项
					id : function() {
						return $("#saveForm").find("input[name='pregId']").val();
					},
					random : function() {
						return Math.random();
					}
				}
			}
		},
		pregWeekBegin: {
			max:40,
			pregWeekCourse : true,
			intege3:true
		},
		pregWeekEnd: {
			pregWeekCourse : true,
			max:40,
			intege1:true
		}
	},	
	messages : {
		pregId : {
			remote : "该编码已经存在，请重新输入"
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
            			datatable.add(courseTable, data.value);// 添加
            			checkedRow = null;
        				checkedData = null;
        				$("#id").val("");
            		}
					$("#saveModal").modal("hide");
				});
            }
        });
	}
};
var update = {
		rules: {
			pregName: {
				maxlength: 80,
				required:true
			},
			pregWeekBegin: {
				max:40,
				pregWeekCourseUpdate : true,
				intege3:true
			},
			pregWeekEnd: {
				pregWeekCourseUpdate : true,
				max:40,
				intege1:true
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
						if(data.result){
							datatable.update(courseTable, data.value,checkedRow);// 修改
						}
						$("#updateModal").modal("hide");
					});
				}
			});
		}
};
var ajaxOptions = {
		rules: {
			problemContent: {
				required:true
			},
			problemType: {
				required:true
			},
			problemSex: {
				required:true
			},
			optionContent: {
				required:true
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
					saveData();
				}
			});
		}
};
redirectPage = function(actionName){
	if(actionName == 'query'){
		common.pageForward(initQueryAction);
	}else if(actionName == 'init_add'){
		common.pageForward(initAddAction);
	}else if(actionName == 'query_'){
		common.pageForward(initAddAction);
	}else{
		common.pageForward(actionName);
	}
};
openModel = function(){
	itemModel.openItemModel();
};
function getPregCourseDetail(id,name){
	common.pageForward(URL.get("Master.PREGNANCYCOURSE_DETAIL_VIEW")+"?id="+id+"&name="+name);
}
//pageList(data,start,end)写法固定
function optionPageList(data,start,end){
	for(var i=start;i<end;i++){
		var option = data[i];
		var optionSex=option.optionSex;
		if(common.isEmpty(optionSex)){
			problemSex = '————';
		}else if(optionSex=='all'){
			optionSex="不限";
		}else if(optionSex=='female'){
			optionSex="女性";
		}else if(optionSex=='male'){
			optionSex="男性";
		}
		
		
		$("#option_body").append(
			"<tr row-index='"+i+"'>"+
			"	<td class='text-center'>" + option.optionContent + "</td>" +
			"	<td class='text-center'>" + optionSex + "</td>" +
			"	<td class='text-center'>" +
			"		<button class='btn btn-primary btn-sm' onclick='resetParam(\""+option.paramId+"\")'><i class='fa fa-cog fa-fw'></i> 上移</button>"+
			"		<button class='btn btn-primary btn-sm' onclick='resetParam(\""+option.paramId+"\")'><i class='fa fa-cog fa-fw'></i> 下移</button>"+
			"		<button class='btn btn-primary btn-sm' onclick='resetParam(\""+option.paramId+"\")'><i class='fa fa-cog fa-fw'></i> 编辑</button>"+
			"		<button class='btn btn-primary btn-sm' onclick='resetParam(\""+option.paramId+"\")'><i class='fa fa-cog fa-fw'></i> 删除</button>"+
			"	</td>" +			
			"</tr>"
		);
	}
	//支持选择行事件
	$("#option_body tr").on("click",function(){
		var row_index = $(this).attr("row-index");
		var chooseData = data[row_index];
		console.log('对象：',chooseData);
		//选中
		$("input[name='chooseRadio'][value="+row_index+"]").attr("checked",'checked');
		var id=chooseData.problemId;
		console.log(id);
		$("#id").val(id);
	});
}
/**
 * form提交成功回调函数
 */
function submitSuc(data){
	common.btnDisable(false);
    layer.alert(data.message, function(){
    	//操作成功返回查询页
    	if(data.result){
    		redirectPage('query');
        }
    });
};
/**
 * form提交成功回调函数
 */
function submitProblemSuc(data,id){
	common.btnDisable(false);
	layer.alert(data.message, function(){
		//操作成功返回查询页
		if(data.result){
			common.pageForward(initConfigAction + "?id=" + id);
		}
	});
};

function optionDatatable(){
	$('#option_body').datatable({
		formName : 'optionQuery',
		pageList : function(data,start,end){
			optionPageList(data,start,end);
		}
	});
}
/**
 * 删除提交form请求
 */
function removeClick(id){
	  // common.btnDisable(true);
       layer.confirm("确定对选中内容执行【删除】操作？", function (data) {
           if (data) {
        	    var url = URL.get("Master.PREGNANCYCOURSE_REMOVE");
	   			var params = "id="+$("#id").val();
	   			ajax.post(url,params,dataType.json,function(data){
	   				layer.alert(data.message);
	   				if(data.result){
	   					datatable.remove(courseTable,checkedRow);
	   					checkedRow = null;
	   					checkedData = null;
	   					$("#id").val("");
	   				}
	   			});
           }
       });
};
