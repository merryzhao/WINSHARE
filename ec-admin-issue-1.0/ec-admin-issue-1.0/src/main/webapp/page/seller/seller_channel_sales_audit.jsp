<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>卖家渠道销售审批</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/seller/seller_channel_sales_audit.css"
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
				<!-- 卖家渠道销售审批 -->
				<h4>卖家渠道销售审批</h4>
				<div>
					<!-- 条件部分 -->
					<div>
						<form action="/seller/salesAudit" method="post"
							id="ChannelSalesQueryForm">
							<table class="query_condition">
								<colgroup>
									<col class="title" />
									<col class="textarea" />
									<col class="title" />
									<col class="content" />
									<col class="title" />
									<col class="shortcontent" />
									<col class="button" />
								</colgroup>
								<tr>
									<td align="right"><select name="productCode">
											<option value="productSaleIds"
												<c:if test="${pSForm.productCode=='productSaleIds'}">selected="selected"</c:if>>商品编号</option>
											<option value="outerIds"
												<c:if test="${pSForm.productCode=='outerIds'}">selected="selected"</c:if>>商品自编码</option>
									</select>
									</td>
									<td rowspan="2"><textarea name="code" class="query_list">${pSForm.code}</textarea>
									</td>
									<td align="right"><label>店铺：</label>
									</td>
									<td><select name="shop">
											<option>请选择</option>
											<c:forEach var="shop" items="${shops}">
												<option value="${shop.id }"
													<c:if test="${pSForm.shop==shop.id }">selected="selected"</c:if>>${shop.shopName
													}</option>
											</c:forEach>
									</select>
									</td>
									<td align="right"><label>申请类型：</label>
									</td>
									<td><select name="applyType">
											<option>请选择</option>
											<c:forEach var="type" items="${types.children}">
												<option value="${type.id }"
													<c:if test="${pSForm.applyType==type.id }">selected="selected"</c:if>>${type.name
													}</option>
											</c:forEach>
									</select>
									</td>
									<td colspan="2"><button type="button" onclick="query();">查询</button>
									</td>
								</tr>
								<tr>
									<td></td>
									<td align="right"><label>申请时间：</label>
									</td>
									<td><input name="applyStartTime" class="date" type="text"
										bind="datepicker" value="${pSForm.applyStartTime}">~ <input
										name="applyEndTime" class="date" type="text" bind="datepicker"
										value="${pSForm.applyEndTime}"></td>
									<td align="right"><label>状态：</label>
									</td>
									<td><select name="status">
											<option>请选择</option>
											<c:forEach var="state" items="${states.children  }">
												<option value="${state.id }"
													<c:if test="${pSForm.status==state.id  }">selected="selected"</c:if>>${state.name
													}</option>
											</c:forEach>
									</select>
									</td>
									<td></td>
								</tr>
							</table>
						</form>
					</div>
					<br>
					<!-- 查询结果部分 -->
					<c:if test="${pagination!=null }">
						<div>
							<!-- 功能按钮及连接 -->
							<div>
								<button type="button"
									onclick="channelSaleAudit(0,1,${pagination.currentPage},${pagination.pageSize});">批量通过</button>
								<button type="button"
									onclick="channelSaleAudit(0,0,${pagination.currentPage},${pagination.pageSize});">批量退回</button>
								<a href="javascript:void(0);" onclick="channelSalesExcel();">导出搜索结果</a>
							</div>
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							<!-- 结果table表 -->
							<div class="table">
								<form action="" id="ChannelSaleForm">
									<table class="query_result">
										<colgroup>
											<col class="checkbox" />
											<col class="shortcharacters" />
											<col class="longcharacters" />
											<col class="shortcharacters" />
											<col class="longnumber" />
											<col class="longcharacters2" />
											<col class="shortcharacters" />
											<col class="shortcharacters" />
											<col class="shortnumber" />
											<col class="shortnumber" />
											<col class="characters" />
											<col class="shortcharacters" />
											<col class="longdate" />
											<col class="longdate" />
											<col class="operate" />
										</colgroup>
										<tr>
											<th><input type="checkbox" name="selectA"></th>
											<th>申请类型</th>
											<th>卖家名</th>
											<th>经营分类</th>
											<th>商品编号</th>
											<th>商品名称</th>
											<th>渠道</th>
											<th>储配方式</th>
											<th>码洋</th>
											<th>实洋</th>
											<th>信息是否完整</th>
											<th>状态</th>
											<th>创建时间</th>
											<th>审核时间</th>
											<th>操作</th>
										</tr>
										<c:forEach var="pCA" items="${pCAs}">
											<tr>
												<td><input type="checkbox" name="ids"
													value="${pCA.productSale.id }"
													<c:if test="${pCA.state.id!=37001}">disabled="disabled"</c:if>>
												</td>
												<td>${pCA.type.name }</td>
												<td>${pCA.productSale.seller.name }</td>
												<td>${pCA.productSale.seller.shop.businessScope}</td>
												<td>${pCA.productSale.id }</td>
												<td>${pCA.productSale.product.name}</td>
												<td>${pCA.channel.name }</td>
												<td>${pCA.productSale.storageType.name}</td>
												<td>${pCA.productSale.product.listPrice }</td>
												<td>${pCA.productSale.salePrice}</td>
												<td>是</td>
												<td>${pCA.state.name}</td>
												<td><fmt:formatDate value="${pCA.createDate}"
														type="both" /></td>
												<td><fmt:formatDate value="${pCA.auditDate}"
														type="both" /></td>
												<td><c:if test="${pCA.state.id==37001}">
														<a href="javascript:void(0);"
															onclick="channelSaleAudit(${pCA.productSale.id },1,${pagination.currentPage},${pagination.pageSize});">通过</a>
														<a href="javascript:void(0);"
															onclick="channelSaleAudit(${pCA.productSale.id },0,${pagination.currentPage},${pagination.pageSize});">退回</a>
													</c:if></td>
											</tr>
										</c:forEach>
										<c:if test="${pagination.pageCount!=0}">
											<tr>
												<th><input type="checkbox" name="selectB"></th>
												<th>申请类型</th>
												<th>卖家名</th>
												<th>经营分类</th>
												<th>商品编号</th>
												<th>商品名称</th>
												<th>渠道</th>
												<th>储配方式</th>
												<th>码洋</th>
												<th>实洋</th>
												<th>信息是否完整</th>
												<th>状态</th>
												<th>创建时间</th>
												<th>审核时间</th>
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
									<button type="button"
										onclick="channelSaleAudit(0,1,${pagination.currentPage},${pagination.pageSize});">批量通过</button>
									<button type="button"
										onclick="channelSaleAudit(0,0,${pagination.currentPage},${pagination.pageSize});">批量退回</button>
									<a href="javascript:void(0);" onclick="channelSalesExcel();">导出搜索结果</a>
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
		src="${pageContext.request.contextPath}/js/seller/seller_channel_sales_audit.js"></script>
</body>
</html>
