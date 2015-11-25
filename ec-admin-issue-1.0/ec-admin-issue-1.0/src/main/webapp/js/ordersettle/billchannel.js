function searchBill() {
	var billId = $('#bill_search_id').val();
	var list = $('#bill_search_list').val();
	var invoice = $('#bill_search_invoice').val();
	var accountname = $('#bill_search_accountname').val();
	if(billId == "请输入账单号") {
		$('#bill_search_id').val('');
	}
	if(list == "请输入清单") {
		$('#bill_search_list').val('');
	}
	if(invoice == "请输入发票") {
		$('#bill_search_invoice').val('');
	}
	if(accountname == "请输入结算账户名") {
		$('#bill_search_accountname').val('');
	}
	$("#billorder_form").submit();
}

function confirmTolerance() {
	var channel_id = $("#settleorder_channelid").val();
	var amount = $('#tolerance_amount').val();
	if(channel_id=="") {
		alert("请选择渠道");
		return false;
	}
	if(amount.trim()=="" || isNaN(amount)) {
		alert("请输入正确金额");
		return false;
	}
	$('#requestInfo').html('数据更新中...').dialog('open');
	$('#billchannel_form').submit();
}

function confirmCheckBill(billId) {
	if(confirm("确认审核吗？")) {
		$('#requestInfo').html('请求提交中...').dialog('open');
		window.location.href="/ordersettle/confirmBill?billId=" + billId;
	}
}

function lockBill(billId) {
	if(confirm("确认分配吗？")) {
		$('#requestInfo').html('请求提交中...').dialog('open');
		window.location.href="/ordersettle/lockBill?billId=" + billId;
	}
}

$("#requestInfo").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 250,
	height : 80
});