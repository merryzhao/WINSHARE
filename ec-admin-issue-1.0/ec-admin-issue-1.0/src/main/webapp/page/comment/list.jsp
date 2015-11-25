<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>店铺评论管理</title>
<style type="text/css">
textarea {
	width: 120px;
	height: 50px;
	margin-bottom: -5px;
}
input{vertical-align:middle;margin:0px;}
</style>
<!-- 引入JS -->
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
				<div id="tabs">
					<div id="info">
						${success}
						<c:if test="${!empty info }">商品编号:${info }的商品修改分类失败!其余修改成功!</c:if>
					</div>
					<div id="search">
						<form action="/comment/list" method="post" id="findform">
							<div>
								商品编号:
								<input type="text" name="productSaleId" value="${form.productSaleId }" />
							    商品名称：<input type="text" name="productName" value="${form.productName }" />
							<select id="channel" name="channel" >
						      	<option value="104" <c:if test="${empty form.channel || form.channel == '104'}">selected="selected"</c:if>>文轩评论</option>
						      	<option value="110" <c:if test="${form.channel == '110'}">selected="selected"</c:if>>天猫评论</option>
						    </select>
								<input type="submit"
									style="margin-left: 5px; margin-top:0px;" value="查询"/>
							</div>
							<div>
								&nbsp;&nbsp;&nbsp;&nbsp;评论人:
								<input type="text" name="customerName" value="${form.customerName }" />
								评论内容:
								<input type="text" name="content" value="${form.content}" />
								
								提交时间： <input type="text" name="commentTimeStart"
									bind="datepicker" value="<fmt:formatDate value="${form.commentTimeStart}" pattern="yyyy-MM-dd"/>"/>~<input name="commentTimeEnd" type="text"
									bind="datepicker" value="<fmt:formatDate value="${form.commentTimeEnd}" pattern="yyyy-MM-dd"/>"/>
							</div>
						</form>
					</div>
					<div id="result">
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
						<c:if test="${list!=null}">
						<form action="/comment/batchdel" method="post">
							<table class="list-table">
								<tr>
									<th><input type="checkbox"  class="selectall"></th>
									<th>商品编号</th>
									<th>商品名称</th>
									<th>评论标题</th>
									<th>评论详情</th>
									<th>评分</th>
									<th>评论时间</th>
									<th>评论人</th>
									<th>操作</th>
								</tr>
								<c:if test="${fn:length(list)!=0}">
									<tbody>
									<tr><td colspan="9"><input type="button" value="批量删除" id="batchDel"></td></tr>
									<c:forEach items="${list}" var="customerComment">
										<tr class="data">
											<td><input type="checkbox" value="${customerComment.id}" name="commentid" class="selectCommentItem"></td>
											<td><a
												href="http://www.winxuan.com/product/${customerComment.productSale.id}"
												target="_blank">${customerComment.productSale.id}</a></td>
											<td>${customerComment.productSale.name}</td>
											<td>${customerComment.title}</td>
											<td width="200px"><a href="/comment/goReply?id=${customerComment.id }" class="edit"><c:if test="${fn:length(customerComment.content)>40}">${fn:substring(customerComment.content, 0, 40)}...</c:if><c:if test="${fn:length(customerComment.content)<=40}">${customerComment.content}</c:if></a></td>
											<td>${customerComment.rank.rank }</td>
											<td><fmt:formatDate value="${customerComment.commentTime}" type="both"/></td>
											<td><c:if test="${empty customerComment.nickName}">${customerComment.customer.name }</c:if><c:if test="${not empty customerComment.nickName}">${customerComment.nickName }</c:if></td>
											<td><c:if test="${form.channel == '104'}"><a href="/comment/goReply?id=${customerComment.id }" >回复</a></c:if> <a
												href="javascript:void(0);" onclick="del(${customerComment.id},this);" >删除</a>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</c:if>
								<c:if test="${fn:length(list)==0}">
									<tr>
										<td colspan="8">暂无数据</td>
									</tr>
								</c:if>
								<tr>
									<th><input type="checkbox" class="selectall"></th>
									<th>商品编号</th>
									<th>商品名称</th>
									<th>评论标题</th>
									<th>评论详情</th>
									<th>评分</th>
									<th>评论时间</th>
									<th>评论人</th>
									<th>操作</th>
								</tr>
							</table>
							</form>
						</c:if>
						<c:if test="${pagination!=null}">
							<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script>
		function del(id,obj){
			var con = confirm("确认删除?");
			if(con){
				$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					url : '/comment/delete?format=json',
					data: 'id='+id,
					error : function() {
						alert("删除失败");
					},
					success : function(data) { 
						if(data.result==1){
							//$(obj).parent().parent().remove();
							$('#findform').submit();
						}
					}
				});
			}
		}
		$(function(){
			$(".selectall").click(function(){
				if($(this).attr("checked")){
					$("input[name='commentid']").attr("checked", "checked");
					$(".selectall").attr("checked", "checked");
				}else{
					$("input[name='commentid']").removeAttr("checked");
					$(".selectall").removeAttr("checked");
				}
			});
			$("#batchDel").click(function(){
				if(!confirm("确定指删除?")){
					return;
				}
				var vals = new Array();
				var a = $("input[name='commentid']:checked");
				$.each(a,function(i, n){
					vals[i] = "commentid=" + $(n).val();
				});
				$.ajax({
					type:"POST",
					url:"/comment/batchdel?" + vals.join("&"),
					success:function(e){
						$("#findform").submit();
					}
				});
			});
		});
	</script>
</body>
</html>