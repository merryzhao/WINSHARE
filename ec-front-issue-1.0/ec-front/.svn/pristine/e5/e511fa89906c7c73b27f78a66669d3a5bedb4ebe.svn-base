define(function(require){
	var $=require("jQuery");
	require("widgets-css");
	var template={
		confirm:'<div class="ui:confirm"><div class="ui:wrap"><div class="ui:title"><label></label><b>X</b></div><div class="ui:content"><p></p></div><div class="ui:button"><button name="ok" class="button:ok">确认</button><button name="cancel" class="button:cancel">取消</button></div></div></div>',
		overlay:'<div class="ui:overlay"><iframe></iframe></div>',
		highlight:'<div class="ui:highlight"></div>',
		loading:'<div class="ui:loading"></div>',
		cartButton:'<div class="ui:button"><button class="button:gotoCart">去购物车并结算</button><button class="button:continue">继续购物&gt;&gt;</button></div>',
		tip:'<div class="ui:tip"><div class="ui:wrap"><div class="ui:title"><label></label><b>X</b></div><div class="ui:content">aa</div></div></div>'
	},
	fn={
		locate:function(target,dock,direction){
			
			if((!!dock)&&dock.length>0){
				var offset=dock.offset(),
					top=offset.top-dock.height()-target.height(),
					left=offset.left-target.width()/2+dock.width()/2;
					if(direction=="left"){
						top=offset.top+dock.height()*2-target.height(),
				     	left=offset.left-target.width()-dock.width();
					}
					
					top=top<0?0:top;
					left=left<0?0:left;
					target.css({top:top,left:left});
			}else{
				fn.center(target);
			}
		},
		position:function(target,dock,direction){
			var dct=direction||"above",
				offset=dock.offset(),
				top,left;
				if(dct=="above"){
					top=offset.top-dock.height()-target.height(),
					left=offset.left-target.width()/2+dock.width()/2;
				
					top=top<0?0:top;
					left=left<0?0:left;
				}
				// TODO
			target.css({top:top,left:left});
		},
		center:function(target){
			var top=$(window).height()/2-target.height()/2+$(document).scrollTop(),
				left=$(window).width()/2-target.width()/2;
				
				top=top<0?0:top;
				left=left<0?0:left;

			target.css({top:top,left:left});
		},
		fullScreen:function(el){
			var dh=$(document).height(),
				width=$(window).width(),
				wh=$(window).height(),
				iframe=el.find("iframe");
			if(dh<wh){
				el.css({height:wh,width:width});
				iframe.css({height:wh,width:width});
			}else{
				el.css({height:dh,width:width});
				iframe.css({height:dh,width:width});
			}
		}
	};
	
	function Confirm(cfg){
		if(window==this)return new Confirm(cfg);
		this.defCfg={
			callback:function(){},
			cancel:function(){},
			width:200,
			height:"auto",
			title:"",
			message:"",
			buttons:{
				ok:"确认",
				cancel:"取消"
			},
			isOverlay:false,
			dock:null
		};
		
		this.init(cfg);
	};
	
	Confirm.prototype={
		init:function(cfg){
			if(typeof(cfg)=="string"){
				this.defCfg.message=cfg;
			}else{
				$.extend(this.defCfg,cfg);
			}
			this.show(this.defCfg);
		},
		show:function(cfg){
			this.el=$(template.confirm);
			this.el.find(".ui\\:title label").html(cfg.title);
			this.el.find(".ui\\:content p").html(cfg.message);
			this.initButton();
			this.el.find(".ui\\:wrap").css({
				width:cfg.width,
				height:cfg.height
			});
			this.el.appendTo(document.body);
			if(cfg.isOverlay){
				this.overlay=$(template.overlay);
				this.overlay.css({width:$(document).width(),height:$(document).height()});
				this.overlay.appendTo(document.body);
			}
			fn.locate(this.el,$(cfg.dock),'left');
			this.bind();
		},
		initButton:function(){
			var btns=this.defCfg.buttons;
			if(btns.ok){
				this.el.find("button[name='ok']").html(btns.ok);
			}else{
				this.el.find("button[name='ok']").remove();
			}
			if(btns.cancel){
				this.el.find("button[name='cancel']").html(btns.cancel);
			}else{
				this.el.find("button[name='cancel']").remove();
			}
		},
		bind:function(){
			var obj=this;
			this.el.find("button[name='ok']").click(function(){obj.submit();});
			this.el.find("button[name='cancel']").click(function(){obj.close();});
			this.el.find(".ui\\:title b").click(function(){obj.close();});
		},
		submit:function(){
			this.close();
			this.defCfg.callback();
		},
		close:function(){
			this.el.remove();
			if(this.overlay){
				this.overlay.remove();
			}
		}
	};
	
	function Highlight(cfg){
		if(window==this)return new Highlight(cfg);
		this.defCfg={
			overlay:true,
			el:null
		};
		this.init(cfg);
	};
	
	Highlight.prototype={
		init:function(cfg){
			this.cfg=$.extend(this.defCfg,cfg);
			if (!!this.cfg.el) {
				if(this.cfg.overlay){
					this.overlay=$(template.overlay);
					fn.fullScreen(this.overlay);
					this.overlay.hide();
					this.overlay.appendTo(document.body);
				}
				this.el=$(template.highlight);
				var el=this.cfg.el;
					el.css({margin:0,padding:0});
					el.appendTo(this.el);
				this.el.hide();
				this.el.appendTo(document.body);
				this.show();
			}
		},
		show:function(){
			if(this.overlay){
				fn.fullScreen(this.overlay);
				this.overlay.show();
			}
			fn.center(this.el);
			this.el.show();
		},
		close:function(){
			if(this.overlay)
				this.overlay.hide();
			this.el.hide();
		},
		remove:function(){
			this.overlay.remove();
			this.el.remove();
		}
	};
	
	function TipWindow(cfg){
		if(window==this)return new TipWindow(cfg);
		this.opt={
			isAjax:true,
			title:"提示",
			dock:null,
			loadingText:"正在加载...请稍后",
			button:template.cartButton,
			callback:function(){
				throw new Error("TODO add tip action");
			}
		}
		$.extend(this.opt,cfg);
		this.init();
	};
	TipWindow.prototype={
		init:function(){
			this.el=$(template.tip);
			this.title=this.el.find("div.ui\\:title");
			this.content=this.el.find("div.ui\\:content");
			this.title.find("label").html(this.opt.title);
			this.el.find(".ui\\:wrap").css({
				width:this.opt.width,
				height:this.opt.height
			});
			if(this.opt.isAjax){
				this.content.html("<div class='ui:loading'>"+this.opt.loadingText+"</div>");
			}
			this.el.appendTo(document.body);
			fn.locate(this.el,$(this.opt.dock));
			this.bind();
		},
		bind:function(){
			var obj=this;
			this.title.find("b").click(function(){
				obj.close();
			});
		},
		change:function(status,msg){
			var obj=this;
			if(status=="success"){
				this.content.html("<div class='ui:msg'>"+msg+"</div>");
				if(!this.button){
					this.button=$(this.opt.button);
					this.button.find("button.button\\:gotoCart").click(function(){
						obj.opt.callback();
					});
					this.button.find("button.button\\:continue").click(function(){
						obj.close();
					});
					this.button.appendTo(this.el.find("div.ui\\:wrap"));
				}else{
					this.button.show();
				}
			}else if(status=="error"){
				this.content.html("<div class='ui:error'>"+msg+"</div>");			
			}else if(status=="loading"){
				this.content.html("<div class='ui:loading'>"+this.opt.loadingText+"</div>");
				if(this.button){
					this.button.hide();
				}
			}
			fn.locate(this.el,$(this.opt.dock));
		},
		close:function(){
			this.el.remove();
			this.hasClosed=true;
		}
	};
	
	return {
		
		confirm:function(cfg){
			return Confirm(cfg);
		},
		
		highlight:function(cfg){
			return Highlight(cfg);
		},
		tipWindow:function(cfg){
			return TipWindow(cfg);
		}
	};
	
});
