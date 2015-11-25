$(document).ready(function(){
	var stock=null;
	var result=0;
	stock ={
		init:function(){
			$(".returnBtn").click(function(){
				location.href="/stockrule/find";
			});
			stock.submit();
		},
		submit:function(){
			$("#editsubmit").click(function(){
				result==0;
				/*$("input[name=stockDcId]").each(function(i,s){
					$(this).attr("name","stockRuleDc["+i+"].id");
				});*/
				$("input[name=stockRules]").each(function(){
					 if(this.checked){
						 $(this).attr("name","stockRuleUpdateDc");
						 result=1;
					 }
				});
				if(result==1){
					if(window.confirm("是否确认修改")){
						$("#editsubmit").attr("disabled",true);
						stock.changeName();
					}
				}else{
					alert("请选择库位");
				}
			});
		},
		changeName:function(){
			$("input[name=stockRuleUpdateDc]").each(function(i,s){
				$(this).attr("name","stockRuleDc["+i+"].dc.id");
			});
			var dcid=$("span[for=progressmarkers]").text().split('%')[0];
			$("#entering_table_one").append("<input type='hidden' name='percent'  value='"+dcid+"'/>");
			$("form").submit();
		},
	};
	stock.init();
});