seajs.use(["jQuery"],function($){
	var context = $("ul.tips_tab");
			var tipscon = $("div.tips_con");
			tipscon.css("height","958px");
			tipscon.hide();
			$(tipscon.get(0)).show();
			var li = context.find("li");
			li.click(function(){
				tipscon.hide();
				li.removeClass("cur_tab");
				$(this).addClass("cur_tab");
				var index = $(this).index();
				$(tipscon.get(index)).show();
			 });
	var view={
		init:function(){
			this.cardfrom = $("#activate");
			this.cardno = $("[name='card']");
			this.cardpwd = $("[name='password']");
			this.loginbut = $(".login_but");
			this.logininfo = $("[name='logininfo']");
			this.bind();
		},
		bind:function(){
			var obj = this;
			this.loginbut.click(function(){
				if(!obj.validation()){return false;};
			
			});
			if(this.logininfo.val()!="")
			{
				alert(this.logininfo.val());
				this.logininfo.val("");
			}
			 
		},
		validation:function(){
				if(this.cardno.val().length == 0){
					alert("请输入卡号")
					return false;
				}else if(isNaN(this.cardno.val())){
					alert("卡号只能为数字")
					return false;
				}
				if(this.cardpwd.val().length == 0){
					alert("请输入密码")
					return false;
				}
				return true;
			
		}
		
		
	}	
	view.init();
	
	});