define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		base=conf.portalServer+"/shoppingcart/modify",
		format="format=jsonp",
		callback="callback=?",
		remove="qty=0",
		option="opt=update",
		updateEvent="cart-update",
		initEvent="cart-init",
		func=function(data){$(cart).trigger(updateEvent,[data]);},
		cart={
			INIT_EVENT:initEvent,
			UPDATE_EVENT:updateEvent,
			BATCH_ADD_EVENT:"batch-update-event",
			add:function(id,el){
				var url=[base+"?p="+id];
					url.push(format);
					url.push(callback);
				$.getJSON(url.join("&"),function(data){$(cart).trigger(cart.UPDATE_EVENT,[data,id,el]);});			
			},
			remove:function(id){
				var url=[base+"?p="+id];
					url.push(remove);
					url.push(option);
					url.push(format);
					url.push(callback);
				$.getJSON(url.join("&"),func);
			},
			update:function(id,quantity,el){
				var url=[base+"?p="+id];
					url.push("qty="+quantity);
					url.push(option);
					url.push(format);
					url.push(callback);
				$.getJSON(url.join("&"),function(data){$(cart).trigger(cart.UPDATE_EVENT,[data,id,el]);});
			},
			batchAdd:function(params,el){
				var url=[base+"?"+params.join("&")];
					url.push(format);
					url.push(callback);
				$.getJSON(url.join("&"),function(data){$(cart).trigger(cart.BATCH_ADD_EVENT,[data,el]);});
			},
			init:function(){
				$.getJSON(conf.portalServer+"/customer/status?format=jsonp&callback=?",function(data){
					$(cart).trigger(initEvent,[data]);
				});
			}
		};
		return cart;
});
