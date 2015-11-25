<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="winxuan" uri="http://www.winxuan.com/tag/page" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的暂存款 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="acc_order" name="type"/>
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
     <div class="now_score">
      <ul class="money_info">
        <li class="fl">现在暂存款总额：<b class="fb red f16" bind="balance">￥${account.balance+account.frozen}</b></li>
        <li class="fl">被锁定的金额：<b class="fb red f16" bind="frozen">￥${account.frozen}</b></li>
        <li class="fl">您可以提款：<b class="fb green f16" bind="overagebalance">￥${account.balance}</b>
        <c:if test="${account.balance >=1}">
        	(<a class="red fb haveline" href="/customer/advanceaccount/refund" id="refundhref">申请退款</a>)
        </c:if>
		</li>
      </ul>
      <div class="hei1"></div>
    </div>
    <p class="margin10 fb f14">我的暂存款记录</p>  
    	<ul class="infor_tab">
     	<li> <a href="/customer/advanceaccount">暂存款明细</a></li>
     	 <li class="current_info" >退款进度查询</li>
   	   </ul> 
   	<c:choose>
    <c:when test="${!empty cashApplys}">
    <div class="hei10"></div>
    <table width="100%" class="favorite_goods record" cellspacing="0" cellpadding="0">
      <thead>
        <tr>
          <th width="12%">申请日期</th>
          <th width="18%">退款渠道</th>
          <th width="10%">退款金额</th>
          <th width="9%">收款人</th>
          <th width="11%">退款状态</th>
          <th width="12%">处理日期</th>
          <th width="28%">备注</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach var="cashApply" items="${cashApplys }" varStatus="status"> 
        <tr>
          <td class="nodash"><b class="black"><fmt:formatDate value="${cashApply.createTime }" pattern="yyyy-MM-dd"></fmt:formatDate></b></td>
									<td class="nodash"><b class="black">${cashApply.type.name
											}</b>(<a class="link2" href="javascript:;" id="cashdetail" 
										        <c:if test="${cashApply.type.id == 43003}">
														param='${cashApply.type.id},${cashApply.bankAccount.bank.name},${cashApply.bankAccount.bankProvince.id},${cashApply.bankAccount.bankCity.id},${cashApply.bankAccount.bankAccountName},${cashApply.bankAccount.bankAccountCode},${cashApply.bankAccount.bankAccountContactPhone}'
												</c:if>
												<c:if test="${cashApply.type.id == 43001}">
														param='${cashApply.type.id},${cashApply.alipay},${cashApply.alipayName}'
												</c:if>
												<c:if test="${cashApply.type.id == 43002}">
														param='${cashApply.type.id},${cashApply.tenpay}'
												</c:if>
												<c:if test="${cashApply.type.id == 43004}">
														param='${cashApply.type.id},${cashApply.postAccount.postAccountName},${cashApply.postAccount.postCountry.id},${cashApply.postAccount.postProvince.id},${cashApply.postAccount.postCity.id},${cashApply.postAccount.postDistrict.id},${cashApply.postAccount.postAddress},${cashApply.postAccount.postCode},${cashApply.postAccount.postContactPhone}'
												</c:if>
										>详细</a>)</td>
									<td class="nodash"><b class="black">￥${cashApply.money}</b></td>
          <td class="nodash"><b class="black">
          <c:if test="${cashApply.type.id ==43003}">
          		 ${cashApply.bankAccount.bankAccountName }
          </c:if>
          <c:if test="${cashApply.type.id ==43004}">
          		 ${cashApply.postAccount.postAccountName }
          </c:if>
          <c:if test="${cashApply.type.id ==43002 ||cashApply.type.id ==43001}">
          		 ----
          </c:if>
          </b>
          </td>
          <td class="nodash"><b class="fb orange"><b class="statusName${cashApply.id}">${cashApply.status.name}</b></b></td>
          <td class="nodash"><b class="black">
          <c:choose>
          <c:when test="${!empty cashApply.processTime}">
          		<b><fmt:formatDate value="${cashApply.processTime}" pattern="yyyy-MM-dd"></fmt:formatDate></b>
          </c:when>
          <c:otherwise>
          		----
          </c:otherwise>
          </c:choose></b></td>
          <td class="nodash"><p class="txt_left gray" id="remark${cashApply.id}">
          	<c:if test="${cashApply.status.id==44001 }">您可以<a class="link2" href="javascript:;" id="cashModify" 
					<c:if test="${cashApply.type.id == 43001}">
							param='${cashApply.type.id},${cashApply.id},${cashApply.alipay},${cashApply.alipayName}'
					</c:if>
					 <c:if test="${cashApply.type.id == 43003}">
							param='${cashApply.type.id},${cashApply.id},${cashApply.bankAccount.bank.id},${cashApply.bankAccount.bankProvince.id},${cashApply.bankAccount.bankCity.id},${cashApply.bankAccount.bankAccountName},${cashApply.bankAccount.bankAccountCode},${cashApply.bankAccount.bankAccountContactPhone}'
					</c:if>
					<c:if test="${cashApply.type.id == 43004}">
							param='${cashApply.type.id},${cashApply.id},${cashApply.postAccount.postAccountName},${cashApply.postAccount.postCountry.id},${cashApply.postAccount.postProvince.id},${cashApply.postAccount.postCity.id},${cashApply.postAccount.postDistrict.id},${cashApply.postAccount.postAddress},${cashApply.postAccount.postCode},${cashApply.postAccount.postContactPhone}'
					</c:if>
          	>修改</a>，<a class="link2" href="javascript:;" bind="cancel" parma="${cashApply.id }">撤销</a>该申请</c:if>
          	<c:if test="${cashApply.status.id==44002 }">处理中</c:if>
          	<c:if test="${cashApply.status.id==44003 }">退款成功</c:if>
          	<c:if test="${cashApply.status.id==44004 }">已撤销</c:if>
          </p></td>
        </tr>  
        </c:forEach>  
      </tbody>
    </table>
     <c:if test="${!empty pagination }">
    	<div class="margin10 fav_pages"><winxuan-page:page bodyStyle="front-default" pagination="${pagination}" ></winxuan-page:page></div>
    </c:if>
    </c:when>
    <c:otherwise>	
    	<div class="null_context_tip">你还没有退款记录！</div> 	
    </c:otherwise>
    </c:choose>
    </div>
  </div>
  <div class="hei10"></div>
  <div class="refund_box" style="display:none" bind ="detailBank">  
  <h3><a href="javascript:;" class="close">close</a></h3>
  <p class="bank_box"> &nbsp;</p>	
  	 <div class="detail_box">
      <table width="100%" border="0" cellspacing="0">
        <tr>
          <td class="txt_right" width="20%" >银行名称：</td>
          <td width="80%">
          <input type="text"  disabled="disabled" id="detailBankName"/>
          </td>
        </tr>
        <tr>
          <td class="txt_right">所在支行：</td>
          <td> 
          <select areaLevel ="country1" style="display:none" disabled="disabled">
	   				<option value="-1">请选择国家</option>
          </select>
          
		  <select areaLevel="province1"  name="bankProvince" bind="detailBankProvince" disabled="disabled">
					<option value="-1">请选择省份</option>
		  </select> 
		 
		  <select areaLevel="city1"  name="bankCity" bind="detailBankCity" disabled="disabled">
					<option value="-1">请选择城市</option>
		 </select>
		
		 <select areaLevel="district1" name="district" style="display:none" disabled="disabled">
				<option value="-1" >请选择区县</option>
		 </select>
		  </td>                                                                           
	     </tr>
        <tr>
          <td class="txt_right">开卡人姓名：</td>
          <td><input type="text" size="40" name="bankAccountName" id="detailBankAccountName"  disabled="disabled"> 
           	 </td>
        </tr>
        <tr>
          <td class="txt_right">卡               号：</td>
          <td><input type="text" size="40" name="bankAccountCode" id="detailBankAccountCode"  disabled="disabled"> 
        	 </td>
        </tr>
        <tr>
          <td class="txt_right">联系电话：</td>
          <td><input type="text" size="40" name="bankAccountContactPhone" id="detailBankAccountContactPhone"  disabled="disabled"> 
           	</td>
        </tr>
      </table>
      <p class="bank_box"> &nbsp;</p>
      </div>    
    </div>

	<div class="refund_box" style="display: none" bind="detailAlipay" >
		<h3>
			<a href="javascript:;" class="close">close</a>
		</h3>
		<div class="detail_box" >
			<p class="bank_box"> &nbsp; </p>	
			<p class="bank_box">&nbsp;&nbsp;&nbsp;&nbsp;您的支付宝帐号：<input type="text" size="30"  name="alipay" id="detailAlipay" disabled="disabled">
			<p class="bank_box">您的支付宝认证姓名：<input type="text" size="30"  name="alipayname" id="detailAlipayname" disabled="disabled">
			</p>
			<p class="bank_box"> &nbsp; </p>	
		</div>
	</div>
	<%-- <div class="refund_box" style="display: none" bind="detailTenpay" style="width:300px">
		<h3>
			<a href="javascript:;" class="close">close</a>
		</h3>
		<div class="detail_box" >	
			<p class="bank_box">您的财付通帐号：<input type="text" size="30"  name="tenpay" id="tenpay" disabled="disabled">
			</p>
			<p class="bank_box"> &nbsp;</p>
			<p class="bank_box"> &nbsp;</p>	
		</div>
	</div> --%>
	
	<div  class="refund_post_box" style="display: none" bind="detailPost">
		<h3>
			<a href="javascript:;" class="close">close</a>
		</h3>
		<p class="bank_box"> &nbsp;</p>
      <table width="100%" border="0" cellspacing="0">
        <tr>
          <td class="txt_right" width="20%">收款人姓名：</td>
          <td width="80%"><input type="text" size="40" name="postAccountName" id="detailPostAccountName" disabled="disabled"></td>
        <tr>
          <td class="txt_right">地区：</td>
          <td>
          	<select areaLevel ="country2"  name="postCountry" disabled="disabled">
	   		     <option value="-1">请选择国家</option>
            </select>
			<select areaLevel="province2" name="postProvince" disabled="disabled">
				 <option value="-1">请选择省份</option>
			</select> <select areaLevel="city2" name="postCity" bind="detailPostCity" width="100px" disabled="disabled">
				 <option value="-1" width="100px">请选择城市</option>
			</select> <select areaLevel="district2" name="postDistrict"  id="detailPostDistrict"  width="100px" disabled="disabled">
				<option value="-1" width="100px">请选择区县</option>
			</select>
          </td>
        </tr>
        <tr>
          <td class="txt_right">街道/村庄：</td>
          <td><input type="text" size="40" name="postAddress"  id="detailPostAddress" disabled="disabled"/> </td>
        </tr>
        <tr>
          <td class="txt_right">邮编：</td>
          <td><input type="text" size="40" name="postCode"  id="detailPostCode" disabled="disabled">
          </td>
        </tr>
        <tr>
          <td class="txt_right">联系电话：</td>
          <td><input type="text" size="40" name="postContactPhone"  id="detailPostContactPhone" disabled="disabled"></td>
        </tr>
      </table>
      <p class="bank_box"> &nbsp;</p>	
    </div>  	
    	<div class="m_box" style="display: none" bind="modifyAlipay" >
			<h3>
				<a href="javascript:;" class="close">close</a>
			</h3>
			<p class="bank_box"> &nbsp; </p>
			<div class="modify_box" > 
			    <form:form class="temporary_money" action="" method="post" commandName="cashApplyForm" bind="alipayForm" >		
					<input type="hidden" name="type" value="43001"/>
					<p class="bank_box">&nbsp;&nbsp;&nbsp;&nbsp;您的支付宝帐号：<input type="text" size="30"  name="alipay" id="alipayAccount" /><b class="red" id="alipayError"></b>
			<p class="bank_box">您的支付宝认证姓名：<input type="text" size="30"  name="alipayname" id="alipaynameAccount"/><b class="red" id="alipaynameError"></b>
					</p>
					<p class="bank_box"> &nbsp; </p>
					<p class="margin10 txt_center">
					<input class="pas_save" type="button" value="确定" bind="confrimModify" param="43001">
					<input class="pas_cancel" type="button" value="取消" bind="cancelModify">
			   		</p>
			   </form:form>
			    <p class="bank_box"> &nbsp; </p>
			</div>		 
	 </div>
	 <div class="m_box"  bind="modifyBank" style="display: none">
			<h3>
				<a href="javascript:;" class="close">close</a>
			</h3>
			 <p class="bank_box"> &nbsp; </p>
	<div class="modify_box" > 
		<form:form class="temporary_money"  method="post" commandName="cashApplyForm" bind="bankForm" >
  		 <input  name="type" type="hidden" value="43003" />
      <table width="100%" border="0" cellspacing="0">
        <tr>
          <td class="txt_right" width="20%">银行名称：</td>
          <td width="80%">
          <select class="select_with1" name="bank" bind="bank" >
            	<option value="-1"> --请选择银行-- </option>
            	<c:forEach var="bank" items="${bankType}">
						<option value="${bank.id}"> ${bank.name} </option>					
				</c:forEach>
          </select><b class="red" id="bankError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">所在支行：</td>
          <td>   
          <select areaLevel ="country" style="display:none" disabled="disabled">
	   				<option value="-1">请选择国家</option>	
          </select>
          
		  <select areaLevel="province"  name="bankProvince" bind="bankProvince">
					<option value="-1">请选择省份</option>
		  </select> 
		 
		  <select areaLevel="city"  name="bankCity" bind="bankCity">
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
          <td><input type="text" size="20" name="bankAccountName" id="bankAccountName" > 
           	 <b id="bankAccountNameMessage">如：张信哲</b><b class="red" id="bankAccountNameError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">卡               号：</td>
          <td><input type="text" size="20" name="bankAccountCode" id="bankAccountCode" > 
        	 <b id="bankAccountCodeMessage">如：6625441033825537517</b><b class="red" id="bankAccountCodeError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">联系电话：</td>
          <td><input type="text" size="20" name="bankAccountContactPhone" id="bankAccountContactPhone" > 
           	 <b id="bankAccountContactPhoneMessage">如：010-88008800,13908181234</b><b class="red" id="bankAccountContactPhoneError"></b></td>
        </tr>
      </table>     
    <p class="margin10 txt_center">
      	<input class="pas_save" type="button" value="确定" bind="confrimModify" param="43003">
		<input class="pas_cancel" type="button" value="取消" bind="cancelModify">  
    </p>
    <p class="bank_box"> &nbsp; </p>
    </form:form>	
	</div>	
	</div>
	 <div class="m_post_box"  bind="modifyPost" style="display: none">
			<h3>
				<a href="javascript:;" class="close">close</a>
			</h3>
			 <p class="bank_box"> &nbsp; </p>
	<div class="modify_box" > 
		<form:form class="temporary_money" action="" method="post" commandName="cashApplyForm" bind="postForm" >		   
    <input  name="type" type="hidden" value="43004" />
      <table width="100%" border="0" cellspacing="0">
        <tr>
          <td class="txt_right" width="20%">收款人姓名：</td>
          <td width="80%"><input type="text" size="30" name="postAccountName" id="postAccountName"  ><b class="red" id="postAccountNameError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">地区：</td>
          <td>
          	<select areaLevel ="country3"  name="postCountry" width="50px" disabled="disabled">
	   		     <option value="-1">请选择国家</option>
            </select>
			<select areaLevel="province3" name="postProvince" width="50px">
				 <option value="-1">请选择省份</option>
			</select> <select areaLevel="city3" name="postCity" bind="postCity" width="50px">
				 <option value="-1" width="100px">请选择城市</option>
			</select> <select areaLevel="district3" name="postDistrict" width="50px">
				<option value="-1" width="100px">请选择区县</option>
			</select><br/>
			<b class="red" id="postAreaError"></b>
          </td>
        </tr>
        <tr>
          <td class="txt_right">街道/村庄：</td>
          <td><input type="text" size="30" name="postAddress"  id="postAddress"/> 
           	 <b id="postAddressMessage">如：西藏北路45号东方大厦1564室</b><b class="red" id="postAddressError"></b></td>
        </tr>
        <tr>
          <td class="txt_right">邮编：</td>
          <td><input type="text" size="30" name="postCode"  id="postCode">
          		<b class="red" id="postCodeError"></b>
          </td>
        </tr>
        <tr>
          <td class="txt_right">联系电话：</td>
          <td><input type="text" size="30" name="postContactPhone"  id="postContactPhone"> 
         	   <b id="postContactPhoneMessage">如：010-88008800,13908181234</b><b class="red" id="postContactPhoneError"></b></td>
        </tr>
      </table>
    <p class="margin10 txt_center">
      <input class="pas_save" type="button" value="确定" bind="confrimModify" param="43004">
		<input class="pas_cancel" type="button" value="取消" bind="cancelModify">  
    </p>
    <p class="bank_box"> &nbsp; </p>
    </form:form>
    </div>
    </div>
	
	<%@include file="../../snippets/footer.jsp"%>
<script type="text/javascript" src="${serverPrefix}js/account/account.js?${version}"></script>
<script type="text/javascript" src="${serverPrefix}js/account/detail.js?${version}"></script>
<script type="text/javascript" src="${serverPrefix}js/account/modify.js?${version}"></script>
</body>
</html>
