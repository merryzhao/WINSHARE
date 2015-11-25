$(document).ready(function(){
	var account_id = $("#settleorder_billaccountid").val();
	if(account_id != "" && account_id > 0) {
		showChannelList(account_id);
	}
})

function showChannelList(account_id) {
	$(".channel_list_display").find("input[type=checkbox]").each(function(i, e){
		$(e).attr("checked", false);
	})
	$(".channel_list_display").hide();
	$("#channel_list_" + account_id).find("input[type=checkbox]").each(function(i, e){
		$(e).attr("checked", true);
	})
	$("#channel_list_" + account_id).show();
}

function confirmSettle(tolerate, billId) {
	var str = "确认分配吗？";
	if(tolerate > 50 || tolerate < -50) {
		str = "容差大于50，" + str;
	}
	
	if(confirm(str)) {
		$('#requestInfo').html('请求提交中...').dialog('open');
		window.location.href="/ordersettle/lockBill?billId=" + billId;
	}
}

function startSettle() {
	var account_id = $("#settleorder_billaccountid").val();
	var amount = $("#settleorder_amount").val();
	var invoice = $("#settleorder_invoice").val();
	var list = $("#settleorder_list").val();
	if(account_id == "") {
		alert("请选择账户");
		return false;
	}
	if(amount == "请输入金额" || isNaN(amount)) {
		alert("请输入金额");
		return false;
	}
	if(list == "") {
		alert("请输入清单");
		return false;
	}
	if(invoice == "") {
		alert("请选择发票");
		return false;
	}
	$('#requestInfo').html('请求提交中...').dialog('open');
	$('#settle_button').hide();
	$.ajax({
		type : 'get',
		url : 'findLastBill',
		data : 'billAccountId=' + account_id,
		dataType : 'text',
		success : function(msg) {
			if(msg != "") {
				if(confirm("该渠道存在未确认的账单，是否继续创建新账单?")) {
					$("#ordersettle_form").submit();
				} else {
					window.location.href = "/ordersettle/toLastBill?billId="+msg;
				}
			} else {
				$("#ordersettle_form").submit();
			}
		},
		error : function() {
		}
	})
}

function showOrderItems(billId) {
	var order_id = $('#order_id').val();
	$.ajax({
		type : 'get',
		url : 'listOrderItems',
		data : 'orderId=' + order_id + '&billId=' + billId,
		dataType : 'html',
		success : function(msg) {
			$('#orderItemList').html(msg);
			$('#orderItemInfo').dialog('open');
		},
		error : function() {
		}
	})
}

function submitOrderItemForm() {
	var has_item = false;
	$('#order_item_table').find("input:checkbox").each(function(){
		if($(this).attr("checked") == "checked"){
			has_item = true;
		}
	})
	if(has_item == true) {
		$("#order_item_form").submit();
	} else {
		alert("未选择商品项");
	}
}

function changeOrderItem() {
	var price = 0.0;
	$('#order_item_table').find("input:checkbox").each(function(){
		if($(this).attr("checked") == "checked"){
			var item_price = $('#orderitemprice_' + $(this).val()).html();
			price += item_price * 1;
		}
		$('#orderitem_total').html(parseFloatNew(price));
	})
}
function parseFloatNew(number) {
	number = parseFloat(number);
	if(!isNaN(number))
		return number.toFixed(2);
}
$("#requestInfo").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 250,
	height : 80
});
$("#orderItemInfo").dialog({
	autoOpen : false,
	bgiframe : false,
	modal : true,
	width : 1000,
	height : 600
});