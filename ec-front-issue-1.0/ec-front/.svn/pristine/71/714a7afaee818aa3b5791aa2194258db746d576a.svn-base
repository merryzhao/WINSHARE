<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-用户_礼品卡基本信息</title>
<link href="${serverPrefix }css2/images/giftcard.css" rel="stylesheet" type="text/css">
	<jsp:include page="../snippets/version2/meta.jsp" flush="true" >
		<jsp:param name="type" value="acc_order" />
	</jsp:include>
</head>

<body>
<%@include file="../snippets/version2/header.jsp" %>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span>文轩网 > 我的帐户</span></div>
  <div class="left_box">
         <c:import url="left_box.jsp"  >
           <c:param name="jspname" value="0"></c:param>
         </c:import>
    </div>
  <div class="right_box">
    <h3 class="myfav_tit margin10">礼品卡基本信息</h3>
    <table class="returns_info" width="100%" border="0" cellspacing="0">
      <tr>
        <td width="9%"><b class="fb">卡&nbsp;&nbsp;号：</b></td>
        <td width="91%">${presentCard.id}</td>
      </tr>
      <tr>
        <td><b class="fb">面&nbsp;&nbsp;值：</b></td>
        <td>￥${presentCard.denomination}</td>
      </tr>
      <tr>
        <td><b class="fb">余&nbsp;&nbsp;额：</b></td>
        <td><b class="fb red">￥${presentCard.balance}</b></td>
      </tr>
      <tr>
        <td><b class="fb">有效期：</b></td>
        <td> <fmt:formatDate value="${presentCard.endDate}" pattern="yyyy-MM-dd"/> </td>
       
      </tr>
      <tr>
          <td><b class="fb">状&nbsp;&nbsp;态：</b></td>
          
          <c:if test="${canUse}">
           <td><b class="green fb">有效</b> <b class="gray fb">已过期</b></td>
          </c:if>
          <c:if test="${!canUse}">
           <td><b class="gray fb">有效</b> <b class="green fb">已过期</b></td>
          </c:if>
         
      </tr>
    </table>
     <ul class="infor_tab">
      <li class="current_info">礼品卡交易明细</li>
    </ul>
    <div class="hei10"></div>
    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="9%">序号</th>
          <th width="16%">用户名</th>
          <th width="22%">交易时间</th>
          <th width="22%">交易号</th>
          <th width="16%">使用/退款(元)</th>
          <th width="15%">余额(元)</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${presentCardDealLoglist}" var="pcdllist" varStatus="status">
        <tr>
          <td class="nodash">${status.index+1}</td>
          <td class="nodash">${pcdllist.operator.realName}</td>
          <td class="nodash"><fmt:formatDate value="${pcdllist.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
          <td class="nodash">${pcdllist.order.id}</td>
          <td class="nodash">${pcdllist.dealMoney}</td>
          <td class="nodash">${pcdllist.currentBalance}</td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
     <c:if test="${pagination.count == 0}"><div class="tips">暂无礼品卡</div></c:if>
	    <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="front-user" /></c:if>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../snippets/version2/footer.jsp" %>
</body>
</html>
