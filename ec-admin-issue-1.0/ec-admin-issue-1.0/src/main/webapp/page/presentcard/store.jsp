<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>入库礼品卡</title>
<style type="text/css">
.error {
	padding: 0px;
	border: 0px;
	color: #FF0000;
}

#presentcardIds {
	width: 200px;
	height: 50px;
	margin-bottom: 0px;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="content-result">
					<h4>入库礼品卡</h4>
					<form action="${contextPath }/presentcard/store" method="post"
						enctype="multipart/form-data" id="fileForm"
						onsubmit="return check();">
						<input type="hidden" value="0" name="storeType"/>
						<table>
							<tr>
								<td>导入Excel：</td>
								<td><input type="file" name="file" id="file" /></td>
								<td><button type="submit">确定</button></td>
								<td class="error" id="message"></td>
							</tr>
						</table>
					</form>
					<form action="${contextPath }/presentcard/store" method="post" onsubmit="return checkTextArea();">
						<div>
							<label>请输入卡号：</label>
							<input type="hidden" value="1" name="storeType"/>
							<textarea rows="" cols="" id="presentcardIds"></textarea>
							<button type="submit" id="submitText">确定</button>
							<input type="hidden" name="presentCardIdstr" id="presentCardIdstr"/>
							<label id="textMessage" class="error"></label>
						</div>
					</form>
					<hr />
					<c:if test="${empty storageSuccessPresentCards }">
						<span class="error">${exceptionMessage}</span>
					</c:if>
					<c:if test="${!empty storageSuccessPresentCards }">
						<c:if test="${!empty pagination}">
							<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
						</c:if>
						<table class="list-table">
							<colgroup>
								<col class="id" />
								<col class="type" />
								<col class="status" />
								<col class="number" />
								<col class="date" />
								<col class="date" />
							</colgroup>
							<tr>
								<th>序号</th>
								<th>卡号</th>
								<th>类型</th>
								<th>状态</th>
								<th>创建时间</th>
								<th>有效日期</th>
							</tr>
							<c:forEach items="${storageSuccessPresentCards}"
								var="presentCard" varStatus="status">
								<tr >
									<td>
										${status.index+1}
									</td>
									<td><a
										href="${contextPath }/presentcard/cardinfo/${presentCard.id}">${presentCard.id
											}</a>
									</td>
									<td>${presentCard.type.name}</td>
									<td>${presentCard.status.name }</td>
									<td><fmt:formatDate value="${presentCard.createDate }" type="both"/></td>
									<td><fmt:formatDate value="${presentCard.endDate }" type="both"/></td>
								</tr>
								<c:if test="${status.last }"><tr><th colspan="7">总共:${status.index+1 }</th></tr></c:if>
							</c:forEach>
						</table>
						<c:if test="${!empty pagination}">
							<winxuan:page pagination="${pagination}" bodyStyle="html"></winxuan:page>
						</c:if>
					</c:if>

				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/presentcard/store.js"></script>
</body>
</html>