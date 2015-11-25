$(function() {
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
		var check = $("#productTextArea").val();
		if (check.search("^[\\s*\\d\\s]*$") != 0) {
			$("#productMessage").html("只能为数字");
		} else {
			$("#addProductDialog").dialog("close");
			var ids = $("#productTextArea").val().split('\n');
			var idsStr = "";
			for ( var i = 0; i < ids.length; i++) {
				idsStr += ids[i].replace(/(^\s*)|(\s*$)/g, "") + ",";
			}
			submitProduct(idsStr);

		}

	}
	if (which == 1) {
		var check = $("#giftTextArea").val();
		if (check.search("^[\\s*\\d\\s]*$") != 0) {
			$("#giftMessage").html("只能为数字");
		} else {
			$("#addGiftDialog").dialog("close");
			var ids = $("#giftTextArea").val().split('\n');
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
			+ '&numberType=' + numberType + '&type=0&isAdd=1';
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
			$("#productTable").html(data);
				$("#nodata").remove();
		}
	});
}

function submitGift(idsStr) {
	var numberType = $("#giftSelectType option:selected").val();
	var url = '/promotion/findProductOrGift' + '?numbers=' + idsStr
			+ '&numberType=' + numberType + '&type=1&isAdd=1';
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
			$("#giftTable").html(data);
			$("nodata").remove();
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
		for ( var i = 0; i < productQuantity.length; i++) {
			if (productQuantity[i].value == ""
					|| productQuantity[i].value.match(/^\d+$/) == null) {
				alert("商品数量必须为数字");
				return false;
			}
		}

	}
	if (gift == null) {
		alert("请添加赠品");
		return false;
	} else {
		for ( var i = 0; i < giftQuantity.length; i++) {
			if (giftQuantity[i].value == ""
					|| giftQuantity[i].value.match(/^\d+$/) == null) {
				alert("赠品数量必须为数字");
				return false;
			}
		}

	}
	return true;
}
