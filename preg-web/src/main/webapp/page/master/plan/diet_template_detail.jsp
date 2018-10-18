<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <%@ include file="/common/common.jsp" %>
    <title>配置食谱</title>
    <!-- 标签 -->
    <script type="text/javascript" src="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.js"></script>
    <link rel="stylesheet" type="text/css" href="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.css">
    <!-- Ztree -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/ztree.css"/>
    <style type="text/css">
        .panel {
            margin-bottom: 0px;
        }

        #buttonList .checkClass {
            background-color: #1B9AF7;
            border-color: #1B9AF7;
            color: #fff !important;
        }

        #buttonList .button:hover, .button:focus {
            background-color: #1B9AF7;
            color: #fff !important;
        }
    </style>
    <script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.core-3.5.js"></script>
    <script type="text/javascript" src="${common.basepath}/common/plugins/zTree/jquery.ztree.exedit-3.5.js"></script>
    <script type="text/javascript">
        // 全局食谱id、名称
        var dietId = '${dietId }';
        var dietName = '${dietName }';

        // 树结点      数据集合、当前选中结点、树对象
        var zNodes = ${treeNodes};
        var selectNode;
        var treeObj;
    </script>
    <script type="text/javascript" charset="utf-8" src="${common.basepath}/page/master/plan/diet_template_detail.js"></script>
</head>
<body>
<!-- 食谱模版 -->
<div class="row">
	<div class="panel-body" id="addDiv">
	    <div id="timeline-panel">
	        <div class="form-group text-left">
	            <button class="btn btn-primary" id="backButton" name="clickButton">返回列表</button>
	        </div>
	        <div class="panel panel-lightblue">
	            <div class="panel-heading">编辑食谱</div>
	            <div class="panel-body" style="padding:0px !important;">
	                <div class="panel panel-default col-xs-2">
	                    <div class="panel-body" id="zTree_div">
	                        <ul id="zTree" class="ztree" style="overflow: hidden;"></ul>
	                    </div>
	                </div>
	                <div class="panel panel-default col-xs-3 padding-zero" style="border: 1px solid #ddd;height: 510px;">
	                    <div class="row" style="padding:5px;">
		                    <div class="col-xs-7 no-right" id="foodCondition">
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
	                <div class="panel panel-default col-xs-7 padding-zero" style="border: 1px solid #ddd;">
	                    <div class="timeline-panel" id="buttonList" style="padding:2px;">
	                        <input type="hidden" name="foodDay" id="foodDay"/>
	                        <!-- <i class="fa fa-plus"></i>添加 -->
	                        <button class="button button-default button-box button-circle" style="font-size: 14px;color:#1B9AF7;font-weight: bold;" onclick="alertAddDay();">
	                            <i class="fa"></i>添加
	                        </button>
	                    </div>
	                    <div class="timeline-panel form-inline" id="buttonList" style="padding:1px;border-top:1px solid #ddd;">
	                        <select id="amountLevel" name="amountLevel" class="form-control">
	                            <option value="">请选择能量范围</option>
	                            <option value="1000~1200">1000~1200</option>
	                            <option value="1200~1400">1200~1400</option>
	                            <option value="1400~1600">1400~1600</option>
	                            <option value="1600~1800">1600~1800</option>
	                            <option value="1800~2000">1800~2000</option>
	                            <option value="2000~2200">2000~2200</option>
	                            <option value="2200~2400">2200~2400</option>
	                            <option value="2400~2600">2400~2600</option>
	                            <option value="2600~2800">2600~2800</option>
	                            <option value="2800~3000">2800~3000</option>
	                        </select>
	                        <div style="margin-left:8px;display: inline-block;" id="dosage"></div>
	                        <button class="btn btn-default" style="color:red;float:right;" id="deleteDetail" onclick="removeFoodDay()">删除</button>
	                    </div>
	                    <div class="table-responsive" style="max-height:427px;">
	                        <table class="table table-bordered table-hover table-padding-4">
	                            <thead>
	                            <tr class="active">
	                                <th class="text-center" style="width:15%;">餐次</th>
	                                <th class="text-center" style="width:25%" colspan='2'>菜名</th>
	                                <th class="text-center" style="width:25%">食材</th>
	                                <th class="text-center" style="width:15%;">用量（g）</th>
	                                <th class="text-center" style="width:20%;">能量（kcal）</th>
	                            </tr>
	                            </thead>
	                            <tbody id="addFoodExt"></tbody>
	                        </table>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</div>

<!-- 添加页面 -->
<div id="editModel" class="modal fade bs-example-modal" style="dispaly:none;">
    <div class="modal-dialog" style="margin-top: 130px; width: 600px;">
        <div class="modal-content">
            <div class="modal-header">
                <label class="label-title"><i class="fa fa-edit fa-fw"></i>增加一日食谱</label>
            </div>
            <div class="modal-body" style="height: 80px;">
                <div class="form-group">
                    <div class="col-xs-1"></div>
                    <label class="col-xs-3 control-label">一日食谱名称</label>
                    <div class="col-xs-6">
                        <input id="foodAddDay" type="text" class="form-control" maxlength="4"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" onclick="addDayButton();" class="btn btn-primary">确定</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
