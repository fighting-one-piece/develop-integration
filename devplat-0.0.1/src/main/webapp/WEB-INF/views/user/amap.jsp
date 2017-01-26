<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>GIS查询</title>
    <link rel="stylesheet" href="<%=basePath%>/css/sweetalert2.min.css" />
	<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
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
		#container {
			position: absolute;
			top: 4.5%;
			left: 0;
			right: 0;
			bottom: 0;
			width: 100%;
			height: 95.5%;
			visibility: hidden;
		}
		#forceDiv{
			position: absolute;
			top: 4.5%;
			left: 0;
			right: 0;
			bottom: 0;
			visibility: hidden;
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
		.black_overlay {
			display: none;
			position: absolute;
			top: 0%;
			left: 0%;
			width: 100%;
			height: 100%;
			background-color: black;
			z-index: 1001;
			-moz-opacity: 0.8;
			opacity: .80;
			filter: alpha(opacity = 80);
		}
		.link_white_content {
			display: none;
			position: absolute;
			top: 90px;
			left: 20%;
			width: 60%;
			height: 580px;
			padding: 16px;
			border: 5px solid orange;
			border-radius: 10px;
			background-color: white;
			z-index: 1002;
			overflow-y: auto;
		}
    </style>
    <script type="text/javascript" src="<%=basePath %>/js/jquery-1.8.0.js"></script>
    <script src="http://cache.amap.com/lbs/static/es5.min.js"></script>
    <script src="http://webapi.amap.com/maps?v=1.3&key=f9e3f46783fd0d412cc9ea713c986202&plugin=AMap.Geocoder,AMap.ToolBar"></script>
</head>
<body>

<div id="searchdiv" align="center" style="width: 100%">
	<div style="width: 600px" align="center" id="adfeesf">
		<input type="text" class="form-control input-sm" placeholder="请输入关键字，多个关键字间用空格隔开" id="amapquery" style="width: 50%;">
		<button class="btn btn-sm btn-info" id="toChooseCity">选择城市</button>
		<button value="搜索" class="btn btn-sm btn-info" id="amapsearch">搜索</button>
		<button class="btn btn-sm btn-info" id="toAmapSearchResult" style="display: none;">数据列表</button>
		<button class="btn btn-sm btn-info" id="toAmap">地图定位</button>
		<button class="btn btn-sm btn-info" id="toForce">人物关系</button>
	</div>

</div>
<div id="container"></div>
<div id="setFitViewDiv" class="button-group" style="visibility: hidden;">
    <input id="setFitView" class="button" type="button" value="地图自适应显示"/>
</div>
<div id="resultsAmapSearch" align="center">
		
</div>
<div id="chooseCity" class="link_white_content">
	<div class="styleWhite">
		<button class="btn btn-sm btn-info" id="closeChooseCityDivBtn"
			style="margin-bottom: 10px; margin-left: 5%">关闭</button>
	</div>
	<br>
	<div style="min-height: 5%;" align="center" id="chooseCitiTypeRadio">
		<input type="radio" class="btn btn-info" id="radio3" name="chooseCitiTypeRadio" value="3" checked="checked"><label for="radio3">不设置城市范围</label>&nbsp;&nbsp;
		<input type="radio" class="btn btn-info" id="radio1" name="chooseCitiTypeRadio" value="1"><label for="radio1">手动选择</label>&nbsp;&nbsp;
		<input type="radio" class="btn btn-info" id="radio2" name="chooseCitiTypeRadio" value="2"><label for="radio2">当前搜索手机号的归属地（如果关键字中不包含手机号则无效）</label>
	</div>
	<br>
	<div align="center" style="min-height: 65%;">
	<div class="row" align="center" style="min-height: 70%;display: none;" id="provinceCityDiv">
	<select class="form-control input-sm" style="max-width: 25%;display: inline;" id="selectprovince">
		<option selected="selected"></option>
	</select>
	</div>
	</div>
	<button class="btn btn-sm btn-info pull-right"
		id="submitchooseCityBtn"
		style="margin-top: 10px; margin-right: 5%">确定</button>
</div>
<div id="amapFade" class="black_overlay"></div>
<div id="forceDiv" style="width: 100%;height: 95.5%;"></div>
<!-- 点击搜索后的背景显示 -->
<div id="background" class="all_backgroundcolor" align="center">
	<img class="background_img"  src="<%=basePath %>/img/backgroundcenter.gif">
