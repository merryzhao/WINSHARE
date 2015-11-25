<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="../snippets/tags.jsp"%>
<div class="showinfo">
	<h4>订单信息</h4>
	<table class="list-table">
		<tr>
			<td align="left">订单号：<label class="delivery-lable">${order.id}</label>
			</td>
			<td align="left">外部订单号：<label class="delivery-lable">${order.outerId}</label>
			</td>
			<td align="left">下单时间：<label class="delivery-lable"><fmt:formatDate type="both" value="${order.createTime}"/></label>
			</td>
		</tr>
		<tr>
			<td align="left">处理状态：<label class="delivery-lable" id="orderStatusLabel">${order.processStatus.name}</label>
			</td>
			<td align="left">处理时间：<label class="delivery-lable"><fmt:formatDate type="both" value="${order.lastProcessTime}"/></label>
			</td>
			<td align="left">渠道：<label class="delivery-lable">${order.channel.name}</label>
			</td>
		</tr>
		<tr>
			<td align="left">配送方式：<label class="delivery-lable"
				id="deliveryType"> <span>${order.deliveryType.name}</span><img
					name="配送方式" alt="编辑" class="edit-selectimg"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label>
			</td>
			<td align="left">支付类型：<label class="delivery-lable">${order.payType.name}</label>
			</td>
			<td align="left">支付状态：<label class="delivery-lable">
					${order.paymentStatus.name}
					</label>
			</td>
		</tr>
		<tr>
			<td align="left">储配方式：<label class="delivery-lable">${order.storageType.name}</label>
			</td>
			<td align="left">供应类型：<label class="delivery-lable">${order.supplyType.name}</label>
			</td>
			<c:if test="${order.transferResult.id != 35002}">
				<td align="left">下传中启：
					<label class="delivery-lable" id="transferLabel" data-val="${order.transferResult.id}"> 
						<c:if test="${order.transferResult.id == 35003}">
							<span id="transferValue">否</span>
						</c:if>
						<c:if test="${order.transferResult.id == 35001}">
							<span id="transferValue">是</span>
						</c:if>
						<img name="是否需要下传中启" title="点击下传中启" alt="编辑" class="edit-transfer" src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> 
					</label>
					
				</td>
			</c:if>
		</tr>
		<tr>
			<td align="left">总码洋：<label class="delivery-lable">${order.listPrice}</label>
			</td>
			<td align="left">总实洋：<label class="delivery-lable">${order.salePrice}</label>
			</td>
			<td align="left">运费：<label class="delivery-lable">${order.deliveryFee}</label>
			</td>
		</tr>
		<tr>
			<td align="left">优惠金额：<label class="delivery-lable">${order.saveMoney}</label>
			</td>
			<td align="left">已支付金额：<label class="delivery-lable">${order.advanceMoney}</label>
			</td>
			<td align="left">还需支付：<label class="delivery-lable">${order.requidPayMoney}&nbsp;&nbsp;
			<c:if test="${order.deliveryType.id == 4 && order.paymentStatus.id != 4001}">
				<c:if test="${order.processStatus.id == 8001 || order.processStatus.id == 8004 || order.processStatus.id ==8005 || order.processStatus.id == 8011}">
		<a href='/order/${order.id}/pay'>登记到款</a>
	</c:if>
	</c:if>
	</label>
			</td>
		</tr>
		<tr>
			<td align="left">发货DC：
				<label class="delivery-lable" id="${order.distributionCenter.dcOriginal.id }">
					${order.distributionCenter.dcOriginal.name}
					<c:if test="${(order.processStatus.id == 8001 || order.processStatus.id == 8002) && order.distributionCenter.dcOriginal.id != 110005}">
						<img name="发货DC" alt="编辑" class="edit-ori-dc" src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" />
					</c:if>
				</label>
			</td>
			<td align="left">出货DC：
				<label class="delivery-lable">
					${order.distributionCenter.dcDest.name}<c:if test="${order.CODOrder}">(${codWarhouse })</c:if>
				</label>
			</td>
			<td align="left">订单类型：
				<label class="" id="${order.supplyType.id }">
					<c:if test="${order.supplyType.id == 13101 }">正常销售订单</c:if>
					<c:if test="${order.supplyType.id == 13102 }">新品预售订单</c:if>
					<c:if test="${order.supplyType.id == 7003 }">快速分拨订单</c:if>
					<c:if test="${order.supplyType.id == 13102 || order.supplyType.id == 7003}">
						<img name="订单类型" alt="编辑" class="edit-orderSupplyType" src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" />
					</c:if>
				</label>
			</td>
		</tr>
		
		<tr>
			<td align="left">承运商：
				<label class="delivery-lable">
					<c:if test="${order.deliveryCompany.id != null}">
					${order.deliveryCompany.company}
					</c:if>
					<c:if test="${order.processStatus.id == 8002}">
						<c:if test="${order.deliveryCompany.id == null}">
							<img name="承运商" alt="编辑" class="edit-delivery-company" src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" />
						</c:if>
					</c:if>
				</label>
			</td>
			<td align="left">
			</td>
			<td align="left">
			</td>
		</tr>

		<c:if test="${order.transferResult.id==35004 || order.transferResult.id==35005}">
			<tr>
				<td align="left">物流中心：<label class="delivery-lable">${order.distributionCenter.dcOriginal.name}</label>
				</td>
				<td align="left">物流系统收货人（客户代码）：<label class="delivery-lable">${order.distributionCenter.remark}</label>
				</td>
			</tr>
		</c:if>
		
	</table>
