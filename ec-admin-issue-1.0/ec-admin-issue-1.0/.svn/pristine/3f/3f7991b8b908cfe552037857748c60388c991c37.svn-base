<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<html>
<head>
<title>商品管理分级设置</title>
<%@include file="../snippets/meta.jsp"%>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="add-body">
					<h4>添加商品管理分级 </h4>
					<form action="/product/saveManageGrade" method="post" onsubmit="return check();">
						<token:token></token:token>
						<input type="hidden"   name="psId" value="${productSale.id}"/>
						<div>
							<span style="color: red;">${message }</span>
							<table class="list-table1">
								<tr>
									<td align="right">商品名称：</td>
									<td align="left">
									<label>
										${productSale.product.name}
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">商品编码：</td>
									<td align="left"> 
										<label> 
											${productSale.id}
										</label>
									</td>
								</tr>
								<tr> 
									<td>管理分级等级：</td>
									<td>
										<select cssStyle="vertical-align:top;" name="grade">
											<c:forEach items="${manageGrade.availableChildren}" var="grade">
												<option value="${grade.id}">${grade.name }</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>热销时间范围</td>
									<td><input id="hotSellingStart" name="hotSellingStart" />~<input id="hotSellingEnd" name="hotSellingEnd" /></td>
				 				</tr>
				 				<tr>
									<td>进货折扣</td>
									<td><input id="purchaseDiscount" name="purchaseDiscount" /></td>
				 				</tr>
				 				<tr>
									<td>备注</td>
									<td><textarea id="remark" name="remark" cols="50" rows="5"></textarea></td>
				 				</tr>
							</table>
 						</div>
 						<div><input type="submit" value="提交" /> </div>
					</form>
					<br/>
					<br/>
				<h4>管理分级记录</h4>
				<table class="list-table">
				<tr>
					<th>等级</th>
					<th>热销时间范围</th>
					<th>进货折扣</th>
					<th>最后修改人</th>
					<th>最后修改时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${productSale.manageGrades}" var="mg">
				<tr>
					<td>${mg.grade.name}</td>
					<td><fmt:formatDate value="${mg.hotSellingStart}" type="both"/>~
						<fmt:formatDate value="${mg.hotSellingEnd }" type="both"/></td>
					<td>${mg.purchaseDiscount}</td>
					<td>${mg.operator.name}</td>
					<td><fmt:formatDate value="${mg.operateTime}" type="both"/></td>
					<td><a href="/product/${mg.id }/removeManageGrade" onclick="return check();">删除</a></td>
				</tr>
				<c:if test="${mg.remark != '' && not empty mg.remark }">
				<tr>
					<td colspan="5" align="left">备注：${mg.remark }</td>
				</tr>
				</c:if>
				</c:forEach>
				</table>
			</div>
			</div>
		</div>
	</div>
   	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
<script type="text/javascript">
$().ready(function(){
	$("#hotSellingStart").datepicker({changeYear:true});
	$("#hotSellingEnd").datepicker({changeYear:true});
})

function check(){
	if(confirm("您确定执行此操作?")){
		return true;
	}
	return false;
}
</script>
 </body>
</html>