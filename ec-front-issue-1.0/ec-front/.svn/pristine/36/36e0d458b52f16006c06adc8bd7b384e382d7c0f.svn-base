<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.winxuan.com/tag/page"  prefix="winxuan-page"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-用户资料</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_1" />
</jsp:include>
<form:form action="http://www.winxuan.com/customer/detail" commandName="detailForm"  method="post">
<input name="_method" type="hidden" value="get"/>
  <div class="right_box">
    <h3 class="myfav_tit margin10">
    <c:if test="${customer.source.id!=40100&&customer.source.id!=40001}">
                  您是来自于<font color="#EC7800">${customer.source.name}</font>的用户
    </c:if>
     <c:if test="${customer.source.id==40100||customer.source.id==40001}">
                我的资料
    </c:if>
    </h3>
    
    <ul class="infor_tab">
      <li class="current_info">基本信息</li>
      <li><a id="moreInfo">更多个人信息</a></li>
      <li style="display:none">我的会员级别</li>
    </ul>
    <div class="locate">
    <div class="edit_photo"><img width="96" height="96" src="${customer.avatar == null || customer.avatar == '' ? 'http://static.winxuancdn.com/goods/account_photo.jpg' : customer.avatar}"><br><a class="link1" href="javascript:;">修改头像</a></div>
    <table class="user_info" width="100%" border="0" cellspacing="0">
      
      <c:if test="${customer.source.id==40001||customer.source.id==40100}">
      <tr>
            <td class="txt_right" width="21%">登录名：</td>
            <td class="fb" width="79%">${customer.name}<input type="hidden" name="flag" value="2"></td>
           </tr> 
           <tr>
            <td class="txt_right" width="21%">昵称：</td>
            <td class="fb" width="79%">
            <form:input path="nickName" value="${customer.nickName}"/>
            <form:errors path="nickName" cssStyle="color: red;" />
            <!-- 
            <input name="nickName" id="nickName" type="text" value="${customer.nickName}"></td>
            -->
           </tr>
         </c:if>
        
       <c:if test="${customer.source.id!=40001&&customer.source.id!=40100}">
       <tr>
           <td class="txt_right" width="21%">昵称：</td>
            <td class="fb" width="79%">
            <form:input path="nickName" value="${customer.nickName}"/>
            <form:errors path="nickName" cssStyle="color: red;" />
            <input type="hidden" name="flag" value="2"></td>
            </tr>
          </c:if>
          
    
      <tr>
        <td class="txt_right" width="21%">真实姓名：</td>
        <td><input name="realName" id="realName" type="text" value="${customer.realName}"></td>
      </tr>
      <tr>
        <td class="txt_right" width="21%">性别：</td>
        <td><input type="radio" name="gender" id="gender" value="1" <c:if test="${customer.gender == 1}">checked="checked"</c:if>>
          男
          <input type="radio" name="gender" id="gender" value="0" <c:if test="${customer.gender == 0}">checked="checked"</c:if>>
          女</td>
      </tr>
      <tr>
        <td class="txt_right" width="21%">生日：</td>
        <td>
        	<input name="birthday" id="birthday" type="text" value="<fmt:formatDate value="${customer.birthday}" pattern="yyyy-MM-dd"/>" readonly="readonly" value="${customer.birthday}" style="display:none;">
        	<select name="year" bind="date:year" style="width:60px;"></select>年
        	<select name="month" bind="date:month" style="width:40px;"></select>月
        	<select name="day" bind="date:day" style="width:40px;"></select>日
        </td>
      </tr>
      <tr>
        <td class="txt_right" width="21%">居住地：</td>
        <td>
          <select areaLevel ="country" name="country" id="country"></select>
          <select areaLevel ="province" name="province" id="province"><option value="-1">请选择省份</option></select>
          <select areaLevel="city" name="city" id="city"><option value="-1">请选择城市</option></select>
        </td>
      </tr>
     
      <tr>
        <td class="txt_right" width="21%">邮箱：</td>
        <td><input name="email" id="email" type="text" value="${customer.validEmail}">
        <c:if test="${customer.emailActive == 0}"><a class="link1" bind="emailValidate" href="javascript:;">立即验证</a></c:if>
        <c:if test="${customer.emailActive == 1}"><span class="link2">已验证</span></c:if>
        <c:if test="${customer.emailActive != 0 && customer.emailActive != 1}"><a class="link2" bind="emailValidate" href="javascript:;">重新发送验证码</a></c:if>
          </td>
      </tr>
     
      <tr>
        <td class="txt_right" width="21%">手机：</td>
        <td><input name="mobile" id="mobile" type="text" value="${customer.mobile}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
      </tr>
    </table>
        <p class="margin10 txt_center">
      <input class="user_submit" id="basic_but" type="submit" value="保存基本信息">
    </p>
    </div>
    <div class="hei10"></div>
  </div>
  <div class="hei10"></div>
</div>
</form:form>
<%@include file="../../snippets/version2/footer.jsp" %>
<script>
<c:if test="${customer != null}">
	var currentArea={
		country:"${customer.country.id}",
		province:"${customer.province.id}",
		city:"${customer.city.id}"
	};
	var birthday={
			year:"${year}",
			month:"${month}",
			day:"${day}"
	};
	var email={email:"${customer.email}"};
	</c:if>
</script>
<script type="text/javascript" src="${serverPrefix}js/customer/detail.js"></script>	
</body>
</html>
