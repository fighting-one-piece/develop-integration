<html>
<script type="text/javascript">
function ajax(url, fnSucc, fnFaild){
    //1.创建对象
    var oAjax = null;
    if(window.XMLHttpRequest){
        oAjax = new XMLHttpRequest();
    }else{
        oAjax = new ActiveXObject("Microsoft.XMLHTTP");
    }
      
    //2.连接服务器  
    oAjax.open('GET', url, true);   //open(方法, url, 是否异步)
      
    //3.发送请求  
    oAjax.send();
      
    //4.接收返回
    oAjax.onreadystatechange = function(){  //OnReadyStateChange事件
        if(oAjax.readyState == 4){  //4为完成
            if(oAjax.status == 200){    //200为成功
                fnSucc(oAjax.responseText) 
            }else{
                if(fnFaild){
                    fnFaild();
                }
            }
        }
    };
}
window.onload = function(){
	var dbHost = document.getElementById('dbHost');
	var dbName = document.getElementById('dbName');
    var btn = document.getElementById('btn');
    var results = document.getElementById('results');
    btn.onclick = function(){
    	alert(1);
    	btn.disabled = true;
        ajax('/devplat/host/' + dbHost.value +'/db/' + dbName.value + '/tables/statistics', function(data){
        	var dataObj=eval("("+data+")");
        	if (dataObj.code == 1) {
	        	var list = dataObj.data;
	        	var table = "<table>";
	        	for (var i = 0; i< list.length; i++){ 
	        		value = list[i] 
	        		for (var key in value) {
		        		table += "<tr><td>" + key + "</td><td>" + value[key] + "</td></tr>";
	        		}
	        	} 
	        	table += "</table>";
	        	results.innerHTML = table
        	} else if (dataObj.code == 2) {
        		results.innerHTML = dataObj.failure
        	}
        	btn.disabled = false;
        });
    }
}

</script>
<script type="text/javascript" src="js/jquery-1.8.0.js">
	
</script>
<body>
<h2>Develop Platform <a href="/devplat/dr/insert">Data Record</a></h2>
<form action="/db/">
	<table>
		<tr>
			<td>DatabaseHost:</td>
			<td><input id="dbHost" type="text" /></td>
			<td>DatabaseName:</td>
			<td><input id="dbName" type="text" /></td>
			<td><input id="btn" type="button" value="Submit"/></td>
		</tr>
	</table>
	<div id = "results">
	
	</div>
</form>
</body>
</html>
