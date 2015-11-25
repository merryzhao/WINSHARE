<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
 <%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户投诉管理</title>
</head>
<body>
		<div class="frame-main-inner">
			<!-- 核心内容部分div -->
			 
			<div id="search" >
					<form:form action="/complaint/list" method="post" commandName="complainForm">
						<div>
							订单号: <input type="text" name="orderId" /> 
							用户账号: <input type="text" name="customer" />
							是否回复：<select name="state">
										<option value="0">全部</option>
										<option value="70002" <c:if test="${state==70002}">selected="selected"</c:if>>已回复</option>
										<option value="70001" <c:if test="${state==70001}">selected="selected"</c:if>>未回复</option>
									</select>
						提交时间：<input type="text" name="startCreateTime" bind="datepicker" />~<input name="endCreateTime" type="text" bind="datepicker" />
							<input type="submit" style="margin-left: 5px; margin-top: 0px;" value="查询" />
						</div>
					
						<div>														
						</div>
					</form:form>
				</div>	 
			<div id="result">
			
			
	    <c:if test="${pagination.count > 0}"><winxuan-page:page pagination="${pagination}" bodyStyle="javascript" /></c:if>
				<table class="list-table">
							<tr>
								<th width="">序号</th>
												
								<th width="">投诉内容</th>								
								<th width="">提交时间</th>
								<th width="">客户名称</th>
								<th width="">状态</th>
								<th width="">操作</th>
							</tr>
							<form action="/complaint/deleteComplaint" method="post">
							<c:forEach items="${list}" var="complaint" varStatus="status">
							<tr>
								<td>${status.index+1 }</td>
								
								<td>${complaint.content }</td>
								<td><fmt:formatDate value="${complaint.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/></td>
								<td>${complaint.customer.name }</td>
								<td><c:if test="${complaint.state.id==70001}">未回复</c:if>
								<c:if test="${complaint.state.id==70002}">已回复</c:if>
								</td>
									
								<td><a href="/complaint/goReply?id=${complaint.id }" >查看</a>/
								<a href="javascript:;"
											onclick="del(${complaint.id},this);">删除</a></td>
							</tr>
							</c:forEach>
							</form>
							<c:if test="${fn:length(list)==0}">
									<tr>
										<td colspan="8">---- &nbsp;&nbsp;键入查询条件&nbsp;&nbsp; ----</td>
									</tr>
							</c:if>
							
							<tr>
								<th width="">序号</th>
												
								<th width="">投诉内容</th>								
								<th width="">提交时间</th>
								<th width="">客户名称</th>
								<th width="">状态</th>
								<th width="">操作</th>
							</tr>
					</table>
				</div>
			</div>
	<script>
		function del(id,obj){
			var con = confirm("确认删除?");
			if(con){
			var url = '/complaint/deleteComplaint?format=json';
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