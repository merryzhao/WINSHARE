define(function(require){
	var 
		$=require("jQuery");

	function View(opt){
		this.cfg=$.extend({
			container:"dl.count-down",
			title:"距离11月20日24点"
		},opt);
		
		this.el=$(this.cfg.container);
		this.init();
	};

	View.prototype={
		init:function(){
			this.daysEl=this.el.find(".days");
			this.hoursEl=this.el.find(".hours");
			this.minutesEl=this.el.find(".minutes");
			this.secondsEl=this.el.find(".seconds");
			this.titleEl=this.el.find("dt");
			this.titleEl.html(this.cfg.title);
		},
		cover:function(num){
			if(num<10){
				return "0"+num;
			}
			return num;
		},
		days:function(days){
			if(!!days){
				this.daysEl.html(this.cover(days));
			}
		},
		hours:function(hours){
			if(!!hours){
				this.hoursEl.html(this.cover(hours));
			}
		},
		minutes:function(minutes){
			if(!!minutes){
				this.minutesEl.html(this.cover(minutes));
			}
		},
		seconds:function(seconds){
			if(!!seconds){
				this.secondsEl.html(this.cover(seconds));
			}
		},
		show:function(){
			this.el.removeClass("hidden").addClass("visible");
		}
	};

	return View;
});