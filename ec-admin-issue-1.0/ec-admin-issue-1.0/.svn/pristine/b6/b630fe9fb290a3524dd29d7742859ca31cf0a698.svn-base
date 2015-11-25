$(function() {
	$("#promotion_error").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 350
	});
});
function success(){
	$("#content").html("<center><h4>操作促销活动成功!</h4></br><a href='/promotion/list'>返回列表页面</a></center>");
}
function fail(information){
	$("#promotion_error").html(information);
	$("#promotion_error").dialog("open");
}