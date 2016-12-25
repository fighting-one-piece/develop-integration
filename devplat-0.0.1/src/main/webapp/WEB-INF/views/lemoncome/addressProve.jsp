<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="idCard" type="text"  style="width: 220px;" placeholder="请输入身份证号Address"/></p>
	<p><input id="name" type="text"  style="width: 220px;" placeholder="请输入名字"/></p>
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入手机号"/></p>
	<p><label style="padding-right: 65px;">证件类型:</label>
		<select style="width: 90px;" id="idType">
		  <option value="1">居民身份证</option>
		  <option value="2">港澳居民往来内地通行证</option>
		  <option value="3">台胞证</option>
		  <option value="4">护 照</option>
		  <option value="5">军官证</option>
		  <option value="6">其他证件</option>
		</select>
	</p>
	<p style="color: red">提示:Home和company两类,必须有一类填写</p>
	<p><input id="homeCity" type="text"  style="width: 220px;" placeholder="请输入家庭所在城市"/></p>
	<p><input id="homeAddress" type="text"  style="width: 220px;" placeholder="请输入家庭地址"/></p>
	<p><input id="companyCity" type="text"  style="width: 220px;" placeholder="请输入公司所在地址"/></p>
	<p><input id="companyAddress" type="text"  style="width: 220px;" placeholder="请输入公司地址"/></p>
	<p><input id="address" type="button" value="确定"/></p>
</div>
<div id="resultAddress" align="center" style="top: 30%;position: absolute;">

</div>
</body>
</html>
