/**
 * @author libin
 */
define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
		UI=require("../util/ui"),
		msgMap={
			mobile:{
				"1":"请输入你要绑定的手机号码！",
				"2":"请输入格式正确的手机号码！"
			},
			code:"校验码输入不正确，请重新输入！",
			password:{
				"1":"密码不能为空！",
				"2":"你输入的密码不符合要求，请重新输入！"
			},
			confirm:"你输入的密码不一致！"
		};
		
		require("checkpass-css");
	function CheckPwd(cfg){
		this.opt={
			title:null,
			callback:null,
			obj:null
		};
		$.extend(this.opt,cfg);
		this.apiUrl=conf.portalServer;
		this.seconds=60;
		this.payEmail=null;
		this.el=$(CheckPwd.TEMPLATE.BASE);
		this.checkMobile=$(CheckPwd.TEMPLATE.MOBILE);
		this.step=this.el.find(".set_step");
		this.mask=UI.build("Mask");
		this.checkMobile.appendTo(this.step);
		this.msgEl=this.el.find("p.message");
		this.init();
	};
	CheckPwd.TEMPLATE={
		BASE:'<div id="check_pass_set"><div class="wrap"><h4 class="f14 fb">暂存款支付密码<span id="set-mod" class="fb">设置</span></h4>'+
			 '<div class="set_step"></div><div class="close"><a href="javascript:void(0);">关闭</a></div></div></div>',
		MOBILE:'<div class="check_mobile"><div class="curr_step"></div><div class="emailTips">如果你是国外手机用户，你可以使用<a href="javascript:;" bind="checkEmail">电子邮箱</a>获取验证码</div>'+
			  '<p class="payMobile erroTip tip">请输入格式正确的手机号码！</p><div class="typefield"><label>手机号码：</label>'+
			  '<input type="text" class="mobile" bind="checkType" name="payMobile" required="required" pattern="^[0-9]{11}$" /><p id="mobile-message" class="message hide">若号码输入有误，请关闭此对话框后重新设置！</p>'+
			  '<span class="bindInfo hide lh"></span><a href="javascript:;" class="hide lh" bind="changeTel">更改绑定的手机号码</a></div>'+
			  '<p class="checkCode tip">请输入正确的校验码！</p><div><label>校验码：</label>'+
			  '<input type="text" class="code" name="checkCode" required="required" pattern="^[0-9]{6}$" /><button class="get_code" bind="getCode" data-check="payMobile">点此免费获取</button>'+
			  '<span class="tips">校验码是6位数字</span></div><div class="btn_field"><button bind="next" data-bind="bindMobile" disabled="true" data-check="checkCode">下一步</button></div></div>',
		EMAIL:'<div class="check_email"><div class="curr_step"></div><div class="emailTips">你也可以继续使用<a href="javascript:;" bind="checkMobile">手机</a>获取验证码</div>'+
			  '<p class="payEmail erroTip tip">请输入格式正确的电子邮箱！</p><div class="typefield"><label>电子邮箱：</label>'+
			  '<input type="text" class="email" bind="checkType" name="payEmail" required="required" pattern="^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$" /><p id="email-message" class="message hide">若邮箱输入有误，请关闭此对话框后重新设置！</p>'+
			  '<span class="bindInfo hide lh"></span><a href="javascript:;" class="hide lh" bind="changeTel">更改绑定的电子邮箱</a></div>'+
			  '<p class="checkCode tip">请输入正确的校验码！</p><div><label>校验码：</label>'+
			  '<input type="text" class="code" name="checkCode" required="required" pattern="^[0-9]{6}$" /><button class="get_code" bind="getCode" data-check="payEmail">点此免费获取</button>'+
			  '<span class="tips">校验码是6位数字</span></div><div class="btn_field"><button bind="next" data-bind="bindEmail" disabled="true" data-check="checkCode">下一步</button></div></div>',
		PASSWORD:'<div class="pass_type"><div class="curr_step"></div><p class="payPassword tip">密码由4-12位字符、符号组成</p>'+
				 '<div><label>新的支付密码：</label><input type="password" name="payPassword" class="new_password" placeholder="密码由4-12位字符、符号组成" required="required" pattern=".{4,12}" /></div>'+
				 '<p class="rePayPassword tip">密码输入不一致</p><div><label>确认输入：</label><input type="password"  name="rePayPassword" class="confirm_password" required="required" pattern=".{4,12}" /></div>'+
				 '<div class="btn_field"><button bind="submit">提交</button></div></div>',
		SUCCESS:'<div class="set_success"><div class="curr_step"></div>'+
				'<div class="success_tip"><span class="fb">恭喜，暂存款支付密码设置成功！</span><a href="javascript:;" bind="back">点此继续</a></div></div>',
		MODIFY:'<div class="modify_mobile"><p class="modify_tip">请拨打客服电话：<span class="red">400-702-0808</span>，'+
			   '经人工核实无误后可解除以前绑定的手机号码或电子邮箱，然后才能重新绑定新的手机号码或电子邮箱。</p></div>'
	};
	CheckPwd.prototype={
		init:function(){
			this.el.hide();
			this.isExist(true);
			this.el.appendTo(document.body);
			this.bind();
		},
		isForeign:function(){
			var pass=this,
				emailUrl=this.apiUrl+"/customer/payment/info?format=jsonp&callback=?";
			$.getJSON(emailUrl,function(data){
				if(data.payEmailStatus==0){
					pass.step.find("div.emailTips").hide();
				}else if(data.payEmailStatus==1){
					pass.payEmail=data.payEmail;
				}
			});
		},
		isExist:function(isRender){
			var pass=this,
				mobileUrl=this.apiUrl+"/customer/advanceaccount/getCustomerMobile?format=jsonp&callback=?";
			$.getJSON(mobileUrl,function(data){
				if(data.payMobile&&isRender){
					pass.render(data.payMobile);
				}
				pass.isForeign();
			});
			if(this.opt.title){
				this.el.find("#set-mod").html(this.opt.title);
			}
		},
		render:function(checkInfo){
			var pass=this;
			pass.step.find("input[bind='checkType']").remove();
			pass.step.find("p.message").remove();
			pass.step.find("span.bindInfo").html(checkInfo).show();
			pass.step.find("a[bind='changeTel']").show();
			pass.el.find("#set-mod").html("修改");
		},
		bind:function(){
			var pass=this;
			this.el.delegate("div.close","click",function(e){pass.close()})
				.delegate("button[bind='getCode']","click",function(e){if(pass.validate(this)){pass.getCode(this)}})
				.delegate("button[bind='next']","click",function(e){if(pass.validate(this)){pass.next(this)}})
				.delegate("button[bind='submit']","click",function(e){if(pass.validate(this)){pass.submit(this)}})
				.delegate("a[bind='changeTel']","click",function(e){pass.changeTel()})
				.delegate("a[bind='back']","click",function(e){pass.back()})
				.delegate("a[bind='checkEmail']","click",function(e){pass.checkEmail()})
				.delegate("a[bind='checkMobile']","click",function(e){pass.mobileCheck()})
				.delegate("input[type='password']","focus",function(e){pass.focus(this)})
				.delegate("input[type='password']","blur",function(e){pass.blur(this)});
		},
		show:function(){
			this.mask.show();
			UI.center(this.el);
			this.el.show();
		},
		close:function(){
			clearInterval(this.Timer);
			this.el.remove();
			this.mask.remove();
			if(typeof this.opt.callback == "function"){
				this.opt.callback.call(this.opt.obj||this);
			}
		},
		getCode:function(el){
			var checkfield=this.el.find("input[bind='checkType']"),
				el=$(el);
			el.prop("disabled",true);
			if(el.data("check")=="payMobile"){
				this.sendToMobile(checkfield,el);
			}else if(el.data("check")=="payEmail"){
				this.sendToEmail(checkfield,el);
			}
		},
		sendToMobile:function(checkfield,el){
			var sendMobileUrl=this.apiUrl+"/customer/advanceaccount/sendCheckCode?format=jsonp&callback=?",
				pass=this;
			checkfield.prop("disabled",true);
			$.getJSON(sendMobileUrl,pass.getFormData(),function(data){
				if(!data.status){
					el.prop("disabled",false);
					checkfield.prop("disabled",false);
					pass.el.find("p.erroTip").html(data.message);
					pass.showTip(checkfield);
				}else{
					pass.renderButton(el);	
				}
			});
		},
		sendToEmail:function(checkfield,el){
			var sendEmailUrl=this.apiUrl+"/customer/advanceaccount/sendVerifyCodeToEmail?format=jsonp&callback=?",
				pass=this;
			checkfield.prop("disabled",true);
			$.getJSON(sendEmailUrl,pass.getFormData(),function(data){
				if(data.status!=1){
					el.prop("disabled",false);
					checkfield.prop("disabled",false);
					pass.el.find("p.erroTip").html(data.message);
					pass.showTip(checkfield);
				}else if(data.status==1){
					pass.el.find("p.erroTip").html(data.message);
					pass.showTip(checkfield);
					pass.renderButton(el);	
				}
			});
		},
		renderButton:function(el){
			var html="(<span id='second'>60</span>)秒后重新获取",
				pass=this;
			this.step.find("p.message").show();
			this.step.find("input.code").show();
			this.step.find("button[bind='next']").prop("disabled",false);
			el.html(html);
			el.prop("disabled",true);
			this.Timer=setInterval(function(){pass.time(el)},1000);
		},
		time:function(el){
			this.seconds=this.seconds-1;
			$("#second").html(this.seconds);
			if(this.seconds<0){
				el.html("点此免费获取");
				el.prop("disabled",false);
				clearInterval(this.Timer);
				this.seconds=60;
			}
		},
		next:function(el){
			var checkfield=this.el.find("input[name='checkCode']"),
				el=$(el);
			if(el.data("bind")=="bindMobile"){
				this.bindMobile(checkfield,el);
			}else if(el.data("bind")=="bindEmail"){
				this.bindEmail(checkfield,el);
			}
		},
		bindMobile:function(checkfield,el){
			var url=this.apiUrl+"/customer/advanceaccount/verifyCheckCode?format=jsonp&callback=?",
				pass=this;
			$.getJSON(url,pass.getFormData(),function(data){
				if(data.status){
					pass.step.html(CheckPwd.TEMPLATE.PASSWORD);
				}else{
					pass.showTip(checkfield);
				}
			});
		},
		bindEmail:function(checkfield,el){
			var url=this.apiUrl+"/customer/advanceaccount/verifyEmailCode?format=jsonp&callback=?",
				pass=this;
			$.getJSON(url,pass.getFormData(),function(data){
				if(data.status==1){
					pass.step.html(CheckPwd.TEMPLATE.PASSWORD);
				}else{
					pass.el.find("p.checkCode").html(data.message);
					pass.showTip(checkfield);
				}
			});
		},
		submit:function(el){
			var url=this.apiUrl+"/customer/advanceaccount/updatePayPassword?format=jsonp&callback=?",
				pass=this,el=$(el),
				passfield=this.el.find("input[name='payPassword']"),
				repassfield=this.el.find("input[name='rePayPassword']");
			if(this.getFormData()["rePayPassword"]!=this.getFormData()["payPassword"]){
				pass.showTip(repassfield);
			}else{
				$.post(url,pass.getFormData(),function(data){
					if(data.status){
						pass.step.html(CheckPwd.TEMPLATE.SUCCESS);
					}else{
						pass.el.find("p.payPassword").html(data.message);
						pass.showTip(passfield);
					}
				}, "json");
			}
		},
		validate:function(el){
			var fields=this.el.find("input"),
				pass=this,
				check=$(el).data("check");
			pass.isValidated=true;
			fields.each(function(){
				if(!check||$(this).attr("name")==check){
					var valid=false;
					valid=pass.check(this);   
					if(!valid){
						pass.isValidated=false;
						return false;		
					}
				}
			});
			return pass.isValidated;
		},
		check:function(el){
			var reg,el=$(el),
				required=el.attr("required"),
				pattern=el.attr("pattern");
			if(required){
				reg=new RegExp(pattern);
				if(reg.test($.trim(el.val()))){
					this.hideTip(el);
					return true;
				}
				else{
					this.showTip(el);
					return false;
				}
			}
			return true;
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
			var name=el.attr("name");
			if(name){
				this.el.find("p."+name).css("visibility","hidden");
			}
		},
		getFormData:function(){
			var data={};
			this.el.find("input").each(function(){
					var el=$(this),
						name=el.attr("name"),
						value=$.trim(el.val());
					if((!!name)&&name.length>0){
						data[name]=value;					
					}
			});
			return data;
		},
		changeTel:function(){
			this.step.html(CheckPwd.TEMPLATE.MODIFY);
		},
		checkEmail:function(){
			this.resetTime();
			this.isExist(false);
			this.step.html(CheckPwd.TEMPLATE.EMAIL);
			if(!!this.payEmail){
				this.render(this.payEmail);
			}
		},
		mobileCheck:function(){
			this.resetTime();
			this.step.html(CheckPwd.TEMPLATE.MOBILE);
			this.isExist(true);
		},
		resetTime:function(){
			this.seconds=60;
			clearInterval(this.Timer);
		},
		back:function(){
			this.el.remove();
			this.mask.remove();
			if(typeof this.opt.callback == "function"){
				this.opt.callback.call(this.opt.obj||this);
			}
		},
		focus:function(el){
			
			var el=$(el);
				el.removeClass("new_password");
		},
		
		blur:function(el){
			var el=$(el),
				value=el.val(),
				type=el.attr("type");
			if(type=="text"||type=="password"){
				if(value==el.attr("placeholder")||value.length==0){
					if(!el.hasClass("new_password")){
						el.addClass("new_password");
					}
				}
			}
		}
	};
	return CheckPwd;
});