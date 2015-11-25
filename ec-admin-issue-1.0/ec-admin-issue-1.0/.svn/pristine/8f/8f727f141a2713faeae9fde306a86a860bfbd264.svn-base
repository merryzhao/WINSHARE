$(document).ready(function() {
	$("#sbm").bind("click",function(){
		if($("#presentEndDateString").val().length!=0&&$("#presentEffectiveDay").val().length!=0){
			alert("截止期和有效期只能填写一个");
			return false;
		} 
		if($("#presentEndDateString").val().length==0&&$("#presentEffectiveDay").val().length==0){
			alert("截止期和有效期必须填写一个");
			return false;
		}
		});
		$("#isGeneral").val(0);
		$("input[bind='datepicker']").datepicker("option","minDate",new Date());
 });

