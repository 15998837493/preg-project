<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<title>孕期膳食评价--${custName}</title>
<%@ include file="/common/base.jsp" %>
<%@ include file="/common/common.jsp" %>
<script type="text/javascript">
var URL = opener.URL;
var CODE = opener.CODE;
var PublicConstant = opener.PublicConstant;
</script>
<title>孕期营养门诊诊疗系统</title>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/common/plugins/Handlebars/handlebars.js"></script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/examitem/dietaryrecall/dietary_review.js"></script>
<script id="add-food-template" type="x-handlebars-template">
<tr id='{{id}}_tr'>
	<td class='text-center'><span id='{{id}}_foodName'>{{foodName}}</span></td>
	<td class='text-center'><input class='form-control' id='{{id}}_foodAmount' type='text' maxlength='5' onfocus=getPerFoodEnergy('{{id}}') onblur=changeFoodAmount('{{id}}') value='{{foodAmount}}'/></td>
	<td class='text-center'><span id='{{id}}_foodEnergy'>{{foodEnergy}}</span></td>
	<td class='text-center'><button class='btn btn-primary btn-xs' type='button' onclick=removeFood('{{id}}')><i class='fa fa-minus fa-fw'></i>移除</button></td>" +
</tr>
</script>
<style>
.select_style{width: 25%;font-size:13px;}
.but-style{text-align:left;word-wrap: break-word !important;word-break: break-all !important;white-space: normal !important;}
.style-left{width: 50%;height:554.5px;float:  left;}
.style-right{width: 50%;height:554.5px;float: left;}
</style>
</head>
<body>
<!-- 食物列表 -->
<div class="container-fluid">
	<div class="row" >
		<ul id="timeline">
			<li class="timeline-inverted">
				<div class="timeline-panel" id="timeline-panel">
					<div class="timeline-heading">
	                   	<h4 class="timeline-title">24小时膳食回顾</h4>
	                </div>
					<div class="timeline-body">
						<form id="foodQuery" name="foodQuery"  action="${common.basepath }/${applicationScope.URL.DietaryReview.QUERY_FOOD}"  class="form-horizontal">
							<input type="hidden" name="foodMealType" value="10"/>
							<input type="hidden" name="foodName" />
							<input type="hidden" name="fmName" />
							<input type="hidden" name="foodCuisine" />
						</form>
						<form id="recordMealsForm" name="recordMealsForm" method="post" class="form-horizontal">
							<input name="foodRecordId" value="${foodRecordId}" type="hidden" />
							<input type="hidden" id="inspectName" name="inspectName" />
							<input id="diagnosisId" name="diagnosisId" type="hidden" value="${diagnosisId }"/>
							<input id="inspectId" name="inspectId" type="hidden" value="${inspectId }"/>
							<input id="mealsType" type="hidden" value="C00000CA000000000001"/>
							<input name="foodFeelTimeList[0].mealsId" value="C00000CA000000000001" type="hidden" />
							<input name="foodFeelTimeList[1].mealsId" value="C00000CA000000000002" type="hidden" />
							<input name="foodFeelTimeList[2].mealsId" value="C00000CA000000000003" type="hidden" />
							<input name="foodFeelTimeList[3].mealsId" value="C00000CA000000000004" type="hidden" />
							<input name="foodFeelTimeList[4].mealsId" value="C00000CA000000000005" type="hidden" />
							<input name="foodFeelTimeList[5].mealsId" value="C00000CA000000000006" type="hidden" />
							<div class="row">
						        <div class="col-xs-12">
									<div class="form-group">
					                    <div class="col-xs-1 no-right"><input class="form-control" id="foodName" type="text" placeholder="食物名称" /></div>
				                       	<div class="col-xs-1 no-right"><input class="form-control" id="fmName" type="text" placeholder="食材名称" /></div> 
				                        <div class="col-xs-2 no-right"><select class="form-control" id="cook" ></select></div>
				                        <div class="col-xs-1">
				                        	<button type="button" onclick="queryFoodByCondition();" class="btn btn-primary"><i class="fa fa-search fa-fw"></i>查询</button>
				                       	</div>
					                </div>
								</div>
							</div>
							<div class="col-xs-6 no-left">
								<div class="panel">
									<table class="table table-bordered" id="foodTable">
										<thead>
											<tr class="active">
												<th class="text-center" style="width: 5%;">选择</th>
												<th class="text-center" style="width: 10%;">食物图片</th>
												<th class="text-center" style="width: 10%;">食物名称</th>
												<th class='text-center' style="width: 10%;">能量(千卡)</th>
												<th class='text-center' style="width: 10%;">操作项</th>
											</tr>
										</thead>
										<tbody id="t_body"></tbody>
									</table>
								</div>
							</div>
							<div class="no-left col-xs-6">
								<div id="mealsAccordion" class="panel-group">
									<div class="panel">
										<div class="btn-group col-xs-12">
											<button id="C00000CA000000000001_a" type="button" class="col-xs-9 btn btn-default but-style" data-parent="#mealsAccordion" data-toggle="collapse" 
												data-target="#collapse_C00000CA000000000001">
												早餐：
											</button>
											<select class="form-control select_style" name="foodFeelTimeList[0].mealsTime">
											  <option value="">请选择用餐时间</option>
											  <option value="06:00之前">06:00之前</option>
											  <option value="06:00~07:00">06:00~07:00</option>
											  <option value="07:00~08:00">07:00~08:00</option>
											  <option value="08:00~09:00">08:00~09:00</option>
											  <option value="09:00之后">09:00之后</option>
											</select>	
										</div>
						                <div class="panel-body">
							                <div id="collapse_C00000CA000000000001" class="collapse in">
							                    <div class="panel panel-default table-responsive" style="height: 240px;max-height: 250px;">
													<table class="table">
														<tbody id="C00000CA000000000001_body"><tr id="C00000CA000000000001_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
													</table>
												</div>
							                </div>
						                </div>
						        	</div>
						        	<div class="panel">
						                <div class="btn-group col-xs-12">
						                	<button id="C00000CA000000000002_a" type="button" class="col-xs-9 btn btn-default but-style" data-parent="#mealsAccordion" data-toggle='collapse'
													data-target="#collapse_C00000CA000000000002">
												<span>上午加餐：</span>
							                </button>
							                <select class="form-control select_style" name="foodFeelTimeList[1].mealsTime">
							        		  <option value="">请选择用餐时间</option>
											  <option value="10:00~10:30">10:00~10:30</option>
											  <option value="10:30~11:00">10:30~11:00</option>
											  <option value="11:00~11:30">11:00~11:30</option>
											  <option value="11:30~12:00">11:30~12:00</option>
											</select>
										</div>
						                <div class="panel-body">
							                <div id="collapse_C00000CA000000000002" class="collapse">
							                    <div class="panel panel-default table-responsive" style="height: 240px;max-height: 250px;">
													<table class="table">
														<tbody id="C00000CA000000000002_body"><tr id="C00000CA000000000002_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
													</table>
												</div>
							                </div>
						                </div>
						        	</div>
						        	<div class="panel">
						                <div class="btn-group col-xs-12">
						                	<button id="C00000CA000000000003_a" type="button" class="col-xs-9 btn btn-default but-style" data-parent="#mealsAccordion" data-toggle='collapse'
													data-target="#collapse_C00000CA000000000003">
												<span>午餐：</span>
							                </button>
							                <select class='form-control select_style' name="foodFeelTimeList[2].mealsTime">
											  <option value="">请选择用餐时间</option>
											  <option value="11:00~12:00">11:00~12:00</option>
											  <option value="12:00~13:00">12:00~13:00</option>
											  <option value="13:00~14:00">13:00~14:00</option>
											</select>
										</div>
						                <div class="panel-body">
							                <div id="collapse_C00000CA000000000003" class="collapse">
							                    <div class="panel panel-default table-responsive" style="height: 240px;max-height: 250px;">
													<table class="table">
														<tbody id="C00000CA000000000003_body"><tr id="C00000CA000000000003_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
													</table>
												</div>
							                </div>
						                </div>
						        	</div>
						        	<div class="panel">
						                <div class="btn-group col-xs-12">
						                	<button id="C00000CA000000000004_a" type="button" class="col-xs-9 btn btn-default but-style" data-parent="#mealsAccordion" data-toggle='collapse'
													data-target="#collapse_C00000CA000000000004">
												<span>下午加餐：</span>
							                </button>
							                <select class='form-control select_style' name="foodFeelTimeList[3].mealsTime">
											  <option value="">请选择用餐时间</option>
											  <option value="14:00~14:30">14:00~14:30</option>
											  <option value="14:30~15:00">14:30~15:00</option>
											  <option value="15:00~15:30">15:00~15:30</option>
											  <option value="15:30~16:00">15:30~16:00</option>
											</select>
										</div>
						                <div class="panel-body">
							                <div id="collapse_C00000CA000000000004" class="collapse">
							                    <div class="panel panel-default table-responsive" style="height: 240px;max-height: 250px;">
													<table class="table">
														<tbody id="C00000CA000000000004_body"><tr id="C00000CA000000000004_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
													</table>
												</div>
							                </div>
						                </div>
						        	</div>
						        	<div class="panel">
						                <div class="btn-group col-xs-12">
						                	<button id="C00000CA000000000005_a" type="button" class="col-xs-9 btn btn-default but-style" data-parent="#mealsAccordion" data-toggle='collapse'
													data-target="#collapse_C00000CA000000000005">
												<span>晚餐：</span>
							                </button>
							                <select class='form-control select_style' name="foodFeelTimeList[4].mealsTime">
											  <option value="">请选择用餐时间</option>
											  <option value="16:00~17:00">16:00~17:00</option>
											  <option value="17:00~18:00">17:00~18:00</option>
											  <option value="18:00~19:00">18:00~19:00</option>
											  <option value="19:00之后">19:00之后</option>
											</select>
										</div>
						                <div class="panel-body">
							                <div id="collapse_C00000CA000000000005" class="collapse">
							                    <div class="panel panel-default table-responsive" style="height: 240px;max-height: 250px;">
													<table class="table">
														<tbody id="C00000CA000000000005_body"><tr id="C00000CA000000000005_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
													</table>
												</div>
							                </div>
						                </div>
						        	</div>
						        	<div class="panel">
						                <div class="btn-group col-xs-12">
						                	<button id="C00000CA000000000006_a" type="button" class="col-xs-9 btn btn-default but-style" data-parent="#mealsAccordion" data-toggle='collapse'
													data-target="#collapse_C00000CA000000000006">
												<span>夜宵：</span>
							                </button>
							                <select class='form-control select_style' name="foodFeelTimeList[5].mealsTime">
											  <option value="">请选择用餐时间</option>
											  <option value="19:00~20:00">19:00~20:00</option>
											  <option value="20:00~21:00">20:00~21:00</option>
											  <option value="21:00~22:00">21:00~22:00</option>
											  <option value="22:00之后">22:00之后</option>
											</select>
										</div>
						                <div class="panel-body">
							                <div id="collapse_C00000CA000000000006" class="collapse">
							                    <div class="panel panel-default table-responsive" style="height: 240px;max-height: 250px;">
													<table class="table">
														<tbody id="C00000CA000000000006_body"><tr id="C00000CA000000000006_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
													</table>
												</div>
							                </div>
						                </div>
						        	</div>
								</div>
							</div>
						</form>
					</div>
					<div class="col-xs-2 col-xs-offset-5" style="margin-top: 5px;">
						<button id="recordMealsSubmit" class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i> 生成报告</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<!-- 选择食物重量modal -->
