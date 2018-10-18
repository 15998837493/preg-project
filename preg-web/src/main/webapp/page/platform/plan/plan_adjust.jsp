<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/common/common.jsp" %>
<script type="text/javascript" src="${common.basepath}/common/plugins/laytpl/laytpl.js"></script>
<style>
    .btn-group > .btn {
        border-radius: 4px;
        margin-right: 3px;
        margin-top: 0px;
        margin-bottom: 3px;
    }

    .my-input-group-addon {
        padding: 3px 6px;
        font-size: 14px;
        font-weight: normal;
        line-height: 1;
        color: #555;
        text-align: center;
        margin-right: 10px;
        border: 1px solid #ccc;
        border-left-width: 0px;
        border-left-style: solid;
        border-left-color: rgb(204, 204, 204);
        border-radius: 4px;
        border-top-left-radius: 0px;
        border-bottom-left-radius: 0px;
    }

    .intake-sm {
        width: 76px;
    }
</style>

<!-- Ztree -->
<link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="${common.basepath}/common/mnt/css/ztree.css"/>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>

<script type="text/javascript">
    // 基础数据
    var historyPlanList = ${historyPlanList};// 历次方案列表
    var dietList = ${dietList};// 所有食谱模版
    var productList = ${productList};// 产品列表
    var intakeTypeList = ${intakeTypeList};// 膳食类型列表
    var diseaseList = ${diseaseList};// 诊断项目列表
    // 历史数据
    var diagnosisJson = ${diagnosisJson};// 接诊信息
    var planVo = ${planVo};// 膳食方案信息
    var intakeDetailList = ${intakeDetailList};// 膳食方案明细
    var personIntakeList = ${personIntakeList};// 个人膳食模板列表
    var suggestIntakeEnergy = "${suggestIntakeEnergy}";// 推荐热量

    var intakeTypeMap = new Map();// 摄入类型Map
    var productMap = new Map();// 产品Map

    if (!common.isEmpty(intakeTypeList)) {
        $.each(intakeTypeList, function (index, intakeType) {
            intakeTypeMap.set(intakeType.code, intakeType);
        });
    }
    if (!common.isEmpty(productList)) {
        $.each(productList, function (index, value) {
            var productName = value.productName || (value.productGoodsName + " " + value.productCommonName);
            if (!_.isEmpty($.trim(productName))) {
                value.productName = productName;
            }
            productMap.set(value.productId, value);
        });
    }

    var zNodes = ${treeNodes};// 树结点数据集合
    var selectNode;// 当前选中结点
    var treeObj;// 树对象
