<%@page pageEncoding="UTF-8"%>
<style>
#preview {
	display: inline-block;
	width: 80px;
	height: 80px;
	background-color: #CCC;
}
</style>
<script type="text/javascript">
function checkPic(){
	var picPath=document.getElementById("picPath").value;
		var type=picPath.substring(picPath.lastIndexOf(".")+1,picPath.length).toLowerCase();
	if(type!="jpg"&&type!="bmp"&&type!="gif"&&type!="png"){
		alert("请上传正确的图片格式");
		return false;
	}
	return true;
}

//图片预览
function previewImage(divImage,upload,width,height) { 
if(checkPic()){
		var imgPath; 
		 //图片路径     
	 		var Browser_Agent=navigator.userAgent;
	 		//判断浏览器的类型   
		if(Browser_Agent.indexOf("Firefox")!=-1){
 		    //火狐浏览器
    		imgPath = upload.files[0].getAsDataURL();               
    		document.getElementById(divImage).innerHTML = "<img id='imgPreview' src='"+imgPath+"' width='"+width+"' height='"+height+"'/>";
	}else{
    	//IE浏览器
		//先去掉本来要显示的照片
		document.getElementById('detailimg').style.display="none";
        var Preview = document.getElementById(divImage);
        Preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = upload.value;
        Preview.style.width = width;
        Preview.style.height = height;
	}  
}
}
</script>
<div id="preview"
	style="display: inline; filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);">
	<img id="detailimg"
		<c:if test="${empty  promotion.advertImage}">src=/imgs/promotion/default.jpg </c:if>
		<c:if test="${not empty promotion.advertImage}">src=${promotion.advertImage} </c:if>
		width="80" height="80">
</div>
<input type="file" id="picPath" name="localfile" size="8"
	onChange="previewImage('preview',this,80,80);" />
