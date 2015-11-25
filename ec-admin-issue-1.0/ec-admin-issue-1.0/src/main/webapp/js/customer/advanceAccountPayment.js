$("#paymentOrderId").blur(
		function(){
			if($("#paymentOrderId").val() != ""){
				validate($("#paymentOrderId").val());
			}else{
				$("#error").html("");
			}
		}
	);
	function validate(orderId) {
		var checkUrl = '/order/getOrderInfo';
		var postdata = "id="+orderId+"&format=json";
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			url : checkUrl,
			data:postdata,
			error : function() {
				
			},
			success : function(data) { 
				if(data.result==1){
					$("#mytd").html("<br>订单号:"+data.orderId+" &nbsp;&nbsp;&nbsp;注册名:"+data.customerName+" &nbsp;&nbsp;&nbsp;支付类型:"+data.payType+" &nbsp;&nbsp;&nbsp;支付状态:"+data.orderStatus+"<br><br>账户余额:"+data.balance+"元  &nbsp;&nbsp;&nbsp;还需支付金额:"+data.requidPayMoney+"元 <br><br>");
					if(data.flag==2){
						$("#error").html("不满足支付条件");
						document.getElementById("advanceaccountSubmit").setAttribute('disabled',"true");
					}else{
						$("#error").html("");
						document.getElementById("advanceaccountSubmit").removeAttribute("disabled");
					}
				}else if(data.result==2){
					$("#error").html("请填写正确的订单号!");
					$("#paymentOrderId").focus();
					$("#mytd").html("");
					document.getElementById("advanceaccountSubmit").setAttribute('disabled',"true");
				}
			}
		});
	}
	$().ready(function() {
		$("#form").validate({
			rules : {
				orderId : {
					required:true
				}
			},
			messages : {
				orderId : {
					required:"请输入订单号"
				}
			}
		});
	});