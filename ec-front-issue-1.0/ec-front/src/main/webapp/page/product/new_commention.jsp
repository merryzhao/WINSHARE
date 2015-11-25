<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品评论</title>
<jsp:include page="../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body>
  <c:if test="${productSale.product.sort.id == 11001}">
	<jsp:include page="../snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11002}">
	<jsp:include page="../snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11003}">
	<jsp:include page="../snippets/version2/header.jsp">
		<jsp:param value="mall" name="label"/>
	</jsp:include>
</c:if>
<div class="layout">

   	<input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} > ${productSale.effectiveName}</span></div>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title">评论商品</h4>
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }" class="goodspic"><img src="${productSale.product.imageUrlFor200px }" alt="${productSale.effectiveName}"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName}</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${productSale.product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${productSale.avgStarScore*20}%;"></b></div>（<a class="rating_link" href="javascript:;" id="allScoreCount">${rankCount}</a>）</div>
            <div class="left_com">评论数：${commentCount}</div>
            <p class="txt_center margin10"><a class="buy_but" href="javascript:;">购买</a> <a class="coll_but" href="javascript:;">收藏</a></p>
            <div class="hei10"></div>
        </div>
    </div>
    <div class="right_box">
        <h4 class="comment_tit3 margin10 f14"><b class="black fb">被评商品：</b>
      
        	<a class="fb link3" title="${productSale.product.name}" href="${productSale.product.url}">${productSale.product.name}</a>
        </h4>
        <p class="com_tips yellow_bg">评论只针对商品本身，不要针对交易、配送等服务过程。有关服务过程的问题，请查看<a class="link3" href="javascript:;">帮助中心</a>，或者<a class="link3" href="javascript:;">联系客服</a>。文轩网感谢您的参与！完成评价后，您将获得一定的<a class="link3" href="javascript:;">经验值</a>和<a class="link3" href="javascript:;">积分</a>。</p>
        <div class="margin10">
            <div class="consulting">
            		<form action="/product/${productSale.id}/_newcomment" method="post">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="15%" height="55" align="right">
                        <b class="f14 fb">商品评分：</b></td>
                        <td width="59%">
                     			 <div class="do_score">
                      			 	<i class="solid_star" value="1"></i>
                      			 	<i class="solid_star" value="2"></i>
                      			 	<i class="solid_star" value="3"></i>
                      			 	<i class="solid_star" value="4"></i>
                      			 	<i class="hollow_star" value="5"></i>
                      			 	<label>请点击星星进行评分</label>
                      			 <input type="hidden" name="rank" value="4" >
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
                        <td><b class="f14 fb">（字数3000字）</b><p class="grayfont"><br>˙鼓励你的原创评论<br>˙请只针对只购商品本身<br>˙不要针对交易配送等服务过程</p></td>
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
    <div class="hei10"></div>
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
<script type="text/javascript" src="${serverPrefix}/js/product.js?${version}"></script>
<jsp:include page="../snippets/version2/footer.jsp"></jsp:include>
</body>
</html>
