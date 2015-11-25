<%@page pageEncoding="UTF-8"%><%@include file="page/snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta property="qc:admins" content="1606275167671605166375" />
<meta property="wb:webmaster" content="669d637e35b2f7d1" />
<title>文轩网：您的网上新华书店，新华文轩旗下购书网站</title>
<meta name="description" content="文轩网，新华文轩集团旗下的购书网站，80万册正版图书，38元免邮费"/>
<meta name="keywords" content="文轩网,新华书店,网上书店,网上商城,网上买书,网上书城,图书,音像,音乐,百货,电子书,在线阅读,特价书,书评"/>
<jsp:include page="page/snippets/version2/meta.jsp">
	<jsp:param value="index" name="type" />
</jsp:include>
</head>
<body class="index">
	<jsp:include page="page/snippets/version2/header.jsp">
		<jsp:param value="home" name="label" />
		<jsp:param value="0" name="expandable" />
	</jsp:include>
	<div class="layout">
		<div class="left_box"></div>
		<div class="right_box">
			<jsp:include page="page/fragment/index/adsbox.jsp" flush="true" />
			<jsp:include page="page/fragment/index/notice.jsp" flush="true" />
			<div class="hei10"></div>
			<jsp:include page="page/fragment/index/flashsale.jsp" flush="true" />
		</div>
		<div class="hei10"></div>
		<div table="hotandnew" cn="current_sort">
			<dl class="sort_tab">
				<dt></dt>
				<dd class="current_sort" label="hotandnew">
					<a title="" href="javascript:;">新品</a>
				</dd>
				<dd label="hotandnew">
					<a title="" href="javascript:;">热销书</a>
				</dd>
			</dl>
			<jsp:include page="page/fragment/index/newbook.jsp" flush="true"></jsp:include>
			<jsp:include page="page/fragment/index/hotbook.jsp" flush="true"></jsp:include>
		</div>
		<jsp:include page="page/fragment/index/0F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/1F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/2F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/3F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/4F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/5F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/5F_1.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/6F.jsp" flush="true"></jsp:include>
		<jsp:include page="page/fragment/index/7F.jsp" flush="true"></jsp:include>
	</div>
	<jsp:include page="page/fragment/index/about.jsp"></jsp:include>
	<jsp:include page="page/snippets/version2/footer.jsp"></jsp:include>
	<script type="text/javascript" src="${serverPrefix}js/index.js?${version}"></script>
</body>
</html>
