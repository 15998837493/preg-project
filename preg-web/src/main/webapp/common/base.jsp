<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title }</title>

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

    <%-- <!-- icheck插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/icheck/square/square.css" />
    <script charset="UTF-8" src="${common.basepath}/common/plugins/icheck/icheck.js"></script> --%>

    <%-- <!-- 百分比滚动条插件（不常用单独引入） -->
    <link rel="stylesheet" href="${common.basepath}/common/plugins/slider/bootstrap-slider.min.css">
    <script charset="UTF-8" src="${common.basepath}/common/plugins/slider/bootstrap-slider.min.js"></script> --%>

    <!-- 自定义 -->
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/style.css"/>
    <link rel="stylesheet" href="${common.basepath}/common/mnt/css/timeline.css"/>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/ajax-request.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/datatable.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/common.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/pregnancy.js"></script>
    <script charset="UTF-8" src="${common.basepath}/common/mnt/js/pinyin.js"></script>

    <script type="text/javascript">
        // 设置登陆用户信息
        var loginUserData = $.parseJSON('${userinfo}');
        // 基础路径
        var PublicConstant = {none: "none", basePath: "${common.basepath}", websocketPort: "${common.websocketPort}"};
        // 关闭父窗口的遮罩层
        if (!common.isEmpty(window.opener)) {
            window.opener.layer.close(window.opener.index);
        }
    </script>
    <script type="text/javascript" charset="UTF-8" src="${common.basepath}/common/mnt/js/main.js"></script>

    <style>
        body {
            background-color: #eaf1fb;
            padding: 15px;
            min-width: 1280px;
        }

        #guide-page-css {
            padding: 80px 10px 0 15px;
        }

        .list-group-item:first-child {
            border-top-left-radius: 0px;
            border-top-right-radius: 0px;
        }

        .list-group-item:last-child {
            border-bottom-left-radius: 0px;
            border-bottom-right-radius: 0px;
        }

        @media (min-width: 768px) {
            .main {
                padding-right: 40px;
                padding-left: 40px;
            }
        }

        .main .page-header {
            margin-top: 0;
        }

    </style>

</head>
