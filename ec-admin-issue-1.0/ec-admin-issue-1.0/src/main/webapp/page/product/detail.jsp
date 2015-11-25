<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_product.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
div.showContent a:link {
	color: #FF5500;
}
div.ui-widget-content{
	width:1150px;
}
</style>
<title>商品详细信息</title>
</head>
<body>
	<!-- 引入JS -->
	<%@include file="../snippets/scripts.jsp"%>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner ui-widget-content" id="content">
				<div id="ui-widget-content">
				<p><input type="button" value="编辑商品信息" id="editButton"></p>
				<br/>
  					<div class="ui-widget-content">
						<h4>基础字段</h4>
						<table class="list-table" >
							<c:set var="isGoods"
								value="${productSale.product.sort.id == 11003}"></c:set>
							<tr>
								<c:choose>
									<c:when test="${productSale.product.sort.id == 11003}">
										<td>自编码：</td>
										<td>${productSale.outerId}</td>
									</c:when>
									<c:otherwise>
										<td>商品编码：</td>
										<td>${productSale.id}</td>
									</c:otherwise>
								</c:choose>
								<td><c:choose>
										<c:when test="${isGoods }">条形码：</c:when>
										<c:otherwise>ISBN号：</c:otherwise>
									</c:choose></td>
								<td>${productSale.product.barcode}</td>
								<td>商品名称：</td>
								<td>${productSale.sellName}</td>
							</tr>
							<tr>
								<td><c:choose>
										<c:when test="${isGoods }">生产日期：</c:when>
										<c:otherwise>出版日期：</c:otherwise>
									</c:choose></td>
								<td><fmt:formatDate
										value="${productSale.product.productionDate}" type="date" />
								</td>
								<td>印刷日期：</td>
								<td><fmt:formatDate
										value="${productSale.product.productionDate}" type="date" />
								</td>

								<td><c:choose>
										<c:when test="${isGoods }">市场价：</c:when>
										<c:otherwise>码洋：</c:otherwise>
									</c:choose></td>
								<td>${productSale.product.listPrice}</td>
							</tr>
							<tr>
								<td>类型：</td>
								<td>${productSale.product.sort.name}</td>
								<td>录入时间：</td>
								<td><fmt:formatDate
										value="${productSale.product.createTime}" type="both" />
								</td>
								<td>更新时间：</td>
								<td><fmt:formatDate
										value="${productSale.product.updateTime}" type="both" />
								</td>
							</tr>
							<tr>
								<td><c:choose>
										<c:when test="${isGoods }">生产商：</c:when>
										<c:otherwise>出版社：</c:otherwise>
									</c:choose></td>
								<td>${productSale.product.manufacturer}</td>
								<td>制造者：</td>
								<td>${productSale.product.author}</td>
								<td>网站分类：</td>
								<td>
									<c:forEach var="cate" items="${productSale.product.categories}">
										${cate.name} | 
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td>经营分类：</td>
								<td>${productSale.product.workCategory}</td>
								<td>管理分类：</td>
								<td>${productSale.product.manageCategory}</td>
								<td>MC分类：</td>
								<td>${productSale.product.mcCategory}</td>
							</tr>
							<tr>
								<td>常用供应商：</td>
								<td>${productSale.product.vendor}</td>
								<td>是否套装商品：</td>
								<td><c:if test="${productSale.product.complex==1}">是</c:if>
								    <c:if test="${productSale.product.complex==2}">是</c:if>
									<c:if test="${productSale.product.complex==0}">否</c:if></td>
								<td>是否虚拟商品(礼品卡)：</td>
								<td><c:if test="${productSale.product.virtual}">是</c:if> <c:if
										test="${!productSale.product.virtual}">否</c:if></td>
							</tr>
							<c:if test="${productSale.shop.id != 1 }">
							<tr>
								<td>供应类型：</td>
								<td>${productSale.supplyType.name }</td>
								<td>上下架状态：</td>
								<td>${productSale.saleStatus.name }</td>
								<td>百货库存量：</td>
								<td>${productSale.stockQuantity}</td>
							</tr>
							<tr>
								<td>百货占用量：</td>
								<td>${productSale.saleQuantity}</td>
								<td>百货可用量：</td>
								<td>${productSale.stockQuantity - productSale.saleQuantity}</td>
							</tr>
							</c:if>							
							<c:if test="${productSale.shop.id == 1}">
								<tr>
									<td>供应类型：</td>
									<td>${productSale.supplyType.name }</td>
									<td>上下架状态：</td>
									<td>${productSale.saleStatus.name }</td>
									<td>网站库存量：</td>
									<td>${productSale.stockQuantity}</td>
								</tr>
								<c:if test="${productSale.productSaleStockVos != null}">
								<tr>
									<td>占用信息：</td>
									<td>
									<c:forEach var="productSaleStock" items="${productSale.productSaleStockVos}">${productSaleStock.dcdetail.name}:${productSaleStock.sales}；&nbsp;</c:forEach>
									</td>
									<td>实物库存信息：</td>
									<td>
										<c:forEach var="productSaleStock" items="${productSale.productSaleStockVos}">${productSaleStock.dcdetail.name}:${productSaleStock.stock}；&nbsp;</c:forEach>
									</td>
									<td>虚拟库存信息：</td>
									<td>
										<c:forEach var="productSaleStock" items="${productSale.productSaleStockVos}">${productSaleStock.dcdetail.name}:${productSaleStock.virtual}；&nbsp;</c:forEach>
									</td>
								</tr>
								</c:if>
							</c:if>
							<c:if test="${productSale.product.hasImage}">
								<tr>
									<c:forEach items="${smallImages}" var="smallImage"
										varStatus="status">
										<td>商品小图：${status.count}</td>
										<td><img alt="小图" src="${smallImage.url}" >
										</td>
									</c:forEach>
									<c:forEach items="${mediumImages}" var="mediumImage"
										varStatus="status">
										<td>商品中图：${status.count}</td>
										<td><a href="javascript:;"
											onclick="openImage('${mediumImage.url}');">查看</a></td>
									</c:forEach>
									<c:forEach items="${largeImages}" var="largeImage"
										varStatus="status">
										<td>商品大图：${status.count}</td>
										<td><a href="javascript:;" onclick="openImage('${largeImage.url}');">查看</a>
										</td>
									</c:forEach>
								</tr>
								<tr>
									<c:forEach items="${illustrationImages}"
										var="illustrationImage" varStatus="status">
										<td>商品插图：${status.count}</td>
										<td><a href="javascript:;" onclick="openImage('${illustrationImage.url}');">查看</a></td>
									</c:forEach>
								</tr>
							</c:if>
						</table>
					</div>
					<div class="ui-widget-content">
						<h4>业务字段</h4>
						<table class="list-table">
							<tr>
								<td>卖家店铺：</td>
								<td>${productSale.shop.name }</td>
								<td>销售价格：</td>
								<td>${productSale.salePrice }</td>
								<td>基础价格：</td>
								<td>${productSale.basicPrice }</td>
							</tr>
							<tr>
								<td>销售名称：</td>
								<td>${productSale.sellName }</td>
								<td>促销语：</td>
								<td>${productSale.subheading }</td>
							</tr>
								<tr>
								<td>存储类型：</td>
								<td>${productSale.storageType.name }</td>
								<td>审核状态：</td>
								<td>${productSale.auditStatus.name }</td>
								<td>存储地点：</td>
								<td>${productSale.location.name }</td>
							</tr>
							<tr>
								<td>是否有促销：</td>
								<td><c:if test="${productSale.hasPromotion==true}">有</c:if><c:if test="${productSale.hasPromotion!=true }">无</c:if></td>
								<td>备用字段：</td>
								<td colspan="3">${productSale.promValue }</td>
							</tr>
							<c:if test="${productSale.hasPromotion }">
							<tr>
								<td>促销时间：</td>
								<td><fmt:formatDate value="${productSale.promotionStartTime}" type="date"/>~<fmt:formatDate value="${productSale.promotionEndTime}" type="date"/></td>
								<td>促销价:</td>
								<td>${productSale.promotionPrice}</td>
							</tr>
							</c:if>
						</table>
					</div>
					<div class="ui-widget-content">
						<h4>扩展字段</h4>
						<table class="list-table">
							<c:forEach items="${productSale.product.extendList }"
								var="proExtend" varStatus="status">
								<c:if test="${status.index%4==0 }">
									<tr>
								</c:if>
								<td>${proExtend.name }：</td>
								<td>${proExtend.value }</td>
								<c:if test="${status.index%4==0 && status.index>3}">
									</tr>
								</c:if>
							</c:forEach>
						</table>
					</div>
					<div class="ui-widget-content">
						<c:forEach items="${productSale.product.descriptionList }"
							var="proDecription" varStatus="status">
							<div class="showContent">
								${proDecription.name }：
								<c:if test="${fn:length(proDecription.content)>45}">
									<c:out
										value="${fn:substring(proDecription.content,0,45)}......" />
								</c:if>
								<a class="show" href="javascript:void(0);"
									onclick="showDetail('description${status.index }');"
									name="show${status.index }">显示全文</a>
							</div>
							<p class="book_detail" id="description${status.index }">${proDecription.content}</p>
						</c:forEach>
					</div>
				</div>
				<div>
				<h4>商品修改日志</h4>
					<table class="list-table">
						<tr>
							<th>修改人</th>
							<th>修改时间</th>
							<th>原销售价格</th>
							<th>修改后的销售价格</th>
							<th>原状态</th>
							<th>修改后的状态</th>
							<th>备注</th>
						</tr>
						<c:forEach items="${log}" var="pl" varStatus="index">
							<tr>
								<td>${pl.operator.name}</td>
								<td><fmt:formatDate value="${pl.updateTime}" type="both" />
								</td>
								<td>${pl.originalPrice}</td>
								<td>${pl.newPrice}</td>
								<td>${pl.originalStatus.name}</td>
								<td>${pl.newStatus.name}</td>
								<td>${pl.remark}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="pid" value="${productSale.id}">
	<div id="dialog"></div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript">
	$().ready(function(){
		$("#editButton").bind("click",function(){
			window.location.href="/product/"+$("#pid").val()+"/edit";
		})
	})
		$(document).ready(function() {
			$("#dialog").dialog({
				autoOpen : false,
				bgiframe : false,
				modal : true
			});
			$("p[class='book_detail']").hide();
		});
		function openImage(url) {
			var img = "<center><img alt='图片' src="+url+" align='middle' /></center>"
			$("#dialog").html(img).dialog("open");
		}
		function showDetail(p) {
			$("#" + p).toggle("normal");
		}
	</script>
</body>
	   	
</html>