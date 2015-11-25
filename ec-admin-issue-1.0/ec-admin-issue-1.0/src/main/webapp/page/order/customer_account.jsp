<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="showinfo">
	<h4>客户账户信息</h4> 
	
	<table class="list-table">
		<tr>
			<td align="left">注册名：<label class="delivery-lable">${order.customer.name}</label>
			</td>
			<td align="left">真实姓名：<label class="delivery-lable">${order.customer.realName}</label>
			</td>
			<td align="left">用户来源：<label class="delivery-lable">${order.customer.source.name}</label>
			</td>
		</tr>
		<tr>
			<td align="left">所属渠道：<label class="delivery-lable">${order.customer.channel.name}</label>
			</td>
			<td align="left">会员等级：
				<label class="delivery-lable">
					<c:if test="${order.customer.grade == 0}">普通会员</c:if>
					<c:if test="${order.customer.grade == 1}">银卡会员</c:if>
					<c:if test="${order.customer.grade == 2}">金卡会员</c:if>
				</label>
			</td>
			<td align="left">折扣：<label class="delivery-lable">${order.customer.discount}</label>
			</td>
		</tr>
		<tr>
			<td align="left">邮箱：<label class="delivery-lable">${order.customer.email}</label>
			</td>
			<td align="left">电话：<label class="delivery-lable">${order.customer.phone}</label>
			</td>
			<td align="left">手机：<label class="delivery-lable">${order.customer.mobile}</label>
			</td>
		</tr>
		<tr>
			<td align="left">是否有效：<label class="delivery-lable"> <c:if test="${order.customer.available}">是</c:if><c:if test="${!order.customer.available}">否</c:if> </label>
			</td>
			<td align="left">注册时间：<label class="delivery-lable" ><fmt:formatDate type="both" value="${order.customer.registerTime}"></fmt:formatDate></label>
			</td>
			<td align="left">最后登录时间：<label class="delivery-lable"><fmt:formatDate type="both" value="${order.customer.lastLoginTime}"></fmt:formatDate></label>
			</td>	 
		</tr>
		<tr>
			<td align="left">暂存款余额：<label class="delivery-lable">${order.customer.account.balance}</label>
			</td>
			<td align="left">冻结款：<label class="delivery-lable">${order.customer.account.frozen}</label>
			</td>
			<td align="left">积分：<label class="delivery-lable">${order.customer.account.points}</label>
			</td>
		</tr>
	</table>
</div>