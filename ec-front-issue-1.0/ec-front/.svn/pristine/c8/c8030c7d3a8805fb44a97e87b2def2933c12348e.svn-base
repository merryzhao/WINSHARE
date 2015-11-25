seajs.use(["jQuery","notify","ui","cart"],function($,notify,ui,cart){
	
	var el = $(".right_box");
	var checkBoxEl = el.find("table input[name='checkbox']");
	
	function getUrl(param,value){
		var url = window.location.search.substring(1).split("&");
		var reUrl = "";
		if(url != ''){
			for (var i=0; i<url.length; i++) {
				var p = url[i].split("=");
				if(p[0] != param){
					reUrl = reUrl == "" ? reUrl : reUrl + "&";
					reUrl = reUrl + p[0] + "=" + p[1]; 
				}
			};
		}
		reUrl = (reUrl == "" ? reUrl : reUrl + "&");
		reUrl = reUrl + param + "=" + value;
		return reUrl;
	};
	
	var view = {
		init:function(){
			seajs.use(["jQuery","toolkit"],function($,ToolKit){new ToolKit();});

			
			//批量添加到购物车
			el.find("a[bind='batchAddToCart']").live("click",function(){
				var pids = el.find("input[name='checkbox']"),
					param=[];
					pids.each(function(){
						var ell=$(this);
						if(ell.attr("checked")){
							param.push("p=" + ell.closest("tr").attr("productsaleid"));
						}
					});
				if(param.length == 0){
					alert("请选择要购买的商品");
					return;
				}
			});
			$(cart).bind(cart.BATCH_ADD_EVENT,function(e,data){view.favoriteToCart(data);});
			
			//删除通知
			el.find("a[bind='remove']").live("click",function(){
				
				var type = el.find("a[bind='removeBatch']").attr("name");
				if(type == "arrival"){
					type = 461003;
				}
				else if(type == "priceReduce"){
					type = 461004;
				}
				else {
					alert("错误");
					return;
				}
				var pid = $(this).closest("tr").attr("productsaleid");
				var param = [pid];
				notify.remove(param,this,type);
			});
			
			//批量删除通知
			el.find("a[bind='removeBatch']").live("click",function(){
				var type = $(this).attr("name");
				if(type == "arrival"){
					type = 461003;
				}
				else if(type == "priceReduce"){
					type = 461004;
				}
				else {
					alert("错误");
					return;
				}
				var pids = el.find("input[name='checkbox']"),
					param=[];
					pids.each(function(){
						var ell=$(this);
						if(ell.attr("checked")){
							param.push(ell.closest("tr").attr("productsaleid"));
						}
					});
				if(param.length == 0){
					alert("请选择要删除的通知");
					return;
				}
				notify.remove(param,this,type);
			});
			$(notify).bind(notify.REMOVE_EVENT,function(e,data){view.remove(data);});
			
			
			
			//排序
			el.find("select[bind='notifyOrder']").live("change",function(){
				var param = "order";
				var value = $(this).val();
				var url = getUrl(param , value);
				window.location = window.location.pathname + "?" + url;
			});
			
		},
		
		
		favoriteToCart:function(data){
			checkBoxEl.removeAttr("checked");
		},
		remove:function(data){
			if(data.status != 1){
				alert("删除失败");
			}
			else{
				el.find("tr input[name='checkbox']").removeAttr("checked");
				window.location.reload();
			}
		}
	};
	view.init();
});