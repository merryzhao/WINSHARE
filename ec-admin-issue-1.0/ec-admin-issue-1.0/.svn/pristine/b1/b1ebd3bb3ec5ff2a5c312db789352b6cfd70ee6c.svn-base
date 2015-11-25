$("#accountname").blur(
		function(){
			if($("#accountname").val()!=""){
				getBalance($("#accountname").val());
			}else{
				$("#error").html("");
			}
		}		
	);
	
	function getBalance(name) {
		var ordercCheckUrl = '/customer/getBalanceByName';
		var postdata = "name="+name+"&format=json";
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			url : ordercCheckUrl,
			data:postdata,
			error : function() {
				
			},
			success : function(data) { 
				if(data.result==1){
					$("#mytd").html("账户余额:");
				$("#money").html(data.message+'元');
				$("input[name='balance']").val(data.message);
				$("#tijiao").show();
				$("#tijiao2").hide();
				$("#error").html("");
				}else if(data.result==2){
					$("#error").html("请填写正确的账户姓名!");
					$("#accountname").focus();
					$("#tijiao").hide();
					$("#tijiao2").show();
				}
			}
		});
	}
	$().ready(function() {
		$("#form").validate({
			rules : {
				customer : {
					required:true
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
					number:true
				}
			},
			messages : {
				customer : {
					required:"请输入账户名"
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
					number:"外部交易号必须为数字"
				}
			}
		});
	});