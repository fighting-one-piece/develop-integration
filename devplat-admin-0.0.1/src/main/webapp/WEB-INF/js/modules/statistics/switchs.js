$(function() {
		load();
		//添加接口
		$("#addSwitch").click(function(){
			var identity= $("#identity").val();
			var name = $("#name").val();
			var desc = $("#desc").val();
			var status =$("#testSelect").val();
			$.ajax({
				type : "get",
				url : "switch/findAll",
				dataType : "json",
				success : function(result) {
					$("#areaList").empty();
					if(result.code==1){
						for (var i = 0; i < result.data.length; i++) {
							var biaoshi = result.data[i].switch_identity;
							if(identity==biaoshi){
								/* swal(""); */
								swal({
			       					text:"该标识已有!",
			       					type:"warning",  
			       					confirmButtonColor: "#DD6B55",
			       					confirmButtonText:"确定",
			       				}).then(function(isConfirm){
			       					if(isConfirm == true){
			       						window.location.reload();
			       					}else{
			       						window.location.reload();
			       					}})
								$("#identity").attr("value",'');
								$("#name").attr("value",'');
								$("#desc").attr("value",'');
								return;
							}
						}
						if(identity==""||identity==undefined||identity==null){
							swal("标识不能为空!");
							load();
						}else if (name==""||name==undefined||name==null) {
							swal("名称不能为空!");
						}else if (desc==""||desc==undefined||desc==null) {
							swal("描述不能为空!");
						}else if (status==""||status==undefined||status==null) {
							swal("状态不能为空!");
						}else{
							$.ajax({
								type : "post",
								url : "switch/saveSwitch",
								dataType : "json",
								data:{"switch_identity":identity,"switch_name":name,"swith_desc":desc,"status":status},
								success : function(result) {
									if(result.code==1){
										swal({
					       					text:"添加成功!",
					       					type:"success",  
					       					confirmButtonColor: "#DD6B55",
					       					confirmButtonText:"确定",
					       				}).then(function(isConfirm){
					       					if(isConfirm == true){
					       						window.location.reload();
					       					}else{
					       						window.location.reload();
					       					}})
										$("#identity").attr("value",'');
										$("#name").attr("value",'');
										$("#desc").attr("value",'');
									}
								},
								error : function() {
									swal("系统错误！");
								}
							})	
						}
					}
				},
				error : function() {
					swal("系统错误!");
				}
			})
		})
		
	});
	function bootstrapTable() {
		$('#projectList').bootstrapTable({
			data : tmp_arr
		});
	}
	function load() {
		var arealist_arr = new Array();
		$.ajax({
			type : "get",
			url : "switch/findAll",
			dataType : "json",
			success : function(result) {
				if(result.code==1){
					arealist_arr=result.data;
	                $('#areaList').bootstrapTable({
	                    data: result.data
	                });
				}
			},
			error : function() {
				bootstrapTable();
			}
		})
	}

function actionFormatterProject(value, row, index) {
		 return [       
	         '<a class="edit" href="javascript:void(0)" target="_blank" title="编辑">',
	      	 '<button class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#updata">修改</button>','</a>' ,'&nbsp;',
	         '<a class="remove" href="javascript:void(0)" target="_blank" title="编辑">',
	         '<button class="btn btn-primary">删除</button>','</a>'       
	         ].join('');
	         };

        window.actionEventsProject = {
        'click .edit': function (e, value, row, index) {
        	var id = row.id;
        	var identity = row.switch_identity;
        	var name = row.switch_name;
        	var desc = row.swith_desc;
        	var status = row.status;

       		$("#identityA").val(identity)
       		$("#nameA").val(name);
       		$("#descA").val(desc);
       		$("#statusA").val(status);	
       		$('#updataType').click(function() {
       			var identity= $("#identityA").val();
       			var name= $("#nameA").val();
       			var desc= $("#descA").val();
       			var status =$("#statusA").val();
       			
       			if(identity==""||identity==undefined||identity==null){
       				swal("标识不能为空!");
       			}else if (name==""||name==undefined||name==null) {
       				swal("名称不能为空!");
       			}else if (desc==""||desc==undefined||desc==null) {
       				swal("描述不能为空!");
       			}else if (status==""||status==undefined||status==null) {
       				swal("状态不能为空!");
       			}else{
       				swal({
       					title:"",  
       					text:"确定修改？",  
       					type:"warning",  
       					showCancelButton:"true",  
       					showConfirmButton:"true",
       					confirmButtonColor: "#DD6B55",
       					confirmButtonText:"确定",
       					cancelButtonText:"取消", 
       					animation:"slide-from-top"  
       				}).then(function(isConfirm){
       					if(isConfirm == true){
       						$.ajax({
       							url:"switch/updateSwitch",
       							type:"post",
       							dataType:"json",
       							data:{"id":id,"identity":identity,"name":name,"desc":desc,"status":status},
       							success:function(result){
       								if(result.code == 1){
       									swal("操作成功!"); 
       									$.ajax({
       										type : "get",
       										url : "switch/findAll",
       										dataType : "json",
       										success : function(result) {
       											if(result.code==1){
       												swal({
       							       					text:"修改成功!",
       							       					type:"success",  
       							       					confirmButtonColor: "#DD6B55",
       							       					confirmButtonText:"确定",
       							       				}).then(function(isConfirm){
       							       					if(isConfirm == true){
       							       						window.location.reload();
       							       					}else{
       							       						window.location.reload();
       							       					}})
       											}
       										}
       									})
       								}else{
       									swal("操作失败!");
       								}
       							},
       							error:function(){
       								swal("操作失败!");
       							}
       						})
       					}
       				})
       			}
       		}) 
        },
    	'click .remove': function (e, value, row, index) {
    		swal({
    			title:"",  
    			text:"确定删除吗？",  
    			type:"warning",  
    			showCancelButton:"true",  
    			showConfirmButton:"true",
    			confirmButtonColor: "#DD6B55",
    			confirmButtonText:"确定",  
    			cancelButtonText:"取消",  
    			animation:"slide-from-top"  
    		}).then(function(isConfirm){
    			if(isConfirm == true){
    				console.log(row.switch_identity)
	    			$.ajax({
						url:"switch/deleteSwitch",
						type:"post",
						dataType:"json",
						data:{"identity":row.switch_identity},
						success:function(result){
							if(result.code == 1){
								swal("操作成功!");
								$("tr[data-index='"+index+"']").remove();
							}else{
								swal("操作失败!");
							}
						},
						error:function(){
							swal("操作失败!");
						}
					})
    			}
    		})
    	}
};