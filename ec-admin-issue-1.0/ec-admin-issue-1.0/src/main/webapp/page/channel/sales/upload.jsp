<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.winxuan.com/tag/page" prefix="winxuan" %>
<%@include file="../../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../snippets/meta.jsp"%>
<title>渠道销售上传</title>
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
				<form id="exForm" name="exForm" method="POST" target="_blank" action="/excel/channelSalesTempalte">
					<input type="hidden" value="xls" name="format">
					<a onclick="document.exForm.submit()">销售模板下载,需要转换成CSV</a>
				</form>
				<hr/>当前销售记录统计截止日期：<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>
				<hr/> 
				<form action="/channelsales/upload" method="post" enctype="multipart/form-data">
					<label>渠道:</label>
					<select name="channelId">
					<c:forEach items="${channels }" var="c">
						<!-- 苏宁（40,8090）销售数据 是合并了的.目前都合并到 40(先采后销),就隐藏了(8090先销后采) -->
						<!--  卓越少儿也是合并了的(41,8093),后续不会有41的少儿订单 -->
						<c:if test="${c.id != 8090 && c.id != 41 }">
							<option value="${c.id}">${c.name }</option>
						</c:if>
					</c:forEach>
					</select>
					<label>文件上传(CSV):</label>
					<input type="file" name="file" />
					<br />
					<label>备注</label>：<input type="text" name="remark" />
					<hr />
					<input type="submit" value="上传" onclick="$(this).hide(); return true" />
				</form>
			</div>
		</div>
	</div>
	<%@include file="../../snippets/scripts.jsp"%>
</body>
</html>