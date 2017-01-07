$(function(){
	load();
	
	$("#addGroup").click(function(){
		showDIV();
	});
	
	$("#Colsebtn").click(function(){
		hiddenDIV();
	});
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}
	$("#add").click(function(){
		//群组名称
		var name = $("#groupNmae").val();
		//优先级
		var Sort = $("#groupSort").val();
		//群组描述
		var Describe = $("#groupDescribe").val();
		//是否删除标识
		var radio = $('input:radio:checked').val();
		$.ajax({
			url:"organization/addGroup",
			type:"post",
			dataType:"json",
			data:{"name":name,"identity":Sort,"desc":Describe,"deleteFlag":radio},
			success:function(result){
				console.log(result)
				if(result.code == 2){
					swal("操作成功!", "已添加！", "success");
					load();
				}else{
					swal("操作失败!", "群组名称已存在！", "error");
				}
			},
			error:function(){
				swal("OMG", "添加失败！", "error"); 
			}
		})
	});
	//编辑信息
	$(".compile").live("click",function(){
		//获取tr的id
		var id = $(this).parent().parent().find("td").eq(0).html();
		$.ajax({
			url:"organization/getIdGroup",
			type:"get",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				$("#cc").empty();
				console.log(result.data);
				$.each(result.data,function(i,data){
					var html="<div id='content' class='content'>"+
								"<div class='styleWhite'>"+
									"<button class='btn btn-sm btn-info' id='colse'>关闭</button>"+
									"<table id='tableStyle'  class='table table-striped table-bordered'>"+
										"<tr>"+
											"<td>群组名称</td>"+
											"<td><input id='Nmae' type='text' placeholder='请输入群组名称' value='"+data.name+"'/></td>"+
											"<td>排序</td>"+
											"<td><input id='Sort' type='text' placeholder='请输入数字' value='"+data.identity+"'/></td>"+
										"</tr>"+
										"<tr>"+
											"<td>群组描述</td>"+
											"<td><input id='Describe' type='text' placeholder='群组描述' value='"+data.desc+"'/></td>"+
											"<td>是否可删除</td>"+
											"<td><label><input type='radio' name='radio' value='1' checked='checked'>可删除</label><label><input type='radio' name='radio' value='0' >不可删除</label></td>"+
										"</tr>"+
										"<tr>"+
											"<td colspan='4'><input id='update' type='button' value='修改'/></td>"+
										"</tr>"+
									"</table>"+
								"</div>"+
							"</div>";
					$("#cc").append(html);
					if(data.deleteFlag == true){
						$("input[name='radio'][value=1]").attr("checked",true); 
					}
					if(data.deleteFlag == false){
						$("input[name='radio'][value=0]").attr("checked",true); 
					}
					show();
					$("#colse").click(function(){
						hidden();
					});
					$("#update").click(function(){
						//群组名称
						var name = $("#Nmae").val();
						//优先级
						var Sort = $("#Sort").val();
						//群组描述
						var Describe = $("#Describe").val();
						//是否删除标识
						var radio = $('input:radio:checked').val();
						
						$.ajax({
							url:"organization/updateGroup",
							type:"post",
							dataType:"json",
							data:{"name":name,"identity":Sort,"desc":Describe,"deleteFlag":radio,"id":id},
							success:function(result){
								console.log(result);
								if(result.code == 1){
									swal("操作成功!", "已修改！", "success");
									load();
								}
							},
							error:function(){
								swal("OMG", "添加失败！", "error"); 
							}
						})
					});
				});
			},
			error:function(){
				console.log("错误");
			}
		})
	});
	//删除信息
	$(".del").live("click",function(){
		//获取tr的id
		var id = $(this).parent().parent().find("td").eq(0).html();
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
					url:"organization/delGroup",
					type:"post",
					dataType:"json",
					data:{"id":id},
					success:function(result){
						if(result.data == 1){
							swal("操作成功!", "已成功删除数据！", "success"); 
							$("tr[id="+id+"]").remove();
						}else{
							swal("OMG", "删除操作失败了!", "error");
						}
					},
					error:function(){
						swal("OMG", "删除操作失败了!", "error");
					}
				})
			}
		})
	});
	var group_id;
	//点击群组为群组添加用户
	$(".group").live("click",function(){
		//获取tr的id
		var id = $(this).parent().parent().find("td").eq(0).html();
		group_id = id;
		$.ajax({
			url:"organization/getByIdUser",
			type:"post",
			dataType:"json",
			data:{"id":id},
			success:function(result){
				console.log(result)
				var user = result.data.user;
				var notUser = result.data.notUser;
				if(result.code ==1){
					var td_notUser = "<select  name='first' multiple='multiple' size=10 class='td3' id='first'>";
					for (var i = 0; i < notUser.length; i++) {
						td_notUser+="<option value='"+notUser[i]["userId"]+"'>"+notUser[i]["account"]+"</option>";
					}
					$("#notUser").html(td_notUser+="</select>");
					var td_getByIdUser = "<select name='second' size='10' multiple='multiple' class='td3' id='second'>";
					for (var j = 0; j < user.length; j++) {
						td_getByIdUser+="<option value='"+user[j]["userId"]+"'>"+user[j]["account"]+"</option>";
					}
					$("#getByIdUser").html(td_getByIdUser+="</select>");
				}
				evt();
				show_GU();
				
				},
			error:function(){
				
			}
		});
	});
	//为群组添加用户或移除用户
	$("#add_GUser").click(function(){
		//获取全部当前select中的用户ID
		var str = $("#getByIdUser option").map(function(){return $(this).val();}).get().join(",");
		$.ajax({
			url:"organization/addGUser",
			type:"post",
			dataType:"json",
			data:{"user_id":str,"group_id":group_id},
			success:function(result){
				if(result.code == 1){
					swal("操作成功!", "", "success");
				}
			},
			error:function(){
				console.log("error");
			}
		});
		
	});
	//隐藏视图
	$("#Colseone").click(function(){
		hidden_GU();
	});
	$("body").on("click",".addpermissions",function(){
		$("#resourceTreeUl").html("");
		var id = $(this).parent().parent().find("td").eq(0).html();
		getResource(1,id);
		showResourceTreeDIV();
	});
	
});
//更新数据事件
function update(id){
	//群组名称
	var name = $("#groupNmae").val();
	//优先级
	var Sort = $("#groupSort").val();
	//群组描述
	var Describe = $("#groupDescribe").val();
	//是否删除标识
	var radio = $('input:radio:checked').val();
	alert(name,Sort,Describe,radio,id)
	$.ajax({
		url:"organization/",
		type:"post",
		dataType:"json",
		data:{"name":name,"identity":Sort,"desc":Describe,"deleteFlag":radio,"id":id},
		success:function(result){
			if(result.code == 2){
				swal("操作成功!", "已添加！", "success");
				load();
			}
		},
		error:function(){
			swal("OMG", "添加失败！", "error"); 
		}
	})
}
//显示DIV
function showDIV(){
	document.getElementById('light').style.display='block';
	document.getElementById('fade').style.display='block';
}
//隐藏DIV
function hiddenDIV(){
	document.getElementById('light').style.display='none';
	document.getElementById('fade').style.display='none';
}
function show(){
	document.getElementById('fade').style.display='block';
	document.getElementById('content').style.display='block';
}
function hidden(){
	document.getElementById('content').style.display='none';
	document.getElementById('fade').style.display='none';
}
function show_GU(){
	document.getElementById('fade').style.display='block';
	document.getElementById('ad_GU').style.display='block';
}
function hidden_GU(){
	document.getElementById('fade').style.display='none';
	document.getElementById('ad_GU').style.display='none';
}
//页面加载
function load(){
	$.ajax({
		url:"organization/all",
		type:"get",
		dataType:"json",
		success:function(result){
			var html="";
			if(result.code == 1){
				$.each(result.data,function(i,data){
					var date = new Date(data.createTime);
					html+="<tr id='"+data.id+"'><td>"+data.id+"</td><td><a class='group' href='javascript:void(0)'><i class='glyphicon glyphicon-folder-close groupname'></i>"+data.name+"</a></td><td>"+data.identity+"</td><td>"+data.desc+"</td><td>"+date.toLocaleString()+"</td>"+
					"<td><a class='addpermissions' href='javascript:void(0)'><i class='glyphicon glyphicon-ok'></i>授权</a>|<a class='compile' href='javascript:void(0)'><i class='glyphicon glyphicon-pencil'></i>编辑</a>|<a class='del' href='javascript:void(0)'><i class='glyphicon glyphicon-remove'></i>删除</a></td></tr>";
				});
				$(".groupresult").html(html);
			}
		},
		error:function(){
			console.log("错误");
		}
	});
}
function evt(){
	//选中的从左端移到右端  
	document.getElementById("addU").onclick = function() {
			//获取select  
			var firstElement = document.getElementById("first");
			//获取option  
			var optionElements = firstElement.getElementsByTagName("option");
			var len = optionElements.length;
			//获取右边select  
			var secondElement = document.getElementById("second");
			//遍历option  
			for(var i = 0; i < len; i++) {
				/*selectedIndex表示当前被选中的选项的索引，从0开始。 
				 *如果没有被选中的selectedIndex=-1 
				 *如果有多个被选中，selectedIndex的值永远是第一个被选中的值 
				 *javaScript的数组是动态的数组，数组长度是可变的，索引值也在变 
				 */
				//alert(firstElement.selectedIndex);  
				if(firstElement.selectedIndex != -1) {
					secondElement.appendChild(optionElements[firstElement.selectedIndex]);
				}
			}
		}
		//全部从左端移到右端  
	document.getElementById("add_all").onclick = function() {
			//获取select  
			var firstElement = document.getElementById("first");

			//获取option  
			var optionElements = firstElement.getElementsByTagName("option");
			var len = optionElements.length;

			//获取右边select  
			var secondElement = document.getElementById("second");

			//遍历option  
			for(var i = 0; i < len; i++) {
				secondElement.appendChild(optionElements[0]);
			}
		}
		//选中的从右端移到左端  
	document.getElementById("remove").onclick = function() {
			//获取select  
			var secondElement = document.getElementById("second");

			//获取option  
			var optionElements = secondElement.getElementsByTagName("option");
			var len = optionElements.length;

			//获取左边select  
			var firstElement = document.getElementById("first");

			//遍历option  
			for(var i = 0; i < len; i++) {
				if(secondElement.selectedIndex != -1) {
					firstElement.appendChild(optionElements[secondElement.selectedIndex]);
				}
			}
		}
		//全部从右端移到左端  
	document.getElementById("remove_all").onclick = function() {
			///获取select  
			var secondElement = document.getElementById("second");

			//获取option  
			var optionElements = secondElement.getElementsByTagName("option");
			var len = optionElements.length;

			//获取左边select  
			var firstElement = document.getElementById("first");

			//遍历option  
			for(var i = 0; i < len; i++) {
				firstElement.appendChild(optionElements[0]);
			}
		}
		//双击的从左端移到右端  
	document.getElementById("first").ondblclick = function() {
			var firstElement = document.getElementById("first");
			var optionElements = firstElement.getElementsByTagName("option");

			var secondElement = document.getElementById("second");
			for(var i = 0; i < optionElements.length; i++) {
				secondElement.appendChild(optionElements[firstElement.selectedIndex]);
			}
		}
		/************************************************************************/
		//双击的从右端移到左端  
	document.getElementById("second").ondblclick = function() {
		var secondElement = document.getElementById("second");
		var optionElements = secondElement.getElementsByTagName("option");

		var firstElement = document.getElementById("first");
		for(var i = 0; i < optionElements.length; i++) {
			firstElement.appendChild(optionElements[secondElement.selectedIndex]);
		}
	}
}