<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<body>
<h5 align="center">&nbsp;</h5>
<div align="center">
<!-- <span style="font-size:15">关键字：</span> -->
<input id="query" type="text" onkeydown="EnterPressDatada();" style="width: 220px"  placeholder="请输入手机号码或者身份证号码"/>
<input id="submitDatada" type="submit" value="搜索"/>
</div>
<div id="resultsDatada" align="center">
</div>
<div id="buttonArea" align="center">
<input id="nextDatada" style="display: none;" value="下一页" type="button"/>
</div>

<!-- 
<div class="digg"> 
<span class="disabled"> &lt; </span>
<span class="current">1</span>
<span id="pv"></span>
<a href="#?page=2"> &gt; </a></div> 
-->
</body>
</html>
