<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>促销活动标签</title>
</head>
<div>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<form action="/promotion/eidt/tag" method="post">
					<h4>新建促销标签</h4>
					<input type="hidden" name="id" value="${promotionTag.id}">
					<table>	
						<tr>
							<td>促销标签地址：</td>
							<td><input name="url" value="${promotionTag.url}" type="text"></td>
						</tr>
						<tr>
							<td>促销排位：</td>
							<td><input name="rank" value="${promotionTag.rank}" type="text"></td>
						</tr>
						<tr>
							<td>促销类型：</td>
							<td>
								<select name="type.id">
									<c:forEach items="${code}" var="c">
										<option value="${c.id }" <c:if test="${c.id==promotionTag.type.id}">selected="selected"</c:if>>${c.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>是否可用：</td>
							<td><input  name="available" value="true"<c:if test="${promotionTag.available==true}"> checked="checked" </c:if> type="radio">可用
							<input  name="available" value="false" <c:if test="${promotionTag.available==false}"> checked="checked"</c:if> type="radio">不可用</td>
						</tr>
					</table>
					<input type="submit" value="保存">
				</form>
			</div>
		</div>
	</div>
</div>

