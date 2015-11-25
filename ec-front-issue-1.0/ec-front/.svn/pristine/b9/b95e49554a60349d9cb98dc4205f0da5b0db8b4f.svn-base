seajs.use(["jQuery", "jQueryUI", "cart", "inputTip"], function($, ui, cart, inputTip){
	ui($);
	inputTip($);
	var action = {
		fav_buy: function(el){
			var productSaleId = $(el).data("id");// $(this).parent().parent().attr("productSaleId");
			cart.add(productSaleId);
			alert("已添加到购物车");
		},
		receiveOrder: function(el){
			var id = $(el).data("id");
			seajs.use(["feedback"],function(feedback){
				feedback.show({id:id, success:function(){
					$(el).after("<a class='link2' href='/customer/bought/"+id+"'>写评论</a>");
					$(el).remove();
				}});
			});
	    }, 
	    cancelOrder:function(el){
			if (!confirm("确认取消订单?")) {
	            return;
	        }
			var id=$(el).data("id");
	        var el = $("table").find("tr[attr='" + id + "']");
	        var controlEL = el.find("td[attr='control']");
	        controlEL.html("<img src='../images/loading.gif'/>");
	        $.get("/customer/order/" + id + "/cancel", {
	            format: "json"
	        }, function(data){
	            if (data.result == "1") {
	                refreshOrderRow(id, "取消成功!");
	            }
	            else {
	                controlEL.html(data.message);
	            }
	        });
		}
	};
	
	$("a[bind]").click(function(){
		action[$(this).attr("bind")]($(this));
	});
});