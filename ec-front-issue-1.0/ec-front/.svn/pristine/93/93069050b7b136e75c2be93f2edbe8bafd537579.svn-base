<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配送范围和时间-文轩网</title>
<jsp:include page="/page/snippets/v2/meta.jsp">
	<jsp:param value="help" name="type" />
</jsp:include>
<style type="text/css">
.infostyle td {
	border: 1px solid #ccc;padding:4px;
}
.orange21b {
	margin-bottom: 16px!important;
}
</style>
</head>
<body class="delivery_info">
<div class="wrap">
	<jsp:include page="/page/snippets/v2/header.jsp"></jsp:include>
	<jsp:include page="/page/snippets/v2/navigation.jsp">
 		<jsp:param value="false" name="index" />
	</jsp:include>
	<div class="layout">
		<jsp:include page="help_menu.jsp">
			<jsp:param value="help_5_1" name="label" />
		</jsp:include>
		<div class="right_box">
			<h2 class="orange21b">订单配送方式及收费说明</h2>
			<div class="padding_left">请您根据收货人地址选择正确的省、市、区/县后，系统会提示您可供选择的送货方式及相关配送信息。</div>
			

			<div class="padding_left">
			<div id="help_r">
			<h3>书籍、音像商品送货方式及货到付款区域查询</h3>
			</div>
				<div>
					<span>地&nbsp;&nbsp;区：</span> <select areaLevel="country"
						name="country.id" class="country"></select> <select
						areaLevel="province" name="province.id"><option
							value="-1">请选择省份</option>
					</select> <select areaLevel="city" name="city.id" class="city"><option
							value="-1">请选择城市</option>
					</select> <select areaLevel="district" name="district.id" class="district"><option
							value="-1">请选择区县</option>
					</select>
				</div>
			</div>
			<br />
			<div id="view" class="padding_left">
				<table width="100%" cellspacing="1" cellpadding="3"
					class="infostyle">
					<thead>
						<tr>
							<td>配送区域</td>
							<td>送货方式</td>
							<td>配送范围</td>
							<td>配送时限</td>
							<td>货到付款</td>
						</tr>
					</thead>
					<tbody id="datashow">
						<tr>
							<td colspan="5">请选择地址</td>
						</tr>
					</tbody>

				</table>
			</div>
			<div class="hei10"></div>
		</div>
	</div>
	<script src="${serverPrefix}js/delivery_info.js?${version}"></script>
	<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
	</div>
</body>
</html>