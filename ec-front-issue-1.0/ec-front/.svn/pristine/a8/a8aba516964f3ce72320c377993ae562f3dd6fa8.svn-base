<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 分类推荐(mod-classifyrecommend) -->
<div class="mod mod-classifyrecommend">
	<div class="col col-indexbig">
		<%-- focus_otitle --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600112">
		       <c:import url="/fragment/600112" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<%-- tab_link --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600026">
		       <c:import url="/fragment/600026" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<div class="cont">
			<jsp:include page="tab_category.jsp"/>
		</div>
		<%-- category_attach --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600031">
		       <c:import url="/fragment/600031" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
	</div>
</div>
<!-- 分类推荐(mod-classifyrecommend) end -->
