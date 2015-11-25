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
<title>订单列表</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>
<div class="frame">
		<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
<%@include file="../snippets/frame-left-order.jsp"%>
<div class="frame-main">
			<!-- 核心内容部分div -->
<div class="frame-main-inner" id="content">
<div >
<c:if test="${not empty pagination}">		   
			<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
<table class="list-table" >  
<tr>		
<c:forEach var="row" items="${selectRows}">
   <c:if test="${row == 'id'}">
    <th align="center">订单号</th>
  </c:if>
  <c:if test="${row == 'processStatus'}">
    <th>订单状态</th>
  </c:if>
  <c:if test="${row == 'paymentStatus'}">
    <th>支付状态</th>
  </c:if>
  <c:if test="${row == 'paymentType'}">
    <th>支付方式</th>
  </c:if>
  <c:if test="${row == 'createTime'}">
    <th>创建时间</th>
  </c:if>
  <c:if test="${row == 'deliveryTime'}">
    <th>配送时间<a style ="cursor:pointer" onclick="resort(2);">↓</a><a style ="cursor:pointer" onclick="resort(3);">↑</a></th>
  </c:if>
  <c:if test="${row == 'lastProcessTime'}">
    <th>更新时间</th>
  </c:if>
  <c:if test="${row == 'deliveryType'}">
    <th>配送方式</th>
  </c:if>
  <c:if test="${row == 'name'}">
    <th>注册名</th>
  </c:if>
  
  <c:if test="${row == 'consignee'}">
    <th>收货人姓名</th>
  </c:if>
   <c:if test="${row == 'phone'}">
    <th>电话</th>
  </c:if>
  <c:if test="${row == 'mobile'}">
    <th>手机</th>
  </c:if>
   <c:if test="${row == 'area'}">
    <th>区域</th>
  </c:if>
   <c:if test="${row == 'address'}">
    <th>详细地址</th>
  </c:if> 
  <c:if test="${row == 'zipCode'}">
    <th>邮编</th>
  </c:if>
   <c:if test="${row == 'salePrice'}">
    <th>实洋</th>
  </c:if>
   <c:if test="${row == 'listPrice'}">
    <th>码洋<a style ="cursor:pointer" onclick="resort(8);">↓</a><a style ="cursor:pointer" onclick="resort(9);">↑</a></th>
  </c:if>
  <c:if test="${row == 'requidPayMoney'}">
    <th>应付金额</th>
  </c:if>
   <c:if test="${row == 'deliveryListPrice'}">
    <th>发货码洋<a style ="cursor:pointer"  onclick="resort(6);">↓</a><a style ="cursor:pointer" onclick="resort(7);">↑</a></th>
  </c:if>
  <c:if test="${row == 'deliverySalePrice'}">
    <th>发货实洋</th>
  </c:if>
  <c:if test="${row == 'deliveryQuantity'}">
    <th>发货数量</th>
  </c:if>
  <c:if test="${row == 'outerid'}">
    <th>外部交易号</th>
  </c:if>
  <c:if test="${row == 'deliveryCode'}">
    <th>交寄单号</th>
  </c:if>
  <c:if test="${row == 'deliveryCompany'}">
    <th>承运商</th>
  </c:if>
    <c:if test="${row == 'remark'}">
    <th>备注</th>
  </c:if>
   <c:if test="${row == 'grade'}">
    <th>用户等级</th>
  </c:if> 
   <c:if test="${row == 'savemoney'}">
    <th>优惠金额</th>
  </c:if>   
   <c:if test="${row == 'paymentTime'}">
    <th>支付时间</th>
  </c:if>   
   <c:if test="${row == 'purchaseQuantity'}">
    <th>购买数量</th>
  </c:if>   
    <c:if test="${row == 'isReturnOrder'}">
    <th>是否有退换货</th>
  </c:if>
   <c:if test="${row == 'outofstockoption'}">
    <th>缺货选择</th>
  </c:if> 
  <c:if test="${row == 'channel'}">
  	<th>渠道</th>
  </c:if>
  <c:if test="${row == 'dcoriginal'}">
  	<th>出货DC</th>
  </c:if>
  <c:if test="${row == 'dcdest'}">
  	<th>发货DC</th>
  </c:if>
