seajs.use(["jQuery"],function($){
	
	var nameValidate = true;
	var emailValidate = true;
	var loading = "<img src='"+ document.location.protocol +"//passport.winxuan.com/images/loading.gif'/>";
	
	var nameEl = $("input[name='name']"),
		passwordEl = $("input[name='password']"),
		rePasswordEl = $("input[name='passwordConfirm']"),
		emailEl = $("input[name='email']"),
		verifyCodeEl = $("input[name='verifyCode']"),
		agreeEl = $("input[name='agree']"),
		submitEl = $("input[name='submit']");
	
	var nameInfo = '用户名：6-40位字符，可由中文、英文、数字及"_"、"-"组成',
		passwordInfo = '密码：6-16位字符，可由英文、数字及"_"、"-"组成',
		rePasswordInfo = "两次输入密码不一致",
		emailErr = '请输入正确的邮箱地址且长度不能超过40个字符',
		emailInfo = "您的常用邮箱，如abc@sina.com,用来登录、找回密码、接收订单通知等",
		verifyCodeErr = " 请输入4位验证码",
		agreeInfo = "* 必须同意才能注册";
		
		
	var nameInfoEl = $("span[name='name_info']"),
		passwordInfoEl = $("span[name='password_info']"),
		rePasswordInfoEl = $("span[name='re_password_info']"),
		emailInfoEl = $("span[name='email_info']"),
		verifyCodeInfoEl = $("span[name='verify_info']"),
		agreeInfoEl = $("span[name='agree_info']");
	
	var nameReg = "^([a-zA-Z0-9_-]{1,21})@([a-zA-Z0-9_-]){1,10}((\.[a-zA-Z0-9_-]{2,3}){1,2})$",
		passwordReg = "^[A-Za-z0-9_-]{6,16}$",
		emailReg = "^([a-zA-Z0-9_-]{1,21})@([a-zA-Z0-9_-]){1,10}((\.[a-zA-Z0-9_-]{2,3}){1,2})$",
		verifyCodeReg = "^[A-Za-z0-9_-]{4}$";
	
	var greyClass = "l_gray",
		redClass ="red",
		rightClass = "input_r";
	
	/*
nameEl.focus(function(){
		nameEl.val($.trim(nameEl.val()));
		focusEl(nameEl,nameInfoEl,nameInfo);
	}); 
	nameEl.focusout(function(){
		nameEl.val($.trim(nameEl.val()));
		var success = focusoutEl(nameEl,nameInfoEl,nameInfo,nameReg);
		if(success){
			nameInfoEl.attr("class" , greyClass);
			nameInfoEl.html(loading);
			$.ajax({
				   type: "GET",
				   url: "/nameExist",
				   data: "name=" + nameEl.val() + "&format=json" ,
				   success : (function(data){
				   		if(data.status == true){
							nameInfoEl.attr("class" , redClass);
							nameInfoEl.html("此用户名已经存在");
							nameValidate = false;
						}
						else{
							nameInfoEl.attr("class" , rightClass);
							nameInfoEl.html("");
							nameValidate = true;
						}
				   })
			}); 
		}
	});
*/
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
	emailEl.focus(function(){
		focusEl(emailEl,emailInfoEl,emailInfo);
	}); 
	emailEl.focusout(function(){
		var success = focusoutEl(emailEl,emailInfoEl,emailErr,emailReg);
		if(success){
			emailInfoEl.attr("class" , greyClass);
			emailInfoEl.html(loading);
			$.ajax({
				   type: "GET",
				   url: "/emailExist",
				   data: "email=" + emailEl.val() + "&format=json" ,
				   success : (function(data){
				   		if(data.status == true){
							emailInfoEl.attr("class" , redClass);
							emailInfoEl.html("此邮箱已经存在");
							emailValidate = false;
						}
						else{
							emailInfoEl.attr("class" , rightClass);
							emailInfoEl.html("");
							emailValidate = true;
						}
				   })
			}); 
		}
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
		
		if(!nameValidate){
			return false;
		}
		if(!emailValidate){
			return false;
		}
		var email=emailEl.val();
			/*
			name=email.substring(0,email.indexOf("@"));
			*/
		nameEl.val(email);
		if(focusoutEl(nameEl,nameInfoEl,nameInfo,nameReg) &
			focusoutEl(passwordEl,passwordInfoEl,passwordInfo,passwordReg) &
			rePasswordCheck() &
			focusoutEl(emailEl,emailInfoEl,emailErr,emailReg) &
			focusoutEl(verifyCodeEl,verifyCodeInfoEl,verifyCodeErr,verifyCodeReg) &
			agreeCheck()){
				verifyCodeInfoEl.attr("class", "");
				return true;
		}
		else{
			return false;
		}
	}); 
	
	
	function focusEl(el,elInfo,info){
		elInfo.attr("class",greyClass);
		elInfo.html(info);
	}
	function focusoutEl(el,elInfo,error,reg){
		var str = el.val();
		 if(new RegExp(reg).test(str)){
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
	function agreeCheck(){
		if(agreeEl.attr("checked") != "checked"){
			agreeInfoEl.attr("class",redClass);
			agreeInfoEl.html(agreeInfo);
			return false;
		}
		else{
			agreeInfoEl.html("");
			return true;
		}
	}
	
	
	$(document).ready(function(){
		$("#verifyCodeImage").click(function(){
			changeValidateCode();
		});
	});
	function changeValidateCode() {  
		var timenow = new Date().getTime();  
		$("#verifyCodeImage")[0].src=""+ document.location.protocol +"//passport.winxuan.com/verifyCode?d="+timenow;
	}
});