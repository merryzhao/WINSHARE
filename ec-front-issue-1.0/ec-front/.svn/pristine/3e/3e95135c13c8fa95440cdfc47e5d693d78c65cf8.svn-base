/**
 * 输入框提示
 */
define(function(require){
return function($){
	$.fn.inputTip = function(tip){
		var isNull = true;
		var it = $(this);
		setStatic()
		
		it.val(tip);
		it.focusout(function(){
			isNull = $(this).val() != "" ? false : true;
			setStatic();
			if(isNull){
				$(this).val(tip);
			}
		});
		it.focusin(function(){
			isNull = $(this).val() != "" ? 
					$(this).val() != tip ? false : true 
					: true;
			setStatic()
			if(isNull){
				$(this).val("");
			}
		});
		it.parents("form").each(function(){
			$(this).submit(function(){
				it.val("");
			});
		});
		function setStatic(){
			it.data("isNull", isNull);
		}
	};
};
});