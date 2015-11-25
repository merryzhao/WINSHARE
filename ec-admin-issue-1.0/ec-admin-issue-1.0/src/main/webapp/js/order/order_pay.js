$().ready(function() {
			$("#form").validate({
				rules : {
					orderId:{
						required : true,
						orderIdNumber:true 
					},
					money : {
						min:0.01,
						required : true,
						number:true 
					},
					payTime : {
						required : true
					},
					outerId :{
						required:true,
						number:true
					}
				},
				messages : {
					orderId:{
						required : "订单号必填",
						orderIdNumber:"订单号必须为数字" 
					},
					money : {
						min:"金额不能为负数和零",
						required : "金额必填",
						number:"金额必须为数字"
					},
					payTime : {
						required : "支付时间必填"
					},
					outerId :{
						required:"外部交易号必填",
						number:"外部交易号必须为数字"
					}
				}
			});
		});
		$(function() {
			$("#information").hide();
		});

		jQuery(function($) {
			$("#orderId").blur(
					function() {
						var id = $("#orderId").val();
						if (id != "") {
							var url = '/order/getBalanceAndName?orderId=' + id
									+ '&format=json';
							$.ajax({
								async : false,
								cache : false,
								type : 'GET',
								url : url,
								error : function() {
									
								},
								success : function(data) {
 									if(data.errors!=null&&data.errors.length!=0){
										$("#error").html("<br>"+data.errors);
										$("#tijiao").hide();
										$("#tijiao2").show();
										return;
									}
									if (data.message == "success") {
										$("#error").html('');
										$("submit");
										$("#tijiao2").hide();
										$("#tijiao").show();
									} else {
										$("#error").html('订单状态不正确');
										$("#tijiao").hide();
										$("#tijiao2").show();
									}
									$("#information").show();
									$("#orderIdLabel").html(id);
									$("#nameLabel").html(data.customer);
									$("#balanceLabel").html(data.balance);
									$("#form").attr("action","/order/" + id + "/pay");
									$("#customer").val(data.customer);
									$("#requidPayMoney").html(data.requidPayMoney+'元');
									$("#money").val(data.requidPayMoney);
									$("#pay_"+data.paymentId).attr("selected","selected");
								}
							});
						} else {
							$("#error").html("");
						}
					});

		});
		