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
	href="<%=basePath%>/css/modules/admin/link/link.css" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/laydate.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/modules/admin/link/link.js"></script>
</head>
<body>
	<div class="styleWhite" align="center">
		<button class="btn btn-sm btn-info" id="toAddLinkAddressBtn"
			style="margin-bottom: 20px; margin-top: 20px">添加</button>
	</div>
	<div class="row">
		<div class="col-lg-12">
			<table class="table table-striped table-bordered"
				id="resultAResourceTb">
				<thead class="styleThead">
					<tr>
						<td>资源名称</td>
						<td>资源标识</td>
						<td>资源URL</td>
						<td>资源类型</td>
						<td>资源图标</td>
						<td>资源优先级</td>
						<td>父资源名称</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="allLinkAddressTable" align="center">

				</tbody>
			</table>
		</div>
		
		<div class="row" style="text-align: center;" id="resultAResourcePage">
			<div class="btn-group" id="button">
				<button id="AResourcehomepage" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="AResourcelastpage" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="AResourcenextpage" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="AResourceendpage" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
		</div>
	</div>
	<div id="addLinkAddressDiv" class="link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeAddLinkAddressDivBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle">
				<tr>
					<td>资源名称</td>
					<td><input class="form-control" id="addAResourceName"></td>
				</tr>
				<tr>
					<td>资源类型</td>
					<td><select class="form-control" id="addAResourceType">
							<option id="addusermenu">用户菜单</option>
							<option id="addadminmenu">管理员菜单</option>
							<option id="addinterface">接口</option>
					</select></td>
				</tr>
				<tr>
					<td>资源标识</td>
					<td><input class="form-control" id="addAResourceIdentity"></td>
				</tr>
				<tr>
					<td>资源图标</td>
					<td><input class="form-control" id="addAResourceIcon" placeholder="选填"></td>
				</tr>
				<tr>
					<td>资源URL</td>
					<td><input class="form-control" id="addAResourceUrl"></td>
				</tr>
				<tr>
					<td>资源优先权</td>
					<td><input class="form-control" id="addAResourcePriority"></td>
				</tr>
				<tr>
					<td>父资源</td>
					<td><select class="form-control" id="addAResourceParentId"></select></td>
				</tr>
			</tbody>
		</table>
		<button class="btn btn-sm btn-info pull-right"
			id="submitAddLinkAddressDivBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
	<div id="updateLinkAddressDiv" class="link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeUpdateLinkAddressDivBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle" id="updateLinkAddressTb">
				<tr>
					<td>资源名称</td>
					<td><input class="form-control" id="updateLinkname"></td>
				</tr>
				<tr>
					<td>资源标识</td>
					<td><input class="form-control" id="updateLinkidentity"></td>
				</tr>
				<tr>
					<td>资源图标</td>
					<td><input class="form-control" id="updateLinkicon" placeholder="选填"></td>
				</tr>
				<tr>
					<td>资源URL</td>
					<td><input class="form-control" id="updateLinkurl"></td>
				</tr>
				<tr>
					<td>资源类型</td>
					<td><select class="form-control" id="updateLinktype">
						<option id="updateusermenu">用户菜单</option>
						<option id="updateadminmenu">管理员菜单</option>
						<option id="updateinterface">接口</option>
					</select></td>
				</tr>
				<tr>
					<td>资源优先权</td>
					<td><input class="form-control" id="updateLinkpriority"></td>
				</tr>
				<tr>
					<td>父资源</td>
					<td><select class="form-control" id="updateLinkparentId"></select></td>
				</tr>
			</tbody>
		</table>
		<button class="btn btn-sm btn-info pull-right"
			id="submitUpdateLinkAddressDivBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
	<!-- 点击进入群详情页面后的背景 -->
	<div id="addLinkAddressFade" class="black_overlay"></div>
</body>
</html>
