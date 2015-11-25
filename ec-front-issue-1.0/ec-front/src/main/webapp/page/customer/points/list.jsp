<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-我的积分</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
</jsp:include>
</head>
<body>
<div class="layout">
    <%@include file="../../snippets/version2/header.jsp" %>
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
    <div class="left_box">
    	<jsp:include page="../../left_menu.jsp" flush="true" >
			<jsp:param name="id" value="3_5" />
		</jsp:include>
    </div>
	<div class="right_box">
	    <h3 class="myfav_tit margin10">我的积分</h3>
	    <h4 class="now_score f14">当前积分：<b class="fb red">${points}</b></h4>
	    <ul class="infor_tab">
	      <li class="current_info" bind="record">积分记录</li>
	      <li bind="exchange" style="display:none">积分兑换</li>
	    </ul>
	    <p class="margin10">
	      <select class="selectstyle" id="select" name="select"  bind="record_type">
	      	<option value="3" <c:if test="${empty param.type || param.type == '3'}">selected="selected"</c:if>>近3个月的积分记录</option>
	      	<option value="12" <c:if test="${param.type == '12'}">selected="selected"</c:if>>近一年的积分记录</option>
	      </select>
	    </p>
	    <div class="hei10"></div>
	    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
	      <thead>
	        <tr>
	          <th width="12%"><span class="black">日期</span></th>
	          <th width="13%"><span class="black">类型</span></th>
	          <th width="12%"><span class="black">积分</span></th>
	          <th width="16%"><span class="black">订单号</span></th>
	          <th width="47%"><span class="black">备注</span></th>
	        </tr>
	      </thead>
	      <tbody>
	      	<c:forEach items="${customerPointsList}" var="customerPoints" varStatus="status">
		        <tr>
		          <td class="nodash"><fmt:formatDate value="${customerPoints.time}" pattern="yyyy.MM.dd"/></td>
		          <td class="nodash" style="text-align:left;">${customerPoints.type.name}</td>
		          <td class="nodash" style="text-align:right;">${customerPoints.points}</td>
		          <c:if test="${customerPoints.order.id==null}">
		          	<td class="nodash"><span>---</span></td>
		          </c:if>
		          <c:if test="${customerPoints.order.id!=null}">
		          	<td class="nodash"><a class="green" href="javascript:;" bind='order'>${customerPoints.order.id}</a></td>
		          </c:if>
		          <td class="nodash" style="text-align:left;">${customerPoints.comment}</td>
		        </tr>
		    </c:forEach>
	      </tbody>
	    </table>
	    <c:if test="${pagination.count == 0}"><div class="tips">暂无积分记录</div></c:if>
	    <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="front-user" /></c:if>
	
	  </div>
	  
	  <div class="hei10"></div>
	</div>
<div class="hei10"></div>

<%@include file="../../snippets/version2/footer.jsp" %>

<script src="${serverPrefix}js/points/points.js"></script>

</body>
</html>
