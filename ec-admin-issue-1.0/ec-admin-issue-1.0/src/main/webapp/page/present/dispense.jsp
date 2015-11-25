<%@page pageEncoding="UTF-8"%>
<%@include file="../snippets/tags.jsp"%>
<%@ taglib uri="http://www.winxuan.com/tag/token" prefix="token"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../snippets/meta.jsp"%>
<style type="text/css">
#textarea1 {
	width: 180px;
	height: 200px;
	margin-bottom: 0px;
}
table tr {
	height: 20px;
}
</style>
<title>分发礼券</title>
</head>
<body>
	<div class="frame">
		<!-- 引入top部分 -->
		<%@include file="../snippets/frame-top.jsp"%>
		<!-- 引入left菜单列表部分 -->
		<%@include file="../snippets/frame-left-website.jsp"%>
		<div class="frame-main">
			<!-- 核心内容部分div -->
			<div class="frame-main-inner" id="content">
				<div id="requestInfo" align="center"
					style="font-size: 15px; font-weight: bold;"></div>
				<div id="fenfa" align="left">
					<table>
						<tr>
							<td colspan="2"><h4>分发礼券</h4></td>
						</tr>
						<tr>
							<td align="left">批次：<font color="red">${presentBatch.id} </font> </td>
							<td align="center">礼券金额： ${presentBatch.value}元</td>
						</tr>
						<tr>
							<td colspan="2">名称: ${presentBatch.batchTitle}</td>
						</tr>
						<tr>
							<td align="left">分发方式：</td>
							<td align="center"><input type="radio" name="dispenseMethod"
								checked="checked" value="1" />分发到账号 <input type="radio"
								name="dispenseMethod" value="-1" />导出excel</td>
						</tr>
					</table>
				</div>

				<div id="ff_customer">
					<form action="/present/dispense" method="post"
						onsubmit="return checkdispense();" enctype="multipart/form-data">
						<token:token></token:token>
						<table>
							<tr>
								<td colspan="2"><input type="hidden" name="id"
									value="${presentBatch.id }" />可分发数量：</td>
								<td colspan="2"><c:if test="${presentBatch.num==0 }">不限</c:if>
									<c:set var="remainNum" value="${presentBatch.num-presentBatch.createdNum}"></c:set>
									<c:if test="${presentBatch.num!=0 }"><c:if test="${remainNum>0}" >${remainNum}</c:if><c:if test="${remainNum<=0}" ><font color=red>可分发数量为0，不能分发</font><span id="flag" style="display:none;">nosubmit</span></c:if></c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2">分发类型：</td>
								<td><input type="radio" name="dispenseType"
									checked="checked" value="customer" />账户 <input type="radio"
									name="dispenseType" value="email" />邮箱</td>
								<td></td>
							</tr>
							<tr>
								<td colspan="3"><textarea id="textarea1" name="dispenseObj"></textarea><br/>*数据一行一条</td>
								<td >&nbsp;&nbsp;&nbsp;<input type="file" id="file" name="hiddenfile" /></td>
							</tr>
							<tr></tr>
							<tr>
								<td colspan="2"><button type="submit" id="submit">确定</button></td>
								<td colspan="2"><button type="reset">重置</button></td>
							</tr>
						</table>
					</form>
				</div>
				<div id="excel" style="margin: 30px 10px 0px 0px;">
					<c:if test="${presentBatch.num!=0 }">
						<form action="/excel/presents" method="post" id="excelForm">
							<input type="hidden" name="format" value="json" /> <input
								type="hidden" name="presentBatchId" value="${presentBatch.id }" />
							<button type="button" onclick="ajaxForm();">导出Excel2003格式</button>
						</form>
					</c:if>
					<c:if test="${presentBatch.num==0 }">
						<label> 无法导出分发数量无限的礼券</label>
					</c:if>
				</div>
				<div id="message" style="text-align: center;"></div>
			</div>
		</div>
	</div>
	<%@include file="../snippets/scripts.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/js/jquery.validate.defaults.js"></script>
	<script>	
		$(function() {
			$("#message").dialog({
				autoOpen : false,
				bgiframe : false,
				modal : true
			});
			if($("#flag").text()=="nosubmit"){
				$("#submit").attr({"disabled":true});
			}
			$("#excel").hide();
			$("input[name=dispenseMethod]").change(function() {
				$("#ff_customer").toggle();
				$("#excel").toggle();
			});
			$("input[name=dispenseType]").change(function() {
				$("#notEmail").toggle();
			})

			$("input[name=isEmail]").change(function() {
				emailChange($(this));
			})
			
		});
	</script>
	<script type="text/javascript">
		window.CKEDITOR_BASEPATH = '../../ckeditor/';
	</script>
	<script type="text/javascript"
		src="${contextPath}/ckeditor/ckeditor.js?t=B37D54V"></script>
	<script type="text/javascript">
	$().ready(function(){
		$("#requestInfo").dialog({
			autoOpen : false,
			bgiframe : true,
			modal : true,
			width : 250,
			height : 80
		});
	});
		function checkdispense() {
			if ($("#file").val() == "" && $("#textarea1").val() == "") {
				$("#message").html(
						"请输入需要分发礼券的用户 / 邮箱地址<br>或者<br>使用导入功能导入用户 / 邮箱地址");
				$("#message").dialog('open');
				return false;
			}
			return true;
		}
		$("#file").change(function() {
			var strs = '';
			var nums = 0;
			var Arr = new Array();
			try {
				var fso = new ActiveXObject("scripting.filesystemobject");
				var txtstream = fso.openTextFile($("#file").val(), 1, false);
			} catch (e) {
				alert("导入txt功能只支持IE浏览器");
				return;
			}
			while (!txtstream.atEndOfLine) {
				Arr.push(txtstream.readLine());
			}
			txtstream.close();
			txtstream = null;
			fso = null;
			for ( var i = 0; i < Arr.length; i++) {
				strs = strs + Arr[i] + "\n";
				nums += 1;
			}
			if ($("#textarea1").val() == "") {
				$("#textarea1").val(strs);
			} else {
				$("#textarea1").val($("#textarea1").val() + "\n" + strs);
			}

		});
		function ajaxForm() {
			$("#requestInfo").html('正在导出 ,请稍后......');
			$("#requestInfo").dialog("open");
			var url = "/excel/presents"
			form = $("#excelForm").serialize(); 
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : 'json',
				data : form,
				url : url,
				error : function() {// 请求失败处理函数
					$("#requestInfo").html('导出失败');
				},
				success : function(msg) { // 请求成功后回调函数。
					if (msg.isDown) {
						$("#requestInfo").dialog("close");
						alert('1' + msg.path)
						window.location.href = "/files/"+msg.path;
					} else {
						alert('2' + msg.path)
						$("#requestInfo").html(
								'由于数据较多，耗时较长，程序将在后台生成文件，请稍后在"用户附件管理"下载该文件 ');
					}
				}
			});
		}
	</script>
</body>
</html>