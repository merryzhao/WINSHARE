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
<div class="part_toolbar">
    <button type="button" id="add_select_radio"><img src="${serverPrefix}css2/survey/choise.gif" width="16" height="16" />单选题</button>
    <button type="button" id="add_select_checkbox"><img src="${serverPrefix}css2/survey/checkbox.gif" width="16" height="16" />多选题</button>
    <button type="button" id="add_text_input"><img src="${serverPrefix}css2/survey/singletext.gif" width="16" height="16" />填空题</button>
    <button type="button" id="add_text_textarea"><img src="${serverPrefix}css2/survey/textbox.gif" width="16" height="16" />问答题</button>
    <button type="button" id="add_separator"><img src="${serverPrefix}css2/survey/separator.gif" width="16" height="16" />分隔符</button>
</div>
<div class="survey">
<div class="top">
	<div class="logo_box">
		<a title="文轩网,新华书店" href="http://www.winxuan.com" target="_blank">
			<img alt="文轩网" src="${serverPrefix}css2/images/logo.png" data-lazy="false"/> 
		</a>
	</div>
	<h1 class="survey_title">${survey.title}</h1>
	<div class="float_buttons">
        <button type="button" name="survey_edit" title="编辑" edit="${survey.id}"><img src="${serverPrefix}css2/survey/edit.gif"/></button>
   	</div>	
	<c:if test="${!empty survey.description}">
		<div class="description">${survey.description}</div>
	</c:if>
