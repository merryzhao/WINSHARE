<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="other_pro" fragment="56">
     <h3><span class="fr"><a class="previous" href="javascript:;" style="display:none;"></a><a class="next" href="javascript:;" style="display:none;"></a></span><b class="khaki fb">NEW</b><br/>即将开始的抢购</h3>
     <dl>
     
     <c:forEach items="${contentList}" var="content" varStatus="status">
	       <c:if test="${content.currentPromotion[1]>0}">
	         <dd class='${status.index==0?"dt":""}' bind="limit" end="${content.currentPromotion[3]}">
	             <h4><em>${status.index+1}</em><a   target="_blank"  href="${content.url}" title="<c:out value="${content.product.name }"/>">${content.product.name }</a></h4>
	             <p><b class="red fb">￥${content.effectivePrice }</b><del>￥${content.product.listPrice }</del><br/>距离开始：<b class="red" bind="hour">00</b>小时<b class="red" bind="min">00</b>分<b class="red" bind="sec">00</b>秒</p>
	         </dd>
	         </c:if>
       </c:forEach>
     </dl>
 </div>