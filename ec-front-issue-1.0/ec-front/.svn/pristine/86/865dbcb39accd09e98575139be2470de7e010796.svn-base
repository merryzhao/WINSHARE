seajs.use(["jQuery","toolkit","score"],function($,toolkit,score){	
	new toolkit();
	var instance=score({	
		});
	var commentForm = $("form[bind='commentForm']");	
	var productSaleId = $(".productSaleId").attr("value");
	
	var canPCFsubmit = true;
	$("input[bind='newComment']").click(function(){
		var title =$("#commentTitle").val(),
		    content =$("#commentContent").val(), 
			verifyCode=$("#commentVerifyCode").val();	
		if (check(title,content,verifyCode)) {
			if(!canPCFsubmit){
				alert("正在提交, 请勿多次操作!");
				return false;
			}
			canPCFsubmit = false;
			commentForm.submit();
		}
	  });
  $("a[bind='changeVerifyCode']").click(function(){
  		var srcUrl = $(".verifyCodeImg").attr("src");
		$(".verifyCodeImg").attr("src",srcUrl+"?d="+Math.random);
  });
    $(".verifyCodeImg").click(function(){
  		var srcUrl = $(".verifyCodeImg").attr("src");
		$(".verifyCodeImg").attr("src",srcUrl+"?d="+Math.random);
  });
function check(title,comment,verifyCode) {	
	if(trim(title).length==0 || trim(title).length>25) {
		$("div[bind='titleMessage']").hide();
		$("div[bind='titleMessageError']").show();	
		return false;
	}else{
		$("div[bind='commentMessage']").show();
		$("div[bind='commentMessageError']").hide();	
	}
	if(trim(comment).length==0 || trim(comment).length>3000) {
		$("div[bind='commentMessage']").hide();
		$("div[bind='commentMessageError']").show();
		return false;
	}else{
		$("div[bind='titleMessage']").show();
		$("div[bind='titleMessageError']").hide();	
	}
	if(trim(verifyCode).length==0||trim(verifyCode).length>4){
		$("b[bind='verifyCodeError']").show();
		return false;
	}else{
		return verify(trim(verifyCode));
	}
	return true;
}
 function trim(str){
 	return str.replace(/(^\s*)|(\s*$)/g,"");
 }
 function verify(verifyCode){
 		var url = "/product/verify?format=json",
			param = {"sRand":verifyCode};
		var blag = false;
		$.ajax({
				async:false,
				type:"GET",
				dataType: "json",
				url: url,
				data: param,
				success: function(data){		
					if(data.useful){
						blag = true;	
						$("b[bind='verifyCodeError']").hide();	
					}else{	 					
						$("b[bind='verifyCodeError']").html("验证码输入错误").show();                   				  
					}										
				},
				error:function(){					
					blag = false;
				}	
	});
	return blag;
 }

});