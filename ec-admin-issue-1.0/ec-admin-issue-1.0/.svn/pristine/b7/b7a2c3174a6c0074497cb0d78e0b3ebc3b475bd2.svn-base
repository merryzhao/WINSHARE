<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div>
	<table class="deliveryInfo">
		<colgroup>
			<col class="type" />
			<col class="type" />
			<col class="rule" />
			<col class="description" />
		</colgroup>
		<tr>
			<th>配送方式</th>
			<th>运费收取方式</th>
			<th>运费收取规则</th>
			<th>备注</th>
		</tr>
		<c:forEach var="deliveryInfo" items="${deliveryInfos}">
			<tr>
				<td>${deliveryInfo.deliveryType.name}</td>
				<td>${deliveryInfo.deliveryFeeType.name}</td>
				<td><c:set var="rule"
						value="${fn:split(deliveryInfo.deliveryFeeRule,',')}"
						scope="request" /> 
						<c:choose>
						<c:when test="${fn:length(rule)==1}">
                                                                    每单${rule[0]}元
                        </c:when>
						<c:otherwise>
                                                                   按码洋${rule[0]*100}%收取，
                                                                   最高${rule[1]}元，
                                                                   最低${rule[2]}元
                        </c:otherwise>
					</c:choose>
				</td>
				<td>${deliveryInfo.description}</td>
			</tr>
		</c:forEach>
	</table>
</div>
