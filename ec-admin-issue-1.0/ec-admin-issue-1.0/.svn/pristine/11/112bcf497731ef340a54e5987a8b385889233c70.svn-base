<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>渠道销售</title>
</head>
<body>
    <!-- 引入JS -->
	<div class="frame">
	     <!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		 <!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-finance.jsp"%>
		<div class="frame-main">
			
		    <!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<form action="/channelsales/sendtosap">
					下传SAP码洋:<input type="text" name="money"/>
					<input type="submit" value="下传" onclick="if(!/[0-9]+(?:\.[0-9]+)?/.test($('input[name=money]').val())){alert('码洋格式错误!');return false;}else{ return confirm('确认下传?'); }" />
					<!-- &nbsp;&nbsp;最小下传码洋：${minMoney } -->
				</form>
				<hr />
				<c:if test="${!empty pagination}">		   
					<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
				</c:if>
				<table class="list-table" >
					<tr>
						<th>流水ID</th>
						<th>上传渠道</th>
						<th>上传人</th>
						<th>上传时间</th>
						<th>当前状态</th>
						<th>发货码洋</th>
						<th>退货码洋</th>
						<th>下传SAP发货金额</th>
						<th>下传SAP退货金额</th>
						<th>备注</th>
						<th>系统消息</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${records }" var="r">
						<tr>
							<td>${r.id }</td>
							<td>${r.channel.name }</td>
							<td>${r.uploader.name }</td>
							<td><fmt:formatDate value="${r.uploadtime }" pattern="yyyy-MM-dd hh:mm:ss" /></td>
							<td>${r.status.name }</td>
							<td>${r.deliveryListprice }</td>
							<td>${r.refundListprice }</td>
							<td>${r.sapDeliveryListprice }</td>
							<td>${r.sapRefundListprice }</td>
							<td>${r.remark }</td>
							<td>${r.sysmsg }</td>
							<td>
							<c:if test="${r.allowDelete }" > 
								<a href="/channelsales/delete/${r.id }" onclick="return confirm('确认删除?');" >删除</a>
							</c:if>
							<c:if test="${r.allowRollback }" >
								<a href="/channelsales/rollback/${r.id }" onclick="return confirm('确认冲销?');" >冲销</a> 
							</c:if>	
							<c:if test="${r.status.id==91003 }">
								| <a href="/channelsales/audit/${r.id }" onclick="return confirm('确认审核')">审核</a>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${!empty pagination}">		   
					<winxuan-page:page bodyStyle="javascript" pagination="${pagination}" ></winxuan-page:page>
				</c:if>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
</body>
</html>