$(document).ready(function () {
	$("#confirmbtn").click(function() {
		var account = $(".username").html().trim();
		var originalpassword = $("#originalpassword").val().trim();
		var newpassword = $("#setnewpassword").val().trim();
		var confirmnewpassword = $("#confirmnewpassword").val().trim();
		if(originalpassword == null || originalpassword == ""){
			 $("#waring").empty();
			 $("#waring").append("请输入原密码！");
		}else if(newpassword == null || newpassword == "" ){
			 $("#waring").empty();
			 $("#waring").append("请输入新密码！");
		}else if(newpassword.match(new RegExp("^[a-zA-Z][a-zA-Z0-9_]{5，15}")) || newpassword.length < 6 || newpassword.length > 16){
			 $("#waring").empty();
			 $("#waring").append("密码为6至16位的字母及数字组成！");
		}else if (confirmnewpassword == null || confirmnewpassword == "" ) {
			 $("#waring").empty();
			 $("#waring").append("请输入确认密码！");
		}else if (newpassword != confirmnewpassword) {
			 $("#waring").empty();
			 $("#waring").append("两次输入的密码不相同！");
		}else {
			$("#waring").empty();
			 $("#waring").append("正在全速处理中····");
			 $.ajax({
				url:"password",
				type:"post",
				data:{"account":account,"originalpassword":originalpassword,"newpassword":newpassword},
				dataType:"json",
				success:function(result){
					if (result.code == 1) {
						$("#waring").empty();
						$("#waring").append(result.data);
						if (result.data == "密码已修改！") {
							$("#btnlogout").click();
						}
					}else {
						$("#waring").empty();
						$("#waring").append("请重试！");
					}
				}
			 });
		}
	});
	$("#cancelbtn").click(function() {
		window.location.href = history.back();
	});
});

