$(document).ready(function(){
	var stock=null;
	stock ={
		init:function(){
			$(".returnBtn").click(function(){
				location.href="/stockrule/lock/list";
			});
			stock.submit();
			
		},
		submit:function(){
			$("#yessubmit").click(function(){
				stock.isVerification();
			});
		},
		isVerification:function(){
						if($("input[name=beginTime]").val() != ""){
						    var eDate = new Date(($("input[name=endTime]").val()).replace(/-/g,"/"));
						    if(eDate!= ""){
						    	var lockstock = $("input[name=lockStock]").val().trim();
								var dcid=$("input[name=lockStockFactor]").val();
								if(lockstock!=""||dcid!=""){
									//$("#lockStockFactor").empty();
									//$("#lockStockFactor").append("<input type='hidden' name='lockFactor'  value='"+dcid+"'/>");
									$("form").submit();
								}else{
									alert("系数为空锁定库存必填");
								}
						    }else{
						    	alert("结束时间不能为空");
						    }
						}else{
							alert("开始时间不能为空");
						}
		},
	};
	stock.init();
});