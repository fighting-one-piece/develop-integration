$(function(){
	var pathName=window.document.location.pathname;
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	
	
	
	$.ajax({
		type:"get",
		url:projectName+"/admin/aResource/menuLink",
		dataType:"json",
		success:function(result){
			if (result.code == 1){
				var str = "";
				$.each(result.data,function(n,list){
					var a = " <a href = "+projectName+list.url+" >"+list.name+"</a>"
					str += a;
				});
				$("#headLinkAddress").html(str);
				$("#menu a").each(function(index,data){
					$(data).attr("style","");
					var url = $(data).attr("href");
					var nowurl = window.location.pathname
					if (url==nowurl) {
						$(data).attr("style","color:red");
					}
				})
			}
			
		}
	});
	
	
	
})