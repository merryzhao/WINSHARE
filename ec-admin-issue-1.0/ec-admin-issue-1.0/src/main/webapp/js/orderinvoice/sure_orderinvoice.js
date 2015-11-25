$(document).ready(function() {
	$("#prestep").bind("click", function() {
		window.history.go(-1);
		});
	
	$("#submit").bind("click", function() {
		document.forms['invoiceForm'].submit();
		});
})