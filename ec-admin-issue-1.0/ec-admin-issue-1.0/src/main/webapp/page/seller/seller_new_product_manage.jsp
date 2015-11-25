<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>卖家新建商品管理</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/seller/seller_new_product_manage.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-seller.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
			    <!-- 信息弹出框 -->
				<div id="requestInfo" align="center"
					style="font-size: 15px; font-weight: bold;"></div>
				<!-- 图片弹出框 -->
				<div id="pictrueDialog" align="center"
					style="font-size: 15px; font-weight: bold;">
				</div>
				<!-- 卖家新建商品管理 -->
				<h4>卖家新建商品管理</h4>
				<div>
					<!-- 条件部分 -->
					<div>
						<form action="/seller/productManage" method="post" id="productManageQueryForm">
							<table class="query_condition">
							    <colgroup>
					               <col class="title" />
					               <col class="textarea" />
					               <col class="title" />
					               <col class="content" />
					               <col class="title" />
					               <col class="content" />
					               <col class="shorttitle" />
					               <col class="content" />
					            </colgroup>
								<tr>
									<td align="right"><select name="productCode">
											<option value="productSaleIds" 
											<c:if test="${pSForm.productCode=='productSaleIds'}">selected="selected"</c:if>
											>商品编号</option>
											<option value="outerIds" 
											<c:if test="${pSForm.productCode=='outerIds'}">selected="selected"</c:if>
											>商品自编码</option>
									</select></td>
									<td rowspan="2"><textarea name="code" class="query_list">${pSForm.code}</textarea>
									</td>
									<td align="right"><label>店铺：</label></td>
									<td><select name="shop">
									        <option >请选择</option>
									        <c:forEach var="shop" items="${shops}">
									        <option value="${shop.id }"
									        <c:if test="${pSForm.shop==shop.id }">selected="selected"</c:if>
									        >${shop.shopName }</option>
									        </c:forEach>
									</select></td>
									<td align="right"><label>经营分类：</label></td>
									<td><select name="classifiedManage">
											<option value="">请选择</option>
											<c:forEach var="category" items="${categorys.children }">
											<option value="${category.name}"
											<c:if test="${pSForm.classifiedManage==category.name }">selected="selected"</c:if>
											>${category.name }</option>
											</c:forEach>
									</select></td>
									<td></td>
									<td>
										<button type="button" onclick="query();">查询</button></td>
								</tr>
								<tr>
									<td></td>
									<td align="right"><label>审核状态：</label></td>
									<td><select name="auditStatus">
											<option >请选择</option>
											<c:forEach var="productAudit" items="${productAudits.children  }">
											<option value="${productAudit.id }"
											<c:if test="${pSForm.auditStatus==productAudit.id  }">selected="selected"</c:if>
											>${productAudit.name }</option>
											</c:forEach>
									</select></td>
									<td align="right"><label>商品上下架：</label></td>
									<td><select name="productUpDown">
											<option >请选择</option>
											<c:forEach var="produactState" items="${produactStatus.children}">
											<option value="${produactState.id }"
											<c:if test="${pSForm.productUpDown==produactState.id  }">selected="selected"</c:if>
											>${produactState.name}</option>
											</c:forEach>
									</select></td>
									<td align="right"><label>图片：</label></td>
									<td><select name="picture">
											<option >请选择</option>
											<option value="1" 
											<c:if test="${pSForm.picture=='true'}">selected="selected"</c:if>
											>有</option>
											<option value="0"
											<c:if test="${pSForm.picture=='false'}">selected="selected"</c:if>
											>没有</option>
									</select></td>
								</tr>
							</table>
						</form>
					</div>
					<br>
					<!-- 查询结果部分 -->
					<c:if test="${pagination!=null }">
					<div>
					<!-- 功能按钮及连接 -->
					<div >
					<button type="button" onclick="productManageAudit(0,1,${pagination.currentPage},${pagination.pageSize });">批量通过</button>
					<button type="button" onclick="productManageAudit(0,0,${pagination.currentPage},${pagination.pageSize });">批量退回</button>
					<a href="javascript:void(0);" onclick="productManageExcel()">导出搜索结果</a>
					</div>
					<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					<!-- 结果table表 -->
					<div class="table">
					<form action="" id="productManageForm">
					<table class="query_result">
					<colgroup>
					    <col class="checkbox" />
					    <col class="lettername" />
					    <col class="characters" />
					    <col class="shortcharacters" />
					    <col class="longnumber" />
					    <col class="longnumber" />
					    <col class="longcharacters" />
					    <col class="shortcharacters" />
					    <col class="characters" />
					    <col class="shortnumber" />
					    <col class="shortnumber" />
					    <col class="shortcharacters" />
					    <col class="shortnumber" />
					    <col class="longcharacters" />
					    <col class="date" />
					    <col class="characters" />
					    <col class="longnumber" />
					    <col class="date" />
					    <col class="date" />
					    <col class="operate" />
					</colgroup>
					<tr>
					<th><input type="checkbox" name="selectA"> </th>
					<th>店铺名称</th>
					<th>经营分类</th>
					<th>商品编码</th>
					<th>条形码(ISBN)</th>
					<th>商品名称</th>
					<th>审核状态</th>
					<th>上下架状态</th>
					<th>码洋</th>
					<th>实洋</th>
					<th>销售分类</th>
					<th>图片</th>
					<th>制造商(出版社)</th>
					<th>生产日期(出版日期)</th>
					<th>设计者(作者)</th>
					<th>商品数量</th>
					<th>创建日期</th>
					<th>最后修改日期</th>
					<th>操作</th>
					</tr>
					<c:forEach var="productSale" items="${productSales}">
					<tr>
					<td><input type="checkbox" name="ids" value="${productSale.id}"
					<c:if test="${productSale.auditStatus.id!=37001}">
					disabled="disabled"
					</c:if>
					> </td>
					<td>${productSale.shop.shopName}</td>
					<td>${productSale.shop.businessScope }</td>
					<td>${productSale.id }</td>
					<td>${productSale.product.barcode}</td>
					<td>${productSale.product.name}</td>
					<td>${productSale.auditStatus.name}</td>
					<td>${productSale.saleStatus.name}</td>
					<td>${productSale.product.listPrice}</td>
					<td>${productSale.salePrice}</td>
					<td>${productSale.product.category.name}</td>
					<td>
					<c:if test="${productSale.product.imageList!=null&&fn:length(productSale.product.imageList)>0}">
					<c:set value="" var="url"></c:set>
                    <c:forEach var="image" items="${productSale.product.imageList}">
                    <c:if test="${(image.url!=null||image.url!='')&&image.type!=4}">
                    <c:set value="${url}-${image.url}" var="url"></c:set>
                    </c:if>
                    </c:forEach>
					<a href="javascript:void(0);" onclick="pictrue('${url}');">查看</a>
                    </c:if>
                    <c:if test="${productSale.product.imageList==null||fn:length(productSale.product.imageList)==0}">
					X
                    </c:if>
                    </td>
					<td>${productSale.product.vendor}</td>
					<td>${productSale.product.productionDate}</td>
					<td>${productSale.product.author}</td>
					<td>${productSale.stockQuantity}</td>
					<td>
					<fmt:formatDate value="${productSale.product.createTime}" type="both" />
					</td>
					<td>
					<fmt:formatDate value="${productSale.product.updateTime}" type="both" />
					</td>
					<td>
					<c:if test="${productSale.auditStatus.id==37001}">
					<a href="javascript:void(0);" onclick="productManageAudit(${productSale.id},1,${pagination.currentPage},${pagination.pageSize });">通过</a>
					<a href="javascript:void(0);" onclick="productManageAudit(${productSale.id},0,${pagination.currentPage},${pagination.pageSize });">退回</a>
					</c:if>
					</td>
					</tr>
					</c:forEach>
					<c:if test="${pagination.pageCount!=0}">
					<tr>
					<th><input type="checkbox" name="selectB" > </th>
					<th>店铺名称</th>
					<th>经营分类</th>
					<th>商品编码</th>
					<th>条形码(ISBN)</th>
					<th>商品名称</th>
					<th>审核状态</th>
					<th>上下架状态</th>
					<th>码洋</th>
					<th>实洋</th>
					<th>销售分类</th>
					<th>图片</th>
					<th>制造商(出版社)</th>
					<th>生产日期(出版日期)</th>
					<th>设计者(作者)</th>
					<th>商品数量</th>
					<th>创建日期</th>
					<th>最后修改日期</th>
					<th>操作</th>
					</tr>
					</c:if>
					</table>
					</form>
					</div>
					<c:if test="${pagination.pageCount!=0}">
					<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					<!-- 功能按钮及连接 -->
					<div>
					<button type="button" onclick="productManageAudit(0,1,${pagination.currentPage},${pagination.pageSize });">批量通过</button>
					<button type="button" onclick="productManageAudit(0,0,${pagination.currentPage},${pagination.pageSize });">批量退回</button>
					<a href="javascript:void(0);" onclick="productManageExcel()">导出搜索结果</a>
					</div>
					</c:if>
					</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/seller/seller_new_product_manage.js"></script>
</body>
</html>
