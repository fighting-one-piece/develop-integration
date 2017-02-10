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
		var ecConfig = require('echarts/config');
	    myChart = ec.init(document.getElementById('QQforceDiv'));
	    myChart.on(ecConfig.EVENT.CLICK,addNewData);
	})
	//窗口大小发生变化时，图表div大小也变化
	$(window).resize(function(){
		myChart.resize();
    });
	//处理点击事件
	function addNewData(param){
		if(param.data.category){
			if(param.data.category == 1){
				document.getElementById('background').style.display='block';
				var option = myChart.getOption();//获取已生成图形的Option
				var qqNum = param.name;
				//如果不显示之前数据
				option.series[0].nodes = new Array();
				option.series[0].links = new Array();
				option.title.text = 'QQ群关系（滑动鼠标滑轮可进行缩放）\n点击蓝色节点可查询对应QQ号群关系图\n当前查询QQ号：'+qqNum;
				$.ajax({
					url:projectName+"/qq/qq/quns/search",
					data:{"qqNum":qqNum},
					type:"get",
					dataType:"json",
					success:function(result){
						if(result.code == 1){
							var qunBase = result.data.qunBase;
							var qun = result.data.qun;
							$.each(qunBase,function(index,qunObject){
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
									option.series[0].nodes.push(node);
								}
							})
							$.each(qun,function(index,qunlist){
								$.each(qunlist,function(ind,qunObj){
									if(qunObj.qqNum && qunObj.qqNum != 'NA' && qunObj.qqNum != ''){
										var node = {
											"name":qunObj.qqNum,
											"data":"QQ号："+qunObj.qqNum,
								            "category":1,
								            "value": 16
								        }
										option.series[0].nodes.push(node);
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
											option.series[0].links.push(link);
										}
									}
								})
							})
							option.series[0].scaling = getScalingByNodeNum(option.series[0].nodes.length);
							cleanForce();
							myChart.setOption(option);
							
						}
						//清除背景
						document.getElementById('background').style.display='none';
					},
					error:function(){
						document.getElementById('background').style.display='none';
					}
				})
			}
		}
	}
	//显示关系图
	function loadForce(qq,qunList){
		var qunNumArr = new Array();
		if(qunList.length > 0){
			$.each(qunList,function(index,qunObject){
				if(qunObject.QQ群号 && qunObject.QQ群号 != 'NA'){
					qunNumArr.push(qunObject.QQ群号);
				}
			})
			//751919318               603180677
			if(qunNumArr.length >0){
				$.ajax({
					url:projectName+"/qq/quns/search",
					traditional: true,
					type:"get",
					data:{"qunNumList":qunNumArr},
					dataType:"json",
					success:function(result){
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
										
									})
								})
								var option = {
									title : {
										text: 'QQ群关系（滑动鼠标滑轮可进行缩放）\n点击蓝色节点可查询对应QQ号群关系图\n当前查询QQ号：'+qq,
										subtext: '',
										x:'left',
										y:'top'
									},
//									tooltip : {
//										trigger: 'item',
//										formatter: '{b}'
//									},
									tooltip : {
										show : true,   //默认显示
										showContent:true, //是否显示提示框浮层
										trigger:'item',//触发类型，默认数据项触发
										triggerOn:'click',//提示触发条件，mousemove鼠标移至触发，还有click点击触发
										alwaysShowContent:false, //默认离开提示框区域隐藏，true为一直显示
										showDelay:0,//浮层显示的延迟，单位为 ms，默认没有延迟，也不建议设置。在 triggerOn 为 'mousemove' 时有效。
										hideDelay:0,//浮层隐藏的延迟，单位为 ms，在 alwaysShowContent 为 true 的时候无效。
										enterable:true,//鼠标是否可进入提示框浮层中，默认为false，如需详情内交互，如添加链接，按钮，可设置为 true。
										position:'right',//提示框浮层的位置，默认不设置时位置会跟随鼠标的位置。只在 trigger 为'item'的时候有效。
										confine:false,//是否将 tooltip 框限制在图表的区域内。外层的 dom 被设置为 'overflow: hidden'，或者移动端窄屏，导致 tooltip 超出外界被截断时，此配置比较有用。
										transitionDuration:0.2,//提示框浮层的移动动画过渡时间，单位是 s，设置为 0 的时候会紧跟着鼠标移动。
										formatter: function (params, ticket, callback) {
											var node=params.data; //当前选中节点数据
//						                    var category=params.data.category;  //选中节点图例
											return node.data;
											
										}
									},
									toolbox: {
										show : true,
										feature : {
											restore : {show: true},
											//magicType: {show: true, type: ['force', 'chord']},
											saveAsImage : {show: true},
//										    dataZoom : {show: true}
										}
									},
//									legend: {
//										x: 'left',
//										y: 'bottom',
//										data:['QQ群号','QQ号']
//									},
									series : [{
						        	  type:'force',
						        	  name : "关系",
						        	  ribbonType: false,
						        	  categories : [
			        	                {
			        	                	name: 'QQ群号'
			        	                },
			        	                {
			        	                	name: 'QQ号'
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
//	                    				dataZoom : {
//	                        				show : true,
//	                        				title : {
//	                            				dataZoom : '区域缩放',
//	                            				dataZoomReset : '区域缩放后退'
//	                        				}
//	                    				},
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
			        	                //测试效果为让横向布局更大
			        	                ratioScaling:true,
//				        				large:true,
//				        				useWorker:true,
//				        				steps:1,
//				      					center:['50%', '50%'],
//				       					size: '200%',
			        	                roam : true,//是否开启鼠标缩放和平移漫游。默认不开启。如果只想要开启缩放或者平移，可以设置成 'scale' 或者 'move'。设置成 true 为都开启
//				        				nodeScaleRatio : 1,//鼠标漫游缩放时节点的相应缩放比例，当设为0时节点不随着鼠标的缩放而缩放
//				        				focusNodeAdjacency : true,//是否在鼠标移到节点上的时候突出显示节点以及节点的边和邻接节点。
			        	                minRadius : 15,
			        	                maxRadius : 25,
			        	                //向心力
				        				gravity: 1,
			        	                //布局缩放系数，并不完全精确, 效果跟布局大小类似
			        	                scaling: 3.5,
			        	                //是否可拖拽
			        	                draggable: true,
			        	                density : 0.8,
			        	                linkSymbol: 'arrow',
			        	                //attractiveness: 0.8,
//			        	                steps: 10,
//				       					coolDown: 0.97,
			        	                //preventOverlap: true,
			        	                
			        	                nodes: graph.nodes,
			        	                links: graph.links
						          }]
								};
								option.series[0].scaling = getScalingByNodeNum(option.series[0].nodes.length);
								myChart.setOption(option);
								$("#toShowForceDiv").css("visibility","visible");
							}
						}
					}
				})
			}
		}
	}
	//根据节点数，返回恰当的布局缩放系数
	function getScalingByNodeNum(nodeNum){
		if(nodeNum <= 270){
			return 3.5;
		} else {
			var num = nodeNum - 270;
			var n = Math.round(num/150);
			return n/2 + 3.5;
		}
	}
	
	//清空图标
	function cleanForce(){
		if(myChart){
			myChart.clear();
		}
	}