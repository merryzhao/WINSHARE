define(function(require){
	var $=require("jQuery"),
		Config=require("../../etc/config"),
		SandboxEvent=require("../event/sandbox"),
		Observable=require("../event/observable");
		
	
	function Model(){
		
		this.apiUrl=Config.passportServer;
		
		$.extend(this,Observable);
	};
	
	Model.ACTION={
		SIGNIN:"signin"
	};
	
	Model.prototype={
		
		signin:function(data){
			var url=this.apiUrl.replace("http:","https:")+"/verify?format=jsonp&callback=?",
				model=this;
			
			$.getJSON(url+"&"+$.param(data),function(data){
				model.trigger({
					type:SandboxEvent.NOTIFY,
					action:Model.ACTION.SIGNIN,
					eventData:data
				});
			});
		}
	};
	
	return Model; 
});