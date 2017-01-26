$(document).ready(function(){
	
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
	
	var scrollId = null;
	var rowNumPerPage = 40;
	var query;
	var excludeArr = new Array();
	excludeArr.push("account");
	excludeArr.push("email");
	excludeArr.push("qqqundata");
	excludeArr.push("qqqunrelation");
	excludeArr.push("business");
	excludeArr.push("telecom");
	excludeArr.push("finance");
	excludeArr.push("logistics");
	$("#submitapp").click(function(){
		document.getElementById('background').style.display='block';
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
			$("#appresult").append("<br>")
			//是否已经有请求完成
			var flag1 = false;
			//是否之前有数据
			var flag2 = false;
			var flag3 = false;
			var k = 1;
			var m = 1;
			
			$.ajax({
				url:"/devplat/labels/exclude/search",
				type:"get",
				dataType:"json",
				traditional: true,
				data:{"query":query,"excludeTypes":excludeArr},
				success:function(result){
					if (result.code == 1) {
						if(result.data.length > 0){
							flag2 = false;
							$.each(result.data,function(index,value){
								if (m > 11 * k) {
									$("#appresult").append("<br><br>");
									k++;	
								}
								m ++;
								var app = "<a id='"+value.index+"' class='"+value.type+"' href='#'>"+value.label+"("+value.hits+"条)"+"</a>&nbsp;&nbsp;";
								$("#appresult").append(app);
							})
							document.getElementById('background').style.display='none';
						}else {
							//如果之前已有请求完成，并且没有数据
							if(flag1 && flag2){
								$("#appresult").append("未找到相关信息");
							} else {
								flag1 = true;
								flag2 = true;
							}
						}
					}else {
						if (flag1 && flag2){
							$("#appresult").append(result.failure);
						} else {
							flag1 = true;
							flag2 = true;
						}
					}
					$("#submitapp").attr("disabled", false); 
					$("#submitapp").attr("style", "color:black;"); 
					if(flag3)document.getElementById('background').style.display='none';
					flag3 = true;
				},
				error:function(){
					console.log("ajax发送请求失败！");
					document.getElementById('background').style.display='none';
				}
			});
			
			$.ajax({
				url:"/devplat/labels/include/search",
				type:"get",
				dataType:"json",
				traditional: true,
				data:{"query":query,"includeTypes":excludeArr},
				success:function(result){
					if (result.code == 1) {
						if(result.data.length > 0){
							flag2 = false;
							$.each(result.data,function(index,value){
								if (m > 11 * k) {
									$("#appresult").append("<br><br>");
									k++;	
								}
								m ++;
								var app = "<a id='"+value.index+"' class='"+value.type+"' href='#'>"+value.label+"("+value.hits+"条)"+"</a>&nbsp;&nbsp;";
								$("#appresult").append(app);
							})
							document.getElementById('background').style.display='none';
						}else {
							//如果之前已有请求完成，并且没有数据
							if(flag1 && flag2){
								$("#appresult").append("未找到相关信息");
							} else {
								flag1 = true;
								flag2 = true;
							}
						}
					}else {
						if (flag1 && flag2){
							$("#appresult").append(result.failure);
						} else {
							flag1 = true;
							flag2 = true;
						}
					}
					$("#submitapp").attr("disabled", false); 
					$("#submitapp").attr("style", "color:black;"); 
					if(flag3)document.getElementById('background').style.display='none';
					flag3 = true;
				},
				error:function(){
					console.log("ajax发送请求失败！");
					document.getElementById('background').style.display='none';
				}
			});
			
		}
	}
	var index;
	var type;
	//点击a标签
	$("#appresult a").live("click",function(){
		document.getElementById('background').style.display='block';
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
					if (result.data.resultList.length == rowNumPerPage) {
						$("#nextapp").show();
					}else {
						$("#nextapp").hide();
					}
					if (result.data.resultList.length > 0) {
						var key = "<table style='width:100%;'><tr>";
						var value = "<tr>";
						var keyArr = new Array();
						$.each(result.data.resultList,function(index,resultList){
							$.each(resultList,function(key1,value1){
								if (key1 == "data") {
									if(type == 'logistics'){
										keyArr.push("寄件人姓名");
										keyArr.push("寄件人手机号");
										keyArr.push("寄件人座机");
										keyArr.push("寄件人地址");
										keyArr.push("寄件人地区");
										keyArr.push("收件人姓名");
										keyArr.push("收件人手机号");
										keyArr.push("收件人座机");
										keyArr.push("收件人地址");
										keyArr.push("收件人地区");
										keyArr.push("货物内容");
										keyArr.push("下单日期");
										keyArr.push("下单时间");
										keyArr.push("源文件");
									} else{
										
										$.each(value1,function(key2,value2){
											keyArr.push(key2);
										})
									}
								}
							})
						});
						
						keyArr = keyArr.unique();
						var flag = false;
						for (var i=0; i<keyArr.length;i++){
							if (keyArr[i] == '源文件'){
								flag = true;
								continue;
							}
							key +="<td style='background: #EEE8AA;'>"+keyArr[i]+"</td>";
						}
						if (flag){
							key +="<td style='background: #EEE8AA;'>源文件</td>";
						}
						if(type == 'logistics' ){
							$.each(result.data.resultList,function(index,resultList){
								$.each(resultList,function(key1,value1){
									if (key1 == "data") {
										if(value1.寄件人姓名 && value1.寄件人姓名 != 'NA'){
											value +="<td>"+value1.寄件人姓名+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.寄件人手机号 && value1.寄件人手机号 != 'NA'){
											value +="<td>"+value1.寄件人手机号+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.寄件人座机 && value1.寄件人座机 != 'NA'){
											value +="<td>"+value1.寄件人座机+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.寄件人地址 && value1.寄件人地址 != 'NA'){
											value +="<td>"+value1.寄件人地址+"</td>";
										} else {
											value +="<td></td>";
										}
										var linkArea = "";
										if (value1.寄件人省 && value1.寄件人省 != 'NA'){
											linkArea += value1.寄件人省
										}
										if (value1.寄件人市 && value1.寄件人市 != 'NA'){
											linkArea += value1.寄件人市
										}
										if (value1.寄件人县 && value1.寄件人县 != 'NA'){
											linkArea += value1.寄件人县
										}
										value += "<td>"+linkArea+"</td>";
										if(value1.收件人姓名 && value1.收件人姓名 != 'NA'){
											value +="<td>"+value1.收件人姓名+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.收件人手机号 && value1.收件人手机号 != 'NA'){
											value +="<td>"+value1.收件人手机号+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.收件人座机 && value1.收件人座机 != 'NA'){
											value +="<td>"+value1.收件人座机+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.收件人地址 && value1.收件人地址 != 'NA'){
											value +="<td>"+value1.收件人地址+"</td>";
										} else {
											value +="<td></td>";
										}
										var Area = "";
										if (value1.收件人省 && value1.收件人省 != 'NA'){
											Area += value1.收件人省
										}
										if (value1.收件人市 && value1.收件人市 != 'NA'){
											Area += value1.收件人市
										}
										if (value1.收件人县 && value1.收件人县 != 'NA'){
											Area += value1.收件人县
										}
										value += "<td>"+Area+"</td>";
										var goodName = "";
										if (value1.货物名称  && value1.货物名称 != 'NA'){
											goodName += value1.货物名称;
										}
										if (value1.货物内容  && value1.货物内容 != 'NA'){
											goodName += value1.货物内容;
										}
										value += "<td>"+goodName+"</td>";
										if(value1.下单日期 && value1.下单日期 != 'NA'){
											value +="<td>"+value1.下单日期+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.下单时间 && value1.下单时间 != 'NA'){
											value +="<td>"+value1.下单时间+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.源文件 && value1.源文件 != 'NA'){
											value +="<td>"+value1.源文件+"</td>";
										} else {
											value +="<td></td>";
										}
										value += "</tr>"
									}
								})
							});
						} else {
							
							$.each(result.data.resultList,function(index,resultList){
								$.each(resultList,function(key1,value1){
									if (key1 == "data") {
										for (var i=0; i<keyArr.length;i++){
											if(keyArr[i] == '源文件'){
												continue;
											}
											if(value1[keyArr[i]]){
												value +="<td>"+value1[keyArr[i]]+"</td>";
											} else {
												value +="<td></td>";
											}
										}
										if(flag){
											if (value1['源文件']){
												value +="<td>"+value1['源文件']+"</td>";
											} else {
												value +="<td></td>";
											}
										}
										value += "</tr>"
									}
								})
							});
						}
						$("#appresults").append("<br>"+key+value+"</table>");
						
					}else {
						$("#appresults").append("未找到相关信息");
					}
				}else {
					$("#appresults").append(result.failure);
				}
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("ajax发送请求失败！");
				document.getElementById('background').style.display='none';
			}
		});
	});
	
	//点击下一页
	$("#nextapp").live("click",function(){
		document.getElementById('background').style.display='block';
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
					if (result.data.resultList.length == rowNumPerPage) {
						$("#nextapp").show();
					}else {
						$("#nextapp").hide();
					}
					if (result.data.resultList.length > 0) {
						var key = "<table style='width:100%;'><tr>";
						var value = "<tr>";
						var keyArr = new Array();
						$.each(result.data.resultList,function(index,resultList){
							$.each(resultList,function(key1,value1){
								if (key1 == "data") {
									if(type == 'logistics'){
										keyArr.push("寄件人姓名");
										keyArr.push("寄件人手机号");
										keyArr.push("寄件人座机");
										keyArr.push("寄件人地址");
										keyArr.push("寄件人地区");
										keyArr.push("收件人姓名");
										keyArr.push("收件人手机号");
										keyArr.push("收件人座机");
										keyArr.push("收件人地址");
										keyArr.push("收件人地区");
										keyArr.push("货物内容");
										keyArr.push("下单日期");
										keyArr.push("下单时间");
										keyArr.push("源文件");
									} else{
										
										$.each(value1,function(key2,value2){
											keyArr.push(key2);
										})
									}
								}
							})
						});
						
						keyArr = keyArr.unique();
						var flag = false;
						for (var i=0; i<keyArr.length;i++){
							if (keyArr[i] == '源文件'){
								flag = true;
								continue;
							}
							key +="<td style='background: #EEE8AA;'>"+keyArr[i]+"</td>";
						}
						if (flag){
							key +="<td style='background: #EEE8AA;'>源文件</td>";
						}
						if(type == 'logistics' ){
							$.each(result.data.resultList,function(index,resultList){
								$.each(resultList,function(key1,value1){
									if (key1 == "data") {
										if(value1.寄件人姓名 && value1.寄件人姓名 != 'NA'){
											value +="<td>"+value1.寄件人姓名+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.寄件人手机号 && value1.寄件人手机号 != 'NA'){
											value +="<td>"+value1.寄件人手机号+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.寄件人座机 && value1.寄件人座机 != 'NA'){
											value +="<td>"+value1.寄件人座机+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.寄件人地址 && value1.寄件人地址 != 'NA'){
											value +="<td>"+value1.寄件人地址+"</td>";
										} else {
											value +="<td></td>";
										}
										var linkArea = "";
										if (value1.寄件人省 && value1.寄件人省 != 'NA'){
											linkArea += value1.寄件人省
										}
										if (value1.寄件人市 && value1.寄件人市 != 'NA'){
											linkArea += value1.寄件人市
										}
										if (value1.寄件人县 && value1.寄件人县 != 'NA'){
											linkArea += value1.寄件人县
										}
										value += "<td>"+linkArea+"</td>";
										if(value1.收件人姓名 && value1.收件人姓名 != 'NA'){
											value +="<td>"+value1.收件人姓名+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.收件人手机号 && value1.收件人手机号 != 'NA'){
											value +="<td>"+value1.收件人手机号+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.收件人座机 && value1.收件人座机 != 'NA'){
											value +="<td>"+value1.收件人座机+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.收件人地址 && value1.收件人地址 != 'NA'){
											value +="<td>"+value1.收件人地址+"</td>";
										} else {
											value +="<td></td>";
										}
										var Area = "";
										if (value1.收件人省 && value1.收件人省 != 'NA'){
											Area += value1.收件人省
										}
										if (value1.收件人市 && value1.收件人市 != 'NA'){
											Area += value1.收件人市
										}
										if (value1.收件人县 && value1.收件人县 != 'NA'){
											Area += value1.收件人县
										}
										value += "<td>"+Area+"</td>";
										var goodName = "";
										if (value1.货物名称  && value1.货物名称 != 'NA'){
											goodName += value1.货物名称;
										}
										if (value1.货物内容  && value1.货物内容 != 'NA'){
											goodName += value1.货物内容;
										}
										value += "<td>"+goodName+"</td>";
										if(value1.下单日期 && value1.下单日期 != 'NA'){
											value +="<td>"+value1.下单日期+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.下单时间 && value1.下单时间 != 'NA'){
											value +="<td>"+value1.下单时间+"</td>";
										} else {
											value +="<td></td>";
										}
										if(value1.源文件 && value1.源文件 != 'NA'){
											value +="<td>"+value1.源文件+"</td>";
										} else {
											value +="<td></td>";
										}
										value += "</tr>"
									}
								})
							});
						} else {
							
							$.each(result.data.resultList,function(index,resultList){
								$.each(resultList,function(key1,value1){
									if (key1 == "data") {
										for (var i=0; i<keyArr.length;i++){
											if(keyArr[i] == '源文件'){
												continue;
											}
											if(value1[keyArr[i]]){
												value +="<td>"+value1[keyArr[i]]+"</td>";
											} else {
												value +="<td></td>";
											}
										}
										if(flag){
											if (value1['源文件']){
												value +="<td>"+value1['源文件']+"</td>";
											} else {
												value +="<td></td>";
											}
										}
										value += "</tr>"
									}
								})
							});
						}
						$("#appresults").append("<br>"+key+value+"</table>");
						
					}else {
						$("#appresults").append("未找到相关信息");
					}
				}else {
					$("#appresults").append("未找到相关信息");
				}
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("ajax发送请求失败！");
				document.getElementById('background').style.display='none';
			}
		});
	});
});


//enter 控件
function EnterPressapp(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitapp").click(); 
	} 
	}; 
