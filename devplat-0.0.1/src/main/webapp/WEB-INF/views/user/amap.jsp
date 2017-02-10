<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>GIS查询</title>
    <link rel="stylesheet" href="<%=basePath%>/css/sweetalert2.min.css" />
	<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
    <style>
    	.info-tip {
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 12px;
            background-color: #fff;
            height: 35px;
            text-align: left;
        }
        #amapquery {
  			 display:inline;
        }
		#container {
			position: absolute;
			top: 4.5%;
			left: 0;
			right: 0;
			bottom: 0;
			width: 100%;
			height: 95.5%;
			visibility: hidden;
		}
		#forceDiv{
			position: absolute;
			top: 4.5%;
			left: 0;
			right: 0;
			bottom: 0;
			visibility: hidden;
		}
		
		.button-group {
			position: absolute;
			top: 6%;
			left: 5%;
			font-size: 12px;
			padding: 10px;
		}
		
		.button-group .button {
			height: 28px;
			line-height: 28px;
			background-color: #0D9BF2;
			color: #FFF;
			border: 0;
			outline: none;
			padding-left: 5px;
			padding-right: 5px;
			border-radius: 3px;
			margin-bottom: 4px;
			cursor: pointer;
		}
		.button-group .inputtext {
			height: 26px;
			line-height: 26px;
			border: 1px;
			outline: none;
			padding-left: 5px;
			padding-right: 5px;
			border-radius: 3px;
			margin-bottom: 4px;
			cursor: pointer;
		}
		#tip {
			background-color: #fff;
			padding-left: 10px;
			padding-right: 10px;
			position: absolute;
			font-size: 12px;
			right: 10px;
			top: 20px;
			border-radius: 3px;
			border: 1px solid #ccc;
			line-height: 30px;
		}
		
		.amap-info-content {
			font-size: 12px;
		}
		
		#myPageTop {
			position: absolute;
			top: 5px;
			right: 10px;
			background: #fff none repeat scroll 0 0;
			border: 1px solid #ccc;
			margin: 10px auto;
			padding:6px;
			font-family: "Microsoft Yahei", "微软雅黑", "Pinghei";
			font-size: 14px;
		}
		#myPageTop label {
			margin: 0 20px 0 0;
			color: #666666;
			font-weight: normal;
		}
		#myPageTop input {
			width: 170px;
		}
		#myPageTop .column2{
			padding-left: 25px;
		}
		.black_overlay {
			display: none;
			position: absolute;
			top: 0%;
			left: 0%;
			width: 100%;
			height: 100%;
			background-color: black;
			z-index: 1001;
			-moz-opacity: 0.8;
			opacity: .80;
			filter: alpha(opacity = 80);
		}
		.link_white_content {
			display: none;
			position: absolute;
			top: 90px;
			left: 20%;
			width: 60%;
			height: 580px;
			padding: 16px;
			border: 5px solid orange;
			border-radius: 10px;
			background-color: white;
			z-index: 1002;
			overflow-y: auto;
		}
    </style>
    <script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.3&key=f9e3f46783fd0d412cc9ea713c986202&plugin=AMap.Geocoder,AMap.ToolBar"></script>
</head>
<body>

<div id="searchdiv" align="center" style="width: 100%">
	<div style="width: 600px" align="center" id="adfeesf">
		<input type="text" class="form-control input-sm" placeholder="请输入关键字，多个关键字间用空格隔开" id="amapquery" style="width: 50%;">
		<button class="btn btn-sm btn-info" id="toChooseCity">选择城市</button>
		<button value="搜索" class="btn btn-sm btn-info" id="amapsearch">搜索</button>
		<button class="btn btn-sm btn-info" id="toAmapSearchResult" style="display: none;">数据列表</button>
		<button class="btn btn-sm btn-info" id="toAmap">地图定位</button>
		<button class="btn btn-sm btn-info" id="toForce">人物关系</button>
	</div>

</div>
<div id="container"></div>
<div id="setFitViewDiv" class="button-group" style="visibility: hidden;">
    <input id="setFitView" class="button" type="button" value="地图自适应显示"/>
</div>
<div id="resultsAmapSearch" align="center">
		
</div>
<div id="chooseCity" class="link_white_content">
	<div class="styleWhite">
		<button class="btn btn-sm btn-info" id="closeChooseCityDivBtn"
			style="margin-bottom: 10px; margin-left: 5%">关闭</button>
	</div>
	<br>
	<div style="min-height: 5%;" align="center" id="chooseCitiTypeRadio">
		<input type="radio" class="btn btn-info" id="radio3" name="chooseCitiTypeRadio" value="3" checked="checked"><label for="radio3">不设置城市范围</label>&nbsp;&nbsp;
		<input type="radio" class="btn btn-info" id="radio1" name="chooseCitiTypeRadio" value="1"><label for="radio1">手动选择</label>&nbsp;&nbsp;
		<input type="radio" class="btn btn-info" id="radio2" name="chooseCitiTypeRadio" value="2"><label for="radio2">当前搜索手机号的归属地（如果关键字中不包含手机号则无效）</label>
	</div>
	<br>
	<div align="center" style="min-height: 65%;">
	<div class="row" align="center" style="min-height: 70%;display: none;" id="provinceCityDiv">
	<select class="form-control input-sm" style="max-width: 25%;display: inline;" id="selectprovince">
		<option selected="selected"></option>
	</select>
	</div>
	</div>
	<button class="btn btn-sm btn-info pull-right"
		id="submitchooseCityBtn"
		style="margin-top: 10px; margin-right: 5%">确定</button>
</div>
<div id="amapFade" class="black_overlay"></div>
<div id="forceDiv" style="width: 100%;height: 95.5%;"></div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
	<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
</div>
<!-- eCharts模块 -->
<script type="text/javascript" src="<%=basePath%>/js/modules/user/map/force/echarts.js"></script>
<!-- 关系图模块 -->
<script type="text/javascript" src="<%=basePath%>/js/modules/user/map/GISForce.js"></script>
<!-- 地图，搜索模块 -->
<script type="text/javascript" src="<%=basePath%>/js/modules/user/map/map.js"></script>
</body>

</html>