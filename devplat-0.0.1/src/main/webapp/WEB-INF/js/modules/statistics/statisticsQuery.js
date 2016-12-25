$(function() {
	var url = "statistics/show";
	$.ajax({
		type : "get",
		url : url,
		dataType : "json",
		success : function(result) {
			$("#logistics").empty();
			if (result.code == 1) {
				$("#logistics").append(result.data.logistics);
				$("#account").append(result.data.account);
				$("#mailbox").append(result.data.mailbox);
				$("#car").append(result.data.car);
				$("#house").append(result.data.house);
				$("#business").append(
						result.data.business);
				$("#student").append(result.data.student);
				$("#telecom").append(result.data.telecom);
				$("#Internet").append(result.data.internet);
				$("#contact").append(result.data.contact);
				$("#hotel").append(result.data.hotel);
				$("#hospital").append(result.data.hospital);
				$("#finance").append(result.data.finance);
				$("#airplane").append(result.data.airplane);
				$("#resume").append(result.data.resume);
				$("#cybercafe").append(result.data.cybercafe);
				$("#socialSecurity").append(result.data.socialSecurity);
				$("#qqdata").append(result.data.qqdata);
				$("#qqqundata").append(result.data.qqqundata);
				$("#qqqunrelation").append(result.data.qqqunrelation);
				$("#Qualification").append(
						result.data.WorkQualification
								+ result.data.qualification);
				var heji = result.data.logistics + result.data.account
						+ result.data.mailbox + result.data.car
						+ result.data.house + result.data.business + result.data.student
						+ result.data.telecom + result.data.internet
						+ result.data.contact + result.data.hotel
						+ result.data.hospital + result.data.finance
						+ result.data.airplane + result.data.airplane
						+ result.data.cybercafe + result.data.socialSecurity
						+ result.data.qqdata + result.data.qqqundata
						+ result.data.qqqunrelation
						+ result.data.WorkQualification
						+ result.data.qualification;
//				console.log(heji)
				$("#heji").append(heji);
			} 
			else {
				alert("系统故障");
			}
		}
	});
	
	$('#GridView td').click(function() {
		var type = $(this).parent().find('td').eq(1).html();
		console.log(type)
		$("#results").empty();
			$.ajax({
			type : "get",
			url : "dataType",
			dataType : "json",
			data:{"type":type},
			success : function(result) {
				for (var i = 0; i < result.data.length; i++) {
					var keyStr = "<tr id="+i+"><td style='display:none;'>"+result.data[i].id+"</td><td class='col-md-1'>"+result.data[i].indexs+"</td><td class='col-md-1'>"+result.data[i].type+"</td><td class='col-md-1'>"+result.data[i].attribute_en+"</td><td class='col-md-2'>"+result.data[i].attribute_ch+"</td><td class='col-md-3'><button class='updataBtn btn btn-default' data-dismiss='modal' style='color:red;margin-right:5px;'data-toggle='modal' data-target='#updata'>修改</button><button  class='deleteBtn btn btn-default' style='color:red' data-dismiss='modal'>删除</button></td></tr>";
					$("#results").append(keyStr);
				}
				}
			})
	})
	
	//删除
	$(".deleteBtn").live("click",function(){
		var ids = $(this).parent().parent().find("td:eq(0)").html();
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
				console.log("删除OK")
				$.ajax({
					url:"dataTypeId",
					type:"post",
					dataType:"json",
					data:{"id":ids},
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
	
	//赋值
	$('#saves').click(function() {
		var indexs = $(this).parent().parent().find("td:eq(1)").html();
		var types = $(this).parent().parent().find("td:eq(2)").html();
		$("#indexA").val(indexs)
		$("#typeA").val(types);
	})
	
	//添加
	$('#addType').click(function() {
		var indexs= $("#indexA").val();
		var type= $("#typeA").val();
		var attribute_en= $("#attribute_enA").val();
		var attribute_ch= $("#attribute_chA").val();
		console.log(indexs+"--"+type+"--"+attribute_en+"--"+attribute_ch)
		swal({
			title:"",  
			text:"确定添加？",  
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
					type:"post",
					url:"saveData",
					dataType:"json",
					data:{"indexs":indexs,"type":type,"attribute_en":attribute_en,"attribute_ch":attribute_ch},
					success:function(result){
						if(result.code == 1){
							swal("操作成功!"); 
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
		var indexs = $(this).parent().parent().find("td:eq(1)").html();
		var types = $(this).parent().parent().find("td:eq(2)").html();
		var attribute_ens = $(this).parent().parent().find("td:eq(3)").html();
		var attribute_chs = $(this).parent().parent().find("td:eq(4)").html();
		$("#indexB").val(indexs)
		$("#typeB").val(types);
		$("#attribute_enB").val(attribute_ens);
		$("#attribute_chB").val(attribute_chs);	
		
		//修改
		$('#updataType').click(function() {
			console.log(id+"------------")
			var indexs= $("#indexB").val();
			var type= $("#typeB").val();
			var attribute_en= $("#attribute_enB").val();
			var attribute_ch= $("#attribute_chB").val();
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
						url:"updateDate",
						type:"post",
						dataType:"json",
						data:{"id":id,"indexs":indexs,"type":types,"attribute_en":attribute_en,"attribute_ch":attribute_ch},
						success:function(result){
							if(result.code == 1){
								swal("操作成功!"); 
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
	})
})