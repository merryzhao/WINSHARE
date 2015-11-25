<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>Insert title here</title>

<script type="text/javascript">
function nextStep(){
 	document.getElementById("norderId").innerHTML=document.getElementById("orderId").innerHTML;
	document.getElementById("ninvoiceTpye").innerHTML=document.getElementById("invoiceTpye")
	                                        .options[document.getElementById("invoiceTpye")
	                                        .selectedIndex].text;
	document.getElementById("ninvoiceTitle").innerHTML=document.getElementById("invoiceTitle").value;
	document.getElementById("nmoney").innerHTML=document.getElementById("money").value;
	document.getElementById("nconsignee").innerHTML=document.getElementById("consignee").value;
	document.getElementById("nmobile").innerHTML=document.getElementById("mobile").value;
	document.getElementById("nemail").innerHTML=document.getElementById("email").value;
	document.getElementById("nzipCode").innerHTML=document.getElementById("zipCode").value;
	document.getElementById("nreceiveTime").innerHTML=document.getElementById("receiveTime").value;
	document.getElementById("nemail").innerHTML=document.getElementById("email").value;
	document.getElementById("narea").innerHTML=document.getElementById("country")
                                              .options[document.getElementById("country")
                                               .selectedIndex].text+"  "+document.getElementById("province")
                                               .options[document.getElementById("province")
                                                        .selectedIndex].text+"  "+document.getElementById("city")
                                                        .options[document.getElementById("city")
                                                                 .selectedIndex].text+"  "+document.getElementById("district")
                                                                 .options[document.getElementById("district")
                                                                          .selectedIndex].text;
	document.getElementById("naddress").innerHTML=document.getElementById("address").value;

	document.getElementById("invoice-body").style.display="none";
	document.getElementById("ninvoice-body").style.display="block";

}

function preStep(){
	document.getElementById("invoice-body").style.display="block";
	document.getElementById("ninvoice-body").style.display="none";
}
</script>
<style type="text/css">
#invoice-body {
	margin-top: 50px;
	width: 600px;
	height: 500px;
}

#ninvoice-body {
	margin-top:50px;
	width: 600px;
	height: 500px;
	display: none;
}

.iinfo-lable {
	padding: 0 0 0 15px;
}
select.select-size{
   width: 135px;
}
select.area-size{
   width:80px;
}

.cbstyle
{
   width: 70px;
   height: 25px;
   margin-right: 30px;
}
.cnstyle
{
   width: 70px;
   height: 25px;
   margin-left: 30px;
}

