define(function(require){
	var $=require("jQuery"),
		cart=require("cart"),
		conf=require("config"),
		css=require("widgets.css"),
		favorite=require("favorite"),
		login=require("core/login"),
		wxBar=require("winxuan-bar");

	$("<div id='cart-widgets'></div>").appendTo(document.body);
	
	var widgets={
		el:$("#cart-widgets"),
		referenceEl:null,
		cartMessageMap:{
			"1":'<h6>商品已成功添加到购物车！</h6><p>购物车共有<span class="widget-count">X</span>种商品，合计：<span class="cart-price"></span></p><p><button bind="goToCart">去购物车结算</button><a class="close-link" href="javascript:;">继续逛逛&gt;&gt;</a></p>',
			"2":"<div class='message'>您所选的商品不存在</div>",
			"3":"<div class='message'>您所选的商品可用量不足</div>",
			"4":"<div class='message'>您所选的商品已下架</div>",
			"5":"<div class='message'>错误的数量</div>"
		},
		goToCart:function(){
			try{
				var win=window.open(conf.portalServer+"/shoppingcart","shoppingcart");
				win.focus();
			}catch(e){}
			widgets.el.hide();
		},
		
		showMessage:function(el,msg){
			this.el.find("div.widget-wrap").addClass("status-loading");
			this.el.find("div.content").html("<div class='loading'>"+msg+"</div>");
			this.referenceEl=el;
			this.locate();
			this.el.show();
		},
		
		addToCart:function(e){
			cart.add($(this).attr("productSaleId"));
			widgets.showMessage(this,"正在更新购物车,请稍候...");
		},
		
		locate:function(){
			if(!!this.referenceEl){
				var offset=$(this.referenceEl).offset();
				widgets.el.css({
					"top":offset.top-$(this.referenceEl).height()-widgets.el.height(),
					"left":offset.left-(widgets.el.width()/2)+$(this.referenceEl).width()/2
				});	
			}
		},
		
		addToFavorite:function(){
			var pid=$(this).attr("productSaleId");
				favorite.add(pid,this);
		},
		
		favoriteAddHandler:function(data,el){
			var html=favorite.template;
				html=html.replace("{result}",favorite.message[data.status]);
				html=html.replace("{productSaleId}",$(el).attr("productSaleId"));
			var tags=[];
				$.each(data.recommends,function(){
					tags.push("<a href='javascript:;'>"+this+"</a>");
				});
				html=html.replace("{tags}",tags.join(","));
			favorite.show(html,el);
		},
		favoriteTagHandler:function(data){
			if(data.status=="1"){
				favorite.hide();
			}else{
				throw new Error("TODO save favoriteTag failed!");
			}
		},
		
		update:function(data){
			var wrap=widgets.el.find("div.widget-wrap");
			wrap.removeClass("status-loading status-error");
			if(!!data){
				wrap.find("div.content").html(this.cartMessageMap[data.status]);
				if(data.status=="1"){
					wrap.find("span.widget-count").html(data.shoppingcart.kind);
					wrap.find("span.cart-price").html("￥"+data.shoppingcart.salePrice.toFixed(2));
				}
				this.locate();
			}
		},
		
		bind:function(){
			var el=this.el;
			el.find("a.close-btn").live('click',function(){widgets.el.hide();});
			el.find("a.close-link").live('click',function(){widgets.el.hide();});
			el.find("button[bind='goToCart']").live('click',this.goToCart);
			$("input.add_cart_but").click(this.addToCart);
			$("input.collection_but").click(this.addToFavorite);
		},
		
		init:function(){
			$(cart).bind(cart.INIT_EVENT,function(event,data){
				wxBar.signInfo(data);
			}).bind(cart.UPDATE_EVENT,function(event,data){
				wxBar.refresh(data);
				widgets.update(data);
			});
			$(favorite).bind(favorite.ADD_EVENT,function(event,data,el){
				widgets.favoriteAddHandler(data,el);
			}).bind(favorite.TAG_EVENT,function(event,data){
				widgets.favoriteTagHandler(data);
			}).bind(favorite.LOGIN_EVENT,function(event,id,el){
				favorite.hide();
				login.callbackUrl="http://search.winxuan.com/wxdoor.jsp?callback=miniLoginWindowCallBack";
				window.miniLoginWindowCallBack=function(status,msg){
					login.callback(status,msg);
				};
				login.show(el);
			});
			cart.init();
			this.el.html('<div class="widget-wrap"><div class="content"><h6>商品已成功添加到购物车！</h6><p>购物车共有<span class="widget-count">X</span>种商品，合计：<span class="cart-price"></span></p><p><button>去购物车结算</button><a class="close-link" href="javascript:;">继续逛逛&gt;&gt;</a></p></div><a class="close-btn" href="javascript:;">关闭</a></div>');
			this.bind();
		}
	};
	return widgets; 
});
