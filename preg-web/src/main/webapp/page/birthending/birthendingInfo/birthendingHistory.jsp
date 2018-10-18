<%--
  Created by IntelliJ IDEA.
  User: ligp
  Date: 2018/9/5
  Time: 下午3:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    .div-inline{
        width: 300px;
        padding: 2px;
    }
    .title-span{
        width: 70px;
        display: inline-block;
        text-align: right;
    }
    .intake-sm{
        width: 180px;
        height: 30px;
        margin: 0px;
        font-size: 12px;
    }
    .intake-input{
        width: 155px;
        height: 30px;
        color: black;
        margin: 0px;
        font-size: 12px;
    }
    .intake-input-group-addon {
        padding: 7px 6px;
        margin-right: 0px;
        font-size: 12px;
    }
    .form-inline select.form-control {
        display: inline-block;
        width: 218px;
        vertical-align: middle;
    }
    .input-collect{
        width:71px;
        height: 30px;
        margin: 0px;
        font-size: 12px;
    }
</style>
<script type="text/javascript">
 //取消回车事件
 $(document).keydown(function(event){
     switch(event.keyCode){
         case 13:return false;
     }

 });
 // 分娩结局信息
 var birthEndingPojo = "${birthEndingPojo }";
 var customer = "${customerPojo }";
 var custId = "${customerPojo.custId }";
 var customerBirthday = "${customerPojo.custBirthday }";
 var insPojo = ${insPojo };
 var archivesList = "${archivesList }";
 //补充剂自动补全数组
 var productAllMap = ${diseaseList};
 var productListData = [];
 if(!_.isEmpty(productAllMap)){
     for(var key in productAllMap){
         var product = productAllMap[key];
         productListData.push({name:product.diseaseName, value:product});
     };
 }
 var hospitalMap = ${hospitalList };
 var hospitalListData = [];
 if(!_.isEmpty(hospitalMap)){
     for(var key in hospitalMap){
         var hospital = hospitalMap[key];
         hospitalListData.push({name:hospital.hospitalName, value:hospital});
     };
 }
 var birthDirectionMap = ${codeList}
 var birthDirectionListData = [];
 if(!_.isEmpty(birthDirectionMap)){
     for(var key in birthDirectionMap){
         var birthDirection = birthDirectionMap[key];
         birthDirectionListData.push({name:birthDirection.codeName, value:birthDirection});
     };
 }   
</script>
<script type="text/javascript">
/**
 * 小时/分钟 前面补0
 */
function formatDate(date) {
	if(!common.isEmpty(date)) {
			if(date.length == 1) {
				return "0"+date;
			}else {
				return date;
			}
	}else {
		return date;
	}
}

/**
 * 分娩时间校验格式化
 */
