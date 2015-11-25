<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="showinfo">
	<!-- 选择卖家 -->
	<div class="general">
		<label>选择卖家</label> <select id="seller_select">
			<c:forEach items="${sellerList }" var="seller">
				<option value="${seller.id }">${seller.name }</option>
			</c:forEach>
		</select>
		<button type="button" onclick="addSellers();">添加</button>
	</div>
	<!-- 添加的卖家 -->
	<div id="seller_list" class="sellers"></div>
	
	<div id="" style="padding-left: 66">
		 <a	href="javascript:void(0);" id="addpresentitems3" onclick="showregdiv(this)">礼券 </a>
	</div>
	<div id="register_present_send">
		礼券批次：<select id="paramType">
			<option value="0">批次编号</option>
			<option value="1">批次标题</option>
		</select> <input name="paramValue" id="paramValue" type="text" size="15" />
		面额：<input type="text" id="nomey" name="nomey" size="10" /> 
			<input type="button" id="regpreadd" value="添加" /><br />
		<table class="list-table" id="regpresentitems">
		</table>
		<center>
			<p>
				<input id="sbm" type="button" onclick="sbmregdiv()" value="提交" /> <input
					style="margin-left: 15;" id="regcancel" type="button" value="取消" />
			</p>
		</center>
	</div>
	<div id="presentdata" style="display: none;"></div>
	<div id="datasbm" style="display: none;">
		<input type="text" id="promotionData1" name="promotionRuleData" value="" />
	</div>
</div>
<%-- <script type="text/javascript" src="${contextPath}/js/promotion/register_present_send.js"></script> --%>



