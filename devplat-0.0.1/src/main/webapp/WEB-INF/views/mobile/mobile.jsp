<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
	<title>手机姓名归属地查询</title>
</head>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
<input id="query"   type="text" style="width: 220px" 
onkeydown="EnterPressMobile()" placeholder="请输入手机号码" />
<input id="submits" type="submit" value="搜索" />
<div id="error">
			
  </div>
</div>
<div id="results" align="center">
	
</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
	<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
</div>
<script type="text/javascript" src="<%=basePath %>/js/modules/mobile/mobile.js"></script>
</body>
</html>
