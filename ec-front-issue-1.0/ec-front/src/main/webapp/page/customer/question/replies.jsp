<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的回复 - 会员中心 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>

<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的回复</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="4_2" />
</jsp:include>
 <div class="right_box">
    <h3 class="myfav_tit margin10">我的社区咨询</h3>
    <ul class="infor_tab">
      <li><a href="/customer/question">我的提问(${questionCount})</a></li>
      <li  class="current_info">我的回复(${replyCount})</li>
    </ul>
    <p class="txt_right margin10"><select class="selectstyle" name="">
      <option>全部咨询</option>
    </select></p>
       <c:if test="${!empty questionReplies}">
      		<c:forEach items="${questionReplies}" var="reply">
      		
	      		<div class="reviewer"><span class="fr">我提问于  <fmt:formatDate value="${reply.question.askTime}" pattern="yyyy-MM-dd"/></span><a class="fb link3" href="/product/${reply.question.productSale.id}" target="_blank">${reply.question.productSale.effectiveName}</a></div>
			    <div class="qa_contents">
			    <p class="fl"><img src="${reply.question.productSale.product.imageUrlFor110px}"><br><a class="link2" href="/product/question/${reply.question.id}">查看全部回复</a></p>
			    <h2><c:out value="${reply.question.title}"/></h2>
			    <p><c:out value="${reply.question.content}"/></p>
			    <p class="khaki"><c:out value="${reply.content}"/></p>
			    <p class="txt_right light_gray">${reply.replier.name}回复于<fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd"/></p>
			    <div class="hei1"></div>
			    </div>
			    
      		</c:forEach>
     <c:if test="${!empty pagination}">
    	<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page></div>
     </c:if>
     </c:if>
     <c:if test="${empty questionReplies}">
     <div class="hei10"></div>
    <p class="margin10 no_com"> <b class="fb">您从未回复过别人的商品提问</b><br>
    		如果您正好了解某个提问，不妨把你的知识与他/她一起分享！</p>
    </c:if>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
