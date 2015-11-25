<%@page pageEncoding="UTF-8"%>
<%@include file="../../snippets/tags.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>修改默认区域</title>
<%@include file="../../snippets/meta.jsp"%>
<meta content="text/html; charset=utf-8" http-equiv="content-type" />
</head>
<body>
<div class="frame" >
		<!-- 引入top部分 -->
		<%@include file="../../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../../snippets/frame-left-system.jsp"%>
	<div class="frame-main">
		<h4>添加修改默认区域</h4>
		<form action="/default/saveupdate" method="post">
		<div id="roadmap-body">
			<table class="list-table">
				<tr>
					<td class="property">区域<label class="red">*</label>
						<select areaLevel="country" name="directionId" id="country">
								<option value="-1">请选择国家</option>
						</select> 
						<select areaLevel="province" name="provinceId" id="province">
								<option value="-1">请选择省份</option>
						</select>
						<select areaLevel="city" name="cityId" id="city">
								<option value="-1">请选择城市</option>
						</select>
						<select areaLevel="district" name="parent.id" id="district">
								<option value="-1">请选择区县</option>
						</select> 
						<select areaLevel="town" name="id" id="town">
								<option value="-1">请选择乡镇</option>
						</select>
						<input value="提交" type="button" onclick="townsubmit()" />
					<c:if test="${success==true }">
							<script type="text/javascript">
								alert("操作成功");
							</script>
					</c:if>
					</td>
					<td>
						<input type="hidden" value="${defaultForm.directionId }" name="defaultdirection"/>
						<input type="hidden" value="${defaultForm.provinceId }" name="defaultprovince"/>
						<input type="hidden" value="${defaultForm.cityId }" name="defaultcity"/>
						<input type="hidden" value="${defaultForm.districtId }" name="defaultdistrict"/>
						<input type="hidden" value="${defaultForm.town }" name="defaulttown"/>
					</td>
				</tr>
			</table>
		</div>
		</form>
	</div>
</div>
 	<%@include file="../../snippets/scripts.jsp"%>
	<script type="text/javascript" src="${contextPath}/js/area/areadata.js"></script>
	<script type="text/javascript"src="${contextPath}/js/area/areaevent.js"></script>
	<script type="text/javascript">
		var district = $("input[name=defaultdistrict]").val();
		if(district.trim()!=""){
			var direction = parseInt($("input[name=defaultdirection]").val());
			if(direction==6119 ||direction==6120 ||direction==6121 ||direction==6122 ||direction==6123 ||direction==6124||direction==6125 ){
				direction=23;
			}
			var town = $("input[name=defaulttown]").val();
			town = town.trim()!=""?town:-1;
				initArea(direction, $("input[name=defaultprovince]").val(), $("input[name=defaultcity]").val(),district, town);
		}else{
			initArea(23, 175, -1, -1, -1);
		}
		
		function townsubmit(){
			if(parseInt($("#town").val())!=-1){
				$("form").submit();
			}else{
				alert("请选择有效的区域信息");
			}
		}
	</script>
</body>
</html>