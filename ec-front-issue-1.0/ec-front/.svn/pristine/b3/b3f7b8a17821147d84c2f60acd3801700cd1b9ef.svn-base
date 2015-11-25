seajs.use(["jQuery","ui","address","areaSelector"],function($,ui,address,area){
	
	var el=$(".right_box");
	var windowFrame = $("div[name='addressWindow']");
	var addressEl = [];
	var saveOrUpdate;
	
	function getUrl(param,value){
		var url = window.location.search.substring(1).split("&");
		var reUrl = "";
		if(url != ''){
			for (var i=0; i<url.length; i++) {
				var p = url[i].split("=");
				if(p[0] != param){
					reUrl = reUrl == "" ? reUrl : reUrl + "&";
					reUrl = reUrl + p[0] + "=" + p[1]; 
				}
			};
		}
		reUrl = (reUrl == "" ? reUrl : reUrl + "&");
		reUrl = reUrl + param + "=" + value;
		return reUrl;
	};
	
	var view = {
		init:function(){
			
			area.init(23,175,1507,1510);
			
			//修改默认地址
			el.find('a[bind="setDefaultAddress"]').live("click",function(){
				var id = $(this).closest("div").attr("addressId");
				address.defaultUsual(id);
			});
			$(address).bind(address.DEFAULT_USUAL_EVENT,function(e,data){view.defaultUsual(data);});
			
			//删除地址
			el.find('a[bind="deleteAddress"]').live("click",function(){
				var id = $(this).closest("div").attr("addressId");
				address.deleteAddress(this,id);
			});
			$(address).bind(address.DELETE_EVENT,function(e,data){view.deleteAddress(data);});
			
			//新增地址
			
			//新增弹窗
			el.find('a[bind="addAddress"]').live("click",function(){
				saveOrUpdate = "save";
				view.initWindowFrame(-1);
				
				addressEl.country.trigger("change");
				addressEl.province.trigger("change");
				addressEl.city.trigger("change");
				addressEl.district.trigger("change");
				addressEl.town.trigger("change");
			});
			//更新弹窗
			el.find('a[bind="updateAddress"]').live("click",function(){
				saveOrUpdate = "update";
				var id = $(this).closest("div[class*='address_box']").attr("addressId");
				view.initWindowFrame(id);
				
				address.getAddress(id,addressEl);
			});
			//关闭
			windowFrame.find('.close').live("click",function(){
				windowFrame.hide();
				view.light.close();
			});
			//新增 or 更新
			windowFrame.find('button[name="submit"]').live("click",function(){
				if(view.validate(addressEl)){
					var addressObj = [];
					addressObj.consignee = addressEl.consignee.val();
					addressObj.country = addressEl.country.val();
					addressObj.province = addressEl.province.val();
					addressObj.city = addressEl.city.val();
					addressObj.district = addressEl.district.val();
					addressObj.town = addressEl.town.val();
					addressObj.address = addressEl.address.val();
					addressObj.zipCode = addressEl.zipCode.val();
					addressObj.mobile = addressEl.mobile.val();
					addressObj.phone = addressEl.phone.val();
					addressObj.email = addressEl.email.val();
					addressObj.usual = addressEl.usual.attr("checked") == "checked";
					address.saveOrUpadteAddress(saveOrUpdate,windowFrame,addressObj);
				}
			});
			$(address).bind(address.ADD_OR_UPDATE_EVENT,function(e,data){view.saveOrUpdateAddress(data);});
		},
		initWindowFrame:function(id){
			windowFrame.find("input").val("");
			var info = windowFrame.find("span[name='info']").html("");
			info.html("");
			info.removeAttr("style");
			windowFrame.find("input").removeAttr("disabled");
			windowFrame.find("select").removeAttr("disabled");
			windowFrame.find("input[name='mobile']").parent().find("span[name='info']").html("用于发货或送货通知");
			windowFrame.find("input[name='phone']").parent().find("span[name='info']").html("用于送货通知");
			windowFrame.find("input[name='email']").parent().find("span[name='info']").html("用于接收订单执行状态提醒邮件");
			windowFrame.find("input[name='usual']").removeAttr("checked");
			
			windowFrame.show();
			view.light=ui.highlight({
					el:windowFrame
			});
			windowFrame = $("div[name='addressWindow']");
			addressEl.consignee = windowFrame.find("input[name='consignee']");
			addressEl.country = windowFrame.find("select[arealevel='country']");
			addressEl.province = windowFrame.find("select[arealevel='province']");
			addressEl.city = windowFrame.find("select[arealevel='city']");
			addressEl.district = windowFrame.find("select[arealevel='district']");
			addressEl.town = windowFrame.find("select[arealevel='town']");
			addressEl.address = windowFrame.find("input[name='address']");
			addressEl.zipCode = windowFrame.find("input[name='zipCode']");
			addressEl.mobile = windowFrame.find("input[name='mobile']");
			addressEl.phone = windowFrame.find("input[name='phone']");
			addressEl.email = windowFrame.find("input[name='email']");
			addressEl.usual = windowFrame.find("input[name='usual']");
			
			if(saveOrUpdate == "update" && id != -1){
				windowFrame.attr("addressId",id)
			}
		},
		defaultUsual:function(data){
			if(data.result != 1){
				return;
			}
			var old = el.find("div[class*='yellow_bg']");
			old.removeClass("yellow_bg");
			old.find("span[name='defaultAddress']").replaceWith('<a class="link1" href="javascript:;" bind="setDefaultAddress">设置为默认地址</a>');
			
			var newAdd = el.find("div[addressId='"+ data.id +"']");
			newAdd.addClass("yellow_bg");
			newAdd.find('a[bind="setDefaultAddress"]').replaceWith('<span name="defaultAddress">默认地址</span>');
		},
		deleteAddress:function(data){
			if(data.result != 1){
				return;
			}
			var ell = el.find('div[addressId="'+ data.id +'"]');
			if(!ell.hasClass("bot_dash")){
				ell.prev().removeClass("bot_dash");
			}
			ell.remove();
		},
		saveOrUpdateAddress:function(data){
			if(data.result == 1){
				window.location.reload();
			}
		},
		validate:function(addressEl){
			var ret = true;
			var consignee = addressEl.consignee,
				country = addressEl.country,
				province = addressEl.province,
				city = addressEl.city,
				district = addressEl.district,
				town = addressEl.town,
				address = addressEl.address,
				zipCode = addressEl.zipCode,
				mobile = addressEl.mobile,
				phone = addressEl.phone,
				email = addressEl.email,
				usual = addressEl.usual;
			if(!zipCode.val()){
				var ele = zipCode.closest("p").find("span[name='info']");
				ele.html("请输入邮编");
				ele.css("color","red");
				ret = false;
			}
			else if(!mobile.val() && !phone.val()){
				var ele = mobile.closest("p").find("span[name='info']");
				ele.html("请输入手机号码或者固定电话");
				ele.css("color","red");
				ret = false;
				var ele = zipCode.closest("p").find("span[name='info']");
				ele.html("");
			}
			else{
				var ele = mobile.closest("p").find("span[name='info']");
				ele.html("");
				if(country.val()!=23){
					ret=true;
				}else{
					if (mobile.val()) {
						if(!view.errorMsg(mobile, /^1[1-9]{1}[0-9]{9}$/, "手机号码由11位数字组成")){
							ret = false;
						};
					}
					if (phone.val()) {
						if(!view.errorMsg(phone, /^((\d{3,4})|(\d{3,4}\-))?((\d){7,8})+((\d{2,4})|(\-\d{2,4}))?$/, "由数字及\"-\"组成，格式:区号-固话号码-分机号")){
							ret = false;
						};
					}
					if(!view.errorMsg(zipCode,/^[1-9]{1}[0-9]{5}$/,"邮编由6位数字组成")){
						ret = false;
					}
				}
			}
			if((country.val() == 23 && (province.val() == -1  || city.val() == -1 || district.val() == -1 || town.val() == -1 ))){
					var ele = country.closest("p").find("span[name='info']");
					ele.html("请选择所在地区");
					ele.css("color","red");
					ret = false;
			}
			else {
				var ele = country.closest("p").find("span[name='info']");
				ele.html("");
				ele.removeAttr("style");
			}
			if(!view.errorMsg(consignee,/^[\s\S]{2,30}$/,"收货人姓名,由2-30个字符组成,如：张三")){
				ret = false;	
			}
			if(!view.errorMsg(address,/^[\s\S]{4,200}$/,"街道地址由4-200字符组成")){
				ret = false;	
			}
			if(!view.errorMsg(email,/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,"请按Email格式输入,6-100个字符")){
				ret = false;	
			}
			return ret;
		},
		errorMsg:function(el,reg,msg){
			var ele = el.closest("p").find("span[name='info']");
			if (!reg.test(el.val())) {
				ele.html(msg);
				ele.css("color","red");
				return false;
			}
			else{
				ele.html("");
				ele.removeAttr("style");
				return true;
			}
		}
	};
	view.init();
});