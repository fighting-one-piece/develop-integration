var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(document).ready(function() {
	//加载显示
	load(1);
	//首页
	$("#homepage").click(function(){
		var index = 1;
		$("#homepage").val(index);
		load(index);
	});
	//上一页
	$("#lastpage").click(function(){
		var index = parseInt($("#homepage").val())-1;
		if(index <= 1){
			index =1;
		}
		$("#homepage").val(index);
		load(index);
	});
	//下一页
	$("#nextpage").click(function(){
		var index = parseInt($("#homepage").val())+1;
		if(index >= $("#endpage").val()){
			index = $("#endpage").val();
		}
		$("#homepage").val(index);
		load(index);
	});
	//末页
	$("#endpage").click(function(){
		var index = $("#endpage").val();
		$("#homepage").val(index);
		load(index);
	});
	//删除事件
	$(".Tbody button").live("click",function(){
		var id = $(this).parent().parent().attr("id")
		var name = $(this).val();
		swal({
			title:"",  
			text:"确定删除吗？",  
			type:"warning",  
			showCancelButton:"true",  
			showConfirmButton:"true",
			confirmButtonColor: "#DD6B55",
			confirmButtonText:"确定",  
			cancelButtonText:"取消",  
			animation:"slide-from-top"  
		}).then(function(isConfirm){
			if(isConfirm == true){
				$.ajax({
					url:"data_analy/delEvent",
					type:"get",
					dataType:"json",
					data:{"name":name},
					success:function(result){
						if(result.data == 1){
							swal("操作成功!", "已成功删除数据！", "success"); 
							$("tr[id="+id+"]").remove();
						}else{
							swal("OMG", "删除操作失败了!", "error");
						}
					},
					error:function(){
						swal("OMG", "删除操作失败了!", "error");
					}
				})
			}
		})
	});
	//点击事件
	$(".Tbody a").live("click",function(){
		var name = $(this).parent().text();
		show();
		$("#expAnalyze").val(name);
		$("#expExcel").val(name);
		showLoad(1);
		//首页
		$("#home").click(function(){
			var index = 1;
			$("#home").val(index);
			showLoad(index);
		});
		//上一页
		$("#last").click(function(){
			var index = parseInt($("#home").val())-1;
			if(index <= 1){
				index =1;
			}
			$("#home").val(index);
			showLoad(index);
		});
		//下一页
		$("#next").click(function(){
			var index = parseInt($("#home").val())+1;
			if(index >= $("#end").val()){
				index = $("#end").val();
			}
			$("#home").val(index);
			showLoad(index);
		});
		//末页
		$("#end").click(function(){
			var index = $("#end").val();
			$("#home").val(index);
			showLoad(index);
		});
	});
	//点击再次分析
	$("#expAnalyze").click(function(){
		alert($(this).val());
	});
	//点击导出
	$("#expExcel").click(function(){
		var filename = $(this).val();
		var url = "data_analy/exportexcel?fileName="+filename;
		window.location.href=url;
	});
	//关闭事件
	$("#Colsebutton").click(function(){
		hidden();
	});
	$('input[id=upfile]').change(function() {  
		 $('#photoCover').val($(this).val());
	});
	//上传事件
	$('#btn').click(function(){
		if(checkData()){
			$("#resultShow").empty();
			$.ajax({
				url : "data_analy/uploadexcel",
				type : "post",
				data : new FormData($('#form1')[0]),
				dataType:"json",
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(result) {
					var listBiao = result.data.listBiao;
					var listMap = result.data.listMap;
					if(result.code>0){
						checkHidden();
						//清除元素
						$(".ExcelThead").empty();
						$(".ExcelTbody").empty();
						swal({
			    			title:"",
			    			text:"该文件名已存在",
			    			type:"warning",  
			    			showConfirmButton:"true",
			    			confirmButtonColor: "#ec6c62",
			    			confirmButtonText:"确定",    
			    			animation:"slide-from-top"
			    		});
					}
					if(result.code<=0){
						if(result.data.resultCode == "success"){
							checkHidden();
							//清除元素
							$(".ExcelThead").empty();
							$(".ExcelTbody").empty();
							//拼接表头
							var ExcelThead ="<tr>";
							for (var i = 0; i < listBiao.length; i++) {
								ExcelThead+= "<td>"+listBiao[i]+"</td>";
							}
							$(".ExcelThead").html(ExcelThead+="</tr>");
							//拼接内容
							var ExcelTbody = "<tr>";
							for (var j = 1; j < listMap.length; j++) {
								for (var k = 0; k < listBiao.length; k++) {
									ExcelTbody+= "<td>"+listMap[j][listBiao[k]]+"</td>";
								}
								ExcelTbody+="</tr>";
							}
							$(".ExcelTbody").html(ExcelTbody);
						}else{
							//清除元素
							$(".ExcelThead").empty();
							$(".ExcelTbody").empty();
							checkShow();
						}
					}
				},
				error : function() {
					console.log("出错");
				}
			});
		}
	 	
	});
	//导出事件
	$("#btnExport").click(function(){
		var fileDir = $("#photoCover").val();
		var str = fileDir.split("\\");
		var filename = str[str.length-1].substring(0,str[str.length-1].lastIndexOf("."));
		var url = "data_analy/exportexcel?fileName="+filename;
		window.location.href=url;
	});
	$("#btnExportDemo").click(function(){
		var url = "data_analy/exportDemoExcel";
		window.location.href=url;
	});
	//JS校验form表单信息
    function checkData(){
    	var fileDir = $("#photoCover").val();
    	var suffix = fileDir.substr(fileDir.lastIndexOf("."));
    	if("" == fileDir){
    		swal({
    			title:"",
    			text:"请选择文件...",
    			type:"warning",
    			showConfirmButton:"true",
    			confirmButtonColor: "#ec6c62",
    			confirmButtonText:"确定",    
    			animation:"slide-from-top"
    		});
    		return false;
    	}
    	if(".xls" != suffix && ".xlsx" != suffix ){
    		swal({
    			title:"",
    			text:"格式错误！",
    			type:"warning",  
    			showConfirmButton:"true",
    			confirmButtonColor: "#ec6c62",
    			confirmButtonText:"确定",    
    			animation:"slide-from-top"
    		});
    		return false;
    	}
    	return true;
    }
    //弹出
    $("#btnAnalyze").click(function(){  
    	$("#ituation").click(function(){
    		$("#ituationsuer").click(function(){
    			var type= $(' input[name="Fruit"]:checked').val();
        			if(type!=null){
        				$.ajax({
        					type:"post",
        					url:projectName+"/batchquery/"+type, 	
        					dataType:"json",
        					data:new FormData($('#form1')[0]),
        					async : true,
        					beforeSend:function(){
        						ShowDiv();
        					},
        					complete:function(){
        						HiddenDiv();
        					},
        					cache : false,
        					contentType : false,
        					processData : false,
        					success:function(result){
        						$(".col-xs-7").empty();
        						console.log(result.data);
        						var set="<table style='table-layout:fixed' class='table table-striped table-bordered'><tr>";
        						var value="<tr>";
        						set=set+"<td>"+"数据结果展示:"+"</td>"
        						$.each(result.data,function(n,x){
        							value=value+"<td style='white-space:nowrap;overflow:hidden;text-overflow: ellipsis;'>"+x.extendInfo2+"</td><tr>"    						
        						})
        						set=set+"</tr>";
        						$(".col-xs-7").append(set+value);
        					}   			
        				})	    		
        			}else{
        				swal("ERROR!", "操作失败！", "error");
        			}
        			document.getElementById('local_locals').style.display='none';
        			document.getElementById('UserPresentation').style.display='none';
        			document.getElementById('addLinkAddressFade').style.display='none';
        			$(".col-xs-7").empty();
    		})
    		ovaer();
    		//全局选择关闭
    		$("#ituationColsebtn").click(function(){
    			document.getElementById('Ovaerall').style.display='none';
    		})
    	})
    	//定时器
    	self.setInterval(function(){etInter()},1000*60*10);
    	//类型搜索
    	$("#local").click(function(){
    		 //ajax 初始加载 10上面所有的index
    		$.ajax({
    			  type:"get",
    			  url:projectName+"/indices",
    			  dataType:"json",
    			  success:function(result){
    				  console.log(result)
    				  var value;
    				  $.each(result.data,function(n,x){
    					  value=value+"<option value="+n+">"+x+"</option>";
    				  })
    				  value= value+"</option>" 		
    				  $(".index").html(value);
    			  }
    		})
    		typeaver();
    	});
    	 //获取到value 初始值
    	  $("#localindex").change(function(){
    		  //获得index value 值
    		  var checkValue=$("select option:selected").val() ;
    		  console.log(checkValue);
    		  //传回 获得type类型 
    		  $.ajax({
    			  type:"get",
    			  url:projectName+"/batchquery/type/types",
    			  dataType:"json",
    			  data:{"indexs":checkValue},
    			  success:function(result){
    				  console.log(result);
    				  var value;
    				  $.each(result.data,function(n,x){
    					  value=value+"<option value="+n+">"+x+"</option>"
    				  })
    				  value= value+"</option>" 		
    				  $(".type").html(value);
    			  }
    			  
    		  })
    	  })
    	  // index  type 
    	  $("#localSuer").click(function(){
    		  //index
    		  var selectObj = document.getElementById('localindex'); 
    		  var selectvalue = selectObj.value;   
    		  console.log(selectvalue);
    		  //type
    		  var selectype= document.getElementById('localtype');
    		  var typevalue=selectype.value;
    		  console.log(typevalue);
    		  //phone idcard
    		  var Classification=$('#local_locals input[name="Fru"]:checked').val();;
    		  console.log(Classification);
    		  //对参数进行判断
 
    		  if(typevalue==null || typevalue==""){
    			  swal("ERROR!", "操作失败！", "error");
    		  }else if(Classification ==undefined){
    			  swal("ERROR!", "请选择分析类型！", "error");
    		  }else{
    			  $.ajax({
    				type:"post",
  					url:projectName+"/batchquery/"+Classification+"/"+selectvalue+"/"+typevalue, 			
  					dataType:"json",
  					data:new FormData($('#form1')[0]),
  					async : true,
  					beforeSend:function(){
  						ShowDiv();
  					},
  					complete: function () {
  						HiddenDiv();
  					},
  					cache : false,
  					contentType : false,
  					processData : false,
  					success:function(result){						
  							$(".col-xs-7").empty();
  							console.log(result.data);
  							var set="<table style='table-layout:fixed' class='table table-striped table-bordered'><tr>";
  							var value="<tr>";
  							set=set+"<td>"+"数据结果展示:"+"</td>"
  							$.each(result.data,function(y,x){								
  								value=value+"<td style='white-space:nowrap;overflow:hidden;text-overflow: ellipsis;'>"+x.extendInfo2+"</td><tr>"
  							})
  							set=set+"</tr>";
  							$(".col-xs-7").append(set+value);
  						}
    			  })
    		  }
    	  document.getElementById('local_locals').style.display='none';
   		  document.getElementById('UserPresentation').style.display='none';
   		  document.getElementById('addLinkAddressFade').style.display='none';
   		  $(".col-xs-7").empty();
    	  })
    	  
    	  $("#localColsebtn").click(function(){
    		  document.getElementById('local_locals').style.display='none';	
    	  })
    	
    	hides();
    	$("#Colsebtn").click(function(){
    		document.getElementById('UserPresentation').style.display='none';
			document.getElementById('addLinkAddressFade').style.display='none';
			document.getElementById('addLinkAddressFade').style.display='none';
    	})	
    })
});
function load(index){
	var j = 1;
	$.ajax({
		url:"data_analy/seleventBase",
		type:"post",
		dataType:"json",
		data:{"index":index},
		success:function(result){
			var data = eval(result.data.list)
			$("#endpage").val(result.data.pageCount);
			var html="";
			for (var i = 0; i < data.length; i++) {
				html+="<tr id="+j+">"+
						"<td><a>"+data[i].eventName+"</a></td>"+
						"<td><button class='btn btn-warning' value="+data[i].eventName+">删除</button></td>"+
					  "</tr>"
				j++;
			}
			var styleThead = "<tr>"+
								"<td>文件名</td>"+
								"<td>操作</td>"+
							 "</tr>";
			$(".styleThead").html(styleThead);
			$(".Tbody").html(html);
		},
		error:function(){
			console.log("查询错误");
		}
	});
}
function showLoad(index){
	var name = $("#expExcel").val();
	$.ajax({
		url:"data_analy/selExendPage",
		type:"get",
		dataType:"json",
		data:{"name":name,"index":index},
		success:function(result){
			$(".TheadExcel").empty();
			$(".TbodyExcel").empty();
			$(".TheadExcelone").empty();
			$(".TbodyExcelone").empty();
			$("#end").val(result.data.pageCount);
			//拼接原始数据表头
			var thead = "<tr>";
			for (var i = 0; i < result.data.listBase.length; i++) {
				for(var key in result.data.listBase[i]){
					thead+="<td>"+key+"</td>";
				}
				break;
			}
			$(".TheadExcel").html(thead+"</tr>");
			//拼接原始数据内容
			var tbody = "";
			for (var i = 0; i < result.data.listBase.length; i++) {
				tbody+="<tr>";
				for(var key in result.data.listBase[i]){
					tbody+="<td>"+result.data.listBase[i][key]+"</td>";
				}
				tbody+="</tr>";
			}
			$(".TbodyExcel").html(tbody);
			var theadExd = "<tr><td>分析结果</td></tr>";
			$(".TheadExcelone").html(theadExd);
			var tbodyExd = "";
			for (var i = 0; i < result.data.list.length; i++) {
				tbodyExd +="<tr><td class='scs' title='"+result.data.list[i].extendInfo2+"'>"+result.data.list[i].extendInfo2+"</td></tr>";
			}
			$(".TbodyExcelone").html(tbodyExd);
		},
		error:function(){
			console.log("错误");
		}
	});
}
//弹窗的隐藏显示
function hides(){
	document.getElementById('UserPresentation').style.display='block';
	document.getElementById('addLinkAddressFade').style.display='block';
}
//全局弹窗显示
function ovaer(){
	document.getElementById('Ovaerall').style.display='block';
}
//类型搜索弹窗显示
function typeaver(){
	document.getElementById('local_locals').style.display='block';
}

//動態加載隱藏顯示方法
//显示加载数据
function ShowDiv() {
$("#loading").show();

}
//隐藏加载数据
function HiddenDiv() {
$("#loading").hide();

}
//显示分析的数据
function show(){
	document.getElementById('showEvent').style.display='block';
	document.getElementById('fade').style.display='block';
}
function hidden(){
	document.getElementById('showEvent').style.display='none';
	document.getElementById('fade').style.display='none';
}
//提示显示与隐藏
function checkShow(){
	document.getElementById('styleCheck').style.display='inline-block';
	document.getElementById('btnExportDemo').style.display='inline-block';
}
function checkHidden(){
	document.getElementById('styleCheck').style.display='none';
	document.getElementById('btnExportDemo').style.display='none';
}
//定时器防止页面坏死
function etInter(){
	$.ajax({
		  type:"get",
		  url:projectName+"/indices",
		  dataType:"json",
		  success:function(result){
			  console.log(result)
		  }
	})
}

	
	



