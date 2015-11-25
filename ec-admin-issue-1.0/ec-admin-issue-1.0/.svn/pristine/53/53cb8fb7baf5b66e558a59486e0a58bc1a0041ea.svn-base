<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
	<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>会员详细</title>
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
	<h4>会员详细信息 </h4>
 	<div id="preview" style="filter: progid : DXImageTransform.Microsoft.AlphaImageLoader ( sizingMethod = scale );">
		<img id="detailimg" 
			<c:if test="${empty  customer.avatar }">src=/imgs/customer/default.jpg </c:if> 
			<c:if test="${!empty  customer.avatar }">src=/imgs/customer/${customer.avatar} </c:if>  
		width="100" height="100">
							</div>
 	<br/>
	<table class="list-table">
		<tr>
			<td align="left">注册名<label class="delivery-lable">${customer.name}</label>
			</td>
			<td align="left">真实姓名<label class="delivery-lable">${customer.realName}</label>
			</td>
			<td align="left">性别<label class="delivery-lable"><c:if test="${customer.gender==0}">女</c:if><c:if test="${customer.gender==1}">男</c:if></label>
			</td>
			<td align="left">生日<label class="delivery-lable">${customer.birthday}</label>
			</td>
		</tr>
		<tr>
			<td align="left">邮箱<label class="delivery-lable">${customer.email}</label>
			</td>
			<td align="left">电话<label class="delivery-lable">${customer.mobile}</label>
			</td>
		    <td align="left">会员类型<label class="delivery-lable"><c:if test="${customer.grade==0}">普通会员</c:if><c:if test="${customer.grade==1}">银牌会员</c:if><c:if test="${customer.grade==2}">金牌会员</c:if></label>
			</td>
			<td align="left">注册时间<label class="delivery-lable"><fmt:formatDate type="both" value="${customer.registerTime}"/></label>
			</td>
		</tr>
		<tr>
 			<td align="left">积分<label class="delivery-lable">XXX</label>
			</td>
			<td align="left">购买折扣<label class="delivery-lable">XXX</label>
			</td>
			<td align="left">状态<label class="delivery-lable"><c:if test="${customer.available==true}">可用</c:if><c:if test="${customer.available==false}">不可用</c:if></label>
			</td>
			<td align="left">最近成交时间<label class="delivery-lable"><c:if test="${empty customer.lastTradeTime }">暂无交易</c:if> <c:if test="${not empty customer.lastTradeTime }"><fmt:formatDate value="${customer.lastTradeTime }" type="both" /></c:if></label>
			</td>
		</tr>
		<tr>
			<td align="left">居住地<label class="delivery-lable">${customer.country.name}&nbsp;${customer.province.name}</label>
			</td>
			<td align="left">居住状态<label class="delivery-lable">${customer.extension.livingStatus}</label>
			</td>
		    <td align="left">身份<label class="delivery-lable">${customer.extension.career.name}</label>
			</td>
			<td align="left">月收入<label class="delivery-lable">${customer.extension.salary.name}</label>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="4">兴趣爱好<label class="delivery-lable">${customer.extension.interest}</label>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="4">喜欢或欣赏的人<label class="delivery-lable">${customer.extension.favorite}</label>
			</td>
		</tr>
	</table>
</div>
</div>
</div>
</div>
<%@include file="../snippets/scripts.jsp"%>
 </body>
</html>
