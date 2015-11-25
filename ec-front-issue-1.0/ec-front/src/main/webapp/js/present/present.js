seajs.use(["jQuery","present","ui"],function($,present,ui){
	var view={
		template:{
			normal:'<p class="cou_tips">激活是对礼券的验证过程，激活后的礼券才能使用</p><p class="txt_center input_cou">激活码：<input name="code" type="text" value=""/></p><div style="text-align:center;margin-bottom:40px;"><button class="pas_save" name="active">激活</button></div>',
			success:'<p class="txt_center input_cou"><b class="fb green f14">恭喜您，激活成功！</b>此礼券将发送到您的账户<br>请稍后进入<a class="link2" href="javascript:;" bind="myPresent">我的礼券</a>查询</p><div style="text-align:center;margin-bottom:40px;"><button class="pas_save" name="confirm">确定</button></div>',
			error:'<p class="txt_center input_cou"><b class="fb red f14">sorry，激活失败！</b><br/><br/>您输入的激活码不对，或者已经失效<br>您可以<a class="link2" href="javascript:;" bind="activeAgain">重新输入</a><br/><br/><button class="pas_cancel" name="cancel">放弃</button></p>'
		},
		init:function(){
			$(present).bind(present.ACTIVE_SUCCESS_EVENT,function(e,data){
				view.showSuccess(data);
			});
			$(present).bind(present.ACTIVE_ERROR_EVENT,function(e,msg){
				view.showFailed(msg);
			});
			this.light=null;
			this.activeLink=$("#active_link");
			this.formEl=$("div.activate_box");
			this.title=this.formEl.find("h3");
			this.closeLink=this.title.find("a.close");
			this.input=this.formEl.find("input[name='code']");
			this.activeButton=this.formEl.find("button[name='active']");
			this.confirmButton=this.formEl.find("button[name='confirm']");
			this.cancelButton=this.formEl.find("button[name='cancel']");
			this.content=this.formEl.find("div.coupon_box");
			this.activeAgainLink=this.formEl.find("a[bind='activeAgain']");
			this.select = $("select[bind='present_type']");
			this.myPresentLink=this.formEl.find("a[bind='myPresent']");
			this.bind();
		},
		bind:function(){
			this.activeLink.click(view.show);
			this.closeLink.click(view.close);
			this.activeButton.live("click",view.submit);
			this.cancelButton.live("click",view.cancel);
			this.confirmButton.live("click",view.confirm);
			this.select.live("change",view.change);
			this.activeAgainLink.live("click",view.activeAgain);
			this.myPresentLink.live("click",view.myPresent);
		},
		myPresent:function(){
			view.loadPresent();
		},
		loadPresent:function(){
			/*var url = window.location.pathname;
			window.open(url,"_self");*/
			window.location.reload();
		},
		activeAgain:function(){
			view.title.find("span").html("激活新礼券");
			view.content.html(view.template.normal);
			$(view.input).val("");//将input数据清空
		},
		change:function(){
			var url = window.location.pathname;
			url = url + "?presentType="+$(view.select).val();
			window.open(url,"_self");
		},
		confirm:function(){
			view.loadPresent();
		},
		cancel:function(){
			view.close();
		},
		show:function(){
			if(!!view.light){
				view.light.show();
				view.title.find("span").html("激活新礼券");
				//view.content.find("button[name='active']").click(view.submit);
				view.input.focus();
				return;
			}
			view.light=ui.highlight({
				el:view.formEl.show()
			});
			view.input.focus();
		},
		submit:function(){
			view.input=view.formEl.find("input[name='code']");
			var code =view.input.val();
			if(code == null || code.length == 0){
				alert("激活码不能为空");
				view.input.focus();
				return;
			}
			present.active(code);
		},
		close:function(){
			/*if(!!view.light){
				view.light.close();
				view.content.html(view.template.normal);
				$(view.input).val("");//将input数据清空
			}*/
			window.location.reload();
		},
		showSuccess:function(data){
			view.title.find("span").html("激活成功");
			view.content.html(view.template.success);
		},
		showFailed:function(){
			view.title.find("span").html("激活失败");
			view.content.html(view.template.error);
		}
	};
	view.init();
});