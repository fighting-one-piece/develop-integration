$(document).ready(function () {
	
	//隐藏多个界面
	function hideAll(){
		$("#showSelectauser").hide();
		$("#showaddauserpage").hide();
		$("#showupdateauserpage").hide();
		$("#showupdateauserpagep").hide();
		$("#turnPage").hide();
	};
	
	loadingAuser(1);
	//点击查看加载首页
	$("#showselectallauser").click(function(){
		hideAll();
		$("#showAUserResult").empty();
		loadingAuser(1);
	});
	function loadingAuser(index){
		hideAll();
		$("#showAUserResult").empty();
	$.ajax({
		url:"page",
		type:"get",
		data:{"index":index},
		dataType:"json",
		success:function(result){
			$("#endpage").val(result.data.pageCount);
			if (result.code == 1) {
				var data = result.data;
				
				var html = "<table style='width:80%;margin-top: 3%;' class='table table-striped table-bordered' id='selecttable'><thead style='text-align:center'><tr>";
					html += 	"<td>ID</td>";
				 	html += 	"<td>账号</td>";
				 	html += 	"<td>标识</td>";
				 	html += 	"<td>昵称</td>";
				 	html += 	"<td>邮箱</td>";
				 	html += 	"<td>状态</td>";
				 	html += 	"<td>创建时间</td>";
				 	html += 	"<td>过期时间</td>";
				 	html += 	"<td>删除标识</td>";
				 	html += 	"<td>查询总条数</td>";
				 	html += 	"<td>剩余条数</td>";
				 	html += 	"<td>修改/删除</td>";
				 	html += 	"<td>添加功能</td>";
				 	html += "</tr></thead>";
				for (var i = 0; i < data.aUser.length; i++) {
					if (data.aUser[i].identity == undefined) {
						data.aUser[i].identity = "";
					}
					if (data.aUser[i].nickname == undefined) {
						data.aUser[i].nickname = "";
					}
					if (data.aUser[i].email == undefined) {
						data.aUser[i].email = "";
					}
					if (data.aUser[i].status == undefined) {
						data.aUser[i].status = "";
					}
					if (data.aUser[i].expireTime == undefined) {
						data.aUser[i].expireTime = "";
					}
					var count = "";
					var remainingCount = "";
					if (data.aUser[i].accessUserControl.count){
						count = data.aUser[i].accessUserControl.count;
						remainingCount = data.aUser[i].accessUserControl.remainingCount
					} else {
						count = 0;
						remainingCount = 0;
					}
					html += "<tr align='center'>";
					html += 	"<td>"+data.aUser[i].id+"</td>";
					html += 	"<td>"+data.aUser[i].account+"</td>";
					html += 	"<td>"+data.aUser[i].identity+"</td>";
					html += 	"<td>"+data.aUser[i].nickname+"</td>";
					html += 	"<td>"+data.aUser[i].email+"</td>";
					html += 	"<td>"+data.aUser[i].status+"</td>";
					html += 	"<td>"+FormatDate(new Date(data.aUser[i].createTime))+"</td>";
					html += 	"<td>"+FormatDate(new Date(data.aUser[i].expireTime))+"</td>";
					html += 	"<td id='deleteFlag'>"+data.aUser[i].deleteFlag+"</td>";
					html += 	"<td>"+count+"</td>";
					html += 	"<td>"+remainingCount+"</td>";
					html += 	"<td><button class='btn btn-sm btn-info update-accessUserControl' style='margin-right: 1%;'>修改剩余条数</button><button id='updateauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>修改</button>";
					html +=		"<button id='deleteauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>删除</button></td>";
					html += 	"<td><button id='addgroup' class='btn btn-sm btn-info' style='margin-right: 1%;'>组</button>";
					html += 	"<button id='addrole' class='btn btn-sm btn-info' style='margin-right: 1%;'>角色</button>";
					html += 	"<button  class='btn btn-sm btn-info addpermissions' style='margin-right: 1%;'>资源</button></td>";
					html += "</tr>";
				}
					html += "</table>";
				$("#showAUserResult").append(html);
				if (result.data.pageCount > 10) {
					$("#turnPage").show();
				}
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
						if (result.data.nickname == undefined) {
							result.data.nickname = "";
						}
						if (result.data.email == undefined) {
							result.data.email = "";
						}
					var html = "<table style='width:80%;margin-top: 3%;' class='table table-striped table-bordered'><thead style='text-align:center'><tr>";
					html += 	"<td>ID</td>";
					html += 	"<td>账号</td>";
				 	html += 	"<td>标识</td>";
				 	html += 	"<td>昵称</td>";
				 	html += 	"<td>邮箱</td>";
				 	html += 	"<td>状态</td>";
				 	html += 	"<td>创建时间</td>";
				 	html += 	"<td>过期时间</td>";
				 	html += 	"<td>删除标识</td>";
				 	html += 	"<td>查询总条数</td>";
				 	html += 	"<td>剩余条数</td>";
				 	html += 	"<td>修改/删除</td>";
				 	html += 	"<td>添加功能</td>";
				 	html += "</tr></thead>";
				 	html += "<tr align='center'>";
				 	html += 	"<td>"+result.data.id+"</td>";
					html += 	"<td>"+result.data.account+"</td>";
					html += 	"<td>"+result.data.identity+"</td>";
					html += 	"<td>"+result.data.nickname+"</td>";
					html += 	"<td>"+result.data.email+"</td>";
					html += 	"<td>"+result.data.status+"</td>";
					html += 	"<td>"+FormatDate(new Date(result.data.createTime))+"</td>";
					html += 	"<td>"+FormatDate(new Date(result.data.expireTime))+"</td>";
					html += 	"<td>"+result.data.deleteFlag+"</td>";
					var count = "";
					var remainingCount = "";
					if (result.data.accessUserControl.count){
						count = result.data.accessUserControl.count;
						remainingCount = result.data.accessUserControl.remainingCount
					} else {
						count = 0;
						remainingCount = 0;
					}
					html += 	"<td>"+count+"</td>";
					html += 	"<td>"+remainingCount+"</td>";
					html += 	"<td><button class='btn btn-sm btn-info update-accessUserControl' style='margin-right: 1%;'>修改剩余条数</button><button id='updateauser' class='btn btn-sm btn-info' style='margin-right: 1%;'>修改</button>";
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
		$("#setaccount").val("");
		$("#setpassword").val("");
		$("#confirmpassword").val("");
		$("#nickname").val("");
		$("#email").val("");
		$("#expireTime").val("");
		$("#showaddauserpage").show();
		$("#showAUserResult").empty();
		 $("#addwarning").append("请保证填写的正确性！");
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
		var  expireTime = new Date( $("#expireTime").val().trim());
		if (status == "是" ) {
			status = "0";
		}else {
			status = "1";
		}
		if (nickname == null || nickname == "") {
			nickname = null;
		}
		if (expireTime == null || expireTime == "" || expireTime == "Invalid Date") {
			expireTime = FormatYear();
		}
		if (identity == null || identity == "") {
			identity = null;
		}
		if (account == null || account == "") {
			$("#addwarning").empty();
			 $("#addwarning").append("账号不能为空！");
		}else if (password == null || password == "" ) {
			$("#addwarning").empty();
			$("#addwarning").append("请输入密码！");
		}else if(password.match(new RegExp("^[a-zA-Z][a-zA-Z0-9_]{5，15}")) || password.length < 6 || password.length > 16){
			$("#addwarning").empty();
			 $("#addwarning").append("密码为6至16位的字母及数字组成！");
		}else if (confirmpassword == null || confirmpassword == "" ) {
			$("#addwarning").empty();
			$("#addwarning").append("请输入确认密码！");
		}else if (password != confirmpassword) {
			$("#addwarning").empty();
			 $("#addwarning").append("两次输入的密码不相同！");
		}else if (email.match(new RegExp(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/))) {
			$("#addwarning").empty();
			$("#addwarning").append("邮箱 不正确！");
		}else{
			$("#addwarning").empty();
			$("#addwarning").append("正在全速处理中····");
			 swal({
					title:"",  
					text:"确定增加吗？",  
					type:"warning",  
					showCancelButton:"true",  
					showConfirmButton:"true",
					confirmButtonColor: "#DD6B55",
					confirmButtonText:"确定",  
					cancelButtonText:"取消",  
					animation:"slide-from-top"  
				}).then(function(isConfirm){
					if(isConfirm == true){
			 $.ajax({
				 url:"selectauser",
					type:"get",
					dataType:"json",
					data:{"account":account},
					success:function(result){
						if (result.code == 1) {
							$("#addwarning").empty();
							$("#addwarning").append("账号已存在！");
						}else {
							 $.ajax({
								url:"addauser",
								type:"post",
								dataType:"json",
								data:{"account":account,"password":password,"nickname":nickname,"email":email,"identity":identity,"status":status,"expireTime":expireTime},
								success:function(result){
									if (result.code == 1) {
										$("#addwarning").empty();
										 $("#addwarning").append(result.data);
									}else {
										$("#addwarning").empty();
										$("#addwarning").append("添加失败！");
									}
								},
								error:function(){
									$("#addwarning").empty();
									$("#addwarning").append("添加失败！");
								}
							 });
						}
				}
			});
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
			 swal({
					title:"",  
					text:"确定修改吗？",  
					type:"warning",  
					showCancelButton:"true",  
					showConfirmButton:"true",
					confirmButtonColor: "#DD6B55",
					confirmButtonText:"确定",  
					cancelButtonText:"取消",  
					animation:"slide-from-top"  
				}).then(function(isConfirm){
					if(isConfirm == true){
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
					}
					});
			//关闭DIV
			document.getElementById('resourceTreeDiv').style.display='none';
			document.getElementById('resourceTreeFade').style.display='none';
			loadingAuser(1);
	});
	//增加组
	$("#showAUserResult").on("click","#addgroup",function(){
		var userid= $(this).parent().parent().find("td").eq(0).html();
		$("#showAUserResult").empty();
		$.ajax({
			url:"selectgroup",
			type:"get",
			data:{"userid":userid},
			dataType:"json",
			success:function(result){
				if (result.code == 1) {
				var ul ="<ul id="+userid+">";
				for (var i = 0; i < result.data.group.length; i++) {
					var num = 0;
					for (var j = 0; j < result.data.usergroup.length; j++) {
						if (result.data.group[i].id == result.data.usergroup[j].agroup) {
							num = 1;
						}
					}
					if (num == 0) {
						ul += "<li id="+result.data.group[i].id+"><input type='checkbox' class='read'>"+result.data.group[i].name+"</li>";
					}else if(num == 1){
						ul += "<li id="+result.data.group[i].id+"><input type='checkbox' class='read' checked>"+result.data.group[i].name+"</li>";	
					}
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
				loadingAuser(1);
			});
	});
	
	
	//增加角色
	$("#showAUserResult").on("click","#addrole",function(){
		var userid= $(this).parent().parent().find("td").eq(0).html();
		$("#showAUserResult").empty();
		$.ajax({
			url:"selectrole",
			type:"get",
			data:{"userid":userid},
			dataType:"json",
			success:function(result){
				if (result.code == 1) {
				var ul ="<ul id="+userid+">";
				for (var i = 0; i < result.data.role.length; i++) {
					var num = 0;
					for (var j = 0; j < result.data.userrole.length; j++) {
						if (result.data.role[i].roleid == result.data.userrole[j].arole) {
							num = 1;
						}
					}
					if (num == 0) {
						ul += "<li id="+result.data.role[i].roleid+"><input type='checkbox'class='read'>"+result.data.role[i].name+"</li>";
					}else if (num == 1) {
						ul += "<li id="+result.data.role[i].roleid+"><input type='checkbox' class='read' checked>"+result.data.role[i].name+"</li>";
					}
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
				loadingAuser(1);
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
			 swal({
					title:"",  
					text:"确定修改吗？",  
					type:"warning",  
					showCancelButton:"true",  
					showConfirmButton:"true",
					confirmButtonColor: "#DD6B55",
					confirmButtonText:"确定",  
					cancelButtonText:"取消",  
					animation:"slide-from-top"  
				}).then(function(isConfirm){
					if(isConfirm == true){
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
					}
				});
			//关闭DIV
			document.getElementById('resourceTreeDiv').style.display='none';
			document.getElementById('resourceTreeFade').style.display='none';
			loadingAuser(1);
	});
	//显示DIV
	function showRoleTreeDIV(){
		document.getElementById('resourceTreeDiv').style.display='block';
		document.getElementById('resourceTreeFade').style.display='block';
	}
	
	//点击删除按钮删除
	$("#showAUserResult").on("click","#deleteauser",function(){
		var id= $(this).parent().parent().find("td").eq(0).html();
		 swal({
				title:"",  
				text:"确定删除吗？",  
				type:"warning",  
				showCancelButton:"true",  
				showConfirmButton:"true",
				confirmButtonColor: "#DD6B55",
				confirmButtonText:"确定",  
				cancelButtonText:"取消",  
				animation:"slide-from-top"  
			}).then(function(isConfirm){
				if(isConfirm == true){
		$.ajax({
			url:"deleteauser",
			type:"get",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				if (result.code == 1) {
					alert(result.data);
					loadingAuser(1);
				}else {
					alert("请刷新页面重试！");
				}
			}
		});
				}
			});
	});
	
	//点击修改按钮修改
	var id=null;
	$("#showAUserResult").on("click","#updateauser",function(){
		hideAll();
		$("#addwarningp").empty();
		$("#showupdateauserpagep").show();
		 id= $(this).parent().parent().find("td").eq(0).html();
		var account= $(this).parent().parent().find("td").eq(1).html();
		var identity= $(this).parent().parent().find("td").eq(2).html();
		var nickname= $(this).parent().parent().find("td").eq(3).html();
		var email= $(this).parent().parent().find("td").eq(4).html();
		var status= $(this).parent().parent().find("td").eq(5).html();
		var createTime= $(this).parent().parent().find("td").eq(6).html();
		var expireTime= $(this).parent().parent().find("td").eq(7).html();
		var deleteFlag= $(this).parent().parent().find("td").eq(8).html();
		$("#showAUserResult").empty();
		if (account != "undefined") {
			$("#setaccountp").val(account);
		}
		if (identity != "undefined") {
			$("#identityp").val(identity);
		}
		if (nickname != "undefined") {
			$("#nicknamep").val(nickname);
		}
		if (email != "undefined") {
			$("#emailp").val(email);
		}
		if (status != "undefined") {
			$("#statusp").val(status);
		}
		if (expireTime != "undefined") {
			$("#expireTimep").val(FormatDate(new Date(expireTime)));
		}
		if (deleteFlag != "undefined") {
			$("#deleteFlagp").val(deleteFlag);
		}
		$("#setpasswordp").val("");
		$("#confirmpasswordp").val("");
	});
	
	//点击修改页面确定按钮
	$("#buttonp").on("click","#updateausers",function(){
		var account = $("#setaccountp").val().trim();
		var password = $("#setpasswordp").val().trim();
		var confirmpassword = $("#confirmpasswordp").val().trim();
		var nickname = $("#nicknamep").val().trim();
		var email = $("#emailp").val().trim();
		var identity = $("#identityp").val().trim();
		var status = $("#statusp").val().trim();	
		var expireTime =new Date($("#expireTimep").val().trim());
		if (status == "是" ) {
			status = "0";
		}else {
			status = "1";
		}
		if (nickname == null || nickname == "") {
			nickname = null;
		}
		if (expireTime == null || expireTime == "" || expireTime == "Invalid Date") {
			expireTime = FormatYear();
		}
		if (identity == null || identity == "") {
			identity = null;
		}
		if (account == null || account == "") {
			$("#addwarningp").empty();
			 $("#addwarningp").append("账号不能为空！");
		}else if (password == null || password == "" ) {
			$("#addwarningp").empty();
			$("#addwarningp").append("请输入密码！");
		}else if( password.match(new RegExp("^[a-zA-Z][a-zA-Z0-9_]{5，15}")) || password.length < 6 || password.length > 16){
			$("#addwarningp").empty();
			 $("#addwarningp").append("密码为6至16位的字母及数字组成！");
		}else if (confirmpassword == null || confirmpassword == "" ) {
			$("#addwarningp").empty();
			$("#addwarningp").append("请输入确认密码！");
		}else if(password != confirmpassword) {
			$("#addwarningp").empty();
			 $("#addwarningp").append("两次输入的密码不相同！");
		}else if(email.match(new RegExp(/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/))) {
			$("#addwarningp").empty();
			 $("#addwarningp").append("邮箱不正确！");
		}else{
			$("#addwarningp").empty();
			 $("#addwarningp").append("正在全速处理中····");
			 swal({
					title:"",  
					text:"确定修改吗？",  
					type:"warning",  
					showCancelButton:"true",  
					showConfirmButton:"true",
					confirmButtonColor: "#DD6B55",
					confirmButtonText:"确定",  
					cancelButtonText:"取消",  
					animation:"slide-from-top"  
				}).then(function(isConfirm){
					if(isConfirm == true){
			 $.ajax({
					url:"updateauser",
					type:"post",
					dataType:"json",
					data:{"account":account,"password":password,"id":id,"nickname":nickname,"email":email,"identity":identity,"status":status,"expireTime":expireTime},
					success:function(result){
						if (result.code == 1) {
							$("#addwarningp").empty();
							 $("#addwarningp").append(result.data);
							 alert("修改成功！");
							 loadingAuser(1);
						}else {
							$("#addwarningp").empty();
							$("#addwarningp").append("更新失败！");
						}
				}
			});
			}
			});
		}
	});
	
	//首页
	$("#homepage").click(function(){
		var index =1;
		$("#homepage").val(index);
		loadingAuser(index);
	});
	
	
	
	//上一页
	$("#lastpage").click(function(){
			var index = parseInt($("#homepage").val())-1;
			if(index<=1){
				index=1;
			}
			$("#homepage").val(index);
			loadingAuser(index);
	});
	
	
	//下一页
	$("#nextpage").click(function(){
			var index = parseInt($("#homepage").val())+1;
			if(index >= $("#endpage").val()){
				index = $("#endpage").val();
			}
			$("#homepage").val(index);
			loadingAuser(index);
	});
	
	//末页
	$("#endpage").click(function(){
		var index = $("#endpage").val();
		$("#homepage").val(index);
		loadingAuser(index);
	});
	
	$("body").on("click",".addpermissions",function(){
		var tid = $(this).parent().parent().find("td").eq(0).html();
		$("#resourceTreeUl").html("");
		getResource(0,tid);
		showResourceTreeDIV();
	})
	//修改剩余条数
	$(document).on("click",".update-accessUserControl",function(){
		var account = $(this).parent().parent().children().eq(1).html();
		$("#submitupdateAccessUserControlBtn").data("account",account);
		document.getElementById('updateAccessUserControl').style.display='block';
		document.getElementById('resourceTreeFade').style.display='block';
	})
	$("#closeupdateAccessUserControlBtn").click(function(){
		$("#updateAccessUserControlWaring").html("")
		document.getElementById('updateAccessUserControl').style.display='none';
		document.getElementById('resourceTreeFade').style.display='none';
	})
	//增加减少剩余条数
	$("#submitupdateAccessUserControlBtn").click(function(){
		$("#updateAccessUserControlWaring").html("");
		var account = $(this).data("account");
		var num = $("#updateAccessUserControlCount").val().trim();
		var updateType = $("#chooseUpdateAccessUserControlTypeDiv input[name=chooseUpdateAccessControlType]:checked").val();
		if (num == ''){
			$("#updateAccessUserControlWaring").html("请输入增加/减少条数！")
		} else {
			if (judgeIsNum(num)){
				$.ajax({
					type:"post",
					data:{"changeCount":num,"type":updateType,"account":account},
					url:projectName+"/admin/accessUserControl/updateCount",
					dataType:"json",
					success:function(result){
						if(result.code == 1){
							$("#closeupdateAccessUserControlBtn").click();
							swal("Updated! ", "修改成功！", "success");
							$("#showselectallauser").click();
						} else {
							swal("Error!", "系统繁忙，请稍后再试！", "error");
						}
					}
				});
			} else {
				$("#updateAccessUserControlWaring").html("增加/减少条数必须为纯数字！")
			}
		}
	})
	
	//判断是否为纯数字
	function judgeIsNum (srt){  
        var pattern=/^\d+$/g;    
        var result= srt.match(pattern);//match 是匹配的意思   用正则表达式来匹配  
        if (result==null){  
            return false;  
        }else{  
            return true;  
        }  
    }   

});

function FormatDate (strTime) {
	var paddNum = function(num){
        num += "";
        return num.replace(/^(\d)$/,"0$1");
     }
    var date = new Date(strTime);
    return date.getFullYear()+"-"+paddNum(date.getMonth() + 1)+"-"+paddNum(date.getDate())+" "+paddNum(date.getHours())+":"+paddNum(date.getMinutes())+":"+paddNum(date.getSeconds());
}
function FormatYear () {
    var date = new Date();
    var dates = new  Date((date.getFullYear()+ 1)+"-"+date.getMonth() + 1+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
    return dates;
}
//enter 控件
function EnterPressEnsure(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("ensure").click(); 
	} 
}; 