</script>
<!-- 早、中、晚餐 模版 -->
<script id="threeMealHtml" type="text/html">
    <tr>
        <td class="text-center active" rowspan="2" style="width: 13%">{{layData.mealtypeName}}</td>
        <td class="text-right" style="border-bottom: 0px solid #fff;border-right: 0px solid #fff;width: 90px;">普通食物：</td>
        <td style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;" name="intake-td">
            <div class="col-xs-12 no-left no-right">
                {{# $.each(threeCodes, function(index, code){ }}
                <div class="div-inline">{{intakeTypeMap.get(code).name}}
                    <input type="text" name="intakeList" id="{{layData.mealtype}}_{{code}}" info="intake_{{intakeTypeMap.get(code).name}}" class="intake-input" input-required/><span
                            class='my-input-group-addon'>份</span>
                </div>
                {{# }); }}
                <div class="div-inline" style="padding-top: 2px;"><a onclick="showMoreProduct('{{layData.mealtype}}');"><i id="{{layData.mealtype}}_meal_i" class="fa fa-angle-double-down"
                                                                                                                           aria-hidden="true">更多</i></a></div>
            </div>
            <div class="col-xs-12 no-left no-right" id="{{layData.mealtype}}_more_div" style="padding-top: 5px; display: none;">
                {{# $.each(plusCodes, function(index, code){ }}
                <div class="div-inline">{{intakeTypeMap.get(code).name}}
                    <input type="text" name="intakeList" id="{{layData.mealtype}}_{{code}}" info="intake_{{intakeTypeMap.get(code).name}}" class="intake-input" input-required/><span
                            class='my-input-group-addon'>份</span>
                </div>
                {{# }); }}
            </div>
        </td>
    </tr>
    <tr>
        <td class="text-right" style="border-top: 0px solid #fff;border-right: 0px solid #fff;width: 90px;">营养食品：</td>
        <td style="border-top: 0px solid #fff;border-left: 0px solid #fff;" id="{{layData.mealtype}}_product" name="product-td">
            <div class="div-inline" style="padding-right: 5px;"><input id="{{layData.mealtype}}_search" type="text" class="intake-sm" placeholder="请输入产品名称"></div>
        </td>
    </tr>
</script>
<!-- 加餐 模版 -->
<script id="plusMealHtml" type="text/html">
    <tr>
        <td class="text-center active" rowspan="2">{{layData.mealtypeName}}</td>
        <td class="text-right" style="border-bottom: 0px solid #fff;border-right: 0px solid #fff;width: 90px;">普通食物：</td>
        <td style="border-bottom: 0px solid #fff;border-left: 0px solid #fff;" name="intake-td">
            <div class="col-xs-12 no-left no-right">
                {{# $.each(plusCodes, function(index, code){ }}
                <div class="div-inline">{{intakeTypeMap.get(code).name}}
                    <input type="text" name="intakeList" id="{{layData.mealtype}}_{{code}}" info="intake_{{intakeTypeMap.get(code).name}}" class="intake-input" input-required/><span
                            class='my-input-group-addon'>份</span>
                </div>
                {{# }); }}
                <div class="div-inline" style="padding-top: 2px;"><a onclick="showMoreProduct('{{layData.mealtype}}');"><i id="{{layData.mealtype}}_meal_i" class="fa fa-angle-double-down"
                                                                                                                           aria-hidden="true">更多</i></a></div>
            </div>
            <div class="col-xs-12 no-left no-right" id="{{layData.mealtype}}_more_div" style="padding-top: 5px; display: none;">
                {{# $.each(threeCodes, function(index, code){ }}
                <div class="div-inline">{{intakeTypeMap.get(code).name}}
                    <input type="text" name="intakeList" id="{{layData.mealtype}}_{{code}}" info="intake_{{intakeTypeMap.get(code).name}}" class="intake-input" input-required/><span
                            class='my-input-group-addon'>份</span>
                </div>
                {{# }); }}
            </div>
        </td>
    </tr>
    <tr>
        <td class="text-right" style="border-top: 0px solid #fff;border-right: 0px solid #fff;width: 90px;">营养食品：</td>
        <td style="border-top: 0px solid #fff;border-left: 0px solid #fff;" id="{{layData.mealtype}}_product" name="product-td">
            <div class="div-inline" style="padding-right: 5px;"><input id="{{layData.mealtype}}_search" type="text" class="intake-sm" placeholder="请输入产品名称"></div>
        </td>
    </tr>
</script>
<!-- 普通食物 模版 -->
<script id="normalFoodHtml" type="text/html">
    {{# $.each(intakeTypeList, function(index, intakeType){ }}
    {{# if(index%3 == 0){ }}
    <tr>
        {{# } }}
        <td class="text-center active">{{intakeType.name}}</td>
        <td class="text-center"><span id="{{intakeType.code}}_modal" name="modal_span"></span></td>
        {{# if((index+1)%3 == 0){ }}
    </tr>
    {{# } }}
    {{#    }); }}
</script>
<script type="text/javascript" charset="UTF-8" src="${common.basepath}/page/platform/plan/plan_adjust.js"></script>
<title>选择摄入量模版</title>
</head>
<body>
<input type="hidden" id="createUserId" value="${createUserId }"/>
<input type="hidden" id="productMealType"/>
<input type="hidden" id="diagnosisCustWeight" value="${diagnosisVo.diagnosisCustWeight }">
<%--<div id="dietDetailModal" class="modal fade bs-example-modal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body">
				<div class="panel panel-lightblue">
					<div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 食谱明细<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
					<div class="table-responsive">
						<table class="table table-bordered">
							<thead>
								<tr class="active">
									<th class='text-center'>餐次</th>
									<th class='text-center'>菜名</th>
									<th class='text-center'>食物成分名称</th>
									<th class='text-center'>食物成分重量</th>
							   	</tr>
							</thead>
							<tbody id="intake_diet_detail_inbody"><tr><td colspan="10" class="text-center"><h4>没有找到记录！</h4></td></tr></tbody>
						</table>
					</div>
				</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->--%>

<div id="dietModal" class="modal fade bs-example-modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 食谱列表<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                    <div class="panel-body">
                        <div class="row" id="dietCondition">
                            <div class="col-xs-4">
                                <input type='text' class='form-control' placeholder='请输入检索内容'/>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="dietTable">
                            <thead>
                            <tr class="active">
                                <th class="text-center">序号</th>
                                <th class="text-center">食谱模版名</th>
                                <th class="text-center">孕期阶段</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="historyPlanModal" class="modal fade bs-example-modal">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 历次方案<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                    <div class="panel-body">
                        <div class="row" id="historyPlanCondition">
                            <div class="col-xs-3">
                                <input type='text' class='form-control' placeholder='请输入检索内容'/>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="historyPlanTable">
                            <thead>
                            <tr class="active">
                                <th class="text-center" rowspan="2" style="line-height: 52px;">日期</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">能量</th>
                                <th class="text-center" colspan="2">碳水化合物</th>
                                <th class="text-center" colspan="2">蛋白质</th>
                                <th class="text-center" colspan="2">脂肪</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">诊断</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">操作</th>
                            </tr>
                            <tr class="active">
                                <th class="text-center">克(g)</th>
                                <th class="text-center">占比(%)</th>
                                <th class="text-center">克(g)</th>
                                <th class="text-center">占比(%)</th>
                                <th class="text-center">克(g)</th>
                                <th class="text-center">占比(%)</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="personTemplateModal" class="modal fade bs-example-modal">
    <div class="modal-dialog" style="width: 1200px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 个人模板<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                    <div class="panel-body">
                        <div class="row" id="personTemplateCondition">
                            <div class="col-xs-3">
                                <input type='text' class='form-control' placeholder='请输入检索内容'/>
                            </div>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover" id="personTemplateTable">
                            <thead>
                            <tr class="active">
                                <th class="text-center" rowspan="2" style="line-height: 52px;">模板名称</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">能量</th>
                                <th class="text-center" colspan="2">碳水化合物</th>
                                <th class="text-center" colspan="2">蛋白质</th>
                                <th class="text-center" colspan="2">脂肪</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">孕期阶段</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">诊断</th>
                                <th class="text-center" rowspan="2" style="line-height: 52px;">操作</th>
                            </tr>
                            <tr class="active">
                                <th class="text-center">克(g)</th>
                                <th class="text-center">占比(%)</th>
                                <th class="text-center">克(g)</th>
                                <th class="text-center">占比(%)</th>
                                <th class="text-center">克(g)</th>
                                <th class="text-center">占比(%)</th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="productQitModal" class="modal fade bs-example-modal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 摄入类型提示信息<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr class="active">
                                <th class="text-center">编号</th>
                                <th class="text-center">名称</th>
                                <th class="text-center">单位</th>
                                <th class="text-center">每单位量（g）</th>
                                <th class="text-center">每份热量</th>
                                <th class="text-center">碳水化合物含量</th>
                                <th class="text-center">蛋白质含量</th>
                                <th class="text-center">脂肪含量</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${intakeTypeQitList }" var="list">
                                <tr>
                                    <td class="text-center">${list.code }</td>
                                    <td>${list.name }</td>
                                    <td class="text-center">${list.unit }</td>
                                    <td>${list.unitAmount }</td>
                                    <td>${list.unitEnergy }</td>
                                    <td>${list.unitCbr }</td>
                                    <td>${list.unitProtein }</td>
                                    <td>${list.unitFat }</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<form id="intakeTemplateForm" action="${common.basepath }/${applicationScope.URL.Platform.ADD_PERSON_INTAKE}" method="post" class="form-horizontal">
    <input type="hidden" name="intakeId"/>
    <input type="hidden" name="intakeMode" value="2"/>
    <input type="hidden" name="dietId"/>
    <input type="hidden" name="dietName"/>
    <input type="hidden" name="intakeCaloric"/>
    <input type="hidden" name="intakeCbr"/>
    <input type="hidden" name="intakeCbrPercent"/>
    <input type="hidden" name="intakeProtein"/>
    <input type="hidden" name="intakeProteinPercent"/>
    <input type="hidden" name="intakeFat"/>
    <input type="hidden" name="intakeFatPercent"/>
    <input type="hidden" name="intakeActualEnergy"/>
    <input type="hidden" id="intakeMark" name="intakeMark"/>
    <input type="hidden" id="detailsJson" name="detailsJson"/>
    <input type="hidden" id="dataType" name="dataType" value="2"/>
    <div id="addPersonTemplateModal" class="modal fade bs-example-modal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="panel panel-lightblue">
                        <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 编辑个人膳食模板<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-xs-3 control-label">模版名称</label>
                                <div class="col-xs-6">
                                    <input name="intakeName" class="form-control" type="text" maxlength="32"/>
                                    <input type="hidden" id="intakeNameOld"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">孕期阶段</label>
                                <div class="col-xs-6">
                                    <select id="pregnantStage" name="pregnantStage" class="form-control">
                                        <option value="">请选择孕期阶段</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">诊断项目</label>
                                <div class="col-xs-6">
                                    <input id="diseaseSearch" class="form-control" type="text" maxlength="64"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-6 col-xs-offset-3" id="personTemplateDisease_div"></div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body" style="padding: 0px;">
                        <div class="col-xs-2 col-xs-offset-10 no-right">
                            <button class="btn btn-primary btn-block" type="button" id="submitPersonTemplateButton" name="planAdjustButton">确认</button>
                        </div>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form>

<div class="panel panel-lightblue">
    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 膳食处方</div>
    <div class="panel-body form-inline" id="planIntakeCondition">
        <form id="planIntakeQueryForm" action="${common.basepath}/${applicationScope.URL.Master.PLAN_INTAKE_QUERY}" method="post" class="form-horizontal">
            <select id="diseaseCode" name="diseaseCode" class="form-control">
                <option value="">==请选择诊断疾病==</option>
                <c:forEach items="${diseaseMap }" var="map">
                    <option value="${map.key }">${map.value }</option>
                </c:forEach>
            </select>
            <select id="energy" name="energy" class="form-control">
                <option value="">==请选择能量==</option>
                <option value="1600~1800">1000~1200</option>
                <option value="1600~1800">1200~1400</option>
                <option value="1600~1800">1400~1600</option>
                <option value="1600~1800">1600~1800</option>
                <option value="1800~2000">1800~2000</option>
                <option value="2000~2200">2000~2200</option>
                <option value="2200~2400">2200~2400</option>
                <option value="2400~2600">2400~2600</option>
                <option value="2400~2600">2600~2800</option>
                <option value="2400~2600">2800~3000</option>
                <option value="2400~2600">3000~3200</option>
                <option value="2400~2600">3200~3400</option>
                <option value="2400~2600">3400~3600</option>
            </select>
            <select id="pregnantStageSelect" name="pregnantStage" class="form-control"></select>
            <select id="intakeMode" name="intakeMode" class="form-control"></select>
            <input type='text' id='searchText' name="searchText" class='form-control' placeholder='请输入检索内容'/>
            <div class="vertical-line"></div>
            <button type="button" class="btn btn-lightblue" id="historyTemplate" name="planAdjustButton">历次方案</button>
            <button type="button" class="btn btn-lightblue" id="personTemplate" name="planAdjustButton">个人模板</button>
        </form>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" id="planIntakeTable">
            <thead>
            <tr class="active">
                <th class="text-center" rowspan="2" style="line-height: 52px;">模板名称</th>
                <th class="text-center" rowspan="2" style="line-height: 52px;">能量</th>
                <th class="text-center" colspan="2">碳水化合物</th>
                <th class="text-center" colspan="2">蛋白质</th>
                <th class="text-center" colspan="2">脂肪</th>
                <th class="text-center" rowspan="2" style="line-height: 52px;">孕期阶段</th>
                <th class="text-center" rowspan="2" style="line-height: 52px;">诊断</th>
                <th class="text-center" rowspan="2" style="line-height: 52px;">备注</th>
                <th class="text-center" rowspan="2" style="line-height: 52px;">膳食</th>
                <th class="text-center" rowspan="2" style="line-height: 52px;">操作</th>
            </tr>
            <tr class="active">
                <th class="text-center">克(g)</th>
                <th class="text-center">占比(%)</th>
                <th class="text-center">克(g)</th>
                <th class="text-center">占比(%)</th>
                <th class="text-center">克(g)</th>
                <th class="text-center">占比(%)</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<div class="panel panel-lightblue">
    <div class="panel-heading text-left">
        <i class="fa fa-tag fa-fw"></i> 能量 / 产能营养素明细
    </div>
    <div class="table-responsive">
        <table class="table table-bordered">
            <tr>
                <th class="text-center active" style="width:13%;">名称</th>
                <th class="text-center active" style="width:16%;">计算值</th>
                <th class="text-center active" style="width:16%;">占比</th>
                <td class="text-left" style="padding: 5px 0 0 10px;">
                    <button class="btn btn-lightblue" type="button" id="intakeMealsButton" name="planAdjustButton">
                        <i class="fa fa-reorder fa-fw"></i> 餐次安排
                    </button>
                    <button class="btn btn-lightblue" type="button" id="showIntakeTypeQit" name="planAdjustButton">
                        <i class="fa fa-tags fa-fw"></i> 摄入类型提示
                    </button>
                    <button class="btn btn-lightblue" type="button" id="addPersonTemplate" name="planAdjustButton">
                        <i class="fa fa-plus fa-fw"></i> 添加到膳食模板
                    </button>
                </td>
            </tr>
            <tr>
                <td class="text-center active">能量</td>
                <td><span id="energy_amount" name="info_span">——</span>（kcal）</td>
                <td class="text-center">——</td>
                <td rowspan="4" style="border-top:0px;">
                    <div class="col-xs-12 padding-zero">
                        <div class="div-inline" style="font-weight:bold;">普通食物：</div>
                        <div class="div-inline"><span id="normal_food"><font color='gray'>未选择普通食物</font></span></div>
                    </div>
                    <div class="col-xs-12" style="margin: 7px 0 7px 0;"></div>
                    <div class="col-xs-12 padding-zero">
                        <div class="div-inline" style="font-weight:bold;">营养食品：</div>
                        <div class="div-inline"><span id="product_food"><font color='gray'>未选择营养食品</font></span></div>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="text-center active">碳水化合物</td>
                <td><span id="cbr_amount" name="info_span">——</span>（g）</td>
                <td class="text-center"><span id="cbr_percent" name="info_span">——</span></td>
            </tr>
            <tr>
                <td class="text-center active">蛋白质</td>
                <td><span id="protein_amount" name="info_span">——</span>（g）</td>
                <td class="text-center"><span id="protein_percent" name="info_span">——</span></td>
            </tr>
            <tr>
                <td class="text-center active">脂肪</td>
                <td><span id="fat_amount" name="info_span">——</span>（g）</td>
                <td class="text-center"><span id="fat_percent" name="info_span">——</span></td>
            </tr>
            <tr>
                <td class="text-center active">一周食谱</td>
                <td colspan="3" style="padding: 5px 0 0 5px;">
                    <div class="col-xs-2 no-left" style="margin-bottom: 5px;">
                        <button class="btn btn-lightblue btn-block" type="button" id="addDietButton" name="planAdjustButton">
                            <i class="fa fa-cutlery fa-fw"></i> 选择食谱
                        </button>
                    </div>
                    <div id="diet_list_body" class="col-xs-7 no-left no-right form-inline"></div>
                    <div class="col-xs-3 no-left no-right text-right" style="margin-top: 7px;">
                        <span id="dietName_span"></span>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="text-center active">提示</td>
                <td colspan="3">
                    <div><span style="color: red;">根据孕前标准体重健康孕妇热量推荐为1820(计算)；合并GDM等问题时请遵医嘱</span></div>
                    <div><span id="cbr_prompt" name="prompt_span" style="color: red;"></span></div>
                    <div><span id="protein_prompt" name="prompt_span" style="color: red;"></span></div>
                    <div><span id="fat_prompt" name="prompt_span" style="color: red;"></span></div>
                </td>
            </tr>
            <tr>
                <td colspan="4" style="padding: 2px;">
                    <textarea maxlength="400" class="form-control" onblur="saveText(this.value);" id="diagnosisDietRemarkText" placeholder="请输入膳食方案备注……">${diagnosisVo.diagnosisDietRemark }</textarea>
                </td>
            </tr>
        </table>
    </div>
</div>

<div id="intakeMealsModal" class="modal fade bs-example-modal">
    <div class="modal-dialog" style="width: 1200px; margin-bottom: 0px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue" style="margin-bottom: 5px;">
                    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 能量 / 产能营养素明细<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                    <div class="panel-body" style="padding: 0px;">
                        <div class="table-responsive col-xs-5 no-left no-right">
                            <table class="table table-bordered table-sm" style="margin-bottom: 0px;">
                                <tr class="active">
                                    <th class="text-center">名称</th>
                                    <th class="text-center">计算值</th>
                                    <th class="text-center">占比</th>
                                </tr>
                                <tr>
                                    <td class="text-center">能量</td>
                                    <td><span id="energy_amount_modal" name="info_span">——</span>（kcal）</td>
                                    <td class="text-center">——</td>
                                </tr>
                                <tr>
                                    <td class="text-center">碳水化合物</td>
                                    <td><span id="cbr_amount_modal" name="info_span">——</span>（g）</td>
                                    <td class="text-center"><span id="cbr_percent_modal" name="info_span">——</span></td>
                                </tr>
                                <tr>
                                    <td class="text-center">蛋白质</td>
                                    <td><span id="protein_amount_modal" name="info_span">——</span>（g）</td>
                                    <td class="text-center"><span id="protein_percent_modal" name="info_span">——</span></td>
                                </tr>
                                <tr>
                                    <td class="text-center">脂肪</td>
                                    <td><span id="fat_amount_modal" name="info_span">——</span>（g）</td>
                                    <td class="text-center"><span id="fat_percent_modal" name="info_span">——</span></td>
                                </tr>
                            </table>
                        </div>
                        <div class="table-responsive col-xs-7 no-left no-right">
                            <table class="table table-bordered table-sm" style="margin-bottom: 0px;">
                                <thead>
                                <tr class="active">
                                    <th class="text-center" colspan="6">普通食品</th>
                                </tr>
                                <tr>
                                    <th class="text-center active">类别</th>
                                    <th class="text-center active">份数</th>
                                    <th class="text-center active">类别</th>
                                    <th class="text-center active">份数</th>
                                    <th class="text-center active">类别</th>
                                    <th class="text-center active">份数</th>
                                </tr>
                                </thead>
                                <tbody id="normalFood_tbody"></tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-lightblue" style="margin-bottom: 5px;">
                    <div class="panel-heading text-left"><i class="fa fa-tag fa-fw"></i> 营养食品</div>
                    <div class="panel-body" style="padding: 0px;">
                        <div class="table-responsive" style="max-height: 187px;">
                            <table class="table table-bordered table-sm" style="margin-bottom: 0px;">
                                <thead>
                                <tr class="active">
                                    <th class="text-center">名称</th>
                                    <th class="text-center">单位</th>
                                    <th class="text-center">规格</th>
                                    <th class="text-center">频次</th>
                                    <th class="text-center">一日剂量</th>
                                </tr>
                                </thead>
                                <tbody id="intakeProduct_tbody">
                                <tr id="intakeProduct_norecord_tr">
                                    <td class="text-center" colspan="10"><h4>未选择营养食品！</h4></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel panel-lightblue">
                    <div class="panel-heading text-left">编辑膳食执行清单</div>
                    <div class="panel-body" style="padding: 0px;">
                        <div class="table-responsive" style="max-height: 415px;">
                            <form id="planCreateForm" action="${common.basepath }/${applicationScope.URL.Platform.RECEIVE_PLAN_ADD}" method="post">
                                <input type="hidden" id="diaId" name="diagnosisId" value="${diagnosisId }"/>
                                <input type="hidden" name="intakeId"/>
                                <input type="hidden" id="planId" name="planId"/>
                                <input type="hidden" id="intakeName" name="intakeName"/>
                                <input type="hidden" id="dietId" name="dietId"/>
                                <input type="hidden" id="dietName" name="dietName"/>
                                <input type="hidden" id="foodDays" name="foodDays"/>
                                <input type="hidden" id="intakeCaloric" name="intakeCaloric"/>
                                <input type="hidden" id="intakeCbr" name="intakeCbr"/>
                                <input type="hidden" id="intakeCbrPercent" name="intakeCbrPercent"/>
                                <input type="hidden" id="intakeProtein" name="intakeProtein"/>
                                <input type="hidden" id="intakeProteinPercent" name="intakeProteinPercent"/>
                                <input type="hidden" id="intakeFat" name="intakeFat"/>
                                <input type="hidden" id="intakeFatPercent" name="intakeFatPercent"/>
                                <input type="hidden" id="detailList" name="detailList"/>
                                <input type="hidden" id="diagnosisDietRemark" name="diagnosisDietRemark"/>
                                <table class="table table-bordered table-sm" style="margin-bottom: 0px;">
                                    <tbody id="intakeMeals_tbody"></tbody>
                                    <tfoot>
                                    <tr>
                                        <td class="text-center active">备注：</td>
                                        <td colspan="2" style="padding: 4px;"><textarea id="foodRecommend" name="foodRecommend" class="form-control" rows="10">${foodRecommend }</textarea></td>
                                    </tr>
                                    </tfoot>
                                </table>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--编辑食谱Modal--%>
<input type="hidden" id="foodDay"/>
<input type="hidden" id="amountLevel"/>
<div id="dietDetailModal" class="modal fade bs-example-modal">
    <div class="modal-dialog" style="width: 1200px; margin-bottom: 0px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="panel panel-lightblue">
                    <div class="panel-heading"><i class="fa fa-tag fa-fw"></i> 编辑食谱<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a></div>
                    <div class="panel-body">
                        <div class="panel panel-default col-xs-2">
                            <div class="panel-body" id="zTree_div">
                                <ul id="zTree" class="ztree"></ul>
                            </div>
                        </div>
                        <div class="col-xs-3" style="padding: 0px 5px;">
                            <div class="row">
                                <div class="col-xs-7 no-right" id="foodCondition" style="padding-bottom: 6px;">
                                    <form id="queryForm" action="${common.basepath }/${applicationScope.URL.Food.FOOD_CUISINE_AJAX_QUERY}" method="post">
                                        <div class="input-group">
                                            <input type="hidden" name="foodTreeTypeLike" id="categoryIdQuery"/>
                                            <input type='text' id='foodName' name="foodName" class='form-control' placeholder='请输入菜名'/>
                                            <span class="input-group-btn">
									        	<button class="btn btn-default" type="button" style="padding: 6px;"><i class="fa fa-search fa-fw"></i></button>
									      	</span>
                                        </div>
                                    </form>
                                </div>
                                <div class="col-xs-5 no-left">
                                    <select id="foodMeal" name="foodMeal" class="form-control" style="width: 95%; float: right; padding: 2px;"></select>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <table id="foodListTable" class="table table-bordered table-hover">
                                    <thead>
                                    <tr class="active">
                                        <th class="text-center">菜名</th>
                                        <th class="text-center">操作</th>
                                    </tr>
                                    </thead>
                                </table>
                            </div>
                        </div>
                        <div class="col-xs-7 padding-zero">
                            <div class="panel panel-default panel-body" style="padding: 6px; margin-bottom: 6px;">
                                <div class="col-xs-12 padding-zero">
                                    推荐能量：<span id="amountLevelSpan"></span> &nbsp;
                                    <span id="dosage"></span>
                                </div>
                            </div>
                            <div class="table-responsive" style="">
                                <table class="table table-bordered table-padding-2">
                                    <thead>
                                    <tr class="active">
                                        <th class="text-center" style="width:15%;">餐次</th>
                                        <th class="text-center" style="width:28%">菜名</th>
                                        <th class="text-center" style="width:28%">食材</th>
                                        <th class="text-center" style="width:13%;">用量（g）</th>
                                        <th class="text-center" style="width:16%;">能量（kcal）</th>
                                    </tr>
                                    </thead>
                                    <tbody id="intake_diet_detail_inbody"></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>
