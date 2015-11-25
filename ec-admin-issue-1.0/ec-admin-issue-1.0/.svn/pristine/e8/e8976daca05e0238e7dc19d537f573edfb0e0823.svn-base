$(function(){
	$("input:submit,input:button,input:file,button,.jButton").button();
	
	$(".active").click(function(){
		var appKey = $(this).attr("for");
		var appPwd = $(this).attr("title");
		var msg = prompt("输入启用提示消息","您在文轩网的API已启用,appkey为：" + appKey + ", app密钥为：" + appPwd);
		if(msg == null){
			return;
		}
		
		$.ajax({
			type: "POST",
			async: false,
			url: "/app/audit?format=json",
			data: {
				_method:"PUT",
				appKey:appKey,
				viewName:"/app/result",
				message:msg
			},
			success:function(mav){
				if(mav.result == 1){
					result = true;
				}else{
					alert(mav.message);
				}
			}
		});
		window.location.reload();
	});
	
	$(".block").click(function(){
		var appKey = $(this).attr("for");
		var msg=prompt("输入停用提示消息","您在文轩网的API已停用,该API的appkey为：" + appKey);
		if(msg == null){
			return;
		}
		var appKey = $(this).attr("for");
		$.ajax({
			type: "POST",
			async: false,
			url: "/app/audit?format=json",
			data: {
				_method:"PUT",
				appKey:appKey,
				viewName:"/app/result",
				message : msg
			},
			success:function(mav){
				if(mav.result == 1){
					result = true;
				}else{
					alert(mav.message);
				}
			}
		});
		window.location.reload();
	});
	
	$(".delete").click(function(){
		if(!confirm("确定删除吗?")){
			return;
		}
		var appKey = $(this).attr("for");
		$.ajax({
			type: "POST",
			async: false,
			url: "/app?format=json",
			data: {
				_method:"DELETE",
				appKey:appKey,
				viewName:"/app/result"
			},
			success:function(mav){
				if(mav.result == 1){
					result = true;
				}else{
					alert(mav.message);
				}
			}
		});
		window.location.reload();
	});
});