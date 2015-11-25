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
<style type="text/css">
.textarea_gray{ color:#a6a6a6;}
</style>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>

<body>
<jsp:include page="../../snippets/version2/header.jsp"></jsp:include>
<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_1" />
</jsp:include>
<form:form action="http://www.winxuan.com/customer/more" commandName="moreForm"  method="post" >
<input name="_method" type="hidden" value="get"/>
  <div class="right_box">
    <h3 class="myfav_tit margin10">我的资料</h3>
    <ul class="infor_tab">
      <li><a id="basicInfo">基本信息</a></li>
      <li class="current_info">更多个人信息</li>
  	  <li style="display:none">我的会员级别</li>
    </ul>
    <table class="user_info" width="100%" border="0" cellspacing="0">
      <tr>
        <td class="txt_right" width="21%">居住状态：<input type="hidden" name="flag" value="2"></td>
        <td width="79%">
       	<input class="living" type="checkbox" value="32001" <c:if test="${selectedParameters['32001'] == 32001}">checked="checked"</c:if>>独居
       	<input class="living" type="checkbox" value="32002" <c:if test="${selectedParameters['32002'] == 32002}">checked="checked"</c:if>>和伴侣
       	<input class="living" type="checkbox" value="32003" <c:if test="${selectedParameters['32003'] == 32003}">checked="checked"</c:if>>和室友
       	<input class="living" type="checkbox" value="32004" <c:if test="${selectedParameters['32004'] == 32004}">checked="checked"</c:if>>和父母
       	<input class="living" type="checkbox" value="32005" <c:if test="${selectedParameters['32005'] == 32005}">checked="checked"</c:if>>和孩子
       	<input class="living" type="checkbox" value="32006" <c:if test="${selectedParameters['32006'] == 32006}">checked="checked"</c:if>>和宠物
        <input id="livingStatus" name="livingStatus" type="hidden" value="${customerExtension.livingStatus}">
		</td>
      </tr>
      <tr>
        <td class="txt_right" width="21%">身份：</td>
        <td><input type="radio" name="careerType" id="careerType" value="33001" <c:if test="${customerExtension.careerType.id == 33001}">checked="checked"</c:if>>
          在校学生
          <input type="radio" name="careerType" id="careerType" value="33002" <c:if test="${customerExtension.careerType.id == 33002}">checked="checked"</c:if>>
          教师
          <input type="radio" name="careerType" id="careerType" value="33003" <c:if test="${customerExtension.careerType.id == 33003}">checked="checked"</c:if>>
          上班族
          <input type="radio" name="careerType" id="careerType" value="33004" <c:if test="${customerExtension.careerType.id == 33004}">checked="checked"</c:if>>
          自由职业<br><select name="career" id="career" currentValue="${customerExtension.career.id}"></select></td>
      </tr>
      <tr>
        <td class="txt_right" width="21%">月收入：</td>
        <td><select name="salary" id="salary">
        	<option value="-1">请选择</option>
        	<c:if test="${!empty salaryList}">
	        <c:forEach var="salary" items="${salaryList}">
	        	<option value="${salary.id}" <c:if test="${customerExtension.salary.id == salary.id}">selected="selected"</c:if>>${salary.name}</option>
	        </c:forEach>
	        </c:if>
          </select></td>
      </tr>
      <tr>
        <td class="txt_right" width="21%">兴趣爱好：</td>
        <td><textarea id="interest" name="interest" cols="65" rows="3">${customerExtension.interest}</textarea></td>
      </tr>
      <tr>
        <td class="txt_right" width="21%" nowrap="nowrap">喜欢或欣赏的人：</td>
        <td><textarea id="favorite" name="favorite" cols="65" rows="3">${customerExtension.favorite}</textarea></td>
      </tr>
    </table>
    <p class="margin10 txt_center">
      <input class="user_submit" type="submit" value="保 存 信 息">
    </p>
  </div>
  <div class="hei10"></div>
</div>
</form:form>
<%@include file="../../snippets/version2/footer.jsp" %>
<script type="text/javascript" src="${serverPrefix}js/customer/more.js"></script>
<script type="text/javascript" src="${serverPrefix}js/customer/identity.js"></script>	
</body>
</html>