<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.winxuan.com/cache" prefix="cache"%>

<!-- header -->
    <header class="head">
    		
   		<cache:fragmentPageCache idleSeconds="86400"  key="FRAGMENT_3020"> 
        	<c:import url="/fragment/3020">
        		<c:param value="${cacheKey}" name="cacheKey"/> 
        	</c:import>
       	</cache:fragmentPageCache>	
    
        <div class="line top-nav-wrap">

            <!-- top-nav -->
            <div class="unit top-nav cf">
                <ul class="list link-list fl">
                    <li><a href="javascript:void(0);" class="add_favorite"><b class="icon16 icon16-1"></b>收藏文轩网</a></li>
                </ul>
                <ul class="list link-list link-list-user fr cf">
                    <li><span class="welcome">您好，欢迎光临文轩网！</span><!-- 已登录状态： <span class="welcome">您好，<a href="#">yourname</a></span> --></li>
                    <li class="login-box"><a href="https://passport.winxuan.com/signin" target="_blank">【登录】</a><a href="https://passport.winxuan.com/signup" target="_blank">【免费注册】</a><!-- 已登录状态： <a href="#">退出</a> --></li>
                    <li class="li-1"><s></s><a class="link-user" href="http://www.winxuan.com/customer/order" target="_blank">我的订单</a></li>
                    <li class="li-2"><s></s><a class="link-user" href="http://www.winxuan.com/giftcard" target="_blank">礼品卡</a></li>
                    <li class="li-3"><s></s><a class="link-user link-user-icon" href="http://www.winxuan.com/help/shopping_insurance.html" target="_blank"><span class="outline"></span><span class="overlayout"></span>新手入门<b></b></a>
                        <div class="pop">
                            <ul class="pop-user-list">
                                <li><a href="http://www.winxuan.com/help/shopping_insurance.html" target="_blank">购物保障</a></li>
                                <li><a href="http://www.winxuan.com/help/shopping_flow.html" target="_blank">购物流程</a></li>
                                <li><a href="http://www.winxuan.com/help/member_service.html" target="_blank">会员介绍</a></li>
                                <li><a href="http://www.winxuan.com/help/faq.html" target="_blank">常见问题</a></li>
                            </ul>
                        </div>
                    </li>
                    <li class="li-3"><s></s><a class="link-user link-user-icon" href="http://www.winxuan.com/help/help_center.html" target="_blank"><span class="outline"></span><span class="overlayout"></span>帮助中心<b></b></a>
                        <div class="pop">
                            <ul class="pop-user-list">
                                <li><a href="http://www.winxuan.com/help/delivery_info_dynamic_query.html" target="_blank">配送方式</a></li>
                                <li><a href="http://www.winxuan.com/help/payment_on_arrival.html" target="_blank">货到付款</a></li>
                                <li><a href="http://www.winxuan.com/customer/returnorder" target="_blank">退换货</a></li>
                                <li><a href="http://www.winxuan.com/customer/order" target="_blank">订单状态</a></li>
                                <li><a href="http://www.winxuan.com/help/invoice.html" target="_blank">发票制度</a></li>
                            </ul>
                        </div>
                    </li>
                    <li class="li-3"><s></s><a class="link-user link-user-icon" href="#"><span class="outline"></span><span class="overlayout"></span>客户服务<b></b></a>
                        <div class="pop">
                            <ul class="pop-user-list">
                                <li><a href="http://www.winxuan.com/help/contact.html" target="_blank">客服中心</a></li>
                                <li><a href="http://www.winxuan.com/help/complaint.html" target="_blank">投诉建议</a></li>
                            </ul>
                        </div>
                    </li>
                    <li class="li-3 li-4"><s></s><a class="link-user link-user-icon" href="#"><span class="outline"></span><span class="overlayout"></span><i class="mobile-icon"></i>手机文轩<b></b></a>
                        <div class="pop mobile-pop">
                            <div class="mobile-code cf">
                                <div class="mobile-code-item fl"><img data-lazy="false" src="http://static.winxuancdn.com/css/v2/images/code1.png" alt="" ><a class="btn-1" href="http://www.winxuan.com/help/quickmark_wenxuan.html" target="_blank">文轩手机购物 客户端 &gt;</a></div>
                                <div class="mobile-code-item fr"><img data-lazy="false" src="http://static.winxuancdn.com/css/v2/images/code2.png" alt="" ><a class="btn-2" href="http://ebook.winxuan.com/cms/download" target="_blank">电子书 客户端 &gt;</a></div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- top-nav end -->


        </div>
        <%-- 安全提示 --%>
        <div id="customerUpdatePwd" style="display: none;"  class="customerUpdatePwd" >尊敬的用户:您的密码目前存在安全隐患,为了保证您的账号安全，请立即<a href="http://www.winxuan.com/customer/updatePassword">修改密码</a></div>
       	<cache:fragmentPageCache idleSeconds="86400" key="FRAGMENT_3021">
       		<c:import url="http://www.winxuan.com/fragment/3021">
       			<c:param value="${cacheKey}" name="cacheKey" />
       		</c:import>
       	</cache:fragmentPageCache>
        <div class="line head-content">
        <div class="master-header cf">

            <!-- master-logo -->
            <div class="unit master-logo fl"><a href="http://www.winxuan.com"><img src="http://static.winxuancdn.com/css/v2/images/logo.png" alt="文轩网"></a></div>
            <!-- master-logo end -->

            <!-- master-search -->
            <div class="unit master-search fl">
                <form method="get" action="http://search.winxuan.com/search" name="searchForm">
                    <div class="master-search-box cf">
                        <%--
                        <div class="search-select fl"><span class="select_category_name">全部商品</span><b></b>
                            <div class="search-select-pop" style="display: none;">
                                <ul class="search-select-list">
                                    <li><a data-key="" href="javascript:void(0);">全部商品</a></li>
                                    <li><a data-key="11001" href="javascript:void(0);">图书</a></li>
                                    <li><a data-key="11002" href="javascript:void(0);">音像</a></li>
                                    <li><a data-key="11004" href="javascript:void(0);">电子书</a></li>
                                    <li><a data-key="11003" href="javascript:void(0);">百货</a></li>
                                </ul>
                            </div>
                        </div>
                        --%>
                        <input class="master-search-input fl" type="text" autocomplete="off" name="keyword">
                        <input type="hidden" value="" name="type">
                        <button class="master-search-btn fl" type="submit">搜索</button>
                        <a class="master-search-adv fl" href="http://search.winxuan.com/advanceSearch" target="_blank">高级搜索</a>
                    </div>
                </form>
                  <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_3006">
              <c:import url="/fragment/3006" >
              <c:param value="${cacheKey}" name="cacheKey" />
              </c:import>
              </cache:fragmentPageCache>
            </div>
            <!-- master-search end -->

            <!-- master-user -->
            <div class="unit master-user fr cf">

                <!-- master-user-my -->
                <div class="master-user-my fl">
                    <div class="master-user-box">
                        <b></b><a href="http://www.winxuan.com/customer" class="master-user-btn" target="_blank ">我的文轩</a><s></s>
                        <span class="outline"></span><span class="topline"></span><span class="overlayout"></span>
                        <div class="master-user-pop">
                            <div class="user-active">
                                您好，请<a href="https://passport.winxuan.com/signin" target="_blank">【登录】</a>&nbsp;&nbsp;新用户请<a href="https://passport.winxuan.com/signup" target="_blank">【免费注册】</a><!-- 已登录状态：您好，username! -->
                            </div>
                            <div class="user-link cf">
                                <ul class="user-link-list user-link-list-1 fl">
                                    <li><a class="link" href="http://www.winxuan.com/customer" target="_blank">我的帐户 &gt;</a></li>
                                    <li><a class="link" href="http://www.winxuan.com/customer/favorite" target="_blank">我的收藏 &gt;</a></li>
                                    <li><a class="link" href="http://www.winxuan.com/customer/question" target="_blank">我的咨询 &gt;</a></li>
                               </ul>
                                <ul class="user-link-list fl">
                                    <li><a class="link" href="http://www.winxuan.com/customer/order" target="_blank">我的订单 &gt;</a></li>
                                    <li><a class="link" href="http://www.winxuan.com/customer/comment/product" target="_blank">我的评论 &gt;</a></li>
                                    <li><a class="link" href="http://www.winxuan.com/customer/presentcard" target="_blank">我的礼品卡 &gt;</a></li>
                                </ul>
                            </div>
                              <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_3009">
                            <c:import url="/fragment/3009" >
                            <c:param value="${cacheKey}" name="cacheKey" />
                            </c:import>
                            </cache:fragmentPageCache>
                        </div>
                    </div>
                    <s></s>
                </div>
                <!-- header end -->
				
                <!-- master-user-my end -->

                <div class="master-user-sc fr">
                    <div class="master-user-box">
                        <b></b><a href="http://www.winxuan.com/shoppingcart" class="master-user-btn master-user-sc-btn" target="_blank">购物车</a><s></s>
                        <span class="outline"></span><span class="topline"></span><span class="overlayout"></span>
                        <div class="master-cartnum-pop">0</div>
                        <div class="master-user-pop master-user-sc-pop">
                            
                            <!-- col-base-1 -->
                            <div class="col col-base-1 col-user-sc">
                                <!-- 热门活动 -->
                               <cache:fragmentPageCache idleSeconds="86400"
								key="FRAGMENT_3009">
                            <c:import url="/fragment/3009" >
                            <c:param value="${cacheKey}" name="cacheKey" />
                            </c:import>
                            </cache:fragmentPageCache>
                                <!-- 热门活动 end -->
                            </div>
                            <!-- col-base-1 end -->

                        </div>
                        <s></s>
                    </div>
                </div>

            </div>
            <!-- master-user end -->
        </div>
         </div>
    </header>
<script>
	seajs.use("header", function(header) {
		header.init();
	})
</script>