define(function(require){
	var 
		$=require("jQuery");

	function View(opt){
		this.cfg=$.extend({
			devOrderPanel:"aside .dev-order-succecc",
			successPanel:"aside .order-success",
			deliveryPanel:"aside .dev-success",
		
			curtainEl:"#curtain"
		},opt);
		this.degree = 0;
		this.audio = new Audio();
		
		this.init();
	
		
		
	};
	
	View.AUDIO_MESSAGE = {
		"COMPLETE" : "audio/style.mp3"
	};

	View.prototype={
		init:function(){
			this.successPanel=$(this.cfg.successPanel);
			this.successPanel.orderCount=this.successPanel.find(".count em");
			this.successPanel.orderPrice=this.successPanel.find(".price em");
			
			this.deliveryPanel=$(this.cfg.deliveryPanel);
			this.deliveryPanel.orderCount=this.deliveryPanel.find(".count em");
			this.deliveryPanel.orderPrice=this.deliveryPanel.find(".price em");
			this.deliveryPanel.surplus=this.deliveryPanel.find(".surplus em");
			this.deliveryPanel.today=this.deliveryPanel.find(".today em");
			
			
			this.devOrderPanel=$(this.cfg.devOrderPanel);
			this.devOrderPanel.orderCount=this.devOrderPanel.find(".count em");
			this.devOrderPanel.orderPrice=this.devOrderPanel.find(".price em");
			this.devOrderPanel.surplus=this.devOrderPanel.find(".surplus em");
			this.devOrderPanel.today=this.devOrderPanel.find(".today em");
			
			
			this.curtain=$(this.cfg.curtainEl);
			

		},
		success:function(data){
			return ;
			this.successPanel.orderCount.html(data.total);
            var count = Math.floor(data.price/1000000);
            // var _curtain = this.curtain;
            // var _audio = this.audio;
			if(count>0){
			   //console.info(count +","+this.degree)
			    if(count !=this.degree){
			
				 //this.curtain.show();
				 // window.setTimeout(function(){
				 	// _curtain.hide();
				 	// _audio.pause();
				 	// },60000)
				 }
				this.degree = count;	
				console.info(count +","+this.degree)	
			}
			var str = this.formatNumber(data.price.toString());
			this.successPanel.orderPrice.html("&yen;"+str);
		},
		delivery:function(data){
			  model = this;
			  
							
				this.deliveryPanel.orderCount.html("&yen;"+this.formatNumber(data.targetListprice.toString()));
				this.deliveryPanel.orderPrice.html("&yen;"+this.formatNumber(data.price.toString()));
			    this.deliveryPanel.surplus.html("&yen;"+this.formatNumber(data.surplus.toString()));
				this.deliveryPanel.today.html("&yen;"+	this.formatNumber(data.today.toString()));
				 var  devPrice = data.price;
				 this.count = Math.floor(data.price/1000000);
				 if(this.count>0&&this.degree>0){
				 	 if(this.count !=this.degree){
					 	$(this.audio).attr("src",View.AUDIO_MESSAGE.COMPLETE);
					 				this.audio.play();
									this.curtain.show();
							        this.audio.addEventListener('ended', function(){model.curtain.hide();},false);
					 }
				 }
				this.degree = this.count;
				
				
		},
		
		orderSummer:function(data){
			   this.devOrderPanel.orderCount.html(data.targetNum);
				this.devOrderPanel.orderPrice.html(data.devOrder);
			    this.devOrderPanel.surplus.html(data.surplusOrder);
				this.devOrderPanel.today.html(data.todayOrder);
		},
		
		formatNumber:function(s){
			 if(/[^0-9\.]/.test(s)) return "invalid value";
		        s=s.replace(/^(\d*)$/,"$1.");
		        s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
		        s=s.replace(".",",");
		        var re=/(\d)(\d{3},)/;
		        while(re.test(s))
		                s=s.replace(re,"$1,$2");
		        s=s.replace(/,(\d\d)$/,".$1");
		        s = s.replace(/^\./,"0.");
		        //削掉零头
		        s = s.replace(/\.\d+/,"");
		   return s
		}
	};

	return View;
});