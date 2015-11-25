<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>卖家信息管理</title>
<link type="text/css" href="${contextPath}/css/seller/seller_manage.css"
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
				<div id="requestInfo" align="center"
					style="font-size: 15px; font-weight: bold;"></div>
				<!-- 查询条件部分 -->
				<form action="${contextPath }/seller/list" method="post"
					name="sellerQueryForm">
					<table id="conditionTable">
						<tr>
							<td>
								<div>
									<select name="nameType">
										<option value="0">卖家账号</option>
										<option value="1">店铺名称</option>
									</select> <input type="text" name="nameValue" />
								</div>
								<div>
									<label>店铺状态：</label> <select name="shopState">
										<option value="0">--请选择--</option>
										<c:forEach items="${shopStates }" var="state">
											<option value="${state.id }">${state.name }</option>
										</c:forEach>
									</select>
								</div></td>
							<td>
								<div>
									<label class="conditions">经营分类：</label> <input type="checkbox"
										name="businessScope" value="B">图书 <input
										type="checkbox" name="businessScope" value="V">音像 <input
										type="checkbox" name="businessScope" value="G">百货
								</div>
								<div>
									<select name="dateType" class="conditions">
										<option value="0">店铺开通日期</option>
										<option value="1">店铺激活日期</option>
										<option value="2">店铺截止日期</option>
									</select> <input name="dateBegin" bind="datepicker" readonly="readonly" />~
									<input name="dateEnd" bind="datepicker" readonly="readonly" />
								</div></td>
							<td><button type="submit" id="search">查询</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<hr>
			<br>
			<!-- 查询结果 -->
			<div id="searchResult">
				<input type="hidden" id="index" /> <input type="hidden"
					id="currentSellerId" />
				<c:if test="${not empty sellers }">
					<c:if test="${!empty pagination}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<table class="list-table">
						<colgroup>
							<col class="id" />
							<col class="id" />
							<col class="type" />
							<col class="name" />
							<col class="type" />
							<col class="name" />
							<col class="name" />
							<col class="name" />
							<col class="name" />
							<col class="name" />
							<col class="operate2" />
						</colgroup>
						<tr>
							<th>卖家编号</th>
							<th>卖家账号</th>
							<th>店铺状态</th>
							<th>店铺名称</th>
							<th>经营分类</th>
							<th>公司名称</th>
							<th>负责人</th>
							<th>开店日期</th>
							<th>激活日期</th>
							<th>截止日期</th>
							<th>操作</th>
						</tr>
						<c:forEach var="seller" items="${sellers}" varStatus="status">
							<c:if test="${status.index%2==1}">
								<c:set var="rowStatus" value="trs" />
							</c:if>
							<c:if test="${status.index%2==0}">
								<c:set var="rowStatus" value="trc" />
							</c:if>
							<tr class="${rowStatus}">
								<td>${seller.id}</td>
								<td><a
									href="${contextPath }/seller/${seller.shop.id}/_edit">${seller.name}</a>
								</td>
								<td id="stateTd${status.index }">${seller.shop.state.name}</td>
								<td>${seller.shop.shopName}</td>
								<td><c:if
										test="${fn:contains(seller.shop.businessScope,'B')}">图书</c:if>
									<c:if test="${fn:contains(seller.shop.businessScope,'V')}">&nbsp;音像</c:if>
									<c:if test="${fn:contains(seller.shop.businessScope,'G')}">&nbsp;百货</c:if>
								</td>
								<td>${seller.shop.companyName }</td>
								<td>${seller.realName}</td>
								<td><fmt:formatDate value="${seller.shop.createDate}"
										type="date" /></td>
								<td id="activedateTd${status.index }"><fmt:formatDate
										value="${seller.shop.activeDate}" type="date" /></td>
								<td><fmt:formatDate value="${seller.shop.endDate}"
										type="date" /></td>
								<td id="optTd${status.index }"><c:if
										test="${seller.shop.state.id == 36001||seller.shop.state.id==36003||seller.shop.state.id==36004}">
										<a class="operate-link" href="javascript:void(0);"
											onclick="update(${seller.id },0,${seller.shop.state.id},${status.index });">激活</a>
									</c:if> <c:if
										test="${seller.shop.state.id==36002||seller.shop.state.id==36003}">
										<a class="operate-link" href="javascript:void(0);"
											onclick="update(${seller.id },1,${seller.shop.state.id},${status.index });">冻结</a>
									</c:if> <c:if test="${!(seller.shop.state.id==36005)}">
										<a class="operate-link" href="javascript:void(0);"
											onclick="update(${seller.id },2,${seller.shop.state.id},${status.index });">注销</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>

						<tr>
							<th>卖家编号</th>
							<th>卖家账号</th>
							<th>店铺状态</th>
							<th>店铺名称</th>
							<th>经营分类</th>
							<th>公司名称</th>
							<th>负责人</th>
							<th>开店日期</th>
							<th>激活日期</th>
							<th>截止日期</th>
							<th>操作</th>
						</tr>
					</table>
					<c:if test="${!empty pagination}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</c:if>
			</div>
		</div>
	</div>
	<div id="freezenDialog" align="center">
		<select id="freezenType">
			<option selected="selected" value="0">搜索引擎屏蔽</option>
			<option value="1">完全冻结</option>
		</select>
	</div>
	<div id="verifyFreezen">是否确认冻结？</div>
	<div id="verifyLogout">是否确认注销？</div>
	<div id="verifyActive">是否确认激活？</div>
	<div id="success">成功</div>
	<div id="fail">失败</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/seller/seller_manage.js"></script>
</body>
</html>
