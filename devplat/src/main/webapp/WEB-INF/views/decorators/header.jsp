<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
 	
 	response.setHeader("Cache-Control", "no-cache"); 
 	response.setHeader("Cache-Control", "no-store"); 
 	response.setHeader("Pragma", "no-cache"); 
 	response.setDateHeader("Expires", 0); 
%>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/header/header.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/index/index.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/phoneuser/phoneuser.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/qq/qq.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/user/data_analysis.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/fileinput.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/fileinput.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/header/backgroundcolor.css"/>

<%-- <link rel="stylesheet" href="<%=basePath %>/css/modules/log/log.css"/> --%>
<%-- <script type="text/javascript" src="<%=basePath %>/js/modules/log/log.js"></script> --%>

<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/laydate.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/fileinput.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/fileinput_locale_zh.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/menu/menu.js"></script>

<%-- <script type="text/javascript" src="<%=basePath %>/js/modules/lemon/fb_lemon.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/NameIdcheck.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/NameIDCardBankcard.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/NamePhoneID.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/NameIdPhoneBank.js"></script> --%>
<script type="text/javascript" src="<%=basePath %>/js/modules/user/updatepassword.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/NameIDPhoto3D.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/LMall.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/LMTwice.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/lemon/LMThird.js"></script>

<script type="text/javascript">
	//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外 
	function banBackSpace(e){ 
		var ev = e || window.event;//获取event对象 
		var obj = ev.target || ev.srcElement;//获取事件源 
	
		var t = obj.type || obj.getAttribute('type');//获取事件源类型 
	
		//获取作为判断条件的事件类型 
		var vReadOnly = obj.getAttribute('readonly'); 
		var vEnabled = obj.getAttribute('enabled'); 
		//处理null值情况 
		vReadOnly = (vReadOnly == null) ? false : vReadOnly; 
		vEnabled = (vEnabled == null) ? true : vEnabled; 
	
		//当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
		//并且readonly属性为true或enabled属性为false的，则退格键失效 
		var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") 
			&& (vReadOnly==true || vEnabled!=true))?true:false; 
	
		//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
		var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") ? true : false; 
	
		//判断 
		if(flag2){ 
		return false; 
		} 
		if(flag1){ 
		return false; 
		} 
	} 
	
	//禁止后退键 作用于Firefox、Opera 
	document.onkeypress=banBackSpace; 
	//禁止后退键 作用于IE、Chrome 
	document.onkeydown=banBackSpace; 
	
	$(function(){
		$("#btnlogout").click(function(){
			var pathName=window.document.location.pathname;
			var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
			location.href = projectName+"/logout"
		});
		$("#newpassword").click(function() {
			showDIV();
		});
	});
</script>

<div align="center" id="headerpage">
	<hr style="margin-bottom: 0.25%; margin-top: 1%"/>
	<div class="nav" align="center" style="margin-top: 5px;margin-bottom: 0px;">
	<a id="title" href="<%=basePath %>/portal" style="color: black; text-decoration: none;font-size: 30px;" >
	CisionData
	<!-- 获取用户名 -->
	</a>
		<div class="ce"  style="margin-top: 25px;margin-right: 0;">
			<strong>当前用户：</strong><i class="username"><shiro:principal/></i>
			<a id="updatepassword" href="<%=basePath %>/user/updatepassword">修改密码</a>
			<a id="btnlogout" href="#">退出系统</a>
		</div>
	</div>
	<hr style="margin-bottom: 5px;margin-top: 5px;"/>
</div>

