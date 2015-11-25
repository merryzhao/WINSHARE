<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="文轩网图书预售频道，本频道提供新书预售、摘要、书评、试读、插图等相关信息。 " />
<meta name="Keywords" content="新书,预售图书,网上买书,畅销书" />
<title>新书-图书预售-文轩网</title>
<jsp:include page="/page/snippets/v2/meta.jsp" flush="true" >
       <jsp:param name="type" value="details" />
       <jsp:param value="false" name="ebook" />
</jsp:include>
<link rel="stylesheet" type="text/css" href="/css/v2/booking.css?${version}">
<link rel="stylesheet" type="text/css" href="/css/v2/bookingclash.css?${version}">
</head>
<body>
<div class="wrap">
<jsp:include page="../snippets/v2/header.jsp">
	   <jsp:param value="book" name="label" />
</jsp:include>
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
                    <h2><a ${menuTitleClass} href="javascript:;">全部商品分类</a><b></b></h2>

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
                <!-- interaction-menu end -->

                </div>
                <!-- master-menu end -->

                <!-- master-nav-main -->
                 <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_3007">
                   <c:import url="/fragment/3007" >
                   <c:param value="${cacheKey}" name="cacheKey" />
                   </c:import>
                   </cache:fragmentPageCache>
                <!-- master-nav-main end -->

            </div>
            <!-- master-nav end -->

        </div>
        <div class="line slave-nav-wrap cf">
         <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_3008">
            <c:import url="/fragment/3008" >
            <c:param value="${cacheKey}" name="cacheKey" />
            </c:import>
            </cache:fragmentPageCache>
        </div>
    </nav>
