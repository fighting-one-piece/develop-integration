$(document).ready(function(){
	var scrollId = null;
	$("#submitCard").click(function(){
		var idCard = $("#query").val();
		$("#submit").attr("disabled", true); 
		$("#submit").attr("style", "color:gray;"); 
		search();
		
	});
	function search() {
		var idCard = $("#query").val();
		if(idCard.length > 0){
			addLog(idCard);
			$.ajax({
				url:"card/search",
				type:"get",
				dataType:"json",
				data:{"idCard":idCard},
				success:function(result){
					$("#results").empty();
					if(result.data){
						if (result.data.length > 0){
							var keytr;
							var valuetr;
							$.each(result.data,function(n,list){
								keytr = "<br><table class='table table-striped table-bordered'><tr>";
								valuetr = "<tr>";
								$.each(list, function(resultKey, resultValue){
									keytr += "<td>"+resultKey+"</td>";
									valuetr += "<td>"+resultValue+"</td>";
								});
								keytr += "</tr>";
								valuetr += "</tr></table>";
								$("#results").append(keytr+valuetr);
							});
						} else {
							$("#results").append("未找到相关数据")
						}
					}else{
						$("#results").append("未找到相关数据")
					}
					$("#submit").attr("disabled", false); 
					$("#submit").attr("style", "color:black;"); 
				},
				error:function(){
					alert("错误");
				}
			});
		}
	}
	//添加日志
	function addLog(keyword){
			$.ajax({
				url:"log/addlog",
				type:"post",
				dataType:"json",
				data:{"keyword":keyword},
				success:function(){
					
				},
				error:function(){
					console.log(keyword);
				}
			})
	}
});


//enter 控件
function EnterPressCard(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitCard").click(); 
	} 
	}; 
