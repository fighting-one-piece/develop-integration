$(function() {
	load();
	// 添加接口
	$("#addSwitch").click(
			function() {
				var identity = $("#identity").val();
				var name = $("#name").val();
				var desc = $("#desc").val();
				var status = $("#testSelect").val();
				$.ajax({
					type : "get",
					url : "switch/findAll",
					dataType : "json",
					success : function(result) {
						$("#areaList").empty();
						if (result.code == 1) {
							for (var i = 0; i < result.data.length; i++) {
								var biaoshi = result.data[i].switch_identity;
								if (identity == biaoshi) {
									/* swal(""); */
									swal({
										text : "该标识已有!",
										type : "warning",
										confirmButtonColor : "#DD6B55",
										confirmButtonText : "确定",
									}).then(function(isConfirm) {
										if (isConfirm == true) {
											window.location.reload();
										} else {
											window.location.reload();
										}
									})
									$("#identity").attr("value", '');
									$("#name").attr("value", '');
									$("#desc").attr("value", '');
									return;
								}
							}
							if (identity == "" || identity == undefined
									|| identity == null) {
								swal("标识不能为空!");
								load();
							} else if (name == "" || name == undefined
									|| name == null) {
								swal("名称不能为空!");
							} else if (desc == "" || desc == undefined
									|| desc == null) {
								swal("描述不能为空!");
							} else if (status == "" || status == undefined
									|| status == null) {
								swal("状态不能为空!");
							} else {
								$.ajax({
									type : "post",
									url : "switch/saveSwitch",
									dataType : "json",
									data : {
										"switch_identity" : identity,
										"switch_name" : name,
										"swith_desc" : desc,
										"status" : status
									},
									success : function(result) {
										if (result.code == 1) {
											swal({
												text : "添加成功!",
												type : "success",
												confirmButtonColor : "#DD6B55",
												confirmButtonText : "确定",
											}).then(function(isConfirm) {
												if (isConfirm == true) {
													window.location.reload();
												} else {
													window.location.reload();
												}
											})
											$("#identity").attr("value", '');
											$("#name").attr("value", '');
											$("#desc").attr("value", '');
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
	//批量开启
	$("#batchOpen").click(function() {
		swal({
			title : "",
			text : "确定批量打开？",
			type : "warning",
			showCancelButton : "true",
			showConfirmButton : "true",
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
			animation : "slide-from-top"
		}).then(function(isConfirm) {
			if (isConfirm == true) {
				var objs = $('#areaList').bootstrapTable(
						'getAllSelections');
				if (objs == "" || objs == undefined) {
					swal("请选中开启内容!");
				} else {
					var id = "";
					var identity = "";
					$.map($('#areaList').bootstrapTable('getAllSelections'), function(row) {
						id += row.id + ",";
						identity += row.switch_identity + ",";
					});
					// 去掉最后一个逗号(如果不需要去掉，就不用写)
					if (id.length > 0) {
						id = id.substr(0, id.length - 1);
					}
					if (identity.length > 0) {
						identity = identity.substr(0, identity.length - 1);
					}
					$.ajax({
						 url:"switch/updateIdStatus",
						 type:"post",
						 dataType:"json",
						 data:{"id":id,"identity":identity,"status":1},
						 success:function(result){
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
						 },
						 error:function(){
							 swal("操作失败!");
							 }
						 })
				}
			}
		})
	})
	//批量关闭
	$("#batchClose").click(function() {
		swal({
			title : "",
			text : "确定批量打开？",
			type : "warning",
			showCancelButton : "true",
			showConfirmButton : "true",
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
			animation : "slide-from-top"
		}).then(function(isConfirm) {
					if (isConfirm == true) {
						var objs = $('#areaList').bootstrapTable('getAllSelections');
						if (objs == "" || objs == undefined) {
							swal("请选中开启内容!");
						} else {
							var id = "";
							var identity = "";
							$.map($('#areaList').bootstrapTable('getAllSelections'), function(row) {
								id += row.id + ",";
								identity += row.switch_identity + ",";
							});
							// 去掉最后一个逗号(如果不需要去掉，就不用写)
							if (id.length > 0) {
								id = id.substr(0, id.length - 1);
							}
							if (identity.length > 0) {
								identity = identity.substr(0, identity.length - 1);
							}
							$.ajax({
								 url:"switch/updateIdStatus",
								 type:"post",
								 dataType:"json",
								 data:{"id":id,"identity":identity,"status":0},
								 success:function(result){
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
								 },
								 error:function(){
									 swal("操作失败!");
									 }
								 })
						}
					}
				})
	})
	//批量测试
	$("#batchTest").click(function() {
		swal({
			title : "",
			text : "确定批量测试接口修改？",
			type : "warning",
			showCancelButton : "true",
			showConfirmButton : "true",
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
			animation : "slide-from-top"
		}).then(function(isConfirm) {
			if (isConfirm == true) {
				var objs = $('#areaList').bootstrapTable('getAllSelections');
				if (objs == ""|| objs == undefined) {
					swal("请选中测试接口!");
				} else {
					var id = "";
					var identity = "";
					$.map($('#areaList').bootstrapTable('getAllSelections'),function(row) {
						id += row.id + ",";
						identity += row.switch_identity + ",";
					});
					// 去掉最后一个逗号(如果不需要去掉，就不用写)
					if (id.length > 0) {
						id = id.substr(0, id.length - 1);
					}
					if (identity.length > 0) {
						identity = identity.substr(0, identity.length - 1);
					}
					$.ajax({
						 url:"switch/updateIdStatus",
						 type:"post",
						 dataType:"json",
						 data:{"id":id,"identity":identity,"status":2},
						 success:function(result){
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
						 },
						 error:function(){
							 swal("操作失败!");
							 }
						 })
				}
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
	$.ajax({
		type : "get",
		url : "switch/findAll",
		dataType : "json",
		success : function(result) {
			if (result.code == 1) {
				for (var i = 0; i < result.data.length; i++) {
					var statusA = result.data[i].status;
					if (statusA == 1) {
						result.data[i].status = '开';
					} else if (statusA == 2) {
						result.data[i].status = '测试';
					} else {
						result.data[i].status = '关';
					}
				}
				$('#areaList').bootstrapTable({
					data : result.data

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
			'<button class="btn btn-primary" data-dismiss="modal" data-toggle="modal" data-target="#updata">修改</button>',
			'</a>',
			'&nbsp;',
			'<a class="remove" href="javascript:void(0)" target="_blank" title="编辑">',
			'<button class="btn btn-primary">删除</button>', '</a>' ].join('');
};

window.actionEventsProject = {
	'click .edit' : function(e, value, row, index) {
		var id = row.id;
		var identity = row.switch_identity;
		var name = row.switch_name;
		var desc = row.swith_desc;
		var status = row.status;

		$("#identityA").val(identity)
		$("#nameA").val(name);
		$("#descA").val(desc);
		$("#statusA").val(status);
		$('#updataType').click(
			function() {
				var identity = $("#identityA").val();
				var name = $("#nameA").val();
				var desc = $("#descA").val();
				var status = $("#statusA").val();

				if (identity == "" || identity == undefined
						|| identity == null) {
					swal("标识不能为空!");
				} else if (name == "" || name == undefined
						|| name == null) {
					swal("名称不能为空!");
				} else if (desc == "" || desc == undefined
						|| desc == null) {
					swal("描述不能为空!");
				} else if (status == "" || status == undefined
						|| status == null) {
					swal("状态不能为空!");
				} else {
					swal({
						title : "",
						text : "确定修改？",
						type : "warning",
						showCancelButton : "true",
						showConfirmButton : "true",
						confirmButtonColor : "#DD6B55",
						confirmButtonText : "确定",
						cancelButtonText : "取消",
						animation : "slide-from-top"
					}).then(function(isConfirm) {
										if (isConfirm == true) {
											$.ajax({
												url : "switch/updateSwitch",
												type : "post",
												dataType : "json",
												data : {
													"id" : id,
													"identity" : identity,
													"name" : name,
													"desc" : desc,
													"status" : status
												},
												success : function(result) {
													if (result.code == 1) {
														swal("操作成功!");
														$.ajax({
															type : "get",
															url : "switch/findAll",
															dataType : "json",
															success : function(result) {
																if (result.code == 1) {
																	swal({
																			text : "修改成功!",
																			type : "success",
																			confirmButtonColor : "#DD6B55",
																			confirmButtonText : "确定",
																		}).then(function(isConfirm) {
																			if (isConfirm == true) {
																				window.location
																						.reload();
																			} else {
																				window.location
																						.reload();
																			}
																		})
																}
															}
														})
													} else {
															swal("操作失败!");
													}
												},
												error : function() {
													swal("操作失败!");
												}
											})
										}
									})
				}
			})
	},
	'click .remove' : function(e, value, row, index) {
		swal({
			title : "",
			text : "确定删除吗？",
			type : "warning",
			showCancelButton : "true",
			showConfirmButton : "true",
			confirmButtonColor : "#DD6B55",
			confirmButtonText : "确定",
			cancelButtonText : "取消",
			animation : "slide-from-top"
		}).then(function(isConfirm) {
			if (isConfirm == true) {
				console.log(row.switch_identity)
				$.ajax({
					url : "switch/deleteSwitch",
					type : "post",
					dataType : "json",
					data : {
						"identity" : row.switch_identity
					},
					success : function(result) {
						if (result.code == 1) {
							swal("操作成功!");
							$("tr[data-index='" + index + "']").remove();
						} else {
							swal("操作失败!");
						}
					},
					error : function() {
						swal("操作失败!");
					}
				})
			}
		})
	}
};