
function checkContactNumbersa() { 
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
	$(function(){
		$("#submitss").click(function(){
			if (checkContactNumbersa()==true) {
				$("#submitss").attr("disabled", true); 
				$("#submitss").attr("style", "color:gray;"); 
				var mobile = $("#query").val();
				var url =mobile+"/users";  
				document.getElementById('background').style.display='block';
				$.ajax({
					type:"get",
					url:url,
					dataType:"json",
					success:function(result){
						$("#resultss").empty();
						if (result.code == 2) {
							$("#resultss").append(result.failure);
						}
						if(result.code == 1) {
							if (result.data.resultList && result.data.resultList.length > 0) {
							$.each(result.data.resultList,function(n,list){
								var keyStr = "<table class='table table-striped'><tr>";
								var valueStr = "<tr>";
								$.each(list,function(resultKey,resultValue){
									keyStr = keyStr + "<td>"+ resultKey+"</td>";
									valueStr = valueStr + "<td>"+ resultValue+"</td>";
								});
								keyStr= keyStr + "</tr>";
								valueStr= valueStr + "</tr></table><br/>"
								$("#resultss").append(keyStr+valueStr);
							});	
							
							$("#submitss").attr("disabled", false); 
							$("#submitss").attr("style", "color:black;"); 
							} else {
								$("#resultss").append("未找到相关数据")
							}
							
						  } else {
							$("#resultss").append("未找到相关数据")
					
						}
						
						document.getElementById('background').style.display='none';
					},
					error:function(){
						console.log("ajax发送请求失败！");
						document.getElementById('background').style.display='none';
						$("#submitss").attr("disabled", false); 
						$("#submitss").attr("style", "color:black;"); 
					}
				});
			} else {
				
				alert("请重新输出电话号码！");
				checkContactNumbersa()==false;
				
			}
		});
	}); 
	

	//enter 控件
	function EnterPressPhoneuser(){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
		document.getElementById("submitss").focus(); 
		} 
		}; 
	