<%@ page language="java" pageEncoding="utf-8"%>
<!-- 单选题模板 -->
<script id="p1" type="text/html">
		{{# var question = questionnaire.problem.current ;
		      var findIndex = _.findIndex(questionnaire.result.data, {
					problemId: question.problemId
					});
		      var result = questionnaire.result.data[findIndex];
			  var answerContent= "";
		}}
		<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{ question.problemContent}}
		</h4></div>
		<div class="panel panel-danger">
	    	<div class="panel-body">
			{{# var optlength = question.options.length ;}}
			{{# for(var i = 0; i <optlength; i++){ }}
			<div class="col-xs-12">
				<h5>
					<label class="radio-inline">
						<input ptype="2" type="radio" name={{ question.problemId }}  pid={{ question.problemId }} id={{ question.options[i].optionId }} 
						{{# 
							var isChecked = false;
							if(!common.isEmpty(result)){
								isChecked=question.options[i].optionId==result.optionId?true:false;
								answerContent=result.addvalue;
							}
						}}
						{{ isChecked?"checked":"" }}
						>
						{{ question.options[i].optionName }}
					</label>
				</h5>
			</div>
			{{# } }}
			{{# if(!common.isEmpty(question.hint)){ }}
				<div class="col-xs-12">
					<h5>
						<span class="text-danger">{{question.hint}}</span>
					</h5>
				</div>
			{{# } }}
	   		</div>
		</div>


</script>
<!-- 多选题模板 -->
<script id="p2" type="text/html">
	{{# var question = questionnaire.problem.current ;
		   var results = new Array();
           var tempresult = questionnaire.result.data;
		   var findIndex = -1;
		   do{
			    findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   	if(findIndex!=-1){
					results.push(tempresult[findIndex]);
					tempresult = _.without(tempresult, tempresult[findIndex]) ;
				};
		      
			}while (findIndex!=-1);
		   
			  var answerContent= "";
		}}
		<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{ question.problemContent}}
		</h4></div>
		<div class="panel panel-danger">
	    <div class="panel-body">
		{{# var optlength = question.options.length ;}}
		{{# for(var i = 0; i <optlength; i++){ }}
		<div class="col-xs-12">
			<h5>
				<label class="radio-inline">
					<input ptype="2" type="checkbox" name={{ question.problemId }}  pid={{ question.problemId }} id={{ question.options[i].optionId }} 
					{{# 
						var isChecked = false;
						if(!common.isEmpty(results)){
							var len = results.length;
							do{
								--len;
								isChecked=question.options[i].optionId==results[len].optionId?true:false;
								if(results[len].optionType=="1")
								answerContent=results[len].addvalue;
							}while(len>0&&!isChecked);
						}
					}}
					{{ isChecked?"checked":"" }}
					>
					{{ question.options[i].optionName }}
				</label>
			</h5>
		</div>
		{{# } }}
		{{# if(question.hasOther){ }}
			<div class="col-xs-12">
				<h5>
					<label class="radio-inline">
					其他：<input ptype="1" type="text"  class="underline"  pid={{ question.problemId }} value={{answerContent}} ></input>
					</label>
				</h5>
			</div>
		{{# } }}
		</div>
		</div>
</script>
<!-- 用餐时间模板 -->
<script id="p3" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
		      var results = new Array();
           	  var tempresult = questionnaire.result.data;
		      var findIndex = -1;
		      do{
			    	findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   		if(findIndex!=-1){
						results.push(tempresult[findIndex]);
						tempresult = _.without(tempresult, tempresult[findIndex]) ;
					};
		      
			  }while (findIndex!=-1);
			  var answerContent= "";
			  var pid="";
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{ question.problemContent}}
		
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	<div class="col-xs-12 table-responsive">
 		<table class="table table-bordered">
			{{# var optlength = question.options.length ;var isSelected = false;var isChecked = false;}}
			{{# for(var i = 0; i <optlength; i++){
			var isDisabled = false;
			 }}
			  <tr>
			  		<td class="text-right bg-info"><strong>{{question.options[i].optionName}}</strong></td>
			  		<td class="bg-warning"><select ptype="1" class="form-control" id="sel{{i}}" oid="{{question.options[i].optionId}}" option="relation"
									{{# if(!common.isEmpty(results)){
												var len = results.length;
												do{
													--len;
													isChecked = (results[len].optionId==question.options[i].optionId&&results[len].addvalue=="不吃")?true:false;
												}while(len>0&&!isChecked);
												pid="";
												var oIndex = _.findIndex(results, {
													optionId: question.options[i].optionId
												});
												if(oIndex!=-1&&!isChecked){
													pid=question.problemId;
										    	}
									}}}	
								pid="{{pid}}" {{isChecked?"disabled":""}}>
								 <option value="">==选择{{question.options[i].optionName}}时间==</option>
								 {{# for(var bft = 0; bft <commonInfo.mealsTime[i].length; bft++){ }}
								 <option
										{{# if(!common.isEmpty(results)){
												var len = results.length;
												do{
													--len;
													isSelected = results[len].addvalue==commonInfo.mealsTime[i][bft]?true:false;
												}while(len>0&&!isSelected);
												pid="";
												if(isSelected){
														isDisabled = isSelected;
														pid=question.problemId;
												}
												pid="";
												if(isChecked){
													pid=question.problemId;
												}
										}}}
											{{isSelected?"selected":""}} >{{commonInfo.mealsTime[i][bft]}}</option>
								 {{#}}}
							</select>
			  		</td>
					<td class="text-left bg-warning"><span class="checkbox"><label><input ptype="1" type="checkbox" oid="{{question.options[i].optionId}}" option="relation" id="cb{{i}}" value="不吃" 
					pid="{{pid}}" {{isChecked?"checked":""}} {{isDisabled?"disabled":""}}>不吃</label></span></td>
			  </tr>
			  {{#}}}
		</table>
	</div>
	</div>
	</div>
</script>
<!-- 单张图片填空模板 -->
<script id="p4" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
		      var contents = question.problemContent.split("@#$");
		      var findIndex = _.findIndex(questionnaire.result.data, {
					problemId: question.problemId
					});
		      var result = questionnaire.result.data[findIndex];
			  var answerContent= "";
			 if(!common.isEmpty(result)){
				answerContent= result.addvalue;
			 }
			  
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{ contents[0]}}
		<input ptype="1" type="text"  class="underline"  regexp={{question.options[0].validate}} pid={{ question.problemId }} value={{answerContent}} ></input><span class="label label-danger"></span>
		{{ contents[1]}}
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	<div class="col-xs-10 col-xs-offset-1">
		<img src="${common.basepath}/{{question.options[0].imgUrl}}" class="img-responsive" nolarge>
	</div>
	</div>
	</div>
</script>
<!-- 主食模板 -->
<script id="p5" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
		      var results = new Array();
           	  var tempresult = questionnaire.result.data;
		      var findIndex = -1;
		      do{
			    	findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   		if(findIndex!=-1){
						results.push(tempresult[findIndex]);
						tempresult = _.without(tempresult, tempresult[findIndex]) ;
					};
		      
			  }while (findIndex!=-1);
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{question.problemContent}}
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	{{#
		var addRatio = "0%";
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.hidden[0]
					});
					
					if(resultIndex!=-1){
						addRatio = results[resultIndex].addvalue;
					}
				}
	}}
	<div class="col-xs-12 alert alert-warning text-center">
		<p>{{question.ratio[0]}}</p>
		<input id="ex0" data-slider-id='exSlider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="10" data-slider-value="{{addRatio.substr(0,addRatio.length-1)}}"/>
		<span id="exCurrentSliderValLabel" class="label label-danger">当前值: <span id="exSliderVal0">{{addRatio.substr(0,addRatio.length-1)}}</span>%</span>
 	</div>
	{{#
	for(var i=0;i<question.options.length;i=i+4){
	var addval = "";
    var pid="";
	}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i].imgUrl}}" 
            alt="{{question.options[i].optionName}}">
            <div class="caption">
                <h5 class="text-center">{{question.options[i].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid=results[resultIndex].problemId;	
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i].validate}} unit="g" pid="{{pid}}" oid={{question.options[i].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#
if(i+1<question.options.length){ 
var addval = "";
var pid="";
}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+1].imgUrl}}" 
            alt={{question.options[i+1].optionName}}>
            <div class="caption">
                <h5 class="text-center">{{question.options[i+1].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+1].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid=results[resultIndex].problemId;	
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+1].validate}} unit="g" pid="{{pid}}" oid={{question.options[i+1].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#}

if(i+2<question.options.length){
var addval = "";
var pid="";
 }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+2].imgUrl}}" 
            alt={{question.options[i+2].optionName}}>
            <div class="caption">
                <h5 class="text-center">{{question.options[i+2].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+2].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid=results[resultIndex].problemId;	
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+2].validate}} unit="g" pid="{{pid}}" oid={{question.options[i+2].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#}

if(i+3<question.options.length){
var addval = "";
var pid="";
 }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+3].imgUrl}}" 
            alt="{{question.options[i+3].optionName}}">
            <div class="caption">
                <h5 class="text-center">{{question.options[i+3].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+3].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid=results[resultIndex].problemId;	
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+3].validate}} unit="g" pid="{{pid}}" oid={{question.options[i+3].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{# } 

	}
}}
<div hidden="hidden"><input type="text" id="ratio0" ptype="1" pid={{question.problemId}} oid="{{question.hidden[0]}}" value="{{addRatio}}" /></div>
</div>
</div>
</script>

<!-- 多张图片天一个空模板 -->
<script id="p6" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
			  var contents = question.problemContent.split("@#$");
		      var results = new Array();
           	  var tempresult = questionnaire.result.data;
		      var findIndex = -1;
		      do{
			    	findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   		if(findIndex!=-1){
						results.push(tempresult[findIndex]);
						tempresult = _.without(tempresult, tempresult[findIndex]) ;
					};
		      
			  }while (findIndex!=-1);
			  var answerContent= "";
			  var addvals = new Array(question.hidden.length);
			  for(var r=0;r<question.ratio.length;r++){
					addvals[r] = "0%";
			 }	
			 if(!common.isEmpty(results)){
				$.each(results,function(key,val){
					if(common.isEmpty(val.optionId)){
						answerContent= val.addvalue;
					}else{
						if(key>0){
							addvals[key-1]= val.addvalue;
						}
						
					}
				});
				
			 }
			  
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{ contents[0]}}
		<input ptype="1" type="text"  class="underline"  regexp={{question.options[0].validate}} pid={{ question.problemId }} value={{answerContent}} ></input><span class="label label-danger"></span>
		{{ contents[1]}}
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	<div class="col-xs-12 well well-sm">{{question.hint}}</div>
	{{# 
		for(var r=0;r<question.ratio.length;r++){
	}}
	<div class="col-xs-12 alert alert-warning text-center">
		<p>{{question.ratio[r]}}</p>
		{{#
			var ex = "ex"+r;
			var exSliderVal = "exSliderVal"+r;
		}}
		<input id="{{ex}}" data-slider-id='exSlider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="10" data-slider-value="{{addvals[r].substr(0,addvals[r].length-1)}}"/>
		<span id="exCurrentSliderValLabel" class="label label-danger">当前值: <span id="{{exSliderVal}}">{{addvals[r].substr(0,addvals[r].length-1)}}</span>%</span>
 	</div>
	{{#
		}

	for(var i=0;i<question.options.length;i=i+4){}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i].imgUrl}}" 
            alt="{{question.options[i].optionName}}">
            <div class="caption">
                <h5 class="text-center">{{question.options[i].optionName}}</h5>
            </div>
        </div>
    </div>
{{#if(i+1<question.options.length){ }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+1].imgUrl}}" 
            alt={{question.options[i+1].optionName}}>
            <div class="caption">
                <h5 class="text-center">{{question.options[i+1].optionName}}</h5>
            </div>
        </div>
    </div>
{{#}
	if(i+2<question.options.length){
}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+2].imgUrl}}" 
            alt={{question.options[i+2].optionName}}>
            <div class="caption">
                <h5 class="text-center">{{question.options[i+2].optionName}}</h5>
            </div>
        </div>
    </div>
{{#}
	if(i+3<question.options.length){
}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+3].imgUrl}}" 
            alt="{{question.options[i+3].optionName}}">
            <div class="caption">
                <h5 class="text-center">{{question.options[i+3].optionName}}</h5>
            </div>
        </div>
    </div>
{{# 
	} 
}

for(var r=0;r<question.hidden.length;r++){
   var ratio = "ratio"+r;
}}
<div hidden="hidden"><input type="text" id="{{ratio}}" ptype="1" pid={{question.problemId}} oid="{{question.hidden[r]}}" value="{{addvals[r]}}" /></div>
{{#}}}
</div>
</div>
</script>
<!-- 水果模板 -->
<script id="p7" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
		      var results = new Array();
           	  var tempresult = questionnaire.result.data;
		      var findIndex = -1;
		      do{
			    	findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   		if(findIndex!=-1){
						results.push(tempresult[findIndex]);
						tempresult = _.without(tempresult, tempresult[findIndex]) ;
					};
		      
			  }while (findIndex!=-1);
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{question.problemContent}}
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	{{#
	for(var i=0;i<question.options.length;i=i+4){
	var addval = "";
	var pid=""
	}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i].imgUrl}}" 
            alt="{{question.options[i].optionName}}">
            <div class="caption">
                <h5 class="text-center">{{question.options[i].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid = results[resultIndex].problemId;
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i].validate}} unit="g" pid="{{pid}}" oid={{question.options[i].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#
if(i+1<question.options.length){ 
var addval = "";
var pid=""
}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+1].imgUrl}}" 
            alt={{question.options[i+1].optionName}}>
            <div class="caption">
                <h5 class="text-center">{{question.options[i+1].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+1].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid = results[resultIndex].problemId;
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+1].validate}} unit="g" pid="{{pid}}" oid={{question.options[i+1].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#}

if(i+2<question.options.length){
var addval = "";
var pid=""
 }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+2].imgUrl}}" 
            alt={{question.options[i+2].optionName}}>
            <div class="caption">
                <h5 class="text-center">{{question.options[i+2].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+2].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid = results[resultIndex].problemId;
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+2].validate}} unit="g" pid="{{pid}}" oid={{question.options[i+2].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#}

if(i+3<question.options.length){
var addval = "";
var pid=""
 }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+3].imgUrl}}" 
            alt="{{question.options[i+3].optionName}}">
            <div class="caption">
                <h5 class="text-center">{{question.options[i+3].optionName}}</h5>
                <p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+3].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						pid = results[resultIndex].problemId;
					}
				}
				}}
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+3].validate}} unit="g" pid="{{pid}}" oid={{question.options[i+3].optionId}} value={{addval}}>
           				 <span class="input-group-addon">g</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{# } 

	}

}}
</div>
</div>
</script>

