<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-投诉和咨询</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body class="simple">
<jsp:include page="../../snippets/version2/header.jsp"></jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  我的帐户</span></div>
	<jsp:include page="/page/left_menu.jsp">
		<jsp:param name="id" value="1_4" />
	</jsp:include>
 <div class="right_box">
     <h3 class="myfav_tit margin10">我要投诉</h3>
    <ul class="infor_tab">
      <li  class="current_info" target="tabcontent01">投诉历史</li>
      <li  target="tabcontent02">提交投诉</li>
    </ul>
 
  
      <div class="tabcontent01" >
         <c:if test="${!empty customerComplains}">
      		<c:forEach items="${customerComplains}" var="customerComplain">
	      		<div class="reviewer"><c:out value="${customerComplain.title}"/> <span class="fr">我投诉于 <fmt:formatDate value="${customerComplain.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span><a class="fb link3" href="/product/${question.productSale.id}">${question.productSale.name}</a></div>
			    <div class="qa_contents">
			    <p><c:out value="${customerComplain.content}"/></p>
			     <c:if test="${empty customerComplain.replyList}">
			    	<p class="khaki">您的投诉正在处理中...</p>
			     </c:if>
			    <c:if test="${!empty customerComplain.replyList}">
			     	<c:forEach items="${customerComplain.replyList}" var="reply">
				    	<p class="khaki"><c:out value="${reply.content}"/></p>
	    				<p class="txt_right light_gray">文轩网客服  回复于<fmt:formatDate value="${reply.replyTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
    				</c:forEach>
			     </c:if>
			     <div class="hei1"></div>
   				 </div>
      		</c:forEach>
      		</c:if>
   		   	<br/>
         <c:if test="${pagination.count == 0}"><div class="tips" align="center">-- 暂无投诉 --</div></c:if>
	    <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="front-user" /></c:if>
      </div>
		<br/>
        <div class="tabcontent02" style="display:none">
   			<h4 class="comment_tit2">在下面提交您的投诉信息</h4> 
        	 <div class="consulting">
        		<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
        			<tr>
        				<td align="right">(其中<span style="color: red">*</span>为必填)</td>
        			
        			</tr>       
    				<tr>
        				<td width="18%" align="right">您的昵称：</td>
        				<td>
           					<input  type="text" readonly="readonly" style="background-color:#EDEDED;"  id="username" 
        					<c:choose >
        						<c:when test="${!empty customer}">
       								value=" ${customer.displayName}"
        						</c:when >
        						<c:otherwise>
        							value="游客" 
         						</c:otherwise>
        					</c:choose > /> 
      					<c:if test="${empty customer}"> <b bind="loginmessage">建议先<a class="link1" href="javascript:;" bind ="login">登录</a></b></c:if>       
    				</tr>
    				<tr>
        				<td align="right">email地址：</td>
        				<td><input type="text" id="email" name="email" value="${customer.email}" > <span style="color: red">*</span> </td>
    				</tr>
    				<tr> 
        				<td align="right">订单号：</td>
        				<td><input  type="text" name="orderId" ></td>
    				</tr>
    				<tr> 
        				<td align="right">联系电话：</td>
        				<td><input  type="text" name="phone" value="${customer.mobile}" >  <span style="color: red">*</span></td>
    				</tr>
    				<tr> 
        				<td align="right">投诉什么：</td>
        				<td><input type="text" name="title" > <span style="color: red">*</span></td>
    				</tr>
    				<tr>
        				<td align="right" style="vertical-align:text-top;">投诉内容：</td>
        				<td><textarea class="unity_w" name="content" id="content" cols="45" rows="5" ></textarea><span style="color: red">*</span></td>
    				</tr>
    				<tr>
        				<td align="right">验证码：</td>
        				<td><input type="text" size="10" name="verifyCode" id="verifyCode"/> <img  data-lazy="false" src="http://www.winxuan.com/verifyCode?d=<%=System.currentTimeMillis() %>" class="verifyCodeImg"/> <a class="link1" href="javascript:;" bind="changeVerifyCode"> 换一张</a></td>
    				</tr>
    				<tr>
        				<td>&nbsp;</td>
        				<td align="right"><input id ="newquestion" class="consult_but" type="button" value="提交你的信息"></td>
    				</tr>
				</table>
        	</div>
      </div>
  </div>
  <div class="hei10"></div>
</div>
<script type="text/javascript" src="${serverPrefix}js/customer/complaint.js?${version}"></script>
<%@include file="../../snippets/version2/footer.jsp" %>
</body>
</html>