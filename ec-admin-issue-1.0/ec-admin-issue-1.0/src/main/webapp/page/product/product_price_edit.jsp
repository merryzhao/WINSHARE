<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<html>
<head>
<title>修改商品销售价格</title>

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
	width: 650px;
 	border: 1px solid #DFDFDF;
}

label.error {
	padding: 0.1em;
}

#priceEditForm input.error {
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
					<h4>修改商品销售价格 </h4><span
									style="color: red; font-size: 10px">(目前仅支持电子书修改,非电子书请到价格系统修改)</span>
					<form  name="priceEditForm" id="priceEditForm"  
						action="${contextPath}/product/priceupdate" method="post">
						<token:token></token:token>
						<input type="hidden" id="id"  name="id" value="${productSale.id}"/>
						<div>
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
									<td>促销语：</td>
									<td>${productSale.promValue}</td>
								</tr>
								<c:if test="${productSale.hasPromotion }">
								<tr>
									<td>促销价:${productSale.promotionPrice}</td>
									<td>促销价:<fmt:formatDate value="${productSale.promotionStartTime}" type="date"/>~<fmt:formatDate value="${productSale.promotionEndTime}" type="date"/></td>
				 				</tr>
				 				</c:if>
								<tr>
									<td align="right">修改前的销售价格：</td>
									<td align="left">
 											<label>${productSale.salePrice}</label>
 									</td>
								</tr>
								<tr>
									<td align="right">修改后的销售价格：</td>
									<td align="left">
										<input type="text" name="newPrice" id="newPrice"/>
  									</td>
								</tr>
							</table>
 							<div>
 									 <input style="width: 60px;"
										type="submit" value="提交"/> <input type="reset"
										style="width: 60px;" value="重置" /> 
							</div>
 						</div>
					</form>
					<br/>
					<br/>
				<h4>销售价格修改日志</h4>
				<table class="list-table">
				<tr>
					<th>序号</th>
					<th>修改人</th>
					<th>修改时间</th>
					<th>原销售价格</th>
					<th>修改后的销售价格</th>
				</tr>
				<c:forEach items="${priceLog}" var="pl" varStatus="index">
				<tr>
				<td>${index.count}</td>
				<td>${pl.operator.name}</td>
				<td><fmt:formatDate value="${pl.updateTime}" type="both"/></td>
				<td>${pl.originalPrice}</td>
				<td>${pl.newPrice}</td>
				</tr>
				</c:forEach>
				</table>
			</div>
			</div>
		</div>
	</div>
   	<%@include file="../snippets/scripts.jsp"%>
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
<script type="text/javascript">
$().ready(function(){
 	 //表单验证
 	$("#priceEditForm").validate({
		rules:{
			newPrice:{
		    		required:true,
		    		isMoney:true
			    }  
 	    },
 	    
	messages:{
 
		newPrice:{
					required:"销售价格必须填写",
					isMoney:"销售价格式错误" 
			    }  
 	}
	});	
})
</script>
 </body>
</html> 