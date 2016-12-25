<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="query" type="text"  style="width: 220px;" placeholder="请输入身份证号码"/></p>
	<p><input id="name" type="text"  style="width: 220px;" placeholder="请输入姓名"/></p>
	<p><input id="levelNo" type="text"  style="width: 220px;" placeholder="请输入编号"/></p>
	<p><input id="lemonSubmit" type="button" value="确定"/></p>
</div>
<div id="resultVerifyEeducation" align="center" style="top: 30%;position: absolute;">

</div>
</body>
</html>
