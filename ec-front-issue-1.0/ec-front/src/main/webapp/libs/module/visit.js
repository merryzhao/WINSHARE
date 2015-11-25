define(function(require){
	var $=require("jQuery"),
		config=require("config"),
		base=config.portalServer+"/product",
		visit={
		ADD_EVENT : "add_event",
	    CLEAR_EVENT : "clear_event",
	    LOAD_EVENT:"load_event",
		ERROR_EVENT:"error",
		add:function(id,el){
			var url=[base+"/"];
				url.push(id);
				url.push("/visit?format=json&t="+new Date().getTime());
				$.ajax({
					url:url.join(""),
					success:function(data){
						$(visit).trigger(visit.LOAD_EVENT,[data,el]);
					},
					error:function(xhr,status){
						$(visit).trigger(visit.ERROR_EVENT);
					},
					dataType:"json"
				});
		},
		addall:function(el){
			$.ajax({
				url:"/product/visit/list?format=json&t="+new Date().getTime(),
				success:function(data){
					$(visit).trigger(visit.LOAD_EVENT,[data,el]);
				},
				error:function(xhr,status){
					$(visit).trigger(visit.ERROR_EVENT);
				},
				dataType:"json"
			});
		},
	    clear:function(){
			var url=[base];
				url.push("/cleanVisited?format=json&t="+new Date().getTime());
				$.ajax({
					url:url.join(""),
					success:function(data){
						$(visit).trigger(visit.CLEAR_EVENT,[data]);
					},
					error:function(xhr,status){
						$(visit).trigger(visit.ERROR_EVENT);
					},
					dataType:"json"
				});
	    }
	};
	return visit;
});		