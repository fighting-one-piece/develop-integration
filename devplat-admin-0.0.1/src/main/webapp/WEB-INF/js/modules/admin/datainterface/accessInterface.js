var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function(){
	findByPage(1,10);
	
	//分页查询
	$("#AccessInterfaceendpage").click(function(){
		var page = $(this).val();
		findByPage(page,10);
	})
	$("#AccessInterfacehomepage").click(function(){
		var page = $(this).val();
		findByPage(page,10);
	})
	$("#AccessInterfacelastpage").click(function(){
		var nowPage = $(this).val();
		var homePage = $("#AccessInterfacehomepage").val();
		if(nowPage == homePage){
			return;
		}
		var page = parseInt(nowPage) - parseInt(1);
		findByPage(page,10);
	})
	$("#AccessInterfacenextpage").click(function(){
		var nowPage = $(this).val();
		var endPage = $("#AccessInterfaceendpage").val();
		if (nowPage >= endPage){
			return;
		}
		var page = parseInt(nowPage) + parseInt(1);
		findByPage(page,10);
	})
	
	//添加
	$("#submitAddAccessInterfaceDivBtn").click(function(){
		var url = $("#interfaceAddress").val().trim();
		var identity = $("#interfaceIdentity").val().trim();
		var money = $("#interfaceMoney").val().trim();
		if(url == "" || identity == "" || money == "" || url == null || identity == null || money == null){
			alert("请输入参数")
		} else {
			$.ajax({
				url:projectName+"/admin/accessInterface/add",
				data:{"url":url,"identity":identity,"money":money},
				dataType:"json",
				type:"post",
				success:function(result){
					if(result.code == 1){
						if(result.data == 1){
							swal("Updated! ", "添加成功！", "success");
						} else {
							swal("Error!", "添加失败", "error");
						}
					}
					$("#closeAddAccessInterfaceDivBtn").click();
					var page = $("#AccessInterfacelastpage").val();
					findByPage(page,10);
				}
				
			})
		}
	})
	
	
	$("#toAddAccessInterfaceBtn").click(function(){
		document.getElementById('addAccessInterfaceDiv').style.display='block';
		document.getElementById('addAccessInterfaceFade').style.display='block';
	})
	$("#closeAddAccessInterfaceDivBtn").click(function(){
		document.getElementById('addAccessInterfaceDiv').style.display='none';
		document.getElementById('addAccessInterfaceFade').style.display='none';
	})
	
	//停用
	$("#resultAccessInterfaceTb").on("click",".delete-accessUser",function(){
		var accessId = $(this).parent().parent().attr("id");
		swal({
			title:"",  
			text:"确定停用吗？",  
			type:"warning",  
			showCancelButton:"true",  
			showConfirmButton:"true",
			confirmButtonColor: "#DD6B55",
			confirmButtonText:"确定",  
			cancelButtonText:"取消"
		}).then(function(isConfirm){
			if(isConfirm == true){
				updateDeleteFlag(accessId,true);
			}
		});
	})
	//启用
	$("#resultAccessInterfaceTb").on("click",".enable-accessUser",function(){
		var accessId = $(this).parent().parent().attr("id");
		swal({
			title:"",  
			text:"确定启用吗？",  
			type:"warning",  
			showCancelButton:"true",  
			showConfirmButton:"true",
			confirmButtonColor: "#DD6B55",
			confirmButtonText:"确定",  
			cancelButtonText:"取消"
		}).then(function(isConfirm){
			if(isConfirm == true){
				updateDeleteFlag(accessId,false);
			}
		});
	})
	
	//修改
	$(document).on("click",".update-accessInterface",function(){
		var url = $(this).parent().parent().children().eq(1).html();
		var identity = $(this).parent().parent().children().eq(2).html();
		var money = $(this).parent().parent().children().eq(3).html()
		$("#updateinterfaceAddress").val(url);
		$("#updateinterfaceIdentity").val(identity);
		$("#updateinterfaceMoney").val(money);
		var id = $(this).parent().parent().children().eq(0).html();
		$("#submitupdateAccessInterfaceDivBtn").data("id",id);
		document.getElementById('updateAccessInterfaceDiv').style.display='block';
		document.getElementById('addAccessInterfaceFade').style.display='block';
	})
	$("#closeUpdateAccessInterfaceDivBtn").click(function(){
		document.getElementById('updateAccessInterfaceDiv').style.display='none';
		document.getElementById('addAccessInterfaceFade').style.display='none';
	});
	$("#submitupdateAccessInterfaceDivBtn").click(function(){
		var id = $("#submitupdateAccessInterfaceDivBtn").data("id");
		var url = $("#updateinterfaceAddress").val().trim();
		var identity = $("#updateinterfaceIdentity").val().trim();
		var money = $("#updateinterfaceMoney").val().trim();
		if(url == "" || identity == "" || money == "" || url == null || identity == null || money == null){
			alert("请输入参数")
		} else {
			$.ajax({
				url:projectName+"/admin/accessInterface/update",
				data:{"id":id,"url":url,"identity":identity,"money":money},
				dataType:"json",
				type:"post",
				success:function(result){
					if(result.code == 1){
						if(result.data == 1){
							swal("Updated! ", "修改成功！", "success");
						} else {
							swal("Error!", "添加失败", "error");
						}
					}
					$("#closeUpdateAccessInterfaceDivBtn").click();
					var page = $("#AccessInterfacelastpage").val();
					findByPage(page,10);
				}
				
			})
		}
	})
	
	function updateDeleteFlag(id,deleteFlag){
		$.ajax({
			type:"post",
			data:{"id":id,"deleteFlag":deleteFlag},
			url:projectName+"/admin/accessInterface/update/deleteFlag",
			dataType:"json",
			success:function(result){
				if(result.code == 1){
					//刷新 当前页
					if (result.data == 1){
						swal("Updated! ", "修改成功！", "success");
					} else {
						swal("Error!", "系统繁忙，请稍后再试！", "error");
					}
					var page = $("#AccessInterfacelastpage").val();
					findByPage(page,10);
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error");
				}
			}
		});
	}
	
	function findByPage(page,pageSize){
		$.ajax({
			type:"get",
			data:{"page":page,"pageSize":pageSize},
			dataType:"json",
			url:projectName+"/admin/accessInterface/find",
			success:function(result){
				if(result.code == 1){
					if(result.data.data){
						var str = "";
						$.each(result.data.data,function(index,accessInterface){
							var id = accessInterface.id;
							var identity = accessInterface.identity;
							var money = accessInterface.money;
							var deleteFlag = accessInterface.deleteFlag;
							var url = accessInterface.url;
							var status = "";
							if(deleteFlag){
								status = "停用"
								str += "<tr id='"+id+"'><td>"+id+"</td><td>"+url+"</td><td>"+identity+"</td><td>"+money+"</td><td>"+status+"</td><td><button class='btn btn-sm btn-info enable-accessUser'>启用</button> <button class='btn btn-sm btn-info update-accessInterface'>修改</button></td>";
							} else {
								status = "正常使用"
								str += "<tr id='"+id+"'><td>"+id+"</td><td>"+url+"</td><td>"+identity+"</td><td>"+money+"</td><td>"+status+"</td><td><button class='btn btn-sm btn-info delete-accessUser'>停用</button> <button class='btn btn-sm btn-info update-accessInterface'>修改</button></td>";
							}
							
						})
						$("#allAccessInterfaceTable").html("");
						$("#allAccessInterfaceTable").append(str);
					}
					$("#AccessInterfaceendpage").val(result.data.pageCount);
					$("#AccessInterfacelastpage").val(result.data.pageNum);
					$("#AccessInterfacenextpage").val(result.data.pageNum);
				} else {
					alert("系统错误")
				}
			}
		})
	}
})
