define(function(require){
	var $=require("jQuery");
	
	function table(cfg){
		if(window==this)return new table(cfg);
		this.opt={};
		$.extend(this.opt,cfg);
		this.init();
	};
	table.prototype={
		init:function(){
			//this.label=this.opt.context.find(this.opt.label);
			//this.content=this.opt.context.find(this.opt.content);
			this.bind();
		},
		bind:function(){
			var obj=this;
			this.opt.context.delegate(
				this.opt.label,
				"mouseover",
				function(){
					obj.over($(this));
			});
		},
		over:function(el){
			this.currentEl=el;
			this.toggle();				
		},
		toggle:function(){
			var obj=this,label,content,current;
			if(!this.timer){
				this.timer=setTimeout(function(){
						label=obj.opt.context.find(obj.opt.label);
						label.removeClass(obj.opt.className);
						obj.currentEl.addClass(obj.opt.className);
						content=obj.opt.context.find(obj.opt.content);
						content.hide();
						current=content.eq(label.index(obj.currentEl)).css({display:"block"}).show();
					if(!current.data("loaded")){
						current.find("img").trigger("imgload");
						current.data("loaded","loaded");
					}
					obj.timer=null;
				},100);
			}
		}
	};
	return table;
});
