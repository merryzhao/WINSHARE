<%@page pageEncoding="UTF-8"%><%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品评论</title>
<jsp:include page="../../snippets/version2/meta.jsp">
	<jsp:param value="details" name="type"/>
</jsp:include>
</head> 
<body class="newcomment">
  <c:if test="${productSale.product.sort.id == 11001}">
	<jsp:include page="../../snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11002}">
	<jsp:include page="../../snippets/version2/header.jsp">
		<jsp:param value="book" name="label"/>
	</jsp:include>
</c:if>
<c:if test="${productSale.product.sort.id == 11003}">
	<jsp:include page="../../snippets/version2/header.jsp">
		<jsp:param value="mall" name="label"/>
	</jsp:include>
</c:if>
<div class="layout">

   	<input type="hidden" value="${productSale.id}" class="productSaleId"/>
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;  ${productSale.product.category.allCategoryName} > ${productSale.effectiveName}</span></div>
    <div class="left_box">
        <div class="left_container yellow_bg">
            <h4 class="fb box_title" style="text-align:center;">商品信息</h4>
            <p class="advisory txt_center"><a title="${productSale.effectiveName}" href="${productSale.product.url }" class="goodspic"><img src="${productSale.product.imageUrlFor200px }" alt="${productSale.effectiveName}"></a><br><a class="link1" href="${productSale.product.url }">${productSale.effectiveName}</a><br>文轩价：<b class="red fb">￥${productSale.salePrice }</b><br><del>定价：￥${productSale.product.listPrice}</del></p>
            <div class="left_com"><span class="fl">评分：</span><div class="com_star fl"><b style="width:${aveRank*20}%;"></b></div>（<b class="rating_link" href="javascript:;" id="allScoreCount">${rankCount}</b>人）</div>
            <div class="left_com">评论数：${commentCount}人</div>
            <p class="txt_center margin10">
            	<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${productSale.id}">购买</a> 
            	<a class="mini_coll_but" href="javascript:;" bind="addToFavorite" data-id="${productSale.id}">收藏</a>
            </p>
            <div class="hei10"></div>
        </div>
    </div>
    <div class="right_box">
        <h4 class="comment_tit3 margin10 f14"><b class="black fb">被评商品：</b>
        	<a class="fb link3" title="${productSale.effectiveName}" href="${productSale.product.url}">${productSale.effectiveName}</a>
        </h4>
        <p class="com_tips yellow_bg">评论只针对商品本身，不要针对交易、配送等服务过程。有关服务过程的问题，请查看<a class="link3" href="http://www.winxuan.com/help/contact.html" target="_blank">帮助中心</a>，或者<a class="link3" href="http://www.winxuan.com/help/contact.html" target="_blank">联系客服</a>。文轩网感谢您的参与！完成评价后，您将获得一定的<a class="link3" href="http://www.winxuan.com/help/score.html" target="_blank">经验值</a>和<a class="link3" href="http://www.winxuan.com/help/score.html" target="_blank">积分</a>。</p>
        <div class="margin10">
            <div class="consulting">
            		<form action="/product/${productSale.id}/comment" method="post"  bind="commentForm">
					<c:if test="${!empty quoteCustomerComment }"><input type="hidden" name="quoteCommentId" value="${quoteCustomerComment.id }"/></c:if>
                	<c:if test="${not empty quoteCustomerComment }">
                		<div class="yy_comment">
                			<h1>引用<b class="u_name link1"><c:choose><c:when test="${not empty quoteCustomerComment.customer }">${quoteCustomerComment.customer.protectionName }</c:when><c:otherwise>游客</c:otherwise></c:choose></b>的评论: <b>${quoteCustomerComment.title }</b></h1>
                			${quoteCustomerComment.content}
                		</div>
                	</c:if>
                	
                	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="15%" height="55" align="right">
                        <b class="f14 fb">商品评分：</b></td>
                        <td width="59%">
                     			 <div class="do_score" id="do_score" param="0">                   			 	
                     				 <b class="star1"></b><b class="star2"></b><b class="star3"></b><b class="star4"></b><b class="star5"></b><label bind="scoring"'></label>
                     			 </div>
                     			  <input type="hidden" name="rank" value="5" >
                       </td>
                        <td width="26%">&nbsp;</td>
                    </tr>
                    <tr>
                        <td align="right"><b class="f14 fb">评论标题：</b></td>
                        <td><input class="unity_w2 reply_txt" type="text" name="title" id="commentTitle"></td>
                        <td><div bind="titleMessage"><b class="fb f14">（25字）</b><b class="l_gray">请给予真实、客观的评价。</b></div>
                        	<div class="red" bind="titleMessageError" style="display:none;">标题输入值必须25字内</div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" style="vertical-align:text-top;"><b class="f14 fb">评论正文：</b></td>
                        <td><textarea class="unity_w2 reply_txt" name="content" id="commentContent" cols="45" rows="7"></textarea></td>
                        <td>
                        	<div bind="commentMessage"><b class="f14 fb">（字数500字）</b><p class="grayfont"><br>˙鼓励你的原创评论<br>˙请只针对只购商品本身<br>˙不要针对交易配送等服务过程</p></div>
                       		<div class="red" bind="commentMessageError" style="display:none;">评论内容必须在500字内</div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><b class="f14 fb">验证码：</b></td>
                        <td><input name="sRand" type="text" size="10" id="commentVerifyCode" class="reply_txt">
                            <img data-lazy="false" id="verifyCodeImg" src="/verifyCode" class="verifyCodeImg"/> 看不清 <a class="link2" href="javascript:;" bind="changeVerifyCode">换一张</a>
                        	<b class="red" bind="verifyCodeError" style="display:none;">输入验证必须为4位数</b>
                        </td>      

                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input class="consult_but" name="" type="button" value="立即发表评论" bind="newComment"/></td>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                </form>
            </div>
        </div>
   
     </div>
    <div class="hei10"></div>
</div>
<%-- <script type="text/javascript">
function changeRandCode(){
	var img =document.getElementById("safeCode"); 
	img.src=img.src+"?d="+Math.random; 
}
</script> --%>
<script type="text/javascript" src="${serverPrefix}js/product/comment.js?${version}"></script>
<jsp:include page="../../snippets/version2/footer.jsp"></jsp:include>
</body>
</html>
