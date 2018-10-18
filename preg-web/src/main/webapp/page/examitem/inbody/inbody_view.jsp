<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>孕期人体成分评估--${custInfo.custName}</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
var inbodyData;
var inbodyRow;
var inbodyTable;

var inbodyTableOption = {
		id: "inbodyListTable",
		form: "inBodyQuery",
		columns: [
			{"data": null,"sClass": "text-center",
				"render":  function (data, type, row, meta) {
	        		return "<input type='radio' name='tableRadio' value='' />";
	        	}
			},
			{"data": "userId","sClass": "text-center" },
			{"data": "name","sClass": "text-center" },
			{"data": "age","sClass": "text-center" },
			{"data": "height","sClass": "text-center" },
			{"data": "birthday","sClass": "text-center"},
			{"data": "userExamDate","sClass": "text-center",
				"render":  function (data, type, row, meta) {
					if(common.isEmpty(data)){
						return "";
					}
	        		return data.substr(0,4) + "-" + data.substr(4,2) + "-" + data.substr(6,2) + " " + 
	        			   data.substr(8,2) + ":" + data.substr(10,2) + ":" + data.substr(12,2);
	        	}
			}
		],
		rowClick: function(data, row){
			inbodyData = data;
			inbodyRow = row;
			$("#datetimes").val(data.datetimes);
			$("#url").val(data.url);
		},
		condition : "inbodyCondition"
};

$().ready(function (){
	//日期插件格式定义--出生日期
	common.initDate(null,null,null,"#userExamDate");
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	$("#userExamDate").datetimepicker("setEndDate",nowDate);
	$("#userExamDate").datetimepicker("update",nowDate);

	inbodyTable = datatable.table(inbodyTableOption);
	
	// 按钮点击事件
	$("button[name='inbodyPage']").click(function(){
		if(this.id == 'addButton' && isChooseInbody()){
			var url = URL.get("Inbody.INBODY_ADD");
			var params = "datetimes=" + $("#datetimes").val() + "&url=" + $("#url").val() + "&custId=${custInfo.custId}&diagnosisId=${diagnosisId}";
			ajax.post(url, params, dataType.json, function(data){
				if(!common.isEmpty(data.value.wt) && common.isEmpty(opener.diagnosisCustWeight)){
					//更新父页面数据
					opener.inbodyUpdateWt(data.value.wt);
					//更新父页面曲线图
					if(opener.showWeight && $.isFunction(opener.showWeight)){
						opener.showWeight(data.value.wt);
					}
				}
				//更新检查项目状态
				opener.updateInspectStatus("${inspectId}", data.value.bcaId);
				var ajaxUrl = URL.get("Inbody.INBODY_REPORT") + "?id=${inspectId}";
				var params = "";
				ajax.post(ajaxUrl, params, dataType.json, function(data){
					window.location.href = PublicConstant.basePath +"/"+ data.value;
				});
			});
		}

		if(this.id == 'searchButton'){
			inbodyTable = datatable.table(inbodyTableOption);
		}
	});
});

function isChooseInbody(){
	if(common.isEmpty($("#datetimes").val())){
	    layer.alert('请选择操作的记录');
	    return false;
	}else{
		return true;
	}
}

</script>
<body>
<input type="hidden" id="datetimes" />
<input type="hidden" id="url" />

<div class="container-fluid">
<div class="row">
	<ul id="timeline">
		<li class="timeline-inverted">
			<div class="timeline-panel" id="timeline-panel">
				<div class="timeline-body form-horizontal">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-center">人体成分客户列表</div>
						<div class="panel-body form-inline" id="inbodyCondition">
							<form id="inBodyQuery" name="inBodyQuery" action="${common.basepath}${applicationScope.URL.Inbody.INBODY_QUERY}" method="post">
		                        <input name="name" class="form-control" type="text" placeholder="请输入姓名" value="${custInfo.custName }" />
			                    <div class="input-group">
							      	<input id="userExamDate" name="userExamDate" type="text" class="form-control form_date" placeholder="请选择检测日期" readonly value=""/>
							      	<span class="input-group-btn">
							        	<button class="btn btn-default" type="button" onclick="common.dateShow('userExamDate')"><i class="fa fa-calendar fa-fw"></i>选择</button>
							      	</span>
					   			</div>
					      		<button type="button" id="searchButton" name="inbodyPage" class="btn btn-default"><i class="fa fa-search fa-fw"></i>查询</button>
					      		<div class="vertical-line"></div>
					      		<button id="addButton" name="inbodyPage" type="button" class="btn btn-default">
						     		<i class="fa fa-refresh fa-fw"></i> 同步数据
						  		</button>
							</form>
						</div>
						<div class="table-responsive">
							<table id="inbodyListTable" class="table table-bordered table-hover padding-zero margin-zero">
							<thead>
								<tr class="active">
									<th class="text-center">选择</th>
									<th class="text-center">编号</th>
									<th class="text-center">姓名</th>
							     	<th class="text-center">年龄</th>
							     	<th class="text-center">身高</th>
							     	<th class="text-center">出生日期</th>
							     	<th class="text-center">检测日期</th>
							   </tr>
							</thead>
							</table>
						</div>
					</div>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>
</body>
</html>
