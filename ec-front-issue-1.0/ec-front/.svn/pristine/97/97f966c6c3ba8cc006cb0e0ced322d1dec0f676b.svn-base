<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 作家推荐(mod-authorsrecommend) -->
<div class="mod mod-authorsrecommend">
	<div class="col col-indexbig J-tab">
		<%-- focus_otitle(通用楼层标题) --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600107">
		       <c:import url="/fragment/600107" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<%-- tab_link(tab切换) --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600055">
		       <c:import url="/fragment/600055" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<div class="cont">
			<div class="grid-mod grid-mod-3-l210r210">
				<div class="grid-mod-l">
					<div class="col col-narrowside">
						<%-- link_img(左上图片) --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600053">
						       <c:import url="/fragment/600053" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
						</cache:fragmentPageCache>
						<div class="cont">
							<%-- promotion_link --%>
							<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600054">
						       <c:import url="/fragment/600054" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
							</cache:fragmentPageCache>
						</div>
					</div>
				</div>
				<jsp:include page="tab_author.jsp"/>
				<div class="grid-mod-r">
					<div class="col col-singleside">
						<%-- tab_other --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600056">
					       <c:import url="/fragment/600056" >
					       	 <c:param value="${cacheKey}" name="cacheKey" />
					       </c:import>
						</cache:fragmentPageCache>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 作家推荐(mod-authorsrecommend) end -->
