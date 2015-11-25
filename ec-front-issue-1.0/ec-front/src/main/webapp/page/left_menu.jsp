<%@page import="com.winxuan.ec.model.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<%
    User u = (User)request.getAttribute("com.winxuan.ec.model.user.User");
    request.setAttribute("customer",u);
%> 
<div class="left_box">
        <div class="left_menu_bg" style="display:none;"></div>
        <div class="left_container myaccount">
            <h3 class="txt_center fb acc_tit">订单管理</h3>
            <ul class="acc_menu">
                <li id="menu_1_1"><a href="/customer/order">我的订单</a></li>
                <li id="menu_1_2"><a href="/customer/returnorder">退换货服务</a></li>
                <li id="menu_1_4"><a href="/customer/complaint">我要投诉</a></li>
                <li id="menu_1_3" class="last"><a href="/help/help_center.html" target="_blank">帮助</a></li>
            </ul>
            <h3 class="txt_center fb acc_tit">我的商品</h3>
            <ul class="acc_menu">
                <li id="menu_2_1"><a href="/customer/bought">已购商品</a></li>
                <li id="menu_2_2"><a href="/customer/favorite">我的收藏</a></li>
                <li id="menu_2_3"><a href="/customer/notify/arrival?hasStock=true">到货通知</a></li>
                <li id="menu_2_4" class="last"><a href="/customer/notify/priceReduce?reduced=true">降价通知</a></li>
            </ul>
             <h3 class="txt_center fb acc_tit">我的数字馆</h3>
            <ul class="acc_menu">
                <li id="menu_5_1"><a href="/customer/digital/shelf">我的书架</a></li>
                <li id="menu_5_2"><a href="/customer/digital/order">我的电子书订单</a></li>
                <li id="menu_5_3" class="last"><a href="/customer/digital/bookexchange">书籍券兑换</a></li>
            </ul>
            <h3 class="txt_center fb acc_tit">我的帐户</h3>
            <ul class="acc_menu">
                <li id="menu_3_1"><a href="/customer/detail">我的资料</a></li> 
               <c:if test="${customer.source.id==40001||customer.source.id==40100||customer.source.id==40021}">
                <li id="menu_3_2"><a href="/customer/updatePassword">修改密码</a></li>
                </c:if> 
                <li id="menu_3_3"><a href="/customer/present">我的礼券</a></li>
                <li id="menu_3_4"><a href="/customer/presentcard">我的礼品卡</a></li>
                <li id="menu_3_5"><a href="/customer/points">我的积分</a></li>
                <li id="menu_3_6"><a href="/customer/address">我的收货地址</a></li>
                <li id="menu_3_7" class="last"><a href="/customer/advanceaccount">我的暂存款</a></li>
                <c:if test="${customer.source.id!=40001&&customer.source.id!=40100&&customer.source.id!=40021}">
                  <li id="menu_3_8"><a href="/customer/bindingAccount">绑定账号</a></li>   
                </c:if>   
            </ul>
            <h3 class="txt_center fb acc_tit">我的社区</h3>
            <ul class="acc_menu">
                <li id="menu_4_1"><a href="/customer/comment/product">我的评论</a></li>
                <li id="menu_4_2" class="last"><a href="/customer/question">我的咨询</a></li>
            </ul>
           
        </div>
    </div>
    
 <script>
	 	<c:if test='${param.id != null}'>
	 	var selected="menu_"+"${param.id}";
	 	</c:if>
		if(typeof(selected)!="undefined"){
			var selectedObj = document.getElementById(selected);
			if (selectedObj) {
				var objEl =selectedObj;
				objEl.className = objEl.className==null?"":objEl.className + " linknow";
			}
		}
	</script>