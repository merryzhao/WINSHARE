<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/page"  prefix="winxuan-page"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-已购商品</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<style>.infor_tab .current_info a{color:#fff;}</style>
<body>
<jsp:include page="../../snippets/version2/header.jsp"></jsp:include>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="2_1" />
</jsp:include>    
	<form:form action="http://www.winxuan.com/customer/bought" commandName="boughtForm"  method="get">
    <div class="right_box">
        <h3 class="myfav_tit margin10">已购商品（共<c:if test="${!empty total}">${total}</c:if>件）</h3>
        <input type="hidden" name="total" id="total" value="${total}">
      <div class="inquiry margin10">商品名称：<input type="text" name="productName" id="productName" size="25" value="${selectedParameters['pruductName']}">购买时间从：<input type="text" name="startDate" id="startDate" size="15" value="<fmt:formatDate value="${selectedParameters['startDate']}" pattern="yyyy-MM-dd"/>" readonly="readonly">到<input type="text" name="endDate" id="endDate" size="15" value="<fmt:formatDate value="${selectedParameters['endDate']}" pattern="yyyy-MM-dd"/>" readonly="readonly"><input class="inquiry_but" type="submit" value="&nbsp;&nbsp;查询&nbsp;&nbsp;"></div>
      <div class="locate">
	  <select class="sequence" name="orderBy" id="orderBy">
	        <c:forEach var="compositor" items="${compositorList}">
				<option value="${compositor.description}" <c:if test="${compositor.description == selectedParameters['orderBy'] }">selected="selected"</c:if>>${compositor.name}</option>
			</c:forEach>
	  </select>
	  <input type="hidden" name="sort" id="sort" value="${selectedParameters['sort']}">
        <ul class="infor_tab">
        	<li <c:if test="${selectedParameters['sort'] == null}">class="current_info"</c:if>><a id="all">全部商品</a></li>
            <li <c:if test="${selectedParameters['sort'] == 11001}">class="current_info"</c:if>><a id="book">图书</a></li>
            <li <c:if test="${selectedParameters['sort'] == 11002}">class="current_info"</c:if>><a id="video">音像</a></li>
            <li <c:if test="${selectedParameters['sort'] == 11003}">class="current_info"</c:if>><a id="merchandise">百货</a></li>
        </ul>
      </div>
	</form:form>
        <div class="hei10"></div>
        <table width="100%" class="favorite_goods purchased" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th width="12%">商品</th>
                    <th width="26%">&nbsp;</th>
                    <th width="15%">文轩价</th>
                    <th width="15%">购买时间</th>
                    <th width="16%">订单号</th>
                    <th width="16%">操作</th>
                </tr>
            </thead>
            <tbody>
             <c:if test="${!empty boughtList}">
              <c:forEach var="bought" items="${boughtList}"  varStatus="status">
                <tr attr="${bought.id}">
                    <td <c:if test="${status.count == 1}"> class="nodash"</c:if>><div class="boughtList"><img src="${bought.productSale.product.imageUrlFor110px}" alt="${bought.productSale.effectiveName}"  class="fl"></div></td>
                    <td <c:if test="${status.count == 1}"> class="nodash"</c:if>><p class="favgoods_info"><a class="link1" href="${bought.productSale.product.url }" title="${bought.productSale.effectiveName}" target="blank">${bought.productSale.effectiveName}</a></p></td>
                    <td <c:if test="${status.count == 1}"> class="nodash"</c:if>>￥${bought.productSale.salePrice}</td>
                    <td <c:if test="${status.count == 1}"> class="nodash"</c:if>><fmt:formatDate value="${bought.buyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td <c:if test="${status.count == 1}"> class="nodash"</c:if>><a class="link1" href="/customer/order/${bought.order.id}" target="blank">${bought.order.id}</a></td>
                    <td <c:if test="${status.count == 1}"> class="nodash"</c:if>>
                    <c:if test="${bought.productSale.canSale == true}"><a class="buy_but" href="javascript:;" productSaleId="${bought.productSale.id}">购买</a></c:if>
                    <c:if test="${bought.productSale.canSale == false}"><a class="arr_notice" href="javascript:;" productSaleId="${bought.productSale.id}">到货通知</a></c:if>
                    <br><a class="link1" href="/customer/comment/${bought.productSale.id}" target="blank">发表评论</a></td>
                </tr>
              </c:forEach>
             </c:if>
             <c:if test="${empty boughtList}"><td colspan="6"><div align="center"><br/>您还没有购买商品<br/></div></td></c:if>
            </tbody>
        </table>
        <c:if test="${!empty pagination}">		   
			<winxuan-page:page bodyStyle="front-user" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
    </div>
    <div class="hei10"></div>
<%@include file="../../snippets/version2/footer.jsp" %>
<script type="text/javascript" src="http://static.winxuancdn.com/js/bought/bought.js"></script>	
</body>
</html>