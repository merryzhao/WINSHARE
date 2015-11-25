<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<jsp:include page="../snippets2/meta.jsp">
	<jsp:param value="webmanage" name="type" />
</jsp:include>
<title>商品信息合并</title>
</head>
<body>
		<!-- 引入top部分 -->
		<%@include file="../snippets2/frame-top.jsp"%>
		<div class="container-fluid">
         <div class="span3">
         <!-- 引入left菜单列表部分 -->
         <%@include file="../snippets2/frame-left-product.jsp"%>
           </div>
			<!-- 核心内容部分div -->
				<c:forEach items="${data}" var="item">
					<c:set value="${item.key}" var="productSale"></c:set>
					<c:set value="${item.value}" var="productSaleList"></c:set>
					<div class="span5">
					 <div class="span5" >电子书(原九月网商品)</div>
					  <div class="span5">
					<table class="table table-bordered table-striped" style="font-size: 13px;">
						<tr>
							<td>商品名称:
							 <input type="radio" name="yuep" value="${productSale.id}" checked="checked">
							
							</td>
							<td><a	href="http://www.9yue.com/productdetail/${productSale.remark}.html"	target="_black">${productSale.name}</a></td>
						</tr>
						<tr>
							<td>条形码:</td>
							<td>${productSale.product.barcode}</td>
						</tr>
						<tr>
								<td>上下架状态:</td>
								<td>${productSale.saleStatus.name}</td>
							</tr>
						
						<tr>
							<td>码洋:</td>
							<td>${productSale.product.listPrice}</td>
						</tr>
						<tr>
							<td>作者:</td>
							<td>${productSale.product.author}</td>
						</tr>
						<tr>
							<td>出版社:</td>
							<td>${productSale.product.manufacturer}</td>
						</tr>
						<tr>
							<td>出版时间:</td>
							<td>${productSale.product.productionDate}</td>
						</tr>
						<tr>
							<td>图片:</td>
							<td><img src="${productSale.imageUrl}"
								alt="${productSale.name}" /></td>
						</tr>
					</table>
					</div>
				 </div>
				 <div class="span5">
				  <div class="span5">文轩网商品</div>
				 <div class="span5 pre-scrollable" style="max-height:500px;">
					 <c:forEach items="${productSaleList}" var = "ps" varStatus="status">
					 <table class="table table-bordered table-striped" style="font-size: 13px;">
							<tr>
								<td>商品名称:
								
								<c:if test="${status.index==0}">
								 <input type="radio" name="winxuanP" value="${ps.id}" checked="checked">
								</c:if>
								
								<c:if test="${status.index!=0}">
								 <input type="radio" name="winxuanP" value="${ps.id}">
								</c:if>
								
								
								</td>
								<td><a
									href="http://www.winxuan.com/product/${ps.id}"
									target="_black">${ps.name}</a></td>
							</tr>
							<tr>
								<td>条形码:</td>
								<td>${ps.product.barcode}</td>
							</tr>
							
							<tr>
								<td>上下架状态:</td>
								<td>${ps.saleStatus.name}</td>
							</tr>
							
							<tr>
								<td>码洋:</td>
								<td>${ps.product.listPrice}</td>
							</tr>
							<tr>
								<td>作者:</td>
								<td>${ps.product.author}</td>
					    	</tr>
							
							<tr>
								<td>出版社:</td>
								<td>${ps.product.manufacturer}</td>
							</tr>
							<tr>
								<td>出版时间:</td>
								<td>${ps.product.productionDate}</td>
							</tr>
							<tr>
								<td>图片:</td>
								<td><img src="${ps.imageUrl}"
									alt="${ps.name}" /></td>
							</tr>
						</table>
					 </c:forEach>
				 </div>
				 
				 <div class="span5 pagination-right">
				 <br/>
				 
				 <c:if test="${not empty prevId}">
				 <a href="http://console.winxuan.com/product/recoverData?psId=${prevId}" class="btn btn-small btn-primary prev" data-content="恢复前一条数据" data-original-title="快捷键,字键1,非小键盘。">恢复</a>
				 </c:if>
				 <a href="javascript:;" class="btn btn-small btn-primary merage" data="true" data-content="对选中的商品进行合并" data-original-title="快捷键F1">合并</a>
				 <a href="javascript:;" class="btn btn-small btn-warning notmerage" data="false" data-content="该商品没有匹配项不进行合并" data-original-title="快捷键F2">不合并</a>
				  <a href="javascript:;" class="btn btn-small btn-inverse ignore"  data-content="优先处理一对一的的商品,文轩网商品过多的情况下 可以暂时不处理,放置最后处理." data-original-title="快捷键F4">忽略</a>
				 </div>
				 </div>
				</c:forEach>
				   <div class="span3">
				           使用说明:<br>1、电子书可能对应多个文轩网商品.<br>2、核对完毕后,点击商品名称前的按钮<br>3、然后点击合并,或者按下F1<br>4、不合并的商品按下F2<br>5、鼠标移至下方按钮会有说明<br>6、使用数字键1恢复上一条记录
				   </div>
			</div>
			
			<div id="modalbackdroptrue" class="modal hide fade in" style="display: none;">
			            <div class="modal-body">
					      处理中...<img alt="" src="http://www.winxuan.com/css/images/loading_16x16.gif"/>
			           </div>
			</div>
			 
			<div class="modal" id="modelConfirm" style="display: none;">
			  <div class="modal-header">
			    <a class="close" data-dismiss="modal">×</a>
			    <h3>确认?</h3>
			  </div>
			  <div class="modal-body msg">
			    <p>对话框内容</p>
			  </div>
			  <div class="modal-footer">
			    <a href="#" class="btn ">取消</a>
			     <a href="#" class="btn btn-primary">确定</a>
			  </div>
			</div>
			
			
</body>

<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/js/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"	src="${contextPath}/js/product/produtMerge.js"></script>
</html>