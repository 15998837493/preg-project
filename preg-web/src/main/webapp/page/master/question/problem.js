/** 按钮参数*/
var operateType;
/** 验证参数*/
var validator;
/** 判断是选项属性/填空属性*/
var map={'1':'choice','2':'choice','3':'fill','4':'fill'};
/** 默认数据*/
var choiceData={optionOrder:1,optionContent:"",optionSex:"all"};
var fillData={optionOrder:1,optionContent:"",optionValidate:""};      		
/*******************************************初始化配置*************************************************************/
/** datatable配置*/
var masProblemData;
//选中行信息
var masProblemRow;
//table对象
var masProblemTable;
//列表配置信息
var masProblemTableOptions = {
	id : "masProblemTable",
	form : "masProblemQueryForm",
	columns : [ {
		"data" : null,
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return "<input type='radio' name='tableRadio'/>";
		}
	}, {
		"data" : "problemId",
		"sClass" : "text-center"
	}, {
		"data" : "problemContent",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			return data || '————';
		}
	}, {
		"data" : "problemType",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			var problemType = data;
			if(common.isEmpty(problemType)){
				problemType = '————';
			}else if(problemType=='1'){
				problemType="单选题";
			}else if(problemType=='2'){
				problemType="多选题";
			}else if(problemType=='3'){
				problemType="填空题";
			}else if(problemType=='4'){
				problemType="是非题";
			}
			return problemType;
		}
	}, {
		"data" : "problemCategory",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			var problemCategory=data;
			if(common.isEmpty(problemCategory)){
				problemCategory = '————';
			}else{
				problemCategory=CODE.transCode("PROBLEMCATEGORY",problemCategory);
			}
			return problemCategory;
		}
	}, {
		"data" : "problemSex",
		"sClass" : "text-center",
		"render" : function(data, type, row, meta) {
			var problemSex=data;
			if(common.isEmpty(problemSex)){
				problemSex = '————';
			}else if(problemSex=='all'){
				problemSex="不限";
			}else if(problemSex=='female'){
				problemSex="女性";
			}else if(problemSex=='male'){
				problemSex="男性";
			}
			return problemSex;
		}
	} ],
	rowClick : function(data, row) {
		masProblemData = data;
		masProblemRow = row;
		$("#id").val(masProblemData.problemId);
	},
	condition : "masProblemCondition"
};

