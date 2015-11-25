		function ajaxSubmit(orderId,flag) {
			$("#requestInfo").html('正在提交请求 ,请稍后......');
			$("#requestInfo").dialog("open");
			var url = "/presentcardorder";
			var result = "";
			if(flag=="grant"){
				url = url+"/send";
				result="发卡";
			}
			if(flag=="reissue"){
				url = url+"/resend";
				result="补发";
			}
			form = $("#excelForm").serialize();
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : 'json',
				data : 'orderId='+orderId+'&format=json',
				url : url,
				error : function() {// 请求失败处理函数
					$("#requestInfo").html('请求失败');
				},
				success : function(date) { // 请求成功后回调函数。
					if(date.result==0){
					$("#requestInfo").html('操作失败：'+date.message);
					}else{
					$("#requestInfo").html(result+"成功");
					}
				}
			});
		}
		$().ready(function(){
				$("#queryPresentCard").bind("click",function(){
				var form = $("#cardQueryForm");
				form.attr("action","/presentcardorder/tosendofdata");
				form.submit();
			})
			$("#exPresentCard").bind("click",function(){
				var form = $("#cardQueryForm");
				form.attr("action","/excel/giftcard/list?format=xls&style=2");
				form.submit();
			})
			//实现复选框全选
			$("#select_checkbox").click(function(){
				if($(this).attr("checked")){
					$(".checkbox_list").attr("checked",true);
				}else{
					$(".checkbox_list").attr("checked",false);
				}
			
			});
			//反选
			$("#invert_select_checkbox").click(function(){
				var checkboxList = $(".checkbox_list");
				var length = checkboxList.length;
				if($(this).attr("checked")){
					for ( var i = 0; i < length; i++) {
						var checkbox = $(".checkbox_list").eq(i);
						var checkState = checkbox.attr("checked");
						if (checkState) {
							checkbox.attr("checked",false);
						} else {
							checkbox.attr("checked",true);
						}
					}
				}else{
					for ( var i = 0; i < length; i++) {
						var checkbox = $(".checkbox_list").eq(i);
						var checkState = checkbox.attr("checked");
						if (!checkState) {
							checkbox.attr("checked",true);
						} else {
							checkbox.attr("checked",false);
						}
					}
				}
			
			});
			//激活勾选的礼品卡
			$("#activate").click(function() {
				var trCheckBoxs = $(".tr_list").find("input:checked");
				var length = trCheckBoxs.length;
				var orderId = $('#orderId').val();
				if(length < 1) {
					alert("请勾选需要激活的礼品卡号！");
					return false;
				}
				var presentCardIds = "";
				for ( var i = 0; i < length; i++) {
					var presentCardId = trCheckBoxs.eq(i).attr("id").slice("checkbox_".length);
					if(i != length-1) {
						presentCardIds += presentCardId +"|";
					} else {
						presentCardIds += presentCardId;
					}
				}
				if(!window.confirm("您确定要激活吗？")){
					return false;
				}
				$("#requestInfo").dialog("open");
				$("#requestInfo").html('请稍后,正在激活...');
				$.ajax({
					url : "/presentcardorder/active",
					type : "post",
					async : true,
					dateType : "json",
					data :{
						presentCardIds : $.trim(presentCardIds),
						orderId:orderId
					},
					error : function() {// 请求失败处理函数
						$("#requestInfo").html('请求失败');
					},
					success : function() { // 请求成功后回调函数
						$("#requestInfo").html('激活成功,请刷新页面');
					}
				});
			});
		})
		$("#requestInfo").dialog({
			autoOpen : false,
			bgiframe : false,
			modal : true,
			width : 280,
			height : 120
		});