</div>
<ol class="content">
	<c:forEach items="${survey.items}" var="item" varStatus="status">
		<c:choose>
                <c:when test="${item.type == 'S'}">
					<li class="part separator">
						<div class="float_buttons">
					         		<button type="button" name="edit" title="编辑" edit="${item.content.id}"><img src="${serverPrefix}css2/survey/edit.gif"/></button> 
							        <button type="button" name="moveup" title="上移" moveup="${item.id}"><img src="${serverPrefix}css2/survey/up.gif"/></button>
							        <button type="button" name="movedown" title="下移" movedown="${item.id}"><img src="${serverPrefix}css2/survey/down.gif"/></button>
					        <button type="button" name="remove" title="删除" remove="${item.id}"><img src="${serverPrefix}css2/survey/del.gif"/></button>
				    	</div>
				    	<c:if test="${item.content.type == 1}"><h2 class="title">${item.content.title}</h2></c:if>
				    	<c:if test="${item.content.type == 2}"><h4 class="title"><span class="subject">${item.content.title}</span></h4></c:if>
				    	<c:if test="${item.content.type == 3}"><h4 class="title"><span class="subject">${item.content.title}</span></h4></c:if>
					</li>               
                </c:when>
                <c:otherwise>
					<c:choose>
                		<c:when test="${item.type == 'T'}">
							<li class="part text">
								<input type="hidden" name="content_type" value="${item.content.type}"/>
								<div class="float_buttons">
							        <button type="button" name="edit" title="编辑" edit="${item.content.id}"><img src="${serverPrefix}css2/survey/edit.gif"/></button>
							        <button type="button" name="moveup" title="上移" moveup="${item.id}"><img src="${serverPrefix}css2/survey/up.gif"/></button>
							        <button type="button" name="movedown" title="下移" movedown="${item.id}"><img src="${serverPrefix}css2/survey/down.gif"/></button>
							        <button type="button" name="remove" title="删除" remove="${item.id}"><img src="${serverPrefix}css2/survey/del.gif"/></button>
						    	</div>
								<h4 class="title"><span class="subject">
									${item.content.title}</span>
									<c:if test="${item.content.type == 'I'}"><input class="input_text" type="text" id="" name="" size="50" maxlength="${item.content.maxLength}" minlength="${item.content.minLength}" value=""/></c:if>
								</h4>
								<c:if test="${item.content.type == 'T'}"><textarea id="" name="" cols="80" rows="5" maxlength="${item.content.maxLength}" minlength="${item.content.minLength}"></textarea></c:if>
							</li>                		
                		</c:when>  
                		<c:otherwise>
	                		<c:choose>
	                			<c:when test="${item.type == 'C'}">
									<li class="part select">
										<input type="hidden" name="content_type" value="${item.content.type}"/>
										<input type="hidden" name="content_direction" value="${item.content.direction}"/>
										<div class="float_buttons">
									        <button type="button" name="edit" title="编辑" edit="${item.content.id}"><img src="${serverPrefix}css2/survey/edit.gif"/></button>
							        		<button type="button" name="moveup" title="上移" moveup="${item.id}"><img src="${serverPrefix}css2/survey/up.gif"/></button>
							        		<button type="button" name="movedown" title="下移" movedown="${item.id}"><img src="${serverPrefix}css2/survey/down.gif"/></button>
									        <button type="button" name="remove" title="删除" remove="${item.id}"><img src="${serverPrefix}css2/survey/del.gif"/></button>
								    	</div>
										<h4 class="title"><span class="subject" maxselect="${item.content.maxSelect}" minselect="${item.content.minSelect}">${item.content.title}</span></h4>
										<div class="content">
											<table class="options">
												<c:if test="${item.content.type == 'R'}">
													<c:if test="${item.content.direction == 1}">
														<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
															<tr><td><input type="radio" value=""/><label name="label" type="${surveyValue.type}">${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><input class="input_text" type="text" /></c:if></td></tr>
														</c:forEach>
													</c:if>
													<c:if test="${item.content.direction == 2}">
													<tr>
														<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
															<td><input type="radio" value=""/><label name="label" type="${surveyValue.type}">${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><input class="input_text" type="text" /></c:if></td>
														</c:forEach>
													</tr>
													</c:if>
												</c:if>
												<c:if test="${item.content.type == 'C'}">
													<c:if test="${item.content.direction == 1}">
														<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
															<tr><td><input type="checkbox" value=""/><label name="label" type="${surveyValue.type}">${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><input class="input_text" type="text" /></c:if></td></tr>
														</c:forEach>
													</c:if>
													<c:if test="${item.content.direction == 2}">
														<tr>
															<c:forEach items="${item.content.values}" var="surveyValue" varStatus="status">
																<td><input type="checkbox" value=""/><label name="label" type="${surveyValue.type}">${surveyValue.value}</label><c:if test="${surveyValue.type == 1}"><input class="input_text" type="text" /></c:if></td>
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
<center><button class="submit" name="release" id="release" >发布到文轩网</button></center>
</div>
<div class="content_bottom"></div>
<div class="footer">Copyright(C) 四川文轩在线电子商务有限公司 2000-2012, All Rights Reserved</div>


	<div class="release_box" style="display:none" survey="${survey.id}">
		<div style="font-weight: bolder;" class="rule">发布规则：
			<p>1、只限文轩网（www.winxuan.com）内url</p>
			<p>2、发布url指定第一层</p>
			<p>例子：发布到底层页面（www.winxuan.com/product/123456），url只需要指定为product，那么所有底层页面都会发布该调查表</p>
			<p>3、特别的，首页（www.winxuan.com）,发布url为“/”</p>
			<p>4、多个url之间通过;分隔</p>
		</div>
		<textarea name="release" cols="60" rows="5">${survey.releaseWithSeg}</textarea>
		<div class="control"><button type="button" name="release_button">确定</button></div>
    </div>
    
    
	<div class="separator_box" style="display:none" survey="${survey.id}">
        <div class="body">
            <input type="text" name="separator_title" style="width: 98%; text-align: left;" maxlength="255" value="" separator=""/>
        </div>
        <div>
        	<select name="type">
                <option value="1">一号分隔符</option>
                <option value="2">二号分隔符</option>
                <!--<option value="3">三号分隔符</option> -->
            </select>
        </div>
        <div class="control">
            <button type="button" name="separator_button">确定</button>
        </div>
    </div>
    
	<div class="text_box" style="display:none" survey="${survey.id}">
		<input type="hidden" name="text_type" value=""/>
        <div class="body">
            <input type="text" name="text_title" style="width: 98%; text-align: left;" maxlength="255" value="" textId=""/>
        </div>
        <div>
        	至少填<input type="text" name="min_input" size="5" maxlength="4"/>字&nbsp;最多填<input type="text" name="max_input" size="5" maxlength="4"/>字
        </div>
        <div class="control">
            <button type="button" name="text_button">确定</button>
        </div>
    </div>
    
	<div class="select_box" style="display:none;" survey="${survey.id}">
	<input type="hidden" name="select_type" value=""/>
        <div class="body">
            <input type="text" name="select_title" style="width: 98%; text-align: left;" maxlength="255" value="" selectId=""/>
        </div>
        <div class="limit" style="display:none;" >
        	至少选择<input type="text" name="min_select" size="5" maxlength="4"/>项&nbsp;最多选择<input type="text" name="max_select" size="5" maxlength="4"/>项
        </div>
        <div>
        	选项排序方向<select name="direction">
                        <option value="1">竖向</option>
                        <option value="2">橫向</option>
                    </select>
        </div>
        <div class="list">
			<div class='select_option' ><input type='text' name='label'/><button type='button' class='insert' title='添加选项'>&nbsp;</button><button type='button' class='remove' title='删除选项'>&nbsp;</button><input type='checkbox' name='allowInput'/><label>可填空</label><span class='text_limit' style='display:none;' >至少<input type='text' name='leastInput' size='2' maxlength='2'/>字，最多<input type='text' name='mostInput' size='2' maxlength='2'/>字</span></div>
	        <div class='select_option' ><input type='text' name='label'/><button type='button' class='insert' title='添加选项'>&nbsp;</button><button type='button' class='remove' title='删除选项'>&nbsp;</button><input type='checkbox' name='allowInput'/><label>可填空</label><span class='text_limit' style='display:none;'>至少<input type='text' name='leastInput' size='2' maxlength='2'/>字，最多<input type='text' name='mostInput' size='2' maxlength='2'/>字</span></div>
    	</div>
        <div class="control">
            <button type="button" name="select_button">确定</button>
        </div>
    </div>
	<div class="survey_edit_box" style="display:none;">
        <div class="body">
            <input type="text" name="survey_title" style="width: 98%; text-align: left;" maxlength="255" value="${survey.title}" />
        </div>
		<textarea id="wysiwyg" rows="14" cols="66"></textarea>
		<input type="hidden" value="<c:out value='${survey.description}' />" name="description"/>
		<input type="hidden" value="${survey.id}" name="surveyId"/>
        <div class="control">
            <center><button type="button" name="survey_button">确定</button></center>
        </div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script src="${serverPrefix}libs/core.js?20121220"></script>
	<script src="${contextPath}/js/survey/survey.js"></script>
</body>
</html>
