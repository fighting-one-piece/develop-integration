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
<link rel="stylesheet" href="<%=basePath %>/css/modules/header/header.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/phoneuser/phoneuser.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/qq/qq.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/statistics/statistics.css"/>


<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/user/group.js"></script>


<head>
	<title>Internal</title>
	<style type="text/css">
	#tops {
		width: 100%;
		hight: 3%;
    	position: fixed; 
    	top: 5%;
    	overflow: visible;
	}
	
	#bottoms {
		width: 100%;
		hight: 3%;
    	position: fixed;
    	bottom: 3%;
    	font: 900;
	}
	
	#resultsChoose {
		width: 98%;
		height: 80%;
		top: 20%;
		left: 2%;
		position: absolute;
		overflow: scroll; 
	}
	</style>
</head>
<body>
<h1 align="center">Group</h1>
<div align="center" id="tops">
<h1 align="center">&nbsp;</h1>
<!-- <span style="font-size:15">关键字：</span> -->
<input id="query" type="text" style="width: 120px"  
 onkeydown="EnterPressChoose();" placeholder="数据总条数"/>
 <input id="submitChoose" type="submit" value="确定"/>
<br/><br/>
</div>
<div id="resultsChoose" align="left" style="font-size: medium; color: red; margin-left: 45%;">
</div>
<div id="bottoms" align="center">
			<h3>友情链接:
				<a href="<%=basePath %>/excludeUtils">Data Show</a>
			</h3>
</div>
</body>
</html>