<!-- nav end -->
<!-- HTML5 布局 -->

    	<!-- 主体 -->
    	<div class="main main-presell">

    		<!-- 100%通栏布局 -->
    		<div class="grid-100p grid-margin-bottom">

    			<!-- ################ mod ################### -->

				<!-- 预售幻灯切换(mod-presell-slider) -->
				<div class="mod mod-presell-slider">
					<!-- Tab栏目(col-tab-2) -->
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714150">
						<c:import url="/fragment/714150">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
					<!-- Tab栏目(col-tab) end -->
				</div>
				<!-- 预售幻灯切换(mod-presell-slider) end -->

				<!-- ################ mod end ################### -->

    		</div>
    		<!-- 100%通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">

    			<!-- 1000侧栏布局(左) -->
    			<div class="grid-1000s grid-left">

    				<!-- ################ mod ################### -->
					
					<!-- 重磅推荐(mod-heavy-recommend) 1101 -->
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714140">
						<c:import url="/fragment/714140">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
					<!-- 重磅推荐(mod-heavy-recommend) end -->

					<!-- ################ mod end ################### -->

    			</div>
    			<!-- 1000侧栏布局(左) end -->

    			<!-- 210侧栏布局(左) -->
    			<div class="grid-210s grid-right">

    				<!-- ################ mod ################### -->

					<!-- 新品热卖榜(mod-new-hot-rank) -->
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714250">
						<c:import url="/fragment/714250">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
					<!-- 新品热卖榜(mod-new-hot-rank) end -->

					<!-- ################ mod end ################### -->

    			</div>
    			<!-- 210侧栏布局(左) end -->
    			
    		</div>
    		<!-- 1210通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">
    			<!-- ################ mod ################### -->
				<!-- 单行广告(mod-ad-row) -->
					<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714270">
						<c:import url="/fragment/714270">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
					</cache:fragmentPageCache>
				</div>
				<!-- 单行广告(mod-ad-row) end -->

				<!-- ################ mod end ################### -->

    		<!-- 1210通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">

    			<!-- ################ mod ################### -->

				<!-- 更多预售(mod-more-presell) -->
				<div class="mod-more-presell">
					<!-- Tab栏目(col-tab-1) -->
					<div class="col col-tab-1 J-tab">

						<!-- Tab切换触发器(tab) -->
							<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714290">
								<c:import url="/fragment/714290">
									<c:param value="${cacheKey}" name="cacheKey" />
								</c:import>
							</cache:fragmentPageCache>
						<!-- Tab切换触发器(tab) end -->

						<!-- 栏目主体(cont) -->
						<div class="cont">
							<div class="tab-cont J-tab-cont">
								<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714160">
									<c:import url="/fragment/714160">
										<c:param value="${cacheKey}" name="cacheKey" />
									</c:import>
								</cache:fragmentPageCache>
								<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714170">
									<c:import url="/fragment/714170">
										<c:param value="${cacheKey}" name="cacheKey" />
									</c:import>
								</cache:fragmentPageCache>
								<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714180">
									<c:import url="/fragment/714180">
										<c:param value="${cacheKey}" name="cacheKey" />
									</c:import>
								</cache:fragmentPageCache>
								<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714190">
									<c:import url="/fragment/714190">
										<c:param value="${cacheKey}" name="cacheKey" />
									</c:import>
								</cache:fragmentPageCache>
								<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714200">
									<c:import url="/fragment/714200">
										<c:param value="${cacheKey}" name="cacheKey" />
									</c:import>
								</cache:fragmentPageCache>
								<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714210">
									<c:import url="/fragment/714210">
										<c:param value="${cacheKey}" name="cacheKey" />
									</c:import>
								</cache:fragmentPageCache>
							</div>
						</div>
						<!-- 栏目主体(cont) end -->
					</div>
					<!-- Tab栏目(col-tab) end -->
				</div>
				<!-- 更多预售(mod-more-presell) end -->

				<!-- ################ mod end ################### -->

    		</div>
    		<!-- 1210通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">

    			<!-- ################ mod ################### -->

				<!-- 火热上架(mod-hot-shelves) -->
				<div class="mod-hot-shelves">
					
					<!-- 火热上架  begin-->
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714220">
							<c:import url="/fragment/714220">
								<c:param value="${cacheKey}" name="cacheKey" />
							</c:import>
						</cache:fragmentPageCache>
						<!-- 火热上架  end-->	
				</div>
				<!-- ################ mod end ################### -->

    		</div>
    		<!-- 1210通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">

    			<!-- ################ mod ################### -->

				<!-- 单行广告(mod-ad-row) -->
				<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714280">
					<c:import url="/fragment/714280">
						<c:param value="${cacheKey}" name="cacheKey" />
					</c:import>
				</cache:fragmentPageCache>
				<!-- 单行广告(mod-ad-row) end -->

				<!-- ################ mod end ################### -->
    			
    		</div>
    		<!-- 1210通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">

    			<!-- ################ mod ################### -->

				<!-- 本周到货(mod-week-arrival) -->
				<div class="mod-week-arrival">
					<!-- Tab栏目(col-tab-2) -->
					<div class="col col-base-1 col-slider-1 J-slider">

						<!-- 栏目主体(cont) -->
						<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714230">
						<c:import url="/fragment/714230">
							<c:param value="${cacheKey}" name="cacheKey" />
						</c:import>
						</cache:fragmentPageCache>
						<!-- 栏目主体(cont) end -->

						<!-- 附加内容区域(attach) -->
						<div class="attach">
							<a href="javascript:;" class="slider-prev iconfont J-slider-prev">&#xe602;</a>
							<a href="javascript:;" class="slider-next iconfont J-slider-next">&#xe601;</a>
						</div>
						<!-- 附加内容区域(attach) end -->

					</div>
					<!-- Tab栏目(col-tab) end -->
				</div>
				<!-- 本周到货(mod-week-arrival) end -->

				<!-- ################ mod end ################### -->

    		</div>
    		<!-- 1210通栏布局 end -->

    		<!-- 1210通栏布局 -->
    		<div class="grid-1210w grid-margin-bottom">

    			<!-- ################ mod ################### -->

				<!-- 爆款抢订(mod-explosion-buy) -->
				<div class="mod-explosion-buy">
					
					<!-- 基本栏目(col-base-1) -->
					<div class="col col-base-1">


						<!-- 栏目主体(cont) -->
						<div class="cont">
							<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_714240">
							<c:import url="/fragment/714240">
								<c:param value="${cacheKey}" name="cacheKey" />
							</c:import>
							</cache:fragmentPageCache>
						</div>
						<!-- 栏目主体(cont) end -->

					</div>
					<!-- 基本栏目(col) end -->

				</div>
				<!-- 爆款抢订(mod-explosion-buy) end -->

				<!-- ################ mod end ################### -->

    		</div>
    		<!-- 1210通栏布局 end -->
		</div>
    	<!-- 主体 end -->
</div>
    <!-- HTML5 布局 end -->
    <script type="text/javascript"
		src="${serverPrefix}js/booking.js?${version}"></script>
    <jsp:include page="../snippets/v2/footer.jsp"></jsp:include>
    <script type="text/javascript">
        var _ozurltail = "#${product.secondLevelCategoryId}";
        var _ozprm = "cid99=${productSale.bookStorageType}${product.category.id}&press=${product.manufacturer}&author=${product.author}";
    </script>
</body>
</html>
