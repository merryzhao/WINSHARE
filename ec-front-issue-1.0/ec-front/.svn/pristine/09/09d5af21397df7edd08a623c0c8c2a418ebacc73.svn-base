seajs.use(["jQuery"], function($){
    var view = {
        init: function(){
            this.weekTop = $("a[bind='week_top']");
            this.aMonthTop = $("a[bind='a_month_top']");
            this.threeMonthTop = $("a[bind='three_month_top']");
            this.lastYearTop = $("a[bind='last_year_top']");
            this.page_head_1 = $("a[bind='page_head_1']");
            this.page_head_2 = $("a[bind='page_head_2']");
            this.page_head_3 = $("a[bind='page_head_3']");
            this.page_head_4 = $("a[bind='page_head_4']");
            this.page_head_5 = $("a[bind='page_head_5']");
            this.page_bottom_1 = $("a[bind='page_bottom_1']");
            this.page_bottom_2 = $("a[bind='page_bottom_2']");
            this.page_bottom_3 = $("a[bind='page_bottom_3']");
            this.page_bottom_4 = $("a[bind='page_bottom_4']");
            this.page_bottom_5 = $("a[bind='page_bottom_5']");
            var url = window.location.href;
            var search=location.search||"";
            var strs=url.split("?");
			var head = strs[0].substr(0,strs[0].length-4);
			this.weekTop.attr("href",head+"/1/1"+search);
			this.aMonthTop.attr("href",head+"/2/1"+search);
			this.threeMonthTop.attr("href",head+"/3/1"+search);
			this.lastYearTop.attr("href",head+"/4/1"+search);
			var page = strs[0].substring(0,strs[0].length-1);
			if(!!this.page_head_1){
				this.page_head_1.attr("href",page+"1"+search);
			}
			if(!!this.page_head_2){
				this.page_head_2.attr("href",page+"2"+search);
			}
			if(!!this.page_head_3){
				this.page_head_3.attr("href",page+"3"+search);
			}
			if(!!this.page_head_4){
				this.page_head_4.attr("href",page+"4"+search);
			}
			if(!!this.page_head_5){
				this.page_head_5.attr("href",page+"5"+search);
			}
			if(!!this.page_bottom_1){
				this.page_bottom_1.attr("href",page+"1"+search);
			}
			if(!!this.page_bottom_2){
				this.page_bottom_2.attr("href",page+"2"+search);
			}
			if(!!this.page_bottom_3){
				this.page_bottom_3.attr("href",page+"3"+search);
			}
			if(!!this.page_bottom_4){
				this.page_bottom_4.attr("href",page+"4"+search);
			}
			if(!!this.page_bottom_5){
				this.page_bottom_5.attr("href",page+"5"+search);
			}
			$(".filter_list").mousemove(function() {

				var t = $(this).children(".filter_list_ul").html();
				t = t.replace(/[\s]/g, "");
				if (t == "") {
					$(this).children(".filter_list_ul").hide();
					$(this).children(".filter_list_h5").removeClass("hover");
				} else {
					$(this).children('.filter_list_ul').show();
					$(this).children('.filter_list_h5').addClass("hover");
					$(this).mouseleave(function() {
						$(this).children('.filter_list_ul').hide();
						$(this).children('.filter_list_h5').removeClass("hover");
					});
				}
			});
        }
    };
    view.init();
});
