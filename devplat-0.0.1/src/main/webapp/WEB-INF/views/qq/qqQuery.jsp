<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
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
		<div class="row" align="center" style="margin-top: 20px;width: 98%;">
			<div class="input-group" style="width: 300px;">
				<input id="query" type="text" class="form-control" onkeydown="EnterPressQuery()" 
				style="width: 280px"  placeholder="输入QQ号或者QQ群号或者QQ昵称"/> 
				<span class="input-group-btn" >
					<input class="btn btn-default" type="button" id="submitQQ" value="搜索"/>
				</span>
			<button class="btn btn-warning" style="position: absolute;visibility: hidden;" id="toShowForceDiv">查看关系图</button>
			<button class="btn btn-warning" style="position: absolute;visibility: hidden;" id="toShowQQresult">查看数据</button>
			</div>
			<div class="input-group" style="width: 98%; position: absolute; padding-top: 0.3%;">
				<!-- <input id="queryQQ" type="text" class="form-control"
					 onkeydown="EnterPressQun()" 
	value="输入QQ群号" onfocus="if(value=='输入QQ群号'){value=''}" 
	onblur="if(value==''){value='输入QQ群号'}"/> <span class="input-group-btn">
					<button class="btn btn-default" type="button" id="submitQQqun">Go!</button>
				</span> -->
				<label class="radio-inline" style="margin-left: 25px;">	
				  <input type="radio" name="radio" value="qq" checked> QQ
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="qqqun"> QQ群
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="qqnick"> QQ昵称
				</label>	
				<label class="radio-inline" style="margin-left: 25px;">	
				  <input type="radio" name="radio" value="qqtopyw"> QQ找朋友网
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="qqtowb"> QQ反找微博
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="qqtosession"> 对查找qq发起临时会话
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="qqtotalk"> qq查找最后说说
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="pywtoqq"> 朋友网找QQ
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="wbtoqq"> 微博url找QQ  
				</label>
				<label class="radio-inline" style="margin-left: 25px;">
				  <input type="radio" name="radio" value="phonetoqq"> 手机号找QQ   
				</label>
			</div>
			<!-- 显示QQ的基本信息 -->
			<div id="resultsQQ" align="center" style="padding-top: 1.2%;">
				<label class="styleTitleOne">关联信息</label>
				<table  style="width: 100%;">
					<thead class="styleQQThead">
						
					</thead>
					<tbody class="styleQQTbody">
						
					</tbody>
				</table>
				<label class="ss"></label>
			</div>
			<!-- 关系图div -->
			<div id="QQforceDiv" style="width: 97%;height: 89%;visibility: hidden;"></div>
			<!-- 显示群信息 -->
			<div id="results" align="center">
				<table  style="width: 100%;">
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
	<div id="pengyousearch">
	
	</div>
	<!-- 点击进入群详情页面后的背景 -->
	<div id="fade" class="black_overlay"></div>
	<!-- 点击搜索后的背景显示 -->
	<div id="background" class="all_backgroundcolor" align="center">
		<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
	</div>
	<script type="text/javascript" src="<%=basePath%>/js/modules/user/map/force/echarts.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/modules/qq/qqForce.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/modules/qq/qqQuery.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/modules/qq/honggu.js"></script>
</body>
</html>