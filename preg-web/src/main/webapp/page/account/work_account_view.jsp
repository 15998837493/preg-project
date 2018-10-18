<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>元素库</title>
</head>
<style>
.tooltip{
	width: 300px;
}
.echarts-body {
	width:100%;
	height:500px;
}
.echarts-top{
	height: 10px;
}
.echarts-top ul {
	list-style:none;
	margin-top:120px;
	padding-left:0px;
}
.echarts-top li {
	cursor:pointer;
	margin-left: 5px;
}
.echarts-left {
	width:86%;
	float: left;
	margin-left: 10px;
}
.echarts-right {
	width:20%;
	float: right;
	margin-top: -500px;
}
.echarts-right ul {
	list-style:none;
	margin-top: 80px;
	float: right;
}
.echarts-right ul img[name='button'] {
	cursor: pointer;
	vertical-align:middle;
}
.echarts-right ul a {
	color:#5598CF;
	vertical-align:middle;
	text-decoration:none;
	cursor: pointer;
}
.echarts-right ul a:hover {
	text-decoration:underline;
}
.echarts-right ul li {
	padding:6px;
}
.nav-tabs > li.active > a, .nav-tabs > li.active > a:hover, .nav-tabs > li.active > a:focus {
    color: #555;
    background-color: #dff0d8;
    border: 1px solid #337ab7;
    border-bottom-color: transparent;
    cursor: default;
}
.table{
	margin-bottom: 0px;
}
.panel-body{
	padding: 7px;
}
/*  小于1400px */
@media screen and (max-width:1400px){
	.echarts-left {
		width:90%;
		margin-left:20px;
	}
}
/*  大于1700px */
@media screen and (min-width:1700px){
	.echarts-left {
		width:95%;
		margin-left:10px;
	}
}
.content-ellipsis{
	overflow:hidden; 
	text-overflow:ellipsis; 
	white-space:nowrap;
}
</style>
<script type="text/javascript">
var diseaseList = ${diseaseList};// 疾病列表
//自动补全功能使用的集合（诊断名称）
var diseaseListData = [];
// 遍历所有诊断疾病
$.each(diseaseList, function(index, obj){
	diseaseListData.push({name:obj.diseaseName, val:obj});
});
var diseaseMap = ${diseaseMap};// 疾病map
var userList = ${userList};// 医师列表
var zhuanDoctorList = ${zhuanDoctorList};// 转诊医师列表
var masItemNames = ${masItemNames};// 系统营养评价项目
var masItemAuthor = ${masItemAuthor};// 全部评价项目操作者
var userMap = new Map();
if(!_.isEmpty(userList)){
	$.each(userList, function(index, user){
		userMap.set(user.userId, user.userName);
	});
}
var zhuanUserMap = new Map();
if(!_.isEmpty(zhuanDoctorList)){
	$.each(zhuanDoctorList, function(index, user){
		zhuanUserMap.set(user.doctorId, user.doctorName);
	});
}
var itemNamesMap = new Map();
if(!_.isEmpty(masItemNames)){
	$.each(masItemNames, function(index, user){
		itemNamesMap.set(user.inspectCode, user.inspectName);
	});
}
var masItemAuthorMap = new Map();
if(!_.isEmpty(masItemAuthor)){
	$.each(masItemAuthor, function(index, user){
		masItemAuthorMap.set(user.userId, user.userName);
	});
}
</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/echarts_option.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_view.js"></script>
<body>
<!-- 工作量统计 -->
<div class="panel panel-lightblue" style="margin-top: 15px;">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 工作量统计
		<button type="button" class="btn btn-sm btn-default" style="float: right;padding: 1px 10px;" onclick="createExcelButton();">
			导出Excel
		</button>
	</div>
	<div class="panel-body form-inline">
		<form id="workQueryForm" action="${common.basepath }${applicationScope.URL.WorkAccount.WORK_ACCOUNT_QUERY}">
			<input type='text' id='startDate' name="startDate" class='form-control' placeholder='请选择开始日期' readonly="readonly" />
			至
			<input type='text' id='endDate' name="endDate" class='form-control' placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
			<select class="form-control" id="zhuanSelect" name="zhuanSelect" multiple="multiple" style="height: 34px; width: 170px;">
			</select>
			<select class="form-control" id="userSelect" name="userSelect" multiple="multiple" style="height: 34px; width: 210px;">
			</select>
			<input type='text' id='diseaseName' name="diseaseName" class='form-control' placeholder='请输入诊断名称' />
			<select class="form-control" id="masItems" name="masItems" multiple="multiple" style="height: 34px; width: 210px;">
			</select>
			<select class="form-control" id="workSelect" name="workSelect" multiple="multiple" style="height: 34px; width: 210px;">
			</select>
			<input type="hidden" id='diseaseId' name="diseaseId" />
			<input type="hidden" id='diagnosisUser' name="diagnosisUser" />
			<input type="hidden" id='diagnosisUserName' name="diagnosisUserName" />
			<input type="hidden" id='diagnosisZhuanUser' name="diagnosisZhuanUser" />
			<input type="hidden" id='diagnosisMasItems' name="diagnosisMasItems" />
			<input type="hidden" id='diagnosisMasItemAuthors' name="diagnosisMasItemAuthors" />
			<button type="button" id="searchButton" name="intakeTemplatePage" class="btn btn-default">
				<i class="fa fa-search fa-fw"></i>查询
			</button>
		</form>
	</div>
	<table id="accountListTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th colspan="5" class="text-center">统计汇总</th>
				<th colspan="6" class="text-center">初诊阶段分布</th>
			</tr>
			<tr class="active">
				<th class="text-center">时间段</th>
				<th class="text-center">就诊人数</th>
				<th class="text-center">就诊人次</th>
				<th class="text-center">初诊人数</th>
				<th class="text-center">复诊率（%）</th>
				<th class="text-center">孕早期</th>
				<th class="text-center">占比（%）</th>
				<th class="text-center">孕中期</th>
				<th class="text-center">占比（%）</th>
				<th class="text-center">孕晚期</th>
				<th class="text-center">占比（%）</th>
			</tr>
		</thead>
		<tfoot id="tfood">
			<tr><td colspan="100" class="text-center"><h4>没有数据！</h4></td></tr>
		</tfoot>
	</table>
	<div class="panel-body">月平均就诊人次：<b id="monthAverageNum"></b></div>
	<table id="diseaseFrequencyTable" class="table table-bordered" style="border: 1px solid #ddd; width: 25%;">
		<thead>
			<tr class="active">
				<th class="text-center">诊断名称</th>
				<th class="text-center">诊断频次（次/人）</th>
			</tr>
		</thead>
		<tbody><tr><td class="text-center" colspan="100"><h4>没有数据！</h4></td></tr></tbody>
	</table>
	<div class="panel-body form-inline" style="border-top: 0px;padding: 5px;">
		<input type='text' class='form-control' style="width: 250px;" placeholder='请输入诊断名称' id="addDiseaseFrequency"/>
	</div>
	<div class="panel-body" style="padding: 0px;">
		<div class="panel-group no-bottom" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
						   href="#collapseOne" style="text-decoration:none;display: block;">
							统计列表 <span class="glyphicon glyphicon-collapse-down"></span>
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse">
					<form id="statisticDataForm" name="statisticDataForm" action="${common.basepath }${applicationScope.URL.WorkAccount.STATISTICS_DATA_QUERY}">
						<input type="hidden" id='startStatisticDate' name="startDate"/>
						<input type='hidden' id='endStatisticDate' name="endDate"/>
						<input type="hidden" id='diseaseIdStatistic' name="diseaseId" />
						<input type="hidden" id='diagnosisUserStatistic' name="diagnosisUsers" /> 
						<input type="hidden" id='diagnosisZhuanUserStatistic' name="diagnosisZhuanUser" />
						<input type="hidden" id='diagnosisMasItemsStatistic' name="diagnosisMasItems" />
						<input type="hidden" id='diagnosisMasItemAuthorsStatistic' name="diagnosisMasItemAuthors" />
					</form>
					<table id="statisticDataTable" class="table table-bordered" style="table-layout:fixed;">
						<thead>
							<tr class="active">
								<th class="text-center" style="width: 10%;">就诊日期</th>
								<th class="text-center" style="width: 10%;">病案号</th>
								<th class="text-center" style="width: 10%;">姓名</th>
								<th class="text-center" style="width: 10%;">孕周</th>
								<th class="text-center" style="width: 10%;">身高</th>
								<th class="text-center" style="width: 10%;">孕前体重</th>
								<th class="text-center" style="width: 10%;">现体重</th>
								<th class="text-center" style="width: 10%;">诊断</th>
								<th class="text-center" style="width: 10%;">医师</th>
								<th class="text-center" style="width: 10%;">操作</th>
							</tr>
						</thead>
						<tfoot id="tstatic">
							<tr><td colspan="100" class="text-center"><h4>没有数据！</h4></td></tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 工作量对比 -->
