<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>孕期营养门诊诊疗系统</title>

    <!-- jquery插件 -->
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/jquery.min.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/jquery.form.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/jquery.loadJSON.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/jquery.validate.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/additional-methods.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/localization/messages_zh.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/validate/validate.regexp.js"></script>

    <!-- bootstrap插件 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/bootstrap3.2.0/css/bootstrap-default.min.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/plugins/bootstrap-button/buttons.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/plugins/bootstrap-select-1.12.4/css/bootstrap-select.min.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/plugins/bootstrap-multiselect/css/bootstrap-multiselect.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/bootstrap3.2.0/js/bootstrap.min.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/bootstrap-datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/bootstrap-select-1.12.4/js/bootstrap-select.min.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/bootstrap-multiselect/js/bootstrap-multiselect.js"></script>

    <!-- dataTables插件 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/datatable/css/dataTables.bootstrap.min.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/datatable/js/jquery.dataTables.min.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/datatable/js/dataTables.bootstrap.min.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/datatable/plugins/sorting/chinese-string.js"></script>

    <!-- font（小图标）插件 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/font-awesome/css/font-awesome.min.css"/>

    <!-- underscore（功能类库）插件 -->
    <script charset="UTF-8" src="${common.basepath}/common/plugins/Underscore/Underscore.js"></script>

    <!-- layer插件 -->
    <script charset="UTF-8" src="${common.basepath}/common/plugins/layer/layer.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/layer/extend/layer.ext.js"></script>

    <!-- metisMenu插件 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/metisMenu/metisMenu.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/metisMenu/metisMenu.min.js"></script>

    <!-- tip插件 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/jquery/qtip/jquery.qtip.min.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/qtip/jquery.qtip.min.js"></script>

    <!-- scrollup插件 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/scrollup/css/themes/image.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/scrollup/js/jquery.scrollUp.min.js"></script>

    <!-- 多级下拉菜单导航 -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/ddlevelsmenu/css/ddlevelsmenu-base.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/plugins/ddlevelsmenu/css/ddlevelsmenu-sidebar.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/plugins/ddlevelsmenu/css/ddlevelsmenu-topbar.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/ddlevelsmenu/js/ddlevelsmenu.js"></script>

    <!-- 新写的多级下拉菜单导航 lp-->
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/levelsmenu.css"/>

    <!-- echarts 3.x -->
    <script charset="UTF-8" src="${common.basepath}/common/plugins/echarts/echarts.min.js"></script>

    <!-- autocomplete插件（自动补全搜索） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/autocomplete/jquery.autocomplete.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/autocomplete/jquery.autocomplete.js"></script>

    <%-- <!-- 标签插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.css">
    <link rel="stylesheet" href="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.css">
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/jquery-ui/jquery-ui.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/plugins/jquery/tag-editor/jquery.tag-editor.js"></script> --%>

    <%-- <!-- kindeditor插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/kindeditor-4.1.11/themes/default/default.css" />
    <link rel="stylesheet" href="${common.basepath}/common/plugins/kindeditor-4.1.11/plugins/code/prettify.css" />
    <script charset="utf-8" src="${common.basepath}/common/plugins/kindeditor-4.1.11/kindeditor-all.js"></script>
    <script charset="utf-8" src="${common.basepath}/common/plugins/kindeditor-4.1.11/lang/zh-CN.js"></script>
    <script charset="utf-8" src="${common.basepath}/common/plugins/kindeditor-4.1.11/plugins/code/prettify.js"></script> --%>

    <%-- <!-- zTree插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeStyle.css" />
    <link rel="stylesheet" href="${common.basepath}/common/plugins/zTree/zTreeMnt.css" /><!-- 自定义 -->
    <script charset="utf-8" src="${common.basepath}/common/plugins/zTree/jquery.ztree.all-3.5.js"></script> --%>

    <%-- <!-- uploadifive插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadifive.css" />
    <link rel="stylesheet" href="${common.basepath}/common/plugins/uploadifive-v1.2.2/uploadButton.css" /><!-- 自定义 -->
    <script charset="utf-8" src="${common.basepath}/common/plugins/uploadifive-v1.2.2/jquery.uploadifive.min.js"></script> --%>

    <%-- <!-- echart图标插件（不常用单独引入） -->
    <script charset="UTF-8" src="${common.basepath}/common/plugins/echarts/echarts-all.js"></script> --%>

    <%-- <!-- laytpl（html模板）（不常用单独引入） -->
    <script charset="UTF-8" src="${common.basepath}/common/plugins/laytpl/laytpl.js"></script> --%>

    <%-- <!-- icheck插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/icheck/square/square.css" />
    <script charset="UTF-8" src="${common.basepath}/common/plugins/icheck/icheck.js"></script> --%>

    <%-- <!-- 百分比滚动条插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/slider/bootstrap-slider.min.css">
    <script charset="UTF-8" src="${common.basepath}/common/plugins/slider/bootstrap-slider.min.js"></script> --%>

    <%-- <!-- ztree自定义样式（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/ztree.css" /> --%>

    <!-- 自定义 -->
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/timeline.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/style.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/ajax-request.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/datatable.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/common.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/pregnancy.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/pinyin.js"></script>

    <script type="text/javascript">
        //设置登陆用户信息
        var loginUserData = $.parseJSON('${userinfo}');
        //基础路径
        var PublicConstant = {none: "none", basePath: "${common.basepath}", websocketPort: "${common.websocketPort}"};
    </script>
    <script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/mnt/js/main.js"></script>
