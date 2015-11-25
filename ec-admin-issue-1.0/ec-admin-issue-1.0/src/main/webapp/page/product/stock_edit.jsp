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
				<form action="/stockrule/update" method="post">
								<table class="entering_table">
									<tbody>
									<tr>
										<td colspan="9">库存信息修改</td>
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
										${stockRule.channel.name }
										<input type="hidden"  for="lowerId" name="channel" value="${stockRule.channel.id }">
										</td>
										<td class="property">销售类型：
										</td>
										<td>
										<input type="hidden" name="supply" value="${stockRule.supplyType.id }">
										${stockRule.supplyType.name }
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td></td>
										<td class="property">库存类型：
										</td>
										<td>
										<input type="hidden" name="stockType" value="${stockRule.stockType}">
										<c:if test="${stockRule.stockType==1 }">实物库存</c:if>
										<c:if test="${stockRule.stockType==2 }">虚拟库存</c:if>
										</td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td class="property">库位：
										</td>
										<c:forEach var="dc" items="${dcList }" varStatus="i">
											<td>
											<label><input name="stockRules" for="stockRules" class="verification" type="checkbox" value="${dc.id }">${dc.name }</label>
											</td>
										</c:forEach>
									</tr>
									<tr>
										<td></td>
										<td class="property">总使用量：</td>
										<td  colspan="9">
											<code class='taskBox'><code class='taskBoxLinks'><h3 style='width:${stockRule.percent}%;'></h3><h4></h4></code>占用<span  for="progressmarkers">${stockRule.percent}%</span></code>
										</td>
									</tr>
									<c:forEach var="stockDc" items="${stockRule.stockRuleDc }" varStatus="i"> 
											<tr>
												<td><input  type="hidden" name="stockRule" value="${stockDc.dc.id }"/></td>
											</tr>
											<input type="hidden" name="stRuleDcs[${i.index }].id" value="${stockDc.id }"/>
									</c:forEach>
										<tr><td><input type="hidden" name="id" value="${stockRule.id }" /></td></tr>
									</tbody>
							</table>
							<table class="entering_table" id="entering_table_one">
								<tr>
									<td><c:if test="${success==true }"><script type="text/javascript">alert("修改成功");</script></c:if>
									 </td>
									<td></td>
									<td align="center"><input type="button" id="editsubmit" value="确定">&nbsp;&nbsp;&nbsp;<input type="button"  class="returnBtn" value="返回"></td>
								</tr>
							</table>
					</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/product/stockedit.js"></script>
	<script type="text/javascript" src="${contextPath}/js/product/stock_progress.js"></script>
	<!-- 多库存的时候选中库存信息 -->
<script type="text/javascript">
	$(function(){
		$("input[name=stockRule]").each(function(){
			var dcId = $(this).val();
			$("input[name=stockRules]").each(function(){
				if(dcId==$(this).val()){
					$(this).attr("checked",true);
				}
			});
		});
	});
</script>
</html>