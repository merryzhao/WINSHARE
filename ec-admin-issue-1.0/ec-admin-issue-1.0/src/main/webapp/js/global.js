(function($){
	if(!window.WX){
		window.WX={};
	}
	var ns=window.WX;
	ns.ui={
		effects:{
			mouseOver:function(){$(this).addClass("hover")},
			mouseOut:function(){$(this).removeClass("hover")},
		},

		lock:function(){
			if(!this.lockTimer){
				this.lockTimer=setInterval(function(){
					var width=$(window).width();
						$("div.frame").css(width<990?{"width":"990"}:{"width":"auto"});
				});
			}
		}
	};
})(jQuery);
