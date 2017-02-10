<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<style>
BODY { 
	FONT-SIZE: 12px;FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
	WIDTH: 60%; PADDING-LEFT: 25px; 
} 
DIV.digg { 
	PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px;
 	MARGIN: 3px; PADDING-TOP: 3px; TEXT-ALIGN: center 
} 
DIV.digg A { 
	BORDER-RIGHT: #aaaadd 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #aaaadd 1px solid;
 	PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px; 
 	BORDER-LEFT: #aaaadd 1px solid; COLOR: #000099; PADDING-TOP: 2px;
 	BORDER-BOTTOM: #aaaadd 1px solid; TEXT-DECORATION: none 
} 
DIV.digg A:hover { 
 	BORDER-RIGHT: #000099 1px solid; BORDER-TOP: #000099 1px solid;
 	BORDER-LEFT: #000099 1px solid; COLOR: #000; BORDER-BOTTOM: #000099 1px solid 
} 
DIV.digg A:active { 
	BORDER-RIGHT: #000099 1px solid; BORDER-TOP: #000099 1px solid;
 	BORDER-LEFT: #000099 1px solid; COLOR: #000; BORDER-BOTTOM: #000099 1px solid 
} 
DIV.digg SPAN.current { 
	BORDER-RIGHT: #000099 1px solid; PADDING-RIGHT: 5px;
 	BORDER-TOP: #000099 1px solid; PADDING-LEFT: 5px; FONT-WEIGHT: bold;
 	PADDING-BOTTOM: 2px; MARGIN: 2px; BORDER-LEFT: #000099 1px solid;
 	COLOR: #fff; PADDING-TOP: 2px; BORDER-BOTTOM: #000099 1px solid; BACKGROUND-COLOR: #000099 
} 
DIV.digg SPAN.disabled { 
	BORDER-RIGHT: #eee 1px solid; PADDING-RIGHT: 5px; BORDER-TOP: #eee 1px solid;
 	PADDING-LEFT: 5px; PADDING-BOTTOM: 2px; MARGIN: 2px;
 	BORDER-LEFT: #eee 1px solid; COLOR: #ddd; PADDING-TOP: 2px;
 	BORDER-BOTTOM: #eee 1px solid 
} 

</style>
<!-- 
<script type="text/javascript" src="<%=%>/WEB-INF/js/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="<%=%>/js/jquery-3.0.0.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="http://code.jquery.com/jquery-3.1.0.js"></script>
-->
<script type="text/javascript" src="js/jquery-3.0.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	var scrollId = null;
	
	$("#submit").click(function(){
		$("#submit").attr("disabled", true); 
		$("#next").attr("disabled", true); 
		var query = $("#query").val();
		var scrollId = "";
		var rowNumPerPage = 10;
		var url = "search/quick?query=" + query;
		search(url)
	});
	
	function search(url) {
		$.getJSON(url, function(result) {
			$("#results").empty();
			if (result.code == 2) {
				$("#results").append(result.failure)
			} else if (result.code == 1) {
				if (result.data) {
					if (result.data.resultList && result.data.resultList.length > 0) {
						$("#results").append("搜索共" + result.data.totalRowNum + "结果</br></br></br>")
						$.each(result.data.resultList, function(i, item) {
							$("#results").append(JSON.stringify(item));
							$("#results").append("<br/><br/><br/>");
						})
					} else {
						$("#results").append("未找到相关数据")
					}
				} else {
					$("#results").append("未找到相关数据")
				}
			}
			$("#submit").attr("disabled", false); 
			/*
			$("#next").attr("disabled", false); 
			*/
		});
	}
	
});

</script>
<body>
<h1 align="center">ESQuery</h1>
<div align="center">
<span style="font-size:15">关键字：</span><input id="query" type="text"/>
<input id="submit" type="submit" value="搜索"/>
</div>
<div id="results" align="center">
</div>
</body>
</html>
