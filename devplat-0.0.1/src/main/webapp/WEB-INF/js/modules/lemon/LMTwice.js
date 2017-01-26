$(document).ready(function () {
	if($.browser.msie) {  
		$('.obj').boxShadow(-10,-10,5,"#0cc"); //obj元素使用了box-shadow  
	}  

	//38、信贷综合信息查询
	$("#SubmitloanPhone").click(function(){
		document.getElementById('background').style.display='block';
		//获取电话号码
		var phone = $("#loanPhone").val();
		$.ajax({
			url:"loan/info",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				console.log(result);
				var html;
				if(result.code == 1){
					$.each(result.data,function(key,value){
						if(key == "RESULTS"){
							$.each(value,function(index,values){
//								console.log(values);
								$.each(values,function(key1,value1){
//									console.log(value1);
									if(key1 == "DATA"){
										$.each(value1,function(key2,value2){
											console.log(value2)
										})
									}
								})
							});
						}
//						console.log(result.data[key]);
					});
				}else{
					html = "未找到相关信息";
				}
				$("#resultloanPhone").append(html);
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("ajax发送请求失败！");
				document.getElementById('background').style.display='none';
			}
		})
	});
	//点击手机号查询事件
	$("#selphone").click(function(){
		document.getElementById('nav_bank').style.display='none';
		document.getElementById('nav_phone').style.display='block';		
	});
	//点击银行卡查询事件
	$("#selbank").click(function(){
		document.getElementById('nav_phone').style.display='none';
		document.getElementById('nav_bank').style.display='block';
	});
	$("#submitquery").click(function(){
		document.getElementById('background').style.display='block';
		var phone = $("#selPhone").val();
		$.ajax({
			url:"query/phoneBank",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				$("#nav_result").show();
				$("#bank_result").hide();
				$("#nav_result").empty();
				console.log(result);
				if(result.code == 1){
					var html="";
					var html_11="";
					var html_12="";
					var html_13="";
					var html_14="";
					//11
					if(result.data["changeLine"] == "未查到相关信息"){
						console.log("未查到相关信息");
					}else{
						var a = "<div class='obj' style='wigth:100%'><strong>银行卡出入账</strong><table class='table-striped table-bordered'>";
						var key1="<tr>";
						var key2="";
						var key3="";
						var key4="";
						var key5="";
						var value1="<tr>";
						var value2="";
						var value3="";
						var value4="";
						var value5="";
						$.each(result.data["changeLine"],function(key,value){
							if(key=="debitCardRemainingSum"){
								$.each(result.data["changeLine"]["debitCardRemainingSum"],function(keys,values){
									if(keys == "minIncluded"){
										
										key1+="<td style='background: #EEE8AA;'>借记卡余额(是否包含最小值)</td>";
									}
									if(keys == "maxIncluded"){
										key1+="<td style='background: #EEE8AA;'>借记卡余额(是否包含最大值)</td>";
									}
									if(keys == "min"){
										key1+="<td style='background: #EEE8AA;'>借记卡余额(最小值)</td>";
									}
									if(keys == "max"){
										key1+="<td style='background: #EEE8AA;'>借记卡余额(最大值)</td>";
									}
									if(keys == "unit"){
										key1+="<td style='background: #EEE8AA;'>借记卡余额(单位)</td>";
									}
								});
								$.each(result.data["changeLine"]["debitCardRemainingSum"],function(keyss,valuess){
									if(keyss == "minIncluded"){
										value1+="<td>"+valuess+"</td>";
									}
									if(keyss == "maxIncluded"){
										value1+="<td>"+valuess+"</td>";
									}
									if(keyss == "min"){
										value1+="<td>"+valuess+"</td>";
									}
									if(keyss == "max"){
										value1+="<td>"+valuess+"</td>";
									}
									if(keyss == "unit"){
										value1+="<td>"+valuess+"</td>";
									}
								});
							}
							if(key=="debitCardPayment3m"){
								$.each(result.data["changeLine"]["debitCardPayment3m"],function(key,value){
									if(key == "minIncluded"){
										key2+="<td style='background: #EEE8AA;'>3个月出账(是否包含最小值)</td>";
									}
									if(key == "maxIncluded"){
										key2+="<td style='background: #EEE8AA;'>3个月出账(是否包含最大值)</td>";
									}
									if(key == "min"){
										key2+="<td style='background: #EEE8AA;'>3个月出账(最小值)</td>";
									}
									if(key == "max"){
										key2+="<td style='background: #EEE8AA;'>3个月出账(最大值)</td>";
									}
									if(key == "unit"){
										key2+="<td style='background: #EEE8AA;'>3个月出账(单位)</td>";
									}
								});
								$.each(result.data["changeLine"]["debitCardPayment3m"],function(key,value){
									if(key == "minIncluded"){
										value2+="<td>"+value+"</td>";
									}
									if(key == "maxIncluded"){
										value2+="<td>"+value+"</td>";
									}
									if(key == "min"){
										value2+="<td>"+value+"</td>";
									}
									if(key == "max"){
										value2+="<td>"+value+"</td>";
									}
									if(key == "unit"){
										value2+="<td>"+value+"</td>";
									}
								});
							}
							if(key=="debitCardDeposit3m"){
								$.each(result.data["changeLine"]["debitCardDeposit3m"],function(key,value){
									if(key == "minIncluded"){
										key3+="<td style='background: #EEE8AA;'>3个月入账(是否包含最小值)</td>";
									}
									if(key == "maxIncluded"){
										key3+="<td style='background: #EEE8AA;'>3个月入账(是否包含最大值)</td>";
									}
									if(key == "min"){
										key3+="<td style='background: #EEE8AA;'>3个月入账(最小值)</td>";
									}
									if(key == "max"){
										key3+="<td style='background: #EEE8AA;'>3个月入账(最大值)</td>";
									}
									if(key == "unit"){
										key3+="<td style='background: #EEE8AA;'>3个月入账(单位)</td>";
									}
								});
								$.each(result.data["changeLine"]["debitCardDeposit3m"],function(key,value){
									if(key == "minIncluded"){
										value3+="<td>"+value+"</td>";
									}
									if(key == "maxIncluded"){
										value3+="<td>"+value+"</td>";
									}
									if(key == "min"){
										value3+="<td>"+value+"</td>";
									}
									if(key == "max"){
										value3+="<td>"+value+"</td>";
									}
									if(key == "unit"){
										value3+="<td>"+value+"</td>";
									}
								});
							}
							if(key=="debitCardPayment12m"){
								$.each(result.data["changeLine"]["debitCardPayment12m"],function(key,value){
									if(key == "minIncluded"){
										key4+="<td style='background: #EEE8AA;'>12个月出账(是否包含最小值)</td>";
									}
									if(key == "maxIncluded"){
										key4+="<td style='background: #EEE8AA;'>12个月出账(是否包含最大值)</td>";
									}
									if(key == "min"){
										key4+="<td style='background: #EEE8AA;'>12个月出账(最小值)</td>";
									}
									if(key == "max"){
										key4+="<td style='background: #EEE8AA;'>12个月出账(最大值)</td>";
									}
									if(key == "unit"){
										key4+="<td style='background: #EEE8AA;'>12个月出账(单位)</td>";
									}
								});
								$.each(result.data["changeLine"]["debitCardPayment12m"],function(key,value){
									if(key == "minIncluded"){
										value4+="<td>"+value+"</td>";
									}
									if(key == "maxIncluded"){
										value4+="<td>"+value+"</td>";
									}
									if(key == "min"){
										value4+="<td>"+value+"</td>";
									}
									if(key == "max"){
										value4+="<td>"+value+"</td>";
									}
									if(key == "unit"){
										value4+="<td>"+value+"</td>";
									}
								});
							}
							if(key=="debitCardDeposit12m"){
								$.each(result.data["changeLine"]["debitCardDeposit12m"],function(key,value){
									if(key == "minIncluded"){
										key5+="<td style='background: #EEE8AA;'>12个月入账(是否包含最小值)</td>";
									}
									if(key == "maxIncluded"){
										key5+="<td style='background: #EEE8AA;'>12个月入账(是否包含最大值)</td>";
									}
									if(key == "min"){
										key5+="<td style='background: #EEE8AA;'>12个月入账(最小值)</td>";
									}
									if(key == "max"){
										key5+="<td style='background: #EEE8AA;'>12个月入账(最大值)</td>";
									}
									if(key == "unit"){
										key5+="<td style='background: #EEE8AA;'>12个月入账(单位)</td>";
									}
								});
								$.each(result.data["changeLine"]["debitCardDeposit12m"],function(key,value){
									if(key == "minIncluded"){
										value5+="<td>"+value+"</td>";
									}
									if(key == "maxIncluded"){
										value5+="<td>"+value+"</td>";
									}
									if(key == "min"){
										value5+="<td>"+value+"</td>";
									}
									if(key == "max"){
										value5+="<td>"+value+"</td>";
									}
									if(key == "unit"){
										value5+="<td>"+value+"</td>";
									}
								});
							}
						});
						a+=key1+key2+key3+key4+key5+"</tr>"+value1+value2+value3+value4+value5+"</tr></table></div>";
						html_11+=a;
					}
					//12
					if(result.data["loaddata"] == "未查到相关信息"){
						console.log("未查到相关信息");
					}else{
						var a = "<div class='obj' style='wigth:100%'><strong>银行卡还款情况</strong><table class='table-striped table-bordered'>";
						var key1="<tr>";
						var value1="<tr>";
						var key2="";
						var value2="";
						$.each(result.data["loaddata"],function(key,value){
							if(key == "currentOutstandingLoanCount"){
								key1+="<td style='background: #EEE8AA;'>未结清笔数</td>";
								value1+="<td>"+value+"</td>";
							}
							if(key == "currentOutstandingLoanAmount"){
								$.each(result.data["loaddata"][key],function(keys,values){
									console.log(keys);
									if(keys =="minIncluded"){
										key2+="<td style='background: #EEE8AA;'>是否包含最小值</td>";
										value2+="<td>"+values+"</td>";
									}
									if(keys =="maxIncluded"){
										key2+="<td style='background: #EEE8AA;'>是否包含最大值</td>";
										value2+="<td>"+values+"</td>";
									}
									if(keys =="min"){
										key2+="<td style='background: #EEE8AA;'>最小值</td>";
										value2+="<td>"+values+"</td>";	
									}
									if(keys =="max"){
										key2+="<td style='background: #EEE8AA;'>最大值</td>";
										value2+="<td>"+values+"</td>";	
									}
									if(keys =="unit"){
										key2+="<td style='background: #EEE8AA;'>单位</td>";
										value2+="<td>"+values+"</td>";	
									}
								});
							}
						});
						key2+="</tr>";
						a+=key1+key2+value1+value2+"</tr></table></div>";
						html_12+=a;
					}
					//13
					if(result.data["activedata"] == "未查到相关信息"){
						console.log("未查到相关信息");
					}else{
						var a = "<div class='obj' style='wigth:100%'><strong>银行卡账动信息</strong><table class='table-striped table-bordered'>";
						var key1="<tr>";
						var key2="";
						var key3="";
						var value1="<tr>";
						var value2="";
						var value3="";
						$.each(result.data["activedata"],function(key,value){
							if(key=="paymentShortTermVolatilityIndex"){
								key1+="<td style='background: #EEE8AA;'>出账短期波动指数</td>";
								value1+="<td>"+value+"</td>";
								
							}
							if(key=="depositShortTermVolatilityIndex"){
								key2+="<td style='background: #EEE8AA;'>出账短期波动指数</td>";
								value2+="<td>"+value+"</td>";
							}
							if(key=="depositLongTermVolatilityIndex"){
								key3+="<td style='background: #EEE8AA;'>出账短期波动指数</td>";
								value3+="<td>"+value+"</td>";
							}
						});
						a+=key1+key2+key3+"</tr>"+value1+value2+value3+"<tr></table></div>";
						html_13+=a;
					}
					//拼接14
					if(result.data["binddata"] == "未查到相关信息"){
						console.log("未查到相关信息");
					}else{
						var a = "<div class='obj' style='wigth:100%'><strong>银行卡信息</strong><table class='table-striped table-bordered'>";
						var keys1="<tr>";
						var keys2="";
						var keys3="";
						var keys4="";
						var values1="<tr>";
						var values2="";
						var values3="";
						var values4="";
						$.each(result.data["binddata"],function(key,value){
							if(key=="debitCardCount"){
								keys1 += "<td style='background: #EEE8AA;'>借记卡数量</td>";
								values1+="<td>"+value+"</td>";
							}
							if(key=="creditCardCount"){
								keys2+="<td style='background: #EEE8AA;'>信用卡数量</td>";
								values2+="<td>"+value+"</td>";
							}
							if(key == "creditCardAge"){
								$.each(result.data["binddata"][key],function(key1,value1){
									if(key1 == "minIncluded"){
										keys3 +="<td style='background: #EEE8AA;'>卡龄(是否包含最小值)</td>";
									}
									if(key1 == "maxIncluded"){
										keys3 +="<td style='background: #EEE8AA;'>卡龄(是否包含最大值)</td>";
									}
									if(key1 == "min"){
										keys3 +="<td style='background: #EEE8AA;'>卡龄(最小值)</td>";
									}
									if(key1 == "max"){
										keys3 +="<td style='background: #EEE8AA;'>卡龄(最大值)</td>";
									}
									if(key1 == "unit"){
										keys3 +="<td style='background: #EEE8AA;'>卡龄(单位)</td>";
									}
								});
								$.each(result.data["binddata"][key],function(key1,value1){
									if(key1 == "minIncluded"){
										values3 +="<td>"+value1+"</td>";
									}
									if(key1 == "maxIncluded"){
										values3 +="<td>"+value1+"</td>";
									}
									if(key1 == "min"){
										values3 +="<td>"+value1+"</td>";
									}
									if(key1 == "max"){
										values3 +="<td>"+value1+"</td>";
									}
									if(key1 == "unit"){
										values3 +="<td>"+value1+"</td>";
									}
								});
							}
							if(key == "creditCardAging"){
								$.each(result.data["binddata"][key],function(key1,value1){
									if(key1 == "minIncluded"){
										keys4 +="<td style='background: #EEE8AA;'>账龄(是否包含最小值)</td>";
									}
									if(key1 == "maxIncluded"){
										keys4 +="<td style='background: #EEE8AA;'>账龄(是否包含最大值)</td>";
									}
									if(key1 == "min"){
										keys4 +="<td style='background: #EEE8AA;'>账龄(最小值)</td>";
									}
									if(key1 == "max"){
										keys4 +="<td style='background: #EEE8AA;'>账龄(最大值)</td>";
									}
									if(key1 == "unit"){
										keys4 +="<td style='background: #EEE8AA;'>账龄(单位)</td>";
									}
								});
								$.each(result.data["binddata"][key],function(key1,value1){
									if(key1 == "minIncluded"){
										values4 +="<td>"+value1+"</td>";
									}
									if(key1 == "maxIncluded"){
										values4 +="<td>"+value1+"</td>";
									}
									if(key1 == "min"){
										values4 +="<td>"+value1+"</td>";
									}
									if(key1 == "max"){
										values4 +="<td>"+value1+"</td>";
									}
									if(key1 == "unit"){
										values4 +="<td>"+value1+"</td>";
									}
									
								});
							}
						})
						a+=keys1+keys2+keys3+keys4+"</tr>"+values1+values2+values3+values4+"</tr></table></div>"
						html_14+=a;
					}
					html+=html_14+html_11+html_13+html_12;
					$("#nav_result").html(html);
				}else {
					$("#nav_result").html("未找到相关数据！");
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