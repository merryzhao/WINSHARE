<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<title>新建礼品卡商品</title>
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
				<h4>新建礼品卡商品</h4>
				<div>
					<form id="create"   action="/presentcard/productCreate" method="post">
						<div>
							<!-- 收货人信息 -->
							<div class="entering_table">
								<table class="entering_table">
									<tbody>
								
								
									<tr>
										<td class="property">
											类型
										<label class="red">*</label>
										</td>
										<td>
									实物卡<input type="radio" name="category" value="${physicalCategory}" /> 
									电子卡<input type="radio" name="category" value="${electronicCategory}" />
									
										</td>
										<td width="20"></td>
										<td class="property">价格<label class="red">*</label>
										</td>
										<td><input type="text" name="listPrice" size="5">
										</td>
									</tr>
									<tr>
										<td class="property">名称<label class="red">*</label>
										</td>
										<td><input type="text" id="name" name="name">
										</td>
										<td></td>
											<td class="property">库存数量<label class="red">*</label>
										</td>
										<td><input type="text" id="stock" name="stock" size="5">
										</td>
									</tr>
									
									</tbody>

								</table>
							</div>
							<input type="submit" id="tjdd" value="提交" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
<%@include file="../snippets/scripts.jsp"%>
</body>
</html>