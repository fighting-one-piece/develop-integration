$(function() {
	$.ajax({
		type : "get",
		url : "switch/findAll",
		dataType : "json",
		success : function(result) {
			$("#GridView").empty();
			if(result.code==1){
				for (var i = 0; i < result.data.length; i++) {
					var keyStr = "<tr id="+i+"><td style='display:none;'>"+result.data[i].id+"</td><td class='col-md-1'>"+result.data[i].switch_identity+"</td><td class='col-md-1'>"+result.data[i].switch_name+"</td><td class='col-md-1'>"+result.data[i].swith_desc+"</td><td class='col-md-1'>"+result.data[i].status+"</td><td class='col-md-2' style='text-align:center;'><button class='updataBtn btn btn-info' data-dismiss='modal' style='margin-right:5px;'data-toggle='modal' data-target='#updata'>修改</button><button  class='deleteBtn btn btn-info' data-dismiss='modal' style='margin-left: 27px;'>删除</button></td></tr>";
					$("#GridView").append(keyStr);
				}
			}
		},
		error : function() {
			swal("操作失败!");
		}
	})
	
	//添加接口
	$("#addSwitch").click(function(){
		var identity= $("#identity").val();
		var name = $("#name").val();
		var desc = $("#desc").val();
		var status =$("#testSelect").val();
		$.ajax({
			type : "get",
			url : "switch/findAll",
			dataType : "json",
			success : function(result) {
				$("#GridView").empty();
				if(result.code==1){
					for (var i = 0; i < result.data.length; i++) {
						var biaoshi = result.data[i].switch_identity;
						if(identity==biaoshi){
							swal("该标识已有!");
							$.ajax({
								type : "get",
								url : "switch/findAll",
								dataType : "json",
								success : function(result) {
									$("#GridView").empty();
									if(result.code==1){
										for (var i = 0; i < result.data.length; i++) {
											var keyStr = "<tr id="+i+"><td style='display:none;'>"+result.data[i].id+"</td><td class='col-md-1'>"+result.data[i].switch_identity+"</td><td class='col-md-1'>"+result.data[i].switch_name+"</td><td class='col-md-1'>"+result.data[i].swith_desc+"</td><td class='col-md-1'>"+result.data[i].status+"</td><td class='col-md-2'><button class='updataBtn btn btn-info' data-dismiss='modal' style='margin-right:5px;'data-toggle='modal' data-target='#updata'>修改</button><button  class='deleteBtn btn btn-info' data-dismiss='modal'>删除</button></td></tr>";
											$("#GridView").append(keyStr);
										}
										$("#identity").attr("value",'');
										$("#name").attr("value",'');
										$("#desc").attr("value",'');
									}
								}
							})
							return;
						}
					}
					if(identity==""||identity==undefined||identity==null){
						swal("标识不能为空!");
						shuaxin();
					}else if (name==""||name==undefined||name==null) {
						swal("名称不能为空!");
						shuaxin();
					}else if (desc==""||desc==undefined||desc==null) {
						swal("描述不能为空!");
						shuaxin();
					}else if (status==""||status==undefined||status==null) {
						swal("状态不能为空!");
						shuaxin();
					}else{
						$.ajax({
							type : "post",
							url : "switch/saveSwitch",
							dataType : "json",
							data:{"switch_identity":identity,"switch_name":name,"swith_desc":desc,"status":status},
							success : function(result) {
								if(result.code==1){
									swal("添加成功！");
									$.ajax({
										type : "get",
										url : "switch/findAll",
										dataType : "json",
										success : function(result) {
											$("#GridView").empty();
											if(result.code==1){
												for (var i = 0; i < result.data.length; i++) {
													var keyStr = "<tr id="+i+"><td style='display:none;'>"+result.data[i].id+"</td><td class='col-md-1'>"+result.data[i].switch_identity+"</td><td class='col-md-1'>"+result.data[i].switch_name+"</td><td class='col-md-1'>"+result.data[i].swith_desc+"</td><td class='col-md-1'>"+result.data[i].status+"</td><td class='col-md-2'><button class='updataBtn btn btn-info' data-dismiss='modal' style='margin-right:5px;'data-toggle='modal' data-target='#updata'>修改</button><button  class='deleteBtn btn btn-info' data-dismiss='modal'>删除</button></td></tr>";
													$("#GridView").append(keyStr);
												}
												$("#identity").attr("value",'');
												$("#name").attr("value",'');
												$("#desc").attr("value",'');
											}
										}
									})
								}
							},
							error : function() {
								swal("系统错误！");
							}
						})	
					}
				}
			},
			error : function() {
				swal("系统错误!");
			}
		})
	})
	
	//删除
	$(".deleteBtn").live("click",function(){
		var identity = $(this).parent().parent().find("td:eq(1)").html();
		var id = $(this).parent().parent().attr("id")
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
//				console.log("删除OK")
				$.ajax({
					url:"switch/deleteSwitch",
					type:"post",
					dataType:"json",
					data:{"identity":identity},
					success:function(result){
						if(result.code == 1){
							swal("操作成功!"); 
							$("tr[id="+id+"]").remove();
						}else{
							swal("操作失败!");
						}
					},
					error:function(){
						swal("操作失败!");
					}
				})
			}
		})
	})
	
	//修改
	$(".updataBtn").live("click",function(){
		var id = $(this).parent().parent().find("td:eq(0)").html();
		var identity = $(this).parent().parent().find("td:eq(1)").html();
		var name = $(this).parent().parent().find("td:eq(2)").html();
		var desc = $(this).parent().parent().find("td:eq(3)").html();
		var status = $(this).parent().parent().find("td:eq(4)").html();
		$("#identityA").val(identity)
		$("#nameA").val(name);
		$("#descA").val(desc);
		$("#statusA").val(status);	
		$('#updataType').click(function() {
//			console.log(id+"------------")
			var identity= $("#identityA").val();
			var name= $("#nameA").val();
			var desc= $("#descA").val();
			var status =$("#statusA").val();
			
			if(identity==""||identity==undefined||identity==null){
				swal("标识不能为空!");
			}else if (name==""||name==undefined||name==null) {
				swal("名称不能为空!");
			}else if (desc==""||desc==undefined||desc==null) {
				swal("描述不能为空!");
			}else if (status==""||status==undefined||status==null) {
				swal("状态不能为空!");
			}else{
				swal({
					title:"",  
					text:"确定修改？",  
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
							url:"switch/updateSwitch",
							type:"post",
							dataType:"json",
							data:{"id":id,"identity":identity,"name":name,"desc":desc,"status":status},
							success:function(result){
								if(result.code == 1){
									swal("操作成功!"); 
									$.ajax({
										type : "get",
										url : "switch/findAll",
										dataType : "json",
										success : function(result) {
											$("#GridView").empty();
											if(result.code==1){
												for (var i = 0; i < result.data.length; i++) {
													var keyStr = "<tr id="+i+"><td style='display:none;'>"+result.data[i].id+"</td><td class='col-md-1'>"+result.data[i].switch_identity+"</td><td class='col-md-1'>"+result.data[i].switch_name+"</td><td class='col-md-1'>"+result.data[i].swith_desc+"</td><td class='col-md-1'>"+result.data[i].status+"</td><td class='col-md-2'><button class='updataBtn btn btn-info' data-dismiss='modal' style='margin-right:5px;'data-toggle='modal' data-target='#updata'>修改</button><button  class='deleteBtn btn btn-info' data-dismiss='modal'>删除</button></td></tr>";
													$("#GridView").append(keyStr);
												}
											}
										}
									})
								}else{
									swal("操作失败!");
								}
							},
							error:function(){
								swal("操作失败!");
							}
						})
					}
				})
			}
		})
	})
})

function shuaxin() {
	$.ajax({
		type : "get",
		url : "switch/findAll",
		dataType : "json",
		success : function(result) {
			$("#GridView").empty();
			if(result.code==1){
				for (var i = 0; i < result.data.length; i++) {
					var keyStr = "<tr id="+i+"><td style='display:none;'>"+result.data[i].id+"</td><td class='col-md-1'>"+result.data[i].switch_identity+"</td><td class='col-md-1'>"+result.data[i].switch_name+"</td><td class='col-md-1'>"+result.data[i].swith_desc+"</td><td class='col-md-1'>"+result.data[i].status+"</td><td class='col-md-2'><button class='updataBtn btn btn-info' data-dismiss='modal' style='margin-right:5px;'data-toggle='modal' data-target='#updata'>修改</button><button  class='deleteBtn btn btn-info' data-dismiss='modal'>删除</button></td></tr>";
					$("#GridView").append(keyStr);
				}
			}
		}
	})
}