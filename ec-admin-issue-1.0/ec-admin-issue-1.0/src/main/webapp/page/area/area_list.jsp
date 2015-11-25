<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet" />
<title>查询区域-配送方式</title>
<style type="text/css">
div.areaProvince {	font-size: 25px;	background: #D9D9D9;}
div.foldhead {    font-size: 20px;	margin-left: 20px;	margin-top: 5px;}
div.foldcontent {	margin-left: 20px;	display: none;}
div.foldcontenttable {	margin-left: 40px;	display: none;}

table.deliveryInfo tr{height:30px;line-height:20px;border:1px solid #DFDFDF;text-align:center;}
table.deliveryInfo tr:HOVER{background:#ffffe1}
table.deliveryInfo th{background:#6293BB;color:#fff;font-weight:bolder;text-align:center;}
table.deliveryInfo .type{width:100px;min-width:80px;margin-right: 20px;}
table.deliveryInfo .rule{width:250px;min-width:250px;margin-right: 20px;}
table.deliveryInfo .description{width:250px;min-width:250px;margin-right: 20px;}
</style>
<script type="text/javascript">
	function visible(target, img, url) {
		target.style.display = "block";
		img.src = url + "/imgs/area/open.jpg"
	}
	function invisible(target, img, url) {
		target.style.display = "none";
		img.src = url + "/imgs/area/close.jpg"
	}
	function isvisible(targetid, url) {
		target = document.getElementById(targetid);
		img = document.getElementById(targetid + "_img");
		if (target.style.display == "none" || target.style.display == "") {
			visible(target, img, url);
		} else {
			invisible(target, img, url);
		}
	}  

	function ajaxget(id) {
		if ($('#' + id).html() == "") {
			$.ajax({
				type : 'get', //可选get
				url : '/area/'+id+'/deliveryinfo', //这里是接收数据的程序
				data : '', //传给PHP的数据，多个参数用&连接
				dataType : 'html', //服务器返回的数据类型 可选XML ,Json jsonp script html text等
				success : function(msg) {
					//这里是ajax提交成功后，程序返回的数据处理函数。msg是返回的数据，数据类型在dataType参数里定义！
					$('#' + id).html(msg);
				},
				error : function() {
				}
			})
		}
	}
</script>
</head>
<body>
	<h4>查询区域-配送方式</h4>
	<div class="areaProvince">
		<c:forEach var="areaA" items="${areaCountry.children}">
			<c:forEach var="areaP" items="${areaA.children}">
				<a href="/area/${areaP.id}">${areaP.name}</a>
			</c:forEach>
		</c:forEach>
	</div>
	<!-- 折叠部分 -->
	<div>
		<c:if test="${areaProvince!=null}">
			<div class="foldhead">
				<img id="${areaProvince.id}_img"
					src="${pageContext.request.contextPath}/imgs/area/close.jpg"
					onclick="isvisible('${areaProvince.id}','${pageContext.request.contextPath}');" />
				<label>${areaProvince.name}</label>
			</div>
			<div id="${areaProvince.id}" class="foldcontent">
				<c:forEach var="areaCity" items="${areaProvince.children}">
					<div class="foldhead">
						<img id="${areaCity.id}_img"
							src="${pageContext.request.contextPath}/imgs/area/close.jpg"
							onclick="isvisible('${areaCity.id}','${pageContext.request.contextPath}');" />
						<label>${areaCity.name}</label>
					</div>
					<div id="${areaCity.id}" class="foldcontent">
						<c:forEach var="areaRegion" items="${areaCity.children}">
							<div class="foldhead">
								<img id="${areaRegion.id}_img"
									src="${pageContext.request.contextPath}/imgs/area/close.jpg"
									onclick="isvisible('${areaRegion.id}','${pageContext.request.contextPath}');" />
								<label>${areaRegion.name}</label>
							</div>
							<div id="${areaRegion.id}" class="foldcontent">
							<c:forEach var="areatwon" items="${areaRegion.children }">
							<div class="foldhead">
								<img id="${areatwon.id}_img"
									src="${pageContext.request.contextPath}/imgs/area/close.jpg"
									onclick="isvisible('${areatwon.id}','${pageContext.request.contextPath}');
									ajaxget('${areatwon.id}');
									" />
								<label>${areatwon.name}</label>
								<label >
								<c:choose>
								<c:when test="${areatwon.supportCod}">支持货到付款</c:when>
								<c:otherwise>不支持货到付款</c:otherwise>
								</c:choose>
								</label>	
							</div>
							<div id="${areatwon.id}" class="foldcontenttable"></div>
							</c:forEach>
							</div>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</c:if>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
</body>
</html>