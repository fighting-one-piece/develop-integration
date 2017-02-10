var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
var wordID;
var update;
var updateInitialCount;
$(function(){
	//加载分页数据
	pagez(1,10);
	//分页查询
	$("#Accessendpage").click(function(){
		var page = $(this).val();
		pagez(page,10);
	})
	$("#Accesshomepage").click(function(){
		var page = $(this).val();
		pagez(page,10);
	})
	$("#Accesslastpage").click(function(){
		var nowPage = $(this).val();
		var homePage = $("#Accesshomepage").val();
		if(nowPage == homePage){
			return;
		}
		var page = parseInt(nowPage) - parseInt(1);
		pagez(page,10);
	})
	$("#Accesscenextpage").click(function(){
		var nowPage = $(this).val();
		var endPage = $("#Accessendpage").val();
		if (nowPage >= endPage){
			return;
		}
		var page = parseInt(nowPage) + parseInt(1);
		pagez(page,10);
	})
	
		//修改
		$("#main_sernsitive").on("click",".sensitive_updata",function(){
			var ID=$(this).parent().parent().find("td").eq(0).html();
			updateInitialCount=$(this).parent().parent().find("td").eq(1).html();
			console.log(updateInitialCount);
			console.log(ID);
			//发送请求
			$.ajax({
				type:"get",
				url:projectName+"/findid"+ID,
				dataType:"json",
				success:function(result){
					console.log(result.data);
					$.each(result.data,function(w,y){
						document.getElementById("r_ID").value = result.data.id;
						document.getElementById("r_Sensitive").value = result.data.word;
					})
				}
					
			})
			updata();
			//关闭
			$("#Colsebtn").click(function(){
				document.getElementById('updata').style.display='none';
				document.getElementById('addLinkAddressFade').style.display='none';
			})
		})
		//接收修改请求
			$("#Suerbtn").click(function(){
				var updateid =document.getElementById("r_ID").value;
				var updateCount=document.getElementById("r_Sensitive").value;
				alert(updateCount);
				alert(updateInitialCount);
				$.ajax({
					type:"get",
					url:projectName+"/updataID/"+updateid+"/"+updateCount+"/"+updateInitialCount,    
					dataType:"json",
					success:function(result){
						console.log(result);
						if(result.code==2){
							swal("ERROR!", "2次修改不能相同！", "error"); 
						}
						if(result.code==1){
							swal("GOOD!", "修改成功！", "success");
							pagez(1,10);
						}
					}
				})
				//关闭刷新数据
				document.getElementById('updata').style.display='none';
				document.getElementById('addLinkAddressFade').style.display='none';
			})
			//新增事件
			$("#main_sernsitive").on("click",".sensitive_add",function(){
				//获得ID
				wordID=$(this).parent().parent().find("td").eq(0).html();
				console.log(wordID);
				addfind();
			//关闭
			$("#add_Colsebtn").click(function(){
				document.getElementById('adDate').style.display='none';
				document.getElementById('addLinkAddressFade').style.display='none';
			})
		})
		//新增
		$("#add_Suerbtn").click(function(){
			var word=$("#add_Sensitive").val();
			console.log(word);
			$.ajax({
				type:"get",
				url:projectName+"/AddSensitive",
				data:{"word":word},
				dataType:"json",
				success:function(result){
					 console.log(result)
					 if(result.code==2){
						 swal("ERROR!", "存在相同敏感词！", "error");
					 }
					 if(result.code==1){
						 swal("GOOD!", "新增成功！", "success");
						 pagez(1,10);
					 }
				}
			})
			//关闭刷新数据
			document.getElementById('adDate').style.display='none';
			document.getElementById('addLinkAddressFade').style.display='none';
		})
		//删除事件
		$("#main_sernsitive").on("click",".sensitive_delete",function(){
			 update=$(this).parent().parent().find("td").eq(0).html();
			 //接收的值
			 var DeleteCount=$(this).parent().parent().find("td").eq(1).html();
			 console.log(DeleteCount);
			 var conn;
			 conn=confirm("你确定删除吗？这是不可逆转的操作");
			 if(conn==true){
				 $.ajax({
					 dataType:"json",
					 type:"get",
					 url:projectName+"/Sendelete/"+update+"/"+DeleteCount,   ///Sendelete/{deleteId}/{deleteCount}
					 success:function(result){
						 console.log(result);
						 if(result.code==2){
							 swal("ERROR!", "删除失败！", "error"); 
						 }
						 if(result.code==1){
							 swal("GOOD!", "删除成功！", "success"); 
							 pagez(1,10);
						 }
					 }
				 })
			 }
		})
		
		
}) 

//修改框隐藏
function updata(){
	document.getElementById('updata').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}

//增加框
function addfind(){
	document.getElementById('adDate').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}
//分页方法
function pagez(page,pageSize){
	$.ajax({
		dataType:"json",
		type:"get",
		url:projectName+"/SensitiveAll",
		data:{"page":page,"pageSize":pageSize},
		success:function(result){
			$(".sernsitive").empty();
			console.log(result);
			var key="<table class='table table-striped table-bordered' style='text-align: center;'><tr>";
			var value="</tr>";
			key+="<td width='400px'>"+"序列"+"</td>"+
			"<td width='400px'>"+"敏感词"+"</td>"+
			"<td width='400px' colspan='4'>"+"权限"+"</td>"
			$.each(result.data.data,function(index,counts){
				value= value+ "<td>"+counts.id+"</td>"+"<td>"+counts.word+"</td>"+
				"<td width='200px'>"+"<input type='button' value='修改' class='btn btn-sm btn-info sensitive_updata' id='butns'/>"+"</td>"+
				"<td width='200px'>"+"<input type='button' value='增加' class='btn btn-sm btn-info sensitive_add' id='butns'>"+"</td>"+
				"<td width='200px'>"+"<input type='button' value='删除' class='btn btn-sm btn-info sensitive_delete' id='butns'>"+"</td></tr>";
				
			})
			$("#Accessendpage").val(result.data.pageCount);
			$("#Accesslastpage").val(result.data.pageNum);
			$("#Accesscenextpage").val(result.data.pageNum);
			key= key + "</tr>";
			value= value + "</tr></table>"
			$(".sernsitive").append(key+value);
		}
	})
	
}


