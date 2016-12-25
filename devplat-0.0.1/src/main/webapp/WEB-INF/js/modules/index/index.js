$(document).ready(function () {
	var scrollId = null;
	var dateline = null;
	var searchToken = null;
	var totalRowNum = 0;
	var resultNum = 0;
	var datadaNum = 0;
	
	//点击搜索查询数据
	$("#submitIndex").click(function(){
		$("#submitIndex").attr("disabled", true); 
		$("#nextIndex").attr("disabled", true); 
		$("#submitIndex").attr("style", "color:gray;"); 
		$("#nextIndex").attr("style", "color:gray;");
		$("#resultsIndex").empty();
		$("#resultsLable").empty();
		$("#resultsDatada").empty();
		$("#nextIndex").hide();
		var query = $("#query").val();
		var scrollId = "";
		var rowNumPerPage = 10;
		var url = "search?query=" + query + "&scrollId=" + scrollId + "&rowNumPerPage=" + rowNumPerPage;
		var dateline = null;
		var searchToken = "0";
		var urlDatada = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		search1(urlDatada);
		search2(url);
		addLog(query);
	});
	
	//点击下一页
	$("#nextIndex").click(function(){
		$("#submitIndex").attr("disabled", true); 
		$("#nextIndex").attr("disabled", true); 
		$("#submitIndex").attr("style", "color:gray;"); 
		$("#nextIndex").attr("style", "color:gray;");
		$("#nextIndex").hide();
		var query = $("#query").val();
		var rowNumPerPage = 10;
		var url = "search?query=" + query + "&scrollId=" + scrollId + "&rowNumPerPage=" + rowNumPerPage;
		var urlDatada = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		var check = totalRowNum%10;
		if (resultNum != check ) {
			search3(url);
		}
		if (datadaNum > 9) {
			search1(urlDatada);
		}
		if (resultNum == check & datadaNum < 10) {
			$("#nextIndex").hide();
			$("#submitIndex").attr("disabled", false); 
			$("#submitIndex").attr("style", "color:black;"); 
		}else {
			$("#nextIndex").attr("disabled", false); 
			$("#nextIndex").attr("style", "color:black;");
		}
	});
	
	//Datada数据查询
	function search1(urlDatada) {
		$.getJSON(urlDatada, function(result) {
			$("#resultsDatada").empty();
			if (result.code == 2) {
				$("#resultsDatada").append(result.failure)
			} else if (result.code == 1) {
				if (result.data) {
					searchToken = result.data.scrollId;
					dateline = result.data.totalRowNum;
						datadaNum = result.data.resultList.length;
						if (datadaNum > 9) {
							$("#nextIndex").attr("disabled", false); 
							$("#nextIndex").attr("style", "color:black;");
						}
						$.each(result.data.resultList, function(i, item){
							var keytr = "<br><table style='width:100%;'><tr>";
							var valuetr = "<tr>";
							$.each(item["fields"],function(i,key){
								keytr += "<td style='background: #EEE8AA;'>"+key+"</td>";
							})
							$.each(item["data"],function(i,value){
								valuetr += "<td style='background: white;'>"+value+"</td>";
							});
							keytr += "</tr>";
							valuetr += "</tr></table></br>";
							$("#resultsDatada").append(keytr+valuetr);
						});
					} else {
						$("#resultsDatada").append("未找到相关数据")
					}
				} else {
					$("#resultsDatada").append("未找到相关数据")
				}
			$("#submitIndex").attr("disabled", false); 
			$("#submitIndex").attr("style", "color:black;"); 
		});
	}
	
	//ES数据查询
	function search2(url) {
		$.getJSON(url, function(result) {
			$("#resultsIndex").empty();
			if (result.code == 2) {
				$("#resultsIndex").append(result.failure);
			} else if (result.code == 1) {
				if (result.data) {
					es = result.data.es;
					scrollId = result.data.scrollId;
					var rowNumPerPage = 10;
					totalRowNum = result.data.totalRowNum;
					if ( totalRowNum > 10) {
							$("#nextIndex").attr("disabled", false); 
							$("#nextIndex").attr("style", "color:black;");
						}
					resultNum = result.data.resultList.length;
					if (result.data.resultList && result.data.resultList.length > 0) {
						$.each(result.data.resultList, function(i, item){
							var keytr = "<br><table style='width:100%;'><tr><td style='background: #EEE8AA;'>"+"库"+"</td><td style='background: #EEE8AA;'>"+"表"+"</td>";
							var valuetr = "<tr><td style='background: white;'>"+item["index"]+"</td><td style='background: white;'>"+item["type"]+"</td>";
							$.each(item.data, function(key, value){
								keytr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								valuetr += "<td style='background: white;'>"+value+"</td>";
							})
							keytr += "</tr>";
							valuetr += "</tr></table></br>";
							$("#resultsIndex").append(keytr+valuetr);
						});
						$("#resultsLable").append("搜索共" + totalRowNum + "结果</br><br/>")
						
					} else {
						$("#resultsIndex").append("未找到相关数据")
					}
				} else {
					$("#resultsIndex").append("未找到相关数据")
				}
			}
			$("#submitIndex").attr("disabled", false); 
			$("#submitIndex").attr("style", "color:black;"); 
		});
	}
	
	//ES点击下一页查询数据
	function search3(url) {
		$.getJSON(url, function(result) {
			$("#resultsIndex").empty();
			if (result.code == 2) {
				$("#resultsIndex").append(result.failure)
			} else if (result.code == 1) {
				if (result.data) {
					es = result.data.es;
					scrollId = result.data.scrollId;
					var rowNumPerPage = 10;
					resultNum = result.data.resultList.length;
					if (result.data.resultList && result.data.resultList.length > 0) {
						$.each(result.data.resultList, function(i, item){
							var keytr = "<br><table style='width:100%;'><tr><td style='background: #EEE8AA;'>"+"库"+"</td><td style='background: #EEE8AA;'>"+"表"+"</td>";
							var valuetr = "<tr><td style='background: white;'>"+item["index"]+"</td><td style='background: white;'>"+item["type"]+"</td>";
							$.each(item.data, function(key, value){
								keytr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								valuetr += "<td style='background: white;'>"+value+"</td>";
							})
							keytr += "</tr>";
							valuetr += "</tr></table></br>";
							$("#resultsIndex").append(keytr+valuetr);
						});
						
					} else {
						$("#resultsIndex").append("未找到相关数据")
					}
				} else {
					$("#resultsIndex").append("未找到相关数据")
				}
			}
			$("#submitIndex").attr("disabled", false); 
			$("#submitIndex").attr("style", "color:black;"); 
		});
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
function EnterPressIndex(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitIndex").click(); 
	} 
	}; 