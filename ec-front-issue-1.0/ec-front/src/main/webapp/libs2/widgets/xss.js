define(function(require){
    var $=require("jQuery");
    function XSS(cfg){
        var opt={
            context:"body"
        };
        $.extend(this,opt,cfg);
        this.init();
    }; 
    XSS.prototype={
        init:function(){
            this.el=$(this.context);
            this.bind();
        },
        bind:function(){
            var validate=this;
            this.el.delegate("input[type='text']","keypress",function(e){
                validate.keypress(this,e);
            });
            this.el.delegate("input[type='text']","blur",function(e){
                validate.keypress(this,e);
            });
        },
        keypress:function(el,e){
            var el=$(el),value=el.val();
            value=value.toString();
            value=value.replace(/</g, "&lt;");
            value=value.replace(/>/g, "&gt;");
            value=value.replace(/"/g, "&quot;");
            value=value.replace(/'/g, "&#39;");
            el.val(value);
        }
    };
    return XSS;
});