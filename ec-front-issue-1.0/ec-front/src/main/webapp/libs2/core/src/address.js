define(function(require){
	var $ = require("jQuery"), 
		ui = require("ui"), 
		config = require("config"), 
		base = config.portalServer + "/customer/address", 
		address = {
			ADD_OR_UPDATE_EVENT: "add_or_update_event",
			DELETE_EVENT:"delete_event",
			DEFAULT_USUAL_EVENT:"default_usual_event",
			
			getAddress:function(id,addressEl){
				$.ajax({
				   type: "GET",
				   url: base + "/" + id,
				   data: "format=json",
				   success: function(data){
				   	 var address = data.address;
				   	 addressEl.consignee.val(address.consignee);
					 addressEl.country.val(address.country.id);
					 addressEl.country.trigger("change");
					 if(address.country.id == 23){
					 	addressEl.province.val(address.province.id);
						addressEl.province.trigger("change");
						addressEl.city.val(address.city.id);
						addressEl.city.trigger("change");
						addressEl.district.val(address.district.id);
						addressEl.district.trigger("change");
						addressEl.town.val(address.town.id);
						addressEl.town.trigger("change");
					 }
					 addressEl.address.val(address.address);
					 addressEl.zipCode.val(address.zipCode);
					 addressEl.mobile.val(address.mobile);
					 addressEl.phone.val(address.phone);
					 addressEl.email.val(address.email);
					 if(address.usual){
					 	addressEl.usual.attr("checked","checked");
					 }
				   }
				}); 
			},
			defaultUsual:function(id){
				$.ajax({
				   type: "POST",
				   url: base + "/usual",
				   data: "id=" + id + "&format=json&_method=put",
				   success: function(data){
				   	 data.id = id;
				     $(address).trigger(address.DEFAULT_USUAL_EVENT,[data]);
				   }
				}); 
			},
			deleteAddress:function(el , id){
				ui.confirm({
					message:"您确定要移除此条数据？",
					dock:el,
					callback:function(){
						$.ajax({
						   type: "POST",
						   url: base + "/" + id,
						   data: "format=json&_method=delete",
						   success: function(data){
							   data.id = id;
							   $(address).trigger(address.DELETE_EVENT,[data]);
							}
						});
					}
				});
			},
			saveOrUpadteAddress:function(saveOrUpdate,windowFrame,addressObj){
				windowFrame.find("input").attr("disabled","disabled");
				windowFrame.find("select").attr("disabled","disabled");
				
				var param = "";
				param += "consignee=" + addressObj.consignee;
				param += "&country.id=" + addressObj.country;
				param += "&province.id=" + addressObj.province;
				param += "&city.id=" + addressObj.city;
				param += "&district.id=" + addressObj.district;
				param += "&town.id=" + addressObj.town;
				param += "&address=" + addressObj.address;
				param += "&zipCode=" + addressObj.zipCode;
				param += "&mobile=" + addressObj.mobile;
				param += "&phone=" + addressObj.phone;
				param += "&email=" + addressObj.email;
				if(!addressObj.usual){
					addressObj.usual = 0;
				}
				else{
					addressObj.usual = 1;
				}
				param += "&usual=" + addressObj.usual;
				
				if(saveOrUpdate == "save"){
					$.ajax({
					   type: "POST",
					   url: base,
					   data: param + "&format=json",
					   success: function(data){
						   $(address).trigger(address.ADD_OR_UPDATE_EVENT,[data]);
						}
					});
				}
				else if(saveOrUpdate == "update"){
					var id = windowFrame.attr("addressId");
					param += "&id=" + id;
					
					$.ajax({
					   type: "POST",
					   url: base,
					   data: param + "&format=json&_method=put",
					   success: function(data){
						   $(address).trigger(address.ADD_OR_UPDATE_EVENT,[data]);
						}
					});
				}
				
			}
		};
	return address;
});
