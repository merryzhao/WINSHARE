/**
 * 根据参数1最近一天 2最近一周 3最近一月 设置开始日期和截止日期
 * 
 * @param {}type
 */
var aobj;
function getTime(type) {
	var today = new Date();
	var start = null;
	var end = null;
	if (type == 1) {
		start = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
				+ (today.getDate() - 1);
		end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
				+ today.getDate();
	}
	if (type == 2) {
		start = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
				+ (today.getDate() - 7);
		end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
				+ today.getDate();
	}
	if (type == 3) {
		start = today.getFullYear() + "-" + today.getMonth() + "-"
				+ today.getDate();
		end = today.getFullYear() + "-" + (today.getMonth() + 1) + "-"
				+ today.getDate();
	}
	$("#startDate").val(start);
	$("#endDate").val(end);
}


$().ready(function(){
	$("#selectAll").bind("click",function(){
		if($(this).attr("checked")){
 			$("#datatable").find("input[name='ids']").each(function(){
				$(this).attr("checked",true);
			})
		}else{
 			$("#datatable").find("input[name='ids']").each(function(){
				$(this).attr("checked",false);
			}) 
		}
	})
	
	$("#processButton").bind("click",function(){
		if($("#datatable").find(":checked:checked[name='ids']").length==0){
			alert("请选择数据");
			return;
		}
		if(!confirm("确定批量办理？")){
			return;
		}
		var ids="";
		$("#datatable").find(":checked:checked[name='ids']").each(function(){
			ids=ids+$(this).val()+",";
 		})
 		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
 				"cids" : ids,
				"format" : 'json'
			},
			url : "/customer/processall",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				$("#datatable").find(":checked:checked[name='ids']").each(function(){
					for(var i=0;i<data.errorIds.length;i++){
						if($(this).val()==data.errorIds[i]){
							$(this).parent().parent().find("td[class='statuscss']").text(data.status);
							$(this).parent().parent().find("input[class='statusId']").val(data.statusId);
						}
					}

		 		})	
		 		if(data.error!=""){
					alert(data.error);
				}
		 	}
 		});
	})
	
	$("#cancelButton").bind("click",function(){
		if($("#datatable").find(":checked:checked[name='ids']").length==0){
			alert("请选择数据");
			return;
		}
		if(!confirm("确定批量撤回？")){
			return;
		}
		var ids="";
		$("#datatable").find(":checked:checked[name='ids']").each(function(){
			ids=ids+$(this).val()+",";
 		})
 		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
 				"cids" : ids,
				"format" : 'json'
			},
			url : "/customer/cancelall",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				$("#datatable").find(":checked:checked[name='ids']").each(function(){
					for(var i=0;i<data.errorIds.length;i++){
						if($(this).val()==data.errorIds[i]){
							$(this).parent().parent().find("td[class='statuscss']").text(data.status);
							$(this).parent().parent().find("input[class='statusId']").val(data.statusId);
						}
					}

		 		})	
		 		if(data.error!=""){
					alert(data.error);
				}
		 	}
 		});
	})
	
	$("#exApplyButton").bind("click",function(){
		if($("#datatable").find(":checked:checked[name='ids']").length==0){
			alert("请选择数据");
			return;
		}
		if(!confirm("确定批量导出信息？")){
			return;
		}
		var ids="";
		$("#datatable").find(":checked:checked[name='ids']").each(function(){
			ids=ids+$(this).val()+",";
 		})
 		$("#exids").val(ids);
		$("#actionType").val("apply");
		$("#exForm").submit();
	})
	
	$("#exButton").bind("click",function(){
		if($("#datatable").find(":checked:checked[name='ids']").length==0){
			alert("请选择数据");
			return;
		}
		if(!confirm("确定批量导出信息？")){
			return;
		}
		var ids="";
		$("#datatable").find(":checked:checked[name='ids']").each(function(){
			ids=ids+$(this).val()+",";
 		})
 		$("#exids").val(ids);
		$("#exForm").submit();
 	})
	$("#exRefundButton").bind("click",function(){
		if($("#datatable").find(":checked:checked[name='ids']").length==0){
			alert("请选择数据");
			return;
		}
		if(!confirm("确定批量导出信息？")){
			return;
		}
		var ids="";
		$("#datatable").find(":checked:checked[name='ids']").each(function(){
			ids=ids+$(this).val()+",";
			
 		})
 		$("#exrefundids").val(ids);
		$("#refundForm").submit();
	})
 	
	$("#fileButton").bind("click",function(){
		if($("#fileName").val().length==0){
			alert("请选择文件");
			return;
		}
		$("#fileForm").submit();
	})
	
	$(".back").live("click",function(){
		  aobj=$(this);
		  if($(this).parent().find("input[class='statusId']").val()!=44002){
			  alert("只用办理中的的提现申请才能回填");
			  return;
		  }
		  $("#ccode").text($(this).parent().find("input[class='cid']").val());
 		  $("#backDiv").dialog('open');
	})
	
	$(".print").live("click",function(){
		  if($(this).parent().find("input[class='statusId']").val()!=44002||$(this).parent().find("input[class='type']").val()!=43004){
			  alert("只有邮局汇款且在办理中的提现申请才可以打印");
			  return;
		  }
	})
	
	
	
	$("#sbm").bind("click",function(){
		if($.trim($("#tradeNo").val()).length==0){
			alert("交易编号不能为空");
			return;
		}
 
 		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
 				"id" : $("#ccode").text(),
 				"tradeNo" : $("#tradeNo").val(),
 				"mark" : $("#mark").val(),
				"format" : 'json'
			},
			url : "/customer/successcash",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
		 		if(data.error!=""){
					alert(data.error);
				}else{
					alert("操作成功");
			 		$("#backDiv").dialog('close');
					aobj.parent().parent().find("td[class='statuscss']").text(data.status);
					aobj.parent().parent().find("input[class='statusId']").val(data.statusId);
				}
		 	}
 		});
	})
	
	$("#backDiv").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width:300
	});	
	
	$("#clearnTime").live("click",function(){
		$("#startDate").val("");
		$("#endDate").val("");
	})
	
})