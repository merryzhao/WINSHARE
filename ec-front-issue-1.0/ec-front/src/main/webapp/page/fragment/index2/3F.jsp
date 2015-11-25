<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 特色推荐(mod-classifyrecommend) -->
<div class="mod mod-specialrecommend">
	<div class="col col-indexbig">
		<%-- focus_otitle --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600114">
		       <c:import url="/fragment/600114" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<%-- tab_link --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600021">
		       <c:import url="/fragment/600021" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<div class="cont">
			<jsp:include page="tab_recommend.jsp"/>
		</div>
	</div>
</div>
<!-- 特色推荐(mod-classifyrecommend) end -->
