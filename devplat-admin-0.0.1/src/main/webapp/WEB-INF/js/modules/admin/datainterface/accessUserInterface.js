var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var update_id;

$(function(){
	//加载分页数据
	pagez(1,10);
	//分页查询
	$("#Accessendpage").click(function(){
		var page = $(this).val();
		pagez(page,10);
	})
	$("#Accesshomepage").click(function(){
		var page = $(this).val();
		pagez(page,10);
	})
	$("#Accesslastpage").click(function(){
		var nowPage = $(this).val();
		var homePage = $("#Accesshomepage").val();
		if(nowPage == homePage){
			return;
		}
		var page = parseInt(nowPage) - parseInt(1);
		pagez(page,10);
	})
	$("#Accesscenextpage").click(function(){
		var nowPage = $(this).val();
		var endPage = $("#Accessendpage").val();
		if (nowPage >= endPage){
			return;
		}
		var page = parseInt(nowPage) + parseInt(1);
		pagez(page,10);
	})
	//修改
	$("#Pagination").on("click",".UserInterface_updata",function(){
		 update_id=$(this).parent().parent().find("td").eq(0).html();
		$.ajax({
			dataType:"json",
			type:"get",
			url:projectName+"/AccessUserInterface"+"/QueryId/"+update_id,    ///AccessUserInterface/QueryId/{id}
			success:function(result){
				console.log(result);
				$.each(result.data,function(x,y){
					document.getElementById("update_id").value = result.data.id;
					document.getElementById("update_account").value = result.data.account;
					document.getElementById("update_interfaceId").value = result.data.interfaceId;					
				})
			}
		})
		updata();
		$("#Colsebtn").click(function(){
			document.getElementById('updata').style.display='none';
			document.getElementById('addLinkAddressFade').style.display='none';
		});
	});
	$("#Suerbtn").click(function(){

		var ss= $('#sid option:selected').val();
		$.ajax({
			dataType:"json",
			type:"get",
			url:projectName+"/AccessUserInterface/"+ss+"/"+update_id+"/Interface",				//  /AccessUserInterface/{id}/Number/Interface
			success:function(result){
				console.log(result);
				if(result.code==1){
					swal("GOOD!", "修改成功！", "success");
					pagez(1,10);
					document.getElementById('updata').style.display='none';
					document.getElementById('addLinkAddressFade').style.display='none';
				}
				if(result.code==2){
					swal("ERROR!", "修改失败！", "error");
					pagez(1,10);
					document.getElementById('updata').style.display='none';
					document.getElementById('addLinkAddressFade').style.display='none';
				}
			}
		})
	})
	//删除
	$("#Pagination").on("click",".UserInterface_examine",function(){
		var Dete=$(this).parent().parent().find("td").eq(0).html();
		var conn;
		 conn=confirm("你确定删除吗？这是不可逆转的操作");
		 if(conn==true){
			 $.ajax({
				 dataType:"json",
				 type:"get",
				 url:projectName+"/AccessUserInterface/RemoveUser/"+Dete,
				 success:function(result){
					 console.log(result);
					 if(result.code==1){
						 swal("GOOD!", "删除成功！", "success");
						 pagez(1,10);
					 }
					 if(result.code==2){
						 swal("ERROR!", "删除失败！", "error");
						 pagez(1,10);
					 }
				 }
			 })
		 }
		
	})
	//新增用户
	$("#User_face").click(function(){
		$.ajax({
			dataType:"json",
			type:"get",
			url:projectName+"/AccessUserInterface/userAccount",
			success:function(result){
				console.log(result);
				var key="";
				$.each(result.data,function(t,y){
					key=key+"<option>"+y.account+"</option>";
				})
				key= key+"</option>"
				$("#add_user").append(key);
			}
		})
		$.ajax({
			dataType:"json",
			type:"get",
			url:projectName+"/AccessUserInterface/hickey",
			success:function(result){
				console.log(result);
				var hickey="";
				$.each(result.data,function(t,x){
					hickey=hickey+"<option>"+x.id+"</option>";
				})
				hickey= hickey+"</option>"
				$("#add_face").append(hickey);
			}
		})
		
		add_user();
		$("#Colsebtn_addUser").click(function(){
			document.getElementById('addUser_face').style.display='none';
			document.getElementById('addLinkAddressFade').style.display='none';
		})
	})
	//确定整加账号
	$("#Suerbtn_addUser").click(function(){
		var zhanghao= $('#add_user option:selected').val();
		var jiekou =$('#add_face option:selected').val();
		var shoufei=$('#add_chargeFlag option:selected').val();
		$.ajax({
			dataType:"json",
			type:"get",
			url:projectName+"/AccessUserInterface/"+zhanghao+"/"+jiekou+"/"+shoufei,   //	/AccessUserInterface/{account}/{interfaceid}/{chargeFla}
			success:function(result){
				if(result.code==1){
					 swal("GOOD!", "新增成功！", "success");
					 pagez(1,10);
					 document.getElementById('addUser_face').style.display='none';
					 document.getElementById('addLinkAddressFade').style.display='none';
				}
				if(result.code==2){
					swal("ERROR!", "新增失败！", "error");
					 pagez(1,10);
					 document.getElementById('addUser_face').style.display='none';
					document.getElementById('addLinkAddressFade').style.display='none';
				}
			}
		})
	})
	
});
function pagez(page,pageSize){
	$.ajax({
		dataType:"json",
		type:"get",
		url:projectName+"/AccessUserInterface/Pagination",
		data:{"page":page,"pageSize":pageSize},
		success:function(result){
			$(".Pagination_id").empty();
			console.log(result);
			var key="<table class='table table-striped table-bordered' style='text-align: center;'><tr>";
			var value="</tr>";
			key=key+"<td width='200px'>"+"序号"+"</td>"+
				"<td width='200'>"+"账号"+"</td>"+
				"<td width='200'>"+"接口"+"</td>"+
				"<td width='200'>"+"是否收费"+"</td>"+
				"<td width='600'colspan='3'>"+"权限"+"</td>";
			$.each(result.data.data,function(index,counts){
			if(counts.chargeFlag==true){
				value=value+"<td>"+counts.id+"</td>"+
				"<td>"+counts.account+"</td>"+
				"<td>"+counts.interfaceId+"</td>"+
				"<td>"+"收费"+"</td>"+
				"<td>"+"<input type='button' value='修改' class='btn btn-sm btn-info UserInterface_updata' id='butns'/>"+"</td>"+
				"<td>"+"<input type='button' value='查看' class='btn btn-sm btn-info UserInterface_examine' id='butns'/>"+"</td>"+
				"<td>"+"<input type='button' value='删除' class='btn btn-sm btn-info UserInterface_examine' id='butns'/>"+"</td></tr>";
			}else{
				value=value+"<td>"+counts.id+"</td>"+
				"<td>"+counts.account+"</td>"+
				"<td>"+counts.interfaceId+"</td>"+
				"<td>"+"未收费"+"</td>"+
				"<td>"+"<input type='button' value='修改' class='btn btn-sm btn-info UserInterface_updata' id='butns'/>"+"</td>"+
				"<td>"+"<input type='button' value='查看' class='btn btn-sm btn-info UserInterface_examine' id='butns'/>"+"</td>"+
				"<td>"+"<input type='button' value='删除' class='btn btn-sm btn-info UserInterface_examine' id='butns'/>"+"</td></tr>";
			}
			})
			$("#Accessendpage").val(result.data.pageCount);
			$("#Accesslastpage").val(result.data.pageNum);
			$("#Accesscenextpage").val(result.data.pageNum);
			key= key + "</tr>";
			value= value + "</tr></table>"
			$(".Pagination_id").append(key+value);
		}
	})
}
//修改框的隐藏显示
function updata(){
	document.getElementById('updata').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}
//增加的隐藏显示
function add_user(){
	document.getElementById('addUser_face').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}

