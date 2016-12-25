<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
<sitemesh:write property='title' /> - CisionData
</title>
<sitemesh:write property='head' />
</head>
<script type="text/javascript">
</script>
<body>
	<div id="header">
	 <jsp:include flush="true" page="header.jsp"></jsp:include>
	 </div>
     <br/>
     <div id="menu">
     <jsp:include flush="true" page="menu.jsp"></jsp:include>
     </div>
     <div id="body">
     	<sitemesh:write property='body' />
     </div>
     <div id="footer">
     <jsp:include flush="true" page="footer.jsp"></jsp:include>
     </div>
</body>
</html>