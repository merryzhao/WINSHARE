/**
 * 
 * ShoppingCart 应用单元，应该不依赖基础库(像jQuery之类的)，只依赖模块
 * 
 * 这里直接返回新的ShoppingCartApp实例即可
 * 
 * html中或者脚本中直接通过
 * app.start()启动即可
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */

define(function(require,exports){
	
	/**
	 * import 相关依懒与java中类似，只是关键字不同，仅此而已
	 * 
	 */
	
    var CartView = require("../view/shoppingcart"),
		CartModel = require("../model/shoppingcart"),
		CartEvent = require("../event/shoppingcart"),
		SandboxEvent=require("../event/sandbox"),
		ProductEvent=require("../event/product"),
		SecurityEvent=require("../event/security"),
		NoticeModel=require("../model/product_notice"),
		
		ComboModule = require("../wml/shoppingcart-combo"),
		SigninModule = require("../wml/signin-signup");
	
	/**
	 * route常量定义
	 */
	var ROUTE={
		FAVORITE:"#favorite"
	};
	    
	/**
	 * 构造函数
	 */
	
    function CartApp(){
        this.view = new CartView;
        this.model = new CartModel;
		this.noticeModel=new NoticeModel;
        this.comboModule = new ComboModule(this.model);
        this.isMerged = false;
    };
    
	/**
	 * 原型声明
	 */
    CartApp.prototype = {
    		
		
		/**
		 * app 启动方法，初始化相关工作
		 */
		
        start: function(){
            if(!this.started){
				this.bindView();
	            this.bindModel();
	            this.comboModule.start();
				this.started=true;
			}
            return this;
        },
		
		/**
		 * 初始绑定视图，view对象直接proxy所有dom事件
		 */
		
        bindView: function(){
            var view = this.view, app = this;
            
            view.on(CartEvent.REMOVE_SELECT, function(e){app.removeSelect(e)}).
				on(CartEvent.REMOVE, function(e){app.remove(e)}).
				on(CartEvent.INCREASE, function(e){app.increase(e)}).
				on(CartEvent.REDUCE, function(e){app.reduce(e)}).
				on(CartEvent.FAVORITE_SELECT,function(e){app.addSelectedToFavorite(e)}).
				on(CartEvent.FAVORITE_ITEM,function(e){app.addToFavorite(e)}).
				on(CartEvent.UPDATE, function(e){app.update(e)}).
				on(CartEvent.RESTORE, function(e){app.restore(e)}).
				on(CartEvent.OPEN_FAVORITE, function(e){app.openFavorite(e);}).
				on(CartEvent.BALANCE, function(e){app.balance(e);}).
				on(CartEvent.MERGE, function(e){app.merge(e);}).
				on(ProductEvent.ARRIVAL_NOTICE, function(e){app.arrivalNotice(e);});
        },
		
		/**
		 * 
		 * 初始绑定模型事件，在v0.1版本中所有的model都认为是一个sandbox
		 * 
		 * 简化后的Sandbox 执行成功后只有一个事件哪就是 SandboxEvent.NOTIFY这事件
		 *  
		 */
		
        bindModel: function(){
            var model = this.model,
				noticeModel=this.noticeModel,
				app = this;
			
            model.on(SandboxEvent.NOTIFY, function(e){
                app.notify(e);
            });
			
			noticeModel.on(SandboxEvent.NOTIFY,function(e){
				app.notify(e);
			}).on(SecurityEvent.RESOURCES_PROTECTED,function(e){
				app.view.closeTip();
				app.showLogin();
			});
			
			
        },
		
		/**
		 * SandboxEvent.NOTIFY 事件回调方法
		 * 
		 * 使用类似Java中的反射原理，调用view中的方法进行数据处理
		 * 
		 * @param {Object} e
		 */
        notify: function(e){
            var method = this.view[e.action];
            if (typeof(method) == "function") {
                method.call(this.view, e.eventData);
            }
        },
		
		/**
		 * 删除选中的条目
		 * @param {Object} e
		 */
		
        removeSelect: function(e){
            var items = this.view.getSelectedItem(), app = this;
            /*app.view.lock();*/
            if (items.length > 0) {
                app.view.confirm({
                    dock: e.source,
                    message: "从购物车中删除选中的商品？",
                    confirm: function(){
                        app.view.showTip({
                            dock: e.source
                        });
                        app.model.remove(items);
                    }
                });
            }
            else {
                app.view.showTip({
                    dock: e.source,
                    message: "<p>请选择您要删除的商品！</p>",
                    delayClose: 3000
                });
            }
        },
		
		/**
		 * 增加商品数量
		 * @param {Object} e
		 */
        increase: function(e){
            var quantity = this.view.getItemQuantity(e.eventData.id);
            	quantity++;
            this.view.showTip({
                dock: this.view.getItemDock(e.eventData.id)
            });
            this.model.update(e.eventData.id, quantity, true);
        },
		
		/**
		 * 从购物车中移除商品
		 * 
		 * @param {Object} e
		 */
        remove: function(e){
            var app = this, dock = this.view.getItemDock(e.eventData.id);
            this.view.confirm({
                dock: dock,
                confirm: function(){
                    app.model.remove([e.eventData.id]);
                    app.view.showTip({
                        dock: dock
                    });
                },
                cancel: function(){
					if(dock.is(":input")){
							if (dock.val() != e.original) {
	                        dock.val(e.original);
	                        dock.data("value", e.original);
	                    }
					}
                }
            });
        },
		
		/**
		 * 减少商品数量
		 * @param {Object} e
		 */
		
        reduce: function(e){
            var app = this, original = this.view.getItemQuantity(e.eventData.id), quantity = original - 1;
            e.original = original;
            if (quantity <= 0) {
                app.remove(e);
            }
            else {
                this.view.showTip({
                    dock: this.view.getItemDock(e.eventData.id)
                });
                this.model.update(e.eventData.id, quantity, true);
            }
        },
		
		/**
		 * 更新商品数量为指定值
		 * @param {Object} e
		 */
		
        update: function(e){
            var app = this, quantity = app.view.getItemQuantity(e.eventData.id);
            if (isNaN(quantity)) {
                app.view.reset(e.eventData.id);
            }
            else {
                if (quantity != 0) {
                    app.view.showTip({
                        dock: this.view.getItemDock(e.eventData.id)
                    });
                    app.model.update(e.eventData.id, quantity, false);
                }
                else {
                    app.remove(e);
                }
            }
        },
		
		/**
		 * 
		 * 添加选定的商品到收藏
		 * 
		 * @param {Object} e
		 */
		
		addSelectedToFavorite:function(e){
			var app=this,
				items=this.view.getSelectedItem(),
				msg="";
			if(items.length>0){
				app.comboModule.show();
				app.comboModule.addToFavorite(items);
				if(app.comboModule.isLogin){
					msg="<p>"+items.length+"件商品已加入您的收藏</p>";
				}else{
					msg="<p>请先登录！</p>";
				}
				app.view.showTip({
					dock:e.source,
					message:msg,
					delayClose:3000
				});					
			}else{
				app.view.showTip({
					dock:e.source,
					message:"<p>请选择您要收藏的商品！</p>",
					delayClose:3000
				});
			}
		},
		
		/**
		 * 添加单个商品至收藏
		 * @param {Object} e
		 */
		
		addToFavorite:function(e){
			this.comboModule.show();
			if(e.eventData.id){
				this.comboModule.addToFavorite([e.eventData.id]);				
			}
		},
		
			
		/**
		 * 打开用户的收藏
		 * @param {Object} e
		 */
		
        openFavorite: function(e){
            this.comboModule.show();
        },
		
		
		/**
		 * 还原历史操作条目, 暂没做批量还原
		 * @param {Object} e
		 */
		
        restore: function(e){
            var app = this;
            app.view.showTip({
                dock: e.source
            });
            app.model.append(e.eventData.id,e.eventData.quantity);
        },
		
		next:function(){
			var url="http://"+document.domain+"/shoppingcart/select";
			try{
				open(url,"_self");
			}catch(e){
				document.location.href=url;
			}
		},
		
		arrivalNotice:function(e){
			var app=this,
				id=e.eventData.id;
			if(app.comboModule.isLogin){
				app.view.showTip({dock:e.source})
				app.noticeModel.arrivalNotice([id]);
			}else{
				new SigninModule({
					signin:function(){
						app.noticeModel.arrivalNotice([id]);
					}
				});
			}
		},
		
		showLogin:function(){
			new SigninModule();
		},
		
		/**
		 * 结算事件
		 */
		balance:function(e){
			var app=this;
			if(this.view.valid()){
				return false;	
			}
			/*这里暂时借用combo模块的登录判断*/
			if(this.comboModule.isLogin){
			    if(this.isMerged){
			        this.next();
			    }else{
                    this.model.query();
                    this.isMerged=true;
                }	
			}else{
				(new SigninModule({
					signin:function(){
					    app.comboModule.isLogin=true;
						app.model.query();
                        app.isMerged=true;
					},
					footer:"<p class='sunshine'>阳光阅读用户购买请<a href='http://www.winxuan.com/shoppingcart/select' target='_self'>点击这里</a></p><p class='quick'>暂不登录，我想快速购买：<a href='http://www.winxuan.com/shoppingcart/select' target='_self'>继续结算&gt;&gt;</a></p>",
					referrer:"http://www.winxuan.com/shoppingcart/select"
				})).show();
			}
		},
		
		/*
		 * 合并购物车
		 */
		merge:function(e){
		    this.model.merge(e.action);
		}
    };
    exports= new CartApp().start();
});
