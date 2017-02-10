$(document).ready(function () {
	$.ajax({
		url:"view",
		type:"get",
		dataType:"json",
		success:function(result){
			if (result.code == 2) {
				$("#resultsChoose").append(result.failure);
			}else if (result.code == 1) {
				
				$.each(result.data, function(i, item){
					$("#resultsChoose").append(item + "<br/>")
				})
					
			}
		}
	});
		
	$("#submitChoose").click(function() {
		$("#resultsChoose").empty();
		var query = $("#query").val();
		$.ajax({
			url:"distribute",
			type:"post",
			dataType:"json",
			data:{"query":query},
			success:function(result){
				if (result.code == 2) {
					$("#resultsChoose").append(result.failure);
				}else if (result.code == 1) {
					
					$.each(result.data, function(i, item){
						$("#resultsChoose").append(item + "<br/>")
					})
						
				}
			},
			error:function(){
				$("#resultsChoose").append("数据类型错误");
			}
		});
	});
});
//enter 控件
function EnterPressChoose(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitChoose").click(); 
	} 
	}; 
	
	
	
	
	
	
	
	
	
	
	
	