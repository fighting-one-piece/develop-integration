var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
$(function(){
	
	function showAccess(page,pageSize){
		$.ajax({
			type:"get",
			data:{"page":page,"pageSize":pageSize},
			dataType:"json",
			url:projectName+"/admin/accessUser/find",
			success:function(result){
				if (result.code == 1){
					var str = "";
					$.each(result.data.data,function(index,accessUser){
						var accessId = accessUser.accessId;
						var name = accessUser.name;
						var accessKey = accessUser.accessKey;
						var applyTime = FormatDate(new Date(accessUser.applyTime));
						
						var deleteFlag = accessUser.deleteFlag;
						var count = "";
						var remainingCount = "";
						var money = "";
						var remainingMoney = "";
						var queryCount = "";
						if (accessUser.accessControl){
							count = accessUser.accessControl.count;
							remainingCount = accessUser.accessControl.remainingCount;
							money = accessUser.accessControl.money;
							remainingMoney = accessUser.accessControl.remainingMoney;
							queryCount = accessUser.accessControl.queryCount;
						} else {
							count = 0;
							remainingCount = 0;
							money = 0.0;
							remainingMoney = 0.0;
							queryCount = 0;
						}
						if (deleteFlag == 0){
							str = str + "<tr id='"+accessId+"'><td>"+accessId+"</td><td>"+accessKey+"</td><td>"+name
							+"</td><td>"+applyTime+"</td><td>"+count+"</td><td>"+remainingCount+"</td><td>"+money+"</td><td>"+remainingMoney+"</td><td>"+queryCount+"</td><td>正常使用</td><td><button class='btn btn-sm btn-info update-accessControl'>修改剩余条数</button><button class='btn btn-sm btn-info update-remainingMoney'>修改剩余金额</button><button class='btn btn-sm btn-info delete-accessUser'>停用</button></td></tr>";
						} else {
							str = str + "<tr id='"+accessId+"'><td>"+accessId+"</td><td>"+accessKey+"</td><td>"+name
							+"</td><td>"+applyTime+"</td><td>"+count+"</td><td>"+remainingCount+"</td><td>"+money+"</td><td>"+remainingMoney+"</td><td>"+queryCount+"</td><td>已停用</td><td><button class='btn btn-sm btn-info update-accessControl'>修改剩余条数</button><button class='btn btn-sm btn-info update-remainingMoney'>修改剩余金额</button><button class='btn btn-sm btn-info enable-accessUser'>启用</button></td></tr>";
						}
					})
					$("#Accessendpage").val(result.data.pageCount);
					$("#Accesslastpage").val(result.data.pageNum);
					$("#Accesscenextpage").val(result.data.pageNum);
					$("#allAccessTable").html("");
					$("#allAccessTable").append(str);
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error")
				}
			}
		})
	}
	showAccess(1,10);
	//分页查询
	$("#Accessendpage").click(function(){
		var page = $(this).val();
		showAccess(page,10);
	})
	$("#Accesshomepage").click(function(){
		var page = $(this).val();
		showAccess(page,10);
	})
	$("#Accesslastpage").click(function(){
		var nowPage = $(this).val();
		var homePage = $("#Accesshomepage").val();
		if(nowPage == homePage){
			return;
		}
		var page = parseInt(nowPage) - parseInt(1);
		showAccess(page,10);
	})
	$("#Accesscenextpage").click(function(){
		var nowPage = $(this).val();
		var endPage = $("#Accessendpage").val();
		if (nowPage >= endPage){
			return;
		}
		var page = parseInt(nowPage) + parseInt(1);
		showAccess(page,10);
	})
	
	
	
	//显示DIV
	function showAddAccessDIV(){
		document.getElementById('addAccessDiv').style.display='block';
		document.getElementById('addAccessFade').style.display='block';
	}
	$("#closeAddAccessDivBtn").click(function(){
		document.getElementById('addAccessDiv').style.display='none';
		document.getElementById('addAccessFade').style.display='none';
	});
	$("#toAddAccessBtn").click(function(){
		showAddAccessDIV();
	});
	
	$(document).on("click",".update-accessControl",function(){
		var account = $(this).parent().parent().children().eq(0).html();
		$("#submitupdateAccessControlBtn").data("account",account);
		document.getElementById('updateAccessControl').style.display='block';
		document.getElementById('addAccessFade').style.display='block';
	})
	$("#closeupdateAccessControlBtn").click(function(){
		$("#updateAccessControlWaring").html("")
		document.getElementById('updateAccessControl').style.display='none';
		document.getElementById('addAccessFade').style.display='none';
	})
	
	//添加
	$("#submitAddAccessDivBtn").click(function(){
		var name = $("#addAccessName").val().trim();
		if (name == null || name == "") {
			swal("Error!", "请填写完整！", "error");
			return;
		} else {
			$.ajax({
				type:"post",
				data:{"name":name},
				url:projectName+"/admin/accessUser/add",
				dataType:"json",
				success:function(result){
					if(result.code == 1){
						if (result.data == 1){
							swal("Updated! ", "修改成功！", "success");
						} else {
							swal("Error!", "用户已存在！", "error");
						}
					} else {
						swal("Error!", "系统繁忙，请稍后再试！", "error");
					}
					$("#closeAddAccessDivBtn").click();
					var page = $("#Accesslastpage").val();
					showAccess(page,10);
				}
			});
		}
	});
	
	//停用
	$("#resultAccessTb").on("click",".delete-accessUser",function(){
		var accessId = $(this).parent().parent().attr("id");
		swal({
			title:"",  
			text:"确定停用吗？",  
			type:"warning",  
			showCancelButton:"true",  
			showConfirmButton:"true",
			confirmButtonColor: "#DD6B55",
			confirmButtonText:"确定",  
			cancelButtonText:"取消"
		}).then(function(isConfirm){
			if(isConfirm == true){
				changeDeleteFlag(accessId,1);
			}
		});
	})
	//启用
	$("#resultAccessTb").on("click",".enable-accessUser",function(){
		var accessId = $(this).parent().parent().attr("id");
		swal({
			title:"",  
			text:"确定启用吗？",  
			type:"warning",  
			showCancelButton:"true",  
			showConfirmButton:"true",
			confirmButtonColor: "#DD6B55",
			confirmButtonText:"确定",  
			cancelButtonText:"取消"
		}).then(function(isConfirm){
			if(isConfirm == true){
				changeDeleteFlag(accessId,0);
			}
		});
	})
	//增加减少剩余条数
	$("#submitupdateAccessControlBtn").click(function(){
		$("#updateAccessControlWaring").html("");
		var account = $(this).data("account");
		var num = $("#updateAccessControlCount").val().trim();
		var updateType = $("#chooseUpdateAccessControlTypeDiv input[name=chooseUpdateAccessControlType]:checked").val();
		if (num == ''){
			$("#updateAccessControlWaring").html("请输入增加/减少条数！")
		} else {
			if (judgeIsNum(num)){
				$.ajax({
					type:"post",
					data:{"changeCount":num,"type":updateType,"account":account},
					url:projectName+"/admin/accessUserControl/updateCount",
					dataType:"json",
					success:function(result){
						if(result.code == 1){
							$("#closeupdateAccessControlBtn").click();
							swal("Updated! ", "修改成功！", "success");
							var page = $("#Accesslastpage").val();
							showAccess(page,10);
						} else {
							swal("Error!", "系统繁忙，请稍后再试！", "error");
						}
					}
				});
			} else {
				$("#updateAccessControlWaring").html("增加/减少条数必须为纯数字！")
			}
		}
	})
	
	
		//修改剩余金额
	$(document).on("click",".update-remainingMoney",function(){
		var account = $(this).parent().parent().children().eq(0).html();
		$("#submitupdateRemainingMoneyBtn").data("account",account);
		document.getElementById('updateRemainingMoney').style.display='block';
		document.getElementById('addAccessFade').style.display='block';
	})
	$("#closeupdateRemainingMoneyBtn").click(function(){
		$("#updateRemainingMoneyWaring").html("")
		document.getElementById('updateRemainingMoney').style.display='none';
		document.getElementById('addAccessFade').style.display='none';
	})
	//增加减少剩余金额
	$("#submitupdateRemainingMoneyBtn").click(function(){
		$("#updateRemainingMoneyWaring").html("");
		var account = $(this).data("account");
		var num = $("#updateRemainingMoneyCount").val().trim();
		var updateType = $("#chooseUpdateRemainingMoneyTypeDiv input[name=chooseUpdateRemainingMoneyType]:checked").val();
		if (num == ''){
			$("#updateAccessUserControlWaring").html("请输入增加/减少金额！")
		} else {
			if (judgeIsNum(num)){
				$.ajax({
					type:"post",
					data:{"changeCount":num,"type":updateType,"account":account},
					url:projectName+"/admin/accessUserControl/updateMoney",
					dataType:"json",
					success:function(result){
						if(result.code == 1){
							$("#closeupdateRemainingMoneyBtn").click();
							swal("Updated! ", "修改成功！", "success");
							var page = $("#Accesslastpage").val();
							showAccess(page,10);
						} else {
							swal("Error!", "系统繁忙，请稍后再试！", "error");
						}
					}
				});
			} else {
				$("#updateAccessUserControlWaring").html("增加/减少金额必须为纯数字！")
			}
		}
	})
	
	
	//判断是否为纯数字
	function judgeIsNum (srt){  
        var pattern=/^\d+$/g;    
        var result= srt.match(pattern);//match 是匹配的意思   用正则表达式来匹配  
        if (result==null){  
            return false;  
        }else{  
            return true;  
        }  
    }   

	function changeDeleteFlag(accessid,deleteflag){
		$.ajax({
			type:"post",
			data:{"accessId":accessid,"deleteFlag":deleteflag},
			url:projectName+"/admin/accessUser/update/deleteFlag",
			dataType:"json",
			success:function(result){
				if(result.code == 1){
					//刷新 当前页
					if (result.data == 1){
						swal("Updated! ", "修改成功！", "success");
					} else {
						swal("Error!", "系统繁忙，请稍后再试！", "error");
					}
					var page = $("#Accesslastpage").val();
					showAccess(page,10);
				} else {
					swal("Error!", "系统繁忙，请稍后再试！", "error");
				}
			}
		});
	}
});

function FormatDate (strTime) {
	var paddNum = function(num){
        num += "";
        return num.replace(/^(\d)$/,"0$1");
     }
    var date = new Date(strTime);
    return date.getFullYear()+"-"+paddNum(date.getMonth() + 1)+"-"+paddNum(date.getDate())+" "+paddNum(date.getHours())+":"+paddNum(date.getMinutes())+":"+paddNum(date.getSeconds());
}