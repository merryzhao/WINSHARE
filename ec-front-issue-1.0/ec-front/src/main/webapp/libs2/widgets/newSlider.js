define(function(require) {
    var $ = require("jQuery");

    function newslider(cfg) {
        var opt = {
            selector: {
                context: "div.mod-index-slider",
                cont: "div.J-tab-cont",
                trigger: "div.J-tab-trigger"
            },
            autotag: true,
            delay: 200,
            interval: 3000,
            isAnimate: true,
            vertical: "top"
        };
        $.extend(this, opt, cfg);
        this.init();
    };
    newslider.prototype = {
        init: function() {
            this.el = $(this.selector.context);
            this.cont = this.el.find(this.selector.cont);
            this.trigger = this.el.find(this.selector.trigger);
            this.item = this.el.find(this.selector.item);
            this.imgtitle = this.el.find(this.selector.imgtitle);
            this.index = 0;
            this.len = this.trigger.length;
            this.move();
            this.bind();
            this.autoPlay();
        },
        bind: function() {
            var self = this;
            this.trigger.mouseover(function() {
                clearTimeout(self.timer);
                self.move(this);
            }).mouseout(function() {
                self.autoPlay();
            });
            this.item.mouseover(function() {
                clearTimeout(self.timer);
                self.move(this);
            }).mouseout(function() {
                self.autoPlay();
            });
        },
        currentIndex: function(el) {
            var i = this.index = el ? $(el).index() : this.index,
                j = this.len - 1;
            if (this.index >= j) {
                this.index = 0;
            } else {
                this.index++;
            }
            return i;
        },
        move: function(op) {
            var n = this.currentIndex(op);
            this.trigger.removeClass("current");
            this.trigger.eq(n).addClass("current");
            this.cont.eq(n).show().siblings(this.selector.cont).hide();
            if (this.isAnimate) {
                this.change(n);
            } else {
                return;
            }
        },
        toggle: function(el, m, change, original) {
            el.stop(true).eq(m).animate(change, this.delay).siblings().css(original);
        },
        change: function(p) {
            var ie6 = $.browser.msie && $.browser.version == 6;
            if (this.vertical == "top") {
                this.toggle(this.trigger, p, {
                    marginTop: '-15px'
                }, {
                    "margin-top": '0'
                });
                this.trigger.find("img").removeAttr("style");
                this.trigger.eq(p).find("img").css({
                    opacity: '1.0',
                    filter: 'alpha(opacity = 100)'
                });              
            } else if (this.vertical == "left") {
                this.toggle(this.item, p, {
                    marginLeft: '0px',
                    opacity: '1.0'
                }, {
                    "margin-left": '10px',
                    "opacity": '0.6'
                });
                this.imgtitle.hide();
                !ie6 ? this.imgtitle.eq(p).fadeIn(2000).css("display", "block") : this.imgtitle.eq(p).show().css("display", "block");
            }
        },
        autoPlay: function() {
            var _this = this;
            if (this.autotag) {
                this.timer = setTimeout(function() {
                    _this.autoPlay();
                    _this.move();
                }, _this.interval);
            }
        }
    };
    return newslider;

});