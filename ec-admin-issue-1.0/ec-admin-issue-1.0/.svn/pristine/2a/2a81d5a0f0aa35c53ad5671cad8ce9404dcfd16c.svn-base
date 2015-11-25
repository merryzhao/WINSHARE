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
	<p id="stime">
		礼券发放时间：<input	name="sendTime" value="8002" type="radio">支付/订单审核 <input
			name="sendTime" value="8004" type="radio">发货 <input
			name="sendTime" value="8005" type="radio">确认收货
	</p>
	<p id="manners">
		促销方式： <input name="smanner" id="showpt" type="radio" value="21001">普通优惠
		<input name="smanner" id="showtd" value="21002" type="radio">梯度优惠
	</p>
	<div id="tidu" style="padding-left: 66; display: none;">
		<p>
			订单满<input type="text" size="10" name="sendprice">元送 <a
				href="javascript:void(0);" id="addpresentitems1" class="mark"
				onclick="showdiv(this)">礼券 </a> <a id="addtidu"
				href="javascript:void(0);">增加>></a>
		</p>
	</div>
	<div id="putong" style="padding-left: 66; display: none;">
		订单满<input type="text" size="10" name="sendprice">元送 <a
			href="javascript:void(0);" id="addpresentitems0"
			onclick="showdiv(this)">礼券 </a> <span style="color: red;">优惠成倍增长</span>
	</div>
	<div id="present_send">
		礼券批次：<select id="paramType">
			<option value="0">批次编号</option>
			<option value="1">批次标题</option>
		</select> <input name="paramValue" id="paramValue" type="text" size="15" />
		面额：<input type="text" id="nomey" name="nomey" size="10" /> <input
			type="button" id="preadd" value="添加" /><br />
		<table class="list-table" id="presentitems">
		</table>
		<center>
			<p>
				<input id="sbm" type="button" onclick="sbmdiv()" value="提交" /> <input
					style="margin-left: 15;" id="cancel" type="button" value="取消" />
			</p>
		</center>
	</div>
	<div id="tidudata" style="display: none;"></div>
	<div id="putongdata" style="display: none;"></div>
	<div id="datasbm" style="display: none;">
		<input type="text" id="promotionData1" name="promotionData1" value="" />
	</div>
</div>
<script type="text/javascript" src="${contextPath}/js/promotion/present_send.js"></script>



