define(function(require){
	var $=require("jQuery"),
		View=require("../view/signin-signup"),
		UserEvent=require("../event/user"),
		SandboxEvent=require("../event/sandbox"),
		SigninModel=require("../model/signin-signup");
	
	function SigninModule(cfg){
		this.opt={
			signin:function(){},
			signup:function(){},
			footer:"&nbsp;",
			referrer:null
		};
		$.extend(this.opt,cfg);
		
		this.view=new View(this.opt.footer);
		this.model=new SigninModel;
		this.init();		
	};
	
	SigninModule.MESSAGE={
		"0":"请输您的用户名/Email及您的密码",
		"2":"用户名或密码错误",
		"3":"用户名或密码错误",
		"4":"您的帐户已被锁定，请与我们的客服联系",
		"5":"验证码错误",
		"password":"您输入的两次密码不符，请确认后提交"
	};
	
	SigninModule.prototype={
		
		init:function(){
			this.bindView();
			this.bindModel();	
		},
		
		bindModel:function(){
			var app=this;
			this.model.on(SandboxEvent.NOTIFY,function(e){
				app.notify(e);
			});	
		},
		
		bindView:function(){
			var app=this;
			this.view.on(UserEvent.SIGNIN_SUBMIT,function(e){app.signin(e.eventData);});
			this.view.on(UserEvent.SIGNUP_SUBMIT,function(e){app.signup(e.eventData);});
		},
		
		show:function(){
			this.view.show();	
		},
		
		signin:function(data){
			this.model.signin(data);
		},
		
		signup:function(data){
			if(data.password==data.passwordConfirm){
				this.view.submit(this.opt.referrer);
			}else{
				this.view.error(SigninModule.MESSAGE["password"]);
			}
		},
		
		notify:function(e){
			var data=e.eventData;
			if(data&&data.status=="1"){
				this.view.close();
				if(typeof(this.opt[e.action])=="function"){
					this.opt[e.action]();					
				}
			}else{
				this.view.error(SigninModule.MESSAGE[data.status]);
			}
		}
	};
	
	return SigninModule;
});
