<%@ page contentType="text/html;charset=utf-8"%>
<script type="text/javascript" >
var _rsClientId="124";
var _rsStepId=0;
var _rsStepTp=0;
var _rsDWLP=location.protocol.indexOf("https")>-1?"https:":"http:";
var _rsDWDN="//cv.dmclick.cn/";
var _rsDWURL = _rsDWLP+_rsDWDN+"trk.js";
document.write(unescape("%3Cscript src='" + _rsDWURL + "' type='text/javascript'%3E%3C/script%3E"));
</script>
<% 
	request.getRequestDispatcher("/track/5").forward(request, response);
%>