<div id="foodAmountModal" class="modal fade bs-example-modal">
	<div class="modal-dialog" style="width: 850px;">
		<div class="modal-content">
			<div class="modal-header">
				<i class="fa fa-edit fa-fw"></i>编辑食物重量
				<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
			</div>
			<div class="modal-body">
				<div class="table-responsive form-horizontal">
					<table class="table form-horizontal">
						<tr>
							<td class="text-center" style="border-top: 0px;">
								<div class="col-xs-4 no-left">
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="0"/></div>
									<div class="col-xs-10"><input class="form-control" id="0" name="inputRadio" type="text" input-valid="number" placeholder="直接输入食物重量" readonly/></div>
								</div>
							</td>
						</tr>
						<tr>
							<td class="text-center" style="border-top: 0px;">
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/s-xiao.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="1"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="1" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1小口（约20g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">小口</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
							      	<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/s-da.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="2"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="2" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1大口（约30g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">大口</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/p-da.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="3"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="3" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1份（约50g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">份</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/w-xiao.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="4"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="4" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1小碗（约100g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">小碗</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/w-zhong.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="5"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="5" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1中碗（约150g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">中碗</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/w-da.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="6"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="6" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1大碗（约300g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">大碗</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/p-xiao.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="7"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="7" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1小盘（约50g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">小盘</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/p-zhong.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="8"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="8" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1中盘（约100g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">中盘</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
								<div class="col-xs-4 no-left">
									<div class="col-xs-12 no-left no-right"><img src="${common.basepath }/resource/template/image/cover/diet/p-da.jpg" style="width:130px;height:130px;"></div>
									<div class="col-xs-1 no-left no-right"><input type="radio" name="editFoodAmountRadio" value="9"/></div>
									<div class="col-xs-10">
										<div class="input-group">
											<input id="9" name="inputRadio" type="text" input-valid="number" class="form-control input-sm" placeholder="1大盘（约150g）" readonly/>
											<span class="input-group-btn">
									        	<button class="btn btn-default btn-sm" type="button" title="计量单位">大盘</button>
									      	</span>
								      	</div>
							      	</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary col-xs-offset-10 col-xs-2" onclick="appendFoodRow();">确定</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>