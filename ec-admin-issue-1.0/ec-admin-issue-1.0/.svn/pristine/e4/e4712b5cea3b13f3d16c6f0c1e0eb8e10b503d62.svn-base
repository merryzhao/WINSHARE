<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/meta.jsp"%>
<html>
<head>
<title>结算账户</title>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/returnorder/returnorder.css"
	rel="stylesheet" />
</head>
<body onload="">
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="requestInfo" align="center" style="font-size: 15px; font-weight: bold;">${message }</div>
				<div>
					<h4>已有结算账户</h4>
					<div>
						<table class="list-table" style="width: 100%;">
							<tr>
								<th>id</th>
								<th>名称</th>
								<th>渠道</th>
								<th>操作</th>
								<th>容差</th>
							</tr>
							<c:forEach var="billAccount"
										items="${billAccounts }">
								<tr>
									<td>${billAccount.id }</td>
									<td>${billAccount.name }</td>
									<td>
										<c:forEach var="channelItem"
											items="${billAccount.channels }">
											<span style="margin-right:5px">${channelItem.name}<a href="javascript:void(0)" onclick="removeChannel(this, '${billAccount.id }', '${channelItem.id }');" title="删除">X</a></span>
										</c:forEach>
									</td>
									<td>
										<a href="#" onclick="$('#channel_info_${billAccount.id }').show();return false;">添加</a>
										<div id="channel_info_${billAccount.id }" class="ui-dialog-content ui-widget-content" style="display: none; z-index: 1002; outline: 0px none; position: absolute; height: auto; left:700px;" align="left">
											<form action="/ordersettle/addBillAccountChannels" method="post" id="channel_form_${billAccount.id }">
												<input type="hidden" name="billAccountId" value="${billAccount.id }"/>
												<ul>
													<c:forEach var="channelItem" items="${channelList }">
														<c:if test="${fn:contains(billAccount.channels, channelItem)==false}">
															<li>
																<input type="checkbox" name="channelIds" value="${channelItem.id }"/>${channelItem.name }
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</form>
											<p align="center">
												<button type="button" onclick="$('#requestInfo').html('请求提交中...').dialog('open');$('#channel_form_${billAccount.id }').submit();">确定</button>
												<button type="button" onclick="$('#channel_info_${billAccount.id }').hide();return false;">关闭</button>
											</p>
										</div>
									</td>
									<td>
										<input type="text" value="${billAccount.tolerance }" id="tolerance_${billAccount.id }" style="width:40px"/><a href="#" onclick="updateTolerance('${billAccount.id}');return false;">修改</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<h4>录入结算账户</h4>
					<div>
						<form action="/ordersettle/billAccountCreate" method="post" id="billaccount_form">
							<div>
								<p>
									账户:<input type="text" name="name" <c:if test="${name==null}"> value="请输入账户名"</c:if><c:if test="${name!=null}"> value="${name }"</c:if> onfocus="if(this.value=='请输入账户名'){this.value=''}" onblur="if(this.value==''){this.value='请输入账户名'}"/>
								</p>
								<ul>
									<c:forEach var="channelItem"
										items="${channelList }">
										<li>
											<input type="checkbox" name="channelIds" value="${channelItem.id }"/>${channelItem.name}  
										</li>
									</c:forEach>
								</ul>
								<p>
									容差输入：<input type="text" name="tolerance"/>
								</p>
								<button id="settle_button" type="submit" onclick="createBillAccount();return false;">录入</button>
							</div>
						</form>
					</div>
					<br>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/ordersettle/billaccount.js"></script>
</body>
</html>
