$(document).ready(function () {
	
	//隐藏多个界面
	function hideAll(){
		$("#showSelectauser").hide();
		$("#showaddauserpage").hide();
		$("#showupdateauserpage").hide();
		$("#turnPage").hide();
	};
	
	loadingAuser();
	//点击查看加载首页
	$("#showselectallauser").click(function(){
		hideAll();
		$("#showAUserResult").empty();
		loadingAuser();
	});
	function loadingAuser(){
		hideAll();
		$("#showAUserResult").empty();
	$.ajax({
		url:"page",
		type:"get",
		dataType:"json",
		success:function(result){
			if (result.code == 1) {
				var data = result.data;
				var num = data.length;
				var html = "<table style='width:80%;margin-top: 3%;' id='selecttable'><tr align='center'>";
					html += 	"<td style='background: #EEE8AA;'>ID</td>";
				 	html += 	"<td style='background: #EEE8AA;'>账号</td>";
				 	html += 	"<td style='background: #EEE8AA;'>标识</td>";
				 	html += 	"<td style='background: #EEE8AA;'>昵称</td>";
				 	html += 	"<td style='background: #EEE8AA;'>邮箱</td>";
				 	html += 	"<td style='background: #EEE8AA;'>状态</td>";
				 	html += 	"<td style='background: #EEE8AA;'>创建时间</td>";
				 	html += 	"<td style='background: #EEE8AA;'>过期时间</td>";
				 	html += 	"<td style='background: #EEE8AA;'>删除标识</td>";
				 	html += 	"<td style='background: #EEE8AA;'>修改/删除</td>";
				 	html += 	"<td style='background: #EEE8AA;'>添加功能</td>";
				 	html += "</tr>";
				for (var i = 0; i < data.length; i++) {
					html += "<tr align='center'>";
					html += 	"<td>"+data[i].id+"</td>";
					html += 	"<td>"+data[i].account+"</td>";
					html += 	"<td>"+data[i].identity+"</td>";
					html += 	"<td>"+data[i].nickname+"</td>";
					html += 	"<td>"+data[i].email+"</td>";
					html += 	"<td>"+data[i].status+"</td>";
					html += 	"<td>"+data[i].createTime+"</td>";
					html += 	"<td>"+data[i].expireTime+"</td>";
					html += 	"<td>"+data[i].deleteFlag+"</td>";
					html += 	"<td><button id='updateauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>修改</button>";
					html +=		"<button id='deleteauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>删除</button></td>";
					html += 	"<td><button id='addgroup' class='btn btn-sm btn-info' style='margin-right: 1%;'>组</button>";
					html += 	"<button id='addrole' class='btn btn-sm btn-info' style='margin-right: 1%;'>角色</button>";
					html += 	"<button  class='btn btn-sm btn-info addpermissions' style='margin-right: 1%;'>资源</button></td>";
					html += "</tr>";
				}
					html += "</table>";
				$("#showAUserResult").append(html);
			}else {
				$("#showAUserResult").append("请刷新页面！");
			}
		}
	});
}
	//点击搜索显示查询页面
	$("#showselectauser").click(function(){
		hideAll();
		$("#showAUserResult").empty();
		$("#showSelectauser").show();
	});
	
	//点击搜索查询数据
	$("#ensureauser").click(function(){
		$("#showAUserResult").empty();
		var account = $("#selectauser").val();
		if (account == null || account == "") {
			$("#warning").empty();
			$("#warning").append("ID不能为空！");
		}else {
			$("#warning").empty();
			$.ajax({
				url:"selectauser",
				type:"get",
				data:{"account":account},
				dataType:"json",
				success:function(result){
					if (result.code == 1) {
					var html = "<table style='width:80%;margin-top: 3%;'><tr align='center'>";
					html += 	"<td style='background: #EEE8AA;'>ID</td>";
					html += 	"<td style='background: #EEE8AA;'>账号</td>";
				 	html += 	"<td style='background: #EEE8AA;'>标识</td>";
				 	html += 	"<td style='background: #EEE8AA;'>昵称</td>";
				 	html += 	"<td style='background: #EEE8AA;'>邮箱</td>";
				 	html += 	"<td style='background: #EEE8AA;'>状态</td>";
				 	html += 	"<td style='background: #EEE8AA;'>创建时间</td>";
				 	html += 	"<td style='background: #EEE8AA;'>过期时间</td>";
				 	html += 	"<td style='background: #EEE8AA;'>删除标识</td>";
				 	html += 	"<td style='background: #EEE8AA;'>修改/删除</td>";
				 	html += 	"<td style='background: #EEE8AA;'>添加功能</td>";
				 	html += "</tr>";
				 	html += "<tr align='center'>";
				 	html += 	"<td>"+result.data.id+"</td>";
					html += 	"<td>"+result.data.account+"</td>";
					html += 	"<td>"+result.data.identity+"</td>";
					html += 	"<td>"+result.data.nickname+"</td>";
					html += 	"<td>"+result.data.email+"</td>";
					html += 	"<td>"+result.data.status+"</td>";
					html += 	"<td>"+result.data.createTime+"</td>";
					html += 	"<td>"+result.data.expireTime+"</td>";
					html += 	"<td>"+result.data.deleteFlag+"</td>";
					html += 	"<td><button id='updateauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>修改</button>";
					html += 	"<button id='deleteauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>删除</button></td>";
					html += 	"<td><button id='addgroup' class='btn btn-sm btn-info' style='margin-right: 1%;'>组</button>";
					html += 	"<button id='addrole' class='btn btn-sm btn-info' style='margin-right: 1%;'>角色</button>";
					html += 	"<button  class='btn btn-sm btn-info addpermissions' style='margin-right: 1%;'>资源</button></td>";
					html += "</tr>";
					html += "</table>";
					$("#showAUserResult").append(html);
					}else {
						$("#warning").append("未查到数据！");
					}
				}
					
			});
		}
	});
	
	//点击增加显示页面	
	$("#showaddauser").click(function(){
		hideAll();
		$("#addwarning").empty();
		$("#showaddauserpage").show();
		$("#showAUserResult").empty();
		 $("#addwarning").append("*为必填内容！");
	});
	//点击增加页面确定	
	$("#addauser").click(function(){
		var account = $("#setaccount").val().trim();
		var password = $("#setpassword").val().trim();
		var confirmpassword = $("#confirmpassword").val().trim();
		var nickname = $("#nickname").val().trim();
		var email = $("#email").val().trim();
		var identity = $("#identity").val().trim();
		var status = $("#status").val().trim();	
		var expireTime = $("#expireTime").val().trim();
		if (account == null || account == "") {
			$("#addwarning").empty();
			 $("#addwarning").append("账号不能为空！");
		}else if (password == null || password == "" ) {
			$("#addwarning").empty();
			$("#addwarning").append("请输入密码！");
		}else if( password.match(new RegExp("^[a-zA-Z][a-zA-Z0-9_]{5，15}")) || password.length < 6 || password.length > 16){
			$("#addwarning").empty();
			 $("#addwarning").append("密码为6至16位的字母及数字组成！");
		}else if (confirmpassword == null || confirmpassword == "" ) {
			$("#addwarning").empty();
			$("#addwarning").append("请输入确认密码！");
		}else if (password != confirmpassword) {
			$("#addwarning").empty();
			 $("#addwarning").append("两次输入的密码不相同！");
		}else {
			$("#addwarning").empty();
			 $("#addwarning").append("正在全速处理中····");
			 $.ajax({
					url:"addauser",
					type:"post",
					dataType:"json",
					data:{"account":account,"password":password},
					success:function(result){
						if (result.code == 1) {
							$("#addwarning").empty();
							 $("#addwarning").append(result.data);
						}else {
							$("#addwarning").empty();
							$("#addwarning").append("账号已存在！");
						}
				}
			});
		}
	});

	//点击增加组内部提交按钮
	$("#showAUserResult").on("click","#addauseragroup",function(){
			var userid = $("#addul ul").attr("id");
			var groupUL = document.getElementById("addul");
			var groups =  $("#addul ul li input:checked");
			var groupid =userid;
			$.each(groups,function(index,group){
				var s = $(group).parent().attr("id");
				groupid += ","+s;
			})
			$.ajax({
			url:"insertgroup",
			type:"post",
			dataType:"json",
			data:{"groupid":groupid},
			success:function(result){
				if (result.code == 1) {
					alert("添加成功！");
				}else {
					alert("添加失败！");
				}
			}
			});
			//关闭DIV
			document.getElementById('resourceTreeDiv').style.display='none';
			document.getElementById('resourceTreeFade').style.display='none';
			loadingAuser();
	});
	//增加组
	$("#showAUserResult").on("click","#addgroup",function(){
		var id= $(this).parent().parent().find("td").eq(0).html();
		$("#showAUserResult").empty();
		$.ajax({
			url:"selectgroup",
			type:"get",
			dataType:"json",
			success:function(result){
				if (result.code == 1) {
				var ul ="<ul id="+id+">";
					for (var i = 0; i < result.data.length; i++) {
						ul += "<li id="+result.data[i].id+"><input type='checkbox'class='read'>"+result.data[i].name+"</li>";
					}
					ul +="</ul>";
					$("#addul").append(ul);	
				}else {
					$("#addul").append("请刷新页面！");	
				}
			}
		});
		var html = "<div id='resourceTreeDiv' class='resource_link_white_content'>";
			html +="<div class='styleWhite'><button class='btn btn-sm btn-info' id='closeResourceTreeDiv' style='margin-bottom: 10px; margin-left: 5%;'>关闭</button></div>";
			html +="<div id='addul'></div>";
			html +="<button class='btn btn-sm btn-info pull-right' id='addauseragroup' style='margin-top: 10px; margin-right: 5%'>提交</button>";
			html +="</div>";
			html +="<div id='resourceTreeFade' class='resource_black_overlay'></div>";
			$("#showAUserResult").append(html);	
			showRoleTreeDIV();
			//关闭DIV
			$("#closeResourceTreeDiv").click(function(){
				document.getElementById('resourceTreeDiv').style.display='none';
				document.getElementById('resourceTreeFade').style.display='none';
				loadingAuser();
			});
	});
	
	//点击增加角色内部提交按钮
	$("#showAUserResult").on("click","#addauserarole",function(){
			var userid = $("#addul ul").attr("id");
			var roles =  $("#addul ul li input:checked");
			var roleid = userid;
			$.each(roles,function(index,role){
				var s = $(role).parent().attr("id");
				roleid += ","+s;
			})
			$.ajax({
			url:"insertrole",
			type:"post",
			dataType:"json",
			data:{"roleid":roleid},
			success:function(result){
				if (result.code == 1) {
					alert("添加成功！");
				}else {
					alert("添加失败！");
				}
			}
			});
			//关闭DIV
			document.getElementById('resourceTreeDiv').style.display='none';
			document.getElementById('resourceTreeFade').style.display='none';
			loadingAuser();
	});
	//增加角色
	$("#showAUserResult").on("click","#addrole",function(){
		var id= $(this).parent().parent().find("td").eq(0).html();
		$("#showAUserResult").empty();
		$.ajax({
			url:"selectrole",
			type:"get",
			dataType:"json",
			success:function(result){
				if (result.code == 1) {
				var ul ="<ul id="+id+">";
					for (var i = 0; i < result.data.length; i++) {
						ul += "<li id="+result.data[i].roleid+"><input type='checkbox'class='read'>"+result.data[i].name+"</li>";
					}
					ul +="</ul>";
					$("#addul").append(ul);	
				}else {
					$("#addul").append("请刷新页面！");	
				}
			}
		});
		var html = "<div id='resourceTreeDiv' class='resource_link_white_content'>";
			html +="<div class='styleWhite'><button class='btn btn-sm btn-info' id='closeResourceTreeDiv' style='margin-bottom: 10px; margin-left: 5%;'>关闭</button></div>";
			html +="<div id='addul'></div>";
			html +="<button class='btn btn-sm btn-info pull-right' id='addauserarole' style='margin-top: 10px; margin-right: 5%'>提交</button>";
			html +="</div>";
			html +="<div id='resourceTreeFade' class='resource_black_overlay'></div>";
			$("#showAUserResult").append(html);	
			showRoleTreeDIV();
			//关闭DIV
			$("#closeResourceTreeDiv").click(function(){
				document.getElementById('resourceTreeDiv').style.display='none';
				document.getElementById('resourceTreeFade').style.display='none';
				loadingAuser();
			});
	});
	
	//显示DIV
	function showRoleTreeDIV(){
		document.getElementById('resourceTreeDiv').style.display='block';
		document.getElementById('resourceTreeFade').style.display='block';
	}
	
	//点击删除按钮删除
	$("#showAUserResult").on("click","#deleteauser",function(){
		var id= $(this).parent().parent().find("td").eq(0).html();
		$.ajax({
			url:"deleteauser",
			type:"get",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if (result.code == 1) {
					alert(result.data);
				}else {
					alert("请刷新页面重试！");
				}
			}
		});
	});
	
	//点击修改按钮修改
	var id=null;
	$("#showAUserResult").on("click","#updateauser",function(){
		hideAll();
		$("#addwarning").empty();
		$("#showaddauserpage").show();
		$("#button").empty();
		var button ="<button id='updateausers' class='btn btn-sm btn-info' value='1'>确定</button>";
		$("#button").append(button);
		$("#showAUserResult").empty();
		 id= $(this).parent().parent().find("td").eq(0).html();
		var account= $(this).parent().parent().find("td").eq(1).html();
		var identity= $(this).parent().parent().find("td").eq(2).html();
		var nickname= $(this).parent().parent().find("td").eq(3).html();
		var email= $(this).parent().parent().find("td").eq(4).html();
		var status= $(this).parent().parent().find("td").eq(5).html();
		var createTime= $(this).parent().parent().find("td").eq(6).html();
		var expireTime= $(this).parent().parent().find("td").eq(7).html();
		var deleteFlag= $(this).parent().parent().find("td").eq(8).html();
		if (account != "undefined") {
			$("#setaccount").val(account);
		}
		if (identity != "undefined") {
			$("#identity").val(identity);
		}
		if (nickname != "undefined") {
			$("#nickname").val(nickname);
		}
		if (email != "undefined") {
			$("#email").val(email);
		}
		if (status != "undefined") {
			$("#status").val(status);
		}
		if (createTime != "undefined") {
			$("#createTime").val(createTime);
		}
		if (expireTime != "undefined") {
			$("#expireTime").val(expireTime);
		}
		if (deleteFlag != "undefined") {
			$("#deleteFlag").val(deleteFlag);
		}
	});
	
	//点击修改页面确定按钮
	$("#button").on("click","#updateausers",function(){
		var account = $("#setaccount").val().trim();
		var password = $("#setpassword").val().trim();
		var confirmpassword = $("#confirmpassword").val().trim();
		var nickname = $("#nickname").val().trim();
		var email = $("#email").val().trim();
		var identity = $("#identity").val().trim();
		var status = $("#status").val().trim();	
		var expireTime = $("#expireTime").val().trim();
		if (account == null || account == "") {
			$("#addwarning").empty();
			 $("#addwarning").append("账号不能为空！");
		}else if (password == null || password == "" ) {
			$("#addwarning").empty();
			$("#addwarning").append("请输入密码！");
		}else if( password.match(new RegExp("^[a-zA-Z][a-zA-Z0-9_]{5，15}")) || password.length < 6 || password.length > 16){
			$("#addwarning").empty();
			 $("#addwarning").append("密码为6至16位的字母及数字组成！");
		}else if (confirmpassword == null || confirmpassword == "" ) {
			$("#addwarning").empty();
			$("#addwarning").append("请输入确认密码！");
		}else if (password != confirmpassword) {
			$("#addwarning").empty();
			 $("#addwarning").append("两次输入的密码不相同！");
		}else {
			$("#addwarning").empty();
			 $("#addwarning").append("正在全速处理中····");
			 $.ajax({
					url:"updateauser",
					type:"post",
					dataType:"json",
					data:{"account":account,"password":password,"id":id},
					success:function(result){
						if (result.code == 1) {
							$("#addwarning").empty();
							 $("#addwarning").append(result.data);
						}else {
							$("#addwarning").empty();
							$("#addwarning").append("更新失败！");
						}
				}
			});
		}
	});
	
	$("#addgroup").click(function(){
		hideAll();
		$("#showAUserResult").empty();
		$.ajax({
			url:"addgroup",
			type:"get",
			dataType:"json",
			success:function(result){
				
			}
		});
	});
	
	//点击下一页	
	$("#nextpage").click(function(){
	});
	
	$("body").on("click",".addpermissions",function(){
		var tid = $(this).parent().parent().find("td").eq(0).html();
		$("#resourceTreeUl").html("");
		getResource(0,tid);
		showResourceTreeDIV();
})

});

//enter 控件
function EnterPressEnsure(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("ensure").click(); 
	} 
}; 
