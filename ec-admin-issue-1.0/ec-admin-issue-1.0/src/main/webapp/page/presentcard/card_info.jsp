<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
	<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>添加商品属性</title>
<style type="text/css">
.delivery-lable{
  padding:0 0 0 15px; 
}
 .showinfo{
   border:2px solid #DFDFDF;
 }
 </style>
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
 	<div class="showinfo">
	<h4>礼品卡明细 </h4>
	<table class="list-table">
		<tr>
			<td align="left">卡号<label class="delivery-lable">${card.id}</label>
			</td>
			<td align="left">类型<label class="delivery-lable">${card.type.name}</label>
			</td>
		</tr>
		<tr>
			<td align="left">状态<label class="delivery-lable">${card.status.name}</label>
			</td>
			<td align="left">创建日期<label class="delivery-lable"><fmt:formatDate type="date" value="${card.createDate}"/></label>
			</td>
		</tr>
		<tr>
			<td align="left">有效日期<label class="delivery-lable"><c:if test="${card.endDate==null}">未设定</c:if><c:if test="${card.endDate!=null}"><fmt:formatDate type="date" value="${card.endDate}"/></c:if></label>
			</td>
			<td align="left">面额<label class="delivery-lable">
					<c:if test="${card.denomination==null}">未设定</c:if><c:if test="${card.denomination!=null}">${card.denomination}</c:if></label>
			</td>
		</tr>
		<tr>
			<td align="left">余额<label class="delivery-lable"><c:if test="${card.balance==null}">未设定</c:if><c:if test="${card.balance!=null}">${card.balance}</c:if></label>
			</td>
			<td align="left">最近修改密码时间<label class="delivery-lable"><c:if test="${card.latestModifyPasswordTime==null}">未修改</c:if><c:if test="${card.latestModifyPasswordTime!=null}"><fmt:formatDate type="both" value="${card.latestModifyPasswordTime}"/></c:if></label>
			</td>
		</tr>
		<tr>
			<td align="left">最近使用时间<label class="delivery-lable"><c:if test="${card.latestUsedTime==null}">未使用</c:if><c:if test="${card.latestUsedTime!=null}"><fmt:formatDate type="both" value="${card.latestUsedTime}"/></c:if></label>
			</td>
			<td align="left">礼品卡订单<label class="delivery-lable">${card.order.id}</label>
			</td>
		</tr>
	</table>
</div>
<br>
  <div class="showinfo">
	<h4>礼品卡状态日志 </h4>
	<table class="list-table">
		<tr>
			<th>状态 </th>
			<th>状态更改时间 </th>
			<th>操作人</th>
 		</tr>
		<c:forEach items="${card.statusLogList}" var="statusLog">
		<tr>
		<td>${statusLog.status.name}</td>
		<td><fmt:formatDate type="both" value="${statusLog.createDate}"/></td>
		<td>${statusLog.operator.name}</td>
		</tr>
		</c:forEach>
	</table>
</div>
<br>
 <div class="showinfo">
	<h4>礼品卡修改密码日志 </h4>
	<table class="list-table">
		<tr>
			<th>修改时间  </th>
			<th>操作人</th>
 		</tr>
		<c:forEach items="${card.modifyPasswordLogList}" var="modifyPasswordLog">
		<tr>
 		<td><fmt:formatDate type="both" value="${modifyPasswordLog.createDate}"/></td>
		<td>${modifyPasswordLog.operator.name}</td>
		</tr>
		</c:forEach>
	</table>
</div>
<br>
  <div class="showinfo">
	<h4>礼品卡交易日志   </h4>
	<table class="list-table">
		<tr>
			<th>卡号   </th>
			<th>交易日期  </th>
			<th>交易类型</th>
			<th>使用/退款 </th>
			<th>当前余额</th>
			<th>原卡金额 </th>
			<th>订单号</th>
			<th>子订单号  </th>
			<th>操作人(后台用户)</th>
			<th>用户名</th>
			
 		</tr>
		<c:forEach items="${card.dealLogList}" var="dealLog">
		<tr>
 		<td>${card.id}</td>
		<td><fmt:formatDate type="both" value="${dealLog.createDate}"/></td>
	 	<td>${dealLog.type}</td>
		<td>${dealLog.dealMoney}</td>
 		<td>${dealLog.currentBalance}</td>
		<td>${card.balance}</td>
 		<td>${dealLog.order.id}</td>
		<td> </td>
 		<td>${dealLog.operator.name}</td>
		<td>${dealLog.operator.name}</td>
		</tr>
		</c:forEach>
	</table>
</div>
</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp"%>
 </body>
</html>
