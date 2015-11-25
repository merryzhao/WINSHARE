define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		present={
			ACTIVE_SUCCESS_EVENT:"success",
			ACTIVE_ERROR_EVENT:"error",
			msgMap:{
				"0":"输入的激活码不存在，请确认后重新输入",
				"1":"激活成功",
				"2":"服务器内部错误",
				"3":"警告"
			},
			active:function(code){
				var urlArray=[conf.portalServer+"/customer/present/active?format=jsonp"];
					urlArray.push("callback=?");
					urlArray.push("code="+code);
				$.ajax({
					url:urlArray.join("&"),
					success:function(data){
						if(data.result=="1"){
							$(present).trigger(present.ACTIVE_SUCCESS_EVENT,[data]);
						}else{
							$(present).trigger(present.ACTIVE_ERROR_EVENT,[present.msgMap[data.result]]);		
						}
					},
					error:function(xhr,status){
						$(present).trigger(present.ACTIVE_ERROR_EVENT,[status]);
					},
					dataType:"jsonp"
				}); 
			}			
		};
	return present;
});