<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>礼券查询</title>
<!-- 引入css -->
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
select.query {
	width: 100px;
}

textarea.query {
vertical-align:top;
	width: 200px;
	height: 50px;
}

button.query {
	width: 50px;
	width: 50px
}

div.query {
	height: 90px;
}

label.space {
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
            <div id="requestInfo" align="center" style="font-size: 15px; font-weight: bold;"></div>
				<h4>礼券查询</h4>
				<!-- 全部内容 -->
				<div>
					<!-- 查询条件 -->
					<div>
						<form action="/present/list" method="post" id="listForm">
							<!-- 查询条件1 -->
							<div class="query">
								<select class="query" name="codeName">

									<option value="id" <c:if test="${presentFindForm.codeName=='id'}">selected="selected"</c:if>
									>礼券编号</option>
									<option value="batch" <c:if test="${presentFindForm.codeName=='batch'}">selected="selected"</c:if>
									>礼券批次</option>
									<option value="code" <c:if test="${presentFindForm.codeName=='code'}">selected="selected"</c:if>
									>礼券激活码</option>
									<option value="customers" <c:if test="${presentFindForm.codeName=='customers'}">selected="selected"</c:if>
									>用户名</option>
								</select>
								<textarea class="query" name="coding">${presentFindForm.coding}</textarea>
								<button class="query" type="submit">查询</button>
							</div>
							<!-- 查询条件2 -->
							<div>
								<label>状态：</label>
								<c:forEach var="statu" items="${status.children }">	
								<input type="checkbox" name="status" value="${statu.id }"						
								<c:forEach var="id" items="${presentFindForm.status }">
								  <c:if test="${id==statu.id}">
								   checked="checked"
								  </c:if>
								</c:forEach>
								>${statu.name}
								</c:forEach>
							</div>
						</form>
					</div>
					<!-- 显示查询结果 -->
					<div>
						    <c:if test="${pagination!=null}">
							<button type="button" onclick="logout(0,${pagination.currentPage},${pagination.pageSize});">批量注销</button>
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							<div>
								<table class="list-table">
									<tr>
										<th><input type="checkbox" name="selectAll">
										</th>
										<th>编号</th>
										<th>批次编号</th>
										<th>用户名</th>
										<th>激活码</th>
										<th>面值</th>
										<th>状态</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>关联订单</th>
										<th>使用时间</th>
										<th>订单基准金额</th>
										<th>操作</th>
									</tr>
									<form action="/present/logout" id="logoutData" method="post">
									<c:forEach var="present" items="${presents}">
									<tr>
										<td>
										<c:if test="${present.state.id!=17001}">
										<input type="checkbox" disabled="disabled">
										</c:if>
										<c:if test="${present.state.id==17001}">
										<input type="checkbox" name="logout" value="${present.id}">
										</c:if>
										</td>
										<td><a href="/present/${present.id}/particular">${present.id}</a>
										</td>
										<td>${present.batch.id}</td>
										<td>${present.customer.name}</td>
										<td>${present.code}</td>
										<td>${present.value}</td>
										<td>${present.state.name}</td>
										<td><fmt:formatDate value="${present.startDate}" type="date" /></td>
										<td><fmt:formatDate value="${present.endDate}" type="date" /></td>
										<td>${present.order.id}</td>
										<td>${present.payTime}</td>
										<td>${present.batch.orderBaseAmount}</td>
										<td>
										<c:if test="${present.state.id==17001}">
										<a href="javascript:logout('${present.id}',${pagination.currentPage},${pagination.pageSize});">注销</a>
										</c:if>
										</td>
									</tr>
									</c:forEach>
									</form>
									<c:if test="${pagination.count>0}">
									<tr>
										<th><input type="checkbox" name="selectAll2">
										</th>
										<th>编号</th>
										<th>批次编号</th>
										<th>用户名</th>
										<th>激活码</th>
										<th>面值</th>
										<th>状态</th>
										<th>开始时间</th>
										<th>结束时间</th>
										<th>关联订单</th>
										<th>使用时间</th>
										<th>订单基准金额</th>
										<th>操作</th>
									</tr>
									</c:if>
								</table>
							</div>
							<c:if test="${pagination.count>0}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
							<button type="button" onclick="logout(0);">批量注销</button>
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
	//初始 
	$(document).ready(function() {
		$("input[name='selectAll']").click(function() {
			if ($("input[name='selectAll']").attr("checked")) {
				$("input[name='logout']").attr("checked", "checked");
			} else {
				$("input[name='logout']").removeAttr("checked");
			}
		});
		$("input[name='selectAll2']").click(function() {
			if ($("input[name='selectAll2']").attr("checked")) {
				$("input[name='logout']").attr("checked", "checked");
			} else {
				$("input[name='logout']").removeAttr("checked");
			}
		});
	})
	function logout(id,page,pageSize){
		if(confirm('确定要注销吗 ?')){
		var form = "";
		if(id==0){
			form = $('#logoutData').serialize();
		}else{
			form = "logout="+id;
		}
		form = form +"&format=json"
		ajaxForm(form,page,pageSize);
		}
	}
	function ajaxForm(form,page,pageSize) {
		var url = "/present/logout";
		$("#requestInfo").html('正在注销......');
		$("#requestInfo").dialog("open");
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			data : form,
			url : url,
			error : function(data) {// 请求失败处理函数
				$("#requestInfo").html('注销失败 ');
			},
			success : function(data) { // 请求成功后回调函数。
				  if(data.result){
					  $("#requestInfo").dialog("close");
						if(data.all==1){
							 pagination(page,pageSize);
						}else{
							if(confirm('成功注销'+data.count+'条记录,失败 '+(data.all-data.count)+'条记录 ')){
								pagination(page,pageSize);
							}
						}
				  }else{
					  $("#requestInfo").html('注销失败 '); 
				  }
			}
		});
	}
	$("#requestInfo").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 250,
		height : 80
	});
</script>
</body>
</html>