.list-table1{width:100%;border:1px solid #DFDFDF}
table.list-table1 tr{height:30px;line-height:30px;border:1px solid #DFDFDF;}
table.list-table1 tr.hover{background:#ffffe1}
table.list-table1 th{background:#6293BB;color:#fff;font-weight:bolder;}
table.list-table1 .select,table.list-table .id{width:100px}
table.list-table1.state{width:10px}
table.list-table1 .date-time{width:140px}
table.list-table1 .operate{with:60px;}
table.list-table1 a.operate-link{padding:0 10px;}
table.list-table1 th input,table.list-table td input{margin:0px;}

</style>
</head>
<script type="text/javascript" src="${contextPath}/js/jQuery.js"></script>
<script type="text/javascript" src="${contextPath}/js/orderDetail.js"></script>
<body
	onload="init('${order.consignee.country.name}','${oarea.name}','${order.consignee.province.name}','${order.consignee.city.name}','${order.consignee.district.name}')">
	<center>
		<div id="invoice-body">
		<form action="">
			<table class="list-table1" >
				<tr>
					<td align="right">订单号</td>
					<td colspan="3"><label class="iinfo-lable" id="orderId">${order.id}</label>
					</td>
				</tr>
				<tr>
					<td align="right">抬头类型</td>
					<td><label class="iinfo-lable"> <select class="select-size" name="invoiceTpye">
								<option value="3453">普通发票</option>
								<option value="3453">增值税发票</option>
						</select> </label></td>
					<td align="right">发票抬头</td>
					<td><label class="iinfo-lable"><input type="text"
							name="invoiceTitle" value="" />
					</label>
					</td>
				</tr>
				<tr>
					<td align="right">发票金额</td>
					<td colspan="3"><label class="iinfo-lable"><input
							type="text" name="money" value="" />
					</label>
					</td>
				</tr>
				<tr>
					<td align="right">发票内容</td>
					<td><label class="iinfo-lable">图书/百货</label>
					</td>
					<td align="right">商品数量</td>
					<td><label class="iinfo-lable">300</label>
					</td>
				</tr>
				<tr>
					<td align="right">收货人</td>
					<td><label class="iinfo-lable"><input type="text"
							name="consignee" value="${order.consignee.consignee}" />
					</label></td>
					<td align="right">电话</td>
					<td><label class="iinfo-lable"><input type="text"
							name="mobile" value="${order.consignee.mobile}" />
					</label>
					</td>
				</tr>
				<tr>
					<td align="right">电子邮件</td>
					<td><label class="iinfo-lable"><input type="text"
							name="email" value="${order.consignee.email}" />
					</label></td>
					<td align="right">邮编</td>
					<td><label class="iinfo-lable"><input type="text" 
							name="zipCode" value="${order.consignee.zipCode}" />
					</label></td>
				</tr>
				<tr>
					<td align="right">收货时间</td>
					<td colspan="3"><label class="iinfo-lable"><input
							type="text" name="receiveTime"
							value="${order.receive.receiveTime}" />
					</label></td>
				</tr>
				<tr>
					<td align="right">区域</td>
					<td colspan="3"><label class="iinfo-lable"> <!-- 国家 -->
							<select class="area-size" name="country" onchange="onSelectChange(this,'area')">
								<c:forEach var="country" items="${countrys}">
									<option value="${country.id}">${country.name}</option>
								</c:forEach>
						</select>&nbsp;&nbsp; <!-- 区域 --> <select class="area-size" name="area"
							onchange="onSelectChange(this,'province')">
								<c:forEach var="area" items="${areas}">
									<option value="${area.id}">${area.name}</option>
								</c:forEach>
						</select>&nbsp;&nbsp; <!-- 省 --> <select class="area-size" name="province"
							onchange="onSelectChange(this,'city')">
								<c:forEach var="province" items="${provinces}">
									<option value="${province.id}">${province.name}</option>
								</c:forEach>
						</select>&nbsp;&nbsp; <!-- 城市 --> <select class="area-size" name="city"
							onchange="onSelectChange(this,'district')">
								<c:forEach var="city" items="${cities}">
									<option value="${city.id}">${city.name}</option>
								</c:forEach>
						</select>&nbsp;&nbsp; <!-- 区县 --> <select class="area-size" name="district"
							onchange="onSelectChange(this,'')">
								<c:forEach var="district" items="${districts}">
									<option value="${district.id}">${district.name}</option>
								</c:forEach>
						</select>
					</label></td>
				</tr>
				<tr>
					<td align="right">详细地址</td>
					<td colspan="3"><label class="iinfo-lable"><input size="83"
							type="text" name="address" value="${order.consignee.address}" />
					</label></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="button" class="cbstyle"   value="取消" />
						<input type="button"  class="cnstyle"   onclick="nextStep()" value="下一步" /></td>
				</tr>

			</table>
			</form>
		</div>
		
		<div id="ninvoice-body" >
					<table class="list-table1" >
				<tr>
					<td align="right">订单号</td>
					<td colspan="3"><label class="iinfo-lable" id="norderId"></label>
					</td>
				</tr>
				<tr>
					<td align="right">抬头类型</td>
					<td><label class="iinfo-lable" id="ninvoiceTpye" ></label></td>
					<td align="right">发票抬头</td>
					<td><label class="iinfo-lable" id="ninvoiceTitle"></label>
					</td>
				</tr>
				<tr>
					<td align="right">发票金额</td>
					<td colspan="3"><label class="iinfo-lable" id="nmoney"></label>
					</td>
				</tr>
				<tr>
					<td align="right">发票内容</td>
					<td><label class="iinfo-lable">图书/百货</label>
					</td>
					<td align="right">商品数量</td>
					<td><label class="iinfo-lable">300</label>
					</td>
				</tr>
				<tr>
					<td align="right">收货人</td>
					<td><label class="iinfo-lable" id="nconsignee"></label></td>
					<td align="right">电话</td>
					<td><label class="iinfo-lable" id="nmobile"></label>
					</td>
				</tr>
				<tr>
					<td align="right">电子邮件</td>
					<td><label class="iinfo-lable" id="nemail"></label></td>
					<td align="right">邮编</td>
					<td><label class="iinfo-lable" id="nzipCode"></label></td>
				</tr>
				<tr>
					<td align="right">收货时间</td>
					<td colspan="3"><label class="iinfo-lable" id="nreceiveTime"></label></td>
				</tr>
				<tr>
					<td align="right">区域</td>
					<td colspan="3"><label class="iinfo-lable" id="narea"></label></td>
				</tr>
				<tr>
					<td align="right">详细地址</td>
					<td colspan="3"><label class="iinfo-lable" id="naddress"></label></td>
				</tr>
				<tr>
					<td colspan="4" align="center"><input type="button" class="cbstyle" onclick="preStep()"  value="上一步" />
						<input type="button"  class="cnstyle"  style="height=25px;width=60px;"  value="提交" /></td>
				</tr>

			</table>
		</div>
	</center>

</body>
</html>