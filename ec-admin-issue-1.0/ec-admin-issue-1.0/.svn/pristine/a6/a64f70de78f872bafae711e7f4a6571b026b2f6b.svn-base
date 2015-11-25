<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets2/meta.jsp"%>
<%@include file="../snippets/scripts.jsp"%>
<title>我的报表</title>
<style type="text/css">
div.letter {
	background: #D9D9D9;
}
#reports .span12{border-bottom:1px #D9D9D9 solid;margin-bottom: 20px;}

</style>
<script>
function insertDiv(id,name,myGridId){	
	$("#myGridList").append("<div class='span4'><a href=/report/grid/search/"+id+" >"+name+" </a> <a style='margin-right:20px;' title='移除我的报表' href=/report/grid/romvemygrid/"+myGridId+">[-]</a></div>");
}
</script>
</head>
<body>
		<!-- 引入top部分 -->
	<jsp:include page="../snippets2/frame-top.jsp">
		<jsp:param value="report" name="type" />
    </jsp:include>
		<div class="container-fluid">
		<div class="row-fluid">
		<div class="span2">
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets2/frame-left-form.jsp"%>
		</div>
		<div class="span10">
		<!--[if lt IE 7]>
		<div class="alert alert-info">
			亲, 你怎么还在用IE6呀~~~IE6不安全容易中木马有木有! IE6兼容性差速度慢还很丑有木有! 连微软这个亲妈都不要它了有木有! 亲, 升级吧, 程序猿们都哭了~~~<br>
			<strong><a target="_blank" href="http://windows.microsoft.com/zh-CN/internet-explorer/products/ie-9/compare-browsers">点击这里去微软网站下载IE8</a></strong>, 亲你还可以使用360升级哦~~~
		</div>
		<![endif]-->
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">

				<h4>我的报表</h4>
				<hr>
				<div class="btn-group">					
				</div><br>
				<div id="reports" class="row">
					<div class="span12"><code>列表:</code><div id="myGridList" class="row"></div></div>
					</div>
				<script>
					<c:forEach items="${mygridlist.list}" var="grid" >
						insertDiv('${grid.id}','${grid.name}','${grid.myGridId}');						
					</c:forEach>
				</script>
			</div>
		</div>
		</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
</body>
</html>
