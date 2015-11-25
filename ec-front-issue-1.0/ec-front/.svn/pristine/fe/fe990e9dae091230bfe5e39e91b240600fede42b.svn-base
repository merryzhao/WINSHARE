seajs.use(["jQuery", "areaSelector","ui","validate"], function($, areaSelector,ui,validate){
	 var constant = {
        country: 'select[areaLevel="country3"]',
        province: 'select[areaLevel="province3"]',
        city: 'select[areaLevel="city3"]',
        district: 'select[areaLevel="district3"]',
        chooseProvince: '<option value="-1">请选择省份</option>',
        chooseCity: '<option value="-1">请选择城市</option>',
        chooseDistrict: '<option value="-1">请选择区县</option>',
        option: '<option></option>',
        china: 23
    };
	 var modify = {
	 	init: function(){
			this.light = null;
			this.modifyAlipayEl=$("div[bind='modifyAlipay']");
			this.modifyBankEl=$("div[bind='modifyBank']");
			this.modifyPostEl=$("div[bind='modifyPost']");
			this.modifyEl = $(".m_box");
			this.modifyPostEl = $(".m_post_box");	
			this.title = this.modifyEl.find("h3");
			this.postTitle = this.modifyPostEl.find("h3");
			this.instanceModifyLink = $("#cashModify");
			if (!!this.modifyEl) {
				this.closeLink=this.title.find("a.close");
			}
			if (!!this.modifyPostEl) {
				this.closePostLink = this.postTitle.find("a.close");
			}
			this.cancelLink=$("input[bind='cancelModify']");
			this.modifyLink=$("input[bind='confrimModify']");
			this.cancelLink.live("click",modify.closeAction)
			if(!!this.instanceModifyLink)
				this.instanceModifyLink.live("click",modify.show);			
			this.closeLink.live("click",modify.closeAction);
			this.closePostLink.live("click",modify.closeAction);
			this.modifyLink.live("click",modify.submit);
		},
		reloadAlipay:function(p){		
			$("#alipayAccount").val(p[2]);
			$("#alipaynameAccount").val(p[3]);
		},
		reloadBank:function(p){
			$("select[bind='bank'] option[value='"+p[2]+"']").attr("selected",true);
			$("#bankAccountName").val(p[5]);
			$("#bankAccountCode").val(p[6]);
			$("#bankAccountContactPhone").val(p[7]);
			areaSelector.init(23,p[3],p[4]);
		},
		reloadPost:function(p){		
			if(p[6]==null ||p[6].length==0){
				p[6]=null;
			}			
			$("#postAccountName").val(p[2]);
			$("#postAddress").val(p[7]);
			$("#postCode").val(p[8]);
			$("#postContactPhone").val(p[9]);
			if(p[6]==null){
			 $("#postDistrict").hide();	
			}
			areaSelector.init(p[3],p[4],p[5],p[6],constant);
		},
		closeAction:function(){
			if(!!modify.light){
				modify.light.close();
			}
		},
		show:function(){
			 var param  = $(this).attr("param");				
			 var p = param.split(",");
				if (p[0] == 43003) {
					modify.reloadBank(p);
					modify.light=ui.highlight({
						el:modify.modifyBankEl.show()
					});
					var alipayForm =$("form[bind='bankForm']");
					alipayForm.attr("action","/customer/advanceaccount/refund/"+p[1]+"/modify");
				}else if(p[0] == 43001){
					modify.reloadAlipay(p);
					modify.light=ui.highlight({
						el:modify.modifyAlipayEl.show()
					});
					var alipayForm =$("form[bind='alipayForm']");
					alipayForm.attr("action","/customer/advanceaccount/refund/"+p[1]+"/modify");
				}else if(p[0]==43004){
					modify.reloadPost(p);
					modify.light=ui.highlight({
						el:modify.modifyPostEl.show()
					});
					var postForm =$("form[bind='postForm']");
					postForm.attr("action","/customer/advanceaccount/refund/"+p[1]+"/modify");
				}	
		},
		submit:function(){
			var param  = $(this).attr("param");	
			if(param==43001){
				var alipayForm = $("form[bind='alipayForm']");
					if (validate.validateAlipay()) {
						alipayForm.submit();
						modify.closeAction;
					}
			}
			if(param==43003){
				var bankForm = $("form[bind='bankForm']");
					if (validate.validateBank()) {
						bankForm.submit();
						modify.closeAction;
					}
			}
			if(param==43004){
				var postForm = $("form[bind='postForm']");
					if (validate.validatePost()) {
						postForm.submit();
						modify.closeAction;
					}
			}
			
		}
	 };
	 modify.init();
	
});
