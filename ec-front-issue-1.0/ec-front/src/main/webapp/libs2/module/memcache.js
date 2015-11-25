define(function(require){
	var $ = require("jQuery"),
		conf=require("config");
	var view = {
		init:function(){
			this.clear();
		},
		clear:function(){
			var url = this.getUrl();
			this.action(url)
		},
		getUrl:function(){
          return window.location.href;
		},
		action:function(url){
			var actionurl = conf.portalServer+"/memcache/remove?format=json";
			$.post(actionurl,{url:url});
		}
		
	};
	return view;
});
