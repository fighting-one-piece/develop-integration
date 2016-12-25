<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + "//" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">

</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QQ查询</title>
</head>
<body>
	<div>
		<div class="row" align="center" style="margin-top: 20px;">
			<div class="input-group" style="width: 300px;">
				<input id="query" type="text" class="form-control" onkeydown="EnterPressQuery()" 
				style="width: 280px"  placeholder="输入QQ号或者QQ群号或者QQ昵称"/> 
				<span class="input-group-btn" >
					<input class="btn btn-default" type="button" id="submitQQ" value="Go!"/>
				</span>
			</div>
			<div class="input-group" style="width: 280px; position: absolute;left: 39%; padding-top: 0.2%;">
				<!-- <input id="queryQQ" type="text" class="form-control"
					 onkeydown="EnterPressQun()" 
	value="输入QQ群号" onfocus="if(value=='输入QQ群号'){value=''}" 
	onblur="if(value==''){value='输入QQ群号'}"/> <span class="input-group-btn">
					<button class="btn btn-default" type="button" id="submitQQqun">Go!</button>
				</span> -->
				<label class="radio-inline" style="margin-left: 35px;">	
				  <input type="radio" name="radio" value="qq" checked> QQ
				</label>
				<label class="radio-inline" style="margin-left: 35px;">
				  <input type="radio" name="radio" value="qqqun"> QQ群
				</label>
				<label class="radio-inline" style="margin-left: 35px;">
				  <input type="radio" name="radio" value="qqnick"> QQ昵称
				</label>
			</div>
			<!-- 显示QQ的基本信息 -->
			<div id="resultsQQ" align="center" style="padding-top: 1.2%;">
				<label class="styleTitleOne">QQ基本信息</label>
				<table class="table table-striped table-bordered">
					<thead class="styleQQThead">
						
					</thead>
					<tbody class="styleQQTbody">
						
					</tbody>
				</table>
				<label class="ss"></label>
			</div>
			<!-- 显示群信息 -->
			<div id="results" align="center">
				<label class="styleTitleOne">群信息</label>
				<table class="table table-striped table-bordered">
					<thead class="styleThead">

					</thead>
					<tbody class="styleTbody">

					</tbody>
				</table>
				<label class="ss"></label>
			</div>
			<!-- 显示总数 -->
			<div id="count" align="center"></div>
			<!-- 下一页 -->
			<div align="center">
				<input id="nextQQNick" style="display: none;" value="下一页" type="button"/>
			</div>
		</div>
	</div>
	<!-- 群详情的DIV -->
	<div id="light" class="white_content">
		<div class="styleWhite">
			<button class="btn btn-sm btn-info" id="Colsebtn">关闭</button>
		</div>
		<table class="table table-striped table-bordered">
			<thead class="theadStyle">

			</thead>
			<tbody class="tbodyStyle">

			</tbody>
		</table>
		<!-- 显示总数 -->
		<div id="count1" align="center"></div>
	</div>
	<!-- 点击进入群详情页面后的背景 -->
	<div id="fade" class="black_overlay"></div>
</body>
</html>