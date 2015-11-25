define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		cart=require("cart"),
		login=require("core/login"),
		wxBar={
			barEl:$("#wx-nav-bar"),
			welcome:$("#wx-nav-welcome"),
			miniCart:$("#mini-cart"),
			signBar:$("#wx-nav-signbar"),
			barCart:$("#wx-bar-shopping-cart"),
			signInfo:function(data){
				if(!!data.visitor){
					this.isLogin=data.isLogin;
					this.visitor=data.visitor||"";
					if(this.isLogin){
						this.welcome.html("您好，"+"<a href='"+conf.portalServer+"/customer"+"' bind='homePage'>"+data.visitor+"</a>&nbsp;&nbsp;<a href='http://passport.winxuan.com/logout' bind='logout'>退出登录</a>");	
					}else{
						this.welcome.html("您好，"+this.visitor+"欢迎光临文轩网！&nbsp;"+'<a href="http://passport.winxuan.com/login" title="登录">登录</a>&nbsp;&nbsp;<a href="http://passport.winxuan.com/register" title="免费注册">免费注册</a>');
					}
					this.signBar.hide();
				}
				this.init(data);
			},
			
			init:function(data){
				this.miniCart.html(this.fill(data));
				this.barCart.mouseover(function(){wxBar.miniCart.show();});
				this.miniCart.mouseout(function(){wxBar.miniCart.hide();});
				//this.barCart.mouseout(function(){wxBar.miniCart.hide();});
				this.barCart.click(function(){wxBar.miniCart.show();});
				this.barEl.find("*[bind]").live("click",function(e){
					var fn=wxBar[$(this).attr("bind")];
						if(fn)fn.call(this);
						e.preventDefault();
				});
				this.barEl.find("a[bind='logout']").live("click",function(e){
					var url=$(this).attr("href");
					$.getJSON(url+"?format=jsonp&callback=?",function(data){
						if(data.status=="1"){
							wxBar.welcome.html("您好，"+wxBar.visitor+"您已退出文轩网！&nbsp;"+'<a href="http://passport.winxuan.com/login" title="登录">登录</a>&nbsp;&nbsp;<a href="http://passport.winxuan.com/register" title="免费注册">免费注册</a>');
						}
					});
					e.preventDefault();
					e.stopPropagation();
					return false;
				});
			},
			
			fill:function(data){
				var html=[],item,cart,kind;
				html.push("<div class='cart-wrap'><div class='no-content'><p>您的购物车中没有商品<br/><a href='javascript:' bind='goToMall'>去活动卖场看看&gt;&gt;</a></p></div></div>");
				if(data&&data.shoppingcart&&data.shoppingcart.kind>0){
					cart=data.shoppingcart;
					kind=(cart.kind>3?3:cart.kind);
					$("#cart-count").html(cart.kind);
					html=["<div class='cart-wrap'><div class='mini-cart-head'>最近加入购物车的<span>"+kind+"</span>件商品</div><ul class='cart-list'>"];
					for(var i=0;i<kind;i++){
						item=cart.itemList[i];
						html.push("<li class='cart-item'><a class='product-img' href='"+item.url+"' title='"+item.name+"'><img src='"+item.imageUrl+"'/></a>");
						html.push("<div class='product-detail'><p><a href='"+item.url+"'>"+item.name+"</a></p><ul class='item-price-detail'><li class='item-Price'><span>￥"+item.salePrice.toFixed(2)+"</span>");
						html.push("</li><li class='item-quantity'>x "+item.quantity);
						html.push("</li><li class='item-delete'>[<a href='javascript:;' bind='removeItem' productSaleId='"+item.productSaleId+"'>删除</a>]</li></ul></div></li>");
					}
					html.push("</ul>");
					html.push("<div class='mini-cart-foot'><div class='cart-total'><b>购物车共<span>"+cart.kind+"</span>件商品 总计<span class='clblue'>￥"+cart.salePrice.toFixed(2)+"</span></b></div><div class='cart-balance'>");
					html.push("<button bind='goToCart'>立即去购物车结算</button><a href='"+conf.portalServer+"/shoppingcart' bind='goToCart'>查看购物车&gt;&gt;</a></div></div></div>");
				}
				return html.join("");
			},
			
			refresh:function(data){
				this.miniCart.html(this.fill(data));
			},
			
			removeItem:function(){
				var id=$(this).attr("productSaleId");
					$(cart).bind(cart.UPDATE_EVENT,function(event,data){
						wxBar.refresh(data);
					})
					cart.remove(id);
			},
			homePage:function(){
				try{
					var win=window.open(this.href,"_self");
					win.focus();
				}catch(e){}
			},
			goToMall:function(){
				alert("TODO");
			},
			goToCart:function(){
				try{
					var win=window.open(conf.portalServer+"/shoppingcart","shoppingcart");
					win.focus();
				}catch(e){}
			}
		};
		$(login).bind(login.LOGINED_EVENT,function(){
			cart.init();
		});
	return wxBar;
});
