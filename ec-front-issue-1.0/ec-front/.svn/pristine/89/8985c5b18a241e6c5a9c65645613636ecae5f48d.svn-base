define(function(require){
    var $ = require("jQuery");
    var i=0;
    function Turn(cfg){
        if(window==this)return new Turn(cfg);
        var timer;
        var opt = {
            selector:{
                banner:"#banner",
                banner_img:"#banner_list img",
                banner_circle:"#banner_circle li"
            }
        };
        $.extend(this,opt,cfg);
        this.el = $(this.selector.banner);
        this.init();
        clearInterval(timer);
        this.setInterval();
    }
    Turn.prototype = {
        init:function(){
            this.banner_img =this.el.find(this.selector.banner_img);
            this.banner_circle = this.el.find(this.selector.banner_circle);
            this.bind();
        },
        bind:function(){
            var turn = this;
            this.banner_circle.hover(function(){
                clearInterval(turn.timer);
                var i=$(this).text()-1;
                turn.banner_img.filter(":visible").hide();
                turn.banner_img.eq(i).show();
                turn.banner_circle.removeClass();
                turn.banner_circle.eq(i).addClass("on");
            },function(){
                turn.setInterval();
            });
        },
        setInterval:function(){
            var that = this;
            that.timer= setInterval(function(){
                if(i>2){
                    i=0;
                } else{
                    i++;
                }
                that.banner_img.filter(":visible").hide();
                that.banner_img.eq(i).show();
                that.banner_circle.removeClass();
                that.banner_circle.eq(i).addClass("on");
            },3000);
        }
    };
    return Turn;
});