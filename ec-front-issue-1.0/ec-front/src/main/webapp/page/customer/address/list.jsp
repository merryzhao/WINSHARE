<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-收货地址</title>
<jsp:include page="../../snippets/version2/meta.jsp" flush="true" >
	<jsp:param name="type" value="acc_order" />
</jsp:include>
<style type="text/css">
	.consignee fieldset{padding:10px 5px;}
	.consignee fieldset div{margin:5px;text-align:center;}
	.consignee p {clear:both;}
	.consignee p em{color:red;marign:0 4px;height:30px;line-height:30px;}
	.consignee p label{display:block;float:left;width:100px;text-align:right;margin:0 5px;}
	.consignee input.less,.consignee input.more{border:1px solid #ccc;padding:3px;margin:0 4px;}
	.consignee input.more{width:300px;}
	.consignee input.less{width:200px;}
	.consignee div button{padding:4px;}
</style>
</head>

<body>
<%@include file="../../snippets/version2/header.jsp" %>
<div class="layout">
    <div class="your_path cl">你现在的位置： <span><a href="http://www.winxuan.com/">文轩网</a> &gt; 我的帐户</span></div>
    <jsp:include page="/page/left_menu.jsp">
	<jsp:param name="id" value="3_6" />
</jsp:include>
    <div class="right_box">
        <h3 class="myfav_tit margin10">我的收货地址</h3>
        <h4><b class="fb">现有收货地址</b> （<a class="red haveline fb" href="javascript:;" bind="addAddress">新增收货地址</a>）</h4>
        
        <c:forEach var="address" items="${addresses}" varStatus="status">
        	<c:set var="classAdd">
        		address_box
        		<c:if test="${address.usual}">yellow_bg</c:if>
        		<c:if test="${!status.last}">bot_dash</c:if>
        	</c:set>
        	<div class="${classAdd}" addressId="${address.id }">
					<p class="margin10">
						<span class="fr">
							<c:choose>
				        		<c:when test="${address.usual}">
				        			<span name="defaultAddress">默认地址</span>
				        		</c:when>
				        		<c:otherwise>
				        			<a class="link1" href="javascript:;" bind="setDefaultAddress">设置为默认地址</a>
				        		</c:otherwise>
				        	</c:choose>
							<a class="modify_but" href="javascript:;" bind="updateAddress">修改</a><a
							class="modify_but" href="javascript:;" bind="deleteAddress">删除</a>
						</span><b class="fb">地址${status.count}</b>
					</p>
					<table class="address_info" width="100%" border="0" cellspacing="0" cellpadding="0">
	             <tr>
	                 <td width="16%"><p class="txt_right">收货人：</p></td>
	                 <td width="84%">${address.consignee}</td>
	             </tr>
	             <tr>
	                 <td><p class="txt_right">详细地址：</p></td>
	                 <td>${address.country.name}
	                 <%--国外的地址,省市县都是一个名字,用不着显示,比如,日本 --%>
	                 <c:if test="${address.country.name!=address.province.name}">
	                 ${address.province.name} ${address.city.name} ${address.district.name} 
	                 </c:if>
	                 <c:choose>
	                 	<c:when test="${not empty address.town && address.town.id != 0 && address.country.name!=address.province.name}">${address.town.name }</c:when>
	                 	<c:otherwise>
	                 		<c:if test="${address.country.name!=address.province.name }"><a class="modify_but" href="javascript:;" bind="updateAddress">请完善地址信息</a></c:if>
	                 	</c:otherwise>
	                 </c:choose>
	                 <c:if test="${not empty address.town && address.town.id != 0}"></c:if>
	                 ${address.address}  ${address.zipCode }</td>
	             </tr>
	             <tr>
	                 <td><p class="txt_right">手机号码：</p></td>
	                 <td>${address.mobile }</td>
	             </tr>
	             <tr>
	                 <td><p class="txt_right">固定电话：</p></td>
	                 <td>${address.phone }</td>
	             </tr>
	             <tr>
	                 <td><p class="txt_right">电子邮件：</p></td>
	                 <td>${address.email }</td>
	             </tr>
	         </table>
	        </div>
        </c:forEach>
    </div>
    <div class="hei10"></div>
</div>
<%@include file="../../snippets/version2/footer.jsp" %>
<div class="tab_box" name="addressWindow" style="display:none;width: 700px">
	<h3><a class="close" href="javascript:;">close</a>收货地址管理</h3>
	<form name="consigneeForm" class="consignee">
		<fieldset>
			<p><label><em>*</em> 收货人</label><input type="text" class="less" name="consignee"/> <span name="info">123321</span></p>
			<p><label><em>*</em> 地区</label>

				<select name="country.id" style="margin:0 4px;" areaLevel="country"></select>
				<select name="province.id" areaLevel="province"><option value="-1">请选择</option></select>
				<select name="city.id" areaLevel="city"><option value="-1">请选择</option></select>
				<select name="district.id" areaLevel="district"><option value="-1">请选择</option></select> 
				<select name="town.id" areaLevel="town"><option value="-1">请选择</option></select> 
				<span name="info"></span>
			</p>
			<p>
				<label><em>*</em> 街道地址</label>
				<input type="text" class="more" name="address"/> 
				<span name="info"></span>
			</p>
			<p>
				<label><em>*</em> 邮编</label>
				<input type="text" class="less" name="zipCode"/> 
				<span name="info"></span>
			</p>
			<p>
				<label><em>*</em> 手机号码</label>

				<input type="text" class="less" name="mobile"/>  
				<span name="info">用于发货或送货通知</span>
			</p>
			<p>
				<label><em>*</em> 固定电话</label>
				<input type="text" class="less" name="phone"/> 
				<span name="info">用于送货通知</span>

			</p>
			<p>
				<label><em>*</em> 电子邮箱</label>
				<input type="text" class="less" name="email"/> 
				<span name="info">用于接收订单执行状态提醒邮件</span>
			</p>
			<p><label>设为默认地址</label><input type="checkbox" name="usual"/></p>

			<div><button name="submit" type="button">保存收货地址</button></div>
		</fieldset>
	</form>
</div>

<script src="${serverPrefix}js/address.js?${version}"></script>
</body>
</html>
