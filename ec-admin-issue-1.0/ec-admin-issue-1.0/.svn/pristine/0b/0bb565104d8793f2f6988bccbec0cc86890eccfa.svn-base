function submitEdit() {
	if (!mainvalidate()) {
		return false;
	}
	if (!validateProduct()) {
		return false;
	}
	document.promotionform.submit();
}

function number() {
	var number = "onkeydown=\"if(!isMoney(event)){if(document.all){event.returnValue = false;}else{event.preventDefault();}}\"";
	return number;
}
function isMoney(event) {
	var isMoney = (event.keyCode == 8 || event.keyCode == 190)
			|| (event.keyCode > 47 && event.keyCode < 58);
	return isMoney;
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

// 图片预览
function previewImage(divImage,upload,width,height) { 
if(checkPic()){
		var imgPath; 
		 // 图片路径
	 		var Browser_Agent=navigator.userAgent;
	 		// 判断浏览器的类型
		if(Browser_Agent.indexOf("Firefox")!=-1){
 		    // 火狐浏览器
    		imgPath = upload.files[0].getAsDataURL();               
    		document.getElementById(divImage).innerHTML = "<img id='imgPreview' src='"+imgPath+"' width='"+width+"' height='"+height+"'/>";
	}else{
    	// IE浏览器
		// 先去掉本来要显示的照片
		document.getElementById('detailimg').style.display="none";
        var Preview = document.getElementById(divImage);
        Preview.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = upload.value;
        Preview.style.width = width;
        Preview.style.height = height;
	}  
}
}
$(function() {
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
	$("#addProductDialog").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"提交" : function() {
				checkTextArea(0);
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#addGiftDialog").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"提交" : function() {
				checkTextArea(1);
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#addGift").click(function() {
		$("#giftMessage").html("");
		$("#addGiftDialog").dialog('open');
	});

	$("#addProduct").click(function() {
		$("#productMessage").html("");
		$("#addProductDialog").dialog('open');
	});
});

function checkTextArea(which) {
	if (which == 0) {
		var oldInputs = $("input[name='productSaleIds']");
		var check = $("#productTextArea").val();
		if (check.search("^[\\s*\\d\\s]*$") != 0) {
			$("#productMessage").html("只能为数字");
		} else {
			$("#addProductDialog").dialog("close");
			var ids = $("#productTextArea").val().split('\n');
			for(var i = 0; i < ids.length; i++){
				var id =ids[i].replace(/(^\s*)|(\s*$)/g, "");
				for(var j=0;j<oldInputs.length;j++){
					if(oldInputs[j].value==id){
						ids[i]="";
						break;
					}
				}
			}
			var idsStr = "";
			for ( var i = 0; i < ids.length; i++) {
				idsStr += ids[i].replace(/(^\s*)|(\s*$)/g, "") + ",";
			}
			submitProduct(idsStr);
		}

	}
	if (which == 1) {
		var oldInputs = $("input[name='giftIds']");
		var check = $("#giftTextArea").val();
		if (check.search("^[\\s*\\d\\s]*$") != 0) {
			$("#giftMessage").html("只能为数字");
		} else {
			$("#addGiftDialog").dialog("close");
			var ids = $("#giftTextArea").val().split('\n');
			for(var i = 0; i < ids.length; i++){
				var id =ids[i].replace(/(^\s*)|(\s*$)/g, "");
				for(var j=0;j<oldInputs.length;j++){
					if(oldInputs[j].value==id){
						ids[i]="";
						break;
					}
				}
			}
			var idsStr = "";
			for ( var i = 0; i < ids.length; i++) {
				idsStr += ids[i].replace(/(^\s*)|(\s*$)/g, "") + ",";
			}
			submitGift(idsStr);
		}
	}
}

function submitProduct(idsStr) {
	var numberType = $("#productSelectType option:selected").val();
	var url = '/promotion/findProductOrGift' + '?numbers=' + idsStr
			+ '&numberType=' + numberType + '&type=0&isAdd=0';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,
		dataType : 'html',
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			$("#productTable").append(data);
			$("#nodata").remove();
		}
	});
}

function submitGift(idsStr) {
	var numberType = $("#giftSelectType option:selected").val();
	var url = '/promotion/findProductOrGift' + '?numbers=' + idsStr
			+ '&numberType=' + numberType + '&type=1&isAdd=0';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,
		dataType : 'html',
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			$("#giftTable").append(data);
			$("#nodata").remove();
		}
	});
}
function deleteTr(index, type) {
	if (type == 0) {
		$("#productTr" + index).remove();
		if($("#product_table tr").length==1){
			$("#product_table tr:last").after("<tr id=nodata><td colspan=20>暂无数据</td></tr>");
		}
	} else {
		$("#giftTr" + index).remove();
		if($("#gift_table tr").length==1){
			$("#gift_table tr:last").after("<tr id=nodata><td colspan=20>暂无数据</td></tr>");
		}
	}
}

// 验证买商品送商品
function validateProduct() {
	var product = $("#productQuantity").html();
	var gift = $("#giftQuantity").html();
	var productQuantity = $("input[name='productQuantity']");
	var giftQuantity = $("input[name='giftQuantity']");
	if (product == null) {
		alert("请添加商品");
		return false;

	} else {
		for(var i=0;i<productQuantity.length;i++){
			if(productQuantity[i].value==""||productQuantity[i].value.match(/^\d+$/)==null){
				alert("商品数量必须为数字");
				return false;
			}
		}
		
	}
	if (gift == null) {
		alert("请添加赠品");
		return false;
	} else{
		for(var i=0;i<giftQuantity.length;i++){
			if(giftQuantity[i].value==""||giftQuantity[i].value.match(/^\d+$/)==null){
				alert("赠品数量必须为数字");
				return false;
			}
		}
		
	}
	return true;
}
