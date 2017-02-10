$(document).ready(function () {
	var dateline = null;
	var searchToken = null;
	
	$("#submitDatada").click(function(){
		$("#submitDatada").attr("disabled", true); 
		$("#nextDatada").attr("disabled", true); 
		$("#submitDatada").attr("style", "color:gray;"); 
		$("#nextDatada").attr("style", "color:gray;");
		$("#resultsDatada").empty();
		$("#nextDatada").hide();
		var query = $("#query").val();
		var dateline = null;
		var searchToken = "0";
		var url = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		search(url)
	});
	
	$("#nextDatada").click(function(){
		$("#submitDatada").attr("disabled", true); 
		$("#nextDatada").attr("disabled", true);
		$("#submitDatada").attr("style", "color:gray;"); 
		$("#nextDatada").attr("style", "color:gray;");
		
		var query = $("#query").val();
		var url = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		search(url)
	});
	
	function search(url) {
		$.getJSON(url, function(result) {
			$("#resultsDatada").empty();
			if (result.code == 2) {
				$("#resultsDatada").append(result.failure)
			} else if (result.code == 1) {
				if (result.data) {
					searchToken = result.data.scrollId
					dateline = result.data.totalRowNum + ""
					if (result.data.resultList && result.data.resultList.length > 0) {
						if (result.data.resultList.length < 5 ) {
							$("#nextDatada").hide();
						}else {
							$("#nextDatada").attr("disabled", false); 
							$("#nextDatada").attr("style", "color:black;");
						}
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
			}
			$("#submitDatada").attr("disabled", false); 
			$("#submitDatada").attr("style", "color:black;"); 
		});
	}
});

//enter 控件
function EnterPressDatada(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitDatada").click(); 
	} 
	}; 	
