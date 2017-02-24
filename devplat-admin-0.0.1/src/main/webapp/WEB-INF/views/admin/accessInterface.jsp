<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<link rel="stylesheet" href="<%=basePath%>/css/sweetalert2.min.css" />
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet"
	href="<%=basePath%>/css/modules/admin/datainterface/accessInterface.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/laydate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/modules/admin/datainterface/accessInterface.js"></script>
</head>
<body>
<div id="accessInterface">
	<div class="styleWhite" align="center">
		<button class="btn btn-sm btn-info" id="toAddAccessInterfaceBtn"
			style="margin-bottom: 20px; margin-top: 20px">添加接口</button>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<table class="table table-striped table-bordered"
				id="resultAccessInterfaceTb">
				<thead class="styleThead">
					<tr>
						<td>ID</td>
						<td>接口地址</td>
						<td>接口标识</td>
						<td>单价</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="allAccessInterfaceTable" align="center">

				</tbody>
			</table>
		</div>
		
		<div class="row" style="text-align: center;" id="resultAccessPage">
			<div class="btn-group" id="button">
				<button id="AccessInterfacehomepage" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="AccessInterfacelastpage" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="AccessInterfacenextpage" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="AccessInterfaceendpage" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
		</div>
	</div>
	<div id="addAccessInterfaceDiv" class="link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeAddAccessInterfaceDivBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle">
				<tr>
					<td>接口地址</td>
					<td><input class="form-control" id="interfaceAddress"></td>
				</tr>
				<tr>
					<td>接口标识</td>
					<td><input class="form-control" id="interfaceIdentity"></td>
				</tr>
				<tr>
					<td>单价</td>
					<td><input class="form-control" id="interfaceMoney"></td>
				</tr>
			</tbody>
		</table>
		<div align="center" style="min-height: 45%;"></div>
		<button class="btn btn-sm btn-info pull-right"
			id="submitAddAccessInterfaceDivBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
	<div id="updateAccessInterfaceDiv" class="link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeUpdateAccessInterfaceDivBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle">
				<tr>
					<td>接口地址</td>
					<td><input class="form-control" id="updateinterfaceAddress"></td>
				</tr>
				<tr>
					<td>接口标识</td>
					<td><input class="form-control" id="updateinterfaceIdentity"></td>
				</tr>
				<tr>
					<td>单价</td>
					<td><input class="form-control" id="updateinterfaceMoney"></td>
				</tr>
			</tbody>
		</table>
		<div align="center" style="min-height: 45%;"></div>
		<button class="btn btn-sm btn-info pull-right"
			id="submitupdateAccessInterfaceDivBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
	
</div>
<div id="addAccessInterfaceFade" class="black_overlay"></div>
</body>
</html>
