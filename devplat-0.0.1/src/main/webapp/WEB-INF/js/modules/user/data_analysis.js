
$(document).ready(function() {
	$('input[id=upfile]').change(function() {  
		$('#photoCover').val($(this).val());
	});
	$('#btn').click(function(){
		if(checkData()){	
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
					console.log(result);
					if(result.code>0){
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
						//清楚元素
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
					}
				},
				error : function() {
					console.log("出错");
				}
			});
		}
	 	
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
});
