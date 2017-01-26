<!DOCTYPE HTML>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/index/index.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/log/log.css"/>

<script type="text/javascript" src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/laydate.js"></script>
<head>
<meta charset="utf-8">
</head>
<div  align="center">
		<h2 align="center">密码修改</h2>
		<table style="width: 20%;">
			<tr align="center">
				<td style="background: #EEE8AA;">原密码</td>
				<td><input type="password" style="width: 100%" id="originalpassword" placeholder="请输入原密码"/></td>
			</tr>	
			<tr align="center">
				<td style="background: #EEE8AA;">新密码</td>
				<td><input type="password" style="width: 100%" id="setnewpassword" placeholder="密码为6至16位的字母及数字组成"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">确认密码</td>
				<td><input type="password" style="width: 100%" id="confirmnewpassword" placeholder="请输入确认密码"/></td>
			</tr>
		</table>
		<p><span id="waring" style="color: red;">请牢记新密码！</span></p>
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="confirmbtn">确定</button>
			<button class="btn btn-sm btn-info" id="cancelbtn">取消</button>
		</div>
</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
</html>
