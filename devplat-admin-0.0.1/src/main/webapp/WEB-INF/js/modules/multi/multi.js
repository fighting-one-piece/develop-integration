var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function(){
		/*
		获取所有类型
		*/
		$.ajax({
			type:"get",
			url:projectName+"/indices",
			dataType:"json",
			success:function(result){
				$.each(result.data,function(indexKey,indexValue){
					var index = indexKey;
					$.ajax({
						type:"get",
						url:projectName+"/"+index+"/types",
						dataType:"json",
						success:function(result){
							$.each(result.data,function(typeKey,typeValue){
								if (typeKey == "properties" || typeKey == "WorkQualification") {
									
								}else{
									var $type = $("<option>"+typeValue+"</option>");
									$type.data("index",indexKey);
									$type.data("type",typeKey);
									$(".chooseClass").append($type);
								}
								
							})
							$(".chooseClass").change();
						}
					})
				})
			}
		});
		
		/*
		获取type下属性列表
		*/
		$(".chooseClass").change(function(){
			var type = $(this).children("option:selected").data("type");
			var index = $(this).children("option:selected").data("index");
			$("#term div:gt(1)").remove();
			var $select = "";
			if(type == "n" || index == "n"){
				$(".attributeClass").eq(0).empty();
				$(".attributeClass").change();
			}else{
				$.ajax({
					type:"get",
					url:projectName+"/"+index+"/"+type+"/attributes",
					dataType:"json",
					success:function(result){
						$.each(result.data,function(key,value){
							$select += "<option value='"+key+"'>"+value+"</option>";
						});
						$(".attributeClass").html($select);
						$("#term").find("input.qual").val("");
					}
				})
			}
		});
		
		$("#term").on("change","select.attributeClass",function(){
			var att1 = $(this).children("option:selected");
			var att = $(this).children("option:selected").val();
			if(att==null){
				//$(this).siblings("input.qual").css("display","none");
				$(this).siblings("input.qual").val("");
			}else{
//				$(this).siblings("input.qual").removeAttr("style");
				$(this).siblings("input.qual").val("");
				if($("#term div").length<=2){
					return;
				}
				//将改变后未选择的option添加到其他select
				var opArr = [];
				if($(this).html()==$("#term div:gt(0)").find("select").eq(0).html()){
					$(this).children("option").each(function(b,bEle){
						opArr.push($(bEle)[0].outerHTML);
						$("#term div:gt(0)").find("select").eq(1).children("option").each(function(c,cEle){
							if($(cEle).html()==$(bEle).html()){
								opArr.splice($.inArray($(cEle)[0].outerHTML,opArr));
							}
						});
						
					});
				}else{
					$(this).children("option").each(function(b,bEle){
						opArr.push($(bEle)[0].outerHTML);
						$("#term div:gt(0)").find("select").eq(0).children("option").each(function(c,cEle){
							if($(cEle).html()==$(bEle).html()){
								opArr.splice($.inArray($(cEle)[0].outerHTML,opArr));
							}
						});
						
					});
					
				}
				$("#term div:gt(0)").children("select").each(function(f,fEle){
					//$(fEle)[0].outerHTML!=$(this)[0].outerHTML
					if($(fEle).children("option:selected").val()!=att){
						$(fEle).append($(opArr[0]));
					}
				});
				$("#term div:gt(0)").children("select").children("option:selected").each(function(i,optionEle){
					if($(optionEle).val()!=att){
						$("#term div:gt(0)").children("select").each(function(n,selectEle){
							if(i==n){
								$(selectEle).children("option").each(function(m,oEle){
									if($(oEle).val()==att){
										$(oEle).remove();
									}
								});
							}
						});
					}
				});
			}
		});
		/*+*/
		$("#term").on("click","button.addDiv",function(){
			if($(this).siblings("select").children("option").length==1){
				return;
			}
			var addDiv = $(this).parent().html();
			addDiv = "<div>" + addDiv + "</div>";
			$("#term").append(addDiv);
			$("#term div:gt(0)").each(function(i,domEle){
				var delVal = $(domEle).children("select").children("option:selected").val();
				var $option = $("#term div:gt(0) select option");
				$("#term div:gt(0)").each(function(n,divEle){
					if(i!=n){
						$(divEle).find("option").each(function(m,opEle){
							if($(opEle).val()==delVal){
								$(opEle).remove();
							}
						});
					}
				});
			});
		});
		/*-*/
		$("#term").on("click","button.deleteDiv",function(){
			if($("#term").children().length==2){
				return;
			}
			var $string = $(this).siblings("select").children("option:selected");
			if($string==undefined)return;
			string = $string[0].outerHTML;
			$("#term div:gt(0)").children("select").append(string);
			$(this).parent().remove();
		});
		
		
		/*查询*/
		$("#submitMulti").click(function(){
			$("#resultsMulti").empty();
			$("#submitMulti").attr("disabled", true); 
			$("#nextMulti").attr("disabled", true);
			$("#submitMulti").attr("style", "color:gray;width: 48px;"); 
			$("#nextMulti").attr("style", "color:gray;width: 48px;");
			$("#nextMulti").hide();
			var index = $(".chooseClass").children("option:selected").data("index");
			var type = $(".chooseClass").children("option:selected").data("type");
			if(index == "n" || type == "n"){
			}else{
				var url = projectName+"/search/multi/scroll?scrollId=&pageSize=10&esindex="+index+"&estype="+type;
				var op = $("#term div:gt(0)").find("option:selected")
				$.each(op,function(i,option){
					var value = $(option).parent().siblings("input").val();
					var key = $(option).val();
					url += "&"+key+"="+value;
				});
				$.ajax({
					type:"get",
					url:url,
					dataType:"json",
					success:function(result){
						showDatas(result);
					},
					error:function(){
						$("#submitMulti").attr("disabled", false);
						$("#submitMulti").attr("style", "color:black;width: 48px;"); 
					}
				
				})
			}
		})
		
		
		//下一页
		$("#nextMulti").click(function(){
			
			$("#submitMulti").attr("disabled", true); 
			$("#nextMulti").attr("disabled", true);
			$("#submitMulti").attr("style", "color:gray;width: 48px;"); 
			$("#nextMulti").attr("style", "color:gray;width: 48px;");
			var index = $(".chooseClass").children("option:selected").data("index");
			var type = $(".chooseClass").children("option:selected").data("type");
			if(index == "n" || type == "n"){
			} else {
				var scrollId = $(this).data("scrollId");
				var url = "../search/multi/scroll?scrollId="+scrollId+"&pageSize=10&esindex="+index+"&estype="+type;
				var op = $("#term div:gt(0)").find("option:selected")
				$.each(op,function(i,option){
					var value = $(option).parent().siblings("input").val();
					var key = $(option).val();
					url += "&"+key+"="+value;
				});
				$.ajax({
					type:"get",
					url:url,
					dataType:"json",
					success:function(result){
						showDatas(result);
					},
					error:function(){
						$("#submitMulti").attr("disabled", false); 
						$("#submitMulti").attr("style", "color:black;width: 48px;"); 
					}
				})
			}
		});
		
		/*显示到返回数据界面*/
		function showDatas(result){
			$("#resultsMulti").empty();
			if(result.data){
				if (result.data.resultList && result.data.resultList.length > 0){
					if (result.data.resultList.length < 10 || result.data.totalRowNum == 10) {
						$("#nextMulti").hide();
					}else {
						$("#nextMulti").attr("disabled", false); 
						$("#nextMulti").attr("style", "color:black;");
					}
					$.each(result.data.resultList,function(n,list){
						var keytr = "<br><table style='width:100%;'><tr><td style='background: #EEE8AA;'>库</td><td style='background: #EEE8AA;'>表</td>";
						var valuetr = "<tr><td style='background: white;'>"+list["index"]+"</td><td style='background: white;'>"+list["type"]+"</td>";
						$.each(list.data, function(resultKey, resultValue){
							keytr += "<td style='background: #EEE8AA;'>"+resultKey+"</td>";
							valuetr += "<td style='background: white;'>"+resultValue+"</td>";
						});
						keytr += "</tr>";
						valuetr += "</tr></table></br>";
						$("#resultsMulti").append(keytr+valuetr);
					});
					$("#resultsMulti").append("<br align='center'>"+"搜索共" + result.data.totalRowNum + "结果</br>")
				} else {
					$("#resultsMulti").append("未找到相关数据")
				}
			} else{
				$("#resultsMulti").append("未找到相关数据")
			}
			$("#nextMulti").data("scrollId",result.data.scrollId);
			if (result.data.scrollId) {
				
			}
			$("#submitMulti").attr("disabled", false); 
			$("#submitMulti").attr("style", "color:black;width: 48px;"); 
		}
		
	});


//enter 控件
function EnterPressMulti(){ //传入 event 
	var e = e || window.event; 
	if(e.keyCode == 13){ 
	document.getElementById("submitMulti").focus(); 
	} 
	}; 
