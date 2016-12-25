<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p>提示：至少有一项不能为空</p>
	<p><input id="idCard" type="text"  style="width: 220px;" placeholder="请输入身份证号码"/></p>
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入手机号码"/></p>
	<p><input id="bankCard" type="text"  style="width: 220px;" placeholder="请输入银行卡号"/></p>
	<p><input id="name" type="text"  style="width: 220px;" placeholder="请输入姓名"/></p>
	<p><input id="sumbitthree" type="button" value="确定"/></p>
</div>
<div id="blackListCheats" align="center" style="top: 30%;position: absolute;">
</div>
</body>
</html>
