<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<a id="anchor"></a>
<h5 align="center">&nbsp;</h5>
<div align="center">
<h1>未授权访问</h1>
${error}
${exception}
</div>
</body>
</html>
