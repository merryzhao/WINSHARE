<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<html>
<head> 
	<title>任务管理 - 文轩网 </title>
<%@include file="/page/snippets/meta.jsp"%>
</head>
<body>
<div class="head">

<div id="tabs-index">
	<ul>
		<li><a href="#tabs-index-1">首页</a></li>
		<li><a href="/task">闲置计划</a></li>
		<li><a href="/trigger">已添加计划</a></li>
		<li><a href="/trigger/run">运行中计划</a></li>
		<li><a href="/log/error">日志信息</a></li>
	</ul>
	<div id="tabs-index-1">
		<p>Winxuan.Com Task. </p>
		<p>0/15 * * * * ?  - 执行间隔15秒</p>
		<p>5 0/5 * * * ?  - 每5分钟的第5秒执行 </p>
		<p>0 15 10 15 * ?  - 每月15号的10：15触发 </p>
		<p>"0 0 12 * * ?"	  	每天中午十二点触发 </p>
		<p>"0 15 10 ? * *"	  	每天早上10：15触发 </p>
		<p>"0 15 10 * * ?"	  	每天早上10：15触发 </p>
		<p>"0 15 10 * * ? *"	  	每天早上10：15触发 </p>
		<p>"0 15 10 * * ? 2005"	  	2005年的每天早上10：15触发 </p>
		<p>"0 * 14 * * ?"	  	每天从下午2点开始到2点59分每分钟一次触发 </p>
		<p>"0 0/5 14 * * ?"	  	每天从下午2点开始到2：55分结束每5分钟一次触发 </p>
		<p>"0 0/5 14,18 * * ?"	  	每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 </p>
		<p>"0 0-5 14 * * ?"	  	每天14:00至14:05每分钟一次触发 </p>
		<p>"0 10,44 14 ? 3 WED"	  	三月的每周三的14：10和14：44触发 </p>
		<p>"0 15 10 ? * MON-FRI"	  	每个周一、周二、周三、周四、周五的10：15触发 </p>
		<p>"0 15 10 15 * ?"	  	每月15号的10：15触发 </p>
		<p>"0 15 10 L * ?"	  	每月的最后一天的10：15触发 </p>
		<p>"0 15 10 ? * 6L"	  	每月最后一个周五的10：15触发 </p>
		<p>"0 15 10 ? * 6L"	  	每月最后一个周五的10：15触发 </p>
		<p>"0 15 10 ? * 6L 2002-2005"	  	2002年至2005年的每月最后一个周五的10：15触发 </p>
		<p>"0 15 10 ? * 6#3"	  	每月的第三个周五的10：15触发 </p>
	</div>
</div>

</div>


<div class="demo-description">
<p>winxuan.</p>
<%@include file="/page/snippets/scripts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/custom/taskList.js"></script>
<script type="text/javascript">
		var binded = false;
		$(function() {
			$("#tabs-index").tabs({
				load: function (e, ui) {
						$("#dialog-modal").dialog("destory");
						$("#dialog-modal").hide();
						$("#tabs-trigger").tabs({
							load: function (e, ui) {
							}
						});
						$(ui.panel).find(".tab-loading").remove();
						$(".datepicker").datetimepicker({
							showSecond: true,
							timeFormat: 'hh:mm:ss'
						});
						binded=true;
						$("#refreshTriggerButton").button();
						$("#refreshLogButton").button();
						$("#refreshTriggerButton").click(function() { refreshTrigger(); });
						$("#refreshLogButton").click(function() { refreshLog(); });
				},
				select: function (e, ui) {
					var $panel = $(ui.panel);
					$("#dialog-modal").remove();
					if ($panel.is(":empty")) {
						$panel.append("<div class='tab-loading'></div>");
					}
				}
			});
		});
	</script>
</body>
</html>
