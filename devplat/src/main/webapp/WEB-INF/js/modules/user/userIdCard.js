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
		console.log(name == "");
		if(reg.test(idCard)){
			if(name == "" || name == null){
				document.getElementById('background').style.display='block';
				$.ajax({
					url:"card/search",
					type:"get",
					dataType:"json",
					data:{"idCard":idCard},
					success:function(result){
						console.log(result);
						$("#results").empty();
						if(result.data){
							if (result.data.length > 0){
								var studentArr = new Array();
								var resumeArr = new Array();
								var studentKeyArr = new Array();
								var resumeKeyArr = new Array();
								$.each(result.data,function(index,item){
									if(item.type == 'student'){
										studentArr.push(item);
										$.each(item,function(key,value){
											if(key != 'type')studentKeyArr.push(key);
										})
									} else if (item.type == 'resume') {
										resumeArr.push(item);
										$.each(item,function(key,value){
											if(key != 'type')resumeKeyArr.push(key);
										})
									}
								})
								studentKeyArr = studentKeyArr.unique();
								resumeKeyArr = resumeKeyArr.unique();
								
								var stutable = "";
								if (studentArr.length !=0){
									stutable += "<br><table class='table table-striped table-bordered'><tr>";
									for (var i = 0 ; i<studentKeyArr.length; i++){
										stutable += "<td>"+studentKeyArr[i]+"</td>";
									}
									stutable += "</tr>";
									for (var i = 0 ; i<studentArr.length ; i++){
										stutable += "<tr>";
										for (var j = 0; j<studentKeyArr.length ; j++){
											if(studentArr[i][studentKeyArr[j]]){
												stutable += "<td>"+studentArr[i][studentKeyArr[j]]+"</td>"
											} else {
												stutable += "<td></td>"
											}
										}
										stutable += "</tr>";
									}
									stutable += "</table>"
								}
								var resutable = "";
								if(resumeArr.length != 0){
									resutable += "<br><table class='table table-striped table-bordered'><tr>";
									for (var i = 0 ; i<resumeKeyArr.length; i++){
										resutable += "<td>"+resumeKeyArr[i]+"</td>";
									}
									resutable += "</tr>";
									for (var i = 0 ; i<resumeArr.length ; i++){
										resutable += "<tr>";
										for (var j = 0; j<resumeKeyArr.length ; j++){
											if(resumeArr[i][resumeKeyArr[j]]){
												resutable += "<td>"+resumeArr[i][resumeKeyArr[j]]+"</td>"
											} else {
												resutable += "<td></td>"
											}
										}
										resutable += "</tr>";
									}
									resutable += "</table>"
								}
								$("#results").append(stutable+resutable);
//							var keytr;
//							var valuetr;
//							$.each(result.data,function(n,list){
//								keytr = "<br><table class='table table-striped table-bordered'><tr>";
//								valuetr = "<tr>";
//								$.each(list, function(resultKey, resultValue){
//									keytr += "<td>"+resultKey+"</td>";
//									valuetr += "<td>"+resultValue+"</td>";
//								});
//								keytr += "</tr>";
//								valuetr += "</tr></table>";
//								$("#results").append(keytr+valuetr);
//							});
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
				document.getElementById('background').style.display='block';
				$.ajax({
					url:"education/organizeD",
					type:"post",
					dataType:"json",
					data:{"name":name,"idCard":idCard,},
					success:function(result){
						$("#results").empty();
						var tab;
						if (result.code == 1) {
							tab = "<table style='width:100%;margin: 5px;'><tr>";
							$.each(result.data.checkResult.college,function(key,value){
								tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
							});
							tab +=	"</tr><tr>";
							$.each(result.data.checkResult.college,function(key,value){
								tab += "<td>"+value+"</td>";
							});
							tab +=	"<table style='width:100%;margin: 5px;margin-top:15px'><tr>";
							$.each(result.data.checkResult.degree,function(key,value){
								tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
							});
							tab +=	"</tr><tr>";
							$.each(result.data.checkResult.degree,function(key,value){
								tab += "<td>"+value+"</td>";
							});
							tab +=	"<table style='width:100%;margin: 5px;margin-top:15px'><tr>";
							$.each(result.data.checkResult.personBase,function(key,value){
								tab += "<td style='background: #EEE8AA;'>"+key+"</td>";
							});
							tab +=	"</tr><tr>";
							$.each(result.data.checkResult.personBase,function(key,value){
								tab += "<td>"+value+"</td>";
							});
							tab +=	"</tr></table>";
						}else {
							tab = "未找到相关信息";
						}
						$("#results").html(tab);
						document.getElementById('background').style.display='none';
					},
					error:function(){
						console.log("ajax发送请求失败！");
						document.getElementById('background').style.display='none';
					}
				});
			}
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
