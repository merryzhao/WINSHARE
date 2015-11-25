define(function(require){
	var $=require("jQuery"),
		cart=require("cart"),
		login=require("login"),
		favorite=require("favorite"),
		ui=require("ui"),
		notify=require("notify"),
		conf=require("config"),
		msgMap={
			cart:{
				"1":'<div class="success">商品已成功添加到购物车！</div><p>购物车共有<b class="red fb">{count}</b>件商品，合计：<b class="red fb">￥{price}</b></p>',
				"2":"您所购买的商品不存在",
				"3":"您所购买的商品库存数量不足，系统已将商品的最大库存数更新至您的购物车",
				"4":"您所购买的商品已下架",
				"5":"错误的数量",
				"6":"参数缺省或输入数量超出系统限制",
				"7":"你购买的商品数量超出购买限制",
				"presell":'<div class="success">商品已成功添加到购物车！</div><p>该商品预计{date},从{region}发货</p>',
				"lowStocks":'<div class="success">商品已成功添加到购物车！</div><p>部份商品库存数量不能满足您的要求，请进入您的购物车中调整。</p>'
			},
			favorite:{
				"batch":'<div class="favorite-result"><p>{result}</p><p><a href="http://www.winxuan.com/customer/favorite" target="myfavorite" bind="viewFavorite">查看我的收藏</a></p></div>'
			}
		};
	
	function ToolKit(cfg){
		this.opt={
			button:{
				addToCart:"[bind='addToCart']",
				batchAddToCart:"[bind='batchAddToCart']",
				batchFavorite:"[bind='batchFavorite']",
				addToFavorite:"[bind='addToFavorite']",
				arrivalNotify:"[bind='arrivalNotify']",
				pricecutNotify:"[bind='pricecutNotify']",
				presell:"[bind='presell']"
			},
			context:$(document),
			domain:document.domain
		};
		this.buttons=[];
		$.extend(this.opt,cfg);
		this.init();
	};
	ToolKit.prototype={
		init:function(){
			this.find();
			this.bind();
		},
		find:function(){
			var context=this.opt.context.find("[bind]");
			for(var name in this.opt.button){
				this.merge(name,this.opt.button[name],context);
			}
		},
		merge:function(name,bind,context){
			var array=context.filter(bind)
				.data("action",name).
				toArray();
			this.buttons=$.merge(this.buttons,array);
		},
		bind:function(){
			var obj=this;
			$(this.buttons).click(function(e){
				var action=$(this).data("action");
				if(obj[action]){
					obj[action].apply(this,[obj]);
				}
				__ozclk();//99click监控
				e.preventDefault();
				e.stopPropagation();
				return false;
			});
			$(favorite).bind(favorite.ADD_EVENT,function(evn,data,el){
				obj.favoriteAddHandler(data,el);
			}).bind(favorite.TAG_EVENT,function(evn,data){
				obj.favoriteTagHandler(data);
			}).bind(favorite.LOGIN_EVENT,function(evn,id,el){
				favorite.hide();
				obj.login(el,obj.addToFavorite);}
			).bind(favorite.ADD_ITEMS_EVENT,function(e,data,el){
				obj.favoriteAddHandler(data,el,true);
			});
			$(cart).bind(cart.UPDATE_EVENT,function(e,data,id,el){obj.cartUpdate(data,el);}).
			bind(cart.BATCH_ADD_EVENT,function(e,data,el){obj.cartUpdate(data,el,true);});
			$(notify).bind(notify.ADD_EVENT,function(evn,data,type,el){
				obj.notifyAddHandler(data,type,el);
			}).bind(notify.LOGIN_EVENT,function(e,type,el){
				obj.notifyWin.close();
				if(type=="461003"){
					obj.login(el,obj.arrivalNotify);
				}else if(type=="461004"){
					obj.login(el,obj.pricecutNotify);
				}
			});
		},
		cartUpdate:function(data,el,isBatch){
			var message;
			if(!!$(el).data("type")){
				return;
			}
			if(this.cartWin&&el){
				var date=$(el).data("date");
				if(!date){
					this.normalUpdate(data,el,isBatch);
				}else{
					//message=msgMap.cart["presell"].replace(/{date}/g,date).replace(/{region}/g,"成都");
					message=msgMap.cart["1"].replace("{count}",data.shoppingcart.count).replace("{price}",data.shoppingcart.salePrice.toFixed(2));
					this.cartWin.change("success",message);
				}
			}else{
				//throw new Error("can't found the tip window or element!");
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
		},
		login:function(el,cb){
			var obj=this;
			login.callbackUrl="http://"+obj.opt.domain+"/wxdoor.jsp?callback=miniLoginWindowCallBack";
			window.miniLoginWindowCallBack=function(status,msg){
				login.callback(status,msg);
			};
			login.show(el);
			$(login).bind(login.LOGINED_EVENT,function(e){
				cb.apply(el,[obj]);
				$(login).unbind(e);
			});
		},
		favoriteTagHandler:function(data){
			if(data.status=="1"){
				favorite.hide();
			}else{
				throw new Error("TODO save favoriteTag has failed!");
			}
		},
		favoriteAddHandler:function(data,el,isBatch){
			var html=(!!isBatch)?msgMap.favorite.batch:favorite.template;
				html=html.replace("{result}",favorite.message[data.status]);
				html=html.replace("{productSaleId}",$(el).data("id"));
			if(!isBatch){
				var tags=[];
				$.each(data.recommends,function(){
					tags.push("<a href='javascript:;'>"+this+"</a>");
				});
				html=html.replace("{tags}",tags.join(","));
			}
			favorite.show(html,el);
		},
		notifyAddHandler:function(data,type,el){
			var html=notify.template[type];
			this.notifyWin.opt.dock=$(el);
			this.notifyWin.change("success",html);
		},
		addToCart:function(obj){
			var el=$(this),ref=el.data("ref"),num;
				obj.showCartTipWin(el);
			if(!!ref){
				num=$(ref).val();
				if(num.length==0||isNaN(num)||num<=0){
					num=1;
					$(ref).val(1);
				}
				cart.update(el.data("id"),num,el);
				return;
			}
			cart.add(el.data("id"),this);
		},
		batchAddToCart:function(obj){
			var el=$(this),
				params=[];
			obj.getBatchParams(el,params);
			if(params.length>0){
				obj.showCartTipWin(el);
				cart.batchAdd(params,el);				
			}
		},
		getBatchParams:function(el,params){
			if(el&&params){
				var items=el.data("items");
				if((!!items)&&items.length>0){
					this.opt.context.find(items).each(function(){
						params.push("p="+$(this).data("id"));
					});
				}else{
					throw new Error("Can't found bound items");
				}				
			}
		},
		showCartTipWin:function(el){
			var obj=this;
			if(!obj.cartWin||obj.cartWin.hasClosed){
				obj.cartWin=ui.tipWindow({
					width:"300",
					dock:el,
					callback:function(){
						try{
							window.open(conf.portalServer+"/shoppingcart","_blank");
							obj.cartWin.close();
						}catch(e){}
					}
				});
			}else{
				obj.cartWin.opt.dock=el;
				obj.cartWin.change("loading");
			}
		},
		addToFavorite:function(obj){
			var pid=$(this).data("id");
			favorite.add(pid,this);
		},
		batchFavorite:function(obj){
			var params=[],el=$(this);
			obj.getBatchParams(el,params);
			if(params.length>0){
				favorite.addItems(params,el);				
			}
		},
		arrivalNotify:function(obj){
			obj.showNotifyWin($(this));
			notify.add($(this).data("id"),"461003",this);
		},
		showNotifyWin:function(el){
			var obj=this;
			if(!obj.notifyWin||obj.notifyWin.hasClosed){
				obj.notifyWin=ui.tipWindow({
					width:"300",
					dock:el,
					button:"<div class='ui:button'><button class='button:continue'>确&nbsp;定</button></div>"
				});
			}else{
				obj.notifyWin.opt.dock=el;
				obj.notifyWin.change("loading");
			}
		},
		pricecutNotify:function(obj){
			obj.showNotifyWin($(this));
			notify.add($(this).data("id"),"461004",this);
		},
		presell:function(obj){
			obj.addToCart.apply(this,[obj]);
		}
	};	
	return ToolKit;
});