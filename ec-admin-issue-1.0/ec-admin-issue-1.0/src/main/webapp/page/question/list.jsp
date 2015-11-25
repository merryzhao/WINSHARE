<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>店铺咨询管理</title>
<style type="text/css">
textarea {
	width: 120px;
	height: 50px;
	margin-bottom: -5px;
}

input {
	vertical-align: middle;
	margin: 0px;
}
</style>
<!-- 引入JS -->
<%@include file="../snippets/scripts.jsp"%>
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
				<div id="info"></div>
				<div id="search">
					<form action="/question/list" method="post">

						<div>
							商品编号: <input type="text" name="productSaleId"
								value="${form.productSaleId }" /> 商品名称：<input type="text"
								name="productName" value="${form.productName }" /> 
							<select id="shopId" name="shopId" >
								<c:forEach items="${shops}" var="shop">
								<option value="${shop.id}" <c:if test="${shop.id == form.shopId}">selected="selected"</c:if>>${shop.name}</option>
								</c:forEach>
						    </select>								
								<input type="submit" style="margin-left: 5px; margin-top: 0px;"
								value="查询" />
						</div>
						<div>
							&nbsp;&nbsp;&nbsp;&nbsp;提问人: <input type="text"
								name="customerName" value="${form.customerName }" /> 提问时间： <input
								type="text" name="startSubmitTime" bind="datepicker" />~<input
								name="endSubmitTime" type="text" bind="datepicker" />
						</div>
						<div>
							是否回复：<select id="reply" name="reply">
								<option value="">全部</option>
								<option value="1"
									<c:if test="${form.reply == true}">selected="selected"</c:if>>已回复</option>
								<option value="0"
									<c:if test="${form.reply == false}">selected="selected"</c:if>>未回复</option>
							</select> 回复人: <input type="text" name="replierName"
								value="${form.replierName }" /> 回复时间： <input type="text"
								name="replyTimeBegin" bind="datepicker" />~<input
								name="replyTimeEnd" type="text" bind="datepicker" />

						</div>

					</form>
				</div>
				<div id="result">
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<c:if test="${list!=null}">
						<table class="list-table">
							<tr>
								<th width="10%">卖家</th>
								<th width="10%">商品编号</th>
								<th width="25%">商品名称</th>
								<th width="25%">提问详情</th>
								<th width="10%">提问时间</th>
								<th width="10%">提问人</th>
								<th width="10%">操作</th>
							</tr>
							<c:if test="${fn:length(list)!=0}">
								<c:forEach items="${list}" var="question">
									<tr class="data">
										<td>${question.shop.name}</td>
										<td><a
											href="http://www.winxuan.com/product/${question.productSale.id}"
											target="_blank">${question.productSale.id}</a>
										</td>
										<td>${question.productSale.name}</td>
										<td><a href="/question/goReply?id=${question.id }"><c:out value="${question.content}"></c:out></a>
										</td>
										<td><fmt:formatDate value="${question.askTime}"
												type="both" />
										</td>
										<td>${question.customer.name }</td>
										<td><a href="/question/goReply?id=${question.id }">回复</a>
											<a href="javascript:void(0);"
											onclick="del(${question.id},this);">删除</a></td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${fn:length(list)==0}">
								<tr>
									<td colspan="8">暂无数据</td>
								</tr>
							</c:if>
							<tr>
								<th>卖家</th>
								<th>商品编号</th>
								<th>商品名称</th>
								<th>提问详情</th>
								<th>提问时间</th>
								<th>提问人</th>
								<th>操作</th>
							</tr>
						</table>
					</c:if>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<script>
		function del(id,obj){
			var con = confirm("确认删除?");
			if(con){
			var url = '/question/delete/?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : url,
				data: "id="+id,
				error : function() {
					
				},
				success : function(data) { 
					if(data.result==1){
						$(obj).parent().parent().remove();
					}
				}
			});
			}
		}
	</script>
</body>
</html>