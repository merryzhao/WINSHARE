$(function(){
	$('#order_item_form').submit(function() { 
		$.ajax({ 
			url: '/ordersettle/addBillItems', 
			data: $('#order_item_form').serialize(), 
			type: "post", 
			success: function(data){
				if(data == "success") {
					var billId = $("#order_item_form").find('input[name=billId]').eq(0).val();
					window.location.href = "/ordersettle/toLastBill?billId=" + billId
				} else {
					alert(data);
				}
			},
			 error: function() {
                 alert("有商品已经结算");
             }
		}); 
		return false;
	}); 
});