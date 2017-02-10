<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>敏感词管理</title>
<link rel="stylesheet" href="<%=basePath%>/css/sweetalert2.min.css" />
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/resourceTree.css" />
<link rel="stylesheet" href="<%=basePath%>/css/modules/sensitive/sensitive.css" />

<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript"src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/resourceTree.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/sensitive/sensitive.js"></script>
</head>
<body>
<!-- 表头 -->
	<div class="top">敏感词库信息</div>
	<!--拼接内容-->
	 <div class="main">
	 	<table id="main_sernsitive">
	 	<tr class="sernsitive">
	 	
	 	</tr>
	 	</table>
	 	<!--修改-->
	 	<div id="updata" class="updata">
	 		<div>敏感词修改</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">敏感词编号</span>
			<input disabled="true" style="width: 250px" id="r_ID" type="text" class="form-control" placeholder="" aria-describedby="basic-addon1">
	 		</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">敏感词</span>
			<input  style="width: 250px" id="r_Sensitive" type="text" class="form-control" placeholder="" aria-describedby="basic-addon1">
	 		</div><br/>
	 	<div class="styleWhite" align="center" id="centers">
			<button class="btn btn-sm btn-info" id="Suerbtn">确定</button>&nbsp;&nbsp;
			<button class="btn btn-sm btn-info" id="Colsebtn">关闭</button>
		</div>
	 	</div>
	 	<!--增加  -->
	 	<div id="adDate" class="updata">
	 		<div>敏感字增加</div><br/>
			<div class="input-group" id="center">
				<span style="width: 100px" class="input-group-addon">敏感词</span>
				<input style="width: 250px" id="add_Sensitive" type="text" class="form-control" placeholder="" aria-describedby="basic-addon1">
			</div>
			<br />
			<div class="styleWhite" align="center" id="centers">
				<button class="btn btn-sm btn-info" id="add_Suerbtn">确定</button>
				&nbsp;&nbsp;
				<button class="btn btn-sm btn-info" id="add_Colsebtn">关闭</button>
			</div>
		</div>
		<!--分页  -->
		<div class="styleWhite" align="center" id="centers">
			<div class="btn-group" align="center">
				<button id="Accesshomepage" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="Accesslastpage" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="Accesscenextpage" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="Accessendpage" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
			</div>
	 </div>
	 <!--背景  -->
	 <div id="addLinkAddressFade" class="black_overlay"></div>
</body>
</html>