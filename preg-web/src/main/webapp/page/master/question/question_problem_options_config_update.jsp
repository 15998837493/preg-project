<%@ page language="java" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <%@ include file="/common/common.jsp"%>
        <%@ include file="/common/plugins/bootstrap-table/bootstrap-table.jsp" %>
            <script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/question/problem.js"></script>
            <title>编辑问卷记录</title>
            <script type="text/javascript">
            var vali;
            var choiceData={optionOrder:1};
            /** 配置问题选项页面路径*/
            var initConfigAction =URL.get("Question.ADD_INIT_QUESTION_CONFIG");      
              //添加行    
                function addRowbtn(index){
                	var data = {
                	};
                	var optionOrder=0;
                	var addrow=$('#reportTable').bootstrapTable('getData');
                	for(var i=0;i<addrow.length;i++){
                		if(common.isEmpty(addrow[i].optionContent)){
                			layer.alert("选项内容不能为空！");
                			return;
                		}
                	}
                	var optionOrder=addrow.length+1;
                	data.optionOrder=optionOrder;
                	$('#reportTable').bootstrapTable('append',data);
                	
                };

				 //初始化列表框       		
				function inittable(){
					$('#reportTable').bootstrapTable({
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
									  }
									},
								title:"性别",
								align:"center",
								width:"200px"
							 },{
						        title: '操作',
						        formatter:function(value,row,rowIndex){
						        	var upHtml = '<a href="javascript:void(0);"   name="up'+rowIndex+'" onclick="upRow('+rowIndex+')">上移</a> <a href="javascript:void(0);" name="down'+rowIndex+'" onclick="downRow('+rowIndex+')">下移</a> <a href="javascript:void(0);" onclick="addRowbtn('+rowIndex+')">增加</a> <a href="javascript:void(0);" onclick="removeRow('+rowIndex+')" class="up">删除</a>';
						        	return upHtml;
						        },
						        edit:false,
						        align:"center"
					    		}		    
						    ]
					});	
				}    
                 //初始化填空题列表框       		
                function initfilltable(){
                		$('#fillTable').bootstrapTable({
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
                			        required:true,
                			    },{field:"optionValidate",
                			    	edit:{
                						type:'select',//下拉框
                						data:[{id:'^[1-9]\d*$',text:'数字验证'}],
                	        			valueField:'id',
                	        			textField:'text',
                	        			onSelect:function(val,rec){
                						  }
                						},
                					title:"验证",
                					align:"center",
                					width:"200px"
                				 }	    
                			    ]
                		});	
                }
                //保存数据
                function saveData(){
                	if(!vali.form()){
                		return;
                	}
                	var url = URL.get("Question.UPDATE_QUESTION_PROBLEM");
                	//将表单序列化为json
                	var fromParams = $('#updateForm').serializeObject();
                	var optionlistData;
                	if($("#problemType").val()==1 || $("#problemType").val()==2){
                		optionlistData=$('#reportTable').bootstrapTable('getData');
                	}else{
                		optionlistData=$('#fillTable').bootstrapTable('getData');
                	}
                	//将选项的json放入到fromParams
                	
                	fromParams.optionVos=optionlistData;
                 	var params = "fromParams="+JSON.stringify(fromParams)+"&optionParams="+JSON.stringify(optionlistData);
                       layer.confirm("确定对选中内容执行【保存】操作？", function () {
                   	   	ajax.post(url, params, dataType.json,function(data){
                  		    layer.alert(data.message, function(){
                  		    	var questionId=$("#questionIdUpdate").val();
                      			common.pageForward(initConfigAction + "?id=" + questionId);
                  		    });
                   		});
					});
                }
                //列向下移动
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
                	
                	console.log("看看有没有数据:",$('#reportTable').bootstrapTable('getData'));
                	if(index==0){
                		$('#reportTable').bootstrapTable('prepend',currentData);
                		$('#reportTable').bootstrapTable('prepend',nextData);
                	}else{
                		$('#reportTable').bootstrapTable('insertRow',{index:index,row:currentData});
                		$('#reportTable').bootstrapTable('insertRow',{index:index,row:nextData});
                	}
                } 
                    
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
                	cData.optionOrder=(prevRowNo);
                	prevData.optionOrder=(rowNo);
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
                /**
                 * 恢复tr，使之处于不可编辑状态
                 */
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
                //删除行
        		function removeRow(rowIndex){
        			$('#reportTable').bootstrapTable('removeRow',rowIndex);
        		}	
	//初始化列表数据
	function initData(problemId){
		 $.ajax({
		       url: PublicConstant.basePath + URL.Question.QUERY_QUESTION_OPTION,
		       data: {"problemId":problemId},  
		       dataType: 'json',
		       success: function(data) {
              	 if($("#problemType").val()==1 || $("#problemType").val()==2){
             		 $('#reportTable').bootstrapTable('load', data);
                	 }else{
                		 $('#fillTable').bootstrapTable('load', data);
                	 }  
		       },
		       error: function(e) {
		       }
		    });	
	}
	queryQuestionProblem=function(){
		var questionId=$("#questionIdUpdate").val();
		common.pageForward(initConfigAction + "?id=" + questionId);
	}
     </script>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row row-top">
            <div class="col-xs-12">
	    	<div class="panel panel-primary">
	    		<div class="panel-heading">
					<i class="fa fa-table fa-fw"></i>
					问题属性
	    		</div>  
	    		<div class="panel-body">             
                <form id="updateForm" class="form-horizontal" action="${common.basepath}/${applicationScope.URL.Question.UPDATE_PROBLEM}" method="post">
                <input class="form-control" type="hidden" name="problemId" id="id" value="${problem.problemId}"/>
                <input class="form-control" type="hidden" name="questionId" id="questionIdUpdate" value="${problem.questionId}"/>
                    <div class="col-xs-10 col-xs-offset-1 border-bottom-blue">
                        <label class="label-title"><i class="fa fa-edit fa-fw"></i>填写问题信息</label>
                    </div>
                    <div class="col-xs-12 row-top">
                        <div class="form-group">
                            <label class="col-xs-2 control-label">问题类型</label>
                            <div class="col-xs-4">
                                <select id="problemType"  name="problemType" class="form-control">
								<option value="1" selected = "selected" >单选题</option>
								<option value="2">多选题</option>
								<option value="3">单项填空题</option>
								<option value="4">多项填空题</option>
						</select>
                            </div>
                            <div>
                                <label class="col-xs-2 control-label">必答题</label>
                                <div class="col-xs-4">
                                	<div class="col-xs-6 control-label">
                                		<input  type="radio" name="problemRequired" id="problemRequiredYes" value="1" /> 是
                                	</div>
                                	<div class="col-xs-6 control-label">
                                		<input  type="radio" name="problemRequired" id="problemRequiredNo" value="0" /> 否         
                                	</div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div>
                                <label class="col-xs-2 control-label">适用性别</label>
                                <div class="col-xs-4">
                                    <select id="problemSex" name="problemSex" class="form-control">
							<option value="">==请选择适用性别==</option>
								<option value="all">不限</option>
								<option value="female">女性</option>
								<option value="male">男性</option>
						</select>
                                </div>
                            </div>
                            <div>
                                <label class="col-xs-2 control-label">问题分类</label>
                                <div class="col-xs-4">
                                    <select id="problemCategory" name="problemCategory" class="form-control"></select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
								<label class="col-xs-1 col-xs-offset-1 control-label">标题/内容</label>
								<div class="col-xs-10">
									<textarea id="problemContent" name="problemContent" class="form-control" maxlength="200"></textarea>
									<!-- <input id="optionVos" name="optionVos" class="form-control" /> -->
								</div>                                    
                        </div>                             
                        <div class="form-group">
								<label class="col-xs-1 col-xs-offset-1 control-label">提示</label>
								<div class="col-xs-10">
									<textarea id="problemHint" name="problemHint"  class="form-control" maxlength="200"></textarea>
								</div>                                    
                        </div>                            
                   </div>
                    </form>
                    </div>
                    </div>
                    </div>
					<div id="choice" class="container-fluid" style="display: none;">
				        <div class="row row-top">
				        <div class="col-xs-12">
					    	<div class="panel panel-primary">
					    		<div class="panel-heading">
									<i class="fa fa-table fa-fw"></i>
									选项属性
					    		</div>  
					    		<div class="panel-body">     
					      			<div class="row">
								        <div class="col-xs-12">
								            <form id="optionQuery" name="optionQuery" action="" method="post" class="form-horizontal">
								            <input class="form-control" type="hidden" name="problemId" id="optionProblemId"/>
								            </form>
								        </div>
								    </div>
				    			</div>
							   <div class="tab-pane fade in active" id="tab2">
							      <table class="table table-striped table-hover" id="reportTable"></table>
							   </div>
					  		</div>					
						</div>
					</div>
					</div>
					<div id="fill" class="container-fluid" style="display: none;">
				        <div class="row row-top">
				        <div class="col-xs-12">
					    	<div class="panel panel-primary">
					    		<div class="panel-heading">
									<i class="fa fa-table fa-fw"></i>
									填空属性
					    		</div>  
							   <div class="tab-pane fade in active">
							      <table class="table table-striped table-hover" id="fillTable"></table>
							   </div>
					  		</div>					
						</div>
					</div>
					</div>					                   
                    <div class="col-xs-12  border-top-blue row-top">
                        <div class="col-xs-8 col-xs-offset-3 ">
                            <div class="col-xs-4">
                                <button class="btn btn-primary btn-block" onclick="saveData()"><i class='fa fa-save fa-fw'></i>保存</button>
                            </div>
                            <div class="col-xs-4">
                                <button class="btn btn-primary btn-block" type="button" onclick="queryQuestionProblem();"><i class='fa fa-share-square-o fa-fw'></i>返回</button>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </body>
