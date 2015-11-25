<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
td
{
    word-break:break-all;
} 
</style>
<div id="content-result">
<div class="showinfo">
<h4>订单跟踪信息</h4>
<ul><li ><button type="button" binding="newOrderTrack">新建订单跟踪</button></li></ul>
<c:if test="${fn:length(orderTrackList)==0}" >
	<script>
		$("#order_track").hide();
	</script>
</c:if>
	<table class="list-table" id="order_track" width="750px">
		<tr><th width="20%">时间</th><th width="20%">操作人</th><th width="20%">类型</th><th width="40%">备注</th></tr>
		<c:forEach var="orderTrack" items="${orderTrackList}" varStatus="status">
			<c:set var="rowStatus" value="odd"/>
			<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
			<tr class="${rowStatus}">
				<td ><fmt:formatDate type="both" value="${orderTrack.createTime}" /></td>
				<td >${orderTrack.employee.name}</td>
				<td >${orderTrack.type.name}</td>
				<td >${orderTrack.content}</td>
			</tr>
		</c:forEach>
	</table>
	
	
<div id="orderTrack-editor" >
		<c:set var="codeList" value="${code.children}"></c:set>
		<input type="hidden" name="orderid" id="clorderid" value=${order.id} />
			订单类型:
			<select name="typeId" id="cltypeId">
				<c:forEach var="codeitem" items="${codeList}" varStatus="status"><option value=${codeitem.id }>${codeitem.name}</option></c:forEach>
			</select><br>
			订单备注:<textarea name="content" style="width: 300px; height: 100px;" id="clcontent"></textarea><br>
			<button type="button" id="ordertrackButton" value="test">提交</button>
</div>
</div>
</div>
<script>
$(document).ready(function() {
		  		$("#orderTrack-editor").dialog({
		  			autoOpen:false,
		  			bgiframe:false,
		  			modal:true,
		  			width:350,
		  			hight:100
		  		});
});	
</script>