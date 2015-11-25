<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
     	<div class="detail_info">
       <ul class="price_info">	
                    <li>文&nbsp;轩&nbsp;价：<b class="fb">￥${productSale.effectivePrice}</b>&nbsp;<a class="link4" bind="pricecutNotify" href="javascript:;" data-id="${productSale.id }">降价通知我</a></li>
                    <li>定&nbsp;&nbsp;&nbsp;&nbsp;价：￥${product.listPrice }</li>
                    <li>折&nbsp;&nbsp;&nbsp;&nbsp;扣：<span class="red fb">${productSale.integerDiscount}折</span> （为你节省<span class="red fb">￥${product.listPrice-productSale.effectivePrice}</span>）</li>
                   	<li>库&nbsp;&nbsp;&nbsp;&nbsp;存：
                    <c:choose>
                    <c:when test="${productSale.supplyType.id != 13102 && productSale.supplyType.id !=13103}">
                    		<c:choose>
                    			<c:when test="${productSale.canSale}">
                    			 	<c:choose>
                    			 		<c:when test="${productSale.avalibleQuantity<=5}">
                    			 			<span class="red fb">仅剩${productSale.avalibleQuantity}本</span>
                    			 		</c:when>
                    			 		<c:otherwise>
                    			 			<span class="red fb">现在有货</span>
                    			 		</c:otherwise>
                    			 	</c:choose>	            				
                    			</c:when>
                    			<c:otherwise>
                    				<span class="red fb">现在没货</span>（建议点击下面的“到货通知”）
                    			</c:otherwise>
                    		</c:choose>
                	</c:when>
                	<c:otherwise>
                		<c:if test="${productSale.supplyType.id == 13102}">
                				<c:choose>
                					<c:when test="${productSale.preSaleCanBuy }">
                						<span class="red fb">预售商品</span><c:if test="${!empty productSale.booking.presellDescription}">（预计发货时间${productSale.booking.presellDescription}）</c:if>
                					</c:when>
                					<c:otherwise>
                						<span class="red fb">现在没货</span>（建议点击下面的“到货通知”）
                					</c:otherwise>
                				</c:choose>
                		</c:if>
                		<c:if test="${productSale.supplyType.id == 13103}">
               				<span class="red fb">订购商品</span>（通常5-8天从北京发货）
                		</c:if>
                	</c:otherwise>
                	</c:choose>	
                	<a class="link4" href="javascript:;" bind="tips">配送说明</a>
					<div class="tips">
						商品出库后，到货时间一般为3-5天(成都市区1天)，配送方式详情请见<a href="http://www.winxuan.com/help/logistic_scope.html" class="link1"  target="_blank">配送时间范围</a>以及<a href="http://www.winxuan.com/help/logistic_post_payment.html" class="link1" target="_blank">货到付款区域</a>;<br>更多疑问请看<a href="http://www.winxuan.com/help/logistic_charge.html" class="link1" target="_blank">配送收费标准</a>和<a href="http://www.winxuan.com/help/logistic_company.html" class="link1" target="_blank">快递公司查询</a>
					</div>
					</li>
                </ul>
                
                <dl class="ohter_info">         
                    <c:forEach items="${product.finalAuthorInfo}" var="tmpMap">  
                    	<c:forEach items="${tmpMap}" var="entry">
					      <dd> ${entry.key}：${entry.value} </dd>
					      </c:forEach>
					</c:forEach>
                    
                    <dd>出&nbsp;版&nbsp;社：<a target="_bland" class="link1" href="http://search.winxuan.com/search?manufacturer=<winxuan-string:encode content='${product.manufacturer }' type='utf-8'/>" title='${product.manufacturer }'>${product.manufacturer}</a></dd>
                    <dd>出版时间：<fmt:formatDate value="${product.productionDate }" pattern="yyyy-MM-dd"/></dd>                     
                     <c:if test="${!empty promotions }"> 
                    <dd><label>促销信息：</label><c:forEach items="${promotions}" var = "promotion"><c:if test ="${!empty promotion.advertUrl }"><p class="promotion_info"><a href = "${promotion.advertUrl}" title="${promotion.description}" style="color: red;">该商品参加了${promotion.type.name},<c:out value="${promotion.title}"></c:out></a>,${promotion.advert}</p></c:if>
                     <c:if test="${empty promotion.advertUrl }">
                     <span class="promotion_info_noUrl" style="color:red;">
                   		  该商品参加了${promotion.type.name},${promotion.title}
                     </span>
                     <c:if test="${!empty promotion.advert}">,${promotion.advert}</c:if>
                     </c:if>
                    </c:forEach>
                    </dd>
                	</c:if>
                    <dt><a class="ilike" href="javascript:;"><span class="red" ><b dig="dig" id="totalDigging"></b></span></a></dt>
                </dl>
                <table class="addbuttons">
                 <c:choose>
                 <c:when test="${productSale.supplyType.id == 13102}">              
                      	               		
                      			<%-- <c:if test="${productSale.preSaleHasShow || productSale.preSaleCanBuy}">
                      					新品预售
                    				<c:if test="${!empty productSale.booking}">
                    					(该商品预售时间：<fmt:formatDate value="${productSale.booking.startDate }" pattern="yyyy-MM-dd"/> 到  <fmt:formatDate value="${productSale.booking.endDate }" pattern="yyyy-MM-dd"/>)<br/>
                    					<c:if test="${!empty productSale.booking.description }">
                    						<c:out value="${productSale.booking.description}"></c:out>
                    					</c:if>
                    				</c:if>
                      			</c:if>  --%>
                      			<c:choose>
                      			<c:when test="${productSale.preSaleCanBuy}">
                      				<tr>
                       					 <td colspan="2" class="hei45"><b class="fb">我要买：</b>
                           			 		<input class="goods_num" type="text" size="3" value="1"/>件 
                           			 		</td>
                  					 </tr>
                   					 <tr>
                     			  		<td><button class="order_butb" bind="presell" data-id="${productSale.id}" data-region="成都" data-date=" <fmt:formatDate value="${productSale.booking.endDate }" pattern="yyyy年MM月dd日"/>"></button></td>
                       			 		<td class="hei100">
                       			 		<c:if test="${!empty productSale.EBook.productSeptember}">
				                               <a class="coll_but_medi" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
												<a	href="http://www.winxuan.com/product/${productSale.EBook.id}" target="_blank" class="ebook_buy_medi">
					                            </a>
                 							   </c:if>
                 							   
                 							    <c:if test="${empty productSale.EBook.productSeptember}">
				                                  <a class="coll_but_big" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
                 							   </c:if>
                 					</td>
                   					 </tr>
                      			</c:when>
                      			<c:otherwise>
                      				<tr>
                        				<td><button class="notice_butb" bind="arrivalNotify" data-id="${productSale.id}"></button></td>
                       					<td><a class="coll_but" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a></td>
                    				</tr>
                      			</c:otherwise>
                      			</c:choose>       		      		
                  		</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${productSale.canSale}">
										<tr >
											<td class="hei45" colspan="2"><b class="fb">我要买：</b> <input class="goods_num" type="text" size="3" value="1"/> 件
											</td>
										</tr>
										<tr>
											<td><a href="http://www.winxuan.com/shoppingcart/modify?p=${productSale.id}" id="addtocart" class="addtocart" bind="addToCart" data-id="${productSale.id}" data-ref="input.goods_num" target="_blank">
											</a>
											</td>
											<td class="hei100">
				                               <c:if test="${!empty productSale.EBook.productSeptember}">
				                               <a class="coll_but_medi" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
												<a	href="http://www.winxuan.com/product/${productSale.EBook.id}" target="_blank" class="ebook_buy_medi">
					                            </a>
                 							   </c:if>
                 							   
                 							    <c:if test="${empty productSale.EBook.productSeptember}">
				                                  <a class="coll_but_big" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
                 							   </c:if>
											</td>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td class="hei45"  colspan="2"><b class="fb">我要买：</b> <input class="goods_num" type="text" size="3" value="1" disabled="disabled"/> 件
											</td>
										</tr>
										<tr>
											<td><button class="notice_butb" bind="arrivalNotify" data-id="${productSale.id}"></button>
											</td>
											<td class="hei100">
												 <c:if test="${!empty productSale.EBook.productSeptember}">
													<a class="coll_but_medi" href="javascript:;" id="addToFavorite" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
														<a	href="http://www.winxuan.com/product/${productSale.EBook.id}"  class="ebook_buy_medi">
														</a>
												 </c:if>
												          <c:if test="${empty productSale.EBook.productSeptember}">
																                                  <a class="coll_but_big" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏备选</a>
												            </c:if>
											</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
                </table>
                <dl class="ohter_info">
                    <dd>发货方：文轩网 &nbsp;&nbsp;&nbsp;&nbsp;卖家：${productSale.shop.shopName}</dd>
                </dl>
                
                </div>