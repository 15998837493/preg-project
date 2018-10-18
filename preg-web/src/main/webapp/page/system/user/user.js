$.validator.addMethod("RegUserCode",
function(value, element) {
    return new RegExp(validateRegExp.id).test(element.value);
},
'用户名只允许字母与数字组合');

$.validator.addMethod("newMobile",
function(value, element) {
    if (element.value == '' || (/^1[3|4|5|8][0-9]\d{8}$/).test(element.value)) {
        return true;
    } else {
        return false;
    }
},
'请输入正确的手机号');

var operateType;

/**
 * 验证参数设置
 */
var userOptions = {
    rules: {
        userCode: {
            required: true,
            RegUserCode: true,
            remote: {
                url: URL.get("User.USER_VALIDATE_CODE"),
                //后台处理程序  
                type: "post",
                //数据发送方式  
                dataType: "json",
                //接受数据格式     
                data: { //要传递的数据，默认已传递应用此规则的表单项  
                    userCode: function() {
                        return $("#userCode").val();
                    },
                    operateType: function() {
                        return operateType;
                    },
                    random: function() {
                        return Math.random();
                    }
                }
            }
        },
        userName: {
            required: true
        },
        userType: {
            required: true
        },
        userPhone: {
            newMobile: true
        },
        roleId: {
            required: true
        }
    },
    messages: {
        userCode: {
            remote: "用户名已经存在，请重新输入"
        }
    },
    //设置错误信息显示到指定位置
    errorPlacement: function(error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        layer.confirm("确定要执行【保存】操作？",
        function(index) {
            $(form).ajaxPost(dataType.json,
            function(data) {
                if (operateType == "add") {
                    datatable.add(userTable, data.value); // 添加
                    $("#updateModal").modal("hide");
                } else if (operateType == "update") {
                    datatable.update(userTable, data.value, userRow); // 修改
                    $("#updateModal").modal("hide");
                } else {
                    common.pageForward(URL.get("User.USER_VIEW"));
                }
                //datatable.remove(userTable, userRow);// 删除
            });
        });
    }
};

/**
 * 验证参数设置
 */
var userAssistantOptions = {
    //设置错误信息显示到指定位置
    errorPlacement: function(error, element) {
        element = element.parent();
        common.showmassage(error, element);
    },
    success: $.noop,
    submitHandler: function(form) {
        var author = $("#userTypeSign").val();
        if (author == 'doctor' && $("*[name='assistantId']").is(":checked") == false) {
            layer.msg("助理为必选项!");
        } else if (author == 'assistant' && !common.isEmpty($("#docotrIsNone").val())) {
            layer.msg("没有可配置的医生!");
        } else {
            layer.confirm("确定要执行【保存】操作？",
            function(index) {
                $(form).ajaxPost(dataType.json,
                function(data) {
                    $("#configModal").modal("hide");
                    layer.alert("配置成功！");
                    //datatable.remove(userTable, userRow);// 删除
                });
            });
        }
    }
};

/**
 * 返回一个2维数组
 */
function formatArrayData(arr) {
    var newArr = [];
    if (arr.length == 1) {
        newArr.push(arr.slice(0));
    } else {
        for (var x = 1; x < arr.length; x += 2) {
            newArr.push(arr.slice((x - 1), x + 1));
        }
        if (arr.length % 2 != 0) {
            newArr.push(arr.slice(arr.length - 1));
        }
    }
    return newArr;
}

