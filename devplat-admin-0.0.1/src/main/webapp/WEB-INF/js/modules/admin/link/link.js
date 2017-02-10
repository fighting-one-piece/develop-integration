var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function(){
	function showLinkAddress(page,pageSize){
		
		$.ajax({
			type:"get",
			data:{"page":page,"pageSize":pageSize},
			url:projectName+"/admin/aResource/find/page",
			dataType:"json",
			success:function(result){
				if(result.code == 1){
					var str ;
					$.each(result.data.data,function(n,link){
						var type;
						if (link.type == 1) {
							type = "管理员菜单";
						} else if (link.type == 2){
							type = "用户菜单";
						} else if (link.type == 3){
							type = "接口";
						}
						var priority = link.priority;
						var parentName = link.parentName;
						var icon = link.icon
						if(parentName == undefined){
							parentName = "";
						}
						if(priority == undefined){
							priority = "";
						}
						if(icon == undefined){
							icon = "";
						}
						str = str + "<tr id='"+link.id+"'><td>"+link.name+"</td><td>"+link.identity+"</td><td>"+link.url
						+"</td><td>"+type+"</td><td>"+icon+"</td><td>"+priority+"</td><td id='parentId"+link.parentId+"'>"+parentName+"</td><td><button class='btn btn-sm btn-info update-link'>修改</button>&nbsp;&nbsp;&nbsp;&nbsp;<button class='btn btn-sm btn-info delete-link'>删除</button></td></tr>";
					});
					$("#AResourceendpage").val(result.data.pageCount);
					$("#AResourcelastpage").val(result.data.pageNum);
					$("#AResourcenextpage").val(result.data.pageNum);
					
					$("#allLinkAddressTable").html("");
					$("#allLinkAddressTable").append(str);
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error")
				}
			}
		});
	}
	showLinkAddress(1,10);
	$("#AResourceendpage").click(function(){
		var page = $(this).val();
		showLinkAddress(page,10);
	})
	$("#AResourcehomepage").click(function(){
		var page = $(this).val();
		showLinkAddress(page,10);
	})
	$("#AResourcelastpage").click(function(){
		var nowPage = $(this).val();
		var homePage = $("#AResourcehomepage").val();
		if(nowPage == homePage){
			return;
		}
		var page = parseInt(nowPage) - parseInt(1);
		showLinkAddress(page,10);
	})
	$("#AResourcenextpage").click(function(){
		var nowPage = $(this).val();
		var endPage = $("#AResourceendpage").val();
		if (nowPage >= endPage){
			return;
		}
		var page = parseInt(nowPage) + parseInt(1);
		showLinkAddress(page,10);
	})
	//显示DIV
	function showAddLinkDIV(){
		document.getElementById('addLinkAddressDiv').style.display='block';
		document.getElementById('addLinkAddressFade').style.display='block';
	}
	$("#closeAddLinkAddressDivBtn").click(function(){
		document.getElementById('addLinkAddressDiv').style.display='none';
		document.getElementById('addLinkAddressFade').style.display='none';
	});
	function showUpdateLinkDIV(){
		document.getElementById('updateLinkAddressDiv').style.display='block';
		document.getElementById('addLinkAddressFade').style.display='block';
	}
	$("#closeUpdateLinkAddressDivBtn").click(function(){
		document.getElementById('updateLinkAddressDiv').style.display='none';
		document.getElementById('addLinkAddressFade').style.display='none';
	});
	$("#toAddLinkAddressBtn").click(function(){
		showAddLinkDIV();
	});
	/**
	 * 修改事件
	 * */
	var id;
	$("#allLinkAddressTable").on("click",".update-link",function(){
		id = $(this).parent().parent().attr("id");
		var parentId = $(this).parent().prev().attr("id").replace("parentId","");
		showUpdateLinkDIV();
		//填入当前信息到表单中
		$.ajax({
			type:"post",
			url:projectName+"/admin/aResource/find/id",
			data:{"id":id},
			dataType:"json",
			success:function(result){
				if(result.code == 1 && result){
					$("#updateLinkname").val(result.data.name);
					$("#updateLinkidentity").val(result.data.identity);
					$("#updateLinkurl").val(result.data.url);
					$("#updateLinkpriority").val(result.data.priority);
					$("#updateLinkicon").val(result.data.icon);
					
					if (result.data.type == 1){
						$("#updateadminmenu").attr("selected",true);
						$("#updateLinkparentId").attr("disabled",false);
					} else if (result.data.type ==2) {
						$("#updateusermenu").attr("selected",true);
						$("#updateLinkparentId").attr("disabled",false);
					} else if (result.data.type ==3){
						$("#updateinterface").attr("selected",true);
						$("#updateLinkparentId").attr("disabled",true);
					}
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error")
					showLinkAddress();
				}
				//获取可能的父资源
				getParentNameUpdate(parentId,id);
				$("#updateLinktype").change(function(){
					getParentNameUpdate("",id);
				})
			}
		});
	});
	//修改
	$("#submitUpdateLinkAddressDivBtn").click(function(){
		var name = $("#updateLinkname").val().trim();
		var identity = $("#updateLinkidentity").val().trim();
		var url = $("#updateLinkurl").val().trim();
		var icon = $("#updateLinkicon").val().trim();
		
		var type;
		if ($("#updateLinktype option:selected").attr("id") == "updateusermenu"){
			type = 2;
		} else if ($("#updateLinktype option:selected").attr("id") == "updateadminmenu"){
			type = 1;
		} else if ($("#updateLinktype option:selected").attr("id") == "updateinterface"){
			type = 3;
		}
		var priority = $("#updateLinkpriority").val().trim();
		var parentId = $("#updateLinkparentId option:selected").attr("id").replace("linkparentId","");
		if (name == null || identity == null || url == null || priority == null || type == null
				|| name == "" || identity == "" || url == "" || priority == "" || type == "") {
			swal("Error!", "请输入完整参数！", "error");
			return;
		} else {
			$.ajax({
				type:"post",
				data:{"name":name,"identity":identity,"url":url,"type":type,"priority":priority,"parentId":parentId,"id":id,"icon":icon},
				url:projectName+"/admin/aResource/update",
				dataType:"json",
				success:function(result){
					if(result.code == 1){
						$("#closeUpdateLinkAddressDivBtn").click();
						swal("Updated! ", "修改成功！", "success");
						var page = $("#AResourcelastpage").val();
						showLinkAddress(page,10);
					} else {
						$("#closeUpdateLinkAddressDivBtn").click();
						swal("Error!", "系统繁忙，请稍后再试！", "error");
						var page = $("#AResourcelastpage").val();
						showLinkAddress(page,10);
					}
				}
			});
		}
	});
	/**
	 * 删除事件
	 */
	$("#allLinkAddressTable").on("click",".delete-link",function(){
		var id = $(this).parent().parent().attr("id");
		
		swal({
			title:"",  
			text:"确定删除吗？",  
			type:"warning",  
			showCancelButton:"true",  
			showConfirmButton:"true",
			confirmButtonColor: "#DD6B55",
			confirmButtonText:"确定",  
			cancelButtonText:"取消"
		}).then(function(isConfirm){
			if(isConfirm == true){
				$.ajax({
					type:"post",
					url:projectName+"/admin/aResource/delete",
					data:{"id":id},
					dataType:"json",
					success:function(result){
						if(result.code == 1){
							swal("Deleted! ", "删除成功！", "success");
							var page = $("#AResourcelastpage").val();
							showLinkAddress(page,10);
						} else {
							swal("Error!", "系统繁忙，请稍后再试！", "error")
							var page = $("#AResourcelastpage").val();
							showLinkAddress(page,10);
						}
					}
				});
			}
		});
	});
	/**
	 * 添加事件
	 */
	$("#toAddLinkAddressBtn").click(function(){
		$("#addAResourceName").val("");
		$("#addAResourceIdentity").val("");
		$("#addAResourceUrl").val("");
		$("#addAResourceIcon").val("");
		$("#addAResourcePriority").val("");
		$("#addAResourceParentId").html("");
		//获取可能的父资源
		getParentNameAdd();
	});
	$("#addAResourceType").change(function(){
		getParentNameAdd()
	});
	//添加
	$("#submitAddLinkAddressDivBtn").click(function(){
		var name = $("#addAResourceName").val().trim();
		var identity = $("#addAResourceIdentity").val().trim();
		var url = $("#addAResourceUrl").val().trim();
		var priority = $("#addAResourcePriority").val().trim();
		var icon = $("#addAResourceIcon").val().trim();
		var type;
		if ($("#addAResourceType option:selected").attr("id") == "addusermenu"){
			type = 2;
		} else if ($("#addAResourceType option:selected").attr("id") == "addadminmenu"){
			type = 1;
		} else if ($("#addAResourceType option:selected").attr("id") == "addinterface"){
			type = 3;
		}
		var parentId = $("#addAResourceParentId option:selected").attr("id").replace("addparentId","");
		if (name == null || identity == null || url == null || priority == null || type == null
				|| name == "" || identity == "" || url == "" || priority == "" || type == "") {
			swal("Error!", "请输入完整参数！", "error");
			return;
		} else {
			$.ajax({
				type:"post",
				data:{"name":name,"identity":identity,"url":url,"type":type,"priority":priority,"parentId":parentId,"id":id,"icon":icon},
				url:projectName+"/admin/aResource/add",
				dataType:"json",
				success:function(result){
					if(result.code == 1){
						$("#closeAddLinkAddressDivBtn").click();
						swal("Updated! ", "添加成功！", "success");
						var page = $("#AResourcelastpage").val();
						showLinkAddress(page,10);
					} else {
						$("#closeAddLinkAddressDivBtn").click();
						swal("Error!", "系统繁忙，请稍后再试！", "error");
						var page = $("#AResourcelastpage").val();
						showLinkAddress(page,10);
					}
				}
			});
		}
	});
	
	
	
});
function getParentNameAdd(){
	var type0 ;
	if ($("#addAResourceType option:selected").attr("id") == "addusermenu"){
		type0 = 2;
		$("#addAResourceParentId").attr("disabled",false);
	} else if ($("#addAResourceType option:selected").attr("id") == "addadminmenu"){
		type0 = 1;
		$("#addAResourceParentId").attr("disabled",false);
	} else if ($("#addAResourceType option:selected").attr("id") == "addinterface"){
		type0 = 3;
		$("#addAResourceParentId").attr("disabled",true);
	}
	$.ajax({
		type:"get",
		url:projectName+"/admin/aResource/find/type",
		data:{"type":type0},
		dataType:"json",
		success:function(result){
			if(result.code == 1){
				var str = "<option id='addparentId' selected='true'>请选择(选择此项则为无父资源)</option>";
				$.each(result.data,function(n,aResource){
					str += "<option id='addparentId"+aResource.id+"'>"+aResource.name+"</option>"
				})
				$("#addAResourceParentId").html(str);
			} else {
				swal("Error!", "系统繁忙，请稍后再试！", "error")
			}
		}
	});
}
function getParentNameUpdate(parentId,id){
	var type0 ;
	if ($("#updateLinktype option:selected").attr("id") == "updateusermenu"){
		type0 = 2;
		$("#updateLinkparentId").attr("disabled",false);
	} else if ($("#updateLinktype option:selected").attr("id") == "updateadminmenu"){
		type0 = 1;
		$("#updateLinkparentId").attr("disabled",false);
	} else if ($("#updateLinktype option:selected").attr("id") == "updateinterface"){
		type0 = 3;
		$("#updateLinkparentId").attr("disabled",true);
	}
	$.ajax({
		type:"get",
		url:projectName+"/admin/aResource/find/type",
		data:{"type":type0},
		dataType:"json",
		success:function(result){
			if(result.code == 1){
				var str = "<option id='linkparentId'>请选择(选择此项则为无父资源)</option>";
				$.each(result.data,function(n,aResource){
					if (id != aResource.id) {
						if (parentId == aResource.id){
							str += "<option id='linkparentId"+aResource.id+"' selected='true'>"+aResource.name+"</option>"
						} else {
							str += "<option id='linkparentId"+aResource.id+"'>"+aResource.name+"</option>"
						}
					}
				})
				$("#updateLinkparentId").html(str);
			} else {
				swal("Error!", "系统繁忙，请稍后再试！", "error")
			}
		}
	});
}