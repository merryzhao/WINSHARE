seajs.use(["jQuery","ui"],function($,ui){
	var view = {
		init :function() {
			this.password = $("[name='password']");
			this.newPassWord = $("[name='newPassWord']");
			this.newPassWord2 = $("input.newPassWord2");
			this.passave = $("input.pas_save");
			this.pascancel =$("input.pas_cancel");
			this.activate=$("form#activate");
			this.bind();
			
			
		},
		bind:function(){
			var obj  = this;
			this.pascancel.click(function(){obj.clear()});
			this.passave.click(function(){obj.check()});
			
		},
		check:function(){
		  if(this.password.val().length>300){
		  	alert("旧密码长度错误");
		  	return false;
		  }
		  if(this.password.val().length<1){
		  	alert("请输入旧密码");
		  	return false;
		  }
		  if(this.newPassWord.val().length>20){
		  	alert("密码请限制在20位以内");
		  	return false;
		  }
		  if(this.newPassWord.val().length<6){
		  	alert("请输入长度大于6位的新密码");
		  	return false;
		  }
		  if(this.newPassWord2.val() != this.newPassWord.val()){
		  	alert("两次新密码确认不一致");
		  	return false;
		  }
		  
			this.activate.submit();
			
		},
		clear:function(){
			this.password.val("");
			this.newPassWord.val("");
			this.newPassWord2.val("");
		}
		
	};
	
	view.init();
});