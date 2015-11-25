define(function(require){
	var $=require("jQuery"),
	config=require("config"),
    base=config.portalServer+"/customer/notify";
	require("widgets-css");
	var template={
		confirm:'<div class="ui:confirm"><div class="ui:wrap"><div class="ui:title"><label></label><b>X</b></div><div class="ui:content"><p></p></div><div class="ui:button"><button name="ok" class="button:ok">确认</button><button name="cancel" class="button:cancel">取消</button></div></div></div>',
		overlay:'<div class="ui:overlay"><iframe></iframe></div>',
		highlight:'<div class="ui:highlight"></div>',
		loading:'<div class="ui:loading"></div>',
		cartButton:'<div class="ui:button"><button class="button:gotoCart">去购物车并结算</button><button class="button:continue">继续购物&gt;&gt;</button></div>',
		tip:'<div class="ui:tip"><div class="ui:wrap"><div class="ui:title"><label></label><b>X</b></div><div class="ui:content">aa</div></div></div>',
		pricecut:'<div class="col col-master-popwindow"><div class="title"><h3></h3><a class="close" href="javascript:void(0);"></a></div><div class="cont">'+
		         '<div class="cut-price"><div class="cut-price-notice">一旦商品在30日内降价，您将收到邮件或短信通知！您可以进入“我的文轩 > 降价通知”查看降价商品。</div><div class="cut-price-active"></div></div></div></div>',
		normalcut:'<div class="form-normal-list"> <div class="form-item"><span class="text-title">价格低于￥</span>'+
            '<input class="text bold" name="expectedprice" type="text" value="30.00"  required="required" pattern="^[0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*$"><span>时，通知我</span>'+
            '</div> <div class="form-item"><span class="text-title">手机号码：</span><input class="text" name="phone" type="text" pattern="^[0-9]{11}$"></div>'+
            '<div class="form-item"><span class="text-title">邮箱地址：</span><input class="text" name="email" type="text" required="required" pattern="^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$"><span class="text-tip">* 必填</span></div>'+
            '<div class="form-item"><span class="text-title"></span><button class="button submit">提交</button></div> </div>',
        successcut:'<div class="popwindow-tip"><span class="success">订阅成功！</span></div>',
        errorcut:'<div class="popwindow-tip"><span class="fail"><b>因网络延迟</b>，您未能订阅成功，<br>请您重新操作！</span><div class="form-normal-list"><div class="form-item center"><button class="button back">返回</button></div></div></div>'
	},
	fn={
		locate:function(target,dock,direction){
			
			if((!!dock)&&dock.length>0){
				var offset=dock.offset(),
					top=offset.top-dock.height()-target.height(),
					left=offset.left-target.width()/2;
					if(direction=="left"){
						top=offset.top+dock.height()*2-target.height(),
				     	left=offset.left-target.width()-dock.width();
					}
					
					top=top<0?0:top;
					left=left<0?0:left;
					target.css({top:top,left:left});
			}else{
				fn.center(target);
			}
		},
		position:function(target,dock,direction){
			var dct=direction||"above",
				offset=dock.offset(),
				top,left;
				if(dct=="above"){
					top=offset.top-dock.height()-target.height(),
					left=offset.left-target.width()/2+dock.width()/2;
				
					top=top<0?0:top;
					left=left<0?0:left;
				}
				// TODO
			target.css({top:top,left:left});
		},
		center:function(target){
			var top=$(window).height()/2-target.height()/2+$(document).scrollTop(),
				left=$(window).width()/2-target.width()/2;
				
				top=top<0?0:top;
				left=left<0?0:left;

			target.css({top:top,left:left});
		},
		fullScreen:function(el){
			var dh=$(document).height(),
				width=$(window).width(),
				wh=$(window).height(),
				iframe=el.find("iframe");
			if(dh<wh){
				el.css({height:wh,width:width});
				iframe.css({height:wh,width:width});
			}else{
				el.css({height:dh,width:width});
				iframe.css({height:dh,width:width});
			}
		}
	};
	
	function Confirm(cfg){
		if(window==this)return new Confirm(cfg);
		this.defCfg={
			callback:function(){},
			cancel:function(){},
			width:200,
			height:"auto",
			title:"",
			message:"",
			buttons:{
				ok:"确认",
				cancel:"取消"
			},
			isOverlay:false,
			dock:null
		};
		
		this.init(cfg);
	};
	
	Confirm.prototype={
		init:function(cfg){
			if(typeof(cfg)=="string"){
				this.defCfg.message=cfg;
			}else{
				$.extend(this.defCfg,cfg);
			}
			this.show(this.defCfg);
		},
		show:function(cfg){
			this.el=$(template.confirm);
			this.el.find(".ui\\:title label").html(cfg.title);
			this.el.find(".ui\\:content p").html(cfg.message);
			this.initButton();
			this.el.find(".ui\\:wrap").css({
				width:cfg.width,
				height:cfg.height
			});
			this.el.appendTo(document.body);
			if(cfg.isOverlay){
				this.overlay=$(template.overlay);
				this.overlay.css({width:$(document).width(),height:$(document).height()});
				this.overlay.appendTo(document.body);
			}
			fn.locate(this.el,$(cfg.dock),'left');
			this.bind();
		},
		initButton:function(){
			var btns=this.defCfg.buttons;
			if(btns.ok){
				this.el.find("button[name='ok']").html(btns.ok);
			}else{
				this.el.find("button[name='ok']").remove();
			}
			if(btns.cancel){
				this.el.find("button[name='cancel']").html(btns.cancel);
			}else{
				this.el.find("button[name='cancel']").remove();
			}
		},
		bind:function(){
			var obj=this;
			this.el.find("button[name='ok']").click(function(){obj.submit();});
			this.el.find("button[name='cancel']").click(function(){obj.close();});
			this.el.find(".ui\\:title b").click(function(){obj.close();});
		},
		submit:function(){
			this.close();
			this.defCfg.callback();
		},
		close:function(){
			this.el.remove();
			if(this.overlay){
				this.overlay.remove();
			}
		}
	};
	
	function Highlight(cfg){
		if(window==this)return new Highlight(cfg);
		this.defCfg={
			overlay:true,
			el:null
		};
		this.init(cfg);
	};
	
	Highlight.prototype={
		init:function(cfg){
			this.cfg=$.extend(this.defCfg,cfg);
			if (!!this.cfg.el) {
				if(this.cfg.overlay){
					this.overlay=$(template.overlay);
					fn.fullScreen(this.overlay);
					this.overlay.hide();
					this.overlay.appendTo(document.body);
				}
				this.el=$(template.highlight);
				var el=this.cfg.el;
					el.css({margin:0,padding:0});
					el.appendTo(this.el);
				this.el.hide();
				this.el.appendTo(document.body);
				this.show();
			}
		},
		show:function(){
			if(this.overlay){
				fn.fullScreen(this.overlay);
				this.overlay.show();
			}
			fn.center(this.el);
			this.el.show();
		},
		close:function(){
			if(this.overlay)
				this.overlay.hide();
			this.el.hide();
		},
		remove:function(){
			this.overlay.remove();
			this.el.remove();
		}
	};
	
	function TipWindow(cfg){
		if(window==this)return new TipWindow(cfg);
		this.opt={
			isAjax:true,
			title:"提示",
			dock:null,
			loadingText:"正在加载...请稍后",
			button:template.cartButton,
			callback:function(){
				throw new Error("TODO add tip action");
			}
		};
		$.extend(this.opt,cfg);
		this.init();
	};
	TipWindow.prototype={
		init:function(){
			this.el=$(template.tip);
			this.title=this.el.find("div.ui\\:title");
			this.content=this.el.find("div.ui\\:content");
			this.title.find("label").html(this.opt.title);
			this.el.find(".ui\\:wrap").css({
				width:this.opt.width,
				height:this.opt.height
			});
			if(this.opt.isAjax){
				this.content.html("<div class='ui:loading'>"+this.opt.loadingText+"</div>");
			}
			this.el.appendTo(document.body);
			fn.locate(this.el,$(this.opt.dock));
			this.bind();
		},
		bind:function(){
			var obj=this;
			this.title.find("b").click(function(){
				obj.close();
			});
		},
		change:function(status,msg){
			var obj=this;
			if(status=="success"){
				this.content.html("<div class='ui:msg'>"+msg+"</div>");
				if(!this.button){
					this.button=$(this.opt.button);
					this.button.find("button.button\\:gotoCart").click(function(){
						obj.opt.callback();
					});
					this.button.find("button.button\\:continue").click(function(){
						obj.close();
					});
					this.button.appendTo(this.el.find("div.ui\\:wrap"));
				}else{
					this.button.show();
				}
			}else if(status=="error"){
				this.content.html("<div class='ui:error'>"+msg+"</div>");			
			}else if(status=="loading"){
				this.content.html("<div class='ui:loading'>"+this.opt.loadingText+"</div>");
				if(this.button){
					this.button.hide();
				}
			}
			fn.locate(this.el,$(this.opt.dock));
		},
		close:function(){
			this.el.remove();
			this.hasClosed=true;
		}
	};
	function PriceCut(cfg){
        if(window==this)return new PriceCut(cfg);
        this.opt={
            title:"降价通知",
            pid:null,
            price:"",
            email:"",
            phone:"",
            dock:null,
            callback:function(){
                throw new Error("TODO add tip action");
            }
        };
        this.message={
            email:"请输入格式正确的邮箱地址",
            expectedprice:"价格必须为正确的数值",
            phone:"请输入格式正确的手机号码"
        };
        $.extend(this.opt,cfg);
        this.init();
    };
    PriceCut.prototype={
        init:function(){
            this.el=$(template.pricecut);
            this.title=this.el.find("div.title");
            this.content=this.el.find("div.cut-price-active");
            this.content.html(template.normalcut);
            this.expectedprice=this.el.find("input[name='expectedprice']");
            this.email=this.el.find("input[name='email']");
            this.phone=this.el.find("input[name='phone']");
            this.title.find("h3").html(this.opt.title);
            this.expectedprice.val(this.opt.price);
            this.email.val(this.opt.email);
            this.phone.val(this.opt.phone);
            this.el.appendTo(document.body);
            this.bind();
        },
        bind:function(){
            var obj=this;
            this.title.find(".close").click(function(){
                obj.close();
            });
            this.el.find("button.submit").click(function(){
                obj.submit();
            });
            this.el.delegate("button.back","click",function(){
                obj.content.html(template.normalcut);
            });
        },
        submit:function(){
            var obj=this;
            if(this.validate(this.email,this.message.email)&&
            this.validate(this.expectedprice,this.message.expectedprice)&&
            this.validate(this.phone,this.message.phone)){
                var url=[base+"/add?p="+this.opt.pid];
                    url.push("type="+461004);
                    url.push("expectedprice="+this.expectedprice.val());
                    url.push("email="+this.email.val());
                    url.push("phone="+this.phone.val());
                    url.push("format=json");
                    url.push("t="+new Date().getTime());
                $.post(url.join("&"),function(data){
                    if(data.status==1||data.status==2){
                        obj.success();
                    }else{
                        obj.error();
                    }
                });
            }
        },
        validate:function(el,error){
            var str = $.trim(el.val()),
                required=el.attr("required"),
                reg = el.attr("pattern");
            if(required){
                if(new RegExp(reg).test(str)){
                    return true;
                }else{
                    alert(error);
                    return false;
                }
            }else{
                if(!str || (str && new RegExp(reg).test(str))){
                    return true;
                }else{  
                    return false;
                }
            }
        },
        close:function(){
            this.el.remove();
            this.hasClosed=true;
        },
        success:function(){
            this.content.html(template.successcut);
        },
        error:function(){
            this.content.html(template.errorcut);
        }
    };
	return {
		
		confirm:function(cfg){
			return Confirm(cfg);
		},
		
		highlight:function(cfg){
			return Highlight(cfg);
		},
		tipWindow:function(cfg){
			return TipWindow(cfg);
		},
		priceCut:function(cfg){
		    return PriceCut(cfg);
		}
	};
	
});
