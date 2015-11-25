jQuery(function($) {
	$("#id").blur(function() {
		var id = $("#id").val();
		if (id != "") {
			var url = '/presentcard/get?id=' + id + '&format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : url,
				error : function() {

				},
				success : function(data) {
					if (data.result == 1) {
						
						$("#error").html('');
						$("#presentCardId").html(data.presentCard.id);
						var name = data.presentCard.status.name;
						$("#status").html(name);
						$("#type").html(data.presentCard.type.name);
						$("#createdate").html(data.presentCard.createDate);
						$("#denomination").html(data.presentCard.denomination);
						$("#balance").html(data.presentCard.balance);
						var date = data.presentCard.endDate;
						if(date!='' && date != null){
							var dateday=date.split(" ");
							$("#enddate").html(dateday[0]);
						}else{
							$("#enddate").html('');
							$("#delay").attr("disabled","disabled");
							$("#date").attr("disabled","disabled");
							
						}
						
						var expired = data.presentCard.expired;
						if (expired == true) {
							$("#expired").html("有效");
						} else {
							$("#expired").html("无效");
						}
						$("#delaydiv").attr("style", "display: block");
						
					} else {
						$("#error").html('礼品卡不存在');
						$("#delaydiv").attr("style", "display: none");
					}
				}
			});
		} else {
			return false;
		}
	});

});
jQuery(function($) {
	$("input[bind=datepicker]").datepicker('option', 'changeYear', true);
	$("#delay").click(function() {
		var newdate = document.getElementById('date').value;
		if($("#enddate").html() > newdate){
			alert("延迟日期须大于有效期");
	        return false;
		}
		
		if (window.confirm("是否确认延期有效期")) {
			var id = $("#id").val();
			var url = '/presentcard/delay?id=' + id + '&newdate='+newdate+'&format=json';
			$.ajax({
				async : false,
				cache : false,
				type : 'GET',
				url : url,
				error : function() {
					alert('发生错误');
				},
				success : function(data) {
					if (data.result == 0) {
						alert('延期成功');
						window.location.href='/presentcard/delaypage';
					} else {
						alert('延期失败');
					}
				}

			});
		}
	});
});