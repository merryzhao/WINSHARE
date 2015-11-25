<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>店铺评论管理</title>
<style type="text/css">
textarea {
	width: 350px;
	height: 50px;
	margin-bottom: -5px;
}
#content p{margin:10px 0px 10px 0px;padding:2px;}
select{margin:0px 10px 0px 10px;}
.replyitem{border-bottom: 1px black solid;width:360px;padding: 20px 10px;}
/*
#content div{margin:10px 0px 10px 200px;}
*/
.replayText{display: none;}
</style>
<!-- 引入JS -->
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content" align="center">
					<h4>${comment.title }</h4>
				<div align="left">
					评论人：${comment.customer.name } 评论时间：
					<fmt:formatDate value="${comment.commentTime }" type="both" />
					评分：${comment.rank.rank}
					<p>${comment.content }</p>
				</div>
				<div align="left">
					<c:forEach items="${comment.replyList }" var="reply">
						<div class="replyitem">
						<form action="/comment/updatereply" method="post">
								<fmt:formatDate value="${reply.replyTime }" type="both" />
								${reply.replier.name }回复：<br>
								${reply.content }[<a href="javascript:;" class="update" bind="updatereply">修改</a>]
								<div class="replayText">
									<input type="hidden" name="id" value = "${reply.id}"/>
									<input type="hidden" name="comment.id" value = "${comment.id}"/>
									<textarea name="content">${reply.content}</textarea>
									<input type="submit" value="提交"/>
								</div>
						</form>
						</div>
					</c:forEach>
				</div>
				<div align="left">
					管理员回复：<br />
					<form action="/comment/reply" onsubmit="return checkform();" method="post">
					<input type="hidden" name="commentId" value="${comment.id }" />
					<textarea name="content" id="replycontent"></textarea><br/>
					<br /> 
					<button type="submit">提交</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script>
	function checkform(){
		if($("#replycontent").val()==""){
			alert("请写回复!");
			return false;
		}
		return true;
	}
	$(function(){
		$("a[bind=updatereply]").click(function(){
			var ri = $(this).parentsUntil(".replyitem");
			ri.find(".replayText").show();
			ri.find(".replyContent").hide();
		});
	});
	</script>
</body>
</html>