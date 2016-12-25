<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/bootstrap.css" type="text/css"/>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery-1.8.0.js"></script>
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
					<input id="query" type="text" class="form-control" style="width: 220px"  onkeydown="EnterPressCard()" placeholder="请输入身份证号码"/>
					<span class="input-group-btn">
						<button class="btn btn-default" type="button" id="submitCard">Go!</button>
					</span>
				</div>
			<div id="results" align="center" style="margin-left: 20px;">
				
			</div>
			
		</div>
	</div>
</body>
</html>