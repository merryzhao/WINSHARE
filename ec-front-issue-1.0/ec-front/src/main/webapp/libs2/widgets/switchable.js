define(function(require) {
	var $ = require("jQuery");

	function switchable(cfg) {
		this.opt = {
			context: "div.mod-index-slider",
			currentline: ".J-tab-currentline",
			currentClass: "current",
			animatetime: 200,
			animate: false
		};
		$.extend(this.opt, cfg);
		this.init();
	};
	switchable.prototype = {
		init: function() {
			this.el = $(this.opt.context);
			this.content = this.el.find(".J-tab-cont");
			this.trigger = this.el.find(".J-tab-trigger");
			this.currentline = this.el.find(this.opt.currentline);
			this.curr = this.opt.currentClass;
			this.itemwidth = this.trigger.width();
			this.outerwidth = this.trigger.outerWidth(true);
			this.initCont();
			this.bind();
		},
		bind: function() {
			var self = this;
			this.trigger.mouseover(function() {
				self.toggle(this);
			}).mouseout(function() {
				if (self.timer) {
					clearTimeout(self.timer);
				}
			});
		},
		initCont: function() {
			this.content.hide();
			this.content.eq(0).show();
		},
		toggle: function(el) {
			this.currentEl = $(el);
			var obj = this;
			this.timer = setTimeout(function() {
				obj.move();
			}, 100);
		},
		move: function() {
			var n = this.trigger.index(this.currentEl),
				marginLeft = (this.outerwidth - this.itemwidth) / 2;
			this.trigger.removeClass(this.curr);
			this.trigger.eq(n).addClass(this.curr);
			this.content.eq(n).show().siblings(".J-tab-cont").hide();
			this.content.eq(n).find("img").trigger("imgload");
			if (!this.opt.animate) {
				return;
			} else {
				this.currentline.stop(true).animate({
					left: marginLeft + this.outerwidth * n + "px"
				}, this.opt.animatetime);
			}
		}
	}
	return switchable;

});