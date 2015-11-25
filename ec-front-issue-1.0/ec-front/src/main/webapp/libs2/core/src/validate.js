define(function(require){
	var $=require("jQuery"),
		conf=require("config"),
        validate = {
        message: {
            isEmpty: "* 不能为空",
            maxLength: "* {key}最小不能为{value}",
            isNaN: "* {key}不是数字"
        },
        isEmpty: function(el){
            return el == null || el.length == 0;
        },
        maxLength: function(el, length){ 
            return trim(el).length > length;
        },
        compareTo: function(a, b){
            return a > b ? true : false;
        },
		trim :function(str){
			return str.replace(/(^\s*)|(\s*$)/g, "");
		},      
		validateBank :function(){
			var bank = $("select[bind='bank']").val();
         	var bankProvince = $("select[bind='bankProvince']").val();
       		var bankCity = $("select[bind='bankCity']").val();
       	 	var bankAccountName = validate.trim($("#bankAccountName").val());
        	var bankAccountNameMessage = $("#bankAccountNameMessage");
        	var bankAccountCode = validate.trim($("#bankAccountCode").val());
        	var bankAccountCodeMessage = $("#bankAccountCodeMessage");
        	var bankAccountContactPhone = validate.trim($("#bankAccountContactPhone").val());
        	var bankAccountContactPhoneMessage = $("#bankAccountContactPhoneMessage");
        	if (bank == -1) {
           		$("#bankError").html("  * 请选择银行").show();
            	return false;
        	}else {
            	$("#bankError").hide();
        	}
        	if (bankProvince == -1) {
            	$("#areaError").html("  * 请选择开户支行省").show();
           		return false;
        	}else {
            	$("#areaError").hide();
        	}
        	if (bankCity == -1) {
            	$("#areaError").html("  * 请选择开户支行市").show();
            	return false;
        	}else {
            	$("#areaError").hide();
        	}
        	if (validate.isEmpty(bankAccountName)) {
           		bankAccountNameMessage.hide();
            	$("#bankAccountNameError").html("  * 开户名不能为空").show();
            	return false;
        	}else {
            	$("#bankAccountNameError").hide();
            	bankAccountNameMessage.html("如：张信哲").show();
        	}
        	if (validate.isEmpty(bankAccountCode)) {
            	bankAccountCodeMessage.hide();
            	$("#bankAccountCodeError").html("  * 开户账号不能为空").show();
            	return false;
        	}else {
           		$("#bankAccountCodeError").hide();
            	bankAccountCodeMessage.html("如：6625441033825537517").show();
        	}
        	if (validate.isEmpty(bankAccountContactPhone)) {
            	bankAccountContactPhoneMessage.hide();
            	$("#bankAccountContactPhoneError").html("  * 联系方式不能为空").show();
            	return false;
        	}else {
           		$("#bankAccountContactPhoneError").hide();
           		bankAccountContactPhoneMessage.html("如：010-88008800,13908181234").show();
        	} 
        	return true;
		},
        validatePost: function(){
            var postAccountName = validate.trim($("#postAccountName").val());
            var postAddress = validate.trim($("#postAddress").val());
            var postAddressMessage = $("#postAddressMessage");
            var postCode = $("#postCode").val();
            var postContactPhone = $("#postContactPhone").val();
            var postContactPhoneMessage = $("#postContactPhoneMessage");
            var postCity = $("select[bind='postCity']").val();
            if (validate.isEmpty(postAccountName)) {
                $("#postAccountNameError").html("  * 收款人不能为空").show();
                return false;
            }
            else {
                $("#postAccountNameError").hide();
            }
            if (postCity == -1) {
                $("#postAreaError").html("  * 请选择区域到市").show();
                return false;
            }
            else {
                $("#postAreaError").hide();
            }
            if (validate.isEmpty(postAddress)) {
                postAddressMessage.hide();
                $("#postAddressError").html("  * 地址不能为空").show();
                return false;
            }
            else {
                $("#postAddressError").hide();
                postAddressMessage.html("如：西藏北路45号东方大厦1564室").show();
            }
            if (validate.isEmpty(postCode)) {
                $("#postCodeError").html("  * 邮编不能为空");
                return false;
            }
            else {
                $("postCodeError").hide();
            }
            
            if (validate.isEmpty(postContactPhone)) {
                postContactPhoneMessage.hide();
                $("#postContactPhoneError").html("  * 联系方式不能为空").show();
                return false;
            }
            else {
                postContactPhoneMessage.html("如：010-88008800,13908181234").show();
                $("#postContactPhoneError").hide();
            }
            return true;
        },
		validateAlipay:function(){
			var alipay = validate.trim($("#alipayAccount").val());
        	var alipayError = $("#alipayError");
        	if (validate.isEmpty(alipay)) {
          	  	alipayError.html("* 支付宝账号不能为空");
            	return false;
       		 };
       		 var alipaynameError = $("#alipaynameError");
       		 if (validate.isEmpty(validate.trim($("#alipaynameAccount").val()))) {
       			 alipaynameError.html("* 您的支付宝认证姓名不能为空");
       			 return false;
       		 };
       		alipaynameError.html("");
        	alipayError.html("");
        	return true;
		},
		validateTenpay:function(){
			var tenpay = validate.trim($("#tenpay").val());
        	var tenpayError = $("#tenpayError");
        	if (validate.isEmpty(tenpay)) {
            	tenpayError.html("* 财付通账号不能为空");
            	return false;
       		 };
       		tenpayError.html("");
        	return true;
		}	
    };
	return validate; 
});
