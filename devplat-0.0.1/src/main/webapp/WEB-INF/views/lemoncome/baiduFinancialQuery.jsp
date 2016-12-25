<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
	<p><input id="phone" type="text"  style="width: 220px;" placeholder="请输入手机号baidu"/></p>
	<p><input id="name" type="text"  style="width: 220px;" placeholder="请输入名字"/></p>
	<p><input id="idCard" type="text"  style="width: 220px;" placeholder="请输入身份证号"/></p>
	<p><label style="padding-right: 65px;">婚姻状况:</label>
		<select style="width: 90px;" id="marriage">
		  <option value="unmarried">未 婚</option>
		  <option value="married">已 婚</option>
		  <option value="other">其 他</option>
		</select>
	</p>
	<p><label  style="padding-right: 65px;">学历信息:</label>
	<select style="width: 90px;" id="education">
	  <option value="0">文 盲</option>
	  <option value="1">小 学</option>
	  <option value="2">中 学</option>
	  <option value="3">高 中</option>
	  <option value="4">大 专</option>
	  <option value="5">本 科</option>
	  <option value="6">硕 士</option>
	  <option value="7">博 士</option>
	</select>
	</p>
	<p><input id="homeAddress" type="text"  style="width: 220px;" placeholder="请输入家庭地址"/></p>
	<p><input id="companyAddress" type="text"  style="width: 220px;" placeholder="请输入单位地址"/></p>
	<p><input id="baiduQuery" type="button" value="确定"/></p>
</div>
<div id="resultBaiduQuery" align="center" style="top: 30%;position: absolute;">

</div>
</body>
</html>