</div>

<div class="showinfo">
	<h4>客户信息</h4>
	<table class="list-table">
		<tr>
			<td align="left">注册名： <label class="delivery-lable" title="">${order.customer.name}</label>
			</td>
			<td align="left">收货人姓名：<label class="delivery-lable"
				id="consignee-consignee"><span>${order.consignee.consignee}</span><img
					alt="编辑" name="收货人姓名" class="edit-img"
					src="/imgs/orderinfo/edit.jpg" /> </label>
			</td>
			<td align="left">电话：<label class="delivery-lable"
				id="consignee-phone"><span>${order.consignee.phone}</span><img
					name="电话" alt="编辑" class="edit-img" src="/imgs/orderinfo/edit.jpg"
					style="cursor: pointer;" /> </label>
			</td>
		</tr>
		<tr>
			<td align="left">手机 ：<label class="delivery-lable"
				id="consignee-mobile"><span>${order.consignee.mobile}</span><img
					name="手机" alt="编辑" class="edit-img" src="/imgs/orderinfo/edit.jpg"
					style="cursor: pointer;" /> </label>
			</td>
			<td align="left">电子邮件：<label class="delivery-lable"
				id="consignee-email"><span>${order.consignee.email}</span><img
					name="电子邮件" alt="编辑" class="edit-img"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label></td>
			<td align="left">邮编：<label class="delivery-lable"
				id="consignee-zipCode"><span>${order.consignee.zipCode}</span><img
					name="邮编" alt="编辑" class="edit-img" src="/imgs/orderinfo/edit.jpg"
					style="cursor: pointer;" /> </label></td>
		</tr>
		<tr>
			<td align="left">是否需要发票：<label class="delivery-lable"
				id="consignee-needInvoice"> <span
					id="${order.consignee.needInvoice}"> <c:if
							test="${order.consignee.needInvoice==true}">
			     是
			  </c:if> <c:if test="${order.consignee.needInvoice==false}">
			  否
			</c:if> </span><img name="是否需要发票" alt="编辑" class="edit-needi"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label>
			</td>
			 <c:if test="${order.consignee.needInvoice==true&&order.consignee.invoiceTitleType.id==3461}">
			      	<td align="left">发票抬头：<label class="delivery-lable"
				id="consignee-invoiceTitle"><span>${order.consignee.invoiceTitle}</span><img
					name="发票抬头" alt="编辑" class="edit-img"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label>
					<input type="hidden" id="holdTitle" value="${order.consignee.invoiceTitle}">
					<input type="hidden" id="holdNeedTitle" value="true">
					</td>
			  </c:if>
		
			<td align="left">送货时间要求：<label class="delivery-lable"
				id="consignee-deliveryOption"><span>${order.consignee.deliveryOption.name}</span><img
					name="送货时间要求" alt="编辑" class="edit-selectimg"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label></td>
		</tr>
		<tr>
			<td align="left">所在区域 ：<label class="delivery-lable"> <span
					id="countryText">${order.consignee.country.name}</span> <span
					id="provinceText">${order.consignee.province.name}</span> <span
					id="cityText">${order.consignee.city.name}</span> <span
					id="districtText">${order.consignee.district.name}</span> <span
					id="townText">${order.consignee.town.name }</span> <img
					name="所在区域" alt="编辑" class="edit-area"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="3">详细地址： <label class="delivery-lable"
				id="consignee-address"><span>${order.consignee.address}</span><img
					alt="编辑" name="详细地址" class="edit-areaimg"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label></td>
		</tr>


		<tr>
			<td align="left" colspan="3">备注留言： <label class="delivery-lable"
				id="consignee-remark"><span>${order.consignee.remark}</span><img
					name="备注留言" alt="编辑" class="edit-areaimg"
					src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </label>
			</td>

		</tr>


	</table>
