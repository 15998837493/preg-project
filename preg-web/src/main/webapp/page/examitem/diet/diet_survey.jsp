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
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/examitem/diet/diet_survey.js"></script>
<script id="add-food-template" type="x-handlebars-template">
<tr id='{{id}}_tr'>
	<td class='text-center'><span id='{{id}}_foodName'>{{foodName}}</span></td>
	<td class='text-center'><input class='form-control' id='{{id}}_foodAmount' type='text' maxlength='5' onfocus=getPerFoodEnergy('{{id}}') onblur=changeFoodAmount('{{id}}') value='{{foodAmount}}'/></td>
	<td class='text-center'><span id='{{id}}_foodEnergy'>{{foodEnergy}}</span></td>
	<td class='text-center'><button class='btn btn-primary btn-xs' type='button' onclick=removeFood('{{id}}')><i class='fa fa-minus fa-fw'></i>移除</button></td>" +
</tr>
</script>
</head>
<body style="padding-left: 30px;">
<div id="foodAmountModal" class="modal fade bs-example-modal">
	<div class="modal-dialog" style="width: 850px;">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title text-center">编辑食物重量</h3>
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
				<button type="button" class="btn btn-primary" onclick="appendFoodRow();">确定</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="planContent_div">
<div class="row" >
<form id="foodQuery" name="foodQuery"  action="${common.basepath }/${applicationScope.URL.DietFood.RECORD_FOOD_QUERY}"  class="form-horizontal">
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
	<input name="foodFeelTimeList[1].mealsId" value="C00000CA000000000003" type="hidden" />
	<input name="foodFeelTimeList[2].mealsId" value="C00000CA000000000005" type="hidden" />
	<div class="col-xs-12">
		<ul class="timeline">
			<li class="timeline-inverted">
				<div class="timeline-badge warning">
					<i class="fa fa-heart"></i>
				</div>
				<div class="timeline-panel">
					<div class="timeline-heading">
                        <h4 class="timeline-title">您的用餐习惯</h4>
                    </div>
					<div class="timeline-body row-top">
						<div class="panel panel-default">
						<div class="panel-body">
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>1、您在孕期的用餐是否规律</label>
								</div>
								<div class="col-xs-12" id="pregnancy_rule"></div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>2、您在孕期的平均用餐时间</label>
								</div>
								<div class="col-xs-12" id="pregnancy_speed"></div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>3、您在孕期在外用餐的频率为</label>
								</div>
								<div class="col-xs-12" id="pregnancy_frequency"></div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>4、您在孕期经常吃的有（可多选）</label>
								</div>
								<div class="col-xs-12" id="pregnancy_like"></div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>5、您在孕期的进餐感受为</label>
								</div>
								<div class="col-xs-12" id="pregnancy_feel"></div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>6、您在孕期饮食中能做到的有（可多选）</label>
								</div>
								<div class="col-xs-12" id="pregnancy_avoid"></div>
							</div>
							<div class="form-group">
								<div class="col-xs-12">
									<label class='contorl-label'>7、您在孕期饮食中达到推荐摄入量的有（可多选）</label>
								</div>
								<div class="col-xs-12" id="pregnancy_recommend"></div>
							</div>
						</div>
						</div>
					</div>
				</div>
			</li>
			<li class="timeline-inverted">
				<div class="timeline-badge primary">
					<i class="fa fa-cutlery"></i>
				</div>
				<div class="timeline-panel">
					<div class="timeline-heading">
                        <h4 class="timeline-title">您的用餐情况</h4>
                    </div>
					<div class="timeline-body row-top">
						<div class="row">
					        <div class="col-xs-12">
								<div class="form-group">
				                    <div class="col-xs-2 no-right"><input class="form-control" id="foodName" type="text" placeholder="请输入食物名称" /></div>
			                       	<div class="col-xs-2 no-right"><input class="form-control" id="fmName" type="text" placeholder="请输入食材名称" /></div> 
			                        <div class="col-xs-2 no-right"><select class="form-control" id="cook" ></select></div>
			                        <div class="col-xs-2">
			                        	<button type="button" onclick="queryFoodByCondition();" class="btn btn-primary"><i class="fa fa-search fa-fw"></i>查询</button>
			                       	</div>
				                </div>
							</div>
						</div>
						<div class="col-xs-6 no-left" style="padding-left: 0px;padding-right: 0px;">
							<div class="panel panel-default">
								<div class="table-responsive">
									<table class="table table-bordered" id="foodTable">
										<thead>
											<tr class="active">
												<th class="text-center">选择</th>
												<th class="text-center">食物图片</th>
												<th class="text-center">食物名称</th>
												<th class='text-center'>能量(千卡)</th>
												<th class='text-center'>操作项</th>
											</tr>
										</thead>
										<tbody id="t_body"></tbody>
										<tr><td colspan="100"><div id="pager"></div></td></tr>
									</table>
								</div>
							</div>
						</div>
						<div class="col-xs-6 no-left no-right" style="padding-right: 0px;">
							<div id="mealsAccordion" class="panel-group">
								<div class="panel">
					                <a id="C00000CA000000000001_a" href="#collapse_C00000CA000000000001" class="list-group-item disabled collapsed" data-parent="#mealsAccordion" data-toggle='collapse' style="cursor:pointer;">
					                   	早餐：
					                </a>
					                <div id="collapse_C00000CA000000000001" class="collapse in">
					                    <div class="panel panel-default table-responsive" style="height: 208px;max-height: 208px;">
											<table class="table">
												<tbody id="C00000CA000000000001_body"><tr id="C00000CA000000000001_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
											</table>
										</div>
					                </div>
					        	</div>
					        	<div class="panel">
					                <a id="C00000CA000000000002_a" href="#collapse_C00000CA000000000002" class="list-group-item" data-parent="#mealsAccordion" data-toggle='collapse' style="cursor:pointer;">
					                   	上午加餐：
					                </a>
					                <div id="collapse_C00000CA000000000002" class="collapse">
					                    <div class="panel panel-default table-responsive" style="height: 208px;max-height: 208px;">
											<table class="table">
												<tbody id="C00000CA000000000002_body"><tr id="C00000CA000000000002_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
											</table>
										</div>
					                </div>
					        	</div>
					        	<div class="panel">
					                <a id="C00000CA000000000003_a" href="#collapse_C00000CA000000000003" class="list-group-item" data-parent="#mealsAccordion" data-toggle='collapse' style="cursor:pointer;">
					                   	午餐：
					                </a>
					                <div id="collapse_C00000CA000000000003" class="collapse">
					                    <div class="panel panel-default table-responsive" style="height: 208px;max-height: 208px;">
											<table class="table">
												<tbody id="C00000CA000000000003_body"><tr id="C00000CA000000000003_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
											</table>
										</div>
					                </div>
					        	</div>
					        	<div class="panel">
					                <a id="C00000CA000000000004_a" href="#collapse_C00000CA000000000004" class="list-group-item" data-parent="#mealsAccordion" data-toggle='collapse' style="cursor:pointer;">
					                   	下午加餐：
					                </a>
					                <div id="collapse_C00000CA000000000004" class="collapse">
					                    <div class="panel panel-default table-responsive" style="height: 208px;max-height: 208px;">
											<table class="table">
												<tbody id="C00000CA000000000004_body"><tr id="C00000CA000000000004_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
											</table>
										</div>
					                </div>
					        	</div>
					        	<div class="panel">
					                <a id="C00000CA000000000005_a" href="#collapse_C00000CA000000000005" class="list-group-item" data-parent="#mealsAccordion" data-toggle='collapse' style="cursor:pointer;">
					                   	晚餐：
					                </a>
					                <div id="collapse_C00000CA000000000005" class="collapse">
					                    <div class="panel panel-default table-responsive" style="height: 208px;max-height: 208px;">
											<table class="table">
												<tbody id="C00000CA000000000005_body"><tr id="C00000CA000000000005_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
											</table>
										</div>
					                </div>
					        	</div>
					        	<div class="panel">
					                <a id="C00000CA000000000006_a" href="#collapse_C00000CA000000000006" class="list-group-item" data-parent="#mealsAccordion" data-toggle='collapse' style="cursor:pointer;">
					                   	夜宵：
					                </a>
					                <div id="collapse_C00000CA000000000006" class="collapse">
					                    <div class="panel panel-default table-responsive" style="height: 208px;max-height: 208px;">
											<table class="table">
												<tbody id="C00000CA000000000006_body"><tr id="C00000CA000000000006_tr_init"><td>请从左边选择食物，然后点击 “添加”。</td></tr></tbody>
											</table>
										</div>
					                </div>
					        	</div>
							</div>
						</div>
					</div>
				</div>
			</li>
			<li class="timeline-inverted">
				<div class="timeline-badge info">
					<i class="fa fa-bar-chart"></i>
				</div>
				<div class="timeline-panel">
					<div class="timeline-heading">
                        <h4 class="timeline-title">您的饮食摄入频率</h4>
                    </div>
					<div class="timeline-body row-top">
