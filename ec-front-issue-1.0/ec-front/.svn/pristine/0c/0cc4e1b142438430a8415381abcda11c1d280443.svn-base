/**
 * 
 * 用户登录/注册框View
 *  
 * @version 0.1
 * @author min@winxuan.com
 * @copyright winxuan.com
 * 
 */
define(function(require){
	
	var $=require("jQuery"),
		Observable=require("../event/observable"),
		UserEvent=require("../event/user"),
		UI=require("../util/ui");
		
	/**
	 * 
	 * Panel 对象，注册和登录都由此对象保持状态
	 * 
	 * @param {Object} template
	 */
	
	function Panel(template,ajaxFunc){
		
		this.el=$(template);
		this.verifyImg=this.el.find('img[name="verify"]');
		this.form=this.el.find('form');
		this.button=this.el.find("button");
		this.msgEl=this.el.find("p.message");
		this.otherEl=this.el.find("div.other");
		this.ajaxFunc=ajaxFunc||function(){};
		$.extend(this,Observable);
		
		this.init();
	};
	
	Panel.prototype={
		init:function(){
			var panel=this;
			
			this.el.delegate("[bind]","click",function(e){panel.trigger({type:$(this).attr("bind"),source:this});e.preventDefault();e.stopPropagation();});
			this.otherEl.delegate("a","click",function(e){
				if(this.href.indexOf("returnUrl")==-1&&this.href!="javascript:;"){
					this.href+="?returnUrl="+encodeURI(document.location.href);
				}
			});
			this.el.delegate("form","keypress",function(e){panel.keypress(this,e)});
			this.loadVerify();
			this.bind();
		},
		
		loadVerify:function(){
			
			if(this.verifyImg.length>0&&this.verifyImg.is(":visible")){
				this.verifyImg.attr("src","http://passport.winxuan.com/verifyCode?"+(new Date).getTime());
			}
			
		},
		showVerify:function(){
			this.verifyImg.parents(".verify").show();
			if(!this.verifyImg.data("loaded")){
				this.loadVerify();
				this.verifyImg.data("loaded",true);
			}
		},
		hideVerify:function(){
			this.verifyImg.parent(".verify").hide();
		},
		keypress:function(form,e){
			var name=$(form).attr("name"),
				eventType;
			if(e.keyCode==13){
				eventType=(name=="signin")?UserEvent.SIGNIN_SUBMIT:UserEvent.SIGNUP_SUBMIT;
				this.trigger({
					type:eventType,
					source:form
				});
			}
		},
			
		bind:function(){
			var panel=this;
			this.el.find("input").focus(function(e){panel.focus(this)}).
				blur(function(e){panel.blur(this)});
			
			panel.on(UserEvent.SIGNUP_SUBMIT,function(e){panel.lock();}).
				on(UserEvent.SIGNIN_SUBMIT,function(e){panel.lock();}).
				on(UserEvent.RELOAD_VERIFY,function(e){panel.loadVerify()});
		},
		
		focus:function(el){
			
			var el=$(el);
				el.removeClass("placeholder");
			this.showTip(el);
			this.msgEl.hide();
		},
		
		blur:function(el){
			var el=$(el),
				value=el.val(),
				type=el.attr("type"),
				ajax=el.attr("ajax");
			if(type=="text"||type=="password"){
				if(value==el.attr("placeholder")||value.length==0){
					if(!el.hasClass("placeholder")){
						el.addClass("placeholder");
					}
				}
			}
			this.hideTip(el);
			if(ajax&&value.length>0&&this.check(el)){
				this.ajaxValid(el)
			}
		},
		
		ajaxValid:function(el){
			this.ajaxFunc(el);
		},
		
		lock:function(){
			if(this.validate()){
				this.button.data("text",this.button.text());
				this.el.find(":input").attr("disabled","disabled");
				this.el.find(".loading").css("visibility","visible");
				this.button.text("处理中...");
			}
		},
		
		validate:function(){
			var fields=this.form.find("input"),
				panel=this;
			
			panel.isValidated=true;
			
			fields.each(function(){
				var valid=false;
				if($(this).is(":visible")){
					if(this.validity){
						valid=this.validity.valid; /* 支持Form 2.0特性*/
					}else{
						valid=panel.check(this);   /* 不支持Form 2.0 向下兼容处理*/
					}
					if(!valid){
						panel.isValidated=false;
						panel.showRule(this);
						return false;		
					}
				}
			});
			
			return panel.isValidated;
		},
		
		showRule:function(el,msg){
			var el=$(el),
				name=el.attr("name"),
				tip,message=msg||el.attr("message");
			if(name){
				tip=this.el.find("p."+name);
				tip.data("init-text",tip.text());
				if(message){
					tip.html(message);
					tip.addClass("error")				
				}
				this.showTip(el);
			}
		},
		
		showTip:function(el){	
			var name=el.attr("name");
			if(name){
				this.el.find("p."+name).css({
					"visibility":"visible"
				});
			}		
		},
		
		hideTip:function(el){
			var name=el.attr("name"),
				tip,initText;
			if(name){
				this.el.find("p."+name).css("visibility","hidden");
				tip=this.el.find("p."+name);
				initText=tip.data("init-text");
				if(initText){
					tip.html(initText);
				}
				tip.removeClass("error");
			}
		},
		
		
		check:function(el){
			var el=$(this),reg,
				required=el.attr("required"),
				pattern=el.attr("pattern");
			if(required){
				reg=new RegExp(pattern);
				if(reg.test(el.val())){
					return true;
				}
				else{
					this.showTip(el);
					return false;
				}
			}
			return true;
		},
		
		unlock:function(){
			this.el.find(":input").removeAttr("disabled");
			this.el.find(".loading").css("visibility","hidden");
			this.button.text(this.button.data("text"));
		},
		
		appendTo:function(target){
			this.el.appendTo(target);
		},
		
		getFormData:function(){
			var data={};
			
			this.form.find("input").each(function(){
				var el=$(this),
					name=el.attr("name"),
					value=el.val();
				if((!!name)&&name.length>0){
					data[name]=value;					
				}
			});
			
			return data;
			
		},
		submit:function(ref){
			var action=this.form.attr("action"),
				name=this.form.find("input[name='name']"),
				email=this.form.find("input[name='email']");
				name.val(email.val());
			if(action.indexOf("returnUrl")==-1){
				action+="?returnUrl="+encodeURIComponent(ref||document.URL);
				action+="&"+$.param(this.getFormData());
				this.form.attr("action",action);
				this.form.submit();
			}
		},
		
		error:function(msg){
			this.msgEl.show();
			this.msgEl.html(msg);
			this.unlock();
			this.showVerify();
			this.loadVerify();
		},
		
		show:function(){
			this.el.show();
			this.loadVerify();
		},
		hide:function(){
			this.el.hide();
		}
	};
	
	
	
	function View(footer){
		var view=this;
		this.el=$(View.TEMPLATE.BASE.replace(/\{footer\}/g,footer));
		this.panels=this.el.find(".panels");
		this.closeButton=this.el.find(".close");
		this.navTabs=this.el.find(".nav-tab li");
		this.signinPanel=new Panel(View.TEMPLATE.SIGNIN,function(el){view.signinNameValidate(this,el)});
		this.signupPanel=new Panel(View.TEMPLATE.SIGNUP,function(el){view.signupNameValidate(this,el)});
		this.mask=UI.build("Mask");
		this.currentState="signin";
		
		this.signinPanel.appendTo(this.panels);
		this.signupPanel.appendTo(this.panels);
		
		$.extend(this,Observable);
		
		this.init();
		
	};
	
	View.TEMPLATE={
		BASE:'<div id="mini-signin"><div class="wrap">'+
			'<ul class="nav-tab"><li class="selected" tabindex="1">登录</li><li tabindex="2">注册</li></ul>'+
			'<div class="panels"></div>'+
			'<div class="foot-panel">{footer}</div>'+
			'<div class="close"><a href="javascript:;">关闭</a></div>'+
			'</div></div>',
			
		SIGNIN:'<div class="signin-panel"><form name="signin" action="https://passport.winxuan.com/signin" method="GET">'+
			'<fieldset><div><label>帐户</label><input name="name" type="text" class="placeholder" placeholder="帐户名或Email地址" required="required" pattern=".{1,}" ajax="https://passport.winxuan.com/needVerifyCode"/></div>'+
			'<p class="name tip">请在上方输入您的帐户名/Email</p>'+
			'<div><label>密码</label><input name="password" type="password" class="placeholder" placeholder="您的文轩网密码" required="required" pattern=".{4,}"/><a href="https://passport.winxuan.com/findPassword" target="_blank">忘记密码了？</a></div>'+
			'<p class="password tip">请在上方输入您的密码</p>'+
			'<div class="verify"><div><label>验证码</label><input type="text" name="verifyCode" required="required" pattern="[A-Za-z0-9_-]{4}"/><img name="verify"/><a href="#" bind="reload_verify">换一张</a></div>'+
			'<p class="verifyCode tip">请输入右上方图片中的文字</p></div>'+
			'</fieldset><p class="message"></p>'+
			'<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signin_submit">登录</button>'+
			'<b>新用户？</b><a href="javascript:;" bind="user_signup">立即注册</a></div>'+
			'</form><div class="other">'+
			'<p>使用下面合作网站帐号登录文轩网</p>'+
			'<ul><li class="qq"><a href="https://passport.winxuan.com/auth/qq" target="signin">QQ</a></li>'+
			'<li class="alipay"><a href="https://passport.winxuan.com/auth/alipay" target="signin">支付宝</a></li>'+
			'<li class="sina"><a href="https://passport.winxuan.com/auth/weibo" target="signin">新浪微博</a></li>'+
			'<li class="douban"><a href="https://passport.winxuan.com/auth/douban" target="signin">豆瓣</a></li></ul>'+
			'</div></div>',
			
		SIGNUP:'<div class="signup-panel"><form name="signup" action="https://passport.winxuan.com/signup" method="POST" class="signup"><input type="hidden" name="name"/><fieldset>'+
			'<div><label>邮箱</label><input name="email" type="text" class="placeholder" placeholder="电子邮箱/Email地址" required="required" pattern="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$" message="请按正确的Email格式填写" ajax="https://passport.winxuan.com/emailExist"/></div>'+
			'<p class="email tip">请输入您的Email，做为您的帐户名</p>'+
			'<div><label>密码</label><input name="password" class="placeholder" type="password" placeholder="请输入您的密码" required="required" pattern=".{4,12}" message="密码由4-12位字符、符号组成"/></div>'+
			'<p class="password tip">请输入您的文轩网密码</p>'+
			'<div><label>确认密码</label><input name="passwordConfirm" class="placeholder" type="password" placeholder="请重复输入上方的密码" required="required" pattern=".{4,12}" message="密码由4-12位字符、符号组成"/></div>'+
			'<p class="passwordConfirm tip">请重复输入上方的登录密码</p>'+
			'<div class="verify"><label>验证码</label><input type="text" name="verifyCode" required="required" pattern="[A-Za-z0-9_-]{4}"/><img name="verify"/><a href="#" bind="reload_verify">换一张</a></div>'+
			'<p class="verifyCode tip">请输入右上方图片中的文字</p>'+
			'<div class="agreement"><input type="checkbox" name="agree" required="required" value="1"/><p>我已阅读并同意 <a href="http://www.winxuan.com/help/terms.html" target="terms">《文轩网交易条款》</a></p></div>'+
			'<p class="agree tip">请阅读并同意文轩网交易条款</p><p class="message"></p></fieldset>'+
			'<div class="b-wrap"><span class="loading">处理中...</span><button type="button" bind="signup_submit">立即注册</button></div>'+
			'</form></div>'	
	};
	
	View.prototype={
		
		init:function(){
			
			this.el.hide();
			this.el.appendTo(document.body);
			
			this.bind();
		},
		
		bind:function(){
			var view=this;
			
			view.closeButton.click(function(){view.close();});
			view.navTabs.click(function(){view.toggle(this);});
			
			view.signinPanel.
			on(UserEvent.SIGNUP,function(){
				view.toggle(view.navTabs.filter(":not(.selected)"));
			}).
			on(UserEvent.SIGNIN_SUBMIT,function(e){
				if(view.signinPanel.isValidated){
					view.trigger({type:e.type,eventData:view.signinPanel.getFormData()});					
				}
			});
			
			view.signupPanel.
			on(UserEvent.SIGNUP_SUBMIT,function(e){
				if(view.signupPanel.isValidated){
					view.trigger({type:e.type,eventData:view.signupPanel.getFormData()});	
				}
			});
			
		},
		
		toggle:function(el){
			var el=$(el),idx=el.attr("tabindex");
			
			if(idx=="1"){
				this.currentState="signin";
				this.signinPanel.show();
				this.signupPanel.hide();
			}else{
				this.currentState="signup";
				this.signupPanel.show();
				this.signinPanel.hide();
			}
			
			this.navTabs.removeClass("selected");
			el.addClass("selected");
		},
		error:function(msg){
			
			if(this.currentState=="signin"){
				this.signinPanel.error(msg);
			}else{
				this.signupPanel.error(msg);
			}
			
		},
		
		submit:function(ref){
			this.signupPanel.submit(ref);
		},
		
		show:function(){
			
			this.mask.show();
			UI.center(this.el);
			this.el.show();
			
		},
		
		close:function(){
			this.el.remove();
			this.mask.remove();
		},
		
		signupNameValidate:function(panel,el){
			var img=el.parent().find("img"),url=el.attr("ajax");
			if(img.length===0){
				img=$("<img src='http://static.winxuancdn.com/css/images/loading_16x16.gif' width='16' height='16'/>");
				img.insertAfter(el)
			}
			$.getJSON(url+"?"+el.attr("name")+"="+el.val()+"&format=jsonp&callback=?",function(data){
				if(!data.status){
					img.attr("src","http://static.winxuancdn.com/css/images/success.gif");
					el.data("status","validated");
				}else{
					img.attr("src","http://static.winxuancdn.com/css/images/error.gif");
					panel.isValidated=false;
					panel.showRule(el[0],"此邮箱已被注册，请更换一个邮箱");
					el.data("status","existed");
				}
			});
		},
		
		signinNameValidate:function(panel,el){
			var img=el.parent().find("img"),url=el.attr("ajax");
			if(img.length==0){
				img=$("<img src='http://static.winxuancdn.com/css/images/loading_16x16.gif' width='16' height='16'/>");
				img.insertAfter(el)
			}
			
			$.getJSON(url+"?username="+el.val()+"&format=jsonp&callback=?",function(data){
				if(!data.needVerifyCode){
					panel.hideVerify();
				}else{
					panel.showVerify();
				}
				img.remove();
			});
		}
		
	};
	
	return View;
});
