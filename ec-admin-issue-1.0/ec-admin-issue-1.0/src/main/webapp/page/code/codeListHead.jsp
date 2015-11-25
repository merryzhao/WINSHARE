<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ include file="../snippets/tags.jsp"%>
<%@ include file="../snippets/meta.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/code/code.js"></script>
<link type="text/css" href="${pageContext.request.contextPath}/css/code/code.css" rel="stylesheet"/>
<span class="ui-icon ui-icon-triangle-1-s"></span>
							<a tabindex="-1">
								<div id="parent_${parentCode.id}_view">${parentCode.name
									}</div>
								<div id="parent_${parentCode.id}_update"
									class="codeFormAndNone" onclick="event.stopPropagation();">
									<form action="/code/${parentCode.id}" method="post" id="parent_${parentCode.id}_update_form">
										<input name="parent" type="hidden" value="1"/>
										<input name="_method" type="hidden" value="put"/> <input
											type="hidden" name="id" value="${parentCode.id}"> 名称：<input
											type="text" name="name" value="${parentCode.name }">
										描述：<input type="text" name="description"
											value="${parentCode.description }">
									<button type="button" onclick="
									ajaxpost('/code/${parentCode.id}','parent_${parentCode.id}_update_form','head_${parentCode.id}');">
									保存</button>
										<button type="button"
											onclick="visible('parent_${parentCode.id}_view');invisible('parent_${parentCode.id}_update');">取消</button>
									</form>
								</div></a>