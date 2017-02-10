	//省市下拉选
	var provinceCityDivHtml = '<select class="form-control input-sm" style="max-width: 25%;display: inline;" id="selectprovince">'+
	'</select>'+
	'<select class="form-control input-sm" style="max-width: 25%;display: inline;" id="selectcity">'+
	'</select>'
	$("#provinceCityDiv").html(provinceCityDivHtml);
	//获取省
	function loadProvince(){
		$.ajax({
			type:"get",
			url:projectName+"/provinceCity/province",
			dataType:"json",
			success:function(result){
				if (result.code == 1){
					var str = '<option value="" selected="selected">请选择省</option>'; 
					$.each(result.data,function(index,value){
						str += '<option value="'+value+'">'+value+'</option>';
					})
					$("#selectprovince").append(str)
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error");
				}
			}
		})
	}
	//根据省获得市
	function loadCity(province){
		$.ajax({
			type:"get",
			url:projectName+"/provinceCity/city",
			data:{"province":province},
			dataType:"json",
			success:function(result){
				if (result.code == 1){
					var str = '<option value="" selected="selected">请选择市</option>'; 
					$.each(result.data,function(index,value){
						str += '<option value="'+value+'">'+value+'</option>';
					})
					$("#selectcity").html(str)
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error");
				}
			}
		})
	}
	loadProvince();
	//省下拉框change事件
	$(document).on("change","#selectprovince",function(){
		var province = $(this).find("option:selected").val();
		if (province == "") {
			$("#selectcity").html("");
		} else {
			loadCity(province)
		}
	})
	//手机号正则表达式
	var rex = /1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\d{8}/;
	//选择城市按钮点击事件
	$("#toChooseCity").click(function(){
		document.getElementById('chooseCity').style.display='block';
		document.getElementById('amapFade').style.display='block';
		
	})
	$("#closeChooseCityDivBtn").click(function(){
		$("#radio3").attr("checked",true);
		$("#selectprovince option[value='']").attr("selected",true);
		$("#selectprovince").change()
		$("input[name=chooseCitiTypeRadio]").change();
		document.getElementById('chooseCity').style.display='none';
		document.getElementById('amapFade').style.display='none';
	})
	$("#submitchooseCityBtn").click(function(){
		document.getElementById('chooseCity').style.display='none';
		document.getElementById('amapFade').style.display='none';
	})
	$("#chooseCitiTypeRadio").on("change","input[name=chooseCitiTypeRadio]",function(){
		var value = $("#chooseCitiTypeRadio input[name=chooseCitiTypeRadio]:checked").val();
		if (value == 1){
			$("#provinceCityDiv").css("display","block");
		} else {
			$("#provinceCityDiv").css("display","none");
		}
	})
	
	//存放经纬度的数组
	var lngLagArr = [];
	//添加地图对象
    var map = new AMap.Map('container', {
    	//showIndoorMap	Boolean	是否在有矢量底图的时候自动展示室内地图，PC端默认是true，移动端默认是false
    	//是否监控地图容器尺寸变化，默认值为false
        resizeEnable: true,
    });   
	/* toolBar = new AMap.ToolBar({
		locate: false
	})
    map.addControl(toolBar); */
    
    var MSearch ;
    var times;
    
    //地理编码后显示markers
    function amapShowMarkers(addresses,city){
   		lngLagArr = [];
   		var geocoder = new AMap.Geocoder({
        	 city: city
        });
   		times = 0;
    	$.each($(addresses),function(index,add){
            //地理编码,返回地理编码结果
            geocoder.getLocation(add, function(status, result) {
                times++;
                if (status === 'complete' && result.info === 'OK') {
                	geocoder_CallBack(result)
                }
                if (times == addresses.length){
		    		addPolygon(lngLagArr);
		    		$("#amapsearch").attr("disabled",false);
                } 
            });
            
    	})
    }
    
    //凸包算法
    function convexHull(points) {
        points.sort(function (a, b) {
            return a.x != b.x ? a.x - b.x : a.y - b.y;
        });

        var n = points.length;
        var hull = [];

        for (var i = 0; i < 2 * n; i++) {
            var j = i < n ? i : 2 * n - 1 - i;
            while (hull.length >= 2 && removeMiddle(hull[hull.length - 2], hull[hull.length - 1], points[j]))
                hull.pop();
            hull.push(points[j]);
        }

        hull.pop();
        return hull;
    }
    function removeMiddle(a, b, c) {
        var cross = (a.x - b.x) * (c.y - b.y) - (a.y - b.y) * (c.x - b.x);
        var dot = (a.x - b.x) * (c.x - b.x) + (a.y - b.y) * (c.y - b.y);
        return cross < 0 || cross == 0 && dot <= 0;
    }  
    
    
    
    //多边形覆盖物
    function addPolygon(lngLagArr){  
    	//如果少于三个点 则不需要画范围
   	   if(lngLagArr.length<3){
   		   return false;
   	   }
   	   //计算出边界点
   	   var lngLagObjArr = convexHull(lngLagArr);
   	   var polygonArr = new Array();
   	   $.each($(lngLagObjArr),function(index,value){
   			polygonArr.push(new AMap.LngLat(value.x,value.y));
   	   })
   	   //画多边形
   	   polygon=new AMap.Polygon({     
	 	   path:polygonArr,//设置多边形边界路径  
	 	   strokeColor:"#0000ff", //线颜色  
	 	   strokeOpacity:0.2, //线透明度   
	 	   strokeWeight:3,    //线宽   
	 	   fillColor: "#f5deb3", //填充色  
	 	   fillOpacity: 0.35//填充透明度  
   	  });   
   	   polygon.setMap(map);  
    }  
    
    //地理编码返回结果展示
    function geocoder_CallBack(data) {
        var resultStr = "";
        //地理编码结果数组
        var geocode = data.geocodes;
       	var mark = {'position': [geocode[0].location.getLng(),geocode[0].location.getLat()],'title':geocode[0].formattedAddress};
       	//创建坐标对象，以后画多边形
        var obj = new Object();
        obj.x = geocode[0].location.getLng();
        obj.y = geocode[0].location.getLat()
        lngLagArr.push(obj)
        new AMap.Marker({
            map: map,
            position: [geocode[0].location.getLng(),geocode[0].location.getLat()],
            offset: new AMap.Pixel(-12, -36),
            title: geocode[0].formattedAddress
        });
     	//自适应显示
        map.setFitView();
    }
    map.clearMap();  // 清除地图覆盖物
    // 添加事件监听, 使地图自适应显示到合适的范围
      AMap.event.addDomListener(document.getElementById('setFitView'), 'click', function() {
        var newCenter = map.setFitView();
    });
    
    
    //替换掉字符串中的高亮代码，并获得地址的省市县
    function getAddressPre(addressPre,object) {
    	if(object.收件人省){
			var province = object.收件人省
			province = province.replace(/<span style="color:red">/g,'')
			province = province.replace(/<\/span>/g,'')
			if(province != 'NA'){
			addressPre += province
			}
		}
		if(object.收件人市){
			var city = object.收件人市
			city = city.replace(/<span style="color:red">/g,'')
			city = city.replace(/<\/span>/g,'')
			if(city !='NA'){
			addressPre += city
			}
		}
		if(object.收件人县){
			var country = object.收件人县
			country = country.replace(/<span style="color:red">/g,'')
			country = country.replace(/<\/span>/g,'')
			if (country != 'NA'){
			addressPre += country
			}
		}
		return addressPre
    }
    
    function searchLogistics(query,city){
    	document.getElementById('background').style.display='block';
    	$.ajax({
			type:"get",
			url:projectName+"/logistics/search",
			data:{"query":query},
			dataType:"json",
			success:function(result){
				if(result.data.length > 0 && result.code == 1){
					var address = [];
					var keytr = '<br><table style="width:100%;"><tr>' +
					'<td style="background: #EEE8AA;">寄件人姓名</td>' +
					'<td style="background: #EEE8AA;">寄件人手机号</td>' +
					'<td style="background: #EEE8AA;">寄件人地址</td>' +
					'<td style="background: #EEE8AA;">寄件人地区</td>' +
					'<td style="background: #EEE8AA;">收件人姓名</td>' +
					'<td style="background: #EEE8AA;">收件人手机号</td>' +
					'<td style="background: #EEE8AA;">收件人座机</td>' +
					'<td style="background: #EEE8AA;">收件人地址</td>' +
					'<td style="background: #EEE8AA;">收件人地区</td>' +
					'<td style="background: #EEE8AA;">货物内容</td>' +
					'<td style="background: #EEE8AA;">下单日期</td>' +
					'<td style="background: #EEE8AA;">下单时间</td><tr>' ;
					var valuetr = "";
					$.each(result.data,function(n,list){
						var addressPre = "";
						addressPre = getAddressPre(addressPre,list);
						
							valuetr += "<tr>";
							
							if (list.寄件人姓名  && list.寄件人姓名 != 'NA'){
								valuetr += "<td>"+list.寄件人姓名+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							if (list.寄件人手机号  && list.寄件人手机号 != 'NA'){
								valuetr += "<td>"+list.寄件人手机号+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							if (list.寄件人地址  && list.寄件人地址 != 'NA'){
								valuetr += "<td>"+list.寄件人地址+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							
							var linkArea = "";
							if (list.寄件人省 && list.寄件人省 != 'NA'){
								linkArea += list.寄件人省
							}
							if (list.寄件人市 && list.寄件人市 != 'NA'){
								linkArea += list.寄件人市
							}
							if (list.寄件人县 && list.寄件人县 != 'NA'){
								linkArea += list.寄件人县
							}
							valuetr += "<td>"+linkArea+"</td>";
							
							if (list.收件人姓名  && list.收件人姓名 != 'NA'){
								valuetr += "<td>"+list.收件人姓名+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							if (list.收件人手机号  && list.收件人手机号 != 'NA'){
								valuetr += "<td>"+list.收件人手机号+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							if (list.收件人座机  && list.收件人座机 != 'NA'){
								valuetr += "<td>"+list.收件人座机+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							if (list.收件人地址  && list.收件人地址 != 'NA'){
								valuetr += "<td>"+list.收件人地址+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							var Area = "";
							if (list.收件人省 && list.收件人省 != 'NA'){
								Area += list.收件人省
							}
							if (list.收件人市 && list.收件人市 != 'NA'){
								Area += list.收件人市
							}
							if (list.收件人县 && list.收件人县 != 'NA'){
								Area += list.收件人县
							}
							valuetr += "<td>"+Area+"</td>";
							
							var goodName = "";
							if (list.货物名称  && list.货物名称 != 'NA'){
								goodName += list.货物名称;
							}
							if (list.货物内容  && list.货物内容 != 'NA'){
								goodName += list.货物内容;
							}
							valuetr += "<td>"+goodName+"</td>";
							if (list.下单日期  && list.下单日期  != 'NA'){
								valuetr += "<td>"+list.下单日期+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							if (list.下单时间  && list.下单时间 != 'NA'){
								valuetr += "<td>"+list.下单时间+"</td>";
							} else {
								valuetr += "<td></td>";
							}
							
							
							
							if (list.收件人地址) {
								if (list.收件人地址.trim() != "" && list.收件人地址 != 'NA'){
									var addr = list.收件人地址.trim();
									addr = addr.replace(/<span style="color:red">/g,'')
									addr = addr.replace(/<\/span>/g,'')
									address.push(addressPre + addr);
								}
							}
					});
					valuetr += "</tr></table></br>";
					$("#resultsAmapSearch").append(keytr+valuetr);
					amapShowMarkers(address,city)
					loadForce(result.data)
				} else{
					$("#resultsAmapSearch").append("未找到相关数据")
					$("#amapsearch").attr("disabled",false);
				}
				document.getElementById('background').style.display='none';
			},
			error:function(){
				console.log("ajax发送请求失败！");
				document.getElementById('background').style.display='none';
				$("#amapsearch").attr("disabled",false);
			}
		});
    }
    
    //搜索
	$("#amapsearch").click(function(){
		map.clearMap();
		cleanForce();
		$("#resultsAmapSearch").empty();
		$("#amapsearch").attr("disabled",true);
		var query = $("#amapquery").val().trim();
		var city = "";
		if (query == ''){
			$("#resultsAmapSearch").append("未找到相关数据")
			$("#amapsearch").attr("disabled",false);
			return;
		} else{
		
			var val = $("#chooseCitiTypeRadio input[name=chooseCitiTypeRadio]:checked").val();
			//如果选择了手动选择
			if (val == 1){
				//获取下拉选
				city = $("#selectcity option:selected").val();
				map.clearMap();
				if (city != ""){
					query = query + " " + city
				}
				searchLogistics(query,city);
				
				
			} else if(val == 2) {//如果选择了手机号归属地
				var phones = query.match(rex);
				if (phones != null){
					var phone = phones[0];
					//获取当前手机的归属地
					$.ajax({
						type:"get",
						url:projectName+"/provinceCity/phone/city",
						data:{"phone":phone},
						dataType:"json",
						success:function(result){
							if (result.code==1){
								city = result.data;
								map.clearMap();
								if (city != ""){
									query = query + " " + city
								}
								searchLogistics(query,city);
								
							} else {
								swal("Error!", "系统繁忙，请稍后再试！", "error");
							}
						}
					});
				} else {//关键字中不包含手机号
					searchLogistics(query,city);
				}
			} else if (val == 3) {//不设置城市范围
				searchLogistics(query,city);
			}
		}
	})
	
	$("#toAmap").click(function(){
		document.getElementById('toAmap').style.display='none';
		document.getElementById('toAmapSearchResult').style.display='inline';
		document.getElementById('toForce').style.display='inline';
		document.getElementById('container').style.visibility='visible';
		document.getElementById('setFitViewDiv').style.visibility='visible';
		document.getElementById('resultsAmapSearch').style.display='none';
		document.getElementById('forceDiv').style.visibility='hidden';
	});
	$("#toAmapSearchResult").click(function(){
		document.getElementById('toAmap').style.display='inline';
		document.getElementById('toForce').style.display='inline';
		document.getElementById('toAmapSearchResult').style.display='none';
		document.getElementById('forceDiv').style.visibility='hidden';
		document.getElementById('container').style.visibility='hidden';
		document.getElementById('setFitViewDiv').style.visibility='hidden';
		document.getElementById('resultsAmapSearch').style.display='inline';
		
	});
	$("#toForce").click(function(){
		document.getElementById('toAmap').style.display='inline';
		document.getElementById('toAmapSearchResult').style.display='inline';
		document.getElementById('toForce').style.display='none';
		document.getElementById('container').style.visibility='hidden';
		document.getElementById('setFitViewDiv').style.visibility='hidden';
		document.getElementById('resultsAmapSearch').style.display='none';
		document.getElementById('forceDiv').style.visibility='visible';
		
	});
	
	$("#amapquery").keydown(EnterPressEnsure)
	//enter 控件
	function EnterPressEnsure(){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			document.getElementById("amapsearch").click(); 
		} 
	}; 