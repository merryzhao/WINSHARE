seajs.use(["jQuery","presentcard","ui","config"],function($,presentcard,ui,config){
	var view={
		template:{
			normal:'<p class="cou_tips">绑定是对礼品卡的验证过程，绑定后的礼品卡才能使用；<br>一旦绑定，则只能在此账号使用</p><div class="txt_center input_cou"><div class="text_space"><span>礼品卡卡号：</span><input name="code" type="text" value="" size="25"/></div><div class="text_space"><span>密码：</span><input name="password" type="password" value="" size="25"></div></div><div style="text-align:center;margin-bottom:20px;"><button class="pas_save" name="bind">绑定</button></div>',
			success:'<p class="txt_center input_cou"><b class="fb green f14">恭喜您，激活成功！</b><br/><br/>此礼品卡已经绑定到您的账户<br>请稍后进入<a class="link2" href="javascript:;" bind="myPresentcard">我的礼品卡</a>查询</p><div style="text-align:center;margin-bottom:20px;"><button class="pas_save" name="confirm">确定</button></div>',
			error:'<p class="txt_center input_cou"><b class="fb red f14">sorry，绑定失败！</b><br/><br/>您输入的卡号或密码不对，或者已经失效<br/>您可以<a class="link2" href="javascript:;" bind="bindAgain">重新输入</a><br/><br/><button class="pas_cancel" name="cancel">放弃</button></p>'
		},
		init:function(){
			$(presentcard).bind(presentcard.BIND_SUCCESS_EVENT,function(e,data){
				view.showSuccess(data);
			});
			$(presentcard).bind(presentcard.BIND_ERROR_EVENT,function(e,msg){
				view.showFailed(msg);
			});
			this.light=null;
			this.ul = $("ul.infor_tab");
			this.card = this.ul.find("li[bind='card']");
			this.used = this.ul.find("li[bind='used']");
			this.bindLink = this.ul.find("a[bind='bindCard']");
			this.cardEL = $("div.activate_box");
			this.title = this.cardEL.find("h3");
			this.closeLink=this.title.find("a.close");
			this.content=this.cardEL.find("div.coupon_box");
			this.code = this.cardEL.find("input[name='code']");
			this.password = this.cardEL.find("input[name='password']");
			this.bindButton = this.cardEL.find("button[name='bind']");
			this.cancelButton = this.cardEL.find("button[name='cancel']");
			this.bindAgainLink = this.cardEL.find("a[bind='bindAgain']");
			this.myPresentcardLink = this.cardEL.find("a[bind='myPresentcard']");
			this.confirmButton = this.cardEL.find("button[name='confirm']");
			this.orderLink = $("a[bind='order']");
			this.bind();
		},
		bind:function(){
			this.card.live("click",view.cardLocation);
			this.used.live("click",view.usedLocation);
			this.bindLink.live("click",view.popAction);
			this.closeLink.live("click",view.closeAction);
			this.bindButton.live("click",view.bindAction);
			this.bindAgainLink.live("click",view.bindAgainAction);
			this.cancelButton.live("click",view.cancelAction);
			this.myPresentcardLink.live("click",view.cardLocation);
			this.confirmButton.live("click",view.confirmAction);
			this.orderLink.live("click",view.orderAction);
		},
		orderAction:function(){//订单页面
			window.open(config.portalServer+"/customer/order/"+$(this).html(),"_blank");
		},
		confirmAction:function(){//绑定成功确定
			view.cardLocation();
		},
		bindAgainAction:function(){//重新绑定礼品卡
			view.title.find("span").html("绑定一张礼品卡");
			view.content.html(view.template.normal);
			$(view.code).val("");//将input数据清空
			$(view.password).val("");//将input数据清空
		},
		cancelAction:function(){//放弃绑定礼品卡
			view.closeAction();
		},
		showSuccess:function(data){//绑定成功处理逻辑
			view.title.find("span").html("绑定成功");
			view.content.html(view.template.success);
		},
		showFailed:function(msg){//绑定失败处理逻辑
			view.title.find("span").html("绑定失败");
			view.content.html(view.template.error);
		},
		bindAction:function(){//绑定礼品卡
			view.code=view.cardEL.find("input[name='code']");
			view.password=view.cardEL.find("input[name='password']");
			var code =view.code.val();
			var password =view.password.val();
			if(code == null || code.length == 0){
				alert("礼品卡号不能为空");
				return;
			}
			if(password == null || password.length == 0){
				alert("密码不能为空");
				return;
			}
			presentcard.bind(code,password);
		},
		closeAction:function(){//关闭弹出窗口
			/*if(!!view.light){
				view.light.close();
				view.content.html(view.template.normal);
				$(view.code).val("");//将input数据清空
				$(view.password).val("");//将input数据清空
			}*/
			window.location.reload();
		},
		cardLocation:function(){//页面定位到礼品卡页面
			var url = config.portalServer + "/customer/presentcard";
			window.open(url,"_self");
		},
		usedLocation:function(){//页面定位到使用明细
			var url = config.portalServer + "/customer/presentcard/usedList";
			window.open(url,"_self");
		},
		popAction:function(){//弹出绑定礼品卡的页面片段
			if(!!view.light){
				view.light.show();
				view.title.find("span").html("绑定一张礼品卡");
				
				return;
			}
			view.light=ui.highlight({
				el:view.cardEL.show()
			});
		}
	};
	view.init();
});