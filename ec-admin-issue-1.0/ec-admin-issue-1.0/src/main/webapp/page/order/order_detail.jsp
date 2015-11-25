<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>订单详细信息</title>
<style>
div.decorate {border-bottom: 2px solid #ccc;margin:4px 0;text-align:left;}
div.decorate h4{margin:10px 0;}
table.order-list{width:100%;}
col.id{width:200px;}
col.eid{width:300px;}
col.time{width:120px;}
div.orderCancel {
	border-left:2px outset; border-bottom: 2px outset; BORDER-RIGHT : 2px outset; BORDER-TOP : 2px outset;
	position: absolute;
	display:none;
	background: #F0F0F0;
	top: 10%;
	left:25%;
	width: 50%;
	height: 20%;
}
h5{
background: #6495ED;
}
div.orderCancelClose {
	position: absolute;
	cursor: pointer;
	top: 1px;
	right: 5px;
}

table.list-table{
margin-top:10px;
border: 1px;
    width: 100%;
}
.edit-img,.edit-needi,.edit-selectimg,.edit-area,.editareaimg{
   cursor: pointer;
   padding: 0 0 0 10px;"
}

.delivery-lable{
  padding:0 0 0 15px; 
}

.oinfo-lable{
  padding:0 15px 0 0; 
}

.showinfo{
   border:2px solid #DFDFDF;
   margin-top: 30px;
}
.hidden{display:none;}
td .editZone{display:none;}
td div.edit .editZone{display:block;position:absolute;}
td div.edit a{display:none !important;}
td div.edit label{display:none;}
div.tip a{display:inline-block;}
.inputCss{
  width: 50px;
}
td button{width: 28px;height: 28;}
.saveCss{width: 50px;}
</style>
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
			     <div class="demo">

  		<div id="tabs">
			<ul>
				<li><a href="#details">订单信息${orderId}</a></li>
				 <c:if test="${orderLogList != 0}">
				<li><a href="${contextPath}/order/${orderId}?view=log">订单日志(${orderLogList})</a></li>
				</c:if>
				<c:if test="${orderStatusList != 0}">
				<li><a href="${contextPath}/order/${orderId}?view=status">订单状态(${orderStatusList})</a></li>
				</c:if>
				
				<c:if test="${order.processStatus.id == 8004 || order.processStatus.id == 8005 ||
				order.processStatus.id == 8009 || order.processStatus.id == 8011}">
				<li><a href="${contextPath}/order/${orderId}?view=delivery">发货信息</a></li>
				</c:if>
				
				<c:if test="${isLogistics}">
				<li><a href="${contextPath}/order/${orderId}?view=logistics">物流跟踪</a></li>
				</c:if>
				<li><a href="${contextPath}/order/${orderId}/returnorder">退/换货(${returnOrderCount})</a></li>
				<li><a href="${contextPath}/order/${orderId}?view=track">订单跟踪(<label id="trackSize">${trackSize}</label>)</a></li>
			    <li><a href="${contextPath}/order/${orderId}?view=customerAccount">客户账户</a></li>
			    <li><a href="${contextPath}/order/${orderId}?view=accountLog">到款日志</a></li>
			    <li><a href="${contextPath}/order/${orderId}?view=erpStatus">物流状态</a></li>
			</ul>
			<div id="details">
				<%@include file="../order/order_info.jsp"%>
			</div>
        </div>
		</div><!-- End demo -->
            </div>
         </div>
     </div>	
<%@include file="../snippets/scripts.jsp" %>
<script>
	$(function() {
		$( "#tabs" ).tabs({
			ajaxOptions: {
				error: function( xhr, status, index, anchor ) {
					$( anchor.hash ).html(
						"Couldn't load this tab. We'll try to fix this as soon as possible. " +
						"If this wouldn't be a demo." );
				},cash:true
			}
		});
	});
</script>
<script type="text/javascript" src="${contextPath}/js/area/areadata.js?${version}"></script>
<script type="text/javascript" src="${contextPath}/js/area/areaevent.js?${version}" ></script> 
<script type="text/javascript" src="${contextPath}/js/order/orderDetail.js?${version}"></script>
</body>
</html>