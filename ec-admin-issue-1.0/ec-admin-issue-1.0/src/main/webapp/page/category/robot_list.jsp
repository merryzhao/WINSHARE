
<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<title>分类管理</title>
<style>
button.add { background:url("/css/zTreeStyle/img/add.jpg")  no-repeat scroll 1px 1px transparent;}
button.info { background:url("/css/zTreeStyle/img/info.png") no-repeat scroll 1px 1px transparent;}
button.update { background:url("/css/zTreeStyle/img/sim/10.png") no-repeat scroll 1px 1px transparent;}
div.updatesort{
position:fixed;left:450px;float: left;border: 0px;background-color:#D9D9D9;display: none;
}
button.move { background:url("/css/zTreeStyle/img/move.png") no-repeat scroll 1px 1px transparent;}
</style>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/category/category_move.js"></script>
	<script type="text/javascript" src="${contextPath}/js/category/robot.js"></script>
</head>
<body>
	<!-- 引入JS -->
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		
		<div class="frame-main">
			<div >
				<ul>
					<li>1、分类前方的复选框代表启用状态，钩中为启用，未钩中为不启用。</li>
					<li>2、修改分类名称时如果名称跟别名相同则只填写名称即可，如果不同则如下格式修改：<font color=red >名称*别名</font>。</li>
					<li>3、<font color=red >点击编辑</font>可以查看该分类的数据描述。<font color=red >双击分类</font>关闭数据描述界面</li>
					<li>4、点击移动按钮，选择商品移动的目标分类，如果目标分类下已存在此商品分类，批量移动会失败。</li>
					<li>5、分类批量移动后，商品的分类更新时间根据缓存策略而定，目前为<font color=red >1小时</font>。</li>
					<li>6、点击删除按钮会删除此分类，此操作只支持分类下无商品的百货分类</li>
				    <li>7、文轩网前端导航使用的是<font color=red >别名</font>展示</li>
				</ul>
			</div>
			<hr />
			
			<div id="nodeattr" style="position:fixed;bottom:5px;right:10px;float: right;border: 0px;"><font color=red>选中节点展示属性</font></div>
		<div id="updatesort" class="updatesort" >
		当前位置:<span class="nowsort"></span>,改为:
		<input name="sort" class="sort" type="text" value="0"/><a class="submit" href="javascript:;">确认</a>||<a class="cancel" href="javascript:;">取消</a>
		
		
		</div>
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content" style="float: left;">
				所有分类:<br/>
				<ul id="category_tree" class="tree"></ul>
			</div>
			<div style="position:fixed;bottom:5px;right:10px;float: right;border: 0px;">
				<iframe id="category_description" name="category_description" frameborder=0 style="border-width:0px;border:0px;width: 600px;height: 500px;" src="" ></iframe> 
			</div>
			<div id="categoryMoveDiv">
				<ul id="categoryMoveDiv_tree" class="tree"></ul>
				<br />
				<input name="selectTargetId" type="hidden"/>
			</div>
	   </div>
	</div>
</body>
</html>