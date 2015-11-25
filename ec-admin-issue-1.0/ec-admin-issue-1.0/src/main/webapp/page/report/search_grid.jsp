<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE>
<html>
<head>
<%@include file="../snippets2/meta.jsp"%>
<title>报表搜索</title>
<style type="text/css">
</style>
</head>
<body screen_capture_injected="true">
		<!-- 引入top部分 -->
		<%@include file="../snippets2/frame-top.jsp"%>
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
				<h4>${grid.name}</h4>
				<hr>
				<div>
					<form action="/report/grid/showTable" method="POST" id="conditionForm" target="result_frame">
						<input type="hidden" name="gridId" value="${grid.id }"/>
						<input type="hidden" name="orderColumnIndex" id="orderColumnIndex" value="-1"/>
						<input type="hidden" name="isAsc" id="isAsc" value="false"/>
						<input type="hidden" name="format" id="format" value=""/>
						<input type="hidden" name="page" id="page" value="1"/>
						<div class="row download">
							<c:forEach var="condition" items="${grid.conditionList}">
							<c:choose>
								<c:when test="${condition.type==8}">
									<!-- 枚举类型 -->
                                         <c:choose>
										<c:when test="${condition.control==3 }">
											<!-- 下拉框 -->
											<div tag="div_condition" class="span6">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name" class="label_default text-overflow">${condition.name}:</label>
											<select name="${condition.parameterName}" class="input-medium">
												<option value="0">请选择</option>
												<c:forEach var="enumeration" items="${enumerations}">
													<c:if test="${enumeration[0].id==condition.id }">
														<c:forEach var="keyV" items="${enumeration[1] }">
															<option value="${keyV[0]}">${keyV[1]}</option>
														</c:forEach>
													</c:if>
												</c:forEach>
											</select>
											</div>
										</c:when>
									</c:choose>
								</c:when>
								<c:otherwise>
									<!-- 其它类型 -->
                                    		 <c:choose>
										<c:when test="${condition.control==1 }">
											<!-- 文本框 -->
											<div tag="div_condition" class="span4">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name" class="label_default text-overflow">${condition.name}:</label>
											<input type="text" name="${condition.parameterName}" value="${condition.defaultValue}" class="input-medium">
											</div>
										</c:when>
										<c:when test="${condition.control==9 }">
											<!-- 日期控件-->
											<div tag="div_condition" class="span4">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name" class="label_default text-overflow" >${condition.name}:</label>
											<input type="text" name="${condition.parameterName}" value="${condition.defaultValue}" bind="datepicker" class="input-medium">
											</div>
										</c:when>
										<c:when test="${condition.control==8 }">
											<!-- 日期时间控件-->
											<div tag="div_condition" class="span4">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name" class="label_default text-overflow" >${condition.name}:</label>
											<input type="text" name="${condition.parameterName}" value="${condition.defaultValue}" bind="datetimepicker" class="input-medium">
											</div>
										</c:when>
									</c:choose>
								</c:otherwise>
							</c:choose>
							</c:forEach>
						</div>
						<hr>
						
						<div class="row download">
							<c:forEach var="condition" items="${grid.conditionList}">
							<c:choose>
								<c:when test="${condition.type!=8}">
									<!-- 其它类型 -->
                                    		 <c:choose>
										<c:when test="${condition.control==2 }">
											<!-- 文本域-->
											<div tag="div_condition" class="span4">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name" class="label_default text-overflow">${condition.name}:</label>
											<textarea style="height: 50px" name="${condition.parameterName}"  class="input-medium">${condition.defaultValue}</textarea>
											</div>
										</c:when>
									</c:choose>
								</c:when>
							</c:choose>
							</c:forEach>
						</div>
						<hr>
						
						<div class="row download">
							<c:forEach var="condition" items="${grid.conditionList}">
							<c:choose>
								<c:when test="${condition.type==8}">
									<!-- 枚举类型 -->
                                         <c:choose>
										<c:when test="${condition.control==6 }">
											<!-- 单选框 -->
											<div tag="div_condition" class="span12">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name">${condition.name}:</label>
											<c:forEach var="enumeration" items="${enumerations}">
												<c:if test="${enumeration[0].id==condition.id }">
													<div class="row">
													<c:forEach var="keyV" items="${enumeration[1] }">
														<div class="span2">
														<label class="radio text-overflow" title="${keyV[1]}"><input type="radio" name="${condition.parameterName}" value="${keyV[0]}">${keyV[1]}</label>
														</div>
													</c:forEach>
													</div>
												</c:if>
											</c:forEach>
											<hr>
											</div>
										</c:when>
										<c:when test="${condition.control==7 }">
											<!-- 复选框 -->
											<div tag="div_condition" class="span12">
											<input id="allowNull" type="hidden" value="${ condition.allowNull }" />
											<label id="name">${condition.name}:</label>
											<c:forEach var="enumeration" items="${enumerations}">
												<c:if test="${enumeration[0].id==condition.id }">
													<div class="row">
													<c:forEach var="keyV" items="${enumeration[1] }">
														<div class="span2">
														<label class="checkbox text-overflow" title="${keyV[1]}">
														<input type="checkbox" name="${condition.parameterName}" value="${keyV[0]}">${keyV[1]}
														</label>
														</div>
													</c:forEach>
													</div>
												</c:if>
											</c:forEach>
											<hr>
											</div>
										</c:when>
									</c:choose>
								</c:when>
							</c:choose>
							</c:forEach>
						</div>
						<div>
						<hr>
						
						<div class="row download">
							<!-- 后台导出-->
							<div tag="div_condition" class="span4">
								<c:if test="${asyncReporting == 1}">
									是否后台导出?:<input  type="checkbox" id="isAsync" name="isAsync" disabled="disabled" title="您有导出任务正在执行，请稍等！"/>
								</c:if><c:if test="${asyncReporting == 0}">
									是否后台导出?:<input  type="checkbox" id="isAsync" name="isAsync" />
								</c:if>
							</div>
							<div tag="div_condition" class="span4">
								导出数据量：<input name="size_end" type="text" value="1000" style="width:80px;"/>条
							</div>
							<hr>
							<div tag="div_condition" class="span12">
							历史导出记录：
								<div class="row">	
							 	<c:forEach var="log" items="${logs}">
							 		<div class="span3">
									<label class="checkbox text-overflow" title="${log.columnString}">
										<c:if test="${log.errorMessage == null }">
											<a href="http://console.winxuan.com/files/${log.path }" target="_blank">${log.createTime}</a>
										</c:if>
										<c:if test="${log.errorMessage != null }">
											<span title="后台导出异常，${log.errorMessage}">
												${log.createTime}
											</span>
										</c:if>
									</label>
									</div>
								</c:forEach>
								</div>
							</div>
						</div>
							<button type="button" onclick="report.query('conditionForm')" class="btn btn-primary">检索报表</button>
						</div>
					</form>
				</div>
				<iframe id="iframe" name="result_frame" frameborder="no" width="100%" scrolling="no"></iframe>
			</div>
		</div>
	</div>
	</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="${contextPath}/js/report/grid.js"></script>
<script>
$(document).ready(function(){
	$("input[bind='datetimepicker']").datetimepicker({
		 regional:"zh-CN",
		 timeFormat: 'hh:mm:ss tt'
	});
})
function updateHeight(height){
	$("#iframe").height(height);
}
</script>
</body>
</html>
