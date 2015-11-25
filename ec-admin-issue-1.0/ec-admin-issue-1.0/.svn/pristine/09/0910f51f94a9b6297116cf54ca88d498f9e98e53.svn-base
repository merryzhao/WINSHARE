$(document).ready(function(){
	var stock=null;
	stock ={
		init:function(){
			$(".returnBtn").click(function(){
				location.href="/stockrule/lock/list";
			});
			stock.submit();
			
			$("input[name=customerName]").blur(function(){
				stock.isCustomer();
			});
			
			$("#getChecktree").click(function(){
				stock.isCustomer();
			});
		},
		submit:function(){
			$("#yessubmit").click(function(){
				stock.isVerification();
			});
		},
		isVerification:function(){
				if($("input[name=channel]").val()!=undefined){
					var name = $("input[name=customerName]").val();
					var nameId = $("input[name='customer.id']").val();
					if(name!=""&&nameId==""){
						alert("用户验证失败");
						return;
					}
					if($("#productSales").val().trim() != ""){
						if($("input[name=beginTime]").val() != ""){
							var sDate=new Date(($("input[name=beginTime]").val()).replace(/-/g,"/"));
						    var eDate = new Date(($("input[name=endTime]").val()).replace(/-/g,"/"));
						    if(sDate<=eDate){
						    	var lockstock = $("input[name=lockStock]").val().trim();
								var dcid=$("span[for=progressmarkers]").text().split('%')[0];
								if(lockstock!=""||dcid!=0){
									$("#lockStockFactor").empty();
									$("#lockStockFactor").append("<input type='hidden' name='lockFactor'  value='"+dcid+"'/>");
									$("input[name=channel]").attr("name","channel.id");
									$("form").submit();
								}else{
									alert("锁定库存或者锁定系数必填一个");
								}
						    }else{
						    	alert("结束时间不能小于开始时间");
						    }
						}else{
							alert("开始时间不能为空");
						}
					}else{
						alert("商品编码不能为空");
					}
				}else{
					alert("请选择渠道");
				}
		},
		isCustomer:function(){
			var name = encodeURI($("input[name=customerName]").val());
			var channel = $("input[name=channel]").val();
			if(name!=""&&channel!=""&&channel!=undefined){
				$.ajax({
		            type: 'POST', 
		            url: "/customer/customerIsExistence", 
		            data:"format=json&name="+name+"&channel="+channel,
		            dataType: 'json', 
		            success: function(data) { 
		            	if(data.customer!=null){
		            		$("input[name='customer.id']").val(data.customer.id);
		            		$("#customerOk").replaceWith('<b style="color: #33CC00;" id="customerOk">OK</b>');
		            	}else{
		            		$("input[name='customer.id']").val("");
		            		$("#customerOk").replaceWith('<b style="color: red;" id="customerOk">用户名错误</b>');
		            	}
		            },
		            error: function() {
		            	alert('请求失败');
		            }
		        });
			}else{
				$("input[name='customer.id']").val("");
				$("#customerOk").replaceWith('<b style="color: red;" id="customerOk"></b>');
			}
		},
	};
	stock.init();
});
$(document).ready(function() {
 	// 弹出式初始化渠道树
	$("#channelDiv").dialog({
		autoOpen : false,
		bgiframe : true,
		modal : true,
		width : 350,
//		height:300
	});
	
	$("#add_channel").click(function(){
		$("#channelDiv").dialog("open");
	});
	
   $('#channelTitle').hide();
   $('#outerOrderIdTitle').hide();
   $('#outerOrderIdContent').hide();
   if($('#isInvoice').val()){
   $('#invoiceTitleTShow').hide();
   $('#invoiceTitleShow').hide();
   $('#companyNameTitle').hide();
   $('#companyName').hide();
   }
});