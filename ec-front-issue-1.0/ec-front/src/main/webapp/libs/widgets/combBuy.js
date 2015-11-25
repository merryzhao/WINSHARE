define(function(require){
	var $=require("jQuery"),
		cart=require("cart"),
		ui=require("ui"),
		conf=require("config"),
		msgMap={
			cart:{
				"1":'<div class="success">商品已成功添加到购物车！</div><p>购物车共有<b class="red fb">{count}</b>种商品，合计：<b class="red fb">￥{price}</b></p>',
				"2":"您所购买的商品不存在",
				"3":"您所购买的商品库存数量不足，系统已将商品的最大库存数更新至您的购物车",
				"4":"您所购买的商品已下架",
				"5":"错误的数量",
				"presell":'<div class="success">商品已成功添加到购物车！</div><p>该商品预计{date},从{region}发货</p>',
				"lowStocks":'<div class="success">商品已成功添加到购物车！</div><p>部份商品库存数量不能满足您的要求，请进入您的购物车中调整。</p>'
			},
			favorite:{
				"batch":'<div class="favorite-result"><p>{result}</p><p><a href="http://www.winxuan.com/customer/favorite" target="myfavorite" bind="viewFavorite">查看我的收藏</a></p></div>'
			}
		}
	function CombBuy(cfg){
		this.apiUrl=conf.portalServer+"/product";
		this.format="format=json";
		this.price=0;
		this.el=$("<div></div>");
		this.opt={
			productId:"",
			context:""
		};
		$.extend(this.opt,cfg);
	};
	CombBuy.TEMPLATE={
		LAYOUT:'<div class="comb_buy"><h4 class="fb">组合购买</h4><div class="master"></div><div class="suits"><ul></ul></div>'+
				'<div class="infos"><p>购买最佳组合</p><p>合计：<span class="red fb" id="total_price"></span></p>'+
				'<button class="comb_btn" data-type="comb">购买此组合</button></div></div><div class="cl"></div>',
		MASTER:'<a href="{url}" class="book_pic" target="_blank"><img id="master_img" src="{image}" /></a>'+
				'<a href="{url}" class="book_name link4" target="_blank">{name}</a><b></b>',
		ITEM:'<li><a href="{url}" class="book_pic" target="_blank"><img src="{image}" />'+
			'</a><a href="{url}" class="book_name link4" target="_blank">{name}</a>'+
			'<b><input type="checkbox" name="price" checked data-price="{price}" value="{id}" />'+
			'<span class="red fb">￥{price}</span></b></li>'
	};
	CombBuy.prototype={		
		init:function(){
			var comb=this;
			this.bind();
			this.render();
			$(cart).bind(cart.BATCH_ADD_EVENT,function(e,data,el){comb.cartUpdate(data,el,true);});
		},
		render:function(){
			var url=this.apiUrl+"/"+this.opt.productId+"/shoppingCombination?"+this.format,
				comb=this,
				ctx=$(comb.opt.context);
			ctx.after(this.el);
			$.getJSON(url,function(data){
				if(!!data.shoppingCombinations&&!!data.productSale){
					var comb_books=data.shoppingCombinations,
						master_book=data.productSale;
					comb.price=master_book.effectivePrice;
					comb.el.append(CombBuy.TEMPLATE.LAYOUT);
					comb.append(comb_books,master_book);
				}
			});
		},
		append:function(list,obj){
			var comb=this,mhtml,shtml="",
				master_html=CombBuy.TEMPLATE.MASTER,
				suits_html=CombBuy.TEMPLATE.ITEM;
			if(!!obj){
				mhtml=this.renderItem(obj,master_html,false);
				this.el.find("div.master").append(mhtml);
			}
			$.each(list,function(){
				comb.price+=this.effectivePrice;
				shtml+=comb.renderItem(this,suits_html,true);
			});
			this.el.find("ul").append(shtml);
			this.el.find("#total_price").html('￥'+comb.price.toFixed(2));
		},
		renderItem:function(item,html,flag){
			html = html.replace(/\{url\}/g, item.url);
			html = html.replace(/\{name\}/g, item.sellName);
			html = html.replace(/\{image\}/g, item.product.imageUrlFor110px);
			if(flag){
				html = html.replace(/\{price\}/g, item.effectivePrice.toFixed(2));
				html = html.replace(/\{id\}/g, item.id);
			}
			return html;
		},
		bind:function(){
			var comb=this;
			this.el.delegate("input[name='price'][type='checkbox']","click",function(e){comb.selectItem(this)});
			this.el.delegate("button.comb_btn","click",function(e){comb.batchAddToCart(this)});
		},
		selectItem:function(el){
			if(el.checked){
				this.price+=$(el).data('price');
			}else{
				this.price-=$(el).data('price');
			}
			this.el.find("#total_price").html('￥'+this.price.toFixed(2));
		},
		batchAddToCart:function(el){
			var ids=["p="+this.opt.productId];
			this.el.find("input[type='checkbox']").filter("[name='price']:checked").each(function(){
				var id=$(this).val();
				if(!!id){
					ids.push("p="+id);					
				}
			});
			this.showCartTipWin(el);
			cart.batchAdd(ids,el);
		},
		showCartTipWin:function(el){
			if(!this.cartWin||this.cartWin.hasClosed){
				this.cartWin=ui.tipWindow({
					width:"300",
					dock:el,
					callback:function(){
						try{
							window.open(conf.portalServer+"/shoppingcart","shoppingcart");
							this.cartWin.close();
						}catch(e){}
					}
				});
			}else{
				this.cartWin.opt.dock=el;
				this.cartWin.change("loading");
			}
		},
		cartUpdate:function(data,el,isBatch){
			var message;
			if(this.cartWin&&el){
				var date=$(el).data("date");
				if(!date){
					this.normalUpdate(data,el,isBatch);
				}else{
					message=msgMap.cart["presell"].replace(/{date}/g,date).replace(/{region}/g,"成都");
					this.cartWin.change("success",message);
				}
			}else{
				throw new Error("can't found the tip window or element!");
			}
		},
		normalUpdate:function(data,el,isBatch){
			var message;
			if(data.status=="1"){
				message=msgMap.cart["1"].replace("{count}",data.shoppingcart.count).replace("{price}",data.shoppingcart.salePrice.toFixed(2));
				this.cartWin.change("success",message);
			}else if(data.status=="3"){
				var ref=$(el).data("ref");
				if(!!ref){
					$(ref).val(data.avalibleQuantity);
				}
				if(isBatch){
					message=msgMap.cart["lowStocks"];						
				}else{
					message=msgMap.cart[data.status];
				}
				this.cartWin.change("success",message);
			}else{
				message=msgMap.cart[data.status]
				this.cartWin.change("error",message);
			}
		}
	}
	return function(cfg){
			return new CombBuy(cfg);
	};
});