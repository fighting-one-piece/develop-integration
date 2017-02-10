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
		<!-- //<input type="button" value="账号赋予角色" id="butn"/> -->
		<input type="button" value="新增角色" class="btn btn-sm btn-info" id="butns"/>
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
	<!-- 用户赋予角色，初始隐藏  -->
	 <div id="UserPresentation" class="User_Peresentation">
			 <h4 id="role">選擇用戶添加角色</h4>
	 		<table id="User_Peresentation_esentation">
	 			<tr class="User">
	 			</tr>
	 		</table>
	 	<div class="styleWhite" align="center" id="centers">
		<button class="btn btn-sm btn-info" id="Suerbtun">確定</button>&nbsp;&nbsp;
		<button class="btn btn-sm btn-info" id="Colsebtun">关闭</button>
		</div>
	 </div>
	 <!--下拉框  弹出 -->
	 <div id="UserRole"  class="role_result_updata">
	 			<h3 id="role">选择角色</h3>
	 			<div style="text-align: center;">
		 			<select  id="user_role" style="width: 200px;height: 30px">
		 			
		 			</select>
	 			</div><br>
	 	 <div id="centers" class="styleWhite" align="center">
	 	<button class="btn btn-sm btn-info" id="dowunsuer">確定</button>&nbsp;&nbsp;
	 	<button class="btn btn-sm btn-info" id="downbtun">关闭</button>
	 	 </div>
	 </div>
	 <!--新增用户  -->
	 <div id="added_user" class="role_result_updata">
	 	<div  class="input-group" id="centers">
		<span style="width: 100px" class="input-group-addon">姓名</span>
		<input style="width: 250px" id="added_name" type="text" class="form-control" placeholder="姓名" aria-describedby="basic-addon1">
		</div><br/>	
	 	 <div class="input-group" id="centers">
	 	 <span style="width: 100px" class="input-group-addon">标识</span>
		 <input style="width: 250px" id="added_desc" type="text" class="form-control" placeholder="标识" aria-describedby="basic-addon1">
	 	 </div><br/>
	 	 <div class="input-group" id="centers">
	 	 <span style="width: 100px" class="input-group-addon">角色
	 	 </span>
		 <input style="width: 250px" id="added_roles" type="text" class="form-control" placeholder="角色" aria-describedby="basic-addon1">
	 	 </div><br/> 
	 	 <div class="styleWhite" align="center" id="centers">
			<button class="btn btn-sm btn-info" id="addSuerbtn">確定</button>&nbsp;&nbsp;
			<button class="btn btn-sm btn-info" id="addColsebtn">关闭</button>
		</div>
	 </div>
</div>
<!-- 点击进入群详情页面后的背景 -->
	<div id="addLinkAddressFade" class="black_overlay"></div>
</body>
</html>