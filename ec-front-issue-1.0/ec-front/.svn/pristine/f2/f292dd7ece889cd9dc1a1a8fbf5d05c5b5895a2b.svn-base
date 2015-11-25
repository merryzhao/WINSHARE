<%@page contentType="text/html; charset=UTF-8"%>
<%response.setHeader("no-cache","true");%>
<html>
	<head>
		<meta content="text/html;charset=UTF-8">
		<title>winxuan door</title>
	</head>
	<body>
		<script type="text/javascript">
			try{
				top["${param.callback}"]('${param.status}','${param.errorMsg}');				
			}catch(e){
				throw new Error("login callback failed :"+e.message);
			}
		</script>
	</body>
</html>