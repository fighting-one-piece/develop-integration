<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<title>手机身份证分类查询</title>
</head>
<body>
 <h5 align="center">&nbsp;</h5>
<div align="center">
<input id="query" placeholder="请输入手机号码或者身份证" style="width: 220px"  type="text"  onkeydown="EnterPressPhoneIdCard();"/>
<input id="submitt" type="submit" value="搜索" />
<div id="error">	
  </div>
</div>
<div id="topResults" align="center">
</div>
<div id="label" align="center">
	
</div>
<div id="bottomResults" align="center"></div>
<div id="index_inde"></div>
<table class="menu">
	<tr>
		
	</tr>
    </table>
    <!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
    <script type="text/javascript" src="<%=basePath %>/js/modules/phoneidcard/phoneidcard.js"></script>
</body>
</html>