function fmtTime() {
	let year = new Array();  
	let hour = new Array(); 
	let minutes = new Array(); 
	const num = "${birthEndingPojo.size() }";
	if(common.isEmpty(num) || num == "0") {
		$("#birrhInfoTable_tbody").show();
		return;
	}
	<c:forEach items="${birthEndingPojo }" var="date"> 
	　　  year.push("${date.birthDate}");
		hour.push("${date.baseTimeHour}");
		minutes.push("${date.baseTimeMinuters}");
	</c:forEach> 
	for(var i=0;i<year.length;i++){
		let result = "";
		if(common.isEmpty(hour[i]) || common.isEmpty(minutes[i])) {
			result = year[i];
		}else {
			result = year[i] + " " +formatDate(hour[i])+":"+formatDate(minutes[i]);
		}
		$("td[name='birthEndingDateTd']:eq("+i+")").html(result);
		$("#birrhInfoTable_tbody").show();
	}; 
}
</script>
<script type="text/javascript">
$(document).ready(function() {
   // 初始化分娩时间
   fmtTime();
}); 
</script>
<script charset="UTF-8" src="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo_validate.js"></script>
<script charset="UTF-8" src="${common.basepath}/page/birthending/birthendingInfo/birthendingInfo.js"></script>
<body>
<!-- 个人信息 -->
<div class="panel panel-lightblue" id="guide_customerinfo_div">
    <div class="panel-heading text-left">
        <i class="fa fa-tag fa-fw"></i> <span>分娩结局录入列表</span>
    </div>
    <table class="table table-bordered table-padding-4">
        <thead>
	        <tr>
	            <td style="border: 0px; text-align: right;">病案号：</td>
	            <td style="border: 0px; text-align: left;">${customerPojo.custSikeId }</td>
	            <td style="border: 0px; text-align: right; ">ID：</td>
	            <td style="border: 0px; text-align: left; ">${customerPojo.custPatientId }</td>
	            <td style="border: 0px; text-align: right; ">姓名：</td>
	            <td style="border: 0px; text-align: left; ">${customerPojo.custName }</td>
	            <td style="border: 0px; text-align: right; ">身份证号：</td>
	            <td style="border: 0px; text-align: left; ">${customerPojo.custIcard }</td>
	        </tr>
	        <tr class="active">
	            <td class="text-center">序号</td>
	            <td class="text-center">分娩日期</td>
	            <td class="text-center">分娩孕周</td>
	            <td class="text-center">分娩医院</td>
	            <td class="text-center">产检医院</td>
	            <td class="text-center">登记时间</td>
	            <td class="text-center">登记人员</td>
	            <td class="text-center">操作</td>
	        </tr>
        </thead>
        <tbody id="birrhInfoTable_tbody" style="display:none;">
        <c:if test="${!empty birthEndingPojo}">
            <c:forEach var="birthEnding" items="${birthEndingPojo }" varStatus="status">
                <tr>       	
                    <td class="text-center">${status.count }</td>
                    <td class="text-center" name="birthEndingDateTd"></td>
                    <td class="text-center" name="birthEndingWeek">
                    <script>$("[name='birthEndingWeek']").eq("${status.index}").html(pregnancy.gestationalWeeksSupHtml("${birthEnding.birthWeeks }"));</script>
                    </td>
                    <td>${birthEnding.birthHospital }</td>
                    <td>${birthEnding.birthPregHospital }</td>
                    <td class="text-center"><fmt:formatDate value="${birthEnding.createTime}" pattern="yyyy-MM-dd" /></td>
                    <td class="text-center">${birthEnding.createUserName }</td>
                    <td class="text-center">
                        <a href="#" onclick="showBirthEnding('${birthEnding.birthId}','${birthEnding.custId }')">查看</a>
                        <%--<a href="javascript:void(0)" onclick="editBirthEnding('${birthEnding.birthId}')">编辑</a>--%>
                        <a href="javascript:void(0)" id="${birthEnding.birthId}" target="_parent">编辑</a>
                        <a href="#" onclick="deleteBirthEnding('${birthEnding.birthId}',this)">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty birthEndingPojo}">
            <tr>
                <td colspan="8" style="text-align: center">无分娩记录</td>
            </tr>
        </c:if>
        <tr id="birthInfoTable_add">
            <td colspan="8" style="text-align: center"><label><a href="#" onclick="addBirthEnding()"> + 添加</a></label></td>
        </tr>
        </tbody>
    </table>
</div>

<!-- 弹窗选择建档数据 -->
<form id="archivesListForm" name="editItemForm" action="${common.basepath }/${applicationScope.URL.Customer.QUERY_CUST_PREG_RECPRD}" class="form-horizontal" method="post" >
    <div id="archivesListModal" class="modal fade bs-example-modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left" id="tittle"></div>
                        <table class="table table-bordered" id="archivesTable">
                            <thead class="active">
	                            <th class="text-center">序号</th>
	                            <th class="text-center">建档日期</th>
	                            <th class="text-center">受孕方式</th>
	                            <th class="text-center">胎数</th>
	                            <th class="text-center">预产期</th>
	                            <th class="text-center">建档医师</th>
	                            <th class="text-center">操作</th>
                            </thead>
                            <tbody id="archives_tbody"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>
