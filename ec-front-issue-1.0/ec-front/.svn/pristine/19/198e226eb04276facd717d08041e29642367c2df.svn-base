define(function (require){
	var $=require("jQuery"),
		top=require("./top"),
		conf=require("config"),
		cart=require("cart"),
		Assister=require("searchAssister"),
		Menu=require("./category-menu"),
		header={
			baseUrl:conf.portalServer+'/customer/status?callback=?',
			selector:{
				header:"div.header",
				cartButton:"div.shop_cart",
				balance:"a[bind='gotoBalance']",
				count:"b.red",
				miniCart:"#nav_mini_cart"
			},
			getCount:function(){
				var cookies=document.cookie.split(";"),
					temp;
				for(var i=0;i<cookies.length;i++){
					temp=cookies[i].split("=");
					if($.trim(temp[0])=="sc"){
						return decodeURIComponent(temp[1]);	
					}
				}
				return 0;
			},
			init:function(){
				top.init();
				new Assister({isReload:false});
				new Menu();
				this.miniCart=$(this.selector.miniCart);
				this.content=this.miniCart.find("div.mini_cart");
				this.el=$(this.selector.header);
				this.cartButton=this.el.find(this.selector.cartButton);
				this.hotZone=this.cartButton.find("li:eq(1)");
				this.balance=this.el.find(this.selector.balance);
				this.count=this.el.find(this.selector.count);
				this.bind();
				this.cartButton.find("li b.red").html(this.getCount());
				this.hotKeyword();
				if(!$(document.body).data("lazyimg")){
					return;
				}else{
					seajs.use("lazyimg",function(lazy){
						lazy.run();
					});
				}
				this.loadOtherWidgets();
			},
			bind:function(){
				$(cart).bind(cart.UPDATE_EVENT,function(e,data){header.fill(data);});
				this.hotZone.click(header.showCart).mouseover(header.showCart);
				this.miniCart.mouseover(header.showCart);
				$(document).mouseover(header.hideCart);
				this.miniCart.delegate("a[bind='removeCartItem']",'click',function(){
					cart.remove($(this).attr("pid"));
				});
			},
			showCart:function(e){
				header.dock();
				if(!header.cartShowing){
					header.miniCart.fadeIn(200,function(){
						header.cartShowing=true;
					});
				}
				if(!header.loading){
					header.loading=true;
					header.reload();
				}
				e.stopPropagation();
			},
			dock:function(){
				var el=this.cartButton,offset=el.offset();
				this.miniCart.css({
					top:offset.top-26,
					left:580
				});
			},
			hideCart:function(){
				if(header.cartShowing){
					header.miniCart.fadeOut("slow",function(){
						header.cartShowing=false;
						if(header.loading){
							header.loading=false;
						}
					});
				}
			},
			fill:function(data){
				var cart=data.shoppingcart,
					html=[],index;
				if(data.shoppingcart&&data.shoppingcart.kind){
					index=cart.kind>4?4:cart.kind;
					html.push('<p class="txt_center"><a class="gray" href="'+conf.portalServer+'/shoppingcart" target="shoppingcart">共<b class="c2">'+cart.kind+'</b>种商品，查看全部商品</a></p>');
					html.push('<table width="300" border="0" cellspacing="0" cellpadding="0">');
					$.each(cart.itemList,function(i){
						html.push('<tr><td width="54" align="center"><a href="');
						html.push(this.url+'" title="'+this.name+'" target="_blank"><img src="'+this.imageUrl+'" alt="'+this.name+'"/></a></td>');
						html.push('<td width="147"><a href="'+this.url+'" title="'+this.name+'" target="_blank"/>'+this.name+'</a></td>');
						html.push('<td width="99" align="right"><b class="c2">￥'+this.salePrice.toFixed(2)+' x '+this.quantity+'</b>');
						html.push('<a href="javascript:;" bind="removeCartItem" pid="'+this.productSaleId+'">删除</a>');
						html.push("</td></tr>");
						if(i>=index){
							return false;
						}
					});
					html.push('</table>');
					html.push('<p class="txt_right">共<b class="c2">'+cart.kind+'</b>种商品 金额总计：<b class="c2">￥'+cart.salePrice.toFixed(2)+"</b></p>");
					html.push('<p class="p_but"><a class="settlement fr" href="'+conf.portalServer+'/shoppingcart" target="_blank">去购物车并结算</a><a class="shop_button" href="'+conf.portalServer+'/customer/favorite/addAllFromCart" target="_blank">全部收藏</a></p>');
					this.cartButton.find("li b.red").html(cart.count);
				}else{
					this.cartButton.find("li b.red").html(0);
					html.push('<p class="empty_tip">您的购物车是空的，赶快挑选心爱的商品吧！</p>');
				}
				this.content.html(html.join(""));
			},
			
			reload:function(){
				var data={format:"jsonp"};
				$.getJSON(this.baseUrl,data,function(data){
					header.fill(data)
				});
			},
			
			hotKeyword:function(){
				var url=conf.searchServer+"/searchHistory/hot.jsonp?callback=?",
					href=conf.searchServer+"/search?keyword=";
				$.getJSON(url,function(data){
					var html=[];
					if(data&&data.searchHot){
						$.each(data.searchHot,function(){
							html.push("<a href='"+href+encodeURIComponent(this)+"' target='_blank' title='"+this+"'>"+this+"</a>");
						});
					}
					if(html.length>0){
						$(html.join("")).appendTo(header.el.find("p.hot_search"));
					}
				});
			},
			loadOtherWidgets:function(){
				$(function(){
					if($(document).height()>1000){
						seajs.use("returnTop",function(returnTop){
							returnTop.init();
						});
					}
				});
			}
		};
	return header;
});
