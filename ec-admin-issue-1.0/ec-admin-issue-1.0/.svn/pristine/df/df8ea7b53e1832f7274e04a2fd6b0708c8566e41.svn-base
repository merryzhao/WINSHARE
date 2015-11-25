<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>新建报表</title>
<style type="text/css">
label.space {
	margin-left: 60px;
}

label.error {
	padding:0.1em;
}
#grid_form input.error,textarea.error{
	padding:0px;
	border: 2px solid red;
}

#Condition_form input.error,select.error {
	padding:0px;
	border: 2px solid red;
}
#Column_form input.error ,select.error {
	padding:0px;
	border: 2px solid red;
}

table.gridOperator-table {
	width: 100%;
	margin-top: 5px;
}

table.gridOperator-table th {
	background: #6293BB;
	height: 30px;
	line-height: 20px;
	color: #fff;
	font-weight: bolder;
	text-align: center;
}

table.gridOperator-table tr {
	height: 30px;
	line-height: 20px;
	border: 1px solid #DFDFDF;
	text-align: center;
}

table.gridOperator-table tr:HOVER {
	background: #ffffe1
}

table.gridOperator-table .type {
	width: 10%;
	min-width: 80px;
	margin-left: 20px;
}

table.gridOperator-table .name {
	width: 10%;
	margin-left: 50px;
}

table.gridOperator-table .operator {
	width: 20%;
	min-width: 120px;
	margin-left: 20px;
}

table.gridupdate-table {
	width: 80%;
	margin-left: 10%;
	margin-bottom: 10%;
}

table.gridupdate-table .name {
	text-align: right;
	width: 10%;
}

table.gridupdate-table .input {
	text-align: left;
	width: 30%;
}

textarea {
	height: 80px;
	width: 80%;
}

div.updateform {
	display: none;
}

div.table {
	width: 80%;
	margin-left: 10%;
	margin-top: 20px;
	margin-bottom: 20px;
}

div.center {
	text-align: center;
}

label.none {
	display: none;
}

select.none {
	display: none;
}

