<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>礼品卡查询</title>
<!-- 引入css -->
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
div.queryBuilder {
	margin-left: 10px;
}

div.queryBuilder2 {
	margin-top: 5px;
}

textarea {
vertical-align:top;
	margin-top: 0px;
	width: 400px;
	height: 30px;
}
select.choose {
	margin-left: 10px;
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
				<div id="requestInfo" align="center"
					style="font-size: 15px; font-weight: bold;"></div>
				<h4>礼品卡查询</h4>
				<hr>
				<!-- 全部内容 -->
				<div>
					<!-- 查询条件 -->
					<div class="queryBuilder">
						<form action="/presentcard/list" id="giftcardForm" method="post">
							<div>
								<label>卡号：</label>
								<textarea name="id">${cardForm.id}</textarea>
								<button type="button" id="search" >查询</button>
							</div>
							<div class="queryBuilder2">
								<label>卡类型：</label> <select class="choose" name="type">
									<option value="0">全部</option>
									<c:forEach var="type" items="${types}">
									<c:if test="${cardForm.type==type.id}">
									<option value="${type.id}" selected="selected">${type.name}</option>
									</c:if>
									<c:if test="${cardForm.type!=type.id}">
									<option value="${type.id}">${type.name}</option>
									</c:if>
									</c:forEach>
								</select> <label>卡状态：</label> 
								<c:forEach var="statu" items="${status}">
								 ${statu.name} > <input type="checkbox" name="status" value="${statu.id}" <c:forEach var="statu2" items="${cardForm.status}">
								           <c:if test="${statu2==statu.id}">
								               checked="checked" 
								           </c:if>
								       </c:forEach> />
								</c:forEach>
							</div>
							<div>
								<label>订单号：</label> <input type="text" name="orderId" value="${cardForm.orderId}"> <label>面值：</label>
								<input type="text" name="denomination" value="${cardForm.denomination}"> <label>创建时间：</label>
								<input type="text" name="startdate" bind="datepicker" value="${cardForm.startdate}">至<input
									type="text" name="enddate" bind="datepicker" value="${cardForm.enddate}">
							</div>
						</form>
					</div>
					<button type="button" onclick="excel('giftcardForm');">导出Excel</button>
					<!-- 查询结果 -->
					<br>
					<div>
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							<div>
								<input type="hidden" name="format" value="xls">
									<table class="list-table">
										<tr>
											<th>卡号</th>
											<th>类型</th>
											<th>状态</th>
											<th>有效期</th>
											<th>面值</th>
											<th>余额</th>
											<th>订单号</th>
											<th>绑定用户</th>
										</tr>
										<c:forEach var="presentCard" items="${presentCards}" varStatus="status" >
											<tr>
												<td><a href="/presentcard/cardinfo/${presentCard.id}">${presentCard.id}</a>
												</td>
												<td>${presentCard.type.name}</td>
												<td>${presentCard.status.name}</td>
												<td><fmt:formatDate value="${presentCard.endDate}"
														type="date" /></td>
												<td>${presentCard.denomination}元</td>
												<td>${presentCard.balance}元</td>
												<td><c:if test="${!empty presentCard.order}"><a href="/order/${presentCard.order.id}">${presentCard.order.id}</a></c:if></td>
												<td><c:if test="${!empty presentCard.customer}">${ presentCard.customer.name}</c:if></td>
											</tr>
										</c:forEach>
										<c:if test="${pagination.count>0}">
											<tr>
												<th>卡号</th>
												<th>类型</th>
												<th>状态</th>
												<th>有效期</th>
												<th>面值</th>
												<th>余额</th>
												<th>订单号</th>
												<th>绑定用户</th>
											</tr>
										</c:if>
									</table>
							</div>
							<c:if test="${pagination.count>0}">
								<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							</c:if>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="loading">数据处理中... 请稍候</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript">
	//提交excel表单
    function excel(id){
			var form = $('#'+id);
			form.attr("action","/excel/giftcard/list?format=xls&style=1");
		    form.submit();
	}
	$("#requestInfo").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 250,
			height : 80
		});
	$("#search").click(function(){
		$("#giftcardForm").attr("action","/presentcard/list");
		$("#giftcardForm").submit();
	});
	</script>
</body>
</html>