<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的社区评论-会员中心-文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="my_acc_order" />
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp"></jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的评论</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="4_1" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">我的社区评论</h3>
    <ul class="infor_tab">
     <li><a href="/customer/comment/product">商品评论(${productCommentCount})</a></li>
      <li class="current_info">我的评论(${myCommentCount})</li>
      <li><a href="/customer/comment/useful">有用的评论(${myUseFulCount})</a></li>
    </ul>
    <div class="content">
     <table width="100%" class="favorite_goods record margin10" cellspacing="0" cellpadding="0">
     	 <c:if test="${!empty comments}">
      <thead>
        <tr>
          <th width="36%"><b class="black">商品名称</b></th>
          <th width="39%"><b class="black">评价</b></th>
          <th width="12%"><b class="black">评论时间</b></th>
          <th width="13%"><b class="black">已有评论数</b></th>
        </tr>
      </thead>
      <tbody>
     
      		<c:forEach items="${comments}" var="comment">
        <tr>
          <td class="nodash"><p class="txt_left"><a class="link1" target="_blank"  href="${comment.productSale.product.url}">${comment.productSale.effectiveName}</a></p></td>
          <td class="nodash"><p class="txt_left"><a class="link1"  href="/product/comment/${comment.id}">${comment.title}</a></p></td>
          <td class="nodash"><fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd"/></td>
          <td class="nodash"><a class="link2" href="/product/${comment.productSale.id}/comment/list">${comment.productSale.performance.totalComment}</a></td>
        </tr>
        </c:forEach>
      	</c:if>
      	<c:if test="${empty comments}">
      	<p class="margin10 no_com"><b class="fb">暂无评论．．．</b><br>
      	如果您<a href="/customer/favorite">看过</a>或<a href="/customer/bought">购买过</a>某商品，有任何想法，请在该商品下发表点评论哦</p>
      	</c:if>
      </tbody>
    </table>
    	 <c:if test="${!empty pagination && !empty comments}">		  
				<winxuan-page:page pagination="${pagination}" bodyStyle="front-user" />
		</c:if>
    </div>
    <div class="hei10"></div>
    
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