</div>
<script type="text/javascript" src="<%=basePath%>/js/modules/user/map/force/echarts.js"></script>
<!-- 关系图模块 -->
<script type="text/javascript">
var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

	require.config({
	    paths: {
	        echarts: '../../js/modules/user/map/force'
	    }
	});
	
	var myChart;
	require(
	[
	    'echarts',
	    'echarts/chart/force',
	],
	function(ec) {
	    myChart = ec.init(document.getElementById('forceDiv'));
	    
	})
	//显示关系图
	function loadForce(list){
		var graph ={
           nodes:[],
           links:[]    
	    };
		$.each(list,function(n,item){
			//寄件人姓名
			if(item.寄件人姓名 && item.寄件人姓名 != 'NA' && item.寄件人姓名  != ''){
				var linkname = item.寄件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
				var node = {
					"name":linkname,
					"data":"姓名："+linkname,
		            "category":0,
		            "value": 10
		        }
				graph.nodes.push(node);
			}
			//寄件人地址节点
			if(item.寄件人地址 && item.寄件人地址 != 'NA' && item.寄件人地址  != ''){
				var linkaddress = item.寄件人地址.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
				var node = {
					"name":linkaddress,
					"data":"地址："+linkaddress,
		            "category":1,
		            "value": 5
		        }
				graph.nodes.push(node);
				if(item.寄件人姓名 && item.寄件人姓名 != 'NA' && item.寄件人姓名  != ''){
					var linkname = item.寄件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
					//寄件人与寄件人地址连线
					var link = {
		                "source": linkname,
		                "data":"<ul style='text-align:left'><li style='font:bolder;'>姓名："+linkname+
		                	"<li style='font:bolder;'>地址："+linkaddress+"</ul>",
		                "weight" : 1,
		                "target": linkaddress
		            }
					graph.links.push(link);
				}
			}
			//寄件人手机号
			if(item.寄件人手机号 && item.寄件人手机号 != 'NA' && item.寄件人手机号  != ''){
				var linkphone = item.寄件人手机号.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
				var node = {
						"name":linkphone,
						"data":"电话："+linkphone,
			            "category":2,
			            "value": 5
		        }
				graph.nodes.push(node);
				if(item.寄件人姓名 && item.寄件人姓名 != 'NA' && item.寄件人姓名  != ''){
					//寄件人与寄件人手机号连线
					var linkname = item.寄件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
					var link = {
		                "source": linkname,
		                "data":"<ul style='text-align:left'><li style='font:bolder;'>姓名："+linkname+
	                	"<li style='font:bolder;'>电话："+linkphone+"</ul>",
		                "weight" : 1,
		                "target": linkphone
		            }
					graph.links.push(link);
				}
			}
			//收件人姓名
			if(item.收件人姓名 && item.收件人姓名 != 'NA' && item.收件人姓名  != ''){
				var name = item.收件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
				var node = {
					"name":name,
					"data":"姓名："+name,
		            "category":0,
		            "value": 10
		        }
				graph.nodes.push(node);
				if(item.寄件人姓名 && item.寄件人姓名 != 'NA' && item.寄件人姓名  != ''){
					var linkname = item.寄件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
					//寄件人与收件人连线
					var link = {
		                "source": linkname,
		                "data":"<ul style='text-align:left'><li style='font:bolder;'>寄件人姓名："+linkname+
	                	"<li style='font:bolder;'>收件人姓名："+name+"</ul>",
		                "weight" : 1,
		                "target": name
		            }
					graph.links.push(link);
				}
			}
			//收件人地址节点
			if(item.收件人地址 && item.收件人地址 != 'NA' && item.收件人地址  != ''){
				var address = item.收件人地址.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
				var node = {
					"name":address,
					"data":"地址："+address,
		            "category":1,
		            "value": 5
		        }
				graph.nodes.push(node);
				if(item.收件人姓名 && item.收件人姓名 != 'NA' && item.收件人姓名  != ''){
					var name = item.收件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
					//收件人与收件人地址连线
					var link = {
		                "source": name,
		                "data":"<ul style='text-align:left'><li style='font:bolder;'>姓名："+name+
	                	"<li style='font:bolder;'>地址："+address+"</ul>",
		                "weight" : 1,
		                "target": address
		            }
					graph.links.push(link);
				}
			}
			//收件人手机号
			if(item.收件人手机号 && item.收件人手机号 != 'NA' && item.收件人手机号  != ''){
				var phone = item.收件人手机号.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
				var node = {
						"name":phone,
						"data":"电话："+phone,
			            "category":2,
			            "value": 5
		        }
				graph.nodes.push(node);
				if(item.收件人姓名 && item.收件人姓名 != 'NA' && item.收件人姓名  != ''){
					var name = item.收件人姓名.replace(/<span style="color:red">/g,'').replace(/<\/span>/g,'');
					var link = {
		                "source": name,
		                "data":"<ul style='text-align:left'><li style='font:bolder;'>姓名："+name+
	                	"<li style='font:bolder;'>电话："+phone+"</ul>",
		                "weight" : 1,
		                "target": phone
		            }
					graph.links.push(link);
				}
			}
		})
		
		var  option = {
			title : {
			    text: '物流关系（滑动鼠标滑轮可进行缩放）',
			    x:'left',
			    y:'top'
			},
			tooltip : {
                show : true,   //默认显示
                showContent:true, //是否显示提示框浮层
                trigger:'item',//触发类型，默认数据项触发
                triggerOn:'click',//提示触发条件，mousemove鼠标移至触发，还有click点击触发
                alwaysShowContent:false, //默认离开提示框区域隐藏，true为一直显示
                showDelay:0,//浮层显示的延迟，单位为 ms，默认没有延迟，也不建议设置。在 triggerOn 为 'mousemove' 时有效。
                hideDelay:0,//浮层隐藏的延迟，单位为 ms，在 alwaysShowContent 为 true 的时候无效。
                enterable:false,//鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为 true。
                position:'right',//提示框浮层的位置，默认不设置时位置会跟随鼠标的位置。只在 trigger 为'item'的时候有效。
                confine:false,//是否将 tooltip 框限制在图表的区域内。外层的 dom 被设置为 'overflow: hidden'，或者移动端窄屏，导致 tooltip 超出外界被截断时，此配置比较有用。
                transitionDuration:0.4,//提示框浮层的移动动画过渡时间，单位是 s，设置为 0 的时候会紧跟着鼠标移动。
                formatter: function (params, ticket, callback) {
                    //判断数据，提供相应的url。
                    var node=params.data; //当前选中节点数据
//                    var category=params.data.category;  //选中节点图例0负载 1中间件 2端口号 3数据库 4用户名 
                    return node.data;
                    
                }
            },
			toolbox: {
			    show : true,
			    feature : {
			        restore : {show: true},
			        //magicType: {show: true, type: ['force', 'chord']},
			        saveAsImage : {show: true}
			    }
			},
			/* legend: {
			    x: 'left',
			    data:['人物','地址','电话']
			}, */
			series : [
			    {
			        type:'force',
			        name : "关系",
			        ribbonType: false,
			        categories : [
			            {
			                name: '人物'
			            },
			            {
			                name: '地址',
			                symbol: 'diamond'
			            },
			            {
			                name:'电话'
			            }
			        ],
			        force : { //力引导图基本配置
                        //initLayout: ,//力引导的初始化布局，默认使用xy轴的标点
                        repulsion : 100,//节点之间的斥力因子。支持数组表达斥力范围，值越大斥力越大。
                        gravity : 0.03,//节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
                        edgeLength :80,//边的两个节点之间的距离，这个距离也会受 repulsion。[10, 50] 。值越小则长度越长
                       layoutAnimation : false
                    //因为力引导布局会在多次迭代后才会稳定，这个参数决定是否显示布局的迭代动画，在浏览器端节点数据较多（>100）的时候不建议关闭，布局过程会造成浏览器假死。                        
                    },
			        itemStyle: {
			            normal: {
			                label: {
			                    show: true,
			                    textStyle: {
			                        color: '#333'
			                    }
			                },
			                nodeStyle : {
			                    brushType : 'both',
			                    borderColor : 'rgba(255,215,0,0.4)',
			                    borderWidth : 1
			                }
			            },
			            emphasis: {
			                label: {
			                    show: false
			                    // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
			                },
			                nodeStyle : {
			                    //r: 30
			                },
			                linkStyle : {}
			            }
			        },
			        minRadius : 15,
			        maxRadius : 25,
			        roam : true,
			        //向心力
			        gravity: 1,
			        //布局缩放系数，并不完全精确, 效果跟布局大小类似
			        scaling: 1.2,
					//是否可拖拽
			        draggable: true,
			       // linkSymbol: 'arrow',
			        density : 0.8,
					linkSymbol: 'arrow',
		            //attractiveness: 0.8,
			       // steps: 10,
			        //coolDown: 0.9,
			        //preventOverlap: true,
			        
			        nodes: graph.nodes,
			        links: graph.links
			    }
			]
		};
		myChart.setOption(option);
	}
	//清空图标
	function cleanForce(){
		if(myChart){
			myChart.clear();
		}
	}

</script>
<!-- 地图，搜索模块 -->
<script>
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
</script>
</body>

</html>