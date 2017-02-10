var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(document).ready(function() {

	$("#username").focus();
	
	$(".jcaptcha-btn").click(function() {
        var img = $(".jcaptcha-img");
        var imageSrc = img.attr("src");
        if(imageSrc.indexOf("?") > 0) {
            imageSrc = imageSrc.substr(0, imageSrc.indexOf("?"));
        }
        imageSrc = imageSrc + "?" + new Date().getTime();
        img.attr("src", imageSrc);
    });
	
    $.validationEngineLanguage.allRules.ajaxJcaptchaCall={
        "url": projectName+"/jcaptcha-validate",
        "alertTextLoad": "* 正在验证，请稍等。。。"
    };
    
    $("#loginForm").validationEngine({scroll:false});
	
});


//enter 控件
function EnterPressLogin(){ 
	//传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
		document.getElementById("submitLogin").click(); 
	} 
}; 
