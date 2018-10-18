//按钮参数
var operateType;
/** 默认数据*/
var choiceData={optionOrder:1,optionContent:"",optionSex:"all"};
//选中项信息
var qstCofigData;
// 选中行信息
var qstCofigRow;
// table对象
var qstCofigTable;
// 列表配置信息
var qstCofigTableOptions = {
	id : "qstCofigTable",
	form : "qstCofigQueryForm",
	columns : [
			{
				"data" : null,
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return "<input type='radio' name='tableRadio'/>";
				}
			},
			{
				"data" : "problemOrder",
				"sClass" : "text-center"
			},
			{
				"data" : "problemContent",
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return data || "————";
				}
			},
			{
				"data" : "problemType",
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					var problemType = data;
					if (common.isEmpty(problemType)) {
						problemType = '————';
					} else if (problemType == '1') {
						problemType = "单选题";
					} else if (problemType == '2') {
						problemType = "多选题";
					} else if (problemType == '3') {
						problemType = "填空题";
					} else if (problemType == '4') {
						problemType = "是非题";
					}
					return problemType;
				}
			},
			{
				"data" : "problemSex",
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					var problemSex = data;
					if (common.isEmpty(problemSex)) {
						problemSex = '————';
					} else if (problemSex == 'all') {
						problemSex = "不限";
					} else if (problemSex == 'female') {
						problemSex = "女性";
					} else if (problemSex == 'male') {
						problemSex = "男性";
					}
					return problemSex;
				}
			},
			{
				"data" : "problemRequired",
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					var problemRequired = data;
					if (common.isEmpty(problemRequired)) {
						problemRequired = '————';
					} else if (problemRequired == '1') {
						problemRequired = "必答";
					} else if (problemRequired == '0') {
						problemRequired = "非必答";
					}
					return problemRequired;
				}
			},
			{
				"data" : "problemHint",
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return data || '————';
				}
			},
			{
				"data" : null,
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return "<button class='btn btn-default' type='button'" + " onclick=moveClick('" + data.problemId + "','-1')>"
							+ " <i class='fa fa-sort-asc'></i>上移</button>" + " <button class='btn btn-default' type='button' "
							+ " onclick=moveClick('" + data.problemId + "','1')>" + " <i class='fa fa-sort-desc'></i>下移</button>";
				}
			} ],
	rowClick : function(data, row) {
		qstCofigData = data;
		qstCofigRow = row;
		$("#editFormProblemId").val(data.problemId);
	},
	loading : false,// 是否启用遮罩层
	condition : "qstCofigCondition"
};
// 选中项信息
var qstModalData;
// 选中行信息
var qstModalRow;
// table对象
var qstModalTable;
// 列表配置信息
var qstModalTableOptions = {
	id : "qstModalTable",
	form : "qstModalQueryForm",
	columns : [
			{
				"data" : "problemContent",
				"sClass" : "text-center"
			},
			{
				"data" : null,
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					var problemType = data.problemType;
					if (common.isEmpty(problemType)) {
						problemType = '————';
					} else if (problemType == '1') {
						problemType = "单选题";
					} else if (problemType == '2') {
						problemType = "多选题";
					} else if (problemType == '3') {
						problemType = "填空题";
					} else if (problemType == '4') {
						problemType = "是非题";
					}
					return problemType;
				}
			},
			{
				"data" : null,
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					var problemSex = data.problemSex;
					if (common.isEmpty(problemSex)) {
						problemSex = '————';
					} else if (problemSex == 'all') {
						problemSex = "不限";
					} else if (problemSex == 'female') {
						problemSex = "女性";
					} else if (problemSex == 'male') {
						problemSex = "男性";
					}
					return problemSex;
				}
			},
			{
				"data" : null,
				"sClass" : "text-center",
				"render" : function(data, type, row, meta) {
					return "<button class='btn btn-primary btn-xs' type='button' onclick=saveMemData('" + data.problemId
							+ "')><i class='fa fa-plus fa-fw'></i></button>";
				}
			} ],
	rowClick : function(data, row) {
		qstModalData = data;
		qstModalRow = row;
	},
	condition : "qstModalCondition"
};
// 保存问题库里边的内容
function saveMemData(problemId) {
	var url = URL.get("Question.ADD_LIBPROBLEM_QUESTION");
	var params = "problemId=" + problemId + "&questionId=" + $("#questionId").val();
	ajax.post(url, params, dataType.json, function() {
		qstCofigTable = datatable.table(qstCofigTableOptions);
	});
};
/**
 * 移动标识
 */
