<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>复查预约--${custName}</title>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
var pregCourseBookList = ${pregCourseBookList};
var courseBooking = [];

$.validator.addMethod("oneDigit", function(value,element) {
if(element.value.trim()==""){
	return true;
}
return new RegExp("^[0-9]+(.[0-9]{0,1})?$").test(element.value);
}, '必须是数字，最多支持一位小数');

var courseOption = {
	rules : {
		heatRate : {
			maxlength : 4,
			intege1:true
		},
		preprandialBooldGlucose : {
			maxlength : 4,
			oneDigit : true
		},
		postprandialBooldGlucose : {
			maxlength : 4,
			oneDigit : true
		}
	},
	// 设置错误信息显示到指定位置
	errorPlacement : function(error, element) {
		element = element.parent();
		common.showmassage(error, element);
	},
	success : $.noop,
	submitHandler : function(form) {
		$(form).ajaxPost(dataType.json, function(data){
			updateDiagnosisStatus();
			var id = $("#bookingId").val();
		 	$("#"+id+"_status").html("已反馈");
			$("#editFeedbackModal").modal("hide");
			layer.alert("保存成功");
		}, false, false);
	}
};

/**
 * 比较日期大小，格式为yyyy-MM-dd
 */
function dateCompare(date1,date2){
	date1 = date1.replace(/\-/gi,"/");
	date2 = date2.replace(/\-/gi,"/");
	var time1 = new Date(date1).getTime();
	var time2 = new Date(date2).getTime();
	if(time1 > time2){
		return 1;
	}else if(time1 == time2){
		return 0;
	}else{
		return -1;
	}
}

$(function(){
	//初始化日期
	common.initDate(null,null,null,"#courseDate","1990-01-01");
	var nowDate = common.dateFormatToString(new Date(),"yyyy-MM-dd");
	$("#courseDate").datetimepicker("setEndDate",nowDate);
	//表单验证
	validator = $("#editFeedbackForm").validate(courseOption);
	common.requiredHint("editFeedbackForm", courseOption);
	var bookingList = [];
	$.each(pregCourseBookList,function(index,val) {
		/* if(dateCompare(val.bookingDate,diagnosisJson.diagnosisDate) == 1) {// 过滤：预约日期必须大于接诊日期
			bookingList.push(val);
		} */
		bookingList.push(val);
	});
	initCourseBooing(bookingList);
});
/**
 * 初始化课程数据
 */
function initPregCourseBooking(scheduleMap){
	$("#course_booking_tbody").empty();
	var days = ["周日", "周一", "周二", "周三", "周四", "周五", "周六"];//周数的中文写法
	for(var i = 1; i <= 14; i++){//按需求要的两周14天
		var dd = getDateByAfterDays(i);
		var w = parseInt(new Date(dd).getDay());
		var scheduleListOneDay = scheduleMap[days[w]];
		if(!common.isEmpty(scheduleListOneDay)){
			$.each(scheduleListOneDay, function(index, value){
				var coursePreson = 0;
				if(!common.isEmpty(value.coursePresonFirst) && i <= 7){
					coursePreson = value.coursePresonFirst;
				} else if(!common.isEmpty(value.coursePresonSecond)){
					coursePreson = value.coursePresonSecond;
				}
				var trHtml = "onclick='checkThisBox(this);' ondblclick='addThisCourse(this);'";
				var disHtml = "";
				if(parseInt(coursePreson) >= parseInt(value.scheduleMaxPerson)){
					trHtml = "  style='color: gray;'";
					disHtml = "disabled";
				}
				$("#course_booking_tbody").append(
					"<tr "+trHtml+">" +
					"  <td class='hide'>{\"archivesId\":\""+preArchiveJson.id+"\",\"scheduleId\":\""+value.scheduleId+"\",\"bookingDate\":\""+dd+"\",\"courseTime\":\""+value.scheduleWeek + " " +value.scheduleNoon+"\",\"courseContent\":\""+value.scheduleContent+"\"}</td>"+
					"  <td class='text-center'><input "+disHtml+" type='checkbox' value='" + value.scheduleId + "' name='courseData' onclick='checkBoxClick(this);'/></td>" +
					"  <td class='text-center'>" + dd + "</td>"+
					"  <td class='text-left'>" + value.scheduleWeek + " " +value.scheduleNoon+"</td>" +
					"  <td class='text-left'>" + value.scheduleContent + "</td>" +
					"  <td class='text-left'>已预约"+coursePreson+"人，可预约"+value.scheduleMaxPerson+"人</td>" +
					"</tr>"
				);
			});
		}
	}
	if($("#course_booking_tbody").find("tr").length <= 0){
		$("#course_booking_tbody").append("<tr><td colspan='10' class='text-center'><h4>没有找到记录！</h4></td></tr>");
	}
}
/**
 * 防止多选框冒泡
 */
