define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		presentcard={
			BIND_SUCCESS_EVENT:"success",
			BIND_ERROR_EVENT:"error",
			msgMap:{
				"0":"输入的礼品卡卡号或密码不正确，请确认后重新输入",
				"1":"绑定成功"
			},
			bind:function(code,password){
				var urlArray=[conf.portalServer+"/customer/presentcard/bind?format=jsonp"];
					urlArray.push("callback=?");
					urlArray.push("code="+code);
					urlArray.push("password="+password);
				$.ajax({
					url:urlArray.join("&"),
					success:function(data){
						if(data.result=="1"){
							$(presentcard).trigger(presentcard.BIND_SUCCESS_EVENT,[data]);
						}else{
							$(presentcard).trigger(presentcard.BIND_ERROR_EVENT,[presentcard.msgMap[data.result]]);		
						}
					},
					error:function(xhr,status){
						$(presentcard).trigger(presentcard.BIND_ERROR_EVENT,[status]);
					},
					dataType:"jsonp"
				}); 
			}			
		};
	return presentcard;
});