<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet"/>
<title>联盟管理</title>
<style type="text/css">
.clcheckbox{ margin:0px;padding:0px;width:14px;}
input.availableyes{ margin-left: 22px}
input.availableno{margin-left: 8px}
input.usingapiyes{margin-left: 5px}
input.usingapino{margin-left: 8px}
label.error {
	padding:0.05em;
	color: red;
	font-weight: normal;
}
input.error {
	padding:0px;
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
     <div class ="ui-widget" >       
           <div class ="ui-widget-content">  
           <div id="content-result">
				<h4>修改佣金日志</h4>
							<div id="content-result">
								<c:choose>
									<c:when test="${!empty logs}">
										<table class="list-table" id="tree-table">
											<colgroup>
												<col class="createdate" />
												<col class="oldcommission" />
												<col class="newcommission" />
												<col class="operator" />
											</colgroup>
											<tr>
												<th>修改时间</th>
												<th>修改前金额</th>
												<th>修改后金额</th>
												<th>修改者</th>
											</tr>
											<c:forEach var="log" items="${logs}" varStatus="status">
												<c:if test="${log.type == 1}">
													<c:set var="rowStatus" value="odd" />
													<c:if test="${status.index%2==1}">
														<c:set var="rowStatus" value="even" />
													</c:if>
													<tr class=" ${rowStatus}">
														<td><fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
														<td>${log.oldCommission}</td>
														<td>${log.newCommission}</td>
														<td>${log.operator.name}</td>
													</tr>
												</c:if>
											</c:forEach>
										</table>
									</c:when>
								</c:choose>
							</div>
						</div>
     </div>
     <div class ="ui-widget" >       
           <div class ="ui-widget-content">  
           <div id="content-result">
				<h4>修改支付日志</h4>
				 <div id="content-result">
     			<c:choose>
    			 <c:when test="${!empty logs}">
										<table class="list-table" id="tree-table">
											<colgroup>
												<col class="createdate" />
												<col class="oldcommission" />
												<col class="newcommission" />
												<col class="operator" />
											</colgroup>
											<tr>
												<th>修改时间</th>
												<th>修改前状态</th>
												<th>修改后状态</th>
												<th>修改者</th>
											</tr>
											<c:forEach var="log" items="${logs}" varStatus="status">
												<c:if test="${log.type == 2}">
													<c:set var="rowStatus" value="odd" />
													<c:if test="${status.index%2==1}">
														<c:set var="rowStatus" value="even" />
													</c:if>
													<tr class=" ${rowStatus}">
														<td><fmt:formatDate value="${log.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
														<td><c:choose> <c:when test="${log.oldPay}">已支付</c:when><c:otherwise>未支付</c:otherwise></c:choose></td>
														<td><c:choose> <c:when test="${log.newPay}">已支付</c:when><c:otherwise>未支付</c:otherwise></c:choose></td>
														<td>${log.operator.name}</td>
													</tr>
												</c:if>
											</c:forEach>
										</table>
									</c:when>
    			 </c:choose>
           </div>
           </div>
     </div>
     </div>
     </div>
<%@include file="../snippets/scripts.jsp" %>
<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/union/commission.js"></script>
</div>
</div>
</div>
</body>
</html>