define(function(require){
	var 
		View=require("../view/count-down"),
		config=require("../etc/app-config"),
		AppEvent=require("../event/app"),
		AppModel = require("../model/app");


	function CountDown(){
		this.appModel = new AppModel();
		this.view=new View();
		this.isOver=false;
		this.bind();
	};

	CountDown.prototype={
		
		bind:function(){
			this.appModel.on(AppEvent.INIT_SUMMARY, function(e) {
				console.info(e.eventData)
			});
			
		},
		
		init:function(){
			var time=Date.parse(this.targetTime);
			if(!isNaN(time)){
				var distance=time-this.current;
				if(distance<0||isNaN(distance)){
					this.isOver=true;
				}else{
					this.distance=distance;
					this.run();
				}
			}else{
				throw new Error("Format error of targetTime in app-config.js");
			}

		},

		start:function(current){
			this.current=current.serverTime;
			if(current.targetTime){
				this.targetTime = current.targetTime;
				this.targetTime = this.targetTime.replace(/-/g, "/");
			}else{
				throw new Error("没有设定目标时间targetTime");
			}
			this.init();
			this.view.show();
		},

		calculate:function(){
			var result;
			if(!this.isOver&&this.distance>0){
				result=this.parse();
				this.view.days(result.days);
				this.view.hours(result.hours);
				this.view.minutes(result.minutes);
				this.view.seconds(result.seconds);
			}else{
				clearInterval(this.timer);
			}
		},

		parse:function(){
			var days,hours,minutes,seconds,
				remain;
			// 天数
			days=Math.floor(this.distance/(24*3600*1000));
			
			// 计算天数后剩余毫秒数
			remain=(this.distance%(24*3600*1000));
			// 小时
			hours=Math.floor(remain/(3600*1000));
			// 计算小时后剩余毫秒数
			remain=remain%(3600*1000);
			// 分钟
			minutes=Math.floor(remain/(60*1000));
			// 计算分钟后剩余毫秒数
			remain=remain%(60*1000);
			// 秒
			seconds=Math.round(remain/1000);
			return {
				days:days,
				hours:hours,
				minutes:minutes,
				seconds:seconds
			};
		},

		run:function(){
			var countDown=this;
			this.calculate();

			this.timer=setInterval(function(){
				countDown.distance=countDown.distance-1000;
				countDown.calculate();
			},1000);
		}
		
	};

	return CountDown;
});