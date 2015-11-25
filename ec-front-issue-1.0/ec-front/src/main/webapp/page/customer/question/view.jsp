<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的提问 - 会员中心 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>

<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  我的提问</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="4_2" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">我的社区咨询</h3>
    <ul class="infor_tab">
      <li class="current_info">我的提问(${questionCount})</li>
      <li ><a href="/customer/question/reply">我的回复(${replyCount})</a></li>
    </ul>
    <p class="txt_right margin10"><select class="selectstyle" name="">
      <option>全部咨询</option>
    </select></p>
    <c:if test="${!empty questions}">
      		<c:forEach items="${questions}" var="question">
	      		<div class="reviewer"><span class="fr">我提问于 <fmt:formatDate value="${question.askTime}" pattern="yyyy-MM-dd"/></span><a class="fb link3" href="/product/${question.productSale.id}">${question.productSale.effectiveName}</a></div>
			    <div class="qa_contents">
			    <p class="fl"><span class="questionList"><img src="${question.productSale.product.imageUrlFor110px}"></span><br><a class="link2" href="/product/question/${question.id}">查看全部回复</a></p>
			    <h2><c:out value="${question.title}"/></h2>
			    <p><c:out value="${question.content}"/></p>
			     <c:if test="${empty question.replyList}">
			    	<p class="khaki">暂无回复，请稍等...</p>
			     </c:if>
			    <c:if test="${!empty question.replyList}">
			     	<c:forEach items="${question.replyList}" var="reply">
				    	<p class="khaki"><c:out value="${reply.content}"/></p>
	    				<p class="txt_right light_gray">${reply.replier.name}回复于2011-8-30 11:21</p>
    				</c:forEach>
			     </c:if>
			     <div class="hei1"></div>
   				 </div>
      		</c:forEach>
    
     <c:if test="${!empty pagination}">
    	<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page></div>
     </c:if>
     </c:if>
     <c:if test="${empty questions}">
	    <div class="hei10"></div>
	    <p class="margin10 no_com"> <b class="fb">您没用任何提问！</b><br>
	    如果您要提问，请直接点击您要购买的商品，然后在页面下部的“在线咨询”表格填写</p>
      </c:if>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
