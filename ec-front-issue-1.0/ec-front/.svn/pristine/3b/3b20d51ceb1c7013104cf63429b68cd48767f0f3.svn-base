<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!-- 品牌出版(mod-brandpublication) -->
<div class="mod mod-brandpublication">
	<div class="col col-indexbig J-tab">
		<%-- focus_otitle(通用楼层标题) --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600108">
		       <c:import url="/fragment/600108" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<%-- tab_link(tab切换) --%>
		<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600075">
		       <c:import url="/fragment/600075" >
		       	 <c:param value="${cacheKey}" name="cacheKey" />
		       </c:import>
		</cache:fragmentPageCache>
		<div class="cont">
			<div class="grid-mod grid-mod-3-l210r210">
				<div class="grid-mod-l">
					<div class="col col-narrowside">
						<%-- link_img(左上图片) --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600073">
						       <c:import url="/fragment/600073" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
						</cache:fragmentPageCache>
						<div class="cont">
							<%-- promotion_link(tab切换) --%>
							<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600074">
						       <c:import url="/fragment/600074" >
						       	 <c:param value="${cacheKey}" name="cacheKey" />
						       </c:import>
							</cache:fragmentPageCache>
						</div>
					</div>
				</div>
				<jsp:include page="tab_press.jsp"/>
				<div class="grid-mod-r">
					<div class="col col-singleside">
						<%-- tab_other --%>
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_600076">
					       <c:import url="/fragment/600076" >
					       	 <c:param value="${cacheKey}" name="cacheKey" />
					       </c:import>
						</cache:fragmentPageCache>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 出版社推荐(mod-authorsrecommend) end -->