$().ready(function(){
   //表单验证
 	$("#shopForm").validate({
		rules:{
			    name:{
			    		required:true
		        } , 
		       	passWrod:{
		       		    required:true
		       	} ,
		        shopName:{
		    		required:true
			    } , 
			    businessScope:{
	       		    required:true
			    } ,
			    companyName:{
		    		required:true
			    } , 
			    chargeMan:{
	       		    required:true
			    } ,
			    phone:{
			    	required:true,
			    	isTelephone:true
			    } , 
			    mobilePhone:{
			    	required:true,
			    	isCellphone:true
			    } ,
			    address:{
			    	required:true
			    } ,
			    email:{
			    	required:true,
			    	email:true
			    } , 
			    zipCode:{
			    	required:true,
			    	isZipCode:true
			    } ,
			    bankAcount:{
			    	required:true,
			    	isBankAcount:true
			    } ,
			    bank:{
			    	required:true
			    } ,
			    serviceTel:{
			    	required:true
			    },
			    endDate:{
			    	required:true
			    } ,
			    deliveryFee:{
			    	required:true,
			    	number:true,
			    	range:[0,999]
			    } 
 	    },
 	    
	messages:{
				name:{
			    		required:"账号不能为空"
			    } , 
			   	passWrod:{
			   		    required:"密码不能为空"
			   	} ,
			    shopName:{
					required:"店铺名不能为空"
			    } , 
			    businessScope:{
					    required:"经营分类不能为空"
			    } ,
			    companyName:{
			    	required:"公司名称不能为空"
			    } , 
			    chargeMan:{
					    required:"负责人不能为空"
			    } ,
			    phone:{
			    	required:"固定电话不能为空",
			    	isTelephone:"固定电话格式错误"
			    } , 
			    mobilePhone:{
			    	required:"移动不能为空",
			    	isCellphone:"移动电话格式错误"
			    } ,
			    address:{
			    	required:"公司地址不能为空"
			    } ,
			    email:{
			    	required:"邮箱不能为空",
			    	email:"邮箱格式错误"
			    } , 
			    zipCode:{
			    	required:"邮编不能为空",
			    	isZipCode:"邮编格式错误"
			    } ,
			    bankAcount:{
			    	required:"银行账号不能为空",
			    	isBankAcount:"银行账号格式错误"
			    } ,
			    bank:{
			    	required:"银行名称不能为空"
			    } ,
			    serviceTel:{
			    	required:"客户电话不能为空"
			    },
			    endDate:{
			    	required:"店铺截止日期不能为空"
			    } ,
			    deliveryFee:{
			    	required:"卖家运费不能为空",
			    	number:"运费格式错误",
			    	range:"运费范围为[0,999]"
			    } 
			 
 	}
	});	


});

