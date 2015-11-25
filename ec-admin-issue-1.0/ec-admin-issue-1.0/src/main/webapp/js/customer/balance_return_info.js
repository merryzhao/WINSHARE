var robj;
$().ready(function(){
	$("#processButton").bind("click",function(){
		if(!confirm("确定办理？")){
			return;
		}
		var obj=$(this);
 		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
 				"id" : $("#id").val(),
				"format" : 'json'
			},
			url : "/customer/process",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
		 		if(data.error!=""){
					alert(data.error);
				}else{
					alert("办理成功");
					window.location.href="/customer/returninfo/"+$("#id").val();
				}
  		 	}
 		});
	})
	
	$("#cancelButton").bind("click",function(){
		if(!confirm("确定撤回？")){
			return;
		}
		var obj=$(this);
 		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : {
 				"id" : $("#id").val(),
				"format" : 'json'
			},
			url : "/customer/cancel",
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
		 		if(data.error!=""){
					alert(data.error);
				}
		 		else{
  					alert("撤回成功");
  	 				window.location.href="/customer/returninfo/"+$("#id").val();
		 		}
		 	}
 		});
	})
	
 	$("#returnButton").live("click",function(){
 		  $("#ccode").text($("#id").val());
 		  $("#backDiv").dialog('open');
 		  robj=$(this);

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
					alert("回填成功");
	 				window.location.href="/customer/returninfo/"+$("#id").val();
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
	
})