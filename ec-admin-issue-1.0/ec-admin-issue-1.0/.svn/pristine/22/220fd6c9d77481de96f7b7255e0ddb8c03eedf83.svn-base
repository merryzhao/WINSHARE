$(document).ready(function() {
 	var dates = $( "#promotionStartDate, #promotionEndDate" ).datepicker({
			onSelect: function( selectedDate ) {
				var option = this.id == "promotionStartDate" ? "minDate" : "maxDate",
					instance = $( this ).data( "datepicker" ),
					date = $.datepicker.parseDate(
						instance.settings.dateFormat ||
						$.datepicker._defaults.dateFormat,
						selectedDate, instance.settings );
				dates.not( this ).datepicker( "option", option, date );
			}
		});
			
 	$('#promotionStartTime').timepicker({});          
	$('#promotionEndTime').timepicker({});
	$("#present_send").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width:600,
		height:350
	});	
	// 删除此行
	$("#delete").live("click", function() {
		$(this).parent("label").parent("div").remove();
	});
	var promotionType = $("#promotionType").val();
		if(promotionType == 20005){
	   		var url = "/promotion/findruleslist?format=json"; 
	 	   $.ajax({
			async : false,
			cache : false,
			type : 'POST',
				data : {
				"id" : $("#pid").val() 
			},
			url : url,
			error : function() {// 请求失败处理函数
				alert('请求失败');
			},
			success : function(data) { // 请求成功后回调函数。
				   if (data.message != 0) {
					   havedata=true; 
	               var orderRulesBeans=data.orderRulesBeans;
	               if(orderRulesBeans[0].manner=='21001'){
	            	   $("input[name='smanner'][value=21001]").attr("checked",true);  
	            	   disflag=true;
	  				 $("#tidu").css('display','none');
	 				 $("#putong").css('display','block');
	            	   var list=orderRulesBeans[0].orderPresnetBeans;
	                   var trtext=""; 
	                   var textdata="";
	            	   $("#addpresentitems0").parent().find("input[name='sendprice']").val(orderRulesBeans[0].minAmount); 
	            	   textdata="{\""+orderRulesBeans[0].minAmount+"\":[";
	             	   for(var i=0;i<list.length;i++){
	              		  trtext=trtext+'<tr>'+
	      				  '<td class='+"'idclass'"+'>'
	      				  +"<input name='presentchoice' value='"+list[i].presentBatch.id+"' type='checkbox' checked='checked'  />"
	      				  +list[i].presentBatch.id+
	      			      '</td>'+
	   					  '<td>'+list[i].presentBatch.batchTitle+'</td>'+
	   					  '<td>'+list[i].presentBatch.value+'</td>'+
	   					  '<td>'+list[i].presentBatch.productType+'</td>'+
	   					  '<td>'+list[i].presentBatch.orderBaseAmount+'</td>'+
	   					  '<td>'+"<input type='text' class='nomey' size='10' value='"+list[i].presentNum+"'>"+'</td>'+
	   					  '<td>'+"<a href='javascript:void(0);' class='deleteItems'>删除</a>"+'</td>'+
	   					  '</tr>' ;
	            		  textdata=textdata+"{\"id\":\""+list[i].presentBatch.id+"\",\"count\":\""+list[i].presentNum+"\"},";
	                     }
	             	  textdata=textdata+"]}";
	           	    	$("#putongdata").text(textdata);
	           	    	$('body').data("addpresentitems0"+keyName,trtext);
	               }else if(orderRulesBeans[0].manner=='21002'){
	            	$("input[name='smanner'][value=21002]").attr("checked",true);  
	         	   disflag=false;
					 $("#tidu").css('display','block');
	 				 $("#putong").css('display','none');
	 			   var oindex=1;
	               for(var j=0;j<orderRulesBeans.length;j++){
	             	   var list=orderRulesBeans[j].orderPresnetBeans;
	                   var trtext=""; 
	                   var textdata="";
	                   textdata="\""+orderRulesBeans[j].minAmount+"\":[";
	              	   for(var i=0;i<list.length;i++){
	              		  trtext=trtext+'<tr>'+
	      				  '<td class='+"'idclass'"+'>'
	      				  +"<input name='presentchoice' value='"+list[i].presentBatch.id+"' type='checkbox' checked='checked'  />"
	      				  +list[i].presentBatch.id+
	      			      '</td>'+
	   					  '<td>'+list[i].presentBatch.batchTitle+'</td>'+
	   					  '<td>'+list[i].presentBatch.value+'</td>'+
	   					  '<td>'+list[i].presentBatch.productType+'</td>'+
	   					  '<td>'+list[i].presentBatch.orderBaseAmount+'</td>'+
	   					  '<td>'+"<input type='text' class='nomey' size='10' value='"+list[i].presentNum+"'>"+'</td>'+
	   					  '<td>'+"<a href='javascript:void(0);' class='deleteItems'>删除</a>"+'</td>'+
	   					  '</tr>' ;
	          		      textdata=textdata+"{\"id\":\""+list[i].presentBatch.id+"\",\"count\":\""+list[i].presentNum+"\"},";
	                     }
	              	     textdata=textdata+"],";
	          		      if(oindex>1){
	          		        	$("#tidudata").append("<p class='tidudatap' id='"+"addpresentitems"+keyindex+"p"+"'></p>");
	            		  		$("#tidu").append("<p>订单满<input value='"+orderRulesBeans[j].minAmount+"' type='text' size='10' name='sendprice'>元送  <a href='javascript:void(0);' id='addpresentitems"+keyindex+"' class='mark' onclick='showdiv(this)'>礼券 </a>  <a href='javascript:void(0);' class='addpresentitems"+keyindex+"' onclick='removeadditems(this)'>删除>> </a></p>");
	            		  		$('body').data("addpresentitems"+keyindex+keyName,trtext);
	                 		    $("#"+"addpresentitems"+keyindex+"p").text(textdata);
	                   		    keyindex++;
	         		      }else{
	        		        	$("#tidudata").append("<p class='tidudatap' id='"+"addpresentitems"+oindex+"p"+"'></p>");
	                    	   $("#addpresentitems"+oindex).parent().find("input[name='sendprice']").val(orderRulesBeans[j].minAmount); 
	                    	   $('body').data("addpresentitems"+oindex+keyName,trtext);
	               		      $("#"+"addpresentitems"+oindex+"p").text(textdata);
	               		      oindex++; 
	         		      }
	               }
	           		   
	
	                 }
	   			}
	                
	               
			}              
	               
	 });}else if(promotionType == 20009){
	 		  var url = "/promotion/findregruleslist?format=json"; 
	 	 	   $.ajax({
	 			async : false,
	 			cache : false,
	 			type : 'POST',
	 			data : {"id" : $("#pid").val() },
	 			url : url,
	 			error : function() {// 请求失败处理函数
	 				alert('请求失败');
	 			},
	 			success : function(data) { // 请求成功后回调函数。
	 				if (data.message != 0) {
	 					havedata=true; 
	 					var registerRulesBean=data.registerRulesBean;
	 	            	var registerPresentBean=registerRulesBean.registerPresentBean;
	 	                var trtext=""; 
	 	                var textdata="";
	 	            	textdata="{\"present\":[";
	 	            	trtext=trtext+'<tr><td class='+"'idclass'"+'>'
	 	      				  +"<input name='presentchoice' value='"+registerPresentBean.presentBatch.id+"' type='checkbox' checked='checked'  />"
	 	      				  +registerPresentBean.presentBatch.id+
	 	      			      '</td>'+
	 	   					  '<td>'+registerPresentBean.presentBatch.batchTitle+'</td>'+
	 	   					  '<td>'+registerPresentBean.presentBatch.value+'</td>'+
	 	   					  '<td>'+registerPresentBean.presentBatch.productType+'</td>'+
	 	   					  '<td>'+registerPresentBean.presentBatch.orderBaseAmount+'</td>'+
	 	   					  '<td>'+"<input type='text' class='nomey' size='10' value='"+registerPresentBean.presentNum+"'>"+'</td>'+
	 	   					  '<td>'+"<a href='javascript:void(0);' class='deleteItems'>删除</a>"+'</td>'+
	 	   					  '</tr>' ;
	 	            		  textdata=textdata+"{\"id\":\""+registerPresentBean.presentBatch.id+"\",\"count\":\""+registerPresentBean.presentNum+"\"},";
	 	             	  textdata=textdata+"]}";
	 	           	    	$("#presentdata").text(textdata);
	 	           	    	$('body').data("addpresentitems3"+keyName,trtext);
	 				}
	 			}
	 	 	   });	
	 	}else{
		   alert('请求错误!');
	 	}
});
function storedata(){
	var textdata="";
	havedata=false;
	var count="";
	var flag=true;
	if(disflag){
		textdata="{\""+$("#"+key).parent().find("input[name='sendprice']").val()+"\":[";
		$("#presentitems").find(":checkbox:checked").each(function(){
 			count=$(this).parent().parent().find("input[class='nomey']").val();
 			if(!/^[0-9]{1,9}$/.test(count)){
				flag=false;
				return flag;
			}
			havedata=true;
			textdata=textdata+"{\"id\":\""+$(this).val()+"\",\"count\":\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"},";
  		 });
		textdata=textdata+"]}";
		$("#putongdata").text(textdata);
	}
	else{
		if($("#presentitems").find(":checkbox:checked").length>0){
 		textdata="\""+$("#"+key).parent().find("input[name='sendprice']").val()+"\":[";
 		$("#presentitems").find(":checkbox:checked").each(function(){
			count=$(this).parent().parent().find("input[class='nomey']").val();
			if(!/^[0-9]{1,9}$/.test(count)){
				flag=false;
				return flag;
			}
			havedata=true;
			textdata=textdata+"{\"id\":\""+$(this).val()+"\",\"count\":\""+$(this).parent().parent().find("input[class='nomey']").val()+"\"},";
  		 });
		  textdata=textdata+"],";
		} 
		$("#"+key+"p").text(textdata);
 	}
  	return flag;
}

