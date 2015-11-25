<%@page pageEncoding="UTF-8" %><%@include file="/page/snippets/tags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文轩网-${survey.title}</title>
<link href="${serverPrefix}css2/survey/style.css?${version}" rel="stylesheet" type="text/css" />
</head>
<body>	
<center>
	<div class="main">
	    <h1 class="success">调查完成，谢谢参与</h1>
	    <h1 class="surveytitle">${survey.title}</h1>
	    <p class="message">等待<span id="time">10</span>秒后自动跳转，或点击<a href="${referer}" target="_top">这里</a></p>  
	    <input type="hidden" id="referer" value="${referer}"/>
	</div>
</center>
<div class="footer">Copyright(C) 四川文轩在线电子商务有限公司 2000-2012, All Rights Reserved</div>
<script type="text/javascript">
var overDate = 10000;
var NowTime = new Date();
var EndTime= new Date(NowTime.getTime() + overDate);
function getRTime(){
	NowTime = new Date();
	var nMS=EndTime.getTime() - NowTime.getTime();
	var nD=Math.floor(nMS/(1000 * 60 * 60 * 24));
	var nH=Math.floor(nMS/(1000*60*60)) % 24;
	var nM=Math.floor(nMS/(1000*60)) % 60;
	var nS=Math.floor(nMS/1000) % 60;
	var nMS=Math.floor(nMS/100) % 10;
	if(nD>= 0){
		document.getElementById("time").innerHTML=nS;
	}
	else {
		var url = document.getElementById("referer").value;
		window.location.href=url;
		return;
	}
	setTimeout("getRTime()",100);
}
window.onload=function(){
	getRTime();
} 
</script>
</body>
</html>
