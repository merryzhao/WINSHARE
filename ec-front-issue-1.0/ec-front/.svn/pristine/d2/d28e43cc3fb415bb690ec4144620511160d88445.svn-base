<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../tags.jsp" %>
<!-- nav -->
    <nav class="nav">
        <div class="line master-nav-wrap">
        <c:set var="expandable" value="true" />
		<c:if test='${param.expandable=="0"}'>
			<c:set var="expandable" value="false" />
		</c:if>
		<c:set var="menuTitleClass" value="" />
		<c:set var="styleDisplay" value="" />
		<c:if test="${expandable}">
			<c:set var="menuTitleClass" value="class='unfold'" />
			<c:set var="styleDisplay" value="style='display:none;'" />
		</c:if>
            <!-- master-nav -->
            <div class="unit master-nav cf">

                <!-- interaction-menu -->
                <div class="interaction-menu fl">
                    <h2><span ${menuTitleClass}>全部商品分类</span>
                    	<c:if test="${!param.index}">
                    		<b></b>
                    	</c:if>
                    </h2>
					<c:if test="${!param.index}">
                    <!-- master-menu-pop -->
                    <div class="master-menu-pop" data-expandable="${expandable}" style="display:none;">
	                 <c:import url="http://www.winxuan.com/category/book/navnew"/>
                      
                      <div class="item"  data-index="9">
                            <div class="item-title">
                                <h3><a href="http://list.winxuan.com/7786">音像</a></h3>
                                <div class="item-title-class">
								<a href="http://list.winxuan.com/7791" title="音乐">音乐</a><a
						href="http://list.winxuan.com/7788" title="影视">影视</a><a
						href="http://list.winxuan.com/7790" title="软件">软件</a><a
						href="http://list.winxuan.com/7789" title="教学">教学</a>
								</div>
                                <b></b>
                            </div>
                            <span class="overlayout"></span>
                        </div>
                        
                        <div class="item" data-index="11">
                            <div class="item-title">
                                <h3><a href="http://list.winxuan.com/1?onlyEBook=true">电子书</a></h3>
                                <div class="item-title-class">
								<a href="http://list.winxuan.com/505?onlyEBook=true" target="_blank" title="历史">历史</a><a
						href="http://list.winxuan.com/4429?onlyEBook=true" target="_blank" title="文学">文学</a><a
						href="http://list.winxuan.com/4098?onlyEBook=true" target="_blank" title="互联网">互联网</a><a
						href="http://list.winxuan.com/3676?onlyEBook=true" target="_blank" title="传记">传记</a>
								</div>
                                <b></b>
                            </div>
                            <span class="overlayout"></span>
                        </div>
                          <div class="item" data-index="10">
                            <div class="item-title">
                                <h3><a href="http://list.winxuan.com/7787/">百货</a></h3>
                                <div class="item-title-class">
								<a href="http://list.winxuan.com/7971" title="家电">家电</a><a
						href="http://list.winxuan.com/8000" title="美妆">美妆</a><a
						href="http://list.winxuan.com/7974" title="家居">家居</a><a
						href="http://list.winxuan.com/8004" title="母婴">母婴</a>
								</div>
                                <b></b>
                            </div>
                            <span class="overlayout"></span>
                        </div>
               
                        <div class="item item-all"><a href="http://www.winxuan.com/catalog_book.html" target="_blank">全部图书</a>/<a
					href="http://www.winxuan.com/catalog_media.html" target="_blank">音像</a>/<a
					href="http://www.winxuan.com/catalog_ebook.html" target="_blank">电子书</a>/<a
					href="http://www.winxuan.com/catalog_mall.html" target="_blank">百货分类</a>
					&gt;&gt;</div>
                    </div>
                    <!-- master-menu-pop end -->
                    <div class="item-pop" style="display:none;">
                                                                                内容正在完善中……
                    </div>
                    </c:if>
                <!-- interaction-menu end -->

                </div>
                <!-- master-menu end -->

                <!-- master-nav-main -->
               	 <cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3007">
                   <c:import url="/fragment/3007" >
                   	<c:param value="${cacheKey}" name="cacheKey" />
                   </c:import>
                 </cache:fragmentPageCache>
                <!-- master-nav-main end -->

            </div>
            <!-- master-nav end -->
        </div>
        	<c:if test="${!param.index}">
		        <div class="line slave-nav-wrap cf">
		        	<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3008">
			            <c:import url="/fragment/3008" >
			            	<c:param value="${cacheKey}" name="cacheKey" />
			            </c:import>
		            </cache:fragmentPageCache>
		      	</div>
            </c:if>
    </nav>
<!-- nav end -->
			
	
		