/**
 * 添加商家
 */
function addSellers() {
	// 下拉列表选中项
	var sellers = $("#seller_select option:selected");
	var value = sellers.val();
	var name = sellers.text();
	// 卖家隐藏id
	var seller_values = $("input[name='sellers']");
	var isSame = true;
	// 如果卖家中已有即将要添加的卖家 则isSame = false;
	if (jQuery.support.opacity) {
		for ( var i = 0; i < seller_values.length; i++) {
			if (value == seller_values.eq(i).val()) {
				isSame = false;
			}
		}
	} else {
		if (seller_values.length != 0) {
			for ( var i = 0; i < seller_values.length; i++) {
				if (value == seller_values.eq(i).val()) {
					isSame = false;
				}
			}
		}
	}
	// 如果isSame = true;则添加此卖家
	if (isSame) {
		var div = "<div class='seller'>"
				+ "<label class='text'>"
				+ name
				+ "</label>"
				+ "<label><a id='delete' class='delete' href='javascript:void(0);'>删除</a></label>"
				+ "<input type='hidden' name='sellers' value='" + value + "'>"
				+ "</div> "
		var seller_list = $("#seller_list");
		seller_list.append(div);
	}
}

function submitto() {
	var selectedtype = $("#promotionType").val();
	document.promotionform.action = getupdatecontroller(selectedtype);
	if (!mainvalidate()) {
		return false;
	}
	if (selectedtype == 20005) {
		if (!orderPresentValidate()) {
			return false;
		}
	}
	if(selectedtype == 20009){
		if(!registerPresentValidate()){
			return false;
		}
	}
	document.promotionform.submit();
} 
function getupdatecontroller(selectedtype) {
	var url;
	if (selectedtype == 20005) {
		// 订单送礼券活动
		url = '/promotion/presentfororder';
	} else if (selectedtype == 20009){
		//注册送礼券
		url = '/promotion/updatePresentForNewCustomer';
	} else {
		alert('do not have done')
	}
	return url;
}
function reset(){
	document.promotionform.reset();
}

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
function mainvalidate(){
  	if($("#promotionTitle").val()==""){
		alert("活动标题不能为空");
		return false;
	}
	else if($("#promotionStartDate").val()==""){
		    alert("活动开始日期不能为空");
			return false;
		}
	else if($("#promotionEndDate").val()==""){
	    alert("活动结束日期不能为空");
		return false;
	}
    return true;
}
 