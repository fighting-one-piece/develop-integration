var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function(){
	var bodystr = '<div id="resourceTreeDiv" class="resource_link_white_content"><div class="styleWhite"><button class="btn btn-sm btn-info" id="closeResourceTreeDiv" style="margin-bottom: 10px; margin-left: 5%">关闭</button></div><div><ul id="resourceTreeUl"></ul></div><button class="btn btn-sm btn-info pull-right" id="submitUpdatePermissionBtn" style="margin-top: 10px; margin-right: 5%">提交</button></div><!-- 背景 --><div id="resourceTreeFade" class="resource_black_overlay"></div>';
	$("body").append(bodystr);
//	$("body").on("click",".addpermissions",function(){
//		$("#resourceTreeUl").html("");
//		getResource(参数1,参数2);
//		showResourceTreeDIV();
//	})
	$("#closeResourceTreeDiv").click(function(){
		document.getElementById('resourceTreeDiv').style.display='none';
		document.getElementById('resourceTreeFade').style.display='none';
	});
	
	$("body").on("click","#submitUpdatePermissionBtn",function(){
		
	})
	
})
function getResource(principalType,principalId){
	$("#resourceTreeUl").data("principalType",principalType);
	$("#resourceTreeUl").data("principalId",principalId);
	alert($("#resourceTreeUl").data("principalId"))
	alert($("#resourceTreeUl").data("principalType"))
	$.ajax({
		type:"get",
		url:projectName+"/admin/aResource/tree",
		dataType:"json",
		success:function(result){
			if(result.code == 1){
				$.each(result.data,function(index,resource){
					showResourceTree(resource);
				});
			} else {
				swal("Error!", "系统繁忙，请稍后再试！", "error")
			}
		}
	});
}
function showResourceTree(resource){
	if (resource.parentId == null || resource.parentId == "") {
		var str = "<li id='li"+resource.id+"'><input type='checkbox'class='read'>读<input type='checkbox' class='write'>写<span><span>&nbsp;&nbsp"+resource.name+"</span><ul id='ul"+resource.id+"'></ul></li>";
		$("#resourceTreeUl").append(str);
	} else {
		var str = "<li id='li"+resource.id+"'><input type='checkbox'class='read'>读<input type='checkbox' class='write'>写<span>&nbsp;&nbsp"+resource.name+"</span><ul id='ul"+resource.id+"'></ul></li>";
		var str1 = "#ul" + resource.parentId
		$(str1+"").append(str);
		
	}
	if (resource.children != undefined && resource.children != null && resource.children != "") {
		$.each(resource.children,function(index,resourceSon){
			showResourceTree(resourceSon);
		})
	}
	
	
}
//显示DIV
	function showResourceTreeDIV(){
		document.getElementById('resourceTreeDiv').style.display='block';
		document.getElementById('resourceTreeFade').style.display='block';
	}