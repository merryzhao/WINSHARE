<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="left_menu_bg" style="display:none;"></div>
<input type="hidden" value="${param.jspname}"/>
        <div class="left_container myaccount">
            <h3 class="fb acc_tit">文轩礼品卡</h3>
            <ul class="acc_menu">
              <li ><a href="http://www.winxuan.com/presentcard/info">基本信息</a></li>
              <li ><a href="http://www.winxuan.com/presentcard/pwdchange">修改密码</a></li>
              <li class="last"><a href="http://www.winxuan.com/presentcard/quit">退出</a></li>
            </ul>
            <ul class="acc_menu">
                <li><a href="javascript:;">礼品卡使用帮助</a></li>
                <li class="last"><a href="javascript:;">礼品卡客服</a></li>
            </ul>
        </div>
      
<img class="margin10" src="/images/presentcard/contact-method.png"/>
<script type="text/javascript">
  seajs.use(["jQuery"],function($){
	  var typeval = $("[type='hidden']").val();
	  var ul = $("ul.acc_menu");
	  var li = ul.find("li");
	  li.eq(typeval).attr("class","linknow");
  })
</script>