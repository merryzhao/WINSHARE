<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 电子书  精品推荐(mod-saudiovisualrecommend) -->
<div class="mod-ebookrecommendnew" >
	<div class="sg-ebookrecommendnew">
		<div class="col sg-col-ebookside">
			<%-- focus_otitle --%>
			<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600106">
			       <c:import url="/fragment/600106" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
			</cache:fragmentPageCache>
			<div class="cont">
				<%-- ebook_free --%>
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600226">
			       <c:import url="/fragment/600226" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
				</cache:fragmentPageCache>
			</div>
		</div>
		
		<div class="col sg-col-ebookmiddle">
			<%-- focus_otitle --%>
			<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600224">
			       <c:import url="/fragment/600224" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
			</cache:fragmentPageCache>
			<div class="cont">
				<%-- ebook_banner --%>
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600227">
			       <c:import url="/fragment/600227" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
				</cache:fragmentPageCache>
				<%-- ebook_product --%>
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600230">
			       <c:import url="/fragment/600230" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
				</cache:fragmentPageCache>
				<%-- ebook_link --%>
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600229">
			       <c:import url="/fragment/600229" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
				</cache:fragmentPageCache>
			</div>
		</div>
		
		<div class="col sg-col-ebookside">
			<%-- focus_otitle --%>
			<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600225">
			       <c:import url="/fragment/600225" >
			       	 <c:param value="${cacheKey}" name="cacheKey" />
			       </c:import>
			</cache:fragmentPageCache>
			<div class="cont">
				<%-- ebook_down --%>
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600228">
				       <c:import url="/fragment/600228" >
				       	 <c:param value="${cacheKey}" name="cacheKey" />
				       </c:import>
				</cache:fragmentPageCache>
			</div>
		</div>
	</div>
</div>
<!-- 音像  精品推荐(mod-audiovisualrecommend) end -->