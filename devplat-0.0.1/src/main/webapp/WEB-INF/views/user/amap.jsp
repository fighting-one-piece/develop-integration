<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>GIS查询</title>
    <style>
    	.info-tip {
            position: absolute;
            top: 10px;
            right: 10px;
            font-size: 12px;
            background-color: #fff;
            height: 35px;
            text-align: left;
        }
        #amapquery {
  			 display:inline;
        }
        /* #searchdiv {
        	 position: absolute;
        	 top: 0;
        	 height: 5%;
        	 text-align: center;
        } */
		#container {
			position: absolute;
			top: 4.5%;
			left: 0;
			right: 0;
			bottom: 0;
			width: 100%;
			height: 95.5%;
		}
		
		.button-group {
			position: absolute;
			top: 6%;
			left: 5%;
			font-size: 12px;
			padding: 10px;
		}
		
		.button-group .button {
			height: 28px;
			line-height: 28px;
			background-color: #0D9BF2;
			color: #FFF;
			border: 0;
			outline: none;
			padding-left: 5px;
			padding-right: 5px;
			border-radius: 3px;
			margin-bottom: 4px;
			cursor: pointer;
		}
		.button-group .inputtext {
			height: 26px;
			line-height: 26px;
			border: 1px;
			outline: none;
			padding-left: 5px;
			padding-right: 5px;
			border-radius: 3px;
			margin-bottom: 4px;
			cursor: pointer;
		}
		 /*
		.tip {
			position: absolute;
			bottom: 30px;
			right: 10px;
			background-color: #FFF;
			text-align: center;
			border: 1px solid #ccc;
			line-height: 30px;
			border-radius: 3px;
			padding: 0 5px;
			font-size: 12px;
		}
		*/
		#tip {
			background-color: #fff;
			padding-left: 10px;
			padding-right: 10px;
			position: absolute;
			font-size: 12px;
			right: 10px;
			top: 20px;
			border-radius: 3px;
			border: 1px solid #ccc;
			line-height: 30px;
		}
		
		/*
		#tip input[type='button'] {
			margin-top: 10px;
			margin-bottom: 10px;
			background-color: #0D9BF2;
			height: 30px;
			text-align: center;
			line-height: 30px;
			color: #fff;
			font-size: 12px;
			border-radius: 3px;
			outline: none;
			border: 0;
		}
		*/
		.amap-info-content {
			font-size: 12px;
		}
		
		#myPageTop {
			position: absolute;
			top: 5px;
			right: 10px;
			background: #fff none repeat scroll 0 0;
			border: 1px solid #ccc;
			margin: 10px auto;
			padding:6px;
			font-family: "Microsoft Yahei", "微软雅黑", "Pinghei";
			font-size: 14px;
		}
		#myPageTop label {
			margin: 0 20px 0 0;
			color: #666666;
			font-weight: normal;
		}
		#myPageTop input {
			width: 170px;
		}
		#myPageTop .column2{
			padding-left: 25px;
		}
    </style>
    <script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.3&key=f9e3f46783fd0d412cc9ea713c986202&plugin=AMap.Geocoder,AMap.ToolBar"></script>
</head>
<body>
<div id="searchdiv" align="center" style="width: 100%">
	<div style="width: 600px" align="center" id="adfeesf">
		<input type="text" class="form-control input-sm" id="amapquery" style="width: 50%;">
		<button value="搜索" class="btn btn-sm btn-info" id="amapsearch">搜索</button>
		<button class="btn btn-sm btn-info" id="toAmapSearchResult" style="display: none;">数据列表</button>
		<button class="btn btn-sm btn-info" id="toAmap">地图定位</button>
	</div>

</div>
<div id="container" style="display: none;"></div>
<div id="setFitViewDiv" class="button-group" style="display: none;">
    <input id="setFitView" class="button" type="button" value="地图自适应显示"/>
</div>
<!-- <div class="info-tip">
    <div id="centerCoord"></div>
    <div id="tips"></div>
</div> -->
<div id="resultsAmapSearch" align="center">
		
</div>

