<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<link rel="stylesheet" href="<%=basePath %>/css/bootstrap-table.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/bootstrap-table.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/statistics/switchs.js"></script>
<body>
	<div class="container" style="margin-top: 20px">
	
		<div role="tabpanel" class="tab-pane active"
			style="margin-bottom: 50px;">
			<div style="float: left; margin-top: 20px; font-size: 16px;">
				<span>接口开关列表</span>
				<button style="margin-top:-10px;margin-left:500px;" type="button" class="btn btn-danger" data-toggle='modal' data-target='#save' data-dismiss='modal'>添加</button>
			</div>
			<table id="areaList" 
				data-show-columns="true"
				data-row-style="rowStyle" 
				data-toolbar="#athleteTableToolbar"
				data-search="true" 
				data-pagination="true" 
				data-height="650"
				data-cache="false" 
				data-click-to-select="false">
				<thead>
					<tr>
						<th data-align="center" data-checkbox="true"></th>
						<!-- <th data-field="id" data-align="center">序号</th> -->
						<th data-field="switch_identity" data-align="center">标识</th>
						<th data-field="switch_name" data-align="center">名称</th>
						<th data-field="swith_desc" data-align="center">描述</th>
						<th data-field="status" data-align="center">状态</th>
						<th data-field="action" data-align="center"data-formatter="actionFormatterProject"data-events="actionEventsProject">编辑</th>
					</tr>
				</thead>
			</table>
			<button class="btn btn-primary" id="batchOpen">批量开</button>
			<button class="btn btn-primary" id="batchClose">批量关</button>
			<button class="btn btn-primary" id="batchTest">批量测试</button>
		</div>
	</div>
	
<!--  添加     -->
<div class="modal fade" id="save" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel" style="font-weight: bold;">添加</h4>
      </div>
      <div class="modal-body">
      	<div style="text-align: center;margin: 10px;"><label>标识：</label><input id='identity' style="border-radius: 3px;border: 1px solid #999;"/></div>
		<div style="text-align: center;margin: 10px;"><label>名称：</label><input id='name' style="border-radius: 3px;border: 1px solid #999;"/></div>
		<div style="text-align: center;margin: 10px;"><label>描述：</label><input id='desc' style="border-radius: 3px;border: 1px solid #999;"/></div>
		<div style="text-align: center;margin: 10px;width: 479px;"><label>状态：</label>
			<select id="testSelect" style="width: 100px">  
			  <option value ="0">停止使用</option>  
			  <option value ="1">正常使用</option>  
			  <option value="2">测试使用</option>
			</select>  
		</div>
		<div style="text-align: center;margin: 10px;">
			<button class="btn btn-default" data-dismiss="modal" style="width: 80px;float: right;margin-top: -25px;" id="addSwitch">保存</button>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

<!-- 修改  -->
<div class="modal fade" id="updata" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel" style="font-weight: bold;">修改</h4>
      </div>
      <div class="modal-body">
		<div style="text-align: center;margin: 10px;"><label>标识：</label><input id='identityA' disabled="disabled" style="border-radius: 3px;border: 1px solid #999;"/></div>
		<div style="text-align: center;margin: 10px;"><label>名称：</label><input id='nameA' style="border-radius: 3px;border: 1px solid #999;"/></div>
		<div style="text-align: center;margin: 10px;"><label>描述：</label><input id='descA' style="border-radius: 3px;border: 1px solid #999;"/></div>
		<div style="text-align: center;margin: 10px;width: 479px;"><label>状态：</label>
			<select id="statusA" style="width: 100px">  
			  <option value ="0">停止使用</option>  
			  <option value ="1">正常使用</option>  
			  <option value="2">测试使用</option>
			</select>  
		</div>
		<div style="text-align: center;margin: 10px;">
			<button class="btn btn-default" data-dismiss="modal" style="width: 80px;float: right;margin-top: -25px;" id="updataType">修改</button>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>
