<%@page pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@include file="../snippets/tags.jsp"%>
<c:set var="serverPrefix" value="http://static.winxuancdn.com/"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-${survey.title}</title>
<link type="text/css" href="${pageContext.request.contextPath}/css/redmond/jquery-ui-1.8.14.custom.css" rel="stylesheet"/>
<link href="${serverPrefix}css2/survey/style.css?20121127" rel="stylesheet" type="text/css" />
</head>
<body>	
<div class="survey">
<div class="top">
	<div class="logo_box">
		<a title="文轩网,新华书店" href="http://www.winxuan.com" target="_blank">
			<img alt="文轩网" src="${serverPrefix}css2/images/logo.png" data-lazy="false"/> 
		</a>
	</div>
	<h1 class="survey_title">${survey.title}</h1>
	<c:if test="${!empty survey.description}">
		<div class="description">${survey.description}</div>
	</c:if>
</div>
<ol class="content">
	<c:forEach items="${survey.items}" var="item" varStatus="status">
		<c:choose>
                <c:when test="${item.type == 'S'}">
					<li class="part separator">
				    	<c:if test="${item.content.type == 1}"><h2 class="title">${item.content.title}</h2></c:if>
				    	<c:if test="${item.content.type == 2}"><h4 class="title"><span class="subject">${item.content.title}</span></h4></c:if>
				    	<c:if test="${item.content.type == 3}"><h4 class="title"><span class="subject">${item.content.title}</span></h4></c:if>
					</li>               
                </c:when>
                <c:otherwise>
					<c:choose>
                		<c:when test="${item.type == 'T'}">
							<li class="part text" itemId="${item.id}">
								<h4 class="title">
								<span class="subject">${item.content.title}</span>
								<c:if test="${item.content.type == 'I'}">
									<c:forEach items="${answers}" var="answer" varStatus="status">
										<c:if test="${answer.item.id == item.id}">
											<c:set var="find" value="1"></c:set>
											<input class="input_text" type="text" id="" name="text" size="50" maxlength="" value="${answer.submit}"/>
										</c:if>
									</c:forEach>
									<c:if test="${find != 1}">
										<input class="input_text" type="text" id="" name="text" size="50" maxlength="" value=""/>
										<c:set var="find" value="0"></c:set>
									</c:if>
								</c:if>
								</h4>
								<c:if test="${item.content.type == 'T'}">
									<c:forEach items="${answers}" var="answer" varStatus="status">
										<c:if test="${answer.item.id == item.id}">
											<c:set var="find" value="1"></c:set>					
											<textarea name="textarea" cols="80" rows="5">${answer.submit}</textarea>
										</c:if>
									</c:forEach>
									<c:if test="${find != 1}">
										<textarea name="textarea" cols="80" rows="5"></textarea>
										<c:set var="find" value="0"></c:set>
									</c:if>
								</c:if>
							</li>                		
                		</c:when>  
                		<c:otherwise>
	                		<c:choose>
	                			<c:when test="${item.type == 'C'}">
									<li class="part select" itemId="${item.id}">
										<h4 class="title"><span class="subject">${item.content.title}</span></h4>
										<div class="content">
											<table class="options" maxSelect="${item.content.maxSelect}" minSelect="${item.content.minSelect}">
												<c:if test="${item.content.type == 'R'}">
													<c:if test="${item.content.direction == 1}">
														<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
															<c:forEach items="${answers}" var="answer" varStatus="status">
																<c:if test="${answer.value.id == surveyValue.id}">
																	<c:set var="radioFind" value="1"></c:set>					
																	<tr><td><input type="radio" checked="checked" value="${surveyValue.id}" name="radio${item.id}"/><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><textarea  name="radio_text" cols="50" rows="5">${answer.submit}</textarea></c:if></td></tr>
																</c:if>
															</c:forEach>														
															<c:if test="${radioFind != 1}">
																<tr><td><input type="radio" value="${surveyValue.id}" name="radio${item.id}"/><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea  name="radio_text" cols="50" rows="5"></textarea></div></c:if></td></tr>
															</c:if>	
															<c:set var="radioFind" value="0"></c:set>												
														</c:forEach>
													</c:if>
													<c:if test="${item.content.direction == 2}">
														<tr>
															<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
																<c:forEach items="${answers}" var="answer" varStatus="status">
																	<c:if test="${answer.value.id == surveyValue.id}">
																		<c:set var="radioFind" value="1"></c:set>					
																		<td><input type="radio" checked="checked" value="${surveyValue.id}" name="radio${item.id}"/><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><textarea  name="radio_text" cols="50" rows="5">${answer.submit}</textarea></c:if></td>
																	</c:if>
																</c:forEach>														
																<c:if test="${radioFind != 1}">
																	<td><input type="radio" value="${surveyValue.id}" name="radio${item.id}"/><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea  name="radio_text" cols="50" rows="5"></textarea></div></c:if></td>
																</c:if>	
																<c:set var="radioFind" value="0"></c:set>																		
															</c:forEach>
														</tr>													
													</c:if>
												</c:if>
												<c:if test="${item.content.type == 'C'}">
													<c:if test="${item.content.direction == 1}">
														<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
																<c:forEach items="${answers}" var="answer" varStatus="status">
																	<c:if test="${answer.value.id == surveyValue.id}">
																		<c:set var="checkboxFind" value="1"></c:set>					
																		<tr><td><input type="checkbox" value="${surveyValue.id}" checked="checked" name="checkbox" /><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><textarea name="checkbox_text" cols="50" rows="5">${answer.submit}</textarea></c:if></td></tr>
																	</c:if>
																</c:forEach>														
																<c:if test="${checkboxFind != 1}">
																	<tr><td><input type="checkbox" value="${surveyValue.id}" name="checkbox" /><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea name="checkbox_text" cols="50" rows="5"></textarea></div></c:if></td></tr>
																</c:if>	
																<c:set var="checkboxFind" value="0"></c:set>
														</c:forEach>
													</c:if>
													<c:if test="${item.content.direction == 2}">
														<tr>
															<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
																<c:forEach items="${answers}" var="answer" varStatus="status">
																	<c:if test="${answer.value.id == surveyValue.id}">
																		<c:set var="checkboxFind" value="1"></c:set>					
																		<td><input type="checkbox" value="${surveyValue.id}" checked="checked" name="checkbox" /><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><textarea name="checkbox_text" cols="50" rows="5">${answer.submit}</textarea></c:if></td>
																	</c:if>
																</c:forEach>														
																<c:if test="${checkboxFind != 1}">
																	<td><input type="checkbox" value="${surveyValue.id}" name="checkbox" /><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea name="checkbox_text" cols="50" rows="5"></textarea></div></c:if></td>
																</c:if>	
																<c:set var="checkboxFind" value="0"></c:set>																
																
															</c:forEach>
														</tr>
													</c:if>
												</c:if>
											</table>
										</div>
									</li>	                			
	                			</c:when>
	                		</c:choose>
                		</c:otherwise>
                	</c:choose>            	
                </c:otherwise>
        </c:choose>
	</c:forEach>
</ol>
</div>
<div class="content_bottom"></div>
<div class="footer">Copyright(C) 四川文轩在线电子商务有限公司 2000-2012, All Rights Reserved</div>
</body>
</html>
