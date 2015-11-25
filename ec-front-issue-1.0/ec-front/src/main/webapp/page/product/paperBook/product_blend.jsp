<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<div class="unit">
               
                <div class="col col-base-2">
                <a name="p-const"></a>
                    <div class="title">
                        <div class="tab">
                            <div class="tab-item"><a href="#p-intro">商品介绍</a></div>
                            <div class="tab-item"><a href="#p-comm">商品评论</a><a class="link" href="/product/${productSale.id}/comment/list" target="_blank">(${commentCount+tmallCommentCount}条)</a></div>
                            <div class="tab-item current"><a href="#p-const">商品咨询</a><a class="link" href="/product/${productSale.id}/question/list" target="_blank">(${questionCount}条)</a></div>
                        </div>
                    </div>
                    <div class="cont">
                        <!-- master-consult -->
                        <div class="master-consult">

                            <!-- consult-policy --> 
                            <div class="consult-policy">
                                <div class="consult-policy-item ">
                                   <span class="tag">正品</span>
                                    <p class="item-product">文轩网作为纸质出版物网络销售平台，所售图书均为全新正版，请放心购买。</p>
                                </div>
                                <div class="consult-policy-item">
                                    <span class="tag">运费</span>
                                   <p> 文轩网图书/音像单笔订单满38元免运费（内蒙古、青海、海南、新疆、西藏、宁夏及海外地区除外）。未满38元全国运费5元/单。</p>
                                </div>
                                <div class="consult-policy-item">
                                   <span class="tag">配送</span>
                                <p>与文轩网合作的物流公司有：申通/圆通/CCES/港中能达/邮政经济快递/邮政平邮等。（发货系统自动匹配，暂时无法指定快递）</p>
                                </div>
                                <div class="consult-policy-item consult-policy-question">
                                    <span class="text">没有解决您的问题？</span>
                                    <a href="http://www.winxuan.com/product/${productSale.id}/question/list" class="consult-btn" title="我要咨询"></a>
                                </div>
                            </div>
                            
                         
						<c:choose>
				        <c:when test="${not empty customerQuestions}">
				            <c:forEach var="customerQuestion" items="${customerQuestions}"
						varStatus="status">
                            <div class="common-item">
                                <div class="common-user">
                                    <c:choose>
										<c:when test="${not empty customerQuestion.customer }">
											<c:choose>
												<c:when test="${not empty customerQuestion.customer.avatar }">
                                    			<div class="img">
													<img src="${customerQuestion.customer.avatar }" alt="头像" />
                                    			</div>
												</c:when>
												<c:otherwise>
													<c:if test="${customerQuestion.customer.grade == 0 }"> <div class="img img1" ></div></c:if>
													<c:if test="${customerQuestion.customer.grade == 1 }"> <div class="img img2" ></div></c:if>
													<c:if test="${customerQuestion.customer.grade == 2 }"> <div class="img img3" ></div></c:if>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<img src="../../goods/comment_true.jpg" alt="头像" />
										</c:otherwise>
										</c:choose>
                                    <div class="name"><winxuan-string:substr length="8" content="${customerQuestion.customer.protectionName }"></winxuan-string:substr></div>
                                    
                                    <c:if test="${customerQuestion.customer.grade == 0 }"><div class="class class1">铜牌会员</div><!-- 根据用户等级class1~n --></c:if>
									<c:if test="${customerQuestion.customer.grade == 1 }"><div class="class class2">银牌会员</div><!-- 根据用户等级class1~n --></c:if>
									<c:if test="${customerQuestion.customer.grade == 2 }"><div class="class class3">金牌会员</div><!-- 根据用户等级class1~n --></c:if>
                                </div>
                                <div class="common-cont">
                                    <dl class="consult-normal-list">
                                        <dt><b>${customerQuestion.title}</b><span class="time"><fmt:formatDate value="${customerQuestion.askTime}"
									pattern="yyyy-MM-dd" /></span></dt>
                                        <dd>${customerQuestion.content}</dd>
                                        

                            <c:if test="${not empty  customerQuestion.replyList}">
							<c:forEach var="reply" items="${customerQuestion.replyList}">
								<c:choose>
									<c:when test="${not empty reply.replier}">
										<c:choose>
											<c:when test="${reply.replier.protectionName == '文轩客服'}">
											    <dd class="consult-reply">
												<div class="consult-reply-title"><b>文轩客服</b>回复说：
												<span class="time"><fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd" /></span>
												</div>
												<div class="consult-reply-text">${reply.content}</div>
												</dd>
											</c:when>
											<c:otherwise>
											     <dd class="consult-reply user-reply">
												<div class="consult-reply-title"><b><winxuan-string:substr length="8" content="${reply.replier.protectionName }"></winxuan-string:substr></b>回复说：
												<span class="time"><fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd" /></span>
												</div>
												<div class="consult-reply-text">${reply.content}</div>
												</dd>
											</c:otherwise>
										</c:choose>
									
									</c:when>
									<c:otherwise>
									    <dd class="consult-reply user-reply">
												<div class="consult-reply-title"><b>游客</b>回复说：
												<span class="time"><fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd" /></span>
												</div>
												<div class="consult-reply-text">${reply.content}</div>
												</dd>
									</c:otherwise>
									</c:choose>
							</c:forEach>
							
						</c:if>
						
					<dd class="consult-avtive cf"><a class="consult-btn-1 fr" bind="reply" href="javascript:void(0);">回复</a>
					   <div class="consult-avtive-reply" style="display:none;">
                            <div class="reply-floor">
                                <div class="reply-floor-active">
                                    <div class="reply-floor-arrow"></div>
                                    <p><s>回复</s></p>
                                    <p><input type="text" class="text"><span>0/300</span><a class="consult-btn-1" bind="replySubmit" data-question-id="${customerQuestion.id}" href="javascript:void(0);">提交</a></p>
                                </div>
                            </div>
                        </div>  
					</dd>
                    
                </dl>
                <div class="consult-cont-arrow"></div>
					</div>
					</div>
                     </c:forEach>
                        <div class="consult-master-avtive"><a class="comment-btn-3" href="http://www.winxuan.com/product/${productSale.id}/question/list" title="我要咨询"></a><a href="/product/${productSale.id}/question/list" class="common-all">查看所有咨询  &gt;&gt;</a></div>
				            </c:when>
				           
                            <c:otherwise>
                              
                            <div class="consult-item common-item-none">
                                <span class="text">暂无提问，您可以咨询我们，我们会在一个工作日内响应~</span>
                                <div><a href="/product/${productSale.id}/question/list" class="common-all">查看所有咨询  &gt;&gt;</a></div>
                            </div>
                           
                           </c:otherwise>
                           </c:choose>
                         

                        </div>

                        </div>


                    </div>

                    </div>