function checkBoxClick(obj){
	var sta = $(obj).attr("checked");
	$(obj).attr("checked", !sta);
	return false;
}
/**
 * 课程数据表行单击事件
 */
function checkThisBox(obj){
	var sta = $(obj).find(":checkbox").attr("checked");
	$(obj).find(":checkbox").attr("checked", !sta);
}
/**
 * 课程数据表行双击事件
 */
function addThisCourse(obj){
	$(obj).find(":checkbox").attr("checked", false);
	var courseJson = JSON.parse($(obj).children("td").html()); 
	courseBooking = [];
	courseBooking.push(courseJson);
	saveCourseBooking();
}
/**
 * 显示预约课程的Modal
 */
function showCourseBookoingModal(){
	var url = URL.get("Platform.QUERY_PREG_SCHEDULE_COURSE");
	ajax.post(url, null, dataType.json, function(data) {
		if(!common.isEmpty(data.value)){
			initPregCourseBooking(data.value);
		}
	}, false, false);
	$("[name='courseData']").attr("checked", false);
	$("#courseBookingModal").modal("show");
}
/**
 * 保存课程
 */
function saveCourseBooking(){
	var url = URL.get("Platform.PREG_COURSE_BOOKING_SAVE");
	var params = "courseList=" + JSON.stringify(courseBooking);
	ajax.post(url, params, dataType.json, function(data) {
		if(!common.isEmpty(data.data)){
			initCourseBooing(data.data);
		}
		$("#courseBookingModal").modal("hide");
		updateDiagnosisStatus();
		layer.alert(data.message);
	}, false, false);
}
/**
 * 加载已经选择的预约课程
 */
function initCourseBooing(data){
	$("#ready_course_tbody").empty();
	$.each(data, function(index, value){
	    /* if(dateCompare(value.bookingDate,diagnosisJson.diagnosisDate) == 1) {// 过滤：预约日期必须大于接诊日期
			addRowCourse(value);
		} */ 
		addRowCourse(value);
	});
	if($("#ready_course_tbody").find("tr").length <= 0){
		$("#ready_course_tbody").append(
			"<tr><td colspan='10' class='text-center'><h4>没有找到记录！</h4></td></tr>"
		);
	}
}
/**
 * 为已选择的预约课程添加一行
 */
function addRowCourse(data){
	var status = "未反馈";
	if(!common.isEmpty(data.feedId)){
		status = "已反馈";
	}
	$("#ready_course_tbody").append(
		"<tr id='"+data.id+"_tr'>"+
		"  <td class='text-center' id='"+data.id+"_time'>"+ data.bookingDate +"</td>"+
		"  <td class='text-center'>"+ data.courseTime +"</td>"+
		"  <td class='text-center'>"+ data.courseContent +"</td>"+
		"  <td class='text-center' id='"+data.id+"_status'>"+status+"</td>"+
		"  <td class='text-center'><a onclick='showCourseFeedback(\""+data.id+"\");'>打开</a>&nbsp&nbsp<a onclick='removeRowCourse(\""+data.id+"\");'>删除</a></td>"+
		"</tr>"
	);
}
/**
 * 移除预约课程
 */
function removeRowCourse(id){
	var url = URL.get("Platform.PREG_COURSE_BOOKING_DEL");
	var param = "id="+id;
	layer.confirm("确定删除此条课程预约记录？",function(index) {
		ajax.post(url, param, dataType.json, function(data) {
			$("#"+id+"_tr").remove();
		}, false, false);
		if($("#ready_course_tbody").find("tr").length <= 0){
			$("#ready_course_tbody").append(
				"<tr><td colspan='10' class='text-center'><h4>没有找到记录！</h4></td></tr>"
			);
		}
		layer.close(index);
	});
}

/**
 * 批量保存预约的课程
 */
