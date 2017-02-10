$(document).ready(function () {
	var scrollId = null;
	var dateline = null;
	var searchToken = null;
	var totalRowNum = 0;
	var resultNum = 0;
	var datadaNum = 0;
	
	//点击搜索查询数据
	$("#submitIndex").click(function(){
		document.getElementById('background').style.display='block';
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
		var rowNumPerPage = 40;
		var url = "search?query=" + query + "&scrollId=" + scrollId + "&rowNumPerPage=" + rowNumPerPage;
		var dateline = null;
		var searchToken = "0";
		var urlDatada = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		search1(urlDatada);
		search2(url);
	});
	
	//点击下一页
	$("#nextIndex").click(function(){
		document.getElementById('background').style.display='block';
		$("#submitIndex").attr("disabled", true); 
		$("#nextIndex").attr("disabled", true); 
		$("#submitIndex").attr("style", "color:gray;"); 
		$("#nextIndex").attr("style", "color:gray;");
		$("#resultsIndex").empty();
		$("#resultsDatada").empty();
		$("#nextIndex").hide();
		var query = $("#query").val();
		var rowNumPerPage = 40;
		var url = "search?query=" + query + "&scrollId=" + scrollId + "&rowNumPerPage=" + rowNumPerPage;
		var urlDatada = "datada/search?query=" + query + "&dateline=" + dateline + "&searchToken=" + searchToken;
		var check = totalRowNum%rowNumPerPage;
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
			document.getElementById('background').style.display='none';
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
					var rowNumPerPage = 40;
					totalRowNum = result.data.totalRowNum;
					if ( totalRowNum > 40) {
							$("#nextIndex").attr("disabled", false); 
							$("#nextIndex").attr("style", "color:black;");
						}
					resultNum = result.data.resultList.length;
					if (result.data.resultList && result.data.resultList.length > 0) {
						var tables = "";
						var typeArr = new Array();
						$.each(result.data.resultList,function(ide,item){
							typeArr.push(item.type);
						})
						typeArr = typeArr.unique();
						var keyArr = new Array();
						for(var i = 0;i < typeArr.length; i++){
							keyArr.push(new Array());
						}
						
						$.each(result.data.resultList,function(ide,item){
							var type =item.type;
							var n = -1;
							for (var i = 0; i < typeArr.length; i++){
								if(type == typeArr[i])n=i;
							}
							var newKeyArr = keyArr[n];
							if(type == '物流' && newKeyArr.length == 0){
								newKeyArr.push("寄件人姓名");
								newKeyArr.push("寄件人手机号");
								newKeyArr.push("寄件人座机");
								newKeyArr.push("寄件人地址");
								newKeyArr.push("寄件人地区");
								newKeyArr.push("收件人姓名");
								newKeyArr.push("收件人手机号");
								newKeyArr.push("收件人座机");
								newKeyArr.push("收件人地址");
								newKeyArr.push("收件人地区");
								newKeyArr.push("货物内容");
								newKeyArr.push("下单日期");
								newKeyArr.push("下单时间");
								newKeyArr.push("源文件");
								keyArr[n] = newKeyArr;
							} else if (type != '物流'){
								$.each(item.data,function(key,value){
									newKeyArr.push(key);
								})
								keyArr[n] = newKeyArr.unique();
							}
						})
						var valueStrArr = new Array();
						for(var i = 0;i < keyArr.length; i++){
							valueStrArr.push("");
						}
						$.each(result.data.resultList,function(ide,item){
							var type =item.type;
							var n = -1;
							for (var i = 0; i < typeArr.length; i++){
								if(type == typeArr[i])n=i;
							}
							var keys = keyArr[n];
							var valueStr = valueStrArr[n];
							valueStr += "<tr>"
							var flag = false;
							for (var i =0; i<keys.length ;i++){
								//物流寄件人地区处理
								if(type == '物流' && keys[i] == '寄件人地区'){
									var linkArea = "";
									if (item.data.寄件人省 && item.data.寄件人省 != 'NA'){
										linkArea += item.data.寄件人省
									}
									if (item.data.寄件人市 && item.data.寄件人市 != 'NA'){
										linkArea += item.data.寄件人市
									}
									if (item.data.寄件人县 && item.data.寄件人县 != 'NA'){
										linkArea += item.data.寄件人县
									}
									valueStr += "<td style='background: white;'>"+linkArea+"</td>";
									continue;
								} else if (type == '物流' && keys[i] == '收件人地区'){
									var Area = "";
									if (item.data.收件人省 && item.data.收件人省 != 'NA'){
										Area += item.data.收件人省
									}
									if (item.data.收件人市 && item.data.收件人市 != 'NA'){
										Area += item.data.收件人市
									}
									if (item.data.收件人县 && item.data.收件人县 != 'NA'){
										Area += item.data.收件人县
									}
									valueStr += "<td style='background: white;'>"+Area+"</td>";
									continue;
								} else if(type == '物流' && keys[i] == '货物内容'){
									var goodName = "";
									if (item.data.货物名称  && item.data.货物名称 != 'NA'){
										goodName += item.data.货物名称;
									}
									if (item.data.货物内容  && item.data.货物内容 != 'NA'){
										goodName += item.data.货物内容;
									}
									valueStr += "<td style='background: white;'>"+goodName+"</td>";
									continue;
								}
								
								if (item.data[keys[i]] && item.data[keys[i]] != 'NA'){
									if (keys[i] == '源文件'){
										flag = true;
										continue;
									}
									valueStr += "<td style='background: white;'>"+item.data[keys[i]]+"</td>";
								} else {
									valueStr += "<td style='background: white;'></td>";
								}
							}
							if (flag){
								if(item.data['源文件']){
									valueStr += "<td style='background: white;'>"+item.data['源文件']+"</td>";
								} else {
									valueStr += "<td style='background: white;'></td>";
								}
							}
							valueStr += "<td style='background: white;'>"+item.index+"</td><td style='background: white;'>"+item.type+"</td>";
							valueStr += "</tr>";
							valueStrArr[n] = valueStr;
						})
						
						for (var i = 0 ; i<keyArr.length; i++){
							var keys = keyArr[i];
							tables += "<br><table style='width:100%;'><tr>";
							var flag = false;
							for (var j = 0; j < keys.length; j++){
								if(keys[j] == '源文件'){
									flag = true;
									continue;
								}
								tables += "<td style='background: #EEE8AA;'>"+keys[j]+"</td>";
							}
							if(flag)tables += "<td style='background: #EEE8AA;'>源文件</td>";
							tables += "<td style='background: #EEE8AA;'>库</td><td style='background: #EEE8AA;'>表</td>"
							tables += "</tr>";
							tables += valueStrArr[i];
							tables += "</table>";
						}
						$("#resultsIndex").append(tables);
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
			document.getElementById('background').style.display='none';
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
					var rowNumPerPage = 40;
					resultNum = result.data.resultList.length;
					if (result.data.resultList && result.data.resultList.length > 0) {
						var tables = "";
						var typeArr = new Array();
						$.each(result.data.resultList,function(ide,item){
							typeArr.push(item.type);
						})
						typeArr = typeArr.unique();
						var keyArr = new Array();
						for(var i = 0;i < typeArr.length; i++){
							keyArr.push(new Array());
						}
						
						$.each(result.data.resultList,function(ide,item){
							var type =item.type;
							var n = -1;
							for (var i = 0; i < typeArr.length; i++){
								if(type == typeArr[i])n=i;
							}
							var newKeyArr = keyArr[n];
							if(type == '物流' && newKeyArr.length == 0){
								newKeyArr.push("寄件人姓名");
								newKeyArr.push("寄件人手机号");
								newKeyArr.push("寄件人座机");
								newKeyArr.push("寄件人地址");
								newKeyArr.push("寄件人地区");
								newKeyArr.push("收件人姓名");
								newKeyArr.push("收件人手机号");
								newKeyArr.push("收件人座机");
								newKeyArr.push("收件人地址");
								newKeyArr.push("收件人地区");
								newKeyArr.push("货物内容");
								newKeyArr.push("下单日期");
								newKeyArr.push("下单时间");
								newKeyArr.push("源文件");
								keyArr[n] = newKeyArr;
							} else if (type != '物流'){
								$.each(item.data,function(key,value){
									newKeyArr.push(key);
								})
								keyArr[n] = newKeyArr.unique();
							}
						})
						var valueStrArr = new Array();
						for(var i = 0;i < keyArr.length; i++){
							valueStrArr.push("");
						}
						$.each(result.data.resultList,function(ide,item){
							var type =item.type;
							var n = -1;
							for (var i = 0; i < typeArr.length; i++){
								if(type == typeArr[i])n=i;
							}
							var keys = keyArr[n];
							var valueStr = valueStrArr[n];
							valueStr += "<tr>"
							var flag = false;
							for (var i =0; i<keys.length ;i++){
								//物流寄件人地区处理
								if(type == '物流' && keys[i] == '寄件人地区'){
									var linkArea = "";
									if (item.data.寄件人省 && item.data.寄件人省 != 'NA'){
										linkArea += item.data.寄件人省
									}
									if (item.data.寄件人市 && item.data.寄件人市 != 'NA'){
										linkArea += item.data.寄件人市
									}
									if (item.data.寄件人县 && item.data.寄件人县 != 'NA'){
										linkArea += item.data.寄件人县
									}
									valueStr += "<td style='background: white;'>"+linkArea+"</td>";
									continue;
								} else if (type == '物流' && keys[i] == '收件人地区'){
									var Area = "";
									if (item.data.收件人省 && item.data.收件人省 != 'NA'){
										Area += item.data.收件人省
									}
									if (item.data.收件人市 && item.data.收件人市 != 'NA'){
										Area += item.data.收件人市
									}
									if (item.data.收件人县 && item.data.收件人县 != 'NA'){
										Area += item.data.收件人县
									}
									valueStr += "<td style='background: white;'>"+Area+"</td>";
									continue;
								} else if(type == '物流' && keys[i] == '货物内容'){
									var goodName = "";
									if (item.data.货物名称  && item.data.货物名称 != 'NA'){
										goodName += item.data.货物名称;
									}
									if (item.data.货物内容  && item.data.货物内容 != 'NA'){
										goodName += item.data.货物内容;
									}
									valueStr += "<td style='background: white;'>"+goodName+"</td>";
									continue;
								}
								
								if (item.data[keys[i]] && item.data[keys[i]] != 'NA'){
									if (keys[i] == '源文件'){
										flag = true;
										continue;
									}
									valueStr += "<td style='background: white;'>"+item.data[keys[i]]+"</td>";
								} else {
									valueStr += "<td style='background: white;'></td>";
								}
							}
							if (flag){
								if(item.data['源文件']){
									valueStr += "<td style='background: white;'>"+item.data['源文件']+"</td>";
								} else {
									valueStr += "<td style='background: white;'></td>";
								}
							}
							valueStr += "<td style='background: white;'>"+item.index+"</td><td style='background: white;'>"+item.type+"</td>";
							valueStr += "</tr>";
							valueStrArr[n] = valueStr;
						})
						
						for (var i = 0 ; i<keyArr.length; i++){
							var keys = keyArr[i];
							tables += "<br><table style='width:100%;'><tr>";
							var flag = false;
							for (var j = 0; j < keys.length; j++){
								if(keys[j] == '源文件'){
									flag = true;
									continue;
								}
								tables += "<td style='background: #EEE8AA;'>"+keys[j]+"</td>";
							}
							if(flag)tables += "<td style='background: #EEE8AA;'>源文件</td>";
							tables += "<td style='background: #EEE8AA;'>库</td><td style='background: #EEE8AA;'>表</td>"
							tables += "</tr>";
							tables += valueStrArr[i];
							tables += "</table>";
						}
						$("#resultsIndex").append(tables);
					} else {
						$("#resultsIndex").append("未找到相关数据")
					}
				} else {
					$("#resultsIndex").append("未找到相关数据")
				}
			}
			$("#submitIndex").attr("disabled", false); 
			$("#submitIndex").attr("style", "color:black;"); 
			document.getElementById('background').style.display='none';
		});
	}
	
	//数组去重
	Array.prototype.unique = function(){
		var res = [];
		var json = {};
		for(var i = 0; i < this.length; i++){
			if(!json[this[i]]){
				res.push(this[i]);
				json[this[i]] = 1;
			}
		}
		return res;
	}
	
	//导出
	$("#poiExpExcel").click(function(){
		var query = $("#query").val();
		if(query==""||query==null){
			swal("导出内容不能为空!");
		}else{
			var url = "export/xls?query=" + query;
			window.location.href=url;
		}
	})
});


//enter 控件
function EnterPressIndex(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
		document.getElementById("submitIndex").click(); 
	} 
}; 