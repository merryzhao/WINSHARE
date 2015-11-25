seajs.use(["jQuery","areaSelector","validate"], function($,areaSelector,validate){
	var  bank = $("input[bind='bankSubmit']"),
		 alipay = $("input[bind='alipaySubmit']"),
		 tenpay = $("input[bind='tenpaySubmit']"),
		 post = $("input[bind='postSubmit']"),
		 refundMoney = $("#refundMoney").val(),
		 canRefundMoney = $("#canRefundMoney").html();
   var constant = {
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
	var bankArea ={
		bankCity : $("#bankCity").val(),
		bankProvince:$("#bankProvince").val()
	};
	var postArea ={
		postDistrict:$("#postDistrict").val(),
		postCity : $("#postCity").val(),
		postProvince:$("#postProvince").val()
	}
	bankCity = bankArea.bankCity==null||bankArea.bankCity.length==0?null:bankArea.bankCity;
	bankProvince = bankArea.bankProvince==null||bankArea.bankProvince.length==0?null:bankArea.bankProvince;
	postCountry = postArea.postCountry==null||postArea.postCountry.length==0?23:postArea.postCountry;
    postProvince = postArea.postProvince==null||postArea.postProvince.length==0?null:postArea.postProvince;
	postCity = postArea.postCity==null||postArea.postCity.length==0?null:postArea.postCity;
	postDistrict = postArea.postDistrict==null||postArea.postDistrict.length==0?null:postArea.postDistrict;
	areaSelector.init(23,bankProvince,bankCity);
    areaSelector.init(postCountry, postProvince, postCity, postDistrict, constant);   
    var tab = $("p[bind='banktal']"), tabArray = ["bank1", "bank2", "bank3", "bank4"]; 
	var refund = {
		init :function(){
			bank.bind("click",refund.bankSubmit);	
			alipay.bind("click",refund.alipaySubmit);
			tenpay.bind("click",refund.tenpaySubmit);
			post.bind("click",refund.postSubmit);
			var defaultRank = $("input[target='bank1']");
            defaultRank.attr("checked", true);
            bank.show(defaultRank);
            tab.find("input").bind("click", function(){
                $.each(tabArray, function(){
                    $("." + this).hide();
                });
                refund.show(this);
            });
		},
		show: function(el){
            var name = $(el).attr("target");
            $('.' + name).show();
        },
		 validateMoney: function(money){
            if (validate.isEmpty(validate.trim(money))) {
                alert("请填入退款金额");
                return false;
            }
            var priceReg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
            if (!priceReg.test(money)) {
                alert("退款金额必须为正整数并退款金额最多保留两位");
                return false;
            }
            if (isNaN(parseInt(money))) {
                alert("输入退款金额必须为数字");
                return false;
            }
            if (parseFloat(money) <= 0) {
                alert("输入退款金额必须大于0");
                return false;
            }
            if (validate.compareTo(parseFloat(money), parseFloat(canRefundMoney))) {
                alert("输入金额必须小于可退金额");
                return false;
            }
            return true;
        },
		bankSubmit:function(){
			var bankForm = $("#bankForm"); 
			var refundMoney = $("#refundMoney").val();    	
        	if (refund.validateMoney(refundMoney)) {
            	if (validate.validateBank()) {
               	 	$('<input />').attr('type', 'hidden').attr('name', 'refundMoney').attr('value', refundMoney).appendTo(bankForm);
               	 	bankForm.submit();
            	}
        	}
		},
		alipaySubmit:function(){
			var alipayForm = $("#alipayForm");
			var refundMoney = $("#refundMoney").val(); 
        	if (refund.validateMoney(refundMoney)) {
            	if (validate.validateAlipay()) {
                	$('<input />').attr('type', 'hidden').attr('name', 'refundMoney').attr('value', refundMoney).appendTo(alipayForm);
                	alipayForm.submit();
            	}
        	}
		},
		tenpaySubmit:function(){
			var tenpayForm = $("#tenpayForm");
			var refundMoney = $("#refundMoney").val(); 
        	if (refund.validateMoney(refundMoney)) {
            	if (validate.validateTenpay()) {
                	$('<input />').attr('type', 'hidden').attr('name', 'refundMoney').attr('value', refundMoney).appendTo(tenpayForm);
                	tenpayForm.submit();
            	}
        	}
		},
		postSubmit:function(){
			var postForm = $("#postForm");
			var refundMoney = $("#refundMoney").val(); 
        	if (refund.validateMoney(refundMoney)) {
           		if (validate.validatePost()) {
                	$('<input />').attr('type', 'hidden').attr('name', 'refundMoney').attr('value', refundMoney).appendTo(postForm);
                	postForm.submit();
            	}
        	}
		}	
	};
	refund.init();
});
