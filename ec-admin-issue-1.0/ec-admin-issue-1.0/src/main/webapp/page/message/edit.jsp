<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信平台</title>
</head>
<body>
<div class="frame">
<!-- 引入top部分 -->
<%@include file="../snippets/frame-top.jsp"%>
<%@include file="../snippets/frame-left-message.jsp"%>
<div class="frame-main">
	<div class="frame-main-inner" id="content">
		<form action="/message/edit/${message.id }" method="post">
			短信备注:<input name="remark" id="remark" type="text" value="${message.remark }"/>
			<hr/>
			短信类型：
			<c:forEach var="t" items="${messagetype }" varStatus="s">
				<input name="type" <c:if test="${message.type.id==t.id }" > checked="checked" </c:if>  type="radio" value="${t.id }"/>${t.name }
			</c:forEach>
			<hr/>
			渠道：<br/>			
			<c:forEach var="c" items="${channels }" varStatus="s">
				<c:set value=";${c.id };" var="temp"></c:set>
				<input name="channels" type="checkbox" value="${c.id }"  <c:if test="${fn:contains (message.channels,temp) }" > checked="checked" </c:if>  />${c.name }　
				<c:if test="${(s.index + 1) % 6 == 0 }"><br/></c:if>
			</c:forEach>
			<hr />
			支付方式:
			<c:forEach var="p" items="${paytype }" varStatus="s">
				<c:set value=";${p.id };" var="temp"></c:set>
				<input name="paytype" type="checkbox" value="${p.id }" <c:if test="${fn:contains (message.paytype,temp) }" > checked="checked" </c:if>/>${p.name }　
			</c:forEach>
			<hr />
			缺货品种数:
			0≤<input name="kindgreat" id="kindgreat" maxlength="8" value="${message.kindgreat}" onkeyup="(function(e){e.value = e.value.replace(/\D/g,'');})(this);"/>
			≤<input name="kindless" id="kindless"  maxlength="8" value="${message.kindless}" onkeyup="(function(e){e.value = e.value.replace(/\D/g,'');})(this);" />种
			<hr />
			发货类型:
			<c:forEach var="d" items="${deliverytype }">
				<c:set value=";${d.id };" var="temp"></c:set>
				<input name="deliverytype" type="checkbox" value="${d.id }" <c:if test="${fn:contains (message.deliveryType,temp) }" > checked="checked" </c:if> />${d.name }
			</c:forEach>
			<hr />
			
				短信内容:<br />
				<div>
					<div style="float: left;"><textarea name="message" id ="message" >${message.message }</textarea></div>
					<div>
						<br/><span>占位符,短信文本若出现以下关键字则替换相应内容</span><br/>
						<ul>
							<c:forEach var="t" items="${tags }">
								<li>${t.key}: ${t.value }</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			
			<hr />
			<input type="submit" value="修改保存" onclick="return sms.check(this)" />
		</form>
	</div>
</div>
</div>

<%@include file="../snippets/scripts.jsp" %>
<script src="../../js/message/new.js"></script>
</body>
</html>