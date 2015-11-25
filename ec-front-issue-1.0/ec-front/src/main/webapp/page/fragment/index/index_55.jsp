<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="goods_box" fragment="55">

<jsp:include page="../null.jsp"/>
	<div class="goods_list">

		<c:forEach items="${contentList}" var="content" varStatus="status">
		<c:if test="${content.currentPromotion[1]>0}">
		   <dl bind="limit" end="${content.currentPromotion[1]}" >
				<dd>	
					剩余:<b class="fb red" bind="hour">00</b>小时<b class="fb red"
						bind="min">00</b>分<b class="fb red" bind="sec">00</b>秒
				</dd>
				<dt>
					<p>
						<img src="images/ads/pro_icon1.png" data-lazy="false"/>
					</p>
					<a   target="_blank"  href="${content.url}" title="<c:out value="${content.name}"/>"><img
						class="book_img" src="${content.imageUrl}" alt="${content.name}" data-lazy="false">
					</a>
				</dt>
				<dd>
					<a   target="_blank"  href="${content.url}" title="<c:out value="${content.name}"/>"><winxuan-string:substr length="9" content="${content.name}"></winxuan-string:substr></a>
				</dd>
				<dd>
					<b class="red fb">￥${content.effectivePrice}</b>
					<del>￥${content.listPrice}</del>
				</dd>
			</dl>
			</c:if>
		</c:forEach>
	</div>
</div>