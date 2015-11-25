<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>渠道商品关系导入</title>
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
				<form action="/channelsales/product/upload" method="post">
					<label>渠道:</label> <br />
					<c:forEach items="${channels }" var="c" varStatus="s">
							<input type="checkbox" value="${c.id }" name="channels" />${c.name }
						<c:if test="${s.index % 3==0}">
							<br /> 
						</c:if>
					</c:forEach>
					<br /> <label> 渠道商品ID: </label>
					<textarea name="channelProducts"
						style="width: 150px; height: 150px"></textarea>

					<label> EC商品ID: </label>
					<textarea name="productSales" style="width: 150px; height: 150px"></textarea>
					<br /> <input type="submit" value="上传"
						onclick="$(this).hide(); return true" />
				</form>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
</body>
</html>