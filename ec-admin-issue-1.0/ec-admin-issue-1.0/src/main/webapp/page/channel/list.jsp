<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp" %>
<html>
<head>
<%@include file="../snippets/meta.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link href="${contextPath}/css/jquery.treeTable.css" rel="stylesheet"/>
<title>渠道管理</title>
<style type="text/css">
.clcheckbox{ margin:0px;padding:0px;width:14px;}
input.availableyes{ margin-left: 22px}
input.availableno{margin-left: 8px}
input.usingapiyes{margin-left: 5px}
input.usingapino{margin-left: 8px}
label.error {
	padding:0.1em;
	color: red;
	font-weight: normal;
}
input.error {
	padding:0px;
	border: 2px solid red;
}
</style>
</head>
<body>
<div id="content-result">
<h4>渠道列表  
<a href="http://console.winxuan.com/channel/list?needAvailableSort=1" class="operate-link" >所有渠道</a>
<a href="http://console.winxuan.com/channel/list?needAvailableSort=3" class="operate-link" >未启用渠道</a>
</h4>
<c:choose>
	<c:when test="${!empty channelList}">
	<table class="list-table" id="tree-table" style="width:1000px;">
		<colgroup>
			<col class="name"/>
			<col class="type"/>
			<col class="state"/>
			<col class="state"/>
			<col class="date-time"/>
			<col class="operate"/>
		</colgroup>
		<tr><th class="name">名称</th><th>类别</th><th>状态</th><th>API是否对接</th><th>创建时间</th><th>操作</th></tr>
			<c:forEach var="channel" items="${channelList}" varStatus="status">
			<c:set var="rowStatus" value="odd"/>
			<c:if test="${status.index%2==1}">
				<c:set var="rowStatus" value="even"/>
			</c:if>
			<c:set var="className" value="child-of-node-${channel.parent.id}"/>
			<c:if test="${empty channel.parent}">
			<c:set var="className" value=""/>
			</c:if>			
			<c:if test="${needAvailableSort == 1||(needAvailableSort == 2&&channel.available)||(needAvailableSort == 3&&!channel.available) }">
			<tr class="${className} ${rowStatus}" id="node-${channel.id}">
				<td class="name">${channel.name}</td>
				<c:choose>
				<c:when test="${!channel.hasAvailableChildren }">
				<td id="typename${status.index}">${channel.type.name}</td>
				<td> 
				<c:choose>
					<c:when test="${channel.available}">
					<input type="checkbox" class="clcheckbox" value="1" onclick="channelChange('${channel.id}','${status.index}')" id="checkid${status.index}" checked="checked" name="available"/>
					<span id="checkspan${status.index}">已启用</span></c:when>
					<c:otherwise><input class="clcheckbox" type="checkbox" onclick="channelChange('${channel.id}','${status.index}')" id="checkid${status.index}" value="1" name="available"/>
					<span id="checkspan${status.index}">未启用</span></c:otherwise>
				</c:choose>
				</td>
				<td>
				<c:choose>
					<c:when test="${channel.usingApi}">
					<span >已对接</span></c:when>
					<c:otherwise>
					<span >未对接</span></c:otherwise>
				</c:choose>
				</td>
				</c:when>
				<c:otherwise>
				<td></td><td></td><td></td>
				</c:otherwise>
				</c:choose>
				<td><fmt:formatDate value="${channel.createTime}" type="both" /></td>
				<td><a href="#" class="operate-link" onclick="addChild('${channel.id}')">添加子渠道</a>|<a href="#" onclick="edit('${channel.id}','${channel.name}','${status.index}',${channel.available},${channel.usingApi},${channel.issettle });" class="operate-link">编辑</a></td>	
			</tr>
			</c:if>
			</c:forEach>
			<tr><th class="name">名称</th><th>类别</th><th>状态</th><th>API是否对接</th><th>创建时间</th><th>操作</th></tr>
	</table>
	</c:when>
</c:choose>
</div>

<div id="newChild">
	<form action="${contextPath}/channel/create" method="POST" id="channelForm">
		<fieldset>
			<p><label>渠道名称：</label><input type="text" name="name" id="clname"/></p>
			<input type="hidden" name="parent" />
			<p><label>渠道类别：</label>
				<select name="type" id="selecttype">
					<c:forEach var="code" items="${channelType}">
					<option value="${code.id}">${code.name}</option>	
					</c:forEach>
				</select>
			</p>
			<p>
			是否启用：
			<input class="availableyes" type="radio" name="available" value="1" checked="checked">是
			<input class="availableno" type="radio" name="available" value="0" >否
			</p>
			<p>
			API是否对接：
			<input class="usingapiyes" type="radio" name="usingapi" value="1" checked="checked">是
			<input class="usingapino" type="radio" name="usingapi" value="0" >否		
			</p><br>
			<p>
			该渠道是否进行结算：
			<input class="usingapiyes" type="radio" name="issettle" value="1" checked="checked">是
			<input class="usingapino" type="radio" name="issettle" value="0" >否		
			</p><br>
			<div class="center"><button type="submit">保存</button></div>
		</fieldset>
	</form>
</div>

<%@include file="../snippets/scripts.jsp" %>
<script src="${contextPath}/js/jquery.treeTable.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/channel/list.js"></script>
</body>
</html>