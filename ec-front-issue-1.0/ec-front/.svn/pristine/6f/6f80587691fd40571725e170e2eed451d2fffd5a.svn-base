<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="daily_pro" fragment="241">
	<h3>限时抢购</h3>
	<c:forEach items="${contentList}" var="content" varStatus="status">
		<c:if test="${content.currentPromotion[1]>0}">
		
		<dl bind="limit" end="${content.currentPromotion[1]}">
			<dd class="fb">
				剩余:<b class="fb red" bind="hour">1</b>小时<b class="fb red" bind="min">16</b>分<b class="fb red" bind="sec">22</b>秒
			</dd>
			<dt>
				<p>
					<img src="images/ads/pro_icon1.png" />
				</p>
				<a target="_blank" href="${content.url}" title="<c:out value="${content.name}"/>"><img class="book_img" src="${content.imageUrl}" alt="${content.name}"/></a>
			</dt>
			<dd>
				<a target="_blank" href="${content.url}" title="<c:out value="${content.name}"/>">${content.name}</a>
			</dd>
			<dd class="fb">
				抢购价：<b class="red fb">￥${content.effectivePrice}</b>
			</dd>
		</dl>
		</c:if>
	</c:forEach>
</div>
<script>seajs.use(["jQuery","limit"],function($,limit){
	var el=$("div[fragment='241']");
		limit({context:el});
});</script>