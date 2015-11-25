/**
 * @author libin
 */
seajs.use(["jQuery","login"],function($,login){
	var successTemp="<div class='successWin tipWin'><a class='close'></a><p><a href='http://www.winxuan.com/customer/present'>去看看钱&gt;&gt;</a><a href='http://www.winxuan.com/help/gift_ticket.html'>使用方法&gt;&gt;</a></p></div>",
		erroTemp="<div class='erroWin tipWin'><a class='close'></a></div>",
		overlay="<div class='overlay'></div>";
	$("#wrap").delegate(".close","click",function(){
		$(".tipWin").hide();
		$(".tipWin").remove();
		$(".overlay").remove();
	});
	$("area[bind='getCoupon']").click(function(){
		var batch = $(this).data("batch"),
			url = "http://www.winxuan.com/present/send?id="+batch+"&token=841b6b54bd0591b8cbacd7ec9cf92b96&format=jsonp&callback=?";
		$(this).attr("disabled","disabled");
		$.getJSON(url,function(data){
			$(this).removeAttr("disabled");
			if(data.result==0){
				login.show();
			}else if(data.result==1){
				loadoverlay();
				$(successTemp).appendTo($("#wrap"));
			}else{
				loadoverlay();
				$(erroTemp).appendTo($("#wrap"));
			}
		});
	});
	function loadoverlay(){
		$(overlay).appendTo($("#wrap"));
	 	 $(".overlay").css({
                height: $(document).height(),
                width: $(window).width(),
                display: "block"
         });
	}
	function isAvailable(){
		var coupons = [], param;
		$("a[bind='getCoupon']").each(function(){
			coupons.push("b="+$(this).data("batch"));
		});
		param = coupons.join("&");
		var url = "http://www.winxuan.com/present/vailable?"+param+"&format=jsonp&callback=?";
		$.getJSON(url,function(data){
			if(!data.t0){
				$("#d1").attr("src","images/d2_08.jpg");
			}
			if(!data.t1){
				$("#d2").attr("src","images/d2_10.jpg");
			}
			if(!data.t2){
				$("#d3").attr("src","images/d2_12.jpg");
			}
		});
	}
	//isAvailable();
});