define(function(require){
	var 
		CountDown=require("../mod/count-down"),
		View=require("../view/summary");

	function Summary(){
		this.countDown=new CountDown();
		this.view=new View();
		var setting = {
                renderTo: "dev-progressBar",
                direction: "v",
                width: 180,
                height: 413,
				highlight:"none"
					
             };
        this.progressBar = new JProgressBar(setting);
		setting.renderTo = "order-progressBar";
		this.orderProgressBar = new JProgressBar(setting);
		
	};

	Summary.prototype={

		init:function(data){
			if(data.serverTime){
				this.countDown.start(data);
			}
			
			//setting.renderTo = "order-progress-Bar";
		
			//this.reset(data);
			//this.refresh();
		},

	   reset:function(data){
	   	    model = this;
			this.success={
				total:data.orderStatistics.total,
				price:data.orderStatistics.listprice
			};
			this.delivery={
				total:data.orderStatistics.deliveryTotal,
				price:data.orderStatistics.deliveryListprice,
				surplus:data.orderStatistics.surplusprice,
				today:data.orderStatistics.todayPrice,
				targetListprice:data.orderStatistics.targetListprice
			};
			
			
		   this.orderdev={
				targetNum:data.orderStatistics.totalOrderNum,
				todayOrder:data.orderStatistics.todayOrderNum,
				surplusOrder:data.orderStatistics.surplusOrder,
				devOrder:data.orderStatistics.deliveryOrder
			}
			
		
			
			this.value =data.orderStatistics.deliveryListprice;
			this.maxvalue = data.orderStatistics.targetListprice;
			this.rate = (this.value/this.maxvalue*100).toFixed(2);
			
			this.progressBarData = {
                value: this.value,
                maxvalue: this.maxvalue,
				rate :  this.rate,
				text : "完成率:"+this.rate+"%"
            };
			
			this.value = this.orderdev.devOrder;
			this.maxvalue = this.orderdev.targetNum;
			this.rate = (this.value/this.maxvalue*100).toFixed(2);
			this.orderProgressBarData={
				value: this.value,
                maxvalue: this.maxvalue,
				rate :  this.rate,
				text : "完成率:"+this.rate+"%"
				
			};
		},

		refresh:function(){
			this.view.delivery(this.delivery);
			this.view.orderSummer(this.orderdev);
			this.progressBar.calculate(this.progressBarData);
			this.orderProgressBar.calculate(this.orderProgressBarData);
		},

		addToWait:function(item){
			//this.success.total++;
			//this.success.price=this.success.price+item.listprice;
			//this.refresh();
		},
		addToSent:function(item){
			//this.delivery.total++;
			//this.delivery.price=this.delivery.price+item.listprice;
			//tarti.on();
			//this.refresh();
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

	return Summary;
});