<script type="text/javascript">
$().ready(function() {
	var map={'1':'choice','2':'choice','3':'fill','4':'fill'};
    
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
        
common.initCodeSelect("PROBLEMCATEGORY","PROBLEMCATEGORY","problemCategory","${problem.problemCategory}");    
	 $("#problemType").val("${problem.problemType}");//问题类型
	 $("#problemSex").val("${problem.problemSex}");//适用性别
	 $("#problemRequired").val("${problem.problemRequired}");//必答题
	 $("#problemHint").val("${problem.problemHint}");//提示
	 $("#problemContent").val("${problem.problemContent}");//内容
 	 //必答题初始化
	 if("${problem.problemRequired}"==1){
		 $("#problemRequiredYes").attr("checked",'true');
	 }else{
		 $("#problemRequiredNo").attr("checked",'true');
	 } 
    vali= $("#updateForm").validate(intakeTemplateOptions);
    //加入必填项提示
    common.requiredHint("updateForm", intakeTemplateOptions);
    inittable();
    initfilltable();
    //填空题鼠标失去焦点
    $("#problemContent").blur(function(){
    	if($("#problemType").val()==3 || $("#problemType").val()==4){
    		var problemContent= $("#problemContent").val();
    		$("#fillTable").bootstrapTable('removeAll');
    		var ss=[];
    		if(problemContent){
    			ss=problemContent.split("");
    			if(ss){
    				for(var i=0;i<ss.length;i++){
    					
    					if(ss[i]=="#"){
    						 var d={optionOrder:i+1};
    						$("#fillTable").bootstrapTable('append', d);
    					}
    				}
    			}	
    		}
    	}else{
    		$("#reportTable").bootstrapTable('removeAll');
    		$('#reportTable').bootstrapTable('append', choiceData);
    	}
    }); 
    initData("${problem.problemId}");
    if($("#problemType").val()==3 || $("#problemType").val()==4 ){
		$("#fill").show();
		$("#choice").hide();
    }else{
		$("#fill").hide();
		$("#choice").show();	
    }
    
});
</script>
    </html>