<!-- 						<div class="col-xs-12 no-left no-right"> -->
						<div class="panel panel-default">
						<div class="panel-body">
							<img alt="" class="col-xs-2 no-left no-right" src="${common.basepath }/common/images/diet_ta.png" height="282px;">
							
							<div class="col-xs-10">
								<table class="table table-bordered">
									<tr style="height: 40px;">
										<td style="width: 60px;">油脂</td>
										<td>——</td>
										<td>——</td>
									</tr>
									<tr style="height: 55px;">
										<td>蛋奶豆</td>
										<td>1份大豆类约20克，1份坚果约为10克，1份乳制品约为200克</td>
										<td>黄豆20克=北豆腐60克=南豆腐110克=内酯豆腐120克=豆干45克，豆浆360-380毫升，葵花籽仁10克=板栗25克=莲子20克，液态奶200克=奶酪20-25克=奶粉20-30克</td>
									</tr>
									<tr style="height: 51px;">
										<td>鱼肉蛋</td>
										<td>1份鱼肉蛋类约为50克</td>
										<td>——</td>
									</tr>
									<tr style="height: 65px;">
										<td>果蔬</td>
										<td>1份蔬菜约为100克，1份水果约为100克</td>
										<td>新鲜叶菜/瓜茄类蔬菜约为100克，鲜豆类蔬菜如芸豆约为50克，苹果100克=枣25克=柿子65克</td>
									</tr>
									<tr style="height: 70px;">
										<td>谷薯</td>
										<td>1份谷类为50克，1份薯类约为100克</td>
										<td>50克面粉=70-80克馒头，50克大米=100-120克米饭，红薯80克=马铃薯100克</td>
									</tr>
								</table>
							</div>
						</div>
						</div>
<!-- 						</div> -->
<!-- 						<div class="col-xs-12 no-left no-right"> -->
							<div class="table-responsive">
								<table class="table table-bordered">
									<thead>
										<tr class="active">
											<th class="text-center" style="width: 15%;">项目</th>
											<th class="text-center">周期</th>
											<th class="text-center">平均摄入次数</th>
											<th class='text-center'>平均每次摄入量</th>
											<th class='text-center'>单位</th>
										</tr>
									</thead>
									<tbody id="food_frequency_body"></tbody>
								</table>
							</div>
<!-- 						</div> -->
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
<div class="col-xs-2 col-xs-offset-5" style="margin-bottom: 30px;">
	<button id="recordMealsSubmit" class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i> 生成报告</button>
</div>
</div>
</div>
</body>