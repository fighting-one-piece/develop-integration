<!DOCTYPE HTML>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="<%=basePath%>/css/modules/admin/H-ui.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>/css/modules/admin/H-ui.admin.css"
	rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/modules/admin/font-awesome.min.css"
	type="text/css" rel="stylesheet" />
<title>Cisiondata-管理界面</title>
</head>
<body style="overflow: hidden">
	<header class="Hui-header cl">
		<a class="Hui-logo l" title="后台管理" href="#">Cisiondata-后台管理</a> <a
			class="Hui-logo-m l" href="#" title="管理">管理</a> <span
			class="Hui-userbox"><span class="c-white">管理员：<i><shiro:principal/></i></span> <a
			class="btn btn-danger radius ml-10" href="#" title="退出" id="logoutBtn"><i
				class="icon-off"></i> 退出</a></span> <a aria-hidden="false"
			class="Hui-nav-toggle" id="nav-toggle" href="#"></a>
	</header>
	<div class="cl Hui-main">
		<aside class="Hui-aside" style="">
			<input runat="server" id="divScrollValue" type="hidden" value="" />
			<div class="menu_dropdown bk_2" id="adminMenuDiv">
				
			</div>
		</aside>
		<div class="dislpayArrow">
			<a class="pngfix" href="javascript:void(0);"></a>
		</div>

		<section class="Hui-article">
			<div id="Hui-tabNav" class="Hui-tabNav">
				<div class="Hui-tabNav-wp">
					<ul id="min_title_list" class="acrossTab cl">
						<li class="active"><span title="我的桌面" data-href="#">我的桌面</span><em></em></li>
					</ul>
				</div>
				<div class="Hui-tabNav-more btn-group">
					<a id="js-tabNav-prev" class="btn radius btn-default btn-small"
						href="javascript:;"><i class="icon-step-backward"></i></a><a
						id="js-tabNav-next" class="btn radius btn-default btn-small"
						href="javascript:;"><i class="icon-step-forward"></i></a>
				</div>
			</div>
			<div id="iframe_box" class="Hui-articlebox">
				<div class="show_iframe">
					<div style="display: none" class="loading"></div>
					<!-- 在此处填主页路径 -->
					<iframe scrolling="yes" frameborder="0" src=""></iframe>
				</div>
			</div>
		</section>
	</div>
	<script type="text/javascript" src="<%=basePath%>/js/jquery.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/js/modules/admin/H-ui.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/js/modules/admin/H-ui.admin.js"></script>
</body>
</html>
