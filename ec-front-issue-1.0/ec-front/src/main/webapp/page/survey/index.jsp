<%@page pageEncoding="UTF-8" %><%@include file="/page/snippets/tags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-${survey.title}</title>
<link href="${serverPrefix}css2/survey/style.css?${version}" rel="stylesheet" type="text/css" />
</head>
<body>	
<div class="survey" surveyId="${survey.id}">
<input type="hidden" value="<fmt:formatDate value="${start}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="start_time"/>
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
				    	<c:if test="${item.content.type == 2}"><h3 class="title">${item.content.title}</h3></c:if>
				    	<c:if test="${item.content.type == 3}"><h4 class="title">${item.content.title}</h4></c:if>
					</li>               
                </c:when>
                <c:otherwise>
					<c:choose>
                		<c:when test="${item.type == 'T'}">
							<li class="part text" itemId="${item.id}">
								<h4 class="title"><span class="subject">${item.content.title}</span></h4>
								<c:if test="${item.content.type == 'I'}"><input class="input_text" type="text" id="" name="text" size="50" maxlength="" value=""/></c:if>
								<c:if test="${item.content.type == 'T'}"><textarea name="textarea" cols="80" rows="5"></textarea></c:if>
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
															<tr><td><input type="radio" value="${surveyValue.id}" name="radio"/><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea  name="radio_text" cols="50" rows="5"></textarea></div></c:if></td></tr>
														</c:forEach>
													</c:if>
													<c:if test="${item.content.direction == 2}">
														<tr>
															<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
																<td><input type="radio" value="${surveyValue.id}" name="radio"/><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea  name="radio_text" cols="50" rows="5"></textarea></div></c:if></td>
															</c:forEach>
														</tr>													
													</c:if>
												</c:if>
												<c:if test="${item.content.type == 'C'}">
													<c:if test="${item.content.direction == 1}">
														<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
															<tr><td><input type="checkbox" value="${surveyValue.id}" name="checkbox" /><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea name="checkbox_text" cols="50" rows="5"></textarea></div></c:if></td></tr>
														</c:forEach>
													</c:if>
													<c:if test="${item.content.direction == 2}">
														<tr>
															<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
																<td><input type="checkbox" value="${surveyValue.id}" name="checkbox" /><label>${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><div name="more" style="display:none;"><textarea name="checkbox_text" cols="50" rows="5"></textarea></div></c:if></td>
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
<center><button class="submit" name="submit" id="submit" >提交问卷</button></center>
<input type="hidden" name="referer" value="${referer}"/>
</div>
<div class="content_bottom"></div>
<div class="footer">Copyright(C) 四川文轩在线电子商务有限公司 2000-2012, All Rights Reserved</div>
<script src="${serverPrefix}libs/core.js?${version}"></script>
<script src="${serverPrefix}js/survey/survey.js"></script>
</body>
</html>