<!-- 901 问题 -->
<script id="p8" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
			  var eggPid = "";
			  var selectegg = "0";
			  var valegg = "";
			  var milkPid = "";
			  var selectmilk = "0";
			  var valmilk = "";
			  var tmilkPid = "";
			  var selecttmilk = "0";
			  var valtmilk = "";
			  var beansPid = "";
			  var selectbeans = "0";
			  var valbeans = "";
			  var seaPid = "";
			  var selectsea = "0";
			  var valsea = "";
			  var nutPid = "";
			  var selectnut = "0";
			  var valnut = "";
			  var organsPid = "";
			  var selectorgans = "0";
			  var valorgans = "";
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{question.problemContent}}
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	<div class="col-xs-12">
	<div class="panel panel-primary">

    	<div class="panel-body">
        		<div class="col-xs-12">
					<div class="col-xs-3 text-right">
						<h4><span class="label label-success"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;蛋&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 类   ：</span></h4>
					</div>
					{{#
						var findIndex = _.findIndex(questionnaire.result.data, {
							optionId: question.options[0].optionId
							});
						if(findIndex!=-1){
							eggPid = questionnaire.result.data[findIndex].problemId;
							selectegg = questionnaire.result.data[findIndex].frequency;
							valegg = questionnaire.result.data[findIndex].addvalue;
							};
					}}
					<div class="col-xs-3">
						<select ptype="1" class="form-control" option="get" id="selectegg">
						{{#
							var disableFlag = selectegg=="0"?true:false;
							for(var i=0;i<commonInfo.frequency.length;i++){
						}}
							<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selectegg?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
						{{#}}}
						</select>
					</div>
					<div class="col-xs-3">
						<div class="input-group">
								<input ptype="1" type="text" class="form-control" regexp={{question.options[0].validate}} unit="份" pid="{{eggPid}}" frequency="{{selectegg}}" id="inputegg" oid={{question.options[0].optionId}} placeholder="每次摄入" value="{{valegg}}" {{disableFlag?"disabled":""}}>
								<span class="input-group-addon">份</span>
						</div>
					</div>
					<div class="col-xs-3"><h4><span class="label label-danger">一份蛋约为50克</span></h4></div>
				</div>
				<div class="col-xs-10 col-xs-offset-1">
					<img src="${common.basepath}/{{question.options[0].imgUrl}}" class="img-responsive" nolarge>
				</div>
    	</div>
	</div>
	<div class="panel panel-primary">

    	<div class="panel-body">
        				<div class="col-xs-12">
							<div class="col-xs-3 text-right">
								<h4><span class="label label-success">全脂奶及奶制品：</span></h4>
							</div>
							{{#
								var findIndex = _.findIndex(questionnaire.result.data, {
									optionId: question.options[1].optionId
									});
								if(findIndex!=-1){
									milkPid = questionnaire.result.data[findIndex].problemId;
									selectmilk = questionnaire.result.data[findIndex].frequency;
									valmilk = questionnaire.result.data[findIndex].addvalue;
									};
							}}
							<div class="col-xs-3">
								<select ptype="1" class="form-control" option="get" id="selectmilk">
								{{#			
									var disableFlag = selectmilk=="0"?true:false;
									for(var i=0;i<commonInfo.frequency.length;i++){
								}}
									<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selectmilk?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
								{{#}}}
								</select>
							</div>
							<div class="col-xs-3">
								<div class="input-group">
										<input ptype="1" type="text" class="form-control" regexp={{question.options[1].validate}} unit="份" pid="{{milkPid}}" frequency="{{selectmilk}}" id="inputmilk" oid={{question.options[1].optionId}} placeholder="每次摄入" value="{{valmilk}}" {{disableFlag?"disabled":""}}>
										<span class="input-group-addon">份</span>
								</div>
							</div>
						</div>
						<div class="col-xs-12">
							<div class="col-xs-3 text-right">
								<h4><span class="label label-success">脱脂奶及奶制品：</span></h4>
							</div>
							{{#
								var findIndex = _.findIndex(questionnaire.result.data, {
									optionId: question.options[2].optionId
									});
								if(findIndex!=-1){
									tmilkPid = questionnaire.result.data[findIndex].problemId;
									selecttmilk = questionnaire.result.data[findIndex].frequency;
									valtmilk = questionnaire.result.data[findIndex].addvalue;
									};
							}}
							<div class="col-xs-3">
								<select ptype="1" class="form-control" option="get" id="selecttmilk">
								{{#			
									var disableFlag = selecttmilk=="0"?true:false;
									for(var i=0;i<commonInfo.frequency.length;i++){
								}}
									<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selecttmilk?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
								{{#}}}
								</select>
							</div>
							<div class="col-xs-3">
								<div class="input-group">
										<input ptype="1" type="text" class="form-control" regexp={{question.options[2].validate}} unit="份" pid="{{tmilkPid}}" frequency="{{selecttmilk}}" id="inputtmilk" oid={{question.options[2].optionId}} placeholder="每次摄入" value="{{valtmilk}}" {{disableFlag?"disabled":""}}>
										<span class="input-group-addon">份</span>
								</div>
							</div>
						</div>
						<div class="col-xs-12">&nbsp;</div>
						<div class="col-xs-12">
							{{#
								 for(var pic=0;pic<question.options[1].imgUrl.length;pic++){
							}}
								 <div class="col-md-3">
									<div class="thumbnail">
										<img src="${common.basepath}/{{question.options[1].imgUrl[pic]}}" class="img-responsive">
										<div class="caption">
											<h5 class="text-center">{{question.options[1].model[pic]}}</h5>
										</div>
									</div>
								</div>
							{{#
							}
							}}
						</div>
						<div class="col-xs-12 text-center"><h4><span class="label label-danger col-xs-2">一份牛奶200ml</span>  <span class="col-xs-1">  =  </span><span class="label label-danger col-xs-2">一份奶酪25g</span><span class=" col-xs-1"> =</span><span class="label label-danger col-xs-3"> 一份酸奶125ml*2</span><span class="col-xs-1">=</span><span class="label label-danger col-xs-2"> 一份奶粉10g</span></h4>
						</div>
    	</div>
	</div>
	<div class="panel panel-primary">

    	<div class="panel-body">
        			<div class="col-xs-12">
						<div class="col-xs-3 text-right">
							<h4><span class="label label-success"> &nbsp;&nbsp;大 &nbsp;&nbsp; &nbsp;&nbsp;豆 &nbsp;&nbsp; &nbsp;&nbsp;类   ：</span></h4>
						</div>
						{{#
							var findIndex = _.findIndex(questionnaire.result.data, {
								optionId: question.options[3].optionId
								});
							if(findIndex!=-1){
								beansPid = questionnaire.result.data[findIndex].problemId;
								selectbeans = questionnaire.result.data[findIndex].frequency;
								valbeans = questionnaire.result.data[findIndex].addvalue;
								};
						}}
						<div class="col-xs-3">
							<select ptype="1" class="form-control" option="get" id="selectbeans">
									{{#			
								var disableFlag = selectbeans=="0"?true:false;
								for(var i=0;i<commonInfo.frequency.length;i++){
							}}
								<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selectbeans?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
							{{#}}}
							</select>
						</div>
						<div class="col-xs-3">
							<div class="input-group">
									<input ptype="1" type="text" class="form-control" regexp={{question.options[3].validate}} unit="份" pid="{{beansPid}}" frequency="{{selectbeans}}" id="inputbeans" oid={{question.options[3].optionId}} placeholder="每次摄入" value="{{valbeans}}" {{disableFlag?"disabled":""}}>
									<span class="input-group-addon">份</span>
							</div>
						</div>
					</div>
					<div class="col-xs-12">&nbsp;</div>
					<div class="col-xs-12">
						{{#
							 for(var pic=0;pic<question.options[3].imgUrl.length;pic++){
						}}
							 <div class="col-md-3">
								<div class="thumbnail">
									<img src="${common.basepath}/{{question.options[3].imgUrl[pic]}}" class="img-responsive">
									<div class="caption">
										<h5 class="text-center">{{question.options[3].model[pic]}}</h5>
									</div>
								</div>
							</div>
						{{#
						}
						}}
					</div>
					<div class="col-xs-11 col-xs-offset-1"><h4><span class="label label-danger col-xs-2">一份黄豆20g </span>  <span class="col-xs-1">  =  </span><span class="label label-danger col-xs-2">一份豆浆360ml</span><span class=" col-xs-1"> =</span><span class="label label-danger col-xs-2"> 一份豆干45g</span><span class="col-xs-1">=</span><span class="label label-danger col-xs-2"> 一份北豆腐60g</span></h4>
					</div>
    	</div>
	</div>
	<div class="panel panel-primary">

    	<div class="panel-body">
        		<div class="col-xs-12">
					<div class="col-xs-3 text-right">
						<h4><span class="label label-success"> &nbsp;&nbsp;鱼 &nbsp;&nbsp; &nbsp;&nbsp;虾 &nbsp;&nbsp; &nbsp;&nbsp;类   ：</span></h4>
					</div>
					{{#
						var findIndex = _.findIndex(questionnaire.result.data, {
							optionId: question.options[4].optionId
							});
						if(findIndex!=-1){
							seaPid = questionnaire.result.data[findIndex].problemId;
							selectsea = questionnaire.result.data[findIndex].frequency;
							valsea = questionnaire.result.data[findIndex].addvalue;
							};
					}}
					<div class="col-xs-3">
						<select ptype="1" class="form-control" option="get" id="selectsea">
						{{#			
							var disableFlag = selectsea=="0"?true:false;
							for(var i=0;i<commonInfo.frequency.length;i++){
						}}
							<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selectsea?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
						{{#}}}
						</select>
					</div>
					<div class="col-xs-3">
						<div class="input-group">
								<input ptype="1" type="text" class="form-control" regexp={{question.options[4].validate}} unit="份" pid="{{seaPid}}" frequency="{{selectsea}}" id="inputsea" oid={{question.options[4].optionId}} placeholder="每次摄入" value="{{valsea}}" {{disableFlag?"disabled":""}}>
								<span class="input-group-addon">份</span>
						</div>
					</div>
				</div>
					
				<div class="col-xs-12">&nbsp;</div>
				<div class="col-xs-12">
					{{#
						 for(var pic=0;pic<question.options[4].imgUrl.length;pic++){
					}}
						 <div class="col-md-3">
							<div class="thumbnail">
								<img src="${common.basepath}/{{question.options[4].imgUrl[pic]}}" class="img-responsive">
								<div class="caption">
									<h5 class="text-center">{{question.options[4].model[pic]}}</h5>
								</div>
							</div>
						</div>
					{{#
					}
					}}
				</div>
					{{#
						var findIndex = _.findIndex(questionnaire.result.data, {
							optionId: question.hidden[0]
							});
						var addval="0%";
						if(findIndex!=-1){
							addval = questionnaire.result.data[findIndex].addvalue;
							};
					}}
					<div hidden="hidden"><input type="text" id="ratio0" ptype="1" pid={{question.problemId}} oid="{{question.hidden[0]}}" value="{{addval}}" /></div>
				<div class="col-xs-11 col-xs-offset-1"><h4><span class="label label-danger col-xs-2">一份三文鱼50g</span>  <span class="col-xs-1">  =  </span><span class="label label-danger col-xs-2">一份带鱼65g</span><span class=" col-xs-1"> =</span><span class="label label-danger col-xs-2">一份草虾85g</span><span class="col-xs-1">=</span><span class="label label-danger col-xs-2"> 一份草鱼90g</span></h4>
				</div>
				<div class="col-xs-12">&nbsp;</div>
				<div class="col-xs-12 alert alert-warning text-center">
					<p>{{question.ratio[0]}}</p>
					<input id="ex0" data-slider-id='exSlider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="10" data-slider-value="{{addval.substr(0,addval.length-1)}}"/>
					<span id="exCurrentSliderValLabel" class="label label-danger">当前值: <span id="exSliderVal0">{{addval.substr(0,addval.length-1)}}</span>%</span>
  				</div>
    	</div>
	</div>
	<div class="panel panel-primary">

    	<div class="panel-body">
        			<div class="col-xs-12">
						<div class="col-xs-3 text-right">
							<h4><span class="label label-success">坚果（非油炸）：</span></h4>
						</div>
						{{#
							var findIndex = _.findIndex(questionnaire.result.data, {
								optionId: question.options[5].optionId
								});
							if(findIndex!=-1){
								nutPid = questionnaire.result.data[findIndex].problemId;
								selectnut = questionnaire.result.data[findIndex].frequency;
								valnut = questionnaire.result.data[findIndex].addvalue;
								};
						}}
						<div class="col-xs-3">
							<select ptype="1" class="form-control" option="get" id="selectnut">
							{{#			
								var disableFlag = selectnut=="0"?true:false;
								for(var i=0;i<commonInfo.frequency.length;i++){
							}}
								<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selectnut?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
							{{#}}}
							</select>
						</div>
						<div class="col-xs-3">
							<div class="input-group">
									<input ptype="1" type="text" class="form-control" regexp={{question.options[5].validate}} unit="份" pid="{{nutPid}}" frequency="{{selectnut}}" id="inputnut" oid={{question.options[5].optionId}} placeholder="每次摄入" value="{{valnut}}" {{disableFlag?"disabled":""}}>
									<span class="input-group-addon">份</span>
							</div>
						</div>
					</div>
					<div class="col-xs-12">&nbsp;</div>
					<div class="col-xs-12">
						{{#
							 for(var pic=0;pic<question.options[5].imgUrl.length;pic++){
						}}
							 <div class="col-md-3">
								<div class="thumbnail">
									<img src="${common.basepath}/{{question.options[5].imgUrl[pic]}}" class="img-responsive">
									<div class="caption">
										<h5 class="text-center">{{question.options[5].model[pic]}}</h5>
									</div>
								</div>
							</div>
						{{#
						}
						}}
					</div>
					<div class="col-xs-11 col-xs-offset-1"><h4><span class="label label-danger col-xs-2">一份瓜子仁10g </span>  <span class="col-xs-1">  =  </span><span class="label label-danger col-xs-2">一份瓜子24g</span><span class=" col-xs-1"> =</span><span class="label label-danger col-xs-2"> 一份花生米10g</span><span class="col-xs-1">=</span><span class="label label-danger col-xs-2"> 一份花生28g</span></h4>
					</div>
    	</div>
	</div>
	<div class="panel panel-primary">

    	<div class="panel-body">
        	<div class="col-xs-12">
				<div class="col-xs-3 text-right">
					<h4><span class="label label-success">动 &nbsp;&nbsp;物 &nbsp;&nbsp;内 &nbsp;&nbsp;脏   ：</span></h4>
				</div>
				{{#
					var findIndex = _.findIndex(questionnaire.result.data, {
						optionId: question.options[6].optionId
						});
					if(findIndex!=-1){
						organsPid = questionnaire.result.data[findIndex].problemId;
						selectorgans = questionnaire.result.data[findIndex].frequency;
						valorgans = questionnaire.result.data[findIndex].addvalue;
						};
				}}
				<div class="col-xs-3">
					<select ptype="1" class="form-control" option="get" id="selectorgans">
					{{#			
						var disableFlag = selectorgans=="0"?true:false;
						for(var i=0;i<commonInfo.frequency.length;i++){
					}}
						<option value="{{commonInfo.frequency[i].val}}" {{commonInfo.frequency[i].val==selectorgans?"selected":""}}>{{commonInfo.frequency[i].name}}</option>
					{{#}}}
					</select>
				</div>
				<div class="col-xs-3">
					<div class="input-group">
							<input ptype="1" type="text" class="form-control" regexp={{question.options[6].validate}} unit="份" pid="{{organsPid}}" frequency="{{selectorgans}}" id="inputorgans" oid={{question.options[6].optionId}} placeholder="每次摄入" value="{{valorgans}}" {{disableFlag?"disabled":""}}>
							<span class="input-group-addon">份</span>
					</div>
				</div>
				<div class="col-xs-3">
					<h4><span class="label label-danger">一份鸡肝50克</span></h4>
				</div>
			</div>
			<div class="col-xs-3 col-xs-offset-4">
				<img src="${common.basepath}/{{question.options[6].imgUrl}}" class="img-responsive">
			</div>
    	</div>
	</div>
	</div>
	</div>
	</div>

</script>

<!-- 频率膳食模板 -->
<script id="p9" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
		      var results = new Array();
           	  var tempresult = questionnaire.result.data;
		      var findIndex = -1;
		      do{
			    	findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   		if(findIndex!=-1){
						results.push(tempresult[findIndex]);
						tempresult = _.without(tempresult, tempresult[findIndex]) ;
					};
		      
			  }while (findIndex!=-1);
	 		}}
	<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{question.problemContent}}
	</h4></div>
	<div class="panel panel-danger">
	<div class="panel-body">
	{{#
	for(var i=0;i<question.options.length;i=i+4){
	var addval = "";
	var selectval = "0";
    var pid="";

	}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i].imgUrl}}" 
            alt="{{question.options[i].optionName}}">
            <div class="caption">
                <h6 class="text-center">{{question.options[i].optionName}}</h6>
				<p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						selectval = results[resultIndex].frequency;
						pid = results[resultIndex].problemId;
					}
				}
				}}
					<select ptype="1" class="form-control" option="get" id="select{{i}}">
					{{#			
						var disableFlag = selectval=="0"?true:false;
						for(var m=0;m<commonInfo.frequency.length;m++){
					}}
						<option value="{{commonInfo.frequency[m].val}}" 
						{{selectval==commonInfo.frequency[m].val?"selected":""}}
						>{{commonInfo.frequency[m].name}}</option>
					{{#}}}
					</select>
				</p>
                <p>
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i].validate}} unit="g" pid="{{pid}}" frequency="{{selectval}}" id="input{{i}}" oid={{question.options[i].optionId}} placeholder="每次摄入" value="{{addval}}" {{disableFlag?"disabled":""}}>
           				 <span class="input-group-addon">{{question.options[i].model}}</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#
if(i+1<question.options.length){ 
var addval = "";
var selectval = "0";
var pid="";
}}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+1].imgUrl}}" 
            alt={{question.options[i+1].optionName}}>
            <div class="caption">
                <h6 class="text-center">{{question.options[i+1].optionName}}</h6>
				<p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+1].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						selectval = results[resultIndex].frequency;
						pid = results[resultIndex].problemId;
					}
				}
				}}
					<select ptype="1" class="form-control" option="get" id="select{{i+1}}">
					{{#			
						var disableFlag = selectval=="0"?true:false;
						for(var m=0;m<commonInfo.frequency.length;m++){
					}}
						<option value="{{commonInfo.frequency[m].val}}"
						{{selectval==commonInfo.frequency[m].val?"selected":""}}
						>{{commonInfo.frequency[m].name}}</option>
					{{#}}}
					</select>
				</p>
                <p>
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+1].validate}} unit="g" id="input{{i+1}}" pid="{{pid}}" frequency="{{selectval}}" oid={{question.options[i+1].optionId}} placeholder="每次摄入" value="{{addval}}" {{disableFlag?"disabled":""}}>
           				 <span class="input-group-addon">{{question.options[i+1].model}}</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#}

if(i+2<question.options.length){
var addval = "";
var selectval = "0";
var pid="";
 }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+2].imgUrl}}" 
            alt={{question.options[i+2].optionName}}>
            <div class="caption">
                <h6 class="text-center">{{question.options[i+2].optionName}}</h6>
				<p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+2].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						selectval = results[resultIndex].frequency;
						pid = results[resultIndex].problemId;
					}
				}
				}}
					<select ptype="1" class="form-control" option="get" id="select{{i+2}}">
					{{#			
						var disableFlag = selectval=="0"?true:false;
						for(var m=0;m<commonInfo.frequency.length;m++){
					}}
						<option value="{{commonInfo.frequency[m].val}}"
						{{selectval==commonInfo.frequency[m].val?"selected":""}}
						>{{commonInfo.frequency[m].name}}</option>
					{{#}}}
					</select>
				</p>
                <p>
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+2].validate}} unit="g" id="input{{i+2}}" pid="{{pid}}" frequency="{{selectval}}" oid={{question.options[i+2].optionId}} placeholder="每次摄入" value="{{addval}}" {{disableFlag?"disabled":""}}>
           				 <span class="input-group-addon">{{question.options[i+2].model}}</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{#}

if(i+3<question.options.length){
var addval = "";
var selectval = "0";
var pid="";
 }}
	<div class="col-xs-3">
        <div class="thumbnail">
            <img src="${common.basepath}/{{question.options[i+3].imgUrl}}" 
            alt="{{question.options[i+3].optionName}}">
            <div class="caption">
                <h6 class="text-center">{{question.options[i+3].optionName}}</h6>
				<p>
				{{#
				if(!common.isEmpty(results)){
					var resultIndex = _.findIndex(results, {
						optionId: question.options[i+3].optionId
					});
					if(resultIndex!=-1){
						addval = results[resultIndex].addvalue;
						selectval = results[resultIndex].frequency;
						pid = results[resultIndex].problemId;
					}
				}
				}}
					<select ptype="1" class="form-control" option="get" id="select{{i+3}}">
					  {{#			
						var disableFlag = selectval=="0"?true:false;
						for(var m=0;m<commonInfo.frequency.length;m++){
					  }}
						<option value="{{commonInfo.frequency[m].val}}"
						{{selectval==commonInfo.frequency[m].val?"selected":""}}
						>{{commonInfo.frequency[m].name}}</option>
					{{#}}}
					</select>
				</p>
                <p>
                    <div class="input-group">
            			<input ptype="1" type="text" class="form-control" regexp={{question.options[i+3].validate}} unit="g" id="input{{i+3}}" pid="{{pid}}" frequency="{{selectval}}" oid={{question.options[i+3].optionId}} placeholder="每次摄入" value="{{addval}}" {{disableFlag?"disabled":""}}>
           				 <span class="input-group-addon">{{question.options[i+3].model}}</span>
       				 </div>
                </p>
            </div>
        </div>
    </div>
{{# } 

	}

}}
</div>
</div>
</script>

<!-- 多选题模板-烹饪习惯 -->
<script id="p10" type="text/html">
{{# var question = questionnaire.problem.current ;
		   var results = new Array();
           var tempresult = questionnaire.result.data;
		   var findIndex = -1;
		   do{
			    findIndex = _.findIndex(tempresult, {
					problemId: question.problemId
					});
			   	if(findIndex!=-1){
					results.push(tempresult[findIndex]);
					tempresult = _.without(tempresult, tempresult[findIndex]) ;
				};
		      
			}while (findIndex!=-1);
		   
			  var answerContent= "";
		}}
		<div class="col-xs-12 alert alert-danger"><h4>{{ questionnaire.problem.titleindex+1 }}、{{ question.problemContent}}
		</h4></div>
		<div class="panel panel-danger">
	    <div class="panel-body">
		{{# var optlength = question.options.length ;}}
		{{# for(var i = 0; i <optlength; i++){ }}
		<div class="col-xs-12">
			<h5>
				<label class="radio-inline">
					<input ptype="2" type="checkbox" name={{ question.problemId }}  pid={{ question.problemId }} id={{ question.options[i].optionId }} filter="disabled"
					{{# 
						var isChecked = false;
						if(!common.isEmpty(results)){
							var len = results.length;
							do{
								--len;
								isChecked=question.options[i].optionId==results[len].optionId?true:false;
								if(results[len].optionType=="1")
								answerContent=results[len].addvalue;
							}while(len>0&&!isChecked);
						}
					}}
					{{ isChecked?"checked":"" }}
					>
					{{ question.options[i].optionName }}
				</label>
			</h5>
		</div>
		{{# } }}
		{{# if(question.hasOther){ }}
			<div class="col-xs-12">
				<h5>
					<label class="radio-inline">
					其他：<input ptype="1" type="text"  class="underline"  pid={{ question.problemId }} value={{answerContent}} ></input>
					</label>
				</h5>
			</div>
		{{# } }}
		</div>
		</div>
</script>

<!-- 单张图片填空模板 -->
<script id="p11" type="text/html">
			{{# 
			  var question = questionnaire.problem.current ;
	 		}}
	<div class="col-xs-12">
		<img src="${common.basepath}/{{question.options[0].imgUrl}}" class="img-responsive" nolarge>
	</div>
</script>