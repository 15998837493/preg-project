<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <%@ include file="/common/common.jsp" %>
    <title>课程详情</title>
    <script type="text/javascript">
        var addOptions = {
            rules: {
                pregDeName: {maxlength: 80,required: true},
                pregDeCode: {
                	maxlength: 64,
                    required: true,
                    remote : {
        				url : URL.get("Master.PREGDECODE_VALIDATE"),// 后台处理程序
        				type : "post", // 数据发送方式
        				dataType : "json", // 接受数据格式
        				data : { // 要传递的数据，默认已传递应用此规则的表单项
        					pregId : function() {
        						return $("#saveDetailForm").find("input[name='pregId']").val();
        					},
        					pregDeCode : function() {
        						return $("#pregDeCode").val();
        					},
        					type : function() {
        						return "add";
        					},
        					random : function() {
        						return Math.random();
        					}
        				}
        			}

                    }
            },
            messages : {
            	pregDeCode : {
        			remote : "该编码已经存在，请重新输入"
        		}
        	},
            //设置错误信息显示到指定位置
            errorPlacement: function(error, element) {
                element = element.parent();
                common.showmassage(error, element);
            },
            success: $.noop,
            submitHandler: function(form) {
                $(form).ajaxPost(dataType.json,function(data){
                    if(data.result){
                        initDetailList();
                        $("#pregDeCode").val("");
                        $("#pregDeName").val("");
                    }else{
                        layer.alert(data.message);
                    }
                });
            }
        };

        var updateOptions = {
            rules: {
                /* intakeMealtype: {required: true},
                intakeType: {required: true},
                intakeCount: {required: true, decmal6: true}, */
                pregDeName: {maxlength: 80,required: true},
                pregDeCode: {
                	maxlength: 64,
                    required: true,
                    remote : {
        				url : URL.get("Master.PREGDECODE_VALIDATE"),// 后台处理程序
        				type : "post", // 数据发送方式
        				dataType : "json", // 接受数据格式
        				data : { // 要传递的数据，默认已传递应用此规则的表单项
        					pregId : function() {
        						return $("#saveDetailForm").find("input[name='pregId']").val();
        					},
        					pregDeCode : function() {
        						return $("#updateForm [name='pregDeCode']").val();
        					},
        					pregDeOldCode: function() {
        						return $("#updateForm [name='pregDeOldCode']").val();
        					},
        					type : function() {
        						return "update";
        					},
        					random : function() {
        						return Math.random();
        					}
        				}
        			}
                }
            },
            messages : {
            	pregDeCode : {
        			remote : "该编码已经存在，请重新输入"
        		}
        	},
            //设置错误信息显示到指定位置
            errorPlacement: function(error, element) {
                element = element.parent();
                common.showmassage(error, element);
            },
            success: $.noop,
            submitHandler: function(form) {
                $(form).ajaxPost(dataType.json,function(data){
                    if(data.result){
                        $("#intakeDetailModal").modal("hide");
                        initDetailList();
                    }else{
                        layer.alert(data.message);
                    }
                });
            }
        };

        $().ready(function(){
            //加入必填项提示
            $("#saveDetailForm").validate(addOptions);
            common.requiredHint("saveDetailForm", addOptions);
            //加入必填项提示
            $("#updateForm").validate(updateOptions);
            common.requiredHint("updateForm", updateOptions);
            $("#backButton").click(function(){
                common.pageForward(PublicConstant.basePath+"/page/master/plan/course_view.jsp");
            });

            initDetailList();
        });

        function initDetailList(){
            $("#t_foot").empty();
            var url = URL.get("Master.PREGNANCYCOURSE_DETAIL_QUERY");
            var params = "pregId="+$("#saveDetailForm").find("input[name='pregId']").val();
            ajax.post(url, params, dataType.json, function(data){
                if(data.result){
                    if(!common.isEmpty(data.value)){
                        $(data.value).each(function(index, detail){
                            $("#t_foot").append(
                                "<tr id='"+detail.id+"_tr'>"+
                                "	<td class='text-center'>"+detail.pregDeCode+"</td>" +
                                "	<td class='text-center'>"+detail.pregDeName+"</td>" +
                                "	<td class='text-center'>"+
                                "		<a style='cursor: pointer;' onclick='moveRow(\""+detail.id+"\",\"up\")'><i class='fa fa-edit fa-fw'></i>上移</a>&nbsp;&nbsp;"+
                                "		<a style='cursor: pointer;' onclick='moveRow(\""+detail.id+"\",\"down\")'><i class='fa fa-edit fa-fw'></i>下移</a>&nbsp;&nbsp;"+
                                "		<a style='cursor: pointer;' onclick='editIntakeDetail(\""+detail.id+"\",\""+detail.pregDeName+"\",\""+detail.pregDeCode+"\")'><i class='fa fa-edit fa-fw'></i>编辑</a>&nbsp;&nbsp;"+
                                "		<a style='cursor: pointer;' onclick='removeIntakeDetail(\""+detail.id+"\")'><i class='fa fa-remove fa-fw'></i>删除</a>"+
                                "	</td>" +
                                "</tr>"
                            );
                        });
                    }
                } else {
                    layer.alert(data.message);
                }
            });
        }

        function editIntakeDetail(id,pregDeName, pregDeCode){
            $("#updateForm [name='id']").val(id);
            $("#updateForm [name='pregDeName']").val(pregDeName);
            $("#updateForm [name='pregDeCode']").val(pregDeCode);
            $("#updateForm [name='pregDeOldCode']").val(pregDeCode);
            $("#intakeDetailModal").modal("show");
        }

        function removeIntakeDetail(id){
            var url = URL.get("Master.PREGNANCYCOURSE_DETAIL_REMOVE");
            var params = "id="+id;
            ajax.post(url, params, dataType.json, function(data){
                if(data.result){
                    $("#"+id+"_tr").remove();
                } else {
                    layer.alert(data.message);
                }
            });
        }
        //向上移动
        function moveRow(id,flag){
            $.ajax({
                url: PublicConstant.basePath + URL.Master.MOVE_PREGNANCYCOURSE_DETAIL,
                data: {"flag":flag,"id":id},
                dataType: 'json',
                success: function(data) {
                    if(data.value){
                        console.log(data);
                        initDetailList();
                    } else {
                        layer.alert(data.message);
                    }
                },
                error: function(e) {
                    console.log(e.responseText);
                }
            });
        }
    </script>
