<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<html>
<head>
<title>退货包件信息查询</title>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
div.entering_table {
	background: #ecf5ff;
	margin-top: 25px;
	margin-bottom: 25px;
	border: 1px solid #6293bb;
}

table.entering_table {
	width: 90%;
	margin-left: 5%;
	margin-right: 5%;
	margin-top: 10px;
	margin-bottom: 10px;
}

table.entering_table td.property {
	text-align: right;
}

table.entering_table .title {
	width: 5%;
}

table.entering_table .property {
	width: 10%;
}

table.entering_table .input {
	width: 10%;
}

table.entering_table .longinput {
	width: 20%;
}

label.red {
	color: red;
}

input.long {
	width: 98%;
}

label.error {
	border: none;
	padding: 0.1em;
	color: red;
	font-weight: normal;
	background: none;
	padding-left: 16px;
}

#create input.error {
	border: 2px solid red;
}

td label.error {
	display: none !important;
}

#create select {
	border: 1px solid gray;
	padding: 0px;
}

#create select.error {
	border: 1px solid red;
	padding: 0px;
	margin: 0px;
}

#create input {
	border: 1px solid gray;
	padding: 2px;
}

#create input.error {
	border: 1px solid red;
	padding: 2px;
}
</style>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<h4>退货包件信息查询</h4>
				<div>
					<form id="queryPackageForm" action="/returnorder/packagequery" method="post">
						<div  class="entering_table">
							<table class="entering_table">
								<tr>
									<td colspan="6">查询条件</td>
								</tr>
								<tr>
									<td colspan="6"><HR width="100%" SIZE=1>
									</td>
								</tr>
								<!-- 表单布局 -->
								<colgroup>
									<col class="property" />
									<col class="input" />
									<col class="property" />
									<col class="input" />
									<col class="property" />
									<col class="input" />
								</colgroup>
								<!--数据输入 -->
								<tr>
									<td align="right">包件运单号：</td>
									<td>
										<input type="text" id="expressid" name="expressid" value="${expressid_param }" />
									</td>
									<td align="right">关联订单号：</td>
									<td>
										<input type="text" id="orderid" name="orderid" value="${orderid_param }" />
									</td>
									<td align="right">包件状态：</td>
									<td>
										<select name="status" id="status">
											<option value="">全部</option>
											<c:forEach var="status" items="${statusList }">
												<option value="${status.id }" ${status_param == status.id?'selected':''}>${status.name }</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td align="right">发件人：</td>
									<td>
										<input type="text" id="customer" name="customer" value="${customer_param }" />
									</td>
									<td align="right">发件人电话：</td>
									<td>
										<input type="text" id="phone" name="phone" value="${phone_param }" />
									</td>
									<td align="right">渠道退货单号：</td>
									<td>
										<input type="text" id="returnid" name="returnid" value="${returnid_param }" />
									</td>
								</tr>
								<tr>
									<td align="right">
										包件录入时间: 
									</td>
									<td colspan="5">
										<input type="radio" name="Date1" value="1" onclick="clickRadio(this,1)" class="day" /> 最近一天 
										<input type="radio" name="Date1" value="2" onclick="clickRadio(this,2)" class="week" /> 最近一周 
										<input type="radio" name="Date1" value="3" onclick="clickRadio(this,3)" class="month" /> 最近一月
										<input type="radio" name="Date1" value="4" onclick="clickRadio(this,4)" class="month" /> 最近三月
										<input class="starttime" name="starttime" bind="datepicker" style="width:100px">
										~ <input class="endtime" name="endtime" bind="datepicker" style="width:100px">
										<button type="submit" id="query">查询</button>
									</td>
								</tr>
							</table>
						</div>
					</form>
					<!-- 包件信息展示 -->
					<button type="button" onclick="checkCheckBox('suspend')">批量挂起</button>
					&nbsp;&nbsp;
					<span id="span-smg" style="color: red"></span>
					<table class="list-table" style="width: 100%">
						<tr>
							<th><input type="checkbox" name="selectAll" /></th>
							<th>包件运单号</th>
							<th>退货类型</th>
							<th>承运商</th>
							<th>发件人</th>
							<th>发件人电话</th>
							<th>录入时间</th>
							<th>关联订单</th>
							<th>状态</th>
							<th>操作</th>
							<th>备注</th>
							<th>日志</th>
						</tr>
						<c:if test="${!empty packagelist }">
							<c:forEach var="returnPackage" items="${packagelist }">
								<tr>
									<td><input type="checkbox" name="checkbox" title="${returnPackage.id }" value="${returnPackage.status.id }"/></td>
									<td><a href="/returnorder/packagequery/${returnPackage.id }" >${returnPackage.expressid }</a></td>
									<td>${returnPackage.returntype.name }</td>
									<td>${returnPackage.carrier }</td>
									<td>${returnPackage.customer }</td>
									<td>${returnPackage.phone }</td>
									<td><fmt:formatDate
												value="${returnPackage.sSignTime }"
												pattern="yyyy-MM-dd HH" />时</td>
									<td>
										<c:forEach var="returnOrderPackageRelate" items="${returnPackage.returnOrderPackageRelateList }">
											<c:if test="${returnOrderPackageRelate.status == 1 }">
												<a href="/order/${returnOrderPackageRelate.relateid }" >${returnOrderPackageRelate.relateid }</a>
												<a href="/returnorder/${returnOrderPackageRelate.returnorderid }/detail">${returnOrderPackageRelate.returnorderid }</a>
												<br>
											</c:if>
											<c:if test="${returnOrderPackageRelate.status == 0 }">
												<a href="/order/${returnOrderPackageRelate.relateid }" >${returnOrderPackageRelate.relateid }</a>
												<c:if test="${returnPackage.status.id != 600124 }">
													<a href="javascript:;" onclick="applyReturn(${returnPackage.id },${returnPackage.expressid },${returnOrderPackageRelate.relateid })" >申请退换货</a>
												</c:if>
												<br>
											</c:if>
										</c:forEach>
									</td>
									<td>${returnPackage.status.name }</td>
									<td>
										<c:if test="${returnPackage.status.id == 600121 }">
											<a href="javascript:;" onclick="relateOrderOpen(${returnPackage.id })">关联订单</a>
											<%-- <button onclick="changeStatus(${returnPackage.id },'dealing')">继续处理</button> --%>
											<button onclick="changeStatus(${returnPackage.id },'suspend')">挂起</button>
										</c:if>
										<c:if test="${returnPackage.status.id == 600122 }">
											<a href="javascript:;" onclick="relateOrderOpen(${returnPackage.id })">关联订单</a>
											<button onclick="changeStatus(${returnPackage.id },'done')">处理完毕</button>
											<button onclick="changeStatus(${returnPackage.id },'suspend')">挂起</button>
										</c:if>
										<c:if test="${returnPackage.status.id == 600123 }">
											<button onclick="changeStatus(${returnPackage.id },'dealing')">继续处理</button>
										</c:if>
									</td>
									<td>
										<a href="javascript:;" onclick="doremark(${returnPackage.id })">备注</a>
										<a href="javascript:;" onclick="showremark(${returnPackage.id })">查看</a>
									</td>
									<td><a href="javascript:;" onclick="showlog(${returnPackage.id })">查看</a></td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					<div>
						<c:if test="${pagination.count>0}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<div id="relateOrderDialog">
	<p>请输入单个完整有效的订单号...</p>
	<span id="temp_msg" style="color: red"></span>
	<div>
		<input type="text" id="ordersList" class="long" />
		<!-- <textarea rows="10" cols="40" style="width:600px;height:100px;" id="ordersList"></textarea> -->
	</div>
	<input type="text" id="temp_packageid" hidden="hidden" >
	<input type="button" value=" 关联 " onclick="relateOrder()">
</div>

<div id="remarkDialog">
	<div>
		<textarea id="temp_remark" rows="5" cols="40" style="width:600px;height:100px;" placeholder="请输入备注信息..."></textarea>
	</div>
	<input type="text" id="temp_remark_packageid" hidden="hidden" >
	<input type="submit" value=" 备注 " onclick="closeRemarkDialog()">
</div>

<iframe name="hidden-table" style="display: none;"></iframe>

<%@include file="../snippets/scripts.jsp"%>
<script src="${pageContext.request.contextPath}/js/returnorder/returnorderPackage.js"></script>
</body>
</html>
