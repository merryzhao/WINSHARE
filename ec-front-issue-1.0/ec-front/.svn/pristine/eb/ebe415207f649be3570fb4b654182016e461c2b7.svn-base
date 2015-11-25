/**
 * @author libin
 */
define(function(require){
    var $=require("jQuery");
    
    function MiniATF(cfg){
        var opt={
            context:document,
            vertical:"top",
            refer:".footer",
            animate:false,
            scrollT:100
        }
        $.extend(this,opt,cfg);
        this.el=$(this.context);
        this.view=$(window);
        this.page=$(document);
    };
    MiniATF.prototype={
        init:function(){
            this.pending=false;
            this.clicked=false;
            this.lastCheck=0;
            this.bind();
        },
        bind:function(){
            var atf=this;
            this.view.scroll(function(){atf.auto(false)});
            this.view.resize(function(){atf.auto(true)});
        },
        auto:function(isResize){
            if(this.pending && !isResize) return;
            var diff=new Date().getTime() - this.lastCheck,
                delay=diff<100?100-diff:10,
                atf=this;
            this.pending=true;          
            this.offset=$(this.refer).offset();
            this.height=this.el.height();
            setTimeout(function(){atf.slide()},delay);
        },
        slide:function(){
            var yPos=this.view.scrollTop(),
                clientTop=this.offset.top-yPos,
                bottom=(this.vertical=="bottom")?(clientTop-this.view.height()):(clientTop-this.height);
            if(yPos>=this.scrollT && bottom>0){
                if(!this.clicked && this.el.is(":hidden")){
                    this.animate?this.el.slideDown(200):this.el.show();
                }
            }else{
                if(this.el.is(":visible")) this.animate?this.el.slideUp(200):this.el.hide();
                this.clicked=false;
            }
            this.pending=false;
            this.lastCheck=new Date().getTime();
        }
    };
    return function(cfg){
        return new MiniATF(cfg);
    }
});
