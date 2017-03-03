$(document).ready(function(){
	var scrollId = null;
	$("#submitCard").click(function(){
		var idCard = $("#query").val();
		$("#submit").attr("disabled", true); 
		$("#submit").attr("style", "color:gray;"); 
		search();
		
	});
	function search() {
		var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		var idCard = $("#query").val();
		var name = $("#queryname").val();
		if(reg.test(idCard)){
			document.getElementById('background').style.display='block';
			$.ajax({
				url:"educations",
				type:"get",
				dataType:"json",
				data:{"name":name,"idCard":idCard},
				success:function(result){
					$("#results").empty();
					if(result.data){
						var html="";
						if (result.data.length > 0){
							for (var i = 0; i < result.data.length; i++) {
								var table="<table class='table table-striped table-bordered'>";
								var keytb="<tr>";
								var valuetb="<tr>";
								for ( var key in result.data[i]) {
									keytb+="<td style='background: #EEE8AA;'>"+key+"</td>";
									valuetb+="<td>"+result.data[i][key]+"</td>";
								}
								keytb+="</tr>";
								valuetb+="</tr>";
								html+=table+keytb+valuetb+"</table>";
							}
							$("#results").html(html);
						} else {
							$("#results").append("未找到相关数据")
						}
					}else{
						$("#results").append("未找到相关数据")
					}
					$("#submit").attr("disabled", false); 
					$("#submit").attr("style", "color:black;"); 
					document.getElementById('background').style.display='none';
				},
				error:function(){
					console.log("ajax发送请求失败！");
					document.getElementById('background').style.display='none';
				}
			});
		}else{
			swal("错误！","请输入有效身份证号码","error");
		}
	}
	
	//数组去重
	Array.prototype.unique = function(){
		var res = [];
		var json = {};
		for(var i = 0; i < this.length; i++){
			if(!json[this[i]]){
				res.push(this[i]);
				json[this[i]] = 1;
			}
		}
		return res;
	}
	
});


//enter 控件
function EnterPressCard(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitCard").click(); 
	} 
	}; 