</div>

<div class="showinfo">
	<h4>支付信息</h4>
	<table class="list-table">
		<tr>
			<th>支付方式</th>
			<th>支付金额</th>
			<th>是否已支付</th>
			<th>支付时间</th>
		</tr>
		<c:forEach items="${order.paymentList}" var="orderPayment">
			<tr>
				<td><label id="${orderPayment.id}" class="orderPayment">
						<span class="payspan" id="${orderPayment.payment.id}">${orderPayment.payment.name}</span><c:if test="${orderPayment.payment.name!='货到付款'&&!orderPayment.pay}"><img
						name="支付方式" alt="编辑" class="edit-selectimg"
						src="/imgs/orderinfo/edit.jpg" style="cursor: pointer;" /> </c:if></label>
				</td>
				<td>${orderPayment.payMoney}</td>
				<td><c:if test="${orderPayment.pay}" >是</c:if><c:if test="${!orderPayment.pay}" >否</c:if></td>
				<td><fmt:formatDate type="date" value="${orderPayment.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
	</table>
</div>

<div class="showinfo">
	<h4>商品信息</h4>
	<table class="list-table">
		<tr>
			<th>商品编号</th>
			<th>卖家</th>
			<th>商品自编码</th>
			<th>商品名</th>
			<th>码洋</th>
			<th>实洋</th>
			<th>折扣</th>
			<th>实际结算金额</th>
			<th>购买数量</th>
			<th>发货数量</th>
			<th>小计</th>
			<th>仓库</th>
			<th>可用量</th>
			<th>库存</th>
		</tr>
		<c:forEach var="orderItem" items="${order.itemList}" varStatus="index">
			
			<c:choose>
				<c:when test="${orderItem.productSale.supplyType.id==13102 }">
					<tr bgcolor="#00ff2a">
				</c:when>
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>
			
  				<td>${orderItem.productSale.id}</td>
				<td>${orderItem.shop.shopName}</td>
				<td>${orderItem.productSale.outerId}</td>
				<td>${orderItem.productSale.sellName}</td>
				<td>${orderItem.listPrice}</td>
				<td>${orderItem.salePrice}</td>
				<td>${orderItem.discount}</td>
				<td>${orderItem.balancePrice }</td>
				<td>${orderItem.purchaseQuantity}</td>
				<td>${orderItem.deliveryQuantity}</td>
				<td>${orderItem.purchaseQuantity*orderItem.salePrice}</td>
				<td>${order.distributionCenter.dcOriginal.name}</td>
				<td>
					<c:if test="${orderItem.productSale.supplyType.id==13101}">
						<c:forEach var="productSaleStock" items="${orderItem.productSale.productSaleStockVos}" varStatus="status" >
							<c:if test="${productSaleStock.dc == order.distributionCenter.dcOriginal.id }">
							${productSaleStock.stock - productSaleStock.sales}
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${orderItem.productSale.supplyType.id==13102 || order.supplyType.id == 7003}">
					   <c:forEach var="productSaleStock" items="${orderItem.productSale.productSaleStockVos}" varStatus="status" >
					   	<c:if test="${productSaleStock.dc == order.distributionCenter.dcOriginal.id }">
							${productSaleStock.virtual - productSaleStock.sales}
							</c:if>
						</c:forEach>
					</c:if>
				</td>
				<td>
					<c:if test="${orderItem.productSale.supplyType.id==13101}">
						<c:forEach var="productSaleStock" items="${orderItem.productSale.productSaleStockVos}" varStatus="status" >
							<c:if test="${productSaleStock.dc == order.distributionCenter.dcOriginal.id }">
							${productSaleStock.stock}
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${orderItem.productSale.supplyType.id==13102 || order.supplyType.id == 7003}">
					   <c:forEach var="productSaleStock" items="${orderItem.productSale.productSaleStockVos}" varStatus="status" >
					   	<c:if test="${productSaleStock.dc == order.distributionCenter.dcOriginal.id }">
							${productSaleStock.virtual}
							</c:if>
						</c:forEach>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td>合计</td>
			<td>订单码洋   ${order.listPrice}</td>
			<td>订单实洋${order.salePrice}</td>
			<td>购买数量  ${order.purchaseQuantity}</td>
			<td>发货数量  ${order.deliveryQuantity}</td>
			<td>订单结算金额   ${order.totalBalancePrice}<td>
			<td></td>
			<td></td>
		</tr>
	</table>
