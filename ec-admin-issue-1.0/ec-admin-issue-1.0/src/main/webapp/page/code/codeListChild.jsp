<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ include file="../snippets/tags.jsp"%>

							<c:forEach var="childCode" items="${parentCode.children}"
								varStatus="forEachChild">
								<div
									id="child_${childCode.id}_view"
									class="codeChild">
									<label class="codeName">${childCode.name }</label> <label
										class="codeDescription">${childCode.description }</label>
									<c:if test="${parentCode.editable&&childCode.editable}">
										<label class="codeAvailable"
											>
											<c:if test="${childCode.available}">
												<input type="checkbox" checked="checked" onchange="ajaxget('/code/${childCode.id}/available','','child_${parentCode.id}');">
								已启用
								</c:if> <c:if test="${!childCode.available}">
												<input type="checkbox" onchange="ajaxget('/code/${childCode.id}/available','','child_${parentCode.id}');">
								未启用
								</c:if> </label>
									</c:if>
									<c:if test="${parentCode.editable&&childCode.editable}">
										<a class="codeUpdate" href="javascript:void(0)"
											onclick="invisible('child_${childCode.id}_view');visible('child_${childCode.id}_update');">
											编辑</a>
										<a class="codeUp" href="javascript:void(0)" onclick="ajaxget('/code/${childCode.id}/up','','child_${parentCode.id}',false);"> 
										<img   src="${pageContext.request.contextPath}/imgs/code/up.jpg"
										/>
                                        </a>
                                        <a class="codeDown" href="javascript:void(0)" onclick="ajaxget('/code/${childCode.id}/down','','child_${parentCode.id}',false);"> 
										<img  src="${pageContext.request.contextPath}/imgs/code/down.jpg"
										/>
										</a>
									</c:if>
								</div>
								<!-- 修改子节点 -->
								<div
									id="child_${childCode.id}_update"
									class="codeFormAndNone">
									<form id="child_${childCode.id}_update_form" action="/code/${childCode.id}" method="post">
									<input name="parent" type="hidden" value="0"/>
										<input name="_method" type="hidden" value="put" /> <input
											type="hidden" name="id" value="${childCode.id}"> 名称：<input
											type="text" name="name" value="${childCode.name }">
										描述：<input type="text" name="description"
											value="${childCode.description }">
										<button type="button" onclick="ajaxpost('/code/${childCode.id}','child_${childCode.id}_update_form','child_${parentCode.id}');">保存</button>
										<button type="button"
											onclick="visible('child_${childCode.id}_view');invisible('child_${childCode.id}_update');">取消</button>
									</form>
								</div>
							</c:forEach>
							<!-- 添加子节点 -->
							<div id="child_${parentCode.id}_add"
								class="codeFormAndNone">
								<form action="/code/new" method="post" id="child_${parentCode.id}_add_form">
									<input type="hidden" name="parentId" value="${parentCode.id}">
									名称：<input type="text" name="name"> 描述：<input
										type="text" name="description">
									<button type="button" onclick="
									ajaxpost('/code/new','child_${parentCode.id}_add_form','child_${parentCode.id}');">
									保存</button>
									<button type="button"
										onclick="invisible('child_${parentCode.id}_add');">取消</button>
								</form>
							</div>
							<!-- 操作按钮 -->
							<div class="codeChildEnd">
								<c:if test="${parentCode.editable}">
									<button
									onclick="visible('child_${parentCode.id}_add');">新增</button>
									<button
										onclick="invisible('parent_${parentCode.id}_view');visible('parent_${parentCode.id}_update');">编辑父节点</button>
								</c:if>
							</div>
