<!DOCTYPE HTML>
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<%-- <link rel="stylesheet" href="<%=basePath %>/css/modules/index/index.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/log/log.css"/> --%>
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/resourceTree.css" />

<script type="text/javascript" src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/resourceTree.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/admin/auser.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/laydate.js"></script>
<head>
<meta charset="utf-8">
</head>
<body>
	<h1 align="center" style="margin-top: 2%;"></h1>
	<!-- 增加按钮 -->
	<div  align="right" style="margin-right: 12%;">
		<button id="showaddauser" class="btn btn-sm btn-info" style="margin-right: 1%;">增加用户</button>
		<button id="showselectallauser" class="btn btn-sm btn-info" style="margin-right: 1%;">查看用户</button>
		<button id="showselectauser" class="btn btn-sm btn-info" style="margin-right: 1%; ">搜索用户</button>
	</div>
	<!-- 搜索界面 -->
	<div id="showSelectauser" align="center" style="display: none;margin-top: 5%">
		<input id="selectauser" type="text" style="width: 200px;" placeholder="请输入账号" onkeydown="EnterPressEnsure();"/>
		<input id="ensureauser" type="button" value="搜索"/>
		<br/><span id="warning" style="color: red;"></span>
	</div>
	
	<!-- 添加用户界面 -->
	<div id="showaddauserpage" align="center" style="display: none; margin-top: 5%;">
		<table style="height: 80%;width: 22%;" class="table table-striped table-bordered">
			<tr align="center">
				<td style="background: #EEE8AA;">账号</td>
				<td><input type="text" style="width: 100%" id="setaccount" placeholder="请输入账号"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">密码</td>
				<td><input type="password" style="width: 100%" id="setpassword" placeholder="密码为6至16位的字母及数字组成"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">确认密码</td>
				<td><input type="password" style="width: 100%" id="confirmpassword" placeholder="请输入确认密码"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">昵称</td>
				<td><input type="text" style="width: 100%" id="nickname"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">邮箱</td>
				<td><input type="text" style="width: 100%" id="email"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">标识</td>
				<td ><input type="text" style="width: 100%" id="identity"/></td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">状态</td>
				<td>
					<select style="width: 100%" id="status">
						<option>是</option>
						<option>否</option>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td style="background: #EEE8AA;">过期时间</td>
				<td><input type="text"  id="expireTime" class="inline laydate-icon" style="width: 100%" /></td>
			</tr>
		</table>
		<p><span id="addwarning" style="color: red;"></span></p>
		<div id="button">
			<button id="addauser" class="btn btn-sm btn-info" value="1">确定</button>
		</div>
	</div>
	
	<div id="showAUserResult" align="center">
	
	</div>
	<!-- 翻页按钮 -->
	<div class="row" align="center" style="margin: 1%; display: none;" id="turnPage">
		<div  class="btn-group" >
			<button id="homepage" class="btn btn-sm btn-info" value="1">首页</button>
			<button id="lastpage" class="btn btn-sm btn-info" value="1">上一页</button>
			<button id="nextpage" class="btn btn-sm btn-info" value="1">下一页</button>
			<button id="endpage" class="btn btn-sm btn-info" value="1">末页</button>
		</div>
	</div>	
	<!-- 修改查询条数 -->
	<div id="updateAccessUserControl" class="resource_link_white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="closeupdateAccessUserControlBtn"
				style="margin-bottom: 10px; margin-left: 5%">关闭</button>
		</div>
		<br>
		<div align="center" id="chooseUpdateAccessUserControlTypeDiv">
			<input type="radio" class="btn btn-info" id="updateAccessControlRadio1" name="chooseUpdateAccessControlType" value="1" checked="checked"><label for="updateAccessControlRadio1">增加</label>&nbsp;&nbsp;&nbsp;
			<input type="radio" class="btn btn-info" id="updateAccessControlRadio2" name="chooseUpdateAccessControlType" value="2"><label for="updateAccessControlRadio2">减少</label>
		</div>
		<br>
		<table class="table table-striped table-bordered">

			<tbody class="tbodyStyle">
				<tr>
					<td>请输入增加/减少条数</td>
					<td><input class="form-control" id="updateAccessUserControlCount"></td>
				</tr>
			</tbody>
		</table>
		<div id="updateAccessUserControlWaring" align="center" style="min-height: 55%;color: red;"></div>
		<button class="btn btn-sm btn-info pull-right"
			id="submitupdateAccessUserControlBtn"
			style="margin-top: 10px; margin-right: 5%">提交</button>
	</div>
</body>
<script type="text/javascript">
!function(){
	laydate.skin('yalan');//切换皮肤，请查看skins下面皮肤库
	laydate({elem: '#demo'});//绑定元素
}();

//自定义日期格式
laydate({
    elem: '#expireTime',
    format: 'YYYY-MM-DD',
    festival: true, //显示节日
});
</script>
</html>
