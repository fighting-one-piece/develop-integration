var images;
$(document).ready(function () {
	//图片上传
	window.onload = function(){ 
		var input = document.getElementById("demo_input"); 
		var result= document.getElementById("result"); 
		var img_area = document.getElementById("img_area"); 
		if ( typeof(FileReader) === 'undefined' ){ 
		result.innerHTML = "抱歉，你的浏览器不支持 FileReader，请使用现代浏览器操作！"; 
		input.setAttribute( 'disabled','disabled' ); 
		} else { 
		input.addEventListener( 'change',readFile,false );} 
		} 
		function readFile(){ 
		var file = this.files[0]; 
		//这里我们判断下类型如果不是图片就返回 去掉就可以上传任意文件 
		if(!/image\/\w+/.test(file.type)){ 
		alert("请确保文件为图像类型"); 
		return false; 
		} 
		var reader = new FileReader(); 
		reader.readAsDataURL(file); 
		reader.onload = function(e){ 
		result.innerHTML = '<img src="'+this.result+'" />';
		images=this.result;
		img_area.innerHTML = '<div class="sitetip">图片img标签展示：</div><img src="'+this.result+'" alt=""/>'; 
		 } 
		} 
	
});