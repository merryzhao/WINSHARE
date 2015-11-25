define(function(require){
	var $=require("jQuery"),
		SandboxEvent=require("../event/sandbox"),
		Config=require("../../etc/config"),
		Observable=require("../event/observable"),
		SecurityEvent=require("../event/security");
		
	function NoticeModel(){
		this.apiUrl=Config.apiServer+"/customer/notify";
		this.opt={
			format:"jsonp"
		};
		$.extend(this,Observable);
	};
	
	NoticeModel.ACTION={
		ARRIVAL_NOTICE:"arrivalNotice"
	};
	
	NoticeModel.prototype={
		arrivalNotice:function(list){
			var url=this.apiUrl+"/add",
				params=[],
				model=this;
				
				$.each(list,function(){
					params.push("p="+this);
				});
				params.push("callback=?");
				params.push("type=461003");
				params.push($.param(this.opt));
				
			$.ajax({
				url:url,
				data:params.join("&"),
				success:function(data){
					model.trigger({
						type:SandboxEvent.NOTIFY,
						action:NoticeModel.ACTION.ARRIVAL_NOTICE,
						eventData:{
							list:list,
							result:data
						}
					});
				},
				
				error:function(xhr){
					if(xhr.status==401){
						model.trigger({
							type:SecurityEvent.RESOURCES_PROTECTED
						});
					}					
				},
				
				dataType:"jsonp"			
			});		
		}	
	};
	return NoticeModel;
});
