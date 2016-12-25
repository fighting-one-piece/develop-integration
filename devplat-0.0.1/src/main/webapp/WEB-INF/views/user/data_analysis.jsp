<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>数据分析</title>
</head>
<body>
	<div>
		<!-- 查询 -->
		<div class="" style="margin-top: 60px;">
			<form method="POST" enctype="multipart/form-data" id="form1">
				<input id="upfile" type="file" style="display: none" name="upfile"/>
				<div class="input-group col-md-4" style="width: 300px;float:left">
					<input id="photoCover" disabled="disabled" class="form-control" type="text" style="height:30px;">
					<span class="input-group-btn" >
						<a class="btn btn-sm btn-primary" onclick="$('input[id=upfile]').click();"><i class="glyphicon glyphicon-folder-open"></i></a>  
					</span>  
				</div>
				<div class="col-md-8">
    				<input type="button" value="上传" id="btn" name="btn" class="btn btn-sm btn-info">
    				<input type="button" value="分析" id="btnAnalyze" name="btnAnalyze" class="btn btn-sm btn-warning">
    				<input type="button" value="导出" id="btnExport" name="btnExport" class="btn btn-sm btn-success">
				</div>
			</form>
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
			<div class="col-xs-7">
				
			</div>
		</div>
	</div>
</body>
</html>