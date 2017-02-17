$(document).ready(function(){
	//显示DIV
	function showDIV(){
		document.getElementById('lightone').style.display='block';
		document.getElementById('fadeone').style.display='block';
	}
	
	//热词搜索 分页显示的方法
	function loadPage(index){
		var j = 1;
		$.ajax({
			url:"log/allLog",
			type:"get",
			dataType:"json",
			data:{"index":index},
			success:function(result){
				var array = eval(result.data.data);
				$("#endpage").val(result.data.pageCount);
				var html="";
				for(var i=0;i<array.length;i++){
					html+='<tr id="'+j+'">'+
								'<td><a>'+array[i].keyword+'</a></td>'+
								'<td>'+array[i].count+'</td>'+
//								'<td>'<button id="'+j+'" class="btn btn-warning" value="'+array[i].keyword+'">删除</button></td>'+
							'</tr>'	
					j++;
				}
				var styleThead = "<tr>"+
									"<td>关键字</td>"+
									"<td>统计</td>"+
//									"<td>操作</td>"+
								"</tr>";
				$(".styleThead").html(styleThead);
				$(".Tbody").html(html);
			},
			error:function(){
				console.log("ajax发送请求失败！");
			}
		});
	}
	
	// 顺序搜索 分页
	function orderTime(index) {
		var j = 1;
		$.ajax({
			url:"log/ordertime",
			type:"get",
			dataType:"json",
			data:{"index":index},
			success:function(result){
				var array = eval(result.data.data);
				console.log(array);
				$("#endpage").val(result.data.pageCount);
				var html="";
				var account ="";
				for(var i=0;i<array.length;i++){
					var date = new Date(array[i].accessTime);
					if(array[i].account == null || array[i].account == ""){
						account = "";
					}else{
						account = array[i].account;
					}
					html+='<tr id="'+j+'">'+
								'<td><a>'+array[i].keyword+'</a></td>'+
								'<td>'+FormatDate(date)+'</td>'+
								'<td>'+array[i].ip+'</td>'+
								'<td>'+account+'</td>'+
//								'<td><button id="'+j+'" class="btn btn-warning" value="'+array[i].keyword+'">删除</button></td>'+
							'</tr>'	
					j++;
				}
				var styleThead = "<tr>"+
									"<td>关键字</td>"+
									"<td>时间</td>"+
									"<td>IP</td>"+
									"<td>访问者</td>"+
								"</tr>";
				$(".styleThead").html(styleThead);
				$(".Tbody").html(html);
			},
			error:function(){
				console.log("ajax发送请求失败！");
			}
		});	
	}
	var keyWord;
	//0 加载热词搜索   1 加载顺序搜索
	var check = 0;
	
	//点击热词搜索加载
	$("#hotWords").click(function(){
		$(".Tbody").empty();
		loadPage(1);
		$("#homepage").val(1);
		check = 0;
	});
	
	//点击顺序搜索加载
	$("#orderTime").click(function(){
		$(".Tbody").empty();
		orderTime(1);
		check = 1;
	});
	
	
	
	//进入页面加载热词搜索
	$(function(){
		if (check == 0) {
		//加载显示
			loadPage(1);
		}
		
		
		//首页
		$("#homepage").click(function(){
			var index =1;
			$("#homepage").val(index);
			if (check == 0) {
				loadPage(index);
			}else {
				orderTime(index);
			}
		});
		
		
		
		//上一页
		$("#lastpage").click(function(){
				var index = parseInt($("#homepage").val())-1;
				if(index<=1){
					index=1;
				}
				$("#homepage").val(index);
				if (check == 0) {
					loadPage(index);
				}else {
					orderTime(index);
			}
		});
		
		
		//下一页
		$("#nextpage").click(function(){
				var index = parseInt($("#homepage").val())+1;
				if(index >= $("#endpage").val()){
					index = $("#endpage").val();
				}
				$("#homepage").val(index);
				if (check == 0) {
					loadPage(index);
				}else {
					orderTime(index);
			}
		});
		
		//末页
		$("#endpage").click(function(){
			var index = $("#endpage").val();
			$("#homepage").val(index);
			if (check == 0) {
				loadPage(index);
			}else {
				orderTime(index);
			}
		});
		
		//关闭事件
		$("#btnColse").click(function(){
			document.getElementById('lightone').style.display='none';
			document.getElementById('fadeone').style.display='none';
		});
		
		$("#home").click(function(){
			console.log(keyWord);
			var index =1;
			keypage(index,keyWord);
			$("#home").val(index);
		});
		
		$("#last").click(function(){
			var index = parseInt($("#home").val())-1;
			if(index<=1){
				index=1;
			}
			$("#home").val(index);
			keypage(index,keyWord);
		});
		
		$("#nextone").click(function(){
			var index = parseInt($("#home").val())+1;
			if(index >= $("#end").val()){
				index = $("#end").val();
			}
			$("#home").val(index);
			keypage(index,keyWord);
		});
		
		$("#end").click(function(){
			var index = $("#end").val();
			keypage(index,keyWord);
			$("#home").val(index);
		});
	
	});
	
	//删除事件
//	$(".Tbody button").live("click",function(){
//		//获取tr的id
//		var id = $(this).parent().parent().attr("id")
//		var keyword = $(this).val();
//		swal({
//			title:"",  
//			text:"确定删除吗？",  
//			type:"warning",  
//			showCancelButton:"true",  
//			showConfirmButton:"true",
//			confirmButtonColor: "#DD6B55",
//			confirmButtonText:"确定",  
//			cancelButtonText:"取消",  
//			animation:"slide-from-top"  
//		}).then(function(isConfirm){
//			if(isConfirm == true){
//				
//				$.ajax({
//					url:"log/dellog",
//					type:"get",
//					dataType:"json",
//					data:{"keyword":keyword},
//					success:function(result){
//						if(result == true){
//							swal("操作成功!", "已成功删除数据！", "success"); 
//							$("tr[id="+id+"]").remove();
//						}else{
//							swal("OMG", "删除操作失败了!", "error");
//						}
//					},
//					error:function(){
//						swal("OMG", "删除操作失败了!", "error");
//					}
//				})
//			}
//		})
//	});
	
	//点击关键字
	$(".Tbody a").live("click",function(){
		var keyword = $(this).text();
		keyWord = $(this).text();
		console.log(keyword);
		keypage(1,keyword);
		showDIV();
	});
	//点击关键字事件
	function keypage(index,keyword){
		$.ajax({
			url:"log/allkeyword",
			type:"get",
			dataType:"json",
			data:{"index":index,"keyword":keyword},
			success:function(result){
				var array = eval(result.data.data);
				$("#end").val(result.data.pageCount);
				var html="";
				var account ="";
				for(var i=0;i<array.length;i++){
					var date = new Date(array[i].accessTime);
					if(array[i].account == null || array[i].account == ""){
						account = "";
					}else{
						account = array[i].account;
					}
					html+='<tr>'+
								'<td>'+array[i].keyword+'</td>'+
								'<td>'+array[i].ip+'</td>'+
								'<td>'+FormatDate(date)+'</td>'+
								'<td>'+account+'</td>'+
							'</tr>'	
				}
				$(".styletb").html(html);
			},
			error:function(){
				console.log("ajax发送请求失败！");
			}
		})
	}
});

function FormatDate (strTime) {
	var paddNum = function(num){
        num += "";
        return num.replace(/^(\d)$/,"0$1");
     }
    var date = new Date(strTime);
    return date.getFullYear()+"-"+paddNum(date.getMonth() + 1)+"-"+paddNum(date.getDate())+" "+paddNum(date.getHours())+":"+paddNum(date.getMinutes())+":"+paddNum(date.getSeconds());
}
