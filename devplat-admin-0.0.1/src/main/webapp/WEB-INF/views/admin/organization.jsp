<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/modules/admin/group/group.js"></script>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/admin/group/group.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<script type="text/javascript" src="<%=basePath %>/js/sweetalert2.min.js"></script>
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/resourceTree.css" />
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/resourceTree.js"></script>
</head>
<body>
	<div class="styleWhite">
		<button id="addGroup" class="btn btn-sm btn-info"><i class="glyphicon glyphicon-plus"></i>添加</button>
		<table id="styleTable" style="width: 600px;font-size: 12px;" class="table table-striped table-bordered">
			<thead class="">
				<tr>
					<td>编号</td>
					<td>排序</td>
					<td>标识</td>
					<td>描述</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody id="groupresult" class="groupresult">
					
			</tbody>
		</table>
	</div>
	<div id="light" class="whitecontent">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="Colsebtn">关闭</button>
			<table id="tableStyle"  class="table table-striped table-bordered">
				<tr>
					<td>群组名称</td>
					<td><input id="groupNmae" type="text" placeholder="请输入群组名称"/></td>
					<td>排序</td>
					<td><input id="groupSort" type="text" placeholder="请输入数字"/></td>
				</tr>
				<tr>
					<td>群组描述</td>
					<td><input id="groupDescribe" type="text" placeholder="群组描述"/></td>
					<td>是否可删除</td>
					<td><label><input type="radio" name="radio" value="1" checked="checked">可删除</label><label><input type="radio" name="radio" value="0" >不可删除</label></td>
				</tr>
				<tr>
					<td colspan="4"><input id="add" type="button" class="btn btn-sm btn-info" value="添加"/></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- 背景 -->
	<div id="fade" class="blackoverlay"></div>
	<div id="cc"></div>
	<!-- 为群组添加、移除用户 -->
	<div id="ad_GU" class="add_del_GU">
		<button class="btn btn-sm btn-info" id="Colseone">关闭</button>
		<table class="table table-striped table-bordered">
				<tr>
					<td>用户</td>
					<td>操作</td>
					<td>群组</td>
				</tr>
				<tr>
					<td width="126" id="notUser">
						<!--multiple="multiple" 能同时选择多个   size="10"  确定下拉选的长度-->

						<select  name="first" multiple="multiple" size=10 class="td3" id="first" >
							
						</select>
					</td>
					<td width="69" valign="middle" class="td_button">
						<button style="width: 86px" name="add" id="addU" class="btn btn-sm btn-info">添加<i class="glyphicon glyphicon-chevron-right"></i></button><br/>
						<button style="width: 86px" name="add_all" id="add_all" class="btn btn-sm btn-info">全部添加<i class="glyphicon glyphicon-forward"></i></button><br/>
						<button style="width: 86px" name="remove" id="remove" class="btn btn-sm btn-info">移除<i class="glyphicon glyphicon-chevron-left"></i></button><br/>
						<button style="width: 86px" name="remove_all" id="remove_all" class="btn btn-sm btn-info">全部移除<i class="glyphicon glyphicon-backward"></i></button>						
					</td>
					<td width="127" align="left" id="getByIdUser">
						<select name="second" size="10" multiple="multiple" class="td3" id="second">
							
						</select>
					</td>
				</tr>
			</table>
			<button id="add_GUser" class="btn btn-sm btn-info">确定</button>
	</div>
</body>
</html>