#treeText {
	width: 280px;
	height: 300px;
	max-width: 280px;
	max-height: 300px;
	border: 0px;
	text-align: left;
}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-form.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
			<div id="requestInfo" align="center" style="font-size: 15px; font-weight: bold;"></div>
				<h4>报表编辑</h4>
				<hr>
				<form action="" method="post" id="grid_form">

					<div class="table">
						<table width="100%">
							<tr>
								<td><input type="hidden" name="id" value="${grid.id}">
									<div>
										报表名称：<input type="text" id="name" name="name" value="${grid.name}" /><br>
										<c:choose>
											<c:when test="${grid.paged}">
												<input type="checkbox" name="paged" checked="checked" />
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="paged" />
											</c:otherwise>
										</c:choose>
										是否分页 <label class="space">每页显示数量</label><input type="text"
											id="pageSize" name="pageSize" value="${grid.pageSize}" />
									</div>
								</td>
								<td>
									<div>
										数据源： <select name="dataSource" id="dataSource">
											<c:forEach items="${dataSources}" var="datasource">
												<option <c:if test="${datasource.id==grid.dataSource.id}">selected="selected"</c:if> value="${datasource.id}">${datasource.name}</option>
											</c:forEach>
										</select><br>
										<c:choose>
											<c:when test="${grid.exported}">
												<input type="checkbox" name="exported" checked="checked" />
											</c:when>
											<c:otherwise>
												<input type="checkbox" name="exported" />
											</c:otherwise>
										</c:choose>
										是否可导出文件 <label class="space">每次导出数量</label> <input type="text"
											id="exportSize" name="exportSize" value="${grid.exportSize}" />
									</div>
								</td>
							</tr>
							<tr>
								<td id="tableTree">
								主要sql:<br>
										<textarea rows="3" cols="3"  id="mainSql" name="mainSql">${grid.mainSql}</textarea>
										<br>合计sql:<br>
										<textarea rows="3" cols="3" name="aggregateSql">${grid.aggregateSql}</textarea>
								<td>
									<div>
										 排序sql：<br>
										<textarea rows="3" cols="3" name="orderSql">${grid.orderSql}</textarea>
										<br><br><br><br><br>
										<button type="button" onclick="gridFormSubmit('${grid.id}');">提交</button><br><br>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</form>
				<c:if test="${grid!=null}">
					<div>
						<div class="table" id="condition_div">
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
										<td><label class="none" id="${condition.id}_type">${condition.type}</label>
											<c:choose>
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
											</c:choose> <label class="none" id="${condition.id}_enumeration">${condition.enumeration.id}</label>
										</td>
										<td><label class="none" id="${condition.id}_allowNull">${condition.allowNull}</label>
											<c:if test="${condition.allowNull==true}">
										是
										</c:if> <c:if test="${condition.allowNull!=true}">
										否
										</c:if>
										</td>
										<td id="${condition.id}_defaultValue">${condition.defaultValue}</td>
										<td><label class="none" id="${condition.id}_control">${condition.control}</label>
											<a href="javascript:void(0)；"
											onclick="updateCondition('${condition.id}');">编辑</a> <a
											href="javascript:deleterow(1,${condition.id});">删除</a> <a
											href="javascript:upordown('up',1,${condition.id});">上</a> <a
											href="javascript:upordown('down',1,${condition.id});">下</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>


						<div class="table" id="column_div">
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
										<td><label class="none" id="${column.id}_order">${column.order}</label>
											<c:if test="${column.order==true}">
										是
										</c:if> <c:if test="${column.order!=true}">
										否
										</c:if>
										</td>
										<td><label class="none" id="${column.id}_aggregated">${column.aggregated}</label>
											<c:if test="${column.aggregated==true}">
										是
										</c:if> <c:if test="${column.aggregated!=true}">
										否
										</c:if>
										</td>
										<td id="${column.id}_width">${column.width }</td>
										<td><label class="none" id="${column.id}_ascSql">${column.ascSql}</label>
											<label class="none" id="${column.id}_descSql">${column.descSql}</label>
											<a href="javascript:void(0)；"
											onclick="updateColumn('${column.id}');">编辑</a> <a
											href="javascript:deleterow(2,${column.id});">删除</a> <a
											href="javascript:upordown('up',2,${column.id});">上</a> <a
											href="javascript:upordown('down',2,${column.id});">下</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</c:if>
				<!--  隐藏新建条件div-->
				<div class="updateform" id="ConditionDiv">
					<h5 id="ConditionDivTitle">新建条件</h5>
					<form action="" method="post" id="Condition_form">
						<table class="gridupdate-table">
							<colgroup>
								<col class="name" />
								<col class="input" />
								<col class="name" />
								<col class="input" />
							</colgroup>
							<tr>
								<td>字段名</td>
								<td><input type="text" name="name" id="Condition_name">
								</td>
								<td>参数名</td>
								<td><input type="text" name="parameterName"
									id="Condition_parameterName">
								</td>
							</tr>
							<tr>
								<td>类型</td>
								<td><select name="type" id="Condition_type">
										<option value="">请选择</option>
										<option value="1">字符串</option>
										<option value="2">整数</option>
										<option value="3">浮点数</option>
										<option value="4">日期</option>
										<option value="5">日期时间</option>
										<option value="6">多行数字</option>
										<option value="7">多行文本</option>
										<option value="8">枚举</option>
								</select> <select class="none" name="enumerationId"
									id="Condition_enumeration">
										<option value="">请选择枚举</option>
										<c:forEach var="enumeration" items="${enumerations}">
											<option value="${enumeration.id }">${enumeration.name}</option>
										</c:forEach>
								</select>
								</td>
								<td>默认值</td>
								<td><input type="text" name="defaultValue"
									id="Condition_defaultValue"></td>
							</tr>
							<tr>
								<td>控件</td>
								<td><select name="control" id="Condition_control">
										<option value="">请选择</option>
										<option value="1">文本框</option>
										<option value="2">文本区域</option>
										<option value="3">下拉列表</option>
										<option value="6">单选按钮</option>
										<option value="7">复选框</option>
										<option value="9">日期选择器</option>
										<option value="8">时间选择器</option>
								</select>
								</td>
								<td><input type="checkbox" name="allowNull"
									id="Condition_allowNull">允许空</td>
								<td><input type="hidden" name="id" value=""
									id="Condition_id"> <input type="hidden" name="gridId"
									value="${grid.id }">
								</td>
							</tr>
							<tr>
								<td></td>
								<td colspan="2" align="center">
									<button type="button" onclick="submitform(1);">提交</button>
									<button type="button" id="ConditionDiv_cancel"
										onclick="closed('ConditionDiv');">取消</button>
								</td>
								<td></td>
							</tr>
						</table>
					</form>
				</div>

				<!--  隐藏新建显示字段div-->
				<div class="updateform" id="ColumnDiv">
					<h5 id="ColumnDivTitle">新建显示字段</h5>
					<form action="" method="post" id="Column_form">
						<table class="gridupdate-table">
							<colgroup>
								<col class="name" />
								<col class="input" />
								<col class="name" />
								<col class="input" />
							</colgroup>
							<tr>
								<td>列名</td>
								<td><input type="text" name="name" id="Column_name">
								</td>
								<td>值</td>
								<td><input type="text" name="value" id="Column_value">
								</td>
							</tr>
							<tr>
								<td>列宽度</td>
								<td><input type="text" name="width" id="Column_width">
								</td>
								<td colspan="2"><input type="checkbox" name="order"
									id="Column_order">是否可排序<label class="space"></label> <input
									type="checkbox" name="aggregated" id="Column_aggregated">是否合计</td>
							</tr>
							<tr>
								<td colspan="4">升序SQL：</td>
							</tr>
							<tr>
								<td colspan="4"><textarea rows="1" cols="3" name="ascSql"
										id="Column_ascSql"></textarea></td>
							</tr>
							<tr>
								<td colspan="4">降序SQL：</td>
							</tr>
							<tr>
								<td colspan="4"><textarea rows="1" cols="3" name="descSql"
										id="Column_descSql"></textarea></td>
							</tr>
							<tr>
								<td><input type="hidden" name="id" value="" id="Column_id">
									<input type="hidden" name="gridId" value="${grid.id }">
								</td>
								<td colspan="2" align="center">
									<button type="button" onclick="submitform(2);">提交</button>
									<button type="button" id="ColumnDiv_cancel"
										onclick="closed('ColumnDiv');">取消</button></td>
								<td></td>
							</tr>
						</table>
					</form>
				</div>

			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	    <script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/report/grid.js"></script>
		<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
	<script type="text/javascript" 
		src="${contextPath}/js/report/edit_grid_validate.js"></script>
</body>
</html>