</div>
<br>
<!-- 订单基本信息修改的dialog -->
<div id="update-text">
	<center>
		<label id="textname"></label> <input type="text" id="updatetext"
			name="updatetext" /><br /> <br /> <input type="button"
			id="updateSubmit" value="确定" /> <input type="button"
			id="updateCancle" value="取消" />
	</center>
</div>
<!-- 是否需要发票修改的dialog -->
<div id="update-needinvoice">
	<center>
	   <table>
	   	<tr>
		   	<td align="right"><label style="display: none;" id="textname"></label>是否需要发票：</td>
		   	<td align="left"><label id="textname"></label> <input id="yes" name="needinvoice"
			type="radio" value="1" />是 <input id="no" name="needinvoice"
			type="radio" value="0" />否 </td>
	   	</tr>
	  	<tr id="theTitleType">
		   	<td align="right">抬头类型：</td>
		   	<td align="left"><select name="invoiceTitleType" id="invoiceTitleType">
 				<option value="3460">个人</option>
 				<option value="3461">公司</option>
			</select> </td>
	   	</tr>
		<tr id="comName">
		   	<td align="right">发票抬头：</td>
		   	<td align="left"><input class="valid" type="text" name="invoiceTitle" id="invoiceTitle" /></td>
	   	</tr>
	   </table>
			 <input type="button"
			id="needSubmit" value="确定" /> <input type="button" id="needCancle"
			value="取消" />
	</center>
</div>
<!-- 是否需要下传中启 -->
<div id="update-transfer">
	<center>
	   <table>
	   	<tr>
		   	<td align="right"><label style="display: none;" id="textname"></label>是否需要下传中启：</td>
		   	<td align="left">
		   		<label id="textname"></label> 
			   	<input id="yes" name="transfer" type="radio" value="true" />是 
			   	<input id="no" name="transfer" type="radio" value="false" />否 
		   	</td>
	   	</tr>
	   </table>
	   <input type="button" id="transferSubmit" value="确定" /> 
	   <input type="button" id="transferCancle" value="取消" />
	</center>
</div>


<!-- 订单备注留言和详细地址修改的dialog -->
<div id="update-textarea">
	<center>
		<label id="textareaname"></label><br />
		<textarea id="updatetextarea" name="updatetextarea"></textarea>
		<br /> <br /> <input type="button" id="areaSubmit" value="确定" /> <input
			type="button" id="areaCancle" value="取消" />
	</center>

</div>

<!-- 下拉列表框中的信息修改的dialog -->
<div id="update-select">
	<center>
		<label id="selectname"></label> <label id="updateselect"></label><br />
		<br /> <input type="button" id="selectSubmit" value="确定" /> <input
			type="button" id="selectCancle" value="取消" />
	</center>
