$(document).ready(function () {
	
	//37
	$("#SubmitIdNameCheck").click(function(){
		var name = $("#name").val();
		var idCard=$("#idCard").val();
		$("#resultIdNameCheck").empty();
		console.log(name+"--"+idCard+"--")
		console.log(1)
		$.ajax({
			url:"idName/Check",
			type:"post",
			dataType:"json",
			data:{"name":name,"idCard":idCard},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>状态码</td><td style='background: #EEE8AA;text-align: center;'>描述信息</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.checkStatus+"</td><td style='text-align: center;'>"+result.data.message+"</td></tr></table>";
				}else {
					html = "未找到相关信息";
				}
				$("#resultIdNameCheck").append(html);
			},
			error:function(){
			}
		});
	});	
	
	//35数信网黑名单
	$("#blackListSubmit").click(function(){
		var idCard = $("#idCard").val();
		var name = $("#name").val();
		var phone = $("#phone").val();
		$("#resultBlackList").empty();
		$.ajax({
			url:"lemonblacklist/blacklistdata",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"name":name,"phone":phone},
			success:function(result){
				console.log(result);
				var tab;
				var ss = result.data.content;
				if (result.code == 1) {
					tab = 	"<table style='width:100%;margin: 5px;'><tr>";
					tab += "<td style='background: #EEE8AA;'>date</td>";
					tab += "<td style='background: #EEE8AA;'>desc</td>";
					tab += "<td style='background: #EEE8AA;'>age</td>";
					tab += "<td style='background: #EEE8AA;'>xingzuo</td>";
					tab += "<td style='background: #EEE8AA;'>app_id_card</td>";
					tab += "<td style='background: #EEE8AA;'>type</td>";
					tab += "<td style='background: #EEE8AA;'>app_name</td>";
					tab += "<td style='background: #EEE8AA;'>app_mobile</td>";
					tab += "<td style='background: #EEE8AA;'>app_qq</td>";
					tab += "<td style='background: #EEE8AA;'>hit_num</td>";
					tab += "<td style='background: #EEE8AA;'>home</td>";
					tab += "<td style='background: #EEE8AA;'>message</td>";
					tab += "<td style='background: #EEE8AA;'>sex</td>";
					tab += "<td style='background: #EEE8AA;'>type</td>";
					tab += "<td style='background: #EEE8AA;'>comp_name</td>";
					tab += "<td style='background: #EEE8AA;'>court</td>";
					tab += "<td style='background: #EEE8AA;'>file_time</td>";
					tab += "<td style='background: #EEE8AA;'>overdue_amount</td>";
					tab += "<td style='background: #EEE8AA;'>province</td>";
					tab += "<td style='background: #EEE8AA;'>publish_date</td>";
					tab += "<td style='background: #EEE8AA;'>source_url</td>";
					
					
					tab +=	"</tr><tr>";
					tab += "<td>"+ss[0].date+"</td>";
					tab += "<td>"+ss[0].desc+"</td>";
					tab += "<td>"+ss[0].originalRet.age+"</td>";
					tab += "<td>"+ss[0].originalRet.xingzuo+"</td>";
					tab += "<td>"+ss[0].originalRet.app_id_card+"</td>";
					tab += "<td>"+ss[0].originalRet.type+"</td>";
					tab += "<td>"+ss[0].originalRet.app_name+"</td>";
					tab += "<td>"+ss[0].originalRet.app_mobile+"</td>";
					tab += "<td>"+ss[0].originalRet.app_qq+"</td>";
					tab += "<td>"+ss[0].originalRet.hit_num+"</td>";
					tab += "<td>"+ss[0].originalRet.home+"</td>";
					tab += "<td>"+ss[0].originalRet.message+"</td>";
					tab += "<td>"+ss[0].originalRet.sex+"</td>";
					tab += "<td>"+ss[0].type+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].comp_name+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].court+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].file_time+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].overdue_amount+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].province+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].publish_date+"</td>";
					tab += "<td>"+(ss[0].originalRet.hit_list)[0].source_url+"</td>";
					
					
					tab +=	"</tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#resultBlackList").append(tab);
			}
		});
	});
	
	//34多次申请记录查询C
	$("#multiSubmit").click(function(){
		var phone = $("#phone").val();
		$("#multiplatfrom").empty();
		$.ajax({
			url:"multiplatfrom/multi",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				var tab;
				var ss = result.data.content;
				if (result.code == 1) {
					tab = 	"<table style='width:100%;margin: 5px;'>";
					for (var i = 0; i < ss.length; i++) {
						tab += "<tr>";
						$.each(ss[0],function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
						});
						tab += "</tr><tr>";
						$.each(ss[0],function(key,value){
							tab += "<td>"+value+"</td>";
						});
						tab += "</tr>";
						}
					tab +=	"</table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#multiplatfrom").append(tab);
			}
		});
	});	
	
	//33网络公开逾期信息
	$("#loanOverdueSubmit").click(function(){
		var idCard = $("#idCard").val();
		var phone = $("#phone").val();
		$("#loanOverdue").empty();
		$.ajax({
			url:"loanOverdue/blackList",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"phone":phone},
			success:function(result){
				console.log(result);
				var tab;
				var ss = result.data.content;
				if (result.code == 1) {
					tab = 	"<table style='width:100%;margin: 5px;'><tr>";
					tab += "<td style='background: #EEE8AA;'>date</td>";
					tab += "<td style='background: #EEE8AA;'>desc</td>";
					tab += "<td style='background: #EEE8AA;'>type</td>";
					$.each(ss[0].originalRet,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					});
					tab +=	"</tr><tr>";
					tab += "<td>"+ss[0].date+"</td>";
					tab += "<td>"+ss[0].desc+"</td>";
					tab += "<td>"+ss[0].type+"</td>";
					$.each(ss[0].originalRet,function(key,value){
						tab += "<td>"+value+"</td>";
					});
					tab +=	"</tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#loanOverdue").append(tab);
			}
		});
	});	
	
	
	
	//32合作机构共享黑名单
	$("#blackListCheatSubmit").click(function(){
		var idCard = $("#idCard").val();
		var name = $("#name").val();
		var phone = $("#phone").val();
		$("#blackListCheat").empty();
		$.ajax({
			url:"blackListCheat/cheat",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"phone":phone,"name":name},
			success:function(result){
				console.log(result);
				var tab;
				var ss = result.data.content;
				if (result.code == 1) {
					tab = 	"<table style='width:100%;margin: 5px;'><tr>";
					tab += "<td style='background: #EEE8AA;'>date</td>";
					tab += "<td style='background: #EEE8AA;'>desc</td>";
					tab += "<td style='background: #EEE8AA;'>type</td>";
					$.each(ss[0].originalRet,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					});
					tab +=	"</tr><tr>";
					tab += "<td>"+ss[0].date+"</td>";
					tab += "<td>"+ss[0].desc+"</td>";
					tab += "<td>"+ss[0].type+"</td>";
					$.each(ss[0].originalRet,function(key,value){
						tab += "<td>"+value+"</td>";
					});
					tab +=	"</tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#blackListCheat").append(tab);
			}
		});
	});	
	
	//31丶网络公开负面信息
	$("#Negative").click(function(){
		var phone=$("#phone").val();
		console.log(phone)
		$("#resultActive").empty();
			$.ajax({
				url:"internet/negative",
				type:"post",
				dataType:"json",
				data:{"phone":phone},
				success:function(result){
					console.log(result.data);
					console.log(result.code);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultActive").append(tab);
				},
				error:function(){
				}
		});
	})
	
	//30赌博吸毒名单
		$("#GambingDrug").click(function(){
		var phone=$("#phone").val();
		$("#resultGamble").empty();
		console.log(phone)
		$.ajax({
			url:"gambing/dru",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				var keytr = "<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				var valuetr = "<tr>";
				if (result.code == 1) {
					$.each(result.data,function(key,value){
						$.each(value,function(keys,values){
							$.each(values,function(index,desc){
							keytr += "<td style='background: #EEE8AA;'>"+index+"</td>";
							valuetr += "<td style='background: white;'>"+desc+"</td>";
						})
						})
					})
					keytr += "</tr>";
					valuetr += "</tr></table></br>";
					
				}else {
					tab = "未找到相关信息";
				}
				$("#resultGamble").append(keytr+valuetr);
			},
			error:function(){
			}
		});
	});	
	
	//29丶多次申请记录查询B
	$("#inquire").click(function(){
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
//		console.log(idCard+"---"+name+"---"+phone)
		$("#resultInquire").empty();
			$.ajax({
				url:"select/recording",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone},
				success:function(result){
					console.log(result.data);
					console.log(result.code);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultInquire").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
	//28丶多次申请记录查询A
	$("#inquireA").click(function(){
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
//		console.log(idCard+"---"+name+"---"+phone)
		$("#resultInquireA").empty();
			$.ajax({
				url:"select/recordingA",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone},
				success:function(result){
					console.log(result.data);
					console.log(result.code);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultInquireA").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
	//27丶银行、 P2P 逾期记录
	$("#p2p").click(function(){
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
		$("#resultP2P").empty();
			$.ajax({
				url:"p2p/overdue",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone},
				success:function(result){
					console.log(result.data);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultP2P").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
	//26法院被执行人记录
	$("#court").click(function(){
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
		var idType=$("#idType").val();
		var gender=$("#gender").val();
		console.log(gender)
		$("#resultCourt").empty();
			$.ajax({
				url:"court/execute",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone,"idType":idType,"gender":gender},
				success:function(result){
					console.log(result.data);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultCourt").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
	//25 手机号标签查询
	$("#phoneQuery").click(function(){
		var phone=$("#phone").val();
		$("#resultTagQuery").empty();
			$.ajax({
				url:"phone/query",
				type:"post",
				dataType:"json",
				data:{"phone":phone},
				success:function(result){
					console.log(result.data);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultTagQuery").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
	//24地址验证
	$("#address").click(function(){
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
		var idType=$("#idType").val();
		var homeCity =$("#homeCity").val();
		var homeAddress =$("#homeAddress").val();
		var companyCity =$("#companyCity").val();
		var companyAddress =$("#companyAddress").val();
		$("#resultAddress").empty();
			$.ajax({
				url:"address/verification",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone,"idType":idType,"homeCity":homeCity,"homeAddress":homeAddress,"companyCity":companyCity,"companyAddress":companyAddress},
				success:function(result){
					console.log(result.data);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+result.code+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultAddress").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
	//23百度消费金融评分查询
	$("#baiduQuery").click(function(){
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
		var maritalStatus =$("#marriage").val();
		var degree =$("#education").val();
		var homeAddress =$("#homeAddress").val();
		var companyAddress =$("#companyAddress").val();
		$("#resultBaiduQuery").empty();
			$.ajax({
				url:"baidu/integralQuery",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone,"idCard":idCard,"maritalStatus":maritalStatus,"degree":degree,"homeAddress":homeAddress,"companyAddress":companyAddress},
				success:function(result){
					console.log(result.data);
					var tab;
					tab =	"<p></p>";
					tab += 	"<table style='width:100%;margin: 5px;margin-top:125px;' class='table table-striped table-bordered'><tr>";
					if (result.code == 1) {
						$.each(result.data,function(key,value){
							tab += "<td style='background: #EEE8AA;'>"+key+":"+"</td>";
							tab += "<td style='background: #EEE8AA;'>"+value+"</td>";
						})
						tab += "</tr>";
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultBaiduQuery").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		})
		
		
		//22申请数据查询
		$("#sumbitforsapply").click(function(){
			var name=$("#name").val();
			var idCard = $("#idCard").val();
			var phone=$("#phone").val();
			var bankCard = $("#bankCard").val();
			console.log(name+"--"+idCard+"--"+phone+"--"+bankCard)
			$("#resultEducations").empty();
			$.ajax({
				url:"query/apply",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"bankCard":bankCard,"phone":phone,"name":name},
				success:function(result){
					console.log(2222)
					if (result.code == 1) {
						var tab="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
						$.each(result.data,function(key,value){
							console.log(value);
							tab += "<td style='background: #EEE8AA;'>"+value+"</td>";
						})
						tab +=	"</tr></table>";
						
					}else {
						tab = "未找到相关信息";
					}
					$("#resultEducations").append(tab);
				},
				error:function(){
					alert("系统错误！")
				}
			});
		});	
	
	//21 、柠檬黑名单查询 
	$("#sumbitthree").click(function(){
	var idCard = $("#idCard").val();
	var phone=$("#phone").val();
	var bankCard = $("#bankCard").val();
	var name=$("#name").val();
	$("#blackListCheats").empty();
		$.ajax({
			url:"Blacklist/Blacklistcation",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"bankCard":bankCard,"phone":phone,"name":name},
			success:function(result){
				if (result.code == 1) {
					var tab="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
					tab += "<td style='background: #EEE8AA;'>"+result.data.message+"</td>";
					tab +=	"</tr></table>";
					
				}else {
					tab = "未找到相关信息";
				}
				$("#blackListCheats").append(tab);
			},
			error:function(){
			}
		});
	});	
	
	//20可以人员信息查询
	$("#lemonSubmittwo").click(function(){
		var idCard = $("#idCard").val();
		var staffType = $("#staffType").val();
		var phone=$("#phone").val();
		$("#resultEducation").empty();
		$.ajax({
			url:"suspiciousPersonscation",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"phone":phone,"staffType":staffType},
			success:function(result){
				console.log(result.data);
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					tab += "<td style='background: #EEE8AA;'>"+result.data.message+"</td>";
					tab +=	"</tr></table>";
					
				}else {
					tab = "未找到相关信息";
				}
				$("#resultEducation").append(tab);
			},
			error:function(){
			}
		});
	});	
	
	//19欺诈案件信息查询 
	$("#lemonSubmitone").click(function(){
		var phone = $("#phone").val();
		var idCard = $("#idCard").val();
		var caseType=$("#caseType").val();
		$("#resultCation").empty();
		$.ajax({
			url:"FraudulentInformation/InformationCation",
			type:"post",
			dataType:"json",
			data:{"phone":phone,"idCard":idCard,"caseType":caseType},
			success:function(result){
				console.log(result.data);
				console.log(result.data.message);
				var tab ="<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					tab += "<td style='background: #EEE8AA;'>"+result.data.message+"</td>";
					tab +=	"</tr></table>";
					
				}else {
					tab = "未找到相关信息";
				}
				$("#resultCation").append(tab);
			},
			error:function(){
			}
		});
	});	
	
	//18 没做
	
	//17逾期短信信息查询
	$("#SubmitBlacklist").click(function(){
		var phone = $("#phone").val();
		$("#resultVerify").empty();
		console.log(phone)
		$.ajax({
			url:"verify/phoneIsInBlacklist",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){
					var html = "<table><tr>"+
						"<td>电话号码</td>"+
						"<td>"+result.data["phone"]+"</td>"+
						"</tr><tr>"+
						"<td>是否有逾期短信</td>"+
						"<td>"+result.data["hit"]+"</td>"+
						"</tr></table>";
					$("#resultVerify").html(html);
				}
			},
			error:function(){
				console.log("错误");
			}
		}) 
	});
	
	//16、通信小号 
	
	//15、手机号所属运营商查询 
	$("#SubmitNameQuery").click(function(){
		var phone = $("#phone").val();
		console.log(phone+"--15")
		$("#resultNameQuery").empty();
		$.ajax({
			url:"phone/OperatorNameQuery",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					tab += "<td style='background: #EEE8AA;'>"+result.data.msg+"</td><td style='background: #EEE8AA;'>"+result.data.operatorType+"</td></tr></table>";
					
				}else {
					tab = "未找到相关信息";
				}
				$("#resultNameQuery").append(tab);
			},
			error:function(){
			}
		});
	});
	
	//14、手机号绑定银行卡信息查询(只支持移动)
	$("#SubmitBindInfo").click(function(){
		console.log(14)
		var phone = $("#phone").val();
		console.log(phone)
		$.ajax({
			url:"mobile/operatorNameQuery",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){
					var html = "<tr>"+
									"<td></td>"+
									"<td><strong>借记卡数量</strong></td>"+
									"<td>"+result.data.debitCardCount+"</td>"+
									"<td><strong>信用卡数量</strong></td>"+
									"<td>"+result.data.creditCardCount+"</td>"+
									"<td><strong></strong></td>"+
								"</tr>"+
								"<tr>"+
									"<td></td>"+
									"<td><strong>是否包含最小值</strong></td>"+
									"<td><strong>是否包含最大值</strong></td>"+
									"<td><strong>最小值</strong></td>"+
									"<td><strong>最大值</strong></td>"+
									"<td><strong>单位</strong></td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>信用卡卡龄</strong></td>"+
									"<td>"+result.data.statusMsg.creditCardAge.minIncluded+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAge.maxIncluded+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAge.max+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAge.min+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAge.unit+"</td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>信用卡帐龄</strong></td>"+
									"<td>"+result.data.statusMsg.creditCardAging.minIncluded+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAging.maxIncluded+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAging.max+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAging.min+"</td>"+
									"<td>"+result.data.statusMsg.creditCardAging.unit+"</td>"+
								"</tr>";
					$("#resultBindInfo").append(html);
				}
			},
			error:function(){
				console.log("错误");
			}
		}) 
	});
	
	//13、手机号绑定银行卡账动信息查询(只支持移动)
	$("#SubmitPhoneActive").click(function(){
		var phone = $("#phone").val();
		$.ajax({
			url:"phoneactivecation",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){
					var html = "<table><tr>"+
						"<td>出账短期波动指数</td>"+
						"<td>入账短期波动指数</td>"+
						"<td>入账长期波动指数</td>"+
						"</tr><tr>"+
						"<td>"+result.data.paymentShortTermVoloatilityIndex+"</td>"+
						"<td>"+result.data.depositShortTermVoloatilityIndex+"</td>"+
						"<td>"+result.data.depositLongTermVoloatilityIndex+"</td>"+
						"</tr></table>";
					$("#resultPhoneActive").html(html);
				}
			},
			error:function(){
				console.log("错误");
			}
		}) 
	});
	
	//12、手机号绑定银行卡还款情况查询(只支持移动)
	// 根据手机号查询银行卡还款情况
	$("#SubmitPhoneInfo").click(function(){
		var phone = $("#phone").val();
		$.ajax({
			url:"phoneinfocation",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){
					var html = "<tr>"+
									"<td></td>"+
									"<td><strong>是否包含最小值</strong></td>"+
									"<td><strong>是否包含最大值</strong></td>"+
									"<td><strong>最小值</strong></td>"+
									"<td><strong>最大值</strong></td>"+
									"<td><strong>单位</strong></td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>当前未结清贷款总金额</strong></td>"+
									"<td>"+result.data["currentOutstandingLoanAmount"]["minIncluded"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanAmount"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanAmount"]["min"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanAmount"]["max"]+"</td>"+
									"<td>"+result.data.currentOutstandingLoanAmount.unit+"</td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>当前未结清贷款总笔数</strong></td>"+
									"<td>"+result.data["currentOutstandingLoanCount"]["minIncluded"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanCount"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanCount"]["min"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanCount"]["max"]+"</td>"+
									"<td>"+result.data["currentOutstandingLoanCount"]["unit"]+"</td>"+
								"</tr>";
					$("#resultInfo table").html(html);
				}else{
					$("#resultInfo").html("未找到相关信息");
				}
			},
			error:function(){
				console.log("错误");
			}
		}) 
	});
	
	//11、手机号绑定银行卡出入账查询(只支持移动) 
	$("#SubmitPhoneLines").click(function(){
		var phone = $("#phone").val();
		$.ajax({
			url:"phoneinfocation/query",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){
					var html = "<tr>"+
									"<td></td>"+
									"<td><strong>是否包含最小值</strong></td>"+
									"<td><strong>是否包含最大值</strong></td>"+
									"<td><strong>最小值</strong></td>"+
									"<td><strong>最大值</strong></td>"+
									"<td><strong>单位</strong></td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>借记卡余额总量</strong></td>"+
									"<td>"+result.data["debitCardRemainingSum"]["minIncluded"]+"</td>"+
									"<td>"+result.data["debitCardRemainingSum"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["debitCardRemainingSum"]["min"]+"</td>"+
									"<td>"+result.data["debitCardRemainingSum"]["max"]+"</td>"+
									"<td>"+result.data["debitCardRemainingSum"]["unit"]+"</td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>3个月借记卡出账金额</strong></td>"+
									"<td>"+result.data["debitCardPayment3m"]["minIncluded"]+"</td>"+
									"<td>"+result.data["debitCardPayment3m"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["debitCardPayment3m"]["min"]+"</td>"+
									"<td>"+result.data["debitCardPayment3m"]["max"]+"</td>"+
									"<td>"+result.data["debitCardPayment3m"]["unit"]+"</td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>3个月借记卡入账金额</strong></td>"+
									"<td>"+result.data["debitCardDeposit3m"]["minIncluded"]+"</td>"+
									"<td>"+result.data["debitCardDeposit3m"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["debitCardDeposit3m"]["min"]+"</td>"+
									"<td>"+result.data["debitCardDeposit3m"]["max"]+"</td>"+
									"<td>"+result.data["debitCardDeposit3m"]["unit"]+"</td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>12个月借记卡出账金额</strong></td>"+
									"<td>"+result.data["debitCardPayment12m"]["minIncluded"]+"</td>"+
									"<td>"+result.data["debitCardPayment12m"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["debitCardPayment12m"]["min"]+"</td>"+
									"<td>"+result.data["debitCardPayment12m"]["max"]+"</td>"+
									"<td>"+result.data["debitCardPayment12m"]["unit"]+"</td>"+
								"</tr>"+
								"<tr>"+
									"<td><strong>12个月借记卡入账金额</strong></td>"+
									"<td>"+result.data["debitCardDeposit12m"]["minIncluded"]+"</td>"+
									"<td>"+result.data["debitCardDeposit12m"]["maxIncluded"]+"</td>"+
									"<td>"+result.data["debitCardDeposit12m"]["min"]+"</td>"+
									"<td>"+result.data["debitCardDeposit12m"]["max"]+"</td>"+
									"<td>"+result.data["debitCardDeposit12m"]["unit"]+"</td>"+
								"</tr>";
					$("#resultLines").html(html);
				}else{
					$("#resultLines").html("未找到相关信息");
				}
			},
			error:function(){
				console.log("错误");
			}
		})
	});
	
	//10、手机号当前状态查询(只支持移动)
	$("#SubmitMoble").click(function(){
		var phone = $("#phone").val();
		$.ajax({
			url:"phonecation/query",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){				
					var html="<table>" +
					"<tr><td>是否存在</td><td>当前状态</td></tr>" +
					"<tr><td>"+result.data.result+"</td><td>"+result.data.msg+"</td></tr>"+
					"</table>";
					$("#resultphone").html(html);
				}else{
					$("#resultphone").html("未找到相关信息");
				}
			},
			error:function(){
				console.log("错误");
			}
		});
	});
	
	//9、手机号在网时长查
	$("#SubmitMoble1").click(function(){
		var phone = $("#phone").val();
		$.ajax({
			url:"mobile/OnlineIntervalQuery",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				if(result.code == 1){				
					var html="<table>" +
					"<tr><td>是否存在</td><td>在网时长</td></tr>" +
					"<tr><td>"+result.data.result+"</td><td>"+result.data.msg+"</td></tr>"+
					"</table>";
					$("#resultPhone").html(html);
				}else{
					$("#resultPhone").html("未找到相关信息");
				}
			},
			error:function(){
				console.log("错误");
			}
		});
	});
	
	//8身份证照片查询 
	$("#SubmitPhoto").click(function(){
		var idCard = $("#idCard").val();
		var name = $("#name").val();
		$("#resultphoto").empty();
		$.ajax({
			url:"idphotocation",
			type:"post",
			dataType:"json",
			data:{"name":name,"idCard":idCard},
			success:function(result){
				console.log(result.data)
				console.log(result.code)
				if(result.code == 1){					
					$("#resultphoto").html("<img src='data:image/jpg;base64,"+result.data.idCardPhoto+"' />");
				}else{					
					$("#resultphoto").html("未找到相关信息");
				}
			},
			error:function(){
				console.log("错误");
			}
		});
	});	
	
	//7、学历查询 
	$("#lemonSubmit").click(function(){
		var idCard = $("#query").val();
		var name = $("#name").val();
		var levelNo = $("#levelNo").val();
		$("#resultVerifyEeducation").empty();
		$.ajax({
			url:"lemoneducation",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"name":name,"levelNo":levelNo},
			success:function(result){
				console.log(result.code)
				console.log(result.data)
				var tab;
				if (result.code == 1) {
					tab =	"<p>degree</p>";
					tab += 	"<table style='width:100%;margin: 5px;'><tr>";
					$.each(result.data.degree,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					})
					tab += "</tr><tr>";
					$.each(result.data.degree,function(key,value){
						tab += "<td>"+value+"</td>";
					})
					tab +=	"</tr></table>";
					tab +=	"<p>college</p>";
					tab += 	"<table style='width:100%;margin: 5px;'><tr>";
					$.each(result.data.college,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					})
					tab += "</tr>";
					tab += "<tr>";
					$.each(result.data.college,function(key,value){
						tab += "<td>"+value+"</td>";
					})
					tab +=	"</tr></table>";
					
					
					tab +=	"<p>personBase</p>";
					tab += 	"<table style='width:100%;margin: 5px;'><tr>";
					$.each(result.data.personBase,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					})
					tab += "</tr>";
					tab += "<tr>";
					$.each(result.data.personBase,function(key,value){
						tab += "<td>"+value+"</td>";
					})
					tab +=	"</tr></table>";
					
				}else {
					tab = "未找到相关信息";
				}
				$("#resultVerifyEeducation").append(tab);
			}
		});
	});	
	
	//6、姓名-银行卡号一致性校验 
	$("#lemonSubmitsys").click(function(){
		var bankCard = $("#bankCard").val();
		var name = $("#name").val();
		console.log(6)
		$("#resultBankcard2item").empty();
		$.ajax({
			url:"bankcard/item",
			type:"post",
			dataType:"json",
			data:{"bankCard":bankCard,"name":name},
			success:function(result){
				console.log(result.data);
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;'>返回结果</td></tr>" +
					"<tr><td>"+result.data.message+"</td></tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#resultBankcard2item").append(html);
			},
			error:function(){
			}
		});
	});
	
	//5、姓名-身份证号-照片三维校验
	$("#lemonSubmitsy").click(function(){
		var idCard = $("#query").val();
		var name = $("#name").val();
		var s=images.split(',');
		var image=s[1];
		$("#result3D").empty();
		$.ajax({
			url:"nameIDPhoto3Dcation",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"name":name,"image":image},
			success:function(result){
				console.log(result.data.msg);
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>返回状态</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.msg+"</td></tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#result3D").append(html);
			},
			error:function(){
			}
		});
	});
	
	//4、姓名-身份证号-手机号-银行卡号一致性校验 
	$("#SubmitBankcard4item").click(function(){
		var name = $("#name").val();
		var phone=$("#phone").val();
		var idCard=$("#idCard").val();
		var bankCard=$("#bankCard").val();
		$("#resultBankcard4item").empty();
		$.ajax({
			url:"bankcard/4item",
			type:"post",
			dataType:"json",
			data:{"bankCard":bankCard,"name":name,"phone":phone,"idCard":idCard},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>状态码</td><td style='background: #EEE8AA;text-align: center;'>返回状态</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.checkStatus+"</td><td style='text-align: center;'>"+result.data.message+"</td></tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#resultBankcard4item").append(html);
			},
			error:function(){
			}
		});
	});	
	
	//3、姓名-身份证号-手机号一致性校验 
	$("#SubmitNamePhoneCheck").click(function(){
		var name = $("#name").val();
		var phone=$("#phone").val();
		var idCard=$("#idCard").val();
		$("#resultNamePhoneCheck").empty();
		console.log(name+"--"+idCard+"--"+phone+"--")
		$.ajax({
			url:"idNamePhoneCheck",
			type:"post",
			dataType:"json",
			data:{"name":name,"phone":phone,"idCard":idCard},
			success:function(result){
				console.log(result.data)
				console.log(result.code)
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>状态码</td><td style='background: #EEE8AA;text-align: center;'>返回状态</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.result+"</td><td style='text-align: center;'>"+result.data.msg+"</td></tr></table>";
				}else {
					html = "未找到相关信息";
				}
				$("#resultNamePhoneCheck").append(html);
			},
			error:function(){
			}
		});
	});	
	
	//2、姓名-身份证号-银行卡号一致性校验
	$("#SubmitAccountVerify").click(function(){
		var name = $("#name").val();
		var bankCard=$("#bankCard").val();
		var idCard=$("#idCard").val();
		$("#resultAccountVerify").empty();
		console.log(name+"--"+idCard+"--"+bankCard+"--")
		$.ajax({
			url:"nameIdCard/AccountVerify",
			type:"post",
			dataType:"json",
			data:{"name":name,"bankCard":bankCard,"idCard":idCard},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>状态码</td><td style='background: #EEE8AA;text-align: center;'>描述信息</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.checkStatus+"</td><td style='text-align: center;'>"+result.data.message+"</td></tr></table>";
				}else {
					html = "未找到相关信息";
				}
				$("#resultAccountVerify").append(html);
			},
			error:function(){
			}
		});
	});	
	
	$("#SubmitAccountVerifyM").click(function(){
		var name = $("#name").val();
		var bankCard=$("#bankCard").val();
		var idCard=$("#idCard").val();
		$("#resultAccountVerifyM").empty();
		console.log(name+"--"+idCard+"--"+bankCard+"--")
		$.ajax({
			url:"nameIdCard/AccountVerifyM",
			type:"post",
			dataType:"json",
			data:{"name":name,"bankCard":bankCard,"idCard":idCard},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>状态码</td><td style='background: #EEE8AA;text-align: center;'>描述信息</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.checkStatus+"</td><td style='text-align: center;'>"+result.data.message+"</td></tr></table>";
				}else {
					html = "未找到相关信息";
				}
				$("#resultAccountVerifyM").append(html);
			},
			error:function(){
			}
		});
	});	
	
	//1
	$("#SubmitIdNameCheck").click(function(){
		var name = $("#name").val();
		var idCard=$("#idCard").val();
		$("#resultIdNameCheck").empty();
		console.log(name+"--"+idCard+"--")
		console.log(1)
		$.ajax({
			url:"idName/Check",
			type:"post",
			dataType:"json",
			data:{"name":name,"idCard":idCard},
			success:function(result){
				var tab;
				tab =	"<p></p>";
				tab += 	"<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'><tr>";
				if (result.code == 1) {
					var html="<table style='width:100%;margin: 5px;' class='table table-striped table-bordered'>" +
					"<tr><td style='background: #EEE8AA;text-align: center;'>状态码</td><td style='background: #EEE8AA;text-align: center;'>描述信息</td></tr>" +
					"<tr><td style='text-align: center;'>"+result.data.checkStatus+"</td><td style='text-align: center;'>"+result.data.message+"</td></tr></table>";
				}else {
					html = "未找到相关信息";
				}
				$("#resultIdNameCheck").append(html);
			},
			error:function(){
			}
		});
	});	
	
});