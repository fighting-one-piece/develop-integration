<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日志查询</title>
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/modules/log/log.css"/>

<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/log/log.js"></script>

</head>
<body>
	<div align="center" style="margin-top: 33px;">
		<button id="hotWords" class="btn btn-sm btn-info" style="margin-right: 1%;">热词搜索</button>
		<button id="orderTime" class="btn btn-sm btn-info">最近搜索</button>
	</div>
	<div>
		<!--查询显示-->
		<div class="row" style="margin-top: 33px;">
			<div class="col-md-10 col-md-offset-1">
				<table class="table table-striped table-bordered">
					<thead class="styleThead">
						<tr>
							<td>关键字</td>
							<td>统计</td>
						<!-- 	<td>操作</td> -->
						</tr>
					</thead>
					<tbody class="Tbody">
						
					</tbody>
				</table>
			</div>
		</div>
		<!--分页-->
		<div class="row" style="text-align: center;">
			<div class="btn-group" id="button">
				<button id="homepage" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="lastpage" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="nextpage" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="endpage" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
		</div>
	</div>
	<!-- 关键字详情 -->
	<div id="lightone" >
		<div class="styleTo">
			<button class="btn btn-sm btn-info" id="btnColse">关闭</button>
		</div>
		<table class="table table-striped table-bordered">
			<thead class="styleth">
				<tr>
					<td>关键字</td>
					<td>IP</td>
					<td>时间</td>
				</tr>
			</thead>
			<tbody class="styletb">
				
			</tbody>
		</table>
		<!--分页-->
		<div class="row" style="text-align: center;">
			<div class="btn-group" id="button">
				<button id="home" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="last" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="nextone" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="end" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
		</div>
	</div>
	<!-- 点击进入群详情页面后的背景 -->
	<div id="fadeone" class="black_overlay"></div>
	<script type="text/javascript" src="<%=basePath %>/js/modules/log/time.js"></script>
</body>
</html>