<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<title>礼劵批次查询</title>
<style type="text/css">
button{line-height:1.5;}
table{width:1150px;}
</style>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<h4>礼劵批次查询</h4>

				<div id="queryForm">
					<form class="inline" action="/presentbatch/list" method="post"
						id="productform">
						<label>批次编号： </label> <input type="text" name="id"
							onkeyup="this.value=this.value.replace(/\D/g,'')"> <label>礼券面额：
						</label> <input type="text" name="value"
							onkeyup="this.value=this.value.replace(/\D/g,'')"> <label>标题关键字：
						</label> <input type="text" name="batchTitle"> <label>创建人：
						</label> <input type="text" name="createUser"><br/>
						<label>礼券批次状态：</label>
								<c:forEach var="statu" items="${batchStatus.children }">	
								<input type="checkbox" name="batchState" value="${statu.id }"						
								<c:forEach var="id" items="${presentFindForm.status }">
								  <c:if test="${id==statu.id}">
								   checked="checked"
								  </c:if>
								</c:forEach>
								>${statu.name}
								</c:forEach>
						<button type="submit">检索</button>
					</form>
				</div>
				<!-- 查询结果展示div -->

				<div>
					<c:if test="${pagination!=null}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
					<form action="">
						<table class="list-table" style="width:1150px;">
							<c:if test="${pagination!=null}">
								<tr>
									<th>批次编号</th>
									<th>批次标题</th>
									<th>分发数量</th>
									<th>激活数量</th>
									<th>使用数量</th>
									<th>支付数量</th>
									<th>礼券面额</th>
									<th>订单基准金额</th>
									<th>是否通用</th>
									<th>针对商品种类</th>
									<th>礼券有效开始时间</th>
									<th>礼券有效截止期</th>
									<th>礼券有效期</th>
									<th>批次状态</th>
									<th>操作</th>
								</tr>
							</c:if>
							<c:forEach var="presentBatch" items="${presentBatchList}"
								varStatus="i">
								<tr>
									<td><a href="${contextPath }/presentbatch/${presentBatch.id}/edit">${presentBatch.id}</a></td>
									<td>${presentBatch.batchTitle}</td>
									<td>${presentBatch.createdNum}/${presentBatch.num}</td>
									<td>${presentBatch.activeNum}</td>
									<td>${presentBatch.usedNum}</td>
									<td>${presentBatch.payNum}</td>
									<td>${presentBatch.value}</td>
									<td>${presentBatch.orderBaseAmount}</td>
									<td><c:if test="${presentBatch.general==true}">
									                 是
									</c:if> <c:if test="${presentBatch.general==false}">
									               否
									</c:if></td>
									<td>${presentBatch.productType}</td>
									<td><fmt:formatDate
											value="${presentBatch.presentStartDate}" pattern="yyyy-MM-dd" />
									</td>
									<td><fmt:formatDate value="${presentBatch.presentEndDate}"
											pattern="yyyy-MM-dd" /></td>
									<td><c:if test="${presentBatch.presentEffectiveDay!=null}">
											<c:if test="${presentBatch.presentEffectiveDay==0}">
									                 不限
									     </c:if>
											<c:if test="${presentBatch.presentEffectiveDay!=0}">
									       ${presentBatch.presentEffectiveDay}天  
									     </c:if>

										</c:if></td>
									<td id="stateTd${i.index }">${presentBatch.state.name}</td>
									<td id="operateTd${i.index}"><c:choose>
											<c:when test="${presentBatch.state.id==16001}">
												<a href="/presentbatch/${presentBatch.id}/edit">修改</a>
												<a href="javascript:void(0);"
													onclick="excuteVerifySingle('${presentBatch.id}','${i.index}');">审核</a>
											</c:when>
											<c:when test="${presentBatch.state.id==16002}">
												<a href="/present/dispensePage?id=${presentBatch.id}">分发礼券</a>
											</c:when>
											<c:otherwise>
												<a href="/presentbatch/${presentBatch.id}/edit">修改</a>
											</c:otherwise>
										</c:choose></td>

								</tr>
							</c:forEach>
							<c:if test="${pagination!=null&&pagination.pageCount!=0}">
								<tr>
									<th>批次编号</th>
									<th>批次标题</th>
									<th>分发数量</th>
									<th>激活数量</th>
									<th>使用数量</th>
									<th>支付数量</th>
									<th>礼券面额</th>
									<th>订单基准金额</th>
									<th>是否通用</th>
									<th>针对商品种类</th>
									<th>礼券有效开始时间</th>
									<th>礼券有效截止期</th>
									<th>礼券有效期</th>
									<th>批次状态</th>
									<th>操作</th>
								</tr>
							</c:if>
						</table>
					</form>
					<c:if test="${pagination!=null&&pagination.pageCount!=0}">
						<winxuan:page pagination="${pagination}" bodyStyle="javascript"></winxuan:page>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="currentId" />
	<input type="hidden" id="rowId" />
	<div id="verifySelectSingle2" align="center">
		请选择审核类型： <select id="verifyTypeSingle">
			<option selected="selected" value="1">审核通过</option>
			<option value="0">审核不通过</option>
		</select>
	</div>
	<div id="verifySucess" align="center">审核成功！</div>
	<div id="verifyFail" align="center">审核失败！</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${contextPath}/js/present/verify_batch.js"></script>
</body>
</html>