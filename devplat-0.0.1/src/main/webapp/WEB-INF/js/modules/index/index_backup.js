$(document).ready(function () {
	var es = 0;
	var scrollId = null;
	var dateline = null;
	var searchToken = null;
	
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
		var url = "search/scroll?query=" + query + "&es=" + 0 + "&scrollId=" + scrollId 
			+ "&rowNumPerPage=" + rowNumPerPage;
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
		var query = $("#query").val();
		var rowNumPerPage = 10;
		var url = "search/scroll?query=" + query + "&es=" + es + "&scrollId=" + scrollId 
			+ "&rowNumPerPage=" + rowNumPerPage;
		var urlDatada = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		search1(urlDatada);
		search3(url);
	});
	
	//Datada数据查询
	function search1(urlDatada) {
		$.getJSON(urlDatada, function(result) {
			$("#resultsDatada").empty();
			if (result.code == 2) {
				$("#resultsDatada").append(result.failure)
			} else if (result.code == 1) {
				if (result.data) {
					searchToken = result.data.scrollId
					dateline = result.data.totalRowNum + ""
						$.each(result.data.resultList, function(i, item){
							/**
							$("#resultsDatada").append("<table>");
							$("#resultsDatada").append("<tr><td>ItemId</td><td>").append(item.itemId).append("</td></tr>");
							$("#resultsDatada").append("<tr><td>Fields</td><td>").append(item.fields).append("</td></tr>");
							$("#resultsDatada").append("<tr><td>Data</td><td>").append(item.data).append("</td></tr>");
							$("#resultsDatada").append("</table>");
							*/
							//$("#results").append(JSON.stringify(item));
							//$("#results").append("<br/><br/>");
							var keytr = "<br><table><tr>";
							var valuetr = "<tr>";
							$.each(item["fields"],function(i,key){
								keytr += "<td>"+key+"</td>";
							})
							$.each(item["data"],function(i,value){
								valuetr += "<td>"+value+"</td>";
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
		});
	}
	
	//ES数据查询
	function search2(url) {
		$.getJSON(url, function(result) {
			$("#resultsIndex").empty();
			if (result.code == 2) {
				$("#resultsIndex").append(result.failure)
			} else if (result.code == 1) {
				if (result.data) {
					es = result.data.es
					scrollId = result.data.scrollId
					var rowNumPerPage = 10;
					var totalRowNum = result.data.totalRowNum;
					/** 
					var pageNum = (totalRowNum / rowNumPerPage) + 1
					for (var i = 2; i < (pageNum + 2); i++) {
						if (i < 7 || i > (pageNum - 3)) {
							$("#pv").append("<a href=\"\">" + i + "</a>")
						}
					}
					*/
					if (result.data.resultList && result.data.resultList.length > 0) {
						if (result.data.resultList.length < 10 || totalRowNum == 10) {
							$("#nextIndex").hide();
						}else {
							$("#nextIndex").attr("disabled", false); 
							$("#nextIndex").attr("style", "color:black;");
						}
						$.each(result.data.resultList, function(i, item){
							var keytr = "<br><table><tr><td>"+"库"+"</td><td>"+"表"+"</td>";
							var valuetr = "<tr><td>"+item["index"]+"</td><td>"+item["type"]+"</td>";
							$.each(item.data, function(key, value){
								keytr += "<td>"+key+"</td>";
								valuetr += "<td>"+value+"</td>";
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
					es = result.data.es
					scrollId = result.data.scrollId
					var rowNumPerPage = 10;
					/** 
					var pageNum = (totalRowNum / rowNumPerPage) + 1
					for (var i = 2; i < (pageNum + 2); i++) {
						if (i < 7 || i > (pageNum - 3)) {
							$("#pv").append("<a href=\"\">" + i + "</a>")
						}
					}
					*/
					if (result.data.resultList && result.data.resultList.length > 0) {
						if (result.data.resultList.length < 10) {
							$("#nextIndex").hide();
						}else {
							$("#nextIndex").attr("disabled", false); 
							$("#nextIndex").attr("style", "color:black;");
						}
						$.each(result.data.resultList, function(i, item){
							var keytr = "<br><table><tr><td>"+"库"+"</td><td>"+"表"+"</td>";
							var valuetr = "<tr><td>"+item["index"]+"</td><td>"+item["type"]+"</td>";
							$.each(item.data, function(key, value){
								keytr += "<td>"+key+"</td>";
								valuetr += "<td>"+value+"</td>";
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