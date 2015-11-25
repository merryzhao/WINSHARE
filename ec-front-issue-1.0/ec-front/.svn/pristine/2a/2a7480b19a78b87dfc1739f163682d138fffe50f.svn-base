define(function(require){
    var $=require("jQuery");
    
    function Slider(cfg){
        var opt={
            element : '.mod-presell-slider',
            effect : 'fade',
            interval : 3000
        };
        $.extend(this, opt, cfg);
        this.index=0;
        this.init();
    };
    Slider.prototype={
        init:function(){
            this.el=$(this.element);
            this.content=this.el.find(".J-tab-cont");
            this.circle=this.el.find(".tab-2 .J-tab-trigger");
            this.thumb=this.el.find(".tab-1 .J-tab-trigger");
            this.imgtitle=this.el.find(".J-title");
            this.bind();
            this.show();
            this.play();
        },
        bind:function(){
            var slider=this;
            this.circle.mouseover(function(){   
                clearTimeout(slider.timer);
                slider.show(this);
            }).mouseout(function(){
                slider.play();
            });
            this.thumb.mouseover(function(){   
                clearTimeout(slider.timer);
                slider.show(this);
            }).mouseout(function(){
                slider.play();
            });
        },
        play:function(){
            var slider=this;
            this.timer = setTimeout(function(){slider.play();slider.show();},5000);
        },
        show:function(el){
            var n;
            n=this.index=el?$(el).index():this.index;
            if(this.index>=3){
                this.index=0;
            }else{
                this.index++;
            }
            this.content.hide();
            this.content.eq(n).show();
            this.circle.removeClass("current");
            this.circle.eq(n).addClass("current");
            this.thumb.removeAttr("style");
            this.thumb.stop(true).eq(n).animate({        
                marginLeft:'0px',
                opacity:'0.6'
            },400);
            this.imgtitle.hide();
            this.imgtitle.eq(n).fadeIn(2000).css("display","block");
        }
    };
    return Slider;
});
