seajs.use(["jQuery","config","checkPwd"],function($, Config, CheckPwd){
	var seconds=60,Timer,
		msgBox=$("p.message"),
		codetip=$("p.code_tip"),
		passtip=$("p.password_tip"),
		checkForm=$("div.check_orders"),
		check=null,
		getCodeBtn=$("button[bind='getCode']");
	if(checkForm.find("input[type='radio']").length!=0){
		getCodeBtn.prop("disabled",true);
	}
	$("input[name='checkType']").removeAttr("checked");
	$("a[bind='set-checkpass']").click(function(e){
		(new CheckPwd({callback:reFresh})).show();
	});
	$("input[name='checkType']").click(function(e){
		var checkType=$(this).val(),
			hasMobile=checkForm.find("input[name='hasMobile']").val(),
			hasEmail=checkForm.find("input[name='hasEmail']").val();
		if(checkType=="mobile"){
			if(hasMobile==0){
				alert("你尚未绑定手机，请使用其他方式获取验证码！");
				$(this).removeAttr("checked");
			}else{
				check=checkType;	
				getCodeBtn.prop("disabled",false);
			}
		}else if(checkType=="email"){
			if(hasEmail==-1){
				alert("你尚未绑定邮箱，请使用其他方式获取验证码！");
				$(this).removeAttr("checked");
			}else{
				check=checkType;	
				getCodeBtn.prop("disabled",false);
			}
		}
	});
	$("button[bind='getCode']").click(function(e){
		e.preventDefault();
		var userType=checkForm.find("input[name='userType']").val();
		var mobileUrl=Config.portalServer+"/customer/advanceaccount/sendPayCheckCode?format=jsonp&callback=?",
			emailUrl=Config.portalServer+"/customer/advanceaccount/sendVerifyCodeToEmail?format=jsonp&callback=?",
			el=$(this);
		if(userType!=0&&check=="email"){
			$.getJSON(emailUrl,function(data){
				if(data.status==1){
					msgBox.css("visibility","hidden");
					codetip.css("visibility","hidden");
					renderCodeField(el);
				}else{
					msgBox.html(data.message);
					msgBox.css({
						"visibility":"visible"
					});
				}
			});	
		}else{
			$.getJSON(mobileUrl,function(data){
				if(data.status){
					msgBox.css("visibility","hidden");
					codetip.css("visibility","hidden");
					renderCodeField(el);
				}else{
					msgBox.html(data.message);
					msgBox.css({
						"visibility":"visible"
					});
				}
			});	
		}
	});
	function reFresh() {
		window.location.reload(); 
	}
	function renderCodeField(el){
		var html="(<span id='second'>60</span>)秒后重新获取";
		$("input.code").show();
		el.html(html);
		el.prop("disabled",true);
		Timer=setInterval(function(){time(el)},1000);
	}
	function time(el){
		seconds=seconds-1;
		$("#second").html(seconds);
		if(seconds<0){
			el.html("点此免费获取");
			el.prop("disabled",false);
			clearInterval(Timer);
			seconds=60;
		}
	}
	$("button[bind='withdraw']").click(function(e){
		codetip.css("visibility","hidden");
		passtip.css("visibility","hidden");
		var url=Config.portalServer+"/customer/advanceaccount/applyRefundConfirm?format=jsonp&callback=?";
		$.post(url,getFormData(),function(data){
			if(data.status=="0"){
				window.location.href=Config.portalServer+"/customer/advanceaccount/refund_ok";
			}else{
				msgBox.html(data.message);
				msgBox.css("visibility","visible");
			}
		}, "json");
	});
	function getFormData(){
		var data={};
		$("div.check_orders").find("input").each(function(){
			var el=$(this),
				name=el.attr("name"),
				value=el.val();
			if((!!name)&&name.length>0){
				data[name]=value;					
			}
		});
		return data;
	}
})