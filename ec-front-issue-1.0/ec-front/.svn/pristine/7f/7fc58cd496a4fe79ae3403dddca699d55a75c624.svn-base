seajs.use(["jQuery","config"],function($,config){
	var form=$(document.loginForm);
	var uEl = form.find("input[name='name']");
	var pEl = form.find("input[name='password']");
	var rvEl = form.find("input[name='rememberId']");
	var spanEl = form.find("input[name='rememberId']");
	var errorMsg = form.find("span[name='errorMsg']");
	var errorMsg1 = form.find("span[name='errorMsg1']");
	var btn = form.find(".login_but");
	var nameMsg = form.find("span[name='nameMsg']");
	var typeMsg = form.find("span[name='typeMsg']");
	var flag = false;
	var verifyCodeEl = form.find("input[name='verifyCode']");
	var needVerifyCode = false;
	var needAccountType = false;
	
	form.submit(function(){
		var u = $.trim(uEl.val());
		var p = $.trim(pEl.val());
        var tEl = form.find("input[name='type']:checked");
		var rv = rvEl.attr("checked")== "checked" ? true : false;
		if(!u){
			nameMsg.html("<b class='red' name='errorMsg'>请在上方输入您的用户名或者邮箱</b>");
			uEl.focus();
			return false;
		}
        if(needAccountType&&!tEl.length){
            typeMsg.html("<b class='red' name='errorMsg'>请选择账户类型</b>");
            return false;
        }
		if(!p){
			pEl.focus();
			return false;
		}
		var verifyCode = $.trim(verifyCodeEl.val());
		if(needVerifyCode &&(!verifyCode)){
			errorMsg1.html(" <br/><b class='red' name='errorMsg1'>验证码错误</b>");
//		  	errorMsg.html("");
			verifyCodeEl.focus();
			return false;
		}
		
		var para = form.serialize();
		$.ajax({
			url:"/verify?format=json",
			type:"GET",
			dataType:"json",
			data:para,
			beforeSend:function(d){btn.attr("disabled","disabled");},
			error:function(d){errorMsg.html("<b class='red' name='errorMsg'>登陆异常,请致电客服.</b>	");},
			success:function(d){
			  if(d.status==1){
			  	errorMsg.html("跳转中<img alt=\"加载中\" src=\"https://passport.winxuan.com/css/images/loading_16x16.gif\">	");
			  	errorMsg1.html("");
			    window.location.href = d.returnUrl||config.portalServer;
			  }
			  if(d.status==2){
			  	errorMsg.html("	<b class='red' name='errorMsg'>参数错误</b>");
			  	errorMsg1.html("");
			  }
			  if(d.status==3){
				  if(parseInt(d.lockCountDown)<=0){
					   errorMsg.html("<b class='red' name='errorMsg'>该账号已被锁定,<a href='https://passport.winxuan.com/findPassword'>点击这里修改密码</a></b>");
				  }else{
					  errorMsg.html("<b class='red' name='errorMsg'>用户名或者密码错误,剩余次数"+d.lockCountDown+"</b>	");
				  }
				  	errorMsg1.html("");
			  }
			  if(d.status==4){
				  	errorMsg.html("<b class='red' name='errorMsg'>该账号已被锁定,<a href='https://passport.winxuan.com/findPassword'>点击这里修改密码</a></b>");
				  	errorMsg1.html("");
			  }
			  if(d.status==5){
				  	errorMsg1.html(" <br/><b class='red' name='errorMsg1'>验证码错误</b>");
				  	//errorMsg.html("");没有文字会跳层的...
				  	changeValidateCode();
			  }
			  needVerifyCodeRequest();
			},
			complete:function(d){btn.removeAttr("disabled");}
		});
		return false;
	});
	form.find(":input[bind='focus']").focus(function(){
		  $(this).attr("class" , "red_input");
	}); 
	form.find(":input[bind='focus']").focusout(function(){
		  $(this).attr("class" , "w_input");
	});
	pEl.focusin(function(){
		if(flag){return false;}
		$.ajax({
			url:"/usersource?format=json",
			type:"GET",
			dataType:"json",
			data:form.serialize(),
			error:function(d){errorMsg.html("<b class='red' name='errorMsg'>账号验证异常,请致电客服</b>");errorMsg1.html("");},
			beforeSend:function(d){flag=true;btn.attr("disabled","disabled");errorMsg.html("<img alt=\"加载中\" src=\"https://passport.winxuan.com/css/images/loading_16x16.gif\">");errorMsg1.html("");},
			success:function(d){
			     if(d.status==1){
				   errorMsg.html("<b class='red' name='errorMsg'>密码默认为手机或者固定电话后6位</b>");
				   errorMsg1.html("");
				 }else{
				 	errorMsg.html("<b class='red' name='errorMsg'>请输入您的登录密码</b>");
				 	errorMsg1.html("");
				 }
			},
			complete:function(d){btn.removeAttr("disabled");flag=false;}
		});		
	});
	$("img[bind='verifyCodeImage']").click(function(){
		changeValidateCode();
	});
	$("a[bind='verifyCodeImage']").click(function(){
		changeValidateCode();
	});
	function changeValidateCode() {  
		var timenow = new Date().getTime();  
		$("#verifyCodeImage")[0].src="https://passport.winxuan.com/verifyCode?d="+timenow;
	}
	
	
	needVerifyCodeRequest();
	uEl.focusout(function(){
		needVerifyCodeRequest();
	});
	function needVerifyCodeRequest(){
		var username = $.trim(uEl.val());
		$.ajax({
			   type: "GET",
			   url: "/needVerifyCode.json",
			   data: "username=" + encodeURIComponent(username) ,
			   success: function(data){
			   		if(data.needVerifyCode){
			   			showVerifyCode();
			   		}else{
			   			hideVerifyCode();
			   		}
			   		if(data.needAccountType){
			   		    showAccountType();
			   		}else{
                        hideAccountType();
                    }
			   }
		}); 
	}
	function showVerifyCode(){
		needVerifyCode = true;
		var verifyNum = getCookie("verify_number");
		if(!verifyNum){
			changeValidateCode();
		}
		form.find("#verifyCodeInput").show();
	}
	function hideVerifyCode(){
		needVerifyCode = false;
		var errMsg = form.find("b[name='errorMsg']");
		if($.trim(errMsg.text()) == '验证码错误'){
			errMsg.html("");
		}
		form.find("#verifyCodeInput").hide();
	}
	function showAccountType(){
        needAccountType = true;
        form.find("#accountSelect").show();
    }
    function hideAccountType(){
        needAccountType = false;
        var errMsg = form.find("b[name='errorMsg']");
        if($.trim(errMsg.text()) == '请选择账户类型'){
            errMsg.html("");
        }
        form.find("#accountSelect").hide();
    }
	function getCookie(objName){//获取指定名称的cookie的值
	    var arrStr = document.cookie.split("; ");
	    for(var i = 0;i < arrStr.length;i ++){
	        var temp = arrStr[i].split("=");
	        if(temp[0] == objName) return unescape(temp[1]);
	   } 
	}

});