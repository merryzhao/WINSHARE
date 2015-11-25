<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<TITLE>业务管理系统 ·文轩在线 - Console.Winxuan.Com - 新华文轩</TITLE>
<LINK href="../images/login.css" type=text/css rel=stylesheet>
</HEAD>
<BODY id=userlogin_body>
<DIV></DIV>
<form:form action="/employee/login" method="post" commandName="loginForm">
<token:token/>
<DIV id=user_login>
<DL>
  <DD id=user_top>
  <UL>
    <LI class=user_top_l></LI>
    <LI class=user_top_c></LI>
    <LI class=user_top_r></LI></UL>
  <DD id=user_main>
  <UL>
    <LI class=user_main_l></LI>
    <LI class=user_main_c>
    <DIV class=user_main_box>
    <UL>
      <LI class=user_main_text>用户名： </LI>
      <LI class=user_main_input>
      <form:input path="name" cssClass="TxtUserNameCssClass" maxlength="20"/>
      </LI></UL>
     
    <UL>
      <LI class=user_main_text>密 码： </LI>
      <LI class=user_main_input>
      <form:password path="password" cssClass="TxtPasswordCssClass" maxlength="20"/>
      <form:hidden path="from" />
      </LI></UL>
      <UL>
       <LI class=user_main_input>
         <SPAN id=ValrUserName style="COLOR:#D95353">${errorMessage }</SPAN>
   		 <SPAN id=ValrUserName style="COLOR:#D95353"><form:errors path="name" cssStyle="" /></SPAN>
		 <SPAN id=ValrPassword style=" COLOR:#D95353"> <form:errors path="password" cssStyle="" /></SPAN>
		</LI>
      </UL>
    </DIV>
    </LI>
    <LI class=user_main_r><INPUT class=IbtnEnterCssClass id=IbtnEnter 
    style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px" 
    onclick='javascript:WebForm_DoPostBackWithOptions(new WebForm_PostBackOptions("IbtnEnter", "", true, "", "", false, false))' 
    type=image src="../images/user_botton.gif" name=IbtnEnter> </LI></UL>
  <DD id=user_bottom>
  <UL>
    <LI class=user_bottom_l></LI>
    <LI class=user_bottom_c>
    <SPAN style="MARGIN-TOP: 0px"><a href="http://10.1.2.51:8099/report/login.html" target="_blank">报表系统</a></SPAN>|
    <SPAN style="MARGIN-TOP: 0px"><a href="http://jm-console.winxuan.com" target="_blank">加盟店后台</a></SPAN>|
    <SPAN style="MARGIN-TOP: 0px"><a href="http://seller.winxuan.com" target="_blank">卖家后台</a></SPAN>|
    <SPAN style="MARGIN-TOP: 0px"><a href="http://cs.winxuan.com" target="_blank">客服系统</a></SPAN>|
    <SPAN style="MARGIN-TOP: 0px"><a href="http://issue.winxuan.com" target="_blank">问题跟踪系统</a></SPAN>|
    <SPAN style="MARGIN-TOP: 0px">Console.Winxuan.Com</SPAN> 
    </LI>
    <LI class=user_bottom_r></LI></UL></DD></DL></DIV>
</DIV>
<DIV></DIV>

</form:form>
</BODY></HTML>
