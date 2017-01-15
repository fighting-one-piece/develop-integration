$(document).ready(function () {
	//2、F姓名-身份证号-银行卡号一致性校验
	$("#SubmitAccountVerifyF").click(function(){
		var name = $("#name").val();
		var bankCard=$("#bankCard").val();
		var idCard=$("#idCard").val();
		$("#resultAccountVerifyF").empty();
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
			},
			error:function(){
			}
		});
	});	
	
	//46、反欺诈黑名单验证
	$("#Submitaudit").click(function(){
		var phone = $("#phone").val();
		$("#resultaudit").empty();
		$.ajax({
			url:"audit/phone",
			type:"post",
			dataType:"json",
			data:{"phone":phone},
			success:function(result){
				console.log(result);
				$("#resultaudit").append("audit");
			},
			error:function(){
			}
		});
	});	
});