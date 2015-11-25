<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
		输入条件 <a href="javascript:void(0)；" onclick="newCondition();">新建</a>
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
									<th>字段名称</th>
									<th>参数名称</th>
									<th>类型</th>
									<th>允许空</th>
									<th>默认值</th>
									<th>操作</th>
								</tr>
								<c:forEach var="condition" items="${grid.conditionList}"
									varStatus="i">
									<tr>
										<td id="${condition.id}_name">${condition.name}</td>
										<td id="${condition.id}_parameterName">${condition.parameterName}</td>
										<td ><label class="none"
											id="${condition.id}_type">${condition.type}</label> <c:choose>
												<c:when test="${condition.type==1}">
										字符串
										</c:when>
												<c:when test="${condition.type==2}">
										整数
										</c:when>
												<c:when test="${condition.type==3}">
										浮点数
										</c:when>
												<c:when test="${condition.type==4}">
										日期
										</c:when>
												<c:when test="${condition.type==5}">
										日期时间
										</c:when>
												<c:when test="${condition.type==6}">
										多行数字
										</c:when>
												<c:when test="${condition.type==7}">
										多行文本
										</c:when>
										<c:when test="${condition.type==8}">
										枚举
										</c:when>
											</c:choose> 
											<label class="none" id="${condition.id}_enumeration">${condition.enumeration.id}</label>
											</td>
										<td><label class="none" id="${condition.id}_allowNull">${condition.allowNull}</label>
											<c:if test="${condition.allowNull==true}">
										是
										</c:if> <c:if test="${condition.allowNull!=true}">
										否
										</c:if></td>
										<td id="${condition.id}_defaultValue">${condition.defaultValue}</td>
										<td><label class="none" id="${condition.id}_control">${condition.control}</label>
											<a href="javascript:void(0)；"
											onclick="updateCondition('${condition.id}');">编辑</a> <a
											href="javascript:deleterow(1,${condition.id});">删除</a> <a
											href="javascript:upordown('up',1,${condition.id});">上</a> <a
											href="javascript:upordown('down',1,${condition.id});">下</a></td>
									</tr>
								</c:forEach>
							</table>