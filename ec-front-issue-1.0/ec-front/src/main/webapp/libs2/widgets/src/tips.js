/**
 * 输入框提示
 */
define(function(require){
return function($){
	//tips
	$("a[bind=tips]").mouseover(function(e){
		var offset = $(this).offset();
		var tip = $(this).next(".tips");
		tip.show();
		tip.offset({top:offset.top, left:(offset.left - tip.width()/2)});
		tip.mouseleave(function(){
			tip.hide();
		});
	});
}
});