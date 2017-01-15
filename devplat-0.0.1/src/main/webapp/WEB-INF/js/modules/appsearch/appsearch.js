$(document).ready(function(){
	var scrollId = null;
	var rowNumPerPage = 10;
	var query;
	$("#submitapp").click(function(){
		$("#appresult").empty();
		$("#appresults").empty();
		$("#nextapp").hide();
		query = $("#query").val().trim();
		$("#submitapp").attr("disabled", true); 
		$("#submitapp").attr("style", "color:gray;"); 
		appsearch();
		
	});
	function appsearch() {
		if(query.length > 0){
			$.ajax({
				url:"/devplat/labels/search",
				type:"get",
				dataType:"json",
				data:{"query":query},
				success:function(result){
					console.log(result);
					console.log(query);
					$("#appresult").empty();
					if (result.code == 1) {
						var app="<br>";
						var k = 1;
					if(result.data.length > 0){
						for (var i = 0; i < result.data.length; i++) {
							
							if (i > 11 * k) {
								app +="<br><br>";
								k++;	
							}
							app +="<a id='"+result.data[i].index+"' class='"+result.data[i].type+"' href='#'>"+result.data[i].label+"("+result.data[i].hits+"条)"+"</a>&nbsp;&nbsp;";
						}
						$("#appresult").append(app);
					}else {
						$("#appresult").append("未找到相关信息");
					}
					}else {
						$("#appresult").append("未找到相关信息");
					}
					$("#submitapp").attr("disabled", false); 
					$("#submitapp").attr("style", "color:black;"); 
				},
				error:function(){
					alert("错误");
				}
			});
		}
	}
	var index;
	var type;
	//点击a标签
	$("#appresult a").live("click",function(){
		$("#nextapp").hide();
		$("#appresult a").attr("style","");
		$(this).attr("style","color:red");
		$("#appresults").empty();
		index =  $(this).attr("id");
		type =  $(this).attr("class");
		scrollId = null;
		scrollId = "1";
		$.ajax({
			url:"/devplat/index/"+index+"/type/"+type+"/search",
			type:"get",
			dataType:"json",
			data:{"query":query,"scrollId":scrollId,"rowNumPerPage":rowNumPerPage},
			success:function(result){
				if (result.code == 1) {
					scrollId = result.data.scrollId;
					if (result.data.resultList.length == 10) {
						$("#nextapp").show();
					}else {
						$("#nextapp").hide();
					}
					if (result.data.resultList.length > 0) {
						$.each(result.data.resultList,function(index,resultList){
							var key = "<table style='width:100%;'><tr>";
							var value = "<tr>";
							$.each(resultList,function(key1,value1){
								if (key1 == "data") {
									$.each(value1,function(key2,value2){
										key +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										value +="<td>"+value2+"</td>";	
									})
								}else {
									key +="<td style='background: #EEE8AA;'>"+key1+"</td>";
									value +="<td>"+value1+"</td>";
								}
							})
							$("#appresults").append("<br>"+key+value+"</table>");
						});
					}else {
						$("#appresults").append("未找到相关信息");
					}
				}else {
					$("#appresults").append("未找到相关信息");
				}
			}
		});
	});
	
	//点击下一页
	$("#nextapp").live("click",function(){
		$("#nextapp").hide();
		$("#appresults").empty();
		$.ajax({
			url:"/devplat/index/"+index+"/type/"+type+"/search",
			type:"get",
			dataType:"json",
			data:{"query":query,"scrollId":scrollId,"rowNumPerPage":rowNumPerPage},
			success:function(result){
				if (result.code == 1) {
					scrollId = result.data.scrollId;
					if (result.data.resultList.length == 10) {
						$("#nextapp").show();
					}else {
						$("#nextapp").hide();
					}
					if (result.data.resultList.length > 0) {
						$.each(result.data.resultList,function(index,resultList){
							var key = "<table style='width:100%;'><tr>";
							var value = "<tr>";
							$.each(resultList,function(key1,value1){
								if (key1 == "data") {
									$.each(value1,function(key2,value2){
										key +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										value +="<td>"+value2+"</td>";	
									})
								}else {
									key +="<td style='background: #EEE8AA;'>"+key1+"</td>";
									value +="<td>"+value1+"</td>";
								}
							})
							$("#appresults").append("<br>"+key+value+"</table>");
						});
					}else {
						$("#appresults").append("未找到相关信息");
					}
				}else {
					$("#appresults").append("未找到相关信息");
				}
			}
		});
	});
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
function EnterPressapp(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitapp").click(); 
	} 
	}; 
