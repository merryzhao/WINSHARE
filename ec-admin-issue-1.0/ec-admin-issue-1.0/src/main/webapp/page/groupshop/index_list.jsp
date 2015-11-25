<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>团购申请管理</title>
</head>
<body>

	<div class="frame-main-inner">
		<!-- 核心内容部分div -->
		<div id="search" >
					<form action="/groupshop/search" method="post" commandName="form">
						<div>
							&nbsp;&nbsp;&nbsp;&nbsp;公司名称: <input type="text" name="companyName" value="${form.companyName }"/> 
							是否审核：<select name="state">
										<option value="0">全部</option>
										<option value="60001" <c:if test="${state==60001}">selected="selected"</c:if>>待审核</option>
										<option value="60002" <c:if test="${state==60002}">selected="selected"</c:if>>已审核</option>
										<option value="60003" <c:if test="${state==60003}">selected="selected"</c:if>>已作废</option>
									</select>
							提交时间：<input type="text" name="startTime" bind="datepicker" />~
							<input type="text" name="endTime" bind="datepicker" />
							<input type="submit" style="margin-left: 5px; margin-top: 0px;" value="查询" />	 
						</div>
					</form>
			</div>	
			<div id="result">
		 <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="javascript" /></c:if>
		<table class="list-table">
			<tr>
				<th width="">公司名称</th>
				<th width="">咨询人</th>
				<th width="">咨询时间</th>
				<th width="">状态</th>
				<th width="">查看并操作</th>
			</tr>
			<c:forEach items="${list}" var="groupShop" varStatus="status">
				<tr>
					<td>${groupShop.companyName }</td>
					<td>${groupShop.name }</td>
					<td><fmt:formatDate value="${groupShop.createTime}"
							type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:if test="${groupShop.state.id==60001 }">待审核</c:if> 
						<c:if test="${groupShop.state.id==60002 }">已审核</c:if>
						<c:if test="${groupShop.state.id==60003 }">已作废</c:if>
					</td>
					<td><a href="/groupshop/goDetail?id=${groupShop.id} ">查看</a>/
						<a href="javascript:;"
											onclick="del(${groupShop.id},this);">删除</a></td>
				</tr>
			</c:forEach>
			<c:if test="${fn:length(list)==0}">
				<tr>
					<td colspan="8">---- &nbsp;&nbsp;暂无数据&nbsp;&nbsp; ----</td>
				</tr>
			</c:if>

			<tr>
				<th width="">公司名称</th>
				<th width="">咨询人</th>
				<th width="">咨询时间</th>
				<th width="">状态</th>
				<th width="">详情并操作</th>
			</tr>
		</table>
		</div>
	</div>
	<script>
		function del(id,obj){
			var con = confirm("确认删除?");
			if(con){
			var url = '/groupshop/goDelete?format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : url,
				data: "id="+id,
				error : function() {	
				},
				success : function(data) { 
					if(data.result==1){
						$(obj).parent().parent().remove();
					}
				}
			});
			}
		}
	</script>
</body>
</html>