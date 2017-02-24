$(document).ready(function() {
	
	//查看
	$(".Pagination_id").on("click","#findAccessBtn",function(){
		var userInterfaceId = $(this).parent().parent().find("td").eq(0).html();
		$("#accessMoneyResult").empty();
		$.ajax({
			url:"access/findAccessMoney",
			type:"get",
			dataType:"json",
			data:{"userInterfaceId":userInterfaceId},
			success:function(result){
				console.log(result)
				if (result.code == 1) {
					var access = "<tr id='"+userInterfaceId+"'>";
						access += "<td  width='200'>ID</td>";
						access += "<td  width='200'>用户接口ID</td>";
						access += "<td  width='200'>响应代码</td>";
						access += "<td  width='200'>金额</td>";
						access += "<td  width='200'>是否删除</td>";
						access += "<td  width='200'>功能</td>";
						access += "<tr>";
						$.each(result.data,function(index,content) {
							access += "<tr>";
							access += "<td  width='200'>"+content.id+"</td>";
							access += "<td  width='200'>"+content.userInterfaceId+"</td>";
							access += "<td  width='200'>"+content.responseCode+"</td>";
							access += "<td  width='200'>"+content.money+"</td>";
							if (content.deleteFlag == false) {
								access += "<td  width='200'>使用中</td>";
								access += "<td  width='200'><button id='updateAccessMoney'>修改</button><button id='deleteAccessMoney'>删除</button></td>";
							}else {
								access += "<td  width='200'>已删除</td>";
								access += "<td  width='200'><button id='updateAccessMoney'>修改</button><button id='deleteAccessMoney'>开启</button></td>";
							}
							access += "<tr>";
						})
					$("#accessMoneyResult").append(access);
				}else {
					$("#accessMoneyResult").append("未找到相关数据");
				}
			}
		});
		findAccessMoney();
		$("#closeAccessMoney").click(function() {
			hide();
		});
	});
	
	
	function load(){
		var userInterfaceId = $("#accessMoneyResult tr").attr("id");
		$("#accessMoneyResult").empty();
		$.ajax({
			url:"access/findAccessMoney",
			type:"get",
			dataType:"json",
			data:{"userInterfaceId":userInterfaceId},
			success:function(result){
				if (result.code == 1) {
					var access = "<tr id='"+userInterfaceId+"'>";
						access += "<td  width='200'>ID</td>";
						access += "<td  width='200'>用户接口ID</td>";
						access += "<td  width='200'>响应代码</td>";
						access += "<td  width='200'>金额</td>";
						access += "<td  width='200'>是否删除</td>";
						access += "<td  width='200'>功能</td>";
						access += "<tr>";
						$.each(result.data,function(index,content) {
							access += "<tr>";
							access += "<td  width='200'>"+content.id+"</td>";
							access += "<td  width='200'>"+content.userInterfaceId+"</td>";
							access += "<td  width='200'>"+content.responseCode+"</td>";
							access += "<td  width='200'>"+content.money+"</td>";
							if (content.deleteFlag == false) {
								access += "<td  width='200'>使用中</td>";
								access += "<td  width='200'><button id='updateAccessMoney'>修改</button><button id='deleteAccessMoney'>删除</button></td>";
							}else {
								access += "<td  width='200'>已删除</td>";
								access += "<td  width='200'><button id='updateAccessMoney'>修改</button><button id='deleteAccessMoney'>开启</button></td>";
							}
							access += "<tr>";
						})
					$("#accessMoneyResult").append(access);
				}else {
					$("#accessMoneyResult").append("未找到相关数据");
				}
			}
		});
	};
		
	//添加
	$("#addAccessMoney").click(function() {
		hide();
		addAccessMoney();
		$("#closeAAccessMoney").click(function() {
			hide();
			findAccessMoney();
		});
	});
	//添加确定
	$("#addSureAccessMoney").click(function() {
		var userInterfaceId = $("#asetUserID").val();
		var responseCode = $("#asetRponeCode").val();
		var money = $("#asetMoney").val();
		console.log(userInterfaceId)
		console.log(responseCode)
		console.log(money)
		swal({
			title:"",  
			text:"确定添加吗？",  
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
			url:"access/addAccessMoney",
			type:"get",
			dataType:"json",
			data:{"userInterfaceId":userInterfaceId,"responseCode":responseCode,"money":money},
			success:function(result){
					if (result.code == 1) {
						alert("添加成功");
						hide();
						findAccessMoney();
						load();
					}else {
						alert("添加失败");
					}
			}
		});
		}
	});
	});
	//修改
	$("#accessMoneyResult").on("click","#updateAccessMoney",function(){
		var id = $(this).parent().parent().find("td").eq(0).html();
		var userInterfaceId = $(this).parent().parent().find("td").eq(1).html();
		var responseCode = $(this).parent().parent().find("td").eq(2).html();
		var money = $(this).parent().parent().find("td").eq(3).html();
		var deleteFlag = $(this).parent().parent().find("td").eq(4).html();
		$("#setID").html(id);
		$("#setUserID").html(userInterfaceId);
		$("#setRponeCode").val(responseCode);
		$("#setMoney").val(money);
		$("#setFlag").html(deleteFlag);
		hide();
		updateAccessMoney();
		$("#closeUAccessMoney").click(function() {
			hide();
			findAccessMoney();
		});
	});
	
	//修改确定
	$("#updateSureAccessMoney").click(function() {
		var id = $("#setID").html();
		var userInterfaceId = $("#setUserID").html();
		var responseCode = $("#setRponeCode").val();
		var money = $("#setMoney").val();
		swal({
			title:"",  
			text:"确定更改吗？",  
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
			url:"access/updateAccessMoney",
			type:"get",
			dataType:"json",
			data:{"id":id,"userInterfaceId":userInterfaceId,"responseCode":responseCode,"money":money},
			success:function(result){
					if (result.code == 1) {
						alert("修改成功");
						hide();
						findAccessMoney();
						load();
					}else {
						alert("修改失败");
					}
			}
		});
		}
	});
	});
	//删除
	$("#accessMoneyResult").on("click","#deleteAccessMoney",function(){
		var userInterfaceId= $(this).parent().parent().find("td").eq(1).html();
		var responseCode= $(this).parent().parent().find("td").eq(2).html();
		var deleteFlag = $(this).parent().parent().find("td").eq(4).html();
		if (deleteFlag == "使用中") {
			deleteFlag = false;
		}else if(deleteFlag == "已删除"){
			deleteFlag = true;
		}
		console.log(deleteFlag)
		swal({
			title:"",  
			text:"确定更新吗？",  
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
			url:"access/deleteAccessMoney",
			type:"get",
			dataType:"json",
			data:{"userInterfaceId":userInterfaceId,"responseCode":responseCode,"deleteFlag":deleteFlag},
			success:function(result){
					alert(result.data);
					load();
			}
		});
		}
	});
	});
	
});


function findAccessMoney(){
	document.getElementById('findAccessMoney').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}
function updateAccessMoney(){
	document.getElementById('updateAccessMoneys').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}
function addAccessMoney(){
	document.getElementById('addAccessMoneys').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}
function hide(){
	document.getElementById('addAccessMoneys').style.display='none';
	document.getElementById('findAccessMoney').style.display='none';
	document.getElementById('updateAccessMoneys').style.display='none';
	document.getElementById('addLinkAddressFade').style.display='none';
}