define(function(require){
	var $=require("jQuery").sub();
		require("../jQuery/plugin/lazyload")($),
		lazy={
			run:function(){
				$(function(){
					var fragments=$("[fragment]");
						fragments.mouseover(function(){
							var el=$(this);
								if(!el.data("loaded")){
									el.find("img").trigger("imgload");
									el.data("loaded","loaded");
								}
						});
						fragments.find("img:hidden").lazyload({
							event:"imgload"
						});
					var visible=$("img[data-lazy!='false']:visible");
						visible.lazyload();
						
				});
			}
		};
	return lazy;
});