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
	href="<%=basePath%>/css/modules/admin/datainterface/accessControl.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/laydate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/modules/admin/datainterface/accessControl.js"></script>
</head>
<body>
<div id="accessUser">
	<div class="styleWhite" align="center">
		<button class="btn btn-sm btn-info" id="toAddAccessBtn"
			style="margin-bottom: 20px; margin-top: 20px">添加用户</button>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<table class="table table-striped table-bordered"
				id="resultAccessTb">
				<thead class="styleThead">
					<tr>
						<td>ACCESS_ID</td>
						<td>ACCESS_KEY</td>
						<td>用户/企业名称</td>
						<td>申请时间</td>
						<td>查询总条数</td>
						<td>剩余条数</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="allAccessTable" align="center">

				</tbody>
			</table>
		</div>
		
		<div class="row" style="text-align: center;" id="resultAccessPage">
			<div class="btn-group" id="button">
				<button id="Accesshomepage" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="Accesslastpage" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="Accesscenextpage" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="Accessendpage" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
		</div>
	</div>
	<div id="addAccessDiv" class="link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeAddAccessDivBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle">
				<tr>
					<td>用户/企业名称</td>
					<td><input class="form-control" id="addAccessName"></td>
				</tr>
			</tbody>
		</table>
		<div align="center" style="min-height: 65%;"></div>
		<button class="btn btn-sm btn-info pull-right"
			id="submitAddAccessDivBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
	<div id="updateAccessControl" class="link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeupdateAccessControlBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<br>
		<div align="center" id="chooseUpdateAccessControlTypeDiv">
			<input type="radio" class="btn btn-info" id="updateAccessControlRadio1" name="chooseUpdateAccessControlType" value="1" checked="checked"><label for="updateAccessControlRadio1">增加</label>&nbsp;&nbsp;&nbsp;
			<input type="radio" class="btn btn-info" id="updateAccessControlRadio2" name="chooseUpdateAccessControlType" value="2"><label for="updateAccessControlRadio2">减少</label>
		</div>
		<br>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle">
				<tr>
					<td>请输入增加/减少条数</td>
					<td><input class="form-control" id="updateAccessControlCount"></td>
				</tr>
			</tbody>
		</table>
		<div id="updateAccessControlWaring" align="center" style="min-height: 55%;color: red;"></div>
		<button class="btn btn-sm btn-info pull-right"
			id="submitupdateAccessControlBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
</div>
<div id="addAccessFade" class="black_overlay"></div>
</body>
</html>