</c:forEach>
</tr>
<c:forEach var="order" items="${orderList}" varStatus="status">
  <c:set var="rowStatus" value="odd"/>
  	<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
  <tr class="${rowStatus}">
  <c:forEach var="row" items="${selectRows}"> 
   <c:if test="${row == 'id'}">
    <td width ="100px" align="center"><a href="${contextPath}/order/${order.id }" target="_blank">${order.id}</a></td>
  </c:if>
 <c:if test="${row == 'processStatus'}">
    <td width ="80px" align="center">${order.processStatus.name}</td>
    
  </c:if>
  <c:if test="${row == 'paymentStatus'}">
     <td width ="80px" align="center">${order.paymentStatus.name}</td>
  </c:if> 
  <c:if test="${row == 'paymentType'}">
     <td width ="80px" align="center">${order.payType.name}</td>
  </c:if> 
  <c:if test="${row == 'createTime'}">
    <td width ="100px" align="center">${order.createTime}</td>
  </c:if>
  <c:if test="${row == 'deliveryTime'}">
    <td width ="100px" align="center">${order.deliveryTime}</td>
  </c:if>
  <c:if test="${row == 'lastProcessTime'}">
    <td width ="100px" align="center">${order.lastProcessTime}</td>
  </c:if>
  <c:if test="${row == 'deliveryType'}">
    <td width ="80px" align="center">${order.deliveryType.name}</td>
  </c:if>
  <c:if test="${row == 'name'}">
    <td width ="60px" align="center">${order.customer.name}</td>
  </c:if>
  <c:if test="${row == 'consignee'}">
    <td width ="80px">${order.consignee.consignee}</td>
  </c:if>
  <c:if test="${row == 'phone'}">
    <td width ="100px" align="center">${order.consignee.phone}</td>
  </c:if> 
  <c:if test="${row == 'mobile'}">
    <td width ="100px" align="center">${order.consignee.mobile}</td>
  </c:if> 
  <c:if test="${row == 'area'}">
    <td width ="180px" align="center">${order.consignee.province.name} ${order.consignee.city.name} ${order.consignee.district.name} ${order.consignee.town.name}</td>
  </c:if>
  <c:if test="${row == 'address'}">
    <td width="200px">${order.consignee.address}</td>
  </c:if>
  <c:if test="${row == 'zipCode'}">
    <td width ="80px" align="center">${order.consignee.zipCode}</td>
  </c:if>   
  <c:if test="${row == 'salePrice'}">
    <td  width ="80px" align="center">${order.salePrice}</td>
  </c:if>
  <c:if test="${row == 'listPrice'}">
    <td  width ="80px" align="center">${order.listPrice}</td>
  </c:if>
  <c:if test="${row == 'requidPayMoney'}">
    <td  width ="80px" align="center">${order.requidPayMoney}</td>
  </c:if>
  <c:if test="${row == 'deliveryListPrice'}">
   <td  width ="80px" align="center">${order.deliveryListPrice}</td>
  </c:if>
   <c:if test="${row == 'deliverySalePrice'}">
   <td  width ="80px" align="center">${order.deliverySalePrice}</td>
  </c:if>
  <c:if test="${row == 'deliveryQuantity'}">
      <td  width ="80px" align="center">${order.deliveryQuantity}</td>
  </c:if>
  <c:if test="${row == 'outerid'}">
    <td  width ="80px" align="center">${order.outerId}</td>
  </c:if>
  <c:if test="${row == 'deliveryCode'}">
    <td  width ="80px" align="center">${order.deliveryCode}</td>
  </c:if>
  <c:if test="${row == 'deliveryCompany'}">
   <td  width ="80px" align="center">${order.deliveryCompany.company}</td>
  </c:if>
    <c:if test="${row == 'remark'}">
       <td  width ="80px" align="center">${order.consignee.remark}</td>
  </c:if>
   <c:if test="${row == 'grade'}">
    <td  width ="840px" align="center">${order.customer.grade}</td>
  </c:if> 
   <c:if test="${row == 'savemoney'}">
   <td  width ="80px" align="center">${order.saveMoney}</td>
  </c:if>   
  <c:if test="${row == 'paymentTime'}">
     <c:forEach var="payment" items="${order.paymentList}" >
     <td  width ="80px" align="center">${payment.payTime}</td>
     </c:forEach>
  </c:if>   
   <c:if test="${row == 'purchaseQuantity'}">
    <td  width ="80px" align="center">${order.purchaseQuantity}</td>
  </c:if>   
    <c:if test="${row == 'isReturnOrder'}">
     <td  width ="80px" align="center">
     <c:choose>
      <c:when test="${order.returnOrder}">是</c:when>
      <c:otherwise>否</c:otherwise>
      </c:choose>
    </td>
  </c:if>   
   <c:if test="${row == 'outofstockoption'}">
     <td  width ="80px" align="center">
		<c:choose>
	      <c:when test="${order.outOfStockOption}">发货</c:when>
	      <c:otherwise>不发货</c:otherwise>
		</c:choose>
    </td>
  </c:if>  
  <c:if test="${row == 'channel'}">
  	<td  width ="80px" align="center">
  		${order.channel.name }
  	</td>
  </c:if>  
   <c:if test="${row == 'dcoriginal'}">
   <td>${order.distributionCenter.dcOriginal.name}</td>
  </c:if>
  <c:if test="${row == 'dcdest'}">
  	<td>${order.distributionCenter.dcDest.name}</td>
  </c:if>
