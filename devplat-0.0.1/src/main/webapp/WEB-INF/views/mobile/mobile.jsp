<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
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
</body>
</html>
