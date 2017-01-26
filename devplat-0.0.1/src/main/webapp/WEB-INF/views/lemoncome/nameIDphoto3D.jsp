<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
img {
  height: 250px;
}
</style>
</head>
<body>
	<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="demo_input" type="file"  style="width: 220px;" placeholder="请选择图片"/></p>
	 <textarea style="text-align: center;" id="result" rows=5 cols=60></textarea>
	<p  align="center" id="img_area">
	</p>
	<p><input id="query" type="text"  style="width: 220px;" placeholder="请输入身份证号码"/></p>
	<p><input id="name" type="text"  style="width: 220px;" placeholder="请输入姓名"/></p>
	<p><input id="lemonSubmitsy" type="button" value="确定"/></p>
</div>

<div id="result3D" align="center">
</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
</body>
</html>