define(function(require){
	var $=require("jQuery"),
		cart=require("cart"),
		MsgMap={
			"0":"商品详情",
			"1":"商品评论",
			"2":"商品咨询",
			"3":"文轩评论",
			"4":"天猫评论"
		};
		
	function EventTrack(cfg){
		this.opt={
			trackbtn:[],
			tracktab:[],
			productId:"",
			context:$(document)
		};
		this.buttons=[];
		this.tabs=[];
		$.extend(this.opt,cfg);
	};
	EventTrack.prototype={		
		init:function(){
			this.find();
			this.bind();
		},
		find:function(){
			this.buttons=this.opt.context.find(this.opt.trackbtn.join(","));
			this.tabs=this.opt.context.find(this.opt.tracktab.join(","));
		},
		
		bind:function(){
			var tracker=this;
			$(this.buttons).click(function(e){tracker.timer(this,e)});
			$(this.tabs).click(function(e){tracker.send(this,e)});
			$(cart).bind(cart.UPDATE_EVENT,function(){tracker.update();});
		},
		
		timer:function(el,e){
			var el=$(el);
			if(el.attr("bind")=="addToCart"){
				this.startTime=new Date().getTime();
			}
		},
		
		send:function(el,e){
			var el=$(el),
			key = el.data("ga");
			if(key&&MsgMap[key]){
				_gaq.push(['_trackEvent','Tabs',MsgMap[key],'product/'+this.opt.productId]);
			}
		},
		
		update:function(){
			if(this.startTime){
				var endTime=new Date().getTime(),
					time=(endTime-this.startTime);
				_gaq.push(['_trackEvent','Buttons','AddToCart','product/'+this.opt.productId,time]);
			}
		}
	};
	
	return function(cfg){
			return new EventTrack(cfg);
	};
});