<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<div class="daily_pre" fragment="340">
	<h3>今日秒杀</h3>
	<div class="discount_goods">
			<ul id="daily_maddness">
			<c:forEach items="${contentList}" var="content" varStatus="status">
				<li>
				<div class="limit_buy">
				  <div class="countdown" bind="limit"
						end="${content.currentPromotion[1]}">
						<b class="fb">剩余：</b><span class="fb" bind="hour">00</span><span
							class="fb" bind="min">00</span><span class="fb" bind="sec">00</span>
					</div>
				</div>	
					<p class="txt_center">		
						<a   target="_blank"  href="${content.url }" title="${content.product.name }"><img data-lazy="false" src="${content.imageUrl }" alt="${content.product.name }">
						</a>
					</p> 
					<strong>
						<a target="_blank"  href="${content.url }" class="link1" title="${content.product.name }">
						<winxuan-string:substr length="12" content="${content.product.name }"></winxuan-string:substr>
						</a>
				   </strong>
					<p>原价：<del>￥${content.listPrice}</del>
						<br/> 秒杀价：<b class="red fb">￥${content.effectivePrice}</b> (<fmt:formatNumber value="${content.discount*100}" pattern="##" minFractionDigits="0"></fmt:formatNumber>折)
					</p>
				</li>
					</c:forEach>
			</ul>
		</div>

	<div class="shop_pages">
		<a class="pre_page"    href="javascript:;" id="daily_pre">上一页</a>
		<a class="pre_page2" id="daily_pre2">上一页</a> <span id="page_daily">第1页
			共4页</span> <a class="next_page"     href="javascript:;" id="daily_next">下一页</a>
			<a class="next_page2" id="daily_next2">下一页</a>
	</div>
</div>
<c:import url="/fragment/342"></c:import>

<script type="text/javascript" charset="utf-8">
    seajs.use(['jQuery','limit'],function($,Limit){
    	var context=$("div.limit_buy");
    	Limit({context:context});
    });
</script>
