<%@page import="java.util.ArrayList"%>
<%@page import="com.winxuan.ec.model.category.Category"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<html>
<head>
<title>修改商品信息</title>

<style type="text/css">
.selectStyle {
	width: 60;
}

.buttonstyle {
	width: 85px;
	height: 25px;
}

.textareaStyle {
	width: 350px;
	height: 60px;
	padding: 0px;
}

#add-body {
	margin-top: 30px;
	margin-left: 30px;
	width: 650px;
	height: 650px;
	border: 1px solid #DFDFDF;
}

label.error {
	padding: 0.1em;
}

.berror {
    border: 0px solid red;
    padding: 0px;
    margin: 0px;
    color: red;
}
textarea {
	width: 50px;
	height: 50px;
	vertical-align:top;
	margin-top:-2px;
}

#proEditForm input.error {
	padding: 0px;
	border: 1px solid red;
}

#preview {
	display: inline-block;
	width: 100px;
	height: 100px;
	background-color: #CCC;
}
</style>
<%@include file="../snippets/meta.jsp"%>
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

				<div id="add-body">
					<h4>修改商品信息</h4>
					<form:form commandName="proEditForm" name="proEditForm" id="proEditForm"
						action="${contextPath}/product/update" method="post">
						<token:token></token:token>
						<form:hidden path="id"/>
						<div>
							<table class="list-table1">
								<tr>
									<td align="right">销售名称：</td>
									<td align="left">
										<form:input path="sellName" />
										<label><form:errors path="sellName"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right" >所属分类:</br>
									<a onclick="window.open('/category/view?id=${productSale.product.id}&sid=${productSale.id}');">【修改分类 】</a> 
									</td>
									<td>
										<ul>
											<c:forEach items="${productSale.product.categories }" var="category" varStatus="s">
											<li>
												<input type="button" value="X" onclick="$(this).parent().remove()" />
												<input type="hidden" name="categories" value="${category.id}" />
												<c:set var="parent" value="${ category.parent}" scope="request"></c:set>
												<%
													Category category;
													ArrayList<String> list = new ArrayList<String>();
													while(null != (category = (Category)request.getAttribute("parent"))){
														list.add(category.getName());
														request.setAttribute("parent",category.getParent());
													}
													for(int i=list.size()-1;i>=0;i--){
														out.write(list.get(i) + "-->");
													}
												%>${category.name }
												<c:if test="${not s.last }">
													<br />
												</c:if>
											</li>
											</c:forEach>
										</ul>
									</td>
								</tr>
								<tr>
									<td align="right">MC分类型：</td>
									<td align="left">
										<c:if test="${productSale.product.mcCategory != null}" >
											${mcCategory.name }
										</c:if>
									</td>
								</tr>
								<tr>
									<td align="right">促销语：</td>
									<td align="left">
										<form:input path="subheading" />
										<label><form:errors path="subheading"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">副标题：</td>
									<td align="left"><form:input path="promValue"/>
										<label><form:errors path="promValue"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">定价：</td>
									<td align="left">
									<c:if test="${isBh==true}">
										<form:input path="listPrice" maxlength="25" />
											<label><form:errors path="listPrice"></form:errors>
										</label>
									</c:if>
									<c:if test="${isBh==false}">
											<label>${proEditForm.listPrice}</label>
											<form:hidden path="listPrice"/>
									</c:if>
									</td>
								</tr>
								<tr>
									<td align="right">储配方式：</td>
									<td align="left"><form:select path="storageType" items="${storageType}" id="storageType" multiple="" itemLabel="name"
												itemValue="id" />
  									</td>
								</tr>
								<tr>
									<td align="right">上下架状态：</td>
									<td align="left">
									<c:if test="${proEditForm.saleStatus==13004||proEditForm.saleStatus==13005}">
									    <label>${productSale.saleStatus.name}</label>
										<form:hidden path="saleStatus" />
									</c:if>
								<c:if test="${proEditForm.saleStatus!=13004&&proEditForm.saleStatus!=13005}">
									<form:select path="saleStatus"   id="saleStatus" multiple="">
										<form:option value="13001" label="下架"></form:option>
										<form:option value="13002" label="上架销售"></form:option>
										<form:option value="13003" label="EC停用"></form:option>
									</form:select>
								</c:if>
									</td>
								</tr>
                                <tr>
                                    <td align="right">备注：</td>
                                    <td align="left">
                                        <div id="remark_checkbox_div">
                                            <select id="remark_checkbox">
                                                <option value="0">选择</option>
                                                <option value="清退供应商">清退供应商</option>
                                                <option value="商品质量问题">商品质量问题</option>
                                                <option value="商品版权问题">商品版权问题</option>
                                                <option value="商品改版停采">商品改版停采</option>
                                                <option value="禁止销售">禁止销售</option>
                                                <option value="库存不足,出现缺货系统停用">库存不足,出现缺货系统停用</option>
                                            </select>
                                        </div>
                                        <textarea style="height:60px;" name="remark" id="remark"></textarea>
                                    </td>
                                </tr>
								<c:if test="${productSale.supplyType.id==13101 }">
								<!-- 正常销售 自储 -->
								<c:if test="${productSale.storageType.id==6002||productSale.storageType.id==6003 }">
								<tr>
									<td align="right">EC库存量：</td>
									<td align="left"><form:input id="stockQuantity" path="stockQuantity"/>
										<label><form:errors path="activeQuantity"></form:errors>
									</label>
									</td>
								</tr>
								<tr>
									<td align="right">EC库存占用量：</td>
									<td align="left">
										<label id="saleQuantity">${productSale.saleQuantity }</label>
									</td>
								</tr>
								<tr>
									<td align="right">EC库存可用量：</td>
									<td align="left"><form:input id="activeQuantity" path="activeQuantity"/>
										<label><form:errors path="activeQuantity"></form:errors>
									</label>
									</td>
								</tr>
								</c:if>
								<!-- 正常销售 代储 -->
								<c:if test="${productSale.storageType.id==6001 }">
								<tr>
									<td align="right">EC库存量：</td>
									<td align="left">${productSale.stockQuantity}
									</td>
								</tr>
								<tr>
									<td align="right">EC库存占用量：</td>
									<td align="left">
										<label id="saleQuantity">${productSale.saleQuantity }</label>
									</td>
								</tr>
								<tr>
									<td align="right">EC库存可用量：</td>
									<td align="left">${productSale.stockQuantity-productSale.saleQuantity }
									</td>
								</tr>
								</c:if>
								</c:if>
							</table>
		<c:if test="${productSale.supplyType.id==13102&&productSale.booking!=null}">
			<div id="productBookingDialog">
				<h4>预售信息</h4>
				<div>
					<c:forEach var="productSaleStock" items="${productSale.productSaleStockVos}">
						<c:if test="${productSaleStock.virtual != 0}">
							<label style="margin-left:50px;">预售DC：</label>${productSaleStock.dcdetail.name}<br/>
							<label style="margin-left:25px;">虚拟库存量：</label>
							<c:if test="${productSale.product.complex == 1}">${productSaleStock.virtual}<input value="${productSaleStock.virtual}" name="stockQuantity" type="hidden"/>(预售套装书的库存量不能直接更改)<br/>
							</c:if>
							<c:if test="${productSale.product.complex == 0}" >
								<input value="${productSaleStock.virtual}" name="stockQuantity"/><br/>
							</c:if>
							<label style="margin-left:25px;">库存占用量：</label>${productSaleStock.sales}<br/>
							<label style="margin-left:25px;">库存可用量：</label>${productSaleStock.virtual - productSaleStock.sales}<br/>
						</c:if>
					</c:forEach>
					
					<label>预售起止时间*：</label> 
					<c:if test="${productSale.product.complex == 1}">
					<form:input path="bookStartDate" type="hidden" name="bookStartDate"
						 readonly="true" id="bookStartDate" />${proEditForm.bookStartDate}&nbsp;&nbsp;-<form:input path="bookEndDate"  
						name="bookEndDate" type="hidden" id="bookEndDate"/>&nbsp;&nbsp;${proEditForm.bookEndDate}
					</c:if>
						
						<c:if test="${productSale.product.complex == 0}">
						<form:input path="bookStartDate" type="text" name="bookStartDate"
						 readonly="true" id="bookStartDate" />-<form:input path="bookEndDate" type="text" 
						name="bookEndDate" id="bookEndDate"/>
						</c:if>
						<label  id="dateError" class="berror"></label>
				</div>
				<div>
					<table>
					<tr>
					<td><label style="margin-left:36px;">预售描述：</label></td>
					<td>
					<form:textarea style="width:300px;height:100px;" path="bookDescription"  />  </td>
					</tr>
					</table>
				</div>
			</div>
		</c:if>
							<div>
								<c:if test="${productSale.supplyType.id==13102 }">
 									 <input style="width: 60px;" id="sbm"
										type="button" value="提交" />
 								</c:if>
 								<c:if test="${productSale.supplyType.id!=13102 }">
 									 <input style="width: 60px;"
										type="submit" value="提交" />
 								</c:if>
										<input type="reset" style="width: 60px;" value="重置" />
							</div>
 						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
     <input type="hidden" name="supplyType" id="supplyType" value="${productSale.supplyType.id}">
    <input type="hidden" name="oldQuantity" id="oldQuantity" value="${proEditForm.activeQuantity}">
    <input type="hidden" name="oldSaleStatus" id="oldSaleStatus" value="${proEditForm.saleStatus}">
    <input type="hidden" name="noEditType" id="noEditType" value="${noEditType}">
   	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.mousewheel.min.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/order/jquery.easing.1.3.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.min.js"></script>

	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.defaults.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery.validate.methods.min.js"></script>
 	<script type="text/javascript"
		src="${contextPath}/js/product/product_edit.js"></script>

 </body>
</html>