function moveClick(problemId, flag) {
	layer.confirm("确定对选中内容执行移动操作？", function() {
		$("#moveFormFlag").val("");
		$("#moveFormFlag").val(flag);
		$("#editFormProblemId").val("");
		$("#editFormProblemId").val(problemId);
		$("#editForm").attr("action", URL.get("Question.MOVE_QUESTION_PROBLEM"));
		$("#editForm").ajaxPost(dataType.json, function(data) {
			qstCofigTable = datatable.table(qstCofigTableOptions);
		});
	});
};
function submitSucRedirectConfigPage(data) {
	layer.alert(data.message, function() {
		qstCofigTable = datatable.table(qstCofigTableOptions);
	});
};

/** 选项属性可编辑表格*/
var reportOption={
	id:'reportProblemTable',
	method: 'get',
	idField:"optionOrder",
	uniqueId:"optionOrder", 
	editable:true,//开启编辑模式
	clickToSelect: true,
	columns: [
	       {field: "optionOrder",
	    	title: '排序号',
	        align:"center",
	        edit:false,
	    },{field: 'optionContent',
	        title: '选项名称',
	        align:"center",
	        required:true
	    },{field:"optionSex",
	    	edit:{
				type:'select',//下拉框
				data:[{id:'all',text:'不限'},{id:'male',text:'男性'},{id:'female',text:'女性'}],
    			valueField:'id',
    			textField:'text',
    			onSelect:function(val,rec){
    				console.log(val,rec);
				  }
				},
			title:"性别",
			align:"center",
			width:"200px"
		 },{
	        title: '操作',
	        formatter:function(value,row,rowIndex){
	        	var upHtml = '<a href="javascript:void(0);"   name="up'+rowIndex+'" onclick="upRow('+rowIndex+')">上移</a> <a href="javascript:void(0);" name="down'+rowIndex+'" onclick="downRow('+rowIndex+')">下移</a> <a href="javascript:void(0);" <a href="javascript:void(0);" onclick="removeRow('+rowIndex+')" class="up">删除</a>';
	        	return upHtml;
	        },
	        edit:false,
	        align:"center"
    		}		    
	    ],
	    data:choiceData
	};	

/** 填空属性可编辑表格*/
var fillOption={
	id:'fillProblemTable',
	method: 'get',
	idField:"optionOrder",
	uniqueId:"optionOrder", 
	editable:true,//开启编辑模式
	clickToSelect: true,
	columns: [
	       {title: '排序号',
		    field: "optionOrder",
	        align:"center",
	        edit:false,
	    },{field: 'optionContent',
	        title: '默认内容',
	        align:"center",
	        required:true
	    },{field:"optionValidate",
	    	edit:{
				type:'select',//下拉框
				data:[{id:'^[1-9]\d*$',text:'数字验证'}],
    			valueField:'id',
    			textField:'text',
    			onSelect:function(val,rec){
    				console.log(val,rec);
				  }
				},
			title:"验证",
			align:"center",
			width:"200px"
		 }	    
	    ],
	data:choiceData
};	

/** modal绑定关闭保存事件*/
function editModalOnhHideBs(){
	$('#editProblemModal').on('hide.bs.modal', function () {
		validator = $("#editProblemForm").validate();
		if(!validator.form()){
			layer.confirm("是否放弃填写表单内容", {
				  btn: ["是","否"] //按钮
				}, 
			function(){
				layer.closeAll();
			},
			function(){
				layer.msg("请补全信息！");
				$('#editProblemModal').modal("show");
			});
			return;
		}
		layer.confirm("确定对选中内容执行【保存】操作？", {
			  btn: ["确定","取消"] //按钮
			}, 
		function(){
			saveData();
		});
	});
}

