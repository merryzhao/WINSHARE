<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/snippets/tags.jsp" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-降价通知</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="my_acc_order" />
</jsp:include>
</head>

<body>
<%@include file="../../snippets/version2/header.jsp" %>

<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="2_4" />
</jsp:include>
    <div class="right_box">
        <h3 class="myfav_tit margin10">降价通知</h3>
			<p class="mylabel">
				<b class="black">按登记时间：</b>
				
				<c:forEach var="count" items="${countDate}">
					<c:choose>
						<c:when test="${count.key == 0}">
							<c:set var="name">全部</c:set>
						</c:when>
						<c:when test="${count.key == 1}">
							<c:set var="name">近一个月</c:set>
						</c:when>
						<c:when test="${count.key == 2}">
							<c:set var="name">近三个月</c:set>
						</c:when>
						<c:when test="${count.key == 3}">
							<c:set var="name">近半年</c:set>
						</c:when>
						<c:when test="${count.key == 4}">
							<c:set var="name">近一年</c:set>
						</c:when>
						<c:when test="${count.key == 5}">
							<c:set var="name">一年以前</c:set>
						</c:when>
					</c:choose>
					<c:choose>
						<c:when test="${count.key == 0 && empty param.addTime}">
							<c:set var="class">link4</c:set>
						</c:when>
						<c:when test="${count.key == param.addTime}">
							<c:set var="class">link4</c:set>
						</c:when>
						<c:otherwise>
							<c:set var="class">link1</c:set>
						</c:otherwise>
					</c:choose>
					<a class="${class}" href="/customer/notify/priceReduce?addTime=${count.key}&hasStock=${param.hasStock}&sort=${param.sort}&order=${param.order}">${name}(${count.value})</a>&nbsp;
				</c:forEach>
			</p>
			<div class="locate">
		<select id="select" name="select" class="sequence" bind="notifyOrder">
		  <option value="0" <c:if test="${empty param.order || param.order == 0}">selected="selected"</c:if>>按登记时间排序</option>
          <option value="1" <c:if test="${param.order == 1}">selected="selected"</c:if>>按折扣幅度排序</option>
          <option value="2" <c:if test="${param.order == 2}">selected="selected"</c:if>>按降价幅度排序</option>
          <option value="3" <c:if test="${param.order == 3}">selected="selected"</c:if>>按商品价格排序</option>
          <option value="4" <c:if test="${param.order == 4}">selected="selected"</c:if>>按商品折扣排序</option>
        </select>
        <ul class="infor_tab">
            <c:choose>
		        	<c:when test="${param.reduced}">
		        		<li class="current_info">已降价商品</li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="/customer/notify/priceReduce?reduced=true&addTime=${param.addTime}&order=${param.order}">已降价商品</a></li>
		        	</c:otherwise>
		        </c:choose>
		        
		        <c:choose>
		        	<c:when test="${(empty param.sort || param.sort == -1) && empty param.reduced}">
		        		<li class="current_info">全部商品</li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="/customer/notify/priceReduce?sort=-1&addTime=${param.addTime}&order=${param.order}">全部商品</a></li>
		        	</c:otherwise>
		        </c:choose>
		        
		        <c:choose>
		        	<c:when test="${param.sort==11001}">
		        		 <li class="current_info">图书</li>
		        	</c:when>
		        	<c:otherwise>
		        		 <li><a href="/customer/notify/priceReduce?sort=11001&addTime=${param.addTime}&order=${param.order}">图书</a></li>
		        	</c:otherwise>
		        </c:choose>
		    
		        <c:choose>
		        	<c:when test="${param.sort==11002}">
		        		<li class="current_info">音像</li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="/customer/notify/priceReduce?sort=11002&addTime=${param.addTime}&order=${param.order}">音像</a></li>
		        	</c:otherwise>
		        </c:choose>
		        
		        <c:choose>
		        	<c:when test="${param.sort==11003}">
		        		<li class="current_info">百货 </li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="/customer/notify/priceReduce?sort=11003&addTime=${param.addTime}&order=${param.order}">百货</a> </li>
		        	</c:otherwise>
		        </c:choose>
        </ul>
        </div>
        <p class="txt_right margin10"><a class="batch_but" href="javascript:;" bind="batchAddToCart" data-items="table input[name='checkbox']:checked">批量购买</a> <a class="batch_but" href="javascript:;" bind="removeBatch" name="priceReduce">批量删除</a></p>
        <div class="hei10"></div>
        <table width="100%" class="favorite_goods purchased" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th width="5%">&nbsp;</th>

                    <th width="12%">商品信息</th>
                    <th width="45%">&nbsp;</th>
                    <th width="22%">文轩价</th>
                    <th width="16%">操作</th>
                </tr>
            </thead>
            <tbody>
            	<c:forEach var="priceReduce" items="${priceReduceList}" varStatus="status">
            		<tr productSaleId="${priceReduce.productSale.id}">
	                    <td <c:if test="${status.first }">class="nodash"</c:if>><input data-id="${priceReduce.productSale.id}" type="checkbox" name="checkbox" id="checkbox"></td>
	                    <td <c:if test="${status.first }">class="nodash"</c:if>><a href="${priceReduce.productSale.product.url}" target="_blank" class="reduceList"><img src="${priceReduce.productSale.product.imageUrlFor110px }" alt="${priceReduce.productSale.sellName }" class="fl"></a></td>
						<td <c:if test="${status.first }">class="nodash"</c:if>><p class="favgoods_info">
								<a target="_blank" class="link1"
									href="${priceReduce.productSale.product.url }"
									title="${priceReduce.productSale.sellName}">${priceReduce.productSale.sellName}</a>
								<c:if test="${priceReduce.productSale.avalibleQuantity < 5 && priceReduce.productSale.canSale}">
                    				<b class="red">余量有限</b>
                    			</c:if>
							</p>
							<p class="favgoods_info margin10">
								登记时间：
								<fmt:formatDate value="${priceReduce.addTime }"
									pattern="yyyy-MM-dd" />
							</p>
						</td>
							<td <c:if test="${status.first }">class="nodash"</c:if>>
								<c:choose>	
		                    		<c:when test="${!priceReduce.productSale.canSale}">
		                    			<b class="fb l_gray f14">￥${priceReduce.productSale.product.listPrice}</b>
		                    			</c:when>
		                    		<c:when test="${priceReduce.productSale.effectivePrice < priceReduce.addSellPrice}">
		                    			<p class="prices">
											<del>￥${priceReduce.addSellPrice }</del>
											<br> <b class="fb red">￥${priceReduce.productSale.effectivePrice}</b> (${priceReduce.productSale.integerDiscount }折)
										</p>
										<p class="price_compare">比放入时降￥${priceReduce.addSellPrice -priceReduce.productSale.effectivePrice}</p>
		                    		</c:when>
									<c:otherwise>
		                    			<b class="fb red f14">￥${priceReduce.productSale.effectivePrice}</b><b class="l_gray">(${priceReduce.productSale.integerDiscount }折)</b>
		                    		</c:otherwise>
		                    	</c:choose>
							</td>
	                    <td <c:if test="${status.first }">class="nodash"</c:if>>
	                    	<c:choose>	
	                    		<c:when test="${priceReduce.productSale.canSale}">
	                    			<c:choose>
	                    				<c:when test="${priceReduce.productSale.preSaleCanBuy}">
	                    					<a class="subs_but" href="javascript:;" bind="presell" data-id="${priceReduce.productSale.id}" 
	                    					data-date="<fmt:formatDate value="${priceReduce.productSale.booking.endDate }" pattern="yyyy年MM月dd日"/>" data-region="成都">现在预定</a>
	                    				</c:when>
	                    				<c:otherwise>
	                    					<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${priceReduce.productSale.id}" >购买</a>
	                    				</c:otherwise>
	                    			</c:choose>
	                    		</c:when>
	                    		<c:otherwise>
	                    			<a class="arr_notice" href="javascript:;" bind="arrivalNotify" data-id="${priceReduce.productSale.id}">到货通知</a>
	                    		</c:otherwise>
	                    	</c:choose>
	                    	<br/>
	                    	<a class="link2" href="javascript:;" bind="remove">删除</a>
	                    </td>
                	</tr>
            	</c:forEach>
            </tbody>
        </table>
        <c:if test="${empty priceReduceList }"><div align="center"><br/><br/><br/>暂无商品<br/><br/><br/></div></c:if>
        <winxuan-page:page pagination="${pagination}" bodyStyle="front-user"/>

    </div>
    <div class="hei10"></div>
</div>
<%@include file="../../snippets/version2/footer.jsp" %>
<script type="text/javascript" src="${serverPrefix}js/customer_notify.js"></script>
</body>
</html>
