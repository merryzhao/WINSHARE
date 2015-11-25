<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_payment.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>库存浏览</title>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content" align="center">
				<div id="content-result">
					<div class="showinfo">
					<form action="/stockrule/find" method="get">
					<h4>库存信息(库存编号：${stockRule.id })<input type="submit" style="float:right;color: blue;" value="返回" /></h4>
					</form>
					<table class="list-table">
						<tr>
							<td align="left">渠道：<label class="delivery-lable">${stockRule.channel.name}</label>
							</td>
							<td align="left">销售类型：<label class="delivery-lable">${stockRule.supplyType.name}</label>
							</td>
							<td align="left">库存类型：<label class="delivery-lable">
								<c:if test="${stockRule.stockType==1 }">实物库存</c:if>
								<c:if test="${stockRule.stockType==2 }">虚拟库存 </c:if>
							</label>
							</td>
						</tr>
						<tr>
							<td align="left">库存：
								<c:forEach var="ruleDc" items="${stockRule.stockRuleDc }">
									${ruleDc.dc.name }&nbsp;&nbsp;
								</c:forEach>
							</td>
							<c:forEach var="ruleDc" items="${stockRule.stockRuleDc }">
								<td align="left">${ruleDc.dc.name }</td>
							</c:forEach>
						</tr>
						<tr>
							<td align="left">总使用量
							</td>
								<td align="left"><label class="delivery-lable">${stockRule.percent }%</label></td>
						</tr>
					</table>
				</div>
				<div class="showinfo">
					<h4>日志信息</h4>
					<table class="list-table">
						<tr>
							<th>操作人</th>
							<th>操作时间</th>
						</tr>
						<c:forEach items="${stockRule.stockRuleLog}" var="ruleLog">
							<tr>
								<td>${ruleLog.opreator.name}</td>
								<td><fmt:formatDate type="date" value="${ruleLog.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				</div>
			</div>
		</div>
	</div>
</body>
	<%@include file="../snippets/scripts.jsp"%>
</html>