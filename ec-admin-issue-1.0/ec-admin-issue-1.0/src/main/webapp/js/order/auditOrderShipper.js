function checkForm() {
	var ids = document.getElementsByName('item');
	var count = 0;
	var len = ids.length;
	for ( var i = 0; i < len; i++) {
		if (ids[i].checked) {
			count++;
		}
	}
	if (count == 0) {
		alert('请至少选择一项!');
	} else {
		var str = "";
		for ( var i = 0; i < len; i++) {
			if (ids[i].checked) {
				str += ids[i].value + ",";
			}
		}
		document.getElementById("ids").value=str;
		document.getElementById("len").value=len;
		$("#confirmDiv").dialog("open");
	}
}

function confirmSingle(thisId){
	document.getElementById("thisId").value=thisId;
	$("#confirmDiv2").find("select[name='deliveryCompany']").val("-1")
	$("#confirmDiv2").dialog('open');	
}

$(function() {
	$("input[name='selectAll']").click(function() {
		if ($("input[name='selectAll']").attr("checked")) {
			$("input[name='item']").attr("checked", "checked");
			$("input[name='selectAll2']").attr("checked","checked");
		} else {
			$("input[name='item']").removeAttr("checked");
			$("input[name='selectAll2']").removeAttr("checked");
		}
	});
	$("input[name='selectAll2']").click(function() {
		if ($("input[name='selectAll2']").attr("checked")) {
			$("input[name='item']").attr("checked", "checked");
			$("input[name='selectAll']").attr("checked","checked");
		} else {
			$("input[name='item']").removeAttr("checked");
			$("input[name='selectAll']").removeAttr("checked");
		}
	});
	$("#auditResult").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true
	});

	$("#confirmDiv").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons: {
			"确定": function() {
				audit($("#ids").val(), $("#len").val());
				$( this ).dialog( "close" );
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	$("#confirmDiv2").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons: {
			"确定": function() {
				var deliveryCompanyId = $("#confirmDiv2").find("select[name='deliveryCompany']").val();
				auditSingle($("#thisId").val(), deliveryCompanyId);
				$( this ).dialog( "close" );
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});

	$("#auditResult").bind("dialogclose", function() {
		window.location.href = "/order/auditOrderShipper";
	});

	$("button[name='submit']").click(function() {
		checkForm();
	});
});

function audit(str, len) {
	var auditUrl = '/order/audit?item=' + str + '&format=json';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : auditUrl,
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			if (data.result == 1) {
				$("#preCount").html(data.preCount);
				$("#passCount").html(data.passCount);
				$("#currentCount").html(data.currentCount);
				if(data.currentCount!=0){
					$("#finalReason").html(data.finalReason);
					$("#reasonStr").html("原因：");	
				}	
				$("#auditResult").dialog('open');
			}
		}
	});
}
function auditSingle(orderId,deliveryCompanyId) {
	var auditUrl = '/order/' + orderId + '/auditOrderShipper?format=json&deliveryCompanyId='+deliveryCompanyId;
	$.ajax({
		async : false,
		cache : false,
		type : 'GET',
		url : auditUrl,
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			$("#message").html(data.message);
			$("#auditResult").dialog('open');
		}
//			if (data.result == 1) {
//				$("#preCount").html(data.preCount);
//			$("#passCount").html(data.passCount);
//				$("#currentCount").html(data.currentCount);
//			if(data.currentCount!=0){
//					$("#finalReason").html(data.finalReason);
//				$("#reasonStr").html("原因：");	
//				}
//				$("#message").html(data.message);			
//				$("#auditResult").dialog('open');
//			} else {
//				$("#message").html(data.message);
//				$("#auditResult").dialog('open');
//			}
	});
}
