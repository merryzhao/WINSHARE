/**
 * 
 * 购物车下方的组合框view
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define(function(require){
	
	var $=require("jQuery"),
		Observable=require("../event/observable"),
		CartEvent=require("../event/shoppingcart"),
		SecurityEvent=require("../event/security"),
		PaginationEvent=require("../event/pagination");
	
	/**
	 * 抽象化的Panel
	 * 组合框可放置各种Panel，此对象可扩展配置
	 */
	function Panel(){
		this.el=$('.panel');
		this.listEl=this.el.find('.list');
		this.pageEl=this.el.find('.page');
		this.resultKey="favoriteList";
		this.pageKey="pagination";
		this.loadingState=this.listEl.html();
		this.emptyState='<p class="empty">您的收藏中，没有找到任何商品！</p>';
		this.loginState='<p class="login">您还没有登录哟，点这里<a href="javascript:;" bind="login">登录文轩网</a></p>';
		this.init();
	};
	
	Panel.Template={
		ITEM:'<ul><li class="image"><a href="{url}" target="_blank"><img src="{imgUrl}"/></a></li><li class="title"><a href="{url}" target="_blank">{name}</a></li>'+
		'<li class="list-price">&yen;<em>{price}</em></li><li class="button"><button data-id="{productSaleId}" bind="add_to_cart"{state}><b>{buttonText}</b></button></li></ul>'
	};
	
	Panel.prototype={
		init:function(){
			$.extend(this,Observable);
			this.bind();	
		},
		
		bind:function(){
			var view=this;
			
			function delegate(eventType,el){
				view.trigger({type:eventType,source:el,eventData:$(el).data()});
			};
			
			this.el.delegate("[bind='add_to_cart']","click",function(){delegate(CartEvent.ADD,this)}).
			delegate("[bind='page_turning']","click",function(e){delegate(PaginationEvent.PAGE_TURNING,this)}).
			delegate("[bind='login']","click",function(e){delegate(SecurityEvent.RESOURCES_PROTECTED,this)}).
			delegate("[bind='add_to_cart']","mouseover",function(){
				if(!this.disabled){
					$(this).addClass("hover");
				}
			}).delegate("[bind='add_to_cart']","mouseout",function(){
				$(this).removeClass("hover");
			}).delegate("[bind='add_to_cart']","click",function(){
				var button=$(this);
				
				if(button.data("state")!="loading"){
					button.data("init-state-text",button.text());
					button.data("state","loading");
					button.html("<b>添加中...</b>");
				}
			});
		},
		
		render:function(item){
			var html,state=item.currentStatusCode==13002?"":" class='disabled' disabled='disabled'",
				buttonText=item.currentStatusCode==13002?"加入购物车":""||item.currentStatusCode==13001?"商品已下架":""||"商品已停用";
				html=Panel.Template.ITEM.replace(/\{url\}/g,item.url).
				replace(/\{imgUrl\}/g,item.imageUrl).
				replace(/\{name\}/g,item.name).
				replace(/\{sellName\}/g,item.sellName).
				replace(/\{price\}/g,item.salePrice.toFixed(2)).
				replace(/\{productSaleId\}/g,item.productSaleId).
				replace(/\{state\}/g,state).
				replace(/\{buttonText\}/g,buttonText);
			return html;
		},
		
		list:function(data){
			var list=data.result[this.resultKey],
				page=data.result[this.pageKey],
				html=[],
				panel=this;
			if((!!list)&&list.length!=0){
				$.each(list,function(){
					html.push(panel.render(this));
				});	
				this.renderPageBar(page);		
			}else{
				html.push(this.emptyState);
			}
			this.listEl.html(html.join(""));
		},
		
		renderPageBar:function(page){
			var html=["<p>"];
			for(var i=1;i<=page.pageCount;i++){
				if(i==page.currentPage){
					html.push('<a href="javascript:;" class="selected">'+i+'</a>');					
				}else{
					html.push('<a href="javascript:;" bind="page_turning" data-page="'+i+'">'+i+'</a>');
				}
			}
			html.push("</p>");
			this.pageEl.html(html.join(""));
		},
		
		changeState:function(state){
			if(state=="login"){
				this.listEl.html(this.loginState);
			}else{
				this.listEl.html(this.loadingState);
			}
		},
		
		restoreButton:function(id){
			var btn=this.el.find("button[data-id='"+id+"']");
			btn.data("state","normal");
			if(btn.data("init-state-text")){
				btn.html("<b>"+btn.data("init-state-text")+"</b>");				
			}else{
				btn.html("<b>加入购物车</b>");
			}
		}
		
	};
	
	function ComboView(){
		this.panels=[];
		this.el=$("#combo-tab");
		this.isShown=false;
		$.extend(this,Observable);
		this.init();
	};
	
	ComboView.PANEL={
		FAVORITE:"favorite",
		HISTORY:"history"
	};
	
	ComboView.prototype={
		
		init:function(){
			var cfg=this.el.data("config");
			if(!!cfg&&typeof(cfg)=="string"){
				cfg=eval("("+cfg+")");
			}else{
				cfg=cfg||{};
			}
			this.opt=$.extend({index:0,page:1,size:30},cfg);
			this.panels.push(new Panel());
			this.bind();
		},
		
		bind:function(){
			var fav=this.panels[0],
				view=this;
				
				fav.on(CartEvent.ADD,function(e){view.dispatch(e); }).
					on(PaginationEvent.PAGE_TURNING,function(e){view.dispatch(e);}).
					on(SecurityEvent.RESOURCES_PROTECTED,function(e){view.dispatch(e);});
		},
		
		getCurrentPanel:function(){
			return this.panels[0];
		},
		
		list:function(data){
			var panel=this.getCurrentPanel();
			panel.list(data);
		},
		
		changeState:function(state){
			var state=state||"login",
				panel=this.getCurrentPanel();

			panel.changeState(state);
			
		},
		
		dispatch:function(e){
			this.trigger(e);
		},
		
		append:function(){
			this.trigger({
				type:PaginationEvent.PAGE_TURNING,
				eventData:{
					page:1
				}
			});
		},
		
		show:function(){
			if(!this.isShown){
				this.el.show();				
			}
		},
		
		restoreButton:function(id){
			var panel=this.getCurrentPanel();
			panel.restoreButton(id);
		}
		
	};
	return ComboView;
});
