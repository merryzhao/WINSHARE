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
<title>库存锁添加</title>
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
				<form action="/stockrule/lock/save" method="post">
								<table class="entering_table">
									<tbody>
									<tr>
										<td colspan="9">库存锁添加(<b style="color: red;">加盟店用户必填，其余渠道不填</b>)</td>
									</tr>
									<tr>
										<td colspan="9"><HR width="100%" SIZE=1>
										</td>
									</tr>
									
									<tr>
										<td></td>
										<td class="property"><b style="color: red;">*</b>渠道：
										</td>
										<td>
										<button type="button" id="add_channel" >添加渠道</button>
										</td>
										<td><span id="channelChecked"></span></td>
									</tr>
									<tr>
										<td></td>
										<td class="property">用户：
										</td>
										<td>
										<input type="text" name="customerName" value="" />
										<b id="customerOk"></b>
										<input type="hidden" name="customer.id" value="" />
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property"><b style="color: red;">*</b>商品编码：	
										</td>
										<td>
											<!-- <input type="text" name="productSale" /> -->
											<textarea id="productSales" style="width:157px; height: 55px;" name="productSales" onkeyup="this.value=this.value.replace(/[^\d\n]/g,'')"></textarea>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property"><b style="color: red;">*</b>开始时间：
										</td>
										<td>
											<input type="text" bind="mindatemepicker" id="startTime" name="beginTime" readonly="readonly"/>
										</td>
										<td class="property"><b style="color: red;">*</b>结束时间：
										</td>
										<td>
											<input type="text" bind="mindatemepicker" id="endTime" name="endTime" readonly="readonly"/>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">锁定库存：
										</td>
										<td>
											<input type="text" name="lockStock" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
										</td>
										<td class="property">锁定系数：
										</td>
										<td><code class='taskBox'><code class='taskBoxLinks'><h3 style='width:0%;' ></h3><h4></h4></code>锁定<span for="progressmarkers">%</span></code>
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
									<td style="color: blue;"><b style="color: red;">*</b>(锁定库存)和(锁定系数)必填一个<b style="color: red;">(如果两个都填写了那么默认为"锁定库存"有效)</b></td>
								</tr>
								</table>
							<table id="lockStockFactor">
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
	<c:if test="${isVerification!=null}">
    		<script type="text/javascript">
	    			if(${isVerification}){
	    				alert("新增:"+${psidsava}+";已经存在:"+${psidupdate}+";商品编码错误:"+${psidnull});
	    		 	}
    		</script>
    	</c:if>
	<script type="text/javascript" src="${contextPath}/js/product/stocklocksave.js"></script>
	<script type="text/javascript" src="${contextPath}/js/product/stock_progress.js"></script>
	<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.ztree-2.6.min.js"></script>
	<script type="text/javascript" src="${contextPath}/js/tree/channel_tree.js"></script>
</html>