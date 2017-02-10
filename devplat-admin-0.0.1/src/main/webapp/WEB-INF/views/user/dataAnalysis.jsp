<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/user/data_analysis.js"></script>
<link rel="stylesheet" href="<%=basePath%>/css/modules/user/data_analysis.css" />
<link rel="stylesheet" href="<%=basePath %>/css/bootstrap.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据分析</title>
</head>
<body>
	<div>
		<!-- 查询 -->
		<div style="margin-top: 20px;margin-left: 20px;">
			<form method="POST" enctype="multipart/form-data" id="form1">
				<input id="upfile" type="file" style="display: none" name="upfile"/>
				<div class="input-group col-md-4" style="width: 300px;float:left">
					<input id="photoCover" disabled="disabled" class="form-control" type="text" style="height:30px;">
					<span class="input-group-btn" >
						<a class="btn btn-sm btn-primary" style="height:30px;" onclick="$('input[id=upfile]').click();"><i class="glyphicon glyphicon-folder-open"></i></a>  
					</span>  
				</div>
				<div class="col-md-8">
    				<input type="button" value="上传" id="btn" name="btn" class="btn btn-sm btn-info">
    				<input type="button" value="分析" id="btnAnalyze" name="btnAnalyze" class="btn btn-sm btn-warning">
    				<input type="button" value="导出" id="btnExport" name="btnExport" class="btn btn-sm btn-success">
    				<input type="button" value="下载模板" id="btnExportDemo" name="btnExportDemo" class="btn btn-sm btn-success">
					<i id="styleCheck">*格式错误,请参考模板。</i>
				</div>
			</form>
		</div>
		<!-- 加载显示 -->
		<div id="resultShow" class="row" style="width: 460px;float: left;margin-left: 20px;margin-top:20px; text-align: center">
			<!--查询显示-->
			<div class="" >
					<table class="table table-striped table-bordered">
						<thead class="styleThead">
							<tr>
								<td>文件名</td>
								<td>操作</td> 
							</tr>
						</thead>
						<tbody class="Tbody">
							
						</tbody>
					</table>
			</div>
			<!--分页-->
			<div class="" style="text-align: center;">
				<div class="btn-group" id="button">
					<button id="homepage" class="btn btn-sm btn-info" value="1">首页</button>
					<button id="lastpage" class="btn btn-sm btn-info" value="1">上一页</button>
					<button id="nextpage" class="btn btn-sm btn-info" value="1">下一页</button>
					<button id="endpage" class="btn btn-sm btn-info" value="1">末页</button>
				</div>
			</div>
		</div>
		<!-- 分析页面 -->
		<div id="nav" style="float: left; margin-top: 10px;">
			<!-- 原数据展示DIV -->
			<div class="col-xs-5">
				<table class="table table-striped table-bordered">
					<thead class="ExcelThead">
						
					</thead>
					<tbody class="ExcelTbody">
						
					</tbody>
				</table>
			</div>
			<!-- 分析后数据展示DIV -->
			<div class="col-xs-7" id="DataPrese">
					
			</div>
		</div>
		<!-- 类型框，初始隐藏 -->
		<div  id="UserPresentation" align="center" class="User_Peresentation">
			<br/>
			<div align="center">
			<button class="btn btn-sm btn-info" id="ituation">全局搜索</button>&nbsp;&nbsp;
			<button class="btn btn-sm btn-info" id="local">类型搜索</button>			
			</div><br/><br/>
			<!--全局搜索选择框 -->
			<div id="Ovaerall" class="radio_radios">
				<label><input name="Fruit" type="radio" value="phone" />手机</label>
				<label><input name="Fruit" type="radio" value="idCard" />身份证 </label><br/><br/>
			  <div class="styleWhite" align="center" id="centers">
				<button class="btn btn-sm btn-info" id="ituationsuer">確定</button>&nbsp;&nbsp;
				<button class="btn btn-sm btn-info" id="ituationColsebtn">关闭</button>	
			   </div>			
			</div>
			<!--类型搜索选择 -->
			<div id="local_locals" class="radio_radios">
				<label><input name="Fru" type="radio" value="phone" />手机</label>
				<label><input name="Fru" type="radio" value="idCard" />身份证 </label>
				<!-- 下拉标签 -->
				<div>
				 <select style="width: 150px"class="index" id="localindex">
				</select>
				 <select style="width: 150px" class="type" id="localtype">
				</select>
				
				</div><br/>
				<div class="styleWhite" align="center" id="centers">
					<button class="btn btn-sm btn-info" id="localSuer">確定</button>&nbsp;&nbsp;
				<button class="btn btn-sm btn-info" id="localColsebtn">关闭</button>	
				</div>
			</div>
			<!--外层关闭按钮 -->
			<div class="styleWhite" align="center" id="centers">
				<button class="btn btn-sm btn-info" id="Colsebtn">关闭</button>
				</div>
		</div>
		<!-- 弹框后的背景，初始隐藏 -->
		<div id="loading" class="load_loading"><img src="images/admin/loadding.jpg" alt=""/>正在加载分析数据,请稍候...</div>
		<div id="addLinkAddressFade" class="black_overlay"></div>
		<!-- 显示分析的数据 -->
		<div id="showEvent" class="showData">
			<button class="btn btn-sm btn-warning" id="expAnalyze">再次分析</button>&nbsp;&nbsp;<button class="btn btn-sm btn-success" id="expExcel">导出</button><button class="btn btn-sm btn-info" id="Colsebutton">关闭</button>
			<!-- 显示数据 -->
			<div class="row" style="margin-top: 10px;">
				<div class="col-xs-5">
					<table class="table table-striped table-bordered">
						<thead class="TheadExcel">
							
						</thead>
						<tbody class="TbodyExcel">
							
						</tbody>
					</table>
				</div>
				<div class="col-xs-7" >
					<table width="100px" class="table table-striped table-bordered" >
						<thead class="TheadExcelone">
							
						</thead>
						<tbody class="TbodyExcelone">
							
						</tbody>
					</table>
				</div>
			</div>
			<!--分页-->
			<div class="" style="text-align: center;">
				<div class="btn-group" id="button">
					<button id="home" class="btn btn-sm btn-info" value="1">首页</button>
					<button id="last" class="btn btn-sm btn-info" value="1">上一页</button>
					<button id="next" class="btn btn-sm btn-info" value="1">下一页</button>
					<button id="end" class="btn btn-sm btn-info" value="1">末页</button>
				</div>
			</div>
		</div>
		<!-- 背景 -->
		<div id="fade" class="blackoverlay"></div>
	</div>
</body>
</html>