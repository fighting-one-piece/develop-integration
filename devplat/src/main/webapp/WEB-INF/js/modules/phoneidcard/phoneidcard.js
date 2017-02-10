var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var labless=new Array();
var mobile;
function checkContactNumber(){
	mobile = $("#query").val().trim();
	 var isIDCard1=/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;
	 var isPhone = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
	 var isMob=/^((\+?86)|(\(\+86\)))?(13[012356789][0-9]{8}|15[012356789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
	 var error = "<label>请检查你的输入</label>";
	 if (mobile.substring(0, 1) == 1) {  
	        if (!isMob.exec(mobile) && mobile.length != 11) {
	        	$("#error").css("display", "block"); 
	            $("#error").html(error);  
	            return false;  
	        }  
	    }else if (mobile.substring(0, 1) == 0) {  
	        if (!isPhone.test(mobile)) {  
	        	$("#error").css("display", "block"); 
	        	$("#error").html(error);    
	            return false;  
	        }  
	    }else if(mobile.length!=18 || mobile.length!=15){
	    	if(!isIDCard1.test(mobile)){
	    		$("#error").css("display", "block"); 
	    		$("#error").html(error);
	    		return false;	    			    		
	    	}
	    	
	    }
	   //否则全部不通过  
	    else{  
	    	$("#error").css("display", "block"); 
	    	$("#error").html(error);
	        return false;  
	    } 
	    $("#error").css("display", "none");
	    return true;   	 
 }

$(function(){
	$("#label a").live("click",function(){
		var solitArr = new Array();
		var types = $(this).text().trim();
		var indexs;
		var Englientype;
		var phones=mobile;
		if(types!=null){
			 for (var i = 0; i < labless.length; i++) {  
				var regex = /:/;
				solitArr=labless[i].split(regex);
		        if (solitArr.indexOf(types)!= -1) { 		        	
		        	var spli = new Array();
		        	spli=labless[i].split(regex);
					console.log(spli)
					for(i=0; i <spli.length; i++){
						if(escape(spli[i]).indexOf( "%u" )<0){
							console.log(spli[i]);
								indexs=spli[0];
								Englientype=spli[1];
						}else{
							
						}
					}
					var ur=projectName+"/identity/"+phones+"/index/"+indexs+"/type/"+Englientype
					document.getElementById('background').style.display='block';
					$.ajax({
					    type:"get",
						url:ur,
						dataType:"json",
						success:function(result){
							$("#bottomResults").empty();
							$("#submitt").click(function(){
								$("#tbottomResults").empty();
								
							});
							if (result.code == 2) {
								$("#bottomResults").append(result.failure);
							}
							if(result.code == 1) {
								if (result.data && result.data.length > 0) {
								$.each(result.data,function(n,list){
									var keySt = "<table  class='table table-striped table-bordered'><tr>";
									var valueSt = "<tr>";
									$.each(list,function(resultKes,resultValus){
										keySt = keySt + "<td>"+ resultKes+"</td>";
										valueSt = valueSt + "<td>"+ resultValus+"</td>";
									});
									keySt= keySt + "</tr>";
									valueSt= valueSt + "</tr></table>"
									$("#bottomResults").append(keySt+valueSt);
								});	
								
								$("#submitss").attr("disabled", true); 
								$("#submitss").attr("style", "color:black;"); 
								} else {
									$("#bottomResults").append("未找到相关数据")
								}
								
							  } else {
								$("#bottomResults").append("未找到相关数据")
							}							
							document.getElementById('background').style.display='none';
						},
						error:function(){
							console.log("ajax发送请求失败！");
							document.getElementById('background').style.display='none';
						}
					});
		            return true; 
		        } 
			 }
		}
		
	});
	$("#submitt").click(function(){
		if(checkContactNumber()==true){
			$("#submitt").attr("disabled", true); 
			$("#label").empty();
			mobile = $("#query").val(); 
			var url=projectName+"/identity/"+mobile+"/labels"
			console.log(url);
			document.getElementById('background').style.display='block';
			$.ajax({
				type:"get",
				url:url,
				dataType:"json",
				success:function(result){
					console.log(result);
					$("#topResults").empty();
					if (result.code == 2) {
						$("#topResults").append(result.failure);
					}
					var term;
					if (result.data!=null) {
						labless=result.data.labels;
						var table = "<table class='table table-striped table-bordered'>";
						var keyStr="<tr>";
						var valueStr= "<tr>"+"<td>"+"姓名:"+"</td><td>";
						var valuestt= "<tr>"+"<td>"+"手机:"+"</td><td>";
						var valueSts= "<tr>"+"<td>"+"身份证:"+"</td><td>";
						var typeid = "";
						var cc = "类型标签：";
						if(result.code == 1) {					
							$.each(result.data.names,function(i,item){
								if(item!=null){
									valueStr+=item+" ";						
								}
							});
							$.each(result.data.mobiles,function(i,item){
								if(item !=null){						
									valuestt+=item+" ";
								}
							});
							$.each(result.data.idcards,function(i,item){
								if(item !=null){
									valueSts+=item+" "+" ";
								}
							});
							valueStr +="</td></tr>"
								valuestt+="</td</tr>"
									valueSts+="</td></tr>"
										$("#topResults").append(valueStr+valuestt+valueSts);
							$("#topResults").html(table+valueStr+valuestt+valueSts+"</table>");
							
							$.each(result.data.labels,function(i,item){
								
								var splitArray = new Array();
								var regex =/:/;
								splitArray=item.split(regex);
								console.log(splitArray)
								console.log(1);
								for(i=0; i < splitArray.length; i++){
									if(escape(splitArray[i]).indexOf( "%u" )<0){
										
									}else{
										console.log(splitArray[i])
										cc +="<a>"+splitArray[i]+" "+"</a>";
									}
									$("#label").html(cc);
								}
							});		
						}					
					}else {
						$("#topResults").append("未找到相关数据")
					}
					$("#submitt").attr("disabled", false); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					console.log("ajax发送请求失败！");
					document.getElementById('background').style.display='none';
					$("#submitt").attr("disabled", false);
					$("#submits").attr("style", "color:black;"); 
				}
			});
		}else{
			alert("请检查你的输入！");
			checkContactNumber()==false;
		}
	
  });
});

//enter 控件
function EnterPressPhoneIdCard(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitt").click(); 
	} 
	}; 