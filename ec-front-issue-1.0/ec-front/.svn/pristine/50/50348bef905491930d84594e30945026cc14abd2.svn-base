<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<%@page import="java.util.Random"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品评论 - 会员中心 - 文轩网</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="my_acc_order" name="type"/>
</jsp:include>
</head>
<body>
<jsp:include page="../../snippets/version2/header.jsp">
	<jsp:param value="book" name="label"/>
</jsp:include>


<div class="layout">
  <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的评论</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="4_1" />
</jsp:include>
<div class="right_box">
    <h3 class="myfav_tit margin10">我的社区评论</h3>
        <h4 class="comment_tit3 margin10 f14"><b class="black fb">被评商品：</b>
        	<a class="fb link3" title="将才：让年轻人少奋斗5年" href="${prodcutSale.id}">${prodcutSale.effectiveName}</a>
        </h4>
        <p class="border_style">请只针对商品本身的质量、使用体验等方面发表评论。如果您对商品的交易、配送等服务过程有任何意见，请查看<a class="link3" href="http://www.winxuan.com/help/faq.html" target="_blank">帮助中心</a>，或者<a class="link3" href="http://www.winxuan.com/help/contact.html" target="_blank">联系客服</a>。文轩网感谢您的参与！完成评价后，您将获得一定的<a class="link3" href="http://www.winxuan.com/help/score.html" target="_blank">会员积分</a>。</p>
        <div class="margin10">
            <div class="consulting">
            		<form action="/customer/comment/${prodcutSale.id}" method="post">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="15%" height="55" align="right">
                        <b class="f14 fb">商品评分：</b></td>
                        <td width="59%">
                     			 <div class="do_score" id="do_score">
                      			 	<i class="solid_star" value="1"></i>
                      			 	<i class="solid_star" value="2"></i>
                      			 	<i class="solid_star" value="3"></i>
                      			 	<i class="solid_star" value="4"></i>
                      			 	<i class="solid_star" value="5"></i>
                      			 	<label>请点击星星进行评分</label>
                      			 <input type="hidden" name="rank" value="5" >
                     			 </div>
                       </td>
                        <td width="26%">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right"><b class="f14 fb">评论标题：</b></td>
                        <td><input class="unity_w2" type="text" name="title" ></td>
                        <td><b class="fb f14">（25字）</b><b class="l_gray">请给予真实、客观的评价。</b></td>
                    </tr>
                    <tr>
                        <td align="right" style="vertical-align:text-top;"><b class="f14 fb">评论正文：</b></td>
                        <td><textarea class="unity_w2" name="content" id="content" cols="45" rows="7"></textarea></td>
                        <td><b class="f14 fb">（字数500字）</b><p class="grayfont"><br>˙鼓励你的原创评论<br>˙请只针对只购商品本身<br>˙不要针对交易配送等服务过程</p></td>
                    </tr>
                    <tr>
                        <td align="right"><b class="f14 fb">验证码：</b></td>
                        <td><input name="sRand" type="text" size="10" >
                            <img data-lazy="false" id="safeCode" src="/verifyCode" /> 看不清 <a class="link2" href="javascript:changeRandCode()">换一张</a></td>
                        <td>&nbsp;</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td align="right"><input class="consult_but" name="" type="submit" value="立即发表评论"></td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                </form>
            </div>
        </div>
    </div>
<script type="text/javascript">
function changeRandCode(){
	var img =document.getElementById("safeCode"); 
	img.src=img.src+"?d="+Math.random; 
}

seajs.use(["jQuery"],function($){
	var parentDiv = $("div.do_score").find("i");
	parentDiv.click(function(){
		var thisEl = $(this);
		value = thisEl.attr("value");
		$("input[name=rank]").attr("value",value);
		parentDiv.each(function(){
			var el = $(this);
			var otherValue = el.attr("value");
			if(otherValue <= value){
				el.removeClass("hollow_star");
				el.addClass("solid_star");
			}else{
				el.removeClass("solid_star");
				el.addClass("hollow_star");
			}
		});
	});
});
</script>
<%@include file="../../snippets/footer.jsp"%>
</body>
</html>
