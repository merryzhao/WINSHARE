<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/promotion/_new.css"
	rel="stylesheet" />
<%@include file="../snippets/meta.jsp"%>
<!-- 引入JS -->
<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/promotion/_new.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/jquery.validate.methods.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/js/promotion/new_validate.js"></script>
<script type="text/javascript" src="${contextPath}/js/promotion/present_send.js"></script>
<%-- <script type="text/javascript" src="${contextPath}/js/promotion/register_present_send.js"></script> --%>
<script type="text/javascript" src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
<title>新建促销活动</title>
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
			<iframe style="display:none;" name="myiframe"></iframe>
				<form target="myiframe" name="promotionform" id="promotionform" action=""
					method="post" enctype="multipart/form-data">
					<h4>新建促销活动</h4><span
									style="color: red; font-size: 10px">(目前仅支持电子书修改,非电子书请到价格系统修改)</span><div id="promotion_error"></div>
					<hr>
					<table>
						<tr>
							<td>活动标题：</td>
							<td><input name="promotionTitle" id="promotionTitle"
								type="text">
							</td>
						</tr>
						<tr>
							<td>活动描述：</td>
							<td><textarea id="promotiondescribe"
									name="promotionDescription"></textarea></td>
						</tr>
						<tr>
							<td>活动类型：</td>
							<td><select id="type">
									<c:forEach var="activity" items="${types}">
										<OPTION value="${activity.id}">${activity.name}</OPTION>
									</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td>活动有效期：</td>
							<td>日期<input type="text" id="promotionStartDate" readonly="readonly" name="promotionStartDate" >
								<span id="time1">时间<input value="00:00" type="text" id="promotionStartTime" readonly="readonly"
								name="promotionStartTime" ></span>
								~ 日期<input  readonly="readonly" type="text" name="promotionEndDate" id="promotionEndDate">
								<span id="time2">时间<input value="23:30" type="text" id="promotionEndTime" readonly="readonly"
								name="promotionEndTime" ></span>
							</td>
						</tr>
						<tr id="productTips">
						<!--  
							<td colspan="2" style="color: red">温馨提示：1、类别调价活动每晚23:30计算相关商品第二天的促销价格，请提前一天设置相关价格；
							<br/> 2、单品调价活动每晚23:30会计算商品第二天生效的促销活动价格，设置结束时间时请尽量不要超过23:30
							</td>
						-->
						<td colspan="2" style="color: red">温馨提示：1、由于文轩网商品分类调整，类别调价活动已经被暂时停用，即使活动设置成功也无法生效；
							<br/> 2、单品调价活动每晚23:30会计算商品第二天生效的促销活动价格，设置结束时间时请尽量不要超过23:30
							</td>
						</tr>
						<tr>
							<td>订单支付时间：</td>
							<td><input type="text" name="effectivetime"
								id="effectivetime" onkeyup="this.value=this.value.replace(/\D/g,'')"/>小时</td>
						</tr>
						<tr>
							<td>活动促销语：</td>
							<td><input name="advert" type="text" />
							</td>

							<td>宣传图标：</td>
							<td>
							<%@include file="./img.jsp"%>
							</td>

						</tr>
						<tr>
							<td>活动专题链接：</td>
							<td colspan="3"><input name="advertUrl" type="text" />
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
					</table>
					<div id="activitycontent">
						
					</div>
					<input id="activitytype" type="hidden" value="productpreferential" />
					<input type="button" onclick="submitto()" value="提交" />
				</form>
				<div id="hiddendiv" style="display: none">

					<!--单品价格优惠活动 -->
					<div id="productpreferential">
						<%@include file="./single_product.jsp"%>
					</div>
					<!--类别优惠活动 -->
					<div id="category_price">
						<%@include file="./category_price.jsp"%>
					</div>
					<!--买商品赠商品活动 -->
					<div id="productgive">
						<%@include file="./product_promotion.jsp"%>
					</div>
					<!--订单价格优惠活动 -->
					<div id="orderpreferential">
						<%@include file="./promotion_price.jsp"%>
					</div>
					<!--订单送礼券活动 -->
					<div id="orderpresent">
						<%@include file="./present_send.jsp"%>
					</div>
					<!--订单减运费活动 -->
					<div id="deliveryfee">
						<%@include	file="./deliveryfee_promotion.jsp"%>
					</div>
					<!--指定商品满省活动 -->
					<div id="specifyproductpreferential">
						<%@include file="./specifyproduct_preferential.jsp"%>
					</div>
					<!-- 注册送礼券活动  -->
					<div id="presentfornewcustomer">
						<%@include file="./register_present_send.jsp" %>
					</div>
					
				</div>
			</div>

		</div>

	</div>
</body>
</html>