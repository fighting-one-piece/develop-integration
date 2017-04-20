$(document).ready(function(){
		$("#submitQQ").click(function(){
			$("#submitQQ").attr("disabled", true); 
			$("#submitQQ").attr("style", "color:gray;"); 
			$("#nextQQNick").hide();
			$(".styleQQThead tr").empty();
			$(".styleQQTbody tr").empty();
			$(".styleTbody").empty();
			$(".styleQQThead").empty();
			$("#resultsQQ .ss").empty();
			$(".styleThead tr").empty();
			$(".styleTbody tr").empty();
			$("#results .ss").empty();
			$("#count").empty();
			$("#pengyousearch").empty();
			var chkObjs = document.getElementsByName("radio");
	        for(var i=0;i<chkObjs.length;i++){
	            if(chkObjs[i].checked==true){
	                if(chkObjs[i].value =="qqtopyw"){
	                	qqtopyw();
	                }else if(chkObjs[i].value =="qqtowb"){
	                	qqtowb();
	                }else if(chkObjs[i].value =="qqtosession"){
	                	qqtosession();
	                }else if(chkObjs[i].value =="qqtotalk"){
	                	qqtotalk();
	                }else if(chkObjs[i].value =="pywtoqq"){
	                	pywtoqq();
	                }else if(chkObjs[i].value =="wbtoqq"){
	                	wbtoqq();
	                }else if(chkObjs[i].value =="phonetoqq"){
	                	phonetoqq();
	                }
	            }
	        }
		});
		
		function qqtopyw() {
			var qq = $("#query").val().trim();
			if (! qq.match(new RegExp("[1-9][0-9]{4,10}"))) {
				 $("#results .ss").append("不是QQ号码！"); 
				 $("#submitQQ").attr("disabled", false); 
				 $("#submitQQ").attr("style", "color:black;"); 
			}else {
				document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/qqtopyw",
				type:"get",
				dataType:"json",
				data:{"qq":qq},
				success:function(result){
					if (result.code == 1) {
						
						var frame = "<div style='width:98%;height:500px;'><iframe style='width:100%;height:100%' src='"+result.data.个人信息地址+"'></div>";
						$("#pengyousearch").append("<a href='"+result.data.朋友网地址+"' target='_blank'>朋友网："+result.data.朋友网地址+"</a><br/>");
						$("#pengyousearch").append("<a href='"+result.data.个人信息地址+"' target='_blank'>个人信息："+result.data.个人信息地址+"</a><br/>");
						$("#pengyousearch").append(frame);
						
					} else {
						$("#pengyousearch").append("未找到相关数据");
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
			}
		}
		
		function qqtowb() {
			var qq = $("#query").val().trim();
			if (! qq.match(new RegExp("[1-9][0-9]{4,10}"))) {
				 $("#results .ss").append("不是QQ号码！"); 
				 $("#submitQQ").attr("disabled", false); 
				 $("#submitQQ").attr("style", "color:black;"); 
			}else {
				document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/qqtowb",
				type:"get",
				dataType:"json",
				data:{"qq":qq},
				success:function(result){
					if (result.code == 1) {
						var wb = "<a href='"+result.data.微博地址+"' target='_blank'>微博："+result.data.微博名+"</a><br/>";
						$(".styleQQThead").append(wb);
					}else {
						$("#results .ss").append("未找到相关数据");
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
			}
		}
		
		function qqtosession() {
			var qq = $("#query").val().trim();
			if (! qq.match(new RegExp("[1-9][0-9]{4,10}"))) {
				 $("#results .ss").append("不是QQ号码！"); 
				 $("#submitQQ").attr("disabled", false); 
				 $("#submitQQ").attr("style", "color:black;"); 
			}else {
				document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/qqtosession",
				type:"get",
				dataType:"json",
				data:{"qq":qq},
				success:function(result){
					if (result.code == 1) {
						var session = "<a href='"+result.data+"'>查询结果："+result.data+"</a>"
						$(".styleQQThead").append(session);
					}else {
						$("#results .ss").append("未找到相关数据");
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
			}
		}
		
		function qqtotalk() {
			var qq = $("#query").val().trim();
			if (! qq.match(new RegExp("[1-9][0-9]{4,10}"))) {
				 $("#results .ss").append("不是QQ号码！"); 
				 $("#submitQQ").attr("disabled", false); 
				 $("#submitQQ").attr("style", "color:black;"); 
			}else {
				document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/qqtotalk",
				type:"get",
				dataType:"json",
				data:{"qq":qq},
				success:function(result){
					if (result.code == 1) {
						var adata = "<p>说说条数："+result.data.说说总条数+"</p>";
						adata += "<p>最后一次发说说时间："+result.data.最后说说时间+"</p>";
						adata += "<p>最后一次说说内容："+result.data.说说内容+"</p>";
						$(".styleTbody").append(adata);
					}else {
						$("#results .ss").append("未找到相关数据");
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
			}
		}
		
		function pywtoqq() {
			var url = $("#query").val().trim();
			document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/pywtoqq",
				type:"get",
				dataType:"json",
				data:{"url":url},
				success:function(result){
					if (result.code == 1) {
						var adata = "<p>朋友网名称："+result.data.朋友网名称+"</p>";
						adata += "<p>QQ账号："+result.data.QQ账号+"</p>";
						$(".styleTbody").append(adata);
					} else {
						$("#results .ss").append(result.failure);
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
		}
		
		function wbtoqq() {
			var url = $("#query").val().trim();
			document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/wbtoqq",
				type:"get",
				dataType:"json",
				data:{"url":url},
				success:function(result){
					if (result.code == 1) {
						var adata = "<p>微博名称："+result.data.微博名称+"</p>";
						adata += "<p>QQ账号："+result.data.QQ账号+"</p>";
						$(".styleTbody").append(adata);
					}else {
						$("#results .ss").append(result.failure);
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
		}
		
		function phonetoqq() {
			var phone = $("#query").val().trim();
			 if (! phone.match(new RegExp(/^1(3|4|5|7|8)\d{9}$/))) {  
				 $("#results .ss").append("不是电话号码！"); 
				 $("#submitQQ").attr("disabled", false); 
				 $("#submitQQ").attr("style", "color:black;"); 
			    }else{
			    	document.getElementById('background').style.display='block';
			$.ajax({
				url:"honggu/phonetoqq",
				type:"get",
				dataType:"json",
				data:{"phone":phone},
				success:function(result){
					if (result.code == 1) {
						if (result.data == "") {
							$("#results .ss").append("未找到相关数据");
						}else {
							var adata = "<p>查询结果："+result.failure+"</p>"
							$(".styleTbody").append(adata);
						}
					} else if(result.code == 601){
						$(".styleTbody").append(result.failure);
					} else {
						$("#results .ss").append(result.failure);
					}
					$("#submitQQ").attr("disabled", false); 
					$("#submitQQ").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					document.getElementById('background').style.display='none';
				}
			});
			}
		}
		
	});



//enter 控件
function EnterPressQuery(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitQQ").focus(); 
	} 
	}; 
	
