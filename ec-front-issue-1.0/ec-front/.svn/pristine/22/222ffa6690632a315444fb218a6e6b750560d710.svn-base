/**
 * 
 * 购物车view层定义
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define(function(require){
	
	/**
	 * import 依赖
	 */
	
	var $=require("jQuery"),
		Observable=require("../event/observable"),
		CartDataWrap=require("../model/cart-data-wrap"),
		CartEvent=require("../event/shoppingcart"),
		ProductEvent=require("../event/product"),
		UI=require("../util/ui");
		
	/**
	 * 确认小窗口，本次暂未将它单独成一个widget
	 * 暂时只有购物车中会使用它
	 * @param {Object} opt
	 */
		
	function ItemConfirm(opt){
		var defaultOpt={
			dock:null,
			title:"确认删除",
			message:"从购物车中删除此商品？",
			type:"dock",
			confirm:function(){},
			cancel:function(){}
		};
		this.opt=$.extend(defaultOpt,opt);
		this.init();
	};
	
	ItemConfirm.TEMPLATE={
		INIT:'<div class="cart-confirm"><div class="wrap {type}"><div class="title"><p>{title}</p><a hre="javascript:;" bind="close">Close</a>'+
		'</div><div class="msg"><p>{message}</p></div><div class="button"><button name="confirm">确认</button><button name="cancel">取消'+
		'</button></div></div></div>'
	};
	
	ItemConfirm.prototype={
		init:function(){
			var html=ItemConfirm.TEMPLATE.INIT;
				html=html.replace(/\{title\}/g,this.opt.title);
				html=html.replace(/\{message\}/g,this.opt.message);
				html=html.replace(/\{type\}/g,this.opt.type);
			this.el=$(html);
			this.el.appendTo(document.body);
			this.bind();
		},
		bind:function(){
			var confirm=this;
			this.el.find("[bind='close']").click(function(){
				confirm.close();
			});
			this.el.find("button[name='confirm']").click(function(){
				confirm.opt.confirm.apply(this);
				confirm.close();
			});
			this.el.find("button[name='cancel']").click(function(){
				confirm.opt.cancel.apply(this);
				confirm.close();
			});
		},
		show:function(){
			if(!this.opt.dock){
				throw new Error("The ItemConfirm need have a dock element")
			}
			var dock=$(this.opt.dock),
				offset=dock.offset();
			if(this.opt.type=="center"){
				this.el.css({
					top:offset.top+(dock.height()-this.el.height())/2,
					left:offset.left+(dock.width()-this.el.width())/2
				});
			}else{
				this.el.css({
					top:offset.top-this.el.height(),
					left:offset.left-this.el.width()/2+dock.width()/2
				});				
			}
			this.el.show();
		},
		close:function(){
			this.el.hide();
			this.el.remove();
		}
	};
	
	/**
	 * 与ItemConfirm类似
	 * @param {Object} opt
	 */
	function ItemTip(opt){
		var defaultOpt={
			dock:null,
			status:"init",
			message:'<em class="loading"></em>'
		};
		this.opt=$.extend(defaultOpt,opt);
		this.init();
	};
	
	ItemTip.TEMPLATE={
		INIT:'<div class="cart-tip"><div class="tip-wrap {status}"><div class="tip-msg">{msg}</div><p class="tip-footer">'+
		'<a href="javascript:;" bind="close">关闭</a></p></div></div>',
		SUCCESS:'<p>更新成功，共有<b>{count}</b>件商品</p><p>总计：<span>&yen;<em>{salePrice}</em></span></p>',
		UNAVAILABLE:'<p>库存数量不足！</p><p>可购买的数量为:<em>{avalibleQuantity}</em></p>',
		ERROR:'<p>未找到此商品或商品已停用！</p>',
		OFFSELF:'<p>此商品已下架！</p>',
		NUMBER:'<p>错误的商品数量！</p>',
		PARAM:'<p>参数错误！</p>',
		LIMIT:'<p>商品数量超出购买限制！</p>'
	};
		
	ItemTip.prototype={
		init:function(){
			var html=ItemTip.TEMPLATE.INIT;
			
			html=html.replace(/\{msg\}/g,this.opt.message);
			html=html.replace(/\{status\}/g,this.opt.status);
			
			this.el=$(html);
			//this.el.data("status",this.opt.status);
			this.el.appendTo(document.body);
			this.el.hide();
			this.bind();				
		},
		bind:function(){
			var tip=this;
			this.el.find("[bind='close']").click(function(){
				tip.close();
			});
		},
		
		show:function(){
			var dock=$(this.opt.dock||".layout"),
				offset=dock.offset();
			this.el.css({
				top:offset.top-this.el.height(),
				left:offset.left-this.el.width()/2+dock.width()/2
			});
			this.el.fadeIn("slow");
			if(!!this.opt.delayClose){
				var tip=this;
				setTimeout(function(){tip.close();},this.opt.delayClose);
			}
		},
		change:function(opt){
			$.extend(this.opt,opt);
			this.show();
		},
		close:function(){
			var tip=this
			this.el.fadeOut("slow",function(){
				tip.el.remove();
			});
		}
	};
	
	/**
     * 购物车合并窗口
     * @param {Object} opt
     */
    function MergeWindow(opt){
        var defaultOpt={
            dock:null,
            type:"center",
            title:"历史购物车",
            content:"<p>数据加载中……</p>",
            close:function(){},
            merge:function(){},
            addToFavor:function(){}
        };
        this.mask=UI.build("Mask");
        this.opt=$.extend(defaultOpt,opt);
        this.init();
    };
    MergeWindow.ACTION={
        CONFIRM:1,
        CANCEL:2,
        ADDTOFAVOR:3
    };
    MergeWindow.TEMPLATE={
        INIT:'<div class="widgets-window merge-window" id="mergeWin" style="display:none;">'+
             '<div class="widgets-title"><span>{title}</span><a href="javascript:;" bind="close">X</a></div>'+
             '<div class="widgets-content">{content}</div></div>',
        CONTENT:'<p class="tip"><b class="fb">温馨提示：</b>你曾经在购物车中加入过商品，是否与现在的商品合并？</p>'+
                '<p class="fb">共有<b class="red">{quantity}</b>件商品</p>'+
                '<div class="list"><ul class="booklist">{list}</ul></div>'+
                '<p class="operate"><button bind="merge">添加至购物车</button><button bind="addFavor">不，移入收藏</button></p>',
        ITEM:'<li><a href="{url}" target="_blank" title="{name}" class="img">'+
             '<img alt="{name}" src="{imageUrl}" class="fl"></a>'+
             '<p><a href="{url}" target="_blank" title="{sellName}">{sellName}</a><br/>'+
             '<b class="red fb">文轩价：￥{salePrice}</b><del>定价￥{listPrice}</del></p></li>'
    };
    MergeWindow.prototype={
        init:function(){
            var html=MergeWindow.TEMPLATE.INIT;
                html=html.replace(/\{title\}/g,this.opt.title);
                html=html.replace(/\{content\}/g,this.opt.content);
            this.el=$(html);
            this.el.appendTo(document.body);
            this.bind();
        },
        bind:function(){
            var merge=this;
            this.el.find("[bind='close']").click(function(){
                merge.opt.close.apply(this);
                merge.close();
            });
            this.el.find("button[bind='merge']").click(function(){
                merge.opt.merge.apply(this);
                merge.close();
            });
            this.el.find("button[bind='addFavor']").click(function(){
                merge.opt.addToFavor.apply(this);
                merge.close();
            });
        },
        show:function(){
            if(!this.opt.dock){
                throw new Error("The MergeWindow need have a dock element")
            }
            var dock=$(this.opt.dock),
                offset=dock.offset();
            if(this.opt.type=="center"){
                this.el.css({
                    top:offset.top+(dock.height()-this.el.height())/2,
                    left:offset.left+(dock.width()-this.el.width())/2
                });
            }else{
                this.el.css({
                    top:offset.top-this.el.height(),
                    left:offset.left-this.el.width()/2+dock.width()/2
                });             
            }
            this.mask.show();
            UI.center(this.el);
            this.el.show();
        },
        close:function(){
            this.el.hide();
            this.el.remove();
            this.mask.remove();
        }
    };
	/**
	 * 
	 * 删除历史对象
	 * 在这里只封装了相关DOM操作方法
	 * 如果需要对数据进行持久化操时，扩展此对象即可
	 * 
	 */
	
	function HistoryList(){
		this.el=$("div.history-list");
		this.isVisible=false;
		
		$.extend(this,Observable);
		
		this.init();
	};
	
	HistoryList.TEMPLATE={
		ROW:'<div class="trow" data-id="{id}"></div>',
		QUANTITY:'<div class="quantity">{quantity}</div>',
		OPERATION:'<div class="operation"><a href="javascript:;" bind="restore" data-id="{id}" data-quantity="{quantity}">重新购买</a> | <a href="javascript:;" bind="favorite" data-id="{id}">加入收藏</a></div>',
		OUT_OF_STOCK:'<div class="operation">商品已下架或停用 | <a href="javascript:;" bind="favorite" data-id="{id}">加入收藏</a></div>'
	};
	HistoryList.prototype={
		init:function(){
			this.bind();
		},
		bind:function(){
			var list=this;
			this.el.delegate("a[bind='restore']","click",function(){
				var row=$(this).parents(".trow"),
					id=row.data("id"),
					quantity=$(this).data("quantity");
				list.restore(id,quantity,this);
			}).delegate("a[bind='favorite']","click",function(){
				var row=$(this).parents(".trow");
					id=row.data("id");
				list.favorite(id,this);
			});
		},
		add:function(row){
			var product=row.find(".product").clone(),
				price=row.find(".price").clone(),
				credits=row.find(".credits").clone(),
				privilege=row.find(".privilege").clone(),
				input=row.find("input[name='quantity']"),
				quantity=input.val(),
				id=row.find("input[name='item']").val(),
				newRow,
				quantityEl,
				operationLink;
				
			newRow=$(HistoryList.TEMPLATE.ROW.replace(/\{id\}/g,id));
			if(!row.hasClass("out-of-stock")){
				quantityEl=$(HistoryList.TEMPLATE.QUANTITY.replace(/\{quantity\}/g,quantity));
				operationLink=$(HistoryList.TEMPLATE.OPERATION.replace(/\{id\}/g,id).replace(/\{quantity\}/g,quantity));
			}else{
				quantityEl=$(HistoryList.TEMPLATE.QUANTITY.replace(/\{quantity\}/g,"&nbsp;"));
				operationLink=$(HistoryList.TEMPLATE.OUT_OF_STOCK.replace(/\{id\}/g,id));	
			}
			
			newRow.hide();
			newRow.append(product).append(price).append(credits).append(privilege).append(quantityEl).append(operationLink);
			newRow.appendTo(this.el);
			newRow.fadeIn("slow");
			if(!this.isVisible){
				this.el.show("slow");
			}
		},
		remove:function(id){
			var list=this;
			this.el.find(".trow[data-id='"+id+"']").
			fadeOut("slow",function(){
				$(this).remove();
				var rows=list.el.find(".trow");
				if(rows.length==0){
					list.isVisible=false;
					list.el.hide("slow");
				}
			});
			
		},
		favorite:function(id,el){
			this.trigger({
				type:CartEvent.FAVORITE_ITEM,
				source:el,
				eventData:{
					id:id
				}
			});
		},
		restore:function(id,quantity,el){
			this.trigger({
				type:CartEvent.RESTORE,
				source:el,
				eventData:{
					id:id,
					quantity:quantity
				}
			});
		}
	};
	
	/**
	 * 
	 * 购物车列表视图，可继续细分为多个小的视图
	 * 
	 */
	function CartView(){
		
		this.isAllSelected=false;
		this.el=$("div#cart-list");
		this.context=$("body.cart");
		this.header=$("div.header");
		this.gobackButton=$("a.continue");
		this.balanceButton=$("a.balance");
		this.validTip=$("p.valid-tip");
		this.checkbox=this.context.find("input[type='checkbox']");
		this.history=new HistoryList();
		this.init();
		
	};
	
	CartView.TEMPLATE={
		ROW:'<div class="trow"><div class="checkbox"><input name="item" type="checkbox" value="{id}" {checked}/></div>'+
		'<div class="product"><a href="{url}" target="_blank"><img src="{imgUrl}"/></a>'+
		'<a href="{url}" target="_blank" title="{sellName}">{sellName}</a></div><div class="price">&yen;<em>{salePrice}</em></div>'+
		'<div class="credits">{points}</div><div class="privilege">&nbsp;</div>'+
		'<div class="quantity"><a href="javascript:;" title="减少" class="reduce" bind="reduce" data-id="{id}">减少</a>'+
		'<input name="quantity" value="{quantity}" data-value="{quantity}" data-id="{id}" bind="change"/>'+
		'<a href="javascript:;" title="增加" class="increase" bind="increase" data-id="{id}">增加</a></div>'+
		'<div class="operation"><a href="javascript:;" bind="remove" data-id="{id}">删除</a></div>'+
		'</div>',
		GROUP:'<div class="group" data-shop-id="{id}" data-supply="{supply}"><div class="shop"><b>商家:{name}</b><label class="{className}">新品预售</label><p class="prom-tag"></p></div></div>',
		GIFT:'<p>[赠品] {name} x {num}</p>',
		PROMOTION:'<span data-type="{type}"></span>',
		SAVE_PROMOTION:'<div class="proms"><p data-type="{type}"></p></div>'
	};
	
	CartView.prototype={
		
		init:function(){
			var view=this;
			if(view.isAllSelected){
				view.checkbox.attr("checked","checked");		
			}else{
				view.checkbox.removeAttr("checked");
			}
			
			$.extend(view,Observable);
			
			this.timer=setTimeout(function(){view.watch();},2000);
			this.bind();
			this.render();
		},
		
		/**
		 * 
		 * 渲染促销信息
		 * 
		 */
		
		render:function(){
			var proms=this.el.find(".proms p,.proms li");
				proms.each(function(){
					var el=$(this),
						data=el.data("prom-price");
					if(!!data){
						data=eval("("+data+")");
						el.addClass(CartDataWrap.PromTemplate[data.promType].style);
						el.html(CartDataWrap.getPromMessage(data));
						if(data.promType==20004){
							el.parents(".proms").addClass("save-label");
						}
					}			
				});
				proms.filter("li:first").addClass("first");
		},
		
		/**
		 * 
		 * 用户数量修改的监听器
		 * 当用户输入正确的数字时，监听器会触发更新事件
		 * 应用捕获此事件后，通知模型沙箱进行服务端处理
		 *  
		 */
		
		watch:function(){
			var view=this;
			this.el.find("input[name='quantity']").each(function(){
				var el=$(this),
					quantity=parseInt(el.val()),
					original=el.data("value");
				if(quantity!=original){
					if(!isNaN(quantity)){
						el.data("value",quantity);
						view.trigger({
							type:CartEvent.UPDATE,
							source:el,
							original:original,
							quantity:el.val(),
							eventData:el.data()
						});					
					}else{
						el.val(original);
					}
				}
			});
			this.timer=setTimeout(function(){view.watch();},1500);
		},
		
		bind:function(){
			var view=this;
			this.el.delegate("a[bind]","click",function(e){
				var el=this,event=$(this).attr("bind");
					event=event?event.toUpperCase():"";
				if(typeof(CartEvent[event])!="undefined"){
					view.trigger({
						type:CartEvent[event],
						source:el,
						eventData:$(el).data()
					});
				}
			});
			
			this.el.delegate("a[bind='arrival_notice']","click",function(){view.trigger({
				type:ProductEvent.ARRIVAL_NOTICE,
				source:this,
				eventData:$(this).data()
			})});
			this.checkbox.filter("[name='all']").click(function(){view.selectAll()});
			this.el.delegate("input[name='quantity']","paste",function(e){view.paste(e)}).
			delegate("input[name='quantity']","keypress",function(e){view.keypress(this,e)}).
			delegate("input[name='item'][type='checkbox']","click",function(e){view.selectItem(this)});
			this.gobackButton.click(function(){view.goback();});
			this.balanceButton.click(function(){view.balance(this);});
			
			this.history.on(CartEvent.RESTORE,function(e){view.trigger(e);}).
				on(CartEvent.FAVORITE_ITEM,function(e){view.trigger(e);});
		},
		
		goback:function(){
			var url=document.referrer;
			if (url.length > 0 && url.indexOf("/customer") == -1 && url.indexOf("/shoppingcart") == -1) {
                window.location = url;
            }else {
                window.location = "http://www.winxuan.com";
            }
		},
		
		balance:function(el){
			this.trigger({
				type:CartEvent.BALANCE,
				source:el
			});
		},
		
		/**
		 * 
		 * 监听用户输入事件,如果用户输入的数量不是数字，是其它字符时忽略掉当前操作
		 * 
		 * @param {Object} el
		 * @param {Object} e
		 */
		
		keypress:function(el,e){
			var view=this,el=$(el),
				code=e.charCode||e.keyCode,
				isDigit=(code>=48&&code<=57);
			if(!isDigit){
				if(e.keyCode==13&&el.val()!=el.data("value")){
					el.data("value",el.val());
					setTimeout(function(){
						view.trigger({
							type:CartEvent.UPDATE,
							source:el,
							quantity:$(el).val(),
							eventData:$(el).data()
						});
					},1);
				}else if(e.charCode==0){
					return;
				}else{
					e.preventDefault();
					e.stopPropagation();
					return;
				}
			}else{
				clearTimeout(this.timer);
				this.timer=setTimeout(function(){view.watch();},1500);
			}
		},
		
		/**
		 * 针对IE下的粘贴事件，这里此方法可忽略
		 * 意义不大  
		 * @param {Object} e
		 */
		
		paste:function(e){
			var isDigit=true;
			if(!!window.clipboardData){
				isDigit=window.clipboardData.getData('text').match(/\D/);
			}
			if(!isDigit){
				e.preventDefault();
				e.stopPropagation();
			}			
		},
		
		/**
		 * DOM层全选操作
		 */
		
		selectAll:function(){
			this.isAllSelected=!this.isAllSelected;
			if(this.isAllSelected){
				this.context.find("input[type='checkbox']:not(:disabled)").attr("checked","checked");		
			}else{
				this.context.find("input[type='checkbox']:not(:disabled)").removeAttr("checked");
			}			
		},
		
		selectItem:function(el){
			if(!el.checked){
				this.isAllSelected=false;
				this.checkbox.filter("[name='all']").removeAttr("checked");
			}
		},
		
		getSelectedItem:function(){
			var items=[];
			this.el.find("input[type='checkbox']").filter("[name='item']:checked").each(function(){
				var id=$(this).val();
				if(!!id){
					items.push(id)					
				}
			});
			return items;			
		},
		
		getItemQuantity:function(id){
			var input=this.el.find("input[name='quantity'][data-id='"+id+"']");
			return parseInt(input.val());
		},
		
		getItemDock:function(id){
			 var dock=this.el.find("input[name='quantity'][data-id='"+id+"']");
			 if(dock.length==0){
			 	dock=this.el.find("div.quantity em[data-id='"+id+"']");
			 }
			 if(dock.length>0){
			 	return dock;	
			 }
			 return null;
		},
		
		remove:function(data){
			var view=this;
			if(data&&data.itemList){
				if(data.itemList.length>1){
					view.closeTip();
					$.each(data.itemList,function(){
						view.removeItemRow(this);
					});
				}else{
					view.closeTip();
					view.removeItemRow(data.itemList[0])
				}
				view.refresh(new CartDataWrap(data));	
			}else{
				throw new Error("shoppingcart view remove method-Todo:fix this bug!");
			}
		},
		
		removeItemRow:function(id){
			var dock=this.getItemDock(id),
				row=dock.parents(".trow"),
				next=row.next(),
				view=this,
				group=row.parents(".group");
			if(next.hasClass("gift")){
				next.fadeOut("slow");
			}
			row.fadeOut("slow",function(){
				view.history.add(row);
				row.remove();
				if (group.find(".trow").length == 0) {
					group.fadeOut("slow", function(){
						group.remove();
						view.checkGroup();
					});
				}				
			});
			this.validTip.fadeOut();
		},
		
		update:function(data){
			var data=new CartDataWrap(data,ItemTip.TEMPLATE),
				el=this.getItemDock(data.id),
				msg=data.getMessage(),
				status=data.getStatusString(),
				row=el.parents(".trow");
			if(data.getStatusInt()===1){
				if(data.refresh){
					el.val(data.getUpdateItem().quantity);
					el.data("value",data.getUpdateItem().quantity);
				};
			}else{
				el.val(data.getAvailable());
				el.data("value",data.getAvailable());
			}
			this.showTip({dock:el,status:status,message:msg,delayClose:3000});
			this.refresh(data,row);
		},
		
		checkGroup:function(){
			var group=this.el.find(".group");
			
			if(group.length>1){
				this.context.find("div.progress").addClass("split");
			}else{
				this.context.find("div.progress").removeClass("split");
			}
		},
		
		refresh:function(data,row){
			var brief=this.context.find(".brief"),
				count=this.context.find(".count");
				
			brief.find(".save em").html(data.getSaveMoney());
			brief.find("span b").html(data.getCount());
			brief.find(".price em").html(data.getBriefPrice());
			count.find(".price em").html(data.getCountPrice());
			
			if(!!row){
				row.find(".credits").html(data.getPoints());				
			}
			this.checkGroup();
			this.reloadPromotion(data);
		},
		
		reloadPromotion:function(data){
			var proms=data.getProms(),view=this;
			if(proms&&proms.length!==0){
				$.each(proms,function(){
					var group=view.el.find(".group[data-shop-id='"+this.shopId+"'][data-supply='"+this.supplyTypeCode+"']"),
						el=group.find(".proms [data-type='"+this.promotionPrice.promType+"']"),
						parent=el.parents(".proms");
						
						el.html(CartDataWrap.getPromMessage(this.promotionPrice))
						
					if(this.promotionPrice.promType==20004&&!parent.hasClass("save-label")){
						parent.addClass("save-label");
					}
					
				});
			}
		},
		
		showTip:function(opt){
			this.closeTip();
			this.itemTip=new ItemTip(opt);
			this.itemTip.show();
		},
		
		closeTip:function(){
			if(!!this.itemTip){
				this.itemTip.close();
				this.itemTip=null;
			}
		},
		
		reset:function(id){
			var el=this.el.find("input[name='quantity'][data-id='"+id+"']"),
				defaultValue=el.data("value");
			el.val(defaultValue);
		},
		
		confirm:function(opt){
			if(!!this.itemConfirm){
				this.itemConfirm.close();
				this.itemConfirm=null;
			}
			this.itemConfirm=new ItemConfirm(opt);
			this.itemConfirm.show(); 
		},
		
		renderRow:function(item){
			var html=CartView.TEMPLATE.ROW,checked='';
			
				if(this.isAllSelected){
					checked='checked="checked"'
				}
				
				html = html.replace(/\{checked\}/g,checked);
				html = html.replace(/\{id\}/g, item.productSaleId);
				html = html.replace(/\{sellName\}/g, item.sellName);
				html = html.replace(/\{imgUrl\}/g, item.imageUrl);
				html = html.replace(/\{salePrice\}/g, item.salePrice.toFixed(2));
				html = html.replace(/\{points\}/g, item.points);
				html = html.replace(/\{quantity\}/g, item.quantity);
				html = html.replace(/\{url\}/g, item.url);
				
				if (!!item.gifts&&item.gifts.length>0) {
					$.each(item.gifts,function(){
						html+=CartView.TEMPLATE.GIFT.replace(/\{name\}/g,this.giftName).replace(/\{num\}/g,this.sendNum);						
					});
				}
				
			return html;
		},
		
		buildGroup:function(data,item){
			var className=item.supplyTypeCode==13102?"new":"",
				group=$(CartView.TEMPLATE.GROUP.replace(/\{id\}/g, item.shop.id).
						replace(/\{supply\}/g, item.supplyTypeCode).
						replace(/\{name\}/g,item.shop.shopName).
						replace(/\{className\}/g,className)),
				proms=data.getProms(item.shop.id,item.supplyTypeCode),
				saveProm=null;
				$.each(proms,function(){
					var html,promPrice=this.promotionPrice;
					if(promPrice.promType!=20004&&promPrice.promType!=20008){
						html=CartView.TEMPLATE.PROMOTION.replace(/\{type\}/g,promPrice.promType);
						$(html).appendTo(group.find(".shop"));
					}else{
						if(promPrice.promType==20004){
							html=CartView.TEMPLATE.SAVE_PROMOTION.replace(/\{type\}/g,promPrice.promType);
							$(html).appendTo(group);
						}
					}
				});
			return group;	
		},
		
		arrivalNotice:function(data){
			var el,
				view=this,
				list=data.list;
			this.closeTip();
			$.each(list,function(){
				el=view.el.find("div.quantity em[data-id='"+this+"']");
				if(el.length>0){
					view.trigger({
						type:CartEvent.REMOVE,
						source:el[0],
						eventData:el.data()
					});
				}
			});
		},
		append:function(data){
			if(!!this.getItemDock(data.sourceId)){
				data.refresh=true;
				this.update(data);
				return
			}
			var data=new CartDataWrap(data,ItemTip.TEMPLATE),
				item=data.getUpdateItem(),
				view=this,
				html,row,el,wrap;
			if(data.getStatusInt()===1){
				if (!!item) {
					html=this.renderRow(item);
					row = $(html);
					var group = this.el.find("div.group[data-shop-id='" + item.shop.id + "'][data-supply='" + item.supplyTypeCode + "']");
					if (group.length == 0) {
						group = this.buildGroup(data,item);
						group.append(row);
						group.insertBefore(this.el.find(".tfoot"));
						wrap=group;
					}
					else {
						row.appendTo(group);
						wrap=row;
					}
					el=this.getItemDock(data.id);
					wrap.hide().fadeIn("slow",function(){
						view.history.remove(data.id);
					});
					view.showTip({dock:el,status:data.getStatusString(),message:data.getMessage(),delayClose:3000});
					view.refresh(data);
				}
				else {
					throw new Error("Can not found element by id:" + data.id);
				}
			}else{
				this.itemTip.change({status:data.getStatusString(),message:data.getMessage(),delayClose:3000});
			}
		},
		
		/*
		 * 判断是否需要合并购物车
		 */
		query:function(data){
		    var data=new CartDataWrap(data,MergeWindow.TEMPLATE),
                itemList=data.getHistoryList(),
		        view=this;
		    if(!!itemList&&itemList.length>0){
                this.showMergeWin({
                        dock:"body",
                        content:data.getContent(),
                        close:function(){
                            view.trigger({
                                type:CartEvent.MERGE,
                                action:MergeWindow.ACTION.CANCEL
                            });
                        },
                        merge:function(){
                            view.trigger({
                                type:CartEvent.MERGE,
                                action:MergeWindow.ACTION.CONFIRM
                            });
                        },
                        addToFavor:function(){
                            view.trigger({
                                type:CartEvent.MERGE,
                                action:MergeWindow.ACTION.ADDTOFAVOR
                            });
                        }
                        });
            }else{
                view.trigger({
                    type:CartEvent.BALANCE
                });
            }
		},
		
		/*
		 * 显示合并购物车窗口
		 */
		showMergeWin:function(opt){
            this.closeMergeWin();
            this.mergeWindow=new MergeWindow(opt);
            this.mergeWindow.show();
		},
		
		/*
         * 关闭合并购物车窗口
         */
		closeMergeWin:function(){
		    if(!!this.mergeWindow){
                this.mergeWindow.close();
                this.mergeWindow=null;
            }
		},
		
		/*
		 * 合并购物车
		 */
		merge:function(data){
		    this.closeMergeWin();
		    this.refresh(new CartDataWrap(data));
            this.trigger({
                type:CartEvent.BALANCE
            });
		},
		/*
		 *验证购物车数据有效性
		 */
		valid:function(){
			var unavailable = this.el.find(".out-of-stock").html();
				validTip = this.validTip;
			if(!!unavailable){
				validTip.fadeIn();
				return true;
			}
			return false;	
		}
	};
	return CartView;
});
