seajs.use(["jQuery"],function($){
	var emailEl = $("input[name='email']"),
		verifyCodeEl = $("input[name='verifyCode']"),
		submitEl = $("input[name='submit']");
	
	var emailInfoEl = $("span[name='email_info']"),
		verifyCodeInfoEl = $("span[name='verify_info']");
	
	var emailInfo = "请输入您注册时的邮箱",
		emailErr = "请输入正确的邮箱地址",
		verifyCodeErr = " 请输入4位数字验证码";
	
	var	emailReg = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$",
		verifyCodeReg = "^[A-Za-z0-9_-]{4}$";
	
	var greyClass = "l_gray",
		redClass ="red",
		rightClass = "input_r";
	
	emailEl.focus(function(){
		focusEl(emailEl,emailInfoEl,emailInfo);
	}); 
	emailEl.focusout(function(){
		focusoutEl(emailEl,emailInfoEl,emailErr,emailReg,emailErr);
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
		if(focusoutEl(emailEl,emailInfoEl,emailErr,emailReg,emailErr) &
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
	function focusoutEl(el,elInfo,error,reg,blankErr){
		var str = el.val();
		if(!str){
			elInfo.attr("class",redClass);
			elInfo.html(blankErr);
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
	
	
	$("#verifyCodeImage").click(function(){
		changeValidateCode();
	});
	function changeValidateCode() {  
		var timenow = new Date().getTime();  
		$("#verifyCodeImage")[0].src=""+ document.location.protocol +"//passport.winxuan.com/verifyCode?d="+timenow;
	}
});