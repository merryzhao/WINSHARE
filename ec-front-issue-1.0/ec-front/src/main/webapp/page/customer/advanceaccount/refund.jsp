<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的暂存款 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>

<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的暂存款</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_7" />
</jsp:include>
   <div class="right_box">
    <h3 class="myfav_tit margin10">我的暂存款</h3>
    
    <p class="border_style margin10">退款前请先阅读：<a class="link2" href="http://www.winxuan.com/help/return_capital.html">退款说明</a></p>
    <h4 class="now_score f14">您申请的退款额：<input class="refund_num" type="text" size="7" name="refundMoney" id="refundMoney"/> 元，您最多可退款：<b class="red">￥<b id="canRefundMoney">${customer.account.balance }</b></b><b class="red" id="moneyError"></b></h4>
    <p class="margin10" bind="banktal"><b class="f14">您申请退款到：</b>
    <c:if test="${!empty customer.payee}"></c:if>
     <input name="bankType" type="radio"  target="bank1" checked="checked" /> 
      		  银行账户 
     <input name="bankType" type="radio"  target="bank2"/>
     		 支付宝帐号
    <%--  <input name="bankType" type="radio"  target="bank3"/> 
  			  财付通帐号    --%>   
     <input name="bankType" type="radio"  target="bank4"/> 
  			   邮局汇款</p>
  	<form:form class="temporary_money" action="/customer/advanceaccount/applyRefund" method="post" commandName="bankForm"  >
  		 <input  name="type" type="hidden" value="43003" />
  	 <div class="bank1">
    <div class="border_style bank_box">
      <table width="100%" border="0" cellspacing="0">
        <tr>
          <td class="txt_right" width="15%">银行名称：</td>
          <td width="85%">
          <select class="select_with1" name="bank" bind="bank" >
            	<option value="-1"> --请选择银行-- </option>
            	<c:forEach var="bank" items="${bankType}">
						<option value="${bank.id}" <c:if test="${bank.id == customer.payee.bankAccount.bank.id  }">selected="selected"</c:if>> ${bank.name} </option>					
				</c:forEach>
          </select><b class="red" id="bankError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">所在支行：</td>
          <td>
          <input type="hidden" value="${customer.payee.postAccount.postCountry.id}" id="postCountry" />
          <input type="hidden" value="${customer.payee.postAccount.postProvince.id}" id="postProvince" />
		  <input type="hidden" value="${customer.payee.postAccount.postCity.id}" id="postCity" />
          <input type="hidden" value="${customer.payee.postAccount.postDistrict.id}" id="postDistrict" />
          <input type="hidden" value="${customer.payee.bankAccount.bankProvince.id}" id="bankProvince" />
          <input type="hidden" value="${customer.payee.bankAccount.bankCity.id}" id="bankCity" />
          <select areaLevel ="country" style="display:none" >
	   				<option value="-1">请选择国家</option>	
          </select>
          
		  <select areaLevel="province"  name="bankProvince" bind="bankProvince">
					<option value="-1">请选择省份</option>
		  </select> 
		 
		  <select areaLevel="city"  name="bankCity" bind="bankCity" >
					<option value="-1">请选择城市</option>
		 </select>
		
		 <select areaLevel="district" name="district" style="display:none">
				<option value="-1" >请选择区县</option>
		 </select>
		 <b class="red" id="areaError"></b>
		  </td> 
	       </tr>
        <tr>
          <td class="txt_right">开卡人姓名：</td>
          <td><input type="text" size="40" name="bankAccountName" id="bankAccountName" value="${customer.payee.bankAccount.bankAccountName}"> 
           	 <b id="bankAccountNameMessage">如：张信哲</b><b class="red" id="bankAccountNameError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">卡               号：</td>
          <td><input type="text" size="40" name="bankAccountCode" id="bankAccountCode" value="${customer.payee.bankAccount.bankAccountCode}"> 
        	 <b id="bankAccountCodeMessage">如：6625441033825537517</b><b class="red" id="bankAccountCodeError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">联系电话：</td>
          <td><input type="text" size="40" name="bankAccountContactPhone" id="bankAccountContactPhone" value="${customer.payee.bankAccount.bankAccountContactPhone}" > 
           	 <b id="bankAccountContactPhoneMessage">如：010-88008800,13908181234</b><b class="red" id="bankAccountContactPhoneError"></b></td>
        </tr>
      </table>
    </div>
    <p class="margin10 txt_center">
      <input class="user_submit" name="" type="button" value="提交退款申请"  bind="bankSubmit">
    </p>
    </div>
    </form:form>
     <form:form class="temporary_money" action="/customer/advanceaccount/applyRefund" method="post" commandName="cashApplyForm" id="alipayForm" >		   
  		<input  name="type" type="hidden" value="43001" />
  		<div class="bank2" style="display:none"> 
   			 <p class="border_style bank_box">&nbsp;&nbsp;&nbsp;&nbsp;您的支付宝帐号：<input type="text" size="30" value="${customer.payee.alipay}" name="alipay" id="alipayAccount"><b class="red" id="alipayError"></b>
   			 <br/><br/>您的支付宝认证姓名：<input type="text" size="30" value="${customer.payee.alipayName}" name="alipayname" id="alipaynameAccount"><b class="red" id="alipaynameError"></b></p>
    		 <p class="margin10 txt_center">
     			 <input class="user_submit" name="" type="button" bind="alipaySubmit" value="提交退款申请" >
   			 </p>
    	</div>	
    </form:form>
  <%--   <form:form class="temporary_money" action="/customer/account/applyRefund" method="post" commandName="cashApplyForm" id="tenpayForm" >		   
    	<input  name="type" type="hidden" value="43002" />
    	<div class="bank3" style="display:none">
   			 <p class="border_style bank_box">您的财付通帐号：<input type="text" size="30" value="${customer.payee.tenpay}" name="tenpay" id="tenpay"><b class="red" id="tenpayError"></b></p>
   		 	<p class="margin10 txt_center">
      			<input class="user_submit" name="" type="button" bind="tenpaySubmit" value="提交退款申请">
  			</p>
   		 </div>
  		
    </form:form> --%>
    <form:form class="temporary_money" action="/customer/advanceaccount/applyRefund" method="post" commandName="cashApplyForm" id="postForm" >		   
    <input  name="type" type="hidden" value="43004" />
    <div class="bank4" style="display:none">
    <div class="border_style bank_box">
      <table width="100%" border="0" cellspacing="0">
        <tr>
          <td class="txt_right" width="15%">收款人姓名：</td>
          <td width="85%"><input type="text" size="40" name="postAccountName" id="postAccountName" value="${customer.payee.postAccount.postAccountName}" ><b class="red" id="postAccountNameError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">地区：</td>
          <td>
          	<select areaLevel ="country2"  name="postCountry" width="50px">
	   		     <option value="-1">请选择国家</option>
            </select>
			<select areaLevel="province2" name="postProvince" width="50px">
				 <option value="-1">请选择省份</option>
			</select> <select areaLevel="city2" name="postCity" bind="postCity" width="50px">
				 <option value="-1" width="100px">请选择城市</option>
			</select> <select areaLevel="district2" name="postDistrict" width="50px">
				<option value="-1" width="100px">请选择区县</option>
			</select>
			<b class="red" id="postAreaError"></b>
          </td>
        </tr>
        <tr>
          <td class="txt_right">街道/村庄：</td>
          <td><input type="text" size="40" name="postAddress" value="${customer.payee.postAccount.postAddress}" id="postAddress"/> 
           	 <b id="postAddressMessage">如：西藏北路45号东方大厦1564室</b><b class="red" id="postAddressError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">邮编：</td>
          <td><input type="text" size="40" name="postCode" value="${customer.payee.postAccount.postCode}" id="postCode">
          		<b class="red" id="postCodeError"></b>
          </td>
        </tr>
        <tr>
          <td class="txt_right">联系电话：</td>
          <td><input type="text" size="40" name="postContactPhone" value="${customer.payee.postAccount.postContactPhone}" id="postContactPhone"> 
         	   <b id="postContactPhoneMessage">如：010-88008800,13908181234</b><b class="red" id="postContactPhoneError"></b></td>
        </tr>
      </table>
    </div>
    <p class="margin10 txt_center">
      <input class="user_submit" name="" type="button"  bind="postSubmit" value="提交退款申请" >
    </p>
    </div> 
    </form:form>
  </div>
  <div class="hei10"></div>
</div>
<%@include file="../../snippets/footer.jsp"%>
	<script type="text/javascript" src="${serverPrefix}js/account/refund.js"></script>	
</body>
</html>
