
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
</body>
</html>
