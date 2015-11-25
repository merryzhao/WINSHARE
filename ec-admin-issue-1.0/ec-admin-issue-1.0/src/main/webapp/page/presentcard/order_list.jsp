<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.winxuan.com/tag/page"  prefix="winxuan-page"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>礼品卡订单列表</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>
<div class="frame">
		<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
<%@include file="../snippets/frame-left-website.jsp"%>
<div class="frame-main">
			<!-- 核心内容部分div -->
<div class="frame-main-inner" id="content">
<div >
<c:if test="${!empty pagination}">		   
			<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
<table class="list-table" >  
<tr>		
    <th align="center">订单号</th>
    <th>订单状态</th>
    <th>支付状态</th>
    <th>支付方式</th>
    <th>配送方式</th>
    <th>真实姓名</th>
    <th>电话</th>
    <th>手机</th>
    <th>区域</th>
    <th>详细地址</th>
    <th>码洋</th>

</tr>
<c:forEach var="order" items="${orderList}" varStatus="status">
  <c:set var="rowStatus" value="odd"/>
  	<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
  <tr class="${rowStatus}">
    <td width ="100px" align="center"><a href="${contextPath}/order/${order.id }" target="_blank">${order.id}</a></td>
    <td width ="80px" align="center">${order.processStatus.name}</td>
     <td width ="80px" align="center">${order.paymentStatus.name}</td>
     <td width ="80px" align="center">${order.payType.name}</td>
	<td width ="80px" align="center">${order.deliveryType.name }</td>
    <td width ="80px">${order.consignee.consignee}</td>
    <td width ="100px" align="center">${order.consignee.phone}</td>
    <td width ="100px" align="center">${order.consignee.mobile}</td>
    <td width ="180px" align="center">${order.consignee.province.name} ${order.consignee.city.name} ${order.consignee.district.name}</td>
    <td width="200px">${order.consignee.address}</td>
    <td  width ="80px" align="center">${order.listPrice}</td>
  </tr>
</c:forEach>
</table>
<c:if test="${!empty pagination}">		   
			<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
</div>
</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
		src="${contextPath}/js/order/sort.js"></script>	
</body>
</html>