<body>
<form id="updateForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.Master.PREGNANCYCOURSE_DETAIL_UPDATE}">
    <input type="hidden" name="id" />
    <div id="intakeDetailModal" class="modal fade bs-example-modal">
        <div class="modal-dialog" style="margin-top: 130px;">
            <div class="modal-content">
                <div class="modal-header">
                    <label class="label-title"><i class="fa fa-edit fa-fw"></i>编辑课程明细</label>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">编码</label>
                        <div class="col-xs-7">
                        	<input name="pregDeOldCode" type="hidden" maxlength="30" />
                            <input name="pregDeCode" class="form-control" type="text" maxlength="30" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">名称</label>
                        <div class="col-xs-7">
                            <input name="pregDeName" class="form-control" type="text" maxlength="30" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" >确定</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form>

<%-- <label class="label-title"><i class="fa fa-cog fa-fw"></i> 【${name}】设置</label> --%>
<div class="timeline-body row-top">
    <form id="saveDetailForm" class="form-horizontal" action="${common.basepath }/${applicationScope.URL.Master.PREGNANCYCOURSE_DETAIL_ADD}">
        <input type="hidden" name="pregId" value="${id }" />
        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                    <tr class="active">
                        <th class="text-center" style="width: 28%">代码</th>
                        <th class="text-center" style="width: 28%">课程名称</th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="text-center">
                            <div class="col-xs-10 col-xs-offset-1 input-group">
                                <input id="pregDeCode" name="pregDeCode" type="text" class="form-control input-sm" placeholder="请输入编码" />
                            </div>
                        </td>
                        <td class="text-center">
                            <div class="col-xs-10 col-xs-offset-1 input-group">
                                <input id="pregDeName" name="pregDeName" type="text" class="form-control input-sm" placeholder="请输入名称" />
                            </div>
                        </td>
                        <td class="text-center">
                            <button class="btn btn-primary btn-sm" type="submit"><i class='fa fa-plus fa-fw'></i>添加</button>
                            <button class="btn btn-primary btn-sm" type="button" onclick="initDetailList();"><i class='fa fa-refresh fa-fw'></i>刷新</button>
                        </td>
                    </tr>
                    </tbody>
                    <tfoot id="t_foot"></tfoot>
                </table>
            </div>
        </div>
    </form>
    <div class="col-xs-2 col-xs-offset-5">
        <button class="btn btn-primary btn-block" type="button" id="backButton"><i class="fa fa-reply fa-fw"></i> 返回</button>
    </div>
</div>
</body>
</html>