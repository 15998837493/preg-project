<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
	<style>
	    input {
            width: 100%;
            }
	</style>
	<!-- 此处引用未匹配到本项目中下列引用文件的路径 -->
    <link rel="stylesheet" type="text/css"  href="test/jquery-ui.css">
	<link rel="stylesheet" type="text/css"  href="test/jquery.tag-editor.css">
	<script type="text/javascript" charset="utf8" src="DataTables-1.10.13/media/js/jquery.js"></script>
	<script type="text/javascript"  src="test/jquery.tag-editor.js"></script>
	<script type="text/javascript"  src="test/jquery-ui.js"></script>
    <script type="text/javascript" >
        $(function() {
            $('#demo1').tagEditor({
                 initialTags: ['Hello', 'World', 'Example', 'Tags'], 
                 delimiter: ', ',
                 placeholder: 'Enter tags ...' }).css('display', 'block').attr('readonly', true);
			$("#but").click(function(){
				 	alert($("#demo1").val())
				});

			 
            $('#demo2').tagEditor({
            	initialTags: ['Hello', 'World', 'Example', 'Tags'],//初始化标签
            	delimiter: ', ',//标签分隔符号
                autocomplete: { delay: 0,//自动补全配置
                     position: { collision: 'flip' }, 
                     source: ['ActionScript', 'AppleScript'] },//自动补全数据源
                forceLowercase: false,
                placeholder: 'Programming languages ...',//无标签时提供的提示信息
                onChange: function(){},//回调
                beforeTagSave: function(){},////回调
                beforeTagDelete: function(){}////回调
            }).css('display', 'block').attr('readonly', true);//将#demo2设置成显示（默认是隐藏），并禁用。
            
            //设置指定区域点击添加标签
			$("#add").click(function(){
				 $('#demo2').tagEditor('addTag', 'example');
				});
            });
		
    </script> 
  </head>
  <body>
     <input id="demo1" name="elment" type="hidden"/>
     <button id="but">button</button>
     <input id="demo2"/>
     <button id="add">addButton</button>
  </body>
</html>
