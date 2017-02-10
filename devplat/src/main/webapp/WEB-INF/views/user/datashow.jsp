<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/index/index.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/log/log.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/header/header.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/phoneuser/phoneuser.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/qq/qq.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/statistics/statistics.css"/>


<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/user/showdatas.js"></script>
<head>
	<title>Internal</title>
	<style type="text/css">
	#top {
		width: 100%;
		hight: 3%;
    	position: fixed; 
    	top: 5%;
    	overflow: visible;
	}
	
	#bottom {
		width: 100%;
		hight: 3%;
    	position: fixed;
    	bottom: 3%;
    	font: 900;
	}
	
	#resultShow {
		width: 96%;
		height: 80%;	
		top: 20%;
		left: 2%;
		position: absolute;
		overflow: scroll; 
	}
	
		
	</style>
</head>
	<body>
	<div id="top">
		<h1 align="center">Data Show</h1>
	</div>
		<h1 align="center">&nbsp;</h1>
		<div id="resultShow">
		
		</div>
		<div id="bottom" align="center">
			<h3>友情链接:
				<a href="<%=basePath %>/excludegroup/">Group</a>
			</h3>
		</div>
	</body>
</html>