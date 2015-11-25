
$(document).ready(function(){
	var message = $('#requestInfo').html();
	$("#requestInfo").dialog({
		autoOpen : false,
		bgiframe : false,
		modal : true,
		width : 250,
		height : 80
	});
	if(message.trim() != "") {
		$('#requestInfo').html(message).dialog('open');
	}	
})
function createBillAccount() {
	var name = $("#billaccount_form").find("input[name='name']").eq(0).val();
	var tolerance = $('#billaccount_form').find("input[name='tolerance']").eq(0).val();
	if(name == "" || name == "请输入账户名") {
		alert("请输入账户名");
		return false;
	}
	var is_checked = false;
	$("input[name='channelIds']").each(function(i,e){
		if($(e).attr("checked") == "checked") {
			is_checked = true;
		}
	})
	if(is_checked == false) {
		alert("请选择账户渠道");
		return false;
	}
	if(tolerance.trim()=="" || isNaN(tolerance)) {
		alert("请输入正确金额");
		return false;
	}
	if(confirm("确定创建结算账户？")) {
		$('#requestInfo').html('账户创建中...').dialog('open');
		$("#billaccount_form").submit();
	}
}

function removeChannel(obj, account_id, channel_id) {
	$('#requestInfo').html('删除处理中...').dialog('open');
	$.ajax({
		type : 'get',
		url : 'deleteBillAccountChannel',
		data : 'billAccountId=' + account_id + '&channelId=' + channel_id,
		dataType : 'text',
		success : function(msg) {
			if(msg == 'success') {
				$('#requestInfo').html('删除成功!');
				$(obj).parent().remove();
			} else {
				$('#requestInfo').html(msg);
			}
		},
		error : function() {
		}
	})
}

function updateTolerance(account_id) {
	var tolerance_value = $("#tolerance_"+account_id).val();
	$.ajax({
		type : 'get',
		url : 'updateTolerance',
		data : 'billAccountId=' + account_id + '&tolerance=' + tolerance_value,
		dataType : 'text',
		success : function(msg) {
			alert("容差更新成功!");
		},
		error : function() {
		}
	})
}

