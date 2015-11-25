<%@page pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<style type="text/css">
#preview {
	display: inline-block;
	width: 100px;
	height: 100px;
	background-color: #CCC;
}

#productInfo {
	width: 140px;
	height: 80px;
}

#orderPresentEdit p span {
	margin: 15px 20px 5px 0;
}

#orderPresentEdit p {
	margin: 5px 0 0 5px;
}

#saleprice {
	margin-left: 200px;
}

#orderPresentEdit textarea {
	width: 150px;
	height: 50px;
}
</style>

<title>修改促销活动-注册送券活动</title>
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
				<iframe style="display: none;" name="myiframe"></iframe>
				<div id="promotion_error"></div>
				<form target="myiframe" action="/promotion/updatePresentForNewCustomer"
					enctype="multipart/form-data" id="promotionform"
					name="promotionform" method=post>
					<div id="orderPresentEdit">
						<p>
							<span>活动编号:${promotion.id }</span><span>活动标题:<input
								type=text name=promotionTitle value=${promotion.title } /> </span>
						</p>
						<p>
							<span>活动类型:${promotion.type.name }</span>
							<span>活动有效期:<input type="text" id="promotionStartDate" readonly="readonly" name="promotionStartDate"
								value="<fmt:formatDate value="${promotion.startDate }" pattern="yyyy-MM-dd"/>">
								时间<input value=<fmt:formatDate pattern="HH:mm" value="${promotion.startDate }" type="time"/>
								type="text" id="promotionStartTime" readonly="readonly"	name="promotionStartTime">~ 
								<input readonly="readonly" type="text" name="promotionEndDate" id="promotionEndDate"
								value="<fmt:formatDate value="${promotion.endDate }" pattern="yyyy-MM-dd"/>">
								时间<input value="<fmt:formatDate pattern="HH:mm" value="${promotion.endDate }" type="time"/>"
								type="text" id="promotionEndTime" readonly="readonly"
								name="promotionEndTime"> </span>
						</p>
						<p>
							<span>创建人:${promotion.createUser.name }</span><span>创建时间:<fmt:formatDate
									type="both" value="${promotion.createTime}" /> </span>
						</p>
						<p>
							<span>最后更新者:${promotion.assessor.name }</span><span>最后更新时间:<fmt:formatDate
									type="both" value="${promotion.assessTime}" /> </span>
						</p>
						<p>
							<label style="vertical-align: top;">活动描述:</label>
							<textarea id="promotiondescribe" name="promotionDescription">${promotion.description }</textarea>
						</p>
						<p>
							<span>促销语:<input name="advert" type="text"
								value="${promotion.advert }" /> </span><span>
								<div id="preview"
									style="display: inline; filter: progid :   DXImageTransform.Microsoft.AlphaImageLoader (   sizingMethod =   scale );">
									<img id="detailimg"
										<c:if test="${empty  promotion.advertImage }">src=/imgs/promotion/default.jpg </c:if>
										<c:if test="${not empty  promotion.advertImage }">src=/imgs/promotion/${promotion.advertImage } </c:if>
										width="100" height="100">
								</div> <input type="file" id="picPath" name="localfile"
								onChange="previewImage('preview',this,100,100);" /> </span>
						</p>
						<p>
							<span>活动专题链接：<input name="advertUrl" type="text" value="${promotion.advertUrl }" /> 
							</span>
						</p>
						<p>
							<b>活动规则</b>
						</p>
						<hr />
					</div>
					<!-- 选择卖家 -->
					<div class="general">
						<label>选择卖家</label> <select id="seller_select">
							<c:forEach items="${sellerList}" var="seller">
								<option value="${seller.id }">${seller.name }</option>
							</c:forEach>
						</select>
						<button type="button" onclick="addSellers();">添加</button>
					</div>
					<!-- 添加的卖家 -->
					<div id="seller_list" class="sellers">
						<c:forEach items="${sellers }" var="seller2">
							<div class='seller'>
								<label class='text'> ${seller2.name } </label> <label><a
									id='delete' class='delete' href='javascript:void(0);'>删除</a> </label> <input
									type='hidden' name='sellers' value='${seller2.id }'>
							</div>
						</c:forEach>
					</div>
					<div id="presentdata" style="display: none;"></div>
					<input type="hidden" value="${promotion.id}" id="pid" name="promotionId"> 
					<div id="datasbm" style="display: none;">
						<input type="text" id="promotionData1" name="promotionData1" value="" />
					</div>
				</form>
				<div id="" style="padding-left: 66">
			 		<a	href="javascript:void(0);" id="addpresentitems3" onclick="showregdiv(this)">礼券 </a>
				</div>
				<table>
					<tr>
						<td><input type="button" onclick="submitto()" value="保存 " />
						</td>
						<td><input type="button" onclick="reset()" value="取消" />
						</td>
					</tr>
				</table>
				<div id="register_present_send">
					礼券批次：<select id="paramType">
						<option value="0">批次编号</option>
						<option value="1">批次标题</option>
					</select> <input name="paramValue" id="paramValue" type="text" size="15" />
					面额：<input type="text" id="nomey" name="nomey" size="10" /> <input
						type="button" id="regpreadd" value="添加" /><br />
					<table class="list-table" id="regpresentitems">
					</table>
					<center>
						<p>
							<input id="sbm" type="button" onclick="sbmregdiv()" value="提交" /> <input
								style="margin-left: 15;" id="regcancel" type="button" value="取消" />
						</p>
					</center>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="${promotion.type.id }" id="promotionType">
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/present_send.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/promotion/present_send_edit.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/promotion/invoke_parent.js"></script>
	<script type="text/javascript"
		src="${contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript">
		var initindex = 1;
		var name = "addpresentitems";
		$(
				"input[name='sendTime'][value=${promotion.effectiveState.id}]")
				.attr("checked", true);
	</script>
</body>
</html>


