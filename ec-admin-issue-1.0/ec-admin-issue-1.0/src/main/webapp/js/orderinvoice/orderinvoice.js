$(document).ready(function() {
	initArea($("#icountry").val(), $("#iprovince").val(), $("#icity").val(), $("#idistrict").val(), $("#itown").val());
	$("#cancle").bind("click", function() {
		window.document.location=$("#path").val()+"/orderinvoice/list" ;
		});
})