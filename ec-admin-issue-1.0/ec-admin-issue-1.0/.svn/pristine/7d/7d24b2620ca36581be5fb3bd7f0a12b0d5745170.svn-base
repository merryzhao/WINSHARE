<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib prefix="winxuan" tagdir="/WEB-INF/tags"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>用户创建</title>
	<meta content="text/html; charset=utf-8" http-equiv="content-type"/>
	<%@include file="../snippets/meta.jsp"%>
	<link type="text/css"
	href="${pageContext.request.contextPath}/css/authority/authority.css"
	rel="stylesheet" />
</head>
<body>
	<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-system.jsp"%>
		<div class="frame-main">
			<div class="frame-main-inner">
			<div>
		 		 <form action="/switchconfig/save" method="post" >
				    <table class="view" width="100%" border="0" cellspacing="0">
				      <tr>
				        <td class="txt_right" style="width: 255px;">类型：</td>
				        <td>
				            <winxuan:switchConfig />
				        </td>
				      </tr>
				      <tr>
				        <td class="txt_right" style="width: 255px;">是否生效：</td>
				        <td>
				        	<label>是<input type="radio" name="valueOpen" value="true" checked="checked"></label>&nbsp;&nbsp;&nbsp;&nbsp;
				        	<label>否<input type="radio" value="false" name="valueOpen"></label>
						</td>
				      </tr>
				      <tr>
				        <td class="txt_right" style="width: 255px;">描述：</td>
				        <td>
				            <textarea name="description" style="width:157px; height: 55px;"  rows="2" cols="19"></textarea>
						</td>
				      </tr>
				      <tr>
				        <td>&nbsp;</td>
				        <td>
				        <input type="submit" value="保	存">
				        </td>
				      </tr>
				    </table>
			    </form>
			</div>
 		</div>
 		</div>
 	</div>
 	<%@include file="../snippets/scripts.jsp"%>
 	<c:if test="${isVerification!=null}">
    		<script type="text/javascript">
	    			if(${isVerification}){
	    				alert("新增成功");
	    		 	}else if(${isVerification}==false){
	    		 		alert("该类型已经存在");
	    		 	}
    		</script>
    </c:if>
</body>
</html>