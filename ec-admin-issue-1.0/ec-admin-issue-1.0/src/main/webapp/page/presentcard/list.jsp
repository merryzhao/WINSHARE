<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>礼品卡列表</title>
<script type="text/javascript">
function setDisably(){
	$("#excelForm").submit();
	$("#sbm").attr("disabled","disabled");
}
</script>

</head>
<body>
<div id="content-result">
     <form action="/excel/printlist" method="post" target="ref" id="excelForm">
    <input type='hidden' name='format' value='xls' />
    <input type="button" value="确认打印" onclick="setDisably()" id="sbm">
    <input type="hidden" value="${printString}"  name="printString" id="printString"/>
    </form>
    <c:if test="${empty message }">
	<table class="list-table">
		<tr>
			<th>序号</th>
			<th>卡号</th>
			<th>类型</th>
			<th>状态</th>
			<th>有效期</th>
			<th>面额</th>
			<th>余额</th>
 		</tr>
		
		<c:forEach items="${cardList}" var="card" varStatus="index">
		<tr>
			<td>${index.count}</td>
			<td><a target="_parent" href="/presentcard/cardinfo/${card.id}">${card.id}</a></td>
			<td>${card.type.name}</td>
			<td>${card.status.name}</td> 
			<td><c:choose><c:when test="${card.endDate==null}">未设定</c:when><c:otherwise><fmt:formatDate value="${card.endDate }" type="both"/></c:otherwise></c:choose></td>
			<td><c:choose><c:when test="${card.denomination==null}">未设定</c:when><c:otherwise>${card.denomination}</c:otherwise></c:choose> </td>
			<td><c:choose><c:when test="${card.balance==null}">未设定</c:when><c:otherwise>${card.balance}</c:otherwise></c:choose> </td>
		</tr>
		</c:forEach>	 
	</table>
	</c:if>
	<b><br/>${message }</b>
	</div>
	<iframe id="ref" name="ref" style="display: none;"></iframe>
	<%@include file="../snippets/scripts.jsp"%>
	</body>
 </html>
