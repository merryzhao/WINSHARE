<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
				 	显示字段 <a href="javascript:void(0)；" onclick="newColumn();">新建</a>
							<table class="gridOperator-table">
								<colgroup>
									<col class="name" />
									<col class="name" />
									<col class="type" />
									<col class="type" />
									<col class="type" />
									<col class="operator" />
								</colgroup>
								<tr>
									<th>列名</th>
									<th>值</th>
									<th>是否可排序</th>
									<th>是否合计列</th>
									<th>列宽度</th>
									<th>操作</th>
								</tr>
								<c:forEach var="column" items="${grid.columnList}" varStatus="i">
									<tr>
										<td id="${column.id}_name">${column.name }</td>
										<td id="${column.id}_value">${column.value }</td>
										<td ><label class="none" id="${column.id}_order">${column.order}</label>
										<c:if test="${column.order==true}">
										是
										</c:if>
										<c:if test="${column.order!=true}">
										否
										</c:if>
										</td>
										<td ><label class="none" id="${column.id}_aggregated">${column.aggregated}</label>
										<c:if test="${column.aggregated==true}">
										是
										</c:if>
										<c:if test="${column.aggregated!=true}">
										否
										</c:if>
										</td>
										<td id="${column.id}_width">${column.width }</td>
										<td><label class="none" id="${column.id}_ascSql">${column.ascSql}</label>
											<label class="none" id="${column.id}_descSql">${column.descSql}</label>
											<a href="javascript:void(0)；"
											onclick="updateColumn('${column.id}');">编辑</a> 
											<a href="javascript:deleterow(2,${column.id});">删除</a>
											<a href="javascript:upordown('up',2,${column.id});">上</a>
											<a href="javascript:upordown('down',2,${column.id});">下</a>
											</td>
									</tr>
								</c:forEach>
							</table>