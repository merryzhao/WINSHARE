<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet"/>
<title>联盟管理</title>
<style type="text/css">
.clcheckbox{ margin:0px;padding:0px;width:14px;}
input.availableyes{ margin-left: 22px}
input.availableno{margin-left: 8px}
input.usingapiyes{margin-left: 5px}
input.usingapino{margin-left: 8px}
label.error {
	padding:0.05em;
	color: red;
	font-weight: normal;
}
input.error {
	padding:0px;
	border: 1px solid red;
}
</style>
</head>
<body>
<div class="frame">
	<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
	<%@include file="../snippets/frame-left-channel.jsp"%>
	
<div class="frame-main">
 <div class="right_box">
     <div class ="ui-widget" >       
           <div class ="ui-widget-content">  
           <div id="content-result">
				<h4>查询条件 </h4>
				<form:form action="/union/unionOrderList"  method="post" id="unionOrderList" commandName="unionOrderForm" >
				    <table>
				         <tr ><td width="10%"></td><td width="15%" ></td><td width="10%"></td><td width="20%"></td></td><td width="15%"></td><td width="10%"></td><td width="30%"></td></tr>
				         <tr><td align="right">联盟名称：</td><td> <input type="text" name="name" style="width:120px" value="${fn:replace(selectedParameters['name'],'%','')}"/> 
				         
				             <td align="center">联盟标识：</td><td><input type="text" name="unionId" style="width:120px" value="${selectedParameters['unionId']}"/></td>
				              <td >  
				              <input type="hidden" name="isProduct" value="false"/>
				              <input type = "button" onclick ="query(false,false)" value="查  询"></td>
				                
				         </tr>
				         <tr><td align="right">下单起始时间：</td><td><input class ="starttime" name="startCreateTime"  bind="datepicker" style="width:120px" value="<fmt:formatDate value="${selectedParameters['startCreateDate']}" pattern="yyyy-MM-dd"/>"/>  ~
				         	 <td align="right">下单结束时间：</td><td><input class ="endTime" name="endCreateTime"  bind="datepicker" style="width:120px" value="<fmt:formatDate value="${selectedParameters['endCreateDate']}" pattern="yyyy-MM-dd"/>"/></td>
				         	  <td  align ="left">  <input type = "reset" value="重  置"></td></tr>				   
				         <tr>
				             <td  align ="right">  <input type = "button" onclick="query(true,false)" value="按订单导出"></td> 
				             <td  align ="left">  <input type = "button" onclick="query(true,true)" value="按商品导出"></td> 
				         </tr>
				    </table>
				</form:form>
           </div>
     </div>
     <div id="content-result">
     <c:choose>
     <c:when test="${!empty unionOrderList}">
     	<c:if test="${!empty pagination}">		   
			<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
	<table class="list-table" id="tree-table">
		<colgroup>
			<col class="orderId"/>
			<col class="createTime"/>
			<col class="unionName"/>
			<col class="unionId"/>
			<col class="createtime"/>
			<col class="purchaseQuantity"/>
			<col class="effectiveMoney"/>
		</colgroup>
		<tr><th>订单号</th><th>下单时间</th><th>联盟名称</th><th>标识</th><th>发货数量</th><th>有效金额</th></tr>
		<c:forEach var="unionOrder" items="${unionOrderList}" varStatus="status">
			<c:set var="rowStatus" value="odd"/>
			<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
			<tr class=" ${rowStatus}">
			    <td ><a href="${contextPath}/order/${unionOrder.order.id}">${unionOrder.order.id}</a></td>
			    <td ><fmt:formatDate value="${unionOrder.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
			    <td >${unionOrder.union.name}</td>
			    <td >${unionOrder.union.id}</td>
			    <td >${unionOrder.order.deliveryQuantity}</td>
			    <td >${unionOrder.order.effiveMoney}</td>
			</tr>
		</c:forEach>
		<tr><th>订单号</th><th>下单时间</th><th>联盟名称</th><th>标识</th><th>发货数量</th><th>有效金额</th></tr>
		</table>
		<c:if test="${!empty pagination}">		   
			<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
		</c:when>
		</c:choose>
     </div>
 </div>
<%@include file="../snippets/scripts.jsp" %>
<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/union/list.js"></script>
</div>
</div>
</div>
</body>
</html>