jQuery.validator.setDefaults({
    onfocusout : function(element) {
    	if($(element).hasClass("hasDatepicker")){
    		var validator = this;
    		$(element).change(function(){
    			validator.element(element);
    		});
    	}
    	this.element(element);    	       
    },
    focusCleanup : false,
    focusInvalid : false
});
