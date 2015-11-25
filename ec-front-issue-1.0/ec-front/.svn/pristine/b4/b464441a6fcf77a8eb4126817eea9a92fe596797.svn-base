<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="winxuan" uri="http://www.winxuan.com/tag/page" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-我的收藏</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="my_acc_order" />
</jsp:include>
</head>

<body>
<%@include file="../../snippets/version2/header.jsp" %>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/" class="link3">文轩网</a> &gt;   我的帐户</span></div>
<jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="2_2" />
</jsp:include>
    <div class="right_box">
        <h3 class="myfav_tit margin10">我的收藏</h3>
        <p class="mylabel">
        	<a class="fr link3" href="javascript:;" id="edit_tag">标签管理</a>
        	我的标签：
        	<c:forEach var="tag" items="${tags}" varStatus="status">
        		
        			<c:choose>
			        	<c:when test="${status.index == 0 && empty param.tag}">
			        		<a class="all" href="javascript:;" >${tag.key.tagName}(${tag.value})</a> 
			        	</c:when>
			        	<c:when test="${param.tag == tag.key.id}">
			        		<a class="all" href="javascript:;" >${tag.key.tagName}(${tag.value})</a> 
			        	</c:when>
			        	<c:otherwise>
			        		<a class="link1" 
			        		href="favorite?tag=${tag.key.id}&sort=${param.sort}&order=${param.order}" >${tag.key.tagName}(${tag.value})</a>&nbsp;
			        	</c:otherwise>
			        </c:choose>
        			
        	</c:forEach>
        <div class="locate">
        <select id="select" name="select" class="sequence" bind="fav_order">
          <option value="0" <c:if test="${empty param.order || param.order == 0}">selected="selected"</c:if>>按收藏时间从新到旧</option>
          <option value="1" <c:if test="${param.order == 1}">selected="selected"</c:if>>按商品价格从低到高</option>
          <option value="2" <c:if test="${param.order == 2}">selected="selected"</c:if>>按商品折扣从低到高</option>
        </select>
        <ul class="infor_tab">
	        
		        <c:choose>
		        	<c:when test="${empty param.sort}">
		        		<li class="current_info">全部商品</li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="favorite?tag=${param.tag}&order=${param.order}">全部商品</a></li>
		        	</c:otherwise>
		        </c:choose>
		   
		        <c:choose>
		        	<c:when test="${param.sort==11001}">
		        		 <li class="current_info">图书</li>
		        	</c:when>
		        	<c:otherwise>
		        		 <li><a href="favorite?sort=11001&tag=${param.tag}&order=${param.order}">图书</a></li>
		        	</c:otherwise>
		        </c:choose>
		    
		        <c:choose>
		        	<c:when test="${param.sort==11002}">
		        		<li class="current_info">音像</li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="favorite?sort=11002&tag=${param.tag}&order=${param.order}">音像</a></li>
		        	</c:otherwise>
		        </c:choose>
		    
		    
		        <c:choose>
		        	<c:when test="${param.sort==11003}">
		        		<li class="current_info">百货 </li>
		        	</c:when>
		        	<c:otherwise>
		        		<li><a href="favorite?sort=11003&tag=${param.tag}&order=${param.order}">百货</a> </li>
		        	</c:otherwise>
		        </c:choose>
        </ul>
        </div>
        <p class="txt_right margin10"><a class="batch_but" href="javascript:;" bind="batchAddToCart" data-items="table input[name='checkbox']:checked">批量购买</a> 
        <a class="batch_but" href="javascript:;" bind="fav_batch_delete">批量删除</a></p>
        <div class="hei10"></div>
        <table width="100%" class="favorite_goods" cellspacing="0" cellpadding="0">
            <thead>
                <tr>
                    <th width="5%">&nbsp;</th>
                    <th width="12%">商品信息</th>
                    <th width="53%">&nbsp;</th>
                    <th width="17%">文轩价</th>
                    <th width="13%">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${favoriteList}" var="favorite" varStatus="status">
                	<tr productSaleId="${favorite.productSale.id }">
	                    <td <c:if test="${status.first}">class="nodash"</c:if>><input data-id="${favorite.productSale.id}" type="checkbox" name="checkbox" id="checkbox"></td>
	                    <td <c:if test="${status.first}">class="nodash"</c:if>>
	                    	<a target="_blank" 
								href="${favorite.productSale.url}" class="favoriteList"><img
									src="${favorite.productSale.product.imageUrlFor110px}"
									alt="${favorite.productSale.sellName}" 
									class="fl" />
							</a>
							
						</td>
	                    <td <c:if test="${status.first}">class="nodash"</c:if>>
	                    	<p class="favgoods_info">
	                    		<a  target="_blank" class="link1" href="${favorite.productSale.url}" title="${favorite.productSale.sellName}">${favorite.productSale.sellName}</a>
	                    		<c:choose>
	                    			<c:when test="${favorite.purchased }">
	                    				<b class="red">已购买</b>
	                    			</c:when>
	                    			<c:when test="${favorite.productSale.avalibleQuantity < 5 && favorite.productSale.canSale}">
	                    				<b class="red">余量有限</b>
	                    			</c:when>
	                    		</c:choose>
	                    	</p>
	                    	<p class="favgoods_info">收藏时间：<fmt:formatDate value="${favorite.addTime }" pattern="yyyy-MM-dd"/><br>
	                    		<span name="favgoods_info_tag">
						                    标签：<span name="favgoods_info_tag_values">
						                    <c:forEach items="${favorite.tagList}" var="tag">${tag.tagName} </c:forEach></span>
						                    [<a class="link1" href="javascript:;" name="favoriteAddTag"><c:choose><c:when test="${empty favorite.tagList}">添加</c:when><c:otherwise>编辑</c:otherwise></c:choose></a>]
						        </span>
	                    	</p>
	                    </td>
	                    
	                    <td <c:if test="${status.first}">class="nodash"</c:if>>
	                    	<c:choose>
	                    		<c:when test="${!favorite.productSale.canSale}">
	                    			<b class="fb">￥${favorite.productSale.effectivePrice}</b>
	                    		</c:when>
	                    		<c:when test="${favorite.addPrice > favorite.productSale.effectivePrice}">
	                    			<p class="prices"><del>￥${favorite.addPrice }</del><br>
				                            <b class="fb red">￥${favorite.productSale.effectivePrice}</b> (${favorite.productSale.integerDiscount}折)</p>
				                    <p class="price_compare">比放入时降￥${favorite.addPrice - favorite.productSale.effectivePrice }</p>
	                    		</c:when>
	                    		<c:otherwise>
	                    			<b class="fb red f14">￥${favorite.productSale.effectivePrice}</b><b class="l_gray">(${favorite.productSale.integerDiscount }折)</b>
	                    		</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    
	                    <td <c:if test="${status.first}">class="nodash"</c:if>>
	                    	<c:if test="${favorite.productSale.storageType.id==6004}"><a href="${favorite.productSale.url}" class="buy_ebook" target="_blank">购买电子书</a></c:if>
	                    	<c:if test="${favorite.productSale.storageType.id!=6004}">
		                    	<c:choose>	
		                    		<c:when test="${favorite.productSale.canSale}">
		                    			<c:choose>
		                    				<c:when test="${favorite.productSale.preSaleCanBuy}">
		                    					<a class="subs_but" href="javascript:;" bind="presell" data-id="${favorite.productSale.id}" 
		                    					data-date="<fmt:formatDate value="${favorite.productSale.booking.endDate }" pattern="yyyy年MM月dd日"/>" data-region="成都">现在预定</a>
		                    				</c:when>
		                    				<c:otherwise>
		                    					<a class="buy_but" href="javascript:;" bind="addToCart" data-id="${favorite.productSale.id}" >购买</a>
		                    				</c:otherwise>
		                    			</c:choose>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<a class="arr_notice" href="javascript:;" bind="arrivalNotify" data-id="${favorite.productSale.id}">到货通知</a>
		                    		</c:otherwise>
		                    	</c:choose>
	                    	</c:if>
	                    	 <a class="link4" href="javascript:;" bind="remove" productSaleId="${favorite.productSale.id}">删除</a>
	                   	</td>
	                </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${empty favoriteList }"><div align="center"><br/><br/><br/>暂无商品<br/><br/><br/></div></c:if>
        <winxuan:page pagination="${pagination}" bodyStyle="front-user"/>
    </div>
    <div class="hei10"></div>
</div>
<%@include file="../../snippets/version2/footer.jsp" %>
	<div class="tab_box" style="display:none" id="edit_tag_el">
		<h3>
			<a href="javascript:;" class="close" id="edit_tag_colse">close</a>标签管理
		</h3>
		<div class="sorts_box">
			<p class="new_sort">
				新增标签类别：<input type="text" > <input type="button"
					value="提交" class="addnew_but" name="add_new_but_submit">
			</p>
			<div style="max-height:200px;overflow:auto">
				<table cellspacing="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>名称</th>
						<th>数量</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${tags}" var="tag" begin="2">
						<tr tagId="${tag.key.id}">
							<td><p class="txt_left" id="tag_name">${tag.key.tagName}</p>
							</td>
							<td id="count">${tag.value }</td>
							<td><a href="javascript:;" class="gray" id="edit_tag_edit">编辑</a> | <a href="javascript:;"
								class="gray" bind="removeTag">删除</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
<script src="${serverPrefix}js/customer_fav.js"></script>
</body>
</html>
