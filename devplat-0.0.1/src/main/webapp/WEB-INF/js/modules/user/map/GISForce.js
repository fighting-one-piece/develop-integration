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
	//窗口发生变化时，图表也变化
	$(window).resize(function(){
		myChart.resize();

    });
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
			        //测试效果为让横向布局更大
			        ratioScaling:true,
			        minRadius : 15,
			        maxRadius : 25,
			        roam : true,
			        //向心力
			        gravity: 1,
			        //布局缩放系数，并不完全精确, 效果跟布局大小类似
			        scaling: 6.2,
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
		option.series[0].scaling = getForceScalingByNodeNum(option.series[0].nodes.length);
		myChart.setOption(option);
	}
	//根据节点数，返回恰当的布局缩放系数
	function getForceScalingByNodeNum(nodeNum){
		if(nodeNum <= 40){
			return 1.5;
		} else {
			var num = nodeNum - 40;
			var n = Math.round(num/20);
			return n/10 + 1.5;
		}
	}
	
	//清空图标
	function cleanForce(){
		if(myChart){
			myChart.clear();
		}
	}
