$().ready(function(){
	$("#sbm").bind("click",function(){
		var fn = $("#fileName").val();
		if(fn == ""){
			alert("请选择上传的Excel文件");
			return false;
		}
		if(fn.lastIndexOf(".xls") == -1 || fn.lastIndexOf(".xls") == -1){
			alert("上传的文件必须为Excel文件");
			return false;
		}
		$("#excelForm").submit();
	/*	alert("该功能已停用！");*/
	})

   $("#productPriceUpdateButton").bind("click",function(){
	   if(!confirm("确认批量调整商品价格嘛？")){
		   return false;
	   }else{
		  $("#priceUpdateing").submit();
		  /* alert("该功能已停用！");*/
	   }
    })
   
   $(".delectpro").live("click",function(){
 	   if(!confirm("确认删除该条记录？")){
			return;
		}
	    var obj=$(this).parent().parent();
	    obj.remove();
		chackValue();
   })
});
/*function chackValue(){
	var cv = document.getElementsByName("checkValue");
	document.getElementById("productPriceUpdateButton").removeAttribute("disabled");
	if(cv.length == 0){
		document.getElementById("productPriceUpdateButton").setAttribute('disabled',"true");
	}
	for(var i = 0; i < cv.length; i++){
		if(cv[i].value == 'true'){
			document.getElementById("productPriceUpdateButton").setAttribute('disabled',"true");
			return false;
		}
	}
}*/