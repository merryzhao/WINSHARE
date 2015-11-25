seajs.use(["jQuery","points","ui","config"],function($,points,ui,config){
	var view={
		template:{
			normal:'',
			success:'<p class="txt_center input_cou"><b class="fb green f14">恭喜您，兑换成功！</b><br/><br/>此礼品将发送到你的账户<br/>稍后进入<a class="link2" href="javascript:;" bind="present">我的礼券</a>查询<br/><br/><button class="pas_save" name="success_confirm" >确定</button></p>',
			error_header:'<p class="txt_center input_cou"><b class="fb green f14">sorry，兑换失败！</b><br/><br/>',
			error_bottom:'<br/><br/><button class="pas_save" name="fail_confirm" >确定</button></p>'
		},
		init:function(){
			$(points).bind(points.EXCHANGE_SUCCESS_EVENT,function(e,data){
				view.showSuccess(data);
			});
			$(points).bind(points.EXCHANGE_ERROR_EVENT,function(e,msg){
				view.showFailed(msg);
			});
			this.light=null;
			this.select = $("select[bind='record_type']");
			this.orderLink = $("a[bind='order']");
			this.ul = $("ul.infor_tab");
			this.record = this.ul.find("li[bind='record']");
			this.exchange = this.ul.find("li[bind='exchange']");
			this.imgLink = $("img[bind='img']");
			this.imgDesLink = $("a[bind='link']");
			this.instanceExchangeLink = $("a[bind='exchange']");
			this.exchangeEl = $("div.activate_box");
			if(!!this.exchangeEl){
				this.title = this.exchangeEl.find("h3");
				this.exchangeButton = this.exchangeEl.find("button[name='exchange']");
				this.cancelButton = this.exchangeEl.find("button[name='cancel']");
				this.closeLink=this.title.find("a.close");
				this.content=this.exchangeEl.find("div.coupon_box");
				this.template.normal = this.content.html();//由于normal数据不固定,所以在初始化时设置为当前页面内容
				this.present = this.exchangeEl.find("a[bind='present']");
				this.successConfirm = this.exchangeEl.find("button[name='success_confirm']");
				this.failConfirm = this.exchangeEl.find("button[name='fail_confirm']");
			}
			this.bind();
		},
		bind:function(){
			if(!!this.select)
				this.select.live("change",view.changeAction);
			if(!!this.orderLink)
				this.orderLink.live("click",view.orderAction);
			if(!!this.record)
				this.record.live("click",view.recordAction);
			if(!!this.exchange)
				this.exchange.live("click",view.exchangeAction);
			if(!!this.imgLink)
				this.imgLink.click(function(){
					view.imgAction(this.name);
				});
			if(!!this.imgDesLink)
				this.imgDesLink.click(function(){
					view.imgDesAction(this.id);
				});
			if(!!this.instanceExchangeLink)
				this.instanceExchangeLink.live("click",view.show);
			if(!!this.exchangeEl){
				this.closeLink.live("click",view.closeAction);
				this.cancelButton.live("click",view.cancelAction);
				this.exchangeButton.live("click",view.excuteExchangeAction);
				this.present.live("click",view.presentAction);
				this.successConfirm.live("click",view.successConfirmAction);
				this.failConfirm.live("click",view.failConfirmAction);
			}
		},
		presentAction:function(){
			window.open(config.portalServer + "/customer/present","_self");
		},
		successConfirmAction:function(){
			view.presentAction();
		},
		failConfirmAction:function(){
			view.closeAction();
		},
		showSuccess:function(data){//绑定成功处理逻辑
			view.title.find("span").html("兑换成功");
			view.content.html(view.template.success);
		},
		showFailed:function(msg){//绑定失败处理逻辑
			view.title.find("span").html("兑换失败");
			//view.message.html(msg);
			view.content.html(view.template.error_header+msg+view.template.error_bottom);
		},
		excuteExchangeAction:function(){
			points.exchange(this.id);
		},
		cancelAction:function(){
			view.closeAction();
		},
		closeAction:function(){
			if(!!view.light){
				view.light.close();
				view.content.html(view.template.normal);
			}
		},
		show:function(){
			if(!!view.light){
				view.light.show();
				view.title.find("span").html("兑换确认");
				return;
			}
			view.light=ui.highlight({
				el:view.exchangeEl.show()
			});
		},
		imgAction:function(id){
			window.open(config.portalServer+"/customer/points/presentExchange/"+id,"_blank");
		},
		imgDesAction:function(id){
			view.imgAction(id);
		},
		exchangeAction:function(){//页面定位积分兑换页面
			var url = config.portalServer + "/customer/points/pointsExchange";
			window.open(url,"_self");
		},
		recordAction:function(){//页面定位积分记录页面
			var url = config.portalServer + "/customer/points";
			window.open(url,"_self");
		},
		orderAction:function(){//订单页面
			window.open(config.portalServer+"/customer/order/"+$(this).html(),"_blank");
		},
		changeAction:function(){//查询时间选择
			var url = window.location.pathname;
			url = url + "?type="+$(view.select).val();
			window.open(url,"_self");
		}
	};
	view.init();
});