</div>
<!-- 区域修改的dialog -->
<div id="update-area">
	<center>
		<label>所在区域</label> <select areaLevel="country" id="country"
			name="country" style="width: 100px">
		</select> <select areaLevel="province" name="province" id="province">
			<option value="-1">请选择省份</option>
		</select> <select areaLevel="city" id="city" name="city">
			<option value="-1">请选择城市</option>
		</select> <select areaLevel="district" id="district" name="district">
			<option value="-1">请选择区县</option>
		</select> <select areaLevel="town" id="town" name="town">
			<option value="-1">请选择乡镇</option>
		</select><br /> <br /> <input type="button" id="addSubmit" value="确定" /> <input
			type="button" id="addCancle" value="取消" />
	</center>
</div>

<!-- 修改DC信息 -->
<div id="ori-dc-edit">
	<center>
		<table class="ori-dc-table">
			<tr>
				<td><label id=>发货DC：</label></td>
				<td>
					<select id="dc" name="oriDc">
					</select>
				</td>
			</tr>
		</table>
		<input type="button" id="oriDcSubmit" value="确定" /> 
	   	<input type="button" id="oriDcCancle" value="取消" />
	</center>
</div>

<!-- 修改订单类型 -->
<div id="orderSupplyType-edit">
	<center>
		<table class="orderSupplyType-table">
			<tr>
				<td><label id=>订单类型：</label></td>
				<td>
					<select name="orderSupplyType">
						<option value="13101">正常销售订单</option>
					</select>
				</td>
			</tr>
		</table>
		<input type="button" id="orderSupplyTypeSubmit" value="确定" /> 
	   	<input type="button" id="orderSupplyTypeCancle" value="取消" />
	</center>
</div>

<!-- 修改承运商 -->
<div id="delivery-company-edit">
	<center>
		<table class="delivery-compamy-table">
			<tr>
				<td><label id="">承运商：</label></td>
				<td>
					<select class="deliveryCompany" name="deliveryCompany">
						<c:forEach items="${deliveryCompanies}" var="deliveryCompany">
							<option value="${deliveryCompany.id}">${deliveryCompany.company}</option>
						</c:forEach>
				  </select>
				</td>
			</tr>
		</table>
		<input type="button" id="deliveryCompanySubmit" value="确定" /> 
	   	<input type="button" id="deliveryCompanyCancle" value="取消" />
	</center>
</div>



<!-- 发货的dialog -->
<div id="order-delivery">
	<center>
		<table class="delivery-table">
			<tr>
				<th>配送公司：</th>
				<td><select id="deliveryCompany">
						<c:forEach items="${deliveryCompanies}" var="deliveryCompany">
							<option value="${deliveryCompany.id}">${deliveryCompany.company}</option>
						</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<th>运单号：</th>
				<td><input id="orderDeliveryCode" /></td>
			</tr>
			<tr id="deliveryProduct">
				<th>商品</th>
				<th>数量</th>
			</tr>
			<c:forEach items="${order.itemList}" var="orderItem">
				<tr>
					<input type="hidden" name="productId" value="${orderItem.productSale.product.id}"/>
					<td>${orderItem.productSale.product.name}</td>
					<td><input name="productQuantity" value="${orderItem.purchaseQuantity}"/></td>
				</tr>
			</c:forEach>
		</table>
		<button onclick="deliverySubmit('${order.id}');">确定</button>
		<button id="delivery-close">取消</button>
	</center>
