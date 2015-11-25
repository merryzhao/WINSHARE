<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${contextPath}/js/jQuery.js"></script>
<script type="text/javascript" src="${contextPath }/js/order/report/list.js"></script>
<%@include file="../../snippets/meta.jsp"%>
<title>清单查询</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>

<div class="frame">
		<!-- 引入top部分 -->
<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
<%@include file="../../snippets/frame-left-order.jsp"%>
	<div class="frame-main">
		<div>
			<div>
				<input type="button" value="导出Excel" onclick="report.download()"/>
			</div>
			<div>
				<table class="list-table">
					<tbody>
						<tr>
							<th>订单号</th>
							<th>注册名</th>
							<th>收货人</th>
							<th>配送区域</th>
							<th>包件数</th>
						</tr>
						<c:forEach var="o" items="${orders }">
							<tr>
								<td>${o.id }</td>
								<td>${o.customer.name }</td>
								<td>${o.consignee.consignee }</td>
								<td>
									${o.consignee.province.name }-${o.consignee.city.name }
								</td>
								<td>
									<input type="text" value="${o.packages }" maxlength="10" onblur="report.savePackages('${o.id}',this.value,'${o.packages }')" />
								</td>
							</tr>
						</c:forEach>
						<tr>
							<th>订单号</th>
							<th>注册名</th>
							<th>收货人</th>
							<th>配送区域</th>
							<th>包件数</th>
						</tr>
					</tbody>
				</table>
			</div>	   
			<c:if test="${not empty pagination}">	
				<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
			</c:if>
		</div>
		<form name="downloadForm" action="/order/report/delivery/download" method="post" >
			
		</form>
		
	</div>
</div>
</body>