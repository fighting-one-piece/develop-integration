<html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<head>
<link rel="stylesheet" href="<%=basePath%>/css/sweetalert2.min.css" />
<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/resourceTree.css" />
<link rel="stylesheet" href="<%=basePath%>/css/modules/admin/datainterface/AccessUserInterface.css"/>

<script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.0.js"></script>
<script type="text/javascript"src="<%=basePath%>/js/sweetalert2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/resourceTree.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/datainterface/accessUserInterface.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/modules/admin/datainterface/accessMoney.js"></script>
</head>
<body>
	 <div class="headline">用户接口
	 </div> 
	  <div id="addUser">
	  	<div id="User_face" class="btn btn-sm btn-info">增加新用户</div>
	  </div>
	<!--数据拼接  -->
	<div class="main">
		<table id="Pagination">
	 	<tr class="Pagination_id">
	 	</tr>
	 	</table>
	 	
	<!--分页  -->
		<div class="styleWhite" align="center" id="centers">
			<div class="btn-group" align="center">
				<button id="Accesshomepage" class="btn btn-sm btn-info" value="1">首页</button>
				<button id="Accesslastpage" class="btn btn-sm btn-info" value="1">上一页</button>
				<button id="Accesscenextpage" class="btn btn-sm btn-info" value="1">下一页</button>
				<button id="Accessendpage" class="btn btn-sm btn-info" value="1">末页</button>
			</div>
			</div>
	<!-- 修改弹窗 -->
		<div id="updata" class="updata">
			<div>用户接口修改</div><br/>
			<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">编号</span>
			<input disabled="true" style="width: 300px" id="update_id" type="text"   class="form-control" placeholder="" aria-describedby="basic-addon1">
	 		</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">账户</span>
			<input disabled="true" style="width: 300px" id="update_account" type="text"  class="form-control" placeholder="" aria-describedby="basic-addon1">
	 		</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">接口</span>
			<input disabled="true" style="width: 300px" id="update_interfaceId" type="text"  class="form-control" placeholder="" aria-describedby="basic-addon1">
	 		</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">费用</span>
	 		<select  id="sid" style="width:300px;height: 30px;">  
            			<option id="one" value="0">未收费</option>
            			<option id="two" value="1">收费</option>  
    				</select>		
	 		</div><br/>
    		<div class="styleWhite" align="center" id="centers">
				<button class="btn btn-sm btn-info" id="Suerbtn">确定</button>&nbsp;&nbsp;
				<button class="btn btn-sm btn-info" id="Colsebtn">关闭</button>
			</div>
		</div>
		
		<!-- 查看弹窗 -->
		<div id="findAccessMoney" class="updata">
			<div>
				<div  style="margin-bottom: 1%;"><button style="left: 0px;" id="closeAccessMoney">关闭</button><button style="right: 0px;" id="addAccessMoney">添加</button></div>
				<table class="table table-striped table-bordered" style="text-align: center;" id="accessMoneyResult">

				</table>
				<div><span id="accessMoneyWaring"></span></div>
			</div>
		</div>
		
		<!-- 修改弹窗 -->
		<div id="updateAccessMoneys" class="updata">
			<div>
				<div  style="margin-bottom: 1%;"><button style="left: 0px;" id="closeUAccessMoney">关闭</button><button style="right: 0px;" id="updateSureAccessMoney">确定</button></div>
				<table class="table table-striped table-bordered" style="text-align: center;" id="accessUMoneyResult">
					<tr>
						<td>ID</td>
						<td id="setID"></td>
					</tr>
					<tr>
						<td>用户接口ID</td>
						<td id="setUserID"></td>
					</tr>
					<tr>
						<td>响应代码</td>
						<td><input type="text" id="setRponeCode"/></td>
					</tr>
					<tr>
						<td>金额</td>
						<td><input type="text" id="setMoney"/></td>
					</tr>
					<tr>
						<td>是否删除</td>
						<td id="setFlag"></td>
					</tr>
				</table>
				<div><span id="uaccessMoneyWaring"></span></div>
			</div>
		</div>
		
		<!-- 添加弹窗 -->
		<div id="addAccessMoneys" class="updata">
			<div>
				<div  style="margin-bottom: 1%;"><button style="left: 0px;" id="closeAAccessMoney">关闭</button><button style="right: 0px;" id="addSureAccessMoney">确定</button></div>
				<table class="table table-striped table-bordered" style="text-align: center;" id="accessAMoneyResult">
					<tr>
						<td>用户接口ID</td>
						<td><input type="text" id="asetUserID"/></td>
					</tr>
					<tr>
						<td>响应代码</td>
						<td><input type="text" id="asetRponeCode"/></td>
					</tr>
					<tr>
						<td>金额</td>
						<td><input type="text" id="asetMoney"/></td>
					</tr>
				</table>
				<div><span id="aaccessMoneyWaring"></span></div>
			</div>
		</div>
		<!-- 新增用户-->
		<div id="addUser_face" class="addUser_face">
			<div>添加用户接口</div><br/>
			<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">添加用户</span>
			<select  id="add_user" style="width:300px;height: 30px;">  
            			
    				</select>		
	 		</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">添加接口</span>
			<select  id="add_face" style="width:300px;height: 30px;">  
            			
    				</select>		
	 		</div><br/>
	 		<div class="input-group" id="center">
	 		<span style="width: 100px" class="input-group-addon">是否收费</span>
			<select  id="add_chargeFlag" style="width:300px;height: 30px;">  
            			<option id="one" value="0">未收费</option>
            			<option id="two" value="1">收费</option>  
    				</select>		
	 		</div><br/>
	 		
	 		<!--按钮-->
			<div class="styleWhite" align="center" id="centers">
				<button class="btn btn-sm btn-info" id="Suerbtn_addUser">确定</button>&nbsp;&nbsp;
				<button class="btn btn-sm btn-info" id="Colsebtn_addUser">关闭</button>
			</div>
	 		</div><br/>
		</div>
	 <!--背景  -->
	 <div id="addLinkAddressFade" class="black_overlay"></div>	

</body>
</html>