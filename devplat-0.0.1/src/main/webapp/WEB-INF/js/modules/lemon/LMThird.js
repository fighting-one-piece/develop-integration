$(document).ready(function() {
	
	//2、F姓名-身份证号-银行卡号一致性校验
	$("#SubmitAccountVerifyF").click(function(){
		var name = $("#name").val();
		var bankCard=$("#bankCard").val();
		var idCard=$("#idCard").val();
		$("#resultAccountVerifyF").empty();
		document.getElementById('background').style.display='block';
		$.ajax({
			url:"nameIdCard/AccountVerifyF",
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
				$("#resultAccountVerifyF").append(html);
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("ajax发送请求失败！");
				document.getElementById('background').style.display='none';
			}
		});
	});	
	
	//18、银行卡消费信息查询   1
	$("#lemonSub").click(function(){
		var bankCard = $("#bankCard").val().trim();
		$("#nav_result").empty();
//		colorstyle();
//		blank();
		if (bankCard == null || bankCard == "") {
			$("#error").empty();
			$("#error").append("请输入银行卡号"); 
		}else {
		$("#error").empty();
		document.getElementById('background').style.display='block';
		$.ajax({
			url:"/devplat/bankcards/" + bankCard ,
			type:"get",
			dataType:"json",
			success:function(result){
				console.log(result);
				if (result.code == 1) {
					var keys = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
					var values = "<tr>";	
					$.each(result.data,function(key,value){
						keys +="<td style='background: #EEE8AA;'>"+key+"</td>";
						values +="<td>"+value+"</td>";	
					});
					keys +="</tr>";
					values +="</tr></table>";	
					$("#nav_result").append(keys + values);
				}else {
					$("#nav_result").append("未找到相关数据");
				}
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("ajax发送请求失败！");
				document.getElementById('background').style.display='none';
			}
		});
	}
});	

	
//	//18、银行卡消费信息查询   2
//	$("#lemonSub").click(function(){
//		var bankCard = $("#bankCard").val().trim();
//		colorstyle();
//		blank();
//		if (bankCard == null || bankCard == "") {
//			$("#error").empty();
//			$("#error").append("请输入银行卡号"); 
//		}else {
//		$("#error").empty();
//		document.getElementById('background').style.display='block';
//		$.ajax({
//			url:"query/quota2",
//			type:"post",
//			dataType:"json",
//			data:{"bankCard":bankCard},
//			success:function(result){
//				$("#basic").attr("style","color:red");
//				if (result.code == 1) {
//					//存在validate和result字段
//					if (result.data.statCode == "1000") {
//						if (result.data.validate == 1) {
//							var expense1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense2 = "<tr>";
//							var expense3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense4 = "<tr>";
//							var expense5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense6 = "<tr>";
//							var expense7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense8 = "<tr>";
//							var expense9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											//
//											if (key2 == "S0505") {expense1 +="<td style='background: #EEE8AA;'>2011年至今有交易的月数</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0506") {expense1 +="<td style='background: #EEE8AA;'>第一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0135") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0136") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易城市</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0134") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易金额</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0138") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易商户中文名</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0507") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0508") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易城市</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0509") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易金额</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0510") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易商户中文名</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0511") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总金额（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0512") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总笔数（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0513") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总天数</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0534") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0535") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（银行类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0536") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（全部）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0560") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额同城排名（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0549") {expense3 +="<td style='background: #EEE8AA;'>近3月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0550") {expense3 +="<td style='background: #EEE8AA;'>近6月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0075") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总金额（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0551") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0128") {expense3 +="<td style='background: #EEE8AA;'>年交易金额同城排名（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0563") {expense3 +="<td style='background: #EEE8AA;'>年交易金额同城排名（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0544") {expense3 +="<td style='background: #EEE8AA;'>近12月月均消费金额</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0567") {expense3 +="<td style='background: #EEE8AA;'>近12月月均消费金额本市对比</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0547") {expense3 +="<td style='background: #EEE8AA;'>低于近12月月均消费金额的有效月份</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0548") {expense3 +="<td style='background: #EEE8AA;'>低于近12月月均消费金额的有效月份枚举</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0568") {expense3 +="<td style='background: #EEE8AA;'>每月非批发类交易金额枚expense1";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0569") {expense3 +="<td style='background: #EEE8AA;'>每月非批发类交易笔数枚举</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0570") {expense3 +="<td style='background: #EEE8AA;'>非批发类月均交易金额</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0537") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0538") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（银行类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0539") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（全部）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0561") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数同城排名（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0552") {expense3 +="<td style='background: #EEE8AA;'>近3月交易总笔数（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0553") {expense3 +="<td style='background: #EEE8AA;'>近6月交易总笔数（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0078") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总笔数（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0554") {expense5 +="<td style='background: #EEE8AA;'>近12月交易总笔数（消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0131") {expense5 +="<td style='background: #EEE8AA;'>年交易笔数同城排名（部分消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0564") {expense5 +="<td style='background: #EEE8AA;'>年交易笔数同城排名（消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0053") {expense5 +="<td style='background: #EEE8AA;'>每月消费金额日期分布（各月明细）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0054") {expense5 +="<td style='background: #EEE8AA;'>每月消费笔数日期分布（各月明细）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0541") {expense5 +="<td style='background: #EEE8AA;'>每月消费笔单价列举</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0542") {expense5 +="<td style='background: #EEE8AA;'>近12月平均笔单价</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0543") {expense5 +="<td style='background: #EEE8AA;'>低于近12月平均笔单价的有效月份</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0566") {expense5 +="<td style='background: #EEE8AA;'>近12月平均笔单价同市排名</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0540") {expense5 +="<td style='background: #EEE8AA;'>每月交易天数枚举（全部）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0562") {expense5 +="<td style='background: #EEE8AA;'>每月交易天数同城排名（全部）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0545") {expense5 +="<td style='background: #EEE8AA;'>近12月有交易的月数</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0546") {expense5 +="<td style='background: #EEE8AA;'>近12月有交易的月枚举</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0565") {expense5 +="<td style='background: #EEE8AA;'>年交易天数同城排名</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0573") {expense5 +="<td style='background: #EEE8AA;'>交易地域偏好</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0574") {expense5 +="<td style='background: #EEE8AA;'>近3月交易金额城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0575") {expense5 +="<td style='background: #EEE8AA;'>近3月交易笔数城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0576") {expense5 +="<td style='background: #EEE8AA;'>近3月交易天数城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0083") {expense5 +="<td style='background: #EEE8AA;'>近6月交易金额城市分布（部分消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0577") {expense5 +="<td style='background: #EEE8AA;'>近6月交易金额城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0086") {expense7 +="<td style='background: #EEE8AA;'>近6月交易笔数城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0578") {expense7 +="<td style='background: #EEE8AA;'>近6月交易笔数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0579") {expense7 +="<td style='background: #EEE8AA;'>近6月交易天数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0084") {expense7 +="<td style='background: #EEE8AA;'>近12月交易金额城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0580") {expense7 +="<td style='background: #EEE8AA;'>近12月交易金额城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0087") {expense7 +="<td style='background: #EEE8AA;'>近12月交易笔数城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0581") {expense7 +="<td style='background: #EEE8AA;'>近12月交易笔数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0582") {expense7 +="<td style='background: #EEE8AA;'>近12月交易天数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0583") {expense7 +="<td style='background: #EEE8AA;'>近3月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0584") {expense7 +="<td style='background: #EEE8AA;'>近6月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0585") {expense7 +="<td style='background: #EEE8AA;'>近12月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0300") {expense7 +="<td style='background: #EEE8AA;'>近1月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0301") {expense7 +="<td style='background: #EEE8AA;'>近6月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0302") {expense7 +="<td style='background: #EEE8AA;'>近12月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0303") {expense7 +="<td style='background: #EEE8AA;'>近1月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0304") {expense7 +="<td style='background: #EEE8AA;'>近6月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0305") {expense7 +="<td style='background: #EEE8AA;'>近12月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0120") {expense7 +="<td style='background: #EEE8AA;'>近1月消费金额全 国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0121") {expense7 +="<td style='background: #EEE8AA;'>近6月消费金额全国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0122") {expense7 +="<td style='background: #EEE8AA;'>近12月消费金额全国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0127") {expense9 +="<td style='background: #EEE8AA;'>近6月消费金额全市排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0123") {expense9 +="<td style='background: #EEE8AA;'>近1月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0124") {expense9 +="<td style='background: #EEE8AA;'>近6月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0125") {expense9 +="<td style='background: #EEE8AA;'>近12月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0130") {expense9 +="<td style='background: #EEE8AA;'>近6月消费笔数全市排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0082") {expense9 +="<td style='background: #EEE8AA;'>近1月消费城市金额分布</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0085") {expense9 +="<td style='background: #EEE8AA;'>近1月消费城市笔数分布</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0108") {expense9 +="<td style='background: #EEE8AA;'>近1月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0109") {expense9 +="<td style='background: #EEE8AA;'>近6月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0111") {expense9 +="<td style='background: #EEE8AA;'>近1月最长消费时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0112") {expense9 +="<td style='background: #EEE8AA;'>近6月最长消费时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0113") {expense9 +="<td style='background: #EEE8AA;'>近12月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0297") {expense9 +="<td style='background: #EEE8AA;'>近1月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0298") {expense9 +="<td style='background: #EEE8AA;'>近6月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0299") {expense9 +="<td style='background: #EEE8AA;'>近12月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//										})
//									}
//								});
//						}else {
//							$("#expenseresult18").append("未找到相关数据");
//						}
//						$("#expenseresult18").append("<br>"+expense1+expense2+"</table>"+"<br>"+expense3+expense4+"</table>"+"<br>"+expense5+expense6+"</table>"+"<br>"+expense7+expense8+"</table>"+"<br>"+expense9+expense10+"</table>");
//					}
//					//存在validate和result字段
//					if (result.data.statCode == "1001") {
//						if (result.data.validate == 1) {
//							var expense1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense2 = "<tr>";
//							var expense3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense4 = "<tr>";
//							var expense5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense6 = "<tr>";
//							var expense7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense8 = "<tr>";
//							var expense9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											//
//											if (key2 == "S0505") {expense1 +="<td style='background: #EEE8AA;'>2011年至今有交易的月数</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0506") {expense1 +="<td style='background: #EEE8AA;'>第一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0135") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0136") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易城市</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0134") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易金额</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0138") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易商户中文名</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0507") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0508") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易城市</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0509") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易金额</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0510") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易商户中文名</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0511") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总金额（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0512") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总笔数（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0513") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总天数</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0534") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0535") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（银行类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0536") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（全部）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0560") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额同城排名（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0549") {expense3 +="<td style='background: #EEE8AA;'>近3月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0550") {expense3 +="<td style='background: #EEE8AA;'>近6月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0075") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总金额（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0551") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0128") {expense3 +="<td style='background: #EEE8AA;'>年交易金额同城排名（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0563") {expense3 +="<td style='background: #EEE8AA;'>年交易金额同城排名（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0544") {expense3 +="<td style='background: #EEE8AA;'>近12月月均消费金额</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0567") {expense3 +="<td style='background: #EEE8AA;'>近12月月均消费金额本市对比</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0547") {expense3 +="<td style='background: #EEE8AA;'>低于近12月月均消费金额的有效月份</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0548") {expense3 +="<td style='background: #EEE8AA;'>低于近12月月均消费金额的有效月份枚举</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0568") {expense3 +="<td style='background: #EEE8AA;'>每月非批发类交易金额枚expense1";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0569") {expense3 +="<td style='background: #EEE8AA;'>每月非批发类交易笔数枚举</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0570") {expense3 +="<td style='background: #EEE8AA;'>非批发类月均交易金额</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0537") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0538") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（银行类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0539") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（全部）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0561") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数同城排名（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0552") {expense3 +="<td style='background: #EEE8AA;'>近3月交易总笔数（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0553") {expense3 +="<td style='background: #EEE8AA;'>近6月交易总笔数（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0078") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总笔数（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0554") {expense5 +="<td style='background: #EEE8AA;'>近12月交易总笔数（消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0131") {expense5 +="<td style='background: #EEE8AA;'>年交易笔数同城排名（部分消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0564") {expense5 +="<td style='background: #EEE8AA;'>年交易笔数同城排名（消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0053") {expense5 +="<td style='background: #EEE8AA;'>每月消费金额日期分布（各月明细）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0054") {expense5 +="<td style='background: #EEE8AA;'>每月消费笔数日期分布（各月明细）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0541") {expense5 +="<td style='background: #EEE8AA;'>每月消费笔单价列举</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0542") {expense5 +="<td style='background: #EEE8AA;'>近12月平均笔单价</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0543") {expense5 +="<td style='background: #EEE8AA;'>低于近12月平均笔单价的有效月份</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0566") {expense5 +="<td style='background: #EEE8AA;'>近12月平均笔单价同市排名</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0540") {expense5 +="<td style='background: #EEE8AA;'>每月交易天数枚举（全部）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0562") {expense5 +="<td style='background: #EEE8AA;'>每月交易天数同城排名（全部）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0545") {expense5 +="<td style='background: #EEE8AA;'>近12月有交易的月数</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0546") {expense5 +="<td style='background: #EEE8AA;'>近12月有交易的月枚举</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0565") {expense5 +="<td style='background: #EEE8AA;'>年交易天数同城排名</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0573") {expense5 +="<td style='background: #EEE8AA;'>交易地域偏好</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0574") {expense5 +="<td style='background: #EEE8AA;'>近3月交易金额城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0575") {expense5 +="<td style='background: #EEE8AA;'>近3月交易笔数城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0576") {expense5 +="<td style='background: #EEE8AA;'>近3月交易天数城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0083") {expense5 +="<td style='background: #EEE8AA;'>近6月交易金额城市分布（部分消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0577") {expense5 +="<td style='background: #EEE8AA;'>近6月交易金额城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0086") {expense7 +="<td style='background: #EEE8AA;'>近6月交易笔数城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0578") {expense7 +="<td style='background: #EEE8AA;'>近6月交易笔数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0579") {expense7 +="<td style='background: #EEE8AA;'>近6月交易天数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0084") {expense7 +="<td style='background: #EEE8AA;'>近12月交易金额城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0580") {expense7 +="<td style='background: #EEE8AA;'>近12月交易金额城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0087") {expense7 +="<td style='background: #EEE8AA;'>近12月交易笔数城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0581") {expense7 +="<td style='background: #EEE8AA;'>近12月交易笔数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0582") {expense7 +="<td style='background: #EEE8AA;'>近12月交易天数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0583") {expense7 +="<td style='background: #EEE8AA;'>近3月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0584") {expense7 +="<td style='background: #EEE8AA;'>近6月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0585") {expense7 +="<td style='background: #EEE8AA;'>近12月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0300") {expense7 +="<td style='background: #EEE8AA;'>近1月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0301") {expense7 +="<td style='background: #EEE8AA;'>近6月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0302") {expense7 +="<td style='background: #EEE8AA;'>近12月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0303") {expense7 +="<td style='background: #EEE8AA;'>近1月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0304") {expense7 +="<td style='background: #EEE8AA;'>近6月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0305") {expense7 +="<td style='background: #EEE8AA;'>近12月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0120") {expense7 +="<td style='background: #EEE8AA;'>近1月消费金额全 国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0121") {expense7 +="<td style='background: #EEE8AA;'>近6月消费金额全国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0122") {expense7 +="<td style='background: #EEE8AA;'>近12月消费金额全国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0127") {expense9 +="<td style='background: #EEE8AA;'>近6月消费金额全市排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0123") {expense9 +="<td style='background: #EEE8AA;'>近1月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0124") {expense9 +="<td style='background: #EEE8AA;'>近6月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0125") {expense9 +="<td style='background: #EEE8AA;'>近12月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0130") {expense9 +="<td style='background: #EEE8AA;'>近6月消费笔数全市排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0082") {expense9 +="<td style='background: #EEE8AA;'>近1月消费城市金额分布</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0085") {expense9 +="<td style='background: #EEE8AA;'>近1月消费城市笔数分布</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0108") {expense9 +="<td style='background: #EEE8AA;'>近1月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0109") {expense9 +="<td style='background: #EEE8AA;'>近6月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0111") {expense9 +="<td style='background: #EEE8AA;'>近1月最长消费时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0112") {expense9 +="<td style='background: #EEE8AA;'>近6月最长消费时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0113") {expense9 +="<td style='background: #EEE8AA;'>近12月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0297") {expense9 +="<td style='background: #EEE8AA;'>近1月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0298") {expense9 +="<td style='background: #EEE8AA;'>近6月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0299") {expense9 +="<td style='background: #EEE8AA;'>近12月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//										})
//									}
//								});
//						}else {
//							$("#expenseresult18").append("未找到相关数据");
//						}
//						$("#expenseresult18").append("<br>"+expense1+expense2+"</table>"+"<br>"+expense3+expense4+"</table>"+"<br>"+expense5+expense6+"</table>"+"<br>"+expense7+expense8+"</table>"+"<br>"+expense9+expense10+"</table>");
//					}
//					
//					if (result.data.statCode == "2000") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2001") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2002") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2003") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2004") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2005") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2006") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2007") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2008") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2009") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "9999") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					//存在validate和result字段
//					if (result.data.statCode == "8000") {
//						if (result.data.validate == 1) {
//							var expense1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense2 = "<tr>";
//							var expense3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense4 = "<tr>";
//							var expense5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense6 = "<tr>";
//							var expense7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense8 = "<tr>";
//							var expense9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var expense10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											//
//											if (key2 == "S0505") {expense1 +="<td style='background: #EEE8AA;'>2011年至今有交易的月数</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0506") {expense1 +="<td style='background: #EEE8AA;'>第一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0135") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0136") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易城市</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0134") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易金额</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0138") {expense1 +="<td style='background: #EEE8AA;'>近12月，最近一笔交易商户中文名</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0507") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易时间</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0508") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易城市</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0509") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易金额</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0510") {expense1 +="<td style='background: #EEE8AA;'>2011年至今，最近一笔交易商户中文名</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0511") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总金额（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0512") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总笔数（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0513") {expense1 +="<td style='background: #EEE8AA;'>全部历史交易总天数</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0534") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0535") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（银行类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0536") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额枚举（全部）</td>";expense2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0560") {expense1 +="<td style='background: #EEE8AA;'>每月交易金额同城排名（消费类）</td>";expense2 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0549") {expense3 +="<td style='background: #EEE8AA;'>近3月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0550") {expense3 +="<td style='background: #EEE8AA;'>近6月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0075") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总金额（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0551") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总金额（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0128") {expense3 +="<td style='background: #EEE8AA;'>年交易金额同城排名（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0563") {expense3 +="<td style='background: #EEE8AA;'>年交易金额同城排名（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0544") {expense3 +="<td style='background: #EEE8AA;'>近12月月均消费金额</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0567") {expense3 +="<td style='background: #EEE8AA;'>近12月月均消费金额本市对比</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0547") {expense3 +="<td style='background: #EEE8AA;'>低于近12月月均消费金额的有效月份</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0548") {expense3 +="<td style='background: #EEE8AA;'>低于近12月月均消费金额的有效月份枚举</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0568") {expense3 +="<td style='background: #EEE8AA;'>每月非批发类交易金额枚expense1";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0569") {expense3 +="<td style='background: #EEE8AA;'>每月非批发类交易笔数枚举</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0570") {expense3 +="<td style='background: #EEE8AA;'>非批发类月均交易金额</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0537") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0538") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（银行类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0539") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数枚举（全部）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0561") {expense3 +="<td style='background: #EEE8AA;'>每月交易笔数同城排名（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0552") {expense3 +="<td style='background: #EEE8AA;'>近3月交易总笔数（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0553") {expense3 +="<td style='background: #EEE8AA;'>近6月交易总笔数（消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0078") {expense3 +="<td style='background: #EEE8AA;'>近12月交易总笔数（部分消费类）</td>";expense4 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0554") {expense5 +="<td style='background: #EEE8AA;'>近12月交易总笔数（消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0131") {expense5 +="<td style='background: #EEE8AA;'>年交易笔数同城排名（部分消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0564") {expense5 +="<td style='background: #EEE8AA;'>年交易笔数同城排名（消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0053") {expense5 +="<td style='background: #EEE8AA;'>每月消费金额日期分布（各月明细）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0054") {expense5 +="<td style='background: #EEE8AA;'>每月消费笔数日期分布（各月明细）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0541") {expense5 +="<td style='background: #EEE8AA;'>每月消费笔单价列举</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0542") {expense5 +="<td style='background: #EEE8AA;'>近12月平均笔单价</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0543") {expense5 +="<td style='background: #EEE8AA;'>低于近12月平均笔单价的有效月份</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0566") {expense5 +="<td style='background: #EEE8AA;'>近12月平均笔单价同市排名</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0540") {expense5 +="<td style='background: #EEE8AA;'>每月交易天数枚举（全部）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0562") {expense5 +="<td style='background: #EEE8AA;'>每月交易天数同城排名（全部）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0545") {expense5 +="<td style='background: #EEE8AA;'>近12月有交易的月数</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0546") {expense5 +="<td style='background: #EEE8AA;'>近12月有交易的月枚举</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0565") {expense5 +="<td style='background: #EEE8AA;'>年交易天数同城排名</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0573") {expense5 +="<td style='background: #EEE8AA;'>交易地域偏好</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0574") {expense5 +="<td style='background: #EEE8AA;'>近3月交易金额城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0575") {expense5 +="<td style='background: #EEE8AA;'>近3月交易笔数城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0576") {expense5 +="<td style='background: #EEE8AA;'>近3月交易天数城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0083") {expense5 +="<td style='background: #EEE8AA;'>近6月交易金额城市分布（部分消费类）</td>";expense6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0577") {expense5 +="<td style='background: #EEE8AA;'>近6月交易金额城市分布</td>";expense6 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0086") {expense7 +="<td style='background: #EEE8AA;'>近6月交易笔数城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0578") {expense7 +="<td style='background: #EEE8AA;'>近6月交易笔数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0579") {expense7 +="<td style='background: #EEE8AA;'>近6月交易天数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0084") {expense7 +="<td style='background: #EEE8AA;'>近12月交易金额城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0580") {expense7 +="<td style='background: #EEE8AA;'>近12月交易金额城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0087") {expense7 +="<td style='background: #EEE8AA;'>近12月交易笔数城市分布（部分消费类）</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0581") {expense7 +="<td style='background: #EEE8AA;'>近12月交易笔数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0582") {expense7 +="<td style='background: #EEE8AA;'>近12月交易天数城市分布</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0583") {expense7 +="<td style='background: #EEE8AA;'>近3月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0584") {expense7 +="<td style='background: #EEE8AA;'>近6月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0585") {expense7 +="<td style='background: #EEE8AA;'>近12月TOP3交易地列表</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0300") {expense7 +="<td style='background: #EEE8AA;'>近1月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0301") {expense7 +="<td style='background: #EEE8AA;'>近6月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0302") {expense7 +="<td style='background: #EEE8AA;'>近12月跨城市消费最小时间间隔</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0303") {expense7 +="<td style='background: #EEE8AA;'>近1月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0304") {expense7 +="<td style='background: #EEE8AA;'>近6月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0305") {expense7 +="<td style='background: #EEE8AA;'>近12月,1小时内跨城市消费笔数</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0120") {expense7 +="<td style='background: #EEE8AA;'>近1月消费金额全 国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0121") {expense7 +="<td style='background: #EEE8AA;'>近6月消费金额全国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0122") {expense7 +="<td style='background: #EEE8AA;'>近12月消费金额全国排名</td>";expense8 +="<td>"+value2+"</td>";	}
//											//
//											else if (key2 == "S0127") {expense9 +="<td style='background: #EEE8AA;'>近6月消费金额全市排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0123") {expense9 +="<td style='background: #EEE8AA;'>近1月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0124") {expense9 +="<td style='background: #EEE8AA;'>近6月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0125") {expense9 +="<td style='background: #EEE8AA;'>近12月消费笔数全 国排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0130") {expense9 +="<td style='background: #EEE8AA;'>近6月消费笔数全市排名</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0082") {expense9 +="<td style='background: #EEE8AA;'>近1月消费城市金额分布</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0085") {expense9 +="<td style='background: #EEE8AA;'>近1月消费城市笔数分布</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0108") {expense9 +="<td style='background: #EEE8AA;'>近1月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0109") {expense9 +="<td style='background: #EEE8AA;'>近6月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0111") {expense9 +="<td style='background: #EEE8AA;'>近1月最长消费时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0112") {expense9 +="<td style='background: #EEE8AA;'>近6月最长消费时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0113") {expense9 +="<td style='background: #EEE8AA;'>近12月最长交易时 间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0297") {expense9 +="<td style='background: #EEE8AA;'>近1月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0298") {expense9 +="<td style='background: #EEE8AA;'>近6月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0299") {expense9 +="<td style='background: #EEE8AA;'>近12月同商户多笔消费最小时间间隔</td>";expense10 +="<td>"+value2+"</td>";	}
//										})
//									}
//								});
//						}else {
//							$("#expenseresult18").append("未找到相关数据");
//						}
//						$("#expenseresult18").append("<br>"+expense1+expense2+"</table>"+"<br>"+expense3+expense4+"</table>"+"<br>"+expense5+expense6+"</table>"+"<br>"+expense7+expense8+"</table>"+"<br>"+expense9+expense10+"</table>");
//					}
//					
//					if (result.data.statCode == "8001") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8002") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8888") {
//						$("#expenseresult18").append(result.data.statMsg);
//					}
//					
//				}else {
//					$("#expenseresult18").append("未找到相关数据");
//				}
//				document.getElementById('background').style.display='none';
//			},
//			error:function(){
//				console.log("ajax发送请求失败！");
//				document.getElementById('background').style.display='none';
//			}
//		});
//	}
//});		
//
//
//	//18、银行卡消费信息查询   3
//	$("#lemonSub").click(function(){
//		var bankCard = $("#bankCard").val().trim();
//		colorstyle();
//		blank();
//		if (bankCard == null || bankCard == "") {
//			$("#error").empty();
//			$("#error").append("请输入银行卡号"); 
//		}else {
//		$("#error").empty();
//		document.getElementById('background').style.display='block';
//		$.ajax({
//			url:"query/quota3",
//			type:"post",
//			dataType:"json",
//			data:{"bankCard":bankCard},
//			success:function(result){
//				$("#basic").attr("style","color:red");
//				if (result.code == 1) {
//					//存在validate和result字段
//					if (result.data.statCode == "1000") {
//						if (result.data.validate == 1) {
//							var detailed1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed2 = "<tr>";
//							var detailed3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed4 = "<tr>";
//							var detailed5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed6 = "<tr>";
//							var detailed7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed8 = "<tr>";
//							var detailed9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0683") {detailed1 +="<td style='background: #EEE8AA;'>跨境交易国家列举</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0094") {detailed1 +="<td style='background: #EEE8AA;'>近1月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0095") {detailed1 +="<td style='background: #EEE8AA;'>近6月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0096") {detailed1 +="<td style='background: #EEE8AA;'>近12月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0091") {detailed1 +="<td style='background: #EEE8AA;'>近1月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0092") {detailed1 +="<td style='background: #EEE8AA;'>近6月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0093") {detailed1 +="<td style='background: #EEE8AA;'>近12月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0099") {detailed1 +="<td style='background: #EEE8AA;'>境外交易金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0106") {detailed1 +="<td style='background: #EEE8AA;'>境外交易金额占比</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0102") {detailed1 +="<td style='background: #EEE8AA;'>境外交易笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0107") {detailed1 +="<td style='background: #EEE8AA;'>境外交易笔数占比</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0680") {detailed1 +="<td style='background: #EEE8AA;'>近6月有无境外消费</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0681") {detailed1 +="<td style='background: #EEE8AA;'>近12月有无境外消费</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0056") {detailed1 +="<td style='background: #EEE8AA;'>近6月网购金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0057") {detailed1 +="<td style='background: #EEE8AA;'>近12月网购金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0058") {detailed1 +="<td style='background: #EEE8AA;'>近1月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0059") {detailed1 +="<td style='background: #EEE8AA;'>近6月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0060") {detailed1 +="<td style='background: #EEE8AA;'>近12月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0682") {detailed1 +="<td style='background: #EEE8AA;'>电脑、手机支付金额、笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0348") {detailed1 +="<td style='background: #EEE8AA;'>近1月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0349") {detailed1 +="<td style='background: #EEE8AA;'>近6月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0350") {detailed1 +="<td style='background: #EEE8AA;'>近12月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0351") {detailed1 +="<td style='background: #EEE8AA;'>近1月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0352") {detailed1 +="<td style='background: #EEE8AA;'>近6月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0353") {detailed1 +="<td style='background: #EEE8AA;'>近12月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0052") {detailed1 +="<td style='background: #EEE8AA;'>每月最大单日累计预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0586") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔[0,200] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0587") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔[0,200] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0588") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(200,600] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0589") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(200,600] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0590") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(600,1000] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0591") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(600,1000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0592") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(1000,5000] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0593") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(1000,5000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0594") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(5000,20000]交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0595") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(5000,20000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0596") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(20000,+] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0597") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(20000,+] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0598") {detailed3 +="<td style='background: #EEE8AA;'>低额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0599") {detailed3 +="<td style='background: #EEE8AA;'>中额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0600") {detailed3 +="<td style='background: #EEE8AA;'>高额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0332") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡转账金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0335") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡转账笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0334") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡转账笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0331") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡转账金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0684") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡入账总金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0685") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡入账总笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0686") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡入账总金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0687") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡入账总笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0629") {detailed3 +="<td style='background: #EEE8AA;'>取现金额占交易金额比例</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0630") {detailed3 +="<td style='background: #EEE8AA;'>近12月每月取现金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0631") {detailed3 +="<td style='background: #EEE8AA;'>近12月每月取现笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0632") {detailed5 +="<td style='background: #EEE8AA;'>近12月每月取现地点</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0372") {detailed5 +="<td style='background: #EEE8AA;'>近1月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0373") {detailed5 +="<td style='background: #EEE8AA;'>近6月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0374") {detailed5 +="<td style='background: #EEE8AA;'>近12月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0633") {detailed5 +="<td style='background: #EEE8AA;'>近期取现金额枚举（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0634") {detailed5 +="<td style='background: #EEE8AA;'>近期取现笔数枚举（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0635") {detailed5 +="<td style='background: #EEE8AA;'>近期取现均值波动（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0282") {detailed5 +="<td style='background: #EEE8AA;'>近1月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0283") {detailed5 +="<td style='background: #EEE8AA;'>近6月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0284") {detailed5 +="<td style='background: #EEE8AA;'>近12月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0279") {detailed5 +="<td style='background: #EEE8AA;'>近1月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0280") {detailed5 +="<td style='background: #EEE8AA;'>近6月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0281") {detailed5 +="<td style='background: #EEE8AA;'>近12月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0336") {detailed5 +="<td style='background: #EEE8AA;'>近1月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0337") {detailed5 +="<td style='background: #EEE8AA;'>近6月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0338") {detailed5 +="<td style='background: #EEE8AA;'>近12月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0339") {detailed5 +="<td style='background: #EEE8AA;'>近1月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0340") {detailed5 +="<td style='background: #EEE8AA;'>近6月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0341") {detailed5 +="<td style='background: #EEE8AA;'>近12月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0342") {detailed5 +="<td style='background: #EEE8AA;'>近1月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0343") {detailed5 +="<td style='background: #EEE8AA;'>近6月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0344") {detailed5 +="<td style='background: #EEE8AA;'>近12月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0345") {detailed5 +="<td style='background: #EEE8AA;'>近1月中额转账笔数（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0346") {detailed5 +="<td style='background: #EEE8AA;'>近6月中额转账笔数（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0347") {detailed7 +="<td style='background: #EEE8AA;'>近12月中额转账笔数（800，10000）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0307") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账金额 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0308") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账金额 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0313") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后转账金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0314") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后转账金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0316") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后取现金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0317") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后取现金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0319") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后消费金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0320") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后消费金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0555") {detailed7 +="<td style='background: #EEE8AA;'>每月交易金额环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0556") {detailed7 +="<td style='background: #EEE8AA;'>每月交易笔数环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0557") {detailed7 +="<td style='background: #EEE8AA;'>每月交易天数环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0558") {detailed7 +="<td style='background: #EEE8AA;'>近期交易金额增长率（有交易的近4个月）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0515") {detailed7 +="<td style='background: #EEE8AA;'>交易活跃度-月</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0559") {detailed7 +="<td style='background: #EEE8AA;'>交易活跃度-天</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0615") {detailed7 +="<td style='background: #EEE8AA;'>交易过的交易类型数量</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0616") {detailed7 +="<td style='background: #EEE8AA;'>交易过的交易类型列举</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0110") {detailed7 +="<td style='background: #EEE8AA;'>最长交易天数间隔（过滤小额交易）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0617") {detailed7 +="<td style='background: #EEE8AA;'>最长交易天数间隔</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0018") {detailed7 +="<td style='background: #EEE8AA;'>使用交易的渠道数</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0021") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0024") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易笔数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0019") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0020") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0022") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易次数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0023") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易次数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#detailedresult18").append("未找到相关数据");
//						}
//						$("#detailedresult18").append("<br>"+detailed1+detailed2+"</table>"+"<br>"+detailed3+detailed4+"</table>"+"<br>"+detailed5+detailed6+"</table>"+"<br>"+detailed7+detailed8+"</table>");
//					}
//					
//					//存在validate和result字段
//					if (result.data.statCode == "1001") {
//						if (result.data.validate == 1) {
//							var detailed1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed2 = "<tr>";
//							var detailed3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed4 = "<tr>";
//							var detailed5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed6 = "<tr>";
//							var detailed7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed8 = "<tr>";
//							var detailed9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0683") {detailed1 +="<td style='background: #EEE8AA;'>跨境交易国家列举</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0094") {detailed1 +="<td style='background: #EEE8AA;'>近1月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0095") {detailed1 +="<td style='background: #EEE8AA;'>近6月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0096") {detailed1 +="<td style='background: #EEE8AA;'>近12月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0091") {detailed1 +="<td style='background: #EEE8AA;'>近1月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0092") {detailed1 +="<td style='background: #EEE8AA;'>近6月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0093") {detailed1 +="<td style='background: #EEE8AA;'>近12月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0099") {detailed1 +="<td style='background: #EEE8AA;'>境外交易金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0106") {detailed1 +="<td style='background: #EEE8AA;'>境外交易金额占比</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0102") {detailed1 +="<td style='background: #EEE8AA;'>境外交易笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0107") {detailed1 +="<td style='background: #EEE8AA;'>境外交易笔数占比</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0680") {detailed1 +="<td style='background: #EEE8AA;'>近6月有无境外消费</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0681") {detailed1 +="<td style='background: #EEE8AA;'>近12月有无境外消费</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0056") {detailed1 +="<td style='background: #EEE8AA;'>近6月网购金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0057") {detailed1 +="<td style='background: #EEE8AA;'>近12月网购金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0058") {detailed1 +="<td style='background: #EEE8AA;'>近1月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0059") {detailed1 +="<td style='background: #EEE8AA;'>近6月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0060") {detailed1 +="<td style='background: #EEE8AA;'>近12月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0682") {detailed1 +="<td style='background: #EEE8AA;'>电脑、手机支付金额、笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0348") {detailed1 +="<td style='background: #EEE8AA;'>近1月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0349") {detailed1 +="<td style='background: #EEE8AA;'>近6月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0350") {detailed1 +="<td style='background: #EEE8AA;'>近12月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0351") {detailed1 +="<td style='background: #EEE8AA;'>近1月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0352") {detailed1 +="<td style='background: #EEE8AA;'>近6月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0353") {detailed1 +="<td style='background: #EEE8AA;'>近12月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0052") {detailed1 +="<td style='background: #EEE8AA;'>每月最大单日累计预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0586") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔[0,200] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0587") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔[0,200] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0588") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(200,600] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0589") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(200,600] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0590") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(600,1000] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0591") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(600,1000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0592") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(1000,5000] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0593") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(1000,5000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0594") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(5000,20000]交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0595") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(5000,20000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0596") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(20000,+] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0597") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(20000,+] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0598") {detailed3 +="<td style='background: #EEE8AA;'>低额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0599") {detailed3 +="<td style='background: #EEE8AA;'>中额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0600") {detailed3 +="<td style='background: #EEE8AA;'>高额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0332") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡转账金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0335") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡转账笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0334") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡转账笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0331") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡转账金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0684") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡入账总金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0685") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡入账总笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0686") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡入账总金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0687") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡入账总笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0629") {detailed3 +="<td style='background: #EEE8AA;'>取现金额占交易金额比例</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0630") {detailed3 +="<td style='background: #EEE8AA;'>近12月每月取现金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0631") {detailed3 +="<td style='background: #EEE8AA;'>近12月每月取现笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0632") {detailed5 +="<td style='background: #EEE8AA;'>近12月每月取现地点</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0372") {detailed5 +="<td style='background: #EEE8AA;'>近1月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0373") {detailed5 +="<td style='background: #EEE8AA;'>近6月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0374") {detailed5 +="<td style='background: #EEE8AA;'>近12月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0633") {detailed5 +="<td style='background: #EEE8AA;'>近期取现金额枚举（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0634") {detailed5 +="<td style='background: #EEE8AA;'>近期取现笔数枚举（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0635") {detailed5 +="<td style='background: #EEE8AA;'>近期取现均值波动（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0282") {detailed5 +="<td style='background: #EEE8AA;'>近1月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0283") {detailed5 +="<td style='background: #EEE8AA;'>近6月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0284") {detailed5 +="<td style='background: #EEE8AA;'>近12月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0279") {detailed5 +="<td style='background: #EEE8AA;'>近1月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0280") {detailed5 +="<td style='background: #EEE8AA;'>近6月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0281") {detailed5 +="<td style='background: #EEE8AA;'>近12月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0336") {detailed5 +="<td style='background: #EEE8AA;'>近1月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0337") {detailed5 +="<td style='background: #EEE8AA;'>近6月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0338") {detailed5 +="<td style='background: #EEE8AA;'>近12月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0339") {detailed5 +="<td style='background: #EEE8AA;'>近1月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0340") {detailed5 +="<td style='background: #EEE8AA;'>近6月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0341") {detailed5 +="<td style='background: #EEE8AA;'>近12月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0342") {detailed5 +="<td style='background: #EEE8AA;'>近1月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0343") {detailed5 +="<td style='background: #EEE8AA;'>近6月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0344") {detailed5 +="<td style='background: #EEE8AA;'>近12月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0345") {detailed5 +="<td style='background: #EEE8AA;'>近1月中额转账笔数（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0346") {detailed5 +="<td style='background: #EEE8AA;'>近6月中额转账笔数（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0347") {detailed7 +="<td style='background: #EEE8AA;'>近12月中额转账笔数（800，10000）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0307") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账金额 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0308") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账金额 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0313") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后转账金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0314") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后转账金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0316") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后取现金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0317") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后取现金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0319") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后消费金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0320") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后消费金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0555") {detailed7 +="<td style='background: #EEE8AA;'>每月交易金额环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0556") {detailed7 +="<td style='background: #EEE8AA;'>每月交易笔数环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0557") {detailed7 +="<td style='background: #EEE8AA;'>每月交易天数环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0558") {detailed7 +="<td style='background: #EEE8AA;'>近期交易金额增长率（有交易的近4个月）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0515") {detailed7 +="<td style='background: #EEE8AA;'>交易活跃度-月</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0559") {detailed7 +="<td style='background: #EEE8AA;'>交易活跃度-天</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0615") {detailed7 +="<td style='background: #EEE8AA;'>交易过的交易类型数量</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0616") {detailed7 +="<td style='background: #EEE8AA;'>交易过的交易类型列举</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0110") {detailed7 +="<td style='background: #EEE8AA;'>最长交易天数间隔（过滤小额交易）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0617") {detailed7 +="<td style='background: #EEE8AA;'>最长交易天数间隔</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0018") {detailed7 +="<td style='background: #EEE8AA;'>使用交易的渠道数</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0021") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0024") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易笔数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0019") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0020") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0022") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易次数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0023") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易次数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#detailedresult18").append("未找到相关数据");
//						}
//						$("#detailedresult18").append("<br>"+detailed1+detailed2+"</table>"+"<br>"+detailed3+detailed4+"</table>"+"<br>"+detailed5+detailed6+"</table>"+"<br>"+detailed7+detailed8+"</table>");
//					}
//					if (result.data.statCode == "2000") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2001") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2002") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2003") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2004") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2005") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2006") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2007") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2008") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2009") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "9999") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					//存在validate和result字段
//					if (result.data.statCode == "8000") {
//						if (result.data.validate == 1) {
//							var detailed1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed2 = "<tr>";
//							var detailed3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed4 = "<tr>";
//							var detailed5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed6 = "<tr>";
//							var detailed7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed8 = "<tr>";
//							var detailed9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var detailed10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0683") {detailed1 +="<td style='background: #EEE8AA;'>跨境交易国家列举</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0094") {detailed1 +="<td style='background: #EEE8AA;'>近1月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0095") {detailed1 +="<td style='background: #EEE8AA;'>近6月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0096") {detailed1 +="<td style='background: #EEE8AA;'>近12月外币交易币种数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0091") {detailed1 +="<td style='background: #EEE8AA;'>近1月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0092") {detailed1 +="<td style='background: #EEE8AA;'>近6月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0093") {detailed1 +="<td style='background: #EEE8AA;'>近12月最常用外币交易币种</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0099") {detailed1 +="<td style='background: #EEE8AA;'>境外交易金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0106") {detailed1 +="<td style='background: #EEE8AA;'>境外交易金额占比</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0102") {detailed1 +="<td style='background: #EEE8AA;'>境外交易笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0107") {detailed1 +="<td style='background: #EEE8AA;'>境外交易笔数占比</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0680") {detailed1 +="<td style='background: #EEE8AA;'>近6月有无境外消费</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0681") {detailed1 +="<td style='background: #EEE8AA;'>近12月有无境外消费</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0056") {detailed1 +="<td style='background: #EEE8AA;'>近6月网购金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0057") {detailed1 +="<td style='background: #EEE8AA;'>近12月网购金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0058") {detailed1 +="<td style='background: #EEE8AA;'>近1月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0059") {detailed1 +="<td style='background: #EEE8AA;'>近6月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0060") {detailed1 +="<td style='background: #EEE8AA;'>近12月网购笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0682") {detailed1 +="<td style='background: #EEE8AA;'>电脑、手机支付金额、笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0348") {detailed1 +="<td style='background: #EEE8AA;'>近1月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0349") {detailed1 +="<td style='background: #EEE8AA;'>近6月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0350") {detailed1 +="<td style='background: #EEE8AA;'>近12月预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0351") {detailed1 +="<td style='background: #EEE8AA;'>近1月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0352") {detailed1 +="<td style='background: #EEE8AA;'>近6月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0353") {detailed1 +="<td style='background: #EEE8AA;'>近12月预授权扣款笔数</td>";detailed2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0052") {detailed1 +="<td style='background: #EEE8AA;'>每月最大单日累计预授权扣款金额</td>";detailed2 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0586") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔[0,200] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0587") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔[0,200] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0588") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(200,600] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0589") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(200,600] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0590") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(600,1000] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0591") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(600,1000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0592") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(1000,5000] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0593") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(1000,5000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0594") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(5000,20000]交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0595") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(5000,20000] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0596") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(20000,+] 交易金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0597") {detailed3 +="<td style='background: #EEE8AA;'>每月单笔(20000,+] 交易笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0598") {detailed3 +="<td style='background: #EEE8AA;'>低额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0599") {detailed3 +="<td style='background: #EEE8AA;'>中额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0600") {detailed3 +="<td style='background: #EEE8AA;'>高额交易 占比</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0332") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡转账金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0335") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡转账笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0334") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡转账笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0331") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡转账金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0684") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡入账总金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0685") {detailed3 +="<td style='background: #EEE8AA;'>近6月银行卡入账总笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0686") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡入账总金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0687") {detailed3 +="<td style='background: #EEE8AA;'>近12月银行卡入账总笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0629") {detailed3 +="<td style='background: #EEE8AA;'>取现金额占交易金额比例</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0630") {detailed3 +="<td style='background: #EEE8AA;'>近12月每月取现金额</td>";detailed4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0631") {detailed3 +="<td style='background: #EEE8AA;'>近12月每月取现笔数</td>";detailed4 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0632") {detailed5 +="<td style='background: #EEE8AA;'>近12月每月取现地点</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0372") {detailed5 +="<td style='background: #EEE8AA;'>近1月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0373") {detailed5 +="<td style='background: #EEE8AA;'>近6月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0374") {detailed5 +="<td style='background: #EEE8AA;'>近12月整额消费金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0633") {detailed5 +="<td style='background: #EEE8AA;'>近期取现金额枚举（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0634") {detailed5 +="<td style='background: #EEE8AA;'>近期取现笔数枚举（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0635") {detailed5 +="<td style='background: #EEE8AA;'>近期取现均值波动（有取现的近3个月）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0282") {detailed5 +="<td style='background: #EEE8AA;'>近1月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0283") {detailed5 +="<td style='background: #EEE8AA;'>近6月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0284") {detailed5 +="<td style='background: #EEE8AA;'>近12月最大单日累计取现笔数</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0279") {detailed5 +="<td style='background: #EEE8AA;'>近1月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0280") {detailed5 +="<td style='background: #EEE8AA;'>近6月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0281") {detailed5 +="<td style='background: #EEE8AA;'>近12月最大单日累计取现金额</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0336") {detailed5 +="<td style='background: #EEE8AA;'>近1月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0337") {detailed5 +="<td style='background: #EEE8AA;'>近6月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0338") {detailed5 +="<td style='background: #EEE8AA;'>近12月大额转账金额 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0339") {detailed5 +="<td style='background: #EEE8AA;'>近1月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0340") {detailed5 +="<td style='background: #EEE8AA;'>近6月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0341") {detailed5 +="<td style='background: #EEE8AA;'>近12月大额转账笔数 [1万，+）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0342") {detailed5 +="<td style='background: #EEE8AA;'>近1月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0343") {detailed5 +="<td style='background: #EEE8AA;'>近6月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0344") {detailed5 +="<td style='background: #EEE8AA;'>近12月中额转账金额（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0345") {detailed5 +="<td style='background: #EEE8AA;'>近1月中额转账笔数（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0346") {detailed5 +="<td style='background: #EEE8AA;'>近6月中额转账笔数（800，10000）</td>";detailed6 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0347") {detailed7 +="<td style='background: #EEE8AA;'>近12月中额转账笔数（800，10000）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0307") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账金额 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0308") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账金额 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0313") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后转账金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0314") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后转账金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0316") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后取现金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0317") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后取现金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0319") {detailed7 +="<td style='background: #EEE8AA;'>近6月大额入账后消费金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0320") {detailed7 +="<td style='background: #EEE8AA;'>近12月大额入账后消费金额占比 [1万，+）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0555") {detailed7 +="<td style='background: #EEE8AA;'>每月交易金额环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0556") {detailed7 +="<td style='background: #EEE8AA;'>每月交易笔数环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0557") {detailed7 +="<td style='background: #EEE8AA;'>每月交易天数环比增长</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0558") {detailed7 +="<td style='background: #EEE8AA;'>近期交易金额增长率（有交易的近4个月）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0515") {detailed7 +="<td style='background: #EEE8AA;'>交易活跃度-月</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0559") {detailed7 +="<td style='background: #EEE8AA;'>交易活跃度-天</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0615") {detailed7 +="<td style='background: #EEE8AA;'>交易过的交易类型数量</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0616") {detailed7 +="<td style='background: #EEE8AA;'>交易过的交易类型列举</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0110") {detailed7 +="<td style='background: #EEE8AA;'>最长交易天数间隔（过滤小额交易）</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0617") {detailed7 +="<td style='background: #EEE8AA;'>最长交易天数间隔</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0018") {detailed7 +="<td style='background: #EEE8AA;'>使用交易的渠道数</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0021") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0024") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易笔数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0019") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0020") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道类型</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0022") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易次数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0023") {detailed7 +="<td style='background: #EEE8AA;'>最常用的交易渠道交易次数占比</td>";detailed8 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#detailedresult18").append("未找到相关数据");
//						}
//						$("#detailedresult18").append("<br>"+detailed1+detailed2+"</table>"+"<br>"+detailed3+detailed4+"</table>"+"<br>"+detailed5+detailed6+"</table>"+"<br>"+detailed7+detailed8+"</table>");
//					}
//					if (result.data.statCode == "8001") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8002") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8888") {
//						
//						$("#detailedresult18").append(result.data.statMsg);
//					}
//					
//				}else {
//					
//					$("#detailedresult18").append("未找到相关数据");
//				}
//				document.getElementById('background').style.display='none';
//			},
//			error:function(){
//				console.log("ajax发送请求失败！");
//				document.getElementById('background').style.display='none';
//			}
//		});
//	}
//});	
//
//
//	//18、银行卡消费信息查询   4
//	$("#lemonSub").click(function(){
//		var bankCard = $("#bankCard").val().trim();
//		colorstyle();
//		blank();
//		if (bankCard == null || bankCard == "") {
//			$("#error").empty();
//			$("#error").append("请输入银行卡号"); 
//		}else {
//		$("#error").empty();
//		document.getElementById('background').style.display='block';
//		$.ajax({
//			url:"query/quota4",
//			type:"post",
//			dataType:"json",
//			data:{"bankCard":bankCard},
//			success:function(result){
//				$("#basic").attr("style","color:red");
//				if (result.code == 1) {
//					//存在validate和result字段
//					if (result.data.statCode == "1000") {
//						if (result.data.validate == 1) {
//							var kell1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell2 = "<tr>";
//							var kell3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell4 = "<tr>";
//							var kell5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell6 = "<tr>";
//							var kell7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell8 = "<tr>";
//							var kell9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0378") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易金额</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0379") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易金额</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0381") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易笔数</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0382") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易笔数</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0166") {kell1 +="<td style='background: #EEE8AA;'>夜交易金额 TOP10MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0167") {kell1 +="<td style='background: #EEE8AA;'>夜交易笔数 TOP10MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0384") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0385") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0386") {kell1 +="<td style='background: #EEE8AA;'>近12月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0387") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0388") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0389") {kell1 +="<td style='background: #EEE8AA;'>近12月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0151") {kell1 +="<td style='background: #EEE8AA;'>近1月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0152") {kell1 +="<td style='background: #EEE8AA;'>近6月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0153") {kell1 +="<td style='background: #EEE8AA;'>近12月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0160") {kell1 +="<td style='background: #EEE8AA;'>近1月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0161") {kell1 +="<td style='background: #EEE8AA;'>近6月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0162") {kell1 +="<td style='background: #EEE8AA;'>近12月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0163") {kell1 +="<td style='background: #EEE8AA;'>近1月中额消费笔 数MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0164") {kell1 +="<td style='background: #EEE8AA;'>近6月中额消费笔 数MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0165") {kell3 +="<td style='background: #EEE8AA;'>近12月中额消费笔 数MTC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0572") {kell3 +="<td style='background: #EEE8AA;'>高额交易MCC统计</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0668") {kell3 +="<td style='background: #EEE8AA;'>MTC金额分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0666") {kell3 +="<td style='background: #EEE8AA;'>MTC笔数分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0647") {kell3 +="<td style='background: #EEE8AA;'>MCC金额分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0649") {kell3 +="<td style='background: #EEE8AA;'>MCC笔数分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0669") {kell3 +="<td style='background: #EEE8AA;'>MTC金额TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0667") {kell3 +="<td style='background: #EEE8AA;'>MTC笔数TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0645") {kell3 +="<td style='background: #EEE8AA;'>MCC金额TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0650") {kell3 +="<td style='background: #EEE8AA;'>MCC笔数TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0636") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0637") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0640") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额MCC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0641") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数MCC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0642") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额TOP3MCC</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0643") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数TOP3MCC</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0648") {kell3 +="<td style='background: #EEE8AA;'>交易过的MCC数量</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0646") {kell3 +="<td style='background: #EEE8AA;'>交易过的MCC列举</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0174") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额占比</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0175") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数占比</td>";kell4 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0025") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0026") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0027") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0031") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0032") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0033") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0037") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0038") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0039") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0028") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0029") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0030") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0034") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0035") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0036") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0040") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0041") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0042") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0433") {kell7 +="<td style='background: #EEE8AA;'>近6月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0434") {kell7 +="<td style='background: #EEE8AA;'>近12月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0436") {kell7 +="<td style='background: #EEE8AA;'>近6月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0437") {kell7 +="<td style='background: #EEE8AA;'>近12月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0432") {kell7 +="<td style='background: #EEE8AA;'>近1月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0435") {kell7 +="<td style='background: #EEE8AA;'>近1月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0438") {kell7 +="<td style='background: #EEE8AA;'>近1月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0441") {kell7 +="<td style='background: #EEE8AA;'>近1月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0444") {kell7 +="<td style='background: #EEE8AA;'>近1月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0447") {kell7 +="<td style='background: #EEE8AA;'>近1月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0204") {kell7 +="<td style='background: #EEE8AA;'>近1月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0205") {kell7 +="<td style='background: #EEE8AA;'>近6月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0207") {kell7 +="<td style='background: #EEE8AA;'>近1月公用事业缴 费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0208") {kell7 +="<td style='background: #EEE8AA;'>近6月公用事业缴 费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0456") {kell7 +="<td style='background: #EEE8AA;'>近1月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0459") {kell7 +="<td style='background: #EEE8AA;'>近1月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0439") {kell7 +="<td style='background: #EEE8AA;'>近6月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0440") {kell7 +="<td style='background: #EEE8AA;'>近12月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0442") {kell7 +="<td style='background: #EEE8AA;'>近6月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0443") {kell7 +="<td style='background: #EEE8AA;'>近12月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0451") {kell7 +="<td style='background: #EEE8AA;'>近6月保险支出金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0452") {kell7 +="<td style='background: #EEE8AA;'>近12月保险支出金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0454") {kell7 +="<td style='background: #EEE8AA;'>近6月保险支出笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0455") {kell7 +="<td style='background: #EEE8AA;'>近12月保险支出笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0457") {kell7 +="<td style='background: #EEE8AA;'>近6月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0458") {kell7 +="<td style='background: #EEE8AA;'>近12月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0460") {kell7 +="<td style='background: #EEE8AA;'>近6月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0461") {kell7 +="<td style='background: #EEE8AA;'>近12月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0445") {kell7 +="<td style='background: #EEE8AA;'>近6月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0446") {kell7 +="<td style='background: #EEE8AA;'>近12月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0448") {kell7 +="<td style='background: #EEE8AA;'>近6月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0449") {kell7 +="<td style='background: #EEE8AA;'>近12月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0672") {kell7 +="<td style='background: #EEE8AA;'>近12月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0673") {kell7 +="<td style='background: #EEE8AA;'>近12月公用事业缴费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//
//
//
//											else if (key2 == "S0626") {kell9 +="<td style='background: #EEE8AA;'>最近一笔失败交易原因</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0627") {kell9 +="<td style='background: #EEE8AA;'>最近一笔失败交易日期</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0628") {kell9 +="<td style='background: #EEE8AA;'>最近一日失败交易原因列举</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0620") {kell9 +="<td style='background: #EEE8AA;'>近12月各月失败交易原因枚举</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0621") {kell9 +="<td style='background: #EEE8AA;'>近12月各月失败交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0276") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0277") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0278") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0294") {kell9 +="<td style='background: #EEE8AA;'>近1月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0295") {kell9 +="<td style='background: #EEE8AA;'>近6月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0296") {kell9 +="<td style='background: #EEE8AA;'>近12月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0291") {kell9 +="<td style='background: #EEE8AA;'>近1月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0292") {kell9 +="<td style='background: #EEE8AA;'>近6月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0293") {kell9 +="<td style='background: #EEE8AA;'>近12月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0571") {kell9 +="<td style='background: #EEE8AA;'>每月高额交易统计</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0051") {kell9 +="<td style='background: #EEE8AA;'>每月高占比消费天数（各月明细）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0013") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易商户</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0014") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易城市</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0015") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0016") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易日期</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0601") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近1月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0602") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近3月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0603") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近6月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0604") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近12月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0514") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（全部历史）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0114") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0115") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0116") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0117") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0118") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0119") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#kellresult18").append("未找到相关数据");
//						}
//						$("#kellresult18").append("<br>"+kell1+kell2+"</table>"+"<br>"+kell3+kell4+"</table>"+"<br>"+kell5+kell6+"</table>"+"<br>"+kell7+kell8+"</table>"+"<br>"+kell9+kell10+"</table>");
//					}
//					
//					//存在validate和result字段
//					if (result.data.statCode == "1001") {
//						if (result.data.validate == 1) {
//							var kell1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell2 = "<tr>";
//							var kell3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell4 = "<tr>";
//							var kell5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell6 = "<tr>";
//							var kell7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell8 = "<tr>";
//							var kell9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0378") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易金额</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0379") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易金额</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0381") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易笔数</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0382") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易笔数</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0166") {kell1 +="<td style='background: #EEE8AA;'>夜交易金额 TOP10MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0167") {kell1 +="<td style='background: #EEE8AA;'>夜交易笔数 TOP10MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0384") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0385") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0386") {kell1 +="<td style='background: #EEE8AA;'>近12月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0387") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0388") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0389") {kell1 +="<td style='background: #EEE8AA;'>近12月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0151") {kell1 +="<td style='background: #EEE8AA;'>近1月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0152") {kell1 +="<td style='background: #EEE8AA;'>近6月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0153") {kell1 +="<td style='background: #EEE8AA;'>近12月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0160") {kell1 +="<td style='background: #EEE8AA;'>近1月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0161") {kell1 +="<td style='background: #EEE8AA;'>近6月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0162") {kell1 +="<td style='background: #EEE8AA;'>近12月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0163") {kell1 +="<td style='background: #EEE8AA;'>近1月中额消费笔 数MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0164") {kell1 +="<td style='background: #EEE8AA;'>近6月中额消费笔 数MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0165") {kell3 +="<td style='background: #EEE8AA;'>近12月中额消费笔 数MTC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0572") {kell3 +="<td style='background: #EEE8AA;'>高额交易MCC统计</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0668") {kell3 +="<td style='background: #EEE8AA;'>MTC金额分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0666") {kell3 +="<td style='background: #EEE8AA;'>MTC笔数分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0647") {kell3 +="<td style='background: #EEE8AA;'>MCC金额分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0649") {kell3 +="<td style='background: #EEE8AA;'>MCC笔数分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0669") {kell3 +="<td style='background: #EEE8AA;'>MTC金额TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0667") {kell3 +="<td style='background: #EEE8AA;'>MTC笔数TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0645") {kell3 +="<td style='background: #EEE8AA;'>MCC金额TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0650") {kell3 +="<td style='background: #EEE8AA;'>MCC笔数TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0636") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0637") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0640") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额MCC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0641") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数MCC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0642") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额TOP3MCC</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0643") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数TOP3MCC</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0648") {kell3 +="<td style='background: #EEE8AA;'>交易过的MCC数量</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0646") {kell3 +="<td style='background: #EEE8AA;'>交易过的MCC列举</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0174") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额占比</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0175") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数占比</td>";kell4 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0025") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0026") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0027") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0031") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0032") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0033") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0037") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0038") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0039") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0028") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0029") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0030") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0034") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0035") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0036") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0040") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0041") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0042") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0433") {kell7 +="<td style='background: #EEE8AA;'>近6月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0434") {kell7 +="<td style='background: #EEE8AA;'>近12月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0436") {kell7 +="<td style='background: #EEE8AA;'>近6月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0437") {kell7 +="<td style='background: #EEE8AA;'>近12月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0432") {kell7 +="<td style='background: #EEE8AA;'>近1月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0435") {kell7 +="<td style='background: #EEE8AA;'>近1月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0438") {kell7 +="<td style='background: #EEE8AA;'>近1月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0441") {kell7 +="<td style='background: #EEE8AA;'>近1月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0444") {kell7 +="<td style='background: #EEE8AA;'>近1月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0447") {kell7 +="<td style='background: #EEE8AA;'>近1月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0204") {kell7 +="<td style='background: #EEE8AA;'>近1月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0205") {kell7 +="<td style='background: #EEE8AA;'>近6月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0207") {kell7 +="<td style='background: #EEE8AA;'>近1月公用事业缴 费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0208") {kell7 +="<td style='background: #EEE8AA;'>近6月公用事业缴 费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0456") {kell7 +="<td style='background: #EEE8AA;'>近1月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0459") {kell7 +="<td style='background: #EEE8AA;'>近1月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0439") {kell7 +="<td style='background: #EEE8AA;'>近6月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0440") {kell7 +="<td style='background: #EEE8AA;'>近12月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0442") {kell7 +="<td style='background: #EEE8AA;'>近6月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0443") {kell7 +="<td style='background: #EEE8AA;'>近12月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0451") {kell7 +="<td style='background: #EEE8AA;'>近6月保险支出金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0452") {kell7 +="<td style='background: #EEE8AA;'>近12月保险支出金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0454") {kell7 +="<td style='background: #EEE8AA;'>近6月保险支出笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0455") {kell7 +="<td style='background: #EEE8AA;'>近12月保险支出笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0457") {kell7 +="<td style='background: #EEE8AA;'>近6月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0458") {kell7 +="<td style='background: #EEE8AA;'>近12月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0460") {kell7 +="<td style='background: #EEE8AA;'>近6月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0461") {kell7 +="<td style='background: #EEE8AA;'>近12月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0445") {kell7 +="<td style='background: #EEE8AA;'>近6月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0446") {kell7 +="<td style='background: #EEE8AA;'>近12月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0448") {kell7 +="<td style='background: #EEE8AA;'>近6月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0449") {kell7 +="<td style='background: #EEE8AA;'>近12月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0672") {kell7 +="<td style='background: #EEE8AA;'>近12月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0673") {kell7 +="<td style='background: #EEE8AA;'>近12月公用事业缴费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//
//
//
//											else if (key2 == "S0626") {kell9 +="<td style='background: #EEE8AA;'>最近一笔失败交易原因</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0627") {kell9 +="<td style='background: #EEE8AA;'>最近一笔失败交易日期</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0628") {kell9 +="<td style='background: #EEE8AA;'>最近一日失败交易原因列举</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0620") {kell9 +="<td style='background: #EEE8AA;'>近12月各月失败交易原因枚举</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0621") {kell9 +="<td style='background: #EEE8AA;'>近12月各月失败交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0276") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0277") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0278") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0294") {kell9 +="<td style='background: #EEE8AA;'>近1月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0295") {kell9 +="<td style='background: #EEE8AA;'>近6月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0296") {kell9 +="<td style='background: #EEE8AA;'>近12月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0291") {kell9 +="<td style='background: #EEE8AA;'>近1月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0292") {kell9 +="<td style='background: #EEE8AA;'>近6月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0293") {kell9 +="<td style='background: #EEE8AA;'>近12月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0571") {kell9 +="<td style='background: #EEE8AA;'>每月高额交易统计</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0051") {kell9 +="<td style='background: #EEE8AA;'>每月高占比消费天数（各月明细）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0013") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易商户</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0014") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易城市</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0015") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0016") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易日期</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0601") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近1月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0602") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近3月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0603") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近6月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0604") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近12月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0514") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（全部历史）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0114") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0115") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0116") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0117") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0118") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0119") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#kellresult18").append("未找到相关数据");
//						}
//						$("#kellresult18").append("<br>"+kell1+kell2+"</table>"+"<br>"+kell3+kell4+"</table>"+"<br>"+kell5+kell6+"</table>"+"<br>"+kell7+kell8+"</table>"+"<br>"+kell9+kell10+"</table>");
//					}
//					if (result.data.statCode == "2000") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2001") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2002") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2003") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2004") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2005") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2006") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2007") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2008") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2009") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "9999") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					//存在validate和result字段
//					if (result.data.statCode == "8000") {
//						if (result.data.validate == 1) {
//							var kell1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell2 = "<tr>";
//							var kell3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell4 = "<tr>";
//							var kell5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell6 = "<tr>";
//							var kell7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell8 = "<tr>";
//							var kell9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var kell10 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0378") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易金额</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0379") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易金额</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0381") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易笔数</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0382") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易笔数</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0166") {kell1 +="<td style='background: #EEE8AA;'>夜交易金额 TOP10MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0167") {kell1 +="<td style='background: #EEE8AA;'>夜交易笔数 TOP10MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0384") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0385") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0386") {kell1 +="<td style='background: #EEE8AA;'>近12月夜交易金额 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0387") {kell1 +="<td style='background: #EEE8AA;'>近1月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0388") {kell1 +="<td style='background: #EEE8AA;'>近6月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0389") {kell1 +="<td style='background: #EEE8AA;'>近12月夜交易笔数 MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0151") {kell1 +="<td style='background: #EEE8AA;'>近1月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0152") {kell1 +="<td style='background: #EEE8AA;'>近6月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0153") {kell1 +="<td style='background: #EEE8AA;'>近12月有过交易的 MTC</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0160") {kell1 +="<td style='background: #EEE8AA;'>近1月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0161") {kell1 +="<td style='background: #EEE8AA;'>近6月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0162") {kell1 +="<td style='background: #EEE8AA;'>近12月中额消费金 额MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0163") {kell1 +="<td style='background: #EEE8AA;'>近1月中额消费笔 数MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0164") {kell1 +="<td style='background: #EEE8AA;'>近6月中额消费笔 数MTC分布</td>";kell2 +="<td>"+value2+"</td>";	}
//											
//											else if (key2 == "S0165") {kell3 +="<td style='background: #EEE8AA;'>近12月中额消费笔 数MTC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0572") {kell3 +="<td style='background: #EEE8AA;'>高额交易MCC统计</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0668") {kell3 +="<td style='background: #EEE8AA;'>MTC金额分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0666") {kell3 +="<td style='background: #EEE8AA;'>MTC笔数分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0647") {kell3 +="<td style='background: #EEE8AA;'>MCC金额分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0649") {kell3 +="<td style='background: #EEE8AA;'>MCC笔数分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0669") {kell3 +="<td style='background: #EEE8AA;'>MTC金额TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0667") {kell3 +="<td style='background: #EEE8AA;'>MTC笔数TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0645") {kell3 +="<td style='background: #EEE8AA;'>MCC金额TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0650") {kell3 +="<td style='background: #EEE8AA;'>MCC笔数TOP5</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0636") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0637") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0640") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额MCC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0641") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数MCC分布</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0642") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额TOP3MCC</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0643") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数TOP3MCC</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0648") {kell3 +="<td style='background: #EEE8AA;'>交易过的MCC数量</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0646") {kell3 +="<td style='background: #EEE8AA;'>交易过的MCC列举</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0174") {kell3 +="<td style='background: #EEE8AA;'>夜交易金额占比</td>";kell4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0175") {kell3 +="<td style='background: #EEE8AA;'>夜交易笔数占比</td>";kell4 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0025") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0026") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0027") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0031") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0032") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0033") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0037") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0038") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0039") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中额零头金额</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0028") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0029") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0030") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付笔 数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0034") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0035") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0036") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0040") {kell5 +="<td style='background: #EEE8AA;'>近1月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0041") {kell5 +="<td style='background: #EEE8AA;'>近6月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0042") {kell5 +="<td style='background: #EEE8AA;'>近12月三方支付中 额零头笔数</td>";kell6 +="<td>"+value2+"</td>";	}
//
//
//											else if (key2 == "S0433") {kell7 +="<td style='background: #EEE8AA;'>近6月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0434") {kell7 +="<td style='background: #EEE8AA;'>近12月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0436") {kell7 +="<td style='background: #EEE8AA;'>近6月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0437") {kell7 +="<td style='background: #EEE8AA;'>近12月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0432") {kell7 +="<td style='background: #EEE8AA;'>近1月法律服务消费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0435") {kell7 +="<td style='background: #EEE8AA;'>近1月法律服务消费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0438") {kell7 +="<td style='background: #EEE8AA;'>近1月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0441") {kell7 +="<td style='background: #EEE8AA;'>近1月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0444") {kell7 +="<td style='background: #EEE8AA;'>近1月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0447") {kell7 +="<td style='background: #EEE8AA;'>近1月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0204") {kell7 +="<td style='background: #EEE8AA;'>近1月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0205") {kell7 +="<td style='background: #EEE8AA;'>近6月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0207") {kell7 +="<td style='background: #EEE8AA;'>近1月公用事业缴 费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0208") {kell7 +="<td style='background: #EEE8AA;'>近6月公用事业缴 费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0456") {kell7 +="<td style='background: #EEE8AA;'>近1月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0459") {kell7 +="<td style='background: #EEE8AA;'>近1月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0439") {kell7 +="<td style='background: #EEE8AA;'>近6月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0440") {kell7 +="<td style='background: #EEE8AA;'>近12月罚款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0442") {kell7 +="<td style='background: #EEE8AA;'>近6月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0443") {kell7 +="<td style='background: #EEE8AA;'>近12月罚款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0451") {kell7 +="<td style='background: #EEE8AA;'>近6月保险支出金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0452") {kell7 +="<td style='background: #EEE8AA;'>近12月保险支出金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0454") {kell7 +="<td style='background: #EEE8AA;'>近6月保险支出笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0455") {kell7 +="<td style='background: #EEE8AA;'>近12月保险支出笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0457") {kell7 +="<td style='background: #EEE8AA;'>近6月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0458") {kell7 +="<td style='background: #EEE8AA;'>近12月捐款金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0460") {kell7 +="<td style='background: #EEE8AA;'>近6月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0461") {kell7 +="<td style='background: #EEE8AA;'>近12月捐款笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0445") {kell7 +="<td style='background: #EEE8AA;'>近6月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0446") {kell7 +="<td style='background: #EEE8AA;'>近12月纳税金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0448") {kell7 +="<td style='background: #EEE8AA;'>近6月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0449") {kell7 +="<td style='background: #EEE8AA;'>近12月纳税笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0672") {kell7 +="<td style='background: #EEE8AA;'>近12月公用事业缴费金额</td>";kell8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0673") {kell7 +="<td style='background: #EEE8AA;'>近12月公用事业缴费笔数</td>";kell8 +="<td>"+value2+"</td>";	}
//
//
//
//											else if (key2 == "S0626") {kell9 +="<td style='background: #EEE8AA;'>最近一笔失败交易原因</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0627") {kell9 +="<td style='background: #EEE8AA;'>最近一笔失败交易日期</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0628") {kell9 +="<td style='background: #EEE8AA;'>最近一日失败交易原因列举</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0620") {kell9 +="<td style='background: #EEE8AA;'>近12月各月失败交易原因枚举</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0621") {kell9 +="<td style='background: #EEE8AA;'>近12月各月失败交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0276") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0277") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0278") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计交易失败笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0294") {kell9 +="<td style='background: #EEE8AA;'>近1月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0295") {kell9 +="<td style='background: #EEE8AA;'>近6月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0296") {kell9 +="<td style='background: #EEE8AA;'>近12月单日同商户多笔等额消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0291") {kell9 +="<td style='background: #EEE8AA;'>近1月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0292") {kell9 +="<td style='background: #EEE8AA;'>近6月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0293") {kell9 +="<td style='background: #EEE8AA;'>近12月单日同商户多笔交易笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0571") {kell9 +="<td style='background: #EEE8AA;'>每月高额交易统计</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0051") {kell9 +="<td style='background: #EEE8AA;'>每月高占比消费天数（各月明细）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0013") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易商户</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0014") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易城市</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0015") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0016") {kell9 +="<td style='background: #EEE8AA;'>单笔1万元以上的交易日期</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0601") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近1月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0602") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近3月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0603") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近6月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0604") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（近12月）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0514") {kell9 +="<td style='background: #EEE8AA;'>单笔最大金额、交易类型（全部历史）</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0114") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0115") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0116") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计消费金额</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0117") {kell9 +="<td style='background: #EEE8AA;'>近1月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0118") {kell9 +="<td style='background: #EEE8AA;'>近6月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0119") {kell9 +="<td style='background: #EEE8AA;'>近12月最大单日累计消费笔数</td>";kell10 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#kellresult18").append("未找到相关数据");
//						}
//						$("#kellresult18").append("<br>"+kell1+kell2+"</table>"+"<br>"+kell3+kell4+"</table>"+"<br>"+kell5+kell6+"</table>"+"<br>"+kell7+kell8+"</table>"+"<br>"+kell9+kell10+"</table>");
//					}
//					if (result.data.statCode == "8001") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8002") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8888") {
//						
//						$("#kellresult18").append(result.data.statMsg);
//					}
//					
//				}else {
//					
//					$("#kellresult18").append("未找到相关数据");
//				}
//				document.getElementById('background').style.display='none';
//			},
//			error:function(){
//				console.log("ajax发送请求失败！");
//				document.getElementById('background').style.display='none';
//			}
//		});
//	}
//});	
//
//
//	//18、银行卡消费信息查询  5
//	$("#lemonSub").click(function(){
//		var bankCard = $("#bankCard").val().trim();
//		colorstyle();
//		blank();
//		if (bankCard == null || bankCard == "") {
//			$("#error").empty();
//			$("#error").append("请输入银行卡号"); 
//		}else {
//		$("#error").empty();
//		document.getElementById('background').style.display='block';
//		$.ajax({
//			url:"query/quota5",
//			type:"post",
//			dataType:"json",
//			data:{"bankCard":bankCard},
//			success:function(result){
//				$("#basic").attr("style","color:red");
//				if (result.code == 1) {
//					//存在validate和result字段
//					if (result.data.statCode == "1000") {
//						if (result.data.validate == 1) {
//							var other1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other2 = "<tr>";
//							var other3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other4 = "<tr>";
//							var other5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other6 = "<tr>";
//							var other7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other8 = "<tr>";
//							var other9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other10 = "<tr>";
//							var other11 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other12 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0678") {other1 +="<td style='background: #EEE8AA;'>近12月百货消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0679") {other1 +="<td style='background: #EEE8AA;'>近12月日用品消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0644") {other1 +="<td style='background: #EEE8AA;'>近12月医院单笔大额支出</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0651") {other1 +="<td style='background: #EEE8AA;'>近12月医院、药店消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0675") {other1 +="<td style='background: #EEE8AA;'>品牌消费1（流百,家纺,酒水）</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0676") {other1 +="<td style='background: #EEE8AA;'>品牌消费2（家居家装,家电,户外运动,珠宝首饰）</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0253") {other1 +="<td style='background: #EEE8AA;'>近6月饮酒场所消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0254") {other1 +="<td style='background: #EEE8AA;'>近12月饮酒场所消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0256") {other1 +="<td style='background: #EEE8AA;'>近6月饮酒场所消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0257") {other1 +="<td style='background: #EEE8AA;'>近12月饮酒场所消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0259") {other1 +="<td style='background: #EEE8AA;'>近6月KTV消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0260") {other1 +="<td style='background: #EEE8AA;'>近12月KTV消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0262") {other1 +="<td style='background: #EEE8AA;'>近6月KTV消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0263") {other1 +="<td style='background: #EEE8AA;'>近12月KTV消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0265") {other1 +="<td style='background: #EEE8AA;'>近6月大型超市消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0266") {other1 +="<td style='background: #EEE8AA;'>近12月大型超市消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0268") {other1 +="<td style='background: #EEE8AA;'>近6月大型超市消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0269") {other1 +="<td style='background: #EEE8AA;'>近12月大型超市消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0241") {other1 +="<td style='background: #EEE8AA;'>近6月高档运动消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0242") {other1 +="<td style='background: #EEE8AA;'>近12月高档运动消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0244") {other1 +="<td style='background: #EEE8AA;'>近6月高档运动消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											
//											
//											
//											else if (key2 == "S0245") {other3 +="<td style='background: #EEE8AA;'>近12月高档运动消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0235") {other3 +="<td style='background: #EEE8AA;'>近6月按摩、保健、美容SPA消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0236") {other3 +="<td style='background: #EEE8AA;'>近12月按摩、保健、美容SPA消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0238") {other3 +="<td style='background: #EEE8AA;'>近6月按摩、保健、美容SPA消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0239") {other3 +="<td style='background: #EEE8AA;'>近12月按摩、保健、美容SPA消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0222") {other3 +="<td style='background: #EEE8AA;'>近1月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0223") {other3 +="<td style='background: #EEE8AA;'>近6月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0224") {other3 +="<td style='background: #EEE8AA;'>近12月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0226") {other3 +="<td style='background: #EEE8AA;'>近6月化妆品消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0227") {other3 +="<td style='background: #EEE8AA;'>近12月化妆品消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0229") {other3 +="<td style='background: #EEE8AA;'>近6月住宿服务消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0230") {other3 +="<td style='background: #EEE8AA;'>近12月住宿服务消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0232") {other3 +="<td style='background: #EEE8AA;'>近6月住宿服务消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0233") {other3 +="<td style='background: #EEE8AA;'>近12月住宿服务消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0199") {other3 +="<td style='background: #EEE8AA;'>近6月医药消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0200") {other3 +="<td style='background: #EEE8AA;'>近12月医药消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0193") {other3 +="<td style='background: #EEE8AA;'>近6月旅行类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0194") {other3 +="<td style='background: #EEE8AA;'>近12月旅行类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0196") {other3 +="<td style='background: #EEE8AA;'>近6月旅行类消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0197") {other3 +="<td style='background: #EEE8AA;'>近12月旅行类消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0187") {other3 +="<td style='background: #EEE8AA;'>近6月餐饮类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0188") {other5 +="<td style='background: #EEE8AA;'>近12月餐饮类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0190") {other5 +="<td style='background: #EEE8AA;'>近6月餐饮类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0191") {other5 +="<td style='background: #EEE8AA;'>近12月餐饮类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0181") {other5 +="<td style='background: #EEE8AA;'>近6月娱乐类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0182") {other5 +="<td style='background: #EEE8AA;'>近12月娱乐类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0184") {other5 +="<td style='background: #EEE8AA;'>近6月娱乐类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0185") {other5 +="<td style='background: #EEE8AA;'>近12月娱乐类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0391") {other5 +="<td style='background: #EEE8AA;'>近6月航空消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0392") {other5 +="<td style='background: #EEE8AA;'>近12月航空消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0394") {other5 +="<td style='background: #EEE8AA;'>近6月航空消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0395") {other5 +="<td style='background: #EEE8AA;'>近12月航空消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0397") {other5 +="<td style='background: #EEE8AA;'>近6月铁路消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0398") {other5 +="<td style='background: #EEE8AA;'>近12月铁路消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0400") {other5 +="<td style='background: #EEE8AA;'>近6月铁路消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0401") {other5 +="<td style='background: #EEE8AA;'>近12月铁路消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0003") {other5 +="<td style='background: #EEE8AA;'>近12月出差次数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0252") {other5 +="<td style='background: #EEE8AA;'>近1月饮酒场所消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0255") {other5 +="<td style='background: #EEE8AA;'>近1月饮酒场所消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0258") {other5 +="<td style='background: #EEE8AA;'>近1月KTV消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0261") {other5 +="<td style='background: #EEE8AA;'>近1月KTV消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0264") {other5 +="<td style='background: #EEE8AA;'>近1月大型超市消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0267") {other5 +="<td style='background: #EEE8AA;'>近1月大型超市消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0240") {other5 +="<td style='background: #EEE8AA;'>近1月高档运动消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0243") {other5 +="<td style='background: #EEE8AA;'>近1月高档运动消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0234") {other7 +="<td style='background: #EEE8AA;'>近1月按摩、保健、美容SPA消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0237") {other7 +="<td style='background: #EEE8AA;'>近1月按摩、保健、美容SPA消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0225") {other7 +="<td style='background: #EEE8AA;'>近1月化妆品消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0228") {other7 +="<td style='background: #EEE8AA;'>近1月住宿服务消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0231") {other7 +="<td style='background: #EEE8AA;'>近1月住宿服务消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0198") {other7 +="<td style='background: #EEE8AA;'>近1月医药消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0201") {other7 +="<td style='background: #EEE8AA;'>近1月医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0202") {other7 +="<td style='background: #EEE8AA;'>近6月医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0203") {other7 +="<td style='background: #EEE8AA;'>近12医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0192") {other7 +="<td style='background: #EEE8AA;'>近1月旅行类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0195") {other7 +="<td style='background: #EEE8AA;'>近1月旅行类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0186") {other7 +="<td style='background: #EEE8AA;'>近1月餐饮类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0189") {other7 +="<td style='background: #EEE8AA;'>近1月餐饮类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0180") {other7 +="<td style='background: #EEE8AA;'>近1月娱乐类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0183") {other7 +="<td style='background: #EEE8AA;'>近1月娱乐类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0390") {other7 +="<td style='background: #EEE8AA;'>近1月航空消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0393") {other7 +="<td style='background: #EEE8AA;'>近1月航空消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0396") {other7 +="<td style='background: #EEE8AA;'>近1月铁路消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0399") {other7 +="<td style='background: #EEE8AA;'>近1月铁路消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0402") {other7 +="<td style='background: #EEE8AA;'>近1月典当、拍卖、信托消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0405") {other7 +="<td style='background: #EEE8AA;'>近1月典当、拍卖、信托消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0408") {other7 +="<td style='background: #EEE8AA;'>近1月证券消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0411") {other9 +="<td style='background: #EEE8AA;'>近1月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0403") {other9 +="<td style='background: #EEE8AA;'>近6月典当、拍卖、信托消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0404") {other9 +="<td style='background: #EEE8AA;'>近12月典当、拍卖、信托消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0406") {other9 +="<td style='background: #EEE8AA;'>近6月典当、拍卖、信托消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0407") {other9 +="<td style='background: #EEE8AA;'>近12月典当、拍卖、信托消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0409") {other9 +="<td style='background: #EEE8AA;'>近6月证券消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0410") {other9 +="<td style='background: #EEE8AA;'>近12月证券消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0412") {other9 +="<td style='background: #EEE8AA;'>近6月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0413") {other9 +="<td style='background: #EEE8AA;'>近12月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0415") {other9 +="<td style='background: #EEE8AA;'>近6月贵重珠宝、首饰、钟表、银器消费金额额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0416") {other9 +="<td style='background: #EEE8AA;'>近12月贵重珠宝、首饰、钟表、银器消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0418") {other9 +="<td style='background: #EEE8AA;'>近6月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0419") {other9 +="<td style='background: #EEE8AA;'>近12月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0421") {other9 +="<td style='background: #EEE8AA;'>近6月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0422") {other9 +="<td style='background: #EEE8AA;'>近12月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0424") {other9 +="<td style='background: #EEE8AA;'>近6月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0425") {other9 +="<td style='background: #EEE8AA;'>近12月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0414") {other9 +="<td style='background: #EEE8AA;'>近1月贵重珠宝、首饰、钟表、银器消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0417") {other9 +="<td style='background: #EEE8AA;'>近1月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0420") {other9 +="<td style='background: #EEE8AA;'>近1月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0423") {other9 +="<td style='background: #EEE8AA;'>近1月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0427") {other9 +="<td style='background: #EEE8AA;'>近6月博彩消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0428") {other9 +="<td style='background: #EEE8AA;'>近12月博彩消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0430") {other9 +="<td style='background: #EEE8AA;'>近6月博彩消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0431") {other11 +="<td style='background: #EEE8AA;'>近12月博彩消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0426") {other11 +="<td style='background: #EEE8AA;'>近1月博彩消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0429") {other11 +="<td style='background: #EEE8AA;'>近1月博彩消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0216") {other11 +="<td style='background: #EEE8AA;'>近1月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0217") {other11 +="<td style='background: #EEE8AA;'>近6月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0218") {other11 +="<td style='background: #EEE8AA;'>近12月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0219") {other11 +="<td style='background: #EEE8AA;'>近1月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0220") {other11 +="<td style='background: #EEE8AA;'>近6月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0221") {other11 +="<td style='background: #EEE8AA;'>近12月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0450") {other11 +="<td style='background: #EEE8AA;'>近1月保险支出金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0453") {other11 +="<td style='background: #EEE8AA;'>近1月保险支出笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0246") {other11 +="<td style='background: #EEE8AA;'>近1月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0247") {other11 +="<td style='background: #EEE8AA;'>近6月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0248") {other11 +="<td style='background: #EEE8AA;'>近12月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0249") {other11 +="<td style='background: #EEE8AA;'>近1月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0250") {other11 +="<td style='background: #EEE8AA;'>近6月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0251") {other11 +="<td style='background: #EEE8AA;'>近12月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#result18").append("未找到相关数据");
//						}
//						$("#result18").append("<br>"+other1+other2+"</table>"+"<br>"+other3+other4+"</table>"+"<br>"+other5+other6+"</table>"+"<br>"+other7+other8+"</table>"+"<br>"+other9+other10+"</table>"+"<br>"+other11+other12+"</table>");
//					}
//					
//					//存在validate和result字段
//					if (result.data.statCode == "1001") {
//						if (result.data.validate == 1) {
//							var other1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other2 = "<tr>";
//							var other3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other4 = "<tr>";
//							var other5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other6 = "<tr>";
//							var other7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other8 = "<tr>";
//							var other9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other10 = "<tr>";
//							var other11 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other12 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0678") {other1 +="<td style='background: #EEE8AA;'>近12月百货消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0679") {other1 +="<td style='background: #EEE8AA;'>近12月日用品消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0644") {other1 +="<td style='background: #EEE8AA;'>近12月医院单笔大额支出</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0651") {other1 +="<td style='background: #EEE8AA;'>近12月医院、药店消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0675") {other1 +="<td style='background: #EEE8AA;'>品牌消费1（流百,家纺,酒水）</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0676") {other1 +="<td style='background: #EEE8AA;'>品牌消费2（家居家装,家电,户外运动,珠宝首饰）</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0253") {other1 +="<td style='background: #EEE8AA;'>近6月饮酒场所消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0254") {other1 +="<td style='background: #EEE8AA;'>近12月饮酒场所消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0256") {other1 +="<td style='background: #EEE8AA;'>近6月饮酒场所消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0257") {other1 +="<td style='background: #EEE8AA;'>近12月饮酒场所消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0259") {other1 +="<td style='background: #EEE8AA;'>近6月KTV消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0260") {other1 +="<td style='background: #EEE8AA;'>近12月KTV消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0262") {other1 +="<td style='background: #EEE8AA;'>近6月KTV消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0263") {other1 +="<td style='background: #EEE8AA;'>近12月KTV消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0265") {other1 +="<td style='background: #EEE8AA;'>近6月大型超市消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0266") {other1 +="<td style='background: #EEE8AA;'>近12月大型超市消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0268") {other1 +="<td style='background: #EEE8AA;'>近6月大型超市消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0269") {other1 +="<td style='background: #EEE8AA;'>近12月大型超市消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0241") {other1 +="<td style='background: #EEE8AA;'>近6月高档运动消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0242") {other1 +="<td style='background: #EEE8AA;'>近12月高档运动消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0244") {other1 +="<td style='background: #EEE8AA;'>近6月高档运动消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											
//											
//											
//											else if (key2 == "S0245") {other3 +="<td style='background: #EEE8AA;'>近12月高档运动消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0235") {other3 +="<td style='background: #EEE8AA;'>近6月按摩、保健、美容SPA消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0236") {other3 +="<td style='background: #EEE8AA;'>近12月按摩、保健、美容SPA消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0238") {other3 +="<td style='background: #EEE8AA;'>近6月按摩、保健、美容SPA消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0239") {other3 +="<td style='background: #EEE8AA;'>近12月按摩、保健、美容SPA消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0222") {other3 +="<td style='background: #EEE8AA;'>近1月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0223") {other3 +="<td style='background: #EEE8AA;'>近6月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0224") {other3 +="<td style='background: #EEE8AA;'>近12月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0226") {other3 +="<td style='background: #EEE8AA;'>近6月化妆品消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0227") {other3 +="<td style='background: #EEE8AA;'>近12月化妆品消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0229") {other3 +="<td style='background: #EEE8AA;'>近6月住宿服务消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0230") {other3 +="<td style='background: #EEE8AA;'>近12月住宿服务消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0232") {other3 +="<td style='background: #EEE8AA;'>近6月住宿服务消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0233") {other3 +="<td style='background: #EEE8AA;'>近12月住宿服务消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0199") {other3 +="<td style='background: #EEE8AA;'>近6月医药消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0200") {other3 +="<td style='background: #EEE8AA;'>近12月医药消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0193") {other3 +="<td style='background: #EEE8AA;'>近6月旅行类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0194") {other3 +="<td style='background: #EEE8AA;'>近12月旅行类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0196") {other3 +="<td style='background: #EEE8AA;'>近6月旅行类消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0197") {other3 +="<td style='background: #EEE8AA;'>近12月旅行类消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0187") {other3 +="<td style='background: #EEE8AA;'>近6月餐饮类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0188") {other5 +="<td style='background: #EEE8AA;'>近12月餐饮类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0190") {other5 +="<td style='background: #EEE8AA;'>近6月餐饮类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0191") {other5 +="<td style='background: #EEE8AA;'>近12月餐饮类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0181") {other5 +="<td style='background: #EEE8AA;'>近6月娱乐类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0182") {other5 +="<td style='background: #EEE8AA;'>近12月娱乐类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0184") {other5 +="<td style='background: #EEE8AA;'>近6月娱乐类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0185") {other5 +="<td style='background: #EEE8AA;'>近12月娱乐类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0391") {other5 +="<td style='background: #EEE8AA;'>近6月航空消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0392") {other5 +="<td style='background: #EEE8AA;'>近12月航空消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0394") {other5 +="<td style='background: #EEE8AA;'>近6月航空消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0395") {other5 +="<td style='background: #EEE8AA;'>近12月航空消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0397") {other5 +="<td style='background: #EEE8AA;'>近6月铁路消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0398") {other5 +="<td style='background: #EEE8AA;'>近12月铁路消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0400") {other5 +="<td style='background: #EEE8AA;'>近6月铁路消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0401") {other5 +="<td style='background: #EEE8AA;'>近12月铁路消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0003") {other5 +="<td style='background: #EEE8AA;'>近12月出差次数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0252") {other5 +="<td style='background: #EEE8AA;'>近1月饮酒场所消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0255") {other5 +="<td style='background: #EEE8AA;'>近1月饮酒场所消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0258") {other5 +="<td style='background: #EEE8AA;'>近1月KTV消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0261") {other5 +="<td style='background: #EEE8AA;'>近1月KTV消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0264") {other5 +="<td style='background: #EEE8AA;'>近1月大型超市消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0267") {other5 +="<td style='background: #EEE8AA;'>近1月大型超市消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0240") {other5 +="<td style='background: #EEE8AA;'>近1月高档运动消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0243") {other5 +="<td style='background: #EEE8AA;'>近1月高档运动消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0234") {other7 +="<td style='background: #EEE8AA;'>近1月按摩、保健、美容SPA消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0237") {other7 +="<td style='background: #EEE8AA;'>近1月按摩、保健、美容SPA消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0225") {other7 +="<td style='background: #EEE8AA;'>近1月化妆品消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0228") {other7 +="<td style='background: #EEE8AA;'>近1月住宿服务消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0231") {other7 +="<td style='background: #EEE8AA;'>近1月住宿服务消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0198") {other7 +="<td style='background: #EEE8AA;'>近1月医药消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0201") {other7 +="<td style='background: #EEE8AA;'>近1月医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0202") {other7 +="<td style='background: #EEE8AA;'>近6月医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0203") {other7 +="<td style='background: #EEE8AA;'>近12医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0192") {other7 +="<td style='background: #EEE8AA;'>近1月旅行类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0195") {other7 +="<td style='background: #EEE8AA;'>近1月旅行类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0186") {other7 +="<td style='background: #EEE8AA;'>近1月餐饮类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0189") {other7 +="<td style='background: #EEE8AA;'>近1月餐饮类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0180") {other7 +="<td style='background: #EEE8AA;'>近1月娱乐类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0183") {other7 +="<td style='background: #EEE8AA;'>近1月娱乐类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0390") {other7 +="<td style='background: #EEE8AA;'>近1月航空消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0393") {other7 +="<td style='background: #EEE8AA;'>近1月航空消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0396") {other7 +="<td style='background: #EEE8AA;'>近1月铁路消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0399") {other7 +="<td style='background: #EEE8AA;'>近1月铁路消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0402") {other7 +="<td style='background: #EEE8AA;'>近1月典当、拍卖、信托消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0405") {other7 +="<td style='background: #EEE8AA;'>近1月典当、拍卖、信托消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0408") {other7 +="<td style='background: #EEE8AA;'>近1月证券消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0411") {other9 +="<td style='background: #EEE8AA;'>近1月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0403") {other9 +="<td style='background: #EEE8AA;'>近6月典当、拍卖、信托消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0404") {other9 +="<td style='background: #EEE8AA;'>近12月典当、拍卖、信托消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0406") {other9 +="<td style='background: #EEE8AA;'>近6月典当、拍卖、信托消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0407") {other9 +="<td style='background: #EEE8AA;'>近12月典当、拍卖、信托消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0409") {other9 +="<td style='background: #EEE8AA;'>近6月证券消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0410") {other9 +="<td style='background: #EEE8AA;'>近12月证券消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0412") {other9 +="<td style='background: #EEE8AA;'>近6月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0413") {other9 +="<td style='background: #EEE8AA;'>近12月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0415") {other9 +="<td style='background: #EEE8AA;'>近6月贵重珠宝、首饰、钟表、银器消费金额额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0416") {other9 +="<td style='background: #EEE8AA;'>近12月贵重珠宝、首饰、钟表、银器消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0418") {other9 +="<td style='background: #EEE8AA;'>近6月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0419") {other9 +="<td style='background: #EEE8AA;'>近12月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0421") {other9 +="<td style='background: #EEE8AA;'>近6月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0422") {other9 +="<td style='background: #EEE8AA;'>近12月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0424") {other9 +="<td style='background: #EEE8AA;'>近6月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0425") {other9 +="<td style='background: #EEE8AA;'>近12月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0414") {other9 +="<td style='background: #EEE8AA;'>近1月贵重珠宝、首饰、钟表、银器消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0417") {other9 +="<td style='background: #EEE8AA;'>近1月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0420") {other9 +="<td style='background: #EEE8AA;'>近1月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0423") {other9 +="<td style='background: #EEE8AA;'>近1月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0427") {other9 +="<td style='background: #EEE8AA;'>近6月博彩消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0428") {other9 +="<td style='background: #EEE8AA;'>近12月博彩消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0430") {other9 +="<td style='background: #EEE8AA;'>近6月博彩消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0431") {other11 +="<td style='background: #EEE8AA;'>近12月博彩消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0426") {other11 +="<td style='background: #EEE8AA;'>近1月博彩消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0429") {other11 +="<td style='background: #EEE8AA;'>近1月博彩消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0216") {other11 +="<td style='background: #EEE8AA;'>近1月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0217") {other11 +="<td style='background: #EEE8AA;'>近6月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0218") {other11 +="<td style='background: #EEE8AA;'>近12月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0219") {other11 +="<td style='background: #EEE8AA;'>近1月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0220") {other11 +="<td style='background: #EEE8AA;'>近6月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0221") {other11 +="<td style='background: #EEE8AA;'>近12月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0450") {other11 +="<td style='background: #EEE8AA;'>近1月保险支出金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0453") {other11 +="<td style='background: #EEE8AA;'>近1月保险支出笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0246") {other11 +="<td style='background: #EEE8AA;'>近1月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0247") {other11 +="<td style='background: #EEE8AA;'>近6月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0248") {other11 +="<td style='background: #EEE8AA;'>近12月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0249") {other11 +="<td style='background: #EEE8AA;'>近1月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0250") {other11 +="<td style='background: #EEE8AA;'>近6月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0251") {other11 +="<td style='background: #EEE8AA;'>近12月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#result18").append("未找到相关数据");
//						}
//						$("#result18").append("<br>"+other1+other2+"</table>"+"<br>"+other3+other4+"</table>"+"<br>"+other5+other6+"</table>"+"<br>"+other7+other8+"</table>"+"<br>"+other9+other10+"</table>"+"<br>"+other11+other12+"</table>");
//					}
//					if (result.data.statCode == "2000") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2001") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2002") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2003") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2004") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2005") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2006") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2007") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2008") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "2009") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "9999") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					//存在validate和result字段
//					if (result.data.statCode == "8000") {
//						if (result.data.validate == 1) {
//							var other1 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other2 = "<tr>";
//							var other3 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other4 = "<tr>";
//							var other5 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other6 = "<tr>";
//							var other7 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other8 = "<tr>";
//							var other9 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other10 = "<tr>";
//							var other11 = "<table style='width:100%; word-break: break-all; word-wrap: break-word;'><tr>";
//							var other12 = "<tr>";
//								$.each(result.data.result,function(key1,value1){
//									if (key1 == "quota") {
//										$.each(value1,function(key2,value2){
//											if (key2 == "S0678") {other1 +="<td style='background: #EEE8AA;'>近12月百货消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0679") {other1 +="<td style='background: #EEE8AA;'>近12月日用品消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0644") {other1 +="<td style='background: #EEE8AA;'>近12月医院单笔大额支出</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0651") {other1 +="<td style='background: #EEE8AA;'>近12月医院、药店消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0675") {other1 +="<td style='background: #EEE8AA;'>品牌消费1（流百,家纺,酒水）</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0676") {other1 +="<td style='background: #EEE8AA;'>品牌消费2（家居家装,家电,户外运动,珠宝首饰）</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0253") {other1 +="<td style='background: #EEE8AA;'>近6月饮酒场所消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0254") {other1 +="<td style='background: #EEE8AA;'>近12月饮酒场所消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0256") {other1 +="<td style='background: #EEE8AA;'>近6月饮酒场所消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0257") {other1 +="<td style='background: #EEE8AA;'>近12月饮酒场所消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0259") {other1 +="<td style='background: #EEE8AA;'>近6月KTV消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0260") {other1 +="<td style='background: #EEE8AA;'>近12月KTV消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0262") {other1 +="<td style='background: #EEE8AA;'>近6月KTV消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0263") {other1 +="<td style='background: #EEE8AA;'>近12月KTV消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0265") {other1 +="<td style='background: #EEE8AA;'>近6月大型超市消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0266") {other1 +="<td style='background: #EEE8AA;'>近12月大型超市消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0268") {other1 +="<td style='background: #EEE8AA;'>近6月大型超市消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0269") {other1 +="<td style='background: #EEE8AA;'>近12月大型超市消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0241") {other1 +="<td style='background: #EEE8AA;'>近6月高档运动消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0242") {other1 +="<td style='background: #EEE8AA;'>近12月高档运动消费金额</td>";other2 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0244") {other1 +="<td style='background: #EEE8AA;'>近6月高档运动消费笔数</td>";other2 +="<td>"+value2+"</td>";	}
//											
//											
//											
//											else if (key2 == "S0245") {other3 +="<td style='background: #EEE8AA;'>近12月高档运动消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0235") {other3 +="<td style='background: #EEE8AA;'>近6月按摩、保健、美容SPA消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0236") {other3 +="<td style='background: #EEE8AA;'>近12月按摩、保健、美容SPA消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0238") {other3 +="<td style='background: #EEE8AA;'>近6月按摩、保健、美容SPA消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0239") {other3 +="<td style='background: #EEE8AA;'>近12月按摩、保健、美容SPA消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0222") {other3 +="<td style='background: #EEE8AA;'>近1月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0223") {other3 +="<td style='background: #EEE8AA;'>近6月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0224") {other3 +="<td style='background: #EEE8AA;'>近12月化妆品消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0226") {other3 +="<td style='background: #EEE8AA;'>近6月化妆品消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0227") {other3 +="<td style='background: #EEE8AA;'>近12月化妆品消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0229") {other3 +="<td style='background: #EEE8AA;'>近6月住宿服务消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0230") {other3 +="<td style='background: #EEE8AA;'>近12月住宿服务消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0232") {other3 +="<td style='background: #EEE8AA;'>近6月住宿服务消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0233") {other3 +="<td style='background: #EEE8AA;'>近12月住宿服务消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0199") {other3 +="<td style='background: #EEE8AA;'>近6月医药消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0200") {other3 +="<td style='background: #EEE8AA;'>近12月医药消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0193") {other3 +="<td style='background: #EEE8AA;'>近6月旅行类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0194") {other3 +="<td style='background: #EEE8AA;'>近12月旅行类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0196") {other3 +="<td style='background: #EEE8AA;'>近6月旅行类消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0197") {other3 +="<td style='background: #EEE8AA;'>近12月旅行类消费笔数</td>";other4 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0187") {other3 +="<td style='background: #EEE8AA;'>近6月餐饮类消费金额</td>";other4 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0188") {other5 +="<td style='background: #EEE8AA;'>近12月餐饮类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0190") {other5 +="<td style='background: #EEE8AA;'>近6月餐饮类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0191") {other5 +="<td style='background: #EEE8AA;'>近12月餐饮类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0181") {other5 +="<td style='background: #EEE8AA;'>近6月娱乐类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0182") {other5 +="<td style='background: #EEE8AA;'>近12月娱乐类消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0184") {other5 +="<td style='background: #EEE8AA;'>近6月娱乐类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0185") {other5 +="<td style='background: #EEE8AA;'>近12月娱乐类消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0391") {other5 +="<td style='background: #EEE8AA;'>近6月航空消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0392") {other5 +="<td style='background: #EEE8AA;'>近12月航空消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0394") {other5 +="<td style='background: #EEE8AA;'>近6月航空消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0395") {other5 +="<td style='background: #EEE8AA;'>近12月航空消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0397") {other5 +="<td style='background: #EEE8AA;'>近6月铁路消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0398") {other5 +="<td style='background: #EEE8AA;'>近12月铁路消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0400") {other5 +="<td style='background: #EEE8AA;'>近6月铁路消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0401") {other5 +="<td style='background: #EEE8AA;'>近12月铁路消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0003") {other5 +="<td style='background: #EEE8AA;'>近12月出差次数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0252") {other5 +="<td style='background: #EEE8AA;'>近1月饮酒场所消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0255") {other5 +="<td style='background: #EEE8AA;'>近1月饮酒场所消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0258") {other5 +="<td style='background: #EEE8AA;'>近1月KTV消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0261") {other5 +="<td style='background: #EEE8AA;'>近1月KTV消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0264") {other5 +="<td style='background: #EEE8AA;'>近1月大型超市消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0267") {other5 +="<td style='background: #EEE8AA;'>近1月大型超市消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0240") {other5 +="<td style='background: #EEE8AA;'>近1月高档运动消费金额</td>";other6 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0243") {other5 +="<td style='background: #EEE8AA;'>近1月高档运动消费笔数</td>";other6 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0234") {other7 +="<td style='background: #EEE8AA;'>近1月按摩、保健、美容SPA消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0237") {other7 +="<td style='background: #EEE8AA;'>近1月按摩、保健、美容SPA消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0225") {other7 +="<td style='background: #EEE8AA;'>近1月化妆品消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0228") {other7 +="<td style='background: #EEE8AA;'>近1月住宿服务消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0231") {other7 +="<td style='background: #EEE8AA;'>近1月住宿服务消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0198") {other7 +="<td style='background: #EEE8AA;'>近1月医药消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0201") {other7 +="<td style='background: #EEE8AA;'>近1月医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0202") {other7 +="<td style='background: #EEE8AA;'>近6月医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0203") {other7 +="<td style='background: #EEE8AA;'>近12医药消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0192") {other7 +="<td style='background: #EEE8AA;'>近1月旅行类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0195") {other7 +="<td style='background: #EEE8AA;'>近1月旅行类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0186") {other7 +="<td style='background: #EEE8AA;'>近1月餐饮类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0189") {other7 +="<td style='background: #EEE8AA;'>近1月餐饮类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0180") {other7 +="<td style='background: #EEE8AA;'>近1月娱乐类消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0183") {other7 +="<td style='background: #EEE8AA;'>近1月娱乐类消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0390") {other7 +="<td style='background: #EEE8AA;'>近1月航空消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0393") {other7 +="<td style='background: #EEE8AA;'>近1月航空消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0396") {other7 +="<td style='background: #EEE8AA;'>近1月铁路消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0399") {other7 +="<td style='background: #EEE8AA;'>近1月铁路消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0402") {other7 +="<td style='background: #EEE8AA;'>近1月典当、拍卖、信托消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0405") {other7 +="<td style='background: #EEE8AA;'>近1月典当、拍卖、信托消费笔数</td>";other8 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0408") {other7 +="<td style='background: #EEE8AA;'>近1月证券消费金额</td>";other8 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0411") {other9 +="<td style='background: #EEE8AA;'>近1月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0403") {other9 +="<td style='background: #EEE8AA;'>近6月典当、拍卖、信托消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0404") {other9 +="<td style='background: #EEE8AA;'>近12月典当、拍卖、信托消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0406") {other9 +="<td style='background: #EEE8AA;'>近6月典当、拍卖、信托消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0407") {other9 +="<td style='background: #EEE8AA;'>近12月典当、拍卖、信托消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0409") {other9 +="<td style='background: #EEE8AA;'>近6月证券消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0410") {other9 +="<td style='background: #EEE8AA;'>近12月证券消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0412") {other9 +="<td style='background: #EEE8AA;'>近6月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0413") {other9 +="<td style='background: #EEE8AA;'>近12月证券消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0415") {other9 +="<td style='background: #EEE8AA;'>近6月贵重珠宝、首饰、钟表、银器消费金额额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0416") {other9 +="<td style='background: #EEE8AA;'>近12月贵重珠宝、首饰、钟表、银器消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0418") {other9 +="<td style='background: #EEE8AA;'>近6月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0419") {other9 +="<td style='background: #EEE8AA;'>近12月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0421") {other9 +="<td style='background: #EEE8AA;'>近6月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0422") {other9 +="<td style='background: #EEE8AA;'>近12月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0424") {other9 +="<td style='background: #EEE8AA;'>近6月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0425") {other9 +="<td style='background: #EEE8AA;'>近12月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0414") {other9 +="<td style='background: #EEE8AA;'>近1月贵重珠宝、首饰、钟表、银器消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0417") {other9 +="<td style='background: #EEE8AA;'>近1月贵重珠宝、首饰、钟表、银器消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0420") {other9 +="<td style='background: #EEE8AA;'>近1月古玩、艺术品消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0423") {other9 +="<td style='background: #EEE8AA;'>近1月古玩、艺术品消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0427") {other9 +="<td style='background: #EEE8AA;'>近6月博彩消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0428") {other9 +="<td style='background: #EEE8AA;'>近12月博彩消费金额</td>";other10 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0430") {other9 +="<td style='background: #EEE8AA;'>近6月博彩消费笔数</td>";other10 +="<td>"+value2+"</td>";	}
//											
//											
//											else if (key2 == "S0431") {other11 +="<td style='background: #EEE8AA;'>近12月博彩消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0426") {other11 +="<td style='background: #EEE8AA;'>近1月博彩消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0429") {other11 +="<td style='background: #EEE8AA;'>近1月博彩消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0216") {other11 +="<td style='background: #EEE8AA;'>近1月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0217") {other11 +="<td style='background: #EEE8AA;'>近6月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0218") {other11 +="<td style='background: #EEE8AA;'>近12月加油站消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0219") {other11 +="<td style='background: #EEE8AA;'>近1月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0220") {other11 +="<td style='background: #EEE8AA;'>近6月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0221") {other11 +="<td style='background: #EEE8AA;'>近12月加油站消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0450") {other11 +="<td style='background: #EEE8AA;'>近1月保险支出金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0453") {other11 +="<td style='background: #EEE8AA;'>近1月保险支出笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0246") {other11 +="<td style='background: #EEE8AA;'>近1月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0247") {other11 +="<td style='background: #EEE8AA;'>近6月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0248") {other11 +="<td style='background: #EEE8AA;'>近12月汽车消费金额</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0249") {other11 +="<td style='background: #EEE8AA;'>近1月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0250") {other11 +="<td style='background: #EEE8AA;'>近6月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//											else if (key2 == "S0251") {other11 +="<td style='background: #EEE8AA;'>近12月汽车消费笔数</td>";other12 +="<td>"+value2+"</td>";	}
//
//											
//											
//										})
//									}
//								});
//						}else {
//							
//							$("#result18").append("未找到相关数据");
//						}
//						$("#result18").append("<br>"+other1+other2+"</table>"+"<br>"+other3+other4+"</table>"+"<br>"+other5+other6+"</table>"+"<br>"+other7+other8+"</table>"+"<br>"+other9+other10+"</table>"+"<br>"+other11+other12+"</table>");
//					}
//					if (result.data.statCode == "8001") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8002") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					if (result.data.statCode == "8888") {
//						
//						$("#result18").append(result.data.statMsg);
//					}
//					
//				}else {
//					
//					$("#result18").append("未找到相关数据");
//				}
//				document.getElementById('background').style.display='none';
//			},
//			error:function(){
//				console.log("ajax发送请求失败！");
//				document.getElementById('background').style.display='none';
//			}
//		});
//	}
//});
//	
//function blank() {
//	hide();
//	$("#nav_result").hide();
//	$("#bank_result").show();
//	$("#basicresult18").empty();
//	$("#basicresult18").show();
//	$("#expenseresult18").empty();
//	$("#detailedresult18").empty();
//	$("#result18").empty();
//	$("#kellresult18").empty();
//}	
//function hide() {
//	$("#basicresult18").hide();
//	$("#expenseresult18").hide();
//	$("#detailedresult18").hide();
//	$("#result18").hide();
//	$("#kellresult18").hide();
//	$("#nav_result").hide();
//}
//function colorstyle() {
//	$("#basic").attr("style", "");
//	$("#expense").attr("style", "");
//	$("#detailed").attr("style", "");
//	$("#other").attr("style", "");
//	$("#kell").attr("style", "");
//}
//$("#basic").click(function(){
//	hide();
//	colorstyle();
//	$("#basicresult18").show();
//	$(this).attr("style","color:red");
//});
//$("#expense").click(function(){
//	hide();
//	colorstyle();
//	$("#expenseresult18").show();
//	$(this).attr("style", "color:red;");
//});
//$("#detailed").click(function(){
//	hide();
//	colorstyle();
//	$("#detailedresult18").show();
//	$(this).attr("style", "color:red;");
//});
//$("#other").click(function(){
//	hide();
//	colorstyle();
//	$("#result18").show();
//	$(this).attr("style", "color:red;");
//});	
//$("#kell").click(function(){
//	hide();
//	colorstyle();
//	$("#kellresult18").show();
//	$(this).attr("style", "color:red;");
//});	
//

});