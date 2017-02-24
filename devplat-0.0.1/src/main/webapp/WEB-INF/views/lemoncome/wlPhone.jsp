<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
	<title>金融用户画像</title>
</head>
<script type="text/javascript"
	src="<%=basePath%>/js/modules/lemon/LMTwice.js"></script>

<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="wlphone" type="text"  style="width: 220px;" placeholder="请输入手机号"/></p>
	<p><input id="SubmitMobleOne" type="button" value="确定"/></p>
</div>
<div id="resultWlPhone" align="center" >
</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
</body>
</html>
