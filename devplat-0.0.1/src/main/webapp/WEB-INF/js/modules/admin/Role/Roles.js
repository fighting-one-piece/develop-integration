var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var id;
var RolesDeid;
//角色管理JS
$(function(){
	//显示全部的角色信息
	getALL();
	   
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
						});
					 $("Suerbtn").click(function(){
						 
					 })
					 
			
		 })
									//点击确定传值修改
									$("#Suerbtn").click(function(){
										alert(id);
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
												swal("OMG!", "修改失败！", "error");
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
						 
						 swal("GOOD!", "刪除成功！", "success");
					 }else{
						 swal("GOOD!", "取消操作！", "error");
					 }
					 
				 })   
	$("body").on("click",".addpermissions",function(){
    	var code = $(this).parent().parent().find("td").eq(0).html();
		$("#resourceTreeUl").html("");
		getResource(2,code);
		showResourceTreeDIV();
	})	
});
//的框体的出现方法
function showDIV(){
	document.getElementById('ss').style.display='block';
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
					"<td width='400' colspan='3'>"+"权限"+"</td>"
						$.each(result.data,function(index,roleUser){	
							valueSt = valueSt + "<td>"+ roleUser.roleid+"</td>"+"<td>"+roleUser.name+"</td>"+"<td>"+roleUser.identity+"</td>"+
							"<td>"+roleUser.desc+"</td>"+
							"<td width='200'><a class='role_updata' href='javascript:void(0)'>"+"修改"+"</a></td>"+
							"<td width='200'><a class='role_delete' href='javascript:void(0)'>"+"删除"+"</a></td>"+
							"<td width='400'><a class='addpermissions' href='javascript:void(0)'>"+"授權"+"</a></td></tr>";
						})
						keySt= keySt + "</tr>";
						valueSt= valueSt + "</tr></table>"
					    $(".result").append(keySt+valueSt);
						
				}
			}
		})
}


	