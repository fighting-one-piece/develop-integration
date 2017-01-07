var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var id;
var RolesDeid;
var userid;
var Character;
var findROleID;
//角色管理JS
$(function(){
	//显示全部的角色信息
	getALL();
	
	$("body").on("click",".addpermissions",function(){
    	var code = $(this).parent().parent().find("td").eq(0).html();
		$("#resourceTreeUl").html("");
		getResource(2,code);
		showResourceTreeDIV();
	});
	
	//新增角色
	$("#butns").click(function() {
	// 显示框体
	getaddUser();
	// 隐藏框
	$("#addColsebtn").click(function() {
		document.getElementById('added_user').style.display = 'none';
		document.getElementById('addLinkAddressFade').style.display='none';
	})
});
$("#addSuerbtn").click(function() {
	var added_name = document.getElementById("added_name").value;
	var added_desc = document.getElementById("added_desc").value;
	var added_roles = document.getElementById("added_roles").value;
	$.ajax({
		type : "get",
		url : projectName + "/getaddRole",
		dataType : "json",
		data : {
			"name" : added_name,
			"identity" : added_desc,
			"desc" : added_roles
		},
		success : function(result) {
			console.log(result);
			if (result.code == 2) {
				swal("OMD!", "有相同数据存在", "error");
			}
			if (result.code == 1) {
				swal("OMD!", "新增成功！", "success");
				getALL();
				// location.reload();
			}
		}
	})
});
	   
		$("#role_result_table").on("click",".role_updata",function(){
				//获取tr的id
					 id = $(this).parent().parent().find("td").eq(0).html();
					  $.ajax({
							type:"get",
							url:projectName+"/certains/"+id,
							dataType:"json",
							success:function(result){
								//console.log(result);
								if(result.code==2){
									swal("OMG!", "查询错误！", "error");
								}
								if(result.code==1){
									document.getElementById("role_name").value = result.data.name;
									document.getElementById("role_desc").value = result.data.identity;
									document.getElementById("role_roles").value = result.data.desc;
									
								}
								
								 }
					  })
					 showDIV();
					 $("#Colsebtn").click(function(){
							document.getElementById('ss').style.display='none';
							document.getElementById('addLinkAddressFade').style.display='none';
						});		
		 		})
									//点击确定传值修改
									$("#Suerbtn").click(function(){
										//获得参数
										var name =document.getElementById("role_name").value;
										console.log(name);
										var identity=document.getElementById("role_desc").value;
										console.log(identity);
										var desc=document.getElementById("role_roles").value;
										console.log(desc);
										var ur=projectName+"/updata"
										console.log(ur);
									  $.ajax({
										type:"get",
										url:ur, 
										data:{"Roleid":id,"name":name,"identity":identity,"desc":desc},
										dataType:"json",
										success:function(result){
											console.log(result);
											if(result.code==2){
												swal("OMG!", "数据重复！", "error");
											}
											if(result.code==1){
												swal("GOOD!", "修改成功！", "success");
												getALL();
											}
											//location.reload();
										}
									  })
									  
									})		
						 
				 //刪除					
				 $("#role_result_table").on("click",".role_delete",function(){
					 RolesDeid=$(this).parent().parent().find("td").eq(0).html();
					 console.log(RolesDeid);
					 var conn;
					 conn=confirm("你確定刪除嗎？這是不可逆轉的操作");
					 if(conn==true){
						 $.ajax({
							 type:"get",
							 url:projectName+"/Delet",
							 data:{"id":RolesDeid},
							 dataType:"json",
							 success:function(result){
								 console.log(result)
								 if(result.code==2){
									 swal("GOOD!", "刪除失敗！", "error");
								 }
								 if(result.code==1){
									 swal("GOOD!", "刪除成功！", "success");
									 getALL();
								 }
							 }
						 })
					 }else{
						 swal("GOOD!", "取消操作！", "error");
					 }
					 
				 })
				 
				 //给用户赋予赋予角色
				 $("#role_result_table").on("click",".butn",function(){ 
					 	//获取ID值,查询到关联表数据
					findROleID=$(this).parent().parent().find("td").eq(0).html();
					 console.log(findROleID);
					    //获取到角色的ID
						 $.ajax({
							 type:"get",
							 url:projectName+"/getalluers",
							 dataType:"json",
							 success:function(result){
								 console.log(result);
								 if(result.code==2){
									 swal("GOOD!", "查詢失敗！", "error");
								 }
								 if(result.code==1){
									 $(".User").empty();
									 var keys="<table class='table table-striped table-bordered'><tr>";
									 var valuest="<tr>";
									 keys+="<td width='400'>"+"序列"+"</td>"+
									 "<td width='400'>"+"账号"+"</td>"+
									 "<td width='400'>"+"用户标识"+"</td>"+
									 "<td width='400'>"+"用户昵称"+"</td>"+
									 "<td width='400'>"+"选择"+"</td>"
									 $.each(result.data,function(n,finduser){
										 valuest=valuest +"<td id='tb'>"+finduser.id+"</td>"+"<td>"+finduser.account+"</td>"+"<td>"+finduser.identity+"</td>"+"<td>"+finduser.nickname+"</td>"+
										 "<td width='300'><a class='find_User'  href='javascript:void(0)'>"+"<input id='a"+finduser.id+"' name='enjoy' style='width:30px;height:30px' type='checkbox'/>"+"</a></td></tr>"
									 }) 
									 keys= keys + "</tr>";
									 valuest= valuest + "</tr></table>"
									 $(".User").append(keys+valuest);
									 
									 var userROID="";
									 $.each(result.data,function(n,user){
										 userROID= userROID+ user.id+","
									 })
										$.ajax({
											type:"get",
											url:projectName+"/ROleIDS"+userROID+"/"+findROleID,
											dataType:"json",
											success:function(result){														
											console.log(result.data);
											//分割数据
											var strs= new Array();
											var one =new Array();
											var set="";
											$.each(result.data,function(n,x){
												//截取第一位  和最后一位
												var one1 =x.split(",")[0];
												var one2 =x.split(",")[2];
												//等值于1,判断打钩
												if(one2==1){												
												 $("#a"+one1+"").prop("checked",true);
												}
												
												})
												
											}
										})					
								 }	 	 
							 }
						 
						 })		 					 
						 //显示全部的用户数据
						 getAlluser();
						 $("#Colsebtun").click(function(){
							 document.getElementById('UserPresentation').style.display='none';
							 document.getElementById('addLinkAddressFade').style.display='none';
							 getALL();
						 })
						 
					 })
					 //点击获取信息
					 $("#Suerbtun").click(function(){
						 //获得状态
						 var addDelete=""; 
						 var Deleteadd="";
						 $("input[name=enjoy]").each(function() {  
							 if ($(this).attr("checked")) {  
								 addDelete += $(this).parent().parent().parent().find("td").eq(0).html()+",";  
							 }else{
								 Deleteadd += $(this).parent().parent().parent().find("td").eq(0).html()+",";
							 }  
						 });
						 //新增
						 $.ajax({
							 type:"get",
							 url:projectName+"/AddDelete/"+addDelete+"/"+findROleID,   
							 dataType:"json",
							 success:function(result){
								 if(result.code==1){
									 swal("GOOD!", "成功！", "success"); 
								 }
								 if(result.code==2){
									 swal("GOOD","失败！","error");
								 }
							 }
						 })
						 
						//删除
						 $.ajax({
							 type:"get",				
							 url:projectName+"/Deleteadd/"+Deleteadd+"/"+findROleID,	
							 dataType:"json",
							 success:function(result){
								 console.log(result);
								 if(result.code==1){
									 swal("GOOD!", "成功！", "success");
								 }
								 if(result.code==2){
									 swal("GOOD","失败！","error");
								 }
							 }
						 })
					 })	
				 })
				 				 	 	
		 	
