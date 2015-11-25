<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet" />
<title>联盟管理</title>
<style type="text/css">
.clcheckbox {
	margin: 0px;
	padding: 0px;
	width: 14px;
}

input.availableyes {
	margin-left: 22px
}

input.availableno {
	margin-left: 8px
}

input.usingapiyes {
	margin-left: 5px
}

input.usingapino {
	margin-left: 8px
}

label.error {
	padding: 0.05em;
	color: red;
	font-weight: normal;
}

input.error {
	padding: 0px;
	border: 1px solid red;
}
</style>
</head>
<body>
	<div class="frame">
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-channel.jsp"%>

		<div class="frame-main">
			<div class="right_box">
				<div class="ui-widget">
					<div class="ui-widget-content">
						<div id="content-result">
							<h4>查询条件</h4>
							<form:form action="/union/unionCommissionList"
								id="unionCommissionList" method="post"
								commandName="unionCommissionForm" onsubmit="return checkTime();">
								<table>
									<tr>
										<td width="10%"></td>
										<td width="15%"></td>
										<td width="5%"></td>
										<td width="10%"></td>
										<td width="15%"></td>
										<td width="45%"></td>
									</tr>
									<tr>
										<td align="right">联盟名称：</td>
										<td><input type="text" name="name" style="width: 120px"
											value="${selectedParameters['name']}" /></td>
										<td />

										<td align="right">联盟标识：</td>
										<td><input type="text" name="unionId"
											style="width: 120px" value="${selectedParameters['unionId']}" />
										</td>
									</tr>
									<tr>
										<td align="right">下单时间：</td>
										<td colspan="3"><select name="startYear"
											class="startYear" style="width: 55px">
												<option value="2011">2011</option>
												<option value="2010">2010</option>
												<option value="2009">2009</option>
										</select> <select name="startMonth" class="startMonth"
											style="width: 40px">
												<c:forEach var="month" begin="1" end="12">
													<option value="${month}">${month}</option>
												</c:forEach>
										</select>— <select name="endYear" class="endYear" style="width: 55px">
												<option value="2011">2011</option>
												<option value="2010">2010</option>
												<option value="2009">2009</option>
										</select> <select name="endMonth" class="endMonth" style="width: 40px">
												<c:forEach var="month" begin="1" end="12">
													<option value="${month}">${month}</option>
												</c:forEach>
										</select></td>
									</tr>
									<tr>
										<td align="right">支付状态：</td>
										<td><select name="isPay" class="isPay"
											style="width: 100px">
												<option value="-1">所有支付状态</option>
												<option value="0">未支付</option>
												<option value="1">已支付</option>
										</select></td>
										<td align="right"><input type="submit" value="查  询">
										</td>
										<td align="center"><input type="reset" value="重   置">
										</td>
									</tr>
									<tr>
										<td align="right"><input type="button"
											onclick="exportUnionCommission()" value="导    出">
										</td>
									</tr>
								</table>
							</form:form>
						</div>
					</div>
					<div id="content-result">
						<c:choose>
							<c:when test="${!empty unionCommissionList}">
								<c:if test="${!empty pagination}">
									<winxuan-page:page bodyStyle="javascript"
										pagination="${pagination}"></winxuan-page:page>
								</c:if>
								<table class="list-table" id="tree-table">
									<colgroup>
										<col class="unionName" />
										<col class="unionId" />
										<col class="rate" />
										<col class="createtime" />
										<col class="effectiveMoney" />
										<col class="commission" />
										<col class="ispay" />
									</colgroup>
									<tr>
										<th>联盟名称</th>
										<th>联盟标识</th>
										<th>佣金比例</th>
										<th>下单时间</th>
										<th>有效金额</th>
										<th>佣金金额</th>
										<th>是否支付</th>
										<th>操作</th>
									</tr>
									<c:forEach var="unionCommission" items="${unionCommissionList}"
										varStatus="status">
										<c:set var="rowStatus" value="odd" />
										<c:if test="${status.index%2==1}">
											<c:set var="rowStatus" value="even" />
										</c:if>
										<tr class=" ${rowStatus}">
											<td>${unionCommission.union.name}</td>
											<td>${unionCommission.union.id}</td>
											<td>${unionCommission.union.rate}</td>
											<td>${unionCommission.time}</td>
											<td>${unionCommission.effiveMoney}</td>
											<td><span id="commission${unionCommission.id }">${unionCommission.commission}</span>
												<a href="#"
												onclick="showCommission(${unionCommission.id },${unionCommission.commission})">
													修改</a>
											</td>
											<td><span id="pay${unionCommission.id }"> <c:choose>
														<c:when test="${unionCommission.pay}">已支付</c:when>
														<c:otherwise>未支付</c:otherwise>
													</c:choose> </span> <a href="#"
												onclick="showPay(${unionCommission.id },${unionCommission.pay})">
													修改</a></td>
											<td><a href="/union/${unionCommission.id}/log">查看日志</a>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<th>联盟名称</th>
										<th>联盟标识</th>
										<th>佣金比例</th>
										<th>下单时间</th>
										<th>有效金额</th>
										<th>佣金金额</th>
										<th>是否支付</th>
										<th>操作</th>
									</tr>
								</table>
								<c:if test="${!empty pagination}">
									<winxuan-page:page bodyStyle="javascript"
										pagination="${pagination}"></winxuan-page:page>
								</c:if>
							</c:when>
						</c:choose>
					</div>
				</div>
				<div id="editCommission">
					<input type="hidden" name="unionCommissionId"
						id="unionCommissionId" />
					<fieldset>
						<p>
							<label>原佣金：</label><input type="text" name="oldCommission"
								id="oldCommission" width="10px" readonly="readonly" />
						</p>
						<p>
							<label>新佣金：</label><input type="text" name="newCommission"
								id="newCommission" width="10px" />
						</p>
					</fieldset>
					<div class="center">
						<button type="submit" onclick="editCommission();">保存</button>
					</div>
				</div>

				<div id="editPay">
					<input type="hidden" name="unionCommissionId2"
						id="unionCommissionId2" />
					<fieldset>
						<p>
							是否支付： <input id="isPay" type="radio" name="isPay" value="1"
								checked="checked">已支付 <input id="isNoPay" type="radio"
								name="isPay" value="0">未支付
						</p>
					</fieldset>
					<div class="center">
						<button type="submit" onclick="editPay();">保存</button>
					</div>
				</div>

				<%@include file="../snippets/scripts.jsp"%>
				<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
				<script type="text/javascript"
					src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
				<script type="text/javascript"
					src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
				<script type="text/javascript"
					src="${pageContext.request.contextPath}/js/union/commission.js"></script>
			</div>
		</div>
	</div>
	
</body>
</html>
