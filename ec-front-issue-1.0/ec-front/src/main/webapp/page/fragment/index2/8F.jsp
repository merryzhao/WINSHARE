<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 音像  精品推荐(mod-saudiovisualrecommend) -->
<div class="mod mod-audiovisualrecommend" >
	<div class="col col-indexbig">
		<%-- focus_otitle --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600105">
		       <c:import url="/fragment/600105" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<div class="cont">
			<div class="grid-mod grid-mod-2-l210">
				<div class="grid-mod-l">
					<div class="col col-narrowside">
						<%-- link_img --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600097">
						       <c:import url="/fragment/600097" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
						</cache:fragmentPageCache>
						<div class="cont">
							<%-- media_link --%>
							<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600098">
							       <c:import url="/fragment/600098" >
							       	 <c:param value="${cacheKey}" name="cacheKey" />
							       </c:import>
							</cache:fragmentPageCache>
						</div>
					</div>
				</div>
				<div class="grid-mod-r">
					<div class="list list-1l-6s">
						<%-- link_img --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600099">
						       <c:import url="/fragment/600099" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
						</cache:fragmentPageCache>
						<%-- media_product --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600100">
						       <c:import url="/fragment/600100" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
						</cache:fragmentPageCache>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 音像  精品推荐(mod-audiovisualrecommend) end -->