function saveBatchCourse(){
	courseBooking = [];
	if($("[name='courseData'][checked]").length <= 0){
		layer.alert("请选择预约课程");
		return;
	}
	$("[name='courseData'][checked]").each(function(index, value){
		var obj = $(value).parent().parent();
		var courseJson = JSON.parse(obj.children("td").html()); 
		courseBooking.push(courseJson);
	});
	saveCourseBooking();
	$("#courseBookingModal").modal("hide");
}
/**
 * 课程反馈的modal显示
 */
function showCourseFeedback(id, feedId){
	common.clearForm("editFeedbackForm");
	$("#bookingId").val(id);
	var url = URL.get("Platform.PREG_BOOKING_FEEDBACK_QUERY");
	var param = "id="+id;
	ajax.post(url, param, dataType.json, function(data) {
		if(common.isEmpty(data.value)){
			//保存
			$("#editFeedbackForm").attr("action", URL.get("Platform.PREG_BOOKING_FEEDBACK_ADD"));
			$("[name='joinCourse'][value='是']").attr("checked", true);
			$("#courseDate").val($("#"+id+"_time").html());
			$("#courseDate").datetimepicker("update",$("#"+id+"_time").html());
		}else{
			//更新
			$("#editFeedbackForm").attr("action", URL.get("Platform.PREG_COURSE_BOOKING_UPDATE"));
			$("#editFeedbackForm").jsonToForm(data.value);
			$("#courseDate").val(data.value.courseDate);
			$("#courseDate").datetimepicker("update", data.value.courseDate);
		}
		$("#editFeedbackModal").modal("show");
	}, false, false);
}
/**
 * 只能输入3位整数或者4位带小数点，比如999或者99.9
 */
 function valid(val,id){
		if(parseFloat(val)>1000) {
			layer.msg("最多输入4位（带小数点）或3位整数！");
			$("#"+id).val("");
		}
		if(val.substring(0,1)==".") {
	        layer.msg("不能以小数点开头！");
			$("#"+id).val("");
		}
		if(val.substring(val.length-1)==".") {
	        layer.msg("不能以小数点结尾！");
			$("#"+id).val("");
		}	
}

//键盘录入操作，不能输入4位整数
 function validUp(val,id){
		if(parseFloat(val)>1000) {
			layer.msg("最多输入4位（带小数点）或3位整数！");
			$("#"+id).val("");
		}	
}
/********************* 工具方法 ****************************/
/**
 * 获取当日后 AddDayCount 天的日期
 */
function getDateByAfterDays(AddDayCount) {     
	var dd = new Date();
	dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期    
	var y = dd.getFullYear();     
	var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0    
	var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0    
	return y+"-"+m+"-"+d;     
}
</script>
<body>
<!-- 复诊预约一览 -->
<div class="panel panel-lightblue">
	<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 课程预约一览</div>
	<div class="table-responsive">
		<table class="table table-bordered table-hover">
			<thead>
				<tr class="active">
					<th class='text-center'>预约日期</th>
					<th class='text-center'>课程时间</th>
					<th class='text-center'>课程内容</th>
					<th class='text-center'>课程反馈状态</th>
					<th class='text-center'>操作</th>
				</tr>
			</thead>
			<tbody id="ready_course_tbody">
				<tr><td colspan="10" class='text-center'><h4>没有找到记录！</h4></td></tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="10" class='text-left'>
						<button class="btn btn-lightblue" type="button" onclick="showCourseBookoingModal();">
							<i class="fa fa-plus fa-fw"></i> 选择课程预约
						</button>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>

