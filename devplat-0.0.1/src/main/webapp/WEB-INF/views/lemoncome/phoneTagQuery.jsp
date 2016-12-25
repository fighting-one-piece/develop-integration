phoneTagQuery<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入手机号Phone"/></p>
	<p><input id="phoneQuery" type="button" value="确定"/></p>
</div>
<div id="resultTagQuery" align="center" style="top: 30%;position: absolute;">

</div>
</body>
</html>
