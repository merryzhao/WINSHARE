function checkForm(type) {
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
		audit(ids, len, type);
	}
}

function openSingleDialog(id) {
	$("#currentId").val(id);
	$("#verifySelectSingle").dialog('open');
}

$(function() {
	$("input[name='selectAll']").click(function() {
		if ($("input[name='selectAll']").attr("checked")) {
			$("input[name='item']").attr("checked", "checked");
			$("input[name='selectAll2']").attr("checked", "checked");
		} else {
			$("input[name='item']").removeAttr("checked");
			$("input[name='selectAll2']").removeAttr("checked");
		}
	});
	$("input[name='selectAll2']").click(function() {
		if ($("input[name='selectAll2']").attr("checked")) {
			$("input[name='item']").attr("checked", "checked");
			$("input[name='selectAll']").attr("checked", "checked");
		} else {
			$("input[name='item']").removeAttr("checked");
			$("input[name='selectAll']").removeAttr("checked");
		}
	});
	$("#verifyResult").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true
	});

	$("#verifySelectSingle").dialog(
			{
				autoOpen : false,
				bgiframe : false,
				modal : true,
				buttons : {
					"确定" : function() {
						$(this).dialog("close");
						auditSingle($("#currentId").val(), $(
								"#verifyTypeSingle option:selected").val());
					},
					"取消" : function() {
						$(this).dialog("close");
					}
				}
			});

	$("#verifySelectSingle2").dialog(
			{
				autoOpen : false,
				bgiframe : false,
				modal : true,
				buttons : {
					"确定" : function() {
						$(this).dialog("close");
						verifySingle($("#currentId").val(), $("#rowId").val(),
								$("#verifyTypeSingle2 option:selected").val());
					},
					"取消" : function() {
						$(this).dialog("close");
					}
				}
			});

	$("#verifySelectBatch").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		buttons : {
			"确定" : function() {
				$(this).dialog("close");
				checkForm($("#verifyTypeBatch option:selected").val());
			},
			"取消" : function() {
				$(this).dialog("close");
			}
		}
	});

	$("#verifyResult").bind("dialogclose", function() {
		window.location.href = "/promotion/unVerifyPromotion";
	});

	$("#verifySelectBatch").bind("dialogclose", function() {
		$("#batchTable").html("");
	});

	$("#verifySucess").bind("dialogclose", function() {
		window.location.href = "/promotion/unVerifyPromotion";
	});
	
	$("#verifyFail").bind("dialogclose", function() {
		window.location.href = "/promotion/unVerifyPromotion";
	});

	$("button[name='submit']").click(
			function() {
				var ids = document.getElementsByName('item');
				var count = 0;
				var len = ids.length;
				for ( var i = 0; i < len; i++) {
					if (ids[i].checked) {
						$("#batchTable").append(
								'<tr><td>' + $("#idTd" + i).html()
										+ '</td><td>'
										+ $("#titleTd" + i).html()
										+ '</td></tr>');
						count++;
					}
				}
				if (count == 0) {
					alert("请至少选择一项");
				} else {
					$("#verifySelectBatch").dialog('open');
				}
			});

	$("#verifySucess").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true
	});

	$("#verifyFail").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true
	});
	
	$("#verifyError").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true
	});
});

function audit(ids, len, type) {
	var str = "";
	for ( var i = 0; i < len; i++) {
		if (ids[i].checked) {
			str += ids[i].value + ",";
		}
	}
	var auditUrl = '/promotion/verifyBatch?item=' + str + '&type=' + type
			+ '&format=json';
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
				$("#passVerifyCount").html(data.passVerifyCount);
				$("#totalVerifyCount").html(data.totalVerifyCount);
				$("#failVerifyCount").html(data.totalVerifyCount-data.passVerifyCount);
				$("#verifyResult").dialog('open');
			} else {
				$("#verifyError").dialog('open');
			}
		}
	});
}

function auditSingle(promotionId, type) {
	var auditUrl = '/promotion/verify?promotionId=' + promotionId + '&type='
			+ type + '&format=json';
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
				$("#verifySucess").dialog('open');
			} else if (data.result == 2) {
				$("#verifyFail").dialog('open');
			} else {
				$("#verifyError").dialog('open');
			}
		}
	});
}

/**
 * 弹出对话框
 */
$("#requestInfo").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 250,
	height : 80
});
/**
 * 停用
 * 
 * @param {}
 *            promotionId
 */
function promotionStop(promotionId,id) {
	var url = '/promotion/stop?promotionId=' + promotionId + '&format=json';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			if (data.result == 1) {
				$("#requestInfo").html('停用成功');
				$("#operateTd" + id).html("");
				$("#statusTd" + id).html("已停用");
			} else {
				$("#requestInfo").html('停用失败 ');
			}
			$("#requestInfo").dialog("open");
		}
	});
}

function excuteVerifySingle(promotionId, id) {
	$("#currentId").val(promotionId);
	$("#rowId").val(id);
	$("#verifySelectSingle2").dialog('open');
}

function verifySingle(promotionId, id, type) {
	var auditUrl = '/promotion/verify?promotionId=' + promotionId + '&type='
			+ type + '&format=json';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : auditUrl,
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			if (data.result != 0) {
				$("#operateTd" + id).html(
						"<a href='#' onclick='promotionStop("+ promotionId +","+id+" );'>停用</a>");
				$("#statusTd" + id).html("审核通过");
				if (data.result == 2) {
					$("#statusTd" + id).html("审核未通过");
				}
				$("#assessorTd"+id).html(data.assessor);
				$("#assessTimeTd"+id).html(data.assessTime);
				$("#verifySucess2").dialog('open');
				$("#needVerifyPromotionCount").html($("#needVerifyPromotionCount").text() - 1);
			}
			if (data.result == 0) {
				$("#verifyError").dialog('open');
			}
		}
	});
}
