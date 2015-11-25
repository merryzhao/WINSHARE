<%@page pageEncoding="UTF-8"%>
<%@ include file="../snippets/tags.jsp"%>
<div>
	<div class="create">
		<a href="javascript:void(0)" onclick="visible('add');">创建枚举项</a>
	</div>
	<table class="enum-table">
		<colgroup>
			<col class="name" />
			<col class="number" />
			<col class="type" />
			<col class="operator" />
		</colgroup>
		<tr>
			<th>枚举Value</th>
			<th>顺序</th>
			<th>是否默认</th>
			<th>操作</th>
		</tr>
		<c:forEach var="productMetaEnum" items="${productMetaEnums}">
			<!-- 显示数据 -->
			<tr id="${productMetaEnum.id}_view" class="normal">
				<td>${productMetaEnum.value}</td>
				<td>${productMetaEnum.index}</td>
				<td><c:choose>
						<c:when test="${productMetaEnum.defaultValue}">
                                                                 是
                        </c:when>
						<c:otherwise>
                                                                 否
                        </c:otherwise>
					</c:choose></td>
				<td><a class="update" href="javascript:void(0)"
					onclick="visible('${productMetaEnum.id}_update');invisible('${productMetaEnum.id}_view');">编辑</a>
					<a class="delete"
					onclick="deleteproductMetaEnum('/product/productMetaEnum/${productMetaEnum.id}/delete','productMetaEnumdiv');"
					href="javascript:void(0)">删除</a>
				</td>
			</tr>
			<!-- 编辑数据 -->
			<tr id="${productMetaEnum.id}_update" class="update">
				<td colspan="4">
					<form action="/product/productMetaEnum/${productMetaEnum.id}"
						id="update_${productMetaEnum.id}" method="post">
						<input name="_method" type="hidden" value="put" /> <input
							class="text" type="text" name="value"
							value="${productMetaEnum.value}"> <input class="text"
							type="text" name="index" id="editIndex" value="${productMetaEnum.index}">
						<select name="isdefault" class="isnot">
							<c:choose>
								<c:when test="${productMetaEnum.defaultValue}">
									<option value="0">否</option>
									<option value="1" selected="selected">是</option>
								</c:when>
								<c:otherwise>
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</c:otherwise>
							</c:choose>
						</select>
						<button class="save" type="button"
							onclick="ajaxpost('/product/productMetaEnum/${productMetaEnum.id}','update_${productMetaEnum.id}','productMetaEnumdiv','edit')">提交</button>
						<button class="cancel" type="button"
							onclick="visible('${productMetaEnum.id}_view');invisible('${productMetaEnum.id}_update');">取消</button>
					</form>
				</td>
			</tr>

		</c:forEach>

		<tr id="add" class="add">
			<td colspan="4">
				<form action="/product/productMetaEnum" method="post"
					id="addproductMetaEnum">
					<input name="meta" type="hidden" value="${meta}" /> <input
						class="text" type="text" name="value"> <input class="text"
						type="text" name="index" id="index"/> <select name="isdefault" class="isnot">
						<option value="0">否</option>
						<option value="1" selected="selected">是</option>
					</select>
					<button class="save" type="button"
						onclick="ajaxpost('/product/productMetaEnum','addproductMetaEnum','productMetaEnumdiv','add');">提交</button>
					<button class="cancel" type="button" onclick="invisible('add');">取消</button>
				</form>
			</td>
		</tr>

	</table>
	<div id="requestInfo" align="center"
		style="font-size: 15px; font-weight: bold;"></div>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/productmeta/productMetaEnum.js"></script>
</div>