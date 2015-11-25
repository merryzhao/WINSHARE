define(function(require) {
	var $ = require("jQuery"),Assiter=require("searchAssister");

	function pagescroll(cfg) {
		var opt = {
			context: ".head",
			fixedEl: ".top-nav-wrap",
			relativeEl: ".nav",
			fixedClass: "top-nav-fixed",
			popClass:""
		};
		$.extend(this, opt, cfg);
		this.el = $(this.context);
		this.view = $(window);
		this.init();
	};
	pagescroll.prototype = {
		init: function() {
			this.fixedEl = this.el.find(this.fixedEl);
			this.relativeEl = this.el.find(this.relativeEl);
			this.fixedT = this.fixedEl.offset().top;
			this.relativeT =this.relativeEl.offset().top;
			this.fixedH = this.fixedEl.height();
			this.relativeH=this.relativeEl.height();
			this.bind();
		},
		bind: function() {
			var self = this,
				ie6 = $.browser.msie && $.browser.version == 6;
			if (ie6) {
				return;
			}
			this.view.scroll(function() {
				self.auto();
			});
		
		},
		auto: function() {
			var obj = this;
			setTimeout(function() {
				obj.scroll();
			}, 100);
		},
		scroll: function() {
			var scrollT = this.view.scrollTop(),relativeH=this.relativeEl.height()/4;
				distance = this.relativeT+relativeH,totalT=this.fixedH+this.relativeH;
			if (scrollT >this.fixedT) {
				this.fixedEl.addClass(this.fixedClass);
				this.el.css("padding-top", this.fixedH);
				if (scrollT >= distance && !this.scrolled) {								this.el.addClass(this.popClass).css("padding-top",totalT);
					this.relativeEl.css("top",this.fixedH);
				} else {					this.el.removeClass(this.popClass);
					this.relativeEl.css("top",0);
				}
			} else {
				this.fixedEl.removeClass(this.fixedClass);
				this.el.css("padding-top", 0);				this.el.removeClass(this.popClass);
				this.scrolled = false;
			}
		}
	}
	return pagescroll;

});