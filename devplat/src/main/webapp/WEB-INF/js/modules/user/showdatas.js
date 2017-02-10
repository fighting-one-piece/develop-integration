$(document).ready(function () {
		$.ajax({
			url:"/devplat/excludeUtils/shows",
			type:"get",
			dataType:"json",
			success:function(result){
				if (result.code == 2) {
					 $("#resultShow").append("请刷新页面");
				}else if (result.code == 1) {
					var table = "";
					table += "<table style='width: 100%;'>";
					table += 	"<tr align='center'>";
					table += 		"<td rowspan='2'>姓名</td>";
					table += 		"<td colspan='2'>昨天数据量</td>";
					table += 		"<td colspan='2'>一周数据量</td>";
					table += 		"<td colspan='2'>一月数据量</td>";
					table += 	"</tr>";
					table += 	"<tr  align='center'>";
					table += 		"<td>数据量</td>";
					table += 		"<td>文件数</td>";
					table += 		"<td>数据量</td>";
					table += 		"<td>文件数</td>";
					table += 		"<td>数据量</td>";
					table += 		"<td>文件数</td>";
					table += 	"</tr>";
					var item = result.data;
					console.log(item);
					if (item.length != 42) {
						for (var i = 0; i < item.length/2; i++) {
							table += "<tr  align='center'>";
							table += 	"<td>" +item[i].name+"</td>";
							table += 	"<td style='color: blue;'>"+ "" +"</td>";
							table += 	"<td style='color: blue;'>" + "" + "</td>";
							
							table += 	"<td style='color: red;'>" +item[i].dataNum+"</td>";
							table +=	 "<td style='color: red;'>" +item[i].docNum+"</td>";
							
							table += 	"<td style='color: green;'>" +item[item.length/2  + i].dataNum+"</td>";
							table += 	"<td style='color: green;'>" +item[item.length/2  + i].docNum+"</td>";
							table +="</tr>";
						}
					}else {
						for (var i = 0; i < item.length/3; i++) {
							table += "<tr  align='center'>";
							table += 	"<td>" +item[i].name+"</td>";
							table += 	"<td style='color: blue;'>" +item[i].dataNum+"</td>";
							table += 	"<td style='color: blue;'>" +item[i].docNum+"</td>";
							
							table += 	"<td style='color: red;'>" +item[item.length/3 + i].dataNum+"</td>";
							table +=	 "<td style='color: red;'>" +item[item.length/3 + i].docNum+"</td>";
							
							table += 	"<td style='color: green;'>" +item[item.length/3 * 2 + i].dataNum+"</td>";
							table += 	"<td style='color: green;'>" +item[item.length/3 * 2 + i].docNum+"</td>";
							table +="</tr>";
						}
					}
					table += "</table>";
					$("#resultShow").append(table);
				}
			},
			error:function(){
				$("#resultShow").append("请刷新页面");
			}
		});
	});
	
	
	
	
	