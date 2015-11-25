<%@page pageEncoding="UTF-8"%><%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../snippets/v2/meta.jsp" flush="true" >
          <jsp:param name="type" value="details" />
            <jsp:param name="page" value="cmspage" />
          <jsp:param value="false" name="ebook" />
</jsp:include>
<c:set var="serverPrefix" value="http://static.winxuancdn.com/" scope="page"/>

<link href="${serverPrefix}css/v2/index/index.css?${version}" rel="stylesheet" type="text/css">
<c:import url="http://www.winxuan.com/cms/${page}?reqfragment=header" />
<style type="text/css">
.master-nav-wrap {overflow: visible!important;}
.mod-breadhead a:first-child {font-size: 18px;font-weight: bold;font-family: "Microsoft Yahei"}
</style>
</head>
<body class="index">
	<div class="wrap">

	<jsp:include page="../snippets/v2/header.jsp">
	  <jsp:param value="book" name="label" />
	</jsp:include>

	<jsp:include page="../snippets/v2/navigation.jsp">
	 		<jsp:param value="false" name="index" />
	</jsp:include>
	
    <c:import url="http://www.winxuan.com/cms/${page}?reqfragment=body" />
      
	<jsp:include page="../snippets/v2/footer.jsp"></jsp:include>
	</div>
	 <script type="text/javascript">
        var _ozurltail = "#${product.secondLevelCategoryId}";
        var _ozprm = "cid99=${productSale.bookStorageType}${product.category.id}&press=${product.manufacturer}&author=${product.author}";
    </script>
</body>
</html>
