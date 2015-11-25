<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<%@include file="../snippets/tags.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>团购申请管理</title>
<style>
.nullify {
	float: left
}

td.name,.status {
	float: right
}
</style>
</head>
<%response.addHeader("Cache-Control", "no-cache");

response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT"); %>
<body>
	<div>
		<a href="goList">返回上一页</a>
	</div>
	<br>
	<table>
		<tr>
			<td class="status">
			公司名称:
			<c:if test="${roupShoppingInfo.companyName==null}">
			没有填写公司名称
			</c:if>
			<c:if test="${roupShoppingInfo.companyName!=null}">
			${roupShoppingInfo.companyName}
			</c:if>
			状态:<c:if test="${groupShoppingInfo.state.id==60001 }">待审核</c:if> 
					<c:if test="${groupShoppingInfo.state.id==60002 }">已审核</c:if>
					<c:if test="${groupShoppingInfo.state.id==60003 }">已作废</c:if>
			咨询人:${groupShoppingInfo.name } 联系电话:${groupShoppingInfo.phone }
			
			</td>
		</tr>
	</table>
	<br />
	<br />
	<p>${groupShoppingInfo.content }</p>
	<br>
	<br>
	<c:if test="${groupShoppingInfo.state.id==60001 }">
		<a href="javascript:;" onclick="pass(${groupShoppingInfo.id},this);">通过</a>/
		<a href="javascript:;" onclick="nullify(${groupShoppingInfo.id},this);">作废</a>
	</c:if>
	<c:if test="${groupShoppingInfo.state.id!=60001 }">
		该信息已做处理
	</c:if>
	<script>
	function pass(id,obj){
		var con = confirm("确认通过？");
		if(con){
			var url = "/groupshop/goUpdatePass";
			$.ajax({
				type : 'GET',
				url : url,
				data : "id="+id,
				success:function(data){
					location.reload();
				
				}
			
			})
		}
	}
	function nullify(id,obj){
		var con = confirm("确认作废？");
		if(con){
			var url = "/groupshop/goUpdateNullify";
			$.ajax({
				type : 'GET',
				url : url,
				data : "id="+id,
				success:function(data){
					location.reload();
				}
			
			})
		}
	}
	</script>
	
</body>
</html>