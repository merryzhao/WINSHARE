define(function(require){
	var $=require("jQuery"),
	
	UI={
		center:function(target){
			var top=$(window).height()/2-target.height()/2+$(document).scrollTop(),
				left=$(window).width()/2-target.width()/2;
				
				top=top<0?0:top;
				left=left<0?0:left;

			target.css({top:top,left:left});
		},
		build:function(type){
			if(!!type){
				if(typeof(UI[type])!="undefined"){
					return new UI[type];
				}else{
					throw new Error('UI build method: can not found type - '+type);
				}		
			}else{
				throw new Error('UI build method: "type" can not be null!');
			}
		}
	};
	
	require("ui-base-css");
	
	
	UI.Mask=function(){
		this.el=$('<div class="ui:mask"><div>');
		this.isInDOM=false;
		this.init();
	};
	
	UI.Mask.prototype={
		init:function(){
			this.resize();
			this.bind();
		},
		
		resize:function(){
			var dh=$(document).height(),
				ww=$(window).width(),
				dw=$(document).width(),
				wh=$(window).height();
			if(dh<wh){
				this.el.css({height:wh});
			}else{
				this.el.css({height:dh});
			}
			if(dw<ww){
				this.el.css({width:ww});
			}else{
				this.el.css({width:dw});
			}
		},
		
		bind:function(){
		},
		
		showIn:function(target){
			var target=target||document.body;
			if(!this.isInDOM){
				this.el.appendTo(target);
				this.el.show();
				this.isInDOM=true;
			}else{
				this.resize();
				this.el.show();				
			}
		},
		
		show:function(){
			this.showIn(document.body);
		},
		
		remove:function(){
			this.el.remove();
		},
		
		hide:function(){
			this.el.hide();
		}
	};
	
	return UI;
});
