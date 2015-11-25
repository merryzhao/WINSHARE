<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>周期参数.</title>

<link type="text/css" href="../../css/redmond/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link type="text/css" href="../../css/global.css" rel="stylesheet" />
<script type="text/javascript" src="../../js/replenishment/cycle.js"></script>
<style type="text/css">
label.red {
	color: red;
}

input.long {
	width: 98%;
}

label.error {
	border: none;
	padding: 0.1em;
	color: red;
	font-weight: normal;
	background: none;
	padding-left: 16px;
}

td label.error {
	display: none !important;
}
#uploadPanel{
	margin:20px 0;
}
#uploadbtn{
	padding: 2px 10px;
}
</style>
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
		<h4>周期导入</h4>
		<div>
			<div>
				<p>说明:</p>
				<ul>
					<li>1. 格式只能使用excel2003兼容格式, 即后缀是xls的excel文件</li>
					<li>2. 样例模板<a href="http://console.winxuan.com/excel/replenishment/mrcycle.xls">下载</a></li>
					<li></li>
					<li></li>
				</ul>
			</div>
			<div id="uploadPanel">
				<form action="/replenishment/mrCycleExcelImport" method="post" enctype="multipart/form-data" id="uploadForm">
					<table>
						<tr>
							<td>文件:<input type="file" name="file"></td>
							<td><input type="submit" value="提交" id="uploadbtn"/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div style="color: red">
			<p>
				<c:if test="${msg == 'success'}">
					导入成功！
				</c:if>
				<c:if test="${msg == 'error'}">
					操作失败！${errMsg}
				</c:if>
			</p>
		</div>
		<div>
			<table class="list-table">
					<tbody>
						<tr>
							<td colspan="7" align="left"  style="padding-left:20px;"><strong style="font: 16px;" >MC分类：</strong>
							<input type="text" name="mc" id="mc" style="height: 25px;"/>
							<input type="button" value="条件查询" onclick="cycle.findStatus();"/></td>
						</tr>
						<tr>
							<th>编号</th>
							<th>mc分类</th>
							<th>销售周期</th>
							<th>补货周期</th>
							<th>在途周期</th>
							<th>DC</th>
							<th>创建时间</th>
							<th>操作</th>	
						</tr>
						<c:forEach var="t" items="${MrCycle }">
							<tr id="tr_list${t.id}">
								<td>${t.id }</td>
								<td>${t.mcCategory.id }</td>
								<td>${t.saleCycle }</td>
								<td>${t.replenishmentCycle }</td>
								<td>${t.transitCycle}</td>
								<td>${t.dc.name}</td>
								<td>
									<fmt:formatDate value="${t.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								
								<td>
									<input type="button" value="修改" onclick="cycle.updateStatus(${t.id});"/>
									<input type="button" value="删除" onclick="cycle.deleteStatus(${t.id});"/>
								</td>
							</tr>
						</c:forEach>
						<tr>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
							<th></th>
						</tr>
					</tbody>
				</table>
		</div>
		<c:if test="${not empty pagination}">	
				<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
		</c:if>
	</div>
	
</div>
	<!-- 修改界面start -->
		<div id="save_cycle" style="display: none;height: 100%;width: 100%;">
			<form id="form" action="/replenishment/saveMrCycle" method="POST">
				<table  style="width: 100%;height: 100%;border-collapse: collapse;">
					<tr height="30">
						<td><strong style="font: 16px;">mc分类：</strong></td>
						<td><input type="text" name="mcCategory.id" id="mcCategoryId" style="height: 25px;" /></td>
						<td><strong style="font: 16px;">销售周期：</strong></td>
						<td><input type="text" name="saleCycle" id="saleCycle" style="height: 25px;" /></td>
					</tr>
					<tr height="30">
						<td><strong style="font: 16px;">补货周期：</strong></td>
						<td><input type="text" name="replenishmentCycle" id="replenishmentCycle" style="height: 25px;" /></td>
						<td><strong style="font: 16px;">在途周期：</strong></td>
						<td><input type="text" name="transitCycle" id="transitCycle" style="height: 25px;" /></td>
					</tr>
					<tr height="30">
						<td colspan="4" height="30" align="center">
							<input type="hidden" id="cycleId" name="id" />
							<input style="width: 50px" type="submit" value="完成" id="saveButton" onclick="cycle.submit()"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
</div>
</body>
</html>