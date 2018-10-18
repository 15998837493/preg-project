<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/common/common.jsp"%>
<title>查询客户</title>
<script type="text/javascript">
layer.close(layer.index);

//取消回车事件
$(document).keydown(function(event){
    switch(event.keyCode){
       case 13:return false; 
    }
});
</script>
<script type="text/javascript" charset="utf-8" src="${common.basepath}/page/birthending/birthending_customer_list.js"></script>
</head>
<body>
<input type="hidden" id="custId"/>
<div class="row">
	<div class="panel-body form-inline" id="customerCondition">
		<form id="customerQuery" action="${common.basepath }/${applicationScope.URL.Customer.QUERY_CUST_BIRTHENDING}" method="post" class="form-horizontal">
			<input type='text' name="content" id="content" class='form-control' style="width:16%" placeholder='请输入 病案号/ID/姓名/身份证号' />
			<button type="button" id="searchButton" name="operateButton" class="btn btn-default">
				<i class="fa fa-search fa-fw"></i>查询
			</button>
			<button type="button" id="registerButton" name="operateButton" class="btn btn-default">
				<i class="fa fa-plus fa-fw"></i>分娩登记
			</button>
		</form>
	</div>
</div>
<div class="table-responsive">
	<table id="customerTable" class="table table-bordered table-hover">
		<thead>
			<tr class="active">
				<th class='text-center'>病案号</th>
				<th class='text-center'>ID</th>
				<th class='text-center'>姓名</th>
				<th class='text-center'>分娩日期</th>
				<th class='text-center'>身份证号</th>
				<th class='text-center'>手机号</th>
				<th class='text-center'>操作</th>
			</tr>
		</thead>
		<tbody id="t_body">
			<tr><td colspan='100' class='text-center'><h4>没有数据！</h4></td></tr>
		</tbody>
	</table>
</div>

<!-- 弹窗添加、编辑 -->
<form id="editItemForm" name="editItemForm" action="" class="form-horizontal" method="post">
	<div id="editItemModal" class="modal fade bs-example-modal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-body">
					<div class="panel panel-lightblue">
						<div class="panel-heading text-left">
							<i class="fa fa-plus fa-fw"></i>分娩登记
							<a class="close" data-dismiss="modal"><i class="fa fa-remove fa-fw"></i></a>
						</div>
						<div class="panel-body">
							<div class="form-group row-top">
				                <label class="col-xs-4 control-label">病案号</label>
				                <div class="col-xs-5">
				                    <input id="custSikeId" name="custSikeId" class="form-control" type="text" maxlength="80" value=""/>
				                </div>
						    </div>
							<div class="form-group">
				        		<label class="col-xs-4 control-label">ID</label>
				                <div class="col-xs-5">
				                    <input type="text" id="custPatientId" name="custPatientId" class="form-control" maxlength="50"/>
				                </div>
				            </div>
							<div class="form-group">
				       			<label class="col-xs-4 control-label">姓名</label>
				                <div class="col-xs-5">
				                    <input id="custName" name="custName" class="form-control" type="text" maxlength="30" value=""/>
									<input type="hidden" id="custSex" name="custSex" value="female"/>
				                </div>
				            </div>
				            <div class="form-group">
				                <label class="col-xs-4 control-label">身份证号</label>
				                <div class="col-xs-5">
				                    <input id="custIcard" name="custIcard" class="form-control" type="text" maxlength="23"/>
				                </div>
				            </div>
						</div>
					</div>
					<div class="panel-body padding-zero">
						<div class="col-xs-2 col-xs-offset-10 no-right">
							<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-save fa-fw"></i>保存</button>
						</div>
					</div>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</form>
</body>
</html>
