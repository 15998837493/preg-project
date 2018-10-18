<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp" %>
<title>检查收费项目配置</title>

<style type="text/css">
.label-checkbox{
	position: relative;
	display: inline-block;
	padding-left: 7px;
	margin-bottom: 0;
	vertical-align: middle;
	font-weight: normal;
	cursor: pointer;
}
#inspect-checkbox{
	position: absolute;
	margin-left: -18px;
}
</style>

<script type="text/javascript">
var itemList = ${itemList};// 检验项目列表
var itemListData = [];
if(!_.isEmpty(itemList)){
	$.each(itemList, function(index, value){
		itemListData.push({name:value.itemName,val:value});
	});
}
</script>
<script charset="UTF-8" src="${common.basepath}/common/mnt/js/pinyin.js"></script>
<script charset="utf-8" src="${common.basepath}/page/master/items/hospital_inspect_pay.js"></script>

</head>
<body>

<!-- 主体一览页面 -->
<div class="row">
	<div class="panel-body form-inline" id="inspectPayCondition">
		<form id="inspectPayQueryForm" action="${applicationScope.URL.item.INSPECT_PAY_QUERY}" method="post">
			<input name="inspectName" class="form-control" type="text" placeholder="项目名称">
			<button type="button" id="searchButton" name="inspectPayButton" class="btn btn-default">
				<i class="fa fa-search fa-fw"></i>查询
			</button>
			<div class="vertical-line"></div>
			<button id="addButton" name="inspectPayButton" type="button" class="btn btn-default">
				<i class="fa fa-plus fa-fw"></i> 增加
			</button>
			<button id="updateButton" name="inspectPayButton" type="button" class="btn btn-default">
				<i class="fa fa-edit fa-fw"></i> 编辑
			</button>
			<button id="removeButton" name="inspectPayButton" type="button" class="btn btn-default">
				<i class="fa fa-remove fa-fw"></i> 删除
			</button>
		</form>
	</div>	
</div>			
<div class="table-responsive">
	<table  id="inspectPayListTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class="text-center">选择</th>
				<th class="text-center">项目名称</th>
				<th class="text-center">适合性别</th>
				<th class="text-center">项目排序</th>
				<th class="text-center">出结果时限</th>
		   	</tr>
		</thead>
	</table>
</div>

<!-- 弹出页面-编辑收费项目信息 -->
<form id="inspectPayEditForm" action="${applicationScope.URL.item.INSPECT_PAY_SAVE }" class="form-horizontal" method="post">
	<div id="editInspectPayModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg" >
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-search"></i>增加/编辑 收费项目
							<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
						</div>
						<div class="row">
		                    <div class="col-xs-9 col-xs-offset-1 row-top">
		                        <input type="hidden" id="inspectId" name="inspectId" />
		                        <div class="form-group">
		                            <label class="col-xs-3 control-label">项目名称</label>
		                            <div class="col-xs-9">
		                                <input id="inspectName" name="inspectName" placeholder="请输入名称"  class="form-control" maxlength="50" type="text"/>
		                            	<input id="inspectOldName" name="inspectOldName" type="hidden"/>
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label class="col-xs-3 control-label">适宜性别</label>
		                            <div class="col-xs-9">
		                                <select id="inspectSex" name="inspectSex" class="form-control"></select>
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label class="col-xs-3 control-label">项目价格</label>
		                            <div class="col-xs-9">
		                                <input id="inspectPrice" name="inspectPrice" placeholder="请输入价格"  class="form-control" maxlength="7" type="text" />
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label class="col-xs-3 control-label">项目排序</label>
		                            <div class="col-xs-9">
		                                <input id="inspectSort" name="inspectSort" placeholder="请输入数字"  class="form-control" maxlength="50" type="text" />
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <label class="col-xs-3 control-label">出结果时限</label>
		                            <div class="col-xs-9">
		                                <select id="resultsSuggest" name="resultsSuggest" class="form-control"></select>
		                            </div>
		                        </div> 
		                        <div class="form-group">
									<label class="col-xs-3 control-label">复查提示</label>
									<div class="col-xs-9">
										<textarea id="reviewHints" 
								                  name="reviewHints" 
								                  class="form-control text-left" 
								                  placeholder="点击输入内容" 
								                  style="background-color: white;" maxlength="1000"></textarea>
									</div>   	
		                        </div>  
		                        <div class="form-group" >
									<label class="col-xs-3 control-label">检验项目</label>
									<div class="col-xs-9">
										<input id="hospital_inspect_text" placeholder="请输入检验项目" class="form-control" type="text"/>
										<div class="panel panel-default table-responsive" style="max-height: 146px;margin-top: 5px;">
											<table class="table table-bordered table-padding-4" style="margin: 0px;" id="hospital_inspect">
												<tr id="hospital_inspect_no_record"><td><label class='label-checkbox' style="color:gray;">未配置检验项目！</label></td></tr>
											</table>
										</div>
									</div>   	
		                        </div> 
							</div>
						</div>
					</div>
					<div class="panel-body padding-zero" style="padding: 0px;">
						<div class="col-xs-2 col-xs-offset-10 no-right">
							<button type="submit" class="btn btn-primary btn-block">确认</button>
						</div>
					</div>	
				</div>		
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>

</body>
</html>