//保存数据
function saveData(){
	var url;
	if(operateType == "add"){
		url = URL.get("Question.ADD_QUESTIONS_PROBLEM");
	}else{
		url = URL.get("Question.UPDATE_QUESTION_PROBLEM");
	}
	//将表单序列化为json
	var fromParams = $('#editProblemForm').serializeObject();
	fromParams.questionId=$("#questionId").val();
	var optionlistData;
	if($("#problemType").val()==1 || $("#problemType").val()==2){
		optionlistData=$('#reportProblemTable').bootstrapTable('getData');
	}else{
		optionlistData=$('#fillProblemTable').bootstrapTable('getData');
	}
	//去掉空的选项   (parseInt(fromParams.problemType) < 3 如果problemType<3是选择题 需要做去掉空选项处理)
	$(optionlistData).each(function(index,obj){
		if(parseInt(fromParams.problemType) < 3 &&  common.isEmpty(obj.optionContent)){
			//删除数组中一个数据
			optionlistData.splice(index, 1);
		}
	});
 	var params = "fromParams="+JSON.stringify(fromParams)+"&optionParams="+JSON.stringify(optionlistData);
 	ajax.post(url, params, dataType.json,function(data){
	   if(operateType == "add"){
		   datatable.add(qstCofigTable, data.value);// 添加
		}else{
		   datatable.update(qstCofigTable, data.value, qstCofigRow);// 修改
		}
 	});
}
//初始化列表数据
function initData(problemId){
	var url = URL.get("Question.QUERY_QUESTION_OPTION");
	var params = "problemId=" + problemId;
	ajax.post(url, params, dataType.json, function(result){
		if(result.data==null){
	    	 if($("#problemType").val()==1 || $("#problemType").val()==2){
	    		 $('#reportTable').bootstrapTable('load', choiceData);
	       	 }
	    }else{
	      	 if($("#problemType").val()==1 || $("#problemType").val()==2){
	    		 $('#reportProblemTable').bootstrapTable('load', result.data);
	       	 }else{
	       		 $('#fillProblemTable').bootstrapTable('load', result.data);
	       	 }  
	   }
	});
}
// 頁面初始化方案
$().ready(function() {
	//问题种类绑定事件
	problemTypeBindChange();
	//初始化下拉列表
	common.initCodeSelect("PROBLEMCATEGORY", "PROBLEMCATEGORY","problemCategory");
	//加入必填项提示
    validator= $("#editProblemForm").validate(intakeTemplateOptions);
    common.requiredHint("editProblemForm", intakeTemplateOptions);
	//初始化可编辑问题选项/问题内容表格
    common.initEditTable(reportOption);
    common.initEditTable(fillOption);
    //加载问题表格
	qstCofigTable = datatable.table(qstCofigTableOptions);
	//绑定modal关闭保存事件
    editModalOnhHideBs();
	// 按鈕初始化
	$("button[name='operateButton']").click(function() {
		var problemId = $("#editFormProblemId").val();
		if (this.id=='search'){
			datatable.table(qstCofigTableOptions);
		}
		if (this.id == 'add') {
			//设置按钮状态
			operateType = "add";
			//清空表单内容
			common.clearForm("editProblemForm");
			//清空选项表格
			$("#reportProblemTable").bootstrapTable('removeAll');
	    	$("#fillProblemTable").bootstrapTable('removeAll');
	    	//设置readio选中
	    	$("[name='problemRequired']")[0].checked = true;
	    	$("#fill").hide();
			$("#choice").show();
			$("#editProblemModal").modal("show");
			//common.pageForward(URL.get("Question.ADD_INIT_QUESTION_PROBLEM") + "?questionId=" + $("#questionId").val());
		}
		if (this.id == 'edit' && common.isChoose(problemId)) {
			//设置按钮状态
			operateType = "update";
		    if(qstCofigData.problemType == 3){
				$("#fill").show();
				$("#choice").hide();
		    }else{
				$("#fill").hide();
				$("#choice").show();	
		    }
			//清空表单内容
			common.clearForm("editProblemForm");
			//清空选项表格
			$("#reportProblemTable").bootstrapTable('removeAll');
	    	$("#fillProblemTable").bootstrapTable('removeAll'); 
	    	//给表单赋值
			$("#editProblemForm").jsonToForm(qstCofigData);
			//设置readio选中
			$("[name='problemRequired']").each(function(index,obj){
				if(obj.value == qstCofigData.problemRequired){
					obj.checked = true;
				}
			});
			//设置适合性别
			$("[name='problemSex']").val(qstCofigData.problemSex);
			//给选项表格赋值
			initData(problemId);
			$("#editProblemModal").modal("show");
			//common.pageForward(URL.get("Question.UPDATE_INIT_QUESTION_PROBLEM") + "?problemId=" + problemId);
		}
		if (this.id == 'remove' && common.isChoose(problemId)) {
			layer.confirm("确定对选中内容执行【删除】操作？", function() {
				$("#editForm").attr("action", URL.get("Question.QUESTION_PROBLEM_REMOVE"));
				$("#editForm").ajaxPost(dataType.json, submitSucRedirectConfigPage);
			});
		}
		if (this.id == 'config' && common.isChoose(problemId)) {
			var questionId = $("#questionId").val();
			configParentModel.openconfigParentModel({
				"problemId" : problemId,
				"questionId" : questionId
			});
		}
		if (this.id == 'problem' && common.isChoose(problemId)) {
			qstModalTable = datatable.table(qstModalTableOptions);
			$('#itemModel').modal('show');
		}
	});
	
	$("#problemList").click(function(){
		$("#checkOption").html("");
		var url = URL.get("Question.QUERY_PROBLEM_OPTION_LIST");
		//根据问题的id查询问题的选项
		if($("#problemList").val()){
			var params = "problemParentId="+ $("#problemList").val()+"&problemCurrentId="+ $("#editFormProblemId").val();
			ajax.post(url, params, dataType.json, function(data){
				if(!common.isEmpty(data.value)){
					$(data.value.questionProblemOptionsList).each(function(index, option){
						if(!common.isEmpty(data.value.questionProblemsVo.problemRule) && data.value.questionProblemsVo.problemRule.indexOf(option.problemOptionId) >= 0){
							$("#checkOption").append("<div class='checkbox'> <label> <input type='checkbox' name='checkBoxModel' checked='checked' value="+option.problemOptionId+">"+option.optionContent+ "</label> </div>");	
						}else{
							$("#checkOption").append("<div class='checkbox'> <label> <input type='checkbox' name='checkBoxModel' value="+option.problemOptionId+">"+option.optionContent+ "</label> </div>");	
						}
					});
				}
			},false,false);
		}else{
			$("#checkOption").html("<h5>未选择关联问题！</h5>");
		}
	});
});