$().ready(function() {
    // 初始化	
    common.initCodeSelect("sex", "sex", "userSex");
    common.initCodeSelect("STATUS", "STATUS", "userStatus");
    common.initCodeSelect("USERTYPE", "USERTYPE", "status", null, "请选择用户类别");
    // 初始化用户出生日期
    common.initDate(null, null, 4, "#userBirthday");
    // 按钮点击事件
    $("button[name='userButton']").click(function() {
        var userId = $("#userId").val();
        var userType = $("#userTypeSign").val();
        if (this.id == "searchButton") {
            //userTable = datatable.table(tableOptions);
            datatable.search(userTable, tableOptions);
        }
        if (this.id == 'addButton') {
            operateType = "add";
            $("#addUserForm").attr("action", URL.get("User.USER_ADD"));
            common.clearForm("addUserForm");
            $('#showpic').attr("src", PublicConstant.basePath + "/common/images/upload_head.png");
            $("#userCode").attr("readonly", false);
            $("#updateModal").modal("show");
        }
        if (this.id == 'editButton' && common.isChoose(userId)) {
            operateType = "update";
            $("#addUserForm").attr("action", URL.get("User.USER_UPDATE"));
            common.clearForm("addUserForm");
            $("#addUserForm").jsonToForm(userData);
            $("#userHeadSculptureOld").val(userData.userHeadSculpture);
            $("#userBirthday").datetimepicker("update", userData.userBirthday);
            $("#userCode").attr("readonly", true);
            var serverPath = URL.get("resource.server.path") + URL.get("path.constants.user_head_image");
            $('#showpic').attr("src", PublicConstant.basePath + "/common/images/upload_head.png");
            if (!common.isEmpty(userData.userHeadSculpture)) {
                $('#showpic').attr("src", serverPath + userData.userHeadSculpture);
            };
            $("#updateModal").modal("show");
        }
        if (this.id == 'resetPswButton' && common.isChoose(userId)) {
            pswResetSubmit(userId);
        }
        if (this.id == 'config' && common.isChoose(userId)) {
            if (userType == 'doctor') {
                common.clearForm("configUserForm"); // 清空表单
                $("#configTitle").html("配置助理");
                $("#configAuthorName").html("医生姓名");
                $("#configUserForm").attr("action", URL.get("Assistant.USER_ADD_ASSISSTANT"));
                $("#configDoctorName").val($("#userNameSign").val()); // 医师姓名
                $("#doctorId").val($("#userId").val()); // 医师ID
                $(".assistant").remove(); // 清空助理
                var assistantId = "";
                // 查询医生对应的助理
                var url = URL.get("Assistant.USER_QUERY");
                var params = "doctorId=" + $("#userId").val();
                ajax.post(url, params, dataType.json,
                function(data) {
                    var result = data.data;
                    if (result.length == 1) {
                        assistantId = result[0].assistantId; // 可能有一个或多个助理，逗号分割
                    } else if (result.length > 1) {
                        var array = [];
                        $.each(result,
                        function(index, assis) {
                            array.push(assis.assistantId);
                        });
                        assistantId = array.join();
                    }
                },
                false, false);
                // 查询助理
                var url = URL.get("User.USER_QUERY");
                var params = "userType=assistant";
                ajax.post(url, params, dataType.json,
                function(data) {
                    var result = data.data;
                    if (result.length > 0) {
                        $(".assistant").remove();
                        var resultArray = formatArrayData(result);
                        for (var x = 0; x < resultArray.length; x++) {
                            var hml = "";
                            var checkOne = "";
                            var checkTwo = "";
                            var assistantIds = assistantId.split(",");
                            if (assistantIds.includes(resultArray[x][0].userId)) {
                                checkOne = "checked";
                            }
                            if (!common.isEmpty(resultArray[x][1]) && assistantIds.includes(resultArray[x][1].userId)) {
                                checkTwo = "checked";
                            }
                            if (x == 0) {
                                var partHml = "";
                                if (!common.isEmpty(resultArray[x][1])) {
                                    partHml = "<div class='col-xs-4'>" + "<div class='radio'>" + "<label>" + "<input type='radio' " + checkTwo + " value='" + resultArray[x][1].userId + "' name='assistantId'/>" + resultArray[x][1].userName + "</label>" + "</div>" + "</div>";
                                }
                                hml = "<div class='form-group assistant'>" + "<label class='col-xs-4 control-label'>选择助理</label>" + "<div class='col-xs-4'>" + "<div class='radio'>" + "<label>" + "<input type='radio' " + checkOne + " value='" + resultArray[x][0].userId + "' name='assistantId'/>" + resultArray[x][0].userName + "</label>" + "</div>" + "</div>" + partHml + "</div>";
                            } else {
                                var partHml = "";
                                if (!common.isEmpty(resultArray[x][1])) {
                                    partHml = "<div class='col-xs-4'>" + "<div class='radio'>" + "<label>" + "<input type='radio' " + checkTwo + " value='" + resultArray[x][1].userId + "' name='assistantId'/>" + resultArray[x][1].userName + "</label>" + "</div>" + "</div>";
                                }
                                hml = "<div class='form-group assistant'>" + "<label class='col-xs-4 control-label'></label>" + "<div class='col-xs-4'>" + "<div class='radio'>" + "<label>" + "<input type='radio' " + checkOne + " value='" + resultArray[x][0].userId + "' name='assistantId'/>" + resultArray[x][0].userName + "</label>" + "</div>" + "</div>" + partHml + "</div>";
                            }
                            $("#configDiv").append(hml);
                        }
                    } else {
                        $(".assistant").remove();
                        var html = "<div class='form-group assistant'>" + "<label class='col-xs-4 control-label'></label>" + "<div class='col-xs-8'>没有可配置的助理！</div>" + "</div>";
                        $("#configDiv").append(html);
                    }
                },
                false, false);
                $("#configModal").modal("show"); // 显示
            } else if (userType == 'assistant') {
                common.clearForm("configUserForm"); // 清空
                $("#configTitle").html("配置医生");
                $("#configAuthorName").html("助理姓名");
                $("#configUserForm").attr("action", URL.get("Assistant.USER_ADD_DOCTOR"));
                $("#configDoctorName").val($("#userNameSign").val()); // 助理姓名
                $("#assistantId").val($("#userId").val()); // 助理ID
                $(".assistant").remove(); // 清空医生
                var assistantId = "";
                // 查询助理所对应的医生
                var url = URL.get("Assistant.USER_QUERY");
                var params = "assistantId=" + $("#userId").val();
                ajax.post(url, params, dataType.json,
                function(data) {
                    var result = data.data;
                    if (result.length > 0) {
                        var array = [];
                        $.each(result,
                        function(index, assis) {
                            array.push(assis.doctorId);
                        });
                        assistantId = array.join();
                    }
                },
                false, false);
                // 查询医生
                var url = URL.get("User.USER_QUERY");
                var params = "userType=doctorConfig&userId=" + $("#userId").val();
                ajax.post(url, params, dataType.json,
                function(data) {
                    var result = data.data;
                    if (result.length > 0) {
                        $(".assistant").remove();
                        var resultArray = formatArrayData(result);
                        for (var x = 0; x < resultArray.length; x++) {
                            var hml = "";
                            var checkOne = "";
                            var checkTwo = "";
                            var assistantIds = assistantId.split(",");
                            if (assistantIds.includes(resultArray[x][0].userId)) {
                                checkOne = "checked";
                            }
                            if (!common.isEmpty(resultArray[x][1]) && assistantIds.includes(resultArray[x][1].userId)) {
                                checkTwo = "checked";
                            }
                            if (x == 0) {
                                var partHml = "";
                                if (!common.isEmpty(resultArray[x][1])) {
                                    partHml = "<div class='col-xs-4'>" + "<div class='checkbox'>" + "<label>" + "<input type='checkbox' " + checkTwo + " value='" + resultArray[x][1].userId + "' name='doctorId'/>" + resultArray[x][1].userName + "</label>" + "</div>" + "</div>";
                                }
                                hml = "<div class='form-group assistant'>" + "<label class='col-xs-4 control-label'>选择医生</label>" + "<div class='col-xs-4'>" + "<div class='checkbox'>" + "<label>" + "<input type='checkbox' " + checkOne + " value='" + resultArray[x][0].userId + "' name='doctorId'/>" + resultArray[x][0].userName + "</label>" + "</div>" + "</div>" + partHml + "</div>";
                            } else {
                                var partHml = "";
                                if (!common.isEmpty(resultArray[x][1])) {
                                    partHml = "<div class='col-xs-4'>" + "<div class='checkbox'>" + "<label>" + "<input type='checkbox' " + checkTwo + " value='" + resultArray[x][1].userId + "' name='doctorId'/>" + resultArray[x][1].userName + "</label>" + "</div>" + "</div>";
                                }
                                hml = "<div class='form-group assistant'>" + "<label class='col-xs-4 control-label'></label>" + "<div class='col-xs-4'>" + "<div class='checkbox'>" + "<label>" + "<input type='checkbox' " + checkOne + " value='" + resultArray[x][0].userId + "' name='doctorId'/>" + resultArray[x][0].userName + "</label>" + "</div>" + "</div>" + partHml + "</div>";
                            }
                            $("#configDiv").append(hml);
                        }
                    } else { // 没有医生可显示时，
                        $(".assistant").remove();
                        var html = "";
                        html = "<div class='form-group assistant'>" + "<input type='hidden' id='docotrIsNone' value='noneDoctor'/>" + "<label class='col-xs-4 control-label'></label>" + "<div class='col-xs-8'>没有可配置的医生！</div>" + "</div>";
                        $("#configDiv").append(html);
                    }
                },
                false, false);
                $("#configModal").modal("show"); // 显示
            } else {
                layer.msg("只有医师或助理才可以进行配置!");
            };
        }
    });

    //加入必填项提示
    $("#addUserForm").validate(userOptions);
    common.requiredHint("addUserForm", userOptions);
    $("#configUserForm").validate(userAssistantOptions);
    common.requiredHint("configUserForm", userAssistantOptions);
    // 初始化 uploadifive 上传控件
    common.uploadifive("上传头像",
    function(data) {
        $('#userHeadSculpture').val(data);
        $('#showpic').attr("src", data);
    });
});

//重置密码
function pswResetSubmit(userId) {
    layer.confirm("确定要执行【密码重置】操作？",
    function(index) {
        var url = URL.get("User.USER_PSW_RESET");
        var params = "userId=" + userId;
        ajax.post(url, params, dataType.json,
        function(data) {
            layer.alert(data.message);
        });
        layer.close(index);
    });
};