</div>
<div>
	<c:choose>
		<c:when
			test="${order.processStatus.id==8001 && order.payType.id==5002}">
			<button id="checkBtn" onclick="checkOrder('${order.id}')">审核</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">审核</button>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${order.processStatus.id==8002 && order.transferResult.id == 35003}">
			<button binding="picking" id="picking" onclick="picking('${order.id}')">配货</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">配货</button>
		</c:otherwise>
	</c:choose>

    <c:choose>
		<c:when
			test="${order.canbeCancel}">
			<button binding="cancelOrderinfo">取消</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">取消</button>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${order.canBeArchived}">
			<button id="nullifyBtn" onclick="nullifyOrder('${order.id}')">作废</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">作废</button>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${order.transferResult.id == Code.EC2ERP_STATUS_NEW && order.processStatus.id == Code.ORDER_PROCESS_STATUS_PICKING}">
			<button id="erpInterceptBtn" onclick="erpIntercept('${order.id}',${order.distributionCenter.dcDest.id })">物流拦截</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">物流拦截</button>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${order.transferResult.id == Code.EC2ERP_STATUS_NEW && order.processStatus.id == Code.ORDER_PROCESS_STATUS_PICKING}">
			<button id="reOrderBtn" onclick="reorder('${order.id}')">重新转单</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">重新转单</button>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when
			test="${order.canBeCopy}">
			<button id="copyBtn" onclick="copyOrder('${order.id}')">复制新订单</button>
		</c:when>
		<c:otherwise>
			<button disabled="disabled">复制新订单</button>
		</c:otherwise>
	</c:choose>

	<c:if
		test="${order.processStatus.id ==8001 && order.payType.id == 5001 && order.deliveryType.id != 4}">
		<button
			onclick="javascript:window.location.href='/order/${order.id}/pay'">登记到款</button>
	</c:if>
	
	<c:if test="${order.deliveryType.id == 4 && order.paymentStatus.id != 4001}">
		<c:if test="${order.processStatus.id == 8001 || order.processStatus.id == 8004 || order.processStatus.id ==8005 || order.processStatus.id == 8011}">
			<button onclick="javascript:window.location.href='/order/${order.id}/pay'">登记到款</button>
		</c:if>
	</c:if>

	<c:if 
		test="${(order.processStatus.id == 8004 || order.processStatus.id == 8011) && order.deliveryType.id == 4}">
		<button
			onclick="javascript:if(confirm('确认收货?')){$.get('/order/completedorder/${order.id}',{'format':'json'},function(msg){alert(msg.message);window.location.reload();},'json');}">确认收货</button>
	</c:if>

	<button>打印</button>
 	<c:choose>
		<c:when test="${order.processStatus.id ==8003  && order.transferResult.id == 35003}">
			<button id="delivery-button" type="button" onclick="">发货</button>
		</c:when>
		<c:otherwise>
			<button id="delivery-button" type="button" disabled="disabled">发货</button>
		</c:otherwise>
	</c:choose>

    <input type="button"
           <c:if test="${order.supplyType.id != Code.PRODUCT_SALE_SUPPLY_TYPE_BOOKING }">disabled="disabled"</c:if>
           value="解除预售"
           onclick="javascript:if(confirm('解决预售?')){$.post('/order/cancelPresell/${order.id}',{'format':'json'},function(msg){alert(msg.message);window.location.reload();},'json');}"/>
</div>


<div id="cancelOrder-editor">
	<form action="/order/cancel" method="post">
		取消订单
		<input type="hidden" name="id" value="${order.id}">
		<fieldset>
			<br>
			<div class="center">
				<select name="processStatus">
					<option value="8006">客户取消</option>
					<option value="8008">系统取消</option>
				</select>
			</div>
			<br> <br>
			<div class="center">
				<button type="submit">保存</button>
			</div>
		</fieldset>
	</form>
</div>
<input type="hidden" id="orderId" value="${order.id}"></input>
<input type="hidden" id="icountry" value="${order.consignee.country.id}"></input>
<input type="hidden" id="iprovince"
	value="${order.consignee.province.id}"></input>
<input type="hidden" id="icity" value="${order.consignee.city.id}"></input>
<input type="hidden" id="idistrict"	value="${order.consignee.district.id}"></input>
<input type="hidden" id="itown"	value="${order.consignee.town.id}"></input>
<input type="hidden" id="updateid" value=""></input>
<input type="hidden" id="updatevalue" value=""></input>
<input type="hidden" id="editShow" value="${order.processStatus.id}"></input>
<input type="hidden" id="areaId" value="${order.consignee.town.id }"></input>