define(function(require){
	var $=require("jQuery"),
		FavoriteModel=require("../model/favorite-model"),
		CartEvent = require("../event/shoppingcart"),
		SandboxEvent=require("../event/sandbox"),
		SecurityEvent=require("../event/security"),
		PaginationEvent=require("../event/pagination"),
		SigninModule=require("../wml/signin-signup"),
		ComboView=require("../view/shoppingcart-combo");
		
	function ComboModule(cartModel,defPanel){
		this.cartModel=cartModel;
		this.comboView=new ComboView;
		this.initPanel=defPanel||ComboView.PANEL.FAVORITE;
		this.favModel=new FavoriteModel;
		this.isLogin=false;
		this.pageOpt={index:0,page:1,size:6};
		
	};
	
	ComboModule.prototype={
		
		bindView:function(){
			var view=this.comboView,
				app=this;
			view.on(CartEvent.ADD,function(e){app.addToCart(e);}).
			on(PaginationEvent.PAGE_TURNING,function(e){app.pageTurning(e);}).
			on(SecurityEvent.RESOURCES_PROTECTED,function(e){app.showLogin();});
		},
		
		bindModel:function(){
			var favModel=this.favModel,
				cartModel=this.cartModel,
				app=this;
			favModel.on(SecurityEvent.RESOURCES_PROTECTED,function(e){app.login(e)}).
			on(SandboxEvent.NOTIFY,function(e){app.notify(e);});
			cartModel.on(SandboxEvent.NOTIFY,function(e){app.restoreButton(e)});		
		},
		
		pageTurning:function(e){
			if(this.isLogin){
				var page=e.eventData.page;
				this.comboView.changeState("loading");
				this.favModel.list($.extend(this.pageOpt,{page:page}));
			}else{
				this.showLogin();
			}
		},
		
		showLogin:function(callback){
			var combo=this,
				callback=callback||(function(){combo.isLogin=true;combo.comboView.changeState("loading");combo.favModel.list();});
				/*
			SigninModule=combo.SigninModule;
			if(!SigninModule){
				seajs.use("wml/signin-signup",function(Module){
					SigninModule=combo.SigninModule=Module;
					(new SigninModule({signin:callback})).show();
				});
			}else{
				(new SigninModule({signin:callback})).show();
			}
			*/
			(new SigninModule({signin:callback})).show();
		},
		
		addToCart:function(e){
			var id=e.eventData.id;
			this.cartModel.append(id);
		},
		
		init:function(){
			this.bindView();
			this.bindModel();
			if(this.initPanel==ComboView.PANEL.FAVORITE){
				this.favModel.list();	
			}
			/*
			TODO init other Panel
			*/
		},
		
		addToFavorite:function(list){
			var combo=this;
			if(this.isLogin){
				this.comboView.changeState("loading");
				this.favModel.append(list);
			}else{
				this.showLogin(function(){
					combo.isLogin=true;
					combo.favModel.append(list);
				});
			}
		},
		
		
		start:function(){
			this.init();	
		},
		
		notify:function(e){
			var method=this.comboView[e.action];
			if(typeof(method)=="function"){
				method.call(this.comboView,e.eventData);
			}
			this.isLogin=true;		
		},
		
		login:function(e){
			this.isLogin=false;
			if(e.action==FavoriteModel.ACTION.APPEND){
				alert("TODO favorite append login");
			}else{
				this.comboView.changeState("login");
			}
		},
		
		restoreButton:function(e){
			var id=e.eventData.sourceId;
			this.comboView.restoreButton(id);
		},
		
		error:function(){
			throw new Error("TODO Handle this Error");
		},
		
		show:function(){
			this.comboView.show();
		}
	};
	return ComboModule;
});
