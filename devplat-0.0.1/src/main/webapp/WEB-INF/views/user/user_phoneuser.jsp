<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>手机追踪 </title>
</head>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
<input id="query"   type="text"  onkeydown="EnterPressPhoneuser()" style="width: 220px" placeholder="输入手机号码"/>
<input id="submitss" type="submit" value="搜索" />
<div id="error">
			
  </div>
</div>
<div id="resultss" align="center">
	
</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
<script type="text/javascript" src="<%=basePath %>/js/modules/phoneuser/phoneuser.js"></script>
</body>
</html>
