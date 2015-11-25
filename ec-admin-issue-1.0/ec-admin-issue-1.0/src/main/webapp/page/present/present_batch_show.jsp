<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>查看礼券批次</title>

<style type="text/css">
.selectStyle {
	width: 100;
}

.buttonstyle {
	width: 85px;
	height: 25px;
}

.textareaStyle {
	width: 350px;
	height: 60px;
	padding: 0px;
}

#add-body {
	margin-top: 30px;
	margin-left: 30px;
	width: 620px;
	height: 450px;
	border: 2px solid #DFDFDF;
}

label.error {
	padding: 0.1em;
}

#presentBatchForm input.error {
	padding: 0px;
	border: 2px solid red;
}
</style>
<%@include file="../snippets/meta.jsp"%>
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

				<div id="add-body">
					<center><h4>查看礼券批次</h4></center>
					<form:form commandName="presentBatchForm"
						action="${contextPath}/presentbatch/createorupdate" method="post">
						<token:token></token:token>
						<table class="list-table1">
							<tr>
								<td align="right">礼券批次：</td>
								<td align="left">${presentBatchForm.id}</td>
								<td align="right" style="padding: 0 0 0 90;">礼券面额：</td>
								<td align="left">${presentBatchForm.value }</td>
							</tr>
							<tr>
								<td align="right">生成数量：</td>
								<td align="left">${presentBatchForm.num}</td>
							</tr>
							<tr>
								<td align="right">是否通用：</td>
								<td align="left"><form:select path="isGeneral" cssClass="selectStyle" disabled="true">
										<form:option value="true" label="是"></form:option>
										<form:option value="false" label="否"></form:option>
									</form:select></td>
								<td align="right" style="padding: 0 0 0 90;">通用码：</td>
								<td align="left">${presentBatchForm.generalCode}</td>
							</tr>
							<tr>
								<td align="right">订单额基准金额：</td>
								<td align="left" colspan="3">${presentBatchForm.orderBaseAmount
									}</td>
							</tr>
							<tr>
								<td align="right">针对商品类别：</td>
								<td align="left" colspan="3"><form:checkbox value="B"
										path="productType" disabled="true" />图书 <form:checkbox
										value="V" path="productType" disabled="true" />影像 <form:checkbox
										value="G" path="productType" disabled="true" />百货</td>
							</tr>
							<tr>
								<td align="right">礼券生效开始日期：</td>
								<td align="left">${presentBatchForm.presentStartDateString
									}</td>
							</tr>
							<tr>
								<td align="right">礼券有效截止期：</td>
								<td align="left">${presentBatchForm.presentEndDateString }
								</td>
								<td align="right">礼券有效期：</td>
								<td align="left">${presentBatchForm.presentEffectiveDay } 日</td>
							</tr>
							<tr>
								<td align="right">单用户最多申领数量：</td>
								<td align="left" colspan="3">
									${presentBatchForm.maxQuantity }</td>
							</tr>
							<tr>
							<tr>
								<td></td>
								<td align="left" colspan="3"><form:checkbox value="Y"
										path="isRebate" disabled="true" />是否参与联盟 <form:checkbox
										value="Y" path="isPloy" disabled="true" />是否参与活动</td>
							</tr>
							<td align="right">批次标题：</td>
							<td align="left" colspan="3">${presentBatchForm.batchTitle }
							</td>
							</tr>
							<tr>
								<td align="right"><form:hidden path="id" />批次描述：</td>
								<td align="left" colspan="3">${presentBatchForm.description
									}</td>
							</tr>
							<tr>
								<td></td>
								<td align="left"><a href="/present/dispensePage?id=${presentBatchForm.id}">分发礼券</a></td>
							</tr>
						</table>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/presentbatch/present_batch_add.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/presentbatch/presentbatch_validate.js"></script>
</body>
</html>