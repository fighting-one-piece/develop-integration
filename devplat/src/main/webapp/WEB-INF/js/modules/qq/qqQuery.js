$(document).ready(function(){
		var scrollId = null;
		var rowNumPerPage = 10;
		//显示DIV
		function showDIV(){
			document.getElementById('light').style.display='block';
			document.getElementById('fade').style.display='block';
		}
		$("#Colsebtn").click(function(){
			document.getElementById('light').style.display='none';
			document.getElementById('fade').style.display='none';
		});
		$("#results a").live("click",function(){
			var qunNum = $(this).text();
			searchQQA(qunNum);
			showDIV();
		})
		$("#submitQQ").click(function(){
			document.getElementById('background').style.display='block';
			$("#nextQQNick").hide();
			$(".styleQQThead tr").empty();
			$(".styleQQTbody tr").empty();
			$("#resultsQQ .ss").empty();
			$(".styleThead tr").empty();
			$(".styleTbody tr").empty();
			$("#results .ss").empty();
			$("#count").empty();
			var chkObjs = document.getElementsByName("radio");
	        for(var i=0;i<chkObjs.length;i++){
	            if(chkObjs[i].checked==true){
	                if(chkObjs[i].value =="qq"){
	        			readQQBaseQun();
	                }else if(chkObjs[i].value =="qqqun"){
	        			var qunNum=$("#query").val();
	        			searchQQ(qunNum);
	                }else if(chkObjs[i].value =="qqnick"){
	                	scrollId = "";
	        			searchQQNick();
	                }
	            }
	        }
		});
		//关系图与数据间切换
		$("#toShowForceDiv").click(function(){
			console.log(123213)
			$("#resultsQQ").css("visibility","hidden");
			$("#results").css("visibility","hidden");
			$("#toShowForceDiv").css("visibility","hidden");
			$("#QQforceDiv").css("visibility","visible");
			$("#toShowQQresult").css("visibility","visible");
		})
		$("#toShowQQresult").click(function(){
			$("#QQforceDiv").css("visibility","hidden");
			$("#toShowQQresult").css("visibility","hidden");
			$("#resultsQQ").css("visibility","visible");
			$("#results").css("visibility","visible");
			$("#toShowForceDiv").css("visibility","visible");
		})
		//点击搜索时恢复到初始状态
		$("#submitQQ").click(function(){
			$("#toShowForceDiv").css("visibility","hidden");
			$("#QQforceDiv").css("visibility","hidden");
			$("#toShowQQresult").css("visibility","hidden");
			$("#resultsQQ").css("visibility","visible");
			$("#results").css("visibility","visible");
			cleanForce();
		})
		
		$("#nextQQNick").click(function(){
			document.getElementById('background').style.display='block';
			$("#submitQQ").attr("disabled", true); 
			$("#nextQQNick").attr("disabled", true); 
			$("#submitQQ").attr("style", "color:gray;"); 
			$("#nextQQNick").attr("style", "color:gray;");
			$("#nextQQNick").hide();
			$(".styleQQThead tr").empty();
			$(".styleQQTbody tr").empty();
			$("#resultsQQ .ss").empty();
			$(".styleThead tr").empty();
			$(".styleTbody tr").empty();
			$("#results .ss").empty();
			$("#count").empty();
			var chkObjs = document.getElementsByName("radio");
			 for(var i=0;i<chkObjs.length;i++){
		            if(chkObjs[i].checked==true){
		                if(chkObjs[i].value =="qq"){
		                	readQQBaseQun();
		                }else if(chkObjs[i].value =="qqqun"){
		        			var qunNum=$("#query").val();
		        			searchQQ(qunNum);
		                }else if(chkObjs[i].value =="qqnick"){
		        			searchQQNick();
		                }
		            }
		        }
		});
		
		//通过QQ昵称查询
		function searchQQNick() {
//			var qqnick = encodeURI($("#query").val());
			var qqnick = $("#query").val();
			console.log(qqnick);
			$("#count").empty();
				$.ajax({
					url:"qq/nickname/search",
					type:"post",
					dataType:"json",
					data:{"nickname":qqnick,"scrollId":scrollId,"rowNumPerPage":rowNumPerPage},
					success:function(result){
						if(result.data){
							es = result.data.es;
							scrollId = result.data.scrollId;
							var totalRowNum = result.data.totalRowNum;
							if (result.data.resultList && result.data.resultList.length > 0){
								if (result.data.resultList.length < 10 || totalRowNum == 10) {
									$("#nextQQNick").hide();
								}else {
									$("#nextQQNick").attr("disabled", false); 
									$("#nextQQNick").attr("style", "color:black;");
								}
								var thead = "<tr>";
//								for(var i=0;i<result.data.resultList.length;i++){
//									for(var key in result.data.resultList[i].data){
//										thead+="<td>"+key+"</td>";
//									}
//									break;
//								}
								thead+="<td>"+"QQ昵称"+"</td>";
								thead+="<td>"+"QQ群号"+"</td>";
								thead+="<td>"+"群名称"+"</td>";
								thead+="<td>"+"QQ号"+"</td>";
								thead+="<td>"+"年龄"+"</td>";
								thead+="<td>"+"性别"+"</td>";
								thead+="<td>"+"群通知"+"</td>";
								$(".styleThead").html(thead+="</tr>");
								
								var html ="";
								for(var i=0;i<result.data.resultList.length;i++){
									html+="<tr align='left'>";
									html+="<td>"+result.data.resultList[i].data['QQ昵称']+"</td>";
									if (! result.data.resultList[i].data['QQ群号']) {
										html+="<td></td>";
									}else {
										html+="<td><a>"+result.data.resultList[i].data['QQ群号']+"</a></td>";
									}
									if (! result.data.resultList[i].data['群名称']) {
										html+="<td></td>";
									}else {
										html+="<td>"+result.data.resultList[i].data['群名称']+"</td>";
									}
									if (! result.data.resultList[i].data['QQ号']) {
										html+="<td></td>";
									}else {
										html+="<td>"+result.data.resultList[i].data['QQ号']+"</td>";
									}
									if (! result.data.resultList[i].data['年龄']) {
										html+="<td></td>";
									}else {
										html+="<td>"+result.data.resultList[i].data['年龄']+"</td>";
									}
									if (! result.data.resultList[i].data['性别']) {
										html+="<td></td>";
									}else {
										html+="<td>"+result.data.resultList[i].data['性别']+"</td>";
									}
									if (! result.data.resultList[i].data['群通知']) {
										html+="<td></td>";
									}else {
										html+="<td>"+result.data.resultList[i].data['群通知']+"</td>";
									}
									html+="</tr>";
								}
								//清除QQ基本信息并提示暂无数据
								$(".styleQQThead tr").empty();
								$(".styleQQTbody tr").empty();
								$("#resultsQQ .ss").empty();
								//清除群基本信息并提示数据量
								$(".styleTbody").html(html);
								$("#results .ss").empty();
								$("#count").empty();
								$("#count").append("搜索共" + result.data.totalRowNum + "结果</br>");
								
							} else {
								//清除群基本信息并提示暂无数据
								$(".styleThead tr").empty();
								$(".styleTbody tr").empty();
								$("#results .ss").empty();
								$("#results .ss").append("未找到相关数据");
								$("#count").empty();
								//清除QQ基本信息并提示暂无数据
								$(".styleQQThead tr").empty();
								$(".styleQQTbody tr").empty();
								$("#resultsQQ .ss").empty();
							}
						}else{
							//清除群基本信息并提示暂无数据
							$(".styleThead tr").empty();
							$(".styleTbody tr").empty();
							$("#results .ss").empty();
							$("#results .ss").append("未找到相关数据");
							$("#count").empty();
							//清除QQ基本信息并提示暂无数据
							$(".styleQQThead tr").empty();
							$(".styleQQTbody tr").empty();
							$("#resultsQQ .ss").empty();
						}
						$("#submitQQ").attr("disabled", false); 
						$("#submitQQ").attr("style", "color:black;"); 
						document.getElementById('background').style.display='none';
					},
					error:function(){
						console.log("ajax发送请求失败！");
						document.getElementById('background').style.display='none';
					}
				})
		}
		//整合
		function readQQBaseQun(){
			var qq = $("#query").val();
			if(qq.length > 0){
				$.ajax({
					url:"qq/search",
					type:"get",
					dataType:"json",
					data:{"qqNum":qq},
					success:function(result){
						console.log(!result.data)
						if(result.data){
							$("#results .ss").empty();
							$("#resultsQQ .ss").empty();
							if(result.data["qqBase"].length > 0){
								//拼接表头
								var theadQQ = "<tr>";
								for (var i = 0; i < result.data["qqBase"].length; i++) {
									for ( var key in result.data["qqBase"][i]) {
										theadQQ+="<td>"+key+"</td>";
									}
									break;
								}
								$(".styleQQThead").html(theadQQ+="</tr>");
								//拼接内容
								var htmlQQ = "";
								for(var j = 0; j < result.data["qqBase"].length; j++){
									htmlQQ += "<tr>";
									for(var key in result.data["qqBase"][i]){
										htmlQQ += "<td>"+result.data["qqBase"][i][key]+"</td>";
									}
									htmlQQ += "</tr>";
								}
								$(".styleQQTbody").html(htmlQQ);
							}
							if(result.data["qun"].length > 0){
								//拼接群信息
								var thead = "<tr><td>QQ群号</td><td>群名称</td><td>创建时间</td><td>群人数</td><td>用户昵称</td><td>群通知</td></tr>";
								$(".styleThead").html(thead);
								var html ="";
								for(var i = 0;i<result.data["qun"].length;i++){
									html+="<tr>"+
									"<td><a>"+result.data["qun"][i]["QQ群号"]+"</a></td>"+
									"<td>"+result.data["qun"][i]["群名称"]+"</td>"+
									"<td>"+result.data["qun"][i]["创建时间"]+"</td>"+
									"<td>"+result.data["qun"][i]["群人数"]+"</td>"+
									"<td>"+result.data["qun"][i]["nick"]+"</td>"+
									"<td>"+result.data["qun"][i]["群通知"]+"</td>"+
									"</tr>";
									
								}
								//清除群基本信息并提示数据量
								$(".styleTbody").html(html);
								$("#results .ss").empty();
								loadForce(result.data["qun"],result.data["qqBase"]);
							}
							if(result.data["qun"].length == 0 && result.data["qqBase"].length == 0){
								$("#results .ss").append("未找到相关数据");
							}
						}else{
							//清除QQ基本信息并提示暂无数据
							$(".styleQQThead tr").empty();
							$(".styleQQTbody tr").empty();
							$("#resultsQQ .ss").empty();
							//清除群基本信息并提示暂无数据
							$(".styleThead tr").empty();
							$(".styleTbody tr").empty();
							$("#results .ss").empty();
							$("#results .ss").append("抱歉!该查询涉及敏感信息");
						}
						$("#submitQQ").attr("disabled", false); 
						$("#submitQQ").attr("style", "color:black;");
						document.getElementById('background').style.display='none';
					},
					error:function(){
						console.log("ajax发送请求失败！");
						document.getElementById('background').style.display='none';
					}
				})
			}
		}
		//当前页面显示
		function searchQQ(qunNum){
			if(qunNum.length > 0){
				$.ajax({
					url:"qq/qun/search",
					type:"get",
					dataType:"json",
					data:{"qunNum":qunNum},
					success:function(result){
						if(result.data){
							if (result.data && result.data.length > 0){
								var thead = "<tr>";
								for(var i=0;i<result.data.length;i++){
									for(var key in result.data[i]){
										thead+="<td>"+key+"</td>"
									}
									break;
								}
								$(".styleThead").html(thead+="</tr>");
								
								var html ="";
								for(var i=0;i<result.data.length;i++){
									
									html+="<tr>";
									for(var key in result.data[i]){
										
										html+="<td>"+result.data[i][key]+"</td>"
										
									}
									html+="</tr>";
								}
								//清除QQ基本信息并提示暂无数据
								$(".styleQQThead tr").empty();
								$(".styleQQTbody tr").empty();
								$("#resultsQQ .ss").empty();
								//清除群基本信息并提示数据量
								$(".styleTbody").html(html);
								$("#results .ss").empty();
								$("#count").empty();
							} else {
								//清除群基本信息并提示暂无数据
								$(".styleThead tr").empty();
								$(".styleTbody tr").empty();
								$("#results .ss").empty();
								$("#results .ss").append("未找到相关数据");
								$("#count").empty();
								//清除QQ基本信息并提示暂无数据
								$(".styleQQThead tr").empty();
								$(".styleQQTbody tr").empty();
								$("#resultsQQ .ss").empty();
							}
						}else{
							//清除群基本信息并提示暂无数据
							$(".styleThead tr").empty();
							$(".styleTbody tr").empty();
							$("#results .ss").empty();
							$("#results .ss").append("未找到相关数据");
							$("#count").empty();
							//清除QQ基本信息并提示暂无数据
							$(".styleQQThead tr").empty();
							$(".styleQQTbody tr").empty();
							$("#resultsQQ .ss").empty();
						}
						$("#submitQQ").attr("disabled", false); 
						$("#submitQQ").attr("style", "color:black;"); 
						document.getElementById('background').style.display='none';
					},
					error:function(){
						console.log("ajax发送请求失败！");
						document.getElementById('background').style.display='none';
					}
				})
			}
		}
		//另一个页面显示
		function searchQQA(qunNum){
			$.ajax({
				url:"qq/qun/search",
				type:"get",
				dataType:"json",
				data:{"qunNum":qunNum},
				success:function(result){
					if (result.data && result.data.length > 0){
						var thead = "<tr>";
						for(var i=0;i<result.data.length;i++){
							for(var key in result.data[i]){
								thead+="<td>"+key+"</td>"
							}
							break;
						}
						$(".theadStyle").html(thead+="</tr>");
						var html ="";
						for(var i=0;i<result.data.length;i++){
							
							html+="<tr>";
							for(var key in result.data[i]){
								
								html+="<td>"+result.data[i][key]+"</td>"
								
							}
							html+="</tr>";
						}
						$(".tbodyStyle").html(html);
						
					} else {
						$("#results").empty();
						$("#light").append("未找到相关数据")
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
				},
				error:function(){
					alert("错误");
				}
			})
		}
	});


//enter 控件
function EnterPressQuery(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitQQ").focus(); 
	} 
	}; 
	
