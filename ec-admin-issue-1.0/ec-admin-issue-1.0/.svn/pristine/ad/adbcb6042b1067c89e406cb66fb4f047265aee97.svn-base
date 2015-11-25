<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_payment.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
div.entering_table {
	background: #ecf5ff;
	margin-top: 25px;
	margin-bottom: 25px;
	border: 1px solid #6293bb;
}

table.entering_table {
	width: 90%;
	margin-left: 5%;
	margin-right: 5%;
	margin-top: 10px;
	margin-bottom: 10px;
}

table.entering_table td.property {
	text-align: right;
}
</style>
<title>锁定库存修改</title>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
			<div id="content-result" >
			<div class="entering_table">
				<form action="/stockrule/lock/update" method="post">
								<table class="entering_table">
									<tbody>
									<tr>
										<td colspan="9">锁定库存详情查看</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									<c:if test="${stockLockRule.customer!=null }">
									<tr>
										<td></td>
										<td class="property">用户：
										</td>
										<td>
										<input type="text" name="" value="" />
										</td>
									</tr>
									</c:if>
									<tr>
										<td><input type="hidden" name="id" value="${stockLockRule.id }" /></td>
										<td class="property">渠道：
										</td>
										<td>
											${stockLockRule.channel.name }
											<input type="hidden" name="channel.id" value="${stockLockRule.channel.id }" />
										</td>
										<td class="property">商品编码：	
										</td>
										<td>
											${stockLockRule.productSale }
											<input type="hidden" name="productSale" value="${stockLockRule.productSale }" />
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">锁定库存：
										</td>
										<td>
											${stockLockRule.lockStock }
										</td>
										<td class="property">锁定系数：
										</td>
										<td>${stockLockRule.lockFactor }%</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">实际锁定库存：
										</td>
										<td>
											${stockLockRule.realLock }
										</td>
										<td class="property">销量：
										</td>
										<td>${stockLockRule.sales }</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">开始时间：
										</td>
										<td>
											<fmt:formatDate value='${stockLockRule.beginTime }' pattern='yyyy-MM-dd'/>
										</td>
										<td class="property">结束时间：
										</td>
										<td>
											<fmt:formatDate value='${stockLockRule.endTime }' pattern='yyyy-MM-dd'/>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">创建时间：
										</td>
										<td>
											<fmt:formatDate value='${stockLockRule.createTime }' pattern='yyyy-MM-dd'/>
										</td>
										<td class="property">最近更新时间：
										</td>
										<td>
											<fmt:formatDate value='${stockLockRule.updateTime }' pattern='yyyy-MM-dd'/>
										</td>
									</tr>
									</tbody>
								</table>
								<table class="entering_table"  id="entering_table_one">
								<tr>
									<td align="center"><input type="button" class="button" id="yessubmit" value="确定">&nbsp;&nbsp;&nbsp;<input type="button" class="button returnBtn" value="返回"></td>
									<td></td>
								</tr>
								</table>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/product/stocklockedit.js"></script>
</html>