<script>
   /*  var markers = []; */
    var address = [];
   	var lngLagArr = [];
   	var lagArr = [];
    //address.push("杭州市西湖区浙江省杭州市西湖区西溪街道文三路108号中大文锦苑16-2-");
    //address.push("上海上海市徐汇区田林路487号")
    var map = new AMap.Map('container', {
        resizeEnable: true,
    });   
	toolBar = new AMap.ToolBar({
	})
    map.addControl(toolBar);
    
    var MSearch ;
    var times;
    
    function amapShowMarkers(addresses){
    	lngArr = [];
    	lagArr = [];
   		var geocoder = new AMap.Geocoder({
        	/* city: "成都" */
        });
   		times = 0;
    	$.each($(addresses),function(index,add){
            //地理编码,返回地理编码结果
            geocoder.getLocation(add, function(status, result) {
                times++;
                if (status === 'complete' && result.info === 'OK') {
                	geocoder_CallBack(result)
                } else {
               		/*  map.plugin(["AMap.PlaceSearch"], function() {    //构造地点查询类  
                       MSearch = new AMap.PlaceSearch({ 
                       	 city:"成都", 
                           pageSize:1,
                           pageIndex:1,
                       });
                       AMap.event.addListener(MSearch, "complete", function(data){
                           var poiArr = data.poiList.pois;
                       	if (poiArr.length > 0) {
                       		
               	           var lngX = poiArr[0].location.getLng();
               	           var latY = poiArr[0].location.getLat();
               	          // var mark = {'position': [poiArr[0].location.getLng(),poiArr[0].location.getLat()],'title':poiArr[0].address};
               	          
               	           new AMap.Marker({
               	               map: map,
               	               position: [lngX, latY],
               	               offset: new AMap.Pixel(-12, -36),
               	               title: poiArr[0].address
               	           });
               	           map.setFitView();
                       	}
                       });     
                        
                    });
                	MSearch.search(add);  */
                }
                if (times == addresses.length){
		    		addPolygon(lngLagArr);
                } 
            });
            
    	})
    }
    //多边形覆盖物
    function addPolygon(lngLagArr){  
		var polygonArr=new Array();//多边形覆盖物节点坐标数组   
		var maxLng = Math.max.apply(null,lngArr);
		var minLng = Math.min.apply(null,lngArr);
		var maxLag = Math.max.apply(null,lagArr);
		var minLag = Math.min.apply(null,lagArr);
		//画矩形
		polygonArr.push(new AMap.LngLat(maxLng,maxLag));
		polygonArr.push(new AMap.LngLat(minLng,maxLag));
		polygonArr.push(new AMap.LngLat(minLng,minLag));
		polygonArr.push(new AMap.LngLat(maxLng,minLag));
   	   /* $.each($(lngLagArr),function(index,value){
   			console.log(value)
			polygonArr.push(value);   
   	   }) */
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
        //'icon': 'http://webapi.amap.com/theme/v1.3/markers/n/mark_b1.png',
       	var mark = {'position': [geocode[0].location.getLng(),geocode[0].location.getLat()],'title':geocode[0].formattedAddress};
       	/* lngLagArr.push(new AMap.LngLat(geocode[0].location.getLng(),geocode[0].location.getLat())); */
       	lngArr.push(geocode[0].location.getLng());
       	lagArr.push(geocode[0].location.getLat())
       	/* markers.push(mark); */
        new AMap.Marker({
            map: map,
            //icon: marker.icon,
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
    
	$("#amapsearch").click(function(){
		var query = $("#amapquery").val().trim();
		$("#amapsearch").attr("disabled",true);
		if (query == ''){
			$("#amapsearch").attr("disabled",false);
			return;
		} else {
			map.clearMap();
			$("#resultsAmapSearch").empty();
			$.ajax({
				type:"get",
				url:projectName+"/logistics/search",
				data:{"query":query},
				dataType:"json",
				success:function(result){
					if(result.data.length > 0 && result.code == 1){
						address = [];
						$.each(result.data,function(n,list){
							var keytr = "<br><table style='width:100%;'><tr>";
							var valuetr = "<tr>";
							$.each(list, function(resultKey, resultValue){
								if (resultKey != "index" && resultKey != "type"){
									keytr += "<td style='background: #EEE8AA;'>"+resultKey+"</td>";
									valuetr += "<td style='background: white;'>"+resultValue+"</td>";
								}
								if (resultKey=="收件人地址") {
									if (resultValue.trim() != ""){
										address.push(resultValue.trim());
									}
								}
							});
							keytr += "</tr>";
							valuetr += "</tr></table></br>";
							$("#resultsAmapSearch").append(keytr+valuetr);
						});
						amapShowMarkers(address)
						
					} else{
						$("#resultsAmapSearch").append("未找到相关数据")
					}
					$("#amapsearch").attr("disabled",false);
				},
				error:function(){
					$("#amapsearch").attr("disabled",false);
				}
			});
		}
	})
	$("#toAmap").click(function(){
		document.getElementById('toAmapSearchResult').style.display='inline';
		document.getElementById('toAmap').style.display='none';
		document.getElementById('resultsAmapSearch').style.display='none';
		document.getElementById('container').style.display='inline';
		document.getElementById('setFitViewDiv').style.display='inline';
	});
	$("#toAmapSearchResult").click(function(){
		document.getElementById('toAmap').style.display='inline';
		document.getElementById('container').style.display='none';
		document.getElementById('toAmapSearchResult').style.display='none';
		document.getElementById('setFitViewDiv').style.display='none';
		document.getElementById('resultsAmapSearch').style.display='inline';
		
	});
	
	$("#amapquery").keydown(EnterPressEnsure)
	//enter 控件
	function EnterPressEnsure(){ //传入 event 
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			document.getElementById("amapsearch").click(); 
		} 
	}; 
</script>
</body>

</html>