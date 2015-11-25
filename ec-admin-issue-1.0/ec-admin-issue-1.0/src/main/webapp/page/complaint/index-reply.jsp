<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户投诉管理</title>
<style type="text/css">
textarea {
	width: 350px;
	height: 50px;
	margin-bottom: -5px;
}
#content p{margin:10px 0px 10px 0px;padding:2px;}
select{margin:0px 10px 0px 10px;}
.replyitem{border-bottom: 1px black solid;width:360px;padding: 20px 10px;}
.replayText{display: none;}
</style>
</head>
<body>		
		
			<form:form action="/complaint/list" method="post" commandName="complainForm">
			   <input type="submit" style="margin-left: 5px; margin-top: 0px;" value="返回上一页" />
			</form:form>
			<div class="frame-main-inner" id="content" align="center">
				<h4>${customerComplain.title}</h4>	
				<div align="left">
					投诉人：${customerComplain.customer.name }   投诉时间：<fmt:formatDate value="${customerComplain.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					联系电话:${customerComplain.phone}
					邮箱地址:${customerComplain.email}
					<c:if test="${customerComplain.order.id!=null}">
					订单号:${customerComplain.order.id}
					</c:if>
					<c:if test="${customerComplain.order.id==null}">
					订单号:该用户没有填写订单号
					</c:if>
					<p>${customerComplain.content}</p>
				</div>
				<div align="left">
				<c:forEach items="${customerComplain.replyList}" var="reply">
				<form action="/complaint/deleteReply" method="post">
						<div class="replyitem">	
						<fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
								${reply.replier.name} 回复<br>
								回复内容：${reply.content}<br/>
								
								
								[<a href="javascript:;" id="${reply.id}" class="update" bind="updatereply">修改</a>][<a id="${reply.id}" href="javascript:;" bind="deleteReply">删除</a>]
								<div class="replayText" style="display:none;">
									
									<textarea name="content">${reply.content}</textarea>
									  <input type="button" id="${reply.id}" class="btnupdate" value="提交"/>
								</div>
						
						</div>
						</form>
				</c:forEach>		
				</div>
				<div align="left">
					管理员回复：<br />
					
					<form action="/complaint/reply" method="post" name="complainReplyForm">
						<input type="hidden" name="id" value="${customerComplain.id}" />
						<textarea name="content" id="replycontent"></textarea><br/>
						<br /> 
						<input type="button" class="submit" value="提交" />
					</form>
				</div>
			</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="/js/complaint/reply.js"></script>
	
</body>
</html>