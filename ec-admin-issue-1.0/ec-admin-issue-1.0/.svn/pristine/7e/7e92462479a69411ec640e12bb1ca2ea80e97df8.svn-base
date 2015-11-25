	var View={
			selectDc:$("#dc"),
			inputDc:$("input[name='dc']"),
			init:function(){
				this.bind();
			},
	        bind:function(){
	        	mode = this;
	        	this.selectDc.change(function(){
	        		var dc = mode.selectDc.val();
	        	    mode.inputDc.val(dc);
	        		mode.detectionStock();
	        	});
	        },
			detectionStock:function(){
				var dc = this.inputDc.val();
				var data = Array();
				data.push("format=json");
				data.push("dc="+dc);
				if(productSaleList.length<1){
					return;
				}
				for(var i=0;i<productSaleList.length;i++){
					data.push("id="+productSaleList[i].id);
					var productStocks = productSaleList[i].productSaleStockVos;
					for(var j=0;j<productStocks.length;j++){
						data.push("itemdc="+productStocks[j].dc);
					}
				}
				$.ajax({
					type : 'get',
					url : '/product/minStock',
					dataType : 'json',
					data : data.join("&"),
					beforeSend:function(xhr){
						$("#complexQuantityInfo").html("计算中...");
					},
					error:function(xhr){
						$("#complexQuantityInfo").html(xhr);
					},
					success:function(d){
						var info = mode.renderMinStock(d.minStockList)
						$("#complexQuantityInfo").html(info);
					}
				})
			},
			renderMinStock:function(productStocks){
				var template = '可用量:{stock}';
				var result = template;
				var stockInfo = '';
				for ( var i = 0; i < productStocks.length; i++) {
					result = result.replace(/{stock}/g, productStocks[i].quantity);
					stockInfo += result;
					result = template;
				}
				return stockInfo;
			}
	};
	View.init();
