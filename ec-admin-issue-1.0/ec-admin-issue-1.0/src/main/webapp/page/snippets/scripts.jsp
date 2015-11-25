<div style="clear:both;"></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/console.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.form.min.js"></script>
<script type="text/javascript">
(function($){
	$(function(){
		$("input[bind='datetimepicker']").datetimepicker({
			regional:"zh-CN"	
		});	
		$("input[bind='datepicker']").datepicker({
			 regional:"zh-CN"
		});
		$("input[bind='mindatemepicker']").datepicker({
			minDate: new Date(),
			regional:"zh-CN"
		});	
		$("input[bind='mindate']").datepicker({
			minDate: new Date(),
			regional:"zh-CN"
		});	
		
		$("input[bind='greaterstart']").datepicker({
			minDate: $("input[bind='mindate']").val(),
			regional:"zh-CN"
		});	
	});
})(jQuery);
</script>