$(document).ready(function () {
	//金融用户画像
	$("#SubmitMobleOne").click(function(){
		var phone = $("#wlphone").val();
		$.ajax({
			url:"wlQuery",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				$("#resultWlPhone").empty();
				var html="";
				if(result.code == 1){
					if(result.data.resultCode == 1200){
						var title1="";
						var title2="";
						var title3="";
						var title4="";
						//第一个table
						var tab1 = "<table class='table-striped table-bordered' style='text-align:center'>";
						var key1="<tr>";
						var value1="<tr>";
						//第二个table
						var tab2 = "<table class='table-striped table-bordered' style='text-align:center'>";
						var key2="<tr>";
						var value2="<tr>";
						//第三个table
						var tab3 = "<table class='table-striped table-bordered' style='text-align:center'>";
						var key3="<tr>";
						var value3="<tr>";
						//第四个table
						var tab4 = "<table class='table-striped table-bordered' style='text-align:center'>";
						var key4="<tr>";
						var value4="<tr>";
						$.each(result.data.result,function(key,value){
							$.each(value,function(keys,values){
								if(keys == "basic_age"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>年龄</td>";value1+="<td>"+values+"</td>";}
								if(keys == "basic_gender"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>性别</td>";value1+="<td>"+values+"</td>";}
								if(keys == "basic_city"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>城市</td>";value1+="<td>"+values+"</td>";}
								if(keys == "consume_act"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>网购活跃度</td>";value1+="<td>"+values+"</td>";}
								if(keys == "consume_auth"){
									title1="<p style='color:red'>基本信息</p>";
									key1+="<td style='background: #EEE8AA;'>网购实名认证</td>";
									if(values == 1){
										value1+="<td>认证</td>";
									}
									if(values == 0){
										value1+="<td>未认证</td>";
									}
								}
								if(keys == "consume_sumlevel"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>消费等级</td>";value1+="<td>"+values+"</td>";}
								if(keys == "consume_year"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>网购年龄</td>";value1+="<td>"+values+"</td>";}
								if(keys == "favor_feedrate"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>主动评论百分比</td>";value1+="<td>"+values+"</td>";}
								if(keys == "favor_high_brand_per"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>高端品牌百分比</td>";value1+="<td>"+values+"</td>";}
								if(keys == "m_abnormal_cnt"){title1="<p style='color:red'>基本信息</p>";key1+="<td style='background: #EEE8AA;'>累计贷款类异常购买次数</td>";value1+="<td>"+values+"</td>";}
								
								
								if(keys == "m_std_month_cnt"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>各月购物次数标准差</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_b_price_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>商城类总金额比重</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_b_cnt_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>商城类总次数比重</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_brand_price_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>品牌类总金额比重</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_brand_cnt_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>品牌类总次数比重</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_bfifty_price_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>50元以下消费订单金额比重</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_bfifty_cnt_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>50元以下消费订单次数比重</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_std_month_price"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>购物金额的波动</td>";value2+="<td>"+values+"</td>";}
								if(keys == "m_general_ratio"){title2="<p style='color:red'>分析网购比例</p>";key2+="<td style='background: #EEE8AA;'>平均单次购买金额</td>";value2+="<td>"+values+"</td>";}
								if(keys == "favor_brand"){
									$.each(values,function(keys1,values1){
										title3="<p style='color:red'>品牌-品类偏好百分比top5</p>";
										key3 +="<td style='background: #EEE8AA;'>"+keys1+"</td>";
										value3+="<td>"+values1+"</td>";
									});
								}
								if(keys == "favor_cat"){
									$.each(values,function(keys1,values1){
										title3="<p style='color:red'>品牌-品类偏好百分比top5</p>";
										key3 +="<td style='background: #EEE8AA;'>"+keys1+"</td>";
										value3+="<td>"+values1+"</td>";
									});
								}
								if(keys == "tag"){
									$.each(values,function(keys1,values1){
										title4="<p style='color:red'>其他</p>";
										key4 +="<td style='background: #EEE8AA;'>"+keys1+"</td>";
										if(values1 == 1){
											value4+="<td>是</td>";
										}else{
											value4+="<td>不是</td>";
										}
									});
								}
							});
						});
						key1+="</tr>";
						value1+="</tr>";
						tab1+=key1+value1+"</table></br>";
						
						key2+="</tr>";
						value2+="</tr>";
						tab2+=key2+value2+"</table></br>";
						
						key3+="</tr>";
						value3+="</tr>";
						tab3+=key3+value3+"</table></br>";
						
						key4+="</tr>";
						value4+="</tr>";
						tab4+=key4+value4+"</table></br>";
						
						html+=title1+tab1+title2+tab2+title3+tab3+title4+tab4;
						
					}else{
						html = "没有";
					}
				}else{
					html = "未找到相关信息";
				}
				$("#resultWlPhone").append(html);
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("错误！");
			}
		});
	});
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
				var html;
				if(result.code == 1){
					$.each(result.data,function(key,value){
						if(key == "RESULTS"){
							$.each(value,function(index,values){
								$.each(values,function(key1,value1){
									if(key1 == "DATA"){
										$.each(value1,function(key2,value2){
											console.log(value2)
										})
									}
								})
							});
						}
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
		var mobile = $("#selPhone").val();
		$.ajax({
			url:"/devplat/bankcards/mobile/" + mobile,
			type:"get",
			dataType:"json",
			success:function(result){
				$("#nav_result").show();
				$("#bank_result").hide();
				$("#nav_result").empty();
				if(result.code == 1){
					var html="";
					var table="<div class='obj' style='color:red;wigth:100%'><strong>信用卡</strong><table class='table-striped table-bordered'>";
					var keytb="<tr>";
					var valuetb="<tr>";
					if(result.data["信用卡"]){
						$.each(result.data["信用卡"],function(key,value){
							keytb+="<td>"+key+"</td>";
							valuetb+="<td>"+value+"</td>";
						});
						keytb+="</tr>";
						valuetb+="</tr>";
						table+=keytb+valuetb+"</table></div>";
					}
					var table1="<div class='obj' style='color:red;wigth:100%'><strong>借记卡</strong><table class='table-striped table-bordered'>";
					var keytb1="<tr>";
					var valuetb1="<tr>";
					if(result.data["借记卡"]){
						$.each(result.data["借记卡"],function(key,value){
							keytb1+="<td>"+key+"</td>";
							valuetb1+="<td>"+value+"</td>";
						});
						keytb1+="</tr>";
						valuetb1+="</tr>";
						table1+=keytb1+valuetb1+"</table></div>";
					}
					html += table+table1;
					$("#nav_result").html(html);
				}else{
					$("#nav_result").html("未找到相关数据");
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