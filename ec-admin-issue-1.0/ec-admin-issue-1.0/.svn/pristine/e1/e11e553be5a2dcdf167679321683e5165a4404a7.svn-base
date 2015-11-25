<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@include file="../../snippets/scripts.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token"  prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="${contextPath }/js/order/report/delivery.js"></script>
<%@include file="../../snippets/meta.jsp"%>
<title>清单查询</title>
<link rel="stylesheet" href="${contextPath}/css/slidingtabs-horizontal.css">
</head>
<body>

<div class="frame">
		<!-- 引入top部分 -->
<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
<%@include file="../../snippets/frame-left-order.jsp"%>
	<div class="frame-main">
		<div>
			<form action="/order/report/delivery/list" method="post">
				查询条件<hr />
				<div style="float: inherit;">
					<label><b>发货时间:</b></label>
					<input class="starttime" id='startDeliveryTime' name="startDeliveryTime" readonly="readonly" bind="datepicker" width="10px"  style="width:100px" /> ~
					<input class="endtime" id='endDeliveryTime' name="endDeliveryTime" readonly="readonly" bind="datepicker"  style="width:100px" />
					
					回填状态：<select name="state">
						<option value="">请选择</option>
						<option value="1">有回填</option>
						<option value="-1">无回填</option>
					</select>
				</div>
				<hr />
				<div style="float: inherit;">
					<label><b>订单号</b></label>
					<textarea name="orders" style="height: 100px;width: 300px;"></textarea>
				</div>
				<hr />
				<div style="float: inherit;">
					<label><b>渠道:</b></label><br />
					<c:forEach var="c" items="${channels}" varStatus="s">
						<input name="channels" type="checkbox" value="${c.id }" />${c.name }&nbsp;&nbsp;&nbsp;
						<c:if test="${s.count % 5 == 0 }">
							<br />
						</c:if>
					</c:forEach>
				</div>
				<hr />
				<div style="float: inherit;">
					<label><b>区域:</b></label>
					<select areaLevel="country" style="width:120px">
						<option value="-1">请选择国家</option>
    				</select>
					<select areaLevel="province">
							<option value="-1">请选择省份</option>
					</select> <select areaLevel="city">
							<option value="-1">请选择城市</option>
					</select> <select areaLevel="district" >
							<option value="-1" name="district">请选择区县</option>
					</select>
					<input value="添加" type="button" onclick="delivery.appendArea();" />
					<br />
				</div>
				<div>
					<ul id='areaList'>
						
					</ul>
				</div>
				<hr />
				<div>
					<input type="submit" value="查询" onclick="return delivery.formCheck();" />
					&nbsp;&nbsp;
					<input type="reset" value="重置"/>
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
<script type="text/javascript" src="${contextPath}/js/area/areaevent.js"></script>
</body>