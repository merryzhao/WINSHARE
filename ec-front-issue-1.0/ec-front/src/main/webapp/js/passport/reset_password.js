seajs.use(["jQuery"],function($){
	
	var passwordEl = $("input[name='password']"),
		rePasswordEl = $("input[name='passwordConfirm']"),
		verifyCodeEl = $("input[name='verifyCode']"),
		submitEl = $("input[name='submit']");
	
	var 
		passwordInfo = '密码：6-16为字符，可由英文、数字及"_"、"-"组成',
		rePasswordInfo = "两次输入密码不一致",
		verifyCodeErr = " 请输入4位数字验证码";
		
	var passwordInfoEl = $("span[name='password_info']"),
		rePasswordInfoEl = $("span[name='re_password_info']"),
		verifyCodeInfoEl = $("span[name='verify_info']");
		
	
	var passwordReg = "^[A-Za-z0-9_-]{6,16}$",
		verifyCodeReg = "^[A-Za-z0-9_-]{4}$";
	
	var greyClass = "l_gray",
		redClass ="red",
		rightClass = "input_r";
	
	passwordEl.focus(function(){
		focusEl(passwordEl,passwordInfoEl,passwordInfo);
	}); 
	passwordEl.focusout(function(){
		focusoutEl(passwordEl,passwordInfoEl,passwordInfo,passwordReg);
	});
	rePasswordEl.focus(function(){
		rePasswordInfoEl.html("");
	});
	rePasswordEl.focusout(function(){
		rePasswordCheck();
	});
	verifyCodeEl.focus(function(){
		verifyCodeEl.val($.trim(verifyCodeEl.val()));
		focusEl(verifyCodeEl,verifyCodeInfoEl,"");
	}); 
	verifyCodeEl.focusout(function(){
		verifyCodeEl.val($.trim(verifyCodeEl.val()));
		if(focusoutEl(verifyCodeEl,verifyCodeInfoEl,verifyCodeErr,verifyCodeReg)){
			verifyCodeInfoEl.attr("class", "");
		}
	});
	
	submitEl.click(function(){
		var validate = false;
		//先验空
		if(focusoutEl(passwordEl,passwordInfoEl,passwordInfo,passwordReg) &
				rePasswordCheck() &
				focusoutEl(verifyCodeEl,verifyCodeInfoEl,verifyCodeErr,verifyCodeReg)){
			validate = true;
		}
		if(!validate){
			alert("请填写完正确完整的信息");
		}
		return validate;
	}); 
	
	
	function focusEl(el,elInfo,info){
		elInfo.attr("class",greyClass);
		elInfo.html(info);
	}
	function focusoutEl(el,elInfo,error,reg){
		var str = el.val();
		if(!str){
			elInfo.html("");
		}
		else if(new RegExp(reg).test(str)){
			elInfo.attr("class",rightClass);
			elInfo.html("");
			return true;
		}
		else{
			elInfo.attr("class",redClass);
			elInfo.html(error);
		}
		return false;
	}
	function rePasswordCheck(){
		if(rePasswordEl.val() != passwordEl.val()){
			rePasswordInfoEl.attr("class",redClass);
			rePasswordInfoEl.html(rePasswordInfo);
		}
		else if(!rePasswordEl.val()){
			rePasswordInfoEl.html("");
		}
		else{
			rePasswordInfoEl.html("");
			rePasswordInfoEl.attr("class",rightClass);
			return true;
		}
		return false;
	}
	
	
	$("#verifyCodeImage").click(function(){
		changeValidateCode();
	});
	function changeValidateCode() {  
		var timenow = new Date().getTime();  
		$("#verifyCodeImage")[0].src = document.location.protocol +"//passport.winxuan.com/verifyCode?d="+timenow;
	}
});