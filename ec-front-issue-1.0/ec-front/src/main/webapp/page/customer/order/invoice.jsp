<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="/css2/images/my/acc_order.css" type="text/css" rel="stylesheet" />
<title>补开发票</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
</jsp:include>
</head>
<body>
<jsp:include page="/page/snippets/version2/header.jsp"></jsp:include>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   补开发票</span></div>
    <jsp:include page="/page/left_menu.jsp">
		<jsp:param name="id" value="1_1" />
	</jsp:include>
    <div class="right_box">
    	<h3 class="myfav_tit margin10">补开发票</h3>
        <div class="order_invoice">
   		  <form action="/customer/order/save?format=json" method="post" id="invoiceForm">
       	  <div class="order_invoice_form">
            	<p><span class="form_title">发票抬头：</span><input id="personal" name="titleType" type="radio" value="3460" checked="checked" />个人<input id="company" name="titleType" class="radio"  type="radio" value="3461" />单位<input 
            	  type="text" class="text" title="请填写单位名称" style="margin-left:18px;color:#999; display:none;" name="title" value="" /><span name="info"></span></p>
            <p><span class="form_title">发票面额：</span><strong class="price">￥${orderInvoiceCreateForm.payMentMoney.pay}</strong><a class="view" href="javascript:;">查看计算规则</a>
            <div class="view_pop" style="display:none;">
            	<span class="close"></span>
           	<table class="view_table">
                    	<tr>
                       	    <th>商品应付金额</th>
                            <th>礼券金额</th>
                            <th>礼品卡金额</th>
                       	    <th>运费金额</th>
                            <th>发票面额</th>
                        </tr>
                        <tr class="count">
                       	  <td colspan="5">
                       	  	<span>￥${orderInvoiceCreateForm.payMentMoney.all} </span><span><fmt:formatNumber  type="currency" value="${orderInvoiceCreateForm.payMentMoney.certificate}" pattern="￥0.00"></fmt:formatNumber></span><span><fmt:formatNumber  type="currency" value="${orderInvoiceCreateForm.payMentMoney.gift}" pattern="￥0.00"></fmt:formatNumber></span><span>￥${orderInvoiceCreateForm.payMentMoney.deliveryfree}</span><span>￥${orderInvoiceCreateForm.payMentMoney.pay}</span>
                       	  </td>
                        </tr>
                  </table>
              </div>
              <p><span class="form_title"> 地区：</span>
              	<input id="area" type="hidden" value="${orderInvoiceCreateForm.country},${orderInvoiceCreateForm.province},${orderInvoiceCreateForm.city},${orderInvoiceCreateForm.district},${orderInvoiceCreateForm.town}">
				<select name="country" areaLevel="country"></select>
				<select name="province" areaLevel="province"><option value="-1">请选择</option></select>
				<select name="city" areaLevel="city"><option value="-1">请选择</option></select>
				<select name="district" areaLevel="district"><option value="-1">请选择</option></select> 
				<select name="town" areaLevel="town"><option value="-1">请选择</option></select> 
				<span name="info"></span>
			  </p>
              <p><span class="form_title">邮寄地址：</span><input type="text" name="address" class="text" title="请填写邮寄地址" value="${orderInvoiceCreateForm.address}" /><span name="info"></span></p>
              <p><span class="form_title">联系电话：</span><input type="text" name="mobile" class="text" title="请填写联系电话" value="${orderInvoiceCreateForm.mobile}" /><span name="info"></span></p>
              <p><span class="form_title">邮政编码：</span><input type="text" name="zipCode" class="text" title="请填写邮政编码" value="${orderInvoiceCreateForm.zipCode}" /><span name="info"></span></p>
              <p><span class="form_title">收件人姓名：</span><input type="text" name="consignee" class="text" title="请填写收件人姓名" value="${orderInvoiceCreateForm.consignee}" /><span name="info"></span></p>
              <p class="btn"><span class="form_title"></span><button type="button" class="btn1" name="submit">确认提交</button><button type="button" class="btn2" onclick="javascript:window.location.href = '/customer/order'">取消</button></p>
            </div>
            <input type="hidden" name="orderId" value="${orderInvoiceCreateForm.orderId}">
            </form>
            <div class="order_invoice_info">
            	<dl>
                	<dt>特别说明：</dt>
                    <dd>1、补开图书(及音像)类商品发票，开具内容只能为“图书”；</dd>
					<dd>2、补开百货类商品发票，请与第三方卖家联系进行补开；</dd>
					<dd>3、发票开具总金额为商品应付总金额，不包含运费、礼品卡及礼券支付部分的金额；</dd>
					<dd>4、百货类商品明细总条目数超过单张发票可开具的条目数时，系统会根据实际应开发票的张数，自动将您购买的百货类商品应付的总金额作平均
   分配。</dd>
   					<dd><a class="help" href="http://www.winxuan.com/help/invoice.html" target="_blank">查看帮助&gt;&gt;</a></dd>
                </dl>
            </div>
        </div>
    </div>
    <div class="hei10"></div>
</div>
<%@include file="/page/snippets/version2/footer.jsp" %>
<script type="text/javascript" src="${serverPrefix}js/orderinvoice/orderinvoice.js?20131028"></script>
</body>
</html>
