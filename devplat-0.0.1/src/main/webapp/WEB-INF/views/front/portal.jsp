<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<body>
<a id="anchor"></a>
<h5 align="center">&nbsp;</h5>
<div align="center">
<!-- <span style="font-size:15">关键字：</span> -->
<input id="query" type="text" style="width: 220px"  
 onkeydown="EnterPressIndex();" placeholder="请输入手机号码或者身份证号码"/>
<input id="submitIndex" type="submit" value="搜索"/>
<a href="<%=basePath %>/search/multi" style="font-size: 15px; margin-left: 5px; font-weight: bold;">高级搜索</a>
<a id="poiExpExcel" style="margin-left: 5px;  font-size: 15px; font-weight: bold;">导出EXCEL</a>
<br/><br/>
</div>
<!-- 显示ES数据 -->
<label class="styleTitleOne" style="background:gray;">数据源一</label>
<div id="resultsIndex" align="left" style="margin-bottom: 3%;">	
</div>					
<div id="resultsLable" align="center"></div>		
<!-- 显示DATATA数据 -->
<label class="styleTitleOne" style="background:gray;">数据源二</label>
<div id="resultsDatada" align="left">	
</div>
<div id="buttonArea" align="center">
<a href="#anchor"><input id="nextIndex" style="display: none;" value="下一页" type="button"/></a>

</div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
<script type="text/javascript" src="<%=basePath %>/js/modules/index/index.js"></script>
</body>
</html>
