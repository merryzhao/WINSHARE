define(function(require){
	var $=require("jQuery"),
		conf=require("config");
	
	function QRcode(cfg){
		var opt={
			client:"",
			selector:{
				androidUrl:"a.android_download",
				iosUrl:"a.apple_download"
			}
		};
		$.extend(this,opt,cfg);
		this.init();
	};
	QRcode.prototype={
		init:function(){
			this.getClient();
		},
		getClient:function(){
			var userAgent=navigator.userAgent.toLowerCase();
			if(this.client=="jiuyue"){
				if(userAgent.indexOf("android")>0){
                	window.location.href="http://126.am/oZSUr2";
	            }else if(userAgent.indexOf("iphone")>0){
	                window.location.href="http://126.am/dgfsde2";
	            }else if(userAgent.indexOf("ipad")>0){
	                window.location.href="http://126.am/sdfdffc";
	            }
			}else if(this.client=="wenxuan"){
				$(this.selector.androidUrl).attr("href",conf.androidAPK);
				$(this.selector.iosUrl).attr("href",conf.iosAPK);
				if(userAgent.indexOf("android")>0){
                	window.location.href=conf.androidAPK;
	            }else if(userAgent.indexOf("iphone")>0||userAgent.indexOf("ipad")>0){
	                window.location.href=conf.iosAPK;
	            }
			}
		}
	};
	return function(cfg){
		return new QRcode(cfg);
	};
});
