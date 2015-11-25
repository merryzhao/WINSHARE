<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-退换货服务</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  文轩网-退换货服务</span></div>
  <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="1_2" />
</jsp:include>
  <div class="right_box">
    <h3 class="myfav_tit margin10">退换货服务：退货申请</h3>
    <ul class="step_box">
    <li class="step_statu"><span>1、输入订单号</span></li>
    <li class="step_statu"><span>2、选择需退货商品</span></li>
    <li class="step_statu"><span>3、填写退货原因</span></li>
    <li><span>4、确认退货信息</span></li>
    <li class="last_step"><span>5、成功提交</span></li>
    </ul>
    <p class="return_box txt_center f16 fb">您正在提交订单 <b class="red fb">${orderId}</b> 的商品退货申请</p>
    <form:form action="/customer/returnorder/step4" method="POST" commandName="returnOrderForms" enctype="multipart/form-data" bind="reasonForm">
     <input type="hidden" name="isReturn" value="false"/>
    <input type="hidden" value="${orderId}" name="orderId"></input>
    <c:forEach var="item" items="${returnOrderItems}" varStatus="status">
    <input type="hidden" value="${item.appQuantity}" name="returnOrderForm[${status.index }].quantity"></input>
    <input type="hidden" value="${item.orderItem.id}" name="returnOrderForm[${status.index }].orderItemId"></input>
    <h2 class="return_tit">${status.index+1}、${item.orderItem.productSale.product.name} <a class="red fb haveline" href="javascript:;" bind="show" target1="table${status.index+1}">填写退货原因</a></h2>
    <table width="100%" class="return_reason" cellspacing="0" cellpadding="0" id="table${status.index+1}" <c:if test="${!status.first }">style="display:none"</c:if>>
      <tbody>
        <tr>
          <td width="16%" class="txt_right">退货原因：</td>
           <td width="84%"><select name="returnOrderForm[${status.index }].reason" bind="reason" param="${item.orderItem.productSale.product.name}" target="remark${status.index+1}">
            <option value="-1">请选择</option>
            <c:forEach var="code" items="${codes}">
            	 <option value="${code.id }">${code.name }</option>
            </c:forEach>
          &nbsp;</select></td> 
        </tr>
        <tr>
          <td class="txt_right">备注：</td>
          <td><textarea cols="60" rows="5" name="returnOrderForm[${status.index }].remark" bind="remark${status.index+1}"></textarea> <span class="d_yellow">限80字</span></td>
        </tr>
         <tr>
         <%--  <td class="txt_right">上传图片：</td>
          <td><input name="picFile" type="file"></input>
            <span class="d_yellow">图片大小不得超过200KB，仅支持JPG格式，最多五张</span><br><a class="gray haveline" href="#">继续添加</a>
            
          </td> --%>
        </tr>
      </tbody>
    </table>
    </c:forEach>  
    <p class="txt_center margin10"><input class="red_but" name="" type="button" value="下一步" bind="reasonSubmit"></p>
    </form:form>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
<script type="text/javascript" src="${serverPrefix}js/returnorder/returnorder.js"></script>	
</body>
</html>
