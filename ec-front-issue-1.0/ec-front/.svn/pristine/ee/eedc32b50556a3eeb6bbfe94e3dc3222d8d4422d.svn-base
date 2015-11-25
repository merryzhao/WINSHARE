<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<div class="col col-base-1" fragment="${fragment.id}" cachekey="${param.cacheKey }">
                        <div class="title">
                            <h2>${fragment.name }</h2>
                        </div>
                        <div class="cont">
                        <c:if test="${fn:length(contentList) <= 0}">
            	                      暂无内容
            	        </c:if>
                            <!-- list-text-1 -->
                            <ul class="list list-text-1">
                           <c:forEach var="content" items="${contentList}" varStatus="status">
 							<c:if test="${status.index<3}">
                                <li>
                                    <a href="${content.url}" target="_blank">${content.name}</a>
                                </li>
 						   </c:if>
                            </c:forEach>
                             </ul>
                             <c:forEach var="content" items="${contentList}" varStatus="status">
                           <c:if test="${status.index>2}">
                           
                            <div class="ad-base"><a href="${content.url}" target="_blank"><img src="${content.src }" alt="${content.name}"></a></div>
                           
                            </c:if>
                            </c:forEach>
                        </div>
                        
                    </div>
