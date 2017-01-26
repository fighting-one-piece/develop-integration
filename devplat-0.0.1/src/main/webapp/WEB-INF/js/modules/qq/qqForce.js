var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

	require.config({
	    paths: {
	        echarts: 'js/modules/user/map/force'
	    }
	});
	
	var myChart;
	require(
	[
	    'echarts',
	    'echarts/chart/force',
	],
	function(ec) {
	    myChart = ec.init(document.getElementById('QQforceDiv'));
	    
	})
	//窗口发生变化时，图表也变化
	$(window).resize(function(){
		myChart.resize();

    });
	//显示关系图
	function loadForce(qunList){
		console.log(qunList)
		var qunNumArr = new Array();
		console.log(qunList)
		if(qunList.length > 0){
			$.each(qunList,function(index,qunObject){
				if(qunObject.QQ群号 && qunObject.QQ群号 != 'MA'){
					qunNumArr.push(qunObject.QQ群号);
				}
			})
			//751919318 visibility: hidden;
			if(qunNumArr.length >0){
				$.ajax({
					url:projectName+"/qq/quns/search",
					traditional: true,
					type:"get",
					data:{"qunNumList":qunNumArr},
					dataType:"json",
					success:function(result){
						console.log(result)
						if(result.code == 1){
							if(result.data && result.data.length>0){
								var graph ={
									nodes:[],
									links:[]    
								};
								$.each(qunList,function(index,qunObject){
									if(qunObject.QQ群号 && qunObject.QQ群号 != 'NA' && qunObject.QQ群号 != ''){
										var thisData = "<ul style='text-align:left'><li style='font:bolder;'>QQ群号："+qunObject.QQ群号;
										if(qunObject.群名称 && qunObject.群名称 != 'NA' && qunObject.群名称 != ''){
											thisData += "<li style='font:bolder;'>群名称："+qunObject.群名称
										}
										if(qunObject.群通知 && qunObject.群通知 != 'NA' && qunObject.群通知 != ''){
											thisData += "<li style='font:bolder;'>群通知："+qunObject.群通知
										}
										thisData += "</ul>"
										var node = {
												"name":qunObject.QQ群号,
												"data":thisData,
												"category":0,
												"value": 20
										} 
										graph.nodes.push(node);
									}
								})
								$.each(result.data,function(index,qunlist){
									$.each(qunlist,function(ind,qunObj){
//										if(qunObj.qunNum && qunObj.qunNum != 'NA' && qunObj.qunNum != ''){
//											var node = {
//												"name":qunObj.qunNum,
//												"data":"QQ群号："+qunObj.qunNum,
//									            "category":0,
//									            "value": 20
//									        }
//											graph.nodes.push(node);
//										}
										if(qunObj.qqNum && qunObj.qqNum != 'NA' && qunObj.qqNum != ''){
											var node = {
												"name":qunObj.qqNum,
												"data":"QQ号："+qunObj.qqNum,
									            "category":1,
									            "value": 16
									        }
											graph.nodes.push(node);
											//QQ与QQ群连线
											if(qunObj.qunNum && qunObj.qunNum != 'NA' && qunObj.qunNum != ''){
												var thisData = "<ul style='text-align:left'><li style='font:bolder;'>QQ号："+qunObj.qqNum+"<li style='font:bolder;'>QQ群号："+qunObj.qunNum;
												if(qunObj.nick && qunObj.nick != 'NA' && qunObj.nick != ''){
													thisData += "<li style='font:bolder;'>昵称："+qunObj.nick;
												}
												thisData += "<ul>"
												var link = {
									                "source": qunObj.qqNum,
									                "data":thisData,
									                "weight" : 1,
									                "target": qunObj.qunNum
									            }
												graph.links.push(link)
											}
										}
//										if(qunObj.nick && qunObj.nick != 'NA' && qunObj.nick != ''){
//											var node = {
//												"name":qunObj.nick,
//									            "category":2,
//									            "value": 5
//									        }
//											graph.nodes.push(node);
//											//QQ与QQ群昵称连线
//											if(qunObj.qqNum && qunObj.qqNum != 'NA' && qunObj.qqNum != ''){
//												var link = {
//									                "source": qunObj.qqNum,
//									                "weight" : 1,
//									                "target": qunObj.nick
//									            }
//												graph.links.push(link)
//											}
//										}
										
										var  option = {
										title : {
										    text: 'QQ群关系（滑动鼠标滑轮可进行缩放）',
										    subtext: '',
										    x:'left',
										    y:'top'
										},
//										tooltip : {
//										    trigger: 'item',
//										    formatter: '{b}'
//										},
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
//						                        var category=params.data.category;  //选中节点图例0负载 1中间件 2端口号 3数据库 4用户名 
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
										                name: 'QQ群号'
										            },
										            {
										                name: 'QQ号'
										            },
										            {
										                name:'昵称',
									                	symbol: 'diamond'
										            }
										        ],
										        force : { //力引导图基本配置
							                        //initLayout: ,//力引导的初始化布局，默认使用xy轴的标点
							                        repulsion : 100,//节点之间的斥力因子。支持数组表达斥力范围，值越大斥力越大。
							                        gravity : 0.03,//节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
							                        edgeLength :80,//边的两个节点之间的距离，这个距离也会受 repulsion。[10, 50] 。值越小则长度越长
							                        layoutAnimation : true
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
//										        large:true,
//										        useWorker:true,
//										        steps:1,
//										        center:['50%', '100%'],
										        roam : true,//是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
//										        nodeScaleRatio : 1,//鼠标漫游缩放时节点的相应缩放比例，当设为0时节点不随着鼠标的缩放而缩放
//										        focusNodeAdjacency : true,//是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。
										        minRadius : 15,
										        maxRadius : 25,
										        //向心力
//										        gravity: 1,
										        //布局缩放系数，并不完全精确, 效果跟布局大小类似
										        scaling: 3.5,
												//是否可拖拽
										        draggable: true,
										       // linkSymbol: 'arrow',
										        density : 0.8,
												linkSymbol: 'arrow',
									            //attractiveness: 0.8,
										       // steps: 10,
//										        coolDown: 1,
										        //preventOverlap: true,
										        
										        nodes: graph.nodes,
										        links: graph.links
										    }
										]
									};
									myChart.setOption(option);
										
									})
								})
								$("#toShowForceDiv").css("visibility","visible");
								
							}
						}
					}
				})
				
			}
		}
		
		
	}
	//清空图标
	function cleanForce(){
		if(myChart){
			myChart.restore();
			myChart.clear();
		}
	}