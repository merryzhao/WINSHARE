seajs.use(["jQuery", "ui", "feedback","toolkit"],function($, ui, feedback,ToolKit){
	var shoppingcartStatus=1,productSaleName="",
		currentEl;
	$("a[bind='addItemsToCart']").click(function(){
		var url=$(this).attr("provider"),
			param={format:"json"};
		$.getJSON(url,param,function(data){
			var list=data.order.itemList,
				url="/shoppingcart/modify",
				product,result,params=[];
			$.each(list,function(){
				params.push("p="+this.productSale.id);
			});
			$.getJSON(url+"?"+params.join("&")+"&format=json",function(cart){
				showChartInfo(cart);
			});
		});
		currentEl=this;
		alert("已添加到购物车");
	});
	
	$("a[bind='feedback']").click(function(){
		var id = $(this).data("id");
		feedback.show({id:id, success:function(){
			$(el).remove();
		}});
	});
	
	seajs.use("favorite",function(favorite){
		var currentEl;
		$(favorite).bind(favorite.ADD_ITEMS_EVENT,function(e,data){
			if(data.status=="1"||data.status=="2"){
				var tip=$("#cart-tip");
				tip.html("<div class='content'><div class='message'><p>"+
				"订单中的商品已添加至您的收藏夹"+
				"</p><div><button>查看我的收藏</button><a href='javascript:;' class='close'>关闭</a></div></div></div>");
				if(currentEl){
					var el=$(currentEl);
						tip.css({
							"top":el.offset().top-el.height()-tip.height(),
							"left":el.offset().left+(el.width()/2)-(tip.width()/2)
						});
				}
				tip.find("button").click(function(){try{window.open("/customer/favorite","favorite");tip.hide();}catch(e){}});
				tip.find("a.close").click(function(){tip.hide();});
				tip.show();
			}
		});
		$("a[bind='addItemsToFavorite']").click(function(){
			var url=$(this).attr("provider");
				param={format:"json"};
			$.getJSON(url,param,function(data){
				var list=data.order.itemList,
					params=[];
				$.each(list,function(){
					params.push("p="+this.productSale.id);
				})
				favorite.addItems(params.join("&"));
			});
			currentEl=this;
			alert("已添加到收藏夹");
		});
	});
	var msg={
		"1":"商品已成功加入购物车,<br/>购物车商品合计: <b class='red fb'>￥{salePrice}</b>",
		"2":"商品:{name}不存在<br/>购物车中已过滤此商品，<br/>购物车商品合计:<b class='red fb'>￥ {salePrice}</b>",
		"3":"商品:{name}可用量不足<br/>购物车中已过滤此商品，<br/>购物车商品合计: <b class='red fb'>￥{salePrice}</b>",
		"4":"商品:{name}已下架<br/>购物车中已过滤此商品，<br/>购物车商品合计: <b class='red fb'>￥{salePrice}</b>",
		"5":"商品:{name} 数量错误<br/>购物车中已过滤此商品，<br/>购物车商品合计:<b class='red fb'>￥ {salePrice}</b>"
		};
	$("<div id='cart-tip'></div>").appendTo(document.body);
	function showChartInfo(result){
		var tip=$("#cart-tip");
		tip.html("<div class='content'><div class='message'><p>"+
		msg[shoppingcartStatus].replace("{name}",productSaleName).replace("{salePrice}",result.shoppingcart.salePrice.toFixed(2))+
		"</p><div><button>查看购物车</button><a href='javascript:;' class='close'>关闭</a></div></div></div>");
		if(currentEl){
			var el=$(currentEl);
				tip.css({
					"top":el.offset().top-el.height()-tip.height(),
					"left":el.offset().left+(el.width()/2)-(tip.width()/2)
				});
		}
		tip.find("button").click(function(){try{window.open("/shoppingcart","shoppingcart");tip.hide();}catch(e){}});
		tip.find("a.close").click(function(){tip.hide();});
		tip.show();
	};
	new ToolKit();
	
	
	//快递信息
	var logistics = function(){
		var self = this;
		var uihl;
		$.extend(this, {
			windowbox : '<div class="logisticswindow" style="width:600px; min-height:300px;*height:300px"><div class="widgets-title"><span>物流信息</span><a href="javascript:;" bind="close">X</a></div><div class="widgets-content" style="text-align:center;"></div></div>',
			noinfo : '<div style="padding:100px 0 0 0">温馨提示:&nbsp;订单发出后才可看到物流信息!</div>',
			errorStr : '<div style="padding:100px 0 0 0">你的信息不正确!</div>',
			logisticsinfo : "<div><table class='logisticsinfo'  cellpadding=0 cellspacing=0><tr><td width='150px'>配送方式:</td><td><span id='deliverytype'></span></td></tr><tr><td>&nbsp;&nbsp;承运商:</td><td><span id='deliverycompany'></span></td></tr><tr><td>交寄单号:</td><td><span id='deliverycode'></span></td></tr><tr><td>发货时间:</td><td><span id='deliverytime'></span></td></tr></table><table class='orderstatus' cellpadding=0 cellspacing=0><thead><tr><th>时间</th><th>状态</th></tr></thead><tbody><tr><td>1</td><td>1</td></tr></tbody></table></div>",
			init : function(){
				$("a[bind='noloistics']").click(self.noloistics);
				$("a[bind='showloistics']").click(self.showloistics);
			},
			noloistics : function(){
				self.showWindow(self.noinfo);
			},
			showloistics : function(){
				var lois = $("a[bind='showloistics']");
				var id = lois.data("id");
				var tmp = lois.data("loistics");
				var info = eval("(" + tmp + ")");
				var html = $(self.logisticsinfo);
				html.find("#deliverycode").html(info.dcode);
				html.find("#deliverytime").html(info.dtime);
				html.find("#deliverytype").html(info.dtype);
				html.find("#deliverycompany").html(info.dcompany);
				html.find(".orderstatus tbody").html("<tr><td colspan='2'>正在查询订单物流信息...</td></tr>");
				self.showWindow(html);
				
				var canshow = lois.data("canshow");
				if(!canshow){
					html.find(".orderstatus tbody").html("<tr><td colspan='2'>亲，您的订单已发货，你可以根据[<a href='http://www.winxuan.com/help/logistic_company.html' target='_blank'><b style='color:red'>快递查询入口</b></a>]中的地址进行查询!</td></tr>");
					return;
				}
				
				var url = "http://www.winxuan.com/customer/order/logisticsinfo/" + id;
				$.ajax({"url":url,"data":{format:"json"},"dataType":"json","type":"get","success":function(e){ 
					if(e.result != 1){
						html.find(".orderstatus tbody").html("<tr><td colspan='2'>亲，您的订单已发货，你可以根据[<a href='http://www.winxuan.com/help/logistic_company.html' target='_blank'><b style='color:red'>快递查询入口</b></a>]中的地址进行查询!</td></tr>");
						return;
					}
					if(!e || !e.order){
						html.find(".orderstatus tbody").html("<tr><td colspan='2'>亲，您的订单已发货，你可以根据[<a href='http://www.winxuan.com/help/logistic_company.html' target='_blank'><b style='color:red'>快递查询入口</b></a>]中的地址进行查询!</td></tr>");
						return;
					}
					if(!e.order.logisticsList){
						html.find(".orderstatus tbody").html("<tr><td colspan='2'>亲，您的订单已发货，你可以根据[<a href='http://www.winxuan.com/help/logistic_company.html' target='_blank'><b style='color:red'>快递查询入口</b></a>]中的地址进行查询!</td></tr>");
						return;
					}
					var tbody = "";
					for(var i in e.order.logisticsList){
						var logistics = e.order.logisticsList[i];
						tbody += "<tr><td>";
						tbody += logistics.time;
						tbody += "</td><td style='text-align:left'>";
						tbody += logistics.context;
						tbody += "</td></tr>";
					}
					html.find(".orderstatus tbody").html(tbody);
				}, error:function(e){
					html.find(".orderstatus tbody").html("<tr><td colspan='2'>亲，您的订单已发货，你可以根据[<a href='http://www.winxuan.com/help/logistic_company.html' target='_blank'><b style='color:red'>快递查询入口</b></a>]中的地址进行查询!</td></tr>");
				}});
			},
			showWindow: function(content){
				var wb = $(self.windowbox);
				var con = wb.find(".widgets-content");
				con.html(content);
				$("body").append(wb);
				uihl = ui.highlight({el:wb});
				self.bindclose();
			},
			bindclose: function(){
				$("a[bind='close']").click(function(){
					$(".logisticswindow").remove();
					if(uihl){
						uihl.close();
						uihl.remove();
					}
				});
			}
		});
	}
	var logi = new logistics();
	logi.init();
});