</c:forEach>
</tr>
</c:forEach>
<tr>
<c:forEach var="row" items="${selectRows}">
   <c:if test="${row == 'id'}">
    <th align="center">订单号</th>
  </c:if>
  <c:if test="${row == 'processStatus'}">
    <th>订单状态</th>
  </c:if>
  <c:if test="${row == 'paymentStatus'}">
    <th>支付状态</th>   
  </c:if>
  <c:if test="${row == 'paymentType'}">
    <th>支付方式</th>
  </c:if>
  <c:if test="${row == 'createTime'}">
    <th>创建时间</th>
  </c:if>
  <c:if test="${row == 'deliveryTime'}">
    <th>配送时间</th>
  </c:if>
  <c:if test="${row == 'lastProcessTime'}">
    <th>更新时间</th>
  </c:if>
  <c:if test="${row == 'deliveryType'}">
    <th>配送方式</th>
  </c:if>
  <c:if test="${row == 'name'}">
    <th>注册名</th>
  </c:if>
  <c:if test="${row == 'consignee'}">
    <th>收货人姓名</th>
  </c:if>
   <c:if test="${row == 'phone'}">
    <th>电话</th>
  </c:if>
   <c:if test="${row == 'mobile'}">
    <th>手机</th>
  </c:if>
   <c:if test="${row == 'area'}">
    <th>区域</th>
  </c:if>
   <c:if test="${row == 'address'}">
    <th>详细地址</th>
  </c:if> 
  <c:if test="${row == 'zipCode'}">
    <th>邮编</th>
  </c:if>
   <c:if test="${row == 'salePrice'}">
    <th>实样</th>
  </c:if>
   <c:if test="${row == 'listPrice'}">
    <th>码洋</th>
  </c:if>
  <c:if test="${row == 'requidPayMoney'}">
    <th>应付金额</th>
  </c:if>
   <c:if test="${row == 'deliveryListPrice'}">
    <th>发货码洋</th>
  </c:if>
  <c:if test="${row == 'deliverySalePrice'}">
    <th>发货实洋</th>
  </c:if>
  <c:if test="${row == 'deliveryQuantity'}">
    <th>发货数量</th>
  </c:if>
  <c:if test="${row == 'outerid'}">
    <th>外部交易号</th>
  </c:if>
  <c:if test="${row == 'deliveryCode'}">
    <th>交寄单号</th>
  </c:if>
  <c:if test="${row == 'deliveryCompany'}">
    <th>承运商</th>
  </c:if>
    <c:if test="${row == 'remark'}">
    <th>备注</th>
  </c:if>
   <c:if test="${row == 'grade'}">
    <th>用户等级</th>
  </c:if> 
   <c:if test="${row == 'savemoney'}">
    <th>优惠金额</th>
  </c:if>
  <c:if test="${row == 'paymentTime'}">
    <th>支付时间</th>
  </c:if>   
   <c:if test="${row == 'purchaseQuantity'}">
    <th>购买数量</th>
  </c:if>   
    <c:if test="${row == 'isReturnOrder'}">
    <th>是否有退换货</th>
  </c:if>
  <c:if test="${row == 'outofstockoption'}">
    <th>缺货选择</th>
  </c:if>
  <c:if test="${row == 'channel'}">
    <th>渠道</th>
  </c:if>  
   <c:if test="${row == 'dcoriginal'}">
  	<th>出货DC</th>
  </c:if>
  <c:if test="${row == 'dcdest'}">
  	<th>发货DC</th>
  </c:if>
</c:forEach>
</tr>
</table>
<c:if test="${not empty pagination}">		   
			<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
</div>
</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
		src="${contextPath}/js/order/orderAuditBatch.js"></script>
<script type="text/javascript"
		src="${contextPath}/js/order/sort.js"></script>		
</body>
</html>
