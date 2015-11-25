jQuery(function($) {
	$("#id").blur(function() {
		var id = $("#id").val();
		
		if (id != '') {
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
							alert(dateday[0]);
						}else{
							$("#enddate").html('');
						}
						var expired = data.presentCard.expired;
						if (expired == true) {
							$("#expired").html("有效");
						} else {
							$("#expired").html("无效");
						}
						if (name != "入库" && name != "印刷") {
							$("#cancel").attr("disabled","disabled");
						}
						$("#canceldiv").attr("style", "display: block");
					} else {
                        $("#error").html('礼品卡不存在');
                        $("#canceldiv").attr("style", "display: none");
					}
				}
			});
		}
	});

});
jQuery(function($) {
	$("#cancel").click(function() {
		if (window.confirm("是否确认注销")) {
			var id = $("#id").val();
			var url = '/presentcard/cancel?id=' + id + '&format=json';
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
						alert('注销成功');
						window.location.href='/presentcard/cancelpage';
					} else {
						alert('注销失败');
					}
				}

			});
		}
	});
});