</head>
<body>
<input type="hidden" id="insLogoPath" value="${insLogoPath }"/>

<!-- 顶部工具栏 -->
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="navbar-header">
        <a href="#"> <img class="logo-image" id="mainInsLogo" src="${common.basepath}/common/images/ins_logo.png"/></a>
    </div>
    <div class="navbar-header">
        <a href="#"> <img class="logo-image" id="mainInsName" src="${common.basepath}/common/images/logo/logo.png"/></a>
    </div>
    <ul class="nav navbar-nav navbar-right">
        <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">个人中心 <span class="caret"></span></a>
            <ul class="dropdown-menu">
                <li><a href="#" onclick="myInfo();"><i class="fa fa-user fa-fw"></i> 我的资料</a></li>
                <li class="divider"></li>
                <li><a href="#" onClick="updatePsw()"><i class="fa fa-key fa-fw"></i> 密码修改</a></li>
            </ul>
        </li>
        <li><a onclick="menuClick('${common.basepath}/page/main/version.jsp','关于');"><i class="fa fa-info fa-fw"></i> 关于</a></li>
        <li><a href="#" onclick="logoutEvent('确定要【<font color=red>退出系统</font>】？','');"> <i class="fa fa-sign-out fa-fw fa-lg"></i> 退出
        </a></li>
    </ul>
</nav>

<!-- 左侧菜单栏 -->
<div class="sidebar">
    <div class="sidebar-title">
        <strong><span class="fa fa-sitemap fa-fw"></span> &nbsp;菜单目录</strong>
        <a class="pull-right sidebar-menu"><i class="fa fa-hand-o-left fa-lg"></i></a>
        <span id="sidebar_prompt" style="display: none;">打 开 导 航</span>
    </div>
    <nav class="sidebar-nav">
        <ul class="metismenu" id="menu">
            <c:forEach items="${menuList}" var="modules" varStatus="i">
                <li>
                    <a class="has-arrow" href="#" aria-expanded="true"> <i class="fa ${modules.menuIcon} fa-fw"></i> <span class="sidebar-nav-item">${modules.menuName}</span></a>
                    <ul aria-expanded="false">
                        <c:forEach items="${modules.childList}" var="childModules" varStatus="j">
                            <li><a href="#" onclick="menuClick('${common.basepath}${childModules.menuUrl}','${modules.menuName}_${childModules.menuName}')"> ${childModules.menuName} </a></li>
                        </c:forEach>
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>

<!-- 页眉信息 -->
<div class="tab-header">
    &nbsp;&nbsp;<span class='fa fa-desktop'></span> &nbsp;工作平台
</div>

<!-- 主页面信息 -->
<div class="main-page" id="mainp">
    <div id="content">
        <%@ include file="/page/main/version.jsp" %>
    </div>
</div>

<!-- 页脚信息 -->
<nav class="navbar navbar-default navbar-fixed-bottom" style="min-height:20px;">
    <div class="col-xs-3 text-left">${insInfo.insName }</div>
    <div class="col-xs-9 text-right">
        <i class="fa fa-question-circle fa-fw"></i> 技术支持：北京麦芽健康管理有限公司
    </div>
</nav>

<%@ include file="/common/template/psw_update_model.jsp" %>
<%@ include file="/common/template/myinfo_model.jsp" %>

</body>
</html>
