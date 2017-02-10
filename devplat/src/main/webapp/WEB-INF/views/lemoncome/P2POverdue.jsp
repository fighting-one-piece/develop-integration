<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="idCard" type="text"  style="width: 220px;" placeholder="请输入身份证号"/></p>
	<p><input id="name" type="text"  style="width: 220px;" placeholder="请输入名字"/></p>
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入手机号"/></p>
	<p><input id="p2p" type="button" value="确定"/></p>
</div>
<div id="resultP2P" align="center" style="top: 30%;position: absolute;">

</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
</body>
</html>
