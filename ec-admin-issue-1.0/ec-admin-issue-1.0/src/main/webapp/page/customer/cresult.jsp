<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/scripts.jsp"%>
 <input type="hidden" name="error" id="error" value="${error}">
 <script type="text/javascript">
 $().ready(function(){
	 if($("#error").val().length!=0){
		 alert($("#error").val());
	 }
 })
 </script>
 