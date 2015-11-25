<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 精挑细选(mod-selective) -->
<div class="mod mod-selective">
	<div class="col col-indexbig">
		<%-- focus_otitle --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600111">
		       <c:import url="/fragment/600111" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
	</div>
	<%-- selective --%>
	<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600018">
	       <c:import url="/fragment/600018" >
	       	 <c:param value="${cacheKey}" name="cacheKey" />
	       </c:import>
	</cache:fragmentPageCache>
</div>
<!-- 精挑细选(mod-selective) end -->