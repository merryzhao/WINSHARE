<%@page pageEncoding="UTF-8"%><%@include file="/page/snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-${subject.title}-专题</title>
<jsp:include page="/page/snippets/version2/meta.jsp">
	<jsp:param value="bookshop" name="type" />
</jsp:include>
<link href="${serverPrefix}css2/images/detail.css?${version}"
	rel="stylesheet" type="text/css">
</head>
<body class="bookshop">

	<c:if test="${subject.sort.id==Code.PRODUCT_SORT_BOOK}">
		<jsp:include page="/page/snippets/version2/header.jsp">
			<jsp:param value="book" name="label" />
		</jsp:include>
	</c:if>
	<c:if test="${subject.sort.id==Code.PRODUCT_SORT_VIDEO}">
		<jsp:include page="/page/snippets/version2/header.jsp">
			<jsp:param value="media" name="label" />
		</jsp:include>
	</c:if>
	<c:if test="${subject.sort.id==Code.PRODUCT_SORT_MERCHANDISE}">
		<jsp:include page="/page/snippets/version2/header.jsp">
			<jsp:param value="mall" name="label" />
		</jsp:include>
	</c:if>

	<div class="layout">
		<c:forEach items="${fragments}" var="fragment" varStatus="status">
			<cache:fragmentPageCache key="SUBJECT_${fragment.id}" idleSeconds="86400">
				<c:import url="/fragment/${fragment.id}">
					 <c:param value="${cacheKey}" name="cacheKey" />
				</c:import>
			</cache:fragmentPageCache>

		</c:forEach>
	</div>

	<script type="text/javascript"
		src="${serverPrefix}/js/book/common.js?${version}"></script>

	<%@include file="/page/snippets/version2/footer.jsp"%>
	<script>
		seajs.use([ "jQuery", "toolkit" ], function($, ToolKit) {
			new ToolKit();
		});
		seajs.use([ "jQuery", "lazyimg" ], function($, lazyimg) {
			lazyimg.run();
		});
	</script>
</body>
</html>
