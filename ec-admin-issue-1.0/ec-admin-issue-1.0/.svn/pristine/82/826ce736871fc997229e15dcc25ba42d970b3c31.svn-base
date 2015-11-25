<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<title>添加卖家</title>

<style type="text/css">
.selectStyle {
	width: 60;
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
	width: 500px;
	height: 500px;
	border: 1px solid #DFDFDF;
}

label.error {
	padding: 0.1em;
}

#shopForm input.error {
	padding: 0px;
	border: 1px solid red;
}

#preview {
	display: inline-block;
	width: 100px;
	height: 100px;
	background-color: #CCC;
}
</style>
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

				<div id="add-body">
					<h4>新建卖家</h4>
					<form:form commandName="shopForm" name="shopForm" id="shopForm"  enctype="multipart/form-data"
						action="${contextPath}/seller/_save" method="post">
						<token:token></token:token>
						<input type="hidden" value="add" name="operate" />
						<div>
							<table class="list-table1">
								<tr>
									<td align="right">卖家账号名<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="name" maxlength="25" />
										<label><form:errors path="name"></form:errors>
									</label>
									<label style="color: red;">${nameError}</label>
									</td>
								</tr>
								<tr>
									<td align="right">卖家密码<span style="color: red;">*</span>：</td>
									<td align="left"><form:password path="passWrod" maxlength="25" />
										<label><form:errors path="passWrod"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">卖家店铺名<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="shopName" maxlength="25" />
										<label><form:errors path="shopName"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">存储方式<span style="color: red;">*</span>：</td>
									<td align="left"><form:select path="storageType">
									<c:forEach items="${storage.children }" var="storage">
										<form:option value="${storage.id }">${storage.name }</form:option>
									</c:forEach>
									</form:select>
									</td>
								</tr>
								<tr>
									<td align="right">客服电话<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="serviceTel" maxlength="20" />
										<label><form:errors path="serviceTel"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">经营分类<span style="color: red;">*</span>：</td>
									<td><form:checkbox value="B" path="businessScope" />图书 <form:checkbox
											value="V" path="businessScope" />音像 <form:checkbox value="G"
											path="businessScope" />百货&nbsp;<label for="businessScope" generated="true" class="error" style="display: none;"></label></td>
								</tr>
								<tr>
									<td align="right">公司名称<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="companyName" maxlength="25" />
										<label><form:errors path="companyName"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">负责人<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="chargeMan" maxlength="25" />
										<label><form:errors path="chargeMan"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">固定电话<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="phone" maxlength="25" />
										<label><form:errors path="phone"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">手机<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="mobilePhone" maxlength="25" />
										<label><form:errors path="mobilePhone"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">邮箱<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="email" maxlength="25" />
										<label><form:errors path="email"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">邮编<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="zipCode" maxlength="25" />
										<label><form:errors path="zipCode"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">公司地址<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="address" maxlength="25" />
										<label><form:errors path="address"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">开户行<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="bank" maxlength="25" />
										<label><form:errors path="bank"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">银行账号<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="bankAcount" maxlength="25" />
										<label><form:errors path="bankAcount"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">店铺截止日期<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="endDate" readonly="true"   bind="datepicker" />
										<label><form:errors path="endDate"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">运费<span style="color: red;">*</span>：</td>
									<td align="left"><form:input path="deliveryFee" maxlength="10" />
										<label><form:errors path="deliveryFee"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">导入logo：</td>
									<td align="left"><input type="file" id="picPath"  
										name="localfile"
										onChange="previewImage('preview',this,100,100);" /></td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2" align="center"><input style="width: 60px;"
										type="submit" value="提交" /> <input type="reset"
										style="width: 60px;" value="重置" /></td>
								</tr>
							</table>
						</div>
						<div style="float: right; margin-top: -100px; margin-right: 50px;">
							<div id="preview" 
								style="filter: progid : DXImageTransform.Microsoft.AlphaImageLoader ( sizingMethod = scale );">
								<img id="detailimg" src="/imgs/seller/default.jpg"
									width="100" height="100">
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

   	<%@include file="../snippets/scripts.jsp"%>
	<%@include file="../snippets/meta.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.mousewheel.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.easing.1.3.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/seller/seller_add.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/seller/seller_validate.js"></script>
</body>
</html>