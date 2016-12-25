
function checkContactNumber() { 
    var phone = $("#query").val(); 
    var isMobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1})|(14[0-9]{1}))+\d{8})$/;  
    var isPhone = /^(?:(?:0\d{2,3})-)?(?:\d{7,8})(-(?:\d{3,}))?$/;;  
    var error = "<label>请正确填写电话号码，例如:13511111111或010-11111111</label>";  
    //如果为1开头则验证手机号码  
    if (phone.substring(0, 1) == 1) {  
        if (!isMobile.exec(phone) && phone.length != 11) {
        	$("#error").css("display", "block"); 
            $("#error").html(error);  
            return false;  
        }  
    }  
    //如果为0开头则验证固定电话号码  
    else if (mobile.substring(0, 1) == 0) {  
        if (!isPhone.test(mobile)) {  
        	$("#error").css("display", "block"); 
        	$("#error").html(error);    
            return false;  
        }  
    }  
    //否则全部不通过  
    else {  
    	$("#error").css("display", "block"); 
    	$("#error").html(error);
        
        return false;  
    } 
    $("#error").css("display", "none");
    return true;  
}
//添加日志
function addLog(keyword){
	$.ajax({
		url:"log/addlog",
		type:"post",
		dataType:"json",
		data:{"keyword":keyword},
		success:function(){
			
		},
		error:function(){
			console.log(keyword);
		}
	})
}

	$(function(){
		$("#submits").click(function(){
			if (checkContactNumber()==true) {
				$("#submits").attr("disabled", true); 
				$("#submits").attr("style", "color:gray;"); 
				var moblie = $("#query").val();
				var url = "mobile/" + moblie;
				$.ajax({
					type:"get",
					url:url,
					dataType:"json",
					success:function(result){
						$("#results").empty();
						var str = "<table class='table table-striped table-bordered'>";
						$.each(result.data,function(resultKey,resultValue){
							if (result.code == 2) {
								$("#results").append(result.failure);
							} else if(result.code == 1) {
								 str += "<tr><td>"+resultKey+"</td>"+
										"<td>"+resultValue+"</td></tr>";
								
							} else {
								var  name =result.data["name"];
								var msg = result.data["msg"];
								var str1 = "<table><tr><td>"+"姓名"+"</td>"+
								"<td>"+name+"</td></tr>"+
								"<tr><td>"+"归属地"+"</td>"+
								"<td>"+msg+"</td></tr></table>";
								$("#results").append(str1);
								
							}
						});
						$("#results").append(str+"</table>");
						console.log(str);
					addLog(moblie);
					$("#submits").attr("disabled", false); 
					$("#submits").attr("style", "color:black;"); 
					},
					error:function(){
						alert("查询出错");
						$("#submits").attr("disabled", false); 
						$("#submits").attr("style", "color:black;"); 
					}
				});
			} else {			
				alert("请重新输出电话号码！");
				checkContactNumber()==false;
				
			}
		
		});
	}); 
	
	//enter 控件
	function EnterPressMobile(){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
		document.getElementById("submits").click(); 
		} 
		};
	