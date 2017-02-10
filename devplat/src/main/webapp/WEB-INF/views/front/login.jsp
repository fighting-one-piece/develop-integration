<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/login/login.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/validationEngine.jquery.css">
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/login/login.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-validationEngine.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-validationEngine-zh_CN.js"></script>
<script type="text/javascript">
	window.history.forward(1);
</script>	
	
<meta charset="utf-8"/>
<meta http-equiv="Cache-Control" content="no-cache,no-store,must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<title></title>
</head>
<body style="background: #0ca3d2;">
	<section class="container" style="position: absolute;top: 30%;left: 30%;">
    <div class="login">
      <h1>CisionData</h1>
      <form id="loginForm" method="post">
      <div id="nav">
        <p><input id="username" type="text" name="username" class="input-medium validate[required]" value="" placeholder="账号" onkeydown="EnterPressLogin();"></p>
        <p><input id="password" type="password" name="password" class="input-medium validate[required]" value="" placeholder="密码" onkeydown="EnterPressLogin();"></p>
        <p><input type="text" id="jcaptchaCode" name="jcaptchaCode" class="input-medium validate[required,ajax[ajaxJcaptchaCall]]" placeholder="验证码">
        	<img class="jcaptcha-btn jcaptcha-img" style="margin-left: 10px;" src="/devplat/jcaptcha.jpg" title="点击更换验证码">
            <a class="jcaptcha-btn btn btn-link">换一张</a>
        </p>
        <p align="center">
        ${error}
        ${message}
        </p>
        <p align="center">
        <span id="warning" style="position: absolute;left: 10%;"></span>
        <input id="submitLogin" type="submit" name="commit" value="登录">
        </p>
      </div>
      </form>
    </div>
  	</section>
</body>
</html>
