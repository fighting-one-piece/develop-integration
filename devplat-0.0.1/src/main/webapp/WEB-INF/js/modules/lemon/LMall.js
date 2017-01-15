$(document).ready(function () {

	//37 学历查询（D机构）
	$("#lemonSubmit37").click(function(){
		var name = $("#name").val();
		var idCard=$("#idCard").val();
		$("#result37").empty();
		$.ajax({
			url:"education/organizeD",
			type:"post",
			dataType:"json",
			data:{"name":name,"idCard":idCard},
			success:function(result){
				var tab;
				if (result.code == 1) {
					tab = "<table style='width:100%;margin: 5px;'><tr>";
					$.each(result.data.checkResult.college,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					});
					tab +=	"</tr><tr>";
					$.each(result.data.checkResult.college,function(key,value){
						tab += "<td>"+value+"</td>";
					});
					tab +=	"<table style='width:100%;margin: 5px;margin-top:15px'><tr>";
					$.each(result.data.checkResult.degree,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					});
					tab +=	"</tr><tr>";
					$.each(result.data.checkResult.degree,function(key,value){
						tab += "<td>"+value+"</td>";
					});
					tab +=	"<table style='width:100%;margin: 5px;margin-top:15px'><tr>";
					$.each(result.data.checkResult.personBase,function(key,value){
						tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
					});
					tab +=	"</tr><tr>";
					$.each(result.data.checkResult.personBase,function(key,value){
						tab += "<td>"+value+"</td>";
					});
					tab +=	"</tr></table>";
				}else {
					tab = "未找到相关信息";
				}
				$("#result37").append(tab);
			},
			error:function(){
				console.log("系统错误");
			}
		});
	});	
	
	//36、搜索黑名单
	$("#SubmitSearch").click(function(){
		var idCard = $("#idCard").val();
		var name = $("#name").val();
		var phone = $("#phone").val();
		console.log(idCard)
		$("#resultSearch").empty();
		$.ajax({
			url:"blacklist/search",
			type:"post",
			dataType:"json",
			data:{"idCard":idCard,"name":name,"phone":phone},
			success:function(result){
				console.log(result.data)
				var tab;
				if (result.code == 1) {
					tab = "<table style='width:100%;margin: 5px;'><tr>";
					 if(result.data.blackLevel=="A"){
						 if(result.data.blackReason=="T01"){
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"法人失信,偷税漏税、工商股权冻结、工商无照经营等"+"</td></tr>";
						 }else if (result.data.blackReason=="T02") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"法院失信"+"</td></tr>";
						 }else if (result.data.blackReason=="T03") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"高危网络行为,贴吧（套现吧等）、黑网站（赌博、吸毒）、高危app等"+"</td></tr>";
						 }else if (result.data.blackReason=="T04") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"高危号码,黑产电话、号码标签（欺诈、套现等）、阿里小号等"+"</td></tr>";
						 }else if (result.data.blackReason=="T05") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"身份伪冒风险,身份证丢失、12306泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T06") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"网贷失信	网贷黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T07") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"信贷欺诈,信贷秒借疑似欺诈用户列表、百度内部业务反馈等"+"</td></tr>";
						 }else if (result.data.blackReason=="T08") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"信贷逾期,P2P、消费金融公司逾期名单、百度信贷场景等"+"</td></tr>";
						 }else if (result.data.blackReason=="T09") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"信用卡逾期,银行共享"+"</td></tr>";
						 }else if (result.data.blackReason=="T10") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"账号伪冒风险,账号途径来自机器注册、马甲、撞库或泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T11") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"支付欺诈,支付黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T12") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"羊毛党,刷单电话、某某宝薅羊毛等"+"</td></tr>";
						 }else if (result.data.blackReason=="T13") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"在逃嫌犯"+"</td></tr>";
						 }
					 }if(result.data.blackLevel=="B"){
						 if(result.data.blackReason=="T01"){
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"法人失信,偷税漏税、工商股权冻结、工商无照经营等"+"</td></tr>";
						 }else if (result.data.blackReason=="T02") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"法院失信"+"</td></tr>";
						 }else if (result.data.blackReason=="T03") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"高危网络行为,贴吧（套现吧等）、黑网站（赌博、吸毒）、高危app等"+"</td></tr>";
						 }else if (result.data.blackReason=="T04") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"高危号码,黑产电话、号码标签（欺诈、套现等）、阿里小号等"+"</td></tr>";
						 }else if (result.data.blackReason=="T05") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"身份伪冒风险,身份证丢失、12306泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T06") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"网贷失信	网贷黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T07") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"信贷欺诈,信贷秒借疑似欺诈用户列表、百度内部业务反馈等"+"</td></tr>";
						 }else if (result.data.blackReason=="T08") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"信贷逾期,P2P、消费金融公司逾期名单、百度信贷场景等"+"</td></tr>";
						 }else if (result.data.blackReason=="T09") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"信用卡逾期,银行共享"+"</td></tr>";
						 }else if (result.data.blackReason=="T10") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"账号伪冒风险,账号途径来自机器注册、马甲、撞库或泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T11") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"支付欺诈,支付黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T12") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"羊毛党,刷单电话、某某宝薅羊毛等"+"</td></tr>";
						 }else if (result.data.blackReason=="T13") {
							 tab += "<td style='background: #EEE8AA;'>"+"极黑，建议拒绝"+"</td></tr><tr><td>"+"在逃嫌犯"+"</td></tr>";
						 }
					 }if(result.data.blackLevel=="C"){
						 if(result.data.blackReason=="T01"){
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"法人失信,偷税漏税、工商股权冻结、工商无照经营等"+"</td></tr>";
						 }else if (result.data.blackReason=="T02") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"法院失信"+"</td></tr>";
						 }else if (result.data.blackReason=="T03") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"高危网络行为,贴吧（套现吧等）、黑网站（赌博、吸毒）、高危app等"+"</td></tr>";
						 }else if (result.data.blackReason=="T04") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"高危号码,黑产电话、号码标签（欺诈、套现等）、阿里小号等"+"</td></tr>";
						 }else if (result.data.blackReason=="T05") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"身份伪冒风险,身份证丢失、12306泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T06") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"网贷失信	网贷黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T07") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"信贷欺诈,信贷秒借疑似欺诈用户列表、百度内部业务反馈等"+"</td></tr>";
						 }else if (result.data.blackReason=="T08") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"信贷逾期,P2P、消费金融公司逾期名单、百度信贷场景等"+"</td></tr>";
						 }else if (result.data.blackReason=="T09") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"信用卡逾期,银行共享"+"</td></tr>";
						 }else if (result.data.blackReason=="T10") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"账号伪冒风险,账号途径来自机器注册、马甲、撞库或泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T11") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"支付欺诈,支付黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T12") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"羊毛党,刷单电话、某某宝薅羊毛等"+"</td></tr>";
						 }else if (result.data.blackReason=="T13") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，建议列为重点观察名单"+"</td></tr><tr><td>"+"在逃嫌犯"+"</td></tr>";
						 }
					 }if(result.data.blackLevel=="D"){
						 if(result.data.blackReason=="T01"){
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"法人失信,偷税漏税、工商股权冻结、工商无照经营等"+"</td></tr>";
						 }else if (result.data.blackReason=="T02") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"法院失信"+"</td></tr>";
						 }else if (result.data.blackReason=="T03") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"高危网络行为,贴吧（套现吧等）、黑网站（赌博、吸毒）、高危app等"+"</td></tr>";
						 }else if (result.data.blackReason=="T04") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"高危号码,黑产电话、号码标签（欺诈、套现等）、阿里小号等"+"</td></tr>";
						 }else if (result.data.blackReason=="T05") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"身份伪冒风险,身份证丢失、12306泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T06") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"网贷失信	网贷黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T07") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"信贷欺诈,信贷秒借疑似欺诈用户列表、百度内部业务反馈等"+"</td></tr>";
						 }else if (result.data.blackReason=="T08") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"信贷逾期,P2P、消费金融公司逾期名单、百度信贷场景等"+"</td></tr>";
						 }else if (result.data.blackReason=="T09") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"信用卡逾期,银行共享"+"</td></tr>";
						 }else if (result.data.blackReason=="T10") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"账号伪冒风险,账号途径来自机器注册、马甲、撞库或泄露等"+"</td></tr>";
						 }else if (result.data.blackReason=="T11") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"支付欺诈,支付黑名单"+"</td></tr>";
						 }else if (result.data.blackReason=="T12") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"羊毛党,刷单电话、某某宝薅羊毛等"+"</td></tr>";
						 }else if (result.data.blackReason=="T13") {
							 tab += "<td style='background: #EEE8AA;'>"+"灰黑，较弱，建议列为观察名单"+"</td></tr><tr><td>"+"在逃嫌犯"+"</td></tr>";
						 }
					 }if(result.data.blackLevel=="-9999"){
						 tab += "<td style='background: #EEE8AA;'>"+"未命中"+"</td></tr><tr><td>"+"未命中"+"</td></tr>";
					 }
				}else {
					tab = "未找到相关信息";
				}
				$("#resultSearch").append(tab);
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
				var tab = "";
				if (result.code == 1) {
					$.each(result.data.content,function(index,content){
						var tab1 = "<table style='width:100%;text-align: center;'>";
						var keyStr = "<tr>";
						var valueStr = "<tr>";
						$.each(content,function(key,value){
							if (key == "originalRet"){
								$.each(value,function(key2,value2){
									if (key2 == "hit_list"){
										keyStr += "<td style='background: #EEE8AA;'>"+key2+"</td>";
										var newTb = "<table>";
										$.each(value2,function(index,value3){
											var newKeystr = "<tr>";
											var newValuestr = "<tr>";
											$.each(value3,function(key1,value1){
												newKeystr +="<td style='background: #EEE8AA;'>"+key1+"</td>";
												newValuestr += "<td>"+value1+"</td>";
											})
											newKeystr += "</tr>";
											newValuestr += "</tr>";
											newTb += newKeystr;
											newTb += newValuestr;
											
										})
										newTb += "</table>";
										valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
									} else {
										keyStr += "<td style='background: #EEE8AA;'>"+key2+"</td>";
										valueStr += "<td>"+value2+"</td>";
									}
								})
							} else {
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								valueStr += "<td>"+value+"</td>";
							}
						})
						tab += tab1 + keyStr + valueStr + "</table>";
					})
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
				var tab = "";
				if (result.code == 1) {
					$.each(result.data.content,function(index,content){
						var tab1 = "<table style='width:100%;text-align: center;'>";
						var keyStr = "<tr>";
						var valueStr = "<tr>";
						$.each(content,function(key,value){
							keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
							valueStr += "<td>"+value+"</td>";
						})
						tab += tab1+keyStr+valueStr+"</table>";
					})
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
				var tab = "";
				if (result.code == 1) {
					$.each(result.data.content,function(index,content){
						var tab1 = "<table style='width:100%;text-align: center;'>";
						var keyStr = "<tr>";
						var valueStr = "<tr>";
						$.each(content,function(key,value){
							if (key == "originalRet"){
								$.each(value,function(key1,value1){
									keyStr += "<td style='background: #EEE8AA;'>"+key1+"</td>";
									valueStr += "<td>"+value1+"</td>";
								})
							}else {
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								valueStr += "<td>"+value+"</td>";
							}
						})
						tab += tab1+keyStr+valueStr+"</table>";
					})
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
				var tab = "";
				if (result.code == 1) {
					$.each(result.data.content,function(index,content){
						var tab1 = "<table style='width:100%;text-align: center;'>";
						var keyStr = "<tr>";
						var valueStr = "<tr>";
						$.each(content,function(key,value){
							if (key == "originalRet"){
								$.each(value,function(key1,value1){
									keyStr += "<td style='background: #EEE8AA;'>"+key1+"</td>";
									valueStr += "<td>"+value1+"</td>";
								})
							}else {
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								valueStr += "<td>"+value+"</td>";
							}
						})
						tab += tab1+keyStr+valueStr+"</table>";
					})
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
					var tab = "";
					if (result.code == 1) {
						$.each(result.data.content,function(index,content){
							var tab1 = "<table style='width:100%;text-align: center;'>";
							var keyStr = "<tr>";
							var valueStr = "<tr>";
							$.each(content,function(key,value){
								if (key == "originalRet"){
									$.each(value,function(key1,value1){
										keyStr += "<td style='background: #EEE8AA;'>"+key1+"</td>";
										valueStr += "<td>"+value1+"</td>";
									})
								}else {
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									valueStr += "<td>"+value+"</td>";
								}
							})
							tab += tab1+keyStr+valueStr+"</table>";
						})
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
		$.ajax({
			url:"gambing/dru",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				var tab = "";
				if (result.code == 1) {
					$.each(result.data.content,function(index,content){
						var tab1 = "<table style='width:100%;text-align: center;'>";
						var keyStr = "<tr>";
						var valueStr = "<tr>";
						$.each(content,function(key,value){
							keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
							valueStr += "<td>"+value+"</td>";
						})
						tab += tab1+keyStr+valueStr+"</table>";
					})
				}else {
					tab = "未找到相关信息";
				}
				$("#resultGamble").append(tab);
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
					var tab = "";
					if (result.code == 1) {
						$.each(result.data.content,function(index,content){
							var tab1 = "<table style='width:100%;text-align: center;'>";
							var keyStr = "<tr>";
							var valueStr = "<tr>";
							$.each(content,function(key,value){
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								valueStr += "<td>"+value+"</td>";
							})
							tab += tab1+keyStr+valueStr+"</table>";
						})
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
					var tab = "";
					if (result.code == 1) {
						$.each(result.data.content,function(index,content){
							var tab1 = "<table style='width:100%;text-align: center;'>";
							var keyStr = "<tr>";
							var valueStr = "<tr>";
							$.each(content,function(key,value){
								if (key == "Flag"){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value,function(key2,value2){
										newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										newValuestr += "<td>"+value2+"</td>";
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else if (key == "SpecialList_c"){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value,function(key2,value2){
										newKeystr += "<td style='background: #EEE8AA;'>"+key2+"</td>";
										var newTb1 = "<table>";
										var newKeystr1 = "<tr>";
										var newValuestr1 = "<tr>";
										$.each(value2,function(key3,value3){
											newKeystr1 += "<td style='background: #EEE8AA;'>"+key3+"</td>";
											newValuestr1 += "<td>"+value3+"</td>";
										})
										newKeystr1 += "</tr>";
										newValuestr1 += "</tr>";
										newTb1 += newKeystr1 + newValuestr1 + "</table>";
										newValuestr += "<td style='padding: 0px;'>"+newTb1+"</td>";	
									})
									
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else {
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									valueStr += "<td>"+value+"</td>";
								}
							})
							tab += tab1 + keyStr + valueStr + "</table>";
						})
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
		$("#resultCourt").empty();
			$.ajax({
				url:"court/execute",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone,"idType":idType,"gender":gender},
				success:function(result){
					console.log(result.data);
					var tab = "";
					if (result.code == 1) {
						$.each(result.data.content,function(index,content){
							var tab1 = "<table style='width:100%;text-align: center;'>";
							var keyStr = "<tr>";
							var valueStr = "<tr>";
							$.each(content,function(key,value){
								if (key == "discredit_records"){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value,function(key1,value1){
										newKeystr +="<td style='background: #EEE8AA;'>"+key1+"</td>";
										newValuestr += "<td>"+value1+"</td>";
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else {
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									valueStr += "<td>"+value+"</td>";
								}
							})
							tab += tab1+keyStr+valueStr+"</table>";
						})
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
//		console.log(111)
			$.ajax({
				url:"phone/query",
				type:"post",
				dataType:"json",
				data:{"phone":phone},
				success:function(result){
					var tab;
					if (result.code == 1) {
						$.each(result.data.content,function(index,content){
							var tab1 = "<table style='width:100%;text-align: center;'>";
							var keyStr = "<tr>";
							var valueStr = "<tr>";
							$.each(content,function(key,value){
								if (key == "tags"){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									$.each(value,function(index1,value1){
										var newKeystr = "<tr>";
										var newValuestr = "<tr>";
										$.each(value1,function(key2,value2){
											newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
											newValuestr += "<td>"+value2+"</td>";
											
										})
										newKeystr += "</tr>";
										newValuestr += "</tr>";
										newTb += newKeystr;
										newTb += newValuestr;
									})
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else {
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									valueStr += "<td>"+value+"</td>";
								}
							})
							tab += tab1+keyStr+valueStr+"</table>";
						})
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
		$("#resultAddress").empty();
		var idCard=$("#idCard").val();
		var name=$("#name").val();
		var phone=$("#phone").val();
		var idType=$("#idType").val();
		var homeCity =$("#homeCity").val();
		var homeAddress =$("#homeAddress").val();
		var companyCity =$("#companyCity").val();
		var companyAddress =$("#companyAddress").val();
			$.ajax({
				url:"address/verification",
				type:"post",
				dataType:"json",
				data:{"idCard":idCard,"name":name,"phone":phone,"idType":idType,"homeCity":homeCity,"homeAddress":homeAddress,"companyCity":companyCity,"companyAddress":companyAddress},
				success:function(result){
					var tab = "";
					if (result.code == 1) {
						$.each(result.data.content,function(index,content){
							var tab1 = "<table style='width:100%;text-align: center;'>";
							var keyStr = "<tr>";
							var valueStr = "<tr>";
							$.each(content,function(key,value){
								if (key == "local_freq_address_list" && value != null){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									$.each(value,function(index1,value1){
										var newKeystr = "<tr>";
										var newValuestr = "<tr>";
										$.each(value1,function(key2,value2){
											newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
											newValuestr += "<td>"+value2+"</td>";
											
										})
										newKeystr += "</tr>";
										newValuestr += "</tr>";
										newTb += newKeystr;
										newTb += newValuestr;
									})
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else if (key == "company_address_list" && value != null){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									$.each(value,function(index1,value1){
										var newKeystr = "<tr>";
										var newValuestr = "<tr>";
										$.each(value1,function(key2,value2){
											newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
											newValuestr += "<td>"+value2+"</td>";
											
										})
										newKeystr += "</tr>";
										newValuestr += "</tr>";
										newTb += newKeystr;
										newTb += newValuestr;
									})
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else if (key == "home_address_list" && value != null){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									$.each(value,function(index1,value1){
										var newKeystr = "<tr>";
										var newValuestr = "<tr>";
										$.each(value1,function(key2,value2){
											newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
											newValuestr += "<td>"+value2+"</td>";
											
										})
										newKeystr += "</tr>";
										newValuestr += "</tr>";
										newTb += newKeystr;
										newTb += newValuestr;
									})
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else if (key == "nolocal_freq_address_list" && value != null){
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									var newTb = "<table>";
									$.each(value,function(index1,value1){
										var newKeystr = "<tr>";
										var newValuestr = "<tr>";
										$.each(value1,function(key2,value2){
											newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
											newValuestr += "<td>"+value2+"</td>";
											
										})
										newKeystr += "</tr>";
										newValuestr += "</tr>";
										newTb += newKeystr;
										newTb += newValuestr;
									})
									newTb += "</table>";
									valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
								} else {
									keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
									valueStr += "<td>"+value+"</td>";
								}
							})
							tab += tab1+keyStr+valueStr+"</table>";
						})
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
						})
						tab+="<tr>";
						$.each(result.data,function(key,value){
							tab += "<td>"+value+"</td>";
						})
						tab +=	"</tr></tr></table>";
						
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
				} else {
					$("#resultVerify").html("未找到相关信息");
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
				} else {
					$("#resultBindInfo").append("未找到相关信息");
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
				} else {
					$("#resultBindInfo").append("未找到相关信息");
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
					var tab = "";
					if (result.data.flag == 1){
						var tab1 = "<table style='width:100%;text-align: center;'>";
						var keyStr = "<tr>";
						var valueStr = "<tr>";
						$.each(result.data.result,function(key,value){
							
							if (key == "debitCardRemainingSum" && value != null){
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								var newTb = "<table>";
								$.each(value,function(index1,value1){
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value1,function(key2,value2){
										newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										newValuestr += "<td>"+value2+"</td>";
										
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
								})
								newTb += "</table>";
								valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
							} else if (key == "debitCardPayment3m" && value != null){
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								var newTb = "<table>";
								$.each(value,function(index1,value1){
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value1,function(key2,value2){
										newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										newValuestr += "<td>"+value2+"</td>";
										
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
								})
								newTb += "</table>";
								valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
							} else if (key == "debitCardDeposit3m" && value != null){
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								var newTb = "<table>";
								$.each(value,function(index1,value1){
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value1,function(key2,value2){
										newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										newValuestr += "<td>"+value2+"</td>";
										
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
								})
								newTb += "</table>";
								valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
							} else if (key == "debitCardPayment12m" && value != null){
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								var newTb = "<table>";
								$.each(value,function(index1,value1){
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value1,function(key2,value2){
										newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										newValuestr += "<td>"+value2+"</td>";
										
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
								})
								newTb += "</table>";
								valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
							} else if (key == "debitCardDeposit12m" && value != null){
								keyStr += "<td style='background: #EEE8AA;'>"+key+"</td>";
								var newTb = "<table>";
								$.each(value,function(index1,value1){
									var newKeystr = "<tr>";
									var newValuestr = "<tr>";
									$.each(value1,function(key2,value2){
										newKeystr +="<td style='background: #EEE8AA;'>"+key2+"</td>";
										newValuestr += "<td>"+value2+"</td>";
										
									})
									newKeystr += "</tr>";
									newValuestr += "</tr>";
									newTb += newKeystr;
									newTb += newValuestr;
								})
								newTb += "</table>";
								valueStr += "<td style='padding: 0px;'>"+newTb+"</td>";										
							}
							
							
						})
						tab += tab1 + keyStr + valueStr + "</table>"
						$("#resultLines").html(tab);
					} else {
						$("#resultLines").html("未找到相关信息");
					}
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
	
	//多种接口
	$("#Relational").click(function(){
		var type=  $("input[name='searchType']:checked").val()
		var index = $("input[name='searchContent']").val()
		$("#pvalue").html("查询中......").css("color","red");
		$.ajax({
			url:'relational/query',
			type:'get',
			dataType:"json",
			data:{"type":type,"index":index},
			success:function(result){
				if(result.code ==1){
					$("#pvalue").html(result.data).css("color","black");
				}else{
					$("#pvalue").html("查询失败").css("color","black");
				}
			},
			error:function(){
				alert("系统故障")
			}
		});
	});
});