<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<html>
<head>
<script type="text/javascript" src="jquery-1.8.0.js"></script>
<script type="text/javascript" src="multi.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>高级查询</title>
</head>
<body>
<button id="verificationBtn">验证测试</button>
<button id="loginBtn">登录测试</button>
<button id="loginJsonBtn">登录测试</button>
<button id="indicesBtn">ESMetadata测试</button>
<button id="cookieBtn">Cookie测试</button>
</body>
</html>