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
function openType(type, id) {
	if (type == 1) {
		$("#currentId").val(id);
		$("#verifySelectSingle").dialog('open');
	}
	if (type == 0) {
		$("#verifySelectBatch").dialog('open');
	}
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

	$("#verifySelectSingle").dialog(
			{
				autoOpen : false,
				bgiframe : false,
				modal : true,
				height:200,
				buttons : {
					"确定" : function() {
						$(this).dialog("close");
						auditSingle($("#currentId").val(), $(
								"#verifyTypeSingle option:selected").val())
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
				height:200,
				buttons : {
					"确定" : function() {
						$(this).dialog("close");
						verifySingle($("#currentId").val(), $("#rowId").val(),
								$("#verifyTypeSingle option:selected").val())
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
		height:200,
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

	$("#auditResult").bind("dialogclose", function() {
		window.location.href = "/presentbatch/unVerifyPresentBatch";
	});

	$("button[name='submit']").click(function() {
		$("#verifySelectBatch").dialog('open');
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
});

function audit(ids, len, type) {
	var str = "";
	for ( var i = 0; i < len; i++) {
		if (ids[i].checked) {
			str += ids[i].value + ",";
		}
	}
	var auditUrl = '/presentbatch/verifyBatch?item=' + str + '&type=' + type
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
				$("#auditResult").dialog('open');
			}
		}
	});
}
function auditSingle(presentBatchId, type) {
	var auditUrl = '/presentbatch/verify?presentBatchId=' + presentBatchId
			+ '&type=' + type + '&format=json';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : auditUrl,
		error : function() {// 请求失败处理函数
			alert('请求失败');
		},
		success : function(data) { // 请求成功后回调函数。
			$("#passVerifyCount").html(data.passVerifyCount);
			$("#auditResult").dialog('open');
			$("#needVerifyBatchCount").html($("#needVerifyBatchCount").text() - 1);
		}
	});
}

function excuteVerifySingle(presentBatchId, id) {
	$("#currentId").val(presentBatchId);
	$("#rowId").val(id);
	$("#verifySelectSingle2").dialog('open');
}

function verifySingle(presentBatchId, id, type) {
	var auditUrl = '/presentbatch/verify?presentBatchId=' + presentBatchId
			+ '&type=' + type + '&format=json';
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
				$("#operateTd" + id).html(
						'<a href="/present/dispensePage?id=' + presentBatchId
								+ '">分发礼券</a>');
				$("#stateTd"+id).html("审核通过");
				$("#verifySucess").dialog('open');
			} else {
				$("#operateTd" + id).html(
						'<a href="/presentbatch/' + presentBatchId
								+ '/edit">修改</a>');
				$("#stateTd"+id).html("审核不通过");
				$("#verifySucess").dialog('open');				
			}
			$("#needVerifyBatchCount").html($("#needVerifyBatchCount").text() - 1);
		}
	});
}