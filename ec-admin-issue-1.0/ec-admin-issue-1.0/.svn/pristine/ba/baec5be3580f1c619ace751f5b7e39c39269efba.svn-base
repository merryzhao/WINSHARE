$(":radio").css({
				"margin-left" : "10px"
			});
			$("#cltextarea").css({
				"float" : "left",
				"width" : "190px",
				"height" : "48px",
				"font-size" : "13px",
				"margin-left" : "10px",
				"margin-right" : "10px"
			});
			$(".cl_leftfloat").css({
				"float" : "left"
			});
		
			$("#time-choose").dialog({
				autoOpen : false,
				bgiframe : false,
				modal : true,
				width : 400,
				buttons : {
					"Ok" : function() {
						$("form:first").submit();
						$(this).dialog("close");
					}
				}
			});
			$("a[binding='opentime']").click(function() {
				$("#time-choose").dialog("open")
			});
			$("#hb").click(function(){
				if($(":checkbox:checked").size()>1){
					$("#form").attr("action","/orderinvoice/new_together").submit();
				}else{
					alert("合并发票至少选择两个或者两个以上发票！");
				}
				
			});
		