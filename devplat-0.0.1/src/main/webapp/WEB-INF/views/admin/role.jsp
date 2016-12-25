<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>角色管理</title>
<link rel="stylesheet" href="<%=basePath%>/css/sweetalert2.min.css" />
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/Role/role.css" />
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/resourceTree.css" />

<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript"src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/resourceTree.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/Role/Roles.js"></script>


</head>
<body>
<div id="whole">
		<h1 id="role">角色信息</h1>
		<input type="button" value="新增会员" id="butn"/>
		<p></p>
		<table id="role_result_table">			
			<tr class="result">
			</tr>
		</table>
	<!-- 账号修改框  初始隐藏 -->
	<div id="ss" class="role_result_updata">
		<div  class="input-group" id="centers">
		<span style="width: 100px" class="input-group-addon">姓名</span>
		<input style="width: 250px" id="role_name" type="text" class="form-control" placeholder="姓名" aria-describedby="basic-addon1">
		</div><br/>
		<div class="input-group" id="centers">
		<span style="width: 100px" class="input-group-addon">标识</span>
		 <input style="width: 250px" id="role_desc" type="text" class="form-control" placeholder="标识" aria-describedby="basic-addon1">
		</div><br/>
		<div class="input-group" id="centers">
		<span style="width: 100px" class="input-group-addon">角色</span>
		 <input style="width: 250px" id="role_roles" type="text" class="form-control" placeholder="角色" aria-describedby="basic-addon1">
		</div><br/>
		<div class="styleWhite" align="center" id="centers">
			<button class="btn btn-sm btn-info" id="Suerbtn">確定</button>&nbsp;&nbsp;
			<button class="btn btn-sm btn-info" id="Colsebtn">关闭</button>
		</div>
	</div>
	<!-- 删除框  初始隐藏 -->
	<!-- 新增用户，初始隐藏  -->
</div>
</body>
</html>