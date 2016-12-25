<html>
<script type="text/javascript">
function ajax(url, params, fnSucc, fnFaild){
    var oAjax = null;
    if(window.XMLHttpRequest){
        oAjax = new XMLHttpRequest();
    }else{
        oAjax = new ActiveXObject("Microsoft.XMLHTTP");
    }
      
    oAjax.open('POST', url, true);   
    oAjax.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');  
    oAjax.send(params);
      
    oAjax.onreadystatechange = function(){  
        if(oAjax.readyState == 4){
            if(oAjax.status == 200){
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
	var submit = document.getElementById('submit');
	var results = document.getElementById('results');
	submit.onclick = function(){
		submit.disabled = true;
		var name = document.getElementById('name').value;
		var dataNum = document.getElementById('dataNum').value;
		var docNum = document.getElementById('docNum').value;
		var params = "name="+name+"&dataNum="+dataNum+"&docNum="+docNum;
		ajax('/devplat/excludeUtils/insert', params, function(data){
        	var dataObj=eval("("+data+")");
        	var items = dataObj.data;
        	if (dataObj.code == 1){
	        	var table = "<table>";
	        	for (var i=0;i<items.length;i++){
	        		table += "<tr>"
	        		table += "<td>" + items[i].name + "</td>";
	        		table += "<td>" + items[i].dataNum + "</td>";
	        		table += "<td>" + items[i].docNum + "</td>";
	        		table += "<td>" + items[i].insertDate + "</td>";
	        		table += "</tr>"
	       		}
	        	table += "</table>";
	        	results.innerHTML = table
	        	submit.disabled = false;
        	} else if (dataObj.code == 2) {
        		alert(dataObj.failure)
        		results.innerHTML = dataObj.failure
        		submit.disabled = false;
        	}
		})
	}
}

</script>
<body>
<h2>Develop Platform <a href="/devplat/db/tables/statistics">Return</a></h2>
<form action="/devplat/excludeUtils/" method="post">
	<table>
		<tr>
			<td>Name:</td>
			<td><input id="name" name="name" type="text" /></td>
		</tr>
		<tr>
			<td>DataNum:</td>
			<td><input id="dataNum" name="dataNum" type="text" /></td>
		</tr>
		<tr>
			<td>DocNum:</td>
			<td><input id="docNum" name="docNum" type="text" /></td>
		</tr>
		<tr>
			<td><input id="submit" type="button" value="Submit"/></td>
			<td></td>
		</tr>
	</table>
	<div id = "results">
	
	</div>
</form>
</body>
</html>