var configParentModel = {};

configParentModel.openconfigParentModel = function(data){
	problemOption(data.questionId,data.problemId);
	$('#configParentModel').modal('show');
};
/**
 * 初始化页面
 */
function problemOption(questionId,problemId){
	$("#checkOption").html("");
	$("#problemList").html(" <option value=''>==请选择==</option> ");
	var url = URL.get("Question.QUERY_PROBLEM_LIST");
	var params = "questionId="+questionId+"&problemId="+problemId;
	
	ajax.post(url, params, dataType.json, function(data){
		var questionProblemsVo = data.value.questionProblemsVo;
		if(!common.isEmpty(questionProblemsVo)){
			$("#problemIdmodel").val(questionProblemsVo.problemId);
			$("#ProblemContentModel").val(questionProblemsVo.problemContent);
			
			$(data.value.questionProblemsList).each(function(index, problem){
				$("#problemList").append("<option value="+problem.problemId+">"+problem.problemContent+'</option>');
			});
			if(!common.isEmpty(questionProblemsVo.problemParentId)){
				$("#problemList").val(questionProblemsVo.problemParentId);
			}
		}
		if(!common.isEmpty(data.value.questionProblemOptionsList) &&  !common.isEmpty(questionProblemsVo) && !common.isEmpty(questionProblemsVo.problemRule)){
			$(data.value.questionProblemOptionsList).each(function(index, option){
				if(questionProblemsVo.problemRule.indexOf(option.problemOptionId) >= 0){
					$("#checkOption").append("<div class='checkbox'> <label> <input type='checkbox' name='checkBoxModel' checked='checked' value="+option.problemOptionId+">"+option.optionContent+ "</label> </div>");	
				}else{
					$("#checkOption").append("<div class='checkbox'> <label> <input type='checkbox' name='checkBoxModel' value="+option.problemOptionId+">"+option.optionContent+ "</label> </div>");	
				}
			});
			
		}else{
			$("#checkOption").html("<h5>未选择关联问题！</h5>");
		}
	}, false, false);
};

function saveCheckBox(){
    var checkedValues="";
    $("input[name='checkBoxModel']:checkbox").each(function(){ 
        if($(this).attr("checked")){
        	checkedValues += $(this).val()+",";
        }
    });
	var url = URL.get("Question.SAVE_PARENT_NODE");
	var parentProblemId=$("#problemList").val();
	var problemId=$("#problemIdmodel").val();
	var params = "parentProblemId="+parentProblemId+"&problemId="+problemId+"&checkedValues="+checkedValues;
	ajax.post(url, params, dataType.json, function(data){
		$('#configParentModel').modal('hide');
	});
}

/***********************************************************工具方法**************************************************************************************************************/
/** 添加行*/
function addRowbtn(id){
	var data = {};
	var addrow=$('#'+id).bootstrapTable('getData');
	for(var i=0;i<addrow.length;i++){
		if(common.isEmpty(addrow[i].optionContent)){
			layer.alert("选项名称不能为空！");
			return;
		};
	}
	var optionOrder=addrow.length+1;
	data.optionOrder=optionOrder;
	$('#'+id).bootstrapTable('append',data);
	
};

/** 删除行*/
function removeRow(rowIndex){
	$('#reportProblemTable').bootstrapTable('removeRow',rowIndex);
}	

