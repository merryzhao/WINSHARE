<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>

                <c:if test="${!empty limitBuy }">
                <div class="unit limit_buy">

                    <!-- col-base-1 -->
                    <div class="col col-base-1">
                        <div class="title">
                            <h2>疯狂抢购</h2>
                        </div>   
                        <div class="cont" fragment="55" cachekey="${param.cacheKey }">
                            <!-- list-base-2 -->
                            <ul class="list list-base-2">
                             <c:forEach var="limitbuy" items="${limitBuy}">
                                <!-- list-base-2 li (loop) -->
                                <c:if test="${limitbuy.currentPromotion[1]>0}">
                                <li  end="${limitbuy.currentPromotion[1]}" bind="limit">
                                    <div class="cell cell-style-2">
                                        <div class="time">疯抢剩余时间：<b bind="hour">00</b>时<b bind="min">00</b>分<b bind="sec">00</b>秒</div>
                                        <div class="img"><a href="${limitbuy.url}" target="_blank"><img src="${limitbuy.product.imageUrlFor160px}" alt="${limitbuy.name}"></a></div>
                                        <div class="attr">
                                            <div class="price-n">￥${limitbuy.effectivePrice}</div>
                                            <div class="active"><a class="btn btn-1" href="${limitbuy.url}" target="_blank"></a></div>
                                        </div>
                                        <div class="attr cf">
                                            <div class="price-o">
                                                <span class="text-1">原价<br><b class="color-1">￥${limitbuy.listPrice}</b></span>
                                                <span class="text-2">折扣<br><b class="color-2">${fn:substringBefore(limitbuy.discount*100,".")}折</b></span>
                                                <span class="text-3">节省<br><b class="color-3">￥${limitbuy.listPrice-limitbuy.effectivePrice}</b></span>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                </c:if>
                                <!-- list-base-b li (loop) end -->
							</c:forEach>
                            </ul>
                            <!-- list-base-2 end -->

                            
                        </div>
   </div>
                    <!-- col-base-1 end -->

                </div>
                </c:if>