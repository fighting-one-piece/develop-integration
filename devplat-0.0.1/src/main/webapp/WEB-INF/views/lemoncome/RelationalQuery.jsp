<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
<title>查询</title>
<head>
<body>
	<div align="center" style="font-size: 16px;">
		 <br /> 
		 <br /> 
		 <label class="radio-inline"> 
		 	<input name="searchType"type="radio" value="QTofriend" checked="checked" />QQ找朋友网
		 </label> 
		 <label class="radio-inline" style="margin-left: 35px;"> 
		 	<input name="searchType" type="radio" value="friendToQ" />朋友网找QQ
		 </label> 
		 <label class="radio-inline" style="margin-left: 35px;"> 
		 <input name="searchType" type="radio" value="WeiboToQ" />微博url找QQ
		 </label> 
		 <label class="radio-inline" style="margin-left: 35px;"> 
			 <input name="searchType" type="radio" value="QToWeibo" />QQ反找微博
	  	 </label>
		 <label class="radio-inline" style="margin-left: 35px;">
		 	<input name="searchType" type="radio" value="QQTemSession" />对查找qq发起临时会话
		 </label> 
		 <label class="radio-inline" style="margin-left: 35px;"> 
		 	<input name="searchType" type="radio" value="LatetSay" />qq查找最后说说
		 </label> 
		 <label class="radio-inline" style="margin-left: 35px;"> 
		 	<input name="searchType" type="radio" value="PhoneToQ" />手机号找QQ
		 </label>
		 <br />
		 <br />
		 <br />
		 <br /> 
			 <div>查询内容:<input type="text" name="searchContent" placeholder="请输内容不能有空格" /> 
				<input id="Relational" type="button" value="提交"/>
			 </div>
		<br />
		<br />
			<div style="width: 322px;">
				<p id="pvalue" style="font-size: 16px;float: left;" align="center">返回结果:</p>
			</div>
	</div>
	
</body>
</html>
