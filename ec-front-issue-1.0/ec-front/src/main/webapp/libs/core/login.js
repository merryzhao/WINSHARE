define(function(require){
	var $=require("jQuery"),
		css=require("widgets-css"),
		conf=require("config"),
		template='<div class="w-mask"></div><div class="w-signin"><div class="signin"><form action="https://passport.winxuan.com/verify" method="get"><fieldset><legend>登录文轩网</legend><p><label for="name">帐　户</label><input type="text" name="name" tabindex="1"/></p><p class="tip" for="name">请在上方输入您的用户名或者Email邮箱</p><p><label for="password">密　码</label><input type="password" name="password" tabindex="2"/><a href="https://passport.winxuan.com/findPassword">忘记密码？</a></p><p class="verify"><label>验证码</label><input type="text" name="verifyCode"/><img src="javascript:;"/><a href="javascript:;" bind="refresh">换一张</a></p><p class="message"></p><div><button tabindex="3">登　录</button><em>新用户？</em><a href="https://passport.winxuan.com/signup">立即注册</a></div></fieldset></form><div class="other"><p>使用合作网站帐户登录文轩网：</p><ul><li class="QQ"><a href="https://passport.winxuan.com/auth/qq"><s></s>QQ</a></li><li class="alipay"><a href="https://passport.winxuan.com/auth/alipay"><s></s>支付宝</a></li><li class="weibo"><a href="https://passport.winxuan.com/auth/weibo"><s></s>新浪微博</a></li><li class="douban"><a href="https://passport.winxuan.com/auth/douban"><s></s>豆瓣</a></li></ul></div><div class="anonym"><p>暂不登录，我想快速购买：<a href="javascript:;" bind="continue">继续结算&gt;&gt;</a></p></div><a href="javascript:;" class="close">关闭</a></div></div>',
		message={
			"0":"请输入你的用户名或Email及密码",
			"2":"用户名或密码错误",
			"3":"用户名或密码错误",
			"4":"当前用户已被锁定，请与客服联系或者尝试<a href='https://passport.winxuan.com/findPassword'>修改密码<a/>",
			"5":"验证码错误"
		},
		countDown={
		"2":",剩余次数 ",
		"3":",剩余次数 "
	    }
	
	    
		
	var login={
			callbackUrl:"",
			LOADED_EVENT:"loaded",
			LOGINED_EVENT:"logined",
			lockCountDown:0,
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
				if($("div.w-signin").length==0){
					$(template).appendTo(document.body);
				}
				this.mask=$("div.w-mask");
				this.el=$("div.w-signin");
				this.init();
			},
			
			init:function(){
				this.el.find("a.close").click(function(){login.el.hide();login.mask.hide();});
				this.el.find("a[bind='refresh']").click(function(){login.refreshCode()});
				this.el.find("button").click(login.submit);
				this.el.find("input").focus(
					function(){
						$(this).addClass("focus");
						login.el.find("p.tip[for='"+$(this).attr("name")+"']").removeClass("hidden").addClass("visible");
					}).blur(
					function(){
						$(this).removeClass("focus error");
						login.el.find("p.tip[for='"+$(this).attr("name")+"']").removeClass("visible").addClass("hidden");
					}
				);
				this.msgEl=this.el.find("p.message");
				this.el.delegate("a","click",function(){
					var anchor=$(this),href=anchor.attr("href")+"?returnUrl="+encodeURIComponent(document.URL);
					if(!(href.indexOf("javascript:;")>-1)){
						anchor.attr("href",href);
					}
				});
				this.el.find("a[bind='continue']").click(function(e){
					login.callback("1");
					e.preventDefault();
					e.stopPropagation();
				});
				
				this.el.find("input[name='name']").blur(function(e){
					login.checkStatus();
				});
			},
			
			submit:function(e){
				var model = this;
				var form=login.el.find("form"),url=form.attr("action"),button=form.find("button"),params=form.serialize();
					if(login.validate(form)){
						button.attr("disabled","disabled");
						form.find(":input").attr("disabled","disabled");
						form.addClass("loading");
						$.getJSON(url+"?format=jsonp&callback=?",params,function(data){
							login.callback(data.status, data.lockCountDown);
							if(data.status!="1"){
								login.el.find("p.verify img").data("nonValidated",false);
								login.showVerifyCode();
							}
						});
					}
					e.preventDefault();
					e.stopPropagation();
				return false;
			},
			validate:function(form){
				var result=true;
				form.find("input:visible").each(function(){
					if((!this.value)||this.value.length==0){
						result=false;
						$(this).addClass("error");
						return false;
					}else{
						$(this).removeClass("error");
					}
				});
				if(!result){
					this.tip("0");
				}
				return result;				
			},
			show:function(cfg){
				if(!this.el){
					this.miniLogin();
				}
				if(!!cfg){
					this.config(cfg);
				}
				this.locate();
				this.el.find(":disabled").removeAttr("disabled");
				this.checkStatus();
				this.el.show();
			},
			
			checkStatus:function(){
				var api=conf.passportServer+"/needVerifyCode",
					name=this.el.find("input[name='name']");
				if(name.val()&&name.val().length>0){
					$.getJSON(api+"?format=jsonp&callback=?&username="+name.val(),function(data){
						if(!!data){
							if(data.needVerifyCode){
								login.showVerifyCode();
							}else{
								login.hideVerifyCode();
							}							
						}
					});
				}
			},
			refreshCode:function(){
				var img=this.el.find("p.verify img"),url=conf.passportServer+"/verifyCode";
				img.attr("src",url+"?"+(new Date()).getTime());
			},
			
			showVerifyCode:function(){
				var verify=this.el.find("p.verify"),img=verify.find("img"),url=conf.passportServer+"/verifyCode";
					verify.show();
					if(!img.data("nonValidated")){
						img.attr("src",url+"?"+(new Date()).getTime());
						img.data("nonValidated",true);						
					}
			},
			
			hideVerifyCode:function(){
				this.el.find("p.verify").hide();
			},
			
			config:function(cfg){
				if(cfg.user){
					this.el.find("input[name='name']").val(cfg.user);
				}
				if(cfg.message){
					this.el.find("p.message").addClass("error").html(cfg.message);
				}
				if(cfg.showBalance){
					this.el.find(".anonym").show();
				}else{
					this.el.find(".anonym").hide();
				}
			},
			locate:function(el){
				var doc=$(document),win=$(window);
				this.mask.css({
					width:doc.width(),
					height:doc.height()
				});
				this.el.css({
					top:(win.height()-this.el.height())/2+doc.scrollTop(),
					left:(win.width()-this.el.width())/2+doc.scrollLeft()
				});
				this.mask.show();
			},
			hide:function(){
				this.el.find(":disabled").removeAttr("disabled");
				this.el.find("form").removeClass("loading");
				this.el.hide();
				this.mask.hide();
			},
			
			callback:function(status,msg){
				if(status=="1"){
					$(this).trigger(this.LOGINED_EVENT);
					this.msgEl.removeClass("error");
					this.hide();
				}else{
					this.tip(status,msg);
				}
			},
			
			tip:function(status,msg){
				var countDownMsg  = countDown[status]+msg;
				console.info(!countDown[status]);
				if(!countDown[status]){
					this.msgEl.html(message[status]);
				}else{
					this.msgEl.html(message[status]+countDown[status]+msg);
				}
				
				this.msgEl.addClass("error");
				this.el.find(":disabled").removeAttr("disabled");
				this.el.find("form").removeClass("loading");
			}
		};
	return login;
});
