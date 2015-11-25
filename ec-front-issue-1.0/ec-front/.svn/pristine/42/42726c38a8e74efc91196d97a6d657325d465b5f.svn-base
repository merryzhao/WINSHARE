seajs.use(["jQuery", "areaSelector","ui"], function($, areaSelector,ui){
	var constant1 = {
        country: 'select[areaLevel="country1"]',
        province: 'select[areaLevel="province1"]',
        city: 'select[areaLevel="city1"]',
        district: 'select[areaLevel="district1"]',
        chooseProvince: '<option value="-1">请选择省份</option>',
        chooseCity: '<option value="-1">请选择城市</option>',
        chooseDistrict: '<option value="-1">请选择区县</option>',
        option: '<option></option>',
        china: 23
    }; 
	 var constant2 = {
        country: 'select[areaLevel="country2"]',
        province: 'select[areaLevel="province2"]',
        city: 'select[areaLevel="city2"]',
        district: 'select[areaLevel="district2"]',
        chooseProvince: '<option value="-1">请选择省份</option>',
        chooseCity: '<option value="-1">请选择城市</option>',
        chooseDistrict: '<option value="-1">请选择区县</option>',
        option: '<option></option>',
        china: 23
    };
	 var detail ={
		init:function(){
			this.light=null;
			this.detailBankEl=$("div[bind='detailBank']");
			this.detailAlipayEl=$("div[bind='detailAlipay']");
			this.detailPostEl=$("div[bind='detailPost']");
			this.detailEl = $(".refund_box");	
			this.detailPostEl = $(".refund_post_box");		
			this.title = this.detailEl.find("h3");
			this.titlePost = this.detailPostEl.find("h3");
			this.instanceDetailLink = $("#cashdetail");
			if (!!this.detailEl) {
				this.closeLink=this.title.find("a.close");
			}
			if (!!this.detailPostEl) {
				this.closeLinkPost=this.detailPostEl.find("a.close");
			}
			if(!!this.instanceDetailLink)
				this.instanceDetailLink.live("click",detail.show);			
			this.closeLink.live("click",detail.closeAction);
			this.closeLinkPost.live("click",detail.closeAction);
		},
		reloadBank:function(p){
			$("#detailBankName").val(p[1]);
			$("#detailBankAccountName").val(p[4]);
			$("#detailBankAccountCode").val(p[5]);
			$("#detailBankAccountContactPhone").val(p[6]);
			areaSelector.init(23,p[2],p[3],null,constant1);
		},
		reloadAlipay:function(p){
			$("#detailAlipay").val(p[1]);
			$("#detailAlipayname").val(p[2]);
		},
		reloadPost:function(p){		
			if(p[5]==null ||p[5].length==0){
				p[5]=null;
			}			
			$("#detailPostAccountName").val(p[1]);
			$("#detailPostAddress").val(p[6]);
			$("#detailPostCode").val(p[7]);
			$("#detailPostContactPhone").val(p[8]);
			if(p[5]==null){
			 $("#detailPostDistrict").hide();	
			}
			
			areaSelector.init(23,p[3],p[4],p[5],constant2);
		},
		closeAction:function(){
			if(!!detail.light){
				detail.light.close();
			}
		},
		show:function(){
			 var param  = $(this).attr("param");				
			 var p = param.split(",");
				if (p[0] == 43003) {
					detail.reloadBank(p);
					detail.light=ui.highlight({
						el:detail.detailBankEl.show()
					});
				}else if(p[0] == 43001){
					detail.reloadAlipay(p);
					detail.light=ui.highlight({
						el:detail.detailAlipayEl.show()
					});
				}else if(p[0]==43004){
					detail.reloadPost(p);
					detail.light=ui.highlight({
						el:detail.detailPostEl.show()
					});
				}	
		}

	}; 
	detail.init();
	
});