<div class="panel panel-lightblue">
	<div class="panel-heading text-left">
		<i class="fa fa-tag fa-fw"></i> 工作量对比
		<button type="button" class="btn btn-sm btn-default" style="float: right;padding: 1px 10px;" onclick="createCompareExcel();">
			导出Excel
		</button>
	</div>
	<div class="panel-body" style="padding: 0px;">
		<ul id="myTab" class="nav nav-tabs row-top">
			<li class="active"><a href="#tab1" data-toggle="tab">孕期营养门诊门诊量统计</a></li>
			<li><a href="#tab2" data-toggle="tab">不同出诊单元门诊量统计</a></li>
			<li><a href="#tab3" data-toggle="tab">初诊患者孕期分布</a></li>
			<li><a href="#tab4" data-toggle="tab">就诊人数对比</a></li>
			<li><a href="#tab5" data-toggle="tab">初诊人数对比</a></li>
			<li><a href="#tab6" data-toggle="tab">复诊率</a></li>
			<li><a href="#tab7" data-toggle="tab">诊断频次</a></li>
		</ul>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane active" id="tab1">
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab1_echart.js"></script>
				<div class="panel-body form-inline">
					<form id="tab1QueryForm" action="${common.basepath }${applicationScope.URL.WorkAccount.WORK_ACCOUNT_QUERY_TAB1}">
						<input type='text' id='tab1StartDate' name="startDate" class='form-control' placeholder='请选择开始日期' readonly="readonly" />
						至
						<input type='text' id='tab1EndDate' name="endDate" class='form-control' placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
						<select class="form-control" id="tab1UserSelect" name="tab1UserSelect" multiple="multiple" style="height: 34px; width: 170px;">
			            </select>
			            <input type="hidden" id='tab1SelectDiagnosisUser' name="diagnosisUser" />
						<button type="button" id="tab1CreatButton" class="btn btn-default">
							生成图表
						</button>
					</form>
				</div>
				<div class="panel-body" id="tab1Echart" style="border-top: 1px solid #ddd; display: none;"> 
   					<div class="echarts-left">
    					<div id="echartsTab1Count" style="height:500px"></div>
   					</div>
   					<div class="echarts-right"> 
    					<ul> 
     						<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhuzhuang.png" id="tab1_bar_button" name="button" /> <a name="button">柱状图</a></li> 
     						<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/tiaoxing.png" id="tab1_leaf_button" name="button" /> <a name="button">条形图</a></li> 
    					</ul> 
   					</div> 
  				</div>
  				<table class="table table-bordered table-hover" id="tab1Table" >
  					<thead>
						<tr><td colspan="100" class="text-center"><h4>没有数据！</h4></td></tr>
					</thead>
					<tbody></tbody>
  				</table>
			</div>
			<div class="tab-pane fade" id="tab2">
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab2_echart.js"></script>
				<div class="panel-body form-inline">
					<form id="nutritionQueryForm" action="${common.basepath }${applicationScope.URL.WorkAccount.OUTPATIENT_DATA_QUERY}">
						<input type='text' id='outpatientStartDate' name="startDate" class='form-control' placeholder='请选择开始日期' readonly="readonly" />
						至
						<input type='text' id='outpatientEndDate' name="endDate" class='form-control' placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
						<select class="form-control" id="outpatientUserSelect" name="diagnosisUserList" multiple="multiple" style="height: 34px; width: 170px;">
						</select>
						<button type="button" id="outpatientButton" name="creativeEchartBut" class="btn btn-default">
							生成图表
						</button>
					</form>
				</div>
				<div class="panel-body" id="tab2EchartLeft" style="border-top: 1px solid #ddd; display: none;"> 
   					<div id="tab2CreativeEchartLeft" class="col-xs-6" style="height:500px;"></div>
   					<div class="col-xs-2 echarts-top"> 
   						<ul> 
   							<li id="tab2_zhuzhuang" name="button" style="padding:6px;"><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhuzhuang.png" /><a name="button">柱状图</a></li> 
   							<li id="tab2_tiaoxing" name="button" style="padding:6px;"><img alt="加载失败" src="${common.basepath }/common/images/echarts/tiaoxing.png" /><a name="button">条形图</a></li> 
   						</ul> 
   					</div> 
   					<div id="tab2CreativeEchartRight" class="col-xs-4" style="height:500px;margin-left: 0px;"></div>
   				</div>
				<table id="tab2Table" class="table table-bordered table-hover">
					<thead>
						<tr><td colspan="100" class="text-center"><h4>没有数据！</h4></td></tr>
					</thead>
					<tbody></tbody>
				</table>
  			</div> 
			<div class="tab-pane fade" id="tab3">
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab3_echart.js"></script>
				<div class="panel-body form-inline">
					<form id="nutritionQueryForm" action="${common.basepath }${applicationScope.URL.WorkAccount.WORK_ACCOUNT_QUERY}">
						<input type='text' id='pregStartDate' name="startDate" class='form-control' placeholder='请选择开始日期' readonly="readonly" />
						至
						<input type='text' id='pregEndDate' name="endDate" class='form-control' placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
						<select class="form-control" id="pregUserSelect" name="diagnosisUser" multiple="multiple" style="height: 34px; width: 170px;">
						</select>
						<button type="button" id="pregButton" name="creativeEchartBut" class="btn btn-default">
							生成图表
						</button>
					</form>
				</div>
				<!-- <div id="tab3Title" style="text-align: center; display: none;"><h1>初诊患者孕期分布</h1></div> -->
				<div class="panel-body" id="tab3EchartLeft" style="border-top: 1px solid #ddd; display: none;"></div> 
				<table id="tab3Table" class="table table-bordered table-hover">
					<thead>
						<tr><td colspan="100" class="text-center"><h4>没有数据！</h4></td></tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
			<div class="tab-pane fade" id="tab4">
				<!-- 引入 echarts配置 -->
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab4_echart.js"></script>
  				<div class="panel-body form-inline">
   					<input id="firstScope1" name="startDate" type="text" class="form-control" placeholder='请选择开始日期' readonly="readonly"/> 至 
   					<input id="firstScope2" name="endDate" type="text" class="form-control" placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/> VS 
   					<input id="lastScope1" name="startDate2" type="text" class="form-control" placeholder='请选择开始日期' readonly="readonly"/> 至 
   					<input id="lastScope2" name="endDate2" type="text" class="form-control" placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
   					<select class="form-control" id="diagnosis_count_doctor" multiple="multiple"></select> 
   					<input type="hidden" id="userId" name="diagnosisUser" /> 
   					<button type="button" id="search_diagnosis_value" name="echarts_button" class="btn btn-default">生成图表</button> 
 				</div> 
  				<div class="panel-body" style="padding-top: 0px; border-top: 1px solid #ddd;" id="diagnosisCountBody"> 
  					<div class="echarts-body"> 
   						<div class="echarts-left">
    						<div id="echartsDiagnosisCount" style="height:500px;"></div>
   						</div> 
   						<div class="echarts-right"> 
    						<ul> 
     							<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhuzhuang.png" id="diagnosis_count_bar_button" name="button" /> <a name="button">柱状图</a></li> 
     							<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/tiaoxing.png" id="diagnosis_count_leaf_button" name="button" /> <a name="button">条形图</a></li> 
    						</ul> 
   						</div> 
  					</div> 
  				</div>	
  				<table class="table table-bordered table-hover" id="diagnosisCountTable"> 
   					<tfoot id="diagnosisCounttfood"> 
   	 					<tr>
     						<td colspan="100" class="text-center"><h4>没有数据！</h4></td>
    					</tr> 
   					</tfoot> 
  				</table>
			</div>
			<div class="tab-pane fade" id="tab5">
				<!-- 引入 echarts配置 -->
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab5_echart.js"></script>
  				<div class="panel-body form-inline">
   					<input type="hidden" name="type" value="firstDiagnosis"/>
   					<input id="firstDiagnosisScope1" name="startDate" type="text" class="form-control" placeholder='请选择开始日期' readonly="readonly"/> 至 
   					<input id="firstDiagnosisScope2" name="endDate" type="text" class="form-control" placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/> VS 
   					<input id="firstDiagnosislastScope1" name="startDate2" type="text" class="form-control" placeholder='请选择开始日期' readonly="readonly"/> 至 
   					<input id="firstDiagnosislastScope2" name="endDate2" type="text" class="form-control" placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
   					<select class="form-control" id="first_diagnosis_count_doctor" multiple="multiple"></select> 
   					<input type="hidden" id="first_diagnosis_userId" name="diagnosisUser" /> 
   					<button type="button" id="search_first_diagnosis_value" name="echarts_button" class="btn btn-default">生成图表</button> 
				</div> 
  				<div class="panel-body" style="padding-top: 0px; border-top: 1px solid #ddd;" id="firstDiagnosisCountBody">   				 
  					<div class="echarts-body"> 
   						<div class="echarts-left">
    						<div id="echartsFirstDiagnosisCount" style="height:500px;"></div>
   						</div> 
   						<div class="echarts-right"> 
    						<ul> 
     							<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhuzhuang.png" id="first_diagnosis_count_bar_button" name="button" /> <a name="button">柱状图</a></li> 
     							<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/tiaoxing.png" id="first_diagnosis_count_leaf_button" name="button" /> <a name="button">条形图</a></li> 
    						</ul> 
   						</div> 
  					</div>
  				</div> 
  				<table class="table table-bordered table-hover" id="firstDiagnosisCountTable">  
   					<tfoot id="firstDiagnosisCounttfood"> 
   	 					<tr>
     						<td colspan="100" class="text-center"><h4>没有数据！</h4></td>
    					</tr> 
   					</tfoot> 
  				</table>
			</div>
			<div class="tab-pane fade" id="tab6">
				<!-- 引入 echarts配置 -->
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab6_echart.js"></script>
  				<div class="panel-body form-inline">
   					<input id="rateScope1" name="startDate" type="text" class="form-control" placeholder='请选择开始日期' readonly="readonly"/> 至 
   					<input id="rateScope2" name="endDate" type="text" class="form-control" placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/> VS 
   					<input id="rateLastScope1" name="startDate2" type="text" class="form-control" placeholder='请选择开始日期' readonly="readonly"/> 至 
   					<input id="rateLastScope2" name="endDate2" type="text" class="form-control" placeholder='请选择结束日期' readonly="readonly" disabled="disabled"/>
   					<select class="form-control" id="rate_doctor" multiple="multiple"></select> 
   					<input type="hidden" id="rate_userId" name="diagnosisUser" /> 
   					<button type="button" id="search_rate_value" name="echarts_button" class="btn btn-default">生成图表</button> 
				</div> 
  				<div class="panel-body" style="padding-top: 0px; border-top: 1px solid #ddd;" id="rateBody">  
  					<div class="echarts-body"> 
   						<div class="echarts-left">
    						<div id="echartsRate" style="height:500px;"></div>
   						</div> 
   						<div class="echarts-right"> 
    						<ul> 
     							<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/zhuzhuang.png" id="rate_bar_button" name="button" /> <a name="button">柱状图</a></li> 
     							<li><img alt="加载失败" src="${common.basepath }/common/images/echarts/tiaoxing.png" id="rate_leaf_button" name="button" /> <a name="button">条形图</a></li> 
    						</ul> 
   						</div> 
  					</div>
  				</div>	 
  				<table class="table table-bordered table-hover" id="rateTable"> 
   					<tfoot id="ratetfood"> 
   	 					<tr>
     						<td colspan="100" class="text-center"><h4>没有数据！</h4></td>
    					</tr> 
   					</tfoot> 
  				</table>
			</div>
			
			<div class="tab-pane fade" id="tab7">
				<!-- 引入 echarts配置 -->
				<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/account/work_account_tab7_echart.js"></script>
			</div>
		</div>
	</div>
</div>
</body>
</html>
