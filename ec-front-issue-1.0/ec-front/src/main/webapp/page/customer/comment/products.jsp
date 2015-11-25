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
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  我的评论</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="4_1" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">我的社区评论</h3>
    <ul class="infor_tab">
      <li class="current_info">商品评论(${productCommentCount})</li>
      <li><a href="/customer/comment/my">我的评论(${myCommentCount})</a></li>
      <li><a href="/customer/comment/useful">有用的评论(${myUseFulCount})</a></li>
    </ul>
    <div class="content">
    <table width="100%" class="favorite_goods record margin10 purchased" cellspacing="0" cellpadding="0">
      	<c:if test="${!empty customerBoughts}">
      <thead>
        <tr>
          <th width="36%"><b>商品名称</b></th>
          <th width="19%"><b>订单编号</b></th>
          <th width="15%"><b>购买时间</b></th>
          <th width="13%"><b>已有评论数</b></th>
          <th width="17%"><b>操作</b></th>
        </tr>
      </thead>
      <tbody>
      		<c:forEach items="${customerBoughts}" var="customerBought">
	      	<tr>
	          <td class="nodash"><p class="txt_left"><a class="link1" target="_blank"  href="/product/${customerBought.productSale.id}">${customerBought.productSale.effectiveName}</a></p></td>
	          <td class="nodash"><a class="link1" target="_blank" href="/customer/order/${customerBought.order.id}">${customerBought.order.id}</a></td>
	          <td class="nodash"><fmt:formatDate value="${customerBought.order.createTime}" pattern="yyyy-MM-dd"/> </td>
	          <td class="nodash"><a class="link2" href="/product/${customerBought.productSale.id}/comment/list">${customerBought.productSale.performance.totalComment}</a></td>
	          <td class="nodash">
		          <c:if test="${!customerBought.hasComment}">
		          <a class="green " href="/customer/comment/${customerBought.productSale.id}">发表评论</a>
		          </c:if>
		        	 <c:if test="${customerBought.hasComment}">
		          		<a class="green haveline" href="/product/${customerBought.productSale.id}/comment/list">查看我的评论</a>
		        	 </c:if>
	          </td>
	        </tr>
      		</c:forEach>
      	</c:if>
      	<c:if test="${empty customerBoughts}">
      	<p class="margin10 no_com"><b class="fb">暂无评论．．．</b><br>
      	如果您<a href="/customer/favorite">看过</a>或<a href="/customer/bought">购买过</a>某商品，有任何想法，请在该商品下发表点评论哦</p>
      	</c:if>
      </tbody>
    </table>
     <c:if test="${!empty pagination && !empty customerBoughts}">		
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
