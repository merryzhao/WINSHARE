define(function(require){
var $=require("jQuery");
		
		function ShopSlide(cfg){

				this.opt={
					context:$("div.seller_ads_box"),
					speed:2000,
					isauto:true
				};
				$.extend(this.opt,cfg);
				this.init();
			};
			
			ShopSlide.prototype={
				init:function(){
					this.ads = this.opt.context.find("ul.seller_ads");
					this.adsli = this.ads.find("li");
				    this.adsno = this.opt.context.find("ul.ads_no li");
				    this.adsnoclass="current_ad";
				    this.bind();
		    	    this.interval();
				    
				},
				interval:function(){
					 if(!this.opt.isauto){return false};
					 var obj = this;
					 var count = this.adsli.length;
					 var sum=0;
				   window.setInterval(function(){
				   	   if(sum==count){
				   	   	sum =0;
				   	   }
				     	obj.load(obj.adsno.get(sum));	
				     	sum++;
				   },this.opt.speed);
					
				},
				
				bind:function(){
				    var obj = this;
					this.adsno.click(function(){obj.load(this)});
					
				},
				load:function(el){
					var el = $(el);
					this.changeState(el);
				},		
				changeState:function(el){	
					this.adsno.removeClass(this.adsnoclass);
					el.addClass(this.adsnoclass);
				    var index = el.index();
				    var adsimg = this.adsli.get(index);
				   // this.adsli.hide(500);
				   this.adsli.animate({
				   	opacity:'hide'
				   },10);
				    //$(adsimg).show(600);
				 $(adsimg).animate({opacity:'show'},500);
				
				}
				
			};
			
		return ShopSlide;	
});  
 