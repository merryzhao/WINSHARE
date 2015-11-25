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
			<a href="-1/tag"><input type="button" value="新建标签"/></a>
			<table class="list-table" id="product_table">
				<c:if test="${! empty promotionTag }">
				<tr>
					<th>标签编号</th>
					<th>促销类型</th>
					<th>标签地址</th>
					<th>创建时间</th>
					<th>操作人</th>
					<th>排序</th>
					<th>操作</th>
				</tr>
					<c:forEach items="${promotionTag}" var="tag">
						<tr>
							<td><a href="${tag.id}/tag">${tag.id}</td>
							<td>${tag.type.name}</td>
							<td><c:if test="${! empty tag.url}"><a href="${tag.url}">${tag.url}</c:if></td>
							<td>${tag.createtime}</td>
							<td>${tag.employee.name}</td>
							<td><input type="text" value="${tag.rank}" onchange="rank(${tag.id},this);" style="width: 30px;"></td>
							<td>
								<c:if test="${tag.available==true}"><a href="#" option="" onclick="available(${tag.id},this,${tag.available});">停用</a></c:if>
								<c:if test="${tag.available==false}"><a href="#" option="" onclick="available(${tag.id},this,${tag.available});">启用</a></c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
		</div>
		</div>
	</div>
</div>
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript">
	function rank(id,$this){
		$this = $($this);
		$this.css('border-color','red');
			$.ajax({
				  type: 'POST',
				  url: "/promotion/rank/tag?format=json",
				  data: {id:id,rank:$this.val(),type:'rank'},
				  success: function(data){
					 if(data.message=='success'){
						 $this.css('border-color','black');
					 }
				  },
				  dataType: "json"
				}); 
	}
	function available(id,$this,available){
		$this = $($this);
		if($this.attr("option")==''){
			$this.attr("option",'1');
			$this.css('color','red');
				$.ajax({
					  type: 'POST',
					  url: "/promotion/rank/tag?format=json",
					  data: {id:id,available:available,type:2},
					  success: function(data){
						 if(data.message=='success'){
							 $this.css('color','#21759B');
							 if(data.promotionTag.available){
							 	$this.html('停用');
							 }else{
								$this.html('启用');
							 }
							 $this.attr("option","");
						 }
					  },
					  dataType: "json"
					});
		}
	}
</script>
</html>

