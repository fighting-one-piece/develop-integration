<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>

<link rel="stylesheet" href="<%=basePath %>/css/modules/statistics/statistics.css"/>
<link rel="stylesheet" href="<%=basePath %>/css/sweetalert2.min.css"/>
<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/modules/statistics/switch.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
<body>
<div class="container" style="margin-top: 20px">
		<h3 style="font-weight:bold;text-align: center;">接口开关</h3>
		<div style="float: right;margin-right: 15%;margin-bottom: 20px;"><button type="button" class="btn btn-danger" data-toggle='modal' data-target='#save' data-dismiss='modal'>添加</button></div>
		<br/>
		<table class="table table-bordered">
			<thead>
				<tr>
					<td>开关标识</td>
					<td>开关名称</td>
					<td>开关描述</td>
					<td>状态</td>
					<td>编辑</td>
				</tr>
			</thead>
			<tbody id="GridView">	
 			</tbody>
		</table>
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
