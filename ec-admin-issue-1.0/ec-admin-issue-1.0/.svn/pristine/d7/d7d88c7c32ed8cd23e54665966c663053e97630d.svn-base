$(document).ready(function(){
	window.report = {
		savePackages :function (order,value,origin) {
			
			if(origin != $.trim(value)){
				$.post("/order/saveExtends", {'order':order, 'value':$.trim(value),'meta':1,'format':'json'});
			}
		},
		download : function(){
			var inputs = $("form[id=paginationForm]").find("input[type=hidden]");
			
			$(document.downloadForm).find("input[type=hidden][name!=format]").remove();
			$(document.downloadForm).append(inputs.clone());
			document.downloadForm.submit();
			
			
		}
	};
	
});