/** 选项属性可编辑表格 onclick="addRowbtn('+rowIndex+')">增加</a>*/
var reportOption={
	id:'reportTable',
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
	id:'fillTable',
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

/** 验证参数设置*/
var intakeTemplateOptions = {
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
		problemRequired: {
			required:true
		}
	},
	//设置错误信息显示到指定位置
	errorPlacement: function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success: $.noop,
};
/****************************************************************增加/编辑功能*****************************************************/
/** modal绑定关闭保存事件*/
function editModalOnhHideBs(){
	$('#editModal').on('hide.bs.modal', function () {
		validator = $("#editForm").validate();
		if(!validator.form()){
			layer.confirm("是否放弃填写表单内容", {
				  btn: ["是","否"] //按钮
				}, 
			function(){
				layer.closeAll();
			},
			function(){
				layer.msg("请补全信息！");
				$('#editModal').modal("show");
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
		url = URL.get("Question.ADD_PROBLEM");
	}else{
		url = URL.get("Question.UPDATE_PROBLEM");
	}
	//将表单序列化为json
	var fromParams = $('#editForm').serializeObject();
	var optionlistData;
	if($("#problemType").val()==1 || $("#problemType").val()==2){
		optionlistData=$('#reportTable').bootstrapTable('getData');
	}else{
		optionlistData=$('#fillTable').bootstrapTable('getData');
	}
	//去掉空的选项
	$(optionlistData).each(function(index,obj){
		if(common.isEmpty(obj.optionContent)){
			optionlistData.splice(index, 1);
		}
	});
 	var params = "fromParams="+JSON.stringify(fromParams)+"&optionParams="+JSON.stringify(optionlistData);
 	ajax.post(url, params, dataType.json,function(data){
	   if(operateType == "add"){
		   datatable.add(masProblemTable, data.value);// 添加
		}else{
		   datatable.update(masProblemTable, data.value, masProblemRow);// 修改
		}
 	});
}

//初始化列表数据
function initData(problemId){
	var url = URL.get("Question.QUERY_OPTION");
	var params = "problemId=" + problemId;
	ajax.post(url, params, dataType.json, function(result){
		if(result.data==null){
	    	 if($("#problemType").val()==1 || $("#problemType").val()==2){
	    		 $('#reportTable').bootstrapTable('load', choiceData);
	       	 }
	    }else{
	      	 if($("#problemType").val()==1 || $("#problemType").val()==2){
	    		 $('#reportTable').bootstrapTable('load', result.data);
	       	 }else{
	       		 $('#fillTable').bootstrapTable('load', result.data);
	       	 }  
	   }
	});
}
/********************************************************删除操作*****************************************************************************************/

/** 删除提交form请求*/
function removeClick(id){
	layer.confirm('确定对选中内容执行【删除】操作？', {
		  btn: ['确定','取消'] //按钮
		},
	function(){
		var url = URL.get("Question.PROBLEM_REMOVE");
		var params = "id=" + id;
		ajax.post(url, params, dataType.json,function(data){
			datatable.remove(masProblemTable, masProblemRow);// 删除
			$("#id").val("");
		});
	});
};
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
	$('#reportTable').bootstrapTable('removeRow',rowIndex);
}	

/** 列向上移动*/
function upRow(index){
	//如果是第一行不显示向上按钮
	//如果是最后一行不显示向下按钮
	var allRow= $('#reportTable').bootstrapTable('getData');
	var rowlength=allRow.length;
	if(index==0){
		layer.alert("已经是最上层了！");
		return;
	}
	//上一行
	var prevRowNo=index;
	//当前行
	
	var rowNo=(index+1);
	var cData=$('#reportTable').bootstrapTable('getRowByUniqueId',rowNo);
	var prevData=$('#reportTable').bootstrapTable('getRowByUniqueId',prevRowNo);
	cData.optionOrder=prevRowNo;
	prevData.optionOrder=rowNo;
	//删除这两行数据
	//判断行号是不是0如果是0，则app两条数据，如果不是，在上行的基础上追加两行数据
	$('#reportTable').bootstrapTable('removeByUniqueId', rowNo);
	$('#reportTable').bootstrapTable('removeByUniqueId', prevRowNo);
	if(rowNo==rowlength){
		$('#reportTable').bootstrapTable('append',cData);
		$('#reportTable').bootstrapTable('append',prevData);
	}else{
		$('#reportTable').bootstrapTable('insertRow',{index:index-1,row:prevData});
		$('#reportTable').bootstrapTable('insertRow',{index:index-1,row:cData});
	}	
}

/** 列向下移动*/
function downRow(index){
	//如果是第一行不显示向上按钮
	var allRow= $('#reportTable').bootstrapTable('getData');
	if(index+1==allRow.length){
		layer.alert("已经是最下层了！");
		return;
	}
	var currentRowNo=index+1;
	var nextRowNo=index+2;
	var currentData=$('#reportTable').bootstrapTable('getRowByUniqueId',currentRowNo);
	currentData.optionOrder=(nextRowNo);
	var nextData=$('#reportTable').bootstrapTable('getRowByUniqueId',nextRowNo);
	nextData.optionOrder=(currentRowNo);
	//删除这两行数据
	//判断行号是不是0如果是0，则app两条数据，如果不是，在上行的基础上追加两行数据
	$('#reportTable').bootstrapTable('removeByUniqueId', currentRowNo);
	$('#reportTable').bootstrapTable('removeByUniqueId', nextRowNo);
	
	if(index==0){
		$('#reportTable').bootstrapTable('prepend',currentData);
		$('#reportTable').bootstrapTable('prepend',nextData);
	}else{
		$('#reportTable').bootstrapTable('insertRow',{index:index,row:currentData});
		$('#reportTable').bootstrapTable('insertRow',{index:index,row:nextData});
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

/** 设置提示文案*/
function placeholder(id,content){
	$("#"+id)[0].placeholder=content;
}

/** 问题种类绑定事件*/
function problemTypeBindChange(){
	$("#problemType").bind("change", function(){
    	var divId = map[this.value];
    	if(divId=='fill'){
    		$("#fill").show();
    		$("#choice").hide();
    		//placeholder("problemContent","输入'#'后失去光标编辑默认内容与验证！");
    	}else{
    		$("#fill").hide();
    		$("#choice").show();
    		//placeholder("problemContent","输入标题！");
    	}
    });
}