function showDIV(){
	document.getElementById('ss').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}

//刷新方法
function getALL(){
	$.ajax({
		type:"get",
			url:projectName+"/role",
			dataType:"json",
			success:function(result){
				if(result.code==2){
					alert("查询错误")
				}
				if(result.code==1){
					$(".result").empty();
					var keySt = "<table class='table table-striped table-bordered'><tr>";
					var valueSt = "<tr>";
					keySt+="<td width='400'>"+"序列"+"</td>"+
					"<td width='400'>"+"姓名"+"</td>"+
					"<td width='400'>"+"标识"+"</td>"+
					"<td width='400'>"+"角色"+"</td>"+
					"<td width='400' colspan='4'>"+"权限"+"</td>"
						$.each(result.data,function(index,roleUser){	
							valueSt = valueSt + "<td>"+ roleUser.roleid+"</td>"+"<td>"+roleUser.name+"</td>"+"<td>"+roleUser.identity+"</td>"+
							"<td>"+roleUser.desc+"</td>"+
							"<td width='200'><input type='button' value='修改' class='btn btn-sm btn-info role_updata' id='butns'/></td>"+
							"<td width='200'><input type='button' class='btn btn-sm btn-info role_delete' value='删除' id='butns'></td>"+
							"<td width='200'><input type='button' class='btn btn-sm btn-info addpermissions' id='butns' value='授权'></td>"+
							"<td width='200'><input type='button' class='btn btn-sm btn-info butn' id='butns'value='赋予用户'></td></tr>";
							
									
						})
						keySt= keySt + "</tr>";
						valueSt= valueSt + "</tr></table>"
					    $(".result").append(keySt+valueSt);
						
				}
			}
		})
}

//顯示 全部的用戶
function getAlluser(){
	document.getElementById('UserPresentation').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}

//添加角色信息框体
function getaddUser(){
	document.getElementById('added_user').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}

//弹出下拉框
function getdown(){
	document.getElementById('UserRole').style.display='block';
}


	