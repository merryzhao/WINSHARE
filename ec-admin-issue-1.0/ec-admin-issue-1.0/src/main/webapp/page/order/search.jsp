<!DOCTYPE html>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<title>订单查询</title>
<%@include file="../snippets/meta.jsp"%>
<link rel="stylesheet" href="${contextPath}/css/order/order.css">
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-order.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div class="ui-widget-content">
				   <div class="ui-widget-content"> 
					<h4>按订单号查询</h4>
					<form:form action="/order" method="post" commandName="orderForm">
						<table>
							<tr>
								<td width="10%"></td>
								<td width="7%"></td>
								<td width="20%"></td>
								<td width="10%"></td>
								<td width="10%"></td>
								<td width="43%"></td>
							</tr>
							<tr>
							    <td></td>
								<td align="center" class="text-weight" align="right">订单号*:</td>
								<td colspan="2" align="left" style="padding: 10px;"><textarea
										name="orderId" class="width-200 height-50"></textarea></td>
								<td align="center" colspan="1"><input type="submit"
									value="查  询" id='btnQuery' onclick="$(this).css('display','none');setTimeout(function(e){$('#btnQuery').css('display','');},2000);" /></td>
								<td><span style="color:red">${message }</span></td>
							</tr>
						</table>
					<div class="ui-widget-content">
					<h4>组合条件查询</h4>
						<table>
							<tr>
								<td width="10%"></td>
								<td width="20%"></td>
								<td width="7%"></td>
								<td width="20%"></td>
								<td width="43%"></td>
							</tr>
							<tr class="timeTr1">
								<td colspan="1" class="hight-30" align="right"><select
									name="time1" class="time1" onchange="change(this);" style="width:70px">
										<option id="time_select" value="1">创建时间*</option>
										<option value="2">更新时间*</option>
										<option value="3">支付时间*</option>
										<option value="4">发货时间*</option>
								</select>
								</td>
								<td colspan="4" align="left" style="padding: 10px;">
								<input type="radio" name="Date1" value ="1" onclick = "clickRadio(this,1)" class="day"/>最近一天   
								<input type="radio" name="Date1" value ="2" onclick = "clickRadio(this,2)" class="week"/>最近一周  
								<input type="radio" name="Date1" value ="3" onclick = "clickRadio(this,3)" class="month"/>最近一月   
								<input class="starttime" name="startCreateTime" bind="datepicker" width="10px"  style="width:100px" onclick="clearRadio(this)"> ~<input class="endtime"
									name="endCreateTime" bind="datepicker"  style="width:100px" onclick="clearRadio(this)"> <input
									class="addbutton" type="button" value="添    加"
									onclick="addDate(this);"><b class="ts"
									style="display: none">不需要该条件可不填</b>
								</td>
							</tr>
							<tr>
								<td class="text-weight" align="right">订单状态*：</td>
								<td class="hight-30" colspan="4" align="left" style="padding: 10px;">
									<c:forEach var="ps" items="${processStatus}">
										<input name="processStatus" id="processStatus" type="checkbox"
											value="${ps.id}" />${ps.name}
  									</c:forEach>
								</td>
							</tr>
							<tr>
								<td align="right" class="text-weight">订单来源：</td>	
								<td colspan="4">
								<button type="button" id="add_channel" >添加渠道</button><span id="channelChecked"></span>
						       </td>
							</tr>
							<tr>
								<td align="right" class="hight-30" align="right">
								<select name="code" class="code" style="width:70px">
										<option value="1">商品编码</option>
										<option value="2">SAP编码</option>
										<option value="3">ISBN编码</option>
								</select></td>
								<td colspan="2" align="left" style="padding: 10px;">
									<textarea name="product" class="width-200 height-50" id="productCode"></textarea></td>		
								<td align="right" class="text-weight">外部订单号*：</td>
								<td colspan="2" align="left" style="padding: 10px;"><textarea
										name="outerId" class="width-200 height-50"></textarea></td>															
							</tr>
							<tr>
								<td class="text-weight" align="right">店         铺：</td>
								<td class="hight-30" colspan="6" align="left" style="padding: 10px;">
									<c:forEach var="shop" items="${shops}" varStatus="status">  
										<input name="shop" id="shop" type="checkbox"
											value="${shop.id}" />${shop.shopName} &nbsp;&nbsp;&nbsp;
											<c:if test="${status.index%5 == 0 &&status.index !=0}"><br/></c:if>
  									</c:forEach>
								</td>			
							</tr>
							<tr>
								<td class="text-weight" align="right">承运商：</td>
								<td class="hight-30" colspan="6" align="left" style="padding: 10px;">
									<c:forEach var="company" items="${deliveryCompanys}" varStatus="status">  
										<input name="deliveryCompany" id="deliveryCompany" type="checkbox"
											value="${company.id}" />${company.company} &nbsp;&nbsp;&nbsp;
											<c:if test="${status.index%5 == 0 &&status.index !=0}"><br/></c:if>
  									</c:forEach>
								</td>
							</tr>
							</table>
							</div>
							<div class="ui-widget-content">
							<div id="accordion">
								<h3><a href="#">其他查询条件</a></h3>
						     <div>
							<table>
							<tr>
								<td width="10%"></td>
								<td width="20%"></td>
								<td width="7%"></td>
								<td width="20%"></td>
								<td width="43%"></td>
							</tr>
							<tr>
								<td class="hight-30 text-weight" align="right">是否虚拟订单：</td>
								<td class="hight-30" colspan="2" align="left"
									style="padding: 10px;">
										<input name="virtual" id="virtual" type="checkbox"
											value="0" />普通订单
										<input name="virtual" id="virtual" type="checkbox"
											value="1" />虚拟订单	
								</td>
							</tr>
							<tr>
								<td class="hight-30 text-weight" align="right">支付状态：</td>
								<td class="hight-30" colspan="2" align="left"
									style="padding: 10px;"><c:forEach var="ps"
										items="${paymentStatus}">
										<input name="paymentStatus" id="paymentStatus" type="checkbox"
											value="${ps.id}" />${ps.name}</c:forEach>
								</td>
								<td align="right" class="text-weight">销售类型：</td>
								<td colspan="2" class="hight-30" align="left" style="padding: 10px;">
									<c:forEach var="saleType" items="${saleTypes}">
										<input name=saleType id="saleType" type="checkbox"
											value="${saleType.id}" />${saleType.name}
									</c:forEach>
								</td>	
							</tr>
							<tr>
							<td class="hight-30 text-weight" align="right">是否有发票：</td>
								<td class="hight-30" align="left" style="padding: 10px;" colspan="2">有<input
									type="radio" name="needInvoice" value="true" /> 无<input
									type="radio" name="needInvoice" value="false" /><br /></td>			
									<td class="hight-30 text-weight" align="right">商品属性：</td>
								<td colspan="2" align="left" style="padding: 10px;"><c:forEach
										var="sortType" items="${sortTypes}">
										<input name="sort" id="sort" type="checkbox"
											value="${sortType.id}" />${sortType.name}
 										 </c:forEach>
								</td>			
							</tr>
							
							<tr>
								<td align="right" class="text-weight">储存方式：</td>
								<td colspan="2" align="left" style="padding: 10px;"><c:forEach
										var="storageType" items="${storageTypes}">
										<input name="storageType" id="storageType" type="checkbox"
											value="${storageType.id}" />${storageType.name}
										</c:forEach>
								</td>
								<td align="right" class="hight-30 text-weight">用户等级：</td>
								<td class="hight-30" align="left" style="padding: 10px;">
										<input name="gradeType" id="gradeType" type="checkbox"
											value="0" />普通会员 
											<input name="gradeType" id="gradeType" type="checkbox"
											value="1" />银牌会员
											<input name="gradeType" id="gradeType" type="checkbox"
											value="2" />金牌会员
								</td>
							</tr>
							<tr>
							   <td class="hight-30 text-weight" align="right">支付方式：</td>
								<td class="hight-30" colspan="4" align="left" style="padding: 10px;">
									<c:forEach var="paymentType" items="${paymentTypes}">
										<input name="paymentType" id="paymentType" type="checkbox"
											    value="${paymentType.id}" />${paymentType.name}
                               		 </c:forEach>
								</td>	
							</tr>
							<tr>
								<td align="right" >
									<select class="userName" style="width:60px">
										<option value="1">注册名</option>
										<option value="2">收货人</option>
									</select></td>		
							<td colspan="2" align="left" style="padding: 10px;"><textarea
										name="registerName" class="width-200 height-50" id="name"></textarea></td>		
								<td align="right" class="hight-30" align="right">
								<select name="phoneCode" class="phoneCode" style="width:70px">
										<option value="1">固定电话</option>
										<option value="2">手机</option>
								</select></td>
								<td colspan="2" align="left" style="padding: 10px;">
									<textarea name="phone" class="width-200 height-50" id="phoneNumber"></textarea></td>
														
							</tr>
							<tr>
								<td class="hight-30" align="right"><select name="price"
									class="price">
										<option value="1">码 洋</option>
										<option value="2">实 洋</option>
								</select>
								</td>
								<td class="hight-30" colspan="2" align="left"
									style="padding: 10px;"><input name="startListPrice"
									class="price1" style="width:70px"/>~<input name="endListPrice" class="price2" style="width:70px"/>
								</td>	
								<td class="text-weight" align="right">配送方式：</td>
								<td class="hight-30" colspan="4" align="left" style="padding: 10px;">
									<c:forEach var="deliveryType" items="${deliveryTypes}">
										<input name="deliveryType" id="deliveryType" type="checkbox"
											value="${deliveryType.id}" />${deliveryType.name}
  									</c:forEach>
								</td>							
							</tr>
							
							<tr>
								<td class="text-weight" align="right">原始DC：</td>
								<td class="hight-30" align="left" style="padding: 10px;" colspan="2">
								<c:forEach var="dc" items="${dcList}">
									<input name="dcoriginal" id="dcoriginal" type="checkbox" value="${dc.id}" />${dc.name}
								</c:forEach>
								</td>	
								<td class="text-weight hight-30" align="right">实际发货DC：</td>
								<td class="hight-30" align="left" style="padding: 10px;">
									<c:forEach var="dc" items="${dcList}">
										<input name="dcdest" id="dcdest" type="checkbox" value="${dc.id}" />${dc.name}
									</c:forEach>
								</td>								
							</tr>
							<tr>
								<td align="right" class="text-weight">配送区域：</td>
								<td colspan="3" style="padding: 10px;">
								<select areaLevel ="country" style="width:120px">
	   								<option value="-1">请选择国家</option>
            					</select>
								<select areaLevel="province">
										<option value="-1">请选择省份</option>
								</select> <select areaLevel="city">
										<option value="-1">请选择城市</option>
								</select> <select areaLevel="district" name="district">
										<option value="-1" name="district">请选择区县</option>
								</select><select areaLevel="town" name="town">
										<option value="-1" name="town">请选择区县</option>
								</select></td>
								
								<td  align="left" ><b>收货人姓名：</b><input type="text" name="customerName" /></td>
								
										
							</tr>
							<tr>
								<td align="right" class="text-weight">交寄单*：</td>
								<td colspan="2" align="left" style="padding: 10px;"><textarea
										name="deliveryCode" class="width-200 height-50"></textarea></td>			
							</tr>					
						</table>	
					        </div>
					        </div>
					        </div>
					
						<div class="ui-widget-content">
						<table>
							<tr>
								<td class="hight-30 text-weight" align="right">显示条件：</td>
								<td colspan="5" align="left" style="padding: 10px;"><input
									name="selectRow" type="checkbox" value="id" checked="checked" />订单号
									<input name="selectRow" type="checkbox" value="processStatus"
									checked="checked" />订单状态 <input name="selectRow"
									type="checkbox" value="paymentStatus" checked="checked" />到款状态
									<input name="selectRow" type="checkbox" value="paymentType"
									checked="checked" />支付方式 <input name="selectRow"
									type="checkbox" value="createTime" />下单时间 <input
									name="selectRow" type="checkbox" value="deliveryTime" />发货时间 <input
									name="selectRow" type="checkbox" value="lastProcessTime" />更新时间
									<input name="selectRow" type="checkbox" value="deliveryType"
									checked="checked" />配送方式 <input name="selectRow"
									type="checkbox" value="name" />注册名 <input name="selectRow"
									type="checkbox" value="consignee" checked="checked" />客户姓名 <input
									name="selectRow" type="checkbox" value="phone"
									checked="checked" />电话 <input name="selectRow" type="checkbox" value="mobile"
									checked="checked" />手机 <input name="selectRow" type="checkbox"
									value="area" checked="checked" />区域 
									</tr>
									<tr><td></td><td colspan="5" align="left" style="padding: 10px;"><input name="selectRow"
									type="checkbox" value="address" checked="checked" />									
									详细地址 <input
									name="selectRow" type="checkbox" value="zipCode" />邮编 <input
									name="selectRow" type="checkbox" value="listPrice" />码洋 <input
									name="selectRow" type="checkbox" value="salePrice" checked="checked" />实洋
									<input name="selectRow" type="checkbox" value="requidPayMoney" />应付金额
									<input name="selectRow" type="checkbox" value="deliveryListPrice" />发货码洋
									<input name="selectRow" type="checkbox" value="deliverySalePrice" />发货实洋
									<input name="selectRow" type="checkbox" value="deliveryQuantity" />发货数量
									<input name="selectRow" type="checkbox" value="outerid" />外部交易号
									<input name="selectRow" type="checkbox" value="deliveryCode" />交寄单号
									<input name="selectRow" type="checkbox" value="deliveryCompany" />承运商
									<input name="selectRow" type="checkbox" value="remark" />备注
									<input name="selectRow" type="checkbox" value="grade" />客户等级			
									</td>		
							</tr>
							<tr><td></td><td colspan="5" align="left" style="padding: 10px;">
									<input name="selectRow"type="checkbox" value="savemoney" />优惠金额
							         <input name="selectRow"type="checkbox" value="paymentTime"/>支付时间
							         <input name="selectRow"type="checkbox" value="purchaseQuantity" />购买数量
							         <input name="selectRow"type="checkbox" value="isReturnOrder" />是否有退换货
							         <input name="selectRow" type="checkbox"  value="outofstockoption" />缺货选择
							         <input name="selectRow" type="checkbox" value="channel" />渠道
							         <input name="selectRow" type="checkbox" value="dcoriginal" />出货DC
							         <input name="selectRow" type="checkbox" value="dcdest" />发货DC
									</td>
							</tr>
							<tr>
								<td align="right" class="hight-30 text-weight">每页显示：</td>
								<td colspan="2" align="left" style="padding: 10px;"><select
									name="size">
										<option value="50">50</option>
										<option value="200">200</option>
										<option value="1000">1000</option>
								</select>
								</td>
							</tr>
							<tr>
								<td align="center" colspan="6"><input type="submit"
									value="查      询" /></td>
							</tr>
						</table>
						</div>
					</form:form>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div id="channelDiv">
		<ul id="channel_tree" class="tree"></ul>
		<br />
		<button type=button onclick="insertNodes()" id=getChecktree>确定</button>
	</div>
	<%@include file="../snippets/scripts.jsp"%>	
	<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/tree/channel_tree.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/area/areaevent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/order/search.js"></script>			
	<script type="text/javascript">
	
	</script>
</body>
</html>