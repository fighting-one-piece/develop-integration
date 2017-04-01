var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function(){
	$("#verificationBtn").click(function(){
		$.ajax({
			url:"http://192.168.0.198:8080/devplat/verificationCode.jpg",
			type:"GET",
			success:function(result){
				console.log(result)
			}
		})
	})
	$("#loginBtn").click(function(){
		$.ajax({
			url:"http://192.168.0.198:8080/devplat/api/v1/login",
			type:"POST",
			dataType:"json",
			data:{account:"test",password:"@#test456"},
			success:function(result){
				console.log(result)
			}
		})
	})
	$("#loginJsonBtn").click(function(){
		var user = {"account":"test","password":"@#test456"} 
		$.ajax({
			url: "http://192.168.0.198:8080/devplat/api/v1/login",
			type: "POST",
			headers: {
				contentType: "application/json"
			},
			dataType: "json",
			data: JSON.stringify(user),
			success:function(result){
				console.log(result)
			}
		})
	})
	$("#indicesBtn").click(function(){
		$.ajax({
			url:"http://192.168.0.198:8080/devplat/api/v1/metadatas/indices",
			type:"get",
			dataType:"json",
			headers: {
		        accessToken: "b833556ed45081e1161df56648e6bc79"
		    },
			success:function(result){
				console.log(result)
			}
		})
	})
	document.cookie="account=test;nickname=测试用户;domain=cisiondata.com;";
	$("#cookieBtn").click(function(){
		$.ajax({
			url:"http://192.168.0.198:8080/devplat/api/v1/metadatas/indices",
			type:"get",
			dataType:"json",
			headers: {
		        accessToken: "b833556ed45081e1161df56648e6bc79",
		        Cookie: document.cookie
		    },
		    crossDomain: true,
			success:function(result){
				console.log(result)
			}
		})
	})
		
});

