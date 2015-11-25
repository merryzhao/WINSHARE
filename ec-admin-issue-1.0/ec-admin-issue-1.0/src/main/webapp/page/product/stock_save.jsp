<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@include file="../snippets/meta_payment.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
div.entering_table {
	background: #ecf5ff;
	margin-top: 25px;
	margin-bottom: 25px;
	border: 1px solid #6293bb;
}

table.entering_table {
	width: 90%;
	margin-left: 5%;
	margin-right: 5%;
	margin-top: 10px;
	margin-bottom: 10px;
}

table.entering_table td.property {
	text-align: right;
}
</style>
<!-- 进度条的 -->
<style type="text/css">  
.body,td,th {  
    font-family: Verdana, Geneva, sans-serif;  
    font-size: 12px;  
}  
.h1{ font-size:16px; color:#999; font-weight:normal; text-align:center; line-height:90px; border-top:1px dashed #ddd; margin-top:50px;}  
*{ margin:0; padding:0;}  
.taskBoxLinks { float:left; width:225px; height:7px; font-size:0; line-height:0; background:#f2f2f2; border:1px solid #dedede;-moz-border-radius:6px;-webkit-border-radius:6px;border-radius:6px; position:relative; margin-top:8px; margin-right:10px; cursor:pointer;}  
.taskBoxLinks h3,.taskBoxLinks h4{ position:absolute; left:-1px; top:-1px; height:7px; font-size:0; line-height:0; width:10%; background:#a3d9f4; border:1px solid #187aab; -moz-border-radius:6px;-webkit-border-radius:6px;border-radius:6px; z-index:99;}  
.taskBoxLinks h4 { border:1px solid #91cdea; background:#f5fafc; z-index:88;}  
.taskBox { width:360px; margin:40px auto; color:#666;}  
</style>
<link type="text/css"
	href="${pageContext.request.contextPath}/css/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" />
<title>库存添加</title>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-product.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner" id="content">
			<div id="content-result" >
			<div class="entering_table">
				<form action="/stockrule/save" method="post">
								<table class="entering_table">
									<tbody>
									<tr>
										<td colspan="9">库存信息添加</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									
									<tr>
										<td></td>
										<td class="property">渠道：
										</td>
										<td>
										<button type="button" id="add_channel" >添加渠道</button>
										</td>
										<td><span id="channelChecked"></span></td>
										<td class="property">销售类型：
										</td>
										<td>
										<select name="supply">
											<c:forEach var="supply" items="${supplyTypeList }">
												<option value="${supply.id }">${supply.name }</option>
											</c:forEach>
										</select>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">库存类型：
										</td>
										<td>
										<label><input type="radio" checked="checked" name="stockType" value="1">实物库存</label>
										</td>
										<td><label><input type="radio" name="stockType" value="2">虚拟库存 </label></td>
									</tr>
									<tr>
										<td></td>
										<td class="property">库位：
										</td>
										<c:forEach var="dc" items="${dcList }" varStatus="i">
											<td>
											<label><input name="stockRules" class="verification" type="checkbox" value="${dc.id }">${dc.name }</label>
											<input  type='hidden' value="${dc.id }" for="${dc.id }stock"/>
											</td>
										</c:forEach>
									</tr>
									<tr>
										<td></td>
										<td class="property">总使用量：
										</td>
										<td colspan="9"><code class='taskBox'><code class='taskBoxLinks'><h3 style='width:0%;'></h3><h4></h4></code>占用<span for="progressmarkers">0%</span></code>
										</td>
									</tr>
									</tbody>
								</table>
								<table class="entering_table"  id="entering_table_one">
								<tr>
									<td align="center"><input type="button" class="button" id="yessubmit" value="确定">&nbsp;&nbsp;&nbsp;<input type="button" class="button returnBtn" value="返回"></td>
									<td></td>
								</tr>
								<tr>
									<td style="color: blue;"><b style="color: red;">*</b>当渠道和销售类型已有时，确定按钮为灰色。<b style="color: red;">(请重新选择渠道或者销售类型)</b></td>
								</tr>
								</table>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="channelDiv">
		<ul id="channel_tree" class="tree"></ul>
		<br />
		<button type=button onclick="insertNodes()" id="getChecktree">确定</button>
	</div>
	<iframe name="product" style="display: none;"></iframe>
</body>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/product/stocksave.js"></script>
	<script type="text/javascript" src="${contextPath}/js/product/stock_progress.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/tree/channel_tree.js"></script>
</html>