<!-- 可以预约的基础课程Modal -->
<div id="courseBookingModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 课程预约<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="table-responsive" style="overflow-y: auto; height: 428px;">
						<table class="table table-bordered table-hover" style="word-break:break-all; word-wrap:break-all;">
							<thead>
								<tr class="active">
									<th class='text-center' style="width: 10%">选择</th>
									<th class='text-center' style="width: 13%">日期</th>
									<th class='text-center' style="width: 15%">课程时间</th>
									<th class='text-center' style="width: 37%">课程内容</th>
									<th class='text-center' style="width: 25%">预约状态</th>
							   	</tr>
							</thead>
							<tbody id="course_booking_tbody" class="table-bordered">
							</tbody>
						</table>
					</div>
				</div>
				<div class="panel-body" style="padding: 0px;">
					<div class="col-xs-2 col-xs-offset-10 no-right">
						<button class="btn btn-primary btn-block" type="button" onclick="saveBatchCourse();"><i class="fa fa-save fa-fw"></i>预约</button>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<form id="editFeedbackForm" name="editFeedbackForm" action="" class="form-horizontal" method="post">
	<input id="bookingId" name="bookingId" type="hidden"/>
	<input id="feedId" name="feedId" type="hidden"/>
	<div id="editFeedbackModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 课程反馈<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
						<div class="row">
							<div class="col-xs-offset-1 col-xs-10 row-top">
								<div class="form-group">
					        		<label class="col-xs-4 control-label">是否参与课程</label>
					                <div class="col-xs-6 ">
						                <div class='col-xs-6 padding-zero'>
											<label class='checkbox-inline padding-zero'><input type='radio' name="joinCourse" value="是">是</label>
										</div>
						                <div class='col-xs-6'>
											<label class='checkbox-inline'><input type='radio' name="joinCourse" value="否">否</label>
										</div>
					                </div>
					            </div>
					            <div class="form-group">
					        		<label class="col-xs-4 control-label">杂粮饭是否全吃完</label>
					                <div class="col-xs-6 ">
						                <div class='col-xs-6 padding-zero'>
											<label class='checkbox-inline padding-zero'><input type='radio' name="grainFood" value="是">是</label>
										</div>
						                <div class='col-xs-6'>
											<label class='checkbox-inline'><input type='radio' name="grainFood" value="否">否</label>
										</div>
					                </div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-4 control-label">上课日期</label>
					            	<div class='col-xs-6'>
					            		<div class="input-group">
									      	<input id="courseDate" name="courseDate" type="text" class="form-control form_date" readonly/>
									      	<span class="input-group-btn">
									        	<button class="btn btn-primary" type="button" onclick="common.dateShow('courseDate')"><i class="fa fa-calendar fa-fw"></i>选择</button>
									      	</span>
						    			</div>
					            	</div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-4 control-label">餐前血糖</label>
					            	<div class='col-xs-6'>
						            	<div class="input-group">
						            		<input id="preprandialBooldGlucose" onkeyup="validUp(this.value,this.id);" onblur="valid(this.value,this.id);" name="preprandialBooldGlucose" class="form-control" type="text" maxlength="4"/>
						            		<div class="input-group-addon">mmol/L</div>
						            	</div>
					            	</div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-4 control-label">餐后2h血糖</label>
					            	<div class='col-xs-6'>
						            	<div class="input-group">
						            		<input id="postprandialBooldGlucose" onkeyup="validUp(this.value,this.id);" onblur="valid(this.value,this.id);" name="postprandialBooldGlucose" class="form-control" type="text" maxlength="4"/>
						            		<div class="input-group-addon">mmol/L</div>
						            	</div>
					            	</div>
					            </div>
					            <div class="form-group">
					            	<label class="col-xs-4 control-label">活动心率</label>
					            	<div class='col-xs-6'>
						            	<div class="input-group">
						            		<input id="heatRate" name="heatRate" class="form-control" type="text" maxlength="4"/>
						            		<div class="input-group-addon">次/分</div>
						            	</div>
					            	</div>
					            </div>
					            <div class="form-group">
					        		<label class="col-xs-4 control-label">运动强度反馈</label>
					                <div class="col-xs-6">
						                <div class='col-xs-12 padding-zero'>
											<label class='checkbox-inline padding-zero'><input type='radio' name="sprotFeedback" value="疲劳：超出日常活动强度">疲劳：超出日常活动强度</label>
										</div>
						                <div class='col-xs-12 padding-zero'>
											<label class='checkbox-inline padding-zero'><input type='radio' name="sprotFeedback" value="可耐受：基本等同于日常活动强度">可耐受：基本等同于日常活动强度</label>
										</div>
						                <div class='col-xs-12 padding-zero'>
											<label class='checkbox-inline padding-zero'><input type='radio' name="sprotFeedback" value="轻松：小于日常活动强度">轻松：小于日常活动强度</label>
										</div>
					                </div>
					            </div>
							</div>
						</div>
					</div>
					<div class="panel-body text-right" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right" >
							<button type="submit" class="btn btn-primary btn-block">保存</button>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

</body>
</html>