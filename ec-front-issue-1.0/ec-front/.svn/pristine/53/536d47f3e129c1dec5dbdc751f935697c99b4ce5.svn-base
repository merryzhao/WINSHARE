seajs.use(["jQuery"],function($){
	var el={
		orderId : $("input[bind='orderId']"),
		applyReturnForm :$("form[bind='applyReturnForm']"),
		applyReturnSubmit:$("input[bind='applyReturnSubmit']"),
		selectReturnItemSubmit:$("input[bind='selectReturnItemSubmit']"),
		errorMessage : $("b[bind='errorMessage']"),
		selectReturnItemForm :$("form[name='selectReturnItemForm']"),
		reasons :$("select[bind='reason']"),
		reasonSubmit:$("input[bind='reasonSubmit']"),
		reasonForm :$("form[bind='reasonForm']") 
	};
	 
	var applyReturnProduct={
		init:function(){
			el.applyReturnSubmit.bind("click",function(){
				$.ajax({
				type: "POST",
				dataType: "json",
				url: "/customer/returnorder/validate?format=json",
				data: {"orderId":el.orderId.val()},
				success: function(data){
					if(data.status) {
						el.applyReturnForm.submit();
					}else {
						el.errorMessage.html(data.message);
					}
				}
			});
			});	
		  el.selectReturnItemSubmit.bind("click",function(){
		  	$("form input[bind='quantity']").remove();
			$("form input[bind='orderItemId']").remove();
		  	var blag = true;	
		  	var selectReturnItemForm = el.selectReturnItemForm;
			var returnOrderItems = $("input[@name='returnOrderItem'][type='checkbox'][checked]");
			if(returnOrderItems.length<= 0){
				alert("请选择退货项");
				blag = false;
				return ;
			}
			returnOrderItems.each(function(i){
				var item =$(this).val(),
				    quantity = $(this).parent().parent().find("input[name='quantity']").val(),
					deliveryquantity = $(this).parent().parent().find("b[id='deliveryquantity']").html();
					
				if(quantity==null||quantity.length==0){
					alert("填入的数量值不能为空");
					blag = false;
					return false;
				}
				var re = /^[1-9]+[0-9]*]*$/;
				if(!re.test(quantity)){
					alert("填入的数量值必须为正整数");
					blag = false;
					return false;
				}
				if(parseInt(quantity) > parseInt(deliveryquantity)){
					alert("退货的数量不能大于所购买数量");
					blag = false;
					return false;
				}
				$("<input type='hidden' bind ='quantity' name='returnOrderForm["+i+"].quantity' value='"+quantity+"'/>").appendTo(selectReturnItemForm);
				$("<input type='hidden' bind='orderItemId' name='returnOrderForm["+i+"].orderItemId' value='"+item+"'/>").appendTo(selectReturnItemForm);
			});
			if (blag) {
				selectReturnItemForm.submit();
			}else{
				$("form input[bind='quantity']").remove();
				$("form input[bind='orderItemId']").remove();
			}
		  });
		  $("a[bind='cancel']").bind("click",function(){
			if(!confirm("确认要撤销?")){
				return;
			}
			var id =$(this).attr("parma");
			var statusName =$(".statusName"+id);
			var status =$(".status"+id);
			$.ajax({
				url:"/customer/returnorder/"+id+"/cancel?format=json",
				type: "GET",
				dataType: "json",
				success:function(data){	
					statusName.html("审核未通过");
					status.html("<a class='link1' href='/customer/returnorder/"+id+"'>查看</a>");
				}
			})
			});
			$("a[bind='show']").bind('click',function(){
				$("#"+$(this).attr("target1")).toggle();
			});
          var returnOrderItems = $("input[@name='returnOrderItem'][type='checkbox']");
		  returnOrderItems.each(function(){
		  	$(this).bind("click",function(){
				if($(this).attr("checked") == "checked"){
					$(this).parent().parent().find("input[name='quantity']").attr("disabled",false);
				}else{
					$(this).parent().parent().find("input[name='quantity']").attr("disabled","disabled");
				}
			});
		  });
		  el.reasonSubmit.bind("click",function(){
		  		var blag = true;
		  		el.reasons.each(function(index){
					var reasonId = $(this).val(),
					    target = $(this).attr("target"),
						remark =$("textarea[bind='"+target+"']").val();
					if(reasonId == null || reasonId == -1){
						blag = false;
						alert("申请商品：\""+$(this).attr("param")+"\"未填入选择退货原因");
						return false ;
					}		
				})
				if(blag){
					el.reasonForm.submit();
				}
						
		  });
		}
	}
	applyReturnProduct.init(); 
	});