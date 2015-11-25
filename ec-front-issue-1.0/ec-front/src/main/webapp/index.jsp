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
<jsp:include page="/page/snippets/v2/meta.jsp" flush="true" >
       <jsp:param name="type" value="details" />
       <jsp:param value="false" name="ebook" />
</jsp:include>
<c:set var="serverPrefix" value="http://static.winxuancdn.com/" scope="page"/>
<link href="${serverPrefix}css/v2/index/index.css?${version}" rel="stylesheet" type="text/css">
</head>
<body class="index">
	<div class="wrap">

	<jsp:include page="/page/snippets/v2/header.jsp">
	   <jsp:param value="book" name="label" />
	</jsp:include>

	<jsp:include page="/page/snippets/v2/navigation.jsp">
	 	<jsp:param value="true" name="index" />
	</jsp:include>


		<%-- 主体 --%>
		<div class="main main-index">


			<%-- banner --%>
			<div class="grid-100p">
				<jsp:include page="/page/fragment/index2/0F.jsp"/>
			</div>



			<div class="grid-1210w">
  				<div class="grid-mod-2-l210">
  					<div class="grid-mod-l">
						<%-- nav --%>
						<jsp:include page="/page/fragment/index2/nav.jsp"/>
					</div>
					<div class="grid-mod-r">
						<%-- 精心挑选 --%>
				 		<jsp:include page="/page/fragment/index2/1F.jsp"/>
				 	</div>
				</div>
			</div>



			<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 文选聚焦 --%>
		 		<jsp:include page="/page/fragment/index2/2F.jsp"/>
	 		</div>



	 		<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 特色推荐 --%>
		 		<jsp:include page="/page/fragment/index2/3F.jsp"/>
	 		</div>



	 		<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 分类推荐 --%>
		 		<jsp:include page="/page/fragment/index2/4F.jsp"/>
	 		</div>


	 		<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 作家推荐 --%>
		 		<jsp:include page="/page/fragment/index2/5F.jsp"/>
	 		</div>



	 		<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 品牌出版 --%>
		 		<jsp:include page="/page/fragment/index2/6F.jsp"/>
		 	</div>



		 	<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 电子书 --%>
		 		<jsp:include page="/page/fragment/index2/7F.jsp"/>
	 		</div>



	 		<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 音响  精品推荐 --%>
		 		<jsp:include page="/page/fragment/index2/8F.jsp"/>
		 	</div>



		 	<div class="grid-1210w grid-margin-bottom ">
		 		<%-- 百货  精品推荐 --%>
		 		<jsp:include page="/page/fragment/index2/9F.jsp"/>
	 		</div>

	 		
	 		<div class="grid-100p">
		 		<%-- 底部 --%>
		 		<jsp:include page="/page/fragment/index2/10F.jsp"/>
	 		</div>
	 	</div>
	<jsp:include page="/page/snippets/v2/footer.jsp"></jsp:include>
	</div>
    <script type="text/javascript"
        src="${serverPrefix}js/v2/index.js?${version}"></script>
	 <script type="text/javascript">
        var _ozurltail = "#${product.secondLevelCategoryId}";
        var _ozprm = "cid99=${productSale.bookStorageType}${product.category.id}&press=${product.manufacturer}&author=${product.author}";
    </script>
</body>
</html>
