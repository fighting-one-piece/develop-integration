<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
 	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<head>
	<title>银行数据查询</title>
</head>
<body>
	<div class="row" style="margin-top: 5px; width: 100%; height: 100%;">
		<div style="width: 16%; float: left; padding-left: 20px;">
			<div>
				<button id="selphone" class="btn btn-sm btn-success">手机号查询</button>
				&nbsp;&nbsp;
				<button id="selbank" class="btn btn-sm btn-success">银行卡查询</button>
			</div>
			<!-- 手机号查询 -->
			<div id="nav_phone" style="display: none">
				<h5 align="center">&nbsp;</h5>
				<input id="selPhone" type="text" style="width: 200px;"
					placeholder="请输入手机号" /> <input id="submitquery" type="button"
					value="搜索" />
			</div>
			<!-- 银行卡号查询 -->
			<div id="nav_bank">
				<h5 align="center">&nbsp;</h5>
				<div align="left">
					<p>
						<input id="bankCard" type="text" style="width: 220px;"
							placeholder="请输入银行卡号" />
					</p>
					<p>
						<input id="idCard" type="text" style="width: 220px;"
							placeholder="请输入身份证号" />
					</p>
					<p>
						<input id="phone" type="text" style="width: 220px;"
							placeholder="请输入手机号" />
					</p>
					<p>
						<input id="name" type="text" style="width: 220px;"
							placeholder="请输入姓名" />
					</p>
					<p>
						<input id="lemonSub" style="margin-left: 70px;" type="button"
							value="确定" />
					</p>
					<p id="error" style="color: red; margin-left: 50px;"></p>
				</div>
			</div>
		</div>

		<div id="bank_result" align="center"
			style="width: 84%; float: left; display: none;">
			<div align="center" id="record">
				<p>
					<a href="#" id="basic" class="btn btn-sm btn-success">银行信息1</a>&nbsp;
					<a href="#" id="expense" class="btn btn-sm btn-success">银行信息2</a>&nbsp;
					<a href="#" id="detailed" class="btn btn-sm btn-success">银行信息3</a>&nbsp;
					<a href="#" id="other" class="btn btn-sm btn-success">银行信息4</a>&nbsp;
					<a href="#" id="kell" class="btn btn-sm btn-success">银行信息5</a>&nbsp;
				</p>
			</div>
			<div id="basicresult18" align="center"></div>
			<div id="expenseresult18" align="center"></div>
			<div id="detailedresult18" align="center"></div>
			<div id="kellresult18" align="center"></div>
			<div id="result18" align="center"></div>
		</div>


		<!-- 显示结果数据 -->
		<div id="nav_result" align="center" style="width: 84%; float: left;">

		</div>

	</div>
	<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
	<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
</div>
</body>
</html>
