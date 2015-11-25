define(function(require){
	var $=require("jQuery"),
		css=require("widgets-css"),
		conf=require("config");
	var login={
			callbackUrl:"",
			LOADED_EVENT:"loaded",
			LOGINED_EVENT:"logined",
			iframeContent:'<!DOCTYPE HTML><html><head><title>登录小窗口</title><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/><style type="text/css">body{font-size:12px;}body,p,ul,li,form a{margin:0;padding:0;text-decoration:none;}.login-form ul{list-style:none;}.login-form li.selected{display:block;}.login-form li{display:none;}.login-form p{clear:both;height:24px;line-height:24px;margin:10px 0;}.login-form input{width:170px;border:1px solid #ccc;padding:4px;float:left;}.login-form label{margin:0 10px;width:80px;display:block;width:60px;text-align:right;height:24px;float:left;}.login-form .buttonWrap a{margin:0 10px;color:#bfbfbf;}.login-form .buttonWrap button{height:29px;width:67px;border:none;background:url(http://static.winxuancdn.com/css2/images/detail-bg.png) no-repeat -302px -67px;color:#fff;}form .buttonWrap span{display:none;}form.loading .buttonWrap span{display:block;position:absolute;left:50px;width:20px;height:20px;background:url('+conf.portalServer+'/css/images/loading_16x16.gif) no-repeat center;} .login-form .buttonWrap {margin:10px 0;position:relative;padding:0 0 0 80px;}.login-form .info{text-align:center;}</style></head><body><form action="http://passport.winxuan.com/login" method="post" target="hidenIframe"><input type="hidden" name="redirectUrl" value=""/><div class="login-form"><p><label for="name">帐户名</label><input type="text" name="name"/></p><p><label for="password">密　码</label><input type="password" name="password"/></p><div class="buttonWrap"><span></span><button>登录</button><a href="http://passport.winxuan.com/findPassword" target="_blank">忘记密码？</a></div><div class="info">还不是文轩网用户？<a href="http://passport.winxuan.com/register?returnUrl='+encodeURIComponent(document.URL)+'" target="shoppingcart">马上注册</a></div></div></form><iframe name="hidenIframe" style="display:none;"></iframe></body></html>',
			template:'<div class="widgets-window mini-login" id="miniLogin" style="display:none;"><div class="widgets-title"><ul><li class="selected">请先登录</li><li>使用手机/其它帐号</li></ul><a href="javascript:;">X</a></div><div class="widgets-content"><iframe name="loginFrame" frameborder="0"></iframe></div></div>',
			isLogin:function(){
				$.ajax({
					url:conf.portalServer+"/customer/status?mode=2&format=jsonp&callback=?",
					success:function(data){
						$(login).trigger(login.LOADED_EVENT,[data])
					},
					global:false,
					dataType:"jsonp"
				});
			},
			miniLogin:function(){
				if($("#miniLogin").length==0){
					$(this.template).appendTo(document.body);
					var doc=window.frames["loginFrame"].document;
					doc.write(this.iframeContent);
					doc.close();
					this.doc=$(doc);
				}
				this.el=$("#miniLogin");
				this.init();
			},
			init:function(){
				this.el.find(".widgets-title a").click(function(){login.el.hide();});
				this.doc.find("button").click(login.submit);
			},
			submit:function(e){
				var form=login.doc.find("form"),action=form.attr("action"),
					url=form.find("input[name='redirectUrl']");
					if(url.length==0){
						try{
							url=$("<input type='hidden' name='redirectUrl' value='"+login.callbackUrl+"'/>");
							url.appendTo(form);
						}catch(er){alert(er.message);}
					}else{
						url.val(login.callbackUrl);
					}
				form.submit();
				form.find(":input").attr("disabled","disabled");
				form.addClass("loading");
				e.preventDefault();
				e.stopPropagation();
				return false;
			},
			show:function(refEl){
				if(!this.el){
					this.miniLogin();
				}
				this.locate(refEl);
				this.doc.find(":disabled").removeAttr("disabled");
				this.el.show();
			},
			locate:function(el){
				if(!!el){
					var offset=$(el).offset();
					this.el.css({
						"top":offset.top-$(el).height()-this.el.height(),
						"left":offset.left-(this.el.width())+$(el).width()
					});
				}
			},
			hide:function(){
				login.doc.find(":disabled").removeAttr("disabled");
				login.doc.find("form").removeClass("loading");
				this.el.hide();
			},
			callback:function(status,msg){
				if(status=="1"){
					$(login).trigger(login.LOGINED_EVENT);
					this.hide();
				}else{
					login.doc.find(":disabled").removeAttr("disabled");
					login.doc.find("form").removeClass("loading");
					alert(msg);
				}
			}
		};
	return login;
});
