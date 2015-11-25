seajs.use("jQuery", function($) {
	var body = $("body");
	var ad_index = body.find("div#ad_index");
	var banner='<img src="http://static.winxuancdn.com/topic/subject/yiyuecuxiao/dingbu/banner-2.jpg" width="990" height="340" style="margin: 0;border: none" />';
	body.find("div#ad_index a").append(banner);
	setTimeout(showBanner,1000);
	setTimeout(hideBanner,5000);
	function showBanner () {
		ad_index.slideDown(1000);
	}
	function hideBanner () {
	  ad_index.slideUp(1000);
	}
})