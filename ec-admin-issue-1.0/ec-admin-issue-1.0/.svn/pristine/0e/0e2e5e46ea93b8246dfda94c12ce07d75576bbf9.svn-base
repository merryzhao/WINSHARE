<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>渠道销售</title>
</head>
<body>
    <!-- 引入JS -->
	<div class="frame">
	     <!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		 <!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
		    <!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<form action="/channelsales/product/view" method="post">
					<label>渠道:</label><br />
					<c:forEach items="${channels }" var="c" varStatus="s">
						
						<input type="checkbox" value="${c.id }" name="channels" />${c.name }
						<c:if test="${s.index % 3==0}"><br /></c:if>
					</c:forEach>
					<br />
					<label>
						渠道商品ID:
					</label>
					<textarea name="channelProducts" style="width: 150px;height:150px"></textarea>

					<label>
						EC商品ID:					
					</label>
					<textarea name="productSales" style="width: 150px;height:150px"></textarea>
					<br />
					<input type="submit" value="查询" />
				</form>
				<hr />
				<input type="button" value="全选" onclick="$('input[name=channelproducts]').attr('checked','checked')"/>
				<input type="button" value="反选" onclick="$('input[name=channelproducts]').each(function(i,e){$(e).attr('checked',!$(e).attr('checked'))});"/>
				<input type="button" value="删除" onclick="if(confirm('确认删除?')){document.product.submit();}"/>
				<c:if test="${!empty pagination}">		   
					<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
				</c:if>
				<form action="/channelsales/product/delete" name="product">
				<table class="list-table" >
					<tr>
						<th></th>
						<th>渠道</th>
						<th>渠道商品ID</th>
						<th>EC商品ID</th>
						<th>销售书名</th>
						<th>上传人</th>
						<th>导入方式</th>
						<th>导入时间</th>
					</tr>
					<c:forEach items="${channelproducts }" var="c">
						<tr>
							<td><input type="checkbox" name="channelproducts" value="${c.id }"/></td>
							<td>${c.channel.name }</td>
							<td>${c.channelProduct}</td>
							<td>${c.productSale.id }</td>
							<td>${c.productSale.name }</td>
							<td>${c.operator.name }</td>
							<td>${c.type.name }</td>
							<td><fmt:formatDate value="${c.updatetime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</table>
				</form>
				<c:if test="${!empty pagination}">		   
					<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
				</c:if>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
</body>
</html>