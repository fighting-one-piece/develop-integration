<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css"/>
<script type="text/javascript" src="js/bootstrap.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>身份证查询</title>
<style type="text/css">
	.styleBtn{
		position: absolute;
		right: 50px;
		top:10px;
	}
	.fonStyle{
		font-size: 25px;
	}
</style>
</head>
<body>
	<div>
		<div class="row" align="center" style="margin-top: 20px;">
			<br/>
				<div class="input-group" style="width:240px;" >
					<input id="queryname" type="text" class="form-control" style="width: 220px"  onkeydown="EnterPressCard()" placeholder="请输入姓名"/>
					<span  style="padding-right: 5px;"></span>
					<input id="query" type="text" class="form-control" style="width: 220px"  onkeydown="EnterPressCard()" placeholder="请输入身份证号码"/>
					<span class="input-group-btn">
						<button class="btn btn-default" type="button" id="submitCard">搜索</button>
					</span>
				</div>
			<div id="results" align="center" style="margin-left: 20px;">
				
			</div>
			
		</div>
	</div>
	<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
	<script type="text/javascript" src="<%=basePath %>/js/modules/user/userIdCard.js"></script>
</body>
</html>