/** 列向上移动*/
function upRow(index){
	//如果是第一行不显示向上按钮
	//如果是最后一行不显示向下按钮
	var allRow= $('#reportProblemTable').bootstrapTable('getData');
	var rowlength=allRow.length;
	if(index==0){
		layer.alert("已经是最上层了！");
		return;
	}
	//上一行
	var prevRowNo=index;
	//当前行
	
	var rowNo=(index+1);
	var cData=$('#reportProblemTable').bootstrapTable('getRowByUniqueId',rowNo);
	var prevData=$('#reportProblemTable').bootstrapTable('getRowByUniqueId',prevRowNo);
	cData.optionOrder=prevRowNo;
	prevData.optionOrder=rowNo;
	//删除这两行数据
	//判断行号是不是0如果是0，则app两条数据，如果不是，在上行的基础上追加两行数据
	$('#reportProblemTable').bootstrapTable('removeByUniqueId', rowNo);
	$('#reportProblemTable').bootstrapTable('removeByUniqueId', prevRowNo);
	if(rowNo==rowlength){
		$('#reportProblemTable').bootstrapTable('append',cData);
		$('#reportProblemTable').bootstrapTable('append',prevData);
	}else{
		$('#reportProblemTable').bootstrapTable('insertRow',{index:index-1,row:prevData});
		$('#reportProblemTable').bootstrapTable('insertRow',{index:index-1,row:cData});
	}	
}

/** 列向下移动*/
function downRow(index){
	//如果是第一行不显示向上按钮
	var allRow= $('#reportProblemTable').bootstrapTable('getData');
	if(index+1==allRow.length){
		layer.alert("已经是最下层了！");
		return;
	}
	var currentRowNo=index+1;
	var nextRowNo=index+2;
	var currentData=$('#reportProblemTable').bootstrapTable('getRowByUniqueId',currentRowNo);
	currentData.optionOrder=(nextRowNo);
	var nextData=$('#reportProblemTable').bootstrapTable('getRowByUniqueId',nextRowNo);
	nextData.optionOrder=(currentRowNo);
	//删除这两行数据
	//判断行号是不是0如果是0，则app两条数据，如果不是，在上行的基础上追加两行数据
	$('#reportProblemTable').bootstrapTable('removeByUniqueId', currentRowNo);
	$('#reportProblemTable').bootstrapTable('removeByUniqueId', nextRowNo);
	
	if(index==0){
		$('#reportProblemTable').bootstrapTable('prepend',currentData);
		$('#reportProblemTable').bootstrapTable('prepend',nextData);
	}else{
		$('#reportProblemTable').bootstrapTable('insertRow',{index:index,row:currentData});
		$('#reportProblemTable').bootstrapTable('insertRow',{index:index,row:nextData});
	}
} 
    

/** 恢复tr，使之处于不可编辑状态*/
function recover(that){
	var isModi = false;//判断行值是否变动过
	if(that.prevEditRow != null){
		that.prevEditRow.find('td').closest('td').siblings().html(function(i,html){
			$(this).attr('style',$(this).data('style'));
			var textVal = $(this).find('input[type="text"]').val();
			var hiddenVal = $(this).find('input[type="hidden"]').val();
			if(typeof $(this).find('input[type="text"]').bootstrapSelect('getText') != 'object'){
				$(this).find('input[type="text"]').bootstrapSelect('destroy');
			}
			
			if(textVal != undefined){
				if($(this).data('oldVal') != (hiddenVal?hiddenVal:$.trim(textVal)) && $(this).data('field')) {
					that.data[that.prevEditRow.data('index')][$(this).data('field')] = hiddenVal?hiddenVal:$.trim(textVal);
					isModi = true;
				}
				if(that.columns['column'+i].edit.required == true){
					if(textVal == null || textVal == ''){
						that.enableAppend = false;
						return '<span style="color:red;">必填项不能为空</span>';
					}
				}
				return $.trim(textVal);
			}
    	});
		//新值跟旧值不匹配证明被改过
		if(isModi || that.prevEditRow.hasClass('editable-insert')){
			that.prevEditRow.addClass('editable-modify');
		}
		else{
			that.prevEditRow.removeClass('editable-modify');
		}
		that.prevEditRow = null;
		that.$body.find('> tr').removeClass('editable-select');
	}
}

/** 问题种类绑定事件*/
function problemTypeBindChange(){
	$("#problemType").bind("change", function(){
    	var divId = map[this.value];
    	if(divId=='fill'){
    		$("#fill").show();
    		$("#choice").hide();
    	}else{
    		$("#fill").hide();
    		$("#choice").show();
    	}
    });
}