define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		points={
			EXCHANGE_SUCCESS_EVENT:"success",
			EXCHANGE_ERROR_EVENT:"error",
			msgMap:{
				"0":"没有查询到指定id的present exchange对应关系",
				"1":"兑换成功",
				"2":"服务器内部错误",
				"3":"你拥有的积分已经不够兑换相应的礼券"
			},
			exchange:function(id){
				var urlArray=[conf.portalServer+"/customer/points/exchange?format=jsonp"];
					urlArray.push("callback=?");
					urlArray.push("id="+id);
				$.ajax({
					url:urlArray.join("&"),
					success:function(data){
						if(data.result=="1"){
							$(points).trigger(points.EXCHANGE_SUCCESS_EVENT,[data]);
						}else{
							$(points).trigger(points.EXCHANGE_ERROR_EVENT,[points.msgMap[data.result]]);		
						}
					},
					error:function(xhr,status){
						$(points).trigger(points.EXCHANGE_ERROR_EVENT,[status]);
					},
					dataType:"jsonp"
				}); 
			}			
		};
	return points;
});