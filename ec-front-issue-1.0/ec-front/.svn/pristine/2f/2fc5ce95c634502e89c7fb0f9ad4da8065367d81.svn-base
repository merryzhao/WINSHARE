define(function(require){
	var $=require("jQuery");
	
	function pageTurn(cfg){
		if(window==this)return new pageTurn(cfg); 
		this.opt={
			context:$(document), // 默认上下文元素		
			auto:false,	
			selector:{
				container:"", // 整个组件的容器
				items:"",//触发偏移的元素
				movecontent:"",//偏移的内容
				arrow:"",//翻页箭头
				prev:"", // 上一页元素
				next:"" // 下一页元素
			}
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	
	pageTurn.prototype={
		init:function(){
			this.el=this.opt.context.find(this.opt.selector.container);
			this.items=this.el.find(this.opt.selector.items);
			this.movecontent=this.el.find(this.opt.selector.movecontent);
			this.bind();
			this.auto();
		},
		bind:function(){
			var obj=this;
			this.opt.context.delegate(this.opt.selector.next,"click",function(){obj.next();});
			this.opt.context.delegate(this.opt.selector.prev,"click",function(){obj.prev();});
			this.opt.context.delegate(this.opt.selector.arrow,"mouseover",function(){ clearInterval(obj.timer);});
			this.opt.context.delegate(this.opt.selector.arrow,"mouseout",function(){ obj.auto();});
			this.items.delegate(this.opt.selector.movecontent,"mouseover",function(){clearInterval(obj.timer);});
			this.items.delegate(this.opt.selector.movecontent,"mouseout",function(){obj.auto();});
		},
		refresh:function(index){
			this.el.hide();
			this.el.eq(index).fadeIn(1000);			
		},
		next:function(){
			var i=this.el.filter(":visible").index(this.opt.selector.container);
			if(i>=this.el.length-1){
				i=0;
			}else{
				i++;
			}
			this.refresh(i);
		},
		prev:function(){
			var i=this.el.filter(":visible").index(this.opt.selector.container);
			if(i==0){
				i=this.el.length-1;
			}else{
				i--;
			}
			this.refresh(i);
		},
		auto:function(){
			var obj=this,i=0;
			if(this.opt.auto){
			this.timer=setInterval(function(){
				if(i>=obj.el.length-1){
				i=0;
				}else{
				i++;
			}
			obj.el.hide();
			obj.el.eq(i).fadeIn(1000);	
			},3000);	
			}			
		}
	};
	return pageTurn;
});