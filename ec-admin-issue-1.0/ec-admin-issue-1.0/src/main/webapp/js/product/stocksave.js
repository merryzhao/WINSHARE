$(document).ready(function(){
	var stock=null;
	var result=0;
	//var issubmit=0;
	stock ={
		init:function(){
			//多库存选择时候
			$("input[name=stockRules]").click(function(){
				//var name = $(this).parent().text();
				var dcId = $(this).val();
				if(this.checked){
					$("input[for="+dcId+"stock]").attr("name","stockRule");
				}else{
					$("input[for="+dcId+"stock]").attr("name","name");
				}
			});
			$(".returnBtn").click(function(){
				location.href="/stockrule/find";
			});
			stock.submit();
			$("select[name=supply]").change(function() {
				if($("input[name=channel]").val()!=undefined){
					stock.isVerification();
				}
			});
				$("#getChecktree").click(function() {
					if($("input[name=channel]").val()!=undefined){
						stock.isVerification();
					}
				});
		},
		submit:function(){
			$("#yessubmit").click(function(){
				if($("input[name=channel]").val()!=undefined){
								result==0;
								$("input[name=stockRules]").each(function(){
									 if(this.checked){
										 result=1;
									 }
								});
								if(result==1){
									stock.changeName();
								}else{
									alert("请选择库位");
								}
				}else{
					alert("请选择渠道");
				}
				
			});
		},
		changeName:function(){
			$("input[name=stockRule]").each(function(i,s){
				$(this).attr("name","stockRuleDc["+i+"].dc.id");
			});
			var dcid=$("span[for=progressmarkers]").text().split('%')[0];
			$("#entering_table_one").append("<input type='hidden' name='percent'  value='"+dcid+"'/>");
			$("form").submit();
		},
		isVerification:function(){
			var supplyType = $("select[name=supply] option:selected").val();
			var channel = $("input[name=channel]").val();
			$.ajax({
	            type: 'get', 
	            url: "/stockrule/verification?format=json&channelId="+channel+"&supplyTypeId="+supplyType, 
	            dataType: 'json', 
	            success: function(data) { 
	            	if(data.isb){
	            		issubmit=0;
	            		$("#yessubmit").attr("disabled",true);
	            	}else{
	            		issubmit=1;
	            		$("#yessubmit").attr("disabled",false);
	            	}
	            },
	            error: function() {
	            	alert('请求失败');
	            }
	        });
		}
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